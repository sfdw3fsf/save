<template src="./TraCuuTBC.html"></template>
<style src="./TraCuuTBC.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import DatePicker from 'vue2-datepicker'
import VueFlatPickr from 'vue-flatpickr-component';
import API from './API'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'

import frmTrangThaiSMS from './popup/frmTrangThaiSMS.vue';
import {
  NHOM_LNV,
  TrangThaiHD,
  LoaiHopDong,
  LoaiHinhTB,
  KieuLapDat,
  TRANGTHAI_DONGBO
} from "@/const/enums";

import moment from 'moment'
import { async, Promise, reject } from 'q';
//
export default {
  components: {
    breadcrumb,
    VueFlatPickr,
    API,DatePicker, frmTrangThaiSMS
},
  name: 'TraCuuTBC',
  mounted () {
    //   this.frmDangKyGoiDaDV_Load()
    //   this.$refs.txtMaGD.focus()
  },
  watch : {
    //   'cboLoaiHinhThueBao.value'(newValue, oldValue) {
    //       this.LoadDS_Goi()
    //   }
    txtSoDT(val) {
        this.sdtFormat = '84' + val
    },
    dtpTuNgay(val) {
        this.tungayFormat = moment(val).format('DD/MM/yyyy 00:00:00')
    }
  },
  data () {
    return {
        gridDanhSach : {
            list : [],
            header : [
                {fieldName: 'stt', headerText: 'STT', allowFiltering: true, width : 'auto'}, 
                {fieldName: 'kenhgui' , headerText: 'Kênh gửi TB', allowFiltering: true, width : 'auto'}, 
                {fieldName: 'loaitin', headerText: 'Loại tin', allowFiltering: true, width : 'auto'},
                {fieldName: 'ngay_gui', headerText: 'Thời gian', allowFiltering: true, width : 'auto'},
                {fieldName: 'ma_tt', headerText: 'Mã TT', allowFiltering: true, width : 'auto'},
                {fieldName: 'so_dt', headerText: 'Số ĐT / Email', allowFiltering: true, width : 'auto'},
                {fieldName: 'trangthai', headerText: 'Trạng thái', allowFiltering: true, width : 'auto'},
            ],
            value : {},
            isEnabled : true,
        },
        settingSelect2 : {
            language: 'vi'
        },
        ma_kho_kt : '',
        cboTrangThai : {
            list : [
                {
                    'id' : -100,
                    'text' : '-- Tất cả --'
                },
                {
                    'id' : 6,
                    'text' : 'Thành công'
                },
                {
                    'id' : -1,
                    'text' : 'Không thành công'
                },
                {
                    'id' : 1,
                    'text' : 'Chưa gửi'
                }
            ],
            value : -100,
            disable: false,
        },
        chkTuNgay : false,
        chkDenNgay : false,
        dtpTuNgay: new Date(),
        dtpDenNgay: new Date(),
        txtMaTT : '',
        txtSoDT : '',
        txtEmail : '',
        params: {
            isIPtinh: 0,
            p_today: new Date(),
            enable: false,
            p_today_string : moment(new Date()).format('DD/MM/YYYY'),
            enableDenNgay: false,
        },
        trangThaiDialog : false,
        tungayFormat : '',
        sdtFormat : '',

    }
  },
  computed: {


  },
  methods : {
    showAlert(mesage)
    {
      this.$toast.error(mesage);
    },
    showSuccess(mesage) {
        this.$toast.success(mesage);
    },

    getData: function (response) {
      if (response.data.error === 200 || response.data.error === '200' || response.data.error === '0') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },

    changeCheckTuNgay() {
        if (this.chkTuNgay) {
            this.params.disable = false
        }
    },

    openModal() {
      this.trangThaiDialog = true
      this.tungayFormat = moment(this.dtpTuNgay).format('DD/MM/yyyy 00:00:00')
      this.sdtFormat = '84' + this.txtSoDT
      this.$refs.frmTrangThaiSMS.show()
    },
    closeModal() {
      this.trangThaiDialog = false
    },

    async HienThi_DanhSach() {
        let tungay = "";
        let denngay = "";

        if (this.chkTuNgay)
            tungay = moment(this.dtpTuNgay).format('DD/MM/YYYY')

        if (this.chkDenNgay)
            denngay = moment(this.dtpDenNgay).format('DD/MM/YYYY')

        // let ds = new QuanLyThuNoFacade_v2().Lay_ds_thongtin_nhan_tbc(tungay, denngay, txtMaTT.Text.Trim(), txtSoDT.Text.Trim(),
        //     txtEmail.Text.Trim(), Convert.ToInt32(cboTrangThai.EditValue));
        this.loading(true)
        let ds = this.getData(await API.lay_ds_thongtin_nhan_tbc(this.axios, {
            'vtungay' : tungay,
            'vdenngay' : denngay,
            'vma_tt' : this.txtMaTT,
            'vso_dt' : this.txtSoDT,
            'vemail' : this.txtEmail,
            'vtrangthai' : this.cboTrangThai.value
        }))

        if (ds != null && ds.length > 0)
        {
            this.gridDanhSach.list = ds
        }
        else
        {
            this.showAlert("Không có dữ liệu!");
            this.gridDanhSach.list = [];
        }
        this.loading(false)
    },
    btnTimKiem_Click() {
        this.HienThi_DanhSach()
    },

    txtMaTT_KeyPress() {
        this.HienThi_DanhSach()
    },
    txtEmail_KeyPress() {
        this.HienThi_DanhSach()
    },
    txtSoDT_KeyPress() {
        this.HienThi_DanhSach()
    },
    tsbtnTrangThaiSMS_Click () {
        console.log('click')
    },
    chkTuNgay_CheckedChanged(){
        this.params.enable = this.chkTuNgay

    },
    chkDenNgay_CheckedChanged(){
      this.params.enableDenNgay = this.chkDenNgay
      console.log(this.params.disableDenNgay)
    }

}
}

</script>

<style>
    .searchAccountDialogClass .e-dlg-header-content {
        display: none;
    }
</style>
