<template src="./NormalizeCAinformation.html"></template>
<style src="./NormalizeCAinformation.scss" scoped></style>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import moment from "moment";
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import BssErrorMarker from "@/components/BssErrorMarker";
import BssRequiredMarker from "@/components/BssRequiredMarker";
import { required, minValue, maxValue } from "vuelidate/lib/validators";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
import ModalChonDiaChi from "@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi";
import breadcrumb from "@/components/breadcrumb";
import DataGrid from "@/components/Controls/DataGrid";
import api from "./API.js";
import UploadEProfileAPI from "@/modules/contract/profile/UploadEProfile/UploadEProfileAPI.js";
import { LoaiHopDong, LOAI_DV } from "./enum.js";
import ModalNormalizeCAinformation from "./ModalNormalizeCAinformation.vue";
// import uploadEProfile from "@/modules/contract/profile/UploadEProfile";
import UploadEProfileContent from "@/modules/contract/profile/UploadEProfile/UploadEProfile_content.vue";

export default {
  name: "NormalizeCAinformation",
  components: {
    DatePickerPlugin,
    BssErrorMarker,
    BssRequiredMarker,
    breadcrumb,
    DataGrid,
    ModalChonDiaChi,
    ModalNormalizeCAinformation,
    // uploadEProfile,
    UploadEProfileContent,
    DialogPlugin,
  },
  validations: {
    diachiLD: {
      DIACHI: {
        required,
      },
    },
  },
  data() {
    return {
      targetTop: "#app", // document.querySelector('body'),.main-wrapper
      position: { X: "center", Y: "top" },
      isShowTTKH: true,
      isShowTTTB: true,
      isShowDVD: true,
      isShowTTCMS: true,
      isShowTTThucTe: true,
      isNhapMoi: true,
      isShowFrmHoSo: false,
      btnMaGD: false,
      btnMaTB: false,
      today: new Date(),
      ds_kieuLD: [],
      ds_dichvuVT: [],
      ds_loaihinhTB_all: [],
      ds_loaihinhTB: [],
      ds_loaiGT: [],
      ds_goicuoc: [],
      ds_mucdich: [],
      ds_htsinhkhoa: [],
      ds_loaicts: [],
      ds_donvi: [],
      diachiLD: {
        DIACHI: "",
        DIACHI_ID: 0,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        SO_NHA: "",
        Lng: 0,
        Lat: 0,
        BLOCK: "",
        TANG: 0,
        PHONG: 0,
        MOTA: "",
      },
      leftForm: {
        cboKieuLD: 13182,
        cboDichVuVT: 14,
        cboLoaiHinhThueBao: 116,
        txtMaGD: "",
        txtMaHD: "",
        txtMaKH: "",
        dtpNgayYeuCau: "",
        dtpNgayLapHD: "",
        txtTenKH: "",
        txtDiaChiKH: "",
        txtMaTB: "",
        txtTenTB: "",
        txtDiaChiLD: "",
        txtUserName: "",
        txtSoThangSD: 0,
        dtpNgayBD: "",
        dtpNgayKT: "",
        txtSerial: "",
        txtMST: "",
        txtSDT: "",
        txtEmail: "",
        cboLoaiGT: 0,
        txtSoGT: "",
        txtChucDanh: "",
        dtpNgayHetHan: "",
        cboGoiCuoc: 0,
        cboMucDichCTS: 0,
        txtThucTheID: 0, //ID thuc the
        txtCTSID: 0, //ID chung thu
        cboHinhThucSinhKhoaId: 0,
        cboLoaiCTSId: 0,
        txtTrangThaiTB: "",
      },
      rightForm: {
        txtCMSUserName: "",
        txtCMSMaSoThue: "",
        txtCMSSerial: "",
        txtCMSTenChuThe: "",
        txtCMSSoDT: "",
        txtCMSEmail: "",
        dtpCMSNgayBD: "",
        dtpCMSNgayKT: "",
        txtGoiCuocCMS: "",
        txtCMSSoThang: 0,
        txtCMSLoaiGT: "",
        txtCMSSoGT: 0,
        txtMucDichCMS: "",
        txtCMSDoDaiKhoa: "",
        txtIDThucTheCMS: 0,
        txtIDChungThuSoCMS: 0,
        txtCMSDiaChi: "",
      },
      CheckedBoxs: {
        ckbDongBoCMSUserName: false,
        ckbDongBoSerial: false,
        ckbDongBoTenChuThe: false,
        ckbDongBoSDT: false,
        ckbDongBoEmail: false,
        ckbDongBoNgayBD: false,
        ckbDongBoNgayKT: false,
        ckbDongBoSoThang: false,
        ckbDongBoIdThucThe: false,
        cbkDongBoIDCTS: false,
      },
      danhbaKH: {},
      danhsachHopDongThueBao: {},
      thuebao_id: 0,
      hdtb_id: 0,
      muccuoctn_id: 0,
      vhuonggiao_id: 0,
      vquytrinh_id: 0,
      vluong_id: 0,
      grcThueBao: {
        list: [],
        header: [
          {
            fieldName: "ma_tb",
            headerText: "Mã thuê bao",
            allowFiltering: true,
          },
          {
            fieldName: "ten_tb",
            headerText: "Tên thuê bao",
            allowFiltering: true,
          },
          {
            fieldName: "trangthai_hd",
            headerText: "Trạng thái HĐ",
            allowFiltering: true,
          },
          {
            fieldName: "diachi_ld",
            headerText: "Địa chỉ lắp đặt",
            allowFiltering: true,
          },
          {
            fieldName: "status",
            headerText: "Trạng thái",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      grcChungThuSoCMS: {
        list: [],
        header: [
          {
            fieldName: "",
            headerText: "ID Chứng thư",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Serial Number",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Ngày KT",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Gói cước (CMS)",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Số tháng",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Kiểu cấp CTS",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Trạng thái",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Loại chứng thư",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Số tháng nợ",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "	Subject DN",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Email CTS",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Mục đích",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Lý do",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Serial cha",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "RC ID",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "RC TYPE",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      grcThucTheCMS: {
        list: [],
        header: [
          {
            fieldName: "",
            headerText: "User name",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Tên chủ thể",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Prefix",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Subfix",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Tên chủ thể",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Email",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Subject DN",
            allowFiltering: true,
          },
          {
            fieldName: "",
            headerText: "Tỉnh",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      vds_hdkh: {},
      vds_hdtb: {},
      vds_hdtb_sub: {},
      vds_hdtb_cntt: {},
      vds_diachi_ld: {},
      vds_hdtb_donvi: [],
    };
  },
  async created() {
    this.loading(true);
    try {
      await Promise.all([
        this.getDSKieuYCLD(),
        this.getDSDichVuVT(),
        this.getDSLHTB(),
        this.getDSLoaiGT(),
        this.getDSMucDichSD(),
        this.getDSLoaiCTS(),
      ]);
    } catch (ex) {
      this.loading(false);
    } finally {
      this.loading(false);
    }
  },
  methods: {
    getDSKieuYCLD: async function () {
      let data = this.GetData(await api.getDSKieuYCLD(this.axios));
      if (data.length > 0) {
        this.ds_kieuLD = data.map((th) => ({
          id: th.KIEULD_ID,
          text: th.TEN_KIEULD,
        }));
      }
    },
    getDSDichVuVT: async function () {
      let data = this.GetData(await api.getDSDichVuVT(this.axios));
      if (data.length > 0) {
        this.ds_dichvuVT = data.map((th) => ({
          id: th.DICHVUVT_ID,
          text: th.TEN_DVVT,
        }));
      }
    },
    getDSLHTB: async function () {
      let data = this.GetData(await api.getDSLHTB(this.axios));
      if (data.length > 0) {
        this.ds_loaihinhTB_all = data;
        if (data.length > 0) {
          this.ds_loaihinhTB = data
            .filter((e) => e.DICHVUVT_ID == this.cp_dichvuvt_id)
            .map((th) => ({
              id: th.LOAITB_ID,
              text: th.LOAIHINH_TB,
            }));
          this.getDSGoiCuoc(this.cp_loaitb_id);
          this.getDSHTSinhKhoa(this.cp_loaitb_id);
        }
      }
    },
    getDSLoaiGT: async function () {
      let data = this.GetData(await api.getDSLoaiGT(this.axios));
      if (data.length > 0) {
        this.ds_loaiGT = data
          .filter((e) => e.MA_GT_CA != null)
          .map((th) => ({
            id: th.LOAIGT_ID,
            text: th.LOAI_GT,
          }));
        this.leftForm.cboLoaiGT =
          this.ds_loaiGT.length > 0 ? this.ds_loaiGT[0].id : 0;
      }
    },
    getDSGoiCuoc: async function (loaitb_id) {
      var input = {
        loaitb_id: loaitb_id,
      };
      // var input = {
      //   loaitb_id: 80,
      // };
      let data = this.GetData(await api.getDSGoiCuoc(this.axios, input));
      if (data.length > 0) {
        this.ds_goicuoc = data.map((th) => ({
          id: th.tocdo_id,
          text: th.thuonghieu,
        }));
        this.leftForm.cboGoiCuoc =
          this.ds_goicuoc.length > 0 ? this.ds_goicuoc[0].id : 0;
      }
    },
    getDSMucDichSD: async function () {
      let data = this.GetData(await api.getDSMucDichSD(this.axios));
      if (data.length > 0) {
        this.ds_mucdich = data
          .filter((e) => e.MUCDICH_CA_ID != null)
          .map((th) => ({
            id: th.MUCDICH_ID,
            text: th.MUCDICH_SD,
          }));
        this.leftForm.cboMucDichCTS =
          this.ds_mucdich.length > 0 ? this.ds_mucdich[0].id : 0;
      }
    },
    getDSHTSinhKhoa: async function (loaitb_id) {
      var input = {
        loaitb_id: loaitb_id,
      };
      // var input = {
      //   loaitb_id: 80,
      // };
      let data = this.GetData(await api.getDSHTSinhKhoa(this.axios, input));
      if (data.length > 0) {
        this.ds_htsinhkhoa = data.map((th) => ({
          id: th.ht_sinhkhoa_id,
          text: th.ht_sinhkhoa,
        }));
        this.leftForm.cboHinhThucSinhKhoaId =
          this.ds_htsinhkhoa.length > 0 ? this.ds_htsinhkhoa[0].id : 0;
      }
    },
    getDSLoaiCTS: async function () {
      let data = this.GetData(await api.getDSLoaiCTS(this.axios));
      if (data.length > 0) {
        this.ds_loaicts = data
          .filter((e) => e.LOAITB_ID == 116)
          .map((th) => ({
            id: th.LOAICTS_ID,
            text: th.LOAI_CTS,
          }));
        this.leftForm.cboLoaiCTSId =
          this.ds_loaicts.length > 0 ? this.ds_loaicts[0].id : 0;
      }
    },
    getdiachiLD: function (data) {
      this["diachiLD"].DIACHI = data.diachimoi ? data.diachimoi : "";
      this["diachiLD"].TINH_ID = data.tinh_id ? data.tinh_id : 0;
      this["diachiLD"].QUAN_ID = data.quan_id ? data.quan_id : 0;
      this["diachiLD"].PHUONG_ID = data.phuong_id ? data.phuong_id : 0;
      this["diachiLD"].PHO_ID = data.pho_id ? data.pho_id : 0;
      this["diachiLD"].AP_ID = data.ap_id ? data.ap_id : 0;
      this["diachiLD"].KHU_ID = data.khu_id ? data.khu_id : 0;
      this["diachiLD"].DACDIEM_ID = data.dacdiem_id ? data.dacdiem_id : 0;
      this["diachiLD"].SO_NHA = data.so_nha ? data.so_nha : "";
      this["diachiLD"].Lat = data.lat ? data.lat : 0;
      this["diachiLD"].Lng = data.lng ? data.lng : 0;
    },
    txtMaTB_KeyPress: async function () {
      this.loading(true);
      this.XoaForm();
      this.leftForm.txtMaGD = "";
      await this.HienThiThongTinDBKH(this.leftForm.txtMaTB);
      this.loading(false);
    },
    HienThiThongTinDBKH: async function (ma_tb) {
      var input = {
        in_ma_tb: ma_tb,
        in_dichvuvt_id: this.leftForm.cboDichVuVT,
        in_donvi_dl_id: 0,
      };
      if (!this.validateThongTinDBKH(input)) return;
      let data = this.GetData(await api.getDSDB_MTB(this.axios, input));
      if (data.length > 0) {
        this.danhbaKH = data[0];
        if (data[0].loaitb_id != 116) {
          this.ShowError("Chức năng chỉ dùng cho dịch vụ CA!");
        } else {
          this.leftForm.txtMaHD = data[0].ma_hd ? data[0].ma_hd : "";
          this.leftForm.dtpNgayYeuCau = moment(this.today).format("DD/MM/y");
          this.leftForm.dtpNgayLapHD = data[0].ngaylap_hd
            ? moment(data[0].ngaylap_hd).format("DD/MM/y")
            : "";
          this.leftForm.txtTenKH = data[0].ten_kh ? data[0].ten_kh : "";
          this.leftForm.txtMaKH = data[0].ma_hd ? data[0].ma_hd : "";
          this.leftForm.txtDiaChiKH = data[0].diachi_kh
            ? data[0].diachi_kh
            : "";
          this.leftForm.txtTenTB = data[0].ten_tb ? data[0].ten_tb : "";
          this.leftForm.txtMaTB = data[0].ma_tb ? data[0].ma_tb : "";
          this.leftForm.txtDiaChiLD = data[0].diachi_ld
            ? data[0].diachi_ld
            : "";
          this.diachiLD.DIACHI = data[0].diachi_ld ? data[0].diachi_ld : "";
          this.leftForm.txtTrangThaiTB = data[0].trangthai_tb
            ? data[0].trangthai_tb
            : "";
          this.thuebao_id = data[0].thuebao_id;

          if (!this.getDSDiaChi_DBTBID(this.thuebao_id)) return;
          this.LoadThongTinDBSub(this.thuebao_id);
          this.LoadThongTinDBTBCNTT(this.thuebao_id);
          this.LayDonViDich(
            this.danhsachHopDongThueBao.donvi_id,
            this.thuebao_id,
            0
          );
          this.LayLuongNghiepVu();
          this.isNhapMoi = true;
        }
      } else {
        this.ShowError("Không tìm thấy thông tin!");
      }
    },
    getDSDiaChi_DBTBID: async function (thuebao_id) {
      var input = {
        in_thuebao_id: thuebao_id,
      };
      let data = this.GetData(await api.getDSDiaChi_DBTBID(this.axios, input));
      if (data.length > 0) {
        this["diachiLD"].DIACHI = data[0].diachi ? data[0].diachi : "";
        this["diachiLD"].TINH_ID = data[0].tinh_id ? data[0].tinh_id : 0;
        this["diachiLD"].QUAN_ID = data[0].quan_id ? data[0].quan_id : 0;
        this["diachiLD"].PHUONG_ID = data[0].phuong_id ? data[0].phuong_id : 0;
        this["diachiLD"].PHO_ID = data[0].pho_id ? data[0].pho_id : 0;
        this["diachiLD"].AP_ID = data[0].ap_id ? data[0].ap_id : 0;
        this["diachiLD"].KHU_ID = data[0].khu_id ? data[0].khu_id : 0;
        this["diachiLD"].DACDIEM_ID = data[0].dacdiem_id
          ? data[0].dacdiem_id
          : 0;
        this["diachiLD"].SO_NHA = data[0].sonha ? data[0].sonha : "";
        this["diachiLD"].Lat = data[0].vido ? data[0].vido : 0;
        this["diachiLD"].Lng = data[0].kinhdo ? data[0].kinhdo : 0;
        this.leftForm.txtDiaChiLD = data[0].diachi_ld ? data[0].diachi_ld : "";
        return true;
      } else {
        this.ShowError("Không lấy được thông tin địa chỉ!");
        return false;
      }
    },
    LoadThongTinDBSub: async function (thuebao_id) {
      var input = {
        vthuebao_id: thuebao_id,
      };
      let data = this.GetData(await api.getThongTinDBSub(this.axios, input));
      if (data.length > 0) {
        this.leftForm.txtSoGT = data[0].so_gt ? data[0].so_gt : "";
        this.leftForm.dtpNgayHetHan = data[0].ngayhethan_gt
          ? moment(data[0].ngayhethan_gt).format("DD/MM/y")
          : "";
        this.leftForm.txtChucDanh = data[0].chucdanh ? data[0].chucdanh : "";
        this.leftForm.cboLoaiGT = data[0].loaigt_id ? data[0].loaigt_id : 0;
      }
    },
    LoadThongTinDBTBCNTT: async function (thuebao_id) {
      var input = {
        in_thuebao_id: thuebao_id,
      };
      let data = this.GetData(await api.getThongTinDBTBCNTT(this.axios, input));
      if (data.length > 0) {
        this.leftForm.txtMST = data[0].mst ? data[0].mst : "";
        this.leftForm.txtSDT = data[0].so_dt ? data[0].so_dt : "";
        this.leftForm.txtSerial = data[0].serial ? data[0].serial : "";
        this.leftForm.txtSoThangSD = data[0].so_thang ? data[0].so_thang : 0;
        this.leftForm.dtpNgayBD = data[0].ngay_bd
          ? moment(data[0].ngay_bd).format("DD/MM/y")
          : "";
        this.leftForm.dtpNgayKT = data[0].ngay_kt
          ? moment(data[0].ngay_kt).format("DD/MM/y")
          : "";
        this.leftForm.cboGoiCuoc = data[0].tocdo_id ? data[0].tocdo_id : 0;
        this.leftForm.txtEmail = data[0].email ? data[0].email : "";
        this.leftForm.txtUserName = data[0].username ? data[0].username : "";
        this.leftForm.cboMucDichCTS = data[0].mucdich_ids
          ? data[0].mucdich_ids
          : 0;
        this.leftForm.txtCTSID = data[0].cts_id ? data[0].cts_id : 0;
        this.leftForm.txtThucTheID = data[0].ee_id ? data[0].ee_id : 0;
        this.muccuoctn_id = data[0].muccuoc_id ? data[0].muccuoc_id : 0;
        this.leftForm.cboLoaiCTSId = data[0].loaicts_id
          ? data[0].loaicts_id
          : 0;
        this.leftForm.cboHinhThucSinhKhoaId = data[0].ht_sinhkhoa_id
          ? data[0].ht_sinhkhoa_id
          : 0;
      }
    },
    validateThongTinDBKH: function (input) {
      if (!input.in_ma_tb || input.in_ma_tb.trim() == "") {
        this.ShowError("Mã thuê bao không được trống!");
        return false;
      }
      return true;
    },
    txtMaGD_KeyPress: async function () {
      this.loading(true);
      await this.HienThiThongTinHDTB(this.leftForm.txtMaGD);
      this.loading(false);
    },
    HienThiThongTinHDTB: async function (ma_gd) {
      this.grcThueBao.list = [];
      // var input = {
      //   in_ma_gd: ma_gd,
      //   in_loaihd_id: 24,
      //   in_donvi_id: Number(this.$auth.getDonViID()),
      //   in_tthd_id: 1,
      //   in_nhanvien_id: Number(this.$auth.getNhanVienID()),
      //   in_donvi_dl_id: 0,
      // };
      var input = {
        in_ma_gd: ma_gd,
        in_loaihd_id: "24",
        in_donvi_id: "0",
        in_tthd_id: "0",
        in_nhanvien_id: "0",
        in_donvi_dl_id: "0",
      };
      if (!this.validateThiThongTinHDTB(input)) return;
      let data = this.GetData(await api.getDSHD_MGD(this.axios, input));
      if (data.length > 0) {
        this.grcThueBao.list = data;
        // this.danhsachHopDongThueBao = data[0];
        // this.leftForm.txtMaGD = data[0].ma_gd ? data[0].ma_gd : "";
        // this.leftForm.txtMaKH = data[0].ma_kh ? data[0].ma_kh : "";
        // this.leftForm.txtTenKH = data[0].ten_kh ? data[0].ten_kh : "";
        // this.leftForm.dtpNgayYeuCau = data[0].ngay_yc
        //   ? moment(data[0].ngay_yc).format("DD/MM/y")
        //   : "";
        // this.leftForm.dtpNgayLapHD = data[0].ngaylap_hd
        //   ? moment(data[0].ngaylap_hd).format("DD/MM/y")
        //   : "";
        // this.leftForm.txtMaHD = data[0].ma_hd ? data[0].ma_hd : "";
        // this.leftForm.txtDiaChiKH = data[0].diachi_kh ? data[0].diachi_kh : "";
        // this.leftForm.txtTenTB = data[0].ten_tb ? data[0].ten_tb : "";
        // this.leftForm.txtMaTB = data[0].ma_tb ? data[0].ma_tb : "";
        // this.leftForm.txtDiaChiLD = data[0].diachi_ld ? data[0].diachi_ld : "";
        // this.diachiLD.DIACHI = data[0].diachi_ld ? data[0].diachi_ld : "";
        // this.leftForm.txtTrangThaiTB = data[0].trangthai_hd
        //   ? data[0].trangthai_hd
        //   : "";

        // if (!this.getDSDiaChi_HDTBID(this.danhsachHopDongThueBao.hdtb_id))
        //   return;
        // this.LoadThongTinHDTBCNTT(this.danhsachHopDongThueBao.hdtb_id);
        // this.LoadThongTinHDTB_SUB(this.danhsachHopDongThueBao.hdtb_id);
        // this.LayDonViDich(
        //   this.danhsachHopDongThueBao.donvi_id,
        //   0,
        //   this.danhsachHopDongThueBao.hdtb_id
        // );
        // this.LayLuongNghiepVu();
        // this.isNhapMoi = false;
      } else {
        this.ShowError("Không tìm thấy thông tin!");
      }
    },
    getDSDiaChi_HDTBID: async function (hdtb_id) {
      var input = {
        in_hdtb_id: hdtb_id,
      };
      let data = this.GetData(await api.getDSDiaChi_HDTBID(this.axios, input));
      if (data.length > 0) {
        this["diachiLD"].DIACHI = data[0].diachi ? data[0].diachi : "";
        this["diachiLD"].TINH_ID = data[0].tinh_id ? data[0].tinh_id : 0;
        this["diachiLD"].QUAN_ID = data[0].quan_id ? data[0].quan_id : 0;
        this["diachiLD"].PHUONG_ID = data[0].phuong_id ? data[0].phuong_id : 0;
        this["diachiLD"].PHO_ID = data[0].pho_id ? data[0].pho_id : 0;
        this["diachiLD"].AP_ID = data[0].ap_id ? data[0].ap_id : 0;
        this["diachiLD"].KHU_ID = data[0].khu_id ? data[0].khu_id : 0;
        this["diachiLD"].DACDIEM_ID = data[0].dacdiem_id
          ? data[0].dacdiem_id
          : 0;
        this["diachiLD"].SO_NHA = data[0].sonha ? data[0].sonha : "";
        this["diachiLD"].Lat = data[0].vido ? data[0].vido : 0;
        this["diachiLD"].Lng = data[0].kinhdo ? data[0].kinhdo : 0;
        this.leftForm.txtDiaChiLD = data[0].diachi_ld ? data[0].diachi_ld : "";
        return true;
      } else {
        this.ShowError("Không lấy được thông tin địa chỉ!");
        return false;
      }
    },
    LoadThongTinHDTBCNTT: async function (hdtb_id) {
      var input = {
        in_hdtb_id: hdtb_id,
      };
      let data = this.GetData(await api.getThongTinHDTBCNTT(this.axios, input));
      if (data.length > 0) {
        this.leftForm.txtMST = data[0].mst ? data[0].mst : "";
        this.leftForm.txtSDT = data[0].so_dt ? data[0].so_dt : "";
        this.leftForm.txtSerial = data[0].serial ? data[0].serial : "";
        this.leftForm.txtSoThangSD = data[0].so_thang ? data[0].so_thang : 0;
        this.leftForm.dtpNgayBD = data[0].ngay_bd
          ? moment(data[0].ngay_bd).format("DD/MM/y")
          : "";
        this.leftForm.dtpNgayKT = data[0].ngay_kt
          ? moment(data[0].ngay_kt).format("DD/MM/y")
          : "";
        this.leftForm.cboGoiCuoc = data[0].tocdo_id ? data[0].tocdo_id : 0;
        this.leftForm.txtEmail = data[0].email ? data[0].email : "";
        this.leftForm.txtUserName = data[0].username ? data[0].username : "";
        this.leftForm.cboMucDichCTS = data[0].mucdich_ids
          ? data[0].mucdich_ids
          : 0;
        this.leftForm.txtCTSID = data[0].cts_id ? data[0].cts_id : 0;
        this.leftForm.txtThucTheID = data[0].ee_id ? data[0].ee_id : 0;

        this.leftForm.cboLoaiCTSId = data[0].loaicts_id
          ? data[0].loaicts_id
          : 0;
        this.leftForm.cboHinhThucSinhKhoaId = data[0].ht_sinhkhoa_id
          ? data[0].ht_sinhkhoa_id
          : 0;
      }
    },
    LoadThongTinHDTB_SUB: async function (hdtb_id) {
      var input = {
        hdtb_id: hdtb_id,
      };
      let data = this.GetData(await api.getThongTinHDSub(this.axios, input));
      if (data.length > 0) {
        this.leftForm.txtSoGT = data[0].so_gt ? data[0].so_gt : "";
        this.leftForm.dtpNgayHetHan = data[0].ngayhethan_gt
          ? moment(data[0].ngayhethan_gt).format("DD/MM/y")
          : "";
        this.leftForm.txtChucDanh = data[0].chucdanh ? data[0].chucdanh : "";
        this.leftForm.cboLoaiGT = data[0].loaigt_id ? data[0].loaigt_id : 0;
      }
    },
    validateThiThongTinHDTB: function (input) {
      if (!input.in_ma_gd || input.in_ma_gd.trim() == "") {
        this.ShowError("Mã giao dịch không được trống!");
        return false;
      }
      return true;
    },
    LayDonViDich: async function (donvi_ql_id, tb_id, hd_id) {
      this.ds_donvi = [];
      var input = {
        dichvuvt_id: this.leftForm.cboDichVuVT,
        tocdo: this.leftForm.cboGoiCuoc,
        muccuoc: this.muccuoctn_id,
        loaihinhtb_id: this.leftForm.cboLoaiHinhThueBao,
        kieuld_id: this.leftForm.cboKieuLD,
        loaidv_id: LOAI_DV.GIAODICH,
        loaihd_id: LoaiHopDong.KY_LAI_HD_GOC,
      };
      // var input = {
      //   dichvuvt_id: 2,
      //   tocdo: 105,
      //   muccuoc: 0,
      //   loaihinhtb_id: 20,
      //   kieuld_id: 2,
      //   loaidv_id: 4,
      //   loaihd_id: 1,
      // };
      let dt = this.GetData(await api.getDONVI_THEO_LOAIHD(this.axios, input));
      var dt1 = this.getDS_DONVI_TRAMTC_BANTHIETBI(donvi_ql_id);
      var dt2 = this.getDS_DONVI_THEO_LOAIDV_BANTHIETBI();
      var ds_dv_tramtc = await dt1;
      var ds_dv_loaidv = await dt2;
      if (dt.length > 0) {
        var data = [];
        data = dt.map((e) => {
          if (
            e.loaidv_dich_id == LOAI_DV.TRAM_VT ||
            e.loaidv_dich_id == LOAI_DV.TRAM_CAP_PORT
          ) {
            return {
              thutu_id: e.thutu_id,
              loaidv_dich_id: e.loaidv_dich_id,
              ten_loaidv: e.ten_loaidv,
              selected: 0,
              ds_dv: ds_dv_tramtc.map((e) => ({
                id: e.donvi_id,
                text: e.ten_dv,
              })),
            };
          } else {
            return {
              thutu_id: e.thutu_id,
              loaidv_dich_id: e.loaidv_dich_id,
              ten_loaidv: e.ten_loaidv,
              selected: 0,
              ds_dv: ds_dv_loaidv.map((e) => ({
                id: e.donvi_id,
                text: e.ten_dv,
              })),
            };
          }
        });

        if (tb_id > 0) {
          var dt3 = this.getDS_DBTB_DV(tb_id);
          var dbtb_dv = await dt3;
          if (dbtb_dv.length > 0) {
            dt.forEach((dr) => {
              var kiemtra = dbtb_dv.filter(
                (e) => e.loaidv_id == dr.loaidv_dich_id
              );
              if (kiemtra.length > 0) {
                data.map((e) => {
                  if (e.loaidv_dich_id == dr.loaidv_dich_id) {
                    e.selected = kiemtra[0].donvi_id;
                  }
                });
              }
            });
          }
        } else if (hd_id > 0) {
          var dt4 = this.getDS_HDTB_DV_CA(hd_id);
          var dbtb_dv = await dt4;
          if (dbtb_dv.length > 0) {
            dt.forEach((dr) => {
              var kiemtra = dbtb_dv.filter(
                (e) => e.loaidv_id == dr.loaidv_dich_id
              );
              if (kiemtra.length > 0) {
                data.map((e) => {
                  if (e.loaidv_dich_id == dr.loaidv_dich_id) {
                    e.selected = kiemtra[0].donvi_id;
                  }
                });
              }
            });
          }
        }
        this.ds_donvi = data;
      }
    },
    getDS_DONVI_TRAMTC_BANTHIETBI: async function (donvi_ql_id) {
      var input = {
        donviql_id: donvi_ql_id,
        loaidv_id: LOAI_DV.TRAM_VT,
      };
      // var input = {
      //   donviql_id: 7,
      //   loaidv_id: 5,
      // };
      var data = [];
      data = this.GetData(
        await api.getDS_DONVI_TRAMTC_BANTHIETBI(this.axios, input)
      );
      return data;
    },
    getDS_DONVI_THEO_LOAIDV_BANTHIETBI: async function () {
      var input = {
        loaidv_id: LOAI_DV.TRAM_VT,
      };
      var data = [];
      data = this.GetData(
        await api.getDS_DONVI_THEO_LOAIDV_BANTHIETBI(this.axios, input)
      );
      return data;
    },
    getDS_DBTB_DV: async function (thuebao_id) {
      var input = {
        thuebao_id: thuebao_id,
      };
      // var input = {
      //   thuebao_id: 274194,
      // };
      var data = [];
      data = this.GetData(await api.getDS_DBTB_DV(this.axios, input));
      return data;
    },
    getDS_HDTB_DV_CA: async function (hdtb_id) {
      var input = {
        hdtb_id: hdtb_id,
      };
      // var input = {
      //   hdtb_id: 1468300,
      // };
      var data = [];
      data = this.GetData(await api.getDS_HDTB_DV_CA(this.axios, input));
      return data;
    },
    LayLuongNghiepVu: async function () {
      var input = {
        kieuLdId: this.leftForm.cboKieuLD,
        loaiHdId: LoaiHopDong.KY_LAI_HD_GOC,
        loaiTbId: this.leftForm.cboLoaiHinhThueBao,
        mucCuocId: this.muccuoctn_id,
        tocDoId: this.leftForm.cboGoiCuoc,
      };
      let data = this.GetData(await api.getLuongNghiepVu(this.axios, input));
      if (data && data.length > 0) {
        this.vhuonggiao_id = data[0].huonggiao_id;
        this.vquytrinh_id = data[0].quytrinh_id;
        this.vluong_id = data[0].luong_id;
      } else {
        this.vhuonggiao_id = 0;
        this.vquytrinh_id = 0;
        this.vluong_id = 0;
        this.ShowError(
          "Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!"
        );
      }
    },
    CMSTimKiemThueBaoHienHuu: async function (
      serial,
      ten_chu_the,
      email,
      username,
      mst
    ) {
      var input = {
        chungchi_id: 1,
      };
      let data = this.GetData(await api.getds_chungchi_ssl(this.axios, input));
      if (data.length > 0) {
        var chung_chi_ssl_b64 = data[0].command.toString();
        var cts_ssl = atob(chung_chi_ssl_b64);
      }
    },
    capnhat_chuanhoaca: async function () {
      var vds = [
        {
          MA_TB: this.leftForm.txtMaTB,
          HUONGGIAO_ID: this.vhuonggiao_id,
          KHACHHANG_ID: this.vds_hdkh.KHACHHANG_ID,
          THUEBAO_ID: this.vds_hdtb.THUEBAO_ID,
          DICHVUVT_ID: this.cp_dichvuvt_id,
          NHANVIEN_ID: Number(this.$root.token.getNhanVienID()),
          DONVI_ID: Number(this.$root.token.getDonViID()),
          NGUOIDUNG_ID: Number(this.$root.token.getNguoiDungID()),
          MAY_CN: "localhost",
          IP_CN: "127.0.0.1",
        },
      ];
      var input = {
        isnhapmoi: this.isNhapMoi ? 1 : 0,
        vds: JSON.stringify(vds),
        vds_hdkh: JSON.stringify(this.vds_hdkh),
        vds_hdtb: JSON.stringify(this.vds_hdtb),
        vds_hdtb_sub: JSON.stringify(this.vds_hdtb_sub),
        vds_hdtb_cntt: JSON.stringify(this.vds_hdtb_cntt),
        vds_diachi_ld: JSON.stringify(this.vds_diachi_ld),
        vds_hdtb_dv: JSON.stringify(this.vds_hdtb_donvi),
      };
      let response = await api.capnhat_chuanhoaca(this.axios, input);
      if (response.data.error === 200 || response.data.error === "200") {
        var check = response.data.data.split("+");
        if (Number(check[0]) == 1) {
          if (this.isNhapMoi) {
            this.leftForm.txtMaGD = check[1];
            this.ShowSuccess("Thêm thành công!");
          } else {
            this.ShowSuccess("Cập nhật thành công!");
          }
          await this.HienThiThongTinHDTB(this.leftForm.txtMaGD);
        } else {
          this.ShowError(response.data.data);
        }
      } else {
        this.ShowError("Có lỗi trong quá trình cập nhật!");
      }
    },
    XoaHDKH: async function () {
      if (this.isNhapMoi) {
        this.ShowError("Không đúng trạng thái không thể xoá!");
        return;
      }
      var input = {
        vhdtb_id: this.danhsachHopDongThueBao.hdtb_id,
        vhdkh_id: this.danhsachHopDongThueBao.hdkh_id,
        vloaihd_id: LoaiHopDong.KY_LAI_HD_GOC,
      };
      let response = await api.xoa_hdkh_chuanhoaca(this.axios, input);
      if (response.data.error === 200 || response.data.error === "200") {
        var check = response.data.data.split("+");
        if (Number(check[0]) == 1) {
          this.ShowSuccess("Xóa thành công!");
          this.XoaForm();
        } else {
          this.ShowError(response.data.data);
        }
      } else {
        this.ShowError("Có lỗi khi xoá hợp đồng!");
      }
    },
    btnMaGD_click: function () {
      this.btnMaGD = true;
      this.btnMaTB = false;
      this.showModal();
    },
    btnMaTB_click: function () {
      this.btnMaGD = false;
      this.btnMaTB = true;
      this.showModal();
    },
    btnNhapMoi_Click: function () {
      this.XoaForm();
    },
    btnCapNhat_Click: async function () {
      this.loading(true);
      await this.CAPNHAT();
      this.loading(false);
    },
    btnXoaHopDong_Click: async function () {
      if (this.danhsachHopDongThueBao.hdtb_id) {
        this.$bvModal
          .msgBoxConfirm(
            "Bạn có muốn xóa hợp đồng cho thuê bao: " +
              this.leftForm.txtMaTB +
              "?",
            {
              title: "Thông báo",
              size: "sm",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          )
          .then(async (v) => {
            if (v) {
              this.loading(true);
              await this.XoaHDKH();
              this.loading(false);
            }
          })
          .catch((e) => {
            this.ShowError(e);
            this.loading(false);
          });
      } else {
        this.ShowError("Bạn chưa chọn hợp đồng thuê bao để xóa!");
      }
    },
    btnUpHoSo_Click: function () {
      if (!this.danhsachHopDongThueBao || this.danhsachHopDongThueBao == {}) {
        this.ShowError("Bạn chưa chọn thuê bao nào");
        return;
      }
      if (this.leftForm.txtMaTB && this.leftForm.txtMaTB != "")
        // this.$refs.dialogObjHoSo.show();
        this.$bvModal.show("frmUploadEProfile");
      else {
        this.ShowError("Bạn chưa nhập mã thuê bao");
      }
    },
    btnGiaoPhieu_Click: function () {
      this.$bvModal
        .msgBoxConfirm("Bạn phải xem lại hồ sơ trước khi giao phiếu!", {
          title: "Thông báo!",
          size: "m",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            if (
              !this.leftForm.txtMaGD ||
              !this.danhsachHopDongThueBao.hdtb_id
            ) {
              this.ShowError("Bạn chưa chọn hợp đồng, không thể giao phiếu!");
              return;
            }

            if (this.vhuonggiao_id == 0 || this.vluong_id == 0) {
              this.ShowError(
                "Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!"
              );
              return;
            }
            this.loading(true);
            UploadEProfileAPI.get_danhsach_hopdong_theo_matb(this.axios, {
              ma_tb: this.leftForm.txtMaTB,
            }).then((response) => {
              this.loading(false);
              if (
                response.data.error_code &&
                response.data.error_code === this.success_code
              ) {
                this.$router.push({
                  path: "HandoverTicket",
                  query: {
                    magd: this.leftForm.txtMaGD,
                    madv: this.leftForm.cboDichVuVT,
                    huonggiao_id: this.vhuonggiao_id,
                    hdtb_id: this.danhsachHopDongThueBao.hdtb_id,
                    luong_id: this.vluong_id,
                  },
                });
                this.isNhapMoi = true;
                this.XoaForm();
              } else {
                this.ShowError("Không tìm thấy upload hồ sơ thuê bao!");
              }
            });
          }
        })
        .catch((e) => {
          this.ShowError(e);
          this.loading(false);
        });
    },
    btnDongBo_Click: function () {
      this.DongBoDuLieu();
    },
    btnLamMoiCMS_Click: function () {
      this.rightForm = {
        txtCMSUserName: "",
        txtCMSMaSoThue: "",
        txtCMSSerial: "",
        txtCMSTenChuThe: "",
        txtCMSSoDT: "",
        txtCMSEmail: "",
        dtpCMSNgayBD: "",
        dtpCMSNgayKT: "",
        txtGoiCuocCMS: "",
        txtCMSSoThang: 0,
        txtCMSLoaiGT: "",
        txtCMSSoGT: 0,
        txtMucDichCMS: "",
        txtCMSDoDaiKhoa: "",
        txtIDThucTheCMS: 0,
        txtIDChungThuSoCMS: 0,
        txtCMSDiaChi: "",
      };
      this.CheckedBoxs = {
        ckbDongBoCMSUserName: false,
        ckbDongBoSerial: false,
        ckbDongBoTenChuThe: false,
        ckbDongBoSDT: false,
        ckbDongBoEmail: false,
        ckbDongBoNgayBD: false,
        ckbDongBoNgayKT: false,
        ckbDongBoSoThang: false,
        ckbDongBoIdThucThe: false,
        cbkDongBoIDCTS: false,
      };
      this.grcChungThuSoCMS.list = [];
      this.grcThucTheCMS.DataSource = [];
    },
    TaoDuLieu: async function () {
      this.vds_hdkh = {};
      this.vds_hdtb = {};
      this.vds_hdtb_sub = {};
      this.vds_hdtb_cntt = {};
      this.vds_diachi_ld = {};
      this.vds_hdtb_donvi = [];
      if (this.isNhapMoi) {
        var promiseKhachHang = this.get_keys("HD_KHACHHANG");
        var promiseHDTB = this.get_keys("HD_THUEBAO");
        var promiseDIACHI = this.get_keys("DIACHI");
        var promiseMaGD = this.SINH_MAGD_CHUANHOA_CA(
          Number(this.$root.token.getDonViID()),
          24
        );
        var hdkh_id = Number(await promiseKhachHang);
        var hdtb_id = Number(await promiseHDTB);
        var ma_gd = await promiseMaGD;
        var diachi_id = Number(await promiseDIACHI);

        var vds_hdkh = {
          HDKH_ID: hdkh_id,
          MA_GD: ma_gd,
          KHACHHANG_ID: this.danhbaKH.khachhang_id,
        };

        var vds_hdtb = {
          HDTB_ID: hdtb_id,
          HDKH_ID: vds_hdkh.HDKH_ID,
          MA_TB: this.leftForm.txtMaTB,
          TEN_TB: this.leftForm.txtTenTB,
          THUEBAO_ID: this.danhbaKH.thuebao_id,
          DIACHI_LD: this.diachiLD.DIACHI,
          QUYTRINH_ID: this.vquytrinh_id,
        };

        var vds_hdtb_sub = {
          HDTB_ID: hdtb_id,
          CHUC_DANH: this.leftForm.txtChucDanh,
          LOAIGT_ID: Number(this.leftForm.cboLoaiGT),
          SO_GT: this.leftForm.txtSoGT,
          NGAYHETHAN_GT: this.leftForm.dtpNgayHetHan,
        };

        var vds_hdtb_cntt = {
          HDTB_ID: hdtb_id,
          SERIAL: this.leftForm.txtSerial,
          MST: this.leftForm.txtMST,
          NGAY_BD: this.leftForm.dtpNgayBD,
          NGAY_KT: this.leftForm.dtpNgayKT,
          SO_DT: this.leftForm.txtSDT,
          SO_THANG: Number(this.leftForm.txtSoThangSD),
          USER_NAME: this.leftForm.txtUserName,
          EMAIL: this.leftForm.txtEmail,
          EE_ID: Number(this.leftForm.txtThucTheID),
          CTS_ID: Number(this.leftForm.txtCTSID),
          TOCDO_ID: Number(this.leftForm.cboGoiCuoc),
          MUCDICH_IDS: this.leftForm.cboMucDichCTS,
          HT_SINHKHOA_ID: Number(this.leftForm.cboHinhThucSinhKhoaId),
          LOAICTS_ID: Number(this.leftForm.cboLoaiCTSId),
        };

        var vds_diachi_ld = {
          DIACHI_ID: diachi_id,
          AP_ID: this.diachiLD.AP_ID,
          DACDIEM_ID: this.diachiLD.DACDIEM_ID,
          DIACHI: this.diachiLD.DIACHI,
          KHU_ID: this.diachiLD.KHU_ID,
          PHO_ID: this.diachiLD.PHO_ID,
          PHUONG_ID: this.diachiLD.PHUONG_ID,
          QUAN_ID: this.diachiLD.QUAN_ID,
          SONHA: this.diachiLD.SONHA,
          TINH_ID: this.diachiLD.TINH_ID,
          VIDO: this.diachiLD.Lat,
          KINHDO: this.diachiLD.Lng,
        };

        var vds_hdtb_donvi = this.ds_donvi.map((e) => ({
          DONVI_ID: Number(e.selected),
          HDTB_ID: hdtb_id,
          LOAIDV_ID: Number(e.loaidv_dich_id),
          KIEUDV_ID: 2,
        }));

        this.vds_hdkh = vds_hdkh;
        this.vds_hdtb = vds_hdtb;
        this.vds_hdtb_sub = vds_hdtb_sub;
        this.vds_hdtb_cntt = vds_hdtb_cntt;
        this.vds_diachi_ld = vds_diachi_ld;
        this.vds_hdtb_donvi = vds_hdtb_donvi;
        this.isNhapMoi = false;
      } else {
        var promiseDIACHI = this.get_keys("DIACHI");

        var vds_hdkh = {
          HDKH_ID: this.danhsachHopDongThueBao.hdkh_id,
          MA_GD: this.danhsachHopDongThueBao.ma_gd,
          KHACHHANG_ID: this.danhsachHopDongThueBao.khachhang_id,
        };

        var vds_hdtb = {
          HDTB_ID: this.danhsachHopDongThueBao.hdtb_id,
          HDKH_ID: vds_hdkh.HDKH_ID,
          MA_TB: this.leftForm.txtMaTB,
          TEN_TB: this.leftForm.txtTenTB,
          THUEBAO_ID: this.danhsachHopDongThueBao.thuebao_id,
          DIACHI_LD: this.diachiLD.DIACHI,
          QUYTRINH_ID: this.vquytrinh_id,
        };

        var vds_hdtb_sub = {
          HDTB_ID: this.danhsachHopDongThueBao.hdtb_id,
          CHUC_DANH: this.leftForm.txtChucDanh,
          LOAIGT_ID: Number(this.leftForm.cboLoaiGT),
          SO_GT: this.leftForm.txtSoGT,
          NGAYHETHAN_GT: this.leftForm.dtpNgayHetHan,
        };

        var vds_hdtb_cntt = {
          HDTB_ID: this.danhsachHopDongThueBao.hdtb_id,
          SERIAL: this.leftForm.txtSerial,
          MST: this.leftForm.txtMST,
          NGAY_BD: this.leftForm.dtpNgayBD,
          NGAY_KT: this.leftForm.dtpNgayKT,
          SO_DT: this.leftForm.txtSDT,
          SO_THANG: Number(this.leftForm.txtSoThangSD),
          USER_NAME: this.leftForm.txtUserName,
          EMAIL: this.leftForm.txtEmail,
          EE_ID: Number(this.leftForm.txtThucTheID),
          CTS_ID: Number(this.leftForm.txtCTSID),
          TOCDO_ID: Number(this.leftForm.cboGoiCuoc),
          MUCDICH_IDS: this.leftForm.cboMucDichCTS,
          HT_SINHKHOA_ID: Number(this.leftForm.cboHinhThucSinhKhoaId),
          LOAICTS_ID: Number(this.leftForm.cboLoaiCTSId),
        };

        var vds_diachi_ld = {
          DIACHI_ID: Number(await promiseDIACHI),
          AP_ID: this.diachiLD.AP_ID,
          DACDIEM_ID: this.diachiLD.DACDIEM_ID,
          DIACHI: this.diachiLD.DIACHI,
          KHU_ID: this.diachiLD.KHU_ID,
          PHO_ID: this.diachiLD.PHO_ID,
          PHUONG_ID: this.diachiLD.PHUONG_ID,
          QUAN_ID: this.diachiLD.QUAN_ID,
          SONHA: this.diachiLD.SONHA,
          TINH_ID: this.diachiLD.TINH_ID,
          VIDO: this.diachiLD.Lat,
          KINHDO: this.diachiLD.Lng,
        };

        var vds_hdtb_donvi = this.ds_donvi.map((e) => ({
          DONVI_ID: Number(e.selected),
          HDTB_ID: this.danhsachHopDongThueBao.hdtb_id,
          LOAIDV_ID: Number(e.loaidv_dich_id),
          KIEUDV_ID: 2,
        }));

        this.vds_hdkh = vds_hdkh;
        this.vds_hdtb = vds_hdtb;
        this.vds_hdtb_sub = vds_hdtb_sub;
        this.vds_hdtb_cntt = vds_hdtb_cntt;
        this.vds_diachi_ld = vds_diachi_ld;
        this.vds_hdtb_donvi = vds_hdtb_donvi;
        this.isNhapMoi = false;
      }
    },
    KiemTraThemMoi: function () {
      if (this.danhbaKH.khachhang_id == 0 || this.danhbaKH.thuebao_id == 0) {
        this.ShowError("Chưa có dữ liệu khách hàng không thể ghi lại!");
        return false;
      }
    },
    CAPNHAT: async function () {
      if (!this.vldCAChuanHoa()) {
        this.ShowError("Dữ liệu chưa hợp lệ!");
        return;
      }
      if (this.ds_donvi.length > 0) {
        this.ds_donvi.forEach((e) => {
          if (e.selected == 0) this.ShowError("Chưa chọn đơn vị đích!");
          return;
        });
      } else {
        this.ShowError("Đơn vị đích không tồn tại!");
        return;
      }

      await this.TaoDuLieu();
      if (this.isNhapMoi && !this.KiemTraThemMoi()) return;
      await this.capnhat_chuanhoaca();
    },
    vldCAChuanHoa: function () {
      if (this.leftForm.txtUserName.trim() == "") {
        this.ShowError("Bạn không được để trống trường Username!");
        return false;
      } else {
        if (
          this.CheckedBoxs.ckbDongBoCMSUserName &&
          this.leftForm.txtUserName.toLowerCase().trim() !=
            this.rightForm.txtCMSUserName.toLowerCase().trim()
        ) {
          this.ShowError("Không khớp thông tin Username với CMS!");
          return false;
        }
      }
      if (this.leftForm.txtCTSID == 0) {
        this.ShowError("Bạn không được để trống trường Chứng thư số ID!");
        return false;
      } else {
        if (
          this.CheckedBoxs.cbkDongBoIDCTS &&
          this.leftForm.txtCTSID.toString().toLowerCase().trim() !=
            this.rightForm.txtIDChungThuSoCMS.toString().toLowerCase().trim()
        ) {
          this.ShowError("Không khớp thông tin chứng thư số ID với CMS!");
          return false;
        }
      }
      if (this.leftForm.txtThucTheID == 0) {
        this.ShowError("Bạn không được để trống trường ID thực thể!");
        return false;
      } else {
        if (
          this.CheckedBoxs.ckbDongBoIdThucThe &&
          this.leftForm.txtThucTheID.toString().toLowerCase().trim() !=
            this.rightForm.txtIDThucTheCMS.toString().toLowerCase().trim()
        ) {
          this.ShowError("Không khớp thông tin ID thực thể với CMS!");
          return false;
        }
      }
      if (this.leftForm.txtSerial.trim() == "") {
        this.ShowError("Bạn không được để trống trường Serial!");
        return false;
      } else {
        if (
          this.CheckedBoxs.ckbDongBoSerial &&
          this.leftForm.txtSerial.toString().toLowerCase().trim() !=
            this.rightForm.txtCMSSerial.toString().toLowerCase().trim()
        ) {
          this.ShowError("Không khớp thông tin Serial với CMS!");
          return false;
        }
      }
      if (this.leftForm.txtTenTB.trim() == "") {
        this.ShowError("Bạn không được để trống trường tên thuê bao!");
        return false;
      } else {
        if (
          this.CheckedBoxs.ckbDongBoTenChuThe &&
          this.leftForm.txtTenTB.toString().toLowerCase().trim() !=
            this.rightForm.txtCMSTenChuThe.toString().toLowerCase().trim()
        ) {
          this.ShowError(
            "Không khớp thông tin tên thuê bao với tên chủ thể CMS!"
          );
          return false;
        }
      }
      if (this.leftForm.dtpNgayBD == "") {
        this.ShowError("Bạn không được để trống trường ngày bắt đầu!");
        return false;
      } else {
        if (
          this.CheckedBoxs.ckbDongBoNgayBD &&
          moment(this.leftForm.dtpNgayBD)
            .format("DD/MM/y")
            .toLowerCase()
            .trim() !=
            moment(this.rightForm.dtpCMSNgayBD)
              .format("DD/MM/y")
              .toLowerCase()
              .trim()
        ) {
          this.ShowError("Không khớp thông tin ngày bắt đầu!");
          return false;
        }
      }
      if (this.leftForm.dtpNgayKT == "") {
        this.ShowError("Bạn không được để trống trường ngày kết thúc!");
        return false;
      } else {
        if (
          this.CheckedBoxs.ckbDongBoNgayKT &&
          moment(this.leftForm.dtpNgayKT)
            .format("DD/MM/y")
            .toLowerCase()
            .trim() !=
            moment(this.rightForm.dtpCMSNgayKT)
              .format("DD/MM/y")
              .toLowerCase()
              .trim()
        ) {
          this.ShowError("Không khớp thông tin ngày kết thúc!");
          return false;
        }
      }
      if (this.leftForm.txtEmail.trim() == "") {
        this.ShowError("Bạn không được để trống trường email!");
        return false;
      } else {
        if (
          this.CheckedBoxs.ckbDongBoEmail &&
          this.leftForm.txtEmail.toString().toLowerCase().trim() !=
            this.rightForm.txtCMSEmail.toString().toLowerCase().trim()
        ) {
          this.ShowError("Không khớp thông tin email!");
          return false;
        }
      }
      if (this.leftForm.txtSDT.trim() == "") {
        this.ShowError("Bạn không được để trống trường số điện thoại!");
        return false;
      } else {
        if (
          this.CheckedBoxs.ckbDongBoSDT &&
          this.leftForm.txtSDT.toString().toLowerCase().trim() !=
            this.rightForm.txtCMSSoDT.toString().toLowerCase().trim()
        ) {
          this.ShowError("Không khớp thông tin số điện thoại!");
          return false;
        }
      }
      if (this.leftForm.cboHinhThucSinhKhoaId == 0) {
        this.ShowError("Bạn không được để trống trường hình thức sinh khóa!");
        return false;
      }
      if (this.leftForm.cboLoaiCTSId == 0) {
        this.ShowError("Bạn không được để trống trường loại chứng thư số!");
        return false;
      }
      return true;
    },
    DongBoDuLieu: function () {
      if (this.CheckedBoxs.ckbDongBoCMSUserName)
        this.leftForm.txtUserName = this.rightForm.txtCMSUserName;
      if (this.CheckedBoxs.ckbDongBoEmail)
        this.leftForm.txtEmail = this.rightForm.txtCMSEmail;
      if (this.CheckedBoxs.ckbDongBoIdThucThe)
        this.leftForm.txtThucTheID = this.rightForm.txtIDThucTheCMS;
      if (this.CheckedBoxs.ckbDongBoNgayBD)
        this.leftForm.dtpNgayBD = this.rightForm.dtpCMSNgayBD;
      if (this.CheckedBoxs.ckbDongBoNgayKT)
        this.leftForm.dtpNgayKT = this.rightForm.dtpCMSNgayKT;
      if (this.CheckedBoxs.ckbDongBoSDT)
        this.leftForm.txtSDT = this.rightForm.txtCMSSoDT;
      if (this.CheckedBoxs.ckbDongBoSerial)
        this.leftForm.txtSerial = this.rightForm.txtCMSSerial;
      if (this.CheckedBoxs.ckbDongBoSoThang)
        this.leftForm.txtSoThangSD = this.rightForm.txtCMSSoThang;
      if (this.CheckedBoxs.ckbDongBoTenChuThe)
        this.leftForm.txtTenTB = this.rightForm.txtCMSTenChuThe;
      if (this.CheckedBoxs.cbkDongBoIDCTS)
        this.leftForm.txtCTSID = this.rightForm.txtIDChungThuSoCMS;
    },
    XoaForm: function () {
      this.grcThueBao.list = [];
      Object.keys(this.leftForm).forEach((e) => {
        if (
          e != "txtMaGD" &&
          e != "txtMaTB" &&
          e != "cboKieuLD" &&
          e != "cboDichVuVT" &&
          e != "cboLoaiHinhThueBao"
        ) {
          this.leftForm[e] = "";
        }
      });
      this.KhoiTaoDoiTuong();
    },
    KhoiTaoDoiTuong: function () {
      this.isNhapMoi = true;
      this.thuebao_id = 0;
      this.hdtb_id = 0;
      this.muccuoctn_id = 0;
      this.vquytrinh_id = 0;
      this.vhuonggiao_id = 0;
      this.vluong_id = 0;
      this.danhbaKH = {};
      this.danhsachHopDongThueBao = {};
      this.diachiLD = {
        DIACHI: "",
        DIACHI_ID: 0,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        SO_NHA: "",
        Lng: 0,
        Lat: 0,
        BLOCK: "",
        TANG: 0,
        PHONG: 0,
        MOTA: "",
      };
    },
    SINH_MAGD_CHUANHOA_CA: async function (donvi_id, loaihd_id) {
      var input = {
        vdonvi_id: donvi_id,
        vloaihd_id: loaihd_id,
      };
      let data = this.GetData(
        await api.sinh_magd_chuanhoa_ca(this.axios, input)
      );
      if (data && data.length > 0) {
        return data;
      } else {
        return "";
      }
    },
    get_keys: async function (keyname) {
      var input = {
        keyname: keyname,
      };
      let data = this.GetData(await api.get_keys(this.axios, input));
      return data;
    },
    selectedHDTB_click: function (data) {
      if (data && data.length > 0) {
        this.danhsachHopDongThueBao = data[0];
        this.leftForm.txtMaGD = data[0].ma_gd ? data[0].ma_gd : "";
        this.leftForm.txtMaKH = data[0].ma_kh ? data[0].ma_kh : "";
        this.leftForm.txtTenKH = data[0].ten_kh ? data[0].ten_kh : "";
        this.leftForm.dtpNgayYeuCau = data[0].ngay_yc
          ? moment(data[0].ngay_yc).format("DD/MM/y")
          : "";
        this.leftForm.dtpNgayLapHD = data[0].ngaylap_hd
          ? moment(data[0].ngaylap_hd).format("DD/MM/y")
          : "";
        this.leftForm.txtMaHD = data[0].ma_hd ? data[0].ma_hd : "";
        this.leftForm.txtDiaChiKH = data[0].diachi_kh ? data[0].diachi_kh : "";
        this.leftForm.txtTenTB = data[0].ten_tb ? data[0].ten_tb : "";
        this.leftForm.txtMaTB = data[0].ma_tb ? data[0].ma_tb : "";
        this.leftForm.txtDiaChiLD = data[0].diachi_ld ? data[0].diachi_ld : "";
        this.diachiLD.DIACHI = data[0].diachi_ld ? data[0].diachi_ld : "";
        this.leftForm.txtTrangThaiTB = data[0].trangthai_hd
          ? data[0].trangthai_hd
          : "";

        if (!this.getDSDiaChi_HDTBID(this.danhsachHopDongThueBao.hdtb_id))
          return;
        this.LoadThongTinHDTBCNTT(this.danhsachHopDongThueBao.hdtb_id);
        this.LoadThongTinHDTB_SUB(this.danhsachHopDongThueBao.hdtb_id);
        this.LayDonViDich(
          this.danhsachHopDongThueBao.donvi_id,
          0,
          this.danhsachHopDongThueBao.hdtb_id
        );
        this.LayLuongNghiepVu();
        this.isNhapMoi = false;
      }
    },
    timthongtinHD: async function (data) {
      this.loading(true);
      if (this.btnMaGD && data.ma_gd) {
        this.leftForm.txtMaGD = data.ma_gd;
        await this.HienThiThongTinHDTB(data.ma_gd);
      }
      if (this.btnMaTB && data.ma_tb) {
        this.XoaForm();
        this.leftForm.txtMaTB = data.ma_tb;
        this.leftForm.txtMaGD = "";
        await this.HienThiThongTinDBKH(data.ma_tb);
      }
      this.loading(false);
    },
    showModal() {
      this.$refs.ModalNormalizeCAinformation.showModal();
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === "200") {
        return response.data.data;
      } else {
        // console.log(response.data.message);
        // this.ShowError(response.data.message);
      }
      return [];
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
  },
  computed: {
    cp_dichvuvt_id: {
      get() {
        return this.leftForm.cboDichVuVT;
      },
      set(value) {
        return (this.leftForm.cboDichVuVT = value);
      },
    },
    cp_loaitb_id: {
      get() {
        return this.leftForm.cboLoaiHinhThueBao;
      },
      set(value) {
        return (this.leftForm.cboLoaiHinhThueBao = value);
      },
    },
  },
  watch: {
    cp_dichvuvt_id: {
      handler: async function (newValue, oldValue) {
        if (this.ds_loaihinhTB_all.length > 0) {
          this.ds_loaihinhTB = this.ds_loaihinhTB_all
            .filter((e) => e.DICHVUVT_ID == newValue)
            .map((th) => ({
              id: th.LOAITB_ID,
              text: th.LOAIHINH_TB,
            }));
          this.cp_loaitb_id =
            this.ds_loaihinhTB.length > 0 ? this.ds_loaihinhTB[0].id : 0;
        }
      },
    },
    cp_loaitb_id: {
      handler: async function (newValue, oldValue) {
        this.loading(true);
        if (newValue && newValue != oldValue) {
          this.getDSGoiCuoc(newValue);
          this.getDSHTSinhKhoa(newValue);
        }
        this.loading(false);
      },
    },
  },
};
</script>
<style>
.hoso-dientu .page-content {
  position: unset;
}
</style>