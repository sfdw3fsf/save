<template src="./GuiSMSTienTrinhBH.html"></template>
<script>
import Vue from 'vue';
import api from '../../api';
import apiHelper from '../../api.helper.js';
import _ from 'lodash'
import BssErrorMarker from '@/components/BssErrorMarker'

export default {
  name: 'GuiSMSTienTrinhBH',
  components: {
    BssErrorMarker
  },
  props: {
    idModal:{
      type: String,
      default:"popupGuiSMSTienTrinhBH"
    },
    nhom_id: {
      type: Number | String,
      default: 0,
    },
    baohong_id: {
      type: Number | String,
      default: 0,
    },
    tientrinhbh_id: {
      type: Number | String,
      default: 0,
    },
    ma_tb: {
      type: String,
      default: "",
    },
    noidung: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      columnsDanhBa: [
        { fieldName: "SO_DT", headerText: "Số điện thoại", textAlign: "left", width: "200px" },
        { fieldName: "TEN_NV", headerText: "Tên nhân viên", textAlign: "left", width: "200px" },
        { fieldName: "TEN_DV", headerText: "Tên đơn vị", textAlign: "left", width: "200px" },
      ],
      columnsDSTinNhan: [
        { fieldName: "NGUOI_CN", headerText: "Người gửi", textAlign: "left", width: "200px" },
        { fieldName: "NGAY_CN", headerText: "Ngày gửi", textAlign: "left", width: "200px" },
        { fieldName: "SO_DT", headerText: "Số điện thoại", textAlign: "left", width: "200px" },
        { fieldName: "NOIDUNG", headerText: "Nội dung", textAlign: "left", width: "200px" },
      ],
      gridDanhBa: [],

      gridSmsSent: [],
      txtNoiDung: '',
      txtSdt: "",
      validateTextSend: false,

    }
  },
  methods: {
    async initModal() {
   
      try {
        const param = {
          p_nhom_id: this.nhom_id,
          p_baohong_id: this.baohong_id, 
          p_hdtb_id: 0,
          p_tientrinhbh_id: this.tientrinhbh_id
        }
        this.$bvModal.show(this.idModal);
        const response = await api.loadSMS(this.axios, param);
        const strResult = apiHelper.getDataFromResponseApiReturnRaise(response);
        const objResult = JSON.parse(strResult);

        this.gridDanhBa  = objResult.DS_DANHBA_TIENTRINH_SMS|| [];
        this.gridSmsSent =  objResult.DS_TT_TIENTRINH_SMS|| [];
        let listSDTdeXuat = objResult.DS_SO_DT.map(e => e.SO_DT) || [];
        if(listSDTdeXuat.length > 0){
          listSDTdeXuat =  _.uniq(listSDTdeXuat);
          this.txtSdt = listSDTdeXuat.join(",");
        }
      } catch (error) {
        console.log(error)
      }

    
    },
    async tsbtnGuiSMS_Click() {
      if(!this.txtNoiDung.length){
        this.$toast.error("Vui lòng nhập nội dung tin nhắn");
        return
      }
      
      if(!this.validatePhoneNumber(this.txtSdt)){
        this.$toast.error("Vui lòng nhập đúng định dạng số điện thoại");
        return
      }

      try {
        const param = {
          p_nhom_id: this.nhom_id,
          p_tientrinhbh_id: this.tientrinhbh_id, 
          p_noidung: this.txtNoiDung,
          p_so_dt: this.txtSdt,
          p_ma_tb: this.ma_tb
        }
        const response = await api.sendSMS(this.axios, param);
        const strResult = apiHelper.getDataFromResponseApiReturnRaise(response);
        if(strResult !== 'ok'){
          this.$toast.error(strResult);
          return
        }
        this.$toast.success("Gửi tin nhắn thành công");
        this.tsbtnXoa_Click();
        await this.initModal();
      } catch (error) {
        console.log(error)
      }

    
    },
    rowDouble_DB_Clicked(){

    },

    tsbtnXoa_Click() {
      this.txtNoiDung = "";
      this.txtSdt = "";
    },

    closeModal(){
      this.txtNoiDung = "";
      this.txtSdt = "";
      this.$bvModal.hide(this.idModal);
    },
    validatePhoneNumber(txtPhone){
      const arrPhone = txtPhone.trim().split(";");    
      // const vietnamesePhoneNumberRegex = /^(\+84|84|0)[1-9]\d{8}$/;
    let sdtError = ""
    const vietnamesePhoneNumberRegex =  /^(\+84|84|0)[1-9]\d{8}$/;
      arrPhone.forEach( phone =>{
        
        if(!vietnamesePhoneNumberRegex.test(phone)){
          sdtError +=  phone
          this.$toast.warning("SĐT không đúng định dạng: " + phone);
        }
      
      })
      return sdtError.length === 0;
    },
  },
  computed:{
    noiDungMaxLength () {
      return 158 - this.ma_tb.length
    },
    noiDungRemainingLength () {
      return this.noiDungMaxLength - this.txtNoiDung.length
    }
  }
}
</script>
