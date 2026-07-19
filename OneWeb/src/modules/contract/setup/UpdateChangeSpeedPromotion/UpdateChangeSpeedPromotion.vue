<template src="./UpdateChangeSpeedPromotion.html"></template>
<style scoped src="./UpdateChangeSpeedPromotion.scss"></style>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
import "@/assets/vendor/jquery/split";
import moment from "moment";
import gridview from "@/components/Shared/gridview";
import SearchAccount from "@/modules/search/subscriber/SearchAccount";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
// import ThongTinEmail from "@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue";
import Payment from "@/modules/contract/setup/Payment/Payment.vue"
import Vue from "vue";
import { DialogPlugin } from '@syncfusion/ej2-vue-popups';
import popupTraCuuThueBao from "./popupTraCuuThueBao"; //tham chiếu qua kypc_dev
// import ThongTinHenKhachHang from "@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue"
// import  ThongTinLienHe from "@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue"
import {LoaiHopDong, KieuLapDat, KHOANMUC_TT, DichVuVienThong, LoaiHinhTB, TrangThaiHD} from './ThamSo';
import popupDieuChinh from './popupDieuChinh.vue';
import convert from 'xml-js';
import ModalInPhieu from '@/modules/print/InHopDong/InHopDong.vue';
import popupKM from '@/modules/contract/setup/InstallNewSubs/Popups/popup-ChonKhuyenMai';
import Popup_TienKM from '@/modules/contract/setup/InstallNewSubs/Popups/popupTienKM.vue';
import popupThongTinTC from '@/modules/contract/setup/InstallNewSubs/Popups/popupThongTinTC.vue';
import frmDangKyGoiDaDVLM from '@/modules/admin/category/NiceNumberStyle/popups/frmDangKyGoiDaDVLM.vue';


