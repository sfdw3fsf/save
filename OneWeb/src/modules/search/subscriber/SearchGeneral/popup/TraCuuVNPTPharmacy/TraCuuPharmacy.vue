<template src='./TraCuuPharmacy.html'></template>

<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import { required, minLength, integer, maxValue } from 'vuelidate/lib/validators'
import api from '../../API'
export default {
  name: 'TraCuuPharmacy',
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
        list: [
        ],
        header: [
          {fieldName: 'contractCode', headerText: 'Mã thuê bao', allowFiltering: true, width: 130},
          {fieldName: 'name', headerText: 'Tên', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'licenseAddress', headerText: 'Địa chỉ', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'phoneNumber', headerText: 'Sđt', allowFiltering: true , allowHtml : true, width: 150},
          {fieldName: 'email', headerText: 'Email', allowFiltering: true , allowHtml : true, width: 200},
          {fieldName: 'birthDate', headerText: 'Ngày sinh', allowFiltering: true , allowHtml : true, width: 150},
          {fieldName: 'contractNote', headerText: 'Ghi chú', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'contractStatus', headerText: 'Trạng thái', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'contractType', headerText: 'Phiên bản', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'effectiveDate', headerText: 'Ngày kết thúc', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'shopQuota', headerText: 'Số cửa hàng', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'accountQuota', headerText: 'Số User', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'pauseFrom', headerText: 'Tạm dừng từ', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'pauseTo', headerText: 'Tạm dừng đến', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'expireDate', headerText: 'Ngày hủy', allowFiltering: true , allowHtml : true, width: 100},
        ],
        value: {},
        isEnabled: true,
        checked: []
      },
      ds_cuahang: {
        list: [],
        header: [ {fieldName: 'MA_CH', headerText: 'Mã cửa hàng', allowFiltering: true, width: 130},
          {fieldName: 'TEN_TT', headerText: 'Tên TT', allowFiltering: true, width: 130},
          {fieldName: 'TEN', headerText: 'Tên', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'DIACHI_TT', headerText: 'DIACHI TT', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'NGUOI_DD', headerText: 'NGUOI DD', allowFiltering: true , allowHtml : true, width: 150},
          {fieldName: 'MOBILE', headerText: 'SĐT', allowFiltering: true , allowHtml : true, width: 200},
          {fieldName: 'EMAIL', headerText: 'Email', allowFiltering: true , allowHtml : true, width: 150},
          {fieldName: 'NGAYSINH', headerText: 'Ngày sinh', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'NGUOI_CN', headerText: 'Người CN', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'MAY_CN', headerText: 'Máy CN', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'PRICE_LEVEL_NOTE', headerText: 'PRICE LEVEL NOTE', allowFiltering: true , allowHtml : true, width: 100},
        ],
        value: {},
        isEnabled: true,
        checked: []
      },
      ds_user: {
        list: [],
        header: [ {fieldName: 'USERID', headerText: 'User ID', allowFiltering: true, width: 150},
          {fieldName: 'USER_NAME', headerText: 'USER_NAME', allowFiltering: true, width: 100},
          {fieldName: 'DOB', headerText: 'Ngày sinh', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'ADDRESS', headerText: 'Địa chỉ', allowFiltering: true , allowHtml : true, width: 150},
          {fieldName: 'MOBILE', headerText: 'Số điện thoại', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'EMAIL', headerText: 'Email', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'COMPANY_ADDRESS', headerText: 'Địa chỉ công ty', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'STATUS', headerText: 'Trạng thái', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'CREATED_BY', headerText: 'Người tạo', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'LOGIN_USERID', headerText: 'LOGIN_USERID', allowFiltering: true , allowHtml : true, width: 100},
        ],
        value: {},
        isEnabled: true,
        checked: []
      },
      ds_thuebaocungdoicap: {
        list: [],
        header: [ {fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true, width: 100},
          {fieldName: 'dichvu', headerText: 'Dịch vụ', allowFiltering: true, width: 150},
          {fieldName: 'loaihinh', headerText: 'Loại hình', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'matb_tn', headerText: 'Mã TN', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'dslam_visa', headerText: 'Dslam Visa', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'madoicap', headerText: 'Mã đối cáp', allowFiltering: true , allowHtml : true, width: 100},
          {fieldName: 'slot', headerText: 'Slot', allowFiltering: true , allowHtml : true, width: 50},
          {fieldName: 'port', headerText: 'Port', allowFiltering: true , allowHtml : true, width: 50},
          {fieldName: 'rack', headerText: 'Rack', allowFiltering: true , allowHtml : true, width: 50},
          {fieldName: 'shelf', headerText: 'Shelf', allowFiltering: true , allowHtml : true, width: 50},
          {fieldName: 'dslam_tinh', headerText: 'DSLam. Tinh', allowFiltering: true , allowHtml : true, width: 150},
        ],
        value: {},
        isEnabled: true,
        checked: []
      },

      datas: {
        "transId":"123456789",
        "sme":{
          "subscriptionCode":"hcm_pharmacy_00000439",
          "provinceCode":"HCM"
        }
      },

      chkCheckDate: false,
      Loading: false,
      txtTrangThai: '',
      txtTenKH: '',
      txtDiaChi: '',
      txtSDT: '',
      txtEmail: '',
      txtNgayKT: '',

      formUSER: {
        txtTongUSER: '',
        txtCountUserCatHuy: '',
        txtCountUserKhoiTaoUS: '',
      },

      formCH: {
        txtCountUserKhoiTaoCH: '',
        txtTongCH: '',
      },
    }
  },

  computed: {

  },
  watch: {
    /* Loading: function (val, oldval) {
      this.$root.showLoading(val)
    } */
  },

  async mounted() {
    await this.LOAD_THONGTINCHITIET_PHARMACY();
    await this.LOAD_DSCUAHANG_PHARMACY();
    await this.LOAD_DSUSER_PHARMACY();
  },

  methods: {
    async LOAD_THONGTINCHITIET_PHARMACY() {
      try {
        let response = await api.fn_thongtinchitiet_pharmacy(this.axios, this.datas);
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          const rs = JSON.parse(response.data.data);
          this.ds_thongtinchitiet.list = rs.map.rs;
        }
      } catch (e) {
        console.log(e)
      }
    },

    async LOAD_DSCUAHANG_PHARMACY() {
      try {
        let response = await api.fn_layDsCuaHang_pharmacy(this.axios, this.datas);
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          const rs = JSON.parse(response.data.data);
          this.ds_cuahang.list = rs.map.rs;
          console.log("rs.map")
          console.log(rs.map)
          this.formCH.txtTongCH = rs.map.PO_TOTAL;
          this.formCH.txtCountUserKhoiTaoCH = rs.map.QUOTA;
        }
      } catch (e) {
        console.log(e)
      }
    },

    async LOAD_DSUSER_PHARMACY() {
      try {
        let response = await api.fn_layDsUser_pharmacy(this.axios, this.datas);
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          const rs = JSON.parse(response.data.data);
          this.ds_user.list = rs.map.rs;
          console.log("rs.map")
          console.log(rs.map)
         this.formUSER.txtTongUSER = rs.map.PO_TOTAL;
         this.formUSER.txtCountUserCatHuy = rs.map.REMOVABLE;
         this.formUSER.txtCountUserKhoiTaoUS = rs.map.QUOTA;
        }
      } catch (e) {
        console.log(e)
      }
    },

    gridViewTTChiTiet_FocusedRowChanged(data) {
      this.txtTenKH = data.name;
      this.txtDiaChi = data.licenseAddress;
      this.txtSDT = data.phoneNumber;
      this.txtEmail = data.email;
      this.txtTrangThai = data.contractStatus;
      this.txtNgayKT = data.effectiveDate;
    },

    onSearch() {
      console.log("on search")
    }
  },
}

</script>

