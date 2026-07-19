<template src="./GiaoviecNV.html"></template>
<style scoped src="./GiaoviecNV.scss"></style>
<script>
  import ActionTop from '@/components/kylq_components/ActionTop.vue'
import API from "../../api.js";
 export default {
      name:'GiaoviecNV',
      props:{
        phanvung_id: {
            type: Number,
            default: 0
        },
        donvi_id: {
            type: Number,
            default: 0
        }
      },
      components:{
          ActionTop,
      },
      data(){
          return {
              DsNhanvien:[]
          }
      },
      methods:{
          showModal() {
              this.$refs["giaoviecNVModal"].show()
          },
          hideModal() {
              this.$refs["giaoviecNVModal"].hide()
          },
          async handleShowModal(){
              this.lay_ds_nhanvien()
              
          },
          selectedItems(data){
            this.items = data
          },
          async lay_ds_nhanvien(){
                  this.loading(true)
                  let input = {
                    "p_phanvung_id": this.phanvung_id,
                    "p_donvi_id": this.donvi_id
                  }
                  let data = this.GetDataList(await API.lay_ds_nhanvien_theo_dv(this.axios,input));
                   this.DsNhanvien = data
                   this.loading(false)
          },
            async Ghilai_click() {
                this.$emit("xacnhan", {...this.items});
                this.$refs["giaoviecNVModal"].hide()
            },
           GetDataList: function (response) {
            console.log(response)
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data
            } else if (response && response.data && response.data.error_code === 'BSS-00000204') {} else {
                this.$root.$toast.error('Lấy dữ liệu không thành công')
            }
            return []
        },
      }
  }
  </script>