<template src="./template.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import api from "../../../contract/complete_profile/CompleteTransferFile/api/index";
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
import DiaChiKhachHangModal from '../../../contract/setup/SurveyRequest/popups/ChonDiaChi/DiaChiKhachHangModal.vue'
import ChiTietNganhNgheModal
  from '../../../contract/setup/SurveyRequest/popups/ChiTietNganhNghe/ChiTietNganhNgheModal.vue'
import ChiTietTSL from "../../../contract/complete_profile/CompleteTransferFile/ChiTietTSL";
import PopupTimKiemNhanVienThuCuoc from '../../../contract/setup/InstallNewSubs/Popups/PopupTimKiemNhanVienThuCuoc'
// import SearchContractModal from '../../../contract/setup/ChangeInstallUnit/popup/SearchContract/SearchContractModal'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
import {
  DichVuVienThong,
  TRANGBI,
  DUNG_THU,
  LoaiHopDong,
  TrangThaiHD,
  LoaiHinhTB,
  ThoiHan,
  KHOANMUC_TT,
  KieuCaiDatDVGT,
  THUESO,
  TRANGTHAI_DONGBO,
  KieuLapDat,
  LOAI_DV,
  Loai_KH,
  HINHTHUC_TC,
  KIEU_HD,
  KIEU_KH,
  PHANLOAI_KH,
  Loai_NV,
  LOAISO,
  LOAI_KV,
  Loai_KM,
} from "../../../contract/complete_profile/CompleteTransferFile/Enum";

