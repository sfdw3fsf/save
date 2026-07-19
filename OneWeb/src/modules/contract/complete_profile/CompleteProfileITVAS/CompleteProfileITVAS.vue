<template src="./CompleteProfileITVAS.html"></template>
<style src="./CompleteProfileITVAS.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import forge from 'node-forge'
import DatePicker from 'vue2-datepicker'
import { KIEU_DL, TRANGTHAI_PH } from './enum'
import CompleteProfileAPI from './API/CompleteProfileAPI'
import api from './API/CompleteProfileAPI'
import fn from './API/funtions'
import ca from './API/caFuntions'
import smartca from './API/smartCAFunction'
import hkd from './API/hkdFunction'
import ekyc from './API/ekycFunction'
import iqms from './API/iqmsFunction'
import gomeet from './API/gomeetFunction'
import gridview from '@/components/Shared/gridview'
import UploadEProfile from '../../profile/UploadEProfile/UploadEProfile_content.vue'
import ModalChonDiaChi from '../../setup/InstallNewSubs/Popups/popupChonDiaChi'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import ThongTinLienHe from '../../setup/ChangeContractOwner/popup/ThongTinLienHe.vue'
import ChuyenTo from '../../profile/CompleteProfileChangeAccount/Popups/ChuyenTo.vue'
import TonPhieu from '../../setup/Payment/components/TonPhieu.vue'
import SearchEProfile from '../../../search/subscriber/SearchEProfile'
import GiaoPhieuPhoiHop from './popups/popupGiaoPhieuPhoiHop/GiaoPhieuPhoiHop.vue'
import ExportDataModal from '../../profile/ExportData/ExportDataModal'
import NhapTienTrinhTC from './popups/popupNhapTienTrinhTC/NhapTienTrinhTC.vue'
import VatTuThueBao from '@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/VatTuThueBao.vue'
import PopupThongTinHenKhachHang from '@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import CreateIssue from './popups/popupCreateIssue/CreateIssue.vue'
import jira from './popups/popupCreateIssue/PopUpApi'
import commonFn from '@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions'
import HDTB_SUB_V2 from './popups/popupHDTB_SUB/HDTB_SUB_V2.vue'
import HDTB_SUB_V3 from './popups/popupHDTB_SUB/HDTB_SUB_V3.vue'
import XacNhanBaoTri from './popups/popupXacNhanBaoTri/XacNhanBaoTri.vue'
import KiemTraSoGiayToCA from './popups/popupKiemTraSoGiayToCA/KiemTraSoGiayToCA.vue'
import TraCuuHoSoHDTB from './popups/popupTraCuuHoSoHDTB/TraCuuHoSoHDTB.vue'
import DoiSoatCA from './popups/popupDoiSoatCA/DoiSoatCA.vue'
//import frmTraPhieuTC from "../../setup/ReturnInstallTicket/frmTraPhieuTC.vue"
import InBienBan from '@/modules/print/InBienBan/InBienBan.vue'
import DangNhapIVAN from '@/modules/contract/setup/SubscriberLiquidationHost/DangNhapIVAN.vue'
import DangKyHoaDonDienTu78 from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupDangKyHoaDonDienTu78/DangKyHoaDonDienTu78.vue'
import CapNhatThongTinKHHDDT from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupCapNhatThongTinKHHDDT/CapNhatThongTinKHHDDT.vue'
import frmBRNGuiMail from './popups/frmBRNGuiMail/frmBRNGuiMail'
//TUANLAHYN
import RegiSMSBrandname from './popups/RegiSMSBrandname/RegiSMSBrandname.vue'
//TUANLAHYN
/**cuongpq thêm kích hoạt sms brn */
import popupCreateAgentSMSBrandname from '@/modules/contract/setup/CreateAgentSMSBrandname/popupCreateAgentSMSBrandname.vue'
import popupTaoKHTT from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupTaoKHTT'
import popupKichhoatBrandname from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupKichhoatBrandname/popupKichhoatBrandname.vue'
import GiaoPhieuNet from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupGiaoPhieuNet/GiaoPhieuNet.vue'
import ThayDoiKhuVucQuanLyThueBaoModal from '@/modules/contract/setup/SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue'
import frmBanDoTB from '@/modules/contract/setup/DeclareLandline/popups/frmBanDoTB.vue'
import DangKyHKD from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupKichHoatHKD/KichHoatHKD.vue'
import PopupThuHoiCATaoLoi from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupThuHoiCATaoLoi/frmPopupThuHoiCATaoLoi.vue'
import DuyetSmartCAOnline from './popups/PopupKySmartCA/PopUpDuyetSmartCA.vue'
import DangKyIQMS from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupKichHoatIQMS/KichHoatIQMS.vue'
import ModalPromise from '@/modules/contract/complete_profile/CompleteProfileITVAS/components/ModalPromise.vue'
import HuyDuyetCAOnline from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupHuyDuyetCAOnline/HuyDuyetCAOnline.vue'
//import frnGiaoPhieuNV from "../../setup/DeclareLandline/popups/frnGiaoPhieuNV.vue"
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import { required, minValue, maxValue } from 'vuelidate/lib/validators'
import treemenuVue from '../../../quantri/components/UploadFileHoSoDienTu/treemenu.vue'
import PopupChuyenDoiHDDT from './popups/popupChuyenDoiHDDT/popupChuyenDoiHDDT.vue'
import PopupHocBaDienTu from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupHocBaDienTu/HocBaDienTu.vue'
import PopupHuyHocSinhHBDT from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupHocBaDienTu/HuyHocSinhHBDT'
import { LOAI_HOPDONG, DICHVU_VIENTHONG, TRANGTHAI_HOPDONG, LOAIHINH_THUEBAO, TRANGTHAI_DONGBO } from '@/constants'
import { KIEU_HD, LoaiHopDong, LoaiHinhTB, KieuLapDat } from '@/const/enums'
import GiaoPhieuView from '@/modules/contract/setup/HandoverTicket/giao_phieu_view.vue'
import CatHuyUserPharmacy from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupVNPTPharmacy/CatHuyUserPharmacy.vue'
import CatHuyCuaHangPharmacy from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupVNPTPharmacy/CatHuyCuaHangPharmacy.vue'
import DongBoEticket from './popups/popupETicket/DongBoEticket.vue'
// SMS A2P
import frm_Create_Agent_SMPP from '@/modules/contract/complete_profile/CompleteProfileITVAS/SMS_A2P/frm_Create_Agent_SMPP.vue'
import frm_Create_Label_A2p from '@/modules/contract/complete_profile/CompleteProfileITVAS/SMS_A2P/frm_Create_Label_A2p.vue'
import frm_Tao_DHTT_A2P from '@/modules/contract/complete_profile/CompleteProfileITVAS/SMS_A2P/frm_Tao_DHTT_A2P.vue'
import frm_Giahan_DHTT_A2P from '@/modules/contract/complete_profile/CompleteProfileITVAS/SMS_A2P/frm_Giahan_DHTT_A2P.vue'
import frm_TDTT_Label_A2P from '@/modules/contract/complete_profile/CompleteProfileITVAS/SMS_A2P/frm_TDTT_Label_A2P.vue'
import SearchVNEduData from '@/modules/search/subscriber/SearchVNEduData/popup/themeListEdu/themeListEdu.vue'
const frmNhapDSThanhVien = () => import('@/modules/contract/setup/ChangeContractOwner/popup/frmNhapDSThanhVien.vue')
//
import fileDownload from 'js-file-download'
import { defineAsyncComponent } from '@vue/composition-api'
import { mapState, mapActions } from 'vuex'
import { fn_kiemtra_thongtin_hopdong, lay_tt_hdtb_sub, lay_trangthai_hdtb, kiemtra_dulieu_laphopdong_cntt } from '../../setup/z_common_func/setup_common'
// import { lay_tt_hdtb_sub } from '../../setup/z_common_func/setup_common'
import CATokenSign from './popups/popupSign/CASign.vue'
import SmartCASign from './popups/popupSign/SmartCASign.vue'
import CapBuSanLuongHDDT from './popups/CapBuSanLuongHDDT/CapBuSanLuongHDDT'
import CapBuSanLuongMTT from './popups/CapBuSanLuongHDDT/CapBuSanLuongMTT'
import axios from 'axios'
export default {
  components: {
    breadcrumb,
    gridview,
    UploadEProfile,
    ModalChonDiaChi,
    DatePicker,
    BssErrorMarker,
    BssRequiredMarker,
    ThongTinLienHe,
    ChuyenTo,
    TonPhieu,
    SearchEProfile,
    GiaoPhieuPhoiHop,
    ExportDataModal,
    VatTuThueBao,
    NhapTienTrinhTC,
    PopupThongTinHenKhachHang,
    CreateIssue,
    HDTB_SUB_V2,
    HDTB_SUB_V3,
    //frmTraPhieuTC,
    InBienBan,
    DangNhapIVAN,
    DangKyHoaDonDienTu78,
    CapNhatThongTinKHHDDT,
    frmBRNGuiMail,
    //TUANLAHYN
    RegiSMSBrandname,
    //ENDTUANLAHYN
    /**cuongpq sms brn */
    popupCreateAgentSMSBrandname,
    popupTaoKHTT,
    popupKichhoatBrandname,
    GiaoPhieuNet,
    XacNhanBaoTri,
    KiemTraSoGiayToCA,
    TraCuuHoSoHDTB,
    DoiSoatCA,
    CATokenSign,
    SmartCASign,
    ThayDoiKhuVucQuanLyThueBaoModal,
    frmBanDoTB,
    ModalPromise,
    DangKyHKD,
    PopupThuHoiCATaoLoi,
    DuyetSmartCAOnline,
    DangKyIQMS,
    HuyDuyetCAOnline,
    GiaoPhieuView,
    PopupChuyenDoiHDDT,
    PopupHocBaDienTu,
    PopupHuyHocSinhHBDT,
    CatHuyUserPharmacy,
    CatHuyCuaHangPharmacy,
    frmNhapDSThanhVien,
    frm_Create_Agent_SMPP,
    frm_Create_Label_A2p,
    frm_Tao_DHTT_A2P,
    frm_Giahan_DHTT_A2P,
    frm_TDTT_Label_A2P,
    frmETicket: defineAsyncComponent(() => import('./popups/popupETicket/ETicket.vue')),
    LoginCommon: defineAsyncComponent(() => import('./popups/popupLoginCommon/LoginCommon.vue')),
    SearchVNEduData,
    DongBoEticket,
    CapBuSanLuongHDDT,
    CapBuSanLuongMTT
  },
  name: 'CompleteProfile',
  props: ['Tag'],
  validations: {
    diachiLD: {
      DIACHI: {
        required
      }
    }
  },
  created() {
    //THANGDM1 them 2022-12-07 them de xu ly loading
    let numberOfAjaxCAllPending = 0
    // Add a request interceptor
    this.axios.interceptors.request.use(
      (config) => {
        numberOfAjaxCAllPending++
        // show loader
        //this.loading(true)
        return config
      },
      (error) => {
        return Promise.reject(error)
      }
    )
    // Add a response interceptor
    this.axios.interceptors.response.use(
      (response) => {
        numberOfAjaxCAllPending--
        //this.loading(true)
        if (numberOfAjaxCAllPending == 0) {
          //hide loader
          //this.loading(false)
        }
        return response
      },
      (error) => {
        numberOfAjaxCAllPending--
        if (numberOfAjaxCAllPending == 0) {
          //hide loader
          //this.loading(false)
        }
        return Promise.reject(error)
      }
    )
  },
  mounted() {
    let vnptPluginScript = document.createElement('script')
    vnptPluginScript.setAttribute('src', '/static/vendor/vnpt-plugins/vnpt-cams-plugin.js')
    document.head.appendChild(vnptPluginScript)
    // vnptPluginScript = document.createElement('script')
    // vnptPluginScript.setAttribute('src', '/static/vendor/vnpt-plugins/vnpt-plugin.js')
    // document.head.appendChild(vnptPluginScript);
    vnptPluginScript = document.createElement('script')
    vnptPluginScript.setAttribute('src', '/static/vendor/vnpt-plugins/base64.js')
    document.head.appendChild(vnptPluginScript)
    this.pageLoad()
  },
  data() {
    // WinUI.WinUIDieuHanhThiCong.frmHoanCongDVCNTT,PTTB.exe
    // Tag = "9+1+14;15;16;13";(9%2B1%2B14%3B15%3B16%3B13)          [UR2.6.032] - Khai báo Dịch vụ CNTT
    // Tag = "5+1;3;6;30;41;15+15;16;14;13";                        [UR2.6.045] HTHS DỊCH VỤ CNTT/GTGT
    // Tag = "4+1;41+15;14";(4%2B1%3B41%2B15%3B14)                  [UR2.6.063] - THI CÔNG DV CNTT
    // Tag=3+1;30+15;16;14; (urlEncode: 3%2B1%3B30%2B15%3B16%3B14)  [UR2.5.040] - TT CNTT - Khai báo HDDT
    // 22+1+13; urlEncode: 22%2B1%2B13                              [UR2.5.043] - ĐHTT- Lắp mới CNTT
    // 4+1+15;14;16 4%2B1%2B15%3B14%3B16                            [UR2.6.050] - Thi công đặt mới dịch vụ CNTT
    // 4+1+15;14;16 4%2B1%2B15%3B14%3B16                            [UR2.6.033] - Thi công lắp mới Dịch vụ CNTT- TT dữ liệu
    // 9+1;3;4;7;30;41;8+14;15;16;13                                [UR2.6.049] - Đài hỗ trợ khai báo Dịch vụ CNTT- ATBMTT-TTDL-Hội nghị truyền hình
    // 9+1+13 9%2B1%2B13                                            [UR2.6.051] - Khai báo dịch vụ Hội nghị truyền hình
    // 5+1+15;14;16 5%2B1%2B15%3B14%3B16                            [UR2.6.047] - Hoàn thiện hồ sơ đặt mới Dịch vụ CNTT - TTDL
    // 9+1+14 CompleteIProfileVANService                            [UR2.6.031] - Hoàn thiện hồ sơ dịch vụ IVAN
    console.log('$route.query.tag', this.$route.query)
    var Tag = ''
    if (this.$route.query.tag != null && this.$route.params.tag != '' && this.$route.query.tag.length > 0) {
      Tag = this.$route.query.tag
    }
    console.log('TAG', Tag)
    var tthd_id = 4
    var dsloaihd_id = 1
    var dsdichvuvt_id = 4
    var _flagTSKT = ''
    if (Tag != '') {
      var str = Tag.toString()
      var words = str.split('+')
      if (words.length >= 1) tthd_id = words[0]
      if (words.length >= 2) {
        dsloaihd_id = words[1]
        dsloaihd_id = dsloaihd_id.replaceAll(';', ',')
      }
      if (words.length >= 3) {
        dsdichvuvt_id = words[2]
        dsdichvuvt_id = dsdichvuvt_id.replaceAll(';', ',')
      }
      if (words.length >= 4) {
        _flagTSKT = words[3]
        _flagTSKT = _flagTSKT.replaceAll(';', ',')
      }
      if (dsdichvuvt_id == '') {
        dsdichvuvt_id = '4' //TuanNA: Thêm code này trong trường hợp Lam ko truyền dịch vụ vt vào chức năng(Tag)
      }
    } else {
      tthd_id = TRANGTHAI_HOPDONG.DANG_THI_CONG
      dsloaihd_id = '1'
      dsdichvuvt_id = '4'
    }
    console.log('tthd_id', tthd_id)
    console.log('dsloaihd_id', dsloaihd_id)
    console.log('dsdichvuvt_id', dsdichvuvt_id)
    return {
      inputDataPopupTaoKHTT: { vhdtb_id: 0 },
      dialogComponent: null,
      dialogEvts: {
        close: this.dlgClose
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: '',
      popupComponentHeader: '',
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close': this.popupClosed
      },
      header: {
        title: 'OMC/Vip/Tổ CM- Thay đổi dịch vụ',
        list: [
          { name: 'Lập hợp đồng/Đổi tốc độ', link: { name: 'Ui.cards' } },
          {
            name: 'Loại hình thuê bao/OMC/Vip/Tổ CM- Thay đổi dịch vụ',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      data_emit_func: {},
      selectionOptions: { type: 'Multiple' },
      target: '.main-wrapper',
      dateFormat: 'DD/MM/YYYY',
      datetimeFormat: 'DD/MM/YYYY hh:mm A',
      tag: Tag,
      position: { X: 'center', Y: 'top' },
      modelVatTu: {
        phieu_id: 0,
        loaihd_id: 0,
        hdtb_id: 0,
        thuebao_id: 0
      },
      tt_nd: {
        nhanvien_id: this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        may_cn: this.$root.token.getMachine(),
        ip_cn: this.$root.token.getIP(),
        ngay_cn: moment(new Date()).format('DD/MM/YYYY hh:mm A'),
        ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format('DD/MM/YYYY hh:mm A'),
        nguoi_cn: this.$root.token.getUserName(),
        ma_nd: this.$root.token.getUserName(),
        ma_tinh: this.$root.token.getMaTinh()
      },
      xuatPhieuShow: false,
      controls: {
        tsbtnGiaoViec: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnChapNhan: {
          visible: true,
          enabled: false,
          type: 'menu_button'
        },
        tsbtnTraPhieu: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnGiaoPhieu: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbUpHoSo: {
          visible: true,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnHuyKH: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnKichHoat: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnHoanCong: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnGiaoPhoiHop: {
          visible: true,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnHoanThienHS: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnTraPhieu: {
          visible: true,
          enabled: true,
          type: 'menu_button'
        },
        tsbtn_KichHoat_CA: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnVatTu: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnGiaoViec_KBB: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnCreatIssue: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbThuHoiCTS: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tbs_giahan_ca: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbXemCMS: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbXemHS: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtn_Duyet_CA: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtn_khong_duyet_CA: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbTaoToken: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnGiaoPhieuPhoiHop: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnHoanThanh: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnChuyenTo: {
          visible: true,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnGiaoPhieu_Ton: {
          visible: true,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnNVKT: {
          visible: true,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnXuatFile: {
          visible: true,
          enabled: true,
          type: 'menu_button'
        },
        tsddbIn: {
          visible: true,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnHen: {
          visible: true,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnFile_Upload: {
          visible: true,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnNhapMoiTT: {
          visible: true,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnGuiMailKH: {
          visible: true,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnDongBoCMS: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnDongBoHS: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnGuiHSCMS: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnXanNhanBT: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnLayThongTinSmartCMS: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnKiemTraCA: {
          visible: false,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnDuyetCA: {
          visible: true,
          enabled: true,
          type: 'menu_button'
        },
        tsbtnSmartCAStatus: {
          visible: true,
          enabled: true,
          type: 'menu_button'
        },
        cboQuyTrinh: {
          visible: true,
          enabled: false,
          type: 'droplist'
        },
        xtraTabPage1: {
          visible: true,
          enabled: true,
          type: 'tab'
        },
        xtraTabPage2: {
          visible: false,
          enabled: true,
          type: 'tab'
        },
        xtraTabPage3: {
          visible: false,
          enabled: true,
          type: 'tab'
        },
        dtpNgayBG: {
          enabled: true,
          value: moment(new Date() + 30)
        },
        dtpNgayGV: {
          enabled: true,
          value: moment(new Date())
        },
        dtpDueDate: {
          enabled: true,
          value: moment(new Date())
        },
        dtpResolved: {
          enabled: true,
          value: moment(new Date())
        },
        chkNgayGV: {
          enabled: true
        },
        chkNgayBG: {
          enabled: true
        }
      },
      dsHDTB_SUB: [],
      ma_tb: '',
      state: {
        phieu_id: 0,
        isdemo: false,
        isdebug: true,
        tthd_id: tthd_id, //TRANGTHAI_HOPDONG.DA_THI_CONG
        dsloaihd_id: dsloaihd_id,
        dsdichvuvt_id: dsdichvuvt_id,
        donvi_id: this.$root.token.getDonViID(),
        lay_phieu_moi: true,
        loc_theo_nv: false,
        tongtien: 0,
        nhanvien: '',
        nhanvien_tiepthi: '',
        checkbox_hen: false,
        luong_id: 0,
        loaihd_id: 0,
        dichvuvt_id: 0,
        ma_tb: '',
        ma_gd: '',
        hdtb_id: 0,
        selectedPhieu: {
          ma_gd: '',
          ma_tb: '',
          loaitb_id: 89,
          phieu_id: 0,
          hdtb_id: 0,
          hdkh_id: 0,
          nhanvien_id: 0,
          ngaygiao: ''
        },
        dataExport: []
      },
      listbox: {
        ds_kieuhd: {
          list: [],
          value: 0
        },
        ds_kenhthu: {
          list: [],
          value: 0
        },
        ds_loaihd: {
          list: [],
          value: 0
        },
        ds_quytrinh: {
          list: [],
          value: 0
        },
        ds_trangthai_jira: {
          list: [],
          value: 0
        },
        resolution_name: '',
        resolution_id: 0
      },
      diachiLD: {
        DIACHI: null,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        SO_NHA: null,
        Lng: 0,
        Lat: 0,
        BLOCK: null,
        TANG: null,
        PHONG: null,
        MOTA: null
      },
      datagrid: {
        ds_hdtb: {
          list: [],
          selected_row: {}
        }
      },
      tab_dvcntt: {
        list: [],
        tt_giaoviec: {
          check_ngaygv: false,
          check_ngayht: false,
          ngay_gv: null,
          ngay_ht: null,
          ds_nguoigv: {
            list: [],
            value: 0
          },
          tt_hdlapdat: {},
          grid_nhanvien: { list: [] }
        }
      },
      tab_it360: {
        list: []
      },
      tab_ttgoicuoc: {
        list: []
      },
      tab_dvgiatang: {
        list: []
      },
      tab_mua_tbi: {
        list: []
      },
      tab_tthicong: {
        list: []
      },
      tab_ctthicong: {
        list: []
      },
      CAConstanst: {
        isCADev: false,
        isShowLineWarning: false
      },
      //Eticket
      ETicketData: {
        mst: '',
        isShow: false
      },
      isShow: false,
      modelIn: {},
      dataPopupKhuVuc: {},
      giaoPhieuViewDialog: {
        animationSettings: { effect: 'None' },
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
      iAlert_value: {
        trang_thai_lap_dat: 0,
        trang_thai_load_hd: false
      },
      ppLoginCommon: {
        isShow: false
      },
      resource: ''
    }
  },
  computed: {},
  validations: {
    check_ngaygv: {
      required(value) {
        return value
      }
    }
  },
  methods: {
    ...mapActions(['setThongTinHDTB', 'setTsktDvCntt', 'setDSHDTB', 'setRAKichHoat']),
    //hconganh
    showFrmETicket() {
      this.ETicketData.isShow = true
      this.$refs.modalETicket.show()
    },
    close_frmETicket() {
      this.ETicketData.isShow = false
      this.$refs.modalETicket.hide()
    },
    //end hconganh
    close_frmVNeduPortal() {
      this.isShow = false
      this.$refs.modalETicket.hide()
    },
    ThongBaoBMIS: function(response) {
      if (response.data.error == 200) {
        if (response.data.error_code === 'BSS-00000000') {
          const data = JSON.parse(response.data.data)
          if (data.ErrorCode == '00') return this.$toast.success(data.ErrorDesc)
          else return this.$toast.error(data.ErrorDesc)
        } else return this.$toast.error(response.data.data)
      } else return this.$toast.error(response.data.message_detail)
    },
    async pageLoad() {
      if (!this.tag || this.tag == '') {
        this.$toast.error('Bạn đang truy cập vào đường link thiếu form tag!')
        return
      }
      try {
        this.tt_nd.may_cn = await this.$root.token.getMachine()
        this.tt_nd.ip_cn = await this.$root.token.getIP()
        //Combobox Kênh Thu
        var api1 = CompleteProfileAPI.CSS_KENHTHU(this.axios, {}).then((res) => {
          //console.log("CSS_KIEU_HD", response);
          this.listbox.ds_kenhthu.list = res.data.data.map((x) => ({
            id: x.KENHTHU_ID,
            text: x.KENHTHU
          }))
          if (this.listbox.ds_kenhthu.list && this.listbox.ds_kenhthu.list.length > 0) this.listbox.ds_kenhthu.value = this.listbox.ds_kenhthu.list[0].id
        })
        //Load ListBox
        var api2 = CompleteProfileAPI.CSS_KIEU_HD(this.axios, {}).then((res) => {
          //console.log("CSS_KIEU_HD", response);
          this.listbox.ds_kieuhd.list = res.data.data.map((x) => ({
            id: x.KIEUHD_ID,
            text: x.KIEU_HD
          }))
          if (this.listbox.ds_kieuhd.list && this.listbox.ds_kieuhd.list.length > 0) this.listbox.ds_kieuhd.value = this.listbox.ds_kieuhd.list[0].id
        })
        var api3 = CompleteProfileAPI.lay_ds_loai_hd(this.axios, {
          loaihd_id: this.state.dsloaihd_id
        }).then((res) => {
          //console.log("CSS_KIEU_HD", response);
          this.listbox.ds_loaihd.list = res.data.data.map((x) => ({
            id: x.loaihd_id,
            text: x.ten_loaihd
          }))
          if (this.listbox.ds_loaihd.list && this.listbox.ds_loaihd.list.length > 0) {
            this.listbox.ds_loaihd.value = this.listbox.ds_loaihd.list[0].id
            this.onChange_loaihd(this.listbox.ds_loaihd.value)
          }
        })
        var api4 = CompleteProfileAPI.lay_ds_nhanvien_theo_donvi(this.axios, {
          donvi_id: this.tt_nd.donvi_id
        }).then((res) => {
          this.tab_dvcntt.tt_giaoviec.ds_nguoigv.list = res.data.data.map((x) => ({
            id: x.nhanvien_id,
            text: x.ten_nv
          }))
        })
        this.controls.dtpNgayGV.enabled = false
        this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value = this.tt_nd.nhanvien_id
        if (this.state.tthd_id == TRANGTHAI_HOPDONG.DA_THI_CONG_XONG) {
          this.controls.tsbtnHoanThienHS.visible = true
        }
        var api5 = this.CREATE_TABLE_CMT()
        var api6 = this.LOAD_COMBO()
        this.kt_load = true
        if (this.state.tthd_id != 25) {
          this.controls.xtraTabPage2.visible = false
          this.controls.xtraTabPage3.visible = false
          this.controls.tsbtnGiaoPhoiHop.visible = false
        }
        //this.loading(true)
        this.state.isPageLoad = true
        await Promise.all([api1, api2, api3, api4, api5, api6])
          .then(async () => {})
          .catch(() => {})
          .then(async () => {})
          .finally(() => {})
          .then(() => {})
        console.log('pageLoad DONE!!!!')
        this.hideLoading()
      } catch (ex) {
        ////this.loading(false)
        commonFn.showException(this, ex)
      } finally {
        this.hideLoading()
      }
    },
    async CREATE_TABLE_CMT() {
      //TODO
    },
    async LOAD_COMBO() {
      await CompleteProfileAPI.lay_ds_trangthai_jira(this.axios, {
        donvi_id: this.tt_nd.donvi_id
      }).then((res) => {
        if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
          this.listbox.ds_trangthai_jira.list = res.data.data
            // .filter((x) => x.ttjr_id > 0)
            .map((x) => ({ id: x.trangthai_jira, text: x.trangthai_jira }))
        }
      })
    },
    async HienThiTTHopDongTB(ds) {
      if (!ds || ds.length == 0) return
      try {
        //this.loading(true)
        var ngay_cn = this.tt_nd.ngay_cn
        var ngay_bg = this.tt_nd.ngay_bg
        var dr = this.state.selectedPhieu
        this.controls.tsbtnChapNhan.enabled = true
        this.controls.tsbtnTraPhieu.enabled = true
        this.state.hdtb_id = dr['hdtb_id'] * 1
        this.state.hdkh_id = dr['hdkh_id'] * 1
        this.state.loaihd_id = dr['loaihd_id'] * 1
        this.state.dichvuvt_id = dr['dichvuvt_id'] * 1
        this.state.ngaygiao = dr['ngaygiao']
        this.state.ngaytra = this.tt_nd.ngay_cn
        this.state.vloaitb_id = dr['loaitb_id'] * 1
        if (this.state.loaihinh_tb != dr['loaihinh_tb'].toString()) {
          if (this.state.vloaitb_id == LOAIHINH_THUEBAO.CA || this.state.vloaitb_id == LOAIHINH_THUEBAO.SMART_CA) {
            await this.HienThiGiaoDien(this.state.luong_id)
          }
        }
        this.state.loaihinh_tb = dr['loaihinh_tb'].toString()
        if (this.listbox.ds_quytrinh.value * 1 != dr['quytrinh_id'] * 1) {
          this.listbox.ds_quytrinh.value = dr['quytrinh_id'] * 1
          this.state.luong_thaotac = []
          await this.onChange_quytrinh(dr['quytrinh_id'] * 1)
        }
        // txtMaGD.text = dr["ma_gd"];
        // txtSoPL.text = dr["so_pl"];
        this.state.ma_gd = dr['ma_gd']
        // txtMaTB.text = dr["ma_tb"];
        // txtTenTB.text = CommonFunction.ChuanHoaTen(dr["ten_tb"].toString());
        this.diachiLD.DIACHI = this.ChuanHoaTen(dr['diachi_ld']) // txtDiaChiLD.text = CommonFunction.ChuanHoaTen(dr["diachi_ld"].toString());
        let apiHienThiDiaChi = this.HienThiDiaChi(dr)
        // txtGhiChu.text = dr["ghichu"];
        // txtNDGiao.text = dr["nd_giao"];
        // txtTenKieuLD.text = dr["ten_kieuld"];
        this.state.passwordMegaVNN = dr['password']
        // txtLoaiHinh.text = dr["loaihinh_tb"];
        //this.state.txtKhuVuc = dr["ten_kv"];
        this.state.kieuld_id = dr['kieuld_id'] * 1
        this.state.ma_tb = dr['ma_tb']
        this.ma_tb = dr['ma_tb']
        //Lấy thông tin liên hệ lắp đặt thuê bao
        let apilh_lapdat = fn.lay_thongtin_lh_lapdat(this.axios, this.state.hdkh_id).then((res) => {
          this.tab_dvcntt.tt_giaoviec.tt_hdlapdat = 'Liên hệ : ' + res
          if (this.tab_dvcntt.tt_giaoviec.tt_hdlapdat == 'Liên hệ : ') this.tab_dvcntt.tt_giaoviec.tt_hdlapdat = ''
        })
        //Tổng tiền
        let apiTongTien = CompleteProfileAPI.lay_tongtien_theo_hdkh_id(this.axios, {
          vhdkh_id: dr.hdkh_id
        }).then((res) => {
          this.state.tongtien = 0
          if (res.data.data.length > 0 && res.data.data[0].tongtien) {
            this.state.tongtien = res.data.data[0].tongtien
          }
        })
        //txtNhanVien
        let apinhanvien = CompleteProfileAPI.lay_thongtin_nhanvien(this.axios, {
          nhanvien_id: this.state.selectedPhieu.nhanvien_id
        }).then((res) => {
          this.state.nhanvien = res.data.data
        })
        //NV Tiếp thị
        if (this.state.selectedPhieu.ctv_id) {
          CompleteProfileAPI.lay_thongtin_nhanvien(this.axios, {
            nhanvien_id: this.state.selectedPhieu.ctv_id
          }).then((res) => {
            this.state.nhanvien_tiepthi = res.data.data
          })
        }
        this.state.vloaitb_id = dr['loaitb_id'] * 1
        let loaihd_id = this.listbox.ds_loaihd.value * 1
        this.state.thuebao_id = dr['thuebao_id']
        if (loaihd_id == LOAI_HOPDONG.DI_CHUYEN || loaihd_id == LOAI_HOPDONG.KHOIPHUC_SD || (loaihd_id == LOAI_HOPDONG.CHUYENDOI_LH && this.state.vloaitb_id != LOAIHINH_THUEBAO.FSECURE)) {
          //Lấy thuê bao ID đối với loại hợp đồng dịch chuyển
          this.state.thuebao_id = dr['thuebao_id']
        }
        this.state.phieu_id = dr['phieu_id'] * 1
        //set index combobox kênh thu
        let apiKenhThu = CompleteProfileAPI.sp_lay_kenhthu_theo_hdkh(this.axios, {
          hdkh_id: this.state.selectedPhieu.hdkh_id
        }).then((res) => {
          if (res.data.data.length > 0) this.listbox.ds_kenhthu.value = res.data.data[0].kenhthu_id
        })
        //set index combobox Kiểu HĐ
        if (this.state.selectedPhieu.kieuhd_id) this.listbox.ds_kieuhd.value = this.state.selectedPhieu.kieuhd_id
        else this.listbox.ds_kieuhd.value = KIEU_HD.TAI_GD
        //checkbox_hen
        let apiCheckHen = CompleteProfileAPI.kiemtra_hen_hdtb(this.axios, {
          hdtb_id: this.state.hdtb_id
        }).then((res) => {
          if (res.data.data.length > 0) this.state.checkbox_hen = res.data.data[0].kq > 0
          else this.state.checkbox_hen = false
        })
        //Lấy thông tin trả
        let apiTTTra = CompleteProfileAPI.sp_ht_phieu_tra(this.axios, {
          hdtb_id: this.state.selectedPhieu.hdtb_id,
          phieu_id: this.state.selectedPhieu.phieu_id
        }).then((res) => {
          if (res.data.data.length > 0) {
            this.state.nd_tra = res.data.data[0].nd_tra
          }
        })
        //Lấy thông tin KH Lớn
        this.state.khachhanglon = ''
        if (dr['khlon_id'] && dr['khlon_id'].toString() != '') {
          var loaikhlon_id = dr['khlon_id'] * 1
          this.state.loaikhlon_id = loaikhlon_id
          CompleteProfileAPI.lay_ten_loai_khl(this.axios, {
            lkhl_id: loaikhlon_id
          }).then((res) => {
            this.state.khachhanglon = res.data.data
          })
        }
        let vloaitb_id = this.state.vloaitb_id
        let kt_status = dr['status'] ? dr['status'] : ''
        this.state.kt_status = kt_status
        let ngay_ht_gp = dr['ngay_ht_gp'] ? dr['ngay_ht_gp'] : null
        if (ngay_ht_gp) ngay_ht_gp = moment(ngay_ht_gp, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY hh:mm A')
        this.controls.tsbtn_KichHoat_CA.text = 'Kích hoạt'
        if (vloaitb_id == LOAIHINH_THUEBAO.TTDL_HDDT) {
          if (kt_status == '0') {
            this.controls.tsbtn_KichHoat_CA.text = 'Kích hoạt'
            this.controls.tsbtn_KichHoat_CA.toolTipText = 'Tạo khách hàng trên hệ thống HDDT'
          }
          if (kt_status == '1') {
            this.controls.tsbtn_KichHoat_CA.text = 'Kích hoạt'
            this.controls.tsbtn_KichHoat_CA.toolTipText = 'Đăng ký mãu hóa đơn'
          }
          if (kt_status == '3') {
            this.controls.tsbtn_KichHoat_CA.text = 'Kích hoạt'
            this.controls.tsbtn_KichHoat_CA.toolTipText = 'Tạo hệ thống demo cho khách hàng'
          }
          if (kt_status == '7') {
            this.controls.tsbtn_KichHoat_CA.text = 'Kích hoạt'
            this.controls.tsbtn_KichHoat_CA.toolTipText = 'Tạo hệ thống thật cho khách hàng'
          }
          if (loaihd_id == LOAI_HOPDONG.CHAMDUT_SD || loaihd_id == LOAI_HOPDONG.THAY_DOI_DV) {
            this.controls.tsbtn_KichHoat_CA.text = 'Kích hoạt'
            this.controls.tsbtn_KichHoat_CA.toolTipText = 'Hủy gói hóa đơn điện tử'
          }
          this.controls.tbs_giahan_ca.text = 'Cập nhật KH'
        } else if (vloaitb_id == LOAIHINH_THUEBAO.SMART_CA || vloaitb_id == LOAIHINH_THUEBAO.CA) {
          this.controls.tbs_giahan_ca.text = 'Thu hồi CTS'
          // this.controls.tbs_giahan_ca.text = 'Thu hồi chứng thư số tạo lỗi'
          this.controls.tbs_giahan_ca.toolTipText = 'Thu hồi chứng thư số tạo lỗi'
        } else {
          this.controls.tbs_giahan_ca.text = 'Gia  hạn'
          this.controls.tsbtn_KichHoat_CA.text = 'Kích hoạt'
          this.controls.tsbtn_KichHoat_CA.toolTipText = 'Kích hoạt dịch vụ'
        }
        if (vloaitb_id == LOAIHINH_THUEBAO.VNPT_SLL) {
          this.controls.tsbXemCMS.text = 'Thông Tin SMS'
          this.controls.tsbXemCMS.toolTipText = 'Xem tổng số SMS nhà trường hiện đang còn dư'
          this.controls.tsbtn_KichHoat_CA.toolTipText = 'Đăng ký/ hủy sổ liên lạc'
        } else {
          this.controls.tsbXemCMS.text = 'Xem CMS'
          this.controls.tsbXemCMS.toolTipText = 'Xem thông tin đối soát CMS'
          this.controls.tsbtn_KichHoat_CA.toolTipText = 'Kích hoạt dịch vụ'
        }
        this.controls.tsbtnHuyKH.visible = false
        if (await this.PHAILAM(this.state.luong_id, 'TSBTNHUYKH')) {
          this.controls.tsbtnHuyKH.visible = treemenuVue
        }
        if (kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString()) this.controls.tsbtnHuyKH.enabled = true
        else this.controls.tsbtnHuyKH.enabled = false
        //#region CDLH
        if (loaihd_id == LOAI_HOPDONG.CHUYENDOI_LH) {
          //LOAI_HOPDONG.CHUYENDOI_LH = 6
          if ((await this.PHAILAM(this.state.luong_id, 'KICHHOAT_TD')) == true) {
            if (ngay_ht_gp) {
              this.controls.dtpNgayBG.value = ngay_ht_gp
              this.controls.dtpNgayBG.enabled = true
              if (this.state.selectedPhieu.status == 5)
                //TRANGTHAI_DONGBO.DONGBO_CM = 5
                this.controls.tsbtnGiaoPhieu.enabled = true
              else this.controls.tsbtnGiaoPhieu.enabled = false
            } else {
              if ((await this.PHAILAM(this.state.luong_id, 'GIAOVIEC')) == false) {
                this.controls.dtpNgayBG.enabled = true
                //this.controls.dtpNgayGV.value =  ;
              } else {
                this.controls.dtpNgayBG.enabled = false
              }
              this.controls.tsbtnGiaoPhieu.enabled = false
            }
          }
          //Nếu phải giao phiếu
          if ((await this.PHAILAM(this.state.luong_id, 'GIAOPHIEU')) == true) {
            if (ngay_ht_gp) {
              this.controls.dtpNgayBG.enabled = true
              this.controls.dtpNgayBG.value = ngay_ht_gp
              if ((await this.PHAILAM(this.state.luong_id, 'KICHHOAT_TD')) == true) {
                if (kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString()) this.controls.tsbtnGiaoPhieu.enabled = true
                else this.controls.tsbtnGiaoPhieu.enabled = false
              } else this.controls.tsbtnGiaoPhieu.enabled = true
            } else {
              if ((await this.PHAILAM(this.state.luong_id, 'GIAOVIEC')) == false) {
                this.controls.dtpNgayBG.enabled = true
                this.controls.dtpNgayBG.value = ngay_bg //tt_nd.ngay_cn.AddSeconds(30);
              } else {
                this.controls.dtpNgayBG.enabled = false
                this.controls.dtpNgayBG.value = ngay_bg //tt_nd.ngay_cn.AddSeconds(30);
              }
              this.controls.tsbtnGiaoPhieu.enabled = false
            }
          }
          //Nếu phải hoàn công
          if ((await this.PHAILAM(this.state.luong_id, 'HOANCONG')) == true) {
            if (ngay_ht_gp && kt_status == TRANGTHAI_DONGBO.DONGBO_CM) {
              this.controls.dtpNgayBG.enabled = true
              this.controls.dtpNgayBG.value = ngay_ht_gp
              this.controls.tsbtnHoanCong.enabled = true
            } else {
              if ((await this.PHAILAM(this.state.luong_id, 'GIAOVIEC')) == false) {
                this.controls.dtpNgayBG.enabled = true
                this.controls.dtpNgayBG.value = ngay_bg
              } else {
                this.controls.dtpNgayBG.enabled = false
                this.controls.dtpNgayBG.value = ngay_bg
              }
              this.controls.tsbtnHoanCong.enabled = false
            }
            console.log('set tsbtnHoanCong4', this.controls.tsbtnHoanCong.enabled)
          }
        }
        //#endregion CDLH
        //#region Đặt mới + Khôi phục + Gia han
        if (loaihd_id == LOAI_HOPDONG.DAT_MOI * 1 || loaihd_id == LOAI_HOPDONG.KHOIPHUC_SD * 1 || loaihd_id == LOAI_HOPDONG.GIAHAN * 1 || loaihd_id == LOAI_HOPDONG.THAY_DOI_DV * 1) {
          if ((await this.PHAILAM(this.state.luong_id, 'RA_KICH_HOAT')) == true) {
            if (this.state.vloaitb_id == LOAIHINH_THUEBAO.VNPT_SLL) {
              if (kt_status == TRANGTHAI_DONGBO.KICH_HOAT_CA.toString()) {
                this.controls.tsbtnChapNhan.enabled = true
              } else this.controls.tsbtnChapNhan.enabled = false
            }
          }
          //-------
          if ((await this.PHAILAM(this.state.luong_id, 'KICHHOAT_TD')) == true) {
            if (ngay_ht_gp) {
              this.controls.dtpNgayBG.enabled = true
              this.controls.dtpNgayBG.value = ngay_ht_gp
              if (kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString()) this.controls.tsbtnGiaoPhieu.enabled = true
              else this.controls.tsbtnGiaoPhieu.enabled = false
            }
          } else {
            if ((await this.PHAILAM(this.state.luong_id, 'GIAOVIEC')) == false) {
              this.controls.dtpNgayBG.enabled = true
              this.controls.dtpNgayBG.value = ngay_bg //tt_nd.ngay_cn.AddSeconds(30);
            } else {
              this.controls.dtpNgayBG.enabled = false
              this.controls.dtpNgayGV.value = ngay_cn
              this.controls.dtpNgayBG.value = ngay_bg //tt_nd.ngay_cn.AddSeconds(30);
            }
            this.controls.tsbtnGiaoPhieu.enabled = false
          }
          //-------
          //Nếu phải giao phiếu
          if ((await this.PHAILAM(this.state.luong_id, 'GIAOPHIEU')) == true) {
            if (ngay_ht_gp) {
              this.controls.dtpNgayBG.enabled = true
              this.controls.dtpNgayBG.value = ngay_ht_gp
              this.controls.tsbtnGiaoPhieu.enabled = false
              if ((await this.PHAILAM(this.state.luong_id, 'KICHHOAT_TD')) == true) {
                if (kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString()) {
                  this.controls.tsbtnGiaoPhieu.enabled = true
                } else this.controls.tsbtnGiaoPhieu.enabled = false
              } else {
                this.controls.tsbtnGiaoPhieu.enabled = true
              }
            } else {
              if ((await this.PHAILAM(this.state.luong_id, 'GIAOVIEC')) == false) {
                this.controls.dtpNgayBG.enabled = true
                this.controls.dtpNgayBG.value = ngay_cn
              } else {
                this.controls.dtpNgayBG.enabled = false
                this.controls.dtpNgayGV.value = ngay_cn
                this.controls.dtpNgayBG.value = ngay_bg
              }
              this.controls.tsbtnGiaoPhieu.enabled = false
            }
          }
          //Nếu phải hoàn công
          if ((await this.PHAILAM(this.state.luong_id, 'HOANCONG')) == true) {
            var trangthai_db = ''
            if (this.state.vloaitb_id == LOAIHINH_THUEBAO.FSECURE) trangthai_db = TRANGTHAI_DONGBO.DONGBO_CM.toString()
            if (this.state.vloaitb_id == LOAIHINH_THUEBAO.CA && this.CAConstanst.isCADev == true) {
              trangthai_db = TRANGTHAI_DONGBO.DONGBO_CM.toString()
            }
            if (this.state.vloaitb_id == LOAIHINH_THUEBAO.IVAN) trangthai_db = TRANGTHAI_DONGBO.KICH_HOAT_CA.toString()
            if (kt_status == '0') {
              kt_status = ''
            }
            if (ngay_ht_gp && kt_status == trangthai_db) {
              this.controls.dtpNgayBG.enabled = true
              this.controls.dtpNgayBG.value = ngay_ht_gp
              this.controls.tsbtnHoanCong.enabled = true
            } else {
              if ((await this.PHAILAM(this.state.luong_id, 'GIAOVIEC')) == false) {
                this.controls.dtpNgayBG.enabled = true
                this.controls.dtpNgayBG.value = ngay_bg
              } else {
                this.controls.dtpNgayBG.enabled = false
                this.controls.dtpNgayGV.value = ngay_cn
                this.controls.dtpNgayBG.value = ngay_bg
              }
              this.controls.tsbtnHoanCong.enabled = false
            }
            console.log('ngay_ht_gp', ngay_ht_gp)
            console.log('kt_status', kt_status)
            console.log('trangthai_db', trangthai_db)
            console.log('set tsbtnHoanCong1', this.controls.tsbtnHoanCong.enabled)
          }
          if ((await this.PHAILAM(this.state.luong_id, 'HOANTHIENHOSO')) == true) {
            if (ngay_ht_gp) {
              this.controls.dtpNgayBG.enabled = true
              this.controls.dtpNgayBG.value = ngay_ht_gp
              this.controls.tsbtnHoanThienHS.enabled = true
            } else {
              if ((await this.PHAILAM(this.state.luong_id, 'GIAOVIEC')) == false) {
                this.controls.dtpNgayBG.enabled = true
                this.controls.dtpNgayBG.value = ngay_bg
              } else {
                this.controls.dtpNgayBG.enabled = false
                this.controls.dtpNgayGV.value = ngay_cn
                this.controls.dtpNgayBG.value = ngay_bg
              }
              this.controls.tsbtnHoanThienHS.enabled = false
            }
          }
          if ((await this.PHAILAM(this.state.luong_id, 'KichHoat_KAS')) == true || (await this.PHAILAM(this.state.luong_id, 'RA_KICH_HOAT')) == true) {
            if (kt_status == trangthai_db) {
              //trangthai_db == 5
              this.controls.tsbtnHoanThienHS.enabled = true
            } else {
              this.controls.tsbtnHoanThienHS.enabled = false
            }
          }
        }
        //#region Đặt mới + Khôi phục + Gia hạn
        //#region Di Chuyển
        else if (loaihd_id == LOAI_HOPDONG.DI_CHUYEN * 1) {
          if ((await this.PHAILAM(this.state.luong_id, 'KICHHOAT_TD')) == true) {
            if (ngay_ht_gp) {
              this.controls.dtpNgayBG.enabled = true
              this.controls.dtpNgayBG.value = ngay_ht_gp
              if (kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString()) this.controls.tsbtnGiaoPhieu.enabled = true
              else this.controls.tsbtnGiaoPhieu.enabled = false
            }
          } else {
            if ((await this.PHAILAM(this.state.luong_id, 'GIAOVIEC')) == false) {
              this.controls.dtpNgayBG.enabled = true
              this.controls.dtpNgayBG.value = ngay_bg
            } else {
              this.controls.dtpNgayBG.enabled = false
              this.controls.dtpNgayGV.value = ngay_cn
              this.controls.dtpNgayBG.value = ngay_bg
            }
            this.controls.tsbtnGiaoPhieu.enabled = false
          }
          //Nếu phải giao phiếu
          if ((await this.PHAILAM(this.state.luong_id, 'GIAOPHIEU')) == true) {
            if (ngay_ht_gp) {
              this.controls.dtpNgayBG.enabled = true
              this.controls.dtpNgayBG.value = ngay_ht_gp
              if ((await this.PHAILAM(this.state.luong_id, 'KICHHOAT_TD')) == true) {
                if (kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString()) this.controls.tsbtnGiaoPhieu.enabled = true
                else this.controls.tsbtnGiaoPhieu.enabled = false
              } else this.controls.tsbtnGiaoPhieu.enabled = true
            } else {
              if ((await this.PHAILAM(this.state.luong_id, 'GIAOVIEC')) == false) {
                this.controls.dtpNgayBG.enabled = true
                this.controls.dtpNgayBG.value = ngay_cn //tt_nd.ngay_cn.AddSeconds(30);
              } else {
                this.controls.dtpNgayBG.enabled = false
                this.controls.dtpNgayBG.value = ngay_bg //tt_nd.ngay_cn.AddSeconds(30);
              }
              this.controls.tsbtnGiaoPhieu.enabled = false
            }
          }
          //Nếu phải hoàn công
          if ((await this.PHAILAM(this.state.luong_id, 'HOANCONG')) == true) {
            var trangthai_db = ''
            if (dichvuvt_id == DICHVU_VIENTHONG.MEGA_EYES) trangthai_db = ''
            else if (this.state.vloaitb_id == LOAIHINH_THUEBAO.IOFFICE) trangthai_db = '0'
            else trangthai_db = TRANGTHAI_DONGBO.DONGBO_CM.toString()
            if (this.state.vloaitb_id == LOAIHINH_THUEBAO.CA && this.CAConstanst.isCADev == true) {
              trangthai_db = TRANGTHAI_DONGBO.DONGBO_CM.toString()
            }
            if (kt_status == '0') {
              kt_status = ''
            }
            if (ngay_ht_gp && kt_status == trangthai_db) {
              this.controls.dtpNgayBG.enabled = true
              this.controls.dtpNgayBG.value = ngay_ht_gp
              this.controls.tsbtnHoanCong.enabled = true
            } else {
              if ((await this.PHAILAM(this.state.luong_id, 'GIAOVIEC')) == false) {
                this.controls.dtpNgayBG.enabled = true
                this.controls.dtpNgayBG.value = ngay_bg
              } else {
                this.controls.dtpNgayBG.enabled = false
                this.controls.dtpNgayGV.value = ngay_cn
                this.controls.dtpNgayBG.value = ngay_bg
              }
              this.controls.tsbtnHoanCong.enabled = false
            }
            console.log('ngay_ht_gp', ngay_ht_gp)
            console.log('kt_status', kt_status)
            console.log('trangthai_db', trangthai_db)
            console.log('set tsbtnHoanCong2', this.controls.tsbtnHoanCong.enabled)
          }
          if ((await this.PHAILAM(this.state.luong_id, 'HOANTHIENHOSO')) == true) {
            if (ngay_ht_gp) {
              this.controls.dtpNgayBG.enabled = true
              this.controls.dtpNgayBG.Value = ngay_ht_gp
              this.controls.tsbtnHoanThienHS.enabled = true
            } else {
              if ((await this.PHAILAM(this.state.luong_id, 'GIAOVIEC')) == false) {
                this.controls.dtpNgayBG.enabled = true
                this.controls.dtpNgayBG.value = ngay_bg
              } else {
                this.controls.dtpNgayBG.enabled = false
                this.controls.dtpNgayGV.value = ngay_cn
                this.controls.dtpNgayBG.value = ngay_bg
              }
              this.controls.tsbtnHoanThienHS.enabled = false
            }
          }
        }
        //#endregion Di chuyển
        //#region thu_hoi_ca
        else if (loaihd_id == LOAI_HOPDONG.CHAMDUT_SD * 1 || loaihd_id == LOAI_HOPDONG.THAY_DOI_DV * 1) {
          if ((await this.PHAILAM(this.state.luong_id, 'KICHHOAT_TD')) == true) {
            if (ngay_ht_gp) {
              this.controls.dtpNgayBG.enabled = true
              this.controls.dtpNgayBG.value = ngay_ht_gp
              if (kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString()) this.controls.tsbtnGiaoPhieu.enabled = true
              else this.controls.tsbtnGiaoPhieu.enabled = false
            }
          } else {
            if ((await this.PHAILAM(this.state.luong_id, 'GIAOVIEC')) == false) {
              this.controls.dtpNgayBG.enabled = true
              this.controls.dtpNgayBG.value = ngay_bg
            } else {
              this.controls.dtpNgayBG.enabled = false
              this.controls.dtpNgayGV.value = ngay_cn
              this.controls.dtpNgayBG.value = ngay_bg
            }
            this.controls.tsbtnGiaoPhieu.enabled = false
          }
          //Nếu phải giao phiếu
          if ((await this.PHAILAM(this.state.luong_id, 'GIAOPHIEU')) == true) {
            if (ngay_ht_gp) {
              this.controls.dtpNgayBG.enabled = true
              this.controls.dtpNgayBG.value = ngay_ht_gp
              if ((await this.PHAILAM(this.state.luong_id, 'KICHHOAT_TD')) == true) {
                if (kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString()) this.controls.tsbtnGiaoPhieu.enabled = true
                else this.controls.tsbtnGiaoPhieu.enabled = false
              } else this.controls.tsbtnGiaoPhieu.enabled = true
            } else {
              if ((await this.PHAILAM(this.state.luong_id, 'GIAOVIEC')) == false) {
                this.controls.dtpNgayBG.enabled = true
                this.controls.dtpNgayBG.value = ngay_cn //tt_nd.ngay_cn.AddSeconds(30);
              } else {
                this.controls.dtpNgayBG.enabled = false
                this.controls.dtpNgayBG.value = ngay_bg //tt_nd.ngay_cn.AddSeconds(30);
              }
              this.controls.tsbtnGiaoPhieu.enabled = false
            }
          }
          //Nếu phải hoàn công
          if ((await this.PHAILAM(this.state.luong_id, 'HOANCONG')) == true) {
            var trangthai_db = ''
            if (dichvuvt_id == DICHVU_VIENTHONG.MEGA_EYES) trangthai_db = ''
            else if (this.state.vloaitb_id == LOAIHINH_THUEBAO.IOFFICE) trangthai_db = '0'
            else trangthai_db = TRANGTHAI_DONGBO.DONGBO_CM.toString()
            if (kt_status == '0') {
              kt_status = ''
            }
            if (ngay_ht_gp && kt_status == trangthai_db) {
              this.controls.dtpNgayBG.enabled = true
              this.controls.dtpNgayBG.value = ngay_ht_gp
              this.controls.tsbtnHoanCong.enabled = true
            } else {
              if ((await this.PHAILAM(this.state.luong_id, 'GIAOVIEC')) == false) {
                this.controls.dtpNgayBG.enabled = true
                this.controls.dtpNgayBG.value = ngay_bg
              } else {
                this.controls.dtpNgayBG.enabled = false
                this.controls.dtpNgayGV.value = ngay_cn
                this.controls.dtpNgayBG.value = ngay_bg
              }
              this.controls.tsbtnHoanCong.enabled = false
            }
            console.log('ngay_ht_gp', ngay_ht_gp)
            console.log('kt_status', kt_status)
            console.log('trangthai_db', trangthai_db)
            console.log('set tsbtnHoanCong3', this.controls.tsbtnHoanCong.enabled)
          }
          if ((await this.PHAILAM(this.state.luong_id, 'HOANTHIENHOSO')) == true) {
            if (ngay_ht_gp) {
              this.controls.dtpNgayBG.enabled = true
              this.controls.dtpNgayBG.Value = ngay_ht_gp
              this.controls.tsbtnHoanThienHS.enabled = true
            } else {
              if ((await this.PHAILAM(this.state.luong_id, 'GIAOVIEC')) == false) {
                this.controls.dtpNgayBG.enabled = true
                this.controls.dtpNgayBG.value = ngay_bg
              } else {
                this.controls.dtpNgayBG.enabled = false
                this.controls.dtpNgayGV.value = ngay_cn
                this.controls.dtpNgayBG.value = ngay_bg
              }
              this.controls.tsbtnHoanThienHS.enabled = false
            }
          }
        }
        //#endregion thu_hoi_ca
        //#region KICHHOAT_TD
        if ((await this.PHAILAM(this.state.luong_id, 'KICHHOAT_TD')) == true) {
          if (kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString()) this.controls.tsbtnKichHoat.enabled = false
          else this.controls.tsbtnKichHoat.enabled = true
        } else this.controls.tsbtnKichHoat.enabled = false
        if ((await this.PHAILAM(this.state.luong_id, 'GIAOVIEC')) == false) {
          if ((await this.PHAILAM(this.state.luong_id, 'HT_NGAY_GV_NGAY_HT')) == false) {
            //this.controls.dtpNgayBG.enabled = false;
            //this.controls.dtpNgayGV.enabled = false;
            //this.controls.chkNgayGV.enabled = false;
            //this.controls.chkNgayBG.enabled = false;
          } else {
            this.controls.dtpNgayBG.enabled = true
            this.controls.dtpNgayGV.enabled = true
            this.controls.chkNgayGV.enabled = true
            this.controls.chkNgayBG.enabled = true
          }
        } else {
          this.controls.dtpNgayBG.enabled = true
          this.controls.dtpNgayGV.enabled = true
          this.controls.chkNgayGV.enabled = true
          this.controls.chkNgayBG.enabled = true
        }
        //#endregion KICHHOAT_TD
        //#region chuan_hoa_ca
        if (loaihd_id == LOAI_HOPDONG.KY_LAI_HD_GOC * 1) {
          this.controls.tsbtnGiaoPhieu.enabled = true
          if (this.listbox.ds_quytrinh.value * 1 == 8806) {
            if ((await this.PHAILAM(this.state.luong_id, 'HOANTHIENHOSO')) == true) {
              if (ngay_ht_gp) {
                this.controls.dtpNgayBG.enabled = true
                this.controls.dtpNgayBG.value = ngay_ht_gp
                this.controls.tsbtnHoanThienHS.enabled = true
              }
            }
            //neu phai giao phieu
            if ((await this.PHAILAM(this.state.luong_id, 'GIAOPHIEU')) == true) {
              if (ngay_ht_gp) {
                this.controls.dtpNgayBG.enabled = true
                this.controls.dtpNgayBG.value = ngay_ht_gp
                if ((await this.PHAILAM(this.state.luong_id, 'CA_DUYET_TAO_THUC_THE')) == true) {
                  if (kt_status == TRANGTHAI_DONGBO.DONGBO_SERVICE.toString()) this.controls.tsbtnGiaoPhieu.enabled = true
                  else this.controls.tsbtnGiaoPhieu.enabled = false
                } else this.controls.tsbtnGiaoPhieu.enabled = true
              } else {
                this.controls.tsbtnGiaoPhieu.enabled = false
              }
            }
          }
        }
        //#region Ban_ThietBi_CNTT
        if ((await this.PHAILAM(this.state.luong_id, 'HOANTHIENHOSO')) == true) {
          if (ngay_ht_gp) {
            this.controls.dtpNgayBG.enabled = true
            this.controls.dtpNgayBG.value = ngay_ht_gp
            this.controls.tsbtnHoanThienHS.enabled = true
          }
        }
        //#endregion Ban_ThietBi_CNTT
        //#endregion chuan_hoa_ca
        //#region hoangpkn : 05/08/2015 : Bổ sung chặn theo yc a Thắng
        if (loaihd_id == LOAI_HOPDONG.DAT_MOI || loaihd_id == LOAI_HOPDONG.DI_CHUYEN) {
          if ((await this.PHAILAM(this.state.luong_id, 'KICHHOAT_TD')) && (await this.PHAILAM(this.state.luong_id, 'CAPNHAT_CAP')) == true) {
            if (dr['chan'] && dr['chan'].toString() == '1') this.controls.tsbtnKichHoat.enabled = false
            else {
              if (kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString()) this.controls.tsbtnKichHoat.enabled = false
              else this.controls.tsbtnKichHoat.enabled = true
            }
          }
        }
        //#endregion hoàng
        //#region hoangpkn : 08/04/2021. Kiểm tra xem có phải phiếu phối hợp không ? Phiếu phối hợp thì chỉ được ấn nút Hoàn thành. update ttph_id = 4
        if ((await this.PHAILAM(this.state.luong_id, 'HOANTHANH_PHIEU_CNTT')) == true) {
          var cnt = (await this.kt_phieu_phoihop(this.state.phieu_id, this.state.hdtb_id)) * 1
          if (cnt == 0) {
            // ko phải phiếu phối hợp --> ẩn nút Hoàn thành đi
            this.controls.tsbtnHoanThanh.enabled = false
          } // Nếu là phiếu phối hợp: hiển thị nút Hoàn thành + ẩn nút Giao phiếu, Hoàn Công, Hoàn thiện
          else {
            this.controls.tsbtnGiaoPhieu.enabled = false
            this.controls.tsbtnHoanCong.enabled = false
            this.controls.tsbtnHoanThienHS.enabled = false
            if (ngay_ht_gp) this.controls.tsbtnHoanThanh.enabled = true
            else this.controls.tsbtnHoanThanh.enabled = false
          }
        }
        //#endregion  hoàng
        //#region Lấy danh sách thuê bao lắp kèm
        if (this.state.selectedPhieu.madoicap) {
          var dataPost = {
            vhdtb_id: this.state.hdtb_id,
            vmadoicap: this.state.selectedPhieu.madoicap
          }
          await CompleteProfileAPI.lay_thuebao_lapkem(this.axios, dataPost).then((res) => {
            this.state.thuebao_lapkem = res.data.data
          })
        }
        //#endregion Lấy danh sách thuê bao lắp kèm
        //#region Hiển thị nút kích hoạt
        try {
          if (this.state.vloaitb_id == LOAIHINH_THUEBAO.IVAN) {
            let httb_id = this.state.hdtb_id
            let kt_hoancong = this.GetData(
              await api.fn_kiemtra_trangthai_kichhoat_hoancong(this.axios, {
                vhdtb_id: httb_id,
                hdtb_id: httb_id,
                vluong_id: this.state.luong_id,
                luong_id: this.state.luong_id
              })
            )
            this.controls.tsbtnHoanThienHS.enabled = kt_hoancong === 'OK'
          }
        } catch (ex) {
          console.error(ex)
        }
        //#endregion
        await this.hienthi_tab_dvcntt()
        this.HienThiDSNV(await fn.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.axios, this.state.phieu_id, this.tt_nd.nhanvien_id, 2))
        this.HienThiDVGT(await fn.LAY_DS_DANGKY_DVGT(this.axios, this.state.hdtb_id))
        await this.hienthi_tab_mua_tbi();
        let selectedrow_data = {
          luong_thaotac: this.state.luong_thaotac,
          luong_id: this.state.luong_id,
          loaihd_id: this.state.loaihd_id,
          kieuld_id: this.state.kieuld_id,
          hdtb_id: this.state.hdtb_id,
          thuebao_id: this.state.thuebao_id,
          ma_tinh: this.tt_nd.ma_tinh,
          nguoi_cn: this.tt_nd.nguoi_cn,
          may_cn: this.tt_nd.may_cn,
          ip_cn: this.tt_nd.ip_cn,
          ma_gd: this.state.ma_gd,
          ma_tb: this.state.ma_tb
        }
        //  this.setTsktDvCntt(this.state.tskt_dvcntt);
        this.setThongTinHDTB(selectedrow_data)
      } catch (ex) {
        ////this.loading(false)
        commonFn.showException(this, ex)
      } finally {
        this.hideLoading()
      }
    },
    async HienThiDSNV(ds) {
      if (ds.length > 0) {
        this.controls.dtpNgayGV.enabled = true
        this.controls.dtpNgayGV.value = moment(ds[0]['ngaygiao'].toString(), 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY hh:mm A') //04/03/2021 11:34:30
        this.tab_dvcntt.tt_giaoviec.grid_nhanvien.list = ds
        if (ds[0]['nhanvien_giao_id']) this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value = ds[0]['nhanvien_giao_id'].toString() * 1
        this.state.nhanvien_tc_id = 0
        if (ds[0]['nhanvien_th_id']) this.state.nhanvien_tc_id = ds[0]['nhanvien_th_id'].toString() * 1
        this.state.congviec_th = 0
        if (ds[0]['nhiemvu']) this.state.congviec_th = ds[0]['nhiemvu'].toString()
      } else {
        this.tab_dvcntt.tt_giaoviec.grid_nhanvien.list = null
        if ((await this.PHAILAM(this.state.luong_id, 'GIAOVIEC')) == false) {
          this.controls.dtpNgayGV.enabled = true
          this.controls.dtpNgayGV.value = this.tt_nd.ngay_cn
        } else {
          this.controls.dtpNgayGV.enabled = false
          this.controls.dtpNgayGV.value = this.tt_nd.ngay_cn
        }
        this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value = this.tt_nd.nhanvien_id
        this.state.nhanvien_tc_id = -1
        this.state.congviec_th = ''
      }
    },
    async HienThiDVGT(ds) {
      if (ds.length > 0) {
        this.tab_dvgiatang.list = ds
      } else {
        this.tab_dvgiatang.list = null
      }
    },
    async PHAILAM(luong_id, code) {
      if (!this.state.luong_thaotac) {
        await CompleteProfileAPI.lay_luong_thaotac(this.axios, { luong_id: luong_id }).then((res) => {
          this.state.luong_thaotac = res.data.data
        })
      }
      if (!this.state.luong_thaotac) {
        this.state.luong_thaotac = []
        console.warn('PHAILAM luong_thaotac is empty!')
      }
      return this.state.luong_thaotac.findIndex((item) => item.enable == 1 && item.code == code) >= 0
    },
    //Kiểm tra xem có phải phiếu phối hợp không
    async kt_phieu_phoihop(phieu_id, hdtb_id) {
      var kieuphoihop = 0
      await CompleteProfileAPI.kt_phieu_phoihop(this.axios, {
        vphieu_id: phieu_id,
        vhdtb_id: hdtb_id
      }).then((res) => {
        kieuphoihop = res.data.data
      })
      return kieuphoihop
    },
    async HienThiDanhSachHDTB() {
      try {
        this.ClearForm()
        this.CLEAR()
        let loc_nv = 0
        if ((await this.PHAILAM(this.state.luong_id, 'LOCTHEO_NV')) == true) loc_nv = 1
        var dataPost = {}
        dataPost.vphieu_id = 0
        dataPost.vhuonggiao_id = 0 //huonggiao_id DB yêu cầu set = 0
        dataPost.vtthd_id = this.state.tthd_id * 1
        dataPost.vnhanvien_id = this.tt_nd.nhanvien_id * 1
        dataPost.vma_gd = '0' // this.state.ma_gd; DB yêu cầu set = 0
        dataPost.vloaihd_id = this.listbox.ds_loaihd.value * 1
        dataPost.vkieu_id = -1
        dataPost.vloai_id = String(this.state.lay_phieu_moi).toLowerCase() === 'true' ? 0 : 1
        dataPost.vloc_nv = loc_nv
        dataPost.vdichvuvt_id = this.state.dsdichvuvt_id
        dataPost.dsdichvuvt_id = this.state.dsdichvuvt_id
        //this.loading(true)
        await CompleteProfileAPI.lay_ds_phieu_hc_dv_cntt_v3(this.axios, dataPost)
          .then((res) => {
            let list = res.data.data
            list = commonFn.LowerCasePropertyList(list)
            if (list && list.length > 0 && this.state.loc_nv == true) {
              list = list.filter((x) => x.nhanvien_id * 1 == this.tt_nd.nhanvien_id * 1)
            }
            this.datagrid.ds_hdtb.list = list
          })
          .catch((ex) => {
            ////this.loading(false)
            commonFn.showException(this, ex)
          })
      } catch (ex) {
        ////this.loading(false)
        commonFn.showException(this, ex)
      } finally {
        this.hideLoading()
      }
      if (!this.datagrid.ds_hdtb.list || this.datagrid.ds_hdtb.list.length == 0) this.state.isPageLoad = false
      this.hideLoading()
    },
    ClearForm() {
      this.state.hdtb_id = 0
      this.state.phieu_id = 0
      this.state.selectedPhieu = {}
      this.state.tongtien = 0
      this.state.nhanvien = ''
      this.state.nd_tra = ''
      this.diachiLD = {}
      this.tab_dvcntt.tt_giaoviec.tt_hdlapdat = null
      this.datagrid.ds_hdtb.list = []
      this.tab_dvcntt.list = []
      this.tab_dvcntt.tt_giaoviec.grid_nhanvien.list = []
      this.controls.dtpNgayGV.enabled = false
      this.controls.dtpNgayBG.enabled = false
      this.controls.dtpNgayGV.value = moment(new Date()).format('DD/MM/YYYY hh:mm A')
      this.controls.dtpNgayBG.value = moment(new Date()).format('DD/MM/YYYY hh:mm A')
      this.tab_it360.list = []
      this.tab_ttgoicuoc.list = []
      this.tab_dvgiatang.list = []
      this.tab_tthicong.list = []
      this.tab_ctthicong.list = []
      this.state.khachhanglon = ''
      this.controls.tsbtnHoanCong.enabled = false
      this.controls.tsbtnKichHoat.enabled = false
      this.controls.tsbtnHoanThienHS.enabled = false
      this.controls.tsbtnChapNhan.enabled = false
      this.controls.tsbtnTraPhieu.enabled = false
    },
    async CLEAR() {
      this.state.vkey_ht = ''
      this.state.vid_ht = ''
      this.state.vproject_id = ''
      this.state.txtSummary = ''
      this.state.txtDescription = ''
      this.state.txtReporter = ''
      this.state.txtIssuseType = ''
      this.controls.dtpResolved.value = null
      this.controls.dtpDueDate.value = null
      this.state.trangthai_iss = ''
      this.state.btnAssignee = ''
      this.listbox.ds_trangthai_jira.value = null
    },
    async hienthi_tab_dvcntt() {
      if (!this.state.selectedPhieu) return
      var dataPost = {
        vloaitb_id: this.state.selectedPhieu.loaitb_id,
        vten_bang: 'HDTB_CNTT',
        vopt_list: '00000'
      }
      try {
        var res = null
        await CompleteProfileAPI.lay_danhsach_thuoctinh_v3_tichhop_sql(this.axios, dataPost).then((resP) => {
          res = resP
        })
        this.tab_dvcntt.list = res.data.data.filter((v, i, a) => a.findIndex((t) => t.field_name === v.field_name) === i)
        this.tab_dvcntt.list.forEach((item) => {
          if (item.result_sql && item.result_sql.length > 0)
            item.result_sql = item.result_sql.map((x) => ({
              id: x[item.parent_keyfield],
              text: x[item.parent_descfield]
            }))
        })
        //Lấy danh sách HDTB CNTT theo hdtb_id
        CompleteProfileAPI.lay_ds_hdtb_cntt_theo_hdtb_id(this.axios, {
          in_hdtb_id: this.state.selectedPhieu.hdtb_id
        }).then((res) => {
          //this.tab_dvcntt.list
          if (this.tab_dvcntt.list && this.tab_dvcntt.list.length > 0 && res.data.message == 'Success' && res.data.data.length > 0) {
            //  this.state.tskt_dvcntt = res.data.data;
            this.setTsktDvCntt(res.data.data)
            this.tab_dvcntt.list.forEach((item) => {
              if (item.field_name.toLowerCase() == 'ma_tb') {
                item.field_value = this.state.selectedPhieu.ma_tb
              } else {
                item.field_value = res.data.data[0][item.field_name.toLowerCase()]
                if (item.att == 2) {
                  item.field_value = moment(item.field_value, 'YYYY-MM-DD').format('DD/MM/YYYY')
                }
                if (item.att == 7) {
                  item.field_value = item.field_value == '1'
                }
                //mucdich_ids
                if (item.field_name && item.field_name.toUpperCase() == 'MUCDICH_IDS') {
                  if (item.field_value) {
                    item.field_value = item.field_value.split(',')
                  } else {
                    item.field_value = []
                  }
                }
              }
            })
          }
        })
      } catch (ex) {
        ////this.loading(false)
        commonFn.showException(this, ex)
      } finally {
        this.hideLoading()
      }
    },
    async hienthi_tab_dvgiatang() {
      try {
        var dataPost = {
          vhdtb_id: this.state.selectedPhieu.hdtb_id
        }
        await CompleteProfileAPI.lay_ds_dangky_dvgt(this.axios, dataPost).then((res) => {
          this.tab_dvgiatang.list = res.data.data
        })
        await this.hienthi_tab_mua_tbi();
      } catch (ex) {
        commonFn.showException(this, ex)
      }
    },
    async hienthi_tab_mua_tbi() {
      try {
        var dataPost = {
          HDTB_ID: this.state.selectedPhieu.hdtb_id
        }
        let data = await api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
          p_nghiepvu: 'SP_LAY_DS_CT_MUA_TBI',
          p_ds_para: JSON.stringify(dataPost)
        });
        this.tab_mua_tbi.list = data.data.data;

      } catch (ex) {
        commonFn.showException(this, ex)
      }
    },
    async HienThiGiaoDien(luong_id) {
      this.controls.tsbtnTraPhieu.visible = false
      this.controls.tsbtnHoanCong.visible = false
      this.controls.tsbtnHoanThienHS.visible = false
      this.controls.tsbtnGiaoPhieu.visible = false
      this.controls.tsbtnGiaoViec.visible = false
      this.controls.tsbtnKichHoat.visible = false
      this.controls.tsbtn_KichHoat_CA.visible = false
      this.controls.tsbtnVatTu.visible = false
      this.controls.tsbtnGiaoViec_KBB.visible = false
      this.controls.tsbtnCreatIssue.visible = false
      this.controls.tsbThuHoiCTS.visible = false
      this.controls.tbs_giahan_ca.visible = false
      this.controls.tsbXemCMS.visible = false
      this.controls.tsbXemHS.visible = false
      this.controls.tsbtn_Duyet_CA.visible = false
      this.controls.tsbtn_khong_duyet_CA.visible = false
      this.controls.tsbtnGiaoPhieuPhoiHop.visible = false
      this.controls.tsbtnHoanThanh.visible = false
      this.controls.tsbtnDongBoCMS.visible = false
      this.controls.tsbtnDongBoHS.visible = false
      this.controls.tsbtnGuiHSCMS.visible = false
      this.controls.tsbtnXanNhanBT.visible = false
      this.controls.tsbtnLayThongTinSmartCMS.visible = false
      this.controls.tsbtnKiemTraCA.visible = false
      this.controls.tsbTaoToken.visible = false
      this.controls.tsbtnSmartCAStatus.visible = false
      this.controls.tsbtnDuyetCA.visible = false
      try {
        if (!this.state[`luong_thaotac_${luong_id}`] || this.state[`luong_thaotac_${luong_id}`].length == 0) {
          await CompleteProfileAPI.lay_luong_thaotac(this.axios, {
            luong_id: luong_id
          }).then((res) => {
            this.state[`luong_thaotac_${luong_id}`] = res.data.data
          })
        }
        this.state.luong_thaotac = this.state[`luong_thaotac_${luong_id}`]
        if (this.state.luong_thaotac && this.state.luong_thaotac.length > 0) {
          const newObj = {
            dsThaotac: this.state.luong_thaotac.map((item) => ({ thaotac_id: item.thaotac_id }))
          }
          const datatest = this.GetData(await api.lay_ct_thaotac_control_v2(this.axios, newObj))
          const objects = []
          for (const key in datatest) {
            if (datatest.hasOwnProperty(key) && datatest[key].length > 0) {
              objects.push(...datatest[key])
            }
          }
          objects.forEach((row) => {
            try {
              var control = this.controls[row.control_name]
              if (control) {
                const _thaotac_id = row.thaotac_id
                const _enable = this.state.luong_thaotac.find((item) => item.thaotac_id === _thaotac_id).enable
                switch (control.type) {
                  case 'menu_button':
                  case 'radio':
                    control.visible = _enable == 1
                    break
                  case 'button':
                    control.enabled = _enable == 1
                    break
                  case 'textbox':
                    control.readOnly = _enable == 1
                    if (control.readOnly == true) {
                      control.bgColor = 'cornsilk'
                      control.textColor = 'maroon'
                    }
                    break
                  case 'checkBox':
                    control.enabled = _enable == 1
                    break
                  default:
                    control.enabled = _enable == 1
                }
              } else {
                console.warn('control not found: ' + row.control_name)
              }
            } catch (ex) {
              console.error(ex)
              console.log(row.control_name, this.controls[row.control_name])
            }
          })
        }
        //console.log('=========================XONG ĐOẠN HOAN TEST================================')
        /*
        if (this.state.luong_thaotac && this.state.luong_thaotac.length > 0) {
          await Promise.all(
            this.state.luong_thaotac.map(async (item) => {
              let data = await commonFn.lay_ct_thaotac_control(this.axios, item.thaotac_id)
              console.log('=========================this.state.luong_thaotac=========================')
              console.log(this.state.luong_thaotac)
              console.log('=========================data========================')
              console.log(data)
              console.log('=========================item=========================')
              console.log(item)
              data.forEach((row) => {
                try {
                  console.log('=========================row=========================')
                  console.log(row)
                  var control = this.controls[row.control_name]
                  console.log('=========================this.controls=========================')
                  console.log(this.controls)
                  //console.log('control')
                  //console.log(control)
                  if (control) {
                    switch (control.type) {
                      case 'menu_button':
                      case 'radio':
                        control.visible = item.enable == 1
                        break
                      case 'button':
                        control.enabled = item.enable == 1
                        break
                      case 'textbox':
                        control.readOnly = item.enable == 1
                        if (control.readOnly == true) {
                          control.bgColor = 'cornsilk'
                          control.textColor = 'maroon'
                        }
                        break
                      case 'checkBox':
                        control.enabled = item.enable == 1
                        break
                      default:
                        control.enabled = item.enable == 1
                    }
                  } else {
                    this.hideLoading()
                    console.warn('control not found: ' + row.control_name)
                  }
                } catch (ex) {
                  this.hideLoading()
                  console.error(ex)
                  console.log(row.control_name, this.controls[row.control_name])
                } finally {
                  this.hideLoading()
                }
              })
            })
          )
        }*/
      } catch (ex) {
        this.hideLoading()
        commonFn.showException(this, ex)
      } finally {
        this.hideLoading()
      }
    },
    //CÁC SỰ KIỆN
    async onChange_loaihd(e) {
      var loaihd_id = parseInt(e)
      if (loaihd_id == LOAI_HOPDONG.DAT_MOI || loaihd_id == LOAI_HOPDONG.DI_CHUYEN || loaihd_id == LOAI_HOPDONG.CHUYENDOI_LH) {
        this.controls.tsbtnHuyKH.visible = true
        this.controls.tsbtnHuyKH.enabled = true
      }
      try {
        //this.loading(true)
        let dataQuyTrinh = await commonFn.lay_ds_quytrinh_v2(this.axios, loaihd_id, this.state.tthd_id, this.state.dsdichvuvt_id)
        this.listbox.ds_quytrinh.list = dataQuyTrinh.map((x) => ({
          id: x.quytrinh_id,
          text: x.quytrinh
        }))
        if (this.listbox.ds_quytrinh.list && this.listbox.ds_quytrinh.list.length > 0) {
          this.listbox.ds_quytrinh.value = this.listbox.ds_quytrinh.list[0].id
          await this.onChange_quytrinh(this.listbox.ds_quytrinh.value)
        } else {
          this.listbox.ds_quytrinh.list = [{ id: -1, text: '--Không có quy trình--' }]
          this.listbox.ds_quytrinh.value = -1
          this.state.isPageLoad = false
          this.hideLoading()
          this.ClearForm()
          this.CLEAR()
          return
        }
        await this.HienThiDanhSachHDTB()
        this.hideLoading()
      } catch (ex) {
        ////this.loading(false)
        commonFn.showException(this, ex)
      } finally {
        this.hideLoading()
      }
    },
    async onChange_quytrinh(e) {
      this.state.quytrinh_id = -1
      if (!this.listbox.ds_quytrinh.list || this.listbox.ds_quytrinh.list.length <= 0) return
      if (!this.listbox.ds_quytrinh.value || this.listbox.ds_quytrinh.value * 1 <= 0) return
      this.state.quytrinh_id = this.listbox.ds_quytrinh.value * 1
      var postData = {
        quytrinh_id: this.state.quytrinh_id,
        tthd_id: this.state.tthd_id * 1
      }
      try {
        //this.loading(true)
        await CompleteProfileAPI.sp_lay_huonggiao_theo_quytrinh(this.axios, postData).then((res) => {
          this.state.ds_huonggiao = res.data.data
        })
        if (this.state.ds_huonggiao && this.state.ds_huonggiao.length > 0) {
          this.state.luong_id = this.state.ds_huonggiao[0].luong_id
          this.state.huonggiao_id = this.state.ds_huonggiao[0].huonggiao_id
          var tenhg = this.state.ds_huonggiao[0].huonggiao
          this.header.title = tenhg
          let formTitleElm = document.getElementsByClassName('main-title')
          if (formTitleElm && formTitleElm.length > 0) formTitleElm[0].innerHTML = tenhg
          await this.HienThiGiaoDien(this.state.luong_id)
          this.state.loc_nv = false
          if (await this.PHAILAM(this.state.luong_id, 'LOCTHEO_NV' == true)) {
            this.state.loc_nv = true
          }
          if (await this.PHAILAM(this.state.luong_id, 'GIAOVIEC' == false)) {
            this.controls.dtpNgayBG.enabled = true
            this.controls.dtpNgayGV.enabled = true
            this.controls.dtpNgayBG.value = this.tt_nd.ngay_bg
            this.controls.dtpNgayGV.value = this.tt_nd.ngay_cn
          }
        } else {
          this.$toast.error('Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại')
          this.hideLoading()
          return
        }
      } catch (ex) {
        ////this.loading(false)
        commonFn.showException(this, ex)
      } finally {
        this.hideLoading()
      }
    },
    onChange_phieumoi: function(e) {
      this.HienThiDanhSachHDTB()
    },
    ckbLocNhanvien_CheckedChanged(e) {
      this.state.loc_nv = e.target.checked
      if (e.target.checked == true) {
        this.datagrid.ds_hdtb.list = this.datagrid.ds_hdtb.list.filter((x) => x.nhanvien_id * 1 == this.tt_nd.nhanvien_id * 1)
      } else this.HienThiDanhSachHDTB()
    },
    btnLayTTMoi_Click: function(e) {
      this.HienThiDanhSachHDTB()
    },
    async KiemTra_DL(luong_id) {
      if (this.datagrid.ds_hdtb.list < 0) {
        this.$toast.error('Chưa có danh thuê bao. Bạn hãy kiểm tra lại! ')
        return false
      }
      var grid = this.$refs.gv_DanhSach
      var seletedRows = grid.getSelectedRecords()
      if (seletedRows.length <= 0) {
        this.$toast.error('Bạn chưa chọn thuê bao! ')
        return false
      }
      if (await this.PHAILAM(luong_id, 'GIAOVIEC')) {
        if (!this.tab_dvcntt.tt_giaoviec.grid_nhanvien.list || this.tab_dvcntt.tt_giaoviec.grid_nhanvien.list.length <= 0) {
          this.$toast.error('Hãy nhập nhân viên thực hiện  !')
          return false
        }
      }
      this.$v.$touch()
      let errorMessage = []
      if (this.controls.dtpNgayGV.enabled == false || this.controls.dtpNgayGV.value == null) {
        //this.$refs.loaiCard.focus()
        this.$toast.error('Hãy chọn ngày giao việc !')
        return false
      }
      if (this.controls.dtpNgayBG.enabled == false || this.controls.dtpNgayBG.value == null) {
        //this.$refs.loaiCard.focus()
        this.$toast.error('Hãy chọn ngày hoàn thành !')
        return false
      }
      let diff = 0
      if (String(this.controls.dtpNgayBG.enabled).toLowerCase() == 'true') {
        diff = moment(this.controls.dtpNgayBG.value, 'DD/MM/YYYY hh:mm A') - moment(this.controls.dtpNgayGV.value, 'DD/MM/YYYY hh:mm A')
        if (diff < 0) {
          this.$toast.error('Ngày giao việc không được lớn hơn ngày hoàn thành!')
          return false
        }
      }
      var thangnamsys = moment(this.state.selectedPhieu.thangnam_sys, 'DD/MM/YYYY')
      if (this.listbox.ds_loaihd.value != LOAI_HOPDONG.KHOIPHUC_SD) {
        if (parseInt(moment(this.controls.dtpNgayBG.value, 'DD/MM/YYYY hh:mm A').format('YYYYMM')) < parseInt(thangnamsys.format('YYYYMM')) && Number(this.tt_nd.phanvung_id) != 98) {
          this.$toast.error('Tháng hoàn thành không được nhỏ hơn tháng hiện tại !')
          return false
        }
      }
      diff = Number(moment(this.controls.dtpNgayBG.value, 'DD/MM/YYYY hh:mm A').format('YYYYMMDD')) - Number(thangnamsys.format('YYYYMMDD'))
      if (diff > 0) {
        this.$toast.error('Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại')
        return false
      }
      diff = parseInt(moment(this.controls.dtpNgayGV.value, 'DD/MM/YYYY hh:mm A').format('YYYYMMDD')) - parseInt(thangnamsys.format('YYYYMMDD'))
      if (diff > 0) {
        this.$toast.error('Bạn không được phép chọn ngày giao việc lớn hơn ngày hiện tại!')
        return false
      }
      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))
        return false
      } else {
        return true
      }
    },
    CheckDataError(NetItems, ItemCount, sTableName, sKeyField, sMaDoiTuong) {
      try {
        var flag = false
        var i = 0
        var TempNum = 0
        for (var k = 0; k < ItemCount; k++) {
          switch (NetItems[k].iDataType) {
            case KIEU_DL.CST_TEXT:
              if (NetItems[k].bIsNotNull && NetItems[k].sValue == '') {
                this.$toast.error('Phải nhập ' + NetItems[k].sCaption + '.')
                return true
              }
              i = NetItems[k].sValue.Length
              if (NetItems[k].bIsNotNull) {
                if (i == 0) {
                  this.$toast.error('Phải nhập ' + NetItems[k].sCaption + '.')
                  return true
                }
              }
              if (!(!NetItems[k].sMaxValue && !NetItems[k].sMinvalue)) {
                if (i > parseInt(NetItems[k].sMaxValue) || i < parseInt(NetItems[k].sMinvalue)) {
                  if (parseInt(NetItems[k].sMaxValue) > 0 && parseInt(NetItems[k].sMinvalue) > 0)
                    if (parseInt(NetItems[k].sMaxValue) > 0) {
                      this.$toast.error(NetItems[k].sCaption + ' không thể lớn hơn ' + NetItems[k].sMaxValue + ' ký tự.')
                      return true
                    } else {
                      this.$toast.error('Phải nhập ' + NetItems[k].sCaption + '.')
                      return true
                    }
                }
              }
              break
            case KIEU_DL.CST_NUMBER:
              if (NetItems[k].sValue.Length > 0 && !this.CheckIsNumber(NetItems[k].sValue)) {
                this.$toast.error(NetItems[k].sCaption + ' phải là kiểu số.')
                return true
              }
              TempNum = parseFloat(!NetItems[k].sValue ? '-1' : NetItems[k].sValue)
              if (!NetItems[k].bIsNotNull && !NetItems[k].sValue) {
                this.$toast.error('Phải nhập ' + NetItems[k].sCaption + '.')
                return true
              }
              i = NetItems[k].sValue.Length
              if (!(!NetItems[k].sMaxValue && !NetItems[k].sMinvalue)) {
                if (i > parseInt(NetItems[k].sMaxValue) || i > parseInt(NetItems[k].sMinvalue)) {
                  if (parseInt(NetItems[k].sMaxValue) > 0 && parseInt(NetItems[k].sMinvalue) > 0)
                    if (parseInt(NetItems[k].sMaxValue) > 0) {
                      this.$toast.error(NetItems[k].sCaption + ' không thể lớn hơn ' + NetItems[k].sMaxValue + ' ký tự.')
                      return true
                    }
                }
              }
              break
            case KIEU_DL.CST_DATE:
              if (!NetItems[k].sValue && NetItems[k].bIsNotNull) {
                this.$toast.error(NetItems[k].sCaption + ' phải có giá trị.')
                return true
              }
              break
            default:
              flag = false
              break
          }
          if (flag) return flag
        }
        return false
      } catch (ex) {
        commonFn.showException(this, ex)
      }
    },
    CheckIsNumber: function(input) {
      return !isNaN(input)
    },
    async CapNhat() {
      try {
        var postData = {
          phieu_id: 12045046,
          hdtb_id: 6058534,
          loaihd_id: 1,
          luong_id: 414,
          json_ttdv_cntt: [
            {
              sCaption: 'Mã thuê bao (*)',
              sFieldName: 'MA_TB',
              nFieldLength: 30,
              sValue: 'hpg_eng_00000187',
              iAtt: 0,
              iDataType: 0,
              sMaxValue: 5,
              sMinvalue: 0,
              sLookUpTableName: '',
              sLookUpKeyField: '',
              sLookUpValueField: '',
              StrSql: '',
              strDepandField: ''
            }
          ],
          json_giaophieu_nv: [
            {
              PHIEU_ID: 12045046,
              NHANVIEN_TH_ID: 12461,
              NHIEMVU: 'Đào tạo, hướng dẫn',
              GHICHU: 'ERP TEST, không thực hiện',
              NHANVIEN_GIAO_ID: 452.0,
              NGAYGIAO: '05/08/2021 16:22:09',
              SO_DT: '0913200119',
              STATUS: 0,
              NHIEMVU_ID: 367,
              KQXL_ID: null
            }
          ],
          nhanvien_hc_id: 456,
          nhanvien_gv_id: 456,
          ngay_ht: '24/09/2021',
          ngay_gv: '24/09/2021'
        }
        var phieuId = 12045046
        phieuId = this.state.phieu_id
        postData.phieu_id = phieuId
        postData.hdtb_id = this.state.hdtb_id
        postData.loaihd_id = this.state.loaihd_id
        postData.luong_id = this.state.luong_id
        postData.nhanvien_hc_id = this.tt_nd.nhanvien_id * 1
        postData.nhanvien_gv_id = this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value * 1
        postData.ngay_ht = moment(this.controls.dtpNgayBG.value, 'DD/MM/YYYY hh:mm A').format('DD/MM/YYYY HH:mm:ss')
        postData.ngay_gv = moment(this.controls.dtpNgayGV.value, 'DD/MM/YYYY hh:mm A').format('DD/MM/YYYY HH:mm:ss')
        postData.thuebao_id = this.state.thuebao_id * 1
        postData.donvi_giao_id = this.tt_nd.donvi_id
        postData.json_ttdv_cntt = this.tab_dvcntt.list.map((x) => ({
          sCaption: x.caption,
          sFieldName: x.field_name,
          nFieldLength: x.field_length,
          sValue: this.mapsValue(x),
          iAtt: x.att,
          iDataType: x.data_type,
          sMaxValue: x.max_value,
          sMinvalue: x.min_value,
          sLookUpTableName: '',
          sLookUpKeyField: '',
          sLookUpValueField: '',
          StrSql: '',
          strDepandField: ''
        }))
        postData.json_giaophieu_nv = postData.json_giaophieu_nv.map((x) => ({
          PHIEU_ID: phieuId,
          NHANVIEN_TH_ID: this.tt_nd.nhanvien_id * 1, //this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value,
          NHIEMVU: 'Khai báo thông số!',
          GHICHU: '',
          NHANVIEN_GIAO_ID: this.tt_nd.nhanvien_id,
          NGAYGIAO: moment(this.controls.dtpNgayGV.value, 'DD/MM/YYYY hh:mm A').format('DD/MM/YYYY HH:mm:ss'),
          SO_DT: '',
          STATUS: 0,
          NHIEMVU_ID: 7,
          KQXL_ID: null
        }))
        var kiemTraDL = await this.KiemTra_DL(this.state.luong_id)
        if (kiemTraDL != true) {
          return false
        }
        //kiểm tra dữ liệu
        var ItemList = this.tab_dvcntt.list
        if (this.CheckDataError(ItemList, ItemList.Length, 'HD_CNTT', 'HDTB_ID', this.state.selectedPhieu.hdtb_id)) {
          this.$toast.error('Lỗi CheckDataError')
          return false
        }
        //this.loading(true)
        var thanhcong = false
        await CompleteProfileAPI.fn_an_capnhat_hoancong_dvcntt(this.axios, postData)
          .then((res) => {
            this.hideLoading()
            if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
              thanhcong = true
              if (res.data.data != 'OK') {
                thanhcong = false
                this.$toast.error(res.data.data)
              }
            } else {
              thanhcong = false
              this.$toast.error('Lỗi cập nhật dữ liệu: ' + res.data.message + ' ' + (res.data.message_detail != null) ? res.data.message_detail : res.data.message_detail)
            }
          })
          .catch((ex) => {
            ////this.loading(false)
            commonFn.showException(this, ex)
          })
          .finally(() => {
            this.hideLoading()
          })
        //thay đổi nội dung phiếu vừa cập nhật
        if (thanhcong == true) {
          if (this.datagrid.ds_hdtb.list && this.datagrid.ds_hdtb.list.length > 0) {
            let phieuUpdated = this.datagrid.ds_hdtb.list.find((x) => x.phieu_id == postData.phieu_id)
            if (phieuUpdated) {
              phieuUpdated['ngay_ht_gp'] = moment(postData.ngay_ht, 'DD/MM/YYYY HH:mm:ss').format('YYYY-MM-DD HH:mm:ss')
            }
            console.log('phieuUpdated[ngay_ht_gp]', phieuUpdated['ngay_ht_gp'])
          }
        }
        this.hideLoading()
        return thanhcong
      } catch (ex) {
        ////this.loading(false)
        commonFn.showException(this, ex)
      } finally {
        this.hideLoading()
      }
      return false
    },
    mapsValue(x) {
      let val = x.field_value
      try {
        if (x.att == 7) {
          val = x.field_value == true || x.field_value == '1' ? '1' : '0'
          return val
        }
        if (val && val == 'Invalid date') {
          val = null
          return val
        }
        if (x && x.field_name === 'MUCDICH_IDS') {
          if (x.field_value && x.field_value.length > 0) {
            return x.field_value.join(',')
          }
        }
        return val
      } catch (e) {
        console.error('mapsValue', x, e)
        return val
      }
    },
    async tsbtnChapNhan_Click(e) {
      if (!this.state.phieu_id || this.state.phieu_id <= 0) {
        this.$toast.error('Bạn chưa chọn phiếu!')
        return
      }
      if ((await this.CapNhat()) == false) {
        return
      }
      this.$toast.success('Cập nhật dữ liệu thành công')
      this.state.vhdtb_id = this.state.hdtb_id
      this.state.vma_tb = this.state.ma_tb.trim().toLowerCase()
      //await this.HienThiDanhSachHDTB();
      this.gv_DanhSach_FocusedRowChanged(this.state.selectedPhieu)
    },
    async Kiemtra_truockhi_giaophieu(vphieu_id) {
      console.log(111111)
      console.log('HDTB_ID : ' + this.state.hdtb_id)
      console.log('Phiếu ID: ' + vphieu_id)
      console.log('Loaitb_id: ' + this.state.vloaitb_id)
      let data_check = await api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
        p_nghiepvu: 'LAY_LOAITB_ID_KIEMTRA_NHAP_ND_GIAOPHIEU',
        p_ds_para: '{"LOAITB_ID":' + this.state.vloaitb_id + '}'
      })
      console.log(data_check.data)
      if (data_check.data.data[0].ketqua == 'ok') {
        data_check = await api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
          p_nghiepvu: 'KIEMTRA_TRUOC_GIAOPHIEU',
          p_ds_para: '{"HDTB_ID":' + this.state.hdtb_id + ',PHIEU_ID: ' + vphieu_id + '}'
        })
        if (data_check.data.data[0].ketqua != 'ok') {
          this.$toast.error(data_check.data.data[0].ketqua)
          return false
        }
        return true
      }
      return true
    },
    async tsbtnGiaoPhieu_Click(e) {
      //debugger
      if (!this.state.phieu_id || this.state.phieu_id <= 0) {
        this.$toast.error('Bạn chưa chọn phiếu!')
        return
      }
      try {
        //bỏ đoạn này theo bug: https://cntt.vnpt.vn/browse/BSS-76261
        // if (await this.PHAILAM(this.state.luong_id, "DONGBO_BACKEND"))
        // {
        //     if (this.state.vloaitb_id == LOAIHINH_THUEBAO.CA || this.state.vloaitb_id == 288)
        //     {
        //         let isConfirm = await this.$bvModal
        //                                 .msgBoxConfirm("Bạn CHƯA ấn nút [Đồng bộ CMS] trước khi giao phiếu???", {
        //                                   title: "",
        //                                   size: "sm",
        //                                   okTitle: "Đồng ý",
        //                                   cancelTitle: "Hủy",
        //                                   centered: true,
        //                                 });
        //         if (isConfirm == true)
        //         {
        //             let data = {
        //               luong_thaotac: this.state.luong_thaotac,
        //               luong_id: this.state.luong_id,
        //               loaihd_id: this.state.loaihd_id,
        //               kieuld_id: this.state.kieuld_id,
        //               hdtb_id: this.state.hdtb_id,
        //               thuebao_id: this.state.thuebao_id,
        //               ma_tinh: this.tt_nd.ma_tinh,
        //             }
        //             //this.loading(true);
        //             let kq = await ca.DongBoCMS(this, data);
        //             this.$root.$toast.success(kq)
        //             //this.loading(false);
        //             return;
        //         }
        //     }
        // }
        if ((await this.KiemTra_DL(this.state.luong_id)) == false) return
        if ((await this.CapNhat()) == false) {
          return
        }
        if ((await this.Kiemtra_truockhi_giaophieu(this.state.phieu_id)) == false) {
          return
        }
        if (!this.state.selectedPhieu.ma_gd) return
        var huongGiao = await commonFn.lay_huonggiao_theo_luong_id(this.axios, this.state.luong_id)
        ////this.loading(false)
        if (!huongGiao) {
          this.$toast.error('Không tìm thấy hướng giao')
          return
        }
        var grid = this.$refs.gv_DanhSach
        var seletedRows = grid.getSelectedRecords()
        if (seletedRows.length <= 0) {
          this.$toast.error('Bạn chưa chọn thuê bao! ')
          return
        }
        this.giaoPhieuViewDialog.input = {
          vhuonggiao_id: huongGiao.huonggiao_id * 1,
          vloai_hd: this.state.loaihd_id,
          vdichvuvt_id: this.state.dichvuvt_id,
          vma_gd: this.state.selectedPhieu.ma_gd,
          vquytrinh: this.state.quytrinh_id,
          vtrangthaiphieu: 1
        }
        this.GiaoPhieuView_OnClick()
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      ////this.loading(false)
    },
    async giaophieu_success() {
      this.HienThiDanhSachHDTB()
    },
    async tsbtnGiaoPhoiHop_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.error('Hãy chọn phiếu trước!')
        return
      }
      let donvi_nhan_id = this.state.selectedPhieu.donvi_nhan_id
      let data = {
        phieu_cha_id: this.state.selectedPhieu.phieu_id,
        phieu_tinh_id: 0,
        donvi_ql_id: donvi_nhan_id,
        tinh_id: 100
      }
      this.$refs.popupGiaoPhieuNet.openDialog(data)
    },
    async tsbtnGiaoPhieuPhoiHop_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.error('Hãy chọn phiếu trước!')
        return
      }
      this.$refs.dialogGiaoPhieuPhoiHop.openDialog(this.state.phieu_id, 1)
    },
    async tsbUpHoSo_Click() {
      if (!this.state.phieu_id || this.state.phieu_id <= 0) {
        this.$toast.error('Bạn chưa chọn phiếu!')
        return
      }
      this.state.modalTitle = 'Upload hồ sơ'
      //this.popupComponent = () => import("../../profile/UploadEProfile/UploadEProfile_content.vue");
      this.popupComponentName = 'popupUploadHoSo'
      this.popupComponentAttr = {
        Tag: '1',
        ma_thue_bao: this.state.selectedPhieu.ma_tb,
        txtMaGD: this.state.selectedPhieu.ma_gd,
        setHoanCong: true
      }
      if (this.state.vloaitb_id == LOAIHINH_THUEBAO.CA) {
        this.popupComponentAttr.gioihan_loaifile = 1
        this.popupComponentAttr.vloaitb_id = this.state.vloaitb_id
      }
      //this.Popup("modalComponentsITVAS");
      this.dialogComponent = () => import('../../profile/UploadEProfile/UploadEProfile_content.vue')
      await this.dialogComponent().then((x) => {})
      this.$nextTick(() => {
        this.$refs.generalDialog.show()
      })
      console.log('tsbUpHoSo_Click', this.popupComponentAttr)
    },
    async tsbtnKichHoatVisa_Click(e) {
      var postData = {
        vhdtb_id: this.state.hdtb_id * 1,
        vstatus: TRANGTHAI_DONGBO.DONGBO_CM
      }
      try {
        let check = await this.fn_kiemtra_thongtin_hopdong()
        if (check == false) return
        //this.loading(true)
        await CompleteProfileAPI.capnhat_ngaykh_status_v2(this.axios, postData).then((response) => {
          this.hideLoading()
          if (response && response.data.message == 'Success') {
            this.$toast.success('Đồng bộ dữ liệu lên công ty dọc thành công !')
            this.gv_DanhSach_FocusedRowChanged(this.state.selectedPhieu)
          } else this.$toast.error('Lỗi kích hoạt: ' + response.data.message + ': ' + response.data.message_detail ? response.data.message_detail : '')
        })
      } catch (ex) {
        ////this.loading(false)
        commonFn.showException(this, ex)
      } finally {
        ////this.loading(false)
      }
    },
    async fn_kiemtra_thongtin_hopdong() {
      try {
        let kiemtra_thongtin = await fn_kiemtra_thongtin_hopdong(this.state.hdtb_id * 1)
        if (kiemtra_thongtin != 'OK') {
          this.$toast.error(kiemtra_thongtin)
          return false
        }
        return true
      } catch (ex) {
        console.log(ex)
      }
    },
    getDiaChi(data, datatype) {
      this[datatype].DIACHI = data.diachimoi
      this[datatype].TINH_ID = data.tinh_id
      this[datatype].QUAN_ID = data.quan_id
      this[datatype].PHUONG_ID = data.phuong_id
      this[datatype].PHO_ID = data.pho_id
      this[datatype].AP_ID = data.ap_id
      this[datatype].KHU_ID = data.khu_id
      this[datatype].DAC_DIEM_ID = data.dac_diem_id
      this[datatype].SO_NHA = data.so_nha
      this[datatype].Lat = data.lat
      this[datatype].Lng = data.lng
    },
    async getDiaChiLD(data) {
      this.getDiaChi(data, 'diachiLD')
      //lưu thông tin tọa độ
      await commonFn.CapnhatToaDoHDTB(this.axios, this.diachiLD.Lat, this.diachiLD.Lng, this.state.hdtb_id, 1)
    },
    async gv_DanhSach_FocusedRowChanged(e) {
      this.iAlert_value.trang_thai_load_hd = false
      this.ETicketData.mst = e.mst
      this.state.mst = e.mst
      this.state.selectedPhieu = e
      this.controls.tsbtnChapNhan.enabled = false
      this.controls.tsbtnTraPhieu.enabled = false
      console.log('selectedPhieu', this.state.selectedPhieu)
      try {
        if (this.datagrid.ds_hdtb.list && this.datagrid.ds_hdtb.list.length > 0) {
          this.state.phieu_id = this.state.selectedPhieu.phieu_id
          //this.loading(true);
          await this.HienThiTTHopDongTB(this.datagrid.ds_hdtb.list)
          if (this.state.SelectedTabPage == 'TabIT360') {
            await this.NAP_DS_ISSUES()
          }
          if (this.state.SelectedTabPage == 'tabTSKT') {
            await this.NAP_DS_TSKT()
          }
          if (this.state.SelectedTabPage == 'xtraTabPage2') {
            await this.NAP_GRD_TIENTRINH()
          }
          if (this.state.SelectedTabPage == 'xtraTabPage3') {
            await this.HienThiDS_PhieuTH(this.state.phieu_id)
          }
          this.controls.tsbtn_KichHoat_CA.enabled = true
          let vloaitb_id = this.state.selectedPhieu.loaitb_id
          if (vloaitb_id != LOAIHINH_THUEBAO.CA * 1 && vloaitb_id != LOAIHINH_THUEBAO.SMART_CA * 1)
            if (this.state.selectedPhieu.status == 5 || this.state.selectedPhieu.status == 6) {
              this.controls.tsbtn_KichHoat_CA.enabled = false
            }
        }
      } catch (ex) {
        ////this.loading(false)
        commonFn.showException(this, ex)
      } finally {
        this.hideLoading()
      }
      this.state.isPageLoad = false
      this.hideLoading()
    },
    async NAP_DS_ISSUES() {
      this.CLEAR()
      var dataPost = {
        hdtb_id: this.state.hdtb_id
      }
      await CompleteProfileAPI.lay_ds_issue_cua_hopdongtb(this.axios, dataPost).then((res) => {
        this.tab_it360.list = res.data.data.map((x) => {
          x.url = `<a target="_blank" href=${x.url}>${x.url}</a>`
          return x
        })
      })
    },
    async NAP_ISSUE_INFO(id) {
      this.CLEAR()
      var data = await this.Get_Issue_Info(id)
      if (!data) return
      try {
        if (data.errorMessages && data.errorMessages.length > 0) {
          this.$toast.error('Có lỗi lấy thông tin phiếu yêu cầu. ' + data.errorMessages[0])
          return
        }
        this.state.vkey_ht = data.key
        this.state.vid_ht = data.id
        this.state.vproject_id = data.fields.project.id
        this.state.txtSummary = data.key + ': ' + data.fields.summary
        this.state.txtDescription = data.fields.description
        this.state.txtReporter = data.fields.reporter.name
        this.state.txtIssuseType = data.fields.issuetype.name
        if (data.fields.resolutiondate != null) {
          this.controls.dtpResolved.value = moment(data.fields.resolutiondate, 'YYYY-MM-DD').format('DD/MM/YYYY')
        }
        this.controls.dtpDueDate.value = moment(data.fields.duedate, 'YYYY-MM-DD').format('DD/MM/YYYY')
        this.state.trangthai_iss = ''
        if (data.fields.status.name) {
          let resolution_name = data.fields.resolution ? data.fields.resolution.name.toString() : ''
          let resolution_id = data.fields.resolution ? data.fields.resolution.id.toString() : ''
          this.listbox.ds_trangthai_jira.value = data.fields.status.name.toString()
          this.listbox.resolution_name = resolution_name
          this.listbox.resolution_id = resolution_id
          this.state.trangthai_iss = data.fields.status.name.toString()
        }
        if (data.fields.assignee) {
          if (data.fields.assignee.name != null) this.state.btnAssignee = data.fields.assignee.name.toString()
          else this.state.btnAssignee = ''
        } else this.state.btnAssignee = ''
      } catch (ex) {
        commonFn.showException(this, ex)
      }
    },
    async btnDongBoClick() {
      var key = this.state.issue_id
      var data = await this.Get_Issue_Info(key)
      console.log(data)
      var kq_dongbo = await this.axios.get(`web-hopdong/vbn/fn_capnhat_trangthai_jira?vhdtb_id=${this.state.hdtb_id.toString()}&vttjr_id=${data.fields.status.id.toString()}&vresolution_id=${this.listbox.resolution_id}`)
      if (kq_dongbo.data && kq_dongbo.data.data == 'OK') {
        this.$toast.success('Đồng bộ thành công')
      } else {
        this.$toast.error('Có lỗi khi đồng bộ . Liên hệ admin để được hỗ trợ')
      }
    },
    async grvIssues_FocusedRowChanged(e) {
      if (!e) return
      //this.loading(true)
      console.log('grvIssues_FocusedRowChanged', e)
      this.state.issue_id = e.key
      await this.NAP_ISSUE_INFO(this.state.issue_id)
      this.hideLoading()
    },
    async NAP_DS_TSKT() {
      var dataPost = {
        hdtb_id: this.state.hdtb_id
      }
      await CompleteProfileAPI.lay_ds_thamso_tskt(this.axios, dataPost).then((res) => {
        this.tab_ttgoicuoc.list = res.data.data
      })
    },
    async NAP_GRD_TIENTRINH() {
      var dataPost = {
        tinhtc_id: 1,
        vhdtb_id: this.state.hdtb_id
      }
      await CompleteProfileAPI.lay_tientrinh_tinh_tc(this.axios, dataPost).then((res) => {
        this.tab_tthicong.list = res.data.data
      })
    },
    async HienThiDS_PhieuTH(phieu_id) {
      var dataPost = {
        vphieu_cha_id: phieu_id
      }
      await CompleteProfileAPI.lay_danhsach_phieu_net_con(this.axios, dataPost).then((res) => {
        this.tab_ctthicong.list = res.data.data
      })
    },
    Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
    },
    async popupClosed(val) {
      console.log('popupClosed')
      if (!val) {
        this.popupRetVal = {}
        this.ClosePopup('modalComponentsITVAS')
        return
      }
      this.ClosePopup('modalComponentsITVAS')
    },
    async tsbtnHoanCong_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.warning('Bạn chưa chọn phiếu')
        return
      }
      var postData = {
        vhdtb_id: 6058534,
        vhdkh_id: 5422987,
        vphieu_id: 12045046,
        vdonvi_giao_id: 900,
        vkyhoadon: 202101,
        vnhanvien_hc_id: 452,
        vnhanvien_gv_id: 452,
        vngayht: '24/09/2021 00:00:00',
        vngaygv: '24/09/2021 00:00:00',
        vjson_ttdv_cntt: [
          {
            sCaption: 'Mã thuê bao (*)',
            sFieldName: 'MA_TB',
            nFieldLength: 30,
            sValue: 'hpg_eng_00000187',
            iAtt: 0,
            iDataType: 0,
            sMaxValue: 5,
            sMinvalue: 0
            // sLookUpTableName: "",
            // sLookUpKeyField: "",
            // sLookUpValueField: "",
            // StrSql: "",
            // strDepandField: "",
          }
        ],
        vjson_giaophieu_nv: [
          {
            PHIEU_ID: 12045046,
            NHANVIEN_TH_ID: 12461,
            NHIEMVU: 'Đào tạo, hướng dẫn',
            GHICHU: 'ERP TEST, không thực hiện',
            NHANVIEN_GIAO_ID: 452.0,
            NGAYGIAO: '05/08/2021 16:22:09',
            SO_DT: '0913200119',
            STATUS: 0,
            NHIEMVU_ID: 367,
            KQXL_ID: null
          }
        ]
      }
      var phieuId = 0
      phieuId = this.state.phieu_id
      postData.vhdtb_id = this.state.hdtb_id
      postData.vhdkh_id = this.state.hdkh_id
      postData.vphieu_id = phieuId
      postData.vdonvi_giao_id = this.tt_nd.donvi_id
      postData.vkyhoadon = 202101
      postData.vnhanvien_hc_id = this.tt_nd.nhanvien_id * 1
      postData.vnhanvien_gv_id = this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value * 1
      postData.vngayht = moment(this.controls.dtpNgayBG.value, 'DD/MM/YYYY hh:mm A').format('DD/MM/YYYY HH:mm:ss')
      postData.vngaygv = moment(this.controls.dtpNgayGV.value, 'DD/MM/YYYY hh:mm A').format('DD/MM/YYYY HH:mm:ss')
      postData.vjson_ttdv_cntt = this.tab_dvcntt.list.map((x) => ({
        sCaption: x.caption,
        sFieldName: x.field_name,
        nFieldLength: x.field_length,
        sValue: this.mapsValue(x),
        iAtt: x.att,
        iDataType: x.data_type,
        sMaxValue: x.max_value,
        sMinvalue: x.min_value
        // sLookUpTableName: "",
        // sLookUpKeyField: "",
        // sLookUpValueField: "",
        // StrSql: "",
        // strDepandField: "",
      }))
      postData.vjson_giaophieu_nv = postData.vjson_giaophieu_nv.map((x) => ({
        PHIEU_ID: phieuId,
        NHANVIEN_TH_ID: this.tt_nd.nhanvien_id, //this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value,
        NHIEMVU: 'Khai báo thông số!',
        GHICHU: '',
        NHANVIEN_GIAO_ID: this.tt_nd.nhanvien_id,
        NGAYGIAO: moment(this.controls.dtpNgayGV.value, 'DD/MM/YYYY HH:mm A').format('DD/MM/YYYY HH:mm:ss'),
        SO_DT: '',
        STATUS: 0,
        NHIEMVU_ID: 7,
        KQXL_ID: null
      }))
      if ((await this.KiemTra_DL(this.state.luong_id)) != true) return
      this.$bvModal
        .msgBoxConfirm('Bạn thật sự muốn hoàn công không ?', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
          centered: true
        })
        .then(async (v) => {
          if (!v) {
            return
          }
          try {
            //this.loading(true)
            await CompleteProfileAPI.fn_an_hoancong_dvcntt(this.axios, postData).then((res) => {
              this.hideLoading()
              if (res.data.error == '200') {
                if (res.data.data && res.data.data.toLowerCase().indexOf('thành công') > 0) {
                  this.$toast.success(res.data.data)
                  this.ClearForm()
                  this.CLEAR()
                  this.HienThiDanhSachHDTB()
                  //this.datagrid.ds_hdtb.list = this.datagrid.ds_hdtb.list.filter(x => x.ma_tb != this.state.ma_tb);
                } else {
                  if (res.data.data) this.$toast.error(res.data.data)
                  else this.$toast.error(res.data.message)
                }
              } else this.$toast.error('Lỗi cập nhật dữ liệu: ' + res.data.message)
            })
          } catch (ex) {
            ////this.loading(false)
            commonFn.showException(this, ex)
          }
          ////this.loading(false)
        })
    },
    nd_th_changed(e) {
      let elm = document.getElementById('txtNoiDungTH')
      if (elm && !elm.validity.valid) {
        elm.setCustomValidity('')
      }
    },
    async btnNDTH_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0 || !this.state.selectedPhieu) {
        this.$toast.warning('Hãy chọn phiếu trước!')
        return
      }
      if (!this.state.selectedPhieu.nd_thuchien || this.state.selectedPhieu.nd_thuchien.length <= 0) {
        this.$toast.warning('Bạn cần nhập nội dung!')
        this.$refs.txtNoiDungTH.focus()
        this.$refs.txtNoiDungTH.setCustomValidity('Invalid field.')
        //this.$refs.txtNoiDungTH.required = true;
        //@change="() => { if(state.selectedPhieu.nd_thuchien.length > 0){ $refs.txtNoiDungTH.setCustomValidity('')} }"
        return
      }
      try {
        //this.loading(true)
        await CompleteProfileAPI.update_nd_thuchien(this.axios, {
          phieu_id: this.state.phieu_id,
          nd_thuchien: this.state.selectedPhieu.nd_thuchien
        }).then((res) => {
          if (res && res.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
            if (!res.data.data || res.data.data[0].kq != 'OK') {
              commonFn.showException(this, res.data.data ? res.data.data[0].kq : 'Lỗi cập nhật dữ liệu!')
              this.$refs.txtNoiDungTH.focus()
              this.$refs.txtNoiDungTH.setCustomValidity('Invalid field.')
            } else {
              this.$toast.success('Cập nhật thành công!')
              this.gv_DanhSach_FocusedRowChanged(this.state.selectedPhieu)
            }
          } else {
            this.$refs.txtNoiDungTH.focus()
            this.$refs.txtNoiDungTH.setCustomValidity('Invalid field.')
            //this.$toast.error("Lỗi cập nhật dữ liệu: " + res.data.message);
            commonFn.showException(this, res.data)
          }
        })
      } catch (ex) {
        ////this.loading(false)
        commonFn.showException(this, ex)
        this.$refs.txtNoiDungTH.setCustomValidity('Invalid field.')
      } finally {
        this.hideLoading()
      }
    },
    async tsbtnGiaoViec_Click() {
      if (this.datagrid.ds_hdtb.list < 0) {
        this.$toast.error('Chưa có danh thuê bao. Bạn hãy kiểm tra lại! ')
        return
      }
      var grid = this.$refs.gv_DanhSach
      var seletedRows = grid.getSelectedRecords()
      if (seletedRows.length <= 0) {
        this.$toast.error('Bạn chưa chọn thuê bao! ')
        return
      }
      if (this.tab_dvcntt.tt_giaoviec.ds_nguoigv.list.length <= 0 && this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value == 0) {
        this.$toast.error('Hãy nhập nhân viên giao việc!')
        return
      }
      if (String(this.controls.dtpNgayGV.enabled).toLowerCase() == 'false') {
        this.$toast.error('Hãy nhập ngày giao việc!')
        return
      }
      if (String(this.controls.dtpNgayBG.enabled).toLowerCase() == 'true') {
        var diff = moment(this.controls.dtpNgayBG.value, 'DD/MM/YYYY hh:mm A') - moment(this.controls.dtpNgayGV.value, 'DD/MM/YYYY hh:mm A')
        if (diff < 0) {
          this.$toast.error('Ngày giao việc không được lớn hơn ngày hoàn thành!')
          return false
        }
      }
      var dsphieu = seletedRows.map((x) => x.phieu_id)
      var dsma_tb = seletedRows.map((x) => x.ma_tb)
      var dshdtb_id = seletedRows.map((x) => x.hdtb_id)
      this.state.dsphieu = dsphieu[0]
      this.state.dsma_tb = dsma_tb[0]
      this.state.dshdtb_id = dshdtb_id[0]
      this.state.huonggiao_id = seletedRows[0].huonggiao_id
      console.log('this.state.dsphieu', this.state.dsphieu)
      console.log('this.state.dsma_tb', this.state.dsma_tb)
      console.log('this.state.dshdtb_id', this.state.dshdtb_id)
      console.log('this.state.huonggiao_id', this.state.huonggiao_id)
      let vnhanvien_giao_id = Number(this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value)
      let vngaygiao = moment(this.controls.dtpNgayGV.value, 'DD/MM/YYYY HH:mm A').format('DD/MM/YYYY HH:mm:ss')
      console.log('vngaygiao', vngaygiao)
      this.state.modalTitle = 'Giao phiếu nhân viên'
      this.dialogComponent = () => import('@/modules/contract/setup/DeclareLandline/popups/frnGiaoPhieuNV.vue')
      this.popupComponentName = 'frnGiaoPhieuNV'
      this.popupComponentEvts['giaoviec_success'] = this.giaoviec_success
      //this.Popup("modalComponentsITVAS");
      await this.dialogComponent().then((x) => {})
      this.$nextTick(() => {
        this.$refs.generalComponent.frnGiaoPhieuNV(this.state.dsphieu, this.$root.token.getNhanVienID(), vnhanvien_giao_id, vngaygiao)
        this.$refs.generalComponent.vma_tb = this.state.dsma_tb
        this.$refs.generalComponent.vhdtb_id = this.state.dshdtb_id
        this.$refs.generalComponent.huonggiao_id = this.state.huonggiao_id
        this.$refs.generalComponent.load()
        this.$refs.generalDialog.show()
      })
      // this.$refs.frnGiaoPhieuNV.frnGiaoPhieuNV(this.state.dsphieu, this.$root.token.getNhanVienID(), vnhanvien_giao_id, vngaygiao);
      // this.$refs.frnGiaoPhieuNV.vma_tb = this.state.dsma_tb;
      // this.$refs.frnGiaoPhieuNV.vhdtb_id = this.state.dshdtb_id;
      // this.$refs.frnGiaoPhieuNV.huonggiao_id = this.state.huonggiao_id;
      // this.$nextTick(() => {
      //   this.$refs.frnGiaoPhieuNV.load()
      //   this.$refs.popupfrnGiaoPhieuNV.show();
      // });
    },
    tsbtnNhapMoiTT_Click() {
      if (!this.state.phieu_id || this.state.phieu_id <= 0) {
        this.$toast.error('Bạn chưa chọn phiếu!')
        return
      }
      let noidung = ''
      if (this.state.selectedPhieu && this.state.selectedPhieu.phieu_id > 0) {
        noidung = `${this.state.selectedPhieu.ma_tb}|${this.state.selectedPhieu.ten_tb}|${this.state.selectedPhieu.diachi_ld}`
      }
      var data = {
        hdtb_id: this.state.hdtb_id,
        nhanvien_id: this.tt_nd.nhanvien_id,
        nguoidung_id: this.tt_nd.nguoidung_id,
        phanvung_bancheo_id: this.tt_nd.phanvung_id,
        noidung: noidung,
        tientrinhtc_id: 0
      }
      console.log('tsbtnNhapMoiTT_Click', data)
      this.$refs.dialogNhapTienTrinhTC.openDialog(data)
    },
    tsddbIn_Click: function(e) {
      this.$toast.success('Đang chờ thông tin đế ghép')
    },
    tsbtnChuyenTo_Click: function(e) {
      if (!this.state.phieu_id || this.state.phieu_id <= 0) {
        this.$toast.error('Bạn chưa chọn phiếu!')
        return
      }
      console.log('tsbtnChuyenTo_Click', 'Phiếu', this.state.phieu_id, 'HDTBID', this.state.hdtb_id)
      this.$refs.popupChuyenTo.openDialog(this.state.phieu_id, this.state.hdtb_id)
    },
    async tsbtnTraPhieu_Click() {
      try {
        if (!this.state.phieu_id || this.state.phieu_id == 0) {
          this.$toast.error('Hãy chọn phiếu trước!')
          return
        }
        //Bỏ đồng bộ CMS do hiện tại đã tự động đồng bộ
        // if (await this.PHAILAM(this.state.luong_id, "DONGBO_BACKEND"))
        // {
        //     if (this.state.vloaitb_id == LOAIHINH_THUEBAO.CA || this.state.vloaitb_id == 288)
        //     {
        //       let isConfirm = await this.$bvModal
        //                               .msgBoxConfirm("Bạn CHƯA ấn nút [Đồng bộ CMS] trước khi giao phiếu???", {
        //                                 title: "",
        //                                 size: "sm",
        //                                 okTitle: "Đồng ý",
        //                                 cancelTitle: "Hủy",
        //                                 centered: true,
        //                               });
        //       if (isConfirm == true)
        //       {
        //           let data = {
        //             luong_thaotac: this.state.luong_thaotac,
        //             luong_id: this.state.luong_id,
        //             loaihd_id: this.state.loaihd_id,
        //             kieuld_id: this.state.kieuld_id,
        //             hdtb_id: this.state.hdtb_id,
        //             thuebao_id: this.state.thuebao_id,
        //             ma_tinh: this.tt_nd.ma_tinh,
        //           }
        //           //this.loading(true);
        //           let kq = await ca.DongBoCMS(this, data);
        //           this.$root.$toast.success(kq)
        //           ////this.loading(false);
        //           return;
        //       }
        //        else return;
        //     }
        // }
        if (!this.state.selectedPhieu.ma_gd) {
          this.$toast.error('Mã giao dịch null!')
          return
        }
        //this.loading(true)
        console.log(`tsbtnTraPhieu_Click ma_gd: ${this.state.selectedPhieu.ma_gd},  ma_tb: ${this.state.selectedPhieu.ma_tb},  dichvuvt_id: ${this.state.dichvuvt_id}, loaihd_id: ${this.state.loaihd_id},  donvi_id: ${this.state.donvi_id}, `)
        this.state.traphieu_success = false
        // this.$refs.frmTraPhieuTC.frmTraPhieuTC(
        //   this.state.selectedPhieu.ma_tb,
        //   this.state.loaihd_id,
        //   this.state.dichvuvt_id,
        //   this.tt_nd.donvi_id,
        //   this.state.selectedPhieu.ma_tb,
        //    "0",
        //    "0",
        //    3);
        // this.$refs.popupFrmTraPhieuTC.show()
        this.state.modalTitle = 'Trả phiếu nhân viên'
        this.state.modalId = 'frmTraPhieuTC'
        this.dialogComponent = () => import('../../setup/ReturnInstallTicket/frmTraPhieuTC.vue')
        this.dialogEvts.close = this.popupTraphieu_closed
        this.popupComponentName = 'frmTraPhieuTC'
        this.popupComponentEvts['traphieu_success'] = this.traphieu_success
        //this.Popup("modalComponentsITVAS");
        await this.dialogComponent().then((x) => {})
        this.$nextTick(() => {
          this.$refs.generalComponent.frmTraPhieuTC(this.state.selectedPhieu.ma_tb, this.state.loaihd_id, this.state.dichvuvt_id, this.tt_nd.donvi_id, this.state.selectedPhieu.ma_tb, '0', '0', 3)
          this.$refs.generalDialog.show()
        })
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      ////this.loading(false)
    },
    async traphieu_success() {
      console.log('traphieu_success')
      this.state.traphieu_success = true
      this.HienThiDanhSachHDTB()
    },
    async popupTraphieu_closed() {
      console.log('popupTraphieu_closed', this.state.traphieu_success)
      if (this.state.traphieu_success && this.state.traphieu_success == true) {
        this.datagrid.ds_hdtb.list = this.datagrid.ds_hdtb.list.filter((x) => x.ma_tb != this.state.ma_tb)
        //this.gv_DanhSach_FocusedRowChanged(this.state.selectedPhieu);
      }
    },
    tsbtnXuatFile_Click: function(e) {
      if (!this.datagrid.ds_hdtb.list || !this.datagrid.ds_hdtb.list.length > 0) {
        this.$toast.error('Không có dữ liệu. Bạn hãy kiểm tra lại điều kiện lấy báo cáo !')
        return
      }
      this.state.dataExport = this.datagrid.ds_hdtb.list
      this.$refs.exportDataModal.showModal()
    },
    tsbtnGiaoPhieu_Ton_Click(e) {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.error('Hãy chọn phiếu trước để nhập lý do tồn!')
        return
      }
      console.log('tsbtnGiaoPhieu_Ton_Click this.state.phieu_id:', this.state.phieu_id)
      console.log(' tsbtnGiaoPhieu_Ton_Click this.state.tthd_id:', this.state.tthd_id)
      this.$refs.dialogObjPhieuTon.showPhieuTon(this.state.phieu_id, this.state.tthd_id, [])
    },
    //Gán KV
    tsbtnNVKT_Click: function(e) {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.error('Bạn chưa chọn phiếu!')
        return
      }
      this.dataPopupKhuVuc.hdtb_id = this.state.hdtb_id
      this.dataPopupKhuVuc.donviql_id = 0
      console.log('tsbtnNVKT_Click', this.dataPopupKhuVuc)
      this.$refs.thayDoiKhuVucQuanLyThueBaoModal.showModal()
      // this.popupComponent = () =>
      //   import("../../setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao");
      // this.popupComponentName = "popupChonKhuVuc";
      // var data = {
      //   diachi: this.diachiLD,
      //   khuvuc_id: 0,
      //   hdtb_id: this.state.hdtb_id,
      //   thuebao_id: 0,
      //   donvi_id: 0,
      // };
      // console.log("popupChonKhuVuc DATA", data);
      // this.state.modalTitle = "Thay đổi khu vực quản lý thuê bao";
      // this.popupComponentData = data;
      // //if(this.dsCTM_TBI && this.dsCTM_TBI.length>0) this.popupComponentData.ds = this.dsCTM_TBI;
      // this.Popup("modalComponentsITVAS");
    },
    async tsbtnHoanThienHS_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.warning('Bạn chưa chọn phiếu')
        return
      }
      // if (this.state.vloaitb_id == 376) {
      //   let result = await this.Check_TrangThaiLapDat(this.state.vloaitb_id, this.state.hdtb_id)
      //   if (!result) return
      // }
      if (this.state.vloaitb_id == 2120 || this.state.vloaitb_id == 2130) {
        const temp = await this.GuiSMSKichHoat()
        if (temp !== 'OK') {
          this.$toast.error(temp)
          console.log(temp)
          return
        }
      }
      try {
        var postData = {
          vhdtb_id: 6058534,
          vhdkh_id: 5422987,
          vphieu_id: 12045046,
          vdonvi_giao_id: 900,
          vkyhoadon: 202101,
          vnhanvien_hc_id: 452,
          vnhanvien_gv_id: 452,
          vngayht: '17/10/2021 00:00:00',
          vngaygv: '17/10/2021 00:00:00',
          vngaykh: '17/10/2021 00:00:00',
          vjson_ttdv_cntt: [
            {
              sCaption: 'Mã thuê bao (*)',
              sFieldName: 'MA_TB',
              nFieldLength: 30,
              sValue: 'hpg_eng_00000187',
              iAtt: 0,
              iDataType: 0,
              sMaxValue: 5,
              sMinvalue: 0,
              sLookUpTableName: '',
              sLookUpKeyField: '',
              sLookUpValueField: '',
              StrSql: '',
              strDepandField: ''
            }
          ],
          vjson_giaophieu_nv: [
            {
              PHIEU_ID: 12045046,
              NHANVIEN_TH_ID: 12461,
              NHIEMVU: 'Đào tạo, hướng dẫn',
              GHICHU: 'ERP TEST, không thực hiện',
              NHANVIEN_GIAO_ID: 452.0,
              NGAYGIAO: '05/08/2021 16:22:09',
              SO_DT: '0913200119',
              STATUS: 0,
              NHIEMVU_ID: 367,
              KQXL_ID: null
            }
          ]
        }
        var vkyhoadon = moment(this.controls.dtpNgayBG.value, 'DD/MM/YYYY hh:mm A').format('YYYYMM')
        var phieuId = 0
        phieuId = this.state.phieu_id
        postData.vphieu_id = phieuId
        postData.vhdtb_id = this.state.hdtb_id
        postData.vloaihd_id = this.state.loaihd_id
        postData.vluong_id = this.state.luong_id
        postData.vnhanvien_hc_id = this.tt_nd.nhanvien_id * 1
        postData.vnhanvien_gv_id = this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value * 1
        postData.vngayht = moment(this.controls.dtpNgayBG.value, 'DD/MM/YYYY hh:mm A').format('DD/MM/YYYY HH:mm:ss')
        postData.vngaygv = moment(this.controls.dtpNgayGV.value, 'DD/MM/YYYY hh:mm A').format('DD/MM/YYYY HH:mm:ss')
        postData.vngaykh = moment(this.tt_nd.ngay_cn, 'DD/MM/YYYY hh:mm A').format('DD/MM/YYYY HH:mm:ss')
        postData.vthuebao_id = this.state.thuebao_id * 1
        postData.vdonvi_giao_id = this.tt_nd.donvi_id
        postData.vkyhoadon = vkyhoadon
        phieuId = this.state.phieu_id
        postData.vphieu_id = phieuId
        postData.vhdtb_id = this.state.hdtb_id
        postData.vloaihd_id = this.state.loaihd_id
        postData.vluong_id = this.state.luong_id
        postData.vnhanvien_hc_id = this.tt_nd.nhanvien_id * 1
        postData.vnhanvien_gv_id = this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value * 1
        postData.vngayht = moment(this.controls.dtpNgayBG.value, 'DD/MM/YYYY hh:mm A').format('DD/MM/YYYY HH:mm:ss')
        postData.vngaygv = moment(this.controls.dtpNgayGV.value, 'DD/MM/YYYY hh:mm A').format('DD/MM/YYYY HH:mm:ss')
        postData.vngaykh = moment(this.tt_nd.ngay_cn, 'DD/MM/YYYY hh:mm A').format('DD/MM/YYYY HH:mm:ss')
        postData.vthuebao_id = this.state.thuebao_id * 1
        postData.vdonvi_giao_id = this.tt_nd.donvi_id
        postData.vkyhoadon = vkyhoadon
        postData.vjson_ttdv_cntt = this.tab_dvcntt.list.map((x) => ({
          sCaption: x.caption,
          sFieldName: x.field_name,
          nFieldLength: x.field_length,
          sValue: this.mapsValue(x),
          iAtt: x.att,
          iDataType: x.data_type,
          sMaxValue: x.max_value,
          sMinvalue: x.min_value,
          sLookUpTableName: null,
          sLookUpKeyField: null,
          sLookUpValueField: null,
          StrSql: null,
          strDepandField: null
        }))
        postData.vjson_giaophieu_nv = postData.vjson_giaophieu_nv.map((x) => ({
          PHIEU_ID: phieuId,
          NHANVIEN_TH_ID: this.tt_nd.nhanvien_id * 1, //this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value,
          NHIEMVU: 'Khai báo thông số!',
          GHICHU: '',
          NHANVIEN_GIAO_ID: this.tt_nd.nhanvien_id,
          NGAYGIAO: moment(this.controls.dtpNgayGV.value, 'DD/MM/YYYY HH:mm A').format('DD/MM/YYYY HH:mm:ss'),
          SO_DT: '',
          STATUS: 0,
          NHIEMVU_ID: 7,
          KQXL_ID: null
        }))
        //region cuongpq thêm code kiểm tra kích hoạt sms brandname
        if ((this.state.kieuld_id.toString() == '840' || this.state.kieuld_id.toString() == '799') /*hủy đăng ký nhà mạng brandname*/
          && (this.state.vloaitb_id == 131 || this.state.vloaitb_id == 316)) {
          //lấy các hướng đăng ký hủy
          var json_data_sp_load_dulieu_form_tao_brn = JSON.stringify({
            vhdtb_id: this.state.hdtb_id
          })
          var link_sp_load_dulieu_form_tao_brn = '/web-quantri/sms-brandname/sp_load_dulieu_form_tao_brn'
          const files_sp_load_dulieu_form_tao_brn = await this.axios.post(link_sp_load_dulieu_form_tao_brn, json_data_sp_load_dulieu_form_tao_brn, {
            headers: {
              'Content-Type': 'application/json'
            }
          })
          if (files_sp_load_dulieu_form_tao_brn.data.data.data) {
            var ds = files_sp_load_dulieu_form_tao_brn.data.data.data
            var thuebao_id = ds[0].thuebao_id
            var agent_id = ds[0].agent_id
            var adser_id = ds[0].adser_id
            var contract_id = ds[0].contract_id
            var agent_type_id = Number(ds[0].agent_type_id)
            var contract_type_id = ds[0].contract_type_id
            var label_id = ds[0].label_id
            //lấy lĩnh vực
            var json_data_sp_lay_thongtin_dangky_brandname = JSON.stringify({
              vhdtb_id: this.state.hdtb_id
            })
            var link_sp_lay_thongtin_dangky_brandname = '/web-quantri/sms-brandname/sp_lay_thongtin_dangky_brandname'
            const files_sp_lay_thongtin_dangky_brandname = await this.axios.post(link_sp_lay_thongtin_dangky_brandname, json_data_sp_lay_thongtin_dangky_brandname, {
              headers: {
                'Content-Type': 'application/json'
              }
            })

        
            if (files_sp_lay_thongtin_dangky_brandname.data.data) {
              ds = files_sp_lay_thongtin_dangky_brandname.data.data
              var link_fn_co_kiemtra_trangthai_brn = '/web-quantri/sms-brandname/fn_co_kiemtra_trangthai_brn'
              const files_fn_co_kiemtra_trangthai_brn = await this.axios.post(link_fn_co_kiemtra_trangthai_brn)
              if (files_fn_co_kiemtra_trangthai_brn.data.data) {
                var kt_trangthai = files_fn_co_kiemtra_trangthai_brn.data.data
                this.resource = (await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', {
              P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
              p_nghiepvu: 'KIEMTRA_WHITELIST_BRANDNAME',
              p_ds_para: JSON.stringify({ vhdtb_id: this.hdtb_id })
            })).data.data[0].kq == 'ok' ? 'web-thicong' : 'tichhop';

                if (kt_trangthai == 'OK') {
                  var link_LAY_DS_NHAN = `/${this.resource}/brandname/LAY_DS_NHAN`
                  var json_LAY_DS_NHAN = JSON.stringify({
                    agent_id: agent_id,
                    adser_id: adser_id,
                    contract_id: contract_id,
                    agent_type: agent_type_id
                  })
                  const files_LAY_DS_NHAN = await this.axios.post(link_LAY_DS_NHAN, json_LAY_DS_NHAN, {
                    headers: {
                      'Content-Type': 'application/json'
                    }
                  })
                  if (files_LAY_DS_NHAN.data.data) {
                    this.dt = files_LAY_DS_NHAN.data.data.data.items.filter((item) => item.label_id == label_id)
                    for (let i = 0; i < this.dt.length; i++) {
                      if (ds.linhvuc_id != -1 || this.state.kieuld_id.toString() == '799') {
                        var temp = this.dt[i].label_type.toString()
                        var temp2 = this.dt[i].status.toString()
                        if (temp != '0' && temp != '' && temp != '-1') {
                          if (temp2 != '-2' && temp2 != '-1' && temp2 != '2' && temp2 != '-3' && temp2 != '0') {
                            this.$toast.warning('Trạng thái mạng Vinaphone đang duyệt, không thể hoàn thành')
                            return
                          }
                        }
                      }
                      if (ds.linhvuc_vms_id != -1 || this.state.kieuld_id.toString() == '799') {
                        var temp = this.dt[i].label_type_vms.toString()
                        var temp2 = this.dt[i].status_vms.toString()
                        if (temp != '0' && temp != '' && temp != '-1') {
                          if (temp2 != '-2' && temp2 != '-1' && temp2 != '2' && temp2 != '-3' && temp2 != '0') {
                            this.$toast.warning('Trạng thái mạng Mobifone đang duyệt, không thể hoàn thành')
                            return
                          }
                        }
                      }
                      if (ds.linhvuc_vtl_id != -1 || this.state.kieuld_id.toString() == '799') {
                        var temp = this.dt[i].label_type_vtl.toString()
                        var temp2 = this.dt[i].status_vtl.toString()
                        if (temp != '0' && temp != '' && temp != '-1') {
                          if (temp2 != '-2' && temp2 != '-1' && temp2 != '2' && temp2 != '-3' && temp2 != '0') {
                            this.$toast.warning('Trạng thái mạng Vietel đang duyệt, không thể hoàn thành')
                            return
                          }
                        }
                      }
                      if (ds.linhvuc_gtel_id != -1 || this.state.kieuld_id.toString() == '799') {
                        var temp = this.dt[i].label_type_gtel.toString()
                        var temp2 = this.dt[i].status_gtel.toString()
                        if (temp != '0' && temp != '' && temp != '-1') {
                          if (temp2 != '-2' && temp2 != '-1' && temp2 != '2' && temp2 != '-3' && temp2 != '0') {
                            this.$toast.warning('Trạng thái mạng Gtel đang duyệt, không thể hoàn thành')
                            return
                          }
                        }
                      }
                      if (ds.linhvuc_vnm_id != -1 || this.state.kieuld_id.toString() == '799') {
                        var temp = this.dt[i].label_type_vnm.toString()
                        var temp2 = this.dt[i].status_vnm.toString()
                        if (temp != '0' && temp != '' && temp != '-1') {
                          if (temp2 != '-2' && temp2 != '-1' && temp2 != '2' && temp2 != '-3' && temp2 != '0') {
                            this.$toast.warning('Trạng thái mạng Vietnamobile đang duyệt, không thể hoàn thành')
                            return
                          }
                        }
                      }
                      if (this.dt[i].label_type_itel) {
                        if (ds.linhvuc_itel_id != -1 || this.state.kieuld_id.toString() == '799') {
                          var temp = this.dt[i].label_type_itel.toString()
                          var temp2 = this.dt[i].status_itel.toString()
                          if (temp != '0' && temp != '' && temp != '-1') {
                            if (temp2 != '-2' && temp2 != '-1' && temp2 != '2' && temp2 != '-3' && temp2 != '0') {
                              this.$toast.warning('Trạng thái mạng Itel đang duyệt, không thể hoàn thành')
                              return
                            }
                          }
                        }
                      }
                      if (ds.linhvuc_mobicast_id != -1 || this.state.kieuld_id.toString() == '799') {
                        var temp = this.dt[i].label_type_mobicast.toString()
                        var temp2 = this.dt[i].status_mobicast.toString()
                        if (temp != '0' && temp != '' && temp != '-1') {
                          if (temp2 != '-2' && temp2 != '-1' && temp2 != '2' && temp2 != '-3' && temp2 != '0') {
                            this.$toast.warning('Trạng thái mạng Mobicast đang duyệt, không thể hoàn thành')
                            return
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        //end region
        //this.loading(true)
        CompleteProfileAPI.fn_an_hoanthienhoso_dichvu_cntt(this.axios, postData)
          .then((res) => {
            this.hideLoading()
            if (res.data.data && (res.data.data.indexOf('thành công') > 0 || res.data.data == 'ok')) {
              this.$toast.success('Hoàn thiện hồ sơ thành công!')
              this.ClearForm()
              this.CLEAR()
              this.HienThiDanhSachHDTB()
            } else {
              this.$toast.error('Lỗi hoàn thiện hồ sơ: ' + res.data.data)
            }
          })
          .catch((ex) => {
            ////this.loading(false)
            commonFn.showException(this, ex)
          })
      } catch (ex) {
        ////this.loading(false)
        commonFn.showException(this, ex)
      }
      ////this.loading(false)
    },
    DangXayDung() {
      this.$toast.error('Chức năng đang được xây dựng và hoàn thiện! (đang chờ API)')
    },
    tsbtnHen_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.error('Bạn chưa chọn phiếu!')
        return
      }
      this.$refs.popupThongTinHenKH.showModal()
    },
    async tsbtnHuyKH_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.error('Bạn chưa chọn phiếu!')
        return
      }
      try {
        let vloaitb_id = this.state.vloaitb_id * 1
        let result = false
        if (vloaitb_id == 376) {
          result = await this.HuyKichHoat_iAlert_Click()
        } else {
          this.$toast.error('Dịch vụ này không thể hủy kích hoạt!')
        }
        this.controls.tsbtnHuyKH.enabled = !result
      } catch (e) {
        this.$toast.error(e)
      }
    },
    async HuyKichHoat_iAlert_Click() {
      try {
        let input = {
          vloaitb_id: 376,
          input_object: `"{\"vhdtb_id\": \"${this.state.hdtb_id * 1}\"}"`,
          api_code: 'kiemtra_ialert',
          vhdtb_id: this.state.hdtb_id * 1
        }
        let rs = await kiemtra_dulieu_laphopdong_cntt(input)
        if (rs.status == '00') {
          this.$toast.success(rs.message)
          return true
        } else {
          this.$toast.error(rs.message)
          return false
        }
      } catch (e) {
        this.$toast.error(e)
        return false
      }
    },
    tsbtnFile_Upload_Click: function(e) {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.error('Hãy chọn phiếu trước!')
        return
      }
      //this.popupComponent = () => import("../../../search/subscriber/SearchEProfile");
      this.dialogComponent = () => import('../../../search/subscriber/SearchEProfile')
      this.popupComponentName = 'popupFileUpload'
      this.popupComponentData = this.state.selectedPhieu.ma_tb
      this.popupComponentAttr = {
        matb: this.state.selectedPhieu.ma_tb,
        magd: this.state.selectedPhieu.ma_gd,
        ispopup: true
      }
      this.state.modalTitle = 'File Upload'
      console.log('tsbtnFile_Upload_Click', this.popupComponentData)
      //this.Popup("modalComponentsITVAS");
      this.$refs.generalDialog.show()
    },
    tsbtnVatTu_Click() {
      if (this.state.phieu_id && this.state.phieu_id > 0) {
        let inphieuid = this.state.phieu_id * 1
        let inhdtbid = this.state.hdtb_id * 1
        let inthuebaoid = this.state.thuebao_id * 1
        this.modelVatTu.hdtb_id = inhdtbid
        this.modelVatTu.phieu_id = inphieuid
        this.modelVatTu.loaihd_id = this.state.loaihd_id * 1
        this.modelVatTu.thuebao_id = inthuebaoid
        this.$refs.popupVatTuThueBao.showModal()
      } else {
        this.$root.$toast.warning('Bạn chưa chọn phiếu!')
      }
    },
    async tbs_giahan_ca_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.warning('Bạn chưa chọn phiếu')
        return
      }
      let vloaitb_id = this.state.vloaitb_id * 1
      if (vloaitb_id == LOAIHINH_THUEBAO.TTDL_HDDT) {
        this.$refs.popupCapNhatThongTinKHHDDT.openDialog(data)
      }
      if (vloaitb_id == LOAIHINH_THUEBAO.CA || vloaitb_id == LOAIHINH_THUEBAO.SMART_CA) {
        await this.$refs.frmPopupThuHoiCATaoLoi.openDialog()
      }
    },
    async tsbtnDongBoCMS_Click() {
      try {
        let vloaitb_id = this.state.vloaitb_id * 1
        if (this.state.hdtb_id == 0) {
          this.$toast.warning('Vui lòng chọn phiếu trước khi nhấn đồng bộ cms')
          return
        }
        if (vloaitb_id == 116 || vloaitb_id == 288) {
          this.$root.$toast.success('Trên OneBss bạn không cần Đồng bộ CMS thủ công')
          return
        }
        this.loading(true)
        const loaitb_sudung_api = await this.ConvertResApiHBDT(
          api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
            p_nghiepvu: 'tsbtnDongBoCMS_Click',
            p_ds_para: JSON.stringify({ LOAITB_ID: vloaitb_id, KIEULD_ID: this.state.kieuld_id, HDTB_ID: this.state.hdtb_id, LOAIHD_ID: this.state.loaihd_id })
          })
        )
        console.log(loaitb_sudung_api)
        const kq_check = loaitb_sudung_api[0].ketqua
        this.loading(false)
        /*Cách dịch vụ không sử dụng API Dynamic CNTT*/
        if (kq_check !== 'ok') {
          if (vloaitb_id == 302) {
            // dich vu hkd
            if (!this.state.phieu_id && this.state.phieu_id <= 0) {
              this.$root.$toast.warning('Bạn chưa chọn phiếu!')
              return
            }
            //this.loading(true)
            let data = {
              luong_thaotac: this.state.luong_thaotac,
              luong_id: this.state.luong_id,
              loaihd_id: this.state.loaihd_id,
              kieuld_id: this.state.kieuld_id,
              hdtb_id: this.state.hdtb_id,
              thuebao_id: this.state.thuebao_id,
              ma_tinh: this.tt_nd.ma_tinh
            }
            //let kq = await ca.DongBoCMS(this, data);
            await hkd.DongBoHKD(this, data)
            //  this.$root.$toast.success(kq)
            //await this.gv_DanhSach_FocusedRowChanged(this.state.selectedPhieu)
          } else if (vloaitb_id == 319) {
            let vstatus = this.GetData(
              await api.fn_get_status_hdtb(this.axios, {
                vhdtb_id: this.state.hdtb_id,
                hdtb_id: this.state.hdtb_id
              })
            )
            if (vstatus == 8) {
              this.$root.$toast.warning('Thuê bao đã được đồng bộ mã thuê bao thành công, vui lòng không đồng bộ lại')
              return
            }
            if (vstatus == 6) {
              this.$root.$toast.warning('Thuê bao đã kích hoạt thành công, vui lòng không đồng bộ mã thuê bao')
              return
            }
            await this.$refs.DongBoEticket.FormLoad({ hdtb_id: this.state.hdtb_id, ma_tb: this.state.ma_tb })
          } else if (vloaitb_id == 122 ||
            vloaitb_id == 175 ||
            vloaitb_id == 319 ||
            vloaitb_id == 2116) {
            let mst = 0
            this.tab_dvcntt.list.forEach((item) => {
              if (item.field_name == 'MST') {
                //debugger
                mst = item.field_value
                console.log(mst)
              }
            })
            if (mst === 0 || mst == null) {
              this.$toast.warning('Không lấy được mã số thuế. Hãy kiểm tra lại trường mã số thuê')
              return
            }
            try {
              this.loading(true)
              let kq = this.GetData(await api.LayThongTinKHHDDT(this.axios, { params: { taxcode: mst } }))
              console.log(kq)
              // debugger
              this.loading(false)
              if (kq && kq.success === false) {
                throw kq.returnvalue
              }
              //kq.versions = kq.Versions
              kq.versions = kq.Versions.filter((item) => item.type.includes('TT78'))
              console.log(kq)
              if (!kq || !kq.versions || kq.versions.length <= 0) {
                this.$toast.warning('Không lấy được thông tin khách hàng theo MST: ' + mst)
                this.loading(false)
                return
              }
              this.state.versions = kq.versions
              if (kq.versions.length > 1) {
                this.$bvModal.show('modal-scoped')
              } else {
                let kq2 = this.GetData(
                  await api.capnhat_thongtin_kh_hddt(this.axios, {
                    vhdtb_id: this.state.hdtb_id,
                    vkhachhang_id: this.state.versions[0].cus_id
                  })
                )
                console.log(kq2)
                if (kq2 == 'ok') {
                  this.state.cus_id = this.state.versions[0].cus_id
                  this.$toast.success('Đồng bộ thành công!')
                  await this.HienThiDanhSachHDTB()
                  return
                } else {
                  this.$toast.error(kq2)
                }
              }
            } catch (ex) {
              commonFn.showException(this, ex)
              this.loading(false)
            }
            this.loading(false)
          }
        } else {
          /*Áp dụng với các loại hình sử sụng API Dynamic*/
          const get_ketqua_dynamic = await this.ConvertResApiHBDT(api.cntt_dynamic_api(this.axios, { hdtb_id: this.state.hdtb_id, vdem: 0, vtype: 'tsbtnDongBoCMS_Click' }))
          const ketqua_status = get_ketqua_dynamic.status
          if (ketqua_status == '00') {
            this.$toast.success(get_ketqua_dynamic.message)
          } else {
            this.$toast.error(get_ketqua_dynamic.message)
          }
          await this.HienThiDanhSachHDTB()
          this.loading(false)
          return
        }
      } catch (ex) {
        commonFn.showException(this, ex)
        this.loading(false)
      } finally {
        this.loading(false)
      }
      ////this.loading(false)
    },
    async ConvertResApiHBDT(response_input) {
      let response = await response_input
      if (response.data.errorCode && response.data.errorCode != '' && response.data.errorCode != '0' && response.data.faultString && response.data.faultString != '') {
        throw response.data.faultString
      }
      if (response.data.error === 200 || response.data.error === '200' || response.data.errorCode === 0) {
        if (response.data.error_code === 'BSS-00000000') {
          return response.data.data
        }
      } else {
        if (response.data.error === '204' || response.data.error === 204) {
          return []
        } else return response.data.message
      }
    },
    async CallAPIVBN(req_url, req_data) {
      var kq = await this.axios.post(req_url, req_data)
      console.log('call vbn api', req_data, kq)
      return kq
    },
    async KhoaMoVoiceBrandname() {
      var data_khoa_mo = this.GetData(await this.axios.get(`/web-hopdong/vbn/lay_ds_thuebao_khoamo?vhdtb_id=${this.state.hdtb_id}`))
      console.log('data_khoa_mo', data_khoa_mo)
      // upper('Yêu cầu tạm dừng'), '3',
      // 	   upper('Yêu cầu khôi phục'), '0',
      // 	   upper('Khôi phục'), '0',
      // 	   upper('Yêu cầu Khóa 2 chiều'), '3',
      // 	   upper('Yêu cầu Khóa 1 chiều gọi đi'), '1',
      // 	   upper('Yêu cầu Khóa 1 chiều gọi đến'), '2',
      // 	   upper('Yêu cầu Hủy'), '3',
      for (let i = 0; i < data_khoa_mo.length; i++) {
        if (data_khoa_mo[i]['kieu_yc'] == '1' || data_khoa_mo[i]['kieu_yc'] == '3') {
          var json_data_khoamo_ic_oc = JSON.stringify({
            ma_ldo: '',
            note: 'Khoa/moi VBN',
            product: 'SPS_PRODUCT_HLR_OC_DEACTIVE',
            so_tb: data_khoa_mo[i]['so_tb']
          })
          var km_response = await this.axios.post('/web-hopdong/hopdong/fn_didong', {
            vinput: json_data_khoamo_ic_oc,
            vtype: 9,
            vhdtb_id: this.state.hdtb_id
          })
          console.log('vbn_km', json_data_khoamo_ic_oc, km_response)
        }
        if (data_khoa_mo[i]['kieu_yc'] == '2' || data_khoa_mo[i]['kieu_yc'] == '3') {
          var json_data_khoamo_ic_oc = JSON.stringify({
            ma_ldo: '',
            note: 'Khoa/moi VBN',
            product: 'SPS_PRODUCT_HLR_IC_DEACTIVE',
            so_tb: data_khoa_mo[i]['so_tb']
          })
          var km_response = await this.axios.post('/web-hopdong/hopdong/fn_didong', {
            vinput: json_data_khoamo_ic_oc,
            vtype: 9,
            vhdtb_id: this.state.hdtb_id
          })
          console.log('vbn_km', json_data_khoamo_ic_oc, km_response)
        }
        if (data_khoa_mo[i]['kieu_yc'] == '0') {
          var json_data_khoamo_ic_oc = JSON.stringify({
            ma_ldo: '',
            note: 'Khoa/moi VBN',
            product: 'SPS_PRODUCT_HLR_OC',
            so_tb: data_khoa_mo[i]['so_tb']
          })
          var km_response = await this.axios.post('/web-hopdong/hopdong/fn_didong', {
            vinput: json_data_khoamo_ic_oc,
            vtype: 9,
            vhdtb_id: this.state.hdtb_id
          })
          console.log('vbn_km', json_data_khoamo_ic_oc, km_response)
          var json_data_khoamo_ic_oc = JSON.stringify({
            ma_ldo: '',
            note: 'Khoa/moi VBN',
            product: 'SPS_PRODUCT_HLR_IC',
            so_tb: data_khoa_mo[i]['so_tb']
          })
          var km_response = await this.axios.post('/web-hopdong/hopdong/fn_didong', {
            vinput: json_data_khoamo_ic_oc,
            vtype: 9,
            vhdtb_id: this.state.hdtb_id
          })
          console.log('vbn_km', json_data_khoamo_ic_oc, km_response)
        }
      }
    },
    unicodeToString(unicodeString) {
      return unicodeString.replace(/\\u[\dA-Fa-f]{4}/g, function(match) {
        var charCode = parseInt(match.substr(2), 16)
        return String.fromCharCode(charCode)
      })
    },
    async GuiSMSKichHoat() {
      const hdtb_id = this.state.hdtb_id
      const res = await api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
        p_nghiepvu: 'GUI_SMS_KICHHOAT_MOBILE_SOLUTION',
        p_ds_para: '{"HDTB_ID":' + hdtb_id + '}'
      })
      const data = res.data.data
      if (data != "OK") {
        return 'Lỗi khi gửi SMS. Liên hệ admin để được xử lý'
      }
      return data;
    },
    async tsbtn_KichHoat_CA_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.error('Hãy chọn phiếu trước!')
        return
      }
      try {
        let vstatus = this.GetData(
          await api.fn_get_status_hdtb(this.axios, {
            vhdtb_id: this.state.hdtb_id,
            hdtb_id: this.state.hdtb_id
          })
        )
        let check = await this.fn_kiemtra_thongtin_hopdong()
        if (check == false) return
        let vloaitb_id = this.state.vloaitb_id * 1
        let loaihd_id = this.state.loaihd_id * 1
        let kieuld_id = this.state.kieuld_id
        let luong_id = this.state.luong_id
        let hdtb_id = this.state.hdtb_id * 1
        let kt_status = this.state.kt_status
        /**
         * Kiểm tra dịch vụ VNEDU PORTAL có mẫu hóa đơn chưa mới cho kích hoạt, không thì phải chọn lại mẫu hóa đơn.
         **/
        if (vloaitb_id == 225) {
          console.log('abc')
          let id_mau
          this.tab_dvcntt.list.forEach((item) => {
            if (item.field_name.toLowerCase() == 'website') id_mau = item.field_value
          })
          console.log('111')
          this.$refs.modalVNeduPortal.show()
          this.isShow = true
        }
        if (vloaitb_id == 390) {
          this.showpp_LoginCommon()
          return
        }
        if (vloaitb_id == 376 && (vstatus == 3 || vstatus == 5)) {
          return this.$toast.info('Thuê bao đã được kích hoạt, hãy ấn nút đồng bộ iAlert để đồng bộ trạng thái lắp đặt!');
        }
        /***
         * Kiểm tra các dịch vụ edu hiện thị popup thông báo
        **/
        try {
          this.loading(true);
          const sp_lay_ds_nghiepvu_cntt_common = await (api.sp_lay_ds_nghiepvu_cntt_common(this.axios, {
              p_nghiepvu: 'LAY_DS_LOAITB_HIENTHI_POPUP_THONGBAO',
              p_ds_para: JSON.stringify({
                  loaitb_id: vloaitb_id,
                  kieuld_id: kieuld_id,
                  hdtb_id: hdtb_id,
                  loaihd_id: loaihd_id
              })
          }))
          this.loading(false);
          const nghiepvu_cntt_common = sp_lay_ds_nghiepvu_cntt_common.data.data.result[0].result
          console.log(nghiepvu_cntt_common)
          if (nghiepvu_cntt_common == 'OK') {
            const dynamic_api_input = sp_lay_ds_nghiepvu_cntt_common.data.data.data
            console.log('dynamic_api_input')
            console.log(dynamic_api_input)
            console.log(sp_lay_ds_nghiepvu_cntt_common)
              if(dynamic_api_input != null){
                const ketqua_dynamic = await api.cntt_dynamic_api(this.axios, dynamic_api_input)
                console.log(ketqua_dynamic.data.data.data)
                return
              }
              else{
                let thongtin_xacnhan_str = await api.fn_thongbao_kichhoat_dvcntt_common(this.axios, {vhdtb_id: hdtb_id})
                console.log('thongtin_xacnhan_str')
                console.log(thongtin_xacnhan_str)
                console.log(thongtin_xacnhan_str.data.data)
                const h = this.$createElement
                const titleVNode = h('div', { domProps: { innerHTML: thongtin_xacnhan_str.data.data } })
                let cfr = await this.$bvModal
                  .msgBoxConfirm([titleVNode], {
                    title: 'Thông tin kích hoạt dịch vụ',
                    size: 'lg',
                    okTitle: 'Đã chính xác',
                    cancelTitle: 'Chưa đúng, để tôi cập nhật lại',
                    centered: true,
                    modalClass: 'custom-modal-height'
                  })
                  .then(async (v) => {
                    if (!v) {
                      return
                    }
                   return this.$bvModal
                      .msgBoxConfirm('Bạn có chắc chắn kích hoạt dịch vụ không ?', {
                        title: '',
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Hủy',
                        centered: true
                      })
                      .then(async (v) => {
                        return v
                      })
                  })
                  if (!cfr) {
                  throw "Dừng lại để kiểm tra thông tin"
                  }
              }
          }
        } catch (e) {
            this.loading(false);
            return
        } finally {
            this.loading(false);
        }
        /**
         * Call Api Dynamic cấu hình API goi DB
         **/
        console.log(`tsbtn_KichHoat_CA_Click loaitb_id: ${vloaitb_id} loaihd_id: ${loaihd_id}`)
        try {
          this.loading(true)
          console.log(JSON.stringify({ LOAITB_ID: vloaitb_id, KIEULD_ID: kieuld_id, HDTB_ID: hdtb_id, LOAIHD_ID: loaihd_id }))
          const loaitb_sudung_api = await this.ConvertResApiHBDT(
            api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
              p_nghiepvu: 'LAY_DS_LOAIHINHTB_SUDUNG_API_DYNAMIC',
              p_ds_para: JSON.stringify({ LOAITB_ID: vloaitb_id, KIEULD_ID: kieuld_id, HDTB_ID: hdtb_id, LOAIHD_ID: loaihd_id })
            })
          )
          const kq_check = loaitb_sudung_api[0].ketqua
          if (kq_check == 'ok') {
            if(vstatus == 6) return this.$toast.info('Thuê bao đã được kích hoạt, hãy ấn nút hoàn thiện hồ sơ!')
            const get_ketqua_dynamic = await this.ConvertResApiHBDT(api.cntt_dynamic_api(this.axios, { hdtb_id: hdtb_id }))
            const ketqua_status = get_ketqua_dynamic.status
            if (ketqua_status == '00') {
              this.$toast.success(get_ketqua_dynamic.message)
            } else {
              this.$toast.error(get_ketqua_dynamic.message)
            }
            return
          }
        } catch (error) {
          this.loading(false)
          this.$toast.error(error.mesage)
          return
        } finally {
          this.loading(false)
        }
        if (vloaitb_id * 1 === 2120) {
          this.GuiSMSKichHoat()
        }
        if (vloaitb_id == LoaiHinhTB.Pharmacy) {
          try {
            this.loading(true)
            //Đặt mới toàn trình -- Chuyển qua Dynamic rồi
            /*
            if (loaihd_id == 1 && kieuld_id == 13266) {
              const res = await this.ConvertResApiHBDT(api.fn_datmoi_pharmacy(this.axios, { hdtb_id: this.state.hdtb_id * 1 }))
              const data_res = JSON.parse(res)
              if (data_res.ErrorCode == '00') {
                this.$toast.success('Đặt mới dịch vụ thành công')
                this.is_disabled = !this.is_disabled
              } else {
                this.$toast.error('Kích hoạt đặt mới không thành công: ' + data_res.TransactionId)
                return
              }
            }*/
            //Chuyển dùng chính thức  -- Chuyển qua Dynamic rồi
            /*
            if (loaihd_id == 41 && kieuld_id == 13189) {
              const res = await this.ConvertResApiHBDT(api.fn_chuyendungchinhthuc_pharmacy(this.axios, { hdtb_id: this.state.hdtb_id * 1 }))
              const data_res = JSON.parse(res)
              if (data_res.ErrorCode == '00') {
                this.$toast.success('Chuyển dùng chính thức thành công')
                this.is_disabled = !this.is_disabled
              } else {
                this.$toast.error(data_res.TransactionId)
                return
              }
            }
              */
            //Tạm dừng --- Chuyển qua Dynamic rồi
            /*
            if (loaihd_id == 7 && kieuld_id == 714) {
              const res = await this.ConvertResApiHBDT(api.fn_tamdung_pharmacy(this.axios, { hdtb_id: this.state.hdtb_id * 1 }))
              const data_res = JSON.parse(res)
              if (data_res.ErrorCode == '00') {
                this.$toast.success('Tạm dừng dịch vụ thành công')
                this.is_disabled = !this.is_disabled
              } else {
                this.$toast.error(data_res.TransactionId)
                return
              }
            }*/
            //Khôi phục -- Chuyển qua Dynamic rồi

            /*
            if (loaihd_id == 7 && kieuld_id == 715) {
              const res = await this.ConvertResApiHBDT(api.fn_khoiphuc_pharmacy(this.axios, { hdtb_id: this.state.hdtb_id * 1 }))
              const data_res = JSON.parse(res)
              if (data_res.ErrorCode == '00') {
                this.$toast.success('Chuyển dùng chính thức thành công')
                this.is_disabled = !this.is_disabled
              } else {
                this.$toast.error(data_res.TransactionId)
                return
              }
            } */
            //Mua thêm/Cắt hủy tài khoản
            if (loaihd_id == 41 && kieuld_id == 13236) {
              const sp_lay_ds_nghiepvu_cntt_common = await (api.sp_lay_ds_nghiepvu_cntt_common(this.axios, {
              p_nghiepvu: 'CHECK_MUATHEM_HOAC_HUY_USER',
              p_ds_para: JSON.stringify({
                  loaitb_id: vloaitb_id,
                  kieuld_id: kieuld_id,
                  hdtb_id: hdtb_id,
                  loaihd_id: loaihd_id
              })}))
              const nghiepvu_cntt_common = sp_lay_ds_nghiepvu_cntt_common.data.data.result[0]
              if (nghiepvu_cntt_common.ketqua == 'OK') {
                const get_ketqua_dynamic = await api.cntt_dynamic_api(this.axios, { hdtb_id: hdtb_id, vdem: 0, vtype: 'MTSLUS' })
                const ketqua_status = get_ketqua_dynamic.data.data.status
                if (ketqua_status == '00') {
                  this.$toast.success(get_ketqua_dynamic.data.data.message)
                } else {
                  this.$toast.error(get_ketqua_dynamic.data.data.message)
                }
                return
              } else {
                this.$refs.CatHuyUserPharmacy.openPupup({
                  vhdtb_id: hdtb_id,
                  vma_tb: this.state.ma_tb,
                  vsl_user_dangky_cathuy: nghiepvu_cntt_common.soluong
                })
              }
            }
            //Mua thêm/Cắt hủy cửa hàng
            if (loaihd_id == 41 && kieuld_id == 14446) {
              try {
                this.loading(true)
                const sp_lay_ds_nghiepvu_cntt_common = await (api.sp_lay_ds_nghiepvu_cntt_common(this.axios, {
                  p_nghiepvu: 'CHECK_MUATHEM_HOAC_HUY_CUAHANG',
                  p_ds_para: JSON.stringify({
                      loaitb_id: vloaitb_id,
                      kieuld_id: kieuld_id,
                      hdtb_id: hdtb_id,
                      loaihd_id: loaihd_id
                  })}))
                const nghiepvu_cntt_common = sp_lay_ds_nghiepvu_cntt_common.data.data.result[0]
                if (nghiepvu_cntt_common.ketqua == 'OK') {
                  const get_ketqua_dynamic = await api.cntt_dynamic_api(this.axios, { hdtb_id: hdtb_id, vdem: 0, vtype: 'MTSLCH' })
                  const ketqua_status = get_ketqua_dynamic.data.data.status
                  if (ketqua_status == '00') {
                    this.$toast.success(get_ketqua_dynamic.data.data.message)
                  } else {
                    this.$toast.error(get_ketqua_dynamic.data.data.message)
                  }
                  return
                } else {
                  this.$refs.CatHuyCuaHangPharmacy.openPupup({
                    vhdtb_id: hdtb_id,
                    vma_tb: this.state.ma_tb,
                    vsl_cuahang_dangky_cathuy: nghiepvu_cntt_common.soluong
                  })
                }
              } catch (error) {
                this.loading(false)
                this.$toast.error(error.mesage)
                return
              } finally {
                this.loading(false)
              }
            }
            //Gia hạn dịch vụ CNTT, Gia hạn và thay đổi tốc độ -- Chuyển qua Dynamic rồi 
            /*if (loaihd_id == 41 && (kieuld_id == 131777 || kieuld_id == 13248)) {
              // Mua thêm cửa hàng
              const res = await this.ConvertResApiHBDT(api.fn_giahan_pharmacy(this.axios, { hdtb_id: this.state.hdtb_id * 1 }))
              const data_res = JSON.parse(res)
              if (data_res.ErrorCode == '00') {
                this.$toast.success('Gia hạn dịch vụ VNPT Pharmacy thành công')
                this.is_disabled = !this.is_disabled
              } else {
                this.$toast.error(data_res.TransactionId)
                return
              }
            }*/
            //Thay đổi thông tin dịch vụ CNTT -- Chuyển qua dynanmic rồi 
           /* if (loaihd_id == 41 && kieuld_id == 13179) {
              const res = await this.ConvertResApiHBDT(api.fn_thaydoithongtin_pharmacy(this.axios, { hdtb_id: this.state.hdtb_id * 1 }))
              const data_res = JSON.parse(res)
              if (data_res.ErrorCode == '00') {
                this.$toast.success('Thay đổi thông tin thành công')
                this.controls.tsbtn_KichHoat_CA.visible = !this.controls.tsbtn_KichHoat_CA.visible
              } else {
                this.$toast.error(data_res.TransactionId)
                return
              }
            }*/
            // Chấm dứt sử dụng toàn trình -- CHuyển qua Dynamic rồi 
            /*if (loaihd_id == 4 && kieuld_id == 13269) {
              const res = await this.ConvertResApiHBDT(api.fn_thanhly_pharmacy(this.axios, { hdtb_id: this.state.hdtb_id * 1 }))
              const data_res = JSON.parse(res)
              if (data_res.ErrorCode == '00') {
                this.$toast.success('Thanh lý dịch vụ thành công')
                this.controls.tsbtn_KichHoat_CA.visible = !this.controls.tsbtn_KichHoat_CA.visible
              } else {
                this.$toast.error('Không thành công: ' + data_res.TransactionId)
                return
              }
            }*/
          } catch (err) {
            this.$toast.error(err)
            this.loading(false)
          } finally {
            this.loading(false)
          }
        }
        // #region cuongpq kich hoat vbn doi tuong 2
        if (vloaitb_id == 358 /*Voice Brandname*/) {
          try {
            this.loading(true)
          console.log('voice brandname')
          let tinh_id = this.$root.token.getProperty('id_tinhthanh')
          let req = { vhdtb_id: hdtb_id }
          let req_tao_dv = { vhdtb_id: hdtb_id, so_tv: '' }
          if (kieuld_id == 14051 || kieuld_id == 14052 || kieuld_id == 19543 || kieuld_id == 19586)  {
            var kq = await this.CallAPIVBN('web-hopdong/vbn/khoi-tao-dich-vu', req_tao_dv)
            console.log('%ckq', 'color:red;font-size:20', kq.data)
            if (kq && (kq.data.error_code == 'BSS-00000500' || kq.data.data == 'Thất bại')) {
              this.$toast.error(this.unicodeToString(kq.data.message))
              return
            }
            var kq_gan_offer = await this.CallAPIVBN('web-hopdong/vbn/thuc-hien-gan-offer', req)
            console.log(kq_gan_offer)
            var kq_ccbs = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-ccbs', { hdtb_id })
            console.log(kq_ccbs)
            var kq_mediation = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-mediation', { hdtb_id, tinh_id })
            console.log(kq_mediation)
            var kq_vcc_vbr = await this.CallAPIVBN('web-hopdong/vbn/vcc_vbr', { hdtb_id })
            console.log(kq_vcc_vbr)
            this.$toast.success('Đăng ký khách hàng mới thành công')
          } else if (kieuld_id == 793) {
            var kq = await this.CallAPIVBN('web-hopdong/vbn/them-brand-name', req_tao_dv)
            if (kq && (kq.data.error_code == 'BSS-00000500' || kq.data.data == 'Thất bại')) {
              this.$toast.error(this.unicodeToString(kq.data.message))
              return
            }
            var kq_gan_offer = await this.CallAPIVBN('web-hopdong/vbn/thuc-hien-gan-offer', req)
            console.log(kq_gan_offer)
            var kq_ccbs = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-ccbs', { hdtb_id })
            console.log(kq_ccbs)
            var kq_mediation = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-mediation', { hdtb_id, tinh_id })
            console.log(kq_mediation)
            var kq_vcc_vbr = await this.CallAPIVBN('web-hopdong/vbn/vcc_vbr', { hdtb_id })
            console.log(kq_vcc_vbr)
            this.$toast.success('Đăng ký brandname mới thành công')
          } else if (kieuld_id == 19544) {
            var kq = await this.CallAPIVBN('web-hopdong/vbn/them-thue-bao', req)
            if (kq && (kq.data.error_code == 'BSS-00000500' || kq.data.data == 'Thất bại')) {
              this.$toast.error(this.unicodeToString(kq.data.message))
              return
            }
            var kq_gan_offer = await this.CallAPIVBN('web-hopdong/vbn/thuc-hien-gan-offer', req)
            console.log(kq_gan_offer)
            var kq_ccbs = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-ccbs', { hdtb_id })
            console.log(kq_ccbs)
            var kq_mediation = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-mediation', { hdtb_id, tinh_id })
            console.log(kq_mediation)
            var kq_vcc_vbr = await this.CallAPIVBN('web-hopdong/vbn/vcc_vbr', { hdtb_id })
            console.log(kq_vcc_vbr)
            this.$toast.success('Đăng ký thêm số thuê bao mới thành công')
          } else if (kieuld_id == 13179) {
            var kq = await this.CallAPIVBN('web-hopdong/vbn/thay-doi-thong-tin', req)
            if (kq && (kq.data.error_code == 'BSS-00000500' || kq.data.data == 'Thất bại')) {
              this.$toast.error(this.unicodeToString(kq.data.message))
              return
            }
            var kq_vcc_vbr = await this.CallAPIVBN('web-hopdong/vbn/vcc_vbr', { hdtb_id })
            console.log(kq_vcc_vbr)
            this.$toast.success('Thay đổi thông tin thành công')
          } else if (kieuld_id == 14054) {
            var kq = await this.CallAPIVBN('web-hopdong/vbn/thay-doi-brand-name', req)
            if (kq && (kq.data.error_code == 'BSS-00000500' || kq.data.data == 'Thất bại')) {
              this.$toast.error(this.unicodeToString(kq.data.message))
              return
            }
            var kq_vcc_vbr = await this.CallAPIVBN('web-hopdong/vbn/vcc_vbr', { hdtb_id })
            console.log(kq_vcc_vbr)
            this.$toast.success('Thay đổi brandname thành công')
          } else if (kieuld_id == 14053) {
            var kq = await this.CallAPIVBN('web-hopdong/vbn/reset-mat-khau', req)
            if (kq && (kq.data.error_code == 'BSS-00000500' || kq.data.data == 'Thất bại')) {
              this.$toast.error(this.unicodeToString(kq.data.message))
              return
            }
            var kq_vcc_vbr = await this.CallAPIVBN('web-hopdong/vbn/vcc_vbr', { hdtb_id })
            console.log(kq_vcc_vbr)
            this.$toast.success('Reset mật khẩu thành công')
          } else if (kieuld_id == 14059) {
            await this.KhoaMoVoiceBrandname()
            var kq = await this.CallAPIVBN('web-hopdong/vbn/tam-dung-hot-line', req_tao_dv)
            if (kq && (kq.data.error_code == 'BSS-00000500' || kq.data.data == 'Thất bại')) {
              this.$toast.error(this.unicodeToString(kq.data.message))
              return
            }
            var kq_ccbs = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-ccbs', { hdtb_id })
            console.log(kq_ccbs)
            var kq_mediation = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-mediation', { hdtb_id, tinh_id })
            console.log(kq_mediation)
            var kq_vcc_vbr = await this.CallAPIVBN('web-hopdong/vbn/vcc_vbr', { hdtb_id })
            console.log(kq_vcc_vbr)
            this.$toast.success('Tạm dừng hotline thành công')
          } else if (kieuld_id == 14060) {
            await this.KhoaMoVoiceBrandname()
            var kq = await this.CallAPIVBN('web-hopdong/vbn/mo-tam-dung-hot-line', req_tao_dv)
            if (kq && (kq.data.error_code == 'BSS-00000500' || kq.data.data == 'Thất bại')) {
              this.$toast.error(this.unicodeToString(kq.data.message))
              return
            }
            var kq_ccbs = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-ccbs', { hdtb_id })
            console.log(kq_ccbs)
            var kq_mediation = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-mediation', { hdtb_id, tinh_id })
            console.log(kq_mediation)
            var kq_vcc_vbr = await this.CallAPIVBN('web-hopdong/vbn/vcc_vbr', { hdtb_id })
            console.log(kq_vcc_vbr)
            this.$toast.success('Tạm dừng hotline thành công')
          } else if (kieuld_id == 14057) {
            await this.KhoaMoVoiceBrandname()
            var kq = await this.CallAPIVBN('web-hopdong/vbn/tam-dung-brand-name', req)
            if (kq && (kq.data.error_code == 'BSS-00000500' || kq.data.data == 'Thất bại')) {
              this.$toast.error(this.unicodeToString(kq.data.message))
              return
            }
            var kq_ccbs = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-ccbs', { hdtb_id })
            console.log(kq_ccbs)
            var kq_mediation = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-mediation', { hdtb_id, tinh_id })
            console.log(kq_mediation)
            var kq_vcc_vbr = await this.CallAPIVBN('web-hopdong/vbn/vcc_vbr', { hdtb_id })
            console.log(kq_vcc_vbr)
            this.$toast.success('Tạm dừng brandname thành công')
          } else if (kieuld_id == 14058) {
            await this.KhoaMoVoiceBrandname()
            var kq = await this.CallAPIVBN('web-hopdong/vbn/mo-tam-dung-brand-name', req)
            if (kq && (kq.data.error_code == 'BSS-00000500' || kq.data.data == 'Thất bại')) {
              this.$toast.error(this.unicodeToString(kq.data.message))
              return
            }
            var kq_ccbs = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-ccbs', { hdtb_id })
            console.log(kq_ccbs)
            var kq_mediation = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-mediation', { hdtb_id, tinh_id })
            console.log(kq_mediation)
            var kq_vcc_vbr = await this.CallAPIVBN('web-hopdong/vbn/vcc_vbr', { hdtb_id })
            console.log(kq_vcc_vbr)
            this.$toast.success('Mở tạm dừng brandname thành công')
          } else if (kieuld_id == 799) {
            await this.KhoaMoVoiceBrandname()
            var kq = await this.CallAPIVBN('web-hopdong/vbn/thanh-ly-brand-name', req)
            if (kq && (kq.data.error_code == 'BSS-00000500' || kq.data.data == 'Thất bại')) {
              this.$toast.error(this.unicodeToString(kq.data.message))
              return
            }
            var kq_mediation = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-mediation', { hdtb_id, tinh_id })
            console.log(kq_mediation)
            var kq_ccbs = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-ccbs', { hdtb_id })
            console.log(kq_ccbs)
            var kq_vcc_vbr = await this.CallAPIVBN('web-hopdong/vbn/vcc_vbr', { hdtb_id })
            console.log(kq_vcc_vbr)
            this.$toast.success('Hủy dịch vụ thành công')
          } else if (kieuld_id == 714) {
            // await this.KhoaMoVoiceBrandname()
            var kq = await this.CallAPIVBN('web-hopdong/vbn/huy-dich-vu', req)
            // if (kq && (kq.data.error_code == "BSS-00000500" || kq.data.data == "Thất bại")) {
            //   this.$toast.error(kq.data.dataage);
            //   return;
            // }
            var kq_mediation = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-mediation', { hdtb_id, tinh_id })
            console.log(kq_mediation)
            var kq_ccbs = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-ccbs', { hdtb_id })
            console.log(kq_ccbs)
            var kq_vcc_vbr = await this.CallAPIVBN('web-hopdong/vbn/vcc_vbr', { hdtb_id })
            console.log(kq_vcc_vbr)
            this.$toast.success('Tạm dừng dịch vụ thành công')
          } else if (kieuld_id == 715) {
            // await this.KhoaMoVoiceBrandname()
            var kq = await this.CallAPIVBN('web-hopdong/vbn/mo-lai-dich-vu', { vhdtb_id: hdtb_id, so_tv: '' })
            if (kq && (kq.data.error_code == 'BSS-00000500' || kq.data.data == 'Thất bại')) {
              this.$toast.error(this.unicodeToString(kq.data.message))
              return
            }
            var kq_mediation = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-mediation', { hdtb_id, tinh_id })
            console.log(kq_mediation)
            var kq_vcc_vbr = await this.CallAPIVBN('web-hopdong/vbn/vcc_vbr', { hdtb_id })
            console.log(kq_vcc_vbr)
            this.$toast.success('Mở tạm dừng dịch vụ thành công')
          } else if (kieuld_id == 13269) {
            await this.KhoaMoVoiceBrandname()
            var kq = await this.CallAPIVBN('web-hopdong/vbn/thanh-ly-dich-vu', req)
            if (kq && (kq.data.error_code == 'BSS-00000500' || kq.data.data == 'Thất bại')) {
              this.$toast.error(this.unicodeToString(kq.data.message))
              return
            }
            var kq_mediation = await this.CallAPIVBN('web-hopdong/vbn/dong-bo-mediation', { hdtb_id, tinh_id })
            console.log(kq_mediation)
            var kq_vcc_vbr = await this.CallAPIVBN('web-hopdong/vbn/vcc_vbr', { hdtb_id })
            console.log(kq_vcc_vbr)
            this.$toast.success('Hủy dịch vụ thành công')
          }
        } catch (error) {
                this.loading(false)
                this.$toast.error(error.mesage)
                return
              } finally {
                this.loading(false)
              }
        }
        // #end region
        //#region kich hoat ivan
        if (vloaitb_id == LOAIHINH_THUEBAO.IVAN * 1) {
          console.log('LOAIHINH_THUEBAO.IVAN')
          let req = { vhdtb_id: this.state.hdtb_id * 1 }
          let status = this.GetData(await api.fn_get_status_hdtb(this.axios, req))
          if (status == '3') {
            this.$toast.warning('Đã kích hoạt. Hãy hoàn thiện hồ sơ')
            return
          }
          if (!this.state.TenDangNhapIVAN || this.state.TenDangNhapIVAN == '') {
            this.$refs.popupDangNhapIVAN.openDialog()
            return
          }
          this.ivan_kichhoat()
        }
        //#endregion
        //#region Hóa đơn điện tử
        if (vloaitb_id == LOAIHINH_THUEBAO.TTDL_HDDT * 1) {
          try {
            console.log('LOAIHINH_THUEBAO.TTDL_HDDT')
            //#region DAT_MOI
            if (loaihd_id == LoaiHopDong.DAT_MOI) {
              let data = {
                kieuld_id: this.state.kieuld_id,
                hdtb_id: this.state.hdtb_id,
                phieu_id: this.state.phieu_id,
                thuebao_id: this.state.thuebao_id,
                ma_gd: this.state.ma_gd,
                loaitb_id: vloaitb_id,
                ma_tb : this.state.ma_tb
              }
              console.log(this.$refs.popupDangKyHoaDonDienTu78)
              this.$refs.popupDangKyHoaDonDienTu78.openDialog(data)
            }
            //#endregion
            //#region GIAHAN
            if (loaihd_id == LoaiHopDong.GIAHAN) {

              if (kieuld_id == 19599) {
                if (vstatus* 1 == 6){
                  //show form chuyen doi hoa don dien tu
                  this.$toast.error('Thuê bao đang ở trạng thái kích hoạt thành công, vui lòng không kích hoạt lại')
                  return
                }
                else {
                  this.$refs.CapBuSanLuongHDDT.openDialog({ hdtb_id: this.state.hdtb_id, mst : this.state.mst })
                  return
                }
                return
              }

              if (this.state.kieuld_id == 13288) {
                // mua them mau hoa don
                let data = {
                  kieuld_id: this.state.kieuld_id,
                  hdtb_id: this.state.hdtb_id,
                  phieu_id: this.state.phieu_id,
                  thuebao_id: this.state.thuebao_id,
                  ma_gd: this.state.ma_gd,
                  layoutControlGroup2_Enabled: false,
                  loaitb_id: vloaitb_id
                }
                this.$refs.popupDangKyHoaDonDienTu78.openDialog(data)
                return
              }
              if (vstatus != 6 && this.state.kieuld_id == 13236) {
                let dt_hddt = this.GetData(await api.lay_thongso_dkmua_them_hd(this.axios, { hdtb_id: hdtb_id }))
                if (!dt_hddt || dt_hddt.length == 0) {
                  throw 'Không có thông số hóa đơn'
                }
                dt_hddt = commonFn.LowerCasePropertyList(dt_hddt)
                let dkt = {}
                dkt.cus_id = dt_hddt[0]['cus_id']
                dkt.taxcode = dt_hddt[0]['taxcode']
                dkt.number_invoices = dt_hddt[0]['number_invoices'] * 1
                let kq = this.GetData(await api.MuaThemHoaDon(this.axios, dkt))
                let reqLog = {
                  vhdtb_id: hdtb_id,
                  verror: '',
                  vreq: JSON.stringify(dkt),
                  vres: JSON.stringify(kq),
                  vma_tb: '',
                  vghichu: 'await api.MuaThemHoaDon(this.axios, dkt)'
                }
                await api.sp_insert_log_cntt(this.axios, reqLog)
                if (!kq) {
                  // tdan.EXECUTE_SQL("UPDATE {?DB2}.hd_thuebao a set a.status = 5 WHERE a.hdtb_id = " + hdtb_id.toString());
                  let res = this.GetData(await api.update_hd_thuebao(this.axios, { hdtb_id: hdtb_id, status: 6 }))
                  if (res) {
                    this.$toast.success('Đăng ký thêm hóa đơn thành công!')
                    await this.HienThiDanhSachHDTB()
                  } else {
                    throw 'Lỗi cập nhật HĐ thuê bao!'
                  }
                } else {
                  throw 'Lỗi đăng ký! ' + kq
                }
                // this.CheckDataErrorHienThiDanhSachHDTB();
              } else {
                this.$toast.error('Thuê bao đã ở trạng thái kích hoạt thành công. Vui lòng không kích hoạt lại.')
                return
              }
            }
            //#endregion
            //#region THAY_DOI_DV
            if (loaihd_id == LoaiHopDong.THAY_DOI_DV) {
              if (kieuld_id == 714) {
                // tạm ngừng
                if (vstatus != 6) {
                  let dt_hddt = this.GetData(await api.lay_thongso_dkmua_them_hd(this.axios, { vhdtb_id: hdtb_id }))
                  if (!dt_hddt || dt_hddt.length == 0) {
                    throw 'Không có thông số hóa đơn'
                  }
                  dt_hddt = commonFn.LowerCasePropertyList(dt_hddt)
                  let dkt = {}
                  dkt.cus_id = dt_hddt[0]['cus_id']
                  dkt.taxcode = dt_hddt[0]['taxcode']
                  let kq = this.GetData(await api.TamNgungDichVuHDDT(this.axios, dkt))
                  if (kq) {
                    // tdan.EXECUTE_SQL("UPDATE {?DB2}.hd_thuebao a set a.status = 5 WHERE a.hdtb_id = " + hdtb_id.toString());
                    let res = this.GetData(await api.update_hd_thuebao(this.axios, { hdtb_id: hdtb_id, status: 6 }))
                    if (res) {
                      this.$toast.success('Tạm ngừng dịch vụ thành công!')
                      await this.HienThiDanhSachHDTB()
                    } else {
                      throw 'Lỗi cập nhật HĐ thuê bao!'
                    }
                  } else {
                    throw 'Lỗi tạm ngừng dịch vụ!'
                  }
                } else {
                  this.$toast.error('Thuê bao đã ở trạng thái kích hoạt thành công. Vui lòng không kích hoạt lại.')
                  return
                }
              }
              if (kieuld_id == 715) {
                // khôi phục
                if (vstatus != 6) {
                  let dt_hddt = this.GetData(await api.lay_thongso_dkmua_them_hd(this.axios, { vhdtb_id: hdtb_id }))
                  if (!dt_hddt || dt_hddt.length == 0) {
                    throw 'Không có thông số hóa đơn'
                  }
                  dt_hddt = commonFn.LowerCasePropertyList(dt_hddt)
                  let dkt = {}
                  dkt.cus_id = dt_hddt[0]['cus_id']
                  dkt.taxcode = dt_hddt[0]['taxcode']
                  let kq = this.GetData(await api.KhoiPhucDichVuHDDT(this.axios, dkt))
                  if (!kq) {
                    // tdan.EXECUTE_SQL("UPDATE {?DB2}.hd_thuebao a set a.status = 5 WHERE a.hdtb_id = " + hdtb_id.toString());
                    let res = this.GetData(await api.update_hd_thuebao(this.axios, { hdtb_id: hdtb_id, status: 6 }))
                    if (res) {
                      this.$toast.success('Khôi phục dịch vụ thành công!')
                      await this.HienThiDanhSachHDTB()
                    } else {
                      throw 'Lỗi cập nhật HĐ thuê bao!'
                    }
                  } else {
                    throw 'Lỗi Khôi phục dịch vụ! ' + kq
                  }
                } else {
                  this.$toast.error('Thuê bao đã ở trạng thái kích hoạt thành công. Vui lòng không kích hoạt lại.')
                  return
                }
              }
            }
            //#endregion
            //#region CHAMDUT_SD
            if (loaihd_id == LoaiHopDong.CHAMDUT_SD) {
              if (vstatus != 6) {
                let dt_hddt = this.GetData(await api.lay_thongso_dkmua_them_hd(this.axios, { vhdtb_id: hdtb_id }))
                if (!dt_hddt || dt_hddt.length == 0) {
                  throw 'Không có thông số hóa đơn'
                }
                dt_hddt = commonFn.LowerCasePropertyList(dt_hddt)
                let dkt = {}
                dkt.cus_id = dt_hddt[0]['cus_id']
                dkt.taxcode = dt_hddt[0]['taxcode']
                let kq = this.GetData(await api.KhoaDichVuHDDT(this.axios, dkt))
                if (!kq) {
                  // tdan.EXECUTE_SQL("UPDATE {?DB2}.hd_thuebao a set a.status = 5 WHERE a.hdtb_id = " + hdtb_id.toString());
                  let res = this.GetData(await api.update_hd_thuebao(this.axios, { hdtb_id: hdtb_id, status: 6 }))
                  if (res) {
                    this.$toast.success('Khóa dịch vụ thành công!')
                    await this.HienThiDanhSachHDTB()
                  } else {
                    throw 'Lỗi cập nhật HĐ thuê bao!'
                  }
                } else {
                  throw 'Lỗi Khóa dịch vụ! ' + kq
                }
              } else {
                this.$toast.error('Thuê bao đã ở trạng thái kích hoạt thành công. Vui lòng không kích hoạt lại.')
                return
              }
            }
            //#endregion
          } catch (e) {
            this.$toast.error('Đã có lỗi xảy ra: ' + e.message)
          } finally {
            this.loading(false)
          }
        }
        //#endregion
        //#region vi ngan sach tai nguyen
        if (vloaitb_id == 318 && this.state.kieuld_id == 13266) {
          smartca.TaoGoiNganSachTaiNguyen(this.axios, this.state.hdtb_id, this)
          return
        }
        if ((vloaitb_id == 318) & (this.state.kieuld_id == 14011)) {
          smartca.MuaThemLuotKyGoiTaiNguyen(this.axios, this.state.hdtb_id, this)
          return
        }
        //#endregion vi ngan sach tai nguyen
        //#region hkd - toan trinh
        if (vloaitb_id == 316 /*sms a2p*/) {
          if (this.state.kieuld_id == 10000) {
            /*khai báo hình thức gửi tin*/ this.$refs.frm_Create_Agent_SMPP.showModal()
          } else if (this.state.kieuld_id == 793) {
            /*khai báo brandname*/ let data = { hdtb_id: this.state.hdtb_id }
            this.$refs.frm_Create_Label_A2p.openDialog(data)
          }
          if (kieuld_id == 794) {
            let data = { hdtb_id: this.state.hdtb_id }

            this.$refs.frm_Tao_DHTT_A2P.is_a2p = true;
            this.$refs.frm_Tao_DHTT_A2P.showModal(data)
          }
          if (kieuld_id == 795) {
            let data = { hdtb_id: this.state.hdtb_id }
            this.$refs.frm_Giahan_DHTT_A2P.is_a2p = true;
            this.$refs.frm_Giahan_DHTT_A2P.showModal(data)
          }
          else if (this.state.kieuld_id == 10006) {
            /*thay đổi thông tin a2p*/ let data = { hdtb_id: this.state.hdtb_id }
            this.$refs.frm_TDTT_Label_A2P.openDialog(data)
          }
          return
        }
        if (vloaitb_id == 302) {
          let data = {
            kieuld_id: this.state.kieuld_id,
            hdtb_id: this.state.hdtb_id,
            phieu_id: this.state.phieu_id,
            thuebao_id: this.state.thuebao_id,
            ma_gd: this.state.ma_gd
          }
          if (data.kieuld_id == 13266) {
            this.$refs.frmDangKyHKD.openDialog(data)
          }
          if (data.kieuld_id == 13248) {
            await hkd.GiaHanHKD(this, data)
          }
          if (data.kieuld_id == 14011) {
            await hkd.TangSoLuongHoaDonHKD(this, data)
          }
          if (data.kieuld_id == 14016) {
            await hkd.ThayDoiGoiCuocHKD(this, data)
          }
          // await this.DatMoiHKD();
        }
        // #end region hkd
        //begin iqms
        if (vloaitb_id == 303) {
          // IQMS
          let data = {
            kieuld_id: this.state.kieuld_id,
            hdtb_id: this.state.hdtb_id,
            phieu_id: this.state.phieu_id,
            thuebao_id: this.state.thuebao_id,
            ma_gd: this.state.ma_gd
          }
          if (loaihd_id == LoaiHopDong.DAT_MOI && kieuld_id == KieuLapDat.DATMOI_CNTT_TOANTRINH) {
            // dat moi dich vu cntt toan trinh
            if (data.kieuld_id == 13266) {
              this.$refs.frmDangKyIQMS.openDialog(data)
            }
            //await iqms.DatMoiIQMS(this, data)
          }
          if (loaihd_id == LoaiHopDong.GIAHAN && kieuld_id == KieuLapDat.GIAHAN_TD_GOICUOC_CNTT) {
            await iqms.GiaHanIQMS(this, data)
          }
          if (loaihd_id == LoaiHopDong.GIAHAN && kieuld_id == KieuLapDat.CHUYENDUNG_CHINHTHUC_CNTT) {
            await iqms.ChuyenChinhThucIQMS(this, data)
          }
          if (loaihd_id == LoaiHopDong.GIAHAN && kieuld_id == KieuLapDat.THAYDOI_SOLUONG_DVCNTT) {
            await iqms.ThayDoiSoLuongPhongIQMS(this, data)
          }
          if (loaihd_id == LoaiHopDong.GIAHAN && kieuld_id == KieuLapDat.THAYDOI_THONGTIN_DVCNTT) {
            await iqms.ThayDoiThongTinIQMS(this, data)
          }
          if (loaihd_id == LoaiHopDong.CHAMDUT_SD && kieuld_id == KieuLapDat.THANHLY_CNTT_TOANTRINH) {
            await iqms.ThanhLyIQMS(this, data)
          }
        }
        //end iqms
        //begin gomeet
        if (vloaitb_id == 322) {
          // IQMS
          let data = {
            kieuld_id: this.state.kieuld_id,
            hdtb_id: this.state.hdtb_id,
            phieu_id: this.state.phieu_id,
            thuebao_id: this.state.thuebao_id,
            ma_gd: this.state.ma_gd
          }
          if (loaihd_id == LoaiHopDong.DAT_MOI && kieuld_id == KieuLapDat.DATMOI_CNTT_TOANTRINH) {
            // dat moi dich vu cntt toan trinh
            await gomeet.DatMoiGoMeet(this, data)
          }
          if (loaihd_id == LoaiHopDong.GIAHAN && kieuld_id == KieuLapDat.GIAHAN_TD_GOICUOC_CNTT) {
            await gomeet.GiaHanGoMeet(this, data)
          }
          if (loaihd_id == LoaiHopDong.CHAMDUT_SD && kieuld_id == KieuLapDat.THANHLY_CNTT_TOANTRINH) {
            await gomeet.ThanhLyGoMeet(this, data)
          }
          if (loaihd_id == LoaiHopDong.GIAHAN && kieuld_id == KieuLapDat.DOI_GOI_CUOC) {
            await gomeet.DoiGoiCuocGoMeet(this, data)
          }
        }
        //end gomeet
        //begin ekyc
        if (vloaitb_id == 276) {
          // ekyc
          let data = {
            kieuld_id: this.state.kieuld_id,
            hdtb_id: this.state.hdtb_id,
            phieu_id: this.state.phieu_id,
            thuebao_id: this.state.thuebao_id,
            ma_gd: this.state.ma_gd
          }
          if (loaihd_id == LoaiHopDong.DAT_MOI && kieuld_id == KieuLapDat.DATMOI_CNTT_TOANTRINH) {
            // dat moi dich vu cntt toan trinh
            await ekyc.DatMoiEKYC(this, data)
          }
          if (loaihd_id == LoaiHopDong.GIAHAN && kieuld_id == KieuLapDat.GIAHAN_TD_GOICUOC_CNTT) {
            await ekyc.GiaHanEKYC(this, data)
          }
          if (loaihd_id == LoaiHopDong.GIAHAN && kieuld_id == KieuLapDat.TANG_SL_TAINGUYEN) {
            await ekyc.ThemREQ(this, data)
          }
          if (loaihd_id == LoaiHopDong.CHAMDUT_SD && kieuld_id == KieuLapDat.THANHLY_CNTT_TOANTRINH) {
            await ekyc.ThanhLyEKYC(this, data)
          }
        }
        //end ekyc
        //#region CA and Smart CA
        if (vloaitb_id == LOAIHINH_THUEBAO.SMART_CA * 1) {
          let kt_thunghiem = await api.kiemtra_nangcap_phienban(this.axios, { ma_dv: 'smartca_v2', hdtb_id: hdtb_id })
          //let kt_thunghiem = 'ok';
          if (kt_thunghiem.data.data == 'ok') {
            let data = {
              luong_thaotac: this.state.luong_thaotac,
              luong_id: this.state.luong_id,
              loaihd_id: this.state.loaihd_id,
              kieuld_id: this.state.kieuld_id,
              hdtb_id: this.state.hdtb_id,
              hdkh_id: this.state.hdkh_id,
              thuebao_id: this.state.thuebao_id,
              ma_tinh: this.tt_nd.ma_tinh,
              nv_thaotac: this.tt_nd.ma_nd
            }
            this.loading(true)
            /*  if (data.kieuld_id == 13266) {
                    kq = await smartca.RASmartCA_V2(this, data)
                  }
                  else
                  {
                    kq = await smartca.RADynamicAPI(this, data)
                  }*/
            if (data.kieuld_id == 14074) {
              kq = await api.cap_nhat_status_hdtb(this.axios, {
                vhdtb_id: data.hdtb_id,
                vstatus: 3
              })
              this.$toast.success('RA Thành công')
            } else {
              // xu ly duyet smartca full ho so
              let kt_duyet_ra_auto = await api.kiemtra_hoso_du(this.axios, {
                vhdtb_id: data.hdtb_id,
                vhdkh_id: data.hdkh_id
              })
              if (kt_duyet_ra_auto.data.data != 'OK') {
                // duyet khong du ho so, can ekyc
                kq = await smartca.RADynamicAPI(this, data)
              } else {
                await this.tsbtnChapNhan_Click()
                kq = await api.ra_ca_full_hoso(this.axios, {
                  vhdtb_id: data.hdtb_id,
                  vra_user: this.tt_nd.ma_nd
                })
                console.log(kq.data)
                let kq_ra = JSON.parse(kq.data.data)
                if (kq_ra.error_code == 200) {
                  // bo hd_thuebao da giao phieu khoi luoi
                  this.datagrid.ds_hdtb.list = [...this.datagrid.ds_hdtb.list.filter((x) => x.hdtb_id != data.hdtb_id)]
                  this.$toast.success('RA full hồ sơ thành công, phiếu đã giao qua CA')
                } else {
                  throw Error(kq_ra.error_message)
                }
              }
              // kq = await smartca.RADynamicAPI(this, data)
            }
            this.loading(false)
            return
          }
        }
        if (vloaitb_id == LOAIHINH_THUEBAO.CA * 1 || vloaitb_id == LOAIHINH_THUEBAO.SMART_CA * 1) {
          //   if (vloaitb_id == LOAIHINH_THUEBAO.CA * 1 ) {
          console.log('LOAIHINH_THUEBAO.CA | SMART_CA', loaihd_id)
          let data = {
            luong_thaotac: this.state.luong_thaotac,
            luong_id: this.state.luong_id,
            loaihd_id: this.state.loaihd_id,
            kieuld_id: this.state.kieuld_id,
            hdtb_id: this.state.hdtb_id,
            thuebao_id: this.state.thuebao_id,
            ma_tinh: this.tt_nd.ma_tinh
          }
          let rs_trangthai = await lay_trangthai_hdtb(hdtb_id)
          let vtthd_id = rs_trangthai[0].tthd_id
          switch (loaihd_id * 1) {
            case 1: // đặt mới
              //if (await this.PHAILAM(this.state.luong_id, 'TSYC_CATOKEN')) {
              if (vtthd_id == 5) {
                //this.loading(true)
                let kq = await ca.YeuCauDuLieuCA(this, data)
                this.$toast.success(kq)
              } else {
                if (vtthd_id != 11) return this.$toast.error('Trạng thái hợp đồng ' + vtthd_id + ' không đúng điều kiện để kích hoạt')
                this.$bvModal
                  .msgBoxConfirm('Bạn có chắc là sẽ kích hoạt dịch vụ CA cho khách hàng?', {
                    title: '',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                    centered: true
                  })
                  .then(async (v) => {
                    if (!v) {
                      return
                    }
                    try {
                      this.loading(true)
                      let kq = await ca.Tao_Moi_CA(this, data)
                      this.$toast.success(kq)
                    } catch (ex) {
                      commonFn.showException(this, ex)
                    } finally {
                      this.loading(false)
                    }
                    ////this.loading(false)
                  })
              }
              break
            case 41: // gia hạn
              if (kieuld_id == 13177 || kieuld_id == 13248) {
                // thêm kiểu gia hạn và dổi tốc độ
                //  if (await this.PHAILAM(this.state.luong_id, 'TSYC_CATOKEN')) {
                if (vtthd_id == 5) {
                  //this.loading(true)
                  let kq = await ca.YeuCauDuLieuCA(this, data)
                  this.$toast.success(kq)
                } else {
                  if (vtthd_id != 11) return this.$toast.error('Trạng thái hợp đồng ' + vtthd_id + ' không đúng điều kiện để kích hoạt')
                  this.$bvModal
                    .msgBoxConfirm('Bạn có chắc là sẽ gia hạn dịch vụ CA cho khách hàng?', {
                      title: '',
                      size: 'sm',
                      okTitle: 'Đồng ý',
                      cancelTitle: 'Hủy',
                      centered: true
                    })
                    .then(async (v) => {
                      if (!v) {
                        return
                      }
                      try {
                        this.loading(true)
                        let kq = await ca.GiaHanCA(this, data)
                        this.$toast.success(kq)
                      } catch (ex) {
                        commonFn.showException(this, ex)
                      } finally {
                        this.loading(false)
                      }
                      ////this.loading(false)
                    })
                }
              }
              if (kieuld_id == 13261) {
                // thay doi giay to
                //  if (await this.PHAILAM(this.state.luong_id, 'TSYC_CATOKEN')) {
                if (vtthd_id == 5) {
                  //this.loading(true)
                  let kq = await ca.YeuCauDuLieuCA(this, data)
                  this.$toast.success(kq)
                } else {
                  if (vtthd_id != 11) return this.$toast.error('Trạng thái hợp đồng ' + vtthd_id + ' không đúng điều kiện để kích hoạt')
                  this.$bvModal
                    .msgBoxConfirm('Bạn có chắc là sẽ kích hoạt thay đổi số giấy tờ CA cho khách hàng?', {
                      title: '',
                      size: 'sm',
                      okTitle: 'Đồng ý',
                      cancelTitle: 'Hủy',
                      centered: true
                    })
                    .then(async (v) => {
                      if (!v) {
                        return
                      }
                      try {
                        //this.loading(true)
                        let kq = await ca.THAY_DOI_UID_CA(this, data)
                        this.$toast.success(kq)
                      } catch (ex) {
                        commonFn.showException(this, ex)
                      }
                      ////this.loading(false)
                    })
                }
              }
              if (kieuld_id == 13179) {
                // if (await this.PHAILAM(this.state.luong_id, 'TSYC_CATOKEN')) {
                if (vtthd_id == 5) {
                  //this.loading(true)
                  let kq = await ca.YeuCauDuLieuCA(this, data)
                  this.$toast.success(kq)
                } else {
                  if (vtthd_id != 11) return this.$toast.error('Trạng thái hợp đồng ' + vtthd_id + ' không đúng điều kiện để kích hoạt')
                  const isConfirm = await this.$bvModal.msgBoxConfirm(`Bạn có chắc là sẽ gửi yêu cầu thay đổi thông tin dịch vụ CA?`, {
                    title: 'Thông báo',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                    centered: true
                  })
                  if (isConfirm) {
                    try {
                      //this.loading(true)
                      let kq = await ca.ThayDoiThongTinCA(this, data)
                      this.$toast.success(kq)
                      ////this.loading(false)
                    } catch (ex) {
                      commonFn.showException(this, ex)
                    } finally {
                      this.loading(false)
                    }
                  }
                }
              }
              break
            case 7: // thay đổi dịch vụ (tạm ngừng, khôi phục)
              if (kieuld_id == 714) {
                // if (await this.PHAILAM(this.state.luong_id, 'TSYC_CATOKEN')) {
                if (vtthd_id == 5) {
                  let kq = await ca.YeuCauDuLieuCA(this, data)
                  this.$toast.success(kq)
                } else {
                  const isConfirm = await this.$bvModal.msgBoxConfirm(`Bạn có chắc là sẽ tạm dừng dịch vụ CA cho khách hàng?`, {
                    title: 'Thông báo',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                    centered: true
                  })
                  if (isConfirm) {
                    try {
                      this.loading(true)
                      let kq = await ca.TamDungCA(this, data)
                      this.$toast.success(kq)
                    } catch (ex) {
                      commonFn.showException(this, ex)
                    } finally {
                      this.loading(false)
                    }
                    ////this.loading(false)
                  }
                }
              }
              if (kieuld_id == 715) {
                //if (await this.PHAILAM(this.state.luong_id, 'TSYC_CATOKEN')) {
                if (vtthd_id == 5) {
                  //this.loading(true)
                  let kq = await ca.YeuCauDuLieuCA(this, data)
                  this.$toast.success(kq)
                } else {
                  const isConfirm = await this.$bvModal.msgBoxConfirm(`Bạn có chắc là sẽ khôi phục dịch vụ CA cho khách hàng?`, {
                    title: 'Thông báo',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                    centered: true
                  })
                  if (isConfirm) {
                    try {
                      this.loading(true)
                      let kq = await ca.KhoiPhucCA(this, data)
                      this.$toast.success(kq)
                    } catch (ex) {
                      commonFn.showException(this, ex)
                    } finally {
                      this.loading(false)
                    }
                    ////this.loading(false)
                  }
                }
              }
              break
            case 4:
              // if (await this.PHAILAM(this.state.luong_id, 'TSYC_CATOKEN')) {
              if (vtthd_id == 5) {
                //this.loading(true)
                let kq = await ca.YeuCauDuLieuCA(this, data)
                this.$toast.success(kq)
              } else {
                const isConfirm = await this.$bvModal.msgBoxConfirm(`Bạn có chắc là sẽ thu hồi dịch vụ CA của khách hàng?`, {
                  title: 'Thông báo',
                  size: 'sm',
                  okTitle: 'Đồng ý',
                  cancelTitle: 'Hủy',
                  centered: true
                })
                if (isConfirm) {
                  try {
                    this.loading(true)
                    let kq = await ca.ThuHoiCA(this, data)
                    this.$toast.success(kq)
                  } catch (ex) {
                    commonFn.showException(this, ex)
                  } finally {
                    this.loading(false)
                  }
                  ////this.loading(false)
                }
              }
              break
            default:
              this.$toast.error('Không có quy trình hiện tại!')
              break
          }
        }
        //#endregion
        //#region Dịch vụ VNEdu Enrollment
        if (vloaitb_id == LoaiHinhTB.VNPT_Enrollment) {
          //let thongbaokichhoat = tdan.GET_VALUE_FUNC("{?DB2}.HIENTHI_THONGBAO_DICHVU", "vhdtb_id", hdtb_id);
          // if (Message_Box.ShowQuestion(thongbaokichhoat.toString()) == DialogResult.No)
          // {
          //     return;
          // }
          this.$bvModal
            .msgBoxConfirm('Bạn có chắc là sẽ kích hoạt dịch vụ VNPT Enrollment cho khách hàng?', {
              title: '',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy',
              centered: true
            })
            .then(async (v) => {
              if (!v) {
                return
              }
              try {
                //this.loading(true)
                await this.KichHoatEnrollment()
              } catch (ex) {
                commonFn.showException(this, ex)
              }
              ////this.loading(false)
            })
        }
        //#endregion
        //#region Kích hoạt Elearning
        if (vloaitb_id == LOAIHINH_THUEBAO.ELEARNING * 1) {
          try {
            let req = ''
            let kq = {}
            let loaihd_id = this.state.loaihd_id
            let kieuld_id = this.state.kieuld_id
            let hdtb_id = this.state.hdtb_id
            console.log('LOAIHINH_THUEBAO.ELEARNING')
            let checkkqgetkieuld = this.GetData(await api.fn_get_kieuld_nhapbosung_elearning(this.axios, { vkieuld_id: kieuld_id }))
            if (checkkqgetkieuld && checkkqgetkieuld.trim() == 'NOK') {
              if ((await this.PHAILAM(luong_id, 'CREAT_ISSUE')) && (await this.PHAILAM(luong_id, 'KIEMTRA_JIRA_HTHS'))) {
                this.$toast.error('Bạn thực hiện dịch vụ với quy trình không phải toàn trình. Liên hệ Admin để thực hiện toàn trình')
                return
              } else {
                let status = this.GetData(await api.fn_get_status_hdtb_elearning(this.axios, { vhdtb_id: hdtb_id }))
                if (status == 3) {
                  this.$toast.warning('Đã kích hoạt')
                  ////this.loading(false)
                  return
                }
                if (loaihd_id == LOAI_HOPDONG.DAT_MOI || (loaihd_id == LOAI_HOPDONG.GIAHAN && kieuld_id == 13177) || (loaihd_id == LOAI_HOPDONG.GIAHAN && kieuld_id == 13248) || (loaihd_id == LOAI_HOPDONG.GIAHAN && kieuld_id == 13189)) {
                  let thongbaokichhoat = this.GetData(await api.fn_thongbao_kichhoat_elearning(this.axios, { vhdtb_id: hdtb_id }))
                  ////this.loading(false)
                  // thongbaokichhoat = thongbaokichhoat.replace(new RegExp('\r?\n','g'), '<br />')
                  const isConfirm = await this.$bvModal.msgBoxConfirm(thongbaokichhoat, {
                    title: 'Bạn có chắc chắn kích hoạt dịch vụ VNPT ELearning cho khách hàng?',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                    centered: true
                  })
                  if (!isConfirm) {
                    ////this.loading(false)
                    return
                  }
                  //this.loading(true)
                  // let kq = await this.getData_Elearnig(hdtb_id);
                  // console.log(kq)
                  // if (kq && kq.toLowerCase() == "ok"){
                  //   this.$toast.success("Kích hoạt thành công");
                  // }
                  // else{
                  //   this.$toast.error(kq ? kq : "Lỗi không xác định!");
                  // }
                  //bỏ gọi hàm getData_Elearnig, DUY đã gộp lại 1 hàm kích hoạt
                  this.loading(true)
                  let kq = this.GetData(await api.fn_kichhoat_elearning(this.axios, { vhdtb_id: hdtb_id }))
                  if (kq && (kq.toLowerCase() == 'ok' || kq.indexOf('thành công') > 0)) {
                    this.$toast.success('Kích hoạt thành công')
                  } else {
                    this.$toast.error(kq ? kq : 'Lỗi không xác định!')
                  }
                }
                if ((loaihd_id == 7 && kieuld_id == 785) || (loaihd_id == 41 && kieuld_id == 13236) || (loaihd_id == 41 && kieuld_id == 14446)) {
                  try {
                    req = this.GetData(
                      await api.tao_req_cntt(this.axios, {
                        hdtb_id: hdtb_id,
                        vhdtb_id: hdtb_id
                      })
                    )
                    if (!req || req == '') {
                      this.$toast.error('Không tạo được request. Hãy kiểm tra lại hàm tạo request')
                      return
                    }
                    if (req == 'NOK') {
                      this.$toast.error('Thuê bao đã kích hoạt thành công')
                      return
                    } else {
                      let reqLog = {
                        vhdtb_id: hdtb_id,
                        verror: 'Tạo req',
                        vreq: req,
                        vres: '',
                        vma_tb: '',
                        vghichu: 'Tạo req đặt mới dịch vụ Elearning'
                      }
                      //debugger
                      let thongbaokichhoat = this.GetData(
                        await api.fn_thongbao_kichhoat_elearning(this.axios, {
                          vhdtb_id: hdtb_id
                        })
                      )
                      //this.loading(false)
                      // thongbaokichhoat = thongbaokichhoat.replace(new RegExp('\r?\n','g'), '<br />')
                      const isConfirm = await this.$bvModal.msgBoxConfirm(thongbaokichhoat, {
                        title: 'Bạn có chắc chắn kích hoạt dịch vụ VNPT ELearning cho khách hàng?',
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Hủy',
                        centered: true
                      })
                      if (!isConfirm) {
                        //this.loading(false)
                        return
                      }
                      //this.loading(true)
                      let apiLoaiHD = 'DVGT'
                      this.GetData(await api.sp_insert_log_cntt(this.axios, reqLog))
                      kq = this.GetData(await api.VNPT_Enrollment(this.axios, apiLoaiHD, JSON.parse(req)))
                      kq = JSON.parse(kq)
                      let kqdk = kq
                      if (kqdk.success == true) {
                        this.GetData(
                          await api.cap_nhat_status_hdtb(this.axios, {
                            vhdtb_id: hdtb_id,
                            vstatus: 6
                          })
                        )
                        this.$toast.success(kqdk.msg)
                        reqLog = {
                          vhdtb_id: hdtb_id,
                          verror: 'Thành công',
                          vreq: req,
                          vres: JSON.stringify(kq),
                          vma_tb: '',
                          vghichu: 'Kích hoạt thành công dịch vụ E-Learning'
                        }
                        this.GetData(await api.sp_insert_log_cntt(this.axios, reqLog))
                      } else {
                        this.$toast.error(kqdk.msg)
                      }
                    }
                  } catch (ex) {
                    commonFn.showException(this, ex)
                    let reqLog = {
                      vhdtb_id: hdtb_id,
                      verror: 'Thất bại',
                      vreq: req,
                      vres: JSON.stringify(kq),
                      vma_tb: '',
                      vghichu: 'Kích hoạt thất bại dịch vụ Enrollment'
                    }
                    this.GetData(await api.sp_insert_log_cntt(this.axios, reqLog))
                    return
                  }
                  //this.loading(false)
                }
                if (loaihd_id == 4 && kieuld_id == 13269) {
                  try {
                    req = this.GetData(
                      await api.tao_req_cntt(this.axios, {
                        hdtb_id: hdtb_id,
                        vhdtb_id: hdtb_id
                      })
                    )
                    if (!req || req == '') {
                      this.$toast.error('Không tạo được request. Hãy kiểm tra lại hàm tạo request')
                      return
                    }
                    if (req == 'NOK') {
                      this.$toast.error('Thuê bao đã kích hoạt thành công')
                      return
                    } else {
                      let reqLog = {
                        vhdtb_id: hdtb_id,
                        verror: 'Tạo req',
                        vreq: req,
                        vres: '',
                        vma_tb: '',
                        vghichu: 'Tạo req đặt mới dịch vụ Elearning'
                      }
                      //this.loading(false)
                      //this.loading(true)
                      let apiLoaiHD = 'THANHLY'
                      this.GetData(await api.sp_insert_log_cntt(this.axios, reqLog))
                      kq = this.GetData(await api.VNPT_Enrollment(this.axios, apiLoaiHD, JSON.parse(req)))
                      kq = JSON.parse(kq)
                      let kqdk = kq
                      if (kqdk.success == true) {
                        this.GetData(
                          await api.cap_nhat_status_hdtb(this.axios, {
                            vhdtb_id: hdtb_id,
                            vstatus: 6
                          })
                        )
                        this.$toast.success(kqdk.msg)
                        reqLog = {
                          vhdtb_id: hdtb_id,
                          verror: 'Thành công',
                          vreq: req,
                          vres: JSON.stringify(kq),
                          vma_tb: '',
                          vghichu: 'Kích hoạt thành công thanh lý dịch vụ E-Learning'
                        }
                        this.GetData(await api.sp_insert_log_cntt(this.axios, reqLog))
                      } else {
                        this.$toast.error(kqdk.msg)
                      }
                    }
                  } catch (ex) {
                    commonFn.showException(this, ex)
                    let reqLog = {
                      vhdtb_id: hdtb_id,
                      verror: 'Thất bại',
                      vreq: req,
                      vres: JSON.stringify(kq),
                      vma_tb: '',
                      vghichu: 'Kích hoạt thất bại dịch vụ Enrollment'
                    }
                    this.GetData(await api.sp_insert_log_cntt(this.axios, reqLog))
                    return
                  }
                  //this.loading(false)
                }
                //this.loading(false)
              }
            }
            this.loading(false)
          } catch (ex) {
            this.loading(false)
            this.$toast.error('Đã có lỗi xảy ra' + ex.message)
          } finally {
            this.loading(false)
          }
        }
        // #endregion
        // KichHoatDichVuKas
        if (vloaitb_id == LoaiHinhTB.BAOMAT_KAS) {
          //this.loading(true)
          let req = {}
          var ttKichHoat = this.GetData(
            await api.fn_lay_thongtin_kichhoat_bhs_kas(this.axios, {
              p_ds_para: JSON.stringify({ HDTB_ID: hdtb_id }),
              p_ds_ttnd: JSON.stringify({
                NHANVIEN_ID: this.tt_nd.nhanvien_id,
                DONVI_ID: this.tt_nd.donvi_id,
                NGUOI_CN: this.tt_nd.nguoi_cn,
                MAY_CN: this.tt_nd.may_cn,
                IP_CN: this.tt_nd.ip_cn
              })
            })
          )
          if (!ttKichHoat) {
            //this.loading(false)
            this.$toast.error('Không lấy được thông tin kích hoạt dịch vụ!')
            return
          }
          ttKichHoat = JSON.parse(ttKichHoat.toLowerCase())
          console.log('ttKichHoat', ttKichHoat)
          if (ttKichHoat.error_code !== 1 || !ttKichHoat.result) {
            //this.loading(false)
            this.$toast.error(JSON.stringify(ttKichHoat))
            return
          }
          ttKichHoat = ttKichHoat.result
          req.productCode = ttKichHoat.matocdo
          req.custCode = ttKichHoat.vma_tb
          req.phone = ttKichHoat.so_dt
          if (req.phone && req.phone.startsWith('0')) req.phone = '84' + req.phone.substring(1)
          req.email = ttKichHoat.email
          req.fullname = this.state.selectedPhieu.ten_tb.trim()
          req.address = this.state.selectedPhieu.diachi_tb.trim()
          req.refId = hdtb_id
          //Lấy thông tin đặt cọc, nếu có đặt cọc thì kiểm tra đăng ký đặt cọc mấy tháng để gán lại productCode
          var ds = this.GetData(await api.lay_ds_chitiet_km_theo_tbid(this.axios, { vthuebao_id: hdtb_id, vkieu: 3 }))
          if (ds && ds.length > 0) {
            if (ds[0]['loai_km'] && ds[0]['loai_km'].toString().trim() == 'dat coc') {
              /*if (ds[0]['huong_dc'] && ds[0]['huong_dc'].toString() == '8') {
                if (req.productCode == '2022010701') req.productCode = '2022010801'
                if (req.productCode == '2022010702') req.productCode = '2022010802'
                if (req.productCode == '2022010703') req.productCode = '2022010803'
                if (req.productCode == '2022010704') req.productCode = '2022010804'
                if (req.productCode == '2022010705') req.productCode = '2022010805'
              } else {
                if (req.productCode == '2022010701') req.productCode = '2022010901'
                if (req.productCode == '2022010702') req.productCode = '2022010902'
                if (req.productCode == '2022010703') req.productCode = '2022010903'
                if (req.productCode == '2022010704') req.productCode = '2022010904'
                if (req.productCode == '2022010705') req.productCode = '2022010905'
              }
            }*/
              if (ds[0]['huong_dc']) {
                req.productCode = this.GetData(
                  await api.get_kas_product_code(this.axios, {
                    vproduct_code_init: req.productCode,
                    vsothang_datcoc: ds[0]['huong_dc'].toString()
                  })
                )
                console.log(req.productCode)
              }
            }
          }
          if (this.state.loaihd_id == 4) {
            let param = {}
            param.vcustCode = ttKichHoat.vma_tb
            param.vhdtb_id = hdtb_id
            let respData = await api.kichHoatThanhLyKas(this.axios, param)
            console.log(respData)
            if (respData.data.data == 'ok') {
              this.$toast.success('Kích hoạt dịch vụ Bảo mật KAS thành công!')
              this.controls.tsbtn_KichHoat_CA.enabled = false
            }
          } else {
            let respData = this.GetData(await api.vnptfiber_kichhoat_dichvu(this.axios, req))
            if (respData && respData.subscriptionId) {
              this.$toast.success('Kích hoạt dịch vụ Bảo mật KAS thành công!')
              this.controls.tsbtn_KichHoat_CA.enabled = false
            } else {
              //this.loading(false)
              if (respData) {
                this.$toast.error(respData.message)
              }
              return
            }
            //Kích hoạt thành công thì cập nhật ngày KH, Cập nhật ngày HT
            respData = this.GetData(
              await api.capnhat_ngaykh_ngay_ht_v2(this.axios, {
                hdtb_id: hdtb_id
              })
            )
          }
        }
        if (vloaitb_id == LoaiHinhTB.HocBaDienTu) {
          let truong_id = 0
          let sl_hocsinh_dk = 0
          if ((loaihd_id == LoaiHopDong.DAT_MOI && kieuld_id == 13266) || (loaihd_id == LoaiHopDong.GIAHAN && kieuld_id == 14011) || (loaihd_id == LoaiHopDong.GIAHAN && kieuld_id == 14012)) {
            this.tab_dvcntt.list.forEach((item) => {
              //console.log(item)
              if (item.field_name == 'MA_PHU') {
                truong_id = item.field_value
                console.log(truong_id)
              }
              if (item.field_name == 'SL_MAILING') {
                sl_hocsinh_dk = item.field_value
                console.log(sl_hocsinh_dk)
              }
            })
            if (truong_id == 0 || truong_id == null) {
              this.$toast.error('Hãy kiểm tra lại thông tin : ID trường')
              return
            }
            if (sl_hocsinh_dk == 0 || sl_hocsinh_dk == null) {
              this.$toast.error('Hãy kiểm tra lại thông tin : Số lượng học sinh')
              return
            }
          }
          // console.log({ hdtb_id: hdtb_id, id_truong: truong_id, sl_hocsinh: sl_hocsinh_dk })
          if (kieuld_id == 13266) {
            //console.log({ hdtb_id: hdtb_id, id_truong: truong_id, sl_hocsinh: sl_hocsinh_dk })
            this.$refs.PopupHocBaDienTu.openDialog({
              hdtb_id: hdtb_id,
              id_truong: truong_id,
              sl_hocsinh: sl_hocsinh_dk,
              kieuld_id: kieuld_id,
              titleName: 'Đăng ký dịch vụ Học bạ điện tử'
            })
          }
          if (loaihd_id == LoaiHopDong.GIAHAN && kieuld_id == 14012) {
            this.$refs.PopupHuyHocSinhHBDT.openDialog({
              hdtb_id: hdtb_id,
              id_truong: truong_id,
              sl_hocsinh: sl_hocsinh_dk,
              thuebao_id: this.state.thuebao_id,
              kieuld_id: kieuld_id,
              titleName: 'Hủy học sinh dịch vụ Học bạ điện tử'
            })
          }

          if (loaihd_id == LoaiHopDong.CHAMDUT_SD && kieuld_id == 13269) {
            try {
              const res = await this.ConvertResApiHBDT(api.huyhocsinh(this.axios, { hdtb_id: this.state.hdtb_id }))
              const data_res = JSON.parse(res)
              if (data_res.ErrorCode == '00') {
                this.$toast.success('Hủy dịch vụ thành công')
                this.is_disabled = !this.is_disabled
              } else {
                this.$toast.error('Kích hoạt không thành công: ' + data_res.TransactionId)
                retun
              }
            } catch (err) {
              this.$toast.error(err)
            }
          }
        }

        // Dịch vụ vnEdu EzStudy - Phần mềm học trực tuyến
        if (vloaitb_id == 395) {
          try {
            this.loading(true)
            let truong_id = 0
            let sl_hocsinh_dk = 0
            /**
             * 13266: Đặt mới dịch vụ CNTT toàn trình
             * 14011: Tăng số lượng tài nguyên
             * 14012: Giảm số lượng tài nguyên
             * => kiểm tra thông tin
             **/
            if (kieuld_id == 13266 || kieuld_id == 14011 || kieuld_id == 14012 || kieuld_id == 13189) {
              this.tab_dvcntt.list.forEach((item) => {
                if (item.field_name == 'MA_PHU') {
                  truong_id = item.field_value
                }
                if (item.field_name == 'SL_MAILING') {
                  sl_hocsinh_dk = item.field_value
                }
              })
              if (truong_id == 0 || truong_id == null) {
                this.$toast.error('Hãy kiểm tra lại thông tin : ID trường')
                return
              }
              if (sl_hocsinh_dk == 0 || sl_hocsinh_dk == null) {
                this.$toast.error('Hãy kiểm tra lại thông tin : Số lượng học sinh')
                return
              }
            }
            /**
             * 13266: Đặt mới dịch vụ CNTT toàn trình
             **/
            if (kieuld_id == 13266) {
              this.$refs.PopupHocBaDienTu.openDialog({
                hdtb_id: hdtb_id,
                id_truong: truong_id,
                sl_hocsinh: sl_hocsinh_dk,
                _kieuld_id: kieuld_id,
                titleName: 'Đăng ký dịch vụ vnEdu EzStudy - Phần mềm học trực tuyến'
              })
            }
            /**
             * 14012: Giảm số lượng tài nguyên
             **/
            if (loaihd_id == LoaiHopDong.GIAHAN && kieuld_id == 14012) {
              await this.$refs.PopupHuyHocSinhHBDT.openDialog({
                hdtb_id: hdtb_id,
                id_truong: truong_id,
                sl_hocsinh: sl_hocsinh_dk,
                thuebao_id: this.state.thuebao_id,
                _kieuld_id: kieuld_id,
                titleName: 'Giảm số lượng tài nguyên vnEdu EzStudy - Phần mềm học trực tuyến'
              })
            }
            /*
             * 13189: Chuyển dùng chính thức
             */
            if (loaihd_id == LoaiHopDong.GIAHAN && kieuld_id == 13189) {
              this.$refs.PopupHuyHocSinhHBDT.openDialog({
                hdtb_id: hdtb_id,
                id_truong: truong_id,
                sl_hocsinh: sl_hocsinh_dk,
                thuebao_id: this.state.thuebao_id,
                _kieuld_id: kieuld_id,
                titleName: 'Chuyển dùng chính thức vnEdu EzStudy - Phần mềm học trực tuyến'
              })
            }
            /**
             * 13269: Chấm dứt sử dụng toàn trình
             **/
            if (loaihd_id == LoaiHopDong.CHAMDUT_SD && kieuld_id == 13269) {
              try {
                const res = await this.ConvertResApiHBDT(api.huyhocsinh(this.axios, { hdtb_id: this.state.hdtb_id }))
                const data_res = JSON.parse(res)
                if (data_res.ErrorCode == '00') {
                  this.$toast.success('Hủy dịch vụ thành công')
                  this.is_disabled = !this.is_disabled
                } else {
                  this.$toast.error('Kích hoạt không thành công: ' + data_res.TransactionId)
                  retun
                }
              } catch (err) {
                this.$toast.error(err.message)
              }
            }
          } catch (err) {
            this.$toast.error(err.message)
            this.loading(false)
          } finally {
            this.loading(false)
          }
        }

        // Hóa đơn điện tử - Máy tính tiền
        if (vloaitb_id == 2116) {
          try {
            this.loading(true)
            if (loaihd_id == LoaiHopDong.DAT_MOI) {
              let data = {
                kieuld_id: this.state.kieuld_id,
                hdtb_id: this.state.hdtb_id,
                phieu_id: this.state.phieu_id,
                thuebao_id: this.state.thuebao_id,
                ma_gd: this.state.ma_gd,
                loaitb_id: vloaitb_id
              }
              this.$refs.popupDangKyHoaDonDienTu78.openDialog(data)
            }
            if (loaihd_id == LoaiHopDong.GIAHAN) {
              if (kieuld_id == 13236) {
                if (vstatus !== 6) {
                  let jsondata = this.GetData(
                    await api.tao_req_cntt(this.axios, {
                      hdtb_id: hdtb_id,
                      vhdtb_id: hdtb_id,
                      dem: 0,
                      vkey: 'MTHHDT_MTT'
                    })
                  )
                  if (!jsondata || jsondata == '') {
                    throw 'Không tạo được request. Hãy kiểm tra lại hàm tạo request'
                  }
                  await this.GetData(await api.MuaThemHoaDon(this.axios, jsondata))
                  await api.cap_nhat_status_hdtb(this.axios, { vhdtb_id: hdtb_id, vstatus: 6 })
                  this.$toast.success('Đăng ký thêm hóa đơn thành công!')
                  this.controls.tsbtnHoanThienHS.enabled = true
                  this.HienThiDanhSachHDTB()
                } else {
                  this.$toast.error('Thuê bao đang ở trạng thái kích hoạt thành công, vui lòng không kích hoạt lại')
                  return
                }
              }
              //update them phan chuyen doi hoa don dien tu
              if (kieuld_id == 19542) {
                if (vstatus != 6)
                  //show form chuyen doi hoa don dien tu
                  this.$refs.popupChuyenDoiHDDT.openDialog({ hdtb_id: this.state.hdtb_id })
                else {
                  this.$toast.error('Thuê bao đang ở trạng thái kích hoạt thành công, vui lòng không kích hoạt lại')
                  return
                }
              }
              if (kieuld_id == 19599) {
                if (vstatus* 1 == 6){
                  //show form chuyen doi hoa don dien tu
                  this.$toast.error('Thuê bao đang ở trạng thái kích hoạt thành công, vui lòng không kích hoạt lại')
                  return
                }
                else {
                  this.$refs.CapBuSanLuongMTT.openDialog({ hdtb_id: this.state.hdtb_id, mst : this.state.mst })
                  return
                }
                return
              }
            }
            if (loaihd_id == LoaiHopDong.THAY_DOI_DV) {
              if (kieuld_id == 714) {
                if (vstatus != 6) {
                  let dt_hddt = this.GetData(await api.lay_thongso_dkmua_them_hd(this.axios, { hdtb_id: this.state.hdtb_id }))
                  if (!dt_hddt || dt_hddt.length == 0) {
                    throw 'Không có thông số hóa đơn'
                  }
                  dt_hddt = commonFn.LowerCasePropertyList(dt_hddt)
                  let dkt = {}
                  dkt.cus_id = dt_hddt[0]['cus_id']
                  dkt.taxcode = dt_hddt[0]['taxcode']
                  let kq = this.GetData(await api.TamNgungDichVuHDDT(this.axios, dkt))
                  let res = this.GetData(
                    await api.update_hd_thuebao(this.axios, {
                      hdtb_id: this.state.hdtb_id,
                      status: 6
                    })
                  )
                  this.$toast.success('Tạm ngừng dịch vụ thành công!')
                  this.controls.tsbtnHoanThienHS.enabled = true
                  this.HienThiDanhSachHDTB()
                } else {
                  this.$toast.error('Thuê bao đang ở trạng thái kích hoạt thành công, vui lòng không kích hoạt lại')
                  return
                }
              }
              if (kieuld_id == 715) {
                // khôi phục
                if (vstatus != 6) {
                  let dt_hddt = this.GetData(await api.lay_thongso_dkmua_them_hd(this.axios, { hdtb_id: this.state.hdtb_id }))
                  if (!dt_hddt || dt_hddt.length == 0) {
                    throw 'Không có thông số hóa đơn'
                  }
                  dt_hddt = commonFn.LowerCasePropertyList(dt_hddt)
                  let dkt = {}
                  dkt.cus_id = dt_hddt[0]['cus_id']
                  dkt.taxcode = dt_hddt[0]['taxcode']
                  let kq = this.GetData(await api.TamNgungDichVuHDDT(this.axios, dkt))
                  let res = this.GetData(
                    await api.update_hd_thuebao(this.axios, {
                      hdtb_id: this.state.hdtb_id,
                      status: 6
                    })
                  )
                  this.$toast.success('Khôi phục dịch vụ thành công!')
                  this.controls.tsbtnHoanThienHS.enabled = true
                  this.HienThiDanhSachHDTB()
                } else {
                  this.$toast.error('Thuê bao đang ở trạng thái kích hoạt thành công, vui lòng không kích hoạt lại')
                  return
                }
              }
            }
            if (loaihd_id == LoaiHopDong.CHAMDUT_SD) {
              if (vstatus != 6) {
                let dt_hddt = this.GetData(
                  await api.lay_thongso_dkmua_them_hd(this.axios, {
                    hdtb_id: this.state.hdtb_id,
                    vhdtb_id: this.state.hdtb_id
                  })
                )
                if (!dt_hddt || dt_hddt.length == 0) {
                  throw 'Không có thông số hóa đơn'
                }
                dt_hddt = commonFn.LowerCasePropertyList(dt_hddt)
                let dkt = {}
                dkt.cus_id = dt_hddt[0]['cus_id']
                dkt.taxcode = dt_hddt[0]['taxcode']
                let kq = this.GetData(await api.KhoaDichVuHDDT(this.axios, dkt))
                if (!kq) {
                  // tdan.EXECUTE_SQL("UPDATE {?DB2}.hd_thuebao a set a.status = 5 WHERE a.hdtb_id = " + hdtb_id.toString());
                  let res = this.GetData(
                    await api.update_hd_thuebao(this.axios, {
                      vhdtb_id: this.state.hdtb_id,
                      hdtb_id: this.state.hdtb_id,
                      status: 6
                    })
                  )
                  if (res) {
                    this.$toast.success('Khóa dịch vụ thành công!')
                    await this.HienThiDanhSachHDTB()
                  } else {
                    throw 'Lỗi cập nhật HĐ thuê bao!'
                  }
                } else {
                  throw 'Lỗi Khóa dịch vụ! ' + kq
                }
              } else {
                this.$toast.error('Thuê bao đang ở trạng thái kích hoạt thành công, vui lòng không kích hoạt lại')
                return
              }
            }
          } catch (e) {
            this.$toast.error('Có lỗi xảy ra:' + e.message)
            this.loading(false)
          } finally {
            this.loading(false)
          }
        }
        // #endregion
        // Dịch vụ VNPT BMIS
        if (vloaitb_id == 286) {
          //this.loading(true)
          if (loaihd_id == LoaiHopDong.DAT_MOI) {
            this.ThongBaoBMIS(await api.DatMoi_DV_BMIS(this.axios, { hdtb_id: hdtb_id }))
            this.controls.tsbtnHoanThienHS.enabled = true
          }
          if ((loaihd_id == LoaiHopDong.GIAHAN && kieuld_id == 13248) || (loaihd_id == LoaiHopDong.GIAHAN && kieuld_id == 13177)) {
            this.ThongBaoBMIS(await api.GiaHan_DV_BMIS(this.axios, { hdtb_id: hdtb_id }))
            this.controls.tsbtnHoanThienHS.enabled = true
          }
          if (loaihd_id == LoaiHopDong.GIAHAN && kieuld_id == 14016) {
            this.ThongBaoBMIS(await api.DoiGoiCuoc_DV_BMIS(this.axios, { hdtb_id: hdtb_id }))
            this.controls.tsbtnHoanThienHS.enabled = true
          }
          if (loaihd_id == LoaiHopDong.THAY_DOI_DV && kieuld_id == 714) {
            this.ThongBaoBMIS(await api.TamDung_DV_BMIS(this.axios, { hdtb_id: hdtb_id }))
            this.controls.tsbtnHoanThienHS.enabled = true
          }
          if (loaihd_id == LoaiHopDong.THAY_DOI_DV && kieuld_id == 715) {
            this.ThongBaoBMIS(await api.KhoiPhuc_DV_BMIS(this.axios, { hdtb_id: hdtb_id }))
            this.controls.tsbtnHoanThienHS.enabled = true
          }
          if (loaihd_id == LoaiHopDong.CHAMDUT_SD && kieuld_id == 13269) {
            this.ThongBaoBMIS(await api.ThanhLy_DV_BMIS(this.axios, { hdtb_id: hdtb_id }))
            this.controls.tsbtnHoanThienHS.enabled = true
          }
          //this.loading(false)
        }
        //#region cuongpq kich hoat brandname
        if (vloaitb_id == LoaiHinhTB.BRANDNAME) {
          /*khách hàng đại lý */
          if (kieuld_id == 792) {
            let data = {
              hdtb_id: this.state.hdtb_id
            }
            this.$refs.popupCreateAgentSMSBrandname.showModal(data)
          }
          if (kieuld_id == 793) {
            let kq = this.GetData(await api.fn_kiemtra_hien_form_tao_brn(this.axios, { vhdtb_id: this.state.hdtb_id }))
            if (kq != 'OK') {
              this.inputDataPopupTaoKHTT = { vhdtb_id: this.state.hdtb_id }
              this.$refs.popupTaoKHTT.show()
            }
            kq = this.GetData(await api.fn_kiemtra_hien_form_tao_brn(this.axios, { vhdtb_id: this.state.hdtb_id }))
            if (kq == 'OK') {
              let data = { hdtb_id: this.state.hdtb_id }
              this.$refs.popupKichhoatBrandname.openDialog(data)
            }
          }
          if (kieuld_id == 799 || kieuld_id == 840) {
            let data = {
              hdtb_id: this.state.hdtb_id
            }
            this.$refs.frmBRNGuiMail.openDialog(data)
          }
          //TUANLAHYN
          if (kieuld_id == 797) {
            let data = {
              hdtb_id: this.state.hdtb_id
            }
            this.$refs.RegiSMSBrandname.openDialog(data)
          }
          //todo dhtt
          if (kieuld_id == 794) {
            /*tạo đơn hàng trả trước*/ let data = { hdtb_id: this.state.hdtb_id }
            console.log('ok')
            this.$refs.frm_Tao_DHTT_A2P.showModal(data)
          }
          if (kieuld_id == 795) {
            /*gia hạn đơn hàng trả trước*/ let data = { hdtb_id: this.state.hdtb_id }
            this.$refs.frm_Giahan_DHTT_A2P.showModal(data)
          }
          //ENDTUANLAHYN
          if (kieuld_id == 796) {
            //this.loading(true)
            var json_data_sp_load_dulieu_form_tao_brn = JSON.stringify({
              vhdtb_id: this.state.hdtb_id
            })
            var link_sp_load_dulieu_form_tao_brn = '/web-quantri/sms-brandname/sp_load_dulieu_form_tao_brn'
            const files_sp_load_dulieu_form_tao_brn = await this.axios.post(link_sp_load_dulieu_form_tao_brn, json_data_sp_load_dulieu_form_tao_brn, {
              headers: {
                'Content-Type': 'application/json'
              }
            })
            if (files_sp_load_dulieu_form_tao_brn.data.data.data) {
              var ds = files_sp_load_dulieu_form_tao_brn.data.data.data
              var thuebao_id = ds[0].thuebao_id
              var agent_id = ds[0].agent_id
              var adser_id = ds[0].adser_id
              var contract_id = ds[0].contract_id
              var agent_type_id = Number(ds[0].agent_type_id)
              var contract_type_id = ds[0].contract_type_id
              var label_id = ds[0].label_id
            }
            var json_HUY_KHACHHANG = JSON.stringify({
              agent_id: agent_id,
              adser_id: adser_id,
              contract_id: contract_id,
              agent_type: agent_type_id,
              status: -2
            })
            this.resource = (await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', {
              P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
              p_nghiepvu: 'KIEMTRA_WHITELIST_BRANDNAME',
              p_ds_para: JSON.stringify({ vhdtb_id: this.hdtb_id })
            })).data.data[0].kq == 'ok' ? 'web-thicong' : 'tichhop';

            var link_HUY_KHACHHANG = `/${this.resource}/brandname/HUY_KHACHHANG`
            var kq = await this.axios.post(link_HUY_KHACHHANG, json_HUY_KHACHHANG, {
              headers: {
                'Content-Type': 'application/json'
              }
            })
            console.log(kq.data.data.message)
            this.$toast.info(kq.data.data.message)
            //this.loading(false)
          }
          if (kieuld_id == 714 || kieuld_id == 715) {
            const files_sp_load_dulieu_form_tao_brn = await this.axios.post('/web-quantri/sms-brandname/sp_load_dulieu_form_tao_brn', {
              vhdtb_id: this.state.hdtb_id
            })
            if (files_sp_load_dulieu_form_tao_brn.data.data.data) {
              var ds = files_sp_load_dulieu_form_tao_brn.data.data.data
              var thuebao_id = ds[0].thuebao_id
              var agent_id = ds[0].agent_id
              var adser_id = ds[0].adser_id
              var contract_id = ds[0].contract_id
              var agent_type_id = Number(ds[0].agent_type_id)
              var contract_type_id = ds[0].contract_type_id
              var label_id = ds[0].label_id
              var lydohuy = ds[0].lydohuy
            }
            this.resource = (await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', {
              P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
              p_nghiepvu: 'KIEMTRA_WHITELIST_BRANDNAME',
              p_ds_para: JSON.stringify({ vhdtb_id: this.hdtb_id })
            })).data.data[0].kq == 'ok' ? 'web-thicong' : 'tichhop';

            var link_LAY_DS_NHAN = `/${this.resource}/brandname/LAY_DS_NHAN`
            var json_LAY_DS_NHAN = JSON.stringify({
              agent_id: agent_id,
              adser_id: adser_id,
              contract_id: contract_id,
              agent_type: agent_type_id
            })
            const files_LAY_DS_NHAN = await this.axios.post(link_LAY_DS_NHAN, json_LAY_DS_NHAN, {
              headers: {
                'Content-Type': 'application/json'
              }
            })
            let brandname = null
            if (files_LAY_DS_NHAN.data.data) {
              brandname = files_LAY_DS_NHAN.data.data.data.items.filter((item) => item.label_id == label_id)[0]
            }
            var req_lock_telco = ''
            var message_lock = ''
            var req_mo_telco = ''
            var message_mo = ''
            if (brandname.status == 1) {
              req_lock_telco += '1'
              message_lock += 'Vinaphone'
            } else if (brandname.status == -1) {
              req_mo_telco += '1'
              message_mo += 'Vinaphone'
            }
            if (brandname.status_vms == 1) {
              if (req_lock_telco) req_lock_telco += ','
              req_lock_telco += '2'
              if (message_lock) message_lock += ','
              message_lock += 'Mobiphone'
            } else if (brandname.status_vms == -1) {
              if (req_mo_telco) req_mo_telco += ','
              req_mo_telco += '2'
              if (message_mo) message_mo += ','
              message_mo += 'Mobiphone'
            }
            if (brandname.status_vtl == 1) {
              if (req_lock_telco) req_lock_telco += ','
              req_lock_telco += '3'
              if (message_lock) message_lock += ','
              message_lock += 'Viettel'
            } else if (brandname.status_vtl == -1) {
              if (req_mo_telco) req_mo_telco += ','
              req_mo_telco += '3'
              if (message_mo) message_mo += ','
              message_mo += 'Viettel'
            }
            if (brandname.status_gtel == 1) {
              if (req_lock_telco) req_lock_telco += ','
              req_lock_telco += '4'
              if (message_lock) message_lock += ','
              message_lock += 'Gtel'
            } else if (brandname.status_gtel == -1) {
              if (req_mo_telco) req_mo_telco += ','
              req_mo_telco += '4'
              if (message_mo) message_mo += ','
              message_mo += 'Gtel'
            }
            if (brandname.status_vnm == 1) {
              if (req_lock_telco) req_lock_telco += ','
              req_lock_telco += '5'
              if (message_lock) message_lock += ','
              message_lock += 'Vietnamobile'
            } else if (brandname.status_vnm == -1) {
              if (req_mo_telco) req_mo_telco += ','
              req_mo_telco += '5'
              if (message_mo) message_mo += ','
              message_mo += 'Vietnamobile'
            }
            if (brandname.status_itel == 1) {
              if (req_lock_telco) req_lock_telco += ','
              req_lock_telco += '7'
              if (message_lock) message_lock += ','
              message_lock += 'Itel'
            } else if (brandname.status_itel == -1) {
              if (req_mo_telco) req_mo_telco += ','
              req_mo_telco += '7'
              if (message_mo) message_mo += ','
              message_mo += 'Itel'
            }
            if (brandname.status_mobicast == 1) {
              if (req_lock_telco) req_lock_telco += ','
              req_lock_telco += '8'
              if (message_lock) message_lock += ','
              message_lock += 'Reddi'
            } else if (brandname.status_mobicast == -1) {
              if (req_mo_telco) req_mo_telco += ','
              req_mo_telco += '8'
              if (message_mo) message_mo += ','
              message_mo += 'Reddi'
            }
          }

          if (kieuld_id == 714) {
            const isConfirm = await this.$bvModal.msgBoxConfirm(`Bạn có chắc chắn kích hoạt tạm dừng dịch vụ SMS Brandname cho khách hàng? [${message_lock}]`, {
              title: 'Xác nhận kích hoạt',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy',
              centered: true
            })
            if (!isConfirm) {
              return
            }
            this.resource = (await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', {
              P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
              p_nghiepvu: 'KIEMTRA_WHITELIST_BRANDNAME',
              p_ds_para: JSON.stringify({ vhdtb_id: this.hdtb_id })
            })).data.data[0].kq == 'ok' ? 'web-thicong' : 'tichhop';

            const kq = await this.axios.post(`/${this.resource}/brandname/updatestatusbrandname`, {
              label_id: label_id,
              telco_id_list: req_lock_telco,
              status: -1,
              reason: lydohuy
            })
            if(kq.data.data.error_code == 0)  {
              await api.update_hd_thuebao(this.axios, { vhdtb_id: this.state.hdtb_id, hdtb_id: this.state.hdtb_id, status: 6 })
              this.$toast.success('Tạm dừng brandmame thành công')
            }
            else {
              this.$toast.error(JSON.stringify(kq.data.data))
            }
          }

          if (kieuld_id == 715) {
            const isConfirm = await this.$bvModal.msgBoxConfirm(`Bạn có chắc chắn kích hoạt khôi phục dịch vụ SMS Brandname cho khách hàng? [${message_mo}]`, {
              title: 'Xác nhận kích hoạt',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy',
              centered: true
            })
            if (!isConfirm) {
              return
            }
            this.resource = (await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', {
              P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
              p_nghiepvu: 'KIEMTRA_WHITELIST_BRANDNAME',
              p_ds_para: JSON.stringify({ vhdtb_id: this.hdtb_id })
            })).data.data[0].kq == 'ok' ? 'web-thicong' : 'tichhop';

            const kq = await this.axios.post(`/${this.resource}/brandname/updatestatusbrandname`, {
              label_id: label_id,
              telco_id_list: req_mo_telco,
              status: 1,
              reason: lydohuy
            })
            if(kq.data.data.error_code == 0)  {
              await api.update_hd_thuebao(this.axios, { vhdtb_id: this.state.hdtb_id, hdtb_id: this.state.hdtb_id, status: 6 })
              this.$toast.success('Khôi phục brandmame thành công')
            }
            else {
              this.$toast.error(JSON.stringify(kq.data.data))
            }

          }
        }

        //dich vu ETicket = 319
        if (vloaitb_id == LOAIHINH_THUEBAO.VNPT_ETICKET) {
          const resStatus = this.GetData(
            await api.fn_get_status_hdtb(this.axios, {
              vhdtb_id: hdtb_id,
              hdtb_id: hdtb_id
            })
          )
          //#region DAT_MOI
          if (loaihd_id == LoaiHopDong.DAT_MOI && kieuld_id == 13266) {
            let data = {
              kieuld_id: this.state.kieuld_id,
              hdtb_id: this.state.hdtb_id,
              phieu_id: this.state.phieu_id,
              thuebao_id: this.state.thuebao_id,
              ma_gd: this.state.ma_gd,
              loaitb_id: vloaitb_id
            }
            console.log(this.$refs.popupDangKyHoaDonDienTu78)
            this.$refs.popupDangKyHoaDonDienTu78.openDialog(data)
          }
          //------------------------------------------------------
          // if (loaihd_id == LoaiHopDong.GIAHAN) {
          //   this.loading(true)
          //   if (resStatus != 6 && this.state.kieuld_id == 14011) {
          //     let jsondata = this.GetData(await api.tao_req_cntt(this.axios, { hdtb_id: hdtb_id, vhdtb_id: hdtb_id }))
          //     if (!jsondata || jsondata == '') {
          //       this.loading(false)
          //       throw 'Không tạo được request. Hãy kiểm tra lại hàm tạo request'
          //     }
          //     const kq = await api.MuaThemHoaDon(this.axios, jsondata)
          //     let reqLog = {
          //       vhdtb_id: hdtb_id,
          //       verror: '',
          //       vreq: JSON.stringify(jsondata),
          //       vres: JSON.stringify(kq),
          //       vma_tb: '',
          //       vghichu: 'await api.MuaThemHoaDon(this.axios, dkt)'
          //     }
          //     await api.sp_insert_log_cntt(this.axios, reqLog)
          //     if (kq.data.errorCode == 0) {
          //       await api.cap_nhat_status_hdtb(this.axios, { vhdtb_id: hdtb_id, hdtb_id: hdtb_id, vstatus: 6 })
          //       this.controls.tsbtnHoanThienHS.enabled = true
          //       this.$toast.success('Mua thê số lượng vé thành công')
          //       await this.HienThiDanhSachHDTB()
          //       this.loading(false)
          //     } else {
          //       this.loading(false)
          //       throw 'Lỗi đăng ký! ' + kq.data.faultString
          //     }
          //   } else {
          //     if (resStatus == 6) {
          //       this.$toast.success('Thuê bao đã kích hoạt thành công')
          //       this.loading(false)
          //     }
          //   }
          // }
          if (loaihd_id == LoaiHopDong.THAY_DOI_DV) {
            if (kieuld_id == 714) {
              this.loading(true)
              if (resStatus != 6) {
                let dt_hddt = this.GetData(
                  await api.lay_thongso_dkmua_them_hd(this.axios, {
                    hdtb_id: hdtb_id,
                    vhdtb_id: hdtb_id
                  })
                )
                if (!dt_hddt || dt_hddt.length == 0) {
                  this.loading(false)
                  throw 'Không lấy được thông tin kích hoạt'
                }
                dt_hddt = commonFn.LowerCasePropertyList(dt_hddt)
                let dkt = {}
                dkt.cus_id = dt_hddt[0]['cus_id']
                dkt.taxcode = dt_hddt[0]['taxcode']
                dkt.service_type = dt_hddt[0]['service_type']
                let kq = await api.TamNgungDichVuHDDT(this.axios, dkt)
                let reqLog = {
                  vhdtb_id: hdtb_id,
                  verror: '',
                  vreq: JSON.stringify(dt_hddt),
                  vres: JSON.stringify(kq),
                  vma_tb: '',
                  vghichu: 'await api.MuaThemHoaDon(this.axios, dkt)'
                }
                await api.sp_insert_log_cntt(this.axios, reqLog)
                if (kq.data.errorCode == 0) {
                  let res = this.GetData(
                    await api.cap_nhat_status_hdtb(this.axios, {
                      vhdtb_id: hdtb_id,
                      hdtb_id: hdtb_id,
                      vstatus: 6
                    })
                  )
                  this.$toast.success('Tạm ngừng dịch vụ thành công!')
                  this.controls.tsbtnHoanThienHS.enabled = true
                  this.HienThiDanhSachHDTB()
                  this.loading(false)
                } else {
                  this.$toast.error('Có lỗi xảy ra: ' + kq.data.faultString)
                  this.loading(false)
                  return
                }
              } else {
                if (resStatus == 6) {
                  this.$toast.error('Thuê bao đã ở trạng thái kích hoạt thành công')
                  this.loading(false)
                  return
                }
              }
            }
            if (kieuld_id == 715) {
              // khôi phục
              this.loading(true)
              if (resStatus != 6) {
                let dt_hddt = this.GetData(
                  await api.lay_thongso_dkmua_them_hd(this.axios, {
                    hdtb_id: hdtb_id,
                    vhdtb_id: hdtb_id
                  })
                )
                if (!dt_hddt || dt_hddt.length == 0) {
                  this.loading(false)
                  throw 'Không có thông tin kích hoạt'
                }
                dt_hddt = commonFn.LowerCasePropertyList(dt_hddt)
                let dkt = {}
                dkt.cus_id = dt_hddt[0]['cus_id']
                dkt.taxcode = dt_hddt[0]['taxcode']
                dkt.service_type = dt_hddt[0]['service_type']
                let kq = await api.KhoiPhucDichVuHDDT(this.axios, dkt)
                let reqLog = {
                  vhdtb_id: hdtb_id,
                  verror: '',
                  vreq: JSON.stringify(dt_hddt),
                  vres: JSON.stringify(kq),
                  vma_tb: '',
                  vghichu: 'await api.MuaThemHoaDon(this.axios, dkt)'
                }
                await api.sp_insert_log_cntt(this.axios, reqLog)
                if (kq.data.errorCode == 0) {
                  let res = this.GetData(
                    await api.cap_nhat_status_hdtb(this.axios, {
                      vhdtb_id: hdtb_id,
                      hdtb_id: hdtb_id,
                      vstatus: 6
                    })
                  )
                  this.$toast.success('Khôi phục dịch vụ thành công!')
                  this.controls.tsbtnHoanThienHS.enabled = true
                  this.HienThiDanhSachHDTB()
                  this.loading(false)
                  return
                } else {
                  this.$toast.error('Có lỗi xảy ra: ' + kq.data.faultString)
                  this.loading(false)
                  return
                }
              } else {
                if (resStatus == 6) {
                  this.$toast.error('Thuê bao đã ở trạng thái kích hoạt thành công')
                  this.loading(false)
                  return
                }
              }
            }
          }
          if (loaihd_id == LoaiHopDong.CHAMDUT_SD) {
            this.loading(true)
            if (resStatus != 6) {
              let dt_hddt = this.GetData(
                await api.lay_thongso_dkmua_them_hd(this.axios, {
                  hdtb_id: hdtb_id,
                  vhdtb_id: hdtb_id
                })
              )
              if (!dt_hddt || dt_hddt.length == 0) {
                throw 'Không có thông số hóa đơn'
              }
              dt_hddt = commonFn.LowerCasePropertyList(dt_hddt)
              let dkt = {}
              dkt.cus_id = dt_hddt[0]['cus_id']
              dkt.taxcode = dt_hddt[0]['taxcode']
              dkt.service_type = dt_hddt[0]['service_type']
              let kq = await api.TamNgungDichVuHDDT(this.axios, dkt)
              let reqLog = {
                vhdtb_id: hdtb_id,
                verror: '',
                vreq: JSON.stringify(dt_hddt),
                vres: JSON.stringify(kq),
                vma_tb: '',
                vghichu: 'await api.MuaThemHoaDon(this.axios, dkt)'
              }
              await api.sp_insert_log_cntt(this.axios, reqLog)
              if (kq.data.errorCode == 0) {
                let res = this.GetData(
                  await api.cap_nhat_status_hdtb(this.axios, {
                    vhdtb_id: hdtb_id,
                    hdtb_id: hdtb_id,
                    vstatus: 6
                  })
                )
                this.$toast.success('Khóa dịch vụ thành công!')
                await this.HienThiDanhSachHDTB()
                this.controls.tsbtnHoanThienHS.enabled = true
                this.loading(false)
                return
              } else {
                if (kq.data.faultCode == 'ERR:6.17') {
                  this.$toast.success('Khóa dịch vụ thành công!')
                  await this.HienThiDanhSachHDTB()
                  this.controls.tsbtnHoanThienHS.enabled = true
                  this.loading(false)
                  return
                } else {
                  this.loading(false)
                  throw 'Lỗi Khóa dịch vụ! ' + kq.data.faultString
                }
              }
            } else {
              if (resStatus == 6) {
                this.$toast.error('Thuê bao đã ở trạng thái kích hoạt thành công')
                this.loading(false)
                return
              }
            }
          }
        }
        if (vloaitb_id == 175) {
          this.loading(true)
          const resStatus = this.GetData(
            await api.fn_get_status_hdtb(this.axios, {
              vhdtb_id: hdtb_id,
              hdtb_id: hdtb_id
            })
          )

          //#region DAT_MOI
          if (loaihd_id == LoaiHopDong.DAT_MOI && kieuld_id == 13266) {
            let data = {
              kieuld_id: this.state.kieuld_id,
              hdtb_id: this.state.hdtb_id,
              phieu_id: this.state.phieu_id,
              thuebao_id: this.state.thuebao_id,
              ma_gd: this.state.ma_gd,
              loaitb_id: vloaitb_id
            }
            console.log(this.$refs.popupDangKyHoaDonDienTu78)
            this.$refs.popupDangKyHoaDonDienTu78.openDialog(data)
          }

          if (loaihd_id == LoaiHopDong.GIAHAN) {
            if (resStatus != 6 && this.state.kieuld_id == 13236) {
              let dt_hddt = this.GetData(await api.lay_thongso_dkmua_them_hd(this.axios, { hdtb_id: hdtb_id }))
              if (!dt_hddt || dt_hddt.length == 0) {
                throw 'Không có thông số hóa đơn'
              }
              dt_hddt = commonFn.LowerCasePropertyList(dt_hddt)
              let dkt = {}
              dkt.cus_id = dt_hddt[0]['cus_id']
              dkt.taxcode = dt_hddt[0]['taxcode']
              dkt.number_invoices = dt_hddt[0]['number_invoices'] * 1
              let kq = this.GetData(await api.MuaThemBienLaiDienTu(this.axios, dkt))
              let reqLog = {
                vhdtb_id: hdtb_id,
                verror: '',
                vreq: JSON.stringify(dkt),
                vres: JSON.stringify(kq),
                vma_tb: '',
                vghichu: 'await api.MuaThemBienLaiDienTu(this.axios, dkt)'
              }
              await api.sp_insert_log_cntt(this.axios, reqLog)
              if (!kq) {
                let res = this.GetData(await api.update_hd_thuebao(this.axios, { hdtb_id: hdtb_id, status: 6 }))
                if (res) {
                  this.$toast.success('Mua thêm biên lại thành công!')
                  await this.HienThiDanhSachHDTB()
                } else {
                  throw 'Lỗi cập nhật HĐ thuê bao!'
                }
              } else {
                throw 'Lỗi đăng ký! ' + kq
              }
            }
          }
          if (loaihd_id == LoaiHopDong.THAY_DOI_DV) {
            if (kieuld_id == 714) {
              // tạm ngừng
              if (kt_status == '0' || kt_status == '') {
                let dt_hddt = this.GetData(await api.lay_thongso_dkmua_them_hd(this.axios, { vhdtb_id: hdtb_id }))
                if (!dt_hddt || dt_hddt.length == 0) {
                  throw 'Không có thông số hóa đơn'
                }
                dt_hddt = commonFn.LowerCasePropertyList(dt_hddt)
                let dkt = {}
                dkt.cus_id = dt_hddt[0]['cus_id']
                dkt.taxcode = dt_hddt[0]['taxcode']
                let kq = this.GetData(await api.TamNgungBienLaiDienTu(this.axios, dkt))
                let reqLog = {
                  vhdtb_id: hdtb_id,
                  verror: '',
                  vreq: JSON.stringify(dkt),
                  vres: JSON.stringify(kq),
                  vma_tb: '',
                  vghichu: 'await api.MuaThemBienLaiDienTu(this.axios, dkt)'
                }
                await api.sp_insert_log_cntt(this.axios, reqLog)
                if (!kq) {
                  // tdan.EXECUTE_SQL("UPDATE {?DB2}.hd_thuebao a set a.status = 5 WHERE a.hdtb_id = " + hdtb_id.toString());
                  let res = this.GetData(await api.update_hd_thuebao(this.axios, { hdtb_id: hdtb_id, status: 6 }))
                  if (res) {
                    this.$toast.success('Tạm ngừng dịch vụ thành công!')
                    await this.HienThiDanhSachHDTB()
                  } else {
                    throw 'Lỗi cập nhật HĐ thuê bao!'
                  }
                } else {
                  throw 'Lỗi tạm ngừng dịch vụ!'
                }
              }
            }
            if (kieuld_id == 715) {
              // khôi phục
              if (kt_status == '0' || kt_status == '') {
                let dt_hddt = this.GetData(await api.lay_thongso_dkmua_them_hd(this.axios, { vhdtb_id: hdtb_id }))
                if (!dt_hddt || dt_hddt.length == 0) {
                  throw 'Không có thông số hóa đơn'
                }
                dt_hddt = commonFn.LowerCasePropertyList(dt_hddt)
                let dkt = {}
                dkt.cus_id = dt_hddt[0]['cus_id']
                dkt.taxcode = dt_hddt[0]['taxcode']
                let kq = this.GetData(await api.KhoiPhucBienLaiDienTu(this.axios, dkt))
                if (!kq) {
                  // tdan.EXECUTE_SQL("UPDATE {?DB2}.hd_thuebao a set a.status = 5 WHERE a.hdtb_id = " + hdtb_id.toString());
                  let res = this.GetData(await api.update_hd_thuebao(this.axios, { hdtb_id: hdtb_id, status: 6 }))
                  if (res) {
                    this.$toast.success('Khôi phục dịch vụ thành công!')
                    await this.HienThiDanhSachHDTB()
                  } else {
                    throw 'Lỗi cập nhật HĐ thuê bao!'
                  }
                } else {
                  throw 'Lỗi Khôi phục dịch vụ! ' + kq
                }
              }
            }
          }
          if (loaihd_id == LoaiHopDong.CHAMDUT_SD && kieuld_id == 13269) {
            if (kt_status == '0' || kt_status == '') {
              let dt_hddt = this.GetData(await api.lay_thongso_dkmua_them_hd(this.axios, { vhdtb_id: hdtb_id }))
              if (!dt_hddt || dt_hddt.length == 0) {
                throw 'Không có thông số hóa đơn'
              }
              dt_hddt = commonFn.LowerCasePropertyList(dt_hddt)
              let dkt = {}
              dkt.cus_id = dt_hddt[0]['cus_id']
              dkt.taxcode = dt_hddt[0]['taxcode']
              let kq = this.GetData(await api.TamNgungBienLaiDienTu(this.axios, dkt))
              if (!kq) {
                // tdan.EXECUTE_SQL("UPDATE {?DB2}.hd_thuebao a set a.status = 5 WHERE a.hdtb_id = " + hdtb_id.toString());
                let res = this.GetData(await api.update_hd_thuebao(this.axios, { hdtb_id: hdtb_id, status: 6 }))
                if (res) {
                  this.$toast.success('Chấm dứt sử dụng thành công!')
                  await this.HienThiDanhSachHDTB()
                } else {
                  throw 'Lỗi cập nhật HĐ thuê bao!'
                }
              } else {
                throw 'Chấm dứt sử dụng thành công!'
              }
            }
          }
        }
      } catch (ex) {
        this.loading(false)
        commonFn.showException(this, ex)
      } finally {
        this.loading(false)
      }
      //this.loading(false)
    },
    async getData_Elearnig(hdtb_id) {
      try {
        // Lấy thông tin chuyển thành json
        let dt_hd_thuebao = this.GetData(await api.lay_dulieu_cho_api_elearning(this.axios, { vhdtb_id: hdtb_id }))
        if (!dt_hd_thuebao || dt_hd_thuebao.length === 0) {
          this.$toast.error('Không lấy được dữ liệu Elerning')
          return
        }
        // Khai báo các class
        var objElearning = {}
        var objInfo = {}
        var objOrder = {}
        var objAtrribute = []
        // Class Info
        objInfo.ms_thue = dt_hd_thuebao[0]['MST']
        objInfo.email = dt_hd_thuebao[0]['EMAIL']
        objInfo.phone = dt_hd_thuebao[0]['SO_DT']
        objInfo.ma_thue_bao = dt_hd_thuebao[0]['MA_TB']
        objInfo.ma_kinh_doanh = dt_hd_thuebao[0]['MA_KINH_DOANH']
        objInfo.ma_hop_dong = dt_hd_thuebao[0]['MA_HD']
        objInfo.hopdong = dt_hd_thuebao[0]['URL']
        objInfo.gia_tri_hop_dong = dt_hd_thuebao[0]['GIATRIHD']
        //Class Order
        objOrder.ma_dich_vu = dt_hd_thuebao[0]['ma_dich_vu']
        objOrder.domain = dt_hd_thuebao[0]['DOMAIN']
        objOrder.goi_cuoc = dt_hd_thuebao[0]['ma_td'] * 1
        objOrder.start_date = dt_hd_thuebao[0]['NGAY_BD']
        objOrder.hinh_thuc = dt_hd_thuebao[0]['hinh_thuc']
        objOrder.loai_don_hang = dt_hd_thuebao[0]['loaihopdong']
        // Class atribute
        if (dt_hd_thuebao.length == 1 && !(dt_hd_thuebao[0]['dvgt_thuoctinh'] || dt_hd_thuebao[0]['dvgt_thuoctinh'] == '') && (!dt_hd_thuebao[0]['soluong'] || dt_hd_thuebao[0]['soluong'] == '')) {
          objAtrribute = null
        } else {
          dt_hd_thuebao.forEach((row) => {
            if (row['soluong'] && row['soluong'] != '') {
              objAtrribute.push({
                type: row['dvgt_thuoctinh'], //Convert.ToInt32(row["DICHVUGT_ID"].toString()),//row["mst"];
                quantity: row['soluong']
              })
            }
          })
        }
        // class Creater
        objElearning.vnedu_site_id = dt_hd_thuebao[0]['ma_phu']
        objElearning.moet_id = dt_hd_thuebao[0]['ma_phu']
        // Chuyển 3 obj thành Json.
        var jsonInfo = JSON.stringify(objInfo)
        var jsonOder = JSON.stringify(objOrder)
        var jsonAtrribute = JSON.stringify(objAtrribute)
        // Gán các json vào biến trong class VNEduElearningCreater
        objElearning.info = jsonInfo
        objElearning.order = jsonOder
        objElearning.attribute = jsonAtrribute
        objElearning.secure_code = '' // secure code này khi gọi phương thức của web CA sẽ sinh. k cho sinh trong này
        // Chuyển class VNEduElearningCreater về Json
        var result = JSON.stringify(objElearning)
        await api.sp_insert_log_action_elearning(this.axios, {
          vuser_name: hdtb_id,
          vcomputer_name: 'Json kích hoạt dịch vụ Elearning',
          vaction_name: 'Thành công',
          vkey_parameter: result,
          vnote: result
        })
        // Gọi file Rest đưa json qua API
        var result_response = this.GetData(await api.fn_khaibao_api_kickhoat_elearning(this.axios, objElearning))
        // Kết quả trả về sau khi gọi API
        var kq_return = result_response
        // Thực hiện soi kq :))) Lỗi thì liên hệ MEDIA, Hoặc đọc lại HDSD
        if (kq_return && kq_return.success) {
          // cập nhật lại trạng thái = 3 trong hd_thuebao tránh TH tỉnh nhấn kích hoạt 2 lần và khi tt = 3 thì nút hths mới hiển thị.
          //tdan.EXECUTE_PROC("{?DB2}.SP_CAPNHAT_STATUS_DV_ELEARNING", "vhdtb_id", hdtb_id);
          await api.sp_capnhat_status_dv_elearning(this.axios, { vhdtb_id: hdtb_id })
          await api.sp_insert_log_action_elearning(this.axios, {
            vuser_name: hdtb_id,
            vcomputer_name: 'Json trả về khi kích hoạt dịch vụ Elearning',
            vaction_name: 'Thành công',
            vkey_parameter: result,
            vnote: JSON.stringify(result_response)
          })
          // cập nhật lại user_name, password cho khách hàng
          var kq_kt_chophep_cn = this.GetData(await api.fn_thuchien_capnhat_userpass_elearning(this.axios, {}))
          if (kq_kt_chophep_cn && kq_kt_chophep_cn == 'OK') {
            var json = result_response
            if (json.indexOf('admin_acc') != -1) {
              var vusername = json.data.admin_acc
              var vpassword = json.data.admin_pwd
              var vdomailfull = json.data.domain_full
              var kq = this.GetData(
                await api.fn_update_user_password_elearning(this.axios, {
                  vhdtb_id: hdtb_id,
                  vusername: vusername,
                  vpassword: vpassword,
                  vdomain: vdomailfull
                })
              )
              if (kq != 'ok') {
                //throw "Không cập nhật được username, password"
              }
            }
          }
          return 'OK'
        } else {
          await api.sp_insert_log_action_elearning(this.axios, {
            vuser_name: hdtb_id,
            vcomputer_name: 'Json trả về khi kích hoạt dịch vụ Elearning',
            vaction_name: 'Thất bại',
            vkey_parameter: result,
            vnote: JSON.stringify(result_response)
          })
          throw kq_return
        }
      } catch (ex) {
        throw ex
      }
    },
    // #region begin hdk
    async DatMoiHKD() {
      let kq = await hkd.DatMoiHKD(this, {
        luong_thaotac: this.state.luong_thaotac,
        luong_id: this.state.luong_id,
        loaihd_id: this.state.loaihd_id,
        kieuld_id: this.state.kieuld_id,
        hdtb_id: this.state.hdtb_id,
        thuebao_id: this.state.thuebao_id,
        ma_tinh: this.tt_nd.ma_tinh
      })
      this.$toast.success(kq.data)
    },
    // #endregion hkd
    async ivan_kichhoat() {
      let thongtin_xacnhan_str = this.GetData(await this.axios.get('web-hopdong/vbn/fn_hoi_truoc_khi_kichhoat_dichvu_cntt?vhdtb_id=' + this.state.hdtb_id))
      const h = this.$createElement
      const titleVNode = h('div', { domProps: { innerHTML: thongtin_xacnhan_str } })
      // this.$bvModal.msgBoxOk([titleVNode], {
      //   centered: true,
      //   buttonSize: 'sm'
      // })
      this.$bvModal
        .msgBoxConfirm([titleVNode], {
          title: 'Yêu cầu xác nhận các thông tin trước khi kích hoạt',
          size: 'lg',
          okTitle: 'Đã chính xác',
          cancelTitle: 'Chưa đúng, để tôi cập nhật lại',
          centered: true
        })
        .then(async (v) => {
          if (!v) {
            return
          }
          this.$bvModal
            .msgBoxConfirm('Bạn có chắc chắn kích hoạt dịch vụ BHXH cho khách hàng?', {
              title: '',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy',
              centered: true
            })
            .then(async (v) => {
              if (!v) {
                return
              }
              try {
                let kq = ''
                let loaihd_id = this.state.loaihd_id * 1
                let hdtb_id = this.state.hdtb_id * 1
                if (loaihd_id == LOAI_HOPDONG.DAT_MOI) {
                  kq = await this.MakeRequestAPI_TAO_BHXH(hdtb_id.toString())
                }
                if (loaihd_id == LOAI_HOPDONG.GIAHAN) {
                  let kq_db = await this.MakeRequestAPI_DONGBO_BACKEND(hdtb_id.toString())
                  if (kq_db != 'OK') {
                    this.$toast.warning(kq_db)
                    return
                  }
                  kq = await this.MakeRequestAPI_GiaHan_BHXH(hdtb_id.toString())
                }
                if (loaihd_id == LOAI_HOPDONG.KHOIPHUC_SD) {
                  let kq_db = await this.MakeRequestAPI_DONGBO_BACKEND(hdtb_id.toString())
                  if (kq_db != 'OK') {
                    this.$toast.warning(kq_db)
                    return
                  }
                  kq = await this.MakeRequestAPI_KHOIPHUC_BHXH(hdtb_id.toString())
                }
                if (loaihd_id == LOAI_HOPDONG.CHAMDUT_SD) {
                  let kq_db = await this.MakeRequestAPI_DONGBO_BACKEND(hdtb_id.toString())
                  if (kq_db != 'OK') {
                    this.$toast.warning(kq_db)
                    return
                  }
                  kq = await this.MakeRequest_ThanhLy_BHXH(hdtb_id.toString())
                }
                if (kq != 'OK') {
                  return
                }
              } catch (ex) {
                commonFn.showException(this, ex)
              }
            })
        })
    },
    async MakeRequest_ThanhLy_BHXH(hdtb_id) {
      let self = this
      let result = 'Có lỗi xảy ra trong quá trình kích hoạt. Liên hệ admin để được hỗ trợ. (func: lay_dulieu_cho_api)'
      let params = {
        vhdtb_id: hdtb_id
      }
      let dt_hd_thuebao = this.GetData(await api.lay_dulieu_cho_api(this.axios, { vhdtb_id: hdtb_id }))
      if (!dt_hd_thuebao || dt_hd_thuebao.length <= 0) {
        this.$toast.error('Có lỗi xảy ra trong quá trình kích hoạt. Liên hệ admin để được hỗ trợ. (func: lay_dulieu_cho_api)')
        return ''
      }
      //let base_url = this.GetData(await api.fn_khaibao_api(this.axios, {"vhdtb_id": hdtb_id}));
      //khởi tạo dịch vụ IVAN
      let expando = dt_hd_thuebao[0]
      expando['ten_dang_nhap_am_backend'] = this.state.TenDangNhapIVAN
      let kq_return = (await api.ivan_thanhly_dv(this.axios, expando)).data.data
      let req = {
        vaction_name: 'THANHLY_BHXH',
        vlog_info: JSON.stringify(kq_return),
        vnote: 'hdtb_id=' + hdtb_id,
        vnguoi_cn: this.tt_nd.nguoi_cn,
        vmay_cn: this.tt_nd.may_cn,
        vip_cn: this.tt_nd.ip_cn
      }
      await api.sp_log_api(this.axios, req)
      if (kq_return.status == '200') {
        //luu log
        await api.sp_capnhat_status_kichhoat(this.axios, { vhdtb_id: hdtb_id })
        this.$toast.success(kq_return.message)
        this.controls.tsbtnHoanThienHS.enabled = true
      } else {
        this.$toast.error('Có lỗi: ' + kq_return.message + ' .\nHãy liên hệ Admin để xử lý')
        return ''
      }
      return 'OK'
    },
    async MakeRequest_ThanhLy_BHXH(hdtb_id) {
      let self = this
      let result = 'Có lỗi xảy ra trong quá trình kích hoạt. Liên hệ admin để được hỗ trợ. (func: lay_dulieu_cho_api)'
      let params = {
        vhdtb_id: hdtb_id
      }
      let dt_hd_thuebao = this.GetData(await api.lay_dulieu_cho_api(this.axios, { vhdtb_id: hdtb_id }))
      if (!dt_hd_thuebao || dt_hd_thuebao.length <= 0) {
        this.$toast.error('Có lỗi xảy ra trong quá trình kích hoạt. Liên hệ admin để được hỗ trợ. (func: lay_dulieu_cho_api)')
        return ''
      }
      //let base_url = this.GetData(await api.fn_khaibao_api(this.axios, {"vhdtb_id": hdtb_id}));
      //khởi tạo dịch vụ IVAN
      let expando = dt_hd_thuebao[0]
      expando['ten_dang_nhap_am_backend'] = this.state.TenDangNhapIVAN
      let kq_return = (await api.ivan_thanhly_dv(this.axios, expando)).data.data
      let req = {
        vaction_name: 'THANHLY_BHXH',
        vlog_info: JSON.stringify(kq_return),
        vnote: 'hdtb_id=' + hdtb_id,
        vnguoi_cn: this.tt_nd.nguoi_cn,
        vmay_cn: this.tt_nd.may_cn,
        vip_cn: this.tt_nd.ip_cn
      }
      await api.sp_log_api(this.axios, req)
      if (kq_return.status == '200') {
        //luu log
        await api.sp_capnhat_status_kichhoat(this.axios, { vhdtb_id: hdtb_id })
        this.$toast.success(kq_return.message)
        this.controls.tsbtnHoanThienHS.enabled = true
      } else {
        this.$toast.error('Có lỗi: ' + kq_return.message + ' .\nHãy liên hệ Admin để xử lý')
        return ''
      }
      return 'OK'
    },
    async MakeRequestAPI_TAO_BHXH(hdtb_id) {
      let kq_kt = this.GetData(await api.fn_kiemtra_dauvao_api(this.axios, { vhdtb_id: hdtb_id }))
      if (kq_kt != 'OK') {
        this.$toast.error(kq_kt)
        return ''
      } else {
        let dt_hd_thuebao = this.GetData(await api.lay_dulieu_cho_api(this.axios, { vhdtb_id: hdtb_id }))
        if (!dt_hd_thuebao || dt_hd_thuebao.length <= 0) {
          this.$toast.error('Có lỗi xảy ra trong quá trình kích hoạt. Liên hệ admin để được hỗ trợ. (func: lay_dulieu_cho_api)')
          return ''
        }
        //let base_url = this.GetData(await api.fn_khaibao_api(this.axios, {"vhdtb_id": hdtb_id}));
        //khởi tạo dịch vụ IVAN
        let expando = dt_hd_thuebao[0]
        expando['ten_dang_nhap_am_backend'] = this.state.TenDangNhapIVAN
        //gọi api kích hoạt IVAN /ivan/a101
        let kq_return = this.GetData(await api.ivan_khoitao_dv(this.axios, expando))
        //lưu log
        let req = {
          vaction_name: 'TAO_BHXH',
          vlog_info: JSON.stringify(kq_return),
          vnote: 'hdtb_id=' + hdtb_id,
          vnguoi_cn: this.tt_nd.nguoi_cn,
          vmay_cn: this.tt_nd.may_cn,
          vip_cn: this.tt_nd.ip_cn
        }
        await api.sp_log_api(this.axios, req)
        if (kq_return.status == '200') {
          //luu log
          await api.sp_capnhat_status_kichhoat(this.axios, { vhdtb_id: hdtb_id })
          this.$toast.success(kq_return.message)
          this.controls.tsbtnHoanThienHS.enabled = true
        } else {
          this.$toast.error('Có lỗi: ' + kq_return.message + ' .\nHãy liên hệ Admin để xử lý')
          return ''
        }
      }
      return 'OK'
    },
    async MakeRequestAPI_DONGBO_BACKEND(hdtb_id) {
      try {
        let dt_thuebao = this.GetData(
          await api.lay_dulieu_cho_api_theo_command(this.axios, {
            vhdtb_id: hdtb_id,
            vcommand: 'DONGBO_IVAN'
          })
        )
        if (!dt_thuebao || dt_thuebao.length <= 0) {
          this.$toast.error('Có lỗi xảy ra trong quá trình kích hoạt. Liên hệ admin để được hỗ trợ. (func: lay_dulieu_cho_api_theo_command)')
          return ''
        }
        //let base_url = this.GetData(await api.fn_khaibao_api(this.axios, {"vhdtb_id": hdtb_id}));
        //Gọi api đồng bộ IVAN
        let expando = dt_thuebao[0]
        let kq_return = this.GetData(await api.ivan_dongbo(this.axios, expando))
        //lưu log
        let req = {
          vaction_name: 'DONGBO_BHXH',
          vlog_info: JSON.stringify(kq_return),
          vnote: 'hdtb_id=' + hdtb_id,
          vnguoi_cn: this.tt_nd.nguoi_cn,
          vmay_cn: this.tt_nd.may_cn,
          vip_cn: this.tt_nd.ip_cn
        }
        await api.sp_log_api(this.axios, req)
        if (kq_return.status == '200') {
          return 'OK'
        } else {
          return kq_return
        }
      } catch (ex) {
        console.error(ex)
        if (ex.message) this.$toast.error(ex.message)
        else if (ex.data && ex.data.message) {
          this.$toast.error(ex.data.message)
        } else {
          this.$toast.error(ex)
        }
      }
      return 'OK'
    },
    async MakeRequestAPI_GiaHan_BHXH(hdtb_id) {
      let kq_kt = this.GetData(await api.fn_kiemtra_dauvao_api(this.axios, { vhdtb_id: hdtb_id }))
      if (kq_kt != 'OK') {
        this.$toast.error(kq_kt)
        return ''
      } else {
        let dt_hd_thuebao = this.GetData(await api.lay_dulieu_cho_api(this.axios, { vhdtb_id: hdtb_id }))
        if (!dt_hd_thuebao || dt_hd_thuebao.length <= 0) {
          this.$toast.error('Có lỗi xảy ra trong quá trình kích hoạt. Liên hệ admin để được hỗ trợ. (func: lay_dulieu_cho_api)')
          return ''
        }
        //let base_url = this.GetData(await api.fn_khaibao_api(this.axios, {"vhdtb_id": hdtb_id}));
        //khởi tạo dịch vụ IVAN
        let expando = dt_hd_thuebao[0]
        expando['ten_dang_nhap_am_backend'] = this.state.TenDangNhapIVAN
        let kq_return = 'NOK'
        if (this.state.kieuld_id == 13248 || this.state.kieuld_id == 13177) {
          kq_return = this.GetData(await api.ivan_giahan_dv(this.axios, expando))
        } else if (this.state.kieuld_id == 13179) {
          kq_return = this.GetData(await api.ivan_thaydoi_tt(this.axios, expando))
        }
        //lưu log
        let req = {
          vaction_name: 'GIAHAN_BHXH',
          vlog_info: JSON.stringify(kq_return),
          vnote: 'hdtb_id=' + hdtb_id,
          vnguoi_cn: this.tt_nd.nguoi_cn,
          vmay_cn: this.tt_nd.may_cn,
          vip_cn: this.tt_nd.ip_cn
        }
        await api.sp_log_api(this.axios, req)
        if (kq_return.status == '200' || this.state.kieuld_id == 14050) {
          //luu log
          await api.sp_capnhat_status_kichhoat(this.axios, { vhdtb_id: hdtb_id })
          this.$toast.success(kq_return.message)
          this.controls.tsbtnHoanThienHS.enabled = true
        } else {
          this.$toast.error('Có lỗi: ' + kq_return.message + ' .\nHãy liên hệ Admin để xử lý')
          return ''
        }
      }
      return 'OK'
    },
    async MakeRequestAPI_KHOIPHUC_BHXH(hdtb_id) {
      let kq_kt = this.GetData(await api.fn_kiemtra_dauvao_api(this.axios, { vhdtb_id: hdtb_id }))
      if (kq_kt != 'OK') {
        this.$toast.error(kq_kt)
        return ''
      } else {
        let dt_hd_thuebao = this.GetData(await api.lay_dulieu_cho_api(this.axios, { vhdtb_id: hdtb_id }))
        if (!dt_hd_thuebao || dt_hd_thuebao.length <= 0) {
          this.$toast.error('Có lỗi xảy ra trong quá trình kích hoạt. Liên hệ admin để được hỗ trợ. (func: lay_dulieu_cho_api)')
          return ''
        }
        //let base_url = this.GetData(await api.fn_khaibao_api(this.axios, {"vhdtb_id": hdtb_id}));
        //khởi tạo dịch vụ IVAN
        let expando = dt_hd_thuebao[0]
        expando['ten_dang_nhap_am_backend'] = this.state.TenDangNhapIVAN
        //url = base_url + "/ivan/khoiphuc_sd";
        let kq_return = this.GetData(await api.ivan_khoiphuc_sd(this.axios, expando))
        //lưu log
        let req = {
          vaction_name: 'KHOIPHUC_BHXH',
          vlog_info: JSON.stringify(kq_return),
          vnote: 'hdtb_id=' + hdtb_id,
          vnguoi_cn: this.tt_nd.nguoi_cn,
          vmay_cn: this.tt_nd.may_cn,
          vip_cn: this.tt_nd.ip_cn
        }
        await api.sp_log_api(this.axios, req)
        if (kq_return.status == '200') {
          //luu log
          await api.sp_capnhat_status_kichhoat(this.axios, { vhdtb_id: hdtb_id })
          this.$toast.success(kq_return.message)
          this.controls.tsbtnHoanThienHS.enabled = true
        } else {
          this.$toast.error('Có lỗi: ' + kq_return.message + ' .\nHãy liên hệ Admin để xử lý')
          return ''
        }
      }
      return 'OK'
    },
    ivan_logINSuccess(data) {
      console.log('ivan_logINSuccess', data)
      this.state.TenDangNhapIVAN = data
      this.ivan_kichhoat()
    },
    tsbtnHoanThanh_Click() {
      var grid = this.$refs.gv_DanhSach
      var seletedRows = grid.getSelectedRecords()
      if (!seletedRows || seletedRows.length <= 0) {
        this.$toast.error('Bạn chưa chọn thuê bao!')
        return
      }
      this.$bvModal
        .msgBoxConfirm('Bạn thật sự muốn hoàn thành không ?', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
          centered: true
        })
        .then(async (v) => {
          if (!v) {
            return
          }
          try {
            //this.loading(true)
            var postData = {
              vphieu_id: this.state.phieu_id,
              vhdtb_id: this.state.hdtb_id,
              vngay_th: moment(this.tt_nd.ngay_cn, 'DD/MM/YYYY HH:mm A').format('DD/MM/YYYY HH:mm:ss'),
              vttph_id: TRANGTHAI_PH.DATHUCHIEN
            }
            //this.loading(true)
            await CompleteProfileAPI.fn_an_hoanthanh(this.axios, postData).then((res) => {
              this.hideLoading()
              if (res.data.error == '200' && res.data.error_code == 'BSS-00000000') {
                if (res.data.data && res.data.data.indexOf('thành công') > 0) {
                  this.$toast.success(res.data.data)
                  //this.gv_DanhSach_FocusedRowChanged(this.state.selectedPhieu);
                  this.HienThiDanhSachHDTB()
                } else {
                  this.$toast.error(res.data.data)
                }
              } else this.$toast.error('Lỗi cập nhật dữ liệu: ' + res.data.message)
            })
          } catch (ex) {
            //this.loading(false)
            commonFn.showException(this, ex)
          }
          //this.loading(false)
        })
    },
    async xTabControl_SelectedPageChanged(tabName) {
      this.state.SelectedTabPage = tabName
      if (!this.state.phieu_id || this.state.phieu_id <= 0) {
        this.CLEAR()
        return
      }
      switch (tabName) {
        case 'TabIT360':
          await this.NAP_DS_ISSUES()
          this.state.it_hdtb_id = this.state.hdtb_id
          break
        case 'tabTSKT':
          await this.NAP_DS_TSKT()
          break
        case 'xtraTabPage2':
          await this.NAP_GRD_TIENTRINH()
          break
        case 'xtraTabPage3':
          await this.HienThiDS_PhieuTH(this.state.phieu_id)
          break
      }
    },
    async btnAssignee_ButtonClick() {
      if (this.state.btnAssignee && this.state.btnAssignee != '') {
        //this.loading(true)
        try {
          var ds = await commonFn.Find_Employees_By_EmailOrPhone(this.axios, this.state.btnAssignee.trim())
          console.log('Find_Employees_By_EmailOrPhone', ds)
          if (ds && ds.success) {
            let dt_hrm = ds.result.records
            let str = '<b>Tên nhân viên :</b> ' + dt_hrm[0]['name'].toString() + '.<br><b>Đơn vị :</b> ' + dt_hrm[0]['department'].toString() + '.<br><b>Công việc :</b> ' + dt_hrm[0]['job_name'].toString() + '.<br><b>Số điện thoại :</b> ' + dt_hrm[0]['mobile_phone'].toString()
            const h = this.$createElement
            const titleVNode = h('div', { domProps: { innerHTML: str } })
            this.$bvModal.msgBoxOk([titleVNode], {
              centered: true,
              buttonSize: 'sm'
            })
          } else {
            this.$toast.error('Không lấy được thông tin từ HRM')
          }
        } catch (ex) {
          //this.loading(false)
          commonFn.showException(this, ex)
        } finally {
          this.hideLoading()
        }
      }
    },
    async tsbXemHS_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.warning('Bạn chưa chọn phiếu')
        return
      }
      let data = {
        hdtb_id: this.state.hdtb_id,
        loaitb_id: this.state.loaitb_id,
        onClose: false
      }
      if (this.state.vloaitb_id == 116) {
        if (await this.PHAILAM(this.state.luong_id, 'CA_GUIYC')) {
          data.isToanTrinh = true
        }
        data.batbuocXemToanBohoSo = true
      }
      console.log('tsbXemHS_Click', data)
      this.$refs.popupTraCuuHoSoHDTB.openDialog(data)
    },
    async tsbXemCMS_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.warning('Bạn chưa chọn phiếu')
        return
      }
      try {
        if (this.state.vloaitb_id == LOAIHINH_THUEBAO.CA || this.state.vloaitb_id == LOAIHINH_THUEBAO.SMART_CA) {
          let data = {
            hdtb_id: this.state.hdtb_id,
            in_ma_gd: this.state.ma_gd,
            isKHDN: false,
            tinh_id: 0,
            TopMost: true,
            loaihd_id: this.state.loaihd_id,
            kieuld_id: this.state.kieuld_id
          }
          console.log('tsbXemCMS_Click', data)
          this.$refs.popupDoiSoatCA.openDialog(data)
        }
        if (this.state.vloaitb_id == LOAIHINH_THUEBAO.VNPT_SLL) {
          let truong_id = ''
          let ItemList = this.tab_dvcntt.list
          for (const item of ItemList) {
            if (item.sFieldName == 'MA_PHU') {
              truong_id = item.sValue
              break
            }
          }
          console.log('tsbXemCMS_Click', truong_id)
          //this.loading(true)
          let kq = this.GetData(await api.getToTalSmsByTruong(this.axios, truong_id))
          if (kq) {
            let msg = `Thông tin đăng ký sổ liên lạc của nhà trường: \n Tổng số: ${kq.total} \n Nội mạng: ${kq.noi_mang} \n Ngoại mạng: ${kq.ngoai_mang}`
            const h = this.$createElement
            const titleVNode = h('div', { domProps: { innerHTML: msg } })
            this.$bvModal.msgBoxOk([titleVNode], {
              centered: true,
              buttonSize: 'sm'
            })
          } else {
            this.$toast.error('Không lấy được dữ liệu! truong_id: ' + truong_id)
          }
        }
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      //this.loading(false)
    },
    async tsbtn_Duyet_CA_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.warning('Bạn chưa chọn phiếu')
        return
      }
      try {
        this.loading(true)
        let kq = this.GetData(await api.kiem_tra_quyen_duyet_ca(this.axios, { ma_nd: this.tt_nd.ma_nd }))
        if (!kq || kq !== 'ok') {
          //this.loading(false)
          this.$toast.warning('Bạn không có quyền duyệt')
          return
        }
        if (this.state.loaihd_id == 24) {
          await this.DuyetCAChuanHoa()
        } else if (this.state.loaihd_id == 1 || this.state.loaihd_id == 41 || this.state.loaihd_id == 7 || this.state.loaihd_id == 4) {
          let data = {
            luong_thaotac: this.state.luong_thaotac,
            luong_id: this.state.luong_id,
            loaihd_id: this.state.loaihd_id,
            kieuld_id: this.state.kieuld_id,
            hdtb_id: this.state.hdtb_id,
            thuebao_id: this.state.thuebao_id,
            ma_tinh: this.tt_nd.ma_tinh,
            ma_gd: this.state.ma_gd,
            ma_tb: this.state.ma_tb
          }
          let kt_thunghiem = await api.kiemtra_nangcap_phienban(this.axios, {
            ma_dv: 'smartca_v2',
            hdtb_id: data.hdtb_id
          })
          if (this.state.kieuld_id == 14045 || this.state.kieuld_id == 14067 || this.state.kieuld_id == 14073) { // truong hop kenh online
            let data = {
              kieuld_id: this.state.kieuld_id,
              hdtb_id: this.state.hdtb_id,
              phieu_id: this.state.phieu_id,
              thuebao_id: this.state.thuebao_id,
              ma_gd: this.state.ma_gd
            }
            if (kt_thunghiem.data.data == 'ok') {
              this.$refs.frmDuyetSmartCAOnline.openDialog(this, data)
            } else {
              let kq2 = await smartca.DuyetCAOnline(this, data)
              console.log(kq2)
              this.GetData(await api.cap_nhat_status_hdtb(this.axios, { vhdtb_id: data.hdtb_id, vstatus: 7 }))
              this.$toast.success(kq2)
            }
            // dat moi CA OneSME
          } else { // truong hop kenh offline
            if (kt_thunghiem.data.data == 'ok') {
              // thu nghiem qnm dat moi
              //this.$refs.frmDuyetSmartCAOnline.openDialog(this, data)
              this.loading(false)
              let isConfirm = await this.$bvModal.msgBoxConfirm('Bạn muốn duyệt bằng SmartCA hay CA?', {
                title: '',
                size: 'sm',
                okTitle: 'SmartCA',
                cancelTitle: 'CA',
                centered: true
              })
              if (isConfirm == true) {  // duyet bang smartca
                let kt_duyet_ra_auto = await api.kiemtra_hoso_du(this.axios, {  // loai du ho so khong can ekyc
                  vhdtb_id: data.hdtb_id,
                  vhdkh_id: data.hdkh_id
                })
                if (kt_duyet_ra_auto.data.data != 'OK') { // duyet khi can ekyc ho so
                  // duyet khong du ho so, can ekyc
                  // tao req_duyet
                  let req_duyet = ''
                  req_duyet = await api.tao_req_duyet_smca(this.axios, {
                    hdtb_id: data.hdtb_id
                  })
                  // }
                  console.log(req_duyet)
                  //end tao req_duyet
               //   let input_hash = data.ma_gd + '.' + data.ma_tb + ':' + req_duyet.data.data.req_id + ':0:' + req_duyet.data.data.uid
                  let input_hash = req_duyet.data.data.input_hash
                  let lydo_duyet_smcav2 = 'Duyet CA'
                  lydo_duyet_smcav2 = req_duyet.data.data.lydo
               /*   if (data.kieuld_id == 14074) { // doi email, so dt
                    input_hash = data.ma_tb + ':' + req_duyet.data.data.uid + ':' + req_duyet.data.data.sdt + ':' + req_duyet.data.data.email
                    lydo_duyet_smcav2 = req_duyet.data.data.lydo
                  }
                  if (data.kieuld_id == 13263) { // duyet thu hoi tao loi
                    input_hash = req_duyet.data.data.input_hash
                  }*/
                  let ketqua_ky = ''
                  let kq = await this.$refs.frm_smartca_sign.show(
                    input_hash,
                    'hash',
                    (x) => {
                      ketqua_ky = x
                    },
                    lydo_duyet_smcav2
                  );

                  let duyet_smca_req = req_duyet.data.data.body_duyet;
                /* let duyet_smca_req = {
                    body: {
                     // refRequestId: data.ma_gd + '.' + data.ma_tb,
                      refRequestId: req_duyet.data.data.ref_request_id,
                      requestcertId: req_duyet.data.data.req_id,
                      status: 0,
                      uid: req_duyet.data.data.uid,
                      reason: 'Duyet CA',
                      caSerial: kq.device_infor.serial_number
                    },
                    cert: kq.device_infor.cert_base64,
                    signature: kq.sign_value
                  }*/

                /*  if (req_duyet.data.data.body_duyet.body) // request duyet thong thuong
                  {
                    duyet_smca_req.body = {
                      ...duyet_smca_req.body,
                      caSerial: kq.device_infor.serial_number
                    };
                    duyet_smca_req = {
                      ...duyet_smca_req,
                      cert: kq.device_infor.cert_base64,
                      signature: kq.sign_value
                    };
                    console.log(duyet_smca_req)
                  }
                  else // req duyet doi email
                  {
                    duyet_smca_req = {
                      ...duyet_smca_req,
                      cert: kq.device_infor.cert_base64,
                      signature: kq.sign_value,
                      caSerial: kq.device_infor.serial_number,
                    };
                  }*/
                    let duyet_smca_v2 = ''
                  if (data.kieuld_id == 14074) { // neu la duyet doi email
                  /* duyet_smca_req = {
                      uid: req_duyet.data.data.uid,
                      ma_tb: data.ma_tb,
                      email: req_duyet.data.data.email,
                      phone: req_duyet.data.data.sdt,
                      reason: 'doi email-sdt',
                      raSerial: req_duyet.data.data.ra_serial,
                      caSerial: kq.device_infor.serial_number,
                      raStaffName: req_duyet.data.data.ra_staff,
                      raStaffID: req_duyet.data.data.ra_id,
                      cert: kq.device_infor.cert_base64,
                      signature: kq.sign_value,
                      fileDocuments: req_duyet.data.data.file_hs,
                      hdtb_id: data.hdtb_id
                    }*/
                    duyet_smca_req = {
                      ...duyet_smca_req,
                      cert: kq.device_infor.cert_base64,
                      signature: kq.sign_value,
                      caSerial: kq.device_infor.serial_number,
                    };
                    duyet_smca_v2 = await api.duyet_smca_doi_email_v2(this.axios, duyet_smca_req)
                  } else {
                    duyet_smca_req.body = {
                      ...duyet_smca_req.body,
                      caSerial: kq.device_infor.serial_number
                    };
                    duyet_smca_req = {
                      ...duyet_smca_req,
                      cert: kq.device_infor.cert_base64,
                      signature: kq.sign_value
                    };
                    console.log(duyet_smca_req)
                    duyet_smca_v2 = await api.duyet_smca_v2(this.axios, duyet_smca_req)
                  }
                  if (duyet_smca_v2.data.data.code != 0) {
                    throw duyet_smca_v2.data.data.message
                  }
                  this.$toast.success('Duyệt thành công!')
                }
                else
                { // duyet smartca full ho so
                  let lay_tt_ca_daydu = await api.lay_thongtin_duyet_smca_full_hoso(this.axios, {
                    vhdtb_id: data.hdtb_id
                  })
                  console.log(lay_tt_ca_daydu)
                  if (!lay_tt_ca_daydu.data.data) {
                    throw 'Không có thông tin duyệt Auto'
                  }
                  let tt_duyet = JSON.parse(lay_tt_ca_daydu.data.data)
                  if (!(tt_duyet.error_code == 200)) {
                    throw tt_duyet.error_message
                  }
                  console.log(tt_duyet.data)
                  let req_ra = JSON.parse(tt_duyet.data)
                  let raw_data = req_ra.raw_data
                  req_ra = JSON.parse(req_ra.req_ra)
                  delete req_ra.sub_onebss
                  console.log(req_ra)
                  let ketqua_ky = ''
                  let kq = await this.$refs.frm_smartca_sign.show(
                    raw_data,
                    'hash',
                    (x) => {
                      ketqua_ky = x
                    },
                    'Duyet CA'
                  )
                  console.log(ketqua_ky)
                  console.log(kq)
                  let req_ky = {
                    ...req_ra,
                    approve: {
                      reason: 'Duyet CA',
                      caSerial: kq.device_infor.serial_number,
                      signature: kq.sign_value,
                      certCA: kq.device_infor.cert_base64
                    }
                  }
                  console.log(req_ky)
                  this.loading(true)
                  let kq_duyet = await api.duyet_smca_du_hoso(this.axios, req_ky)
                  console.log(kq_duyet)
                  if (kq_duyet.data.data.code == 0) {
                    this.$toast.success('Duyệt SMCA thành công')
                  } else {
                    throw Error(kq_duyet.data.data.message | kq_duyet.data.data.codeDesc)
                  }
                  return
                }
              } // duyet bang CA
              else {
                let kt_duyet_ra_auto = await api.kiemtra_hoso_du(this.axios, {
                  vhdtb_id: data.hdtb_id,
                  vhdkh_id: data.hdkh_id
                })
                if (kt_duyet_ra_auto.data.data != 'OK') {
                  // duyet khong du ho so, can ekyc
                  // tao req_duyet
                  let req_duyet = ''
                  req_duyet = await api.tao_req_duyet_smca(this.axios, {
                    hdtb_id: data.hdtb_id
                  })
                  //end tao req_duyet

                  let input_hash = req_duyet.data.data.input_hash
                  let lydo_duyet_smcav2 = 'Duyet CA'
                  lydo_duyet_smcav2 = req_duyet.data.data.lydo

              /*    let input_hash = data.ma_gd + '.' + data.ma_tb + ':' + req_duyet.data.data.req_id + ':0:' + req_duyet.data.data.uid
                  let lydo_duyet = 'Duyet CA'
                  if (data.kieuld_id == 14074) {
                    lydo_duyet = 'doi email-sdt'
                    input_hash = data.ma_tb + ':' + req_duyet.data.data.uid + ':' + req_duyet.data.data.sdt + ':' + req_duyet.data.data.email
                  }*/
                  let ketqua_ky = ''
                  let kq = await this.$refs.frm_catoken_sign.show(
                    input_hash,
                    'hash',
                    (x) => {
                      ketqua_ky = x
                    },
                    lydo_duyet_smcav2
                  )

                  let duyet_smca_req = req_duyet.data.data.body_duyet;
                /*  let duyet_smca_req = {
                    body: {
                      refRequestId: data.ma_gd + '.' + data.ma_tb,
                      requestcertId: req_duyet.data.data.req_id,
                      status: 0,
                      uid: req_duyet.data.data.uid,
                      reason: 'Duyet CA',
                      caSerial: kq.device_infor.serial_number
                    },
                    cert: kq.device_infor.cert_base64,
                    signature: kq.sign_value
                  }*/
                  let duyet_smca_v2 = ''
                  if (data.kieuld_id == 14074) {
                  /*  duyet_smca_req = {
                      uid: req_duyet.data.data.uid,
                      ma_tb: data.ma_tb,
                      email: req_duyet.data.data.email,
                      phone: req_duyet.data.data.sdt,
                      reason: 'doi email-sdt',
                      raSerial: req_duyet.data.data.ra_serial,
                      caSerial: kq.device_infor.serial_number,
                      raStaffName: req_duyet.data.data.ra_staff,
                      raStaffID: req_duyet.data.data.ra_id,
                      cert: kq.device_infor.cert_base64,
                      signature: kq.sign_value,
                      fileDocuments: req_duyet.data.data.file_hs,
                      hdtb_id: data.hdtb_id
                    }*/
                    duyet_smca_req = {
                      ...duyet_smca_req,
                      cert: kq.device_infor.cert_base64,
                      signature: kq.sign_value,
                      caSerial: kq.device_infor.serial_number,
                    };
                    duyet_smca_v2 = await api.duyet_smca_doi_email_v2(this.axios, duyet_smca_req)
                  } else {
                    duyet_smca_req.body = {
                      ...duyet_smca_req.body,
                      caSerial: kq.device_infor.serial_number
                    };
                    duyet_smca_req = {
                      ...duyet_smca_req,
                      cert: kq.device_infor.cert_base64,
                      signature: kq.sign_value
                    };
                    console.log(duyet_smca_req)
                    duyet_smca_v2 = await api.duyet_smca_v2(this.axios, duyet_smca_req)
                  }
                  if (duyet_smca_v2.data.data.code != 0) {
                    throw duyet_smca_v2.data.data.message
                  }
                  this.$toast.success('Duyệt thành công!')
                } else {
                  let lay_tt_ca_daydu = await api.lay_thongtin_duyet_smca_full_hoso(this.axios, {
                    vhdtb_id: data.hdtb_id
                  })
                  console.log(lay_tt_ca_daydu)
                  if (!lay_tt_ca_daydu.data.data) {
                    throw 'Không có thông tin duyệt Auto'
                  }
                  let tt_duyet = JSON.parse(lay_tt_ca_daydu.data.data)
                  if (!(tt_duyet.error_code == 200)) {
                    throw tt_duyet.error_message
                  }
                  console.log(tt_duyet.data)
                  let req_ra = JSON.parse(tt_duyet.data)
                  let raw_data = req_ra.raw_data
                  req_ra = JSON.parse(req_ra.req_ra)
                  delete req_ra.sub_onebss
                  console.log(req_ra)
                  let ketqua_ky = ''
                  let kq = await this.$refs.frm_catoken_sign.show(
                    raw_data,
                    'hash',
                    (x) => {
                      ketqua_ky = x
                    },
                    'Duyet CA'
                  )
                  console.log(ketqua_ky)
                  console.log(kq)
                  let req_ky = {
                    ...req_ra,
                    approve: {
                      reason: 'Duyet CA',
                      caSerial: kq.device_infor.serial_number,
                      signature: kq.sign_value,
                      certCA: kq.device_infor.cert_base64
                    }
                  }
                  console.log(req_ky)
                  this.loading(true)
                  let kq_duyet = await api.duyet_smca_du_hoso(this.axios, req_ky)
                  console.log(kq_duyet)
                  if (kq_duyet.data.data.code == 0) {
                    this.$toast.success('Duyệt SMCA thành công')
                  } else {
                    console.log(kq_duyet)
                    throw Error(kq_duyet.data.data.message | kq_duyet.data.data.codeDesc)
                  }
                  return
                }
              }
              // return;
            } else {
              kq = await ca.DuyetCA(this, data)
              this.$toast.success(kq)
            }
          }
        } else {
          this.$toast.warning('Không phải loại hợp đồng CA !')
        }
      } catch (ex) {
        commonFn.showException(this, ex)
      } finally {
        this.loading(false)
      }
    },
    async DuyetCAChuanHoa() {
      this.loading(false)
      const isConfirm = await this.$bvModal.msgBoxConfirm(`Bạn phải kiểm hồ sơ bằng cách xem từng file hồ sơ trước khi duyệt`, {
        title: 'Thông báo',
        size: 'sm',
        okTitle: 'Đồng ý',
        cancelTitle: 'Hủy',
        centered: true
      })
      if (!isConfirm) return
      //this.loading(true)
      //Kiem_Dem_HoSo
      let data = {
        hdtb_id: this.state.hdtb_id,
        batbuocXemToanBohoSo: true,
        onClose: true
      }
      if (await this.PHAILAM(this.state.luong_id, 'CA_GUIYC')) {
        data.isToanTrinh = true
      }
      this.$refs.popupTraCuuHoSoHDTB.openDialog(data)
    },
    async Kiem_Dem_HoSo_Callback(data) {
      // if(!data.daxemtatca == true){
      //   this.$toast.warning("Bạn chưa kiểm đếm đầy đủ hồ sơ");
      //   return;
      // }
      //this.loading(false)
      const isConfirm = await this.$bvModal.msgBoxConfirm(`Bạn có chắc là sẽ duyệt phiếu này?`, {
        title: 'Thông báo',
        size: 'sm',
        okTitle: 'Đồng ý',
        cancelTitle: 'Hủy',
        centered: true
      })
      if (!isConfirm) return
      try {
        //this.loading(true)
        let kq = this.GetData(await api.sp_update_hd_thuebao_duyetcachuanhoa(this.axios, { vhdtb_id: this.state.hdtb_id }))
        if (kq) {
          this.$toast.success('Duyệt chuẩn hóa CA thành công!')
        } else {
          this.$toast.error(kq)
        }
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      //this.loading(false)
    },
    async DuyetCA() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.warning('Bạn chưa chọn phiếu')
        return
      }
      try {
        //this.loading(true)
        let data = {
          luong_thaotac: this.state.luong_thaotac,
          luong_id: this.state.luong_id,
          loaihd_id: this.state.loaihd_id,
          kieuld_id: this.state.kieuld_id,
          hdtb_id: this.state.hdtb_id,
          thuebao_id: this.state.thuebao_id,
          ma_tinh: this.tt_nd.ma_tinh
        }
        let kq = ca.DuyetCA(this, data)
        this.$toast.success(kq)
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      //this.loading(false)
    },
    async tsbtn_khong_duyet_CA_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.warning('Bạn chưa chọn phiếu')
        return
      }
      try {
        this.loading(true)
        let data = {
          luong_thaotac: this.state.luong_thaotac,
          luong_id: this.state.luong_id,
          loaihd_id: this.state.loaihd_id,
          kieuld_id: this.state.kieuld_id,
          hdtb_id: this.state.hdtb_id,
          thuebao_id: this.state.thuebao_id,
          ma_tinh: this.tt_nd.ma_tinh,
          ma_gd: this.state.ma_gd
        }
        let kt_thunghiem = await api.kiemtra_nangcap_phienban(this.axios, {
          ma_dv: 'smartca_v2',
          hdtb_id: data.hdtb_id
        })
        if (data.kieuld_id == 14045 || data.kieuld_id == 14067) {
          if (kt_thunghiem.data.data == 'ok') {
            this.$refs.frmDuyetSmartCAOnline.openDialog(this, data)
          } else {
            this.$refs.frmHuyDuyetCAOnline.openDialog(data)
          }
        } else {
          if (kt_thunghiem.data.data == 'ok') {
            // thu nghiem qnm
           // this.$refs.frmDuyetSmartCAOnline.openDialog(this, data)

            this.loading(false)
            let isConfirm = await this.$bvModal.msgBoxConfirm('Bạn muốn hủy duyệt bằng SmartCA hay CA?', {
              title: '',
              size: 'sm',
              okTitle: 'SmartCA',
              cancelTitle: 'CA',
              centered: true
            })
            if (isConfirm == true) {  // duyet bang smartca
              let kt_duyet_ra_auto = await api.kiemtra_hoso_du(this.axios, {  // loai du ho so khong can ekyc
                vhdtb_id: data.hdtb_id,
                vhdkh_id: data.hdkh_id
              })
              if (kt_duyet_ra_auto.data.data != 'OK') { // duyet khi can ekyc ho so
                // duyet khong du ho so, can ekyc
                // tao req_duyet
                let req_duyet = ''
                req_duyet = await api.tao_req_huy_duyet_smca(this.axios, {
                  hdtb_id: data.hdtb_id
                })
                // }
                console.log(req_duyet)
                //end tao req_duyet
                //   let input_hash = data.ma_gd + '.' + data.ma_tb + ':' + req_duyet.data.data.req_id + ':0:' + req_duyet.data.data.uid
                let input_hash = req_duyet.data.data.input_hash
                let lydo_duyet_smcav2 = 'Huy Duyet CA'
                lydo_duyet_smcav2 = req_duyet.data.data.lydo

                let ketqua_ky = ''
                let kq = await this.$refs.frm_smartca_sign.show(
                  input_hash,
                  'hash',
                  (x) => {
                    ketqua_ky = x
                  },
                  lydo_duyet_smcav2
                );

                let duyet_smca_req = req_duyet.data.data.body_duyet;
                let duyet_smca_v2 = ''
                if (data.kieuld_id == 14074) { // neu la duyet doi email

                  duyet_smca_req = {
                    ...duyet_smca_req,
                    cert: kq.device_infor.cert_base64,
                    signature: kq.sign_value,
                    caSerial: kq.device_infor.serial_number,
                  };
                  duyet_smca_v2 = await api.duyet_smca_doi_email_v2(this.axios, duyet_smca_req)
                } else {
                  duyet_smca_req.body = {
                    ...duyet_smca_req.body,
                    caSerial: kq.device_infor.serial_number
                  };
                  duyet_smca_req = {
                    ...duyet_smca_req,
                    cert: kq.device_infor.cert_base64,
                    signature: kq.sign_value
                  };
                  console.log(duyet_smca_req)
                  duyet_smca_v2 = await api.duyet_smca_v2(this.axios, duyet_smca_req)
                }
                if (duyet_smca_v2.data.data.code != 0) {
                  throw duyet_smca_v2.data.data.message
                }
                this.$toast.success('Hủy Duyệt thành công!')
              }
            } // duyet bang CA
            else {
              let kt_duyet_ra_auto = await api.kiemtra_hoso_du(this.axios, {
                vhdtb_id: data.hdtb_id,
                vhdkh_id: data.hdkh_id
              })
              if (kt_duyet_ra_auto.data.data != 'OK') {
                // duyet khong du ho so, can ekyc
                // tao req_duyet
                let req_duyet = ''
                req_duyet = await api.tao_req_huy_duyet_smca(this.axios, {
                  hdtb_id: data.hdtb_id
                })
                //end tao req_duyet

                let input_hash = req_duyet.data.data.input_hash
                console.log(input_hash)
                let lydo_duyet_smcav2 = 'Huy Duyet CA'
                lydo_duyet_smcav2 = req_duyet.data.data.lydo
                let ketqua_ky = ''
                let kq = await this.$refs.frm_catoken_sign.show(
                  input_hash,
                  'hash',
                  (x) => {
                    ketqua_ky = x
                  },
                  lydo_duyet_smcav2
                )
                let duyet_smca_req = req_duyet.data.data.body_duyet;

                let duyet_smca_v2 = ''
                if (data.kieuld_id == 14074) {

                  duyet_smca_req = {
                    ...duyet_smca_req,
                    cert: kq.device_infor.cert_base64,
                    signature: kq.sign_value,
                    caSerial: kq.device_infor.serial_number,
                  };
                  duyet_smca_v2 = await api.duyet_smca_doi_email_v2(this.axios, duyet_smca_req)
                } else {
                  duyet_smca_req.body = {
                    ...duyet_smca_req.body,
                    caSerial: kq.device_infor.serial_number
                  };
                  duyet_smca_req = {
                    ...duyet_smca_req,
                    cert: kq.device_infor.cert_base64,
                    signature: kq.sign_value
                  };
                  console.log(duyet_smca_req)
                  duyet_smca_v2 = await api.duyet_smca_v2(this.axios, duyet_smca_req)
                }
                if (duyet_smca_v2.data.data.code != 0) {
                  throw duyet_smca_v2.data.data.message
                }
                this.$toast.success('Hủy duyệt thành công!')
              } else {
                let lay_tt_ca_daydu = await api.lay_thongtin_duyet_smca_full_hoso(this.axios, {
                  vhdtb_id: data.hdtb_id
                })
                console.log(lay_tt_ca_daydu)
                if (!lay_tt_ca_daydu.data.data) {
                  throw 'Không có thông tin duyệt Auto'
                }
                let tt_duyet = JSON.parse(lay_tt_ca_daydu.data.data)
                if (!(tt_duyet.error_code == 200)) {
                  throw tt_duyet.error_message
                }
                console.log(tt_duyet.data)
                let req_ra = JSON.parse(tt_duyet.data)
                let raw_data = req_ra.raw_data
                req_ra = JSON.parse(req_ra.req_ra)
                delete req_ra.sub_onebss
                console.log(req_ra)
                let ketqua_ky = ''
                let kq = await this.$refs.frm_catoken_sign.show(
                  raw_data,
                  'hash',
                  (x) => {
                    ketqua_ky = x
                  },
                  'Duyet CA'
                )
                console.log(ketqua_ky)
                console.log(kq)
                let req_ky = {
                  ...req_ra,
                  approve: {
                    reason: 'Duyet CA',
                    caSerial: kq.device_infor.serial_number,
                    signature: kq.sign_value,
                    certCA: kq.device_infor.cert_base64
                  }
                }
                console.log(req_ky)
                this.loading(true)
                let kq_duyet = await api.duyet_smca_du_hoso(this.axios, req_ky)
                console.log(kq_duyet)
                if (kq_duyet.data.data.code == 0) {
                  this.$toast.success('Duyệt SMCA thành công')
                } else {
                  console.log(kq_duyet)
                  throw Error(kq_duyet.data.data.message | kq_duyet.data.data.codeDesc)
                }
                return
              }
            }

          } else {
            let kq = await ca.KhongDuyetCA(this, data)
            if (kq.code == 200) {
              this.$toast.success(kq.des)
            } else {
              this.$toast.error(kq.des)
            }
          }
        }
      } catch (ex) {
        commonFn.showException(this, ex)
      } finally {
        this.loading(false)
      }
      //this.loading(false)
    },
    async tsbtnGuiHSCMS_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.warning('Bạn chưa chọn phiếu')
        return
      }
      try {
        //this.loading(true)
        let data = {
          luong_thaotac: this.state.luong_thaotac,
          luong_id: this.state.luong_id,
          loaihd_id: this.state.loaihd_id,
          kieuld_id: this.state.kieuld_id,
          hdtb_id: this.state.hdtb_id,
          thuebao_id: this.state.thuebao_id,
          ma_tinh: this.tt_nd.ma_tinh
        }
        let kq = await ca.GuiHSCMS(this, data)
        this.$toast.success(kq)
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      //this.loading(false)
    },
    async tsbtnDongBoHS_Click() {
      try {
        //this.loading(true)
        let data = {
          luong_thaotac: this.state.luong_thaotac,
          luong_id: this.state.luong_id,
          loaihd_id: this.state.loaihd_id,
          kieuld_id: this.state.kieuld_id,
          hdtb_id: this.state.hdtb_id,
          thuebao_id: this.state.thuebao_id,
          ma_tinh: this.tt_nd.ma_tinh
        }
        let kq = await ca.DongBoHS(this, data)
        this.$toast.success(kq)
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      //this.loading(false)
    },
    async tsbTaoToken_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.warning('Bạn chưa chọn phiếu')
        return
      }
      try {
        //this.loading(true)
        let data = {
          luong_thaotac: this.state.luong_thaotac,
          luong_id: this.state.luong_id,
          loaihd_id: this.state.loaihd_id,
          kieuld_id: this.state.kieuld_id,
          hdtb_id: this.state.hdtb_id,
          thuebao_id: this.state.thuebao_id,
          ma_tinh: this.tt_nd.ma_tinh
        }
        //frmThaoTacCAHangLoat
        // let kq = ca.DongBoHS(this, data);
        // this.$toast.success(kq);
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      //this.loading(false)
    },
    async tsbtnXanNhanBT_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.warning('Bạn chưa chọn phiếu')
        return
      }
      let data = {
        hdtb_id: this.state.hdtb_id,
        tag: 1
      }
      console.log('tsbtnXanNhanBT_Click', data)
      this.$refs.popupXacNhanBaoTri.openDialog(data)
    },
    async tsbtnKiemTraCA_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.warning('Bạn chưa chọn phiếu')
        return
      }
      let data = {
        ma_gd: this.state.ma_gd
      }
      console.log('tsbtnKiemTraCA_Click', data)
      this.$refs.popupKiemTraSoGiayToCA.openDialog(data)
    },
    async tsbtnSmartCAStatus_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.warning('Bạn chưa chọn phiếu')
        return
      }
      try {
        //this.loading(true)
        let lst = this.GetData(await api.lay_dulieu_tao_thucthe_ca(this.axios, { params: { hdtb_id: this.state.hdtb_id } }))
        if (!lst || lst.length <= 0) {
          this.$root.$toast.warning('Không tìm thấy dữ liệu thực thể CA!')
          return
        }
        let req_id = lst[0].req_id
        let kq = ''
        let kt_status = this.state.kt_status
        if (kt_status == '5') {
          kq = await ca.KiemTraSmartCA(req_id)
        } else {
          kq = await ca.KiemTraStatus(req_id)
        }
        if (kq == 'ok') {
          let kt = this.GetData(await api.capnhat_trangthai_smartca(this.axios, { hdtb_id: this.state.hdtb_id }))
          if (kt == 'ok') {
            await this.gv_DanhSach_FocusedRowChanged(this.state.selectedPhieu)
            this.$toast.success('Đồng bộ trạng thái thành công, khách hàng đã ký biên bản')
          } else {
            this.$toast.error(kt + ' - Biên bản nghiệm thu chưa được đồng bộ')
          }
        } else {
          this.$toast.error(kq)
        }
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      //this.loading(false)
    },
    async tsbtnCreatIssue_Click() {
      // this.$refs.dialogCreateIssue.openDialog({
      //   kieu: 2,
      //   baohong_id: "3923641",
      //   phieu_baohong_id: "7254102"
      // });
      // return;
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.warning('Bạn chưa chọn phiếu')
        return
      }
      if (this.state.vloaitb_id == LOAIHINH_THUEBAO.Web_Hosting) {
        //TODO:
        // var kq_kiemtra = tdan.GET_VALUE_FUNC("{?DB2}.FN_KIEMTRA_THONGTIN_AM", "vhdtb_id", hdtb_id);
        // if (kq_kiemtra.toString() != "OK")
        // {
        //     Message_Box.ShowTB(kq_kiemtra.toString());
        //     return;
        // }
      }
      if (this.state.kieuld_id == 13273 || (this.state.kieuld_id == 13266 && this.state.loaitb_id == LOAIHINH_THUEBAO.TTDL_HDDT)) {
        this.$toast.error('Loại phức tạp (mẫu không có sẵn) mới cần gửi IT-360')
        return
      }
      let vip_gs = false
      if (await this.PHAILAM(this.state.luong_id, 'VIP_GIAMSAT_IT360')) vip_gs = true
      let kieu = 1
      if (this.state.kieuld_id == 13187 || this.state.kieuld_id == 13188) {
        kieu = 5
      }
      if (this.state.kieuld_id == 13238) {
        kieu = 6
      }
      var data = {
        hdtb_id: this.state.hdtb_id,
        phieu_id: this.state.phieu_id,
        loai: 1,
        kieu: kieu,
        vip_giamsat: vip_gs
      }
      console.log('tsbtnCreatIssue_Click', data)
      this.$refs.dialogCreateIssue.openDialog(data)
    },
    async btnThongTinTBSub_Click() {
      if (!this.state.vloaitb_id || this.state.vloaitb_id == 0) {
        this.$toast.warning('Bạn chưa chọn phiếu')
        return
      }
      if (this.state.vloaitb_id == LOAIHINH_THUEBAO.IVAN) {
        let data = {
          hdtb_id: this.state.hdtb_id,
          loaitb_id: this.state.vloaitb_id,
          kieuld_id: this.state.kieuld_id,
          readOnly: false,
          isUpdate: true,
          ds_hdtb_sub: []
        }
        this.$refs.dialogHDTB_SUB_V3.openDialog(data)
        return
      } else if (this.state.vloaitb_id == LOAIHINH_THUEBAO.VNPT_TSA) {
        if (this.state.hdtb_id) {
          let rs = await lay_tt_hdtb_sub(this.state.hdtb_id)
          if (rs.length > 0) {
            this.dsHDTB_SUB = this.UpperCasePropertyList(rs)
          }
        }
        this.dialogComponent = () => import('../../setup/InstallNewSubs/Popups/frmNhapHDTBSUB_TSA')
        this.popupComponentName = 'frmNhapHDTBSUB_TSA'
        this.popupComponentData = { data: this.dsHDTB_SUB }
        this.popupComponentAttr = {
          disable: true
        }
        this.state.modalTitle = 'Thông tin TB'
        console.log('btnThongTinTBSub_Click', this.popupComponentData)
        //this.Popup("modalComponentsITVAS");
        this.$refs.generalDialog.show()
      } else {
        if (await this.PHAILAM(this.state.luong_id, 'RA_GUIYC')) {
          this.$refs.dialogHDTB_SUB_V2.openDialog({ type: 3, hdtb_id: this.state.hdtb_id, isReadonly: false })
        } else {
          this.$refs.dialogHDTB_SUB_V2.openDialog({ type: 3, hdtb_id: this.state.hdtb_id, isReadonly: true })
        }
      }
    },
    async KichHoatEnrollment() {
      let req = ''
      let kq = {}
      let loaihd_id = this.state.loaihd_id
      let kieuld_id = this.state.kieuld_id
      let hdtb_id = this.state.hdtb_id
      try {
        req = this.GetData(await api.tao_req_cntt(this.axios, { hdtb_id: hdtb_id, vhdtb_id: hdtb_id }))
        if (!req || req == '') {
          this.$toast.error('Không tạo được request. Hãy kiểm tra lại hàm tạo request')
          return
        }
        if (req == 'NOK') {
          this.$toast.error('Thuê bao đã kích hoạt thành công')
          return
        } else {
          let reqLog = {
            vhdtb_id: hdtb_id,
            verror: 'Tạo req',
            vreq: req,
            vres: '',
            vma_tb: '',
            vghichu: 'Tạo req đặt mới dịch vụ Enrollment'
          }
          let apiLoaiHD = ''
          //#region Đặt mới dịch vụ
          if ((loaihd_id == LoaiHopDong.DAT_MOI || loaihd_id == LoaiHopDong.GIAHAN) && (kieuld_id == KieuLapDat.DATMOI_CNTT_TOANTRINH || kieuld_id == KieuLapDat.CHUYENDUNG_CHINHTHUC_CNTT || kieuld_id == KieuLapDat.GIAHAN_TD_GOICUOC_CNTT)) {
            // dat moi dùng thử, chuyển dùng chính thức, gia hạn và đổi gói cước.
            reqLog.vghichu = 'Tạo req đặt mới dịch vụ Enrollment'
            apiLoaiHD = 'DatMoi'
          }
          //#region Thay đổi dịch vụ gia tăng
          if (loaihd_id == LoaiHopDong.GIAHAN && kieuld_id == KieuLapDat.TANG_SL_TAINGUYEN) {
            //MUADICHVUGT
            reqLog.vghichu = 'Tạo req mua dịch vụ gia tăng Enrollment'
            apiLoaiHD = 'DVGT'
          }
          if (loaihd_id == 7 && kieuld_id == 785) {
            //MUADICHVUGT
            reqLog.vghichu = 'Tạo req mua dịch vụ gia tăng Enrollment'
            apiLoaiHD = 'DVGT'
          }
          if (loaihd_id == LoaiHopDong.CHAMDUT_SD && kieuld_id == KieuLapDat.THANHLY_CNTT_TOANTRINH) {
            //CHAMDUTSUDUNG
            reqLog.vghichu = 'Tạo req chấm dứt sử dụng dịch vụ Enrollment'
            apiLoaiHD = 'THANHLY'
          }
          //#endregion
          if (apiLoaiHD == '') {
            this.$toast.error('Loại hợp đồng không hợp lệ: loaihd_id=' + loaihd_id)
            return
          }
          this.GetData(await api.sp_insert_log_cntt(this.axios, reqLog))
          kq = this.GetData(await api.VNPT_Enrollment(this.axios, apiLoaiHD, JSON.parse(req)))
          kq = JSON.parse(kq)
          let kqdk = kq
          if (kqdk.success == true) {
            this.GetData(await api.cap_nhat_status_hdtb(this.axios, { vhdtb_id: hdtb_id, vstatus: 6 }))
            this.$toast.success(kqdk.msg)
            reqLog = {
              vhdtb_id: hdtb_id,
              verror: 'Thành công',
              vreq: req,
              vres: JSON.stringify(kq),
              vma_tb: '',
              vghichu: 'Kích hoạt thành công dịch vụ Enrollment'
            }
            this.GetData(await api.sp_insert_log_cntt(this.axios, reqLog))
          } else {
            this.$toast.error(kqdk.msg)
          }
        }
      } catch (ex) {
        commonFn.showException(this, ex)
        let reqLog = {
          vhdtb_id: hdtb_id,
          verror: 'Thất bại',
          vreq: req,
          vres: JSON.stringify(kq),
          vma_tb: '',
          vghichu: 'Kích hoạt thất bại dịch vụ Enrollment'
        }
        this.GetData(await api.sp_insert_log_cntt(this.axios, reqLog))
        return
      }
    },
    async HienThiDiaChi(dr) {
      if (!dr) return
      let hdtb_id = dr['hdtb_id'] * 1
      var data = await commonFn.lay_ds_diachi_theo_hdtbid(this.axios, hdtb_id)
      if (data && data.length > 0) {
        this.diachiLD.DIACHI = data[0].diachi
        this.diachiLD.TINH_ID = data[0].tinhld_id
        this.diachiLD.QUAN_ID = data[0].quanld_id
        this.diachiLD.PHUONG_ID = data[0].phuongld_id
        this.diachiLD.PHO_ID = data[0].pho_id
        this.diachiLD.SO_NHA = data[0].sonha_ld
        this.diachiLD.DIACHI_ID = data[0].diachild_id
        this.diachiLD.KHU_ID = data[0].khuld_id
        this.diachiLD.DAC_DIEM_ID = data[0].dacdiemld_id
        this.diachiLD.Lng = data[0].kinhdo_ld
        this.diachiLD.Lat = data[0].vido_ld
      }
    },
    async Get_Issue_Info(id) {
      var req = {
        issueKeyOrID: id
      }
      let data = null
      await jira.Get_Issue_Info(this.axios, req).then((res) => {
        data = res.data.data
      })
      return data
    },
    emit_func(data) {
      this.data_emit_func = data
      console.log('Data - data_emit_func')
      console.log(this.data_emit_func)
      this.$refs.generalDialog.hide()
      this.tab_dvcntt.list.forEach((item) => {
        if (item.field_name.toLowerCase() == 'website') {
          item.field_value = data.id
        }
      })
      //this.$refs.generalDialog.close()
    },
    async attr_click(tag) {
      console.log('attr_click tag', tag)
      if (tag == 'THONGTIN_CT' && this.state.vloaitb_id == LOAIHINH_THUEBAO.IT_CARE) {
        //TODO:
        // frmHDTB_CNTT_SUB frm_sub = new frmHDTB_CNTT_SUB();
        // frm_sub.vloaitb_id = vloaitb_id;
        // frm_sub.vhdtb_id = hdtb_id;
        // frm_sub.vappend_name = repositoryItemButtonEdit.Properties.Tag;
        // frm_sub.ShowDialog();
      }
      if (tag == 'MA_PHU' && this.state.vloaitb_id == 358 /*voice brandname*/) {
        this.$refs.frmNhapDSThanhVien.hdtb_id = this.state.hdtb_id
        this.$refs.frmNhapDSThanhVien._ds_hdtbct = this.dsHDTB_CT
        this.$refs.frmNhapDSThanhVien.ShowDialog()
      }
      if (tag == 'DONGIA' || tag == 'DINHMUC_SMS' || tag == 'DOITAC' || tag == 'DINHMUC_LL' || tag == 'DINHTUYEN') {
        if (this.state.hdtb_id != 0) {
          //TODO
          // frmHDTB_CNTT_SUB frm_sub = new frmHDTB_CNTT_SUB();
          // frm_sub.xem = 1;
          // frm_sub.vloaitb_id = vloaitb_id;
          // frm_sub.vhdtb_id = hdtb_id;
          // frm_sub.vappend_name = repositoryItemButtonEdit.Properties.Tag;
          // frm_sub.ShowDialog();
        }
      }
      if (this.state.hdtb_id != 0 && this.state.vloaitb_id == 225 && tag == 'WEBSITE') {
        if (!this.state.phieu_id || this.state.phieu_id == 0) {
          this.$toast.error('Hãy chọn phiếu trước!')
          return
        }
        this.dialogComponent = () => import('@/modules/search/subscriber/SearchVNEduData/popup/themeListEdu/themeListEdu.vue')
        this.popupComponentName = 'frmLayMauGiaoDien'
        this.popupComponentData = {
          matb: this.state.selectedPhieu.ma_tb,
          magd: this.state.selectedPhieu.ma_gd,
          ispopup: true,
          vhdtb_id: this.state.hdtb_id
        }
        this.popupComponentAttr = {
          matb: this.state.selectedPhieu.ma_tb,
          magd: this.state.selectedPhieu.ma_gd,
          ispopup: true,
          vhdtb_id: this.state.hdtb_id
        }
        this.state.modalTitle = 'Chọn mẫu giao diện'
        this.$refs.generalDialog.show()
      }
      if (tag == 'CT_DIEMCAU') {
        if (this.state.hdtb_id != 0) {
          // TODO:
          // frmDanhSach_DiemCau frm_sub = new frmDanhSach_DiemCau();
          // frm_sub.xem = 1;
          // frm_sub.vhdtb_id = hdtb_id;
          // frm_sub.vhdkh_id = hdkh_id;
          // frm_sub.vloaitb_id = vloaitb_id;
          // frm_sub.ShowDialog();
        }
      } else if (tag == 'THONGTIN_CT') {
        if (this.state.hdtb_id != 0 && this.state.vloaitb_id == 376) {
          // TODO:
          if (!this.state.phieu_id || this.state.phieu_id == 0) {
            this.$toast.error('Hãy chọn phiếu trước!')
            return
          }
          //this.popupComponent = () => import("../../../search/subscriber/SearchEProfile");
          this.dialogComponent = () => import('../..//setup/InstallNewSubs/Popups/frmNhanCanhBaoHeThong')
          this.popupComponentName = 'frmNhanCanhBaoHeThong'
          this.popupComponentData = {
            matb: this.state.selectedPhieu.ma_tb,
            magd: this.state.selectedPhieu.ma_gd,
            ispopup: true,
            vhdtb_id: this.state.hdtb_id,
            thuebao_id: this.state.thuebao_id
          }
          this.popupComponentAttr = {
            matb: this.state.selectedPhieu.ma_tb,
            magd: this.state.selectedPhieu.ma_gd,
            ispopup: true,
            vhdtb_id: this.state.hdtb_id,
            thuebao_id: this.state.thuebao_id
          }
          this.state.modalTitle = 'Số thuê bao nhận cảnh báo từ hệ thống'
          console.log('tsbtnFile_Upload_Click', this.popupComponentData)
          //this.Popup("modalComponentsITVAS");
          this.$refs.generalDialog.show()
        }
      } else if (tag == 'THONGTIN_XT') {
        if (this.state.hdtb_id != 0 && this.state.vloaitb_id == 376) {
          // TODO:
          if (!this.state.phieu_id || this.state.phieu_id == 0) {
            this.$toast.error('Hãy chọn phiếu trước!')
            return
          }
          //this.popupComponent = () => import("../../../search/subscriber/SearchEProfile");
          this.dialogComponent = () => import('../..//setup/InstallNewSubs/Popups/frmSoThueBaoM2M')
          this.popupComponentName = 'frmNhanCanhBaoHeThong'
          this.popupComponentData = {
            matb: this.state.selectedPhieu.ma_tb,
            magd: this.state.selectedPhieu.ma_gd,
            ispopup: true,
            vhdtb_id: this.state.hdtb_id
          }
          this.popupComponentAttr = {
            matb: this.state.selectedPhieu.ma_tb,
            magd: this.state.selectedPhieu.ma_gd,
            ispopup: true,
            vhdtb_id: this.state.hdtb_id
          }
          this.state.modalTitle = 'Số thuê bao M2M'
          console.log('tsbtnFile_Upload_Click', this.popupComponentData)
          //this.Popup("modalComponentsITVAS");
          this.$refs.generalDialog.show()
        }
      }

      else if (tag == 'MA_PHU') {
        if (this.state.hdtb_id != 0 && this.state.vloaitb_id == 404) {
          // TODO:
          if (!this.state.phieu_id || this.state.phieu_id == 0) {
            this.$toast.error('Hãy chọn phiếu trước!')
            return
          }
          //this.popupComponent = () => import("../../../search/subscriber/SearchEProfile");
          this.dialogComponent = () => import('@/modules/admin/category/frmGiaoViecVIP/components/frmGanSimVaoKenh/frmGanSimVAoKenhDisable.vue')
          this.popupComponentName = 'frmGanSimVAoKenhDisable'
          this.popupComponentData = {
            matb: this.state.selectedPhieu.ma_tb,
            magd: this.state.selectedPhieu.ma_gd,
            ispopup: true,
            hdtb_id: this.state.hdtb_id,
            tinh_id: this.tt_nd.phanvung_id
          }
          this.popupComponentAttr = {
            matb: this.state.selectedPhieu.ma_tb,
            magd: this.state.selectedPhieu.ma_gd,
            ispopup: true,
            hdtb_id: this.state.hdtb_id,
            tinh_id: this.tt_nd.phanvung_id
          }
          this.state.modalTitle = 'Danh sách gán sim vào kênh'
          console.log('tsbtnFile_Upload_Click', this.popupComponentData)
          //this.Popup("modalComponentsITVAS");
          this.$refs.generalDialog.show()
        }
      }

      let p_ds_para = {
        LOAITB_ID: this.state.vloaitb_id,
        FIELD_NAME: tag,
        KIEULD_ID: this.state.kieuld_id,
        TOCDO_ID: 0
      }
      let data_check = await api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
        p_nghiepvu: 'SUDUNG_DYNAMIC_HD_THUEBAO_CT',
        p_ds_para: JSON.stringify(p_ds_para)
      })

      if(data_check.data.data[0].ketqua == 'ok') {
        console.log('use frmDynamic_Hd_thuebao_ct')
        let vfield_detail = {
          vloaitb_id: this.state.vloaitb_id,
          vopt_list: "00000",
          vfield_name_cha: tag
        }
        this.popupComponentData = {
          vloaitb_id: this.state.vloaitb_id,
          hdtb_id: this.state.hdtb_id,
          vappend_name: tag,
          enableEdit: false,
          vfield_detail: vfield_detail
        }

        this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/frmDynamic_Hd_thuebao_ct/frmDynamic_Hd_thuebao_ct.vue')
        this.popupComponentName = 'frmDynamic_Hd_thuebao_ct'
        this.$refs.popupComponents.show()
      }
    },
    async btnPhieuTC_Click() {
      try {
        var grid = this.$refs.gv_DanhSach
        var seletedRows = grid.getSelectedRecords()
        if (seletedRows.length <= 0) {
          this.$toast.error('Bạn chưa chọn phiếu! ')
          return false
        }
        let vhdkhid = commonFn.MAP_ID(this.axios, 'hdkh_id', 'css.hd_thuebao', 'where hdtb_id=' + this.state.hdtb_id)
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
        this.modelIn.ma_gd = this.state.selectedPhieu.ma_gd
        this.modelIn.n_hdkh_id = nhdkhid.join(',')
        this.modelIn.n_hdtb_id = nhdtbid.join(',')
        this.modelIn.nvth_id = -1
        this.modelIn.huonggiao_id = this.state.huonggiao_id
        this.modelIn.tentram_tb = ''
        console.log(this.modelIn)
        this.$refs.popupInBB.showModal()
      } catch (ex) {
        commonFn.showException(this, ex)
      }
    },
    async btnBienBanNT_Click() {
      this.$toast.warning('Không thực hiện in phiếu ở chức năng này')
    },
    tsbtnGuiMailKH_Click() {
      if (!this.state.hdtb_id || this.state.hdtb_id == 0) {
        this.$toast.warning('Bạn chưa chọn phiếu')
        return
      }
      this.$bvModal
        .msgBoxConfirm('Bạn có chắc là sẽ gửi lại email kích hoạt cho khách hàng?', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
          centered: true
        })
        .then(async (v) => {
          if (!v) {
            this.hideLoading()
            return
          }
          await this.GuiMailKH()
          this.hideLoading()
        })
    },
    async GuiMailKH() {
      try {
        //this.loading(true)
        let data = this.GetData(await api.lay_dulieu_tao_thucthe_ca(this.axios, { params: { hdtb_id: this.state.hdtb_id } }))
        if (!data || data.length <= 0) {
          this.$root.$toast.warning('Không tìm thấy dữ liệu thực thể CA!')
          return
        }
        data = commonFn.LowerCasePropertyList(data)
        let prefix = ''
        if (data[0].prefix) prefix = data[0].prefix
        if (data[0].smartca_suffix) prefix = data[0].smartca_suffix
        let username = data[0].username ? data[0].username : ''
        let resData = this.GetData(await api.GuiLaiSMS(this.axios, { username: username + '_' + prefix }))
        this.$root.$toast.success('Yêu cầu gửi lại Email thành công')
      } catch (ex) {
        //this.loading(false)
        commonFn.showException(this, ex)
      } finally {
        this.hideLoading()
      }
    },
    async btnBanDoLD_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0) {
        this.$toast.warning('Bạn chưa chọn phiếu')
        return
      }
      let data = {}
      data.lng = this.diachiLD.Lng
      data.lat = this.diachiLD.Lat
      if (Number(data.lat) * Number(data.lng) > 0) {
        this.$refs.frmBanDoTB.map_position = data
        this.$refs.frmBanDoTB.map_info = {
          dia_chi: this.diachiLD.DIACHI,
          title: this.state.ma_tb
        }
        this.$refs.frmBanDoTB.mode = 2
        this.$refs.frmBanDoTB.show()
      } else {
        this.$refs.frmBanDoTB.map_position = data
        this.$refs.frmBanDoTB.map_info = {
          dia_chi: this.diachiLD.DIACHI,
          title: 'Vị trí tương đối theo địa chỉ: ' + this.state.ma_tb
        }
        this.$refs.frmBanDoTB.mode = 2
        this.$refs.frmBanDoTB.show()
      }
    },
    async XacNhanToaDo(value) {
      try {
        if (!this.state.hdtb_id) {
          return false
        }
        this.diachiLD.Lat = value.lat
        this.diachiLD.Lng = value.lng
        //this.loading(true)
        let kq = await commonFn.CapnhatToaDoHDTB(this.axios, this.diachiLD.Lat, this.diachiLD.Lng, this.state.hdtb_id, 1)
        if (kq !== '1') {
          this.$toast.error('Không cập nhật được tọa độ: ' + kq)
        }
      } catch (error) {
        commonFn.showException(this, ex)
      }
      //this.loading(false)
    },
    async dlgClose(e) {
      console.log('dlgClose', e)
    },
    GetData: function(response) {
      if (response.data.errorCode && response.data.errorCode != '' && response.data.errorCode != '0' && response.data.faultString && response.data.faultString != '') {
        if ((this.state.vloaitb_id * 1 == 122 || this.state.vloaitb_id * 1 == 2116 || this.state.vloaitb_id * 1 == 319) && response.data.faultCode == 'ERR:2.36') {
          throw response.data.faultString + '. Hãy nhấn nút "Đồng bộ CMS" để đồng bộ lại ID khách hàng'
        }
        throw response.data.faultString
      }
      if (response.data.error === 200 || response.data.error === '200' || response.data.errorCode === 0) {
        if (response.data.error_code === 'BSS-00000000') return response.data.data
        else if (response.data.errorCode === 0) return response.data.data
        else {
          commonFn.showException(this, response.data)
          return response.data.data
        }
      } else {
        console.log(response.data.error_code)
        commonFn.showException(this, response.data)
      }
      return null
    },
    async giaoviec_success() {
      await this.HienThiDSNV(await fn.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.axios, this.state.phieu_id, this.tt_nd.nhanvien_id, 2))
      console.log('giaoviec_success')
    },
    hideLoading() {
      if (this.state && this.state.isPageLoad == false) {
        //this.loading(false)
      }
    },
    hideDropdownMenu(e) {
      if (e.target !== this.$refs.mainWrap) {
        this.isDisplayMenu = false
      }
      if (e.target !== this.$refs.xuatphieu) {
        this.xuatPhieuShow = false
      }
    },
    overlayClick() {
      this.$refs.generalDialog.hide()
    },
    ChuanHoaTen(ten) {
      if (!ten) return ''
      var ten_new = ''
      var str_con = ten.trim()
      var str_xl = ''
      var k = 0
      var found = true
      if (str_con.length < 1) return
      str_con = str_con.replaceAll(/\s+/g, ' ')
      try {
        while (found) {
          k = str_con.indexOf(' ')
          if (k > 0) {
            str_xl = str_con.substring(0, k)
            str_con = str_con.substring(k + 1)
            str_xl = this.ChuyenChuHoa(str_xl)
            if (ten_new != '') ten_new += ' ' + str_xl
            else ten_new = str_xl
            found = true
          } else found = false
        }
        if (str_con != '') {
          str_xl = str_con
          str_xl = this.ChuyenChuHoa(str_xl)
          if (ten_new != '') ten_new = ten_new + ' ' + str_xl
          else ten_new = str_xl
        }
        return ten_new
      } catch (e) {
        return ten
      }
    },
    async KichHoatCAHangLoat() {
      let self = this
      if (this.state.selectedPhieu.loaitb_id == 288) {
        this.dialogComponent = () => import('./popups/popupDuyetHangLoatV2/frmDuyetHangLoatV2.vue')
        console.log('test')
        await this.dialogComponent().then((x) => {
          console.log(x)
        })
        this.$nextTick(() => {
          this.setDSHDTB(self.datagrid.ds_hdtb.list)
          this.setRAKichHoat(1)
          this.$refs.generalDialog.show()
        })
        return
      }
      try {
        this.loading(true)
        if (self.state.tthd_id == 11) {
          // ra kich hoat
          if (self.state.loaihd_id == 1 || self.state.loaihd_id == 41) {
            // loai hop dong la dat moi
            //this.loading(true)
            let ketqua = await smartca.RAHangLoat(this, self.state.selectedPhieu.ma_gd)
            let ketqua_ra = []
            // console.log(ketqua.data);
            if (ketqua.data.error == '200') {
              let dulieu_ca_magd = ketqua.data.data
              // console.log(dulieu_ca_magd);
              for (const item of dulieu_ca_magd) {
                try {
                  let kq_ra = ''
                  if (self.state.loaihd_id == 1) {
                    kq_ra = await ca.Tao_Moi_CA(this, {
                      kieuld_id: dulieu_ca_magd.kieuld_id,
                      hdtb_id: item.hdtb_id,
                      loaihd_id: self.state.loaihd_id,
                      thuebao_id: 0
                    })
                  }
                  if (self.state.loaihd_id == 41) {
                    kq_ra = await ca.GiaHanCA(this, {
                      kieuld_id: dulieu_ca_magd.kieuld_id,
                      hdtb_id: item.hdtb_id,
                      loaihd_id: self.state.loaihd_id,
                      thuebao_id: 0
                    })
                  }
                  // show loi
                  //  console.log(kq_ra);
                  ketqua_ra = [
                    ...ketqua_ra,
                    {
                      hdtb_id: item.hdtb_id,
                      ma_tb: item.ma_tb,
                      ten_tb: item.ten_tb,
                      so_gt: item.so_gt,
                      chucdanh: item.chucdanh,
                      email: item.email,
                      so_dt: item.so_dt,
                      message: kq_ra
                    }
                  ]
                } catch (e) {
                  ketqua_ra = [
                    ...ketqua_ra,
                    {
                      hdtb_id: item.hdtb_id,
                      ma_tb: item.ma_tb,
                      ten_tb: item.ten_tb,
                      so_gt: item.so_gt,
                      chucdanh: item.chucdanh,
                      email: item.email,
                      so_dt: item.so_dt,
                      message: JSON.stringify(e)
                    }
                  ]
                }
              }
            }
            let text = `<style>
                      table,
                      th,
                      td {
                          border: 1 px solid;
                          padding: 10 px;
                      } </style>`
            text += "<table style='border-collapse: collapse; border: solid 1px black;'>"
            text += '<tr><th>ID</th><th>Mã thuê bao</th><th>Tên thuê bao</th><th>Số giấy tờ</th><th>Chức danh</th><th>Email</th><th>Số ĐT</th><th>Kết quả</th></tr>'
            for (let x of ketqua_ra) {
              text += '<tr>'
              var keys = Object.keys(x)
              for (let i of keys) {
                text += '<td>' + x[i] + '</td>'
              }
              text += '</tr>'
            }
            text += '</table>'
            // console.log(text)
            //this.loading(false)
            fileDownload(text, `RA_${self.state.selectedPhieu.ma_gd}.html`, 'text/html')
          }
        }
      } catch (ex) {
        this.loading(false)
        alert('Lỗi - ' + ex)
      } finally {
        this.loading(false)
      }
    },
    async DuyetCAHangLoat() {
      let self = this
      if (this.state.selectedPhieu.loaitb_id == 288) {
        this.dialogComponent = () => import('./popups/popupDuyetHangLoatV2/frmDuyetHangLoatV2.vue')
        await this.dialogComponent().then((x) => {
          console.log(x)
        })
        this.$nextTick(() => {
          this.setDSHDTB(self.datagrid.ds_hdtb.list)
          this.setRAKichHoat(0)
          this.$refs.generalDialog.show()
        })
        return
      }
      try {
        if (this.state.selectedPhieu.loaitb_id != 116 && this.state.selectedPhieu.loaitb_id != 288) {
          this.$toast.error('Chức năng chỉ giành cho CA/SmartcA')
          return
        }
        let isConfirm = await this.$bvModal.msgBoxConfirm('Bạn muốn duyệt hay hủy duyệt hàng loạt', {
          title: '',
          size: 'sm',
          okTitle: 'Duyệt',
          cancelTitle: 'Hủy duyệt',
          centered: true
        })
        if (isConfirm) {
          if (self.state.tthd_id == 9) {
            let ketqua = await smartca.DuyetHangLoat(this, self.state.selectedPhieu.ma_gd)
            //this.loading(true)
            if (ketqua.data.error == '200') {
              let ketqua_ra = []
              let dulieu_ca_magd = ketqua.data.data
              // console.log(dulieu_ca_magd);
              for (const item of dulieu_ca_magd) {
                try {
                  let kq_ra = await smartca.DuyetCA(this, {
                    kieuld_id: item.kieuld_id,
                    hdtb_id: item.hdtb_id,
                    loaihd_id: self.state.loaihd_id,
                    thuebao_id: 0
                  })
                  // show loi
                  //  console.log(kq_ra);
                  ketqua_ra = [
                    ...ketqua_ra,
                    {
                      hdtb_id: item.hdtb_id,
                      ma_tb: item.ma_tb,
                      ten_tb: item.ten_tb,
                      so_gt: item.so_gt,
                      chucdanh: item.chucdanh,
                      email: item.email,
                      so_dt: item.so_dt,
                      message: kq_ra
                    }
                  ]
                } catch (e) {
                  console.log(e)
                  ketqua_ra = [
                    ...ketqua_ra,
                    {
                      hdtb_id: item.hdtb_id,
                      ma_tb: item.ma_tb,
                      ten_tb: item.ten_tb,
                      so_gt: item.so_gt,
                      chucdanh: item.chucdanh,
                      email: item.email,
                      so_dt: item.so_dt,
                      message: JSON.stringify(e)
                    }
                  ]
                }
              }
              //this.loading(false)
              let text = `<style>
                          table,
                          th,
                          td {
                              border: 1 px solid;
                              padding: 10 px;
                          } </style>`
              text += "<table style='border-collapse: collapse; border: solid 1px black;'>"
              text += '<tr><th>ID</th><th>Mã thuê bao</th><th>Tên thuê bao</th><th>Số giấy tờ</th><th>Chức danh</th><th>Email</th><th>Số ĐT</th><th>Kết quả</th></tr>'
              for (let x of ketqua_ra) {
                text += '<tr>'
                var keys = Object.keys(x)
                for (let i of keys) {
                  text += '<td>' + x[i] + '</td>'
                }
                text += '</tr>'
              }
              text += '</table>'
              // console.log(text)
              // //this.loading(false);
              fileDownload(text, `CA_${self.state.selectedPhieu.ma_gd}.html`, 'text/html')
            }
          }
        } else {
          let ketqua = await smartca.DuyetHangLoat(this, self.state.selectedPhieu.ma_gd)
          //this.loading(true)
          if (ketqua.data.error == '200') {
            let ketqua_ra = []
            let dulieu_ca_magd = ketqua.data.data
            // console.log(dulieu_ca_magd);
            for (const item of dulieu_ca_magd) {
              try {
                let kq_ra = await smartca.HuyDuyetCA(this, {
                  kieuld_id: item.kieuld_id,
                  hdtb_id: item.hdtb_id,
                  loaihd_id: self.state.loaihd_id,
                  thuebao_id: 0
                })
                // show loi
                //  console.log(kq_ra);
                ketqua_ra = [
                  ...ketqua_ra,
                  {
                    hdtb_id: item.hdtb_id,
                    ma_tb: item.ma_tb,
                    ten_tb: item.ten_tb,
                    so_gt: item.so_gt,
                    chucdanh: item.chucdanh,
                    email: item.email,
                    so_dt: item.so_dt,
                    message: kq_ra
                  }
                ]
              } catch (e) {
                console.log(e)
                ketqua_ra = [
                  ...ketqua_ra,
                  {
                    hdtb_id: item.hdtb_id,
                    ma_tb: item.ma_tb,
                    ten_tb: item.ten_tb,
                    so_gt: item.so_gt,
                    chucdanh: item.chucdanh,
                    email: item.email,
                    so_dt: item.so_dt,
                    message: JSON.stringify(e)
                  }
                ]
              }
            }
            smartca.resetToken()
            //this.loading(false)
            let text = `<style>
                      table,
                      th,
                      td {
                          border: 1 px solid;
                          padding: 10 px;
                      } </style>`
            text += "<table style='border-collapse: collapse; border: solid 1px black;'>"
            text += '<tr><th>ID</th><th>Mã thuê bao</th><th>Tên thuê bao</th><th>Số giấy tờ</th><th>Chức danh</th><th>Email</th><th>Số ĐT</th><th>Kết quả</th></tr>'
            for (let x of ketqua_ra) {
              text += '<tr>'
              var keys = Object.keys(x)
              for (let i of keys) {
                text += '<td>' + x[i] + '</td>'
              }
              text += '</tr>'
            }
            text += '</table>'
            // console.log(text)
            // //this.loading(false);
            fileDownload(text, `CA_${self.state.selectedPhieu.ma_gd}.html`, 'text/html')
          }
        }
      } catch (ex) {
        this.loading(false)
        alert(ex)
      } finally {
        this.loading(false)
      }
    },
    async GiaoPhieuView_OnClick() {
      this.giaoPhieuViewDialog.isVisiable = true
      this.$refs.GiaoPhieuView.show()
    },
    async GiaoPhieuView_OnClose() {
      this.giaoPhieuViewDialog.isVisiable = false
    },
    btnTestGuiMail() {
      let data = {
        hdtb_id: 9110701
      }
      this.$refs.frmBRNGuiMail.openDialog(data)
    },
    //end methods
    async sync_iAlertStatus() {
      if (this.state.vloaitb_id == 376) {
        let result = await this.Check_TrangThaiLapDat(this.state.vloaitb_id, this.state.hdtb_id)
        if (!result) return
      }
    },
    async Check_TrangThaiLapDat(loaitb_id, hdtb_id) {
      if (loaitb_id == 376 && this.state.kieuld_id != 13224) {
        if (this.iAlert_value.trang_thai_load_hd == true || this.listbox.ds_loaihd.value != 1) return true
        let response = await this.axios.post('/web-thicong/hoinghi_truyenhinh/fn_an_hoanthienhoso_dichvu_cntt_v2', {
          vphanvung_id: this.tt_nd.phanvung_id,
          vhdtb_id: hdtb_id
        })
        // let result = response.data.data[0]
        // if (response.data.error_code === 'BSS-00000000') {
        //   this.iAlert_value.trang_thai_lap_dat = result.trang_thai
        //   if (this.iAlert_value.trang_thai_lap_dat == 0) {
        //     this.$toast.info('BACKEND RESPONSE: ' + result.trang_thai_text + '. Chưa thể hoàn thiện hồ sơ!')
        //     return false
        //   } else {
        //     if (this.iAlert_value.trang_thai_load_hd == false) {
        //       this.$toast.success('BACKEND RESPONSE: ' + result.trang_thai_text + '. Hãy kiêm tra lại thông tin số serial và hoàn thiện hồ sơ!!')
        //       await this.HienThiTTHopDongTB(this.datagrid.ds_hdtb.list)
        //       this.iAlert_value.trang_thai_load_hd = true
        //       return false
        //     } else {
        //       return true
        //     }
        //   }
        // } else {
        //   if(response.data.message != 'Success') {
        //     this.$toast.error('BACKEND RESPONSE: ' + response.data.message + '. Chưa thể hoàn thiện hồ sơ!')
        //   }
        //   else {
        //     this.$toast.error('BACKEND RESPONSE: ' + result.message + '. Chưa thể hoàn thiện hồ sơ!')
        //   }
        // }

        if(response.data.error_code === 'BSS-00000000'
          && response.data.message == 'Success' && response.data.data == 'ok'){
          this.$toast.success('Thiết bị đã được lắp đặt. Hãy kiêm tra lại thông tin số serial và hoàn thiện hồ sơ!!')
          return true
        }
        else {
          this.$toast.error('BACKEND RESPONSE: ' + response.data.data + '. Chưa thể hoàn thiện hồ sơ!')
          return false
        }
        return false
      }
      return true
    },
    async confirm(message, title) {
      try {
        let result = await this.$confirm(message, title, {
          confirmButtonText: 'Đồng ý',
          cancelButtonText: 'Hủy'
        })
        return 1
      } catch (e) {
        return 0
      }
    },
    async tsbtnDuyetCA_Click() {
      this.popupComponent = () => import('./popups/frmDuyetCA/DuyetCA.vue')
      this.popupComponentName = 'frmDuyetCA'
      this.popupComponentData = {
        matb: this.state.selectedPhieu.ma_tb,
        magd: this.state.selectedPhieu.ma_gd,
        ispopup: true,
        vhdtb_id: this.state.hdtb_id
      }
      this.popupComponentAttr = {
        matb: this.state.selectedPhieu.ma_tb,
        magd: this.state.selectedPhieu.ma_gd,
        ispopup: true,
        vhdtb_id: this.state.hdtb_id
      }
      this.state.modalTitle = 'Duyệt CA/SmartCA'
      console.log('tsbtnFile_Upload_Click', this.popupComponentData)
      this.Popup('modalComponentsITVAS')
      // this.$refs.generalDialog.show()
    },
    UpperCasePropertyList(obj) {
      return obj.map(function(item) {
        for (let key in item) {
          let upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    showpp_LoginCommon() {
      this.ppLoginCommon.isShow = true
      this.$refs.modalLoginCommon.show()
    },
    close_modalLoginCommon() {
      this.$refs.modalLoginCommon.hide()
      this.ppLoginCommon.isShow = false
    },
    async btnAutoUpdateAddress() {
      try {
        this.loading(true)
        let rs = await api.api_capnhat_tinh_quan_phuong_id_hopdong(this.axios, {
          hdtb_id: this.state.hdtb_id
        })
        let data = rs.data.data
        if (data.startsWith('ok')) {
          // await this.lay_ds_tt_danhba()
          // await this.focusMyElement()
          return this.$toast.success('Cập nhật thành công')
        } else {
          return this.$toast.error('Cập nhật thất bại, hãy cập nhật thủ công!')
        }
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>

<style>
.custom-modal-height .modal-content {
  height: 75vh; /* Chiều cao bằng 75% màn hình */
}

.custom-modal-height .modal-body {
  overflow-y: auto; /* Kích hoạt thanh cuộn khi cần thiết */
}
</style>
