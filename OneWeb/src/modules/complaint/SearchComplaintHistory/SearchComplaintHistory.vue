<template src="./SearchComplaintHistory.html"></template>
<style src="./SearchComplaintHistory.scss" scoped></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import API from './API'
import gridview from '@/components/Shared/gridview'

import { DataManager, Query, ODataAdaptor, Predicate } from '@syncfusion/ej2-data'
import moment from 'moment'

import DatePicker from 'vue2-datepicker' 
import 'vue2-datepicker/locale/vi.js' 
import 'vue2-datepicker/index.css'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required, minValue, maxValue } from 'vuelidate/lib/validators'
//import ModalChonDiaChi from './components/ModalChonDiaChi.vue'
// import ModalChonDiaChi from './components/ModalChonDiaChi.vue'
// import FollowProject from './components'

//
export default {
  components: {breadcrumb,VueFlatPickr,DatePicker,gridview,API,DataManager, Query, ODataAdaptor, Predicate,BssErrorMarker, BssRequiredMarker },
  name: 'AssignSuppliesToTitleDepartment',
  validations: {
    diachiTB:
    {
      DIACHI: {
        required
      },
    },
  },
  mounted () {
    
  },
  data () {
    return {
      dateconfig: {
        altFormat: 'd/m/Y H:i:S',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput:true,
      },
      dateFormat: 'DD/MM/YYYY HH:mm:ss',
      header: {
        title: 'TRA CỨU LỊCH SỬ KHIẾU NẠI',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ] 
      },

      listbox: {

        ds_lichsukhieunai: {
          list: [],
          header: [ {fieldName: 'ngay_tn', headerText: 'Ngày YC', allowFiltering: true, width: 150}, 
                    {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, width: 130}, 
                    {fieldName: 'nguoi_kn', headerText: 'Người YC', allowFiltering: true, width: 150}, 
                    {fieldName: 'nd_khieunai', headerText: 'Nội dung khiếu nại', allowFiltering: true, width: 300}, 
                    {fieldName: 'ma_tt', headerText: 'Mã thanh toán', allowFiltering: true, width: 150}, 
                    {fieldName: 'ten_kh', headerText: 'Tên khách hàng', allowFiltering: true, width: 150}, 
                    {fieldName: 'diachi_kh', headerText: 'Địa chỉ khách hàng', allowFiltering: true, width: 350}, 
                    {fieldName: 'tenchude', headerText: 'Chủ đề khiếu nại', allowFiltering: true, width: 300}, 
                    {fieldName: 'nhanvien_tn', headerText: 'Người tiếp nhận', allowFiltering: true, width: 130}, 
                    {fieldName: 'donvi_tn', headerText: 'Đơn vị tiếp nhận', allowFiltering: true, width: 200}, 
                    {fieldName: 'trangthai_kn', headerText: 'Kết quả xử lý', allowFiltering: true, width: 150}, 
                    {fieldName: '', headerText: 'Kết quả KN', allowFiltering: true, width: 150}, 
                    {fieldName: 'noidung_gq', headerText: 'Nội dung xử lý', allowFiltering: true, width: 150}, 
                    {fieldName: 'nhanvien_gq', headerText: 'Người xử lý', allowFiltering: true, width: 150}, 
                    {fieldName: 'ngay_gq', headerText: 'Ngày xử lý', allowFiltering: true, width: 150}, 
                    //Bổ sung giảm trử
                    {fieldName: 'ketqua_gc', headerText: 'Kết quả giảm trừ', allowFiltering: true, width: 150}, 
                    {fieldName: 'ngay_tt', headerText: 'Thời điểm giảm trừ', allowFiltering: true, width: 150},
                    {fieldName: 'tien_gc', headerText: 'Tiền giảm trừ', allowFiltering: true, width: 150},
                    {fieldName: 'vat_gc', headerText: 'VAT giảm trừ', allowFiltering: true, width: 150}, 
                    {fieldName: '', headerText: 'Kỳ TT', allowFiltering: true, width: 150}, 
                    {fieldName: '', headerText: 'Gói cước', allowFiltering: true, width: 150}, 
                    {fieldName: '', headerText: 'Tiền trả', allowFiltering: true, width: 150}, 
                    {fieldName: '', headerText: 'Hẹn trả', allowFiltering: true, width: 150}
                  ],
          value: {},
          checked: []
        },

       
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },

      index:0,
      success_code: "BSS-00000000",

      somayacc: "",

    }
  },
  computed: {
   
  },
  methods: {
    ShowAlert(mesage,val)
    {
      if (val==0)
      {
        this.$toast.success(mesage);
      }
      else if (val==1)
      {
        this.$toast.warning(mesage);
      }
      else if (val==2)
      {
        this.$toast.error(mesage);
      }
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },


    btnTimKiem: async function ()
    {
      var input = {};
      input.kyhoadon = "";
      input.ma_tb = this.somayacc.toLowerCase();
      console.log(input);
      this.loading(true);
      await API.lichsu_gqkh(this.axios, input).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.ds_lichsukhieunai.list = response.data.data;
        }
        else
        {
          this.listbox.ds_lichsukhieunai.list =[];
          this.ShowAlert("Không tìm thấy lịch sử khiếu nại của thuê bao : "+ this.somayacc ,2);
        }
      }
      );
    },

    btnTienTrinhDA()
    {
      // this.popupComponent = ()=>import('./components/FollowProject.vue');
      this.popupComponentName = "FollowProject";
      this.popupTitle = "Tiến trình dự án"
      this.popupComponentData = { vDuAnId: this.DuAnID}
      this.$bvModal.show('popupComponents')
    },

/*  this.popupComponent = ()=>import('./components/DetailSub.vue');
      this.popupComponentName = "DetailSub";
      this.popupTitle = "Thông tin chi tiết thuê bao hiện tại trên hệ thống ĐHSX"
      this.popupComponentData = { vthuebao_id:this.thuebao_id}
      this.$bvModal.show('popupComponents') */


    ChuDauTuChanged()
    {

    },
    TrungTamVTChanged()
    {

    },
    getDiaChiTB: function (data) {
      this.getDiaChi(data,'diachiTB');
    },
    getDiaChi:function(data,datatype) {
      this[datatype].DIACHI = data.diachimoi
      this[datatype].TINH_ID = data.tinh_id
      this[datatype].QUAN_ID = data.quan_id
      this[datatype].PHUONG_ID = data.phuong_id
      this[datatype].PHO_ID = data.pho_id
      this[datatype].AP_ID = data.ap_id
      this[datatype].KHU_ID = data.khu_id
      this[datatype].DAC_DIEM_ID = data.dacdiem_id
      this[datatype].SONHA = data.so_nha
      this[datatype].KINHDO = data.lat
      this[datatype].VIDO = data.lng

      this.vds.PHUONG_ID = data.phuong_id;
      this.vds.QUAN_ID = data.quan_id;
      this.vds.DIACHI = data.diachimoi;
    },
   


  }, 
};

</script>
