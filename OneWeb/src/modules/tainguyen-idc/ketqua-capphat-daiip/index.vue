<template src="./index.html"></template>

<script>
import api from './api.js'

export default {
  name: 'KetQuaCapPhatDaiIp',
  components: {},
  data() {
    return {
      searchConditions: {
        donviId: 0,
        loaiCvId: 0,
        maThuebao: null,
        ngayYcStart: null,
        ngayYcEnd: null,
        nhanvienThId: null,
        tenKh: null,
        trangthaiId: null,
        phanloai: null,
        khachhang: null,
        congviec: null,
        idcId: 0,
        ipStoreName: null,
        ngayHTStart: null,
        ngayHTEnd: null,
      },
      displayConditions: {
        loaiCvId: 0
      },
      danhSachCongViec: [],
      loaiCongViecList: [],
      donViList: [],
      trangThaiList: [],
      nhanVienList: [],
      isLoaicvIdFromUrl: false,
      phanLoaiList: [],
      idcByNhanVienList: [],
    }
  },
  computed: {
    selectedLoaiCongViecText() {
      if (this.displayConditions.loaiCvId == 0 || !this.displayConditions.loaiCvId) {
        return 'Tất cả loại công việc'
      }
      const selectedItem = this.loaiCongViecList.find((item) => item.value == this.displayConditions.loaiCvId)
      return selectedItem ? selectedItem.text : 'Không xác định'
    },
    
    // Thêm computed property để kiểm tra xem có thể chọn nhân viên không
    isNhanVienSelectDisabled() {
      return !this.searchConditions.donviId || this.searchConditions.donviId == 0
    },

    isEditMode() {
      const isEditMode = this.$route.query.isEditMode;
      return isEditMode === 'true' || isEditMode === true;
    },
  },
  mounted() {
    this.searchConditions.donviId = this.$root.token.getDonViID()
    this.loadDanhMuc()
  },
  methods: {
    async loadDanhMuc() {
      try {
        const loaicvIdFromUrl = this.$route.query.loaicv || this.$route.params.loaicv
        const loaiCongViecResponse = await api.getDanhMucLoaiCongViec(this.$root.context, {
          loaiDanhMuc: 'IDC-LOAICV'
        })
        this.loaiCongViecList = [
          { value: 0, text: '-- Tất cả --' },
          ...(loaiCongViecResponse.data || []).map((item) => ({
            value: item.LOAICV_ID,
            text: item.TEN
          }))
        ]

        const donViResponse = await api.getDanhMucLoaiCongViec(this.$root.context, {
          loaiDanhMuc: 'IDC-DONVI'
        })
        this.donViList = [
          { value: 0, text: '-- Tất cả --' },
          ...(donViResponse.data || []).map((item) => ({
            value: item.DONVI_ID,
            text: item.TEN_DV
          }))
        ]

        const trangThaiResponse = await api.getDanhMucLoaiCongViec(this.$root.context, {
          loaiDanhMuc: 'TRANGTHAI-THICONG-IP-IDC'
        })
        this.trangThaiList = [
          { value: 0, text: '-- Tất cả --' },
          ...(trangThaiResponse.data || []).map((item) => ({
            value: item.TRANGTHAI_ID,
            text: item.TEN
          }))
        ]

        // Khởi tạo danh sách nhân viên
        const nhanVienResponse = await api.getDanhMucNhanVien(this.$root.context, { donviId: this.$root.token.getDonViID() })
        this.nhanVienList = [
          { value: 0, text: '-- Tất cả --' },
          ...(nhanVienResponse.data || []).map(item => ({
            value: item.NHANVIEN_ID,
            text: item.TEN_NV
          }))
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

        if (loaicvIdFromUrl && loaicvIdFromUrl > 0) {
          const loaicvIdNumber = parseInt(loaicvIdFromUrl)
          const foundItem = this.loaiCongViecList.find((item) => item.value === loaicvIdNumber)
          if (foundItem) {
            this.searchConditions.loaiCvId = loaicvIdNumber
            this.displayConditions.loaiCvId = loaicvIdNumber
            this.isLoaicvIdFromUrl = true
          } else {
            this.searchConditions.loaiCvId = 0
            this.displayConditions.loaiCvId = 0
            this.isLoaicvIdFromUrl = false
          }
        } else {
          this.searchConditions.loaiCvId = 0
          this.displayConditions.loaiCvId = 0
          this.isLoaicvIdFromUrl = false
        }

        this.phanLoaiList = [
          { value: 0, text: '-- Tất cả --' },
          { value: 1, text: 'Khách hàng' },
          { value: 2, text: 'Nội bộ' }
        ]

        await this.onSearch()
      } catch (error) {
        console.error('Lỗi khi tải danh mục:', error)
        this.$toast.error('Có lỗi xảy ra khi tải danh mục')
      }
    },

    async onChangeDonVi(value) {
      this.searchConditions.nhanvienThId = null
      this.nhanVienList = [{ value: 0, text: '-- Tất cả --' }]

      if (!value || value == 0) return

      try {
        this.$root.showLoading(true)
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
        console.error('Lỗi khi tải danh sách nhân viên:', error)
        this.$toast.error('Có lỗi xảy ra khi tải danh sách nhân viên')
      } finally {
        this.$root.showLoading(false)
      }
    },

    async onSearch() {
      try {
        this.$root.showLoading(true)
        this.displayConditions.loaiCvId = this.searchConditions.loaiCvId
        const searchParams = { ...this.searchConditions }
        const response = await api.getDanhSachCongViec(this.$root.context, searchParams)
        this.danhSachCongViec = response.data || []
      } catch (error) {
        console.error('Lỗi khi tìm kiếm:', error)
        this.$toast.error('Có lỗi xảy ra khi tìm kiếm')
      } finally {
        this.$root.showLoading(false)
      }
    },

    createCapPhatDaiIp() {
      this.$router.push({ path: `/tainguyen-idc/danhsach-congviec?loaicv=${this.searchConditions.loaiCvId}` })
    },

    actionColumn(parent) {
      return function () {
        return {
          template: {
            template: `<div class="action-buttons gap-1">
              <button class="btn btn-sm btn-outline-primary" title="Xem chi tiết" @click="xemChiTiet()">
                  <i class="fa fa-eye"></i>
                </button>
                <button v-if="canEdit" class="btn btn-sm btn-outline-primary" title="Chỉnh sửa" @click="xuLyItem()">
                  <i class="fa fa-edit"></i>
                </button>
              </div>`,
            computed: {
              canEdit() {
                const trangThai = this.data.TRANGTHAI_PHIEUTC;
                const nhanVienThId = this.data.NHANVIEN_TH_ID;
                const userLoginId = this.$root.token.getNhanVienID();
                
                // nếu ở trạng thái trả phiếu thì cũng chỉ cho xem
                if (trangThai && trangThai.toLowerCase().includes('trả phiếu')) {
                  return false;
                }

                return (!trangThai || !trangThai.toLowerCase().includes('hoàn thành')) && nhanVienThId == userLoginId;
              }
            },
            methods: {
              xuLyItem() {
                parent.xuLyCongViec(this.data, false)
              },
              xemChiTiet() {
                parent.xuLyCongViec(this.data, true)
              }
            }
          }
        }
      }
    },

    xuLyCongViec(data, viewOnly) {
      try {
        const loaiCVID = data.LOAICV_ID;
        const tenLoaiCV = data.LOAICV;
        const tenTat = data.TENTAT;
        let path = '';
        const loaiCVLower = tenLoaiCV.toLowerCase();
        
        if (loaiCVID === 39 || tenTat === 'CP_DIP' || (loaiCVLower.includes('cấp') && loaiCVLower.includes('dải ip'))) {
          path = '/tainguyen-idc/tc-capphat-ip';
        } else if (loaiCVID === 40 || tenTat === 'CP_IP' || (loaiCVLower.includes('cấp') && loaiCVLower.includes('ip'))) {
          path = '/tainguyen-idc/tc-capphat-ip';
        } else if (loaiCVID === 45 || tenTat === 'TH_DIP' || (loaiCVLower.includes('thu') && loaiCVLower.includes('hồi'))) {
          path = '/tainguyen-idc/tc-thuhoi-ip';
        } else if (loaiCVID === 46 || tenTat === 'TH_IP' || (loaiCVLower.includes('thu') && loaiCVLower.includes('hồi'))) {
          path = '/tainguyen-idc/tc-thuhoi-ip';
        }
        
        this.$router.push({
          path: path,
          query: {
            phieutc: data.PHIEUTC_ID,
            isEditMode: 'true',
          }
        })
      } catch (error) {
        console.error('Lỗi khi xử lý công việc:', error)
        this.$toast.error('Có lỗi xảy ra khi xử lý công việc')
      }
    }
  }
}
</script>
<style scoped>
.info-row {
  .key {
    width: 160px !important;
  }
  .value {
    flex: 1;
  }
  .separator {
    width: 30px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
</style>
