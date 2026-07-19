<template src="./index.template.html"></template>
<script>
import EventBus from "../../../../utils/eventBus";
export default {
  data: function () {
    return {
    thietbi_id : 0,
    mats : "",
    ten_dv : "",
    txtSerial:"",
    txtMats:"",
    vattu_id : 0,
    ten_dv : "",
    donvi_id : 0,
    dsThietBi:[],
    dsVatTu:[],
    maVT:'',
    chkKyHieu:true,
    chkSerial:false,
    chkMaVT:false,
    isDisableMaTS:false,
    isDisableSerial:true,
    isDisableVatTu:true,
    currentItem: {}
    };
  },
  props: ["modalId"],
  computed: {},
  watch: {
   chkKyHieu: function (val){
    if(val)
      this.Hienthi(1)
   },
   chkSerial: function (val){
     if(val)
      this.Hienthi(2)
   },
   chkMaVT: function (val){
      if(val)
      this.Hienthi(3)
   },
  },
  methods: {
    //#region "Nút bấm"
    focusMyElement:async function(){
    this.dsVatTu = await this.load_cb_LOAIVH()

    },
    hiddenForm:  function (){
    this.dsThietBi = []
    this.dsVatTu =[]
    this.txtSerial=""
    this.txtMats=""
    this.currentItem = {}
    this.$bvModal.hide(this.modalId)
    },
    Hienthi(a)
        {
            if (a == 1)
            {
                this.txtSerial = "";
                this.isDisableSerial = true;
                this.isDisableVatTu = true;
                this.isDisableMaTS = false;
                this.chkSerial = false;
                this.chkMaVT = false;

            }
            if (a == 2)
            {
                this.txtMats= "";
                this.isDisableMaTS = true;
                this.isDisableVatTu = true;
                this.isDisableSerial = false;
                this.chkMaVT = false;
                this.chkKyHieu = false;

            }
            if (a == 3)
            {
                this.txtMats = "";
                this.txtSerial = "";
                this.isDisableMaTS = true;
                this.isDisableSerial = true;
                this.isDisableVatTu = false;
                this.chkKyHieu = false;
                this.chkSerial = false;

            }
    },
    phutro_lay_ds_theo_tbi: async function(ma_vt,kieu){
      try{
       let data ={
         ma_vt:ma_vt,
         kieu:kieu
       }
       let response = await this.$root.context.get(`/web-ecms/quanlythietbiphutro/phutro_lay_ds_theo_tbi`, data)
       return response.data
      }catch (err) {
        return null
      }
    },
    load_cb_LOAIVH: async function (){
     try{
       let response = await this.$root.context.get(`/web-ecms/baoduong-phutro/lay_ds_vattu`)
       return response.data
      }catch (err) {
        return null
      }
    },
    tsbtnTimKiem_Click: async function (){
      try
            {
              this.$root.showLoading(true)
                let dt1 = [];
                if (this.chkKyHieu)
                {
                    dt1 = await this.phutro_lay_ds_theo_tbi(this.txtMats, 1);
                }
                else if (this.chkSerial)
                {
                    dt1 = await this.phutro_lay_ds_theo_tbi(this.txtSerial, 2);
                }
                else if (this.chkMaVT)
                {
                  dt1 = await this.phutro_lay_ds_theo_tbi(this.maVT, 3);
                }
                if (dt1 != null)
                {
                   this.dsThietBi = [...dt1];
                   
                }
            }
            catch (ex)
            {
              console.log(ex)
              this.$root.toastError("Tìm kiếm thất bại");
            }
            finally{
               this.$root.showLoading(false)
            }
    } ,
    tsbtnChapNhan_Click: function(){
      EventBus.$emit('chapNhanTraCuu',this.currentItem)
      this.hiddenForm()
    } ,  
    gridThietBi_rowSelected: function(args){
      if(args!=null){
      this.currentItem =   args.data
      this.thietbi_id = args.data.THIETBI_ID;
      this.mats = args.data.MA_TBI;
      this.ten_dv = args.data.TEN_DV;
      this.donvi_id = args.data.DONVI_ID;
      this.vattu_id = args.data.VATTU_ID;
      }
    }
  }
};
</script>
<style>
.modal-fullscreen .modal-dialog{
    max-width: 100%;
    margin: 0;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    height: 100vh;
    display: flex;
    position: fixed;
    z-index: 100000;
  }
div#formTraCuuTBi___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
  position: unset !important;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>