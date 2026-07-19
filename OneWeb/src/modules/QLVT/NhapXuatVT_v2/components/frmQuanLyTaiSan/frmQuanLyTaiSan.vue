<template src='./QuanLyTaiSan.html'></template>

<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import { required, minLength, integer, maxValue } from 'vuelidate/lib/validators'
export default {
  name: 'frmQuanLyTaiSan',
  components: {
    DatePicker
    ,required, minLength, integer, maxValue
  },
  props: [ 'dulieu' ],

  data () {
    return {
      dateFormat: 'DD/MM/YYYY',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'date',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      ds_thongtinchitiet: {
        list: [],
        header: [
          {fieldName: 'ten_kv', headerText: 'Mã VT', allowFiltering: true, width: 130},
          {fieldName: 'ma_nv', headerText: 'Tên VT', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'ma_nd', headerText: 'ĐVT', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'ten_nv', headerText: 'Serial', allowFiltering: true , allowHtml : true, width: 150},
          {fieldName: 'loai_nv', headerText: 'Số lượng', allowFiltering: true , allowHtml : true, width: 200},
          {fieldName: 'ten_dv', headerText: 'Đơn giá', allowFiltering: true , allowHtml : true, width: 150},
          {fieldName: 'so_dt', headerText: 'Thành tiền', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: '', headerText: 'QR Code', allowFiltering: true , allowHtml : true, width: 100},
        ],
        value: {},
        isEnabled: true,
        checked: []
      },
      ds_nvkvtheothuebao: {
        list: [],
        header: [ {fieldName: 'ma_kv', headerText: 'Serial', allowFiltering: true, width: 130},
          {fieldName: 'ten_kv', headerText: 'STT', allowFiltering: true, width: 130},
          {fieldName: 'ma_nv', headerText: 'Số thẻ', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'ma_nd', headerText: 'Mã VT', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'ten_nv', headerText: 'Tên thẻ', allowFiltering: true , allowHtml : true, width: 150},
          {fieldName: 'loai_nv', headerText: 'Giá trị', allowFiltering: true , allowHtml : true, width: 200},
          {fieldName: 'ten_dv', headerText: 'SL', allowFiltering: true , allowHtml : true, width: 150},
          {fieldName: 'so_dt', headerText: 'Model', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: '', headerText: 'Loại TS', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: '', headerText: 'Mã CSHT', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: '', headerText: 'Hệ thống', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: '', headerText: 'QR Code', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: '', headerText: 'Bar Code', allowFiltering: true , allowHtml : true, width: 100},
        ],
        value: {},
        isEnabled: true,
        checked: []
      },
      chkCheckDate: false,
      Loading: false,
      data: [

      ],
    }
  },

  mounted() {
    console.log(123)
  },

  computed: {

  },
  watch: {
    /* Loading: function (val, oldval) {
      this.$root.showLoading(val)
    } */
  },


}

</script>

