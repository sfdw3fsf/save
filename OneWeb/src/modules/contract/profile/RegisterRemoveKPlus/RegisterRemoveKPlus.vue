<template src="./RegisterRemoveKPlus.html"></template>
<style scoped src="./RegisterRemoveKPlus.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import RegisterRemoveKPlusAPI from './RegisterRemoveKPlusAPI'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required, minValue, maxValue, maxLength } from 'vuelidate/lib/validators'

export default {
  components: { BssErrorMarker, BssRequiredMarker,breadcrumb },
  name: 'RegisterRemoveKPlus',  
  
  mounted () {    
    $("#iptvaccount").focus();
    //this.test();
  },
  destroyed () {},
  computed: {
  },
  validations: {
    iptvaccount: {
        required
      }    
  },
  data () {
    return {      
      header: {
        title: 'TRA CỨU THÔNG TIN TÀI KHOẢN MYTV',
        list: [          
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Đăng ký / Hủy K+ addon', link: { name: 'Ui.cards' } },
        ]
      },
      form: {
        //thông tin tài khoản        
        iptvpw: null,
        kplusInfo: null,//xem lại hình thức thanh toán
        status: null,
        stbid: null, //serial no
        mac: null,
        cateName: null,//đối tượng KH
        contractCode: null,
        contractSignDate: null,
        dateBill: null,
        packageName: null, //gói cước
        portDSL: null,
        deviceName: null,

        //thông tin liên hệ
        name: null,
        birthday: null,
        sex: null,
        areaName: null, //tỉnh thành phố
        idno: null, 
        //chưa có số giấy tờ, số GT (2 trường ko có thông tin)

        firstAdd: null,
        telNo: null,
        faxNo: null,
        email: null,
        //chưa có payTV
      },
      iptvaccount: null,  
      payTV:0,    
      lblVMP:null,
    }
  },
  methods: {
    TimThuebaoMytv() {      
      
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      }      
      
      let data = {
        "iptvaccount": this.iptvaccount
      }
      try {
        this.loading(true);
        RegisterRemoveKPlusAPI.get_subscriberinfo(this.axios, data).then(
        (response) => {             
            if(response && response.data && response.data.data && response.data.errorCode==0 ) {            
              let formData=response.data.data;            
              for (let key in formData) {
                this.$set(this.form, key, formData[key]);
              }
              if (formData.fromSys=="1") this.lblVMP ="Hệ thống Mytv VMP";
              else this.lblVMP ="Hệ thống Mytv thường";


              RegisterRemoveKPlusAPI.get_getStatusBlockPayTV(this.axios, data).then(
              (response) => {                        
                  if(response && response.data && response.data.data && response.data.errorCode==0  ) {            
                    this.payTV=response.data.data.getStatusBlockPayTVResult.payTV;
                    
                  }          
                }
              );            
            } 
            else {             
              if(response && response.data && response.data.data && response.data.errorCode==0 ) {
                this.$toast.error('Có lỗi '+response.data.faultString);
              }
              else {
                this.$toast.error('Không tìm thấy thông tin!');
              }
              this.resetForm();
            }                   
            this.loading(false);
          }
        );
      }
      catch(ex){
        
        this.loading(false);
      }
       
    },    
    DangkyKplus() {      
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      }
      
      let data = {
          "kplusDTO": {
            "method_FLG": 1,
            "price": 0,
            "reason": "",
            "subpack": "",
            "username": this.iptvaccount
          }
        }
      this.loading(true);
      RegisterRemoveKPlusAPI.post_registkplus(this.axios, data).then(
      (response) => {
          
          if(response && response.data && response.data.data && response.data.errorCode=="0" ) {            
            this.$toast.success('Đăng ký thành công K+', 'title');
          } 
          else {
            if (response && response.data && response.data.data && response.data.faultString!=""){
              this.$toast.error(response.data.faultString);
            }
            else
              this.$toast.error('Có lỗi khi kích hoạt K+ Add-on lên VASC');
          }                  
          this.loading(false);
        }
      );
    },
    HuyDangkyKplus() {
      this.$v.$touch();
      if (this.$v.$invalid) {
        return;
      }

      let data = {
          "kplusDTO": {
            "method_FLG": 2,
            "price": 0,
            "reason": "",
            "subpack": "",
            "username": this.iptvaccount
          }
        }
      this.loading(true);
      RegisterRemoveKPlusAPI.post_registkplus(this.axios, data).then(
      (response) => {          
          if(response && response.data && response.data.data && response.data.errorCode==0 ) {            
            this.$toast.success('Hủy thành công K+', 'title');
          } 
          else {
            this.$toast.error('Có lỗi khi kích hoạt K+ Add-on lên VASC');
          }               
          this.loading(false);
        }
      );
    },
    resetForm() {
      for (let key in this.form) {        
        this.$set(this.form, key, null);
      }
      this.payTV=0;
      this.lblVMP=null;
    },
    txtMaTB_KeyDown(event) {         
      if (event.target.value) this.TimThuebaoMytv();
      else this.$toast.error('Vui lòng nhập mã thuê bao.');
    },
    
  }
}
</script>
