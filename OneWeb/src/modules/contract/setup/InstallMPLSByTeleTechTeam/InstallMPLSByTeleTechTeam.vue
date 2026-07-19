<template src="./InstallMPLSByTeleTechTeam.html"></template>
<style src="./InstallMPLSByTeleTechTeam.scss"></style>

<style src="../../../../assets/vendor/font-awesome/css/font-awesome.min.css"></style>
<style src="../../../../assets/vendor/ap8/css/style.css"></style>
<style src="../../../../assets/vendor/nucleo/outline/css/style.css"></style>
<style src="../../../../assets/vendor/nucleo/glyph/css/style.css"></style>
<style src="../../../../assets/vendor/bootstrap/css/bootstrap.min.css"></style>
<style src="../../../../assets/vendor/bootstrap-table/bootstrap-table.min.css"></style>
<style src="../../../../assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css"></style>
<style src="../../../../assets/vendor/select2/css/select2.min.css"></style>
<style src="../../../../assets/vendor/oneicon/style.css"></style>
<style src="../../../../assets/css/style.css"></style>

<script src="../../../../assets/vendor/jquery/jquery-3.5.1.slim.min.js"></script>
<script src="../../../../assets/vendor/jquery/moment.min.js"></script>
<script src="../../../../assets/vendor/jquery/popper.min.js"></script>
<script src="../../../../assets/vendor/select2/js/select2.min.js"></script>
<script src="../../../../assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="../../../../assets/vendor/highchart/highcharts.js"></script>
<script src="../../../../assets/vendor/bootstrap-table/bootstrap-table.min.js"></script>
<script src="../../../../assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min.js"></script>
<script src="../../../../assets/vendor/jquery/split.js"></script>
<script src="../../../../assets/js/custom.js"></script>

<script>
import ThongTinChung from "./part/ThongTinChung";
import ThongTinKhachHang from "./part/ThongTinKhachHang";
import ThongTinGiaoViec from "./part/ThongTinGiaoViec";
import ThongTinHoanCong_DD from "./part/ThongTinHoanCong_DD";
import ThongTinHoanCong_DC from "./part/ThongTinHoanCong_DC";
import DanhSachThueBao from "./part/DanhSachThueBao";
import installMPLSByTeleTechTeamModule from "./installMPLSByTeleTechTeamModuleStore";
import HandoverTicket from "../HandoverTicket/HandoverTicket.vue";
import traphieu from "@/modules/contract/setup/ReturnInstallTicket/components/traphieu.vue";
import ChuyenTo from "@/modules/contract/profile/CompleteProfileChangeAccount/Popups/ChuyenTo.vue";

import TonPhieuGiaoModal from "@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/TonPhieuGiao/TonPhieuGiaoModal.vue";

import DoKiemModal from "@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/DoKiem/DoKiemModal.vue";

import giaophieunhanvien from "@/modules/contract/setup/DeclareLandline/popup/giaophieunhanvien.vue";

import PopupGanKhuVucQuanLyThueBao from "@/modules/contract/setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao.vue";

import PopupThongTinHenKhachHang from "@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue";

// import DauNoiThueBao from "@/modules/CABMAN/DauNoiThueBao/index.vue"
import Vue from "vue";
import * as moment from "moment";

