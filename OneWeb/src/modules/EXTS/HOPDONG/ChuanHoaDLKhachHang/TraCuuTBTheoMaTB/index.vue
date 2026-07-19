<template src="./index.html"></template>
<script>
import DateTimeLib from "./../../../../../utils/DateTimeLib";
export default {
  name: "TraCuuTBTheoMaTB",
  props: ["modalId","data"],
  computed: {},
  destroyed() {},
  watch: {
  },
  methods: {
    focusMyElement: async function () {
      try{
        this.$root.showLoading(true)
        console.log('data',this.data)
        this.Lay_dulieu_combobox();
        this.$refs.txtMaTB.focus();
      }catch(er){
        console.log(er)
      }
      finally{
         this.$root.showLoading(false)
      }
    },
    hiddenModal: function(){
      this.dsThueBao = []
      this.dsThanhToan = []
      this.chapnhan = true;
      this.$bvModal.hide(this.modalId);
    },
    Lay_dulieu_combobox: async function(){
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/hoanthien_cbb"
        );
        if (rs.data != null) {       
          this.dsDichVuVT = rs.data.dichvu    
        }
      
        if(this.dsDichVuVT.length > 0) 
        this.dichvuvt_id = this.dsDichVuVT[0].dichvuvt_id
      } catch (error) {
        return null;
      } finally {
      }
     },
    TimKiem:  function(){
      this.chapnhan = false;
      if (this.txtMaTB != "")
        {
               this.HienThiTT_DanhBa_KH(this.txtMaTB);
        }
    },

    GhiLai: async function(){

    },
    onChangecboDichVuVT:async function(){

    },
    gridthanhtoan_selectedItemsChanged: function(data){

    },
    gridthanhtoan_selectedRowChanged: function(data){

    },
    gridthuebao_selectedRowChanged: function(data){

    },
    HienThiTT_DanhBa_KH: async function(ma_tb){

    }
 },
  data() {
    return {
    dsThueBao:[],
    dsThanhToan:[],
    dsDichVuVT:[],
    dichvuvt_id:-1,
    dateVal: Date,
    dateFormat: "dd/MM/yyyy",
    waterMark:"Chọn ngày",
    txtMaHD:'',
    txtMaKH:'',
    txtMaTB:'',
    dtpNgayLapHD: new Date(),
    txtTenKH:'',
    txtDiaChiKH:'',
    idsHDTT:[],
    currentThueBao: null,
    cboDichVuVT:-1,
    hdkh_id :-1,
    ma_gd : '',
    kt_tbdd : false,
    donviQL_id : -1 ,
    kylai : -1,
    ngayyeucau : Date(),
    chapnhan: false
    }
  },
};
</script>
<style>
.defendLine{
 display: flex;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);

}
</style>