Vue.use(DialogPlugin)
export default {
  components: {
    breadcrumb,
    gridview,
    SearchAccount,
    SearchContractModal,
    // ThongTinEmail,
    moment,
    DialogPlugin,
    Payment,
    popupTraCuuThueBao,
    popupDieuChinh,
    ModalInPhieu,
    popupKM,
    Popup_TienKM,
    popupThongTinTC,
    frmDangKyGoiDaDVLM
    // ThongTinHenKhachHang,
    // ThongTinLienHe,

  },
  data: function () {
    return {
      dsKM_HDTB: [],
      dsDC_HDTB: [],
      kiemtra: false,
      dataSearchAccount: {},
      param: {},
      header: {
        title: "Sửa khuyến mại thay đổi tốc độ Internet",
      },
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput:true,
      },
      datetimeconfig: {
        altFormat: 'd/m/Y H:i:S',
        altInput: true,
        dateFormat: 'd/m/Y H:i:S',
        allowInput:true,
        enableTime: true,
      },
      title: '',
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: '',
      popupComponentEvents: {
        'form-close':'popupClosed',
        'acceptChangeCTV':'popupClosed',
        'acceptChangeNGT':'popupClosed',
      },
      listTT_Phieu_YC:{
        ngayyeucau: moment(new Date()).format("DD/MM/YYYY"),
        magiaodich:null,
        maHDPL: null,
        ngayLapHD: moment(new Date()).format("DD/MM/YYYY"),
        dichvuvt_options: [],
        dichvuvt: 0,
        loaihinh_options: [],
        loaihinh: null,
        kieuyc_options: [],
        kieuyc: null,
      },
      list_TTThueBao:{
        hc_hdtb_id: 0,
        hc_tdtt_hdtb_id: 0,
        thuebaoid:null,
        hopdongtbid:null,
        diachitb: null,
        diachild: null,
        mathuebao: "",
        tenthuebao: null,
        loaitbi_options: [],
        loaitbi: null,
        tocdomoi_options: [],
        tocdomoi: null,
        tocdothucmoi_options: [],
        tocdothucmoi: null,
        tocdocu_options: [],
        tocdocu: 0,
        tocdothucmoi:null,
        tocdothuccu:null,
        sothangCK: null,
        muccuocmoi_options: [],
        muccuocmoi: null,
        muccuoccu_options: [],
        muccuoccu: null,
        loaiip_options: [],
        loaiip: null,
        thangtra_options: [],
        thangtra: null,
        ngaytra: new Date(),
        cuocsd:null,
        tientratruoc:null,
        cuocthietbi:null,
        doituongmoi_options: [],
        doituongmoi: null,
        doituongcu: null,
        doituongcu_options: [],
        muccuoctbmoi_options: [],
        muccuoctbmoi: null,
        muccuoctbcu_options: [],
        muccuoctbcu: null,
        trangbimoi_options: [],
        trangbimoi: null,
        trangbicu_options: [],
        trangbicu: null,
        khuyenmai_options: [],
        khuyenmai:null,
        tratruoc_options:[],
        tratruoc: null,
        donviql: null,
        congnghe: null,
        congnghemoi: null,
        congnghemoi_options:[],
        tocdothuc: null,
        return_danhba: []
      },
      list_TTKhachHang:{
        makhachhang: null,
        ngaysinh: null,
        tenkhachhang: null,
        diachikhachhang: null,
        hopdongkhid: null,
        khachhangid:0,
        tungay: new Date(),
        denngay: new Date(),
        txtGhiChu: null,
        sothang: 0,
        thongtintc: null,
        txtTTTCVisa: null,
        return_hdkh: [],
        
      },
      list_ChiTietTien:{
        ds_tien_khoanmuc: [],
        ds_tien_khoanmuc_bd: [],
        tien: 0,
        tienvat: 0,
        tongtien: 0,
        dsCTTHD: []
      },
      params: {
        isIPtinh: 0, 
        p_today: new Date(),
        disable: true,
        chk_tucthi: false,
        value_tucthi:0,
        p_apldid: 0,
        p_khuldid: 0,
        p_pholdid: 0,
        p_phuongldid: 0,
        tongtienhd: 0,
        donvi_id: 0,
        chk_tttcvisa:false,
        isChktttcvisa: false,
        ts_sinhma_gd_theo_donvi: false, //Dungf cho Hni sinh ma GD theo don vi quan ly cua ng dung
        ngaytucthi: 1,
        nhap_tttc: 0,//Default không bắt buộc nhập thông tin tính cước
        chan_thaydoitd_conkhuyenmai: 0,
        kiemtra_khop_tien_datcoc: 0,
        check_chuanhoa_cntt: 0,
        batbuoc_tratruoc: 0,
        phainhap_thoihan: 0,
        batbuoc_ctv_tdtd: 0,
        BATBUOC_SOTHANG_CK: 0,
        md_tucthi_thaydoi_td: 0,
        kiemtra_no_tratruoc: 0,
      },
      ds_Thuebao_table: [],
      //keypress matb lấy địa chỉ lắp đặt
      returnd_diachi_ld:[],
      filterbox: {
        ctv: {
          value: "",
          ischecked: false,
          ma_ctv: '',
          ctv_id: 0,
        },
        nguoigt: {
          value: "",
          ischecked: false,
          nguoigt_id: '',
        },
      },
      isKhuyenMai:false,
      isTraTruoc:false,
      isChkKM: false,
      isChkTraTruoc: false,
      isThoiHan:false,
      isChkTucThi: true, //cho phép check tức thì
      isThongTinTC: false, 
      isCongNgheMoi: false,//ẩn hiện cbo công nghệ mới
      KHOANMUC_TT:{
        KMTT_DOITOCDO:3,
        KMTT_MUATHIETBI:5,
        KMTT_DATCOC:11,
        KMTT_KM_LAPDAT:19,
        KMTT_KM_THIETBI:20,
        KMTT_KM_DATCOC:21,
        KMTT_DATCOC_KM:22,
        KMTT_TRAGOP_THIETBI:24,
        KMTT_TIENTHOAI_DC_DOI_TD:35,
      },
      thamsomacdinh: {},
      button:{
        nhapmoi: false,
        ghilai:false,
        xoahd:false,
        themtb:false,
        xoatb:false,
        huybo:false,
      },
      tt_nd: null,
      tt_hddt: {
        tong_hddt: 0, 
        tongvat_hddt: 0,
        tongtien_hddt: 0,
        tbc: "Không đồng", 
        dsBDHDDT: [],
        Fkey: ''
      }
    };
  },
  validations: {},
  computed: {
    chkKhuyenMai: {
      get() {
        return this.isKhuyenMai;
      },
      set(value) {
        this.isKhuyenMai = value;
      },
    },
    chkTraTruoc: {
      get() {
        return this.isTraTruoc;
      },
      set(value) {
        this.isTraTruoc = value;
      },
    },
    chkThoiHan: {
      get() {
        return this.isThoiHan;
      },
      set(value) {
        this.isThoiHan = value;
        // if(this.isThoiHan==false){
        // }else{
        //    this.$toast.error("checked")
        //    this.$toast.error(this.list_TTKhachHang.denngay+"")
        // }
      },
    },
  },
  created() {
    this.loadDichVu();
    this.loadLoaiTbi();
    this.loadMucCuocMoi();
    this.loadCongNgheMoi();
    this.loadDoiTuongMoi();
    this.loadLoaiIP();
    this.loadThangTra();
    this.loadTrangBiMoi();
    this.SetButton(-1);
    this.lay_thamso_md();
    this.lay_tt_nd();
  },
  methods: {
    PopUpCongTacVien_Thoat(){
      this.$refs.popupComponents.hide();
    },
    async lay_tt_nd(){
      try {
        this.tt_nd = await this.$root.token.getThongTinNguoiDung();
      } catch (error) {
        console.log(error);
      }
    },
    //#region Thông tin phiếu yêu cầu
    lay_thamso_md: function () {
      API.LAY_DS_THAMSO_MD(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let ngaytucthi=1;
            let nhap_tttc =0;
            let kiemtra_khop_tien_datcoc =0
            let isChktttcvisa = false;
            let chan_thaydoitd_conkhuyenmai =0
            let check_chuanhoa_cntt = 0
            let batbuoc_tratruoc = 0
            let phainhap_thoihan = 0
            let batbuoc_ctv_tdtd =0
            let batbuoc_sothang_ck = 0
            let md_tucthi_thaydoi_td = 0
            let kiemtra_no_tratruoc = 0
            response.data.data.forEach(function (item) {
              if(item.ma_ts == "NGAY_TUCTHI") ngaytucthi = item.ten_ts
              if(item.ma_ts == "NHAP_THONGTIN_TINHCUOC") nhap_tttc = item.ten_ts
              if(item.ma_ts == "KIEMTRA_KHOP_TIEN_DATCOC") kiemtra_khop_tien_datcoc = item.ten_ts
              if(item.ma_ts == "KHONG_HIENTHI_TTTC_VISA") isChktttcvisa = item.ten_ts ==1? true : false
              if(item.ma_ts == "CHAN_DOI_TOCDO_CON_KM") chan_thaydoitd_conkhuyenmai = item.ten_ts
              if(item.ma_ts == "CHECK_CHUANHOA_CNTT") check_chuanhoa_cntt = item.ten_ts
              if(item.ma_ts == "BATBUOC_TRATRUOC") batbuoc_tratruoc = item.ten_ts
              if(item.ma_ts == "PHAINHAP_THOIHAN") phainhap_thoihan = item.ten_ts
              if(item.ma_ts == "BATBUOC_CTV_TDTD") batbuoc_ctv_tdtd = item.ten_ts
              if(item.ma_ts == "BATBUOC_SOTHANG_CK") batbuoc_sothang_ck = item.ten_ts
              if(item.ma_ts == "MACDINH_TUCTHI_THAYDOI_TD") md_tucthi_thaydoi_td = item.ten_ts
              if(item.ma_ts == "KIEMTRA_NO_TRATRUOC") kiemtra_no_tratruoc = item.ten_ts
            });
              this.params.ngaytucthi = ngaytucthi;
              this.params.nhap_tttc = nhap_tttc;
              this.params.kiemtra_khop_tien_datcoc  = kiemtra_khop_tien_datcoc;
              this.params.isChktttcvisa = isChktttcvisa;
              this.params.chan_thaydoitd_conkhuyenmai =chan_thaydoitd_conkhuyenmai;
              this.params.check_chuanhoa_cntt = check_chuanhoa_cntt;
              this.params.batbuoc_tratruoc = batbuoc_tratruoc;
              this.params.phainhap_thoihan = phainhap_thoihan;
              this.params.batbuoc_ctv_tdtd = batbuoc_ctv_tdtd;
              this.params.batbuoc_sothang_ck = batbuoc_sothang_ck;
              this.params.md_tucthi_thaydoi_td = md_tucthi_thaydoi_td;
              this.params.kiemtra_no_tratruoc = kiemtra_no_tratruoc;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadDichVu: function () {
      this.listTT_Phieu_YC.dichvuvt_options = [];
      this.listTT_Phieu_YC.dichvuvt = 0;
      let apiParams = {
        dichvu_vt_id: "4,13,14,15",
      };
      API.getDSVTcbb(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.dichvuvt_id, text: item.ten_dvvt });
            });
            this.listTT_Phieu_YC.dichvuvt_options = items;
            this.listTT_Phieu_YC.dichvuvt = items[0].id;
            this.loadLoaiHinh(items[0].id, 0);
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadLoaiHinh: function (dvvtid, loaihinhid) {
      //reset combobox
      this.listTT_Phieu_YC.loaihinh_options = [];
      //this.listTT_Phieu_YC.loaihinh = null;
      this.listTT_Phieu_YC.kieuyc_options = [];
      this.list_TTThueBao.tocdomoi_options = [];
      this.list_TTThueBao.tocdothucmoi_options = [];
      API.getLoaiHinh(this.axios, dvvtid)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.ID, text: item.NAME });
            });
            this.listTT_Phieu_YC.loaihinh_options = items;
            //nếu chọn cbb dịch vụ, loại hình = 0 load mặc định theo dịch vụ, enter_keypress load loại hình theo mã thuê bao
            if(loaihinhid == 0){
              this.listTT_Phieu_YC.loaihinh = items[0].id;
            }else{
              this.listTT_Phieu_YC.loaihinh = loaihinhid;
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadKieuYeuCau: function ( kieu, loaitbid) {
      this.listTT_Phieu_YC.kieuyc_options = [];
      this.listTT_Phieu_YC.kieuyc = null;
      let apiParams = {
        vloaihd_id: 8,
        vkieu: kieu,
        vloaitb_id: loaitbid,
      };
      API.getKieuYeuCaucbb(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.kieuld_id, text: item.ten_kieuld });
            });
            this.listTT_Phieu_YC.kieuyc_options = items;
            this.listTT_Phieu_YC.kieuyc = items[0].id;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    //#endregion
    SetButton(kieu){
      this.button.nhapmoi= false
      this.button.ghilai=false
      this.button.xoahd=false
      this.button.themtb=false
      this.button.xoatb=false
      this.button.huybo=false
      switch (kieu){
        case -1: //Bat dau
          this.button.ghilai=true;
          this.button.huybo=true;
          this.filterbox.ctv.ischecked=false;
          this.filterbox.nguoigt.ischecked=false;
          this.filterbox.ctv.value=null;
          this.filterbox.nguoigt.value=null;
        break;
        case 0: //Bat dau
          this.button.nhapmoi=true;
          this.clearText();
        break;
        case 1: //thêm mới
          this.button.ghilai = true;
          this.button.huybo=true;
          this.filterbox.ctv.ischecked=false;
          this.filterbox.nguoigt.ischecked=false;
          this.filterbox.ctv.value=null;
          this.filterbox.nguoigt.value=null;
          this.button.nhapmoi=false;
          this.clearText();
        break;
        case 2: //hủy
          this.button.nhapmoi=true;
          this.button.xoahd=true;
          this.clearText();
        break;
        case 3: //edit
          this.button.nhapmoi=true;
          this.button.xoahd=true;
          this.button.ghilai=true;
          this.button.huybo=true;
          this.button.themtb=true;
          if(this.ds_Thuebao_table.length>0){
            this.button.xoahd=true;
          }else{
            this.button.xoahd=false;
          }
        break;
      }

    },
    //#region Thông tin thuê bao
    //UR2.1.032_016
    async HienThiTTHopDongKH(ds){
      if(ds.length>0){
        console.log('ABC ',ds);
        this.list_TTKhachHang.hopdongkhid = ds[0].hdkh_id;
        this.listTT_Phieu_YC.magiaodich = ds[0].ma_gd;
        this.params.tongtienhd = ds[0].tongtien;
        this.list_TTThueBao.hopdongtbid = ds[0].hdtb_id;
        this.list_TTKhachHang.makhachhang = ds[0].ma_kh;
        this.list_TTKhachHang.tenkhachhang = ds[0].ten_kh;
        this.list_TTKhachHang.diachikhachhang = ds[0].diachi_kh;
        this.listTT_Phieu_YC.maHDPL = ds[0].ma_hd;
        this.listTT_Phieu_YC.ngayLapHD = ds[0].ngaylap_hd;
        this.listTT_Phieu_YC.ngayyeucau = ds[0].ngay_yc;
        this.list_TTKhachHang.ngaysinh = ds[0].ngay_sn//? moment(ds[0].ngay_sn).format("DD/MM/YYYY") : '';
        this.list_TTKhachHang.khachhangid = ds[0].khachhang_id;    
        this.list_TTKhachHang.return_hdkh = ds;
        await API.getDSHDTBTheoHDKHID(this.axios,this.list_TTKhachHang.hopdongkhid ).then((response) => { //4879227 
          if(response.data.error_code === "BSS-00000000" &&
                response.data.data !== undefined &&
                response.data.data.length > 0) {
                this.HienThiTTHopDongTB(response.data.data);
          }
        });
        // this.HienThiThongTinHopDongKH(3003, 4, 2739098, 2);
        if(this.listTT_Phieu_YC.dichvuvt == 13 || this.listTT_Phieu_YC.dichvuvt == 14 || 
          this.listTT_Phieu_YC.dichvuvt == 15){
          this.LAY_DS_HDTB_THAYDOI_TOCDO_CNTT(this.list_TTKhachHang.hopdongkhid);
           //this.LAY_DS_HDTB_THAYDOI_TOCDO_CNTT(2514155);
        }else{
          this.LAY_DS_HDTB_THAYDOI_TOCDO(this.list_TTKhachHang.hopdongkhid);
           //this.LAY_DS_HDTB_THAYDOI_TOCDO(this.listTT_Phieu_YC.magiaodich ==null ? null:1851924);
        }  
        this.SetButton(3);
        // this.$toast.error("hdkh "+this.list_TTKhachHang.hopdongkhid) 
      }else{
        this.SetButton(0);
      }
      // this.loading(false);
    },
    //UR2.1.032_016: output returnd_diachi
    async HienThiTTHopDongTB(ds){
      // debugger
      // this.loading(true);
      if(ds && ds.length >0){
        this.list_TTThueBao.hopdongtbid =ds[0].hdtb_id;
        this.list_TTThueBao.mathuebao = ds[0].ma_tb;
        this.list_TTThueBao.tenthuebao = ds[0].ten_tb;
        this.list_TTThueBao.thuebaoid = ds[0].thuebao_id;
        if(ds[0].diachi_tb){
          this.list_TTThueBao.diachitb= ds[0].diachi_tb;
        }
        if(ds[0].diachi_ld){
          this.list_TTThueBao.diachild = ds[0].diachi_ld;
        }
        // trangbi
        if(ds[0]['trangbi_id']){
          this.list_TTThueBao.trangbimoi = ds[0]['trangbi_id'];
        }
        if(ds[0]['trangbi_id_cu']){
          this.list_TTThueBao.trangbicu = ds[0]['trangbi_id_cu'];
        }
        // console.log('map trang bị ', ds[0]['trangbi_id'], ds[0]['trangbi_id_cu']);
        this.list_TTThueBao.sothangCK = ds[0].sothang_ck;
        // this.list_TTThueBao.tocdocu = ds[0].tocdo_id;
        ///TuấnNA thêm code lấy địa chỉ lắp đặt trong danh bạ
        ///--- Ngày 17/09/2010
        await API.lay_ds_diachi_theo_dbtbid(this.axios, {
          in_thuebao_id: this.list_TTThueBao.hopdongtbid,
        }).then((response) => {
            if (
              response &&
              response.data &&
              response.data.data &&
              response.data.error_code &&
              response.data.error_code === "BSS-00000000"
            ) {
              let ds_dcld = response.data.data;
              this.params.p_apldid = ds_dcld[0].apld_id;
              this.params.p_phuongldid = ds_dcld[0].phuongld_id ? ds_dcld[0].phuongld_id : 0;
              this.params.p_khuldid = ds_dcld[0].khuld_id ? ds_dcld[0].khuld_id : 0;
              this.params.p_pholdid = ds_dcld[0].phold_id ? ds_dcld[0].phold_id : 0;

            }
          })
          .catch((error) => {
            console.log(error);
          })

        this.listTT_Phieu_YC.dichvuvt = ds[0].dichvuvt_id;
        this.listTT_Phieu_YC.loaihinh = ds[0].loaitb_id;
        //load loại hình tb
        this.loadLoaiHinh(ds[0].dichvuvt_id,ds[0].loaitb_id);
        // load Kiểu yêu cầu
        this.loadKieuYeuCau(1, ds[0].loaitb_id);

        this.listTT_Phieu_YC.kieuyc = ds[0]['kieuld_id'];
        this.list_TTThueBao.doituongmoi = ds[0]['doituong_id'];
        if(this.list_TTThueBao.doituongmoi != '-1' && this.list_TTThueBao.doituongmoi != ''){
          //Đối tượng cũ
          this.list_TTThueBao.doituongcu = ds[0].doituong_id;
        }
        this.list_TTKhachHang.txtGhiChu = ds[0].ghichu;
        this.params.donvi_id = ds[0].donvi_id;
        
        this.list_TTThueBao.muccuoctbcu = ds[0].mucuoctb_id;
        //Mức cước TB mới
        this.loadMucCuocTB_V2(
            this.params.p_pholdid,
            this.list_TTThueBao.doituongcu,
            this.params.p_khuldid,
            this.listTT_Phieu_YC.loaihinh,
            this.list_TTThueBao.muccuoccu,
            moment(this.listTT_Phieu_YC.ngayyeucau).format("DD/MM/YYYY"),
            this.params.p_pholdid,
            this.params.p_phuongldid,
            this.list_TTThueBao.tocdocu,
            this.list_TTThueBao.trangbimoi,
            0
        );
        
        //mức cước TB cũ
        this.loadMucCuocTB_V2(
            this.params.p_pholdid,
            this.list_TTThueBao.doituongcu,
            this.params.p_khuldid,
            this.listTT_Phieu_YC.loaihinh,
            this.list_TTThueBao.muccuoccu,
            moment(this.listTT_Phieu_YC.ngayyeucau).format("DD/MM/YYYY"),
            this.params.p_pholdid,
            this.params.p_phuongldid,
            this.list_TTThueBao.tocdocu,
            this.list_TTThueBao.trangbimoi,
            this.list_TTThueBao.muccuoctbcu
        );
        //chưa có thongtin_tc
        this.list_TTThueBao.muccuoctbmoi = ds[0]["mucuoctb_id"]
          ? ds[0]["mucuoctb_id"]
          : 0;
          console.log('zzzz ',ds[0]["mucuoctb_id"]);
        this.list_TTThueBao.cuocsd = ds[0].cuoctamthu;
        this.list_TTThueBao.cuocthietbi = ds[0].cuoctratruoc;
        //load tốc độ mới
        this.loadTocDo(ds[0].loaitb_id, 0);
        //load tốc độ cũ
        this.loadTocDo(ds[0].loaitb_id,  this.list_TTThueBao.tocdocu );
        //Mức cước cũ
        this.list_TTThueBao.muccuoccu = ds[0].muccuoc_id;
        
        let ds_hdtb_adsl = [];
        if (this.listTT_Phieu_YC.dichvuvt == 15 || this.listTT_Phieu_YC.dichvuvt == 14 ||
            this.listTT_Phieu_YC.dichvuvt == 16 || this.listTT_Phieu_YC.dichvuvt == 13)
        {
          await API.lay_ds_hd_adsl_theo_hdtbcntt(this.axios, {
            in_hdtb_id: ds[0].hdtb_id,
          })
            .then((response) => {
              if (
                response &&
                response.data &&
                response.data.data &&
                response.data.error_code &&
                response.data.error_code === "BSS-00000000"
              ) {
                ds_hdtb_adsl = response.data.data; // this.ds_hdtb_adsl = new TCHopDongFacade().Lay_ds_hd_adsl_theo_hdtbCNTT(hdtb_id);
                //console.log("lay_ds_hd_adsl_theo_hdtbcntt: "+JSON.stringify(this.ds_hdtb_adsl));
              }
            })
            .catch((error) => {
              console.log(error);
            })
          // this.Init_Properties(this.cboLoaihinhTB.value, "HDTB_CNTT", "00000");
        }else{
          await API.lay_ds_hdtb_adsl_theo_hdtb_id(this.axios, ds[0].hdtb_id)
          .then((response) => {
            if (
              response &&
              response.data &&
              response.data.data &&
              response.data.error_code &&
              response.data.error_code === "BSS-00000000"
            ) {
              ds_hdtb_adsl = response.data.data; //new TCHopDongFacade().LAY_DS_HDTB_ADSL_THEO_HDTB_ID(hdtb_id);
              //console.log("lay_ds_hdtb_adsl_theo_hdtb_id: "+JSON.stringify(this.ds_hdtb_adsl));
            }
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {});
        }
        if (
          this.listTT_Phieu_YC.loaihinh == 11 || this.listTT_Phieu_YC.loaihinh == 58 || 
          this.listTT_Phieu_YC.loaihinh == 79
        ) {
          // this.btnThongTinTC.enabled = true;
          // this.txtTTCuoc.enabled = true;
          this.list_TTKhachHang.thongtintc = ds_hdtb_adsl[0]["thongtin_tc"];
        } else {
          // this.btnThongTinTC.enabled = false;
          // this.txtTTCuoc.enabled = false;
          this.list_TTKhachHang.thongtintc = "";
        }
        if (
          this.listTT_Phieu_YC.loaihinh == 57 ||
          this.listTT_Phieu_YC.loaihinh == 59
        ) {
          // this.txtCuoc_TBI.enabled = true;
          // this.txtCuocSD.enabled = true;
          if (ds_hdtb_adsl[0]["cuoc_tbi"]) {
            this.list_TTThueBao.cuocthietbi = ds_hdtb_adsl[0]["cuoc_tbi"];
          } else {
            this.list_TTThueBao.cuocthietbi = 0;
          }
          if (ds_hdtb_adsl[0]["cuoc_sd"]) {
            this.list_TTThueBao.cuocsd = ds_hdtb_adsl[0]["cuoc_sd"];
          } else {
            this.list_TTThueBao.cuocsd = 0;
          }
        } else {
          // this.txtCuoc_TBI.enabled = false;
          // this.txtCuocSD.enabled = false;
          this.list_TTThueBao.cuocthietbi = 0;
          this.list_TTThueBao.cuocsd = 0;
        }
        this.list_TTThueBao.tocdomoi = ds_hdtb_adsl[0]["tocdo_id"];
        this.list_TTThueBao.muccuocmoi = ds_hdtb_adsl[0]["muccuoc_id"];
      
        
        

        if (
          this.listTT_Phieu_YC.dichvuvt != 15 &&
          this.listTT_Phieu_YC.dichvuvt != 14 &&
          this.listTT_Phieu_YC.dichvuvt != 16 &&
          this.listTT_Phieu_YC.dichvuvt != 13
        ) {
          if (ds_hdtb_adsl[0]["tocdothuc_id"]) {
            this.list_TTThueBao.tocdothucmoi = ds_hdtb_adsl[0]["tocdothuc_id"];
          } else {
            this.list_TTThueBao.tocdothucmoi = ds_hdtb_adsl[0]["tocdo_id"];
          }
          if (ds_hdtb_adsl[0]["kieutra_id"]) {
            // this.cboKieuTraMoi.value = this.ds_hdtb_adsl[0]["kieutra_id"];
          }
          if (ds_hdtb_adsl[0]["congnghe_id"]) {
            // this.cboCongNghe_Moi.value = this.ds_hdtb_adsl[0]["congnghe_id"];
          } else {
            // this.cboCongNghe_Moi.value = 0;
          }
          // this.IP = ds_hdtb_adsl[0]["ip"];
          // if (ds_hdtb_adsl[0]["bras_id"]) {
          //   this.bras_id = this.ds_hdtb_adsl[0]["bras_id"];
          // }
          if (
            ds_hdtb_adsl[0]["tucthi"] &&
            ds_hdtb_adsl[0]["tucthi"] != "0"
          ) {
            this.params.chk_tucthi = true;
          }
          // if (
          //   ds[0]["loaitb_id"] == 11 ||
          //   ds[0]["loaitb_id"] == 58 ||
          //   ds[0]["loaitb_id"] == 79
          // ) {
          //   let ngay_yc = moment(this.dtpNgayYeuCau).format("DD");
          //   if (ngay_yc <= this.ngaytucthi) {
          //     this.chkTucThi.enabled = true;
          //   } else {
          //     this.chkTucThi.enabled = false;
          //     this.chkTucThi.checked = false;
          //   }
          // } else {
          //   this.chkTucThi.enabled = false;
          //   this.chkTucThi.checked = true;
          // }
        }else {
          if (ds_hdtb_adsl[0]["so_thang"]) {
            this.chkThoiHan = true;
            this.list_TTKhachHang.sothang = ds_hdtb_adsl[0]["so_thang"];
            if (ds_hdtb_adsl[0]["ngay_bd"]) {
              this.list_TTKhachHang.tungay = ds_hdtb_adsl[0]["ngay_bd"];
            } else {
              this.list_TTKhachHang.tungay = new Date();
            }
            if (ds_hdtb_adsl[0]["ngay_kt"] != "") {
              this.list_TTKhachHang.denngay = ds_hdtb_adsl[0]["ngay_kt"];
            } else {
              this.list_TTKhachHang.denngay = new Date();
            }
          } else {
            this.chkThoiHan = false;
            this.list_TTKhachHang.sothang = "0";
            this.list_TTKhachHang.tungay = new Date();
            this.list_TTKhachHang.denngay = new Date();
          }
        }
        this.HT_DS_KhuyenMai_Combobox();
        // if(this.listTT_Phieu_YC.dichvuvt != 14 && this.listTT_Phieu_YC.dichvuvt != 15 ){
        //   if(this.listTT_Phieu_YC.loaihinh == 58 || this.listTT_Phieu_YC.loaihinh == 79){
        //     let ngay_yc = moment(this.listTT_Phieu_YC.ngayyeucau).format("DD");
        //     if(ngay_yc <= this.params.ngaytucthi){
        //         this.isChkTucThi = true;
        //         if(this.value_tucthi==1){
        //           this.params.chk_tucthi =true;
        //         }else{
        //           this.isChkTucThi = true;
        //           this.params.chk_tucthi =false;
        //         }
        //     }else{
        //         this.isChkTucThi = false;
        //         this.params.chk_tucthi =true;
        //     }
        //   }
        // }
        //Longdx: bổ sung lấy chi tiết KM
        let dsTemp = [];
        this.dsKM_HDTB = []; // new TCHopDongFacade().LAY_CHITIET_KM_THEO_HDTB_ID(hdtb_id, 0);
        var self = this;
        await API.lay_chitiet_km_theo_hdtb_id(this.axios, {
          hdtb_id: ds[0].hdtb_id,
          loai_km: 0,
        })
          .then((response) => {
            if (
              response &&
              response.data &&
              response.data.data &&
              response.data.error_code &&
              response.data.error_code === "BSS-00000000"
            ) {
              dsTemp = response.data.data;
              this.dsKM_HDTB = [...dsTemp]
              this.list_TTThueBao.khuyenmai = dsTemp[0]["khuyenmai_id"];
              console.log('mmm', this.list_TTThueBao.khuyenmai, dsTemp[0]["khuyenmai_id"]);
              this.chkKhuyenMai = true;
              // console.log("lay_chitiet_km_theo_hdtb_id0: "+JSON.stringify(dsTemp))
            }else{
              this.chkKhuyenMai = false;
            }
          })
          .catch((error) => {
            console.log(error);
          })
        // Trả trước
        this.dsDC_HDTB = [];
        await API.lay_chitiet_km_theo_hdtb_id(this.axios, {
          hdtb_id: ds[0].hdtb_id,
          loai_km: 1,
        }).then((response) => {
          console.log('ds trả trước', response);
            if (
              response &&
              response.data &&
              response.data.data &&
              response.data.error_code &&
              response.data.error_code === "BSS-00000000"
            ) {
              this.dsDC_HDTB = response.data.data;
              this.list_TTThueBao.tratruoc = response.data.data[0]["khuyenmai_id"];
              this.chkTraTruoc = true;
              // console.log("lay_chitiet_km_theo_hdtb_id0: "+JSON.stringify(dsTemp))
            }else{
              this.chkTraTruoc = false;
              this.list_TTThueBao.tratruoc = this.list_TTThueBao.tratruoc_options[0]['id'];
            }
          })
          .catch((error) => {
            console.log(error);
          })

        // this.$toast.error("hdtb")
        //Thangph: Copy tu form Đặt mới -Ngày 10/6/2015
        // this.list_ChiTietTien.ds_tien_khoanmuc = new TCHopDongFacade().LAY_TIENHD_KM_THEO_HDTB(hdtb_id);
        await this.axios.post('/web-hopdong/khoiphucthanhly/lay_tienhd_km_theo_hdtb', {
          hdtb_id: this.list_TTThueBao.hopdongtbid
        }).then((response) => {
          if(response.data.error_code === "BSS-00000000"){
            this.list_ChiTietTien.ds_tien_khoanmuc = [...response.data.data];
            this.list_ChiTietTien.ds_tien_khoanmuc_bd = [...this.list_ChiTietTien.ds_tien_khoanmuc];
            for (let i = 0; i < this.list_ChiTietTien.ds_tien_khoanmuc; i++)
            {
                if (this.list_ChiTietTien.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString() == KHOANMUC_TT.KMTT_DOITOCDO.toString())
                {
                    dtienld = Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["tien"]);
                    dvatld = Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["vat"]);
                }
                if (this.list_ChiTietTien.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString() == KHOANMUC_TT.KMTT_DATCOC.toString())
                {
                    dtiendatcoc_tt = Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["tien"]);
                    dvatdatcoc_tt = Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["vat"]);
                }
                if (this.list_ChiTietTien.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString() == KHOANMUC_TT.KMTT_DATCOC_KM.toString())
                {
                    dtiendatcoc_km = Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["tien"]);
                    dvatdatcoc_km = Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["vat"]);
                }
            }
            this.LayTongTien_HDTB();
          }else{
            this.$toast.warning(response.data.message);
          }
        });

        let ds_cu = [];
        if (
          this.listTT_Phieu_YC.dichvuvt == DichVuVienThong.DICHVU_CNTT ||
          this.listTT_Phieu_YC.dichvuvt == DichVuVienThong.ANTOAN_BAOMAT_TT ||
          this.listTT_Phieu_YC.dichvuvt == DichVuVienThong.TRUNGTAM_DULIEU ||
          this.listTT_Phieu_YC.dichvuvt == DichVuVienThong.HOINGHI_TRUYENHINH
        ) {
          
          await API.lay_biendong_theo_hdtb_id_cntt(this.axios, {
            in_hdtb_id: this.list_TTThueBao.hopdongtbid,
          })
            .then((response) => {
              if (
                response &&
                response.data &&
                response.data.data &&
                response.data.error_code &&
                response.data.error_code === "BSS-00000000"
              ) {

                ds_cu = response.data.data; // new TCHopDongFacade().LAY_BIENDONG_THEO_HDTB_ID_CNTT(hdtb_id);//nhapt them rieng cho dvcntt
                console.log("lay_biendong_theo_hdtb_id_cntt: ", ds_cu);
              }
            })
            .catch((error) => {
              console.log(error);
            })
            .finally(() => {
              
            });
        } else {
          
          await API.lay_biendong_thongtin_thuebao(this.axios, this.list_TTThueBao.hopdongtbid)
            .then((response) => {
              if (
                response &&
                response.data &&
                response.data.data &&
                response.data.error_code &&
                response.data.error_code === "BSS-00000000"
              ) {
                ds_cu = response.data.data; // new TCHopDongFacade().LAY_BIENDONG_THEO_HDTB_ID(hdtb_id);
                console.log("lay_biendong_thongtin_thuebao: "+JSON.stringify(ds_cu));
              }
            })
            .catch((error) => {
              console.log(error);
            })
            .finally(() => {
              
            });
        }
        if (ds_cu.length > 0) {
          this.list_TTThueBao.tocdocu = ds_cu[0]["tocdo_id"];
          this.list_TTThueBao.muccuoccu = ds_cu[0]["muccuoc_id"];
          // this.cboKieuTraCu.value = ds_cu[0]["kieutra_id"];
          if (ds_cu[0]["mucuoctb_id"]) {
            await this.HT_MucCuocTB_Combobox(ds_cu[0]["mucuoctb_id"]);
          }
          // if (this.listTT_Phieu_YC.dichvuvt == DichVuVienThong.ADSL) {
          //   if (
          //     this.listTT_Phieu_YC.kieuyc == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON ||
          //     this.listTT_Phieu_YC.kieuyc == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON ||
          //     this.listTT_Phieu_YC.kieuyc == KieuLapDat.THAYDOI_CONGNGHE_AON_GPON ||
          //     this.listTT_Phieu_YC.kieuyc == KieuLapDat.THAYDOI_CONGNGHE_GPON_AON
          //   ) {
          //     if (ds_cu[0]["congnghe_id"]) {
          //       this.cboCongNghe_Cu.value = ds_cu[0]["congnghe_id"];
          //     } else {
          //       this.cboCongNghe_Cu.value = 0;
          //     }
          //   } else this.cboCongNghe_Cu.value = 0;
          // }
        }
        this.list_TTThueBao.sothangCK = ds[0]["sothang_ck"];
      }
      //Cuong moi them
      // if (this.listTT_Phieu_YC.loaihinh == LoaiHinhTB.INTERNET_FTTH) {
      //   // await this.getLoaiIP();
      //   let items = [];
      //   await API.lay_ds_loaiip_theo_hdtb_id(this.axios, ds[0]["hdtb_id"])
      //     .then((response) => {
      //       if (
      //         response &&
      //         response.data &&
      //         response.data.data &&
      //         response.data.error_code &&
      //         response.data.error_code === "BSS-00000000"
      //       ) {
      //         items = response.data.data;
      //       }
      //     })
      //     .catch((error) => {
      //       console.log(error);
      //     })
      //     .finally(() => {});
      //   var result = [];
      //   for (var i in items) {
      //     result.push(items[i].loaiip_id);
      //   }
      //   let arr = JSON.stringify(result);
      //   console.log("2793: " + arr);
      //   if (this.cboLoaiIP.values && this.cboLoaiIP.values.length > 0) {
      //     this.cboLoaiIP.value = this.cboLoaiIP.values[0];
      //     this.cboLoaiIP.valueitems = this.cboLoaiIP.data
      //       .filter((x) => arr.includes(x.loaiip_id))
      //       .map((lo) => ({ id: lo.loaiip_id, text: lo.loai_ip }));
      //   }
      // }
    },
    async HT_MucCuocTB_Combobox(mucuoctb_id) {
      this.list_TTThueBao.muccuoctbcu_options = [];
      this.list_TTThueBao.muccuoctbcu = 0;
      let data = [];
      await API.lay_muccuoc_thuebao(this.axios, mucuoctb_id)
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            var rowMucCuoc = {};
            rowMucCuoc.id = response.data.data.mucCuocTbId;
            rowMucCuoc.text = response.data.data.tenMucCuoc;
            data.push(rowMucCuoc);
            this.list_TTThueBao.muccuoctbcu_options = data;
            this.list_TTThueBao.muccuoctbcu = this.list_TTThueBao.muccuoctbcu_options[0].id;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          // this.loading(false);
        });
    },
    async getLoaiIP() {
      await API.sp_lay_dulieu_bang_theo_dieukien(this.axios, {
        schema: "css",
        list_of_cols: "loaiip_id,loai_ip",
        order: "loaiip_id",
        table_name: "loai_ip",
        where: "",
      })
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.cboLoaiIP.data = response.data.data;
            this.cboLoaiIP.value = response.data.data[0].loaiip_id;
            this.cboLoaiIP.values = [this.cboLoaiIP.data[0].loaiip_id];
            /*this.cboLoaiIP.valueitems =
              [];  [this.cboLoaiIP.data[0]].map((lo) => ({
              id: lo.loaiip_id,
              text: lo.loai_ip,
            })); */
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    LayTongTien_HDTB(){
      try
      {
          let dtongtien = 0;
          let dtongvat = 0;
          for (let i = 0; i < this.list_ChiTietTien.ds_tien_khoanmuc.length; i++)
          {
              if (this.list_ChiTietTien.ds_tien_khoanmuc[i]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_KM_LAPDAT
                ||this.list_ChiTietTien.ds_tien_khoanmuc[i]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_TIENTHOAI_DC_DOI_TD
                ||this.list_ChiTietTien.ds_tien_khoanmuc[i]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_KM_DATCOC
              )
              {
                  if (Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["tien"]) < 0)
                  {
                      dtongtien += Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["tien"]);
                      dtongvat += Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["vat"]);
                  }
                  else
                  {
                      dtongtien += Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["tien"]) * -1;
                      dtongvat += Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["vat"]) * -1;
                  }
              }
              else if (this.list_ChiTietTien.ds_tien_khoanmuc[i]["khoanmuctt_id"] != KHOANMUC_TT.KMTT_TRAGOP_THIETBI
                            && this.list_ChiTietTien.ds_tien_khoanmuc[i]["khoanmuctt_id"] != KHOANMUC_TT.KMTT_KM_THIETBI
                            && this.list_ChiTietTien.ds_tien_khoanmuc[i]["khoanmuctt_id"] != KHOANMUC_TT.KMTT_MUATHIETBI
                        )
              {
                  dtongtien += Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["tien"]);
                  dtongvat += Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["vat"]);
              }
              //dtongtien += Convert.ToDecimal(ds_tien_khoanmuc.Tables[0].Rows[i]["tien"].toString());
              //dtongvat += Convert.ToDecimal(ds_tien_khoanmuc.Tables[0].Rows[i]["vat"].toString());

          }
          // this.list_ChiTietTien.tien = dtongtien; //.toString(Format.FM_TIEN_VND);
          this.list_ChiTietTien.tienvat = dtongvat; //.toString(Format.FM_TIEN_VND);
          this.list_ChiTietTien.tien = (dtongtien + dtongvat); //.toString(Format.FM_TIEN_VND);
      }
      catch (ex)
      {
        this.$toast.error("Lỗi: " + ex.toString());
      }
    },
    //UR2.1.032_021
    HienThiTT_DanhBa(ma_tb) {
      let ds = [];
      let ds_km=[];
      let ds_temp = [];
      API.getTTDanhBa(this.axios, {
        "parameters": {
          "DICHVUVT_ID": this.listTT_Phieu_YC.dichvuvt, 
          "DONVIDL_ID": 0, 
          "MA_TB": ma_tb, 
          "THUEBAO_ID": 0}}).then(async (response) => {
        if(response.data.error_code === "BSS-00000000" &&
          response.data.data.returnd_danhba !== undefined &&
          response.data.data.returnd_danhba.length > 0) 
          {
            // debugger
            ds = response.data.data.returnd_danhba;
            this.list_TTThueBao.return_danhba= response.data.data.returnd_danhba;

            // nếu danh bạ có 2 rows trở lên thì hiện popup :))
            // if (ds.Tables[0].Rows.Count > 1)
            // {
            //     frmDSTBCungMa f = new frmDSTBCungMa();
            //     f.ds = ds;
            //     if (f.ShowDialog() == DialogResult.OK)
            //         ds = f.ds;
            // }
            //kiểm tra HD khác ma_tb, ma_gd, dichvu_id, loaihd_id
            if(this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(ma_tb, '0', this.listTT_Phieu_YC.dichvuvt, 8))
              return;

            //Hiếu tc 5.10.2010:
            ///---> Nếu trạng thái thuê bao # " Hoạt động bình thường" ---> không cho phép làm tiếp
            if (Number(ds[0]["trangthaitb_id"]) != 1) // TrangThaiTB.THUONG = 1
            {
              this.$toast.error("Thuê bao có mã: " + 
                                ds[0]["ma_tb"].toString() + 
                                " đang ở trạng thái: " + 
                                ds[0]["trangthai_tb"].toString() + 
                                "\nBạn không thể thực hiện thay đổi tốc độ cho thuê bao này")
              return;
            }
            await API.get_DS_QUYEN_CHUQUAN(this.axios).then((response) => {
              if (response.data.data != null) {
                let kq = "";
                API.kiem_tra_quyen_nguoidung_chuquan(this.axios, {
                  id: ds[0]["thuebao_id"],
                  ds_chuquan: response.data.data ? response.data.data : "",
                  khachhang_id: ds[0]["khachhang_id"],
                  kieu: 2,
                }).then((response) => {
                  if (response.data.data &&
                      response.data.error_code === "BSS-00000000"
                  ) {
                    kq = response.data.data;
                    if (kq != "OK") {
                      this.$toast.error("Có lỗi: " + kq);
                      return;
                    }
                  }
                });
              }
            });
            if (Number(ds[0]["ky_hd"]) == 0)
              {
                this.$toast.warning("Khách hàng có Account: " + ma_tb + " chưa ký lại hợp đồng gốc \nĐề nghị ký lại hợp đồng gốc")
                // Message_Box.ShowWarning("Khách hàng có Account: " + txtMaTB.Text + " chưa ký lại hợp đồng gốc \nĐề nghị ký lại hợp đồng gốc");
                return;
              }
            
            if (
              response.data.error_code === "BSS-00000000" &&
              response.data.data !== undefined &&
              response.data.data.returnd_diachi_ld.length > 0
            ) {
                this.returnd_diachi_ld = response.data.data.returnd_diachi_ld;
                this.params.p_apldid = response.data.data.returnd_diachi_ld[0].apld_id;
                this.params.p_phuongldid = response.data.data.returnd_diachi_ld[0].phuongld_id;
                this.params.p_khuldid = response.data.data.returnd_diachi_ld[0].khuld_id;
                this.params.p_pholdid = response.data.data.returnd_diachi_ld[0].phold_id;
            }
            // ds_km = bangts.LayDS_TB_Huong_KM(Convert.ToInt64(ds.Tables[0].Rows[0]["thuebao_id"].toString()));
            if(response.data.error_code === "BSS-00000000" &&
                response.data.data.returnd_dstb_huongkm !== undefined &&
                response.data.data.returnd_dstb_huongkm.length > 0) {
                ds_km = response.data.data.returnd_dstb_huongkm;
                let items = [];
                response.data.data.returnd_dstb_huongkm.forEach(function (item) {
                items.push({ ten_chitietkm: item.ten_chitietkm});
                });
                  this.list_TTThueBao.cuocsd = response.data.data.returnd_dstb_huongkm[0].cuoc_sd;
                  this.list_TTThueBao.cuocthietbi = response.data.data.returnd_dstb_huongkm[0].cuoc_tb;
                  var tenctkm = items.map(function(tag) {
                  return tag['ten_chitietkm'];
                });
                debugger
                if(this.params.chan_thaydoitd_conkhuyenmai ==0){
                  var tb = "Thuê bao " + ma_tb+ " đang hưởng khuyến mại : \n " + ds_km[0].ten_km 
                          + " \n    Có chi tiết khuyến mại : "+tenctkm+
                        "\nBạn không thể thực hiện nghiệp vụ này !";
                  this.$alert(tb, "Thông báo", {
                  dangerouslyUseHTMLString: true,
                  showCancelButton: false,
                  confirmButtonText: 'Đồng ý',
                  cancelButtonText: '',
                  showCloseButton: true,
                  type: "warning",
                  }).then(async (v) => {
                  if (!v) {return;}
                   // this.SetButton(1);
                  });
                }else{
                  var tb = "Thuê bao " + ma_tb+ " đang hưởng khuyến mại : \n " + ds_km[0].ten_km 
                          + " \n    Có chi tiết khuyến mại : "+tenctkm+
                        "\nBạn có muốn tiếp tục thực hiện?";
                  this.$alert(tb, "Thông báo", {
                  dangerouslyUseHTMLString: true,
                  showCancelButton: false,
                  confirmButtonText: 'Đồng ý',
                  cancelButtonText: '',
                  showCloseButton: true,
                  type: "warning",
                  }).then(async (v) => {
                  if (!v) {return;}
                   // this.SetButton(1);
                  });
                }
              }
              //Thông tin khách hàng
              this.list_TTKhachHang.makhachhang = ds[0].ma_kh;
              this.list_TTKhachHang.tenkhachhang = ds[0].ten_kh;
              this.list_TTKhachHang.diachikhachhang = ds[0].diachi_kh;
              this.listTT_Phieu_YC.maHDPL = ds[0].ma_hd;
              this.listTT_Phieu_YC.ngayLapHD = ds[0].ngaylap_hd;
              this.list_TTKhachHang.ngaysinh = ds[0].ngay_sn ? moment(ds[0].ngay_sn).format("DD/MM/YYYY") : '';
              this.list_TTKhachHang.khachhangid = ds[0].khachhang_id;
              //Thông tin thuê bao
              this.list_TTThueBao.tenthuebao= ds[0].ten_tb;
              this.list_TTThueBao.thuebaoid = ds[0].thuebao_id;
              this.list_TTThueBao.diachitb= ds[0].diachi_tb;
              this.list_TTThueBao.diachild = ds[0].diachi_ld;
              this.listTT_Phieu_YC.loaihinh = ds[0].loaitb_id;
              this.loadLoaiHinh(ds[0].dichvuvt_id, ds[0].loaitb_id);
              this.listTT_Phieu_YC.dichvuvt = ds[0].dichvuvt_id;
              this.list_TTThueBao.sothangCK = ds[0].sothang_ck;
              this.list_TTThueBao.doituongcu = ds[0].doituong_id;  
              this.list_TTThueBao.doituongmoi = ds[0].doituong_id;
              this.list_TTThueBao.donviql = ds[0].donviql_id;
              this.list_TTThueBao.mathuebao = ds[0].ma_tb;
              this.list_TTThueBao.muccuoctbcu = ds[0].mucuoctb_id;
              this.list_TTThueBao.muccuoccu = ds[0].muccuoc_id;
              this.list_TTThueBao.tocdocu = ds[0].tocdo_id;
              //Mức cước TB mới
              this.loadMucCuocTB_V2(
                    this.params.p_pholdid,
                    this.list_TTThueBao.doituongcu,
                    this.params.p_khuldid,
                    this.listTT_Phieu_YC.loaihinh,
                    this.list_TTThueBao.muccuoccu,
                    moment(this.listTT_Phieu_YC.ngayyeucau).format("DD/MM/YYYY"),
                    this.params.p_pholdid,
                    this.params.p_phuongldid,
                    this.list_TTThueBao.tocdocu,
                    this.list_TTThueBao.trangbimoi,
                    0 //mức cước TB mới 
                  );
                //mức cước TB cũ
              this.loadMucCuocTB_V2(
                    this.params.p_pholdid,
                    this.list_TTThueBao.doituongcu,
                    this.params.p_khuldid,
                    this.listTT_Phieu_YC.loaihinh,
                    this.list_TTThueBao.muccuoccu,
                    moment(this.listTT_Phieu_YC.ngayyeucau).format("DD/MM/YYYY"),
                    this.params.p_pholdid,
                    this.params.p_phuongldid,
                    this.list_TTThueBao.tocdocu,
                    this.list_TTThueBao.trangbimoi,
                    this.list_TTThueBao.muccuoctbcu
              );  
              //load tốc độ mới
              this.loadTocDo(ds[0].loaitb_id,  0);
              //load tốc độ cũ
              this.loadTocDo(ds[0].loaitb_id, this.list_TTThueBao.tocdocu);
              //Kiểu yêu cầu
              this.loadKieuYeuCau(1, response.data.data.returnd_danhba[0].loaitb_id);
              //Hiếu bổ sung lấy thông tin lên chktucthi 25/08/2010
              if(ds[0].loaitb_id == 23 ||ds[0].loaitb_id  ==74 || ds[0].loaitb_id == 58 ){
                let ngay_yc = moment(this.listTT_Phieu_YC.ngayyeucau).format("DD");
                if(ngay_yc <= this.params.ngaytucthi){
                  this.isChkTucThi = true;
                  if(this.value_tucthi==1){
                    this.params.chk_tucthi =true;
                  }else{
                    this.isChkTucThi = true;
                    this.params.chk_tucthi =false;
                  }
                }else{
                  this.isChkTucThi = false;
                  this.params.chk_tucthi =true;
                }
              }
              // Khối lệnh lấy trong danh bạ con
              //nhapt sửa : bổ sung lấy tốc độ cũ cho cả vnpt his
              if (
                response.data.error_code === "BSS-00000000" &&
                response.data.data !== undefined &&
                response.data.data.returnd_danhba_con.length > 0
              ) {
                if(response.data.data.returnd_danhba_con[0].tocdo_id.length>0){
                  this.list_TTThueBao.tocdocu = response.data.data.returnd_danhba_con[0].tocdo_id;
                }
                this.list_TTThueBao.trangbicu = response.data.data.returnd_danhba_con[0].trangbi_id;
                this.isThongTinTC = true;
                this.list_TTKhachHang.thongtintc ="";
                this.list_TTThueBao.loaitbi = response.data.data.returnd_danhba_con[0].thietbidc_id;
                this.list_TTThueBao.thangtra = response.data.data.returnd_danhba_con[0].thang_id;
              }else{
                this.isThongTinTC = false;
                this.list_TTKhachHang.thongtintc ="";
              }
            this.HT_DS_KhuyenMai_Combobox();
            // this.$toast.error("DBa")

            // "Thực hiện thông tin mở rộng cho dịch vụ CNTT"
            if (this.listTT_Phieu_YC.dichvuvt == DichVuVienThong.DICHVU_CNTT || 
                this.listTT_Phieu_YC.dichvuvt == DichVuVienThong.ANTOAN_BAOMAT_TT || 
                this.listTT_Phieu_YC.dichvuvt == DichVuVienThong.TRUNGTAM_DULIEU || 
                this.listTT_Phieu_YC.dichvuvt == DichVuVienThong.HOINGHI_TRUYENHINH
            )
            {
                // ds = tcdanhba.LAY_DS_DB_CNTT_THEO_TBID(thuebao_id);
                this.Init_Properties(this.listTT_Phieu_YC.loaihinh, "HDTB_CNTT", "00000");
            }
          }else{
            this.SetButton(0);
            this.$alert("Không tìm thấy thông tin thuê bao "+this.list_TTThueBao.mathuebao+" đối với dịch vụ "+this.listTT_Phieu_YC.dichvuvt_options[0].text, "Thông báo", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "info",
            });
          }
          this.loading(false);
        }); 
    },
    async Init_Properties(vloaitb_id, table_name, opt_list) {
      await API.lay_danhsach_thuoctinh_v2(this.axios, {
        loaitb_id: vloaitb_id,
        ten_bang: table_name,
        opt_list: opt_list,
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          var ds = response.data.data;
          this.ItemList = [ds.length];
          if (this.ItemList.length <= 0) return;
          for (var i = 0; i < ds.length; i++) {
            var row = ds[i];
            if (!this.ItemList[i]) this.ItemList[i] = {};
            this.ItemList[i].sFieldName = row["field_name"];
            this.ItemList[i].sCaption = row["ten_hienthi"];
            this.ItemList[i].iAtt = row["att"];
            this.ItemList[i].iDataType = row["data_type"];
            this.ItemList[i].sMaxValue = row["max_value"];
            this.ItemList[i].sMinvalue = row["min_value"];
            this.ItemList[i].sValue = row["field_value"];
            this.ItemList[i].nFieldLength = row["field_length"]
              ? 0
              : row["field_length"];
            this.ItemList[i].bIsNotNull = row["field_nullable"]
              ? false
              : row["field_nullable"] == "0"
              ? true
              : false;
            this.ItemList[i].bIsUnique = row["field_unique"]
              ? false
              : row["field_unique"] == "0"
              ? false
              : true;
            this.ItemList[i].sLookUpTableName = row["parent_table"];
            this.ItemList[i].sLookUpKeyField = row["parent_keyfield"];
            this.ItemList[i].sLookUpValueField = row["parent_descfield"];
            this.ItemList[i].strDepandField = row["depend_field"];
            this.ItemList[i].StrSql = row["lookup_sql"];
          }
        }
      });
      // console.log("lay_danhsach_thuoctinh_v2-1364: " + JSON.stringify(this.ItemList));
    },
    //UR2.1.032_018_001 
    async HienThiThongTinHopDongKH(ctvid, dvvt, hdkhid, nvgt){
      // this.loading = true;
      await API.getthaydoi_tocdointernet_httthdkh(this.axios, {"ctv_id": ctvid,"dichvuvt_id": dvvt,"hdkh_id": hdkhid,
             "nhanviengt_id": nvgt }).then((response) => {
          if(response.data.error_code === "BSS-00000000" &&
              response.data.data.returnd_thongtin_khac !== undefined &&
              response.data.data.returnd_thongtin_khac.length > 0) {
              this.filterbox.ctv.value = response.data.data.returnd_thongtin_khac[0].ctv; //tên ctv
              this.filterbox.nguoigt.value = response.data.data.returnd_thongtin_khac[0].ten_nguoigt;
          }
          if(response.data.error_code === "BSS-00000000" &&
              response.data.data.returnd_hdtb_adsl !== undefined &&
              response.data.data.returnd_hdtb_adsl.length > 0) {
              this.list_TTThueBao.congnghe = response.data.data.returnd_hdtb_adsl[0].congnghe_id;
              this.list_TTThueBao.tocdothuc = response.data.data.returnd_hdtb_adsl[0].tocdothuc_id;
          }

      }).catch((error) => {
          console.log(error);
        })
        .finally(() => {this.loading = false});
    },
    txtMaTB_KeyPress(){
      this.loading(true)
      //LAY_DS_HOPDONG_THEO_MA_TB
      API.getDSHopDongTheoMaTB_v2(this.axios, 
      { 
        // "dichvuvt_id": this.listTT_Phieu_YC.dichvuvt, 
        // "ma_tb": this.list_TTThueBao.mathuebao
        "maTb": this.list_TTThueBao.mathuebao,
        "loaiHdId": LoaiHopDong.THAY_DOI_TOCDO_ADSL,
        "ttHdId": TrangThaiHD.THANH_TOAN,
        "donViDlId": this.tt_nd ? this.tt_nd.donvi_id : 0,
        "dichVuVtId": this.listTT_Phieu_YC.dichvuvt,
        "loaiTbId": 0
      }).then((response) => {
        this.loading(false)
        if(response.data.data && response.data.error_code === "BSS-00000000" && response.data.error == "200") {
            this.HienThiTTHopDongKH(response.data.data);
        }else{
          this.HienThiTT_DanhBa(this.list_TTThueBao.mathuebao);
        }
      }).catch(error => {
        this.loading(false);
        console.log('error: ', error);
      });
    },
    loadLoaiTbi: function () {
      this.list_TTThueBao.loaitbi_options = [];
      this.list_TTThueBao.loaitbi= null;
      API.getLoaiTBi(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.thietbidc_id, text: item.ten_tbi });
            });
            this.list_TTThueBao.loaitbi_options = items;
            this.list_TTThueBao.loaitbi = items[0].id;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadTocDo: function (loaitbid, tocdocu) {
      //reset combobox
      this.list_TTThueBao.tocdomoi_options = [];
      this.list_TTThueBao.tocdomoi = null;
      this.list_TTThueBao.tocdothucmoi_options = [];
      this.list_TTThueBao.tocdothucmoi = null;
      this.list_TTThueBao.tocdocu_options =[];
      let apiParams = {
        loaitb_id: loaitbid,
      };
      API.getTocDo(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.tocdo_id, text: item.thuonghieu , iptinh: item.iptinh});
            });
            this.list_TTThueBao.tocdomoi_options = items;
            this.list_TTThueBao.tocdothucmoi_options = items;
            this.list_TTThueBao.tocdocu_options = items;
            // this.list_TTThueBao.tocdomoi = items[0].id;
            // this.list_TTThueBao.tocdothucmoi = items[0].id;
            this.params.isIPtinh = items[0].iptinh;
            
            // if(tocdocu !=0){
            //   this.list_TTThueBao.tocdocu = tocdocu;
            // };
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadMucCuocMoi: function () {
      this.list_TTThueBao.muccuocmoi_options = [];
      this.list_TTThueBao.muccuocmoi = null;
      let apiParams = {
        list_of_cols: "muccuoc_id,tenmuc",
        order: "muccuoc_id",
        schema: "css",
        table_name: "muccuoc",
        where: "",
      };
      API.getMucCuoc(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.muccuoc_id, text: item.tenmuc });
            });
            this.list_TTThueBao.muccuocmoi_options = items;
            this.list_TTThueBao.muccuocmoi = items[0].id;
            // lấy datasource
            this.list_TTThueBao.muccuoccu_options = items;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadCongNgheMoi: function () {
     let items = [
          { id: 2, text: "AON" },
          { id: 3, text: "GPON" }
        ];
      this.list_TTThueBao.congnghemoi_options = items;
      this.list_TTThueBao.congnghemoi = items[0].id;
    },
    loadDoiTuongMoi: function () {
      this.list_TTThueBao.doituongmoi_options = [];
      this.list_TTThueBao.doituongmoi = null;
      API.getDoiTuong(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.doituong_id, text: item.ten_dt });
            });
            this.list_TTThueBao.doituongmoi_options = items;
            this.list_TTThueBao.doituongmoi = items[0].id;
            //lấy datasource
            this.list_TTThueBao.doituongcu_options = items;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadMucCuocTB_V2: function (
      apldid,doituongid,khuldid,loaitbid,muccuocid,ngay,pholdid ,
      phuongid,tocdoid,trangbiid, mucuoctbcu) {
      this.list_TTThueBao.muccuoctbmoi_options = [];
      this.list_TTThueBao.muccuoctbmoi = null;
      this.list_TTThueBao.muccuoctbcu_options=null;
      let apiParams = {
        apld_id: apldid,
        doituong_id: doituongid,
        khuld_id: khuldid,
        loaitb_id: loaitbid,
        muccuoc_id: muccuocid ==null ? 1 : muccuocid,
        ngay: ngay,
        phold_id: pholdid,
        phuongld_id: phuongid,
        tocdo_id: tocdoid ? tocdoid : 0,
        trangbi_id: trangbiid,
       /*  apld_id: 0, doituong_id: 1, khuld_id: 0, loaitb_id: 58, muccuoc_id: 1, 
        ngay:moment(this.listTT_Phieu_YC.ngayyeucau).format("DD/MM/YYYY") , phold_id: 1195, 
        phuongld_id: 136, tocdo_id: 13486, trangbi_id: 0  */
      };
      // console.log('xxxxxx', apiParams)
      API.getMucCuocTB(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.muccuoctb_id, text: item.tenmuccuoc });
            });
            this.list_TTThueBao.muccuoctbmoi_options = items;
            // this.list_TTThueBao.muccuoctbmoi = items[0].id;
            this.list_TTThueBao.muccuoctbcu_options = items;
            if(mucuoctbcu != 0){
              this.list_TTThueBao.muccuoctbcu = mucuoctbcu;
            }
            console.log(response.data.data)
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadLoaiIP: function () {
      this.list_TTThueBao.loaiip_options = [];
      this.list_TTThueBao.loaiip = null;
      let apiParams = {
        schema: "css",
        list_of_cols: "loaiip_id,loai_ip",
        order: "loaiip_id",
        table_name: "loai_ip",
        where: "",
      };
      API.getLoaiIP(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.loaiip_id, text: item.loai_ip});
            });
            this.list_TTThueBao.loaiip_options = items;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadThangTra: function () {
      this.list_TTThueBao.thangtra_options = [];
      this.list_TTThueBao.thangtra = null;
      let apiParams = {
        schema: "css",
        list_of_cols: "thang_id,tenthang",
        order: "thang_id",
        table_name: "thangtra",
        where: "",
      };
      API.getThangTra(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.thang_id, text: item.tenthang });
            });
            this.list_TTThueBao.thangtra_options = items;
            this.list_TTThueBao.thangtra = items[0].id;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadTrangBiMoi: function () {
      this.list_TTThueBao.trangbimoi_options = [];
      this.list_TTThueBao.trangbimoi = null;

      API.getTrangBi(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.trangbi_id, text: item.tentrangbi });
            });
            console.log('Trang bị :', items);
            this.list_TTThueBao.trangbimoi_options = items;
            this.list_TTThueBao.trangbimoi = items[0].id;
            //lấy datasource trang bị cũ
            this.list_TTThueBao.trangbicu_options = items;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    //#endregion
    onDichVuChange: function (agrs) {
      this.loadLoaiHinh(agrs.id, 0);
    },
    onLoaiHinhChange: function (agrs) {
      this.loadKieuYeuCau( 1, agrs.id);
      this.loadTocDo(agrs.id, 0);
      this.params.isIPtinh = 0;
      this.list_TTThueBao.loaiip = null;
    },
    onKieuYCChange(){
      if(this.listTT_Phieu_YC.kieuyc == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON || this.listTT_Phieu_YC.kieuyc == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON
          || this.listTT_Phieu_YC.kieuyc == KieuLapDat.THAYDOI_CONGNGHE_AON_GPON || this.listTT_Phieu_YC.kieuyc == KieuLapDat.THAYDOI_CONGNGHE_GPON_AON){
        this.isCongNgheMoi = true
      }
    },
    onTocDoMoiChange: function (agrs) {
      this.loadMucCuocTB_V2(
            this.params.p_pholdid,
            this.list_TTThueBao.doituongcu,
            this.params.p_khuldid,
            this.listTT_Phieu_YC.loaihinh,
            this.list_TTThueBao.muccuoccu,
            moment(this.listTT_Phieu_YC.ngayyeucau).format("DD/MM/YYYY"),
            this.params.p_pholdid,
            this.params.p_phuongldid,
            agrs.id, //tốc độ
            this.list_TTThueBao.trangbimoi,
            0
        );
      this.params.isIPtinh = agrs.iptinh;
      this.list_TTThueBao.loaiip = null;
      this.list_TTThueBao.tocdothucmoi = agrs.id;
      //this.$toast.error(agrs.iptinh);
    },
    onDoiTuongMoiChange: function (agrs) {
      this.loadMucCuocTB_V2(
            this.params.p_pholdid,
            agrs.id, // đối tượng
            this.params.p_khuldid,
            this.listTT_Phieu_YC.loaihinh,
            this.list_TTThueBao.muccuoccu,
            moment(new Date()).format("DD/MM/YYYY"),
            this.params.p_pholdid,
            this.params.p_phuongldid,
            this.list_TTThueBao.tocdocu,
            this.list_TTThueBao.trangbimoi,
            0
        );
    },
    onMucCuocMoiChange: function(agrs){
       this.loadMucCuocTB_V2(
            this.params.p_pholdid,
            this.list_TTThueBao.doituongcu,
            this.params.p_khuldid,
            this.listTT_Phieu_YC.loaihinh,
            agrs.id, //mức cước
            moment(this.listTT_Phieu_YC.ngayyeucau).format("DD/MM/YYYY"),
            this.params.p_pholdid,
            this.params.p_phuongldid,
            this.list_TTThueBao.tocdocu,
            this.list_TTThueBao.trangbimoi,
            0
        );
    },
    onTrangBiMoiChange: function(agrs){
      this.loadMucCuocTB_V2(
            this.params.p_pholdid,
            this.list_TTThueBao.doituongcu,
            this.params.p_khuldid,
            this.listTT_Phieu_YC.loaihinh,
            this.list_TTThueBao.muccuoccu,
            moment(this.listTT_Phieu_YC.ngayyeucau).format("DD/MM/YYYY"),
            this.params.p_pholdid,
            this.params.p_phuongldid,
            this.list_TTThueBao.tocdocu,
            agrs.id, // trang bị
            0
        );
    },
    //#endregion
    //#region Danh sách thuê bao
    async LAY_DS_HDTB_THAYDOI_TOCDO_CNTT(hdkhid) {
      try {
        this.loading = true;
        let apiParams = {
          hdkh_id: hdkhid,
        };
        const response = await API.getDSHDTBCNTT(this.axios, apiParams);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.ds_Thuebao_table =  response.data.data;
          this.params.tongtienhd = response.data.data[0].tongtien;
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.loading = false;
      }
    },
    async LAY_DS_HDTB_THAYDOI_TOCDO(hdkhid) {
      try {
        this.loading = true;
        let apiParams = {
          hdkh_id: hdkhid,
          dichvuvt_id: this.listTT_Phieu_YC.dichvuvt
        };
        const response = await API.getDSHDTB(this.axios, apiParams);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
            this.ds_Thuebao_table = response.data.data;
            this.params.tongtienhd = response.data.data[0].tongtien;
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.loading = false;
      }
    },
    async HT_DS_KhuyenMai_Combobox() {
      var pkhuyenmai = {
        "vapld_id":  this.params.p_apldid? this.params.p_apldid:0,
        "vdoituong_id": this.list_TTThueBao.doituongcu,
        "vhdtb_id": this.list_TTThueBao.hopdongtbid,
        "vkhoanmuctt_id":1,
        "vkhuld_id": this.params.p_khuldid?this.params.p_khuldid:0,
        "vkieuld_id": this.listTT_Phieu_YC.kieuyc,
        "vloaikm": 0, //khuyến mại
        "vloaitb_id": this.listTT_Phieu_YC.loaihinh,
        "vmuccuoc_id":this.list_TTThueBao.muccuocmoi?this.list_TTThueBao.muccuocmoi:0,
        "vphold_id": this.params.p_pholdid?this.params.p_pholdid:0,
        "vphuongld_id": this.params.p_phuongldid?this.params.p_phuongldid:0,
        "vtocdo_id": this.list_TTThueBao.tocdomoi?this.list_TTThueBao.tocdomoi:0,
        "vtrangbi_id": 0 //this.listbox.trangbi.valuecd, //TODO xac dinh loai trang bi cua CD hay khac? 

        // "vapld_id": 0,
        // "vdoituong_id": 1,
        // "vhdtb_id": 1964785,
        // "vkhoanmuctt_id": 0,
        // "vkhuld_id": 0,       
        // "vkieuld_id": 1,
        // "vloaikm": 0,
        // "vloaitb_id": 1,
        // "vmuccuoc_id": 0,
        // "vphold_id":0,
        // "vphuongld_id": 0,
        // "vtocdo_id": 0,
        // "vtrangbi_id": 3
      };
      var ptratruoc={
        "vapld_id":  this.params.p_apldid? this.params.p_apldid:0,
        "vdoituong_id": this.list_TTThueBao.doituongcu,
        "vhdtb_id": this.list_TTThueBao.hopdongtbid,
        "vkhoanmuctt_id":1,
        "vkhuld_id": this.params.p_khuldid?this.params.p_khuldid:0,
        "vkieuld_id": this.listTT_Phieu_YC.kieuyc,
        "vloaikm": 1, //tra truoc
        "vloaitb_id": this.listTT_Phieu_YC.loaihinh,
        "vmuccuoc_id":this.list_TTThueBao.muccuocmoi?this.list_TTThueBao.muccuocmoi:0,
        "vphold_id": this.params.p_pholdid?this.params.p_pholdid:0,
        "vphuongld_id": this.params.p_phuongldid?this.params.p_phuongldid:0,
        "vtocdo_id": this.list_TTThueBao.tocdomoi?this.list_TTThueBao.tocdomoi:0,
        "vtrangbi_id": 0 // this.listbox.trangbi.valuecd, //TODO xac dinh loai trang bi cua CD hay khac? 

        // "vapld_id": 0,
        // "vdoituong_id": 1,
        // "vhdtb_id": 1964785,
        // "vkhoanmuctt_id": 0,
        // "vkhuld_id": 0,       
        // "vkieuld_id": 1,
        // "vloaikm": 1, //tra truoc
        // "vloaitb_id": 1,
        // "vmuccuoc_id": 0,
        // "vphold_id":0,
        // "vphuongld_id": 0,
        // "vtocdo_id": 0,
        // "vtrangbi_id": 3
      }
      await API.post_lay_khuyenmai_hdtb_v2(this.axios, pkhuyenmai).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.list_TTThueBao.khuyenmai_options = response.data.data.map((x) => ({
              id: x.khuyenmai_id,
              text: x.ten_km
            }));
            this.list_TTThueBao.khuyenmai = response.data.data[0]['khuyenmai_id']
            this.isChkKM = true;
          }else{ 
            this.isChkKM =false; 
          }
        }
      ).catch(error => {
        console.log(error);
      });
      await API.post_lay_khuyenmai_hdtb_v2(this.axios, ptratruoc).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.list_TTThueBao.tratruoc_options = response.data.data.map((x) => ({
              id: x.khuyenmai_id,
              text: x.ten_km
            }));
            this.list_TTThueBao.tratruoc = response.data.data[0]['khuyenmai_id']
            this.isChkTraTruoc = true;
          }else{ 
            this.isChkTraTruoc=false;
          }
        }
      ).catch(error => {
        console.log(error);
      });
    },
    HienThi_TTTC_Visa(){
      var accountServiceModel=[];
      var attribute = [];
      var dtService = [];
      var result=[];
      this.loading = true
      API.lookupRASAccountJson(this.axios, {"accountName": "vin14311"}).then((response) => { 
          if(response.data.errorCode === 0 &&
              response.data.data.lookupRASAccountReturn !== undefined &&
              response.data.data.lookupRASAccountReturn.length > 0)
          {
            accountServiceModel = response.data.data.lookupRASAccountReturn[2];
            attribute = accountServiceModel.attributes;
          }
        }); 
      API.dtService(this.axios, {
        p_type: 1,
        p_param: ""
        // "list_of_cols": "*",
        // "order": null,
        // "schema": "css",
        // "table_name": "vdc_list_attr", 
        // "where": null 
        }
      ).then((response) => { 
            if (response.data.error_code === "BSS-00000000" && response.data.data !== undefined && response.data.data.length > 0)
            {
              dtService = response.data.data;
              result = attribute.filter( x1=> dtService.some(x2 => x1.serviceId === x2.key));
              var value = result.map(function(tag) {
                return tag['value'];
              });
              this.list_TTKhachHang.txtTTTCVisa = value;
              console.log(value);
            }
        }).catch((error) => {
          console.log(error);
        })
        .finally(() => {this.loading = false});  
    },
    checkTTTCVisa: function () {
      // this.loading(true);
      if(this.listTT_Phieu_YC.loaihinh == 11 || this.listTT_Phieu_YC.loaihinh == 58){
        if (this.params.chk_tttcvisa === false) {
          //checked
          this.HienThi_TTTC_Visa();
        } else {
          //uncheck
          this.list_TTKhachHang.txtTTTCVisa = "";
          // this.loading(false)
        }
      }
    }, 
    //row change Datagrid lvwThueBao_SelectedIndexChanged
    onDSTBChange(data){
      try {
        // this.loading(true);
        if(data && Object.keys(data).length > 0) {
          this.list_TTThueBao.hopdongtbid = data.hdtb_id;
          // this.list_TTKhachHang.hopdongkhid = data.hdkh_id;
          this.list_TTThueBao.mathuebao = data.ma_tb;
          this.list_TTThueBao.tenthuebao= data.ten_tb;
          this.list_TTThueBao.diachild = data.diachi_ld;
          this.list_TTThueBao.diachitb = data.diachi_tb;
          // debugger
          API.getDSHDTBTheoHDTBID(this.axios, {in_hdtb_id: data.hdtb_id}).then(response => {
            debugger
            if(response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined &&
                    response.data.data.length > 0) {
                      console.log(response.data.data);
                      this.HienThiTTHopDongTB(response.data.data);
              }
          });
        }
        // this.loading(false);
      } catch (error) {
        console.log(error);
        // this.loading(false);
      }
      
    },
    //#endregion
     //check tức thì
    checkTucThi: function () {
      if (this.params.chk_tucthi === false) {
        //checked
        this.params.value_tucthi = 1;
      } else {
        //uncheck
        this.value = null;
        this.params.value_tucthi = 0;
      }
    },
    TaoDuLieu_HDKH(){},
    //#region btn Cập nhật hợp đồng
    async ThemMoiHopDong(){
      this.loading = true
      let db_adsl=[];
      let quytrinh = 0;
      let tucthi = 0;
      if(this.listTT_Phieu_YC.loaihinh == 61 || this.listTT_Phieu_YC.loaihinh==65){
        tucthi = 2;
      }
      if(this.params.chk_tucthi== false){
        tucthi = 0;
      }
      if(this.params.chk_tucthi == true && this.listTT_Phieu_YC.kieuyc ==KieuLapDat.THAYDOI_MUCCUOC_ADSL){ 
        tucthi = 1;
      }
      if(this.params.chk_tucthi == true && this.listTT_Phieu_YC.kieuyc ==KieuLapDat.THAYDOI_TOCDO_ADSL){ 
        tucthi = 2
      }
      if(this.params.chk_tucthi == true && this.listTT_Phieu_YC.kieuyc ==KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL){ 
        tucthi = 3
      } 
      API.GetKey(this.axios, {"keyname": "HD_KHACHHANG"}).then((response) => { 
        if(response.data.error_code === "BSS-00000000") {
          this.list_TTKhachHang.hopdongkhid =response.data.data;
        }
      });
      API.GetKey(this.axios, {"keyname": "HD_THUEBAO"}).then((response) => { 
        if(response.data.error_code === "BSS-00000000") {
          this.list_TTThueBao.hopdongtbid  =response.data.data;
        }
      });
      await API.LAY_DS_DB_ADSL_THEO_TBID(this.axios, this.list_TTThueBao.return_danhba[0].thuebao_id).then((response) => { 
        if(response.data.error_code === "BSS-00000000") {
          db_adsl = response.data.data;
        }
      });
      await API.LayHuongGiao_TiepNhan(this.axios, {"kieuLdId": this.listTT_Phieu_YC.kieuyc,"loaiHdId": 8,
          "loaiTbId": this.list_TTThueBao.loaihinh,"mucCuocId":  this.list_TTThueBao.muccuocmoi,"tocDoId": this.list_TTThueBao.tocdomoi}).then((response) => { 
          if(response.data.error_code === "BSS-00000000") {
            quytrinh = 0 //response.data.data[0].quytrinh_id;
          }
      });
      if(this.ts_sinhma_gd_theo_donvi == false){
       await API.Lay_Ma_GD_V2(this.axios, {"donvi_id": this.list_TTThueBao.return_danhba[0].donvi_id, "loaihd_id": 8 }).then((response) => {
          if(response.data.error_code === "BSS-00000000") {
            this.listTT_Phieu_YC.magiaodich =response.data.data;
          }
        });
      }else{
        await API.Lay_Ma_GD(this.axios, {"loaihd_id": 8 }).then((response) => { 
          if(response.data.error_code === "BSS-00000000") {
            this.listTT_Phieu_YC.magiaodich =response.data.data;
          }
        });
      }
      let dsHDKH=[{
          HDKH_ID: this.list_TTKhachHang.hopdongkhid,
          MA_GD: this.listTT_Phieu_YC.magiaodich, 
          MA_HD: this.listTT_Phieu_YC.maHDPL, 
          MA_KH: this.list_TTKhachHang.makhachhang, 
          KHACHHANG_ID: this.list_TTKhachHang.khachhangid, 
          TEN_KH: this.list_TTKhachHang.tenkhachhang, 
          DIACHI_KH: this.list_TTKhachHang.diachikhachhang, 
          NGUOI_DD: "",
          CHUCDANH_DD:  this.list_TTThueBao.return_danhba[0].chucdanh,
          SO_DT: this.list_TTThueBao.return_danhba[0].so_dt,
          SO_FAX:  this.list_TTThueBao.return_danhba[0].so_fax,
          EMAIL: this.list_TTThueBao.return_danhba[0].email,
          WEBSITE: this.list_TTThueBao.return_danhba[0].website,
          NGAY_SN: this.list_TTKhachHang.ngaysinh,
          NGAYLAP_HD: moment(this.listTT_Phieu_YC.ngayLapHD).format("DD/MM/YYYY HH:mm:ss"),  //"23/06/2020 09:31:04",
          SO_GT: this.list_TTThueBao.return_danhba[0].so_gt,
          NGAYCAP: moment(this.list_TTThueBao.return_danhba[0].ngaycap).format("DD/MM/YYYY HH:mm:ss"),
          NOICAP: this.list_TTThueBao.return_danhba[0].noicap,
          GHICHU: this.list_TTKhachHang.txtGhiChu,
          DONVI_ID: this.list_TTThueBao.return_danhba[0].donvi_id,
          LOAIGT_ID: this.list_TTThueBao.return_danhba[0].loaigt_id,
          NHANVIEN_ID: this.$root.token.getNhanVienID(), //452,
          KHLON_ID: this.list_TTThueBao.return_danhba[0].khlon_id, //1,
          LOAIHD_ID: 8,
          BOSUNGTB_ID:  "",
          KH_KT3: this.list_TTThueBao.return_danhba[0].kh_kt3,
          MST_KH: "",
          HOKHAU: this.list_TTThueBao.return_danhba[0].hokhau,
          LOAIKH_ID: this.list_TTThueBao.return_danhba[0].loaikh_id,
          NOICAP_HK: this.list_TTThueBao.return_danhba[0].noicap_hk,
          NGAY_HK:moment(this.list_TTThueBao.return_danhba[0].ngay_hk).format("DD/MM/YYYY HH:mm:ss"),
          GIOITINH:this.list_TTThueBao.return_danhba[0].gioitinh,
          DANTOC_ID:this.list_TTThueBao.return_danhba[0].dantoc_id,
          QUOCTICH_ID:this.list_TTThueBao.return_danhba[0].quoctich_id,
          NGANHNGHE_ID: this.list_TTThueBao.return_danhba[0].nganhnghe_id,
          DIACHI_NDD:this.list_TTThueBao.return_danhba[0].diachi_ndd,
          NGAYHETHAN_GT:moment(this.list_TTThueBao.return_danhba[0].ngayhethan_gt).format("DD/MM/YYYY HH:mm:ss"),
          MST:this.list_TTThueBao.return_danhba[0].mst,
          STK:this.list_TTThueBao.return_danhba[0].stk,
          NGANHANG_ID:this.list_TTThueBao.return_danhba[0].nganhang_id,
          NGAY_YC: moment(this.listTT_Phieu_YC.ngayyeucau).format("DD/MM/YYYY HH:mm:ss"),// "23/06/2020 09:31:04",
          DACTRUNG:this.list_TTThueBao.return_danhba[0].dactrung,
          CTV_ID:this.filterbox.ctv.ctv_id,
          TNC1_ID:this.list_TTThueBao.return_danhba[0].tnc1_id,
          TNC2_ID:this.list_TTThueBao.return_danhba[0].tnc2_id,
          TNC3_ID:this.list_TTThueBao.return_danhba[0].tnc3_id,
          MAY_CN:this.$root.token.getMaNhanVien(),
          NGAY_CN: moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"),
          NGUOI_CN:this.$root.token.getUserName(),
          IP_CN: "10.35.65.5",
          SO_PL: 0,
          HDKH_CHA_ID: 0,
          KIEUHD_ID: 0,
          KH_TIEMNANG: 0,
          YEUCAU_ID: 0,
          HTTTHI_ID: 0,
          PHANLOAIKH_ID: this.list_TTThueBao.return_danhba[0].phanloaikh_id, //15
          HDKH_ID_HNI: 0,
          LOAINC_ID: 0,
          MUCDO_ID: 0,
          NGUOILAP_HD: this.$root.token.getMaNhanVien(),
          TBTL_ID: 0,
          NHANVIENGT_ID: this.filterbox.nguoigt.nguoigt_id,
          KENHTN_ID: 0,
          MA_DUAN: "",
          KIEUTN_ID: 0,
          MA_KH_WEB: "",
          PHIEUCSKH_ID: 0,
          DONHANG_ID: 0,
          TNC4_ID: this.list_TTThueBao.return_danhba[0].tnc4_id,
        }]
      let dsHDTB=[{
        HDTB_ID:this.list_TTThueBao.hopdongtbid,
        THUEBAO_ID: this.list_TTThueBao.return_danhba[0].thuebao_id,
        MA_TB: this.list_TTThueBao.mathuebao, //  "canh479.cloudcamera.06",
        TEN_TB: this.list_TTThueBao.tenthuebao, // "Đỗ Danh Cảnh",
        DIACHI_TB: this.list_TTThueBao.diachitb, // "Thôn 1, Xã Kiến Quốc, Huyện Kiến Thuỵ, Hải Phòng",
        HDTB_CHA_ID: 0,
        HDKH_ID: this.list_TTKhachHang.hopdongkhid,
        HDTT_ID: 0,
        KIEULD_ID: this.listTT_Phieu_YC.kieuyc,
        TTHD_ID:1,
        LOAITB_ID: this.listTT_Phieu_YC.loaihinh,
        DOITUONG_ID: this.list_TTThueBao.doituongmoi, //1,
        DICHVUVT_ID:this.list_TTThueBao.dichvuvt,
        DONVI_ID:this.list_TTThueBao.return_danhba[0].donvi_id,
        NGAY_HT: moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"), 
        NGAY_TT: moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"),
        SOLUONG:0,
        KETQUAKS:"",
        SYNC_STATUS:0,
        TBHD_PL:"",
        DIACHI_LD: this.list_TTThueBao.diachild, //"Thôn 1, Xã Kiến Quốc, Huyện Kiến Thuỵ, Hải Phòng",
        NGAY_SN: this.list_TTKhachHang.ngaysinh,
        STATUS: 0,
        KIEU_TB:0,
        DONVI_DAU_ID:0,
        DONVI_CUOI_ID:0,
        EMAIL:"",
        NGAY_INS: moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"),
        NGAY_KH: moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"),
        MUCUOCTB_ID: this.list_TTThueBao.muccuoctbmoi,
        MUCUOC_TB: 0,
        THUEBAO_CHA_ID: this.list_TTThueBao.return_danhba[0].thuebao_cha_id,
        GHICHU: this.list_TTThueBao.return_danhba[0].ghichu,
        QUYTRINH_ID: quytrinh,
        MAY_CN: this.$root.token.getMaNhanVien(),
        NGAY_CN: moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"), 
        NGUOI_CN: this.$root.token.getUserName(),
        CHUNHOM:0,
        NHOMTB_ID:0,
        GOI_ID:0,
        IP_CN:"",
        SO_PL:0,
        CHUYENMANG:0,
        BANGKE_ID:0,
        GOIKT_ID:5,
        CHITIEU_TG:72,
        THOIGIAN_CK:0,
        IP_KH:"10.35.65.5",
        LAN_KS:0,
        TG_THUE_DEN:0,
        TG_THUE_TU:0,
        NGUOI_KH:"",
        TIENTRINHTC_ID:0,
        MA_DHVIP:"",
        SOTHANG_CK: this.list_TTThueBao.sothangCK,
        NGAY_KTCK: moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"),
        HDTB_ID_HNI:0,
        NGAY_HH_TD: moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"),
        SYNC_STB:0,
        NHACC_CU_ID:0,
        DOITUONG_CT_ID:0,
        HTCM_ID:0,
        TRANGTHAIYC_ID:0,
        DUAN_ID:0,
        PHIEUCSKHDV_ID:0
        }]
      let dsHDTB_ADSL=  [{
        BRAS_ID: db_adsl[0].bras_id,
        CAP_GOC: db_adsl[0].cap_goc,
        CAP_P: db_adsl[0].cap_p,
        CHUQUANLD_ID:db_adsl[0].chuquanld_id,       
        CHUQUAN_ID:db_adsl[0].chuquan_id,
        CONGNGHE_ID: db_adsl[0].congnghe_id,
        CULY: db_adsl[0].culy,
        CUOC_DOITAC:db_adsl[0].cuoc_doitac,
        CUOC_QL:db_adsl[0].cuoc_ql,
        CUOC_SD: this.list_TTThueBao.cuocsd,
        CUOC_SODEP:db_adsl[0].cuoc_sodep,
        CUOC_TBI: this.list_TTThueBao.cuocthietbi,
        CUOC_TN:db_adsl[0].cuoc_tn,
        DOICAP_GOC: db_adsl[0].doicap_goc,      
        DOICAP_P: db_adsl[0].doicap_p,
        DOITAC_ID: db_adsl[0].doitac_id,
        DSLAM_ID: db_adsl[0].dslam_id,
        EMAIL: db_adsl[0].email,
        FIRMWARE_VERSION: db_adsl[0].firmware_version,
        GHICHU: db_adsl[0].ghichu,
        HANDATCHO:"",
        HANG_SX:  db_adsl[0].hang_sx ,
        HARDWARE_VERSION: db_adsl[0].hardware_version,
        HDTB_ID: this.list_TTThueBao.hopdongtbid,
        HOPCAP_P: db_adsl[0].hopcap_p,
        IP: db_adsl[0].ip,
        IPV4_WAN: db_adsl[0].ipv4_wan,
        IPV6_LAN: db_adsl[0].ipv6_lan,
        IP_MP: db_adsl[0].ip_mp,
        KETCUOI_ID: db_adsl[0].ketcuoi_id,
        KH_CD:0,
        KIEUTRA_ID: db_adsl[0].kieutra_id,
        LIENTU: db_adsl[0].lientu,
        LOAICAP_ID: db_adsl[0].loaicap_id,
        LOAI_CSHT_ID: db_adsl[0].loai_csht_id,
        MADOICAP: db_adsl[0].madoicap,
        MATB_MGWAN:"string",
        MATB_TN: db_adsl[0].matb_tn,
        MA_LT: db_adsl[0].ma_lt,
        MA_VNPT: db_adsl[0].ma_vnpt,
        MUCCUOC_ID: db_adsl[0].muccuoc_id ,
        NGAYTRA: db_adsl[0].ngaytra,
        NGAY_GC: db_adsl[0].ngay_gc,
        PASSWORD: db_adsl[0].password,
        PORT_ID: db_adsl[0].port_id,
        PROFILEDSL_ID: db_adsl[0].profiledsl_id,
        SERIAL_STB:"string",
        SERI_MD: db_adsl[0].seri_md,
        SLID: db_adsl[0].slid,
        SLTV_HTVC: db_adsl[0].sltv_htvc,
        SO_DT: db_adsl[0].so_dt,
        STATUS_IP:"string",
        TG_GIAM: db_adsl[0].tg_giam,
        THANG_ID: this.list_TTThueBao.thangtra,
        THIETBIDC_ID: db_adsl[0].thietbidc_id,
        THOIHAN_ID: db_adsl[0].thoihan_id,
        THONGTIN_TC: this.list_TTKhachHang.thongtintc,
        TINHKHAC: db_adsl[0].tinhkhac,
        TL_GIAMCUOC_QL: db_adsl[0].tl_giamcuoc_ql,
        TOCDOTHUC_ID: db_adsl[0].tocdothuc_id,
        TOCDO_ID: this.list_TTThueBao.tocdomoi,// 8684,
        TRAMTB_ID: db_adsl[0].tramtb_id,
        TRANGBI_ID: this.list_TTThueBao.trangbimoi,
        TUCAP_GOC: db_adsl[0].tucap_goc,
        TUCAP_P: db_adsl[0].tucap_p,
        TUCTHI: tucthi,
        TUYENCAP: db_adsl[0].tuyencap,
        TYLE_GC: db_adsl[0].tyle_gc,
        USERNAME: db_adsl[0].username,
        VCI_VPI_ID: db_adsl[0].vci_vpi_id,
        VMP: db_adsl[0].vmp,
        }]
      let data = {
        ACTION_TYPE: "A",
        BATBUOC_SOTHANG_CK: this.params.batbuoc_sothang_ck, //0,
        CONGNGHE_CU_ID: this.list_TTThueBao.congnghe,
        CONGNGHE_MOI_ID:0,
        CT_TIENHD:[],
        CUOC_SD:this.list_TTThueBao.cuocsd , //0,
        CUOC_TBI: this.list_TTThueBao.cuocthietbi, //0,
        DICHVUVT_ID: this.listTT_Phieu_YC.dichvuvt,
        HD_KHACHHANG: dsHDKH,
        HD_THUEBAO: dsHDTB,
        HDTB_ADSL: dsHDTB_ADSL,
        hdtb_cntt:[],
        HDTB_ID: this.list_TTThueBao.hopdongtbid,
        HDTB_TC:[],
        KHACHHANG_ID: this.list_TTKhachHang.khachhangid, //967706,
        KHUYENMAI_HDTB:[],
        KIEULD_ID: this.listTT_Phieu_YC.kieuyc,
        KIEUTRA_CU_ID:0,
        KIEUTRA_MOI_ID:0,
        KIEUTRAMOI:0,
        LOAITB_ID: this.listTT_Phieu_YC.loaihinh,
        MA_HD: this.listTT_Phieu_YC.maHDPL,
        MA_KH:  this.list_TTKhachHang.makhachhang,
        ma_tb: this.list_TTThueBao.mathuebao, // "canh479",
        MUCCUOC_ID: this.list_TTThueBao.muccuocmoi,
        TEN_KH:  this.list_TTKhachHang.tenkhachhang,
        THONGTIN_TT:[],
        THUEBAO_ID:this.list_TTThueBao.thuebaoid ,//1578094,
        TOCDO_CU_ID: this.list_TTThueBao.tocdocu, //this.list_TTThueBao.tocdocu, // 25859,
        TOCDO_ID: this.list_TTThueBao.tocdomoi, // 8684,
        TOCDOTHUC_CU_ID:0,
        TOCDOTHUC_MOI_ID:0
        };
      const response = await API.btnCapNhat(this.axios, data);
      if (response.data.error_code === "BSS-00000000") {
        if (
          response.data.data !== undefined &&
          response.data.data.length >= 0
        ) {
          if(this.listTT_Phieu_YC.dichvuvt == 13 || this.listTT_Phieu_YC.dichvuvt == 14 || 
              this.listTT_Phieu_YC.dichvuvt == 15){
              this.LAY_DS_HDTB_THAYDOI_TOCDO_CNTT(this.list_TTKhachHang.hopdongkhid);
              //this.LAY_DS_HDTB_THAYDOI_TOCDO_CNTT(2514155);
          }else{
            //this.LAY_DS_HDTB_THAYDOI_TOCDO(1851924)
            this.LAY_DS_HDTB_THAYDOI_TOCDO(this.list_TTKhachHang.hopdongkhid);
          }
          this.SetButton(3);
          this.$toast.success(response.data.data);
        } else {
          this.$toast.error("Có lỗi trong quá trình cập nhật!");
        }
      }
      this.loading = false;
    },
    async CapNhatHopDong() {
      this.loading = true;
      let db_adsl=[];
      let quytrinh = 0;
      let tucthi = 0;
      if(this.listTT_Phieu_YC.loaihinh == 61 || this.listTT_Phieu_YC.loaihinh==65){
        tucthi = 2;
      }
      if(this.params.chk_tucthi== false){
        tucthi = 0;
      }
      if(this.params.chk_tucthi == true && this.listTT_Phieu_YC.kieuyc ==KieuLapDat.THAYDOI_MUCCUOC_ADSL){ 
        tucthi = 1;
      }
      if(this.params.chk_tucthi == true && this.listTT_Phieu_YC.kieuyc ==KieuLapDat.THAYDOI_TOCDO_ADSL){ 
        tucthi = 2
      }
      if(this.params.chk_tucthi == true && this.listTT_Phieu_YC.kieuyc ==KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL){ 
        tucthi = 3
      } 
      await API.LayHuongGiao_TiepNhan(this.axios, {"kieuLdId": this.listTT_Phieu_YC.kieuyc,"loaiHdId": 8,
          "loaiTbId": this.list_TTThueBao.loaihinh,"mucCuocId":  this.list_TTThueBao.muccuocmoi,"tocDoId": this.list_TTThueBao.tocdomoi}).then((response) => { 
          if(response.data.error_code === "BSS-00000000") {
            quytrinh = 0 //response.data.data[0].quytrinh_id;
          }
      });
      await API.LAY_DS_DB_ADSL_THEO_TBID(this.axios, this.list_TTKhachHang.return_hdkh[0].thuebao_id).then((response) => { 
        if(response.data.error_code === "BSS-00000000") {
          db_adsl = response.data.data;
        }
      });
      let dsHDKH=[{
          HDKH_ID: this.list_TTKhachHang.hopdongkhid,
          MA_GD: this.listTT_Phieu_YC.magiaodich, 
          MA_HD: this.listTT_Phieu_YC.maHDPL, 
          MA_KH: this.list_TTKhachHang.makhachhang, 
          KHACHHANG_ID: this.list_TTKhachHang.khachhangid, 
          TEN_KH: this.list_TTKhachHang.tenkhachhang, 
          DIACHI_KH: this.list_TTKhachHang.diachikhachhang, 
          NGUOI_DD: this.list_TTKhachHang.return_hdkh[0].nguoi_dd,
          CHUCDANH_DD:  this.list_TTKhachHang.return_hdkh[0].chucdanh_dd,
          SO_DT: this.list_TTKhachHang.return_hdkh[0].so_dt,
          SO_FAX: this.list_TTKhachHang.return_hdkh[0].so_fax,
          EMAIL: this.list_TTKhachHang.return_hdkh[0].email,
          WEBSITE: this.list_TTKhachHang.return_hdkh[0].website,
          NGAY_SN: this.list_TTKhachHang.ngaysinh,
          NGAYLAP_HD: moment(this.listTT_Phieu_YC.ngayLapHD).format("DD/MM/YYYY HH:mm:ss"),  //"23/06/2020 09:31:04",
          SO_GT: this.list_TTKhachHang.return_hdkh[0].so_gt,
          NGAYCAP: moment(this.list_TTKhachHang.return_hdkh[0].ngaycap).format("DD/MM/YYYY HH:mm:ss"),
          NOICAP: this.list_TTKhachHang.return_hdkh[0].noicap,
          GHICHU: this.list_TTKhachHang.txtGhiChu,
          DONVI_ID: this.list_TTKhachHang.return_hdkh[0].donvi_id,
          LOAIGT_ID: this.list_TTKhachHang.return_hdkh[0].loaigt_id,
          NHANVIEN_ID: this.$root.token.getNhanVienID(), //452,
          KHLON_ID: this.list_TTKhachHang.return_hdkh[0].khlon_id, //1,
          LOAIHD_ID: 8,
          BOSUNGTB_ID: 0,
          KH_KT3: this.list_TTKhachHang.return_hdkh[0].kh_kt3,
          MST_KH: "",
          HOKHAU: this.list_TTKhachHang.return_hdkh[0].hokhau,
          LOAIKH_ID: this.list_TTKhachHang.return_hdkh[0].loaikh_id,
          NOICAP_HK: this.list_TTKhachHang.return_hdkh[0].noicap_hk,
          NGAY_HK:moment(this.list_TTKhachHang.return_hdkh[0].ngay_hk).format("DD/MM/YYYY HH:mm:ss"),
          GIOITINH:this.list_TTKhachHang.return_hdkh[0].gioitinh,
          DANTOC_ID:this.list_TTKhachHang.return_hdkh[0].dantoc_id,
          QUOCTICH_ID:this.list_TTKhachHang.return_hdkh[0].quoctich_id,
          NGANHNGHE_ID: this.list_TTKhachHang.return_hdkh[0].nganhnghe_id,
          DIACHI_NDD:this.list_TTKhachHang.return_hdkh[0].diachi_ndd,
          NGAYHETHAN_GT:moment(this.list_TTKhachHang.return_hdkh[0].ngayhethan_gt).format("DD/MM/YYYY HH:mm:ss"),
          MST:this.list_TTKhachHang.return_hdkh[0].mst,
          STK:this.list_TTKhachHang.return_hdkh[0].stk,
          NGANHANG_ID:this.list_TTKhachHang.return_hdkh[0].nganhang_id,
          NGAY_YC: moment(this.listTT_Phieu_YC.ngayyeucau).format("DD/MM/YYYY HH:mm:ss"),// "23/06/2020 09:31:04",
          DACTRUNG:this.list_TTKhachHang.return_hdkh[0].dactrung,
          CTV_ID:this.filterbox.ctv.ctv_id,
          TNC1_ID:this.list_TTKhachHang.return_hdkh[0].tnc1_id,
          TNC2_ID:this.list_TTKhachHang.return_hdkh[0].tnc2_id,
          TNC3_ID:this.list_TTKhachHang.return_hdkh[0].tnc3_id,
          MAY_CN:this.$root.token.getMaNhanVien(),
          NGAY_CN: moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"),
          NGUOI_CN:this.$root.token.getUserName(),
          IP_CN: "10.35.65.5",
          SO_PL: 0,
          HDKH_CHA_ID: this.list_TTKhachHang.return_hdkh[0].hdkh_cha_id,
          KIEUHD_ID: this.list_TTKhachHang.return_hdkh[0].kieuhd_id,
          KH_TIEMNANG:this.list_TTKhachHang.return_hdkh[0].kh_tiemnang,
          YEUCAU_ID: this.list_TTKhachHang.return_hdkh[0].yeucau_id,
          HTTTHI_ID: this.list_TTKhachHang.return_hdkh[0].httthi_id,
          PHANLOAIKH_ID:this.list_TTKhachHang.return_hdkh[0].phanloaikh_id, //15
          HDKH_ID_HNI: this.list_TTKhachHang.return_hdkh[0].hdkh_id_hni,
          LOAINC_ID: this.list_TTKhachHang.return_hdkh[0].loainc_id,
          MUCDO_ID: this.list_TTKhachHang.return_hdkh[0].mucdo_id,
          NGUOILAP_HD: this.$root.token.getMaNhanVien(),
          TBTL_ID:this.list_TTKhachHang.return_hdkh[0].tbtl_id,
          NHANVIENGT_ID: this.filterbox.nguoigt.nguoigt_id,
          KENHTN_ID:this.list_TTKhachHang.return_hdkh[0].kenhtn_id,
          MA_DUAN: this.list_TTKhachHang.return_hdkh[0].ma_duan,
          KIEUTN_ID: this.list_TTKhachHang.return_hdkh[0].kieutn_id,
          MA_KH_WEB: this.list_TTKhachHang.return_hdkh[0].ma_kh_web,
          PHIEUCSKH_ID: this.list_TTKhachHang.return_hdkh[0].phieucskh_id,
          DONHANG_ID: this.list_TTKhachHang.return_hdkh[0].donhang_id,
          TNC4_ID: this.list_TTKhachHang.return_hdkh[0].tnc4_id,
        }]
      let dsHDTB=[{
        HDTB_ID:this.list_TTThueBao.hopdongtbid,
        THUEBAO_ID: this.list_TTKhachHang.return_hdkh[0].thuebao_id,
        MA_TB: this.list_TTThueBao.mathuebao, //  "canh479.cloudcamera.06",
        TEN_TB: this.list_TTThueBao.tenthuebao, // "Đỗ Danh Cảnh",
        DIACHI_TB: this.list_TTThueBao.diachitb, // "Thôn 1, Xã Kiến Quốc, Huyện Kiến Thuỵ, Hải Phòng",
        HDTB_CHA_ID: this.list_TTKhachHang.return_hdkh[0].hdtb_cha_id,
        HDKH_ID: this.list_TTKhachHang.hopdongkhid,
        HDTT_ID:this.list_TTKhachHang.return_hdkh[0].hdtt_id,
        KIEULD_ID: this.listTT_Phieu_YC.kieuyc,
        TTHD_ID:1,
        LOAITB_ID: this.listTT_Phieu_YC.loaihinh,
        DOITUONG_ID: this.list_TTThueBao.doituongmoi, //1,
        DICHVUVT_ID:this.list_TTThueBao.dichvuvt,
        DONVI_ID:this.list_TTKhachHang.return_hdkh[0].donvi_id,
        NGAY_HT: moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"), 
        NGAY_TT: moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"),
        SOLUONG: 0,
        KETQUAKS: "",
        SYNC_STATUS:0,
        TBHD_PL: 0,
        DIACHI_LD: this.list_TTThueBao.diachild, //"Thôn 1, Xã Kiến Quốc, Huyện Kiến Thuỵ, Hải Phòng",
        NGAY_SN: this.list_TTKhachHang.ngaysinh,
        STATUS: 0,
        KIEU_TB: "",
        DONVI_DAU_ID: 0,
        DONVI_CUOI_ID: 0,
        EMAIL:this.list_TTKhachHang.return_hdkh[0].email,
        NGAY_INS: moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"), 
        NGAY_KH: moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"),
        MUCUOCTB_ID: this.list_TTThueBao.muccuoctbmoi,
        MUCUOC_TB: 0,
        THUEBAO_CHA_ID:this.list_TTKhachHang.return_hdkh[0].thuebao_cha_id,
        GHICHU: this.list_TTKhachHang.return_hdkh[0].ghichu,
        QUYTRINH_ID: quytrinh,
        MAY_CN: this.$root.token.getMaNhanVien(),
        NGAY_CN: moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"), 
        NGUOI_CN: this.$root.token.getUserName(),
        CHUNHOM: 0,
        NHOMTB_ID: 0,
        GOI_ID: 0,
        IP_CN:"10.35.65.5",
        SO_PL:this.list_TTKhachHang.return_hdkh[0].so_pl,
        CHUYENMANG:this.list_TTKhachHang.return_hdkh[0].chuyenmang_db,
        BANGKE_ID: 0,
        GOIKT_ID:5,
        CHITIEU_TG:72,
        THOIGIAN_CK: "",
        IP_KH:"10.35.65.5",
        LAN_KS:0,
        TG_THUE_DEN: 0,
        NGUOI_KH: "",
        TIENTRINHTC_ID: 0,
        MA_DHVIP:  "",
        SOTHANG_CK: this.list_TTThueBao.sothangCK,
        NGAY_KTCK: moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"),
        HDTB_ID_HNI: 0,
        NGAY_HH_TD: moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"),
        SYNC_STB: 0,
        NHACC_CU_ID: 0,
        DOITUONG_CT_ID: 0,
        HTCM_ID: 0,
        TRANGTHAIYC_ID: 0,
        DUAN_ID: 0,
        PHIEUCSKHDV_ID: this.list_TTKhachHang.return_hdkh[0].phieucskh_id
        }]
      let dsHDTB_ADSL=  [{
        BRAS_ID: db_adsl[0].bras_id,
        CAP_GOC: db_adsl[0].cap_goc,
        CAP_P: db_adsl[0].cap_p,
        CHUQUANLD_ID:db_adsl[0].chuquanld_id,       
        CHUQUAN_ID:db_adsl[0].chuquan_id,
        CONGNGHE_ID: db_adsl[0].congnghe_id,
        CULY: db_adsl[0].culy,
        CUOC_DOITAC:db_adsl[0].cuoc_doitac,
        CUOC_QL:db_adsl[0].cuoc_ql,
        CUOC_SD: this.list_TTThueBao.cuocsd,
        CUOC_SODEP:db_adsl[0].cuoc_sodep,
        CUOC_TBI: this.list_TTThueBao.cuocthietbi,
        CUOC_TN:db_adsl[0].cuoc_tn,
        DOICAP_GOC: db_adsl[0].doicap_goc,      
        DOICAP_P: db_adsl[0].doicap_p,
        DOITAC_ID: db_adsl[0].doitac_id,
        DSLAM_ID: db_adsl[0].dslam_id,
        EMAIL: db_adsl[0].email,
        FIRMWARE_VERSION: db_adsl[0].firmware_version,
        GHICHU: db_adsl[0].ghichu,
        HANDATCHO:"string",
        HANG_SX:  db_adsl[0].hang_sx ,
        HARDWARE_VERSION: db_adsl[0].hardware_version,
        HDTB_ID: this.list_TTThueBao.hopdongtbid,
        HOPCAP_P: db_adsl[0].hopcap_p,
        IP: db_adsl[0].ip,
        IPV4_WAN: db_adsl[0].ipv4_wan,
        IPV6_LAN: db_adsl[0].ipv6_lan,
        IP_MP: db_adsl[0].ip_mp,
        KETCUOI_ID: db_adsl[0].ketcuoi_id,
        KH_CD:0,
        KIEUTRA_ID: db_adsl[0].kieutra_id,
        LIENTU: db_adsl[0].lientu,
        LOAICAP_ID: db_adsl[0].loaicap_id,
        LOAI_CSHT_ID: db_adsl[0].loai_csht_id,
        MADOICAP: db_adsl[0].madoicap,
        MATB_MGWAN:"string",
        MATB_TN: db_adsl[0].matb_tn,
        MA_LT: db_adsl[0].ma_lt,
        MA_VNPT: db_adsl[0].ma_vnpt,
        MUCCUOC_ID: db_adsl[0].muccuoc_id ,
        NGAYTRA: db_adsl[0].ngaytra,
        NGAY_GC: db_adsl[0].ngay_gc,
        PASSWORD: db_adsl[0].password,
        PORT_ID: db_adsl[0].port_id,
        PROFILEDSL_ID: db_adsl[0].profiledsl_id,
        SERIAL_STB:"string",
        SERI_MD: db_adsl[0].seri_md,
        SLID: db_adsl[0].slid,
        SLTV_HTVC: db_adsl[0].sltv_htvc,
        SO_DT: db_adsl[0].so_dt,
        STATUS_IP:"string",
        TG_GIAM: db_adsl[0].tg_giam,
        THANG_ID: this.list_TTThueBao.thangtra,
        THIETBIDC_ID: db_adsl[0].thietbidc_id,
        THOIHAN_ID: db_adsl[0].thoihan_id,
        THONGTIN_TC: this.list_TTKhachHang.thongtintc,
        TINHKHAC: db_adsl[0].tinhkhac,
        TL_GIAMCUOC_QL: db_adsl[0].tl_giamcuoc_ql,
        TOCDOTHUC_ID: db_adsl[0].tocdothuc_id,
        TOCDO_ID: this.list_TTThueBao.tocdomoi,// 8684,
        TRAMTB_ID: db_adsl[0].tramtb_id,
        TRANGBI_ID: this.list_TTThueBao.trangbimoi,
        TUCAP_GOC: db_adsl[0].tucap_goc,
        TUCAP_P: db_adsl[0].tucap_p,
        TUCTHI: tucthi,
        TUYENCAP: db_adsl[0].tuyencap,
        TYLE_GC: db_adsl[0].tyle_gc,
        USERNAME: db_adsl[0].username,
        VCI_VPI_ID: db_adsl[0].vci_vpi_id,
        VMP: db_adsl[0].vmp,
        }]
      let data = {
        ACTION_TYPE: "U", //update
        BATBUOC_SOTHANG_CK: this.params.batbuoc_sothang_ck, //0,
        CONGNGHE_CU_ID: this.list_TTThueBao.congnghe,
        CONGNGHE_MOI_ID:0,
        CT_TIENHD:[],
        CUOC_SD:this.list_TTThueBao.cuocsd , //0,
        CUOC_TBI: this.list_TTThueBao.cuocthietbi, //0,
        DICHVUVT_ID: this.listTT_Phieu_YC.dichvuvt,
        HD_KHACHHANG: dsHDKH,
        HD_THUEBAO: dsHDTB,
        HDTB_ADSL: dsHDTB_ADSL,
        hdtb_cntt:[],
        HDTB_ID: this.list_TTThueBao.hopdongtbid,
        HDTB_TC:[],
        KHACHHANG_ID: this.list_TTKhachHang.khachhangid, //967706,
        KHUYENMAI_HDTB:[],
        KIEULD_ID: this.listTT_Phieu_YC.kieuyc,
        KIEUTRA_CU_ID:0,
        KIEUTRA_MOI_ID:0,
        KIEUTRAMOI:0,
        LOAITB_ID: this.listTT_Phieu_YC.loaihinh,
        MA_HD: this.listTT_Phieu_YC.maHDPL,
        MA_KH:  this.list_TTKhachHang.makhachhang,
        MA_TB: this.list_TTThueBao.mathuebao, // "canh479",
        MUCCUOC_ID: this.list_TTThueBao.muccuocmoi,
        TEN_KH:  this.list_TTKhachHang.tenkhachhang,
        THONGTIN_TT:[],
        THUEBAO_ID:this.list_TTThueBao.thuebaoid ,//1578094,
        TOCDO_CU_ID: this.list_TTThueBao.tocdocu, //this.list_TTThueBao.tocdocu, // 25859,
        TOCDO_ID: this.list_TTThueBao.tocdomoi, // 8684,
        TOCDOTHUC_CU_ID:0,
        TOCDOTHUC_MOI_ID:0
        };
        console.log(data)
      const response = await API.btnCapNhat(this.axios, data);
      if (response.data.error_code === "BSS-00000000") {
        if (
          response.data.data !== undefined &&
          response.data.data.length >= 0
        ) {
          if(this.listTT_Phieu_YC.dichvuvt == 13 || this.listTT_Phieu_YC.dichvuvt == 14 || 
              this.listTT_Phieu_YC.dichvuvt == 15){
              this.LAY_DS_HDTB_THAYDOI_TOCDO_CNTT(this.list_TTKhachHang.hopdongkhid);
              //this.LAY_DS_HDTB_THAYDOI_TOCDO_CNTT(2514155);
          }else{
            //this.LAY_DS_HDTB_THAYDOI_TOCDO(1851924)
            this.LAY_DS_HDTB_THAYDOI_TOCDO(this.list_TTKhachHang.hopdongkhid);
          }
          this.SetButton(3);
          this.$toast.success(response.data.data);
        } else {
          this.$toast.error("Có lỗi trong quá trình cập nhật!");
        }
      }
      this.loading = false;
    },
    async KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(matb, magd, dvvt, loaihd){
      try {
        let data = {
          // vphanvung_id: 26,
          vma_tb: matb,
          vma_gd: magd,
          vdichvuvt_id: dvvt,
          vloaihd_id: loaihd
        };
        if(matb){
          const response = await API.kiemTraMaTBHopDongKhac(this.axios, data);
          if (response.data.error_code === "BSS-00000000" && response.data.data.length) {
            let row1 = response.data.data[0];
            let msg = `Số máy/Acc: ${matb} đang được lập bởi hợp đồng (phụ lục) ${row1['ten_loaihd']} có mã giao dịch ${row1['ma_gd']} do User ${row1['nguoi_cn']}
            thuộc đơn vị ${row1['ten_dv']} thực hiện vào ngày ${row1['ngay_yc'] ? row1['ngay_yc'] : ''}`;
            this.$toast.warning(msg);
            return true;
          }else{
            return false;
          }
        }else{
          return false;
        }
      } catch (error) {
        console.log(error);
      }
    },
    //kiểm tra tốc độ có IP tĩnh, mức cuớ thuê bao cũ = mới
    KiemTraDuLieu_ThueBao(){
      if(this.params.isIPtinh == 1 && this.list_TTThueBao.loaiip == null){
         this.$alert("Hãy chọn loại ip đối với ip tĩnh!", "Thông báo", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "info",
            });
      }else{
        if(this.list_TTThueBao.muccuoctbcu == this.list_TTThueBao.muccuoctbmoi){
            this.$alert("Dữ liệu về mức cước mới và mức cước cũ không được giống nhau!", "Thông báo", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "info",
          });
        }else{
           return true;
        }
      }
    },
    async XoaHDKH(hdkhid, hdtbid){
      this.loading = true;
      let data = {
        hdkh_id:  hdkhid,
        hdtb_id: hdtbid
      };
      const response = await API.btnXoaHDKH(this.axios, data);
      if (response.data.error_code === "BSS-00000000") {
        if (
          response.data.data !== undefined &&
          response.data.data.length >= 0
        ) {
          this.SetButton(0);
        } else {
          this.$toast.error("Có lỗi trong quá trình cập nhật!");
        }
      }else{
        this.$toast.error(response.data.data.toString())
      }
      this.loading = false;
    },
    async ThemHDThueBao(){
      this.loading = true;
      let quytrinh = 0;
      let db_adsl=[];
      await API.LayHuongGiao_TiepNhan(this.axios, {"kieuLdId": this.listTT_Phieu_YC.kieuyc,"loaiHdId": 8,
          "loaiTbId": this.list_TTThueBao.loaihinh,"mucCuocId":  this.list_TTThueBao.muccuocmoi,"tocDoId": this.list_TTThueBao.tocdomoi}).then((response) => { 
          if(response.data.error_code === "BSS-00000000") {
            quytrinh = 0 //response.data.data[0].quytrinh_id;
          }
      });
      await API.LAY_DS_DB_ADSL_THEO_TBID(this.axios, this.list_TTKhachHang.return_hdkh[0].thuebao_id).then((response) => { 
        if(response.data.error_code === "BSS-00000000") {
          db_adsl = response.data.data;
        }
      });
      let data = {
        ACTION_TYPE: "A",
        BATBUOC_SOTHANG_CK: this.params.batbuoc_sothang_ck,
        CONGNGHE_CU_ID: this.list_TTThueBao.congnghe,
        CONGNGHE_MOI_ID: 0,
        CT_TIENHD: [
          {
            CT_TIENHD_ID: 0,
            HDTB_ID: this.list_TTThueBao.hopdongtbid,
            ID: 0,
            KHOANMUCTT_ID: 0,
            LOAI_ID: 0,
            PHIEUTT_ID: 0,
            TIEN: this.list_ChiTietTien.tien,
            TYLE_VAT: 0,
            TYLE_VAT_ID: 0,
            VAT: this.list_ChiTietTien.tienvat
          }
        ],
        CUOC_SD: this.list_TTThueBao.cuocsd,
        CUOC_TBI: this.list_TTThueBao.cuocthietbi,
        DICHVUVT_ID: this.listTT_Phieu_YC.dichvuvt,
        HD_THUEBAO: [
          {
            CHUYENMANG: 0,
            DIACHI_LD: this.list_TTThueBao.diachild,
            DIACHI_TB: this.list_TTThueBao.diachitb,
            DICHVUVT_ID: this.listTT_Phieu_YC.dichvuvt,
            DOITUONG_CT_ID: 0,
            DOITUONG_ID: this.list_TTThueBao.doituongmoi,
            DONVI_CUOI_ID: 0,
            DONVI_DAU_ID: 0,
            DONVI_ID: this.list_TTThueBao.return_danhba[0].donvi_id,
            GHICHU: this.list_TTKhachHang.txtGhiChu,
            GOIKT_ID: 0,
            HDKH_ID: this.list_TTKhachHang.hopdongkhid,
            HDTB_CHA_ID: 0,
            HDTB_ID: this.list_TTThueBao.hopdongtbid,
            HDTT_ID: 0,
            HTCM_ID: 0,
            IP_CN: "string",
            KIEULD_ID: this.listTT_Phieu_YC.kieuyc,
            KIEU_TB: "string",
            LOAITB_ID: this.listTT_Phieu_YC.loaihinh,
            MAY_CN:this.$root.token.getMaNhanVien(),
            MA_TB: this.list_TTThueBao.mathuebao,
            MUCUOCTB_ID: this.list_TTThueBao.muccuoctbmoi,
            MUCUOC_TB: 0,
            NGAY_CN:  moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"), 
            NGAY_HH_TD:  moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"), 
            NGAY_HT:  moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"), 
            NGAY_SN:  moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"), 
            NGUOI_CN: this.$root.token.getUserName(),
            NHACC_CU_ID: 0,
            QUYTRINH_ID: quytrinh,
            SOLUONG: 0,
            SOTHANG_CK: this.list_TTThueBao.sothangCK,
            STATUS: 0,
            TBHD_PL: 0,
            TEN_TB: this.list_TTThueBao.tenthuebao,
            TG_THUE_DEN: "string",
            TG_THUE_TU: "string",
            THUEBAO_CHA_ID: 0,
            THUEBAO_ID: this.list_TTThueBao.thuebaoid ,
            TRANGTHAIYC_ID: 0,
            TTHD_ID: 0
          }
        ],
        HDTB_ADSL: [
         {
            BRAS_ID: db_adsl[0].bras_id,
            CAP_GOC: db_adsl[0].cap_goc,
            CAP_P: db_adsl[0].cap_p,
            CHUQUANLD_ID:db_adsl[0].chuquanld_id,       
            CHUQUAN_ID:db_adsl[0].chuquan_id,
            CONGNGHE_ID: db_adsl[0].congnghe_id,
            CULY: db_adsl[0].culy,
            CUOC_DOITAC:db_adsl[0].cuoc_doitac,
            CUOC_QL:db_adsl[0].cuoc_ql,
            CUOC_SD: this.list_TTThueBao.cuocsd,
            CUOC_SODEP:db_adsl[0].cuoc_sodep,
            CUOC_TBI: this.list_TTThueBao.cuocthietbi,
            CUOC_TN:db_adsl[0].cuoc_tn,
            DOICAP_GOC: db_adsl[0].doicap_goc,      
            DOICAP_P: db_adsl[0].doicap_p,
            DOITAC_ID: db_adsl[0].doitac_id,
            DSLAM_ID: db_adsl[0].dslam_id,
            EMAIL: db_adsl[0].email,
            FIRMWARE_VERSION: db_adsl[0].firmware_version,
            GHICHU: db_adsl[0].ghichu,
            HANDATCHO:"string",
            HANG_SX:  db_adsl[0].hang_sx ,
            HARDWARE_VERSION: db_adsl[0].hardware_version,
            HDTB_ID: this.list_TTThueBao.hopdongtbid,
            HOPCAP_P: db_adsl[0].hopcap_p,
            IP: db_adsl[0].ip,
            IPV4_WAN: db_adsl[0].ipv4_wan,
            IPV6_LAN: db_adsl[0].ipv6_lan,
            IP_MP: db_adsl[0].ip_mp,
            KETCUOI_ID: db_adsl[0].ketcuoi_id,
            KH_CD:0,
            KIEUTRA_ID: db_adsl[0].kieutra_id,
            LIENTU: db_adsl[0].lientu,
            LOAICAP_ID: db_adsl[0].loaicap_id,
            LOAI_CSHT_ID: db_adsl[0].loai_csht_id,
            MADOICAP: db_adsl[0].madoicap,
            MATB_MGWAN:"string",
            MATB_TN: db_adsl[0].matb_tn,
            MA_LT: db_adsl[0].ma_lt,
            MA_VNPT: db_adsl[0].ma_vnpt,
            MUCCUOC_ID: db_adsl[0].muccuoc_id ,
            NGAYTRA: db_adsl[0].ngaytra,
            NGAY_GC: db_adsl[0].ngay_gc,
            PASSWORD: db_adsl[0].password,
            PORT_ID: db_adsl[0].port_id,
            PROFILEDSL_ID: db_adsl[0].profiledsl_id,
            SERIAL_STB:"string",
            SERI_MD: db_adsl[0].seri_md,
            SLID: db_adsl[0].slid,
            SLTV_HTVC: db_adsl[0].sltv_htvc,
            SO_DT: db_adsl[0].so_dt,
            STATUS_IP:"string",
            TG_GIAM: db_adsl[0].tg_giam,
            THANG_ID: this.list_TTThueBao.thangtra,
            THIETBIDC_ID: db_adsl[0].thietbidc_id,
            THOIHAN_ID: db_adsl[0].thoihan_id,
            THONGTIN_TC: this.list_TTKhachHang.thongtintc,
            TINHKHAC: db_adsl[0].tinhkhac,
            TL_GIAMCUOC_QL: db_adsl[0].tl_giamcuoc_ql,
            TOCDOTHUC_ID: db_adsl[0].tocdothuc_id,
            TOCDO_ID: this.list_TTThueBao.tocdomoi,// 8684,
            TRAMTB_ID: db_adsl[0].tramtb_id,
            TRANGBI_ID: this.list_TTThueBao.trangbimoi,
            TUCAP_GOC: db_adsl[0].tucap_goc,
            TUCAP_P: db_adsl[0].tucap_p,
            TUCTHI: 0,
            TUYENCAP: db_adsl[0].tuyencap,
            TYLE_GC: db_adsl[0].tyle_gc,
            USERNAME: db_adsl[0].username,
            VCI_VPI_ID: db_adsl[0].vci_vpi_id,
            VMP: db_adsl[0].vmp,
        }
        ],
        HDTB_ID: this.list_TTThueBao.hopdongtbid,
        HDTB_TC: [
          {
            GIATRI: 0,
            HDTB_ID: this.list_TTThueBao.hopdongtbid,
            LOAITIEN_ID: 0,
            THUEBAO_ID: this.list_TTThueBao.thuebaoid
          }
        ],
        KHACHHANG_ID: this.list_TTKhachHang.khachhangid, 
        KIEULD_ID: this.listTT_Phieu_YC.kieuyc,
        KIEUTRA_CU_ID: 0,
        KIEUTRA_MOI_ID: 0,
        KIEUTRAMOI: 0,
        LOAITB_ID: this.listTT_Phieu_YC.loaihinh,
        MA_HD:  this.listTT_Phieu_YC.maHDPL,
        MA_KH: this.list_TTKhachHang.makhachhang, 
        MA_TB:  this.list_TTThueBao.mathuebao,
        MUCCUOC_ID: 0,
        TEN_KH: this.list_TTKhachHang.tenkhachhang, 
        THONGTIN_TT: [
          {
            HDTB_ID: this.list_TTThueBao.hopdongtbid,
            KHUVUC_ID: 0,
            KIEUTT_ID: 0,
            NGAY_TT:  moment(this.params.p_today).format("DD/MM/YYYY HH:mm:ss"), 
            TYLE_VAT: 0
          }
        ],
        THUEBAO_ID: this.list_TTThueBao.thuebaoid,
        TOCDO_CU_ID: this.list_TTThueBao.tocdocu,
        TOCDO_ID: this.list_TTThueBao.tocdomoi,
        TOCDOTHUC_CU_ID: 0,
        TOCDOTHUC_MOI_ID: 0
      };
      console.log(data);
      const response = await API.btnThemHDTB(this.axios, data);
      if (response.data.error_code === "BSS-00000000") {
        if (
          response.data.data !== undefined &&
          response.data.data.length >= 0
        ) {
          this.LAY_DS_HDTB_THAYDOI_TOCDO(this.list_TTKhachHang.hopdongkhid)
        } else {
          this.$toast.error("Có lỗi trong quá trình cập nhật!");
        }
      }else{
        this.$toast.error(response.data.data.toString())
      }
      this.loading = false;
    },
    async XoaHDThueBao(hdtb, hdkhid){
      this.$toast.error("hdtb: "+hdtb+" hdkh: "+hdkhid);
      this.loading = true;
      let data = {
        hdtb_id: hdtb
      };
      const response = await API.btnXoaHDTB(this.axios, data);
      if (response.data.error_code === "BSS-00000000") {
        if (
          response.data.data !== undefined &&
          response.data.data.length >= 0
        ) {
           this.LAY_DS_HDTB_THAYDOI_TOCDO(hdkhid)
        } else {
          this.$toast.error("Có lỗi trong quá trình cập nhật!");
        }
      }else{
        this.$toast.error(response.data.data.toString())
      }
      this.loading = false;
    },
    async clickNhapMoi(){
      this.SetButton(1);
    },
    async clickGhiLai() {
      if(this.params.value_tucthi == 1){
        this.$confirm('Thuê bao có thể thay đổi tức thì, Bạn có chọn tức thì không ?','Thông báo',{
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                }).then(() => {
                  if(this.KiemTraDuLieu_ThueBao()){
                    if(this.button.nhapmoi == false){
                      this.ThemMoiHopDong();
                    }else{
                      this.CapNhatHopDong();
                    }
                  }
                })
      }else{
        if(this.KiemTraDuLieu_ThueBao()){
           if(this.button.nhapmoi == false){
                this.ThemMoiHopDong();
            }else{
                this.CapNhatHopDong();
          }
        }
      }
      
    },
    clearText(){
      this.ds_Thuebao_table=[];
      this.params.tongtienhd = 0;
      this.params.chk_tucthi=false;
      this.list_TTThueBao.tenthuebao=null;
      this.list_TTThueBao.doituongcu =null;
      this.list_TTThueBao.muccuoctbcu=null;
      this.list_TTThueBao.trangbimoi = 3 //default khách hàng tự trang bị
      this.returnd_diachi_ld = [];
      this.params.p_apldid = 0;
      this.params.p_phuongldid = 0
      this.params.p_khuldid = 0;
      this.params.p_pholdid = 0;
      this.list_TTKhachHang.hopdongkhid =  0;
      this.listTT_Phieu_YC.magiaodich = null;
      this.listTT_Phieu_YC.maHDPL = null;
      this.list_TTKhachHang.makhachhang = null;
      this.list_TTKhachHang.khachhangid = 0;  
      this.list_TTKhachHang.tenkhachhang = null;
      this.list_TTKhachHang.diachikhachhang = null;
      this.list_TTThueBao.cuocsd = 0;
      this.list_TTThueBao.cuocthietbi = 0;
      this.listTT_Phieu_YC.dichvuvt= null
      this.listTT_Phieu_YC.loaihinh = null
      this.listTT_Phieu_YC.kieuyc= null
      this.list_TTThueBao.hopdongtbid = 0;
      this.listTT_Phieu_YC.chk_tucthi = false;
      this.listTT_Phieu_YC.ngayLapHD = null;
      this.list_TTThueBao.thuebaoid = 0;
      this.list_TTThueBao.diachitb= 0;
      this.list_TTThueBao.diachild = null;
      this.list_TTThueBao.sothangCK = 0;
      this.list_TTThueBao.doituongcu = 0;  
      this.list_TTThueBao.doituongmoi = 0;
      this.list_TTThueBao.donviql = 0;
      this.list_TTThueBao.muccuoctbcu = 0;
      this.list_TTThueBao.muccuoccu = 0;
      this.list_TTThueBao.tocdocu = 0;
      this.list_TTKhachHang.ngaysinh = '';
      this.list_TTKhachHang.txtTTTCVisa = '';
      this.filterbox.ctv.value = null;
      this.filterbox.nguoigt.value = null;
      this.list_TTThueBao.tocdomoi = null;
      this.list_TTThueBao.tocdothucmoi = null;
      this.list_TTKhachHang.sothang = 0;
    },
    async clickHuy(){
      this.SetButton(0);
    },
    //#endregion
    async clickXoaHD(){
       this.$alert("Bạn thật sự muốn xóa hợp đồng không ?", "Thông báo", {
        dangerouslyUseHTMLString: true,
        showCancelButton: true,
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        showCloseButton: true,
        type: "warning",
      }).then(async (v) => {
          if (!v) {return;}
          this.XoaHDKH(4419482, 4879227);   
        });
    },
    async clickThemTB(){
      this.ThemHDThueBao();
    },
    async layDsPhieuChuaTT(hopdongtbid){
      try {
        let res = await this.axios.post('/web-hopdong/khuyenmai-thaydoi-loaihinhtb/lay_ds_phieu_chuatt', {
          vhdtb_id: hopdongtbid
        });
        if(res.data.data && res.data.error =="200" && res.data.error_code == "BSS-00000000")
          return res.data.data;
        return [];
      } catch (error) {
        this.$toast.error(error);
      }
    },
    // UR2.7.004_026
    async tsbtnSuaKM_Click(){
      try{
        if(!this.list_TTThueBao.hopdongtbid){
          this.$toast.warning("Bạn phải lấy thông tin hợp đồng trước khi sửa khuyến mại!");
          return;
        }
        // if (hc_hdtb_id != 0 && hc_hdtb_id != hdtb_id)
        // {
        //     Message_Box.ShowWarning("Không đúng thuê bao cần hiệu chỉnh. Bạn hãy kiểm tra lại!");
        //     return;
        // }
        // FE TU XU LY //longdx: bo sung thong tin hdtb_id vao bang khuyenmai, dat coc
        let update_tien = false, vphieutt_id = -1;
        if (!this.kiemtra_khoanmuc_tien()){
          let cfDialog = await this.$confirm("Tiền hợp đồng đã thay đổi, bạn có muốn tiếp tục cập nhật?",'Thông báo', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
          });
          // console.log('xxx', cfDialog);
          if(cfDialog === "cancel"){
            return;
          }
          let ds = await this.layDsPhieuChuaTT(this.list_TTThueBao.hopdongtbid)
          if (ds) {
            if (ds.length == 0)
            {
                this.$toast.warning("Hợp đồng đã thoái trả, không thể sửa tiền!");
                return;
            }
            if (ds[0]["trangthai"].toString() == "1")
            {
              let cfDialog = await this.$confirm("Phiếu thanh toán đã thu tiền sẽ chuyển thành trạng thái chưa thu tiền và hủy hóa đơn cũ, bạn có muốn sửa?",
                  'Thông báo', {
                  confirmButtonText: 'Có',
                  cancelButtonText: 'Không'
              })
              if(cfDialog === "cancel"){
                return;
              }
              vphieutt_id = ds[0]["phieutt_id"]
            }
            
          }else{
            this.$toast.warning("Không thể lấy thông tin phiếu thanh toán, xin thử lại sau!");
            return;
          }
          update_tien = true;
          this.TaoDuLieu_CT_TIENHD(true);

        }else{
          console.log('kiểm tra tiền khoản mục thất bại !!');
        }

        // if(this.params.kiemtra_khop_tien_datcoc == "1"){
        //   let tien_tratruoc = 0;
        //   let tien_datcoc = 0;
        //   for (let i = 0; i < this.list_ChiTietTien.ds_tien_khoanmuc.length; i++)
        //   {
        //       if (this.list_ChiTietTien.ds_tien_khoanmuc[i]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_DATCOC)
        //       {
        //         tien_tratruoc = Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["tien"]) + Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["vat"]);
        //       }
        //   }

        //   for (let dc1 = 0; dc1 < this.dsKM_HDTB.length; dc1++)
        //   {
        //       tien_datcoc += Number(this.dsKM_HDTB[dc1]["datcoc_csd"]);
        //   }

        //   for (let dc2 = 0; dc2 < this.dsDC_HDTB.length; dc2++)
        //   {
        //       tien_datcoc += Number(dsDC_HDTB[dc2]["datcoc_csd"]);
        //   }
          
        //   if (tien_tratruoc != tien_datcoc)
        //   {
        //       this.$toast.warning("Tiền đặt cọc và tiền khoản mục không khớp. Liên hệ admin để kiểm tra lại! ");
        //       return;
        //   }
        // }
        // console.log('tới dòng 3152 rồi nhé !!');
        
        // statrt thêm hdtb_id vào dsKM_HDTB, dsDC_HDTB
        this.dsKM_HDTB.forEach(item => item['HDTB_ID'] = this.list_TTThueBao.hopdongtbid);
        this.dsDC_HDTB.forEach(item => item['HDTB_ID'] = this.list_TTThueBao.hopdongtbid);

        // end thêm hdtb_id vào dsKM_HDTB, dsDC_HDTB
        if (this.list_TTThueBao.hc_hdtb_id != 0 && this.list_TTThueBao.hc_tdtt_hdtb_id != 0){
            // Lưu lịch sử trước khi điều chỉnh
            let ls = await this.axios.post('/web-hopdong/khuyenmai-thaydoi-loaihinhtb/insert_ls_hieuchinh_km', {
              vtdtt_hdtb_id: this.list_TTThueBao.hc_tdtt_hdtb_id,
              vhdtb_id: this.list_TTThueBao.hopdongtbid,
              vkieu: 1
            })
            if (ls.data.data != "OK")
            {
                this.$toast.error(ls.data.data);
                return;
            }
        }
        // console.log('tới dòng 3166 rồi nhé !!');

        let ds_tb_hddt_1 = [];
        let res_ds_tb_hddt_1 = await this.axios.post('/web-hopdong/khuyenmai-thaydoi-loaihinhtb/lay_hopdong_tb_hddt', {
          vhdkh_id: this.list_TTKhachHang.hopdongkhid,
          vkieu: 1
        });
        if(res_ds_tb_hddt_1.data.data && res_ds_tb_hddt_1.data.error == "200" && res_ds_tb_hddt_1.data.error_code == "BSS-00000000"){
          ds_tb_hddt_1 = [...res_ds_tb_hddt_1.data.data];
        }
        // cap nhat KM
        let sErr = await this.axios.post('/web-hopdong/khuyenmai-thaydoi-loaihinhtb/fn_khuyenmai_update', {
          vhdtb_id: this.list_TTThueBao.hopdongtbid,
          dskm_hdtb: JSON.stringify(this.dsKM_HDTB),
          dsdc_hdtb: JSON.stringify(this.dsDC_HDTB),
          dsctthd: JSON.stringify(this.list_ChiTietTien.dsCTTHD),
          vphieutt_id: vphieutt_id,
          vupdate_tien: Number(update_tien)
        });

        let ds_tb_hddt_2 = [];
        let res_ds_tb_hddt_2 = await this.axios.post('/web-hopdong/khuyenmai-thaydoi-loaihinhtb/lay_hopdong_tb_hddt', {
          vhdkh_id: this.list_TTKhachHang.hopdongkhid,
          vkieu: 1
        });
        if(res_ds_tb_hddt_2.data.data && res_ds_tb_hddt_2.data.error == "200" && res_ds_tb_hddt_2.data.error_code == "BSS-00000000"){
          ds_tb_hddt_2 = [...res_ds_tb_hddt_2.data.data];
        }
        debugger
        // Tham chiếu đến UR2.1.035_108
        let shddt = await this.phathanh_hddt_dieuchinh_td(ds_tb_hddt_1, ds_tb_hddt_2);

        if(shddt != "OK"){
          console.log('dong 3194');
          this.$toast.warning(shddt);
        } else if(sErr.data.data == "") {
          console.log('dong 3197');
          this.$toast.success("Cập nhật thành công");
          
          // ko thấy khảo sát
          // if (hc_hdtb_id != 0 && hc_tdtt_hdtb_id != 0)
          // {
          //     string ud = "";
          //     ud += " update " + DatabaseConstants.DB2 + ".tdtt_hd_thuebao ";
          //     ud += " set tthc_id = 6, ";
          //     ud += "     nguoi_th = '" + tt_nd.ma_nd + "', ";
          //     ud += "     ngay_th = sysdate, ";
          //     ud += "     may_th = '" + tt_nd.may_cn + "', ";
          //     ud += "     noidung_th = 'Thuc hien xong', ";
          //     ud += "     ketqua_th = 1 ";
          //     ud += " where tdtt_hdtb_id = " + hc_tdtt_hdtb_id;
          //     new TruyenDanFacade().EXECUTE_SQL(ud);
          //     this.Close();
          //     return;
          // }

          this.MaGDChanged();
        } else {
          console.log('dong 3219');
          this.$toast.success(sErr.data.data)
        }
        
        // ko thấy khảo sát
        // if (hc_hdtb_id != 0 && hc_tdtt_hdtb_id != 0)
        // {
        //     string ud = "";
        //     ud += " update " + DatabaseConstants.DB2 + ".tdtt_hd_thuebao ";
        //     ud += " set tthc_id = 6, ";
        //     ud += "     nguoi_th = '" + tt_nd.ma_nd + "', ";
        //     ud += "     ngay_th = sysdate, ";
        //     ud += "     may_th = '" + tt_nd.may_cn + "', ";
        //     ud += "     noidung_th = 'Thuc hien xong', ";
        //     ud += "     ketqua_th = 1 ";
        //     ud += " where tdtt_hdtb_id = " + hc_tdtt_hdtb_id;
        //     new TruyenDanFacade().EXECUTE_SQL(ud);
        //     this.Close();
        //     return;
        // }

      }catch(error){
        this.$toast.warning("Cập nhật thất bại! " + error);
        console.log(error)
      }
    },
    async phathanh_hddt_dieuchinh_td(dt_khoanmuc_cu, dt_khoanmuc_moi){
      try {
        let sKq = "";
        let sql = "";
        let dtProduct = [...dt_khoanmuc_cu];
        //so sánh khoản mục cũ và mới
        //PRODNAME, TONGTIEN,PRODPRICE,TIEN,VAT,AMOUNT
        let i1, i2;//row bảng cũ, bảng mới trùng nhau
        let bl1 = false;//kiểm tra có row trùng nhau không
        let tongtien_cu = 0, tongtien_moi = 0;
        let dtCu = [...dt_khoanmuc_cu];
        let dtMoi = [...dt_khoanmuc_moi];
        debugger
        for (let i = 0; i < dt_khoanmuc_cu.length; i++)
        {
            bl1 = false;
            let tongtien, prodprice, tien, vat, amount;
            tongtien_cu += Number(dt_khoanmuc_cu[i]["tongtien"]);
            for (let j = 0; j < dt_khoanmuc_moi.length; j++)
            {
                if (dt_khoanmuc_cu[i]["prodname"] == dt_khoanmuc_moi[j]["prodname"])
                {
                    //Cũ mới đều có, so sánh tiền
                    tongtien = Number(dt_khoanmuc_moi[j]["tongtien"]) - Number(dt_khoanmuc_cu[i]["tongtien"]);
                    prodprice = Number(dt_khoanmuc_moi[j]["prodprice"]) - Number(dt_khoanmuc_cu[i]["prodprice"]);
                    tien = Number(dt_khoanmuc_moi[j]["tien"]) - Number(dt_khoanmuc_cu[i]["tien"]);
                    vat = Number(dt_khoanmuc_moi[j]["vat"]) - Number(dt_khoanmuc_cu[i]["vat"]);
                    amount = Number(dt_khoanmuc_moi[j]["amount"]) - Number(dt_khoanmuc_cu[i]["amount"]);
                    if (tongtien != 0)
                    {
                        let dr = dtCu[i];
                        if (tongtien < 0)
                        {
                            dr["prodname"] = "Giảm doanh thu " + dr["prodname"];
                        }
                        else
                        {
                            dr["prodname"] = "Tăng doanh thu " + dr["prodname"];
                        }
                        //sửa lại tiền
                        dr["tongtien"] = tongtien;
                        dr["prodprice"] = prodprice;
                        dr["tien"] = tien;
                        dr["vat"] = vat;
                        dr["amount"] = amount;
                        dtProduct.push(dr);
                    }
                    bl1 = true;
                    break;
                }
            }
            if (!bl1)//Cũ có mới không có, điều chỉnh tăng hoặc giảm tùy theo tiền âm hay dương
            {
                let dr = dtCu[i];

                tongtien = Number(dtCu[i]["tongtien"]);
                prodprice = Number(dtCu[i]["prodprice"]);
                tien = Number(dtCu[i]["tien"]);
                vat = Number(dtCu[i]["vat"]);
                amount = Number(dtCu[i]["amount"]);
                if (tongtien < 0)//Nếu cũ âm, mới ko có, ĐC tăng
                    dr["prodname"] = "Tăng doanh thu " + dr["prodname"];
                else
                    dr["prodname"] = "Giảm doanh thu " + dr["prodname"];
                dr["tongtien"] = -tongtien;
                dr["prodprice"] = -prodprice;
                dr["tien"] = -tien;
                dr["vat"] = -vat;
                dr["amount"] = -amount;
                dtProduct.push(dr);
            }
        }
        for (let i = 0; i < dt_khoanmuc_moi.length; i++)
        {
            bl1 = false;
            let tongtien, prodprice, tien, vat, amount;
            tongtien_moi += Number(dt_khoanmuc_moi[i]["tongtien"]);
            for (let j = 0; j < dt_khoanmuc_cu.length; j++)
            {
                if (dt_khoanmuc_cu[j]["prodname"] == dt_khoanmuc_moi[i]["prodname"])
                {
                    bl1 = true;
                    break;
                }
            }
            if (!bl1)//Mới có cũ không có, điều chỉnh tăng giảm tùy thuộc dấu của khoản mục
            {
                let dr = dtMoi[i];
                tongtien = Number(dtMoi[i]["tongtien"]);
                if (tongtien < 0)//Cũ ko có, mới âm -> DC giảm
                    dr["prodname"] = "Giảm doanh thu " + dr["prodname"];
                dtProduct.push(dr);
            }
        }
        if (dtProduct.length > 0)
        {
            let res = await this.axios.post('/web-hopdong/thaydoiloaihinhtb/lay_tt_hddt_dc', { 
              vhdkh_id: this.list_TTKhachHang.hopdongkhid,
            });
            let ds = [...res.data.data];
            if (ds.length > 0)
            {
                let vthaotac = 2;
                let vkieu = 2;
                if (tongtien_cu == tongtien_moi)
                {
                    // sql = "update " + DatabaseConstants.DB2 + ".phieutt_hd set trangthai_hddt=3 where hdkh_id=" + hdkh_id;
                    // new BangThamSo().ExeSQL(sql);
                    await this.axios.post('/web-hopdong/thaydoiloaihinhtb/sp_capnhat_phieutt_hd_hdkh_id', {
                      p_hdkh_id: this.list_TTKhachHang.hopdongkhid,
                    })
                    return "OK";
                }
                else if (tongtien_cu < tongtien_moi)
                {
                    vkieu = 1;
                }
                let dtThongTin = [...ds];
                let vifkey = dtThongTin[0]["hddt_fkey"];
                if (!vifkey)
                {
                    sKq = "OK";
                    return sKq;
                }

                let vdc_tt = 1;//1: Điều chỉnh, 2 thay thế
                //Kiểm tra hóa đơn trước đó đã được điều chỉnh hay chưa
                //Nếu điều chỉnh rồi thì không cho thay thế nữa
                // sql = "select 1 from " + DatabaseConstants.DB2 + ".phieutt_hd a,  " + DatabaseConstants.DB6 + ".bd_hddt b ";
                // sql += " where a.ma_gd=b.ma_tt and b.kieu_bd in (1,8,9,10) and a.ht_tra_id<>6 and a.hdkh_id= " + hdkh_id;
                // DataTable dt1 = new BangThamSo().GetDataSql(sql);
                let res = await this.axios.post('/web-hopdong/thaydoiloaihinhtb/sp_kt_dieuchinh_hddt', {
                  p_hdkh_id: this.list_TTKhachHang.hopdongkhid,
                });
                let dt1 = [...res.data.data];
                if (dt1.length == 0)
                {
                    this.$refs.popupDieuChinh.showModal();
                    vdc_tt = this.getPicked();
                }
                let vphieutt_id = Number(dtThongTin[0]["phieutt_id"]);
                let vhdkh_id = Number(dtThongTin[0]["hdkh_id"]);
                let vma_gd = dtThongTin[0]["ma_gd"];
                let vten_kh = dtThongTin[0]["ten_kh"];
                let vdiachi_kh = dtThongTin[0]["diachi_kh"];
                let vmst = dtThongTin[0]["mst"];
                let vten_nh = dtThongTin[0]["ten_nh"];
                let vstk = dtThongTin[0]["stk"];
                let vemail = dtThongTin[0]["email"];
                let vfax = dtThongTin[0]["so_fax"];
                let vsodt = dtThongTin[0]["so_dt"];
                let vndd = dtThongTin[0]["nguoi_dd"];
                let vloai_kh = dtThongTin[0]["loai_kh"];
                let vhttt = dtThongTin[0]["httt"];
                //Xuất hóa đơn điều chỉnh giảm hoặc thay thế
                if (vdc_tt == 1)//Điều chỉnh
                {
                  vthaotac = 2;
                }
                else//thay thế
                {
                    vthaotac = 3;
                    dtProduct = [...dt_khoanmuc_moi];
                }
                sKq = await this.dongbo_hddt_tudong(vthaotac, vkieu, vifkey, vphieutt_id, hdkh_id, vma_gd, vten_kh, vdiachi_kh, vmst, vten_nh, vstk, vemail, vfax, vsodt, vndd, vloai_kh, vhttt, dtProduct);
                if (sKq != "OK")
                {
                    if (vdc_tt == 1)
                        sKq = "Phát hành hóa đơn điều chỉnh thất bại!" + sKq;
                    else
                        sKq = "Phát hành hóa đơn thay thế thất bại!" + sKq;
                }
            }
            else
            {
                sKq = "OK";
                return sKq;
            }
        }
        else
        {
            sKq = "OK";
        }
        return sKq;
      } catch (error) {
        this.$toast.error(error);
      }
    },
    
    async GetInvoiceXml_DieuChinh( i_kieu_dc,  i_ten_kh,  i_ma_gd,  i_mst,  i_diachi_ct, i_stk,  i_so_dt,  i_httt, i_dt, obj){
      try {
        // let tongtien_hddt = 0, tongvat_hddt = 0, tong_hddt = 0;
        let str = '';
        let sinh_fkey = await this.axios.get('/web-ccdv/thanhtoan/sinh_fkey?kieu=' + i_kieu_dc);
        let fkey_new = sinh_fkey.data.data;
        // new LapHopDongFacade2().SINH_FKEY(1);
        str += "<AdjustInv>";
        str+= "<key>"+fkey_new+"</key>";
        str+= "<CusCode>"+i_ma_gd+"</CusCode>";
        str+= "<CusName><![CDATA["+obj.CusName+"]]></CusName>";
        str+= "<CusAddress><![CDATA["+obj.CusAddress+"]]></CusAddress>";
        str+= "<CusPhone>"+obj.CusPhone+"</CusPhone>";
        str+= "<CusTaxCode><![CDATA["+obj.CusTaxCode+"]]></CusTaxCode>";
        str+= "<PaymentMethod>Tiền mặt/Chuyển khoản</PaymentMethod>";
        str+= "<KindOfService></KindOfService>";
        let type = i_kieu_dc == 1 ? "2" : i_kieu_dc == 2 ? "3" : "4";
        str+="<Type>"+type+"</Type>";
        str+="<Products>";
        for (let i = 0; i < i_dt; i++)
        {
            if (i_dt[i]["prodname"] != "")
            {
                str+="<Product>";
                str+="<ProdName>"+i_dt[i]["prodname"]+"</ProdName>";
                str+="<ProdUnit>"+i_dt[i]["produnit"]+"</ProdUnit>";
                str+="<ProdQuantity>"+i_dt[i]["prodquantity"]+"</ProdQuantity>";
                str+="<ProdPrice>"+Math.abs(Number(i_dt[i]["prodprice"]))+"</ProdPrice>";
                str+="<Amount>"+Math.abs(Number(i_dt[i]["amount"]))+"</Amount>";
                str+="</Product>";

                tongtien_hddt += Number(i_dt[i]["tien"]);
                tongvat_hddt += Number(i_dt[i]["vat"]);
                tong_hddt += Number(i_dt[i]["tongtien"]);
            }
        }
        this.tt_hddt.tong_hddt = Math.abs(tong_hddt);
        this.tt_hddt.tongvat_hddt = Math.abs(tongvat_hddt);
        this.tt_hddt.tongtien_hddt = Math.abs(tongtien_hddt);
        str +="</Products>";
        str +="<Total>" + this.tt_hddt.tongtien_hddt.toString()+"</Total>";
        str +="<VATRate>10</VATRate>";
        str +="<VATAmount>"+ this.tt_hddt.tongvat_hddt.toString()+"</VATAmount>";
        str +="<Amount>"+ this.tt_hddt.tong_hddt.toString()+"</Amount>";

        let DocTien = "";
        let tienkt = "";
        tienkt = tong_hddt.toString();
        if (tong_hddt.toString().includes("-"))
            tienkt = tienkt.replace("-", "");
        // DocTien = CommonFunction.DocSo(tienkt, ',', "phẩy") + " đồng";
        let getMoneyTotext = await this.axios.post('/web-tracuu/tracuu_th/fn_doisosangchu', {
          money: Number(tienkt)
        });
        if(getMoneyTotext){
          DocTien = getMoneyTotext.data.data;
        }
        let TienBangChu = "";
        TienBangChu += DocTien.toString().toUpperCase();
        for (let i = 1; i < DocTien.Length; i++)
        {
            TienBangChu += DocTien[i];
        }
        if (tong_hddt.toString().includes("-"))
            this.tt_hddt.tbc = "Âm " + TienBangChu;
        else
            this.tt_hddt.tbc = TienBangChu;

        str+="<AmountInWords>" + tbc + "</AmountInWords>";
        str+="<Teller>" + tt_nd.ma_nv + "</Teller>";
        str+="</AdjustInv>";
        return str;
      } catch (error) {
        this.$toast.error(error);
      }
    },
    async dongbo_hddt_tudong(vthaotac, vkieu, vifkey, vphieutt_id, vhdkh_id, vma_gd, vten_kh, vdiachi_kh, vmst, vten_nh, vstk, vemail, vfax, vsodt, vndd, vloai_kh, vhttt, product){
      try {
        let invToken = '';
        if (vthaotac == 2){
          if (vkieu == 1 || vkieu == 2 || vkieu == 3)
          {
              if (!vifkey)
              {
                  return "ERR: Không có thông tin Fkey cần điều chỉnh";
              }
              
              let ds_token = [];
              let sp_lay_ds_phieutt_hd_loaihoadon = await this.axios.post('/web-hopdong/thaydoiloaihinhtb/sp_lay_ds_phieutt_hd_loaihoadon', {
                p_phieutt_id: vphieutt_id
              });
              ds_token = [...sp_lay_ds_phieutt_hd_loaihoadon.data.data];
              if (ds_token.length > 0)
              {
                invToken = ds_token[0]["ten_loaihd"] + ";" + ds_token[0]["seri"] + ";" + ds_token[0]["soseri"];
              }
              else
              {
                return "ERR: Không tìm thấy hóa đơn cần điều chỉnh !";
              }

              let bdhddt_cha_id = 0;
              // let bd_cha = "";
              // bd_cha += " Select max(nvl(bdhddt_id,0)) ";
              // bd_cha += " From " + DatabaseConstants.DB6 + ".bd_hddt ";
              // bd_cha += " Where hdkh_id = " + vhdkh_id;
              let sp_lay_ds_bd_hddt_max = await this.axios.post('/web-hopdong/thaydoiloaihinhtb/sp_lay_ds_bd_hddt_max', {
                p_hdkh_id: vhdkh_id
              });
              ds_bd_cha = [...sp_lay_ds_bd_hddt_max.data.data];
              if (ds_bd_cha.length > 0)
                bdhddt_cha_id = (ds_bd_cha[0]['max(nvl(bdhddt_id,0))']);

              let downloadInvNoPay = await this.axios.post('/tichhop/vnptinvoice/downloadInvNoPay', {
                invToken: invToken
              });
              let xml = downloadInvNoPay.data.data;
              let objHoaDonDienTu;
              // invoice.downloadInvNoPay_V2(invToken, tt_nd.user_hddt, tt_nd.pass_hddt);
              if(!xml)
              {
                  return "ERR: downloadInvNoPay";
              }
              else
              {
                  // XmlDocument doc = new XmlDocument();
                  // doc.LoadXml(xml);
                  // XmlNode xnList = doc.SelectNodes("/Invoice/Content").Item(0);
                  // XmlDocument doc1 = new XmlDocument();
                  // doc1.LoadXml("<Content>" + xnList.InnerXml + "</Content>");
                  // objHoaDonDienTu = (Content)ObjectToXML(doc1.InnerXml, typeof(Content));
                  xml = "<Content>" + xml + "</Content>"
                  objHoaDonDienTu = JSON.parse(convert.xml2json(xml, {compact: true}));
                  // console.log('asdads ', JSON.parse(objHoaDonDienTu));
              }
              this.tt_hddt.tongtien_hddt = 0;
              this.tt_hddt.tongvat_hddt = 0;
              this.tt_hddt.tong_hddt = 0;

              let sohoadon = "", pattern = "", seri = "", loaihoadon_id = 0, seri_id = 0;
              let sp_tt_loaihoadon_seri = await this.axios.get('/web-hopdong/thaydoiloaihinhtb/sp_tt_loaihoadon_seri');

              let ds_hd_seri = [];
              ds_hd_seri = sp_tt_loaihoadon_seri.data.data;
              if (ds_hd_seri.length > 0)
              {
                  loaihoadon_id = Number(ds_hd_seri[0]["loaihoadon_id"]);
                  pattern = ds_hd_seri[0]["ten_loaihd"];
                  seri_id = Number(ds_hd_seri[0]["seri_id"]);
                  seri = ds_hd_seri[0]["seri"];
              }
              else
              {
                  return "ERR: Không tìm thấy loại hóa đơn, seri !";
              }

              // Kiểm tra hóa đơn hiện tại có đang ở trạng thái Đang sử dụng không ?
              // let kq_kt = invoice.listInvByCus_V2(vma_gd, "", "", tt_nd.user_hddt, tt_nd.pass_hddt, pattern);
              let listInvByCus_V2 = await this.axios.post('/tichhop/vnptinvoice/listInvByCus', {
                cusCode: vma_gd,
                fromDate: "",
                toDate: ""
              });
              let kq_kt = listInvByCus_V2.data.data;
              if (!kq_kt)
              {
                  return "ERR: listInvByCus";
              }
              else
              {
                // const parser = new DOMParser();
                // const doc = parser.parseFromString(kq_kt, "application/xml");

                  // Data objKetQua_kt = (Data)ObjectToXML(kq_kt, typeof(Data));
                  // EInvoice.Item item_kt = objKetQua_kt.Items.Where(x => x.invToken == invToken).Single();
                  // let item_kt = parser.parseFromString(doc, "application/xml").getElementsByTagName('invToken')[0].childNodes[0].nodeValue;
                  let item_kt_ls = JSON.parse(convert.xml2json(kq_kt));
                  if(item_kta_ls && item_kt_ls.length > 0){
                    let item_kt = item_kt_ls.find(e => e['invToken'] == invToken)
                    if (item_kt.status != 1)
                    {
                        return "Hóa đơn đã bị thay thế/sửa đổi/hủy. Không thể điều chỉnh !";
                    }
                  }
              }

              //kichhoat:

              // let kq = invoice.adjustInv_V2(tt_nd.user_hddt_td, tt_nd.pass_hddt_td, GetInvoiceXml_DieuChinh(vkieu, vten_kh, vma_gd, vmst, vdiachi_kh,
              //     vstk, vsodt, vhttt, product, objHoaDonDienTu), tt_nd.user_hddt, tt_nd.pass_hddt, pattern, seri, vifkey, 0);
              // string[] result = kq.Split(':');
              let xmlInvData = this.GetInvoiceXml_DieuChinh(vkieu, vten_kh, vma_gd, vmst, vdiachi_kh, vstk, vsodt, vhttt, product, objHoaDonDienTu)
              let adjustInv = await this.axios.post('/tichhop/vnptinvoice/adjustInv', {
                acPass: this.tt_nd.pass_hddt,
                account: this.tt_nd.user_hddt,
                convert: 0,
                fkey: vifkey,
                xmlInvData: xmlInvData
              });
              let kq = adjustInv.data.data;
              let result = kq.split(':');
              if (result[0] == "OK")
              {
                  try
                  {
                      let result1 = result[1].toString().split(';');
                      pattern = result1[0].toString();
                      seri = result1[1].toString();
                      sohoadon = result1[2].toString().split('_')[1].toString();
                      fkey_new = result1[2].toString().split('_')[0].toString();
                      invToken = pattern + ";" + seri + ";" + sohoadon;
                    
                      this.tt_hddt.dsBDHDDT = [];
                      let row = {};
                      // let BDHDDT_ID = 0;
                      // BDHDDT_ID = getkey.GetKey("BDHDDT_ID");
                      // row.BDHDDT_ID = BDHDDT_ID;
                      row.MA_TT = vma_gd;
                    
                      if (vkieu == 1) // Điều chỉnh tăng
                          row.KIEU_BD = 8;
                      else if (vkieu == 2) // Điều chỉnh giảm
                          row.KIEU_BD = 9;
                      else if (vkieu == 3) // Điều chỉnh thông tin
                          row.KIEU_BD = 10;
                      else
                          row.KIEU_BD = 1;

                      row.TONGTIEN = this.tt_hddt.tong_hddt;
                      row.NGUOI_CN = tt_nd.ma_nd;
                      row.MAY_CN = tt_nd.may_cn;
                      row.NGAY_CN = tt_nd.ngay_cn;
                      row.NHANVIEN_ID = tt_nd.nhanvien_id;
                      row.DONVI_ID = tt_nd.donvi_id;
                      row.GHICHU = "Tạo hóa đơn điện tử tự động PTTB";
                      row.HDKH_ID = vhdkh_id;
                      row.PHIEUTT_ID = vphieutt_id;
                      row.LOAIHOADON_CU = objHoaDonDienTu.InvoicePattern;
                      row.LOAIHOADON_MOI = pattern;
                      row.SERI_CU = objHoaDonDienTu.SerialNo;
                      row.SERI_MOI = seri;
                      row.SO_HD_CU = objHoaDonDienTu.InvoiceNo;
                      row.SO_HD_MOI = sohoadon;
                      row.SERI_MOI_ID = seri_id;
                      row.TRANGTHAI = 1;
                      row.BDHDDT_CHA_ID = bdhddt_cha_id;
                      row.TEN_TT = objHoaDonDienTu.CusName;
                      row.DIACHI_TT = objHoaDonDienTu.CusAddress;
                      row.SDT = objHoaDonDienTu.CusPhone;
                      row.MST = objHoaDonDienTu.CusTaxCode;

                      row.HINHTHUC_TT = "Tiền mặt/Chuyển khoản";
                      row.TIEN = this.tt_hddt.tongtien_hddt;
                      row.TIENBANGCHU = this.tt_hddt.tbc;
                      row.TYLE_VAT = 10;
                      row.VAT = this.tt_hddt.tongvat_hddt;

                      // 4.7 ecms.Insert(dsBDHDDT, DatabaseConstants.DB6);
                      this.tt_hddt.dsBDHDDT.push(row);
                      await this.axios.post('/web-hopdong/thaydoiloaihinhtb/sp_insert_bd_hddt', {
                        p_js_bd_hddt: JSON.stringify(this.tt_hddt.dsBDHDDT)
                      });
                      // 4.8
                      // TaoDuLieuBDCT(product);
                      // ecms.Insert(dsBDHDDTCT, DatabaseConstants.DB6);
                      let dsBDHDDTCT = [...this.TaoDuLieuBDCT(product)];
                      await this.axios.post('/web-hopdong/thaydoiloaihinhtb/sp_insert_bd_hddt_ct', {
                        p_js_bd_hddt: JSON.stringify(dsBDHDDTCT)
                      });
                      // 4.9
                      // let str_update = " Update " + DatabaseConstants.DB2 + ".phieutt_hd ";
                      // str_update += " set hddt_fkey = '" + fkey_new + "', ";
                      // str_update += " loaihoadon_id = '" + loaihoadon_id + "',";
                      // str_update += " seri_id = '" + seri_id + "',";
                      // str_update += " seri = '" + seri + "',";
                      // str_update += " soseri = '" + sohoadon + "'";
                      // str_update += " Where phieutt_id  = " + vphieutt_id;
                      // new BangThamSo().ExeSQL(str_update);
                      await this.axios.post('/web-hopdong/thaydoiloaihinhtb/sp_capnhat_phieutt_hd_v2', {
                        p_hddt_fkey: fkey_new,
                        p_loaihoadon_id: loaihoadon_id,
                        p_seri_id: seri_id,
                        p_seri: seri,
                        p_soseri: sohoadon,
                        p_phieutt_id: vphieutt_id,
                      });
                      

                      // 4.10
                      // str_update = "";
                      // str_update += " Update " + DatabaseConstants.DB6 + ".bd_hddt ";
                      // str_update += " Set trangthai = 3, ";
                      // str_update += "     ngay_td = sysdate, ";
                      // str_update += "     nguoi_td = '" + tt_nd.ma_nd + "'";
                      // str_update += " Where bdhddt_id = " + bdhddt_cha_id;
                      // bangts.ExeSQL(str_update);
                      await this.axios.post('/web-hopdong/thaydoiloaihinhtb/sp_capnhat_bd_hddt_theo_bdhddt_cha_id', {
                        p_bdhddt_cha_id: bdhddt_cha_id
                      });
                      

                      Fkey = "";
                      fkey_new = "";
                      return "OK";
                  }
                  catch (error)
                  {
                      return "OK";
                  }
              }
              else
              {
                  return "ERR: Có lỗi trong quá trình xuất hóa đơn điện tử: " + kq;
              }
          }
          else
          {
              return "ERR: Kiểm tra lại thông tin kiểu điều chỉnh !";
          }
        }
        else if (vthaotac == 3) // Thay thế hóa đơn
        {
            // #region Thay thế hóa đơn
            if (vifkey == "")
            {
                return "ERR: Không có thông tin Fkey cần thay thế";
            }
            try
            {
                
              let ds_token = [];
              let sp_lay_ds_phieutt_hd_loaihoadon = await this.axios.post('/web-hopdong/thaydoiloaihinhtb/sp_lay_ds_phieutt_hd_loaihoadon', {
                p_phieutt_id: vphieutt_id
              });
              ds_token = [...sp_lay_ds_phieutt_hd_loaihoadon.data.data];
              if (ds_token.length > 0)
              {
                invToken = ds_token[0]["ten_loaihd"].toString() + ";" + ds_token[0]["seri"].toString() + ";" + ds_token[0]["soseri"].toString();
              }
              else
              {
                return "ERR: Không tìm thấy hóa đơn cần điều chỉnh !";
              }

              let sohoadon = "";
              let pattern = "";
              let seri = "";
              let loaihoadon_id = 0;
              let seri_id = 0;

              let sp_tt_loaihoadon_seri = await this.axios.get('/web-hopdong/thaydoiloaihinhtb/sp_tt_loaihoadon_seri');
              let ds_hd_seri = [];
              ds_hd_seri = sp_tt_loaihoadon_seri.data.data;
                if (ds_hd_seri.length > 0)
                {
                    loaihoadon_id = Number(ds_hd_seri[0]["loaihoadon_id"].toString());
                    pattern = ds_hd_seri[0]["ten_loaihd"].toString();
                    seri_id = Number(ds_hd_seri[0]["seri_id"].toString());
                    seri = ds_hd_seri[0]["seri"].toString();
                }
                else
                {
                    return "ERR: Không tìm thấy loại hóa đơn, seri !";
                }

              let listInvByCus_V2 = await this.axios.post('/tichhop/vnptinvoice/listInvByCus', {
                cusCode: vma_gd,
                fromDate: "",
                toDate: ""
              });
              let kq_kt = listInvByCus_V2.data.data;
              if (!kq_kt)
              {
                  return "ERR: listInvByCus";
              }
              else
              {
                  let item_kt_ls = JSON.parse(convert.xml2json(kq_kt));
                  if(item_kta_ls && item_kt_ls.length > 0){
                    let item_kt = item_kt_ls.find(e => e['invToken'] == invToken)
                    if (item_kt.status != 1)
                    {
                        return "Hóa đơn đã bị thay thế/sửa đổi/hủy. Không thể điều chỉnh !";
                    }
                  }
              }
                // CÓ THỂ KHAO SÁT THIẾU
                // Kiểm tra xem có tồn tại hóa đơn đã điều chỉnh không ?
                // string bd_tt = "";
                // bd_tt += " Select count(*) ";
                // bd_tt += " From " + DatabaseConstants.DB6 + ".bd_hddt ";
                // bd_tt += " Where hdkh_id = " + vhdkh_id;
                // bd_tt += "      and trangthai = 3 ";
                // DataSet ds_tt = bangts.getDataFromSQL(bd_tt, "bdtt");
                // if (ds_tt.Tables[0].Rows.Count > 0)
                // {
                //     if (Convert.ToInt32(ds_tt.Tables[0].Rows[0][0].toString()) > 0)
                //         return "ERR: Tồn tại hóa đơn đã bị điều chỉnh. Bạn không thể thay thé !";
                // }
                // end kiểm tra

                let bdhddt_cha_id = 0;
              let sp_lay_ds_bd_hddt_max = await this.axios.post('/web-hopdong/thaydoiloaihinhtb/sp_lay_ds_bd_hddt_max', {
                p_hdkh_id: vhdkh_id
              });
              ds_bd_cha = [...sp_lay_ds_bd_hddt_max.data.data];
              if (ds_bd_cha.length > 0)
                bdhddt_cha_id = (ds_bd_cha[0]['max(nvl(bdhddt_id,0))']);


              let downloadInvNoPay = await this.axios.post('/tichhop/vnptinvoice/downloadInvNoPay', {
                invToken: invToken
              });
              let xml = downloadInvNoPay.data.data;
              let objHoaDonDienTu;
              if(!xml)
              {
                return "ERR: Không có thông tin hóa đơn cần thay thế !";
              }
              else
              {
                objHoaDonDienTu = convert.xml2json(xml, {compact: true});
              }

                
              this.tt_hddt.tongtien_hddt = 0;
              this.tt_hddt.tongvat_hddt = 0;
              this.tt_hddt.tong_hddt = 0;


                //kichhoat:

                let xmlInvData = this.GetInvoiceXml_ThayThe(vma_gd, vhttt, product, JSON.parse(objHoaDonDienTu));
                let replaceInv = await this.axios.post('/tichhop/vnptinvoice/replaceInv', {
                    acPass: this.tt_nd.pass_hddt,
                    account: this.tt_nd.user_hddt,
                    convert: 0,
                    fkey: vifkey,
                    xmlInvData: xmlInvData
                });
                let kq = replaceInv.data.data ? replaceInv.data.data : [];
                let result = kq.split(':');
                if (result[0] == "OK")
                {
                  let objHoaDonDienTuEdit;
                    let result1 = result[1].toString().split(';');
                    pattern = result1[0].toString();
                    seri = result1[1].toString();
                    sohoadon = result1[2].toString().split('_')[1].toString();
                    fkey_new = result1[2].toString().split('_')[0].toString();
                    invToken = pattern + ";" + seri + ";" + sohoadon;
                    let resXML1 = await this.axios.post('/tichhop/vnptinvoice/downloadInvNoPay', {
                      invToken: invToken,
                    });
                    let xml1 = resXML1.data.data ? resXML1.data.data : '';
                    // invoice.downloadInvNoPay_V2(invToken, tt_nd.user_hddt, tt_nd.pass_hddt);
                    if (xml1.startsWith("ERR:") || !xml1)
                    {
                        return "ERR: Không có thông tin hóa đơn đã thay thế !";
                    }
                    else
                    {
                        // XmlDocument doc2 = new XmlDocument();
                        // doc2.LoadXml(xml1);
                        // XmlNode xnList = doc2.SelectNodes("/Invoice/Content").Item(0);
                        // XmlDocument doc3 = new XmlDocument();
                        // doc3.LoadXml("<Content>" + xnList.InnerXml + "</Content>");
                        // objHoaDonDienTuEdit = (Content)ObjectToXML(doc3.InnerXml, typeof(Content));
                        xml1 = "<Content>" + xml1 + "</Content>";
                        objHoaDonDienTuEdit = convert.xml2json(xml, {compact: true});
                    }


                    let dsBDHDDT = [];
                    let row = {};

                    // BDHDDT_ID = getkey.GetKey("BDHDDT_ID");
                    // row.BDHDDT_ID = BDHDDT_ID;
                    row.MA_TT = vma_gd;
                    row.THONGTINHD_CU = objHoaDonDienTu;
                    row.THONGTINHD_MOI = objHoaDonDienTuEdit;
                    row.KIEU_BD = 2;

                    row.TONGTIEN = tong_hddt;
                    row.NGUOI_CN = tt_nd.ma_nd;
                    row.MAY_CN = tt_nd.may_cn;
                    row.NHANVIEN_ID = tt_nd.nhanvien_id;
                    row.DONVI_ID = tt_nd.donvi_id;
                    row.NGAY_CN = tt_nd.ngay_cn;
                    row.GHICHU = "Thay thế hóa đơn điện tử tự động PTTB";
                    row.HDKH_ID = vhdkh_id;
                    row.PHIEUTT_ID = vphieutt_id;
                    row.LOAIHOADON_CU = JSON.parse(objHoaDonDienTu).InvoicePattern;
                    row.LOAIHOADON_MOI = JSON.parse(objHoaDonDienTuEdit).InvoicePattern;
                    row.SERI_CU = JSON.parse(objHoaDonDienTu).SerialNo;
                    row.SERI_MOI = JSON.parse(objHoaDonDienTuEdit).SerialNo;
                    row.SO_HD_CU = JSON.parse(objHoaDonDienTu).InvoiceNo;
                    row.SO_HD_MOI = JSON.parse(objHoaDonDienTuEdit).InvoiceNo;
                    row.SERI_MOI_ID = seri_id;
                    row.TRANGTHAI = 1;
                    row.BDHDDT_CHA_ID = bdhddt_cha_id;

                    row.HINHTHUC_TT = "Tiền mặt/Chuyển khoản";
                    row.TIEN = this.tt_hddt.tongtien_hddt;
                    row.TIENBANGCHU = this.tt_hddt.tbc;
                    row.TYLE_VAT = 10;
                    row.VAT = this.tt_hddt.tongvat_hddt;

                    // 4.7 ecms.Insert(dsBDHDDT, DatabaseConstants.DB6);
                    dsBDHDDT.push(row);
                    await this.axios.post('/web-hopdong/thaydoiloaihinhtb/sp_insert_bd_hddt', {
                      p_js_bd_hddt: JSON.stringify(dsBDHDDT)
                    });

                    // 4.8
                    // TaoDuLieuBDCT(product);
                    // ecms.Insert(dsBDHDDTCT, DatabaseConstants.DB6);
                    let dsBDHDDTCT = [...this.TaoDuLieuBDCT(product)];
                    await this.axios.post('/web-hopdong/thaydoiloaihinhtb/sp_insert_bd_hddt_ct', {
                      p_js_bd_hddt: JSON.stringify(dsBDHDDTCT)
                    });


                    // string str_update = " Update " + DatabaseConstants.DB2 + ".phieutt_hd ";
                    // str_update += " set hddt_fkey = '" + fkey_new + "', ";
                    // str_update += " loaihoadon_id = '" + loaihoadon_id + "',";
                    // str_update += " seri_id = '" + seri_id + "',";
                    // str_update += " seri = '" + seri + "',";
                    // str_update += " soseri = '" + sohoadon + "'";
                    // str_update += " Where phieutt_id  = " + vphieutt_id;
                    // new BangThamSo().ExeSQL(str_update);
                    await this.axios.post('/web-hopdong/thaydoiloaihinhtb/sp_capnhat_phieutt_hd_v2', {
                      p_hddt_fkey: fkey_new,
                      p_loaihoadon_id: loaihoadon_id,
                      p_seri_id: seri_id,
                      p_seri: seri,
                      p_soseri: sohoadon,
                      p_phieutt_id: vphieutt_id,
                    });

                    // str_update = "";
                    // str_update += " Update " + DatabaseConstants.DB6 + ".bd_hddt ";
                    // str_update += " Set trangthai = 2, ";
                    // str_update += "     ngay_td = sysdate, ";
                    // str_update += "     nguoi_td = '" + tt_nd.ma_nd + "'";
                    // str_update += " Where bdhddt_id = " + bdhddt_cha_id;
                    // bangts.ExeSQL(str_update);
                    await this.axios.post('/web-hopdong/thaydoiloaihinhtb/sp_capnhat_bd_hddt_theo_bdhddt_cha_id', {
                        p_bdhddt_cha_id: bdhddt_cha_id
                      });
                    Fkey = "";
                    fkey_new = "";
                    return "OK";
                }
                else
                {
                    //if (kq.StartsWith("ERR:20"))
                    //{
                    //    string lay_seri = invoice.getSerialByPattern_V2(tt_nd.user_hddt_td, tt_nd.pass_hddt_td, pattern);
                    //    if (lay_seri.StartsWith("ERR"))
                    //    {
                    //        return "ERR: Lỗi lấy seri điện tử ";
                    //    }
                    //    else
                    //    {
                    //        string[] result_s = lay_seri.Split(';');
                    //        seri = result_s[0].toString();
                    //        loi_het_seri = true;
                    //        goto kichhoat;
                    //    }
                    //}
                    //else
                    return "ERR: Có lỗi trong quá trình xuất hóa đơn điện tử: " + kq;
                }
            }
            catch (error)
            {
                return "ERR: " + error;
            }
        }
        else if (vthaotac == 5) // Hủy hóa đơn
        {
            return "ERR";
        }
        else if (vthaotac == 6) // Xuất hóa đơn
        {
            // #region Xuất hóa đơn
            try
            {
                
                this.tt_hddt.tongtien_hddt = 0;
                this.tt_hddt.tongvat_hddt = 0;
                this.tt_hddt.tong_hddt = 0;

                let sohoadon = "",
                pattern = "",
                seri = "",
                loaihoadon_id = 0,
                seri_id = 0;

                let sp_tt_loaihoadon_seri = await this.axios.get('/web-hopdong/thaydoiloaihinhtb/sp_tt_loaihoadon_seri');

              let ds_hd_seri = [];
              ds_hd_seri = sp_tt_loaihoadon_seri.data.data;
              if (ds_hd_seri.length > 0)
              {
                loaihoadon_id = Number(ds_hd_seri[0]["loaihoadon_id"]);
                pattern = ds_hd_seri[0]["ten_loaihd"];
                seri_id = Number(ds_hd_seri[0]["seri_id"]);
                seri = ds_hd_seri[0]["seri"];
              }
              else
              {
                return "ERR: Không tìm thấy loại hóa đơn, seri !";
              }

                // let kq1 = invoice.UpdateCus_V2(
                //   GetInvoiceXml_Cus(vten_kh, vma_gd, vmst, vdiachi_kh, vten_nh, vstk, vemail, vsodt, vloai_kh, vfax), 
                //   tt_nd.user_hddt, tt_nd.pass_hddt, 0);
                let xmlCusData = this.GetInvoiceXml_Cus(vten_kh, vma_gd, vmst, vdiachi_kh, vten_nh, vstk, vemail, vsodt, vloai_kh, vfax);
                let UpdateCus_V2 = await this.axios.post('/tichhop/vnptinvoice/updateCus', {
                  convert: 0,
                  xmlCusData: xmlCusData
                });
                let kq1 = UpdateCus_V2.data.data;
                if (kq1 != "ok")
                {
                    return "ERR: Có lỗi trong quá trình cập nhật thông tin khách hàng : " + kq1;
                }

                // let kq = invoice.ImportAndPublishInv_V2(tt_nd.user_hddt_td, tt_nd.pass_hddt_td,
                //     GetInvoiceXml(vten_kh, vma_gd, vmst, vdiachi_kh, vstk, vsodt, vhttt, product),
                //     tt_nd.user_hddt, tt_nd.pass_hddt, pattern, seri, 0);
                let xmlInvData = this.GetInvoiceXml(vten_kh, vma_gd, vmst, vdiachi_kh, vstk, vsodt, vhttt, product);
                let importAndPublishInv = await this.axios.post('/tichhop/vnptinvoice/importAndPublishInv', {
                  acPass: this.tt_nd.pass_hddt_td,
                  account: this.tt_nd.user_hddt,
                  convert: 0,
                  pattern: pattern,
                  serial: seri,
                  xmlInvData: xmlInvData
                });
                let kq = importAndPublishInv.data.data ? importAndPublishInv.data.data : [];
                let result = kq.split(':');
                if (result[0] == "OK")
                {
                    let result1 = result[1].toString().split(';');
                    let InvoicePattern1 = result1[0].toString();
                    let result2 = result1[1].toString().split('-');
                    let SerialNo1 = result2[0].toString();
                    let result3 = result2[1].toString().split('_');
                    let InvoiceNo1 = result3[1].toString();

                    let kq2 = "";
                    // kq2 = invoice.confirmPaymentFkey_V2(Fkey, tt_nd.user_hddt, tt_nd.pass_hddt, InvoicePattern1, SerialNo1);
                    let confirmPaymentFkey = await this.axios.post('/tichhop/vnptinvoice/confirmPaymentFkey', {
                      fKey: this.tt_hddt.Fkey,
                      convert: 0,
                      pattern: pattern,
                      serial: seri
                    });
                    kq2 = confirmPaymentFkey.data.data;
                    if (kq2 != "OK:")
                    {
                        return "ERR: Lỗi thanh toán HĐĐT : " + kq2;
                    }

                    if (loi_het_seri)
                    {
                        // code insert vào 2 bảng seri_hd và loaihoadon_seri
                        // update thứ tự, trạng thái vào bảng seri_hd
                    }

                    // let str_update = " Update " + DatabaseConstants.DB2 + ".phieutt_hd ";
                    // str_update += " set hddt_fkey = '" + Fkey + "', ";
                    // str_update += " loaihoadon_id = '" + loaihoadon_id + "',";
                    // str_update += " seri_id = '" + seri_id + "',";
                    // str_update += " seri = '" + SerialNo1 + "',";
                    // str_update += " soseri = '" + InvoiceNo1 + "',";
                    // str_update += " hddt_status = '" + kq2 + "'";
                    // str_update += " Where phieutt_id  = " + vphieutt_id;
                    // new BangThamSo().ExeSQL(str_update);
                    await this.axios.post('/web-hopdong/thaydoiloaihinhtb/sp_capnhat_phieutt_hd_v2', {
                        p_hddt_fkey: fkey_new,
                        p_loaihoadon_id: loaihoadon_id,
                        p_seri_id: seri_id,
                        p_seri: seri,
                        p_soseri: sohoadon,
                        p_phieutt_id: vphieutt_id,
                      });

                    let invToken = InvoicePattern1 + ";" + SerialNo1 + ";" + InvoiceNo1;
                    // let xml = invoice.downloadInvNoPay_V2(invToken, tt_nd.user_hddt, tt_nd.pass_hddt);
                    let downloadInvNoPay = await this.axios.post('/tichhop/vnptinvoice/downloadInvNoPay',
                    {
                      invToken: invToken
                    });
                    let xml = downloadInvNoPay.data.data;
                    if (xml.startsWith("ERR:") || !xml)
                        return "ERR: Có lỗi cập nhật biến động hóa đơn điện tử: " + xml;
                    else
                    {
                        // XmlDocument doc = new XmlDocument();
                        // doc.LoadXml(xml);
                        // XmlNode xnList = doc.SelectNodes("/Invoice/Content").Item(0);
                        // XmlDocument doc1 = new XmlDocument();
                        // doc1.LoadXml("<Content>" + xnList.InnerXml + "</Content>");
                        // objHoaDonDienTu = (Content)ObjectToXML(doc1.InnerXml, typeof(Content));
                        // objHoaDonDienTuEdit = (Content)ObjectToXML(doc1.InnerXml, typeof(Content));
                        xml1 = "<Content>" + xml1 + "</Content>";
                        objHoaDonDienTu = convert.xml2json(xml, {compact: true});
                        objHoaDonDienTuEdit = convert.xml2json(xml, {compact: true});
                    }

                    this.tt_hddt.dsBDHDDT = [];
                    let row ={};
                    // BDHDDT_ID = new GetKeyFacade().GetKey("BDHDDT_ID");
                    // row.BDHDDT_ID = BDHDDT_ID;
                    row.MA_TT = objHoaDonDienTuEdit.CusCode;
                    row.KIEU_BD = 5;
                    row.THONGTINHD_CU = convert.json2xml(objHoaDonDienTu);
                    row.THONGTINHD_MOI = convert.json2xml(objHoaDonDienTuEdit);
                    row.TONGTIEN = this.tt_hddt.tong_hddt;
                    row.NGUOI_CN = this.tt_nd.ma_nd;
                    row.MAY_CN = this.tt_nd.may_cn;
                    row.NGAY_CN = this.tt_nd.ngay_cn;
                    row.NHANVIEN_ID = this.tt_nd.nhanvien_id;
                    row.DONVI_ID = this.tt_nd.donvi_id;
                    row.GHICHU = "Tạo hóa đơn điện tử tự động PTTB";
                    row.HDKH_ID = vhdkh_id;
                    row.PHIEUTT_ID = vphieutt_id;
                    row.LOAIHOADON_CU = objHoaDonDienTu.InvoicePattern;
                    row.LOAIHOADON_MOI = objHoaDonDienTuEdit.InvoicePattern;
                    row.SERI_CU = objHoaDonDienTu.SerialNo;
                    row.SERI_MOI = objHoaDonDienTuEdit.SerialNo;
                    row.SO_HD_CU = objHoaDonDienTu.InvoiceNo;
                    row.SO_HD_MOI = objHoaDonDienTuEdit.InvoiceNo;
                    row.SERI_MOI_ID = seri_id;
                    row.TRANGTHAI = 1;

                    row.HINHTHUC_TT = "Tiền mặt/Chuyển khoản";
                    row.TIEN = this.tt_hddt.tongtien_hddt;
                    row.TIENBANGCHU = this.tt_hddt.tbc;
                    row.TYLE_VAT = 10;
                    row.VAT = this.tt_hddt.tongvat_hddt;

                    this.tt_hddt.dsBDHDDT.push(row);
                    // ecms.Insert(dsBDHDDT, DatabaseConstants.DB6);
                    // 4.7 ecms.Insert(dsBDHDDT, DatabaseConstants.DB6);
                    dsBDHDDT.push(row);
                    await this.axios.post('/web-hopdong/thaydoiloaihinhtb/sp_insert_bd_hddt', {
                      p_js_bd_hddt: JSON.stringify(dsBDHDDT)
                    });

                    // TaoDuLieuBDCT(product);
                    // ecms.Insert(dsBDHDDTCT, DatabaseConstants.DB6);
                    // 4.8
                    // TaoDuLieuBDCT(product);
                    // ecms.Insert(dsBDHDDTCT, DatabaseConstants.DB6);
                    let dsBDHDDTCT = [...this.TaoDuLieuBDCT(product)];
                    await this.axios.post('/web-hopdong/thaydoiloaihinhtb/sp_insert_bd_hddt_ct', {
                      p_js_bd_hddt: JSON.stringify(dsBDHDDTCT)
                    });

                    Fkey = "";
                    return "OK";
                }
                else
                {
                  return "ERR: Có lỗi trong quá trình xuất hóa đơn điện tử: " + kq;
                }
            }
            catch (error)
            {
                return "ERR: " + error;
            }
            // #endregion
        }
        else
        {
          return "Sai thao tác !";
        }
      } catch (error) {
        return "ERR: " + error;
      }
    },
    async GetInvoiceXml(i_ten_kh, i_ma_gd, i_mst, i_diachi_ct, i_stk, i_so_dt, i_httt, i_dt)
    {
        let str = '';
        let sinh_fkey = await this.axios.get('/web-ccdv/thanhtoan/sinh_fkey?kieu=' + i_kieu_dc);
        this.tt_hddt.Fkey = sinh_fkey.data.data;
        str+="<Invoices>";
        str+="<Inv>";
        str+="<key>" + this.tt_hddt.Fkey + "</key>";
        str+="<Invoice>";
        str+="<CusCode>" + i_ma_gd + "</CusCode>";
        str+="<CusName><![CDATA[" + i_ten_kh + "]]></CusName>";
        str+="<CusAddress><![CDATA[" + i_diachi_ct + "]]></CusAddress>";
        str+="<CusPhone>" + i_so_dt + "</CusPhone>";
        str+="<CusTaxCode><![CDATA[" + i_mst + "]]></CusTaxCode>";
        str+="<PaymentMethod>Tiền mặt/Chuyển khoản</PaymentMethod>";
        str+="<KindOfService></KindOfService>";
        str+="<Products>";
        for (let i = 0; i < i_dt.length; i++)
        {
            if (i_dt.Rows[i]["prodname"].toString() != "")
            {
                str+="<Product>";
                str+="<ProdName>" + i_dt.Rows[i]["prodname"].toString() + "</ProdName>";
                str+="<ProdUnit>" + i_dt.Rows[i]["produnit"].toString() + "</ProdUnit>";
                str+="<ProdQuantity>" + i_dt.Rows[i]["prodquantity"].toString() + "</ProdQuantity>";
                str+="<ProdPrice>" + Math.abs(Number(i_dt.Rows[i]["prodprice"].toString())) + "</ProdPrice>";
                str+="<Amount>" + Math.abs(Number(i_dt.Rows[i]["amount"].toString())) + "</Amount>";
                str+="</Product>";

                this.tt_hddt.tongtien_hddt += Number(i_dt.Rows[i]["tien"].toString());
                this.tt_hddt.tongvat_hddt += Number(i_dt.Rows[i]["vat"].toString());
                this.tt_hddt.tong_hddt += Number(i_dt.Rows[i]["tongtien"].toString());

            }
        }
        this.tt_hddt.tong_hddt = Math.Abs(tong_hddt);
        this.tt_hddt.tongvat_hddt = Math.Abs(tongvat_hddt);
        this.tt_hddt.tongtien_hddt = Math.Abs(tongtien_hddt);
        str.Append("</Products>");
        str.Append("<Total>" + this.tt_hddt.tongtien_hddt.toString() + "</Total>");
        str.Append("<DiscountAmount></DiscountAmount>");
        str.Append("<VATRate>10</VATRate>");
        str.Append("<VATAmount>" + this.tt_hddt.tongvat_hddt.toString() + "</VATAmount>");
        str.Append("<Amount>" + this.tt_hddt.tong_hddt.toString() + "</Amount>");

        let DocTien = "";
        let tienkt = "";
        tienkt = this.tt_hddt.tong_hddt.toString();
        if (this.tt_hddt.tong_hddt.toString().includes("-"))
            tienkt = tienkt.replace("-", "");
        // DocTien = CommonFunction.DocSo(tienkt, ',', "phẩy") + " đồng";
        let getMoneyTotext = await this.axios.post('/web-tracuu/tracuu_th/fn_doisosangchu', {
          money: Number(tienkt)
        });
        if(getMoneyTotext){
          DocTien = getMoneyTotext.data.data;
        }
        let TienBangChu = "";
        TienBangChu += DocTien.toString().toUpperCase();
        // for (let i = 1; i < DocTien.Length; i++)
        // {
        //     TienBangChu += DocTien[i];
        // }
        if (this.tt_hddt.tong_hddt.toString().includes("-"))
            this.tt_hddt.tbc = "Âm " + TienBangChu;
        else
            this.tt_hddt.tbc = TienBangChu;

        str+="<AmountInWords>" + tbc + "</AmountInWords>";
        str+="<Teller>" + tt_nd.ma_nv + "</Teller>";
        str+="</Invoice>";
        str+="</Inv>";
        str+="</Invoices>";
        return str;
    },
    async GetInvoiceXml_Cus(i_ten_kh, i_ma_gd, i_mst, i_diachi_kh, i_ten_nh,
            i_stk, i_email, i_sdt, i_loaikh, i_so_fax){
      try {
        let str = '';
        str+="<Customers>";
        str+="<Customer>";
        str+="<Name><![CDATA[" + i_ten_kh + "]]></Name>";
        str+="<Code>" + i_ma_gd + "</Code>";
        str+="<TaxCode><![CDATA[" + i_mst + "]]></TaxCode>";
        str+="<Address><![CDATA[" + i_diachi_kh + "]]></Address>";
        str+="<BankAccountName></BankAccountName>";
        if (i_ten_nh != "")
            str+="<BankName>" + i_ten_nh + "</BankName>";
        else
            str+="<BankName></BankName>"
        str+="<BankNumber>" + i_stk + "</BankNumber>"
        str+="<Email>" + i_email + "</Email>"
        str+="<Fax>" + i_so_fax + "</Fax>"
        str+="<Phone>" + i_sdt + "</Phone>"
        str+="<ContactPerson>" + i_ten_kh + "</ContactPerson>"
        str+="<RepresentPerson>" + i_ten_kh + "</RepresentPerson>"
        if (i_loaikh != "1")
            str+="<CusType>1</CusType>"
        else
            str+="<CusType>0</CusType>"
        str+="</Customer>"
        str+="</Customers>"
        return str;
      } catch (error) {
        
      }
    },
    // GetXMLFromObject(o)
    // {
    //     StringWriter sw = new StringWriter();
    //     XmlTextWriter tw = null;
    //     try
    //     {
    //         XmlSerializer serializer = new XmlSerializer(o.GetType());
    //         tw = new XmlTextWriter(sw);
    //         serializer.Serialize(tw, o);
    //     }
    //     catch (error)
    //     {
    //         //Handle Exception Code
    //     }
    //     finally
    //     {
    //         sw.Close();
    //         if (tw != null)
    //         {
    //             tw.Close();
    //         }
    //     }
    //     return sw.toString();
    // },
    async GetInvoiceXml_ThayThe( i_ma_gd, i_httt, i_dt, obj)
    {
      let fkey_new = await this.axios.get('/web-ccdv/thanhtoan/sinh_fkey?kieu=' + i_kieu_dc);
      let str = '';
      str += "<ReplaceInv>";
      str += "<key>" + fkey_new + "</key>";
      str += "<CusCode>" + i_ma_gd + "</CusCode>";
      str += "CusName<![CDATA[" + obj.CusName + "]]></CusName>";
      str += "CusAddress<![CDATA[" + obj.CusAddress + "]]></CusAddress>";
      str += "<CusPhone>" + obj.CusPhone + "</CusPhone>";
      str += "<CusTaxCode><![CDATA[" + obj.CusTaxCode + "]]></CusTaxCode>";
      str += "<PaymentMethod>Tiền mặt/Chuyển khoản</PaymentMethod>";
      str += "<KindOfService></KindOfService>";
      str += "<Products>";
      for (let i = 0; i < i_dt.length; i++)
      {
          if (i_dt[i]["prodname"].toString() != "")
          {
              str +="<Product>";
              str +="<ProdName>" + i_dt[i]["prodname"].toString() + "</ProdName>";
              str +="<ProdUnit>" + i_dt[i]["produnit"].toString() + "</ProdUnit>";
              str +="<ProdQuantity>" + i_dt[i]["prodquantity"].toString() + "</ProdQuantity>";
              str +="<ProdPrice>" + Math.abs(Number(i_dt[i]["prodprice"].toString())) + "</ProdPrice>";
              str +="<Amount>" + Math.abs(Number(i_dt[i]["amount"].toString())) + "</Amount>";
              str +="</Product>";

              this.tt_hddt.tongtien_hddt += Number(i_dt[i]["tien"].toString());
              this.tt_hddt.tongvat_hddt += Number(i_dt[i]["vat"].toString());
              this.tt_hddt.tong_hddt += Number(i_dt[i]["tongtien"].toString());
          }
      }
      this.tt_hddt.tong_hddt = Math.abs(tong_hddt);
      this.tt_hddt.tongvat_hddt = Math.abs(tongvat_hddt);
      this.tt_hddt.tongtien_hddt = Math.abs(tongtien_hddt);

      str+="</Products>";
      str+="<Total>" + tongtien_hddt.toString() + "</Total>";
      str+="<VATRate>10</VATRate>";
      str+="<VATAmount>" + tongvat_hddt.toString() + "</VATAmount>";
      str+="<Amount>" + tong_hddt.toString() + "</Amount>";

      let getMoneyTotext = await this.axios.post('/web-tracuu/tracuu_th/fn_doisosangchu', {
        money: Number(tienkt)
      });
      if(getMoneyTotext){
        DocTien = getMoneyTotext.data.data;
      }
      let TienBangChu = "";
      TienBangChu += DocTien.toString().toUpperCase();
      for (let i = 1; i < DocTien.Length; i++)
      {
          TienBangChu += DocTien[i];
      }
      if (this.tt_hddt.tong_hddt.toString().includes("-"))
          this.tt_hddt.tbc = "Âm " + TienBangChu;
      else
          this.tt_hddt.tbc = TienBangChu;

      str+="<AmountInWords>" + tbc + "</AmountInWords>";
      str+="<Teller>" + tt_nd.ma_nv + "</Teller>";
      str+="</ReplaceInv>";
      return str;
    },
    TaoDuLieuBDCT(tb_hddt){
      try {
        let dsBDHDDTCT = [];
        for (let i = 0; i < tb_hddt.length; i++)
        {
            if (tb_hddt[i]["PRODNAME"].toString() != "")
            {
                let row = {};
                row.BDHDDT_ID = BDHDDT_ID;
                row.DONGIA = Math.abs(Number(tb_hddt[i]["PRODPRICE"].toString()));
                row.DONVITINH = tb_hddt[i]["PRODUNIT"].toString().trim();
                row.NOIDUNG = tb_hddt[i]["PRODNAME"].toString().trim();
                row.SOLUONG = Number(tb_hddt[i]["PRODQUANTITY"].toString());
                row.STT = i + 1;
                row.THANHTIEN = Math.abs(Number(tb_hddt[i]["AMOUNT"].toString()));
                dsBDHDDTCT.push(row);
            }
        }
        return dsBDHDDTCT;
      } catch (error) {
        this.$toast.error(error);
      }
    },
    getPicked(e){
      // this.$refs.popupDieuChinh.showModal();
      // alert(e);
      return e;
    },
    async openPopup(){
      // this.$refs.popupDieuChinh.showModal();
      let downloadInvNoPay = await this.axios.post('/tichhop/vnptinvoice/downloadInvNoPay', {
        invToken: "1/001;K22TAA;00237245"
      });
      let xmlStr = downloadInvNoPay.data.data;
      // const parser = new DOMParser();
      // const doc = parser.parseFromString(xmlStr, "application/xml");
      // console.log('doc ', doc);
      // let value = doc.getElementsByTagName('NBan')[0].childNodes[0];
      // console.log('value ', value);
      let objectHoaDon = convert.xml2json(xmlStr, {compact: true});
      console.log('asdads ', JSON.parse(objectHoaDon));
    },
    async TaoDuLieu_CT_TIENHD(themmoi){
      try {
        let rowCTTHD = {};

            for (let i = 0; i < this.list_ChiTietTien.ds_tien_khoanmuc.length; i++)
            {
                rowCTTHD.HDTB_ID = this.list_TTThueBao.hopdongtbid;
                if (themmoi)
                {
                    let res = await this.axios.post('/web-ccdv/tien_trinh_bao_hong/get_keys', {
                      keyname: 'CT_TIENHD'
                    })
                    // new GetKeyFacade().GetKey("CT_TIENHD");
                    rowCTTHD.CT_TIENHD_ID = res.data.data ? Number(res.data.data) : 0
                }

                rowCTTHD.KHOANMUCTT_ID = Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["khoanmuctt_id"]);
                rowCTTHD.TIEN = Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["tien"]);
                rowCTTHD.VAT = Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["vat"]);
                if (Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["khoanmuctt_id"]) == KHOANMUC_TT.KMTT_KM_LAPDAT
                      || Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["khoanmuctt_id"]) == KHOANMUC_TT.KMTT_KM_THIETBI
                      || Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["khoanmuctt_id"]) == KHOANMUC_TT.KMTT_KM_DATCOC
                      || Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["khoanmuctt_id"]) == KHOANMUC_TT.KMTT_TIENTHOAI_DC_DOI_TD)
                {
                    rowCTTHD.TIEN = Math.abs(rowCTTHD.TIEN) * -1;
                    rowCTTHD.VAT = Math.abs(rowCTTHD.VAT) * -1;
                }
                rowCTTHD.ID = Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["khoanmuctt_id"]);
                rowCTTHD.LOAI_ID = 1;
                this.list_ChiTietTien.dsCTTHD.push(rowCTTHD);
            }
      } catch (error) {
        // this.$toast.error(error);
        console.log('4490:', error);
      }
    },
    kiemtra_khoanmuc_tien()
    {
      
        let drs_cu = this.list_ChiTietTien.ds_tien_khoanmuc_bd.filter(e => {if(Number(e.tien) > 0 && Number(e.vat) > 0) {return e}}) // Select("tien<>0 and vat<>0");
        let drs_moi = this.list_ChiTietTien.ds_tien_khoanmuc.filter(e => {if(Number(e.tien) > 0 && Number(e.vat) > 0) {return e}});
        //kiểm tra số bản ghi có tiền có = nhau không
        console.log('this.list_ChiTietTien.ds_tien_khoanmuc_bd', this.list_ChiTietTien.ds_tien_khoanmuc_bd);
        console.log('this.list_ChiTietTien.ds_tien_khoanmuc', this.list_ChiTietTien.ds_tien_khoanmuc);
        if (drs_cu.length != drs_moi.length)
        {
            return false;
        }
        let vtien_bd, vvat_bd, vtien_moi, vvat_moi;
        //Kiểm tra các khoản mục của 2 bên có khớp nhau không
        let kt = false;
        //Duyệt toàn bộ bảng mới
        //Kiểm tra các khoản mục của bảng mới có tồn tại bên bảng cũ không?
        drs_moi.forEach(drmoi =>
        {
            kt = false;
            drs_cu.every(drcu =>
            {
                vtien_bd = 0;
                vvat_bd = 0;
                vtien_moi = 0;
                vvat_moi = 0;
                if (drmoi["khoanmuctt_id"] == drcu["khoanmuctt_id"])
                {
                    vtien_moi = Number(drmoi["tien"]);
                    vvat_moi = Number(drmoi["vat"]);
                    vtien_bd = Number(drcu["tien"]);
                    vvat_bd = Number(drcu["vat"]);
                    if (vtien_bd == vtien_moi && vvat_bd == vvat_moi)
                    {
                        kt = true;
                        return false;
                    }
                }
            })
            //không tìm thấy khoản mục tương ứng trong bảng cũ trùng với khoản mục đang xét trong bảng mới
            if (kt == false) return false;
        });
        //Kiểm tra ngược lại
        //Kiểm tra các khoản mục của bảng cũ có tồn tại bên bảng mới không?
        drs_cu.forEach(drcu =>
        {
            kt = false;
            drs_moi.every(drmoi =>
            {
                vtien_bd = 0;
                vvat_bd = 0;
                vtien_moi = 0;
                vvat_moi = 0;
                if (drmoi["khoanmuctt_id"].toString() == drcu["khoanmuctt_id"].toString())
                {
                    vtien_moi = Number(drmoi["tien"]);
                    vvat_moi = Number(drmoi["vat"]);
                    vtien_bd = Number(drcu["tien"]);
                    vvat_bd = Number(drcu["vat"]);
                    if (vtien_bd == vtien_moi && vvat_bd == vvat_moi)
                    {
                        kt = true;
                        return false;
                    }
                }
            })
            //không tìm thấy khoản mục tương ứng trong bảng mới trùng với khoản mục đang xét trong bảng cũ
            if (kt == false) return false;
        });
        return kt;
    },
    async clickGoiDaDV(){
      // this.$alert("Ấn “Gói đa DV” để thực hiện ghép gói cho tốc độ mới (nếu có) - Tham chiếu UR2.1.004", "Thông báo", {
      //   dangerouslyUseHTMLString: true,
      //   confirmButtonText: "OK",
      //   type: "info",
      // });

    },
    btnOpenfrmDangKyGoiDaDVLM(){ 
      this.$refs.frmDangKyGoiDaDVLM.showDialog(this.listTT_Phieu_YC.magiaodich);
      // this.$router.push('../admin/frmDangKyGoiDaDVLM')
    },
    async clickInPL(){
      try{
        // lấy dshd theo ma gd 
        if(this.listTT_Phieu_YC.magiaodich){
          // let res = await this.axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd', {
          //   in_loaihd_id: 8,
          //   in_ma_gd: this.listTT_Phieu_YC.magiaodich,
          //   in_tthd_id: 1
          // });
          // if(res.data.error_code != "BSS-00000000"){
          //   this.$toast.warning(res.data.message)
          // }else{
          //   // let tem = {};
          //   this.param.hdkh_id = res.data.data[0].hdkh_id;// this.list_TTKhachHang.hopdongkhid
          //   this.param.hdtb_id = res.data.data[0].hdtb_id;// this.list_TTThueBao.hopdongtbid
          //   this.param.ma_gd = res.data.data[0].ma_gd; // this.listTT_Phieu_YC.magiaodich
          //   this.param.ngay_yc = res.data.data[0].ngay_yc.slice(0,10).split('-').reverse().join('/');// this.listTT_Phieu_YC.ngay_yc
          //   this.param.ngaylap_hd = res.data.data[0].ngaylap_hd.slice(0,10).split('-').reverse().join('/');// this.listTT_Phieu_YC.ngayLapHD
          //   this.$refs.popupInHopDong.showModal();
          // }
          
          this.param.hdkh_id =this.list_TTKhachHang.hopdongkhid
          this.param.hdtb_id = this.list_TTThueBao.hopdongtbid
          this.param.ma_gd = this.listTT_Phieu_YC.magiaodich
          this.param.ngay_yc = moment(this.listTT_Phieu_YC.ngayyeucau).format("DD/MM/YYYY"); // .slice(0,10).split('-').reverse().join('/');// this.listTT_Phieu_YC.ngay_yc
          this.param.ngaylap_hd = moment(this.listTT_Phieu_YC.ngayLapHD).format("DD/MM/YYYY");// this.listTT_Phieu_YC.ngayLapHD
          this.$refs.popupInHopDong.showModal();
        }else{
          this.$alert("Chưa có thông tin hợp đồng");
        }
      }catch(e){
        console.log(e);
      }
    },
    async clickThanhToan(){
     // this.$refs.tienhopdong.show()
       this.popupComponent = Payment;
      this.Popup("popupComponents");
       this.popupComponentName = "popupPayment";
    },
    async clickXoaTB(){
        this.$alert("Bạn thật sự muốn xóa thuê bao không ?", "Thông báo", {
        dangerouslyUseHTMLString: true,
        showCancelButton: true,
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        showCloseButton: true,
        type: "warning",
      }).then(async (v) => {
          if (!v) {return;}
          this.XoaHDThueBao(1, 1851924);
        });
    },
    async clickHenLD(){
    this.$refs.popupThongTinHenKhachHang.showModal()
    },
    async clickLienHe(){
     this.$refs.thongtinlienhe.show()
    },
    thongTinEmailOpen() {
       this.$refs.thongtinemail.show()
    },
    async MAP_ID(type, param){
      try {
        let res = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_muccuoc_tb', {
          // id_neo: id_neo,
          // table: table,
          // dk: dk
          type: type,
          param: param
        });
        return res.data.data;
      } catch (error) {
        console.log(error);
      }
    },
    async clickThongTinTC(){
      // this.$alert("[UR2.1.004] - [Lập hợp đồng]: Lắp đặt mới. Mục 1.4.15", "Thông báo", {
      //   dangerouslyUseHTMLString: true,
      //   confirmButtonText: "OK",
      //   type: "info",
      // });
      try {
        if (!this.list_TTThueBao.muccuoctbmoi && !this.list_TTThueBao.muccuoctbmoi.length)
        {
            this.$toast.warning("Chưa có mức cước thuê bao. Bạn hãy kiểm tra lại!");
            return;
        }
        let cuoc_tb, cuockhoan, cuoc_tg, cuoc_ll = "0";
        cuoc_tb =  await this.MAP_ID(1, this.list_TTThueBao.muccuoctbmoi);
        cuockhoan = await this.MAP_ID(2, this.list_TTThueBao.muccuoctbmoi);
        cuoc_tg = await this.MAP_ID(3, this.list_TTThueBao.muccuoctbmoi);
        cuoc_ll = await this.MAP_ID(4, this.list_TTThueBao.muccuoctbmoi);
        if (cuoc_tb == "-1") cuoc_tb = "0";
        if (cuockhoan == "-1") cuockhoan = "0";
        if (cuoc_tg == "-1") cuoc_tg = "0";
        if (cuoc_ll == "-1") cuoc_ll = "0";

        var dulieu = {};
        dulieu.loaitb_id = Number(this.listTT_Phieu_YC.loaihinh);
        dulieu.thongtintc = this.list_TTKhachHang.thongtintc;
        dulieu.cuoc_tb = cuoc_tb;
        dulieu.cuoc_tg = cuoc_tg;
        dulieu.cuockhoan = cuockhoan;
        dulieu.cuoc_ll = cuoc_ll;
        if (this.list_TTThueBao.muccuoctbmoi)
            dulieu.muccuoctb_id = Number(this.list_TTThueBao.muccuoctbmoi);
        else
            dulieu.muccuoctb_id = 0;
        if (Number(this.listTT_Phieu_YC.dichvuvt) == 4) // this.DichVuVienThong.ADSL)
        {
          let body = {
            MUCCUOC_ID: dulieu.muccuoctb_id,
            DICHVUVT_ID: 4,
            TOCDO_ID: this.list_TTThueBao.tocdomoi
          }
          let res = await this.axios.post('/web-hopdong/TocDoMucCuoc/lay_thongtintc_frmbcthaydoitocdoadsl', {
            ds_para: JSON.stringify(body)
          });
          let _dt = res.data.data ? res.data.data : []
          // DataTable _dt = bangts.getDataFromSQL("select sl_ip_mp from " + DatabaseConstants.DB2 + ".tocdo_adsl where tocdo_id = " + cboTocDoADSL.EditValue.toString() + " and sl_ip_mp is not null", "ds").Tables[0];
          if (_dt.length > 0)
          {
              dulieu.mienip = Number(_dt[0]);
          }
        }
        // dulieu.loai = loaikm;
        // dulieu.dt = loaikm==0?this.list_TTThueBao.khuyenmai_options:this.list_TTThueBao.tratruoc_options;
        this.popupComponentData = dulieu;
        this.$refs.popupThongTinTC.show();
      } catch (error) {
        console.log(error);
      }
    },
    formCloseThongTinTC(){
      this.$refs.popupThongTinTC.hide();
    },
     //#region Popup search account
    ShowSearchAccount() {
      this.$refs.popupSearchAccount.show();
      // this.popupComponent = SearchAccount;
      // dataSearchAccount.isPopup = true;
      // dataSearchAccount.vma_tb = this.list_TTThueBao.mathuebao;
      // dataSearchAccount.vdichvvut_id = this.listTT_Phieu_YC.dichvuvt;
      // dataSearchAccount.vISKHDN = false;

      // this.Popup("popupComponents");
      this.popupComponentName = "popupSearchAccount";
    },
    async ShowDSThueBao(){
      if(this.list_TTThueBao.mathuebao.length>0){
        if(this.list_TTKhachHang.khachhangid != 0){
          this.$refs.tracuuthuebao.showModal(
          this.list_TTKhachHang.khachhangid,
          this.listTT_Phieu_YC.dichvuvt,
          this.listTT_Phieu_YC.magiaodich
          );
        }else{
           let data = await this.axios.post("/web-hopdong/khoiphucthanhly/lay_biendong_theo_hdtb_id",
            {
              hdtb_id:this.list_TTThueBao.hopdongtbid,
            }
          );
          if (data.data && data.data.data.length > 0) {
            this.$refs.tracuuthuebao.showModal(
              data.data.data[0].khachhang_id,
              this.listTT_Phieu_YC.dichvuvt,
              this.listTT_Phieu_YC.magiaodich
            );
          } else {
           this.$alert("Chưa có thông tin về khách hàng thay đổi tốc độ có thuê bao: "+this.list_TTThueBao.mathuebao +" Kiểm tra lại !", "Thông báo", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "info",
            });
          }
        }
      }else{
        this.$alert("Bạn chưa nhập mã thuê bao!!!", "Thông báo", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "info",
            });
      }
      
    },
   /*  Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
    }, */
    //#endregion
    //#region popup CTV, NguoiGT
    ShowPopupCTV() {
      if(this.filterbox.ctv.ischecked) {
        this.popupComponent = ()=>import('@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien');
        this.popupComponentName = "popupCTV";
        this.Popup('popupComponents');
        this.title = 'Chọn công tác viên'
      }
    },
    ShowPopupNguoiGT(){
      if(this.filterbox.nguoigt.ischecked) {
        this.popupComponent = ()=>import('@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu');
        this.popupComponentName = "popupNGT";
        this.Popup('popupComponents');
        this.title = 'Chọn người giới thiệu'
      }
    },
    popupClosed(val) {
      switch(this.popupComponentName) {
        case "popupSearchAccount":
          if(val){
            this.list_TTThueBao.mathuebao = val.ma_tb;
          }
          this.$refs.popupSearchAccount.hide();
         break;
       
        case "popupCTV":
          this.filterbox.ctv.value = val.ten_ht;
          this.filterbox.ctv.ctv_id = val.nhanvien_id;
          break;
        case "popupNGT":
          this.filterbox.nguoigt.value = val.ten_ht;
          this.filterbox.nguoigt.nguoigt_id = val.nhanvien_id;
          break;
        case "popup-Dangkychitietkhuyenmai":
          console.log('close popup chi tiết  khuyến mại: ', val);
          if(val){
            this.dsKM_HDTB = [...val.dsKM_HDTB]
          }
          // this.$refs.popup-Dangkychitietkhuyenmai.hide();
          break;
        case "popup-DangkychitietkhuyenmaiTT":
          console.log('close popup chi tiết  trar truocws: ', val);
          if(val){
            this.dsDC_HDTB = [...val.dsDC_HDTB]
          }
          // this.$refs.popup-DangkychitietkhuyenmaiTT.hide();
          break;
        case "popup-ChonKhuyenMai":
          // debugger
          console.log('close popup khuyến mại: ', val);
          if(val){
            if(val.loaikm==0)
              this.list_TTThueBao.khuyenmai= val.khuyenmai;
            else 
              this.list_TTThueBao.tratruoc = val.khuyenmai;
          }
          this.$refs.popupKM.hide();
          break;  
        case "popupTienKM":
          if(val){
            this.list_ChiTietTien.ds_tien_khoanmuc = val;
              var dtien = 0;
              var dvat = 0;
              var dtongtien = 0;
             for (var i = 0; i < this.list_ChiTietTien.ds_tien_khoanmuc.length; i++)
              {
                dtien += Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["tien"]);
                dvat += Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["vat"]);
                dtongtien += Number(this.list_ChiTietTien.ds_tien_khoanmuc[i]["tongtien"]);
              }
              this.list_ChiTietTien.tien = dtien;
              this.list_ChiTietTien.tienvat = dvat;
               this.list_ChiTietTien.tongtien = dtongtien;
          }
          break;    
      }
      this.ClosePopup('popupComponents');
    },
 //#endregion
 //#region Popup mã giao dịch
    showPopupSearchContract(){   
      this.$refs.popupSearchContract.showModal();
    },
    acceptSearchContract(item){
      this.listTT_Phieu_YC.magiaodich = item.ma_gd;
      if(this.listTT_Phieu_YC.magiaodich && this.listTT_Phieu_YC.magiaodich.trim()!="")
        this.MaGDChanged();
    },
    async LAY_DS_HOPDONG_THEO_MA_GD(ma_gd, loaihd_id, tthd_id){
      try {
        let res = await this.axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd', {
          in_loaihd_id: loaihd_id,
          in_ma_gd: ma_gd,
          in_tthd_id: tthd_id
        });
        if(res.data.error_code == 'BSS-00000000')
          return res.data.data;
        else {
          this.$toast.warning(res.data.message);
          return [];
        }
      } catch (error) {
        console.log(error);
      }
    },
    // txtMaGD_KeyPress
    async MaGDChanged(){
      try {
        if(this.listTT_Phieu_YC.magiaodich){
          // LoaiHopDong.THAY_DOI_TOCDO_ADSL = 8
          let res = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.listTT_Phieu_YC.magiaodich, 8, 1);
          this.HienThiTTHopDongKH(res);
        }
      } catch (error) {
        console.log(error);
      }
    },
 //#endregion
 //#region popup khuyến mại, trả trước
    async ShowPopupChonKhuyenMai(loaikm) {
      var dulieu = {};
      dulieu.loai = loaikm;
      dulieu.dt = loaikm==0 ? await this.list_TTThueBao.khuyenmai_options.map(e => {
        return {
          khuyenmai_id: e.id
        }
      }): await this.list_TTThueBao.tratruoc_options.map(f => {
        return {
          khuyenmai_id: f.id
        }
      });
      console.log('dulieu.dt ', dulieu.dt);
      this.popupComponentData = dulieu;
      this.$refs.popupKM.show();
      this.popupComponentName = "popup-ChonKhuyenMai";
      // this.popupComponent = ()=>import('@/modules/contract/setup/InstallNewSubs/Popups/popup-ChonKhuyenMai');
      // this.Popup('popupComponents');
      // this.title = '';
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
    },
    async btnChiTietTraTruoc_Click() {
      try
      {
        //TODO bo qua vi khong phai phan co dinh
        // if (this.listbox.dichvu_vt.value == DichVuVienThong.ADSL || this.listbox.dichvu_vt.value == DichVuVienThong.ANTOAN_BAOMAT_TT
        //     || this.listbox.dichvu_vt.value == DichVuVienThong.DICHVU_CNTT || this.listbox.dichvu_vt.value == DichVuVienThong.TRUNGTAM_DULIEU
        //     || this.listbox.dichvu_vt.value == DichVuVienThong.HOINGHI_TRUYENHINH)
        // {
        //     if (cboGoiCuocAdsl.EditValue != null && cboGoiCuocAdsl.EditValue != DBNull.Value && cboGoiCuocAdsl.EditValue.toString() != "")
        //         vtocdotn_id = vgoicuocasdl;
        //     if (Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.INTERNET_ADSL || Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.INTERNET_FTTH
        //           || Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.WIFI_FIBER)
        //     {
        //         if (cboMucCuoc.SelectedValue != null)
        //             vmuccuoctn_id = Convert.ToInt32(cboMucCuoc.SelectedValue);
        //     }
        // }
        // else if (dichvuvt_id == DichVuVienThong.MEGAWAN || dichvuvt_id == DichVuVienThong.METRONET)
        //     vtocdotn_id = Convert.ToInt32(cboTocDo.EditValue);
        // else if (dichvuvt_id == DichVuVienThong.TSL)
        //     vtocdotn_id = Convert.ToInt32(cboTocDoKenhTSL.EditValue);

        //Hiếu bổ sung thêm hàm lấy tiền khuyến mại 23.09.2010

        var khuyenmai_id_tt = this.list_TTThueBao.tratruoc;
        var kt = -1;
        var ngayyeucau = moment(this.listTT_Phieu_YC.ngayyeucau).format("DD/MM/YYYY");
        await API.get_kiemTraThoiHanKM(this.axios, { "khuyenMaiId": this.list_TTThueBao.tratruoc, "ngayYc": ngayyeucau?ngayyeucau:null }).then((response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            kt = (response.data.data);
          }
        });
        if (kt == 0)
        {
          var ngay_kt_km = "";
          await API.get_lay_ngaykt_km(this.axios, { "km_id":this.list_TTThueBao.tratruoc }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              ngay_kt_km = (response.data.data);
            }
          });
          var tenkm = this.list_TTThueBao.tratruoc_options.filter(x=>x.khuyenmai_id==this.list_TTThueBao.tratruoc)[0].ten_km;
          await this.$bvModal.msgBoxConfirm('Xác nhận hành động', {
            title: "Khuyến mại " + tenkm + " đã hết thời hạn từ ngày " + ngay_kt_km + ".\nBạn có muốn tiếp tục chọn chi tiết khuyến mại?",
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy',
          }).then(async v => {
            if(!v) {
              //FIXME khong ro dung lam gi, code cu gan nhu ko co tac dung
              this.kmld.tien_kmld_tt = 0;
              this.kmld.vat_kmld_tt = 0;
              this.kmld.tyle_kmld = 0;
              this.kmld.tong_kmld_tt = 0;
              this.kmld.dtiendatcoc_tt = 0;
              this.kmld.dvatdatcoc_tt = 0;
            }
            else 
              kt = 1;
          });
          if(kt!=1) return;
        }
        // Vinh[v zem lai sualoi anh Long
        this.popupComponent = ()=>import('@/modules/contract/setup/InstallNewSubs/Popups/popup-Dangkychitietkhuyenmai');
        this.popupComponentName = "popup-DangkychitietkhuyenmaiTT";
        this.popupComponentData = {
          khuyenmai_id:khuyenmai_id_tt,vchuyenmang:0,
          _hdtb_id:this.list_TTThueBao.hopdongtbid, 
          vdichvuvt_id:this.listTT_Phieu_YC.dichvu, 
          m_dsThueBao_DichVu:0, 
          dsDC_HDTB: this.dsDC_HDTB,
          vtocdotn_id:0,
          vmuccuoctn_id:0,
          loaihinh_tb:this.listTT_Phieu_YC.loaihinh,
          kieu_km:1
        } // 0: khuyen mai, 1 tra truoc
        this.Popup('popupComponents');

      }
      catch (ex)
      {
          this.$toast.error("" + ex);
      }
    },
    async btnChiTietKM_Click() {
      //TODO tam bo qua vi khong lien quan den co dinh
      // if (this.listbox.dichvu_vt.value == DichVuVienThong.ADSL || this.listbox.dichvu_vt.value == DichVuVienThong.ANTOAN_BAOMAT_TT
      //     || this.listbox.dichvu_vt.value == DichVuVienThong.DICHVU_CNTT || this.listbox.dichvu_vt.value == DichVuVienThong.TRUNGTAM_DULIEU
      //     || this.listbox.dichvu_vt.value == DichVuVienThong.HOINGHI_TRUYENHINH || this.listbox.dichvu_vt.value == DichVuVienThong.BRCD_DOITAC)
      // {
      //     if (cboGoiCuocAdsl.EditValue != null && cboGoiCuocAdsl.EditValue != DBNull.Value && cboGoiCuocAdsl.EditValue.toString() != "")
      //         vtocdotn_id = vgoicuocasdl;
      //     if (Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.INTERNET_ADSL || Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.INTERNET_FTTH
      //           || Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.WIFI_FIBER)
      //     {
      //         if (cboMucCuoc.SelectedValue != null)
      //             vmuccuoctn_id = Convert.ToInt32(cboMucCuoc.SelectedValue);
      //     }
      // }
      // else if (this.listbox.dichvu_vt.value == DichVuVienThong.MEGAWAN || this.listbox.dichvu_vt.value == DichVuVienThong.METRONET || this.listbox.dichvu_vt.value == DichVuVienThong.MGW_DOITAC)
      //     vtocdotn_id = Convert.ToInt32(cboTocDo.EditValue);
      // else if (this.listbox.dichvu_vt.value == DichVuVienThong.TSL || this.listbox.dichvu_vt.value == DichVuVienThong.TSL_DOITAC)
      //     vtocdotn_id = Convert.ToInt32(cboTocDoKenhTSL.EditValue);

      //Hiếu bổ sung thêm hàm lấy tiền khuyến mại 23.09.2010 
      var khuyenmai_id = this.list_TTThueBao.khuyenmai;
      var kt = -1;
       var ngayyeucau = moment(this.listTT_Phieu_YC.ngayyeucau).format("DD/MM/YYYY");
      await API.get_kiemTraThoiHanKM(this.axios, { "khuyenMaiId": khuyenmai_id, "ngayYc":ngayyeucau?ngayyeucau:null }).then((response) => {
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          kt = (response.data.data);
        }
      });
      if (kt == 0)
      {
          var ngay_kt_km = "";
          await API.get_lay_ngaykt_km(this.axios, { "km_id": khuyenmai_id }).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              ngay_kt_km = (response.data.data);
            }
          });
          var tenkm = this.list_TTThueBao.khuyenmai_options.filter(x=>x.khuyenmai_id==this.list_TTThueBao.khuyenmai)[0].ten_km;
          await this.$bvModal.msgBoxConfirm('Xác nhận hành động', {
            title: "Khuyến mại " + tenkm + " đã hết thời hạn từ ngày " + ngay_kt_km + ".\nBạn có muốn tiếp tục chọn chi tiết khuyến mại?",
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy',
          }).then(async v => {
            if(!v) {
              //FIXME khong ro dung lam gi, code cu gan nhu ko co tac dung
              this.kmld.dtienkmld = 0;
              this.kmld.dvatkmld = 0;
              this.kmld.tyle_kmld = 0;
              this.kmld.tong_kmld = 0;
              this.kmld.tien_datcoc = 0;
            }
            else 
              kt = 1;
          });
          if(kt!=1) return;
      }
      //VinhPV xem lai sua loi cho anh Long
      this.popupComponent = ()=>import('@/modules/contract/setup/InstallNewSubs/Popups/popup-Dangkychitietkhuyenmai');
      this.popupComponentName = "popup-Dangkychitietkhuyenmai";// 0: khuyen mai, 1 tra truoc
      this.popupComponentData = {
        khuyenmai_id:khuyenmai_id,vchuyenmang:0,
        _hdtb_id:this.list_TTThueBao.hopdongtbid, 
        vdichvuvt_id:this.listTT_Phieu_YC.dichvu, 
        m_dsThueBao_DichVu:0, 
        dsDC_HDTB: this.dsDC_HDTB,
        vtocdotn_id:0,
        vmuccuoctn_id:0,
        loaihinh_tb:this.listTT_Phieu_YC.loaihinh,
        kieu_km:0} // 0: khuyen mai, 1 tra truoc
        this.Popup('popupComponents');
    },
 //#endregion
 //#region popup chi tiết tiền khuyến mại
    async popupTienKM() {
      var ds = [];
      await API.post_lay_khoanmuc_tt_theo_loaihd(this.axios, { "vloaihd_id": 8 }).then((response) => {
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          ds = response.data.data;
          if(this.list_ChiTietTien.ds_tien_khoanmuc && this.list_ChiTietTien.ds_tien_khoanmuc.length>0)
            for(var item of ds) {
              var items = this.list_ChiTietTien.ds_tien_khoanmuc.filter(x=>x.khoanmuctt_id==item.khoanmuctt_id);
              if(items && items.length>0) {
                  item.tien = Number(items[0].tien);
                  item.vat  = Number(items[0].vat);
                  item.tongtien = item.tien+item.vat;
              }
            }
        }
      });
      this.popupComponentData = {
        ds:ds,
        cols:[ 
          {fieldName: 'tentat_kmtt', headerText: 'Khoản mục', allowFiltering: false, allowSorting: false, isPrimaryKey:true}, 
          {fieldName: 'tongtien', textAlign:'right', allowEditing:true, editType: 'numericedit', format:'N', customAttributes:{class: 'tongtien'},  headerText: 'Tổng tiền', allowFiltering: false, allowSorting: false, edit:"tongtienParam"}, 
          {fieldName: 'tien', textAlign:'right', allowEditing:true, editType: 'numericedit', format:'N', customAttributes:{class: 'cuoclapdat'},  headerText: 'Tiền', allowFiltering: false, allowSorting: false, edit:"tienParam"},
          {fieldName: 'vat', textAlign:'right', format:'N', customAttributes:{class: 'vatlapdat'}, headerText: 'Vat', allowFiltering: false, allowSorting: false},
        ],
        keycol: "khoanmuctt_id",allowExit:false,tieude:"Chi tiết tiền khoản mục",tieudebang:"Chi tiết khoản mục thanh toán",
        allowExit: true
      }
      this.$refs.popupTienKM.show();
      // this.popupComponentName = "popupTienKM";
      // this.popupComponent = ()=>import('./popupTienKM');
      // this.Popup('popupComponents');
    },
    formCloseTienKM(val){
      if(val){
            this.list_ChiTietTien.ds_tien_khoanmuc = val;
              var dtien = 0;
              var dvat = 0;
              var dtongtien = 0;
             for (var i = 0; i < this.list_ChiTietTien.ds_tien_khoanmuc.length; i++)
              {
                dtien += this.list_ChiTietTien.ds_tien_khoanmuc[i]["tien"];
                dvat += this.list_ChiTietTien.ds_tien_khoanmuc[i]["vat"];
                dtongtien += this.list_ChiTietTien.ds_tien_khoanmuc[i]["tongtien"];
              }
              this.list_ChiTietTien.tien = dtien;
              this.list_ChiTietTien.tienvat = dvat;
               this.list_ChiTietTien.tongtien = dtongtien;
      }
      this.$refs.popupTienKM.hide();
    },
 //#endregion
    //button + thêm thuê bao
    setMaTB(value) {
      if (value.ma_tb){
        this.list_TTThueBao.mathuebao = value.ma_tb;
        this.listTT_Phieu_YC.dichvuvt = value.dichvuvt_id;
        this.keypressMaTB(); 
      }
    },
  },
};
</script>
<style>
.list a{
    cursor: pointer;
}
</style>
