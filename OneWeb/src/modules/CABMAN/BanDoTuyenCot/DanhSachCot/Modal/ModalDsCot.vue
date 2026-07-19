<template src="./ModalDsCot.html"></template>
<script>
export default {
  name: "ModalDsCot",
  props: ["modalId"],
  computed: {},
  created: async function () {  
 
  },
  watch:{
   isTuyen: function(val){
    if(val)
    this.onChangeToQL()
   }
  },
  methods: {
    focusMyElement: async function () {
      try{
      this.$root.showLoading(true)
      await this.getTTVT();
      }catch(er){}
      finally{
         this.$root.showLoading(false)
      }
    },
    getTTVT: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/dsttvt_theonv"
        );
        this.dsTTVT = rs.data;
      } catch (error) {
      } finally {
      }
    },
    getToQL: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/ds_to_kythuat_theonv/" +
            this.dataSelected.ttvt
        );
        this.dsToQL = rs.data;
      } catch (error) {
      } finally {
      }
    },
    getdsTuyenCot: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/ds_tuyencot/" +
            this.dataSelected.toql
        );
        this.dsTuyenCot = rs.data       
      } catch (error) {
      } finally {
      }
    },
    onChangeTTVT: async function () {
      await this.getToQL();
    },
    onChangeToQL: async function () {
      await this.getdsTuyenCot();
    },
     grid_selectedRowChanged: async function(dataItem){
      this.cot_id= dataItem.COT_ID;  
      this.cotSelected =  dataItem;
    },
    timKiem:async function(){
    try {

        this.$root.showLoading(true)
         let data={
    "tuyen_cot_id":null,
    "to_kt_id":null,
    "query":this.querys
}
        var rs = await this.$root.context.post(
          "/web-cabman/bando_tuyencot/tra_cuu_ds_cot", data
        );
        this.dscot = rs.data       
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    tracuuCot:async function(){
      try {
          this.$root.showLoading(true)
        let data={
    "tuyen_cot_id":this.dataSelected.tuyen_id,
    "to_kt_id":this.dataSelected.toql,
    "query":""
}
        var rs = await this.$root.context.post(
          "/web-cabman/bando_tuyencot/tra_cuu_ds_cot", data
        );
        this.dscot = rs.data       
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    onChangeTuyen:async function(){

    },
     chonCot:async function(){
       this.$emit('xacnhan',this.cotSelected);
       this.$bvModal.hide(this.modalId)
    },

     huybo:async function(){
     this.cot_id = null
    }

  },
  data:function() {
    return {
      dsTTVT: [],
      dsToQL: [],
      dsTuyenCot:[],
      dataSelected:{
        ttvt:0,
        toql:null,
        tuyen_id:null      
      },
      dscot:[],
      isTuyen:false,
      tuyencot_id:null,
      querys:"",
      cot_id:0,
      cotSelected:null
    };
}
};
</script>
<style>
div#popupDsCot___BV_modal_outer_{
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disabled {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>