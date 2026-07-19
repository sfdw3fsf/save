<template src="./UpdatePriorityMultiServicePackage.html"></template>
<style src="./UpdatePriorityMultiServicePackage.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import DatePicker from 'vue2-datepicker' 
import API from './API'
import XLSX from 'xlsx'
import gridview from '@/components/Shared/gridview'
import { required, minLength } from 'vuelidate/lib/validators'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import moment from 'moment'
//
export default {
  components: {breadcrumb,VueFlatPickr,gridview,API,XLSX,DatePicker },
  name: 'UpdatePriorityMultiServicePackage',
  mounted : async function() {
    this.tungay = new Date(new Date().getDate()-(7*24*60*60*1000));
    
   //this.DK_denngay = .addDays(-7);
   await this.getDichVu();
   await this.getGoiDaDichVu();
   await this.getDSThueBao();
   this.timtheongay = false;
  },
  data () {
    return {
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput:true
      },
      dateFormat: "DD/MM/YYYY",
      ngay_ht: new Date(),
      header: {
        title: 'CẬP NHẬT DANH SÁCH ƯU TIÊN ĐĂNG KÝ GÓI ĐA DỊCH VỤ',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      isNhapMoi:true,
      isCapNhat:false,
      isHuyBo:false,
      isXoaPhieu:false,
      isCachTim: true,
      isDKTuNgay: true,
      isDKDenNgay: true,
      isGhiChu: true,
      listbox: {
       dichvu: {
          list: [],
          value: {DICHVUVT_ID: "",TEN_DVVT: ""},
        },
        convan: {
          list: [],
          value: {},
        },
        goidadichvu: {
          list: [],
          value: {goi_id: "", ten_goi: "", ngay_bd: "", tien: 0, mota: ""},
          id: 0,
        },
        ds_thuebao: {
          list: [],
          header: [],
          value: {},
          isEnabled: true,
        },
      },
      dateconfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y',
        allowInput:true
      },
      tungay: new Date(),
      denngay: new Date(),
      DK_tungay: new Date(),
      DK_tungays: "",
      DK_denngay: new Date(),
      DK_denngays: "",
      ngaytaogoi: new Date(),
      ngaytaogois: "",
      denngay_cu: new Date(),
      success_code: "BSS-00000000",
      tien: 0,
      thongtingoi: "",
      MaTB: "",
      TenTB: "",
      LoaiTB: "",
      DiaChi: "",
      GhiChu: "",
      NguoiCN: "",
      GoiID: "",
      GhiChu_Cu: "",
      LoaiTBid_Cu: "",
      GoiID_Cu: "",
      ThuebaoID: "",
      exportlist: [],
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
    timtheongay: {
      get() { return this.isCachTim; },
      set(value) { this.isCachTim=value; }
    },
    timall: {
      get() { return !this.isCachTim; },
      set(value) { this.isCachTim=!value; }
    },
    DKTuNgay: {
      get() { return !this.isDKTuNgay; },
      set(value) { this.isDKTuNgay=!value; }
    },
    DKDenNgay: {
      get() { return !this.isDKDenNgay; },
      set(value) { this.isDKDenNgay=!value; }
    },
   
    
    
  },
  validations: {
    MaTB: {
      required,
      minLength: minLength(1)
    },
    DK_tungays: {
      required,
      minLength: minLength(10)
    },
    DK_denngays: {
      required,
      minLength: minLength(10)
    },
    listbox:
    {
      goidadichvu: 
      {
        id: {required, minLength: minLength(1)}
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
    getDichVu: async function () {
      var input = {};
      var dv = [1,2,4,8,9,10,11,12,14,15,16];
      this.loading(true);
      this.listbox.dichvu.list = this.GetData(await API.CSS_DICHVU_VT(this.axios, input));
      this.listbox.dichvu.list = this.listbox.dichvu.list.filter(a=>a.DICHVUVT_ID in dv);
      this.loading(false);
    },
    getGoiDaDichVu: async function () {
      var input = {};
      this.loading(true);
      this.listbox.goidadichvu.list =  this.GetData(await API.sp_nap_ds_goiddv(this.axios, input)).filter(x => x.goi_id != 0).map(x=>({id:x.goi_id,text:x.ten_goi, mota:x.mota, ngay_bd:x.ngay_bd, ten_goi:x.ten_goi, tien:x.tien}));;
      this.loading(false);
    },
    getThueBao: async function () {
      this.isCachTim = !this.isCachTim;
      if (this.isCachTim)
      {
        this.getDSThueBao();
      }
      else
      {
        this.getAllDSThueBao();
      }
    },
    getDSThueBao: async function () {
        var input = {};
        input.p_tungay= this.tungay.toLocaleString('en-GB').substring(0,10);
        input.p_denngay= this.denngay.toLocaleString('en-GB').substring(0,10);
        input.p_nguoi_cn="";
        this.loading(true);
        this.listbox.ds_thuebao.list = this.GetData(await API.sp_lay_ds_thuebao(this.axios, input));
        this.listbox.ds_thuebao.header = [{name:'ma_tb',text:'Mã TB'},{name:'loai_tb',text:'Loại hình'},{name:'ten_goi',text:'Tên gói'},{name:'tungay',text:'Từ ngày'},{name:'denngay',text:'Đến ngày'},{name:'nguoi_cn',text:'Người CN'},{name:'ngay_cn',text:'Ngày CN'},{name:'ghichu',text:'Ghi chú'}];
        this.loading(false);
    },
    getAllDSThueBao: async function ()
    {
        var input = {};
        input.p_tungay= "01/01/1900";
        input.p_denngay= "01/01/3000"//new Date().toLocaleString('en-GB').substring(0,10);
        input.p_nguoi_cn="";
        //console.log(input);
        this.loading(true);
        this.listbox.ds_thuebao.list = this.GetData(await API.sp_lay_ds_thuebao(this.axios, input));
        this.loading(false);
        //console.log(this.listbox.ds_thuebao.list);
        this.listbox.ds_thuebao.header = [{name:'ma_tb',text:'Mã TB'},{name:'loai_tb',text:'Loại hình'},{name:'ten_goi',text:'Tên gói'},{name:'tungay',text:'Từ ngày'},{name:'denngay',text:'Đến ngày'},{name:'nguoi_cn',text:'Người CN'},{name:'ngay_cn',text:'Ngày CN'},{name:'ghichu',text:'Ghi chú'}];
    }, 
    btnLayTT()
    {
      if (this.isCachTim)
      {
        this.getDSThueBao();
      }
      else
      {
        this.getAllDSThueBao();
      }
    },
    btnNhapMoi()
    {
      this.DK_tungay = new Date();
      this.DK_denngay = new Date();
      this.DK_denngays = new Date();
      this.DK_tungays = new Date();
      this.MaTB = "";
      this.LoaiTB = "";
      this.TenTB = "";
      this.GhiChu = "";
      this.DiaChi = "";
      this.isCapNhat = true;
      this.isHuyBo = true;
      this.isNhapMoi = false;
      this.isDKTuNgay = true;
      this.isDKDenNgay = true;
      this.isGhiChu = true;
      this.$refs.MaTB.focus();
    },
    btnHuyBo()
    {
      this.MaTB = "";
      this.LoaiTB = "";
      this.TenTB = "";
      this.GhiChu = "";
      this.DiaChi = "";
      this.isCapNhat = false;
      this.isHuyBo = false;
      this.isNhapMoi = true;
      this.isDKTuNgay = false;
      this.isDKDenNgay = true;
      this.isGhiChu = false;
    },
    goidadvchange()
    {
      var item = this.listbox.goidadichvu.list.find(data => data.id == this.listbox.goidadichvu.value.id);
      //this.ngaytaogoi = item.ngay_bd; 
      console.log(item);
      this.tien= this.formatPrice(item.tien);
      this.thongtingoi = item.mota;
      this.GoiID = item.id;
      const changedDate2 = item.ngay_bd.replace(/(..)\/(..)\/(....) (..):(..)/, '$3-$2-$1 $4:$5'); 
      this.ngaytaogois = new Date(changedDate2);
    },
    gridChanged(obj)
    {
      if(!obj) { 
        return; 
      }
      //console.log(obj);
      this.ThuebaoID = obj.thuebao_id;
      this.MaTB = obj.ma_tb;
      this.LoaiTB = obj.loai_tb;
      this.TenTB = obj.ten_tb;
      this.GhiChu = obj.ghichu;
      this.NguoiCN = obj.nguoi_cn;
      this.DiaChi = obj.diachi_ld;
      this.listbox.dichvu.value.DICHVUVT_ID = obj.dichvuvt_id;
      const changedDate = obj.tungay.replace(/(..)\/(..)\/(....) (..):(..)/, '$3-$2-$1 $4:$5');
      this.DK_tungays = new Date(changedDate);
      const changedDate1 = obj.denngay.replace(/(..)\/(..)\/(....) (..):(..)/, '$3-$2-$1 $4:$5');
      this.DK_denngays = new Date(changedDate1);
      
      this.denngay_cu = new Date(changedDate1);
      this.GhiChu_Cu = obj.ghichu;
      this.LoaiTBid_Cu = obj.loaitb_id;
      this.GoiID = obj.goi_id;
      this.GoiID_Cu = obj.goi_id;
      this.listbox.goidadichvu.value.id = obj.goi_id;
      var item = this.listbox.goidadichvu.list.find(data => data.id == this.listbox.goidadichvu.value.id);
      this.ngaytaogoi = item.ngay_bd; 
      this.tien= this.formatPrice(item.tien);
      this.thongtingoi = item.mota;
      const changedDate2 = item.ngay_bd.replace(/(..)\/(..)\/(....) (..):(..)/, '$3-$2-$1 $4:$5'); 
      this.ngaytaogois = new Date(changedDate2);
      //this.GoiID = item.goi_id;
    
      this.isCapNhat = true;
      this.isHuyBo = true;
      this.isXoaPhieu =  true;
      this.isNhapMoi = true;
      this.isDKTuNgay = false;
      this.isGhiChu = false;
    },
    Cap_Nhat: async function ()
    {
      
      var input = {};
      input.vdenngay = this.DK_denngays.substring(0,16) + " 00:00"; //this.DK_denngay.toLocaleString('en-GB').substring(0,16);
      input.vdenngay_cu = this.denngay_cu? this.denngay_cu.toLocaleString('en-GB').substring(0,10)  + this.denngay_cu.toLocaleString('en-GB').substring(11,17): null;
      input.vghichu = (this.GhiChu ===null? "": this.GhiChu.trim());
      input.vghichu_cu = (this.GhiChu_Cu ===null? "": this.GhiChu_Cu.trim());
      input.vgoi_id = this.GoiID;
      if (this.isNhapMoi) //cập nhật
      {
        input.vkieu = 0;
        input.vgoi_id_cu = this.GoiID_Cu;
      }
      else //nhập mới
      {
        input.vkieu = 1;
        input.vgoi_id_cu = 0;
      }
      input.vloaitb_id_cu = this.LoaiTBid_Cu;
      input.vthuebao_id = this.ThuebaoID;
      input.vtungay = this.DK_tungays.substring(0,16) + " 00:00"; //this.DK_tungay.toLocaleString('en-GB').substring(0,16); 
      //this.GetData(await API.fn_capnhat_goidadv_v2(this.axios, input));
      console.log(input);
      this.loading(true);
      API.fn_capnhat_goidadv_v2(this.axios, input).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
          this.loading(false);
          if (this.timtheongay)
            this.getDSThueBao();
          else 
            this.getAllDSThueBao(); 
          this.ShowAlert("Cập nhật thông tin thành công",0);
          this.isCapNhat = false;
          this.isNhapMoi = true;
          this.isHuyBo = false;
        }
        else {
          this.loading(false);
          this.ShowAlert(response.data.message,2);
        }
      });
    },
    btnCapNhat()
    {
      console.log(this.isNhapMoi);
      this.$v.$touch()
      if (this.$v.$invalid) {
        this.$toast.error("Bạn cần nhập đủ các trường có dấu *");
        return
      }
      var currentdate = new Date();
      var dateMomentObject = moment(this.DK_denngays, "DD/MM/YYYY"); // 1st argument - string, 2nd argument - format
      this.DK_denngay = dateMomentObject.toDate(); // convert moment.js object to Date object
      if (this.$root.token.getUserName() != this.NguoiCN && this.isNhapMoi == true)
      {
        this.ShowAlert("Bạn không được quyền thao tác trên bản ghi của người khác",2);
        return
      }
      if (this.isNhapMoi) 
      {
        if (moment(this.DK_denngay).format("YYYYMMDD") < moment(currentdate).format("YYYYMMDD"))
        {
          this.ShowAlert("Bạn chỉ có thể sửa đến ngày lớn hơn hoặc bằng ngày hiện tại!",2);
          return
        }
      } 
      var denngay_temp = moment(this.DK_denngays, "DD/MM/YYYY");
      var tungay_temp = moment(this.DK_tungays, "DD/MM/YYYY");
      if (tungay_temp.format("YYYYMMDD") > denngay_temp.format("YYYYMMDD"))
      {
        this.ShowAlert("Bạn phải nhập đến ngày lớn hơn từ ngày đăng ký gói dịch vụ của thuê bao!",2);
        return
      }
      if (this.GhiChu !=null)
      {
         if (this.GhiChu.length>200)
        {
          this.ShowAlert("Ghi chú không được vượt quá 200 ký tự",2);
          this.$refs.GhiChu.focus();
          return
        }
      }
     
      this.Cap_Nhat();
    },
    Xoa_Phieu: async function ()
    {
      await this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xoá thuê bao đăng ký gói đa dịch vụ này không?', {
                    title: '',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                    "centered": true,
                }).then(async v => {
                    if(v) {
                      if (this.$root.token.getUserName() != this.NguoiCN)
                      {
                        this.ShowAlert("Bạn không được quyền thao tác trên bản ghi của người khác",2);
                        return
                      }
                      var input = {};
                      console.log(this.DK_tungays)
                      input.vdenngay = this.DK_denngays.substring(0,16) + " 00:00"; //this.DK_denngay.toLocaleString('en-GB').substring(0,16);
                      input.vdenngay_cu = this.denngay_cu.toLocaleString('en-GB').substring(0,10) + this.denngay_cu.toLocaleString('en-GB').substring(11,17);
                      input.vghichu = (this.GhiChu ===null? "": this.GhiChu);
                      input.vghichu_cu = (this.GhiChu_Cu ===null? "": this.GhiChu_Cu);
                      input.vgoi_id = this.GoiID;
                      input.vgoi_id_cu = this.GoiID_Cu;
                      input.vkieu = 3;
                      input.vloaitb_id_cu = this.LoaiTBid_Cu;
                      input.vthuebao_id = this.ThuebaoID;
                      input.vtungay = this.DK_tungays.substring(0,16) + " 00:00"; //this.DK_tungay.toLocaleString('en-GB').substring(0,16); 
                      console.log(input);
                      this.loading(true);
                      API.fn_capnhat_goidadv_v2(this.axios, input).then((response) => {
                        if(response.data.error_code && response.data.error_code === this.success_code) {
                          this.loading(false);
                          if (this.timtheongay)
                          {
                            this.getDSThueBao();
                          }
                          else 
                          {
                            this.getAllDSThueBao();
                          } 
                          this.ShowAlert("Xóa thuê bao đăng ký gói thành công",0);
                        }
                        else {
                          this.ShowAlert(response.data.message,2);
                          this.loading(false);
                        }
                      }).catch(function(){
                        this.loading(false);
                        console.log('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
                      });
                    }
                });
    },
    btnXoaPhieu()
    {
      this.Xoa_Phieu();
    },
    btnRefreshClick()
    {
     if (this.timtheongay)
        this.getDSThueBao();
      else 
        this.getAllDSThueBao(); 
    },
    onEnter()
    {
      this.TimKiem();
    },
    TimKiem: async function ()
    {
      this.loading(true);
      var input = {};
      input.in_dichvuvt_id= 0; //this.listbox.dichvu.value.DICHVUVT_ID;
      input.in_donvi_dl_id= 0;
      input.in_ma_tb= this.MaTB.toString();
      var tbao = [];
      console.log(input);
      tbao = this.GetData(await API.LAY_DANHBA_THEO_MATB(this.axios, input));
      this.loading(false);
      if (tbao.length>0)
      {
        console.log(tbao)
        this.ThuebaoID = tbao[0].thuebao_id;
        this.LoaiTBid_Cu = tbao[0].loaitb_id;
        //this.GoiID_Cu = temp[0].goi_id;
        //this.GhiChu_Cu = (tbao[0].ghichu == null? "" : (tbao[0].ghichu));
       /*  var input1 = {};
        input1.id_neo = "loaihinh_tb";
        input1.in_table = "css.loaihinh_tb";
        input1.in_dk = "where loaitb_id =" + tbao[0].loaitb_id;
        API.thanh_ly_map_id(this.axios, input1).then((response) => {
          if(response.data.error_code && response.data.error_code === this.success_code) {
            this.LoaiTB = response.data.data;
          }
        });z */
        var input1 = {};
        input1.type = 1;
        input1.param = tbao[0].loaitb_id;
        console.log(input1);
        API.fn_tt_loaihinh_tb(this.axios, input1).then((response) => {
          if(response.data.error_code && response.data.error_code === this.success_code) {
            this.LoaiTB = response.data.data;
          }
        });
        this.TenTB = tbao[0].ten_tb;
        this.DiaChi = tbao[0].diachi_tb;
        this.GhiChu = ""; //(tbao[0].ghichu_tb == null? "" : (tbao[0].ghichu_tb));
        this.listbox.dichvu.value.DICHVUVT_ID = tbao[0].dichvuvt_id;
        this.listbox.goidadichvu.idx = "";
        this.DK_tungays = new Date();
        this.ngaytaogoi = "";
        this.DK_denngays = "";
        this.isCapNhat = true;
        this.isHuyBo = true;
        this.isNhapMoi = false;
        this.isDKTuNgay = true;
        this.isGhiChu = true;
      }
      else
      {
        this.ShowAlert("Không tìm thấy thuê bao "+ this.MaTB,1);
        this.MaTB = "";
        this.LoaiTB = "";
        this.TenTB = "";
        this.GhiChu = "";
        this.DiaChi = "";
        this.isCapNhat = false;
        this.isNhapMoi = true;
        this.isHuyBo = true;
      }
    },
    btnTimKiem()
    {
      this.TimKiem();
    },
    btnExportExcel()
    {
      this.exportlist = this.listbox.ds_thuebao.list.map(x => ({"Mã TB":x.ma_tb,"Loại hình":x.loai_tb,"Tên gói": x.ten_goi,"Từ ngày":x.tungay,"Đến ngày":x.denngay,"Người CN":x.nguoi_cn,"Ngày CN":x.ngay_cn,"Ghi chú":x.ghichu}));
      //console.log(this.exportlist);
      if (this.exportlist != null && this.exportlist.length > 0) {
        let data = XLSX.utils.json_to_sheet(this.exportlist)
        let wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data, 'DsPorts') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'ds_tbao_dky_goidaDV.xlsx')
      } else this.ShowAlert('Không có dữ liệu!',1)
    }
  },
};
</script>