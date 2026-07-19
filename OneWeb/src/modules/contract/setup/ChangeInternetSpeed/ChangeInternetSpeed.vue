<template src="./ChangeInternetSpeed.html"></template>
<style src="./ChangeInternetSpeed.scss"></style>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from '@/components/breadcrumb'
import API from './API'
import api_installnewsubs from '@/modules/contract/setup/InstallNewSubs/InstallNewSubsAPI.js'
import '@/assets/vendor/jquery/split'
import moment from 'moment'
import gridview from '@/components/Shared/gridview'
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import ThongTinEmail from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue'
//import Payment from "@/modules/contract/setup/Payment/Payment.vue";
import Vue from 'vue'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import popupTraCuuThueBao from '@/modules/contract/setup/SeparationSubscriber/popups/popupTraCuuThueBao.vue' //tham chiếu qua kypc_dev
import ThongTinHenKhachHang from '@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import ThongTinLienHe from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue'
import ChonCongTacVienModal from '@/modules/contract/setup/SurveyRequest/popups/ChonCongTacVien/ChonCongTacVienModal.vue'
import ChonNguoiGTModal from '@/modules/contract/setup/SurveyRequest/popups/ChonNguoiGT/ChonNguoiGTModal.vue'
import InHoSo from '@/modules/print/InHopDong/InHopDong.vue'
import ThueBaoCungMa from '@/modules/EXTS/QUANTRI/ThayDoiThongSoInternet/ThueBaoCungMa/index.vue'
import ChiTietTienTheoKhoanMucModal from '@/modules/contract/setup/ChangeSubsType/popups/ChiTietTienTheoKhoanMucModal.vue'
import frmDangKyGoiDaDVLM from '@/modules/admin/category/NiceNumberStyle/popups/frmDangKyGoiDaDVLM.vue'
import TuvanMyTv from '@/modules/CSKH/TuvanMyTv/TuvanMyTv.vue'
import { LoaiHopDong, KieuLapDat, KHOANMUC_TT, TRANGBI, LoaiHinhTB, DichVuVienThong, TrangThaiHD, TRANGTHAI_DONGBO, TrangThaiTB, KieuTra, DUNG_THU, LOAITBI_ADSL, MUCCUOC, Loai_KM, TOCDO_ADSL } from './ThamSo'
import { KieuCaiDatDVGT } from '@/modules/contract/setup/InstallNewSubs/Enums.js'

