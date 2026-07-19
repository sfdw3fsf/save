<template src="./template.html"></template>

<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import ModalChiTiet from '@/modules/QLVT/DoiSoatDuLieuNo/parts/ModalChiTiet.vue/ModalChiTiet.vue'
import Vue from 'vue'
import { Icon} from '@iconify/vue2'
  export default {
    components: {
      breadcrumb,
      Icon,
      ModalChiTiet
    },
    data() {
      return {
        doisoat: 'ma_tt',
        hienthi: 'lech',
        nguoithuchien: '',
        tbMain_columns: [
          {fieldName: 'stt', headerText: 'STT', width: '70px'},
          {fieldName: 'ma_tt', headerText: 'Mã Thanh toán ĐHSX'},
          {fieldName: 'tongno_hdsx', headerText: 'Tổng nợ ĐHSX'},
          {fieldName: 'kycuoc', headerText: 'Kỳ cước ĐHSX'},
          {fieldName: 'matt_ccbs', headerText: 'Mã Thanh toán CCBS'},
          {fieldName: 'tongno_ccbs', headerText: 'Tổng nợ CCBS'},
          {fieldName: 'kycuoc_ccbs', headerText: 'Kỳ cước CCBS'},
          {fieldName: 'lech', headerText: 'Tiền lệch'},
          {
            fieldName: 'action',
            headerText: 'Thao tác',
            template: this.colThaotac(this),
            width: '100px'
          }
        ],
        tbMain_dataSource: [
          {stt: 1, ma_tt: 'HNI124144', tongno_hdsx: '1.000.000', kycuoc: '20231101', matt_ccbs: 'HNI124144', tongno_ccbs: '990.000', kycuoc_ccbs: '20231101', lech: '10.000'},
          {stt: 2, ma_tt: 'HNI124122', tongno_hdsx: '800.000', kycuoc: '20231101', matt_ccbs: 'HNI124144', tongno_ccbs: '850.000', kycuoc_ccbs: '20231101', lech: '-50.000'},
        ],
        chuky_options: [
          {id: 1, text: '112023'}
        ],
        chuky: 1,
      }
    },
    methods: {
      colThaotac(parent){
        return function() {
          return {
            template: Vue.component('colThaotac',{
              template: `
              <div class="btn-group" style="display: flex; align-items: center; justify-content: space-around; ">
                <div @click="showModalChiTiet" class="btn-table-DoiSoatDuLieuNo" style=" 
                display: flex; align-items: center; justify-content: center; 
                " title="Xem chi tiết"><Icon icon="mdi:eye" width="16" height="16" /></div>
                <div class="btn-table-DoiSoatDuLieuNo" style="
                display: flex; align-items: center; justify-content: center; 
                " title="Đồng bộ"><Icon icon="uil:sync" width="16" height="16" /></div>
              </div>
              `,
              components: {
                Icon
              },
              methods: {
                showModalChiTiet(){
                  parent.showModalChiTiet();
                }
              }
            })
          }
        }
      },
      showModalChiTiet(){
        this.$refs.ModalChiTiet.showModal();
      }
    },
    async mounted() {
      console.log(this.$root.token);
      this.nguoithuchien = this.$root.token.getUserName();
    },
  }
</script>

<style lang="scss">
  .btn-table-DoiSoatDuLieuNo{
    // background-color: #fff;
    // border: 1px solid #ccc;
    height:30px;
    width:30px;
    border-radius: 50%;
    cursor: pointer;
    margin: 0 2px;
    color: #0176FF;
    padding: 0 4px;
    transition: all 0.3s ease-in-out;
    &:hover{
      background-color: #ECF5FF;
      border: 1px solid #68B2FF;
      color: #035FCC;
    }
  }
</style>