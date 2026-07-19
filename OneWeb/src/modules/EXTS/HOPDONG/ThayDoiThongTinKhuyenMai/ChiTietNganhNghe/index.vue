<template src="./index.html">
</template>

<script>
export default {
  name: "ChiTietNganhNghe",
  props:{
    id:String,
    objChiTietNganhNghe: Object,
  },
  data: function (){
    return {
      TNC1_ID: null,
      TNC2_ID: null,
      TNC3_ID: null,
      TNC4_ID: null,
      nganhnghe_id: null,
      txtTimKiem: null,
      cboTieuNganh1: null,
      cboTieuNganh2: null,
      cboTieuNganh3: null,
      cboTieuNganh4: null,
      txtMota: null,

      dsTieuNganh1:[],
      dsTieuNganh2:[],
      dsTieuNganh3:[],
      dsTieuNganh4:[],
    }
  },
  methods:{
    onModalHidden(){
      this.clear()
    },
    clear() {
      this.nganhnghe_id = null
      this.TNC1_ID = null
      this.TNC2_ID = null
      this.TNC3_ID = null
      this.TNC4_ID = null
      this.cboTieuNganh1 = null
      this.cboTieuNganh2 = null
      this.cboTieuNganh3 = null
      this.cboTieuNganh4 = null
      this.txtMota = null
      this.dsTieuNganh1 = []
      this.dsTieuNganh2 = []
      this.dsTieuNganh3 = []
      this.dsTieuNganh4 = []
    },
    async focusMyElement() {
      try {
        this.txtTimKiem = null
        this.loading(true)
        this.TNC1_ID = this.objChiTietNganhNghe.TNC1_ID
        this.TNC2_ID = this.objChiTietNganhNghe.TNC2_ID
        this.TNC3_ID = this.objChiTietNganhNghe.TNC3_ID
        this.TNC4_ID = this.objChiTietNganhNghe.TNC4_ID
        this.nganhnghe_id = this.objChiTietNganhNghe.nganhnghe_id
        this.dsTieuNganh1 = await this.getDsTieuNganh(1, this.nganhnghe_id, 0)
        this.cboTieuNganh1 = this.dsTieuNganh1.length > 0 ? this.dsTieuNganh1[0].tnc1_id : null
        if (this.TNC1_ID != 0 && this.TNC2_ID != 0 && this.TNC3_ID != 0 && this.TNC4_ID != 0){
          this.cboTieuNganh1 = this.TNC1_ID
          this.cboTieuNganh2 = this.TNC2_ID
          this.cboTieuNganh3 = this.TNC3_ID
          this.cboTieuNganh4 = this.TNC4_ID
        }
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    async getDsTieuNganh(kieu, nganhnghe_id, tc_id){
      try {
        this.loading(true)
        // --1:tieu nghanh 1, 2: tieu nganh 2, 3: tieu nganh 3, 4: tieu nganh 4, 5: mo ta nganh nghe
        let rs = await this.$root.context.post("web-hopdong/thaydoithongtinkhuyenmai/lay_ds_tieunganh",{
          kieu:kieu,
          nganhnghe_id:nganhnghe_id,
          tc_id:tc_id
        })
        return rs.data
      }catch (e) {
      }finally {
        this.loading(false)
      }
    },
    async timKiemTieuNganh(kieu, timkiem) {
      try {
        this.loading(true)
        // --1: TEN_TNC1, 2: TEN_TNC2, 3: TEN_TNC3, 4: con lai
        let rs = await this.$root.context.get("web-hopdong/thaydoithongtinkhuyenmai/timkiem_tieunganh", {
          kieu:kieu,
          timkiem:timkiem,
        })
        return rs.data
      }catch (e) {

      }finally {
        this.loading(false)
      }

    },
    btnCapNhat_Click(){
      this.TNC1_ID = this.cboTieuNganh1
      this.TNC2_ID = this.cboTieuNganh2
      this.TNC3_ID = this.cboTieuNganh3
      this.TNC4_ID = this.cboTieuNganh4
      this.$emit("objTNC", {TNC1_ID:this.TNC1_ID, TNC2_ID:this.TNC2_ID, TNC3_ID:this.TNC3_ID, TNC4_ID:this.TNC4_ID, nganhnghe_id:this.nganhnghe_id})
      this.$bvModal.hide(this.id)
    },
    async TimKiem(kieu) {
      let rsTimKiem = []
      // --1: TEN_TNC1, 2: TEN_TNC2, 3: TEN_TNC3, 4: con lai
      switch (kieu) {
        case 1:
          rsTimKiem = await this.timKiemTieuNganh(1, this.txtTimKiem)
          break
        case 2:
          rsTimKiem = await this.timKiemTieuNganh(2, this.txtTimKiem)
          break
        case 3:
          rsTimKiem = await this.timKiemTieuNganh(3, this.txtTimKiem)
          break
        default:
          rsTimKiem = await this.timKiemTieuNganh(4, this.txtTimKiem)
      }
      if (rsTimKiem.length > 0){
        if (rsTimKiem[0].nganhnghe_id){
          this.nganhnghe_id = rsTimKiem[0].nganhnghe_id
          this.dsTieuNganh1 = await this.getDsTieuNganh(1, this.nganhnghe_id, 0)
          this.cboTieuNganh1 = this.dsTieuNganh1.length > 0 ? this.dsTieuNganh1[0].tnc1_id : null
          if (rsTimKiem[0].tnc1_id)
            this.cboTieuNganh1 = rsTimKiem[0].tnc1_id
          if (rsTimKiem[0].tnc2_id)
            this.cboTieuNganh2 = rsTimKiem[0].tnc2_id
          if (rsTimKiem[0].tnc3_id)
            this.cboTieuNganh3 = rsTimKiem[0].tnc3_id
          if (rsTimKiem[0].tnc4_id)
            this.cboTieuNganh4 = rsTimKiem[0].tnc4_id
        }
      }
    },
    async txtTimKiem_KeyPress() {
      try {
        let length = (this.txtTimKiem + "").trim().length
        switch (length) {
          case 2:
            await this.TimKiem(1)
            break
          case 3:
            await this.TimKiem(2)
            break
          case 4:
            await this.TimKiem(3)
            break
          case 5:
            await this.TimKiem(4)
        }
      }catch (e) {
        console.log(e)
      }
    },
  },
  watch:{
    cboTieuNganh1:async function (val) {
      if (val !== null) {
        let tnc1_id = parseInt(this.cboTieuNganh1.toString(), 10)
        this.dsTieuNganh2 = await this.getDsTieuNganh(2, this.nganhnghe_id, tnc1_id)
        this.cboTieuNganh2 = this.dsTieuNganh2.length > 0 ? this.dsTieuNganh2[0].tnc2_id : null
      }
    },
    cboTieuNganh2:async function(val){
      if (val !== null){
        let tnc2_id = parseInt(this.cboTieuNganh2.toString(), 10)
        this.dsTieuNganh3 = await this.getDsTieuNganh(3, this.nganhnghe_id, tnc2_id)
        this.cboTieuNganh3 = this.dsTieuNganh3.length > 0 ? this.dsTieuNganh3[0].tnc3_id : null
      }
    },
    cboTieuNganh3:async function(val){
      if (val !== null){
        let tnc3_id = parseInt(this.cboTieuNganh3.toString(), 10)
        this.dsTieuNganh4 = await this.getDsTieuNganh(4, this.nganhnghe_id, tnc3_id)
        this.cboTieuNganh4 = this.dsTieuNganh4.length > 0 ? this.dsTieuNganh4[0].tnc4_id : null
      }
    },
    cboTieuNganh4:async function(val){
      if (val !== null){
        let tnc4_id = parseInt(this.cboTieuNganh4.toString(), 10)
        let dt = await this.getDsTieuNganh(5, this.nganhnghe_id, tnc4_id)
        this.txtMota = dt.length > 0 ? dt[0].mota : null
      }
    }
  }
}
</script>

<style>
.chitiet-nganhnghe .select2-container .select2-selection--single .select2-selection__rendered {
  white-space: initial;
  height: 28px;
}
</style>
