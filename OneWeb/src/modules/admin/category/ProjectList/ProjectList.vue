<template src="./ProjectList.html"></template>
<style src="./ProjectList.scss" scoped></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import API from './API'
//import gridview from '@/components/Shared/gridview'
import DataGrid from "./components/DataGrid";

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
  components: {breadcrumb,VueFlatPickr,DatePicker,API,DataManager, Query, ODataAdaptor, Predicate,ModalChonDiaChi,BssErrorMarker, BssRequiredMarker,FollowProject,DataGrid },
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
    this.LoadChuDauTu(); 
    this.LoadTTVT();
    this.LoadHinhThucHT();
    this.LoadDSTrangThai();
    this.LoadDSDuAn();
    this.SetButton(-1);
  },
  created() { 
    this.$root.$on('bv::modal::shown', (bvEvent, modalId) => { 
      document.getElementsByClassName('modal-content').forEach(function(v){ v.removeAttribute('tabindex'); }); 
    }); 
    this.$root.$on('bv::modal::hidden', (bvEvent, modalId) => { 
      this.ValidateModal(bvEvent,modalId); 
    }); 
  }, 
  

  data () {
    return {
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput:true,
      },
      dateFormat: 'DD/MM/YYYY',
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
      isNhapMoi:false,
      isGhiLai:false,
      isHuy:false,
      isXoa:false,

      isCapNhat:false,
      isCachTim: false,

      listbox: {
        ChuDauTu: {
          list: [],
          value: {chudtu_id: "",ten_chudtu: ""},
        },
        TrungTamVT: {
          list: [],
          value: {donvi_id: "",ten_dv: ""},
        },
        HinhThucHT: {
          list: [],
          value: {htht_id: "",ten_htht: ""},
        },
        dsTrangThai: {
          list: [],
          value: {ttda_id: "",trangthai_da: ""},
        },

        ds_DuAn: {
          list: [],
          header: [ {fieldName: 'ma_duan', headerText: 'Mã dự án', allowFiltering: true, width: 120}, 
                    {fieldName: 'ten_duan', headerText: 'Tên dự án', allowFiltering: true}, 
                    {fieldName: 'ten_chudtu', headerText: 'Chủ đầu tư', allowFiltering: true, width: 250}, 
                    {fieldName: 'kho_duan', headerText: 'Kho dự án', allowFiltering: true, width: 150}, 
                    {fieldName: 'ten_quan', headerText: 'Quận/Huyện', allowFiltering: true, width: 150}, 
                    {fieldName: 'diachi', headerText: 'Địa chỉ', allowFiltering: true}, 
                    {fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true, width: 300}, 
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

      DiaChi:"",
      DuAnID: "",
      MaDA:"",
      TenDA:"",
      NguoiCungCap:"",
      GhiChu:"",
      //NgayTrienKhai: new Date(),
      //NgayHoanThanh: new Date(),
      NgayTrienKhai: "",
      NgayHoanThanh: "",
      popupComponent: null,
      popupComponentData: null,
      popupTitle: "",
      popupComponentName: '',


      diachiTB: {
        DIACHI: null,
        TINH_ID:0,
        QUAN_ID:0,
        PHUONG_ID:0,
        PHO_ID:0,
        AP_ID:0,
        KHU_ID:0,
        DAC_DIEM_ID:0,
        SONHA:0,
        KINHDO: 0,
        VIDO: 0,
        BLOCK: null,
        TANG: null,
        PHONG: null,
        MOTA: null,
        DIACHI_ID: 0,
      },

      vds:{
        PHANVUNG_ID: null,
        DUAN_ID: null,
        MA_DUAN: null,
        TEN_DUAN: null,
        DIACHI: null,
        CHUDTU_ID: 0,
        PHUONG_ID: 0,
        QUAN_ID: 0,
        GHICHU: "",
        NGAY_CN: "",
        MAY_CN: "",
        NGUOI_CN: "",
        IP_CN: "",
        TTDA_ID: 0,
        NGAY_TK: "",
        NGAY_HT: "",
        TTVT_ID: 0,
        NGUOI_CCTT: "",
        HTHT_ID: 0,
        MA_CT: "",
        NGAY_NT: "",
        SOHIEU_CT: 0,
        SOQD_DT: "",
        SOQD_QT: "",
        DIACHI_ID:0,    
      },
      index:0,
      success_code: "BSS-00000000",
    }
  },
  computed: {
    NhapMoi: {
      get() { return this.isNhapMoi; },
      set(value) { this.isNhapMoi=value; 
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
    async ValidateModal(bvEvent,modalId) {},
    
    SetButton: async function(kieu)
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
        await this.Clear();
        this.$refs.gridCtr.setCurrentSelectedRow(this.index);
      }
      if (kieu == 1)//Them moi
      {
        this.isGhiLai = true;
        this.isHuy = true;
        await this.Clear();
        this.$refs.TenDA.focus();
      }
      if (kieu == 2)//Huy
      {
        this.isNhapMoi = true;
        this.isXoa = true;
        await this.Clear();
        this.$refs.TenDA.focus();
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
      this.MaDA = "";
      this.NguoiCungCap = "";
      this.DiaChi = "";
      this.GhiChu = "";
      this.TenDA = "";
      this.NgayHoanThanh = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
      this.NgayTrienKhai = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
      this.listbox.TrungTamVT.value.donvi_id = this.listbox.TrungTamVT.list[1].id;
      this.listbox.HinhThucHT.value.htht_id = this.listbox.HinhThucHT.list[0].id;
      this.listbox.ChuDauTu.value.chudtu_id = this.listbox.ChuDauTu.list[0].id;
      this.listbox.dsTrangThai.value.ttda_id = this.listbox.dsTrangThai.list[0].id;
      this.$v.diachiTB.DIACHI.$model = null
      this.$refs.MaDA.focus();
    },
    
    btnNhapMoi()
    {
      this.SetButton(1);
    },

    btnHuy()
    {
      this.SetButton(0);
    },

    LoadChuDauTu: async function ()
    {
      var input = {};
      this.loading(true);
      this.listbox.ChuDauTu.list = this.GetData(await API.ds_chudtu(this.axios, input)).map(x=>({id:x.chudtu_id,text:x.ma_chudtu +" - "+ x.ten_chudtu}));
      this.loading(false);
      if (this.listbox.ChuDauTu.list.length>0)
      {
        this.listbox.ChuDauTu.value.chudtu_id = this.listbox.ChuDauTu.list[0].id;
      }
    },
    LoadTTVT: async function ()
    {
      var input = {};
      input.ID=3;
      this.loading(true);
      this.listbox.TrungTamVT.list = this.GetData(await API.lay_tt_donvi(this.axios, input)).map(x=>({id:x.donvi_id,text: x.ten_dv? x.donvi_id + " - " + x.ten_dv: ""}));
      this.loading(false);
      if (this.listbox.TrungTamVT.list.length>0)
      {
        this.listbox.TrungTamVT.value.donvi_id = this.listbox.TrungTamVT.list[1].id;
      }
    },
    LoadHinhThucHT: async function ()
    {
      var input = {};
      this.loading(true);
      this.listbox.HinhThucHT.list = this.GetData(await API.ds_ht_hoptac(this.axios, input)).map(x=>({id:x.htht_id,text:x.ten_htht}));
      this.loading(false);
      if (this.listbox.HinhThucHT.list.length>0)
      {
        this.listbox.HinhThucHT.value.htht_id = this.listbox.HinhThucHT.list[0].id;
      }
    },
    LoadDSTrangThai: async function ()
    {
      var input = {};
      this.loading(true);
      this.listbox.dsTrangThai.list = this.GetData(await API.ds_trangthai_da(this.axios, input)).map(x=>({id:x.ttda_id,text:x.trangthai_da}));
      this.loading(false);
      if (this.listbox.dsTrangThai.list.length>0)
      {
        this.listbox.dsTrangThai.value.ttda_id = this.listbox.dsTrangThai.list[0].id;
      }
    },
    LoadDSDuAn: async function ()
    {
      var input = {};
      this.loading(true);
      /* this.$refs.grid.clearFilterConditions()
      this.$refs.grid.grid.refresh() */

      this.listbox.ds_DuAn.list = this.GetData(await API.lay_tt_duan(this.axios, input));
      if (this.listbox.ds_DuAn.list.length>0)
      {
        console.log(this.listbox.ds_DuAn.list);
        this.$refs.gridCtr.setCurrentSelectedRow(this.index);
      }
      //this.dsDuAn = this.listbox.ds_DuAn.list.filter(x =>x.duan_id == 0)
      this.loading(false);
    },
    rowClicked(i,obj)
    {
      if(!obj) { 
        return; 
      }
      this.index = i;
      this.listbox.ChuDauTu.value.chudtu_id = obj.chudtu_id;
      this.DuAnID = obj.duan_id;
      this.MaDA =  obj.ma_duan;
      this.TenDA =  obj.ten_duan;
      this.listbox.dsTrangThai.value.ttda_id = obj.ttda_id;
      this.NguoiCungCap = obj.nguoi_cctt;
      this.NgayTrienKhai = moment(obj.ngay_tk).format("DD/MM/YYYY HH:mm:ss");
      this.NgayHoanThanh = moment(obj.ngay_ht).format("DD/MM/YYYY HH:mm:ss"); 
      this.listbox.TrungTamVT.value.donvi_id = obj.ttvt_id;
      this.DiaChi = obj.diachi;
      this.listbox.HinhThucHT.value.htht_id = obj.htht_id;
      this.GhiChu = obj.ghichu;
      //----------
      this.vds.PHANVUNG_ID = 26;
      this.vds.DUAN_ID = obj.duan_id;
      this.vds.MA_DUAN = obj.ma_duan;
      this.vds.TEN_DUAN = obj.ten_duan;
      this.vds.DIACHI = obj.diachi;
      this.vds.CHUDTU_ID = obj.chudtu_id;
      this.vds.PHUONG_ID = obj.phuong_id;
      this.vds.QUAN_ID = obj.quan_id;
      this.vds.GHICHU = obj.ghichu;
      this.vds.NGAY_CN = obj.ngay_cn;
      this.vds.MAY_CN = obj.may_cn? obj.may_cn: "";
      this.vds.NGUOI_CN = obj.nguoi_cn? obj.nguoi_cn: "";
      this.vds.IP_CN = obj.ip_cn? obj.ip_cn: "";
      this.vds.TTDA_ID = obj.ttda_id;
      this.vds.NGAY_TK = obj.ngay_tk;
      this.vds.NGAY_HT = obj.ngay_ht;
      this.vds.TTVT_ID = obj.ttvt_id;
      this.vds.NGUOI_CCTT = obj.nguoi_cctt;
      this.vds.HTHT_ID = obj.htht_id;
      this.vds.MA_CT = obj.ma_ct;
      this.vds.NGAY_NT = obj.ngay_nt;
      this.vds.SOHIEU_CT = obj.sohieu_ct;
      this.vds.SOQD_DT = obj.soqd_dt;
      this.vds.SOQD_QT = obj.soqd_qt;
      this.vds.DIACHI_ID = (obj.diachi_id==null? 0: obj.diachi_id); 
      //---------
      this.diachiTB.DIACHI = obj.diachi;
      this.diachiTB.TINH_ID = obj.tinh_id;
      this.diachiTB.QUAN_ID = obj.quan_id;
      this.diachiTB.PHUONG_ID = obj.phuong_id;
      this.diachiTB.PHO_ID = obj.pho_id;
      this.diachiTB.AP_ID = obj.ap_id;
      this.diachiTB.KHU_ID = obj.khu_id;
      this.diachiTB.DAC_DIEM_ID = obj.dacdiem_id;
      this.diachiTB.KINHDO = obj.kinhdo
      this.diachiTB.VIDO = obj.vido;
      this.diachiTB.BLOCK = obj.block;
      this.diachiTB.TANG = obj.tang;
      this.diachiTB.PHONG = obj.phong;
      this.diachiTB.MOTA = obj.mota;
      this.SetButton(3);
    },
     gridChanged(obj)
    {
      if(!obj) { 
        return; 
      }
      this.listbox.ChuDauTu.value.chudtu_id = obj.chudtu_id;
      this.DuAnID = obj.duan_id;
      this.MaDA =  obj.ma_duan;
      this.TenDA =  obj.ten_duan;
      this.listbox.dsTrangThai.value.ttda_id = obj.ttda_id;
      this.NguoiCungCap = obj.nguoi_cctt;
      console.log(obj);
      if (obj.ngay_ht != null)
      {
        const ngayHT_temp = obj.ngay_ht.replace(/(..)\/(..)\/(....) (..):(..)/, '$3-$2-$1 $4:$5');
        this.NgayHoanThanh = new Date(ngayHT_temp);
      }

      if (obj.ngay_tk != null)
      {
        const ngayTK_Temp = obj.ngay_tk.replace(/(..)\/(..)\/(....) (..):(..)/, '$3-$2-$1 $4:$5');
        this.NgayTrienKhai = new Date(ngayTK_Temp);
      }



      this.listbox.TrungTamVT.value.donvi_id = obj.ttvt_id;
      this.DiaChi = obj.diachi;
      this.listbox.HinhThucHT.value.htht_id = obj.htht_id;
      this.GhiChu = obj.ghichu;
      //----------
      this.vds.PHANVUNG_ID = 26;
      this.vds.DUAN_ID = obj.duan_id;
      this.vds.MA_DUAN = obj.ma_duan;
      this.vds.TEN_DUAN = obj.ten_duan;
      this.vds.DIACHI = obj.diachi;
      this.vds.CHUDTU_ID = obj.chudtu_id;
      this.vds.PHUONG_ID = obj.phuong_id;
      this.vds.QUAN_ID = obj.quan_id;
      this.vds.GHICHU = obj.ghichu;
      this.vds.NGAY_CN = obj.ngay_cn;
      this.vds.MAY_CN = obj.may_cn? obj.may_cn: "";
      this.vds.NGUOI_CN = obj.nguoi_cn? obj.nguoi_cn: "";
      this.vds.IP_CN = obj.ip_cn? obj.ip_cn: "";
      this.vds.TTDA_ID = obj.ttda_id;
      this.vds.NGAY_TK = obj.ngay_tk;
      this.vds.NGAY_HT = obj.ngay_ht;
      this.vds.TTVT_ID = obj.ttvt_id;
      this.vds.NGUOI_CCTT = obj.nguoi_cctt;
      this.vds.HTHT_ID = obj.htht_id;
      this.vds.MA_CT = obj.ma_ct;
      this.vds.NGAY_NT = obj.ngay_nt;
      this.vds.SOHIEU_CT = obj.sohieu_ct;
      this.vds.SOQD_DT = obj.soqd_dt;
      this.vds.SOQD_QT = obj.soqd_qt;
      this.vds.DIACHI_ID = (obj.diachi_id==null? 0: obj.diachi_id); 
      //---------
      this.diachiTB.DIACHI = obj.diachi;
      this.diachiTB.TINH_ID = obj.tinh_id;
      this.diachiTB.QUAN_ID = obj.quan_id;
      this.diachiTB.PHUONG_ID = obj.phuong_id;
      this.diachiTB.PHO_ID = obj.pho_id;
      this.diachiTB.AP_ID = obj.ap_id;
      this.diachiTB.KHU_ID = obj.khu_id;
      this.diachiTB.DAC_DIEM_ID = obj.dacdiem_id;
      this.diachiTB.KINHDO = obj.kinhdo
      this.diachiTB.VIDO = obj.vido;
      this.diachiTB.BLOCK = obj.block;
      this.diachiTB.TANG = obj.tang;
      this.diachiTB.PHONG = obj.phong;
      this.diachiTB.MOTA = obj.mota;
      this.SetButton(3);
    },
    btnLayTT()
    {
      this.LoadDSDuAn();
    },
    btnGhiLai: async function ()
    {
      if (this.MaDA == "")
      {
        this.ShowAlert("Bạn chưa nhập mã dự án",2);
        this.$refs.MaDA.focus();
        return;
      }
       if (this.TenDA == "")
      {
        this.ShowAlert("Bạn chưa nhập tên dự án",2);
        this.$refs.TenDA.focus();
        return;
      }
      console.log(this.$v.diachiTB.DIACHI.$model)
      if (this.$v.diachiTB.DIACHI.$model == null)
      {
        this.ShowAlert("Bạn chưa nhập địa chỉ",2);
        return;
      }
      if (this.NgayTrienKhai == "")
      {
        this.ShowAlert("Bạn chưa nhập ngày triển khai",2);
        this.$refs.NgayTK.focus();
        return;
      }
      const check = (NgayTrienKhai) => moment(NgayTrienKhai, ['DD/MM/YYYY HH:mm:ss', 'DD/MM/YYYY', 'YYYY-MM-DD HH:mm', 'YYYY-MM-DD'], true).isValid() && !moment(NgayTrienKhai, ['DD/MM/YYYY HH:mm:ss', 'DD/MM/YYYY', moment.ISO_8601], true).isBefore('2024-08-01');
      
      if (this.NgayHoanThanh == "" && check(this.NgayTrienKhai))
      {
        this.ShowAlert("Bạn chưa nhập ngày hoàn thành",2);
        this.$refs.NgayHT.focus();
        return;
      }
      
      this.vds.PHANVUNG_ID = 26;
        this.vds.DUAN_ID = this.DuAnID;
        this.vds.MA_DUAN = this.MaDA;
        this.vds.TEN_DUAN = this.TenDA;
        this.vds.CHUDTU_ID = this.listbox.ChuDauTu.value.chudtu_id;
        this.vds.GHICHU = this.GhiChu;
        this.vds.NGAY_CN = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
        this.vds.MAY_CN = "";
        this.vds.NGUOI_CN = "";
        this.vds.IP_CN = "";
        this.vds.TTDA_ID = this.listbox.dsTrangThai.value.ttda_id;
        this.vds.NGAY_TK =  this.NgayTrienKhai;
        this.vds.NGAY_HT = this.NgayHoanThanh;
        this.vds.TTVT_ID = this.listbox.TrungTamVT.value.donvi_id;
        this.vds.NGUOI_CCTT = this.NguoiCungCap;
        this.vds.HTHT_ID = this.listbox.HinhThucHT.value.htht_id;
      
      if (!this.isNhapMoi) //Thêm mới
      {
        var input = {};
        input.vphanvung_id = 26;
        input.vkieu = 1;
        input.vds = JSON.stringify([this.vds]);
        input.vds_diachi = JSON.stringify([this.diachiTB]);
        console.log(input);

        this.loading(true);
        await API.capnhat_tt_duan(this.axios, input).then((response) => {
          this.loading(false);
          if(response.data.error_code && response.data.error_code === this.success_code) {
              this.ShowAlert("Thêm mới dự án thành công!",0);
              this.LoadDSDuAn();
          }
          else
          {
            this.loading(false);
            this.ShowAlert("Lỗi thêm mới dự án!",2);
          }
        }
        ); 
      }
      else //Cập nhật
      {
        var input = {};
        input.vphanvung_id = 26;
        input.vkieu = 2;
        input.vds = JSON.stringify([this.vds]);
        input.vds_diachi = JSON.stringify([this.diachiTB]);
        console.log(input);

        this.loading(true);
        await API.capnhat_tt_duan(this.axios, input).then((response) => {
          this.loading(false);
          if(response.data.error_code && response.data.error_code === this.success_code) {
              this.ShowAlert("Cập nhật dự án thành công!",0);
              this.LoadDSDuAn();
          }
          else
          {
            this.loading(false);
            this.ShowAlert("Lỗi cập nhật dự án!",2);
          }
        }
        ); 
      }
    },

    btnXoa: async function ()
    {
      this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xoá dự án này không?', {
      title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
      if(v) {
        var input = {};
        input.vphanvung_id = 26;
        input.vkieu = 3;
        input.vds = JSON.stringify([this.vds]);
        input.vds_diachi = JSON.stringify([this.diachiTB]);
        console.log(input);
        this.loading(true);
        await API.capnhat_tt_duan(this.axios, input).then((response) => {
          this.loading(false);
          if(response.data.error_code && response.data.error_code === this.success_code && response.data.data != "ERROR-Bạn không thể xóa bản ghi này! Dự án này đang tồn tại Tòa nhà") {
              this.ShowAlert("Xóa dự án thành công!",0);
              this.LoadDSDuAn();
          }
          else
          {
            this.loading(false);
            this.ShowAlert(response.data.data,2);
          }
        }
        ); 
      }
      });
    },

    popupClosed(val) {
      console.log(val);
      this.$bvModal.hide('popupComponents');
    },

    btnTienTrinhDA()
    {
      this.popupComponent = ()=>import('./components/FollowProject.vue');
      this.popupComponentName = "FollowProject";
      this.popupTitle = "Tiến trình dự án"
      console.log(this.TenDA);
      this.popupComponentData = { vDuAnId: this.DuAnID, vTenDuAn: this.TenDA}
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
