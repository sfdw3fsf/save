<template src="./index.template.html"> </template>
<script>
import Vue from 'vue'
import moment from 'moment'
import ModalChonHeThong from './modal-chon-hethong/index.vue'
import ModalChonThietBi from './modal-chon-thietbi/index.vue'
import ModalChiTietThietBiIDCKhachHang from './chitiet-thietbi-idc-khachhang/index.vue'
import ModalChiTietHeThongIDCKhachHang from './chitiet-hethong-idc-khachhang/index.vue'
import ThongTinCongViec from '../components/ThongTinCongViec/index.vue'
import ModalChuyenXuly from './ModalChuyenXuLy/index.vue'
import ModalTraPhieu from './ModalTraPhieu/index.vue'
import api from './api'
import EventBus from '@/utils/eventBus'
import { createButtonConfig, createDataGridConfig } from './config'
import { TRANG_THAI_PHIEU, LOAI_CONG_VIEC } from './const'

export default {
  name: 'TacNghiepHeThongThietBiKH',
  components: {
    ModalChonHeThong,
    ModalChonThietBi,
    ModalChiTietThietBiIDCKhachHang,
    ModalChiTietHeThongIDCKhachHang,
    ThongTinCongViec,
    ModalChuyenXuly,
    ModalTraPhieu
  },
  data() {
    return {
      idcSelectedEmit: null,
      tacNghiepHeThongId: null,
      isViewOnly: false,
      dsThietBiKhachHang: [],
      listThiCongHeThongThietBiKhachHang: [],
      heThongThietbiIdSelected: null,
      thietBiKhachHangSelected: false,
      thiCongHeThongSelected: null,
      phieutc: null,
      phieuThiCongDetail: {
        MA_CV: null,
        TEN_DV: null,
        TEN_NV: null,
        LOAI_CV: null,
        DANHSACH_NV_PHOIHOP: null,
        TEN_CV: null,
        NGAY_YC: null,
        CANCU_YC: null,
        TRANGTHAI_ID: null,
        MA_CV_CHA: null,
        NGAY_HT: null,
        LOAICV_ID: null,
        IDC_ID: null
      }
    }
  },

  computed: {
    /**
     * Configuration cho toàn bộ buttons và permissions
     * Sử dụng config từ file riêng để dễ quản lý và tái sử dụng
     */
    buttonConfig() {
      const buttonConfig = createButtonConfig({
        currentUserId: this.$root.token.getNhanVienID(),
        isThucHien: this.phieuThiCongDetail.NHANVIEN_TH_ID == this.$root.token.getNhanVienID(),
        isNguoiTao: this.phieuThiCongDetail.NHANVIEN_TAO_ID == this.$root.token.getNhanVienID(),
        isViewOnly: this.isViewOnly,
        trangThaiId: this.phieuThiCongDetail.TRANGTHAI_ID,
        loaiCvId: this.phieuThiCongDetail.LOAICV_ID,
        isKhachHang: this.isKhachHang,
        thiCongHeThongSelected: this.thiCongHeThongSelected,
        thietBiKhachHangSelected: this.thietBiKhachHangSelected
      })
      return buttonConfig
    },

    /**
     * Configuration cho DataGrid columns và điều kiện hiển thị
     * Sử dụng config từ file riêng để dễ quản lý và tái sử dụng
     */
    dataGridConfig() {
      const dataGridConfig = createDataGridConfig({
        trangThaiId: this.phieuThiCongDetail.TRANGTHAI_ID,
        loaiCvId: this.phieuThiCongDetail.LOAICV_ID,
        isKhachHang: this.isKhachHang,
        isViewOnly: this.isViewOnly,
        componentContext: this,
        anCotThaoTac: this.AnCotThaoTac
      })
      return dataGridConfig
    },
    // Hiển thị tên cột hệ thống theo khách hàng hay IDC
    heThongColumnsResolved() {
      return this.isKhachHang ? this.dataGridConfig.heThongColumns.khachHang : this.dataGridConfig.heThongColumns.idc
    },

    // Hiển thị gộp DataGrid hệ thống
    shouldShowHeThongGrid() {
      const showGrid = this.dataGridConfig.shouldShowGrid
      return (
        showGrid.heThongKH_LapDat ||
        showGrid.heThongKH_ThaoDo ||
        showGrid.heThongKH_ThayDoi ||
        showGrid.heThongKH_View ||
        showGrid.heThongIDC_LapDat ||
        showGrid.heThongIDC_ThaoDo ||
        showGrid.heThongIDC_ThayDoi ||
        showGrid.heThongIDC_View
      )
    },

    // Hiển thị gộp DataGrid thiết bị
    shouldShowThietBiGrid() {
      const showGrid = this.dataGridConfig.shouldShowGrid
      return (
        showGrid.thietBiKH_LapDat ||
        showGrid.thietBiIDC_LapDat ||
        showGrid.thietBiKH_ThaoDo ||
        showGrid.thietBiIDC_ThaoDo ||
        showGrid.thietBiKH_ThayDoi ||
        showGrid.thietBiIDC_ThayDoi ||
        showGrid.thietBi_View
      )
    },

    AnCotThaoTac: function() {
      let rs = false
      // trạng thái là tiếp nhận hoặc nhiệm vụ mới
      if (this.phieuThiCongDetail != null) {
        rs =
          this.phieuThiCongDetail.TRANGTHAI_ID == TRANG_THAI_PHIEU.TIEP_NHAN ||
          this.phieuThiCongDetail.TRANGTHAI_ID == TRANG_THAI_PHIEU.NHIEM_VU_MOI
      }
      // chỉ cho phép người thực hiện thao tác, ngươi tạo ko được phép (view vẫn được)
      if (this.phieuThiCongDetail.NHANVIEN_TH_ID != this.$root.token.getNhanVienID()) {
        rs = true
      }
      return rs
    },
    grdNhiemVu: function() {
      return this.$refs.grdNhiemVu
    },
    isKhachHang() {
      return (
        this.phieuThiCongDetail.HDTB_ID != null &&
        this.phieuThiCongDetail.HDTB_ID > 0 &&
        this.phieuThiCongDetail.TINH_ID != null &&
        this.phieuThiCongDetail.TINH_ID > 0
      )
    }
  },

  watch: {
    heThongThietbiIdSelected: function(newVal) {
      if (!newVal) {
        return
      }
    },
    thietBiKhachHangSelected: function(newVal) {
      if (newVal) {
        this.thongBaoDuGiaTriKhaiBao()
      }
    }
  },

  created: async function() {
    try {
      this.$root.showLoading(true)
      const phieutc = this.$route.query.phieutc
      if (phieutc) {
        this.phieutc = Number(phieutc)
        await this.loadPhieuThiCongDetail()
        await this.getPhieuThiCongHeThongKhachHang()
      } else {
        const id = this.$route.query.id
        if (id) {
          this.tacNghiepHeThongId = Number(id)
          let rs = await api.getDetailFromIdThiCong(this.axios, this.tacNghiepHeThongId)
          if (rs) {
            this.phieutc = rs.PHIEUTC_ID
            await this.loadPhieuThiCongDetail()
            await this.getPhieuThiCongHeThongKhachHang()
          }
        }
      }
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    onChuyenThucHien() {
      if (
        !this.listThiCongHeThongThietBiKhachHang ||
        this.listThiCongHeThongThietBiKhachHang.length == 0 ||
        !this.dsThietBiKhachHang ||
        this.dsThietBiKhachHang.length == 0
      ) {
        this.$root.toastError('Cần chọn hoặc thêm mới thiết bị lắp đặt để chuyển người thực hiện')
        return
      }
      this.$confirm('Xác nhận chuyển phiếu thi công sang trạng thái Nhiệm vụ mới cho người thực hiện ?', {
        confirmButtonText: 'Xác nhận',
        cancelButtonText: 'Huỷ',
        type: 'warning'
      }).then(async () => {
        let rs = await this.$refs.thongTinCongViec.chuyenThucHien()
        if (rs) {
          await this.loadPhieuThiCongDetail()
        }
      })
    },
    onXacNhanThucHien() {
      this.$confirm('Xác nhận đã thực hiện phiếu thi công này?', {
        confirmButtonText: 'Xác nhận',
        cancelButtonText: 'Huỷ',
        type: 'warning'
      }).then(async () => {
        let rs = await this.$refs.thongTinCongViec.xacNhanThucHien()
        if (rs) {
          await this.loadPhieuThiCongDetail()
        }
      })
    },
    async loadPhieuThiCongDetail() {
      try {
        this.phieuThiCongDetail = await api.getChiThietPhieuThiCong(this.axios, this.phieutc)
        this.isViewOnly =
          !(
            this.phieuThiCongDetail.TRANGTHAI_ID == TRANG_THAI_PHIEU.TIEP_NHAN ||
            this.phieuThiCongDetail.TRANGTHAI_ID == TRANG_THAI_PHIEU.NHIEM_VU_MOI ||
            this.phieuThiCongDetail.TRANGTHAI_ID == TRANG_THAI_PHIEU.DANG_THUC_HIEN
          ) ||
          this.$route.query.isEditMode !== 'true' ||
          (this.phieuThiCongDetail.TRANGTHAI_ID == TRANG_THAI_PHIEU.TIEP_NHAN
            ? this.phieuThiCongDetail.NHANVIEN_TAO_ID != this.$root.token.getNhanVienID()
            : false)
      } catch (error) {
        this.$root.toastError(error?.message_detail || 'Có lỗi xảy ra khi lấy thông tin phiếu thi công')
      }
    },
    onSave: async function() {
      let rs = await this.$refs.thongTinCongViec.onSave()
      if (rs) {
        await this.loadPhieuThiCongDetail()
      }
    },
    onChuyenXuLy: async function() {
      try {
        if (this.phieuThiCongDetail.NHANVIEN_TH_ID != this.$root.token.getNhanVienID()) {
          this.$root.toastError(
            'Bạn không phải là người được giao thực hiện phiếu thi công này nên không thể chuyển xử lý!'
          )
          return
        }
        this.$refs.ModalChuyenXuly.show(this.phieuThiCongDetail, 'ADD', this.phieutc)
      } catch (error) {
        console.log(error)
        this.$root.toastError(error?.message_detail || 'Có lỗi xảy ra khi chuyển phiếu thi công')
      } finally {
        this.$root.showLoading(false)
      }
    },
    chuyenXylySuccess() {
      this.loadPhieuThiCongDetail()
      this.$refs.thongTinCongViec.loadPhieuThiCongDetail()
    },
    traPhieuSuccess() {
      switch (this.phieuThiCongDetail.LOAICV_ID) {
        case 50:
          this.$router.push({ path: '/tainguyen-idc/ketqua-thicong-lapdat' })
          break
        case 23:
          this.$router.push({ path: '/tainguyen-idc/ketqua-thicong-thaodo' })
          break
        case 52:
          this.$router.push({ path: '/tainguyen-idc/ketqua-thicong-thaydoi' })
          break
      }
    },
    onTraPhieu: async function() {
      if (this.phieuThiCongDetail.NHANVIEN_TH_ID != this.$root.token.getNhanVienID()) {
        this.$root.toastError('Bạn không phải là người được giao thực hiện phiếu thi công này nên không thể trả phiếu!')
        return
      }
      this.$refs.ModalTraPhieu.show(this.phieutc)
    },
    onHoanThanh: async function() {
      if (this.phieuThiCongDetail.NHANVIEN_TH_ID != this.$root.token.getNhanVienID()) {
        this.$root.toastError(
          'Bạn không phải là người được giao thực hiện phiếu thi công này nên không thể hoàn thành!'
        )
        return
      }
      this.$confirm('Xác nhận hoàn thành phiếu thi công?', {
        confirmButtonText: 'Xác nhận',
        cancelButtonText: 'Huỷ',
        type: 'warning'
      }).then(async () => {
        try {
          this.$root.showLoading(true)
          let rs = await api.hoanThanhPhieuThiCong(this.axios, {
            phieuThiCongId: this.phieutc,
            trangThaiId: TRANG_THAI_PHIEU.HOAN_THANH,
            nhanVienId: Number(this.$root.token.getNhanVienID())
          })
          if (rs && rs.error_code === 'BSS-00000000') {
            this.$root.toastSuccess('Hoàn thành phiếu thi công thành công!')
            switch (this.phieuThiCongDetail.LOAICV_ID) {
              case LOAI_CONG_VIEC.LAP_DAT:
                this.$router.push({ path: '/tainguyen-idc/ketqua-thicong-lapdat' })
                break
              case LOAI_CONG_VIEC.THAO_DO:
                this.$router.push({ path: '/tainguyen-idc/ketqua-thicong-thaodo' })
                break
              case LOAI_CONG_VIEC.THAY_DOI:
                this.$router.push({ path: '/tainguyen-idc/ketqua-thicong-thaydoi' })
                break
            }
          } else {
            this.$root.toastError(rs?.message_detail || 'Có lỗi xảy ra khi hoàn thành phiếu thi công')
          }
        } catch (error) {
          this.$root.toastError(error?.message || 'Có lỗi xảy ra khi hoàn thành phiếu thi công')
        } finally {
          this.$root.showLoading(false)
        }
      })
    },
    async getPhieuThiCongHeThongKhachHang() {
      const response = await api.getDanhSachThiCongHeThongKhachHang(this.axios, {
        phieuThiCongId: this.phieutc
      })
      this.listThiCongHeThongThietBiKhachHang = response.filter(item => item.HETHONGTBI_ID != null)
      this.thiCongHeThongSelected = this.listThiCongHeThongThietBiKhachHang[0] || null
    },
    async themThiCongHeThongKhachHang() {
      try {
        let rs = await api.themThiCongHeThongKhachHang(this.axios, {
          phieuThiCongId: this.phieutc,
          heThongTbiId: this.heThongThietbiIdSelected
        })
      } catch (error) {
        this.handleError(error)
      }
      await this.getPhieuThiCongHeThongKhachHang()
      await this.getPhieuThiCongTbiKhachHang()
    },
    async chonHeThongThietBiKH(data) {
      this.heThongThietbiIdSelected = Number(data?.HETHONGTBI_ID || 0)
      await this.themThiCongHeThongKhachHang()
    },
    onChonHeThongThietBiKH() {
      this.$refs.ModalChonHeThong.show('ADD', null, this.phieuThiCongDetail)
    },
    onChonHeThongThietBiKHChoThaoDo() {
      this.$refs.ModalChonHeThong.show('ADD', null, this.phieuThiCongDetail, 'THAO_DO')
    },
    onChonHeThongThietBiKHChoLapDat() {
      this.$refs.ModalChonHeThong.show('ADD', null, this.phieuThiCongDetail, 'LAPDAT')
    },
    onChonHeThongThietBiKHChoThayDoi() {
      this.$refs.ModalChonHeThong.show('ADD', null, this.phieuThiCongDetail, 'THAY_DOI_LAP_DAT')
    },
    onThemMoiHeThongSuccess(heThongId) {
      //
    },
    onAddHeThongThietBiKH() {
      this.$refs.ModalChiTietHeThongIDCKhachHang.show(null, 'ADD', this.phieuThiCongDetail)
    },
    onAddThietBiKH() {
      this.$refs.ModalChiTietThietBiIDCKhachHang.show(null, 'ADD', null)
    },
    onRefreshDataHeThongThietBiKhachHang() {
      this.getPhieuThiCongHeThongKhachHang()
    },
    onSelectThiCongHeThongThietBiKhachHang(event) {
      this.thiCongHeThongSelected = event || null
      this.heThongThietbiIdSelected = this.thiCongHeThongSelected?.HETHONGTBI_ID || null
      this.getPhieuThiCongTbiKhachHang()
    },
    loadDsThietBiKhachHang() {
      this.getPhieuThiCongTbiKhachHang()
    },
    async onThemMoiThietBiSuccess(data) {
      let rs = await api.themThiCongThietBiKhachHang(this.axios, {
        phieuThiCongId: this.phieutc,
        thiCongHeThongId: this.thiCongHeThongSelected.THICONGHT_ID,
        thietBiId: data.ID
      })
      await this.getPhieuThiCongTbiKhachHang()
    },
    async getPhieuThiCongTbiKhachHang(axios, params) {
      this.dsThietBiKhachHang = await api.getPhieuThiCongThietBiKhachHang(this.axios, {
        thiCongHeThongId: this.thiCongHeThongSelected.THICONGHT_ID
      })
      await Promise.all(
        this.dsThietBiKhachHang.map(async (item) => {
          let rs = await api.kiemTraThietBiLapDat(this.axios, item.THIETBI_ID)
          item.DA_LAP_DAT = rs[0].item
        })
      )
      const soLuongKhaiBao = Number(this.phieuThiCongDetail?.GIATRI_KHAIBAO || 0)
      const soLuongThietBi = Number(this.dsThietBiKhachHang?.length || 0)
      this.thietBiKhachHangSelected = soLuongKhaiBao === soLuongThietBi
    },
    handleError: function(error) {
      console.log('Error: ', error)

      // Check multiple possible error message locations
      let errorMessage = 'Đã xảy ra lỗi!'

      if (error.response && error.response.data) {
        // Try different message properties
        errorMessage =
          error.response.data.message_detail ||
          error.response.data.message ||
          error.response.data.error_message ||
          errorMessage
      } else if (error.message) {
        // For non-HTTP errors
        errorMessage = error.message
      }

      this.$root.toastError(errorMessage)
    },
    onSelectThietBiKhachHang(event) {
      const soLuongKhaiBao = Number(this.phieuThiCongDetail?.GIATRI_KHAIBAO || 0)
      const soLuongThietBi = Number(this.dsThietBiKhachHang?.length || 0)
      this.thietBiKhachHangSelected = soLuongKhaiBao === soLuongThietBi
    },
    onChonThietBiKH() {
      this.$refs.ModalChonThietBi.show('ADD', null, this.phieuThiCongDetail, this.heThongThietbiIdSelected)
    },
    chonThietBi(data) {
      this.onThemMoiThietBiSuccess(data)
    },
    lapDatThietBi(item) {
      this.$refs.ModalChiTietThietBiIDCKhachHang.show(item, item.MATSAN_ID ? 'VIEW' : 'EDIT', 'LAPDAT')
    },
    thaoDoThietBi(item) {
      this.$refs.ModalChiTietThietBiIDCKhachHang.show(item, 'EDIT', 'THAO_DO')
    },
    thayDoiThietBi(item) {
      this.$refs.ModalChiTietThietBiIDCKhachHang.show(item, 'EDIT', 'THAY_DOI_LAP_DAT')
    },
    viewThietBi(item) {
      this.$refs.ModalChiTietThietBiIDCKhachHang.show(item, 'VIEW', null)
    },
    lapDatHeThong(item) {
      // Mở modal chi tiết hệ thống với mode lắp đặt - focus vào 3 trường: IDC, thời điểm, căn cứ
      if (!this.isLapDatFullThietBi()) {
        this.$root.toastError('Cần lắp đặt tất cả thiết bị trước khi lắp đặt hệ thống')
        return
      }
      this.$refs.ModalChiTietHeThongIDCKhachHang.show(item, 'EDIT', 'LAPDAT')
    },
    thaoDoHeThong(item) {
      // Mở modal chi tiết hệ thống với mode tháo dỡ - focus vào 3 trường: IDC, thời điểm, căn cứ
      if (!this.isThaoDoFullThietBi()) {
        this.$root.toastError('Cần tháo dỡ tất cả thiết bị trước khi tháo dỡ hệ thống')
        return
      }
      this.$refs.ModalChiTietHeThongIDCKhachHang.show(item, 'EDIT', 'THAO_DO')
    },
    thayDoiHeThong(item) {
      // Mở modal chi tiết hệ thống với mode thay đổi - focus vào 3 trường: IDC, thời điểm, căn cứ
      this.$refs.ModalChiTietHeThongIDCKhachHang.show(item, 'EDIT', 'THAY_DOI_LAP_DAT')
    },
    viewHeThong(item) {
      // Mở modal chi tiết hệ thống với mode xem
      this.$refs.ModalChiTietHeThongIDCKhachHang.show(item, 'VIEW', null)
    },
    isLapDatFullThietBi() {
      return this.dsThietBiKhachHang.every((item) => item.MATSAN_ID != null)
    },
    isThaoDoFullThietBi() {
      return this.dsThietBiKhachHang.every((item) => item.MATSAN == null)
    },
    handleIdcChange: function(newIdcId) {
      this.idcSelectedEmit = newIdcId
    },
    thongBaoDuGiaTriKhaiBao() {
      let action = ''
      switch (this.phieuThiCongDetail.LOAICV_ID) {
        case LOAI_CONG_VIEC.LAP_DAT:
          action = 'lắp đặt'
          break
        case LOAI_CONG_VIEC.THAO_DO:
          action = 'tháo dỡ'
          break
        case LOAI_CONG_VIEC.THAY_DOI:
          action = 'thay đổi'
          break
      }
      if (this.thietBiKhachHangSelected && this.phieuThiCongDetail.TRANGTHAI_ID == TRANG_THAI_PHIEU.TIEP_NHAN) {
        this.$root.toastSuccess('Số lượng thiết bị ' + action + ' đã đủ với số lượng khai báo!')
      }
    }
  },
  mounted: function() {
    EventBus.$on('update-select-idc', this.handleIdcChange)
  },
  beforeDestroy: function() {
    EventBus.$off('update-select-idc', this.handleIdcChange)
  }
}
</script>
<style scoped>
.disabled {
  color: white !important;
}

.disabled-btn {
  pointer-events: none;
  opacity: 0.6;

  &:hover {
    cursor: not-allowed;
    background-color: white !important;
  }
}
</style>
