<template src="./CompleteProfileBuySamsungTV.template.html"></template>
<style scoped src="./CompleteProfileBuySamsungTV.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import api from './CompleteProfileBuySamsungTVApi.js'
import DatePicker from 'vue2-datepicker'

import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
// const searchContractModal = () => import('@/modules/contract/setup/ChangeInstallUnit/popup/SearchContract/SearchContractModal.vue')
import modalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
//const modalChonDiaChi = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi')
import popupTimKiemNhanVienThuCuoc from '@/modules/contract/setup/InstallNewSubs/Popups/PopupTimKiemNhanVienThuCuoc'
import updatePostPaid from '@/modules/contract/setup/UpdatePostPaid/UpdatePostPaid.vue'
//const popupTimKiemNhanVienThuCuoc = () => import('@/modules/contract/setup/InstallNewSubs/Popups/PopupTimKiemNhanVienThuCuoc')
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import ChiTietKhuyenMai from './components/ChiTietKhuyenMai'

import { required } from 'vuelidate/lib/validators'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import { DichVuVienThong, LOAI_DV, LoaiHopDong, LoaiHinhTB, HinhThucThanhToan } from '@/const/enums/index.js'
import { KIEUGOI_CN_HOPDONG } from './enums/index.js'

export default {
  components: {
    breadcrumb,
    moment,
    DatePicker,
    api,
    SearchContractModal,
    modalChonDiaChi,
    popupTimKiemNhanVienThuCuoc,
    BssErrorMarker,
    BssRequiredMarker,
    ChiTietKhuyenMai,
    updatePostPaid
  },
  name: 'CompleteProfileBuySamsungTV',
  data() {
    return {
      header: {
        title: 'Hoàn thiện hồ sơ lắp mới',
        list: [
          { name: 'Hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: '[Lập hợp đồng]: Chuyển quyền',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      nguoigt_id: 0,
      ctv_id: 0,
      hdtt_id: 0,
      thanhtoan_id: 0,
      tinhtt_id: 0,
      quantt_id: 0,
      phuongtt_id: 0,
      phott_id: 0,
      aptt_id: 0,
      sonhatt: '',
      tinhbc_id: 0,
      quanbc_id: 0,
      phuongbc_id: 0,
      phobc_id: 0,
      apbc_id: 0,
      khubc_id: 0,
      dacdiembc_id: 0,
      sonhabc: 0,
      tinhtb_id: 0,
      quantb_id: 0,
      phuongtb_id: 0,
      photb_id: 0,
      aptb_id: 0,
      sonhatb: '',
      pnlTBRight: true,
      DVVT: DichVuVienThong,
      labelFunctionTitle: 'Hoàn thiện hồ sơ lắp mới',
      labelFunctionTitleLower: 'Hoàn thiện hồ sơ lắp mới',
      tsbtnNhapMoi: false,
      tsbtnGhiLai: false,
      tsbtnThongTinLH: false,
      tsbtnHuyBo: false,
      btnChuyenDB: true,
      tsbtnGiaoPhieu: false,
      tsbtnSuaDacTinh: false,
      tsbtnThemTT: false,
      btnDongBoCCBS: true,
      tsbtnXoaTT: false,
      tsbtnHen: false,
      btnChonNe: false,
      btnChonMaTB: false,
      btnChonPort: false,
      tsbtnNhapMoi_Enabled: true,
      tsbtnGhiLai_Enabled: true,
      tsbtnChuyenDB_Enabled: true,
      tsbtnGiaoPhieu_Enabled: true,
      tsbtnSuaDacTinh_Enabled: true,
      tsbtnHuyBo_Enabled: true,
      tsbtnThemTT_Enabled: true,
      tsbtnXoaTT_Enabled: true,
      // tsbtnHuyBo_Enabled: true,
      // tsbtnNhapMoi_Enabled: true,
      tss0: false,
      tss1: false,
      tss2: false,
      watcherActive: false,
      Loading: false,
      dateFormat: 'DD/MM/YYYY',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'date',
      typeFormatMonth: 'month',
      hdkh_id: '',
      thuebao_id: '',
      httt_id: '',
      hdtb_id: '',
      trangbi_id: 0,
      port_id: 0,
      port_id_ht: 0,
      GiayToKH: [],
      GiayToKHNeo: [],
      QuocTich: [],
      DanToc: [],
      LoaiKH: [],
      NganHangKHList: [],
      NganHangList: [],
      cboVeTinhCD_Enabled: true,
      txtLineTrang_CD_Enabled: true,
      txtNe_Enabled: true,
      cboVeTinhAdsl_Enabled: true,
      Tb_cboLoaihinhTB: '',

      DsThueBao: {
        headers: [
          {
            fieldName: 'MA_TB',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            allowSorting: false,
            width: 90,
            textAlign: 'left'
          },
          {
            fieldName: 'TEN_TB',
            headerText: 'Tên thuê bao',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: 'DIACHI_LD',
            headerText: 'Địa chỉ lắp đặt',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'NGAY_HT',
            headerText: 'Ngày bàn giao',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: [],
        selected: 0,
        checked: []
      },
      DsThanhToan: {
        headers: [
          {
            fieldName: 'STT',
            headerText: 'STT',
            allowFiltering: true,
            allowSorting: false,
            width: 30,
            textAlign: 'left'
          },
          {
            fieldName: 'MA_TT',
            headerText: 'Mã TT',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: 'DIACHI_TT',
            headerText: 'Địa chỉ thanh toán',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'DIACHI_CT',
            headerText: 'Địa chỉ chứng từ',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: [],
        selected: 0,
        checked: []
      },
      GPhoneSoDep: {
        headers: [
          {
            fieldName: 'TUTHANG',
            headerText: 'Từ tháng',
            allowFiltering: true,
            allowSorting: false,
            width: 90,
            textAlign: 'left'
          },
          {
            fieldName: 'DENTHANG',
            headerText: 'đến tháng ',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: 'TIEN_CK',
            headerText: 'Tiền cam kết',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: []
      },
      CoDinhSoDep: {
        headers: [
          {
            fieldName: 'TUTHANG',
            headerText: 'Từ tháng',
            allowFiltering: true,
            allowSorting: false,
            width: 90,
            textAlign: 'left'
          },
          {
            fieldName: 'DENTHANG',
            headerText: 'đến tháng ',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: 'TIEN_CK',
            headerText: 'Tiền cam kết',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: []
      },
      IMSSoDep: {
        headers: [
          {
            fieldName: 'STT',
            headerText: 'Từ tháng',
            allowFiltering: true,
            allowSorting: false,
            width: 30,
            textAlign: 'left'
          },
          {
            fieldName: 'MA_TT',
            headerText: 'đến tháng ',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: 'DIACHI_TT',
            headerText: 'Tiền cam kết',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: []
      },
      ThuocTinhCNTT: [],
      // nganhang: { list:[], value:0, valuekh:0, sotaikhoankh:'',sotaikhoan:'', ischecked:false,ischeckedkh:false,isnganhangnn:false, },
      nganhang: {
        TIEN_DM: 0,
        DV_QHNS: 0,
        MACHUONG: 0,
        MN_KT: 0,
        MA_NDKT: 0,
        MN_NSNN: 0
      },
      NganhNgheLoc: [],
      NganhNghe: [],
      kieuyc_list: [],
      kieuyc_list_full: [],
      KIEUYC: {
        list: [],
        text: 'ten_kieuld',
        id: 'kieuld_id'
      },
      DOITUONG: {
        list: [],
        text: 'TEN_DT',
        id: 'DOITUONG_ID'
      },
      DONVIQL_TT: {
        list: [],
        text: 'TEN_DV',
        id: 'DONVI_ID'
      },
      DONVIQL_LD: {
        list: [],
        text: 'TEN_DV',
        id: 'DONVI_ID'
      },
      DONVIQL_HOST: {
        list: [],
        text: 'TEN_DV',
        id: 'DONVI_ID'
      },
      VeTinhAdsl: {
        list: [],
        text: 'TEN_DV',
        id: 'DONVI_ID'
      },
      HINHTHUC_TT: {
        list: [],
        text: 'HINHTHUC_TT',
        id: 'HTTT_ID'
      },
      CboTT: {
        list: [],
        text: 'tentt',
        id: 'hdtt_id'
      },
      CHUQUAN: {
        list: [],
        text: 'TEN_TAT',
        id: 'CHUQUAN_ID'
      },
      TRANGBI: {
        list: [],
        text: 'TENTRANGBI',
        id: 'TRANGBI_ID'
      },
      LOAIHINH_TB: {
        list: [],
        data: [],
        text: 'LOAIHINH_TB',
        id: 'LOAITB_ID'
      },
      MUCCUOC: {
        list: [],
        text: 'TENMUCCUOC',
        id: 'MUCUOCTB_ID'
      },
      MUCCUOCTB: {
        list: [],
        text: 'TENMUCCUOC',
        id: 'MUCUOCTB_ID'
      },
      LOAI_TB: {
        list: [],
        text: 'TEN_TBI',
        id: 'THIETBIDC_ID'
      },
      COMBO_DVVT: {
        list: [],
        text: 'TEN_DVVT',
        id: 'DICHVUVT_ID'
      },
      BrasADSL: {
        list: [],
        text: 'tenbras',
        id: 'bras_id'
      },
      DsLam: {
        list: [],
        text: 'tendslam',
        id: 'dslam_id'
      },
      TramTB_ADSL: {
        list: [],
        text: 'TEN_DV',
        id: 'DONVI_ID'
      },
      GoiCuocAdsl: {
        list: [],
        text: 'thuonghieu',
        id: 'tocdo_id'
      },
      LOAI_KHL: {
        list: [],
        text: 'TEN_LKHL',
        id: 'KHLON_ID'
      },
      KhachHangLon: {
        list: [],
        visible: false,
        text: 'TEN_LKHL',
        id: 'KHLON_ID'
      },
      lvDvkhac_TT: {
        list: [],
        text: 'TEN_DVKHAC',
        id: 'DVKHAC_ID'
      },
      lvDvkhac_TB: {
        list: [],
        text: 'TEN_DVKHAC',
        id: 'DVKHAC_ID'
      },
      lvDvGT: {
        list: [],
        text: 'TEN_DVGT',
        id: 'DICHVUGT_ID'
      },
      TocDoKenhTSL: {
        list: [],
        text: 'tocdo',
        id: 'tocdo_id'
      },
      LoaiKenhTSL: {
        list: [],
        text: 'tocdo',
        id: 'tocdo_id'
      },
      ThoiHanTSL: {
        list: [],
        text: 'TENTHOIHAN',
        id: 'THOIHAN_ID'
      },
      ChuQuanTSL: {
        list: [],
        text: 'tocdo',
        id: 'tocdo_id'
      },
      TrangBiTSL: {
        list: [],
        text: 'tocdo',
        id: 'tocdo_id'
      },
      LoaiTBTSL: {
        list: [],
        text: 'TEN_TBI',
        id: 'THIETBIDC_ID'
      },
      HostCD: {
        list: [],
        text: 'TEN_DV',
        id: 'DONVI_ID'
      },
      VeTinhCD: {
        list: [],
        text: 'TEN_DV',
        id: 'DONVI_ID'
      },
      TramTB_CD: {
        list: [],
        text: 'TEN_DV',
        id: 'DONVI_ID'
      },
      VeTinhIMS: {
        list: [],
        text: 'TEN_DV',
        id: 'DONVI_ID'
      },
      TrangBiIMS: {
        list: [],
        text: 'TENTRANGBI',
        id: 'TRANGBI_ID'
      },
      VeTinhMgwan: {
        list: [],
        text: 'TEN_DV',
        id: 'DONVI_ID'
      },
      ThoiHanMEGAWAN: {
        list: [],
        text: 'TENTHOIHAN',
        id: 'THOIHAN_ID'
      },
      LoaiKenhMgwan: {
        list: [],
        text: 'LOAIKENH',
        id: 'LOAIKENH_ID'
      },
      TocDoMEGAWAN: {
        list: [],
        text: 'TOCDO',
        id: 'TOCDO_ID'
      },
      LoaiModemMgwan: {
        list: [],
        text: 'LOAI_MD',
        id: 'LOAIMD_ID'
      },
      LoaiTBiWan: {
        list: [],
        text: 'TEN_TBI',
        id: 'THIETBIDC_ID'
      },
      TrangBiMEGAWAN: {
        list: [],
        text: 'TENTRANGBI',
        id: 'TRANGBI_ID'
      },
      GoiCuocDD: {
        list: [],
        text: 'TENGOICUOC',
        id: 'GOICUOC_ID'
      },
      HT_KHL: 0,
      DONVIQL_HTTT: '',
      TUYENTHU_HTTT: '',
      trangthai_hd: '',
      tthd_id: 0,
      tuyenthu_id: 0,
      dichvuvt_id: 0,
      dsdichvuvt_id: '',
      loai_hd_id: 1,
      kieugoi_id: 0,
      hdtb_id_cha: 0,
      thuebao_id_cha: 0,
      khachhang_id: 0,
      doituong_id: 0,
      ma_tb: '',
      buoc_ht: 1,
      txtNguoiGTcheck: false,
      txtCTVcheck: false,
      txtITXN: '',
      txtNguoiGT: '',
      txtNguoiGTId: '',
      txtCTV: '',
      txtCTVId: '',
      Tag: '',
      TNC1_ID: 0,
      TNC2_ID: 0,
      TNC3_ID: 0,
      TNC4_ID: 0,
      nganhnghe_id: 0,
      Kh: {
        txtMaGD: '',
        txtMaHD: '',
        txtMaKH: '',
        txtTenKH: '',
        dtpNgayYeuCau: moment(new Date()).format(this.dateFormat),
        dtpNgayLapHD: '',
        txtDiaChiKH: '',
        cboGiayToKH: '',
        txtSoGT: '',
        txtNgayCap: '',
        txtNoiCap: '',
        txtNgayHetHan: '',
        txtNgaySN: '',
        cboQuocTich: '',
        cboNganhNgheLoc: '',
        cboDanToc: '',
        cboLoaiKH: '',
        txtPhanLoaiKH: '',
        gioiTinh: '',
        txtNguoiDD: '',
        txtChucDanhDD: '',
        txtDiaChiNDD: '',
        txtTelKH: '',
        txtFaxKH: '',
        txtHoKhau: '',
        txtNoiCapHK: '',
        txtNgayCapHK: '',
        cboNganHangKH_val: '',
        cboNganHangKH_check: false,
        cboNganHangKH_enable: true,
        // cboNganHangKH: {
        //   check: false,
        //   val: ''
        // },
        txtMaSTKH: '',
        txtSoTKKH: '',
        txtDacTrung: '',
        txtGhiChuKH: '',
        // Thanh toán
        txtMaTT: '',
        txtMaTBDD: '',
        txtTenTT: '',
        txtDiaChiTT: '',
        txtDiaChiCT: '',
        txtDiaChiBC: '',
        cboDonViQL: '',
        cboHTTT: '',
        txtMaNVTC: '',
        txtMaTuyen: '',
        txtSTK: '',
        txtChu_TK: '',
        cboNganHang_val: '',
        cboNganHang_check: false,
        cboNganHang_isnganhangnn: false,
        // cboNganHang: {
        //   check: false,
        //   val: '',
        //   isnganhangnn: false
        // },
        txtMST: '',
        txtGhiChuTT: '',
        dichVuKhacTT: ''
      },
      Tb_cboDichVuVT: 1,
      Tb_cboDaiVT: '',
      Tb_cboKieuLD: '',
      Tb: {
        cboDichVuVT: '',
        cboLoaihinhTB: '',
        cboKieuLD: '',
        cboMucCuocTB: '',
        cboTT: '',
        txtTenTB: '',
        txtNgaySNTB: '',
        txtDiaChiTB: '',
        txtDiaChiLD: '',
        cboDoituongTB: '',
        txtKhuVuc: '',
        cboDaiVT: '',
        txtGhiChuTB: '',
        txtMaTB: '',
        dtpNgayBG: moment(new Date()).format(this.dateFormat),
        dtpNgayBGCheck: false,

        txtMatKhauADSL: '',
        txtEmailAdsl: '',
        txtmaTN_ADSL: '',
        cboGoiCuocAdsl: '',
        cboMucCuoc: '',
        cboVeTinhAdsl: '',
        cboTramTB_ADSL: '',
        cboLoaiTB: '',
        txtDoiCapADSL: '',
        txtHopCapADSL: '',
        cboBrasADSL: '',
        txtCuLyADSL: '',
        cboDSLAM: '',
        txtIPADSL: '',
        txtSoMayTNAdsl: '',
        trangbi_id: 0,
        port_id: 0,
        port_id_ht: 0,
        txtmadoicap_adsl: '',
        txtSystem: {
          Enabled: true,
          Text: ''
        },
        txtRack: {
          Enabled: true,
          Text: ''
        },
        txtPort_mdf: '',
        txtSlotADSL: '',
        txtPort: '',
        txtShelf: {
          Enabled: true,
          Text: ''
        },
        txtVCIADSL: '',
        txtTTCuoc: '',
        txtVPIADSL: '',
        btnChonPort_Visible: false,
        // Co dinh,
        cboHost: '',
        cboVeTinhCD: '',
        txtNe: '',
        txtHopCap: '',
        txtDoiCap: '',
        txtCuLy: '',
        ne: '',
        chkVSVX_Checked: false,
        chkMienCuocTB_Checked: false,
        txtmaTN_CD: '',
        txtmadoicap_cd: '',
        txtLineTrang_CD: 0,
        cboTramTB_CD: 0,
        txtDoiCap: '',
        // IMS
        cboVeTinhIMS: '',
        cboTrangBiIMS: '',
        // Di dong
        txtSimDD: '',
        cboGoiCuocDD: '',
        txtTienDuTK: '',
        txtSoMayNTK: '',
        //MEGAWAN,METRONET
        txtHuongKetNoiMEGAWAN: '',
        txtLineTrang_Wan: '',
        cboVeTinhMgwan: '',
        cboThoiHanMEGAWAN: '',
        txtmadoicap_wan: '',
        txtmaTN_Wan: '',
        cboTocDo: '',
        cboLoaiModem: '',
        cboLoaiKenh: 0,
        cboLoaiTBiWan: '',
        cboThoiHanMEGAWAN: '',
        cboTrangBiMEGAWAN: '',
        txtWAN_IP: '',
        txtLan_IP: '',
        txtDSLAMWan: '',
        txtPortWan: '',
        txtTenTbi: '',
        txtVpiWan: '',
        txtVciWan: '',
        //TSL
        cboTocDoKenhTSL: '',
        txtLineTrang_TSL: '',
        cboLoaiKenhTSL: '',
        cboThoiHan: '',
        cboTrangBi: '',
        cboChuQuan: '',
        cboLoaiTBTSL: '',
        slot_cn: '',
        port_cn: '',
        timeslot_cn: '',
        mapping_cn: '',
        //DichVuVienThong.GPHONE
        txtSoSimGP: '',
        checkVSVXGP_Checked: false,
        txtCell1: '',
        txtCell2: '',
        txtToaDo: ''
      },
      formTitle: '',
      tsbtnGhiLai: {
        Enabled: true
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close': this.popupClosed,
        acceptChangeCTV: this.popupClosed,
        acceptChangeNGT: this.popupClosed,
        PopUpCongTacVien_Thoat: this.PopUpCongTacVien_Thoat,
        PopUpNguoiGioiThieu_Thoat: this.PopUpNguoiGioiThieu_Thoat
      },
      popupComponentName: '',
      tieunganh: {
        dsTNC1: [],
        dsTNC2: [],
        dsTNC3: [],
        dsTNC4: [],
        TNC1_ID: 0,
        TNC2_ID: 0,
        TNC3_ID: 0,
        TNC4_ID: 0,
        TNCMOTA: ''
      },
      diachikh_id: 0,
      diachitt_id: 0,
      diachibc_id: 0,
      diachitb_id: 0,
      diachild_id: 0,
      diachiKH: {
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
      diachiHK: {
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
      diachiNDD: {
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
      diachiTT: {
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
      diachiCT: {
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
      diachiBC: {
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
      diachiTB: {
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
      diachiKN: {
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
      diachiDau: {
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
      diachiCuoi: {
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
      thanhtoan: {
        matt: '',
        tentt: '',
        thanhtoan_id: 0,
        masothue: '',
        sodienthoai: '',
        email: '',
        chutk: '',
        tuyenthu_id: 0,
        matuyen: '',
        manvtc: '',
        tuyenthu_httt: 0,
        nhanvientc_id: 0,
        ghichu: '',
        hdmig_id: 0,
        mig_id: 0,
        mainghep: '',
        matb_dd: 0,
        manvql: ''
      }
    }
  },
  validations: {
    diachiKH: {
      DIACHI: {
        required
      }
    },
    diachiHK: {
      DIACHI: {
        required
      }
    },
    diachiNDD: {
      DIACHI: {
        required
      }
    },
    diachiTT: {
      DIACHI: {
        required
      }
    },
    diachiCT: {
      DIACHI: {
        required
      }
    },
    diachiBC: {
      DIACHI: {
        required
      }
    },
    diachiTB: {
      DIACHI: {
        required
      }
    },
    diachiLD: {
      DIACHI: {
        required
      }
    },
    diachiKN: {
      DIACHI: {
        required
      }
    },
    diachiDau: {
      DIACHI: {
        required
      }
    },
    diachiCuoi: {
      DIACHI: {
        required
      }
    }
  },
  watch: {
    // 'Tb.cboDichVuVT': function (val) {
    //   this.dichvuvt_id = parseInt(val)
    //   //await this.lay_ds_loaihinh_tb(val)
    //   this.Tb.cboLoaihinhTB = this.LOAIHINH_TB.list.filter((x) => x.DICHVUVT_ID == val)[0].LOAITB_ID
    //   this.GiaoDien_DichVuVT(val)
    // },
    Tb_cboDichVuVT: function(val) {
      this.dichvuvt_id = parseInt(val)
      // await this.lay_ds_loaihinh_tb(val)
      this.Tb_cboLoaihinhTB = this.LOAIHINH_TB.list.filter((x) => x.DICHVUVT_ID == val)[0].LOAITB_ID
      this.Tb.cboLoaihinhTB = this.Tb_cboLoaihinhTB
      this.GiaoDien_DichVuVT(val)
    },
    // Tb_cboLoaihinhTB: async function(val) {
    //   try {
    //     this.Loading = true
    //     await this.ds_kieu_ld(val)
    //     await this.HT_GoiCuoc_DD_Combobox(val)
    //     await this.HT_Tocdo_Adsl_Combobox_ByLoaiTB(val)
    //     this.Loading = false
    //   } catch (ex) {
    //     this.Loading = false
    //   }
    // },
    'Tb.cboBrasADSL': async function(val) {
      if (val > 0) {
        await this.lay_ds_dslam(val)
      }
    },
    // Tb_cboDaiVT: async function (val) {
    //   if (val > 0) {
    //     await this.cboDaiVT_SelectedValueChanged(val)
    //     // let vloaidv_id = `${LOAI_DV.TRAM_VT}`
    //     // let rawdata = await this.lay_ds_donvi_theo_loaidv(val, vloaidv_id)
    //     // let data = rawdata.map((x) => ({ TEN_DV: x.ten_dv, DONVI_ID: x.donvi_id }))
    //     // this.VeTinhCD.list = data
    //     // this.VeTinhAdsl.list = data
    //     // this.VeTinhMgwan.list = data
    //     // this.VeTinhIMS.list = data
    //   }
    // },
    // 'Tb.cboVeTinhAdsl': async function (val) {
    //   if (val > 0) {
    //     let data = []
    //     let vloaidv_id = `${LOAI_DV.TRAM_PORT}`
    //     let data1 = await this.lay_ds_donvi_theo_loaidv(val, vloaidv_id)
    //     if (data1 != null) data1.forEach((x) => data.push(x))
    //     vloaidv_id = `${LOAI_DV.TRAM_VETINH}`
    //     let data2 = await this.lay_ds_donvi_theo_loaidv(val, vloaidv_id)
    //     if (data2 != null) data2.forEach((x) => data.push(x))
    //     this.TramTB_ADSL.list = data.map((x) => ({ DONVI_ID: x.donvi_id, TEN_DV: x.ten_dv }))
    //   }
    // },
    'Tb.cboDSLAM': async function(val) {
      if (val > 0) {
        let thamso = await this.map_thamso(val)
        this.Tb.txtSystem.Text = ''
        this.Tb.txtRack.Text = ''
        this.Tb.txtShelf.Text = ''
        this.Tb.txtPort_mdf = ''
        this.Tb.txtSlotADSL = ''
        this.Tb.txtPort = ''
        this.Tb.txtVPIADSL = ''
        this.Tb.txtVCIADSL = ''
        if (thamso == 7) {
          this.Tb.txtSystem_Enabled = true
          this.Tb.txtRack.Enabled = true
          this.Tb.txtShelf.Enabled = true
        } else {
          this.Tb.txtSystem.Enabled = false
          this.Tb.txtRack.Enabled = false
          this.Tb.txtShelf.Enabled = false
        }
      }
    },
    'Tb.cboVeTinhCD': async function(val) {
      let data = await this.ht_donvi_loaidonvi(val, LOAI_DV.TRAM_PORT, LOAI_DV.TRAM_VETINH)
      this.TramTB_CD.list = data.map((x) => ({ TEN_DV: x.ten_dv, DONVI_ID: x.donvi_id }))
    },
    'Kh.cboHTTT': async function(val) {
      if (val > 0) {
        this.httt_id = val
        if (this.httt_id == HinhThucThanhToan.UYNHIEM_THU || this.httt_id == HinhThucThanhToan.UYNHIEM_CHI) {
          this.Kh.cboNganHangKH_enable = true
        } else {
          this.Kh.cboNganHangKH_enable = false
        }
        if (this.TUYENTHU_HTTT == 1) {
          if (this.phuongtt_id != 0 && (this.phott_id != 0 || this.aptt_id != 0 || this.khutt_id != 0)) {
            await this.HT_NVTC_TheoDC(this.quantt_id, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.Kh.cboDonViQL, this.dacdiemtt_id)
          }
        }
      }
    },
    Loading: function(val) {
      this.$root.showLoading(val)
    }
  },
  async created() {
    if (this.$route.name.toLowerCase() == 'CompleteProfileBuySamsungTV'.toLowerCase()) {
      this.Tag = '5+4'
      this.formTitle = 'Hoàn thiện hồ sơ mua tivi Samsung'
    } else if (this.$route.name.toLowerCase() == 'ChiTietLapMoi'.toLowerCase()) {
      this.Tag = '0'
      this.btnChuyenDB = false
      this.formTitle = 'Chi tiết lắp mới'
    } else if (this.$route.name.toLowerCase() == 'DetailNewAssembly'.toLowerCase()) {
      this.Tag = '0'
      this.btnChuyenDB = false
      this.formTitle = 'Chi tiết lắp mới'
    } else {
      this.Tag = '5+4'
      this.formTitle = 'Hoàn thiện hồ sơ mua tivi Samsung'
    }
    this.labelFunctionTitle = this.formTitle
    try {
      if (this.kieugoi_id == 0)
        if (this.Tag != null && this.Tag != '') {
          let arr = this.Tag.split('+')
          if (arr.length > 1) {
            //      console.log('Tag2Arr',arr)
            this.kieugoi_id = parseInt(arr[0].trim())
            //            alert(this.kieugoi_id)
            this.dsdichvuvt_id = arr[1].replace(';', ',')
            this.dichvuvt_id = parseInt(this.dsdichvuvt_id.split(',')[0].trim())
          } else this.kieugoi_id = parseInt(this.Tag.trim())
        } else this.kieugoi_id = 0
      if (parseInt(this.cboDichVuVT) == DichVuVienThong.DI_DONG) this.tsbtnGhiLai.Enabled = false
    } catch (ex) {
      console.log('created-err', ex)
      this.kieugoi_id = 0
    }
    //alert('kieugoi1'+ this.dichvuvt_id)
    this.labelFunctionTitleLower = this.Capitalize(this.labelFunctionTitle)
    this.Loading = true
    this.watcherActive = false
    let loaitb_id = this.Tb_cboLoaihinhTB
    this.HienThiGiaoDien(this.kieugoi_id)
    await this.fn_lay_load_hths_datmoi()

    Promise.all([
      this.ds_kieu_ld(loaitb_id),
      this.lay_ds_bras(),
      this.lay_ds_tocdo_adsl(),
      this.HT_Loai_Kenh_Combobox(),
      this.HT_Tocdo_Kenh_Combobox(),
      this.HT_Loai_Modem_Combobox(),
      this.HT_TrangBi_MegaWan_Combobox(),
      this.HT_TrangBi_TSL_Combobox(),
      this.lay_danhsach_thuoctinh_v3(179),
      this.ht_loaigt_combobox()
    ])
      .then((r) => {
        // console.log('result', r)
      })
      .finally((x) => {
        this.Loading = false
        this.watcherActive = true
      })
  },
  mounted() {
    this.HienThiGiaoDien(this.kieugoi_id)
  },
  computed: {
    loai_kh_tree: {
      get() {
        var listkieukh = [...new Set(this.LoaiKH.map((x) => x.KIEU_KH))].map((x) => ({ label: x, id: x }))
        for (var kieukh of listkieukh) {
          kieukh.children = [...new Set(this.LoaiKH.filter((x) => x.KIEU_KH == kieukh.label).map((x) => x.TEN_NHOM))].map((x) => ({ label: x, id: x }))
          for (var nhomkh of kieukh.children) {
            nhomkh.children = this.LoaiKH.filter((x) => x.KIEU_KH == kieukh.label && x.TEN_NHOM == nhomkh.label).map((x) => ({ id: x.LOAIKH_ID, label: x.TEN_LOAIKH, cate: kieukh.label, subcate: nhomkh.label }))
          }
        }
        return listkieukh
      }
    }
  },
  methods: {
    fn_lay_load_hths_datmoi: async function() {
      let nhanvienId = this.$root.token.getNhanVienID()
      let ds_para = { DSDICHVUVT_ID: this.dsdichvuvt_id, NGUOIDUNG_ID: nhanvienId }
      let input = {
        ds_para: JSON.stringify(ds_para) // "{'DSDICHVUVT_ID':'1,2,3,4,5,6','NGUOIDUNG_ID':1}"
      }
      let data = this.GetData(await api.fn_lay_load_hths_datmoi(this.axios, input))
      let dataJson = JSON.parse(data).RESULT
      this.COMBO_DVVT.list = dataJson.COMBO_DVVT
      if (dataJson.GT_DVVT_MD != null && dataJson.GT_DVVT_MD != '' && dataJson.GT_DVVT_MD > 0) {
        console.log('this.Tb.cboDichVuVT = dataJson.GT_DVVT_MD', dataJson.GT_DVVT_MD)
        this.Tb_cboDichVuVT = dataJson.GT_DVVT_MD
        this.Tb.cboDichVuVT = dataJson.GT_DVVT_MD
      } else {
        // this.Tb.cboDichVuVT = this.dsdichvuvt_id.split(',')[0]
        console.log('this.Tb.cboDichVuVT = this.COMBO_DVVT.list[0].DICHVUVT_ID', this.COMBO_DVVT.list[0].DICHVUVT_ID)
        this.Tb.cboDichVuVT = this.COMBO_DVVT.list[0].DICHVUVT_ID
        this.Tb_cboDichVuVT = this.COMBO_DVVT.list[0].DICHVUVT_ID
      }

      this.LOAIHINH_TB.list = dataJson.LOAIHINH_TB
      this.Tb_cboLoaihinhTB = this.LOAIHINH_TB.list.filter((x) => x.DICHVUVT_ID == this.Tb_cboDichVuVT)[0].LOAITB_ID
      await this.ds_kieu_ld(this.Tb_cboLoaihinhTB)
      // this.GiayToKH = dataJson.LOAI_GT
      this.GiayToKHNeo = dataJson.LOAI_GT
      this.QuocTich = dataJson.QUOCTICH
      this.DanToc = dataJson.DANTOC
      this.LoaiKH = dataJson.LOAI_KH
      this.NganhNgheLoc = dataJson.NGANHNGHE.filter((x) => x.NGANHNGHE_ID != null)
      this.NganHangKHList = dataJson.NGANHANG_LD
      this.NganHangList = dataJson.NGANHANG_LD
      this.HINHTHUC_TT.list = dataJson.HINHTHUC_TT
      this.DONVIQL_TT.list = dataJson.DS_DCTT
      this.DONVIQL_LD.list = dataJson.DS_DCLD
      try {
        this.Tb_cboDaiVT = this.DONVIQL_LD.list[0].DONVI_ID
        this.Tb.cboDaiVT = this.DONVIQL_LD.list[0].DONVI_ID
        await this.cboDaiVT_SelectedValueChanged(this.Tb_cboDaiVT)
      } catch (e1) {
        console.log('this.Tb.cboDaiVT = this.DONVIQL_LD.list[0].DONVI_ID ~ err:', e1)
      }

      this.DONVIQL_HOST.list = dataJson.DS_HOST
      this.HostCD.list = dataJson.DS_HOST
      this.VeTinhCD.list = dataJson.DS_DCLD
      this.CHUQUAN.list = dataJson.CHUQUAN
      this.TRANGBI.list = dataJson.TRANGBI

      this.MUCCUOC.list = dataJson.MUCCUOC
      this.LOAI_TB.list = dataJson.LOAI_TB

      this.LOAI_KHL.list = dataJson.LOAI_KHL
      this.KhachHangLon.list = dataJson.LOAI_KHL
      this.HT_KHL = dataJson.HT_KHL
      this.DONVIQL_HTTT = dataJson.DONVIQL_HTTT
      this.TUYENTHU_HTTT = dataJson.TUYENTHU_HTTT
      this.DOITUONG.list = dataJson.DOITUONG
      this.Tb.cboDoituongTB = this.DOITUONG.list[0].DOITUONG_ID
      this.ThoiHanTSL.list = dataJson.THOIHAN
      this.ThoiHanMEGAWAN.list = dataJson.THOIHAN
      this.LoaiModemMgwan.list = dataJson.LOAI_MODEM

      this.LoaiKenhTSL.list = dataJson.LOAI_KENH
      this.LoaiKenhTSL.id = 'LOAIKENH_ID'
      this.LoaiKenhTSL.text = 'LOAI_KENH'

      this.TocDoKenhTSL.list = dataJson.TOCDO_KENH
      this.TocDoKenhTSL.id = 'TOCDO_ID'
      this.TocDoKenhTSL.text = 'TOCDO'

      this.TrangBiTSL.list = dataJson.TRANGBI
      this.TrangBiTSL.id = 'TRANGBI_ID'
      this.TrangBiTSL.text = 'TRANGBI'

      this.ChuQuanTSL.list = dataJson.CHUQUAN
      this.ChuQuanTSL.id = 'CHUQUAN_ID'
      this.ChuQuanTSL.text = 'TEN_TAT'

      this.LoaiTBTSL.list = dataJson.LOAI_TB.filter((x) => x.DICHVUVT_ID == DichVuVienThong.TSL).slice(0)
      this.LoaiTBTSL.id = 'THIETBIDC_ID'
      this.LoaiTBTSL.text = 'TEN_TBI'

      console.log('dataJson', dataJson)
      if (this.HT_KHL == '1') {
        this.KhachHangLon.visible = true
      } else {
        this.KhachHangLon.visible = false
      }
      this.loadData()
      return dataJson
    },
    lay_ds_hopdong_tt_theo_hdtt_id: async function(hdtt_id) {
      let input = { in_hdtt_id: hdtt_id }
      let data = this.GetData(await api.lay_ds_hopdong_tt_theo_hdtt_id(this.axios, input))
      return data
    },
    lay_ds_hopdong_tb_theo_hdtb_id: async function(hdtb_id) {
      let input = { in_hdtb_id: hdtb_id }
      let data = this.GetData(await api.lay_ds_hopdong_tb_theo_hdtb_id(this.axios, input))
      return data
    },
    fn_ht_tt_morong_hths_datmmoi: async function() {
      let nhanvienId = this.$root.token.getNhanVienID()
      let _loaitbId = this.Tb_cboLoaihinhTB
      let ds_para = { MA_TB: this.ma_tb, HDTB_ID: this.hdtb_id, DICHVUVT_ID: this.dichvuvt_id, LOAITB_ID: _loaitbId /*this.cboLoaihinhTB*/ }
      let input = {
        ds_para: JSON.stringify(ds_para) // "{'DSDICHVUVT_ID':'1,2,3,4,5,6','NGUOIDUNG_ID':1}"
      }
      console.log('fn_ht_tt_morong_hths_datmmoi:', ds_para)
      let data = this.GetData(await api.fn_ht_tt_morong_hths_datmmoi(this.axios, input))
      let dataJson = JSON.parse(data).RESULT
      console.log('fn_ht_tt_morong_hths_datmmoi dataJson', dataJson)
      return dataJson
    },
    fn_hienthi_tt_hd_khachhang_hths_datmmoi: async function(hdkh_id, nhanviengt_id) {
      let donviid = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let ds_para = { HDKH_ID: hdkh_id, CTV_ID: this.ctv_id, NHANVIENGT_ID: nhanviengt_id, TTHD_ID: 0 }
      // ds_para={'HDKH_ID':140841,'CTV_ID':1,'NHANVIENGT_ID':123,'TTHD_ID':0}
      let input = { ds_para: JSON.stringify(ds_para) }
      let data = this.GetData(await api.fn_hienthi_tt_hd_khachhang_hths_datmmoi(this.axios, input))
      let dataJson = JSON.parse(data).RESULT

      console.log('fn_hienthi_tt_hd_khachhang_hths_datmmoi dataJson', dataJson)
      if (dataJson != null) {
        if (dataJson.CTV != '') {
          this.txtCTVcheck = true
          this.txtCTV = dataJson.CTV
        }
        if (dataJson.NVGT_ID != '') {
          this.txtNguoiGT = dataJson.NVGT_ID
          this.txtNguoiGTcheck = true
        }
        this.DsThanhToan.data = dataJson.DS_HDTT
        if (dataJson.DS_HDTB != null && dataJson.DS_HDTB.length > 0) {
          try {
            let tmpData = dataJson.DS_HDTB
            tmpData.forEach(function(item) {
              if (item.NGAY_HT != null) {
                item.NGAY_HT = moment(item.NGAY_HT).format('DD/MM/YYYY  HH:MM:SS')
              }
            })
            this.DsThueBao.data = tmpData
          } catch (err) {
            this.DsThueBao.data = dataJson.DS_HDTB
          }
          try {
            await this.lvwThueBao_SelectedIndexChanged(0, this.DsThueBao.data[0])
          } catch (errIndex) {
            console.log('🚀 ~ file: CompleteProfileBuySamsungTV.vue:1317 ~ errIndex:', errIndex)
          }
        }
      }
      return dataJson
    },
    fn_hienthi_tt_hd_thanhtoan_hths_datmmoi: async function(hdtt_id, tuyenthu_id) {
      let donviid = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let ds_para = { HDTT_ID: hdtt_id, NHANVIEN_ID: nhanvienId, TUYENTHU_ID: tuyenthu_id }
      let input = { ds_para: JSON.stringify(ds_para) }
      let data = this.GetData(await api.fn_hienthi_tt_hd_thanhtoan_hths_datmmoi(this.axios, input))
      let dataJson = JSON.parse(data).RESULT
      console.log('fn_hienthi_tt_hd_thanhtoan_hths_datmmoi dataJson', dataJson)
      let ds_diachi = dataJson.DS_DIACHI
      let dvk = dataJson.DVK
      let ma_nvtc = dataJson.MA_NVTC
      this.Kh.txtMaNVTC = ma_nvtc
      let ma_tuyen = dataJson.MA_TUYEN
      this.Kh.txtMaTuyen = ma_tuyen
      return dataJson
    },
    lay_ds_hopdong_theo_ma_gd: async function(ma_gd, tthd_id) {
      let donviid = this.$root.token.getDonViID()
      let input = {
        in_ma_gd: ma_gd,
        in_loaihd_id: LoaiHopDong.DAT_MOI,
        in_tthd_id: tthd_id,
        in_donvi_dl_id: donviid
      }
      let data = this.GetData(await api.lay_ds_hopdong_theo_ma_gd(this.axios, input))
      return data
    },
    post_lay_ds_hopdong_theo_ma_gd: async function(ma_gd, tthd_id) {
      let donviid = this.$root.token.getDonViID()
      let input = {
        in_donvi_dl_id: 0,
        in_donvi_id: this.$root.token.getDonViID(),
        in_loaihd_id: LoaiHopDong.DAT_MOI, // Test data 1, default code 0
        in_ma_gd: ma_gd,
        in_nhanvien_id: this.$root.token.getNhanVienID(),
        in_tthd_id: tthd_id
      }
      let data = this.GetData(await api.post_lay_ds_hopdong_theo_ma_gd(this.axios, input))
      return data
    },
    fn_lay_hd_theo_ma_gd_hths_datmmoi: async function(ma_gd, tthd_id) {
      let donviid = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let donvi_dl_id = 0 //  this.$root.token.getDonViID()
      let param = { TTHD_ID: tthd_id, MA_GD: ma_gd, TTND_DONVI_ID: donviid, TTND_NHANVIEN_ID: nhanvienId, TTND_DONVI_DL_ID: donvi_dl_id }
      let input = {
        ds_para: JSON.stringify(param)
      }
      let data = this.GetData(await api.fn_lay_hd_theo_ma_gd_hths_datmmoi(this.axios, input))
      let dataJson = JSON.parse(data).RESULT
      console.log('fn_lay_hd_theo_ma_gd_hths_datmmoi dataJson', dataJson)
      return dataJson
      return data
    },
    fn_ht_tt_danhba_hths_datmmoi: async function(thuebao_id) {
      let donviid = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let donvi_dl_id = 0 //  this.$root.token.getDonViID()
      let param = { MA_TB: this.ma_tb, DICHVUVT_ID: this.dichvuvt_id, DONVI_DL_ID: 0, THUEBAO_ID: thuebao_id }
      let input = {
        ds_para: JSON.stringify(param)
      }
      let data = this.GetData(await api.fn_ht_tt_danhba_hths_datmmoi(this.axios, input))
      return data
    },
    fn_hienthi_tt_hd_thuebao_hths_datmmoi: async function(hdtb_id, thuebao_id, loaitb_id) {
      let donviid = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let donvi_dl_id = 0 //  this.$root.token.getDonViID()
      let param = { THUEBAO_ID: thuebao_id, HDTB_ID: hdtb_id, LOAITB_ID: loaitb_id, MUCCUOCTB_ID: 9962 }
      param = { THUEBAO_ID: 2907755, HDTB_ID: 1474319, LOAITB_ID: 1 }
      let input = {
        ds_para: JSON.stringify(param)
      }
      let data = this.GetData(await api.fn_hienthi_tt_hd_thuebao_hths_datmmoi(this.axios, input))
      let dataJson = JSON.parse(data)
      console.log('fn_hienthi_tt_hd_thuebao_hths_datmmoi dataJson', dataJson)
      /*
      return CHITIET_KM:[],DS_DIACHI:[],DS_KIEULD:[],DS_MUATBI:[],DS_MUCCUOC:[],DS_NV:[],
      DS_TB_DICHVU:[],DVK_TB:[]
      */
      if (dataJson.ERROR_CODE == 1) return dataJson.RESULT
      return null
    },
    ds_kieu_ld: async function(loaitbId) {
      console.log('ds_kieu_ld-loaitb_id:', loaitbId)
      try {
        let input = {
          loaihd_id: 1, // dat moi
          loaitb_id: loaitbId // loaitb_id
        }
        if (loaitbId > 0) {
          let data = this.GetData(await api.ds_kieu_ld(this.axios, input))
          this.kieuyc_list = data
          if (data != null && data.length > 0) this.Tb_cboKieuLD = data[0].kieuld_id
        } else {
          // this.KIEUYC.list = []
        }
      } catch (err) {
        console.log('ds_kieu_lderr:1439 ~ err:', err)
      } finally {
        await this.ds_kieu_ld_full()
      }
    },
    ds_kieu_ld_full: async function() {
      try {
        let input = {
          loaihd_id: 1, // dat moi
          loaitb_id: 0 // loaitb_id
        }
        let data = this.GetData(await api.ds_kieu_ld(this.axios, input))
        this.kieuyc_list_full = data
      } catch (err) {
        console.log('ds_kieu_lderr:1439 ~ err:', err)
      }
    },
    lay_ds_donvi_theo_loaidv: async function(donvi_id, loaidv_id) {
      try {
        let input = {
          donvi_id: donvi_id, // dat moi
          loaidv_id: loaidv_id // loaitb_id
        }
        let data = this.GetData(await api.lay_ds_donvi_theo_loaidv(this.axios, input))
        return data
      } catch (err) {
        return []
      }
    },
    lay_hdtb_id_donvi_id_loaidv_id: async function(hdtb_id) {
      // console.log('lay_hdtb_id_donvi_id_loaidv_id',hdtb_id)
      // let input = {
      //   schema: 'css',
      //   list_of_cols: 'hdtb_id,donvi_id,loaidv_id',
      //   order: '',
      //   table_name: 'hdtb_dv',
      //   where: `hdtb_id=${hdtb_id} and loaidv_id=5` // LOAI_DV.TRAM_VT
      // }
      // let data = this.GetData(await api.lay_dulieu_bang_theo_dieukien(this.axios, input))
      try {
        let data = this.GetData(await api.sp_tt_hdtb_dv(this.axios, { param: hdtb_id, type: 1 }))
        return data
      } catch (err) {
        return []
      }
    },
    // ham nay ko dung
    lay_ds_loaihinh_tb: async function(dichvuvt_id) {
      // chua co ham api moi
      // let input = {
      //   schema: 'css',
      //   list_of_cols: 'loaitb_id,loaihinh_tb',
      //   order: '',
      //   table_name: 'loaihinh_tb',
      //   where: `dichvuvt_id=${dichvuvt_id}`
      // }
      // let data = this.GetData(await api.lay_dulieu_bang_theo_dieukien(this.axios, input))
      let data = this.GetData(await api.lay_ds_loaihinh_tb_theo_dvvt_id(this.axios, { dvvtId: dichvuvt_id }))
      this.BrasADSL.list = data
      if (data != null && data.length > 0) this.Tb.cboBrasADSL = data[0].bras_id
    },
    lay_ds_bras: async function() {
      // let input = {
      //   schema: 'css',
      //   list_of_cols: 'bras_id,tenbras',
      //   order: 'caidat_md desc',
      //   table_name: 'bras',
      //   where: ''
      // }
      // let data = this.GetData(await api.lay_dulieu_bang_theo_dieukien(this.axios, input))
      let data = this.GetData(await api.sp_tt_bras(this.axios, { param: '1', type: 1 }))
      this.BrasADSL.list = data
      if (data != null && data.length > 0) this.Tb.cboBrasADSL = data[0].bras_id
    },
    lay_ds_dslam: async function(bras_id) {
      if (bras_id > 0) {
        let data = this.GetData(await api.sp_tt_dslam(this.axios, { param: bras_id, type: 1 }))
        this.DsLam.list = data
        if (this.DsLam.list != null && this.DsLam.list.length > 0) {
          this.Tb.cboDSLAM = data[0].dslam_id
        }
      }
    },
    lay_ds_tocdo_adsl: async function() {
      // let input = {
      //   schema: 'css',
      //   list_of_cols: 'tocdo_id,thuonghieu',
      //   order: 'tocdo_id desc',
      //   table_name: 'tocdo_adsl',
      //   where: ''
      // }
      // let data = this.GetData(await api.lay_dulieu_bang_theo_dieukien(this.axios, input))
      let data = this.GetData(await api.sp_tt_tocdo_adsl(this.axios, { param: '', type: 3 }))
      this.GoiCuocAdsl.list = data.map((x) => ({ TOCDO_ID: x.tocdo_id, THUONGHIEU: x.thuonghieu }))
      if (data != null && data.length > 0) this.Tb.cboGoiCuocAdsl = data[0].tocdo_id
    },
    loadData: function() {
      Promise.all([this.cboLoaiModem(), this.cboLoaiTBiWan()])
    },
    cboLoaiModem: async function() {
      // let input = {
      //   schema: 'css',
      //   list_of_cols: 'loaimd_id,loai_md',
      //   order: '',
      //   table_name: 'loai_modem',
      //   where: ''
      // }
      // let data = this.GetData(await api.lay_dulieu_bang_theo_dieukien(this.axios, input))
      let data = this.GetData(await api.sp_tt_loai_modem(this.axios, { param: '1', type: 1 }))
      this.LoaiModemMgwan.id = 'LOAIMD_ID'
      this.LoaiModemMgwan.text = 'LOAI_MD'
      this.LoaiModemMgwan.list = data.map((x) => ({ LOAIMD_ID: x.loaimd_id, LOAI_MD: x.loai_md }))
      if (data != null && data.length > 0) {
        this.Tb.cboLoaiModem = data[0].LOAIMD_ID
      }
    },
    cboLoaiTBiWan: async function() {
      this.LoaiModemMgwan.id = 'THIETBIDC_ID'
      this.LoaiModemMgwan.text = 'TEN_TBI'
      let data = this.LOAI_TB.list.filter((x) => x.DICHVUVT_ID == this.dichvuvt_id)
      // this.LoaiModemMgwan.list = data
      if (data != null && data.length > 0) this.Tb.LoaiModemMgwan = this.LoaiModemMgwan.list[0].THIETBIDC_ID
    },
    lay_ds_hdtt_theo_hdkh_id_1: async function(hdkh_id) {
      let input = {
        in_hdkh_id: hdkh_id
      }
      let data = this.GetData(await api.lay_ds_hdtt_theo_hdkh_id_1(this.axios, input))
      return data
    },
    Init_Properties: async function(data, dataMR) {
      // let thuoctinh_old = await this.lay_danhsach_thuoctinh_v3(this.Tb.cboLoaihinhTB)
      let thuoctinh = await this.lay_danhsach_thuoctinh_datmoi(this.Tb_cboLoaihinhTB)
      let properties = []
      if (thuoctinh == null) return
      thuoctinh.forEach(function(item) {
        try {
          let oThuocTinh = item // { field_name: item.field_name, caption: item.caption, data_type: item.data_type, text: '', thutu_id: item.thutu_id }
          if (data != null && data.length > 0) oThuocTinh.text = data[0][item.field_name]
          properties.push(oThuocTinh)
        } catch (ex) {
          console.log('🚀 ~ file: CompleteProfileBuySamsungTV.vue:1517 ~ ex:', ex)
        }
      })
      //
      let ma_tb = this.Tb.txtMaTB
      let ds = dataMR.DS_MORONG
      var self = this
      properties.forEach(function(item) {
        try {
          if (item.field_name == 'MA_TB') {
            item.text = ma_tb
          } else {
            // ItemList[index1].sValue = ds.Tables[0].Rows[0][ItemList[index1].sFieldName].ToString();
            item.text = self.convertToDateTime(ds[0][item.field_name])
          }
          if (item.att == 0) {
          } else if (item.att == 1) {
          } else if (item.att == 2) {
          } else if (item.att == 4) {
            try {
              if (item.lookup_sql != null && item.lookup_sql.length > 0 && item.lookup_sql_response != null) {
                if (item.lookup_sql_response.length > 0) {
                  let lookUp = self.UpperCasePropertyList(item.lookup_sql_response)
                  let _val = ds[0][item.field_name]
                  console.log(' ds[0][item.field_name]', item.field_name, ds[0][item.field_name], ds[0], lookUp)
                  let _vals = lookUp.filter((x) => x[item.field_name.toUpperCase()] == _val || x[item.parent_keyfield.toUpperCase()] == _val)
                  console.log('item.att == 4', item.field_name, 'ds[0][item.field_name]', item.field_name, ds[0][item.field_name], _vals)
                  if (_val != null && _vals != null && _vals.length > 0) {
                    console.log('🚀 ~ file: CompleteProfileBuySamsungTV.vue:1541 ~ _vals:', _vals)
                    item.text = _vals[0][item.parent_descfield.toUpperCase()]
                  } else {
                    item.text = lookUp[0][item.parent_keyfield.toUpperCase()]
                  }
                }
              }
            } catch (erratt4) {
              console.log('🚀 ~ file: CompleteProfileBuySamsungTV.vue:1576 ~ properties.forEach ~ erratt4:', erratt4)
            }
          } else if (item.att == 5) {
          } else if (item.att == 7) {
            console.log('item.att == 7', item)
            if (item.text == '0') {
              item.text = '<input type="checkbox" disabled/>'
            } else {
              item.text = '<input type="checkbox" checked="true"  disabled/>'
            }
          } else {
          }
          if (item.field_name == 'MUCDICH_IDS' && item.lookup_sql_response != null) {
            if (item.lookup_sql_response.length > 0 && item.text.length > 0) {
              let lookUp = self.UpperCasePropertyList(item.lookup_sql_response)
              let arr = item.text.split(',').map((x) => x.trim())
              let arr2 = lookUp.filter((x) => arr.filter((y) => y == x.MUCDICH_CA_ID).length > 0)
              item.text = arr2.map((x) => x.MUCDICH_SD).join(', ')
            }
          }
          if (item.field_name == 'TOCDO_ID' && item.lookup_sql_response != null) {
            console.log('MAP-TOCDO_ID:', item.lookup_sql_response, item.text)
            console.log('MAP-TOCDO_ID:', item.lookup_sql_response.length)
            if (item.lookup_sql_response.length > 0) {
              let lookUp = self.UpperCasePropertyList(item.lookup_sql_response)
              let id = parseInt(item.text)
              let arr = lookUp.filter((x) => x.TOCDO_ID == id)
              console.log('MAP-TOCDO_ID:', lookUp, arr, item.text)
              if (arr != null && arr.length > 0) {
                item.text = arr[0].THUONGHIEU
              }
            }
          }
        } catch (errProperties) {
          console.log('🚀 ~ file: CompleteProfileBuySamsungTV.vue:1532 ~ errProperties:', errProperties)
        }
      })
      //
      this.ThuocTinhCNTT = properties
    },
    convertToDateTime(sValue) {
      try {
        if (sValue && sValue.length == '2022-03-30T07:37:00'.length) {
          let date = sValue.substr(0, 10)
          return moment(date, 'YYYY-MM-DD').format('DD/MM/YYYY')
        }
        return sValue
      } catch (err) {
        return sValue
      }
    },
    lay_danhsach_thuoctinh_v3: async function(vloaitb_id) {
      let input = {
        vloaitb_id: vloaitb_id,
        vten_bang: 'HDTB_CNTT',
        vopt_list: '00000'
      }
      let data = this.GetData(await api.lay_danhsach_thuoctinh_v3(this.axios, input))
      console.log('lay_danhsach_thuoctinh_v3', data)
      return data
    },
    lay_danhsach_thuoctinh_datmoi: async function(vloaitb_id) {
      try {
        let input = {
          vloaitb_id: vloaitb_id,
          vten_bang: 'HDTB_CNTT',
          vopt_list: '00000'
        }
        let data = this.GetData(await api.lay_danhsach_thuoctinh_datmoi(this.axios, input))
        console.log('lay_danhsach_thuoctinh_datmoi', data)
        return data
      } catch (err) {
        console.log('🚀 lay_danhsach_thuoctinh_datmoi ~ err:', err)
      }
    },
    map_thamso: async function(dslam_id) {
      // let input = { id_neo: 'thamso', in_table: 'dslam', in_dk: `where dslam_id = ${dslam_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_dslam(this.axios, { param: dslam_id, type: 1 }))
      return data
    },
    MapTocDo: async function(tocdo_id) {
      // let input = { id_neo: 'matocdo', in_table: 'css.tocdo_adsl', in_dk: `where tocdo_id  = ${tocdo_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_tocdo_adsl(this.axios, { param: tocdo_id, type: 4 }))
      return data
    },
    ShowSearchAccount() {
      this.popupComponent = () => import('@/modules/search/subscriber/SearchAccount')
      this.popupComponentName = 'SearchAccount'
      try {
        this.popupComponent.data.isPopup = true
        this.popupComponent.data.kieu = 1
      } catch (ex) {
        if (this.debug) console.error(ex)
      }
      this.Popup('popupComponents')
    },
    showPopupSearchContract() {
      this.$refs.popupSearchContract.showModal()
    },
    async acceptSearchContract(item) {
      this.Kh.txtMaGD = item.ma_gd
      if (this.Kh.txtMaGD && this.Kh.txtMaGD.trim() != '') await this.MaGDChanged(item.ma_gd)
    },
    MaGDChanged: async function() {
      //let data = await this.post_lay_ds_hopdong_theo_ma_gd(this.Kh.txtMaGD, this.tthd_id)
      try {
        this.Loading = true
        let data = await this.fn_lay_hd_theo_ma_gd_hths_datmmoi(this.Kh.txtMaGD, 0) //this.tthd_id)
        await this.HienThiTTHopDongKH(data)
        this.Loading = false
      } catch (err) {
        this.Loading = false
        console.log('MaGDChanged-err', err)
      }
    },
    HienThiGiaoDien: function(kieugoi_id) {
      this.tthd_id = kieugoi_id
      this.tsbtnThongTinLH = false
      this.tsbtnHen = false
      this.tsbtnGhiLai = false
      this.tsbtnChuyenDB = false
      this.tsbtnThemTT = false
      this.tsbtnXoaTT = false
      this.tsbtnHuyBo = false
      this.tsbtnNhapMoi = false
      this.tsbtnGiaoPhieu = false
      this.tsbtnSuaDacTinh = false
      this.btnChonNe = false
      this.btnChonMaTB = false
      this.btnChonPort = false
      this.tss0 = false
      this.tss1 = false
      this.tss2 = false
      // chkNgayBG.Checked = false;
      // labelFunctionTitle.Text = "HOÀN THIỆN HỒ SƠ LẮP MỚI";
      if (this.kieugoi_id == KIEUGOI_CN_HOPDONG.DIEUHANH_THI_CONG) {
        this.tsbtnGhiLai = true
        this.tsbtnThemTT = true
        this.tsbtnXoaTT = true
        this.tsbtnHuyBo = true
        this.tsbtnNhapMoi = true
        this.tsbtnGiaoPhieu = false
        this.tss0 = true
        this.btnChonMaTB = true
      }
      if (this.kieugoi_id == KIEUGOI_CN_HOPDONG.DANG_THI_CONG) {
        this.tsbtnGhiLai = false
        this.tsbtnThemTT = true
        this.tsbtnXoaTT = true
        this.tsbtnHuyBo = true
        this.tsbtnNhapMoi = true
        this.tsbtnSuaDacTinh = true
        this.tss1 = true
        this.btnChonMaTB = true
      }
      if (this.kieugoi_id == KIEUGOI_CN_HOPDONG.DA_THI_CONG_XONG) {
        this.tsbtnChuyenDB = true
        this.chkNgayBG = true
      }
      if (this.kieugoi_id == KIEUGOI_CN_HOPDONG.SUA_HD) {
        this.btnMaNVTC = true
        this.tsbtnGiaoPhieu = false
        this.btnLayMaKH = false
        this.chkNgayBG = false
        this.txtMaKH_ReadOnly = true
        this.tsbtnGhiLai = true
        this.tsbtnThemTT = true
        this.tsbtnXoaTT = true
        this.tsbtnHuyBo = true
        this.tsbtnNhapMoi = true
        this.tss1 = true
        /*
                pnlAdsl.Enabled = true;
                pnlCD.Enabled = true;
                pnlDD.Enabled = false;
                pnlInt.Enabled = false;
                pnlIMS.Enabled = false;
                pnlTSL.Enabled = false;
                pnlWan.Enabled = false;
                pnlCNTT.Enabled = false;
                */

        // hoangpkn : 21/11/2012
        /*
                this.btnDiaChiKH = true;
                this.btnDiaChiTT = true;
                this.btnDiaChiCT = true;
                this.btnDiaChiTB = true;
                this.btnDiaChiNDD = true;
                this.btnDiaChiLD = true;
                this.tsbtnThemTT = false;
                this.tsbtnXoaTT = false;
                //end hoang them
                */
        /*
                this.labelFunctionTitle = "SỬA THÔNG TIN HỢP ĐỒNG ĐẶT MỚI";
                this.cboDaiVT_Enabled = false;
                this.txtSTK_Enabled = true;
                this.txtMST_Enabled = true;
                this.txtSTK.ReadOnly = false;
                this.txtMST.ReadOnly = false;
                this.txtChu_TK.ReadOnly = false;
                this.txtChu_TK_Enabled = true;
                this.tsbtnThongTinLH = true;
                this.tsbtnHen = true;
                */
      }
      if (this.kieugoi_id == 0) {
        //this.grcMacdinh.Enabled = true;
        this.labelFunctionTitle = 'TRA CỨU CHI TIẾT HỢP ĐỒNG ĐẶT MỚI'
        this.labelFunctionTitleLower = this.Capitalize(this.labelFunctionTitle)
      }
    },
    GiaoDien_DichVuVT: function(dichvuvt_id) {
      this.pnlTBRight = true
      switch (dichvuvt_id) {
        case DichVuVienThong.CO_DINH:
          if (this.kieugoi_id == KIEUGOI_CN_HOPDONG.SUA_HD) {
            this.cboVeTinhCD_Enabled = true
            this.txtLineTrang_CD_Enabled = false
            this.txtNe_Enabled = false
          }
          break
        case DichVuVienThong.DI_DONG:
          //btnEditDVGT.Enabled = true;
          break
        case DichVuVienThong.IMS:
          break
        case DichVuVienThong.DICHVU_CNTT:
        case DichVuVienThong.TRUNGTAM_DULIEU:
        case DichVuVienThong.ANTOAN_BAOMAT_TT:
        case DichVuVienThong.HOINGHI_TRUYENHINH:
          this.pnlTBRight = false
          // pnlCNTT.Visible = true;
          // pnlCNTT.Dock = DockStyle.Fill;
          break

        case DichVuVienThong.ADSL:
          if (this.BrasADSL.list != null && this.BrasADSL.list.length > 0) this.Tb.cboBrasADSL = this.BrasADSL.list[0].bras_id
          // cboBrasADSL.SelectedIndex = 0;

          if (this.kieugoi_id == KIEUGOI_CN_HOPDONG.SUA_HD) {
            this.cboVeTinhAdsl_Enabled = true
            this.txtLineTrang_CD_Enabled = false
            this.txtNe_Enabled = false
          }
          break
        case DichVuVienThong.MEGAWAN:
        case DichVuVienThong.METRONET:
          // pnlWan.Visible = true;
          // pnlWan.Dock = DockStyle.Fill;
          break
        case DichVuVienThong.TSL:
          // pnlTSL.Visible = true;
          // pnlTSL.Dock = DockStyle.Fill;
          break
        case DichVuVienThong.GPHONE:
          // pnlGP.Visible = true;
          // pnlGP.Dock = DockStyle.Fill;
          break
        case 50:
          // pnlTBRight.Visible = false;
          // pnlCNTT.Visible = true;
          //pnlCNTT.Dock = DockStyle.Fill;
          break
        default:
          //pnlCD.Visible = true;
          break
      }
    },
    Clear() {
      //lvDvkhac_TT.Items.Clear();
      //chkIpTinh.Checked = false
      this.Tb.txtIPADSL = ''
      this.Kh.txtMaHD = ''
      this.hdkh_id = 0
      this.Kh.txtMaGD = ''
      this.Kh.txtMaKH = ''
      this.khachhang_id = 0
      this.Kh.txtTenKH = ''
      this.Kh.txtDiaChiKH = ''
      this.tinhkh_id = 0
      this.quankh_id = 0
      this.phuongkh_id = 0
      this.phokh_id = 0
      this.apkh_id = 0
      this.sonhakh = ''
      this.Kh.txtTelKH = ''
      this.Kh.txtFaxKH = ''
      this.nguoigt_id = 0
      this.ctv_id = 0

      this.Tb.txtNgaySN = ''
      this.Kh.dtpNgayLapHD = moment().format('DD/MM/YYYY')
      this.Kh.txtSoGT = ''
      this.Kh.txtNgayCap = ''
      this.Kh.txtNoiCap = ''
      this.Kh.cboGiayToKH = 0
      //cboTT.DataSource = null;
      //lvwThanhToan.Items.Clear();
      //lvwThueBao.Items.Clear();
      this.Tb.txtmaTN_CD = ''
      this.hdtt_id = 0
      this.Kh.txtMaTT = ''
      this.thanhtoan_id = 0
      this.Kh.txtTenTT = ''
      this.Kh.txtDiaChiTT = ''
      this.tinhtt_id = 0
      this.quantt_id = 0
      this.phuongtt_id = 0
      this.phott_id = 0
      this.aptt_id = 0
      this.sonhatt = ''
      this.Kh.txtMST = ''

      this.Kh.txtSTK = ''
      this.Kh.txtChu_TK = ''

      this.Kh.txtMaTBDD = ''

      this.Kh.cboHTTT = this.HINHTHUC_TT.list[0].HTTT_ID
      this.Kh.cboDonViQL = DONVIQL_TT.list[0].DONVI_ID
      this.Kh.cboNganHang_val = this.NganHangList[0].NGANHANG_ID

      //diachi bc
      this.Kh.txtDiaChiBC = ''
      this.tinhbc_id = 0
      this.quanbc_id = 0
      this.phuongbc_id = 0
      this.phobc_id = 0
      this.apbc_id = 0
      this.khubc_id = 0
      this.dacdiembc_id = 0
      this.sonhabc = ''
      //

      this.hdtb_id = 0
      this.hdtb_id_cha = 0
      this.thuebao_id = 0
      this.thuebao_id_cha = 0
      this.Tb.txtTenTB = ''
      this.Tb.txtDiaChiTB = ''
      this.Tb.txtMaTB = ''
      this.Tb.txtNe = ''
      this.Tb.txtPort = ''
      this.Tb.txtVPIADSL = ''
      this.tinhtb_id = 0
      this.quantb_id = 0
      this.phuongtb_id = 0
      this.photb_id = 0
      this.aptb_id = 0
      this.sonhatb = ''
      //lvDvgt.Items.Clear()
      //lvDvkhac.Items.Clear()
      //cboLoaihinhTB.SelectedIndex = 0;
      if (this.this.kieuyc_list != null && this.kieuyc_list.length > 0) this.Tb_cboKieuLD = this.kieuyc_list[0].kieuld_id // this.KIEUYC.list[0].kieuld_id
      this.Tb.cboDoituongTB = this.DOITUONG.list[0].DOITUONG_ID
      //Hiếu bổ sung ngày 29/08/2010
      this.Tb.cboThoiHan = this.ThoiHanTSL.list[0].THOIHAN_ID
      this.Tb.cboTrangBi = this.TrangBiTSL.list[0].TRANGBI_ID
      this.Tb.cboChuQuan = this.ChuQuanTSL.list[0].CHUQUAN_ID
      ///cboLoaiModemTLS.SelectedIndex = 0;
      if (this.LoaiTBTSL.list.length > 0) this.Tb.cboLoaiTBTSL = this.LoaiTBTSL.list[0].THIETBIDC_ID
      kieu = 0
      ///TuấnNA thêm code clear các object trên form --- Ngày 04/09/2010
      this.Tb.cboDichVuVT = this.COMBO_DVVT.list[0].DICHVUVT_ID
      this.Tb_cboDichVuVT = this.COMBO_DVVT.list[0].DICHVUVT_ID
      this.Tb.txtDoiCap = ''
      this.Tb.txtCuLy = ''
      this.Tb.txtIPADSL = ''
      //txtIPWan = "";
      this.Kh.txtNguoiDD = ''
      this.Kh.txtDiaChiNDD = ''
      this.Kh.txtDiaChiCT = ''
      this.Kh.txtMaNVTC = ''
      this.Kh.txtMaTuyen = ''
      this.Kh.dtpNgayYeuCau = moment().format('DD/MM/YYYY')
      this.Kh.txtDiaChiLD = ''
      this.Tb.dtpNgayBG = moment().format('DD/MM/YYYY')
      this.Tb.dtpNgayBGCheck = false
      this.Tb.txtmaTN_Wan = ''
      this.Tb.txtmadoicap_cd = ''
      this.Tb.txtmadoicap_adsl = ''
      this.Tb.txtmaTN_Wan = ''
      this.Tb.txtmadoicap_wan = ''

      this.Tb.txtNgaySNTB = ''
      this.Tb.txtHopCap = ''
      this.Tb.txtGhiChuTB = ''
      this.Kh.txtHoKhau = ''
      this.Kh.txtNoiCapHK = ''
      this.Kh.txtNgayCapHK = ''
      this.Kh.txtChucDanhDD = ''
      this.Kh.txtSoTKKH = ''
      this.Kh.cboNganHangKH_check = false
      this.Kh.txtDacTrung = ''
      this.Kh.txtGhiChuTT = ''

      this.Kh.txtGhiChuTT = ''
      this.Kh.txtNgayHetHan = ''
      this.Kh.txtGhiChuKH = ''
      this.Kh.txtMST = ''
      this.Kh.txtMaSTKH = ''
      this.Tb.txtLineTrang_CD = ''
      this.Tb.txtLineTrang_TSL = ''
      this.Tb.txtLineTrang_Wan = ''

      this.TNC1_ID = 0
      this.TNC2_ID = 0
      this.TNC3_ID = 0
      this.TNC4_ID = 0
      this.nganhnghe_id = 0
      /*
            dgvTienCK.DataSource = null;
            dgvTienCKGP.DataSource = null;
            dgvTienCKIMS.DataSource = null;
            dgvTienCK.Update();
            dgvTienCKGP.Update();
            dgvTienCKIMS.Update();

            dsHDTB_KV = new HDTB_KV_DATA();
            */
    },
    SetButton(kieu) {
      try {
        this.tsbtnNhapMoi_Enabled = false
        this.tsbtnGhiLai_Enabled = false
        this.tsbtnChuyenDB_Enabled = false
        this.tsbtnGiaoPhieu_Enabled = false
        this.tsbtnSuaDacTinh_Enabled = false
        this.tsbtnHuyBo_Enabled = false
        //tsbtnXoaTT_Enabled = false;
        //tsbtnThemTT_Enabled = false;
        if (kieu == -1) {
          //Bat dau
          this.$refs.txtMaGD.focus()
          this.tsbtnNhapMoi_Enabled = true
        }
        if (kieu == 0) {
          //Bat dau
          this.$refs.txtMaGD.focus()
          this.tsbtnNhapMoi_Enabled = true
          this.Clear()
        }
        if (kieu == 1) {
          //Them moi
          this.$refs.txtMaGD.focus()
          this.tsbtnNhapMoi_Enabled = true
          this.Clear()
        }
        if (kieu == 2) {
          //Huy
          this.$refs.txtMaGD.focus()
          this.tsbtnNhapMoi_Enabled = true
          this.tsbtnChuyenDB_Enabled = true
          this.Clear()
        }
        if (kieu == 3) {
          //Edit
          this.$refs.txtMaGD.focus()
          this.tsbtnNhapMoi_Enabled = true
          this.tsbtnChuyenDB_Enabled = true
          //tsbtnGiaoPhieu_Enabled = true;
          this.tsbtnGhiLai_Enabled = true
          this.tsbtnHuyBo_Enabled = true
          this.tsbtnSuaDacTinh_Enabled = true
          if (this.kieugoi_id == KIEUGOI_CN_HOPDONG.DA_THI_CONG_XONG) {
            //HungHM - 28/08/2010 - Nếu là hoàn thiện hồ sơ thì không cho sửa cái gì sất.
            ///TuấnNA : cho hiển thị nút ghi lại trong trường hợp hợp đồng không tồn tại thuê bao di động --- Ngày 23/09/2010
            //tsbtnGhiLai_Enabled = false;
            this.tsbtnThemTT_Enabled = false
            this.tsbtnXoaTT_Enabled = false
            this.tsbtnHuyBo_Enabled = false
            this.tsbtnNhapMoi_Enabled = false
          }
          if (this.Tb_cboLoaihinhTB == LoaiHinhTB.DIDONGTRATRUOC) {
            // vuth.hth them
            this.tsbtnChuyenDB = false
          }
        }
      } catch (err) {
        console.log('SetButton-err', err)
      }
    },
    HienThiTTHopDongKH: async function(data) {
      console.log('HienThiTTHopDongKH', data)
      try {
        if (data == null || data.length == 0 || data.DS == null || data.DS.length == 0) {
          this.SetButton(0)
          return
        }
        let kh = data.DS[0] // this.toLowerKey(abc)
        console.log('HienThiTTHopDongKH-lower', kh)
        this.hdkh_id = kh.HDKH_ID
        this.Kh.txtMaGD = kh.MA_GD
        this.Kh.txtMaHD = kh.MA_HD
        this.Kh.txtMaKH = kh.MA_KH
        if (kh.KHACHHANG_ID != '') this.khachhang_id = kh.KHACHHANG_ID
        this.Kh.txtTenKH = kh.TEN_KH
        this.Kh.txtDiaChiKH = kh.DIACHI_KH
        // this.HienThiTTDiaChi(data.DS_DIACHI,1)
        this.Kh.txtTelKH = kh.SO_DT
        this.Kh.txtFaxKH = kh.SO_FAX
        this.Kh.txtNguoiDD = kh.NGUOI_DD
        this.Kh.txtHoKhau = kh.HOKHAU
        if (kh.KHLON_ID != null && kh.KHLON_ID != '') {
          this.KhachHangLon.visible = true
          this.Kh.cboKhachHangLon = kh.KHLON_ID
        } else {
          this.KhachHangLon.visible = false
        }
        this.Kh.txtNgayCapHK = this.toDate(kh.NGAY_HK)
        if (kh.NGAY_SN != null && kh.NGAY_SN != '') {
          this.Kh.txtNgaySN = this.toDate(kh.NGAY_SN)
        }
        if (kh['kieutn_id'] == '3') this.txtITXN = 'Qua APP'
        else this.txtITXN = 'Không qua APP'
        this.Kh.dtpNgayLapHD = this.toDate(kh.NGAYLAP_HD)
        this.Kh.dtpNgayYeuCau = this.toDate(kh.NGAY_YC)
        this.Kh.txtSoGT = kh.SO_GT
        this.Kh.txtNgayCap = this.toDate(kh.NGAYCAP)
        this.Kh.cboGiayToKH = kh.LOAIGT_ID
        this.Kh.txtNoiCap = kh.NOICAP
        this.Kh.cboLoaiKH = kh.LOAIKH_ID
        if (kh.LOAIKH_ID != null && kh.LOAIKH_ID != '') this.Kh.cboLoaiKH = kh.LOAIKH_ID
        this.Kh.txtGhiChuKH = kh.GHICHU
        this.Kh.txtDacTrung = kh.DACTRUNG
        this.Kh.txtChucDanhDD = kh.CHUCDANH_DD
        if (kh.QUOCTICH_ID != null && kh.QUOCTICH_ID != '') this.Kh.cboQuocTich = kh.QUOCTICH_ID
        if (kh.DANTOC_ID != null && kh.DANTOC_ID != '') this.Kh.cboDanToc = kh.DANTOC_ID
        if (kh.GIOITINH != null && kh.GIOITINH != '') this.Kh.gioiTinh = kh.GIOITINH
        if (kh.NGANHNGHE_ID != null && kh.NGANHNGHE_ID != '') {
          this.Kh.cboNganhNgheLoc = kh.NGANHNGHE_ID
        }
        if (kh['TNC1_ID'] != null && kh['TNC1_ID'] != '') {
          this.TNC1_ID = parseInt(kh['TNC1_ID'])
          this.tieunganh.TNC1_ID = parseInt(kh['TNC1_ID'])
        }
        if (kh['TNC2_ID'] != null && kh['TNC2_ID'] != '') {
          this.TNC2_ID = parseInt(kh['TNC2_ID'])
          this.tieunganh.TNC2_ID = parseInt(kh['TNC2_ID'])
        }
        if (kh['TNC3_ID'] != null && kh['TNC3_ID'] != '') {
          this.TNC3_ID = parseInt(kh['TNC3_ID'])
          this.tieunganh.TNC2_ID = parseInt(kh['TNC2_ID'])
        }
        if (kh['TNC4_ID'] != null && kh['TNC4_ID'] != '') {
          this.TNC4_ID = parseInt(kh['TNC4_ID'])
          this.tieunganh.TNC2_ID = parseInt(kh['TNC2_ID'])
        }
        this.Kh.txtDiaChiNDD = kh.DIACHI_NDD
        if (kh.NGAYHETHAN_GT != '') this.Kh.txtNgayHetHan = this.toDate(kh.NGAYHETHAN_GT)
        this.Kh.txtMaSTKH = kh.MST
        this.Kh.txtSoTKKH = kh.STK
        if (kh.NGANHANG_ID != null && kh.NGANHANG_ID != '') this.Kh.cboNganHangKH_val = kh.NGANHANG_ID
        if (kh.CTV_ID != null && kh.CTV_ID != '') {
          this.txtCTVcheck = true
          this.ctv_id = kh.CTV_ID
        } else {
          this.txtCTVcheck = false
          this.txtCTV = ''
          this.ctv_id = 0
        }
        let nhanviengt_id = 0
        if (kh.NHANVIENGT_ID != null && kh.NHANVIENGT_ID != '' && kh.NHANVIENGT_ID > 0) {
          this.txtNguoiGTcheck = true
          nhanviengt_id = kh.NHANVIENGT_ID
        } else {
          this.txtNguoiGTcheck = false
          this.txtNguoiGT = ''
        }
        await this.HienThiDS_HDTT_CBO(this.hdkh_id)
        //test

        let dataTT = await this.fn_hienthi_tt_hd_khachhang_hths_datmmoi(this.hdkh_id, nhanviengt_id)
        await this.HienThiTTHopDongTT(dataTT)
        this.HienThiTTDiaChi(dataTT.DS_DIACHI, 1)
        // Hien thi thong tin thue bao
        await this.HienThiTTHopDongTB(dataTT)
        this.SetButton(3)
      } catch (err) {
        console.log('HienThiTTHopDongKH-err', err)
      }
    },
    async HienThiDS_HDTT_CBO(hdkh_id) {
      let data = await this.lay_ds_hdtt_theo_hdkh_id_1(hdkh_id)
      console.log('HienThiDS_HDTT_CBO', data)
      if (data != null && data.length > 0) {
        this.CboTT.list = data
        this.Tb.cboTT = data[0][this.CboTT.id]
      } else this.CboTT.list = []
    },
    NotNull(val) {
      try {
        if (val == null || val == '') {
          return false
        }
        return true
      } catch (err) {
        console.log('NotNull:Error', err)
        return false
      }
    },
    HienThiTTDiaChi(data, kieu) {
      console.log('HienThiTTDiaChi', data, kieu)
      if (data == null || data[0] == null) {
        console.log('🚀 ~ file: CompleteProfileBuySamsungTV.vue:2059 ~ HienThiTTDiaChi ~ null:', data, kieu)
        return
      }
      try {
        // item = data[0]
        let item = this.toLowerKey(data[0])
        console.log('HienThiTTDiaChi Lower', item)
        if (kieu == 1) {
          this.diachikh_id = item['diachi_id']
          this.tinhkh_id = item['tinh_id']
          this.quankh_id = item['quan_id']
          this.phuongkh_id = item['phuong_id']

          if (item['pho_id'] != null && item['pho_id'] != '') this.phokh_id = item['pho_id']
          else this.phokh_id = 0
          if (item['ap_id'] != null && item['ap_id'] != '') this.apkh_id = item['ap_id']
          else this.apkh_id = 0
          if (item['khu_id'] != null && item['khu_id'] != '') this.khukh_id = item['khu_id']
          else this.khukh_id = 0
          if (item['dacdiem_id'] != null && item['dacdiem_id'] != '') this.dacdiemkh_id = item['dacdiem_id']
          else this.dacdiemkh_id = 0

          this.sonhakh = item['sonha']
        }
        if (kieu == 2) {
          this.diachitt_id = item['diachi_id']
          this.tinhtt_id = item['tinh_id']
          this.quantt_id = item['quan_id']
          this.phuongtt_id = item['phuong_id']
          if (item['pho_id'] != null && item['pho_id'] != '') this.phott_id = item['pho_id']
          else this.phott_id = 0
          if (item['ap_id'] != null && item['ap_id'] != '') this.aptt_id = item['ap_id']
          else this.aptt_id = 0
          if (item['khu_id'] != null && item['khu_id'] != '') this.khutt_id = item['khu_id']
          else this.khutt_id = 0
          if (item['dacdiem_id'] != null && item['dacdiem_id'] != '') this.dacdiemtt_id = item['dacdiem_id']
          else this.dacdiemtt_id = 0
          this.sonhatt = item['sonha']
          //Địa chỉ báo cước
          if (item['diachibc_id'] != null && item['diachibc_id'] != '') this.diachibc_id = item['diachibc_id']
          else this.diachibc_id = 0
          if (item['tinhbc_id'] != null && item['tinhbc_id'] != '') this.tinhbc_id = item['tinhbc_id']
          else this.tinhbc_id = 0
          if (item['quanbc_id'] != null && item['quanbc_id'] != '') this.quanbc_id = item['quanbc_id']
          else this.quanbc_id = 0
          if (item['phuongbc_id'] != null && item['phuongbc_id'] != '') this.phuongbc_id = item['phuongbc_id']
          else this.phuongbc_id = 0
          if (item['phobc_id'] != null && item['phobc_id'] != '') this.phobc_id = item['phobc_id']
          else this.phobc_id = 0
          if (item['apbc_id'] != null && item['apbc_id'] != '') this.apbc_id = item['apbc_id']
          else this.apbc_id = 0
          if (item['khubc_id'] != null && item['khubc_id'] != '') this.khubc_id = item['khubc_id']
          else this.khubc_id = 0
          if (item['dacdiembc_id'] != null && item['dacdiembc_id'] != '') this.dacdiembc_id = item['dacdiembc_id']
          else this.dacdiembc_id = 0
          this.sonhabc = item['sonha_bc']
        }
        if (kieu == 3) {
          this.diachitb_id = item['diachi_id']
          this.tinhtb_id = item['tinh_id']
          this.quantb_id = item['quan_id']
          this.phuongtb_id = item['phuong_id']
          if (item['pho_id'] != null && item['pho_id'] != '') this.photb_id = item['pho_id']
          else this.photb_id = 0
          if (item['ap_id'] != null && item['ap_id'] != '') this.aptb_id = item['ap_id']
          else this.aptb_id = 0
          if (item['khu_id'] != null && item['khu_id'] != '') this.khutb_id = item['khu_id']
          else this.khutb_id = 0
          if (item['dacdiem_id'] != null && item['dacdiem_id'] != '') this.dacdiemtb_id = item['dacdiem_id']
          else this.dacdiemtb_id = 0
          this.sonhatb = item['sonha']
          /*
                    if (ds.Tables[0].Columns.Contains("block"))
                        this.blocktb = item["block"]
                    if (ds.Tables[0].Columns.Contains("tang"))
                        this.tangtb = item["tang"]
                    if (ds.Tables[0].Columns.Contains("phong"))
                        this.sophongtb = item["phong"]
                    if (ds.Tables[0].Columns.Contains("mota"))
                       this. motadctb = item["mota"]
        */
          //Địa chỉ lắp đặt
          this.diachild_id = item['diachild_id']
          this.tinhld_id = item['tinhld_id']
          this.quanld_id = item['quanld_id']
          this.phuongld_id = item['phuongld_id']
          if (this.NotNull(item['phold_id'])) this.phold_id = item['phold_id']
          else this.phold_id = 0
          if (this.NotNull(item['apld_id'])) this.apld_id = item['apld_id']
          else this.apld_id = 0

          if (this.NotNull(item['khuld_id'])) this.khuld_id = item['khuld_id']
          else this.khuld_id = 0

          if (this.NotNull(item['dacdiemld_id'])) this.dacdiemld_id = item['dacdiemld_id']
          else this.dacdiemld_id = 0
          this.sonhald = item['sonha_ld']
          /*
                    if (ds.Tables[0].Columns.Contains("block_ld"))
                        blockld = item["block_ld"]
                    if (ds.Tables[0].Columns.Contains("tang_ld"))
                        tangld = item["tang_ld"]
                    if (ds.Tables[0].Columns.Contains("phong_ld"))
                        sophongld = item["phong_ld"]
                    if (ds.Tables[0].Columns.Contains("mota_ld"))
                        motadcld = item["mota_ld"]
                     */
        }
      } catch (ex) {
        console.log('diachi', ex)
      }
    },
    HienThiDanhSacHDTT(hdkh_id) {},
    HienThiDanhSacHDTB(hdkh_id, tthd_id) {},
    async HienThiTTHopDongTT(dataTT) {
      this.tuyenthu_id = 0
      if (dataTT.DS_HDTT != null && dataTT.DS_HDTT.length > 0) {
        //
        let dshdTT = dataTT.DS_HDTT
        this.hdtt_id = dshdTT[0].HDTT_ID
        this.Kh.txtMaTT = dshdTT[0].MA_TT
        this.thanhtoan_id = dshdTT[0].THANHTOAN_ID
        this.tuyenthu_id = dshdTT[0].TUYENTHU_ID
        this.Kh.txtTenTT = dshdTT[0].TEN_TT
        this.Kh.txtDiaChiTT = dshdTT[0].DIACHI_TT
        this.Kh.txtDiaChiBC = dshdTT[0].DIACHI_BC
        this.diachiBC.DIACHI = dshdTT[0].DIACHI_BC
        this.Kh.txtDiaChiCT = dshdTT[0].DIACHI_CT
        this.HienThiTTDiaChi(dataTT.DS_DIACHI, 2)
        this.Kh.txtChu_TK = dshdTT[0].CHU_TK
        this.Kh.txtSTK = dshdTT[0].STK
        this.Kh.txtGhiChuTT = dshdTT[0].GHICHU
        this.Kh.txtMST = dshdTT[0].MST
        if (dshdTT[0].NGANHANG_ID != null && dshdTT[0].NGANHANG_ID != '') {
          this.Kh.cboNganHangKH_val = dshdTT[0].NGANHANG_ID
        }
        this.Kh.cboHTTT = dshdTT[0].HTTT_ID
        this.Kh.cboDonViQL = dshdTT[0].DONVI_ID
        this.Kh.txtMaTBDD = dshdTT[0].MATT_DD

        if (this.NotNull(dshdTT[0]['TIEN_DM'])) {
          this.nganhang.TIEN_DM = dshdTT[0]['TIEN_DM']
        }
        this.nganhang.DV_QHNS = dshdTT[0]['DV_QHNS']
        this.nganhang.MACHUONG = dshdTT[0]['MACHUONG']
        this.nganhang.MN_KT = dshdTT[0]['MN_KT']
        this.nganhang.MA_NDKT = dshdTT[0]['MA_NDKT']
        this.nganhang.MN_NSNN = dshdTT[0]['MN_NSN']
        let abc = await this.fn_hienthi_tt_hd_thanhtoan_hths_datmmoi(this.hdtb_id, this.tuyenthu_id)
        console.log(abc, abc)
        let dsDK_DVK_TT = abc.DVK
        this.lvDvkhac_TT.list = dsDK_DVK_TT
        this.HT_DichVuKhac(dsDK_DVK_TT, 2)
      }
    },
    // Hien thi tab thue bao
    async HienThiTTHopDongTB(dataTB) {
      console.log('Go HienThiTTHopDongTB', dataTB)
      let dshdTB = dataTB.DS_HDTB[0]
      this.hdtb_id = dshdTB.HDTB_ID
      if (dshdTB.THUEBAO_ID != null && dshdTB.THUEBAO_ID != '') this.thuebao_id = dshdTB.THUEBAO_ID
      else this.thuebao_id = 0

      this.Tb.txtTenTB = dshdTB.TEN_TB
      this.Tb.txtDiaChiTB = dshdTB.DIACHI_TB
      this.Tb.txtDiaChiLD = dshdTB.DIACHI_LD
      this.Tb.txtNgaySNTB = this.toDate(dshdTB.NGAY_SN)

      this.Tb_cboLoaihinhTB = dshdTB.LOAITB_ID
      this.Tb.cboLoaihinhTB = dshdTB.LOAITB_ID
      //
      console.log('HienThiTTHopDongTB:Tb_cboLoaihinhTB', this.Tb_cboLoaihinhTB)
      await this.cboLoaihinhTB_SelectedValueChanged(dshdTB.LOAITB_ID)
      if (this.LOAIHINH_TB.list.filter((x) => x.LOAITB_ID == dshdTB.LOAITB_ID).length > 0) {
        let dvvtId = this.LOAIHINH_TB.list.filter((x) => x.LOAITB_ID == dshdTB.LOAITB_ID)[0].DICHVUVT_ID
        this.LOAIHINH_TB.data = this.LOAIHINH_TB.list.filter((x) => x.DICHVUVT_ID == dvvtId)
        this.dichvuvt_id = dvvtId // this.LOAIHINH_TB.list.filter((x) => x.LOAITB_ID == dshdTB.LOAITB_ID)[0].DICHVUVT_ID
        if (this.dichvuvt_id > 0) {
          this.Tb_cboDichVuVT = this.dichvuvt_id
          this.Tb.cboDichVuVT = this.dichvuvt_id
          // await this.cboDichVuVT_SelectedValueChanged(this.dichvuvt_id)
        }
      }
      this.Tb.cboDoituongTB = dshdTB.DOITUONG_ID
      this.Tb.txtMaTB = dshdTB.MA_TB
      if (dshdTB.MUCUOC_TB != null && dshdTB.MUCUOC_TB != '') this.Tb.cboMucCuoc = dshdTB.MUCUOC_TB
      console.log('dshdTB.MUCCUOCTB_ID', dshdTB.MUCUOCTB_ID)
      // dshdTB.MUCCUOCTB_ID = 231
      if (dshdTB.MUCUOCTB_ID != null && dshdTB.MUCUOCTB_ID != '') {
        await this.HT_MucCuocTB_Combobox(dshdTB.MUCUOCTB_ID)
        this.Tb.cboMucCuocTB = dshdTB.MUCUOCTB_ID
      }
      if (dshdTB.DONVI_ID != null && dshdTB.DONVI_ID > 0) {
        this.Tb.cboDaiVT = dshdTB.DONVI_ID
        this.Tb_cboDaiVT = dshdTB.DONVI_ID
        await this.cboDaiVT_SelectedValueChanged(dshdTB.DONVI_ID)
      } else {
        this.Tb_cboDaiVT = ''
        this.Tb.cboDaiVT = ''
      }
      if (this.kieugoi_id != 200) {
        if (dshdTB['HDTB_CHA_ID'] != null && dshdTB['HDTB_CHA_ID'] != '') {
          //console.log('dshdTB[HDTB_CHA_ID]',dshdTB['HDTB_CHA_ID'])
          this.hdtb_id_cha = parseInt(dshdTB['HDTB_CHA_ID'])
        } else this.hdtb_id_cha = 0
        if (dshdTB['THUEBAO_CHA_ID'] != null && dshdTB['THUEBAO_CHA_ID'] != '') this.thuebao_id_cha = parseInt(dshdTB['THUEBAO_CHA_ID'])
        else this.thuebao_id_cha = 0
      }
      this.doituong_id = parseInt(dshdTB['DOITUONG_ID'])
      this.ma_tb = dshdTB['MA_TB']
      if (this.NotNull(dshdTB['NGAY_HT'])) {
        this.Tb.dtpNgayBG = this.toDate(dshdTB['NGAY_HT'])
      } else {
      }

      // await this.ds_kieu_ld(this.Tb_cboLoaihinhTB)
      try {
        console.log("dshdTB['KIEULD_ID']", dshdTB['KIEULD_ID'])
        if (dshdTB['KIEULD_ID'] != null) {
          let valLd = dshdTB['KIEULD_ID']
          if (this.kieuyc_list.filter((x) => x.kieuld_id == valLd).length > 0) {
            this.Tb_cboKieuLD = valLd
          } else {
            if (this.kieuyc_list_full.filter((x) => x.kieuld_id == valLd).length > 0) {
              let item = this.kieuyc_list_full.filter((x) => x.kieuld_id == valLd)[0]
              this.kieuyc_list.push(item)
              this.Tb_cboKieuLD = valLd
            }
          }
        }
        console.log("cboKieuLDerr: dshdTB['KIEULD_ID']", dshdTB['KIEULD_ID'])
      } catch (cboKieuLDerr) {
        console.log('cboKieuLDerr:', cboKieuLDerr)
      }

      this.GiaoDien_DichVuVT(this.dichvuvt_id)
      let dataHDTB = await this.fn_hienthi_tt_hd_thuebao_hths_datmmoi(this.hdtb_id, this.thuebao_id, dshdTB.LOAITB_ID, dshdTB.MUCCUOCTB_ID)
      if (dataHDTB != null) {
        // this.MUCCUOC.list = dataHDTB.DS_MUCCUOC
        // this.MUCCUOCTB.list = dataHDTB.DS_MUCCUOC
        this.HienThiTTDiaChi(dataHDTB.DS_DIACHI, 3)
      }
      this.HT_DichVuGT(dataHDTB)
      await this.HienThiTTMoRong()
      let dsDK_DVK_TT = dataHDTB.DVK_TB
      this.lvDvkhac_TB.list = dsDK_DVK_TT
      this.HT_DichVuKhac(dsDK_DVK_TT, 1)
    },
    HT_DichVuGT: function(data) {
      try {
        let dvct = data.DS_TB_DICHVU
        if (dvct != null && dvct.length > 0) {
          let dv = dvct.filter((x) => x.HUY == 1 || x.DANGKY == 1)
          dv.forEach((e) => {
            if (e.DANGKY == 1) e.TEN_DVGT = e.TEN_DVGT + ' (ĐK)'
            else e.TEN_DVGT = e.TEN_DVGT + ' (HUY)'
          })
          this.lvDvGT.list = dv
        } else {
          this.lvDvGT.list = []
        }
      } catch (err) {
        console.log('🚀 ~ file: CompleteProfileBuySamsungTV.vue:2430 ~ err:', err)
      }
    },
    ht_donvi_loaidonvi: async function(donvi_id, loai1, loai2) {
      let donvi = []
      let input = {
        donvi_id: donvi_id,
        loaidv_id: loai1
      }
      let data1 = this.GetData(await api.ht_donvi_loaidonvi(this.axios, input))
      if (data1 != null) data1.forEach((x) => donvi.push(x))

      input.loaidv_id = loai2
      let data2 = this.GetData(await api.ht_donvi_loaidonvi(this.axios, input))
      if (data2 != null) data2.forEach((x) => donvi.push(x))
      return donvi
    },
    HT_GoiCuoc_DD_Combobox: async function(loaitb_id) {
      // let input = {
      //   schema: 'css',
      //   list_of_cols: 'goicuoc_id,tengoicuoc',
      //   order: 'goicuoc_id',
      //   table_name: 'goicuoc_dd',
      //   where: `loaitb_id =${loaitb_id}`
      // }
      // let data = this.GetData(await api.lay_dulieu_bang_theo_dieukien(this.axios, input))
      let data = this.GetData(await api.sp_tt_goicuoc_dd(this.axios, { param: loaitb_id, type: 1 }))
      this.GoiCuocDD.list = data.map((x) => ({ GOICUOC_ID: x.goicuoc_id, TENGOICUOC: x.tengoicuoc }))
      this.GoiCuocDD.id = 'GOICUOC_ID'
      this.GoiCuocDD.id = 'TENGOICUOC'
    },
    HT_Tocdo_Adsl_Combobox_ByLoaiTB: async function(loaitb_id) {
      // let input = {
      //   schema: 'css',
      //   list_of_cols: 'TOCDO_ID,THUONGHIEU',
      //   order: 'tocdo_id',
      //   table_name: 'tocdo_adsl',
      //   where: `loaitb_id =${loaitb_id} and matocdo is not null`
      // }
      // let data = this.GetData(await api.lay_dulieu_bang_theo_dieukien(this.axios, input))

      let data = this.GetData(await api.sp_tt_tocdo_adsl(this.axios, { param: loaitb_id, type: 1 }))
      this.GoiCuocAdsl.list = data.map((x = { TOCDO_ID: x.tocdo_id, THUONGHIEU: x.thuonghieu }))
      this.GoiCuocAdsl.id = 'TOCDO_ID'
      this.GoiCuocAdsl.text = 'THUONGHIEU'
    },
    HT_MucCuocTB_Combobox: async function(mucuoctb_id) {
      // let input = {
      //   schema: 'css',
      //   list_of_cols: `MUCUOCTB_ID,MUCCUOC,CUOC_TG,CUOC_TB`,
      //   order: 'mucuoctb_id',
      //   table_name: 'muccuoc_tb',
      //   where: `mucuoctb_id =${mucuoctb_id} `
      // }
      // let data = this.GetData(await api.lay_dulieu_bang_theo_dieukien(this.axios, input))
      let data = this.GetData(await api.sp_tt_muccuoc_tb(this.axios, { param: mucuoctb_id, type: 1 }))

      data = data.map((x) => ({ MUCUOCTB_ID: x.mucuoctb_id, TENMUCCUOC: x.muccuoc + ' - ' + (x.mucuoctb_id == 1 ? x.cuoc_tg : x.cuoc_tb) + ' VND' }))
      console.log('HT_MucCuocTB_Combobox', data)
      this.MUCCUOCTB.list = data
      this.MUCCUOCTB.id = 'MUCUOCTB_ID'
      this.MUCCUOCTB.text = 'TENMUCCUOC'
    },
    HT_Loai_Kenh_Combobox: async function() {
      // chua co api moi
      // let input = {
      //   schema: 'css',
      //   list_of_cols: `loaikenh_id,loai_kenh`,
      //   order: 'loaikenh_id',
      //   table_name: 'loai_kenh',
      //   where: ``
      // }
      // let data = this.GetData(await api.lay_dulieu_bang_theo_dieukien(this.axios, input))
      let data = this.GetData(await api.sp_tt_loai_kenh(this.axios, { param: 1, type: 1 }))
      //console.log('HT_Loai_Kenh_Combobox',data)
      this.LoaiKenhMgwan.list = data.map((x) => ({ LOAIKENH_ID: x.loaikenh_id, LOAI_KENH: x.loai_kenh }))
      this.LoaiKenhMgwan.id = 'LOAIKENH_ID'
      this.LoaiKenhMgwan.id = 'LOAI_KENH'
    },
    HT_Tocdo_Kenh_Combobox: async function() {
      // let input = {
      //   schema: 'css',
      //   list_of_cols: `tocdo_id,tocdo,donvi`,
      //   order: 'tocdo',
      //   table_name: 'tocdo_kenh',
      //   where: ``
      // }
      // let data = this.GetData(await api.lay_dulieu_bang_theo_dieukien(this.axios, input))
      let data = this.GetData(await api.sp_tt_tocdo_kenh(this.axios, { param: 1, type: 1 }))
      //console.log('HT_Tocdo_Kenh_Combobox',data)
      this.TocDoMEGAWAN.list = data.map((x) => ({ TOCDO_ID: x.tocdo_id, TOCDO: x.tocdo + '  ' + x.donvi }))
      this.TocDoMEGAWAN.id = 'TOCDO_ID'
      this.TocDoMEGAWAN.text = 'TOCDO'
    },
    HT_Loai_Modem_Combobox: async function() {
      // let input = {
      //   schema: 'css',
      //   list_of_cols: `loaimd_id,loai_md`,
      //   order: 'loaimd_id',
      //   table_name: 'loai_modem',
      //   where: ``
      // }
      // let data = this.GetData(await api.lay_dulieu_bang_theo_dieukien(this.axios, input))
      let data = this.GetData(await api.sp_tt_loai_kenh(this.axios, { param: 1, type: 1 }))
      //console.log('HT_Loai_Modem_Combobox',data)
      this.LoaiModemMgwan.list = data.map((x) => ({ LOAIMD_ID: x.loaimd_id, LOAI_MD: x.loai_md }))
      this.LoaiModemMgwan.id = 'LOAIMD_ID'
      this.LoaiModemMgwan.text = 'LOAI_MD'
    },
    HT_TrangBi_MegaWan_Combobox: async function() {
      // let input = {
      //   schema: 'css',
      //   list_of_cols: `trangbi_id,tentrangbi`,
      //   order: 'trangbi_id',
      //   table_name: 'trangbi',
      //   where: ``
      // }
      // let data = this.GetData(await api.lay_dulieu_bang_theo_dieukien(this.axios, input))
      let data = this.GetData(await api.sp_tt_trangbi(this.axios, { param: 1, type: 1 }))
      //console.log('HT_Loai_Modem_Combobox',data)
      this.TrangBiMEGAWAN.list = data.map((x) => ({ TRANGBI_ID: x.trangbi_id, TENTRANGBI: x.tentrangbi }))
      this.TrangBiMEGAWAN.id = 'TRANGBI_ID'
      this.TrangBiMEGAWAN.text = 'TENTRANGBI'
      this.TrangBiIMS.list = data.map((x) => ({ TRANGBI_ID: x.trangbi_id, TENTRANGBI: x.tentrangbi }))
      this.TrangBiIMS.id = 'TRANGBI_ID'
      this.TrangBiIMS.text = 'TENTRANGBI'
    },
    HT_TrangBi_TSL_Combobox: async function() {},
    HienThiTTMoRong: async function() {
      console.log('HienThiTTMoRong:')
      let dataMR = await this.fn_ht_tt_morong_hths_datmmoi()
      console.log('HienThiTTMoRong-dichvuvt_id', this.dichvuvt_id, dataMR)
      switch (this.dichvuvt_id) {
        case DichVuVienThong.CO_DINH:
          break
        case DichVuVienThong.DI_DONG:
          break
        case DichVuVienThong.IMS:
          break
        case DichVuVienThong.ADSL:
          break
        case DichVuVienThong.MEGAWAN:
        case DichVuVienThong.METRONET:
          break
        case DichVuVienThong.TSL:
          break
        case DichVuVienThong.GPHONE:
          break
        case DichVuVienThong.DICHVU_CNTT:
        case DichVuVienThong.ANTOAN_BAOMAT_TT:
        case DichVuVienThong.TRUNGTAM_DULIEU:
        case DichVuVienThong.HOINGHI_TRUYENHINH:
          break
        case 50:
          break
        default:
        // code block
      }
      if (dataMR != null && dataMR.DS_MORONG != null && dataMR.DS_MORONG.length > 0) {
        switch (this.dichvuvt_id) {
          case DichVuVienThong.CO_DINH:
            this.Tb.txtMaTB = this.ma_tb
            this.Tb.txtNe = dataMR.DS_MORONG[0]['NE']
            this.Tb.txtHopCap = dataMR.DS_MORONG[0]['HOPCAP_P']
            this.Tb.txtDoiCap = dataMR.DS_MORONG[0]['DOICAP_P']
            this.Tb.txtCuLy = dataMR.DS_MORONG[0]['CULY']
            this.Tb.ne = this.Tb.txtNe
            this.Tb.chkVSVX_Checked = dataMR.DS_MORONG[0]['MC_TBCD_ID'] == '1'
            this.Tb.chkMienCuocTB_Checked = dataMR.DS_MORONG[0]['MIENCUOC_TB'] == '1'
            if (dataMR.VETINH_CD != null) this.Tb.cboVeTinhCD = dataMR.VETINH_CD
            if (dataMR.CD_HDTB_DV != null) {
              this.Tb.cboHost = dataMR.CD_HDTB_DV.DONVI_ID
            }
            //Hiếu bổ sung 1 sô thông tin 23.09.2010
            this.Tb.txtmaTN_CD = dataMR.DS_MORONG[0]['MATB_TN']
            this.Tb.txtmadoicap_cd = dataMR.DS_MORONG[0]['MADOICAP']
            this.Tb.txtLineTrang_CD = dataMR.DS_MORONG[0]['MA_LT']
            if (dataMR.DS_MORONG[0]['TRAMTB_ID'] != '') this.Tb.cboTramTB_CD = parseInt(dataMR.DS_MORONG[0]['TRAMTB_ID'])
            else this.Tb.cboTramTB_CD = ''
            this.Tb.trangbi_id = parseInt(dataMR.DS_MORONG[0]['TRANGBI_ID'])
            this.CoDinhSoDep.data = dataMR.DS_TIEN_CAMKET
            break
          case DichVuVienThong.DI_DONG:
            this.Tb.txtMaTB = this.ma_tb
            this.Tb.txtSimDD = dataMR.DS_MORONG[0]['SIMCARD']
            //if (dataMR.DS_MORONG[0]["goicuoc_id"]!="")
            //bangts.HT_GoiCuoc_DD_Combobox(cboGoiCuocDD, parseInt(cboLoaihinhTB))
            this.Tb.cboGoiCuocDD = dataMR.DS_MORONG[0]['GOICUOC_ID']
            this.Tb.txtTienDuTK = dataMR.DS_MORONG[0]['TIEN_DTK']
            this.Tb.trangbi_id = parseInt(dataMR.DS_MORONG[0]['TRANGBI_ID'])
            break
          case DichVuVienThong.IMS:
            this.Tb.txtMaTB = this.ma_tb
            this.Tb.trangbi_id = parseInt(dataMR.DS_MORONG[0]['TRANGBI_ID'])
            this.Tb.cboTrangBiIMS = parseInt(dataMR.DS_MORONG[0]['TRANGBI_ID'])
            if (dataMR.DS_MORONG[0]['TRAMTB_ID'] != '') this.Tb.cboVeTinhIMS = parseInt(dataMR.DS_MORONG[0]['TRAMTB_ID'])
            this.IMSSoDep.data = dataMR.DS_TIEN_CAMKET
            break
          case DichVuVienThong.ADSL:
            this.Tb.trangbi_id = parseInt(dataMR.DS_MORONG[0]['TRANGBI_ID'])
            this.Tb.txtMaTB = this.ma_tb
            if (dataMR.DS_MORONG[0]['PORT_ID'] != '') this.Tb.port_id = parseInt(dataMR.DS_MORONG[0]['PORT_ID'])
            else this.Tb.port_id = 0
            this.Tb.port_id_ht = this.Tb.port_id
            this.Tb.txtMaTB = this.ma_tb

            if (dataMR.DS_MORONG[0]['TRAMTB_ID'] != '') this.Tb.cboTramTB_ADSL = parseInt(dataMR.DS_MORONG[0]['TRAMTB_ID'])
            else this.Tb.cboTramTB_ADSL = ''

            //TuanNA: Bổ sung code sửa HĐ Lắp mới -- 04.12.2012
            this.Tb.txtSoMayTNAdsl = dataMR.DS_MORONG[0]['SO_DT']
            this.Tb.txtEmailAdsl = dataMR.DS_MORONG[0]['EMAIL']

            this.Tb.cboGoiCuocAdsl = parseInt(dataMR.DS_MORONG[0]['TOCDO_ID'])
            this.Tb.cboMucCuoc = parseInt(dataMR.DS_MORONG[0]['MUCCUOC_ID'])
            this.Tb.cboLoaiTB = parseInt(dataMR.DS_MORONG[0]['THIETBIDC_ID'])
            this.Tb.txtHopCapADSL = dataMR.DS_MORONG[0]['HOPCAP_P']
            this.Tb.txtDoiCapADSL = dataMR.DS_MORONG[0]['DOICAP_P']
            this.Tb.txtCuLyADSL = dataMR.DS_MORONG[0]['CULY']
            this.Tb.txtPort = dataMR.DS_MORONG[0]['PORT']

            //Hiếu bổ sung hiển thị thông tin lên các ô text ADSL 24/08/2010
            this.Tb.txtmadoicap_adsl = dataMR.DS_MORONG[0]['MADOICAP']
            this.Tb.txtmaTN_ADSL = dataMR.DS_MORONG[0]['MATB_TN']
            this.Tb.txtMatKhauADSL = dataMR.DS_MORONG[0]['PASSWORD']
            this.Tb.txtCuLyADSL = dataMR.DS_MORONG[0]['CULY']
            if (dataMR.DS_MORONG[0]['BRAS_ID'] != '') {
              this.Tb.cboBrasADSL = dataMR.DS_MORONG[0]['BRAS_ID']
              await this.lay_ds_dslam(dataMR.DS_MORONG[0]['BRAS_ID'])
            }
            if (dataMR.DS_MORONG[0]['DSLAM_ID'] != null && dataMR.DS_MORONG[0]['DSLAM_ID'] != '') {
              this.Tb.cboDSLAM = dataMR.DS_MORONG[0]['DSLAM_ID']
            }
            this.Tb.txtIPADSL = dataMR.DS_MORONG[0]['IP']
            this.Tb.txtSlotADSL = dataMR.DS_MORONG[0]['SLOT']
            this.Tb.txtPort = dataMR.DS_MORONG[0]['PORT']
            this.Tb.txtVPIADSL = dataMR.DS_MORONG[0]['VPI']
            this.Tb.txtVCIADSL = dataMR.DS_MORONG[0]['VCI']
            this.Tb.txtSystem.Text = dataMR.DS_MORONG[0]['SYSTEM']
            this.Tb.txtRack.Text = dataMR.DS_MORONG[0]['RACK']
            this.Tb.txtShelf.Text = dataMR.DS_MORONG[0]['SHELF']
            this.Tb.txtPort_mdf = dataMR.DS_MORONG[0]['PORT_MDF']
            // Tb.cboVeTinhAdsl
            //  {
            //                     DataTable dtTemp = bangts.GetDataSql("select hdtb_id, donvi_id, loaidv_id from " + DatabaseConstants.DB2 + ".hdtb_dv where hdtb_id =" + hdtb_id);                                //Trạm:
            //                     DataRow[] dr = dtTemp.Select("loaidv_id = " + LOAI_DV.TRAM_VT);
            //                     if (dr.Length > 0)
            //                     {
            //                         cboVeTinhAdsl.SelectedValue = dr[0]["donvi_id"];
            //                     }
            //                 }
            let dtTemp = await this.lay_hdtb_id_donvi_id_loaidv_id(this.hdtb_id)
            if (dtTemp != null && dtTemp.length > 0) {
              this.Tb.cboVeTinhAdsl = dtTemp[0].donvi_id
              await this.cboVeTinhAdsl_SelectedValueChanged(dtTemp[0].donvi_id)
            }

            break
          case DichVuVienThong.MEGAWAN:
          case DichVuVienThong.METRONET:
            this.Tb.trangbi_id = parseInt(dataMR.DS_MORONG[0]['TRANGBI_ID'])
            this.Tb.txtMaTB = this.ma_tb
            this.Tb.txtHuongKetNoiMEGAWAN = dataMR.DS_MORONG[0]['HUONG_KN']
            this.Tb.txtLineTrang_Wan = dataMR.DS_MORONG[0]['MA_LT']
            this.Tb.txtmadoicap_wan = dataMR.DS_MORONG[0]['MADOICAP']
            this.Tb.txtmaTN_Wan = dataMR.DS_MORONG[0]['MATB_TN']
            this.Tb.cboTocDo = parseInt(dataMR.DS_MORONG[0]['TOCDO_ID'])
            this.Tb.cboLoaiModem = parseInt(dataMR.DS_MORONG[0]['LOAIMD_ID'])
            this.Tb.cboLoaiKenh = parseInt(dataMR.DS_MORONG[0]['LOAIKENH_ID'])
            this.Tb.cboLoaiTBiWan = parseInt(dataMR.DS_MORONG[0]['THIETBIDC_ID'])
            this.Tb.cboChuQuanMEGAWAN = parseInt(dataMR.DS_MORONG[0]['CHUQUAN_ID'])
            this.Tb.cboThoiHanMEGAWAN = parseInt(dataMR.DS_MORONG[0]['THOIHAN_ID'])
            this.Tb.cboTrangBiMEGAWAN = parseInt(dataMR.DS_MORONG[0]['TRANGBI_ID'])
            this.Tb.txtWAN_IP = dataMR.DS_MORONG[0]['WAN_IP']
            this.Tb.txtLan_IP = dataMR.DS_MORONG[0]['LAN_IP']
            this.Tb.txtDSLAMWan = dataMR.DS_MORONG[0]['DSLAM']
            this.Tb.txtPortWan = dataMR.DS_MORONG[0]['PORT']
            this.Tb.txtTenTbi = dataMR.DS_MORONG[0]['TEN_TBI']
            this.txtVpiWan = dataMR.DS_MORONG[0]['VPI']
            this.txtVciWan = dataMR.DS_MORONG[0]['VCI']
            break
          case DichVuVienThong.TSL:
            this.Tb.trangbi_id = parseInt(dataMR.DS_MORONG[0]['TRANGBI_ID'])
            this.Tb.txtMaTB = this.ma_tb
            this.Tb.cboTocDoKenhTSL = parseInt(dataMR.DS_MORONG[0]['TOCDO_ID'])
            this.Tb.cboLoaiKenhTSL = parseInt(dataMR.DS_MORONG[0]['LOAIKENH_ID'])
            this.Tb.cboThoiHan = dataMR.DS_MORONG[0]['THOIHAN_ID']
            this.Tb.cboTrangBi = dataMR.DS_MORONG[0]['TRANGBI_ID']
            this.Tb.cboChuQuan = dataMR.DS_MORONG[0]['CHUQUAN_ID']
            // cboLoaiModemTLS= dataMR.DS_MORONG[0]["LOAIMD_ID"];
            this.Tb.cboLoaiTBTSL = dataMR.DS_MORONG[0]['THIETBIDC_ID']

            this.Tb.slot_cn = dataMR.DS_MORONG[0]['SLOT_CN']
            this.Tb.port_cn = dataMR.DS_MORONG[0]['PORT_CN']
            this.Tb.timeslot_cn = dataMR.DS_MORONG[0]['TIMESLOT_CN']
            this.Tb.mapping_cn = dataMR.DS_MORONG[0]['MAPPING_CN']
            break
          case DichVuVienThong.GPHONE:
            this.Tb.trangbi_id = parseInt(dataMR.DS_MORONG[0]['TRANGBI_ID'])
            this.Tb.txtMaTB = this.ma_tb
            this.Tb.txtSoSimGP = dataMR.DS_MORONG[0]['SIMCARD']
            try {
              if (dataMR.DS_MORONG[0]['MC_TBGP_ID'] == '1') this.Tb.checkVSVXGP_Checked = true
              else this.Tb.checkVSVXGP_Checked = false
            } catch (err) {
              console.log('🚀 ~ file: CompleteProfileBuySamsungTV.vue:2579 ~ err:', err)
            }
            this.Tb.txtCell1 = dataMR.DS_MORONG[0]['CELL1']
            this.Tb.txtCell2 = dataMR.DS_MORONG[0]['CELL2']
            this.Tb.txtToaDo = dataMR.DS_MORONG[0]['TOADO']

            this.GPhoneSoDep.data = dataMR.DS_TIEN_CAMKET
            //dgvTienCKGP.AutoGenerateColumns = false;
            //dgvTienCKGP.DataSource = dt;
            break
          case DichVuVienThong.DICHVU_CNTT:
          case DichVuVienThong.ANTOAN_BAOMAT_TT:
          case DichVuVienThong.TRUNGTAM_DULIEU:
          case DichVuVienThong.HOINGHI_TRUYENHINH:
            let data = []
            await this.Init_Properties(data, dataMR)
            break
          default:
            break
        }
      }
    },
    nganhangTTChanged(val) {
      if (!val) return
      this.Kh.cboNganHang_isnganhangnn = this.NganHangList.filter((x) => x.nganhang_id == val)[0].LOAI_NH == 2
      if (this.Kh.cboNganHang_isnganhangnn) this.$bvModal.show('modal-nganhangnn')
    },
    popupTieuNganh() {
      if (!this.watcherActive) return
      this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupTieuNganh')
      this.popupComponentName = 'popupTieuNganh'
      this.popupComponentData = { vnganhnghe_id: this.Kh.cboNganhNgheLoc, tieunganh: this.tieunganh }
      this.Popup('popupComponents')
    },
    popupTimNVThuCuoc: function() {
      this.$refs.dlgTimKiemNVTC.openDialog()
    },
    popupChonKhuVuc() {
      this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao')
      this.popupComponentName = 'popupChonKhuVuc'
      this.popupComponentData = { diachi: this.Tb.txtDiaChiLD, khuvuc_id: this.Tb.txtKhuVucId, hdtb_id: this.hdtb_id, thuebao_id: this.thuebao_id, donvi_id: this.Tb_cboDaiVT }
      this.Popup('popupComponents')
    },
    getNVTCProcess(value) {
      console.log('getNVTCProcess', value)
      this.Kh.txtMaNVTC = value.maNVTC
      this.Kh.txtMaTuyen = value.rowData.MA_TUYENTHU
      this.thanhtoan.nhanvientc_id = value.rowData.NHANVIEN_ID
      this.thanhtoan.manvtc = value.maNVTC
      this.thanhtoan.tuyenthu_id = value.rowData.TUYENTHU_ID
      this.tuyenthu_id = value.rowData.TUYENTHU_ID
      this.thanhtoan.matuyen = value.rowData.MA_TUYENTHU
    },
    Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    PopUpCongTacVien_Thoat() {
      this.ClosePopup('popupComponents')
      return
    },
    PopUpNguoiGioiThieu_Thoat() {
      this.ClosePopup('popupComponents')
      return
    },
    async popupClosed(val) {
      if (!val) {
        this.ClosePopup('popupComponents')
        return
      }
      this.loading(true)
      try {
        switch (this.popupComponentName) {
          case 'SearchAccount':
            if (val.ma_kh) this.Kh.txtMaKH = val.ma_kh
            if (val.ma_tt) this.Kh.txtMaTT = val.ma_tt
            if (this.khachhang.makh && this.Kh.txtMaKH.trim() != '') this.MaKHChanged()
            break
          case 'popupChonMaTT':
            if (val.ma_tt) this.Kh.txtMaTT = val.ma_tt
            break
          case 'popupTieuNganh':
            this.tieunganh = val
            break
          case 'popupCTV':
            this.txtCTV = val.ten_ht
            this.txtCTVId = val.nhanvien_id
            break
          case 'popupNGT':
            this.txtNguoiGT = val.ten_ht
            this.txtNguoiGTId = val.nhanvien_id
            break
          case 'popupThongTinTC':
            if (val) this.Tb.txtTTCuoc = val
            else this.Tb.txtTTCuoc = ''
            break
        }
      } catch (ex) {
      } finally {
        this.ClosePopup('popupComponents')
        this.loading(false)
      }
    },
    MaKHChanged: function() {},
    loaikhChanged(node, insid) {
      //this.buttons.btnThongTinKhac = (node.cate!="Cá nhân")
      //if(!this.buttons.btnThongTinKhac && this.khachhang.gioitinh==-1) this.khachhang.gioitinh = 0
    },
    getDiaChi: function(data, datatype) {
      this[datatype].DIACHI = data.diachimoi
      this[datatype].TINH_ID = data.tinh_id
      this[datatype].QUAN_ID = data.quan_id
      this[datatype].PHUONG_ID = data.phuong_id
      this[datatype].PHO_ID = data.pho_id
      this[datatype].AP_ID = data.ap_id
      this[datatype].KHU_ID = data.khu_id
      this[datatype].DACDIEM_ID = data.dacdiem_id
      this[datatype].SO_NHA = data.so_nha
      this[datatype].Lat = data.lat
      this[datatype].Lng = data.lng
    },
    getDiaChiKH: function(data) {
      this.getDiaChi(data, 'diachiKH')
      //TODO tim don vi quanly cboDonViQL
      //this.listbox.donvi_ql.value=""
      if (!this.diachiTT.DIACHI) this.diachiTT = Object.assign({}, this.diachiKH)
      if (!this.diachiCT.DIACHI) this.diachiCT = Object.assign({}, this.diachiKH)
      if (!this.diachiLD.DIACHI) this.diachiLD = Object.assign({}, this.diachiKH)
      if (!this.diachiTB.DIACHI) this.diachiTB = Object.assign({}, this.diachiKH)
      if (!this.diachiHK.DIACHI) this.diachiHK = Object.assign({}, this.diachiKH)
      if (!this.diachiNDD.DIACHI) this.diachiNDD = Object.assign({}, this.diachiKH)
    },
    getDiaChiHK: function(data) {
      this.getDiaChi(data, 'diachiHK')
    },
    getDiaChiNDD: function(data) {
      this.getDiaChi(data, 'diachiNDD')
    },
    getDiaChiTT: async function(data) {
      this.getDiaChi(data, 'diachiTT')
    },
    getDiaChiCT: function(data) {
      this.getDiaChi(data, 'diachiCT')
    },
    getDiaChiBC: function(data) {
      this.getDiaChi(data, 'diachiBC')
    },
    getDiaChiTB: async function(data) {
      this.getDiaChi(data, 'diachiTB')
      if (!this.diachiLD.DIACHI || (this.diachiLD.DIACHI == this.diachiKH.DIACHI && this.diachiLD.DIACHI != this.diachiTB.DIACHI)) this.diachiLD = Object.assign({}, this.diachiTB)
    },
    getDiaChiLD: function(data) {
      this.getDiaChi(data, 'diachiLD')
    },
    getDiaChiKN: function(data) {
      this.getDiaChi(data, 'diachiKN')
    },
    getDiaChiDau: function(data) {
      this.getDiaChi(data, 'diachiDau')
    },
    getDiaChiCuoi: function(data) {
      this.getDiaChi(data, 'diachiCuoi')
    },
    GetData: function(response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowPopupCTV() {
      if (this.txtCTVcheck) {
        this.popupComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien')
        this.popupComponentName = 'popupCTV'
        // SearchAccount.data.isPopup = true;
        this.Popup('popupComponents')
      }
    },
    ShowPopupNguoiGT() {
      if (this.txtNguoiGTcheck) {
        this.popupComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu')
        this.popupComponentName = 'popupNGT'
        // SearchAccount.data.isPopup = true;
        this.Popup('popupComponents')
      }
    },
    ht_nvtc_theodc_v2: async function(quantt_id, phuongtt_id, phott_id, aptt_id, khutt_id, cboDonViQL, dacdiemtt_id) {
      let input = {
        vphuong_id: phuongtt_id,
        vpho_id: phott_id,
        vap_id: aptt_id,
        vkhu_id: khutt_id,
        vdonvi_ql_id: cboDonViQL,
        vdacdiem_id: dacdiemtt_id,
        vhttt_id: this.httt_id
      }
      let data = this.GetData(await api.ht_nvtc_theodc_v2(this.axios, input))
      console.log('ht_nvtc_theodc_v2', data)
      return data
    },
    capnhat_ngaykh_ngay_ht_v2: async function(hdtb_id, status) {
      let input = {
        hdtb_id: hdtb_id,
        status: status
      }
      let data = this.GetData(await api.capnhat_ngaykh_ngay_ht_v2(this.axios, input))
      console.log('capnhat_ngaykh_ngay_ht_v2', data)
      return data
    },
    HT_NVTC_TheoDC: async function(quantt_id, phuongtt_id, phott_id, aptt_id, khutt_id, cboDonViQL, dacdiemtt_id) {
      let data = await this.ht_nvtc_theodc_v2(quantt_id, phuongtt_id, phott_id, aptt_id, khutt_id, cboDonViQL, dacdiemtt_id)
      if (data != null && data.length > 0) {
        this.thanhtoan.nhanvientc_id = data[0].NHANVIENTC_ID
        this.Kh.txtMaNVTC = data[0].MA_NVTC
        this.thanhtoan.tuyenthu_id = data[0].TUYENTHU_ID
        this.Kh.txtMaTuyen = data[0].MA_TUYEN
      }
    },
    async btnThongTinTC_Click() {
      // ThongTinTC()
      // this.DangXayDung();
      // return;
      try {
        if (!this.Tb.cboMucCuocTB || this.Tb.cboMucCuocTB <= 0) {
          this.$toast.error('Chưa có mức cước thuê bao. Bạn hãy kiểm tra lại!')
          return
        }
        var apidata = {}
        await api.post_fn_lay_thongtin_tc(this.axios, { ds_para: JSON.stringify({ MUCCUOCTB_ID: this.Tb.cboMucCuocTB, DICHVUVT_ID: this.Tb_cboDichVuVT, GOICUOC_ADSL: this.Tb.cboGoiCuocAdsl }) }).then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            apidata = JSON.parse(response.data.data)
            if (apidata['ERROR_CODE'] != 1) {
              this.$toast.error(apidata['MESSAGE'])
              return
            }
            apidata = apidata['RESULT']
          }
        })

        var cuoc_tb = apidata['CUOC_TB'] ? apidata['CUOC_TB'] : 0
        var cuockhoan = apidata['CUOCKHOAN'] ? apidata['CUOCKHOAN'] : 0
        var cuoc_tg = apidata['CUOC_TG'] ? apidata['CUOC_TG'] : 0
        var cuoc_ll = apidata['CUOC_LL'] ? apidata['CUOC_LL'] : 0

        if (cuoc_tb == '-1') cuoc_tb = '0'
        if (cuockhoan == '-1') cuockhoan = '0'
        if (cuoc_tg == '-1') cuoc_tg = '0'
        if (cuoc_ll == '-1') cuoc_ll = '0'

        var dulieu = {
          cuoc_tb: cuoc_tb,
          cuoc_tg: cuoc_tg,
          cuockhoan: cuockhoan,
          cuoc_ll: cuoc_ll,
          muccuoctb_id: this.Tb.cboMucCuocTB ? this.Tb.cboMucCuocTB : 0,
          mienip: apidata['MIENIP'] ? apidata['MIENIP'] : null,
          loaitb_id: this.Tb.cboLoaiTB,
          thongtintc: this.Tb.txtTTCuoc
        }

        this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupThongTinTC')
        this.popupComponentName = 'popupThongTinTC'
        this.popupComponentData = dulieu
        this.Popup('popupComponents')
      } catch (ex) {
        this.$toast.error('' + ex)
        if (this.debug) console.error(ex)
      }
    },
    ShowChiTietKhuyenMai() {
      this.$refs.popupChiTietKhuyenMai.openDialog(this.hdtb_id, 3)
    },
    ShowAlert: function(msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function(msg) {
      this.$toast.success(msg)
    },
    ShowError: function(msg) {
      this.$toast.error(msg)
    },
    disabledRange: function(date) {
      return date > new Date()
    },
    toDate: function(val) {
      //2022-02-11T15:13:20
      try {
        if (val != null && val != '' && val.length > 10) {
          let date = moment(val, 'YYYY-MM-DDHH:mm:ss')
          return moment(date).format(this.dateFormat)
        }
      } catch (err) {
        return this.toDate2(val)
      }

      return ''
    },
    toDate2: function(val) {
      //2022-02-11T15:13:20
      try {
        if (val != null && val != '' && val.length > 10) {
          let date = moment(val)
          return moment(date).format(this.dateFormat)
        }
      } catch (err) {}

      return ''
    },
    toLowerKey(obj) {
      var key,
        keys = Object.keys(obj)
      var n = keys.length
      var newobj = {}
      while (n--) {
        key = keys[n]
        newobj[key.toLowerCase()] = obj[key]
      }
      return newobj
    },
    toUpperKey(obj) {
      var key,
        keys = Object.keys(obj)
      var n = keys.length
      var newobj = {}
      while (n--) {
        key = keys[n]
        newobj[key.toUpperCase()] = obj[key]
      }
      return newobj
    },
    UpperCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    LowerCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toLowerCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    log: function(msg) {
      console.log(msg)
    },
    fn_hoanthien_hths_datmmoi: async function() {
      let donviid = this.$root.token.getDonViID()
      let nhanvienid = this.$root.token.getNhanVienID()
      let username = this.$root.token.getUserName()
      let ds_param = {
        MA_KH: this.Kh.txtMaKH,
        MA_TT: this.Kh.txtMaTT,
        HDTB_CHA_ID: this.hdtb_id_cha == null ? 0 : this.hdtb_id_cha,
        DICHVUVT_ID: this.dichvuvt_id,
        LOAITB_ID: this.loaitb_id,
        OPEN_SYNC_VINA: false,
        HDTT_ID: this.hdtt_id,
        OPEN_SYNC_VINA_TRASAU: false,
        KHACHHANG_ID: this.hdkh_id,
        THANHTOAN_ID: this.thanhtoan_id,
        HDTB_ID: this.hdtb_id,
        NHANVIEN_ID: nhanvienid,
        MAY_CN: 'Web',
        NGUOI_CN: username,
        HDKH_ID: this.hdkh_id
      }
      if (ds_param.HDTB_CHA_ID == null) ds_param.HDTB_CHA_ID = 0
      let input = {
        ds_para: JSON.stringify(ds_param),
        buoc: this.buoc_ht
      }
      /*
      input={
            "ds_para": "{'MA_KH':'ABC','MA_TT':'ABC','HDTB_CHA_ID':1,'DICHVUVT_ID':1,'LOAITB_ID':1,'OPEN_SYNC_VINA':true,'HDTT_ID':1,'OPEN_SYNC_VINA_TRASAU':true,'KHACHHANG_ID':1,'THANHTOAN_ID':1,'HDTB_ID':1,'NHANVIEN_ID':1,'MAY_CN':'ABC','NGUOI_CN':'ABC','HDKH_ID':1}",
            "buoc": 1
        }
      */
      let response = await api.fn_hoanthien_hths_datmmoi(this.axios, input)
      if ((response.data.error === 200 || response.data.error === '200') && response.data.error_code === 'BSS-00000000') {
        let data = response.data.data
        console.log('Ket qua hoan thien:', data)
        return JSON.parse(data)
      } else {
        console.log(response.data.error_code)
      }
    },
    KiemTraDL_ChuyenVaoDB: function() {
      return true
    },
    HoanThienData: async function(data) {
      if (data.ERROR_CODE == 1) {
        this.ShowSuccess(`Cập nhật dữ liệu vào danh bạ thành công!`)
        await this.MaGDChanged()
      } else if (data.ERROR_CODE == 0) {
        this.ShowError(`ERROR_CODE:${data.ERROR_CODE}, ${data.MESSAGE}`)
        return 0
      } else if (data.ERROR_CODE == -2) {
        this.buoc_ht = data.MESSAGE
        let data = await this.fn_hoanthien_hths_datmmoi()
        await this.HoanThienData(data)
      } else {
        this.ShowError(`ERROR_CODE:${data.ERROR_CODE}, ${data.MESSAGE}`)
      }
    },
    async KichhoatDangKyBaoHiemSo(vma_tb, vphone, vproductCode, vemail, vfullname, vaddress, vngaysinh, vso_gt, vgioitinh) {
      //strmatd =  this.MAP_ID("matocdo", DatabaseConstants.DB2 + ".tocdo_adsl", "where tocdo_id =" + cboGoiCuocAdsl.SelectedValue.ToString())
      return this.vtc_subscriptionActivate(vma_tb, vphone, vproductCode, vemail, vfullname, vaddress, vngaysinh, vso_gt, vgioi_tinh)
    },
    async vtc_subscriptionActivate(vma_tb, vphone, vproductCode, vemail, vfullname, vaddress, vngaysinh, vso_gt, vgioitinh) {
      try {
        let input = {
          address: vaddress,
          email: vemail,
          fullname: vfullname,
          gioitinh: vgioitinh,
          ma_tb: vma_tb,
          ngaysinh: vngaysinh,
          phone: vphone,
          productCode: vproductCode,
          so_gt: vso_gt
        }
        //let data = this.GetData(await api.vtc_subscriptionActivate(this.axios, input))
        let response = await api.vtc_subscriptionActivate(this.axios, input)
        if (response.errorCode == '0') {
          if (response.data.data.result == '1') return true
          this.ShowAlert(`Kích hoạt dịch vụ bảo hiểm số thất bại. Chi tiết:  ${data.result} ${data.message}`)
        } else {
          this.ShowAlert(`Kích hoạt dịch vụ bảo hiểm số thất bại. Chi tiết:  ${response.faultString} `)
        }
        return fale
      } catch (err) {
        return false
      }
    },
    HoanThien: async function() {
      try {
        if (this.dichvuvt_id == DichVuVienThong.ADSL && this.Tb_cboLoaihinhTB == LoaiHinhTB.BAOHIEM_VTC) {
          let vphone = this.Kh.txtTelKH
          if (vphone.Substring(0, 1) == '0') vphone = '84' + vphone.Substring(1, vphone.Length - 1)
          let vngay_sinh = ''
          vngay_sinh = moment(this.Tb.txtNgaySNTB, 'DD/MM/YYYY').format('YYYY-MM-DDD')

          let vso_gt = ''
          vso_gt = this.Kh.txtSoGT
          vgioitinh = this.Kh.gioiTinh
          let strmatd = ''
          strmatd = await this.MapTocDo(this.Tb.cboGoiCuocAdsl)
          // Check api VTC
          if (!this.KichhoatDangKyBaoHiemSo(this.Tb.txtMaTB, vphone, strmatd, 'lam87vthp@gmail.com', this.Kh.txtTenKH, this.Kh.txtDiaChiKH, vngay_sinh, vso_gt, vgioi_tinh)) return

          // Update ngay ht public static int DONGBO_CM = 5;
          await this.capnhat_ngaykh_ngay_ht_v2(this.hdtb_id, 5)
        }
        this.Loading = true
        this.buoc_ht = 1
        let data = await this.fn_hoanthien_hths_datmmoi()
        await this.HoanThienData(data)
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      } finally {
        this.Loading = false
      }
    },
    DongBoCCBS: async function() {
      let callby_ejs = true
      if (callby_ejs) {
        try {
          let popup = this.$refs.refupdatePostPaid
          popup.so_tb = this.Tb.txtMaTB
          popup.TimThongTinKH()
        } catch (err) {
          console.log(`DongBoCCBS-Error:${err}`)
        } finally {
          this.$refs.dialogObjDongBoCCBS.show()
        }
      } else {
        this.popupComponent = () => import('@/modules/contract/setup/UpdatePostPaid/index.js')
        this.popupComponentName = 'updatePostPaid'
        this.popupComponentData = { ma_tb: this.Tb.txtMaTB }
        this.Popup('popupComponents')
      }
    },
    async closeDialogObjDongBoCCBS() {
      if (this.Kh.txtMaGD != '') {
        await this.MaGDChanged()
      }
    },
    lay_ds_diachi_theo_hdtt_id: async function(hdtt_id) {
      let input = {
        hdtt_id: hdtt_id
      }
      let data = this.GetData(await api.lay_ds_diachi_theo_hdtt_id(this.axios, input))
      return data
    },
    lay_ds_diachi_theo_hdkh_id: async function(hdkh_id) {
      let input = {
        hdkh_id: hdkh_id
      }
      let data = this.GetData(await api.lay_ds_diachi_theo_hdkh_id(this.axios, input))
      return data
    },
    lay_ds_diachi_theo_hdtb_id: async function(hdtb_id) {
      let input = {
        in_hdtb_id: hdtb_id
      }
      let data = this.GetData(await api.lay_ds_diachi_theo_hdtb_id(this.axios, input))
      return data
    },
    lvwThanhToan_SelectedIndexChanged: async function(i, item) {
      console.log('lvwThanhToan_SelectedIndexChanged:function ~ i, item:', i, item)
      let dataTT = await this.lay_ds_hopdong_tt_theo_hdtt_id(item.HDTT_ID)
      console.log('lvwThanhToan_SelectedIndexChanged', 'item.HDTT_ID', item.HDTT_ID, 'dataTT', dataTT)
      let arr = { DS_HDTT: [], DS_DIACHI: [] }
      if (dataTT != null && dataTT.length > 0) {
        let tt = this.toUpperKey(dataTT[0])
        arr.DS_HDTT.push(tt)
        let ds_diachi = await this.lay_ds_diachi_theo_hdtt_id(item.HDTT_ID)
        if (ds_diachi != null && ds_diachi.length > 0) {
          arr.DS_DIACHI.push(this.toUpperKey(ds_diachi[0]))
        }
        console.log('lvwThanhToan_SelectedIndexChanged', arr)
        await this.HienThiTTHopDongTT(arr)
      }
    },
    lvwThueBao_SelectedIndexChanged: async function(i, item) {
      console.log('lvwThueBao_SelectedIndexChanged:i', i, 'item:', item)
      let arr = { DS_HDTB: [], DS_DIACHI: [] }
      this.hdtb_id = item.HDTB_ID
      let dataTB = await this.lay_ds_hopdong_tb_theo_hdtb_id(item.HDTB_ID)
      if (dataTB != null && dataTB.length > 0) {
        let tb = this.toUpperKey(dataTB[0])
        arr.DS_HDTB.push(tb)
        let ds_diachi = await this.lay_ds_diachi_theo_hdtb_id(item.HDTB_ID)
        if (ds_diachi != null && ds_diachi.length > 0) {
          arr.DS_DIACHI.push(this.toUpperKey(ds_diachi[0]))
        }
        console.log('lvwThueBao_SelectedIndexChanged', arr)
        await this.HienThiTTHopDongTB(arr)
      }
    },
    // popupDVKhacTT() {
    //   this.popupComponent = ()=>import('./Popups/popupServiceOthers');
    //   this.popupComponentName = "popupServiceOthersTT";
    //   this.popupComponentData = {_loaitb_id:this.loaitb_id,_loai_id:2,_thanhtoan_id:this.thanhtoan.thanhtoan_id} //default trong code cu, loai_id=1, thanhtoan_id=0
    //   if(this.dsDK_DVK_TT && this.dsDK_DVK_TT.length>0) this.popupComponentData.ds = this.dsDK_DVK_TT;
    //   this.Popup('popupComponents');
    // },
    HT_DichVuKhac(dvKhac, vloai) {
      try {
        if (vloai == 1) {
        } else {
        }
      } catch (err) {
        console.log('🚀 ~ file: CompleteProfileBuySamsungTV.vue:3303 ~ HT_DichVuKhac ~ err:', err)
      }
    },
    async tsbtnNhapMoi_Click() {},
    async tsbtnGhiLai_Click() {},
    async tsbtnThongTinLH_Click() {},
    async btnChuyenDB_Click() {
      try {
        await this.HoanThien()
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async tsbtnGiaoPhieu_Click() {},
    async tsbtnSuaDacTinh_Click() {},
    async tsbtnThemTT_Click() {},
    async tsbtnDongBoCCBS_Click() {
      try {
        if (this.ma_tb == null || this.ma_tb == '') return
        await this.DongBoCCBS()
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async tsbtnXoaTT_Click() {},
    async tsbtnHen_Click() {},
    Capitalize(input) {
      try {
        var capitalFirst = input.charAt(0).toUpperCase()
        var noCaseTail = input.slice(1, input.length)
        return capitalFirst + noCaseTail
      } catch (err) {
        return input
      }
    },
    async cboDaiVT_SelectedValueChanged(val) {
      console.log('cboDaiVT_SelectedValueChanged:', val)
      try {
        if (val > 0) {
          let vloaidv_id = LOAI_DV.TRAM_VT
          let rawdata = await this.lay_ds_donvi_theo_loaidv(val, vloaidv_id)
          let data = rawdata.map((x) => ({ TEN_DV: x.ten_dv, DONVI_ID: x.donvi_id }))
          console.log('cboDaiVT_SelectedValueChanged:', val, data)
          //
          this.VeTinhCD.list = data
          this.VeTinhAdsl.list = data
          this.VeTinhMgwan.list = data
          this.VeTinhIMS.list = data
          // set default
          if (data != null && data.length > 0) {
            let donviId = data[0].DONVI_ID
            this.Tb.cboVeTinhCD = donviId

            this.Tb.cboVeTinhAdsl = donviId
            await this.cboVeTinhAdsl_SelectedValueChanged(donviId)

            this.Tb.cboVeTinhMgwan = donviId
            this.Tb.cboVeTinhIMS = donviId
          }
        }
      } catch (cboDaiVTerr) {
        console.log('~ cboDaiVTerr:', cboDaiVTerr)
      }
    },

    async cboVeTinhAdsl_SelectedValueChanged(val) {
      console.log('cboVeTinhAdsl_SelectedValueChanged ~ val:', val)
      try {
        if (val != null) {
          let data = []
          let vloaidv_id = `${LOAI_DV.TRAM_PORT}`
          let data1 = await this.lay_ds_donvi_theo_loaidv(val, vloaidv_id)
          if (data1 != null) data1.forEach((x) => data.push(x))
          vloaidv_id = `${LOAI_DV.TRAM_VETINH}`
          let data2 = await this.lay_ds_donvi_theo_loaidv(val, vloaidv_id)
          if (data2 != null) data2.forEach((x) => data.push(x))
          if (data != null && data.length > 0) {
            this.TramTB_ADSL.list = data.map((x) => ({ DONVI_ID: x.donvi_id, TEN_DV: x.ten_dv }))
            this.Tb.cboTramTB_ADSL = this.TramTB_ADSL.list[0].DONVI_ID
          }
        }
      } catch (err1) {
        console.log('cboVeTinhAdsl_SelectedValueChanged-err1:', err1)
      }
    },
    async cboLoaihinhTB_SelectedValueChanged(val) {
      console.log('🚀 ~ file: CompleteProfileBuySamsungTV.vue:3397 ~ cboLoaihinhTB_SelectedValueChanged ~ val:', val)
      try {
        this.Loading = true
        await this.ds_kieu_ld(val)
        await this.HT_GoiCuoc_DD_Combobox(val)
        await this.HT_Tocdo_Adsl_Combobox_ByLoaiTB(val)
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    async cboDichVuVT_SelectedValueChanged(val) {
      if (val > 0) {
        this.dichvuvt_id = parseInt(val)
        this.LOAIHINH_TB.data = this.LOAIHINH_TB.list.filter((x) => x.DICHVUVT_ID == val)
        this.Tb_cboLoaihinhTB = this.LOAIHINH_TB.list.filter((x) => x.DICHVUVT_ID == val)[0].LOAITB_ID
        this.Tb.cboLoaihinhTB = this.Tb_cboLoaihinhTB
        this.GiaoDien_DichVuVT(val)
      }
    },
    async ht_loaigt_combobox() {
      let input = {}
      let response = await api.ht_loaigt_combobox(this.axios, input)
      console.log('ht_loaigt_combobox ~ data:', response, response.data.data)
      let data = response.data.data
      if (data != null) {
        this.GiayToKH = data.map((x) => ({ LOAIGT_ID: x.loaigt_id, LOAI_GT: x.loai_gt }))
      }
      return data
    }
  }
}
</script>
