<template src="./DeclareMPLS.html"></template>
<style src="./DeclareMPLS.scss"></style>
<style>
  .modal .modal-hosodientu-size {
    max-width: 85% !important; 
  }

  .modal-dialog {
    max-width: 60vw;
  }

  .modal-body .page-content {
    position: inherit;
  } 
  
  .modal-sm{
    max-width: 500px !important;
  } 

  .bss-modal-tracuu-danhba .popup-box{
    min-height: 700px !important;
  }

  .captrunggian{
    z-index: 10000 !important;
  }
</style>

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
import breadcrumb from "@/components/breadcrumb";
import ThongTinChung from "./part/ThongTinChung";
import ThongTinKhachHang from "./part/ThongTinKhachHang";
import ThongTinGiaoViec from "./part/ThongTinGiaoViec";
import ThongTinHoanCong_DD from "./part/ThongTinHoanCong_DD";
import ThongTinHoanCong_DC from "./part/ThongTinHoanCong_DC";
import DanhSachThueBao from "./part/DanhSachThueBao";
import declareMPLSModule from "./declareMPLSModuleStore"; 
import traphieu from "@/modules/contract/setup/ReturnInstallTicket/components/traphieu.vue";
import ChuyenTo from "@/modules/contract/profile/CompleteProfileChangeAccount/Popups/ChuyenTo.vue";
import api from "./DeclareMPLSAPI.js";
import fn from "./functions.js";
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions"; 
 
// import TonPhieuGiaoModal from "@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/TonPhieuGiao/TonPhieuGiaoModal.vue";
import TonPhieu from "@/modules/contract/setup/Payment/components/TonPhieu.vue";
 
import DoKiemModal from "@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/DoKiem/DoKiemModal.vue";

import frnGiaoPhieuNV from "@/modules/contract/setup/DeclareLandline/popups/frnGiaoPhieuNV.vue";

//import PopupGanKhuVucQuanLyThueBao from "@/modules/contract/setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao.vue";

import PopupThongTinHenKhachHang from "@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue";

import VatTuThueBao from "../ChangeServiceOMCVIPCM/popups/VatTuThueBao.vue";

import daunoithuebao from "@/modules/contract/setup/DeclareLandline/popup/daunoithuebao" 
import ThayDoiKenhThu from "@/modules/QLTN/ThayDoiKenhThu"

import CapVatTuTB from "@/modules/contract/complete_profile/CompleteProfileChangeTSMyTV/popups/CapVatTuTB.vue"
import TraCuuThongSoKTModal from "@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/TraCuuThongSoKT/TraCuuThongSoKTModal.vue"
import DuAnHDTB from "@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/DuAnHDTB/DuAnHDTB"
import frmTraPhieuTC from "../../setup/ReturnInstallTicket/frmTraPhieuTC.vue"  
import InBienBan from "@/modules/print/InBienBan/InBienBan.vue"
import ThayDoiKhuVucQuanLyThueBaoModal from "@/modules/contract/setup/SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue";

// import DauNoiThueBao from "@/modules/CABMAN/DauNoiThueBao/index.vue"
import Vue from "vue";
import * as moment from "moment";

import { config, PHAILAM, TrangThaiHD, LoaiHopDong } from "./constants";
import { DICHVU_VIENTHONG } from "@/constants"; 
import GiaoPhieuView from '@/modules/contract/setup/HandoverTicket/giao_phieu_view.vue';

import CommonAPI from '../ChangeLandlineIMSType/CommonAPI';

