<template src="./CancelBookingVISAAccount.html">

 
</template>

<style src="./CancelBookingVISAAccount.scss"></style>
<script>

import breadcrumb from '@/components/breadcrumb'
//import API from '../../admin/API/API_Admin'
import API from './API'
import CancelLogin from './CancelLogin.vue'
import CancelMain from './CancelMain.vue'
export default {
  components: { breadcrumb,CancelMain,CancelLogin },
  name: 'CancelBookingVISAAccount',
  mounted () {
   
  },
  //end mounted
  
  destroyed () {},
  data () {
    return {
      is_login: true,
      
    }
  },



  methods:{

    main_login(user){
        console.log(user);
        API.CancelBookingVISAAccount_login(this.axios,user).then((response) => {
        if(response && response.data.error && response.data.error_code === 'BSS-00000000') {
             
            this.is_login = true;
        }
        else {
            alert('Đăng nhập thất bại');
        }
        });
    },

    cancel_visa: async function(acc){
        //console.log(acc);
      var data = {"ma_tb":acc};
      
      this.loading(true);

      await API.fn_kiemtra_hopdong_huyvisa(this.axios,data).then((response) => {
          //console.log(response);
        if(response && response.data.error && response.data.error_code === 'BSS-00000000') {
          this.loading(false);
          this.$confirm('Có hợp đồng đang sử dụng account này. Bạn có chắc muốn hủy đặt chỗ?', 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(async () => {
            data = {"accountName":acc};
            API.unReservedAccount(this.axios,data).then((response) => {
                if(response && response.data.faultString.includes("chưa được đặt chỗ")) {
                  this.$toast.success("Hủy đặt chỗ thành công");
                }
                else{
                  this.$toast.error(response.data.message);
                }
            });
            
          })

          // if (confirm("Có hợp đồng đang sử dụng account này. Bạn có chắc muốn hủy đặt chỗ?")){
          //     data = {"accountName":acc};
          //     //console.log(data1);
          //     API.unReservedAccount(this.axios,data).then((response) => {
          //        //console.log(response);
          //        if(response && response.data.faultString.includes("chưa được đặt chỗ")) {
          //           this.$toast.success("Hủy đặt chỗ thành công");
          //        }
          //        else{
          //           this.$toast.error(response.data.message);
          //        }
          //     });
          // }
        }
          
        else {
          this.$toast.error(response.data.message);
        }
      });
      
    
    }
    

  }
}
</script>
