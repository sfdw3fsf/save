<template src="./index.html"> </template>
<script>
import Vue from 'vue'
import moment from 'moment'
import DataGrid from '@/components/Controls/DataGrid/index.vue';
import SelectExt from '@/components/Controls/SelectExt/index.vue';
import { DATE_TIME_FORMAT } from '@/constants'

export default {
  name: 'DsKetQuaCapPhatRack',
  components: {
    DataGrid,
    SelectExt,
  },
  data() {
    return {
      searchConditions: {
        donviId: 0,
        maCv: null,
        ngayBatdau: null,
        ngayKetthuc: null,
        nhanvienThId: null,
        tenCv: null,
        trangthaiId: null,
        maRack: null,
        tenKh: null,
        phieuThiCongId: null,
        phanLoai: 0,
        ngayYcStart: null,
        ngayYcEnd: null,
        ngayYchtStart: null,
        ngayYchtEnd: null,
      },
      danhSachKetQua: [],
      donViList: [],
      trangThaiList: [],
      nhanVienList: [],
      nhanVienList: [],
      phanLoaiList: [],
      idcByNhanVienList: [],
    }
  },
  computed: {

  },
  watch: {},
  created: async function() {
    await this.loadLoaiCongViec();
  },
  mounted: async function() {
    this.loadDanhMuc()
  },
  methods: {
    async loadLoaiCongViec() {
      const response = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
        loaiDanhMuc: 'IDC-LOAICV',
        thamSo1: null,
        thamSo2: null
      });
      if (response && response.data && response.data.length > 0) {
        this.searchConditions.tenCv = response.data.find(item => item.LOAICV_ID == 57)?.TEN || '';
      }
    },
    async loadDanhMuc() {
      try {
        // Load danh mục idc
        const idcByNhanVienResponse = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'GET-IDC-BY-NHANVIENID',
          thamSo1: this.$root.token.getNhanVienID(),
          thamSo2: null
        })
        this.idcByNhanVienList = [
          { value: 0, text: '-- Tất cả --' },
          ...(idcByNhanVienResponse.data || []).map((item) => ({
            value: item.ID,
            text: item.TEN
          }))
        ]
        this.phanLoaiList = [
          { value: 0, text: '-- Tất cả --' },
          { value: 1, text: 'Khách hàng' },
          { value: 2, text: 'Nội bộ' }
        ]

        // Load danh mục trạng thái
        const trangThaiResponse = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'TRANGTHAI-PHIEU-THICONG',
          thamSo1: null,
          thamSo2: null
        })
        this.trangThaiList = [
          { value: 0, text: '-- Tất cả --' },
          ...(trangThaiResponse.data || []).map((item) => ({
            value: item.TRANGTHAI_ID,
            text: item.TEN
          }))
        ]

        // Load danh mục nhân viên
        const nhanVienResponse = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'NHANVIEN-DONVI-IDC',
          thamSo1: this.$root.token.getDonViID(),
          thamSo2: null
        })
        this.nhanVienList = [
          { value: 0, text: '-- Tất cả --' },
          ...(nhanVienResponse.data || []).map((item) => ({
            value: item.NHANVIEN_ID,
            text: item.TEN_NV
          }))
        ]

        await this.onSearch()
      } catch (error) {
        console.error('Lỗi khi tải danh mục:', error)
        this.$toast.error('Có lỗi xảy ra khi tải danh mục')
      }
    },

    formatDateForApi(date) {
      if (!date) return null;

      // If date is already a string in YYYY-MM-DD format, return as is
      if (typeof date === 'string' && /^\d{4}-\d{2}-\d{2}$/.test(date)) {
        return date;
      }

      // Convert date to local timezone format YYYY-MM-DD
      const dateObj = new Date(date);
      if (isNaN(dateObj.getTime())) return null;

      const year = dateObj.getFullYear();
      const month = String(dateObj.getMonth() + 1).padStart(2, '0');
      const day = String(dateObj.getDate()).padStart(2, '0');

      return `${year}-${month}-${day}`;
    },

    onNgayYcStartChange(args) {
      if (args.value) {
        // Ensure the date is treated as local timezone
        const localDate = new Date(args.value.getFullYear(), args.value.getMonth(), args.value.getDate());
        this.searchConditions.ngayYcStart = localDate;
      }
    },

    onNgayYcEndChange(args) {
      if (args.value) {
        // Ensure the date is treated as local timezone
        const localDate = new Date(args.value.getFullYear(), args.value.getMonth(), args.value.getDate());
        this.searchConditions.ngayYcEnd = localDate;
      }
    },
    async onSearch() {
      try {
        this.$root.showLoading(true)

        const searchParams = { ...this.searchConditions }
        searchParams.loaiCongViec = 'CAP_PHAT_RACK'
        const data = searchParams;

        const response = await this.$root.context.post('/web-ecms/idc/thi-cong/cap-phat-rack/ds-ketqua-capphat-rack', {
          loaiCvId: 57,
          donviId: data.donviId == 0 ? null : data.donviId,
          trangthaiId: data.trangthaiId == 0 ? null : data.trangthaiId,
          nhanvienThId: data.nhanvienThId == 0 ? null : data.nhanvienThId,
          ngayYcStart: this.formatDateForApi(data.ngayYcStart),
          ngayYcEnd: this.formatDateForApi(data.ngayYcEnd),
          ngayYchtStart: this.formatDateForApi(data.ngayYchtStart),
          ngayYchtEnd: this.formatDateForApi(data.ngayYchtEnd),
          phanLoai: data.phanLoai == 0 ? null : data.phanLoai,
          khachHang: data.khachHang ? data.khachHang.trim() : null,
          congViec: data.congViec ? data.congViec.trim() : null,
          idcId: data.idcId == 0 ? null : data.idcId,
          tenRack: data.tenRack ? data.tenRack.trim() : null,
          loaiRack: data.loaiRack ? data.loaiRack.trim() : null,
        })
        this.danhSachKetQua = response.data || []
      } catch (error) {
        console.error('Lỗi khi tìm kiếm kết quả cấp phát Rack:', error)
        this.$toast.error('Có lỗi xảy ra khi tải dữ liệu')
      } finally {
        this.$root.showLoading(false)
      }
    },

    createCapPhatRack() {
      this.$router.push({ path: `/tainguyen-idc/danhsach-congviec?loaicv=57` })
    },

    viewDetails(data) {
      this.$router.push({
        path: '/tainguyen-idc/capphat-rack-form',
        query: { phieutc: data.PHIEUTC_ID }
      })
    },

    actionColumn(parent) {
      return function () {
        return {
          template: {
            template: `<div class="action-buttons">
              <button class="btn btn-sm btn-outline-primary ml-1" title="Xem chi tiết" @click="viewDetail()">
                <i class="fa fa-eye"></i>
              </button>
            </div>`,
            methods: {
              viewDetail() {
                parent.viewDetails(this.data)
              }
            }
          }
        }
      }
    },
    khachHangTemplate(parent) {
      return function() {
        return {
          template: {
            template: `<div v-html="displayKhachHang"></div>`,
            computed: {
              displayKhachHang() {
                if (!this.data.TT_KHACHHANG || this.data.TT_KHACHHANG.trim() === '') {
                  return 'NỘI BỘ IDC'
                }
                return this.data.TT_KHACHHANG
              }
            }
          }
        }
      }
    },
    formatDateTimeTemplate(parent) {
      return function() {
        return {
          template: {
            template: `<span>{{ formatDateTimeValue }}</span>`,
            computed: {
              formatDateTimeValue() {
                const ngayYCHT = this.data.NGAY_YCHT
                return ngayYCHT ? moment(ngayYCHT).format(DATE_TIME_FORMAT) : ''
              }
            }
          }
        }
      }
    },
  }
}
</script>
<style scoped>

