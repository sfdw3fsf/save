<template src="./index.html"></template>
<script>
export default {
  name: "ChonGoiThauOnline",
  props: {
    modalId: {
      type: String,
    },
    maDuAn: {
      type: String,
      default: ''
    }
  },
  data: function () {
    return {
      grcGoiThau: [],
      selected: null,
      txtMaDuAn: '',
      enabledChon: true,
    }
  },
  methods: {
    focusMyElement() {
      this.frmChonGoiThauOnline(this.maDuAn)
      this.btnTraCuu_Click()
    },
    hiddenModal() {
      this.$bvModal.hide(this.modalId)
      this.grcGoiThau = []
      this.txtMaDuAn = ''
    },
    frmChonGoiThauOnline(MaDuAn) {
      this.txtMaDuAn = MaDuAn ? MaDuAn.trim() :''
    },
    btnTraCuu_Click() {
      this.LOAD_GRID()
    },
    async LOAD_GRID() {
      this.enabledChon = false
      this.grcGoiThau = []
      try {
        this.$root.loading(true)
        let res = await this.Get_BiddingPackage_AcceptanceMilestone_byProjectCode((this.txtMaDuAn + '').trim())
        if (res == null || res.data == null || res.data.length == 0) {
          this.$root.toastError(`Không tìm thấy gói thầu theo dự án ${this.txtMaDuAn}`)
          return
        }
        this.grcGoiThau = res.data
      }catch (e) {

      }finally {
        this.$root.loading(false)
        this.enabledChon = this.grcGoiThau != null && this.grcGoiThau.length > 0
      }
    },

    async Get_BiddingPackage_AcceptanceMilestone_byProjectCode(txtMaDuAn) {
      return await this.$root.context.get("tichhop/qldt/get_BiddingPackage_AcceptanceMilestone_byProjectCode", {
        sProjectCode: txtMaDuAn
      })
    },
    grcGoiThau_selectedRowChanged(args) {
      this.selected = args
    },
    chonMa() {
      if (this.selected) {
        this.$emit('xacnhan', this.selected.ma_goi_thau)
        this.hiddenModal()
      }
    }
  }
}
</script>

<style scoped>

</style>
