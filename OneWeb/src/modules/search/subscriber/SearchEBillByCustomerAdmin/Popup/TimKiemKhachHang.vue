<template src="./TimKiemKhachHang.html"></template>
<script>
import API from './API'
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
    name: 'TimKiemKhachHang',
  mounted () {
  },
  data () {
    return {
      header: {
        title: 'Tìm kiếm khách hàng phát hành hóa đơn điện tử tự quản',
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
      tenKH: "",
      MaSoThue: "",
      TuNgay: moment().format("DD/MM/YYYY"),
      DenNgay: moment().format("DD/MM/YYYY"),
      checkTuNgay: false,
      checkDenNgay: false,
      ketquatimkiem: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
          columns: [
          { fieldName: 'stt', headerText: 'Stt', allowFiltering: true, cssClass: 'text-right'},
          { fieldName: 'ma_tt', headerText: 'Mã khách hàng', allowFiltering: true },
          { fieldName: 'fkey', headerText: 'Fkey', allowFiltering: true },
          { fieldName: 'loaihoadon_moi', headerText: 'Mẫu hóa đơn', allowFiltering: true },
          { fieldName: 'seri_moi', headerText: 'Ký hiệu mới', allowFiltering: true},
          { fieldName: 'so_hd_moi', headerText: 'Số hóa đơn mới', allowFiltering: true },
          { fieldName: 'ten_tt', headerText: 'Tên khách hàng', allowFiltering: true },
          { fieldName: 'mst', headerText: 'Mã số thuế', allowFiltering: true},
          { fieldName: 'ngay_td', headerText: 'Ngày phát hành', allowFiltering: true },
          { fieldName: '', headerText: 'Loại hóa đơn', allowFiltering: true },
          { fieldName: 'loaihoadon_cu', headerText: 'Mẫu hóa đơn cũ', allowFiltering: true},
          { fieldName: 'seri_cu', headerText: 'Ký hiệu hóa đơn cũ', allowFiltering: true },
          { fieldName: 'so_hd_cu', headerText: 'Số hóa đơn cũ', allowFiltering: true },   
          { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true },                       
        ],
      },
      listbox: {
        ketquatimkiem3: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
        },
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
      hideModal: function () {
      this.$bvModal.hide('timkiemkhachhang')
    },
     btnSearchClicked() {
      this.ketquatimkiem.list = null;
      if(this.MaSoThue == "" && this.tenKH == "" && this.checkTuNgay == false && this.checkDenNgay == false){
        this.$toast.error('Bạn phải nhập Tên khách hàng hoặc Mã số thuế hoặc Từ ngày/Đến ngày!')
      }
      else {
      var pdata = {};
      pdata.vtt = "0";
      pdata.vten_kh = this.tenKH.trim();
      pdata.vmst = this.MaSoThue.trim();
      if(this.checkTuNgay == true){
          pdata.vtungay = this.format_date(this.TuNgay);
      }
      else pdata.vtungay = "";
      if(this.checkDenNgay == true){
          pdata.vdenngay = this.format_date(this.DenNgay);
      }
      else pdata.vdenngay = "";
      if(this.checkTuNgay == true){
        pdata.vcheck_tungay = 1;
      }
      else pdata.vcheck_tungay = 0;
      if(this.checkDenNgay == true){
        pdata.vcheck_denngay = 1;
      }
      else pdata.vcheck_denngay = 0;
      console.log(pdata);
      API.tracuu(this.axios, pdata).then((response) => {
        if(response.data.data == "" || response.data.data == null){
            this.$toast.error('Không tìm thấy thông tin về khách hàng')
        }
        if(response && response.data) {
            this.ketquatimkiem.list = response.data.data;
            console.log(this.ketquatimkiem.list);
        }
      });
      }
    },
    format_date(value){
         if (value) {
           return moment(String(value)).format('DD/MM/YYYY')
          }
    },
  }
};
</script>