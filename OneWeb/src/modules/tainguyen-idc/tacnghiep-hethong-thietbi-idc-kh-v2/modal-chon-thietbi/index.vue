<template src="./index.template.html"></template>
<script>
import Vue from 'vue'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import api from '../api'
export default {
  name: 'ModalChonThietBi',
  props: { modalID: null, heThongTbi: null, isKhachHang: { type: Boolean, default: true } },
  components: {
    BssErrorMarker,
    BssRequiredMarker
  },
  created: async function() {
    // Không gọi loadDanhMuc ở đây vì chưa có dữ liệu
  },
  validations: {},
  watch: {
    'searchForm.heThongThietBiId': async function(newVal, oldVal) {
      if (newVal !== oldVal && newVal) {
        await this.loadThietBiTheoHeThong()
      }
    }
  },
  computed: {},
  methods: {
    closeModal: function() {
      this.searchForm = {
        idcId: null,
        heThongThietBiId: null,
        khachHang: null,
        maThueBao: null,
        soHopDong: null,
        hdtbId: null
      }
      this.dsThietBiKhachHang = []
      this.currentItem = null
      // Clear selection của DataGrid
      if (this.$refs.grdItems && this.$refs.grdItems.grid) {
        this.$refs.grdItems.grid.clearRowSelection()
      }
      this.$refs.ModalChonThietBi.hide(this.modalID)
    },
    show: async function(item, mode, data, heThongThietbiId) {
      if (this.$refs.grdItems && this.$refs.grdItems.grid) {
        this.$refs.grdItems.grid.clearRowSelection()
      }
      this.searchForm.heThongThietBiId = heThongThietbiId
      if (data) {
        this.searchForm.khachHang = data.TEN_KH
        this.searchForm.maThueBao = data.MA_TB
        this.searchForm.soHopDong = data.SO_HD
        this.searchForm.hdtbId = data.HDTB_ID
        this.searchForm.tinhId = data.TINH_ID
      }
      if (this.heThongTbi) {
        console.log('heThongTbi', this.heThongTbi)
        this.searchForm.heThongId = this.heThongTbi.HETHONGTBI_ID
      }
      
      // Load dữ liệu ngay khi show modal
      await this.loadDanhMuc()
      
      this.$refs.ModalChonThietBi.show()
    },
    onChonThietBiKH: function() {
      if (!this.currentItem) {
        this.$root.toastError('Vui lòng chọn thiết bị')
        return
      }
      this.$confirm('Xác nhận thực hiện thi công thiết bị: ' + this.currentItem?.TEN, 'Xác nhận', {
        confirmButtonText: 'Xác nhận',
        cancelButtonText: 'Huỷ bỏ'
      }).then(async () => {
        this.$emit('chon-thiet-bi', this.currentItem)
        this.closeModal()
      })
    },
    async loadDanhMuc() {
      try {
        // Load danh sách hệ thống thiết bị
        if (this.isKhachHang) {
          this.heThongThietBiList = await api.getDanhSachHetHongThietBiKhachHang(this.axios, {
            hdtbId: this.searchForm.hdtbId,
            tinhId: this.searchForm.tinhId
          })
        } else {
          this.heThongThietBiList = await api.getDanhSachHetHongThietBiIDC(this.axios, {
            idcId: this.heThongTbi.IDC_ID
          })
        }
        
        // Load danh sách thiết bị nếu đã có hệ thống được chọn
        if (this.searchForm.heThongThietBiId) {
          await this.loadThietBiTheoHeThong()
        }
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi lấy danh mục')
      }
    },
    
    async loadThietBiTheoHeThong() {
      try {
        this.$root.showLoading(true)
        const response = await api.getThietBiTheoHeThong(this.axios, this.searchForm.heThongThietBiId)
        const isLapDat = this.$parent.phieuThiCongDetail.LOAICV_ID == 50
        // lắp đặt thì lấy chưa có vị trí lắp đặt còn tháo dỡ và thay đổi thông tin thì cần có vị trí
        this.dsThietBiKhachHang = (response || []).filter((item) => isLapDat ? item.TOA_NHA == null : item.TOA_NHA != null)
        
        // Lọc bỏ những thiết bị đã được chọn trước đó
        const dsThietBiChon = this.$parent.dsThietBiKhachHang
        if (dsThietBiChon && dsThietBiChon.length > 0) {
          this.dsThietBiKhachHang = this.dsThietBiKhachHang.filter(
            (item) => !dsThietBiChon.some((itemChon) => itemChon.THIETBI_ID == item.ID)
          )
        }

        // Kiểm tra trạng thái lắp đặt cho từng thiết bị
        await Promise.all(
          this.dsThietBiKhachHang.map(async (item) => {
            let rs = await api.kiemTraThietBiLapDat(this.axios, item.THIETBI_ID || item.ID)
            item.DA_LAP_DAT = rs[0].item
          })
        )
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi lấy danh sách thiết bị')
      } finally {
        this.$root.showLoading(false)
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
      dialogPosition: { X: 'center', Y: 'center' },
      item: null,
      currentItem: null,
      dsHeThongThietBiKhachHang: [],
      heThongThietBiList: [],
      dsThietBiKhachHang: [],
      searchForm: {
        heThongThietBiId: null,
        khachHang: null,
        maThueBao: null,
        soHopDong: null,
        hdtbId: null,
        tinhId: null,
        heThongId: null
      }
    }
  },
  mounted: function() {}
}
</script>
<style scoped>
.popup-box {
  max-height: 90vh !important;
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
