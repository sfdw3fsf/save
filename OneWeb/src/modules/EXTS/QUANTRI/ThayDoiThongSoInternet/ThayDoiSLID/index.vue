<template src="./index.html">
</template>

<script>
export default {
  name: "ThayDoiSLID",
  props:{
    parentFrm:Object,
  },
  data:function (){
    return {
      vSLID:"",
      vSeriMD:"",
      vthuebao_id :0,
      trangthai_dl:0,
    }
  },
  methods:{
    show(vSLID, vSeriMD, trangthai_dl, vthuebao_id){
      this.vSLID = vSLID
      this.vSeriMD = vSeriMD
      this.trangthai_dl = trangthai_dl
      this.vthuebao_id = vthuebao_id
      // this.$refs.txtSeriMD.focus()
      return this.$refs['ThayDoiSLID'].show()
    },
    async hide() {
      this.vSLID = ""
      this.vSeriMD = ""
      this.trangthai_dl = 0
      this.vthuebao_id = 0
      await this.parentFrm.HienThiThongTin()
      this.$refs['ThayDoiSLID'].hide()
    },
    async tsbtnCapNhat_Click() {
      try {
        this.loading(true)
        let rs = await this.parentFrm.GhiBienDong_TDTT((this.vSLID + '').trim(), (this.vSeriMD + '').trim())
        if (rs && rs.kq == 0)
          return
        await this.parentFrm.CAPNHAT_TS_KHAC(this.vthuebao_id, this.trangthai_dl, (this.vSLID + '').trim(), (this.vSeriMD + '').trim())
        await this.$root.context.post("web-quantri/thaydoi-thongso-internet/capnhat-log-action-bnm",{
          ACTION_TYPE:"CapNhat_Serial",
          MA_TB: this.vthuebao_id.toString(),
          VALUE:`SLID: ${this.vSLID}; Serial: ${this.vSeriMD}`,
          NOTE:"Log from frmThayDoiSLID"
        })
        this.$root.toastSuccess("Cập nhật thông tin thành công!")
      } catch (e) {
        console.log(e)
        this.$root.toastError("Có lỗi xảy ra khi cập nhật thông tin")
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>
