<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import api from './api.js'
import { CONFIG } from './config.js'
import { DATE_TIME_FORMAT } from '@/constants'
import moment from 'moment'
import { PHANLOAI } from './const.js'

export default {
  name: 'QuanLyThiCongHeThongThietBi',
  props: {
    loaiCvId: {
      type: Number,
      default: 0
    }
  },
  components: {},
  data() {
    return {
      searchConditions: {
        loaicvId: 0,
        donviId: 0,
        khachhang: null,
        congviec: null,
        tenHeThong: null,
        ycBatdau: null,
        ycKetthuc: null,
        ychtBatdau: null,
        ychtKetthuc: null,
        nhanvienId: null,
        trangthaiId: null,
        phanloai: null,
        idcId: null
      },
      displayConditions: {
        loaicvId: 0
      },
      danhSachCongViec: [],
      loaiCongViecList: [],
      trangThaiList: [],
      nhanVienList: [],
      phanLoaiList: [],
      donViList: [],
      idcList: [],
      isLoaicvIdFromUrl: false
    }
  },
  computed: {
    selectedLoaiCongViecText() {
      if (this.displayConditions.loaicvId == 0 || !this.displayConditions.loaicvId) {
        return 'Thi công hệ thống thiết bị'
      }
      const selectedItem = this.loaiCongViecList.find((item) => item.value == this.displayConditions.loaicvId)
      return selectedItem ? selectedItem.text : 'Không xác định'
    }
  },
  created: async function() {
    await this.loadInitialData()
    await this.loadDanhMuc()
    // Load IDC list giống tiếp nhận thi công
    // const idcResponse = await api.getDanhMucLoaiCongViec(this.$root.context, {
    //   loaiDanhMuc: 'IDC-DS-IDC',
    //   thamSo1: this.$root.token.getDonViID()
    // })
    // // Handle nested response structure for IDC list
    // const idcData = idcResponse?.data?.data || idcResponse?.data || []
    // this.idcList = idcData.map((item) => ({ value: item.ID, text: item.TEN }))
  },
  methods: {
    async loadInitialData() {
      try {
        const loaicvIdFromUrl = this.loaiCvId
        const loaiCongViecResponse = await api.getDanhMucLoaiCongViec(this.$root.context, {
          loaiDanhMuc: 'IDC-LOAICV-THICONG-TBI'
        })
        const loaiCongViecData = loaiCongViecResponse?.data?.data || loaiCongViecResponse?.data || []
        this.loaiCongViecList = [
          { value: 0, text: '-- Tất cả --' },
          ...loaiCongViecData.map((item) => ({
            value: item.LOAICV_ID,
            text: item.TEN
          }))
        ]

        // Xử lý loaicv từ URL
        if (loaicvIdFromUrl) {
          const loaicvIdNumber = parseInt(loaicvIdFromUrl)
          const foundItem = this.loaiCongViecList.find((item) => item.value === loaicvIdNumber)
          if (foundItem) {
            this.searchConditions.loaicvId = loaicvIdNumber
            this.displayConditions.loaicvId = loaicvIdNumber
            this.isLoaicvIdFromUrl = true
          } else {
            this.searchConditions.loaicvId = 0
            this.displayConditions.loaicvId = 0
            this.isLoaicvIdFromUrl = false
          }
        } else {
          this.isLoaicvIdFromUrl = false
        }
        await this.onSearch()
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra khi tải danh mục')
      }
    },

    async loadDanhMuc() {
      try {
        // Load phân loại list
        this.phanLoaiList = [
          { ID: -1, TEN: '-- Tất cả --' },
          { ID: PHANLOAI.KHACHHANG, TEN: 'Khách hàng' },
          { ID: PHANLOAI.NOIBO, TEN: 'Nội bộ' }
        ]

        // Load danh mục đơn vị
        const donViResponse = await api.getDanhMucDonVi(this.$root.context, {})
        this.donViList = [
          { DONVI_ID: 0, TEN_DV: '-- Tất cả --' },
          ...(donViResponse.data || []).map((item) => ({
            DONVI_ID: item.DONVI_ID,
            TEN_DV: item.TEN_DV
          }))
        ]

        // Load danh mục trạng thái
        const trangThaiResponse = await api.getDanhMucTrangThaiThiCong(this.$root.context, { thamSo1: 'SEARCH-KETQUA' })
        this.trangThaiList = [{ ID: 0, TEN: '-- Tất cả --' }, ...(trangThaiResponse.data || [])]

        // Load IDC list cho user hiện tại (nếu cần)
        const idcByNhanVienResponse = await api.getDanhMucIDCByNhanVien(this.$root.context, {
          nhanvienId: this.$root.token.getNhanVienID()
        })
        this.idcList = [{ ID: 0, TEN: '-- Tất cả --' }, ...(idcByNhanVienResponse.data || [])]

        await this.loadNhanVienByDonVi()
      } catch (error) {
        console.error('Lỗi khi tải danh mục:', error)
        this.$toast.error('Có lỗi xảy ra khi tải danh mục')
      }
    },

    async loadNhanVienByDonVi() {
      try {
        const response = await api.getDanhMucNhanVien(this.$root.context, { donviId: this.$root.token.getDonViID() })
        this.nhanVienList = [{ NHANVIEN_ID: 0, TEN_NV: '-- Tất cả --' }, ...(response.data || [])]
      } catch (error) {
        console.error('Lỗi khi tải danh sách nhân viên:', error)
      }
    },
    async onSearch() {
      try {
        this.$root.showLoading(true)
        this.displayConditions.loaicvId = this.searchConditions.loaicvId

        const searchParams = { ...this.searchConditions }

        // Convert 0 values to null for proper API handling
        if (searchParams.loaicvId == 0) searchParams.loaicvId = null
        if (searchParams.donviId == 0) searchParams.donviId = null
        if (searchParams.idcId == 0) searchParams.idcId = null
        if (searchParams.nhanvienId == 0) searchParams.nhanvienId = null
        if (searchParams.trangthaiId == 0) searchParams.trangthaiId = null
        if (searchParams.phanloai == -1) searchParams.phanloai = null

        // Handle empty strings
        if (!searchParams.khachhang || searchParams.khachhang.trim() === '') {
          searchParams.khachhang = null
        }
        if (!searchParams.congviec || searchParams.congviec.trim() === '') {
          searchParams.congviec = null
        }
        if (!searchParams.tenHeThong || searchParams.tenHeThong.trim() === '') {
          searchParams.tenHeThong = null
        }

        // Set loại công việc theo props if not already set
        if (!searchParams.loaicvId && this.loaiCvId) {
          searchParams.loaicvId = this.loaiCvId
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

    createThiCongHeThong() {
      this.$router.push({ path: '/tainguyen-idc/danhsach-congviec?loaicv=' + this.loaiCvId })
    },

    formatDateLocal(date, defaultValue, inputFormat) {
      return formatDate(date, defaultValue, inputFormat)
    },
    formatDateTimeTemplate(parent) {
      return function() {
        return {
          template: {
            template: `<span>{{ formatDateTimeValue }}</span>`,
            computed: {
              formatDateTimeValue() {
                const ngayYCHT = this.data.NGAY_YCHT?.replace('.0', '')
                return ngayYCHT ? moment(ngayYCHT).format(DATE_TIME_FORMAT) : ''
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
                if (!this.data.KHACHHANG || this.data.KHACHHANG.trim() === '') {
                  return 'NỘI BỘ IDC'
                }
                return this.data.KHACHHANG
              }
            }
          }
        }
      }
    },

    actionColumn(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons gap-1">
              <button class="btn btn-sm btn-outline-primary" title="Xem thi công" @click="xemChiTiet()">
                  <i class="fa fa-eye"></i>
                </button>
                <button class="btn btn-sm btn-outline-primary" title="Chỉnh sửa" @click="xuLyItem()">
                  <i class="fa fa-edit"></i>
                </button>
              </div>`,
            computed: {},
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
        const loaiCongViecId = data.LOAICV_ID

        if (!loaiCongViecId) {
          this.$toast.error('Không xác định được loại công việc')
          return
        }

        // Lấy cấu hình cho loại công việc này
        const config = CONFIG[loaiCongViecId]

        if (!config) {
          this.$toast.error(`Chưa có cấu hình xử lý cho loại công việc ${loaiCongViecId}`)
          return
        }

        // Xử lý theo loại cấu hình
        if (config.type === 'navigate') {
          // Điều hướng đến route
          const routeParams = {}

          // Xử lý params từ config
          if (config.params) {
            Object.keys(config.params).forEach((key) => {
              const paramValue = config.params[key]
              if (typeof paramValue === 'string' && paramValue.startsWith('data.')) {
                // Lấy giá trị từ data object
                const fieldName = paramValue.replace('data.', '')
                routeParams[key] = data[fieldName]
              } else {
                routeParams[key] = paramValue
              }
            })
          }
          console.log(viewOnly)
          const queryParams = {
            ...routeParams,
            ...(!viewOnly ? { isEditMode: 'true' } : {})
          }
          this.$router.push({
            path: config.route,
            query: queryParams
          })
        } else if (config.type === 'modal') {
          // Mở modal
          this.openModal(config, data)
        }
      } catch (error) {
        console.error('Lỗi khi xử lý công việc:', error)
        this.$toast.error('Có lỗi xảy ra khi xử lý công việc')
      }
    }
  }
}
</script>
