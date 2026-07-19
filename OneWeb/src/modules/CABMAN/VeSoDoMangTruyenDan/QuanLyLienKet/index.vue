<template src="./index.html"></template>
<script>
import BssRequiredMarker from '@/components/BssRequiredMarker'

export default {
  components:{BssRequiredMarker},
  name: "QuanLyLienKet",
  props: {
    modalShowQlLk: Boolean,
    objUpdate: Object,
    objLoaiLienKet: Array
  },
  data: function (){
    return {
      lienKetId :-1,
       tenLienKet: '',
       tenHienThi: '',
       dsLoaiLienKet: [],
       loaiLkId: "",
       prtgId: "",
       congNguon: "",
      ghiChuNguon: "",
       congDich: "",
      ghiChuDich: "",
      doituongnguon : "",
      doituongnguonId : "",
      doituongdich : "",
      doituongdichId : "",
      tuyenTruyendan : "",
      tuyenTruyendanId : "",

      category: {
        dinhdanh: 0,
        prtg: 0
      }
    }
  },
  methods:{
    onChangeLoaiLienKet: async function () {

  },
  hideModal: function () {
    this.$emit('hideModal')
    this.$emit('reloadGianDo')
  }, reloadGianDo: function () {
      this.$emit('hideModal')
      this.$emit('reloadGianDo')
    }, onModalShown() {
      this.tenLienKet = 'United'
      this.tenHienThi = 'United'
      if(this.objLoaiLienKet){
        this.loaiLkId = this.objLoaiLienKet[0].LOAILK_ID
      }
      if(this.objUpdate) {
        if (this.objUpdate.KIEU_ === "INSERT") {
          this.doituongnguon = this.objUpdate.DT_NGUON_TEN
          this.doituongnguonId = this.objUpdate.DT_NGUON_ID
          this.doituongdich = this.objUpdate.DT_DICH_TEN
          this.doituongdichId = this.objUpdate.DT_DICH_ID
          this.tuyenTruyendan = this.objUpdate.TEN_TUYENTD_ID
          this.tuyenTruyendanId = this.objUpdate.TUYENTD_ID
        } else {
          this.doituongnguon = this.objUpdate.DT_NGUON_TEN
          this.doituongnguonId = this.objUpdate.DT_NGUON_ID
          this.doituongdich = this.objUpdate.DT_DICH_TEN
          this.doituongdichId = this.objUpdate.DT_DICH_ID
          this.tuyenTruyendan = this.objUpdate.TEN_TUYENTD_ID
          this.tuyenTruyendanId = this.objUpdate.TUYENTD_ID
          this.loaiLkId = this.objUpdate.LOAI_LK_ID
        }
        console.log(this.objUpdate)
      }
    },
    onHiddenModalDT() {

    },
    async onShownModalDT() {

    },
    async btnGhiLai(){
    if(!this.tenLienKet) {
        this.$toast.warning("Trường tên liên kết không được trống.")
      return
      }
    if(!this.tenHienThi) {
        this.$toast.warning("Trường tên hiển thị không được trống.")
      return
      }

      // this.doituongnguonId =   this.doituongnguonId.split("_")[1]
      // this.doituongdichId =   this.doituongdichId.split("_")[1]
    let body ={

      ten_lk: this.tenLienKet,
      ten_ht:this.tenHienThi,
      loailk_id:this.loaiLkId ,
      doituong_n_id:this.doituongnguonId,
      doituong_d_id:this.doituongdichId,
      tuyentd_id:this.tuyenTruyendanId,
      diemuon_x1:-630,
      diemuon_y1:-730,
      diemuon_x2:-220,
      diemuon_y2:120,
      vitri_td_x:0,
      vitri_td_y:0

    }
      await this.$root.context.post(
        "/web-suco/mang_bangrong/taolienket",body
      ).then(res =>{
          if(res){
            this.$toast.success("Thêm mới thành công.")
            this.reloadGianDo()
          }else {
            this.$toast.error("Thêm mới thất bại.")
          }
      }).catch(e=> {throw e})
    }

  },

}
</script>
