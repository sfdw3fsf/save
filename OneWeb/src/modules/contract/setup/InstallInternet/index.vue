<template src="./template.html"></template>
<script>
import moment from 'moment'
import Select2Ext from '../DeclareLandline/components/Select2Ext'
import DataGridCustom from '../DeclareLandline/components/DataGridCustom'
import VueDateExt from '../DeclareLandline/components/VueDateExt'
import breadcrumb from '../DeclareLandline/components/breadcrumb.vue'

import ThongTinLienHe from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue'
import SearchSubsInfo from '@/modules/search/subscriber/SearchSubsInfo/SearchSubsInfo.vue'
import popupTestport from './popups/popup_testport'
import popupSuakenhthu from './popups/popup_suakenhthu.vue'
import popupKetQuaDoKiem from './popups/popup_KetQuaDoKiem.vue'
import popupGiaodoitac from './popups/popup_Giaodoitac.vue'
import popupToaNha from './popups/popup_Toanha.vue'
import DuAnHDTB from '@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/DuAnHDTB/DuAnHDTB'
import TraCuuThongSoKTModal from '@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/TraCuuThongSoKT/TraCuuThongSoKTModal.vue'
import popupDoicong from './popups/popup_doicong.vue'
import traphieu from '@/modules/contract/setup/ReturnInstallTicket/components/traphieu.vue'
import ChangeMacFiberPopUp from '@/modules/account/ChangeMacFiber/PopUps/ChangeMacFiberPopUp'
import ChonIPModal from '@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/ChonIP/ChonIPModal.vue'
import giaophieunhanvien from '@/modules/contract/setup/DeclareLandline/popup/giaophieunhanvien.vue'
import ChonPort from '@/modules/EXTS/QUANTRI/ThayDoiThongSoInternet/chon-port'
import ModalChonToaDo from '@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo'
import daunoithuebao from '@/modules/contract/setup/DeclareLandline/popup/daunoithuebao'
import ChuyenTo from '@/modules/contract/profile/CompleteProfileChangeAccount/Popups/ChuyenTo.vue'
import IP_WAN_LAN from '@/modules/EXTS/QUANTRI/ThayDoiThongSoInternet/IP_LAN_WAN'
import TonPhieuGiaoModal from '@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/TonPhieuGiao/TonPhieuGiaoModal.vue'
import ThongTinHenKhachHangModal from '@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import ThayDoiKhuVucQuanLyThueBaoModal from '../SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue'
import tracuu_onemesh from './popups/tracuu_onemesh.vue'
import CapVatTuTB from '@/modules/contract/complete_profile/CompleteProfileChangeTSMyTV/popups/CapVatTuTB.vue'
import VatTuThueBao from '@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/VatTuThueBao.vue'
import frmThongTinKT_2 from '../DeclareLandline/popups/frmThongTinKT_2.vue'
import frnGiaoPhieuNV from '../DeclareLandline/popups/frnGiaoPhieuNV.vue'
import frmTraPhieuTC from '../ReturnInstallTicket/frmTraPhieuTC.vue'
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal.vue'
import InBienBan from '@/modules/print/InBienBan/InBienBan'
import CompleteProfileChangeTSMyTVAPI from '@/modules/contract/complete_profile/CompleteProfileChangeTSMyTV/CompleteProfileChangeTSMyTVAPI'
import BNMService from '@/modules/EXTS/QUANTRI/ThayDoiThongSoInternet/service-bnm'
import popupGiaoPhieuPhoiHop from '../HandOverTicketVerifyCombine/popupGiaoPhieuPhoiHop.vue'
import CheckPort from '@/modules/search/subscriber/CheckPort'
import GiaoPhieuView from '@/modules/contract/setup/HandoverTicket/giao_phieu_view.vue'
import api from './api'
import PopupLoaiToanha from "@/modules/contract/setup/InstallInternet/popups/popup_Loaitoanha.vue"