.info-row {
  display: flex;
  align-items: center;
}
.value {
  flex: 1;
}

.action-buttons {
  display: flex;
  gap: 5px;
}

.action-buttons .btn {
  padding: 2px 8px;
  font-size: 12px;
}

.action-buttons .btn i {
  font-size: 14px;
}

/* Custom styles for child grid */
:deep(.e-detailrow) {
  background-color: #f8f9fa !important;
}

:deep(.e-detailrow .e-grid) {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1) !important;
  border-radius: 4px !important;
  overflow: hidden !important;
}

:deep(.e-detailrow .e-gridheader) {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
}

:deep(.e-detailrow .e-gridheader .e-headercontent) {
  color: white !important;
}

:deep(.e-detailrow .e-gridheader .e-columnheader) {
  background: transparent !important;
  border-bottom: 1px solid rgba(255, 255, 255, 0.2) !important;
}

:deep(.e-detailrow .e-gridheader th) {
  color: white !important;
  font-weight: 500 !important;
}

:deep(.e-detailrow .e-grid .e-content) {
  background-color: white !important;
}

:deep(.e-detailrow .e-row:hover) {
  background-color: #e3f2fd !important;
}

/* Icon for expand/collapse */
:deep(.e-dtdiagonaldown:before) {
  content: '\e7a9' !important;
  color: #2196f3 !important;
}

:deep(.e-dtdiagonalright:before) {
  content: '\e7a8' !important;
  color: #2196f3 !important;
}

/* Custom header for child devices */
.child-device-header {
  padding: 10px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  font-weight: bold;
  border-radius: 4px 4px 0 0;
  margin-bottom: 0;
}
</style>