Vue.use(DialogPlugin)
export default {
  components: {
    breadcrumb,
    gridview,
    SearchAccount,
    SearchContractModal,
    ThongTinEmail,
    moment,
    DialogPlugin,
    //Payment,
    popupTraCuuThueBao,
    ThongTinHenKhachHang,
    ThongTinLienHe,
    ChonCongTacVienModal,
    ChonNguoiGTModal,
    InHoSo,
    ThueBaoCungMa,
    ChiTietTienTheoKhoanMucModal,
    frmDangKyGoiDaDVLM,
    TuvanMyTv
  },
  data: function () {
    return {
      header: {
        title: ''
      },
      list: [
        { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
        {
          name: 'Lắp đặt mới',
          link: { name: 'Ui.buttons' }
        }
      ],
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput: true
      },
      datetimeconfig: {
        altFormat: 'd/m/Y H:i:S',
        altInput: true,
        dateFormat: 'd/m/Y H:i:S',
        allowInput: true,
        enableTime: true
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: '',
      popupComponentEvents: {
        'form-close': this.popupClosed,
        acceptChangeCTV: this.popupClosed,
        acceptChangeNGT: this.popupClosed
      },
      popupComponentHeader: null,
      popupComponentAttr: {},
      //#region Khai báo
      params: {
        isIPtinh: 0,
        p_today: new Date(),
        disable: true
      },
      filterbox: {
        ctv: {
          value: '',
          ischecked: false,
          ma_ctv: '',
          ctv_id: 0
        },
        nguoigt: {
          value: '',
          ischecked: false,
          nguoigt_id: ''
        }
      },
      chkTTTCVisa: {
        checked: false,
        enabled: false
      },
      chkTucThi: {
        checked: false,
        enabled: false
      },
      chkThoiHan: {
        checked: false,
        enabled: false
      },
      btnThongTinTC: {
        enabled: true
      },
      txtTT_Cuoc: {
        enabled: false,
        value: ''
      },
      txtCuoc_TBI: {
        enabled: false,
        value: 0
      },
      txtCuocSD: {
        enabled: false,
        value: 0
      },

      thamsomacdinh: {},
      button: {
        visible_thanhtoan: 0,
        visible_henld: 0,
        visible_lienhe: 0,
        visible_email: 0
      },
      tsbtnNhapMoi: {
        enabled: false,
        visible: 0
      },
      tsbtnXoaTB: {
        enabled: false,
        visible: 0
      },
      tsbtnGhiLai: {
        enabled: true,
        visible: 0
      },
      tsbtnXoa: {
        enabled: false,
        visible: 0
      },
      tsbtnThemTB: {
        enabled: false,
        visible: 0
      },
      tsbtnHuyBo: {
        enabled: true,
        visible: 0
      },
      tag: 0,
      iKieu: 0, //0: lap hop dong, 1: tra cuu, 2: sua km
      txtMaGD: '',
      dtpNgayYeuCau: new Date(),
      txtMaHD: '',
      dtpNgayLapHD: new Date(),
      cboDichVuVT: {
        value: 0, //dichvuvt_id
        data: [],
        enabled: false
      },
      cboLoaihinhTB: {
        value: 0, //loaitb_id: 0,
        data: [],
        enabled: false
      },
      cboKieuLD: {
        value: 0,
        data: null,
        enabled: false
      },

      cboLoaiTBiADSL: {
        value: 0,
        data: []
      },
      cboTocDoADSL: {
        value: 0,
        data: null,
        enabled: false,
        iptinh: 0
      },
      cboTocDoThuc_Moi: {
        value: 0,
        data: null,
        enabled: false
      },
      cboCongNghe_Moi: {
        value: 0,
        data: [],
        visible: false, //ẩn hiện cbo công nghệ mới
        enabled: true
      },
      cboCongNghe_Cu: {
        value: 0,
        data: [],
        visible: false, //ẩn hiện cbo công nghệ cũ
        enabled: true
      },
      cboMucCuocMoi: {
        value: 0,
        data: [],
        enabled: false
      },
      cboTocDoCu: {
        value: 0,
        data: []
      },
      cboMucCuocCu: {
        value: 0,
        data: []
      },
      cboLoaiIP: {
        value: -1,
        values: [],
        valueitems: [],
        data: []
      },
      cboThangTra: {
        value: 0,
        data: [],
        enabled: true
      },
      cboDoiTuongMoi: {
        value: 1, // private int doituong_id = 1;//Default tư nhân
        data: [],
        enabled: false
      },
      cboMucCuocTB: {
        value: 0,
        data: [],
        enabled: true
      },
      cboKhuyenMai: {
        value: 0,
        data: [],
        checked: false,
        enabled: false
      },
      cboTraTruoc: {
        value: 0,
        data: [],
        checked: false,
        enabled: false
      },
      cboDoiTuongCu: {
        value: 0,
        data: []
      },
      cboMucCuocTBCu: {
        value: 0,
        data: []
      },
      cboTrangBiMoi: {
        value: 0,
        data: [],
        enabled: true
      },
      cboTrangBiCu: {
        value: 0,
        data: [],
        enabled: false
      },
      cboKieuTraMoi: {
        /// chưa có cbb
        value: 0,
        data: [],
        enabled: false
      },
      cboKieuTraCu: {
        /// chưa có cbb
        value: 0,
        data: []
      },
      txtMaTB: '',
      txtMaDuAn: '',
      txtTenTB: '',
      txtSoThangCK: 0,
      dtpNgayTra: new Date(),
      txtTienTra: 0,
      txtTienTra_enabled: true,
      txtMaKH: '',
      txtNgaySN: '',
      txtTenKH: '',
      txtDiaChiKH: '',
      txtTTCuocVisa: '',
      txtGhiChu: '',
      txtSoThang: '',
      matocdo: '',
      dtpTuNgay: new Date(),
      dtpDenNgay: new Date(),
      txtTien: 0,
      txtTongVat: 0,
      txtTongTien: 0,
      lvwThueBao: [],
      //Khai báo
      hdkh_id: 0,
      hdkh_cha_id: 0,
      hdtb_id: 0,
      khachhang_id: 0,
      thuebao_id: 0,

      diachi_tb: null,
      donvi_id: 0,
      trangbi_id: 3, //khách hàng tự trang b
      tdthuccu: 0,
      tdthucmoi: 0,
      quytrinh_id: 0,
      tocdo_id: 0,
      ltb_adsl_id: 1, //Default
      kh_cd: 0,
      ltb_adsl_cu_id: 0,
      ltb_adsl_moi_id: 0,
      tocdo_moi_id: 0,
      tocdo_cu_id: 0,
      //Hiếu bổ sung phần khai báo cho hàm laytien --14/09/2010
      //TuấnNA bổ sung thêm biến sử dụng trong hàm lấy tiền ld ---Ngày 15/09/2010
      tien_tb: 0,
      vat_tb: 0,
      tien_dv: 0,
      vat_dv: 0,
      // Thangph: Copy từ Form Đặt mới -Ngày 10/06/2015
      dtienld: 0,
      dvatld: 0,
      ds_tien_khoanmuc: [],
      ds_tien_khoanmuc_bd: [], //lưu lại thông tin tiền km ban đầu của hợp đồng

      tien_kmld: 0,
      tien_kmtb: 0,
      dtiendatcoc_tt: 0,
      dvatdatcoc_tt: 0,
      dtiendatcoc_km: 0,
      dvatdatcoc_km: 0,
      dtienthoai_dc: 0,

      txtTongTienHD: 0,

      DS_ID_DV: '', //Lưu danh sách ID dịch vụ gia tăng--Hiếu 16/09/2010
      DS_ID_TB: '', //Lưu danh sách ID mua thiết bị - Hiếu 16/09/2010
      diachi_ld: '',
      phuongld_id: 0, //lay cho hàm lay tien theo khoan muc
      phold_id: 0, //lay cho hàm lay tien theo khoan muc
      apld_id: 0,
      khuld_id: 0,
      ngaytucthi: 1, //defaul 01
      IP: '',
      nhap_tttc: 0, //Default không bắt buộc nhập thông tin tính cước
      chan_thaydoitd_conkhuyenmai: 0,
      kiemtra_khop_tien_datcoc: 0,
      kiemtra: true,
      batbuoc_ctv: 0, //Default không bắt buộc nhập CTV
      check_chuanhoa_cntt: 0,
      batbuoc_tratruoc: 0,
      phainhap_thoihan: false,
      batbuoc_gioithieu_tocdo: 0,
      BATBUOC_SOTHANG_CK: 0,
      md_tucthi_thaydoi_td: 0,
      kiemtra_no_tratruoc: 0,
      loi_sinh_magd: false,
      ts_sinhma_gd_theo_donvi: false, //Dungf cho Hni sinh ma GD theo don vi quan ly cua ng dung
      khong_kiemtra_mytv_chinhphu: -1,
      ht_trangbi_thaydoi_ts_adsl: -1,
      trangbi_ht: 0,
      plkhachhang_id: 0,
      VPHIEUCSKH_ID: 0,
      VPHIEUCSKHDV_ID: 0,
      CT_TIENHD_ID: 0,
      dsCTTHD: [],
      dsHDTBDV: [],
      dsHDKH: [],
      dsHDTB: [],
      dsTDTD_ADSL: [],
      dsTDTD_ADSL_2: [],
      dsTIEN_HDTB: [],
      dsKM_HDTB: [],
      dsDC_HDTB: [],
      dsHDTB_CNTT: [],
      dsHDTB_LoaiIp: [],
      dsDK_DVGT: [],
      bras_id: 0,
      ItemList: [],
      dsDBTB: [],
      dsKhuyenMai: [],
      dsTraTruoc: [],
      tocdothuc_cu_id: -1,
      kt_hagoi: false,
      quyen_hagoi: false,
      kt_load: true,
      khoa_trangbi_lhtb: 0,
      dtpNgayTra_enabled: true,
      sothang_sd: 0,
      ds_hdtb_adsl: [],
      phdkh_cha_id: 0,
      pctv_id: 0,
      IS_B2A: false,
      kieu: -1,
      link_url: false,
      popupInHopDong_Param: {
        hdkh_id: 0,
        ma_gd: '',
        ngay_yc: '',
        ngay_lhd: ''
      },
      vmagd_temp: '',
      dslam_id: null,
      port_id: null,
      vci_vpi_id: null,
      madoicap: null,
      ma_lt: null,
      //#endregion
      linhvuc_id: 0,
      m_dsThueBao_DichVu: [],
      lbDichVuGT: {
        list: [],
        value: null,
        isEnabled: true,
        checked: []
      },
      dsDiaChi_DBTB:[],
      loainha_id:0,
      data_doituong: [],
      kinhdo_ld: 0,
      vido_ld: 0,
    }
  },
  validations: {},
  computed: {
    checkKhuyenMai: {
      get() {
        return this.cboKhuyenMai.checked
      },
      set(value) {
        this.cboKhuyenMai.checked = value
        if (this.cboKhuyenMai.checked == true) {
          this.LaySothang_SD()
          // this.dsKM_HDTB = []
          this.HienThiTien_TraTruoc()
          this.HT_Tien_ConLai_DatCoc_Cu()
        } else {
          this.dsKM_HDTB = []
          this.dtiendatcoc_km = 0
          this.dvatdatcoc_km = 0
        }
      }
    },
    checkTraTruoc: {
      get() {
        return this.cboTraTruoc.checked
      },
      set(value) {
        this.cboTraTruoc.checked = value
        if (this.cboTraTruoc.checked == true) {
          this.LaySothang_SD()
          // this.dsDC_HDTB = []
          this.HienThiTien_TraTruoc()
          this.HT_Tien_ConLai_DatCoc_Cu()
        } else {
          this.dsDC_HDTB = []
          this.dtiendatcoc_tt = 0
          this.dvatdatcoc_tt = 0
        }
      }
    },
    checkThoiHan: {
      get() {
        return this.chkThoiHan.checked
      },
      set(value) {
        this.chkThoiHan.checked = value
        if (this.chkThoiHan.checked == false) {
        } else {
        }
      }
    }
  },
  created() {
    if (this.$route.params && this.$route.params.tag !== null && this.$route.params.tag !== undefined) {
      this.tag = this.$route.params.tag
    }
    if (this.Tag != null && this.Tag != '') {
      this.iKieu = this.tag
    } else this.iKieu = 0
    if (this.iKieu == 1) {
      this.header.title = 'TRA CỨU THAY ĐỔI TỐC ĐỘ, MỨC CƯỚC THUÊ BAO INTERNET'
    } else if (this.iKieu == 2) {
      this.header.title = 'SỬA KM THAY ĐỔI TỐC ĐỘ, MỨC CƯỚC THUÊ BAO INTERNET'
    } else {
      this.header.title = 'THAY ĐỔI TỐC ĐỘ, MỨC CƯỚC THUÊ BAO INTERNET'
    }
    // Lay gia tri ma giao dich
    if (this.$route.params.ma_Tb != null && this.$route.params.ma_Tb !== '' && this.$route.params.ma_Tb.length > 0) {
      this.txtMaTB = this.$route.params.ma_Tb
    } else if (this.$route.query.ma_Tb != null && this.$route.query.ma_Tb !== '' && this.$route.query.ma_Tb.length > 0) {
      this.txtMaTB = this.$route.query.ma_Tb
    }

    // BSS-116805 Lấy giá trị Tốc độ mới và mức cước 
    if (this.$route.params.tocdo_id != null && this.$route.params.tocdo_id !== '' && this.$route.params.tocdo_id.length > 0) {
      this.cboTocDoADSL.value = this.$route.params.tocdo_id
    } else if (this.$route.query.tocdo_id != null && this.$route.query.tocdo_id !== '' && this.$route.query.tocdo_id.length > 0) {
      this.cboTocDoADSL.value = this.$route.query.tocdo_id
    }

    if (this.$route.params.mucuoctb_id != null && this.$route.params.mucuoctb_id !== '' && this.$route.params.mucuoctb_id.length > 0) {
      this.cboMucCuocTB.value = this.$route.params.mucuoctb_id
    } else if (this.$route.query.tocdo_id != null && this.$route.query.tocmucuoctb_iddo_id !== '' && this.$route.query.mucuoctb_id.length > 0) {
      this.cboMucCuocTB.value = this.$route.query.mucuoctb_id
    }

    // BSS-116805 Lấy giá trị Tốc độ mới và mức cước 
    this.visibleToolStrip(this.iKieu == 0)
    //this.getLoaiIP();
    this.HT_DichVuVT_Combobox()
    this.HT_MucCuoc_Adsl_Combobox()
    this.HT_LOAI_TBI_ADSL_Combobox()
    this.HT_ThangTra_Combobox()
    this.HT_DoiTuong_Combobox()
    this.lay_thamso_md()
    this.HT_TrangBi_TSL_Combobox_DK()
  },
  mounted: async function () {
    // Lay gia tri
    if (this.$route.params.hdkh_cha_id != null && this.$route.params.hdkh_cha_id !== '') {
      this.phdkh_cha_id = this.$route.params.hdkh_cha_id
    } else if (this.$route.query.hdkh_cha_id != null && this.$route.query.hdkh_cha_id !== '') {
      this.phdkh_cha_id = this.$route.query.hdkh_cha_id
    }
    if (this.$route.params.ctv_id != null && this.$route.params.ctv_id !== '') {
      this.pctv_id = this.$route.params.ctv_id
    } else if (this.$route.query.ctv_id != null && this.$route.query.ctv_id !== '') {
      this.pctv_id = this.$route.query.ctv_id
    }
    if (this.$route.params.phieucskh_id != null && this.$route.params.phieucskh_id !== '') {
      this.VPHIEUCSKH_ID = this.$route.params.phieucskh_id
    } else if (this.$route.query.phieucskh_id != null && this.$route.query.phieucskh_id !== '') {
      this.VPHIEUCSKH_ID = this.$route.query.phieucskh_id
    }
    if (this.$route.params.phieucskhdv_id != null && this.$route.params.phieucskhdv_id !== '') {
      this.VPHIEUCSKHDV_ID = this.$route.params.phieucskhdv_id
    } else if (this.$route.query.phieucskhdv_id != null && this.$route.query.phieucskhdv_id !== '') {
      this.VPHIEUCSKHDV_ID = this.$route.query.phieucskhdv_id
    }

    if (this.$route.params.kieu != null && this.$route.params.kieu !== '') {
      this.kieu = this.$route.params.kieu
    } else if (this.$route.query.kieu != null && this.$route.query.kieu !== '') {
      this.kieu = this.$route.query.kieu
    }
    if (this.kieu == 5) {
      this.LoadTuHopDongB2A()
    } else {
      if (this.kieu > 0) this.LoadTuHopDongTuVan()
    }
    if (this.$route.query.loaitb_id && this.$route.query.loaitb_id !== '') {
            this.cboLoaihinhTB.value = this.$route.query.loaitb_id.replaceAll(' ', '+')
    }
    if (this.$route.query.dichvuvt_id && this.$route.query.dichvuvt_id !== '') {
            this.cboDichVuVT.value = this.$route.query.dichvuvt_id.replaceAll(' ', '+')
    }
    if (this.$route.query.matb_kt && this.$route.query.matb_kt !== '') {
            this.matb_kt = this.$route.query.matb_kt.replaceAll(' ', '+')
    }
    if (this.$route.query.ma_tb && this.$route.query.ma_tb !== '') {
            this.link_url = true
            this.txtMaTB = this.$route.query.ma_tb.replaceAll(' ', '+')
            this.txtMaTB_KeyPress()
    }
  },
  methods: {
    LowerCasePropertyList(obj) {
      return obj.map(function (item) {
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
    UpperCasePropertyList(obj) {
      return obj.map(function (item) {
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
    visibleToolStrip(visible) {
      this.tsbtnNhapMoi.visible = visible
      this.tsbtnGhiLai.visible = visible
      this.tsbtnXoa.visible = visible
      this.tsbtnThemTB.visible = visible
      this.tsbtnXoaTB.visible = visible
      this.tsbtnHuyBo.visible = visible
      this.button.visible_thanhtoan = visible
      this.button.visible_henld = visible
      this.button.visible_lienhe = visible
      this.button.visible_email = visible
      //Kiểm tra nếu không có quyền thay đổi tốc độ khi sửa km thì không cho sửa
      if (!this.KIEMTRA_QUYEN_ND(925)) {
        this.cboDoiTuongMoi.enabled = visible
        this.cboTocDoADSL.enabled = visible
        this.cboDichVuVT.enabled = visible
        this.cboKieuLD.enabled = visible
        this.cboLoaihinhTB.enabled = visible
      }
    },
    KIEMTRA_QUYEN_ND(quyen_id) {
      API.kiemtra_quyen_nd(this.axios, { quyen_id: quyen_id })
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            return true
          } else {
            return false
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {})
    },
    async lay_thamso_md() {
      let dt = []
      await API.ds_thamso_md(this.axios)
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            //console.log("ds_thamso_md: " + JSON.stringify(response.data.data));
            dt = response.data.data
            for (let i = 0; i < dt.length; i++) {
              if (dt[i].ma_ts == 'NGAY_TUCTHI') this.ngaytucthi = dt[i].ten_ts
              if (dt[i].ma_ts == 'NHAP_THONGTIN_TINHCUOC') this.nhap_tttc = dt[i].ten_ts
              if (dt[i].ma_ts == 'KIEMTRA_KHOP_TIEN_DATCOC') this.kiemtra_khop_tien_datcoc = dt[i].ten_ts
              if (dt[i].ma_ts == 'KHONG_HIENTHI_TTTC_VISA') this.chkTTTCVisa.enabled = dt[i].ten_ts == 1 ? false : true
              if (dt[i].ma_ts == 'CHAN_DOI_TOCDO_CON_KM') this.chan_thaydoitd_conkhuyenmai = dt[i].ten_ts
              if (dt[i].ma_ts == 'CHECK_CHUANHOA_CNTT') this.check_chuanhoa_cntt = dt[i].ten_ts
              if (dt[i].ma_ts == 'BATBUOC_TRATRUOC') this.batbuoc_tratruoc = dt[i].ten_ts
              if (dt[i].ma_ts == 'PHAINHAP_THOIHAN') this.phainhap_thoihan = dt[i].ten_ts == 1 ? true : false
              if (dt[i].ma_ts == 'BATBUOC_CTV_TDTD') this.batbuoc_ctv = dt[i].ten_ts
              if (dt[i].ma_ts == 'BATBUOC_SOTHANG_CK') this.batbuoc_sothang_ck = dt[i].ten_ts
              if (dt[i].ma_ts == 'MACDINH_TUCTHI_THAYDOI_TD') this.md_tucthi_thaydoi_td = dt[i].ten_ts
              if (dt[i].ma_ts == 'KIEMTRA_NO_TRATRUOC') this.kiemtra_no_tratruoc = dt[i].ten_ts
              if (dt[i].ma_ts == 'KIEMTRA_HAGOI') this.quyen_hagoi = dt[i].ten_ts
              if (dt[i].ma_ts == 'SINH_MA_GD_THEO_DV') this.ts_sinhma_gd_theo_donvi = dt[i].ten_ts == 0 ? false : true
              if (dt[i].ma_ts == 'KHONG_KIEMTRA_MYTV_CHINHPHU') this.khong_kiemtra_mytv_chinhphu = dt[i].ten_ts
              if (dt[i].ma_ts == 'HT_TRANGBI_THAYDOI_TD_ADSL') this.ht_trangbi_thaydoi_ts_adsl = dt[i].ten_ts
              if (dt[i].ma_ts == 'TRANGBI_HT') this.trangbi_ht = dt[i].ten_ts
            }

            if (this.ht_trangbi_thaydoi_ts_adsl == 0) this.cboTrangBiMoi.enabled = false //Khóa tất cả
            else {
              this.khoa_trangbi_lhtb = this.ht_trangbi_thaydoi_ts_adsl //Khóa theo loại hình TB
              this.cboTrangBiMoi.enabled = true
            }
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {})
      this.kt_load = false
      console.log('this.batbuoc_ctv: ' + this.batbuoc_ctv + ' GT: ' + this.batbuoc_gioithieu_tocdo)
    },
    checkTucThi: function () {
      if (this.chkTucThi.checked === false) {
        //checked
        this.chkTucThi.checked = true
      } else {
        //uncheck
        this.chkTucThi.checked = false
      }
    },
    async HT_DS_KhuyenMai_Combobox() {
      /* if(this.cboKieuLD.data.length<=0){
      return false;
    }
    if(this.cboDoiTuongMoi.data.length<=0){
      return false;
    } */
      var pkhuyenmai = {
        vapld_id: this.apld_id ? this.apld_id : 0,
        vdoituong_id: this.cboDoiTuongMoi.value,
        vhdtb_id: 0,
        vkhoanmuctt_id: KHOANMUC_TT.KMTT_DATMOI,
        vkhuld_id: this.khuld_id ? this.khuld_id : 0,
        vkieuld_id: this.cboKieuLD.value,
        vloaikm: 0, //khuyến mại
        vloaitb_id: this.cboLoaihinhTB.value,
        vmuccuoc_id: this.cboMucCuocMoi.value,
        vphold_id: this.phold_id ? this.phold_id : 0,
        vphuongld_id: this.phuongld_id ? this.phuongld_id : 0,
        vtocdo_id: this.cboTocDoADSL.value,
        vtrangbi_id: 0,
        loainha_id: this.loainha_id
      }
      var ptratruoc = {
        vapld_id: this.apld_id ? this.apld_id : 0,
        vdoituong_id: this.cboDoiTuongMoi.value,
        vhdtb_id: 0,
        vkhoanmuctt_id: KHOANMUC_TT.KMTT_DATMOI,
        vkhuld_id: this.khuld_id ? this.khuld_id : 0,
        vkieuld_id: this.cboKieuLD.value,
        vloaikm: 1, //tra truoc
        vloaitb_id: this.cboLoaihinhTB.value,
        vmuccuoc_id: this.cboMucCuocMoi.value,
        vphold_id: this.phold_id ? this.phold_id : 0,
        vphuongld_id: this.phuongld_id ? this.phuongld_id : 0,
        vtocdo_id: this.cboTocDoADSL.value,
        vtrangbi_id: 0,
        loainha_id: this.loainha_id
      }
      await API.post_lay_khuyenmai_hdtb(this.axios, pkhuyenmai).then((response) => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.dsKhuyenMai = response.data.data
          this.cboKhuyenMai.data = this.dsKhuyenMai.map((x) => ({
            id: x.khuyenmai_id,
            text: x.ten_km
          }))
          this.cboKhuyenMai.value = this.dsKhuyenMai[0]['khuyenmai_id']
          this.cboKhuyenMai.enabled = true
        } else {
          this.cboKhuyenMai.enabled = false
        }
      })
      //this.cboTraTruoc.checked = false;
      await API.post_lay_khuyenmai_hdtb(this.axios, ptratruoc).then((response) => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.dsTraTruoc = response.data.data
          this.cboTraTruoc.data = response.data.data.map((x) => ({
            id: x.khuyenmai_id,
            text: x.ten_km
          }))
          //  console.log("tratruoc: " + this.dsTraTruoc[0]["khuyenmai_id"]);
          this.cboTraTruoc.value = this.dsTraTruoc[0]['khuyenmai_id']
          this.cboTraTruoc.enabled = true
        } else {
          this.cboTraTruoc.enabled = false
        }
      })
      //this.cboTraTruoc.checked = false;
    },
    SetButton(kieu) {
      this.$refs.inputMaHD.focus()
      this.tsbtnNhapMoi.enabled = false
      this.tsbtnGhiLai.enabled = false
      if (this.lvwThueBao.length > 0) {
        this.tsbtnXoa.enabled = true
      } else {
        this.tsbtnXoa.enabled = false
      }
      this.tsbtnThemTB.enabled = false
      // this.tsbtnXoaTB.enabled = false;
      this.tsbtnHuyBo.enabled = false
      switch (kieu) {
        case -1: //Bat dau
          this.tsbtnGhiLai.enabled = true
          this.tsbtnHuyBo.enabled = true
          this.filterbox.ctv.ischecked = false
          this.filterbox.nguoigt.ischecked = false
          this.filterbox.ctv.value = ''
          this.filterbox.nguoigt.value = ''
          break
        case 0: //Bat dau
          this.tsbtnNhapMoi.enabled = true
          this.Clear()
          break
        case 1: //thêm mới
          this.tsbtnGhiLai.enabled = true
          this.tsbtnHuyBo.enabled = true
          this.filterbox.ctv.ischecked = false
          this.filterbox.nguoigt.ischecked = false
          this.filterbox.ctv.value = ''
          this.filterbox.nguoigt.value = ''
          this.tsbtnNhapMoi.enabled = false
          this.Clear()
          break
        case 2: //hủy
          this.tsbtnNhapMoi.enabled = true
          this.tsbtnXoa.enabled = true
          this.Clear()
          break
        case 3: //edit
          this.tsbtnNhapMoi.enabled = true
          this.tsbtnXoa.enabled = true
          this.tsbtnGhiLai.enabled = true
          this.tsbtnHuyBo.enabled = true
          this.tsbtnThemTB.enabled = true
          break
      }
    },
    Clear() {
      this.txtSoThang = '0'
      this.IP = ''
      this.trangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI //default khách hàng tự trang bị
      this.phuongld_id = 0
      this.phold_id = 0
      this.apld_id = 0
      this.khuld_id = 0
      this.$refs.inputMaTB.focus()

      this.hdkh_id = 0
      this.txtMaGD = ''
      this.txtMaHD = ''
      this.txtMaKH = ''
      this.khachhang_id = 0
      this.txtTenKH = ''
      this.txtDiaChiKH = ''

      this.txtCuocSD.value = '0'
      this.txtCuoc_TBI.value = '0'
      this.dtpNgayLapHD = new Date()
      if (this.cboTocDoADSL.data == null) {
        this.cboTocDoADSL.value = 0
      }
      if (this.cboTocDoThuc_Moi.data == null) {
        this.cboTocDoThuc_Moi.value = -1
      }
      this.hdtb_id = 0
      this.thuebao_id = 0
      this.txtTenTB = ''
      this.diachi_tb = ''
      this.diachi_ld = ''
      //Hiếu bổ sung 24/08/2010
      this.dtpNgayYeuCau = new Date()
      this.txtNgaySN = ''
      this.chkTucThi.checked = false
      this.chkTucThi.enabled = true
      this.txtMaTB = ''
      this.txtMaDuAn = ''
      //Tuấn thêm code clear các đối tượng trên form --- Ngày 03/09/2010
      this.lvwThueBao = []
      if (this.cboKieuLD.data == null) {
        this.cboKieuLD.value = -1
      }
      this.txtGhiChu = ''
      this.cboDoiTuongMoi.value = 1 //Default tư nhân
      this.txtTTCuocVisa = ''
      this.cboLoaiIP.data = []
      this.filterbox.ctv.value = null
      this.filterbox.nguoigt.value = null
      this.LayTienTheoKhoanMuc()
      this.cboLoaiIP.valueitems = []
      this.cboDoiTuongCu.value = 0
      this.cboDoiTuongMoi.value = 0
      this.cboMucCuocMoi.value = 0
      this.cboMucCuocCu.value = 0
      this.cboTocDoADSL.value = 0
      this.cboTocDoThuc_Moi.value = 0
      this.cboMucCuocTB.value = 0
      this.cboMucCuocTBCu.value = 0
      this.cboLoaiTBiADSL.value = 0
      this.txtTT_Cuoc.value = ''
    },
    getLoaiIP() {
      /* this.cboLoaiIP.data = [];
      this.cboLoaiIP.value= -1;
      this.cboLoaiIP.values= [];
      this.cboLoaiIP.valueitems= []; */
      API.sp_tt_loai_ip(this.axios, {
        param: 1,
        type: 1
      })
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            this.cboLoaiIP.data = response.data.data
            //this.cboLoaiIP.value = response.data.data[0].loaiip_id;
            //this.cboLoaiIP.values = [this.cboLoaiIP.data[0].loaiip_id];
            /*this.cboLoaiIP.valueitems =
              [];  [this.cboLoaiIP.data[0]].map((lo) => ({
              id: lo.loaiip_id,
              text: lo.loai_ip,
            })); */
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {})
    },
    async LayTienTheoKhoanMuc() {
      if (this.cboDichVuVT.value != DichVuVienThong.ADSL) {
        this.kh_cd = 0
        this.ltb_adsl_id = 1
      }
      var ngayyeucau = ''
      if (moment(this.dtpNgayYeuCau).isValid()) {
        ngayyeucau = moment(this.dtpNgayYeuCau).format('DD/MM/YYYY')
      } else {
        ngayyeucau = this.dtpNgayYeuCau
      }
      var pdata = {
        vapld_id: this.apld_id,
        vdichvuvt_id: this.cboDichVuVT.value,
        vdoituong_id: this.cboDoiTuongMoi.value,
        vkh_cd: this.kh_cd,
        vkhuld_id: this.khuld_id,
        vkieuld_id: this.cboKieuLD.value,
        vloaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL,
        vloaitb_id: this.cboLoaihinhTB.value,
        vltb_adsl_cu_id: this.ltb_adsl_cu_id,
        vltb_adsl_id: this.ltb_adsl_id,
        vltb_adsl_moi_id: this.ltb_adsl_moi_id,
        vmuccuoc_id: 0, /// code cũ ko có
        vngay_yc: ngayyeucau,
        vphanvung_id: this.$root.token.getPhanVungID(),
        vphold_id: this.phold_id,
        vphuongld_id: this.phuongld_id,
        vtocdo_cu_id: this.cboTocDoCu.value,
        vtocdo_id: this.cboTocDoCu.value,
        vtocdo_moi_id: this.cboTocDoADSL.value,
        vtrangbi_id: this.trangbi_id
      }
      await API.post_lay_tien_khoanmuc_ld_v2(this.axios, pdata).then((response) => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          let ds = response.data.data
          if (ds.length > 0) {
            this.HienThiTienLapDat(ds)
            this.LayTongTien_HDTB()
            //console.log("log_lay_tien_khoanmuc_ld_v2 " + JSON.stringify(ds));
          }
        }
      })
    },
    HienThiTienLapDat(ds) {
      var kt_lm = true
      if (ds[0]['tien'] != -1) {
        for (var i = 0; i < ds.length; i++) {
          if (ds[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DOITOCDO) {
            this.dtienld = ds[i]['tien']
            this.dvatld = ds[i]['vat']
            kt_lm = false
          }
        }
        if (kt_lm == true) {
          this.dtienld = 0
          this.dvatld = 0
        }
      } else {
        this.dtienld = 0
        this.dvatld = 0
      }
      for (var j = 0; j < this.ds_tien_khoanmuc.length; j++) {
        if (this.ds_tien_khoanmuc[j]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DOITOCDO) {
          this.ds_tien_khoanmuc[j]['tien'] = this.dtienld
          this.ds_tien_khoanmuc[j]['vat'] = this.dvatld
        }
      }
    },
    LayTongTien_HDTB() {
      try {
        var dtongtien = 0
        var dtongvat = 0
        for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_KM_LAPDAT || this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_TIENTHOAI_DC_DOI_TD || this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_KM_DATCOC) {
            if (this.ds_tien_khoanmuc[i]['tien'] < 0) {
              dtongtien += Number(this.ds_tien_khoanmuc[i]['tien']) ? Number(this.ds_tien_khoanmuc[i]['tien']) : 0
              dtongvat += Number(this.ds_tien_khoanmuc[i]['vat']) ? Number(this.ds_tien_khoanmuc[i]['vat']) : 0
            } else {
              dtongtien += Number(this.ds_tien_khoanmuc[i]['tien']) * -1
              dtongvat += Number(this.ds_tien_khoanmuc[i]['vat']) * -1
            }
          } else if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] != KHOANMUC_TT.KMTT_TRAGOP_THIETBI && this.ds_tien_khoanmuc[i]['khoanmuctt_id'] != KHOANMUC_TT.KMTT_KM_THIETBI && this.ds_tien_khoanmuc[i]['khoanmuctt_id'] != KHOANMUC_TT.KMTT_MUATHIETBI) {
            dtongtien += Number(this.ds_tien_khoanmuc[i]['tien']) ? Number(this.ds_tien_khoanmuc[i]['tien']) : 0
            dtongvat += Number(this.ds_tien_khoanmuc[i]['vat']) ? Number(this.ds_tien_khoanmuc[i]['vat']) : 0
          }
        }
        //duongpd them tinh tien cho dich vu SLL
        this.txtTien = Number(dtongtien) ? Number(dtongtien) : 0
        this.txtTongVat = Number(dtongvat) ? Number(dtongvat) : 0
        this.txtTongTien = parseInt(this.txtTien + this.txtTongVat)
        /* console.log("tien: " + this.txtTien+" - "+dtongtien);
        console.log("vat: " + this.txtTongVat+" - "+dtongvat);
        console.log("tongtien: " + this.txtTongTien); */
      } catch (ex) {
        this.$toast.error('' + ex)
        if (this.debug) console.error(ex)
        throw ex
      }
    },
    HT_DichVuVT_Combobox() {
      this.cboDichVuVT.data = []
      this.cboDichVuVT.value = 0
      let apiParams = {
        dichvu_vt_id: '4,12,13,14,15,16'
      }
      API.sp_ht_dichvuvt_combobox(this.axios, apiParams)
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            let items = []
            response.data.data.forEach(function (item) {
              items.push({ id: item.dichvuvt_id, text: item.ten_dvvt })
            })
            this.cboDichVuVT.data = items
            this.cboDichVuVT.value = items[0].id
            this.HT_LoaiHinh_TB_Combobox(items[0].id, 0)
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {})
    },
    async HT_LoaiHinh_TB_Combobox(dvvtid, loaihinhid) {
      this.cboLoaihinhTB.data = []
      this.cboKieuLD.data = []
      this.cboTocDoADSL.data = []
      this.cboTocDoThuc_Moi.data = []
      await API.lay_ds_loaihinh_thuebao_dichvu(this.axios, dvvtid)
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            let items = []
            response.data.data.forEach(function (item) {
              items.push({ id: item.ID, text: item.NAME })
            })
            this.cboLoaihinhTB.data = items

            //nếu chọn cbb dịch vụ, loại hình = 0 load mặc định theo dịch vụ, enter_keypress load loại hình theo mã thuê bao
            if (loaihinhid == 0) {
              this.cboLoaihinhTB.value = items[0].id
              this.HT_Tocdo_Adsl_Combobox_ByLoaiTB(items[0].id)
              this.HT_Kieu_LD_BANCHEO_Combobox(items[0].id)
            } else {
              this.cboLoaihinhTB.value = loaihinhid
              this.HT_Tocdo_Adsl_Combobox_ByLoaiTB(loaihinhid)
              this.HT_Kieu_LD_BANCHEO_Combobox(loaihinhid)

              if (this.khoa_trangbi_lhtb != 0) {
                if (this.cboLoaihinhTB.value == this.khoa_trangbi_lhtb) {
                  // LoaiHinhTB.INTERNET_MYTV
                  this.cboTrangBiMoi.enabled = false
                } else {
                  this.cboTrangBiMoi.enabled = true
                }
              }
            }
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {})
    },
    HT_MucCuoc_Adsl_Combobox() {
      this.cboMucCuocMoi.data = []
      this.cboMucCuocMoi.value = null
      let apiParams = {
        param: 1,
        type: 1
      }
      API.sp_tt_muccuoc(this.axios, apiParams)
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            let items = []
            response.data.data.forEach(function (item) {
              items.push({ id: item.muccuoc_id, text: item.tenmuc })
            })
            this.cboMucCuocMoi.data = items
            this.cboMucCuocMoi.value = items[0].id
            // lấy datasource
            this.cboMucCuocCu.data = items
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {})
    },
    HT_LOAI_TBI_ADSL_Combobox() {
      this.cboLoaiTBiADSL.data = []
      this.cboLoaiTBiADSL.value = null
      API.sp_lay_ds_loai_thietbi_adsl(this.axios)
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            let items = []
            response.data.data.forEach(function (item) {
              items.push({ id: item.thietbidc_id, text: item.ten_tbi })
            })
            this.cboLoaiTBiADSL.data = items
            this.cboLoaiTBiADSL.value = items[0].id
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {})
    },
    HT_ThangTra_Combobox() {
      this.cboThangTra.data = []
      this.cboThangTra.value = null
      let apiParams = {
        param: 1,
        type: 1
      }
      API.sp_tt_thangtra(this.axios, apiParams)
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            let items = []
            response.data.data.forEach(function (item) {
              items.push({ id: item.thang_id, text: item.tenthang })
            })
            this.cboThangTra.data = items
            this.cboThangTra.value = items[0].id
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {})
    },
    HT_DoiTuong_Combobox() {
      this.cboDoiTuongMoi.data = []
      this.cboDoiTuongMoi.value = null
      API.sp_lay_ds_doituong_theo_dieukien(this.axios)
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            let items = []
            response.data.data.forEach(function (item) {
              items.push({ id: item.doituong_id, text: item.ten_dt })
            })
            this.cboDoiTuongMoi.data = items
            this.cboDoiTuongMoi.value = items[0].id
            //lấy datasource
            this.cboDoiTuongCu.data = items
            this.data_doituong = items
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {})
    },
    HT_DoiTuong_Combobox_TheoTocDo(tocdoid) {
      //this.cboDoiTuongMoi.data = []
      API.sp_lay_ds_doituong_theo_tocdo(this.axios,{p_tocdo_id: tocdoid})
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            let items = []
            response.data.data.forEach(function (item) {
              items.push({ id: item.doituong_id, text: item.ten_dt })
            })
            this.cboDoiTuongMoi.data = items //https://cntt.vnpt.vn/browse/BSS-117969
            this.cboDoiTuongMoi.value = items[0].id
          }else{
            this.cboDoiTuongMoi.data = this.data_doituong 
            this.cboDoiTuongMoi.value = this.data_doituong[0].id
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {})
    },
    HT_Combo_CongNghe() {
      this.cboCongNghe_Moi.data = []
      // this.cboCongNghe_Moi.value = 0;
      this.cboCongNghe_Cu.data = []
      //this.cboCongNghe_Cu.value = 0;
      API.sp_lay_danhsach_congnghe(this.axios, {})
        .then((response) => {
          if (response.data.data !== undefined) {
            let items = []
            response.data.data.forEach(function (item) {
              items.push({ id: item.congnghe_id, text: item.congnghe })
            })
            this.cboCongNghe_Moi.data = items.filter((x) => x.id == 2 || x.id == 3 || x.id == 11)
            //lấy datasource
            this.cboCongNghe_Cu.data = this.cboCongNghe_Moi.data
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {})
    },

    tsbtnNhapMoi_Click() {
      this.SetButton(1)
    },
    async tsbtnGhiLai_Click() {
      try {
        this.loading(true)
        // tu van mytv
        await this.TuVanMyTV_Mesh_Cam(this.khachhang_id)
      } catch (e) {} finally { this.loading(false) }
    },
    tsbtnHuyBo_Click() {
      this.SetButton(0)
    },
    clickXoaHD() {
      this.$alert('Bạn thật sự muốn xóa hợp đồng không ?', 'Thông báo', {
        dangerouslyUseHTMLString: true,
        showCancelButton: true,
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy bỏ',
        showCloseButton: true,
        type: 'warning'
      }).then(async (v) => {
        if (!v) {
          return
        }
        try {
          this.loading(true)
          await this.XoaHDKH()
        } catch (e) {} finally { this.loading(false) }
      })
    },
    btnInHD_Click() {
      if (this.hdkh_id != 0) {
        var ngayyeucau = ''
        if (moment(this.dtpNgayYeuCau).isValid()) {
          ngayyeucau = moment(this.dtpNgayYeuCau).format('DD/MM/YYYY')
        } else {
          ngayyeucau = this.dtpNgayYeuCau
        }

        var ngaylaphd = ''
        if (moment(this.dtpNgayLapHD).isValid()) {
          ngaylaphd = moment(this.dtpNgayLapHD).format('DD/MM/YYYY')
        } else {
          ngaylaphd = this.dtpNgayLapHD
        }
        this.popupInHopDong_Param.hdkh_id = this.hdkh_id
        this.popupInHopDong_Param.ma_gd = this.txtMaGD
        this.popupInHopDong_Param.ngay_yc = ngayyeucau
        this.popupInHopDong_Param.ngay_lhd = ngaylaphd
        this.$refs.popupInHoSo.showModal()
      } else {
        this.$toast.error('Chưa có thông tin hợp đồng!')
      }
    },
    async tsbtnThemTB_Click() {
      try {
        this.loading(true)
        await this.ThemHDThueBao()
      } catch (e) {} finally { this.loading(false) }
    },
    clickXoaTB() {
      this.$alert('Bạn thật sự muốn xóa thuê bao không ?', 'Thông báo', {
        dangerouslyUseHTMLString: true,
        showCancelButton: true,
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy bỏ',
        showCloseButton: true,
        type: 'warning'
      }).then(async (v) => {
        if (!v) {
          return
        }
        try {
          this.loading(true)
          await this.XoaHDTB()
        } catch (e) {} finally { this.loading(false) }
      })
    },
    async CapNhat() {

      if (!(await this.KiemTraDL_KhachHang())) return false
      if (!(await this.KiemTraDL_ThueBao())) return false
      this.quytrinh_id = 0
      let mcuoc_id = 0
      if (this.cboLoaihinhTB.value != LoaiHinhTB.INTERNET_MYTV && this.cboLoaihinhTB.value != LoaiHinhTB.INTERNET_SGTV) {
        mcuoc_id = this.cboMucCuocMoi.value
      }
      //Bo sung XGSPON
      if(this.cboCongNghe_Moi.value == 11) { //XGSPON   
        var ktra = await API.fn_kiemtra_nangluc_mangluoi_xgspon_v2(this.axios,{ "vlat": this.vido_ld, "vlng": this.kinhdo_ld }).then((response) => {
          if (response && response.data)
            return response.data.data=="OK"?"OK":response.data.message;
          else return "Lỗi không xác định khi kiểm tra năng lực mạng lưới đối với việc cung cấp XGSPON!";            
        });
        if(ktra!="OK") {
          this.$toast.error("Địa chỉ lắp đặt của thuê bao chưa có thiết bị XGSPON");
          // return false;
        }
      }        
      let dshg = []
     // console.log('mcuoc_id: ' + mcuoc_id)
      await API.lay_huonggiao_tiepnhan(this.axios, {
        kieuLdId: this.cboKieuLD.value,
        loaiHdId: LoaiHopDong.THAY_DOI_TOCDO_ADSL,
        loaiTbId: this.cboLoaihinhTB.value,
        mucCuocId: mcuoc_id,
        tocDoId: this.cboTocDoADSL.value
      }).then((response) => {
        if (response.data.error_code === 'BSS-00000000') {
          dshg = response.data.data
        }
      })
      if (dshg == null) {
        this.$toast.error('Có lỗi trong quá trình lấy hướng giao, bạn hãy liên hệ Admin để xử lý!')
        return false
      }
      if (dshg.length <= 0) {
        this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
        return false
      }
      this.quytrinh_id = dshg.quytrinh_id
      if ((await this.tsbtnNhapMoi.enabled) == false) {
        await this.TaoDuLieu(true)
        if (this.loi_sinh_magd) {
          this.loi_sinh_magd = false
          return false
        }
        this.loading(true)
        let apiParams = {
          ACTION_TYPE: 'A',
          BATBUOC_SOTHANG_CK: this.txtSoThangCK,
          CONGNGHE_CU_ID: this.cboCongNghe_Cu.value,
          CONGNGHE_MOI_ID: this.cboCongNghe_Moi.value,
          CT_TIENHD: this.dsCTTHD,
          CUOC_SD: this.txtCuocSD.value,
          CUOC_TBI: this.txtCuoc_TBI.value,
          DICHVUVT_ID: this.cboDichVuVT.value,
          HD_KHACHHANG: this.dsHDKH,
          HD_THUEBAO: this.dsHDTB,
          HDTB_ADSL: this.dsTDTD_ADSL,
          HDTB_CNTT: this.dsHDTB_CNTT,
          HDTB_ID: this.hdtb_id,
          HDTB_TC: this.dsTIEN_HDTB,
          KHACHHANG_ID: this.khachhang_id,
          KHUYENMAI_HDTB: this.dsKM_HDTB,
          HDTB_DATCOC: this.dsDC_HDTB,
          KIEULD_ID: this.cboKieuLD.value,
          KIEUTRA_CU_ID: this.cboKieuTraCu.value,
          KIEUTRA_MOI_ID: this.cboKieuTraMoi.value,
          KIEUTRAMOI: '0', /// this.cboKieuTraMoi.data.filter(x => x.id ==this.cboKieuTraMoi.value )[0].text,
          LOAITB_ID: this.cboLoaihinhTB.value,
          MA_HD: this.txtMaHD,
          MA_KH: this.txtMaKH,
          MA_TB: this.txtMaTB,
          MUCCUOC_ID: this.cboMucCuocMoi.value,
          MUCCUOC_CU_ID: this.cboMucCuocCu.value,
          TEN_KH: this.txtTenKH,
          THONGTIN_TT: [
            {
              HDTB_ID: this.hdtb_id,
              KHUVUC_ID: 0,
              KIEUTT_ID: 0,
              NGAY_TT: '',
              TYLE_VAT: 0
            }
          ],
          HDTB_LOAIIP: this.dsHDTB_LoaiIp,
          THUEBAO_ID: this.thuebao_id,
          TOCDO_CU_ID: this.cboTocDoCu.value,
          TOCDO_ID: this.cboTocDoADSL.value,
          TOCDOTHUC_CU_ID: 0,
          TOCDOTHUC_MOI_ID: this.cboTocDoThuc_Moi.value,
          DANGKY_DVGT: this.dsDK_DVGT
        }
        await API.fn_thaydoi_tocdo_internet_capnhat(this.axios, {
          parameters: JSON.stringify(apiParams)
        })
          .then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              this.loading(true)
              if (this.cboDichVuVT.value == DichVuVienThong.DICHVU_CNTT || this.cboDichVuVT.value == DichVuVienThong.ANTOAN_BAOMAT_TT || this.cboDichVuVT.value == DichVuVienThong.TRUNGTAM_DULIEU || this.cboDichVuVT.value == DichVuVienThong.HOINGHI_TRUYENHINH) {
                API.lay_ds_hdtb_thaydoi_tocdo_cntt(this.axios, {
                  hdkh_id: this.hdkh_id
                })
                  .then((response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                      this.HienThiDanhSacHDTB(response.data.data)
                      //console.log("lay_ds_hdtb_thaydoi_tocdo_cntt: "+JSON.stringify(response.data.data));
                    }
                  })
                  .catch((error) => {
                    console.log(error)
                  })
                  .finally(() => {
                    this.loading(false)
                  })
              } else {
                API.sp_hienthidanhsach_hdtb(this.axios, {
                  hdkh_id: this.hdkh_id,
                  dichvuvt_id: this.cboDichVuVT.value
                })
                  .then((response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                      this.HienThiDanhSacHDTB(response.data.data)
                      //console.log("sp_hienthidanhsach_hdtb: "+JSON.stringify(response.data.data));
                    }
                  })
                  .catch((error) => {
                    console.log(error)
                  })
                  .finally(() => {
                    this.loading(false)
                  })
              }
              if (response.data.data.toUpperCase() == 'OK') {
                this.txtMaGD = this.vmagd_temp
                this.SetButton(3)
                this.$toast.success('Cập nhật dữ liệu thành công')
              } else {
                this.$toast.error(response.data.message) //message
              }
            } else {
              this.$toast.error(response.data.message) //message
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {
            this.loading(false)
          })
      } else {
        //cập nhật dữ liệu
        this.quytrinh_id = 0
        let mcuoc_id = 0
        if (this.cboLoaihinhTB.value != LoaiHinhTB.INTERNET_MYTV && this.cboLoaihinhTB.value != LoaiHinhTB.INTERNET_SGTV) {
          mcuoc_id = this.cboMucCuocMoi.value
        }
        let dshg = []
        await API.lay_huonggiao_tiepnhan(this.axios, {
          kieuLdId: this.cboKieuLD.value,
          loaiHdId: LoaiHopDong.THAY_DOI_TOCDO_ADSL,
          loaiTbId: this.cboLoaihinhTB.value,
          mucCuocId: mcuoc_id,
          tocDoId: this.cboTocDoADSL.value
        }).then((response) => {
          if (response.data.error_code === 'BSS-00000000') {
            dshg = response.data.data
          }
        })
        if (dshg.length <= 0) {
          this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
          return false
        }
        this.quytrinh_id = dshg.quytrinh_id
        await this.TaoDuLieu(false)
        this.loading(true)
        let apiParams = {
          ACTION_TYPE: 'U',
          BATBUOC_SOTHANG_CK: this.txtSoThangCK,
          CONGNGHE_CU_ID: this.cboCongNghe_Cu.value,
          CONGNGHE_MOI_ID: this.cboCongNghe_Moi.value,
          CT_TIENHD: this.dsCTTHD,
          CUOC_SD: this.txtCuocSD.value,
          CUOC_TBI: this.txtCuoc_TBI.value,
          DICHVUVT_ID: this.cboDichVuVT.value,
          HD_KHACHHANG: this.dsHDKH,
          HD_THUEBAO: this.dsHDTB,
          HDTB_ADSL: this.dsTDTD_ADSL, //this.ds_hdtb_adsl,
          HDTB_CNTT: this.dsHDTB_CNTT,
          HDTB_ID: this.hdtb_id,
          HDTB_TC: this.dsTIEN_HDTB,
          KHACHHANG_ID: this.khachhang_id,
          KHUYENMAI_HDTB: this.dsKM_HDTB,
          HDTB_DATCOC: this.dsDC_HDTB,
          KIEULD_ID: this.cboKieuLD.value,
          KIEUTRA_CU_ID: this.cboKieuTraCu.value,
          KIEUTRA_MOI_ID: this.cboKieuTraMoi.value,
          KIEUTRAMOI: '0', /// this.cboKieuTraMoi.data.filter(x => x.id ==this.cboKieuTraMoi.value )[0].text,
          LOAITB_ID: this.cboLoaihinhTB.value,
          MA_HD: this.txtMaHD,
          MA_KH: this.txtMaKH,
          MA_TB: this.txtMaTB,
          MUCCUOC_ID: this.cboMucCuocMoi.value,
          MUCCUOC_CU_ID: this.cboMucCuocCu.value,
          TEN_KH: this.txtTenKH,
          THONGTIN_TT: [
            {
              HDTB_ID: this.hdtb_id,
              KHUVUC_ID: 0,
              KIEUTT_ID: 0,
              NGAY_TT: '',
              TYLE_VAT: 0
            }
          ],
          HDTB_LOAIIP: this.dsHDTB_LoaiIp,
          THUEBAO_ID: this.thuebao_id,
          TOCDO_CU_ID: this.cboTocDoCu.value,
          TOCDO_ID: this.cboTocDoADSL.value,
          TOCDOTHUC_CU_ID: 0,
          TOCDOTHUC_MOI_ID: this.cboTocDoThuc_Moi.value,
          DANGKY_DVGT: this.dsDK_DVGT
        }
        await API.fn_thaydoi_tocdo_internet_capnhat(this.axios, {
          parameters: JSON.stringify(apiParams)
        })
          .then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              this.loading(true)
              if (this.cboDichVuVT.value == DichVuVienThong.DICHVU_CNTT || this.cboDichVuVT.value == DichVuVienThong.ANTOAN_BAOMAT_TT || this.cboDichVuVT.value == DichVuVienThong.TRUNGTAM_DULIEU || this.cboDichVuVT.value == DichVuVienThong.HOINGHI_TRUYENHINH) {
                API.lay_ds_hdtb_thaydoi_tocdo_cntt(this.axios, {
                  hdkh_id: this.hdkh_id
                })
                  .then((response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                      this.HienThiDanhSacHDTB(response.data.data)
                      //console.log("lay_ds_hdtb_thaydoi_tocdo_cntt: "+JSON.stringify(response.data.data));
                    }
                  })
                  .catch((error) => {
                    console.log(error)
                  })
                  .finally(() => {
                    this.loading(false)
                  })
              } else {
                API.sp_hienthidanhsach_hdtb(this.axios, {
                  hdkh_id: this.hdkh_id,
                  dichvuvt_id: this.cboDichVuVT.value
                })
                  .then((response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                      this.HienThiDanhSacHDTB(response.data.data)
                      //console.log("sp_hienthidanhsach_hdtb: "+JSON.stringify(response.data.data));
                    }
                  })
                  .catch((error) => {
                    console.log(error)
                  })
                  .finally(() => {
                    this.loading(false)
                  })
              }
              if (response.data.data.toUpperCase() == 'OK') {
                this.SetButton(3)
                this.$toast.success('Cập nhật dữ liệu thành công')
              } else {
                this.$toast.error(response.data.message)
              }
            } else {
              this.$toast.error(response.data.message)
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {
            this.loading(false)
          })
      }
    },
    XoaHDKH() {
      this.loading(true)
      API.xoa_hdkh_v2(this.axios, {
        vhdkh_id: this.hdkh_id,
        vloaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL
      })
        .then((response) => {
          if (response.data.message === 'Success' && response.data.error_code === 'BSS-00000000') {
            this.SetButton(0)
            this.$toast.success('Đã xóa')
          } else {
            this.$toast.error('Lỗi khi xóa!')
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    async ThemHDThueBao() {
      if (!(await this.KiemTraDL_ThueBao())) {
        return false
      }
      var check_thuebaoid = this.lvwThueBao.find(e => e['thuebao_id'] == this.thuebao_id)
      if (check_thuebaoid) {
        this.$toast.error('Thuê bao ID đã tồn tại trong hợp đồng, không thêm mới được ')
        return false
      }
      this.quytrinh_id = 0
      let mcuoc_id = 0
      if (this.cboLoaihinhTB.value != LoaiHinhTB.INTERNET_MYTV && this.cboLoaihinhTB.value != LoaiHinhTB.INTERNET_SGTV) {
        mcuoc_id = this.cboMucCuocMoi.value
      }
      let kiemtra_hdtb = 0
      await API.kiemtra_hdtb_khi_themmoi(this.axios, {
        maTb: this.txtMaTB.trim(),
        hdkhId: this.hdkh_id,
        dichVuVtId: this.cboDichVuVT.value,
        loaiTbId: 0 //fix theo code cũ = 0
      }).then((response) => {
        if (response.data.error_code === 'BSS-00000000') {
          kiemtra_hdtb = response.data.data
        }
      })
      if (kiemtra_hdtb == 1) {
        this.$toast.error('Thuê bao đã tồn tại trong hợp đồng, không thêm mới được ')
        return false
      }
      let dshg = []
      await API.lay_huonggiao_tiepnhan(this.axios, {
        kieuLdId: this.cboKieuLD.value,
        loaiHdId: LoaiHopDong.THAY_DOI_TOCDO_ADSL,
        loaiTbId: this.cboLoaihinhTB.value,
        mucCuocId: mcuoc_id,
        tocDoId: this.cboTocDoADSL.value
      }).then((response) => {
        if (response.data.error_code === 'BSS-00000000') {
          dshg = response.data.data
        }
      })
      if (dshg.length <= 0) {
        this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
        return false
      }
      this.quytrinh_id = dshg.quytrinh_id
      await this.TaoDuLieu_HDTB(true)
      //await this.TaoDuLieu_THONGTIN_TT(true);
      await this.TaoDuLieu_CT_TIENHD(true)
      await this.TaoDuLieu_HDTBDV(true)
      await this.TaoDuLieu_TIEN_HDTB_TC(true)
      await this.TaoDuLieu_HDKH(false)
      await this.TaoDuLieu_HDTB_LOAIIP()
      await this.TaoDuLieu_DKDV()
      if (this.cboDichVuVT.value == DichVuVienThong.ADSL) {
        await this.TaoDuLieu_TDTD_ADSL_2(true)
      } else if (this.cboDichVuVT.value == DichVuVienThong.ANTOAN_BAOMAT_TT || this.cboDichVuVT.value == DichVuVienThong.TRUNGTAM_DULIEU || this.cboDichVuVT.value == DichVuVienThong.DICHVU_CNTT || this.cboDichVuVT.value == DichVuVienThong.HOINGHI_TRUYENHINH) {
        await this.Tao_HDTB_CNTT(true)
      }
      this.loading(true)
      let apiParams = {
        ACTION_TYPE: 'A',
        BATBUOC_SOTHANG_CK: this.txtSoThangCK,
        CONGNGHE_CU_ID: this.cboCongNghe_Cu.value,
        CONGNGHE_MOI_ID: this.cboCongNghe_Moi.value,
        CT_TIENHD: this.dsCTTHD,
        CUOC_SD: this.txtCuocSD.value,
        CUOC_TBI: this.txtCuoc_TBI.value,
        DICHVUVT_ID: this.cboDichVuVT.value,
        HD_THUEBAO: this.dsHDTB,
        HDTB_ADSL: this.dsTDTD_ADSL_2,
        HDTB_ID: this.hdtb_id,
        HDTB_TC: this.dsTIEN_HDTB,
        KHACHHANG_ID: this.khachhang_id,
        KIEULD_ID: this.cboKieuLD.value,
        KIEUTRA_CU_ID: this.cboKieuTraCu.value,
        KIEUTRA_MOI_ID: this.cboKieuTraMoi.value,
        KIEUTRAMOI: '0', /// this.cboKieuTraMoi.data.filter(x => x.id ==this.cboKieuTraMoi.value )[0].text,
        LOAITB_ID: this.cboLoaihinhTB.value,
        MA_HD: this.txtMaHD,
        MA_KH: this.txtMaKH,
        MA_TB: this.txtMaTB,
        MUCCUOC_ID: this.cboMucCuocMoi.value,
        TEN_KH: this.txtTenKH,
        THONGTIN_TT: [
          {
            HDTB_ID: this.hdtb_id,
            KHUVUC_ID: 0,
            KIEUTT_ID: 0,
            NGAY_TT: '',
            TYLE_VAT: 0
          }
        ],
        HDTB_LOAIIP: this.dsHDTB_LoaiIp,
        HD_KHACHHANG: this.dsHDKH,
        THUEBAO_ID: this.thuebao_id,
        TOCDO_CU_ID: this.cboTocDoCu.value,
        TOCDO_ID: this.cboTocDoADSL.value,
        TOCDOTHUC_CU_ID: 0,
        TOCDOTHUC_MOI_ID: this.cboTocDoThuc_Moi.value,
        DANGKY_DVGT: this.dsDK_DVGT,
      }
      await API.fn_thaydoi_tocdo_internet_themhdthuebao(this.axios, {
        parameters: JSON.stringify(apiParams)
      })
        .then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.loading(true)
            if (this.cboDichVuVT.value == DichVuVienThong.DICHVU_CNTT || this.cboDichVuVT.value == DichVuVienThong.ANTOAN_BAOMAT_TT || this.cboDichVuVT.value == DichVuVienThong.TRUNGTAM_DULIEU || this.cboDichVuVT.value == DichVuVienThong.HOINGHI_TRUYENHINH) {
              API.lay_ds_hdtb_thaydoi_tocdo_cntt(this.axios, {
                hdkh_id: this.hdkh_id
              })
                .then((response) => {
                  if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    this.HienThiDanhSacHDTB(response.data.data)
                    //console.log("lay_ds_hdtb_thaydoi_tocdo_cntt: "+JSON.stringify(response.data.data));
                  }
                })
                .catch((error) => {
                  console.log(error)
                })
                .finally(() => {
                  this.loading(false)
                })
            } else {
              API.sp_hienthidanhsach_hdtb(this.axios, {
                hdkh_id: this.hdkh_id,
                dichvuvt_id: this.cboDichVuVT.value
              })
                .then((response) => {
                  if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    this.HienThiDanhSacHDTB(response.data.data)
                    //console.log("1646sp_hienthidanhsach_hdtb: "+JSON.stringify(response.data.data));
                  }
                })
                .catch((error) => {
                  console.log(error)
                })
                .finally(() => {
                  this.loading(false)
                })
            }
            if (response.data.data.toUpperCase() == 'OK') {
              this.txtMaGD = this.vmagd_temp
              this.$toast.success('Thêm thuê bao thành công')
            } else {
              this.$toast.error(response.data.message)
            }
          } else {
            this.$toast.error(response.data.message + '\n' + response.data.data)
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {
          this.loading(false)
        })
      /*  console.log("ThemHD_dsCTTHD: " + JSON.stringify(this.dsCTTHD));
      console.log("ThemHD_dsHDTB: " + JSON.stringify(this.dsHDTB));
      console.log("ThemHD_dsHDTB: " + JSON.stringify(this.ds_hdtb_adsl));
      console.log("ThemHD_dsHDTB: " + JSON.stringify(this.hdtb_id));
      console.log("ThemHD_dsHDTB: " + JSON.stringify(this.dsTIEN_HDTB)); */
    },
    async XoaHDTB() {
      this.loading(true)
      await API.fn_thaydoi_tocdo_internet_xoa_hdtb(this.axios, {
        hdtb_id: this.hdtb_id
      })
        .then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.$toast.success('Xóa thành công!')
            if (this.cboDichVuVT.value == DichVuVienThong.DICHVU_CNTT || this.cboDichVuVT.value == DichVuVienThong.ANTOAN_BAOMAT_TT || this.cboDichVuVT.value == DichVuVienThong.TRUNGTAM_DULIEU || this.cboDichVuVT.value == DichVuVienThong.HOINGHI_TRUYENHINH) {
              API.lay_ds_hdtb_thaydoi_tocdo_cntt(this.axios, {
                hdkh_id: this.hdkh_id
              })
                .then((response) => {
                  if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    this.HienThiDanhSacHDTB(response.data.data)
                    //console.log("lay_ds_hdtb_thaydoi_tocdo_cntt: "+JSON.stringify(response.data.data));
                  }
                })
                .catch((error) => {
                  console.log(error)
                })
                .finally(() => {
                  this.loading(false)
                })
            } else {
              API.sp_hienthidanhsach_hdtb(this.axios, {
                hdkh_id: this.hdkh_id,
                dichvuvt_id: this.cboDichVuVT.value
              })
                .then((response) => {
                  if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    this.HienThiDanhSacHDTB(response.data.data)
                    //console.log("sp_hienthidanhsach_hdtb: "+JSON.stringify(response.data.data));
                  }
                })
                .catch((error) => {
                  console.log(error)
                })
                .finally(() => {
                  this.loading(false)
                })
            }
          } else {
            this.$toast.error('Lỗi khi xóa!')
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {})
    },
    async TaoDuLieu(themmoi) {
      await this.TaoDuLieu_HDKH(themmoi)
      await this.TaoDuLieu_HDTB(themmoi)
      await this.TaoDuLieu_CT_TIENHD(true)
      await this.TaoDuLieu_HDTBDV()
      await this.TaoDuLieu_DKDV()
      await this.TaoDuLieu_TIEN_HDTB_TC(true)
      switch (this.cboDichVuVT.value*1) {
        case DichVuVienThong.ADSL:
          await this.TaoDuLieu_TDTD_ADSL(themmoi)
          if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FTTH) {
            await this.TaoDuLieu_HDTB_LOAIIP()
          }
          break
        case DichVuVienThong.MEGA_EYES:
          await this.TaoDuLieu_TDTD_ADSL()
          break  
        case DichVuVienThong.ANTOAN_BAOMAT_TT:
          await this.Tao_HDTB_CNTT()
          break
        case DichVuVienThong.TRUNGTAM_DULIEU:
          await this.Tao_HDTB_CNTT()
          break
        case DichVuVienThong.DICHVU_CNTT:
          await this.Tao_HDTB_CNTT()
          break
        case DichVuVienThong.HOINGHI_TRUYENHINH:
          await this.Tao_HDTB_CNTT()
          break
        default:
          await this.Tao_HDTB_CNTT()
          break
      }
    },
    async TaoDuLieu_HDKH(themmoi) {
      this.dsHDKH = []
      var rowHDKH = {}
      if (themmoi) {
        rowHDKH.HDKH_ID = 0
        await API.post_get_keys(this.axios, { keyname: 'HD_KHACHHANG' }).then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.hdkh_id = parseInt(response.data.data)
            rowHDKH.HDKH_ID = this.hdkh_id
          }
        })
        if (this.ts_sinhma_gd_theo_donvi) {
          await API.sinh_magd_v2(this.axios, {
            donvi_id: this.$root.token.getDonViID(),
            loaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL
          }).then((response) => {
            if (response.data.error_code === 'BSS-00000000') {
              this.vmagd_temp = response.data.data
            }
          })
        } else {
          await API.sinh_magd(this.axios, {
            loaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL
          }).then((response) => {
            if (response.data.error_code === 'BSS-00000000') {
              this.vmagd_temp = response.data.data
            }
          })
        }
        if (this.vmagd_temp.includes('ERROR:')) {
          this.$toast.error(this.vmagd_temp.split(':')[1])
          this.loi_sinh_magd = true
        }
        rowHDKH.MA_GD = this.vmagd_temp
        rowHDKH.MA_HD = this.vmagd_temp
      } else {
        rowHDKH.HDKH_ID = this.hdkh_id
        rowHDKH.MA_GD = this.txtMaGD
        rowHDKH.MA_HD = this.txtMaHD
      }
      rowHDKH.MA_KH = this.txtMaKH
      if (this.khachhang_id > 0) {
        rowHDKH.KHACHHANG_ID = this.khachhang_id
      }
      if (this.hdkh_cha_id > 0) {
        rowHDKH.HDKH_CHA_ID = this.hdkh_cha_id
      }
      rowHDKH.TEN_KH = this.txtTenKH
      rowHDKH.DIACHI_KH = this.txtDiaChiKH
      if (moment(this.dtpNgayLapHD).isValid()) {
        rowHDKH.NGAYLAP_HD = moment(this.dtpNgayLapHD).format('DD/MM/YYYY')
      } else {
        rowHDKH.NGAYLAP_HD = this.dtpNgayLapHD
      }
      //Hiếu bổ sung một số thông tin HDKH 24/08/2010
      if (moment(this.dtpNgayYeuCau).isValid()) {
        rowHDKH.NGAY_YC = moment(this.dtpNgayYeuCau).format('DD/MM/YYYY')
      } else {
        rowHDKH.NGAY_YC = this.dtpNgayYeuCau
      }
      if (this.txtNgaySN != '') {
        rowHDKH.NGAY_SN = this.txtNgaySN
      }
      rowHDKH.DONVI_ID = this.$root.token.getDonViID()
      rowHDKH.NHANVIEN_ID = this.$root.token.getNhanVienID()
      rowHDKH.PHANLOAIKH_ID = this.plkhachhang_id
      if (this.khachhang_id > 0) {
        var khl_id = -1
        rowHDKH.KHLON_ID = 0
        await API.fn_tt_db_khachhang(this.axios, { param: this.khachhang_id, type: 1 }).then((response) => {
          if (response.data.error_code === 'BSS-00000000') {
            khl_id = response.data.data
            if (khl_id != '' && khl_id != '-1') {
              rowHDKH.KHLON_ID = khl_id
            } else {
              rowHDKH.KHLON_ID = 1
            }
          } else {
            rowHDKH.KHLON_ID = 1
          }
        })
      } else {
        rowHDKH.KHLON_ID = 1
      }
      if (this.filterbox.ctv.ischecked && this.filterbox.ctv.value != '') {
        rowHDKH.CTV_ID = this.filterbox.ctv.ctv_id
      }
      if (this.filterbox.nguoigt.ischecked && this.filterbox.nguoigt.value != '') {
        rowHDKH.NHANVIENGT_ID = this.filterbox.nguoigt.nguoigt_id
      }
      rowHDKH.LOAIHD_ID = LoaiHopDong.THAY_DOI_TOCDO_ADSL
      rowHDKH.NGUOI_CN = this.$root.token.getUserName()
      rowHDKH.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')
      //PNLINH B2A 02/05/2020
      if (this.VPHIEUCSKH_ID != 0) {
        rowHDKH.PHIEUCSKH_ID = this.VPHIEUCSKH_ID
      }
      //Bổ sung cho HCM theo y/c chị Tô Thảo - TP DHNV TTKD
      rowHDKH.MA_DUAN = this.txtMaDuAn
      this.dsHDKH.push(rowHDKH)
      /* if (themmoi == false) {
        this.dsHDKH = [];
      } */
      //console.log("dsHDKH: " + JSON.stringify(this.dsHDKH));
    },
    async TaoDuLieu_HDTB(themmoi) {
      this.dsHDTB = []
      var rowHDTB = {}
      if (themmoi == true) {
        await API.post_get_keys(this.axios, { keyname: 'HD_THUEBAO' }).then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.hdtb_id = response.data.data
          }
        })
      }
      rowHDTB.HDTB_ID = this.hdtb_id
      rowHDTB.HDKH_ID = this.hdkh_id
      if (this.thuebao_id > 0) {
        rowHDTB.THUEBAO_ID = this.thuebao_id
      }
      rowHDTB.TEN_TB = this.txtTenTB
      rowHDTB.MA_TB = this.txtMaTB
      rowHDTB.DICHVUVT_ID = this.cboDichVuVT.value
      rowHDTB.LOAITB_ID = this.cboLoaihinhTB.value
      rowHDTB.KIEULD_ID = this.cboKieuLD.value
      rowHDTB.DOITUONG_ID = this.cboDoiTuongMoi.value //doituong_id
      rowHDTB.TTHD_ID = TrangThaiHD.MOI
      rowHDTB.MUCUOCTB_ID = this.cboMucCuocTB.value
      rowHDTB.MAY_CN = this.$root.token.getUserName()
      rowHDTB.DONVI_ID = this.$root.token.getDonViID()
      rowHDTB.NGUOI_CN = this.$root.token.getUserName()
      rowHDTB.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')
      rowHDTB.GHICHU = this.txtGhiChu ? this.txtGhiChu : ''
      rowHDTB.QUYTRINH_ID = this.quytrinh_id
      rowHDTB.DIACHI_TB = this.diachi_tb
      rowHDTB.DIACHI_LD = this.diachi_ld
      if (this.cboMucCuocTB.value != 0) {
        rowHDTB.MUCUOCTB_ID = this.cboMucCuocTB.value
        rowHDTB.MUCUOC_TB = 0
        await API.checkdataMAP_ID_muccuoctb(this.axios, this.cboMucCuocTB.value).then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            rowHDTB.MUCUOC_TB = response.data.data
          }
        })
      }
      rowHDTB.STATUS = TRANGTHAI_DONGBO.CHUADONGBO
      rowHDTB.SOTHANG_CK = this.txtSoThangCK ? this.txtSoThangCK : 0
      this.dsHDTB.push(rowHDTB)
      //console.log("dsHDTB: " + JSON.stringify(this.dsHDTB));
      //longdx: bo sung thong tin hdtb_id vao bang khuyenmai, dat coc
      for (var row of this.dsDC_HDTB) {
        ;(row.HDTB_ID = this.hdtb_id), (row.THUEBAO_DC_ID = this.thuebao_id)
      }
      for (var row of this.dsKM_HDTB) {
        ;(row.HDTB_ID = this.hdtb_id), (row.THUEBAO_DC_ID = this.thuebao_id)
      }
    },
    async TaoDuLieu_CT_TIENHD(themmoi) {
      if (this.ds_tien_khoanmuc.length < 1) {
        this.ds_tien_khoanmuc = [
          { khoanmuctt_id: 3, tentat_kmtt: 'Thay Đổi Tốc Độ', tongtien: 0, tien: '0', vat: '0', flag: 1 },
          { khoanmuctt_id: 11, tentat_kmtt: 'Trả Trước', tongtien: 0, tien: '0', vat: '0', flag: 0 }
        ]
      }
      this.dsCTTHD = []
      var rowCTTHD = {}
      for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
        var rowCTTHD = {}
        rowCTTHD.HDTB_ID = this.hdtb_id
        rowCTTHD.CT_TIENHD_ID = 0
        if (themmoi) {
          await API.post_get_keys(this.axios, { keyname: 'CT_TIENHD' }).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              this.CT_TIENHD_ID = response.data.data
              rowCTTHD.CT_TIENHD_ID = response.data.data
            }
          })
        }
        rowCTTHD.KHOANMUCTT_ID = Number(this.ds_tien_khoanmuc[i]['khoanmuctt_id'])
        rowCTTHD.TIEN = Math.round(this.ds_tien_khoanmuc[i]['tien'])
        rowCTTHD.VAT = Math.round(this.ds_tien_khoanmuc[i]['vat'])
        if (
          this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_KM_LAPDAT ||
          this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_KM_THIETBI ||
          this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_KM_DATCOC ||
          this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_TIENTHOAI_DC_DOI_TD
        ) {
          rowCTTHD.TIEN = rowCTTHD.TIEN * -1
          rowCTTHD.VAT = rowCTTHD.VAT * -1
        }
        rowCTTHD.ID = this.ds_tien_khoanmuc[i]['khoanmuctt_id']
        rowCTTHD.LOAI_ID = 1
        this.dsCTTHD.push(rowCTTHD)
      }
      //console.log("dsCTTHD: " + JSON.stringify(this.dsCTTHD));
    },
    async TaoDuLieu_HDTBDV() {
      this.dsHDTBDV = []
      let dtTemp = []
      var self = this
      await API.ur2_1_035_038_temp(this.axios, {
        thuebao_id: this.thuebao_id
      }).then((response) => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          response.data.data.forEach(function (item) {
            var row = {}
            row.HDTB_ID = self.hdtb_id
            row.LOAIDV_ID = item.loaidv_id
            row.DONVI_ID = item.donvi_id
            row.KIEUDV_ID = item.kieudv_id
            self.dsHDTBDV.push(row)
          })
          //console.log("dsHDTBDV: " + JSON.stringify(this.dsHDTBDV));
        }
      })
    },
    TaoDuLieu_DKDV() {
      this.dsDK_DVGT = []
      let kieu_dk_dvgt = 0
      if (this.m_dsThueBao_DichVu && this.m_dsThueBao_DichVu.length > 0) {
        for (let dr of this.m_dsThueBao_DichVu) {
          if (dr['dangky'] && Number(dr['dangky']) == 1) kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_THEM_MOI_DVGT
          else if (dr['huy'] && Number(dr['huy']) == 1) kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_HUY_DVGT
          else {
            continue
          }

          let rowHDTDDV = {}
          rowHDTDDV.HDTB_ID = this.hdtb_id
          if (moment(this.dtpNgayYeuCau).isValid()) {
            rowHDTDDV.NGAY_YC = moment(this.dtpNgayYeuCau).format('DD/MM/YYYY')
          } else {
            rowHDTDDV.NGAY_YC = this.dtpNgayYeuCau
          }
          rowHDTDDV.DICHVUGT_ID = dr['dichvugt_id']
          rowHDTDDV.NOIDUNG = dr['noidung']
          rowHDTDDV.KIEU_YC = kieu_dk_dvgt
          if (dr['cuoc_sd']) rowHDTDDV.CUOC_SD = dr['cuoc_sd']
          else rowHDTDDV.CUOC_SD = 0
          if (dr['vat_sd']) rowHDTDDV.VAT_SD = dr['vat_sd']
          else rowHDTDDV.VAT_SD = 0

          rowHDTDDV.SL_CHA = 0
          let jsl_cha = 0
          if (jsl_cha != 0 && dr['block_tiep'] && dr['block_tiep'] && dr['block_tiep'] != 0) rowHDTDDV.SL_CHA = jsl_cha
          else rowHDTDDV.SL_CHA = 0

          if (dr['block_dau']) rowHDTDDV.BLOCK_DAU = dr['block_dau']
          else rowHDTDDV.BLOCK_DAU = 0

          if (dr['block_tiep']) rowHDTDDV.BLOCK_TIEP = dr['block_tiep']
          else rowHDTDDV.BLOCK_TIEP = 0

          if (dr['gia_block_tiep']) rowHDTDDV.GIA_BLOCK_TIEP = dr['gia_block_tiep']
          else rowHDTDDV.GIA_BLOCK_TIEP = 0

          if (dr['vat_block_tiep']) rowHDTDDV.VAT_BLOCK_TIEP = dr['vat_block_tiep']
          else rowHDTDDV.VAT_BLOCK_TIEP = 0

          if (dr['he_so']) rowHDTDDV.HE_SO = dr['he_so']
          else rowHDTDDV.HE_SO = 0

          rowHDTDDV.TTTH = 1
          this.dsDK_DVGT.push(rowHDTDDV)
        }
      }
    },
    //Hoangpkn : 11/05/2012
    //Inset tiền trả trước MyTV vào database
    TaoDuLieu_TIEN_HDTB_TC() {
      this.dsTIEN_HDTB = []
      var rowTIEN_HDTB = {}
      rowTIEN_HDTB.HDTB_ID = this.hdtb_id
      rowTIEN_HDTB.THUEBAO_ID = this.thuebao_id
      rowTIEN_HDTB.LOAITIEN_ID = 3
      rowTIEN_HDTB.GIATRI = this.txtTienTra
      this.dsTIEN_HDTB.push(rowTIEN_HDTB)
      //console.log("dsTIEN_HDTB: " + JSON.stringify(this.dsTIEN_HDTB));
    },
    TaoDuLieu_HDTB_LOAIIP() {
      try {
        this.dsHDTB_LoaiIp = []
        if (this.cboLoaiIP.values && this.cboLoaiIP.values.length > 0) {
          for (var loaiip of this.cboLoaiIP.values) {
            var rowHDTB_LIP = {}
            rowHDTB_LIP.HDTB_ID = this.hdtb_id
            rowHDTB_LIP.LOAIIP_ID = loaiip //this.cboLoaiIP.Properties.Items[i].Value);
            this.dsHDTB_LoaiIp.push(rowHDTB_LIP)
          }
        }
      } catch (ex) {
        this.$toast.error('' + ex)
      }
    },
    async Tao_HDTB_CNTT() {
      await this.Init_Properties(this.cboLoaihinhTB.value, 'HDTB_CNTT', '00000')
      for (var index1 = 0; index1 < this.ItemList.length; ++index1) {
        if (this.ItemList[index1].sFieldName == 'TOCDO_ID') {
          this.ItemList[index1].sValue = this.cboTocDoADSL.value
        }
        if (this.phainhap_thoihan && this.chkThoiHan.checked) {
          if (this.ItemList[index1].sFieldName == 'SO_THANG') {
            this.ItemList[index1].sValue = this.txtSoThang.trim()
          }
          if (this.ItemList[index1].sFieldName == 'NGAY_BD') {
            this.ItemList[index1].sValue = moment(this.dtpTuNgay).format('DD/MM/YYYY')
          }
          if (this.ItemList[index1].sFieldName == 'NGAY_KT') {
            this.ItemList[index1].sValue = moment(this.dtpDenNgay).format('DD/MM/YYYY')
          }
        }
      }
      this.dsHDTB_CNTT = this.ItemList
      //console.log("dsHDTB_CNTT: " + JSON.stringify(this.dsHDTB_CNTT));
    },
    async Init_Properties(vloaitb_id, table_name, opt_list) {
      await API.lay_danhsach_thuoctinh_v2(this.axios, {
        loaitb_id: vloaitb_id,
        ten_bang: table_name,
        opt_list: opt_list
      }).then((response) => {
        if (response.data.data && response.data.error_code === 'BSS-00000000') {
          var ds = response.data.data
          this.ItemList = []
          if (ds.length <= 0) return
          for (var row of ds) {
            var item = {}
            item.sFieldName = row['field_name']
            item.sCaption = row['ten_hienthi']
            item.iAtt = row['att']
            item.iDataType = row['data_type']
            item.sMaxValue = row['max_value']
            item.sMinvalue = row['min_value']
            item.sValue = row['field_value']
            item.nFieldLength = row['field_length'] ? 0 : row['field_length']
            item.bIsNotNull = row['field_nullable'] ? false : row['field_nullable'] == '0' ? true : false
            item.bIsUnique = row['field_unique'] ? false : row['field_unique'] == '0' ? false : true
            item.sLookUpTableName = row['parent_table']
            item.sLookUpKeyField = row['parent_keyfield']
            item.sLookUpValueField = row['parent_descfield']
            item.strDepandField = row['depend_field']
            item.StrSql = row['lookup_sql']
            this.ItemList.push(item)
          }
        } else {
          //console.log(response.data.message)
        }
      })
      //console.log("lay_danhsach_thuoctinh_v2-2234: " + JSON.stringify(this.ItemList));
    },
    //region Tao du lieu cho bang HDTB_ADSL
    async TaoDuLieu_TDTD_ADSL(themmoi) {
      this.dsTDTD_ADSL = []
      var rowTDTD_ADSL = {}
      let profiledsl_id = -1

      //LAY_DS_DB_ADSL_THEO_TBID
      let result = await this.LAY_DS_DB_ADSL_THEO_TBID(this.thuebao_id)

      //upper case key data source
      for (var i = 0; i < result.length; i++) {
        for (var key in result[i]) {
          if (key.toUpperCase() !== key) {
            result[i][key.toUpperCase()] = result[i][key]
            delete result[i][key]
          }
        }
      }

      if (result instanceof Array) Object.assign(rowTDTD_ADSL, result[0])
      else if (result instanceof Object) Object.assign(rowTDTD_ADSL, result)

      console.log('TaoDuLieu_TDTD_ADSL rowTDTD_ADSL', rowTDTD_ADSL)
      console.log('TaoDuLieu_TDTD_ADSL result', result)

      rowTDTD_ADSL.HDTB_ID = this.hdtb_id
      rowTDTD_ADSL.TOCDO_ID = this.cboTocDoADSL.value
      //long profiledsl_id = Convert.ToInt32(checkdata.MAP_ID("profiledsl_id", DatabaseConstants.DB2 + ".tocdo_adsl", "where tocdo_id =" + cboTocDoADSL.EditValue.ToString()));
      API.fn_tt_tocdo_adsl(this.axios, { param: this.cboLoaiTBiADSL.value, type: 3 })
        .then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            profiledsl_id = response.data.data
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {})
      //console.log("profiledsl_id: " + profiledsl_id);
      if (profiledsl_id != -1) {
        rowTDTD_ADSL.PROFILEDSL_ID = profiledsl_id
      } else {
        rowTDTD_ADSL.PROFILEDSL_ID = null
      }
      rowTDTD_ADSL.MUCCUOC_ID = this.cboMucCuocMoi.value
      if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_MYTV) {
        rowTDTD_ADSL.KIEUTRA_ID = this.cboKieuTraMoi.value
        if (
          this.cboKieuLD.value == KieuLapDat.THAYDOI_KIEUTRA_MYTV
          //&& Convert.ToInt32(cboKieuTraMoi.SelectedValue) == KieuTra.TRATRUOC
        ) {
          rowTDTD_ADSL.THANG_ID = this.cboThangTra.value
          rowTDTD_ADSL.NGAYTRA = moment(this.dtpNgayTra).format('DD/MM/YYYY')
        }
      }
      if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
        rowTDTD_ADSL.CUOC_SD = this.txtCuocSD.value
        rowTDTD_ADSL.CUOC_TBI = this.txtCuoc_TBI.value
      }
      rowTDTD_ADSL.TRANGBI_ID = this.cboTrangBiMoi.value
      rowTDTD_ADSL.IP = this.IP
      rowTDTD_ADSL.THONGTIN_TC = this.txtTT_Cuoc.value
      if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_MYTV || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_SGTV) {
        rowTDTD_ADSL.TUCTHI = 2 // Với mytv, saigontv thì thay đổi tức thì luôn.
      } else {
        if (this.chkTucThi.checked == false) {
          rowTDTD_ADSL.TUCTHI = 0
        }
        if (this.chkTucThi.checked == true && this.cboKieuLD.value == KieuLapDat.THAYDOI_MUCCUOC_ADSL) {
          rowTDTD_ADSL.TUCTHI = 1
        }
        if (
          this.chkTucThi.checked == true &&
          (
            this.cboKieuLD.value == KieuLapDat.THAYDOI_TOCDO_ADSL ||
            this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON ||
            this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON || 
            this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_XGSPON ||
            this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_XGSPON_GPON
          )
        ) {
          rowTDTD_ADSL.TUCTHI = 2
        }
        if (this.chkTucThi.checked == true && this.cboKieuLD.value == KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL) {
          rowTDTD_ADSL.TUCTHI = 3
        }
        if (this.chkTucThi.checked == true && (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC)) {
          rowTDTD_ADSL.TUCTHI = 4
        }
      }
      if (this.cboKieuLD.value == KieuLapDat.THAYDOI_TOCDOTHUC_ADSL) {
        rowTDTD_ADSL.TOCDOTHUC_ID = this.cboTocDoThuc_Moi.value
        rowTDTD_ADSL.TUCTHI = 5 // Thay đổi tđ thực thì luôn tức thì
      }
      if (
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON || 
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON ||  
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_XGSPON ||
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_AON_XGSPON ||
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_GPON_XGSPON ||
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_XGSPON_GPON ||
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_XGSPON_GPON
      ) {
        rowTDTD_ADSL.CONGNGHE_ID = this.cboCongNghe_Moi.value
      }
      rowTDTD_ADSL.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')
      rowTDTD_ADSL.TTHD_ID = TrangThaiHD.MOI
      rowTDTD_ADSL.HDTB_ID = this.hdtb_id
      rowTDTD_ADSL.MA_TB = this.txtMaTB
      rowTDTD_ADSL.BRAS_ID = this.bras_id ? this.bras_id : rowTDTD_ADSL.BRAS_ID ? rowTDTD_ADSL.BRAS_ID : null
      rowTDTD_ADSL.DSLAM_ID = this.dslam_id ? this.dslam_id : rowTDTD_ADSL.DSLAM_ID ? rowTDTD_ADSL.DSLAM_ID : null
      rowTDTD_ADSL.PORT_ID = this.port_id ? this.port_id : rowTDTD_ADSL.PORT_ID ? rowTDTD_ADSL.PORT_ID : null
      rowTDTD_ADSL.VCI_VPI_ID = this.vci_vpi_id ? this.vci_vpi_id : rowTDTD_ADSL.VCI_VPI_ID ? rowTDTD_ADSL.VCI_VPI_ID : null
      rowTDTD_ADSL.MADOICAP = this.madoicap ? this.madoicap : rowTDTD_ADSL.MADOICAP ? rowTDTD_ADSL.MADOICAP : null
      rowTDTD_ADSL.MA_LT = this.ma_lt ? this.ma_lt : rowTDTD_ADSL.MA_LT ? rowTDTD_ADSL.MA_LT : null
      rowTDTD_ADSL.DIACHI_LD = this.diachi_ld
      rowTDTD_ADSL.DIACHI_TB = this.diachi_tb
      this.dsTDTD_ADSL.push(rowTDTD_ADSL)
      console.log('dsTDTD_ADSL: ' + JSON.stringify(this.dsTDTD_ADSL))
      /*  if (themmoi == false) {
        this.dsTDTD_ADSL = [];
      } */
    },
    async LAY_DS_DB_ADSL_THEO_TBID(thuebao_id) {
      try {
        this.loading(true)
        let response = await API.LAY_DS_DB_ADSL_THEO_TBID(this.axios, thuebao_id)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async TaoDuLieu_TDTD_ADSL_2(themmoi) {
      this.dsTDTD_ADSL_2 = []
      var rowTDTD_ADSL = {}
      let profiledsl_id = -1
      rowTDTD_ADSL.HDTB_ID = this.hdtb_id
      rowTDTD_ADSL.TOCDO_ID = this.cboTocDoADSL.value
      //long profiledsl_id = Convert.ToInt32(checkdata.MAP_ID("profiledsl_id", DatabaseConstants.DB2 + ".tocdo_adsl", "where tocdo_id =" + cboTocDoADSL.EditValue.ToString()));
      API.fn_tt_tocdo_adsl(this.axios, { param: this.cboLoaiTBiADSL.value, type: 3 })
        .then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            profiledsl_id = response.data.data
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {})
      //console.log("profiledsl_id: " + profiledsl_id);
      if (profiledsl_id != -1) {
        rowTDTD_ADSL.PROFILEDSL_ID = profiledsl_id
      } else {
        rowTDTD_ADSL.PROFILEDSL_ID = null
      }
      rowTDTD_ADSL.MUCCUOC_ID = this.cboMucCuocMoi.value
      if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_MYTV) {
        rowTDTD_ADSL.KIEUTRA_ID = this.cboKieuTraMoi.value
        if (
          this.cboKieuLD.value == KieuLapDat.THAYDOI_KIEUTRA_MYTV
          //&& Convert.ToInt32(cboKieuTraMoi.SelectedValue) == KieuTra.TRATRUOC
        ) {
          rowTDTD_ADSL.THANG_ID = this.cboThangTra.value
          rowTDTD_ADSL.NGAYTRA = moment(this.dtpNgayTra).format('DD/MM/YYYY')
        }
      }
      if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
        rowTDTD_ADSL.CUOC_SD = this.txtCuocSD.value.trim()
        rowTDTD_ADSL.CUOC_TBI = this.txtCuoc_TBI.value.trim()
      }
      rowTDTD_ADSL.TRANGBI_ID = this.cboTrangBiMoi.value
      rowTDTD_ADSL.IP = this.IP
      rowTDTD_ADSL.THONGTIN_TC = this.txtTT_Cuoc.value
      rowTDTD_ADSL.BRAS_ID = this.bras_id
      if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_MYTV || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_SGTV) {
        rowTDTD_ADSL.TUCTHI = 2 // Với mytv, saigontv thì thay đổi tức thì luôn.
      } else {
        if (this.chkTucThi.checked == false) {
          rowTDTD_ADSL.TUCTHI = 0
        }
        if (this.chkTucThi.checked == true && this.cboKieuLD.value == KieuLapDat.THAYDOI_MUCCUOC_ADSL) {
          rowTDTD_ADSL.TUCTHI = 1
        }
        if (
          this.chkTucThi.checked == true &&
          (
            this.cboKieuLD.value == KieuLapDat.THAYDOI_TOCDO_ADSL ||
            this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON ||
            this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON ||
            this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_XGSPON ||
            this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_XGSPON_GPON
          )
        ) {
          rowTDTD_ADSL.TUCTHI = 2
        }
        if (this.chkTucThi.checked == true && this.cboKieuLD.value == KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL) {
          rowTDTD_ADSL.TUCTHI = 3
        }
        if (this.chkTucThi.checked == true && (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC)) {
          rowTDTD_ADSL.TUCTHI = 4
        }
      }
      if (this.cboKieuLD.value == KieuLapDat.THAYDOI_TOCDOTHUC_ADSL) {
        rowTDTD_ADSL.TOCDOTHUC_ID = this.cboTocDoThuc_Moi.value
        rowTDTD_ADSL.TUCTHI = 5 // Thay đổi tđ thực thì luôn tức thì
      }
      if (
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON || 
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON ||  
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_XGSPON ||
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_AON_XGSPON ||
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_GPON_XGSPON ||
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_XGSPON_GPON ||
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_XGSPON_GPON
      ) {
        rowTDTD_ADSL.CONGNGHE_ID = this.cboCongNghe_Moi.value
      }
      rowTDTD_ADSL.EMAIL = null
      rowTDTD_ADSL.KH_CD = null
      rowTDTD_ADSL.CULY = null
      rowTDTD_ADSL.SO_DT = null
      rowTDTD_ADSL.MATB_MGWAN = null
      rowTDTD_ADSL.GHICHU = null
      rowTDTD_ADSL.CULY_TN = null
      rowTDTD_ADSL.NHANVIEN_CD_ID = null
      rowTDTD_ADSL.TT_TC_DM = 0
      rowTDTD_ADSL.NHANVIEN_HC_ID = null
      rowTDTD_ADSL.HC_DM = 0
      rowTDTD_ADSL.NGAY_TCDM = null
      rowTDTD_ADSL.NGAYCN_HC = null
      rowTDTD_ADSL.LOAI_EMAIL = null
      rowTDTD_ADSL.PASSWORD = null
      rowTDTD_ADSL.DSLAM_ID = null
      rowTDTD_ADSL.HANDATCHO = null
      rowTDTD_ADSL.MATB_TN = null
      rowTDTD_ADSL.MADOICAP = null
      rowTDTD_ADSL.MA_LT = null
      rowTDTD_ADSL.TYLE_GC = null
      rowTDTD_ADSL.NGAY_GC = null
      rowTDTD_ADSL.TG_GIAM = null
      rowTDTD_ADSL.IP_MP = null
      rowTDTD_ADSL.THIETBIDC_ID = null
      rowTDTD_ADSL.PORT_ID = null
      rowTDTD_ADSL.VCI_VPI_ID = null
      rowTDTD_ADSL.TRAMTB_ID = null
      rowTDTD_ADSL.TUYENCAP = null
      rowTDTD_ADSL.MA_XACTHUC = null
      rowTDTD_ADSL.LIENTU = null
      rowTDTD_ADSL.DOICAP_P = null
      rowTDTD_ADSL.HOPCAP_P = null
      rowTDTD_ADSL.TUCAP_P = null
      rowTDTD_ADSL.LOAICAP_ID = null
      rowTDTD_ADSL.CAP_GOC = null
      rowTDTD_ADSL.CAP_P = null
      rowTDTD_ADSL.DOICAP_GOC = null
      rowTDTD_ADSL.TUCAP_GOC = null
      rowTDTD_ADSL.STATUS_IP = null
      rowTDTD_ADSL.CHUQUAN_ID = null
      rowTDTD_ADSL.CAP_ID = null
      rowTDTD_ADSL.KETCUOI_ID = null
      rowTDTD_ADSL.DOICAP = null
      rowTDTD_ADSL.VITRI = null
      rowTDTD_ADSL.DOICAP_2 = null
      rowTDTD_ADSL.VITRI_2 = null
      rowTDTD_ADSL.SERI_MD = null
      rowTDTD_ADSL.SLID = null
      rowTDTD_ADSL.VMP = null
      rowTDTD_ADSL.SLTV_HTVC = null
      rowTDTD_ADSL.SYNC = null
      rowTDTD_ADSL.CUOC_SODEP = null
      rowTDTD_ADSL.CUOC_QL = null
      rowTDTD_ADSL.TL_GIAMCUOC_QL = null
      rowTDTD_ADSL.CUOC_TN = null
      rowTDTD_ADSL.KIEUONU_ID = null
      rowTDTD_ADSL.THONGTIN_WF = null
      rowTDTD_ADSL.MATKHAU_WF = null
      rowTDTD_ADSL.VITRI_WF = null
      rowTDTD_ADSL.CONGSUAT_WF3M = null
      rowTDTD_ADSL.MOTACS_WF3M = null
      rowTDTD_ADSL.CONGSUAT_WF1T = null
      rowTDTD_ADSL.MOTACS_WF1T = null
      rowTDTD_ADSL.CONGSUAT_WF1TR = null
      rowTDTD_ADSL.MOTACS_WF1TR = null
      rowTDTD_ADSL.CONGSUAT_WF2T = null
      rowTDTD_ADSL.MOTACS_WF2T = null
      rowTDTD_ADSL.CONGSUAT_WF2TR = null
      rowTDTD_ADSL.MOTACS_WF2TR = null
      rowTDTD_ADSL.SYNC_STB = null
      rowTDTD_ADSL.PORT_MYTV = null
      rowTDTD_ADSL.SOTANG = null
      rowTDTD_ADSL.MOTA_ST = null
      rowTDTD_ADSL.HANG_SX = null
      rowTDTD_ADSL.HARDWARE_VERSION = null
      rowTDTD_ADSL.FIRMWARE_VERSION = null
      rowTDTD_ADSL.THOIHAN_ID = null
      rowTDTD_ADSL.TOANHA_ID = null
      rowTDTD_ADSL.DOITAC_ID = null
      rowTDTD_ADSL.LOAI_CSHT_ID = null
      rowTDTD_ADSL.WAN_IP = null
      rowTDTD_ADSL.LAN_IP = null
      rowTDTD_ADSL.DSLAM_TINH_ID = null
      rowTDTD_ADSL.CHUQUANLD_ID = null
      rowTDTD_ADSL.SERIAL_STB = null
      rowTDTD_ADSL.CUOC_DOITAC = null
      rowTDTD_ADSL.TINHKHAC = null
      rowTDTD_ADSL.PHANLOAI_ID = null
      rowTDTD_ADSL.IPV4_WAN = null
      rowTDTD_ADSL.IPV6_LAN = null
      rowTDTD_ADSL.MA_VNPT = null
      rowTDTD_ADSL.MAC_ADDR = null
      rowTDTD_ADSL.USERNAME = null
      rowTDTD_ADSL.BNM_SLID_SN = null
      rowTDTD_ADSL.LAY_SN = null
      rowTDTD_ADSL.LAY_SN_CU = null
      rowTDTD_ADSL.MOTA_TEMBH = null
      rowTDTD_ADSL.SN_SLID_CU = null
      rowTDTD_ADSL.THIETBI_DICH = null
      rowTDTD_ADSL.MATOCDO = 500000637
      rowTDTD_ADSL.SLOT = null
      rowTDTD_ADSL.PORT = null
      rowTDTD_ADSL.SYSTEM = null
      rowTDTD_ADSL.RACK = null
      rowTDTD_ADSL.SHELF = null
      rowTDTD_ADSL.THAMSO = null
      rowTDTD_ADSL.TT_PORT_ID = null
      rowTDTD_ADSL.DSLAM_ID_TINH = null
      rowTDTD_ADSL.VCI = null
      rowTDTD_ADSL.VPI = null
      rowTDTD_ADSL.PORT_MDF = null
      rowTDTD_ADSL.SELFID01 = null
      rowTDTD_ADSL.SELFID02 = null
      rowTDTD_ADSL.SELFID03 = null
      rowTDTD_ADSL.SELFID04 = null
      this.dsTDTD_ADSL_2.push(rowTDTD_ADSL)
      //console.log("dsTDTD_ADSL_2: " + JSON.stringify(this.dsTDTD_ADSL_2));
      /*  if (themmoi == false) {
        this.dsTDTD_ADSL = [];
      } */
    },
    async HienThiTTHopDongKH(ds) {
      this.loading(true)
      try {
        if (ds.length > 0) {
          this.hdkh_id = ds[0]['hdkh_id']
          this.txtMaGD = ds[0]['ma_gd']
          this.txtMaHD = ds[0]['ma_hd']
          this.txtMaKH = ds[0]['ma_kh']
          this.khachhang_id = ds[0]['khachhang_id'] ? ds[0]['khachhang_id'] : 0
          this.txtTenKH = ds[0]['ten_kh']
          this.txtDiaChiKH = ds[0]['diachi_kh']
          this.dtpNgayLapHD = ds[0]['ngaylap_hd']
          this.dtpNgayYeuCau = ds[0]['ngay_yc']
          this.txtNgaySN = ds[0]['ngay_sn'] ? ds[0]['ngay_sn'] : ''
          if (ds[0]['ctv_id']) {
            await API.fn_lay_thongtin_nhanvien(this.axios, {
              nhanvien_id: ds[0]['ctv_id']
            }).then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                this.filterbox.ctv.value = response.data.data
                this.filterbox.ctv.ctv_id = ds[0]['ctv_id']
                this.filterbox.ctv.ischecked = true
              }
            })
          } else {
            this.filterbox.ctv.value = ''
            this.filterbox.ctv.ma_ctv = ''
            this.filterbox.ctv.ctv_id = 0
            this.filterbox.ctv.ischecked = false
          }
          if (ds[0]['nhanviengt_id']) {
            await API.fn_lay_thongtin_nhanvien(this.axios, {
              nhanvien_id: ds[0]['nhanviengt_id']
            }).then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                this.filterbox.nguoigt.nguoigt_id = ds[0]['nhanviengt_id']
                this.filterbox.nguoigt.value = response.data.data
                this.filterbox.nguoigt.ischecked = true
              }
            })
          } else {
            this.filterbox.nguoigt.nguoigt_id = 0
            this.filterbox.nguoigt.value = ''
            this.filterbox.nguoigt.ischecked = false
          }
          this.VPHIEUCSKH_ID = ds[0]['PHIEUCSKH_ID'] ? ds[0]['PHIEUCSKH_ID'] : 0
          this.txtMaDuAn = ds[0]['ma_duan']
          await API.lay_ds_hopdong_thuebao_theo_hdkh(this.axios, this.hdkh_id)
            .then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                this.HienThiTTHopDongTB(response.data.data)
                //console.log("lay_ds_hopdong_thuebao_theo_hdkh: "+JSON.stringify(response.data.data));
              }
            })
            .catch((error) => {
              console.log(error)
            })
            .finally(() => {})
          if (this.cboDichVuVT.value == DichVuVienThong.DICHVU_CNTT || this.cboDichVuVT.value == DichVuVienThong.ANTOAN_BAOMAT_TT || this.cboDichVuVT.value == DichVuVienThong.TRUNGTAM_DULIEU || this.cboDichVuVT.value == DichVuVienThong.HOINGHI_TRUYENHINH) {
            await API.lay_ds_hdtb_thaydoi_tocdo_cntt(this.axios, {
              hdkh_id: this.hdkh_id
            })
              .then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  this.HienThiDanhSacHDTB(response.data.data)
                  //console.log("lay_ds_hdtb_thaydoi_tocdo_cntt: "+JSON.stringify(response.data.data));
                }
              })
              .catch((error) => {
                console.log(error)
              })
              .finally(() => {
                this.loading(false)
              })
          } else {
            await API.sp_hienthidanhsach_hdtb(this.axios, {
              hdkh_id: this.hdkh_id,
              dichvuvt_id: this.cboDichVuVT.value
            })
              .then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  this.HienThiDanhSacHDTB(response.data.data)
                  //console.log("sp_hienthidanhsach_hdtb: "+JSON.stringify(response.data.data));
                }
              })
              .catch((error) => {
                console.log(error)
              })
              .finally(() => {
                this.loading(false)
              })
          }
          this.SetButton(3)
        }
      } catch (ex) {
        this.lvwThueBao = []
        //console.log("catch HienThiTTHopDongKH " + ex);
      }
    },
    async HienThiTTHopDongTB(ds) {
      this.loading(true)
      //console.log("HienThiTTHopDongTB: " + JSON.stringify(ds));
      if (ds.length > 0) {
        this.dsDBTB = ds
        this.hdtb_id = ds[0]['hdtb_id']
        this.thuebao_id = ds[0]['thuebao_id'] ? ds[0]['thuebao_id'] : 0
        this.txtMaTB = ds[0]['ma_tb']
        this.txtTenTB = ds[0]['ten_tb']
        this.diachi_tb = ds[0]['diachi_tb']
        this.diachi_ld = ds[0]['diachi_ld']
        this.txtMaGD = ds[0]['ma_gd']
        //TuấnNA thêm code lấy địa chỉ lắp đặt trong danh bạ
        //--- Ngày 17/09/2010
        await API.lay_ds_diachi_theo_dbtbid(this.axios, {
          in_thuebao_id: this.thuebao_id
        })
          .then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              let ds_dcld = response.data.data
              this.phuongld_id = ds_dcld[0]['phuongld_id'] ? ds_dcld[0]['phuongld_id'] : 0
              this.phold_id = ds_dcld[0]['phold_id'] ? ds_dcld[0]['phold_id'] : 0
              this.apld_id = ds_dcld[0]['apld_id'] ? ds_dcld[0]['apld_id'] : 0
              this.khuld_id = ds_dcld[0]['khuld_id'] ? ds_dcld[0]['khuld_id'] : 0
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {})
        this.cboDichVuVT.value = ds[0]['dichvuvt_id']
        this.cboLoaihinhTB.value = ds[0]['loaitb_id']
        await this.HT_LoaiHinh_TB_Combobox(ds[0]['dichvuvt_id'], ds[0]['loaitb_id'])
        this.cboDoiTuongMoi.value = ds[0]['doituong_id']
        this.txtGhiChu = ds[0]['ghichu']
        let dt_cu_id = ds[0]['doituong_id']
        if (dt_cu_id != '-1' && dt_cu_id != '') {
          this.cboDoiTuongCu.value = ds[0]['doituong_id']
        }
        this.donvi_id = ds[0]['donvi_id']
        if (ds[0]['trangbi_id'] != null) {
          this.cboTrangBiMoi.value = ds[0]['trangbi_id']
        }
        if (ds[0]['trangbi_id_cu'] != null) {
          this.cboTrangBiCu.value = ds[0]['trangbi_id_cu']
        }
        await this.Lay_DS_MucCuoc_TB_V2()
        setTimeout(() => {
          this.cboMucCuocTB.value = ds[0]['mucuoctb_id'] ? ds[0]['mucuoctb_id'] : 0
          //console.log('1 ' + this.cboMucCuocTB.value)
          this.cboKieuLD.value = ds[0]['kieuld_id']
          if (this.cboKieuLD.value == KieuLapDat.THAYDOI_MUCCUOC_ADSL) {
            this.cboTocDoADSL.enabled = false
          }
        }, 500)
        // Hiếu bổ sung thông tin trường tức thì + sửa lại cboTocDoADSL, cboMucCuoccu 25/08/2010
        this.ds_hdtb_adsl = []
        if (this.cboDichVuVT.value == DichVuVienThong.DICHVU_CNTT || this.cboDichVuVT.value == DichVuVienThong.ANTOAN_BAOMAT_TT || this.cboDichVuVT.value == DichVuVienThong.TRUNGTAM_DULIEU || this.cboDichVuVT.value == DichVuVienThong.HOINGHI_TRUYENHINH) {
          await API.lay_ds_hd_adsl_theo_hdtbcntt(this.axios, {
            in_hdtb_id: this.hdtb_id
          })
            .then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                this.ds_hdtb_adsl = response.data.data // this.ds_hdtb_adsl = new TCHopDongFacade().Lay_ds_hd_adsl_theo_hdtbCNTT(hdtb_id);
                //console.log("lay_ds_hd_adsl_theo_hdtbcntt: "+JSON.stringify(this.ds_hdtb_adsl));
              }
            })
            .catch((error) => {
              console.log(error)
            })
            .finally(() => {})

          this.Init_Properties(this.cboLoaihinhTB.value, 'HDTB_CNTT', '00000')
        } else {
          await API.lay_ds_hdtb_adsl_theo_hdtb_id(this.axios, this.hdtb_id)
            .then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                this.ds_hdtb_adsl = response.data.data //new TCHopDongFacade().LAY_DS_HDTB_ADSL_THEO_HDTB_ID(hdtb_id);
                //console.log("lay_ds_hdtb_adsl_theo_hdtb_id: "+JSON.stringify(this.ds_hdtb_adsl));
              }
            })
            .catch((error) => {
              console.log(error)
            })
            .finally(() => {})
        }
        if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_ADSL || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FTTH || this.cboLoaihinhTB.value == LoaiHinhTB.WIFI_FIBER) {
          this.btnThongTinTC.enabled = true
          this.txtTT_Cuoc.enabled = true
          this.txtTT_Cuoc.value = this.ds_hdtb_adsl[0]['thongtin_tc']
        } else {
          this.btnThongTinTC.enabled = false
          this.txtTT_Cuoc.enabled = false
          this.txtTT_Cuoc.value = ''
        }
        if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
          this.txtCuoc_TBI.enabled = true
          this.txtCuocSD.enabled = true
          if (this.ds_hdtb_adsl[0]['cuoc_tbi'] != null) {
            this.txtCuoc_TBI.value = this.ds_hdtb_adsl[0]['cuoc_tbi']
          } else {
            this.txtCuoc_TBI.value = 0
          }
          if (this.ds_hdtb_adsl[0]['cuoc_sd'] != null) {
            this.txtCuocSD.value = this.ds_hdtb_adsl[0]['cuoc_sd']
          } else {
            this.txtCuocSD.value = 0
          }
        } else {
          this.txtCuoc_TBI.enabled = false
          this.txtCuocSD.enabled = false
          this.txtCuoc_TBI.value = 0
          this.txtCuocSD.value = 0
        }
        try {
          this.cboTocDoADSL.value = this.ds_hdtb_adsl[0]['tocdo_id']
          this.cboMucCuocMoi.value = this.ds_hdtb_adsl[0]['muccuoc_id']
          if (this.cboDichVuVT.value != DichVuVienThong.DICHVU_CNTT && this.cboDichVuVT.value != DichVuVienThong.ANTOAN_BAOMAT_TT && this.cboDichVuVT.value != DichVuVienThong.TRUNGTAM_DULIEU && this.cboDichVuVT.value != DichVuVienThong.HOINGHI_TRUYENHINH) {
            if (this.ds_hdtb_adsl[0]['tocdothuc_id'] != null) {
              this.cboTocDoThuc_Moi.value = this.ds_hdtb_adsl[0]['tocdothuc_id']
            } else {
              this.cboTocDoThuc_Moi.value = this.ds_hdtb_adsl[0]['tocdo_id']
            }
            if (this.ds_hdtb_adsl[0]['kieutra_id'] != null) {
              this.cboKieuTraMoi.value = this.ds_hdtb_adsl[0]['kieutra_id']
            }
            if (this.ds_hdtb_adsl[0]['congnghe_id'] != null) {
              this.cboCongNghe_Moi.value = this.ds_hdtb_adsl[0]['congnghe_id']
            } else {
              this.cboCongNghe_Moi.value = 0
            }
            this.IP = this.ds_hdtb_adsl[0]['ip']
            if (this.ds_hdtb_adsl[0]['bras_id'] != null) {
              this.bras_id = this.ds_hdtb_adsl[0]['bras_id']
            }
            if (this.ds_hdtb_adsl[0]['tucthi'] != null && this.ds_hdtb_adsl[0]['tucthi'] != '0') {
              this.chkTucThi.checked = true
            }
            if (ds[0]['loaitb_id'] == LoaiHinhTB.INTERNET_ADSL || ds[0]['loaitb_id'] == LoaiHinhTB.INTERNET_FTTH || ds[0]['loaitb_id'] == LoaiHinhTB.WIFI_FIBER) {
              let ngay_yc = moment(this.dtpNgayYeuCau).format('DD')
              if (ngay_yc <= this.ngaytucthi) {
                this.chkTucThi.enabled = true
              } else {
                this.chkTucThi.enabled = false
                this.chkTucThi.checked = false
              }
            } else {
              this.chkTucThi.enabled = false
              this.chkTucThi.checked = true
            }
          } else {
            if (this.ds_hdtb_adsl[0]['so_thang'] != '') {
              this.chkThoiHan.checked = true
              this.txtSoThang = this.ds_hdtb_adsl[0]['so_thang']
              if (this.ds_hdtb_adsl[0]['ngay_bd'] != '') {
                this.dtpTuNgay = this.ds_hdtb_adsl[0]['ngay_bd']
              } else {
                this.dtpTuNgay = new Date()
              }
              if (this.ds_hdtb_adsl[0]['ngay_kt'] != '') {
                this.dtpDenNgay = this.ds_hdtb_adsl[0]['ngay_kt']
              } else {
                this.dtpDenNgay = new Date()
              }
            } else {
              this.chkThoiHan.checked = false
              this.txtSoThang = '0'
              this.dtpTuNgay = new Date()
              this.dtpDenNgay = new Date()
            }
          }
        } catch (e) {
          console.log(e)
        }
        await this.HT_DS_KhuyenMai_Combobox()
        //Longdx: bổ sung lấy chi tiết KM
        let dsTempKM = []
        this.dsKM_HDTB = [] // new TCHopDongFacade().LAY_CHITIET_KM_THEO_HDTB_ID(hdtb_id, 0);
        await API.lay_chitiet_km_theo_hdtb_id(this.axios, {
          hdtb_id: this.hdtb_id,
          loai_km: 0
        })
          .then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              dsTempKM = response.data.data
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {})
        if (dsTempKM.length > 0) {
          if (dsTempKM[0]['khuyenmai_id']) {
            this.cboKhuyenMai.value = dsTempKM[0]['khuyenmai_id']
            this.dtiendatcoc_km = 0
            this.dvatdatcoc_km = 0
            for (var i of dsTempKM) {
              var index = this.dsKM_HDTB.findIndex((e) => e['CHITIETKM_ID'] == i['chitietkm_id'])
              if (index > -1) continue
              var item = {}
              item.KHUYENMAI_ID = i['khuyenmai_id']
              item.CHITIETKM_ID = i['chitietkm_id']
              item.KIEU_YC = i['kieu_yc']
              item.SOTHANG_TG = i['sothang_tg']
              item.DATCOC_CSD = i['datcoc_csd']
              item.TIEN_TD = i['tien_td']
              item.TIEN_SD = i['tien_sd']
              item.TIEN_TB = i['tien_tb']
              item.CUOC_SD = i['cuoc_sd']
              item.CUOC_TB = i['cuoc_tb']
              item.TYLE_SD = i['tyle_sd']
              item.TYLE_TB = i['tyle_tb']
              item.TYLE_KMLD = i['tyle_kmld']
              item.TYLE_VAT = i['tyle_vat']
              item.NGAY_BD = i['ngay_bd'] ? moment(i['ngay_bd']).format('DD/MM/YYYY') : null
              item.NGAY_BDDC = i['ngay_bddc'] ? moment(i['ngay_bddc']).format('DD/MM/YYYY') : item.NGAY_BD
              item.THANG_BD = item.NGAY_BD ? moment(item.NGAY_BD, 'DD/MM/YYYY').format('YYYYMM') : null
              item.THANG_BDDC = item.NGAY_BDDC ? moment(item.NGAY_BDDC, 'DD/MM/YYYY').format('YYYYMM') : null
              item.NGAY_KT = i['ngay_kt'] ? moment(i['ngay_kt']).format('DD/MM/YYYY') : null
              item.NGAY_KTDC = i['ngay_ktdc'] ? moment(i['ngay_ktdc']).format('DD/MM/YYYY') : item.NGAY_KT
              item.THANG_KT = item.NGAY_KT ? moment(item.NGAY_KT, 'DD/MM/YYYY').format('YYYYMM') : null
              item.THANG_KTDC = item.NGAY_KTDC ? moment(item.NGAY_KTDC, 'DD/MM/YYYY').format('YYYYMM') : null

              this.dsKM_HDTB.push(item)
              this.dtiendatcoc_km += Math.round((Number(i['datcoc_csd']) * 100) / (100 + Number(i['tyle_vat'])))
              this.dvatdatcoc_km += Math.round((Number(i['datcoc_csd']) * Number(i['tyle_vat'])) / (100 + Number(i['tyle_vat'])))
            }
            //console.log("lay_chitiet_km_theo_hdtb_id0: " + JSON.stringify(this.dsKM_HDTB));
            this.cboKhuyenMai.checked = true
          } else {
            this.cboKhuyenMai.checked = false
            this.dsKM_HDTB = []
          }
        } else {
          this.cboKhuyenMai.checked = false
          this.dsKM_HDTB = []
        }
        let dsTempTT = []
        this.dsDC_HDTB = []
        await API.lay_chitiet_km_theo_hdtb_id(this.axios, {
          hdtb_id: this.hdtb_id,
          loai_km: 1
        })
          .then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              dsTempTT = response.data.data //new TCHopDongFacade().LAY_CHITIET_KM_THEO_HDTB_ID(hdtb_id, 1);
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {})
        if (dsTempTT.length > 0) {
          if (dsTempTT[0]['khuyenmai_id']) {
            this.cboTraTruoc.value = dsTempTT[0]['khuyenmai_id']
            this.dtiendatcoc_tt = 0
            this.dvatdatcoc_tt = 0
            for (var i of dsTempTT) {
              var index = this.dsDC_HDTB.findIndex((e) => e['CHITIETKM_ID'] == i['chitietkm_id'])
              if (index > -1) continue
              var item = {}
              item.KHUYENMAI_ID = i['khuyenmai_id']
              item.CHITIETKM_ID = i['chitietkm_id']
              item.KIEU_YC = i['kieu_yc']
              item.SOTHANG_TG = i['sothang_tg']
              item.DATCOC_CSD = i['datcoc_csd']
              item.TIEN_TD = i['tien_td']
              item.TIEN_SD = i['tien_sd']
              item.TIEN_TB = i['tien_tb']
              item.CUOC_SD = i['cuoc_sd']
              item.CUOC_TB = i['cuoc_tb']
              item.TYLE_SD = i['tyle_sd']
              item.TYLE_TB = i['tyle_tb']
              item.TYLE_KMLD = i['tyle_kmld']
              item.TYLE_VAT = i['tyle_vat']
              item.NGAY_BD = i['ngay_bd'] ? moment(i['ngay_bd']).format('DD/MM/YYYY') : null
              item.NGAY_BDDC = i['ngay_bddc'] ? moment(i['ngay_bddc']).format('DD/MM/YYYY') : item.NGAY_BD
              item.THANG_BD = item.NGAY_BD ? moment(item.NGAY_BD, 'DD/MM/YYYY').format('YYYYMM') : null
              item.THANG_BDDC = item.NGAY_BDDC ? moment(item.NGAY_BDDC, 'DD/MM/YYYY').format('YYYYMM') : null
              item.NGAY_KT = i['ngay_kt'] ? moment(i['ngay_kt']).format('DD/MM/YYYY') : null
              item.NGAY_KTDC = i['ngay_ktdc'] ? moment(i['ngay_ktdc']).format('DD/MM/YYYY') : item.NGAY_KT
              item.THANG_KT = item.NGAY_KT ? moment(item.NGAY_KT, 'DD/MM/YYYY').format('YYYYMM') : null
              item.THANG_KTDC = item.NGAY_KTDC ? moment(item.NGAY_KTDC, 'DD/MM/YYYY').format('YYYYMM') : null

              this.dsDC_HDTB.push(item)
              this.dtiendatcoc_tt += Math.round((Number(i['datcoc_csd']) * 100) / (100 + Number(i['tyle_vat'])))
              this.dvatdatcoc_tt += Math.round((Number(i['datcoc_csd']) * Number(i['tyle_vat'])) / (100 + Number(i['tyle_vat'])))
            }
            console.log('lay_chitiet_km_theo_hdtb_id1: ' + JSON.stringify(this.dsDC_HDTB))
            this.cboTraTruoc.checked = true
          } else {
            this.cboTraTruoc.checked = false
            this.dsDC_HDTB = []
          }
        } else {
          this.cboTraTruoc.checked = false
          this.dsDC_HDTB = []
        }
        // minhtri.hcm: copy tu form Dat moi ds DVGT 17/07/2023
        await this.lay_ds_dvgt_theo_hdtb()
        await this.HienThi_DVGT()
        //Thangph: Copy tu form Đặt mới -Ngày 10/6/2015
        await API.sp_Lay_tienhd_km_theo_hdtb(this.axios, {
          vhdtb_id: this.hdtb_id
        })
          .then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              this.ds_tien_khoanmuc = response.data.data //new TCHopDongFacade().LAY_TIENHD_KM_THEO_HDTB(hdtb_id);
              //console.log("sp_Lay_tienhd_km_theo_hdtb: "+JSON.stringify(this.ds_tien_khoanmuc));
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {})
        for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DOITOCDO) {
            // this.dtienld = this.ds_tien_khoanmuc[i]['tien']
            // this.dvatld = this.ds_tien_khoanmuc[i]['vat']
            this.ds_tien_khoanmuc[i]['tien'] = this.dtienld
            this.ds_tien_khoanmuc[i]['vat'] = this.dvatld
          }
          if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DATCOC) {
            // this.dtiendatcoc_tt = this.ds_tien_khoanmuc[i]['tien']
            // this.dvatdatcoc_tt = this.ds_tien_khoanmuc[i]['vat']
            this.ds_tien_khoanmuc[i]['tien'] = this.dtiendatcoc_tt
            this.ds_tien_khoanmuc[i]['vat'] = this.dvatdatcoc_tt
          }
          if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DATCOC_KM) {
            // this.dtiendatcoc_km = this.ds_tien_khoanmuc[i]['tien']
            // this.dvatdatcoc_km = this.ds_tien_khoanmuc[i]['vat']
            this.ds_tien_khoanmuc[i]['tien'] = this.dtiendatcoc_km
            this.ds_tien_khoanmuc[i]['vat'] = this.dvatdatcoc_km
          }
        }
        await this.LayTongTien_HDTB()
        let ds_cu = []
        if (this.cboDichVuVT.value == DichVuVienThong.DICHVU_CNTT || this.cboDichVuVT.value == DichVuVienThong.ANTOAN_BAOMAT_TT || this.cboDichVuVT.value == DichVuVienThong.TRUNGTAM_DULIEU || this.cboDichVuVT.value == DichVuVienThong.HOINGHI_TRUYENHINH) {
          await API.lay_biendong_theo_hdtb_id_cntt(this.axios, {
            in_hdtb_id: this.hdtb_id
          })
            .then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                ds_cu = response.data.data // new TCHopDongFacade().LAY_BIENDONG_THEO_HDTB_ID_CNTT(hdtb_id);//nhapt them rieng cho dvcntt
                //console.log("lay_biendong_theo_hdtb_id_cntt: "+JSON.stringify(ds_cu));
              }
            })
            .catch((error) => {
              console.log(error)
            })
            .finally(() => {
              this.loading(false)
            })
        } else {
          await API.lay_biendong_thongtin_thuebao(this.axios, this.hdtb_id)
            .then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                ds_cu = response.data.data // new TCHopDongFacade().LAY_BIENDONG_THEO_HDTB_ID(hdtb_id);
                //console.log("lay_biendong_thongtin_thuebao: "+JSON.stringify(ds_cu));
              }
            })
            .catch((error) => {
              console.log(error)
            })
            .finally(() => {
              this.loading(false)
            })
        }
        if (ds_cu.length > 0) {
          this.cboTocDoCu.value = ds_cu[0]['tocdo_id']
          this.cboMucCuocCu.value = ds_cu[0]['muccuoc_id']
          this.cboKieuTraCu.value = ds_cu[0]['kieutra_id']
          if (ds_cu[0]['mucuoctb_id'] != null) {
            await this.HT_MucCuocTB_Combobox(ds_cu[0]['mucuoctb_id'])
          }
          if (this.cboDichVuVT.value == DichVuVienThong.ADSL) {
            if (
              this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON || 
              this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON || 
              this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_XGSPON ||
              this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_XGSPON_GPON
            ) {
              if (ds_cu[0]['congnghe_id'] != null) {
                this.cboCongNghe_Cu.value = ds_cu[0]['congnghe_id']
              } else {
                this.cboCongNghe_Cu.value = 0
              }
            } else this.cboCongNghe_Cu.value = 0
          }
        }
        this.txtSoThangCK = ds[0]['sothang_ck']
      }
      //Cuong moi them
      if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FTTH) {
        if (this.cboTocDoADSL.iptinh == 1) {
          await this.getLoaiIP()
        }
        let items = []
        await API.lay_ds_loaiip_theo_hdtb_id(this.axios, ds[0]['hdtb_id'])
          .then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              items = response.data.data
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {})
        var result = []
        for (var i in items) {
          result.push(items[i].loaiip_id)
        }
        let arr = JSON.stringify(result)
        if (result.length > 0) {
          this.cboLoaiIP.value = this.cboLoaiIP.values[0]
          this.cboLoaiIP.valueitems = this.cboLoaiIP.data.filter((x) => arr.includes(x.loaiip_id)).map((lo) => ({ id: lo.loaiip_id, text: lo.loai_ip }))
          this.cboLoaiIP.values = this.cboLoaiIP.valueitems.map((x) => x.id)
        }
      }
    },
    async Lay_DS_MucCuoc_TB_V2() {
      // Lấy danh sách mức cước mới.
      let vtocdo_id = 0
      let vmuccuoc_id = 0 //2 Lưu lượng
      this.cboMucCuocTB.data = []
      this.cboMucCuocTB.value = 0
      if (
        this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FTTH ||
        this.cboLoaihinhTB.value == LoaiHinhTB.WIFI_FIBER ||
        this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_ADSL ||
        this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC ||
        this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC
      ) {
        vtocdo_id = this.cboTocDoADSL.value
        vmuccuoc_id = this.cboMucCuocMoi.value
      } else if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_MYTV) {
        vtocdo_id = this.cboTocDoADSL.value
        vmuccuoc_id = 0
      } else if (
        this.cboLoaihinhTB.value == LoaiHinhTB.FSECURE ||
        this.cboDichVuVT.value == DichVuVienThong.ANTOAN_BAOMAT_TT ||
        this.cboLoaihinhTB.value == DichVuVienThong.DICHVU_CNTT ||
        this.cboLoaihinhTB.value == DichVuVienThong.HOINGHI_TRUYENHINH ||
        this.cboLoaihinhTB.value == DichVuVienThong.TRUNGTAM_DULIEU
      ) {
        //Lamnt: bổ sung hiển thị mức cước theo tốc độ thuê bao FSecure (09/03/2017)
        vtocdo_id = this.cboTocDoADSL.value
        vmuccuoc_id = 0
      }
      let ds = []
      this.loading(true)
      var ngay_yc = ''
      if (moment(this.dtpNgayYeuCau).isValid()) {
        ngay_yc = moment(this.dtpNgayYeuCau).format('DD/MM/YYYY')
      } else {
        ngay_yc = this.dtpNgayYeuCau
      }
      let pdata = {
        apld_id: this.apld_id ? this.apld_id : 0,
        doituong_id: this.cboDoiTuongMoi.value ? this.cboDoiTuongMoi.value : this.cboDoiTuongMoi.data[0].id,
        khuld_id: this.khuld_id ? this.khuld_id : 0,
        loaitb_id: this.cboLoaihinhTB.value,
        muccuoc_id: this.cboMucCuocMoi.value ? this.cboMucCuocMoi.value : 0,
        ngay: ngay_yc,
        phold_id: this.phold_id ? this.phold_id : 0,
        phuongld_id: this.phuongld_id ? this.phuongld_id : 0,
        tocdo_id: this.cboTocDoADSL.value ? this.cboTocDoADSL.value : 0,
        trangbi_id: this.cboTrangBiMoi.valuem, //default=3
        loainha_id: this.loainha_id
      }
      await API.post_lay_ds_mucuoc_tb_v2(this.axios, pdata)
        .then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            ds = response.data.data //tcdanhba.LAY_DS_MUCUOC_TB_V2()
            this.cboMucCuocTB.data = ds.map((x) => ({
              id: x.muccuoctb_id,
              text: x.tenmuccuoc
            }))
            this.cboMucCuocTB.value = ds[0]['muccuoctb_id']
            if (this.cboMucCuocTBCu.data.length > 0) {
              if (this.hdkh_id == 0) {
                // Nếu chưa ghi lại hợp đồng
                for (let i = 0; i < ds.length; i++) {
                  if (ds[i]['muccuoctb_id'] == this.cboMucCuocTBCu.value) {
                    this.cboMucCuocTB.value = this.cboMucCuocTBCu.value
                  }
                }
              }
            }
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {
          this.loading(false)
        })
      if (ds.length == 0) {
        this.cboMucCuocTB.data = []
        this.cboMucCuocTB.value = 0
      }
    },
    async HienThiTT_DanhBa(ma_tb) {
      let ds_db_km_ld = []
      let ds_db = []
      this.dsDBTB = []
      let ds_temp = []
      this.loading(true)
      let ktthuebaohd = []
      await API.kiemtra_thuebao_lap_hopdong_khac(this.axios, {
        dichVuVtId: this.cboDichVuVT.value,
        loaiHdId: LoaiHopDong.THAY_DOI_TOCDO_ADSL,
        maGd: '0',
        maTb: this.txtMaTB.trim()
      })
        .then((response) => {
          if (response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            ktthuebaohd = response.data.data
            //console.log("kiemtra_thuebao_lap_hopdong_khac: " +JSON.stringify(ktthuebaohd));
            if (Object.keys(ktthuebaohd).length > 0) {
              this.$alert(
                'Số máy/Acc: ' + this.txtMaTB.trim() + ' đang được lập bởi hợp đồng (phụ lục) ' + ktthuebaohd.ten_loaihd + ' có mã giao dịch ' + ktthuebaohd.ma_gd + ' do User ' + ktthuebaohd.nguoi_cn + ' thuộc đơn vị ' + ktthuebaohd.ten_dv + ' thực hiện vào ngày ' + ktthuebaohd.ngay_yc,
                'Thông báo',
                {
                  dangerouslyUseHTMLString: true,
                  confirmButtonText: 'OK',
                  showCloseButton: true,
                  type: 'warning'
                }
              ).then(async (v) => {
                if (!v) {
                  return
                }
                return false
              })
            }
          }
        })
        .catch((error) => {})
        .finally(() => {})
      if (Object.keys(ktthuebaohd).length > 0) {
        this.loading(false)
        return false
      }
      await API.kiemtra_thuebao_lap_hopdong_khac(this.axios, {
        dichVuVtId: this.cboDichVuVT.value,
        loaiHdId: LoaiHopDong.TDTSMYTV,
        maGd: '0',
        maTb: this.txtMaTB.trim()
      })
        .then((response) => {
          if (response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            ktthuebaohd = response.data.data
            //console.log("kiemtra_thuebao_lap_hopdong_khac: " +JSON.stringify(ktthuebaohd));
            if (Object.keys(ktthuebaohd).length > 0) {
              this.$alert(
                'Số máy/Acc: ' + this.txtMaTB.trim() + ' đang được lập bởi hợp đồng (phụ lục) ' + ktthuebaohd.ten_loaihd + ' có mã giao dịch ' + ktthuebaohd.ma_gd + ' do User ' + ktthuebaohd.nguoi_cn + ' thuộc đơn vị ' + ktthuebaohd.ten_dv + ' thực hiện vào ngày ' + ktthuebaohd.ngay_yc,
                'Thông báo',
                {
                  dangerouslyUseHTMLString: true,
                  confirmButtonText: 'OK',
                  showCloseButton: true,
                  type: 'warning'
                }
              ).then(async (v) => {
                if (!v) {
                  return
                }
                return false
              })
            }
          }
        })
        .catch((error) => {})
        .finally(() => {})
      if (Object.keys(ktthuebaohd).length > 0) {
        this.loading(false)
        return false
      }
      await API.sp_thaydoi_tocdo_internet_hienthitt_danhba(this.axios, {
        parameters: {
          DICHVUVT_ID: this.cboDichVuVT.value,
          DONVIDL_ID: 0,
          MA_TB: ma_tb,
          THUEBAO_ID: 0
        }
      })
        .then((response) => {
          if (response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data.returnd_danhba.length > 0) {
            ds_temp = response.data.data.returnd_danhba
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {})
      if (ds_temp.length <= 0) {
        this.$toast.error('Không tìm thấy thông tin về Account ' + this.txtMaTB.trim() + ' đối với dịch vụ ' + this.cboDichVuVT.data.filter((x) => x.id == this.cboDichVuVT.value)[0].text)
        this.loading(false)
        return false
      }
      this.loading(true)
      await API.sp_thaydoi_tocdo_internet_hienthitt_danhba(this.axios, {
        parameters: {
          DICHVUVT_ID: this.cboDichVuVT.value,
          DONVIDL_ID: 0,
          MA_TB: ma_tb,
          THUEBAO_ID: ds_temp[0]['thuebao_id']
        }
      })
        .then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            ds_db_km_ld = response.data.data
            ds_db = ds_db_km_ld.returnd_danhba
            this.dsDBTB = ds_db_km_ld.returnd_danhba
            this.loainha_id = response.data.data.returnd_diachi_ld[0]["loainha_id"]
            this.HT_LoaiHinh_TB_Combobox(this.cboDichVuVT.value, ds_db[0]['loaitb_id'])
            //console.log("HienThiTT_DanhBa: "+response.data.data.returnd_diachi_ld[0]["loainha_id"]);
            this.vido_ld = response.data.data.returnd_diachi_ld[0]["vido_ld"]
            this.kinhdo_ld = response.data.data.returnd_diachi_ld[0]["kinhdo_ld"]
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {})
      if (ds_db.length > 0) {
        if (ds_db.length > 1) {
          //  console.log("dbtruoc: " + JSON.stringify(ds_db));
          ds_db.forEach((item) => {
            Object.keys(item).forEach((key) => (item[key.toUpperCase()] = item[key]))
          })
          this.loading(false)
          let result = await this.$refs.thuebaocungma.show(ds_db)
          ds_db = [result.itemSelected]
          this.loading(true)
          // console.log("dbsau: " + JSON.stringify(ds_db));
        }

        //Hiếu tc 5.10.2010:
        //---> Nếu trạng thái thuê bao # " Hoạt động bình thường" ---> không cho phép làm tiếp
        if (ds_db[0].trangthaitb_id != TrangThaiTB.THUONG) {
          this.kiemtra = false
          this.$alert('Thuê bao có mã: ' + ds_db[0]['ma_tb'] + ' đang ở trạng thái: ' + ds_db[0]['trangthai_tb'] + '\nBạn không thể thực hiện thay đổi tốc độ cho thuê bao này', 'Thông báo', {
            dangerouslyUseHTMLString: true,
            confirmButtonText: 'OK',
            showCloseButton: true,
            type: 'warning'
          }).then(async (v) => {
            if (!v) {
              return
            }
            return false
          })

          return false
        }
        await API.get_DS_QUYEN_CHUQUAN(this.axios).then((response) => {
          if (response.data.data != null) {
            let kq = ''
            API.kiem_tra_quyen_nguoidung_chuquan(this.axios, {
              id: ds_db[0]['thuebao_id'],
              ds_chuquan: response.data.data ? response.data.data : '',
              khachhang_id: ds_db[0]['khachhang_id'],
              kieu: 2
            }).then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                kq = response.data.data
                if (kq != 'OK') {
                  this.$toast.error('Có lỗi: ' + kq)
                }
              }
            })
          }
        })
        let ds_km = ds_db_km_ld.returnd_dstb_huongkm
        if (ds_km.length > 0) {
          if (this.chan_thaydoitd_conkhuyenmai == 1) {
            //Có thể thiết lập tham số để chặn không cho thực hiện nghiệp vụ nếu đang có khuyến mại
            let tb = 'Thuê bao ' + ds_db[0]['ma_tb']
            tb += ' đang hưởng khuyến mại : \n ' + ds_km[0]['ten_km']
            tb += ' \n    Có chi tiết khuyến mại : '
            for (let i = 0; i < ds_km.length; i++) {
              tb += '\n     - ' + ds_km[i]['ten_ctkm']
            }
            tb += '\nBạn không thể thực hiện nghiệp vụ này !'
            this.$confirm(tb, 'Thông báo', {
              dangerouslyUseHTMLString: true,
              confirmButtonText: 'Đồng ý',
              type: 'info'
            })
              .then(async (v) => {
                if (!v) {
                  return
                }
                this.SetButton(1)
                this.kiemtra = false
              })
              .catch(() => {
                this.SetButton(1)
                this.kiemtra = false
              })
          } else {
            let tb = 'Thuê bao ' + ds_db[0]['ma_tb']
            tb += ' đang hưởng khuyến mại : \n ' + ds_km[0]['ten_km']
            tb += ' \n    Có chi tiết khuyến mại : '
            for (let i = 0; i < ds_km.length; i++) {
              tb += '\n     - ' + ds_km[i]['ten_ctkm']
            }
            tb += '\nBạn có muốn tiếp tục thực hiện?'
            this.$alert(tb, 'Thông báo', {
              dangerouslyUseHTMLString: true,
              showCancelButton: true,
              confirmButtonText: 'Đồng ý',
              cancelButtonText: 'Hủy bỏ',
              showCloseButton: true,
              type: 'info'
            })
              .then(async (v) => {
                if (!v) {
                  return
                }
              })
              .catch(() => {
                this.SetButton(1)
                this.kiemtra = false
              })
          }
        }
        this.txtMaKH = ds_db[0]['ma_kh']
        this.txtMaHD = ds_db[0]['ma_hd']
        this.khachhang_id = ds_db[0]['khachhang_id']
        this.txtTenKH = ds_db[0]['ten_kh']
        this.txtDiaChiKH = ds_db[0]['diachi_kh']
        this.plkhachhang_id = ds_db[0]['phanloaikh_id']
        this.txtNgaySN = ds_db[0]['ngay_sn']

        //Thông tin thuê bao
        this.thuebao_id = ds_db[0]['thuebao_id']
        this.txtTenTB = ds_db[0]['ten_tb']
        this.diachi_tb = ds_db[0]['diachi_tb']
        this.diachi_ld = ds_db[0]['diachi_ld']
        //this.cboLoaihinhTB.value = ds_db[0]["loaitb_id"];
        this.cboDoiTuongCu.value = ds_db[0]['doituong_id']
        this.cboDoiTuongMoi.value = ds_db[0]['doituong_id']

        this.donvi_id = ds_db[0]['donviql_id']
        this.txtMaTB = ds_db[0]['ma_tb']
        if (ds_db[0]['mucuoctb_id'] != null) {
          await this.HT_MucCuocTB_Combobox(ds_db[0]['mucuoctb_id'])
        }
        //Hiếu bổ sung lấy thông tin lên chktucthi 25/08/2010
        this.dtpNgayLapHD = ds_db[0]['ngaylap_hd'] ? ds_db[0]['ngaylap_hd'] : new Date()
        //console.log("3485_ngaylaphd: " + ds_db[0]["ngaylap_hd"]);
        if (ds_db[0]['loaitb_id'] == LoaiHinhTB.INTERNET_ADSL || ds_db[0]['loaitb_id'] == LoaiHinhTB.INTERNET_FTTH || ds_db[0]['loaitb_id'] == LoaiHinhTB.WIFI_FIBER) {
          let ngay_yc = moment(this.dtpNgayYeuCau).format('DD')
          if (ngay_yc <= this.ngaytucthi) {
            this.chkTucThi.enabled = true
            if (this.md_tucthi_thaydoi_td == 1) this.chkTucThi.checked = true
          } else {
            this.chkTucThi.enabled = false
            this.chkTucThi.checked = false
          }
        } else {
          this.chkTucThi.enabled = false
          this.chkTucThi.checked = true
        }
        if (ds_db[0]['mucuoctb_id'] != null) {
          this.cboMucCuocTBCu.value = ds_db[0]['mucuoctb_id']
        } else {
          this.cboMucCuocTBCu.data = []
        }
        this.txtSoThangCK = ds_db[0]['sothang_ck']
        // Khối lệnh lấy trong danh bạ con
        //nhapt sửa : bổ sung lấy tốc độ cũ cho cả vnpt his
        let ds_dbcon = []
        ds_dbcon = ds_db_km_ld.returnd_danhba_con
        this.matocdo = ''
        //Toc do cu
        if (ds_dbcon.length > 0) {
          if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_ADSL || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FTTH || this.cboLoaihinhTB.value == LoaiHinhTB.WIFI_FIBER) {
            //this.btnThongTinTC.enabled = true
            //this.txtTT_Cuoc.enabled = true

            // 02/06/2023 LamNT yêu cầu chặn ko cho tự động lấy thông tin cũ
            this.btnThongTinTC.enabled = false
            this.txtTT_Cuoc.enabled = false
            // hoangpkn : 11/06/2018. ko cho tự động lấy thông tin tính cước trong danh bạ nữa.
            // Làm cho tốc độ mới cũng lấy theo thông tin tính cước cũ
            //txtTT_Cuoc.Text = ds.Tables[0].Rows[0]["thongtin_tc"].ToString();
            this.txtTT_Cuoc.value = ''
            // end hoàng
          } else {
            this.btnThongTinTC.enabled = false
            this.txtTT_Cuoc.enabled = false
            this.txtTT_Cuoc.value = ''
          }
          let _congnghe_id = 0
          this.bras_id = ds_dbcon[0]['bras_id']
          this.dslam_id = ds_dbcon[0]['dslam_id']
          this.port_id = ds_dbcon[0]['port_id']
          this.vci_vpi_id = ds_dbcon[0]['vci_vpi_id']
          this.madoicap = ds_dbcon[0]['madoicap']
          this.ma_lt = ds_dbcon[0]['ma_lt']
          if (this.cboCongNghe_Moi.visible) {
            _congnghe_id = this.cboCongNghe_Moi.value
          }
          /* new BangTuDienFacade().THUCTHI_PROCEDURE("LAY_DS_TD_MYTV_V2", DatabaseConstants.DB2, new string[] { "ds_tocdo" }, "vthuebao_id", thuebao_id, "vcongnghe_id", _congnghe_id).Tables["ds_tocdo"];
                    cboTocDoADSL.Properties.DataSource = _dt; */
          await API.lay_ds_td_mytv_v2(this.axios, {
            vthuebao_id: this.thuebao_id,
            vcongnghe_id: _congnghe_id
          })
            .then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                this.cboTocDoADSL.data = response.data.data.map((x) => ({
                  id: x.tocdo_id,
                  text: '[' + x.tocdo_id + '] - ' + x.thuonghieu,
                  iptinh: x.iptinh
                }))
                //console.log("lay_ds_td_mytv_v2: " + JSON.stringify(response.data.data));
              }
            })
            .catch((error) => {
              console.log(error)
            })
            .finally(() => {})
          if (ds_dbcon[0]['tocdo_id'] != null) {
            this.cboTocDoCu.value = ds_dbcon[0]['tocdo_id']
          } else this.cboTocDoCu.value = 0
          //  console.log("TocDoID: "+ds_dbcon[0]["tocdo_id"])
          try {
            if (ds_dbcon[0]['tocdo_id'] != null) {
              if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_MYTV && (this.cboTocDoCu.value == TOCDO_ADSL.MYTV || this.cboTocDoCu.value == TOCDO_ADSL.MYTV_FULLOP || this.cboTocDoCu.value == TOCDO_ADSL.MEGAVNN_EASY || this.cboTocDoCu.value == TOCDO_ADSL.MYTV_HD)) {
                //hieutc: 29.4.2011: Với mytv chỉ cho đổi lên gói mới
                this.cboTocDoADSL.value = this.cboTocDoADSL.data[0].id
                /// cboTocDoADSL.Properties.GetKeyValue(0);
              } else {
                this.matocdo = ds_dbcon[0]['matocdo']
                this.cboTocDoADSL.value = ds_dbcon[0]['tocdo_id']
                this.cboTocDoThuc_Moi.value = ds_dbcon[0]['tocdo_id']
                this.cboTocDoADSL.iptinh = this.cboTocDoADSL.data.filter((x) => x.id == ds_dbcon[0]['tocdo_id'])[0].iptinh
                if (this.cboTocDoADSL.iptinh == 1) {
                  this.getLoaiIP()
                } else {
                  this.cboLoaiIP.data = []
                  this.cboLoaiIP.valueitems = []
                }
              }
            } else {
              this.cboTocDoADSL.value = ''
              this.cboTocDoThuc_Moi.value = ''
            }
          } catch (ex) {
            //console.log("err_HienThiTT_DanhBa: " + ex);
          }
          if (this.cboDichVuVT.value != DichVuVienThong.DICHVU_CNTT && this.cboDichVuVT.value != DichVuVienThong.TRUNGTAM_DULIEU && this.cboDichVuVT.value != DichVuVienThong.ANTOAN_BAOMAT_TT && this.cboDichVuVT.value != DichVuVienThong.HOINGHI_TRUYENHINH) {
            if (ds_dbcon[0]['tocdothuc_id'] != '') this.tocdothuc_cu_id = ds_dbcon[0]['tocdothuc_id']
          }
          if (ds_dbcon[0]['muccuoc_id'] != '') {
            this.cboMucCuocCu.value = ds_dbcon[0]['muccuoc_id']
            this.cboMucCuocMoi.value = ds_dbcon[0]['muccuoc_id']
          } else {
            this.cboMucCuocCu.value = ''
            this.cboMucCuocMoi.value = ''
          }
          if (this.cboDichVuVT.value == DichVuVienThong.ADSL) {
            if (ds_dbcon[0]['congnghe_id'] != '') {
              this.cboCongNghe_Cu.value = ds_dbcon[0]['congnghe_id']
              if (ds_dbcon[0]['congnghe_id'] == '2' || ds_dbcon[0]['congnghe_id'] == '3') this.cboCongNghe_Moi.value = ds_dbcon[0]['congnghe_id']
              else this.cboCongNghe_Moi.value = 0
            } else {
              this.cboCongNghe_Cu.value = 0
              this.cboCongNghe_Moi.value = 0
            }
          }
          let ds_dcld = ds_db_km_ld.returnd_diachi_ld
          if (ds_dcld.length > 0) {
            this.phuongld_id = ds_dcld[0]['phuongld_id'] ? ds_dcld[0]['phuongld_id'] : 0
            this.phold_id = ds_dcld[0]['phold_id'] ? ds_dcld[0]['phold_id'] : 0
            this.apld_id = ds_dcld[0]['apld_id'] ? ds_dcld[0]['apld_id'] : 0
            this.khuld_id = ds_dcld[0]['khuld_id'] ? ds_dcld[0]['khuld_id'] : 0
          }
          if (ds_dbcon[0]['trangbi_id'] != null) {
            this.cboTrangBiMoi.value = this.cboTrangBiCu.value = ds_dbcon[0]['trangbi_id']
          }
          await this.Lay_DS_MucCuoc_TB_V2()
          if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
            this.txtCuoc_TBI.enabled = true
            this.txtCuocSD.enabled = true
            if (ds_dbcon[0]['cuoc_tbi'] != null) this.txtCuoc_TBI.value = ds_dbcon[0]['cuoc_tbi']
            else this.txtCuoc_TBI.value = '0'
            if (ds_dbcon[0]['cuoc_sd'] != null) this.txtCuocSD.value = ds_dbcon[0]['cuoc_sd']
            else this.txtCuocSD.value = '0'
          } else {
            this.txtCuoc_TBI.enabled = false
            this.txtCuocSD.enabled = false
            this.txtCuoc_TBI.value = '0'
            this.txtCuocSD.value = '0'
          }
          if (this.cboDichVuVT.value != DichVuVienThong.DICHVU_CNTT && this.cboDichVuVT.value != DichVuVienThong.ANTOAN_BAOMAT_TT && this.cboDichVuVT.value != DichVuVienThong.TRUNGTAM_DULIEU && this.cboDichVuVT.value != DichVuVienThong.HOINGHI_TRUYENHINH) {
            if (ds_dbcon[0]['kieutra_id'] != null && this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_MYTV) {
              this.cboKieuTraCu.value = ds_dbcon[0]['kieutra_id']
              this.cboKieuTraMoi.value = ds_dbcon[0]['kieutra_id']
              if (ds_dbcon[0]['kieutra_id'] == KieuTra.TRATRUOC) {
                this.cboThangTra.value = ds_dbcon[0]['thang_id']
                this.dtpNgayTra = ds_dbcon[0]['ngaytra']
              }
            } else {
              this.cboKieuTraCu.value = ''
              this.cboKieuTraMoi.value = ''
            }
            if (ds_dbcon[0]['thietbidc_id'] != null) this.cboLoaiTBiADSL.value = ds_dbcon[0]['thietbidc_id']
            else this.cboLoaiTBiADSL.value = ''

            this.IP = ds_dbcon[0]['ip']

            if (ds_dbcon[0]['bras_id'] != null) this.bras_id = ds_dbcon[0]['bras_id']

            if (ds_dbcon[0]['trangbi_id'] != null) this.trangbi_id = ds_dbcon[0]['trangbi_id']
            else this.trangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI
          }
        }
        //frmTraCuuCTDanhBa frmTCCTDB = new frmTraCuuCTDanhBa(dichvuvt_id, txtMaTB.Text);
        //frmTCCTDB.ShowDialog();
        await this.HT_DS_KhuyenMai_Combobox()
        await this.LAY_DS_DVGT_MACDINH()
        await this.LAY_DS_TIEN_KHOANMUC_MACDINH()
        ///cboMucCuocTB.Focus();
        if (this.cboDichVuVT.value == DichVuVienThong.DICHVU_CNTT || this.cboDichVuVT.value == DichVuVienThong.ANTOAN_BAOMAT_TT || this.cboDichVuVT.value == DichVuVienThong.TRUNGTAM_DULIEU || this.cboDichVuVT.value == DichVuVienThong.HOINGHI_TRUYENHINH) {
          /// ds = tcdanhba.LAY_DS_DB_CNTT_THEO_TBID(thuebao_id); ds ko thấy sử dụng
          this.Init_Properties(this.cboLoaihinhTB.value, 'HDTB_CNTT', '00000')
        }
      } else {
        this.$toast.error('Không tìm thấy thông tin về Account ' + this.txtMaTB.trim() + ' đối với dịch vụ ' + this.cboDichVuVT.data.filter((x) => x.id == this.cboDichVuVT.value)[0].text)
        this.SetButton(0)
      }
    },
    HienThiDanhSacHDTB(ds) {
      let tongtienhd = 0
      let tongtientb = 0
      this.lvwThueBao = []
      if (ds.length > 0) {
        var i = 1
        for (var myRow of ds) {
          //var item = [i++];
          var item = { stt: i++ }
          tongtientb = myRow['tongtien'] ? myRow['tongtien'] : 0
          item.tongtien = tongtientb //myRow["tongtien"];
          item.hdtb_id = myRow['hdtb_id']
          item.thuebao_id = myRow['thuebao_id']
          item.ma_tb = myRow['ma_tb']
          item.ten_tb = myRow['ten_tb']
          item.diachi_ld = myRow['diachi_ld']
          item.tdcu = myRow['tdcu']
          item.tdmoi = myRow['tdmoi']
          item.td_cu = myRow['td_cu']
          item.td_moi = myRow['td_moi']
          item.muccuoc_cu = myRow['muccuoc_cu']
          item.muccuoc_moi = myRow['muccuoc_moi']
          item.kieutra_cu = myRow['kieutra_cu']
          item.kieutra_moi = myRow['kieutra_moi']
          tongtienhd = tongtienhd + tongtientb
          this.lvwThueBao.push(item)
        }
        if (this.lvwThueBao.length > 1) {
          this.tsbtnXoaTB.enabled = true
        } else {
          this.tsbtnXoaTB.enabled = false
        }
        if (this.lvwThueBao.length > 0) {
          this.tsbtnXoa.enabled = true
        } else {
          this.tsbtnXoa.enabled = false
        }
      }
      this.txtTongTienHD = tongtienhd
    },
    async KiemTraDL_KhachHang() {
      let kq_bc = ''
      await API.kiemtra_hd_bancheo_tc(this.axios, {
        thuebao_id: this.thuebao_id,
        loaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL
      })
        .then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            kq_bc = response.data.data
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {
          this.loading(false)
        })
      if (kq_bc != 'OK') {
        this.$toast.error('Lỗi: ' + kq_bc)
        return false
      }
      if (this.txtTenKH.trim() == '') {
        this.$toast.error('Hãy nhập tên khách hàng !')
        this.$refs.inputTenKH.focus()
        return false
      }
      if (this.txtMaKH.trim() == '' && this.khachhang_id != 0) {
        this.$toast.error('Kiểm tra lại mã khách hàng !')
        this.$refs.inputMaKH.focus()
        return false
      }
      //Hieutc: 11.10.2011: Kiểm tra mã khách hàng đã theo đúng chuẩn chưa
      if (this.txtMaKH != '') {
        this.loading(true)
        await API.kiemtra_makh_mtt(this.axios, {
          ma_kh: this.txtMaKH,
          kieu: 1,
          loaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL
        })
          .then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              if (response.data.data == 0) {
                this.$toast.error('Mã khách hàng không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
                this.$refs.inputMaKH.focus()
                return false
              }
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {
            this.loading(false)
          })
      }
      if (this.txtMaGD != '') {
        this.loading(true)
        await API.kiemtra_makh_mtt(this.axios, {
          ma_kh: this.txtMaGD,
          kieu: 3,
          loaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL
        })
          .then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              if (response.data.data == 0) {
                this.$toast.error('Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
                this.$refs.inputMaGD.focus()
                return false
              }
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {
            this.loading(false)
          })
      }
      //KieuNV:Zem lại vì ko bắt buộc chọn CTV
      //nhapt mở lại: 27/02/2018
      //console.log("this.batbuoc_ctv: "+this.batbuoc_ctv +" GT: "+this.batbuoc_gioithieu_tocdo)
      if (this.batbuoc_ctv == 1) {
        if (!this.filterbox.ctv.ischecked || this.filterbox.ctv.value == null || this.filterbox.ctv.ctv_id == 0) {
          this.$toast.error('Bạn chưa chọn nhân viên phát triển!')
          //txtCTV.Focus();
          return false
        }
      }
      if (this.batbuoc_gioithieu_tocdo == 1) {
        if (!this.filterbox.nguoigt.ischecked || this.filterbox.nguoigt.value == null || this.filterbox.nguoigt.nguoigt_id == 0) {
          this.$toast.error('Bạn chưa chọn người giới thiệu hợp đồng/phụ lục')
          //txtNguoiGT.Focus();
          return false
        }
      }
      if (this.filterbox.ctv.ischecked && this.filterbox.ctv.value == null && this.filterbox.ctv.ctv_id == 0) {
        this.$toast.error('Bạn chưa chọn nhân viên phát triển!')
        //txtCTV.Focus();
        return false
      }
      if (this.filterbox.nguoigt.ischecked && this.filterbox.nguoigt.value == null && this.filterbox.nguoigt.nguoigt_id == 0) {
        this.$toast.error('Bạn chưa chọn nhân viên giới thiệu hợp đồng!')
        //txtNguoiGT.Focus();
        return false
      }
      return true
    },
    async KiemTraDL_ThueBao() {
      // console.log('tdocid, m_dsThueBao_DichVu', this.cboTocDoADSL.value, this.m_dsThueBao_DichVu)
      // 17/07/2023: minhtri.hcm them dk check green online
      if (Number(this.cboDichVuVT.value) == DichVuVienThong.ADSL || Number(this.cboDichVuVT.value) == DichVuVienThong.BRCD_DOITAC) {
        var check_greennet = 0
        await API.get_fn_ktra_tocdo_dk_greennet(this.axios, { params: { tocdo_id: Number(this.cboTocDoADSL.value) } })
          .then(async (response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              check_greennet = Number(response.data.data)
            } else check_greennet = 0
          })
          .catch(() => {
            check_greennet = 0
          })
        if (check_greennet == 1) {
          if (!this.m_dsThueBao_DichVu || !this.m_dsThueBao_DichVu.find((x) => x.dangky && x.dichvugt_id == 8939)) {
            this.$toast.error('Gói cước có bao gồm dịch vụ GreenNet. Đề nghị khai báo Dịch vụ GTGT GreenNet trước khi khởi tạo gói cước!')
            return false
          }

          var check_kmtt_dvgt = this.ds_tien_khoanmuc.filter((e) => e['khoanmuctt_id'].toString() == KHOANMUC_TT.KMTT_DICHVU.toString())
          if (!check_kmtt_dvgt || check_kmtt_dvgt.length <= 0) {
            // KMTT_DICHVU: 4,//Tiền dịch vụ gia tăng
            this.$toast.error('Không có khoản mục thanh toán 4 - Tiền dịch vụ gia tăng. \nThực hiện tại menu khoản mục thanh toán!')
            return false
          }
        } else {
          if (this.m_dsThueBao_DichVu && this.m_dsThueBao_DichVu.find((x) => x.dangky && x.dichvugt_id == 8939)) {
            this.$toast.error('Tốc độ mới của thuê bao không hỗ trợ đăng ký DVGT GreenNet!')
            return false
          }

        }
      }

      // hoangpkn : 04/06/2012
      // thêm điều kiện  : Không được phép lập hợp đồng thay đổi tốc độ Internet 2 lần trong 1 tháng
      if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_ADSL || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FTTH || this.cboLoaihinhTB.value == LoaiHinhTB.WIFI_FIBER) {
        if (!this.chkTucThi.checked && this.chkTucThi.enabled) {
          /* this.$toast.error(
            "Thuê bao có thể thay đổi tức thì, Bạn có chọn tức thì không ?"
          );
          return false; */
          let xacnhantucthi = 0
          await this.$alert('Thuê bao có thể thay đổi tức thì, Bạn có chọn tức thì không ?', 'Thông báo', {
            dangerouslyUseHTMLString: true,
            showCancelButton: true,
            confirmButtonText: 'Đồng ý',
            cancelButtonText: 'Hủy bỏ',
            showCloseButton: true,
            type: 'info'
          })
            .then(async (v) => {
              if (!v) {
                return
              }
              xacnhantucthi = 0 //nhan nut dong ý
              return
            })
            .catch(() => {
              xacnhantucthi = 1 //nhan nut huy bo
            })
          if (xacnhantucthi == 0) {
            return false
          }
        }
        //kiểm tra đăng ký đặt cọc BSS-87982
        /* if (this.dsDC_HDTB.length > 0) {
          let kqbc = ''
          for (let i = 0; i < this.dsDC_HDTB.length; i++) {
            await API.sp_kt_thoigian_datcoc_v2(this.axios, {
              v_thuebao_id: this.thuebao_id,
              v_thang_bddc: this.dsDC_HDTB[i].THANG_BDDC,
              v_thang_ktdc: this.dsDC_HDTB[i].THANG_KTDC,
              v_ngay_bddc: this.dsDC_HDTB[i].NGAY_BDDC,
              v_ngay_ktdc: this.dsDC_HDTB[i].NGAY_KTDC
            }).then((response) => {
              if (response.data.error_code !== 'BSS-00000000') {
                this.$toast.error(response.data.message)
                kqbc = response.data.data 
              }
            })
          }
          console.log('kq '+kqbc)
          if (kqbc != 'OK') {
            return false
          }
        } */
        if (
          this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON || 
          this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON || 
          this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_XGSPON ||
          this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_XGSPON_GPON
        ) {
          if (this.cboCongNghe_Moi.value == 0) {
            this.$toast.error('Bạn chưa chọn công nghệ mới !')
            return false
          }
          if (this.cboCongNghe_Moi.value == this.cboCongNghe_Cu.value) {
            this.$toast.error('Bạn phải chọn công nghệ mới khác công nghệ cũ !')
            return false
          }
          if ((this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON || this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_AON_GPON) && this.cboCongNghe_Moi.value != 3) {
            this.$toast.error('Bạn phải chọn công nghệ mới là GPON !')
            return false
          }
          if ((this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON || this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_GPON_AON) && this.cboCongNghe_Moi.value != 2) {
            this.$toast.error('Bạn phải chọn công nghệ mới là AON !')
            return false
          }
          if ((this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_XGSPON ) && this.cboCongNghe_Moi.value != 11) {
            this.$toast.error('Bạn phải chọn công nghệ mới là XGSPON !')
            return false
          }
        }
        //Cường LT thêm kiểm tra với IP Tĩnh và FIBER phải chọn loại IP
        if (this.cboTocDoADSL.iptinh == 1 && this.cboLoaiIP.valueitems.length <= 0 && this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FTTH) {
          this.$toast.error('Hãy chọn loại ip đối với ip tĩnh!')
          return false
        }
      }
      if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
        if (this.txtCuocSD == '') {
          this.$toast.error('Bạn chưa nhập cước sử dụng !')
          this.$refs.inputCuocSD.focus()
          return false
        }

        if (this.txtCuoc_TBI == '') {
          this.$toast.error('Bạn chưa nhập cước thiết bị !')
          this.inputCuoc_TBI.focus()
          return false
        }
      }
      if (this.txtMaHD == '' && this.khachhang_id != 0) {
        this.$toast.error('Bạn chưa nhập mã hợp đồng !')
        this.$refs.inputMaHD.focus()
        return false
      }
      if (this.txtTenTB.trim() == '') {
        this.$toast.error('Bạn chưa nhập tên thuê bao !')
        this.$refs.inputTenTB.focus()
        return false
      }
      if (this.cboKieuLD.value == 0) {
        this.$toast.error('Hãy chọn kiểu yêu cầu cho thuê bao này !')
        return false
      }
      if (this.cboKieuLD.value == KieuLapDat.THAYDOI_KIEUTRA_MYTV) {
        if (this.cboKieuTraMoi.value == this.cboKieuTraCu.value) {
          this.$toast.error('Dữ liệu về kiểu trả cũ và kiểu trả mới không được giống nhau !')
          return false
        }
      }
      if (
        this.cboKieuLD.value == KieuLapDat.THAYDOI_TOCDO_ADSL ||
        this.cboKieuLD.value == KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL ||
        this.cboKieuLD.value == KieuLapDat.DOIGOI_CNTT_GTGT ||
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON ||
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON ||
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_XGSPON ||
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_XGSPON_GPON
      ) {
        if (this.cboTocDoADSL.value == 0) {
          this.$toast.error('Hãy nhập tốc độ mới !')
          return false
        }
        console.log('moi: ' + this.cboTocDoADSL.value + ' -cu ' + this.cboTocDoCu.value)
        if (this.cboTocDoADSL.value == this.cboTocDoCu.value) {
          this.$toast.error('Dữ liệu về tốc độ mới và tốc độ cũ không được giống nhau !')
          return false
        }
      }
      if (
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_AON_GPON || 
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_GPON_AON ||
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_AON_XGSPON || 
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_GPON_XGSPON || 
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_XGSPON_AON || 
        this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_XGSPON_GPON
      ) {
        /* if (this.cboTocDoADSL.value == 0) {
          this.$toast.error('Hãy nhập tốc độ mới !')
          return false
        } */
        //chị Huyền yêu cầu gán mức cước TB cũ = mới nếu là thay đổi công nghệ
        this.cboMucCuocTB.value =  this.cboMucCuocTBCu.value;
        //this.cboMucCuocTB.enabled = false;
        
        if (this.cboTocDoADSL.value != this.cboTocDoCu.value) {
          console.log('this.cboTocDoADSL.value: ' + this.cboTocDoADSL.value)
          this.$toast.error('Dữ liệu về tốc độ mới và tốc độ cũ không được khác nhau !')
          return false
        }
      }
      if (this.cboKieuLD.value == KieuLapDat.THAYDOI_TOCDOTHUC_ADSL) {
        if (this.cboTocDoThuc_Moi.value == '' || this.cboTocDoThuc_Moi.value == null || this.cboTocDoThuc_Moi.value == '') {
          this.$toast.error('Hãy chọn tốc độ thực mới!')
          return false
        }

        if (
          this.tocdothuc_cu_id != -1 && //Nếu có tốc độ thực cũ
          this.cboTocDoThuc_Moi.value == this.tocdothuc_cu_id
        ) {
          this.$toast.error('Tốc độ thực mới và tốc độ thực cũ không được giống nhau!')
          return false
        }
      }
      if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FTTH && this.kt_hagoi && !this.quyen_hagoi && (this.cboKieuLD.value == KieuLapDat.THAYDOI_TOCDO_ADSL || this.cboKieuLD.value == KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL)) {
        if (this.cboTocDoCu.value != 0 && this.cboTocDoCu.value != null && this.cboTocDoCu.value != '') {
          //DB đã có kiểm tra hạ gói
          // Nếu cớ tốc đô thực mới kiểm trả
          /* try
                    {

                        var ds = checkdata.GET_DATA_SQL(@" select count(*) from {?DB2}.tocdo_adsl 
                                        where tocdo_id=" + cboTocDoADSL.EditValue.ToString().Trim() + @" and tocdothuc is not null
                                                and tocdothuc <(select tocdothuc from {?DB2}.tocdo_adsl where tocdothuc is not null and tocdo_id=" + cboTocDoCu.value.ToString().Trim() + ")");
                        if (ds == null || ds.Rows.Count == 0)
                        {
                  this.$toast.error("Có lỗi khi kiểm tra tốc độ");
                            return false;
                        }
                        if (ds.Rows[0][0].ToString() == "1")
                        {
                  this.$toast.error("Bạn không có quyền được phép hạ tốc độ gói cước của thuê bao Fiber");
                            return false;
                        }
                    }
                    catch (Exception ex)
                    {
              this.$toast.error("Có lỗi khi kiểm tra tốc độ " + ex);
                        return false;
                    } */
        }
      }
      if (this.cboKieuLD.value == KieuLapDat.THAYDOI_MUCCUOC_ADSL || this.cboKieuLD.value == KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL) {
        if (this.cboMucCuocMoi.value == this.cboMucCuocCu.value) {
          this.$toast.error('Dữ liệu về mức cước mới và mức cước cũ không được giống nhau!')
          return false
        }
      }
      if (this.cboKieuLD.value == KieuLapDat.DOIGOI_FSECURE) {
        if (this.cboTocDoADSL.value == 0) {
          this.$toast.error('Hãy nhập tốc độ mới !')
          return false
        }

        if (this.cboTocDoADSL.value == this.cboTocDoCu.value) {
          this.$toast.error('Dữ liệu về gói cước mới và gói cước cũ không được giống nhau !')
          return false
        }
      }
      if (this.cboMucCuocTB.value <= 0) {
        this.$toast.error('Hãy nhập mức cước thuê bao!')
        return false
      }
      if (this.batbuoc_tratruoc == true) {
        let tt = 0 //Convert.ToInt32(checkdata.MAP_ID("tratruoc", DatabaseConstants.DB2 + ".loaihinh_tb", "where loaitb_id = " + Convert.ToInt32(cboLoaihinhTB.value)));
        API.fn_tt_loaihinh_tb(this.axios, { param: this.cboLoaihinhTB.value, type: 5 })
          .then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              tt = response.data.data
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {})
        if (tt == 1) {
          if (this.cboTraTruoc.checked == false) {
            this.$toast.error('Bạn phải chọn trả trước cho dịch vụ ' + cboLoaihinhTB.Text)
            return false
          }
          if (this.phainhap_thoihan) {
            if (chkThoiHan.checked == false) {
              this.$toast.error('Bạn phải chọn Thời hạn !')
              return false
            }
            if (this.txtSoThang.trim() == '') {
              this.$toast.error('Bạn phải nhập số tháng (Thời hạn) !')
              return false
            }
          }
        }
      }
      //longdx:km không có chi tiết không cho cập nhật
      if (this.cboKhuyenMai.checked && this.dsKM_HDTB.length == 0) {
        this.$toast.error('Bạn chưa chọn chi tiết khuyến mại!')
        return false
      }
      if (this.cboTraTruoc.checked && this.dsDC_HDTB.length == 0) {
        this.$toast.error('Bạn chưa chọn chi tiết trả trước!')
        return false
      }
      if (this.cboTraTruoc.checked) {
        if (this.kiemtra_no_tratruoc == 1) {
          let tienno = this.TraCuu_No_MaTB()
          if (tienno > 0) {
            this.$toast.error('Thuê bao: ' + this.txtMaTB + ' còn nợ: ' + tienno + ' vnđ.\nPhải thanh toán trước khi thực hiện!')
            return false
          }
        }
      }
      // thông tin tính cước: QC Ngô Tấn Đạt yêu cầu bỏ cảnh báo (https://cntt.vnpt.vn/browse/BSS-51966)
      /* if (
        this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_ADSL ||
        this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FTTH ||
        this.cboLoaihinhTB.value == LoaiHinhTB.WIFI_FIBER
      ) {
        if (this.nhap_tttc == 1) {
          if (this.txtTT_Cuoc.value == "") {
            this.$toast.error("Bạn phải nhập thông tin tính cước !");
            this.$refs.inputTTCuoc.focus();
            return false;
          }
        }
      } */
      // end hoang them
      else {
        if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_MYTV) {
          let ngay_epg = ''
          this.loading(true)
          await API.thongtin_nguoidung2(this.axios, {})
            .then((response) => {
              if (response && response.data && response.data.data.ngay_epg != null) {
                ngay_epg = response.data.data.ngay_epg
              } else {
                this.$toast.error('Ngày EPG chưa được thiết lập. Liên hệ Admin để giải quyết !')
                return false
              }
            })
            .catch((error) => {
              console.log(error)
            })
            .finally(() => {})
          await API.getPackageEPG(this.axios, {
            endtime: moment(this.dtpNgayKT).format('DD/MM/YYYY'),
            iptvaccount: this.txtMaTB.trim(),
            starttime: ngay_epg,
            strAll: '0'
          })
            .then((response) => {
              if (response && response.data && response.data.data && response.data.errorCode === 0) {
                if (response.data.data.result != 0) {
                  this.$toast.error('' + response.data.data.errordesc)
                  return false
                }
                let dskt = response.data.data.getData._any[1]
                let convert = require('xml-js')
                let result = convert.xml2json(dskt, {
                  compact: false,
                  spaces: 1
                })
                let rs = JSON.parse(result)
                let rs2 = rs.elements[0].elements[0].elements
                this.dsepg = []
                let rowepg = {}
                if (rs2.length > 0) {
                  for (let i = 0; i < rs2.length; i++) {
                    let acc = rs2[i].elements[0].elements[0]['text']
                    let ngayth = rs2[i].elements[1].elements[0]['text']
                    API.kiemtra_tb_dongbo_epg_vtt(this.axios, {
                      vaccount: acc,
                      vngay_th: ngayth
                    })
                      .then((response) => {
                        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                          let ktra = response.data.data
                          //  console.log("kiemtra_tb_dongbo_epg_vtt: " + ktra);
                          if (ktra == 0) {
                            this.$toast.error('Thuê bao ' + acc + ' chưa đồng bộ EPG. Phải thực hiện đồng bộ EPG trước khi lập phụ lục !')
                            return false
                          }
                        }
                      })
                      .catch((error) => {
                        console.log(error)
                      })
                      .finally(() => {})
                  }
                }
              }
            })
            .catch((error) => {
              // this.$toast.error("Đã xảy ra lỗi: " + JSON.stringify(error));
              console.log(error)
            })
            .finally(() => {
              this.loading(false)
            })

          //Kiểm tra chính phụ
          if (this.cboKieuLD.value == KieuLapDat.THAYDOI_TOCDO_ADSL) {
            let dtKtra = [] // new XuLyThueBaoFacade().Kiemtra_tb_mytv_chinhphu(txtMaTB.Text,1);
            await API.kiemtra_tb_mytv_chinhphu(this.axios, { ma_tb: this.txtMaTB, kieu: 1 })
              .then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  dtKtra = response.data.data
                }
              })
              .catch((error) => {
                console.log(error)
              })
              .finally(() => {})
            if (dtKtra.length > 0) {
              if (this.khong_kiemtra_mytv_chinhphu == 1) {
                //Cho phép làm tiếp nếu đồng ý
                this.$alert('Thuê bao ' + this.txtMaTB + ' đang là thuê bao MyTV chính của thuê bao: ' + dtKtra[0] + '. Bạn có đồng ý tiếp tục?', 'Thông báo', {
                  dangerouslyUseHTMLString: true,
                  showCancelButton: true,
                  confirmButtonText: 'Đồng ý',
                  cancelButtonText: 'Hủy bỏ',
                  showCloseButton: true,
                  type: 'info'
                })
                  .then(async (v) => {
                    if (!v) {
                      return
                    }
                    return false
                  })
                  .catch(() => {
                    this.$alert('Thuê bao ' + this.txtMaTB + ' đang là thuê bao MyTV chính của thuê bao: ' + dtKtra[0] + '. Yêu cầu hủy chính phụ trước khi thực hiện!', 'Thông báo', {
                      dangerouslyUseHTMLString: true,
                      confirmButtonText: 'Đồng ý',
                      showCloseButton: true,
                      type: 'info'
                    }).then(async (v) => {
                      if (!v) {
                        return
                      }
                      return false
                    })
                  })
              }
            }
          }
        }
      }
      // trong DB đã kiểm tra gói đa dịch vụ, FE không cần kiểm tra lại
      //TuanNA: Zem tạm, sau này sửa lại
      // hoangpkn : 31/10/2013. Thêm hàm kiểm tra gói theo yc a Thắng
      /* let kq = 1;
      //kq = laphd.KIEMTRA_THAYDOI_GOI_DADV_1(thuebao_id, loaitb_id, Convert.ToInt32(cboTocDoADSL.EditValue), Convert.ToInt32(cboMucCuocMoi.SelectedValue));   
      if (kq != "1") {
        this.$toast.error("Lỗi kiểm tra gói đa dịch vụ: "+kq);
        return false;
      } */
      // end hoàng thêm

      try {
        if (this.txtSoThangCK != '' && this.txtSoThangCK != '0') {
          if (this.txtSoThangCK < 0 || this.txtSoThangCK > 36) {
            this.$toast.error('Số tháng cam kết phải >=0 và <=36!')
            this.$refs.inputSoThangCK.focus()
            return false
          }
        }
      } catch (e) {
        return false
      }
      if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FTTH || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_MYTV) {
        if (this.cboDoiTuongCu.value == DUNG_THU.DOITUONG_TB || this.cboDoiTuongMoi.value == DUNG_THU.DOITUONG_TB) {
          this.$toast.error('Không thể lập hợp đồng với thuê bao thuộc đối tượng "Thuê bao dùng thử"!')
          return false
        }
      }
      if (this.cboDichVuVT.value == DichVuVienThong.DICHVU_CNTT || this.cboDichVuVT.value == DichVuVienThong.ANTOAN_BAOMAT_TT || this.cboDichVuVT.value == DichVuVienThong.TRUNGTAM_DULIEU || this.cboDichVuVT.value == DichVuVienThong.HOINGHI_TRUYENHINH) {
        // Nếu là thuê bao cần chuẩn hóa --> ko cho thực hiện hợp đồng. cần phải Đặt mới (chon kieu ld = chuan hoa) hoặc làm hợp đồng Tạm dừng/Thanh lý
        /* if (check_chuahoa_cntt)
        {
                    if (laphd2.check_chuahoa_cntt(thuebao_id, LoaiHopDong.THAY_DOI_TOCDO_ADSL))
                    {
                        //Message_Box.ShowWarning("Thuê bao CNTT/GTGT này cần chuẩn hóa dữ liệu.\n Bạn chỉ được thực hiện Tạm dừng, Thanh lý hoặc chuẩn hóa ở chức năng Đặt mới ");
                        if (MessageBox.Show("Thuê bao CNTT/GTGT này cần chuẩn hóa dữ liệu.\n Bạn chỉ được thực hiện Tạm dừng, Thanh lý hoặc chuẩn hóa ở chức năng Đặt mới.\n Bạn muốn chuẩn hóa thông tin ?", "Thông báo", MessageBoxButtons.YesNo, MessageBoxIcon.Information) != DialogResult.Yes) return false;
                        {
                            var frm = new frmDatMoi();
                            frm.MdiParent = this.MdiParent;
                            frm.Show();
                            frm.Load_hopdong_chuanhoa_cntt(txtMaKH.Text.Trim(), dichvuvt_id, loaitb_id, thuebao_id);
                        }
                        return false;
                    }
        } */
      }

      //region Cường thêm tham số động sau này ai thêm kiểm tra gì thì thêm
      /* try
      {
      // CHứ ko mệt lắm suốt ngày hàm kiểm tra      
        let DS_ThamSo_KT ={ vKIEU = this.tsbtnNhapMoi.enabled?"1":"0",
                            vHDDB_ID=tsbtnNhapMoi.Enabled?hdtb_id.ToString():thuebao_id.ToString() ,
                            vTOCDO_MOI_ID =cboTocDoADSL.EditValue ==null
                          ||cboTocDoADSL.EditValue==DBNull.Value?"-1":cboTocDoADSL.EditValue.ToString() ,
                              vTOCDO_CU_ID =cboTocDoCu.SelectedValue==null
                          ||cboTocDoCu.Text==""?"-1":cboTocDoCu.SelectedValue.ToString(),
                                        vLOAITB_ID=loaitb_id.ToString(),
                                        vDICHVUVT_ID=dichvuvt_id.ToString()

        }
                
        let json_list = Newtonsoft.Json.JsonConvert.SerializeObject(ds_ts);
                var kiemtra = checkdata.GET_VALUE_FUNC("{?DB2}.KIEMTRA_HD_THAYDOITD", "vds_json", json_list);
                if (kiemtra == null || kiemtra.ToString() != "1")
                {
                    Message_Box.ShowWarning("[DHSX] " + kiemtra);
                    return false;
                }
      }
      catch ( ex)
      {
        Message_Box.ShowWarning("[DHSX] " + ex);
        return false;
      } */
      return true
    },
    async TraCuu_No_MaTB() {
      let result = 0
      try {
        if (this.thuebao_id != 0) {
          await API.tracuu_no_thuebao(this.axios, this.thuebao_id)
            .then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                result = response.data.data // new TraCuuChungFacade().TRACUUNO_THUEBAO(thuebao_id).ToString();
              }
            })
            .catch((error) => {
              console.log(error)
            })
            .finally(() => {
              this.loading(false)
            })
        } else {
          this.$toast.error('Chưa có thông tin về thuê bao!')
        }
      } catch (ex) {
        this.$toast.error('Có lỗi:' + ex)
      }
      return result
    },
    async txtMaTB_KeyPress() {
      if (this.txtMaTB.trim() != '') {
        this.kiemtra = true
        let ds = []
        this.cboLoaiIP.valueitems = []
        ///this.lvwThueBao = [];
        this.loading(true)
        /* await API.lay_ds_hopdong_theo_ma_tb(this.axios, {
          ma_tb: this.txtMaTB.trim(),
          in_loaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL,
          in_donvi_id: this.$root.token.getDonViID(),
          in_tthd_id: TrangThaiHD.MOI,
          in_nhanvien_id: this.$root.token.getNhanVienID(),
          in_donvi_dl_id: 0,
          in_dichvuvt_id: this.cboDichVuVT.value,
          in_loaitb_id: 0,
        }) */
        await API.sp_thaydoi_tocdo_internet_hienthi(this.axios, {
          ma_tb: this.txtMaTB.trim(),
          dichvuvt_id: this.cboDichVuVT.value
        })
          .then((response) => {
            if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.returnd_hopdong.length > 0) {
              ds = response.data.data.returnd_hopdong
              //console.log("lay_ds_hopdong_theo_ma_tb: " + JSON.stringify(ds));
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {
            this.loading(false)
          })
        //console.log("4440: " + ds.length);
        if (ds.length > 0) {
          this.HienThiTTHopDongKH(ds)
        } else {
          this.HienThiTT_DanhBa(this.txtMaTB.trim())
        }

        //nhapt
        //egion Lay thong tin tren visa xuong
        if (this.kiemtra) {
          let _kieum_mega = 0 //Convert.ToInt32(checkdata.MAP_ID("kieu_mega", DatabaseConstants.DB2 + ".tocdo_adsl", "where tocdo_id = " + Convert.ToInt32(cboTocDoADSL.EditValue)));
          API.fn_tt_tocdo_adsl(this.axios, { param: this.cboTocDoADSL.value, type: 7 })
            .then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                _kieum_mega = response.data.data
              }
            })
            .catch((error) => {
              console.log(error)
            })
            .finally(() => {})
          if ((this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_ADSL || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FTTH) && _kieum_mega == 1) {
            if (this.chkTTTCVisa.checked) {
              this.HienThi_TTTC_Visa()
            }
          }
        }
      }
    },
    HienThi_TTTC_Visa() {
      var accountServiceModel = []
      var attribute = []
      var dtService = []
      var result = []
      this.loading(true)
      API.lookupRASAccountJson(this.axios, { accountName: this.txtMaTB }).then((response) => {
        if (response.data.errorCode === 0 && response.data.data.lookupRASAccountReturn !== undefined && response.data.data.lookupRASAccountReturn.length > 0) {
          accountServiceModel = response.data.data.lookupRASAccountReturn[2]
          attribute = accountServiceModel.attributes
        }
      })
      API.sp_tt_vdc_list_attr(this.axios, { p_param: '', p_type: 1 })
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            dtService = response.data.data
            result = attribute.filter((x1) => dtService.some((x2) => x1.serviceId === x2.key))
            var value = result.map(function (tag) {
              return tag['value']
            })
            this.txtTTCuocVisa = value
            //console.log(value);
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    txtMaGD_KeyPress() {
      if (this.txtMaGD.trim() != '') {
        this.loading(true)
        this.lvwThueBao = []
        if (this.iKieu == 1) {
          API.lay_ds_hopdong_theo_ma_gd(this.axios, {
            in_ma_gd: this.txtMaGD.trim(),
            in_loaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL,
            in_tthd_id: 0,
            in_donvi_dl_id: 0
          })
            .then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                // console.log("lay_ds_hopdong_theo_ma_gd_1: " +JSON.stringify(response.data.data));
                this.HienThiTTHopDongKH(response.data.data)
              } else {
                this.$toast.error(response.data.message)
              }
            })
            .catch((error) => {
              console.log(error)
            })
            .finally(() => {
              this.loading(false)
            })
        } else if (this.iKieu == 2) {
          API.lay_ds_hopdong_theo_ma_gd(this.axios, {
            in_ma_gd: this.txtMaGD.trim(),
            in_loaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL,
            in_tthd_id: 0,
            in_donvi_dl_id: this.$root.token.getDonViID()
          })
            .then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                // console.log("lay_ds_hopdong_theo_ma_gd_2: " +JSON.stringify(response.data.data));
                this.HienThiTTHopDongKH(response.data.data)
              } else {
                this.$toast.error(response.data.message)
              }
            })
            .catch((error) => {
              console.log(error)
            })
            .finally(() => {
              this.loading(false)
            })
        } else {
          API.lay_ds_hopdong_theo_ma_gd(this.axios, {
            in_ma_gd: this.txtMaGD,
            in_loaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL,
            in_tthd_id: TrangThaiHD.MOI,
            in_donvi_dl_id: this.$root.token.getDonViID()
          })
            .then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                //console.log("lay_ds_hopdong_theo_ma_gd_3 "+JSON.stringify(response.data.data))
                this.HienThiTTHopDongKH(response.data.data)
              } else {
                this.$toast.error(response.data.message)
              }
            })
            .catch((error) => {
              console.log(error)
            })
            .finally(() => {
              this.loading(false)
            })
        }
      }
    },
    lvwThueBao_SelectedIndexChanged(data) {
      //console.log("lvwThueBaoChange: " + data.hdtb_id);
       if (Object.keys(data).length > 0) {
         this.hdtb_id = data.hdtb_id
         API.lay_ds_hopdong_tb_theo_hdtb_id(this.axios, {
           in_hdtb_id: this.hdtb_id
         })
           .then((response) => {
             if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
               this.cboLoaiIP.valueitems = []
               this.HienThiTTHopDongTB(response.data.data)
               //console.log("lay_ds_hopdong_tb_theo_hdtb_id: " +JSON.stringify(response.data.data));
             }
           })
           .catch((error) => {
             console.log(error)
           })
           .finally(() => {
             this.loading(false)
           })
       }
    },
    btnLayMaGD_Click() {
      this.$refs.popupSearchContract.showModal()
    },
    //event sau khi đóng form frmTraCuuHopDong
    acceptSearchContract(item) {
      this.txtMaGD = item.ma_gd
      if (this.txtMaGD && this.txtMaGD.trim() != '') {
        this.$refs.inputMaGD.focus()
        this.loading(true)
        API.lay_ds_hopdong_theo_ma_gd(this.axios, {
          in_ma_gd: item.ma_gd,
          in_loaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL,
          in_tthd_id: TrangThaiHD.MOI,
          in_donvi_dl_id: this.$root.token.getDonViID()
        })
          .then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              //console.log("lay_ds_hopdong_theo_ma_gd_popup " +JSON.stringify(response.data.data));
              this.HienThiTTHopDongKH(response.data.data)
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {
            this.loading(false)
          })
      }
    },
    bntLayMaKh_Click() {
      this.popupComponent = SearchAccount
      SearchAccount.data.isPopup = true
      this.Popup('popupComponentMGs')
      this.popupComponentName = 'popupSearchAccount'
    },
    async popupClosed(val) {
      // console.log("closedpopup: " + JSON.stringify(val));
      switch (this.popupComponentName) {
        case 'popupSearchAccount':
          this.txtMaTB = val.ma_tb
          break
        /* case "popupCTV":
          this.filterbox.ctv.value = val.ten_ht;
          this.filterbox.ctv.ctv_id = val.nhanvien_id;
          break;
        case "popupNGT":
          this.filterbox.nguoigt.value = val.ten_ht;
          this.filterbox.nguoigt.nguoigt_id = val.nhanvien_id;
          break; */
        case 'popup-Dangkychitietkhuyenmai':
          if (val != null) {
            await this.LaySothang_SD()
            this.dsKM_HDTB = val.dsKM_HDTB
            this.dtiendatcoc_km = Math.round((val.tien_datcoc * 10) / 11)
            this.dvatdatcoc_km = val.tien_datcoc - this.dtiendatcoc_km
            if (val.dsKM_HDTB.length == 0) {
              this.dtiendatcoc_km = 0
              this.dvatdatcoc_km = 0
            }
            await this.HienThiTien_TraTruoc()
            await this.HT_Tien_ConLai_DatCoc_Cu()
          }
          this.HienThi_DVGT()
          break
        case 'popup-DangkychitietkhuyenmaiTT':
          if (val != null) {
            await this.LaySothang_SD()
            this.dsDC_HDTB = val.dsDC_HDTB
            this.dtiendatcoc_tt = Math.round((val.tien_datcoc_tt * 10) / 11)
            this.dvatdatcoc_tt = val.tien_datcoc_tt - this.dtiendatcoc_tt
            if (val.dsDC_HDTB.length == 0) {
              this.dtiendatcoc_tt = 0
              this.dvatdatcoc_tt = 0
            }
            this.HienThiTien_TraTruoc()
            this.HT_Tien_ConLai_DatCoc_Cu()
          }
          this.HienThi_DVGT()
          break
        case 'popup-ChonKhuyenMai':
          if (val != null) {
            if (val.loaikm == 0) this.cboKhuyenMai.value = val.khuyenmai.khuyenmai_id
            else this.cboTraTruoc.value = val.khuyenmai.khuyenmai_id
          }
          break
        /*  case "popupTienKM":
          this.ds_tien_khoanmuc = val;
          var dtien = 0;
          var dvat = 0;
          var dtongtien = 0;
          for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
            dtien += this.ds_tien_khoanmuc[i]["tien"];
            dvat += this.ds_tien_khoanmuc[i]["vat"];
            dtongtien += this.ds_tien_khoanmuc[i]["tongtien"];
          }
          this.txtTien = dtien;
          this.txtTongVat = dvat;
          this.txtTongTien = dtongtien;
          await this.LayTongTien_HDTB();
          break; */
        case 'popupThongTinTC':
          this.txtTT_Cuoc.value = val
          break
        case 'popupServiceAdds':
          this.m_dsThueBao_DichVu = val && val.length > 0 ? this.LowerCasePropertyList(val) : []
          // this.themmoi_tienkm = true
          this.HienThi_DVGT()
          break
      }
      this.ClosePopup('popupComponentMGs')
      //this.ClosePopup("popupComponents");
    },
    Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
    },
    async acceptTienKM(ds_tien_km) {
      this.ds_tien_khoanmuc = ds_tien_km
      var dtien = 0
      var dvat = 0
      var dtongtien = 0
      for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
        dtien += this.ds_tien_khoanmuc[i]['tien']
        dvat += this.ds_tien_khoanmuc[i]['vat']
        dtongtien += this.ds_tien_khoanmuc[i]['tongtien']
      }

      this.txtTien = dtien
      this.txtTongVat = dvat
      this.txtTongTien = dtongtien
      // console.log("acceptTienKM: " + this.txtTongVat + " - " + this.txtTien);
      await this.LayTongTien_HDTB()
    },
    ShowPopupCTV() {
      if (this.filterbox.ctv.ischecked) {
        this.$refs.chonCongTacVienModal.showModal()
        /* this.popupComponent = () =>
          import(
            "@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien"
          );
        this.popupComponentName = "popupCTV";
        this.Popup("popupComponents"); */
      }
    },
    ShowPopupNguoiGT() {
      if (this.filterbox.nguoigt.ischecked) {
        this.$refs.chonNguoiGTModal.showModal()
        /* this.popupComponent = () =>
          import(
            "@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu"
          );
        this.popupComponentName = "popupNGT";
        this.Popup("popupComponents"); */
      }
    },
    acceptCTV(data) {
      Object.assign(this.filterbox.ctv, data)
      this.filterbox.ctv.value = this.filterbox.ctv.ma_ctv + ' - ' + this.filterbox.ctv.ten_ctv
      this.filterbox.ctv.ctv_id = this.filterbox.ctv.ctv_id
    },
    acceptNguoiGT(data) {
      Object.assign(this.filterbox.nguoigt, data)
      this.filterbox.nguoigt.value = this.filterbox.nguoigt.ma_nguoi_gt + ' - ' + this.filterbox.nguoigt.ten_nguoi_gt
      this.filterbox.nguoigt.nguoigt_id = this.filterbox.nguoigt.nguoi_gt_id
    },
    tsbtnThanhToan_Click() {
	  let user_ipcc = ''
      if (this.$route.query.user_ipcc && this.$route.query.user_ipcc != '') {
        user_ipcc = this.$route.query.user_ipcc
      }

      this.$router.push({ name: 'Payment', params: { magd: this.txtMaGD , user_ipcc: user_ipcc} })
    },
    cboKieuLD_SelectedValueChanged() {
      this.cboKieuLD_ValueChanged()
    },
    async cboKieuLD_ValueChanged() {
      if (this.cboKieuLD.data.length > 0) {
        this.cboTocDoThuc_Moi.enabled = false
        this.cboKieuTraMoi.enabled = true
        this.txtTienTra = 0
        this.EnableTraTruoc(false)
        setTimeout(() => {
          this.cboMucCuocMoi_SelectedValueChanged()
        }, 500)
        if (
          this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON || 
          this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON || 
          this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_XGSPON ||
          this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_GPON_XGSPON ||
          this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_AON_XGSPON ||
          this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_XGSPON_AON ||
          this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_XGSPON_GPON ||
          this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_XGSPON_GPON
        ) {
          await this.HT_Combo_CongNghe()
          this.cboCongNghe_Moi.visible = true
          this.cboCongNghe_Moi.enabled = true

          this.cboCongNghe_Cu.visible = true
          this.cboCongNghe_Cu.enabled = false
          this.cboTocDoADSL.enabled = false
          this.cboMucCuocMoi.enabled = false
          this.cboKieuTraMoi.enabled = false
        } else {
          this.cboCongNghe_Moi.visible = false
          this.cboCongNghe_Cu.visible = false
          if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_MYTV) {
            if (this.cboKieuLD.value == KieuTra.TRATRUOC) this.EnableTraTruoc(true)
            else this.EnableTraTruoc(false)
          } else {
            this.EnableTraTruoc(false)
          }
        }
        if (this.cboKieuLD.value == KieuLapDat.THAYDOI_MUCCUOC_ADSL) {
          this.cboTocDoADSL.enabled = false
          this.cboTocDoADSL.value = this.cboTocDoCu.value
          this.cboTocDoThuc_Moi.value = this.cboTocDoCu.value
        }
        if (this.cboKieuLD.value == KieuLapDat.THAYDOI_KIEUTRA_MYTV) {
          this.cboTocDoADSL.enabled = false
          this.cboCongNghe_Moi.enabled = false
          this.cboTocDoADSL.value = this.cboTocDoADSL.data[0].id
          this.cboTocDoThuc_Moi.value = this.cboTocDoADSL.data[0].id
          this.cboMucCuocMoi.value = this.cboMucCuocCu.value
          this.cboCongNghe_Moi.value = this.cboCongNghe_Cu.value
          if (this.cboKieuTraCu.value == KieuTra.TRASAU) this.cboKieuTraMoi.value = KieuTra.TRATRUOC
          else this.cboKieuTraMoi.value = KieuTra.TRASAU
          if (this.cboKieuTraMoi.value == KieuTra.TRATRUOC) {
            this.EnableTraTruoc(true)
            this.txtTienTra = 0 // obj.LAY_TIEN_TRATRUOC_HDTB(Convert.ToInt32(cboKieuTraMoi.value), Convert.ToInt32(cboThangTra.value), Convert.ToInt32(cboTocDoADSL.EditValue)).ToString();
          } else {
            this.EnableTraTruoc(false)
          }
        } else {
          this.cboKieuTraMoi.value = this.cboKieuTraCu.value
        }
        if (
          this.cboKieuLD.value == KieuLapDat.THAYDOI_TOCDO_ADSL ||
          this.cboKieuLD.value == KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL ||
          this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON ||
          this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON ||
          this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_XGSPON ||
          this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_XGSPON_GPON
        ) {
          this.cboTocDoADSL.enabled = true
          this.cboMucCuocMoi.enabled = false
          this.cboKieuTraMoi.enabled = false
          this.cboMucCuocMoi.value = this.cboMucCuocCu.value
          this.cboCongNghe_Moi.value = this.cboCongNghe_Cu.value
        }
        if (this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON) {
          this.cboCongNghe_Moi.value = 3
          this.cboCongNghe_Moi.enabled = true
        } else if (this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_AON_GPON) {
          this.cboCongNghe_Moi.value = 3
          this.cboCongNghe_Moi.enabled = true
        } else if (
            this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_XGSPON || 
            this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_AON_XGSPON || 
            this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_GPON_XGSPON ||
            this.cboKieuLD.value == KieuLapDat.THAYDOI_CONGNGHE_GPON_XGSPON
          ) {
          this.cboCongNghe_Moi.value = 11
          this.cboCongNghe_Moi.enabled = true
        } else {
          this.cboCongNghe_Moi.value = 2
          this.cboCongNghe_Moi.enabled = true
        }
        // hoangpkn : 05/07/2012
        if (this.cboKieuLD.value == KieuLapDat.THAYDOI_MUCCUOC_ADSL || this.cboKieuLD.value == KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL) {
          if (this.cboMucCuocCu.value == MUCCUOC.LUU_LUONG) this.cboMucCuocMoi.value = MUCCUOC.TRON_GOI
          if (this.cboMucCuocCu.value == MUCCUOC.TRON_GOI) this.cboMucCuocMoi.value = MUCCUOC.LUU_LUONG
        }
        // end hoang them
        if (this.cboKieuLD.value == KieuLapDat.THAYDOI_TOCDOTHUC_ADSL) {
          this.cboTocDoADSL.enabled = false
          this.cboMucCuocMoi.enabled = false
          this.cboKieuTraMoi.enabled = false
          this.cboMucCuocTB.enabled = false
          this.cboCongNghe_Moi.enabled = false

          this.cboTocDoThuc_Moi.enabled = true
          this.cboTocDoThuc_Moi.value = -1

          this.chkTucThi.checked = true
          this.chkTucThi.enabled = false
        }

        if (this.cboKieuLD.value == KieuLapDat.DOIGOI_FSECURE) {
          this.cboMucCuocMoi.enabled = false
          this.cboKieuTraMoi.enabled = false
          this.cboCongNghe_Moi.enabled = false
          this.cboMucCuocMoi.value = this.cboMucCuocCu.value
        }
        //TuấnNA bổ sung hàm lấy tiền lên ô text box --- Ngày 15/09/2010
        this.LayTienTheoKhoanMuc()
        console.log('_congnghe_id: ' + this.cboCongNghe_Moi.value)
        let _congnghe_id = 0
        if (this.cboCongNghe_Moi.visible) {
          _congnghe_id = this.cboCongNghe_Moi.value
        }
        this.loading(true)

        await API.lay_ds_td_mytv_v2(this.axios, {
          vthuebao_id: this.thuebao_id,
          vcongnghe_id: _congnghe_id
        })
          .then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              this.cboTocDoADSL.data = response.data.data.map((x) => ({
                id: x.tocdo_id,
                text: '[' + x.tocdo_id + '] - ' + x.thuonghieu,
                iptinh: x.iptinh
              }))
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {
            this.loading(false)
          })
        /* if (this.cboTocDoCu.value != 0) {
          if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_MYTV && this.cboKieuLD.value != KieuLapDat.THAYDOI_KIEUTRA_MYTV) {
            this.cboTocDoADSL.value = this.cboTocDoADSL.data[0].id
          }
          else this.cboTocDoADSL.value = this.cboTocDoCu.value
        } */
        if (!this.kt_load) {
          this.HT_DS_KhuyenMai_Combobox()
        }
      }
    },
    cboDichVuVT_SelectedValueChanged(agrs) {
      this.HT_LoaiHinh_TB_Combobox(agrs.id, 0)
    },
    async cboLoaihinhTB_SelectedValueChanged(agrs) {
      let loaitb_id = agrs.id

      if (loaitb_id != LoaiHinhTB.INTERNET_FTTH && loaitb_id != LoaiHinhTB.WIFI_FIBER) {
        this.cboLoaiTBiADSL.value = LOAITBI_ADSL.ADSL
      } else this.cboLoaiTBiADSL.value = LOAITBI_ADSL.CPE
      //Hieutc: 29.4.2011: Bổ sung 4 gói mới của Mytv: SilverSD, SilverHD, Gold, HD gold
      //Chỉ thực hiện thay đổi trong gói cước mới, gói cũ -> gói mới
      //string dk = "";
      //dk = "sudung = 1";
      let _congnghe_id = 0
      if (this.cboCongNghe_Moi.visible) _congnghe_id = this.cboCongNghe_Moi.value
      /* DataTable _dt = new BangTuDienFacade().THUCTHI_PROCEDURE("LAY_DS_TD_MYTV_V2", DatabaseConstants.DB2, new string[] { "ds_tocdo" }, "vthuebao_id", thuebao_id, "vcongnghe_id", _congnghe_id).Tables["ds_tocdo"];
                cboTocDoADSL.Properties.DataSource = _dt; */
      this.loading(true)
      await API.lay_ds_td_mytv_v2(this.axios, {
        vthuebao_id: this.thuebao_id,
        vcongnghe_id: _congnghe_id
      })
        .then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.cboTocDoADSL.data = response.data.data.map((x) => ({
              id: x.tocdo_id,
              text: '[' + x.tocdo_id + '] - ' + x.thuonghieu,
              iptinh: x.iptinh
            }))
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {
          this.loading(false)
        })
      this.HT_Tocdo_Adsl_Combobox_ByLoaiTB(loaitb_id)
      let dichvu_vt = this.cboDichVuVT.value
      this.HT_Kieu_LD_BANCHEO_Combobox(loaitb_id)

      this.chkThoiHan.enabled = false
      this.chkThoiHan.checked = false
      if (this.batbuoc_tratruoc == true) {
        let tt = 0 //Convert.ToInt32(checkdata.MAP_ID("tratruoc", DatabaseConstants.DB2 + ".loaihinh_tb", "where loaitb_id = " + loaitb_id ));
        API.fn_tt_loaihinh_tb(this.axios, { param: this.cboLoaihinhTB.value, type: 5 })
          .then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              tt = response.data.data
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {})
        if (tt == 1 && this.phainhap_thoihan) this.chkThoiHan.enabled = true
      }
      //console.log("this.khoa_trangbi_lhtb: "+this.khoa_trangbi_lhtb)
      if (this.khoa_trangbi_lhtb != 0) {
        if (this.cboLoaihinhTB.value == this.khoa_trangbi_lhtb) this.cboTrangBiMoi.enabled = false
        else this.cboTrangBiMoi.enabled = true
      }
      await this.LAY_DS_DVGT_MACDINH()
      await this.LAY_DS_TIEN_KHOANMUC_MACDINH()
    },
    async cboTocDoADSL_SelectedValueChanged(agrs) {
      this.cboMucCuocTB.value = 0
      this.cboTocDoADSL.iptinh = agrs.iptinh
      await this.HT_Tocdo_Thuc_Adsl_Theo_Tocdo_ID(agrs.id)
      await this.LAY_DS_DVGT_MACDINH()
      await this.LAY_DS_TIEN_KHOANMUC_MACDINH()
      this.LayTienTheoKhoanMuc()
      // console.log(this.kt_load)
      if (!this.kt_load) {
        this.HT_DS_KhuyenMai_Combobox()
        this.Lay_DS_MucCuoc_TB_V2()
      }
      if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FTTH) {
        var dt_cuoc_adsl = this.cboTocDoADSL.data
        if (dt_cuoc_adsl != null && dt_cuoc_adsl.length > 0) {
          //console.log("ok" + this.cboTocDoADSL.iptinh+" - "+this.cboLoaiIP.data.length);
          if (this.cboTocDoADSL.iptinh == 1) {
            if (this.cboLoaiIP.data.length < 1) {
              this.getLoaiIP()
            }
          } else {
            this.cboLoaiIP.data = []
            this.cboLoaiIP.valueitems = []
            //console.log("4735: "+JSON.stringify(dt_cuoc_adsl))
          }
        } else {
          this.cboLoaiIP.data = []
        }
        //await this.HT_DoiTuong_Combobox_TheoTocDo(agrs.id); //https://cntt.vnpt.vn/browse/BSS-117339
      } else {
        this.cboLoaiIP.data = []
      }
      this.txtTT_Cuoc.value = '' 
    },
    cboTocDoCu_SelectedValueChanged() {
      let tocdocu_id = this.cboTocDoCu.value
      /* if (checkdata.MAP_ID("tocdothuc", DatabaseConstants.DB2 + ".tocdo_adsl", "where tocdo_id ='" + tocdocu_id + "'") != "")
        tdthuccu = Convert.ToDecimal(checkdata.MAP_ID("tocdothuc", DatabaseConstants.DB2 + ".tocdo_adsl", "where tocdo_id ='" + tocdocu_id + "'")); */
      API.fn_tt_tocdo_adsl(this.axios, { param: this.cboTocDoADSL.value, type: 3 })
        .then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.tdthuccu = response.data.data
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {})
    },
    cboCongNghe_Moi_SelectedValueChanged() {},
    async cboMucCuocMoi_SelectedValueChanged() {
      if (!this.kt_load) {
        await this.HT_DS_KhuyenMai_Combobox()
        await this.Lay_DS_MucCuoc_TB_V2()
      }
    },
    cboTrangBiMoi_SelectedValueChanged() {
      this.Lay_DS_MucCuoc_TB_V2()
    },
    cboLoaiTBiADSL_SelectedValueChanged() {
      this.LayTienTheoKhoanMuc()
    },
    async HT_Tocdo_Adsl_Combobox_ByLoaiTB(loaihinhtb) {
      this.loading(true)
      let apiParams = {
        loaitb_id: loaihinhtb
      }
      await API.lay_tocdo_adsl(this.axios, apiParams)
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            let items = []
            response.data.data.forEach(function (item) {
              items.push({
                id: item.tocdo_id,
                text: '[' + item.tocdo_id + '] - ' + item.thuonghieu,
                iptinh: item.iptinh
              })
            })
            this.cboTocDoADSL.data = items
            //this.cboTocDoADSL.value = items[0].id;
            if (this.cboTocDoADSL.value != 0) {
              this.cboTocDoADSL.iptinh = items.filter((x) => x.id == this.cboTocDoADSL.value)[0].iptinh
            }
            this.cboTocDoCu.data = items
            this.cboTocDoThuc_Moi.data = items
            if (this.cboTocDoADSL.iptinh == 1) {
              this.getLoaiIP()
            } else {
              this.cboLoaiIP.data = []
              this.cboLoaiIP.valueitems = []
            }
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    HT_Kieu_LD_BANCHEO_Combobox(loaihinhtb_id) {
      API.lay_ds_kieu_ld_bc(this.axios, {
        vloaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL,
        vkieu: 1,
        vloaitb_id: loaihinhtb_id
      })
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
            let items = []
            if (response.data.data && response.data.data.length > 0) {
              response.data.data.forEach(function (item) {
                items.push({
                  id: item.kieuld_id,
                  text: item.ten_kieuld
                })
              })
              this.cboKieuLD.data = items
              this.cboKieuLD.value = items[0].id
              //this.cboKieuLD_ValueChanged();
            }
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {})
    },
    async HT_Tocdo_Thuc_Adsl_Theo_Tocdo_ID(tocdoadsl) {
      this.loading(true)
      await API.lay_tocdo_thuc_adsl_theo_tocdo_id(this.axios, { tocdo_id: tocdoadsl })
        .then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.cboTocDoThuc_Moi.data = response.data.data.map((x) => ({
              id: x.tocdo_id,
              text: x.thuonghieu
            }))
            this.cboTocDoThuc_Moi.value = this.cboTocDoADSL.value
          }
        })
        .catch((error) => {
          console.log(error)
          this.loading(false)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    cboKieuTraMoi_SelectedValueChanged() {},
    EnableTraTruoc(kieu) {
      if (kieu) {
        this.cboThangTra.enabled = true
        this.txtTienTra_enabled = true
        this.dtpNgayTra_enabled = true
      } else {
        this.cboThangTra.enabled = false
        this.txtTienTra_enabled = false
        this.dtpNgayTra_enabled = false
      }
    },
    cboThangTra_SelectedValueChanged() {
      this.txtTienTra = 0
    },
    async ThongTinTC() {
      // ThongTinTC()
      try {
        if (!this.cboMucCuocTB.value || this.cboMucCuocTB.value <= 0) {
          this.$toast.error('Chưa có mức cước thuê bao. Bạn hãy kiểm tra lại!')
          return false
        }
        var apidata = {}
        await API.post_fn_lay_thongtin_tc(this.axios, {
          ds_para: JSON.stringify({
            MUCCUOCTB_ID: this.cboMucCuocTB.value,
            DICHVUVT_ID: this.cboDichVuVT.value,
            GOICUOC_ADSL: this.cboTocDoADSL.value
          })
        }).then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            apidata = JSON.parse(response.data.data)
            if (apidata['ERROR_CODE'] != 1) {
              this.$toast.error(apidata['MESSAGE'])
              return
            }
            apidata = apidata['RESULT']
          }
        })
        // CheckDataFacade obj = new CheckDataFacade();
        // string cuoc_tb = obj.MAP_ID("cuoc_tb", DatabaseConstants.DB2 + ".muccuoc_tb", "where mucuoctb_id =" + cboMucCuocTB.SelectedValue);
        // string cuockhoan = obj.MAP_ID("cuockhoan", DatabaseConstants.DB2 + ".muccuoc_tb", "where mucuoctb_id =" + cboMucCuocTB.SelectedValue);
        // string cuoc_tg = obj.MAP_ID("cuoc_tg", DatabaseConstants.DB2 + ".muccuoc_tb", "where mucuoctb_id =" + cboMucCuocTB.SelectedValue);
        // string cuoc_ll = obj.MAP_ID("cuoc_ll", DatabaseConstants.DB2 + ".muccuoc_tb", "where mucuoctb_id =" + cboMucCuocTB.SelectedValue);
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
          muccuoctb_id: this.cboMucCuocTB.value ? this.cboMucCuocTB.value : 0,
          mienip: apidata['MIENIP'] ? apidata['MIENIP'] : null,
          loaitb_id: this.cboLoaihinhTB.value,
          thongtintc: this.txtTT_Cuoc.value
        }

        this.popupComponent = () =>
          //import("@/modules/contract/setup/InstallNewSubs/Popups/popupThongTinTC");
          import('./popupThongTinTC.vue')
        this.Popup('popupComponentMGs')
        this.popupComponentName = 'popupThongTinTC'
        this.popupComponentData = dulieu
      } catch (ex) {
        this.$toast.error('' + ex)
        if (this.debug) console.error(ex)
      }
    },
    btnThongTinTC_Click() {
      this.ThongTinTC()
    },
    cboDoiTuongMoi_SelectedValueChanged() {
      this.LayTienTheoKhoanMuc()
      this.txtTT_Cuoc.value = ''
      if (!this.kt_load) {
        this.HT_DS_KhuyenMai_Combobox()
        this.Lay_DS_MucCuoc_TB_V2()
      }
    },
    btnLayDSTB_Click() {
      if (this.txtMaTB.trim().length > 0) {
        if (this.khachhang_id != 0) {
          this.$refs.tracuuthuebao.showModal(this.khachhang_id, this.cboDichVuVT.value, this.txtMaGD.trim(), 'Đang thay đổi tốc độ')
        } else {
          API.lay_biendong_theo_hdtb_id(this.axios, { hdtb_id: this.hdtb_id })
            .then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                this.$refs.tracuuthuebao.showModal(response.data.data[0].khachhang_id, this.cboDichVuVT.value, this.txtMaGD, 'Đang thay đổi tốc độ')
              } else {
                this.$toast.error('Chưa có thông tin về khách hàng thay đổi tốc độ có thuê bao: ' + this.txtMaTB + ' Kiểm tra lại !')
              }
            })
            .catch((error) => {
              console.log(error)
            })
            .finally(() => {
              this.loading(false)
            })
        }
      } else {
        this.$toast.error('Bạn chưa nhập mã thuê bao!!!')
      }
    },
    frmTraCuuThueBao_chapnhan(value) {
      if (value.ma_tb) {
        this.txtMaTB = value.ma_tb
        this.cboDichVuVT.value = value.dichvuvt_id
        this.$refs.inputMaTB.focus()
        this.txtMaTB_KeyPress()
      }
    },
    tsbtnHen_Click() {
      this.$refs.popupThongTinHenKhachHang.showModal()
    },
    async btnTien_Click() {
      var ds = []
      await API.post_lay_khoanmuc_tt_theo_loaihd(this.axios, {
        vloaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL
      }).then((response) => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          ds = response.data.data
          if (this.ds_tien_khoanmuc && this.ds_tien_khoanmuc.length > 0) {
            for (var item of ds) {
              var items = this.ds_tien_khoanmuc.filter((x) => x.khoanmuctt_id == item.khoanmuctt_id)
              if (items && items.length > 0) {
                item.tien = items[0].tien
                item.vat = items[0].vat
                item.tongtien = item.tien + item.vat
              }
            }
          }
        }
      })
      this.ds_tien_khoanmuc = ds
      setTimeout(() => {
        this.$refs.chiTietTienTheoKhoanMucModal.showModal()
      }, 500)
    },
    ShowPopupChonKhuyenMai(loaikm) {
      // private void btnChon_KhuyenMai_Click(object sender, EventArgs e)
      var dulieu = {}
      dulieu.loai = loaikm
      dulieu.dt = loaikm == Loai_KM.KhuyenMai ? this.cboKhuyenMai.data : this.cboTraTruoc.data
      this.popupComponentData = dulieu
      this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popup-ChonKhuyenMai')
      this.popupComponentName = 'popup-ChonKhuyenMai'
      //this.Popup("popupComponents");
      this.Popup('popupComponentMGs')
    },
    LaySothang_SD() {
      this.txtSoThangCK = 0
      API.post_fn_lay_thang_sd(this.axios, {
        ds_para: JSON.stringify({
          CHK_KHUYENMAI: this.cboKhuyenMai.checked ? 1 : 0,
          CHK_TRATRUOC: this.cboTraTruoc.checked ? 1 : 0,
          CBO_TRATRUOC: this.cboTraTruoc.value,
          CBO_KHUYENMAI: this.cboKhuyenMai.value
        })
      }).then((response) => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          var kq = JSON.parse(response.data.data)
          if (kq && kq['RESULT'] && kq['RESULT']['SOTHANG_SD']) this.txtSoThangCK = kq['RESULT']['SOTHANG_SD']
        }
      })
    },
    async btnChiTietKM_Click() {
      if (this.cboKhuyenMai.checked) {
        if (this.cboKhuyenMai.data.length > 0) {
          let khuyenmai_id = this.cboKhuyenMai.value
          var kt = -1
          var ngayyeucau = ''
          if (moment(this.dtpNgayYeuCau).isValid()) {
            ngayyeucau = moment(this.dtpNgayYeuCau).format('DD/MM/YYYY')
          } else {
            ngayyeucau = this.dtpNgayYeuCau
          }
          await API.get_kiemTraThoiHanKM(this.axios, 'khuyenMaiId=' + khuyenmai_id + '&' + 'ngayYc=' + ngayyeucau).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              kt = response.data.data
            }
          })
          if (kt == 0) {
            var ngay_kt_km = ''
            await API.get_lay_ngaykt_km(this.axios, {
              km_id: khuyenmai_id
            }).then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                ngay_kt_km = response.data.data
              }
            })
            this.$alert('Khuyến mại ' + this.cboKhuyenMai.data.filter((x) => x.id == this.cboKhuyenMai.value)[0].text + ' đã hết thời hạn từ ngày ' + ngay_kt_km + '.\nBạn có muốn tiếp tục chọn chi tiết khuyến mại?', 'Thông báo', {
              dangerouslyUseHTMLString: true,
              showCancelButton: true,
              confirmButtonText: 'Đồng ý',
              cancelButtonText: 'Hủy bỏ',
              showCloseButton: true,
              type: 'warning'
            })
              .then(async (v) => {
                if (!v) {
                  return
                }
              })
              .catch(() => {
                this.dtiendatcoc_tt = 0
                this.dvatdatcoc_tt = 0
                return false
              })
          }
          let itocdo_id = 0
          let itrangbi_id = 0 //Default: KH tự trang bị
          let imuccuoc_id = 0
          let iloaitb_id = 0
          if (this.cboKieuLD.data.length <= 0) return false
          if (this.cboDoiTuongMoi.data.length <= 0) {
            this.$toast.error('Bạn chưa chọn đối tượng thuê bao!')
            return false
          }
          if (this.cboTocDoADSL.data.length > 0) itocdo_id = this.cboTocDoADSL.value
          if (this.cboMucCuocMoi.value != 0 || this.cboMucCuocMoi.value != null) imuccuoc_id = this.cboMucCuocMoi.value
          if (this.cboLoaihinhTB.value != 0 || this.cboLoaihinhTB.value != null) iloaitb_id = this.cboLoaihinhTB.value
          var m_dsThueBao_DichVu = this.m_dsThueBao_DichVu
          var dsDC_HDTB = this.dsDC_HDTB
          this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popup-Dangkychitietkhuyenmai')
          this.popupComponentName = 'popup-Dangkychitietkhuyenmai'
          this.popupComponentData = {
            khuyenmai_id: khuyenmai_id,
            vchuyenmang: 0,
            _hdtb_id: this.hdtb_id,
            vdichvuvt_id: this.cboDichVuVT.value,
            m_dsThueBao_DichVu: m_dsThueBao_DichVu ? this.UpperCasePropertyList(m_dsThueBao_DichVu) : [],
            dsDC_HDTB: dsDC_HDTB ? this.LowerCasePropertyList(dsDC_HDTB) : [],
            vtocdotn_id: itocdo_id,
            vmuccuoctn_id: imuccuoc_id,
            loaihinh_tb: iloaitb_id,
            kieu_km: 0
          } // 0: khuyen mai, 1 tra truoc
          //this.Popup("popupComponents");
          this.Popup('popupComponentMGs')
        }
      }
    },
    async btnChiTietTraTruoc_Click() {
      var khuyenmai_id_tt = this.cboTraTruoc.value
      var kt = -1
      var ngayyeucau = ''
      if (moment(this.dtpNgayYeuCau).isValid()) {
        ngayyeucau = moment(this.dtpNgayYeuCau).format('DD/MM/YYYY')
      } else {
        ngayyeucau = this.dtpNgayYeuCau
      }
      await API.get_kiemTraThoiHanKM(this.axios, 'khuyenMaiId=' + khuyenmai_id_tt + '&' + 'ngayYc=' + ngayyeucau).then((response) => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          kt = response.data.data
        }
      })
      if (kt == 0) {
        var ngay_kt_km = ''
        await API.get_lay_ngaykt_km(this.axios, {
          km_id: khuyenmai_id_tt
        }).then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            ngay_kt_km = response.data.data
          }
        })
        this.$alert('Khuyến mại ' + this.cboTraTruoc.data.filter((x) => x.id == this.cboTraTruoc.value)[0].text + ' đã hết thời hạn từ ngày ' + ngay_kt_km + '.\nBạn có muốn tiếp tục chọn chi tiết khuyến mại?', 'Thông báo', {
          dangerouslyUseHTMLString: true,
          showCancelButton: true,
          confirmButtonText: 'Đồng ý',
          cancelButtonText: 'Hủy bỏ',
          showCloseButton: true,
          type: 'warning'
        })
          .then(async (v) => {
            if (!v) {
              return
            }
          })
          .catch(() => {
            this.dtiendatcoc_tt = 0
            this.dvatdatcoc_tt = 0
            return false
          })
      }
      let itocdo_id = 0
      let itrangbi_id = 0 //Default: KH tự trang bị
      let imuccuoc_id = 0
      let iloaitb_id = 0
      if (this.cboKieuLD.data.length <= 0) return false
      if (this.cboDoiTuongMoi.data.length <= 0) {
        this.$toast.error('Bạn chưa chọn đối tượng thuê bao!')
        return false
      }
      if (this.cboTocDoADSL.data.length > 0) itocdo_id = this.cboTocDoADSL.value
      if (this.cboMucCuocMoi.value != 0 || this.cboMucCuocMoi.value != null) imuccuoc_id = this.cboMucCuocMoi.value
      if (this.cboLoaihinhTB.value != 0 || this.cboLoaihinhTB.value != null) iloaitb_id = this.cboLoaihinhTB.value
      var m_dsThueBao_DichVu = this.m_dsThueBao_DichVu
      var dsDC_HDTB = this.dsDC_HDTB
      this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popup-Dangkychitietkhuyenmai')
      this.popupComponentName = 'popup-DangkychitietkhuyenmaiTT'
      this.popupComponentData = {
        khuyenmai_id: khuyenmai_id_tt,
        vchuyenmang: 0,
        _hdtb_id: this.hdtb_id,
        vdichvuvt_id: this.cboDichVuVT.value,
        m_dsThueBao_DichVu: m_dsThueBao_DichVu ? this.UpperCasePropertyList(m_dsThueBao_DichVu) : [],
        dsDC_HDTB: dsDC_HDTB ? this.LowerCasePropertyList(dsDC_HDTB) : [],
        vtocdotn_id: itocdo_id,
        vmuccuoctn_id: imuccuoc_id,
        loaihinh_tb: iloaitb_id,
        kieu_km: 1
      } // 0: khuyen mai, 1 tra truoc
      //this.Popup("popupComponents")
      this.Popup('popupComponentMGs')
      console.log('mo popup:' + JSON.stringify(this.popupComponentData))
    },
    async HienThiTien_TraTruoc() {
      for (let j = 0; j < this.ds_tien_khoanmuc.length; j++) {
        if (this.ds_tien_khoanmuc[j]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DATCOC_KM) {
          this.ds_tien_khoanmuc[j]['tien'] = this.dtiendatcoc_km
          this.ds_tien_khoanmuc[j]['vat'] = this.dvatdatcoc_km
        }
        if (this.ds_tien_khoanmuc[j]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DATCOC) {
          this.ds_tien_khoanmuc[j]['tien'] = this.dtiendatcoc_tt
          this.ds_tien_khoanmuc[j]['vat'] = this.dvatdatcoc_tt
        }
      }
      // console.log("TienTT: "+JSON.stringify(this.ds_tien_khoanmuc))
      await this.LayTongTien_HDTB()
    },
    tsbtnThongTinLH_Click() {
      if (this.hdkh_id != 0) {
        this.$bvModal.show('ThongTinLienHe')
      }
    },
    tsbtnEmail_Click() {
      if (this.hdkh_id != 0) {
        this.$bvModal.show('ThongTinEmail')
      }
    },
    async HT_Tien_ConLai_DatCoc_Cu() {
      this.dtienthoai_dc = 0
      //Kiểm tra có đặt cọc mới không?
      if (this.thuebao_id != 0) {
        let _tucthi = 1
        if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_ADSL || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FTTH || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
          if (this.chkTucThi.checked == false) {
            _tucthi = 0
          }
        }
        let thang_thoai = moment(new Date()).format('YYYYMM')
        let dt = []
        await API.lay_ds_tien_dc_conlai_doi_td(this.axios, {
          vthuebao_id: this.thuebao_id,
          vthang_thoai: thang_thoai,
          vtucthi: _tucthi
        })
          .then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              dt = response.data.data
              if (dt.length > 0) {
                this.dtienthoai_dc = dt[0]['tien_conlai']
              }
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {})
      }
      this.HT_KM_Tien_Thoai_DC()
    },
    tsbtnDKGoiDaDV_Click() {
      console.log('Chuyen form GoiDDV: ' + this.txtMaGD)
      this.$refs.frmDangKyGoiDaDVLM.showDialog(this.txtMaGD)
    },
    kiemtra_khoanmuc_tien() {},
    tsbtnSuaKM_Click() {},
    phathanh_hddt_dieuchinh_td() {},
    HT_KM_Tien_Thoai_DC() {
      for (let j = 0; j < this.ds_tien_khoanmuc.length; j++) {
        if (this.ds_tien_khoanmuc[j]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_TIENTHOAI_DC_DOI_TD) {
          if ((this.dtiendatcoc_tt * 11) / 10 > this.dtienthoai_dc) {
            this.ds_tien_khoanmuc[j][j]['tien'] = Math.abs(Math.round((this.dtienthoai_dc * 10) / 11))
            this.ds_tien_khoanmuc[j][j]['vat'] = Math.abs(Math.round(this.dtienthoai_dc / 11))
          } else {
            this.ds_tien_khoanmuc[j][j]['tien'] = Math.abs(this.dtiendatcoc_tt)
            this.ds_tien_khoanmuc[j][j]['vat'] = Math.abs(this.dvatdatcoc_tt)
          }
        }
      }
      this.LayTongTien_HDTB()
    },
    HT_TrangBi_TSL_Combobox_DK() {
      this.loading(true)
      API.sp_lay_ds_trangbi_theo_dieukien(this.axios)
        .then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.cboTrangBiMoi.data = this.cboTrangBiCu.data = response.data.data.map((x) => ({
              id: x.trangbi_id,
              text: x.tentrangbi
            }))
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    async HT_MucCuocTB_Combobox(mucuoctb_id) {
      this.cboMucCuocTBCu.data = []
      this.cboMucCuocTBCu.value = 0
      let data = []
      await API.lay_muccuoc_thuebao(this.axios, mucuoctb_id)
        .then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            var rowMucCuoc = {}
            rowMucCuoc.id = response.data.data.mucCuocTbId
            rowMucCuoc.text = response.data.data.tenMucCuoc
            data.push(rowMucCuoc)
            this.cboMucCuocTBCu.data = data
            this.cboMucCuocTBCu.value = this.cboMucCuocTBCu.data[0].id
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    async LoadTuHopDongTuVan() {
      let d = []
      try {
        await API.lay_tt_khachhang_phieu_tuvan(this.axios, this.phdkh_cha_id)
          .then((response) => {
            if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
              d = response.data.data
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {})
        if (d.length > 0) {
          this.hdkh_cha_id = this.phdkh_cha_id
          this.txtTenKH = d[0]['ten_kh']
          this.txtTenTB = d[0]['ten_kh']
          this.txtDiaChiKH = d[0]['diachi_kh']
          if (d[0]['khachhang_id'] == '0') {
            //code cũ đang comment
          } else {
            this.khachhang_id = d[0]['khachhang_id']
            this.txtMaTB = d[0]['ma_tb']
            this.$refs.inputMaTB.focus()
            this.txtMaTB_KeyPress()
            this.filterbox.ctv.ischecked = true
            this.filterbox.ctv.ctv_id = this.pctv_id
            //txtCTV.Text = checkdata.MAP_ID("ten_nv", DatabaseConstants.DB1 + ".nhanvien", "where nhanvien_id = " + ctv_id);
            API.fn_tt_nhanvien(this.axios, { param: this.filterbox.ctv.ctv_id, type: 3 })
              .then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  this.filterbox.ctv.value = response.data.data
                }
              })
              .catch((error) => {
                console.log(error)
              })
              .finally(() => {})
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    async LoadTuHopDongB2A() {
      let VPHIEUCSKH_ID = this.VPHIEUCSKH_ID
      let VPHIEUCSKHDV_ID = this.VPHIEUCSKHDV_ID
      this.IS_B2A = true
      let d = []
      try {
        await API.sp_load_hopdong_b2a(this.axios, VPHIEUCSKHDV_ID)
          .then((response) => {
            if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
              d = JSON.parse(response.data.data).RESULT
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {})
        if (d.length > 0) {
          this.txtTenKH = d[0]['ten_kh']
          this.txtTenTB = d[0]['ten_kh']
          this.txtDiaChiKH = d[0]['diachi_kh']
          if (d[0]['ma_tb'] == '') {
            this.$toast.error('Không tìm thấy mã thuê bao')
            return false
          }
          if (d[0]['khachhang_id'] == '-1') {
            //code cũ đang comment
          } else {
            this.khachhang_id = d[0]['khachhang_id']
            this.txtMaTB = d[0]['ma_tb']
            this.$refs.inputMaTB.focus()
            this.txtMaTB_KeyPress()
            this.filterbox.ctv.ctv_id = d[0]['nhanvien_id'] ? d[0]['nhanvien_id'] : 0
            //txtCTV.Text = checkdata.MAP_ID("ten_nv", DatabaseConstants.DB1 + ".nhanvien", "where nhanvien_id = " + ctv_id);
            API.fn_tt_nhanvien(this.axios, { param: this.filterbox.ctv.ctv_id, type: 3 })
              .then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  this.filterbox.ctv.value = response.data.data
                }
              })
              .catch((error) => {
                console.log(error)
              })
              .finally(() => {})
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    async TuVanMyTV_Mesh_Cam(hdkh_id) {
      var tuvan = await API.kiemtra_tuvan_mytv(this.axios, hdkh_id)
        .then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') return response.data.data == 1
          else return false
        })
        .catch(() => {
          return false
        })
      if (tuvan) {
        await this.$confirm('Khách hàng chưa sử dụng MyTV / WiFi Mesh / Camera, đề nghị Anh/Chị tư vấn mời khách hàng sử dụng dịch vụ MyTV / WiFi Mesh / Camera', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        })
          .then(async (v) => {
            if (!v) {
              return
            }
            this.$refs.dialogTuvanMytv.openDialog({ hdkh_id: 0, khachhang_id: hdkh_id, loai: 1 }) //nhan nut dong ý
            return
          })
          .catch(() => {
            this.SetButton(1) //nhan nut huy bo
          })
      } else {
        this.CapNhat()
      }
    },
    onUpdateTuvanMyTV(data) {
      this.$refs.dialogTuvanMytv.hideDialog()
      if (data == 'OK') {
        this.CapNhat()
      }
      console.log('tuvanmytvdata: ' + data)
    },
    btnDichVuGT_Click() {
      this.popupComponent = () => import('@/modules/contract/setup/ExtenChangeInformationITVATServices/popupServiceAdds')
      this.popupComponentName = 'popupServiceAdds'
      this.popupComponentHeader = 'Đăng ký dịch vụ gia tăng'
      this.popupComponentData = {
        edit_disabled: true,
        _loaitb_id: Number(this.cboLoaihinhTB.value),
        _hdtb_id: this.hdtb_id,
        _linhvuc_id: this.linhvuc_id,
        _tocdo_id: Number(this.cboTocDoADSL.value),
        lvDvgt: this.m_dsThueBao_DichVu ? this.UpperCasePropertyList(this.m_dsThueBao_DichVu) : []
      }
      this.Popup('popupComponentMGs')
    },
    lay_ds_dvgt_theo_hdtb: async function () {
      try {
        let pdata = '{"THUEBAO_ID":' + (this.thuebao_id ? this.thuebao_id : 0) + ',"HDTB_ID":' + this.hdtb_id + ',"LOAITB_ID":' + this.cboLoaihinhTB.value + '}'
        let apidata = {}
        await api_installnewsubs.post_fn_lay_tt_hdtb(this.axios, { ds_para: pdata }).then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            apidata = JSON.parse(response.data.data)
            if (apidata['RESULT']) apidata = apidata['RESULT']
            if (apidata['DS_TB_DICHVU']) {
              this.m_dsThueBao_DichVu = this.LowerCasePropertyList(apidata['DS_TB_DICHVU'])
            }
          }
        })
        // await API.sp_lay_ds_dangky_dvgt_v2(this.axios, { hdtb_id: this.hdtb_id }).then((response) => {
        //   if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
        //     return response.data.data
        //   } else {
        //     return []
        //   }
        // })
      } catch (e) {
        this.$toast.error(JSON.stringify(e))
        return []
      }
    },
    LAY_DS_TIEN_KHOANMUC_MACDINH: async function () {
      try {
        var ds = []
        await API.post_lay_khoanmuc_tt_theo_loaihd(this.axios, {
          vloaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL
        }).then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            ds = response.data.data
            if (this.ds_tien_khoanmuc && this.ds_tien_khoanmuc.length > 0) {
              for (var item of ds) {
                var items = this.ds_tien_khoanmuc.filter((x) => x.khoanmuctt_id == item.khoanmuctt_id)
                if (items && items.length > 0) {
                  item.tien = items[0].tien
                  item.vat = items[0].vat
                  item.tongtien = item.tien + item.vat
                }
              }
            }
          }
        })
        this.ds_tien_khoanmuc = ds
      } catch (e) {
        console.log(e)
      }
    },
    LAY_DS_DVGT_MACDINH: async function () {
      var input = {
        ds_para: JSON.stringify({ LOAITB_ID: Number(this.cboLoaihinhTB.value), HDTB_ID: this.hdtb_id, LINHVUC_ID: this.linhvuc_id, I_TOCDO_ID: Number(this.cboTocDoADSL.value) })
      }
      var ds_dvgt_macdinh = await api_installnewsubs.post_fn_lay_ds_dvgt_macdinh(this.axios, input).then(async (response) => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          let apidata = JSON.parse(response.data.data)
          if (apidata['ERROR_CODE'] == 1) {
            var ds_dvgt_macdinh = apidata['RESULT']['DS_TB_DICHVU'] && apidata['RESULT']['DS_TB_DICHVU'].length > 0 ? this.LowerCasePropertyList(apidata['RESULT']['DS_TB_DICHVU']) : []
            if (ds_dvgt_macdinh && ds_dvgt_macdinh.length > 0) {
              // Bổ sung tự động chọn DV GTGT 8939 khi chọn tốc độ green net
              // if (this.cboTocDoADSL.value == 44715 || this.cboTocDoADSL.value == 44714 || this.cboTocDoADSL.value == 44713 || this.cboTocDoADSL.value == 44712 || this.cboTocDoADSL.value == 44711 || this.cboTocDoADSL.value == 44710 ) {
              //   for (var i = 0; i < ds_dvgt_macdinh.length; i++) {
              //     if (Number(ds_dvgt_macdinh[i]['dichvugt_id']) == 8939 && ds_dvgt_macdinh[i]['kieu_yc'] && Number(ds_dvgt_macdinh[i]['kieu_yc']) == 1) {
              //       ds_dvgt_macdinh[i]['dangky'] = 1
              //     }
              //   }
              // }
              if (Number(this.cboDichVuVT.value) == DichVuVienThong.ADSL || Number(this.cboDichVuVT.value) == DichVuVienThong.BRCD_DOITAC) {
                var check_greennet = 0
                await API.get_fn_ktra_tocdo_dk_greennet(this.axios, { params: { tocdo_id: Number(this.cboTocDoADSL.value) } })
                  .then(async (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                      check_greennet = Number(response.data.data)
                    } else check_greennet = 0
                  })
                  .catch(() => {
                    check_greennet = 0
                  })
                if (check_greennet == 1) {
                  for (var i = 0; i < ds_dvgt_macdinh.length; i++) {
                    if (Number(ds_dvgt_macdinh[i]['dichvugt_id']) == 8939 && ds_dvgt_macdinh[i]['kieu_yc'] && Number(ds_dvgt_macdinh[i]['kieu_yc']) == 1) {
                      ds_dvgt_macdinh[i]['dangky'] = 1
                    }
                  }
                }else{
                  // tự động hủy greennet, nếu tốc độ không có greennet
                  await API.changeGreenNet(this.axios, {accountName: this.txtMaTB.trim(), status: "0"}).then(async (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                      console.log("Tự động hủy greennet, do tốc độ không có greennet")
                    }
                  });
                }
              }
              this.m_dsThueBao_DichVu = ds_dvgt_macdinh
              this.HienThi_DVGT()
            }
          } else {
            this.$toast.error(apidata['MESSAGE'])
          }
        }
      })
    },
    HienThi_DVGT() {
      try {
        // if (Number(this.cboKieuLD.value) == 13187 || Number(this.cboKieuLD.value) == 13188 || Number(this.cboKieuLD.value) == 13229 || Number(this.cboKieuLD.value) == 13238)
        // không sử dụng với loại yêu cầu hỗ trợ vì không có tiền
        // return
        this.lbDichVuGT.list = []
        var ds_dk_dvgt = this.m_dsThueBao_DichVu && this.m_dsThueBao_DichVu.length > 0 ? this.LowerCasePropertyList(this.m_dsThueBao_DichVu) : []
        if (ds_dk_dvgt && ds_dk_dvgt.length > 0) {
          ds_dk_dvgt = ds_dk_dvgt.filter((e) => (e['dangky'] && e['dangky'].toString() == '1') || (e['sua'] && e['sua'].toString() == '1') || (e['huy'] && e['huy'].toString() == '1') || (e['sudung'] && e['sudung'].toString() == 'Su dung'))
          if (ds_dk_dvgt.length > 0) {
            for (var i = 0; i < ds_dk_dvgt.length; i++) {
              var r = ds_dk_dvgt[i]
              if (!r['ten_dvgt']) {
                this.ShowError('TEN_DVGT is null')
                return
              }
              var ten_dvgt = r['dichvugt_id'] + ' - ' + r['ten_dvgt'].toString() + ' - ' + (r['noidung'] ? r['noidung'].toString() : '')

              if (r['dangky'] && r['dangky'].toString() == '1') {
                r['TEN_DVGT'] = ten_dvgt + ' (' + 'ĐĂNG KÝ' + ')'
              }
              if (r['sua'] && r['sua'].toString() == '1') {
                r['TEN_DVGT'] = ten_dvgt + ' (' + 'THAY ĐỔI' + ')'
              }
              if (r['huy'] && r['huy'].toString() == '1') {
                r['TEN_DVGT'] = ten_dvgt + ' (' + 'HỦY' + ')'
              }
              if (Number(r['dangky']).toString() != '1' && Number(r['sua']).toString() != '1' && Number(r['huy']).toString() != '1' && r['sudung'] && r['sudung'].toString() == 'Su dung') {
                r['TEN_DVGT'] = ten_dvgt + ' (' + 'SỬ DỤNG' + ')'
              }
              ds_dk_dvgt[i] = r
            }
          }
          this.lbDichVuGT.list = ds_dk_dvgt

          this.tien_dv = 0
          this.vat_dv = 0

          for (var i = 0; i < ds_dk_dvgt.length; i++) {
            if (ds_dk_dvgt[i]['huy'].toString() == '0' && ds_dk_dvgt[i]['dangky'].toString() == '0' && ds_dk_dvgt[i]['sua'].toString() == '0') {
              ds_dk_dvgt[i]['kieu_yc'] = 0
            }
            if (ds_dk_dvgt[i]['huy'].toString() == '1' || ds_dk_dvgt[i]['dangky'].toString() == '1' || ds_dk_dvgt[i]['sua'].toString() == '1') {
              if (ds_dk_dvgt[i]['dangky'].toString() == '1' && ds_dk_dvgt[i]['huy'].toString() == '0') {
                if (ds_dk_dvgt[i]['cuoc_ld'].toString() != '') {
                  this.tien_dv = this.tien_dv + Number(ds_dk_dvgt[i]['cuoc_ld'].toString())
                }
                if (ds_dk_dvgt[i]['vat_ld'].toString() != '') {
                  this.vat_dv = this.vat_dv + Number(ds_dk_dvgt[i]['vat_ld'].toString())
                }
              }
            }
          }
          if (this.ds_tien_khoanmuc.length > 0) {
            for (var j = 0; j < this.ds_tien_khoanmuc.length; j++) {
              if (this.ds_tien_khoanmuc[j]['khoanmuctt_id'].toString() == KHOANMUC_TT.KMTT_DICHVU.toString()) {
                this.ds_tien_khoanmuc[j]['tien'] = this.tien_dv
                this.ds_tien_khoanmuc[j]['vat'] = this.vat_dv
              }
            }
          }
          this.LayTongTien_HDTB()
        }
      } catch (e) {
        console.log('HienThi_DVGT')
        console.log(e)
      }
    }
  }
}
</script>
<style>
.lable {
  font-weight: bold;
  color: red;
}
</style>