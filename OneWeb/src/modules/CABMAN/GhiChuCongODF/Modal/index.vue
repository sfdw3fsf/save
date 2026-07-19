<template src="./index.html"></template>

<script>
import {required} from 'vuelidate/lib/validators'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
export default {
  name: "GhiChuCongODF",
  props: ["modalId","odf_id"],
  components: { BssErrorMarker, BssRequiredMarker},
  computed: {  
  },
  watch:{
    'ghichu.ttvt_id':async function(val){
      if(val!=null)
      await this.getToQL()
    },
    'ghichu.to_kt':async function(val){
      if(val!=null)
      await this.getTramTbi()
    }
  },
  methods: {
    focusMyElement: async function () {
      try{
      this.$root.showLoading(true)
         await this.getGhiChuOdf()
         await this.getTTVT()
         await this.getToQL()
         await this.getTramTbi()
        
      
      }catch(er){}
      finally{
         this.$root.showLoading(false)
      }
    },
    hiddenModal: function(){
     Object.assign(this.$data, this.$options.data.call(this));
    },
    getTTVT: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/donvi/ds-ttvt-phanvung"
        );
        if(rs.data!=null){
        this.dsTTVT = rs.data
        if(this.ghichu.ttvt_id ==null)
          this.ghichu.ttvt_id = this.dsTTVT[0].DONVI_ID
        }
      } catch (error) {
      } finally {
      }
    },
    getToQL: async function () {
      try {
        if(this.ghichu.ttvt_id!=null){
        var rs = await this.$root.context.get(
          "/web-cabman/donvi/ds-to-vienthong/" +
            this.ghichu.ttvt_id
        );
        if(rs.data!=null){
        this.dsToQL = rs.data
        if(this.ghichu.to_kt ==null)
          this.ghichu.to_kt = this.dsToQL[0].DONVI_ID
        }
        }
      } catch (error) {
      } finally {
      }
    },
    getTramTbi: async function () {
      try {
         if(this.ghichu.to_kt!=null){
        var rs = await this.$root.context.get(
          "/web-cabman/donvi/ds-tramtb/" +
           this.ghichu.to_kt
        );
        this.dsTramTbi = rs.data;
        if(this.ghichu.donvi_id==null)
        this.ghichu.donvi_id=this.dsTramTbi[0].DONVI_ID
         }
      } catch (error) {
      } finally {
      }
    },
    getGhiChuOdf: async function(){
        try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.get(
          "/web-cabman/mang-truyen-dan/ds-ghichu-odf/" +
           this.odf_id
        );
        this.dsGhiChu = rs.data;
        if(rs.data !=null && rs.data.length>0){
          this.ghichu={
            odf_id:this.odf_id,
            thutu:this.dsGhiChu[0].THUTU,
            tu_vitri:this.dsGhiChu[0].TU_VITRI,
            den_vitri: this.dsGhiChu[0].DEN_VITRI,
            donvi_id:this.dsGhiChu[0].DONVI_ID,
            culy:this.dsGhiChu[0].CULY,
            ttvt_id :this.dsGhiChu[0].TTVT_ID,
            to_kt : this.dsGhiChu[0].TOVT_ID
          }         
         
          }
          
      } catch (error) {
      } finally {
          this.$root.showLoading(false)
      }
    },
    getChitietGhiChuOdf: function(data){
     try {
          if(data !=null){
            this.action =1
           this.ghichu={
            odf_id:this.odf_id,
            thutu:data.THUTU,
            tu_vitri:data.TU_VITRI,
            den_vitri: data.DEN_VITRI,
            donvi_id:data.DONVI_ID,
            culy:data.CULY,
            ttvt_id:data.TTVT_ID,
            to_kt : data.TOVT_ID
          }
      }
        Object.assign(this.currentObj ,this.ghichu);
        this.setDisableBtn(false,false,false,false)   
      } catch (error) {
      } finally {
          this.$root.showLoading(false)
      }
    },
    onChangeTTVT: async function () {
      await this.getToQL();
    },
    onChangeToQL: async function () {
      await this.getTramTbi();
    },
    ghichu_selectedRowChanged:function(data){
    this.getChitietGhiChuOdf(data)
    },
     setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa) {
      this.isDisableBtnNhapMoi = isNhapmoi;
      this.isDisableBtnGhiLai = isGhilai;
      this.isDisableBtnHuy = isHuy;
      this.isDisableBtnXoa = isXoa;
    },
        //#region CRUD 
    themMoi: async function(){
       try{
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(
          "/web-cabman/mang-truyen-dan/them-ghichu-odf", this.ghichu
        );
        if(rs.error_code=="BSS-00000000"){
             this.$root.toastSuccess('Thêm mới ghi chú odf thành công')
              this.getGhiChuOdf()
              this.setDisableBtn(false, false, false, false)
        }else{
         this.$root.toastError('Thêm mới ghi chú odf thất bại')}
       }catch(err){
         this.$root.toastError(err.response.data.message_detail)
       }
       finally{
         this.$root.showLoading(false)
       }
    },
    capNhat: async function(){
      try{

          this.$root.showLoading(true)
          let rs = await this.$root.context.post(
          "/web-cabman/mang-truyen-dan/capnhat-ghichu-odf", this.ghichu
        );
  
        if(rs.error_code=="BSS-00000000"){
            this.$root.toastSuccess('Cập nhật ghi chú odf thành công')
             this.getGhiChuOdf()
             this.setDisableBtn(false, false, false, false)
        }else{
           this.$root.toastError('Cập nhật ghi chú odf thất bại')
        }
       }catch(err){
         console.log(err)
         this.$root.toastError(err.response.data.message_detail)
       }
       finally{
         this.$root.showLoading(false)
       }
    },
    ghiLai: async function(){
        if(!this.validateData()){
        if(this.action == 0)
        await this.themMoi()
        else
        await this.capNhat()
        this.$emit('ghiLaiGhiChuOdf')
        }
        
    },
    huyBo: async function(){
      //  if(this.action ==0){
      //  this.clearForm()
      //   this.setDisableBtn(false,false,false,true)
      //   }
      //  else{
       Object.assign(this.ghichu,this.currentObj );
       this.setDisableBtn(false,false,false,false)   
       //} 
    },
    xoa: async function(){
    if(this.dsGhiChu.length > 0)
    this.modalXoa=true   
    else
    this.$root.toastError('Không tồn tại ghi chú để xóa')
    },
    xacNhanXoa: async function(){
     try{
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(
          "/web-cabman/mang-truyen-dan/xoa-ghichu-odf", this.ghichu
        );
        if(rs.error_code=="BSS-00000000"){
           this.$root.toastSuccess('Xóa ghi chú odf thành công')
          this.getGhiChuOdf()
          this.setDisableBtn(false, false, false, false)
        }else{
          this.$root.toastError('Xóa ghi chú odf thất bại')
        }
       }catch(err){
        this.$root.toastError(err.response.data.message_detail)
       }
       finally{
         this.$root.showLoading(false)
       }
    },
    clearForm: async function(){
      Object.assign(this.currentObj, this.ghichu);
         this.ghichu={
          thutu:0,
          tu_vitri:0,
          den_vitri:0,
          donvi_id:null,
          culy:0,
          odf_id:this.odf_id,
          ttvt_id : null,
          to_kt : null
          },
         this.setDisableBtn(true,false,false,true)
         this.$v.$reset()
         this.action=0
    },
    validateData: function(data){
     try{
    this.$v.$touch();
    let errorMessage = []
    if (this.$v.$invalid) {
    if(!this.$v.ghichu.thutu.required)
        errorMessage.push("Trường thứ tự bắt buộc có dữ liệu.")
     if(!this.$v.ghichu.tu_vitri.required)
         errorMessage.push("Trường từ vị trí  bắt buộc có dữ liệu.")
    if(!this.$v.ghichu.den_vitri.required)
         errorMessage.push("Trường đến vị trí  bắt buộc có dữ liệu.")
    if(!this.$v.ghichu.culy.required)
         errorMessage.push("Trường cự ly  bắt buộc có dữ liệu.")
   if(!this.$v.ghichu.ttvt_id.required)
         errorMessage.push("Trường trung tâm viễn thông  bắt buộc có dữ liệu.")
    if(!this.$v.ghichu.to_kt.required)
         errorMessage.push("Trường tổ kỹ thuật  bắt buộc có dữ liệu.")
    if(!this.$v.ghichu.donvi_id.required)
         errorMessage.push("Trường trạm thiết bị  bắt buộc có dữ liệu.")          
     this.$root.toastError(errorMessage.join('\n'))
      return true   
      }
     return false
    }
    catch(e){
      console.log(e)
    }
     }
         //#endregion
  },
  data: function () {
    return {
      dsTTVT: [],
      dsToQL: [],
      dsTramTbi: [],
      dsGhiChu:[],
    isDisableBtnNhapMoi: false,
    isDisableBtnGhiLai: true,
    isDisableBtnHuy: true,
    isDisableBtnXoa: true,
    modalXoa:false,
    ghichu:{
      thutu:0,
      tu_vitri:0,
      den_vitri:0,
      donvi_id:null,
      culy:0,
      odf_id:-1,
      ttvt_id:null,
      to_kt:null,
    },
    currentObj:{
      thutu:0,
      tu_vitri:0,
      den_vitri:0,
      donvi_id:null,
      culy:0,
      odf_id:-1
    },
    action:0
    };
  },
   validations: {
     ghichu:{
        thutu:{required},
        tu_vitri:{required},
        den_vitri:{required},
        donvi_id:{required},
        ttvt_id:{required},
        to_kt:{required},
        culy:{required}
        }
    },
};
</script>
<style>
div#popupTCTTTB___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
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