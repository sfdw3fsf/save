<template src="./HandoverTicket.html"></template>
<style src="./HandoverTicket.scss"></style>
<script>
import VueFlatPickr from 'vue-flatpickr-component';
import DatePicker from 'vue2-datepicker' 
import 'vue2-datepicker/locale/vi.js' 
import 'vue2-datepicker/index.css'

import API from './API'
import { required, minLength } from 'vuelidate/lib/validators'
import XLSX from 'xlsx'
import SearchContractModal from './components/SearchContractModal.vue'
import moment from 'moment'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
  components: {VueFlatPickr,API,XLSX,SearchContractModal,DatePicker },
  name: 'HandoverTicket',
  props: { //quanghs.tvh bo sung de show form giao viec tren ejs-dialog
    isPopup: {
      type: Boolean,
      default: false
    }
  },
  mounted: async function() {
    this.ngaygiao_tk = moment(new Date()).format("DD/MM/YYYY"); 
    this.ngaygiao = moment(new Date()).format("DD/MM/YYYY"); 
    if(!isPopup)
    {
      await this.LoadCombobox();
      this.listbox.trangthaiphieu.value.id =1;
      if (this.$route.query.trangthaiphieu)
      {
        this.listbox.trangthaiphieu.value.id = (this.$route.query.trangthaiphieu ==""? 1: this.$route.query.trangthaiphieu);
      }
      //Loại hợp đồng
      if (this.$route.query.loaihd && this.$route.query.loaihd !="")
      {
        this.isTimTheoLoaiHD = true;
        this.listbox.loaihd.value.LOAIHD_ID =  this.$route.query.loaihd;
      }  
      //Dịch vụ
      this.DICHVU = 1;
      if (this.$route.query.madv && this.$route.query.madv !="")
      {
        this.isTimTheoDichVu = true;
        this.DICHVU =  this.$route.query.madv;
        this.listbox.dichvu.value.DICHVUVT_ID = this.$route.query.madv;
      }
      //Quy trình
      if (this.$route.query.quytrinh && this.$route.query.quytrinh !="")
      {
        this.isTimTheoQuyTrinh = true;
        this.listbox.quytrinh.value.quytrinh_id =  this.$route.query.quytrinh;
      }   
      await this.LoadHuongGiao();
      if (this.$route.query.huonggiao && this.$route.query.huonggiao !="")
      {
        this.listbox.huonggiao.value.huonggiao_id =  this.$route.query.huonggiao;
      }  
      if(this.$route.query.magd){
          this.txtMaGD=this.$route.query.magd;
          if (this.txtMaGD !="")
          {
            await this.onMaGDEnter();
          }
      }
    }
  },
  data () {
    return {
      dateconfig: {
        altFormat: 'd/m/Y H:i:S',
        altInput: true,
        dateFormat: 'd/m/Y H:i:S',
        allowInput:true,
      },
      dateFormat: "DD/MM/YYYY",
      ngay_ht: new Date(),
      tsbtnHuyGiao:true,
      tsbtnCapNhatDV:true,
      tsbtnTimKiem:true,
      tsbtnGiaoPhieu:true,
      ctmsGiaoPhieu:true,
      ctmsHuyPhieu: true,
      isTimTheoQuyTrinh:false,
      isTimTheoDichVu:false,
      isTimTheoLoaiHD: false,

    listbox: {
        thamso: {
          list: [],
          value: {},
        },
        loaihd: {
          list: [],
          value: {},
        },
       dichvu: {
          list: [],
          value: {},
        },
        quytrinh: {
          list: [],
          value: {},
        },
        trangthaiphieu: {
          list: [{id:1,text:"Chưa giao"},{id:2,text:"Đã giao"}],
          value: {},
        },
        donvinhan: {
          list: [],
          value: {},
        },
        huonggiao: {
          list: [],
          value: {},
        },
        ds_phieuyeucau: {
          list: [],
          header: [ {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, width: 150}, 
                      {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, width: 350},
                      {fieldName: 'diachi_tb', headerText: 'Địa chỉ lắp đặt', allowFiltering: true , allowHtml : true, width: 400},
                      {fieldName: 'ttyc', headerText: 'Thông tin yêu cầu', allowFiltering: true , allowHtml : true, width: 300  },                                
                      {fieldName: 'sophieu', headerText: 'Số QĐ', allowFiltering: true , allowHtml : true, width: 150},                                     
                      {fieldName: 'donvinhan', headerText: 'Đơn vị nhận', allowFiltering: true , allowHtml : true},                                     
                      {fieldName: '', headerText: 'Nội dung trả', allowFiltering: true , allowHtml : true},],
          value: {},
          isEnabled: true,
          checked: []
        },
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      ngaygiao_tk: new Date(),
      ngaygiao: new Date(),

      success_code: "BSS-00000000",
      NHANTIN_NHANVIEN_PHOIHOP : -1,
      SEND_SMS_NHANTIN_NV : -1,
      NHANTIN_THEO_HG: -1,
      ts_kt_tthdkhdn : 0, //Kiểm tra cho phép thêm thông tin khách hàng DN  đối với PTTB KHDN mới xài
      kt_phieutra_gdv : false,
      _pSendSMS: false,
      _Giaoviec_td: false,
      _Giaoviec_nvkd: false,

      DV_BTSL : 9, //Mã dịch vụ truyền số liệu
      txtMaGD:"",
      txtMaTBChon:"",
      txtMaGDChon:"",
      txtSoPhieu:"",
      txtNoiDung:"",
      txtDiaChiTC:"",
      loaidv_nhan_id : "-1",
      donvi_id: "",
      nhanvien_id: "",
      ngay_cn: new Date(),
      ma_nd: "",
      may_cn: "",
      ip: "",
      huonggiao_id: "",
      kt_daucuoi: "",
      _Giaoviec_td: false, //Giao việc tự động cho nhân viên kỹ thuật
      _Giaoviec_nvkd: false, //Giao việc tự động cho nhân viên kinh doanh
      DIEUHANH_THICONG: 1,
      HUONG_LAPMOI_CD: 202,
      HUONG_LAPMOI_FTTH: 206,
      HUONG_LAPMOI_MYTV: 207,
      DICHVU: 1, //1:CĐ, 4: BRCD, 10:MYTV
     
    }
  },
  computed: {
    NhapMoi: {
      get() { return this.isNhapMoi; },
      set(value) { this.isNhapMoi=value; 
      }
    },
    CapNhat: {
      get() { return this.isCapNhat; },
      set(value) { this.isCapNhat=value; 
      }
    },
     HuyBo: {
      get() { return this.isHuyBo; },
      set(value) { this.isHuyBo=value; 
      }
    },
    XoaPhieu: {
      get() { return this.isXoaPhieu; },
      set(value) { this.isXoaPhieu=value; 
      }
    },

    timtheohd: {
      get() { return this.isTimTheoLoaiHD; },
      set(value) { 
        this.isTimTheoLoaiHD=value; 
        if (!this.isTimTheoLoaiHD)
        {
          this.isTimTheoDichVu=value; 
        }
        this.listbox.loaihd.value.LOAIHD_ID = 1;
        this.LoadQuyTrinh();
        this.LoadHuongGiao();
        }
    },
    timtheodichvu: {
      get() { return this.isTimTheoDichVu; },
      set(value) { 
        this.isTimTheoDichVu=value; 
        if (!this.isTimTheoDichVu)
        {
          this.isTimTheoQuyTrinh=value; 
        }
        this.listbox.dichvu.value.DICHVUVT_ID = 4;
        this.LoadQuyTrinh();
        this.LoadHuongGiao();
        }
    },
    timtheoquytrinh: {
      get() { return this.isTimTheoQuyTrinh; },
      set(value) { 
        this.isTimTheoQuyTrinh=value; 
        this.listbox.quytrinh.value.quytrinh_id = 813;
        this.listbox.quytrinh.value.nhom_qt_id = 1;
        //this.LoadQuyTrinh();
        this.LoadHuongGiao();
        }
    },
    timall: {
      get() { return !this.isCachTim; },
      set(value) { this.isCachTim=!value; }
    },
    
  },
  validations: {
    MaTB: {
      required,
      minLength: minLength(1)
    },
    DK_tungay: {
      required,
      minLength: minLength(10)
    },
    DK_denngay: {
      required,
      minLength: minLength(10)
    },
    listbox:
    {
      goidadichvu: 
      {
        idx: {required, minLength: minLength(1)}
      }
    }
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

    formatPrice(value) {
        let val = (value/1).toFixed(0).replace(',', '.')
        return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
    },

    showPopupSearchContract(){   
      this.$refs.popupSearchContract.showModal();
    },
    acceptSearchContract(item){
      this.txtMaGD = item.ma_gd;
      if(this.txtMaGD && this.txtMaGD.trim()!="")
        this.onMaGDEnter();
    },

     selectedItems_click: function (dataKeys) {
      this.listbox.ds_phieuyeucau.checked = dataKeys
      this.listbox.ds_phieuyeucau.list.forEach(function (item) {
        if (dataKeys.filter(x => x === item.phieu_id).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
    },  

    LoadCombobox()
    {
      //Load tham số
      var input = {};
      API.lay_ds_thamso_md_mats(this.axios, input).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
            this.listbox.thamso.list = response.data.data;
            if (this.listbox.thamso.list.length>0)
            {
              var temp = this.listbox.thamso.list.filter(x=> x.ma_ts === 'NHANTIN_NHANVIEN_PHOIHOP');
              if (temp.length>0)
              {
                //console.log(temp);
                if (temp[0]["ten_ts"].toString() == "1")
                {
                  this.NHANTIN_NHANVIEN_PHOIHOP = 1;
                }
              }

              var temp1 = this.listbox.thamso.list.filter(x=> x.ma_ts === 'THEM_TT_HDKHDN');
              if (temp1.length>0)
              {
                //console.log(temp1);
                if (temp1[0]["ten_ts"].toString() == "1")
                {
                  this.ts_kt_tthdkhdn = 1;

                }
              }

              var temp2 = this.listbox.thamso.list.filter(x=> x.ma_ts === 'KT_PHIEUTRA_GDV');
              if (temp2.length>0)
              {
                //console.log(temp2);
                if (temp2[0]["ten_ts"].toString() == "1")
                {
                  this.kt_phieutra_gdv = true;
                }
              }

              var temp3 = this.listbox.thamso.list.filter(x=> x.ma_ts === 'SEND_SMS_NHANTIN_NV');
              if (temp3.length>0)
              {
                //console.log(temp2);
                if (temp3[0]["ten_ts"].toString() == "1")
                {
                  this.SEND_SMS_NHANTIN_NV = 1;
                }
              }

              var temp4 = this.listbox.thamso.list.filter(x=> x.ma_ts === 'NHANTIN_THEO_HG');
              if (temp4.length>0)
              {
                //console.log(temp2);
                if (temp4[0]["ten_ts"].toString() == "1")
                {
                  this.NHANTIN_THEO_HG = 1;
                }
              }
            }
        }
        else
        {
          this.ShowAlert("Không tìm thấy danh sách dịch tham số dịch vụ",2);
        }
      }
      ); 

      //Load loại hợp đồng
      try{
        var input = {};
        API.CSS_LOAI_HD(this.axios, input).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
            this.listbox.loaihd.list = response.data.data.filter(x=>x.LOAIHD_ID > 0).map(x=>({id:x.LOAIHD_ID,text:x.TEN_LOAIHD}));
        }
        else
        {
          this.listbox.loaihd.list = [];
          this.ShowAlert("Không tìm thấy danh sách loại hợp đồng!",2);
        }
        }
        ); 
      }catch(e){ this.ShowAlert("Lỗi lấy danh sách loại hợp đồng",2); this.loading(false);}
      //Load dịch vụ VT
      try{
        var input = {};
        API.CSS_DICHVU_VT(this.axios, input).then((response) => {
          if(response.data.error_code && response.data.error_code === this.success_code) {
              this.listbox.dichvu.list = response.data.data.filter(x=>x.DICHVUVT_ID > 0).map(x=>({id:x.DICHVUVT_ID,text:x.TEN_DVVT}));
          }
          else
          {
            this.ShowAlert("Không tìm thấy danh sách dịch vụ Viễn thông!",2);
          }
        }
        ); 
      }catch(e){ this.ShowAlert("Lỗi lấy danh sách dịch vụ Viễn thông!",2);this.loading(false); }
      this.HienThi_DanhSach();
    },

    LoadQuyTrinh()
    {
      //Load qui trình
      try{
        var input = {};
        API.list_all_quytrinh(this.axios, input).then((response) => {
          if(response.data.error_code && response.data.error_code === this.success_code) {
              this.listbox.quytrinh.list = response.data.data.filter(x=>x.quytrinh_id > 0 && x.loaihd_id == (this.listbox.loaihd.value.LOAIHD_ID == null? 0: this.listbox.loaihd.value.LOAIHD_ID) && x.dichvuvt_id == (this.listbox.dichvu.value.DICHVUVT_ID == null? 0: this.listbox.dichvu.value.DICHVUVT_ID)).map(x=>({id:x.quytrinh_id,text:x.quytrinh}));
              console.log(this.listbox.quytrinh.list);
              if (this.listbox.quytrinh.list.length > 0)
              {
                this.listbox.quytrinh.value.quytrinh_id = this.listbox.quytrinh.list[0].id;
              }
          }
          else
          {
            this.listbox.huonggiao.list = [];
            this.ShowAlert("Không tìm thấy thông tin quy trình!",2);
          }
        }
        ); 
      }catch(e){ this.ShowAlert("Lỗi lấy thông tin quy trình!",2);this.loading(false); }
    },

    LoadHuongGiao: async function ()
    {
      
      //Load hướng giao
      try{
        var input = {};
        input.nguoidung_id = this.$root.token.getNguoiDungID(); 
        input.quytrinh_id = this.isTimTheoQuyTrinh? this.listbox.quytrinh.value.quytrinh_id : 0;
        input.loaihd_id = this.isTimTheoLoaiHD? this.listbox.loaihd.value.LOAIHD_ID : 0;
        input.dichvuvt_id = this.isTimTheoDichVu? this.listbox.dichvu.value.DICHVUVT_ID : 0;
        input.nhom_qt_id = this.DIEUHANH_THICONG;
        if (this.isTimTheoLoaiHD)
        {
          input.loaihd_id = (this.listbox.loaihd.value.LOAIHD_ID == null? 0: this.listbox.loaihd.value.LOAIHD_ID);
        }
        if (this.isTimTheoDichVu)
        {
          input.dichvuvt_id = (this.listbox.dichvu.value.DICHVUVT_ID == null? 0: this.listbox.dichvu.value.DICHVUVT_ID);
        }

        if (this.isTimTheoQuyTrinh)
        {
          input.nhom_qt_id = (this.listbox.quytrinh.value.nhom_qt_id == null? 1: this.listbox.quytrinh.value.nhom_qt_id);
          input.quytrinh_id = (this.listbox.quytrinh.value.quytrinh_id == null? 0: this.listbox.quytrinh.value.quytrinh_id);
        }
        //console.log(input);
        await API.sp_lay_ds_huonggiao(this.axios, input).then((response) => {
          if(response.data.error_code && response.data.error_code === this.success_code) {
            this.listbox.huonggiao.list = response.data.data.filter(x=>x.huonggiao_id > 0).map(x=>({id:x.huonggiao_id,text:x.huonggiao}));
            //console.log(this.listbox.huonggiao.list);
            if (this.listbox.huonggiao.list.length>0)
            {
              if (this.DICHVU == 1) //dịch vụ cố định
              {
                this.listbox.huonggiao.value.huonggiao_id = this.HUONG_LAPMOI_CD;
              }
              else if (this.DICHVU == 4)
              {
                this.listbox.huonggiao.value.huonggiao_id = this.HUONG_LAPMOI_FTTH;
              }
              else if (this.DICHVU == 10)
              {
                this.listbox.huonggiao.value.huonggiao_id = this.HUONG_LAPMOI_MYTV;
              }
              this.HuongGiaoChange();
            }
          }
          else
          {
            this.listbox.huonggiao.list = [];
            this.ShowAlert("Không tìm thấy hướng giao!",2);
          }
        }
        ); 
      }catch(e){ this.ShowAlert("Lỗi lấy danh sách hướng giao",2);this.loading(false); }
      this.LoadQuyTrinh();
      this.HienThi_DanhSach();
    },

    HienThi_DanhSach()
    {
      if (this.listbox.trangthaiphieu.value.id == 1)//Chưa giao
      {
        this.tsbtnHuyGiao = false;
        this.tsbtnTimKiem = true;
        this.tsbtnGiaoPhieu = true;
        this.ctmsGiaoPhieu = true;
        this.ctmsHuyPhieu = false;
        this.tsbtnCapNhatDV = true;
      }
      else if (this.listbox.trangthaiphieu.value.id == 2)//đã giao
      {
        var input = {};
        var KHONG_HUY_GIAOPHIEU = -1;
        API.lay_ds_thamso_md_mats(this.axios, input).then((response) => {
          if(response.data.error_code && response.data.error_code === this.success_code) {
              this.listbox.thamso.list = response.data.data;
              if (this.listbox.thamso.list.length>0)
              {
                var temp = this.listbox.thamso.list.filter(x=> x.ma_ts === 'KHONG_HUY_GIAOPHIEU');
                if (temp.length>0)
                {
                  KHONG_HUY_GIAOPHIEU = parseInt(temp[0]["ten_ts"]);
                }
              }
          }
          else
          {
            this.ShowAlert(response.data.message,2);
          }
        }
        ); 
        if (KHONG_HUY_GIAOPHIEU == 1)
            this.tsbtnHuyGiao = false;
        else
            this.tsbtnHuyGiao = true;
        this.tsbtnTimKiem = false;
        this.tsbtnGiaoPhieu = false;
        this.ctmsGiaoPhieu = false;
        this.ctmsHuyPhieu = true;
        this.tsbtnCapNhatDV = false;

      }

      if (this.listbox.huonggiao.value.huonggiao_id == null)
      {
        this.listbox.ds_phieuyeucau.list = [];
        //this.ShowAlert("Bạn cần chọn hướng giao");
        return;
      }
      
      if (this.listbox.huonggiao.value.sms == "1")
      {
        this._pSendSMS=true;
      }
      else
      {
        this._pSendSMS = false;
      }
      //
      if (this.listbox.huonggiao.value.giaoviec == "1")
      {
        this._Giaoviec_td=true;
      }
      else
      {
        this._Giaoviec_td = false;
      }
      //
      if (this.listbox.huonggiao.value.giaoviec_nvkd == "1")
      {
        this._Giaoviec_nvkd=true;
      }
      else
      {
        this._Giaoviec_nvkd = false;
      }
      if (this.listbox.trangthaiphieu.value.id == "1")//Chưa giao
      {
        try{
          var input = {};
          input.dichvuvt_id = this.isTimTheoDichVu? this.listbox.dichvu.value.DICHVUVT_ID: 0; // ((this.listbox.dichvu.value.DICHVUVT_ID ==null || this.isTimTheoLoaiHD == false)? 0: this.listbox.dichvu.value.DICHVUVT_ID);
          input.loaihd_id = this.isTimTheoLoaiHD? this.listbox.loaihd.value.LOAIHD_ID: 0; //(this.listbox.loaihd.value.LOAIHD_ID == null? 0: this.listbox.loaihd.value.LOAIHD_ID);
          input.huonggiao_id = (this.listbox.huonggiao.value.huonggiao_id == null? 0: this.listbox.huonggiao.value.huonggiao_id); 
          input.donvi_id = this.$root.token.getDonViID();  
          input.ma_gd = (this.txtMaGD == ""? "0": this.txtMaGD);
          input.nhanvien_id = this.$root.token.getNhanVienID(); 
          console.log(input);
          this.loading(true);
          API.sp_lay_ds_hdtb_chuagiao(this.axios, input).then((response) => {
            this.loading(false);
            if(response.data.error_code && response.data.error_code === this.success_code) {
              this.listbox.ds_phieuyeucau.list = response.data.data;
              if (this.txtMaGD != "")
              {
                this.listbox.ds_phieuyeucau.list = this.listbox.ds_phieuyeucau.list.filter(x => x.ma_gd == this.txtMaGD);
                if(this.listbox.ds_phieuyeucau.list.length<=0)
                {
                  this.ShowAlert("Không tìm thấy phiếu",2);
                }
              }
              if (this.listbox.ds_phieuyeucau.list.length==0)
              {
                this.txtMaTBChon = "";
                this.txtMaGDChon =  "";
                this.txtSoPhieu = "";
                this.txtNoiDung = "";
                this.txtDiaChiTC = "";
              }
              this.listbox.ds_phieuyeucau.header =[ {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, width: 150}, 
                {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, width: 350},
                {fieldName: 'diachi_tb', headerText: 'Địa chỉ lắp đặt', allowFiltering: true , allowHtml : true, width: 400},
                {fieldName: 'ttyc', headerText: 'Thông tin yêu cầu', allowFiltering: true , allowHtml : true, width: 300},                                
                {fieldName: 'sophieu', headerText: 'Số QĐ', allowFiltering: true , allowHtml : true, width: 150},                                     
                {fieldName: 'donvinhan', headerText: 'Đơn vị nhận', allowFiltering: true , allowHtml : true},                                     
                {fieldName: 'nd_tra_con', headerText: 'Nội dung trả', allowFiltering: true , allowHtml : true},]
            }
            else
            {
              this.listbox.ds_phieuyeucau.list = [];
              this.txtMaTBChon = "";
              this.txtMaGDChon =  "";
              this.txtSoPhieu = "";
              this.txtNoiDung = "";
              this.txtDiaChiTC = "";
              this.ShowAlert("Không tìm thấy phiếu",2);
            }
          }
          ); 
        }catch(e){ this.ShowAlert("Có lỗi lấy danh sách phiếu chưa giao!",2);this.loading(false); }
        
      }
      else if(this.listbox.trangthaiphieu.value.id == "2") //đã giao
      {
        try{
          var input = {};
          input.dichvuvt_id = this.isTimTheoDichVu? this.listbox.dichvu.value.DICHVUVT_ID: 0; //((this.listbox.dichvu.value.DICHVUVT_ID == null || this.isTimTheoLoaiHD == false)? 0: this.listbox.dichvu.value.DICHVUVT_ID);
          input.loaihd_id = this.isTimTheoLoaiHD? this.listbox.loaihd.value.LOAIHD_ID: 0; //(this.listbox.loaihd.value.LOAIHD_ID == null? 0: this.listbox.loaihd.value.LOAIHD_ID);
          input.huonggiao_id = (this.listbox.huonggiao.value.huonggiao_id == null? 0: this.listbox.huonggiao.value.huonggiao_id); 
          input.donvi_id = this.$root.token.getDonViID();
          input.loaipt_id = 0;
          input.ngay_giao = (this.ngaygiao_tk == null? 0: this.ngaygiao_tk); 
          input.ma_gd = (this.txtMaGD == ""? "0": this.txtMaGD);
          input.nhanvien_id = this.$root.token.getNhanVienID(); 
          //console.log(input);
          this.loading(true);
          API.lay_ds_hdtb_dagiao(this.axios, input).then((response) => {
            this.loading(false);
            if(response.data.error_code && response.data.error_code === this.success_code) {
              this.listbox.ds_phieuyeucau.list = response.data.data;
              if (this.txtMaGD != "")
              {
                this.listbox.ds_phieuyeucau.list = this.listbox.ds_phieuyeucau.list.filter(x => x.ma_gd == this.txtMaGD);
                if(this.listbox.ds_phieuyeucau.list.length<=0)
                {
                  this.ShowAlert("Không tìm thấy phiếu",2);
                }
              }
              if (this.listbox.ds_phieuyeucau.list.length==0)
              {
                this.txtMaTBChon = "";
                this.txtMaGDChon =  "";
                this.txtSoPhieu = "";
                this.txtNoiDung = "";
                this.txtDiaChiTC = "";
              }
              this.listbox.ds_phieuyeucau.header =[ {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, width: 150}, 
                {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, width: 350},
                {fieldName: 'diachi_tb', headerText: 'Địa chỉ lắp đặt', allowFiltering: true , allowHtml : true, width: 400},
                {fieldName: 'ttyc', headerText: 'Thông tin yêu cầu', allowFiltering: true , allowHtml : true, width: 300},                                
                {fieldName: 'sophieu', headerText: 'Số QĐ', allowFiltering: true , allowHtml : true, width: 150},                                     
                {fieldName: 'donvinhan', headerText: 'Đơn vị nhận', allowFiltering: true , allowHtml : true},                                     
                {fieldName: 'nd_giao', headerText: 'Nội dung giao', allowFiltering: true , allowHtml : true},]
            }
            else
            {
              this.listbox.ds_phieuyeucau.list = [];
              this.txtMaTBChon = "";
              this.txtMaGDChon =  "";
              this.txtSoPhieu = "";
              this.txtNoiDung = "";
              this.txtDiaChiTC = "";
              this.ShowAlert("Không tìm thấy phiếu",2);
            }
          }
          ); 
        }catch(e){ this.ShowAlert("Có lỗi lấy danh sách phiếu đã giao!",2); this.loading(false);}
        
      }

    },

    async HuongGiaoChange()
    {
      await this.GetDSDonViNhan();
      await this.HienThi_DanhSach();
    },

    GetDSDonViNhan()
    {
      //lấy ra loại đơn vị đích mà hướng giao giao đến
      try{
        var input = {};
        input.huonggiao_id = this.listbox.huonggiao.value.huonggiao_id;
        API.fn_lay_loaidv_dich_theo_hg(this.axios, input).then((response) => {
          if(response.data.error_code && response.data.error_code === this.success_code) {
            this.loaidv_nhan_id = response.data.data;
            if (this.loaidv_nhan_id != "-1") 
            {
              var input1 = {};
              input1.huonggiao_id = this.listbox.huonggiao.value.huonggiao_id;
              input1.loaidv_id = this.loaidv_nhan_id;
              //console.log(input1);
              API.sp_ht_tatca_vetinh_combobox_v2(this.axios, input1).then((response) => {
                if(response.data.error_code && response.data.error_code === this.success_code) {
                  this.listbox.donvinhan.list = response.data.data.map(x=>({id:x.donvi_id,text:x.ten_dv}));
                  //console.log(this.listbox.donvinhan.list);
                }
                else
                {
                  this.listbox.donvinhan.list = [];
                  this.ShowAlert("Có lỗi lấy danh sách đơn vị nhận!",2);
                }
                }
              );
            }
          }
          else
          {
            this.listbox.donvinhan.list = [];
            this.ShowAlert("Có lỗi lấy danh sách đơn vị nhận!",2);
          }
          }
        );
      }catch(e){ this.ShowAlert("Lỗi lấy danh sách loại hợp đồng",2);this.loading(false); }
    },

    btnTimKiem()
    {
      //this.HienThi_DanhSach();
      this.HienThi_DanhSach_v1();
    },
    
    btnGiaoPhieu()
    {
      if (this.listbox.huonggiao.value.huonggiao_id == null)
      {
        this.ShowAlert("Bạn chưa chọn hướng giao",2);
        return;
      }
      if (this.txtNoiDung == "" || this.txtNoiDung == null)
      {
        this.ShowAlert("Bạn chưa nhập nội dung giao",2);
        this.$refs.txtNoiDung.focus();
        return;
      }
      
      var currentdate= new Date();
     /*  console.log(moment(this.ngaygiao).format("DD/MM/YYYY"),moment(currentdate).format("DD/MM/YYYY"));
      if (moment(this.ngaygiao).format("DD/MM/YYYY") > moment(currentdate).format("DD/MM/YYYY"))
      {
        this.ShowAlert("Ngày giao không được lớn hơn ngày hiện tại",1);
        this.$refs.ngaygiao.focus();
        return;
      } */
      
      //LẤY DỮ LIỆU VÀ GIAO PHIẾU
      var listphieuchuyen = [];
      if (this.listbox.ds_phieuyeucau.checked.length>0)
      {
        listphieuchuyen =  this.listbox.ds_phieuyeucau.checked;
        listphieuchuyen = listphieuchuyen.map( function( item ){ 
                    for(var key in item){ 
                        var upper = key.toUpperCase(); 
                        // check if it already wasn't uppercase 
                        if( upper !== key ){  
                            item[ upper ] = item[key]; 
                            delete item[key]; 
                        } 
                    } 
                    return item; 
        });
      }
      else
      {
        this.ShowAlert("Bạn chưa chọn phiếu để chuyển",2);
        return;
      }
      try{
        //console.log(listphieuchuyen);
        this.donvi_id = this.$root.token.getDonViID();
        this.nhanvien_id = this.$root.token.getNhanVienID();
        this.ngay_cn =  new Date();
        this.ma_nd = this.$root.token.getUserName();
        this.may_cn = "";
        this.ip = "";
        this.huonggiao_id = this.listbox.huonggiao.value.huonggiao_id;
        if (this.listbox.huonggiao.value.giaoviec == 1)
        {
          this._Giaoviec_td = true;
        }
        else
        {
          this._Giaoviec_td = false;
        }
        if (this.listbox.huonggiao.value.giaoviec_nvkd == 1)
        {
          this._Giaoviec_nvkd = true;
        }
        else
        {
          this._Giaoviec_nvkd = false;
        }
        var input = {};
        input.json_dsphieu = JSON.stringify(listphieuchuyen);
        input.donvi_id =  this.donvi_id;
        input.nhanvien_id =  this.nhanvien_id;
        input.ngay_cn = moment(this.ngay_cn).format("DD/MM/YYYY HH:mm:ss");  
        input.nguoi_cn = this.ma_nd;
        input.may_cn = this.may_cn;
        input.ip_cn = this.ip;
        input.huonggiao_id = this.huonggiao_id;
        input.sophieu= (this.txtSoPhieu == null? "": this.txtSoPhieu);
        input.noidung = this.txtNoiDung;
        input.kt_daucuoi = this.kt_daucuoi;
        input.taoduyet = 0;
        input.nt_phoihop = this.NHANTIN_NHANVIEN_PHOIHOP;
        input.nt_nhanvien = this.SEND_SMS_NHANTIN_NV;
        input.nt_huonggiao = this.NHANTIN_THEO_HG;
        // input.giaovieckt = this._Giaoviec_td;
        // input.giaovieckd = this._Giaoviec_nvkd;
        //console.log(input);
        this.loading(true);
        API.fn_giaophieu(this.axios, input).then((response) => {
          this.loading(false);
          if(response.data.error_code && response.data.error_code === this.success_code) {
            this.ShowAlert("Giao phiếu thành công!",0);
            //Load lại danh sách
            this.HienThi_DanhSach();
          }
          else
          {
            this.ShowAlert("Giao phiếu không thành công!",2);
          }
          }
        );  
      }catch(e){ this.ShowAlert("Lỗi lấy danh sách loại hợp đồng",2);this.loading(false); }
      
    },

    btnHuyGiao()
    {
      if (this.listbox.huonggiao.value.huonggiao_id == null)
      {
        this.ShowAlert("Bạn chưa chọn hướng trả",2);
        return;
      }
      var listphieuchuyen = [];
      if (this.listbox.ds_phieuyeucau.checked.length>0)
      {
        listphieuchuyen =  this.listbox.ds_phieuyeucau.checked;
        listphieuchuyen = listphieuchuyen.map( function( item ){ 
                    for(var key in item){ 
                        var upper = key.toUpperCase(); 
                        // check if it already wasn't uppercase 
                        if( upper !== key ){  
                            item[ upper ] = item[key]; 
                            delete item[key]; 
                        } 
                    } 
                    return item; 
        });
      }
      else
      {
        this.ShowAlert("Bạn chưa chọn phiếu để chuyển",2);
        return;
      }
      var temp =[];
      temp = listphieuchuyen.map(x=>({CHON:1,PHIEU_ID:x.PHIEU_ID,HDTB_ID:x.HDTB_ID,MA_TB:x.MA_TB,TTPH_ID:x.TTPH_ID,LOAIPT_ID:x.LOAIPT_ID,STATUS:x.STATUS,PHIEU_CHA_ID:x.PHIEU_CHA_ID}));
      try{
        var input = {};
        input.json_ds_phieu = JSON.stringify(temp);
        input.huonggiao_id = this.listbox.huonggiao.value.huonggiao_id;
        //console.log(input);
        this.loading(true);
        API.fn_huyphieu(this.axios, input).then((response) => {
          this.loading(false);
          if(response.data.error_code && response.data.error_code === this.success_code) {
            this.ShowAlert("Trả phiếu thành công!",0);
            //Load lại danh sách
            this.HienThi_DanhSach();
          }
          else
          {
            this.ShowAlert("Trả phiếu không thành công!",2);
          }
          }
        );
      }catch(e){ this.ShowAlert("Lỗi trả phiếu không thành công",2); this.loading(false);}
      
    },

    async btnCapNhatDV()
    { 
      if (this.listbox.donvinhan.value.donvi_id == null || this.listbox.donvinhan.value.donvi_id=="")
      {
        this.ShowAlert("Bạn chưa chọn đơn vị nhận",2);
        //this.$refs.donvinhan.focus();
        return;
      }
      var listphieuchuyen = [];
      if (this.listbox.ds_phieuyeucau.checked.length>0)
      {
        listphieuchuyen =  this.listbox.ds_phieuyeucau.checked;
        listphieuchuyen = listphieuchuyen.map( function( item ){ 
                    for(var key in item){ 
                        var upper = key.toUpperCase(); 
                        // check if it already wasn't uppercase 
                        if( upper !== key ){  
                            item[ upper ] = item[key]; 
                            delete item[key]; 
                        } 
                    } 
                    return item; 
        });
      }
      else
      {
        this.ShowAlert("Bạn chưa chọn phiếu để cập nhật đơn vị nhận",2);
        return;
      }
      //Lấy loại đơn vị nhận id
      var input = {};
      input.huonggiao_id = this.listbox.huonggiao.value.huonggiao_id;
      API.fn_lay_loaidv_dich_theo_hg(this.axios, input).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
            this.loaidv_nhan_id = response.data.data;
        }
        }
      );
      //console.log(listphieuchuyen);
      for (var item of listphieuchuyen)
      {
        if (this.loaidv_nhan_id != "-1") 
        {
          try{
            var input1 = {};
            input1.hdtb_id = item["HDTB_ID"];
            input1.donvi_id = this.listbox.donvinhan.value.donvi_id;
            input1.loaidv_id = this.loaidv_nhan_id;
            if (this.listbox.dichvu.value.DICHVUVT_ID != this.DV_BTSL)//nếu là dịch vụ TSL
            { 
              input1.kieudv_id =2;
            }
            else
            {
              input1.kieudv_id =1;
            }
            this.loading(true);
            //console.log(input1);
            await API.capnhat_donvinhan(this.axios, input1).then((response) => {
              this.loading(false);
              if(response.data.error_code && response.data.error_code === this.success_code) {
                this.ShowAlert("Cập nhật đơn vị nhận thành công!",0);
              }
              else
              {
                this.ShowAlert("Cập nhật đơn vị nhận không thành công!",2);
              }
            }
            );
          }catch(e){ this.ShowAlert("Lỗi cập nhật đơn vị nhận không thành công!",2); this.loading(false); }
        }
      }  
      //Load lại danh sách
      this.HienThi_DanhSach();
    },
    
    btnXuatFile: function () {
      //console.log(this.listbox.ds_phieuyeucau.list);
      if (this.listbox.ds_phieuyeucau.list != null && this.listbox.ds_phieuyeucau.list.length > 0) {
        let data = XLSX.utils.json_to_sheet(this.listbox.ds_phieuyeucau.list)
        let wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data, 'DsPorts') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'ds_phieuyeucau.xlsx')
      } else this.$toast.error('Không có dữ liệu!')
    },

    gridChanged(obj)
    {
       if(!obj) { 
        return; 
      }
      //console.log(obj);
      this.txtMaTBChon = obj.ma_tb;
      this.txtMaGDChon =  obj.ma_gd;
      this.txtSoPhieu = obj.sophieu;
      this.txtNoiDung = obj.nd_tra_con;
      this.txtDiaChiTC = obj.diachi_ld;
      this.listbox.donvinhan.value.donvi_id = obj.donvinhan_id;
      
      if (obj.dichvuvt_id == this.DV_BTSL)
      {
        if (obj.danhdau == "3") //Đơn vị đầu trùng đơn vị cuối
        {
          this.kt_daucuoi = 1;
        }
        else // Đơn vị đầu khác đơn vị cuối
        {
          this.kt_daucuoi = 0;
        }
      }
      else
      {
        this.kt_daucuoi = 1;
      } 
    },

    rowClicked(idx,obj)
    {
      //console.log(obj);
      this.index = idx;
      if(!obj) { 
        return; 
      }
      this.txtMaTBChon = obj.ma_tb;
      this.txtMaGDChon =  obj.ma_gd;
      this.txtSoPhieu = obj.sophieu;
      this.txtNoiDung = obj.nd_tra_con;
      this.txtDiaChiTC = obj.diachi_ld;
      this.listbox.donvinhan.value.donvi_id = obj.donvinhan_id;

      if (obj.dichvuvt_id == this.DV_BTSL)
      {
        if (obj.danhdau == "3") //Đơn vị đầu trùng đơn vị cuối
        {
          this.kt_daucuoi = 1;
        }
        else // Đơn vị đầu khác đơn vị cuối
        {
          this.kt_daucuoi = 0;
        }
      }
      else
      {
        this.kt_daucuoi = 1;
      }
    },

    onMaGDEnter()
    {
      this.HienThi_DanhSach_v1();
    },

    ngaygiaoChange()
    {
     this.HienThi_DanhSach_v1();
    },

    HienThi_DanhSach_v1()
    {
       if (this.listbox.trangthaiphieu.value.id == "1")//Chưa giao
      {
        try{
          var input = {};
          input.dichvuvt_id = this.isTimTheoDichVu? this.listbox.dichvu.value.DICHVUVT_ID: 0; // ((this.listbox.dichvu.value.DICHVUVT_ID ==null || this.isTimTheoLoaiHD == false)? 0: this.listbox.dichvu.value.DICHVUVT_ID);
          input.loaihd_id = this.isTimTheoLoaiHD? this.listbox.loaihd.value.LOAIHD_ID: 0; //(this.listbox.loaihd.value.LOAIHD_ID == null? 0: this.listbox.loaihd.value.LOAIHD_ID);
          input.huonggiao_id = (this.listbox.huonggiao.value.huonggiao_id == null? 0: this.listbox.huonggiao.value.huonggiao_id); 
          input.donvi_id = this.$root.token.getDonViID();  
          input.ma_gd = (this.txtMaGD == ""? "0": this.txtMaGD);
          input.nhanvien_id = this.$root.token.getNhanVienID(); 
          //console.log(input);
          this.loading(true);
          API.sp_lay_ds_hdtb_chuagiao(this.axios, input).then((response) => {
            this.loading(false);
            if(response.data.error_code && response.data.error_code === this.success_code) {
              this.listbox.ds_phieuyeucau.list = response.data.data;
              if (this.txtMaGD != "")
              {
                this.listbox.ds_phieuyeucau.list = this.listbox.ds_phieuyeucau.list.filter(x => x.ma_gd == this.txtMaGD || x.ma_tb == this.txtMaGD);
                if(this.listbox.ds_phieuyeucau.list.length<=0)
                {
                  this.ShowAlert("Không tìm thấy phiếu",2);
                }
              }
              this.listbox.ds_phieuyeucau.header =[ {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, width: 150}, 
                {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, width: 350},
                {fieldName: 'diachi_tb', headerText: 'Địa chỉ lắp đặt', allowFiltering: true , allowHtml : true, width: 400},
                {fieldName: 'ttyc', headerText: 'Thông tin yêu cầu', allowFiltering: true , allowHtml : true},                                
                {fieldName: 'sophieu', headerText: 'Số QĐ', allowFiltering: true , allowHtml : true},                                     
                {fieldName: 'donvinhan', headerText: 'Đơn vị nhận', allowFiltering: true , allowHtml : true},                                     
                {fieldName: 'nd_tra_con', headerText: 'Nội dung trả', allowFiltering: true , allowHtml : true},]
            }
            else
            {
              this.loading(false);
              this.listbox.ds_phieuyeucau.list = [];
              this.ShowAlert("Không tìm thấy phiếu chưa giao!",2);
            }
          }
          ); 
        }catch(e){ this.ShowAlert("Lỗi lấy danh sách phiếu chưa giao",2); this.loading(false); }
        
      }
      else if(this.listbox.trangthaiphieu.value.id == "2") //đã giao
      {
        try{
          var input = {};
          input.dichvuvt_id = this.isTimTheoDichVu? this.listbox.dichvu.value.DICHVUVT_ID: 0; // ((this.listbox.dichvu.value.DICHVUVT_ID ==null || this.isTimTheoLoaiHD == false)? 0: this.listbox.dichvu.value.DICHVUVT_ID);
          input.loaihd_id = this.isTimTheoLoaiHD? this.listbox.loaihd.value.LOAIHD_ID: 0; //(this.listbox.loaihd.value.LOAIHD_ID == null? 0: this.listbox.loaihd.value.LOAIHD_ID);
          input.huonggiao_id = (this.listbox.huonggiao.value.huonggiao_id == null? 0: this.listbox.huonggiao.value.huonggiao_id); //7;
          input.donvi_id = this.$root.token.getDonViID(); 
          input.loaipt_id = 0;
          //console.log(this.ngaygiao_tk);
          input.ngay_giao = (this.ngaygiao_tk == null? 0: this.ngaygiao_tk);
          input.ma_gd = (this.txtMaGD == ""? "0": this.txtMaGD);
          input.nhanvien_id = this.$root.token.getNhanVienID();  
          //console.log(input);
          this.loading(true);
          API.lay_ds_hdtb_dagiao(this.axios, input).then((response) => {
            this.loading(false);
            if(response.data.error_code && response.data.error_code === this.success_code) {
              this.listbox.ds_phieuyeucau.list = response.data.data;
              if (this.txtMaGD != "")
              {
                this.listbox.ds_phieuyeucau.list = this.listbox.ds_phieuyeucau.list.filter(x => x.ma_gd == this.txtMaGD || x.ma_tb == this.txtMaGD);
                if(this.listbox.ds_phieuyeucau.list.length<=0)
                {
                  this.ShowAlert("Không tìm thấy phiếu",2);
                }
              }
              this.listbox.ds_phieuyeucau.header =[ {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, width: 150}, 
                {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, width: 350},
                {fieldName: 'diachi_tb', headerText: 'Địa chỉ lắp đặt', allowFiltering: true , allowHtml : true, width: 400},
                {fieldName: 'ttyc', headerText: 'Thông tin yêu cầu', allowFiltering: true , allowHtml : true, width: 300},                                
                {fieldName: 'sophieu', headerText: 'Số QĐ', allowFiltering: true , allowHtml : true, width: 150},                                     
                {fieldName: 'donvinhan', headerText: 'Đơn vị nhận', allowFiltering: true , allowHtml : true},                                     
                {fieldName: 'nd_giao', headerText: 'Nội dung giao', allowFiltering: true , allowHtml : true},]
            }
            else
            {
              this.loading(false);
              this.listbox.ds_phieuyeucau.list = [];
              this.ShowAlert("Không tìm thấy phiếu đã giao!",2);
            }
          }
          ); 
        }catch(e){ this.ShowAlert("Lỗi lấy danh sách phiếu đã giao",2); this.loading(false); }
        
      }
    },
    async showPopup({trangthaiphieu, loaihd, madv, quytrinh, huonggiao, magd} = {}) //quanghs.tvh bo sung de show form giao viec tren ejs-dialog
    {
      await this.LoadCombobox();
      if (trangthaiphieu)
      {
        this.listbox.trangthaiphieu.value.id = trangthaiphieu;
      }
      //Loại hợp đồng
      if (loaihd)
      {
        this.isTimTheoLoaiHD = true;
        this.listbox.loaihd.value.LOAIHD_ID =  loaihd;
      }  
      //Dịch vụ
      if (madv)
      {
        this.isTimTheoDichVu = true;
        this.DICHVU =  madv;
        this.listbox.dichvu.value.DICHVUVT_ID = madv;
      }
      //Quy trình
      if (quytrinh)
      {
        this.isTimTheoQuyTrinh = true;
        this.listbox.quytrinh.value.quytrinh_id = quytrinh;
      }   
      await this.LoadHuongGiao();
      if (huonggiao)
      {
        this.listbox.huonggiao.value.huonggiao_id =  huonggiao;
      }  
      if(magd){
          this.txtMaGD = magd;
      }
      this.btnTimKiem()
    },

  },
};

</script>
