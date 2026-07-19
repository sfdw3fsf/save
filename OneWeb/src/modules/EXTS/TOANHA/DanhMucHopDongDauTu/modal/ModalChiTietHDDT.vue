<template>
  <b-modal ref="dlgChiTietHDDT" size="xl" body-class="p-0" scrollable title="Chi tiết hợp đồng đầu tư" @shown="frmChiTietHDDTU_Shown" hide-footer>
    <frmChiTietHDDTU ref="frmChiTietHDDTU"> </frmChiTietHDDTU>
  </b-modal>
</template>
<script>
import ChiTietHDDTU from '../../ChiTietHDDTU'
export default {
  name: 'PopupChiTietHDDT',
  components: {
    frmChiTietHDDTU: ChiTietHDDTU
  },
  props: {
    vma_hd: {
      type: String,
      default: ''
    },
    vten_hd: {
      type: String,
      default: ''
    },
    vten_toanha: {
      type: String,
      default: ''
    },
    vhddtu_id: {
      type: Number,
      default: 0
    },
    vchudtu_id: {
      type: Number,
      default: 0
    },
    checkThangChot: false,
    thangChot: {
      value: new Date(),
      currentDate: new Date(),
      changeDate: new Date()
    }
  },
  methods: {
    frmChiTietHDDTU_Shown: async function () {
      this.$refs.frmChiTietHDDTU.vma_hd = this.vma_hd
      this.$refs.frmChiTietHDDTU.vten_hd = this.vten_hd
      this.$refs.frmChiTietHDDTU.vten_toanha = this.vten_toanha
      this.$refs.frmChiTietHDDTU.vhddtu_id = this.vhddtu_id
      this.$refs.frmChiTietHDDTU.checkThangChot = this.checkThangChot
      this.$refs.frmChiTietHDDTU.thangChot = this.thangChot
      await this.$refs.frmChiTietHDDTU.frmChiTiet_HDDTU_Load()
    },
    showModal() {
      this.$refs['dlgChiTietHDDT'].show()
    },
    hideModal() {
      this.$refs['dlgChiTietHDDT'].hide()
    }
  }
}
</script>
<style>
.page-content {
  top: 40px !important;
  bottom: 0 !important;
}
</style>
