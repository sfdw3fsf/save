<template src="./ModalKCCot.html"></template>
<script>
import axios from 'axios';
import EventBus from '@/utils/eventBus'
import {
  minLength,
  maxLength,
} from "vuelidate/lib/validators";
export default {
  name: "ModalKCCot",
  props: ["modalId","data"],
  created: async function () {
  },
  methods: {
      focusMyElement: async function () {
      try{
      this.$root.showLoading(true)
       this.$v.$reset()
      if (this.data != null) {
      this.cot_id = this.data.cot_id;
      this.cotsau_id= this.data.cotsau_id;
      this.cot_n=await this.chitietCot( this.cot_id);
      this.cot_d=await this.chitietCot( this.cotsau_id);
      this.value=this.cot_n.KC_COTSAU
    
      }
      }catch(er){}
      finally{
         this.$root.showLoading(false)
      }
    },
    xacNhan: async function () {
      try {
         this.$root.showLoading(true);
         if(!this.validateData()){
        var rs = await this.$root.context.post(
          "/web-cabman/bando_tuyencot/sua_khoang_cach",
          {
            cot_id: this.cot_id,
            kc_cotsau: this.value,
          }
        );
       if (rs.error_code === "BSS-00000000") {
          this.$toast.success(
            "Cập nhật khoảng cách thành công"
          );         
          this.$bvModal.hide(this.modalId);
           EventBus.$emit('NAP_GIAN_DO',false,true)
          }else
           this.$toast.error(
            "Cập nhật khoảng cách thất bại"
          );
         }
      } catch (error) {
        this.$toast.error("Có lỗi xảy ra!");
      } finally {
      this.$root.showLoading(false);
      }
    },
    chitietCot: async function (cot_id) {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/chitiet_cot_theo_id/" + cot_id
        );
        return rs.data;
      } catch (error) {
        return null;
      } finally {
      }
    },
    tinhKc: async function () {
      try {
        this.$root.showLoading(true);
        var rs = await this.$root.context.post(
          "/web-cabman/bando_tuyencot/kc_theo_toado",
          {
            vido1: this.cot_n.VIDO,
            kinhdo1: this.cot_n.KINHDO,
            vido2: this.cot_d.VIDO,
            kinhdo2: this.cot_d.KINHDO,
          }
        );
        if(rs.data!=null)
        this.value = Number(rs.data.toFixed(0));
        else
        this.value=0
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getKc: async function () {
      try {
        let data= {
            cot_id: this.cot_id,
          }
        let rs = await this.$root.context.post(
          "/web-cabman/bando_tuyencot/lay_kc_cotsau",data        
        );
        this.value = rs.data.KC_COTSAU;
      } catch (error) {
      } finally {
      }
    },
    NumbersOnly(evt) {
      evt = (evt) ? evt : window.event;
      var charCode = (evt.which) ? evt.which : evt.keyCode;
      if ((charCode > 31 && (charCode < 48 || charCode > 57))) {
        evt.preventDefault();
      } else {
        return true;
      }
      },
      validateData(data) {
      this.$v.$touch();
      let errorMessage = [];
       if (!this.$v.value.maxLength) {
        errorMessage.push("Trường Khoảng cách dữ liệu vượt quá độ dài cho phép.");
         this.$refs['kc'].focus()
      }
     
      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join("\n"));     
        return true;
      } else {
        return false;
      }
    },
  },
  validations: {
    
      value: { maxLength:maxLength(5)  },
      
    
  },
  data: function () {
    return {
      value: 0,
      cot_id: 9298,
      cotsau_id: 0,
      isSuccess: true,
      cot_n:Object,
      cot_d:Object,
      vido1:0,
      kinhdo1:0,
      vido2:1,
      kinhdo2:1
    };
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
a.disabled {
  pointer-events: none;
  cursor: default;
}
</style>