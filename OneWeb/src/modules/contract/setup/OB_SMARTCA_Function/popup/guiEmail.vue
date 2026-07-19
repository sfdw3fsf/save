<template>
<div id="guiEmail" >
<div class="list-actions-top">
  <ul class="list">
    <li>
      <a href="#" @click.prevent="gui_email"> <span class="icon fa fa-envelope"></span>Gửi Email </a>
    </li>
  </ul>
</div>
<div class="page-content">
  <div class="box-form">
    <div class="row">
      <div class="col-sm-12 col-12">
          <div class="info-row">
            <div class="key w60">Email <k-required-marker /></div>
              <div class="value">
                <input type="text" class="form-control" v-model="popup_1.email" :disabled="false" />
            </div>
          </div>
      </div>
      <!-- <div class="col-sm-12 col-12">
        <div class="info-row">
          <div class="key w60">Ghi chú</div>
            <div class="value">
            <textarea class="form-control" v-model="popup_1.ghi_chu" rows="5"></textarea>
          </div>
        </div>
      </div> -->
    </div>
  </div>
</div>
</div>
</template>
<style>
</style>
<script>
import api from '../API.js'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
export default {
name:'guiEmail',
components:{
  ActionTop,
  KDatePicker,
  KRequiredMarker
},
props: ["dulieu"],
data(){
  return{
    popup_1:{
      email:'',
      fullname:''
    },     
  }
},
mounted() {
  this.initDulieu()
},
methods:{
  async initDulieu(){
    console.log('Dữ liệu popup nhận được:',this.dulieu)
    this.popup_1.email =  this.dulieu.email
    this.popup_1.fullname = this.dulieu.fullname
  },
  async gui_email(){
    console.log('Địa chỉ email gửi',this.popup_1.email)
    if(this.popup_1.email===''){
      this.$toast.error('Nhập Email')
      return
    }
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailPattern.test(this.popup_1.email)) {
        this.$toast.error('Email không hợp lệ');
        return;
      }
    try{
      this.loading(true)
      const input = {
        email: this.popup_1.email,
        fullname: this.popup_1.fullname 
      }
      const response = await api.guiEmail(this.axios, input);
      if (response && response.data && response.data.errorCode === 0) {
          console.log('response.data.faultString',response.data.faultString)
          this.$toast.success('Thành công')
          this.$emit('form-close', true);
      } else {
        this.$toast.error('Thất bại')
      }
    }catch(error){
      this.$toast.error('Đã xảy ra lỗi khi gửi Email');

    }finally{
      this.loading(false)
    }
  },
  huy(){
      this.$emit('form-close', false);
  },
  clearAll(){

  },
}
}
</script>