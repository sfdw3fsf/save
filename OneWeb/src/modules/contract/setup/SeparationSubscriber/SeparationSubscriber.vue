<template src="./SeparationSubscriber.html"></template>
<script>
import moment from "moment";
import Select2Ext from "../DeclareLandline/components/Select2Ext";
import DataGridCustom from "../DeclareLandline/components/DataGridCustom";
import VueDateExt from "../DeclareLandline/components/VueDateExt";
import frmNguoiGT from "./popups/frmNguoiGT.vue";
import SearchContractModal from "@/modules/search/subscriber/SearchContract/SearchContract.vue";
import popupTraCuuThueBao from "./popups/popupTraCuuThueBao";
import DiaChiModal from '../SurveyRequest/popups/ChonDiaChi/DiaChiModal.vue'
import frmTC_DBTB_KH from "./popups/frmTC_DBTB_KH.vue";
import ThongTinEmail from "../ChangeContractOwner/popup/ThongTinEmail.vue";
import ThongTinLienHe from "../ChangeContractOwner/popup/ThongTinLienHe.vue";
import PopupTimKiemNhanVienThuCuoc from '@/modules/EXTS/HOPDONG/ThayDoiThongTinKhuyenMai/TimNhanVienThuCuoc'
import popupChiTietMLNS from "./popups/popupChiTietMLNS.vue"
import InHoSo from "@/modules/print/InHopDong/InHopDong";
import frmCT_Tien_KM from "./popups/frmCT_Tien_KM.vue";
import breadcrumb from "@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue";
import ChonDonVi from '@/modules/EXTS/HOPDONG/DichChuyenNew/components/ThongTinKhachHang/ChonDonVi'
import SearchGeneral from "@/modules/search/subscriber/SearchGeneral"
import popupChonDonVi from "./popups/popupChonDonVi.vue";

const thanhToan = () => import('@/modules/contract/setup/Payment/Payment.vue')

