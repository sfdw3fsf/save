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
      childGrid: [],
      listThiCongHeThongThietBiKhachHang: [],
      heThongThietbiIdSelected: null,
      thietBiKhachHangSelected: null,
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
    AnCotThaoTac: function(isView = false) {
      let rs = false
      // trạng thái là tiếp nhận hoặc nhiệm vụ mới
      if (this.phieuThiCongDetail != null) {
        rs = this.phieuThiCongDetail.TRANGTHAI_ID == 81 || this.phieuThiCongDetail.TRANGTHAI_ID == 21
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
    },
    // return true khi lắp đặt cho khách hàng và đang ở nhiệm vụ mới
    isEditThongTinLapDat() {
      return (
        this.phieuThiCongDetail.TRANGTHAI_ID == 21 ||
        (this.isKhachHang &&
          this.phieuThiCongDetail.LOAICV_ID == 50 &&
          this.phieuThiCongDetail.TRANGTHAI_ID == 81 &&
          this.phieuThiCongDetail.NHANVIEN_TH_ID == this.$root.token.getNhanVienID())
      )
    },
    isChuyenSangThucHien() {
      return (
        this.phieuThiCongDetail.TRANGTHAI_ID == 81 &&
        this.phieuThiCongDetail.NHANVIEN_TH_ID == this.$root.token.getNhanVienID()
      )
    }
  },

  watch: {
    heThongThietbiIdSelected: function(newVal) {
      if (!newVal) {
        return
      }
      console.log('heThongThietbiIdSelected', newVal)
    }
  },

  created: async function() {
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
        this.$root.showLoading(true)
        this.phieuThiCongDetail = await api.getChiThietPhieuThiCong(this.axios, this.phieutc)
        this.isViewOnly =
          !(
            this.phieuThiCongDetail.TRANGTHAI_ID == 81 ||
            this.phieuThiCongDetail.TRANGTHAI_ID == 21 ||
            this.phieuThiCongDetail.TRANGTHAI_ID == 22
          ) ||
          this.$route.query.viewOnly === '1' ||
          (this.phieuThiCongDetail.TRANGTHAI_ID == 81
            ? this.phieuThiCongDetail.NHANVIEN_TH_ID != this.$root.token.getNhanVienID()
            : false)
      } catch (error) {
        this.$root.toastError(error?.message_detail || 'Có lỗi xảy ra khi lấy thông tin phiếu thi công')
      } finally {
        this.$root.showLoading(false)
      }
    },
    onDetailDataBound() {
      // this.grdNhiemVu.autoFitColumns();
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
      console.log(this.phieuThiCongDetail.LOAICV_ID)
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
        default:
          this.$router.push({ path: '/tainguyen-idc/tiepnhan-thicong' })
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
            trangThaiId: 24,
            nhanVienId: Number(this.$root.token.getNhanVienID())
          })
          if (rs && rs.error_code === 'BSS-00000000') {
            this.$root.toastSuccess('Hoàn thành phiếu thi công thành công!')
            console.log(this.phieuThiCongDetail.LOAICV_ID)
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
    acctionColumnDeviceLapDat(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons">
                      <button class="btn btn-sm btn-outline-primary" title="Lắp đặt thiết bị" @click="lapDat()" >
                        <i class="fa fa-cogs"></i> Lắp đặt
                      </button>
                    </div>`,
            methods: {
              lapDat() {
                parent.lapDat(this.data)
              }
            }
          }
        }
      }
    },
    acctionColumnDeviceThaoDo(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons">
                      <button class="btn btn-sm btn-outline-danger" title="Tháo dỡ thiết bị" @click="thaoDo()" >
                        <i class="fa fa-cogs"></i> Tháo dỡ
                      </button>
                    </div>`,
            methods: {
              thaoDo() {
                parent.thaoDo(this.data)
              }
            }
          }
        }
      }
    },
    acctionColumnDeviceThayDoi(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons">
                      <button class="btn btn-sm btn-outline-success" title="Thay đổi lắp đặt thiết bị" @click="thayDoiLapDat()" >
                        <i class="fa fa-cogs"></i> Thay đổi lắp đặt
                      </button>
                    </div>`,
            methods: {
              thayDoiLapDat() {
                parent.thayDoiLapDat(this.data)
              }
            }
          }
        }
      }
    },
    acctionColumnDeviceView(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons">
                      <button class="btn btn-sm btn-outline-primary" title="Xem thông tin lắp đặt" @click="xemThongTinLapDat()" >
                        <i class="fa fa-cogs"></i> Xem thông tin lắp đặt
                      </button>
                    </div>`,
            methods: {
              xemThongTinLapDat() {
                parent.xemThongTinLapDat(this.data)
              }
            }
          }
        }
      }
    },
    acctionColumnSystemLapDat(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons">
                      <button class="btn btn-sm btn-outline-primary" title="Lắp đặt hệ thống" @click="lapDatHeThong()" >
                        <i class="fa fa-cogs"></i> Lắp đặt
                      </button>
                    </div>`,
            methods: {
              lapDatHeThong() {
                parent.lapDatHeThong(this.data)
              }
            }
          }
        }
      }
    },
    acctionColumnSystemThaoDo(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons">
                      <button v-if="data.NGAY_THAOGO == null" 
                              class="btn btn-sm btn-outline-danger" 
                              title="Tháo dỡ hệ thống" 
                              @click="thaoDoHeThong()">
                        <i class="fa fa-cogs"></i> Tháo dỡ
                      </button>
                      <span v-else class="badge badge-danger">Đã tháo dỡ
                      </span>
                    </div>`,
            methods: {
              thaoDoHeThong() {
                parent.thaoDoHeThong(this.data)
              }
            }
          }
        }
      }
    },
    acctionColumnSystemThayDoi(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons">
                      <button class="btn btn-sm btn-outline-success" title="Thay đổi thông tin hệ thống" @click="thayDoiHeThong()" >
                        <i class="fa fa-cogs"></i> Thay đổi thông tin
                      </button>
                    </div>`,
            methods: {
              thayDoiHeThong() {
                parent.thayDoiHeThong(this.data)
              }
            }
          }
        }
      }
    },
    acctionColumnSystemView(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons">
                      <button class="btn btn-sm btn-outline-info" title="Xem thông tin hệ thống" @click="xemThongTinHeThong()" >
                        <i class="fa fa-eye"></i> Xem thông tin
                      </button>
                    </div>`,
            methods: {
              xemThongTinHeThong() {
                parent.xemThongTinHeThong(this.data)
              }
            }
          }
        }
      }
    },
    async getPhieuThiCongHeThongKhachHang() {
      this.listThiCongHeThongThietBiKhachHang = await api.getDanhSachThiCongHeThongKhachHang(this.axios, {
        phieuThiCongId: this.phieutc
      })
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
    lapDat(item) {
      this.$refs.ModalChiTietThietBiIDCKhachHang.show(item, item.MATSAN_ID ? 'VIEW' : 'EDIT', 'LAPDAT')
    },
    thaoDo(item) {
      this.$refs.ModalChiTietThietBiIDCKhachHang.show(item, 'EDIT', 'THAO_DO')
    },
    thayDoiLapDat(item) {
      this.$refs.ModalChiTietThietBiIDCKhachHang.show(item, 'EDIT', 'THAY_DOI_LAP_DAT')
    },
    xemThongTinLapDat(item) {
      this.$refs.ModalChiTietThietBiIDCKhachHang.show(item, 'VIEW', null)
    },
    onSelectThietBiKhachHang(event) {
      this.thietBiKhachHangSelected = event || null
    },
    onChonThietBiKH() {
      this.$refs.ModalChonThietBi.show('ADD', null, this.phieuThiCongDetail, this.heThongThietbiIdSelected)
    },
    chonThietBi(data) {
      this.onThemMoiThietBiSuccess(data)
    },
    lapDatHeThong(item) {
      // Mở modal chi tiết hệ thống với mode lắp đặt - focus vào 3 trường: IDC, thời điểm, căn cứ
      this.$refs.ModalChiTietHeThongIDCKhachHang.show(item, 'EDIT', 'LAPDAT')
    },
    thaoDoHeThong(item) {
      // Mở modal chi tiết hệ thống với mode tháo dỡ - focus vào 3 trường: IDC, thời điểm, căn cứ
      this.$refs.ModalChiTietHeThongIDCKhachHang.show(item, 'EDIT', 'THAO_DO')
    },
    thayDoiHeThong(item) {
      // Mở modal chi tiết hệ thống với mode thay đổi - focus vào 3 trường: IDC, thời điểm, căn cứ
      this.$refs.ModalChiTietHeThongIDCKhachHang.show(item, 'EDIT', 'THAY_DOI_LAP_DAT')
    },
    xemThongTinHeThong(item) {
      // Mở modal chi tiết hệ thống với mode xem
      this.$refs.ModalChiTietHeThongIDCKhachHang.show(item, 'VIEW', null)
    },
    handleIdcChange: function(newIdcId) {
      this.idcSelectedEmit = newIdcId
      console.log('this.idcSelectedEmit')
      console.log(this.idcSelectedEmit)
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