import { TrangThaiHD, DichVuVienThong, LoaiHopDong, KIEU_HD, TRANGTHAI_DONGBO, LoaiHinhTB, LOAI_DV } from '@/const/enums'
export default {
  components: {
    breadcrumb,
    Select2Ext,
    DataGridCustom,
    VueDateExt,
    popupDoicong,
    frmTraPhieuTC,
    traphieu,
    ChangeMacFiberPopUp,
    frmThongTinKT_2,
    ChonIPModal,
    giaophieunhanvien,
    ChonPort,
    ModalChonToaDo,
    VatTuThueBao,
    daunoithuebao,
    ChuyenTo,
    IP_WAN_LAN,
    TonPhieuGiaoModal,
    ThongTinHenKhachHangModal,
    ThayDoiKhuVucQuanLyThueBaoModal,
    tracuu_onemesh,
    CapVatTuTB,
    ThongTinLienHe,
    SearchSubsInfo,
    popupTestport,
    popupSuakenhthu,
    popupKetQuaDoKiem,
    popupGiaodoitac,
    popupToaNha,
    DuAnHDTB,
    TraCuuThongSoKTModal,
    frnGiaoPhieuNV,
    ExportDataModal,
    InBienBan,
    popupGiaoPhieuPhoiHop,
    CheckPort,
    GiaoPhieuView,
    PopupLoaiToanha
  },
  props: ['Tag', '_matb_link', '_vloaihd_id', '_vquytrinh_id', 'isPopup'],
  data() {
    return {
      current_record: {},
      matb_link: '',
      dialogComponent: null,
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: '',
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close': this.popupClosed
      },
      dialogEvts: {
        close: this.dlgClose
      },
      modelIn: {},
      modalTitle: '',
      position: { X: 'center', Y: 'top' },
      columns_nv: [
        {
          fieldName: 'ten_nv',
          headerText: 'Tên nhân viên',
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: 'so_dt',
          headerText: 'Số điện thoại',
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: 'nhiemvu',
          headerText: 'Nhiệm vụ',
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: 'ghichu',
          headerText: 'Ghi chú',
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        }
      ],
      columns: [
        // {
        //   fieldName: "chon",
        //    width: "100",
        //   freeze: "left",
        //   headerText: "Chọn in",
        //   textAlign: "center",
        //   template: this.getColTemplateChonIn(this)
        // },
        {
          fieldName: 'ma_tb',
          headerText: 'Số máy/Acc',
          allowFiltering: true,
          allowSorting: true,
          width: '100',
          freeze: 'left'
        },
        {
          fieldName: 'ma_lt',
          headerText: 'Số ảo',
          allowFiltering: true,
          allowSorting: true,
          width: '100',
          freeze: 'left'
        },
        {
          fieldName: 'ten_tb',
          headerText: 'Tên thuê bao',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'diachi_ld',
          headerText: 'Địa chỉ lắp đặt',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'ma_kv',
          headerText: 'Mã khu vực',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'ten_kv',
          headerText: 'Tên khu vực',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'ten_dt',
          headerText: 'Đối tác',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'username',
          headerText: 'Username',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'ngay_yc',
          headerText: 'Ngày YC',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'ngaygiao',
          headerText: 'Ngày giao',
          allowFiltering: true,
          customAttributes: { class: 'red' },
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'dv_hen',
          headerText: 'Đơn vị hẹn',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'giohen_tu',
          headerText: 'Hẹn từ',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'giohen_den',
          headerText: 'Hẹn đến',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'ma_gd',
          headerText: 'Mã giao dịch',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'goi_kt',
          headerText: 'Gói KT',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'chitieu_tg',
          headerText: 'Chỉ tiêu TG',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'gio_conlai',
          headerText: 'Giờ còn lại',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'thoigian_ck',
          headerText: 'Thời gian cam kết',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'ma_plkh',
          headerText: 'Mã PLKH',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'dv_giao',
          headerText: 'Đơn vị giao',
          customAttributes: { class: 'blue' },
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'in_status',
          headerText: 'Trạng thái in',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          headerText: 'Trạng thái',
          fieldName: 'ten_status',
          template: '${if(status == 0)} <span class="icon one-download green-icon"></span> ${else} <span class="icon one-check green-icon"></span>${/if} ${ten_status}'
        },
        {
          fieldName: 'ngay_ht',
          headerText: 'Ngày BG',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'ngay_kh',
          headerText: 'Ngày KH',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'lydoton_tc',
          headerText: 'Lý do tồn',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'ghichu_ton',
          headerText: 'Ghi chú tồn',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'ghichu',
          headerText: 'Ghi chú',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        },
        {
          fieldName: 'madoicap',
          headerText: 'Mã đôi cáp',
          allowFiltering: true,
          allowSorting: true,
          width: '150'
        },
        {
          fieldName: 'chuyenmang',
          headerText: 'Mạng khác',
          allowFiltering: true,
          allowSorting: true,
          width: '100',
          template: this.mangkhacTemplate()
        },
        {
          fieldName: 'soluong',
          headerText: 'Số lượng',
          allowFiltering: true,
          allowSorting: true,
          width: '100'
        }
      ],
      modelIn: {},
      modelVatTu: {},
      current_info: {},
      dataPopupKhuVuc: {},
      objIP_WAN_LAN: {},
      loai_chon_id: 1,
      CapVT: {},
      BangTuDien: {},
      tdan: {},
      dsDanhMuc: [],
      dsThamSo: [],
      vasc: {}, //nhapt
      cty_newline: false,
      _IS_USING_CABMAN_V2: -1,
      labelFunctionTitle: '',
      _IS_CHECK_PORT_MANUAL: -1,
      _IS_CHECK_VATTU_SD: -1,
      nhanvien_tc_id: -1,
      congviec_th: '',
      ngay_cn: moment().format('DD/MM/YYYY HH:mm:ss'),

      loaihd_id: 0,
      passwordMegaVNN: null,
      matb_visa: null,

      dtList: null,
      ds: null,
      dsIn: null,
      bangts: null,
      tcchung: null,
      giaophieutc: null,
      hoancong: null,
      getkey: null,
      nhanvien: null,
      hths_dm: null,
      hths_kptl: null,
      hths_dc: null,
      common: null,
      donvi_fc: null,
      //DANGKY_DVK_DATA dsDK_DVK : new DANGKY_DVK_DATA(),

      thamso_md: null,
      baoCaoFacade: null,
      tt_nd: null,
      cmUlt: null,

      ///TuấnNA khai báo biến dùng chung -- Ngày 12/08/2010
      VascService: null,
      ///HIEUTC thêm biến dùng cho VDC --- Ngày 04/09/2010
      vdcService: null,
      //LapHopDongFacade lapHD : new LapHopDongFacade(),
      checkdata: null,
      //Hiếu bổ  sung biên hoàn thiện hồ sơ ngày 04/09/2010
      hths_cd_lhtb: null,
      magoi_vdc: '0',
      magoi_vasc: '0',
      ma_gd: null,
      tb_mytv: '',
      tb_mega: '',
      tb_cd: '',
      madoicap: '',
      dsHDTB_ADSL: null,
      dichvuvt_id: null,
      thuebao_id: null,
      donvi_id: null,
      tramtb_id: null,
      map: {
        lat: '',
        long: ''
      },
      test_port: '',
      system: '',
      daikv_id: 0,
      port_id: null,
      vci_vpi_id: null,

      port_id_cu: 0,
      vci_vpi_id_cu: 0,

      _ketcuoi_id: 0,
      _vitri: 0,

      billingInfo: '',
      in_tocdo_id: null,
      in_tocdothuc_id: -1,
      iptinh: false,
      str_ip: '', //Dải ip tĩnh
      str_ipv4_wan: '',
      str_ipv6_lan: '',
      kt_taobang: 0,
      kt_status: '',

      kt_load: false,
      tthd_id: 0,
      dsloaihd_id: '',
      dsdichvuvt_id: '',
      _flagTSKT: '', //nhapt
      quytrinh_id: 0,
      huonggiao_id: 0,
      loc_nv: 0,
      luong_id: 0,
      congnghe_id: 0,
      hienthi_mau_tgcamket: 0,
      _list_mac_addr: '',

      BOQUA_KT_SUYHAO_UP: false, //AnhDt 26/05/2021: Nghiệm thu bỏ qua đo kiểm chiều UP

      dtThaoTac: null,
      dtControl: null,

      dsnhanviengp: null,
      objgiaophieu: null,
      portfc: null,
      loaitb_id: null,
      kt_thaydoi: false,
      _MESH_BOQUA_VATTU: 0,
      //*:::::::::::::::
      //nhapt 10/07/2015
      n_hdkh_id: '',
      n_hdtb_id: '',
      n_phieu_id: '',

      //
      vpass_alt: '',
      vuser_alt: '',
      vpass_xtest: '',
      vuser_xtest: '',
      khong_hoancong_chua_thanhtoan: 0,
      UPDATE_CTS: false,
      //AnhNT:24/09/2021 DS chủ quản hiển thị
      DS_CHUQUAN_THEO_QUYEN: '',
      current_item: {},
      checkPortDialog: {
        isVisiable: false,
        input: {
          vma_tb: null,
          vthuebao_id: null,
          vkieu: null
        }
      },
      giaoPhieuViewDialog: {
        isVisiable: false,
        input: {
          vhuonggiao_id: null,
          vloai_hd: null,
          vdichvuvt_id: null,
          vma_gd: null,
          vquytrinh: null,
          vtrangthaiphieu: 1
        }
      },
      animationSettings: { effect: 'None' }
    }
  },
  computed: {
    CURRENT_MATB() {
      try {
        return this.current_item ? this.current_item.ma_tb : ''
      } catch (err) {
        return ''
      }
    },
    CURRENT_MAGD() {
      try {
        return this.current_item ? this.current_item.ma_gd : ''
      } catch (err) {
        return ''
      }
    },
    CURRENT_MAKH() {
      try {
        return this.current_item ? this.current_item.ma_kh : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    //TEN_KH đang chọn trên lưới
    CURRENT_TENKH() {
      try {
        return this.current_item ? this.current_item.ten_kh : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    CURRENT_NGUOI_DD() {
      try {
        return this.current_item ? this.current_item.nguoi_dd : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    //HDTB_ID đang chọn trên lưới
    CURRENT_HDTB_ID() {
      try {
        return this.current_item ? this.current_item.hdtb_id : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    CURRENT_TRANGBI_ID() {
      try {
        return this.current_item ? this.current_item.trangbi_id : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    //HDKH_ID đang chọn trên lưới
    CURRENT_HDKH_ID() {
      try {
        return this.current_item ? this.current_item.hdkh_id : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    //PHIEU_ID đang chọn trên lưới
    CURRENT_PHIEU_ID() {
      try {
        return this.current_item ? this.current_item.phieu_id : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    //LOAITB_ID đang chọn trên lưới
    CURRENT_LOAITB_ID() {
      try {
        return this.current_item ? this.current_item.loaitb_id : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    //DICHVUVT_ID đang chọn trên lưới
    CURRENT_DICHVUVT_ID() {
      try {
        return this.current_item ? this.current_item.dichvuvt_id : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    //SOLUONG đang chọn trên lưới
    CURRENT_SOLUONG() {
      try {
        return this.current_item ? this.current_item.soluong : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    //KIEULD_ID đang chọn trên lưới
    CURRENT_KIEULD_ID() {
      try {
        return this.current_item ? this.current_item.kieuld_id : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    //STATUS đang chọn trên lưới
    CURRENT_STATUS() {
      try {
        return this.current_info ? this.current_info.status : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    //kiểm tra status null
    CURRENT_STATUS_ISNULL() {
      return this.current_info.status ? true : fasle
    },

    //DOITUONG_ID(string) đang chọn trên lưới
    CURRENT_DOITUONG_ID() {
      try {
        return this.current_item ? this.current_item.doituong_id : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    //THONGTIN_TC(string) đang chọn trên lưới
    CURRENT_THONGTIN_TC() {
      try {
        return this.current_item ? this.current_item.thongtin_tc : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    //IP(string) đang chọn trên lưới
    CURRENT_IP() {
      try {
        return this.current_item ? this.current_item.ip : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    //MUCCUOC_ID(string) đang chọn trên lưới
    CURRENT_MUCCUOC_ID() {
      try {
        return this.current_item ? this.current_item.muccuoc_id : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    //TUCTHI(string) đang chọn trên lưới
    CURRENT_TUCTHI() {
      try {
        return this.current_item ? this.current_item.tucthi : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    //TOCDOTHUC_ID(string) đang chọn trên lưới
    CURRENT_TOCDOTHUC_ID() {
      try {
        return this.current_item ? this.current_item.tocdothuc_id : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    //TOCDO_ID(string) đang chọn trên lưới
    CURRENT_TOCDO_ID() {
      try {
        return this.current_item ? this.current_item.tocdo_id : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },

    //Lấy giá trị của [col_name] đang chọn trên lưới
    _CURRENT_VALUE(col_name) {
      try {
        return this.current_item ? this.current_item[col_name] : ''
      } catch (err) {
        return ''
        //throw new Exception("Không tìm thấy trường MA_KH");
      }
    },
    //Tham số sử dụng cabman2.0
    IS_USING_CABMAN_V2() {
      if (this._IS_USING_CABMAN_V2 == -1) {
        this._IS_USING_CABMAN_V2 = this.dsThamSo.filter((item) => item.ma_ts == 'USING_CABMAN_2.0').length > 0
        //this._IS_USING_CABMAN_V2 = 1
      }

      return this._IS_USING_CABMAN_V2 == 1
    },

    //Tham số  check port nhân công
    IS_USING_CHECK_PM() {
      if (this._IS_CHECK_PORT_MANUAL == -1) {
        // this._IS_CHECK_PORT_MANUAL = thamso_md.IS_EXIST("CHECK_PORT_MANUAL") ? 1 : 0;
        this._IS_CHECK_PORT_MANUAL = this.dsThamSo.filter((item) => item.ma_ts == 'CHECK_PORT_MANUAL').length > 0
      }

      return this._IS_CHECK_PORT_MANUAL == 1
    },
    //Tham số check vật tư sử dụng 21/21/2021
    // Không check
    IS_USING_CHECK_VATTU_SD() {
      if (this._IS_CHECK_VATTU_SD == -1) {
        // this._IS_CHECK_VATTU_SD = thamso_md.CHECK_IS_EXIST("KIEMTRA_VT_SD") ? 1 : 0;
        this._IS_CHECK_VATTU_SD = this.dsThamSo.filter((item) => item.ma_ts == 'KIEMTRA_VT_SD').length > 0
      }

      return this._IS_CHECK_VATTU_SD == 1
    }
  },
  async mounted() {
    this.loading(true)
    if (this._matb_link) this.matb_link = this._matb_link
    await this.frmHoanCongDMAdsl_Huyen_FormLoad()
    this.loading(false)
  },
  methods: {
    tsbtnTTLoainha_Click() {
      this.$refs.popupLoaiToanha.hdtb_id = this.CURRENT_HDTB_ID
      this.$refs.popupLoaiToanha.type = 1
      this.$refs.popupLoaiToanha.NVKT_BATBUOC_NHAP_LOAINHA = this.NVKT_BATBUOC_NHAP_LOAINHA
      this.$refs.popupLoaiToanha.showDialog()
    },
    SuaMac_Close() {
      this.HienThiDanhSachHDTB()
    },
    txtSuaDCMac_Click()
        {
            if (this.CURRENT_LOAITB_ID != LoaiHinhTB.INTERNET_FTTH)
            {
                this.$toast.error("Thay đổi địa chỉ Mac chỉ áp dụng với loại hình: Fiber");
                return;
            }
            if (this.CURRENT_HDTB_ID == 0)
            {
                this.$toast.error("Bạn hãy chọn thuê bao để thay đổi địa chỉ Mac");
                return;
            }
            if (this.CURRENT_STATUS != TRANGTHAI_DONGBO.DONGBO_CM)
            {
                this.$toast.error("Thay đổi địa chỉ Mac chỉ áp dụng khi thuê bao đã được kích hoạt lên Visa");
                return;
            }
            
            this.$refs.dialogchangeMacFiberPopUp.LoaiId = 1
            this.$refs.dialogchangeMacFiberPopUp.TenThueBao = this.$refs.txtTenTB.value;
            //this.$refs.dialogchangeMacFiberPopUp.openDialog(this.CURRENT_HDTB_ID, this.CURRENT_MATB, this.CURRENT_LOAITB_ID, this.CURRENT_MAGD)
            setTimeout(()=>{
              this.$refs.dialogchangeMacFiberPopUp.openDialog(this.CURRENT_HDTB_ID, this.CURRENT_MATB, this.CURRENT_LOAITB_ID, this.CURRENT_MAGD)
            }, 500)
            
        },
    getColTemplateChonIn(parent) {
      return function() {
        return {
          template: {
            template: `<input v-model="model" type="checkbox"></input>`,
            data() {
              return {
                model: ''
              }
            },
            mounted() {
              this.$set(this.data, 'chon', false)
              this.model = this.data.chon
            },
            watch: {
              model: {
                handler() {
                  //let index = parent.dataSource.findIndex(item => item.ma_nv == this.data.ma_nv)
                  //parent.dataSource[index].chon = this.model
                },
                deep: true
              }
            }
          }
        }
      }
    },
    async popupClosed(val) {
      console.log('popupClosed')
      if (!val) {
        this.popupRetVal = {}
        this.ClosePopup('modalComponentsITVAS')
        return
      }

      try {
        switch (this.popupComponentName) {
          case 'popupHandoverTicket':
          case 'popupDoiCong':
            this.HienThiDanhSachHDTB()
            break
          case 'modalComponentsITVAS':
            this.ClosePopup('modalComponentsITVAS')
            break
        }
      } catch (err) {
        console.log(err)
      }
    },
    thongTinHenKhachHangClose() {
      this.HienThiDanhSachHDTB()
    },
    async dlgClose(e) {
      console.log('dlgClose', e)
      try {
        switch (this.popupComponentName) {
          case 'popupHandoverTicket':
            this.HienThiDanhSachHDTB()
          case 'popupDoiCong':
            this.HienThiDanhSachHDTB()
            break
        }
      } catch (err) {
        console.log(err)
      }
    },
    async tsbtnGiaoPhieu_PH_V2_Click() {
      if (this.$refs.gridDanhSach.getSelectedRecords().length == 0) return
      console.log({
        vhdtb_id_cur: this.CURRENT_HDTB_ID,
        vphieu_id_cur: this.CURRENT_PHIEU_ID,
        quytrinh_id: this.quytrinh_id
      })
      this.$refs.popupGiaoPhieuPhoiHop.showModal()
    },
    tsddbIn_Click() {
      try {
        this.kt_thaydoi = false
        this.InPhieu()
        if (this.kt_thaydoi) {
          let matb = this.$refs.txtMaTB.value
          this.HienThiDanhSachHDTB()
          let index = this.$refs.gridDanhSach.dataSource.filter((item) => item.ma_tb == matb)
          if (index >= 0) {
            this.$refs.gridDanhSach.selectRow(index, true)
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    async FN_MAP_ID(dk, idNeo, table) {
      let result = ''
      const req = {
        dk: dk,
        id_neo: idNeo,
        table: table
      }
      let data = this.GetData(await CompleteProfileChangeTSMyTVAPI.FN_MAP_ID(this.axios, req))
      if (data) {
        result = data
      }
      return result
    },
    GetData: function(response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    async InPhieu() {
      try {
        let vhdkhid = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao', { param: this.CURRENT_HDTB_ID, type: 1 })
        if (vhdkhid.data.data == '-1') {
          this.$root.$toast.warning('Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!')
          return
        }
        if (this.$refs.gridDanhSach.getSelectedRecords().length === 0) {
          this.$root.$toast.warning('Hãy chọn phiếu!')
          return
        }
        let nhdkhid = []
        let nhdtbid = []
        let nphieuid = []
        nhdkhid = this.$refs.gridDanhSach.getSelectedRecords().map((item) => item.hdkh_id)
        nhdtbid = this.$refs.gridDanhSach.getSelectedRecords().map((item) => item.hdtb_id)
        nphieuid = this.$refs.gridDanhSach.getSelectedRecords().map((item) => item.phieu_id)
        this.modelIn.n_phieu_id = nphieuid.join(',')
        this.modelIn.ma_gd = this.$refs.txtMaGD.value.trim()
        this.modelIn.n_hdkh_id = nhdkhid.join(',')
        this.modelIn.n_hdtb_id = nhdtbid.join(',')
        this.modelIn.nvth_id = -1
        this.modelIn.huonggiao_id = this.huonggiao_id
        this.modelIn.tentram_tb = ''
        this.$refs.popupInBB.showModal()
      } catch (error) {
        this.$root.$toast.error('Có lỗi khi in phiếu: ' + error.message)
      }
    },
    mangkhacTemplate() {
      return "<input type='checkbox' />"
    },
    chkNgayGV_CheckedChanged() {
      this.$refs.dtpNgayGV.disabled = !this.$refs.chkNgayGV.checked
    },
    chkNgayBG_CheckedChanged() {
      this.$refs.dtpNgayBG.disabled = !this.$refs.chkNgayBG.checked
    },
    async btnHoanThienHS_Click(cmd) {
      try {
        let data = await this.axios.post('web-thicong/thicong-internet/fn_hoanthien_brcd', {
          phieu_id: this.CURRENT_PHIEU_ID,
          cmd: cmd
        })
        let returnData = JSON.parse(data.data.data)
        if (returnData.ERROR_CODE == -3 || returnData.ERROR_CODE == -2) {
          this.$confirm(returnData.MESSAGE, {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning'
          }).then(async () => {
            this.btnHoanThienHS_Click(returnData.CMD)
          })
        } else {
          if (data.data.error_code != 'BSS-00000000') this.$toast.error(data.data.message)
          else {
            if(returnData.ERROR_CODE != 1) {
              this.$toast.error(returnData.MESSAGE)
            } else {
              this.$toast.success('Hoàn thiện thành công!')
              this.HienThiDanhSachHDTB()
            }
          }
        }
      } catch (err) {
        this.$toast.error('Hoàn thiện không thành công')
      }
    },
    async txtMaTB_KeyPress() {
      this.loc_nv = 0
      if (await this.PHAILAM('LOCTHEO_NV')) this.loc_nv = 1

      let vloai_id = 0 // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
      if (this.$refs.rdoPhieuMoi.checked) vloai_id = 0
      else vloai_id = 1

      let rs = await this.axios.post('/web-ccdv/tiepnhanhopdong/ds_phieu_hc', {
        caidat_dv: 0,
        huonggiao_id: this.huonggiao_id,
        kieu_id: 1,
        loai_id: vloai_id,
        loaihd_id: this.$refs.cboLoaiHD.value,
        loc_nv: this.loc_nv,
        ma_gd: this.$refs.txtMaTB.value,
        nhanvien_id: this.$root.token.getNhanVienID(),
        phieu_id: 0,
        tthd_id: this.tthd_id
      })

      this.dsIn = rs.data ? rs.data.data : []
      this.ClearForm()
      let kt = false
      if (this.dsIn.length > 0) {
        this.$refs.gridDanhSach.dataSource = this.dsIn
        this.dtList = this.dsIn[0]
        //this.Loc_PhieuTon(); làm sau
        this.HienThiAnhTrangThai()
      } else {
        this.ClearForm()
      }
    },
    async txtMaGD_KeyPress() {
      this.loc_nv = 0
      if (await this.PHAILAM('LOCTHEO_NV')) this.loc_nv = 1

      let vloai_id = 0 // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
      if (this.$refs.rdoPhieuMoi.checked) vloai_id = 0
      else vloai_id = 1

      let rs = await this.axios.post('/web-ccdv/tiepnhanhopdong/ds_phieu_hc', {
        caidat_dv: 0,
        huonggiao_id: this.huonggiao_id,
        kieu_id: 1,
        loai_id: vloai_id,
        loaihd_id: this.$refs.cboLoaiHD.value,
        loc_nv: this.loc_nv,
        ma_gd: this.$refs.txtMaGD.value,
        nhanvien_id: this.$root.token.getNhanVienID(),
        phieu_id: 0,
        tthd_id: this.tthd_id
      })

      this.dsIn = rs.data ? rs.data.data : []
      this.ClearForm()
      let kt = false
      if (this.dsIn.length > 0) {
        this.$refs.gridDanhSach.dataSource = this.dsIn
        this.dtList = this.dsIn[0]
        //this.Loc_PhieuTon(); làm sau
        this.HienThiAnhTrangThai()
      } else {
        this.ClearForm()
      }
    },
    XacNhanToaDo(data) {
      this.axios.post('/web-hopdong/thicong-internet/fn_capnhat_toado_thuebao_v2', {
        kieu: 1,
        db_id: this.CURRENT_HDTB_ID,
        lat: data.lat,
        lng: data.lng,
        update_tb_cung_cap: 1,
        force_update: 1,
        daucuoi_id: 2
      })
    },
    async btnChonbando() {
      try {
        let long = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_diachi_hdtb', { param: this.CURRENT_HDTB_ID, type: 1 })

        let lat = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_diachi_hdtb', { param: this.CURRENT_HDTB_ID, type: 2 })
        if (long.data.data != -1 && lat.data.data != -1)
          this.map = {
            lng: Number(long.data.data),
            lat: Number(lat.data.data)
          }

        this.$bvModal.show('ModalChonToaDo')
      } catch (err) {
        this.$toast.error('Có lỗi xảy ra')
      }
    },
    async onSelectPort(port) {
      console.log(port)
      await this.axios.post('web-thicong/thicong-internet/fn_capnhat_port_hdtb', {
        phieu_id: this.CURRENT_PHIEU_ID,
        port_id: port.port_id,
        vci_vpi_id: port.vci_vpi_id,
        matkhau: this.$refs.txtMatKhauADSL.value,
        ipv4_lan: this.$refs.txtIPADSL.value,
        ipv4_wan: this.$refs.txtIPv4Wan.value,
        ipv6_lan: this.$refs.txtIPv6Lan.value
      })

      this.HienThiTTHopDongTB()
    },
    async btnChonPort_TuDong_Click() {
      try {
        if (!this.CURRENT_PHIEU_ID) return
        let data = await this.axios.post('web-thicong/thicong-internet/fn_chon_port_tudong_hdtb', {
          phieu_id: this.CURRENT_PHIEU_ID
        })
        let rs = {}
        try {
          rs = JSON.parse(data.data.data)
        } catch (err) {
          this.$toast.error(data.data.data)
          return
        }
        this.$toast.success(rs.MESSAGE)
        this.HienThiTTHopDongTB()
      } catch (err) {
        this.$toast.error('Có lỗi xảy ra!')
      }
    },
    async chonPortNcong_Click() {
      try {
        let modal = this.$refs['ref-chon-port']
        let thamso = await this.axios.post('web-thicong/thicong-internet/fn_kiemtra_lay_port_macdinh_hdtb', {
          phieu_id: this.CURRENT_PHIEU_ID
        })

        let thamso_data = {}
        try {
          thamso_data = JSON.parse(thamso.data.data)
        } catch (err) {
          this.$toast.error(thamso.data.data)
          return
        }
        if (thamso_data.ERROR_CODE == '-1' || !thamso_data.RESULT) {
          if (thamso_data.MESSAGE) this.$toast.error(thamso_data.MESSAGE)
          else this.$toast.error('Không tìm thấy dữ liệu!')
          return
        }
        if (await this.PHAILAM('AON_CHI_CHON_DSLAM_TINH')) {
          modal.ChiCanChonDSLAM = true
        }
        modal.frmChonPort(
          thamso_data.RESULT.DONVI_ID,
          thamso_data.RESULT.TRAMTB_ID,
          thamso_data.RESULT.PORT_ID,
          thamso_data.RESULT.KIEU,
          parseInt(thamso_data.RESULT.DICHVUVT_ID),
          parseInt(thamso_data.RESULT.LOAITB_ID),
          thamso_data.RESULT.DIACHI_ID,
          thamso_data.RESULT.THAMSO,
          thamso_data.RESULT.HDTB_ID,
          thamso_data.RESULT.THUEBAO_ID,
          null,
          thamso_data.RESULT.TRAMTB_ID
        )

        console.log(thamso_data)

        modal.show()
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi xảy ra')
      }
    },
    callbackChonIP(data) {
      if (this.loai_chon_id == 1) this.$refs.txtIPADSL.value = data.diachiIPv4LAN
      if (this.loai_chon_id == 2) this.$refs.txtIPv4Wan.value = data.diachiIPv4WAN
      if (this.loai_chon_id == 3) this.$refs.txtIPv6Lan.value = data.diachiIPv6LAN
    },
    chonIp(kieu) {
      this.loai_chon_id = kieu
      this.$nextTick(() => {
        this.$refs.chonIPModal.showModal()
      })
    },
    async tsbtnThongTinLH_Click() {
      let data = {
        hdkh_id: this.CURRENT_HDKH_ID,
        khachhang_id: 0
      }

      console.log()
      console.log(data)
      this.modalTitle = 'Thông tin liên hệ'
      // this.$refs.handoverTicket.showPopup(data);
      // this.$refs.popupHandoverTicket.show();
      this.dialogComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue')
      this.popupComponentName = 'popupThongTinLienHe'
      await this.dialogComponent().then((x) => {})
      this.$nextTick(() => {
        this.$refs.generalComponent.showPopup(data)
        this.$refs.generalDialog.show()
      })
      // this.$refs.thongtinlienhe.show();
    },
    tsbtnToaNha_Click() {
      let popup = this.$refs.popupToaNha
      popup.txtMaTB = this.$refs.txtMaTB.value
      this.$nextTick(() => {
        popup.show()
      })
    },
    async tsbtnXemMatKhau_Click() {
      let data = await this.axios.get('web-thicong/thicong-internet/xem_matkhau', {
        params: {
          account: this.CURRENT_MATB
        }
      })

      if (data.data.data) {
        this.$confirm(data.data.data, {
          confirmButtonText: 'Oke',
          cancelButtonText: false,
          type: 'success'
        }).then(async () => {
          this.tsbtnHoanCong_Click(data.data.data.CMD)
        })
      } else {
        this.$toast.error(data.data.message)
      }
    },
    tsbtnTraCuuOneMesh_Click() {
      this.$refs.tracuu_onemesh.serial = this.$refs.txtMaTB.value
      this.$nextTick(() => {
        this.$refs.tracuu_onemesh.showModal()
      })
    },
    tsbtnShowIP_Click() {
      this.$bvModal.show('frmTraCuuVDC')
    },
    async tsbtnTestAllPort_Click() {
      let vInput = this.$refs.txtMaTB.value
      let vdichvuvtID = parseInt(DichVuVienThong.ADSL)

      try {
        let status = await this.getPortStatus(vInput, vdichvuvtID)

        if (!status || status == 'NA') {
          this.$root.toastError('Không tìm thấy thông tin')
        } else if (status == 'UP') {
          this.$root.toastSuccess('UP')
        } else {
          this.$root.toastError(status)
        }
      } finally {
      }
    },
    async getPortStatus(vInput, vdichvuvt_id) {
      try {
        let userAltInfo = await this.lay_tt_user_alt()

        let vuser_alt = !!userAltInfo && !!userAltInfo.user_alt ? userAltInfo.user_alt : ''
        let vpass_alt = !!userAltInfo && !!userAltInfo.pass_alt ? userAltInfo.pass_alt : ''
        if (!vuser_alt || !vpass_alt) {
          return ''
        }

        //Đo kiểm trước khi nghiệm thu
        if (vdichvuvt_id != DichVuVienThong.ADSL) {
          return ''
        }

        let response = await this.$root.context.post(`/tichhop/bnm/getPortStatus`, {
          input: vInput,
          linetestPassword: vpass_alt,
          linetestUser: vuser_alt,
          mac: 0
        })

        return response.data
      } catch (err) {
        return 'ERR: ' + err.toString()
      }
    },
    async lay_tt_user_alt() {
      try {
        let response = await this.$root.context.get(`/web-quantri/thaydoi-thongso-internet/lay_tt_user_alt`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return null
      }
    },
    tsbtnChuyenDoiTac() {
      this.$refs.popupGiaodoitac.show(this.CURRENT_HDTB_ID, this.CURRENT_PHIEU_ID)
    },
    tsbtnDuAn_Click() {
      this.$refs.popupDuAnHDTB.showModal()
    },
    overlayClick() {
      this.$refs.generalDialog.hide()
    },
    async tsbtnChuyenVMP_Click() {
      let _vmp = '0'
      let selected = this.$refs.gridDanhSach.getSelectedRecords()
      if (selected.length < 0) {
        return
      }
      console.log(selected[0].vmp)
      let message = 'Bạn thật sự kích hoạt VMP không ?'
      if (!selected[0].vmp || selected[0].vmp == 0) {
        message = 'Bạn thật sự kích hoạt MYTV không ?'
        _vmp = '1'
      }

      this.$confirm(message, {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning'
      }).then(async () => {
        this.axios.post('web-thicong/thicong-internet/chuyendoi_mytv_vmp', {
          hdtb_id: selected[0].hdtb_id,
          ma_tb: selected[0].ma_tb,
          vmp: _vmp,
          status: selected[0].status
        })
        this.$toast.success('Chuyển sang ' + (_vmp == '1' ? 'VMP' : 'MYTV') + ' thành công!')
        this.HienThiDanhSachHDTB()
      })
    },
    async tsbtnGiaoPhieu_Click() {
      let rs = await this.axios.post('web-ccdv/khaibaotsl/lay_huonggiao_theo_luong_id', { luong_id: this.luong_id })
      let dtTemp = rs.data.data
      if (dtTemp.length > 0) {
        this.giaoPhieuViewDialog.input = {
          vhuonggiao_id: dtTemp[0].huonggiao_id,
          vloai_hd: this.loaihd_id,
          vdichvuvt_id: this.dichvuvt_id,
          vma_gd: this.$refs.txtMaGD.value,
          vquytrinh: this.quytrinh_id,
          vtrangthaiphieu: 1
        }
        this.GiaoPhieuView_OnClick()
      } else {
        this.$toast.error('Chưa có hướng giao được gán')
      }
    },
    async kt_ip_wan_lan_v2(data) {
      try {
        let response = await this.$root.context.post(`/web-quantri/thaydoi-thongso-internet/kt_ip_wan_lan_v2`, data)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return null
      }
    },
    async tsbtnChonIPLANWAN_Click() {
      if (!this.$refs.cboBrasADSL.value) return
      if (this.$refs.cboBrasADSL.dataSource.length == 0) return
      if (this.$refs.gridDanhSach.getSelectedRecords().length <= 0) return

      try {
        let result = await this.kt_ip_wan_lan_v2({
          hdtb_id: this.CURRENT_HDTB_ID
        })

        if (result.kq != '1') {
          this.$root.toastError(result.kq)
          return
        }
      } finally {
      }

      let brasInfo = this.$refs.cboBrasADSL.dataSource.find((item) => item.bras_id == parseInt(this.$refs.cboBrasADSL.value))

      this.objIP_WAN_LAN = {
        hdtb_id: this.CURRENT_HDTB_ID,
        loaitb_id: this.CURRENT_HDTB_ID,
        dichvuvt_id: this.CURRENT_DICHVUVT_ID,
        bras_id: this.$refs.cboBrasADSL.value,
        ma_tb: this.CURRENT_MATB,
        tenbras: !brasInfo ? null : brasInfo.tenbras
      }
      this.$nextTick(() => {
        this.$bvModal.show('thayDoiThongSoInternet_popupIP_WAN_LAN')
      })
    },
    tsbtnDoKiem_Click() {
      this.Showport(0)
    },
    tsbtnShowBras_Click() {
      this.Showport(4)
    },
    Showport(vkieu) {
      this.checkPortDialog.input.vma_tb = this.$refs.txtMaTB.value
      this.checkPortDialog.input.vkieu = vkieu
      this.checkPortOnClick()
    },
    tonPhieuSuccess() {
      this.HienThiDanhSachHDTB()
    },
    tsbtnGiaoPhieu_Ton_Click() {
      if (!this.CURRENT_PHIEU_ID) {
        this.$toast.error('Hãy chọn phiếu trước để nhập lý do tồn!')
        return
      }
      //binding phieu_id, tthd_id
      this.$refs.tonPhieuGiaoModal.showModal()
    },
    btnPhieuGiaoSai_Click() {
      let selected = this.$refs.gridDanhSach.getSelectedRecords()
      if (selected.length == 0) return
      let vdichvuvt_id = selected[0].dichvuvt_id

      this.$refs.frmTraPhieuTC.frmTraPhieuTC(this.$refs.txtMaTB.value, this.$refs.cboLoaiHD.value, vdichvuvt_id, this.$root.token.getDonViID(), this.$refs.txtMaTB.value, '0', '0', 3)
      this.$nextTick(() => {
        this.$refs.popupFrmTraPhieuTC.show()
      })
    },
    traphieu_success() {
      this.HienThiDanhSachHDTB()
    },
    tsbtnGiaoViec_Click() {
      let data = this.$refs.gridDanhSach.getSelectedRecords()
      if (data.length <= 0) {
        this.$toast.error('Chưa có danh thuê bao. Bạn hãy kiểm tra lại! ')
        return
      }
      if (!this.$refs.cboNguoiGV.value) {
        this.$toast.error('Hãy nhập nhân viên giao việc!')
        return
      }
      if (!this.$refs.dtpNgayBG.value) {
        this.$toast.error('Hãy nhập ngày giao việc!')
        return
      }
      if (this.loaitb_id != LoaiHinhTB.INTERNET_ADSL_TINH_KHAC && this.loaitb_id != LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
        if (this.$refs.chkNgayBG.checked) {
          if (moment(this.$refs.dtpNgayGV.value, 'DD/MM/YYYY HH:mm:ss').diff(moment(this.$refs.dtpNgayBG.value, 'DD/MM/YYYY HH:mm:ss'), 'hours') > 0) {
            this.$toast.error('Ngày giao việc không được lớn hơn ngày hoàn thành!')
            return
          }
        }
      }

      let selected = this.$refs.gridDanhSach.getSelectedRecords()
      let dsphieuGV = []
      let dsma_tb = []
      let dshdtb_id = []
      if (selected.length > 0) {
        selected.forEach((item) => {
          dsphieuGV.push(item.phieu_id)
          dsma_tb.push(item.ma_tb)
          dshdtb_id.push(item.hdtb_id)
        })
      }

      //frnGiaoPhieuNV(CURRENT_PHIEU_ID, tt_nd.donvi_id, vnhanvien_giao_id, vngaygiao);
      //}
      let vnhanvien_giao_id = Number(this.$refs.cboNguoiGV.value)
      let vngaygiao = this.$refs.dtpNgayGV.value
      this.$refs.frnGiaoPhieuNV.frnGiaoPhieuNV(dsphieuGV, this.$root.token.getNhanVienID(), vnhanvien_giao_id, vngaygiao)
      this.$refs.frnGiaoPhieuNV.vma_tb = this.$refs.txtMaTB.value
      this.$refs.frnGiaoPhieuNV.vhdtb_id = this.CURRENT_HDTB_ID
      this.$refs.frnGiaoPhieuNV.dsma_tb = dsma_tb
      this.$refs.frnGiaoPhieuNV.dshdtb_id = dshdtb_id
      this.$refs.frnGiaoPhieuNV.huonggiao_id = this.huonggiao_id
      this.$nextTick(() => {
        this.$refs.frnGiaoPhieuNV.load()
        this.$refs.popupfrnGiaoPhieuNV.show()
      })
    },
    tsbtnNVKT_Click() {
      this.dataPopupKhuVuc.hdtb_id = this.CURRENT_HDTB_ID
      this.dataPopupKhuVuc.donviql_id = 0
      this.$nextTick(() => {
        this.$refs.thayDoiKhuVucQuanLyThueBaoModal.showModal()
      })
    },
    tsbtnThongTinKT_Click() {
      this.$refs.frmThongTinKT_2.thuebao_id = this.thuebao_id
      this.$refs.frmThongTinKT_2.ma_tb = this.$refs.txtMaTB.value
      this.$refs.frmThongTinKT_2.dichvuvt_id = DichVuVienThong.ADSL
      this.$refs.frmThongTinKT_2.kieu = 1
      this.$nextTick(() => {
        this.$refs.frmThongTinKT_2.show()
      })
    },
    tsbtnHen_Click() {
      this.$refs.thongTinHenKhachHangModal.showModal()
    },
    async tsbtnDoiCong_Click() {
      if (this.CURRENT_STATUS != TRANGTHAI_DONGBO.DONGBO_CM) {
        this.$toast.error('Thuê bao chưa được kích hoạt trên Visa/Vasc. \n Bạn không được thực hiện chức năng đổi cổng.')
        return
      }
      let data = {
        in_trangthai_dl: 0,
        in_hdtb_id: this.CURRENT_HDTB_ID,
        in_ma_tb: this.$refs.txtMaTB.value,
        in_ten_tb: this.$refs.txtTenTB.value,
        in_diachi_ld: this.$refs.txtDiaChiLD.value,
        in_flagTSKT: this._flagTSKT,
        in_dichvuvt_id: DichVuVienThong.ADSL,
        vloaitb_id: this.CURRENT_LOAITB_ID
      }
      console.log(data)
      this.modalTitle = 'Đổi cổng'

      this.dialogComponent = () => import('@/modules/EXTS/QUANTRI/ThayDoiThongSoInternet')
      this.popupComponentName = 'popupDoiCong'
      await this.dialogComponent().then((x) => {})
      this.$nextTick(() => {
        this.$refs.generalComponent.setPropData(data)
        this.$refs.generalComponent.frmThayDoiTSInternet_Load()
        this.$refs.generalDialog.show()
      })
    },
    async tsbtnXuatFile_Click() {
      this.$refs.ExportDataModal.showModal()
    },
    tsbtnChuyenTo_Click: async function() {
      try {
        if (await this.PHAILAM('KIEMTRA_VT_CHUYENTO')) {
          let bh = await this.axios.post('/web-ccdv/xuly_nghiemthubaohong/sp_lay_ds_phieuvt_hdbh', {
            hdtb_id: this.CURRENT_HDTB_ID,
            phieu_id: this.CURRENT_PHIEU_ID,
            baohong_id: 0
          })
          if (bh.data && bh.data.data && bh.data.data.length > 0) {
            this.$toast.error('Bạn phải xóa hết vật tư trước khi thực hiện chuyển tổ')
            return
          }
        }
        //
        //this.ShowSuccess(`Chuyển sang UR chuyển tổ`)
        if (this.CURRENT_PHIEU_ID > 0) {
          /*
           WinUIQLDHXLSuCo.frmChuyenTo f = new WinUIQLDHXLSuCo.frmChuyenTo();
                f.hdtb_id = hdtb_id;
                f.baohong_id = 0;
                f.phieu_id = phieu_id;
          */

          this.$refs.popupChuyenTo.openDialog(this.CURRENT_PHIEU_ID, this.CURRENT_HDTB_ID, 0)
        } else {
          this.$toast.error('Bạn chưa chọn thuê bao để chuyển !')
          return
        }

        //
        // WinUIQLDHXLSuCo.frmChuyenTo f = new WinUIQLDHXLSuCo.frmChuyenTo();
        // f.hdtb_id = hdtb_id;
        // f.baohong_id = 0;
        // f.phieu_id = phieu_id;
        // f.ShowDialog();
        // if (f.Chapnhan)
        //     HienThiDanhSach(dichvuvt_id);
      } catch (ex) {
        console.log(ex)
        this.$toast.error(JSON.stringify(ex.data ? ex.data.message : ex))
      }
    },
    tsbtnDauNoiCap_Click() {
      let thongTinDauNoi = {
        choPhepDoiTramTbi: true,
        tramTbiId: this.tramtb_id,
        chonTramTbiTheoTB: false,
        thueBaoId: this.CURRENT_HDTB_ID,
        kieu: 1
      }
      this.$refs.daunoithuebao.setData(thongTinDauNoi)
      this.$refs.daunoithuebao.show()
    },

    async tsbtnHoanCong_Click(cmd = '') {
      try {
        this.loading(true)
        let data = await this.axios.post('web-thicong/thicong-internet/hoancong_brcd', {
          phieu_id: String(this.CURRENT_PHIEU_ID),
          cmd: cmd ? cmd : ''
        })
        if (data.data.data.ERROR_CODE == -3) {
          this.loading(false)
          this.$confirm(data.data.data.MESSAGE, {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning'
          }).then(async () => {
            this.tsbtnHoanCong_Click(data.data.data.CMD)
          })
        } else if (data.data.error_code == 'BSS-00000000') {
          if (data.data.data.ERROR_CODE == -2) {
            this.tsbtnHoanCong_Click(data.data.data.CMD)
          } else if (data.data.data.ERROR_CODE == 0) {
            this.$toast.error(data.data.data.MESSAGE)
            this.loading(false)
          } else {
            this.loading(false)
            this.$toast.success(data.data.message)
            this.HienThiDanhSachHDTB()
          }
        } else {
          this.loading(false)
          this.$toast.error(data.data.message)
        }
      } catch (err) {
        this.loading(false)
        this.$toast.error('Hoàn công lỗi')
      }
    },
    async capnhatTH() {
      try {
        let data = await this.axios.post('/web-thicong/thicong-internet/sp_giaophieu_update_tt_giaoviec', {
          vphieu_id: this.CURRENT_PHIEU_ID,
          vnd_thuchien: this.$refs.txtNoiDungTH.value
        })
        this.HienThiTTHopDongTB()
        this.$toast.success('Cập nhật thành công!')
      } catch (e) {
        this.form.nd_thuchien = ''
        this.$toast.error('Cập nhật không thành công!')
        this.$toast.error(e.data)
      }
    },
    txtSeriMD_KeyPress() {
      if (this.$refs.txtSeriMD.value.length > 0) {
        this.$refs.chkSERI_MD.checked = true
      } else this.$refs.chkSERI_MD.checked = false
      this.chkSERI_MD_CheckedChanged()
    },

    txtSLID_KeyPress() {
      if (this.$refs.txtSLID.value.length > 0) {
        this.$refs.chkSLID.checked = true
      } else this.$refs.chkSLID.checked = false
      this.chkSLID_CheckedChanged()
    },
    chkLayTSo_CheckedChanged() {
      this.$toast.error('Chức năng này hiện tại không khả dụng')
    },
    chkSERI_MD_CheckedChanged() {
      if (this.$refs.chkSERI_MD.checked) {
        this.$refs.txtSeriMD.disabled = false
      } else this.$refs.txtSeriMD.disabled = true
    },
    chkSLID_CheckedChanged() {
      if (this.$refs.chkSLID.checked) this.$refs.txtSLID.disabled = false
      else this.$refs.txtSLID.disabled = true
    },
    chkdoicong_CheckedChanged() {
      if (this.$refs.chkdoicong.checked) {
        this.$refs.btnChonPort.disabled = false
        this.$refs.btnChonPort_TuDong.disabled = false
      } else {
        this.$refs.btnChonPort.disabled = true
        this.$refs.btnChonPort_TuDong.disabled = true
      }
    },
    checkEnable() {
      console.log('true')
      return true
    },
    async tsbtnHuyKH_Click(cmd = '') {
      if (!this.checkEnable('tsbtnHuyKH')) return false
      try {
        this.loading(true)
        let data = await this.axios.post('web-thicong/thicong-internet/huy_kichhoat_visa', {
          phieu_id: this.CURRENT_PHIEU_ID,
          cmd: cmd
        })
        if (data.data.data.ERROR_CODE == -3) {
          this.loading(false)
          this.$confirm(data.data.data.MESSAGE, {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning'
          }).then(async () => {
            this.tsbtnHuyKH_Click(data.data.data.CMD)
          })
        } else {
          if (data.data.error_code == 'BSS-00000000') {
            if (data.data.data.ERROR_CODE == -2) {
              this.tsbtnHuyKH_Click(data.data.data.CMD)
            } else {
              this.loading(false)
              this.$toast.success(data.data.message)
              this.HienThiTTHopDongTB()
            }
          } else {
            this.loading(false)
            this.$toast.error(data.data.message)
          }
        }
      } catch (err) {
        this.loading(false)
        console.log(err)

        this.$toast.error('Hủy kích hoạt lỗi')
      }
    },
    tsbtnKichHoatVisa_Click_First() {
      let selected = this.$refs.gridDanhSach.getSelectedRecords()
      if (selected.length == 0) return
      let ma_tb = selected[0].ma_tb
      this.$confirm('Bạn có chắc muốn kích hoạt Account ' + ma_tb + ' hay không?', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning'
      }).then(async () => {
        this.tsbtnKichHoatVisa_Click('')
      })
    },
    async tsbtnKichHoatVisa_Click(cmd = '') {
      try {
        this.loading(true)
        let data = await this.axios.post('web-thicong/thicong-internet/kichhoat_visa', {
          phieu_id: this.CURRENT_PHIEU_ID,
          auto_ip: this.$refs.chkTuDong.checked ? 1 : 0,
          giu_ip: this.$refs.chkGiuIP.checked ? 1 : 0,
          cmd: cmd
        })

        if (data.data.data.ERROR_CODE == -3) {
          this.loading(false)
          this.$confirm(data.data.data.MESSAGE, {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning'
          }).then(async () => {
            this.tsbtnKichHoatVisa_Click(data.data.data.CMD)
          })
        } else {
          if (data.data.error_code == 'BSS-00000000') {
            if (data.data.data.ERROR_CODE == -2) {
              this.tsbtnKichHoatVisa_Click(data.data.data.CMD)
            } else {
              this.loading(false)
              this.$toast.success(data.data.message)
              try {
                let phien_toida = this.getPhienToiDa()
                if((phien_toida != "") || (phien_toida != this.phien_toida)) {
                  let sendPTD = (phien_toida && Number(phien_toida) > 0) ? phien_toida : 1
                  let loginLimit = await this.axios.post("tichhop/visa-com/setLoginLimit", {
                    accountName: this.$refs.txtMaTB.value,
                    loginLimit: sendPTD
                  })
                }
              } catch (err) {
                console.log("Lỗi Login Limit")
                console.log(err)
              }
              this.HienThiTTHopDongTB()
            }
          } else {
            this.loading(false)
            this.$toast.error(data.data.message)
          }
        }
      } catch (err) {
        this.loading(false)
        this.$toast.error('Kích hoạt lỗi')
      }
    },
    getPhienToiDa() {
      let tocdo_id = this.$refs.cboGoiCuocAdsl.value
      let currentItem = this.$refs.cboGoiCuocAdsl.dataSource.find((item) => item.tocdo_id == tocdo_id)
      if (currentItem && currentItem.phien_toida && (Number(currentItem.phien_toida) > 0)){
        return currentItem.phien_toida
      } else {
        return ""
      }
    },
    async tsbtnChapNhan_Click() {
      try {
        // let month = moment(this.formatDate(this.$refs.dtpNgayBG.value), "DD/MM/YYY").format("m")
        // let year = moment(this.formatDate(this.$refs.dtpNgayBG.value), "DD/MM/YYY").format("YYYY")
        // console.log(this.formatDate(this.$refs.dtpNgayBG.value))

        // if(month < moment().format("m") || m > moment().format("m") || year != moment().format("YYYY")) {
        //   this.$toast("Ngày hoàn thành phải ở trong tháng hiện tại")
        //   return
        // }

        if (this.$refs.gridDanhSach.getSelectedRecords().length == 0) {
          this.$toast.error('Bạn chưa chọn phiếu!')
          return
        }

        let vstatus_ip = 0
        if ((await this.PHAILAM('CAPNHAT_IP_TINH')) && this.iptinh) {
          if (this.$refs.chkTuDong.checked) vstatus_ip = 1
          if (this.$refs.txtIPADSL.value || this.$refs.txtIPv4Wan.value || this.$refs.txtIPv6Lan.value) vstatus_ip = 2
          if (this.$refs.chkGiuIP.checked) vstatus_ip = 3
        }

        // if(moment(this.formatDate(this.$refs.dtpNgayBG.value), "DD/MM/YYY").diff(moment(), "days") < 0) {
        //   this.$toast.error("Ngày hoàn thành phải lớn hơn ngày hiện tại!")
        //   return
        // }

        let json = {
          P_PHIEU_ID: this.CURRENT_PHIEU_ID,
          P_HDTB_ID: this.CURRENT_HDTB_ID,
          P_NHANVIEN_ID: this.$root.token.getNhanVienID(),
          P_NGAY_BG: this.formatDate(this.$refs.dtpNgayBG.value),
          P_NGAY_GV: this.formatDate(this.$refs.dtpNgayGV.value),
          P_NGUOI_GV: this.$refs.cboNguoiGV.value,
          P_STATUS_IP: vstatus_ip,
          P_IPV4_LAN: this.$refs.txtIPADSL.value,
          P_IPV4_WAN: this.$refs.txtIPv4Wan.value,
          P_IPV6_LAN: this.$refs.txtIPv6Lan.value,
          P_EMAIL: this.$refs.txtEmailAdsl.value,
          P_MAC_ADDR: this.$refs.txtMAC.value,
          P_BRAS_ID: this.$refs.cboBrasADSL.value,
          P_DSLAM_ID: this.dslamtinh_id,
          // this.$refs.cboDSLAM.value,
          //'P_PORT_ID': this.form.port_id,
          //'P_VCI_VPI_ID':this.form.vci_vpi_id,
          P_PORT_ID: this.current_item.port_id ? this.current_item.port_id : 0,
          P_VCI_VPI_ID: this.current_item.vci_vpi_id ? this.current_item.vci_vpi_id : 0,
          P_MATKHAU: this.$refs.txtMatKhauADSL.value,
          P_MA_LT: this.$refs.txtMaLT.value,
          P_SERI_MD: this.$refs.txtSeriMD.value,
          P_SL_ID: this.$refs.txtSLID.value,
          P_LOAITBI_DC_ID: this.$refs.cboLoaiTB.value,
          P_GHICHU: this.$refs.txtGhiChu.value
        }

        json = JSON.stringify(json)

        let data = await this.axios.post('/web-thicong/thicong-internet/fn_frmhoancongadsl_capnhat', {
          js_data: json
        })
        if (data.data.data == 'OK') {
          this.$toast.success('Cập nhật thành công!')
          this.HienThiTTHopDongTB()
        } else this.$toast.error(data.data.data)
      } catch (err) {
        console.log(err)
        this.$toast.error(err.data ? err.data.message : 'Lỗi xảy ra ')
      }
    },
    HienThiGiaoDien(luong_id) {
      this.$refs.tsbtnDuAn.style.display = 'none'
      this.$refs.tsbtnHoanThanh.style.display = 'none'
      this.$refs.tsbtnKQ_DoKiem.style.display = 'none'
      this.$refs.txtPort.readOnly = true
      this.$refs.txtPortMDF.readOnly = true
      this.$refs.txtSlot.readOnly = true
      this.$refs.txtSystem.readOnly = true
      this.$refs.txtIpDsLam.readOnly = true
      this.$refs.txtRack.readOnly = true
      this.$refs.txtShelf.readOnly = true
      this.$refs.txtVCIADSL.readOnly = true
      this.$refs.txtVPIADSL.readOnly = true
      this.$refs.txtSelfid01.readOnly = true
      this.$refs.txtSelfid02.readOnly = true
      this.$refs.txtSelfid03.readOnly = true
      this.$refs.txtSelfid04.readOnly = true
      this.$refs.txtNasportID.readOnly = true
      this.$refs.tsbtnTraPhieu.style.display = 'none'

      this.$refs.tsbtnHoanCong.style.display = 'none'
      this.$refs.tsbtnHoanThienHS.style.display = 'none'
      this.$refs.tsbtnGiaoPhieu.style.display = 'none'
      this.$refs.tsbtnHoanThanh.style.display = 'none'
      this.$refs.tsbtnGiaoPhieu.style.display = 'none'
      this.$refs.tsbtnGiaoViec.style.display = 'none'
      this.$refs.btnChonPort.disabled = true
      this.$refs.btnChonPort_TuDong.disabled = true
      this.$refs.tsbtnKichHoat.style.display = 'none'
      this.$refs.tsbtnVatTu.style.display = 'none'
      this.$refs.chkGiuIP.checked = false
      this.$refs.chkTuDong.checked = false

      this.$refs.txtIPADSL.disabled = true
      this.$refs.txtIPv4Wan.disabled = true
      this.$refs.txtIPv6Lan.disabled = true
      this.$refs.tsbtnGiaoViec_KBB.style.display = 'none'
      this.$refs.tsbtnSua_KenhThu.style.display = 'none'
      this.$refs.tsbtnChuyenDoiTac.style.display = 'none'

      try {
        this.dtThaoTac = this.dsDanhMuc['quytrinh'].filter((item) => item.loaihd_id == this.loaihd_id && item.luong_id == this.luong_id && item.tthd_id == this.tthd_id)

        if (this.dtThaoTac.length > 0) {
          for (let i = 0; i < this.dtThaoTac.length; i++) {
            let dtControl = this.dsDanhMuc['quytrinh'].filter((item) => item.loaihd_id == this.loaihd_id && item.luong_id == this.luong_id && item.tthd_id == this.tthd_id && item.thaotac_id == this.dtThaoTac[i].thaotac_id)

            if (dtControl.length > 0) {
              for (let j = 0; j < dtControl.length; j++) {
                let control_name = dtControl[j]['control_name']
                let ctrl = this.$refs[control_name]
                if (ctrl) {
                  switch (ctrl.nodeName) {
                    case 'BUTTON':
                      if (this.dtThaoTac[i].enable.toString() == '1') ctrl.disabled = false
                      else ctrl.disabled = true
                      break
                    case 'INPUT':
                      if (this.dtThaoTac[i].enable.toString() == '1') {
                        ctrl.readOnly = false
                        ctrl.style.background = 'yellow'
                        ctrl.style.color = 'brow'
                      } else ctrl.readOnly = true
                      break
                    case 'A':
                      console.log(control_name, this.dtThaoTac[i].enable.toString())
                      if (this.dtThaoTac[i].enable.toString() == '1') {
                        ctrl.style.display = null
                      } else ctrl.style.display = 'none'
                      break
                    default:
                      break
                  }
                }
              }
            }
          }
        }
        console.log('1992 - this.cty_newline')
        console.log(this.cty_newline)
        if (this.cty_newline) {
          console.log('den day roi nhe')
          console.log(this.cty_newline, 'Phú mỹ hưng')
          // Nếu là đối tác HCM (Phú Mỹ Hưng)
          this.$refs.tsbtnGiaoViec.style.display = 'none'
          this.$refs.tsbtnHen.style.display = 'none'
          this.$refs.tsbtnGiaoPhieu_Ton.style.display = 'none'
          this.$refs.tsbtnVatTu.style.display = 'none'
          this.$refs.tsbtnGiaoPhieu_PH_V2.style.display = 'none'
          this.$refs.tsbtnChuyenTo.style.display = 'none'
          this.$refs.tsbtnDauNoiCap.style.display = 'none'
          this.$refs.tsbtnToaNha.style.display = 'none'
          this.$refs.tsbtnThongTinKT.style.display = 'none'
          this.$refs.tsbtnDuAn.style.display = 'none'
          this.$refs.tsbtnVatTu.style.display = 'none'
          this.$refs.tsbtnXemMatKhau.style.display = 'none'
          this.$refs.tsbtnChuyenDoiTac.style.display = 'none'
          this.$refs.tsbtnTraCuuOneMesh.style.display = 'none'
          this.$refs.tsbtnThongTinLH.style.display = 'none'
          // this.$refs.tsbtnShowBras.style.display = "none";
          this.$refs.tsbtnShowIP.style.display = 'none'
          this.$refs.tsbtnTestAllPort.style.display = 'none'
          this.$refs.tsbtnNVKT.style.display = 'none'
          this.$refs.tsbtnVatTuMoi.style.display = 'none'
          this.$refs.tsbtnChonIPLANWAN.style.display = 'none'
          this.$refs.tsbtnChuyenVMP.style.display = 'none'
          this.$refs.tsbtnDoKiem.style.display = 'none'
          this.$refs.tsbtnSua_KenhThu.style.display = 'none'
        }
      } catch (err) {
        console.log('2024 loi roi')
        console.log(err)
        this.$toast.error('Có lỗi : ' + err.message)
      }
    },
    tsbtnVatTuMoi_Click() {
      if (this.CURRENT_PHIEU_ID) {
        this.modelVatTu.phieu_id = this.current_record.phieu_id
        this.modelVatTu.loaihd_id = this.loaihd_id
        this.modelVatTu.hdtb_id = this.CURRENT_HDTB_ID
        this.modelVatTu.thuebao_id = this.thuebao_id
        this.modelVatTu.vdvvt_id = this.CURRENT_DICHVUVT_ID
        this.modelVatTu.loaitb_id = this.CURRENT_LOAITB_ID
        this.modelVatTu.vma_tbcur = this.CURRENT_MATB
        this.modelVatTu.baohong_id = 0
        this.$refs.popupVatTuTB.openDialog()
      } else {
        MessageBox.Show('Bạn chưa chọn phiếu!')
      }
    },
    tsbtnVatTu_Click() {
      if (this.CURRENT_PHIEU_ID) {
        this.modelVatTu.hdtb_id = this.CURRENT_HDTB_ID
        this.modelVatTu.phieu_id = this.current_record.phieu_id
        this.modelVatTu.loaihd_id = this.loaihd_id
        this.modelVatTu.thuebao_id = this.thuebao_id
        this.$refs.popupVatTuThueBao.showModal()
      } else {
        this.$toast.error('Bạn chưa chọn phiếu!')
      }
    },
    async frmHoanCongDMAdsl_Huyen_FormLoad() {
      //Hiện nút tra cứu thông số Bras, dành riêng cho theo yêu cầu của LDG
      //   tsbtnShowBras.Visible = DatabaseConstants.DB1.ToUpper() == "ADMIN_LDG";
      //   chkPhieuTon.Visible = tt_nd.tentat == "THA";
      //   this.tsbtnToaNha.style.display = this.thamso_md.IS_EXIST("HT_TOANHA");
      return new Promise(async (resolve, reject) => {
        try {
          let str = ''
          //region Show Tag
          let Tag = this.isPopup ? this.Tag : this.$route.query.tag
          try {
            if (Tag.toString() != '') {
              str = Tag.toString()
              let words = str.split(' ')
              if (!words || words.length == 1) {
                words = str.split('+')
              }
              if (words.length >= 1) this.tthd_id = Number(words[0])
              if (words.length >= 2) {
                this.dsloaihd_id = words[1]
                this.dsloaihd_id = this.dsloaihd_id.replaceAll(';', ',')
              }
              if (words.length >= 3) {
                this.dsdichvuvt_id = words[2]
                this.dsdichvuvt_id = this.dsdichvuvt_id.replaceAll(';', ',')
              }
              if (words.length >= 4) {
                this._flagTSKT = words[3]
                this._flagTSKT = this._flagTSKT.replaceAll(';', ',')
              }
              if (words.length >= 5) {
                console.log('new-life ok')
                // Áp dụng cho user của đối tác
                if (words[4] != '') this.cty_newline = true
              }
              if (this.dsdichvuvt_id == '') {
                this.dsdichvuvt_id = '4' //TuanNA: Thêm code này trong trường hợp Lam ko truyền dịch vụ vt vào chức năng(Tag)
              }
            } else {
              this.tthd_id = TrangThaiHD.DANG_THI_CONG
              this.dsloaihd_id = '1'
              this.dsdichvuvt_id = '4'
            }
          } catch (err) {
            console.log(err)
            this.tthd_id = TrangThaiHD.DANG_THI_CONG
            this.dsloaihd_id = '1'
            this.dsdichvuvt_id = '4'
          }

          this.$refs.txtMaTB.focus()
          //endregion

          this.wbrTSKT = ''
          this.loading(true)
          //region Lấy các tham số mặc đinh
          let rs_ts = await this.axios.post('web-hopdong/thanhly/sp_lay_ds_thamso_md', { kieu_id: 0 })
          this.ClearForm()
          this.dsThamSo = rs_ts.data ? rs_ts.data.data : []
          if (this.dsThamSo && this.dsThamSo.length > 0) {
            this.$refs.tsbtnToaNha.style.display = this.dsThamSo.filter((item) => item.ma_ts == 'HT_TOANHA').length > 0 ? null : 'none'
            let row
            let str_vtemp = 'KHONGHOANCONG_CHUA_THANHTOAN'
            let det = this.dsThamSo.filter((item) => item.ma_ts == str_vtemp)
            if (det.length > 0) {
              row = det[0]
              if (row['ten_ts'].toString() == '1') this.khong_hoancong_chua_thanhtoan = 1
            }
            str_vtemp = 'CAPNHAT_CTS_MODEM'
            det = this.dsThamSo.filter((item) => item.ma_ts == str_vtemp)
            if (det.length > 0) {
              row = det[0]
              if (row['ten_ts'].toString() == '1') this.UPDATE_CTS = true
            }

            str_vtemp = 'NVKT_BATBUOC_NHAP_LOAINHA'
            det = this.dsThamSo.filter((item) => item.ma_ts == str_vtemp)
            if (det.length > 0) {
              row = det[0]
              if (row['ten_ts'].toString() == '1') this.NVKT_BATBUOC_NHAP_LOAINHA = true
            }

            //BOQUA_KT_SUYHAO_UP
            str_vtemp = 'BOQUA_KT_SUYHAO_UP'
            det = det = this.dsThamSo.filter((item) => item.ma_ts == str_vtemp)
            if (det.length > 0) {
              row = det[0]
              if (row['ten_ts'].toString() == '1') this.BOQUA_KT_SUYHAO_UP = true
            }
          }
          //endregion

          //region Tham số mặc định bổ sung sau
          // ds = checkdata.Lay_ds_thamso_tinh(tt_nd.tinh_id);
          // vuser_alt = ds[0].Rows[0]["user_alt"].toString();// checkdata.MAP_ID("user_alt", DatabaseConstants.DB2 + ".tinh", "where tinh_id = " + tt_nd.tinh_id);
          // vpass_alt = ds[0].Rows[0]["pass_alt"].toString();
          // vuser_xtest = ds[0].Rows[0]["user_xtest"].toString();
          // vpass_xtest = ds[0].Rows[0]["pass_xtest"].toString();
          //endregion

          //region Lấy dữ liệu danh mục
          let rs_danhmuc = await this.axios.post('/web-thicong/thicong-internet/lay_danhmuc_thicong_internet', {
            vchuoi: 'KENHTHU|KIEU_HD|LOAI_HD|LOAI_CAP|NHANVIEN-donvi_id = ' + this.$root.token.getDonViID() + '|TOCDO_ADSL|TRANGBI|BRAS|MUCCUOC|TBI_DAUCUOI|QUYTRINH'
          })

          this.dsDanhMuc = rs_danhmuc.data.data
          this.$refs.cboKenhThu.HT_COMBOBOX(this.dsDanhMuc['kenhthu'], 'kenhthu', 'kenhthu_id')
          this.$refs.cboKieu_HD.HT_COMBOBOX(this.dsDanhMuc['kieu_hd'], 'kieu_hd', 'kieuhd_id')
          this.$refs.cboLoaiHD.HT_COMBOBOX(this.dsDanhMuc['loai_hd'], 'ten_loaihd', 'loaihd_id')
          this.$refs.cboLoaiCap.HT_COMBOBOX(this.dsDanhMuc['loai_cap'], 'loaicap', 'loaicap_id')
          this.$refs.cboNguoiGV.HT_COMBOBOX(this.dsDanhMuc['nhanvien'], 'ten_nv', 'nhanvien_id')

          this.$refs.cboNguoiGV.value = this.$root.token.getNhanVienID()
          let ngay_hientai = moment().format('DD/MM/YYYY HH:mm:ss')
          this.$refs.dtpNgayBG.value = ngay_hientai
          this.$refs.dtpNgayGV.value = ngay_hientai
          this.$refs.dtpNgayGV.disabled = true

          this.$refs.cboTrangBiADSL.HT_COMBOBOX(this.dsDanhMuc['trangbi'], 'tentrangbi', 'trangbi_id')
          this.$refs.cboBrasADSL.HT_COMBOBOX(this.dsDanhMuc['bras'], 'tenbras', 'bras_id')
          this.$refs.cboMucCuoc.HT_COMBOBOX(this.dsDanhMuc['muccuoc'], 'tenmuc', 'muccuoc_id')
          this.$refs.cboLoaiTB.HT_COMBOBOX(this.dsDanhMuc['tbi_daucuoi'], 'ten_tbi', 'thietbidc_id')
          //endregion

          // Vào frm bảo người dùng ấn nút cho đỡ lag
          //ZEm theo anh Hiếu bảo
          //  HienThiDanhSachHDTB();

          if (this.inDex_load != 0) {
            //this.$refs.gridDanhSach.FocusedRowHandle = inDex_load;
          }

          if (this.tthd_id == TrangThaiHD.DA_THI_CONG_XONG) {
            this.$refs.tsbtnHoanThienHS.style.display = null
          }
          //region AnhNT: 24 / 09 / 2021 lấy danh sách các chủ quản của nd quản lý
          this.DS_CHUQUAN_THEO_QUYEN = await this.$root.token.getDsQuyenChuquan()
          //endregion
          this.kt_load = true
        } catch (err) {
          console.log(err)
        }
        resolve(true)
      })
    },
    ClearForm() {
      this._ketcuoi_id = 0
      this._vitri = 0
      this.$refs.txtThongTinTra.value = ''
      this.$refs.txtLyDoTra.value = ''
      this.$refs.txtOnuPass.value = ''
      this.$refs.txtSoDTLH.value = ''
      this.$refs.txtMaTB.value = ''
      this.$refs.lblLienHe.innerHTML = ''
      this.$refs.chkNgayGV.checked = false
      this.$refs.chkNgayBG.checked = false
      this.chkNgayGV_CheckedChanged()
      this.chkNgayBG_CheckedChanged()
      this.$refs.txtNhanVien.value = ''
      this.$refs.txtNV_TiepThi.value = ''
      //cboKieuHD.value = KIEU_HD.TAI_NHA;
      this.$refs.txtLoaiKH.value = ''
      this.$refs.txtMaLT.value = ''
      this.str_ip = ''
      this.str_ipv4_wan = ''
      this.str_ipv6_lan = ''
      this.matb_visa = ''
      this.$refs.txtSoMayTN.value = ''
      this.$refs.txtTBLapKem.value = ''
      this.iptinh = false
      this.vci_vpi_id = 0
      this.vci_vpi_id_cu = 0
      this.thuebao_id = 0
      this.$refs.txtMaGD.value = ''
      this.$refs.txtSoPL.value = ''
      //txtMaTB.value = "";
      this.$refs.txtTenTB.value = ''
      this.$refs.txtkhuvuc.value = ''
      this.$refs.txtDiaChiLD.value = ''
      this.$refs.txtDiaChiLD_Cu.value = ''

      this.$refs.txtTuCapGoc.value = ''
      this.$refs.txtDoiCapGoc.value = ''
      this.$refs.txtCapGoc.value = ''
      this.$refs.txtHopCapNgon.value = ''
      this.$refs.txtTuCapNgon.value = ''
      this.$refs.txtDoiCapNgon.value = ''
      this.$refs.txtCapNgon.value = ''
      this.$refs.txtTuyenCap.value = ''
      this.$refs.txtLienTu.value = ''
      this.$refs.txtSoMetDay.value = '0'

      this.$refs.txtPort.value = ''
      this.$refs.txtPortMDF.value = ''
      this.dichvuvt_id = 0
      this.$refs.txtMatKhauADSL.value = ''
      this.$refs.txtEmailAdsl.value = ''
      this.$refs.txtSlot.value = ''
      this.$refs.txtVCIADSL.value = ''
      this.$refs.txtVPIADSL.value = ''
      this.$refs.txtRack.value = ''
      this.$refs.txtShelf.value = ''
      this.$refs.txtPortMDF.value = ''
      this.$refs.txtSelfid01.value = ''
      this.$refs.txtSelfid02.value = ''
      this.$refs.txtSelfid03.value = ''
      this.$refs.txtSelfid04.value = ''
      this.$refs.txtNasportID.value = ''

      this.$refs.txtGhiChu.value = ''
      this.$refs.txtThongTinTra.value = ''
      this.$refs.txtSoMayTN.value = ''
      this.$refs.txtIPADSL.value = ''
      this.$refs.txtIPv4Wan.value = ''
      this.$refs.txtIPv6Lan.value = ''
      this.$refs.btnIPTinh.disabled = true
      this.$refs.btnIPTinhV4Wan.disabled = true
      this.$refs.btnIPTinhV6Lan.disabled = true

      this.$refs.txtIpDsLam.value = ''
      this.system = ''

      // hoangpkn : 10/08/2017
      //chkNgayGV.checked = false;
      //chkNgayBG.checked = false;
      this.$refs.chkNgayGV.checked = true
      this.$refs.chkNgayBG.checked = true
      this.chkNgayGV_CheckedChanged()
      this.chkNgayBG_CheckedChanged()
      let ngay_hientai = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      this.$refs.dtpNgayBG.value = ngay_hientai
      this.$refs.dtpNgayGV.value = ngay_hientai
      // end hoang

      this.$refs.gridDanhSach.dataSource = []
      setTimeout(() => {
        this.$refs.gridGiaoviec.dataSource = []
      }, 100)
      this.port_id = 0
      this.port_id_cu = 0
      this.$refs.txtTenKieuLD.value = ''
      this.$refs.txtSystem.value = ''
      this.$refs.tsbtnHoanThanh.classList.add('disabled')
      this.$refs.tsbtnHoanCong.classList.add('disabled')
      this.$refs.tsbtnKichHoat.classList.add('disabled')
      this.$refs.txtSoMetDay.value = '0'
      this.in_tocdo_id = -1
      this.in_tocdothuc_id = -1
      this.$refs.txtTTCuoc.value = ''
      this.$refs.txtTramTB.value = ''
      this.$refs.txtTongTien.value = '0' //nhapt 06072015
      this.$refs.txtSLID.value = ''
      this.$refs.txtSeriMD.value = ''
      this.seri_md_cur = ''

      this.wbrTSKT = ''
      this.$refs.cboTocDoThucADSL.dataSource = null
      this.$refs.cboGoiCuocAdsl.dataSource = null
      this.$refs.txtSeriMD.value = ''
      this.$refs.txtSLID.value = ''
      this.$refs.chkSERI_MD.checked = false
      this.$refs.chkSLID.checked = false
      this.chkSERI_MD_CheckedChanged()
      this.chkSLID_CheckedChanged()

      //dsDK_DVK.Clear();
    },

    //region HienThiDanhSachHDTB
    async HienThiDanhSachHDTB() {
      try {
        this.loading(true)
        this.loc_nv = 0
        if (await this.PHAILAM('LOCTHEO_NV')) this.loc_nv = 1

        let vloai_id = 0 // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
        if (this.$refs.rdoPhieuMoi.checked) vloai_id = 0
        else vloai_id = 1
        let rs = null
        if (this.DS_CHUQUAN_THEO_QUYEN == null || this.DS_CHUQUAN_THEO_QUYEN.length == 0) {
          rs = await this.axios.post('/web-hopdong/tiepnhanhopdong/ds_phieu_hc', {
            caidat_dv: 0,
            huonggiao_id: this.huonggiao_id,
            kieu_id: 1,
            loai_id: vloai_id,
            loaihd_id: this.$refs.cboLoaiHD.value,
            loc_nv: this.loc_nv,
            ma_gd: '0',
            nhanvien_id: this.$root.token.getNhanVienID(),
            phieu_id: 0,
            tthd_id: this.tthd_id
          })
        } else {
          rs = await this.axios.post('/web-hopdong/tiepnhanhopdong/lay_ds_phieu_hc_119_huyen_chuquan', {
            vcaidat_dv: 0,
            vhuonggiao_id: this.huonggiao_id,
            vkieu_id: 1,
            vloai_id: vloai_id,
            vloaihd_id: this.$refs.cboLoaiHD.value,
            vloc_nv: this.loc_nv,
            vma_gd: '0',
            vnhanvien_id: this.$root.token.getNhanVienID(),
            vphieu_id: 0,
            vtthd_id: this.tthd_id,
            vds_chuquan_theo_quyen: `(${this.DS_CHUQUAN_THEO_QUYEN})`
          })
        }
        this.dsIn = rs.data ? rs.data.data : []
        this.ClearForm()
        let kt = false
        if (this.dsIn && this.dsIn.length > 0) {
          if (this.matb_link) {
            this.dsIn = this.dsIn.filter((item) => item.ma_tb == this.matb_link)
            this.matb_link = ''
          }
          this.$refs.gridDanhSach.dataSource = this.dsIn
          this.dtList = this.dsIn[0]
          //this.Loc_PhieuTon(); làm sau
          this.HienThiAnhTrangThai()
        } else {
          this.ClearForm()
        }

        this.loading(false)
      } catch (err) {
        console.log(err)
        this.$toast.error('' + err.message)
      }
    },
    //endregion
    dtgDanhSach_SelectionChanged(item) {
      try {
        if (this.$refs.gridDanhSach.dataSource.length == 0) {
          this.current_item = {}
          this.current_record = {}
          this.$refs.cboTocDoThucADSL.dataSource = []
          this.$refs.cboGoiCuocAdsl.dataSource = []
          return
        }

        this.$refs.tsbtnChapNhan.classList.add('disabled')
        this.$refs.tsbtnTraPhieu.classList.add('disabled')

        if (this.$refs.gridDanhSach.dataSource.length > 0) {
          console.log(item.data)
          this.current_item = item.data
          this.current_record = item.data
          this.HienThiTTHopDongTB()
          this.$refs.chkLayTSo.checked = false
        }
        if (this.$refs.gridDanhSach.dataSource.length == 0) this.ClearForm()
      } catch (err) {
        console.log(err)
        this.$toast.error('' + err.message)
      }
    },
    HienThiAnhTrangThai() {},
    numberWithCommas(n) {
      var parts = n.toString().split('.')
      return parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',') + (parts[1] ? '.' + parts[1] : '')
    },
    async HienThiTTHopDongTB() {
      try {
        this.loading(true)
        this.seri_md_cur = ''
        if (this.CURRENT_PHIEU_ID > 0) {
          this.in_tocdothuc_id = -1

          let rs = await this.axios.post('web-thicong/thicong-internet/fn_lay_thongtin_hdtb_thicong_adsl', {
            hdtb_id: this.CURRENT_HDTB_ID
          })

          let ds = []

          let ds_rs = JSON.parse(rs.data.data)
          if (!ds_rs.RESULT && !ds_rs.RESULT.HDTB) return
          let item = ds_rs.RESULT.HDTB[0]
          let refm = {}
          for (let key in item) {
            refm[key.toLowerCase()] = item[key]
          }
          this.current_info = refm
          this.current_item = refm

          ds.push(refm)

          this.$refs.tsbtnChapNhan.classList.remove('disabled')
          this.$refs.tsbtnTraPhieu.classList.remove('disabled')
          this.tramtb_id = 0

          //dsDK_DVK = new DANGKY_DVK_DATA();
          //cmUlt.CopyDataSet(new TCHopDongFacade().LAY_DS_DANGKY_DVK(hdtb_id, 1), dsDK_DVK);

          //Lấy thông tin liên hệ lắp đặt thuê bao
          this.$refs.lblLienHe.innerHTML = 'Liên hệ : ' + ds[0].thongtin_lh_lapdat
          if (this.$refs.lblLienHe.innerHTML == '') this.$refs.lblLienHe.innerHTML = 'Liên hệ : '
          //

          //nhapt thêm 06072015
          if (ds[0].tongtien) this.$refs.txtTongTien.value = this.numberWithCommas(ds[0].tongtien)
          else this.$refs.txtTongTien.value = '0'

          this.$refs.txtNhanVien.value = ds[0].ten_nv_laphd

          if (ds[0].ten_ctv) this.$refs.txtNV_TiepThi.value = ds[0].ten_ctv
          else this.$refs.txtNV_TiepThi.value = ''

          if (this.$refs.cboLoaiHD.value != LoaiHopDong.DAT_MOI)
            //Lấy thuê bao ID đối với loại hợp đồng dịch chuyển
            this.thuebao_id = ds[0]['thuebao_id']

          this.$refs.cboKenhThu.value = ds[0]['kenhthu_id']

          if (ds[0]['kieuhd_id']) this.$refs.cboKieu_HD.value = ds[0]['kieuhd_id']
          else this.$refs.cboKieu_HD.value = KIEU_HD.TAI_GD

          if (ds[0]['giohen_tu']) {
            this.$refs.chkHen.checked = true
          } else this.$refs.chkHen.checked = false
          this.$refs.txtThongTinTra.value = ds[0]['nd_tra_con']
          if (ds[0]['lydotra_con_id']) {
            this.$refs.txtLyDoTra.value = ds[0].lydotra_tc
          } else this.$refs.txtLyDoTra.value = ''

          if (ds[0]['congnghe_id']) this.congnghe_id = ds[0]['congnghe_id']
          else this.congnghe_id = 0

          this.loaihd_id = ds[0]['loaihd_id']
          this.dichvuvt_id = ds[0]['dichvuvt_id']
          this.loaitb_id = ds[0]['loaitb_id']
          this.ltb_id = ds[0]['loaitb_id']
          this.$refs.txtSoDTLH.value = ds[0]['so_dt_lh']

          // var t = dsDanhMuc["TOCDO_ADSL"].Select("TOCDO_ID=" + ds[0]["tocdo_id"] + "");
          // if (t.Length > 0)
          // {
          //     bangts.HT_COMBOBOX(cboGoiCuocAdsl, t.CopyToDataTable(), "thuonghieu", "tocdo_id");
          //     cboGoiCuocAdsl.value = ds[0]["tocdo_id"]
          // } làm áu

          if (ds[0]['sltv_htvc'] == '') this.$refs.lblSL_Tivi.style.display = 'none'
          else {
            this.$refs.lblSL_Tivi.style.display = null
            this.$refs.lblSL_Tivi.innerHTML = 'Số lượng tivi : ' + (ds[0]['sltv_htvc'] ? ds[0]['sltv_htvc'] : '0')
          }

          this.in_tocdo_id = ds[0]['tocdo_id']
          if (ds[0]['tocdothuc_id']) {
            var t_thuc = this.dsDanhMuc['tocdo_adsl'].filter((item) => {
              item.TOCDO_ID = ds[0]['tocdothuc_id']
            })
            if (t_thuc.length > 0) {
              this.$refs.cboTocDoThucADSL.HT_COMBOBOX(t_thuc, 'thuonghieu', 'tocdo_id')
              this.$refs.cboTocDoThucADSL.value = ds[0]['tocdothuc_id']
              this.in_tocdothuc_id = ds[0]['tocdothuc_id']
            }
          } else {
            this.$refs.cboTocDoThucADSL.value = -1
            this.in_tocdothuc_id = -1
          }

          this.str_ip = ds[0]['ip']
          this.str_ipv4_wan = ds[0]['ipv4_wan']
          this.str_ipv6_lan = ds[0]['ipv6_lan']
          this.$refs.txtMaLT.value = ds[0]['ma_lt']
          this.$refs.txtNoiDungTH.value = ds[0]['nd_thuchien']
          if (ds[0]['tocdo_id']) {
            var t_adsl = this.dsDanhMuc['tocdo_adsl'].filter((item) => item.TOCDO_ID == ds[0]['tocdo_id'])
            if (t_adsl.length > 0) {
              this.$refs.cboGoiCuocAdsl.HT_COMBOBOX(t_adsl, 'thuonghieu', 'tocdo_id')
              this.$refs.cboGoiCuocAdsl.value = ds[0]['tocdo_id']
            }
          } else {
            this.$refs.cboTocDoThucADSL.value = -1
            this.in_tocdothuc_id = -1
          }
          if (ds[0]['tocdothuc_id']) {
            var t_thuc = this.dsDanhMuc['tocdo_adsl'].filter((item) => item.TOCDO_ID == ds[0]['tocdothuc_id'])
            if (t_thuc.length > 0) {
              this.$refs.cboTocDoThucADSL.HT_COMBOBOX(t_thuc, 'thuonghieu', 'tocdo_id')
              this.$refs.cboTocDoThucADSL.value = ds[0]['tocdothuc_id']
              this.in_tocdothuc_id = ds[0]['tocdothuc_id']
            }
          } else {
            this.$refs.cboTocDoThucADSL.value = -1
            this.in_tocdothuc_id = -1
          }
          // string str = "select * from " + DatabaseConstants.DB2 + ".hdtb_dv where hdtb_id = " + CURRENT_HDTB_ID + " and loaidv_id = " + LOAI_DV.TRAM_VT + " and kieudv_id = 2";
          // DataTable dtTemp = bangts.GetDataSql(str);
          // if (dtTemp.Rows.Count > 0)
          //     donvi_id = dtTemp.Rows[0]["donvi_id"]

          if (ds[0]['tramtb_id']) {
            this.tramtb_id = ds[0]['tramtb_id']
            this.$refs.txtTramTB.value = ds[0].tram_tbi
          } else {
            this.tramtb_id = 0
            this.$refs.txtTramTB.value = ''
          }

          let loaiKH = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loai_kh', { param: ds[0].loaikh_id, type: 1 })

          this.$refs.txtLoaiKH.value = loaiKH.data ? loaiKH.data.data : ''
          if (this.$refs.txtLoaiKH.value == '-1') this.$refs.txtLoaiKH.value = ''

          this.kt_status = ds[0]['status']

          this.$refs.chkVMP.checked = false
          this.$refs.tsbtnChuyenVMP.innerHTML = 'Chuyển VMP'
          if (ds[0]['vmp']) {
            console.log('checked VMP')
            this.$refs.chkVMP.checked = true
            this.$refs.tsbtnChuyenVMP.innerHTML = 'Chuyển MYTV'
          }

          this.$refs.tsbtnHuyKH.style.display = 'none'
          if ((await this.PHAILAM('TSBTNHUYKH')) && (this.loaitb_id == LoaiHinhTB.WIFI_MESH || this.loaitb_id == LoaiHinhTB.INTERNET_ADSL || this.loaitb_id == LoaiHinhTB.INTERNET_FTTH || this.loaitb_id == LoaiHinhTB.WIFI_FIBER || this.loaitb_id == LoaiHinhTB.INTERNET_MYTV || this.loaitb_id == LoaiHinhTB.INTERNET_MYTV_B2B)) {
            this.$refs.tsbtnHuyKH.style.display = null
          }

          //if (dichvuvt_id == DichVuVienThong.MEGA_EYES && ds[0].Columns.Contains("mac_addr") && ds[0]["mac_addr"] != DBNull.value)
          //{
          //    _list_mac_addr = ds[0]["mac_addr"]
          //}
          //AnhDt
          if (this.dichvuvt_id == DichVuVienThong.MEGA_EYES) {
            this.$refs.txtEmailAdsl.value = ds[0].email
          }

          //nhapt thêm : 17/03/2017
          /// Thực hiện ẩn/hiện nút thay đổi thông tin liên hệ
          /// trên chức năng của trạm; để trạm cập nhật lại thông tin liên hệ sai
          this.$refs.tsbtnThongTinLH.style.display = 'none'
          if (await this.PHAILAM('TSBTNTHONGTINLH')) {
            this.$refs.tsbtnThongTinLH.style.display = null
          }
          if (ds[0]['status'] == TRANGTHAI_DONGBO.DONGBO_CM) this.$refs.tsbtnHuyKH.classList.remove('disabled')
          else this.$refs.tsbtnHuyKH.classList.add('disabled')
          let vstatus_ip = ''
          vstatus_ip = ds[0]['status_ip']
          this.$refs.chkTuDong.disabled = false
          if (vstatus_ip == '0' || vstatus_ip == '') {
            this.$refs.chkTuDong.checked = false
            this.$refs.chkGiuIP.checked = false
          } else if (vstatus_ip == '1') {
            this.$refs.chkTuDong.checked = true
            this.$refs.chkGiuIP.checked = false
            this.$refs.btnIPTinh.disabled = true
            this.$refs.btnIPTinhV4Wan.disabled = true
            this.$refs.btnIPTinhV6Lan.disabled = true
          } else if (vstatus_ip == '2') {
            this.$refs.chkTuDong.checked = false
            this.$refs.chkGiuIP.checked = false
          } else if (vstatus_ip == '3') {
            this.$refs.chkTuDong.checked = false
            this.$refs.chkGiuIP.checked = true
            this.$refs.btnIPTinh.disabled = true
            this.$refs.btnIPTinhV4Wan.disabled = true
            this.$refs.btnIPTinhV6Lan.disabled = true
          }
          this.chkTuDong_CheckedChanged()

          // hoangpkn : 10/08/2017 sửa theo yc DNI
          this.$refs.dtpNgayBG.disabled = false
          this.$refs.chkNgayBG.checked = true
          if (ds[0]['ngay_ht_gp']) this.$refs.dtpNgayBG.value = this.formatDate(ds[0]['ngay_ht_gp'])
          else this.$refs.dtpNgayBG.value = this.ngay_cn

          console.log(this.formatDate(ds[0]['ngay_ht_gp']), 'this.formatDate(ds[0]')
          // end hoang
          try {
            //let kt_bc = new CabmanFacadeV2().GET_VALUE_FUNCTION(DatabaseConstants.DB2 + ".bancheo_kiemtra_gan_macdinh_ngayht", "vhdtb_id", CURRENT_HDTB_ID).Data;
            let kt_bc = 0
            if (kt_bc != '0') {
              this.$refs.dtpNgayBG.value = kt_bc
            }
          } catch (err) {
            if (ds[0]['ngay_ht_gp']) this.$refs.dtpNgayBG.value = ds[0]['ngay_ht_gp']
            else this.$refs.dtpNgayBG.value = tt_nd.ngay_cn.AddSeconds(30)
          }
          this.dslamtinh_id = -1
          if ((await this.PHAILAM('CHECK_OMC')) && (await this.PHAILAM('AON_CHI_CHON_DSLAM_TINH'))) {
            console.log(ds[0]['dslam_tinh_id'], 'dslam_tinh_id là')
            console.log(ds[0])
            this.dslamtinh_id = ds[0]['dslam_tinh_id']
          }
          //region Phải làm
          // Kiểm tra xem tốc độ có phải sử dụng IP tĩnh hay không.
          //region CDLH
          if (this.$refs.cboLoaiHD.value == LoaiHopDong.CHUYENDOI_LH) {
            let rs_iptinh = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tocdo_adsl', { param: this.in_tocdo_id, type: 1 })
            let viptinh = rs_iptinh.data.data

            if (viptinh == '1') {
              this.$refs.chkGiuIP.disabled = false
              //if (await this.PHAILAM("KICHHOAT_TD"))
              if (await this.PHAILAM('CAPNHAT_IP_TINH')) {
                //btnIPTinh.disabled = false
                //chkGiuIP.disabled = false
                //chkTuDong.disabled = false
                //iptinh = true;
              } else {
                this.$refs.btnIPTinh.disabled = true
                this.$refs.btnIPTinhV4Wan.disabled = true
                this.$refs.btnIPTinhV6Lan.disabled = true
                this.$refs.chkGiuIP.disabled = true
                this.$refs.chkTuDong.disabled = true
              }
              this.iptinh = true
            } else {
              this.$refs.btnIPTinh.disabled = true
              this.$refs.btnIPTinhV4Wan.disabled = true
              this.$refs.btnIPTinhV6Lan.disabled = true
              this.$refs.chkGiuIP.disabled = true
              this.$refs.chkTuDong.disabled = true
              this.iptinh = false
            }

            //Nếu phải giao phiếu
            if (await this.PHAILAM('GIAOPHIEU')) {
              if (ds[0]['ngay_ht_gp']) {
                if (await this.PHAILAM('KICHHOAT_TD')) {
                  if (this.kt_status == TRANGTHAI_DONGBO.DONGBO_CM) this.$refs.tsbtnGiaoPhieu.classList.remove('disabled')
                  else this.$refs.tsbtnGiaoPhieu.classList.add('disabled')
                } else this.$refs.tsbtnGiaoPhieu.classList.remove('disabled')
              } else this.$refs.tsbtnGiaoPhieu.classList.add('disabled')
            }

            //Nếu phải hoàn công
            if (await this.PHAILAM('HOANCONG')) {
              if ((ds[0]['ngay_ht_gp'] && this.kt_status == TRANGTHAI_DONGBO.DONGBO_CM) || this.loaitb_id == 210) this.$refs.tsbtnHoanCong.classList.remove('disabled')
              else if (ds[0]['ngay_ht_gp'] && this.kt_status != TRANGTHAI_DONGBO.DONGBO_CM) {
                if (this.dslamtinh_id == 0 || this.dslamtinh_id == -1) {
                  this.$refs.tsbtnHoanCong.classList.add('disabled')
                } else this.$refs.tsbtnHoanCong.classList.remove('disabled')
              } else this.$refs.tsbtnHoanCong.classList.add('disabled')
            }
          }
          //endregion

          //region Đặt mới + Khôi phục
          else if (this.$refs.cboLoaiHD.value == LoaiHopDong.DAT_MOI || this.$refs.cboLoaiHD.value == LoaiHopDong.KHOIPHUC_SD) {
            this.$refs.chkGiuIP.disabled = true
            let rs_iptinh = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tocdo_adsl', { param: this.in_tocdo_id, type: 6 })

            let viptinh = rs_iptinh.data.data
            if (viptinh == '1') {
              // hoangpkn : 16/05/2016
              // Thêm kiểm tra nếu đã được cấp port -> có bras -> mới cho hiển thị check port tự động hoặc chọn port
              if (ds[0]['port_id']) {
                if (await this.PHAILAM('CAPNHAT_IP_TINH')) {
                  if (vstatus_ip == '0' || vstatus_ip == '') {
                    this.$refs.btnIPTinh.disabled = false
                    this.$refs.btnIPTinhV4Wan.disabled = false
                    this.$refs.btnIPTinhV6Lan.disabled = false
                  }
                } else {
                  this.$refs.btnIPTinh.disabled = true
                  this.$refs.btnIPTinhV4Wan.disabled = true
                  this.$refs.btnIPTinhV6Lan.disabled = true
                  this.$refs.chkTuDong.disabled = true
                }
                this.iptinh = true
              } else {
                this.$refs.btnIPTinh.disabled = true
                this.$refs.btnIPTinhV4Wan.disabled = true
                this.$refs.btnIPTinhV6Lan.disabled = true
                this.$refs.chkTuDong.disabled = true
              }
            } else {
              this.$refs.btnIPTinh.disabled = true
              this.$refs.btnIPTinhV4Wan.disabled = true
              this.$refs.btnIPTinhV6Lan.disabled = true
              this.$refs.chkTuDong.disabled = true
              this.iptinh = false
            }

            //Nếu phải giao phiếu
            if (await this.PHAILAM('GIAOPHIEU')) {
              if (ds[0]['ngay_ht_gp']) {
                if (await this.PHAILAM('KICHHOAT_TD')) {
                  if (this.kt_status == TRANGTHAI_DONGBO.DONGBO_CM) this.$refs.tsbtnGiaoPhieu.classList.remove('disabled')
                  else this.$refs.tsbtnGiaoPhieu.classList.add('disabled')
                } else this.$refs.tsbtnGiaoPhieu.classList.remove('disabled')
              } else this.$refs.tsbtnGiaoPhieu.classList.add('disabled')
            }

            //Nếu phải hoàn công
            if (await this.PHAILAM('HOANCONG')) {
              var trangthai_db = '0'
              if (this.dichvuvt_id == DichVuVienThong.MEGA_EYES) {
                trangthai_db = TRANGTHAI_DONGBO.DONGBO_CM
              } else if (
                this.loaitb_id == LoaiHinhTB.IOFFICE ||
                this.loaitb_id == LoaiHinhTB.BRANDNAME ||
                this.loaitb_id == LoaiHinhTB.CA ||
                this.loaitb_id == LoaiHinhTB.CA_IVAN ||
                this.loaitb_id == LoaiHinhTB.IVAN ||
                this.loaitb_id == LoaiHinhTB.HTVC_FIBERVNN_ANALOG ||
                this.loaitb_id == LoaiHinhTB.HTVC_FIBERVNN_DIGITAL ||
                this.loaitb_id == LoaiHinhTB.MAIL_SMD ||
                this.loaitb_id == LoaiHinhTB.Web_Hosting ||
                this.loaitb_id == LoaiHinhTB.VNPT_HIS ||
                this.loaitb_id == LoaiHinhTB.BRCD_FBDT ||
                this.loaitb_id == LoaiHinhTB.BRCD_THDT
              ) {
                trangthai_db = '0'
              } else {
                trangthai_db = TRANGTHAI_DONGBO.DONGBO_CM
              }

              console.log(trangthai_db, 'trangthai_db', TRANGTHAI_DONGBO.DONGBO_CM)
              console.log('adkashdkjashdkahskjd', this.kt_status, trangthai_db, ds[0]['ngay_ht_gp'])
              if ((ds[0]['ngay_ht_gp'] && this.kt_status == trangthai_db) || this.loaitb_id == 210) {
                console.log('Enable nút hoàn công')
                this.$refs.tsbtnHoanCong.classList.remove('disabled')
              } else if (ds[0]['ngay_ht_gp'] && this.kt_status != trangthai_db) {
                if (this.dslamtinh_id == 0 || this.dslamtinh_id == -1) {
                  this.$refs.tsbtnHoanCong.classList.add('disabled')
                } else this.$refs.tsbtnHoanCong.classList.remove('disabled')
              } else {
                this.$refs.tsbtnHoanCong.classList.add('disabled')
              }
            }
            if (await this.PHAILAM('HOANTHIENHOSO')) {
              // if (this.dtList["ngay_ht_gp"])
              this.$refs.tsbtnHoanThienHS.classList.remove('disabled')
              // else this.$refs.tsbtnHoanThienHS.classList.add("disabled");
            }
          }
          //endregion

          //region Di Chuyển
          else if (this.$refs.cboLoaiHD.value == LoaiHopDong.DI_CHUYEN) {
            //Nếu phải giao phiếu
            if (await this.PHAILAM('GIAOPHIEU')) {
              if (ds[0]['ngay_ht_gp']) {
                if (await this.PHAILAM('KICHHOAT_TD')) {
                  if (this.kt_status == TRANGTHAI_DONGBO.DONGBO_CM) this.$refs.tsbtnGiaoPhieu.classList.remove('disabled')
                  else this.$refs.tsbtnGiaoPhieu.classList.add('disabled')
                } else this.$refs.tsbtnGiaoPhieu.classList.remove('disabled')
              } else {
                this.$refs.tsbtnGiaoPhieu.classList.add('disabled')
              }
            }

            //Nếu phải hoàn công
            if (await this.PHAILAM('HOANCONG')) {
              let trangthai_db = ''
              if (this.dichvuvt_id == DichVuVienThong.MEGA_EYES) trangthai_db = TRANGTHAI_DONGBO.DONGBO_CM
              else if (this.loaitb_id == LoaiHinhTB.IOFFICE || this.loaitb_id == LoaiHinhTB.MEGA_CONFERENCE || this.loaitb_id == LoaiHinhTB.HTVC_FIBERVNN_ANALOG || this.loaitb_id == LoaiHinhTB.HTVC_FIBERVNN_DIGITAL || this.dichvuvt_id == DichVuVienThong.BRCD_DOITAC) trangthai_db = '0'
              else trangthai_db = TRANGTHAI_DONGBO.DONGBO_CM

              if ((ds[0]['ngay_ht_gp'] && this.kt_status == trangthai_db) || this.loaitb_id == 210) this.$refs.tsbtnHoanCong.classList.remove('disabled')
              else if (ds[0]['ngay_ht_gp'] && this.kt_status != trangthai_db) {
                if (this.dslamtinh_id == 0 || this.dslamtinh_id == -1) {
                  this.$refs.tsbtnHoanCong.classList.add('disabled')
                } else this.$refs.tsbtnHoanCong.classList.remove('disabled')
              } else this.$refs.tsbtnHoanCong.classList.add('disabled')
            }
            if (await this.PHAILAM('HOANTHIENHOSO')) {
              // if (dtList["ngay_ht_gp"])
              this.$refs.tsbtnHoanThienHS.classList.remove('disabled')
              // else this.$refs.tsbtnHoanThienHS.classList.add("disabled");
            }
          }
          //endregion
          else {
            this.$refs.btnIPTinh.disabled = true
            this.$refs.btnIPTinhV4Wan.disabled = true
            this.$refs.btnIPTinhV6Lan.disabled = true
            this.$refs.chkGiuIP.disabled = true
            this.$refs.chkTuDong.disabled = true
            this.iptinh = false
          }

          /// hoangpkn : 20/07/2015
          if (await this.PHAILAM('KICHHOAT_TD')) {
            if (this.kt_status == TRANGTHAI_DONGBO.DONGBO_CM ) this.$refs.tsbtnKichHoat.classList.add('disabled')
            else this.$refs.tsbtnKichHoat.classList.remove('disabled')
          } else this.$refs.tsbtnKichHoat.classList.add('disabled')

          if (this.kt_status == TRANGTHAI_DONGBO.DONGBO_CM) {
            this.$refs.btnChonPort.disabled = true
            this.$refs.btnChonPort_TuDong.disabled = true
          } else {
            if (await this.PHAILAM('CAPNHAT_PORT')) this.$refs.btnChonPort.disabled = false
            if (await this.PHAILAM('CAPNHAT_PORT_TUDONG')) this.$refs.btnChonPort_TuDong.disabled = false
          }
          // end hoang

          if (!(await this.PHAILAM('GIAOVIEC'))) {
            this.$refs.chkNgayBG.checked = true
            this.$refs.dtpNgayBG.disabled = true
            this.$refs.chkNgayBG.disabled = true

            this.$refs.chkNgayGV.checked = true
            this.$refs.dtpNgayGV.disabled = true
            this.$refs.chkNgayGV.disabled = true
          } else {
            this.$refs.dtpNgayBG.disabled = false
            this.$refs.chkNgayBG.disabled = false

            this.$refs.dtpNgayGV.disabled = false
            this.$refs.chkNgayGV.disabled = false
          }
          this.chkNgayGV_CheckedChanged()
          this.chkNgayBG_CheckedChanged()
          //endregion

          // hoangpkn : 05/08/2015 : Bổ sung chặn theo yc a Thắng
          if (this.loaihd_id == LoaiHopDong.DAT_MOI || this.loaihd_id == LoaiHopDong.DI_CHUYEN) {
            if ((await this.PHAILAM('KICHHOAT_TD')) && (await this.PHAILAM('CAPNHAT_CAP'))) {
              if (ds[0]['chan'] == '1') {
                this.$refs.tsbtnKichHoat.classList.add('disabled')
              } else {
                if (this.kt_status == TRANGTHAI_DONGBO.DONGBO_CM) this.$refs.tsbtnKichHoat.classList.add('disabled')
                else this.$refs.tsbtnKichHoat.classList.remove('disabled')
              }
            }

            if (await this.PHAILAM('CAPNHAT_CAP')) {
              if (ds[0]['chan'] == '1') this.$refs.tsddbIn.classList.add('disabled')
              else this.$refs.tsddbIn.classList.remove('disabled')
            }
          }
          // end hoàng

          if ((this.loaihd_id == LoaiHopDong.DAT_MOI || this.loaihd_id == LoaiHopDong.KHOIPHUC_SD) && this.kt_status == TRANGTHAI_DONGBO.DONGBO_CM) this.$refs.txtMatKhauADSL.disabled = true
          else this.$refs.txtMatKhauADSL.disabled = false

          //bangts.HT_Tocdo_Adsl_Combobox_ByLoaiTB(cboGoiCuocAdsl, loaitb_id, " is not null");
          this.$refs.txtMaGD.value = ds[0]['ma_gd']
          this.$refs.txtSoPL.value = ds[0]['so_pl']
          this.ma_gd = ds[0]['ma_gd']
          this.$refs.txtMaTB.value = ds[0]['ma_tb']
          this.ma_tb_cur = ds[0]['ma_tb']

          this.$refs.txtTenTB.value = ds[0]['ten_tb']
          //nhapt thêm khu vực 24/03/2017
          this.$refs.txtkhuvuc.value = ds[0]['ten_kv']
          this.$refs.txtDiaChiLD.value = ds[0]['diachi_ld']
          this.$refs.txtDiaChiLD_Cu.value = ds[0]['diachild_cu']
          this.$refs.txtGhiChu.value = ds[0]['ghichu']
          this.$refs.txtNDGiao.value = ds[0]['nd_giao']
          this.$refs.txtTenKieuLD.value = ds[0]['ten_kieuld']

          // dữ liệu mới
          this.$refs.txtTuyenCap.value = ds[0]['tuyencap']
          this.$refs.txtLienTu.value = ds[0]['lientu']
          this.$refs.txtTuCapNgon.value = ds[0]['tucap_p']
          //txtDoiCapNgon.value = ds[0]["doicap_p"]
          if (ds[0]['doicap_p'] != '-1') this.$refs.txtDoiCapNgon.value = ds[0]['doicap_p']
          else this.$refs.txtDoiCapNgon.value = ''

          this.$refs.txtHopCapNgon.value = ds[0]['hopcap_p']
          if (ds[0]['loaicap_id']) this.$refs.cboLoaiCap.value = ds[0]['loaicap_id']
          this.$refs.txtCapGoc.value = ds[0]['cap_goc']
          this.$refs.txtCapNgon.value = ds[0]['cap_p']

          this.$refs.txtSeriMD.value = ds[0]['seri_md']
          this.seri_md_cur = ds[0]['seri_md']
          this.$refs.txtSLID.value = ds[0]['slid']

          //dungpa them phuc vu bnm

          if (ds[0]['lay_sn']) {
            this.$refs.chkSERI_MD.checked = false
            this.$refs.chkSLID.checked = false
            let lay_sn = ds[0]['lay_sn']
            if (lay_sn == '1') {
              this.$refs.chkSERI_MD.checked = true
            } else {
              this.$refs.chkSLID.checked = true
            }
          } else {
            this.$refs.chkSERI_MD.checked = false
            this.$refs.chkSLID.checked = false
            this.$refs.txtSeriMD.disabled = false
            this.$refs.txtSLID.disabled = false
          }
          this.chkSERI_MD_CheckedChanged()
          this.chkSLID_CheckedChanged()
          //end dungpa
          this.$refs.txtOnuPass.value = ds[0]['onu_password']

          if (ds[0]['doicap_goc'] != '-1') this.$refs.txtDoiCapGoc.value = ds[0]['doicap_goc']
          else this.$refs.txtDoiCapGoc.value = ''
          this.$refs.txtTuCapGoc.value = ds[0]['tucap_goc']
          // end du lieu moi

          //18.10.2020 AnhDt: Hiển thị Serial GDV đã chọn (Mesh wifi)
          //07.12.2020 bổ sung indoor cam & outdoor cam
          if (this.CURRENT_LOAITB_ID == LoaiHinhTB.WIFI_MESH || this.CURRENT_LOAITB_ID == LoaiHinhTB.INDOOR_CAM || this.CURRENT_LOAITB_ID == LoaiHinhTB.OUTDOOR_CAM) {
            this.$refs.txtListSerial.value = 0 // Serial tý bổ sung sau;
          }
          //End AnhDt

          this.$refs.txtSoMetDay.value = ds[0]['culy']
          this.$refs.txtSoMayTN.value = ds[0]['matb_tn']
          if (!this.$refs.txtSoMetDay.value) this.$refs.txtSoMetDay.value = '0'
          if (ds[0]['bras_id']) {
            this.$refs.cboBrasADSL.value = ds[0]['bras_id']
            this.cboBrasADSL_SelectedValueChanged()
          }
          if (ds[0]['dslam_id']) this.$refs.cboDSLAM.value = ds[0]['dslam_id']
          this.$refs.txtIPADSL.value = ds[0]['ip']
          this.$refs.txtIPv4Wan.value = ds[0]['ipv4_wan']
          this.$refs.txtIPv6Lan.value = ds[0]['ipv6_lan']
          this.$refs.txtPort.value = ds[0]['port']
          this.$refs.txtPortMDF.value = ds[0]['port_mdf']
          this.$refs.txtSlot.value = ds[0]['slot']
          this.$refs.txtVPIADSL.value = ds[0]['vpi']
          this.$refs.txtVCIADSL.value = ds[0]['vci']
          if (ds[0]['system'] != '') {
            this.$refs.txtSystem.value = ds[0]['system']
            this.system = ds[0]['system']
          } else {
            this.$refs.txtSystem.value = ''
            this.system = ''
          }
          this.$refs.txtRack.value = ds[0]['rack']
          this.$refs.txtShelf.value = ds[0]['shelf']
          this.passwordMegaVNN = ds[0]['password']
          if (ds[0]['password']) this.$refs.txtMatKhauADSL.value = ds[0]['password']
          else this.$refs.txtMatKhauADSL.value = ''

          this.$refs.txtUserName.value = ds[0]['username']

          //txtEmailAdsl.value = ds[0]["email"]
          if (ds[0]['tocdo_id']) this.$refs.cboGoiCuocAdsl.value = ds[0]['tocdo_id']
          else this.$refs.cboGoiCuocAdsl.value = ''
          if (ds[0]['muccuoc_id']) this.$refs.cboMucCuoc.value = ds[0]['muccuoc_id']
          else this.$refs.cboMucCuoc.value = ''
          if (ds[0]['thietbidc_id']) this.$refs.cboLoaiTB.value = ds[0]['thietbidc_id']
          else this.$refs.cboLoaiTB.value = ''

          //nhapt thêm 08/11/2016
          if (ds[0]['trangbi_id']) this.$refs.cboTrangBiADSL.value = ds[0]['trangbi_id']
          else this.$refs.cboTrangBiADSL.value = ''

          this.$refs.txtSelfid01.value = ds[0]['selfid01']
          this.$refs.txtSelfid02.value = ds[0]['selfid02']
          this.$refs.txtSelfid03.value = ds[0]['selfid03']
          this.$refs.txtSelfid04.value = ds[0]['selfid04']

          if (ds[0]['port_id']) {
            this.port_id = ds[0]['port_id']
            //port_id_cu = port_id;
            let dsip = []
            // dsip = bangts.Lay_dslam_theo_port_id(port_id);
            let dsiprs = await this.axios.post('web-thicong/khaibao-truyensolieu/sp_lay_dslam_theo_port_id', {
              port_id: this.port_id
            })
            dsip = dsiprs.data.data ? dsiprs.data.data : []
            if (dsip.length > 0) this.$refs.txtIpDsLam.value = dsip[0]['ip']
            else this.$refs.txtIpDsLam.value = ''

            if (this.$refs.txtSelfid01.value) this.$refs.txtNasportID.value = this.$refs.txtSystem.value + '/' + this.$refs.txtSelfid04.value + '/' + this.$refs.txtSelfid03.value + '/' + this.$refs.txtSelfid02.value + '/' + this.$refs.txtSelfid01.value
            else this.$refs.txtNasportID.value = this.$refs.txtSystem.value
          } else {
            this.port_id = 0
            //port_id_cu = 0;
            this.$refs.txtIpDsLam.value = ''
            this.$refs.txtNasportID.value = ''
          }
          if (ds[0]['vci_vpi_id']) {
            this.vci_vpi_id = ds[0]['vci_vpi_id']
            //vci_vpi_id_cu = vci_vpi_id;
          } else {
            this.vci_vpi_id = 0
            //vci_vpi_id_cu = 0;
          }

          if (this.$refs.cboLoaiHD.value != LoaiHopDong.DAT_MOI)
            // end hoàng

            this.billingInfo = ds[0]['thongtin_tc']
          this.$refs.txtTTCuoc.value = this.billingInfo
          this.$refs.tsbtnChuyenVMP.style.display = 'none'
          if (this.$refs.cboLoaiHD.value == LoaiHopDong.DAT_MOI && this.loaitb_id == LoaiHinhTB.INTERNET_MYTV) {
            this.$refs.tsbtnChuyenVMP.style.display = null
            this.$refs.tsbtnChuyenVMP.classList.remove('disabled')
            //if (ds[0]["vmp"] == "1")
            //tsbtnChuyenVMP.disabled = true
          }
          let vdstb_lapkem = ''
          vdstb_lapkem = ds[0]['thuebao_lapkem']
          this.$refs.txtTBLapKem.value = vdstb_lapkem
          this.HienThiDSNV()
          if (this.tthd_id == TrangThaiHD.DA_THI_CONG_XONG) {
            this.$refs.tsbtnHoanThienHS.style.display = null
            this.$refs.tsbtnHoanThienHS.disabled = false
          }

          this.HIEN_THONGSO_KYTHUAT()
        }
        this.phien_toida = this.getPhienToiDa()
        this.loading(false)
      } catch (err) {
        console.log(err)
        this.loading(false)
        console.log(err)
      }
    },
    chkTuDong_CheckedChanged() {
      if (this.$refs.chkTuDong.checked) {
        this.$refs.txtIPv4Wan.value = ''
        this.$refs.txtIPv6Lan.value = ''
        this.$refs.txtIPADSL.value = ''
        this.$refs.btnIPTinh.disabled = true
        this.$refs.btnIPTinhV4Wan.disabled = true
        this.$refs.btnIPTinhV6Lan.disabled = true
      } else {
        this.$refs.btnIPTinh.disabled = false
        this.$refs.btnIPTinhV4Wan.disabled = false
        this.$refs.btnIPTinhV6Lan.disabled = false
        this.$refs.txtIPADSL.value = this.str_ip
        this.$refs.txtIPv4Wan.value = this.str_ipv4_wan
        this.$refs.txtIPv6Lan.value = this.str_ipv6_lan
      }
    },
    async HIEN_THONGSO_KYTHUAT() {
      //   if (!this.$refs.palThongSoKyThuat.display == "none") {
      //   }

      try {
        let thongso = await this.axios.post('web-thicong/thicong-internet/fn_lay_thongtin_kythuat_hdtb', {
          hdtb_id: this.CURRENT_HDTB_ID
        })
        let ds = thongso.data && JSON.parse(thongso.data.data).ERROR_CODE != '-1' && thongso.data.data && JSON.parse(thongso.data.data).RESULT ? JSON.parse(thongso.data.data).RESULT : ''
        // if (!ds) {
        //Chỉnh sửa lại sau
        // this.$refs.palThongTinCapCu.style.display == null;
        // this.$refs.palThongSoKyThuat.style.display == "none";
        // } else {
        this.$refs.palThongSoKyThuat.style.height = '200px'
        // this.$refs.palThongTinCapCu.style.display = "none";
        // this.$refs.palThongSoKyThuat.style.display = null;
        this.$refs.palThongSoKyThuat.style.border = '1px solid blue'
        this.$refs.wbrTSKT.innerHTML = ''
        this.$refs.wbrTSKT.innerHTML = ds
        // }
        // Xóa hiển thị cũ
        // Hiển thị dữ liệu mới
      } catch (err) {
        console.log(err)
        this.$refs.wbrTSKT.innerHTML = 'Lỗi: ' + err.message
      }
    },
    async HienThiDSNV() {
      let data = await this.axios.post('web-ccdv/khaibaotsl/lay_ds_nhanvien_theo_phieu_id', {
        phieu_id: this.CURRENT_PHIEU_ID,
        nhanvien_id: this.$root.token.getNhanVienID(),
        kieu_id: 2
      })
      if (data.data && data.data.data.length > 0) {
        let ds = data.data.data
        this.$refs.chkNgayGV.checked = true
        if (ds[0]['ngaygiao']) {
          this.$refs.dtpNgayGV.value = ds[0]['ngaygiao'].includes('/') ? ds[0]['ngaygiao'] : moment(ds[0]['ngaygiao']).format('DD/MM/YYYY HH:mm:ss')
        } else {
          this.$refs.chkNgayGV.checked = this.ngay_cn
        }
        this.$refs.cboNguoiGV.value = ds[0]['nhanvien_giao_id']
        this.$refs.gridGiaoviec.dataSource = ds
        this.nhanvien_tc_id = ds[0]['nhanvien_th_id']
        this.congviec_th = ds[0]['nhiemvu']
      } else {
        this.$refs.gridGiaoviec.dataSource = []
        this.$refs.chkNgayGV.checked = true
        this.$refs.dtpNgayGV.value = this.ngay_cn
        // end hoang
        this.$refs.cboNguoiGV.SelectedValue = this.$root.token.getNhanVienID()
        this.nhanvien_tc_id = -1
        this.congviec_th = ''
      }
      this.chkNgayGV_CheckedChanged()
    },
    async cboLoaiHD_SelectedValueChanged() {
      try {
        if (this.$refs.cboLoaiHD.dataSource.length > 0) {
          this.$refs.cboQuyTrinh.dataSource = []
          if (!this.$refs.cboLoaiHD.value) return

          if (this._vloaihd_id > 0) {
            this.loaihd_id = this._vloaihd_id
            this.$refs.cboLoaiHD.disabled = true
            this.$refs.cboLoaiHD.value = this._vloaihd_id
          } else this.loaihd_id = this.$refs.cboLoaiHD.value
          this.$refs.tsbtnHuyKH.style.display = 'none'
          if (this.loaihd_id == LoaiHopDong.DAT_MOI || this.loaihd_id == LoaiHopDong.DI_CHUYEN || this.loaihd_id == LoaiHopDong.CHUYENDOI_LH) {
            this.$refs.tsbtnHuyKH.style.display = null
            this.$refs.tsbtnHuyKH.classList.add('disabled')
          }

          if (!this.cty_newline) {
            var dt_quytrinh = this.dsDanhMuc['quytrinh'].filter((item) => item.loaihd_id == this.loaihd_id && this.dsdichvuvt_id.split(',').includes(String(item.dichvuvt_id)) && item.tthd_id == this.tthd_id)
            console.log('Kiếm tra quy trình')
            console.log(this.dsdichvuvt_id.split(','))
            console.log(this.dsdichvuvt_id, 'dsdichvuvt_id')
            console.log(this.loaihd_id, 'loaihd_id')
            console.log(this.tthd_id, 'tthd_id')
            console.log(dt_quytrinh, 'Quy trình tương ứng')

            //ds = bangts.Lay_DS_QuyTrinh(loaihd_id.ToString(), dsdichvuvt_id, tthd_id.ToString(), 0);
            if (dt_quytrinh.length > 0) {
              this.$refs.cboQuyTrinh.dataSource = [...new Map(dt_quytrinh.map((item) => [item['quytrinh'], item])).values()].sort((a, b) => {
                return Number(a['quytrinh_id']) - Number(b['quytrinh_id'])
              })
              this.$refs.cboQuyTrinh.dataValueField = 'quytrinh_id'
              this.$refs.cboQuyTrinh.dataTextField = 'quytrinh'
              if (this._vquytrinh_id) this.$refs.cboQuyTrinh.value = this._vquytrinh_id
              else this.$refs.cboQuyTrinh.value = this.$refs.cboQuyTrinh.dataSource.length > 0 ? this.$refs.cboQuyTrinh.dataSource[0].quytrinh_id : null
            } else {
              this.quytrinh_id = -1
              this.$refs.cboQuyTrinh.DataSource = []
              this.$refs.cboQuyTrinh.value = null
            }
          } // Áp dụng cho bên newlife phú mỹ hưng vào thao tác
          else {
            this.loading(true)
            dt_quytrinh = (
              await api.lay_ds_quytrinh_newlife(this.axios, {
                vphanvung_id: null,
                vloaihd_id: this.loaihd_id,
                vdichvuvt_id: DichVuVienThong.ADSL,
                vtthd_id: this.tthd_id
              })
            ).data.data
            console.log('3403 - dt_quytrinh')
            console.log(dt_quytrinh)
            this.loading(false)
            if (dt_quytrinh.length > 0) {
              console.log('dt_quytrinh.length > 0')
              this.$refs.cboQuyTrinh.dataSource = dt_quytrinh
              this.$refs.cboQuyTrinh.dataValueField = 'quytrinh_id'
              this.$refs.cboQuyTrinh.dataTextField = 'quytrinh'
              if (this._vquytrinh_id != 0) this.$refs.cboQuyTrinh.value = this._vquytrinh_id
            } else {
              this.quytrinh_id = -1
              this.$refs.cboQuyTrinh.dataSource = []
              this.$refs.cboQuyTrinh.value = null
            }
          }
          this.cboQuyTrinh_SelectedValueChanged()

          if (this.$refs.cboLoaiHD.value == LoaiHopDong.DAT_MOI) {
            this.$refs.chkdoicong.disabled = true
            this.$refs.chkdoicong.checked = false
          } else {
            if (Number(this.$refs.cboLoaiHD.value) == LoaiHopDong.DI_CHUYEN) {
              if (await this.PHAILAM('KICHHOAT_TD')) this.$refs.chkdoicong.Enabled = true
            } else {
              this.$refs.chkdoicong.disabled = true
              this.$refs.chkdoicong.checked = false
            }
          }
        }
      } catch (err) {
        console.log(err)
      }
    },
    async cboBrasADSL_SelectedValueChanged() {
      if (this.$refs.cboBrasADSL.dataSource.length > 0) {
        if (this.$refs.cboBrasADSL.value == null) return

        let bras_id = this.$refs.cboBrasADSL.value
        // hoangpkn : 09/03/2017 -> sửa lại cách lấy dslam vdc
        //bangts.HT_DSLAM_ADSL_Combobox(cboDSLAM, bras_id, 1);
        let data = await this.axios.post('web-thicong/hths-thaydoi-tocdo/sp_ht_dslam_cbo', {
          kieu_id: 2,
          bras_id: bras_id
        })
        this.$refs.cboDSLAM.dataSource = data.data ? data.data.data : []
        this.$refs.cboDSLAM.dataTextField = 'tendslam'
        this.$refs.cboDSLAM.dataValueField = 'dslam_id'
      }
    },
    async cboQuyTrinh_SelectedValueChanged() {
      if (this.$refs.cboQuyTrinh.dataSource.length > 0) {
        if (this.$refs.cboQuyTrinh.value == null) return
        this.quytrinh_id = Number(this.$refs.cboQuyTrinh.value)

        var dt_luong = this.dsDanhMuc['quytrinh'].filter((item) => item.quytrinh_id == this.quytrinh_id && item.tthd_id == this.tthd_id)

        // var dt_luong = rs.CopyToDataTable();

        if (dt_luong.length > 0) {
          this.luong_id = dt_luong[0]['luong_id']
          this.huonggiao_id = dt_luong[0]['huonggiao_id']
          let tenhg = dt_luong[0]['huonggiao']
          this.labelFunctionTitle = tenhg.toUpperCase()
          this.Text = tenhg
        } else {
          this.$toast.error('Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại')
          this.HienThiDanhSachHDTB()
          return
        }
        this.HienThiGiaoDien(this.luong_id)
        this.loc_nv = 0
        if (await this.PHAILAM('LOCTHEO_NV')) {
          this.loc_nv = 1
        }
        this.hienthi_mau_tgcamket = 0
        if (await this.PHAILAM('HIENTHI_MAU_THICONG')) this.hienthi_mau_tgcamket = 1
        if (!(await this.PHAILAM('GIAOVIEC'))) {
          this.$refs.chkNgayBG.checked = true
          this.$refs.chkNgayGV.checked = true
          this.$refs.dtpNgayBG.value = this.ngay_cn
          this.$refs.dtpNgayGV.value = this.ngay_cn
          this.chkNgayGV_CheckedChanged()
          this.chkNgayBG_CheckedChanged()
        }

        // if (this.kt_load)
        this.HienThiDanhSachHDTB()
      }
    },
    PHAILAM(code) {
      return this.axios
        .post('/web-hopdong/thaydoi_thongso_megawan/phailam', {
          luong_id: this.luong_id,
          code: code
        })
        .then((response) => {
          return response.data && response.data.data == '1' ? true : false
        })
    },
    formatDate(value_) {
      if (value_ && !value_.includes('/')) {
        return moment(value_).format('DD/MM/YYYY HH:mm:ss')
      } else {
        return value_ ? value_ : moment().format('DD/MM/YYYY HH:mm:ss')
      }
    },
    async onSelectPort2(port) {
      console.log(port)
      if (port.IsAONTinh && port.dslam_tinh_id) {
        this.dslamtinh_id = port.dslam_tinh_id
        await this.axios.post("web-thicong/thicong-internet/sp_update_hdtb_adsl", {
          dslam_tinh_id : port.dslam_tinh_id,
          hdtb_id: this.CURRENT_HDTB_ID
        })
        this.HienThiTTHopDongTB()
        return
      }

      console.log(port)
      await this.axios.post('web-thicong/thicong-internet/fn_capnhat_port_hdtb_v2', {
        phieu_id: this.CURRENT_PHIEU_ID,
        port_id: port.port_id,
        vci_vpi_id: port.vci_vpi_id,
        matkhau: this.$refs.txtMatKhauADSL.value,
        ipv4_lan: this.$refs.txtIPADSL.value,
        ipv4_wan: this.$refs.txtIPv4Wan.value,
        ipv6_lan: this.$refs.txtIPv6Lan.value,
        ketcuoi_id: port.ketcuoi_id,
        vitri : port.vitri
      })

      this.HienThiTTHopDongTB()
    },
    async GiaoPhieuView_OnClick() {
      this.giaoPhieuViewDialog.isVisiable = true
      this.$refs.GiaoPhieuView.show()
    },
    async GiaoPhieuView_OnClose() {
      this.giaoPhieuViewDialog.isVisiable = false
    },
    async checkPortOnClick() {
      this.checkPortDialog.isVisiable = true
      this.$refs.checkPort.show()
    },
    async checkPortOnClose() {
      this.checkPortDialog.isVisiable = false
    },
    rowDataBound(args){
      // console.log('rowDataBound',args )
      // if(args.data['gio_conlai']&&Number(args.data['gio_conlai'])<0){

      //   args.row.classList.remove('e-altrow')
      //   args.row.classList.add('gio-con-lai-below0')
      // }
    },
    queryCellInfo(args){
      if (args.column.field === 'ma_tb') {
          if (args.data['gio_conlai']&&Number(args.data['gio_conlai'])<0) {
            args.cell.classList.remove('e-active')
            args.cell.classList.remove('e-selectionbackground')
            args.cell.classList.add('gio-con-lai-below0')
            console.log('queryCellInfo', args)
          }
      }
    }
  }
}
</script>
<style>
a.disabled {
  color: #d3d3d3 !important;
  pointer-events: none;
  cursor: not-allowed;
  opacity: 0.6;
}

a {
  cursor: pointer;
}
.collapsed.fa-angle-up,
.not-collapsed.fa-angle-down {
  display: none !important;
}
.blue {
  color: blue !important;
}

.tracuu .page-content {
  position: unset;
}

.none-padding {
  padding: 0 !important;
}

.green-icon {
  color: green !important;
  font-size: 20px;
  font-weight: bold;
}

.thanhtoan-modal .breadcrumb-top {
  display: none;
}

.thanhtoan-modal .page-content {
  position: unset !important;
}

.thanhtoan-modal .main-wrapper {
  position: unset !important;
}
.gio-con-lai-below0{
  background-color: #ff4d4d !important;
}
</style>
