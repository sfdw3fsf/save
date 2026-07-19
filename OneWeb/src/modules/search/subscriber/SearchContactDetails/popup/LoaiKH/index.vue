<template src="./index.html">
</template>

<script>
export default {
  name: "LoaiKH",
  props:{
    id: String,
    objLoaiKH: Object,
  },
  data: function (){
    return {
      loaikh_id: -1,
      kieukh_id: -1,
      nhomlkh_id: -1,
      cboKieuKH: -1,
      cboNhomKH:-1,
      dsNhomKH:[],
      dsLoaiKH:[],

      //output
      loaikhId:null,
    }
  },
  methods:{
    onHiddenModal(){
      this.Clear()
      this.$bvModal.hide("popupLoaiKH")
    },
    async onShownModal() {
      try {
        this.Clear()
        this.loading(true)
        this.kieukh_id = this.objLoaiKH.kieukh_id
        this.nhomlkh_id = this.objLoaiKH.nhomlkh_id
        this.loaikh_id = this.objLoaiKH.loaikh_id
        if (this.kieukh_id !== -1) {
          this.cboKieuKH = this.kieukh_id
        }
      } catch (e) {

      } finally {
        this.loading(false)
      }
    },
    async getDsLoaiKH(nhomloaikh_id){
      let rs = await this.$root.context.get("web-ccdv/hoanthienhschuyenquyen/lay-ds-loaikh",{nhomloaikh_id:nhomloaikh_id})
      this.dsLoaiKH =  rs.data
    },
    async getDsNhomLKH(kieukh){
      let rs = await this.$root.context.get("web-hopdong/thaydoithongtinkhuyenmai/lay_ds_nhomkh",{kieukh:kieukh})
      this.dsNhomKH = rs.data
    },
    btnChapNhan(){
      if (this.dsLoaiKH.length === 0){
        this.$root.toastError("Không có dữ liệu!")
        return
      }      
      this.$emit("getLoaiKHID", this.loadkhId)
      this.$bvModal.hide("popupLoaiKH")

    },
    rowSelected(args){
      if (args.data){        
        this.loaikhId = args.data.loaikh_id
      }
    },
    recordDoubleClick(args){
      if (args.rowData){
        this.loadkhId = args.rowData.loaikh_id
        this.btnChapNhan()
      }
    },
    Clear(){
      this.loaikh_id = -1
      this.kieukh_id = -1
      this.nhomlkh_id = -1
      this.cboKieuKH = null
      this.cboNhomKH = null
      this.dsNhomKH = []
      this.dsLoaiKH = []

      this.loaikhId = null
    }
  },
  watch:{
    cboKieuKH:async function (val){
      if (val !== null){
        try {
          this.loading(true)
          this.dsNhomKH = []
          this.dsLoaiKH = []
          await this.getDsNhomLKH(val)
          setTimeout(() =>{
            if (this.nhomlkh_id !== -1){
              // this.cboNhomKH = this.nhomlkh_id
              let idx = this.dsNhomKH.findIndex(item => item.nhomlkh_id == this.nhomlkh_id)
              if (idx !== -1) {
                this.$refs['cboGridNhomKH'].$children[0].grid.selectRow(idx)
              }
            }else {
              // this.cboNhomKH = this.dsNhomKH[0].nhomlkh_id
              this.$refs['cboGridNhomKH'].$children[0].grid.selectRow(0)
            }
          }, 300)
        }catch (e) {
        }finally {
          this.loading(false)
        }
      }
    },
    cboNhomKH: async function (val){
      if (val){
        try {
          this.loading(false)
          await this.getDsLoaiKH(val)
          if (this.loaikh_id !== -1){
            let idx = this.dsLoaiKH.findIndex(item => item.loaikh_id == this.loaikh_id)

            setTimeout(() => {
              if (idx !== -1) {
                this.$refs['gridLoaiKH'].grid.selectRow(idx)
              } else this.$refs['gridLoaiKH'].grid.selectRow(0)
            }, 300)
          }
        }catch (e){}
        finally {
          this.loading(false)
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
