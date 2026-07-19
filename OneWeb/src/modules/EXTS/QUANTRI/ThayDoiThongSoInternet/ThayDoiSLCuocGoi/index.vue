<template src="../ThayDoiSLCuocGoi/index.html">
</template>
<style scoped>
input[type=number] {
  text-align:right;
}
input[type=text] {
  text-align:left;
}
</style>
<script>

export default {
   props: {
   },
   data : function () {
     return {
       inputSlcg: {},
       resolvePromise :{},
       rejectPromise :{},
       somay : "",
       ttthuebao : "",
       tentb : "",
       diachiLd : "",
       slCgCu : "",
       slCgMoi : "",
       trangthai : 1,
       vthuebao_id : 0,
       vhdtb_id : 0
     }
   },
  methods: {
    show (data) {
      return new Promise((resolve, reject) => {
        this.inputSlcg = data

        this.$bvModal.show("thaydoisoluongcuocgoi")
        this.resolvePromise = resolve
        this.rejectPromise = reject
        this.Hienthithongtin()
      })
    },
    showModal: function() {
    },
    Hienthithongtin()
    {
      this.somay =  this.inputSlcg.somay;
      this.tentb = this.inputSlcg.tentb;
      this.ttthuebao = this.inputSlcg.ttthuebao;
      this.diachiLd = this.inputSlcg.diachiLd;
      this.slCgCu = this.inputSlcg.slCg;
      this.slCgMoi = this.inputSlcg.slCg;

    },
     ghilai : async function (){
        if(this.slCgMoi >999 || this.slCgMoi<0 ){
          this.$toast.error("Số lượng thuê bao mới không hợp lệ.")
          return
        }
       if (this.slCgCu != this.slCgMoi) {
         await this.capnhat()
       }else {
         this.$toast.error("Số lượng thuê bao mới phải khác cũ.")
       }
    },closeModal: function () {
      this.$bvModal.hide("thaydoisoluongcuocgoi")
    }, capnhat : async function() {
      try {
        let rs = await this.$root.context.post("web-quantri/thongso_internet/update_soluong_cuocgoi", {
          thuebao_id: this.inputSlcg.trangthai == '1' ? this.inputSlcg.vthuebao_id : this.inputSlcg.vhdtb_id,
          ma_tb: this.somay,
          sl_cuocgoi_moi : this.slCgMoi,
          sl_cuocgoi_cu : this.slCgCu,
          trang_thai : this.inputSlcg.trangthai
        })
        if(rs.data[0].count ==1 ){
          this.$toast.success("Cập nhật thành công!");
          this.resolvePromise({ok: false, data: this.slCgMoi})
          this.closeModal()
        } else {
          this.$toast.success("Cập nhật thất bại!");
        }
      } catch (error) {
        this.$toast.success("Cập nhật thất bại!");
      }
    }
  },
  watch : {
    'slCgMoi': async function(newVal, oldVal) {
      if(newVal> 999){
        this.slCgMoi = 999
      }
      if(newVal<0){
        this.slCgMoi = 0
      }
    }
  }
}
</script>

<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

#table-scroll {
  height: 410px;
  overflow: auto;
  margin-top: 20px;
}

</style>
