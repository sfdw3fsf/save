<template src="./SearchHistoryVNP.html"></template>
<style src="./SearchHistoryVNP.scss"></style>
<script>
import SearchHistoryVNPAPI from './SearchHistoryVNPAPI'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
    components: { breadcrumb },
    name: 'SearchHistoryVNP',
  mounted () {
    this.showModal();
    //load ds loại hình thuê bao
  },
  data () {
    return {
      header: {
        title: 'Tiếp nhận yêu cầu khóa máy cố định',
        list: [
          {
            name: 'Lập hợp đồng',
            link: { name: 'Ui.cards' }
          },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          },
        ]
      },
      TxtSoMay: "",
      TuNgay: "",
      DenNgay: "",
      checkTuNgay: false,
      checkDenNgay: false,
      thongtingoi: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
          columns: [
          { fieldName: 'STATUS_FIBER', headerText: 'Trạng thái', allowFiltering: true, cssClass: 'text-right'},
          { fieldName: 'MSISDN', headerText: 'Số máy/Acc', allowFiltering: true },
          { fieldName: 'PACKAGE_ID', headerText: 'Tên gói', allowFiltering: true },
          { fieldName: 'TIME_START', headerText: 'Ngày hiệu lực', allowFiltering: true },
          { fieldName: 'KEY_MSISDN', headerText: 'Chủ nhóm', allowFiltering: true },
        ],
      },
      lichsutacdong: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
          columns: [
          { fieldName: 'MSISDN', headerText: 'Số máy/Acc', allowFiltering: true, cssClass: 'text-right'},
          { fieldName: 'PACKAGE_NAME', headerText: 'Tên gói', allowFiltering: true },
          { fieldName: 'TIME_START', headerText: 'Ngày hiệu lực gói', allowFiltering: true },
          { fieldName: 'STATUS', headerText: 'Nội dung', allowFiltering: true },
          { fieldName: 'REGIS_DATE', headerText: 'Ngày ĐK', allowFiltering: true },
          { fieldName: 'REGIS_USER', headerText: 'Người ĐK', allowFiltering: true },
        ],
      },
       config: {
                dateConfig: {
                    value: new Date(),
                    format: "dd/MM/yyyy",
                    max: new Date()
                }
            },
    }
  },
  methods: {
      showModal() {
        this.$refs['my-modal'].show()
      },
      hideModal() {
        this.$refs['my-modal'].hide()
      },
    btnSearchClicked() {
      this.Clear();
      var pdata = {};
      if(this.TxtSoMay == "" || this.TxtSoMay == null){
        this.$toast.error('Bạn chưa nhập Số máy/Acc tìm kiếm')
      }
      else if(this.TuNgay > this.DenNgay && this.checkTuNgay == true && this.checkDenNgay == true){
        this.$toast.error('Từ ngày phải nhỏ hơn đến ngày')
      }
      else {
      pdata.ma_tb = this.TxtSoMay.trim();
      if(this.checkTuNgay == true){
            pdata.from_date = this.format_date(this.TuNgay);
        }
        else pdata.from_date = "";
        if(this.checkDenNgay == true){
            pdata.to_date = this.format_date(this.DenNgay);
        }
        else pdata.to_date = "";
      console.log(pdata);
      SearchHistoryVNPAPI.tracuu(this.axios, pdata).then((response) => {
        if(response && response.data) {
            console.log(response.data.data);
            if(response.data.data == null){
              this.$toast.error('Không tìm thấy thông tin về thuê bao')
            }
            else if(response.data.data.chitiet_goi == null || response.data.data.lsdk_goi == null || response.data.data == null){
              this.$toast.error('Không tìm thấy thông tin về thuê bao')
            }
            else {
            this.lichsutacdong.list = response.data.data.lsdk_goi;
            // this.thongtingoi.list = response.data.data.chitiet_goi;
            // make sure all keys in the item are upper case
            this.thongtingoi.list = response.data.data.chitiet_goi.map(item => {
              let upperCaseItem = {};
              for (let key in item) {
                upperCaseItem[key.toUpperCase()] = item[key];
              }
              return upperCaseItem;
            });
            }
        }
      });
      }
    },
    Clear() {
      this.lichsutacdong.list = null;
      this.thongtingoi.list = null;
    },
    format_date(value){
         if (value) {
           return moment(String(value)).format('DD/MM/YYYY')
          }
    },
  },
};
</script>