import { config, PHAILAM, TrangThaiHD, LoaiHopDong } from "./constants";
export default {
  props: ["tag"],
  components: {
    ThongTinChung,
    ThongTinKhachHang,
    ThongTinGiaoViec,
    ThongTinHoanCong_DD,
    ThongTinHoanCong_DC,
    DanhSachThueBao,
    HandoverTicket,
    traphieu,
    ChuyenTo,
    TonPhieuGiaoModal,
    DoKiemModal,
    giaophieunhanvien,
    PopupGanKhuVucQuanLyThueBao,
    PopupThongTinHenKhachHang,
  },
  data() {
    return {
      items: [
        {
          text: "Cut",
          iconCss: "e-db-icons e-cut",
        },
        {
          text: "Copy",
          iconCss: "e-icons e-copy",
        },
        {
          text: "Paste",
          iconCss: "e-db-icons e-paste",
        },
        {
          separator: true,
        },
        {
          text: "Font",
          iconCss: "e-db-icons e-font",
        },
        {
          text: "Paragraph",
          iconCss: "e-db-icons e-paragraph",
        },
      ],
      isDisplayMenu: false,

      header: {
        title: "Xây dựng chức năng Tổ KTVT - Thi công Truyền số liệu",
        list: [
          //  HĐ chưa phân ĐV -> Khai báo Truyền số liệu
          { name: "Cập nhật KQTC", link: { name: "Ui.cards" } },
          {
            name: "Tổ KTVT -Thi công Truyền số liệu",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      isEnterMaGD: false,
      showModalTraphieu: false,
      popupGanKhuVucQuanLyThueBao: false,
      kieu: 0,
      form_button: {
        btnLayTTMoi: true,
        tsbtnChapNhan: true,
        tsbtnGiaoViec: true,
        tsbtnGiaoPhieu: true,
        isTsbtnGiaoPhieu: true,
        tsddbIn: true,
        btnPhieuTC: true,
        btnBienBanNT: true,
        tsbtnHoanThanh: true,
        isTsbtnHoanThanh: true,
        tsbtnHoanCong: true,
        isTsbtnHoanCong: true,
        tsbtnTraPhieu: true,
        isTsbtnTraPhieu: true,
        tsbtnVatTu: true,
        tsbtnHen: true,
        tsbtnDauNoiCap: true,
        tsbtnHuy_HC: false,
        tsbtnNVKT: true,
        isTsbtnVatTu: true,
        tsbtnVatTuMoi: true,
        tsbtnGiaoPhieu_KBB: true,
        isTsbtnGiaoPhieu_KBB: true,
        isTsbtnDuAn: true,
        tsbtnDuAn: true,
        tsbtnThongTinKT: true,
        tsbtnKQ_DoKiem: true,
        isTsbtnChuyenTo: true,
        tsbtnChuyenTo: true,
        tsbtnSua_KenhThu: true,
        isTsbtnSua_KenhThu: true,
        tsbtnGiaoPhieu_Ton: true,
        tsbtnHoanThienHS: true,
        isTsbtnHoanThienHS: true,
      },
      vloai_id: 0,
      huonggiao_id: null,
      luong_id: null,
      tramtb_dau_id: 0,
      port_dau_id: 0,
      port_dau_id_tmp: 0,
      vci_vpi_dau_id: 0,
      tramtb_dau_id: 0,
      vci_vpi_dau_id_tmp: 0,

      tramtb_cuoi_id: 0,
      port_cuoi_id: 0,
      port_cuoi_id_tmp: 0,
      vci_vpi_cuoi_id: 0,
      tramtb_cuoi_id: 0,
      vci_vpi_cuoi_id_tmp: 0,
      danhSachThaoTac: [],
      form_ThongTinChung: {
        txtNV_TiepThi: null,
        cboKieu_HD: null,
        cboKenhThu: null,
        txtTongTien: null,
      },
      options_thongTinChung: {
        options_kieuHD: [],
        options_kenhThu: [],
      },
      form_ThongTinKhachHang: {
        isVisible: true,
        chkHen: null,
        cboLoaiHD: null,
        cboQuyTrinh: null,
        txtMaGD: null,
        txtMaTB: null,
        txtTenKieuLD: null,
        cboLoaiHinh: null,
        cboTocDo: null,
        txtTenTB: null,
        cboChuQuan: null,
        chkDoiTac: null,
        cboDoiTac: null,
        txtDiaChiTB: null,
        phieu_id: null,
        nhanvien_id: null,
        hdkh_id: null,
        hdtb_id: null,
      },
      options_ThongTinKhachHang: {
        options_loaiHD: [],
        options_quyTrinh: [],
        options_loaiHinh: [],
        options_tocDoKenh: [],
        options_chuQuan: [],
        options_doiTac: [],
      },
      form_ThongTinGiaoViec: {
        dischkNgayGV: false,
        chkNgayGV: false,
        disNgayGV: true,
        dtpNgayGV: null,

        dischkNgayBG: false,
        chkNgayBG: false,
        disNgayBG: true,
        dtpNgayBG: null,

        cboNguoiGV: null,
        txtNhanVien: null,
        table_DSNV: {
          currentRecord: null,
          data: null,
          loading: false,
          options: [],
          config: [
            {
              label: "Tên nhân viên",
              filterAble: true,
              key: "ten_nv",
            },
            {
              label: "Số điện thoại",
              filterAble: true,
              key: "so_dt",
            },
            {
              label: "Nhiệm vụ",
              filterAble: true,
              key: "nhiemvu",
            },
            {
              label: "Ghi chú",
              filterAble: true,
              key: "ghichu",
            },
          ],
        },
      },
      options_ThongTinGiaoViec: {
        nguoiGV: [],
      },
      isVisibleThongTinHoanCong: true,
      form_ThongTinHoanCong_DD: {
        txtPort_Dau: "",
        isTxtTimeSlot_Dau: false,
        isTxtTBi_Dau: false,
        isTxtIpwan_Dau: false,
        isTxtVrf_Dau: false,
        _isTxtLink_Dau: false,

        isTxtTuCapGoc_Dau: false,
        isTxtDoiCapGoc_Dau: false,
        isTxtCapGoc_Dau: false,
        isTxtHopCapNgon_Dau: false,
        isTxtTuCapNgon_Dau: false,
        isTxtDoiCapNgon_Dau: false,
        isTxtCapNgon_Dau: false,
        isTxtTuyenCap_Dau: false,
        isTxtLienTu_Dau: false,
        isTxtSoMetDay_Dau: false,
        isTxtSerial_Dau: false,
        isTxtSvlan_Dau: false,
        isTxtCvlan_Dau: false,
        isTxtAdsl_cap_Dau: false,
        isTxtAdsl_port_Dau: false,
        isTxtNasport_id_Dau: false,
        isTxtIpDslam_Dau: false,
        lblLienHe_Dau: "",

        isDisplayTable: false,
        txtSoAoDau: null,
        cboThietBiDau: null,
        txtSerial_Dau: null,
        cboLoaiCap_Dau: null,
        txtSvlan_Dau: null,
        txtCvlan_Dau: null,
        txtCapGoc_Dau: null,
        capNgtxtCapNgon_Dauon: null,
        txtAdsl_cap_Dau: null,
        txtAdsl_port_Dau: null,
        txtTuCapGoc_Dau: null,
        txtTuCapNgon_Dau: null,
        txtNasport_id_Dau: null,
        txtVrf_Dau: null,
        txtDoiCapGoc_Dau: null,
        txtDoiCapNgon_Dau: null,
        txtIpDslam_Dau: null,
        txtLienTu_Dau: null,
        txtHopCapNgon_Dau: null,
        txtTBi_Dau: null,
        txtIpwan_Dau: null,
        txtSoMetDay_Dau: 0,
        txtTuyenCap_Dau: null,
        txtTimeSlot_Dau: null,
        txtLink_Dau: null,
        cboDonViDau: null,
        txtDiaChiDau: null,
        txtDiaChiDau_Cu: null,
        txtLyDoTra_Dau: null,
        txtNoiDungTra_Dau: null,
        txtNoiDungTH: null,
        table: {
          currentRecord: null,
          data: null,
          loading: false,
          options: [],
          config: [
            {
              label: "PORTVL_ID",
              filterAble: true,
              key: "portvl_id",
            },
            {
              label: "Link",
              filterAble: true,
              key: "link_code",
            },
            {
              label: "Thiết bị đích",
              filterAble: true,
              key: "tendslam",
            },
            {
              label: "Tham số",
              filterAble: true,
              key: "thamso",
            },
            {
              label: "Loại thiết bị đích",
              filterAble: true,
              key: "loai_tbi",
            },
          ],
        },
      },
      form_ThongTinHoanCong_DC: {
        txtPort_Cuoi: null,
        isTxtTimeSlot_Cuoi: false,
        isTxtTbi_Cuoi: false,
        isTxtIpwan_Cuoi: false,
        isTxtVrf_Cuoi: false,
        isTxtLink_Cuoi: false,

        isTxtTuCapGoc_Cuoi: false,
        isTxtDoiCapGoc_Cuoi: false,
        isTxtCapGoc_Cuoi: false,
        isTxtHopCapNgon_Cuoi: false,
        isTxtTuCapNgon_Cuoi: false,
        isTxtDoiCapNgon_Cuoi: false,
        isTxtCapNgon_Cuoi: false,
        isTxtTuyenCap_Cuoi: false,
        isTxtLienTu_Cuoi: false,
        isTxtSoMetDay_Cuoi: false,
        isTxtSerial_Cuoi: false,
        isTxtSvlan_Cuoi: false,
        isTxtCvlan_Cuoi: false,
        isTxtAdsl_cap_Cuoi: false,
        isTxtAdsl_port_Cuoi: false,
        isTxtNasport_id_Cuoi: false,
        isTxtIpDslam_Cuoi: false,
        lblLienHe_Cuoi: null,

        isDisplayTable: false,
        txtSoAoCuoi: null,
        cboThietBiCuoi: null,
        txtSerial_Cuoi: null,
        cboLoaiCap_Cuoi: null,
        txtSvlan_Cuoi: null,
        txtCvlan_Cuoi: null,
        txtCapGoc_Cuoi: null,
        txtCapNgon_Cuoi: null,
        txtAdsl_cap_Cuoi: null,
        txtAdsl_port_Cuoi: null,
        txtTuCapGoc_Cuoi: null,
        txtTuCapNgon_Cuoi: null,
        txtNasport_id_Cuoi: null,
        txtVrf_Cuoi: null,
        txtDoiCapGoc_Cuoi: null,
        txtDoiCapNgon_Cuoi: null,
        txtIpDslam_Cuoi: null,
        txtLienTu_Cuoi: null,
        txtHopCapNgon_Cuoi: null,
        txtTbi_Cuoi: null,
        txtIpwan_Cuoi: null,
        txtSoMetDay_Cuoi: null,
        txtTuyenCap_Cuoi: null,
        txtTimeSlot_Cuoi: null,
        txtLink_Cuoi: null,
        cboDonViCuoi: null,
        txtDiaChiCuoi: null,
        txtDiaChiCuoi_Cu: null,
        txtLyDoTra_Cuoi: null,
        txtNoiDungTra_Cuoi: null,
        txtSoSoiQuang: null,
        txtGhiChu: null,
        table: {
          currentRecord: null,
          data: null,
          loading: false,
          options: [],
          config: [
            {
              label: "PORTVL_ID",
              filterAble: true,
              key: "portvl_id",
            },
            {
              label: "Link",
              filterAble: true,
              key: "link_code",
            },
            {
              label: "Thiết bị đích",
              filterAble: true,
              key: "tendslam",
            },
            {
              label: "Tham số",
              filterAble: true,
              key: "thamso",
            },
            {
              label: "Loại thiết bị đích",
              filterAble: true,
              key: "loai_tbi",
            },
          ],
        },
      },
      options_ThongTinHoanCong_DD: {
        options_thietBi: [],
        options_loaiCap: [],
        options_donVi: [],
      },
      options_ThongTinHoanCong_DC: {
        options_thietBi: [],
        options_loaiCap: [],
        options_donVi: [],
      },
      form_DanhSachThueBao: {
        isRdoPhieu_Da_TH: true,
        rdoPhieu: 0,
        // rdoPhieuTra:null,
        // rdoPhieu_Da_TH:null,

        table: {
          currentRecord: null,
          data: null,
          loading: false,
          options: [],
          config: [
            {
              label: "Số máy/Acc",
              filterAble: true,
              key: "ma_tb",
            },
            {
              label: "Số ảo",
              filterAble: true,
              key: "ma_lt",
            },
            {
              label: "Tên thuê bao",
              filterAble: true,
              key: "ten_tb",
            },
            {
              label: "Địa chỉ LĐ đầu",
              filterAble: true,
              key: "diachi_dau",
            },
            {
              label: "Địa chỉ LĐ cuối",
              filterAble: true,
              key: "diachi_cuoi",
            },
            {
              label: "Điểm",
              filterAble: true,
              key: "daucuoi",
            },
            {
              label: "Ngày YC",
              filterAble: true,
              key: "ngay_yc",
            },
            {
              label: "Ngày giao",
              filterAble: true,
              key: "ngaygiao",
            },
            {
              label: "Đơn vị hẹn",
              filterAble: true,
              key: "dv_hen",
            },
            {
              label: "Hẹn từ",
              filterAble: true,
              key: "giohen_tu",
            },
            {
              label: "Hẹn đến",
              filterAble: true,
              key: "giohen_den",
            },
            {
              label: "Mã GD",
              filterAble: true,
              key: "ma_gd",
            },
            {
              label: "Gói KT",
              filterAble: true,
              key: "goi_kt",
            },
            {
              label: "Chỉ tiêu TG",
              filterAble: true,
              key: "chitieu_tg",
            },
            {
              label: "Thời gian cam kết",
              filterAble: true,
              key: "thoigian_ck",
            },
            {
              label: "Giờ còn lại",
              filterAble: true,
              key: "gio_conlai",
            },
            {
              label: "Đơn vị",
              filterAble: true,
              key: "ten_dv_nhan",
            },
            {
              label: "Trạng thái in",
              filterAble: true,
              key: "trangthai_in",
            },
            {
              label: "Trạng thái",
              filterAble: true,
              key: "ten_status",
            },
            {
              label: "Ngày BG",
              filterAble: true,
              key: "ngay_ht",
            },
            {
              label: "Ngày KH",
              filterAble: true,
              key: "ngay_kh",
            },
            {
              label: "Lý do tồn",
              filterAble: true,
              key: "lydoton_tc",
            },
            {
              label: "Ghi chú tồn",
              filterAble: true,
              key: "ghichu_ton",
            },
            {
              label: "Ghi chú",
              filterAble: true,
              key: "ghichu",
            },
          ],
        },
      },
      table: {},
    };
  },
  beforeCreate() {
    this.loading(true);

    this.isRouterLeaving = false;
    if (this.$store.hasModule("installMPLSByTeleTechTeam-module")) {
      this.$store.unregisterModule("installMPLSByTeleTechTeam-module");
    }
    this.$store.registerModule("installMPLSByTeleTechTeam-module", installMPLSByTeleTechTeamModule);
  },

  beforeRouteLeave(to, from, next) {
    this.isRouterLeaving = true;
    next();
  },

  beforeDestroy() {
    if (this.isRouterLeaving)
      this.$store.unregisterModule("installMPLSByTeleTechTeam-module");
  },
  mounted() {
    this.loading(true);

    this.getComboBox();

    this.form_ThongTinGiaoViec.cboNguoiGV = this.$root.token.getNhanVienID();

    // this.getFormThongTinGiaoViec();
  },
  watch: {
    async port_cuoi_id(newValue, oldValue) {
      const dataPortDau = await this.$store.dispatch(
        "installMPLSByTeleTechTeam-module/fetchPortDauCuoi",
        {
          port_id: 7402,
        }
      );
      //TODO

      this.form_ThongTinHoanCong_DD.table.options = dataPortDau.data.data || [];
    },
    async port_dau_id(newValue, oldValue) {
      const dataPortCuoi = await this.$store.dispatch(
        "installMPLSByTeleTechTeam-module/fetchPortDauCuoi",
        {
          port_id: 7402,
        }
      );
      //TODO

      this.form_ThongTinHoanCong_DC.table.options =
        dataPortCuoi.data.data || [];
    },
    ...["form_ThongTinKhachHang.cboLoaiHD"].reduce(
      (watchers, key) => ({
        ...watchers,
        [key](newVal, oldVal) {
          Vue.prototype.loading(true);
          this.form_ThongTinKhachHang.cboQuyTrinh = null;
          this.form_ThongTinKhachHang.phieu_id = null;
          if (!!this.form_ThongTinKhachHang.cboLoaiHD) {
            this.getQuyTrinh();
          }
        },
      }),
      {}
    ),
    "form_ThongTinKhachHang.cboQuyTrinh"(newVal, oldVal) {
      if (
        this.form_ThongTinKhachHang.cboQuyTrinh &&
        this.form_ThongTinKhachHang.cboLoaiHD &&
        newVal !== oldVal
      ) {
        console.log("getHuongGiaoID");
        this.$root.showLoading(true);

        this.getHuongGiaoID();
      }
    },

    // async huonggiao_id(newValue, oldValue) {
    //   console.log("huonggiao_id")
    //   this.loading(true);
    //   if (newValue) {
    //     try {
    //       await this.$store
    //         .dispatch("installMPLSByTeleTechTeam-module/fetchDanhSachThueBao", {
    //           dichvuvt_id: 9,
    //           tthd_id: 2,
    //           huonggiao_id: 810,
    //           nhanvien_id: 452,
    //           ma_gd: "0",
    //           phieu_id: 0,
    //           loaihd_id: 1,
    //           loai_id: this.rdoPhieu,
    //         })
    //         .then((response) => {
    //           if (response.data.message == "Success") {
    //             console.log(response.data.data);
    //             this.form_DanhSachThueBao.table.data = response.data.data;
    //           }
    //         });
    //     } catch (error) {
    //       this.loading(false);
    //     }
    //     this.loading(false);
    //   }
    // },

    "form_DanhSachThueBao.table.options"(newValue, oldValue) {
      if (newValue != undefined && newValue != null && newValue.length > 0) {
        this.form_DanhSachThueBao.table.currentRecord = newValue[0];
      } else {
        this.clearForm();
        this.form_DanhSachThueBao.table.currentRecord = null;
      }
    },
    async luong_id(n, o) {
      const danhSach = await this.$store.dispatch(
        "installMPLSByTeleTechTeam-module/fetchDSLuongThaoTac",
        {
          luong_id: n,
        }
      );
      this.danhSachThaoTac = danhSach.data.data;
    },
    "form_DanhSachThueBao.rdoPhieu"(newVal, oldVal) {
      Vue.prototype.loading(true);

      this.form_DanhSachThueBao.table.options = [];
      this.clearForm();
      this.vloai_id = 0; // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
      if (this.form_DanhSachThueBao.rdoPhieu == 0) {
        this.vloai_id = 0;
        this.form_button.tsbtnHuy_HC = false;
      }
      if (this.form_DanhSachThueBao.rdoPhieu == 1) {
        this.vloai_id = 1;
        this.form_button.tsbtnHuy_HC = false;
      }
      if (this.form_DanhSachThueBao.rdoPhieu == 2) {
        this.vloai_id = 2;
        this.form_button.tsbtnHuy_HC = true;
      }
      // this.form_ThongTinKhachHang.cboLoaiHD = null;

      // this.getLoaiHD();
      console.log("--- rdoPhieu ------ getHuongGiaoID -----------");

      this.getTableDSThueBao();
    },
    // ...["huonggiao_id"].reduce(
    //   (watchers, key) => ({
    //     ...watchers,
    //     [key](newVal, oldVal) {
    //       if (
    //         this.form_ThongTinKhachHang.cboQuyTrinh !== null &&
    //         this.form_ThongTinKhachHang.cboLoaiHD
    //       ) {
    //         this.getHuongGiaoID();
    //       }
    //     },
    //   }),
    //   {}
    // ),

    "form_ThongTinGiaoViec.chkNgayGV"(n, o) {
      n
        ? (this.form_ThongTinGiaoViec.disNgayGV = false)
        : (this.form_ThongTinGiaoViec.disNgayGV = true);
    },
    "form_ThongTinGiaoViec.chkNgayBG"(n, o) {
      n
        ? (this.form_ThongTinGiaoViec.disNgayBG = false)
        : (this.form_ThongTinGiaoViec.disNgayBG = true);
    },
    async "form_DanhSachThueBao.table.currentRecord"(newValue, oldValue) {
      const dataRow = JSON.parse(JSON.stringify(newValue));
      if (
        dataRow != undefined &&
        dataRow != null &&
        dataRow &&
        Object.keys(dataRow).length > 0
      ) {
        //       ch: "0"
        // ctv_id: null
        // daucuoi: null
        // diachi_ct: "Toà nhà Mobifone, Khu VP1, Phường Yên Hoà, Quận Cầu Giấy, TP Hà Nội"
        // diachi_kh: "Toà nhà Mobifone, Khu VP1, Phường Yên Hoà, Quận Cầu Giấy, TP Hà Nội"
        // diachi_ld: "Số 2, Ngõ 6, Đường Nguyên Hồng, Phường Lam Sơn, Quận Lê Chân, Tỉnh Hải Phòng"
        // diachi_tb: "Số 2, Ngõ 6, Đường Nguyên Hồng, Phường Lam Sơn, Quận Lê Chân, Tỉnh Hải Phòng"
        // diachi_tt: "Toà nhà Mobifone, Khu VP1, Phường Yên Hoà, Quận Cầu Giấy, TP Hà Nội"
        // dichvuvt_id: "9"
        // donvi_cuoi_id: "0"
        // donvi_dau_id: "0"
        // donvi_id: "6"
        // donviql_id: "40"
        // email: null
        // ghichu: "Sử dụng cáp 8 sợi Kênh kết nối về Số 38, ngách 233, đường Nguyễn Đức Cảnh LL000403048/TenKhachHang:Trung tâm quản lý , điều hành mạng (NOC) - Chi nhánh Tổng công ty Viễn thông Mobifone;DienThoai:0902008004;Email:"
        // ghichu_tt: "TUANNA UPDATE THEO LO SO LIEU GUI NGAY 28/11/2017"
        // gioitinh: "0"
        // hdkh_id: "3835008"
        // hdtb_id: "4244565"
        // hdtt_id: "1542142"
        // httt_id: "2"
        // khachhang_id: "1656610"
        // kieuhd_id: null
        // kieuld_id: "48"
        // loaigt_id: "8"
        // loaihd_id: (...)
        // loaihinh_tb: "Leasedline STM1"
        // loaikh_id: "62"
        // loaitb_id: "68"
        // loaitb_id1: null
        // luu_in: null
        // lydoton: null
        // ma_gd: "HPG-LD/00572366"
        // ma_lt: "131070523478 , 131070523479"
        // ma_tb: "LL000403047"
        // ma_tt: "HPG-30-345077"
        // manv_tc: "VTDK1"
        // matb: "LL000403047"
        // matb_dd: null
        // mst: "0100686209174"
        // nd_giao: null
        // nd_thuchien: null
        // ngay_dkht: null
        // ngay_ht: null
        // ngay_ht_gp: "2020-01-22 20:48:36.0"
        // ngay_sn: "2000-01-01 00:00:00.0"
        // ngay_yc: "2019-12-02 13:59:07.0"
        // ngaycap: "2000-01-01 00:00:00.0"
        // ngaygiao: "02/12/2019 13:59:31"
        // nguoi_dd: "Tú"
        // nhanvien_cd_id: "12356"
        // nhanvien_id: "12356"
        // noicap: "KHDT     Bộ kế hoạch đầu tư"
        // phieu_id: "8185709"
        // so_dt: "(84)0437831758"
        // so_gt: "12"
        // status: "0"
        // stk: null
        // stt: "0"
        // ten_kh: "Trung tâm quản lý , điều hành mạng (NOC) - Chi nhánh Tổng công ty Viễn thông Mobifone"
        // ten_kieuld: "Đặt mới KTR - TTVT hoàn thiện"
        // ten_loaihd: "Lắp đặt mới"
        // ten_nv: "webservice_hni"
        // ten_status: "Chua kich hoat"
        // ten_tb: "Trung Tâm Quản Lý , Điều Hành Mạng (NOC) - Chi Nhánh Tổng Công Ty Viễn Thông Mobifone"
        // ten_tt: "Trung tâm quản lý , điều hành mạng (NOC) - Chi nhánh Tổng công ty Viễn thông Mobifone"
        // thangnam_sy: "10/11/2021"
        // thangnam_sys: "10/11/2021"
        // thangnam_tt: "02/12/2019"
        // thanhtoan_id: "1499113"
        // thuebao_id: null
        // tnc2_id: "99911"
        // trangthai_hd: "Đã thanh toán"
        // tthd_id: "2"
        // ttph_id: "1"
        // tuyenthu_id: "1"
        this.form_ThongTinKhachHang.dichvuvt_id = dataRow.dichvuvt_id;
        this.form_ThongTinKhachHang.txtMaGD = dataRow.ma_gd;
        this.form_ThongTinKhachHang.txtMaTB = dataRow.ma_tb;
        this.form_ThongTinKhachHang.txtTenKieuLD = dataRow.ten_kieuld;
        this.form_ThongTinKhachHang.cboLoaiHinh = dataRow.loaihd_id;

        this.form_ThongTinKhachHang.txtTenTB = dataRow.ten_tb;
        this.form_ThongTinKhachHang.txtDiaChiTB = dataRow.diachi_tb;

        this.form_ThongTinKhachHang.phieu_id = dataRow.phieu_id;
        this.form_ThongTinKhachHang.nhanvien_id = dataRow.nhanvien_id;
        this.form_ThongTinKhachHang.cboChuQuan = dataRow.donvi_id;
        this.form_ThongTinHoanCong_DD.txtNDGiao = dataRow.nd_giao;
        this.form_ThongTinHoanCong_DD.txtNoiDungTH = dataRow.nd_thuchien;
        this.form_ThongTinHoanCong_DD.txtGhiChu = dataRow.ghichu;
        this.form_ThongTinKhachHang.hdkh_id = dataRow.hdkh_id;
        if (dataRow.hdkh_id) {
          this.getKenhThu();
        }

        this.form_ThongTinKhachHang.hdtb_id = dataRow.hdtb_id;
        if (dataRow.hdtb_id) {
          this.getDSDiaChi();
          this.getHen();
          this.getDSDonVi();
          this.getDanhSachHopDongThueBaoTLS();
        }

        if (dataRow.hdkh_id && dataRow.hdtb_id) {
          this.getLienHe();
        }
        // this.form_ThongTinHoanCong_DD.cboDonViDau = dataRow.donvi_dau_id;

        // this.form_ThongTinHoanCong_DC.cboDonViCuoi = dataRow.donvi_cuoi_id;

        // this.form_ThongTinGiaoViec.dtpNgayGV =  moment(dataRow.ngay_ht_gp).format('DD/MM/YYYY HH:mm:ss')
        if (!!dataRow.ngay_ht_gp) {
          this.form_ThongTinGiaoViec.chkNgayBG = true;
          this.form_ThongTinGiaoViec.dtpNgayBG = moment(
            dataRow.ngay_ht_gp
          ).format("DD/MM/YYYY hh:mm:ss");
        } else {
          // this.$refs.ThongTinGiaoViec.$refs.dtpNgayBG.checkbox_value = false;
          const date = moment(dataRow.ngay_ht_gp)
            .add(30, "days")
            .format("DD/MM/YYYY hh:mm:ss");
          this.form_ThongTinGiaoViec.dtpNgayBG = date;
        }
        this.form_ThongTinGiaoViec.dtpNgayGV = dataRow.ngaygiao;

        !!dataRow.ngaygiao
          ? (this.form_ThongTinGiaoViec.chkNgayGV = true)
          : (this.form_ThongTinGiaoViec.chkNgayGV = false);

        const infoNV = await this.$store.dispatch(
          "installMPLSByTeleTechTeam-module/fetchThongTinNhanVien",
          {
            nhanvien_id: dataRow.nhanvien_id,
          }
        );

        this.form_ThongTinGiaoViec.txtNhanVien = infoNV.data.data;
        if (!!dataRow.ctv_id) {
          const infoNVDV = await this.$store.dispatch(
            "installMPLSByTeleTechTeam-module/fetchThongTinNhanVienDV",
            {
              nhanvien_id: dataRow.ctv_id,
            }
          );
          this.form_ThongTinChung.txtNV_TiepThi =
            infoNVDV.data.data[0].nhanvien;
        } else {
          this.form_ThongTinChung.txtNV_TiepThi = "";
        }

        if (this.form_DanhSachThueBao.rdoPhieu == 1) {
          this.getHT_PHIEU_TRA_TSL();
        } else {
          this.form_ThongTinHoanCong_DD.txtNoiDungTra_Dau = "";
          this.form_ThongTinHoanCong_DD.txtLyDoTra_Dau = "";
          this.form_ThongTinHoanCong_DC.txtNoiDungTra_Cuoi = "";
          this.form_ThongTinHoanCong_DC.txtLyDoTra_Cuoi = "";
        }

        if (this.PHAILAM(PHAILAM.CAPNHAT_THONGSO_KYTHUAT)) {
          if (!!dataRow.ngay_ht_gp) {
            if (this.PHAILAM(PHAILAM.GIAOPHIEU))
              this.form_button.tsbtnGiaoPhieu = true;
            else this.form_button.tsbtnGiaoPhieu = false;
          } else this.form_button.tsbtnGiaoPhieu = false;
        }

        if (this.PHAILAM(PHAILAM.CAPNHAT_CAP_TSL)) {
          if (dataRow.ngay_ht_gp != "") {
            if (this.PHAILAM(PHAILAM.GIAOPHIEU))
              this.form_button.tsbtnGiaoPhieu = true;
            else this.form_button.tsbtnGiaoPhieu = false;

            if (this.PHAILAM(PHAILAM.HOANTHANH_PHIEU))
              this.form_button.tsbtnHoanThanh = true;
            else this.form_button.tsbtnHoanThanh = false;

            if (this.PHAILAM(PHAILAM.HOANCONG))
              this.form_button.tsbtnHoanCong = true;
            else this.form_button.tsbtnHoanCong = false;
          } else {
            this.form_button.tsbtnHoanThanh = false;
            this.form_button.tsbtnHoanCong = false;
            this.form_button.tsbtnGiaoPhieu = false;
          }
        }
        if (this.PHAILAM(PHAILAM.HOANCONG)) {
          if (dataRow.ngay_ht_gp != "") {
            this.form_button.tsbtnHoanCong = true;
            if (this.PHAILAM(PHAILAM.GIAOPHIEU))
              this.form_button.tsbtnGiaoPhieu = true;
            else this.form_button.tsbtnGiaoPhieu = false;
          } else {
            this.form_button.tsbtnHoanCong = false;
          }
        }
        if (this.PHAILAM(PHAILAM.HOANTHIENHOSO)) {
          if (dataRow.ngay_ht_gp != "")
            this.form_button.tsbtnHoanThienHS = true;
          else this.form_button.tsbtnHoanThienHS = false;
        }

        if (!this.PHAILAM(PHAILAM.GIAOVIEC)) {
          this.form_ThongTinGiaoViec.chkNgayGV = true;

          this.form_ThongTinGiaoViec.disNgayGV = false;
          this.form_ThongTinGiaoViec.dischkNgayGV = false;

          this.form_ThongTinGiaoViec.chkNgayBG = true;

          this.form_ThongTinGiaoViec.disNgayBG = false;
          this.form_ThongTinGiaoViec.dischkNgayBG = false;
        } else {
          this.form_ThongTinGiaoViec.disNgayGV = true;
          this.form_ThongTinGiaoViec.dischkNgayGV = true;

          this.form_ThongTinGiaoViec.disNgayBG = true;
          this.form_ThongTinGiaoViec.dischkNgayBG = true;
        }
        if (this.form_DanhSachThueBao.rdoPhieu == 2) {
          this.form_button.tsbtnHen = false;
          this.form_button.tsbtnChapNhan = false;
          this.form_button.tsbtnGiaoViec = false;
          this.form_button.tsbtnTraPhieu = false;

          if (this.PHAILAM(PHAILAM.GIAOPHIEU)) {
            this.form_button.tsbtnGiaoPhieu = true;
            this.form_button.tsbtnHoanCong = false;
            this.form_button.tsbtnHoanThanh = false;
          } else {
            this.form_button.tsbtnGiaoPhieu = false;
            this.form_button.tsbtnHoanCong = true;
            this.form_button.tsbtnHoanThanh = true;
          }
        } else {
          this.form_button.tsbtnHen = true;
          this.form_button.tsbtnChapNhan = true;
          this.form_button.tsbtnGiaoViec = true;
          this.form_button.tsbtnTraPhieu = true;
          this.form_button.tsbtnGiaoPhieu = true;

          this.form_button.tsbtnChuyenTo = true;
          this.form_button.tsbtnGiaoPhieu_Ton = true;
          this.form_button.tsbtnGiaoViec = true;
          this.form_button.tsbtnKQ_DoKiem = true;
        }
        const data = await this.$store.dispatch(
          "installMPLSByTeleTechTeam-module/fetchDanhSachNhanVienID",
          {
             phieu_id: this.form_ThongTinKhachHang.phieu_id,
            nhanvien_id:  this.$root.token.getNhanVienID(),
            kieu_id: 2,
          }
        );

        this.HienThiDSNV(data.data.data);
      } else {
        //  ẩn buttom trên menu vì k có dữ liệu
        this.form_button.tsbtnChapNhan = false;
        this.form_button.tsbtnHoanCong = false;
        this.form_button.tsbtnGiaoPhieu = false;

        // ẩn
        this.form_button.tsbtnTraPhieu = false;
        this.form_button.tsbtnChuyenTo = false;
        this.form_button.tsbtnGiaoPhieu_Ton = false;
        this.form_button.tsbtnGiaoViec = false;
        this.form_button.tsbtnKQ_DoKiem = false;

        this.clearForm();
      }
    },
    async "form_ThongTinKhachHang.phieu_id"(newValue, oldValue) {
      try {
        await this.$store
          .dispatch("installMPLSByTeleTechTeam-module/fetchTongTien", {
            hdkh_id: this.form_ThongTinKhachHang.hdkh_id,
          })
          .then((response) => {
            if (response.data.message == "Success") {
              this.form_ThongTinChung.txtTongTien =
                response.data.data[0].tongtien;
            }
          });

        // this.getFormThongTinGiaoViec();
        this.getHT_PHIEU_TRA_TSL();
        this.getMAPIDDAUCUOIID();
      } catch (error) {}
    },
  },
  methods: {
    HienThiGiaoDien() {
      console.log("HienThiGiaoDien");
      // tsbtnKQ_DoKiem.Visible = false;
      // this.form_button.isTsbtnTraPhieu = false;
      this.form_button.isTsbtnDuAn = false;
      // this.form_button.isTsbtnChuyenTo = false;
      // đầu
      this.form_ThongTinHoanCong_DD.isTxtSvlan_Dau = true;
      this.form_ThongTinHoanCong_DD.isTxtCvlan_Dau = true;
      this.form_ThongTinHoanCong_DD.isTxtAdsl_cap_Dau = true;
      this.form_ThongTinHoanCong_DD.isTxtAdsl_port_Dau = true;
      this.form_ThongTinHoanCong_DD.isTxtNasport_id_Dau = true;
      this.form_ThongTinHoanCong_DD.isTxtIpDslam_Dau = true;
      this.form_ThongTinHoanCong_DD.isTxtVrf_Dau = true;
      this.form_ThongTinHoanCong_DD.isTxtSerial_Dau = true;
      this.form_ThongTinHoanCong_DD.isTxtIpwan_Dau = true;
      this.form_ThongTinHoanCong_DD.isTxtTimeSlot_Dau = true;
      this.form_ThongTinHoanCong_DD.isTxtLink_Dau = true;
      this.form_ThongTinHoanCong_DD.isTxtCapGoc_Dau = true;
      this.form_ThongTinHoanCong_DD.isTxtCapNgon_Dau = true;
      this.form_ThongTinHoanCong_DD.isTxtTuCapGoc_Dau = true;
      this.form_ThongTinHoanCong_DD.isTxtTuCapNgon_Dau = true;
      this.form_ThongTinHoanCong_DD.isTxtDoiCapGoc_Dau = true;
      this.form_ThongTinHoanCong_DD.isTxtDoiCapNgon_Dau = true;
      this.form_ThongTinHoanCong_DD.isTxtHopCapNgon_Dau = true;
      this.form_ThongTinHoanCong_DD.TxtSoMetDay_Dau = true;
      this.form_ThongTinHoanCong_DD.isTxtLienTu_Dau = true;
      this.form_ThongTinHoanCong_DD.TxtTuyenCap_Dau = true;

      // cuoi
      this.form_ThongTinHoanCong_DC.isTxtSvlan_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtCvlan_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtAdsl_cap_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtAdsl_port_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtNasport_id_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtIpDslam_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtVrf_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtSerial_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtIpwan_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtTimeSlot_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtLink_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtCapGoc_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtCapNgon_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtTuCapGoc_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtTuCapNgon_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtDoiCapGoc_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtDoiCapNgon_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtHopCapNgon_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtSoMetDay_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtLienTu_Cuoi = true;
      this.form_ThongTinHoanCong_DC.isTxtTuyenCap_Cuoi = true;

      // this.form_button.isTsbtnHoanThienHS = false;
      // tsbtnHoanThienHS.Enabled = false;
      this.form_button.isTsbtnHoanCong = false;

      // this.form_button.isTsbtnGiaoPhieu = false;
      // this.form_button.isTsbtnHoanThanh = false;
      this.form_button.isTsbtnVatTu = false;
      this.form_DanhSachThueBao.isRdoPhieu_Da_TH = false;

      this.form_button.isTsbtnGiaoPhieu_KBB = false;
      this.form_button.isTsbtnSua_KenhThu = false;

      if (this.PHAILAM("SERIAL_TSL")) {
        this.form_ThongTinHoanCong_DD.isTxtSerial_Dau = true;
        this.form_ThongTinHoanCong_DC.isTxtSerial_Cuoi = true;
      } else {
        this.form_ThongTinHoanCong_DD.isTxtSerial_Dau = false;
        this.form_ThongTinHoanCong_DC.isTxtSerial_Cuoi = false;
      }
    },
    KiemTra_DL() {
      if (this.form_ThongTinGiaoViec.chkNgayGV == false) {
        // Message_Box.ShowWarning();
        this.$toast.error("Hãy chọn ngày giao việc !");

        return false;
      }

      if (!this.form_ThongTinGiaoViec.chkNgayBG) {
        this.$toast.error("Hãy nhập ngày hoàn thành!");
        // chkNgayBG.Focus();
        return false;
      }
      //Kiếm tra với luong_id, thì fai làm những gì
      //1. Giao việc
      if (this.PHAILAM("GIAOVIEC")) {
        if (this.formProps.table_DSNV.config.length <= 0) {
          this.$toast.error("Hãy nhập nhân viên thực hiện !");

          return false;
        }
      }
      //2. Cập nhật thông số kỹ thuật
      if (this.PHAILAM("CAPNHAT_THONGSO_KYTHUAT")) {
        if (this.kieu == 1 || this.kieu == 3) {
          if (!!this.form_ThongTinHoanCong_DD.txtPort_Dau) {
            this.$toast.error("Hãy nhập thông tin về Port điểm đầu");

            // txtAdsl_port_Dau.Focus();
            return false;
          }
          if (!!this.form_ThongTinHoanCong_DD.txtTimeSlot_Dau) {
            this.$toast.error("Hãy nhập thông tin về TimeSlot điểm đầu");

            // txtTimeSlot_Dau.Focus();
            return false;
          }
        }
        if (this.kieu == 2 || this.kieu == 3) {
          if (!!this.form_ThongTinHoanCong_DC.txtPort_Cuoi) {
            this.$toast.error("Hãy nhập thông tin về Port điểm cuối");

            // txtAdsl_port_Cuoi.Focus();
            return false;
          }
          if (!!this.form_ThongTinHoanCong_DC.txtTimeSlot_Cuoi) {
            this.$toast.error("Hãy nhập thông tin về TimeSlot điểm cuối");

            // txtTimeSlot_Cuoi.Focus();
            return false;
          }
        }
      }
      if (this.PHAILAM("CAPNHAT_CAP_TSL")) {
        if (this.kieu == 1 || this.kieu == 3) {
          if (!!this.form_ThongTinHoanCong_DD.txtCapGoc_Dau) {
            this.$toast.error("Hãy nhập thông tin cáp gốc đầu !");

            // txtCapGoc_Dau.Focus();
            return false;
          }
          if (!!this.form_ThongTinHoanCong_DD.txtTuCapGoc_Dau) {
            this.$toast.error("Hãy nhập thông tin tủ cáp gốc đầu !");

            // txtTuCapGoc_Dau.Focus();
            return false;
          }

          if (!!this.form_ThongTinHoanCong_DD.txtDoiCapGoc_Dau) {
            this.$toast.error("Hãy nhập thông tin đôi cáp gốc đầu !");

            // txtDoiCapGoc_Dau.Focus();
            return false;
          }

          if (!!this.form_ThongTinHoanCong_DD.txtCapNgon_Dau) {
            this.$toast.error("Hãy nhập thông tin cáp ngọn đầu !");

            // txtCapNgon_Dau.Focus();
            return false;
          }

          if (!!this.form_ThongTinHoanCong_DD.txtDoiCapNgon_Dau) {
            this.$toast.error("Hãy nhập thông tin đôi cáp ngọn đầu !");

            // txtDoiCapNgon_Dau.Focus();
            return false;
          }

          if (
            !!this.form_ThongTinHoanCong_DD.txtSoMetDay_Dau ||
            this.form_ThongTinHoanCong_DD.txtSoMetDay_Dau == "0"
          ) {
            this.$toast.error("Hãy nhập thông tin số mét dây đầu !");

            // txtSoMetDay_Dau.Focus();
            return false;
          }

          if (!!this.form_ThongTinHoanCong_DD.txtSoMetDay_Dau) {
            if (this.form_ThongTinHoanCong_DD.txtSoMetDay_Dau == "0") {
              this.$toast.error("Hãy nhập số mét dây đầu !");

              // txtSoMetDay_Dau.Focus();
              return false;
            }
            var s = this.form_ThongTinHoanCong_DD.txtSoMetDay_Dau;
            if (typeof num1 !== "number") {
              this.$toast.error("Số mét dây đầu phải là kiểu số");

              // Message_Box.ShowWarning();
              this.form_ThongTinHoanCong_DD.txtSoMetDay_Dau = "0";
              // txtSoMetDay_Dau.Focus();
              return false;
            }
          } else {
            this.$toast.error("Hãy nhập số mét dây đầu !");

            // txtSoMetDay_Dau.Focus();
            return false;
          }
        }
        if (this.kieu == 2 || this.kieu == 3) {
          if (!!this.form_ThongTinHoanCong_DC.txtCapGoc_Cuoi) {
            this.$toast.error("Hãy nhập thông tin cáp gốc cuối !");

            // txtCapGoc_Cuoi.Focus();
            return false;
          }
          if (!!this.form_ThongTinHoanCong_DC.txtTuCapGoc_Cuoi) {
            this.$toast.error("Hãy nhập thông tin tủ cáp gốc cuối !");

            // txtTuCapGoc_Cuoi.Focus();
            return false;
          }

          if (!!this.form_ThongTinHoanCong_DC.txtDoiCapGoc_Cuoi) {
            this.$toast.error("Hãy nhập thông tin đôi cáp gốc cuối !");

            // txtDoiCapGoc_Cuoi.Focus();
            return false;
          }

          if (!!this.form_ThongTinHoanCong_DC.txtCapNgon_Cuoi) {
            this.$toast.error("Hãy nhập thông tin cáp ngọn cuối !");
            // txtCapNgon_Cuoi.Focus();
            return false;
          }

          if (!!this.form_ThongTinHoanCong_DC.txtDoiCapNgon_Cuoi) {
            this.$toast.error("Hãy nhập thông tin đôi cáp ngọn cuối !");
            // txtDoiCapNgon_Cuoi.Focus();
            return false;
          }

          if (
            !!this.form_ThongTinHoanCong_DC.txtSoMetDay_Cuoi ||
            this.form_ThongTinHoanCong_DC.txtSoMetDay_Cuoi == "0"
          ) {
            this.$toast.error("Hãy nhập thông tin số mét dây cuối !");
            // txtSoMetDay_Cuoi.Focus();
            return false;
          }

          if (!!this.form_ThongTinHoanCong_DC.txtSoMetDay_Cuoi) {
            if (this.form_ThongTinHoanCong_DC.txtSoMetDay_Cuoi == "0") {
              this.$toast.error("Hãy nhập số mét dây cuối !");
              // txtSoMetDay_Cuoi.Focus();
              return false;
            }
            var sc = this.form_ThongTinHoanCong_DC.txtSoMetDay_Cuoi;
            if (typeof sc !== "number") {
              this.$toast.error("Số mét dây cuối phải là kiểu số");
              this.form_ThongTinHoanCong_DC.txtSoMetDay_Cuoi = "0";
              // txtSoMetDay_Cuoi.Focus();
              return false;
            }
          } else {
            this.$toast.error("Hãy nhập số mét dây cuối !");
            // txtSoMetDay_Cuoi.Focus();
            return false;
          }
        }

        // hoangpkn : 21/03/2016
        // Rằng buộc cập nhật vật tư đối với hợp đồng lắp mới megawan (yc của HCM)
        if (
          (this.form_ThongTinKhachHang.cboLoaiHD == LoaiHopDong.DAT_MOI ||
            this.form_ThongTinKhachHang.cboLoaiHD == LoaiHopDong.KHOIPHUC_SD) &&
          this.PHAILAM("VAT_TU")
        ) {
          var ktra_giao_vattu = false;
          // ktra_giao_vattu = bangts.Kiemtra_GiaoPhieu_VatTu(phieu_id);
          if (ktra_giao_vattu == false) {
            this.$toast.error("Bạn chưa cấp vật tư thiết bị cho thuê bao !");
            return false;
          }
        }
        // end hoang
      }

      return true;
    },
    async Giao_Phieu(e) {
      // e.preventDefault();
      try {
        if (
          !!this.form_ThongTinKhachHang.cboQuyTrinh &&
          this.form_ThongTinKhachHang.txtMaGD
        ) {
          window.open(
            `/#/contract/liquidate/handoverticket?vquytrinh=${this.form_ThongTinKhachHang.cboQuyTrinh}&vhuonggiao_id=${this.huonggiao_id}&vdichvuvt_id=9&vloai_hd=${this.form_ThongTinKhachHang.cboLoaiHD}&vma_gd=${this.form_ThongTinKhachHang.txtMaGD}`
          );
        }
      } catch (error) {
        this.$toast.error("Có lỗi xảy ra");
      }
    },
    KiemTraDK_HoanThanh(
      thangnam_tt,
      thangnam_sys //thangnam_sys= ngay_ht
    ) {
      let year1 = moment(thangnam_tt, "DD/MM/YYYY").format("YYYY");
      let year2 = moment(thangnam_sys, "DD/MM/YYYY").format("YYYY");

      if (year1 < year2) {
        return true;
      }
      //So sánh năm

      //Nếu năm thanh toán < năm hoàn thành thì true;
      else if (year1 == year2) {
        console.log("Nêu năm = nhau thì kiểm tra tiếp");
        //So sánh năm
        //Nêu năm = nhau thì kiểm tra tiếp
        //Nếu tháng tt < thang ht -> true
        if (
          moment(thangnam_tt, "DD/MM/YYYY").format("MM") <
          moment(thangnam_sys, "DD/MM/YYYY").format("MM")
        )
          return true;
        else if (
          moment(thangnam_tt, "DD/MM/YYYY").format("MM") ==
          moment(thangnam_sys, "DD/MM/YYYY").format("MM")
        )
          if (
            moment(thangnam_tt, "DD/MM/YYYY").format("DD") <=
            moment(thangnam_sys, "DD/MM/YYYY").format("DD")
          ) {
            //Nếu tháng & năm = nhau thì kiểm tra ngày
            console.log("ngay");
            return true;
          } else return false;
        else return false; //tháng tt lớn hơn thang ht
      } else return false;
    },
    async Cap_Nhat() {
      // //Hiếu thêm hàm kiểm tra dữ liệu nhập ngày 03/09/2010
      if (!this.KiemTra_DL()) return false;
      // //Kiểm tra ngày hoàn thành có >= ngày thanh toán , <= ngay hien tai ko

      // const mIndex = await this.$store.dispatch(
      //   "installMPLSByTeleTechTeam-module/fetchKiemTra_GiaoPhieu_VatTu",
      //   {
      //     vphieu_id: this.form_ThongTinKhachHang.phieu_id,
      //   }
      // );
      // console.log(this.form_DanhSachThueBao.table.currentRecord);
      // console.log(mIndex);
      // console.log(this.form_ThongTinKhachHang.phieu_id);
      // TODO
      // var nIndex = LayIndexTheoPhieuID(CURRENT_PHIEU_ID, dtList);
      const ngay_sys =
        this.form_DanhSachThueBao.table.currentRecord.thangnam_sys;
      const ngay_tt = this.form_DanhSachThueBao.table.currentRecord.thangnam_tt;

      const ngay_ht = moment(
        this.form_ThongTinGiaoViec.dtpNgayBG,
        "DD/MM/YYYY"
      ).format("DD/MM/YYYY");

      if (!this.KiemTraDK_HoanThanh(ngay_tt, ngay_ht)) {
        //Nếu ngày thanh toán > ngày hoàn thành
        this.$toast.error(
          " Thuê bao " +
            this.form_DanhSachThueBao.table.currentRecord.ma_tb +
            " có ngày thanh toán : " +
            ngay_tt +
            " \n Ngày hoàn thành không được phép nhỏ hơn ngày thanh toán"
        );
        return false;
      }
      if (!this.KiemTraDK_HoanThanh(ngay_ht, ngay_sys)) {
        //Nếu ngày thanh toán > ngày hoàn thành
        this.$toast.error(
          "Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại"
        );
        return false;
      }

      const data = [
        {
          LUONG_ID: this.luong_id,
          HDTB_ID: this.form_ThongTinKhachHang.hdtb_id,
          PHIEU_ID: this.form_ThongTinKhachHang.phieu_id,
          NHANVIEN_ID: this.form_ThongTinKhachHang.nhanvien_id,
          DONVI_ID: this.form_ThongTinKhachHang.cboChuQuan,

          LOAIHD_ID: this.form_ThongTinKhachHang.cboLoaiHinh,
          NGAY_BG: this.form_ThongTinGiaoViec.dtpNgayBG,
          NGUOIGV_ID: this.form_ThongTinGiaoViec.cboNguoiGV,
          NGAY_GV: this.form_ThongTinGiaoViec.dtpNgayGV,

          TBI_DAU: this.form_ThongTinHoanCong_DD.txtTBi_Dau,
          VRF_DAU: this.form_ThongTinHoanCong_DD.txtVrf_Dau,
          SERIAL_DAU: this.form_ThongTinHoanCong_DD.txtSerial_Dau,
          IPWAN_DAU: this.form_ThongTinHoanCong_DD.txtIpwan_Dau,
          LINK_DAU: this.form_ThongTinHoanCong_DD.txtLink_Dau,
          TIMESLOT_DAU: this.form_ThongTinHoanCong_DD.txtTimeSlot_Dau,
          THIETBI_DAU: this.form_ThongTinHoanCong_DD.cboThietBiDau,

          TBI_CUOI: this.form_ThongTinHoanCong_DC.txtTBi_Cuoi,
          VRF_CUOI: this.form_ThongTinHoanCong_DC.txtVrf_Cuoi,
          SERIAL_CUOI: this.form_ThongTinHoanCong_DC.txtSerial_Cuoi,
          IPWAN_CUOI: this.form_ThongTinHoanCong_DC.txtIpwan_Cuoi,
          LINK_CUOI: this.form_ThongTinHoanCong_DC.txtLink_Cuoi,
          TIMESLOT_CUOI: this.form_ThongTinHoanCong_DC.txtTimeSlot_Cuoi,
          THIETBI_CUOI: this.form_ThongTinHoanCong_DC.cboThietBiCuoi,
          DONVI_DAU: this.form_ThongTinHoanCong_DD.cboDonViDau,
          DONVI_CUOI: this.form_ThongTinHoanCong_DC.cboDonViCuoi,
          TUYENCAP_DAU: this.form_ThongTinHoanCong_DD.txtTuyenCap_Dau,
          LIENTU_DAU: this.form_ThongTinHoanCong_DD.txtLienTu_Dau,
          DOICAPNGON_DAU: this.form_ThongTinHoanCong_DD.txtDoiCapNgon_Dau,
          HOPCAPNGON_DAU: this.form_ThongTinHoanCong_DD.txtHopCapNgon_Dau,
          TUCAPNGON_DAU: this.form_ThongTinHoanCong_DD.txtTuCapGoc_Dau,
          LOAICAP_DAU: this.form_ThongTinHoanCong_DD.cboLoaiCap_Dau,
          CAPGOC_DAU: this.form_ThongTinHoanCong_DD.txtCapGoc_Dau,
          CAPNGON_DAU: this.form_ThongTinHoanCong_DD.txtCapNgon_Dau,
          DOICAPGOC_DAU: this.form_ThongTinHoanCong_DD.txtDoiCapGoc_Dau,
          TUCAPGOC_DAU: this.form_ThongTinHoanCong_DD.txtTuCapGoc_Dau,
          SOMETDAY_DAU: this.form_ThongTinHoanCong_DD.txtSoMetDay_Dau,
          TUYENCAP_CUOI: this.form_ThongTinHoanCong_DC.txtTuyenCap_Cuoi,
          LIENTU_CUOI: this.form_ThongTinHoanCong_DC.txtLienTu_Cuoi,
          DOICAPNGON_CUOI: this.form_ThongTinHoanCong_DC.txtDoiCapNgon_Cuoi,
          HOPCAPNGON_CUOI: this.form_ThongTinHoanCong_DC.txtHopCapNgon_Cuoi,
          TUCAPNGON_CUOI: this.form_ThongTinHoanCong_DC.txtTuCapNgon_Cuoi,
          LOAICAP_CUOI: this.form_ThongTinHoanCong_DC.cboLoaiCap_Cuoi,
          CAPGOC_CUOI: this.form_ThongTinHoanCong_DC.txtCapGoc_Cuoi,

          CAPNGON_CUOI: this.form_ThongTinHoanCong_DC.txtCapNgon_Cuoi,

          DOICAPGOC_CUOI: this.form_ThongTinHoanCong_DC.txtDoiCapGoc_Cuoi,
          TUCAPGOC_CUOI: this.form_ThongTinHoanCong_DC.txtTuCapGoc_Cuoi,

          SOMETDAY_CUOI: this.form_ThongTinHoanCong_DC.txtSoMetDay_Cuoi,
        },
      ];
      console.log(JSON.stringify(data).toString());
      this.$store
        .dispatch("installMPLSByTeleTechTeam-module/PostCapNhat", {
          vds: JSON.stringify(data).toString(),
          vkieu: 1,
        })
        .then((response) => {
          if (response.data.message == "Success") {
            this.$toast.success("Thành công");
            this.form_DanhSachThueBao.rdoPhieu = 0;
            this.$root.showLoading(true);

            this.getTableDSThueBao();
          }
        })
        .catch((error) => {
          if (!error.response.data.message) {
            this.$toast.error(error.response.data.message);
          }
        });
    },
    async getMAPIDDAUCUOIID() {
      const dc_id = await this.$store.dispatch(
        "installMPLSByTeleTechTeam-module/fetchlydotra",
        {
          id_neo: "daucuoi_id",
          in_table: "css.giaophieu",
          in_dk: " where phieu_id = 12",
        }
      );

      if (dc_id.data.data != "-1" && dc_id.data.data != "0") {
        if (dc_id.data.data == "1") {
          // điểm đầu
          this.kieu = 1;
          this.HienThi_Panel(1, false);
          this.HienThi_Panel(2, true);
        } else if (dc_id.data.data == "2") {
          // điểm cuối
          this.kieu = 2;
          this.HienThi_Panel(1, true);
          this.HienThi_Panel(2, false);
        }
        //dtgDanhSach.Columns["cl_daucuoi"].Visible = true;
      } else {
        this.kieu = 0;
        this.HienThi_Panel(1, true);
        this.HienThi_Panel(2, true);
        //dtgDanhSach.Columns["cl_daucuoi"].Visible = false;
      }
    },
    HienThi_Panel(kieu, kt) {
      if (kieu == 1) {
        //Panel đầu
        if (
          this.form_ThongTinKhachHang.cboLoaiHD == TrangThaiHD.DANG_THI_CONG
        ) {
          this.form_ThongTinHoanCong_DD.isTxtTuCapGoc_Dau = kt;
          this.form_ThongTinHoanCong_DD.isTxtDoiCapGoc_Dau = kt;
          this.form_ThongTinHoanCong_DD.isTxtCapGoc_Dau = kt;
          this.form_ThongTinHoanCong_DD.isTxtHopCapNgon_Dau = kt;
          this.form_ThongTinHoanCong_DD.isTxtTuCapNgon_Dau = kt;
          this.form_ThongTinHoanCong_DD.isTxtDoiCapNgon_Dau = kt;
          this.form_ThongTinHoanCong_DD.isTxtCapNgon_Dau = kt;
          this.form_ThongTinHoanCong_DD.isTxtTuyenCap_Dau = kt;
          this.form_ThongTinHoanCong_DD.isTxtLienTu_Dau = kt;
          this.form_ThongTinHoanCong_DD.isTxtSoMetDay_Dau = kt;
          this.form_ThongTinHoanCong_DD.isTxtSerial_Dau = kt;
        } else {
          this.form_ThongTinHoanCong_DD.isTxtTuCapGoc_Dau = true;
          this.form_ThongTinHoanCong_DD.isTxtDoiCapGoc_Dau = true;
          this.form_ThongTinHoanCong_DD.isTxtCapGoc_Dau = true;
          this.form_ThongTinHoanCong_DD.isTxtHopCapNgon_Dau = true;
          this.form_ThongTinHoanCong_DD.isTxtTuCapNgon_Dau = true;
          this.form_ThongTinHoanCong_DD.isTxtDoiCapNgon_Dau = true;
          this.form_ThongTinHoanCong_DD.isTxtCapNgon_Dau = true;
          this.form_ThongTinHoanCong_DD.isTxtTuyenCap_Dau = true;
          this.form_ThongTinHoanCong_DD.isTxtLienTu_Dau = true;
          this.form_ThongTinHoanCong_DD.isTxtSoMetDay_Dau = true;
          this.form_ThongTinHoanCong_DD.isTxtSerial_Dau = true;
        }
      } else {
        if (kieu == 2) {
          //Panel cuoi
          if (
            this.form_ThongTinKhachHang.cboLoaiHD == TrangThaiHD.DANG_THI_CONG
          ) {
            this.form_ThongTinHoanCong_DC.isTxtTuCapGoc_Cuoi = kt;
            this.form_ThongTinHoanCong_DC.isTxtDoiCapGoc_Cuoi = kt;
            this.form_ThongTinHoanCong_DC.isTxtCapGoc_Cuoi = kt;
            this.form_ThongTinHoanCong_DC.isTxtHopCapNgon_Cuoi = kt;
            this.form_ThongTinHoanCong_DC.isTxtTuCapNgon_Cuoi = kt;
            this.form_ThongTinHoanCong_DC.isTxtDoiCapNgon_Cuoi = kt;
            this.form_ThongTinHoanCong_DC.isTxtCapNgon_Cuoi = kt;
            this.form_ThongTinHoanCong_DC.isTxtTuyenCap_Cuoi = kt;
            this.form_ThongTinHoanCong_DC.isTxtLienTu_Cuoi = kt;
            this.form_ThongTinHoanCong_DC.isTxtSoMetDay_Cuoi = kt;
            this.form_ThongTinHoanCong_DC.isTxtSerial_Cuoi = kt;
          } else {
            this.form_ThongTinHoanCong_DC.isTxtTuCapGoc_Cuoi = true;
            this.form_ThongTinHoanCong_DC.isTxtDoiCapGoc_Cuoi = true;
            this.form_ThongTinHoanCong_DC.isTxtCapGoc_Cuoi = true;
            this.form_ThongTinHoanCong_DC.isTxtHopCapNgon_Cuoi = true;
            this.form_ThongTinHoanCong_DC.isTxtTuCapNgon_Cuoi = true;
            this.form_ThongTinHoanCong_DC.isTxtDoiCapNgon_Cuoi = true;
            this.form_ThongTinHoanCong_DC.isTxtCapNgon_Cuoi = true;
            this.form_ThongTinHoanCong_DC.isTxtTuyenCap_Cuoi = true;
            this.form_ThongTinHoanCong_DC.isTxtLienTu_Cuoi = true;
            this.form_ThongTinHoanCong_DC.isTxtSoMetDay_Cuoi = true;
            this.form_ThongTinHoanCong_DC.isTxtSerial_Cuoi = true;
          }
        }
      }

      if (this.PHAILAM("CAPNHAT_THONGSO_KYTHUAT")) {
        this.form_ThongTinHoanCong_DD.isTxtTimeSlot_Dau = false;
        this.form_ThongTinHoanCong_DD.isTxtTBi_Dau = false;
        this.form_ThongTinHoanCong_DD.isTxtIpwan_Dau = false;
        this.form_ThongTinHoanCong_DD.isTxtVrf_Dau = false;
        this.form_ThongTinHoanCong_DD.isTxtLink_Dau = false;

        this.form_ThongTinHoanCong_DC.isTxtTimeSlot_Cuoi = false;
        this.form_ThongTinHoanCong_DC.isTxtTbi_Cuoi = false;
        this.form_ThongTinHoanCong_DC.isTxtIpwan_Cuoi = false;
        this.form_ThongTinHoanCong_DC.isTxtVrf_Cuoi = false;
        this.form_ThongTinHoanCong_DC.isTxtLink_Cuoi = false;
      } else {
        this.form_ThongTinHoanCong_DD.isTxtTimeSlot_Dau = true;
        this.form_ThongTinHoanCong_DD.isTxtTBi_Dau = true;
        this.form_ThongTinHoanCong_DD.isTxtIpwan_Dau = true;
        this.form_ThongTinHoanCong_DD.isTxtVrf_Dau = true;
        this.form_ThongTinHoanCong_DD.isTxtLink_Dau = true;

        this.form_ThongTinHoanCong_DC.isTxtTimeSlot_Cuoi = true;
        this.form_ThongTinHoanCong_DC.isTxtTbi_Cuoi = true;
        this.form_ThongTinHoanCong_DC.isTxtIpwan_Cuoi = true;
        this.form_ThongTinHoanCong_DC.isTxtVrf_Cuoi = true;
        this.form_ThongTinHoanCong_DC.isTxtLink_Cuoi = true;
      }
    },
    async getTableDSThueBao() {
      if (this.huonggiao_id) {
        try {
          this.$store
            .dispatch("installMPLSByTeleTechTeam-module/fetchDanhSachThueBao", {
              dichvuvt_id: 9,
              tthd_id: this.tag || 4,
              huonggiao_id: this.huonggiao_id,
              nhanvien_id: this.$root.token.getNhanVienID(),
              ma_gd: this.isEnterMaGD
                ? this.form_ThongTinKhachHang.txtMaGD
                : "0",
              phieu_id: 0,
              loaihd_id: this.form_ThongTinKhachHang.cboLoaiHD,
              loai_id: this.form_DanhSachThueBao.rdoPhieu,
            })
            .then((response) => {
              if (response.data.message == "Success") {
                this.form_DanhSachThueBao.table.options = response.data.data;
                this.$root.showLoading(false);
              } else {
                this.$root.showLoading(false);
              }
            })
            .finally(() => {
              this.isEnterMaGD = false;
            });
        } catch (error) {
          console.log(error);
          this.$root.showLoading(false);
        }
      } else {
        this.form_DanhSachThueBao.table.options = [];
        setInterval(() => {
          this.$root.showLoading(false);
        }, 1000);
      }
    },
    async getHuongGiaoID() {
      try {
        this.$root.showLoading(true);

        await this.$store
          .dispatch("installMPLSByTeleTechTeam-module/fetchDanhSachHuongGiao", {
            quytrinh_id: this.form_ThongTinKhachHang.cboQuyTrinh,
            tthd_id: 4,
          })
          .then(async (response) => {
            console.log("getHuongGiaoID");
            if (response.data.message == "Success") {
              this.huonggiao_id = await response.data.data[0].huonggiao_id;
              this.luong_id = await response.data.data[0].luong_id;
              console.log("--- Success ------ getHuongGiaoID -----------");
              await this.getTableDSThueBao();
            } else {
              // tesst
              this.huonggiao_id = null;
              console.log("--- null ------ getHuongGiaoID -----------");

              this.form_DanhSachThueBao.table.options = [];
              this.$root.showLoading(false);
            }
            this.HienThiGiaoDien();
          });
      } catch (error) {}
    },
    async getQuyTrinh() {
      try {
        await this.$store
          .dispatch("installMPLSByTeleTechTeam-module/fetchDanhSachQuyTrinh", {
            dsloaihd_id: 1,
            tthd_id: this.form_ThongTinKhachHang.cboLoaiHD,
            kieu: 0,
            dichvuvt_id: 9,
          })
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_ThongTinKhachHang.options_quyTrinh =
                response.data.data;

              this.form_ThongTinKhachHang.cboQuyTrinh =
                response.data.data[0].quytrinh_id;
              console.log(this.form_ThongTinKhachHang.cboQuyTrinh);
            } else {
              this.options_ThongTinKhachHang.options_quyTrinh = [];
              this.form_DanhSachThueBao.table.options = [];

              this.clearForm();

              this.loading(false);
            }
          });
      } catch (error) {}
    },
    async getDanhSachHopDongThueBaoTLS() {
      const log = await this.$store.dispatch(
        "installMPLSByTeleTechTeam-module/fetchDanhSachHopDongThueBaoTLS",
        this.form_ThongTinKhachHang.hdtb_id
      );

      let daset = log.data.data;

      if (daset.length > 0) {
        this.form_ThongTinHoanCong_DC.txtSoSoiQuang = daset[0].sl_soiquang;
        this.form_ThongTinKhachHang.cboTocDo = daset[0].tocdo_id;
        this.form_ThongTinKhachHang.cboChuQuan = daset[0].chuquan_id;

        if (!!daset[0].doitac_id) {
          console.log(daset[0].doitac_id);
          this.$refs.ThongTinKhachHang.$refs.doiTac.checkbox_value = true;
          this.$nextTick(() => {
            console.log(daset[0].doitac_id);
            this.form_ThongTinKhachHang.cboDoiTac = daset[0].doitac_id;
          });
        } else {
          this.$refs.ThongTinKhachHang.$refs.doiTac.checkbox_value = false;
          this.$nextTick(() => {
            this.form_ThongTinKhachHang.cboDoiTac = null;
          });
        }
        // else {
        //
      }

      for (let i = 0; i < daset.length; i++) {
        if (daset[i].daucuoi_id == 1) {
          // dữ liệu tổng đài

          if (!!daset[i].tramtb_id) this.tramtb_dau_id = daset[i].tramtb_id;
          daset[i].port_id = 218532;

          if (!!daset[i].port_id) {
            this.port_dau_id = daset[i].port_id;
            this.port_dau_id_tmp = daset[i].port_id;
          }

          if (!!daset[i].vci_vpi_id) {
            this.vci_vpi_dau_id = daset[i].vci_vpi_id;

            this.vci_vpi_dau_id_tmp = daset[i].vci_vpi_id;
          }

          this.form_ThongTinHoanCong_DD.txtSvlan_Dau = daset[i].vpi;
          this.form_ThongTinHoanCong_DD.txtCvlan_Dau = daset[i].vci;
          this.form_ThongTinHoanCong_DD.txtAdsl_cap_Dau = daset[i].port_mdf;
          this.form_ThongTinHoanCong_DD.txtAdsl_port_Dau = daset[i].adsl_port;
          this.form_ThongTinHoanCong_DD.txtPort_Dau = daset[i].port;

          if (
            !!daset[i].selfid01 &&
            !!daset[i].selfid02 &&
            !!daset[i].selfid03 &&
            !!daset[i].selfid04
          ) {
            this.form_ThongTinHoanCong_DD.txtNasport_id_Dau =
              daset[i].system +
              "/" +
              daset[i].selfid01 +
              "/" +
              daset[i].selfid02 +
              "/" +
              daset[i].selfid03 +
              "/" +
              daset[i].selfid04;
          } else {
            this.form_ThongTinHoanCong_DD.txtNasport_id_Dau = daset[i].system;
          }

          this.form_ThongTinHoanCong_DD.txtIpDslam_Dau = daset[i].ip;

          this.form_ThongTinHoanCong_DD.txtTimeSlot_Dau = daset[i].timeslot;
          this.form_ThongTinHoanCong_DD.txtTBi_Dau = daset[i].ten_tbi;
          this.form_ThongTinHoanCong_DD.txtSoAoDau = daset[i].ma_lt;
          this.form_ThongTinHoanCong_DD.txtLink_Dau = daset[i].link;
          this.form_ThongTinHoanCong_DD.txtSerial_Dau = daset[i].serial;
          this.form_ThongTinHoanCong_DD.txtVrf_Dau = daset[i].vrf;
          this.form_ThongTinHoanCong_DD.txtIpwan_Dau = daset[i].ip_wan;

          // end dữ liệu tổng đài

          // dữ liệu cáp
          this.form_ThongTinHoanCong_DD.txtTuyenCap_Dau = daset[i].tuyencap;
          this.form_ThongTinHoanCong_DD.txtLienTu_Dau = daset[i].lientu;
          this.form_ThongTinHoanCong_DD.txtTuCapNgon_Dau = daset[i].tucap_p;
          this.form_ThongTinHoanCong_DD.txtDoiCapNgon_Dau = daset[i].doicap_p;
          this.form_ThongTinHoanCong_DD.txtHopCapNgon_Dau = daset[i].hopcap_p;

          if (!!daset[i].loaicap_id) {
            this.form_ThongTinHoanCong_DD.cboLoaiCap_Dau = daset[i].loaicap_id;
          }
          this.form_ThongTinHoanCong_DD.txtCapGoc_Dau = daset[i].cap_goc;

          this.form_ThongTinHoanCong_DD.txtCapNgon_Dau = daset[i].cap_p;
          this.form_ThongTinHoanCong_DD.txtDoiCapGoc_Dau = daset[i].doicap_goc;

          this.form_ThongTinHoanCong_DD.txtTuCapGoc_Dau = daset[i].tucap_goc;
          this.form_ThongTinHoanCong_DD.txtSoMetDay_Dau = daset[i].culy;

          // end du lieu cáp
          this.form_ThongTinHoanCong_DD.cboThietBiDau = daset[i].thietbidc_id;
          this.form_ThongTinHoanCong_DD.txtDiaChiDau = daset[i].diachi;

          // const lienHe = await this.getLienHe(1);
          // if (!!lienHe && lienHe.length > 0) {
          //   this.form_ThongTinHoanCong_DD.lblLienHe_Dau = lienHe[0].lienhe;
          // }

          // const lienHeCuoi = await this.getLienHe(2);
          // if (!!lienHeCuoi && lienHeCuoi.length > 0) {
          //   this.form_ThongTinHoanCong_DC.lblLienHe_Dau =
          //     lienHeCuoi[0].lienhe;
          // }
        } else {
          if (!!daset[i].tramtb_id) this.tramtb_cuoi_id = daset[i].tramtb_id;
          daset[i].port_id = 218532;
          if (!!daset[i].port_id) {
            this.port_cuoi_id = daset[i].port_id;
            this.port_cuoi_id_tmp = daset[i].port_id;
          }

          if (!!daset[i].vci_vpi_id) {
            this.vci_vpi_cuoi_id = daset[i].vci_vpi_id;

            this.vci_vpi_cuoi_id_tmp = daset[i].vci_vpi_id;
          }

          this.form_ThongTinHoanCong_DC.txtSvlan_Cuoi = daset[i].vpi;

          this.form_ThongTinHoanCong_DC.txtCvlan_Cuoi = daset[i].vci;
          this.form_ThongTinHoanCong_DC.txtAdsl_cap_Cuoi = daset[i].port_mdf;
          this.form_ThongTinHoanCong_DC.txtAdsl_port_Cuoi = daset[i].adsl_port;
          this.form_ThongTinHoanCong_DC.txtPort_Cuoi = daset[i].port;

          if (
            !!daset[i].selfid01 &&
            !!daset[i].selfid02 &&
            !!daset[i].selfid03 &&
            !!daset[i].selfid04
          ) {
            this.form_ThongTinHoanCong_DC.txtNasport_id_Cuoi =
              daset[i].system +
              "/" +
              daset[i].selfid01 +
              "/" +
              daset[i].selfid02 +
              "/" +
              daset[i].selfid03 +
              "/" +
              daset[i].selfid04;
          } else {
            this.form_ThongTinHoanCong_DC.txtNasport_id_Cuoi = daset[i].system;
          }

          this.form_ThongTinHoanCong_DC.txtIpDslam_Cuoi = daset[i].ip;

          this.form_ThongTinHoanCong_DC.txtTimeSlot_Cuoi = daset[i].timeslot;
          this.form_ThongTinHoanCong_DC.txtTbi_Cuoi = daset[i].ten_tbi;
          this.form_ThongTinHoanCong_DC.txtSoAoCuoi = daset[i].ma_lt;
          this.form_ThongTinHoanCong_DC.txtLink_Cuoi = daset[i].link;
          this.form_ThongTinHoanCong_DC.txtSerial_Cuoi = daset[i].serial;
          this.form_ThongTinHoanCong_DC.txtVrf_Cuoi = daset[i].vrf;
          this.form_ThongTinHoanCong_DC.txtIpwan_Cuoi = daset[i].ip_wan;

          // end dữ liệu tổng đài

          // dữ liệu cáp
          this.form_ThongTinHoanCong_DC.txtTuyenCap_Cuoi =
            daset[i].tuyencap || "";
          this.form_ThongTinHoanCong_DC.txtLienTu_Cuoi = daset[i].lientu;
          this.form_ThongTinHoanCong_DC.txtTuCapNgon_Cuoi = daset[i].tucap_p;
          this.form_ThongTinHoanCong_DC.txtDoiCapNgon_Cuoi = daset[i].doicap_p;
          this.form_ThongTinHoanCong_DC.txtHopCapNgon_Cuoi = daset[i].hopcap_p;

          if (!!daset[i].loaicap_id) {
            this.form_ThongTinHoanCong_DC.cboLoaiCap_Cuoi = daset[i].loaicap_id;
          }
          this.form_ThongTinHoanCong_DC.txtCapGoc_Cuoi = daset[i].cap_goc;
          this.form_ThongTinHoanCong_DC.txtCapNgon_Cuoi = daset[i].cap_p;
          this.form_ThongTinHoanCong_DC.txtDoiCapGoc_Cuoi = daset[i].doicap_goc;
          this.form_ThongTinHoanCong_DC.txtTuCapGoc_Cuoi = daset[i].tucap_goc;
          this.form_ThongTinHoanCong_DC.txtSoMetDay_Cuoi = daset[i].culy;

          // end dữ liệu cáp
          this.form_ThongTinHoanCong_DC.cboThietBiCuoi = daset[i].thietbidc_id;
          this.form_ThongTinHoanCong_DC.txtDiaChiCuoi = daset[i].diachi;

          // string str = "select * from " + DatabaseConstants.DB2 + ".hdtb_dv where hdtb_id = " + hdtb_id + " and loaidv_id = " + LOAI_DV.TRAM_VT + " and kieudv_id = 2";
          // DataTable dtTemp = bangts.GetDataSql(str);
          // if (dtTemp.Rows.Count > 0)
          // {
          //     cboDonViCuoi.SelectedValue = dtTemp.Rows[0]["donvi_id"].ToString();
          //     tramvt_cuoi_id = Convert.ToInt32(dtTemp.Rows[0]["donvi_id"].ToString());
          // }

          // lblLienHe_Cuoi.Text = "Liên hệ : " + bangts.Lay_thongtin_lh_lapdat_tsl(hdkh_id, hdtb_id, 2);

          // const lienHeCuoi = await this.getLienHe(2);
          // if (!!lienHeCuoi && lienHeCuoi.length > 0) {
          //   this.form_ThongTinHoanCong_DC.lblLienHe_Dau =
          //     lienHeCuoi[0].lienhe;
          // }
        }
      }
    },
    HienThiDSNV(ds) {
      console.log(ds);
      if (ds.length > 0) {
        this.form_ThongTinGiaoViec.chkNgayGV = true;
        this.form_ThongTinGiaoViec.dtpNgayGV = ds[0].ngaygiao;
        this.form_ThongTinGiaoViec.cboNguoiGV = ds[0].nhanvien_giao_id;
        this.form_ThongTinGiaoViec.table_DSNV.options = ds;
      } else {
        // this.options_ThongTinGiaoViec.nguoiGV = [];
        if (!this.PHAILAM(PHAILAM.GIAOVIEC)) {
          this.form_ThongTinGiaoViec.chkNgayGV = true;
          this.form_ThongTinGiaoViec.dtpNgayGV = moment().format("DD/MM/YYYY");
        } else {
          this.form_ThongTinGiaoViec.chkNgayGV = false;
          this.form_ThongTinGiaoViec.dtpNgayGV = moment().format("DD/MM/YYYY");
        }
        // cboNguoiGV.Text = "";
        this.form_ThongTinGiaoViec.cboNguoiGV =
          this.$root.token.getNhanVienID();
      }
    },
    PHAILAM(code) {
      return (
        this.danhSachThaoTac.findIndex(
          (item) => item.enable === 1 && item.CODE === code
        ) >= 0
      );
    },
    async getDSDiaChi() {
      const ds_diachi = await this.$store.dispatch(
        "installMPLSByTeleTechTeam-module/fetchLay_DiaChi_LD",
        {
          hdtb_id: this.form_ThongTinKhachHang.hdtb_id,
          dichvuvt_id: 9,
          kieu: 2,
        }
      );

      if (ds_diachi.data.data.length > 0) {
        for (let j = 0; j < ds_diachi.data.data.length; j++) {
          if (ds_diachi.data.data[j].daucuoi_id == "1")
            this.form_ThongTinHoanCong_DD.txtDiaChiDau_Cu =
              ds_diachi.data.data[j].diachi_daucuoi_cu;
          if (ds_diachi.data.data[j].daucuoi_id == "2")
            this.form_ThongTinHoanCong_DC.txtDiaChiCuoi_Cu =
              ds_diachi.data.data[j].diachi_daucuoi_cu;
        }
      }
    },
    async getHen() {
      const bangts = await this.$store.dispatch(
        "installMPLSByTeleTechTeam-module/fetchKIEMTRA_HEN_HDTB",
        { hdkh_id: this.form_ThongTinKhachHang.hdtb_id }
      );
      this.form_ThongTinKhachHang.chkHen = !!bangts.data.data[0].c;
    },
    async getDSDonVi() {
      const ds_donviDD = await this.$store.dispatch(
        "installMPLSByTeleTechTeam-module/fetchDSHDTBDBKieudv",
        {
          hdtb_id: this.form_ThongTinKhachHang.hdtb_id,
          loaidv_id: config.TRAM_VT,
          kieudv_id: 1,
        }
      );

      if (ds_donviDD.data.data.length > 0) {
        this.form_ThongTinHoanCong_DD.cboDonViDau =
          ds_donviDD.data.data[0].donvi_id;
      }

      const ds_donviDC = await this.$store.dispatch(
        "installMPLSByTeleTechTeam-module/fetchDSHDTBDBKieudv",
        {
          hdtb_id: this.form_ThongTinKhachHang.hdtb_id,
          loaidv_id: config.TRAM_VT,
          kieudv_id: 2,
        }
      );
      if (ds_donviDC.data.data.length > 0) {
        this.form_ThongTinHoanCong_DC.cboDonViCuoi =
          ds_donviDC.data.data[0].donvi_id;
      }
    },
    async getHT_PHIEU_TRA_TSL() {
      const dstraDD = await this.$store.dispatch(
        "installMPLSByTeleTechTeam-module/fetchHT_PHIEU_TRA_TSL",
        { phieu_id: this.form_ThongTinKhachHang.phieu_id, ds_daucuoi_id: "0,1" }
      );

      if (dstraDD.data.data.length > 0) {
        this.form_ThongTinHoanCong_DD.txtNoiDungTra_Dau =
          dstraDD.data.data[0].nd_tra;
        const lydotra_cuoi_id = await this.$store.dispatch(
          "installMPLSByTeleTechTeam-module/fetchlydotra",
          {
            id_neo: "lydotra",
            in_table: "css.lydotra_tc",
            in_dk: ` lydotra_id =${dstraDD.data.data[0].lydotra_id}`,
          }
        );

        this.form_ThongTinHoanCong_DC.txtLyDoTra_Cuoi =
          lydotra_cuoi_id.data.data;
        const lydotra_dau_id = await this.$store.dispatch(
          "installMPLSByTeleTechTeam-module/fetchlydotra",
          {
            id_neo: "lydotra",
            in_table: "css.lydotra_tc",
            in_dk: ` lydotra_id =${dstraDD.data.data[0].lydotra_id} `,
            // in_dk: `where lydotra_id = 54 `,
          }
        );
        this.form_ThongTinHoanCong_DD.txtLyDoTra_Dau = lydotra_dau_id.data.data;
      } else {
        this.form_ThongTinHoanCong_DD.txtNoiDungTra_Dau = "";
        this.form_ThongTinHoanCong_DD.txtLyDoTra_Dau = "";
      }

      const dstraDC = await this.$store.dispatch(
        "installMPLSByTeleTechTeam-module/fetchHT_PHIEU_TRA_TSL",
        { phieu_id: this.form_ThongTinKhachHang.phieu_id, ds_daucuoi_id: "2" }
      );

      if (dstraDC.data.data.length > 0) {
        this.form_ThongTinHoanCong_DC.txtNoiDungTra_Cuoi =
          dstraDC.data.data[0].nd_tra;
        //TODO

        // txtLyDoTra_Cuoi.Text = checkdata.MAP_ID("lydotra", DatabaseConstants.DB2 + ".lydotra_tc ", "where lydotra_id = " + lydotra_cuoi_id);
      } else {
        this.form_ThongTinHoanCong_DC.txtNoiDungTra_Cuoi = "";
        this.form_ThongTinHoanCong_DC.txtLyDoTra_Cuoi = "";
      }
    },
    async getKenhThu() {
      await this.$store
        .dispatch("installMPLSByTeleTechTeam-module/fetchKenhThu", {
          hdkh_id: this.form_ThongTinKhachHang.hdkh_id,
        })
        .then((response) => {
          if (response.data.message == "Success") {
            this.form_ThongTinChung.cboKenhThu =
              response.data.data[0].kenhthu_id;
          }
        });
    },
    async getLienHe(daucuoi) {
      const response = await this.$store.dispatch(
        "installMPLSByTeleTechTeam-module/fetchThongTinLH",
        {
          hdtb_id: this.form_ThongTinKhachHang.hdtb_id,
          hdkh_id: this.form_ThongTinKhachHang.hdkh_id,
          daucuoi_id: 1,
        }
      );
      const lienHe = response.data.data;
      if (!!lienHe && lienHe.length > 0) {
        this.form_ThongTinHoanCong_DD.lblLienHe_Dau = lienHe[0].lienhe;
      } else {
        this.form_ThongTinHoanCong_DD.lblLienHe_Dau = "";
      }

      // const  = await this.getLienHe(1);
      const responseDC = await this.$store.dispatch(
        "installMPLSByTeleTechTeam-module/fetchThongTinLH",
        {
          hdtb_id: this.form_ThongTinKhachHang.hdtb_id,
          hdkh_id: this.form_ThongTinKhachHang.hdkh_id,
          daucuoi_id: 2,
        }
      );
      const lienHeCuoi = responseDC.data.data;
      if (!!lienHeCuoi && lienHeCuoi.length > 0) {
        this.form_ThongTinHoanCong_DD.lblLienHe_Cuoi = lienHeCuoi[0].lienhe;
      } else {
        this.form_ThongTinHoanCong_DD.lblLienHe_Cuoi = "";
      }
    },
    async getComboBox() {
      try {
        Vue.prototype.loading(true);
        this.$store
          .dispatch("installMPLSByTeleTechTeam-module/fetchKieuHD", {})
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_thongTinChung.options_kieuHD = response.data.data;
            }
          });
        this.$store
          .dispatch("installMPLSByTeleTechTeam-module/fetchDanhSachKenhThu", {})
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_thongTinChung.options_kenhThu = response.data.data;
            }
          });

        //  get form thong tin khach hang

        this.$store
          .dispatch("installMPLSByTeleTechTeam-module/fetchDanhSachDoiTac", {})
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_ThongTinKhachHang.options_doiTac =
                response.data.data;
            }
          });

        this.$store
          .dispatch("installMPLSByTeleTechTeam-module/fetchTocDoKenh", {})
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_ThongTinKhachHang.options_tocDoKenh =
                response.data.data;
              this.options_ThongTinKhachHang.options_tocDoKenh.map(function (
                x
              ) {
                return (x.item_tocDo = x.TOCDO + "" + x.DONVI);
              });
            }
          });

        this.$store
          .dispatch("installMPLSByTeleTechTeam-module/fetchLoaiHinhTB", {})
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_ThongTinKhachHang.options_loaiHinh =
                response.data.data;
            }
          });

        this.getLoaiHD();
        // end  get form thong tin khach hang

        this.$store
          .dispatch("installMPLSByTeleTechTeam-module/fetchLoaiCap", {})
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_ThongTinHoanCong_DD.options_loaiCap =
                response.data.data;

              this.options_ThongTinHoanCong_DC.options_loaiCap =
                response.data.data;
            }
          });

        this.$store
          .dispatch("installMPLSByTeleTechTeam-module/fetchNhanVienGV", {
            donvi_id: this.$root.token.getDonViID(),
          })
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_ThongTinGiaoViec.nguoiGV = response.data.data;
            }
          });

        this.$store
          .dispatch("installMPLSByTeleTechTeam-module/fetchDonViCuoi", { loaidv_id: 5 })
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_ThongTinHoanCong_DC.options_donVi =
                response.data.data;
              this.options_ThongTinHoanCong_DD.options_donVi =
                response.data.data;
            }
          });
        this.$store
          .dispatch("installMPLSByTeleTechTeam-module/fetchDanhSachChuQuan", {})
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_ThongTinKhachHang.options_chuQuan =
                response.data.data;
            }
          });
        this.$store
          .dispatch("installMPLSByTeleTechTeam-module/fetchDanhSachThietBi", {
            dichvuvt_id: 9,
          })
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_ThongTinHoanCong_DC.options_thietBi =
                response.data.data;
              this.options_ThongTinHoanCong_DD.options_thietBi =
                response.data.data;
            }
          });
      } catch (err) {
        return this.$toast.error(
          "Có lỗi xảy ra, vui lòng lin hệ Quản trị để được hỗ trợ!"
        );
      } finally {
        Vue.prototype.loading(false);
      }
    },
    async getLoaiHD() {
      this.$store
        .dispatch("installMPLSByTeleTechTeam-module/fetchLoaiHD", { dsloaihd_id: 1 })
        .then((response) => {
          if (response.data.message == "Success") {
            var array2 = [1, 3, 6, 30];
            if (response.data.data.length > 0)
              var array1 = response.data.data.filter(function (item) {
                return array2.includes(item.LOAIHD_ID);
              });

            this.options_ThongTinKhachHang.options_loaiHD = array1 || [];
            this.form_ThongTinKhachHang.cboLoaiHD =
              this.options_ThongTinKhachHang.options_loaiHD[0].LOAIHD_ID;
          }
        });
    },
    async getFormThongTinGiaoViec() {
      try {
        Vue.prototype.loading(true);
        await this.$store
          .dispatch("installMPLSByTeleTechTeam-module/fetchDanhSachNhanVienID", {
            phieu_id: this.form_ThongTinKhachHang.phieu_id,
            nhanvien_id:  this.$root.token.getNhanVienID(),
            kieu_id: 2,
          })
          .then((response) => {
            if (response.data.message == "Success") {
              this.form_ThongTinGiaoViec.table_DSNV.options =
                response.data.data;
            } else {
              this.form_ThongTinGiaoViec.table_DSNV.options = [];
            }
          });
      } catch (error) {
        Vue.prototype.loading(false);
      }
      Vue.prototype.loading(false);
    },
    async btnLayTTOnClick() {
      Vue.prototype.loading(true);

      this.getTableDSThueBao();
    },
    btnCapNhatOnClick() {
      alert("btnCapNhatOnClick");
    },
    btnGiaoPhieuOnClick() {
      alert("btnGiaoPhieuOnClick");
    },
    btnKhoiPhucSoOnClick() {
      alert("btnKhoiPhucSoOnClick");
    },
    async tsbtnHuy_HC_Click() {
      if (this.form_DanhSachThueBao.table.options.length == 0) {
        this.$toast.error("Chưa có danh thuê bao. Bạn hãy kiểm tra lại!");

        return;
      }
      const isConfirm = await this.$bvModal.msgBoxConfirm(
        `Bạn thật sự muốn hủy hoàn công không ?`,
        {
          title: "Thông báo",
          size: "xs",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        }
      );
      if (isConfirm) {
        this.$store
          .dispatch("installMPLSByTeleTechTeam-module/fetchCapnhat_tt_phieugiao", {
            phieu_id: this.form_ThongTinKhachHang.phieu_id,
            ttph_id: 1,
            ngay_th: moment().format("DD/MM/YYYY hh:mm:ss"),
          })
          .then((response) => {
            if (response.data.message == "Success") {
              this.$toast.success("Hủy hoàn công thành công");
              this.form_DanhSachThueBao.rdoPhieu = 0;
            }
          })
          .catch((error) => {
            if (!error.response.data.message) {
              this.$toast.error(error.response.data.message);
            }
          });
      }
    },
    btnHoanThanhOnClick() {
      if (this.form_DanhSachThueBao.table.options.length <= 0) {
        this.$toast.error("Chưa có danh thuê bao. Bạn hãy kiểm tra lại! ");
        return;
      }
      //Hiếu thêm hàm kiểm tra dữ liệu nhập ngày 03/09/2010
      if (!this.KiemTra_DL()) return;
      console.log(!this.form_DanhSachThueBao.table.currentRecord);
      if (!this.form_DanhSachThueBao.table.currentRecord) {
        this.$toast.error("Hiện tại chưa có phiếu yêu cầu khảo sát");
        return;
      }
      if (!this.form_ThongTinKhachHang.phieu_id) {
        this.$toast.error("Chưa chọn phiếu để thực hiện!");
        return;
      }
      const data = [
        {
          LUONG_ID: this.luong_id,
          HDTB_ID: this.form_ThongTinKhachHang.hdtb_id,
          MA_TB: this.form_ThongTinKhachHang.txtMaTB,
          PHIEU_ID: this.form_ThongTinKhachHang.phieu_id,
          NHANVIEN_ID: this.form_ThongTinKhachHang.nhanvien_id,
          DONVI_ID: this.form_ThongTinKhachHang.cboChuQuan,
          MAY_CN: "test",
          NGUOI_CN: "test",
          IP_CN: "test",
          HUONGGIAO_ID: this.huonggiao_id,
          LOAIHD_ID: this.form_ThongTinKhachHang.cboLoaiHinh,
          NGAY_BG: this.form_ThongTinGiaoViec.dtpNgayBG,
        },
      ];
      this.$store
        .dispatch("installMPLSByTeleTechTeam-module/PostHoanhThanh", {
          vds: JSON.stringify(data).toString(),
          vkieu: 2,
        })
        .then((response) => {
          if (response.data.message == "Success") {
            this.$toast.success("Hoàn Thành hoàn công thành công");
            Vue.prototype.loading(true);

            this.form_DanhSachThueBao.rdoPhieu = 0;
            this.getTableDSThueBao();
          }
        })
        .catch((error) => {
          if (!error.response.data.message) {
            this.$toast.error(error.response.data.message);
          }
        });
    },
    btnHoanThien() {
      if (this.form_DanhSachThueBao.table.options.length <= 0) {
        this.$toast.error("Chưa có danh thuê bao. Bạn hãy kiểm tra lại! ");
        return;
      }

      const data = [
        {
          LUONG_ID: this.luong_id,
          HDTB_ID: this.form_ThongTinKhachHang.hdtb_id,
          PHIEU_ID: this.form_ThongTinKhachHang.phieu_id,
          NHANVIEN_ID: this.form_ThongTinKhachHang.nhanvien_id,
          DONVI_ID: this.form_ThongTinKhachHang.cboChuQuan,
          MAY_CN: "test",
          NGUOI_CN: "test",
          IP_CN: "test",
          LOAIHD_ID: this.form_ThongTinKhachHang.cboLoaiHinh,
          NGAY_BG: this.form_ThongTinGiaoViec.dtpNgayBG,
        },
      ];
      this.$store
        .dispatch("installMPLSByTeleTechTeam-module/PostHoanThien", {
          vds: JSON.stringify(data).toString(),
          vkieu: 0,
        })
        .then((response) => {
          if (response.data.message == "Success") {
            this.$toast.success("Hoàn thiện hoàn công thành công");
            Vue.prototype.loading(true);

            this.form_DanhSachThueBao.rdoPhieu = 0;
            this.getTableDSThueBao();
          }
        })
        .catch((error) => {
          if (!error.response.data.message) {
            this.$toast.error(error.response.data.message);
          }
        });
    },
    btnKichHoatOnClick() {
      alert("btnKichHoatOnClick");
    },
    btnHuyKichHoatOnClick() {
      alert("btnHuyKichHoatOnClick");
    },
    btnDauNoiOnClick() {
      alert("btnDauNoiOnClick");
    },
    btnXuatPhieuOnClick() {
      alert("btnXuatPhieuOnClick");
    },
    btnChuyenToOnClick() {
      // alert("btnChuyenToOnClick");
      this.$refs.popupChuyenTo.openDialog(
        this.form_ThongTinKhachHang.phieu_id,
        this.form_ThongTinKhachHang.hdtb_id
      );
    },
    btnHenKHOnClick() {
      this.$refs.popupThongTinHenKH.showModal();
    },
    btnPhieuTonOnClick() {
      this.$refs.tonPhieuGiaoModal.showModal();
    },
    btnDoKiemOnClick() {
      this.$refs.doKiem.showModal();
    },
    btnGiaoViecOnClick() {
      this.$refs.formgiaphieu.init();
      this.$refs.giaophieunhanvien.show();
    },
    btnTraPhieuOnClick() {
      alert("btnTraPhieuOnClick");
    },
    btnNDTHOnClick() {
      if (!this.form_ThongTinKhachHang.phieu_id) {
        this.$toast.error("Vui lòng chọn một thuê bao để thực hiện");
        return false;
      }
      if (!this.form_ThongTinHoanCong_DD.txtNoiDungTH) {
        this.$toast.error("Vui lòng nhập Nội dung TH");
        return false;
      }

      this.$store
        .dispatch("installMPLSByTeleTechTeam-module/PostCapnhat_noidung_th", {
          noidung: this.form_ThongTinHoanCong_DD.txtNoiDungTH.trim(),
          phieu_id: this.form_ThongTinKhachHang.phieu_id,
        })
        .then((response) => {
          if (response.data.message == "Success") {
            this.form_ThongTinHoanCong_DD.txtNoiDungTH = "";
            this.$toast.success("Cập nhật Nội dung TH thành công");
          } else {
            this.$toast.error(
              "Có lỗi xảy ra, vui lòng lin hệ Quản trị để được hỗ trợ!"
            );
          }
        })
        .catch((error) => {
          console.log(error.response);
          if (!error.response.data.message) {
            this.$toast.error(error.response.data.message);
          }
        });
    },
    setDisplayDropDownMenu() {
      this.isDisplayMenu = !this.isDisplayMenu;
    },
    setDisplayTableDD() {
      this.form_ThongTinHoanCong_DD.isDisplayTable =
        !this.form_ThongTinHoanCong_DD.isDisplayTable;
    },
    setDisplayTableDC() {
      this.form_ThongTinHoanCong_DC.isDisplayTable =
        !this.form_ThongTinHoanCong_DC.isDisplayTable;
    },
    hideDropdownMenu(e) {
      if (e.target !== this.$refs.mainWrap) {
        this.isDisplayMenu = false;
      }
      // if (
      //   e.target !== this.$refs.tableDC.$refs.DCref &&
      //   e.target !== this.$refs.tableDC.$refs.childrenDCref
      // )
      //   this.form_ThongTinHoanCong_DC.isDisplayTable = false;
      //   e.target !== this.$refs.tableDD.$refs.DDref &&
      //   e.target !== this.$refs.tableDD.$refs.childrenDDref && e.target !== this.$refs.tableDD.$refs.childrenDDrefTable
      // )
      //   this.form_ThongTinHoanCong_DD.isDisplayTable = false;
    },

    clearForm() {
      this.form_ThongTinKhachHang.phieu_id = null;
      this.form_ThongTinKhachHang.hdtb_id = null;
      this.form_ThongTinKhachHang.hdkh_id = null;
      this.form_ThongTinKhachHang.cboLoaiHinh = null;
      this.form_ThongTinKhachHang.chkHen = false;
      // hdtt_id = 0;

      // this.form_ThongTinKhachHang.cboQuyTrinh = null;
      this.form_ThongTinHoanCong_DC.txtSoSoiQuang = "";
      // thuebao_id = 0;

      // tramtb_dau_id = 0;
      // port_dau_id = 0;
      // vci_vpi_dau_id = 0;
      this.form_ThongTinHoanCong_DD.txtPort_Dau = "";

      // tramtb_cuoi_id = 0;
      // port_cuoi_id = 0;
      // vci_vpi_cuoi_id = 0;
      this.form_ThongTinHoanCong_DC.txtPort_Cuoi = "";

      this.form_ThongTinKhachHang.txtMaTB = "";
      this.form_ThongTinHoanCong_DD.txtSoAoDau = "";
      this.form_ThongTinHoanCong_DC.txtSoAoCuoi = "";
      // kt_thaydoi = false;
      this.form_ThongTinGiaoViec.txtNhanVien = "";
      this.form_ThongTinChung.txtNV_TiepThi = "";
      //cboKieuHD.SelectedValue = KIEU_HD.TAI_NHA;
      this.form_ThongTinHoanCong_DD.lblLienHe_Dau = "";
      // kieu = 0;
      // daucuoi_id = 0;
      this.form_ThongTinKhachHang.txtMaGD = "";
      this.form_ThongTinKhachHang.txtTenTB = "";
      this.form_ThongTinKhachHang.txtDiaChiTB = "";
      this.form_ThongTinKhachHang.txtTenKieuLD = "";

      //dtgDanhSach.DataSource = null;
      // gridDanhSach.DataSource = null;
      // kieu = 0;
      // chkNgayBG.Checked = false;
      this.form_ThongTinGiaoViec.cboNguoiGV = "";
      this.form_ThongTinHoanCong_DD.txtNasport_id_Dau = "";
      this.form_ThongTinHoanCong_DC.txtNasport_id_Cuoi = "";
      this.form_ThongTinHoanCong_DC.txtIpDslam_Cuoi = "";
      this.form_ThongTinHoanCong_DD.txtIpDslam_Dau = "";

      this.form_ThongTinHoanCong_DD.txtTimeSlot_Dau = "";
      this.form_ThongTinHoanCong_DC.txtTimeSlot_Cuoi = "";
      this.form_ThongTinHoanCong_DD.txtTBi_Dau = "";
      this.form_ThongTinHoanCong_DC.txtTbi_Cuoi = "";
      this.form_ThongTinHoanCong_DD.txtVrf_Dau = "";
      this.form_ThongTinHoanCong_DC.txtVrf_Cuoi = "";
      this.form_ThongTinHoanCong_DD.txtIpwan_Dau = "";
      this.form_ThongTinHoanCong_DC.txtIpwan_Cuoi = "";

      this.form_ThongTinHoanCong_DD.txtTuCapGoc_Dau = "";
      this.form_ThongTinHoanCong_DD.txtDoiCapGoc_Dau = "";
      this.form_ThongTinHoanCong_DD.txtCapGoc_Dau = "";
      this.form_ThongTinHoanCong_DD.txtHopCapNgon_Dau = "";
      this.form_ThongTinHoanCong_DD.txtTuCapNgon_Dau = "";
      this.form_ThongTinHoanCong_DD.txtDoiCapNgon_Dau = "";
      this.form_ThongTinHoanCong_DD.txtCapNgon_Dau = "";
      this.form_ThongTinHoanCong_DD.txtTuyenCap_Dau = "";
      this.form_ThongTinHoanCong_DD.txtLienTu_Dau = "";
      this.form_ThongTinHoanCong_DD.txtSoMetDay_Dau = "0";
      this.form_ThongTinHoanCong_DD.txtLink_Dau = "";
      this.form_ThongTinHoanCong_DD.txtSerial_Dau = "";

      this.form_ThongTinHoanCong_DC.txtTuCapGoc_Cuoi = "";
      this.form_ThongTinHoanCong_DC.txtDoiCapGoc_Cuoi = "";
      this.form_ThongTinHoanCong_DC.txtCapGoc_Cuoi = "";
      this.form_ThongTinHoanCong_DC.txtHopCapNgon_Cuoi = "";
      this.form_ThongTinHoanCong_DC.txtTuCapNgon_Cuoi = "";
      this.form_ThongTinHoanCong_DC.txtDoiCapNgon_Cuoi = "";
      this.form_ThongTinHoanCong_DC.txtCapNgon_Cuoi = "";
      this.form_ThongTinHoanCong_DC.txtTuyenCap_Cuoi = "";
      this.form_ThongTinHoanCong_DC.txtLienTu_Cuoi = "";
      this.form_ThongTinHoanCong_DC.txtSoMetDay_Cuoi = "0";
      this.form_ThongTinHoanCong_DC.txtLink_Cuoi = "";
      this.form_ThongTinHoanCong_DC.txtSerial_Cuoi = "";

      this.form_ThongTinHoanCong_DD.txtDiaChiDau = "";
      this.form_ThongTinHoanCong_DC.txtDiaChiCuoi = "";

      this.form_ThongTinHoanCong_DD.cboDonViDau = "";

      this.form_ThongTinHoanCong_DC.cboDonViCuoi = "";

      this.form_ThongTinKhachHang.cboChuQuan = null;
      this.form_ThongTinKhachHang.cboTocDo = null;

      // daikv_dau_id = 0;
      // daikv_cuoi_id = 0;
      // tramvt_dau_id = 0;
      // tramvt_cuoi_id = 0;
      // gridNhanVien.DataSource = null;
      // chkNgayGV.Checked = false;

      var dt = new Date();
      var dformat = `${dt.getDate().toString().padStart(2, "0")}/${(
        dt.getMonth() + 1
      )
        .toString()
        .padStart(2, "0")}/${dt.getFullYear().toString().padStart(4, "0")} ${dt
        .getHours()
        .toString()
        .padStart(2, "0")}:${dt.getMinutes().toString().padStart(2, "0")}:${dt
        .getSeconds()
        .toString()
        .padStart(2, "0")}`;

      this.form_ThongTinGiaoViec.dtpNgayGV = dformat;
      this.form_ThongTinGiaoViec.chkNgayGV = false;

      this.form_ThongTinGiaoViec.dtpNgayBG = dformat;
      this.form_ThongTinGiaoViec.chkNgayBG = false;

      // this.form_ThongTinGiaoViec.dtpNgayGV = tt_nd.ngay_cn;

      // khachhang_id = 0;
      // thanhtoan_id = 0;

      // diachikh_id = 0;
      // apkh_id = 0;
      // phokh_id = 0;
      // phuongkh_id = 0;
      // quankh_id = 0;
      // tinhkh_id = 0;
      // sonhakh = "";

      // diachitt_id = 0;
      // phott_id = 0;
      // aptt_id = 0;
      // phuongtt_id = 0;
      // quantt_id = 0;
      // tinhtt_id = 0;
      // sonhatt = "";

      // this.form_ThongTinChung = 0;
    },
  },
};
</script>
<style scoped>
.list-actions-top .more > a:hover {
  list-style: none;
  text-decoration: none !important;
}
</style>