export default {
  name: "MoveDetails",
  components: {
    breadcrumb,
    SearchAccount,
    DiaChiKhachHangModal,
    ChiTietNganhNgheModal,
    ChiTietTSL,
    SearchContractModal,
    PopupTimKiemNhanVienThuCuoc
  },
  computed: {
    donViId() {
      return +this.$root.token.getDonViID()
    },
    nhanvienId() {
      return +this.$root.token.getNhanVienID()
    },
    username() {
      return this.$root.token.getUserName()
    },
    donViDLId() {
      return this.$root.token.getDonViDuLieuID()
    },
    phanvungId() {
      return this.$root.token.getPhanVungID()
    }
  },
  data() {
    return {
      header: {
        title: 'Chi tiết dịch chuyển',
        list: [
          {name: 'Lập hợp đồng', link: {name: 'Ui.cards'}},
          {name: 'Lắp đặt mới', link: {name: 'Ui.cards'}},
        ]
      },
      waterMark: "__/__/____",
      dateVal: new Date(),
      dateFormat: "DD/MM/YYYY",
      watcherActive: false,
      checkNganHang: true,
// -------------------code cu --------------------------//
      tuyenthu_id: 0,
      kieugoi_id: 5,
      tthd_id: 0,
      dichvuvt_id: 1,
      httt_id: null,

      hdkh_id: null,
      hdtt_id: null,
      hdtb_id: null,

      khachhang_id: null,
      thanhtoan_id: null,
      thuebao_id: null,

      port_id: null,
      vci_vpi_id: null,
      port_id_ht: null,
      ne: '',
      ma_tb: '',
      vetinhcd_id: null,

      diachikh_id: null,
      apkh_id: null,
      khukh_id: null,
      phokh_id: null,
      phuongkh_id: null,
      quankh_id: null,
      tinhkh_id: null,
      sonhakh: null,
      blockkh: "",
      tangkh: "",
      sophongkh: "",
      motadckh: "",

      diachitt_id: null,
      phott_id: null,
      aptt_id: null,
      phuongtt_id: null,
      quantt_id: null,
      tinhtt_id: null,
      sonhatt: null,
      khutt_id: null,
      blocktt: "",
      tangtt: "",
      sophongtt: "",
      motadctt: "",

      diachitb_id: null,
      photb_id: null,
      aptb_id: null,
      phuongtb_id: null,
      quantb_id: null,
      tinhtb_id: null,
      sonhatb: null,
      khutb_id: null,
      blocktb: "",
      tangtb: "",
      sophongtb: "",
      motadctb: "",

      diachidau_id: null,
      phodau_id: null,
      apdau_id: null,
      phuongdau_id: null,
      quandau_id: null,
      tinhdau_id: null,
      sonhadau: null,
      khudau_id: null,

      diachicuoi_id: null,
      phocuoi_id: null,
      apcuoi_id: null,
      phuongcuoi_id: null,
      quancuoi_id: null,
      tinhcuoi_id: null,
      sonhacuoi: null,
      khucuoi_id: null,

      apndd_id: null,
      phondd_id: null,
      phuongndd_id: null,
      quanndd_id: null,
      tinhndd_id: null,
      sonhandd: null,
      khudd_id: null,
      diachild_id: null,
      phold_id: null,
      apld_id: null,
      khuld_id: null,
      phuongld_id: null,
      quanld_id: null,
      tinhld_id: null,
      sonhald: null,
      blockld: "",
      tangld: "",
      sophongld: "",
      motadcld: "",

      apct_id: null,
      phoct_id: null,
      phuongct_id: null,
      quanct_id: null,
      tinhct_id: null,
      sonhact: null,
      khuct_id: null,
      SoMayTN: "",

      slot_dau: null,
      port_dau: null,
      timeslot_dau: null,
      ten_tbi_dau: null,

      slot_cuoi: null,
      port_cuoi: null,
      timeslot_cuoi: null,
      ten_tbi_cuoi: null,

      slot_cn: null,
      port_cn: null,
      timeslot_cn: null,
      mapping_cn: null,
      doicap_dau: null,
      doicap_cuoi: null,
      hopcap_dau: null,
      hopcap_cuoi: null,
      culy_dau: 0,
      culy_cuoi: 0,
      donvi_dau_id: 0,
      donvi_cuoi_id: 0,
      diachidau: null,
      diachicuoi: null,
      kieu: 0,

      TNC1_ID: 0,
      TNC2_ID: 0,
      TNC3_ID: 0,
      TNC4_ID: 0,
      nganhnghe_id: 0,
      kt_taobang: 0,

      // ------------------------------UI-------------------------------------//
      dichvuCongthem: '',
      dichvuKhac: '',
      checkNgaybg: false,
      cboDonViQL: {
        option: [],
        value: null,
      },
      txtMaTuyen: '',
      cboNganHang: {
        option: [],
        value: null,
      },
      txtMST: '',
      txtSTK: '',
      cboHTTT: {
        option: [],
        value: null,
      },
      txtMaTT: '',
      txtDiaChiTT: '',
      txtTenTT: '',
      txtNgayCap: '',
      cboGiayToKH: {
        option: [],
        value: null,
      },
      txtNoiCap: '',
      txtSoGT: '',
      txtFaxKH: '',
      txtTelKH: '',
      txtNgaySN: '',
      txtDiaChiKH: '',
      txtTenKH: '',
      txtMaKH: '',
      cboTT: {
        option: [],
        value: null,
      },
      txtTenTB: '',
      txtDiaChiTB: '',
      cboDichVuVT: {
        option: [],
        value: null,
      },
      txtMaGD: '',
      cboLoaihinhTB: {
        option: [],
        value: null,
      },
      cboKieuLD: {
        option: [],
        value: null,
      },
      txtNeCD: '',
      cboVeTinhCD: {
        option: [],
        value: null,
      },
      txtMaTB: '',
      cboDoituongTB: {
        option: [],
        value: null,
      },
      cboDaiVT: {
        option: [],
        value: null,
      },
      cboLoaiModem: {
        option: [],
        value: null,
      },
      cboLoaiTBiWan: {
        option: [],
        value: null,
      },
      cboTocDo: {
        option: [],
        value: null,
      },
      cboVeTinh: {
        option: [],
        value: null,
      },
      cboLoaiKenh: {
        option: [],
        value: null,
      },

      dtpNgayLapHD: new Date().toLocaleString('en-GB', {dateStyle: 'short'}),
      dtpNgayYeuCau: new Date().toLocaleString('en-GB', {dateStyle: 'short'}),
      dtpNgayBG: null,

      txtGhiChuTB: '',
      txtSoMetDay: '',
      chkVSVX: false,
      checkVSVX: false,
      cboVTGPHONE: {
        option: [],
        value: null,
      },
      txtSoSimGphone: '',
      txtHopCap: '',
      txtDoiCap: '',
      txtHoKhau: '',
      txtNgayCapHK: '',
      txtNoiCapHK: '',
      txtMaHD: '',
      txtNgayHetHan: '',
      rdoGTNu: false,
      rdoGTNam: false,
      rdoGTNone: false,
      cboQuocTich: {
        option: [],
        value: null,
      },
      cboDanToc: {
        option: [],
        value: null,
      },
      txtDiaChiNDD: '',
      txtNguoiDD: '',
      txtChucDanhDD: '',
      txtSoTKKH: '',
      chkNganHangKH: false,
      cboNganHangKH: {
        option: [],
        value: null,
      },
      txtMaSTKH: '',
      txtGhiChuKH: '',
      txtDiaChiCT: '',
      txtMaNVTC: '',
      txtGhiChuTT: '',
      txtNgaySNTB: '',
      txtDiaChiLD: '',
      txtMaTBDD: '',
      cboLoaiTBTSL: {
        option: [],
        value: null,
      },
      cboChuQuan: {
        option: [],
        value: null,
      },
      cboTrangBi: {
        option: [],
        value: null,
      },
      cboThoiHan: {
        option: [],
        value: null,
      },
      cboTocDoKenhTSL: {
        option: [],
        value: null,
      },
      cboLoaiKenhTSL: {
        option: [],
        value: null,
      },
      txtDoiCapADSL: '',
      cboVetinhAdsl: {
        option: [],
        value: null,
      },
      txtVCIADSL: '',
      txtIPADSL: '',
      cboDSLAM: {
        option: [],
        value: null,
      },
      txtEmailAdsl: '',
      cboGoiCuocAdsl: {
        option: [],
        value: null,
      },
      txtPort: '',
      txtVPIADSL: '',
      txtMatKhauADSL: '',
      cboLoaiTB: {
        option: [],
        value: null,
      },
      txtHopCapADSL: '',
      txtSoMayTNAdsl: '',
      cboMucCuoc: {
        option: [],
        value: null,
      },
      txtCuLyADSL: '',
      cboBrasADSL: {
        option: [],
        value: null,
      },
      txtHuongKNWan: '',
      cboThoiHanWan: {
        option: [],
        value: null,
      },
      cboChuQuanWan: {
        option: [],
        value: null,
      },
      cboTrangBiWan: {
        option: [],
        value: null,
      },
      txtmaTN_ADSL: '',
      txtmaTN_Wan: '',
      txtmaTN_CD: '',
      txtmadoicap_wan: '',
      txtmadoicap_ADSL: '',
      txtmadoicap_CD: '',
      txtSystem: '',
      txtShelf: '',
      txtRack: '',
      txtSlotADSL: '',
      txtLineTrang_CD: '',
      txtLineTrang_Wan: '',
      txtLineTrang_TSL: '',
      txtLineTrang_ADSL: '',
      txtToaDo: '',
      txtCell2: '',
      txtCell1: '',
      txtMaDoiCap_IMS: '',
      cboTrangBiIMS: {
        option: [],
        value: null,
      },
      cboVeTinhIMS: {
        option: [],
        value: null,
      },
      cboBrasIms: {
        option: [],
        value: null,
      },
      cboThietBiIms: {
        option: [],
        value: null,
      },
      txtDoiCapIms: '',
      txtSlotIms: '',
      txtPortIms: '',
      txtUsername: '',
      txtSystemIms: '',
      cboDsLamIms: {
        option: [],
        value: null,
      },
      txtCuLyIms: '',
      txtHopCapIms: '',
      txtPassword: '',
      txtVpiIms: '',
      txtVciIms: '',
      txtShelfIms: '',
      txtRackIms: '',
      txtTenTbi: '',
      txtVci: '',
      txtVpi: '',
      txtPortSHSL: '',
      txtDSLAMWan: '',
      txtWAN_IP: '',
      txtLAN_IP: '',
      txtPortPE: '',
      txtSlotPE: '',
      cboRouter: {
        option: [],
        value: null,
      },
      txtDacTrung: '',
      txtChu_TK: '',
      cboHost: {
        option: [],
        value: null,
      },
      cboLoaiKH: {
        option: [],
        value: null,
      },
      cboNganhNgheLoc: {
        option: [],
        value: null,
      },

      // ------------------------------END UI-------------------------------------//
      dataDiaChiKH: {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: '',
        diachi: ''
      },
      dataDiaChiNDD: {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: '',
        diachi: ''
      },
      dataDiaChiTT: {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: '',
        diachi: ''
      },
      dataDiaChiTB: {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: '',
        diachi: ''
      },
      dataDiaChiLD: {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: '',
        diachi: ''
      },
      diachiNVTC: {
        DIACHI: "",
        DIACHI_ID: 0,
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
        MOTA: null,
      },
      dataNganhNghe: {
        TNC1_ID: 0,
        TNC2_ID: 0,
        TNC3_ID: 0,
        TNC4_ID: 0,
        nganhnghe_id: 0
      },
      dsHDTB: [],
      thuebaoSelected: [],
      dsHDTT: [],
      gioitinh: -1,
      chitietTSL: {},
      dsDBKH: [],
      strerrorVinaphone: '',
    }
  },
  async created() {
    this.kieugoi_id = 5;
    this.chitietTSL = {
      slot_dau: this.slot_dau,
      port_dau: this.port_dau,
      timeslot_dau: this.timeslot_dau,
      ten_tbi_dau: this.ten_tbi_dau,

      slot_cuoi: this.slot_cuoi,
      port_cuoi: this.port_cuoi,
      timeslot_cuoi: this.timeslot_cuoi,
      ten_tbi_cuoi: this.ten_tbi_cuoi,

      slot_cn: this.slot_cn,
      port_cn: this.port_cn,
      timeslot_cn: this.timeslot_cn,
      mapping_cn: this.mapping_cn,

      doicap_dau: this.doicap_dau,
      doicap_cuoi: this.doicap_cuoi,
      hopcap_dau: this.hopcap_dau,
      hopcap_cuoi: this.hopcap_cuoi,
      culy_dau: this.culy_dau,
      culy_cuoi: this.culy_cuoi,
      donvi_dau_id: this.donvi_dau_id,
      donvi_cuoi_id: this.donvi_cuoi_id,
      //Hiếu bổ sung thêm một số thông tin TSL ngày 12/09/2010
      diachidau: this.diachidau,
      apdau_id: this.apdau_id,
      phodau_id: this.phodau_id,
      phuongdau_id: this.phuongdau_id,
      quandau_id: this.quandau_id,
      tinhdau_id: this.tinhdau_id,

      diachicuoi: this.diachicuoi,
      apcuoi_id: this.apcuoi_id,
      phocuoi_id: this.phocuoi_id,
      phuongcuoi_id: this.phuongcuoi_id,
      quancuoi_id: this.quancuoi_id,
      tinhcuoi_id: this.tinhcuoi_id,
      kieu: this.kieu,
    }
    //TODO du lieu TEST CompleteTransferFile
    this.tthd_id = this.kieugoi_id;
    // this.tthd_id = 2;
    await Promise.all([
      this.HT_LOAI_TB_ADSL_Combobox(this.cboLoaiTB),
      this.HT_DichVuVT_Combobox_DC(this.cboDichVuVT),
      this.HT_LoaiGT_Combobox(this.cboGiayToKH),
      this.HT_DoiTuong_Combobox(this.cboDoituongTB),
      this.HT_NganHang_Combobox_LD(this.cboNganHang),
      this.HT_HinhThucTT_Combobox(this.cboHTTT),
      this.HT_Tocdo_Adsl_Combobox(this.cboGoiCuocAdsl),
      this.HT_MucCuoc_Adsl_Combobox(this.cboMucCuoc),
      this.HT_DonVi_Combobox(this.cboDonViQL, 2),
      this.HT_DonVi_Combobox(this.cboDaiVT, 3),
      this.HT_Loai_Kenh_Combobox(this.cboLoaiKenh),
      this.HT_Tocdo_Kenh_Combobox(this.cboTocDo),
      this.HT_Loai_Modem_Combobox(this.cboLoaiModem),
      this.HT_LOAI_TB_MEGAWAN_Combobox(this.cboLoaiTBiWan),
      //Lam thêm ngày 10/09/2010: Load dữ liệu cho combobox của MegaWan
      this.HT_ChuQuan_MegaWan_Combobox(this.cboChuQuanWan),
      this.HT_TrangBi_MegaWan_Combobox(this.cboTrangBiWan),
      this.HT_ThoiHan_MegaWan_Combobox(this.cboThoiHanWan),
      this.HT_ROUTER_VTN_COMBOBOX(this.cboRouter),
      this.HT_Loai_Kenh_Combobox(this.cboLoaiKenhTSL),
      this.HT_Tocdo_Kenh_Combobox(this.cboTocDoKenhTSL),
      //bỔ sung thêm một số thông tin
      this.HT_DanToc_Combobox(this.cboDanToc),

      this.HT_NganHang_Combobox_LD(this.cboNganHangKH),
      this.HT_QuocTich_Combobox(this.cboQuocTich),
      // this.cboDoituongTB.value = 1,
      this.HT_DonVi_Combobox(this.cboHost, 6),
      this.HT_DoiTuong_Combobox(this.cboDoituongTB),
      //Hiếu bổ sung thông tin ADSL & TSL: 23/08/2010
      this.HT_ThoiHan_TSL_Combobox(this.cboThoiHan),
      this.HT_TrangBi_TSL_Combobox(this.cboTrangBi),
      this.HT_ChuQuan_TSL_Combobox(this.cboChuQuan),
      this.HT_LOAI_TB_TSL_Combobox(this.cboLoaiTBTSL),
      this.HT_BRAS_ADSL_Combobox(this.cboBrasADSL),
      //IMS
      this.HT_TrangBi_TSL_Combobox(this.cboTrangBiIMS),
      this.HT_ThietBi_IMS_Combobox(this.cboThietBiIms),
      this.HT_BRAS_ADSL_Combobox(this.cboBrasIms),
      this.HT_NganhNghe_Combobox(this.cboNganhNgheLoc),
      this.HT_Loai_KH_Combobox(this.cboLoaiKH),
      this.HT_KieuLD_Combobox(this.cboKieuLD),
    ]);
    await this.cboHTTT_SelectedValueChanged();
  },
  methods: {
    btnDiaChiKH_Click() {
      this.dataDiaChiKH.tinh_id = this.tinhkh_id;
      this.dataDiaChiKH.quan_id = this.quankh_id;
      this.dataDiaChiKH.phuong_id = this.phuongkh_id;
      this.dataDiaChiKH.pho_id = this.phokh_id;
      this.dataDiaChiKH.ap_id = this.apkh_id;
      this.dataDiaChiKH.sonha = this.sonhakh;
      this.dataDiaChiKH.diachi = this.txtDiaChiKH;
      this.dataDiaChiKH.block = this.blockkh;
      this.dataDiaChiKH.tang = this.tangkh;
      this.dataDiaChiKH.sophong = this.sophongkh;
      this.dataDiaChiKH.motathem = this.motadckh;
      this.$refs.diaChiKhachHangModal.showModal()
    },
    acceptDiaChiKH(obj) {
      this.tinhkh_id = obj.tinh_id;
      this.quankh_id = obj.quan_id;
      this.phuongkh_id = obj.phuong_id;
      this.phokh_id = obj.pho_id;
      this.apkh_id = obj.ap_id;
      this.sonhakh = obj.sonha;
      this.txtDiaChiKH = obj.diachi;
      this.blockkh = obj.block;
      this.tangkh = obj.tang;
      this.sophongkh = obj.sophong;
      this.motadckh = obj.motathem;
      if (this.quantt_id === 0) {
        this.tinhtt_id = this.tinhkh_id;
        this.quantt_id = this.quankh_id;
        this.phuongtt_id = this.phuongkh_id;
        this.phott_id = this.phokh_id;
        this.aptt_id = this.apkh_id;
        this.sonhatt = this.sonhakh;
        this.txtDiaChiTT = this.txtDiaChiKH;
        this.blocktt = this.blockkh;
        this.tangtt = this.tangkh;
        this.sophongtt = this.sophongkh;
        this.motadctt = obj.motathem;

        this.tinhtb_id = this.tinhkh_id;
        this.quantb_id = this.quankh_id;
        this.phuongtb_id = this.phuongkh_id;
        this.photb_id = this.phokh_id;
        this.aptb_id = this.apkh_id;
        this.sonhatb = this.sonhakh;
        this.txtDiaChiTB = this.txtDiaChiKH;
        this.blocktb = this.blockkh;
        this.tangtb = this.tangkh;
        this.sophongtb = this.sophongkh;
        this.motadctb = this.motadckh;
      }
    },

    btnDiaChiTT_Click() {
      this.dataDiaChiTT.tinh_id = this.tinhtt_id;
      this.dataDiaChiTT.quan_id = this.quantt_id;
      this.dataDiaChiTT.phuong_id = this.phuongtt_id;
      this.dataDiaChiTT.pho_id = this.phott_id;
      this.dataDiaChiTT.ap_id = this.aptt_id;
      this.dataDiaChiTT.sonha = this.sonhatt;
      this.dataDiaChiTT.diachi = this.txtDiaChiTT;
      this.dataDiaChiTT.block = this.blocktt;
      this.dataDiaChiTT.tang = this.tangtt;
      this.dataDiaChiTT.sophong = this.sophongtt;
      this.dataDiaChiTT.motathem = this.motadctt;
      this.$refs.diaChiTTModal.showModal()
    },
    acceptDiaChiTT(obj) {
      this.tinhtt_id = obj.tinh_id;
      this.quantt_id = obj.quan_id;
      this.phuongtt_id = obj.phuong_id;
      this.phott_id = obj.pho_id;
      this.aptt_id = obj.ap_id;
      this.sonhatt = obj.sonha;
      this.txtDiaChiTT = obj.diachi;
      this.blocktt = obj.block;
      this.tangtt = obj.tang;
      this.sophongtt = obj.sophong;
      this.motadctt = obj.motathem;
    },

    btnDiaChiNDD_Click() {
      if (this.quanndd_id !== 0 && this.phuongndd_id !== 0 && this.phondd_id !== 0 && this.txtDiaChiNDD !== "") {
        this.dataDiaChiNDD.tinh_id = this.tinhndd_id;
        this.dataDiaChiNDD.quan_id = this.quanndd_id;
        this.dataDiaChiNDD.phuong_id = this.phuongndd_id;
        this.dataDiaChiNDD.pho_id = this.phondd_id;
        this.dataDiaChiNDD.ap_id = this.apndd_id;
        this.dataDiaChiNDD.sonha = this.sonhandd;
        this.dataDiaChiNDD.diachi = this.txtDiaChiNDD;
      }
      if (this.txtDiaChiNDD === "") {
        this.dataDiaChiNDD.tinh_id = this.tinhkh_id;
        this.dataDiaChiNDD.quan_id = this.quankh_id;
        this.dataDiaChiNDD.phuong_id = this.phuongkh_id;
        this.dataDiaChiNDD.pho_id = this.phokh_id;
        this.dataDiaChiNDD.ap_id = this.apkh_id;
        this.dataDiaChiNDD.sonha = this.sonhakh;
        this.dataDiaChiNDD.diachi = this.txtDiaChiKH;
      }
      this.$refs.diaChiNguoiDDModal.showModal()
    },
    acceptDiaChiNDD(obj) {
      this.tinhndd_id = obj.tinh_id;
      this.quanndd_id = obj.quan_id;
      this.phuongndd_id = obj.phuong_id;
      this.phondd_id = obj.pho_id;
      this.apndd_id = obj.ap_id;
      this.sonhandd = obj.sonha;
      this.txtDiaChiNDD = obj.diachi;
    },

    btnDiaChiTB_Click() {
      this.dataDiaChiTB.tinh_id = this.tinhtb_id;
      this.dataDiaChiTB.quan_id = this.quantb_id;
      this.dataDiaChiTB.phuong_id = this.phuongtb_id;
      this.dataDiaChiTB.pho_id = this.photb_id;
      this.dataDiaChiTB.ap_id = this.aptb_id;
      this.dataDiaChiTB.sonha = this.sonhatb;
      this.dataDiaChiTB.diachi = this.txtDiaChiTB;
      this.dataDiaChiTB.block = this.blocktb;
      this.dataDiaChiTB.tang = this.tangtb;
      this.dataDiaChiTB.sophong = this.sophongtb;
      this.dataDiaChiTB.motathem = this.motadctb;
      this.$refs.diaChiTBModal.showModal()
    },
    acceptDiaChiTB(obj) {
      this.tinhtb_id = obj.tinh_id;
      this.quantb_id = obj.quan_id;
      this.phuongtb_id = obj.phuong_id;
      this.photb_id = obj.pho_id;
      this.aptb_id = obj.ap_id;
      this.sonhatb = obj.sonha;
      this.txtDiaChiTB = obj.diachi;
      this.blocktb = obj.block;
      this.tangtb = obj.tang;
      this.sophongtb = obj.sophong;
      this.motadctb = obj.motathem;
    },
    btnDiaChiLD_Click() {
      this.dataDiaChiLD.tinh_id = this.tinhld_id;
      this.dataDiaChiLD.quan_id = this.quanld_id;
      this.dataDiaChiLD.phuong_id = this.phuongld_id;
      this.dataDiaChiLD.pho_id = this.phold_id;
      this.dataDiaChiLD.ap_id = this.apld_id;
      this.dataDiaChiLD.sonha = this.sonhald;
      this.dataDiaChiLD.diachi = this.txtDiaChiLD;
      this.dataDiaChiLD.block = this.blockld;
      this.dataDiaChiLD.tang = this.tangld;
      this.dataDiaChiLD.sophong = this.sophongld;
      this.dataDiaChiLD.motathem = this.motadcld;
      this.$refs.diaChiLDModal.showModal()
    },
    acceptDiaChiLD(obj) {
      this.tinhld_id = obj.tinh_id;
      this.quanld_id = obj.quan_id;
      this.phuongld_id = obj.phuong_id;
      this.phold_id = obj.pho_id;
      this.apld_id = obj.ap_id;
      this.sonhald = obj.sonha;
      this.txtDiaChiLD = obj.diachi;
      this.blockld = obj.block;
      this.tangld = obj.tang;
      this.sophongld = obj.sophong;
      this.motadcld = obj.motathem;
    },

    bntLayMaKh_Click() {
      this.$refs['LayMaKH'].show()
    },
    accepMaKH(item) {
      console.log(item);
      this.txtMaKH = item.ma_tb
      this.$refs['LayMaKH'].hide()
    },

    btnLayMaTT_Click() {
      this.$refs['LayMaTT'].show()
    },
    acceptMaTT(item) {
      console.log(item);
      this.txtMaTT = item.ma_tt
      this.$refs['LayMaTT'].hide()
    },

    btnLayMaGD_Click() {
      this.$refs.layMaGD.showModal()
    },
    async acceptMaGD(item) {
      console.log(item);
      this.txtMaGD = item.ma_gd
      if (this.txtMaGD !== "") {
        //Hiếu bổ sung thêm ngày 12/09/2010
        //   DataSet ds = new DataSet();
        //   if (tthd_id == KIEUGOI_CN_HOPDONG.SUA_HD)
        //     ds = tchopdong.LAY_DS_HOPDONG_THEO_MA_GD(txtMaGD.Text, LoaiHopDong.DI_CHUYEN, tt_nd.donvi_id, tthd_id, tt_nd.nhanvien_id, (int)tt_nd.donvi_dl_id);
        // else
        //   ds = tchopdong.LAY_DSHD_THEO_MA_GD(txtMaGD.Text, LoaiHopDong.DI_CHUYEN, 0, tthd_id, tt_nd.nhanvien_id, (int)tt_nd.donvi_dl_id);
        //   if (ds.Tables[0].Rows.Count == 0)
        //     ds = tchopdong.LAY_DS_HOPDONG_THEO_MA_GD_TSL(txtMaGD.Text, LoaiHopDong.DI_CHUYEN, tt_nd.donvi_id, tthd_id);
        //   if (ds.Tables[0].Rows.Count > 0)
        //   {
        //     HienThiTTHopDongKH(ds);
        //   }
        //HienThiTTHopDongKH(tchopdong.LAY_DS_HOPDONG_THEO_MA_GD(txtMaGD.Text, LoaiHopDong.DI_CHUYEN, 0, tthd_id, 0, (int)tt_nd.donvi_dl_id));

        this.loading(true);
        this.watcherActive = false;
        // ds = tchopdong.LAY_DSHD_THEO_MA_GD(txtMaGD.Text, LoaiHopDong.DI_CHUYEN, 0, tthd_id, tt_nd.nhanvien_id, (int)tt_nd.donvi_dl_id);
        // if (ds.Tables[0].Rows.Count == 0)
        //   ds = tchopdong.LAY_DS_HOPDONG_THEO_MA_GD_TSL(txtMaGD.Text, LoaiHopDong.DI_CHUYEN, tt_nd.donvi_id, tthd_id);
        await this.HienThiTTHopDongKH();
        this.watcherActive = true;
      }
    },

    btnChonSM_Click() {
      if (this.dichvuvt_id === 1) {
        this.$refs.laySM.showModal()
      }
    },
    acceptSM(item) {
      this.txtMaTB = item.ma_tb;
    },

    btnTimMaNVTC_Click() {
      this.diachiNVTC.QUAN_ID = this.quantt_id;
      this.diachiNVTC.PHUONG_ID = this.phuongtt_id;
      this.diachiNVTC.PHO_ID = this.phott_id;
      this.$refs.TimKiemNVTC.openDialog();
    },
    acceptMaNVTC(obj) {
      // this.txtMaNVTC = obj.maNVTC;
      // this.txtMaTuyen = obj.rowData.MA_TUYENTHU;
      console.log(obj);
      if (this.quantt_id !== 0 && this.phuongtt_id !== 0) {
        // this.nhanvientc_id = obj.nhanvientc_id;
        this.txtMaNVTC = obj.maNVTC;
        this.tuyenthu_id = obj.rowData.TUYENTHU_ID;
        this.txtMaTuyen = obj.rowData.MA_TUYENTHU;
      } else {
        // Message_Box.ShowError("Chưa có thông tin về địa chỉ thanh toán\nBạn kiểm tra lại!");
        // nhanvientc_id = 0;
        this.txtMaNVTC = "";
      }
    },

    btnNganhNghe_Click() {
      this.dataNganhNghe.nganhnghe_id = this.cboNganhNgheLoc.value;
      this.dataNganhNghe.TNC1_ID = this.TNC1_ID;
      this.dataNganhNghe.TNC2_ID = this.TNC2_ID;
      this.dataNganhNghe.TNC3_ID = this.TNC3_ID;
      this.dataNganhNghe.TNC4_ID = this.TNC4_ID;
      this.$refs.ChitietNganhNghe.showModal()
    },
    acceptNganhNghe(obj) {
      this.TNC1_ID = obj.TNC1_ID;
      this.TNC2_ID = obj.TNC2_ID;
      this.TNC3_ID = obj.TNC3_ID;
      this.TNC4_ID = obj.TNC4_ID;
      this.nganhnghe_id = obj.nganhnghe_id;
      this.cboNganhNgheLoc.value = this.nganhnghe_id;
    },


    async btnChiTietTSL_Click() {
      // this.$refs['popupChitietTSL'].show()

      await this.$refs.popupChitietTSL.showModal()
    },
    acceptChitietTSL(f) {
      // console.log(f)
      // this.$refs['popupChitietTSL'].hide()
      this.slot_dau = f.slot_dau;
      this.port_dau = f.port_dau;
      this.timeslot_dau = f.timeslot_dau;
      this.ten_tbi_dau = f.ten_tbi_dau;
      this.slot_cuoi = f.slot_cuoi;
      this.port_cuoi = f.port_cuoi;
      this.timeslot_cuoi = f.timeslot_cuoi;
      this.ten_tbi_cuoi = f.ten_tbi_cuoi;
      this.slot_cn = f.slot_cn;
      this.port_cn = f.port_cn;
      this.timeslot_cn = f.timeslot_cn;
      this.mapping_cn = f.mapping_cn;

      this.doicap_dau = f.doicap_dau;
      this.doicap_cuoi = f.doicap_cuoi;
      this.hopcap_dau = f.hopcap_dau;
      this.hopcap_cuoi = f.hopcap_cuoi;
      this.culy_dau = f.culy_dau;
      this.culy_cuoi = f.culy_cuoi;
      this.donvi_dau_id = f.donvi_dau_id;
      this.donvi_cuoi_id = f.donvi_cuoi_id;
      //Hiếu 12/09/2010
      this.diachidau = f.diachidau;
      this.tinhdau_id = f.tinhdau_id;
      this.quandau_id = f.quandau_id;
      this.phuongdau_id = f.phuongdau_id;
      this.phodau_id = f.phodau_id;
      this.apdau_id = f.apdau_id;

      this.diachicuoi = f.diachicuoi;
      this.tinhcuoi_id = f.tinhcuoi_id;
      this.quancuoi_id = f.quancuoi_id;
      this.phuongcuoi_id = f.phuongcuoi_id;
      this.phocuoi_id = f.phocuoi_id;
      this.apcuoi_id = f.apcuoi_id;
    },

    async HT_LOAI_TB_ADSL_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsLoaiTB_ADSL(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].thietbidc_id
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_DichVuVT_Combobox_DC(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsDichvuVT(this.axios, {
          "p_nguoidung_id": 1
        })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].dichvuvt_id
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_LoaiGT_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsLoaiGT(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].LOAIGT_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_DoiTuong_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsDoituong(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].doituong_id
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_NganHang_Combobox_LD(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsNganHang(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].NGANHANG_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_HinhThucTT_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsHinhThucTT(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].HTTT_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    //Loai KH
    async HT_Tocdo_Adsl_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsTocDo_ADSL(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].tocdo_id
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_MucCuoc_Adsl_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsMucCuoc_ADSL(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].MUCCUOC_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_DonVi_Combobox(cbo, loaidonvi) {
      try {
        cbo.option = []
        const response = await api.getDsDonvi(this.axios, {"loai_donvi_id": loaidonvi})
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
        await this.cboDaiVT_SelectedValueChanged()
      }
    },
    async HT_Loai_Kenh_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsLoaiKenh_MWAN(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].LOAIKENH_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_Tocdo_Kenh_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsTocDoKenh_TSL(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].TOCDO_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_Loai_Modem_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsModem_MWAN(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].LOAIMD_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_LOAI_TB_MEGAWAN_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsThietBi_MWAN(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].thietbidc_id
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_ChuQuan_MegaWan_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsChuQuan_MWAN(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].CHUQUAN_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_TrangBi_MegaWan_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsTrangBi_MWAN(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].TRANGBI_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_ThoiHan_MegaWan_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsThoiHan_MWAN(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].THOIHAN_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_ROUTER_VTN_COMBOBOX(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsRouter_MWAN(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].pe_id
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_DanToc_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsDantoc(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].DANTOC_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_QuocTich_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsQuoctich(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].QUOCTICH_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_ThoiHan_TSL_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsThoiHan_MWAN(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].THOIHAN_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_TrangBi_TSL_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsTrangBi_MWAN(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].TRANGBI_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_ChuQuan_TSL_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsChuQuan_MWAN(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].CHUQUAN_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_LOAI_TB_TSL_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsThietBi_TSL(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].thietbidc_id
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_BRAS_ADSL_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsBRAS_ADSL(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].bras_id
            await this.HT_DSLAM_Cbo(this.cboDsLamIms, cbo.value);
            await this.HT_DSLAM_Cbo(this.cboDSLAM, cbo.value);
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_ThietBi_IMS_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsThietBi_IMS(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].THIETBI_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_Loai_KH_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsLoaiKH(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].LOAIKH_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async HT_NganhNghe_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsNganhnghe(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].NGANHNGHE_ID
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
        // this.watcherActive = true;
      }
    },
    async HT_KieuLD_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsKieuLD(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
        if (cbo.option.length > 0) {
          cbo.value = cbo.option[0].KIEULD_ID
        }
      }
    },
    async HT_LoaiHinh_TB_Combobox(cbo) {
      try {
        cbo.option = []
        const response = await api.getDsLoaiHinhTB(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
        if (cbo.option.length > 0) {
          cbo.value = cbo.option[0].LOAITB_ID
        }
      }
    },
    async HT_DSLAM_Cbo(cbo, bras_id) {
      let post = {
        "bras_id": bras_id,
        "kieu": 2
      }
      try {
        cbo.option = []
        const response = await api.getDsDSLAM_ADSL(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data

          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].dslam_id;
            this.cboDSLAM.value = cbo.option[0].dslam_id;
            this.cboDsLamIms.value = cbo.option[0].dslam_id;
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },

    async HienThiTTHopDongKH() {
      //TODO DATA TEST
      // let post = {
      //   "in_ma_gd": "HPG-DC/00524396",
      //   "in_loaihd_id": 3,
      //   "in_donvi_id": 0,
      //   "in_tthd_id": 2,
      //   "in_nhanvien_id": 6874,
      //   "in_donvi_dl_id": 0
      // }
      let post = {
        "in_ma_gd": this.txtMaGD,
        "in_loaihd_id": 3,
        "in_donvi_id": this.donViId,
        "in_tthd_id": this.tthd_id,
        "in_nhanvien_id": this.nhanvienId,
        "in_donvi_dl_id": 0
      }
      try {
        let ds = []

        const response = await api.LAY_DSHD_THEO_MA_GD(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          ds = data.data
          if (ds.length > 0) {
            let res = ds[0]
            this.hdkh_id = res.hdkh_id
            this.txtMaGD = res.ma_gd;
            this.txtMaKH = res.ma_kh;

            if (res.khachhang_id !== "") {
              this.khachhang_id = res.khachhang_id;
            } else {
              this.khachhang_id = 0;
            }
            //Mã hợp đồng - hiếu 23/08/2010
            this.txtMaHD = res.ma_hd;
            if (res.ngayhethan_gt !== "") {
              this.txtNgayHetHan = new Date(res.ngayhethan_gt).toLocaleString('en-GB', {dateStyle: 'short'});
            }

            this.txtTenKH = res.ten_kh;
            this.txtDiaChiKH = res.diachi_kh;
            // HienThiTTDiaChi(tchopdong.LAY_DS_DIACHI_THEO_HDKH_ID(hdkh_id), 1);
            await this.LAY_DS_DIACHI_THEO_HDKH_ID();
            this.txtTelKH = res.so_dt;
            this.txtFaxKH = res.so_fax;

            this.txtHoKhau = res.hokhau;
            this.txtNoiCapHK = res.noicaphk;


            if (res.ngay_hk !== "") {
              this.txtNgayCapHK = new Date(res.ngay_hk).toLocaleString('en-GB', {dateStyle: 'short'});
            } else {
              this.txtNgayCapHK = "";
            }

            if (res.ngay_sn !== "") {
              this.txtNgaySN = new Date(res.ngay_sn).toLocaleString('en-GB', {dateStyle: 'short'});
            } else {
              this.txtNgaySN = "";
            }

            this.dtpNgayLapHD = new Date(res.ngaylap_hd).toLocaleString('en-GB', {dateStyle: 'short'});

            if (res.ngay_yc !== "") {
              this.dtpNgayYeuCau = new Date(res.ngay_yc).toLocaleString('en-GB', {dateStyle: 'short'});
            }
            this.txtNguoiDD = res.nguoi_dd;
            this.txtChucDanhDD = res.chucdanh_dd;
            if (res.gioitinh !== null) {
              this.gioitinh = res.gioitinh;
              if (res.gioitinh === 0) {
                this.rdoGTNam = true;
              } else if (res.gioitinh === 1) {
                this.rdoGTNu = true;
              }
            } else
              this.rdoGTNone = true;
            if (res.quoctich_id !== null) {
              this.cboQuocTich.value = res.quoctich_id;
            }
            if (res.dantoc_id !== null) {
              this.cboDanToc.value = res.dantoc_id;
            }
            if (res.nganhnghe_id !== null) {
              this.cboNganhNgheLoc.value = res.nganhnghe_id;
              this.nganhnghe_id = res.nganhnghe_id;
            }

            if (res.TNC1_ID !== null) {
              this.TNC1_ID = res.tnc1_id;
            }
            if (res.TNC2_ID !== null) {
              this.TNC2_ID = res.tnc2_id;
            }
            if (res.TNC3_ID !== null) {
              this.TNC3_ID = res.tnc3_id;
            }
            if (res.TNC4_ID !== null) {
              this.TNC4_ID = res.tnc4_id;
            }
            //
            this.txtDiaChiNDD = res.diachi_ndd;
            if (res.ngayhethan_gt !== null && res.ngayhethan_gt !== "") {
              this.txtNgayHetHan = new Date(res.ngayhethan_gt).toLocaleString('en-GB', {dateStyle: 'short'});
            } else {
              this.txtNgayHetHan = "";
            }
            this.txtMaSTKH = res.mst;
            this.txtSoTKKH = res.stk;
            this.cboNganHangKH.value = res.nganhang_id;
            if (res.nganhang_id !== null) {
              this.chkNganHangKH = true;
            }
            this.txtSoGT = res.so_gt;
            this.txtDacTrung = res.dactrung;
            this.txtGhiChuKH = res.ghichu;
            //////

            if (res.ngaycap !== "") {
              this.txtNgayCap = new Date(res.ngaycap).toLocaleString('en-GB', {dateStyle: 'short'});
            } else {
              this.txtNgayCap = "";
            }
            this.cboGiayToKH.value = res.loaigt_id;
            this.txtNoiCap = res.noicap;
            if (res.loaikh_id !== null) {
              this.cboLoaiKH.value = res.loaikh_id;
            }
            await this.HienThiDS_HDTT_CBO();
            await this.HienthiTTHopDongTT();
            await this.HienthiTTHopDongTB();
            // HienThiDanhSacHDTT(tchopdong.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(hdkh_id));
            await this.HienThiDanhSacHDTB();
          } else {
            this.$toast.error("Không tìm thấy dữ liệu cho mã giao dịch " + this.txtMaGD + " !");
            await this.Clear();
          }
        } else {
          this.$toast.error("Không tìm thấy dữ liệu cho mã giao dịch " + this.txtMaGD + " !");
          console.log('Không có dữ liệu')
          await this.Clear();
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
        this.$toast.error("Không tìm thấy dữ liệu cho mã giao dịch " + this.txtMaGD + " !");
        await this.Clear();
      } finally {
        this.loading(false)
      }
    },

    async HienthiTTHopDongTT() {
      try {
        let ds = []
        const response = await api.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.axios, this.hdkh_id)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          ds = data.data
          if (ds.length > 0) {
            let res = ds[0]

            this.hdtt_id = res.hdtt_id;
            this.txtMaTT = res.ma_tt;
            if (res.thanhtoan_id !== null) {
              this.thanhtoan_id = res.thanhtoan_id;
            } else {
              this.thanhtoan_id = 0;
            }
            this.txtTenTT = res.ten_tt;
            this.txtDiaChiTT = res.diachi_tt;
            // HienThiTTDiaChi(tchopdong.LAY_DS_DIACHI_THEO_HDTT_ID(hdtt_id), 2);
            await this.LAY_DS_DIACHI_THEO_HDTT_ID();
            this.txtMST = res.mst;
            this.txtSTK = res.stk;
            this.txtChu_TK = res.chu_tk;

            if (res.nganhang_id !== null) {
              this.cboNganHang.value = res.nganhang_id;
            }
            this.cboHTTT.value = res.httt_id;
            await this.cboHTTT_SelectedValueChanged();
            this.cboDonViQL.value = res.donvi_id;
            this.txtMaTBDD = res.matb_dd;
            //Hiếu bổ sung 1 số thông tin 23/08/2010
            this.txtDiaChiCT = res.diachi_ct;

            this.txtGhiChuTT = res.ghichu;

            if (res.nhanvien_id !== null) {
              await this.mapIDNhanVien(res.nhanvien_id)
              // this.txtMaNVTC = checkdata.MAP_ID("ma_nv", DatabaseConstants.DB1 + ".nhanvien", "where nhanvien_id =" + nhanvientc_id.ToString());
            } else {
              this.txtMaNVTC = "";
            }
            if (res.tuyenthu_id !== null) {
              this.tuyenthu_id = res.tuyenthu_id;
            } else {
              this.tuyenthu_id = 0;
            }

            // this.txtMaTuyen = checkdata.MAP_ID("ma_tuyen", DatabaseConstants.DB2 + ".tuyenthu", "where tuyenthu_id =" + tuyenthu_id.ToString());
            await this.mapIDTuyenThu(res.tuyenthu_id)

            this.dsHDTT = ds;
          } else {
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },

    async HienthiTTHopDongTTGrid() {
      try {
        let post = {
          "hdtt_id": this.hdtt_id
        }
        let ds = []
        const response = await api.LAY_DS_HOPDONG_TT_THEO_HDTT_ID(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          ds = data.data
          if (ds.length > 0) {
            let res = ds[0]

            this.hdtt_id = res.hdtt_id;
            this.txtMaTT = res.ma_tt;
            if (res.thanhtoan_id !== null) {
              this.thanhtoan_id = res.thanhtoan_id;
            } else {
              this.thanhtoan_id = 0;
            }
            this.txtTenTT = res.ten_tt;
            this.txtDiaChiTT = res.diachi_tt;
            // HienThiTTDiaChi(tchopdong.LAY_DS_DIACHI_THEO_HDTT_ID(hdtt_id), 2);
            await this.LAY_DS_DIACHI_THEO_HDTT_ID();
            this.txtMST = res.mst;
            this.txtSTK = res.stk;
            this.txtChu_TK = res.chu_tk;

            if (res.nganhang_id !== null) {
              this.cboNganHang.value = res.nganhang_id;
            }
            this.cboHTTT.value = res.httt_id;
            this.cboDonViQL.value = res.donvi_id;
            this.txtMaTBDD = res.matb_dd;
            //Hiếu bổ sung 1 số thông tin 23/08/2010
            this.txtDiaChiCT = res.diachi_ct;

            this.txtGhiChuTT = res.ghichu;

            if (res.nhanvien_id !== null) {
              await this.mapIDNhanVien(res.nhanvien_id)
              // this.txtMaNVTC = checkdata.MAP_ID("ma_nv", DatabaseConstants.DB1 + ".nhanvien", "where nhanvien_id =" + nhanvientc_id.ToString());
            } else {
              this.txtMaNVTC = "";
            }
            if (res.tuyenthu_id !== null) {
              this.tuyenthu_id = res.tuyenthu_id;
            } else {
              this.tuyenthu_id = 0;
            }

            // this.txtMaTuyen = checkdata.MAP_ID("ma_tuyen", DatabaseConstants.DB2 + ".tuyenthu", "where tuyenthu_id =" + tuyenthu_id.ToString());
            await this.mapIDTuyenThu(res.tuyenthu_id)

            // this.dsHDTT = ds;
          } else {
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
        this.loading(false);
      }
    },

    async HienthiTTHopDongTB() {
      try {
        let ds = []
        // let post = {
        //   "in_hdkh_id": this.hdkh_id,
        //   "in_tthd_id": this.tthd_id
        // }
        let post = {
          "in_hdkh_id": this.hdkh_id,
          "in_tthd_id": this.tthd_id
        }
        // let post = {
        //   "in_hdkh_id": this.hdkh_id,
        //   "in_tthd_id": 5
        // }
        const response = await api.LAY_DS_HDTB_THEO_HDKH_TTHD(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          ds = data.data
          if (ds.length > 0) {
            let res = ds[0];

            this.hdtb_id = res.hdtb_ID;
            if (res.thuebao_ID !== null) {
              this.thuebao_id = res.thuebao_ID;
            } else {
              this.thuebao_id = 0;
            }
            this.txtTenTB = res.ten_TB;
            this.txtDiaChiTB = res.diachi_TB;
            // HienThiTTDiaChi(tchopdong.LAY_DS_DIACHI_THEO_HDTB_ID(hdtb_id), 3);
            await this.LAY_DS_DIACHI_THEO_HDTB_ID();
            this.dichvuvt_id = res.dichvuvt_ID;
            this.cboDichVuVT.value = this.dichvuvt_id;
            this.cboKieuLD.value = res.kieuld_ID;
            this.cboDoituongTB.value = res.doituong_ID;
            this.cboTT.value = res.hdtt_ID;
            this.cboDaiVT.value = res.donvi_ID;
            this.txtGhiChuTB = res.ghichu;
            this.cboLoaiTB.value = res.loaitb_ID;
            if (res.ngay_HT !== null) {
              this.dtpNgayBG = new Date(res.ngay_HT).toLocaleString('en-GB', {dateStyle: 'short'});
            }
            //Hiếu bổ sung một số thông tin thuê bao 23/08/2010
            if (res.ngay_SN !== null) {
              this.txtNgaySNTB = new Date(res.ngay_SN).toLocaleString('en-GB', {dateStyle: 'short'});
            } else {
              this.txtNgaySNTB = "";
            }
            this.txtDiaChiLD = res.diachi_LD;

            await this.HT_DichVuGT();
            await this.HT_DichVuKhac();

            // HienThiTTMoRong(ds.Tables[0].Rows[0]["ma_tb"].ToString(), hdtb_id, dichvuvt_id);
            await this.HienThiTTMoRong(res.ma_TB, this.hdtb_id, this.dichvuvt_id)
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
        console.log(e)
      } finally {

      }
    },

    async HienthiTTHopDongTBGrid() {
      try {
        let ds = []

        let post = {
          "hdtb_id": this.hdtb_id
        }
        const response = await api.LAY_DS_HOPDONG_TB_THEO_HDTB_ID(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          ds = data.data
          if (ds.length > 0) {
            let res = ds[0];

            this.hdtb_id = res.hdtb_id;
            if (res.thuebao_id !== null) {
              this.thuebao_id = res.thuebao_ID;
            } else {
              this.thuebao_id = 0;
            }
            this.txtTenTB = res.ten_tb;
            this.txtDiaChiTB = res.diachi_tb;
            // HienThiTTDiaChi(tchopdong.LAY_DS_DIACHI_THEO_HDTB_ID(hdtb_id), 3);
            await this.LAY_DS_DIACHI_THEO_HDTB_ID();
            this.dichvuvt_id = res.dichvuvt_id;
            this.cboDichVuVT.value = this.dichvuvt_id;
            this.cboKieuLD.value = res.kieuld_id;
            this.cboDoituongTB.value = res.doituong_id;
            this.cboTT.value = res.hdtt_id;
            this.cboDaiVT.value = res.donvi_id;
            this.txtGhiChuTB = res.ghichu;
            this.cboLoaiTB.value = res.loaitb_id;
            if (res.ngay_ht !== null) {
              this.dtpNgayBG = new Date(res.ngay_ht).toLocaleString('en-GB', {dateStyle: 'short'});
            } else {
              this.dtpNgayBG = "";
            }
            //Hiếu bổ sung một số thông tin thuê bao 23/08/2010
            if (res.ngay_sn !== null) {
              this.txtNgaySNTB = new Date(res.ngay_sn).toLocaleString('en-GB', {dateStyle: 'short'});
            } else {
              this.txtNgaySNTB = "";
            }
            this.txtDiaChiLD = res.diachi_ld;

            await this.HT_DichVuGT();
            await this.HT_DichVuKhac();

            // HienThiTTMoRong(ds.Tables[0].Rows[0]["ma_tb"].ToString(), hdtb_id, dichvuvt_id);
            await this.HienThiTTMoRong(res.ma_tb, this.hdtb_id, this.dichvuvt_id)
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
        console.log(e)
      } finally {
        this.loading(false)
      }
    },

    async HienThiTTMoRong(ma_tb, hdtb_id, dichvuvt_id) {
      // await this.LAY_DS_HDTB_CD_THEO_HDTB_ID(ma_tb);
      // await this.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(ma_tb);
      // await this.LAY_DS_HDTB_MGWAN_THEO_HDTB_ID(ma_tb);
      // await this.LAY_DS_HDTB_TSL_THEO_HDTB_ID(ma_tb);
      // await this.lay_ds_hdtb_GP_theo_hdtb_id(ma_tb);
      // await this.LAY_DS_HDTB_IMS_THEO_HDTB_ID(ma_tb);
      if (dichvuvt_id === DichVuVienThong.CO_DINH) {
        await this.LAY_DS_HDTB_CD_THEO_HDTB_ID(ma_tb);
      } else if (dichvuvt_id === DichVuVienThong.ADSL) {
        await this.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(ma_tb);
      } else if (dichvuvt_id === DichVuVienThong.MEGAWAN) {
        await this.LAY_DS_HDTB_MGWAN_THEO_HDTB_ID(ma_tb);
      } else if (dichvuvt_id === DichVuVienThong.TSL) {
        await this.LAY_DS_HDTB_TSL_THEO_HDTB_ID(ma_tb);
      } else if (dichvuvt_id === DichVuVienThong.GPHONE) {
        await this.lay_ds_hdtb_GP_theo_hdtb_id(ma_tb);
      } else if (dichvuvt_id === DichVuVienThong.IMS) {
        await this.LAY_DS_HDTB_IMS_THEO_HDTB_ID(ma_tb);
      } else if (dichvuvt_id === 7) {
      } else {
        await this.LAY_DS_HDTB_CD_THEO_HDTB_ID(ma_tb);
      }
    },

    async LAY_DS_HDTB_CD_THEO_HDTB_ID(ma_tb) {
      try {
        let ds = []
        const response = await api.getDsThueBaoCoDinh(this.axios, this.hdtb_id)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          ds = data.data
          if (ds.length > 0) {
            let res = ds[0];

            this.txtMaTB = ma_tb;
            this.txtNeCD = res.ne;
            // ne = txtNeCD.Text;
            this.chkVSVX = (res.mc_tbcd_id === "1");
            this.txtLineTrang_CD = res.ma_lt;
            this.txtmaTN_CD = res.matb_tn;
            this.txtmadoicap_CD = res.madoicap;
            this.txtDoiCap = res.doicap_goc;
            this.txtHopCap = res.hopcap_p;
            this.txtSoMetDay = res.culy;
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {

      }
    },
    async LAY_DS_HDTB_ADSL_THEO_HDTB_ID(ma_tb) {
      try {
        let ds = []
        //TODO DATA TEST
        const response = await api.getDsThueBaoAdsl(this.axios, this.hdtb_id)
        // const response = await api.getDsThueBaoAdsl(this.axios, 2115120)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          ds = data.data
          if (ds.length > 0) {
            let res = ds[0]

            if (res.port_id !== null) {
              this.port_id = res.port_id;
            } else {
              this.port_id = 0;
            }
            this.port_id_ht = this.port_id;
            this.txtMaTB = ma_tb;
            this.txtSoMayTNAdsl = res.so_dt;
            this.txtEmailAdsl = res.email;
            this.cboGoiCuocAdsl.value = res.tocdo_id;
            this.cboMucCuoc.value = res.muccuoc_id;
            this.cboLoaiTB.value = res.thietbidc_id;
            this.txtPort = res.port;

            //Hiếu bổ sung hiển thị thông tin lên các ô text ADSL 24/08/2010
            this.txtLineTrang_ADSL = res.ma_lt;
            this.txtmadoicap_ADSL = res.madoicap;
            this.txtmaTN_ADSL = res.matb_tn;
            this.txtMatKhauADSL = res.password;
            this.txtDoiCapADSL = res.doicap_goc;
            this.txtHopCapADSL = res.hopcap_p;
            this.txtCuLyADSL = res.culy;
            if (res.bras_id !== null)
              this.cboBrasADSL.value = res.bras_id;
            if (res.dslam_id !== null)
              this.cboDSLAM.value = res.dslam_id;
            this.txtIPADSL = res.ip;
            this.txtSlotADSL = res.slot;
            this.txtPort = res.port;
            this.txtVPIADSL = res.vpi;
            this.txtVCIADSL = res.vci;
            this.txtSystem = res.system;
            this.txtRack = res.rack;
            this.txtShelf = res.shelf;
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async LAY_DS_HDTB_MGWAN_THEO_HDTB_ID(ma_tb) {
      try {
        let ds = []
        //TODO DATA TEST
        // const response = await api.getDsThueBaoMWAN(this.axios, 5503506) //this.hdtb_id
        const response = await api.getDsThueBaoMWAN(this.axios, this.hdtb_id)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          ds = data.data

          if (ds.length > 0) {
            let res = ds[0]

            this.txtMaTB = ma_tb;
            this.txtHuongKNWan = res.huong_kn;
            this.txtLineTrang_Wan = res.ma_lt;
            this.txtmaTN_Wan = res.matb_tn;
            this.txtmadoicap_wan = res.madoicap;
            this.cboTocDo.value = res.tocdo_id;
            this.cboLoaiModem.value = res.loaimd_id;
            this.cboLoaiKenh.value = res.loaikenh_id;
            this.cboLoaiTBiWan.value = res.thietbidc_id;
            this.cboChuQuanWan.value = res.chuquan_id;
            this.cboThoiHanWan.value = res.thoihan_id;
            this.cboTrangBiWan.value = res.trangbi_id;
            if (res.pe_id !== null) {
              this.cboRouter.value = res.pe_id;
            } else {
              this.cboRouter.value = 0;
            }
            this.txtPortPE = res.port_pe;
            this.txtSlotPE = res.slot_pe;
            this.txtWAN_IP = res.wan_ip;
            this.txtLAN_IP = res.lan_ip;
            this.txtDSLAMWan = res.dslam;
            this.txtPortSHSL = res.port;
            this.txtTenTbi = res.ten_tbi;
            this.txtVpi = res.svlan;
            this.txtVci = res.cvlan;
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async LAY_DS_HDTB_IMS_THEO_HDTB_ID(ma_tb) {
      try {
        let ds = []
        //TODO DATA TEST
        // const response = await api.getDsThueBao_IMS(this.axios, 2600637)
        const response = await api.getDsThueBao_IMS(this.axios, this.hdtb_id)//this.hdtb_id
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          ds = data.data
          if (ds.length > 0) {
            let res = ds[0];

            this.txtMaTB = ma_tb;
            if (res.trangbi_id !== null)
              this.cboTrangBiIMS.value = res.trangbi_id;
            this.txtDoiCapIms = res.doicap_goc;
            this.txtHopCapIms = res.hopcap_p;
            this.txtCuLyIms = res.culy;

            //Thong tin thiet bi MiniDSLam
            if (res.bras_id !== null)
              this.cboBrasIms.value = res.bras_id;
            if (res.dslam_id !== null)
              this.cboDsLamIms.value = res.dslam_id;
            if (res.port_id !== null)
              this.port_id = res.port_id;
            else
              this.port_id = 0;
            if (res.vci_vpi_id !== null)
              this.vci_vpi_id = res.vci_vpi_id;
            else
              this.vci_vpi_id = 0;
            this.txtPortIms = res.port;
            this.txtSlotIms = res.slot;
            this.txtVpiIms = res.vpi;
            this.txtVciIms = res.vci;
            if (res.system !== null) {
              this.txtSystemIms = res.system;
              this.system = res.system;
            } else {
              this.system = "";
            }
            this.txtRackIms = res.rack;
            this.txtShelfIms = res.shelf;
            // thông tin cấu hình trên VTN
            this.txtUsername = res.username;
            this.txtPassword = res.password;
            if (res.thietbi_id !== null)
              this.cboThietBiIms.value = res.thietbi_id;
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log(e)
        console.log('Lấy danh sách không thành công')
      } finally {

      }
    },
    async lay_ds_hdtb_GP_theo_hdtb_id(ma_tb) {
      try {
        let ds = []
        // const response = await api.getDsThueBao_GP(this.axios, 1409897)
        const response = await api.getDsThueBao_GP(this.axios, this.hdtb_id)//this.hdtb_id
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          ds = data.data
          if (ds.length > 0) {
            let res = ds[0];
            this.txtSoSimGphone = res.simcard;
            this.checkVSVX = (res.mc_tbgp_id === "1");
            this.txtCell1 = res.cell1;
            this.txtCell2 = res.cell2;
            this.txtToaDo = res.toado;
            this.txtMaTB = ma_tb;
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async LAY_DS_HDTB_TSL_THEO_HDTB_ID(ma_tb) {
      try {
        let ds = []
        //TODO DATA TEST
        // const response = await api.getDsThueBao_TSL(this.axios, 2052330) //this.hdtb_id
        const response = await api.getDsThueBao_TSL(this.axios, this.hdtb_id)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          ds = data.data
          if (ds.length > 0) {
            let res = ds[0]

            // this.chitietTSL = res;

            this.txtMaTB = ma_tb;
            this.cboTocDoKenhTSL.value = res.tocdo_id;
            this.cboLoaiKenhTSL.value = res.loaikenh_id;
            this.cboThoiHan.value = res.thoihan_id;
            this.cboTrangBi.value = res.trangbi_id;
            this.cboChuQuan.value = res.chuquan_id;
            this.cboLoaiTBTSL.value = res.thietbidc_id;
            this.slot_cn = res.slot_cn;
            this.port_cn = res.port_cn;
            this.timeslot_cn = res.timeslot_cn;
            this.mapping_cn = res.mapping_cn;
            this.txtLineTrang_TSL = res.ma_lt;

            for (let i = 0; i < ds.length; i++) {
              if (res.daucuoi_id === 1)//Nếu là điểm đầu
              {
                this.diachidau_id = res.diachi_id;
                this.diachidau = res.diachi;
              } else {
                this.diachicuoi_id = res.diachi_id;
                this.diachicuoi = res.diachi;
              }
            }
            // HienThiTTDiaChi_TSL();
            //TODO DATA TEST
            await this.HienThiTTDiaChi_TSL(this.diachidau_id, this.diachicuoi_id);
            // await this.HienThiTTDiaChi_TSL(536246, 536246);
            for (let i = 0; i < ds.length; i++) {
              if (res.daucuoi_id === 1)//Nếu là điểm đầu
              {
                this.slot_dau = res.slot;
                this.port_dau = res.port;
                this.timeslot_dau = res.timeslot;
                this.ten_tbi_dau = res.ten_tbi;

                this.doicap_dau = res.doicap_goc;
                this.hopcap_dau = res.hopcap_p;
                this.culy_dau = res.culy;
              } else {
                this.slot_cuoi = res.slot;
                this.port_cuoi = res.port;
                this.ten_tbi_cuoi = res.ten_tbi;
                this.timeslot_cuoi = res.timeslot;

                this.doicap_cuoi = res.doicap_p;
                this.hopcap_cuoi = res.hopcap_p;
                this.culy_cuoi = res.culy;
              }
            }

            this.chitietTSL = {
              slot_dau: this.slot_dau,
              port_dau: this.port_dau,
              timeslot_dau: this.timeslot_dau,
              ten_tbi_dau: this.ten_tbi_dau,

              slot_cuoi: this.slot_cuoi,
              port_cuoi: this.port_cuoi,
              timeslot_cuoi: this.timeslot_cuoi,
              ten_tbi_cuoi: this.ten_tbi_cuoi,

              slot_cn: this.slot_cn,
              port_cn: this.port_cn,
              timeslot_cn: this.timeslot_cn,
              mapping_cn: this.mapping_cn,

              doicap_dau: this.doicap_dau,
              doicap_cuoi: this.doicap_cuoi,
              hopcap_dau: this.hopcap_dau,
              hopcap_cuoi: this.hopcap_cuoi,
              culy_dau: this.culy_dau,
              culy_cuoi: this.culy_cuoi,
              donvi_dau_id: this.donvi_dau_id,
              donvi_cuoi_id: this.donvi_cuoi_id,
              //Hiếu bổ sung thêm một số thông tin TSL ngày 12/09/2010
              diachidau: this.diachidau,
              apdau_id: this.apdau_id,
              phodau_id: this.phodau_id,
              phuongdau_id: this.phuongdau_id,
              quandau_id: this.quandau_id,
              tinhdau_id: this.tinhdau_id,

              diachicuoi: this.diachicuoi,
              apcuoi_id: this.apcuoi_id,
              phocuoi_id: this.phocuoi_id,
              phuongcuoi_id: this.phuongcuoi_id,
              quancuoi_id: this.quancuoi_id,
              tinhcuoi_id: this.tinhcuoi_id,
              kieu: this.kieu,
            }
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log(e)
        console.log('Lấy danh sách không thành công')
      } finally {

      }
    },

    async HienThiTTDiaChi_TSL(diachidau, diachicuoi) {
      let post = {
        "p_ds_para": "{\"DIACHIDAU_ID\":" + diachidau + ",\"DIACHICUOI_ID\":" + diachicuoi + "}"
      }
      try {
        const response = await api.LAY_THONGTIN_DIACHI(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          console.log(JSON.parse(data.data))
          let dsDau = []
          let dsCuoi = []
          if (JSON.parse(data.data).RESULT.DIACHIDAU.length > 0) {
            dsDau = JSON.parse(data.data).RESULT.DIACHIDAU;
            let res = dsDau[0];
            this.diachidau_id = res.diachi_id;
            this.tinhdau_id = res.tinh_id;
            this.quandau_id = res.quan_id;
            this.phuongdau_id = res.phuong_id;
            if (res.pho_id !== null)
              this.phodau_id = res.pho_id;
            else
              this.phodau_id = 0;
            if (res.ap_id !== null)
              this.apdau_id = res.ap_id;
            else
              this.apdau_id = 0;
            if (res.khu_id !== null)
              this.khudau_id = res.khu_id;
            else
              this.khudau_id = 0;
            this.sonhadau = res.sonha;
          }
          if (JSON.parse(data.data).RESULT.DIACHICUOI.length > 0) {
            dsCuoi = JSON.parse(data.data).RESULT.DIACHICUOI;
            let res = dsCuoi[0];
            this.diachicuoi_id = res.diachi_id;
            this.tinhcuoi_id = res.tinh_id;
            this.quancuoi_id = res.quan_id;
            this.phuongcuoi_id = res.phuong_id;
            if (res.pho_id !== null)
              this.phocuoi_id = res.pho_id;
            else
              this.phocuoi_id = 0;
            if (res.ap_id !== null)
              this.apcuoi_id = res.ap_id;
            else
              this.apcuoi_id = 0;
            if (res.khu_id !== null)
              this.khucuoi_id = res.khu_id;
            else
              this.khucuoi_id = 0;
            this.sonhacuoi = res.sonha;
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },

    async LAY_DS_DIACHI_THEO_HDKH_ID() {
      let ds = []
      try {
        const response = await api.LAY_DS_DIACHI_THEO_HDKH_ID(this.axios, this.hdkh_id)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          ds = data.data
          // console.log(result)
          if (ds.length > 0) {
            let res = ds[0];
            this.diachikh_id = res.diachi_id;
            this.tinhkh_id = res.tinh_id;
            this.quankh_id = res.quan_id;
            this.phuongkh_id = res.phuong_id;
            if (res.pho_id !== null) {
              this.phokh_id = res.pho_id;
            } else {
              this.phokh_id = 0;
            }
            if (res.ap_id !== null) {
              this.apkh_id = res.ap_id;
            } else {
              this.apkh_id = 0;
            }
            if (res.khu_id !== null) {
              this.khukh_id = res.khu_id;
            } else {
              this.khukh_id = 0;
            }
            if (res.block !== null) {
              this.blockkh = res.block;
            }
            if (res.tang !== null) {
              this.tangkh = res.tang;
            }
            if (res.phong !== null) {
              this.sophongkh = res.phong;
            }
            if (res.mota !== null) {
              this.motadckh = res.mota;
            }
            this.sonhakh = res.sonha;
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async LAY_DS_DIACHI_THEO_HDTT_ID() {
      let ds = []
      try {
        const response = await api.LAY_DS_DIACHI_THEO_HDTT_ID(this.axios, this.hdtt_id)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          ds = data.data

          if (ds.length > 0) {
            let res = ds[0];
            this.diachitt_id = res.diachi_id;
            this.tinhtt_id = res.tinh_id;
            this.quantt_id = res.quan_id;
            this.phuongtt_id = res.phuong_id;
            if (res.pho_id !== null) {
              this.phott_id = res.pho_id;
            } else {
              this.phott_id = 0;
            }
            if (res.ap_id !== null) {
              this.aptt_id = res.ap_id;
            } else {
              this.aptt_id = 0;
            }
            if (res.khu_id !== null) {
              this.khutt_id = res.khu_id;
            } else {
              this.khutt_id = 0;
            }
            if (res.block !== null) {
              this.blocktt = res.block;
            }
            if (res.tang !== null) {
              this.tangtt = res.tang;
            }
            if (res.phong !== null) {
              this.sophongtt = res.phong;
            }
            if (res.mota !== null) {
              this.motadctt = res.mota;
            }
            this.sonhatt = res.sonha;
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    async LAY_DS_DIACHI_THEO_HDTB_ID() {
      let ds = []
      let post = {
        "in_hdtb_id": this.hdtb_id
      }
      try {
        const response = await api.LAY_DS_DIACHI_THEO_HDTB_ID(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          ds = data.data
          // console.log(result)
          if (ds.length > 0) {
            let res = ds[0];
            //Hiếu- thêm phần địa chỉ thuê bao + địa chỉ lắp đặt 21/07/2010
            this.diachitb_id = res.diachi_id;
            this.tinhtb_id = res.tinh_id;
            this.quantb_id = res.quan_id;
            this.phuongtb_id = res.phuong_id;
            if (res.pho_id !== null) {
              this.photb_id = res.pho_id;
            } else {
              this.phott_id = 0;
            }
            if (res.ap_id !== null) {
              this.aptb_id = res.ap_id;
            } else {
              this.aptb_id = 0;
            }
            if (res.khu_id !== null) {
              this.khutb_id = res.khu_id;
            } else {
              this.khutb_id = 0;
            }
            if (res.block !== null) {
              this.blocktb = res.block;
            }
            if (res.tang !== null) {
              this.tangtb = res.tang;
            }
            if (res.phong !== null) {
              this.sophongtb = res.phong;
            }
            if (res.mota !== null) {
              this.motadctb = res.mota;
            }
            this.sonhatb = res.sonha;
            //Địa chỉ lắp đặt
            this.diachild_id = res.diachild_id;
            this.tinhld_id = res.tinhld_id;
            this.quanld_id = res.quanld_id;
            this.phuongld_id = res.phuongld_id;
            if (res.phold_id !== null) {
              this.phold_id = res.phold_id;
            } else {
              this.phold_id = 0;
            }
            if (res.apld_id !== null) {
              this.apld_id = res.apld_id;
            } else {
              this.apld_id = 0;
            }
            if (res.khuld_id !== null) {
              this.khuld_id = res.khuld_id;
            } else {
              this.khuld_id = 0;
            }
            if (res.block_ld !== null) {
              this.blockld = res.block_ld;
            }
            if (res.tang_ld !== null) {
              this.tangld = res.tang_ld;
            }
            if (res.phong_ld !== null) {
              this.sophongld = res.phong_ld;
            }
            if (res.mota_ld !== null) {
              this.motadcld = res.mota_ld;
            }
            this.sonhald = res.sonha_ld;
          }

        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },

    async HT_DichVuGT() {
      this.dichvuCongthem = ''
      let dsDichVu = []
      try {
        let post = {
          "vhdtb_id": this.hdtb_id
        }
        const response = await api.LAY_DS_DANGKY_DVGT(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          dsDichVu = data.data
          if (dsDichVu.length > 0) {
            for (let i of dsDichVu) {
              this.dichvuCongthem += i.ten_dvgt
              if (i.ttth === '1') {
                this.dichvuCongthem += ' (Đã CĐ)'
              } else {
                this.dichvuCongthem += ' (Chưa CĐ)'
              }
              this.dichvuCongthem += '\n'
            }
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log(e)
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },

    async HT_DichVuKhac() {
      let dsDichVu = []
      this.dichvuKhac = ''
      try {
        let post = {
          "hd_id": this.hdtb_id,
          "loaiid": 1
        }
        const response = await api.LAY_DS_DANGKY_DVK(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          dsDichVu = data.data
          if (dsDichVu.length > 0) {
            for (let i of dsDichVu) {
              this.dichvuKhac += i.ten_dvkhac
              this.dichvuKhac += '\n'
            }
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },

    async HienThiDS_HDTT_CBO() {
      try {
        this.cboTT.option = []
        const response = await api.getDsThanhToan(this.axios, this.hdkh_id)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          this.cboTT.option = data.data
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
        if (this.cboTT.option.length > 0) {
          this.cboTT.value = this.cboTT.option[0].ID
        }
      }
    },

    async HienThiDanhSacHDTB() {
      try {
        this.dsHDTB = []
        //TODO DATA TEST
        // let post = {
        //   "hdkh_id": 3244817,
        //   "nhanvien_id": 6874,
        //   "tthd_id": 2
        // }
        let post = {
          "hdkh_id": this.hdkh_id,
          "nhanvien_id": this.nhanvienId,
          "tthd_id": this.tthd_id
        }
        const response = await api.LAY_DS_HDTB_HTHS(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          this.dsHDTB = data.data
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },


    async mapIDTuyenThu(in_dk) {
      try {
        const response = await api.map_id(this.axios, {
          id_neo: "ma_tuyen",
          in_table: "tuyenthu",
          in_dk: `where tuyenthu_id =${in_dk} and phanvung_id = ${this.phanvungId}`
        })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          // this.txtMaTuyen_KHNQ = data.data;
          this.txtMaTuyen = data.data
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {

      }
    },
    async mapIDNhanVien(in_dk) {
      try {
        const response = await api.map_id(this.axios, {
          id_neo: "ma_nv",
          in_table: "admin.nhanvien",
          in_dk: `where nhanvien_id =${in_dk} and phanvung_id = ${this.phanvungId}`
        })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          // this.txtMaNVTC_KHNQ = data.data
          this.txtMaNVTC = data.data
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {

      }
    },

    async HT_DonVi_loaidv_Combobox(cbo, daivt_id) {
      try {
        cbo.option = []
        let post = {
          "donvi_id": daivt_id,
          "loaidv_id": LOAI_DV.TRAM_VT
        }
        const response = await api.getDsTramVT_CODINH(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          cbo.option = data.data
          if (cbo.option.length > 0) {
            cbo.value = cbo.option[0].donvi_id
          }
        } else {
          console.log('Không có dữ liệu')
        }
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },

    async cboDaiVT_SelectedValueChanged() {
      if (this.cboDaiVT.option.length > 0) {
        let daivt_id = this.cboDaiVT.value;
        await this.HT_DonVi_loaidv_Combobox(this.cboVeTinhIMS, daivt_id);
        await this.HT_DonVi_loaidv_Combobox(this.cboVeTinhCD, daivt_id);
        await this.HT_DonVi_loaidv_Combobox(this.cboVetinhAdsl, daivt_id);
        await this.HT_DonVi_loaidv_Combobox(this.cboVeTinh, daivt_id);
        await this.HT_DonVi_loaidv_Combobox(this.cboVTGPHONE, daivt_id);
      }
    },
    async cboDichVuVT_SelectedValueChanged() {
      // if (+this.tt_tb.dichvuvt_id === 1) {
      //   await this.getDsHDTBCoDinh().then(() => this.loading(false));
      // } else if (+this.tt_tb.dichvuvt_id === 4) {
      //   await this.getDsHDTB_ADSL().then(() => this.loading(false));
      // } else if (+this.tt_tb.dichvuvt_id === 8) {
      //   await this.getDsHDTB_MWAN().then(() => this.loading(false));
      // } else if (+this.tt_tb.dichvuvt_id === 9) {
      //   await this.getDsHDTB_TSL().then(() => this.loading(false));
      // } else if (+this.tt_tb.dichvuvt_id === 10) {
      //   await this.getDsHDTB_GP().then(() => this.loading(false));
      // } else if (+this.tt_tb.dichvuvt_id === 11) {
      //   await this.getDsHDTB_IMS().then(() => this.loading(false));
      // } else if (+this.tt_tb.dichvuvt_id === 7) {
      //   await this.getDsHDTBCoDinh().then(() => this.loading(false));
      // }
    },
    async cboHTTT_SelectedValueChanged() {
      this.checkNganHang = this.cboHTTT.value !== 3;
    },


    async txtMaGD_KeyPress() {
      if (this.txtMaGD !== "") {
        this.loading(true);
        this.watcherActive = false;
        // ds = tchopdong.LAY_DSHD_THEO_MA_GD(txtMaGD.Text, LoaiHopDong.DI_CHUYEN, 0, tthd_id, tt_nd.nhanvien_id, (int)tt_nd.donvi_dl_id);
        // if (ds.Tables[0].Rows.Count == 0)
        //   ds = tchopdong.LAY_DS_HOPDONG_THEO_MA_GD_TSL(txtMaGD.Text, LoaiHopDong.DI_CHUYEN, tt_nd.donvi_id, tthd_id);
        await this.HienThiTTHopDongKH();
        this.watcherActive = true;
      }
      //
    },
    async lvwThanhToan_SelectedIndexChanged(item) {
      // if (idx >= 0) {
      //   this.loading(true)
      //   this.hdtt_id = item.hdtt_id;
      // await this.LAY_DS_HOPDONG_TT_THEO_HDTT_ID();
      // await this.HienthiTTHopDongTTGrid();
      // }
      if (item !== undefined) {
        this.loading(true)
        this.hdtt_id = item.data.hdtt_id;
        await this.HienthiTTHopDongTTGrid();
      }
      // console.log(item);
    },
    async lvwThueBao_SelectedIndexChanged(item) {
      this.thuebaoSelected = []
      if (item !== undefined) {
        this.loading(true)
        this.thuebaoSelected.push(item.data);
        this.hdtb_id = item.data.hdtb_id;
        // await this.LAY_DS_HOPDONG_TB_THEO_HDTB_ID();
        await this.HienthiTTHopDongTBGrid();
      }
    },


    async cboNganhNgheLoc_EditValueChanged() {
      // console.log('nganh nghe')
      if (!this.watcherActive) return;
      if (this.nganhnghe_id !== this.cboNganhNgheLoc.value) {
        this.TNC1_ID = 0;
        this.TNC2_ID = 0;
        this.TNC3_ID = 0;
        this.TNC4_ID = 0;
      }
      // this.nganhnghe_id = this.cboNganhNgheLoc.value
      await this.btnNganhNghe_Click();

    },
    async cboBrasADSL_SelectedValueChanged() {
      if (this.cboBrasADSL.option.length > 0) {
        if (this.cboBrasADSL.value == null) return;

        let bras_id = this.cboBrasADSL.value;
        //bangts.HT_DSLAM_ADSL_Combobox(cboDSLAM, bras_id, 1);
        // bangts.HT_DSLAM_Cbo(cboDSLAM, bras_id, 2);
        // this.chkIpTinh = false;
        await this.HT_DSLAM_Cbo(this.cboDSLAM, bras_id);
        // this.txtIPADSL.Text = "";
      }
    },
    async cboBrasIms_SelectedValueChanged() {
      if (this.cboBrasIms.option.length > 0) {
        if (this.cboBrasIms.value == null) return;
        let bras_id = this.cboBrasIms.value;
        //bangts.HT_DSLAM_ADSL_Combobox(cboDsLamIms, bras_id, 1);
        await this.HT_DSLAM_Cbo(this.cboBrasIms, bras_id);
      }
    },
    txtDiaChiTT_KeyPress: function (e) {
      if (e.keyCode === 13) {
      } else {
        this.btnDiaChiTT_Click();
      }
    },
    txtDiaChiKH_KeyPress: function (e) {
      if (e.keyCode === 13) {
      } else {
        this.btnDiaChiKH_Click();
      }
    },
    txtDiaChiNDD_KeyPress: function (e) {
      if (e.keyCode === 13) {
        this.btnDiaChiNDD_Click();
      } else {
      }
    },
    txtDiaChiTB_KeyPress: function (e) {
      if (e.keyCode === 13) {

      } else {
        this.btnDiaChiTB_Click();
      }
    },
    txtDiaChiLD_KeyPress: function (e) {
      if (e.keyCode === 13) {
        this.btnDiaChiLD_Click();
      } else {

      }
    },
    txtMaKH_KeyPress: function (e) {
      if (e.keyCode === 13) {
        if (this.txtMaKH !== '') {
          // this.bntLayMaKh_Click();
        }
      } else {
      }
    },
    txtMaNVTC_KeyPress: function (e) {
      if (e.keyCode === 13) {
        // this.bntLayMaKh_Click();
        this.btnTimMaNVTC_Click();
      } else {
      }
    },


    async Clear() {
      this.txtIPADSL = "";
      this.txtCell1 = "";
      this.txtCell2 = "";
      this.txtToaDo = "";
      this.txtSoSimGphone = "";
      this.hdkh_id = 0;
      this.txtMaGD = "";
      this.txtMaKHxt = "";
      this.khachhang_id = 0;
      this.txtTenKH = "";
      this.txtDiaChiKH = "";
      this.tinhkh_id = 0;
      this.quankh_id = 0;
      this.phuongkh_id = 0;
      this.phokh_id = 0;
      this.apkh_id = 0;
      this.sonhakh = "";
      this.txtTelKH = "";
      this.txtFaxKH = "";

      this.txtNgaySN = "";
      this.txtDacTrung = "";
      this.txtSoGT = "";
      this.txtNgayCap = "";
      this.txtNoiCap = "";
      this.cboGiayToKH.value = this.cboGiayToKH.option[0];
      this.dsHDTT = [];
      this.dsHDTB = [];

      this.hdtt_id = 0;
      this.txtMaTT = "";
      this.thanhtoan_id = 0;
      this.txtTenTT = "";
      this.txtDiaChiTT = "";
      this.tinhtt_id = 0;
      this.quantt_id = 0;
      this.phuongtt_id = 0;
      this.phott_id = 0;
      this.aptt_id = 0;
      this.sonhatt = "";
      this.txtMST = "";
      this.txtSTK = "";
      this.txtChu_TK = "";
      this.txtMaTBDD = "";
      this.cboHTTT.value = this.cboHTTT.option[0];
      this.cboDonViQL.value = this.cboDonViQL.option[0];
      this.cboNganHang.value = this.cboNganHang.option[0];
      this.txtLineTrang_CD = "";
      this.txtLineTrang_Wan = "";
      this.hdtb_id = 0;
      this.thuebao_id = 0;
      this.txtTenTB = "";
      this.txtDiaChiTB = "";
      this.tinhtb_id = 0;
      this.quantb_id = 0;
      this.phuongtb_id = 0;
      this.photb_id = 0;
      this.aptb_id = 0;
      this.sonhatb = "";
      this.vetinhcd_id = 0;
      //dichvuvt_id = DichVuVienThong.CO_DINH;
      //cboDichVuVT.SelectedValue = dichvuvt_id;
      this.dichvuCongthem = '';
      this.dichvuKhac = ''
      this.cboLoaihinhTB.value = this.cboLoaihinhTB.option[0];
      this.cboKieuLD.value = this.cboKieuLD.option[0];
      // Hiếu thêm thông tin địa chỉ lắp đặt 10/07/2010
      this.txtDiaChiLD = "";
      this.tinhld_id = 0;
      this.quanld_id = 0;
      this.phuongld_id = 0;
      this.phold_id = 0;
      this.apld_id = 0;
      this.sonhald = "";
      //dichvuvt_id = DichVuVienThong.CO_DINH;
      //cboDichVuVT.SelectedValue = dichvuvt_id;
      //cboKieuLD.SelectedIndex = 0;
      this.cboDoituongTB.value = this.cboDoituongTB.option[0];
      this.txtSoMayTNAdsl = "";
      // txtSomayWan.Text = "";
      this.txtSoMetDay = "0";

      //doicap = "";
      this.txtLineTrang_TSL = "";
      this.txtLineTrang_ADSL = "";
      //Hiếu bổ sung thêm thông tin: 23/08/2010
      this.txtDiaChiCT = "";
      this.tinhct_id = 0;
      this.quanct_id = 0;
      this.phuongct_id = 0;
      this.phoct_id = 0;
      this.apct_id = 0;
      this.txtChucDanhDD = "";
      this.txtNgayHetHan = "";
      this.txtMaSTKH = "";
      this.txtSoTKKH = "";
      this.txtGhiChuKH = "";
      //txtDacTrung.Text = "";
      this.checkNganHang = false;

      this.txtMaNVTC = "";
      this.txtGhiChuTT = "";
      this.txtNgaySNTB = "";
      //Tsl 18/08/2010
      this.cboThoiHan.value = this.cboThoiHan.option[0];
      this.cboTrangBi.value = this.cboTrangBi.option[0];
      this.cboChuQuan.value = this.cboChuQuan.option[0];
      this.cboLoaiTBTSL.value = this.cboLoaiTBTSL.option[0];
      this.txtMaHD = "";
      this.tinhld_id = 0;
      this.quanld_id = 0;
      this.phuongld_id = 0;
      this.phold_id = 0;
      this.apld_id = 0;
      this.txtGhiChuTB = "";
      this.cboDoituongTB.value = this.cboDoituongTB.option[0];
      this.txtMaTB = "";
      this.checkNgaybg = false;
      this.txtNeCD = "";
      this.txtDoiCap = "";
      this.txtHopCap = "";
      this.txtSoMetDay = "";
      this.checkVSVX = false;
      //Hiếu bổ sung clear form ngày 04/09/2010
      this.txtmaTN_Wan = "";
      this.txtmaTN_CD = "";
      this.txtmadoicap_wan = "";
      this.txtmadoicap_ADSL = "";
      this.txtmadoicap_CD = "";
      if (this.cboDichVuVT.value === 4) {
        this.txtmaTN_ADSL = "";
        this.cboVetinhAdsl = "";
        //chkTucThi.Checked = false;
        if (this.cboGoiCuocAdsl.option.length > 0)
          this.cboGoiCuocAdsl.value = this.cboGoiCuocAdsl.option[0];
        else
          this.cboGoiCuocAdsl = "";
        if (this.cboMucCuoc.option.length > 0)
          this.cboMucCuoc.value = this.cboMucCuoc.option[0];
        else
          this.cboMucCuoc = "";
        this.txtEmailAdsl = "";
        if (this.cboLoaiTB.option.length > 0)
          this.cboLoaiTB.value = this.cboLoaiTB.option[0];
        else
          this.cboLoaiTB = "";
        this.txtMatKhauADSL = "";
        this.txtSoMayTNAdsl = "";
        this.txtDoiCapADSL = "";
        this.txtHopCapADSL = "";
        if (this.cboVetinhAdsl.option.length > 0)
          this.cboVetinhAdsl.value = this.cboVetinhAdsl.option[0];
        else
          this.cboVetinhAdsl = "";
        this.txtCuLyADSL = "";
        if (this.cboBrasADSL.option.length > 0)
          this.cboBrasADSL.value = this.cboBrasADSL.option[0];
        else
          this.cboBrasADSL = "";
        if (this.cboDSLAM.option.length > 0)
          this.cboDSLAM.value = this.cboDSLAM.option[0];
        else
          this.cboDSLAM = "";
        this.txtIPADSL = "";
        this.txtSlotADSL = "";
        this.txtPort = "";
        this.txtSystem = "";
        this.txtRack = "";
        this.txtShelf = "";
        this.txtVPIADSL = "";
        this.txtVCIADSL = "";
      }
      this.txtNguoiDD = "";
      this.txtDiaChiNDD = "";
      this.txtDiaChiCT = "";
      this.txtMaNVTC = "";
      this.txtDiaChiLD = "";
      this.checkNgaybg = false;
      //Hieuetc - 3.10.2010: Clear biến TSL
      this.slot_dau = "";
      this.port_dau = "";
      this.timeslot_dau = "";
      this.ten_tbi_dau = "";
      this.slot_cuoi = "";
      this.port_cuoi = "";
      this.timeslot_cuoi = "";
      this.ten_tbi_cuoi = "";

      this.slot_cn = "";
      this.port_cn = "";
      this.timeslot_cn = "";
      this.mapping_cn = "";

      this.doicap_dau = "";
      this.doicap_cuoi = "";
      this.hopcap_dau = "";
      this.hopcap_cuoi = "";
      this.culy_dau = 0;
      this.culy_cuoi = 0;
      this.donvi_dau_id = 0;
      this.donvi_cuoi_id = 0;
      this.diachidau = "";
      this.apdau_id = 0;
      this.phodau_id = 0;
      this.phuongdau_id = 0;
      this.quandau_id = 0;
      this.tinhdau_id = 0;
      this.diachicuoi = "";
      this.apcuoi_id = 0;
      this.phocuoi_id = 0;
      this.phuongcuoi_id = 0;
      this.quancuoi_id = 0;
      this.tinhcuoi_id = 0;
      //
      this.blockkh = "";
      this.tangkh = "";
      this.sophongkh = "";
      this.motadckh = "";

      this.blockld = "";
      this.tangld = "";
      this.sophongld = "";
      this.motadcld = "";

      this.blocktb = "";
      this.tangtb = "";
      this.sophongtb = "";
      this.motadctb = "";

      this.blocktt = "";
      this.tangtt = "";
      this.sophongtt = "";
      this.motadctt = "";

      //TuấnNA thêm code clear biến -- Ngày 03/12/2010
      this.TNC1_ID = 0;
      this.TNC2_ID = 0;
      this.TNC3_ID = 0;
      this.TNC4_ID = 0;
      this.nganhnghe_id = 0;
    }
  }
}
</script>

<style>
.frm-chon-port .page-content {
  position: inherit !important;
}
</style>