import {
  TrangThaiHD,
  DichVuVienThong,
  LoaiHopDong,
  TRANGTHAI_DONGBO,
  LoaiHinhTB,
  LOAI_DV,
  NHOM_LNV,
  TRANGBI,
  KHOANMUC_TT,
  KieuLapDat,
  HinhThucThanhToan,
  Loai_NV,
  TrangThaiTB,
  TrangThaiCaiDat
} from "@/const/enums";
var rs;
export default {
  components: {
    Select2Ext,
    DataGridCustom,
    VueDateExt,
    frmNguoiGT,
    SearchContractModal,
    popupTraCuuThueBao,
    DiaChiModal,
    frmTC_DBTB_KH,
    ThongTinEmail,
    ThongTinLienHe,
    popupChiTietMLNS,
    InHoSo,
    frmCT_Tien_KM,
    breadcrumb,
    SearchGeneral,
    PopupTimKiemNhanVienThuCuoc,
    ChonDonVi,
    popupChonDonVi
  },
  data() {
    return {
      popupInHoSo_Param: {
        hdkh_id: 0,
        hdtb_id: 0,
        ma_gd: "",
      },
      params: {
        p_donvi_quanly_id: 0,
        ma_nvtc: ""
      },
      objNganhang: {},
      txtMaGDTT: "",
      dataDiaChi: {
        type: "",
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: '',
        //
        block: '',
        tang: '',
        sophong: '',
        motathem: '',
        diachi: ''
      },
      columnsTB: [
        {
          fieldName: "stt",
          headerText: "Stt",
          allowFiltering: true,
          allowSorting: true,
          width: "70",
          textAlign: "left"
        },
        {
          fieldName: "ma_tb",
          headerText: "Số máy/Acc",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
          textAlign: "left"
        },
        {
          fieldName: "ten_tb",
          headerText: "Tên thuê bao",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "diachi_ld",
          headerText: "Địa chỉ lắp đặt",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "ten_dvvt",
          headerText: "Dịch vụ VT",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "ten_kieuld",
          headerText: "Kiểu lắp đặt",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "trangthai_dongbo",
          headerText: "Trạng thái",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
      ],
      columnsTT: [
        {
          fieldName: "stt",
          headerText: "Stt",
          allowFiltering: true,
          allowSorting: true,
          width: "120",
          textAlign: "left"
        },
        {
          fieldName: "ma_tt",
          headerText: "Mã TT",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
          textAlign: "left"
        },
        {
          fieldName: "ten_tt",
          headerText: "Tên thanh toán",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        },
        {
          fieldName: "diachi_tt",
          headerText: "Địa chỉ thanh toán",
          allowFiltering: true,
          allowSorting: true,
          width: "auto",
        }
      ],
      position: { X: 'center', Y: 'top' },
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      targetTop: '#app .main-wrapper',
      popupComponentEvts: {
        'form-close': this.popupClosed,
        paymentcreated: this.paymentcreated
      },
      objNVTC: {
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        tuyenthu_id: 0,
        nhanvientc_id: 0,
        kt_kylai: false,
        donviql_id: 0
      },
      popupComponentName: '',
      nhanvien_ql_am_id: 0,
      dichvuvt_id: null,
      _dichvuvt_id: null, //PNLINH 27/04/2020
      httt_id: null,
      donviql_id: null,
      donvi_id: null,
      hdkh_id: null,
      hdtt_id: null,
      loaiPopupTraCuuNVTC: 0,
      modalSearch: {
        loaihd_id: LoaiHopDong.TACHNHAP_TB,
        tthd_id: TrangThaiHD.MOI
      },
      hdtb_id: null,
      userComponentModule: null,
      tt_nd: {},
      khachhang_id: null,
      hdkh_cha_id: null,
      thanhtoan_id: null,
      thuebao_id: null,
      tuyenthu_id: 0,
      trangbi_id: 3,
      ma_tt: null,
      chapnhan: false,
      diachikh_id: null,
      tinhkh_id: null,
      quankh_id: null,
      phuongkh_id: null,
      phokh_id: null,
      apkh_id: null,
      khukh_id: null,
      dacdiemkh_id: null,
      sonhakh: null,

      diachitt_id: null,
      tinhtt_id: null,
      quantt_id: null,
      phuongtt_id: null,
      phott_id: null,
      aptt_id: null,
      khutt_id: null,
      dacdiemtt_id: null,
      sonhatt: null,

      diachict_id: null,
      tinhct_id: null,
      quanct_id: null,
      phuongct_id: null,
      phoct_id: null,
      apct_id: null,
      khuct_id: null,
      dacdiemct_id: null,
      sonhact: null,

      diachild_id: null,
      tinhld_id: null,
      quanld_id: null,
      phuongld_id: null,
      phold_id: null,
      apld_id: null,
      khuld_id: null,
      dacdiemld_id: null,
      sonhald: null,

      diachitb_id: null,
      tinhtb_id: null,
      quantb_id: null,
      phuongtb_id: null,
      photb_id: null,
      aptb_id: null,
      khutb_id: null,
      dacdiemtb_id: null,
      sonhatb: null,

      diachibc_id: null,
      tinhbc_id: null,
      quanbc_id: null,
      phuongbc_id: null,
      phobc_id: null,
      apbc_id: null,
      khubc_id: null,
      dacdiembc_id: null,
      sonhabc: null,

      blocktb: "",
      tangtb: "",
      sophongtb: "",
      motadctb: "",

      blockld: "",
      tangld: "",
      sophongld: "",
      motadcld: "",

      /// <summary>
      /// TuấnNA thêm khai báo biến -- Ngày 31/07/2010
      khachhang_id_nm: 0, ///khachhang_id_ nm (vừa mới nhập)
      khachhang_id_dtg: 0, ///khachhang_id_dtg (đang tách ghép)
      hdtb_id_dtg: 0, ///hdtb_id_dtg (đang tách ghép)
      doituong_id: 1, //Defaul: tư nhân
      quoctich: "",
      ten_tt: "",
      diachi_tt: "",
      goicuoc_id: "",
      doituong_vinaphone: "",
      loaikh_id: "",
      vnganhnghe_id: 0,
      loaigt_id: "",
      ngaycap: "",
      ngay_sn: "",
      ma_gd: "",
      email_adsl: "",
      email_: "",
      muccuoc_id: 0,
      tocdo_id: 0,
      ma_kh_cu: "",
      ma_tb_cu: "",
      ma_tt_cu: "",
      donvi_ql: 0,
      status: "",
      gioitinh: "", /// TuanNA thêm biến dùng để đưa trường giới tính vào HDKH -- Ngày 26/08/2010
      check_tb_dd: false, /// TuanNA thêm biến dùng để kiểm tra hợp đồng có thuê bao di động hay không -- Ngày 01/08/2010
      /// </summary>
      ///TuấnNA thêm khai báo biến strError_VinaPhone - Biến lưu lại chuỗi thông báo lỗi trả về từ VinaPhone
      ///--Ngày 26/08/2010
      strError_VinaPhone: "",
      loaitb_id: null,
      _loaitb_id: null,
      report: null,
      //TuấnNA thêm khai báo cho nút ngân sách nhà nước // Ngày 12/11/2010
      DV_QHNS: null,
      MACHUONG: null,
      MN_KT: null,
      MA_NDKT: null,
      MN_NSNN: null,
      TIEN_DM: null,

      linhvuc_id: 0,
      //
      bangts: null,
      tachnhaptv: null,
      laphd: null,
      tchopdong: null,
      tcdanhba: null,
      hths: null,
      hoanthienhs: null,
      //GetKeyFacade getkey : new GetKeyFacade(),
      tracuuchung: null,

      tt_nd: null,
      cmUlt: null,
      thamso_md: null,

      dsHDKH: null,
      dsHDTT: null,
      dsHDTB: null,

      dsDiaChi: null,
      dsDiaChiHDKH: null,
      dsDiaChiHDTT: null,
      paymentloaded: false,
      dsDiaChiHDTB: null,
      //Hiếu bổ sung thêm 2 dataset thông tin thanh toán & chi tiết tiền khoản mục 28/09/2010
      dsTTTT: null,
      dsCTTHD: null,
      dsHDTT_NV: null,
      CT_TIENHD_ID: null,
      ltb_adsl_id: 1,
      kh_cd: 0,
      dtCTKM: [], //hiếu 28/09/2010: Lưu danh sách các khoản mục.
      /// <summary>
      /// TuấnNA Thêm khai báo các dataset -- Ngày 02/08/2010
      dsHDTB_CD: null,
      dsHDTB_GP: null,
      dsHDTB_DD: null,
      dsHDTB_: null,
      dsHDTB_ADSL: null,
      pdsHDTB_CNTT: null,
      ItemList: null,
      dsDK_DVGT: null,
      dsDK_DVK: null,

      m_dsThueBao_DichVu: null,
      checkdata: null,

      //TuanNA: thêm khai báo -> hiển thị màu cho lưới -- Ngày 10.05.2012
      cmUtili: null,
      nhanvientc_id: 0,
      quytrinh_id: 0,

      hdmig_id: 0,
      mig_id: 0,
      ds_tien_khoanmuc: [],
      dtientn: 0,
      dvattn: 0,
      tien_dv: 0,
      vat_dv: 0,

      kt_load: true,

      DONVIQL_HTTT: 0,
      TUYENTHU_HTTT: 0,
      khong_ht_tracuudb: 0,
      ma_tb: "", //PNLINH 27/04/2020
      autoload: false, //PNLINH 27/04/2020
      nguoigt_id: null,
      donvi_nguoi_gt_id: "0",
      loainv_nguoi_gt_id: "0",
      ts_sinhma_gd_theo_donvi: false, //Dungf cho Hni sinh ma GD theo don vi quan ly cua ng dung
      loi_sinh_magd: false,
      batbuoc_gioithieu_tachnhap: 0,
      phantap_kh: false,
      pt_doanhthu: 0,
      pt_diem_tn: 0,
      loaihinh_tb_arr : []
    };
  },
  async mounted() {
    
    this.loading(true)
    rs = await this.axios.get("web-quantri/danhmuc-chung/CSS_LOAIHINH_TB");
    this.loaihinh_tb_arr = rs.data.data
    rs = await this.axios.post("/quantri/user/thongtin_nguoidung2", {});
    this.tt_nd = rs.data.data
    await this.frmTachNhapTB_Load();
    this.loading(false)
  },
  methods: {
    getNVTCProcess(data) {
      if(this.loaiPopupTraCuuNVTC == 0) {
        if (
          this.quantt_id  &&
          this.phuongtt_id  &&
          (this.phott_id  || this.khutt_id  || this.aptt_id )
        ) {
          this.nhanvientc_id = data.nhanvientc_id;
          this.$refs.txtMaNVTC.value = data.ma_nvtc;
          this.$refs.txtMaTuyen.value = data.ma_tuyen;
          this.tuyenthu_id = data.tuyenthu_id;

          let vloaikh_id = 0;
          if (this.loaikh_id) vloaikh_id = Number(this.loaikh_id);
          if (this.DONVIQL_HTTT == 1)
            this.HienThi_NVQL_AM_HTTT(
              vloaikh_id,
              Loai_NV.NHANVIEN_KINHDOANH,
              this.phuongtt_id,
              this.phott_id,
              this.aptt_id,
              this.khutt_id,
              this.nhanvientc_id,
              Number(this.$refs.cboDonViQL.value),
              Number(this.$refs.cboHTTT.value)
            );
          else {
            if (!this.phantap_kh)
              this.HienThi_NVQL_AM(
                vloaikh_id,
                Loai_NV.NHANVIEN_KINHDOANH,
                this.phuongtt_id,
                this.phott_id,
                this.aptt_id,
                this.khutt_id,
                this.nhanvientc_id,
                Number(this.$refs.cboDonViQL.value)
              );
          }
        } else {
          this.$toast.error(
            "Chưa có thông tin về địa chỉ thanh toán\nBạn kiểm tra lại!"
          );
          this.nhanvientc_id = 0;
          this.$refs.txtMaNVTC.value = "";
          this.$refs.txtMaTuyen.value = "";
          this.$refs.txtMaNVTC.focus();
          this.$refs.txtNhanVienQL.value = "";
          this.nhanvien_ql_am_id = 0;
        }
      }  if(this.loaiPopupTraCuuNVTC == 1) {
          if(this.objNVTC.QUAN_ID && this.objNVTC.PHUONG_ID) {
            this.nhanvientc_id = data.nhanvientc_id;
            this.$refs.txtMaNVTC.value = data.ma_nvtc;
            this.tuyenthu_id = data.tuyenthu_id;
            this.$refs.txtMaTuyen.value = data.ma_tuyen;
          }
          else {
            this.$toast.error("Chưa có thông tin về địa chỉ thanh toán. Bạn kiểm tra lại!");
            this.$refs.txtMaNVTC.value = "";
            this.$refs.txtMaTuyen.value = "";
            this.nhanvientc_id = 0;
            this.tuyenthu_id = 0;
          }
        }
    },
    open_thanhToanPopUp() {
      this.loading(true)
    },
    formatPrice(value) {
      let val = (value / 1).toFixed(0).replace(",", ".");
      return val.replace(/B(?=(d{3})+(?!d))/g, ",");
    },
    acceptTienKM(value) {
      this.$refs.lblTongTien.value = this.formatPrice(value.tong);
      this.$refs.txtTongTien.value = this.formatPrice(value.tongtien);
      this.$refs.txtTongVat.value = this.formatPrice(value.tongvat);
      this.$bvModal.hide("popup-ChiTietTienKM");
    },
    async popupClosed(val) {
      console.log(val)
      if (!val) {
        this.popupRetVal = {};
        this.ClosePopup('popupComponents');
        return;
      }
      try {
        switch (this.popupComponentName) {
          case "popupChonMaTT":
            if (val.ma_tt) this.$refs.txtMaTT.value = val.ma_tt;
            this.txtMaTT_Enter()
            break;
          case "popupChonMaInGhep":
            if (val && val.hdmig_id) {
              this.hdmig_id = val.hdmig_id;
              this.$refs.txtMaIG.value = val.main_ghep;
            }
            else {
              if (this.hdtt_id && this.hdtt_id > 0) {
                rs = await this.axios.post("/web-hopdong/lapdatmoi/lay_ds_hopdong_tt_theo_hdtt_id", { "hdtt_id": this.hdtt_id })
                this.HienThiTTHopDongTT(rs.data.data);
              }
            }
            break;
        }
        this.ClosePopup('popupComponents');
      } catch (e) {
        console.log(e)
      }finally {
        this.loading(false)
      }
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },
    async ThongTinEmail_Click() {
      this.$bvModal.show("ThongTinEmail")
    },
    async frmTachNhapTB_Load() {
      return new Promise(async (resolve, reject) => {
      try {
        this.chapnhan = false;
        this.$refs.txtMaTB.value = this.ma_tb;
        this.$refs.dtpNgayLapHD.value = moment().format("DD/MM/YYYY HH:mm:ss");
        this.$refs.dtpNgayLapHD.disabled = true
        //nhapt 09/06/2015
        this.$refs.dtpNgayYC.value = moment().format("DD/MM/YYYY HH:mm:ss"); ///TuanNA thêm khoi tao this.$refs.dtpNgayYC -- Ngay 10/08/2010
        this.$refs.dtpNgayYC.disabled = true
        this.SetButton(-1);
        rs = await this.axios.post(
          "web-hopdong/tachnhapthuebao/sp_lay_tienkhoanmuc_loaihd",
          {
            loaihd_id: LoaiHopDong.TACHNHAP_TB,
          }
        );
        this.ds_tien_khoanmuc = rs.data.data;
        rs = await this.axios.post(
          "web-hopdong/tachnhapthuebao/sp_lay_dichvu_vt_theo_nd",
          { nguoidung_id: this.$root.token.getNguoiDungID() }
        );
        let dsDV = rs.data.data
        this.$refs.cboDichVuVT.dataSource = [...new Map(dsDV.map((item) => [item["dichvuvt_id"], item])).values()]
        this.$refs.cboDichVuVT.dataTextField = "ten_dvvt";
        this.$refs.cboDichVuVT.dataValueField = "dichvuvt_id";
        if (this._dichvuvt_id) {
          //PNLINH: 27/04/2020
          this.$refs.cboDichVuVT.value = this._dichvuvt_id;
          rs = await this.axios.get("web-quantri/danhmuc-chung/CSS_LOAIHINH_TB");
          this.$refs.cboLoaihinhTB.dataSource = rs.data.data.filter(
            (item) => item.DICHVUVT_ID == this._dichvuvt_id
          );
          this.$refs.cboLoaihinhTB.dataTextField = "LOAIHINH_TB";
          this.$refs.cboLoaihinhTB.dataValueField = "LOAITB_ID";
          this.$refs.cboLoaihinhTB.value = this.__loaitb_id
        } else {
          this.$refs.cboDichVuVT.SelectedIndex = 0;
          rs = await this.axios.get("web-quantri/danhmuc-chung/CSS_LOAIHINH_TB");
          this.$refs.cboLoaihinhTB.dataSource = rs.data.data.filter(
            (item) => item.DICHVUVT_ID == 1
          );
          this.$refs.cboLoaihinhTB.dataTextField = "LOAIHINH_TB";
          this.$refs.cboLoaihinhTB.dataValueField = "LOAITB_ID";
          this.$refs.cboLoaihinhTB.value = 1
        }
        
        this.dichvuvt_id = this.$refs.cboDichVuVT.value;

        this.cboLoaihinhTB_SelectedValueChanged()

        rs = await this.axios.post(
          "web-hopdong/tachnhapthuebao/sp_lay_doituong_tb",
          { nguoidung_id: this.$root.token.getNhanVienID() }
        );
        this.$refs.cboDoituongTB.dataSource = rs.data.data;
        this.$refs.cboDoituongTB.dataTextField = "ten_dt"
        this.$refs.cboDoituongTB.dataValueField = "doituong_id"
        this.$refs.cboDoituongTB.value = this.$refs.cboDoituongTB.dataSource ? this.$refs.cboDoituongTB.dataSource[0].doituong_id : null
        //bangts.HT_NganHang_Combobox_LD(cboNganHang);
        rs = await this.axios.get("web-quantri/danhmuc-chung/CSS_NGANHANG");
        let dtNganHang = rs.data.data;
        this.$refs.cboNganHang.dataSource = dtNganHang;
        this.$refs.cboNganHang.dataTextField = "TEN_NH";
        this.$refs.cboNganHang.dataValueField = "NGANHANG_ID";
        this.$refs.cboNganHang.value =
          this.$refs.cboNganHang.dataSource.length > 0
            ? this.$refs.cboNganHang.dataSource[0].NGANHANG_ID
            : null;

        rs = await this.axios.get("web-quantri/danhmuc-chung/CSS_HINHTHUC_TT");
        let dtHT = rs.data.data;
        this.$refs.cboHTTT.dataSource = dtHT;
        this.$refs.cboHTTT.dataTextField = "HINHTHUC_TT";
        this.$refs.cboHTTT.dataValueField = "HTTT_ID";
        this.$refs.cboHTTT.value = null

        rs = await this.axios.post(
          "web-hopdong/tachnhapthuebao/sp_lay_ds_donvi_theo_dvql",
          { dvql_id: 0, loaidv_id: LOAI_DV.DONVIQL_TT }
        );
        this.$refs.cboDonViQL.dataSource = rs.data.data;
        this.$refs.cboDonViQL.dataTextField = "ten_dv";
        this.$refs.cboDonViQL.dataValueField = "donvi_id";
        this.$refs.cboDonViQL.SelectedIndex = 0

        this.$refs.cboMaDD.dataTextField = "ma_tb"
        this.$refs.cboMaDD.dataValueField = "ma_tb"

        let rs = await this.axios.post(
          "web-hopdong/thanhly/sp_lay_ds_thamso_md",
          { kieu_id: 0 }
        );
        let ds_kt1 = rs.data.data;
        if (ds_kt1.length > 0) {
          let str_vtemp = "DONVIQL_HTTT";
          let det;
          det = ds_kt1.filter((item) => item.ma_ts == str_vtemp);

          if (det.length > 0) {
            if (det[0]["ten_ts"] == "1") this.DONVIQL_HTTT = 1;
          }

          str_vtemp = "TUYENTHU_HTTT";
          det = ds_kt1.filter((item) => item.ma_ts == str_vtemp);

          if (det.length > 0) {
            if (det[0]["ten_ts"] == "1") this.TUYENTHU_HTTT = 1;
          }

          str_vtemp = "PHANTAP_KH"; // hoangpkn : 26/10/2020. Tham số phân tập khách hàng

          det = ds_kt1.filter((item) => item.ma_ts == str_vtemp);

          if (det.length > 0) {
            if (det[0]["ten_ts"] == "1") this.phantap_kh = 1;
          }

          str_vtemp = "KHONG_HIENTHI_TRACUU_DB_TACHNHAP";
          det = ds_kt1.filter((item) => item.ma_ts == str_vtemp);

          if (det.length > 0) {
            if (det[0]["ten_ts"] == "1") this.khong_ht_tracuudb = 1;
          }

          // Tham số bắt buộc phải nhập người giới thiệu
          str_vtemp = "BATBUOC_GIOITHIEU_TACHNHAP";

          det = ds_kt1.filter((item) => item.ma_ts == str_vtemp);

          if (det.length > 0) {
            if (det[0]["ten_ts"] == "1") this.batbuoc_gioithieu_tachnhap = 1;
          }

          this.ts_sinhma_gd_theo_donvi =
            ds_kt1.filter((item) => item.ma_ts == "SINH_MA_GD_THEO_DV").length >
            0;
        }

        this.kt_load = false;
        if (this.autoload) {
          this.Lay_Thongtin();
        }

        try {
          let ma_kh  = this.$route.query.ma_kh;
          let ma_tb  = this.$route.query.ma_tb;
          let ten_kh = this.$route.query.ten_kh;
          let sdt    = this.$route.query.sdt;
          let hdkh_cha_id = this.$route.query.hdkh_cha_id;
          if(ma_kh) {
            this.$refs.txtMaKH.value = ma_kh
            await this.txtMaKH_KeyPress()
          } else if(ma_tb) {
            this.$refs.txtMaTB.value = ma_tb
            await this.txtMaTB_KeyPress()
          }

          if(ten_kh) {
            this.$refs.txtTenKH.value = ten_kh
          }

          if(sdt) {
            this.$refs.txtTelKH.value = sdt
          }

          if(hdkh_cha_id) {
            await this.LoadTuHopDongTuVan(hdkh_cha_id)
          }

          

        } catch(err) {
          console.log(err)
        }
        //nhapt 09/06/2015
        // ds_tien_khoanmuc = bangts.Lay_tienkhoanmuc_loaihd(LoaiHopDong.TACHNHAP_TB);
        resolve(true)
      } catch (err) {
        resolve(true)
        console.log(err);
      }
      })
    },

    async frmTachNhapTB_Activated() {
      if (this.$refs.txtMaGD.value != "") {
        rs = await this.axios.post(
          "web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd",
          {
            donvi_dl_id: 0,
            donvi_id: this.$root.token.getDonViID(),
            loaihd_id: LoaiHopDong.TACHNHAP_TB,
            ma_gd: this.$refs.txtMaGD.value,
            nhanvien_id: this.$root.token.getNhanVienID(),
            tthd_id: TrangThaiHD.MOI,
          }
        );
        this.HienThiTTHopDongKH(rs.data.data);
      }
      this.$refs.txtMaTB.focus();
    },
    async setMaTB(value) {
      console.log(value)
      if (value.ma_tb) this.$refs.txtMaTB.value = value.ma_tb;
      if (value.dichvuvt_id) {
        this.$refs.cboDichVuVT.value = value.dichvuvt_id
        await this.cboDichVuVT_SelectedValueChanged()
        this.txtMaTB_KeyPress()
      }
    },
    async btnTKMaTB_Click() {
      if (this.$refs.txtMaTB.value) {
        if (this.khachhang_id_dtg) {
          this.$refs.tracuuthuebao.showModal(
            this.khachhang_id_dtg,
            this.dichvuvt_id,
            this.ma_gd,
            "Đang tách nhập thuê bao"
          );
        } else {
          if (this.hdtb_id) {
            let data = await this.axios.post(
              "web-hopdong/khoiphucthanhly/lay_biendong_theo_hdtb_id",
              {
                hdtb_id: this.hdtb_id,
              }
            );
            if (data && data.data && data.data.data.length > 0) {
              this.$refs.tracuuthuebao.showModal(
                data.data.data[0].khachhang_id,
                this.dichvuvt_id,
                this.this.$refs.txtMaGD.value,
                "Đang tách nhập thuê bao"
              );
            } else {
              this.$toast.error(
                "Chưa có thông tin về khách hàng Tách ghép có mã thuê bao: " +
                this.$refs.txtMaTB.value +
                "\nKiểm tra lại !"
              );
            }
          } else {
            this.$toast.error(
              "Chưa có thông tin về khách hàng Tách ghép có mã thuê bao: " +
              this.$refs.txtMaTB.value +
              "\nKiểm tra lại !"
            );
          }
        }
      } else {
        this.$toast.error("Bạn chưa nhập mã thuê bao!!!");
      }
    },
    //endregion

    //region Set button
    SetButton(kieu) {
      this.$refs.tsbtnNhapMoi.classList.add("disabled");
      this.$refs.tsbtnGhiLai.classList.add("disabled");
      this.$refs.tsbtnXoa.classList.add("disabled");
      this.$refs.tsbtnHuyBo.classList.add("disabled");
      this.$refs.tsbtnThemTB.classList.add("disabled");
      this.$refs.tsbtnThemTB_DS.classList.add("disabled");
      //this.$refs.tsbtnXoaTB.disabled = true
      ///TuấnNA thêm code ẩn/ hiện các tsbtn
      ///// Ngày 24/08/2010
      this.$refs.tsbtnThanhToan.classList.add("disabled");
      //this.$refs.tsbtnXoaTT.disabled = true
      //this.$refs.tsbtnThemTT.disabled = true

      this.$refs.btnHTHS.classList.add("disabled");

      this.$refs.tsbtnInhopdong.classList.add("disabled");
      this.$refs.tsbtnGiaoPhieu.classList.add("disabled");

      if (kieu == -1) {
        //Bat dau
        this.$refs.txtMaTB.focus();
        this.$refs.tsbtnGhiLai.classList.remove("disabled");
        this.$refs.tsbtnHuyBo.classList.remove("disabled");
        this.$refs.tsbtnXoaTB.classList.add("disabled");
        this.$refs.tsbtnThemTT.classList.add("disabled");
        this.$refs.cboMaDD.value = null;
      }

      if (kieu == 0) {
        //Bat dau
        this.$refs.txtMaTB.focus();
        this.$refs.tsbtnNhapMoi.classList.remove("disabled");
        this.$refs.tsbtnThemTT.classList.add("disabled");
        this.$refs.tsbtnXoaTB.classList.add("disabled");
        this.Clear();
      }

      if (kieu == 1) {
        //Them moi
        this.$refs.txtMaTB.focus();
        this.$refs.tsbtnGhiLai.classList.remove("disabled");
        this.$refs.tsbtnHuyBo.classList.remove("disabled");
        this.$refs.tsbtnThemTT.classList.add("disabled");
        this.$refs.tsbtnXoaTB.classList.add("disabled");
        this.Clear();
      }

      if (kieu == 2) {
        //Huy
        this.$refs.txtMaGD.txtMaGD.focus();
        this.$refs.tsbtnNhapMoi.classList.remove("disabled");
        this.$refs.tsbtnXoa.classList.remove("disabled");
        this.Clear();
      }

      if (kieu == 3) {
        //Edit
        this.$refs.txtMaGD.focus();

        this.$refs.tsbtnNhapMoi.classList.remove("disabled");
        this.$refs.tsbtnXoa.classList.remove("disabled");
        this.$refs.tsbtnGhiLai.classList.remove("disabled");
        this.$refs.tsbtnHuyBo.classList.remove("disabled");
        this.$refs.tsbtnThemTB.classList.remove("disabled");
        this.$refs.tsbtnThemTB_DS.classList.remove("disabled");
        //this.$refs.tsbtnXoaTB.disabled = false
        this.$refs.tsbtnThanhToan.classList.remove("disabled");
        //this.$refs.tsbtnXoaTT.disabled = false
        //this.$refs.tsbtnThemTT.disabled = false
        this.$refs.tsbnInPhieuYC.classList.remove("disabled");
        this.$refs.tsbtnGiaoPhieu.classList.remove("disabled");
        this.$refs.tsbtnInhopdong.classList.remove("disabled");
        //this.$refs.btnHTHS.disabled = false
      }
    },
    //endregion

    //region Clear
    Clear() {
      this.$refs.txtNhanVienQL.value = "";
      this.nhanvien_ql_am_id = 0;

      this.pt_doanhthu = 0;
      this.pt_diem_tn = 0;

      this.vnganhnghe_id = 0;
      this.loaikh_id = "";
      this.quytrinh_id = 0;
      this.ngaycap = "";
      this.trangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI; //Default tự trang bị
      this.doituong_id = 1; //defaul: tư nhân
      this.$refs.txtTongTienHD.value = "";
      this.hdkh_id = 0;
      this.$refs.txtMaGD.value = "";
      this.$refs.txtMaHD.value = "";
      this.$refs.txtMaKH.value = "";
      this.khachhang_id = 0;
      this.$refs.txtTenKH.value = "";
      this.$refs.txtDiaChiKH.value = "";

      this.tinhkh_id = 0;
      this.quankh_id = 0;
      this.phuongkh_id = 0;
      this.phokh_id = 0;
      this.apkh_id = 0;
      this.khukh_id = 0;
      this.dacdiemkh_id = 0;

      this.sonhakh = "";
      this.$refs.dtpNgayLapHD.value = moment().format("DD/MM/YYYY HH:mm:ss");
      this.$refs.txtMaTuyen.value = "";
      this.$refs.txtEmailTB.value = "";
      this.hdtt_id = 0;
      this.tuyenthu_id = 0;
      this.nhanvientc_id = 0;
      this.$refs.txtMaTT.value = "";
      this.thanhtoan_id = 0;
      this.$refs.txtTenTT.value = "";
      this.$refs.txtDiaChiTT.value = "";

      //diachi bc
      this.$refs.txtDiaChiBC.value = "";
      this.tinhbc_id = 0;
      this.quanbc_id = 0;
      this.phuongbc_id = 0;
      this.phobc_id = 0;
      this.apbc_id = 0;
      this.khubc_id = 0;
      this.dacdiembc_id = 0;
      this.sonhabc = "";

      //diachi ct
      this.$refs.txtDiachiCT.value = "";
      this.tinhct_id = 0;
      this.quanct_id = 0;
      this.phuongct_id = 0;
      this.phoct_id = 0;
      this.apct_id = 0;
      this.khuct_id = 0;
      this.dacdiemct_id = 0;
      this.sonhact = "";

      //
      this.tinhtt_id = 0;
      this.quantt_id = 0;
      this.phuongtt_id = 0;
      this.phott_id = 0;
      this.aptt_id = 0;
      this.khutt_id = 0;
      this.dacdiemtt_id = 0;
      this.sonhatt = "";

      this.tinhtb_id = 0;
      this.quantb_id = 0;
      this.phuongtb_id = 0;
      this.photb_id = 0;
      this.aptb_id = 0;
      this.khutb_id = 0;
      this.dacdiemtb_id = 0;
      this.sonhatb = "";

      this.$refs.txtMST_TT.value = "";
      this.$refs.txtSTK_TT.value = "";
      this.$refs.cboHTTT.SelectedIndex = 0;
      //cboNganHang.SelectedIndex = 0;
      this.$refs.cboNganHang.SelectedIndex = 0;

      this.hdtb_id = 0;
      this.thuebao_id = 0;
      this.$refs.txtTenTB.value = "";
      this.$refs.txtDiaChiTB.value = "";
      this.$refs.cboDichVuVT.SelectedIndex = 0;
      if (this._dichvuvt_id) {
        //PNLINH 27/04/2020
        this.dichvuvt_id = this._dichvuvt_id; //PNLINH 27/04/2020
      } else {
        this.dichvuvt_id = DichVuVienThong.CO_DINH;
      }
      this.$refs.cboDichVuVT.value = this.dichvuvt_id;
      this.cboDichVuVT_SelectedValueChanged()
      this.$refs.cboKieuLD.SelectedIndex = 0;
      this.$refs.cboDoituongTB.SelectedIndex = 0;
      this.$refs.lvwThanhToan.dataSource = [];
      this.$refs.lvwThueBao.dataSource = [];
      this.$refs.cboTT.DataSource = [];

      this.$refs.txtDiachiLD.value = "";
      this.$refs.txtDiachiCT.value = "";

      this.$refs.txtMaNVTC.value = "";
      this.$refs.txtMaTBDD.value = "";
      this.$refs.dtpNgayYC.value = moment().format("DD/MM/YYYY HH:mm:ss");
      this.khachhang_id_dtg = 0;
      this.khachhang_id_nm = 0;
      this.ten_tt = "";
      this.ma_tt = "";
      this.diachi_tt = "";
      this.quoctich = "";
      this.goicuoc_id = "";
      this.$refs.txtGhiChu.value = "";
      this.$refs.txtMaTB.focus();
      this.ltb_adsl_id = 1;
      this.LayTienTheoKhoanMuc();
      //TuấnNA thêm code clear ngân sách nhà nước // Ngày 12/11/2010
      this.DV_QHNS = "";
      this.MACHUONG = "";
      this.MN_KT = "";
      this.MA_NDKT = "";
      this.MN_NSNN = "";
      this.TIEN_DM = 0;

      this.hdmig_id = 0;
      this.mig_id = 0;
      this.$refs.txtMaIG.value = "";
      this.$refs.cboMaDD.dataSource = [];
      this.$refs.cboMaDD.value = null;
      this.$refs.cboTT.dataSource = []
      //new change
      this.$refs.chkNguoiGT.checked = false;
      this.$refs.cboDoituongTB.value = this.$refs.cboDoituongTB.dataSource && this.$refs.cboDoituongTB.dataSource.length > 0 ? this.$refs.cboDoituongTB.dataSource[0].doituong_id  : null
      this.$refs.cboDonViQL.value = this.$refs.cboDonViQL.dataSource.length > 0 ? this.$refs.cboDonViQL.dataSource[0].donvi_id : null
      this.$refs.cboHTTT.value = this.$refs.cboHTTT.dataSource.length > 0 ? this.$refs.cboHTTT.dataSource[0].HTTT_ID : null
      this.$refs.cboNganHang.value = this.$refs.cboNganHang.dataSource.length > 0 ? this.$refs.cboNganHang.dataSource[0].NGANHANG_ID : null
      //
    },
    //endregion

    //region this.$refs.cboDichVuVT SelectedValueChanged
    async cboDichVuVT_SelectedValueChanged() {
      return new Promise((resolve, reject) => {
        if (this.$refs.cboDichVuVT.dataSource.length > 0) {
          if (this.$refs.cboDichVuVT.value == "") return;
          this.dichvuvt_id = this.$refs.cboDichVuVT.value;
          this.$refs.cboLoaihinhTB.dataSource = this.loaihinh_tb_arr.filter(
            (item) => item.DICHVUVT_ID == this.dichvuvt_id
          );
          console.log(this.$refs.cboLoaihinhTB.dataSource)
          this.$refs.cboLoaihinhTB.dataTextField = "LOAIHINH_TB";
          this.$refs.cboLoaihinhTB.dataValueField = "LOAITB_ID";
          this.$refs.cboLoaihinhTB.value = this.$refs.cboLoaihinhTB.dataSource[0].LOAITB_ID
        }
        resolve(true)
      })
    },
    //endregion

    //region KiemTraDuLieuNhap
    //,KiemTraDuLieuNhap()
    //{
    //    //Hieutc- 28.09.2010: THêm tiền LĐ
    //    if (!Common.Utilities.CommonFunction.CheckIsNumber(Common.Utilities.CommonFunction.Unformat_tien(txtTienTNTB.value)))
    //    {
    //        this.$toast.error("Dữ liệu tiền phải là kiểu số !");
    //        txtTienTNTB.focus();
    //        return false;
    //    }
    //    if (!Common.Utilities.CommonFunction.CheckIsNumber(Common.Utilities.CommonFunction.Unformat_tien(txtVatTNTB.value)))
    //    {
    //        this.$toast.error("Dữ liệu tiền phải là kiểu số !");
    //        txtVatTNTB.focus();
    //        return false;
    //    }
    //    return true;
    //}
    //endregion

    //region ButtonClick
    async tsbtnGhiLai_Click() {
      if(!this.$refs.cboHTTT.value) {
        this.$toast.error("Bạn chưa chọn hình thức thanh toán!")
        return
      }

      rs = await this.axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_thuebao",
      { param: this.thuebao_id, type: 1 });
      let vthanhtoan_id = rs.data.data;
      {
        rs = await this.axios.get("web-hopdong/hopdong/tracuu_no_thanhtoan", {
          params: {
            thanhToanId: vthanhtoan_id,
          },
        });
        let no_tt = rs.data.data;
        if (no_tt > 0) {
          //TuanNA: Thêm code kiểm tra nợ của thuê bao còn nợ cước -- ngày 17.12.2011

          let tienno = await this.TraCuu_No_MaTB(
            this.$refs.txtMaTB.value.trim().toLowerCase()
          );
          if (tienno > 0) {
            this.$toast.error(
              "Thuê bao: " +
              this.$refs.txtMaTB.value.trim().toLowerCase() +
              " còn nợ: " +
              parseFloat(tienno) +
              " vnđ.\nChủ thuê bao phải thanh toán hết nợ trước khi thực hiện Tách ghép thuê bao cho khách hàng khác!"
            );
            this.loading(false)
            return;
          }
        }
      }
      this.CapNhat();
    },

    async TraCuu_No_MaTB(ma_tb) {
      return new Promise(async (resolve, reject) => {
        let result = 0;
        try {
          if (this.thuebao_id != 0) {
            let no_tb = "";
            rs = await this.axios.get("web-hopdong/hopdong/tracuu_no_thuebao", {
              params: {
                thueBaoId: this.thuebao_id,
              },
            });
            no_tb = rs.data.data;
            result = Number(no_tb);
          } else {
            this.$toast.error("Chưa có thông tin về thuê bao!");
          }
        } catch (err) {
          reject(false);
          this.$toast.error("Có lỗi:" + err.message);
        }
        resolve(result);
      });
    },

    async tsbtnXoa_Click() {
      this.$confirm("Bạn thật sự muốn xóa hợp đồng không ?", {
        confirmButtonText: "Có",
        cancelButtonText: "Không",
        type: "success",
      }).then(async () => {
        var rs = await this.axios.post("web-hopdong/hopdong/get_app_config",
        {
          "keyname": "OPEN_SYNC_VINA"
        })
        if (rs.data && rs.data.data && rs.data.data.keyvalue == "TRUE") {
          //region Xoá hợp đồng trên vinaphone
          if (Number(this.$refs.cboDichVuVT.value) == DichVuVienThong.DI_DONG && Number(this.$refs.cboLoaihinhTB.value) == LoaiHinhTB.DIDONGTRASAU) {
            let ma_gd_neo = this.$refs.txtMaGD.value;
            rs = await this.axios.post("web-hopdong/tachnhap_thuebao/Delete_HDCQ_VinaPhone",
            { 
              ma_gd: ma_gd_neo
            })
            if (rs.data && rs.data.data == "OK") {
              this.$toast.error(
                "Đã xóa thành công hợp đồng/phụ lục có mã giao dịch: " +
                this.$refs.txtMaGD.value +
                " trên VinaPhone."
              );
            } else {
              this.$toast.error(
                "Có lỗi trong quá trình xóa hợp đồng/phụ lục có mã giao dịch: " +
                this.$refs.txtMaGD.value +
                " trên VinaPhone."
              );
              this.loading(false)
              return;
            }
          }
          //endregion
        }
        this.XoaHDKH();
      });
    },
    async Delete_HDCQ_VinaPhone(ma_gd) {
      try {
        let checkdata = new CheckDataFacade();

        if (thi.tt_nd.USER_NEO) {
          return "Thông tin người dùng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.";
        }
        rs = await this.axios.post(
          "/ccbs/ts_laphd_chuyenquyen_sudung_xoahd_input",
          {
            ma_tinh: this.tt_nd.tentat_ccbs,
            ma_tinh_nguoi_dung: this.tt_nd.tentat_ccbs,
            ma_hd: this.ma_gd,
            userid: this.tt_nd.USER_NEO,
          }
        );
        var rs_ccbs = rs.data.data;
        if (rs_ccbs.IssSuccessed) {
          this.$toast.success(
            "Thành công",
            "Xóa hợp đồng chuyển quyền {0} trên VinaPhone thành công!" +
            this.ma_gd
          );
          return "OK";
        } else return rs_ccbs.message;
      } catch (err) {
        return (
          "Có lỗi xảy ra trong quá trình xóa hợp đồng VinaPhone: " + err.message
        );
      }
    },

    tsbtnNhapMoi_Click() {
      this.NhapMoi();
    },

    NhapMoi() {
      this.SetButton(1);
    },

    tsbtnHuyBo_Click() {
      this.SetButton(0);
    },

    async tsbtnThemTB_Click() {
      rs = await this.axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_thuebao",
      { param: this.thuebao_id, type: 1 });
      let vthanhtoan_id = rs.data.data;
      if (vthanhtoan_id != "-1") {
        rs = await this.axios.get("web-hopdong/hopdong/tracuu_no_thanhtoan", {
          params: {
            thanhToanId: vthanhtoan_id,
          },
        });
        let no_tt = rs.data && rs.data.data ? rs.data.data : 0;
        if (no_tt > 0) {
          //TuanNA: Thêm code kiểm tra nợ của thuê bao còn nợ cước -- ngày 17.12.2011

          let tienno = await this.TraCuu_No_MaTB(
            this.$refs.txtMaTB.value.trim().toLowerCase()
          );
          if (tienno > 0) {
            this.$toast.error(
              "Thuê bao: " +
              this.$refs.txtMaTB.value.trim().toLowerCase() +
              " còn nợ: " +
              parseFloat(tienno) +
              " vnđ.\nChủ thuê bao phải thanh toán hết nợ trước khi thực hiện Tách ghép thuê bao cho khách hàng khác!"
            );
            this.loading(false)
            return;
          }
        }
      }
      this.ThemHDThueBao();
    },

    async tsbtnXoaTB_Click() {
      this.$confirm(`Bạn thật sự muốn xóa thuê bao không ?`, {
        confirmButtonText: "Có",
        cancelButtonText: "Không",
        type: "warning",
      }).then(async () => {
        try {
          this.loading(true);



          let rs = await this.axios.post(
            "web-hopdong/khoiphucthanhly/xoa_hdtb",
            {
              hdtb_id: this.hdtb_id,
            }
          );
          let ma_tb = this.$refs.lvwThueBao.getSelectedRecords()[0].ma_tb
          let deleteTBCQ = await this.axios.post('web-hopdong/tachnhap_thuebao/Delete_TBCQ_VinaPhone', {
                ma_tb: ma_tb,
                mahd_neo: this.$refs.txtMaGD.value
              })
              if (deleteTBCQ && deleteTBCQ.data && deleteTBCQ.data.data == 'OK') {
                this.$toast.error('Đã xóa thành công số thuê bao:: ' + ma_tb + ' trên VinaPhone.')
              } else {
                this.$toast.error('Không thể xóa thuê bao: ' + ma_tb + ' trên VinaPhone.')
                return
              }

          this.$toast.success(
            "Xóa thuê bao trên dữ liệu viễn thông tỉnh thành công ! "
          );
          this.HT_DanhSachTB();
          let dsHopdongTB = await this.axios.get(
            "web-ccdv/hoanthien-hs-tachnhap-tbao/lay_ds_hopdong_tb_theo_id/" +
            this.hdkh_id
          );

          this.HienThiTTHopDongTB(
            dsHopdongTB.data ? dsHopdongTB.data.data : []
          );
          this.loading(false);
        } catch (err) {
          this.$toast.error(
            "Xóa thuê bao trên dữ liệu viễn thông tỉnh không thành công ! "
          );
        }
      });
    },

    async btnHTHS_Click() {
      try {
        //Cường cho vào common 5/5/2020
        await this.axios.post(
          "/hoanthien-hs-tachnhap-tbao/hoanthien_hs_tachnhap_tbao/" +
          this.hdkh_id
        );
        this.SetButton(0);
        this.$toast.success("Đã hoàn thiện hồ sơ.");
      } catch (err) {
        console.log(err);
      }
    },
    //endregion

    //region Kiểm tra Tách nhập
    async KiemTraTachNhap() {
      try {
        let kieu = -1;
        if (Number(this.$refs.cboKieuLD.value) == KieuLapDat.TACH_THUEBAO) {
          kieu = 1;
        }
        if (Number(this.$refs.cboKieuLD.value) == KieuLapDat.NHAP_THUEBAO) {
          kieu = 2;
        }
        return true;
      } catch (err) {
        this.$toast.error("Có lỗi khi kiểm tra tách nhập: " + err.message);
        return false;
      }
    },
    //endregion
    //region Cap nhat HD
    checkDate(date) {
      if(!date)
        return true
      if(date && date.includes("/"))
        return true
      else
        return false
    },
    async CapNhat() {
      let async_status = false; // Biến kiểm tra việc đồng bộ dữ liệu VinaPhone, MyTV...
      try {
        
        this.loading(true)
        if (!(await this.KiemTraDL_KhachHang())) {
          this.loading(false)
          return;
        }
        if (!(await this.KiemTraDL_ThanhToan())) {
          this.loading(false)
          return;
        }
        if (!(await this.KiemTraDL_ThueBao())) {
          this.loading(false)
          return;
        }

        if (!(await this.KiemTraTachNhap())) {
          this.loading(false)
          return;
        }
        // if (!KiemTraDuLieuNhap()) return;//Hiếu thêm kiểm tra dl nhập 28.09.2010
        this.$refs.txtMaTB.focus();
        //region Thêm mới
        let kieu;
        if (this.$refs.tsbtnNhapMoi.classList.contains("disabled")) {
          kieu = 0;
          await this.TaoDuLieu(true);
        } else {
          kieu = 1;
          await this.TaoDuLieu(false);
        }

        console.log(this.dichvuvt_id, DichVuVienThong.DI_DONG, "this.dichvuvt_id")
        if(this.$refs.cboDichVuVT.value == DichVuVienThong.DI_DONG) {
            await this.TaoDuLieu_HDTB_DD(true);
        }
        let data = [
          {
            HDTB_ID: this.hdtb_id,
            MA_TB: this.$refs.txtMaTB.value, // txtMaTB.Text
            MA_GD: this.$refs.txtMaGD.value ? this.$refs.txtMaGD.value : "", //txtMaGD.Text
            MA_HD: this.$refs.txtMaHD.value, //txtMaHD.Text
            MA_KH: this.$refs.txtMaHD.value, // txtMaHD.Text,
            TEN_KH: this.$refs.txtTenKH.value, //CommonFunction.StringToString(txtTenKH.Text)
            DIACHI_KH: this.$refs.txtDiaChiKH.value, //CommonFunction.StringToString(txtDiaChiKH.Text)
            NGAYLAP_HD: this.checkDate(this.$refs.dtpNgayLapHD.value) ? this.$refs.dtpNgayLapHD.value : moment(this.$refs.dtpNgayLapHD.value).format("DD/MM/YYYY HH:mm:ss") , //dtpNgayLapHD.Value
            KHACHHANG_ID: this.khachhang_id ? this.khachhang_id : 0,
            NHANVIEN_ID: this.$root.token.getNhanVienID(), // tt_nd.nhanvien_id
            NHANVIENGT_ID: this.nguoigt_id ? this.nguoigt_id : 0, // Nếu thỏa mãn chkNguoiGT.Checked && !string.IsNullOrEmpty(txtNguoiGT.Text) ==> truyền nguoigt_id, ngược lại truyền 0
            NGAY_YC: this.checkDate(this.$refs.dtpNgayYC.value) ? this.$refs.dtpNgayYC.value : moment(this.$refs.dtpNgayYC.value).format("DD/MM/YYYY HH:mm:ss"), // dtpNgayYC.Value
            GIOITINH: this.gioitinh ? this.gioitinh : 0,
            LOAIKH_ID: this.loaikh_id ? this.loaikh_id : 0,
            LOAIGT_ID: this.loaigt_id ? this.loaigt_id : 0,
            NGAYCAP: this.checkDate(this.ngaycap) ? this.ngaycap : moment(this.ngaycap).format("DD/MM/YYYY HH:mm:ss"),
            TEN_TB: this.$refs.txtTenTB.value, //CommonFunction.StringToString(txtTenTB.Text)
            DIACHI_TB: this.$refs.txtDiaChiTB.value, // CommonFunction.StringToString(txtDiaChiTB.Text)
            HDKH_ID: this.hdkh_id ? this.hdkh_id : 0,
            HDTT_ID: this.hdtt_id ? this.hdtt_id : 0,
            MA_TT: this.$refs.txtMaTT.value, //txtMaTT
            THANHTOAN_ID: this.thanhtoan_id ? this.thanhtoan_id : 0,
            TEN_TT: this.$refs.txtTenTT.value, //CommonFunction.StringToString(txtTenTT.Text)
            DIACHI_TT: this.$refs.txtDiaChiTT.value, //CommonFunction.StringToString(txtDiaChiTT.Text)
            DIACHI_BC: this.$refs.txtDiaChiBC.value, //CommonFunction.StringToString(txtDiaChiBC.Text)
            DIACHI_LD: this.$refs.txtDiachiLD.value, // CommonFunction.StringToString(txtDiachiLD.Text);
            MATB_DD: this.$refs.cboMaDD.value, //cboMaDD.Text,
            MST: this.$refs.txtMST_TT.value, // txtMST_TT.Text
            HTTT_ID: this.$refs.cboHTTT.value, //Number(cboHTTT.Value)
            STK: this.$refs.txtSTK_TT.value, //txtSTK_TT.Text
            NGANHANG_ID: this.$refs.cboNganHang.value, //Number(cboNganHang.EditValue)
            TUYENTHU_ID: this.tuyenthu_id ? this.tuyenthu_id : 0,
            DIACHI_CT: this.$refs.txtDiachiCT.value, //CommonFunction.StringToString(txtDiachiCT.Text)
            GHICHU: this.$refs.txtGhiChu.value, // CommonFunction.StringToString(txtGhiChu.Text)
            DV_QHNS: this.DV_QHNS ? this.DV_QHNS : 0,
            MACHUONG: this.MACHUONG ? this.MACHUONG : 0,
            MN_KT: this.MN_KT ? this.MN_KT : 0,
            MA_NDKT: this.MA_NDKT ? this.MA_NDKT : 0,
            MN_NSNN: this.MN_NSNN ? this.MN_NSNN : 0,
            MATBDD: this.$refs.txtMaTBDD.value,
            TIEN_DM: this.TIEN_DM ? this.TIEN_DM : 0,
            THUEBAO_ID: this.thuebao_id ? this.thuebao_id : 0,
            DONVIQL_ID: this.$refs.cboDonViQL.value,
            KIEULD_ID: this.$refs.cboKieuLD.value, // Number(cboKieuLD.Value),
            DOITUONG_ID: this.$refs.cboDoituongTB.value, // Number(cboDoituongTB.Value)
            NGAY_HT: this.checkDate(this.$refs.dtpNgayLapHD.value) ? this.$refs.dtpNgayLapHD.value : moment(this.$refs.dtpNgayLapHD.value).format("DD/MM/YYYY HH:mm:ss"), // dtpNgayLapHD.Value
            MAY_CN: this.$root.token.getUserName(),
            NGAY_CN: moment().format("DD/MM/YYYY HH:mm:ss"),
            NGUOI_CN: this.$root.token.getUserName(),
            IP_CN: "1.1.1.1",
            TOCDO_ID: this.tocdo_id ? this.tocdo_id : 0,
            MUCCUOC_ID: this.muccuoc_id ? this.muccuoc_id : 0,
            KIEULD_ID: this.$refs.cboKieuLD.value, // Number(cboKieuLD.Value)
            GOICUOC_ID: this.goicuoc_id ? this.goicuoc_id : 0,
            DICHVUVT_ID: this.$refs.cboDichVuVT.value,
            DICHVUVT_ID_CBO: this.$refs.cboDichVuVT.value, // Number(cboDichVuVT.Value)
            DONVI_ID: this.$root.token.getDonViID(), // tt_nd.donvi_id
            LOAITB_ID: this.loaitb_id ? this.loaitb_id : 0,
            TINHKH_ID: this.tinhkh_id ? this.tinhkh_id : 0,
            QUANKH_ID: this.quankh_id ? this.quankh_id : 0,
            PHUONGKH_ID: this.phuongkh_id ? this.phuongkh_id : 0,
            PHOKH_ID: this.phokh_id ? this.phokh_id : 0,
            APKH_ID: this.apkh_id ? this.apkh_id : 0,
            KHUKH_ID: this.khukh_id ? this.khukh_id : 0,
            DACDIEMKH_ID: this.dacdiemkh_id ? this.dacdiemkh_id : 0,
            SONHAKH: this.sonhakh ? this.sonhakh : 0, // CommonFunction.StringToString(sonhaKH)
            DIACHIKH: this.$refs.txtDiaChiKH.value, // CommonFunction.StringToString(txtDiachiKH.Text);
            TINHTT_ID: this.tinhtt_id ? this.tinhtt_id : 0,
            QUANTT_ID: this.quantt_id ? this.quantt_id : 0,
            PHUONGTT_ID: this.phuongtt_id ? this.phuongtt_id : 0,
            PHOTT_ID: this.phott_id ? this.phott_id : 0,
            APTT_ID: this.aptt_id ? this.aptt_id : 0,
            KHUTT_ID: this.khutt_id ? this.khutt_id : 0,
            DACDIEMTT_ID: this.dacdiemtt_id ? this.dacdiemtt_id : 0,
            SONHATT: this.sonhatt ? this.sonhatt : 0, // CommonFunction.StringToString(sonhaTT)
            DIACHITT: this.$refs.txtDiaChiTT.value, // CommonFunction.StringToString(txtDiachiTT.Text);
            TINHLD_ID: this.tinhld_id ? this.tinhld_id : 0,
            QUANLD_ID: this.quanld_id ? this.quanld_id : 0,
            PHUONGLD_ID: this.phuongld_id ? this.phuongld_id : 0,
            PHOLD_ID: this.phold_id ? this.phold_id : 0,
            APLD_ID: this.apld_id ? this.apld_id : 0,
            KHULD_ID: this.khuld_id ? this.khuld_id : 0,
            DACDIEMLD_ID: this.dacdiemld_id ? this.dacdiemld_id : 0,
            SONHALD: this.sonhald ? this.sonhald : "", // CommonFunction.StringToString(sonhald)
            DIACHILD: this.$refs.txtDiachiLD.value, // CommonFunction.StringToString(txtDiachiLD.Text);
            BLOCKLD: this.blockld ? this.blockld : "",
            TANGLD: this.tangld ? this.tangld : "",
            SOPHONGLD: this.sophongld ? this.sophongld : "",
            MOTADCLD: this.motadcld ? this.motadcld : "",
            TINHTB_ID: this.tinhtb_id ? this.tinhtb_id : 0,
            DIACHIKH_ID: this.diachikh_id ? this.diachikh_id : 0,
            DIACHITT_ID: this.diachitt_id ? this.diachitt_id : 0,
            DIACHIHDTB_ID: this.diachitb_id ? this.diachitb_id : 0,
            DIACHI_ID: this.diachitb_id ? this.diachitb_id : 0,
            DIACHIBC_ID: this.diachibc_id ? this.diachibc_id : 0,
            DIACHITB_ID: this.diachitb_id ? this.diachitb_id : 0,
            QUANTB_ID: this.quantb_id ? this.quantb_id : 0,
            PHUONGTB_ID: this.phuongtb_id ? this.phuongtb_id : 0,
            PHOTB_ID: this.photb_id ? this.photb_id : 0,
            APTB_ID: this.aptb_id ? this.aptb_id : 0,
            KHUTB_ID: this.khutb_id ? this.khutb_id : 0,
            DACDIEMTB_ID: this.dacdiemtb_id ? this.dacdiemtb_id : 0,
            SONHATB: this.sonhatb ? this.sonhatb : "", // CommonFunction.StringToString(sonhatb)
            DIACHITB: this.$refs.txtDiaChiTB.value, // CommonFunction.StringToString(txtDiaChiTB.Text)
            BLOCKTB: this.blocktb ? this.blocktb : "",
            TANGTB: this.tangtb ? this.tangtb : "",
            SOPHONGTB: this.sophongtb,
            MOTADCTB: this.motadctb ? this.motadctb : "",
            TINHBC_ID: this.tinhbc_id ? this.tinhbc_id : 0,
            QUANBC_ID: this.quanbc_id ? this.quanbc_id : 0,
            PHUONGBC_ID: this.phuongbc_id ? this.phuongbc_id : 0,
            PHOBC_ID: this.phobc_id ? this.phobc_id : 0,
            APBC_ID: this.apbc_id ? this.apbc_id : 0,
            KHUBC_ID: this.khubc_id ? this.khubc_id : 0,
            DACDIEMBC_ID: this.dacdiembc_id ? this.dacdiembc_id : 0,
            SONHABC: this.sonhakh ? this.sonhakh : "", // CommonFunction.StringToString(sonhaKH)
            DIACHIBC: this.$refs.txtDiaChiBC.value, // CommonFunction.StringToString(txtDiachiKH.Text);
            NHANVIEN_QL_AM_ID: this.nhanvien_ql_am_id ? this.nhanvien_ql_am_id : 0, //nhanvien_ql_am_id
            QUYTRINH_ID: this.quytrinh_id
          },
        ];
        rs = await this.axios.post(
          "web-hopdong/tachnhapthuebao/capnhat_tachnhap_thuebao",
          {
            vkieu: kieu,
            vds: JSON.stringify(data),
            vds_tien_khoanmuc: JSON.stringify(this.dsCTTHD),
            vdstb_dichvu: JSON.stringify(this.dsHDTB),
          }
        );
        let arr = rs.data.message.split("~")
        if((!rs.data.data || rs.data.data == 1 || rs.data.data == "ok") && (rs.data.error_code == "BSS-00000000" || arr.length > 0)) {
          
          
          if(arr.length > 0 && arr.length == 2 && arr[0] == "1") {
              this.$refs.txtMaGD.value = arr[1]
              this.dsHDKH[0].ma_gd = arr[1]
              let trangthai_dongbo = await this.axios.post("web-hopdong/hopdong/get_app_config",
              {
                "keyname": "OPEN_SYNC_VINA"
              })
              // Đồng bộ dữ liệu trên vinaphone
                    //Tuan them code Insert dữ liệu lên vinaphone -- Ngay 20/07/2010
                    if (Number(this.$refs.cboDichVuVT.value) == DichVuVienThong.DI_DONG && Number(this.$refs.cboLoaihinhTB.value) == LoaiHinhTB.DIDONGTRASAU)
                    {
                        if (trangthai_dongbo.data && trangthai_dongbo.data.data.keyvalue == "TRUE")
                        {
                          let check_hd = await this.axios.post("web-hopdong/hopdong/kiemtra_hopdong_thuebao_dongbo_dulieu", {
                            maGd : this.$refs.txtMaGD.value,
                            dichVuVtId: this.dichvuvt_id,
                            loaiTbId: this.loaitb_id
                          })
                          var type = 1
                          let api = "web-hopdong/tachnhap_thuebao/Insert_HDCQ_VinaPhone"
                          let dataHDCQ = { 
                                  dsHDKH: this.ConvertLower(this.dsHDKH),
                                  dsHDTB: this.ConvertLower(this.dsHDTB),
                                  dsHDTT: this.ConvertLower(this.dsHDTT),
                                  dsDK_DVK: this.ConvertLower(this.dsDK_DVK),
                                  ma_tb: this.$refs.txtMaTB.value,
                                  khachhang_id: this.khachhang_id,
                                  dvattn: this.dvattn,
                                  quoctich: this.quoctich,
                                  quankh_id: this.quankh_id,
                                  phuongkh_id: this.phuongkh_id,
                                  phokh_id: this.phokh_id,
                                  sonhakh: this.sonhakh,
                                  quantt_id: this.quantt_id,
                                  phuongtt_id: this.phuongtt_id,
                                  phott_id: this.phott_id,
                                  sonhatt: this.sonhatt,
                                  ten_tt: this.ten_tt,
                                  diachi_tt: this.diachi_tt,
                                  txtMaNVTC: this.$refs.txtMaNVTC.value
                                }

                          if(check_hd.data && check_hd.data.data == "1") {
                            type = 2
                            api = "web-hopdong/tachnhap_thuebao/Update_HDCQ_VinaPhone"
                            dataHDCQ = {
                              dsHDKH : this.ConvertLower(this.dsHDKH),
                              dsHDTB: this.ConvertLower(this.dsHDTB),
                              dsHDTT: this.ConvertLower(this.dsHDTT),
                              dsDK_DVK:this.ConvertLower(this.dsDK_DVK),
                              ma_tb: this.$refs.txtMaTB.value,
                              quankh_id: this.quankh_id,
                              phuongkh_id: this.phuongkh_id,
                              phokh_id: this.phokh_id,
                              sonhakh: this.sonhakh,
                              quantt_id: this.quantt_id,
                              phuongtt_id: this.phuongtt_id,
                              phott_id: this.phott_id,
                              sonhatt: this.sonhatt,
                              txtMaNVTC: this.$refs.txtMaNVTC.value,
                              txtMaTuyen: this.$refs.txtMaTuyen.value
                            }
                          }

                            // Insert Hợp đồng, thuê bao trên vinaphone
                            var kt1 = false
                                var kq = await this.axios.post(api, dataHDCQ);
                                if (kq.data && kq.data.data == "OK")        // Nếu đẩy dữ liệu HD lên VinaPhone thành công. vuhth update 19/12
                                {
                                    kt1 = true;
                                } else
                                {
                                    this.$toast.error("Có lỗi xảy ra trong quá trình thêm mới hợp đồng khách hàng số: " + this.hdkh_id + " trên VinaPhone: " + kq.data.data);
                                    try {
                                          if(type == 1) {
                                            await this.axios.post("/web-hopdong/hopdong/xoa_hdkh_v2", {
                                              vhdkh_id: this.hdkh_id,
                                              vloaihd_id: LoaiHopDong.TACHNHAP_TB,
                                            });
                                          }
                                        } catch(err) {
                                          console.log(err)
                                        }
                                    this.loading(false)
                                    return;
                                }
                            if (kt1)
                            {
                                
                                    rs = await this.axios.post("web-hopdong/tachnhap_thuebao/Insert_TBCQ_VinaPhone", 
                                    { 
                                      dsHDKH: this.ConvertLower(this.dsHDKH),
                                      dsHDTB: this.ConvertLower(this.dsHDTB),
                                      dsHDTB_DD: this.ConvertLower(this.dsHDTB_DD),
                                      dsHDTT: this.ConvertLower(this.dsHDTT),
                                      dsDK_DVK: this.ConvertLower(this.dsDK_DVK),
                                      donvi_id: this.donvi_id,
                                      quoctich: this.quoctich,
                                      donviql_id: this.donviql_id,
                                      quantb_id: this.quantb_id,
                                      phuongtb_id: this.phuongtb_id,
                                      photb_id: this.photb_id,
                                      txtMaNVTC: this.$refs.txtMaNVTC.value,
                                      sonhatb: this.sonhatb
                                    })
                                    var kq = rs.data && rs.data.data ? rs.data.data : "";
                                    if (kq == "OK")    // Đẩy tiếp dữ liệu thuê bao lên VinaPhone. vuth update 19/12
                                    {
                                        this.$toast.success("Cập nhật dữ liệu thuê bao " + this.$refs.txtMaTB.value + " lên VinaPhone thành công!");
                                        
                                    }
                                    else 
                                    {
                                        this.$toast.success("Có lỗi xảy ra trong quá trình thêm mới hợp đồng thuê bao di động trên VinaPhone !\nLỗi :" + kq);
                                        try {
                                          if(type == 1) {
                                            await this.axios.post(
                                                  "web-hopdong/khoiphucthanhly/xoa_hdtb",
                                                  {
                                                    hdtb_id: this.hdtb_id,
                                                  }
                                                  );
                                          }
                                        } catch(err) {
                                          console.log(err)
                                        }
                                        var ma_gd_neo = this.dsHDKH[0]["ma_gd"];
                                        
                                            rs = await this.axios.post("web-hopdong/tachnhap_thuebao/Delete_HDCQ_VinaPhone", 
                                            { 
                                              ma_gd: ma_gd_neo
                                            })
                                            var kq2 = rs.data && rs.data.data ? rs.data.data : "";
                                            if (kq2 == "OK")
                                            {
                                                this.$toast.success("Xoá thành công hợp đồng có mã giao dịch :" + this.dsHDKH[0]["ma_gd"].toString() + " trên VinaPhone !");
                                                
                                            }
                                            if (j == 2)
                                                this.$toast.error("Có lỗi xảy ra trong quá trình Xoá hợp đồng có mã giao dịch :" + this.dsHDKH[0]["ma_gd"].toString() + " trên VinaPhone !\nLỗi" + kq2);
                                       
                                        this.loading(false)
                                        return; // vuth update 19/12
                                    }
                            }
                            //
                        }
                      
                      await this.axios.post("web-hopdong/hopdong/capnhat_status_hopdong_thuebao", {
                        hdtbId: this.hdtb_id,
                        status: TRANGTHAI_DONGBO.DONGBO_LHD
                      })

                    }
                    //

            
            this.$toast.success("Ghi lại thành công!")
          } else {
            this.$toast.error(rs.data.message)
          }
          if(this.$refs.txtMaGD.value)
            this.txtMaGD_KeyPress()
        } else {
          this.$refs.txtMaGD.value = ""
          this.$toast.error(
            rs.data.message.replaceAll("\\\\", /\\/g)
          );
        }
        

        //"Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công."

        //endregion
       
        
      this.loading(false)
      } catch (err) {
        this.loading(false)
        console.log(err);
      }
    },
    //endregion

    //Hiếu bổ sung tạo dữ liệu cho bảng thông tin tt& Ct_TIEN_HD 15/09/2010
    //region Tạo dữ liệu cho bảng THONGTIN_TT
    TaoDuLieu_THONGTIN_TT(themmoi) { },
    //endregion

    //region Tạo dữ liệu cho bảng CT_TIENHD
    async TaoDuLieu_CT_TIENHD(themmoi) {
      return new Promise(async (resolve, reject) => {
        try {
          this.dsCTTHD = [];

          //nhapt thêm
          for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
            let rowCTTHD = {};
            rowCTTHD.HDTB_ID = this.hdtb_id;
            if (themmoi == true) {
              rs = await this.axios.post("/web-hopdong/khoiphucthanhly/get_keys", {
                keyname: "CT_TIENHD",
              });
              rowCTTHD.CT_TIENHD_ID = rs.data.data;
            }
            rowCTTHD.KHOANMUCTT_ID = Number(
              this.ds_tien_khoanmuc[i]["khoanmuctt_id"]
            );
            rowCTTHD.TIEN = parseFloat(this.ds_tien_khoanmuc[i]["tien"]);
            rowCTTHD.VAT = parseFloat(this.ds_tien_khoanmuc[i]["vat"]);
            rowCTTHD.ID = Number(this.ds_tien_khoanmuc[i]["khoanmuctt_id"]);
            rowCTTHD.LOAI_ID = 1;
            this.dsCTTHD.push(rowCTTHD);
          }
        } catch(err) {
          console.log(err)
        }
        resolve(true)
      })
    },

    async TaoDuLieu_CT_TIENHD_DS(hdtb_id, thuebao_id) {
      //dsCTTHD = new CT_TIENHD_DATA();
      rs = await this.axios.post(
        "web-hopdong/tachnhapthuebao/sp_lay_tienkhoanmuc_loaihd",
        {
          loaihd_id: LoaiHopDong.TACHNHAP_TB,
          phanvung_id: this.$root.token.getPhanVungID(),
        }
      );
      this.ds_tien_khoanmuc = rs.data.data;
      //nhapt thêm
      for (let i = 0; i < ds_tien_khoanmuc.length; i++) {
        let rowCTTHD = {};
        rowCTTHD.HDTB_ID = hdtb_id;
        rs = await this.axios.post("/web-hopdong/khoiphucthanhly/get_keys", {
          keyname: "CT_TIENHD",
        });
        rowCTTHD.CT_TIENHD_ID = rs.data.data;

        rowCTTHD.KHOANMUCTT_ID = Number(
          this.ds_tien_khoanmuc[i]["khoanmuctt_id"]
        );
        rowCTTHD.TIEN = parseFloat(this.ds_tien_khoanmuc[i]["tien"]);
        rowCTTHD.VAT = parseFloat(this.ds_tien_khoanmuc[i]["vat"]);
        rowCTTHD.ID = Number(this.ds_tien_khoanmuc[i]["khoanmuctt_id"]);
        rowCTTHD.LOAI_ID = 1;
        this.dsCTTHD.push(rowCTTHD);
      }
    },
    //endregion

    //region XoaHDKH
    async XoaHDKH() {
      try {
        this.loading(true);
        await this.axios.post("/web-hopdong/hopdong/xoa_hdkh_v2", {
          vhdkh_id: this.hdkh_id,
          vloaihd_id: LoaiHopDong.TACHNHAP_TB,
        });
        this.loading(false);
        this.SetButton(0);
      } catch (err) {
        console.log(err);
      }
    },
    //endregion

    //region Them HD thuê bao
    async ThemHDThueBao() {
      try {
        if (!this.KiemTraDL_ThanhToan()) return;
        if (!this.KiemTraDL_ThueBao()) return;
        // if (!KiemTraDuLieuNhap()) return;//Hiếu thêm kiểm tra dl nhập 28.09.2010
        this.$refs.txtMaTB.focus();

        await this.axios.post(
          "web-hopdong/hopdong/fn_kt_tsbtnthemtb_tachnhaptb",
          {
            vthuebao_id: this.thuebao_id,
            vthanhtoan_id: 0,
            vma_tb: this.$refs.txtMaTB.value,
          }
        );

        //Lấy dữ liệu quy trình
        let huonggiao_tn = 0;
        this.quytrinh_id = 0;
        rs = await this.axios.post(
          "web-hopdong/hopdong/lay_huonggiao_tiepnhan",
          {
            kieuLdId: Number(this.$refs.cboKieuLD.value),
            loaiHdId: LoaiHopDong.TACHNHAP_TB,
            loaiTbId: Number(this.$refs.cboLoaihinhTB.value),
            mucCuocId: this.muccuoc_id,
            tocDoId: this.tocdo_id,
          }
        );

        let dset = rs.data.data;

        if (dset.length <= 0) {
          this.$toast.error(
            "Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!"
          );
          return false;
        }

        this.huonggiao_tn = Number(dset["huonggiao_id"]);
        this.quytrinh_id = Number(dset["quytrinh_id"]);
        //

        await this.TaoDuLieu_HDTT(false); //TuấnNA thêm code để cập nhật matb_dd trong nút thêm thuê bao -- ngày 18.03.2011
        await this.TaoDuLieu_HDTB(true);
        //TuấnNA thêm code tạo dữ liệu cho các dataset khác -- Ngày 02/08/2010
        //Thêm mới thongtintt, cttien Hiếu - 28.09.2010
        await this.TaoDuLieu_THONGTIN_TT(true);
        await this.TaoDuLieu_CT_TIENHD(true);
        //
        this.dsHDTB_DD = [];

        switch (this.dichvuvt_id) {
          case DichVuVienThong.DI_DONG:
            await this.TaoDuLieu_HDTB_DD(true);
            break;
        }

        this.TaoDuLieu_DiaChi(true, 3);

        //region Thêm thuê bao vào hợp đồng trên vinaphone
        let async_status = false; // Trạng thái có đồng bộ dữ liệu được không?
        //TuanNA - 28/07/2010: Thực hiện đẩy dữ liệu lên VinaPhone
        rs = await this.axios.post(
          "web-hopdong/hopdong/fn_themhdthuebao_tsbtnthemtb_tachnhaptb",
          {
            vds: JSON.stringify([
              {
                HUONGGIAO_TN: this.huonggiao_tn,
                THUEBAO_ID: this.thuebao_id,
                QUYTRINH_ID: this.quytrinh_id,
                MA_TB: this.$refs.txtMaTB.value,
                TEN_TB: this.$refs.txtTenTB.value,
                DIACHI_TB: this.$refs.txtDiaChiTB.value,
                HDKH_ID: this.hdkh_id,
                HDTT_ID: this.hdtt_id,
                DIACHI_LD: this.$refs.txtDiachiLD.value,
                DOITUONG_ID: this.doituong_id,
                NGAY_HT: moment().format("DD/MM/YYYY hh:mm:ss"),
                MAY_CN: this.$root.token.getUserName(),
                NGAY_CN: moment().format("DD/MM/YYYY hh:mm:ss"),
                NGUOI_CN: this.$root.token.getUserName(),
                IP_CN: '1.1.1.1',
                TOCDO_ID: this.tocdo_id,
                MUCCUOC_ID: this.muccuoc_id,
                KIEULD_ID: this.$refs.cboKieuLD.value,
                GOICUOC_ID: this.goicuoc_id,
                DICHVUVT_ID: this.dichvuvt_id,
                DICHVUVT_ID_CBO: this.$refs.cboDichVuVT.value,
                DONVI_ID: this.$root.token.getDonViID(),
                LOAITB_ID: this.loaitb_id,
                TINHLD_ID: this.tinhld_id,
                QUANLD_ID: this.quanld_id,
                PHUONGLD_ID: this.phuongld_id,
                PHOLD_ID: this.phold_id,
                APLD_ID: this.apld_id,
                KHULD_ID: this.khuld_id,
                DACDIEMLD_ID: this.dacdiemld_id,
                SONHALD: this.sonhald,
                DIACHILD: this.diachi_ld,
                BLOCKLD: this.blockld,
                TANGLD: this.tangld,
                SOPHONGLD: this.sophongld,
                MOTADCLD: this.motadcld,
                TINHTB_ID: this.tinhtb_id,
                QUANTB_ID: this.quantb_id,
                PHUONGTB_ID: this.phuongtb_id,
                PHOTB_ID: this.photb_id,
                APTB_ID: this.aptb_id,
                KHUTB_ID: this.khutb_id,
                DACDIEMTB_ID: this.dacdiemtb_id,
                SONHATB: this.sonha_tb,
                DIACHITB: this.diachi_tb,
                BLOCKLD: this.blockld,
                TANGLD: this.tangld,
                SOPHONGLD: this.sophongld,
                MOTADCLD: this.motadcld,
              },
            ]),
            vds_tien_khoanmuc: JSON.stringify(this.dsCTTHD),
            vdstb_dichvu: JSON.stringify(this.dsHDTB),
          }
        );

        if (rs.data.data && rs.data.data.length > 2) {
          this.$toast.error(rs.data.data)
          return
        }
        //region Cập nhật trạng thái đồng bộ lên vinaphone vào CSDL
        //Tuan them code cập nhật trạng thái đồng bộ dữ liệu lên vinaphone // Ngày 18/08/2010
        //Kiểm tra thuê bao là di động trả sau --> cho phép cập nhật
        if (Number(this.$refs.cboDichVuVT.value) == DichVuVienThong.DI_DONG && Number(this.$refs.cboLoaihinhTB.value) == LoaiHinhTB.DIDONGTRASAU)
                    {
                        let trangthai_dongbo = await this.axios.post("web-hopdong/hopdong/get_app_config",
                          {
                            "keyname": "OPEN_SYNC_VINA"
                          })
                        if (trangthai_dongbo.data && trangthai_dongbo.data && trangthai_dongbo.data.data.keyvalue == "TRUE")
                        {
                          await this.TaoDuLieu_HDKH(false);
                          await this.TaoDuLieu_HDTT(false);
                          let check_hd = await this.axios.post("web-hopdong/hopdong/kiemtra_hopdong_thuebao_dongbo_dulieu", {
                            maGd : this.$refs.txtMaGD.value,
                            dichVuVtId: this.dichvuvt_id,
                            loaiTbId: this.loaitb_id
                          })
                          if(check_hd.data && check_hd.data.data != "1") {
                            // Insert Hợp đồng, thuê bao trên vinaphone
                            var kt1 = false;
                                var kq = await this.axios.post("web-hopdong/tachnhap_thuebao/Insert_HDCQ_VinaPhone", { 
                                  dsHDKH: this.ConvertLower(this.dsHDKH),
                                  dsHDTB: this.ConvertLower(this.dsHDTB),
                                  dsHDTT: this.ConvertLower(this.dsHDTT),
                                  dsDK_DVK: this.ConvertLower(this.dsDK_DVK),
                                  ma_tb: this.$refs.txtMaTB.value,
                                  khachhang_id: this.khachhang_id,
                                  dvattn: this.dvattn,
                                  quoctich: this.quoctich,
                                  quankh_id: this.quankh_id,
                                  phuongkh_id: this.phuongkh_id,
                                  phokh_id: this.phokh_id,
                                  sonhakh: this.sonhakh,
                                  quantt_id: this.quantt_id,
                                  phuongtt_id: this.phuongtt_id,
                                  phott_id: this.phott_id,
                                  sonhatt: this.sonhatt,
                                  ten_tt: this.ten_tt,
                                  diachi_tt: this.diachi_tt,
                                  txtMaNVTC: this.$refs.txtMaNVTC.value
                                });
                                if (kq.data && kq.data.data == "OK")        // Nếu đẩy dữ liệu HD lên VinaPhone thành công. vuhth update 19/12
                                {
                                    kt1 = true;
                                }
                                else 
                                {
                                    this.$toast.error("Có lỗi xảy ra trong quá trình thêm mới hợp đồng khách hàng số: " + this.hdkh_id + " trên VinaPhone: " + kq);
                                    this.loading(false)
                                    return;
                                }
                            if (kt1)
                            {
                                
                                    rs = await this.axios.post("web-hopdong/tachnhap_thuebao/Insert_TBCQ_VinaPhone", 
                                    { 
                                      dsHDKH: this.ConvertLower(this.dsHDKH),
                                      dsHDTB: this.ConvertLower(this.dsHDTB),
                                      dsHDTB_DD: this.ConvertLower(this.dsHDTB_DD),
                                      dsDK_DVK: this.ConvertLower(this.dsDK_DVK),
                                      dsHDTT: this.ConvertLower(this.dsHDTT),
                                      donvi_id: this.donvi_id,
                                      quoctich: this.quoctich,
                                      donviql_id: this.donviql_id,
                                      quantb_id: this.quantb_id,
                                      phuongtb_id: this.phuongtb_id,
                                      photb_id: this.photb_id,
                                      txtMaNVTC: this.$refs.txtMaNVTC.value,
                                      sonhatb: this.sonhatb
                                    })
                                    kq = rs.data && rs.data.data ? rs.data.data : "";
                                    if (kq == "OK")    // Đẩy tiếp dữ liệu thuê bao lên VinaPhone. vuth update 19/12
                                    {
                                        this.$toast.success("Cập nhật dữ liệu thuê bao " + this.$refs.txtMaTB.value + " lên VinaPhone thành công!");
                                        this.loading(false)
                                    } else {
                                      this.$toast.success("Có lỗi xảy ra trong quá trình thêm mới hợp đồng thuê bao di động trên VinaPhone !\nLỗi :" + kq);
                                      var ma_gd_neo = this.dsHDKH[0]["ma_gd"].toString();

                                      rs = await this.axios.post("web-hopdong/tachnhap_thuebao/Delete_HDCQ_VinaPhone", 
                                      { 
                                        ma_gd: ma_gd_neo
                                      })
                                      var kq2 = rs.data && rs.data.data ? rs.data.data : "";
                                      if (kq2 == "OK")
                                      {
                                        this.$toast.success("Xoá thành công hợp đồng có mã giao dịch :" + this.dsHDKH[0]["ma_gd"].toString() + " trên VinaPhone !");

                                      } else {
                                        this.$toast.error("Có lỗi xảy ra trong quá trình Xoá hợp đồng có mã giao dịch :" + this.dsHDKH[0]["ma_gd"].toString() + " trên VinaPhone !\nLỗi" + kq2);
                                        this.loading(false)
                                        return
                                      }
                                    }
                            }
                          } else {
                            rs = await this.axios.post("web-hopdong/tachnhap_thuebao/Insert_TBCQ_VinaPhone", 
                                    { 
                                      dsHDKH: this.ConvertLower(this.dsHDKH),
                                      dsHDTB: this.ConvertLower(this.dsHDTB),
                                      dsHDTB_DD: this.ConvertLower(this.dsHDTB_DD),
                                      dsDK_DVK: this.ConvertLower(this.dsDK_DVK),
                                      dsHDTT: this.ConvertLower(this.dsHDTT),
                                      donvi_id: this.donvi_id,
                                      quoctich: this.quoctich,
                                      donviql_id: this.donviql_id,
                                      quantb_id: this.quantb_id,
                                      phuongtb_id: this.phuongtb_id,
                                      photb_id: this.photb_id,
                                      txtMaNVTC: this.$refs.txtMaNVTC.value,
                                      sonhatb: this.sonhatb
                                    })
                                    kq = rs.data && rs.data.data ? rs.data.data : "";
                                    if (kq == "OK")    // Đẩy tiếp dữ liệu thuê bao lên VinaPhone. vuth update 19/12
                                    {
                                        this.$toast.success("Cập nhật dữ liệu thuê bao " + this.$refs.txtMaTB.value + " lên VinaPhone thành công!");
                                        this.loading(false)
                                    } else {
                                      this.$toast.success("Có lỗi xảy ra trong quá trình thêm mới hợp đồng thuê bao di động trên VinaPhone !\nLỗi :" + kq);
                                      this.loading(false)
                                      return
                                    }
                          }
                            //
                        }
                      
                      await this.axios.post("web-hopdong/hopdong/capnhat_status_hopdong_thuebao", {
                        hdtbId: this.hdtb_id,
                        status: TRANGTHAI_DONGBO.DONGBO_LHD
                      })

                    }
        //endregion
        this.$toast.success(
          "Thêm thuê bao vào dữ liệu viễn thông tỉnh thành công ! "
        );
        await this.HienThiDS_HDTT_CBO(this.hdkh_id);
        rs = await this.axios.get(
          "web-hopdong/hopdong/lay_ds_hopdong_thanhtoan_theo_hdkh",
          {
            params: {
              hdKhId: this.hdkh_id,
            },
          }
        );
        this.HienThiDanhSacHDTT(rs.data.data);
        this.HT_DanhSachTB();
      } catch (err) {
        console.log(err);
      }
    },
    //endregion

    //region XoaHDTB
    async XoaHDTB() {
      try {
        //TuanNA them code xoá thuê bao tách ghép -- 29/07/2010
        var rs = await this.axios.post("web-hopdong/hopdong/get_app_config",
        {
          "keyname": "OPEN_SYNC_VINA"
        })
        if (rs.data && rs.data.data && rs.data.data.keyvalue == "TRUE") {
          //region Xoá thuê bao trên vinaphone
          if (this.$refs.lvwThueBao.getSelectedRecords().length > 0) {
            this.TaoDuLieu_HDTB(false);
            if (this.check_tb_dd) {
              //Nếu có 1 thuê bao di động thì xóa cả hợp đồng đi
              let count = 0;
              for (
                let i = 0;
                i < this.$refs.lvwThueBao.dataSource.length;
                i++
              ) {
                if (lvwThueBao[i]["dichvuvt_id"] == DichVuVienThong.DI_DONG) {
                  count++;
                }
              }
              if (count == 1) {
                let ma_gd_neo = this.$refs.txtMaGD.value;
                for (let i = 0; i < 3; i++) {
                  var result = await this.Delete_HDCQ_VinaPhone(ma_gd_neo);
                  if (result == "OK") {
                    this.$toast.error(
                      "Đã xóa thành công hợp đồng/phụ lục có mã giao dịch: " +
                      this.$refs.txtMaGD.value +
                      " trên VinaPhone."
                    );
                    break;
                  }
                  if (i == 2) {
                    this.$toast.error(
                      "Có lỗi: " +
                      result +
                      " khi thực hiện xóa hợp đồng/phụ lục có mã giao dịch: " +
                      this.$refs.txtMaGD.value +
                      " trên VinaPhone."
                    );
                    this.loading(false)
                    return;
                  }
                }
              } //Nếu có nhiều thuê bao di động
              else {
                let magd_neo = this.$refs.txtMaGD.value;
                var result = await this.Delete_TBCQ_VinaPhone(magd_neo);
                if (result == "OK") {
                  this.$toast.error(
                    "Đã xóa thành công số thuê bao: " +
                    this.dsHDTB[0]["ma_tb"] +
                    " trên VinaPhone."
                  );
                } else {
                  this.$toast.error(
                    result +
                    " \n Không thể xóa thuê bao " +
                    this.dsHDTB[0]["ma_tb"] +
                    " trên dữ liệu VinaPhone"
                  );
                  this.loading(false)
                  return;
                }
              }
            }
          } else {
            this.$toast.error("Bạn chưa chọn thuê bao để xoá !");
            this.loading(false)
            return;
          }
          //endregion
        }
        this.axios.post("web-hopdong/khoiphucthanhly/xoa_hdtb", {
          vhdtb_id: this.hdtb_id,
          vphanvung_id: this.$root.token.getPhanVungID(),
        });
        this.$toast.success(
          "Xóa thuê bao trên dữ liệu viễn thông tỉnh thành công ! "
        );
        this.HT_DanhSachTB();
        if (this.$refs.lvwThueBao.dataSource.length > 0) {
          //this.hdtb_id = Number(this.$refs.lvwThueBao.getSelectedRecords()[0].hdtb_id);
          rs = await this.axios.post(
            "web-hopdong/thaydoiloaihinhtb/lay_ds_hopdong_tb_theo_hdtb_id",
            {
              hdtb_id: this.hdtb_id,
            }
          );
          this.HienThiTTHopDongTB(rs.data.data);
          // HienThiTienTheoKhoanMuc(hdtb_id, tien_cd, loaihd_id, dichvuvt_id, khlon_id, loaitb_id, kieuld_id, doituong_id, ngaylhd, nhaphd_kt, phuongld_id);
        }
      } catch (err) {
        console.log(err);
      }
    },
    //endregion

    //region Tạo dữ liệu cho Dataset
    async TaoDuLieu(themmoi) {
      return new Promise(async (resolve, reject) => {
        await this.TaoDuLieu_HDKH(themmoi);
        await this.TaoDuLieu_HDTT(themmoi);
        this.TaoDuLieu_HDTB(themmoi);
        await this.TaoDuLieu_DiaChi(themmoi, 4);

        await this.TaoDuLieu_HDTT_NV(themmoi);
        //Hiếu - 28.09.2010: bổ sung thêm insert thông tin thanh toán, ct_tienhd
        await this.TaoDuLieu_THONGTIN_TT(themmoi);
        await this.TaoDuLieu_CT_TIENHD(true);
        //
        await this.TaoDuLieu_DKDV();
        //   CapNhat_HDTB_ID_DangKyDV(hdtb_id);

        //TuấnNA thêm code tạo dữ liệu cho các dataset khác -- Ngày 02/08/2010
        this.dsHDTB_CD = [];
        this.dsHDTB_DD = [];
        this.dsHDTB_ = [];
        this.dsHDTB_ADSL = [];
        this.dsHDTB_GP = [];
        this.dsHDTB_CNTT = [];
        switch (this.dichvuvt_id) {
          case DichVuVienThong.DI_DONG:
            this.TaoDuLieu_HDTB_DD(themmoi);
            break;
          //case DichVuVienThong.INTERNET:
          //    TaoDuLieu_HDTB_INT(themmoi);
          //    break;
          //case DichVuVienThong.ADSL:
          //    TaoDuLieu_HDTB_ADSL(themmoi);
          //    break;
          //case DichVuVienThong.GPHONE:
          //    TaoDuLieu_HDTB_GP(themmoi);
          //    break;
          //default:
          //    TaoDuLieu_HDTB_CD(themmoi);
          //    break;
        }
        resolve(true);
      });
    },

    //region  Tao du lieu cho bang HD_KHACHHANG
    async TaoDuLieu_HDKH(themmoi) {
      return new Promise(async (resolve, reject) => {
        let vmagd_temp = "";
        this.dsHDKH = [];
        if (themmoi == true) {
          let rs = await this.axios.post(
            "/web-hopdong/khoiphucthanhly/get_keys",
            {
              keyname: "HD_KHACHHANG",
            }
          );
          this.hdkh_id = rs.data.data;
        }

        let rowHDKH = {};
        rowHDKH.HDKH_ID = this.hdkh_id;
        if (themmoi == true) {
          if (this.ts_sinhma_gd_theo_donvi) {
            rs = await this.axios.post(
              "/web-hopdong/thongtin_tratruoc_dn/sinh_magd_v2",
              {
                vdonvi_id: this.$root.token.getDonViID(),
                vloaihd_id: LoaiHopDong.TACHNHAP_TB,
              }
            );
            vmagd_temp = rs.data.data;
          } else {
            rs = await this.axios.post(
              "/web-hopdong/khoiphucthanhly/sinh_magd",
              {
                loaihd_id: LoaiHopDong.TACHNHAP_TB,
              }
            );
            vmagd_temp = rs.data.data;
          }
          if (!vmagd_temp || vmagd_temp.includes("ERROR:")) {
            this.$toast.error(vmagd_temp.split(":")[1]);
            this.loi_sinh_magd = true;
          }
          // this.$refs.txtMaGD.value = vmagd_temp;
          // this.ma_gd = this.$refs.txtMaGD.value;
          rowHDKH.MA_GD = vmagd_temp;
          rowHDKH.MA_HD = this.$refs.txtMaHD.value;
        } else {
          rowHDKH.MA_GD = this.$refs.txtMaGD.value;
          rowHDKH.MA_HD = this.$refs.txtMaHD.value;
        }
        rowHDKH.MA_KH = this.$refs.txtMaKH.value;
        if (this.khachhang_id > 0) rowHDKH.KHACHHANG_ID = this.khachhang_id;
        rowHDKH.TEN_KH = this.$refs.txtTenKH.value;
        rowHDKH.DIACHI_KH = this.$refs.txtDiaChiKH.value;
        rowHDKH.NGAYLAP_HD = this.checkDate(this.$refs.dtpNgayYC.value) ? this.$refs.dtpNgayYC.value : moment(this.$refs.dtpNgayYC.value).format("DD/MM/YYYY HH:mm:ss");
        rowHDKH.DONVI_ID = this.$root.token.getDonViID();
        rowHDKH.NHANVIEN_ID = this.$root.token.getNhanVienID();
        rowHDKH.KHLON_ID = 1;
        rowHDKH.LOAIHD_ID = LoaiHopDong.TACHNHAP_TB;
        rowHDKH.NGUOI_CN = this.$root.token.getUserName();
        (rowHDKH.NGAY_CN = moment().format("DD/mm/yyyy hh:mm:ss")),
          (rowHDKH.MAY_CN = this.$root.token.getMaNhanVien());
        rowHDKH.IP_CN = "1.1.1.1";
        //New change
        if (this.$refs.chkNguoiGT.checked && this.$refs.txtNguoiGT.value) {
          rowHDKH.NHANVIENGT_ID = this.nguoigt_id;
        }
        //

        ///TuấnNA thêm dữ liệu vào dataset -- Ngày 30/07/2010
        rowHDKH.NGAY_YC = this.$refs.dtpNgayYC.value;
        if (this.gioitinh != "") rowHDKH.GIOITINH = Number(this.gioitinh);
        if (this.loaikh_id != "")
          rowHDKH.LOAIKH_ID = parseFloat(this.loaikh_id);
        if (this.loaigt_id != "")
          rowHDKH.LOAIGT_ID = parseFloat(this.loaigt_id);
        if (this.ngaycap != "") {
          rowHDKH.NGAYCAP = this.checkDate(this.ngaycap) ? this.ngaycap : moment(this.ngaycap).format("DD/MM/YYYY HH:mm:ss");
        }
        //if (ngay_sn != "")
        //{
        //    rowHDKH.NGAY_SN = (ngay_sn);
        //}
        //Hieutc: 28.12.2010: Thêm đoạn lấy tiểu ngành trong danh bạ
        let TNC1_ID = 0;
        let TNC2_ID = 0;
        let TNC3_ID = 0;
        let daset = [];
        rs = await this.axios.post(
          "web-hopdong/phattrienthuebao/lay_ds_dbkh_theo_khid",
          {
            ID: this.khachhang_id,
          }
        );
        daset = (rs.data && rs.data.data) ? rs.data.data : [];
        if (daset.length > 0) {
          if (daset[0]["tnc1_id"] != "") {
            TNC1_ID = Number(daset[0]["tnc1_id"]);
            rowHDKH.TNC1_ID = TNC1_ID;
          }
          if (daset[0]["tnc2_id"] != "") {
            TNC2_ID = Number(daset[0]["tnc2_id"]);
            rowHDKH.TNC2_ID = TNC2_ID;
          }
          if (daset[0]["tnc3_id"] != "") {
            TNC3_ID = Number(daset[0]["tnc3_id"]);
            rowHDKH.TNC3_ID = TNC3_ID;
          }
          if (daset[0]["NGANHNGHE_ID"] != "") {
            rowHDKH.NGANHNGHE_ID = Number(daset[0]["NGANHNGHE_ID"]);
          }
          let ingay_sn = "";
          if (daset[0]["ngay_sn"] != "") {
            ingay_sn = daset[0]["ngay_sn"];
            rowHDKH.NGAY_SN = ingay_sn;
          }
        }
        //
        this.dsHDKH.push(rowHDKH);
        resolve(true);
      });
    },
    //endregion

    //region Tao du lieu cho bang HD_THANHTOAN
    async TaoDuLieu_HDTT(themmoi) {
      return new Promise(async (resolve, reject) => {
        this.dsHDTT = [];

        if (themmoi == true) {
          rs = await this.axios.post("/web-hopdong/khoiphucthanhly/get_keys", {
            keyname: "HD_THANHTOAN",
          });
          this.hdtt_id = rs.data.data;
        }
        let rowHDTT = {};
        rowHDTT.HDTT_ID = this.hdtt_id;
        rowHDTT.HDKH_ID = this.hdkh_id;
        if (this.$refs.txtMaTT.value == "") {
          rs = await this.axios.post(
            "web-hopdong/thong-tin-kenh-trang/sinh_matt_v2",
            {
              vphanvung_id: this.$root.token.getPhanVungID(),
              vdonvi_id: this.$refs.cboDonViQL.value,
              vquan_id: this.quanld_id,
            }
          );
          this.$refs.txtMaTT.value = rs.data.data; //donviql_id TuanNA sửa code bỏ cboDonViQL -- Ngày 30/07/2010
          this.thanhtoan_id = 0;
        }
        rowHDTT.MA_TT = this.$refs.txtMaTT.value;
        if (this.thanhtoan_id > 0) rowHDTT.THANHTOAN_ID = this.thanhtoan_id;
        rowHDTT.TEN_TT = this.$refs.txtTenTT.value;
        rowHDTT.DIACHI_TT = this.$refs.txtDiaChiTT.value;
        rowHDTT.DIACHI_BC = this.$refs.txtDiaChiBC.value;
        rowHDTT.MATB_DD = this.$refs.cboMaDD.value;
        rowHDTT.MST = this.$refs.txtMST_TT.value;
        rowHDTT.HTTT_ID = Number(this.$refs.cboHTTT.value);
        rowHDTT.STK = this.$refs.txtSTK_TT.value;
        if (Number(this.$refs.cboHTTT.value) == HinhThucThanhToan.UYNHIEM_THU) {
          rowHDTT.NGANHANG_ID = Number(this.$refs.cboNganHang.value);
        }
        rowHDTT.DONVI_ID = Number(this.$refs.cboDonViQL.value);
        if (this.tuyenthu_id != 0) rowHDTT.TUYENTHU_ID = this.tuyenthu_id;
        rowHDTT.NGUOI_CN = this.$root.token.getUserName();
        (rowHDTT.NGAY_CN = moment().format("DD/mm/yyyy hh:mm:ss")),
          (rowHDTT.MAY_CN = this.$root.token.getMaNhanVien());
        ///TuấnNA thêm dữ liệu cho dataset dsHDTT -- Ngày 30/07/2010
        rowHDTT.DIACHI_CT = this.$refs.txtDiachiCT.value;

        //rowHDTT.NHANVIEN_ID = nhanvientc_id;
        rowHDTT.GHICHU = this.$refs.txtGhiChu.value;

        //TuấnNA thêm code tạo dữ liệu NSNN
        rs = await this.axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nganhang",
        { param: this.$refs.cboNganHang.value, type: 1 });
        let loai_nh = rs.data.data;
        if (loai_nh == "2") {
          rowHDTT.DV_QHNS = this.DV_QHNS;
          rowHDTT.MACHUONG = this.MACHUONG;
          rowHDTT.MN_KT = this.MN_KT;
          rowHDTT.MA_NDKT = this.MA_NDKT;
          rowHDTT.MN_NSNN = this.MN_NSNN;
          rowHDTT.TIEN_DM = this.TIEN_DM;
        }
        //

        this.dsHDTT.push(rowHDTT);
        resolve(true);
      });
    },
    //endregion

    //region Tao du lieu cho bang HD_THUEBAO
    async TaoDuLieu_HDTB(themmoi) {
      return new Promise(async (resolve, reject) => {
        this.dsHDTB = [];

        if (themmoi == true) {
          rs = await this.axios.post("/web-hopdong/khoiphucthanhly/get_keys", {
            keyname: "HD_THUEBAO",
          });
          this.hdtb_id = rs.data.data;
        }
        let rowHDTB = {};

        rowHDTB.HDTB_ID = this.hdtb_id;
        rowHDTB.HDTT_ID = this.hdtt_id;
        rowHDTB.HDKH_ID = this.hdkh_id;
        if (this.thuebao_id > 0) rowHDTB.THUEBAO_ID = this.thuebao_id;

        rowHDTB.MA_TB = this.$refs.txtMaTB.value;

        ///Tuấn NA thêm code lấy giá trị datcoc_csd // Ngày 20/10/2010
        //rowHDTB.DATCOC_CSD = 0;

        rowHDTB.TEN_TB = this.$refs.txtTenTB.value;
        rowHDTB.DIACHI_TB = this.$refs.txtDiaChiTB.value;
        rowHDTB.DIACHI_LD = this.$refs.txtDiachiLD.value; ///TuấnNA thêm địa chỉ lắp đặt vào dataset dsHDTB // Ngày 19/08/2010
        //rowHDTB.GHICHU = txtGhiChuTB.value;
        rowHDTB.KIEULD_ID = Number(this.$refs.cboKieuLD.value);
        rowHDTB.DOITUONG_ID = Number(this.$refs.cboDoituongTB.value);
        rowHDTB.TTHD_ID = TrangThaiHD.MOI;
        rowHDTB.NGAY_HT = this.checkDate(this.$refs.dtpNgayLapHD.value) ? this.$refs.dtpNgayLapHD.value : moment(this.$refs.dtpNgayLapHD.value).format("DD/MM/YYYY HH:mm:ss");
        rowHDTB.LOAITB_ID = this.loaitb_id;
        rowHDTB.DICHVUVT_ID = this.dichvuvt_id;
        rowHDTB.DONVI_ID = this.donvi_id;
        rowHDTB.NGUOI_CN = this.$root.token.getUserName();
        rowHDTB.NGAY_CN = moment().format("DD/mm/yyyy hh:mm:ss");
        rowHDTB.MAY_CN = this.$root.token.getMaNhanVien();
        rowHDTB.IP_CN = "1.1.1.1";
        rowHDTB.QUYTRINH_ID = this.quytrinh_id;

        if (themmoi == true) {
          if (rowHDTB.DICHVUVT_ID == DichVuVienThong.DI_DONG) this.status = "0";
          else this.status = "";
          rowHDTB.STATUS = this.status;
        } else rowHDTB.STATUS = this.status;
        //Hiếu bổ sung trường tiền đặt cọc cước sử dụng = 0 - 20.10.2010
        //rowHDTB.DATCOC_CSD = 0;
        this.dsHDTB.push(rowHDTB);
        resolve(true);
      });
    },

    async TaoDuLieu_HDTB_DS(hdtb_id, thuebao_id) {
      return new Promise(async (resolve, reject) => {
        let quytrinh_id_2 = 0;
        let huonggiaotn_id = 0;

        //dsHDTB = new HD_THUEBAO_DATA();
        //hdtb_id = new GetKeyFacade().GetKey("HD_THUEBAO");
        let rowHDTB = {};
        rs = await this.axios.get(
          "web-hopdong/hopdong/lay_thongtin_chuyenquyen_theo_tbid",
          {
            params: {
              thueBaoId: this.thuebao_id,
            },
          }
        );
        let dsTT = rs.data.data;

        if (dsTT != null && dsTT.length > 0) {
          let row = dsTT[0];
          rowHDTB.HDTB_ID = hdtb_id;
          rowHDTB.HDTT_ID = hdtt_id;
          rowHDTB.HDKH_ID = hdkh_id;

          rowHDTB.THUEBAO_ID = thuebao_id;

          //rowHDTB.MA_TB = this.$refs.txtMaTB.value;

          ///Tuấn NA thêm code lấy giá trị datcoc_csd // Ngày 20/10/2010
          //rowHDTB.DATCOC_CSD = 0;

          rowHDTB.TEN_TB = this.$refs.txtTenTB.value;
          rowHDTB.DIACHI_TB = this.$refs.txtDiaChiTB.value;
          rowHDTB.DIACHI_LD = row["diachi_ld"]; ///TuấnNA thêm địa chỉ lắp đặt vào dataset dsHDTB // Ngày 19/08/2010
          //rowHDTB.GHICHU = txtGhiChuTB.value;
          rowHDTB.KIEULD_ID = Number(cboKieuLD.value);
          rowHDTB.DOITUONG_ID = Number(row["doituong_id"]); //Number(cboDoituongTB.value);
          rowHDTB.TTHD_ID = TrangThaiHD.MOI;
          rowHDTB.NGAY_HT = this.checkDate(this.$refs.dtpNgayLapHD.value) ? this.$refs.dtpNgayLapHD.value : moment(this.$refs.dtpNgayLapHD.value).format("DD/MM/YYYY HH:mm:ss");
          //rowHDTB.LOAITB_ID = loaitb_id;
          //rowHDTB.DICHVUVT_ID = dichvuvt_id;
          rowHDTB.DONVI_ID = Number(row["donviql_id"]);
          rowHDTB.NGUOI_CN = this.$root.token.getUserName();
          rowHDTB.NGAY_CN = moment().format("DD/MM/YYYY HH:mm:ss");
          rowHDTB.MAY_CN = this.tt_nd.may_cn;
          rowHDTB.IP_CN = this.tt_nd.ip;

          rs = await this.axios.post(
            "web-hopdong/hopdong/lay_huonggiao_tiepnhan",
            {
              kieuLdId: Number(this.$refs.cboKieuLD.value),
              loaiHdId: LoaiHopDong.TACHNHAP_TB,
              loaiTbId: Number(this.$refs.cboLoaihinhTB.value),
              mucCuocId: this.muccuoc_id,
              tocDoId: this.tocdo_id,
            }
          );

          let dset = rs.data.data;
          if (dset.length > 0) {
            huonggiaotn_id = Number(dset["huonggiao_id"]);
            quytrinh_id_2 = Number(dset["quytrinh_id"]);
          }

          rowHDTB.QUYTRINH_ID = quytrinh_id_2;

          if (rowHDTB.DICHVUVT_ID == DichVuVienThong.DI_DONG) this.status = "0";
          else this.status = "";
          rowHDTB.STATUS = this.status;

          //Hiếu bổ sung trường tiền đặt cọc cước sử dụng = 0 - 20.10.2010
          //rowHDTB.DATCOC_CSD = 0;
          this.dsHDTB.push(rowHDTB);
        }
        resolve(huonggiaotn_id);
      });
    },
    //endregion

    //region Tao du lieu cho Dia chi
    async TaoDuLieu_DiaChi(themmoi, kieu) {
      return new Promise(async (resolve, reject) => {
        this.dsDiaChi = [];
        this.dsDiaChiHDKH = [];
        this.dsDiaChiHDTT = [];
        this.dsDiaChiHDTB = [];

        if (kieu == 1 || kieu == 4) {
          let rowDiaChiKH = {};
          if (themmoi == true) {
            rs = await this.axios.post(
              "/web-hopdong/khoiphucthanhly/get_keys",
              {
                keyname: "DIACHI",
              }
            );
            this.diachikh_id = rs.data.data;
          }
          rowDiaChiKH.DIACHI_ID = this.diachikh_id;
          if (this.tinhkh_id != 0) rowDiaChiKH.TINH_ID = this.tinhkh_id;
          rowDiaChiKH.QUAN_ID = this.quankh_id;
          if (this.phuongkh_id != 0) rowDiaChiKH.PHUONG_ID = this.phuongkh_id;
          if (this.phokh_id != 0) rowDiaChiKH.PHO_ID = this.phokh_id;
          if (this.apkh_id != 0) rowDiaChiKH.AP_ID = this.apkh_id;
          if (this.khukh_id != 0) rowDiaChiKH.KHU_ID = this.khukh_id;
          if (this.dacdiemkh_id != 0)
            rowDiaChiKH.DACDIEM_ID = this.dacdiemkh_id;

          rowDiaChiKH.SONHA = this.sonhakh;
          rowDiaChiKH.DIACHI = this.$refs.txtDiaChiKH.value;
          this.dsDiaChi.push(rowDiaChiKH);

          if (themmoi == true) {
            let rowDiaChiHDKH = {};
            rowDiaChiHDKH.DIACHI_ID = this.diachikh_id;
            rowDiaChiHDKH.HDKH_ID = this.hdkh_id;
            this.dsDiaChiHDKH.push(rowDiaChiHDKH);
          }
        }

        if (kieu == 2 || kieu == 4) {
          let rowDiaChiTT = {};
          if (themmoi == true) {
            rs = await this.axios.post(
              "/web-hopdong/khoiphucthanhly/get_keys",
              {
                keyname: "DIACHI",
              }
            );
            this.diachitt_id = rs.data.data;
          }
          rowDiaChiTT.DIACHI_ID = this.diachitt_id;
          if (this.tinhtt_id != 0) rowDiaChiTT.TINH_ID = this.tinhtt_id;
          rowDiaChiTT.QUAN_ID = this.quantt_id;
          if (this.phuongtt_id != 0) rowDiaChiTT.PHUONG_ID = this.phuongtt_id;
          if (this.phott_id != 0) rowDiaChiTT.PHO_ID = this.phott_id;
          if (this.aptt_id != 0) rowDiaChiTT.AP_ID = this.aptt_id;
          if (this.khutt_id != 0) rowDiaChiTT.KHU_ID = this.khutt_id;
          if (this.dacdiemtt_id != 0)
            rowDiaChiTT.DACDIEM_ID = this.dacdiemtt_id;

          ///
          rowDiaChiTT.SONHA = this.sonhatt;
          rowDiaChiTT.DIACHI = this.$refs.txtDiaChiTT.value;
          this.dsDiaChi.push(rowDiaChiTT);

          if (themmoi == true) {
            let rowDiaChiHDTT = {};
            rowDiaChiHDTT.DIACHI_ID = this.diachitt_id;
            rowDiaChiHDTT.HDTT_ID = this.hdtt_id;
            this.dsDiaChiHDTT.push(rowDiaChiHDTT);
          }
        }

        if (kieu == 3 || kieu == 4) {
          let rowDiaChiTB = {};
          let rowDiachiLD = {};
          if (themmoi == true) {
            rs = await this.axios.post(
              "/web-hopdong/khoiphucthanhly/get_keys",
              {
                keyname: "DIACHI",
              }
            );
            this.diachitb_id = rs.data.data;
            rs = await this.axios.post(
              "/web-hopdong/khoiphucthanhly/get_keys",
              {
                keyname: "DIACHI",
              }
            );
            this.diachild_id = rs.data.data;
          }

          //Địa chỉ ld
          rowDiachiLD.DIACHI_ID = this.diachild_id;
          if (this.tinhld_id != 0) rowDiachiLD.TINH_ID = this.tinhld_id;
          rowDiachiLD.QUAN_ID = this.quanld_id;
          if (this.phuongld_id != 0) rowDiachiLD.PHUONG_ID = this.phuongld_id;
          if (this.phold_id != 0) rowDiachiLD.PHO_ID = this.phold_id;
          else rowDiachiLD.PHO_ID = 0;

          if (this.apld_id != 0) rowDiachiLD.AP_ID = this.apld_id;
          else rowDiachiLD.AP_ID = 0;

          if (this.khuld_id != 0) rowDiachiLD.KHU_ID = this.khuld_id;
          else rowDiachiLD.KHU_ID = 0;

          if (this.dacdiemld_id != 0)
            rowDiachiLD.DACDIEM_ID = this.dacdiemld_id;
          //else
          //    rowDiachiLD.DACDIEM_ID = 0;
          //====================================
          rowDiachiLD.BLOCK = this.blockld;
          rowDiachiLD.TANG = this.tangld;
          rowDiachiLD.PHONG = this.sophongld;
          rowDiachiLD.MOTA = this.motadcld;
          //====================================

          rowDiachiLD.SONHA = this.sonhald;
          rowDiachiLD.DIACHI = this.$refs.txtDiachiLD.value;
          this.dsDiaChi.push(rowDiachiLD);

          //Địa chỉ thuê bao
          rowDiaChiTB.DIACHI_ID = this.diachitb_id;
          if (this.tinhtb_id != 0) rowDiaChiTB.TINH_ID = this.tinhtb_id;
          rowDiaChiTB.QUAN_ID = this.quantb_id;
          if (this.phuongtb_id != 0) rowDiaChiTB.PHUONG_ID = this.phuongtb_id;
          if (this.photb_id != 0) rowDiaChiTB.PHO_ID = this.photb_id;
          if (this.aptb_id != 0) rowDiaChiTB.AP_ID = this.aptb_id;
          if (this.khutb_id != 0) rowDiaChiTB.KHU_ID = this.khutb_id;
          if (this.dacdiemtb_id != 0)
            rowDiaChiTB.DACDIEM_ID = this.dacdiemtb_id;
          rowDiaChiTB.SONHA = this.sonhatb;
          rowDiaChiTB.DIACHI = this.$refs.txtDiaChiTB.value;
          //====================================
          rowDiaChiTB.BLOCK = this.blocktb;
          rowDiaChiTB.TANG = this.tangtb;
          rowDiaChiTB.PHONG = this.sophongtb;
          rowDiaChiTB.MOTA = this.motadctb;
          //====================================
          this.dsDiaChi.push(rowDiaChiTB);

          if (themmoi == true) {
            let rowDiaChiHDTB = {};
            rowDiaChiHDTB.DIACHI_ID = this.diachitb_id;
            rowDiaChiHDTB.HDTB_ID = this.hdtb_id;
            rowDiaChiHDTB.DIACHILD_ID = this.diachild_id;
            this.dsDiaChiHDTB.push(rowDiaChiHDTB);
          }
        }
        resolve(true);
      });
    },

    async TaoDuLieu_DiaChi_DS(hdtb_id, thuebao_id) {
      rs = await this.axios.post(
        "web-thicong/thicong/lay_ds_diachi_theo_dbtbid",
        {
          thuebao_id: this.thuebao_id,
        }
      );
      this.HienThiTTDiaChi(rs.data.data, 3);
      rs = await this.axios.get(
        "web-hopdong/hopdong/lay_thongtin_chuyenquyen_theo_tbid",
        {
          params: {
            thueBaoId: this.thuebao_id,
          },
        }
      );
      let dsTT = rs.data.data;

      if (dsTT != null && dsTT.length > 0) {
        let row = dsTT[0];
        let rowDiaChiTB = {};
        let rowDiachiLD = {};
        rs = await this.axios.post("/web-hopdong/khoiphucthanhly/get_keys", {
          keyname: "DIACHI",
        });
        this.diachitb_id = rs.data.data;
        rs = await this.axios.post("/web-hopdong/khoiphucthanhly/get_keys", {
          keyname: "DIACHI",
        });
        this.diachild_id = rs.data.data;

        //Địa chỉ ld
        rowDiachiLD.DIACHI_ID = this.diachild_id;
        if (this.tinhld_id != 0) rowDiachiLD.TINH_ID = this.tinhld_id;
        rowDiachiLD.QUAN_ID = this.quanld_id;
        if (this.phuongld_id != 0) rowDiachiLD.PHUONG_ID = this.phuongld_id;
        if (this.phold_id != 0) rowDiachiLD.PHO_ID = this.phold_id;
        else rowDiachiLD.PHO_ID = 0;

        if (this.apld_id != 0) rowDiachiLD.AP_ID = this.apld_id;
        else rowDiachiLD.AP_ID = 0;

        if (this.khuld_id != 0) rowDiachiLD.KHU_ID = this.khuld_id;
        else rowDiachiLD.KHU_ID = 0;

        if (this.dacdiemld_id != 0) rowDiachiLD.DACDIEM_ID = this.dacdiemld_id;
        //else
        //    rowDiachiLD.DACDIEM_ID = 0;

        rowDiachiLD.SONHA = this.sonhald;
        rowDiachiLD.DIACHI = row["diachi_ld"];
        dsDiaChi.DIACHI.pushDIACHIRow(rowDiachiLD);

        //Địa chỉ thuê bao
        rowDiaChiTB.DIACHI_ID = this.diachitb_id;
        if (this.tinhtb_id != 0) rowDiaChiTB.TINH_ID = this.tinhtb_id;
        rowDiaChiTB.QUAN_ID = this.quantb_id;
        if (this.phuongtb_id != 0) rowDiaChiTB.PHUONG_ID = this.phuongtb_id;
        if (this.photb_id != 0) rowDiaChiTB.PHO_ID = this.photb_id;
        if (this.aptb_id != 0) rowDiaChiTB.AP_ID = this.aptb_id;
        if (this.khutb_id != 0) rowDiaChiTB.KHU_ID = this.khutb_id;
        if (this.dacdiemtb_id != 0) rowDiaChiTB.DACDIEM_ID = this.dacdiemtb_id;
        rowDiaChiTB.SONHA = this.sonhatb;
        rowDiaChiTB.DIACHI = row["diachi_tb"];
        this.dsDiaChi.push(rowDiaChiTB);

        let rowDiaChiHDTB = {};
        rowDiaChiHDTB.DIACHI_ID = diachitb_id;
        rowDiaChiHDTB.HDTB_ID = hdtb_id;
        rowDiaChiHDTB.DIACHILD_ID = diachild_id;
        this.dsDiaChiHDTB.push(rowDiaChiHDTB);
      }
    },
    //endregion

    /// <summary>
    /// Tuấn NA thêm code tạo dữ liệu cho các dataset -- Ngày 02/08/2010
    /// </summary>

    //region Cap nhat HDTB_ID cho cac bang dangky_dvgt, dangky_dvk
    CapNhat_HDTB_ID_DangKyDV(hdtb_id) {
      //   let ds2 = this.dsDK_DVK;
      //   dsDK_DVK = new DANGKY_DVK_DATA();
    },
    //endregion

    //region Tao du lieu cho bang HDTB_CD
    async TaoDuLieu_HDTB_CD(themmoi) {
      this.dsHDTB_CD = [];
      let rowHDTB_CD = {};
      if (themmoi == false) {
        rs = await this.axios.get(
          "web-hopdong/hopdong/lay_ds_hopdong_thuebao_codinh",
          {
            params: {
              hdtbId: this.hdtb_id,
            },
          }
        );
        rowHDTB_CD = rs.data ? rs.data.data[0] : {};
      } else {
        rs = await this.axios.get("web-hopdong/hopdong/lay_ds_danhba_codinh", {
          params: {
            thueBaoId: this.thuebao_id,
          },
        });
        rowHDTB_CD = rs.data ? rs.data.data[0] : {};
      }

      rowHDTB_CD.HDTB_ID = this.hdtb_id;

      this.dsHDTB_CD.push(rowHDTB_CD);
    },
    //endregion

    //region Tao du lieu cho bang HDTB_DD
    async TaoDuLieu_HDTB_DD(themmoi) {
      return new Promise(async (resolve, reject) => {
        try {
          this.dsHDTB_DD = [];
          let rowHDTB_DD = {};
          if (themmoi == false) {
            rs = await this.axios.get(
              "web-ccdv/hoanthienhschuyenquyen/lay_ds_hdtb_dd_theo_hdtb_id?hdtb_id=" + this.hdtb_id
            );
            rowHDTB_DD = rs.data ? rs.data.data[0] : {};
          } else {
            rs = await this.axios.get("web-hopdong/hopdong/lay_ds_danhba_dd", {
              params: {
                thueBaoId: this.thuebao_id,
              },
            });

            rowHDTB_DD = rs.data ? rs.data.data[0] : {};
          }

          rowHDTB_DD.HDTB_ID = this.hdtb_id;
          if (this.goicuoc_id == "") {
            rs = await this.axios.get(
              "web-hopdong/hopdong/lay_ds_hopdong_thuebao_theo_hdkh",
              {
                params: {
                  hdKhId: this.hdkh_id,
                },
              }
            );
            this.goicuoc_id = rs.data ? rs.data.data[0]["goicuoc_id"] : "0";
          }
          rowHDTB_DD.GOICUOC_ID = Number(this.goicuoc_id); // TuanNA - Them column GOICUOC_ID : cboGoiCuocDD.value -- Ngay 15/07/2010
          this.dsHDTB_DD.push(rowHDTB_DD);
        } catch(err) {
          console.log(err)
        }
        resolve(true)
      })
    },

    async TaoDuLieu_HDTB_DD_DS(hdtb_id, thuebao_id) {
      //dsHDTB_DD = new HDTB_DD_DATA();
      let rowHDTB_DD = {};
      rs = await this.axios.get("web-hopdong/hopdong/lay_ds_danhba_dd", {
        params: {
          thueBaoId: this.thuebao_id,
        },
      });
      rowHDTB_DD = rs.data ? rs.data.data[0] : {};

      rowHDTB_DD.HDTB_ID = hdtb_id;
      if (this.goicuoc_id == "") {
        rs = await this.axios.get(
          "web-hopdong/hopdong/lay_ds_hopdong_thuebao_theo_hdkh",
          {
            params: {
              hdKhId: this.hdkh_id,
            },
          }
        );
        this.goicuoc_id = rs.data ? rs.data[0]["goicuoc_id"] : "0";
      }
      rowHDTB_DD.GOICUOC_ID = Number(this.goicuoc_id); // TuanNA - Them column GOICUOC_ID : cboGoiCuocDD.value -- Ngay 15/07/2010
      this.dsHDTB_DD.push(rowHDTB_DD);
    },
    //endregion

    //region Tao du lieu cho bang HDTB_INT
    async TaoDuLieu_HDTB_INT(themmoi) {
      this.dsHDTB_ = [];
      let rowHDTB_ = {};

      if (themmoi == false)
        cmUlt.CopyRow(
          tchopdong.LAY_DS_HDTB_INT_THEO_HDTB_ID(hdtb_id)[0],
          rowHDTB_INT
        );
      else
        cmUlt.CopyRow(
          tcdanhba.LAY_DS_DB_INT_THEO_TBID(thuebao_id)[0],
          rowHDTB_INT
        );

      rowHDTB_INT.HDTB_ID = hdtb_id;
      ///Tuấn sửa code tạo dữ liệu dataset -- Ngày 02/08/2010
      rowHDTB_INT.EMAIL = email_int; //txtEmailInt.value;

      dsHDTB_INT.HDTB_INT.pushHDTB_INTRow(rowHDTB_INT);
      if (themmoi == false) {
        dsHDTB_INT.AcceptChanges();
        dsHDTB_INT.HDTB_INT[0].SetModified();
      }
    },
    //endregion

    //region Tao du lieu cho bang HDTB_ADSL
    async TaoDuLieu_HDTB_ADSL(themmoi) {
      this.dsHDTB_ADSL = [];
      let rowHDTB_ADSL = {};

      if (themmoi == false) {
        rs = await this.axios.get(
          "web-hopdong/hopdong/lay_ds_hopdong_thuebao_adsl",
          {
            params: {
              hdtbId: this.hdtb_id,
            },
          }
        );
        rowHDTB_ADSL = rs.data ? rs.data.data[0] : {};
      } else {
        rs = await this.axios.post(
          "web-ccdv/xuly_nghiemthubaohong/lay_ds_db_adsl_theo_tbid",
          { thuebao_id: this.thuebao_id }
        );
        rowHDTB_ADSL = rs.data ? rs.data.data[0] : {};
      }
      rowHDTB_ADSL.HDTB_ID = this.hdtb_id;
      ///Tuấn sửa code tạo dữ liệu dataset -- Ngày 02/08/2010
      rowHDTB_ADSL.EMAIL = this.email_adsl; //txtEmailAdsl.value;
      rowHDTB_ADSL.TOCDO_ID = Number(this.tocdo_id); //Number(cboGoiCuocAdsl.value);
      rowHDTB_ADSL.MUCCUOC_ID = Number(this.muccuoc_id); //Number(cboMucCuoc.value)

      this.dsHDTB_ADSL.push(rowHDTB_ADSL);
    },
    //endregion

    //region Tao du lieu cho bang DANGKY_DVGT
    async TaoDuLieu_DKDV() {
      this.dsDK_DVGT = [];

      let kieu_dk_dvgt;
      this.m_dsThueBao_DichVu.forEach((dr) => {
        if (dr["DANGKY"] == "1")
          kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_THEM_MOI_DVGT;
        else if (dr["HUY"] == "1")
          kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_HUY_DVGT;
        else {
          return;
        }

        let rowHDTDDV = {};
        rowHDTDDV.HDTB_ID = hdtb_id;
        rowHDTDDV.NGAY_YC = this.$refs.dtpNgayYC.value;
        rowHDTDDV.DICHVUGT_ID = Number(dr["DICHVUGT_ID"]);
        rowHDTDDV.NOIDUNG = dr["NOIDUNG"];
        rowHDTDDV.KIEU_YC = kieu_dk_dvgt;
        rowHDTDDV.TTTH = 1;

        this.dsDK_DVGT.push(rowHDTDDV);
      });
    },
    //endregion

    //region TaoDuLieu_HDTB_GP
    async TaoDuLieu_HDTB_GP(themmoi) {
      this.dsHDTB_GP = [];
      let rowHDTB_GP = {};
      if (themmoi == false) {
        rs = await this.axios.get(
          "web-hopdong/hopdong/lay_ds_hopdong_thuebao_gp",
          {
            params: {
              hdtbId: this.hdtb_id,
            },
          }
        );
        rowHDTB_GP = rs.data ? rs.data.data[0] : {};
      } else {
        rs = await this.axios.get("web-hopdong/hopdong/lay_ds_danhba_gp", {
          params: {
            thueBaoId: this.thuebao_id,
          },
        });
        rowHDTB_GP = rs.data ? rs.data.data[0] : {};
      }
      rowHDTB_GP.HDTB_ID = this.hdtb_id;

      this.dsHDTB_GP.push(rowHDTB_GP);
    },
    //endregion

    //region Tao du lieu cho bang TaoDuLieu_HDTT_NV
    TaoDuLieu_HDTT_NV(themmoi) {
      this.dsHDTT_NV = [];
      if (this.nhanvien_ql_am_id != 0) {
        let rowhdtt_nv = {};
        rowhdtt_nv.HDTT_ID = this.hdtt_id;
        rowhdtt_nv.NHANVIEN_ID = this.nhanvien_ql_am_id;
        rowhdtt_nv.MAY_CN = this.$root.token.getMaNhanVien();
        rowhdtt_nv.NGAY_CN = moment().format("DD/mm/yyyy hh:mm:ss");
        rowhdtt_nv.NGUOI_CN = this.$root.token.getUserName();
        this.dsHDTT_NV.push(rowhdtt_nv);
      }
    },
    //endregion

    //endregion
    /// <summary>
    /// TuấnNA thêm code lấy thông tin các dịch vụ -- Ngày 02/08/2010
    /// </summary>
    /// <param name="thuebao_id"></param>
    //region Dịch vụ GT, khác, Tbi đang sử dụng
    async LAY_DS_SUDUNG_DVGT(thuebao_id) {
      rs = await this.axios.post("web-hopdong/hopdong/lay_ds_thuebao_dichvu", {
        hdtbId: 0,
        kieuId: 1,
        loaiTbId: this.loaitb_id,
        thueBaoId: thuebao_id,
      });
      this.m_dsThueBao_DichVu = (rs.data && rs.data.data) ? rs.data.data : [];
    },

    async LAY_DS_DANGKY_DVK(thuebao_id) {
      rs = await this.axios.get("web-hopdong/hopdong/lay_ds_sudung_dvk", {
        params: {
          dbId: thuebao_id,
          loaiId: 1,
        },
      });
      let ds = rs.data.data;
      this.dsDK_DVK = [];
      for (let i = 0; i < ds.length; i++) {
        let row = {};
        row.DVKHAC_ID = Number(ds[i]["dvkhac_id"]);
        row.KIEU_YC = 1;
        row.TTTH = TrangThaiCaiDat.MOI;
        row.HD_ID = this.hdtb_id;
        row.NGAY_YC = this.$refs.dtpNgayYC.value;
        row.LOAIID = 1;
        this.dsDK_DVK.push(row);
      }
    },

    //endregion

    //region HienThiTTHopDongKH
    async HienThiTTHopDongKH(ds) {
      return new Promise(async (resolve, reject) => {
        try {
          if (ds && ds.length > 0) {
            this.hdkh_id = Number(ds[0]["hdkh_id"]);
            this.$refs.txtMaGD.value = ds[0]["ma_gd"];
            /// TuấnNA thêm dữ liệu cho biến  -- Ngày 01/08/2010
            this.ma_gd = ds[0]["ma_gd"];
            this.loaikh_id = ds[0]["loaikh_id"];
            if (ds[0]["nganhnghe_id"] != "")
              this.vnganhnghe_id = Number(ds[0]["nganhnghe_id"]);
            else this.vnganhnghe_id = 0;

            this.loaigt_id = ds[0]["loaigt_id"];
            this.ngay_sn = ds[0]["ngay_sn"];
            this.gioitinh = ds[0]["gioitinh"];
            this.ngaycap = ds[0]["ngaycap"];
            this.$refs.txtMaHD.value = ds[0]["ma_hd"];
            this.$refs.txtMaKH.value = ds[0]["ma_kh"];
            this.quoctich = ds[0]["quoctich_id"];
            if (ds[0]["khachhang_id"] != "")
              this.khachhang_id = Number(ds[0]["khachhang_id"]);
            else this.khachhang_id = 0;

            if (this.phantap_kh && this.khachhang_id > 0) {
              let ds_dt_dtn = [];
              rs = await this.axios.get("web-hopdong/hopdong/lay_doanhthu_dtn", {
                params: {
                  khachHangId: this.khachhang_id,
                },
              });
              ds_dt_dtn = rs.data;
              if (ds_dt_dtn.length > 0) {
                if (ds_dt_dtn[0]["doanhthu"] != "")
                  this.pt_doanhthu = parseFloat(ds_dt_dtn[0]["doanhthu"]);
                else this.pt_doanhthu = 0;

                if (ds_dt_dtn[0]["dtn"] != "")
                  this.pt_diem_tn = Number(ds_dt_dtn[0]["dtn"]);
                else this.pt_diem_tn = 0;
              } else {
                this.pt_doanhthu = 0;
                this.pt_diem_tn = 0;
              }
            }

            this.$refs.txtTenKH.value = ds[0]["ten_kh"];
            this.$refs.txtDiaChiKH.value = ds[0]["diachi_kh"];
            rs = await this.axios.get(
              "web-hopdong/hopdong/lay_ds_diachi_theo_hdkh",
              {
                params: {
                  hdKhId: this.hdkh_id,
                },
              }
            );
            this.HienThiTTDiaChi(rs.data.data, 1);
            this.$refs.dtpNgayLapHD.value = ds[0]["ngaylap_hd"];
            ///TuấnNA thêm code cho ngày yêu cầu trong tra cứu hợp đồng
            if (ds[0]["ngay_yc"] != "") {
              this.$refs.dtpNgayYC.value = ds[0]["ngay_yc"];
            }
            this.HienThiDS_HDTT_CBO(this.hdkh_id);
            rs = await this.axios.get(
              "web-hopdong/ThayDoiHopDong/lay_ds_hopdong_tt_theo_hdkh_id",
              {
                params: { hdkhId: this.hdkh_id },
              }
            );
            this.HienThiTTHopDongTT(rs.data.data);

            rs = await this.axios.get(
              "web-hopdong/hopdong/lay_ds_hopdong_thuebao_theo_hdkh",
              {
                params: { hdKhId: this.hdkh_id },
              }
            );

            this.HienThiTTHopDongTB(rs.data.data);

            rs = await this.axios.get(
              "web-hopdong/ThayDoiHopDong/lay_ds_hopdong_tt_theo_hdkh_id",
              {
                params: { hdkhId: this.hdkh_id },
              }
            );

            this.HienThiDanhSacHDTT(rs.data.data);
            await this.HT_DanhSachTB();

            this.SetButton(3);

            //New change
            if (ds[0]["nhanviengt_id"]) {
              this.$refs.chkNguoiGT.checked = true;
              this.nguoigt_id = Number(ds[0]["nhanviengt_id"]);
              rs = await this.axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien",
              { param: this.nguoigt_id, type: 3 }
              );
              this.$refs.txtNguoiGT.value = rs.data.data;
              //this.chkNguoiGT_CheckedChanged();
            } else {
              this.$refs.txtNguoiGT.value = "";
              this.$refs.chkNguoiGT.checked = false;
            }
            //
          } else {
            this.SetButton(0);
          }
        resolve(true)
        } catch (err) {
          resolve(true)
          console.log(err);
        }
      })
    },
    //endregion

    //region HienThiTTHopDongTT
    async HienThiTTHopDongTT(ds) {
      if (ds.length > 0) {
        this.hdtt_id = Number(ds[0]["hdtt_id"]);
        this.$refs.txtMaTT.value = ds[0]["ma_tt"];
        ///TuấnNA thêm code lấy mã thanh toán của thuê bao tại thời điểm  đang thực hiện tách ghép // Ngày 25/08/2010
        this.ma_tt_cu = ds[0]["ma_tt"];
        if (ds[0]["thanhtoan_id"] != "")
          this.thanhtoan_id = Number(ds[0]["thanhtoan_id"]);
        else this.thanhtoan_id = 0;
        rs = await this.axios.get(
          "web-hopdong/hopdong/lay_ds_ma_thuebao_daidien",
          {
            params: {
              thanhtoanId: this.thanhtoan_id,
            },
          }
        );
        let dt_madd = rs.data.data;
        this.$refs.cboMaDD.dataSource = dt_madd;
        console.log(dt_madd)
        //
        this.$refs.cboMaDD.value = ds[0]["matb_dd"];

        this.$refs.txtTenTT.value = ds[0]["ten_tt"];

        this.$refs.txtDiaChiTT.value = ds[0]["diachi_tt"];
        this.$refs.txtDiaChiBC.value = ds[0]["diachi_bc"];
        ///TuấnNA thêm code hiển thị địa chỉ chứng từ  // Ngày 18/08/2010
        this.$refs.txtDiachiCT.value = ds[0]["diachi_ct"];
        rs = await this.axios.get(
          "web-hopdong/hopdong/lay_ds_diachi_theo_hdtt",
          {
            params: {
              hdttId: this.hdtt_id,
            },
          }
        );
        this.HienThiTTDiaChi(rs.data.data, 2);
        //TuấnNA thêm code hiển thị thông tin NSNN trong bảng HDTT // Ngày 12/11/2010
        this.DV_QHNS = ds[0]["DV_QHNS"];
        this.MACHUONG = ds[0]["MACHUONG"];
        this.MN_KT = ds[0]["MN_KT"];
        this.MA_NDKT = ds[0]["MA_NDKT"];
        this.MN_NSNN = ds[0]["MN_NSNN"];
        if (ds[0]["TIEN_DM"] != "") this.TIEN_DM = Number(ds[0]["TIEN_DM"]);
        else this.TIEN_DM = 0;
        //
        if (ds[0]["hdmig_id"] != "") {
          this.hdmig_id = Number(ds[0]["hdmig_id"]);
          rs = await this.axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_mig", { param: this.hdmig_id, type: 1 });
          this.$refs.txtMaIG.value = rs.data.data;
        } else {
          this.hdmig_id = 0;
          this.$refs.txtMaIG.value = "";
        }
        this.$refs.txtMST_TT.value = ds[0]["mst"];
        this.$refs.txtSTK_TT.value = ds[0]["stk"];
        if (ds[0]["nganhang_id"] != "")
          this.$refs.cboNganHang.value = ds[0]["nganhang_id"];
        this.$refs.cboHTTT.value = ds[0]["httt_id"];
        ///TuấnNA bổ sung code lấy mã thanh toán cho cboTT  // Ngày 25/08/2010
        this.$refs.cboTT.value = Number(ds[0]["hdtt_id"]);
        this.$refs.txtGhiChu.value = ds[0]["ghichu"];

        this.donviql_id = ds[0]["donvi_id"];
        this.$refs.cboDonViQL.value = this.donviql_id;
        //donvi_qlcu = ds[0]["donvi_id"]
        this.$refs.txtMaTBDD.value = ds[0]["matb_dd"];

        if (ds[0]["nhanvien_id"] != "") {
          this.nhanvientc_id = Number(ds[0]["nhanvien_id"]);
          rs = await this.axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien", { param: this.nhanvientc_id, type: 4 });
          this.$refs.txtMaNVTC.value = rs.data.data;
        } else {
          this.nhanvientc_id = 0;
          this.$refs.txtMaNVTC.value = "";
        }

        if (ds[0]["tuyenthu_id"] != "") {
          this.tuyenthu_id = Number(ds[0]["tuyenthu_id"]);
          rs = await this.axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tuyenthu",{ param: this.tuyenthu_id, type: 1 });
          this.$refs.txtMaTuyen.value = rs.data.data;
        } else {
          this.tuyenthu_id = 0;
          this.$refs.txtMaTuyen.value = "";
        }
        this.HienThi_NVQL_AM_DB_HD(2, this.hdtt_id);
      }
    },
    //endregion

    //region HienThiTTHopDongTB
    async HienThiTTHopDongTB(ds) {
      if (ds.length > 0) {
        //this.hdtb_id = ds[0]["hdtb_id"] ? Number(ds[0]["hdtb_id"]) : 0;
        if (ds[0]["thuebao_id"])
          this.thuebao_id = Number(ds[0]["thuebao_id"]);
        else this.thuebao_id = 0;
        this.$refs.txtMaTB.value = ds[0]["ma_tb"];
        this.$refs.txtTenTB.value = ds[0]["ten_tb"];
        ///TuấnNA thêm code hiển thị địa chỉ lắp đặt  // Ngày 18/08/2010
        this.$refs.txtDiachiLD.value = ds[0]["diachi_ld"];
        this.$refs.txtDiaChiTB.value = ds[0]["diachi_tb"];
        rs = await this.axios.get(
          "web-hopdong/hopdong/lay_ds_diachi_theo_hdtt",
          {
            params: {
              hdttId: this.hdtt_id,
            },
          }
        );
        this.HienThiTTDiaChi(rs.data.data, 3);
        this.dichvuvt_id = ds[0]["dichvuvt_id"];
        this.$refs.cboDichVuVT.value = this.dichvuvt_id;
        this.loaitb_id = Number(ds[0]["loaitb_id"]);
        await this.cboDichVuVT_SelectedValueChanged()
        this.$refs.cboLoaihinhTB.value = this.loaitb_id;
        await this.cboLoaihinhTB_SelectedValueChanged()
        this.$refs.cboKieuLD.value = ds[0]["kieuld_id"];
        this.$refs.cboDoituongTB.value = ds[0]["doituong_id"];
        this.donvi_id = Number(ds[0]["donvi_id"]);
        this.status = ds[0]["status"];
        this.$refs.txtEmailTB.value = ds[0]["email"];
        if (ds[0]["quytrinh_id"])
          this.quytrinh_id = Number(ds[0]["quytrinh_id"]);
        else this.quytrinh_id = 0;
        ///TuấnNA thêm code bổ sung gói cước cho thuê bao di động // Ngày 02/10/2010
        if (this.dichvuvt_id == DichVuVienThong.DI_DONG) {
          let dsdd = [];
          rs = await this.axios.get(
            "web-ccdv/hoanthienhschuyenquyen/lay_ds_hdtb_dd_theo_hdtb_id?hdtb_id=" + ds[0]["hdtb_id"] 
          );
          dsdd = rs.data.data;
          console.log(dsdd, "dây mà")
          this.goicuoc_id = (dsdd && dsdd.length > 0) ? dsdd[0]["goicuoc_id"] : 0;
        }
        ///TuấnNA bổ sung code lấy mã thanh toán cho cboTT  // Ngày 25/08/2010
        this.$refs.cboTT.value = Number(ds[0]["hdtt_id"]);
        ///TuấnNA bổ sung code lấy danh sách dịch vụ khác --Ngày 01/08/2010
        //dsDK_DVK = tcdanhba.LAY_DS_SUDUNG_DVK(thuebao_id);
        rs = await this.axios.post(
          "web-hopdong/khoiphucthanhly/lay_ds_dangky_dvk",
          { hd_id: this.hdtb_id, loaiid: 1 }
        );
        this.dsDK_DVK = rs.data.data;

        rs = await this.axios.post(
          "web-hopdong/hopdong/lay_ds_thuebao_dichvu",
          {
            hdtbId: this.hdtb_id,
            kieuId: 1,
            loaiTbId: this.loaitb_id,
            thueBaoId: this.thuebao_id,
          }
        );
        this.m_dsThueBao_DichVu = (rs.data && rs.data.data) ? rs.data.data : [];
        ///TuấnNA Thêm code lấy lại thông tin thanh toán cũ // Ngày 02/10/2010
        let ds_ttcu = [];
        rs = await this.axios.get(
          "web-hopdong/hopdong/lay_biendong_thongtin_thuebao",
          {
            params: {
              hdtbId: this.hdtb_id,
            },
          }
        );
        ds_ttcu = rs.data.data ? rs.data.data : [];
        this.ten_tt = ds_ttcu[0] ? ds_ttcu[0]["ten_tt"] : "";
        this.diachi_tt = ds_ttcu[0] ? ds_ttcu[0]["diachi_tt"] : "";
        //Hiếu TC bổ sung hàm lấy tiền lên ô text box ngày 28/09/2010
        //dtCTKM = tchopdong.LAY_TIEN_HD_KM_THEO_HDTB(hdtb_id).Tables[0];
        //decimal tien_ld = 0, vat_ld = 0;
        //for (i = 0; i < dtCTKM.length; i++)
        //{
        //    if (dtCTKM[i]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_TNTHUEBAO)
        //    {
        //        tien_ld = parseFloat(dtCTKM[i]["tien"]);
        //        vat_ld = parseFloat(dtCTKM[i]["vat"]);
        //    }
        //}
        //HienThiTienLapDat(tien_ld, vat_ld);
        //HienThiTTDBMoRong(thuebao_id, dichvuvt_id);
        //nhapt thêm
        rs = await this.axios.post(
          "web-hopdong/khoiphucthanhly/lay_tienhd_km_theo_hdtb",
          {
            hdtb_id: this.hdtb_id,
          }
        );
        this.ds_tien_khoanmuc = rs.data.data ? rs.data.data : [];
        for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          if (
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"] ==
            KHOANMUC_TT.KMTT_TNTHUEBAO
          ) {
            this.dtientn = parseFloat(this.ds_tien_khoanmuc[i]["tien"]);
            this.dvattn = parseFloat(this.ds_tien_khoanmuc[i]["vat"]);
          }
        }
        this.LayTongTien_HDTB();
      }
    },
    //endregion

    //region HienThiTTDiaChi
    async HienThiTTDiaChi(ds, kieu) {
      if (ds && ds.length > 0) {
        if (kieu == 1) {
          this.diachikh_id = Number(ds[0]["diachi_id"]);
          if (ds[0]["tinh_id"] != "") this.tinhkh_id = Number(ds[0]["tinh_id"]);
          else this.tinhkh_id = 0;
          this.quankh_id = Number(ds[0]["quan_id"]);
          this.phuongkh_id = Number(ds[0]["phuong_id"]);
          if (ds[0]["pho_id"] != "") this.phokh_id = Number(ds[0]["pho_id"]);
          else this.phokh_id = 0;

          if (ds[0]["ap_id"] != "") this.apkh_id = Number(ds[0]["ap_id"]);
          else this.apkh_id = 0;

          if (ds[0]["khu_id"] != "") this.khukh_id = Number(ds[0]["khu_id"]);
          else this.khukh_id = 0;

          if (ds[0]["dacdiem_id"] != "")
            this.dacdiemkh_id = Number(ds[0]["dacdiem_id"]);
          else this.dacdiemkh_id = 0;
          if (ds[0]["sonha"]) this.sonhakh = ds[0]["sonha"];
          else this.sonhakh = 0;
        }

        if (kieu == 2) {
          this.diachitt_id = Number(ds[0]["diachi_id"]);
          if (ds[0]["tinh_id"] != "") this.tinhtt_id = Number(ds[0]["tinh_id"]);
          else this.tinhtt_id = 0;

          this.quantt_id = Number(ds[0]["quan_id"]);
          this.phuongtt_id = Number(ds[0]["phuong_id"]);
          if (ds[0]["pho_id"] != "") this.phott_id = Number(ds[0]["pho_id"]);
          else this.phott_id = 0;

          if (ds[0]["ap_id"] != "") this.aptt_id = Number(ds[0]["ap_id"]);
          else this.aptt_id = 0;

          if (ds[0]["khu_id"] != "") this.khutt_id = Number(ds[0]["khu_id"]);
          else this.khutt_id = 0;

          if (ds[0]["dacdiem_id"] && ds[0]["dacdiem_id"] != "")
            this.dacdiemtt_id = Number(ds[0]["dacdiem_id"]);
          else this.dacdiemtt_id = 0;
          this.sonhatt = ds[0]["sonha"] ? ds[0]["sonha"] : "0";

          //Địa chỉ báo cước
          if (ds[0]["diachibc_id"] && ds[0]["diachibc_id"] != "")
            this.diachibc_id = Number(ds[0]["diachibc_id"]);
          else this.diachibc_id = 0;

          if (ds[0]["tinhbc_id"] && ds[0]["tinhbc_id"] != "")
            this.tinhbc_id = Number(ds[0]["tinhbc_id"]);
          else this.tinhbc_id = 0;

          if (ds[0]["quanbc_id"] && ds[0]["quanbc_id"] != "")
            this.quanbc_id = Number(ds[0]["quanbc_id"]);
          else this.quanbc_id = 0;

          if (ds[0]["phuongbc_id"] && ds[0]["phuongbc_id"] != "")
            this.phuongbc_id = Number(ds[0]["phuongbc_id"]);
          else this.phuongbc_id = 0;

          if (ds[0]["phobc_id"] && ds[0]["phobc_id"] != "")
            this.phobc_id = Number(ds[0]["phobc_id"]);
          else this.phobc_id = 0;

          if (ds[0]["apbc_id"] && ds[0]["apbc_id"] != "")
            this.apbc_id = Number(ds[0]["apbc_id"]);
          else this.apbc_id = 0;

          if (ds[0]["khubc_id"] && ds[0]["khubc_id"] != "")
            this.khubc_id = Number(ds[0]["khubc_id"]);
          else this.khubc_id = 0;

          if (ds[0]["dacdiembc_id"] && ds[0]["dacdiembc_id"] != "")
            this.dacdiembc_id = Number(ds[0]["dacdiembc_id"]);
          else this.dacdiembc_id = 0;
          this.sonhabc = ds[0]["sonha_bc"] ? ds[0]["sonha_bc"] : 0;
        }

        if (kieu == 3) {
          this.diachitb_id = Number(ds[0]["diachi_id"]);
          if (ds[0]["tinh_id"] != "") this.tinhtb_id = Number(ds[0]["tinh_id"]);
          else this.tinhtb_id = 0;

          if (ds[0]["quan_id"] != "") this.quantb_id = Number(ds[0]["quan_id"]);
          else this.quantb_id = 0;

          if (ds[0]["phuong_id"] != "")
            this.phuongtb_id = Number(ds[0]["phuong_id"]);
          else this.phuongtb_id = 0;

          if (ds[0]["pho_id"] != "") this.photb_id = Number(ds[0]["pho_id"]);
          else this.photb_id = 0;

          if (ds[0]["ap_id"] != "") this.aptb_id = Number(ds[0]["ap_id"]);
          else this.aptb_id = 0;

          if (ds[0]["khu_id"] != "") this.khutb_id = Number(ds[0]["khu_id"]);
          else this.khutb_id = 0;

          if (ds[0]["dacdiem_id"] != "")
            this.dacdiemtb_id = Number(ds[0]["dacdiem_id"]);
          else this.dacdiemtb_id = 0;
          this.sonhatb = ds[0]["sonha"];

          /// Tuấn thêm code lấy địa chỉ lắp đặt cho thuê bao -- Ngày 01/08/2010
          if (ds[0]["diachild_id"] != "")
            this.diachild_id = Number(ds[0]["diachild_id"]);
          else this.diachild_id = 0;
          //====================================
          this.blocktb = ds[0]["block"];
          this.tangtb = ds[0]["tang"];
          this.sophongtb = ds[0]["phong"];
          this.motadctb = ds[0]["mota"];
          //====================================

          if (ds[0]["tinhld_id"] != "")
            this.tinhld_id = Number(ds[0]["tinhld_id"]);
          else this.tinhld_id = 0;

          if (ds[0]["quanld_id"] != "")
            this.quanld_id = Number(ds[0]["quanld_id"]);
          else this.quanld_id = 0;

          if (ds[0]["phuongld_id"] != "")
            this.phuongld_id = Number(ds[0]["phuongld_id"]);
          else this.phuongld_id = 0;

          if (ds[0]["phold_id"] != "")
            this.phold_id = Number(ds[0]["phold_id"]);
          else this.phold_id = 0;

          if (ds[0]["apld_id"] != "") this.apld_id = Number(ds[0]["apld_id"]);
          else this.apld_id = 0;

          if (ds[0]["khuld_id"] != "")
            this.khuld_id = Number(ds[0]["khuld_id"]);
          else this.khuld_id = 0;

          if (ds[0]["dacdiemld_id"] != "")
            this.dacdiemld_id = Number(ds[0]["dacdiemld_id"]);
          else this.dacdiemld_id = 0;
          //====================================
          this.blockld = ds[0]["block_ld"];
          this.tangld = ds[0]["tang_ld"];
          this.sophongld = ds[0]["phong_ld"];
          this.motadcld = ds[0]["mota_ld"];
          //====================================

          this.sonhald = ds[0]["sonha_ld"];
        }
      }
    },
    //endregion

    //region HienThiTT_DanhBa
    async HienThiTT_DanhBa(ma_tb) {
      ///TuấnNA sửa code lấy danh bạ theo đơn vị quản lý user đăng nhập -- Ngày 05/08/2010
      /// DataSet ds = tcdanhba.LAY_DANHBA_THEO_MATB(ma_tb, dichvuvt_id, 0);
      //DataSet ds = tcdanhba.LAY_DANHBA_THEO_MATB(ma_tb, dichvuvt_id, this.tt_nd.donvi_dl_id);
      ///TuaansNA sửa code cho phép lập hợp đồng cho toàn tỉnh.
      return new Promise(async (resolve, reject) => {
      rs = await this.axios.post("/web-quantri/thaydoi-thongso-internet/lay_danhba_theo_matb",
        {
          "ma_tb": ma_tb,"dichvuvt_id": this.dichvuvt_id ? this.dichvuvt_id : 0,"donvi_dl_id":0
        }
      );
      let ds = rs.data.data;
      if (ds && ds.length > 0) {
        //nhapt 25062016
        if (Number(ds[0]["ky_hd"]) == 0) {
          this.$toast.error(
            "Khách hàng có mã thuê bao: " +
            this.$refs.txtMaTB.value +
            " chưa ký lại hợp đồng gốc \nĐề nghị ký lại hợp đồng gốc"
          );
          resolve(true)
          return;
        }
        ///Tuấn thêm code kiểm tra trạng thái thuê bao // Ngày 07/09/2010
        /////> Nếu trạng thái thuê bao // " Hoạt động bình thường" //> không cho phép làm tiếp

        if (
          Number(ds[0]["trangthaitb_id"]) != TrangThaiTB.THUONG &&
          Number(ds[0]["trangthaitb_id"]) != TrangThaiTB.KHOA_MOTCHIEU_YEUCAU
        ) {
          this.$toast.error(
            "Thuê bao có mã: " +
            ds[0]["ma_tb"] +
            " đang ở trạng thái: " +
            ds[0]["trangthai_tb"] +
            "\nBạn không thể thực hiện Tách nhập thuê bao cho thuê bao này"
          );
          resolve(true)
          return;
        }
        if (this.hdtb_id == 0) {
          rs = await this.axios.post(
            "web-hopdong/hopdong/kiemtra_thuebao_lap_hopdong_khac",
            {
              dichVuVtId: Number(this.$refs.cboDichVuVT.value),
              loaiHdId: LoaiHopDong.TACHNHAP_TB,
              maGd: "0",
              maTb: this.$refs.txtMaTB.value.trim(),
            }
          );
          if (rs.data.data) {
            this.$toast.error("Thuê bao đang có hợp đồng phụ lục với mã giao dịch '"+ rs.data.data.ma_gd +"' không thể thực hiện")
            resolve(true)
            return;
          }
        }
        //region Lấy thông tin trong danh bạ
        //if (Number(ds[0]["ky_hd"]) == 0)
        //{
        //    this.$toast.error("Khách hàng có mã thuê bao: " + this.$refs.txtMaTB.value + " chưa ký lại hợp đồng gốc \nĐề nghị ký lại hợp đồng gốc");
        //}
        //else
        //{
        //Tuan bo sung code kiem tra thue bao nhap moi co thuoc khach hang dang chuyen quyen hay khong--Ngay 05/07/2010
        this.khachhang_id_nm = Number(ds[0]["khachhang_id"]);
        this.thuebao_id = ds[0]["thuebao_id"]
        //region Đang có thuê bao thực hiện tách ghép
        if (this.khachhang_id_dtg != 0) {
          if (this.khachhang_id_nm != this.khachhang_id_dtg) {
            if (this.hdkh_id != 0)
              this.$toast.error(
                "Mã thuê bao vừa nhập không thuộc khách hàng đang thực hiện tách nhập thuê bao. Bạn hãy kiểm tra lại !"
              );
          } else {
            if (!this.hdkh_id) {
              if (
                Number(this.$refs.cboKieuLD.value) == KieuLapDat.TACH_THUEBAO
              ) {
                //Thông tin khách hàng
                this.$refs.txtMaKH.value = ds[0]["ma_kh"];
                //TuanNA them code lay thong tin ma_kh_cu -- Ngay 05/08/2010
                this.ma_kh_cu = ds[0]["ma_kh"];
                this.$refs.txtMaHD.value = ds[0]["ma_hd"];
                this.khachhang_id = Number(ds[0]["khachhang_id"]);
                this.khachhang_id_dtg = this.khachhang_id;
                this.$refs.txtTenKH.value = ds[0]["ten_kh"];
                this.$refs.txtDiaChiKH.value = ds[0]["diachi_kh"];
                this.$refs.dtpNgayLapHD.value = ds[0]["ngaylap_hd"];
                rs = await this.axios.get(
                  "web-hopdong/hopdong/lay_ds_diachi_theo_khachhang",
                  {
                    params: {
                      khachHangId: this.khachhang_id,
                    },
                  }
                );
                this.HienThiTTDiaChi(rs.data.data, 1);
                ///TuấnNA sửa code lấy thêm thông tin -- Ngày 31/07/2010
                this.loaikh_id = ds[0]["loaikh_id"];
                this.ngaycap = ds[0]["ngaycap"];
                this.ngay_sn = ds[0]["ngay_sn"];
                this.gioitinh = ds[0]["gioitinh"];
                //Thông tin thanh toán
                this.HT_ThongTin_Thanhtoan(
                  Number(ds[0]["thuebao_id"]),
                  KieuLapDat.TACH_THUEBAO
                );
              } else {
                // Nhập thuê bao
                //Thông tin khách hàng
                this.$refs.txtMaKH.value = "";
                ///TuấnNA sửa code lấy khachhang_id trong truong hơp ghép thuê bao -- Ngay 05/08/2010
                /// khachhang_id = 0; code cũ
                this.khachhang_id = Number(ds[0]["khachhang_id"]);
                this.khachhang_id_dtg = this.khachhang_id;
                this.$refs.txtMaKH.value = ds[0]["ma_kh"];
                this.ma_kh_cu = ds[0]["ma_kh"];
                this.$refs.txtMaHD.value = ds[0]["ma_hd"];
                this.$refs.txtTenKH.value = ds[0]["ten_kh"];
                this.$refs.txtDiaChiKH.value = ds[0]["diachi_kh"];
                this.gioitinh = ds[0]["gioitinh"];
                this.$refs.dtpNgayLapHD.value = ds[0]["ngaylap_hd"];
                rs = await this.axios.get("web-hopdong/hopdong/lay_ds_diachi_theo_khachhang", {
                  params: {
                    khachHangId : this.khachhang_id
                  }
                })
                this.HienThiTTDiaChi(
                  rs.data.data,
                  1
                );
                this.loaikh_id = ds[0]["loaikh_id"];
                //Thông tin thanh toán
                this.HT_ThongTin_Thanhtoan(
                  Number(ds[0]["thuebao_id"]),
                  KieuLapDat.NHAP_THUEBAO
                );
              }
            }

            //Thông tin khách hàng
            this.$refs.txtMaKH.value = ds[0]["ma_kh"];
            //TuanNA them code lay thong tin ma_kh_cu -- Ngay 05/08/2010
            this.ma_kh_cu = ds[0]["ma_kh"];
            this.$refs.txtMaHD.value = ds[0]["ma_hd"];
            this.khachhang_id = Number(ds[0]["khachhang_id"]);
            this.khachhang_id_dtg = this.khachhang_id;
            this.$refs.txtTenKH.value = ds[0]["ten_kh"];
            this.$refs.txtDiaChiKH.value = ds[0]["diachi_kh"];
            this.loaikh_id = ds[0]["loaikh_id"];

            if (this.phantap_kh) {
              let ds_dt_dtn = [];
              rs = await this.axios.get(
                "web-hopdong/hopdong/lay_doanhthu_dtn",
                {
                  params: {
                    khachHangId: this.khachhang_id,
                  },
                }
              );
              ds_dt_dtn = rs.data && rs.data.data ? rs.data.data : [];
              if (ds_dt_dtn && ds_dt_dtn.length > 0) {
                if (ds_dt_dtn[0]["doanhthu"] != "")
                  this.pt_doanhthu = parseFloat(ds_dt_dtn[0]["doanhthu"]);
                else this.pt_doanhthu = 0;

                if (ds_dt_dtn[0]["dtn"] != "")
                  this.pt_diem_tn = Number(ds_dt_dtn[0]["dtn"]);
                else this.pt_diem_tn = 0;
              } else {
                this.pt_doanhthu = 0;
                this.pt_diem_tn = 0;
              }
            }

            if (ds[0]["nganhnghe_id"] != "")
              this.vnganhnghe_id = Number(ds[0]["nganhnghe_id"]);
            else this.vnganhnghe_id = 0;

            rs = await this.axios.get(
              "web-hopdong/hopdong/lay_ds_diachi_theo_khachhang",
              {
                params: {
                  khachHangId: this.khachhang_id,
                },
              }
            );
            this.HienThiTTDiaChi(rs.data.data, 1);

            //Thông tin thuê bao
            this.$refs.txtMaHD.value = ds[0]["ma_hd"];
            this.thuebao_id = Number(ds[0]["thuebao_id"]);
            ///TuấnNA thêm code chặn trường hợp thuê bao di động nhập mới không cùng mã thanh toán với các thuê bao di động có trong hợp đồng
            ///// Ngày 25/08/2010
            if (
              this.dichvuvt_id == DichVuVienThong.DI_DONG &&
              Number(this.$refs.cboLoaihinhTB.value) == LoaiHinhTB.DIDONGTRASAU
            ) {
              if (this.ma_gd != "") {
                rs = await this.axios.get(
                        "web-hopdong/hopdong/kiemtra_hopdong_thuebao",{
                            params:
                            {
                                thueBaoId: this.thuebao_id,
                                maGd: this.ma_gd
                            }
                        }
                    )
                if (rs.data.data != "1") {
                  this.$toast.error(
                    "Số thuê bao di động: " +
                    this.$refs.txtMaTB.value +
                    " vừa nhập không cùng mã thanh toán: " +
                    ds[0]["ma_tt"] +
                    " với thuê bao di động trong phụ lục\nBạn phải lập một phụ lục khác cho thuê bao này! "
                  );
                  this.loading(false)
                  return;
                }
              }
            }
            ///TuấnNA bổ sung code mã TB -- Ngày 05/08/2010
            this.ma_tb_cu = ds[0]["ma_tb"];
            this.$refs.txtTenTB.value = ds[0]["ten_tb"];
            this.$refs.txtDiaChiTB.value = ds[0]["diachi_tb"];
            this.$refs.txtEmailTB.value = ds[0]["email_tb"];
            rs = await this.axios.get(
              "web-hopdong/hopdong/lay_ds_diachi_theo_thuebao",
              {
                params: {
                  thueBaoId: this.thuebao_id,
                },
              }
            );
            this.HienThiTTDiaChi(rs.data.data, 3);
            this.$refs.cboDoituongTB.value = ds[0]["doituong_id"];
            this.loaitb_id = Number(ds[0]["loaitb_id"]);
            //TuanNA them code hien thi loaitb_id -- Ngay 31/07/2010
            this.$refs.cboLoaihinhTB.value = Number(ds[0]["loaitb_id"]);
            this.donvi_id = Number(ds[0]["donviql_id"]);
            ///Tuấn NA bổ sung -- ngày 31/07/2010
            this.$refs.txtDiachiLD.value = ds[0]["diachi_ld"];
            if (this.dichvuvt_id == 2) {
              //lấy dữ liệu gói cước cho các hàm vinaphone
              if (ds[0]["goicuoc_id"] != "") {
                this.goicuoc_id = ds[0]["goicuoc_id"];
              }
            }
            this.HienThiTTDBMoRong(this.thuebao_id, this.dichvuvt_id);
            if (this.khong_ht_tracuudb == 0) {
              this.$bvModal.show("SearchGeneral")
              //this.$toast.error("Popup tra cứu danh bạ đang bổ sung!")
              // let frmTCCTDB = new frmTraCuuCTDanhBa(
              //   dichvuvt_id,
              //   this.$refs.txtMaTB.value
              // );
              // frmTCCTDB.ShowDialog();
            }
            this.LAY_DS_SUDUNG_DVGT(this.thuebao_id);
            this.LAY_DS_DANGKY_DVK(this.thuebao_id);
          }
        }
        //endregion

        //region Chưa có khách hàng đang tách ghép
        else {
          if (!this.hdkh_id) {
            if (Number(this.$refs.cboKieuLD.value) == KieuLapDat.TACH_THUEBAO) {
              //Thông tin khách hàng
              this.$refs.dtpNgayLapHD.value = ds[0]["ngaylap_hd"];
              this.$refs.txtMaKH.value = ds[0]["ma_kh"];
              //TuanNA them code lay thong tin ma_kh_cu -- Ngay 05/08/2010
              this.ma_kh_cu = ds[0]["ma_kh"];
              this.$refs.txtMaHD.value = ds[0]["ma_hd"];
              this.khachhang_id = Number(ds[0]["khachhang_id"]);
              this.khachhang_id_dtg = this.khachhang_id;
              this.$refs.txtTenKH.value = ds[0]["ten_kh"];
              this.$refs.txtDiaChiKH.value = ds[0]["diachi_kh"];
              rs = await this.axios.get(
                "web-hopdong/hopdong/lay_ds_diachi_theo_khachhang",
                {
                  params: {
                    khachHangId: this.khachhang_id,
                  },
                }
              );
              this.HienThiTTDiaChi(rs.data.data, 1);
              ///TuấnNA sửa code lấy thêm thông tin -- Ngày 31/07/2010
              this.loaikh_id = ds[0]["loaikh_id"];
              this.loaigt_id = ds[0]["loaigt_id"];
              this.ngaycap = ds[0]["ngaycap"];
              this.ngay_sn = ds[0]["ngay_sn"];
              this.gioitinh = ds[0]["gioitinh"];
              //Thông tin thanh toán
              this.HT_ThongTin_Thanhtoan(
                Number(ds[0]["thuebao_id"]),
                KieuLapDat.TACH_THUEBAO
              );
            } else {
              //Thông tin khách hàng
              this.$refs.txtMaKH.value = "";
              ///TuấnNA sửa code lấy khachhang_id trong truong hơp ghép thuê bao -- Ngay 05/08/2010
              /// khachhang_id = 0; code cũ
              this.khachhang_id = Number(ds[0]["khachhang_id"]);

              ///TuấnNA sửa code lấy khachhang_id trong truong hơp ghép thuê bao -- Ngay 05/08/2010
              /// khachhang_id = 0; code cũ
              this.khachhang_id_dtg = this.khachhang_id;
              rs = await this.axios.get(
                "web-hopdong/hopdong/lay_ds_diachi_theo_khachhang",
                {
                  params: {
                    khachHangId: this.khachhang_id,
                  },
                }
              );
              this.HienThiTTDiaChi(rs.data.data, 1);
              ///TuấnNA sửa code lấy thêm thông tin -- Ngày 31/07/2010
              this.loaikh_id = ds[0]["loaikh_id"];
              this.loaigt_id = ds[0]["loaigt_id"];
              this.ngaycap = ds[0]["ngaycap"];
              this.gioitinh = ds[0]["gioitinh"];
              this.$refs.txtMaKH.value = ds[0]["ma_kh"];
              this.ma_kh_cu = ds[0]["ma_kh"];
              this.$refs.txtMaHD.value = ds[0]["ma_hd"];
              this.$refs.txtTenKH.value = ds[0]["ten_kh"];
              this.$refs.txtDiaChiKH.value = ds[0]["diachi_kh"];
              this.$refs.dtpNgayLapHD.value = ds[0]["ngaylap_hd"];
              //Thông tin thanh toán
              this.HT_ThongTin_Thanhtoan(
                Number(ds[0]["thuebao_id"]),
                KieuLapDat.TACH_THUEBAO
              );
            }
          }
          this.loaikh_id = ds[0]["loaikh_id"];

          if (this.phantap_kh) {
            let ds_dt_dtn = [];
            rs = await this.axios.get("web-hopdong/hopdong/lay_doanhthu_dtn", {
              params: {
                khachHangId: this.khachhang_id,
              },
            });
            ds_dt_dtn = rs.data && rs.data.data ? rs.data.data : [];
            if (ds_dt_dtn && ds_dt_dtn.length > 0) {
              if (ds_dt_dtn[0]["doanhthu"] != "")
                this.pt_doanhthu = parseFloat(ds_dt_dtn[0]["doanhthu"]);
              else this.pt_doanhthu = 0;

              if (ds_dt_dtn[0]["dtn"] != "")
                this.pt_diem_tn = Number(ds_dt_dtn[0]["dtn"]);
              else this.pt_diem_tn = 0;
            } else {
              this.pt_doanhthu = 0;
              this.pt_diem_tn = 0;
            }
          }

          if (ds[0]["nganhnghe_id"] != "")
            this.vnganhnghe_id = Number(ds[0]["nganhnghe_id"]);
          else this.vnganhnghe_id = 0;

          this.loaigt_id = ds[0]["loaigt_id"];
          //Thông tin thuê bao
          this.$refs.txtMaHD.value = ds[0]["ma_hd"];
          ///TuấnNA thêm code chặn trường hợp thuê bao di động nhập mới không cùng mã thanh toán với các thuê bao di động có trong hợp đồng
          ///// Ngày 25/08/2010
          if (
            this.dichvuvt_id == DichVuVienThong.DI_DONG &&
            Number(this.$refs.cboLoaihinhTB.value) == LoaiHinhTB.DIDONGTRASAU
          ) {
            if (this.ma_gd) {
              rs = await this.axios.post(
                "web-hopdong/hopdong/kiemtra_thuebao_cung_matt",
                {
                  dichVuVtId: 2,
                  hdttId: this.thuebao_id,
                  maGd: this.ma_gd,
                }
              );
              if (rs.data.data != "1") {
                this.$toast.error(
                  "Số thuê bao di động: " +
                  this.$refs.txtMaTB.value +
                  " vừa nhập không cùng mã thanh toán: " +
                  ds[0]["ma_tt"] +
                  " với thuê bao di động trong phụ lục\nBạn phải lập một phụ lục khác cho thuê bao này! "
                );
                this.loading(false)
                return;
              }
            }
          }
          this.$refs.txtTenTB.value = ds[0]["ten_tb"];
          this.$refs.txtDiaChiTB.value = ds[0]["diachi_tb"];
          console.log("Tao vào đây cơ", ds[0])
          rs = await this.axios.get(
            "web-hopdong/hopdong/lay_ds_diachi_theo_thuebao",
            { params: {
              thueBaoId: this.thuebao_id,
            }}
          );
          this.HienThiTTDiaChi(rs.data.data, 3);
          this.$refs.cboDoituongTB.value = ds[0]["doituong_id"];
          this.loaitb_id = Number(ds[0]["loaitb_id"]);
          //TuanNA them code hien thi loaitb_id -- Ngay 31/07/2010
          this.$refs.cboLoaihinhTB.value = Number(ds[0]["loaitb_id"]);
          this.donvi_id = Number(ds[0]["donviql_id"]);
          this.$refs.txtEmailTB.value = ds[0]["email_tb"];
          ///TuấnNA bổ sung code mã TB -- Ngày 05/08/2010
          this.ma_tb_cu = ds[0]["ma_tb"];
          ///TuấnNA bổ sung địa chỉ lắt đặt -- ngày 31/07/2010
          this.$refs.txtDiachiLD.value = ds[0]["diachi_ld"];
          if (this.dichvuvt_id == 2) {
            //lấy dữ liệu gói cước cho các hàm vinaphone
            if (ds[0]["goicuoc_id"] != "")
              this.goicuoc_id = ds[0]["goicuoc_id"];
          }
          this.HienThiTTDBMoRong(this.thuebao_id, this.dichvuvt_id);

          this.LAY_DS_SUDUNG_DVGT(this.thuebao_id);
          this.LAY_DS_DANGKY_DVK(this.thuebao_id);
        }
        //endregion
        //}
        //endregion
      } else {
        this.$toast.error(
          "Kiểm tra lại mã thuê bao tìm kiếm, dịch vụ viễn thông !"
        );
      }
      resolve(true)
      })
    },
    //endregion

    async HT_ThongTin_Thanhtoan(vthuebao_id, vkieu_yc) {
      try {
        let ds = [];
        let rs = await this.axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_thuebao",{ param: vthuebao_id, type: 1 });
        let vthanhtoan_id = rs.data.data;
        if (vthanhtoan_id != "-1") {
          rs = await this.axios.post(
            "web-hopdong/hopdong/khac/lay_ds_dbtt_theo_ttid",
            {
              thanhtoan_id: vthanhtoan_id,
            }
          );
          ds = rs.data.data;
          if (ds && ds.length > 0) {
            if (vkieu_yc == KieuLapDat.TACH_THUEBAO) {
              //txtMaTT.value = "";
              ///TuấnNA sửa code lấy thêm thông tin -- Ngày 31/07/2010
              ///thanhtoan_id = 0;
              this.thanhtoan_id = Number(ds[0]["thanhtoan_id"]);
              rs = await this.axios.post(
                "web-hopdong/hopdong/ht_combo_ma_daidien",
                {
                  thanhtoan_id: this.thanhtoan_id,
                }
              );
              let dt_madd = rs.data.data;
              this.$refs.cboMaDD.dataSource = dt_madd;
              //
              this.$refs.cboMaDD.value = ds[0]["matb_dd"];

              this.ma_tt_cu = ds[0]["ma_tt"];

              this.$refs.txtMaIG.value = ds[0]["main_ghep"];
              if (ds[0]["mig_id"] != "") {
                this.mig_id = Number(ds[0]["mig_id"]);
              }

              this.$refs.txtTenTT.value = "";
              this.$refs.txtDiaChiTT.value = "";
              this.$refs.txtMST_TT.value = "";
              this.$refs.txtSTK_TT.value = "";
              //cboNganHang.SelectedIndex = 0;
              this.$refs.cboNganHang.SelectedIndex = 0;
              this.$refs.cboHTTT.SelectedIndex = 0;

              this.tinhtt_id = 0;
              this.quantt_id = 0;
              this.phuongtt_id = 0;
              this.phott_id = 0;
              this.aptt_id = 0;
              this.khutt_id = 0;
              this.dacdiemtt_id = 0;
              this.sonhatt = "";

              this.$refs.cboDonViQL.value = ds[0]["donvi_id"];

              this.$refs.txtTenTT.value = ds[0]["ten_tt"];
              this.ten_tt = ds[0]["ten_tt"];
              this.$refs.txtDiaChiTT.value = ds[0]["diachi_tt"];
              this.$refs.txtDiaChiBC.value = ds[0]["diachi_bc"];
              this.diachi_tt = ds[0]["diachi_tt"];

              rs = await this.axios.get(
                "web-hopdong/thaydoithongtinkhuyenmai/lay_ds_diachi_theo_dbttid",
                {
                  params: {
                    dbttid: this.thanhtoan_id,
                  },
                }
              );
              this.HienThiTTDiaChi(rs.data.data, 2);
              //TuấnNA thêm code hiển thị thông tin NSNN trong bảng HDTT // Ngày 12/11/2010
              this.DV_QHNS = ds[0]["DV_QHNS"];
              this.MACHUONG = ds[0]["MACHUONG"];
              this.MN_KT = ds[0]["MN_KT"];
              this.MA_NDKT = ds[0]["MA_NDKT"];
              this.MN_NSNN = ds[0]["MN_NSNN"];
              if (ds[0]["TIEN_DM"] != "")
                this.TIEN_DM = Number(ds[0]["TIEN_DM"]);
              else this.TIEN_DM = 0;
              //

              this.$refs.txtMST_TT.value = ds[0]["mst"];
              this.$refs.txtSTK_TT.value = ds[0]["stk"];

              ///TuấnNA bổ xung các thông tin thanh toán lên form -- Ngày 30/07/2010
              this.$refs.txtDiachiCT.value = ds[0]["diachi_ct"];

              this.$refs.txtGhiChu.value = ds[0]["ghichu"];

              if (ds[0]["nhanvien_id"] != "") {
                this.nhanvientc_id = Number(ds[0]["nhanvien_id"]);
                let rs = await this.axios.post(
                  "/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien",{ param: this.nhanvientc_id, type: 4 }
                );
                this.$refs.txtMaNVTC.value = rs.data.data;
              } else {
                this.nhanvientc_id = 0;
                this.$refs.txtMaNVTC.value = "";
              }
              if (ds[0]["tuyenthu_id"] != "") {
                this.tuyenthu_id = Number(ds[0]["tuyenthu_id"]);
                this.nhanvientc_id = Number(ds[0]["nhanvien_id"]);
                let rs = await this.axios.post(
                  "/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tuyenthu",{ param: this.tuyenthu_id, type: 1 }
                );
                this.$refs.txtMaTuyen.value = rs.data.data;
              } else {
                this.tuyenthu_id = 0;
                this.$refs.txtMaTuyen.value = "";
              }
              if (ds[0]["nganhang_id"] != "") {
                this.$refs.cboNganHang.value = ds[0]["nganhang_id"];
              }
              this.$refs.cboHTTT.value = ds[0]["httt_id"];
              this.donviql_id = ds[0]["donvi_id"];
              this.$refs.cboDonViQL.value = Number(this.donviql_id);
              this.HienThi_NVQL_AM_DB_HD(1, this.thanhtoan_id);
            } else {
              //txtMaTT.value = "";
              this.thanhtoan_id = 0;
              this.$refs.txtTenTT.value = "";
              this.$refs.txtDiaChiTT.value = "";
              this.$refs.txtMST_TT.value = "";
              this.$refs.txtSTK_TT.value = "";
              this.$refs.cboNganHang.value = 0;
              this.$refs.cboHTTT.SelectedIndex = 0;
              rs = await this.axios.post(
                "web-hopdong/lapdatmoi/lay_thamso_macdinh",
                {
                  vma_ts: "DONVITHU",
                  vnguoidung_id: this.$root.token.getNguoiDungID(),
                }
              );

              if (rs.data.data != "") {
                this.$refs.cboDonViQL.value = rs.data.data;
              }

              this.tinhtt_id = 0;
              this.quantt_id = 0;
              this.phuongtt_id = 0;
              this.phott_id = 0;
              this.aptt_id = 0;
              this.khutt_id = 0;
              this.dacdiemtt_id = 0;
              this.sonhatt = "";

              //Thông tin thanh toán // comment cũ
              //txtMaTT.value = ds[0]["ma_tt"]
              this.thanhtoan_id = Number(ds[0]["thanhtoan_id"]);
              rs = await this.axios.post(
                "web-hopdong/hopdong/ht_combo_ma_daidien",
                {
                  thanhtoan_id: this.thanhtoan_id,
                }
              );
              let dt_madd = rs.data.data;
              console.log(dt_madd, "Bố máy đây")
              this.$refs.cboMaDD.dataSource = dt_madd;
              this.$refs.cboMaDD.dataTextField = 'ma_tb';
              this.$refs.cboMaDD.dataValueField = 'ma_tb';
              //
              this.$refs.cboMaDD.value = ds[0]["matb_dd"];

              //TuấnNA bổ sung code kiểm tra đơn vị quản lý địa chỉ thanh toán của thuê bao // Ngày 21/05/2012
              ////> nếu đơn vị quản lý của user khác với đơn vị quản lý địa chỉ thanh toán cũ của thuê bao //> không cho lập PL
              ////> ngược lại làm bình thường
              //if (this.tt_nd.donvi_dl_id != ds[0]["donvi_id"])
              //{
              //    this.$toast.error("Đơn vị quản lý của user lập phụ lục hợp đồng khác với đơn vị quản lý địa chỉ thanh toán:\n-- "
              //        + ds[0]["diachi_tt"]
              //        + "--\ncủa thuê bao. Khách hàng phải về đúng đơn vị thanh toán để lập phụ lục hợp đồng tách, ghép cho thuê bao này !");
              //    if (hdkh_id == 0)
              //        this.SetButton(1);
              //    return;
              //}
              //
              ///TuấnNA thêm code lấy mã thanh toán cũ cho thuê bao thực hiện tách nhập // Ngày 19/08/2010
              this.ma_tt_cu = ds[0]["ma_tt"];
              this.$refs.txtTenTT.value = ds[0]["ten_tt"];
              this.ten_tt = ds[0]["ten_tt"];
              this.$refs.txtDiaChiTT.value = ds[0]["diachi_tt"];
              this.$refs.txtDiaChiBC.value = ds[0]["diachi_bc"];
              this.diachi_tt = ds[0]["diachi_tt"];
              //this.$refs.txtMaTBDD.value = ds[0]["matb_dd"]
              rs = await this.axios.get(
                "web-hopdong/thaydoithongtinkhuyenmai/lay_ds_diachi_theo_dbttid",
                {
                  params: {
                    dbttid: this.thanhtoan_id,
                  },
                }
              );
              this.HienThiTTDiaChi(rs.data.data, 2);
              this.$refs.txtMST_TT.value = ds[0]["mst"];
              this.$refs.txtSTK_TT.value = ds[0]["stk"];
              ///TuấnNA bổ xung các thông tin thanh toán lên form -- Ngày 30/07/2010
              this.$refs.txtDiachiCT.value = ds[0]["diachi_ct"];
              if (ds[0]["nhanvien_id"] != "") {
                this.nhanvientc_id = Number(ds[0]["nhanvien_id"]);
                rs = await this.axios.post(
                  "/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien",{ param: this.nhanvientc_id, type: 4 }
                  
                );
                this.$refs.txtMaNVTC.value = rs.data.data;
              } else {
                this.nhanvientc_id = 0;
                this.$refs.txtMaNVTC.value = "";
              }
              if (ds[0]["tuyenthu_id"] != "") {
                this.tuyenthu_id = Number(ds[0]["tuyenthu_id"]);
                rs = await this.axios.post(
                  "/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tuyenthu",{ param: this.tuyenthu_id, type: 1 }
                );
                this.$refs.txtMaTuyen.value = rs.data.data;
              } else this.tuyenthu_id = 0;

              this.$refs.txtGhiChu.value = ds[0]["ghichu"];

              if (ds[0]["nganhang_id"] != "") {
                this.$refs.cboNganHang.value = ds[0]["nganhang_id"];
              }
              this.$refs.cboHTTT.value = ds[0]["httt_id"];
              this.donviql_id = ds[0]["donvi_id"];
              this.$refs.cboDonViQL.value = Number(this.donviql_id);
              this.HienThi_NVQL_AM_DB_HD(1, this.thanhtoan_id);
            }
          } else {
            this.$toast.error(
              "Không tìm thấy thông tin thanh toán của thuê bao!"
            );
          }
        } else {
          this.$toast.error(
            "Không tìm thấy thông tin thanh toán của thuê bao!"
          );
        }
      } catch (err) {
        console.log(err)
        this.$toast.error("Có lỗi: " + err.message);
      }
    },

    async CapNhat_DiaChiBC() {
      try {
        if (this.$refs.txtDiaChiBC.value != "") {
          await this.axios.post("web-hopdong/chuyendich/capnhat_diachi_bc",
            {
              "ap_id": this.apbc_id,
              "dacdiem_id": this.dacdiembc_id,
              "diachi": this.$refs.txtDiaChiBC.value.trim(),
              "diachibc_id": this.diachibc_id,
              "hdtt_id": this.hdtt_id,
              "khu_id": this.khubc_id,
              "pho_id": this.phobc_id,
              "phuong_id": this.phuongbc_id,
              "quan_id": this.quanbc_id,
              "sonha": this.sonhabc,
              "tinh_id": this.tinhbc_id
            })
        } else {
          await this.axios.post("web-hopdong/chuyendich/capnhat_diachi_bc",
            {
              "ap_id": this.apbc_id,
              "dacdiem_id": this.dacdiembc_id,
              "diachi": "0",
              "diachibc_id": this.diachibc_id,
              "hdtt_id": this.hdtt_id,
              "khu_id": this.khubc_id,
              "pho_id": this.phobc_id,
              "phuong_id": this.phuongbc_id,
              "quan_id": this.quanbc_id,
              "sonha": this.sonhabc,
              "tinh_id": this.tinhbc_id
            })
        }
      } catch (err) {
        console.log(err)
        this.$toast.error("Có lỗi: " + err.message);
      }
    },

    //region HienThiTTDBMoRong
    async HienThiTTDBMoRong(thuebao_id, dichvuvt_id) {
      //Lấy trang bị _id từ bảng con
      this.tocdo_id = 0;
      this.muccuoc_id = 0;
      this.trangbi_id = 0;
      let daset = [];
      if (dichvuvt_id == DichVuVienThong.CO_DINH) {
        rs = await this.axios.post(
          "web-hopdong/thaydoiloaihinhtbcodinhims/lay_ds_db_cd_theo_tbid",
          {
            thuebao_id: this.thuebao_id,
          }
        );
        daset = rs.data.data;
        if (daset.length > 0) {
          if (daset[0]["trangbi_id"].trim() != "")
            this.trangbi_id = Number(daset[0]["trangbi_id"].trim());
        } else this.trangbi_id = 0;
      } else if (this.dichvuvt_id == DichVuVienThong.ADSL) {
        rs = await this.axios.get("web-hopdong/hopdong/lay_ds_danhba_adsl", {
          params: {
            thueBaoId: this.thuebao_id,
          },
        });
        daset = rs.data.data;
        if (daset.length > 0) {
          if (daset[0]["trangbi_id"])
            this.trangbi_id = Number(daset[0]["trangbi_id"]);
          if (daset[0]["tocdo_id"])
            this.tocdo_id = Number(daset[0]["tocdo_id"]);

          if (
            Number(this.$refs.cboLoaihinhTB.value) ==
            LoaiHinhTB.INTERNET_FTTH ||
            Number(this.$refs.cboLoaihinhTB.value) == LoaiHinhTB.WIFI_FIBER ||
            Number(this.$refs.cboLoaihinhTB.value) ==
            LoaiHinhTB.INTERNET_ADSL ||
            Number(this.$refs.cboLoaihinhTB.value) ==
            LoaiHinhTB.INTERNET_1260 ||
            Number(this.$refs.cboLoaihinhTB.value) ==
            LoaiHinhTB.INTERNET_FIBER_TINH_KHAC
          ) {
            this.muccuoc_id = Number(daset[0]["muccuoc_id"]);
          }
        }
      } else if (
        this.dichvuvt_id == DichVuVienThong.DICHVU_CNTT ||
        this.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH ||
        this.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT ||
        this.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU
      ) {
        rs = await this.axios.get("web-hopdong/hopdong/lay_ds_danhba_cntt", {
          params: {
            thueBaoId: this.thuebao_id,
          },
        });
        daset = rs.data.data;
        if (daset && daset.length > 0) {
          //if (daset[0]["trangbi_id"].trim() != "")
          //    trangbi_id = Number(daset[0]["trangbi_id"].trim());
          this.trangbi_id = 0;
          if (daset[0]["tocdo_id"])
            this.tocdo_id = Number(daset[0]["tocdo_id"]);
          if (daset[0]["muccuoc_id"])
            this.muccuoc_id = Number(daset[0]["muccuoc_id"]);
        }
      } else if (this.dichvuvt_id == DichVuVienThong.DI_DONG) {
        rs = await this.axios.get("web-hopdong/hopdong/lay_ds_danhba_dd", {
          params: {
            thueBaoId: this.thuebao_id,
          },
        });
        daset = rs.data.data;
        if (daset.length > 0) {
          if (daset[0]["trangbi_id"])
            this.trangbi_id = Number(daset[0]["trangbi_id"]);
        } else this.trangbi_id = 0;
      } else if (this.dichvuvt_id == DichVuVienThong.MEGAWAN) {
        rs = await this.axios.get("web-hopdong/hopdong/lay_ds_danhba_megawan", {
          thueBaoId: this.thuebao_id,
        });
        daset = rs.data.data;
        if (daset.length > 0) {
          if (daset[0]["trangbi_id"].trim() != "")
            this.trangbi_id = Number(daset[0]["trangbi_id"].trim());
          if (daset[0]["tocdo_id"].trim() != "")
            this.tocdo_id = Number(daset[0]["tocdo_id"].trim());
        }
      } else if (this.dichvuvt_id == DichVuVienThong.TSL) {
        rs = await this.axios.get("web-hopdong/hopdong/lay_ds_danhba_tsl", {
          params: {
            thueBaoId: this.thuebao_id,
          },
        });
        daset = rs.data.data;
        if (daset.length > 0) {
          if (daset[0]["trangbi_id"].trim() != "")
            this.trangbi_id = Number(daset[0]["trangbi_id"].trim());
          if (daset[0]["tocdo_id"].trim() != "")
            this.tocdo_id = Number(daset[0]["tocdo_id"].trim());
        }
      } else if (dichvuvt_id == DichVuVienThong.GPHONE) {
        rs = await this.axios.get("web-hopdong/hopdong/lay_ds_danhba_gp", {
          params: {
            thueBaoId: this.thuebao_id,
          },
        });
        daset = rs.data.data;
        if (daset.length > 0) {
          if (daset[0]["trangbi_id"].trim() != "")
            this.trangbi_id = Number(daset[0]["trangbi_id"].trim());
        } else this.trangbi_id = 0;
      } else if (this.dichvuvt_id == DichVuVienThong.MEGA_EYES) {
        rs = await this.axios.get("web-hopdong/hopdong/lay_ds_danhba_megaeys", {
          params: {
            thueBaoId: this.thuebao_id,
          },
        });
        daset = rs.data.data;
        if (daset.length > 0) {
          if (daset[0]["trangbi_id"].trim() != "")
            this.trangbi_id = Number(daset[0]["trangbi_id"].trim());
          if (daset[0]["tocdo_id"].trim() != "")
            this.tocdo_id = Number(daset[0]["tocdo_id"].trim());
        }
      } else if (dichvuvt_id == 50) {
        //Dich vu CNTT
        this.Init_Properties(
          Number(this.$refs.cboLoaihinhTB.value),
          "HDTB_CNTT",
          "00000",
          ItemList
        );
        rs = await this.axios.get("web-hopdong/hopdong/lay_ds_danhba_cntt", {
          params: {
            thueBaoId: this.thuebao_id,
          },
        });
        daset = rs.data.data;
        if (daset.length > 0) {
          if (daset[0]["linhvuc_id"].trim() != "")
            this.linhvuc_id = Number(daset[0]["linhvuc_id"].trim());
        } else this.linhvuc_id = 0;
        this.tocdo_id = 0;
      }
    },
    //endregion

    //region Hiển thị danh sách TB
    async HT_DanhSachTB() {
      ///TuấnNA sửa code hiển thị danh sách hợp đồng thuê bao
      ///// Ngày 25/08/2010
      return new Promise(async (resolve, reject) => {
        let tongtienhd = 0;
        let tongtientb = 0;
        let dsTB = [];
        rs = await this.axios.get(
          "web-hopdong/hopdong/lay_ds_hopdong_thuebao_theo_hdkh",
          {
            params: {
              hdKhId: this.hdkh_id,
            },
          }
        );
        dsTB = rs.data.data;

        ///TuấnNA comment code để thay thế listview danh sách thuê bao cho lstbxDSMaTB // Ngày 25/08/2010
        //lstbxDSMaTB.DataSource = dsTB.Tables[0];
        //lstbxDSMaTB.DisplayMember = "ma_tb";
        //lstbxDSMaTB.valueMember = "hdtb_id";

        this.$refs.lvwThueBao.dataSource = [];
        let myDataTables = dsTB;
        /// đẩy dữ liệu vào luới thuê bao
        let stt = 1
        for (let i = 0; i < myDataTables.length; i++) {
          let myRow = myDataTables[i];
          let lvItem = {};
          this.tongtientb = parseFloat(myRow["tongtien"]);
          lvItem.stt = stt;
          lvItem.ma_tb = myRow["ma_tb"];
          lvItem.hdtb_id = myRow["hdtb_id"];
          lvItem.ten_tb = myRow["ten_tb"];
          lvItem.diachi_ld = myRow["diachi_ld"];
          lvItem.ten_dvvt = myRow["ten_dvvt"];
          lvItem.ten_kieuld = myRow["ten_kieuld"];
          lvItem.trangthai_dongbo = myRow["trangthai_dongbo"];
          lvItem.dichvuvt_id = myRow["dichvuvt_id"];
          tongtienhd = tongtienhd + tongtientb;
          this.$refs.lvwThueBao.dataSource.push(lvItem);
          if (this.$refs.lvwThueBao.dataSource.length == 1)
            this.$refs.tsbtnXoaTB.classList.add("disabled");
          else this.$refs.tsbtnXoaTB.classList.remove("disabled");
          stt++
        }
        //this.$refs.tsbtnThemTT.disabled = false
        //this.$refs.tsbtnXoaTT.disabled = false
        ///TuấnNA thêm code lấy giá trị của thuê bao hdtb_id_dtg đang thực hiện tách ghép -- Ngày 31/07/2010
        rs = await this.axios.get(
          "web-hopdong/hopdong/lay_ds_hopdong_thuebao_theo_hdkh",
          {
            params: {
              hdKhId: this.hdkh_id,
            },
          }
        );
        if (rs.data && rs.data.data && rs.data.data.length > 0) {
          if (rs.data.data[0]["hdtb_id"] != "") {
            this.hdtb_id_dtg = Number(rs.data.data[0]["hdtb_id"]);
          }
          this.$refs.tsbtnThemTT.classList.remove("disabled");
          //this.$refs.tsbtnXoaTT.disabled = false
        }
        //Tuan them code lay khachhang_id cua khach hang dang chuyen quyen -- Ngày 31/07/2010
        let ds_khcu = [];
        rs = await this.axios.post(
          "web-hopdong/khoiphucthanhly/lay_biendong_theo_hdtb_id",
          {
            hdtb_id: this.hdtb_id_dtg,
          }
        );
        ds_khcu = rs.data.data ? rs.data.data : [];
        if (ds_khcu.length > 0) {
          this.khachhang_id_dtg = Number(ds_khcu[0]["khachhang_id"]);
        }
        /// TuấnNA thêm code kiểm tra hợp đồng/phụ lục có thuê bao di động không  -- Ngày 01/08/2010
        console.log(rs.data, "kjahdjkahdkj dahjkd dhakdja")
        for (let i = 0; i < rs.data.data.length; i++) {
          if (
            Number(rs.data.data[i]["dichvuvt_id"]) == DichVuVienThong.DI_DONG &&
            Number(rs.data.data[i]["loaitb_id"]) == LoaiHinhTB.DIDONGTRASAU
          ) {
            if (Number(rs.data.data[i]["status"]) == 1) {
              this.check_tb_dd = true;
              break;
            }
          }
        }
        this.$refs.txtTongTienHD.value = tongtienhd;
        resolve(true)
      })
      //cmUtili.Hienthi_Mau_ListView(lvwThueBao);
    },
    //endregion

    //region Kiểm tra dữ liệu
    KiemTraDL_KhachHang() {
      if (this.$refs.txtTenKH.value == "") {
        this.$toast.error("Hãy nhập tên khách hàng !");
        this.$refs.txtTenKH.focus();
        return false;
      }

      if (
        this.$refs.txtDiaChiKH.value == "" ||
        this.quankh_id == 0 ||
        this.phuongkh_id == 0
      ) {
        this.$toast.error("Hãy nhập địa chỉ khách hàng !");
        this.$refs.txtDiaChiKH.focus();
        return false;
      }

      if (this.$refs.txtMaKH.value == "" && this.khachhang_id != 0) {
        this.$toast.error("Kiểm tra lại mã khách hàng !");
        this.$refs.txtMaKH.focus();
        return false;
      }
      if (this.batbuoc_gioithieu_tachnhap == 1) {
        if (
          !this.$refs.chkNguoiGT.checked ||
          !this.$refs.txtNguoiGT.value ||
          this.nguoigt_id == 0
        ) {
          this.$toast.error("Bạn chưa chọn người giới thiệu hợp đồng !");
          this.$refs.txtNguoiGT.focus();
          return false;
        }
      }

      if (
        this.$refs.chkNguoiGT.checked &&
        this.$refs.txtNguoiGT.value &&
        this.nguoigt_id == 0
      ) {
        this.$toast.error("Bạn chưa chọn người giới thiệu hợp đồng !");
        this.$refs.txtNguoiGT.focus();
        return false;
      }

      return true;
    },

    async KiemTraDL_ThanhToan() {
      return new Promise(async (resolve, reject) => {
        // kiem tra ma nhan vien thu cuoc
        if (this.$refs.txtMaNVTC.value == "") {
          this.$toast.error("Hãy nhập mã nhân viên thu cước !");
          this.$refs.txtMaNVTC.focus();
          reject(false);
        }
        if (!this.$refs.cboMaDD.value) {
          this.$toast.error("Hãy chọn mã dại diện !");
          // this.$refs.cboMaDD.focus();
          reject(false);
        }
        ///TuấnNA thêm code chặn trường hợp nhập thuê bao nhưng mã thanh toán trống // Ngày 25/08/2010
        if (
          Number(this.$refs.cboKieuLD.value) == KieuLapDat.NHAP_THUEBAO &&
          this.$refs.txtMaTT.value == ""
        ) {
          this.$toast.error(
            "Bạn đang chọn kiểu yêu cầu là Nhập thuê bao, không được để mã thanh toán trống!"
          );
          reject(false);
        }
        if (this.$refs.txtTenTT.value == "") {
          this.$toast.error("Hãy nhập tên thanh toán !");
          this.$refs.txtTenTT.focus();
          reject(false);
        }

        if (this.$refs.txtDiaChiTT.value == "") {
          this.$toast.error("Hãy nhập địa chỉ thanh toán!");
          this.$refs.txtDiaChiTT.focus();
          reject(false);
        }

        if (this.quantt_id == 0) {
          this.$toast.error("Địa chỉ thanh toán chưa có thông tin quận!");
          this.$refs.txtDiaChiTT.focus();
          reject(false);
        }

        //if (phuongtt_id == 0)
        //{
        //    this.$toast.error("Địa chỉ thanh toán chưa có thông tin phường!");
        //    txtDiaChiTT.focus();
        //    return false;
        //}

        if (this.ma_tt_cu == "" && this.thanhtoan_id != 0) {
          this.$toast.error("Kiểm tra lại mã thanh toán!");
          this.$refs.txtMaTT.focus();
          reject(false);
        }
        if (Number(this.$refs.cboHTTT.value) == HinhThucThanhToan.UYNHIEM_THU) {
          if (this.$refs.txtSTK_TT.value.trim() == "") {
            this.$toast.error("Hãy nhập số tài khoản!");
            this.$refs.txtSTK_TT.focus();
            reject(false);
          }
        }


          rs = await this.axios.post("web-hopdong/tachnhapthuebao/fn_kiemtradl_tachnhaptb",
          {"kieu":2,"kieuld_id":this.$refs.cboKieuLD.value,"ma_tt":this.$refs.txtMaTT.value,"ma_kh": this.$refs.txtMaKH.value,"thuebao_id": this.thuebao_id})

        //Kiểm tra xem mã thanh toán có thuộc mã kh ko
        //ko thuộc thì cho nghỉ nhập luôn, tránh việc ko đồng bộ với VNP
        //Hieutc: 16.07.2011
        if (Number(this.$refs.cboKieuLD.value) == KieuLapDat.NHAP_THUEBAO) {
          //kiểm tra xem mã thanh toán có thuộc khách hàng hay ko?
        }
        //Hieutc: 11.10.2011: Kiểm tra mã thanh toán đã theo đúng chuẩn chưa
        if (this.$refs.txtMaTT.value.trim() != "") {
          rs = await this.axios.post(
            "web-hopdong/hopdong/kiemtra_makh_matt_magd",
            {
              kieu: 2,
              loaiHdId: LoaiHopDong.TACHNHAP_TB,
              maKh: this.$refs.txtMaTT.value.trim(),
            }
          );
          if (rs.data.data == 0) {
            this.$toast.error(
              "Mã thanh toán không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!"
            );
            this.$refs.txtMaTT.focus();
            reject(false);
          }
        }
        if (this.$refs.txtMaTuyen.value.trim() == "") {
          this.$toast.error("Hãy nhập tuyến thu!");
          this.$refs.txtMaTuyen.focus();
          reject(false);
        }

        resolve(true);
      });
    },

    KiemTraDL_ThueBao() {
      if (this.$refs.txtMaHD.value == "" && this.khachhang_id != 0) {
        this.$toast.error("Hãy nhập mã hợp đồng !");
        this.$refs.txtMaHD.focus();
        return false;
      }

      if (this.$refs.txtTenTB.value == "") {
        this.$toast.error("Hãy nhập tên thuê bao !");
        this.$refs.txtTenTB.focus();
        return false;
      }

      if (
        this.$refs.txtDiaChiTB.value == "" ||
        this.quantb_id == 0 ||
        this.phuongtb_id == 0
      ) {
        this.$toast.error("Hãy nhập địa chỉ thuê bao !");
        this.$refs.txtDiaChiTB.focus();
        return false;
      }
      if (this.$refs.cboKieuLD.value == null) {
        this.$toast.error("Hãy chọn kiểu yêu cầu cho thuê bao này !");
        return false;
      }
      // end hoàng thêm
      return true;
    },

    KiemTraDL_ThueBao_DS() {
      if (this.$refs.txtMaHD.value == "" && this.khachhang_id != 0) {
        this.$toast.error("Hãy nhập mã hợp đồng !");
        this.$refs.txtMaHD.focus();
        return false;
      }
      //Tách ghép Kiểm tra thay đổi gói đa dv khi thanh toán
      //// hoangpkn : 31/10/2013. Thêm hàm kiểm tra gói theo yc a Thắng
      //string kq = "";
      //kq = laphd.KIEMTRA_THAYDOI_GOI_DADV_2(thuebao_id, LoaiHopDong.TACHNHAP_TB);
      //if (kq != "1")
      //{
      //    this.$toast.error(kq);
      //    return false;
      //}
      // end hoàng thêm
      return true;
    },
    //endregion

    //region cboHTTT SelectedValueChanged
    cboHTTT_SelectedValueChanged() {
      if (this.$refs.cboHTTT.dataSource.length > 0) {
        if (this.$refs.cboHTTT.value == "") return;

        this.httt_id = Number(this.$refs.cboHTTT.value);

        if (this.httt_id == HinhThucThanhToan.UYNHIEM_THU) {
          this.$refs.cboNganHang.disabled = false;
        } else {
          this.$refs.cboNganHang.disabled = true;
        }

        //region Lấy lại thông tin ĐVQL, NVTC theo hình thức TT
        if (this.TUYENTHU_HTTT == 1) {
          if (
            this.phuongtt_id != 0 &&
            (this.phott_id != 0 || this.aptt_id != 0 || this.khutt_id != 0)
          ) {
            //region bỏ
            //    DataSet ds = []

            //    ds = new LapHopDongFacade2().Lay_ds_donviql_v2(phuongtt_id, phott_id, aptt_id, khutt_id, 1,
            //        Number(loaikh_id), Number(cboHTTT.value));

            //    if (ds.length <= 0)
            //    {
            //        if (cboDonViQL.dataSource.length != 1)
            //        {
            //            this.$toast.error("Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý!");
            //            cboDonViQL.value = "";
            //            return;
            //        }
            //    }
            //    else if (ds.length == 1)
            //        cboDonViQL.value = Number(ds[0]["donvi_id"]);
            //    else
            //    {
            //        frmChonDonVi frm = new frmChonDonVi(ds);
            //        frm.donvi_index_id = Number(cboDonViQL.value);
            //        frm.ShowDialog();
            //        if (frm.this.chapnhan)
            //        {
            //            cboDonViQL.value = frm.donvi_ql_id;
            //        }
            //        else
            //        {
            //            return;
            //        }
            //    }
            //}
            //endregion

            this.HT_NVTC_TheoDC(
              this.quantt_id,
              this.phuongtt_id,
              this.phott_id,
              this.aptt_id,
              this.khutt_id,
              Number(this.$refs.cboDonViQL.value),
              this.dacdiemtt_id
            );
          }
          //HienThi_NVQL_AM_HTTT(Number(loaikh_id), Loai_NV.NHANVIEN_KINHDOANH, phuongtt_id, phott_id, aptt_id, khutt_id, nhanvientc_id, Number(cboDonViQL.value), httt_id);
        }
        //endregion
      }
    },
    //endregion

    //region KeyPress
    txtMaTB_KeyPress() {
      try {
        //this.$refs.txtMaTB.value = this.$refs.txtMaTB.value.trim().toLowerCase();
        this.Lay_Thongtin();
      } catch (err) {
        this.$toast.error("Có lỗi :" + err.message);
      }
    },

    ConvertLower(arr) {
      if(!arr)
        return []
      return arr.map(function(item) {
          return Object.fromEntries( 
            Object.entries(item).map(([k, v]) => [k.toLowerCase(), v])
          )
      });
    },
    async Lay_Thongtin() {
      
      try {
        this.loading(true)
        if (this.$refs.txtMaTB.value != "") {
          rs = await this.axios.post(
            "web-hopdong/hopdong/lay_ds_hopdong_theo_matb",
            {
              dichVuVtId: this.$refs.cboDichVuVT.value,
              donViDlId: this.$root.token.getDonViID(),
              loaiHdId: LoaiHopDong.TACHNHAP_TB,
              loaiTbId: this.$refs.cboLoaihinhTB.value,
              maTb: this.$refs.txtMaTB.value,
              ttHdId: TrangThaiHD.MOI,
            }
          );
          let ds = rs.data.data;
          if (ds.length > 0) await this.HienThiTTHopDongKH(ds);
          else await this.HienThiTT_DanhBa(this.$refs.txtMaTB.value);
        }
        this.loading(false)
      } catch(err) {
        this.loading(false)
        console.log(err);
      }
    },

    async txtMaGD_KeyPress() {
      this.loading(true)
      if (this.$refs.txtMaGD.value != "") {
        rs = await this.axios.post(
          "web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd",
          {
            in_donvi_dl_id: 0,
            in_loaihd_id: LoaiHopDong.TACHNHAP_TB,
            in_ma_gd: this.$refs.txtMaGD.value,
            in_tthd_id: TrangThaiHD.MOI
          }
        );
        if(rs.data.error_code != "BSS-00000000") {
          this.$toast.error(rs.data.message)
          this.loading(false)
          return
        }
        await this.HienThiTTHopDongKH(rs.data.data);
        this.loading(false)
      }
    },

    txtMaKH_KeyPress() {
      return; ///TuấnNa thêm code : không sử dụng việc gõ mã khách hàng --> Enter
    },
    /// <summary>
    /// Tuấn NA thêm hàm txtMaTT_Enter //> gọi lại lúc bắn dữ liệu từ form tìm kiếm mã thanh toán
    /// // Ngày 25/08/2010
    /// </summary>
    async txtMaTT_Enter() {
      if (Number(this.$refs.cboKieuLD.value) == KieuLapDat.TACH_THUEBAO) {
        this.$toast.error(
          "Bạn đang chọn kiểu yêu cầu là: " +
          cboKieuLD.value +
          "\nMã thanh toán mới sẽ tự sinh! Bạn không được chọn!"
        );
        this.$refs.txtMaTT.value = "";
      } else {
        rs = await this.axios.post(
          "web-hopdong/hopdong/kiemtra_makh_matt_magd",
          {
            kieu: 2,
            loaiHdId: LoaiHopDong.TACHNHAP_TB,
            maKh: this.$refs.txtMaTT.value.trim(),
          }
        );
        if (rs.data.data == 0) {
          this.$toast.error(
            "Mã thanh toán không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!"
          );
          this.$refs.txtMaTT.focus();
          return;
        }
        this.HienThiTT_DanhBa_KH_NhanTB(this.$refs.txtMaTT.value, 2);
      }
    },
    txtMaTT_KeyPress() {
      this.txtMaTT_Enter();
    },

    cboDoituongTB_KeyPress() {
      if (Number(cboKieuLD.value) == KieuLapDat.TACH_THUEBAO) {
        txtTenTT.focus();
      } else {
        txtMaKH.focus();
      }
    },

    txtTenTB_KeyPress() {
      if (e.KeyChar == Convert.ToChar(Keys.Enter)) {
        SendKeys.Send("{TAB}");
        //btnDiaChiTB_Click();
      }
    },

    txtTenKH_KeyPress() {
      //if (e.KeyChar == Convert.ToChar(Keys.Enter))
      //{
      //    SendKeys.Send("{TAB}");
      //    //btnDiaChiKH_Click();
      //}
    },

    txtTenTT_KeyPress() {
      this.btnDiaChiKH_Click();
    },
    //endregion

    //region Dia chi HD khach hang
    //endregion

    //region Dia chi HD thanh toán, chứng từ

    /// <summary>
    /// TuanNA: thêm hàm lấy mã nhân viên tc theo địa chỉ thanh toán vừa chọn
    /// Ngày 08.05.2012
    /// </summary>
    async HT_NVTC_TheoDC(quan_id, phuong_id, pho_id, ap_id, khu_id, donvi_ql_id, dacdiem_id) {
        return new Promise(async (resolve, reject) => {
          console.log("HT_NVTC_TheoDC")
          try {
            let ds = [];
            if(this.TUYENTHU_HTTT == 1 && this.$refs.cboHTTT.value) {
              let rs = await this.axios.post('/web-hopdong/thaydoithongtinkhuyenmai/ht_nvtc_theodc_v2', {
                phuong_id: phuong_id,
                pho_id: pho_id,
                ap_id: ap_id,
                khu_id: khu_id,
                donvi_ql_id: donvi_ql_id,
                dacdiem_id: dacdiem_id,
                httt_id: this.$refs.cboHTTT.value
              });
              ds = rs.data.data;
            }
            else {
              let rs = await this.axios.post('/web-hopdong/thaydoithongtinkhuyenmai/ht_nvtc_theodc', {
                phuong_id: phuong_id,
                pho_id: pho_id,
                ap_id: ap_id,
                khu_id: khu_id,
                donvi_ql_id: donvi_ql_id,
                dacdiem_id: dacdiem_id,
              });
              ds = rs.data.data;
            }
            if(ds.length > 1) {
              this.loaiPopupTraCuuNVTC = 1;
              this.objNVTC = {};
              this.objNVTC.QUAN_ID = quan_id;
              this.objNVTC.PHUONG_ID = phuong_id;
              this.objNVTC.PHO_ID = pho_id;
              this.objNVTC.KHU_ID = khu_id;
              this.objNVTC.AP_ID = ap_id;
              this.objNVTC.DACDIEM_ID = dacdiem_id;
              this.objNVTC.NHANVIENTC_ID = this.nhanvientc_id;
              this.objNVTC.TUYENTHU_ID = ds[0].tuyenthu_id;
              this.objNVTC.DONVIQL_ID = donvi_ql_id;

              if(this.TUYENTHU_HTTT == 1 && this.$refs.cboHTTT.value) {
                this.objNVTC.HTTT_ID = this.$refs.cboHTTT.value;
              }
              this.diachiLD = this.objNVTC;
              this.$bvModal.show('dlgTimKiemNVTC')
            }
            else if(ds.length > 0) {
              this.$refs.txtMaNVTC.value = ds[0].manv_tc;
              if(ds[0].tuyenthu_id) {
                this.tuyenthu_id = ds[0].tuyenthu_id;
              }
              this.$refs.txtMaTuyen.value = ds[0].ma_tuyen;
              this.nhanvientc_id = ds[0].nhanvien_id;
            }
            else {
              this.$refs.txtMaNVTC.value = "";
              this.$refs.txtMaTuyen.value = "";
              this.tuyenthu_id = 0;
              this.nhanvientc_id = 0;
              this.$refs.txtMaNVTC.focus()
            }
          } catch (error) {
            console.log(error)
          }
          resolve(true)
        })
      },
    //endregion

    //region Địa chỉ HD thuê bao, lắp đặt

    btnDiaChiTB_Click() {
      this.dataDiaChi.tinh_id = this.tinhtb_id;
      this.dataDiaChi.quan_id = this.quantb_id;
      this.dataDiaChi.phuong_id = this.phuongtb_id;
      this.dataDiaChi.pho_id = this.photb_id;
      this.dataDiaChi.ap_id = this.aptb_id;
      this.dataDiaChi.khu_id = this.khutb_id;
      this.dataDiaChi.dacdiem_id = this.dacdiemtb_id;
      this.dataDiaChi.sonha = this.sonhatb;
      this.dataDiaChi.block = this.blocktb;
      this.dataDiaChi.tang = this.tangtb;
      this.dataDiaChi.sophong = this.sophongtb;
      this.dataDiaChi.motathem = this.motadctb;
      this.dataDiaChi.diachi = this.$refs.txtDiaChiTB.value;
      this.dataDiaChi.type = "btnDiaChiTB"
      this.$refs.diaChiModal.showModal()

    },
    btnDiaChiBC_Click() {
      this.dataDiaChi.tinh_id = this.tinhbc_id;
      this.dataDiaChi.quan_id = this.quanbc_id;
      this.dataDiaChi.phuong_id = this.phuongbc_id;
      this.dataDiaChi.pho_id = this.phobc_id;
      this.dataDiaChi.ap_id = this.apbc_id;
      this.dataDiaChi.khu_id = this.khubc_id;
      this.dataDiaChi.dacdiem_id = this.dacdiembc_id;
      this.dataDiaChi.sonha = this.sonhabc ? this.sonhabc : '';
      this.dataDiaChi.block = "";
      this.dataDiaChi.tang = "";
      this.dataDiaChi.sophong = "";
      this.dataDiaChi.motathem = "";
      this.dataDiaChi.diachi = this.$refs.txtDiaChiBC.value;
      this.dataDiaChi.type = "btnDiaChiBC"
      this.$refs.diaChiModal.showModal()

    },
    btnDiaChiCT_Click() {
      this.dataDiaChi.tinh_id = this.tinhct_id;
      this.dataDiaChi.quan_id = this.quanct_id;
      this.dataDiaChi.phuong_id = this.phuongct_id;
      this.dataDiaChi.pho_id = this.phoct_id;
      this.dataDiaChi.ap_id = this.apct_id;
      this.dataDiaChi.khu_id = this.khuct_id;
      this.dataDiaChi.dacdiem_id = this.dacdiemct_id;
      this.dataDiaChi.sonha = this.sonhact;
      this.dataDiaChi.block = "";
      this.dataDiaChi.tang = "";
      this.dataDiaChi.sophong = "";
      this.dataDiaChi.motathem = "";
      this.dataDiaChi.diachi = this.$refs.txtDiachiCT.value;
      this.dataDiaChi.type = "btnDiaChiCT"
      this.$refs.diaChiModal.showModal()

    },
    btnDiaChiTT_Click() {
      this.dataDiaChi.tinh_id = this.tinhtt_id;
      this.dataDiaChi.quan_id = this.quantt_id;
      this.dataDiaChi.phuong_id = this.phuongtt_id;
      this.dataDiaChi.pho_id = this.phott_id;
      this.dataDiaChi.ap_id = this.aptt_id;
      this.dataDiaChi.khu_id = this.khutt_id;
      this.dataDiaChi.dacdiem_id = this.dacdiemtt_id;
      this.dataDiaChi.sonha = this.sonhatt;
      this.dataDiaChi.block = "";
      this.dataDiaChi.tang = "";
      this.dataDiaChi.sophong = "";
      this.dataDiaChi.motathem = "";
      this.dataDiaChi.diachi = this.$refs.txtDiaChiTT.value;
      this.dataDiaChi.type = "btnDiaChiTT"
      this.$refs.diaChiModal.showModal()

    },
    btnDiaChiKH_Click() {
      this.dataDiaChi.tinh_id = this.tinhkh_id;
      this.dataDiaChi.quan_id = this.quankh_id;
      this.dataDiaChi.phuong_id = this.phuongkh_id;
      this.dataDiaChi.pho_id = this.phokh_id;
      this.dataDiaChi.ap_id = this.apkh_id;
      this.dataDiaChi.khu_id = this.khukh_id;
      this.dataDiaChi.dacdiem_id = this.dacdiemkh_id;
      this.dataDiaChi.sonha = this.sonhakh;
      this.dataDiaChi.block = "";
      this.dataDiaChi.tang = "";
      this.dataDiaChi.sophong = "";
      this.dataDiaChi.motathem = "";
      this.dataDiaChi.diachi = this.$refs.txtDiaChiKH.value;
      this.dataDiaChi.type = "btnDiaChiKH"
      this.$refs.diaChiModal.showModal()

    },
    btnDiaChiLD_Click() {
      this.dataDiaChi.tinh_id = this.tinhld_id;
      this.dataDiaChi.quan_id = this.quanld_id;
      this.dataDiaChi.phuong_id = this.phuongld_id;
      this.dataDiaChi.pho_id = this.phold_id;
      this.dataDiaChi.ap_id = this.apld_id;
      this.dataDiaChi.khu_id = this.khuld_id;
      this.dataDiaChi.dacdiem_id = this.dacdiemld_id;
      this.dataDiaChi.sonha = this.sonhald;
      this.dataDiaChi.block = this.blockld;
      this.dataDiaChi.tang = this.tangld;
      this.dataDiaChi.sophong = this.sophongld;
      this.dataDiaChi.motathem = this.motadcld;
      this.dataDiaChi.diachi = this.$refs.txtDiaChiLD.value;
      this.dataDiaChi.type = "btnDiaChiLD"
      this.$refs.diaChiModal.showModal()

    },
    async onAcceptDiaChi(obj) {
      if (this.dataDiaChi.type == "btnDiaChiTB") {
        this.tinhtb_id = Number(obj.tinh_id);
        this.quantb_id = Number(obj.quan_id);
        this.phuongtb_id = Number(obj.phuong_id);
        this.photb_id = Number(obj.pho_id);
        this.aptb_id = Number(obj.ap_id);
        this.khutb_id = Number(obj.khu_id);
        this.dacdiemtb_id = Number(obj.dacdiem_id);
        this.sonhatb = obj.sonha;
        this.blocktb = obj.block;
        this.tangtb = obj.tang;
        this.sophongtb = obj.sophong;
        this.motadctb = obj.motathem;
        this.$refs.txtDiaChiTB.value = obj.diachi;
        this.$refs.txtDiaChiTB.focus();
      }
      if (this.dataDiaChi.type == "btnDiaChiLD") {
        this.tinhld_id = Number(obj.tinh_id);
        this.quanld_id = Number(obj.quan_id);
        this.phuongld_id = Number(obj.phuong_id);
        this.phold_id = Number(obj.pho_id);
        this.apld_id = Number(obj.ap_id);
        this.khuld_id = Number(obj.khu_id);
        this.dacdiemld_id = Number(obj.dacdiem_id);
        this.sonhald = obj.sonha;
        this.blockld = obj.block;
        this.tangld = obj.tang;
        this.sophongld = obj.sophong;
        this.motadcld = obj.motathem;
        this.$refs.txtDiaChiLD.value = obj.diachi;
        this.$refs.txtDiaChiLD.focus();
      }
      if (this.dataDiaChi.type == "btnDiaChiKH") {
        this.tinhkh_id = Number(obj.tinh_id);
        this.quankh_id = Number(obj.quan_id);
        this.phuongkh_id = Number(obj.phuong_id);
        this.phokh_id = Number(obj.pho_id);
        this.apkh_id = Number(obj.ap_id);
        this.khukh_id = Number(obj.khu_id);
        this.dacdiemkh_id = Number(obj.dacdiem_id);
        this.sonhakh = obj.sonha;

        this.$refs.txtDiaChiKH.value = obj.diachi;
        this.$refs.txtDiaChiKH.focus();
      }
      if (this.dataDiaChi.type == "btnDiaChiTT") {
        let vloaikh_id = 0
        if(this.loaikh_id)
          vloaikh_id = this.loaikh_id
        let ds = []
        //ds = laphd.LAY_DS_DONVIQL(obj.phuong_id, obj.pho_id, obj.ap_id, obj.khu_id, 1);
        if (this.DONVIQL_HTTT == 1) {
          rs = await this.axios.post("/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql_v2",
            { "phuong_id": obj.phuong_id, "pho_id": obj.pho_id, "ap_id": obj.ap_id, "khu_id": obj.khu_id, "kieu": 1, "loaikh_id": vloaikh_id, "httt_id": this.$refs.cboHTTT.value }
          )

          ds = rs.data.data
        }
        else {
          if (this.phantap_kh) {
            rs = await this.axios.post("/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql_ptkh_v2",
              { "phuong_id": obj.phuong_id, "pho_id": obj.pho_id, "ap_id": obj.ap_id, "khu_id": obj.khu_id, "kieu": 1, "loaikh_id": vloaikh_id, "httt_id": 104, "dacdiem_id": obj.dacdiem_id, "nganhnghe_id": this.vnganhnghe_id, "doanhthu": this.pt_doanhthu, "diemtn": this.pt_diem_tn })
            ds = rs.data.data
          }
          else {
            rs = await this.axios.post("/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql",
              { "phuong_id": obj.phuong_id, "pho_id": obj.pho_id, "ap_id": obj.ap_id, "khu_id": obj.khu_id, "kieu": 1, "loaikh_id": vloaikh_id })

            ds = rs.data.data
          }
        }
        if (!ds || ds.length <= 0) {
          if (this.$refs.cboDonViQL.dataSource.length != 1) {
            this.$toast.error("Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý!");
            this.$refs.cboDonViQL.value = null;
            return;
          }
        }
        else if (ds.length == 1) {
          this.$refs.cboDonViQL.value = ds[0]["donviql_id"] ? ds[0]["donviql_id"] : ds[0]["id"];
        }
        else {
          this.$refs.popupChonDonVi.dsDVQL = ds
          this.$refs.popupChonDonVi.dvChon = this.$refs.cboDonViQL.value
          this.$refs.popupChonDonVi.openModal()
          // let resolvePromise = await this.$refs.popupChonDonVi.show(null, null, this.$refs.cboDonViQL.value, null, ds);
          // if (resolvePromise.ok) {
          //   this.$refs.cboDonViQL.value = resolvePromise.data.donviql_id;
          // } else return
        }
        this.tinhtt_id = obj.tinh_id;
        this.quantt_id = obj.quan_id;
        this.phuongtt_id = obj.phuong_id;
        this.phott_id = obj.pho_id;
        this.aptt_id = obj.ap_id;
        this.khutt_id = obj.khu_id;
        this.dacdiemtt_id = obj.dacdiem_id;
        this.sonhatt = obj.sonha;
        this.$refs.txtDiaChiTT.value = obj.diachi;
        if (!this.$refs.txtDiaChiBC.value) {
          this.$refs.txtMaNVTC.value = "";//Nếu chọn lại địa chỉ  thanh toán thì clear mã nhân viên thu cước - Hiếu 29.09.2010
          this.$refs.txtMaTuyen.value = "";
          ///TuanNA: Lấy mã NVTC theo địa chỉ tt
          ///Ngày 07.12.2012                  
          this.HT_NVTC_TheoDC(this.quantt_id, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.$refs.cboDonViQL.value, this.dacdiemtt_id);

          if (this.DONVIQL_HTTT == 1)
            this.HienThi_NVQL_AM_HTTT(vloaikh_id, Loai_NV.NHANVIEN_KINHDOANH, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, this.$refs.cboDonViQL.value, this.$refs.cboHTTT.value);
          else {
            if (!this.phantap_kh) {
              this.HienThi_NVQL_AM_PTKH(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.dacdiemtt_id, vloaikh_id,
                this.vnganhnghe_id,
                this.pt_doanhthu, this.pt_diem_tn, Loai_NV.NHANVIEN_AM, this.$refs.cboDonViQL.value);
            }
            else
              this.HienThi_NVQL_AM(vloaikh_id, Loai_NV.NHANVIEN_KINHDOANH, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, this.$refs.cboDonViQL.value);
          }
          ///
        }


        if (!this.$refs.txtDiachiCT.value) {
          this.tinhct_id = this.tinhtt_id;
          this.quanct_id = this.quantt_id;
          this.phuongct_id = this.phuongtt_id;
          this.phoct_id = this.phott_id;
          this.apct_id = this.aptt_id;
          this.khuct_id = this.khutt_id;
          this.dacdiemct_id = this.dacdiemtt_id;
          this.sonhact = this.sonhatt;
          this.$refs.txtDiachiCT.value = this.$refs.txtDiaChiTT.value;
        }

        this.$refs.txtDiaChiTT.focus();
      }
      if (this.dataDiaChi.type == "btnDiaChiBC") {
        console.log(obj)
        let ds = []
        let vloaikh_id = 0
        if(this.loaikh_id)
          vloaikh_id = this.loaikh_id
        //ds = laphd.LAY_DS_DONVIQL(obj.phuong_id, obj.pho_id, obj.ap_id, obj.khu_id, 1);
        if (this.DONVIQL_HTTT == 1) {
          rs = await this.axios.post("/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql_v2",
            { "phuong_id": obj.phuong_id, "pho_id": obj.pho_id, "ap_id": obj.ap_id, "khu_id": obj.khu_id, "kieu": 1, "loaikh_id": vloaikh_id, "httt_id": this.$refs.cboHTTT.value }
          )

          ds = rs.data.data
        }
        else {
          if (this.phantap_kh) {
            rs = await this.axios.post("/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql_ptkh_v2",
              { "phuong_id": obj.phuong_id, "pho_id": obj.pho_id, "ap_id": obj.ap_id, "khu_id": obj.khu_id, "kieu": 1, "loaikh_id": vloaikh_id, "httt_id": 104, "dacdiem_id": obj.dacdiem_id, "nganhnghe_id": this.vnganhnghe_id, "doanhthu": this.pt_doanhthu, "diemtn": this.pt_diem_tn })
            ds = rs.data.data
          }
          else {
            rs = await this.axios.post("/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql",
              { "phuong_id": obj.phuong_id, "pho_id": obj.pho_id, "ap_id": obj.ap_id, "khu_id": obj.khu_id, "kieu": 1, "loaikh_id": vloaikh_id })

            ds = rs.data.data
          }
        }
        if (!ds || ds.length <= 0) {
          if (this.$refs.cboDonViQL.dataSource.length != 1) {
            this.$toast.error("Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý!");
            this.$refs.cboDonViQL.value = null;
            return;
          }
        }
        else if (ds.length == 1) {
          this.$refs.cboDonViQL.value = ds[0]["donvi_id"];
        }
        else {
          this.$refs.popupChonDonVi.dsDVQL = ds
          this.$refs.popupChonDonVi.dvChon = this.$refs.cboDonViQL.value
          this.$refs.popupChonDonVi.openModal()
          // let resolvePromise = await this.$refs.popupChonDonVi.show(null, null, this.$refs.cboDonViQL.value, null, ds);
          // if (resolvePromise.ok) {
          //   this.$refs.cboDonViQL.value = resolvePromise.data.donviql_id;
          // } else return
        }
        
        if (!this.$refs.txtDiaChiBC.value) {
          this.$refs.txtMaNVTC.value = "";//Nếu chọn lại địa chỉ  thanh toán thì clear mã nhân viên thu cước - Hiếu 29.09.2010
          this.$refs.txtMaTuyen.value = "";
          ///TuanNA: Lấy mã NVTC theo địa chỉ tt
          ///Ngày 07.12.2012                  
          this.HT_NVTC_TheoDC(obj.quan_id, obj.phuong_id, obj.phott_id, obj.ap_id, obj.khu_id, this.$refs.cboDonViQL.value, obj.dacdiem_id);

          if (this.DONVIQL_HTTT == 1)
            this.HienThi_NVQL_AM_HTTT(vloaikh_id, Loai_NV.NHANVIEN_KINHDOANH, obj.phuong_id, obj.pho_id, obj.ap_id, obj.khu_id, obj.nhanvientc_id, this.$refs.cboDonViQL.value, this.$refs.cboHTTT.value);
          else {
            if (this.phantap_kh) {
              this.HienThi_NVQL_AM_PTKH(obj.phuong_id, obj.pho_id, obj.ap_id, obj.khu_id, obj.dacdiem_id, vloaikh_id,
                this.vnganhnghe_id,
                this.pt_doanhthu, this.pt_diem_tn, Loai_NV.NHANVIEN_AM, this.$refs.cboDonViQL.value);
            }
            else
              this.HienThi_NVQL_AM(vloaikh_id, Loai_NV.NHANVIEN_KINHDOANH, obj.phuong_id, obj.pho_id, obj.ap_id, obj.khu_id, obj.nhanvientc_id, this.$refs.cboDonViQL.value);
          }
          ///
        }
        this.tinhbc_id = obj.tinh_id;
        this.quanbc_id = obj.quan_id;
        this.phuongbc_id = obj.phuong_id;
        this.phobc_id = obj.pho_id;
        this.apbc_id = obj.ap_id;
        this.khubc_id = obj.khu_id;
        this.dacdiembc_id = obj.dacdiem_id;
        this.sonhabc = obj.sonha;
        this.$refs.txtDiaChiBC.value = obj.diachi;
        this.$refs.txtDiaChiBC.focus();
      }
      if (this.dataDiaChi.type == "btnDiaChiCT") {
        this.tinhct_id = Number(obj.tinh_id);
        this.quanct_id = Number(obj.quan_id);
        this.phuongct_id = Number(obj.phuong_id);
        this.phoct_id = Number(obj.pho_id);
        this.apct_id = Number(obj.ap_id);
        this.khuct_id = Number(obj.khu_id);
        this.dacdiemct_id = Number(obj.dacdiem_id);
        this.sonhact = obj.sonha;

        this.$refs.txtDiaChiCT.value = obj.diachi;
        this.$refs.txtDiaChiCT.focus();
      }
    },
    button2_Click() {
      this.btnDiaChiLD_Click();
    },

    //endregion

    //region lstbxDSMaTB_SelectedIndexChanged
    ///TuấnNA comment code để thay thế listview danh sách thuê bao cho lstbxDSMaTB // Ngày 25/08/2010
    //,lstbxDSMaTB_SelectedIndexChanged()
    //{
    //    if (lstbxDSMaTB.dataSource.length > 0)
    //    {
    //        if (lstbxDSMaTB.value != null)
    //        {
    //            if (lstbxDSMaTB.value == "") return;
    //            hdtb_id = Number(lstbxDSMaTB.value);
    //            HienThiTTHopDongTB(tchopdong.LAY_DS_HOPDONG_TB_THEO_HDTB_ID(hdtb_id));

    //            if (lvwThanhToan.dataSource.length > 0)
    //            {
    //                hdtt_id = Number(cboTT.value);
    //                HienThiTTHopDongTT(tchopdong.LAY_DS_HOPDONG_TT_THEO_HDTT_ID(hdtt_id));
    //            }
    //        }
    //    }
    //}
    //endregion

    //region HienThiTT_DanhBa_KH_NhanTB
    async HienThiTT_DanhBa_KH_NhanTB(ma_tb, kieu) {
      try {
        ///TuấnNA sửa code lấy thông tin thanh toán theo mã thanh toán -- Ngay 05/08/2010
        let ds = [];

        if (kieu == 2) {
          rs = await this.axios.post(
            "web-hopdong/tachnhapthuebao/lay_thongtin_kh_theo_ma_tt_v2",
            { ma_tt: ma_tb, kieu: 2, loaihd_id: 10 }
          );
          ds = rs.data.data;
        } else {
          rs = await this.axios.post(
            "web-hopdong/tachnhapthuebao/lay_tt_kh_theo_matb",
            { ma_tb: ma_tb, donvi_dl_id: 0 }
          );
          ds = rs.data ? rs.data.data : [];
        }

        if (ds.length <= 0) {
          rs = await this.axios.post("web-hopdong/lapdatmoi/lay_db_theo_matb", {
            "dichvuvt_id": 0,
            "donvi_dl_id": 0,
            "ma_tb": ma_tb,
            "phanvung_id": this.$root.token.getPhanVungID()
          })
          ds = rs.data.data
        }
        ///TuấnNA thêm code kiểm tra khách hàng nhận thuê bao và khách hàng đang thực hiện tách ghép có phải là một hay không?
        ///Ngày 05/08/2010
        if (ds && ds.length > 0) {
          if (this.ma_kh_cu != "") {
            if (ds[0]["ma_kh"] != this.ma_kh_cu) {
              this.$toast.error(
                "Khách hàng nhận thuê bao tách ghép và khách hàng yêu cầu tách ghép không giống nhau !"
              );
              this.HienThiTT_DanhBa(ma_tb_cu);
              this.$refs.txtMaTT.value = "";
              this.$refs.txtMaKH.focus();
            } else {
              /// TuấnNA bổ sung code kiểm tra dữ liệu vào biến mã thanh toán -- Ngày 05/08/2010
              this.ma_tt = ds[0]["ma_tt"];
              if (this.ma_tt_cu == this.ma_tt) {
                this.$toast.error(
                  "Bạn đang thực hiện chức năng Ghép thuê bao\nMã thanh toán mới: " +
                  this.ma_tt +
                  " phải khác mã thuê bao cũ: " +
                  this.ma_tt_cu +
                  "\nĐề nghị kiểm tra lại!"
                );
                this.$refs.txtMaTT.value = "";
                return;
              } else {
                //Thông tin khách hàng
                this.$refs.txtMaKH.value = ds[0]["ma_kh"];
                this.khachhang_id = Number(ds[0]["khachhang_id"]);
                this.$refs.txtTenKH.value = ds[0]["ten_kh"];
                this.$refs.txtDiaChiKH.value = ds[0]["diachi_kh"];
                rs = await this.axios.get("web-hopdong/hopdong/lay_ds_diachi_theo_khachhang", {
                  params: {
                    khachHangId: this.khachhang_id
                  }
                })
                this.HienThiTTDiaChi(
                  rs.data.data,
                  1
                );

                //Thông tin thanh toán
                this.$refs.txtMaTT.value = ds[0]["ma_tt"];
                this.thanhtoan_id = Number(ds[0]["thanhtoan_id"]);
                rs = await this.axios.get(
                  "web-hopdong/hopdong/lay_ds_ma_thuebao_daidien",
                  {
                    params: {
                      thanhtoanId: this.thanhtoan_id,
                    },
                  }
                );
                let dt_madd = rs.data.data;
                this.$refs.cboMaDD.dataSource = dt_madd
                //
                this.$refs.cboMaDD.value = ds[0]["matb_dd"];

                this.$refs.txtTenTT.value = ds[0]["ten_tt"];
                this.$refs.txtDiaChiTT.value = ds[0]["diachi_tt"];
                this.$refs.txtDiaChiBC.value = ds[0]["diachi_bc"];
                this.$refs.txtMaTBDD.value = ds[0]["matb_dd"];
                rs = await this.axios.get(
                  "web-hopdong/thaydoithongtinkhuyenmai/lay_ds_diachi_theo_dbttid",
                  {
                    params: {
                      dbttid: this.thanhtoan_id,
                    },
                  }
                );
                this.HienThiTTDiaChi(rs.data.data, 2);
                this.$refs.txtMaIG.value = ds[0]["main_ghep"];
                if (ds[0]["mig_id"] != "") {
                  this.mig_id = Number(ds[0]["mig_id"]);
                }
                //TuấnNA thêm code hiển thị thông tin NSNN trong bảng HDTT // Ngày 12/11/2010
                this.DV_QHNS = ds[0]["dv_qhns"];
                this.MACHUONG = ds[0]["machuong"];
                this.MN_KT = ds[0]["mn_kt"];
                this.MA_NDKT = ds[0]["ma_ndkt"];
                this.MN_NSNN = ds[0]["mn_nsnn"];
                if (ds[0]["tien_dm"] != "")
                  this.TIEN_DM = Number(ds[0]["tien_dm"]);
                else this.TIEN_DM = 0;
                //

                ///TuấnNA thêm code lấy đơn vị quản lý cũ ứng với mã thanh toán khách hàng đang sử dụng
                ///// Ngày 01/09/2010
                //donvi_qlcu = LayDonVi_QL(phuongtt_id,phott_id);

                this.$refs.txtMST_TT.value = ds[0]["mst_tt"];
                this.$refs.txtSTK_TT.value = ds[0]["stk_tt"];
                if (ds[0]["nganhang_id"] != "")
                  this.$refs.cboNganHang.value = ds[0]["nganhang_id"];
                this.$refs.cboHTTT.value = ds[0]["httt_id"];
                this.donviql_id = ds[0]["donvi_id"];
                this.$refs.cboDonViQL.value = Number(ds[0]["donvi_id"]);
                if (ds[0]["nhanvien_id"] != "") {
                  this.nhanvientc_id = Number(ds[0]["nhanvien_id"]);
                  rs = await this.axios.post(
                    "/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien",{ param: this.nhanvientc_id, type: 4 }
                  );
                  this.$refs.txtMaNVTC.value = rs.data.data;
                } else {
                  this.nhanvientc_id = 0;
                  this.$refs.txtMaNVTC.value = "";
                }

                if (ds[0]["tuyenthu_id"] != "") {
                  this.tuyenthu_id = Number(ds[0]["tuyenthu_id"]);
                  rs = await this.axios.post("/web-hopdong/thanhly/thanh_ly_map_id", 
                  { id_neo: 'ma_tuyen', in_table: 'css.tuyenthu', in_dk: "where tuyenthu_id =" + this.tuyenthu_id }
                  )
                  this.$refs.txtMaTuyen.value = rs.data.data
                } else {
                  this.tuyenthu_id = 0;
                  this.$refs.txtMaTuyen.value = "";
                }

                ///TuấnNA bổ xung các thông tin thanh toán lên form -- Ngày 30/07/2010
                this.$refs.txtDiachiCT.value = ds[0]["diachi_ct"];

                this.$refs.txtGhiChu.value = ds[0]["ghichu"];
                if (ds[0]["nganhang_id"] != "")
                  this.$refs.cboNganHang.value = ds[0]["nganhang_id"];
                if (ds[0]["ngaylap_hd"] != "")
                  this.$refs.dtpNgayLapHD.value = ds[0]["ngaylap_hd"];
                this.HienThi_NVQL_AM_DB_HD(1, this.thanhtoan_id);
              }
            }
          } else this.$toast.error("Chưa có thông tin thuê bao tách ghép!");
        } else {
          this.$refs.txtMaTT.value = "";
          this.$toast.error("Không tìm thấy thông tin thanh toán!");
        }
      } catch (err) {
        console.log(err)
        this.$toast.error("Có lỗi xảy ra !\n" + err.message);
      }
    },
    //endregion

    //region btnLayMaGD_Click
    btnLayMaGD_Click() {
      this.$refs.searchContractModal.clearData();
      this.$refs.searchContractModal.loadPopup();
      this.$refs.popupFrmTraCuuHopDong.show();
    },
    async acceptSearchContract(item) {
      console.log(item)
      this.$refs.popupFrmTraCuuHopDong.hide();
      if(item.hopdong && item.hopdong.ma_gd) {
        this.$refs.txtMaGD.value = item.hopdong.ma_gd;
        if (this.$refs.txtMaGD.value) {
          this.txtMaGD_KeyPress()
        }
      }

    },
    //endregion

    //region bntLayMaKh_Click
    bntLayMaKh_Click() {
      let f = new frmTraCuuDanhBa(0, 1);
      f.ShowDialog();
      if (f.this.chapnhan) {
        txtMaKH.value = f.ma_kh;
        txtMaKH.focus();
      }
    },
    //endregion

    //region btnLayMaTT_Click
    btnLayMaTT_Click() {
      try {

        if (Number(this.$refs.cboKieuLD.value) == KieuLapDat.TACH_THUEBAO) {
          this.$toast.error(
            "Bạn đang chọn kiểu yêu cầu là: " +
            "Tách thuê bao" +
            "\nMã thanh toán mới sẽ tự sinh! Bạn không được chọn!"
          );
        } else {
          ///TuấnNA sửa code tìm kiếm mã thanh toán của cùng 1 khách hàng -- Ngày 05/07/2010
          this.popupComponentData = this.khachhang_id;
          this.popupComponent = () => import('../InstallNewSubs/Popups/popupChonMaTT');
          this.popupComponentName = "popupChonMaTT";
          this.Popup('popupComponents');
          // let frm = new frmChonMaTT();
          // frm.Khachhang_id = khachhang_id;
          // frm.MaTT_Remove = ma_tt_cu;
          // frm.ShowDialog();
          // if (frm.this.chapnhan) {
          //   txtMaTT.value = frm.MaThanhToan;
          //   txtMaTT_Enter(); ///TuấnNA thêm code chọn mã thanh toán -->enter // Ngày 25/08/2010
          //   txtMaTT.focus();
          // }
        }
      } catch (err) {
        this.$toast.error("Có lỗi xảy ra !\n" + err.message);
      }
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    //endregion

    //region InHopDong
    tsbtnInhopdong_Click() {
      //InHopDong obj = new InHopDong();
      //obj.InHopDongBienDong(khachhang_id, this.$refs.dtpNgayLapHD.value);
    },
    //endregion

    //region this.$refs.tsbtnGiaoPhieu_Click
    tsbtnGiaoPhieu_Click() {
      this.$toast.error("Chức năng hiện tại không khả dụng!")
      //frmGiaoPhieu f = new frmGiaoPhieu(txtMaGD.value, 1, HuongGiao.HOANTHIEN_HS, hdtb_id, 0, "0");
      //f.ShowDialog();
      //if (txtMaGD.value != "")
      //{
      //    HienThiTTHopDongKH(tchopdong.LAY_DS_HOPDONG_THEO_MA_GD(txtMaGD.value, LoaiHopDong.TACHNHAP_TB, this.tt_nd.donvi_id, TrangThaiHD.MOI, this.tt_nd.nhanvien_id, 0));
      //}
    },
    //endregion

    //,txtSoLQ_Leave()
    //{
    //    if (txtDiaChiTT.value == "")
    //    {
    //        this.$toast.error("Hãy chọn địa chỉ thanh toán trước!");
    //        btnDiaChiTT.focus();
    //        return;
    //    }
    //    if (!string.IsNullOrEmpty(txtSoLQ.value.trim()))
    //    {
    //        DataSet ds_nvtc = []
    //        ds_nvtc = tcdanhba.LAY_MANV_TC_THEO_SOLIENQUAN(txtSoLQ.value.trim(), donvi_ql);
    //        if (ds_nvtc.length > 0)
    //        {
    //            string manv_tc = "";
    //            manv_tc = ds_nvtc[0]["manv_tc"]
    //            tuyenthu_id = Number(ds_nvtc[0]["tuyenthu_id"]);
    //            txtMaTuyen.value = checkdata.MAP_ID("ma_tuyen", DatabaseConstants.DB2 + ".tuyenthu", "where tuyenthu_id =" + tuyenthu_id);
    //            txtMaNVTC.value = manv_tc;
    //        }
    //        else
    //        {
    //            this.$toast.error("Không tìm thấy thông tin số liên quan: " + txtSoLQ.value + ".Bạn hãy kiểm tra lại số liên quan!");
    //            txtSoLQ.focus();
    //            txtSoLQ.value = "";
    //        }
    //    }
    //}
    //TuanNA Nếu huyện khác văn lãng hoặc cao lộc thì cho phường id = 0 -- Ngày 27/07/2010
    //private string LayDonVi_QL(long phuong_id, long pho_id)
    //{
    //    string Result = "";
    //    Result = laphd.LAY_DONVIQL(phuong_id, pho_id)
    //    return Result;
    //}

    //region Hàm đồng bộ vinaphone

    //region Insert_HopDong_ChuyenQuyen_VinaPhone
    /// <summary>
    /// Created by HungHM - 15/07/2010
    /// Hàm thực hiện đẩy thông tin hợp đồng thuê bao lên VinaPhone.
    /// </summary>
    /// <returns>OK: thành công / //OK: thất bại vuth update 19/12</returns>
    //endregion

    //region Tracuu_HopDong_ChuyenQuyen_VinaPhone
    /// <summary>
    /// TuanNA them ham tra cuu hop dong chuyen quyen tren vinaphone
    /// Tracuu_HopDong_ChuyenQuyen_VinaPhone
    /// // kieu_tc = 0 -->tra cuu hop dong, kieu_tc = 1 --> tra cuu trong danh ba
    /// </summary>
    /// <param name="ma_tb"></param>
    /// <param name="kieu_tc"></param>
    /// <returns>
    ///
    /// </returns>
    //endregion

    async tsbtnThanhToan_Click() {
      try {
        // this.loading(true)
        
        if (this.hdkh_id == 0) {
          this.$toast.error("Hãy ghi lại hợp đồng trước khi thanh toán!");
          return;
        }
        rs = await this.axios.post("web-hopdong/tachnhapthuebao/kiemtra_thanhtoan_datmoi_lh",
          { "hdkh_id": this.hdkh_id })
        if (rs.data.data == "0") {
          this.$toast.error(
            "Hợp đồng chưa có thông tin liên hệ, bạn phải bổ sung thông tin liên hệ trước khi thanh toán !"
          );
          return;
        }
        this.userComponentModule = thanhToan
        // goi su kien load lai ma giao dich
        this.txtMaGDTT = this.$refs.txtMaGD.value
        // tao component neu chua tao, show popup khi da goi xong ham khoi tao
        if (this.paymentloaded) this.$refs.thanhtoanDialog.show(true)
      } catch (err) {
        console.log('tsbtnThanhToan_Click', err)
      } finally {
        // this.loading(false)
      }
    },
    async paymentcreated(val) {
      this.paymentloaded = true
      this.$refs.thanhtoanDialog.show(true)
    },
    //endregion

    tsbtnThemTT_Click() {
      this.ThemHDThanhToan();
    },

    //region Them HD thanh toán
    async ThemHDThanhToan() {
      try {
        if (!(await this.KiemTraDL_ThanhToan())) return;
        this.$refs.txtMaTT.value = "";
        await this.TaoDuLieu_HDTT(true);
        await this.TaoDuLieu_DiaChi(true, 2);
        // await this.axios.post("web-hopdong/tachnhapthuebao/sp_insert_hd_thanhtoan",
        //   { "js_hd_thanhtoan": JSON.stringify(this.dsHDTT) })

        // await this.axios.post("web-hopdong/tachnhapthuebao/sp_insert_diachi",
        //   { "js_diachi": JSON.stringify(this.dsDiaChi) })

        await this.axios.post("web-hopdong/dk_goi_dadichvu/fn_themtt_tachnhaptb",
        {
            "dsHDTT": JSON.stringify(this.dsHDTT), 
            "dsDiaChi": JSON.stringify(this.dsDiaChi), 
            "dsDiaChiHDTT": JSON.stringify(this.dsDiaChiHDTT)
        })

        //Them diachi bao cuoc
        this.diachibc_id = 0;
        this.CapNhat_DiaChiBC();
        //
        this.HienThiDS_HDTT_CBO(this.hdkh_id);
        rs = await this.axios.get(
          "web-hopdong/hopdong/lay_ds_hopdong_thanhtoan_theo_hdkh",
          {
            params: { hdKhId: this.hdkh_id },
          }
        );
        this.HienThiDanhSacHDTT(
          rs.data.data
        );
      } catch (err) {
        console.log(err);
      }
    },
    //endregion

    async tsbtnXoaTT_Click() {
      let kt = await this.axios.get(
        "web-hopdong/hopdong/kiemtra_hdtb_theo_hdtt",
        {
          params: {
            hdttId: this.hdtt_id,
          },
        }
      );

      if (kt.data && kt.data.data > 0) {
        this.$toast.error(
          "Thanh toán " +
          this.$refs.txtMaTT.value +
          " đã có thuê bao! Hãy xóa thuê bao trước."
        );
      } else {
        this.$confirm(`Bạn thật sự muốn xóa thanh toán không ?`, {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
        }).then(async () => {
          let rs = await this.axios.post(
            "web-hopdong/hopdong/xoa_hopdong_thanhtoan?hdttId=" +
            this.hdtt_id
          );

          this.$toast.success("Xóa thanh toán thành công!");

          this.HienThiDS_HDTT_CBO(this.hdkh_id);
          let dsHopdong = await this.axios.get(
            "web-hopdong/hopdong/lay_ds_hopdong_thanhtoan_theo_hdkh",
            { params: { hdKhId: this.hdkh_id } }
          );
          this.HienThiDanhSacHDTT(dsHopdong.data ? dsHopdong.data.data : []);
        });
      }
    },

    //region HienThiDanhSacHDTT
    HienThiDanhSacHDTT(ds) {
      this.$refs.lvwThanhToan.dataSource = [];
      let myDataTables = ds ? ds : [];
      if (myDataTables.length > 0) {
        for (let i = 0; i < myDataTables.length; i++) {
          let myRow = myDataTables[i];
          let lvItem = myRow;
          this.$refs.lvwThanhToan.dataSource.push(lvItem);
          if (this.$refs.lvwThanhToan.dataSource.length == 1)
            this.$refs.tsbtnXoaTT.classList.add("disabled");
          else this.$refs.tsbtnXoaTT.classList.remove("disabled");
        }
      }
    },

    async HienThiDS_HDTT_CBO(hdkh_id) {
      return new Promise(async (resolve, reject) => {
        rs = await this.axios.get(
          "web-hopdong/hopdong/lay_ds_hopdong_thanhtoan_theo_hdkh",
          {
            params: { hdKhId: hdkh_id },
          }
        );
        this.$refs.cboTT.dataSource = rs.data.data;
        this.$refs.cboTT.dataTextField = "ten_tt";
        this.$refs.cboTT.dataValueField = "hdtt_id";
        this.$refs.cboTT.SelectedIndex = 0;
        resolve(true)
      })
    },
    //endregion

    cboTT_SelectionChangeCommitted() {
      try {
        if (lvwThanhToan.dataSource.length > 0) {
          hdtt_id = Number(cboTT.value);
          HienThiTTHopDongTT(tchopdong.LAY_DS_HOPDONG_TT_THEO_HDTT_ID(hdtt_id));
        }
      } catch (err) {
        new LogError().GetError(
          new log4net.Core.LoggingEvent(
            System.Reflection.MethodBase.GetCurrentMethod().DeclaringType,
            log.Logger.Repository,
            log.Logger.Name,
            log4net.Core.Level.Info,
            ex.Message,
            ex
          ),
          true
        );
      }
    },

    async lvwThueBao_SelectedIndexChanged() {
      try {
        if (this.$refs.lvwThueBao.getSelectedRecords().length > 0) {
          this.hdtb_id = Number(this.$refs.lvwThueBao.getSelectedRecords()[0].hdtb_id);
          console.log(this.$refs.lvwThueBao.getSelectedRecords(), "hdtb_id")
          rs = await this.axios.post("web-hopdong/thaydoiloaihinhtb/lay_ds_hopdong_tb_theo_hdtb_id",
            {
              "hdtb_id": this.hdtb_id
            })
          this.HienThiTTHopDongTB(rs.data.data);
          if (this.$refs.lvwThanhToan.dataSource.length > 0) {
            this.hdtt_id = Number(this.$refs.cboTT.value);
            rs = await this.axios.post("web-hopdong/tachnhapthuebao/lay_ds_hopdong_tt_theo_hdtt_id",
              { "hdtt_id": this.hdtt_id })
            this.HienThiTTHopDongTT(
              rs.data.data
            );
          }
        }
      } catch (err) {
        console.log(err)
      }
    },

    async lvwThanhToan_SelectedIndexChanged() {
      try {
        if (this.$refs.lvwThanhToan.getSelectedRecords().length > 0) {
          this.hdtt_id = Number(this.$refs.lvwThanhToan.getSelectedRecords()[0].hdtt_id);
          rs = await this.axios.post("web-hopdong/tachnhapthuebao/lay_ds_hopdong_tt_theo_hdtt_id", {
            hdtt_id: this.hdtt_id
          })
          this.HienThiTTHopDongTT(rs.data.data);
        }
      } catch (err) {
        console.log(err)
      }
    },

    /// <summary>
    /// TuấnNA thêm hàm kiểm tra Mã NVTC ứng với maTT cũ
    /// // Ngày 07/10/2010
    /// </summary>
    /// <param name="matt"></param>
    KiemTra_MaNVTC_MaTT(matt, manvtc_kt) {
      let result = false;
      let ds = tcdanhba.LAY_DB_THEO_MATB(txtMaTT.value.trim(), 0, 0);
      if (ds.length > 0) {
        if (ds[0]["manv_tc"] != "") {
          if (ds[0]["manv_tc"] == manvtc_kt) {
            result = true;
          } else result = false;
        } else result = true;
      } else result = true;
      return result;
    },

    btnTraCuuNVTC_Click() {
      try {
        this.objNVTC.quan_id = this.quantt_id;
        this.objNVTC.phuong_id = this.phuongtt_id;
        this.objNVTC.pho_id = this.phott_id;
        this.objNVTC.ap_id = this.aptt_id;
        this.objNVTC.khu_id = this.khutt_id;
        this.objNVTC.dacdiem_id = this.dacdiemtt_id;
        this.objNVTC.tuyenthu_id = this.tuyenthu_id;
        this.objNVTC.nhanvientc_id = this.nhanvientc_id;
        this.objNVTC.kt_kylai = true;
        this.objNVTC.donviql_id = Number(this.$refs.cboDonViQL.value);
        // this.$refs.dlgTimKiemNVTC.openDialog();
        this.$bvModal.show("dlgTimKiemNVTC")
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi: " + err.message);
      }
    },

    chonNVTCHandler(obj) {
      if (
        this.quantt_id != 0 &&
        this.phuongtt_id != 0 &&
        (this.phott_id != 0 || this.khutt_id != 0 || this.aptt_id != 0)
      ) {
        console.log(obj)
        this.nhanvientc_id = obj.NHANVIEN_ID;
        this.$refs.txtMaNVTC.value = obj.MANV_TC;
        this.$refs.txtMaTuyen.value = obj.MA_TUYENTHU;
        this.tuyenthu_id = obj.TUYENTHU_ID;

        let vloaikh_id = 0;
        if (this.loaikh_id != "") vloaikh_id = Number(this.loaikh_id);
        if (this.DONVIQL_HTTT == 1)
          this.HienThi_NVQL_AM_HTTT(
            vloaikh_id,
            Loai_NV.NHANVIEN_KINHDOANH,
            this.phuongtt_id,
            this.phott_id,
            this.aptt_id,
            this.khutt_id,
            this.nhanvientc_id,
            Number(this.$refs.cboDonViQL.value),
            Number(this.$refs.cboHTTT.value)
          );
        else {
          if (!this.phantap_kh)
            this.HienThi_NVQL_AM(
              vloaikh_id,
              Loai_NV.NHANVIEN_KINHDOANH,
              this.phuongtt_id,
              this.phott_id,
              this.aptt_id,
              this.khutt_id,
              this.nhanvientc_id,
              Number(this.$refs.cboDonViQL.value)
            );
        }
      } else {
        this.$toast.error(
          "Chưa có thông tin về địa chỉ thanh toán\nBạn kiểm tra lại!"
        );
        this.nhanvientc_id = 0;
        this.$refs.txtMaNVTC.value = "";
        this.$refs.txtMaTuyen.value = "";
        this.$refs.txtMaNVTC.focus();
        this.$refs.txtNhanVienQL.value = "";
        this.nhanvien_ql_am_id = 0;
      }
    },

    txtTenTT_Leave() {
      txtTenTT.value = txtTenTT.value;
    },

    txtTenTB_Leave() {
      txtTenTB.value = txtTenTB.value;
    },

    txtGhiChu_Leave() {
      txtGhiChu.value = txtGhiChu.value;
    },

    txtMaTB_Leave() {
      //this.$refs.txtMaTB.value = this.$refs.txtMaTB.value.trim().toLowerCase();
      this.$refs.txtMaTB.value = Common.Utilities.CommonFunction.Catdaunhay(
        this.$refs.txtMaTB.value
      );
    },

    //,txtTienTNTB_Leave()
    //{
    //    if (txtTienTNTB.value.trim() != "")
    //    {
    //        string s = txtTienTNTB.value;
    //        s = Common.Utilities.CommonFunction.Unformat_tien(s);
    //        //if (!IsNumeric(Common.Utilities.CommonFunction.Unformat_tien(s)))
    //        if (!Common.Utilities.CommonFunction.CheckIsNumber(s))
    //        {
    //            this.$toast.error("Dữ liệu nhập phải là kiểu số");
    //            txtTienTNTB.focus();
    //            return;
    //        }
    //        txtTienTNTB.value = parseFloat(s);
    //    }
    //}

    //,txtVatTNTB_Leave()
    //{
    //    if (txtVatTNTB.value.trim() != "")
    //    {
    //        string s = txtVatTNTB.value;
    //        s = Common.Utilities.CommonFunction.Unformat_tien(s);
    //        //if (!IsNumeric(Common.Utilities.CommonFunction.Unformat_tien(s)))
    //        if (!Common.Utilities.CommonFunction.CheckIsNumber(s))
    //        {
    //            this.$toast.error("Dữ liệu nhập phải là kiểu số");
    //            txtVatTNTB.focus();
    //            return;
    //        }
    //        txtVatTNTB.value = parseFloat(s);
    //    }
    //}

    //region LayTienTheoKhoanMuc
    //Hiếu thêm hàm hiển thị tiền lắp đặt lên text box 14/09/2010
    async LayTienTheoKhoanMuc() {
      try {
        //Hiếu TC bổ sung hàm lấy tiền lên ô text box ngày 14/09/2010
        if (
          this.dichvuvt_id != DichVuVienThong.ADSL &&
          this.dichvuvt_id != DichVuVienThong.MEGAWAN
        ) {
          this.kh_cd = 0;
          this.ltb_adsl_id = 1;
        }
        let ds = [];
        if (
          this.$refs.cboLoaihinhTB.value == "" ||
          this.$refs.cboKieuLD.value == ""
        ) {
          return;
        }

        rs = await this.axios.post(
          "web-hopdong/khoiphucthanhly/lay_tien_khoanmuc_ld",
          {
            loaihd_id: LoaiHopDong.TACHNHAP_TB,
            ngay_yc: this.$refs.dtpNgayYC.value.split(" ")[0],
            dichvuvt_id: this.$refs.cboDichVuVT.value
              ? this.$refs.cboDichVuVT.value
              : 0,
            loaitb_id: this.$refs.cboLoaihinhTB.value,
            kieuld_id: this.$refs.cboKieuLD.value,
            phuongld_id: this.phuongld_id,
            tocdo_id: 0,
            muccuoc_id: 0,
            ltb_adsl_id: this.ltb_adsl_id,
            ltb_adsl_moi_id: 0,
            ltb_adsl_cu_id: 0,
            tocdo_moi_id: 0,
            tocdo_cu_id: 0,
            kh_cd: 0,
            phold_id: this.phold_id,
            apld_id: this.apld_id,
            khuld_id: this.khuld_id,
            doituong_id: this.doituong_id,
            trangbi_id: this.trangbi_id,
          }
        );

        ds = rs.data.data;
        if (ds && ds.length > 0) {
          this.HienThiTienLapDat(ds);
        }
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi xảy ra!");
      }
      //
    },
    //endregion

    //region HienThiTienLapDat
    HienThiTienLapDat(ds) {
      let kt_cd = true;
      let kt_lm = true;
      if (ds[0][0] != "-1") {
        for (let i = 0; i < ds.length; i++) {
          if (ds[i]["khoanmuctt_id"].trim() == KHOANMUC_TT.KMTT_TNTHUEBAO) {
            // txtTienTNTB.value = parseFloat(ds[0]["tien"]);
            //txtVatTNTB.value = parseFloat(ds[0]["vat"]);
            this.dtientn = parseFloat(ds[0]["tien"]); //;
            this.dvattn = parseFloat(ds[0]["vat"]); //;
            this.kt_lm = false;
          }
        }
        if (kt_lm == true) {
          //txtTienTNTB.value = "0";
          //txtVatTNTB.value = "0";
          this.dtientn = 0;
          this.dvattn = 0;
        }
      } else {
        //txtTienTNTB.value = "0";
        //txtVatTNTB.value = "0";
        this.dtientn = 0;
        this.dvattn = 0;
        //this.$toast.error("Thông tin giá chưa được cập nhật");
      }
      //nhapt thêm
      for (let j = 0; j < this.ds_tien_khoanmuc.length; j++) {
        if (
          this.ds_tien_khoanmuc[j]["khoanmuctt_id"] ==
          KHOANMUC_TT.KMTT_TNTHUEBAO
        ) {
          this.ds_tien_khoanmuc[j]["tien"] = this.dtientn;
          this.ds_tien_khoanmuc[j]["vat"] = this.dvattn;
        }
      }
    },
    //endregion

    cboKieuLD_SelectedValueChanged() {
      if (this.$refs.cboKieuLD.dataSource.length > 0) {
        if (this.$refs.cboKieuLD.value == null) return;
        if (this.$refs.cboKieuLD.value == "") return;
        //Hiếu TC bổ sung hàm lấy tiền lên ô text box ngày 14/09/2010
        this.LayTienTheoKhoanMuc();
      }
    },

    async cboLoaihinhTB_SelectedValueChanged() {
      return new Promise(async (resolve, reject) => {
        if (this.$refs.cboLoaihinhTB.value == null) return;
        if (this.$refs.cboLoaihinhTB.value == "") return;

        this.loaitb_id = Number(this.$refs.cboLoaihinhTB.value);
        rs = await this.axios.post(
          "/web-thicong/danhmuc/lay_ds_kieu_ld_by_loaitb_loaihd",
          {
            loaihd_id: LoaiHopDong.TACHNHAP_TB,
            loaitb_id: this.loaitb_id,
          }
        );
        this.$refs.cboKieuLD.dataSource = rs.data.data;
        this.$refs.cboKieuLD.dataTextField = "ten_kieuld";
        this.$refs.cboKieuLD.dataValueField = "kieuld_id";
        this.$refs.cboKieuLD.SelectedIndex = 0
        resolve(true)
      })
    },

    //region HienThiTienLapDat
    //,HienThiTienLapDat(decimal tien_ld, decimal vat_ld)
    //{
    //    txtTienTNTB.value = tien_ld;
    //    txtVatTNTB.value = vat_ld;
    //}
    //endregion

    /// <summary>
    /// TuấnNA thêm hàm kiểm tra trong hợp đồng có bao nhiêu thuê bao di động đã đồng bộ lên vinaphone
    /// // Ngày 15/10/2010
    /// // Nếu result >=2 //> true
    /// // Nếu result <2 //> false
    /// </summary>
    /// <param name="hdkh_id"></param>
    /// <returns></returns>
    KiemTra_TB_Vinaphone(hdkh_id) {
      let result = false;
      count = 0;
      let dstb_vinaphone = [];
      dstb_vinaphone = tchopdong.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(hdkh_id);
      if (dstb_vinaphone.length > 0) {
        for (let i = 0; i < dstb_vinaphone.length; i++) {
          if (
            dstb_vinaphone[i]["dichvuvt_id"] ==
            DichVuVienThong.DI_DONG.trim() &&
            dstb_vinaphone[i]["loaitb_id"] == LoaiHinhTB.DIDONGTRASAU.trim()
          ) {
            if (
              dstb_vinaphone[i]["status"].trim() ==
              TRANGTHAI_DONGBO.DONGBO_LHD.trim()
            ) {
              count++;
            }
          }
          if (count == 2) {
            result = true;
            break;
          }
        }
      }
      return result;
    },

    tsbnInPhieuYC_Click() {
      try {
        if (this.hdkh_id != 0) {
          this.popupInHoSo_Param.hdkh_id = this.hdkh_id;
          this.popupInHoSo_Param.ma_gd = this.$refs.txtMaGD.value;
          this.popupInHoSo_Param.hdtb_id = this.hdtb_id;
          this.$refs.popupInHoSo.showModal();

        }
        else {
          this.$toast.error("Chưa có thông tin hợp đồng!");
        }

      }
      catch (ex) {
        console.log(ex);
      }
      /*if (hdkh_id != 0)
            {
                try
                {
                    report = new SSRTool();
                    DataSet ds = []
                    //Tuan sua code -- Ngay 22/07/2010
                    //ds = tchopdong.IN_HOPDONG_THEO_MA_GD(txtMaGD.value, LoaiHopDong.CHUYEN_QUYEN);
                    ds = tchopdong.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(hdkh_id);

                    //region Tạo dataset để test
                    //DataSet ds = []
                    //ds.Tables.push("PhieuYC_TGTB");
                    //ds.Tables["PhieuYC_TGTB"].Columns.push("STT");
                    //ds.Tables["PhieuYC_TGTB"].Columns.push("ma_tb");
                    //ds.Tables["PhieuYC_TGTB"].Columns.push("dvvt");
                    //ds.Tables["PhieuYC_TGTB"].Columns.push("ma_tt");
                    //for (i = 0; i < 7; i++)
                    //{
                    //    ds.Tables["PhieuYC_TGTB"].push(i,"","1", "XXX-"+i);
                    //}

                    //endregion

                    DataSet ds_moi = []
                    ds_moi.Tables.push("PhieuYC_TGTB_G");
                    ds_moi.Tables.push("PhieuYC_TGTB_T");

                    ds_moi.Tables["PhieuYC_TGTB_G"].Columns.push("matb_dd", typeof(string));
                    ds_moi.Tables["PhieuYC_TGTB_G"].Columns.push("ma_tb", typeof(string));
                    ds_moi.Tables["PhieuYC_TGTB_G"].Columns.push("dvvt", typeof(string));
                    ds_moi.Tables["PhieuYC_TGTB_G"].Columns.push("ma_tt", typeof(string));
                    for (i = 0; i < ds.length; i++)
                    {
                        if (ds[i]["kieuld_id"] == KieuLapDat.NHAP_THUEBAO)
                        {
                            ds_moi.Tables["PhieuYC_TGTB_G"].push(ds[i]["matb_dd"]
                            , ds[i]["ma_tb"], ds[i]["ten_dvvt"]
                            , ds[i]["ma_tt"]);
                        }
                    }

                    ds_moi.Tables["PhieuYC_TGTB_T"].Columns.push("matb_dd", typeof(string));
                    ds_moi.Tables["PhieuYC_TGTB_T"].Columns.push("ma_tb", typeof(string));
                    ds_moi.Tables["PhieuYC_TGTB_T"].Columns.push("dvvt", typeof(string));
                    ds_moi.Tables["PhieuYC_TGTB_T"].Columns.push("ma_tt", typeof(string));
                    for (i = 0; i < ds.length; i++)
                    {
                        if (ds[i]["kieuld_id"] == KieuLapDat.TACH_THUEBAO)
                        {
                            ds_moi.Tables["PhieuYC_TGTB_T"].push(ds[i]["matb_dd"]
                            , ds[i]["ma_tb"], ds[i]["ten_dvvt"]
                            , ds[i]["ma_tt"]);
                        }

                    }


                    report.DataSource = ds_moi;
                    //region Lấy dữ liệu trên Form đẩy lên báo cáo In hợp đồng
                    report.Parameters.push("ten_kh", txtTenKH.value.ToUpper());
                    ///Tuấn NA thêm code chia ngày - tháng- năm // Ngày 31/10/2010
                    string date = "";
                    string ngay = "";
                    string thang = "";
                    string nam = "";
                    date = moment().format("DD/MM/YYYY HH:mm:ss").ToString("dd/MM/yyyy");
                    if (date != "")
                    {
                        ngay = date.Substring(0, 2);
                        thang = date.Substring(3, 2);
                        nam = date.Substring(6, date.Length - 6);
                    }
                    report.Parameters.push("ngay", ngay);
                    report.Parameters.push("thang", thang);
                    report.Parameters.push("nam", nam);
                    report.Parameters.push("diachi_tt", txtDiaChiTT.value);
                    report.Parameters.push("diachi_kh", txtDiaChiKH.value);
                    report.Parameters.push("diadanh", clsBaoCao.diadanh);
                    //endregion

                    string path = clsBaoCao.reportpath;
                    report.ReportFileName = path + "\\ReportFile\\PhieuYC_TachghepTB.rst";
                    report.ProductName = "";//"Hệ thống CSS BÌNh PHƯỚC";
                    report.ShowReport(frmFormMain.ActiveForm, true);
                }
                catch (err)
                {
                    MessageBox.Show(err.message);
                }
            }*/
    },

    async btnNSNN_Click() {
      this.objNganhang = {}
      rs = await this.axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nganhang",{ param: this.$refs.cboNganHang.value, type: 1 });
      let loai_nh = rs.data.data
      if (loai_nh == "2") {
        this.objNganhang.DV_QHNS = this.DV_QHNS;
        this.objNganhang.MACHUONG = this.MACHUONG;
        this.objNganhang.MN_KT = this.MN_KT;
        this.objNganhang.MA_NDKT = this.MA_NDKT;
        this.objNganhang.MN_NSNN = this.MN_NSNN;
        this.objNganhang.TIEN_DM = this.TIEN_DM;
        this.$refs.popupChiTietMLNS.showDialog();
      }
    },
    acceptNganhang(frm) {
      if (!frm) return
      this.DV_QHNS = frm.DV_QHNS;
      this.MACHUONG = frm.MACHUONG;
      this.MN_KT = frm.MN_KT;
      this.MA_NDKT = frm.MA_NDKT;
      this.MN_NSNN = frm.MN_NSNN;
      this.TIEN_DM = frm.TIEN_DM;
    },

    txtDiachiCT_Leave() {
      txtDiachiCT.value = txtDiachiCT.value;
    },

    txtMaTT_Leave() {
      if (txtMaTT.value.trim() != "") {
        txtMaTT_Enter();
      }
    },

    btnTaoMIG_Click() {
      if (!this.hdkh_id) {
        this.$toast.error("Chưa có thông tin về hợp đồng khách hàng!");
        return;
      }

      var obj = {};
      obj.hdkh_id = this.hdkh_id;
      obj.khachhang_id = this.khachhang_id;
      obj.hdtt_id = this.hdtt_id;
      obj.thanhtoan_id = this.thanhtoan_id;
      obj.fmig_id = this.mig_id;
      obj.fhdmig_id = this.hdmig_id;
      obj.ma_tt = this.$refs.txtMaTT.value;
      obj.ten_tt = this.$refs.txtTenTT.value;

      this.popupComponent = () => import('../InstallNewSubs/Popups/popupChonMaInGhep');
      this.popupComponentName = "popupChonMaInGhep";
      this.popupComponentData = obj;
      this.Popup('popupComponents');
    },

    //nhapt
    LayTongTien_HDTB() {
      try {
        let dtongtien = 0;
        let dtongvat = 0;
        for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          dtongtien += parseFloat(this.ds_tien_khoanmuc[i]["tien"]);
          dtongvat += parseFloat(this.ds_tien_khoanmuc[i]["vat"]);
        }
        this.$refs.txtTongTien.value = dtongtien;
        this.$refs.txtTongVat.value = dtongvat;
        this.$refs.lblTongTien.value = dtongtien + dtongvat;
      } catch (err) {
        this.$toast.error("" + err.message);
      }
    },

    btnTien_Click() {
      // nhapt -copy từ form Đặt mới thêm 09/06/2015
      try {
        this.$refs.frmCT_Tien_KM.frmCT_Tien_KM(LoaiHopDong.TACHNHAP_TB, this.ds_tien_khoanmuc)
      } catch (err) {
        this.$toast.error("Có lỗi : " + err.message);
      }
    },
    btnTien_Close(m_dsTienKM) {
      this.ds_tien_khoanmuc = m_dsTienKM;
      if (this.ds_tien_khoanmuc.length > 0)
      {
          for (let i = 0; i < this.ds_tien_khoanmuc.length; i++)
          {
              if (this.ds_tien_khoanmuc[i]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_TNTHUEBAO)
              {
                  this.dtientn = Number(this.ds_tien_khoanmuc[i]["tien"]);
                  this.dvattn = Number(this.ds_tien_khoanmuc[i]["vat"]);
              }
              this.LayTongTien_HDTB();
          }
      }
    },

    tsbtnThongTinLH_Click() {
      this.$bvModal.show("ThongTinLienHe")
    },

    ThemHDThueBao_DS(hdtb_id_ds, thuebao_id_ds, dichvuvt_id) {
      try {
        HienThiTTDBMoRong(thuebao_id_ds, dichvuvt_id);

        //TaoDuLieu_HDTT(false);//TuấnNA thêm code để cập nhật matb_dd trong nút thêm thuê bao -- ngày 18.03.2011
        huonggiaotn_id = TaoDuLieu_HDTB_DS(hdtb_id_ds, thuebao_id_ds);

        TaoDuLieu_CT_TIENHD_DS(hdtb_id_ds, thuebao_id_ds);
        dsHDTB_DD = new HDTB_DD_DATA();

        switch (dichvuvt_id) {
          case DichVuVienThong.DI_DONG:
            TaoDuLieu_HDTB_DD_DS(hdtb_id_ds, thuebao_id_ds);
            break;
        }
        TaoDuLieu_DiaChi_DS(hdtb_id_ds, thuebao_id_ds);

        //region Thêm thuê bao vào hợp đồng trên vinaphone
        let async_status = false; // Trạng thái có đồng bộ dữ liệu được không?
        //TuanNA - 28/07/2010: Thực hiện đẩy dữ liệu lên VinaPhone
        if (
          dichvuvt_id == DichVuVienThong.DI_DONG &&
          Number(cboLoaihinhTB.value) == LoaiHinhTB.DIDONGTRASAU
        ) {
          if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA) {
            ///Tuấn thêm code kiểm tra thuê bao di động đang làm có cùng mã thanh toán với các thue bao di động khác trong hợp đồng không
            ///// Ngày 18/08/2010
            if (
              laphd.KIEMTRA_TB_CUNG_MATT_HDTB(
                Number(cboTT.value),
                DichVuVienThong.DI_DONG,
                txtMaGD.value.trim()
              ) != 1
            ) {
              this.$toast.error(
                "Đối với thuê bao di động, tất cả các thuê bao trong cùng hợp đồng phải cùng mã thanh toán"
              );
              cboTT.focus();
              return;
            }
            //Tạo dữ liệu cho HDKH && HDTT
            this.TaoDuLieu_HDKH(false);
            this.TaoDuLieu_HDTT(false);
            //Nếu dữ liệu HĐ chưa được đẩy lên VinaPhone.
            if (
              new TCHopDongFacade2().KIEMTRA_HDTB_DONGBO_DULIEU(
                dsHDKH[0]["ma_gd"],
                Number(cboDichVuVT.value),
                Number(cboLoaihinhTB.value)
              ) != "1"
            ) {
              let matb_neo = CommonFunction.ChuanHoa_XML(dsHDTB[0]["ma_tb"]);
              //string ma_gd_neo = Tracuu_HDCQ_VinaPhone(matb_neo, 0); vuth update 19/12

              //if (!string.IsNullOrEmpty(ma_gd_neo))
              //{
              //    if (!Delete_HDCQ_VinaPhone(ma_gd_neo))
              //    {
              //        this.$toast.error("Có lỗi xảy ra trong quá trình xóa hợp đồng VinaPhone!");
              //        return;
              //    }
              //}
              for (
                i = 0;
                i < 3;
                i++ /// lặp 3 lần quá trình đồng bộ dữ liệu lên vinaphone
              ) {
                if (Insert_HDCQ_VinaPhone() == "OK") {
                  // Nếu đẩy dữ liệu HD lên VinaPhone thành công.
                  if (Insert_TBCQ_VinaPhone() == "OK") {
                    // Đẩy tiếp dữ liệu thuê bao lên VinaPhone.
                    async_status = true;
                    this.$toast.error(
                      "Cập nhật dữ liệu thuê bao " +
                      dsHDTB[0]["ma_tb"] +
                      " lên VinaPhone thành công!"
                    );
                    break;
                  } else {
                    var ma_gd_neo = dsHDKH[0]["ma_gd"];
                    if (Delete_HDCQ_VinaPhone(ma_gd_neo) != "OK") {
                      this.$toast.error(
                        "Có lỗi xảy ra trong quá trình xóa hợp đồng VinaPhone!"
                      );
                      return;
                    }
                  }
                }
                if (i == 2)
                  this.$toast.error(
                    "Có lỗi xảy ra trong quá trình thêm thuê bao VinaPhone" +
                    strError_VinaPhone
                  );
              }
            } // Nếu đã có hợp đồng này trên VinaPhone rồi thì chỉ đẩy 1 thuê bao lên thôi
            else {
              ///TuấnNA thêm code xử lý trường hợp thuê bao đẩy được lên vinaphone nhưng không đẩy được lên CSDL Viễn thông tỉnh
              ///// Ngày 28/09/2010
              ///1: kiểm tra trên vinaphone có tồn tại hợp đồng ứng với mã thuê bao đang thêm và hợp đồng
              /// --> nếu có hợp đồng, so sánh mã hợp đồng với "LSN-CQ + ma_gd" -- kiểm tra xem có phải hợp đồng rác không?
              ///     //> nếu giống nhau //> không phải HĐ rác //> xoá thuê bao chuyển quyền
              ///     //> nếu khác nhau //> HD rác //> xoá hợp đồng rác đó.
              ///2: sau đó thực hiện bình thường
              let matb_neo = CommonFunction.ChuanHoa_XML(dsHDTB[0]["ma_tb"]);
              let ma_gd_neo = Tracuu_HDCQ_VinaPhone(matb_neo, 0);

              if (!string.IsNullOrEmpty(ma_gd_neo)) {
                if (ma_gd_neo == dsHDKH[0]["ma_gd"]) {
                  if (Delete_TBCQ_VinaPhone(ma_gd_neo) != "OK") {
                    async_status = true;
                    this.$toast.error(
                      "Có lỗi xảy ra trong quá trình xoá thuê bao: " +
                      matb_neo +
                      " VinaPhone" +
                      strError_VinaPhone
                    );
                    return;
                  } else async_status = false;
                }
              }
              for (let i = 0; i < 3; i++) {
                if (Insert_TBCQ_VinaPhone() == "OK") {
                  async_status = true;
                  this.$toast.error(
                    "Thêm thuê bao " +
                    dsHDTB[0]["ma_tb"] +
                    " lên VinaPhone thành công!"
                  );
                  break;
                }
                if (i == 2)
                  this.$toast.error(
                    "Có lỗi xảy ra trong quá trình thêm thuê bao VinaPhone" +
                    strError_VinaPhone
                  );
              }
            }
          }
        }
        //endregion

        tachnhaptv.ThemMoi_HopDongTB(
          dsHDTB,
          dsHDTB_DD,
          dsDiaChi,
          dsDiaChiHDTB,
          dsTTTT,
          dsCTTHD,
          dsHDTT,
          huonggiaotn_id
        );
        //region Cập nhật trạng thái đồng bộ lên vinaphone vào CSDL
        //Tuan them code cập nhật trạng thái đồng bộ dữ liệu lên vinaphone // Ngày 18/08/2010
        //Kiểm tra thuê bao là di động trả sau --> cho phép cập nhật
        if (
          Number(cboDichVuVT.value) == 2 &&
          Number(cboLoaihinhTB.value) == 20
        ) {
          if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA) {
            if (async_status) {
              //Gọi hàm cập nhật trạng thái HDTB = 1: Đã đồng bộ dữ liệu với VinaPhone.
              let obj = new LapHopDongFacade();
              obj.CAPNHAT_STATUS_HDTB(hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD);
            }
          } else {
            //Gọi hàm cập nhật trạng thái HDTB = 1: Đã đồng bộ dữ liệu với VinaPhone.
            let obj = new LapHopDongFacade();
            obj.CAPNHAT_STATUS_HDTB(hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD);
          }
        }
        //endregion
      } catch (err) {
        console.log(err);
      }
    },
    async setDBTB(data) {
      console.log(data, "ahdkjashkjdahjkdas")
      if(!data || data.length <= 0) {
        return
      }
      if (this.dichvuvt_id == 2 && this.loaitb_id == 20) {
        let kt1 = await this.axios.post(
          "web-hopdong/hopdong/kiemtra_thuebao_cung_matt",
          {
            dichVuVtId: this.dichvuvt_id,
            hdttId: this.hdtt_id,
            maGd: this.$refs.txtMaGD.value,
          }
        );

        if (kt1.data && kt1.data.data != 1) {
          this.$toast.error(
            "Đối với thuê bao di động, tất cả các thuê bao trong cùng hợp đồng phải cùng mã thanh toán"
          );
          this.loading(false)
          return;
        }

        let kt2 = await this.axios.post(
          "web-hopdong/hopdong/kiemtra_hopdong_thuebao_dongbo_dulieu",
          {
            dichVuVtId: this.dichvuvt_id,
            loaiTbId: this.loaitb_id,
            maGd: this.$refs.txtMaGD.value,
          }
        );

        if (kt2.data && kt2.data.data != 1) {
          this.$toast.error(
            "Có lỗi xảy ra trong quá trình thêm thuê bao VinaPhone"
          );
          this.loading(false)
          return;
        }
      }

      let ds_matb = [];
      data.forEach((item) => {
        ds_matb.push({ THUEBAO_ID: item.thuebao_id });
      });

      let ds_hd = [
        {
          TEN_TB: this.$refs.txtTenTB.value,
          
          DIACHI_TB: this.$refs.txtDiaChiTB.value,
          HDKH_ID: this.hdkh_id,
          HDTT_ID: this.hdtt_id,
          DIACHI_LD: this.$refs.txtDiachiLD.value,
          KIEULD_ID: this.$refs.cboKieuLD.value,
          DOITUONG_ID: this.$refs.cboDoituongTB.value,
          NGAY_HT: moment().format("DD/MM/yyyy hh:mm:ss"),
          MAY_CN: this.$root.token.getUserName(),
          NGAY_CN: moment().format("DD/MM/yyyy hh:mm:ss"),
          NGUOI_CN: this.$root.token.getUserName(),
          IP_CN: this.$root.token.getUserName(),
          TOCDO_ID: this.tocdo_id ? this.tocdo_id : 0,
          MUCCUOC_ID: this.muccuoc_id ? this.muccuoc_id : 0,
          GOICUOC_ID: this.goicuoc_id ? this.goicuoc_id : 0,
          TINHLD_ID: this.tinhld_id ? this.tinhld_id : 0,
          QUANLD_ID: this.quanld_id ? this.quanld_id : 0,
          PHUONGLD_ID: this.phuongld_id ? this.phuongld_id : 0,
          PHOLD_ID: this.phold_id ? this.phold_id : 0,
          APLD_ID: this.apld_id ? this.apld_id : 0,
          KHULD_ID: this.khuld_id ? this.khuld_id : 0,
          DACDIEMLD_ID: this.dacdiemld_id ?  this.dacdiemld_id : 0,
          SONHALD: this.sonhald ? this.sonhald : "",
          TINHTB_ID: this.tinhtb_id ? this.tinhtb_id : 0,
          QUANTB_ID: this.quantb_id ? this.quantb_id : 0,
          PHUONGTB_ID: this.phuongtb_id ? this.phuongtb_id : 0,
          PHOTB_ID: this.photb_id ? this.photb_id : 0,
          APTB_ID: this.aptb_id ? this.aptb_id : 0,
          KHUTB_ID: this.khutb_id ? this.khutb_id : 0,
          DACDIEMTB_ID: this.dacdiemtb_id ? this.dacdiemtb_id : 0,
          SONHATB: this.sonhatb ? this.sonhatb: ""
        },
      ];

      let rs = await this.axios.post(
        "web-hopdong/tachnhapthuebao/fn_themhdtb_ds_tachnhaptb",
        {
          ds_matb: JSON.stringify(ds_matb),
          ds_hd: JSON.stringify(ds_hd),
        }
      );

      if (rs.data) {
        if (rs.data.data != 1) {
          this.$toast.error(rs.data.data);
        } else {
          this.HienThiDS_HDTT_CBO(this.hdkh_id);
          rs = await this.axios.get(
            "web-hopdong/ThayDoiHopDong/lay_ds_hopdong_tt_theo_hdkh_id",
            {
              params: { hdkhId: this.hdkh_id },
            }
          );
          this.HienThiDanhSacHDTT(rs.data.data);
          this.HT_DanhSachTB();
          this.$toast.success("Thêm danh sách thuê bao thành công!");
        }
      }
    },

    async tsbtnThemTB_DS_Click() {
      try {
        if (this.hdkh_id != 0) {
          if (!(await this.KiemTraDL_ThanhToan())) return;
          if (!await this.KiemTraDL_ThueBao_DS()) return;

          this.$refs.frmTC_DBTB_KH.showModal(
            this.khachhang_id,
            0,
            0,
            this.hdkh_id,
            0
          );
        }
      } catch (err) {
        console.log(err)
      }
    },

    async HienThi_NVQL_AM_HTTT(
      i_loaikh_id,
      i_loainv_id,
      i_phuong_id,
      i_pho_id,
      i_ap_id,
      i_khu_id,
      i_nvtc_id,
      vdonviql_id,
      vhttt_id
    ) {
      try {
        if (kt_load == false) {
          let ds_nvql = [];
          //ds_nvql = new TraCuuDanhBaFacade2().lay_ds_nhanvien_ql_am(i_loaikh_id, i_loainv_id, i_phuong_id, i_pho_id, i_ap_id, i_khu_id, i_nvtc_id);
          rs = await this.axios.post("web-hopdong/lapdatmoi/lay_ds_nhanvien_ql_am_v2", {
            "vap_id": i_ap_id,
            "vdonviql_id": vdonviql_id,
            "vhttt_id": vhttt_id,
            "vkhu_id": i_khu_id,
            "vloaikh_id": i_loaikh_id,
            "vloainv_id": i_loainv_id,
            "vnvtc_id": i_nvtc_id,
            "vphanvung_id": this.$root.token.getPhanVungID(),
            "vpho_id": i_pho_id,
            "vphuong_id": i_phuong_id
          })
          ds_nvql = rs.data.data

          if (ds_nvql.length <= 0) {
            this.$refs.txtNhanVienQL.value = "";
            this.nhanvien_ql_am_id = 0;
          } else {
            this.$refs.txtNhanVienQL.value =
              ds_nvql[0]["ma_nv"] + " - " + ds_nvql[0]["ten_nv"];
            this.nhanvien_ql_am_id = Number(ds_nvql[0]["nhanvien_id"]);
          }
        }
      } catch (err) {
        console.log(err)
        this.$toast.error("Có lỗi : " + err);
      }
    },

    async HienThi_NVQL_AM(
      i_loaikh_id,
      i_loainv_id,
      i_phuong_id,
      i_pho_id,
      i_ap_id,
      i_khu_id,
      i_nvtc_id,
      vdonviql_id
    ) {
      try {
          let ds_nvql = [];
          rs = await this.axios.post("web-hopdong/tachnhapthuebao/lay_ds_nhanvien_ql_am_1",
            {
              "loaikh_id": i_loaikh_id, "loainv_id": i_loainv_id, "phuong_id": i_phuong_id, "pho_id": i_pho_id,
              "ap_id": i_ap_id, "khu_id": i_khu_id, "nvtc_id": i_nvtc_id ? i_nvtc_id : 0, "donviql_id": vdonviql_id
            })
          ds_nvql = rs.data ? rs.data.data : []
          if (ds_nvql.length <= 0) {
            this.$refs.txtNhanVienQL.value = "";
            this.nhanvien_ql_am_id = 0;
          } else {
            this.$refs.txtNhanVienQL.value =
              ds_nvql[0]["ma_nv"] + " - " + ds_nvql[0]["ten_nv"];
            this.nhanvien_ql_am_id = Number(ds_nvql[0]["nhanvien_id"]);
          }
      } catch (err) {
        console.log(err)
      }
    },

    async HienThi_NVQL_AM_DB_HD(
      i_kieu,
      id // Kieu = 1 : danh ba, kieu = 2 : hop dong
    ) {
      try {
        let str = "";
        let ds_tt = [];
        rs = await this.axios.post("web-hopdong/lapdatmoi/sp_lay_nhanvien_ql", {
          id: id,
          kieu: i_kieu,
          phanvung_id: this.$root.token.getPhanVungID(),
        });
        ds_tt = rs.data.data;
        if (ds_tt && ds_tt.length > 0) {
          this.$refs.txtNhanVienQL.value =
            ds_tt[0]["ma_nv"] + " - " + ds_tt[0]["ten_nv"];
          this.nhanvien_ql_am_id = Number(ds_tt[0]["nhanvien_id"]);
        } else {
          this.$refs.txtNhanVienQL.value = "";
          this.nhanvien_ql_am_id = 0;
        }
      } catch (err) {
        this.$toast.error("Có lỗi : " + err.message);
      }
    },

    Init_Properties(vloaitb_id, table_name, opt_list, ItemList) {
      try {
        let ds = [];
        let property = new Property_listFacade();
        ds = property.Lay_danhsach_thuoctinh_v2(
          vloaitb_id,
          table_name,
          opt_list
        );
        ItemList = new ITEM_TYPE[ds.length]();
        if (ItemList.Length == 0) return;
        for (let i = 0; i < ItemList.Length; i++) {
          let row = ds[i];
          ItemList[i].sFieldName = row["field_name"];
          ItemList[i].sCaption = row["ten_hienthi"];
          ItemList[i].iAtt = Convert.ToByte(row["att"]);
          ItemList[i].iDataType = Convert.ToByte(row["data_type"]);
          ItemList[i].sMaxValue = row["max_value"];
          ItemList[i].sMinvalue = row["min_value"];
          ItemList[i].sValue = row["field_value"];
          ItemList[i].nFieldLength = string.IsNullOrEmpty(row["field_length"])
            ? 0
            : Number(row["field_length"]);
          ItemList[i].bIsNotNull = string.IsNullOrEmpty(row["field_nullable"])
            ? false
            : row["field_nullable"] == "0"
              ? true
              : false;
          ItemList[i].bIsUnique = string.IsNullOrEmpty(row["field_unique"])
            ? false
            : row["field_unique"] == "0"
              ? false
              : true;
          ItemList[i].sLookUpTableName = row["parent_table"];
          ItemList[i].sLookUpKeyField = row["parent_keyfield"];
          ItemList[i].sLookUpValueField = row["parent_descfield"];
          ItemList[i].strDepandField = row["depend_field"];
          ItemList[i].StrSql = row["lookup_sql"];
        }
      } catch (err) {
        this.$toast.error("Có lỗi: " + ex.Message);
      }
    },
    async LoadTuHopDongTuVan(hdkh_cha_id) {
      return new Promise(async (resolve, reject) => {
        this.hdkh_cha_id = hdkh_cha_id;
        
        let filter = "ma_tb is not null and khachhang_id in (select khachhang_id from css.hd_khachhang WHERE hdkh_id=" + hdkh_cha_id + ")"
        rs = await this.axios.post("web-hopdong/tachnhapthuebao/sp_lay_dulieu_theo_dieukien_phanvung", {
          "p_order": "",
          "p_schema": "css",
          "p_table_name": "db_thuebao",
          "p_text": "ma_tb",
          "p_value": "dichvuvt_id",
          "p_where": filter
        })
        let dt = rs.data.data
        if (dt.length > 0) {
          this.$refs.cboDichVuVT.value = dt[0].dichvuvt_id;
          this.$refs.txtMaTB.value = dt[0].ma_tb;
          await this.txtMaTB_KeyPress();
        } else {
          this.$toast.error("Khách hàng không có thuê bao");
        }
        resolve(true)
      })
    },

    chkNguoiGT_CheckedChanged() {
      if (this.$refs.chkNguoiGT.checked) {
        console.log(this.$refs.frmNguoiGT)
        let f = this.$refs.frmNguoiGT
        f.nhomlnv_id = NHOM_LNV.GIOITHIEU_HD;
        f.donvi_id = this.donvi_nguoi_gt_id;
        f.loainv_id = this.loainv_nguoi_gt_id;
        f.ctv_id = this.nguoigt_id;
        f.ShowDialog();
      } else {
        this.nguoigt_id = 0;
        this.$refs.txtNguoiGT.value = "";
      }
    },
    chonNhanvien(f) {
      console.log(f);
      if(!f) {
        this.$refs.chkNguoiGT.checked = false;
        return
      }
      this.$refs.txtNguoiGT.value = f.ten_nv;
      this.nguoigt_id = f.nhanvien_id;
      this.loainv_nguoi_gt_id = f.loainv_id ? f.loainv_id : "0";
      this.donvi_nguoi_gt_id = f.donvi_id ? f.donvi_id : "0";

      if (this.nguoigt_id == 0) this.$refs.chkNguoiGT.checked = false;
    },
    loadcboDonViQL(value){
          this.$refs.cboDonViQL.value = value
    },
    HienThi_NVQL_AM_PTKH: async function(phuong_id, pho_id, ap_id, khu_id, dacdiem_id, loaikh_id, nganhnghe_id, doanhthu, diem_tn, loainv_id, donvi_ql_id) {
        try {
          if(!this.kt_load) {
            let ds_nvql = [];
            let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_am_ptkh', {
              phuong_id: phuong_id,
              pho_id: pho_id,
              ap_id: ap_id,
              khu_id: khu_id,
              dacdiem_id: dacdiem_id,
              loaikh_id: loaikh_id,
              nganhnghe_id: nganhnghe_id,
              doanhthu: doanhthu,
              diemtn: diem_tn,
              loainv_id: loainv_id,
              donvi_ql_id: donvi_ql_id,
            })
            ds_nvql = rs.data;
            if(ds_nvql.length == 0) {
              this.$refs.txtNhanVienQL.value = "";
              this.nhanvien_ql_am_id = 0;
            }
            else {
              this.$refs.txtNhanVienQL.value = ds_nvql[0].ma_nv + " - " + ds_nvql[0].ten_nv;
              this.nhanvien_ql_am_id = ds[0].nhanvien_id;
            }
          }
        } catch (error) {
          console.log(error)
        }
      },
      
  },
};
</script>
<style>
a.disabled {
  color: #d3d3d3 !important;
  pointer-events: none;
  cursor: not-allowed;
  opacity: 0.6;
}

.thanhtoan-modal .page-content {
  position: unset !important;
}

.thanhtoan-modal .main-wrapper {
  position: unset !important;
}

#frmThanhToanHD .modal-xl {
  max-width: 90% !important;
}

.list-actions-top a {
  cursor: pointer;
}

.btn-second {
    background-color: #fff;
    border-color: #0176FF;
    color: #0176FF;
    font-weight: 600;
}
.btn-second:hover {
    background-color: #0176FF;
    border-color: #0176FF;
    color: #fff;
    font-weight: 600;
}
.btn-outline-secondary {
    background-color: #fff;
    color: #6c757d;
    border-color: #6c757d;
}
.btn-outline-secondary:hover {
    background-color: #6c757d;
    color: #fff;
    border-color: #6c757d;
}

.main-wrapper.SearchGeneral {
  position: unset;
}

.main-wrapper.SearchGeneral .page-content {
  position: unset;
}

</style>