//frmHoanCongTSL.cs
export default {
  props: ["tag", "matb_link", "quytrinh_id"],
  components: {
    breadcrumb,
    ThongTinChung,
    ThongTinKhachHang,
    ThongTinGiaoViec,
    ThongTinHoanCong_DD,
    ThongTinHoanCong_DC,
    DanhSachThueBao,
    traphieu,
    ChuyenTo,
    // TonPhieuGiaoModal,
    TonPhieu,
    DoKiemModal,
    frnGiaoPhieuNV,
    //PopupGanKhuVucQuanLyThueBao,
    PopupThongTinHenKhachHang,
	  VatTuThueBao,
    daunoithuebao,
    ThayDoiKenhThu,
    CapVatTuTB,
    TraCuuThongSoKTModal,
    DuAnHDTB,
    frmTraPhieuTC,
    InBienBan,
    ThayDoiKhuVucQuanLyThueBaoModal,
    GiaoPhieuView
  },
  data() {
    return {
      dialogComponent: null,
      modalComponent: null,
      dialogEvts: {
        'close': this.dlgClose
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: "",
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close':this.popupClosed
      },
      position: { X: 'center', Y: 'top' },
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
        title: 'OMC/Vip/Tổ CM- Thay đổi dịch vụ',
        list: [
          {name: 'Lập hợp đồng/Đổi tốc độ', link: {name: 'Ui.cards'}},
          {
            name: 'Loại hình thuê bao/OMC/Vip/Tổ CM- Thay đổi dịch vụ',
            link: {name: 'Ui.buttons'}
          }
        ]
      },
      target: ".main-wrapper",
      xuatPhieuShow: false,
      tthd_id: 9,
      h_tthd_id: 0,
      isEnterMaGD: false,
      showModalTraphieu: false,
      popupGanKhuVucQuanLyThueBao: false,
      kieu: 0,
	    phanvVungID: 0,
      may_cn: this.$root.token.getMachine(),
      ip_cn: this.$root.token.getIP(),
      nguoi_cn: this.$root.token.getNguoiDungID(),
      state: {},
      khachhang_id: 0,
      thanhtoan_id: 0,
      tt_nd: {
        nhanvien_id:  this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        may_cn: this.$root.token.getMachine(),
        ip_cn: this.$root.token.getIP(),
        ngay_cn: moment(new Date()).format("DD/MM/YYYY hh:mm A"),
        ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format(
          "DD/MM/YYYY hh:mm A"
        ),
        ten_dv: "",
        nguoi_cn: this.$root.token.getUserName()
      },
      controls: {
         btnLayTTMoi: {
          visible: true,
          enabled: true,
          type: "menu_button",
        },
        tsbtnChapNhan: {
          visible: true,
          enabled: true,
          type: "menu_button",
        },
        tsbtnGiaoViec: {
          visible: true,
          enabled: true,
          type: "menu_button",
        },
        tsbtnGiaoPhieu: {
          visible: false,
          enabled: true,
          type: "menu_button",
        }, 
        tsddbIn: {
          visible: true,
          enabled: true,
          type: "menu_button",
        },
        btnPhieuTC: {
          visible: true,
          enabled: true,
          type: "menu_button",
        },
        btnBienBanNT: {
          visible: true,
          enabled: true,
          type: "menu_button",
        },
        tsbtnHoanThanh: {
          visible: false,
          enabled: true,
          type: "menu_button",
        }, 
        tsbtnHoanCong: {
          visible: false,
          enabled: true,
          type: "menu_button",
        }, 
        tsbtnTraPhieu: {
          visible: false,
          enabled: true,
          type: "menu_button",
        }, 
        tsbtnVatTu: {
          visible: false,
          enabled: true,
          type: "menu_button",
        },
        tsbtnHen: {
          visible: true,
          enabled: true,
          type: "menu_button",
        },
        tsbtnDauNoiCap: {
          visible: true,
          enabled: true,
          type: "menu_button",
        },
        tsbtnHuy_HC: {
          visible: true,
          enabled: false,
          type: "menu_button",
        },
        tsbtnNVKT: {
          visible: true,
          enabled: true,
          type: "menu_button",
        }, 
        tsbtnVatTuMoi: {
          visible: false,
          enabled: true,
          type: "menu_button",
        },
        tsbtnGiaoPhieu_KBB: {
          visible: false,
          enabled: true,
          type: "menu_button",
        },  
        tsbtnDuAn: {
          visible: false,
          enabled: true,
          type: "menu_button",
        },
        tsbtnThongTinKT: {
          visible: true,
          enabled: true,
          type: "menu_button",
        },
        tsbtnKQ_DoKiem: {
          visible: false,
          enabled: true,
          type: "menu_button",
        }, 
        tsbtnChuyenTo: {
          visible: false,
          enabled: true,
          type: "menu_button",
        },
        tsbtnSua_KenhThu: {
          visible: false,
          enabled: true,
          type: "menu_button",
        }, 
        tsbtnGiaoPhieu_Ton: {
          visible: true,
          enabled: true,
          type: "menu_button",
        },
        tsbtnHoanThienHS: {
          visible: false,
          enabled: false,
          type: "menu_button",
        }, 
        btnChonPort: {
          visible: true,
          enabled: true,
          type: "button",
        }, 
        btnChonPort_C: {
          visible: true,
          enabled: true,
          type: "button",
        },  
        rdoPhieu_Da_TH: {
          visible: false,
          enabled: true,
          type: "radio",
        }, 
        txtSvlan_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        }, 
        txtCvlan_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },  
        txtAdsl_cap_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },    
        txtAdsl_port_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },    
        txtNasport_id_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },
        txtIpDslam_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },
        txtVrf_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },
        txtSerial_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },      
        txtIpwan_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },   
        txtTimeSlot_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },   
        txtLink_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        }, 
        txtCapGoc_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        }, 
        txtCapNgon_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        }, 
        txtTuCapGoc_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },  
        txtTuCapNgon_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },  
        txtDoiCapGoc_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },  
        txtDoiCapNgon_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },    
        txtHopCapNgon_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        }, 
        txtSoMetDay_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        }, 
        txtLienTu_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        }, 
        txtTuyenCap_Dau: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        }, 
        
        // cuoi
        txtSvlan_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        }, 
        txtCvlan_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },  
        txtAdsl_cap_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },    
        txtAdsl_port_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },    
        txtNasport_id_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },
        txtIpDslam_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },
        txtVrf_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },
        txtSerial_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },      
        txtIpwan_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },   
        txtTimeSlot_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },   
        txtLink_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        }, 
        txtCapGoc_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        }, 
        txtCapNgon_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        }, 
        txtTuCapGoc_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },  
        txtTuCapNgon_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },  
        txtDoiCapGoc_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },  
        txtDoiCapNgon_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        },    
        txtHopCapNgon_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        }, 
        txtSoMetDay_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        }, 
        txtLienTu_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        }, 
        txtTuyenCap_Cuoi: {
          visible: true,
          enabled: true,
          readOnly: true,
          type: "textbox",
        }, 
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
      isVisibleThongTin: true,
      isDisplayThongTin: true,
      form_ThongTinKhachHang: {
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
        checkbox_value: false,
        txtDiaChiTB: null,
        phieu_id: null,
        nhanvien_id: null,
        hdkh_id: null,
        hdtb_id: null,
        hdtt_id: null,
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
        dtpNgayGV: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),

        dischkNgayBG: false,
        chkNgayBG: false,
        disNgayBG: true,
        dtpNgayBG: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),

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
		    options_thuebao: {},
        options_hdtb: null,
        options_data: { },
        options_controls: this.controls
      },
      options_ThongTinHoanCong_DC: {
        options_thietBi: [],
        options_loaiCap: [],
        options_donVi: [],
		    options_thuebao: {},
        options_hdtb: null,
        options_data: { },
        options_controls: this.controls
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
      diachiHK: {
                DIACHI: null,
                TINH_ID:0,
                QUAN_ID:0,
                PHUONG_ID:0,
                PHO_ID:0,
                AP_ID:0,
                KHU_ID:0,
                DACDIEM_ID:0,
                SO_NHA:null,
                Lng: 0,
                Lat: 0,
                BLOCK: null,
                TANG: null,
                PHONG: null,
                MOTA: null,
            },
      diachiTT: {
                DIACHI: null,
                TINH_ID:0,
                QUAN_ID:0,
                PHUONG_ID:0,
                PHO_ID:0,
                AP_ID:0,
                KHU_ID:0,
                DACDIEM_ID:0,
                SO_NHA:null,
                Lng: 0,
                Lat: 0,
                BLOCK: null,
                TANG: null,
                PHONG: null,
                MOTA: null,
            },
      table: {},
      dsloaihd_id: 1,
      dichvuvt_id: DICHVU_VIENTHONG.TSL,
      dsdichvuvt_id: "",
	    modelVatTu: {
        phieu_id: 0,
        loaihd_id: 0,
        hdtb_id: 0,
        thuebao_id: 0
      },
      daucuoi_id: 0,
      daikv_dau_id: 0,
      daikv_cuoi_id: 0,
      tramvt_dau_id: 0,
      tramvt_cuoi_id: 0, 
      modelIn: {},
      dataPopupKhuVuc: {},
      giaoPhieuViewDialog: {
        isVisiable: false,
        input: {
          vhuonggiao_id: null,
          vloai_hd: null,
          vdichvuvt_id: null,
          vma_gd: null,
          vquytrinh: null,
          vtrangthaiphieu: 1,
        }
      },
      animationSettings: { effect: 'None' },
    }
  },
  beforeCreate() {
    this.loading(true);

    this.isRouterLeaving = false;
    if (this.$store.hasModule("declareMPLS-module")) {
      this.$store.unregisterModule("declareMPLS-module");
    }
    this.$store.registerModule("declareMPLS-module", declareMPLSModule);
  },

  beforeRouteLeave(to, from, next) {
    this.isRouterLeaving = true;
    next();
  },

  beforeDestroy() {
    if (this.isRouterLeaving)
      this.$store.unregisterModule("declareMPLS-module");
  },
  mounted() {
    this.loading(true);
    console.log(this.$route.query.tag); 
    console.log('tt_nd', this.tt_nd);

    this.tthd_id = 9; 
    let Tag = this.$route.query.tag ? this.$route.query.tag : this.tag;
 
    try
    {
        this.tthd_id = TrangThaiHD.DANG_THI_CONG;
        this.dsloaihd_id = "1";
        this.dsdichvuvt_id = "9";
        
        if (Tag)
        {
            let str = Tag.toString();
            let words = str.split('+');
            if (words.length >= 1)
                this.tthd_id = Number(words[0]);
            if (words.length >= 2)
            {
                this.dsloaihd_id = words[1];
                this.dsloaihd_id = this.dsloaihd_id.replaceAll(';', ',');
            }
            if (words.length >= 3)
            {
                this.dsdichvuvt_id = words[2];
                this.dsdichvuvt_id = this.dsdichvuvt_id.replaceAll(';', ',');
            }
        }
        else
        {
            this.tthd_id = TrangThaiHD.DANG_THI_CONG;
            this.dsloaihd_id = "1";
            this.dsdichvuvt_id = "1";
        }
    }
    catch(ex)
    {
      commonFn.showException(this, ex);
      console.erro('Get Tag ERROR', ex);
      this.tthd_id = TrangThaiHD.DANG_THI_CONG;
      this.dsloaihd_id = "1";
      this.dsdichvuvt_id = "9";
    }
    this.phanvVungID = this.$root.token.getPhanVungID();
    this.pageLoad();
    this.form_ThongTinKhachHang.cboLoaiHD = 1;
    this.form_ThongTinKhachHang.cboLoaiHD = this.dsloaihd_id.split(',')[0]; 
    //this.dichvuvt_id = this.dsdichvuvt_id;

    console.log('this.tthd_id', this.tthd_id);
    console.log('this.dsdichvuvt_id', this.dsdichvuvt_id);
    console.log('this.dsloaihd_id', this.dsloaihd_id);
     
    //this.getComboBox();
  },
  watch: {
    async port_dau_id(newValue, oldValue) {
      const dataPortDau = await this.$store.dispatch(
        "declareMPLS-module/fetchPortDauCuoi",
        {
          port_id: 7402,
        }
      );
      //TODO

      this.form_ThongTinHoanCong_DD.table.options = dataPortDau.data.data || [];
    },
    async port_cuoi_id(newValue, oldValue) {
      const dataPortCuoi = await this.$store.dispatch(
        "declareMPLS-module/fetchPortDauCuoi",
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

    "form_DanhSachThueBao.table.options"(newValue, oldValue) {
      if (newValue != undefined && newValue != null && newValue.length > 0) {
        this.form_DanhSachThueBao.table.currentRecord = newValue[0];
      } else {
        this.clearForm();
        this.form_DanhSachThueBao.table.currentRecord = null;
		    this.options_ThongTinHoanCong_DD.options_thuebao = null;
        this.options_ThongTinHoanCong_DC.options_thuebao = null; 

      }
    },
    async luong_id(n, o) {
      const danhSach = await this.$store.dispatch(
        "declareMPLS-module/fetchDSLuongThaoTac",
        {
          luong_id: n,
        }
      );
      if (danhSach.data.data) {
        this.danhSachThaoTac = danhSach.data.data;
      } else {
        this.clearForm();
        this.form_DanhSachThueBao.table.currentRecord = null;
      }
    },
    "form_DanhSachThueBao.rdoPhieu"(newVal, oldVal) {
      Vue.prototype.loading(true);

      this.form_DanhSachThueBao.table.options = [];
      this.clearForm();
      this.vloai_id = 0; // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
      if (this.form_DanhSachThueBao.rdoPhieu == 0) {
        this.vloai_id = 0;
        this.controls.tsbtnHuy_HC.enabled = false;
      }
      if (this.form_DanhSachThueBao.rdoPhieu == 1) {
        this.vloai_id = 1;
        this.controls.tsbtnHuy_HC.enabled = false;
      }
      if (this.form_DanhSachThueBao.rdoPhieu == 2) {
        this.vloai_id = 2;
        this.controls.tsbtnHuy_HC.enabled = true;
      }

      console.log("--- rdoPhieu ------ getHuongGiaoID -----------");

      this.getTableDSThueBao();
    },

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

	//gvDanhSach_SelectionChanged HienThiTTHopDongTB
    async "form_DanhSachThueBao.table.currentRecord"(newValue, oldValue) {
      const dataRow = JSON.parse(JSON.stringify(newValue));
      console.log('newValue', newValue);
      if (
        dataRow != undefined &&
        dataRow != null &&
        dataRow &&
        Object.keys(dataRow).length > 0
      ) {
        // //  ẩn buttom trên menu vì k có dữ liệu
        // this.controls.tsbtnChapNhan.visible = false;
        // this.controls.tsbtnHoanCong.visible = false;
        // this.controls.tsbtnGiaoPhieu.visible = false;

        // // ẩn
        //this.controls.tsbtnTraPhieu.visible = false;
        // this.controls.tsbtnChuyenTo.visible = false;
        // this.controls.tsbtnGiaoPhieu_Ton.visible = false;
        // this.controls.tsbtnGiaoViec.visible = false;
        this.controls.tsbtnKQ_DoKiem.visible = false;

        this.form_ThongTinKhachHang.dichvuvt_id = dataRow.dichvuvt_id;
        this.form_ThongTinKhachHang.txtMaGD = dataRow.ma_gd;
        this.form_ThongTinKhachHang.txtMaTB = dataRow.ma_tb;
        this.form_ThongTinKhachHang.txtTenKieuLD = dataRow.ten_kieuld;
        this.form_ThongTinKhachHang.cboLoaiHinh = dataRow.loaitb_id;

        this.form_ThongTinKhachHang.txtTenTB = dataRow.ten_tb;
        this.form_ThongTinKhachHang.txtDiaChiTB = dataRow.diachi_tb; 

        this.form_ThongTinKhachHang.phieu_id = dataRow.phieu_id;
        this.form_ThongTinKhachHang.nhanvien_id = dataRow.nhanvien_id;
        this.form_ThongTinKhachHang.cboChuQuan = dataRow.donvi_id;
        this.form_ThongTinHoanCong_DD.txtNDGiao = dataRow.nd_giao;
        this.form_ThongTinHoanCong_DD.txtNoiDungTH = dataRow.nd_thuchien;
        this.form_ThongTinHoanCong_DD.txtGhiChu = dataRow.ghichu;
        this.form_ThongTinKhachHang.hdkh_id = dataRow.hdkh_id; 
        this.form_ThongTinKhachHang.hdtt_id = dataRow.hdtt_id; 
		    this.form_ThongTinKhachHang.thuebao_id = dataRow.thuebao_id;
        this.form_ThongTinKhachHang.loaitb_id = dataRow.loaitb_id;
        this.options_ThongTinHoanCong_DD.options_thuebao = dataRow;
        this.options_ThongTinHoanCong_DC.options_thuebao = dataRow;

        console.log('dataRow', dataRow);

        if (dataRow["daucuoi"] && (dataRow["daucuoi"].toString().toLowerCase().indexOf("dau") > -1
            || dataRow["daucuoi"].toString().toLowerCase().indexOf("đầu")) > -1)
            this.daucuoi_id = 1;
        else
            this.daucuoi_id = 2; 
        this.loading(true);
        if (dataRow.hdkh_id) {
          await this.getKenhThu();
        }

        this.form_ThongTinKhachHang.hdtb_id = dataRow.hdtb_id;
        this.khachhang_id =  dataRow.hdtb_id;
        this.thanhtoan_id =  dataRow.thanhtoan_id;
        if (dataRow.hdtb_id) {
          await this.getDSDiaChi();
          await this.getHen();
          await this.getDSDonVi();
          await this.getDanhSachHopDongThueBaoTLS();
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
            dataRow.ngay_ht_gp, "YYYY-MM-DD HH:mm:ss"
          ).format("DD/MM/YYYY HH:mm:ss");
        } else {
          const date = moment().format("DD/MM/YYYY HH:mm:ss");
          this.form_ThongTinGiaoViec.dtpNgayBG = date;
        } 
        this.form_ThongTinGiaoViec.dtpNgayGV = dataRow.ngaygiao;

        !!dataRow.ngaygiao
          ? (this.form_ThongTinGiaoViec.chkNgayGV = true)
          : (this.form_ThongTinGiaoViec.chkNgayGV = false);

        const infoNV = await this.$store.dispatch(
          "declareMPLS-module/fetchThongTinNhanVien",
          {
            nhanvien_id: dataRow.nhanvien_id,
          }
        );

        this.form_ThongTinGiaoViec.txtNhanVien = infoNV.data.data;
        if (!!dataRow.ctv_id) {
          const infoNVDV = await this.$store.dispatch(
            "declareMPLS-module/fetchThongTinNhanVienDV",
            {
              nhanvien_id: dataRow.ctv_id,
            }
          );
          if(infoNVDV && infoNVDV.data.data.length > 0)
            this.form_ThongTinChung.txtNV_TiepThi = infoNVDV.data.data[0].nhanvien;
          else
            this.form_ThongTinChung.txtNV_TiepThi = "";
        } else {
          this.form_ThongTinChung.txtNV_TiepThi = "";
        }

        if (this.form_DanhSachThueBao.rdoPhieu == 1) {
          await this.getHT_PHIEU_TRA_TSL();
        } else {
          this.form_ThongTinHoanCong_DD.txtNoiDungTra_Dau = "";
          this.form_ThongTinHoanCong_DD.txtLyDoTra_Dau = "";
          this.form_ThongTinHoanCong_DC.txtNoiDungTra_Cuoi = "";
          this.form_ThongTinHoanCong_DC.txtLyDoTra_Cuoi = "";
        }

        if (this.PHAILAM(PHAILAM.CAPNHAT_THONGSO_KYTHUAT)) {
          if (!!dataRow.ngay_ht_gp) {
            if (this.PHAILAM(PHAILAM.GIAOPHIEU))
              this.controls.tsbtnGiaoPhieu.enabled = true;
            else this.controls.tsbtnGiaoPhieu.enabled = false;
          } else this.controls.tsbtnGiaoPhieu.enabled = false;
        }

        if (this.PHAILAM(PHAILAM.CAPNHAT_CAP_TSL)) {
          if (dataRow.ngay_ht_gp != "") {
            if (this.PHAILAM(PHAILAM.GIAOPHIEU))
              this.controls.tsbtnGiaoPhieu.enabled = true;
            else this.controls.tsbtnGiaoPhieu.enabled = false;

            if (this.PHAILAM(PHAILAM.HOANTHANH_PHIEU))
              this.controls.tsbtnHoanThanh.enabled = true;
            else this.controls.tsbtnHoanThanh.enabled = false;

            if (this.PHAILAM(PHAILAM.HOANCONG))
              this.controls.tsbtnHoanCong.enabled = true;
            else this.controls.tsbtnHoanCong.enabled = false;
          } else {
            this.controls.tsbtnHoanThanh.enabled = false;
            this.controls.tsbtnHoanCong.enabled = false;
            this.controls.tsbtnGiaoPhieu.enabled = false;
          }
        }
        if (this.PHAILAM(PHAILAM.HOANCONG)) {
          if (dataRow.ngay_ht_gp != "") {
            this.controls.tsbtnHoanCong.enabled = true;
            if (this.PHAILAM(PHAILAM.GIAOPHIEU))
              this.controls.tsbtnGiaoPhieu.enabled = true;
            else this.controls.tsbtnGiaoPhieu.enabled = false;
          } else {
            this.form_bcontrolsutton.tsbtnHoanCong.enabled = false;
          }
        }
        if (this.PHAILAM(PHAILAM.HOANTHIENHOSO)) {
          if (dataRow.ngay_ht_gp != "")
            this.controls.tsbtnHoanThienHS.enabled = true;
          else this.controls.tsbtnHoanThienHS.enabled = false;
        }

        if (!this.PHAILAM(PHAILAM.GIAOVIEC)) { 
          this.form_ThongTinGiaoViec.chkNgayGV = true;

          this.form_ThongTinGiaoViec.disNgayGV = false;
          this.form_ThongTinGiaoViec.dischkNgayGV = false;          

          this.form_ThongTinGiaoViec.chkNgayBG = true;

          this.form_ThongTinGiaoViec.disNgayBG = false;
          this.form_ThongTinGiaoViec.dischkNgayBG = false;
        } else { 
          this.form_ThongTinGiaoViec.disNgayGV = false;
          this.form_ThongTinGiaoViec.dischkNgayGV = false;

          this.form_ThongTinGiaoViec.disNgayBG = false;
          this.form_ThongTinGiaoViec.dischkNgayBG = false;
        }
        if (this.form_DanhSachThueBao.rdoPhieu == 2) {
          this.controls.tsbtnHen.enabled = false;
          this.controls.tsbtnChapNhan.enabled = false;
          this.controls.tsbtnGiaoViec.enabled = false;
          this.controls.tsbtnTraPhieu.enabled = false;

          if (this.PHAILAM(PHAILAM.GIAOPHIEU)) {
            this.controls.tsbtnGiaoPhieu.enabled = true;
            this.controls.tsbtnHoanCong.enabled = false;
            this.controls.tsbtnHoanThanh.enabled = false;
          } else {
            this.controls.tsbtnGiaoPhieu.enabled = false;
            this.controls.tsbtnHoanCong.enabled = true;
            this.controls.tsbtnHoanThanh.enabled = true;
          }
        } else {
          this.controls.tsbtnHen.enabled = true;
          this.controls.tsbtnChapNhan.enabled = true;
          this.controls.tsbtnGiaoViec.enabled = true;
          this.controls.tsbtnTraPhieu.enabled = true;
          this.controls.tsbtnGiaoPhieu.enabled = true;

          this.controls.tsbtnChuyenTo.enabled = true;
          this.controls.tsbtnGiaoPhieu_Ton.enabled = true;
          this.controls.tsbtnKQ_DoKiem.enabled = true;
        }
        const data = await this.$store.dispatch(
          "declareMPLS-module/fetchDanhSachNhanVienID",
          {
            phieu_id: this.form_ThongTinKhachHang.phieu_id,
            nhanvien_id: this.$root.token.getNhanVienID(),
            kieu_id: 2,
          }
        );

        this.HienThiDSNV(data.data.data);

        this.loading(false);
      } else {
        //  ẩn buttom trên menu vì k có dữ liệu
        this.controls.tsbtnChapNhan.enabled = false;
        this.controls.tsbtnHoanCong.enabled = false;
        this.controls.tsbtnGiaoPhieu.enabled = false;

        // ẩn
        this.controls.tsbtnTraPhieu.enabled = false;
        this.controls.tsbtnChuyenTo.enabled = false;
        this.controls.tsbtnGiaoPhieu_Ton.enabled = false;
        this.controls.tsbtnKQ_DoKiem.enabled = false;

        this.clearForm();
      }
    },
    async "form_ThongTinKhachHang.phieu_id"(newValue, oldValue) {
      try {
        await this.$store
          .dispatch("declareMPLS-module/fetchTongTien", {
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
      } catch (error) {
        console.error(error);
        //commonFn.showException(this, error);
      }
    },
  },
  methods: {
    rowSelected(row) { 
      this.form_DanhSachThueBao.table.currentRecord = row;
    },
    myAccount() {
 

      this.form_DanhSachThueBao.table.options =
        this.form_DanhSachThueBao.table.options.filter(
          (e) => e.ma_tb == this.form_ThongTinKhachHang.txtMaTB
        );
    },
    async HienThiGiaoDien() {
      console.log("HienThiGiaoDien");
      this.controls.tsbtnKQ_DoKiem.visible = false;
      this.controls.tsbtnTraPhieu.visible = false;
      this.controls.tsbtnDuAn.visible = false;
      this.controls.tsbtnChuyenTo.visible = false;
      // đầu. 
      this.controls.txtSvlan_Dau.readOnly = true;
      this.controls.txtCvlan_Dau.readOnly = true;
      this.controls.txtAdsl_cap_Dau.readOnly = true;
      this.controls.txtAdsl_port_Dau.readOnly = true;
      this.controls.txtNasport_id_Dau.readOnly = true;
      this.controls.txtIpDslam_Dau.readOnly = true;
      this.controls.txtVrf_Dau.readOnly = true;
      this.controls.txtSerial_Dau.readOnly = true;
      this.controls.txtIpwan_Dau.readOnly = true;
      this.controls.txtTimeSlot_Dau.readOnly = true;
      this.controls.txtLink_Dau.readOnly = true;
      this.controls.txtCapGoc_Dau.readOnly = true;
      this.controls.txtCapNgon_Dau.readOnly = true;
      this.controls.txtTuCapGoc_Dau.readOnly = true;
      this.controls.txtTuCapNgon_Dau.readOnly = true;
      this.controls.txtDoiCapGoc_Dau.readOnly = true;
      this.controls.txtDoiCapNgon_Dau.readOnly = true;
      this.controls.txtHopCapNgon_Dau.readOnly = true;
      this.controls.txtSoMetDay_Dau.readOnly = true;
      this.controls.txtLienTu_Dau.readOnly = true;
      this.controls.txtTuyenCap_Dau.readOnly = true;

      // cuoi
      this.controls.txtSvlan_Cuoi.readOnly = true;
      this.controls.txtCvlan_Cuoi.readOnly = true;
      this.controls.txtAdsl_cap_Cuoi.readOnly = true;
      this.controls.txtAdsl_port_Cuoi.readOnly = true;
      this.controls.txtNasport_id_Cuoi.readOnly = true;
      this.controls.txtIpDslam_Cuoi.readOnly = true;
      this.controls.txtVrf_Cuoi.readOnly = true;
      this.controls.txtSerial_Cuoi.readOnly = true;
      this.controls.txtIpwan_Cuoi.readOnly = true;
      this.controls.txtTimeSlot_Cuoi.readOnly = true;
      this.controls.txtLink_Cuoi.readOnly = true;
      this.controls.txtCapGoc_Cuoi.readOnly = true;
      this.controls.txtCapNgon_Cuoi.readOnly = true;
      this.controls.txtTuCapGoc_Cuoi.readOnly = true;
      this.controls.txtTuCapNgon_Cuoi.readOnly = true;
      this.controls.txtDoiCapGoc_Cuoi.readOnly = true;
      this.controls.txtDoiCapNgon_Cuoi.readOnly = true;
      this.controls.txtHopCapNgon_Cuoi.readOnly = true;
      this.controls.txtSoMetDay_Cuoi.readOnly = true;
      this.controls.txtLienTu_Cuoi.readOnly = true;
      this.controls.txtTuyenCap_Cuoi.readOnly = true;
               

      this.controls.tsbtnHoanThienHS.visible = false;
      this.controls.tsbtnHoanThienHS.enabled = false;
      this.controls.tsbtnHoanCong.visible = false;
      this.controls.tsbtnGiaoPhieu.visible = false;
      this.controls.tsbtnHoanThanh.visible = false;
      this.controls.tsbtnVatTu.visible = false;
      this.controls.tsbtnVatTuMoi.visible = false; 
      this.controls.rdoPhieu_Da_TH.visible = false; 

      this.controls.tsbtnGiaoPhieu_KBB.visible = false;
      this.controls.tsbtnSua_KenhThu.visible = false;
 
      //bổ sung load động button chức năng   
      if(this.luong_id && this.luong_id > 0){
        await CommonAPI.sp_lay_luong_thaotac(this.axios, {
          luong_id: this.luong_id,
        }).then((res) => {
          this.danhSachThaoTac = res.data.data;
        });
      } 

      if (this.danhSachThaoTac && this.danhSachThaoTac.length > 0) {
        await Promise.all(this.danhSachThaoTac.map(async (item) => {
          await CommonAPI.lay_ct_thaotac_control(this.axios, {
            thaotac_id: item.thaotac_id,
          }).then((res) => {
            res.data.data.forEach((row) => {
              try{
                var control = this.controls[row.control_name];
                if(control){
                  switch(control.type){
                    case "menu_button":
                    case "radio":
                      control.visible = item.enable == 1;
                      break;
                    case "button":
                      control.enabled = item.enable == 1;
                      break;
                    case "textbox":
                      control.readOnly = item.enable == 1;
                      control.enabled = item.enable == 1;
                      if(control.readOnly == true){
                        control.bgColor = "cornsilk";
                        control.textColor = "maroon"; 
                      }
                      break;
                    case "checkBox":
                      control.enabled = item.enable == 1;
                      break;
                    default:
                      control.enabled = item.enable == 1;
                  }
                  console.log(row.control_name, this.controls[row.control_name]);
                }
                else
                {
                  console.warn('control not found: '+ row.control_name);
                } 
                
              }catch(ex){
                console.error(ex);
                console.log(row.control_name, this.controls[row.control_name]);
              }
            });
          });
        }));
      }
      this.options_ThongTinHoanCong_DD.options_controls = this.controls;
      this.options_ThongTinHoanCong_DC.options_controls = this.controls;
      //END bổ sung load động button chức năng  

      if (this.PHAILAM("SERIAL_TSL")) {
        this.controls.txtSerial_Dau.readOnly = true;
        this.controls.txtSerial_Cuoi.readOnly = true;
      } else {
        this.controls.txtSerial_Dau.readOnly = false;
        this.controls.txtSerial_Cuoi.readOnly = false;
      }
    },
    KiemTra_DL(isUpdate) {

      if(!this.form_ThongTinKhachHang.phieu_id || this.form_ThongTinKhachHang.phieu_id <=0){
        this.$toast.error("Bạn chưa chọn phiếu!");
        return false;
      } 

      if (!isUpdate && this.form_ThongTinGiaoViec.chkNgayGV == false) {
        // Message_Box.ShowWarning();
        this.$toast.error("Hãy chọn ngày giao việc !");

        return false;
      }

      if (!isUpdate && !this.form_ThongTinGiaoViec.chkNgayBG) {
        this.$toast.error("Hãy nhập ngày hoàn thành!");
        // chkNgayBG.Focus();
        return false;
      }
      //Kiếm tra với luong_id, thì fai làm những gì
      //1. Giao việc
      if (!isUpdate && this.PHAILAM("GIAOVIEC")) {
        console.log('this.form_ThongTinGiaoViec', this.form_ThongTinGiaoViec);
        if (this.form_ThongTinGiaoViec.table_DSNV.config.length <= 0) {
          this.$toast.error("Hãy nhập nhân viên thực hiện !");

          return false;
        }
      }
      //2. Cập nhật thông số kỹ thuật
      if (this.PHAILAM("CAPNHAT_THONGSO_KYTHUAT")) {
        if (this.kieu == 1 || this.kieu == 3) {
          if (
            !this.form_ThongTinHoanCong_DD.txtSvlan_Dau
            || !this.form_ThongTinHoanCong_DD.txtCvlan_Dau
          ) {
            this.$toast.error("Hãy nhập thông tin về Port điểm đầu");

            // txtAdsl_port_Dau.Focus();
            return false;
          }
          if (!this.form_ThongTinHoanCong_DD.txtTimeSlot_Dau) {
            this.$toast.error("Hãy nhập thông tin về TimeSlot điểm đầu");

            // txtTimeSlot_Dau.Focus();
            return false;
          }
        }
        if (this.kieu == 2 || this.kieu == 3) {
          if (!this.form_ThongTinHoanCong_DC.txtSvlan_Cuoi
            || !this.form_ThongTinHoanCong_DC.txtCvlan_Cuoi
          ) {
            this.$toast.error("Hãy nhập thông tin về Port điểm cuối");

            // txtAdsl_port_Cuoi.Focus();
            return false;
          }
          if (!this.form_ThongTinHoanCong_DC.txtTimeSlot_Cuoi) {
            this.$toast.error("Hãy nhập thông tin về TimeSlot điểm cuối");

            // txtTimeSlot_Cuoi.Focus();
            return false;
          }
        }
      }
      if (this.PHAILAM("CAPNHAT_CAP_TSL")) {
        if (this.kieu == 1 || this.kieu == 3) {
          if (!this.form_ThongTinHoanCong_DD.txtCapGoc_Dau) {
            this.$toast.error("Hãy nhập thông tin cáp gốc đầu !");

            // txtCapGoc_Dau.Focus();
            return false;
          }
          if (!this.form_ThongTinHoanCong_DD.txtTuCapGoc_Dau) {
            this.$toast.error("Hãy nhập thông tin tủ cáp gốc đầu !");

            // txtTuCapGoc_Dau.Focus();
            return false;
          }

          if (!this.form_ThongTinHoanCong_DD.txtDoiCapGoc_Dau) {
            this.$toast.error("Hãy nhập thông tin đôi cáp gốc đầu !");

            // txtDoiCapGoc_Dau.Focus();
            return false;
          }

          if (!this.form_ThongTinHoanCong_DD.txtCapNgon_Dau) {
            this.$toast.error("Hãy nhập thông tin cáp ngọn đầu !");

            // txtCapNgon_Dau.Focus();
            return false;
          }

          if (!this.form_ThongTinHoanCong_DD.txtDoiCapNgon_Dau) {
            this.$toast.error("Hãy nhập thông tin đôi cáp ngọn đầu !");

            // txtDoiCapNgon_Dau.Focus();
            return false;
          }

          if (
            !this.form_ThongTinHoanCong_DD.txtSoMetDay_Dau ||
            this.form_ThongTinHoanCong_DD.txtSoMetDay_Dau == "0"
          ) {
            this.$toast.error("Hãy nhập thông tin số mét dây đầu !");

            // txtSoMetDay_Dau.Focus();
            return false;
          }

          if (this.form_ThongTinHoanCong_DD.txtSoMetDay_Dau) {
            if (this.form_ThongTinHoanCong_DD.txtSoMetDay_Dau == "0") {
              this.$toast.error("Hãy nhập số mét dây đầu !");

              // txtSoMetDay_Dau.Focus();
              return false;
            }
            var s = parseInt(this.form_ThongTinHoanCong_DD.txtSoMetDay_Dau);
            if (isNaN(s)) {  
              this.$toast.error("Số mét dây đầu phải là kiểu số"); 
              return false;
            }
          } else {
            this.$toast.error("Hãy nhập số mét dây đầu !");

            // txtSoMetDay_Dau.Focus();
            return false;
          }
        }
        if (this.kieu == 2 || this.kieu == 3) {
          if (!this.form_ThongTinHoanCong_DC.txtCapGoc_Cuoi) {
            this.$toast.error("Hãy nhập thông tin cáp gốc cuối !");

            // txtCapGoc_Cuoi.Focus();
            return false;
          }
          if (!this.form_ThongTinHoanCong_DC.txtTuCapGoc_Cuoi) {
            this.$toast.error("Hãy nhập thông tin tủ cáp gốc cuối !");

            // txtTuCapGoc_Cuoi.Focus();
            return false;
          }

          if (!this.form_ThongTinHoanCong_DC.txtDoiCapGoc_Cuoi) {
            this.$toast.error("Hãy nhập thông tin đôi cáp gốc cuối !");

            // txtDoiCapGoc_Cuoi.Focus();
            return false;
          }

          if (!this.form_ThongTinHoanCong_DC.txtCapNgon_Cuoi) {
            this.$toast.error("Hãy nhập thông tin cáp ngọn cuối !");
            // txtCapNgon_Cuoi.Focus();
            return false;
          }

          if (!this.form_ThongTinHoanCong_DC.txtDoiCapNgon_Cuoi) {
            this.$toast.error("Hãy nhập thông tin đôi cáp ngọn cuối !");
            // txtDoiCapNgon_Cuoi.Focus();
            return false;
          }

          if (
            !this.form_ThongTinHoanCong_DC.txtSoMetDay_Cuoi ||
            this.form_ThongTinHoanCong_DC.txtSoMetDay_Cuoi == "0"
          ) {
            this.$toast.error("Hãy nhập thông tin số mét dây cuối !");
            // txtSoMetDay_Cuoi.Focus();
            return false;
          }

          if (this.form_ThongTinHoanCong_DC.txtSoMetDay_Cuoi) {
            if (this.form_ThongTinHoanCong_DC.txtSoMetDay_Cuoi == "0") {
              this.$toast.error("Hãy nhập số mét dây cuối !");
              // txtSoMetDay_Cuoi.Focus();
              return false;
            }
            var sc = this.form_ThongTinHoanCong_DC.txtSoMetDay_Cuoi;
            if (isNaN(sc) == true ) {
              this.$toast.error("Số mét dây cuối phải là kiểu số");
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
        commonFn.showException(this, error);
      }
    },
    KiemTraDK_HoanThanh(
      thangnam_tt,
      thangnam_sys //thangnam_sys= ngay_ht
    ) {
      console.log(thangnam_sys);
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
      if (!this.KiemTra_DL(true)) return false;
  
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
      const ngay_sys =
        this.form_DanhSachThueBao.table.currentRecord.thangnam_sys;
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
          NHANVIEN_ID: this.tt_nd.nhanvien_id,
          DONVI_ID: this.form_ThongTinKhachHang.cboChuQuan,

          LOAIHD_ID: this.form_ThongTinKhachHang.cboLoaiHD,
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

          TBI_CUOI: this.form_ThongTinHoanCong_DC.txtTbi_Cuoi,
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
      let success = false;
      console.log("updating...", data)
      await this.$store
        .dispatch("declareMPLS-module/PostCapNhat", {
          vds: JSON.stringify(data).toString(),
          vkieu: 1,
        })
        .then((response) => {
          if (response.data.message == "Success") {
            success = true
            //this.$toast.success("Thành công");
            this.form_DanhSachThueBao.rdoPhieu = 0;
            this.$root.showLoading(true);

            this.getTableDSThueBao();
          }
        })
        .catch((error) => {
          commonFn.showException(this, error);
        });
      return success;
    },
    async getMAPIDDAUCUOIID() {
      // const dc_id = await this.$store.dispatch(
      //   "declareMPLS-module/fetchlydotra",
      //   {
      //     id_neo: "daucuoi_id",
      //     in_table: "css.giaophieu",
      //     in_dk: " where phieu_id = 12",
      //   }
      // );

      const dc_id = commonFn.GetData(await api.fn_tt_giaophieu(this.axios,  { param: this.form_ThongTinKhachHang.phieu_id, type: 4 } ))

      if (dc_id != "-1" && dc_id != "0") {
        if (dc_id == "1") {
          // điểm đầu
          this.kieu = 1;
          this.HienThi_Panel(1, false);
          this.HienThi_Panel(2, true);
        } else if (dc_id == "2") {
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
          this.controls.txtTuCapGoc_Dau.readOnly = kt;
          this.controls.txtDoiCapGoc_Dau.readOnly = kt;
          this.controls.txtCapGoc_Dau.readOnly = kt;
          this.controls.txtHopCapNgon_Dau.readOnly = kt;
          this.controls.txtTuCapNgon_Dau.readOnly = kt;
          this.controls.txtDoiCapNgon_Dau.readOnly = kt;
          this.controls.txtCapNgon_Dau.readOnly = kt;
          this.controls.txtTuyenCap_Dau.readOnly = kt;
          this.controls.txtLienTu_Dau.readOnly = kt;
          this.controls.txtSoMetDay_Dau.readOnly = kt;
          this.controls.txtSerial_Dau.readOnly = kt;
        } else {
          this.controls.txtTuCapGoc_Dau.readOnly = true;
          this.controls.txtDoiCapGoc_Dau.readOnly = true;
          this.controls.txtCapGoc_Dau.readOnly = true;
          this.controls.txtHopCapNgon_Dau.readOnly = true;
          this.controls.txtTuCapNgon_Dau.readOnly = true;
          this.controls.txtDoiCapNgon_Dau.readOnly = true;
          this.controls.txtCapNgon_Dau.readOnly = true;
          this.controls.txtTuyenCap_Dau.readOnly = true;
          this.controls.txtLienTu_Dau.readOnly = true;
          this.controls.txtSoMetDay_Dau.readOnly = true;
          this.controls.txtSerial_Dau.readOnly = true;
        }
      } else {
        if (kieu == 2) {
          //Panel cuoi
          if (
            this.form_ThongTinKhachHang.cboLoaiHD == TrangThaiHD.DANG_THI_CONG
          ) {
            this.controls.txtTuCapGoc_Cuoi.readOnly = kt;
            this.controls.txtDoiCapGoc_Cuoi.readOnly = kt;
            this.controls.txtCapGoc_Cuoi.readOnly = kt;
            this.controls.txtHopCapNgon_Cuoi.readOnly = kt;
            this.controls.txtTuCapNgon_Cuoi.readOnly = kt;
            this.controls.txtDoiCapNgon_Cuoi.readOnly = kt;
            this.controls.txtCapNgon_Cuoi.readOnly = kt;
            this.controls.txtTuyenCap_Cuoi.readOnly = kt;
            this.controls.txtLienTu_Cuoi.readOnly = kt;
            this.controls.txtSoMetDay_Cuoi.readOnly = kt;
            this.controls.txtSerial_Cuoi.readOnly = kt;
          } else {
            this.controls.txtTuCapGoc_Cuoi.readOnly = true;
            this.controls.txtDoiCapGoc_Cuoi.readOnly = true;
            this.controls.txtCapGoc_Cuoi.readOnly = true;
            this.controls.txtHopCapNgon_Cuoi.readOnly = true;
            this.controls.txtTuCapNgon_Cuoi.readOnly = true;
            this.controls.txtDoiCapNgon_Cuoi.readOnly = true;
            this.controls.txtCapNgon_Cuoi.readOnly = true;
            this.controls.txtTuyenCap_Cuoi.readOnly = true;
            this.controls.txtLienTu_Cuoi.readOnly = true;
            this.controls.txtSoMetDay_Cuoi.readOnly = true;
            this.controls.txtSerial_Cuoi.readOnly = true;
          }
        }
      }

      if (this.PHAILAM("CAPNHAT_THONGSO_KYTHUAT")) {
        this.controls.txtTimeSlot_Dau.readOnly = false;
        this.form_ThongTinHoanCong_DD.isTxtTBi_Dau = false;
        this.controls.txtIpwan_Dau.readOnly = false;
        this.controls.txtVrf_Dau.readOnly = false;
        this.controls.txtLink_Dau.readOnly = false;

        this.controls.txtTimeSlot_Cuoi.readOnly = false;
        this.form_ThongTinHoanCong_DC.isTxtTbi_Cuoi = false;
        this.controls.txtIpwan_Cuoi.readOnly = false;
        this.controls.txtVrf_Cuoi.readOnly = false;
        this.controls.txtLink_Cuoi.readOnly = false;
      } else {
        this.controls.txtTimeSlot_Dau.readOnly = true;
        this.form_ThongTinHoanCong_DD.isTxtTBi_Dau = true;
        this.controls.txtIpwan_Dau.readOnly = true;
        this.controls.txtVrf_Dau.readOnly = true;
        this.controls.txtLink_Dau.readOnly = true;

        this.controls.txtTimeSlot_Cuoi.readOnly = true;
        this.form_ThongTinHoanCong_DC.isTxtTbi_Cuoi = true;
        this.controls.txtIpwan_Cuoi.readOnly = true;
        this.controls.txtVrf_Cuoi.readOnly = true;
        this.controls.txtLink_Cuoi.readOnly = true;
      }
    },
    async getTableDSThueBao() {
      //this.clearForm();
      this.state.selectedMultiRows=[];
      if (this.huonggiao_id) {
        try {
          //if(this.dsdichvuvt_id && this.dsdichvuvt_id.indexOf(',') > 0){
            //this.dsdichvuvt_id = this.dsdichvuvt_id.split(',')[0]
          //}

          await this.$store
            .dispatch("declareMPLS-module/fetchDanhSachThueBao", {
              dichvuvt_id: this.dsdichvuvt_id,
              tthd_id: this.tthd_id,
              huonggiao_id: this.huonggiao_id,
              nhanvien_id: this.$root.token.getNhanVienID(),
              ma_gd: this.isEnterMaGD
                ? this.form_ThongTinKhachHang.txtMaGD
                : "0",
              phieu_id: 0,
              loaihd_id: this.form_ThongTinKhachHang.cboLoaiHD*1,
              loai_id: this.form_DanhSachThueBao.rdoPhieu,
            })
            .then((response) => {
              if (response.data.error_code == "BSS-00000000") {
                this.form_DanhSachThueBao.table.options = response.data.data;
                this.$root.showLoading(false);
              } else {
                this.form_DanhSachThueBao.table.options = [];
                commonFn.showException(this, response.data.message);
                this.clearForm();
                this.$root.showLoading(false);
                // if(response.data && response.data.message && response.data.message == "No Content"){
                //   this.$toast.error("Không có dữ liệu");
                // }
              }
            }) 
            .finally(() => {
              this.isEnterMaGD = false;
            });
        } catch (error) {
          console.error(error);
          //commonFn.showException(this, error);
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
          .dispatch("declareMPLS-module/fetchDanhSachHuongGiao", {
            quytrinh_id: this.form_ThongTinKhachHang.cboQuyTrinh,
            tthd_id: this.tthd_id,
          })
          .then(async (response) => {
            console.log("getHuongGiaoID");
            if (response.data.message == "Success") {
              this.huonggiao_id = await response.data.data[0].huonggiao_id;
              this.luong_id = await response.data.data[0].luong_id;
              this.header.title = response.data.data[0].huonggiao;
			        this.options_ThongTinHoanCong_DD.options_data.luong_id = this.luong_id;
              this.options_ThongTinHoanCong_DC.options_data.luong_id = this.luong_id;
              this.options_ThongTinHoanCong_DD.options_data.tt_nd = this.tt_nd;
              this.options_ThongTinHoanCong_DC.options_data.tt_nd = this.tt_nd;

              let formTitleElm = document.getElementsByClassName("main-title");
              if(formTitleElm && formTitleElm.length > 0)
                formTitleElm[0].innerHTML = this.header.title;

              console.log("--- Success ------ getHuongGiaoID -----------");
              await this.getTableDSThueBao();
            } else {
              // tesst
              this.huonggiao_id = null;
              console.log("--- null ------ getHuongGiaoID -----------");

              this.form_DanhSachThueBao.table.options = [];
              this.$root.showLoading(false);

              // this.$toast.error("Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại");
              return;
            }
            this.HienThiGiaoDien();
          });
      } catch (error) {
        console.error(error);
        //commonFn.showException(this, error);
      }
    },
    async getQuyTrinh() {
      try {
        await this.$store
          .dispatch("declareMPLS-module/fetchDSQuyTrinh", { 
            "dsloaihd_id": this.form_ThongTinKhachHang.cboLoaiHD,
            "tthd_id": this.tthd_id,
            "kieu": 0,
            "dichvuvt_id": this.dsdichvuvt_id,
          })
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_ThongTinKhachHang.options_quyTrinh =
                response.data.data; 
              console.log(this.form_ThongTinKhachHang.cboQuyTrinh);
            } else {
              this.options_ThongTinKhachHang.options_quyTrinh = [];
              this.form_DanhSachThueBao.table.options = [];

              this.clearForm();

              this.loading(false);
            } 
            if(!this.options_ThongTinKhachHang.options_quyTrinh || this.options_ThongTinKhachHang.options_quyTrinh.length <= 0){
              this.options_ThongTinKhachHang.options_quyTrinh = [{quytrinh_id: -1, quytrinh: "--Không có quy trình--"}];              
            }
            this.form_ThongTinKhachHang.cboQuyTrinh = this.options_ThongTinKhachHang.options_quyTrinh[0].quytrinh_id;
          });
      } catch (error) {
        //commonFn.showException(this, error);
        window.location.reload();
      }
    },
	//LAY_DS_HDTB_TSL_THEO_HDTB_ID
    async getDanhSachHopDongThueBaoTLS() {
      const log = await this.$store.dispatch(
        "declareMPLS-module/fetchDanhSachHopDongThueBaoTLS",
        this.form_ThongTinKhachHang.hdtb_id
      );

      let daset = log.data.data;
      console.log(daset);
      if (daset.length > 0) { 
        this.form_ThongTinHoanCong_DC.txtSoSoiQuang = daset[0].sl_soiquang;
        this.form_ThongTinKhachHang.cboTocDo = daset[0].tocdo_id;
        this.form_ThongTinKhachHang.cboChuQuan = daset[0].chuquan_id;

        if (!!daset[0].doitac_id) {
          console.log(daset[0].doitac_id);
          this.form_ThongTinKhachHang.checkbox_value = true;
          this.$nextTick(() => {
            console.log(daset[0].doitac_id);
            this.form_ThongTinKhachHang.cboDoiTac = daset[0].doitac_id;
          });
        } else {
          this.form_ThongTinKhachHang.checkbox_value = false;
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
          //daset[i].port_id = 218532;
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
          this.options_ThongTinHoanCong_DD.options_hdtb = daset[i]; 
           
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
		  
		  //string str = "select * from " + DatabaseConstants.DB2 + ".hdtb_dv where hdtb_id = " + hdtb_id + " and loaidv_id = " + LOAI_DV.TRAM_VT + " and kieudv_id = 1";
          //CSS.SP_LAY_DS_HDTB_DV_KIEUDV 
          var dtTemp = [];
          await api.lay_ds_hdtb_dv_loaidv(this.axios, {
            hdtb_id: this.form_ThongTinKhachHang.hdtb_id,
            loaidv_id: config.TRAM_VT,
            kieudv_id: 1,
          }).then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  dtTemp = response.data.data;
              }
          }); 
 
          if (dtTemp && dtTemp.length > 0)
          {
              this.form_ThongTinHoanCong_DD.cboDonViDau = dtTemp[0]["donvi_id"]*1; 
              this.tramvt_dau_id = this.form_ThongTinHoanCong_DD.cboDonViDau;
              
          }
           
          //Lấy thông tin liên hệ lắp đặt thuê bao điểm đầu
          //lblLienHe_Dau.Text = "Liên hệ : " + bangts.Lay_thongtin_lh_lapdat_tsl(hdkh_id, hdtb_id, 1);

        } else {
          if (!!daset[i].tramtb_id) this.tramtb_cuoi_id = daset[i].tramtb_id; 
          //daset[i].port_id = 218532;
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
          this.options_ThongTinHoanCong_DC.options_hdtb = daset[i]; 
        
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

          var dtTemp = [];
          await api.lay_ds_hdtb_dv_loaidv(this.axios, {
            hdtb_id: this.form_ThongTinKhachHang.hdtb_id,
            loaidv_id: config.TRAM_VT,
            kieudv_id: 2,
          }).then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  dtTemp = response.data.data;
              }
          }); 
 
          if (dtTemp && dtTemp.length > 0)
          {
              this.form_ThongTinHoanCong_DC.cboDonViCuoi = dtTemp[0]["donvi_id"]*1; 
              this.tramvt_cuoi_id = this.form_ThongTinHoanCong_DC.cboDonViCuoi;
              
          }



















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
      console.log("HienThiDSNV");
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
        this.form_ThongTinGiaoViec.cboNguoiGV = this.$root.token.getNhanVienID().toString();
      }
    },
    PHAILAM(code) {
      return (
        this.danhSachThaoTac.findIndex(
          (item) => item.enable === 1 && item.code === code
        ) >= 0
      );
    },
    async getDSDiaChi() {
      const ds_diachi = await this.$store.dispatch(
        "declareMPLS-module/fetchLay_DiaChi_LD",
        {
          hdtb_id: this.form_ThongTinKhachHang.hdtb_id,
          dichvuvt_id: this.dichvuvt_id,
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
        "declareMPLS-module/fetchKIEMTRA_HEN_HDTB",
        { hdkh_id: this.form_ThongTinKhachHang.hdtb_id }
      );
      if(bangts.data.data && bangts.data.data.length > 0)
        this.form_ThongTinKhachHang.chkHen = bangts.data.data[0].c !== "0";
      else
        this.form_ThongTinKhachHang.chkHen = false;
    },
    async getDSDonVi() {
      const ds_donviDD = await this.$store.dispatch(
        "declareMPLS-module/fetchDSHDTBDBKieudv",
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
        "declareMPLS-module/fetchDSHDTBDBKieudv",
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
        "declareMPLS-module/fetchHT_PHIEU_TRA_TSL",
        { phieu_id: this.form_ThongTinKhachHang.phieu_id, ds_daucuoi_id: "0,1" }
      );

      if (dstraDD.data.data.length > 0) {
        this.form_ThongTinHoanCong_DD.txtNoiDungTra_Dau =
          dstraDD.data.data[0].nd_tra;
        // const lydotra_cuoi_id = await this.$store.dispatch(
        //   "declareMPLS-module/fetchlydotra",
        //   {
        //     id_neo: "lydotra",
        //     in_table: "css.lydotra_tc",
        //     in_dk: ` lydotra_id =${dstraDD.data.data[0].lydotra_id}`,
        //   }
        // );

        const lydotra_cuoi_id = commonFn.GetData(await api.fn_tt_lydotra_tc(this.axios,  { param: dstraDD.data.data[0].lydotra_id, type: 1 } ))

        this.form_ThongTinHoanCong_DC.txtLyDoTra_Cuoi = lydotra_cuoi_id;
        //  lydotra_cuoi_id.data.data;
        // const lydotra_dau_id = await this.$store.dispatch(
        //   "declareMPLS-module/fetchlydotra",
        //   {
        //     id_neo: "lydotra",
        //     in_table: "css.lydotra_tc",
        //     in_dk: ` lydotra_id =${dstraDD.data.data[0].lydotra_id} `,
        //     // in_dk: `where lydotra_id = 54 `,
        //   }
        // );

        const lydotra_dau_id = commonFn.GetData(await api.fn_tt_lydotra_tc(this.axios,  { param: dstraDD.data.data[0].lydotra_id, type: 1 } ))
        this.form_ThongTinHoanCong_DD.txtLyDoTra_Dau = lydotra_dau_id;
      } else {
        this.form_ThongTinHoanCong_DD.txtNoiDungTra_Dau = "";
        this.form_ThongTinHoanCong_DD.txtLyDoTra_Dau = "";
      }

      const dstraDC = await this.$store.dispatch(
        "declareMPLS-module/fetchHT_PHIEU_TRA_TSL",
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
        .dispatch("declareMPLS-module/fetchKenhThu", {
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
        "declareMPLS-module/fetchThongTinLH",
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
        "declareMPLS-module/fetchThongTinLH",
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
          .dispatch("declareMPLS-module/fetchKieuHD", {})
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_thongTinChung.options_kieuHD = response.data.data;
            }
          });
        this.$store
          .dispatch("declareMPLS-module/fetchDanhSachKenhThu", {})
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_thongTinChung.options_kenhThu = response.data.data;
            }
          });

        //  get form thong tin khach hang

        this.$store
          .dispatch("declareMPLS-module/fetchDanhSachDoiTac", {})
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_ThongTinKhachHang.options_doiTac =
                response.data.data;
            }
          });

        this.$store
          .dispatch("declareMPLS-module/fetchTocDoKenh", {})
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
          .dispatch("declareMPLS-module/fetchLoaiHinhTB", {})
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_ThongTinKhachHang.options_loaiHinh =
                response.data.data;
            }
          });

        this.getLoaiHD();
        // end  get form thong tin khach hang

        this.$store
          .dispatch("declareMPLS-module/fetchLoaiCap", {})
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_ThongTinHoanCong_DD.options_loaiCap =
                response.data.data;

              this.options_ThongTinHoanCong_DC.options_loaiCap =
                response.data.data;
            }
          });

        this.$store
          .dispatch("declareMPLS-module/fetchNhanVienGV", {
            donvi_id: this.$root.token.getDonViID(),
          })
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_ThongTinGiaoViec.nguoiGV = response.data.data;
              this.form_ThongTinGiaoViec.cboNguoiGV = this.$root.token.getNhanVienID().toString();
            }
          });

        this.$store
          .dispatch("declareMPLS-module/fetchDonViCuoi", { loaidv_id: 5 })
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_ThongTinHoanCong_DC.options_donVi =
                response.data.data;
              this.options_ThongTinHoanCong_DD.options_donVi =
                response.data.data;
            }
          });
        this.$store
          .dispatch("declareMPLS-module/fetchDanhSachChuQuan", {})
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_ThongTinKhachHang.options_chuQuan =
                response.data.data;
            }
          });
        this.$store
          .dispatch("declareMPLS-module/fetchDanhSachThietBi", {
            dichvuvt_id: this.dichvuvt_id //code cũ fix
          })
          .then((response) => {
            if (response.data.message == "Success") {
              this.options_ThongTinHoanCong_DC.options_thietBi =
                response.data.data;
              this.options_ThongTinHoanCong_DD.options_thietBi =
                response.data.data;
            }
          });
      } catch (error) {
        console.error(error);
        //commonFn.showException(this, error);
        window.location.reload();
      } finally {
        Vue.prototype.loading(false);
      }
    },
    async getLoaiHD() {
      this.$store
        .dispatch("declareMPLS-module/fetchLoaiHD", {
          dsloaihd_id: this.dsloaihd_id,
        })
        .then((response) => {
          if (response.data.message == "Success") {
            //var array2 = [1, 3, 6, 30];
            var array2 = this.dsloaihd_id.toString().split(','); //filter Loại hợp đồng theo TAG
            
            if (response.data.data.length > 0)
              var array1 = response.data.data.filter(function (item) {
                return array2.includes(item.LOAIHD_ID.toString());
              });

            this.options_ThongTinKhachHang.options_loaiHD =
              array1.sort((a, b) => a.LOAIHD_ID - b.LOAIHD_ID) || [];
            this.form_ThongTinKhachHang.cboLoaiHD =
              this.options_ThongTinKhachHang.options_loaiHD[0].LOAIHD_ID;
          }
        });
    },
    async getFormThongTinGiaoViec() {
      try {
        Vue.prototype.loading(true);
        await this.$store
          .dispatch("declareMPLS-module/fetchDanhSachNhanVienID", {
            phieu_id: this.form_ThongTinKhachHang.phieu_id,
            nhanvien_id: this.$root.token.getNhanVienID(),
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
        console.error(error);
        //commonFn.showException(this, error);
        Vue.prototype.loading(false);
      }
      Vue.prototype.loading(false);
    },
    async btnLayTTOnClick() {
      Vue.prototype.loading(true);
      this.isEnterMaGD = false;
      this.getTableDSThueBao();
      if (!this.form_DanhSachThueBao.table.options || this.form_DanhSachThueBao.table.options.length ==0){
        this.$toast.error("Không có dữ liệu");
      }
    },
    async onEnter(){
      console.log("ENTER")
      this.isEnterMaGD = true;
      await this.getTableDSThueBao();
      if (!this.form_DanhSachThueBao.table.options || this.form_DanhSachThueBao.table.options.length ==0){
        this.$toast.error("Không có dữ liệu");
      }
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
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
          centered: true,
        }
      );
      if (isConfirm) {
        this.$store
          .dispatch("declareMPLS-module/fetchCapnhat_tt_phieugiao", {
            phieu_id: this.form_ThongTinKhachHang.phieu_id,
            ttph_id: 1,
            ngay_th: moment().format("DD/MM/YYYY HH:mm:ss"),
          })
          .then((response) => {
            if (response.data.message == "Success") {
              this.$toast.success("Hủy hoàn công thành công");
              this.form_DanhSachThueBao.rdoPhieu = 0;
            }
          })
          .catch((error) => {
            commonFn.showException(this, error);
          });
      }
    },
    tsbtnHoanThanh_Click() {
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
          NHANVIEN_ID: this.tt_nd.nhanvien_id,
          DONVI_ID: this.form_ThongTinKhachHang.cboChuQuan,
          MAY_CN: this.tt_nd.may_cn,
          NGUOI_CN: this.tt_nd.nguoi_cn,
          IP_CN: this.tt_nd.ip_cn,
          HUONGGIAO_ID: this.huonggiao_id,
          LOAIHD_ID: this.form_ThongTinKhachHang.cboLoaiHD*1,
          NGAY_BG: this.form_ThongTinGiaoViec.dtpNgayBG,
        },
      ];
      this.$store
        .dispatch("declareMPLS-module/PostHoanhThanh", {
          vds: JSON.stringify(data).toString(),
          vkieu: 2,
        })
        .then((response) => {
          if (response.data.error_code == "BSS-00000000") {
            this.$toast.success("Hoàn Thành hoàn công thành công");
            Vue.prototype.loading(true);

            this.form_DanhSachThueBao.rdoPhieu = 0;
            this.getTableDSThueBao();
          }
          else{
             this.$toast.error(response.data.data);
          }
        })
        .catch((error) => {
          commonFn.showException(this, error);
        });
    },
    async btnHoanThien() {
      if (this.form_DanhSachThueBao.table.options.length <= 0) {
        this.$toast.error("Chưa có danh thuê bao. Bạn hãy kiểm tra lại! ");
        return;
      }

      const data = [
        {
          LUONG_ID: this.luong_id,
          HDTB_ID: this.form_ThongTinKhachHang.hdtb_id,
          PHIEU_ID: this.form_ThongTinKhachHang.phieu_id,
          NHANVIEN_ID: this.tt_nd.nhanvien_id,
          DONVI_ID: this.form_ThongTinKhachHang.cboChuQuan,
          MAY_CN: this.tt_nd.may_cn,
          NGUOI_CN: this.tt_nd.nguoi_cn,
          IP_CN: this.tt_nd.ip_cn,
          LOAIHD_ID: this.form_ThongTinKhachHang.cboLoaiHD*1,
          NGAY_BG: this.form_ThongTinGiaoViec.dtpNgayBG,
        },
      ];      
      let response =  await api.postHoanThien(this.axios,  {
          vds: JSON.stringify(data).toString(),
          vkieu: 0,
        } )
      if (response && response.data && response.data.data && response.data.message == "Success"){
        if ( response.data.data == '1') {
          let response1 =  await api.postHoanThien(this.axios,  {
              vds: JSON.stringify(data).toString(),
              vkieu: 1,
            } )
            if (response1 && response1.data && response1.data.data && response1.data.message == "Success"){
              if ( response1.data.data == '1') {
                this.$toast.success("Hoàn thiện hoàn công thành công");
                Vue.prototype.loading(true);

                this.form_DanhSachThueBao.rdoPhieu = 0;
                this.getTableDSThueBao();
              }
              else {
                this.$toast.error(response1.data.data);
              }
            }
            else {
              if (response1 && response1.data && response1.data.data) {
                this.$toast.error(response1.data.data);
              }
            }
        }
        else {
          this.$toast.error(response.data.data);
        }       
        
      } else {
        if (response && response.data && response.data.data) {
          this.$toast.error(response.data.data);
        }
      }        
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
    tsbtnChuyenTo_Click() { 
      if(!this.form_ThongTinKhachHang.phieu_id || this.form_ThongTinKhachHang.phieu_id <=0){
        this.$toast.error("Bạn chưa chọn phiếu!");
        return;
      } 
      console.log('tsbtnChuyenTo_Click', 'Phiếu', this.form_ThongTinKhachHang.phieu_id, 'HDTBID', this.form_ThongTinKhachHang.hdtb_id);
      this.$refs.popupChuyenTo.openDialog(this.form_ThongTinKhachHang.phieu_id*1, this.form_ThongTinKhachHang.hdtb_id*1); 
    },
    btnHenKHOnClick() {
      if(!this.form_ThongTinKhachHang.phieu_id || this.form_ThongTinKhachHang.phieu_id <=0){
        this.$toast.error("Bạn chưa chọn phiếu!");
        return;
      } 
      this.$refs.popupThongTinHenKH.showModal();
    },
    async tsbtnGiaoPhieu_Ton_Click() {
      //
      if(!this.form_ThongTinKhachHang.phieu_id || this.form_ThongTinKhachHang.phieu_id <=0){
        this.$toast.error("Hãy chọn phiếu trước để nhập lý do tồn!");
        return;
      }

      this.loading(true)
      //let h_tthd_id = await commonFn.MAP_ID(this.axios, "tthd_id", "css.huonggiao", "where huonggiao_id = " + this.huonggiao_id)
      let h_tthd_id = commonFn.GetData(await api.fn_tt_huonggiao(this.axios, { huonggiao_id: this.huonggiao_id, type: 1}))
      this.loading(false)
      console.log('tsbtnGiaoPhieu_Ton_Click', 'Phiếu', this.form_ThongTinKhachHang.phieu_id, 'h_tthd_id', h_tthd_id);
      this.$refs.tonPhieuGiaoModal.showPhieuTon(this.form_ThongTinKhachHang.phieu_id, h_tthd_id, null); 
    },
    btnDoKiemOnClick() {
      if(!this.form_ThongTinKhachHang.phieu_id || this.form_ThongTinKhachHang.phieu_id <=0){
        this.$toast.error("Bạn chưa chọn phiếu!");
        return;
      }
      this.$refs.doKiem.showModal();
    },
    async tsbtnNVKT_Click() {
      if(!this.form_ThongTinKhachHang.phieu_id || this.form_ThongTinKhachHang.phieu_id <=0){
        this.$toast.error("Bạn chưa chọn phiếu!");
        return;
      }

      this.dataPopupKhuVuc.hdtb_id = this.form_ThongTinKhachHang.hdtb_id;
      this.dataPopupKhuVuc.donviql_id = 0;
      this.dataPopupKhuVuc.daucuoi_id = this.daucuoi_id;
      console.log("tsbtnNVKT_Click", this.dataPopupKhuVuc); 
      this.$refs.thayDoiKhuVucQuanLyThueBaoModal.showModal();
 
      // this.popupComponent = () => import("@/modules/contract/setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao.vue");
      // this.popupComponentName = "popupChonKhuVuc";
      // this.loading(true);
      // await this.LayDiaChiLD(this.form_DanhSachThueBao.table.currentRecord);
      // console.log('LayDiaChiLD', this.diachiLD);
      // this.loading(false);
      // var data = {
      //   diachi:   this.diachiLD,
      //   khuvuc_id: 0,
      //   hdtb_id: this.form_ThongTinKhachHang.hdtb_id,
      //   thuebao_id: 0,
      //   donvi_id: 0,
      //   donviql_id: 0,
      //   daucuoi_id: this.daucuoi_id,
      // };
      // this.state.modalTitle = "Gán khu vực quản lý thuê bao"
      // console.log("popupChonKhuVuc DATA", data); 
      // this.popupComponentData = data; 
      // this.Popup("popupComponentsMPLS");
    },
    async ThayDoiKhuVucQuanLyThueBao_success_update(){
      console.log("ThayDoiKhuVucQuanLyThueBao_success_update");
      await this.HienThiDanhSachHDTB();
    },
    async btnGiaoViecOnClick() {
      if(!this.form_ThongTinKhachHang.phieu_id || this.form_ThongTinKhachHang.phieu_id <=0){
        this.$toast.error("Bạn chưa chọn phiếu!");
        return;
      }
       
      //var grid = this.$refs.gv_DanhSach;
      //var seletedRows = grid.getSelectedRecords();
      var seletedRows = [this.form_DanhSachThueBao.table.currentRecord];
      if (seletedRows.length <= 0) {
        this.$toast.error("Bạn chưa chọn phiếu! ");
        return;
      } 
 
      if (
        this.form_ThongTinGiaoViec.cboNguoiGV == 0
      ) {
        this.$toast.error("Hãy nhập nhân viên giao việc!");
        return;
      }
 
      if (this.form_ThongTinGiaoViec.chkNgayGV == false) {
        // Message_Box.ShowWarning();
        this.$toast.error("Hãy chọn ngày giao việc !");

        return false;
      }
       
      var dsphieu = seletedRows.map((x) => x.phieu_id);
      var dsma_tb = seletedRows.map((x) => x.ma_tb);
      var dshdtb_id = seletedRows.map((x) => x.hdtb_id);

      this.state.dsphieu = dsphieu[0];
      this.state.dsma_tb = dsma_tb[0];
      this.state.dshdtb_id = dshdtb_id[0];
      this.state.huonggiao_id = this.huonggiao_id;

      console.log("this.state.dsphieu", this.state.dsphieu);
      console.log("this.state.dsma_tb", this.state.dsma_tb);
      console.log("this.state.dshdtb_id", this.state.dshdtb_id);
      console.log("this.state.huonggiao_id", this.state.huonggiao_id);
             
      let vnhanvien_giao_id = Number(this.form_ThongTinGiaoViec.cboNguoiGV);
      let vngaygiao = this.form_ThongTinGiaoViec.dtpNgayGV;
      console.log("vngaygiao", vngaygiao);
      
      // this.$refs.frnGiaoPhieuNV.frnGiaoPhieuNV(this.state.dsphieu, this.$root.token.getNhanVienID(), vnhanvien_giao_id, vngaygiao);
      // this.$refs.frnGiaoPhieuNV.vma_tb = this.state.dsma_tb;
      // this.$refs.frnGiaoPhieuNV.vhdtb_id = this.state.dshdtb_id;
      // this.$refs.frnGiaoPhieuNV.huonggiao_id = this.state.huonggiao_id;

      // this.$nextTick(() => {
      //   this.$refs.frnGiaoPhieuNV.load()
      //   this.$refs.popupfrnGiaoPhieuNV.show();
      // });

      this.state.modalTitle = "Giao phiếu nhân viên";
      this.dialogComponent = () => import("@/modules/contract/setup/DeclareLandline/popups/frnGiaoPhieuNV.vue");
      this.popupComponentName = "popupfrnGiaoPhieuNV";
      this.popupComponentEvts["giaoviec_success"] =  this.giaoviec_success()
      await this.dialogComponent().then((x) => {});
      this.$nextTick(() => { 
        this.$refs.generalComponent.frnGiaoPhieuNV(this.state.dsphieu, this.$root.token.getNhanVienID(), vnhanvien_giao_id, vngaygiao);
        this.$refs.generalComponent.vma_tb = this.state.dsma_tb;
        this.$refs.generalComponent.vhdtb_id = this.state.dshdtb_id;
        this.$refs.generalComponent.huonggiao_id = this.state.huonggiao_id;

        this.$nextTick(() => {
          this.$refs.generalComponent.load()
          this.$refs.generalDialog.show();
        });
      });  
    },
    btnTraPhieuOnClick() {
      alert("btnTraPhieuOnClick");
    },
	  tsbtnDuAn_Click() {
      this.$refs.popupDuAnHDTB.showModal(); 
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
        .dispatch("declareMPLS-module/PostCapnhat_noidung_th", {
          noidung: this.form_ThongTinHoanCong_DD.txtNoiDungTH.trim(),
          phieu_id: this.form_ThongTinKhachHang.phieu_id,
        })
        .then((response) => {
          if (response.data.message == "Success") {
            // nd_thuchien
            // this.form_ThongTinHoanCong_DD.txtNoiDungTH = "";

            this.form_DanhSachThueBao.table.options =
              this.form_DanhSachThueBao.table.options.map((e) => {
                if (
                  e.matb == this.form_DanhSachThueBao.table.currentRecord.matb
                ) {
                  e.nd_thuchien = this.form_ThongTinHoanCong_DD.txtNoiDungTH;
                  return e;
                }
                return e;
              });
            this.$toast.success("Cập nhật Nội dung TH thành công");
          } else {
            this.$toast.error(
              "Có lỗi xảy ra, vui lòng lin hệ Quản trị để được hỗ trợ!"
            );
          }
        })
        .catch((error) => {
          commonFn.showException(this, error);
        });
    },
    setDisplayDropDownMenu() {
      this.isDisplayMenu = !this.isDisplayMenu;
    },
    setDisplayTableDD() {
      this.form_ThongTinHoanCong_DD.isDisplayTable =
        !this.form_ThongTinHoanCong_DD.isDisplayTable; 
      if(this.form_ThongTinHoanCong_DD.isDisplayTable && (!this.form_ThongTinKhachHang.phieu_id || this.form_ThongTinKhachHang.phieu_id <=0)){
        this.$toast.error("Chưa có thông tin Port!");
        return;
      }
    },
    setDisplayTableDC() {
      this.form_ThongTinHoanCong_DC.isDisplayTable =
        !this.form_ThongTinHoanCong_DC.isDisplayTable;

      if(this.form_ThongTinHoanCong_DC.isDisplayTable && (!this.form_ThongTinKhachHang.phieu_id || this.form_ThongTinKhachHang.phieu_id <=0)){
        this.$toast.error("Chưa có thông tin Port!");
        return;
      }
    },
    hideDropdownMenu(e) {       
      if (e.target !== this.$refs.mainWrap) {
        this.isDisplayMenu = false; 
      }
      if (e.target !== this.$refs.xuatphieu) {
        this.xuatPhieuShow = false; 
      }
    },
	  tsbtnVatTu_Click () { 
      if (this.form_ThongTinKhachHang.phieu_id && this.form_ThongTinKhachHang.phieu_id > 0) {
        let inphieuid = Number.parseInt(this.form_ThongTinKhachHang.phieu_id)
        let inhdtbid = Number.parseInt(this.form_ThongTinKhachHang.hdtb_id)
        let inthuebaoid = Number.parseInt(this.form_ThongTinKhachHang.thuebao_id)
        this.modelVatTu.hdtb_id = inhdtbid
        this.modelVatTu.phieu_id = inphieuid
        this.modelVatTu.loaihd_id = this.form_ThongTinKhachHang.cboLoaiHD
        this.modelVatTu.thuebao_id = inthuebaoid;
        this.modelVatTu.loaitb_id = Number.parseInt(this.form_ThongTinKhachHang.loaitb_id);
        console.log(this.modelVatTu)                                                                                                                                           
        this.$refs.popupVatTuThueBao.showModal() 
      } else {
        this.$root.$toast.error('Bạn chưa chọn phiếu!')
      }
    },
    tsbtnVatTuMoi_Click () { 
      if (this.form_ThongTinKhachHang.phieu_id && this.form_ThongTinKhachHang.phieu_id > 0) {
        let inphieuid = Number.parseInt(this.form_ThongTinKhachHang.phieu_id)
        let inhdtbid = Number.parseInt(this.form_ThongTinKhachHang.hdtb_id)
        let inthuebaoid = Number.parseInt(this.form_ThongTinKhachHang.thuebao_id) //==> null
        this.modelVatTu.hdtb_id = inhdtbid
        this.modelVatTu.phieu_id = inphieuid
        this.modelVatTu.loaihd_id = this.form_ThongTinKhachHang.cboLoaiHD
        this.modelVatTu.thuebao_id = inthuebaoid
        this.modelVatTu.loaitb_id = Number.parseInt(this.form_ThongTinKhachHang.loaitb_id);
        console.log(this.modelVatTu)  
        this.$refs.popupCapVatTuTB.openDialog()
      } else {
        this.$root.$toast.error('Bạn chưa chọn phiếu!')
      }
    },
    tsbtnThongTinKT_Click(){
       if(!this.form_ThongTinKhachHang.phieu_id || this.form_ThongTinKhachHang.phieu_id <= 0){
         this.$root.$toast.error('Bạn chưa chọn phiếu!')
         return false;
       }
       this.$refs.popupTraCuuThongSoKTModal.showModal();
    },
    tsbtnSua_KenhThu_Click(){ 
      if(!this.form_ThongTinKhachHang.phieu_id || this.form_ThongTinKhachHang.phieu_id <= 0){
         this.$root.$toast.error('Bạn chưa chọn phiếu!')
         return false;
      }
      this.$refs.thayDoiKenhThu.show();
    }, 
    async LAY_DS_THAMSO_MD_MATS(ma_ts){
      // const res = await this.$store.dispatch(
      //   "declareMPLS-module/LAY_DS_THAMSO_MD_MATS",
      //   { 
      //     "ds_ma_ts": [ma_ts]
      //   }
      // );
      
      let data = commonFn.GetData(await api.sp_tt_thamso_md(this.axios, {"p_param": "", "p_type":1}))
      if (data && data.length > 0) {
         return data;
      }
      return [];
    },
    async LAY_DS_THAMSO_MD(ma_ts){
      // const data = await this.LAY_DS_THAMSO_MD_MATS(ma_ts);
      let data = common.GetData(await api.sp_tt_thamso_md(this.axios, {"p_param": ma_ts, "p_type":1}))
      if (data.length > 0) {
         return data[0]*1;
      }
      return 0;
    },
    async IS_USING_CABMAN_V2()
    { 
        if (!this.state._IS_USING_CABMAN_V2 || this.state._IS_USING_CABMAN_V2 == -1)
        {
            var tmpData = await this.LAY_DS_THAMSO_MD_MATS("USING_CABMAN_2.0")
            if(tmpData) this.state._IS_USING_CABMAN_V2 = tmpData.length > 0 ? 1 : 0;
        }

        return this.state._IS_USING_CABMAN_V2 == 1; 
    },
    
    async MAP_ID(in_id_neo, in_table, in_dk){
        var data = 0; 

        const response = await this.$store.dispatch(
          "declareMPLS-module/MAP_ID",
          {
            "in_dk": in_dk,
            "in_id_neo": in_id_neo,
            "in_table": in_table
          }
        );

        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          data = response.data.data * 1; 
        }

        return data;
    },
    async tsbtnDauNoiCap_Click(){
      console.log('form_DanhSachThueBao.table.currentRecord', this.form_DanhSachThueBao.table.currentRecord);
      if(!this.form_ThongTinKhachHang.hdtb_id || this.form_ThongTinKhachHang.hdtb_id <= 0){
         this.$root.$toast.error('Bạn chưa chọn phiếu!')
         return false;
       }
       this.loading(true);
      var dscheck_thamso = await this.LAY_DS_THAMSO_MD_MATS("DAUNOI_KOCAN_CAP_PORT");
      console.log('this.LAY_DS_THAMSO_MD_MATS("DAUNOI_KOCAN_CAP_PORT")', dscheck_thamso);
      if (this.port_dau_id == 0 && this.port_cuoi_id == 0 && dscheck_thamso.length == 0)
      { 
          this.$root.$toast.warning("Chưa có thông tin về Port, bạn không được phép cấp cáp.")
          this.loading(false);
          return;
      }

      let thongTinDauNoi = {
        choPhepDoiTramTbi: true,
        tramTbiId: this.tramtb_dau_id,
        chonTramTbiTheoTB: true,
        thueBaoId: this.form_ThongTinKhachHang.hdtb_id,
        kieu: 1
      } 
      console.log("thongTinDauNoi", thongTinDauNoi);

      //Comment dong nay nhe
      // this.$refs.daunoithuebao.setData(thongTinDauNoi)
      // this.$refs.daunoithuebao.show()


      this.state.modalTitle = "";
      this.modalComponent = () => import("@/modules/contract/setup/DeclareLandline/popup/daunoithuebao.vue");
      this.popupComponentName = "popupDauNoi";   
      this.popupComponentEvts["change"] = this.getTableDSThueBao();
      await this.modalComponent().then((x) => {});
      this.loading(false);
      this.$nextTick(() => { 
        this.$refs.modalComponent.setData(thongTinDauNoi);
        this.$refs.modalComponent.show();
      });  
      
    },
    async tsbtnChapNhan_Click(){
      if(await this.Cap_Nhat() == false){ 
        return;
      } 
      this.$toast.success("Cập nhật dữ liệu lên viễn thông tỉnh thành công !");
    }, 
    async tsbtnHoanCong_Click(){
       if(!this.form_ThongTinKhachHang || !this.form_ThongTinKhachHang.hdtb_id || this.form_ThongTinKhachHang.hdtb_id <= 0){
         this.$root.$toast.error('Bạn chưa chọn phiếu!')
         return false;
       }

       this.$bvModal
        .msgBoxConfirm("Bạn thật sự muốn hoàn công không ?", {
          title: "",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
          centered: true,
        })
        .then(async (v) => {
          if (!v) {
            return;
          }

          await this.Hoan_Cong(); 
      }); 
    }, 
    async Hoan_Cong(){
      try{ 
        this.loading(true)
        let loaihd_id = this.form_ThongTinKhachHang.cboLoaiHD;
        let vhuonggiaodich = this.huonggiao_id;
        // 1. ==> tham chiếu đến UR2.5.010_031_001
        let vds = [];
        vds = [{
          LUONG_ID: this.luong_id,
          HDKH_ID: this.form_ThongTinKhachHang.hdkh_id,
          HDTB_ID: this.form_ThongTinKhachHang.hdtb_id,
          MA_TB: this.form_ThongTinKhachHang.txtMaTB,
          PHIEU_ID: this.form_ThongTinKhachHang.phieu_id,
          TEN_DV: this.tt_nd.ten_dv,
        }];
        let vkieu = this.daucuoi_id; //1: điểm đầu, 2: điểm cuối
        console.log(vds); 
        let data = await fn.fn_kiemtra_hoancong_hoancongtsl(this.axios, vds)
       
        console.log("fn_kiemtra_hoancong_hoancongtsl", data);
         
        if(data !== "1") {
          throw "Lỗi kiểm tra hoàn công! " + data;
        } 
 
        // 2. Nếu hàm kiểm tra thành công, gọi 
        //         if (!Cap_Nhat()) return; ==> tham chiếu đến UR2.5.010_032_001
        if(await this.Cap_Nhat() == false){ 
          throw "Lỗi thực hiện cập nhật hợp đồng";
        } 

        // 3. ==> Tham chiếu đến UR2.5.010_031_002 
        vds = [{
          LUONG_ID: this.luong_id,
          HDKH_ID: this.form_ThongTinKhachHang.hdkh_id,
          HDTB_ID: this.form_ThongTinKhachHang.hdtb_id,
          MA_TB: this.form_ThongTinKhachHang.txtMaTB,
          PHIEU_ID: this.form_ThongTinKhachHang.phieu_id,
          NHANVIEN_ID: this.tt_nd.nhanvien_id,
          NGUOIGV_ID: this.form_ThongTinGiaoViec.cboNguoiGV,
          NGAY_GV: this.form_ThongTinGiaoViec.dtpNgayGV, //'07/09/2021 00:00:00',
          NGAY_BG: this.form_ThongTinGiaoViec.dtpNgayBG,
          LOAIHD_ID: loaihd_id,
          TTHD_ID: this.tthd_id,
        }];
        //[{\"HDTB_ID\": 2003394,\"LUONG_ID\": 1322,\"PHIEU_ID\": 147202,\"NHANVIEN_ID\": 452,\"NGUOIGV_ID\": 452, \"NGAY_GV\": '07/09/2021 00:00:00', \"NGAY_BG\": '07/09/2021 00:00:00',\"LOAIHD_ID\": 1,\"MA_TB\": \"thuanvb80\" }]        
        // console.log("fn_thuchien_hoancong_hoancongtsl", vkieu, vds) 
        data = await fn.fn_thuchien_hoancong_hoancongtsl(this.axios, vds, vkieu);
        if (!data) {
          throw "Lỗi thực hiện hoàn công TSL: fn_thuchien_hoancong_hoancongtsl return null";
        }

        console.log("fn_thuchien_hoancong_hoancongtsl", data);
        if(data == "1") {
          this.$toast.success("Hoàn thiện hồ sơ thành công! ");
          await this.HienThiDanhSachHDTB();
          return;
        }

        if(data.indexOf('HUONGGIAO-') >=0 && data.split('-')[0] == '1'){
           vhuonggiaodich = data.split('-')[2]*1
        } 
        else
        { 
          throw "Lỗi thực hiện hoàn công TSL: " + data;
        }
      
        // 4. ==> Tham chiếu đến UR2.5.010_031_003
        /* #region LoaiHopDong.DAT_MOI*/
        if (loaihd_id == LoaiHopDong.DAT_MOI || loaihd_id == LoaiHopDong.KHOIPHUC_SD || loaihd_id == LoaiHopDong.DI_CHUYEN )
        { 
          if(this.khachhang_id > 0){
            if(await fn.fn_co_thuebao_didong(this.axios, this.thanhtoan_id) == 1){ //Mã thanh toán có thuê bao di động
              this.HienThiTTDiaChi(fn.LAY_DS_DIACHI_THEO_HDKH_ID(this.axios, this.form_ThongTinKhachHang.hdkh_id), 1);
              this.HienThiTTDiaChi(fn.LAY_DS_DIACHI_THEO_HDTT_ID(this.axios, this.form_ThongTinKhachHang.hdtt_id), 2);
              //await this.UPDATE_HDKH_VINAPHONE(); Bỏ
            }

            await this.PushData_HNI()

            vds = [{
                LUONG_ID: this.luong_id,
                HDKH_ID: this.form_ThongTinKhachHang.hdkh_id,
                HDTB_ID: this.form_ThongTinKhachHang.hdtb_id,
                MA_TB: this.form_ThongTinKhachHang.txtMaTB,
                PHIEU_ID: this.form_ThongTinKhachHang.phieu_id,
                NHANVIEN_ID: this.tt_nd.nhanvien_id,
                NGUOIGV_ID: this.form_ThongTinGiaoViec.cboNguoiGV,
                NGAY_GV: this.form_ThongTinGiaoViec.dtpNgayGV, //'07/09/2021 00:00:00',
                NGAY_BG: this.form_ThongTinGiaoViec.dtpNgayBG,
                LOAIHD_ID: loaihd_id,
                DONVI_ID: this.tt_nd.donvi_id,
                MAY_CN: this.tt_nd.may_cn,
                NGUOI_CN: this.tt_nd.nguoi_cn,
                IP_CN: this.tt_nd.ip_cn,
                DICHVUVT_ID: this.dichvuvt_id,
                DAUCUOI_ID: this.daucuoi_id,
                HUONGGIAO_ID:  this.huonggiao_id,
                TTHD_ID: this.tthd_id
              }]; 
             data = await fn.fn_theoloaihd_hoancong_hoancongtsl(this.axios, vds, vhuonggiaodich); 
             console.log('fn_theoloaihd_hoancong_hoancongtsl', data); 
             if(data !== "1") {
                throw "Lỗi hoàn công theo loại hợp đồng! " + data;
             } 
          }
        } 
        /* #endregion*/

        // 5. ==> Tham chiếu đến UR2.5.010_031_004
        /* #region LoaiHopDong.KHOIPHUC_SD*/
        if (loaihd_id == LoaiHopDong.KHOIPHUC_SD)
        {
             console.log('HoanCong: LoaiHopDong.KHOIPHUC_SD')
        }
        /* #endregion*/

        // 6. ==> Tham chiếu đến UR2.5.010_031_005
        /* #region LoaiHopDong.DI_CHUYEN*/
        if (loaihd_id == LoaiHopDong.DI_CHUYEN)
        {
           console.log('HoanCong: LoaiHopDong.DI_CHUYEN')
        }
        /* #endregion*/

        // 7. ==> Tham chiếu đến UR2.5.010_031_006
        if (loaihd_id == LoaiHopDong.CHUYENDOI_LH)
        {
            console.log('HoanCong: LoaiHopDong.CHUYENDOI_LH')
            vds = [{
              LUONG_ID: this.luong_id,
              HDKH_ID: this.form_ThongTinKhachHang.hdkh_id,
              HDTB_ID: this.form_ThongTinKhachHang.hdtb_id,
              MA_TB: this.form_ThongTinKhachHang.txtMaTB,
              PHIEU_ID: this.form_ThongTinKhachHang.phieu_id,
              NHANVIEN_ID: this.tt_nd.nhanvien_id,
              NGUOIGV_ID: this.form_ThongTinGiaoViec.cboNguoiGV,
              NGAY_GV: this.form_ThongTinGiaoViec.dtpNgayGV, //'07/09/2021 00:00:00',
              NGAY_BG: this.form_ThongTinGiaoViec.dtpNgayBG,
              LOAIHD_ID: loaihd_id,
              DONVI_ID: this.tt_nd.donvi_id,
              MAY_CN: this.tt_nd.may_cn,
              NGUOI_CN: this.tt_nd.nguoi_cn,
              IP_CN: this.tt_nd.ip_cn,
              DICHVUVT_ID: this.dichvuvt_id,
              DAUCUOI_ID: this.daucuoi_id,
              HUONGGIAO_ID: this.huonggiao_id,
              TTHD_ID: this.tthd_id,
            }];
             
            data = await fn.fn_theoloaihd_hoancong_hoancongtsl(this.axios, vds, vhuonggiaodich); 
            console.log('fn_theoloaihd_hoancong_hoancongtsl', data); 
            if(data !== "1") {
              throw "Lỗi hoàn công theo loại hợp đồng! " + data;
            } 
        }
        // 8. ==> Tham chiếu đến UR2.5.010_031_007
        vds = [{
          HDTB_ID: this.form_ThongTinKhachHang.hdtb_id,
          LUONG_ID: this.luong_id,
          NGAY_BG: this.form_ThongTinGiaoViec.dtpNgayBG,
        }];
        data = await fn.fn_kethuc_hoancong_hoancongtsl(this.axios, vds); 
        if(data=="1"){          
          this.$toast.success("Hoàn thiện hồ sơ thành công! ");
          await this.HienThiDanhSachHDTB();
        }
        else
        {
          this.$toast.success("Hoàn công thành công! " + data);
          await this.HienThiDanhSachHDTB();
        }

        // 9. ==> Tham chiếu đến UR2.5.010_015
        
        //========================
      }catch(ex){
        console.error('tsbtnHoanCong_Click', ex) 
        commonFn.showException(this, ex);
      }finally{
        this.loading(false)
      }
    },
    async HienThiDanhSachHDTB(){
      await this.getTableDSThueBao();
    },
 
    clearForm() {
      this.form_ThongTinGiaoViec.table_DSNV.options = [];
      this.form_ThongTinKhachHang.phieu_id = null;
      this.form_ThongTinKhachHang.hdtb_id = null;
      this.form_ThongTinKhachHang.hdkh_id = null;
      this.form_ThongTinKhachHang.cboLoaiHinh = null;
      this.form_ThongTinKhachHang.chkHen = false;
      this.form_ThongTinKhachHang.hdtt_id = 0;

      // this.form_ThongTinKhachHang.cboQuyTrinh = null;
      this.form_ThongTinHoanCong_DC.txtSoSoiQuang = "";
      // thuebao_id = 0;

      // tramtb_dau_id = 0;
      // port_dau_id = 0;
      // vci_vpi_dau_id = 0;
      this.form_ThongTinHoanCong_DD.txtPort_Dau = "";

      this.tramtb_cuoi_id = 0;
      // port_cuoi_id = 0;
      // vci_vpi_cuoi_id = 0;
      this.form_ThongTinHoanCong_DC.txtPort_Cuoi = "";

      this.form_ThongTinKhachHang.txtMaTB = "";
      this.form_ThongTinHoanCong_DD.txtSoAoDau = "";
      this.form_ThongTinHoanCong_DC.txtSoAoCuoi = "";
      // kt_thaydoi = false;
      this.form_ThongTinGiaoViec.txtNhanVien = "";
      this.form_ThongTinChung.txtNV_TiepThi = "";

      this.form_ThongTinChung.cboKenhThu = "";

      //cboKieuHD.SelectedValue = KIEU_HD.TAI_NHA;
      this.form_ThongTinHoanCong_DD.lblLienHe_Dau = "";
      // kieu = 0;
      this.daucuoi_id = 0;
      this.form_ThongTinKhachHang.txtMaGD = "";
      this.form_ThongTinKhachHang.txtTenTB = "";
      this.form_ThongTinKhachHang.txtDiaChiTB = "";
      this.form_ThongTinKhachHang.txtTenKieuLD = "";

      //dtgDanhSach.DataSource = null;
      // gridDanhSach.DataSource = null;
      // kieu = 0;
      // chkNgayBG.Checked = false;
      this.form_ThongTinGiaoViec.cboNguoiGV = this.$root.token.getNhanVienID().toString();
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

      this.form_ThongTinHoanCong_DD.table.options = []
      this.form_ThongTinHoanCong_DC.table.options = []
      this.form_ThongTinHoanCong_DD.txtSvlan_Dau = "";
      this.form_ThongTinHoanCong_DD.txtAdsl_cap_Dau = "";
      this.form_ThongTinHoanCong_DD.txtCvlan_Dau = "";
      
      this.form_ThongTinHoanCong_DC.txtSvlan_Cuoi = "";
      this.form_ThongTinHoanCong_DC.txtAdsl_cap_Cuoi = "";
      this.form_ThongTinHoanCong_DC.txtCvlan_Cuoi = "";

      this.daikv_dau_id = 0;
      this.daikv_cuoi_id = 0;
      this.tramvt_dau_id = 0;
      this.tramvt_cuoi_id = 0;
      // gridNhanVien.DataSource = null;
      // chkNgayGV.Checked = false;
 
      this.form_ThongTinGiaoViec.dtpNgayGV = moment(new Date()).format('DD/MM/YYYY HH:mm:ss');
      this.form_ThongTinGiaoViec.chkNgayGV = false;

      this.form_ThongTinGiaoViec.dtpNgayBG = moment(new Date()).format('DD/MM/YYYY HH:mm:ss');
      this.form_ThongTinGiaoViec.chkNgayBG = false;

      // this.form_ThongTinGiaoViec.dtpNgayGV = tt_nd.ngay_cn;

      this.khachhang_id = 0;
      this.thanhtoan_id = 0;

      this.diachiHK = {
                DIACHI: null,
                TINH_ID:0,
                QUAN_ID:0,
                PHUONG_ID:0,
                PHO_ID:0,
                AP_ID:0,
                KHU_ID:0,
                DACDIEM_ID:0,
                SO_NHA:null,
                Lng: 0,
                Lat: 0,
                BLOCK: null,
                TANG: null,
                PHONG: null,
                MOTA: null,
            };
      this.diachiTT = {
                DIACHI: null,
                TINH_ID:0,
                QUAN_ID:0,
                PHUONG_ID:0,
                PHO_ID:0,
                AP_ID:0,
                KHU_ID:0,
                DACDIEM_ID:0,
                SO_NHA:null,
                Lng: 0,
                Lat: 0,
                BLOCK: null,
                TANG: null,
                PHONG: null,
                MOTA: null,
            };
        this.diachiLD = {
                DIACHI: null,
                TINH_ID:0,
                QUAN_ID:0,
                PHUONG_ID:0,
                PHO_ID:0,
                AP_ID:0,
                KHU_ID:0,
                DACDIEM_ID:0,
                SO_NHA:null,
                Lng: 0,
                Lat: 0,
                BLOCK: null,
                TANG: null,
                PHONG: null,
                MOTA: null,
            };
    },
    handVisibleThongTin() {
      this.isDisplayThongTin = false;

      this.$nextTick(() => {
        this.isVisibleThongTin = !this.isVisibleThongTin;
        this.isDisplayThongTin = true;
      });
    },
    async getDanhsachThuebao(enter = false, type = '') {
      console.log('getDanhsachThuebao');
    }, 
    HienThiTTDiaChi(ds, kieu) { 
            ds = commonFn.LowerCasePropertyList(ds);
            console.log("HienThiTTDiaChi: kieu= " + kieu, ds);
            if (ds.length > 0) {
                switch (kieu) {
                    case 1:
                        this.diachikh_id = ds[0]['diachi_id'] ? ds[0]['diachi_id'] : ds[0]['diachiid'];
                        this.diachiKH.TINH_ID = ds[0]["tinh_id"] ? ds[0]["tinh_id"] : ds[0]["tinhid"];
                        this.diachiKH.QUAN_ID = ds[0]["quan_id"] ? ds[0]["quan_id"] : ds[0]["quanid"];
                        this.diachiKH.PHUONG_ID = ds[0]["phuong_id"] ? ds[0]["phuong_id"] : ds[0]["phuongid"];
                        if (ds[0]["pho_id"] || ds[0]["phoid"])
                            this.diachiKH.PHO_ID = ds[0]["pho_id"] ? ds[0]["pho_id"] : ds[0]["phoid"];
                        else
                            this.diachiKH.PHO_ID = 0;
                        if (ds[0]["ap_id"] || ds[0]["apid"])
                            this.diachiKH.AP_ID = ds[0]["ap_id"] ? ds[0]["ap_id"] : ds[0]["apid"];
                        else
                            this.diachiKH.AP_ID = 0;
                        if (ds[0]["khu_id"] || ds[0]["khuid"])
                            this.diachiKH.KHU_ID = ds[0]["khu_id"] ? ds[0]["khu_id"] : ds[0]["khuid"];
                        else
                            this.diachiKH.KHU_ID = 0;
                        if (ds[0]["dacdiem_id"] || ds[0]["dacdiemid"])
                            this.diachiKH.DACDIEM_ID = ds[0]["dacdiem_id"] ? ds[0]["dacdiem_id"] : ds[0]["dacdiemid"];
                        else
                            this.diachiKH.DACDIEM_ID = 0;
                        this.diachiKH.SO_NHA = this.ChuanHoaTen(ds[0]["sonha"]);
                        if (ds[0]["vido"] && ds[0]['vido'])
                            this.diachiKH.Lat = ds[0]["vido"];
                        else
                            this.diachiKH.Lat = 0;
                        if (ds[0]["kinhdo"] && ds[0]['kinhdo'])
                            this.diachiKH.Lng = ds[0]["kinhdo"];
                        else
                            this.diachiKH.Lng = 0;
                        
                        break;
                    case 2:
                        this.diachitt_id = ds[0]['diachi_id'] ? ds[0]['diachi_id'] : ds[0]['diachiid'];
                        this.diachiTT.TINH_ID = ds[0]["tinh_id"] ? ds[0]["tinh_id"] : ds[0]["tinhid"];
                        this.diachiTT.QUAN_ID = ds[0]["quan_id"] ? ds[0]["quan_id"] : ds[0]["quanid"];
                        this.diachiTT.PHUONG_ID = ds[0]["phuong_id"] ? ds[0]["phuong_id"] : ds[0]["phuongid"];
                        if (ds[0]["pho_id"] || ds[0]["phoid"])
                            this.diachiTT.PHO_ID = ds[0]["pho_id"] ? ds[0]["pho_id"]: ds[0]["phoid"];
                        else
                            this.diachiTT.PHO_ID = 0;
                        if (ds[0]["ap_id"] && ds[0]["apid"])
                            this.diachiTT.AP_ID = ds[0]["ap_id"] ? ds[0]["ap_id"] : ds[0]["apid"];
                        else
                            this.diachiTT.AP_ID = 0;
                        if (ds[0]["khu_id"] || ds[0]["khuid"])
                            this.diachiTT.KHU_ID = ds[0]["khu_id"] ? ds[0]["khu_id"] : ds[0]["khuid"];
                        else
                            this.diachiTT.KHU_ID = 0;
                        if (ds[0]["dacdiem_id"] || ds[0]["dacdiemid"])
                            this.diachiTT.DACDIEM_ID = ds[0]["dacdiem_id"] ? ds[0]["dacdiem_id"] : ds[0]["dacdiemid"];
                        else
                            this.diachiTT.DACDIEM_ID = 0;
                        this.diachiTT.SO_NHA = this.ChuanHoaTen(ds[0]["sonha"]);
                        if (ds[0]["vido"] && ds[0]['vido'])
                            this.diachiTT.Lat = ds[0]["vido"];
                        else
                            this.diachiTT.Lat = 0;
                        if (ds[0]["kinhdo"] && ds[0]['kinhdo'])
                            this.diachiTT.Lng = ds[0]["kinhdo"];
                        else
                            this.diachiTT.Lng = 0;

                        if (ds[0]["diachibc_id"] || ds[0]["diachibcid"])
                            this.diachibc_id = ds[0]["diachibc_id"] ? ds[0]["diachibc_id"] : ds[0]["diachibcid"];
                        else this.diachibc_id = 0;
                        if (ds[0]["tinhbc_id"] || ds[0]["tinhbcid"])
                            this.diachiBC.TINH_ID = ds[0]["tinhbc_id"] ? ds[0]["tinhbc_id"] : ds[0]["tinhbcid"];
                        else this.diachiBC.TINH_ID = 0;
                        if (ds[0]["quanbc_id"] || ds[0]["quanbcid"])
                            this.diachiBC.QUAN_ID = ds[0]["quanbc_id"] ? ds[0]["quanbc_id"] : ds[0]["quanbcid"];
                        else this.diachiBC.QUAN_ID = 0;
                        if (ds[0]["phuongbc_id"] || ds[0]["phuongbcid"])
                            this.diachiBC.PHUONG_ID = ds[0]["phuongbc_id"] ? ds[0]["phuongbc_id"] : ds[0]["phuongbcid"];
                        else this.diachiBC.PHUONG_ID = 0;
                        if (ds[0]["phobc_id"] || ds[0]["phobcid"])
                            this.diachiBC.PHO_ID = ds[0]["phobc_id"] ? ds[0]["phobc_id"] : ds[0]["phobcid"];
                        else
                            this.diachiBC.PHO_ID = 0;
                        if (ds[0]["apbc_id"] || ds[0]["apbcid"])
                            this.diachiBC.AP_ID = ds[0]["apbc_id"] ? ds[0]["apbc_id"] : ds[0]["apbcid"];
                        else
                            this.diachiBC.AP_ID = 0;
                        if (ds[0]["khubc_id"] || ds[0]["khubcid"])
                            this.diachiBC.KHU_ID = ds[0]["khubc_id"] ? ds[0]["khubc_id"] : ds[0]["khubcid"];
                        else
                            this.diachiBC.KHU_ID = 0;
                        if (ds[0]["dacdiembc_id"] || ds[0]["dacdiembcid"])
                            this.diachiBC.DACDIEM_ID = ds[0]["dacdiembc_id"] ? ds[0]["dacdiembc_id"] : ds[0]["dacdiembcid"];
                        else
                            this.diachiBC.DACDIEM_ID = 0;
                        if (ds[0]["sonha_bc"] || ds[0]["sonhabc"])
                            this.diachiBC.SO_NHA = this.ChuanHoaTen(ds[0]["sonha_bc"] ? ds[0]["sonha_bc"] : ds[0]["sonhabc"]);
                        else this.diachiBC.SO_NHA = '';
                        if (ds[0]["vido_bc"] || ds[0]['vidobc'])
                            this.diachiBC.Lat = ds[0]["vido_bc"] ? ds[0]["vido_bc"] : ds[0]["vidobc"];
                        else
                            this.diachiBC.Lat = 0;
                        if (ds[0]["kinhdo_bc"] || ds[0]['kinhdo_bc'])
                            this.diachiBC.Lng = ds[0]["kinhdo_bc"] ?  ds[0]['kinhdo_bc'] :  ds[0]['kinhdobc'];
                        else
                            this.diachiBC.Lng = 0;
                        break;
                    
 
                        
                        // TODO LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2(phuongld_id, phold_id, apld_id, khuld_id, dacdiemld_id); LINE 15387
                        break;
                }
            }
        },
        async UPDATE_HDKH_VINAPHONE(){
          //TODO
          //Lam đã confirm bỏ
        },
        async PushData_HNI(){
          //TODO
        },
        async onCapNhatPortDau(f){
          if(!f) return;
          this.port_dau_id = f.port_id;
          this.vci_vpi_dau_id = f.vci_vpi_id;
          this.tramtb_dau_id = f.tramtb_id;
        },
        async onCapNhatPortCuoi(f){
          if(!f) return;
          this.port_cuoi_id = f.port_id;
          this.vci_vpi_cuoi_id = f.vci_vpi_id;
          this.tramtb_cuoi_id = f.tramtb_id;
        },
        async pageLoad(){
            this.tt_nd.may_cn = await this.$root.token.getMachine();
            this.tt_nd.ip_cn = await this.$root.token.getIP(); 
            //let ten_dv = await commonFn.MAP_ID(this.axios, 'ten_dv', 'admin.donvi', 'where donvi_id=' + this.tt_nd.donvi_id + ' and phanvung_id='+this.tt_nd.phanvung_id);
            let ten_dv =  commonFn.GetData(await api.fn_tt_donvi(this.axios,  { param: this.tt_nd.donvi_id, type: 2 } ))
            if(ten_dv)
              this.tt_nd.ten_dv = ten_dv;
            console.log('this.tt_nd', this.tt_nd); 
            this.getComboBox();
        },
        async tsbtnGiaoPhieu_Click(e) {
          if(!this.form_ThongTinKhachHang.phieu_id || this.form_ThongTinKhachHang.phieu_id <=0){
            this.$toast.error("Bạn chưa chọn phiếu!");
            return;
          } 

          if (!this.form_ThongTinKhachHang.txtMaGD) 
            return; 

          var huongGiao = await commonFn.lay_huonggiao_theo_luong_id(
            this.axios,
            this.luong_id
          );
          this.loading(false)
          if (!huongGiao) {
            this.$toast.error("Không tìm thấy hướng giao");
            return;
          }
 
          this.giaoPhieuViewDialog.input = {
            vhuonggiao_id: huongGiao.huonggiao_id*1,
            vloai_hd: this.form_ThongTinKhachHang.cboLoaiHD,
            vdichvuvt_id: this.dichvuvt_id,
            vma_gd: this.form_ThongTinKhachHang.txtMaGD,
            vquytrinh: this.form_ThongTinKhachHang.cboQuyTrinh,
            vtrangthaiphieu: 1,
          };
          this.GiaoPhieuView_OnClick()
        },
        async giaophieu_success(){
          this.getTableDSThueBao()
        },
        async giaoviec_success(){
          console.log("giaoviec_success"); 
          const data = await this.$store.dispatch(
            "declareMPLS-module/fetchDanhSachNhanVienID",
            {
              phieu_id: this.form_ThongTinKhachHang.phieu_id,
              nhanvien_id: this.$root.token.getNhanVienID(),
              kieu_id: 2,
            }
          );

          this.HienThiDSNV(data.data.data);
        },
        async btnPhieuTC_Click(){
          try { 
            var seletedRows = this.state.selectedMultiRows;

            if (!seletedRows || seletedRows.length <= 0) {
              this.$toast.error("Bạn chưa chọn phiếu! ");
              return false;
            }

            //let vhdkhid = commonFn.MAP_ID(this.axios, 'hdkh_id', 'css.hd_thuebao', 'where hdtb_id=' + this.form_ThongTinKhachHang.hdtb_id)
            let vhdkhid = commonFn.GetData(await api.fn_tt_hd_thuebao(this.axios,  { phanvung_id: this.tt_nd.phanvung_id, type: 1, param: this.form_ThongTinKhachHang.hdtb_id } ))
            if (vhdkhid == '-1') {
                this.$root.$toast.warning('Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!')
                return
            }
    
            let nhdkhid = []
            let nhdtbid = []
            let nphieuid = []
            nhdkhid = seletedRows.map((item) => item.hdkh_id)
            nhdtbid = seletedRows.map((item) => item.hdtb_id)
            nphieuid = seletedRows.map((item) => item.phieu_id)

            this.modelIn.n_phieu_id = nphieuid.join(',')
            this.modelIn.ma_gd = this.form_ThongTinKhachHang.txtMaGD;
            this.modelIn.n_hdkh_id = nhdkhid.join(',')
            this.modelIn.n_hdtb_id = nhdtbid.join(',')
            this.modelIn.nvth_id = -1
            this.modelIn.huonggiao_id = this.huonggiao_id
            this.modelIn.tentram_tb = ''
            console.log(this.modelIn)
            this.$refs.popupInBB.showModal()
            

          } catch (error) {
              commonFn.showException(this, error);
          }
        },
        async btnBienBanNT_Click(){
          this.$toast.warning("Không thực hiện in phiếu ở chức năng này");
        },
        async tsbtnTraPhieu_Click(){
          if(!this.form_ThongTinKhachHang.phieu_id || this.form_ThongTinKhachHang.phieu_id <=0){
            this.$toast.error("Bạn chưa chọn phiếu!");
            return;
          } 

          this.state.traphieu_success = false;
          this.state.modalTitle = "Trả phiếu nhân viên";
          this.state.modalId = "frmTraPhieuTC";
          this.dialogComponent = () => import("../../setup/ReturnInstallTicket/frmTraPhieuTC.vue");
          this.dialogEvts.close = this.popupTraphieu_closed
          this.popupComponentName = "frmTraPhieuTC"; 
          this.popupComponentEvts["traphieu_success"] = this.traphieu_success;
          //this.Popup("modalComponentsITVAS");
          await this.dialogComponent().then((x) => {});
          this.$nextTick(() => { 
            this.$refs.generalComponent.frmTraPhieuTC(
                                                      this.form_ThongTinKhachHang.txtMaGD, 
                                                      this.form_ThongTinKhachHang.cboLoaiHinh, 
                                                      this.dichvuvt_id, 
                                                      this.tt_nd.donvi_id,
                                                      this.form_ThongTinKhachHang.txtMaTB,
                                                      "0", 
                                                      "0", 
                                                      3);
            this.$refs.generalDialog.show();  
          });

          console.log(`tsbtnTraPhieu_Click ma_gd: ${this.form_ThongTinKhachHang.txtMaGD},  ma_tb: ${this.form_ThongTinKhachHang.txtMaTB},  dichvuvt_id: ${this.dichvuvt_id}, loaihd_id: ${this.form_ThongTinKhachHang.cboLoaiHD},  donvi_id: ${this.tt_nd.donvi_id}, `);
                    
          // this.$refs.frmTraPhieuTC.frmTraPhieuTC(
          //   this.form_ThongTinKhachHang.txtMaTB, 
          //   this.form_ThongTinKhachHang.cboLoaiHinh, 
          //   this.dichvuvt_id, 
          //   this.tt_nd.donvi_id,
          //   this.form_ThongTinKhachHang.txtMaTB,
          //   "0", 
          //   "0", 
          //   3);
          // this.$refs.popupFrmTraPhieuTC.show()
        },
        async traphieu_success(){
          console.log("traphieu_success");
          this.state.traphieu_success = true;
        },

        async popupTraphieu_closed(){
          console.log("popupTraphieu_closed", this.state.traphieu_success);
          if(this.state.traphieu_success && this.state.traphieu_success == true){ 
            this.form_DanhSachThueBao.table.options = this.form_DanhSachThueBao.table.options.filter(x => x.ma_tb != this.form_ThongTinKhachHang.txtMaTB); 
          }
        },
        async LayDiaChiLD(dr){
          console.log("LayDiaChiLD", dr);
          if(!dr) return;
          let hdtb_id = dr["hdtb_id"] * 1;
    
          var data = await commonFn.lay_ds_diachi_theo_hdtbid(this.axios, hdtb_id);
          if(data && data.length > 0){
            this.diachiLD.DIACHI = data[0].diaChi;
            this.diachiLD.TINH_ID = data[0].tinhLdId;
            this.diachiLD.QUAN_ID = data[0].quanLdId;
            this.diachiLD.PHUONG_ID = data[0].phuongLdId;
            this.diachiLD.PHO_ID = data[0].phoId; 
            this.diachiLD.SO_NHA = data[0].soNhaLd; 
            this.diachiLD.DIACHI_ID = data[0].diaChiLdId; 
            this.diachiLD.KHU_ID = data[0].khuLdId;
            this.diachiLD.DAC_DIEM_ID = data[0].dacDiemLdId; 
            this.diachiLD.Lng = data[0].kinhDoLd;  
            this.diachiLD.Lat = data[0].viDoLd; 
          }
        }, 
        onSelectedMultiRows(rows){
          console.log('onSelectedMultiRows', rows);
          this.state.selectedMultiRows = rows;
        },
        Popup(modalId) {
          this.$bvModal.show(modalId);
        },
        ClosePopup(modalId) {
          this.$bvModal.hide(modalId);
        },
        async dlgClose(e){
          console.log("dlgClose", e);
        },
        async popupClosed(val) {
          console.log("popupClosed", val)
          if(!val) {
            this.popupRetVal = {};
            this.ClosePopup('popupComponentsMPLS');
            return;
          }


          this.ClosePopup('popupComponentsMPLS');
        },
        async GiaoPhieuView_OnClick() {
          this.giaoPhieuViewDialog.isVisiable = true
          this.$refs.GiaoPhieuView.show()
        },
        async GiaoPhieuView_OnClose() {
          this.giaoPhieuViewDialog.isVisiable = false
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