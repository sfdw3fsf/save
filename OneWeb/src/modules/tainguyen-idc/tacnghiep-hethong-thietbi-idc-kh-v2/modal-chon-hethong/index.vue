<template src="./index.template.html"></template>
<script>
import Vue from 'vue'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import EventBus from '@/utils/eventBus'
import api from '../api'
export default {
  name: 'ModalChonHeThong',
  props: { modalID: null, isKhachHang: { type: Boolean, default: true } },
  components: {
    BssErrorMarker,
    BssRequiredMarker
  },
  created: async function() {
    await this.loadDanhMuc()
    // Listen to IDC change events from detail form
    EventBus.$on('update-select-idc', this.handleIdcChange)
  },
  beforeDestroy() {
    // Clean up event listener
    EventBus.$off('update-select-idc', this.handleIdcChange)
  },
  validations: {},
  watch: {
    'searchForm.idcId': async function(newVal, oldVal) {  
      if (newVal !== oldVal) {
        try {
          const response = this.isKhachHang
            ? await api.getDanhSachHetHongThietBiKhachHang(this.axios, this.searchForm)
            : await api.getDanhSachHetHongThietBiIDC(this.axios, this.searchForm)
          console.log(response, 'response')

          // Filter dữ liệu dựa trên modeThiCong
          let filteredData = response.filter((httb) => httb.TRANGTHAI_VH_ID == 1) || []

          if (this.modeThiCong === 'THAO_DO') {
            // Tháo dỡ: chỉ lấy hệ thống chưa tháo dỡ (NGAY_THAOGO == null) và đã lắp đặt
            filteredData = filteredData.filter((httb) => httb.NGAY_THAOGO == null)
          } else if (this.modeThiCong === 'LAPDAT' || this.modeThiCong === 'THAY_DOI_LAP_DAT') {
            // Lắp đặt/Thay đổi: chỉ lấy hệ thống chưa tháo dỡ
            filteredData = filteredData.filter((httb) => httb.NGAY_THAOGO == null)
          }

          this.dsHeThongThietBiKhachHang = filteredData
        } catch (error) {
          this.$root.toastError(error?.message || 'Có lỗi xảy ra khi lấy danh sách hệ thống thiết bị khách hàng')
        } finally {
        }
      }
    }
  },
  computed: {},
  methods: {
    handleIdcChange: function(newIdcId) {
      console.log('Received IDC change event:', newIdcId)
      this.searchForm.idcId = newIdcId
    },
    closeModal: function() {
      this.currentItem = null
      // Clear selection của DataGrid
      if (this.$refs.grdItems && this.$refs.grdItems.grid) {
        this.$refs.grdItems.grid.clearRowSelection()
      }
      this.$refs.ModalChonHeThong.hide(this.modalID)
    },
    show: function(item, mode, data, modeThiCong = null) {
      // Clear selection của DataGrid
      if (this.$refs.grdItems && this.$refs.grdItems.grid) {
        this.$refs.grdItems.grid.clearRowSelection()
      }
      this.modeThiCong = modeThiCong // Lưu mode để filter dữ liệu
      if (data) {
        this.searchForm.idcId = data.IDC_ID
        this.searchForm.khachHang = data.TEN_KH
        this.searchForm.maThueBao = data.MA_TB
        this.searchForm.soHopDong = data.SO_HD
        this.searchForm.hdtbId = data.HDTB_ID
        this.searchForm.tinhId = data.TINH_ID
      }
      this.$refs.ModalChonHeThong.show()
    },
    onChonHeThongThietBiKH: function() {
      // Kiểm tra xem có item được chọn hay không
      if (!this.currentItem || !this.currentItem.HETHONGTBI_ID) {
        this.$root.toastError('Vui lòng chọn hệ thống thiết bị!')
        return
      }

      this.$confirm('Xác nhận thực hiện thi công hệ thống: ' + this.currentItem?.TEN, 'Xác nhận', {
        confirmButtonText: 'Xác nhận',
        cancelButtonText: 'Huỷ bỏ'
      }).then(async () => {
        this.$emit('chon-he-thong-thiet-bi-kh', this.currentItem)
        this.closeModal()
      })
    },
    async loadDanhMuc() {
      try {
        this.idcList = await api.getDanhMucIDC(this.axios, this.$root.token, 'VIEW')
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi lấy danh mục')
      }
    },
    onSelectedRowChanged: function(event) {
      this.currentItem = event || null
    }
  },
  data: function() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      item: null,
      currentItem: null,
      modeThiCong: null, // LAPDAT, THAO_DO, THAY_DOI_LAP_DAT
      idcList: [],
      dsHeThongThietBiKhachHang: [],
      searchForm: {
        idcId: null,
        khachHang: '',
        maThueBao: '',
        soHopDong: '',
        hdtbId: null,
        tinhId: null
      }
    }
  },
  mounted: function() {}
}
</script>
<style scoped>
.popup-box {
  max-height: 80vh !important;
  overflow-y: auto;
}
.e-dialog {
  position: fixed !important;
  top: 50% !important;
  left: 50% !important;
  transform: translate(-50%, -50%) !important;
}

.e-dialog .e-dlg-content {
  overflow: visible !important;
  max-height: 80vh !important;
  overflow-y: auto !important;
}

.e-dialog .e-dlg-header-content {
  position: sticky !important;
  top: 0 !important;
  background: white !important;
}

.e-dialog-container {
  position: fixed !important;
  top: 0 !important;
  left: 0 !important;
  width: 100% !important;
  height: 100% !important;
}
</style>
