<template src="./index.html"></template>

<script>
export default {
  name: "ChonHopDongIMSOnline",
  props: {
    modalId: {
      type: String,
    },
    maGoiThau: {
      type: String,
      default: ''
    }
  },
  data: function () {
    return {
      txtMaGoiThau: '',
      grcHopDong: [],
      selected: null,
      enabledChon: true,
    }
  },
  methods: {
    focusMyElement() {
      this.frmChonHopDongIMSOnline(this.maGoiThau)
      this.btnTraCuu_Click()
    },
    hiddenModal() {
      this.$bvModal.hide(this.modalId)
      this.grcHopDong = []
      this.txtMaGoiThau = ''
    },
    frmChonHopDongIMSOnline(maGoiThau) {
      this.txtMaGoiThau = maGoiThau ? maGoiThau.trim() :''
    },
    btnTraCuu_Click() {
      this.LOAD_GRID()
    },
    async LOAD_GRID() {
      this.enabledChon = false
      this.grcHopDong = []
      try {
        this.$root.loading(true)
        let res = await this.Get_ContractInfo_byBiddingPackageCode((this.txtMaGoiThau + '').trim())
        if (res == null || res.data == null || res.data.length == 0) {
          this.$root.toastError(`Không tìm thấy gói thầu theo dự án ${this.txtMaGoiThau}`)
          return
        }
        this.grcHopDong = res.data
      }catch (e) {

      }finally {
        this.$root.loading(false)
        this.enabledChon = this.grcHopDong != null && this.grcHopDong.length > 0
      }
    },

    async Get_ContractInfo_byBiddingPackageCode(txtMaGoiThau) {
      return await this.$root.context.get(" tichhop/qldt/get_ContractInfo_byBiddingPackageCode", {
        sBiddingPackageCode: txtMaGoiThau
      })
    },
    grcHopDong_selectedRowChanged(args) {
      this.selected = args
    },
    chonMa() {
      if (this.selected) {
        this.$emit('xacnhan', this.selected.ma_hop_dong)
        this.hiddenModal()
      }
    }
  }
}
</script>

<style scoped>

</style>
