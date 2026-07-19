<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import api from './api.js'
import { CONFIG } from './config.js'
import ModalChiTietPhieuTacNghiep from '../tacnghiep-hethong-thietbi/chitiet-phieu-tacnghiep/index.vue'
import KetNoiDien from '../ketnoi-dien/index.vue'

export default {
  name: 'DanhSachCongViec',
  components: {
    ModalChiTietPhieuTacNghiep,
    KetNoiDien,
  },
  data() {
    return {
      searchConditions: {
        loaicvId: 0,
        donviId: 0,
        idcId: 0,
        nhanvienId: 0,
        phanloai: 0,
        ycBatdau: null,
        ycKetthuc: null,
        ychtBatdau: null,
        ychtKetthuc: null,
        khachhang: '',
        congviec: ''
      },
      displayConditions: {
        loaicvId: 0
      },
      danhSachCongViec: [],
      loaiCongViecList: [],
      donViList: [],
      idcList: [],
      idcByNhanVienList: [],
      nhanVienList: [],
      phanLoaiList: [],
      isLoaicvIdFromUrl: false
    }
  },
  computed: {
    selectedLoaiCongViecText() {
      if (this.displayConditions.loaicvId == 0 || !this.displayConditions.loaicvId) {
        return 'Tất cả loại công việc'
      }
      const selectedItem = this.loaiCongViecList.find(item => item.value == this.displayConditions.loaicvId)
      return selectedItem ? selectedItem.text : 'Không xác định'
    }
  },
  mounted() {
    this.loadDanhMuc()
  },
  methods: {
    async loadDanhMuc() {
      try {
        // Kiểm tra loaicv từ URL query parameters
        const loaicvIdFromUrl = this.$route.query.loaicv
        
        // Load danh mục loại công việc
        const loaiCongViecResponse = await api.getDanhMucLoaiCongViec(this.$root.context, {
          loaiDanhMuc: 'IDC-LOAICV'
        })
        this.loaiCongViecList = [
          { value: 0, text: '-- Tất cả --' },
          ...(loaiCongViecResponse.data || []).map(item => ({
            value: item.LOAICV_ID,
            text: item.TEN
          }))
        ]

        // Load danh mục đơn vị mà đã có trong thi công
        const donViResponse = await api.getDanhMucLoaiCongViec(this.$root.context, {
          loaiDanhMuc: 'IDC-DONVI'
        })
        this.donViList = [
          { value: 0, text: '-- Tất cả --' },
          ...(donViResponse.data || []).map(item => ({
            value: item.DONVI_ID,
            text: item.TEN_DV
          }))
        ]
        
        // Xử lý loaicv từ URL
        if (!loaicvIdFromUrl) {
          this.$toast.error('Loại công việc không hợp lệ')
          this.$router.push('/tainguyen-idc/home')
          return
        }

        const loaicvIdNumber = parseInt(loaicvIdFromUrl)
        const foundItem = this.loaiCongViecList.find(item => item.value === loaicvIdNumber)
        
        if (!foundItem) {
          this.$toast.error('Loại công việc không hợp lệ')
          this.$router.push('/tainguyen-idc/home')
          return
        }

        this.searchConditions.loaicvId = loaicvIdNumber
        this.displayConditions.loaicvId = loaicvIdNumber 
        this.isLoaicvIdFromUrl = true

        this.phanLoaiList = [
          { value: 0, text: '-- Tất cả --' },
          { value: 1, text: 'Khách hàng' },
          { value: 2, text: 'Nội bộ' }
        ]

        // Load danh sách IDC dựa trên nhân viên
        const idcByNhanVienResponse = await api.getDanhMucLoaiCongViec(this.$root.context, { 
          loaiDanhMuc: 'GET-IDC-BY-NHANVIENID',
          thamSo1: this.$root.token.getNhanVienID(),
         })
        this.idcByNhanVienList = [
          { value: 0, text: '-- Tất cả --' },
          ...(idcByNhanVienResponse.data || []).map(item => ({
            value: item.ID,
            text: item.TEN
          }))
        ]
        
        await this.onSearch()
      } catch (error) {
        console.error('Lỗi khi tải danh mục:', error)
        this.$toast.error('Có lỗi xảy ra khi tải danh mục')
      }
    },

    async onChangeDonVi(value) {
      // Reset các giá trị phụ thuộc
      this.searchConditions.idcId = 0
      this.searchConditions.nhanvienId = 0
      this.idcList = [{ value: 0, text: '-- Tất cả --' }]
      this.nhanVienList = [{ value: 0, text: '-- Tất cả --' }]

      if (!value || value == 0) return

      try {
        this.$root.showLoading(true)
        // Load danh sách IDC dựa trên đơn vị
        const idcResponse = await api.getDanhMucIDC(this.$root.context, { donviId: value })
        this.idcList = [
          { value: 0, text: '-- Tất cả --' },
          ...(idcResponse.data || []).map(item => ({
            value: item.ID,
            text: item.TEN
          }))
        ]

        // Load danh sách nhân viên dựa trên đơn vị
        const nhanVienResponse = await api.getDanhMucNhanVien(this.$root.context, { donviId: value })
        this.nhanVienList = [
          { value: 0, text: '-- Tất cả --' },
          ...(nhanVienResponse.data || []).map(item => ({
            value: item.NHANVIEN_ID,
            text: item.TEN_NV
          }))
        ]
      } catch (error) {
        console.error('Lỗi khi tải danh mục phụ thuộc:', error)
        this.$toast.error('Có lỗi xảy ra khi tải danh mục phụ thuộc')
      } finally {
        this.$root.showLoading(false)
      }
    },

    async onSearch() {
      try {
        this.$root.showLoading(true)
        this.displayConditions.loaicvId = this.searchConditions.loaicvId

        const searchParams = { ...this.searchConditions }
        
        // Chuyển đổi giá trị 0 thành null cho các select box
        if (searchParams.loaicvId == 0) searchParams.loaicvId = null
        if (searchParams.donviId == 0) searchParams.donviId = null
        if (searchParams.idcId == 0) searchParams.idcId = null
        if (searchParams.nhanvienId == 0) searchParams.nhanvienId = null
        if (searchParams.phanloai == 0) searchParams.phanloai = null
        
        // Xử lý chuỗi rỗng
        if (!searchParams.khachhang || searchParams.khachhang.trim() === '') {
          searchParams.khachhang = null
        }
        if (!searchParams.congviec || searchParams.congviec.trim() === '') {
          searchParams.congviec = null
        }
        
        const response = await api.getDanhSachCongViec(this.$root.context, searchParams)
        this.danhSachCongViec = response.data || []
      } catch (error) {
        console.error('Lỗi khi tìm kiếm:', error)
        this.$toast.error('Có lỗi xảy ra khi tìm kiếm dữ liệu')
      } finally {
        this.$root.showLoading(false)
      }
    },

    actionColumn(parent) {
      return function () {
        return {
          template: {
            template: `<div class="action-buttons gap-1">
                <button class="btn btn-sm btn-outline-success" title="Xử lý" @click="xuLyItem()" :disabled="!canProcess">
                  <i class="fa fa-cogs"></i>
                </button>
              </div>`,
            computed: {
              canProcess() {
                // return this.data.TRANGTHAI_THUCHIEN !== 'Hoàn thành thi công' ||
                // this.data.TRANGTHAI_THUCHIEN !== 'Hoàn thành';
                return true;
              },
            },
            methods: {
              xuLyItem() {
                parent.xuLyCongViec(this.data);
              },
            },
          },
        };
      };
    },

    xuLyCongViec(data) {
      const loaiCongViecId = data.LOAICV_ID;
      
      if (!loaiCongViecId) {
        this.$toast.error('Không xác định được loại công việc');
        return;
      }
      
      // Lấy cấu hình cho loại công việc này
      const config = CONFIG[loaiCongViecId];
      
      if (!config) {
        this.$toast.error(`Chưa có cấu hình xử lý cho loại công việc ${loaiCongViecId}`);
        return;
      }
      
      // Xử lý theo loại cấu hình
      if (config.type === 'navigate') {
        // Điều hướng đến route
        const routeParams = {};
        
        // Xử lý params từ config
        if (config.params) {
          Object.keys(config.params).forEach(key => {
            const paramValue = config.params[key];
            if (typeof paramValue === 'string' && paramValue.startsWith('data.')) {
              // Lấy giá trị từ data object
              const fieldName = paramValue.replace('data.', '');
              routeParams[key] = data[fieldName];
            } else {
              routeParams[key] = paramValue;
            }
          });
        }
        // bổ sung param check edit mode
        routeParams['isEditMode'] = 'true';

        this.$router.push({
          path: config.route,
          query: routeParams
        });
        
      } else if (config.type === 'modal') {
        // Mở modal
        this.openModal(config, data);
      }
    },

    async openModal(config, data) {
      try {
        // Tạo props cho modal
        const modalProps = {};
        if (config.modalProps) {
          Object.keys(config.modalProps).forEach(key => {
            const propValue = config.modalProps[key];
            if (typeof propValue === 'string' && propValue.startsWith('data.')) {
              // Lấy giá trị từ data object
              const fieldName = propValue.replace('data.', '');
              modalProps[key] = data[fieldName];
            } else {
              modalProps[key] = propValue;
            }
          });
        }
        
        // Lấy thông tin modal từ config
        const refName = config.refName || 'ModalChiTietPhieuTacNghiep';
        const methodName = config.methodName || 'show';
        const methodParams = config.methodParams || ['data', 'VIEW'];
        
        // Mở modal động dựa trên component name và method
        if (this.$refs[refName]) {
          // Chuẩn bị tham số cho method
          const params = methodParams.map(param => {
            if (param === 'data') return data;
            if (param === 'VIEW') return 'VIEW';
            if (param === 'EDIT') return 'EDIT';
            return param;
          });
          // Gọi method với tham số
          this.$refs[refName][methodName](...params);
        } else {
          console.error(`Modal component ${refName} not found in refs`);
          this.$toast.error(`Không tìm thấy modal ${refName}`);
        }
        
      } catch (error) {
        console.error('Lỗi khi mở modal:', error);
        this.$toast.error('Có lỗi xảy ra khi mở modal');
      }
    },
  }
}
</script>
