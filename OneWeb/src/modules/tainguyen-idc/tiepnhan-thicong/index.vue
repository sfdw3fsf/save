<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import API from './api.js'
import { CONFIG } from '../danhsach-congviec/config.js'
import ChiTietCongViec from './popups/ChiTiet.vue'
import DateTimeLib from '../../../utils/DateTimeLib.js'
import SearchPhieuKenhBan from './popups/SearchPhieuKenhBan.vue'
export default {
  name: 'DanhSachCongViec',
  components: {
    ChiTietCongViec,
    SearchPhieuKenhBan
  },
  data() {
    return {
      searchConditions: {
        loaicvId: null,
        donviId: null,
        idcId: null,
        nhanvienId: null,
        phanloai: null, // Khách hàng: 1; Nội bộ: 2
        ycBatdau: null,
        ycKetthuc: null,
        ychtBatdau: null,
        ychtKetthuc: null,
        khachhang: null, // ma_tb, ma_gd, so_hd, ten_tb
        congviec: null, // ma_congviec, ten_congviec, noidung
        trangthaiId: null
      },
      childGrid: {
        dataSource: [],
        queryString: 'PHIEUTC_CHA_ID',
        columns: [
          { field: 'MA_CV', headerText: 'Mã CV', textAlign: 'Left', width: 130 },
          { field: 'PHIEUTC_ID', headerText: 'Phiếu YC', textAlign: 'Left', width: 100 },
          { field: 'LOAI_PHIEU_TC', headerText: 'Loại phiếu', textAlign: 'Left', width: 120 },
          { field: 'TEN_CV', headerText: 'Hạng mục triển khai', textAlign: 'Left', width: 180 },
          { field: 'LOAICV', headerText: 'Loại công việc', textAlign: 'Left', width: 160 },
          { field: 'TEN_DV', headerText: 'Đơn vị thực hiện', textAlign: 'Left' },
          { field: 'TEN_NV', headerText: 'Cá nhân thực hiện', textAlign: 'Left', width: 180 },
          {
            field: 'DS_NV_PHOIHOP',
            headerText: 'Phối hợp thực hiện',
            textAlign: 'Left',
            width: 220,
            cssClass: 'phoi-hop-thuc-hien',
            disableHtmlEncode: false
          },
          { field: 'NGAY_TH', headerText: 'Thời hạn thực hiện', textAlign: 'Center', width: 160 },
          { field: 'TRANGTHAI', headerText: 'Trạng thái thực hiện', textAlign: 'Left', width: 170 },
          {
            field: 'ACTIONS',
            headerText: 'Thao tác',
            allowFiltering: false,
            textAlign: 'Center',
            template: this.actionColumn(this),
            width: 150
          }
        ]
      },
      showGrid: true,
      danhSachPhieuThiCong: [],
      dsLoaiCongViecOptions: [],
      dsDonViOptions: [],
      dsNhanVienOptions: [],
      dsTrangThaiOptions: [],
      dsIdcOptions: [],
      dsPhanLoaiOptions: [
        { ID: 0, TEN: '-- Tất cả --' },
        { ID: 1, TEN: 'Khách hàng' },
        { ID: 2, TEN: 'Nội bộ' }
      ],
      currentItem: {
        PHIEUTC_ID: null,
        PHIEUTC_CHA_ID: null,
        MA_CV_CHA: null,
        LOAICV_ID: null,
        LOAICV: null,
        DONVI_ID: null,
        TEN_DV: null,
        MATSAN_ID: null,
        MATSAN: null,
        MA_CV: null,
        TEN_CV: null,
        CANCU_YC: null,
        NOIDUNG: null,
        GIATRI_KHAIBAO: null,
        TRANGTHAI_ID: null,
        TRANGTHAI: null,
        NHANVIEN_TH_ID: null,
        TEN_NV: null,
        NGAY_YC: null,
        NGAY_YCHT: null,
        NGAY_HT: null,
        NGAY_TAO: null,
        NHANVIEN_TAO_ID: null,
        MOTA: null,
        NGUOI_CN: null,
        NGAY_CN: null,
        PHIEUKB_ID: null,
        PHANVUNG_ID: null,
        CONGVIEC_ID: null,
        LOAIPH_ID: null,
        LOAIPH: null,
        TINH_ID: null,
        TENTINH: null,
        HDTB_ID: null,
        THUEBAO_ID: null,
        MA_TB: null,
        MA_GD: null,
        TEN_KH: null,
        SO_HD: null,
        NGAY_CN_KB: null,
        GOICUOC_ID: null,
        GOICUOC: null,
        TRONGNUOC: null,
        QUOCTE: null,
        LOAI_PHIEU_TC: null,
        DS_NV_PHOIHOP: null,
        DV_GTGT: null
      },
      loaiCongViecDialog: 0
    }
  },
  computed: {},
  async mounted() {
    await this.loadDanhMuc()
    // Gọi API search lần đầu khi load trang
    await this.onSearch()
  },
  methods: {
    // API
    afterSaveChiTietCongViec(savedData) {
      // Kiểm tra auto redirect sau khi tạo CV nội bộ
      if (savedData && savedData.isNewRecord && savedData.loaiCongViecId) {
        this.handleAutoRedirectAfterCreate(savedData)
      }
      this.onSearch()
    },
    async apiGetDanhMucTaiNguyenIDC(params) {
      this.loading(true)
      let dsResult = []
      try {
        const response = await API.getDanhMucTaiNguyenIDC(this.axios, params)
        if (response.data.error_code === 'BSS-00000000') {
          dsResult = response.data.data
        } else {
          dsResult = []
        }
      } catch (error) {
        console.log('apiGetDSGiamTruCuoc error: ', error)
        dsResult = []
      } finally {
        this.loading(false)
      }
      return dsResult
    },
    async apiGetDSPhieuThiCong(params) {
      this.loading(true)
      let dsResult = []
      try {
        const response = await API.getDSPhieuThiCong(this.axios, params)
        if (response.data.error_code === 'BSS-00000000') {
          dsResult = response.data.data
        } else {
          dsResult = []
        }
      } catch (error) {
        console.log('apiGetDSGiamTruCuoc error: ', error)
        dsResult = []
      } finally {
        this.loading(false)
      }
      return dsResult
    },
    async apiChuyenXuLyPhieuThiCong(params) { },
    async loadDanhMuc() {
      try {
        // Load danh mục loại công việc
        const loaiCongViecResponse = await this.apiGetDanhMucTaiNguyenIDC({ loaiDanhMuc: 'IDC-LOAICV' })
        this.dsLoaiCongViecOptions = [{ LOAICV_ID: 0, TEN: '-- Tất cả --' }, ...loaiCongViecResponse]
        // Load danh mục đơn vị mà đã có trong thi công
        const donViResponse = await this.apiGetDanhMucTaiNguyenIDC({ loaiDanhMuc: 'IDC_LOAI_DV_IDC' })
        this.dsDonViOptions = [{ ID: 0, TEN: '-- Tất cả --' }, ...donViResponse]
        // Load danh mục trạng thái
        const trangThaiResponse = await this.apiGetDanhMucTaiNguyenIDC({ loaiDanhMuc: 'IDC-TRANGTHAI-THICONG' })
        this.dsTrangThaiOptions = [{ ID: 0, TEN: '-- Tất cả --' }, ...trangThaiResponse]
      } catch (error) {
        console.error('Lỗi khi tải danh mục:', error)
        this.$toast.error('Có lỗi xảy ra khi tải danh mục')
      }
    },
    async onSearch() {
      try {
        this.showGrid = false
        const searchParams = {
          loaicvId: this.searchConditions.loaicvId == 0 ? null : this.searchConditions.loaicvId,
          donviId: this.searchConditions.donviId == 0 ? null : this.searchConditions.donviId,
          idcId: this.searchConditions.idcId == 0 ? null : this.searchConditions.idcId,
          nhanvienId: this.searchConditions.nhanvienId || null,
          phanloai: this.searchConditions.phanloai == 0 ? null : this.searchConditions.phanloai,
          ycBatdau: this.searchConditions.ycBatdau || null,
          ycKetthuc: this.searchConditions.ycKetthuc || null,
          ychtBatdau: this.searchConditions.ychtBatdau || null,
          ychtKetthuc: this.searchConditions.ychtKetthuc || null,
          khachhang:
            this.searchConditions.khachhang && this.searchConditions.khachhang.trim() !== ''
              ? this.searchConditions.khachhang.trim()
              : null,
          congviec:
            this.searchConditions.congviec && this.searchConditions.congviec.trim() !== ''
              ? this.searchConditions.congviec.trim()
              : null,
          trangthaiId: this.searchConditions.trangthaiId == 0 ? null : this.searchConditions.trangthaiId
        }
        let allData = await this.apiGetDSPhieuThiCong(searchParams)
        this.danhSachPhieuThiCong = []
        this.childGrid.dataSource = []
        allData.forEach((item, index) => {
          if (item.PHIEUTC_CHA_ID === null || item.PHIEUTC_CHA_ID === undefined) {
            item.PHIEUTC_CHA_ID = item.PHIEUTC_ID
            this.danhSachPhieuThiCong.push(item)
          } else {
            item.PHIEUTC_CHA_ID = item.PHIEUTC_CHA_ID
            this.childGrid.dataSource.push(item)
          }
        })
      } catch (error) {
        console.error('Lỗi khi tải danh sách phiếu thi công:', error)
        this.$toast.error('Có lỗi xảy ra khi tải danh sách phiếu thi công')
      } finally {
        this.showGrid = true
      }
    },
    onCreateNoiBo() {
      console.log('onCreateNoiBo')
      this.$refs.refChiTietCongViec.pLoaiPhieu = 0 //NỘI BỘ
      this.$refs.refChiTietCongViec.pPhieuThiCongId = null
      this.$refs.refChiTietCongViec.onDialogOpen()
    },
    onCreateKenhBan() {
      // this.$refs.refChiTietCongViec.pLoaiPhieu = 1  //KÊNH BÁN
      // this.$refs.refChiTietCongViec.pPhieuThiCongId = null
      // this.$refs.refChiTietCongViec.onDialogOpen()

      //
      this.$refs.refSearchPhieuKenhBan.show()
    },
    onDetailDataBound(args) {
      const detailGrid = args.detailElement.querySelector('.e-grid')
      if (detailGrid) {
        detailGrid.style.border = '1px solid #e0e0e0'
        detailGrid.style.borderRadius = '4px'
        detailGrid.style.margin = '10px'
        detailGrid.style.backgroundColor = '#fafafa'
      }

      const parentData = args.data
      if (parentData) {
        const childDevicesForThisParent = this.childGrid.dataSource.filter(
          (child) => child.PHIEUTC_CHA_ID == parentData.PHIEUTC_ID
        )
        const childCount = childDevicesForThisParent.length

        if (childCount > 0) {
          const headerElement = args.detailElement.querySelector('.e-gridheader')

          if (headerElement) {
            const customHeader = document.createElement('div')
            customHeader.innerHTML = `
                    <div style="padding: 10px; background-color: #f5f5f5; border-bottom: 1px solid #ddd; font-weight: bold; color: #333;">
                      <i class="fa fa-cogs" style="margin-right: 5px;"></i>
                      Phiếu thi công con của: ${parentData.TEN_CV} (${childCount} phiếu)
                    </div>
                  `
            args.detailElement.insertBefore(customHeader, args.detailElement.firstChild)
          }
        }
      }
    },
    onChangePhieuCV(data) {
      this.currentItem = data
    },
    khachHangTemplate(parent) {
      return function () {
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
      return function () {
        return {
          template: {
            template: `<div class="action-buttons gap-1">
                <button class="btn btn-sm btn-outline-success" title="Chi tiết" @click="viewDetail()">
                  <span class="icon nc-icon-outline ui-1_eye-17"></span>
                </button>
                <button :disabled="!canExecuteWork" class="btn btn-sm btn-outline-primary" title="Chỉnh sửa" @click="executeWork()">
                  <span class="icon nc-icon-outline one-edit"></span>
                </button>
                 <button :disabled="!canExecuteWork" class="btn btn-sm btn-outline-danger" title="Xoá" @click="deletePhieu()">
                  <span class="icon one-trash"></span>
                </button>
              </div>`,
            computed: {
              canProcess() {
                return true
              },
              canExecuteWork() {
                const currentUserId = parent.$root.token.getNhanVienID()
                return (
                  this.data.NHANVIEN_TAO_ID &&
                  this.data.NHANVIEN_TAO_ID == currentUserId &&
                  this.data.TRANGTHAI_THICONG_ID &&
                  this.data.TRANGTHAI_THICONG_ID == 21
                )
              }
            },
            methods: {
              async viewDetail() {
                await parent.xemChiTietPhieuCongViec(this.data)
              },
              async deletePhieu() {
                await parent.xoaPhieuThiCong(this.data.PHIEU_YC)
              },
              async executeWork() {
                await parent.chuyenSangThucHienCongViec(this.data)
              }
            }
          }
        }
      }
    },
    async xemChiTietPhieuCongViec(data) {
      const loaiCongViecId = data.LOAICV_ID
      if (!loaiCongViecId) {
        this.$toast.error('Không xác định được loại công việc')
        return
      }
      // Lấy cấu hình cho loại công việc này
      const config = CONFIG[loaiCongViecId]
      if (!config) {
        this.$toast.error(`Chưa có cấu hình xử lý cho loại công việc này`)
        return
      }
      const routeParams = {}
      if (config.params) {
        Object.keys(config.params).forEach((key) => {
          const paramValue = config.params[key]
          if (typeof paramValue === 'string' && paramValue.startsWith('data.')) {
            // Lấy giá trị từ data object
            const fieldName = paramValue.replace('data.', '')
            routeParams[key] = data[fieldName]
            if (key == 'phieutc') {
              routeParams[key] = data.PHIEU_YC
            }
          } else {
            routeParams[key] = paramValue
          }
        })
      }

      this.$router.push({
        path: config.route,
        query: routeParams
      })
    },
    async xoaPhieuThiCong(phieuId) {
      this.$confirm('Bạn có chắc chắn muốn xoá phiếu thi công này không?', 'Xác nhận', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy',
        type: 'warning'
      })
        .then(async () => {
          try {
            let rs = await API.deleteCongViec(this.axios, { congViecId: phieuId })
            if (rs.data.data.result == 1 ) {
              this.$toast.success('Xoá phiếu thi công thành công')
              this.onSearch()
            } else if (rs.data.data.result == -2) {
              this.$toast.error('Phiếu thi công đã được thực hiện, không thể xoá')
            } else if (rs.data.data.result == -1) {
              this.$toast.error('Phiếu thi công không tồn tại hoặc đã bị xoá trước đó')
            } else {
              this.$toast.error('Có lỗi xảy ra, vui lòng thử lại')
            }
          } catch (error) {
            console.error('Lỗi khi xoá phiếu thi công:', error)
            this.$toast.error('Có lỗi xảy ra khi xoá phiếu thi công')
          }
        })
        .catch(() => { })
    },
    onBeforeCloseChiTietCongViec() { },
    async chuyenSangThucHienCongViec(data) {
      console.log('chuyenSangThucHienCongViec', data)

      // Kiểm tra quyền thực hiện
      const currentUserId = this.$root.token.getNhanVienID()
      if (!data.NHANVIEN_TAO_ID || data.NHANVIEN_TAO_ID != currentUserId) {
        this.$toast.error('Bạn không có quyền thực hiện công việc này')
        return
      }

      if (data.TRANGTHAI_THICONG_ID != 21) {
        this.$toast.error('Phiếu thi công không được thực hiện vì trạng thái không phải là mới')
        return
      }

      const loaiCongViecId = data.LOAICV_ID
      if (!loaiCongViecId) {
        this.$toast.error('Không xác định được loại công việc')
        return
      }

      // Lấy cấu hình cho loại công việc này
      const config = CONFIG[loaiCongViecId]
      if (!config) {
        this.$toast.error(`Chưa có cấu hình xử lý cho loại công việc này`)
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
              if (key == 'phieutc') {
                routeParams[key] = data.PHIEU_YC
              }
            } else {
              routeParams[key] = paramValue
            }
          })
        }

        // config edit mode
        if (config.paramEdit) {
          const { name, value } = config.paramEdit
          routeParams[name] = value
        }

        console.log('Chuyển sang thực hiện công việc với params:', routeParams)
        this.$router.push({
          path: config.route,
          query: routeParams
        })
      } else if (config.type === 'modal') {
        // Mở modal
        this.openModal(config, data)
      } else {
        this.$toast.info('Chức năng đang được phát triển')
      }
    },

    handleAutoRedirectAfterCreate(savedData) {
      const loaiCongViecId = savedData.loaiCongViecId
      const phieuYCId = savedData.phieuYCId

      // Lấy cấu hình cho loại công việc này
      const config = CONFIG[loaiCongViecId]

      if (!config || !config.autoRedirectAfterCreate) {
        // Nếu không có cấu hình auto redirect, chỉ refresh danh sách
        this.onSearch()
        return
      }

      // Đóng dialog chi tiết công việc (chỉ khi tạo từ nội bộ)
      if (!savedData.fromKenhBan && this.$refs.refChiTietCongViec) {
        this.$refs.refChiTietCongViec.onDialogClosePopup()
      }
      // Hiển thị thông báo và redirect
      const message = savedData.fromKenhBan
        ? 'Tạo phiếu thi công kênh bán thành công! Đang chuyển sang cấu hình chi tiết...'
        : 'Tạo phiếu thi công thành công! Đang chuyển sang cấu hình chi tiết...'
      this.$toast.success(message, { timeout: 2000 })

      setTimeout(() => {
        if (config.type === 'navigate') {
          const routeParams = {}

          // Xử lý params từ config
          if (config.params) {
            Object.keys(config.params).forEach((key) => {
              const paramValue = config.params[key]
              if (paramValue === 'data.PHIEU_YC') {
                routeParams[key] = phieuYCId
              } else if (typeof paramValue === 'string' && paramValue.startsWith('data.')) {
                // Lấy giá trị từ savedData object nếu có
                const fieldName = paramValue.replace('data.', '')
                routeParams[key] = savedData[fieldName] || paramValue
              } else {
                routeParams[key] = paramValue
              }
            })
          }
          // config edit mode
          if (config.paramEdit) {
            const { name, value } = config.paramEdit
            routeParams[name] = value
          }
          // Redirect đến trang thi công
          this.$router.push({
            path: config.route,
            query: routeParams
          })
        }
      }, 300) // Delay 1.5s để user đọc được thông báo
    },
    async onChangeDonViThucHien(value) {
      this.searchConditions.nhanvienId = null
      this.dsNhanVienOptions = await this.apiGetDanhMucTaiNguyenIDC({
        loaiDanhMuc: 'NHANVIEN-DONVI-IDC',
        thamSo1: value
      })

      const idcResponse = await this.apiGetDanhMucTaiNguyenIDC({
        loaiDanhMuc: 'DANHMUC_IDC',
        thamSo1: value
      })
      // Load danh mục IDC
      this.dsIdcOptions = [{ ID: 0, TEN: '-- Tất cả --' }, ...idcResponse]
    },
    async onBeforeClosePopupSearchPhieuSO(data) {
      console.log('onBeforeClosePopupSearchPhieuSO', data)
      this.$confirm('Bạn có muốn tạo phiếu thi công từ phiếu SO đã chọn không?', 'Xác nhận', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy',
        type: 'warning'
      })
        .then(async () => {
          try {
            let rs = await API.createCongViecKenhBan(this.axios, {
              congViecId: data.CONGVIEC_ID,
              phanLoai: data.PHANLOAI // 1: Kênh bán, 0: Nội bộ
            })
            if (rs.data.error_code === 'BSS-00000000') {
              const savedData = {
                isNewRecord: true,
                loaiCongViecId: data.LOAICV_ID,
                phieuYCId: rs.data.data.result,
                fromKenhBan: true
              }
              this.handleAutoRedirectAfterCreate(savedData)
            } else {
              this.$toast.error(rs.data.message || 'Vui lòng kiểm tra lại dữ liệu giao việc!')
            }
          } catch (error) {
            console.error('Lỗi khi tạo phiếu thi công kênh bán:', error)
            this.$toast.error('Vui lòng kiểm tra lại dữ liệu giao việc!')
          }
        })
        .catch(() => { })
    },

  }
}
</script>
