<template src="./Lichsuphieugiao.html"></template>
<style scoped src="./Lichsuphieugiao.scss"></style>
<script>
  import ActionTop from '@/components/kylq_components/ActionTop.vue'
import API from "../../api.js";
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
      name:'Lichsuphieugiao',
      props:{
        baohong_id: {
            type: Number,
            default: 0
        },
        phanvung_id: {
            type: Number,
            default: 0
        }
      },
      components:{
          ActionTop,
      },
      data(){
          return {
              DsLichsu:[],
              DsSanpham:[],
              DsQuyen:[],
          }
      },
      methods:{
          showModal() {
              this.$refs["lichsugiaophieuModal"].show()
          },
          hideModal() {
              this.$refs["lichsugiaophieuModal"].hide()
          },
          async handleShowModal(){
              this.lay_lichsu_phieugiao()
              
          },
          async lay_lichsu_phieugiao(){
                  this.loading(true)
                  let input = {
                    "p_phanvung_id": this.phanvung_id,
                    "p_json_baohong_id": JSON.stringify([{"baohong_id": this.baohong_id}])
                  }
                  let data = this.GetDataList(await API.sp_tracuugiaophieu_bh_toanquoc(this.axios,input));
                   this.DsLichsu = data
                //   console.log(this.DsLichsu)
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