<template src="./HandoverTicketOnOffSubcriber.html"></template>
<style src="./HandoverTicketOnOffSubcriber.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import DatePicker from 'vue2-datepicker'
import {LoaiHopDong, DichVuVienThong,TrangThaiHD,LOAI_DV} from "@/const/enums";
import api from './API'
import { required, minLength } from 'vuelidate/lib/validators'
import Vue from "vue";
import moment from 'moment'
import SearchContractModal from '../../../search/subscriber/SearchContract/SearchContract.vue'
import uploadEProfile from "@/modules/contract/profile/UploadEProfile"
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
import ExportDataModal from '../../../contract/profile/ExportData/ExportDataModal.vue'
Vue.use(DialogPlugin);
//
export default {
  components: {breadcrumb,api,SearchContractModal,LoaiHopDong, DichVuVienThong,TrangThaiHD,LOAI_DV,ExportDataModal,uploadEProfile},
  name: 'HandoverTicketOnOffSubcriber',
  mounted() {
    try{
            this.loading(true);
            this.trangthai_searchContrat = TrangThaiHD.THANH_TOAN;
            this.ngay_ht = moment(new Date()).format("DD/MM/YYYY");
            this.cboTT.list = [{id:1,text:"Chưa giao"},{id:2,text:"Đã giao"}];
            if (this.ma_gd != "0")
            {
                this.txtMaGD = this.ma_gd;
            }
            this.cboTT.SelectedValue = this.cboTT.list[this.ttph].id;
            if (this.ngaygiao == "0")
                this.dtpNgayGiaoTK = moment(new Date()).format("DD/MM/YYYY");
            else
                this.dtpNgayGiaoTK = moment(this.ngaygiao, 'DD/MM/YYYY').format("DD/MM/YYYY");
            this.frmGiaoPhieu_Load();
            this.loading(false);
    }
    catch(e){
      this.loading(false);
    }

  },
   props:{
        ma_gd:{
            type:String,
            default:"0"
        },
        kieu_id:{
            type:Number,
            default:0
        },
        hg_id:{
            type:Number,
            default:0
        },

        hdtb_id:{
            type:Number,
            default:0
        },
        ttph:{
            type:Number,
            default:0
        },
        ngaygiao:{
          type:String,
          default:"0"
        }

    },
  data () {
    return {
      position: { X: 'center', Y: 'top' },
        header: {
          title: 'GIAO PHIẾU KHÓA MỞ MÁY',
          list: [
            { name: 'Điều hành TC', link: { name: 'Ui.cards' } },
            {
              name: 'Giao phiếu khóa mở máy',
              link: { name: 'Ui.buttons' }
            }
          ]
        },
        txtMaGD:"", dtpNgayGiaoTK:"", dtpNgayGiao:"",
         success_code: "BSS-00000000",
         trangthai_searchContrat:0, ngay_ht:"",
         dichvuvt_id : 0,
         loaihd_id : 0,
         phieu_id:0,
         donvi_nhan_id:0,
         temp : 0,
         temp1 : 0,
         temp2 : 0,
         dvvt_id:0,
         luong_id : 0,
        bGiaoPhieu: false,
        //nhapt them 18102018
        _status:"",
         giao_vip_net : 0,
         giao_vip : 0,
         giao_net : 0,
        dsSelected:[],
         _XacMinh_TheoYeuCau : 0, //12-03-2020 AnhDt thêm
        ts_kt_tthdkhdn : 0,//Kiểm tra cho phép thêm thông tin khách hàng DN  đối với PTTB KHDN mới xài

        NHANTIN_NHANVIEN_PHOIHOP : -1,
        SEND_SMS_NHANTIN_NV:-1,
        NHANTIN_THEO_HG: -1,
        //Hiếu -12/09/2010- bổ sung biến kiểm tra xem đơn vị đầu có : đơn vị cuối hay không
        // kt_daucuoi:0: ko trùng nhau, 1: trùng nhau
        kt_daucuoi : 0,
        loaidv_nhan_id : "-1",
        kt_load : true,
        TC_ThoiGianXuLy:"",
        _pSendSMS: false,
        _Giaoviec_td:false,//Giao việc tự động cho nhân viên kỹ thuật
        _Giaoviec_nvkd:false,//Giao việc tự động cho nhân viên kinh doanh
        vnoidung : "",
        vnoidung_ghichu : "",
        vthuebao_id : 0,

        taoduyet : 0,

        idx_loaihd_id : 0,
        idx_dichvuvt_id : 0,
        idx_quytrinh_id : 0,

        kt_phieutra_gdv : false,
        tsbtnTimKiem:{
          Visible:true,
          Enabled:true,
        },
        tsbtnGiaoPhieu:
        {
          Visible:true,
          Enabled:true,
        },
        tsbtnInDS:{
          Visible:false,
          Enabled:true,
        },
        tsbUpHoSo:{
          Visible:false,
          Enabled:true,
        },
        tsbtnHuyGiao:{
          Enabled:false,
        },
        btnCapNhatVT:{
          Enabled:true,
        },
        cboTT:{
          list:[],
          SelectedValue:null,
        },
        cboDVNhan:{
          list:[],
          SelectedValue:null,
          Enabled:true,
        },
        dsThamSo:{
          list:[],
        },
        cboTKLoaiHD:{
          list:[],
          SelectedValue:null,
        },
        cboTKDichVuVT:{
          list:[],
          SelectedValue:null,
        },
        cboTKQuyTrinh:{
          list:[],
          SelectedValue:null,
        },
        cboTKHuongGiao:{
          list:[],
          SelectedValue:null,
        },
        chkTKLoaiHD:{
            Checked:false,
        },
        chkTKDichVuVT:{
          Checked:false,
        },
        chkTKQuyTrinh:{
          Checked:false,
        },
        txtDiaChiTC:"", txtMaGDChon: "", txtMaTBChon: "", txtNoiDung: "", txtSoPhieu:"",
        lblThoiGianTao:"00:00:00",
        grcList:{
          list:[],
          cols:
            [
                {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, width: 150},
                {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, width: 350},
                {fieldName: 'diachi_tb', headerText: 'Địa chỉ lắp đặt', allowFiltering: true , allowHtml : true, width: 400},
                {fieldName: 'ttyc', headerText: 'Thông tin yêu cầu', allowFiltering: true , allowHtml : true, width: 300},
                {fieldName: 'sophieu', headerText: 'Số QĐ', allowFiltering: true , allowHtml : true, width: 150},
                {fieldName: 'donvinhan', headerText: 'Đơn vị nhận', allowFiltering: true , allowHtml : true},
                {fieldName: 'nd_giao', headerText: 'Nội dung giao', visible: true,allowFiltering: true , allowHtml : true},
                {fieldName: 'nd_tra_con', headerText: 'Nội dung trả', visible: false, allowFiltering: true , allowHtml : true},

            ],
          selectedItems: []
        },
        colnoidung_Visible : true,
        colnoidungtra_Visible:false,
    }
  },
  computed:{
    dataCols() {
      return this.grcList.cols.map(item => {
        if (item.fieldName === 'nd_giao') {
          item.visible = this.colnoidung_Visible;
        }
        else if(item.fieldName === 'nd_tra_con'){
          item.visible = this.colnoidungtra_Visible;
        }
        return item;
      })
    },
  },
  watch:{
    grcList:{
      handler: function () {

                if (this.grcList.list.length <= 0)
                    {

                        this.ClearTextBox();
                    }
                },
                deep: true

    },
    cboTKHuongGiao:{
      handler: async function(){
        try{
            this.loading(true);
            this.lblThoiGianTao = "00:00:00";
            this.TC_ThoiGianXuLy = new Date(moment(new Date).format("YYYY-MM-DD HH:mm:ss")).getTime();
            this.txtSoPhieu = "";
            this.txtDiaChiTC = "";
            await this.GetDSDonViNhan();
            await this.HienThi_DanhSach();
            let currentDate = new Date(moment(new Date).format("YYYY-MM-DD HH:mm:ss")).getTime();
            let myDuration = currentDate - this.TC_ThoiGianXuLy;
            this.lblThoiGianTao = this.formattedTime(myDuration);
            this.loading(false);
        }
        catch(e){
          this.lblThoiGianTao = "00:00:00";
          this.loading(false);
        }
      },
      deep:true
    },
   async dtpNgayGiaoTK(){
      if (!this.kt_load){
        this.loading(true);
        this.HienThi_DanhSach();
        this.loading(false);
      }

    }
  },
  methods: {
    queryCellInfo(args){
      if (args.data.nd_tra_con != null && args.data.nd_tra_con != undefined ) {
        $(args.cell).css({"color": "red", "text-align":"center"});
      }

    },
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
       showPopupSearchContract(){
          //tra cứu phụ lục hợp đồng
            this.$refs.popupFrmTraCuuHopDong.$children[0].clearData();
            this.$refs.popupFrmTraCuuHopDong.$children[0].loadPopup();
            this.$refs.popupFrmTraCuuHopDong.show();
        },
         acceptSearchContract(item){
          this.$refs.popupFrmTraCuuHopDong.hide();
          this.txtMaGD=item.hopdong.ma_gd;
          this.$refs["txtMaGD"].focus();
          if(this.txtMaGD != "" && this.txtMaGD != null && this.txtMaGD){
              this.HienThi_DanhSach();
          }
        },
       async frmGiaoPhieu_Load(){
          this.dtpNgayGiao = moment().format("DD/MM/YYYY HH:mm a");
          api.lay_ds_thamso_md_mats(this.axios, {}).then((response) => {
            if(response.data.error_code && response.data.error_code === this.success_code) {
                this.dsThamSo.list = response.data.data;
                if (this.dsThamSo.list.length>0)
                {
                  var temp0 = this.dsThamSo.list.filter(x=> x.ma_ts === 'LAY_QUYTRINH_PROC');
                  try{
                    if(temp0.length > 0){
                      this._XacMinh_TheoYeuCau = Number(temp0[0]["macdinh"].toString());
                    }
                  }
                  catch(e){}

                  var temp = this.dsThamSo.list.filter(x=> x.ma_ts === 'NHANTIN_NHANVIEN_PHOIHOP');
                  if (temp.length>0)
                  {
                    //console.log(temp);
                    if (temp[0]["ten_ts"].toString() == "1")
                    {
                      this.NHANTIN_NHANVIEN_PHOIHOP = 1;
                    }
                  }

                  var temp1 = this.dsThamSo.list.filter(x=> x.ma_ts === 'THEM_TT_HDKHDN');
                  if (temp1.length>0)
                  {
                    //console.log(temp1);
                    if (temp1[0]["ten_ts"].toString() == "1")
                    {
                      this.ts_kt_tthdkhdn = 1;

                    }
                  }

                  var temp2 = this.dsThamSo.list.filter(x=> x.ma_ts === 'KT_PHIEUTRA_GDV');
                  if (temp2.length>0)
                  {
                    //console.log(temp2);
                    if (temp2[0]["ten_ts"].toString() == "1")
                    {
                      this.kt_phieutra_gdv = true;
                    }
                  }

                  var temp3 = this.dsThamSo.list.filter(x=> x.ma_ts === 'SEND_SMS_NHANTIN_NV');
                  if (temp3.length>0)
                  {
                    //console.log(temp2);
                    if (temp3[0]["ten_ts"].toString() == "1")
                    {
                      this.SEND_SMS_NHANTIN_NV = 1;
                    }
                  }

                  var temp4 = this.dsThamSo.list.filter(x=> x.ma_ts === 'NHANTIN_THEO_HG');
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
              this.ShowAlert("Không tìm thấy danh sách dịch tham số dịch vụ",1);
            }
            });
           await this.HT_Loai_HD_Combobox();
           await this.HT_DichVuVT_Combobox();
           await this.HT_QuyTrinh_Combobox();
           await this.LAY_DS_HUONGGIAO();
           await this.HienThi_GiaoDien();
           this.kt_load = false;
      },

    async  HT_Loai_HD_Combobox(){
              try{
               let response = await api.CSS_LOAI_HD(this.axios, {})
                if(response.data.error_code && response.data.error_code === this.success_code) {
                    this.cboTKLoaiHD.list = response.data.data;
                    this.cboTKLoaiHD.SelectedValue = this.cboTKLoaiHD.list[0].LOAIHD_ID
                }
                else
                {
                  this.cboTKLoaiHD.list = [];
                  this.cboTKLoaiHD.SelectedValue = null;
                  this.ShowAlert("Không tìm thấy danh sách loại hợp đồng!",1);
                }
              }catch(e){ console.log(e);this.ShowAlert("Lỗi lấy danh sách loại hợp đồng",2); this.loading(false);
            }
      },
    async HT_DichVuVT_Combobox(){
              try{
                let response = await api.CSS_DICHVU_VT(this.axios, {});
                if(response.data.error_code && response.data.error_code === this.success_code) {
                    this.cboTKDichVuVT.list = response.data.data;
                    this.cboTKDichVuVT.SelectedValue = this.cboTKDichVuVT.list[0].DICHVUVT_ID
                }
                else
                {
                  this.cboTKDichVuVT.list = [];
                  this.cboTKDichVuVT.SelectedValue = null;
                  this.ShowAlert("Không tìm thấy danh sách dịch vụ viễn thông!",1);
                }

              }catch(e){ console.log(e);this.ShowAlert("Lỗi lấy danh sách dịch vụ viễn thông",2); this.loading(false);
            }
      },
     async HT_QuyTrinh_Combobox(){
           try{

               let response = await api.list_all_quytrinh(this.axios, {})
                if(response.data.error_code && response.data.error_code === this.success_code) {
                    this.cboTKQuyTrinh.list = response.data.data.filter(x=>x.quytrinh_id > 0 && x.loaihd_id == (this.chkTKLoaiHD.Checked?this.cboTKLoaiHD.SelectedValue == null? x.loaihd_id: this.cboTKLoaiHD.SelectedValue:x.loaihd_id) && x.dichvuvt_id == (this.chkTKDichVuVT.Checked?this.cboTKDichVuVT.SelectedValue == null? x.dichvuvt_id: this.cboTKDichVuVT.SelectedValue:x.dichvuvt_id));
                    //this.cboTKQuyTrinh.list = response.data.data.filter(x=>x.quytrinh_id > 0 && x.loaihd_id == (this.cboTKLoaiHD.SelectedValue == null? 0: this.cboTKLoaiHD.SelectedValue) && x.dichvuvt_id == (this.cboTKDichVuVT.SelectedValue == null? 0: this.cboTKDichVuVT.SelectedValue));
                    if(this.cboTKQuyTrinh.list.length > 0){
                        this.cboTKQuyTrinh.SelectedValue = this.cboTKQuyTrinh.list[0].quytrinh_id;
                    }
                }
              else
              {
                this.cboTKQuyTrinh.list = [];
                this.cboTKQuyTrinh.SelectedValue = null;
                this.ShowAlert("Không tìm thấy thông tin quy trình!",1);
              }
          }catch(e){
            console.log(e);
              this.cboTKQuyTrinh.list = [];
              this.cboTKQuyTrinh.SelectedValue = null;
              this.ShowAlert("Lỗi lấy thông tin quy trình!",2);this.loading(false); }

      },
     async LAY_DS_HUONGGIAO()
        {
            try
            {
              let nhom_qt_id = 1;
              if(this.chkTKQuyTrinh.Checked){
                nhom_qt_id = this.cboTKQuyTrinh.list.filter(x=>x.quytrinh_id == this.cboTKQuyTrinh.SelectedValue)[0].nhom_qt_id;
              }
              let response = await api.sp_lay_ds_huonggiao(this.axios,{
                  "nguoidung_id":this.$root.token.getNguoiDungID(),
                  "nhom_qt_id":nhom_qt_id,
                  "quytrinh_id":this.chkTKQuyTrinh.Checked? this.cboTKQuyTrinh.SelectedValue==null?0:this.cboTKQuyTrinh.SelectedValue : 0,
                  "loaihd_id":this.chkTKLoaiHD.Checked? this.cboTKLoaiHD.SelectedValue : 0,
                  "dichvuvt_id":this.chkTKDichVuVT.Checked? this.cboTKDichVuVT.SelectedValue : 0
               })
              if(response.data.error_code && response.data.error_code === this.success_code) {
                    this.cboTKHuongGiao.list = response.data.data;
                    this.cboTKHuongGiao.SelectedValue = response.data.data[0].huonggiao_id;

                }
              else
              {
                this.cboTKHuongGiao.list  = [];
                this.cboTKHuongGiao.SelectedValue = null;
                this.ShowAlert("Không tìm thấy thông tin hướng giao!",1);
              }
            }
            catch (ex)
            {
              console.log(ex);
              this.cboTKHuongGiao.list = [];
              this.cboTKHuongGiao.SelectedValue = null;
              this.ShowAlert("Lỗi lấy thông tin hướng giao!",2);this.loading(false);
            }
        },
        HienThi_GiaoDien(){
          this.grcList.selectedItems = [];
          this.dsSelected = [];
            if (this.kieu_id == 1)
            {
                this.cboTKHuongGiao.SelectedValue = this.hg_id;
                let data = this.cboTKHuongGiao.list.filter(x=>x.huonggiao_id  == this.hg_id);
                //KieuNV: Nếu không có hướng giao được phân quyền thì đưa ra thông báo
                if (data.length <= 0)
                {
                    var msg = "Nhóm quyền của bạn chưa được phân hướng giao số "+ hg_id+ ". Liên hệ admin để được hỗ trợ.";
                    this.ShowAlert(msg,2);
                    return;
                }

                this.HienThi_DanhSach();
            }
             //this.HienThi_DanhSach();
        },
      async HienThi_DanhSach()
        {
            try
            {
                //Chưa giao
                if (this.cboTT.SelectedValue == 1)
                {
                    this.tsbtnHuyGiao.Enabled = false;
                    this.tsbtnTimKiem.Enabled = true;
                    this.tsbtnGiaoPhieu.Enabled = true;
                    //ctmsGiaoPhieu.Visible = true;
                    //ctmsHuyPhieu.Visible = false;
                }
                else
                {
                    let KHONG_HUY_GIAOPHIEU = -1;
                    let temp = this.dsThamSo.list.filter(x=> x.ma_ts === 'KHONG_HUY_GIAOPHIEU');
                    if (temp != null && temp.length > 0)
                    {
                        KHONG_HUY_GIAOPHIEU = temp.ten_ts;
                    }
                    if (KHONG_HUY_GIAOPHIEU == 1)
                        this.tsbtnHuyGiao.Enabled = false;
                    else
                        this.tsbtnHuyGiao.Enabled = true;
                        this.tsbtnTimKiem.Enabled = false;
                        this.tsbtnGiaoPhieu.Enabled = false;
                        //ctmsGiaoPhieu.Visible = false;
                        //ctmsHuyPhieu.Visible = true;
                }

                if (this.cboTKHuongGiao.list.length <= 0) return;
                if (this.cboTKHuongGiao.SelectedValue == null) return;

                //nhapt 17052016
                //kiểm tra hướng giao này có gửi tin nhắn hay không
                let huonggiao_selected = this.cboTKHuongGiao.list.filter(x=> x.huonggiao_id === Number(this.cboTKHuongGiao.SelectedValue))[0];
                if (huonggiao_selected.sms == "1")
                {
                  this._pSendSMS=true;
                }
                else
                {
                  this._pSendSMS = false;
                }
                if (huonggiao_selected.giaoviec == "1")
                {
                  this._Giaoviec_td=true;
                }
                else
                {
                  this._Giaoviec_td = false;
                }
                if (huonggiao_selected.giaoviec_nvkd == "1")
                {
                  this._Giaoviec_nvkd=true;
                }
                else {
                  this._Giaoviec_nvkd = false;
                }
                this.ClearTextBox();
                if(this.cboTT.SelectedValue == 1){
                   this.colnoidung_Visible = false;
                   this.colnoidungtra_Visible = true;
                   await this.LAY_DS_HDTB_CHUAGIAO();
                }
                else if(this.cboTT.SelectedValue == 2){
                   this.colnoidung_Visible = true;
                   this.colnoidungtra_Visible = false ;
                   await this.LAY_DS_HDTB_DAGIAO();
                }

            }
            catch (exp)
            {
                console.log(exp);
            }
        },
        ClearTextBox(){
            this.txtDiaChiTC = "";
            this.txtMaGDChon = "";
            this.txtMaTBChon = "";
            this.txtNoiDung = "";
            this.txtSoPhieu = "";
        },
       async LAY_DS_HDTB_CHUAGIAO(){
         try{
            let response = await api.sp_lay_ds_hdtb_chuagiao(this.axios,{
            "dichvuvt_id":this.chkTKDichVuVT.Checked? this.cboTKDichVuVT.SelectedValue: 0,
            "loaihd_id":this.chkTKLoaiHD.Checked?this.cboTKLoaiHD.SelectedValue:0,
            "huonggiao_id":this.cboTKHuongGiao.SelectedValue == null?0:this.cboTKHuongGiao.SelectedValue,
            "donvi_id":this.$root.token.getDonViID(),
            "ma_gd":this.txtMaGD == ""? "0": this.txtMaGD,
            "nhanvien_id":this.$root.token.getNhanVienID(),
            })
            if(response.data.error_code && response.data.error_code === this.success_code){
                this.grcList.list = response.data.data;

            }
            else{
              this.grcList.list = [];
              this.$toast.warning("Không tìm thấy phiếu")
              //this.ClearTextBox();
            }
         }
         catch(e){
              console.log(e);
              this.grcList.list = [];
           this.$toast.warning("Không tìm thấy phiếu")
              //this.ClearTextBox();
         }


       },
      selectRowsChon (args) {


          if (this.dsSelected.length > 0) {

            if (this.grcList.list.length > 0) {

              for (let i = 0; i < this.grcList.list.length; i++) {

                this.dsSelected.forEach(x => {

                  if (this.grcList.list[i].hdkh_id == x) {

                    this.grcList.selectedItems.push(i);

                  }
                })
              }
            }

          this.$refs.grcList.selectRows(this.grcList.selectedItems);
        } else {
          this.$refs.grcList.selectRows([])
        }

      },
       async LAY_DS_HDTB_DAGIAO(){
           try{
            let response = await api.lay_ds_hdtb_dagiao(this.axios,{
            "dichvuvt_id":this.chkTKDichVuVT.Checked? this.cboTKDichVuVT.SelectedValue: 0,
            "loaihd_id":this.chkTKLoaiHD.Checked?this.cboTKLoaiHD.SelectedValue:0,
            "huonggiao_id":this.cboTKHuongGiao.SelectedValue == null?0:this.cboTKHuongGiao.SelectedValue,
            "donvi_id":this.$root.token.getDonViID(),
            "ma_gd":this.txtMaGD == ""? "0": this.txtMaGD,
            "nhanvien_id":this.$root.token.getNhanVienID(),
            "loaipt_id":0,
            "ngay_giao":(this.dtpNgayGiaoTK== null? 0: this.dtpNgayGiaoTK),
            })
            if(response.data.error_code && response.data.error_code === this.success_code){
                this.grcList.list = response.data.data;
                if(this.grcList.list.length <= 0){
                  this.$toast.warning("Không tìm thấy phiếu")
                }
            }
            else{
              this.grcList.list = [];
              this.$toast.warning("Không tìm thấy phiếu")
              //this.ClearTextBox();
            }
         }
         catch(e){
              console.log(e);
              this.grcList.list = [];
           this.$toast.warning("Không tìm thấy phiếu")
              //this.ClearTextBox();
         }
       },
     async cboTT_SelectedValueChanged(){
         try{

            if(this.cboTT.SelectedValue == 1){
            if(this.cboTKHuongGiao.list.length == 0) return;
            if(this.cboTKHuongGiao.SelectedValue == null) return;
            this.btnCapNhatVT.Enabled = true;
            this.cboDVNhan.Enabled = true;

          }
          else{
            this.btnCapNhatVT.Enabled = false;
            this.cboDVNhan.Enabled = false;
          }
          if (!this.kt_load){
              this.loading(true);
             await this.HienThi_DanhSach();
              this.loading(false);
          }
         }
         catch(e){
            console.log(e);
            this.loading(false);
         }

       },

      async chkTKLoaiHD_CheckedChanged(){
        try{
            this.loading(true);
            await this.HT_QuyTrinh_Combobox();
            if (!this.kt_load){
             await this.LAY_DS_HUONGGIAO();
            }
            this.loading(false);
        }
        catch(e){
            this.loading(false);
        }
       },
       async cboTKLoaiHD_SelectedValueChanged(){
          try{
            this.loading(true);
            await this.HT_QuyTrinh_Combobox();
            if (!this.kt_load){
             await this.LAY_DS_HUONGGIAO();
            }
            this.loading(false);
        }
        catch(e){
            this.loading(false);
        }
       },
       async chkTKDichVuVT_CheckedChanged(){
          try{
            this.loading(true);
            await this.HT_QuyTrinh_Combobox();
            if (!this.kt_load){
             await this.LAY_DS_HUONGGIAO();
            }
            this.loading(false);
        }
        catch(e){
            this.loading(false);
        }
       },
       async cboTKDichVuVT_SelectedValueChanged(){
           try{
            this.loading(true);
            await this.HT_QuyTrinh_Combobox();
            if (!this.kt_load){
             await this.LAY_DS_HUONGGIAO();
            }
            this.loading(false);
        }
        catch(e){
            this.loading(false);
        }
       },
       async chkTKQuyTrinh_CheckedChanged(){
         try{
           this.loading(true);
            if (!this.kt_load){

             await this.LAY_DS_HUONGGIAO();
            }
            this.loading(false);
         }
         catch(e){
            this.loading(false);
         }

       },
       async cboTKQuyTrinh_SelectedValueChanged(){
         try{
           this.loading(true);
            if (this.chkTKQuyTrinh.Checked){
            if (!this.kt_load){

             await this.LAY_DS_HUONGGIAO();
            }
         }
         this.loading(false);
         }
         catch(e){
           this.loading(false);
         }

       },
      async cboTKHuongGiao_SelectedValueChanged(){

       },
      async GetDSDonViNhan(){
        try{
          this.loading(true);
        let response = await api.fn_lay_loaidv_dich_theo_hg(this.axios, {
          "huonggiao_id":this.cboTKHuongGiao.SelectedValue
        })
        if(response.data.error_code && response.data.error_code === this.success_code) {
            this.loaidv_nhan_id = response.data.data;
            if (this.loaidv_nhan_id != "-1")
            {

              let response1 = await api.sp_ht_tatca_vetinh_combobox_v2(this.axios, {
                  "huonggiao_id":this.cboTKHuongGiao.SelectedValue,
                  "loaidv_id":this.loaidv_nhan_id,
              })
              if(response1.data.error_code && response1.data.error_code === this.success_code) {
                  this.cboDVNhan.list = response1.data.data;
                  this.cboDVNhan.SelectedValue = this.cboDVNhan.list[0].donvi_id;
                }
                else
                {
                  this.cboDVNhan.list = [];
                  this.cboDVNhan.SelectedValue = null;
                  this.ShowAlert("Có lỗi lấy danh sách đơn vị nhận!",2);
                }
              }
            this.loading(false);
          }
          else
          {
            this.cboDVNhan.list = [];
            this.cboDVNhan.SelectedValue = null;
            this.ShowAlert("Có lỗi lấy danh sách đơn vị nhận!",2);
            this.loading(false);
          }
        }catch(e){ this.ShowAlert("Lỗi lấy danh sách đơn vị nhận",2);this.loading(false); }
       },
     async GiaoPhieu(){
       try{

          if (this.cboTKHuongGiao.SelectedValue == null)
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
          //if(this.txtNoiDung.trim().length >= 400)

          this.TC_ThoiGianXuLy = new Date(moment(new Date).format("YYYY-MM-DD HH:mm:ss")).getTime();
          this.loading(true);
          let dsPhieuSelect = [];
          dsPhieuSelect = this.$refs.grcList.getSelectedRecords();
          if(dsPhieuSelect.length <= 0){
              this.ShowAlert("Bạn chưa chọn phiếu để chuyển",2);
              return;
          }
          else{
            dsPhieuSelect = dsPhieuSelect.map( function( item ){
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
            if (this.cboTKHuongGiao.SelectedValue == 1)
            {
              this._Giaoviec_td = true;
            }
            else
            {
              this._Giaoviec_td = false;
            }
            if (this.cboTKHuongGiao.SelectedValue == 1)
            {
              this._Giaoviec_nvkd = true;
            }
            else
            {
              this._Giaoviec_nvkd = false;
            }


              let response = await api.fn_giaophieu(this.axios, {
                  "donvi_id": this.$root.token.getDonViID(),
                  "huonggiao_id": this.cboTKHuongGiao.SelectedValue,
                  "ip_cn": "",
                  "json_dsphieu": JSON.stringify(dsPhieuSelect),
                  "kt_daucuoi": this.kt_daucuoi,
                  "may_cn": "" ,
                  "ngay_cn": moment().format("DD/MM/YYYY HH:mm:ss"),
                  "nguoi_cn": this.$root.token.getUserName(),
                  "nhanvien_id": this.$root.token.getNhanVienID(),
                  "noidung": this.txtNoiDung,
                  "nt_huonggiao": this.NHANTIN_THEO_HG,
                  "nt_nhanvien": this.SEND_SMS_NHANTIN_NV,
                  "nt_phoihop": this.NHANTIN_NHANVIEN_PHOIHOP,
                  "sophieu": (this.txtSoPhieu == null? "": this.txtSoPhieu),
                  "taoduyet": 0
            })

              if(response.data.error_code && response.data.error_code === this.success_code) {
                this.ShowAlert("Giao phiếu thành công!",0);
                //Load lại danh sách
               await this.HienThi_DanhSach();

               this.loading(false);
              }
              else
              {
                this.loading(false);
                this.ShowAlert("Giao phiếu không thành công!",2);
              }
              }
            let currentDate = new Date(moment(new Date).format("YYYY-MM-DD HH:mm:ss")).getTime();
            let myDuration = currentDate - this.TC_ThoiGianXuLy;
            this.lblThoiGianTao = this.formattedTime(myDuration);
          }
           catch(e){
              this.lblThoiGianTao = "00:00:00";
              console.log(e);
              this.ShowAlert("Lỗi Giao phiếu không thành công",2);
              this.loading(false);
            }
     },
    formattedTime(milliseconds){
      let seconds = Math.floor((milliseconds / 1000) % 60);
      let minutes = Math.floor((milliseconds / 1000 / 60) % 60);
      let hours = Math.floor((milliseconds / 1000 / 60 / 60) % 24);
      return [
        hours.toString().padStart(2, "0"),
        minutes.toString().padStart(2, "0"),
        seconds.toString().padStart(2, "0")
      ].join(":");
    },
    async HuyPhieu(){
       try{

         if (this.cboTKHuongGiao.SelectedValue == null)
        {
          this.ShowAlert("Bạn chưa chọn hướng trả",2);
          return;
        }
         this.TC_ThoiGianXuLy = new Date(moment(new Date).format("YYYY-MM-DD HH:mm:ss")).getTime();
        let dsPhieuSelect = [];
        dsPhieuSelect = this.$refs.grcList.getSelectedRecords();
        if (this.$refs.grcList.getSelectedRecords().length>0)
        {
          dsPhieuSelect =  this.$refs.grcList.getSelectedRecords();
          dsPhieuSelect = dsPhieuSelect.map( function( item ){
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
        temp = dsPhieuSelect.map(x=>({CHON:1,PHIEU_ID:x.PHIEU_ID,HDTB_ID:x.HDTB_ID,MA_TB:x.MA_TB,TTPH_ID:x.TTPH_ID,LOAIPT_ID:x.LOAIPT_ID,STATUS:x.STATUS,PHIEU_CHA_ID:x.PHIEU_CHA_ID}));

          var input = {};
          input.json_ds_phieu = JSON.stringify(temp);
          input.huonggiao_id = this.cboTKHuongGiao.SelectedValue;
          //console.log(input);
          this.loading(true);
          let response = await api.fn_huyphieu(this.axios, input);
            if(response.data.error_code && response.data.error_code === this.success_code) {
              this.ShowAlert("Trả phiếu thành công!",0);
              //Load lại danh sách
              this.HienThi_DanhSach();
              this.loading(false);
            }
            else
            {
              this.loading(false);
              this.ShowAlert("Trả phiếu không thành công!",2);
            }
         let currentDate = new Date(moment(new Date).format("YYYY-MM-DD HH:mm:ss")).getTime();
         let myDuration = currentDate - this.TC_ThoiGianXuLy;
         this.lblThoiGianTao = this.formattedTime(myDuration);
       }
        catch(e){
          this.lblThoiGianTao = "00:00:00";
          console.log(e);
          this.ShowAlert("Lỗi trả phiếu không thành công",2);
          this.loading(false);}
     },
       async txtMaHD_KeyPress(){
              this.loading(true);
         this.grcList.selectedItems = [];
         this.dsSelected = [];
              await this.HienThi_DanhSach();
              this.loading(false);
        },
       async tsbtnTimKiem_Click(){
              this.loading(true);
         this.grcList.selectedItems = [];
         this.dsSelected = [];
              await this.HienThi_DanhSach();
              this.loading(false);
        },
       tsbtnGiaoPhieu_Click(){
         this.$refs.txtMaGD.focus();
          this.GiaoPhieu();
        },
       tsbtnHuyGiao_Click(){
         this.$refs.txtMaGD.focus();
         this.HuyPhieu();
       },
       tsbtnXuatExcel_Click(){
          if (this.grcList.list.length <= 0)
            {
              this.ShowAlert("Không có dữ liệu !",2);
                return;
            }
         this.$refs.exportDataModal.showModal();
       },
       tsbUpHoSo_Click(){
         this.$refs.popuploadEProfile.show();
       },
       gridChanged(obj){

       },
       selectedItems_click(dataKeys){

       },
       rowClicked(idx,obj){

            this.index = idx;
            if(!obj) {
              return;
            }

            this.txtMaTBChon = obj.ma_tb;
            this.txtMaGDChon =  obj.ma_gd;
            this.txtSoPhieu = obj.sophieu;
            this.txtNoiDung = obj.nd_giao;
            this.txtDiaChiTC = obj.diachi_ld;
            this.cboDVNhan.SelectedValue = obj.donvinhan_id;
            if (obj.dichvuvt_id == DichVuVienThong.TSL)
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

      async CapNhat_DonViNhan() {
        try {
          if (this.grcList.list.length <= 0) {
            this.ShowAlert("Không có dữ liệu. Bạn hãy kiểm tra lại!", 2);
            return;
          }
          this.grcList.selectedItems = [];
          this.dsSelected = [];
          for (var item of this.$refs.grcList.getSelectedRecords()) {
            if (this.loaidv_nhan_id != "-1") {

              this.dsSelected.push(item.hdkh_id);
              this.loading(true);
              let response = await api.capnhat_donvinhan(this.axios, {
                "hdtb_id": item.hdtb_id,
                "donvi_id": this.cboDVNhan.SelectedValue,
                "loaidv_id": this.loaidv_nhan_id,
                "kieudv_id": this.cboTKDichVuVT.SelectedValue != DichVuVienThong.TSL ? 2 : 1
              })

              if (response.data.error_code && response.data.error_code === this.success_code) {
                this.ShowAlert("Cập nhật đơn vị nhận thành công!", 0);
                this.loading(false);
              } else {
                this.loading(false);
                this.ShowAlert("Cập nhật đơn vị nhận không thành công!", 2);
              }

            }
          }
        } catch (e) {
          console.log(e);
          this.ShowAlert("Lỗi cập nhật đơn vị nhận không thành công!", 2);
          this.loading(false);
        }



      //Load lại danh sách
      await this.HienThi_DanhSach();
        // this.dsSelected = [];
        // this.grcList.selectedItems = [];

       },
      btnCapNhatVT_Click(){
        this.CapNhat_DonViNhan();
      },

  },
}

</script>
