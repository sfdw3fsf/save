<template src="./KetquaCSKH_OB.html"></template>
<style scoped src="./KetquaCSKH_OB.scss"></style>
<script>
  import ActionTop from '@/components/kylq_components/ActionTop.vue'
import api from "../../Api.js";
  //import CoDinh from './CoDinh.vue'
  //import BangRongCoDinh from './BangRongCoDinh.vue'
  //import Gphone from './Gphone.vue'
  //import IMS from './IMS.vue'
  //import KenhThueRieng from './KenhThueRieng.vue'
  //import MegawanMetronet from './MegawanMetronet.vue'
  //import {DichVuVienThong} from '../../ThamSo'
  //import TraCuuVatTuThueBao from '../../../../../QLVT/components/TraCuuVatTuThueBao/TraCuuVatTuThueBao.vue'
  //import TraCuuVatTuThueBao from '@/modules/QLVT/components/TraCuuVatTuThueBao/TraCuuVatTuThueBao.vue'
  //import TraCuuVatTuHDTB from '../../../../../search/subscriber/SearchInstallDetail/popup/TraCuuVatTuHDTB.vue'
  export default {
      name:'KetquaCSKH_OB',
      props:{
        baohong_id: {
            type: Number,
            default: 0
        }
      },
      components:{
          ActionTop,
      },
      data(){
          return {
              DsKetquaCSKH_OB:[],
              DsSanpham:[],
              DsQuyen:[],
          }
      },
      methods:{
          showModal() {
              this.$refs["ketquaCSKH_OBModal"].show()
          },
          hideModal() {
              this.$refs["ketquaCSKH_OBModal"].hide()
          },
          async handleShowModal(){
              this.lay_ds_ls_cskh_ob_theo_baohong()
              
          },
          async lay_ds_ls_cskh_ob_theo_baohong(){
                  this.loading(true)
                  let data = this.GetDataList(await api.lay_ds_ls_cskh_ob_theo_baohong(this.axios,{"baohong_id":this.baohong_id}));
                  this.DsKetquaCSKH_OB = data
                  console.log(this.DsKetquaCSKH_OB)
                   this.loading(false)
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