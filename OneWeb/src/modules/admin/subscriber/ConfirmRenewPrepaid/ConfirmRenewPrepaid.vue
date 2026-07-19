<template src="./ConfirmRenewPrepaid.html"></template>
<style src="./ConfirmRenewPrepaid.scss" scoped></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import API from './API'
//import gridview from '@/components/Shared/gridview'
import gridview from "./components/DataGrid";

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
import ModalChonDiaChi from './components/ModalChonDiaChi.vue'
import FollowProject from './components'

//
export default {
  components: {breadcrumb,VueFlatPickr,DatePicker,gridview,API,DataManager, Query, ODataAdaptor, Predicate,ModalChonDiaChi,BssErrorMarker, BssRequiredMarker,FollowProject },
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
    this.LoadDonVi();
    this.LoadDSDatCoc();
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
        title: 'GÁN LOẠI VẬT TƯ - CHỨC DANH, ĐƠN VỊ',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      isDonVi:true,
      isDonViCheck: true,
      isNhanVienQL:true,
      isNhanVienQLCheck: false,
      isNVTC:false,
      isNVTCCheck: false,

      isCapNhat:false,
      isCachTim: false,

      listbox: {
        DonVi: {
          list: [],
          value: {donvi_id: "",ten_dv: ""},
        },
        NhanVienQL: {
          list: [],
          value: {nhanvien_id: "",ten_nv: ""},
        },
        NVTC: {
          list: [],
          value: {tuyenthu_id: "",gheptuyen: ""},
        },

        ds_DatCocTraTruoc: {
          list: [],
          header: [ {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, width : 130}, 
                    {fieldName: 'ten_tb', headerText: 'Tên TB', allowFiltering: true}, 
                    {fieldName: 'ma_tt', headerText: 'Mã TT', allowFiltering: true, width : 130}, 
                    {fieldName: 'diachi_tt', headerText: 'Địa chỉ TT', allowFiltering: true, width : 300}, 
                    {fieldName: 'so_dt', headerText: 'Số ĐT', allowFiltering: true, width : 100}, 
                    {fieldName: 'manv_tc', headerText: 'Mã NVTC', allowFiltering: true, width : 100}, 
                    {fieldName: 'ma_nv_gt', headerText: 'Mã NV giới thiệu', allowFiltering: true, width : 100}, 
                    {fieldName: 'ten_nv_gt', headerText: 'Tên NV giới thiệu', allowFiltering: true, width : 150}, 
                    {fieldName: 'ngay_dk', headerText: 'Ngày ĐK', allowFiltering: true, width : 150}, 
                    {fieldName: 'thang_bd', headerText: 'Tháng BĐ', allowFiltering: true, width : 100}, 
                    {fieldName: 'thang_kt', headerText: 'Tháng KT', allowFiltering: true, width : 100}, 
                    {fieldName: "datcoc_csd", headerText: "Đặt cọc CSD", allowFiltering: true,allowSorting: false, type: "Number", format: "N0", width: 120 },
                    {fieldName: "tien_td", headerText: "Tiền trừ dần", allowFiltering: true,allowSorting: false, type: "Number", format: "N0", width: 120 },
                   /*  {fieldName: 'datcoc_csd', headerText: 'Đặt cọc CSD', allowFiltering: true, width : 100}, 
                    {fieldName: 'tien_td', headerText: 'Tiền trừ dần', allowFiltering: true, width : 100},  */
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
      sodt: "",
      noidung: "",
      matbao: "",
      

      index:0,
      success_code: "BSS-00000000",
    }
  },
  computed: {
    timtheodonvi: {
      get() { return this.isDonViCheck; },
      set(value) { 
        this.isDonViCheck=value; 
        if (this.isDonViCheck)
        {
          this.isNhanVienQL=value; 
        }
        }
    },
    timtheonvql: {
      get() { return this.isNhanVienQLCheck; },
      set(value) { 
        this.isNhanVienQLCheck=value; 
        if (this.isNhanVienQLCheck)
        {
          this.isNVTC=value; 
          this.cboDonVi_Changed();
        }
        else
        {
          this.listbox.NhanVienQL.list = [];
        }
        }
    },
    timtheonvtc: {
      get() { return this.isNVTCCheck; },
      set(value) { 
        this.isNVTCCheck=value; 
        if (this.isNVTCCheck)
        {
          this.isNVTC=value; 
          this.cboNhanVien_Changed();
        }
        else
        {
          this.listbox.NVTC.list = [];
        }
        }
    },
    DonVi: {
      get() { return this.isDonVi; },
      set(value) { this.isDonVi= value;   
      }
    },
    NhanVienQL: {
      get() { return this.isNhanVienQL; },
      set(value) { this.isNhanVienQL= value;   
      }
    },
    NVTC: {
      get() { return this.isNVTC; },
      set(value) { this.isNVTC= value;   
      }
    },
    GhiLai: {
      get() { return this.isGhiLai; },
      set(value) { this.isGhiLai=value; 
      }
    },
    Huy: {
      get() { return this.isHuy; },
      set(value) { this.isHuy=value; 
      }
    },
    Xoa: {
      get() { return this.isXoa; },
      set(value) { this.isXoa=value; 
      }
    },
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
    
    SetButton(kieu)
    {
      this.isNhapMoi = false;
      this.isGhiLai = false;
      this.isHuy = false;
      this.isXoa = false;
      if (kieu == -1)//Bat dau
      {
        this.isGhiLai = true;
        this.isHuy = true;
      }
      if (kieu == 0)//Bat dau
      {
        this.isNhapMoi = true;
        this.Clear();
      }
      if (kieu == 1)//Them moi
      {
        this.isGhiLai = true;
        this.isHuy = true;
        this.Clear();
        this.$refs.chudautu.focus();
      }
      if (kieu == 2)//Huy
      {
        this.isNhapMoi = true;
        this.isXoa = true;
        this.Clear();
        this.$refs.chudautu.focus();
      }
      if (kieu == 3)//Edit
      {
        this.isNhapMoi = true;
        this.isXoa = true;
        this.isGhiLai = true;
        this.isHuy = true;
      }
    },

    Clear()
    {
      
    },
    
    

    LoadDonVi: async function ()
    {
      var input = {};
      this.loading(true);
      this.listbox.DonVi.list = this.GetData(await API.lay_donvi_gan_nhomcno(this.axios, input)).map(x=>({id:x.donvi_id,text:x.ten_dv}));
      this.loading(false);
      if (this.listbox.DonVi.list.length>0)
      {
        this.listbox.DonVi.value.donvi_id = this.listbox.DonVi.list[0].id;
        this.LoadDSDatCoc();
      }
    },
    cboDonVi_Changed: async function()
    {
      if (this.isNhanVienQLCheck)
      {
         var input = {};
        input.donviql_id = this.listbox.DonVi.value.donvi_id;
        this.loading(true);
        this.listbox.NhanVienQL.list = this.GetData(await API.lay_nvql_theo_donvi_id(this.axios, input)).map(x=>({id:x.nhanvien_id,text:x.ten_nv}));
        this.loading(false);
        if (this.listbox.NhanVienQL.list.length>0)
        {
          this.listbox.NhanVienQL.value.nhanvien_id = this.listbox.NhanVienQL.list[0].id;
          this.LoadDSDatCoc();
        }
      }
      else
      {
        this.LoadDSDatCoc();
      }
    },
    cboNhanVien_Changed: async function()
    {
      if (this.isNVTCCheck)
      {
        var input = {};
        input.nhanvienql_id = this.listbox.NhanVienQL.value.nhanvien_id;
        this.loading(true);
        this.listbox.NVTC.list = this.GetData(await API.lay_nvtc_theo_nvkd(this.axios, input)).map(x=>({id:x.tuyenthu_id,text:x.gheptuyen}));
        this.loading(false);
        if (this.listbox.NVTC.list.length>0)
        {
          this.listbox.NVTC.value.tuyenthu_id = this.listbox.NVTC.list[0].id;
          this.LoadDSDatCoc();
        }
      }
      else
      {
        this.LoadDSDatCoc();
      }
    },
    cboNVTC_Changed: async function()
    {
      this.LoadDSDatCoc();
    },
    LoadDSDatCoc: async function ()
    {
      var input = {};
      input.donviql_id = this.listbox.DonVi.value.donvi_id? this.listbox.DonVi.value.donvi_id: -1;
      input.nhanvienql_id= this.listbox.NhanVienQL.value.nhanvien_id? this.listbox.NhanVienQL.value.nhanvien_id: -1;
      input.tuyenthu_id= this.listbox.NVTC.value.tuyenthu_id? this.listbox.NVTC.value.tuyenthu_id: -1;
      input.dagan=1;
      console.log(input);
      this.loading(true);
      this.listbox.ds_DatCocTraTruoc.list = this.GetData(await API.lay_ds_datcoc_theo_thoihan(this.axios, input));
      if (this.listbox.ds_DatCocTraTruoc.list.length>0)
      {
          for (var i=0; i<this.listbox.ds_DatCocTraTruoc.list.length; i++)
          {
            this.listbox.ds_DatCocTraTruoc.list[i].datcoc_csd = parseInt(this.listbox.ds_DatCocTraTruoc.list[i].datcoc_csd);
            this.listbox.ds_DatCocTraTruoc.list[i].tien_td = parseInt(this.listbox.ds_DatCocTraTruoc.list[i].tien_td);

          }
      }
      this.loading(false);
     
    },
    btnTimKiem()
    {
      this.LoadDSDatCoc();
    },

    btnNhanTin: async function ()
    {
      var input = {};
      input.so_dt = this.sodt;
      input.noidung= this.noidung;
      input.ghichu= "";
      input.may_cn="";
      input.nguoi_cn="";
      input.ip_cn="";

      console.log(input);
      this.loading(true);
      API.send_sms(this.axios, input).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === this.success_code) {
          this.ShowAlert("Nhắn tin đến khách hàng thành công!",0);
        }
        else
        {
          this.ShowAlert("Nhắn tin không thành công!",2);
        }
      }
      ); 
      
    },

    btnChuyenHD()
    {
      this.$bvModal.msgBoxConfirm('Bạn thật sự muốn chuyển hợp đồng Thay đổi đặt cọc không?', {
      title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
      if(v) {
         /* frmTBDatCoc frm = new frmTBDatCoc();
                frm.MdiParent = this.MdiParent;

                frm.Show();
                frm.GanTxtMaTB(ma_tb);
                frm.HienThiTT_DanhBa(ma_tb); */
      }
      });
    },
    
    selectedItems_click()
    {

    },
    
    rowClicked(i,obj)
    {
      if(!obj) { 
        return; 
      }
      this.index = i;
      
    },
     gridChanged(obj)
    {
      if(!obj) { 
        return; 
      }
      this.sodt = obj.so_dt;
      this.noidung = obj.noidung;
      this.matbao = obj.ma_tb;
    },
    
   


  }, 
};

</script>
