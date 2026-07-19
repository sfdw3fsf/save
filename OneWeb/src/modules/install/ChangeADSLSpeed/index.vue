<template src="./ChangeADSLSpeed.html"></template>
<style type="text/css">
a:hover {
  cursor: pointer;
}
</style>
<style scoped src="./ChangeADSLSpeed.scss"></style>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import api from './api'
import breadcrumb from '@/components/breadcrumb'
import '@/assets/vendor/jquery/split'
import Vue from 'vue'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import TocDoMucCuoc from './popups/TocDoMucCuoc/TocDoMucCuoc.vue'
import ChonCongTacVienModal from './popups/ChonCongTacVien/ChonCongTacVienModal.vue'
import ChonNguoiGTModal from './popups/ChonNguoiGT/ChonNguoiGTModal.vue'
import SearchAccountModal from '@/modules/search/subscriber/SearchAccount/SearchAccount.vue'
import SearchContractModal from '@/modules/search/subscriber/SearchContract/SearchContract.vue'
import PopupThongTinTC from '@/modules/contract/setup/InstallNewSubs/Popups/popupThongTinTC.vue'
import thongTinEmail from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue'
import thongTinLienHe from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue'
import chiTietKhuyenMai from '@/modules/contract/setup/InstallNewSubs/Popups/popup-Dangkychitietkhuyenmai.vue'
import thongTinHenKhachHangModal from '@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import thanhToan from '@/modules/contract/setup/Payment/Payment.vue'
import InHoSo from '@/modules/print/InHopDong/InHopDong'
import { LoaiHopDong, DichVuVienThong, TrangThaiHD, TRANGTHAI_DONGBO, LOAI_DV, TrangThaiTB, LoaiHinhTB } from '@/const/enums'

Vue.use(DialogPlugin)
export default {
  name: 'ChangeIPAccessModal',
  components: {
    breadcrumb,
    TocDoMucCuoc,
    LoaiHopDong,
    DichVuVienThong,
    TrangThaiHD,
    LOAI_DV,
    TrangThaiTB,
    LoaiHinhTB,
    ChonCongTacVienModal,
    ChonNguoiGTModal,
    SearchAccountModal,
    SearchContractModal,
    PopupThongTinTC,
    thongTinEmail,
    thongTinLienHe,
    thongTinHenKhachHangModal,
    thanhToan,
    InHoSo,
    chiTietKhuyenMai,
    TRANGTHAI_DONGBO
  },
  data: function () {
    return {
      header: {
        title: 'Thay đổi tốc độ ADSL',
        list: [
          { name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } }
        ]
      },
      dulieu: {
        cuoc_tb: 0,
        cuoc_tg: 0,
        cuockhoan: 0,
        cuoc_ll: 0,
        muccuoctb_id: 0,
        mienip: 0,
        loaitb_id: 0,
        thongtintc: ''
      },
      dulieuKM: {
        khuyenmai_id: 0,
        dsKM_HDTB: [],
        dsDC_HDTB: [],
        vtocdotn_id: 0,
        vmuccuoctn_id: 0,
        loaihinh_tb: 0,
        kieu_km: 0
      },
      Loai_KM: {
        KhuyenMai: 0,
        TraTruoc: 1
      },
      titlePopup: {},
      position: { X: 'center', Y: 'top' },
      targetTop: '#app .main-wrapper',
      TRANGBI: {
        KHACHHANG_TU_TRANGBI: 3
      },
      KHOANMUC_TT: {
        KMTT_DOITOCDO: 3,
        KMTT_DATCOC: 11,
        KMTT_DATCOC_KM: 22,
        KMTT_TIENTHOAI_DC_DOI_TD: 35,
        KMTT_KM_DATCOC: 21,
        KMTT_TRAGOP_THIETBI: 24,
        KMTT_DATMOI: 1
      },
      KieuLapDat: {
        THAYDOI_CONGNGHE_TOCDO_AON_GPON: 885,
        THAYDOI_MUCCUOC_ADSL_TK: 13081,
        THAYDOI_KIEUTRA_MYTV: 270,
        THAYDOI_TOCDO_ADSL_TK: 13080,
        THAYDOI_TOCDO_MUCCUOC_ADSL_TK: 13082,
        THAYDOI_CONGNGHE_TOCDO_GPON_AON: 886,
        THAYDOI_TOCDOTHUC_ADSL: 655,
        DOIGOI_FSECURE: 668,
        THAYDOI_TOCDO_ADSL: 24
      },
      TOCDO_ADSL: {
        MEGAVNN_EASY: 1,
        MEGAVNN_FAMILY: 2,
        MYTV: 19,
        MYTV_HD: 20,
        MYTV_FULLOP: 21,
        MYTV_FULLOP_HD: 22
      },
      KieuTra: {
        TRATRUOC: 1,
        TRASAU: 2
      },
      LOAITBI_ADSL: {
        ADSL: 1, //Modem ADSL
        CPE: 3 //CPE
      },
      MUCCUOC: {
        TRON_GOI: 1,
        LUU_LUONG: 2
      },
      pnlMucCuoc: {
        Visible: true
      },
      pnlKieuTra: {
        Visible: false
      },
      pnlCongNghe: {
        Visible: false
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close': this.popupClosed,
        acceptChangeCTV: this.popupClosed,
        acceptChangeNGT: this.popupClosed,
        PopUpCongTacVien_Thoat: this.popupClosed,
        paymentSuccess: this.popupClosed,
        acceptThongTinTC: this.popupClosed
      },
      popupThanhToan: null,
      // popupComponentEvtsThanhToan:{
      //   'paymentSuccess':this.paymentSuccess(),
      // },
      popupComponentName: '',
      result_loadform: [],
      ds_tien_khoanmuc: [],
      ItemList: [],
      thong_So: [],
      dichvuvt_id: 0,
      txtMaHD: '',
      dtpNgayYeuCau: '',
      dtpNgayLapHD: '',
      isDisdtpNgayYeuCau: true,
      isDisdtpNgayLapHD: true,
      txtNgaySN: '',
      txtTongTienHD: '',
      doituong_id: 1,
      txtNguoiGT: '',
      txtCTV: '',
      txtTenTB: '',
      txtMaGD: '',
      txtMaTB: '',
      txtMaKH: '',
      txtTenKH: '',
      txtDiaChiKH: '',
      txtGhiChu: '',
      lblTongTien: 0,
      txtTongTien: 0,
      txtTongVat: 0,
      hdkh_id: 0,
      hdkh_cha_id: 0,
      check: true,
      loai_hd_id: null,
      hdtb_id: null,
      khachhang_id: 0,
      thuebao_id: null,
      thuebao_id_tc: null,
      khachhang_id_tc: null,
      loaitb_id: null,
      diachi_tb: '',
      diachi_ld: '',
      donvi_id: null,
      txtTTCuocVisa: '',
      trangbi_id: 3, //khách hàng tự trang bị
      KieuLD_click: false,
      ngay_cn: '',
      tinh_tb_id: 0,
      // hoang pkn : 21/05/2012
      tdthuccu: null,
      tdthucmoi: null,
      quytrinh_id: null,
      quytrinh_tc_tn: null,
      huonggiao_tc_tn: null,

      // Thangph: Copy từ Form Đặt mới -Ngày 10/06/2015
      dtienld: 0,
      dvatld: 0,
      dsHDKH: [],
      dsDBTBBC: [],
      dsCTTHD: [],
      dsHDBC: [],
      dsKM_HDTB: [],
      dsTIEN_HDTB: [],
      dsTDTD_ADSL: [],
      dsDC_HDTB: [],
      dsHDTBBC: [],
      dsHDBCADSL: [],
      dsHDTBDV: [],
      dsHDTB: [],
      dsHDBCMGWAN: [],
      dsHDBCCD: [],
      dsDKDVGTBC: [],
      dsHDBCTSL: [],
      dsHDBCIMS: [],
      dsHDTB_CNTT: [],
      tocdo_id: 0,
      ltb_adsl_id: 1, //Defaul
      kh_cd: 0,
      ltb_adsl_cu_id: 0,
      ltb_adsl_moi_id: 0,
      tocdo_moi_id: 0,
      tocdo_cu_id: 0,
      //Hiếu bổ sung phần khai báo cho hàm laytien --14/09/2010
      ///TuấnNA bổ sung thêm biến sử dụng trong hàm lấy tiền ld ---Ngày 15/09/2010
      tien_tb: 0,
      vat_tb: 0,
      tien_dv: 0,
      vat_dv: 0,
      tien_kmld: 0,
      tien_kmtb: 0,
      dtiendatcoc_tt: 0,
      dvatdatcoc_tt: 0,
      dtiendatcoc_km: 0,
      dvatdatcoc_km: 0,
      dtienthoai_dc: 0,
      DS_ID_DV: '', //Lưu danh sách ID dịch vụ gia tăng--Hiếu 16/09/2010
      DS_ID_TB: '', //Lưu danh sách ID mua thiết bị - Hiếu 16/09/2010
      dtCTKM: [], //hiếu 16/09/2010: Lưu danh sách các khoản mục.
      phuongld_id: 0, //lay cho hàm lay tien theo khoan muc
      phold_id: 0, //lay cho hàm lay tien theo khoan muc
      apld_id: 0,
      khuld_id: 0,
      CT_TIENHD_ID: null,
      matocdo: '',
      tocdothuc_cu_id: -1,
      ngaytucthi: 1, //defaul 01
      IP: '',
      Bras_id: 0,
      kt_load: true,
      iKieu: 0, //0: lap hop dong, 1: tra cuu, 2: sua km
      nhap_tttc: 0, //Default không bắt buộc nhập thông tin tính cước
      batbuoc_ctv: 0, //Default không bắt buộc nhập CTV
      kiemtra: true,
      batbuoc_sothang_ck: 0,
      plkhachhang_id: 0,

      ctv_id: null,
      donvi_ctv_id: null,
      loainv_ctv_id: null,
      nguoigt_id: null,
      donvi_nguoi_gt_id: null,
      loainv_nguoi_gt_id: null,
      batbuoc_gioithieu_tocdo: 0,
      macdinh_tucthi: 0,
      kiemtra_no_tratruoc: 0,
      ts_sinhma_gd_theo_donvi: false, //Dungf cho Hni sinh ma GD theo don vi quan ly cua ng dung
      loi_sinh_magd: false,
      thongso_ban: {
        tocdo_id: 0,
        tocdothuc_id: 0,
        tocdo_pir_id: 0,
        tocdo_nix: 0,
        tocdo_isp: 0,
        chuquan_id: 0,
        chuquanld_id: 0,
        congnghe_id: 0,
        muccuoc_id: 0,
        kieutra_id: 0,
        doituong_id: 0,
        muccuoctb_id: 0,
        trangbi_id: 0,
        kieuld_id: 0,
        thietbidc_id: 0,
        loaimd_id: 0,
        loaikenh_id: 0,
        thietbidc_id_cuoi: 0,
        thoihan_id: 0,
        ma_tn: '',
        ma_doicap: '',
        chuquanld_id_cuoi: 0,
        phanloai_id: 0,
        phantach_id: 0,
        mhkn_id: 0,
        gdkn_id: 0,
        yc_baove: 0,
        tocdo_pop: 0,
        gdkn_pop_id: 0,
        gdkn_kh_id: 0,
        cauhinh_dt_id: 0,
        kieu_duplex_id: 0,
        kieukn_id: 0,
        mtu_size: '',
        auto_nego: '',
        sncp: '',
        cap_chinh: '',
        cap_duphong: '',
        diem_pop: '',
        wan_ip: '',
        lan_ip: '',
        ce_ip: '',
        tt_ip: '',
        sl_ip: '',
        ten_trunk_nni: '',
        so_as: '',
        hqos: '',
        cos: '',
        kieu_nghiemthu: [],
        svlan: '',
        cvlan: '',
        vlan_transparent: '',
        tt_port: '',
        loai_ip: [],
        thongso_cntt: []
      },
      thongso_tc: {
        tocdo_id: 0,
        tocdothuc_id: 0,
        tocdo_pir_id: 0,
        tocdo_nix: 0,
        tocdo_isp: 0,
        chuquan_id: 0,
        chuquanld_id: 0,
        congnghe_id: 0,
        muccuoc_id: 0,
        kieutra_id: 0,
        doituong_id: 0,
        muccuoctb_id: 0,
        trangbi_id: 0,
        kieuld_id: 0,
        thietbidc_id: 0,
        loaimd_id: 0,
        loaikenh_id: 0,
        thietbidc_id_cuoi: 0,
        thoihan_id: 0,
        ma_tn: '',
        ma_doicap: '',
        chuquanld_id_cuoi: 0,
        phanloai_id: 0,
        phantach_id: 0,
        mhkn_id: 0,
        gdkn_id: 0,
        yc_baove: 0,
        tocdo_pop: 0,
        gdkn_pop_id: 0,
        gdkn_kh_id: 0,
        cauhinh_dt_id: 0,
        kieu_duplex_id: 0,
        kieukn_id: 0,
        mtu_size: '',
        auto_nego: '',
        sncp: '',
        cap_chinh: '',
        cap_duphong: '',
        diem_pop: '',
        wan_ip: '',
        lan_ip: '',
        ce_ip: '',
        tt_ip: '',
        sl_ip: '',
        ten_trunk_nni: '',
        so_as: '',
        hqos: '',
        cos: '',
        kieu_nghiemthu: [],
        svlan: '',
        cvlan: '',
        vlan_transparent: '',
        tt_port: '',
        loai_ip: [],
        thongso_cntt: []
      },
      //Khai báo dành cho thay đổi tốc độ hàng loạt
      ghichu: '',
      sothangck: '',
      ma_tb_v2: '',
      tocdoadsl: 0,
      sothang_sd: 0,
      //Khai báo Grid
      lvwThueBao: {
        loading: false,
        list: [],
        cols: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            allowFilter: true,
            allowSorting: false,
            freeze: 'left'
          },
          {
            fieldName: 'ma_tb',
            headerText: 'Số máy/Acc',
            allowFilter: true,
            allowSorting: false,
            freeze: 'left'
          },
          {
            fieldName: 'ten_tb',
            headerText: 'Tên thuê bao',
            allowFilter: true,
            allowSorting: false,
            freeze: 'left'
          },
          {
            fieldName: 'diachi_ld',
            headerText: 'Địa chỉ lắp đặt',
            allowFilter: true,
            allowSorting: false
          },
          {
            fieldName: 'td_cu',
            headerText: 'Tốc độ cũ',
            allowFilter: true,
            allowSorting: false
          },
          {
            fieldName: 'td_moi',
            headerText: 'Tốc độ mới',
            allowFilter: true,
            allowSorting: false
          },
          {
            fieldName: 'muccuoc_cu',
            headerText: 'Mức cước cũ',
            allowFilter: true,
            allowSorting: false
          },
          {
            fieldName: 'muccuoc_moi',
            headerText: 'Mức cước mới',
            allowFilter: true,
            allowSorting: false
          },
          {
            fieldName: 'kieutra_cu',
            headerText: 'Kiểu trả cũ',
            allowFilter: true,
            allowSorting: false
          },
          {
            fieldName: 'kieutra_moi',
            headerText: 'Kiểu trả mới',
            allowFilter: true,
            allowSorting: false
          }
        ]
      },
      dtpNgayTra: {
        Text: '',
        Enabled: true
      },
      txtTienTra: {
        Text: '',
        Enabled: true
      },

      txtTTCuoc: {
        Text: '',
        Enabled: true
      },
      txtCuoc_TBI: {
        Text: 0,
        Enabled: true
      },
      txtCuocSD: {
        Text: 0,
        Enabled: true
      },
      txtSoThangCK: 0,
      //Khai báo combobox
      cboTocDoADSL: {
        list: [],
        Enabled: true,
        SelectedValue: null
      },
      cboLoaihinhTB: {
        list: [],
        Enabled: false,
        SelectedValue: null
      },
      cboTocDoThuc_Moi: {
        list: [],
        Enabled: true,
        SelectedValue: null
      },
      cboKieuLD: {
        list: [],
        Enabled: true,
        SelectedValue: null
      },
      cboTocDoCu: {
        list: [],
        Enabled: false,
        SelectedValue: null
      },
      cboDichVuVT: {
        list: [],
        Enabled: true,
        SelectedValue: null
      },
      cboMucCuocMoi: {
        list: [],
        Enabled: true,
        SelectedValue: null
      },
      cboMucCuocCu: {
        list: [],
        Enabled: false,
        SelectedValue: null
      },
      cboLoaiTBiADSL: {
        list: [],
        Enabled: false,
        SelectedValue: null
      },
      cboKieuTraMoi: {
        list: [],
        Enabled: true,
        SelectedValue: null
      },
      cboKieuTraCu: {
        list: [],
        Enabled: false,
        SelectedValue: null
      },
      cboCongNghe_Moi: {
        list: [],
        Enabled: false,
        SelectedValue: null
      },
      cboCongNghe_Cu: {
        list: [],
        Enabled: false,
        SelectedValue: null
      },
      cboThangTra: {
        list: [],
        Enabled: true,
        SelectedValue: null
      },

      cboDoiTuongMoi: {
        list: [],
        Enabled: true,
        SelectedValue: null
      },
      cboDoiTuongCu: {
        list: [],
        Enabled: false,
        SelectedValue: null
      },
      cboKhuyenMai: { list: [], Enabled: false, SelectedValue: null },
      cboTraTruoc: { list: [], Enabled: false, SelectedValue: null },
      cboTrangBiMoi: { list: [], Enabled: true, SelectedValue: null },
      cboTrangBiCu: { list: [], Enabled: false, SelectedValue: null },
      cboMucCuocTB: { list: [], Enabled: true, SelectedValue: null },
      cboMucCuocTBCu: { list: [], Enabled: false, SelectedValue: null },
      //Khai báo btn
      tsbtnHuyBo: {
        Enabled: true,
        Visible: true
      },
      tsbtnNhapMoi: {
        Enabled: true,
        Visible: true
      },
      tsbtnHen: {
        Enabled: true,
        Visible: true
      },
      tsbtnEmail: {
        Enabled: true,
        Visible: true
      },
      tsbtnGhiLai: {
        Enabled: true,
        Visible: true
      },
      tsbtnXoa: {
        Enabled: true,
        Visible: true
      },
      tsbtnThanhToan: {
        Enabled: true,
        Visible: true
      },
      tsbtnThemTB: {
        Enabled: true,
        Visible: true
      },
      tsbtnXoaTB: {
        Enabled: true,
        Visible: true
      },
      tsbtnThongTinLH: {
        Enabled: true,
        Visible: true
      },
      tsbtnSuaKM: {
        Enabled: true,
        Visible: true
      },
      btnPhieuYC: {
        Enabled: true
      },
      btnChon_KhuyenMai: {
        Enabled: false
      },
      btnChon_TraTruoc: {
        Enabled: false
      },
      btnChiTietKM: {
        Enabled: false
      },
      btnChiTietTraTruoc: {
        Enabled: false
      },
      btnThongTinTC: {
        Enabled: true
      },
      chkCTV: {
        Checked: false
      },
      chkNguoiGT: {
        Checked: false
      },
      chkTucThi: {
        Checked: false,
        Enabled: false
      },
      chkTTTCVisa: {
        Checked: false
      },
      chkKhuyenMai: {
        Checked: false,
        Enabled: false
      },
      chkTraTruoc: {
        Checked: false,
        Enabled: false
      },
      ctv: {
        ctv_id: null,
        ten_ctv: '',
        ma_ctv: '',
        loainv_id: -1,
        donvi_id: -1
      },
      nguoi_gt: {
        nguoi_gt_id: null,
        ten_nguoi_gt: '',
        ma_nguoi_gt: '',
        loainv_id: -1,
        donvi_id: -1
      },
      popupInHoSo_Param: {
        hdkh_id: 0,
        hdtb_id: 0,
        ma_gd: ''
      },
      headerPopup: {}
    }
  },
  watch: {},
  computed: {
    HdKhIdInt: function () {
      if (this.hdkh_id != null && this.hdkh_id != '') {
        return parseInt(this.hdkh_id)
      } else {
        return 0
      }
    },
    KhachHangIdInt: function () {
      if (this.khachhang_id == null || this.khachhang_id == '') {
        return 0
      }
      return parseInt(this.khachhang_id)
    }
  },
  methods: {
    acceptCTV(data) {
      Object.assign(this.ctv, data)
      //this.ctv.ten_ctv = this.ctv.ma_ctv + ' - ' + this.ctv.ten_ctv;
      this.ctv_id = this.ctv.ctv_id
    },
    acceptNguoiGT(data) {
      Object.assign(this.nguoi_gt, data)
      this.nguoi_gt.ten_nguoi_gt = this.nguoi_gt.ma_nguoi_gt + ' - ' + this.nguoi_gt.ten_nguoi_gt
      this.nguoigt_id = this.nguoi_gt.nguoi_gt_id
    },
    chkCTV_CheckedChanged() {
      if (this.chkCTV.Checked == false) {
        this.$refs.chonCongTacVienModal.showModal()
      }
    },
    chkNguoiGT_CheckedChanged() {
      if (this.chkNguoiGT.Checked == false) {
        this.$refs.chonNguoiGTModal.showModal()
      }
    },
    Popup(modalId) {
      this.loading(false)
      console.log(this.$refs)
      if (this.$refs[modalId]) this.$refs[modalId].show()
      else this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    async popupClosed(val) {
      if (!val) {
        this.popupRetVal = {}
        this.ClosePopup('popupComponents')
        return
      }
      console.log(val)
      this.loading(true)
      try {
        switch (this.popupComponentName) {
          case 'popup-Dangkychitietkhuyenmai':
            this.LaySothang_SD()
            this.dsKM_HDTB = val.dsKM_HDTB
            this.dtiendatcoc_km = Math.round((val.tien_datcoc * 10) / 11)
            this.dvatdatcoc_km = val.tien_datcoc - this.dtiendatcoc_km
            if (this.dsKM_HDTB.length == 0) {
              this.dtiendatcoc_km = 0
              this.dvatdatcoc_km = 0
            }
            this.HienThiTien_TraTruoc()
            this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.cboLoaihinhTB.SelectedValue, this.cboKieuLD.SelectedValue)
            break
          case 'popup-DangkychitietkhuyenmaiTT':
            this.LaySothang_SD()
            this.dsDC_HDTB = val.dsDC_HDTB
            this.dtiendatcoc_tt = Math.round((val.tien_datcoc_tt * 10) / 11)
            this.dvatdatcoc_tt = val.tien_datcoc_tt - this.dtiendatcoc_tt
            if (this.dsDC_HDTB.length == 0) {
              this.dtiendatcoc_tt = 0
              this.dvatdatcoc_tt = 0
            }
            console.log(this.dtiendatcoc_tt)
            console.log(this.dvatdatcoc_tt)
            this.HienThiTien_TraTruoc()
            this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.cboLoaihinhTB.SelectedValue, this.cboKieuLD.SelectedValue)
            break
          case 'popup-ChonKhuyenMai':
            if (val.loaikm == this.Loai_KM.KhuyenMai) this.cboKhuyenMai.SelectedValue = val.khuyenmai.khuyenmai_id
            else this.cboTraTruoc.SelectedValue = val.khuyenmai.khuyenmai_id
            break
          case 'popup-Payment':
            console.log('Đóng popup Thanh toán')
            break
          case 'popup-ThongTinTC':
            console.log(val)
            this.txtTTCuoc.Text = val
            break
        }
      } catch (ex) {
        this.$toast.error('' + ex)
        if (this.debug) console.error(ex)
      } finally {
        this.ClosePopup('popupComponents')
        this.loading(false)
      }
    },
    paymentSuccess(result) {
      // alert('Thanh toán thành công:'+ result)
      // this.$refs.thanhtoanDialog.hide()
    },
    formCloseSearchAccount(data) {
      console.log(data)
      if (data != undefined && data != null) {
        this.txtMaTB = data.ma_tb
      }
      this.$refs.popupFrmTraCuuDanhBa.hide()
    },
    btnLayMaGD_Click() {
      this.ngay_cn = moment().format('DD/MM/YYYY')
      this.$refs.popupFrmTraCuuHopDong.$children[0].clearData()
      this.$refs.popupFrmTraCuuHopDong.$children[0].loadPopup()
      this.$refs.popupFrmTraCuuHopDong.show()
    },
    async acceptSearchContract(item) {
      this.$refs.popupFrmTraCuuHopDong.hide()
      this.txtMaGD = item.hopdong.ma_gd
      this.$refs['txtMaGD'].focus()
      if (this.txtMaGD) {
        let response = await api.lay_ds_hopdong_theo_ma_gd(this.axios, {
          in_donvi_dl_id: this.$root.token.getDonViDuLieuID(),
          in_loaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL,
          in_ma_gd: this.txtMaGD,
          in_tthd_id: TrangThaiHD.MOI
        })
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          await this.HienThiTTHopDongKH(response.data.data)
        }
        if (response.data.error_code === 'BSS-00000204') {
          this.$toast.error(response.data.message)
        }

        //HienThiTTHopDongKH(new TCHopDongFacade().LAY_DS_HOPDONG_THEO_MA_GD(txtMaGD.Text, LoaiHopDong.THAY_DOI_TOCDO_ADSL, (int)tt_nd.donvi_id, TrangThaiHD.MOI, (int)tt_nd.nhanvien_id, 0));
      }
    },

    visibleToolStrip(visible) {
      this.tsbtnNhapMoi.Visible = visible
      this.tsbtnHuyBo.Visible = visible
      this.tsbtnHen.Visible = visible
      this.tsbtnEmail.Visible = visible
      this.tsbtnGhiLai.Visible = visible
      this.tsbtnXoa.Visible = visible
      this.tsbtnThanhToan.Visible = visible
      this.tsbtnThemTB.Visible = visible
      this.tsbtnXoaTB.Visible = visible
      this.tsbtnThongTinLH.Visible = visible

      this.tsbtnSuaKM.Visible = this.iKieu == 2
      this.cboTocDoADSL.Enabled = false
      this.cboLoaihinhTB.Enabled = true
    },
    EnableTraTruoc(kieu) {
      if (kieu) {
        this.cboThangTra.Enabled = true
        this.txtTienTra.Enabled = true
        this.dtpNgayTra.Enabled = true
      } else {
        this.cboThangTra.Enabled = false
        this.txtTienTra.Enabled = false
        this.dtpNgayTra.Enabled = false
      }
    },
    async Lay_DS_MucCuoc_TB_V2(khuld_id, apld_id, phold_id, phuongld_id, loaitb_id, doituong_id, ngay_yc, cboTrangBiMoi_SelectedValue) {
      let vtocdo_id = 0
      let vmuccuoc_id = 0 //2 Lưu lượng

      if (
        this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_FTTH ||
        this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.WIFI_FIBER ||
        this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_ADSL ||
        this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC ||
        this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC
      ) {
        vtocdo_id = this.cboTocDoADSL.SelectedValue
        vmuccuoc_id = this.cboMucCuocMoi.SelectedValue
      } else if (this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_MYTV) {
        vtocdo_id = this.cboTocDoADSL.SelectedValue
        vmuccuoc_id = 0
      } else if (this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.FSECURE) {
        //Lamnt: bổ sung hiển thị mức cước theo tốc độ thuê bao FSecure (09/03/2017)
        vtocdo_id = this.cboTocDoADSL.SelectedValue
        vmuccuoc_id = 0
      }
      try {
        let response = await api.lay_ds_mucuoc_tb_v2(this.axios, {
          apld_id: apld_id,
          doituong_id: doituong_id,
          khuld_id: khuld_id,
          loaitb_id: loaitb_id,
          muccuoc_id: vmuccuoc_id,
          ngay: ngay_yc,
          phold_id: phold_id,
          phuongld_id: phuongld_id,
          tocdo_id: vtocdo_id,
          trangbi_id: cboTrangBiMoi_SelectedValue
        })
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.cboMucCuocTB.list = response.data.data
          this.cboMucCuocTB.SelectedValue = this.cboMucCuocTB.list[0].muccuoctb_id
          await this.cboMucCuocTB_SelectedValueChanged()
          if (this.cboMucCuocTBCu.list.length > 0) {
            if (this.hdkh_id == 0) {
              // Nếu chưa ghi lại hợp đồng
              for (let i = 0; i < response.data.data.length; i++) {
                if (response.data.data[i].mucuoctb_id == this.cboMucCuocTBCu.SelectedValue) {
                  this.cboMucCuocTB.SelectedValue = this.cboMucCuocTBCu.SelectedValue

                  await this.cboMucCuocTB_SelectedValueChanged()
                  break
                }
              }
            }
          }
        } else {
          this.cboMucCuocTB.list = []
          this.cboMucCuocTB.SelectedValue = null
        }
      } catch (e) {
        console.log(e)
      }
    },
    KiemTraDL_KhachHang() {
      if (this.txtTenKH == '') {
        this.$toast.error('Hãy nhập tên khách hàng !')
        this.$refs.txtTenKH.focus()
        return false
      }

      if (this.txtMaKH == '' && this.khachhang_id != 0) {
        this.$toast.error('Kiểm tra lại mã khách hàng !')
        this.$refs.txtMaKH.focus()
        return false
      }
      //Hieutc: 11.10.2011: Kiểm tra mã khách hàng đã theo đúng chuẩn chưa
      if (this.txtMaKH.trim() != '') {
        if (laphd.Kiemtra_makh_mtt(this.txtMaKH.trim(), 1, LoaiHopDong.THAY_DOI_TOCDO_ADSL) == 0) {
          this.ShowAlert(`Bạn không phải thanh toán cho hợp đồng chuyển đổi loại hình cho thuê bao di động từ Post->Card !`)('Mã khách hàng không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
          this.$refs.txtMaKH.focus()
          return false
        }
      }
      if (this.txtMaGD.trim() != '') {
        if (laphd.Kiemtra_makh_mtt(this.txtMaGD.trim(), 3, LoaiHopDong.THAY_DOI_TOCDO_ADSL) == 0) {
          this.ShowAlert(`Bạn không phải thanh toán cho hợp đồng chuyển đổi loại hình cho thuê bao di động từ Post->Card !`)('Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
          //this.txtMaGD.focus()
          return false
        }
      }

      //KieuNV:Zem lại vì ko bắt buộc chọn CTV
      //nhapt mở lại: 27/02/2018
      if (this.batbuoc_ctv == 1) {
        if (!this.chkCTV.Checked || this.ctv.ten_ctv.trim() == '' || this.ctv.ten_ctv == null || this.ctv_id == 0 || this.ctv_id == null) {
          this.$toast.error('Bạn chưa chọn nhân viên phát triển!')
          this.$refs.txtCTV.focus()
          return false
        }
      }

      if (this.batbuoc_gioithieu_tocdo == 1) {
        if (!this.chkNguoiGT.Checked || this.nguoi_gt.ten_nguoi_gt.trim() == '' || this.nguoi_gt.ten_nguoi_gt.trim() == null || this.nguoigt_id == 0 || this.nguoigt_id == null) {
          this.$toast.error('Bạn chưa chọn người giới thiệu hợp đồng/phụ lục')
          this.$refs.txtNguoiGT.focus()
          return false
        }
      }

      if (this.chkCTV.Checked && this.ctv.ten_ctv.trim() == '' && this.ctv_id == 0) {
        this.$toast.error('Bạn chưa chọn nhân viên phát triển!')
        this.$refs.txtCTV.focus()
        return false
      }
      if (this.chkNguoiGT.Checked && this.nguoi_gt.ten_nguoi_gt.trim() == null && this.nguoigt_id == 0) {
        this.$toast.error('Bạn chưa chọn nhân viên giới thiệu hợp đồng!')
        this.$refs.txtNguoiGT.focus()
        return false
      }
      return true
    },

    // Sự kiện button
    btnThongTinTC_Click() {
      this.ThongTinTC()
    },
    async tsbtnDKGoiDaDV_Click() {
      this.goidadv()
    },

    async tsbtnGhiLai_click() {
      //this.CapNhat();
      if (this.thongso_ban.muccuoctb_id > 0) {
        await this.CapNhat()
      } else {
        this.$toast.error('Bạn chưa điền đủ các thông số để tạo lập hợp đồng')
      }
    },
    tsbtnXoa_Click() {
      this.$confirm('Bạn thật sự muốn xóa hợp đồng không ?', 'Thông báo', {
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
        this.XoaHDKH()
      })
    },
    bntLayMaKh_Click() {
      this.$refs.popupFrmTraCuuDanhBa.show()
    },

    tsbtnNhapMoi_Click() {
      this.SetButton(1)
      this.Clear()
    },
    tsbtnHuyBo_Click() {
      this.SetButton(0)
    },
    tsbtnThemTB_Click() {
      this.ThemHDThueBao()
    },
    tsbtnXoaTB_Click() {
      this.XoaHDTB()
    },
    ShowPopupChonKhuyenMai(loaikm) {
      // private void btnChon_KhuyenMai_Click(object sender, EventArgs e)
      let dsKhuyenMai = this.cboKhuyenMai.list.map(function (item) {
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

      let dstraTruoc = this.cboTraTruoc.list.map(function (item) {
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
      var dulieu = {}
      dulieu.loai = loaikm
      dulieu.dt = loaikm == this.Loai_KM.KhuyenMai ? dsKhuyenMai : dstraTruoc
      this.popupComponentData = dulieu
      this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popup-ChonKhuyenMai')
      this.popupComponentName = 'popup-ChonKhuyenMai'
      this.Popup('popupComponents')
    },
    async btnChiTietKM_Click() {
      if (this.chkKhuyenMai.Checked) {
        if (this.cboKhuyenMai.list.length > 0) {
          let response = await api.kiemtra_thoihan_km(this.axios, {
            vkhuyenmai_id: this.cboKhuyenMai.SelectedValue,
            vngay_yc: moment().format('DD/MM/YYYY')
          })
          if (response.data.error_code === 'BSS-00000000' && response.data.data) {
            if (response.data.data == 0) {
              let input1 = {}
              input1.type = 1
              input1.param = this.cboKhuyenMai.SelectedValue
              let response1 = await api.fn_tt_khuyenmai(this.axios, input1)
              if (response1.data.error_code === 'BSS-00000000' && response1.data.data) {
                let ngay_kt_km = response1.data.data
                await this.$confirm('Khuyến mại ' + this.cboKhuyenMai.SelectedText + ' đã hết thời hạn từ ngày ' + ngay_kt_km + '.\nBạn có muốn tiếp tục chọn chi tiết khuyến mại?', {
                  confirmButtonText: 'Có',
                  cancelButtonText: 'Không',
                  type: 'warning'
                })
                  .then(async () => {})
                  .catch(async () => {
                    return
                  })
              }
            }
            let itocdo_id = 0
            let itrangbi_id = 0 //Default: KH tự trang bị
            let imuccuoc_id = 0
            let iloaitb_id = 0
            if (this.cboKieuLD.list.length <= 0) return
            if (this.cboDoiTuongMoi.list.length <= 0) return
            if (this.cboTocDoADSL.SelectedValue != null && this.cboTocDoADSL.SelectedValue != 0) itocdo_id = this.cboTocDoADSL.SelectedValue
            if (this.cboMucCuocMoi.SelectedValue != null && this.cboMucCuocMoi.SelectedValue != 0) imuccuoc_id = this.cboMucCuocMoi.SelectedValue
            if (this.cboLoaihinhTB.SelectedValue != null && this.cboMucCuocMoi.SelectedValue != 0) iloaitb_id = this.cboLoaihinhTB.SelectedValue

            this.dulieuKM.khuyenmai_id = this.cboKhuyenMai.SelectedValue
            this.dulieuKM.vchuyenmang = 0
            this.dulieuKM._hdtb_id = this.hdtb_id
            this.dulieuKM.vdichvuvt_id = this.cboDichVuVT.value
            this.dulieuKM.dsKM_HDTB = this.dsKM_HDTB
            this.dulieuKM.vtocdotn_id = itocdo_id
            this.dulieuKM.vmuccuoctn_id = imuccuoc_id
            this.dulieuKM.loaihinh_tb = iloaitb_id
            this.dulieuKM.kieu_km = 0
            //this.$refs.chitietkmDialog.show();
            this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popup-Dangkychitietkhuyenmai.vue')
            this.popupComponentName = 'popup-Dangkychitietkhuyenmai'
            this.popupComponentData = this.dulieuKM // 0: khuyen mai, 1 tra truoc
            this.Popup('popupComponents')
          }
        }
      }
    },
    formCloseKhuyenMai(retData) {
      if (this.dulieuKM.kieu_km == 0 && retData != null) {
        this.LaySothang_SD()
        this.dsKM_HDTB = retData.dsKM_HDTB
        this.dtiendatcoc_km = Math.round((retData.tien_datcoc * 10) / 11)
        this.dvatdatcoc_km = retData.tien_datcoc - this.dtiendatcoc_km
        if (this.dsKM_HDTB.length == 0) {
          this.dtiendatcoc_km = 0
          this.dvatdatcoc_km = 0
        }
        this.HienThiTien_TraTruoc()
        this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.cboLoaihinhTB.SelectedValue, this.cboKieuLD.SelectedValue)
      }
      if (this.dulieuKM.kieu_km == 1 && retData != null) {
        this.LaySothang_SD()
        this.dsDC_HDTB = retData.dsDC_HDTB
        this.dtiendatcoc_tt = Math.round((retData.tien_datcoc_tt * 10) / 11)
        this.dvatdatcoc_tt = retData.tien_datcoc_tt - this.dtiendatcoc_tt
        if (this.dsDC_HDTB.length == 0) {
          this.dtiendatcoc_tt = 0
          this.dvatdatcoc_tt = 0
        }
        this.HienThiTien_TraTruoc()
        this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.cboLoaihinhTB.SelectedValue, this.cboKieuLD.SelectedValue)
      }
    },
    async btnChiTietTraTruoc_Click() {
      if (this.chkTraTruoc.Checked) {
        if (this.cboTraTruoc.list.length > 0) {
          let response = await api.kiemtra_thoihan_km(this.axios, {
            vkhuyenmai_id: this.cboTraTruoc.SelectedValue,
            vngay_yc: moment().format('DD/MM/YYYY')
          })
          if (response.data.error_code === 'BSS-00000000' && response.data.data) {
            if (response.data.data == 0) {
              let input1 = {}
              input1.type = 1
              input1.param = this.cboTraTruoc.SelectedValue
              let response1 = await api.fn_tt_khuyenmai(this.axios, input1)
              if (response1.data.error_code === 'BSS-00000000' && response1.data.data) {
                let ngay_kt_km = response1.data.data
                await this.$confirm('Khuyến mại ' + this.cboKhuyenMai.SelectedText + ' đã hết thời hạn từ ngày ' + ngay_kt_km + '.\nBạn có muốn tiếp tục chọn chi tiết khuyến mại?', {
                  confirmButtonText: 'Có',
                  cancelButtonText: 'Không',
                  type: 'warning'
                })
                  .then(async () => {})
                  .catch(async () => {
                    return
                  })
              }
            }
            let itocdo_id = 0
            let itrangbi_id = 0 //Default: KH tự trang bị
            let imuccuoc_id = 0
            let iloaitb_id = 0
            if (this.cboKieuLD.list.length <= 0) return
            if (this.cboDoiTuongMoi.list.length <= 0) return
            if (this.cboTocDoADSL.SelectedValue != null && this.cboTocDoADSL.SelectedValue != 0) itocdo_id = this.cboTocDoADSL.SelectedValue
            if (this.cboMucCuocMoi.SelectedValue != null && this.cboMucCuocMoi.SelectedValue != 0) imuccuoc_id = this.cboMucCuocMoi.SelectedValue
            if (this.cboLoaihinhTB.SelectedValue != null && this.cboMucCuocMoi.SelectedValue != 0) iloaitb_id = this.cboLoaihinhTB.SelectedValue

            this.dulieuKM.khuyenmai_id = this.cboTraTruoc.SelectedValue
            this.dulieuKM.dsDC_HDTB = this.dsDC_HDTB
            this.dulieuKM.vtocdotn_id = itocdo_id
            this.dulieuKM.vmuccuoctn_id = imuccuoc_id
            this.dulieuKM.loaihinh_tb = iloaitb_id
            this.dulieuKM.kieu_km = 1
            //this.$refs.chitietkmDialog.show();
            this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popup-Dangkychitietkhuyenmai')
            this.popupComponentName = 'popup-DangkychitietkhuyenmaiTT'
            this.popupComponentData = this.dulieuKM // 0: khuyen mai, 1 tra truoc
            this.Popup('popupComponents')
          }
        }
      }
    },
    tsbtnEmail_Click: async function () {
      if (this.hdkh_id != 0) {
        try {
          await this.$refs.thongTinEmailPopUp.getDsThongTinEmail()
        } catch (err) {
          console.log('thongTinEmailOpen-err', err)
        } finally {
          this.$refs.dialogObjthongTinEmail.show()
        }
      }
    },
    async tsbtnThongTinLH_Click() {
      if (this.hdkh_id > 0) {
        try {
          await this.$refs.thongTinLienHePopUp.getThongTinLienHe()
        } catch (err) {
          console.log('lienHeOpen-err', err)
        } finally {
          this.$refs.thongtinlienheDialog.show()
        }
      }
    },
    async tsbtnThanhToan_Click() {
      try {
        //this.popupComponentData = dulieu
        this.headerPopup = {
          title: 'test',
          list: [
            { name: 'Trang chủ', link: { name: 'Ui.cards' } },
            {
              name: ' Bán chéo',
              link: { name: 'Ui.cards' }
            },
            {
              name: ' Thay đổi tốc độ internet',
              link: { name: 'Ui.cards' }
            }
          ]
        }
        this.popupComponent = () => import('@/modules/contract/setup/Payment/Payment.vue')
        this.popupComponentName = 'popup-ThanhToan'
        this.Popup('popupComponents')
        // this.$refs.thanhtoanDialog.show()
        // this.$refs.thanhToanPopUp.SetMaGd(this.txtMaGD)
      } catch (err) {
        console.log('tsbtnThanhToan_Click', err)
      } finally {
      }
    },
    tsbtnInHD_Click() {
      try {
        if (this.hdkh_id != 0) {
          console.log(this.hdkh_id)
          this.popupInHoSo_Param.hdkh_id = this.hdkh_id
          this.popupInHoSo_Param.ma_gd = this.txtMaGD
          this.popupInHoSo_Param.hdtb_id = this.hdtb_id
          this.$refs.popupInHoSo.showModal()
        } else {
          this.$toast.error('Chưa có thông tin hợp đồng!')
        }
      } catch (ex) {
        console.log(ex)
      }
    },
    tsbtnHen_Click() {
      console.log(this.hdkh_id)
      if (this.hdkh_id != 0) {
        this.$refs.thongTinHenKhachHangModal.showModal()
      }
    },
    tsbtnSuaKM_Click() {},
    chkTucThi_CheckedChanged() {
      this.HT_Tien_ConLai_DatCoc_Cu()
    },
    chkTTTCVisa_CheckedChanged() {
      console.log(this.chkTTTCVisa.Checked)
      if (!this.chkTTTCVisa.Checked) {
        this.HienThi_TTTC_Visa()
      } else {
        this.txtTTCuocVisa = ''
      }
    },
    async goidadv() {
      let query = {
        ma_gd: this.txtMaGD
      }
      let routeData = this.$router.resolve({ name: 'DangKyGoiDaDichVu', query: query })
      window.open(routeData.href, '_blank')
    },
    async txtMaGD_KeyPress() {
      this.txtMaGD = this.clearTheSpace(this.txtMaGD)
      this.txtMaGD = this.Catdaunhay(this.txtMaGD)
      if (this.txtMaGD != '') {
        if (this.txtMaGD) {
          let response = await api.lay_ds_hopdong_theo_ma_gd(this.axios, {
            in_donvi_dl_id: 0,
            in_loaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL,
            in_ma_gd: this.txtMaGD,
            in_tthd_id: TrangThaiHD.MOI
          })
          if (response.data.error_code === 'BSS-00000000' && response.data.data) {
            await this.HienThiTTHopDongKH(response.data.data)
          }
          if (response.data.error_code === 'BSS-00000204') {
            this.$toast.error(response.data.message)
          }
        }
      }
    },
    async CapNhat() {
      console.log('thongso_tc: ', this.thongso_tc)
      console.log('thongso_ban: ', this.thongso_ban)
      try {
        this.loading(true)
        let response = await api.kt_va_lay_tt_capnhat_frmbcthaydoitocdoadsl(this.axios, {
          ds_para: JSON.stringify({
            THEMMOI: !this.tsbtnNhapMoi.Enabled,
            MA_TB: this.txtMaTB,
            DICHVUVT_ID: this.cboDichVuVT.SelectedValue,
            CHKTRATRUOC: !this.chkTraTruoc.Checked,
            KHACHHANG_ID: this.khachhang_id,
            THUEBAO_ID: this.thuebao_id,
            HDTB_ID: this.hdtb_id,
            HDKH_ID: this.hdkh_id,
            MA_KH: this.txtMaKH,
            LOAITB_ID: this.cboLoaihinhTB.SelectedValue,
            TOCDO_ID: this.cboTocDoADSL.SelectedValue,
            TOCDO_CU_ID: this.cboTocDoCu.SelectedValue,
            MUCCUOC_ID: this.cboMucCuocMoi.SelectedValue,
            KIEULD_ID: this.cboKieuLD.SelectedValue,
            TINHTC_ID: this.tinh_tb_id,
            MA_GD: this.txtMaGD
          })
        })
        let huonggiao_tn = 0
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          if (response.data.data.ERROR_CODE == 0) {
            this.loading(false)
            this.$toast.error(response.data.data.MESSAGE)

            return
          }
          if (response.data.data.ERROR_CODE == -1) {
            this.loading(false)
            this.$toast.error(response.data.data.MESSAGE)
            return
          }
          let result = response.data.data.RESULT

          huonggiao_tn = result.HUONGGIAO_ID
          this.quytrinh_id = result.QUYTRINH_ID
        }
        let response2 = await api.layhuonggiao_tiepnhan(this.axios, {
          tinhthicong_id: this.tinh_tb_id,
          loaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL,
          loaitb_id: this.cboLoaihinhTB.SelectedValue,
          tocdo_id: this.thongso_tc.tocdo_id,
          muccuoc_id: this.thongso_tc.muccuoc_id,
          kieuld_id: this.thongso_tc.kieuld_id
        })
        if (response2.data.error_code === 'BSS-00000000' && response2.data.data) {
          this.quytrinh_tc_tn = response2.data.data[0]['quytrinh_id']
          this.huonggiao_tc_tn = response2.data.data[0]['huonggiao_id']
        }
        if (!this.quytrinh_id) {
          this.$toast.error('Quy trình chưa được thiết lập, bạn hãy liên hệ admin để xử lý!')
          return false
        }
        if (!this.quytrinh_tc_tn) {
          this.$toast.error('Quy trình ở tỉnh thi công tiếp nhận chưa được thiết lập, bạn hãy liên hệ admin để xử lý!')
          return false
        }

        await this.TaoDuLieu(true)
        let response1 = await api.ins_update_hd_tdtd_bc_frmbcthaydoitocdoadsl(this.axios, {
          ds_para: JSON.stringify({
            THEMMOI: !this.tsbtnNhapMoi.Enabled,
            HUONGGIAO_ID: huonggiao_tn,
            HDTB_ID: this.hdtb_id,
            KIEULD_ID: this.cboKieuLD.SelectedValue,
            KIEUTRA_MOI: this.cboKieuTraMoi.SelectedValue,
            TINHTC_ID: this.tinh_tb_id,
            HD_KHACHHANG: this.dsHDKH,
            HD_THUEBAO: this.dsHDTB,
            HDTB_ADSL: this.dsTDTD_ADSL,
            CT_TIENHD: this.dsCTTHD,
            HDTB_DV: this.dsHDTBDV,
            KHUYENMAI_HDTB: this.dsKM_HDTB.length == 0 ? '' : this.dsKM_HDTB,
            DATCOC_HDTB: this.dsDC_HDTB,
            HDTB_CNTT: this.dsHDTB_CNTT,
            HD_BANCHEO: this.dsHDBC,
            HDTB_BANCHEO: this.dsHDTBBC,
            HDBC_MGWAN: this.dsHDBCMGWAN,
            HDBC_ADSL: this.dsHDBCADSL,
            HDBC_CD: this.dsHDBCCD,
            DANGKY_DVGT: this.dsDKDVGTBC,
            HDBC_TSL: this.dsHDBCTSL,
            HDBC_IMS: this.dsHDBCIMS,
            THONGTIN_TT: []
          })
        })
        if (response1.data.error_code === 'BSS-00000000' && response1.data.data) {
          if (response1.data.data.ERROR_CODE == 0) {
            this.loading(false)
            this.$toast.error(response1.data.data.MESSAGE)
            return
          }
          if (response1.data.data.ERROR_CODE == -1) {
            this.loading(false)
            this.$toast.error('Cập nhật thất bại')
            return
          }
          if (response1.data.data.ERROR_CODE == 1) {
            this.$toast.success(response1.data.data.MESSAGE)
            let response3 = await api.ht_tdtd_frmbcthaydoitocdoadsl(this.axios, {
              ds_para: JSON.stringify({ DICHVUVT_ID: this.cboDichVuVT.SelectedValue, HDKH_ID: this.hdkh_id })
            })
            if (response3.data.error_code === 'BSS-00000000' && response3.data.data) {
              let result3 = response3.data.data
              await this.HienThiDanhSacHDTB(result3)
              this.SetButton(3)
            }
          }
        }
        this.loading(false)
      } catch (e) {
        this.loading(false)
      }
    },
    async ThemHDThueBao() {
      let response = await api.kt_va_lay_tt_them_hdtb_frmbcthaydoitocdoadsl(this.axios, {
        ds_para: JSON.stringify({
          MA_TB: this.txtMaTB,
          CHKTRATRUOC: !this.chkTraTruoc.Checked,
          MA_GD: this.txtMaGD,
          DICHVUVT_ID: this.cboDichVuVT.SelectedValue,
          HDKH_ID: this.hdkh_id,
          KHACHHANG_ID: this.khachhang_id,
          THUEBAO_ID: this.thuebao_id,
          TOCDO_ID: this.cboTocDoADSL.SelectedValue,
          TOCDO_CU_ID: this.cboTocDoCu.SelectedValue,
          MUCCUOC_ID: this.cboMucCuocMoi.SelectedValue,
          LOAITB_ID: this.cboLoaihinhTB.SelectedValue,
          KIEULD_ID: this.cboKieuLD.SelectedValue
        })
      })
      let huonggiao_tn = 0
      if (response.data.error_code === 'BSS-00000000' && response.data.data) {
        if (response.data.data.ERROR_CODE == 0) {
          this.$toast.error(response.data.data.MESSAGE)
          return
        }
        let result = response.data.data.RESULT
        result = result.map(function (item) {
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
        huonggiao_tn = result.dshg[0]['huonggiao_id']
        this.quytrinh_id = result.dshg[0]['quytrinh_id']
        this.quytrinh_tc_tn = result.rs_qt[0]['quytrinh_id']
        this.huonggiao_tc_tn = result.rs_qt[0]['huonggiao_id']
        if (!this.quytrinh_id) {
          this.$toast.error('Quy trình chưa được thiết lập, bạn hãy liên hệ admin để xử lý!')
          return false
        }
        if (!this.quytrinh_tc_tn) {
          this.$toast.error('Quy trình ở tỉnh thi công tiếp nhận chưa được thiết lập, bạn hãy liên hệ admin để xử lý!')
          return false
        }
        await this.TaoDuLieu_HDTB(true)
        await this.TaoDuLieu_CT_TIENHD(true)
        await this.TaoDuLieu_HDTBDV(true)
        await this.TaoDuLieu_TIEN_HDTB_TC(true)
        await this.TaoDuLieu_TDTD_ADSL(true)
        await this.TaoDuLieu_HDBANCHEO(true)
        await this.TaoDuLieu_TDTD_BANCHEO_ADSL(true)
      }

      let response1 = await api.themhdthuebao_frmbcthaydoitocdoadsl(this.axios, {
        ds_para: JSON.stringify({
          THEMMOI: true,
          HUONGGIAO_TN_ID: huonggiao_tn,
          HDTB_ID: this.hdtb_id,
          KIEULD_ID: this.cboKieuLD.SelectedValue,
          KIEUTRA_MOI: this.cboKieuTraMoi.SelectedValue,
          TINHTC_ID: this.tinh_tb_id,
          HD_KHACHHANG: this.dsHDKH,
          HD_THUEBAO: this.dsHDTB,
          HDTB_ADSL: this.dsTDTD_ADSL,
          CD_TIENHD: this.dsCTTHD,
          HDTB_DV: this.dsHDTBDV,
          KHUYENMAI_HDTB: this.dsKM_HDTB.length == 0 ? '' : this.dsKM_HDTB,
          DATCOC_HDTB: this.dsDC_HDTB,
          HDTB_CNTT: this.dsHDTB_CNTT
        })
      })

      let response3 = await api.lay_ds_hdtb_thaydoi_tocdo(this.axios, {
        in_hdkh_id: this.hdkh_id
      })
      if (response3.data.error_code === 'BSS-00000000' && response3.data.data) {
        let result3 = response3.data.data
        await this.HienThiDanhSacHDTB(result3)
      }
    },
    async XoaHDKH() {
      try {
        this.loading(true)
        let response = await api.xoahdkh_frmbcthaydoitocdoadsl(this.axios, {
          ds_para: JSON.stringify({ HDKH_ID: this.hdkh_id })
        })
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          if (response.data.data.ERROR_CODE == 0) {
            this.$toast.error(response.data.data.MESSAGE)
            return
          }
          if (response.data.data.ERROR_CODE == 1) {
            this.$toast.success(response.data.data.MESSAGE)
          }
          this.SetButton(0)
          this.loading(false)
        }
      } catch (e) {
        console.log(e)
        this.loading(false)
      }
    },
    async XoaHDTB() {
      let response = await api.xoahdtb_frmbcthaydoitocdoadsl(this.axios, {
        ds_para: JSON.stringify({ HDKH_ID: this.hdkh_id })
      })
      if (response.data.error_code === 'BSS-00000000' && response.data.data) {
        if (response.data.data.ERROR_CODE == 0) {
          this.$toast.error(response.data.data.MESSAGE)
          return
        }
        if (response.data.data.ERROR_CODE == 1) {
          this.$toast.success('Xóa thành công')
          let response1 = await api.lay_ds_hdtb_thaydoi_tocdo(this.axios, {
            in_hdkh_id: this.hdkh_id
          })
          if (response1.data.error_code === 'BSS-00000000' && response1.data.data) {
            await this.HienThiDanhSacHDTB(response1.data.data)
          }
        }
      }
    },
    async TaoDuLieu(themmoi) {
      try {
        await this.TaoDuLieu_HDKH(themmoi)
        await this.TaoDuLieu_HDTB(themmoi)
        await this.TaoDuLieu_CT_TIENHD(true)
        await this.TaoDuLieu_HDTBDV(themmoi)
        await this.TaoDuLieu_TIEN_HDTB_TC(true)
        await this.TaoDuLieu_HDBANCHEO(themmoi)
        switch (this.cboDichVuVT.SelectedValue) {
          case DichVuVienThong.ADSL:
            console.log('Tạo dữ liệu Ghi lại')
            await this.TaoDuLieu_TDTD_ADSL(themmoi)
            await this.TaoDuLieu_TDTD_BANCHEO_ADSL(themmoi)
            break
          case DichVuVienThong.ANTOAN_BAOMAT_TT:
          case DichVuVienThong.TRUNGTAM_DULIEU:
          case DichVuVienThong.DICHVU_CNTT:
          case DichVuVienThong.HOINGHI_TRUYENHINH:
            await this.Tao_HDTB_CNTT(themmoi)
            break
          default:
            await this.Tao_HDTB_CNTT(themmoi)
            break
        }
      } catch (e) {
        console.log(e)
      }
    },

    async TaoDuLieu_HDKH(themmoi) {
      try {
        let response = await api.tt_taodulieu_hdkh_frmbcthaydoitocdoadsl(this.axios, {
          ds_para: JSON.stringify({
            THEMMOI: themmoi,
            KHACHHANG_ID: this.khachhang_id,
            DONVI_ID: this.$root.token.getDonViID()
          })
        })
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          if (response.data.data.ERROR_CODE == 0) {
            this.$toast.error(response.data.data.MESSAGE)
            return
          }
          let result = response.data.data.RESULT
          let rowHDKH = {}
          this.hdkh_id = result.HDKH_ID
          rowHDKH.HDKH_ID = result.HDKH_ID
          if (themmoi == true) {
            this.txtMaGD = result.MA_GD
            rowHDKH.MA_GD = result.MA_GD
            rowHDKH.MA_HD = this.txtMaHD
          } else {
            rowHDKH.MA_GD = this.txtMaGD
            rowHDKH.MA_HD = this.txtMaHD
          }
          rowHDKH.MA_KH = this.txtMaKH
          if (this.khachhang_id > 0) rowHDKH.KHACHHANG_ID = this.khachhang_id
          if (this.hdkh_cha_id > 0) rowHDKH.HDKH_CHA_ID = this.hdkh_cha_id
          rowHDKH.TEN_KH = this.txtTenKH
          rowHDKH.DIACHI_KH = this.txtDiaChiKH
          rowHDKH.NGAYLAP_HD = this.dtpNgayLapHD
          rowHDKH.NGAY_YC = this.dtpNgayYeuCau
          if (this.txtNgaySN != '') {
            rowHDKH.NGAY_SN = this.txtNgaySN
          }
          rowHDKH.DONVI_ID = this.$root.token.getDonViID()
          rowHDKH.NHANVIEN_ID = this.$root.token.getNhanVienID()
          rowHDKH.PHANLOAIKH_ID = result.PHANLOAIKH_ID
          if (result.KHLON_ID != null && result.KHLON_ID != '' && result.KHLON_ID != -1) rowHDKH.KHLON_ID = result.KHLON_ID
          else rowHDKH.KHLON_ID = 1
          if (this.chkCTV.Checked && this.ctv.ten_ctv != null && this.ctv.ten_ctv != '') {
            rowHDKH.CTV_ID = this.ctv_id
          }
          if (this.chkNguoiGT.Checked && this.nguoi_gt.ten_nguoi_gt != null && this.nguoi_gt.ten_nguoi_gt != '') {
            rowHDKH.NHANVIENGT_ID = this.nguoigt_id
          }
          rowHDKH.LOAIHD_ID = LoaiHopDong.THAY_DOI_TOCDO_ADSL
          rowHDKH.NGUOI_CN = this.$root.token.getNguoiDungID()
          rowHDKH.NGAY_CN = moment().format('DD/MM/YYYY HH:mm:ss')
          rowHDKH.MAY_CN = await this.$root.token.getMachine()
          rowHDKH.IP_CN = await this.$root.token.getIP()
          //
          if (themmoi == false) {
            if (this.dsHDKH.length > 0) this.dsHDKH[0] = rowHDKH
            else this.dsHDKH.push(rowHDKH)
          } else {
            this.dsHDKH.push(rowHDKH)
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    async TaoDuLieu_HDTB(themmoi) {
      try {
        let response = await api.tt_taodulieu_hd_thuebao_frmbcthaydoitocdoadsl(this.axios, {
          ds_para: JSON.stringify({
            THEMMOI: themmoi,
            MA_TB: this.txtMaTB,
            DICHVUVT_ID: this.cboDichVuVT.SelectedValue,
            MUCCUOCTB_ID: this.cboMucCuocTB.SelectedValue
          })
        })
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          if (response.data.data.ERROR_CODE == 0) {
            this.$toast.error(response.data.data.MESSAGE)
            return
          }
          let result = response.data.data.RESULT.DS_HDTB
          this.dsHDTB = result
          this.hdtb_id = response.data.data.RESULT.HDTB_ID
          this.dsHDTB[0].HDTB_ID = this.hdtb_id
          this.dsHDTB[0].HDKH_ID = this.hdkh_id
          this.dsHDTB[0].KIEULD_ID = this.cboKieuLD.SelectedValue
          this.dsHDTB[0].DOITUONG_ID = this.cboDoiTuongMoi.SelectedValue
          this.dsHDTB[0].TTHD_ID = TrangThaiHD.MOI
          this.dsHDTB[0].MUCUOCTB_ID = this.cboMucCuocTB.SelectedValue

          this.dsHDTB[0].DONVI_ID = this.donvi_id
          this.dsHDTB[0].NGUOI_CN = this.$root.token.getNguoiDungID()
          this.dsHDTB[0].NGAY_CN = moment().format('DD/MM/YYYY HH:mm:ss')
          this.dsHDTB[0].MAY_CN = await this.$root.token.getMachine()
          this.dsHDTB[0].IP_CN = await this.$root.token.getIP()
          this.dsHDTB[0].GHICHU = this.txtGhiChu
          this.dsHDTB[0].QUYTRINH_ID = this.quytrinh_id
          if (this.cboMucCuocTB.SelectedValue != null) {
            this.dsHDTB[0].MUCUOCTB_ID = this.cboMucCuocTB.SelectedValue
            this.dsHDTB[0].MUCUOC_TB = result.MUCCUOC_TB
          }
          this.dsHDTB[0].STATUS = TRANGTHAI_DONGBO.CHUADONGBO
          if (this.txtSoThangCK != '') this.dsHDTB[0].SOTHANG_CK = Number(this.txtSoThangCK)
          for (let i = 0; i < this.dsDC_HDTB.length; i++) {
            this.dsDC_HDTB[i].HDTB_ID = this.hdtb_id
          }
          for (let i = 0; i < this.dsKM_HDTB.length; i++) {
            this.dsKM_HDTB[i].HDTB_ID = this.hdtb_id
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    async TaoDuLieu_CT_TIENHD(themmoi) {
      try {
        for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          let rowCTTHD = {}
          rowCTTHD.HDTB_ID = this.hdtb_id
          if (themmoi == true) {
            let response = await api.get_keys(this.axios, {
              keyname: 'CT_TIENHD'
            })
            if (response.data.error_code === 'BSS-00000000' && response.data.data) {
              this.CT_TIENHD_ID = response.data.data
              rowCTTHD.CT_TIENHD_ID = this.CT_TIENHD_ID
            }
          }

          rowCTTHD.KHOANMUCTT_ID = this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID']
          rowCTTHD.TIEN = this.ds_tien_khoanmuc[i]['TIEN']
          rowCTTHD.VAT = this.ds_tien_khoanmuc[i]['VAT']
          if (
            this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'] == this.KHOANMUC_TT.KMTT_KM_LAPDAT ||
            this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'] == this.KHOANMUC_TT.KMTT_KM_THIETBI ||
            this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'] == this.KHOANMUC_TT.KMTT_KM_DATCOC ||
            this.ds_tien_khoanmuc['KHOANMUCTT_ID'] == this.KHOANMUC_TT.KMTT_TIENTHOAI_DC_DOI_TD
          ) {
            rowCTTHD.TIEN = Math.abs(rowCTTHD.TIEN) * -1
            rowCTTHD.VAT = Math.abs(rowCTTHD.VAT) * -1
          }
          rowCTTHD.ID = this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID']
          rowCTTHD.LOAI_ID = 1
          console.log(rowCTTHD)
          this.dsCTTHD.push(rowCTTHD)
        }
      } catch (e) {
        console.log(e)
      }
    },
    async TaoDuLieu_HDTBDV(themmoi) {
      this.dsHDTBDV = [];

      try {
        let response = await api.getHdTBDV(this.axios, {
          thuebao_id: this.thuebao_id
        })
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          let result = response.data.data
          if (result.length > 0) {
            let row = {}
            for (let i = 0; i < result.length; i++) {
              row.HDTB_ID = this.hdtb_id
              row.LOAIDV_ID = result[i]['loaidv_id']
              row.DONVI_ID = result[i]['donvi_id']
              row.KIEUDV_ID = result[i]['kieudv_id']
              this.dsHDTBDV.push(row)
            }
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    async TaoDuLieu_TIEN_HDTB_TC(themmoi) {
      try {
        let rowTIEN_HDTB = {}
        rowTIEN_HDTB.HDTB_ID = this.hdtb_id
        rowTIEN_HDTB.THUEBAO_ID = this.thuebao_id
        rowTIEN_HDTB.LOAITIEN_ID = 3
        rowTIEN_HDTB.GIATRI = this.Unformat_tien(this.txtTienTra.Text)
        this.dsTIEN_HDTB.push(rowTIEN_HDTB)
      } catch (e) {
        console.log(e)
      }
    },
    async TaoDuLieu_HDBANCHEO(themmoi) {
      try {
        let response = await api.lay_ds_db_thuebao_theo_tbid(this.axios, {
          tinhthicong_id: this.tinh_tb_id,
          thuebao_id: this.thuebao_id_tc
        })
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          let rowHDBC = {}
          let dsHDBC = []
          let ds = response.data.data
          rowHDBC.HDTB_BAN = this.hdtb_id
          rowHDBC.TINH_THICONG = this.tinh_tb_id
          rowHDBC.TINH_BAN = this.$root.token.getPhanVungID()
          rowHDBC.TRANG_THAI = 0
          rowHDBC.LOAIHD_BANCHEO = 1

          rowHDBC.THUEBAO_ID_BAN = this.thuebao_id
          rowHDBC.THUEBAO_ID_THICONG = this.thuebao_id_tc
          rowHDBC.THANHTOAN_ID_TC = ds[0]['thanhtoan_id']
          rowHDBC.KHACHHANG_ID_TC = ds[0]['khachhang_id']

          rowHDBC.THANHTOAN_ID_BAN = ds[0]['thanhtoan_id']
          rowHDBC.KHACHHANG_ID_BAN = this.khachhang_id
          dsHDBC.push(rowHDBC)
          this.dsHDBC = dsHDBC
        }

        let response1 = await api.lay_danhba_theo_thuebaoid(this.axios, {
          tinhthicong_id: this.tinh_tb_id,
          thuebao_id: this.thuebao_id_tc,
          dichvuvt_id: this.cboDichVuVT.SelectedValue,
          donvi_id: 0,
          loaihdbc: 2
        })
        if (response1.data.error_code === 'BSS-00000000' && response1.data.data) {
          let ds1 = response1.data.data
          ds1 = ds1.map(function (item) {
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
          let dsHDTBBC = []
          let rowHDTBBC = ds1[0]

          rowHDTBBC.HDTB_ID = this.hdtb_id
          rowHDTBBC.HDKH_ID = this.hdkh_id
          rowHDTBBC.DOITUONG_ID = this.thongso_tc.doituong_id
          rowHDTBBC.MUCUOCTB_ID = this.thongso_tc.muccuoctb_id
          rowHDTBBC.TTHD_ID = 1
          rowHDTBBC.QUYTRINH_ID = this.quytrinh_tc_tn
          rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn

          rowHDTBBC.KIEULD_ID = this.thongso_tc.kieuld_id
          rowHDTBBC.NGUOI_CN = this.$root.token.getNguoiDungID()
          rowHDTBBC.NGAY_CN = moment().format('DD/MM/YYYY HH:mm:ss')
          rowHDTBBC.MAY_CN = await this.$root.token.getMachine()
          rowHDTBBC.IP_CN = await this.$root.token.getIP()
          rowHDTBBC.GHICHU = this.txtGhiChu

          rowHDTBBC.STATUS = TRANGTHAI_DONGBO.CHUADONGBO
          let input2 = {}
          input2.type = 1
          input2.param = this.thongso_tc.muccuoctb_id
          let response2 = await api.fn_tt_muccuoc_tb(this.axios, input2)
          if (response2.data.error_code === 'BSS-00000000' && response2.data.data) {
            rowHDTBBC.MUCUOC_TB = response2.data.data
          }
          if (this.txtSoThangCK != '') rowHDTBBC.SOTHANG_CK = Number(this.txtSoThangCK)

          dsHDTBBC.push(rowHDTBBC)
          this.dsHDTBBC = dsHDTBBC
        }
      } catch (e) {
        console.log(e)
      }
    },
    async TaoDuLieu_TDTD_ADSL(themmoi) {
      try {
        let response = await api.tt_taodulieu_tdtd_adsl_frmbcthaydoitocdoadsl(this.axios, {
          TOCDO_ADSL_ID: this.cboTocDoADSL.SelectedValue,
          THUEBAO_ID: this.thuebao_id
        })

        let dsTDTD_ADSL = []
        let rowTDTD_ADSL = {}
        if (response.data.data) {
          rowTDTD_ADSL = response.data.data.RESULT.DS_DB_ADSL[0]
          if (response.data.data.RESULT.PROFILEDSL_ID != -1) {
            rowTDTD_ADSL.PROFILEDSL_ID = response.data.data.RESULT.PROFILEDSL_ID
          } else {
            rowTDTD_ADSL.PROFILEDSL_ID = null
          }
        } else {
          rowTDTD_ADSL.PROFILEDSL_ID = null
        }

        rowTDTD_ADSL.HDTB_ID = this.hdtb_id
        rowTDTD_ADSL.TOCDO_ID = this.cboTocDoADSL.SelectedValue
        rowTDTD_ADSL.MUCCUOC_ID = this.cboMucCuocMoi.SelectedValue

        if (this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_MYTV) {
          rowTDTD_ADSL.KIEUTRA_ID = this.cboKieuTraMoi.SelectedValue
          if (this.cboKieuLD.SelectedValue == this.KieuLapDat.THAYDOI_KIEUTRA_MYTV && this.cboKieuTraMoi.SelectedValue == this.KieuTra.TRATRUOC) {
            rowTDTD_ADSL.THANG_ID = this.cboThangTra.SelectedValue
            rowTDTD_ADSL.NGAYTRA = this.dtpNgayTra
          }
        }

        if (this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
          rowTDTD_ADSL.CUOC_SD = Number(this.txtCuocSD.Text.trim())
          rowTDTD_ADSL.CUOC_TBI = Number(this.txtCuoc_TBI.Text.trim())
        }

        rowTDTD_ADSL.TRANGBI_ID = this.cboTrangBiMoi.SelectedValue

        rowTDTD_ADSL.IP = this.IP
        rowTDTD_ADSL.THONGTIN_TC = this.txtTTCuoc.Text.trim()
        if (this.tinh_tb_id > 0) rowTDTD_ADSL.TINHKHAC = 1
        if (this.Bras_id != 0) rowTDTD_ADSL.BRAS_ID = this.Bras_id

        if (this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_MYTV || this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_SGTV) rowTDTD_ADSL.TUCTHI = 2 // Với mytv, saigontv thì thay đổi tức thì luôn.
        else {
          if (this.chkTucThi.Checked == false) rowTDTD_ADSL.TUCTHI = 0
          if (this.chkTucThi.Checked == true && this.cboKieuLD.SelectedValue == this.KieuLapDat.THAYDOI_MUCCUOC_ADSL_TK) rowTDTD_ADSL.TUCTHI = 1
          if ((this.chkTucThi.Checked == true && this.cboKieuLD.SelectedValue == this.KieuLapDat.THAYDOI_TOCDO_ADSL_TK) || this.cboKieuLD.SelectedValue == this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON || this.cboKieuLD.SelectedValue == this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON) {
            console.log('HDTB_BC: ', this.cboKieuLD.SelectedValue)
            rowTDTD_ADSL.TUCTHI = 2
          }

          if (this.chkTucThi.Checked == true && this.cboKieuLD.SelectedValue == this.KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL_TK) rowTDTD_ADSL.TUCTHI = 3
          if ((this.chkTucThi.Checked == true && this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC) || this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) rowTDTD_ADSL.TUCTHI = 4
        }

        if (this.cboKieuLD.SelectedValue == this.KieuLapDat.THAYDOI_TOCDOTHUC_ADSL) {
          rowTDTD_ADSL.TOCDOTHUC_ID = this.cboTocDoThuc_Moi.SelectedValue
          rowTDTD_ADSL.TUCTHI = 5 // Thay đổi tđ thực thì luôn tức thì
        }
        if (this.cboKieuLD.SelectedValue == this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON || this.cboKieuLD.SelectedValue == this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON) {
          rowTDTD_ADSL.CONGNGHE_ID = this.cboCongNghe_Moi.SelectedValue
        }
        dsTDTD_ADSL.push(rowTDTD_ADSL)
        this.dsTDTD_ADSL = dsTDTD_ADSL
        // them  de tesst
        this.chkTucThi.Checked = true
        this.chkTucThi.Enabled = true
      } catch (e) {
        console.log(e)
      }
    },
    async TaoDuLieu_TDTD_BANCHEO_ADSL(themmoi) {
      try {
        console.log('Tạo HĐBC test')
        let rowHDBCADSL = {}
        let response = await api.lay_ds_db_adsl_theo_tbid(this.axios, {
          tinhthicong_id: this.tinh_tb_id,
          thuebao_id: this.thuebao_id_tc
        })

        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          let resultRow = response.data.data.map(function (item) {
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
          rowHDBCADSL = resultRow[0]
          // rowHDBCADSL = rowHDBCADSL.map(function (item) {
          //   for (var key in item) {
          //     var upper = key.toUpperCase()
          //     // check if it already wasn't uppercase
          //     if (upper !== key) {
          //       item[upper] = item[key]
          //       delete item[key]
          //     }
          //   }
          //   return item
          // })
          console.log(rowHDBCADSL)
        }
        rowHDBCADSL.HDTB_ID = this.hdtb_id
        rowHDBCADSL.TOCDO_ID = this.thongso_tc.tocdo_id
        rowHDBCADSL.MUCCUOC_ID = this.thongso_tc.muccuoc_id
        rowHDBCADSL.THIETBIDC_ID = this.thongso_tc.thietbidc_id
        rowHDBCADSL.CHUQUAN_ID = this.thongso_tc.chuquan_id
        rowHDBCADSL.CHUQUANLD_ID = this.thongso_tc.chuquanld_id
        let input1 = {}
        input1.type = 3
        input1.param = this.thongso_tc.tocdo_id
        let response1 = await api.fn_tt_tocdo_adsl(this.axios, input1)
        if (response1.data.error_code === 'BSS-00000000' && response1.data.data) {
          if (response1.data.data != -1) rowHDBCADSL.PROFILEDSL_ID = response1.data.data
          else rowHDBCADSL.PROFILEDSL_ID = null
        }
        if (this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_MYTV) {
          rowHDBCADSL.KIEUTRA_ID = this.thongso_tc.kieutra_id
          if (this.thongso_tc.kieuld_id == this.KieuLapDat.THAYDOI_KIEUTRA_MYTV && this.cboKieuTraMoi.SelectedValue == this.KieuTra.TRATRUOC) {
            rowHDBCADSL.THANG_ID = this.cboThangTra.SelectedValue
            rowHDBCADSL.NGAYTRA = this.dtpNgayTra.Value
          }
        }

        if (this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
          rowHDBCADSL.CUOC_SD = Number(this.txtCuocSD.Text.trim())
          rowHDBCADSL.CUOC_TBI = Number(this.txtCuoc_TBI.Text.trim())
        }

        rowHDBCADSL.TRANGBI_ID = this.thongso_tc.trangbi_id

        rowHDBCADSL.IP = this.IP
        rowHDBCADSL.THONGTIN_TC = this.txtTTCuoc.Text.trim()
        console.log('checl loại hình: ', this.cboLoaihinhTB.SelectedValue)
        console.log('check tuc thì: ', this.chkTucThi.Checked)
        console.log('thongso_tc.kieuld_id: ', this.thongso_tc.kieuld_id)
        if (this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_MYTV || this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_SGTV) rowHDBCADSL.TUCTHI = 2 // Với mytv, saigontv thì thay đổi tức thì luôn.
        else {
          if (this.chkTucThi.Checked == false) rowHDBCADSL.TUCTHI = 0
          if (this.chkTucThi.Checked == true && this.thongso_tc.kieuld_id == this.KieuLapDat.THAYDOI_MUCCUOC_ADSL) rowHDBCADSL.TUCTHI = 1
          if (this.chkTucThi.Checked == true && (this.thongso_tc.kieuld_id == this.KieuLapDat.THAYDOI_TOCDO_ADSL || this.thongso_tc.kieuld_id == this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON || this.thongso_tc.kieuld_id == this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON)) {
            rowHDBCADSL.TUCTHI = 2
            console.log('check tuc thì: ', this.chkTucThi.Checked)
          }

          if (this.chkTucThi.Checked == true && this.thongso_tc.kieuld_id == this.KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL) rowHDBCADSL.TUCTHI = 3
          if (this.chkTucThi.Checked == true && (this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC)) rowHDBCADSL.TUCTHI = 4
        }

        if (this.thongso_tc.kieuld_id == this.KieuLapDat.THAYDOI_TOCDOTHUC_ADSL) {
          rowHDBCADSL.TOCDOTHUC_ID = thongso_tc.tocdothuc_id
          rowHDBCADSL.TUCTHI = 5 // Thay đổi tđ thực thì luôn tức thì
        }
        if (this.thongso_tc.kieuld_id == this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON || this.thongso_tc.kieuld_id == this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON) {
          rowHDBCADSL.CONGNGHE_ID = this.thongso_tc.congnghe_id
        }

        this.dsHDBCADSL.push(rowHDBCADSL)
        // them  de tesst
        this.chkTucThi.Checked = true
        this.chkTucThi.Enabled = true
      } catch (e) {
        console.log(e)
      }
    },
    async Tao_HDTB_CNTT() {
      try {
        let response = await api.lay_ds_danhba_cntt(this.axios, this.thuebao_id)
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          let dt = response.data.data[0]
          for (let i = 0; i < this.ItemList.length; i++) {
            for (let key of Object.keys(dt)) {
              for (let key1 of Object.keys(ItemList[i])) {
                if (key == key1 && key != 'MA_TB') {
                  ItemList[i][key1] = dt[key]
                }
                if (key1 == 'TOCDO_ID') {
                  ItemList[i][key1] = this.cboTocDoADSL.SelectedValue.ToString()
                }
              }
            }
          }
          this.dsHDTB_CNTT = this.ItemList
        }
      } catch (e) {
        console.log(e)
      }
    },

    SetButton(kieu) {
      this.$refs['txtMaHD'].focus()
      this.tsbtnNhapMoi.Enabled = false
      this.tsbtnGhiLai.Enabled = false
      this.tsbtnXoa.Enabled = false
      this.tsbtnHuyBo.Enabled = false
      this.tsbtnThemTB.Enabled = false
      this.tsbtnXoaTB.Enabled = false
      this.tsbtnThanhToan.Enabled = false

      this.btnPhieuYC.Enabled = false

      if (kieu == -1) {
        //Bat dau
        this.$refs['txtMaHD'].focus()
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
        this.chkCTV.Checked = false
        this.chkNguoiGT.Checked = false
        this.nguoi_gt.ten_nguoi_gt = ''
        this.ctv.ten_ctv = ''
      }

      if (kieu == 0) {
        //Bat dau
        this.$refs['txtMaHD'].focus()
        this.tsbtnNhapMoi.Enabled = true
        this.Clear()
      }

      if (kieu == 1) {
        //Them moi
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
        this.chkCTV.Checked = false
        this.chkNguoiGT.Checked = false
        this.nguoi_gt.ten_nguoi_gt = ''
        this.ctv.ten_ctv = ''
        this.Clear()
      }

      if (kieu == 2) {
        //Huy
        this.$refs['txtMaHD'].focus()
        this.tsbtnNhapMoi.Enabled = true
        this.tsbtnXoa.Enabled = true
        this.tsbtnThanhToan.Enabled = true
        this.Clear()
      }

      if (kieu == 3) {
        //Edit
        this.$refs['txtMaHD'].focus()

        this.tsbtnNhapMoi.Enabled = true
        this.tsbtnXoa.Enabled = true
        this.tsbtnThanhToan.Enabled = true
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
        this.tsbtnThemTB.Enabled = true
        if (this.lvwThueBao.list.length > 1) {
          this.tsbtnXoaTB.Enabled = true
        } else {
          this.tsbtnXoaTB.Enabled = false
        }

        //tsbtnXoaTB.Enabled = true;

        this.btnPhieuYC.Enabled = true
      }
    },
    numberWithCommas(n) {
      try {
        var parts = n.toString().split('.')
        return parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',') + (parts[1] ? '.' + parts[1] : '')
      } catch (e) {
        return n
      }
    },
    Clear() {
      console.log('Clear data')
      this.IP = ''
      this.trangbi_id = this.TRANGBI.KHACHHANG_TU_TRANGBI //default khách hàng tự trang bị
      this.phuongld_id = 0
      this.phold_id = 0
      this.apld_id = 0
      this.khuld_id = 0
      this.$refs['txtMaHD'].focus()

      this.hdkh_id = 0
      this.txtTenTB = ''
      this.txtMaGD = ''
      this.txtMaHD = ''
      this.txtMaKH = ''
      this.khachhang_id = 0
      this.txtTenKH = ''
      this.txtDiaChiKH = ''
      
      this.txtCuocSD.Text = 0
      this.txtCuoc_TBI.Text = 0
      this.dtpNgayLapHD = moment().format('DD/MM/YYYY HH:mm:ss')

     /*  if (this.cboTocDoADSL.list == []) {
        this.cboTocDoADSL.SelectedValue = null
      } */
      this.cboTocDoADSL.SelectedValue = null
      /* if (this.cboTocDoThuc_Moi.list == []) {
        this.cboTocDoThuc_Moi.SelectedValue = null
      } */
      this.cboTocDoThuc_Moi.SelectedValue = null

      this.cboMucCuocTB.SelectedValue = null
      this.cboTocDoCu.SelectedValue = null

      this.hdtb_id = 0
      this.thuebao_id = 0
      this.txtTenTB = ''
      this.diachi_tb = ''
      this.diachi_ld = ''

      this.dtpNgayYeuCau = moment().format('DD/MM/YYYY HH:mm:ss')
      this.txtNgaySN = ''

      this.chkTucThi.Checked = false
      this.chkTucThi.Enabled = false

      this.txtMaTB = ''

      //lvwThueBao.Items.Clear();

      /* if (this.cboKieuLD.list == []) {
        this.cboKieuLD.SelectedValue = null
      } */
      this.cboKieuLD.SelectedValue = null
      this.dtienld = 0
      this.dvatld = 0

      this.LayTienTheoKhoanMuc()
      this.txtTongTienHD = '0'
      this.txtGhiChu = ''
      this.matocdo = ''
      this.doituong_id = 1 //Default tư nhân
      this.txtTTCuoc.Text = ''
      this.txtTTCuocVisa = ''
      this.lvwThuebao.list = []
      try {
        this.nguoi_gt.ten_nguoi_gt = ''
        this.ctv.ten_ctv = ''
        this.nguoigt_id = 0
        this.ctv_id = 0
      } catch (Exception) {}
    },
    ChuanHoaTen(ten) {
      if (!ten) return ''
      let ten_new = ''
      let str_con = ten.trim()
      let str_xl = ''
      let k = 0
      let found = true
      if (str_con.length < 1) return
      str_con = str_con.replaceAll(/\s+/g, ' ')
      try {
        while (found) {
          k = str_con.indexOf(' ')
          if (k > 0) {
            str_xl = str_con.substring(0, k)
            str_con = str_con.substring(k + 1)
            str_xl = this.ChuyenChuHoa(str_xl)
            if (ten_new != '') {
              ten_new += ' ' + str_xl
            } else {
              ten_new = str_xl
            }
            found = true
          } else {
            found = false
          }
        }
        if (str_con != '') {
          str_xl = str_con
          str_xl = this.ChuyenChuHoa(str_xl)
          if (ten_new != '') {
            ten_new = ten_new + ' ' + str_xl
          } else {
            ten_new = str_xl
          }
        }
        return ten_new
      } catch (e) {
        return ten
      }
    },
    ChuyenChuHoa(str) {
      let kq = ''
      if (str.ismatch(/[A-Z][A-Z]/g)) {
        kq = str.toUpperCase()
      } else {
        if (str[0] == '(') {
          kq = str[0] + str[1].toUpperCase() + str.substring(2).toLowerCase()
        } else {
          kq = str[0].toUpperCase() + str.substring(1).toLowerCase()
        }
      }
      return kq
    },
    async LayTienTheoKhoanMuc() {
      try {
        if (this.dichvuvt_id != DichVuVienThong.ADSL) {
          this.kh_cd = 0
          this.ltb_adsl_id = 1
        }
        if (this.cboTocDoCu.SelectedValue != null) {
          this.tocdo_cu_id = Number(this.cboTocDoCu.SelectedValue)
        }
        if (this.cboTocDoADSL.SelectedIndex != null) {
          this.tocdo_moi_id = Number(this.cboTocDoADSL.SelectedValue)
        }
        let result = []
        let response = await this.LAY_TIEN_KHOANMUC_LD()
        if (response.data.error_code === 'BSS-00000000' && response.data.data && response.data.data != '') {
          result = response.data.data[0]
        }
        if (result.length > 0) {
          await this.HienThiTienLapDat(result)
          await this.LayTongTien_HDTB()
        }
      } catch (ex) {
        console.log(ex)
      }
    },
    HienThiTienLapDat(ds) {
      let kt_lm = true
      if (result[0].tien != '-1') {
        for (let i = 0; i < result.length; i++) {
          if (result[i].khoanmuctt_id.trim() == this.KHOANMUC_TT.KMTT_DOITOCDO) {
            this.dtienld = Number(result[i].tien)
            this.dvatld = Number(result[i].vat)
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

      for (let j = 0; j < this.ds_tien_khoanmuc.length; j++) {
        if (this.ds_tien_khoanmuc[j].KHOANMUCTT_ID == this.KHOANMUC_TT.KMTT_DOITOCDO) {
          this.ds_tien_khoanmuc[j].TIEN = this.dtienld
          this.ds_tien_khoanmuc[j].VAT = this.dvatld
        }
      }
    },
    LayTongTien_HDTB() {
      console.log('Tiền khoản mục: ', this.ds_tien_khoanmuc)
      try {
        let dtongtien = 0
        let dtongvat = 0
        for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          if (this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'] == this.KHOANMUC_TT.KMTT_KM_LAPDAT || this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'] == this.KHOANMUC_TT.KMTT_TIENTHOAI_DC_DOI_TD || this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'] == this.KHOANMUC_TT.KMTT_KM_DATCOC) {
            if (this.ds_tien_khoanmuc[i]['TIEN'] < 0) {
              dtongtien += this.ds_tien_khoanmuc[i]['TIEN']
              dtongvat += this.ds_tien_khoanmuc[i]['VAT']
            } else {
              dtongtien += this.ds_tien_khoanmuc[i]['TIEN'] * -1
              dtongvat += this.ds_tien_khoanmuc[i]['VAT'] * -1
            }
          } else if (this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'] != this.KHOANMUC_TT.KMTT_TRAGOP_THIETBI && this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'] != this.KHOANMUC_TT.KMTT_KM_THIETBI && this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'] != this.KHOANMUC_TT.KMTT_MUATHIETBI) {
            dtongtien += this.ds_tien_khoanmuc[i]['TIEN']
            dtongvat += this.ds_tien_khoanmuc[i]['VAT']
          }

          //dtongtien += Convert.ToDecimal(ds_tien_khoanmuc.Tables[0].Rows[i]["tien"]);
          //dtongvat += Convert.ToDecimal(ds_tien_khoanmuc.Tables[0].Rows[i]["vat"]);
        }
        this.txtTongTien = dtongtien
        this.txtTongVat = dtongvat
        this.lblTongTien = dtongtien + dtongvat
      } catch (ex) {
        console.log(ex)
        this.$toast.error(ex)
      }
    },
    Unformat_tien(textData) {
      let ketqua = ''
      for (let i = 0; i < textData.length; i++) {
        if (textData[i] != ',' && textData[i] != '.' && textData[i] != ' ') {
          ketqua = ketqua + textData[i]
        }
      }
      return ketqua.trim()
    },
    clearTheSpace(st) {
      let st1 = ''
      st = st.trim()
      while (st.length != 0) {
        st += ' '
        let i = st.indexOf(' ')
        st1 += st[0] + st.substring(1, i)
        st = st.substring(i + 1).trim()
      }
      return st1.trim()
    },
    Catdaunhay(str) {
      str = str.replace("'", '')
      return str
    },
    async loadform() {
      await this.frmBCThayDoiTocDoAdsl_FormLoad()
    },
    async frmBCThayDoiTocDoAdsl_FormLoad() {
      try {
        this.dtpNgayYeuCau = moment().format('DD/MM/YYYY HH:mm:ss')
        this.dtpNgayLapHD = moment().format('DD/MM/YYYY HH:mm:ss')
        this.dtpNgayTra.Text = moment().format('DD/MM/YYYY HH:mm:ss')
        this.SetButton(-1)
        await this.formload_frmbcthaydoitocdoadsl()
        await this.HT_DS_KhuyenMai_Combobox()
        this.kt_load = false
        if (this.Tag != null && this.Tag != '') {
          this.iKieu = this.Tag
        } else {
          this.iKieu = 0
        }
        this.visibleToolStrip(this.iKieu == 0)
        if (this.txtMaGD != '') {
          let result = []
          if (this.iKieu == 1) {
            result = await this.lay_ds_hopdong_theo_matb(0, 0)
          }
          //HienThiTTHopDongKH(new TCHopDongFacade().LAY_DS_HOPDONG_THEO_MA_TB(txtMaTB.Text, LoaiHopDong.THAY_DOI_TOCDO_ADSL, 0, 0, 0, 0, dichvuvt_id, 0));
          else if (this.iKieu == 2) {
            let idonvi_id = 0
            if (this.$root.token.getDonViDuLieuID() != 0) {
              idonvi_id = this.$root.token.getDonViID()
            }
            result = await this.lay_ds_hopdong_theo_matb(idonvi_id, 0)
            //HienThiTTHopDongKH(new TCHopDongFacade().LAY_DS_HOPDONG_THEO_MA_TB(txtMaTB.Text, LoaiHopDong.THAY_DOI_TOCDO_ADSL, idonvi_id, 0, 0, 0, dichvuvt_id, 0));
          } else {
            result = await this.lay_ds_hopdong_theo_matb(this.$root.token.getDonViID(), TrangThaiHD.MOI)
          }
          await this.HienThiTTHopDongKH(result)
          //HienThiTTHopDongKH(new TCHopDongFacade().LAY_DS_HOPDONG_THEO_MA_TB(txtMaTB.Text, LoaiHopDong.THAY_DOI_TOCDO_ADSL, (int)tt_nd.donvi_id, TrangThaiHD.MOI, (int)tt_nd.nhanvien_id, 0, dichvuvt_id, 0));
        }
      } catch (e) {
        console.log(e)
      }
    },
    async formload_frmbcthaydoitocdoadsl() {
      try {
        let response = await api.formload_frmbcthaydoitocdoadsl(this.axios, {
          ds_para: JSON.stringify({ LOAIHD_ID: LoaiHopDong.THAY_DOI_TOCDO_ADSL })
        })

        if (response.data.error_code === 'BSS-00000000' && response.data.data.RESULT) {
          this.result_loadform = response.data.data.RESULT
          this.ds_tien_khoanmuc = this.result_loadform.DS_TIEN_KMTT

          this.cboDichVuVT.list = this.result_loadform.DS_DVVT
          this.cboDichVuVT.SelectedValue = this.cboDichVuVT.list[0].DICHVUVT_ID
          await this.cboDichVuVT_SelectedValueChanged()

          this.cboMucCuocMoi.list = this.result_loadform.DS_MUCCUOC
          this.cboMucCuocMoi.SelectedValue = this.cboMucCuocMoi.list[0].MUCCUOC_ID
          await this.cboMucCuocMoi_SelectedValueChanged()

          this.cboMucCuocCu.list = this.result_loadform.DS_MUCCUOC
          this.cboMucCuocCu.SelectedValue = this.cboMucCuocCu.list[0].MUCCUOC_ID

          this.cboLoaiTBiADSL.list = this.result_loadform.DS_LOAI_TBI
          this.cboLoaiTBiADSL.SelectedValue = this.cboLoaiTBiADSL.list[0].THIETBIDC_ID
          await this.cboLoaiTBiADSL_SelectedValueChanged()

          this.cboKieuTraMoi.list = this.result_loadform.DS_KIEUTRA
          this.cboKieuTraMoi.SelectedValue = this.cboKieuTraMoi.list[0].KIEUTRA_ID
          await this.cboKieuTraMoi_SelectedValueChanged()

          this.cboKieuTraCu.list = this.result_loadform.DS_KIEUTRA
          this.cboKieuTraCu.SelectedValue = this.cboKieuTraCu.list[0].KIEUTRA_ID

          this.cboThangTra.list = this.result_loadform.DS_THANGTRA
          this.cboThangTra.SelectedValue = this.cboThangTra.list[0].THANG_ID
          await this.cboThangTra_SelectedValueChanged()

          this.cboDoiTuongMoi.list = this.result_loadform.DS_DOITUONG
          this.cboDoiTuongMoi.SelectedValue = this.cboDoiTuongMoi.list[0].DOITUONG_ID
          await this.cboDoiTuongMoi_SelectedValueChanged()

          this.cboDoiTuongCu.list = this.result_loadform.DS_DOITUONG
          this.cboDoiTuongCu.SelectedValue = this.cboDoiTuongCu.list[0].DOITUONG_ID

          this.cboCongNghe_Cu.list = this.result_loadform.DS_CONGNGHECU
          this.cboCongNghe_Cu.SelectedValue = this.cboCongNghe_Cu.list[0].CONGNGHE_ID

          this.cboCongNghe_Cu.list = this.result_loadform.DS_CONGNGHEMOI
          this.cboCongNghe_Cu.SelectedValue = this.cboCongNghe_Cu.list[0].CONGNGHE_ID

          this.cboTrangBiCu.list = this.result_loadform.DS_TRANGBI_CU
          this.cboTrangBiCu.SelectedValue = this.cboTrangBiCu.list[0].TRANGBI_ID

          this.cboTrangBiMoi.list = this.result_loadform.DS_TRANGBI_MOI
          this.cboTrangBiMoi.SelectedValue = this.cboTrangBiMoi.list[0].TRANGBI_ID
          this.result_loadform.DS_TS_MD = this.result_loadform.DS_TS_MD.map(function (item) {
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
          this.sp_lay_ds_thamso_md(this.result_loadform.DS_TS_MD)
        } else {
        }
      } catch (e) {
        console.log(e)
      }
    },

    async HT_DS_KhuyenMai_Combobox() {
      try {
        let itocdo_id = 0
        let itrangbi_id = 0 //Default: KH tự trang bị
        let imuccuoc_id = 0
        let iloaitb_id = 0
        if (this.cboKieuLD.length <= 0) {
          return
        }
        if (this.cboDoiTuongMoi.length <= 0) {
          return
        }
        if (this.cboTocDoADSL.SelectedValue != null) {
          itocdo_id = Number(this.cboTocDoADSL.SelectedValue)
        }
        if (this.cboMucCuocMoi.SelectedValue != null) {
          imuccuoc_id = Number(this.cboMucCuocMoi.SelectedValue)
        }
        if (this.cboLoaihinhTB.SelectedValue != null) {
          iloaitb_id = Number(this.cboLoaihinhTB.SelectedValue)
        }
        let response = await api.fn_lay_tt_ds_khuyenmai_cdlhtb(this.axios, {
          p_ds_para: JSON.stringify({
            LOAITB_ID: iloaitb_id,
            KIEULD_ID: this.cboKieuLD.SelectedValue,
            PHUONGLD_ID: this.phuongld_id,
            PHOLD_ID: this.phold_id,
            APLD_ID: this.apld_id,
            KHULD_ID: this.khuld_id,
            TRANGBI_ID: itrangbi_id,
            DOITUONG_ID: this.cboDoiTuongMoi.SelectedValue,
            TOCDO_ID: itocdo_id,
            MUCCUOC_ID: imuccuoc_id
          })
        })
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          let resultData = JSON.parse(response.data.data)

          this.cboKhuyenMai.list = resultData.RESULT.KHUYENMAI
          this.cboTraTruoc.list = resultData.RESULT.DATCOC
          if (this.cboKhuyenMai.list.length == 0) {
            this.chkKhuyenMai.Enabled = false
          } else {
            this.cboKhuyenMai.SelectedValue = this.cboKhuyenMai.list[0].KHUYENMAI_ID
            this.chkKhuyenMai.Enabled = true
          }
          this.chkKhuyenMai.Checked = false
          //longdx: bo sung ham lay thong tin tra truoc
          if (this.cboTraTruoc.list.length == 0) {
            this.chkTraTruoc.Enabled = false
          } else {
            this.cboTraTruoc.SelectedValue = this.cboTraTruoc.list[0].KHUYENMAI_ID
            this.chkTraTruoc.Enabled = true
          }
          this.chkTraTruoc.Checked = false
        }
      } catch (e) {
        console.log(e)
      }
    },
    async lay_ds_hopdong_theo_matb(donViDlId, ttHdId) {
      let result = []
      try {
        let response = await api.lay_ds_hopdong_theo_matb(this.axios, {
          dichVuVtId: this.cboDichVuVT.SelectedValue,
          donViDlId: donViDlId,
          loaiHdId: LoaiHopDong.THAY_DOI_TOCDO_ADSL,
          loaiTbId: 0,
          maTb: this.txtMaTB,
          ttHdId: ttHdId
        })
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
        return result
      } catch (e) {
        console.log(e)
        return result
      }
    },

    HienThiDanhSacHDTB(dsHDTB) {
      dsHDTB = dsHDTB.map(function (item) {
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
      this.lvwThueBao.list = []
      let tongtienhd = 0
      let tongtientb = 0
      if (dsHDTB.length > 0) {
        let result = []
        for (let i = 0; i < dsHDTB.length; i++) {
          dsHDTB[i]['stt'] = i + 1
          if (dsHDTB[i]['tthd_id'] == TrangThaiHD.MOI || this.iKieu == 2) {
            result.push(dsHDTB[i])
            tongtientb = dsHDTB[i]['tongtien']
            tongtienhd = tongtienhd + tongtientb
          }
        }
        this.lvwThueBao.list = result
        if (this.lvwThueBao.list.length > 1) {
          this.tsbtnXoaTB.Enabled = true
        } else {
          this.tsbtnXoaTB.Enabled = false
        }
      }
      this.txtTongTienHD = this.numberWithCommas(tongtienhd)
    },
    async HienThiTTHopDongTB(dataSet) {
      try {
        console.log('HienThiTTHopDongTB')
        dataSet = dataSet.map(function (item) {
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
        //let dataSet = resultData.LAY_DS_HOPDONG_TB_THEO_HDKH_ID;
        this.hdtb_id = dataSet[0]['hdtb_id']
        this.thuebao_id = dataSet[0]['thuebao_id']
        console.log(dataSet)
        await this.lay_tt_gan_thongso_bancheo_hdtb()
        this.txtMaTB = dataSet[0]['ma_tb']
        this.txtTenTB = this.ChuanHoaTen(dataSet[0]['ten_tb'])
        this.diachi_tb = this.ChuanHoaTen(dataSet[0]['diachi_tb'])
        this.diachi_ld = this.ChuanHoaTen(dataSet[0]['diachi_ld'])
        this.cboDichVuVT.SelectedValue = dataSet[0]['dichvuvt_id']
        await this.cboDichVuVT_SelectedValueChanged()
        this.cboLoaihinhTB.SelectedValue = Number(dataSet[0]['loaitb_id'])
        await this.cboLoaihinhTB_SelectedValueChanged()
        this.cboKieuLD.SelectedValue = dataSet[0]['kieuld_id']

        await this.cboKieuLD_SelectedValueChanged()
        this.cboDoiTuongMoi.SelectedValue = dataSet[0]['doituong_id']
        await this.cboDoiTuongMoi_SelectedValueChanged()
        this.txtGhiChu = dataSet[0]['ghichu']
        this.donvi_id = Number(dataSet[0]['donvi_id'])
        this.cboTrangBiMoi.SelectedValue = Number(dataSet[0]['trangbi_id'])
        this.cboTrangBiCu.SelectedValue = Number(dataSet[0]['trangbi_id_cu'])
        this.txtSoThangCK = Number(dataSet[0]['sothang_ck'])
        if (dataSet[0]['LOAITB_ID'] == LoaiHinhTB.INTERNET_ADSL || dataSet[0]['loaitb_id'] == LoaiHinhTB.INTERNET_FTTH || dataSet[0]['LOAITB_ID'] == LoaiHinhTB.WIFI_FIBER) {
          let ngay_yc = new Date(moment(this.dtpNgayYeuCau, 'DD/MM/YYYY HH:mm:ss').format('YYYY-MM-DD HH:mm:ss')).getDate()

          if (ngay_yc <= this.ngaytucthi) {
            this.chkTucThi.Enabled = true
          } else {
            this.chkTucThi.Enabled = false
            this.chkTucThi.Checked = false
          }
        } else {
          this.chkTucThi.Enabled = false
          this.chkTucThi.Checked = true
        }
        await this.hienthitthopdongtb_frmbcthaydoitocdoadsl(dataSet[0]['mucuoctb_id'])
      } catch (e) {
        console.log(e)
      }
    },
    async hienthitthopdongtb_frmbcthaydoitocdoadsl(mucuoctb_id) {
      try {
        let response = await api.hienthitthopdongtb_frmbcthaydoitocdoadsl(this.axios, {
          ds_para: JSON.stringify({ THUEBAO_ID: this.thuebao_id, HDTB_ID: this.hdtb_id })
        })
        if (response.data.error_code === 'BSS-00000000' && response.data.data && response.data.data.RESULT) {
          let result = response.data.data.RESULT
          if (result.DS_DIACHI.length > 0) {
            result.DS_DIACHI = result.DS_DIACHI.map(function (item) {
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
            this.phuongld_id = result.DS_DIACHI[0].phuongld_id == null ? 0 : result.DS_DIACHI[0].phuongld_id
            this.phold_id = result.DS_DIACHI[0].phold_id == null ? 0 : result.DS_DIACHI[0].phold_id
            this.apld_id = result.DS_DIACHI[0].apld_id == null ? 0 : result.DS_DIACHI[0].apld_id
            this.khuld_id = result.DS_DIACHI[0].khuld_id == null ? 0 : result.DS_DIACHI[0].khuld_id
          } else {
            this.phuongld_id = 0
            this.phold_id = 0
            this.apld_id = 0
            this.khuld_id = 0
          }

          await this.Lay_DS_MucCuoc_TB_V2(this.khuld_id, this.apld_id, this.phold_id, this.phuongld_id, this.loaitb_id, this.doituong_id, moment(this.dtpNgayYeuCau, 'DD/MM/YYYY HH:ss:mm').format('DD/MM/YYYY'), this.cboTrangBiMoi.SelectedValue)
          if (mucuoctb_id != '' && mucuoctb_id != null) {
            this.cboMucCuocTB.SelectedValue = mucuoctb_id
            await this.cboMucCuocTB_SelectedValueChanged()
          } else {
            this.cboMucCuocTB.SelectedValue = null
          }
          this.cboDoiTuongCu.SelectedValue = result.DOITUONG_ID
          this.thuebao_id_tc = result.THUEBAO_ID_TC
          let ds_HTDB_ADSL = result.DS_HTDB_ADSL
          ds_HTDB_ADSL = ds_HTDB_ADSL.map(function (item) {
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
          console.log(ds_HTDB_ADSL)
          if (this.loaitb_id == LoaiHinhTB.INTERNET_ADSL || this.loaitb_id == LoaiHinhTB.INTERNET_FTTH || this.loaitb_id == LoaiHinhTB.WIFI_FIBER) {
            this.btnThongTinTC.Enabled = true
            this.txtTTCuoc.Enabled = true
            this.txtTTCuoc.Text = ds_HTDB_ADSL[0]['thongtin_tc']
          } else {
            this.btnThongTinTC.Enabled = false
            this.txtTTCuoc.Enabled = false
            this.txtTTCuoc.Text = ''
          }
          if (this.loaitb_id == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || this.loaitb_id == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
            this.txtCuoc_TBI.Enabled = true
            this.txtCuocSD.Enabled = true
            if (ds_HTDB_ADSL[0]['cuoc_tbi'] != '') {
              this.txtCuoc_TBI.Text = this.numberWithCommas(Number(ds_HTDB_ADSL[0]['cuoc_tbi']))
            } else {
              this.txtCuoc_TBI.Text = '0'
            }

            if (ds_HTDB_ADSL[0]['cuoc_sd'] != '') {
              this.txtCuocSD.Text = this.numberWithCommas(Number(ds_HTDB_ADSL[0]['cuoc_sd']))
            } else {
              this.txtCuocSD.Text = '0'
            }
          } else {
            this.txtCuoc_TBI.Enabled = false
            this.txtCuocSD.Enabled = false
            this.txtCuoc_TBI.Text = '0'
            this.txtCuocSD.Text = '0'
          }
          this.cboTocDoADSL.SelectedValue = ds_HTDB_ADSL[0]['tocdo_id']
          await this.cboTocDoADSL_SelectedValueChanged()
          this.cboMucCuocMoi.SelectedValue = ds_HTDB_ADSL[0]['muccuoc_id']
          await this.cboMucCuocMoi_SelectedValueChanged()
          this.cboTocDoThuc_Moi.SelectedValue = ds_HTDB_ADSL[0]['tocdothuc_id'] == null ? ds_HTDB_ADSL[0]['tocdo_id'] : ds_HTDB_ADSL[0]['tocdothuc_id']
          this.cboKieuTraMoi.SelectedValue = ds_HTDB_ADSL[0]['kieutra_id'] == null ? null : ds_HTDB_ADSL[0]['kieutra_id']
          await this.cboKieuTraMoi_SelectedValueChanged()
          this.cboCongNghe_Moi.SelectedValue = ds_HTDB_ADSL[0]['congnghe_id'] == null ? 0 : ds_HTDB_ADSL[0]['congnghe_id']
          this.IP = ds_HTDB_ADSL[0]['ip']
          this.Bras_id = ds_HTDB_ADSL[0]['bras_id'] == null ? '' : ds_HTDB_ADSL[0]['bras_id']
          if (ds_HTDB_ADSL[0]['tucthi'] != '' && ds_HTDB_ADSL[0]['tucthi'] != '0' && ds_HTDB_ADSL[0]['tucthi'] != null) {
            this.chkTucThi.Checked = true
          }

          await this.HT_DS_KhuyenMai_Combobox()

          //Xử lý KM
          this.dsKM_HDTB = result.DS_CTKM.map(function (item) {
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
          if (this.dsKM_HDTB.length > 0) {
            if (this.dsKM_HDTB[0]['khuyenmai_id'] != null) {
              this.cboKhuyenMai.SelectedValue = this.dsKM_HDTB[0]['khuyenmai_id']
              this.chkKhuyenMai.Checked = false
            } else {
              this.chkKhuyenMai.Checked = true
            }
          } else {
            this.dsKM_HDTB = []
            this.chkKhuyenMai.Checked = true
          }
          //Xử lý trả trước
          this.dsDC_HDTB = result.DS_CTTT.map(function (item) {
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
          if (this.dsDC_HDTB.length > 0) {
            if (this.dsDC_HDTB[0]['khuyenmai_id'] != null) {
              this.cboTraTruoc.SelectedValue = dsDC_HDTB[0]['khuyenmai_id']
              this.chkTraTruoc.Checked = false
            } else {
              this.chkTraTruoc.Checked = true
            }
          } else {
            this.dsDC_HDTB = []
            this.chkTraTruoc.Checked = true
          }

          //Xử lý khoản mục
          this.ds_tien_khoanmuc = result.DS_KMTT.map(function (item) {
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
          this.ds_tien_khoanmuc.forEach((obj) => {
            if (obj.khoanmuctt_id == this.KHOANMUC_TT.KMTT_DOITOCDO) {
              this.dtienld = Number(obj['tien'])
              this.dvatld = Number(obj['vat'])
            }
            if (obj.khoanmuctt_id == this.KHOANMUC_TT.KMTT_DATCOC) {
              this.dtiendatcoc_tt = Number(obj['tien'])
              this.dvatdatcoc_tt = Number(obj['vat'])
            }
            if (obj.khoanmuctt_id == this.KHOANMUC_TT.KMTT_DATCOC_KM) {
              this.dtiendatcoc_km = Number(obj['tien'])
              this.dvatdatcoc_km = Number(obj['vat'])
            }
          })
          this.LayTongTien_HDTB()

          //Xử lý DS_CU
          let ds_cu = result.DS_CU.map(function (item) {
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
          if (ds_cu.length > 0) {
            this.cboTocDoCu.SelectedValue = ds_cu[0]['tocdo_id']
            console.log('Tốc độ cũ 1: ', this.cboTocDoCu.SelectedValue)
            await this.cboTocDoCu_SelectedValueChanged()
            this.cboMucCuocCu.SelectedValue = ds_cu[0]['muccuoc_id']
            if (ds_cu[0]['kieutra_id'] != null && ds_cu[0]['kieutra_id'] != '') {
              this.cboKieuTraCu.SelectedValue = ds_cu[0]['kieutra_id']
            }
            if (ds_cu[0]['mucuoctb_id'] != null && ds_cu[0]['mucuoctb_id'] != '') {
              this.cboMucCuocTBCu.list = result.DS_MUCCUOC
              this.cboMucCuocTBCu.SelectedValue = this.cboMucCuocTBCu.list[0].mucuoctb_id
            }

            if (this.dichvuvt_id == DichVuVienThong.ADSL) {
              if (this.cboKieuLD.SelectedValue == this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON || this.cboKieuLD.SelectedValue == this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON) {
                if (ds_cu[0]['congnghe_id'] != '' && ds_cu[0]['congnghe_id'] != null) {
                  this.cboCongNghe_Cu.SelectedValue = ds_cu[0]['congnghe_id']
                } else {
                  this.cboCongNghe_Cu.SelectedValue = 0
                }
              } else {
                this.cboCongNghe_Cu.SelectedValue = 0
              }
            }
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    async lay_tt_gan_thongso_bancheo_hdtb() {
      try {
        console.log('lay_tt_gan_thongso_bancheo_hdtb')
        let response = await api.lay_tt_gan_thongso_bancheo_hdtb(this.axios, {
          ds_para: JSON.stringify({ HDTB_ID: this.hdtb_id })
        })
        console.log('gán thông số bán chéo: ', response)
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          if (response.data.data.RESULT.DS_TS_TB > 0) {
            this.Gan_thongso_bancheo_hdtb(response.data.data.RESULT.DS_TS_TB[0])
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    Gan_thongso_bancheo_hdtb(drTHONGSO) {
      let dvvt_id = drTHONGSO['DICHVUVT_ID']
      console.log(drTHONGSO)
      if (dvvt_id != DichVuVienThong.HOINGHI_TRUYENHINH && dvvt_id != DichVuVienThong.ANTOAN_BAOMAT_TT && dvvt_id != DichVuVienThong.DICHVU_CNTT && dvvt_id != DichVuVienThong.TRUNGTAM_DULIEU) {
        console.log('Set giá trị bán chéo')
        this.thongso_ban.chuquanld_id = drTHONGSO.CHUQUANLD_BAN
        this.thongso_ban.congnghe_id = drTHONGSO.CONGNGHE_BAN
        this.thongso_ban.loaikenh_id = drTHONGSO.LOAIKENH_BAN
        this.thongso_ban.loaimd_id = drTHONGSO.LOAIMD_BAN
        this.thongso_ban.ma_doicap = drTHONGSO.MADOICAP_BAN
        this.thongso_ban.chuquan_id = drTHONGSO.CHUQUAN_BAN
        this.thongso_ban.ma_tn = drTHONGSO.MATN_BAN
        this.thongso_ban.thietbidc_id = drTHONGSO.THIETBIDAU_BAN
        this.thongso_ban.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_BAN
        this.thongso_ban.thoihan_id = drTHONGSO.THOIHAN_BAN
        this.thongso_ban.tocdo_id = drTHONGSO.TOCDO_BAN
        this.thongso_ban.tocdo_isp = drTHONGSO.TOCDOISP_BAN
        this.thongso_ban.tocdo_nix = drTHONGSO.TOCDONIX_BAN
        this.thongso_ban.tocdo_pir_id = drTHONGSO.TOCDOPIR_BAN
        this.thongso_ban.phanloai_id = drTHONGSO.PHANLOAI_ID
        this.thongso_ban.phantach_id = drTHONGSO.PHANTACH_TD
        this.thongso_ban.tocdothuc_id = drTHONGSO.TOCDOTHUC_BAN
        this.thongso_ban.trangbi_id = drTHONGSO.TRANGBI_BAN

        this.thongso_tc.chuquan_id = drTHONGSO.CHUQUAN_TC
        this.thongso_tc.chuquanld_id = drTHONGSO.CHUQUANLD_TC
        this.thongso_tc.congnghe_id = drTHONGSO.CONGNGHE_TC
        this.thongso_tc.loaikenh_id = drTHONGSO.LOAIKENH_TC
        this.thongso_tc.loaimd_id = drTHONGSO.LOAIMD_TC
        this.thongso_tc.ma_doicap = drTHONGSO.MADOICAP_TC
        this.thongso_tc.ma_tn = drTHONGSO.MATN_TC

        this.thongso_tc.thietbidc_id = drTHONGSO.THIETBIDAU_TC
        this.thongso_tc.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_TC
        this.thongso_tc.thoihan_id = drTHONGSO.THOIHAN_TC
        this.thongso_tc.tocdo_id = drTHONGSO.TOCDO_TC
        this.thongso_tc.tocdo_isp = drTHONGSO.TOCDOISP_TC
        this.thongso_tc.tocdo_nix = drTHONGSO.TOCDONIX_TC
        this.thongso_tc.tocdo_pir_id = drTHONGSO.TOCDOPIR_TC
        this.thongso_tc.tocdothuc_id = drTHONGSO.TOCDOTHUC_TC
        this.thongso_tc.trangbi_id = drTHONGSO.TRANGBI_TC
      }
      this.thongso_ban.muccuoc_id = drTHONGSO.MUCCUOC_BAN
      this.thongso_ban.muccuoctb_id = drTHONGSO.MUCCUOCTB_BAN
      this.thongso_ban.kieuld_id = drTHONGSO.KIEULD_BAN
      this.thongso_ban.doituong_id = drTHONGSO.DOITUONG_BAN
      this.thongso_tc.kieuld_id = drTHONGSO.KIEULD_TC
      this.thongso_tc.doituong_id = drTHONGSO.DOITUONG_TC
      this.thongso_tc.muccuoc_id = drTHONGSO.MUCCUOC_TC
      this.thongso_tc.muccuoctb_id = drTHONGSO.MUCCUOCTB_TC

      this.thongso_tc.kieutra_id = drTHONGSO.KIEUTRA_TC
      this.thongso_ban.kieutra_id = drTHONGSO.KIEUTRA_BAN
      console.log('Thông số bán: ', this.thongso_ban)
    },
    async LoadTuHopDongTuVan(phdkh_cha_id, pctv_id) {
      let response = await api.lay_thongtin_hd_tuvan(this.axios, {
        hdkh_cha_id: phdkh_cha_id,
        ctv_id: pctv_id
      })
      if (response.data.error_code === 'BSS-00000000' && response.data.data && response.data.data.RESULT) {
        let reuslt = response.data.data.RESULT
        this.hdkh_cha_id = phdkh_cha_id
        this.txtTenKH = reuslt['TEN_KH']
        this.txtTenTB = reuslt['TEN_KH']
        this.txtDiaChiKH = reuslt['DIACHI_KH']
        if (result.KHACHHANG_ID != null && result.KHACHHANG_ID != 0 && result.KHACHHANG_ID != '') {
          this.khachhang_id = result['KHACHHANG_ID']
          this.txtMaTB = result['MA_TB']
          this.$refs.txtMaTB.focus()
          this.txtMaTB_KeyPress()
          this.chkCTV.Checked = true
          this.ctv_id = pctv_id
          this.ctv.ten_ctv = result['TXT_CTV']
        }
      }
    },
    async txtMaTB_KeyPress() {
      try {
        this.loading(true)
        this.kiemtra = false
        this.txtMaTB = this.clearTheSpace(this.txtMaTB)
        this.txtMaTB = this.Catdaunhay(this.txtMaTB)
        if (this.txtMaTB.trim() != '') {
          let response = await api.tk_ma_tb_frmbcthaydoitocdoadsl(this.axios, {
            ds_para: JSON.stringify({
              TXT_MA_TB: this.txtMaTB,
              STEP: null,
              TTND_DONVI: this.$root.token.getDonViID(),
              TTND_NV_ID: this.$root.token.getNhanVienID(),
              DICHVUVT_ID: this.cboDichVuVT.SelectedValue
            })

            // "ds_para": JSON.stringify({"TXT_MA_TB":"ABC","STEP":null,"TTND_DONVI":12,"TTND_NV_ID":123,"DICHVUVT_ID":123})
          })
          if (response.data.error_code === 'BSS-00000000' && response.data.data) {
            if (response.data.data.ERROR_CODE == -2) {
              await this.$confirm(response.data.data.MESSAGE, {
                confirmButtonText: 'Có',
                cancelButtonText: 'Không',
                type: 'warning'
              })
                .then(async () => {
                  await this.HienThiTT_DanhBa(this.txtMaTB)
                })
                .catch(async () => {})
            }
            if (response.data.data.ERROR_CODE == -1) {
              this.$toast.error(response.data.data.MESSAGE)
            }
            if (response.data.data.ERROR_CODE == 1) {
              let resultData = response.data.data.RESULT
              console.log(resultData)
              this.kiemtra = true
              this.tinh_tb_id = response.data.data.RESULT.TINH_TB_ID

              if (response.data.data.RESULT.DS_HD.length > 0) {
                let ds = response.data.data.RESULT.DS_HD.map(function (item) {
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
                await this.HienThiTTHopDongKH(ds)
                this.KieuLD_click = true
              } else {
                await this.HienThiTT_DanhBa(this.txtMaTB)
              }
              let _kieum_mega = await this.check_map_id()
              if ((this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_ADSL || this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_FTTH) && _kieum_mega == 1) {
                if (this.chkTTTCVisa.Checked) {
                  this.HienThi_TTTC_Visa()
                }
              }
            }
          }
        }
        this.loading(false)
      } catch (e) {
        this.loading(false)
        this.$toast.error('Không có thông tin')
        console.log(e)
      }
    },
    async HienThiTTHopDongKH(dataReponse) {
      try {
        console.log('HienThiTTHopDongKH')
        // if (response.length > 0) {
        let result = dataReponse[0]
        this.hdkh_id = result.hdkh_id
        this.txtMaGD = result.ma_gd
        this.txtMaHD = result['ma_hd']
        this.txtMaKH = result['ma_kh']
        if (result['khachhang_id'] != '') {
          this.khachhang_id = Number(result['khachhang_id'])
        }
        this.txtTenKH = this.ChuanHoaTen(result['ten_kh'])
        this.txtDiaChiKH = this.ChuanHoaTen(result['diachi_kh'])
        this.dtpNgayLapHD = result['ngaylap_hd']
        this.dtpNgayYeuCau = result['ngay_yc']
        //Hiếu bổ sung ngày 24/08/2010
        if (result['ngay_sn'] != '' && result['ngay_sn'] != null) {
          this.txtNgaySN = result['ngay_sn']
        } else {
          this.txtNgaySN = ''
        }

        this.ctv_id = result['ctv_id']
        this.nhanviengt_id = result['nhanviengt_id']
        let response = await api.hienthitthopdongkh_frmbcthaydoitocdoadsl(this.axios, {
          ds_para: JSON.stringify({
            HDKH_ID: this.hdkh_id,
            HDTB_ID: this.hdtb_id,
            DICHVUVT_ID: this.cboDichVuVT.SelectedValue,
            CTV_ID: this.ctv_id == 0 ? null : this.ctv_id,
            NHANVIENGT_ID: this.nhanviengt_id == 0 ? null : this.nhanviengt_id
          })
        })
        let resultData = []
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          resultData = response.data.data.RESULT
          if (this.ctv_id != null && this.ctv_id != '') {
            this.chkCTV.Checked = true
            this.ctv.ten_ctv = resultData.TXTCTV
          } else {
            this.ctv.ten_ctv = ''
            this.chkCTV.Checked = false
          }

          if (this.nguoigt_id != null && this.nguoigt_id != '') {
            this.chkNguoiGT.Checked = true
            this.nguoi_gt.ten_nguoi_gt = resultData.TXTNGUOIGT
          } else {
            this.nguoi_gt.ten_nguoi_gt = ''
            this.chkNguoiGT.Checked = false
          }
        }
        await this.HienThiTTHopDongTB(resultData.LAY_DS_HOPDONG_TB_THEO_HDKH_ID)
        await this.HienThiDanhSacHDTB(resultData.lay_ds_hdtb_adsl_theo_hdtb_id)
        this.SetButton(3)
        //SendKeys.Send("{TAB}");
        // } else {
        //   this.SetButton(0)
        // }
        // }
      } catch (e) {
        console.log(e)
      }
    },
    async HienThi_TTTC_Visa() {
      try {
        let tc = await this.traCuuThueBao_vdc(this.txtMaTB)
        if (tc.length <= 1) {
          return
        }
        let accountServiceModel = tc[2]
        let logObject = tc[4]

        let list = await this.get_vdc_list_attr()

        //Thông tin chi tiết dịch vụ
        //Hiện chưa ánh xạ được serviceID của hệ thống VDC

        let attribute = accountServiceModel.attributes

        for (let i = 0; i < attribute.length; i++) {
          let result = list.find((x) => x.key == attribute[i].serviceId)

          if (result != null) {
            attribute[i].type = result.NAME
          }
          if (attribute[i].value != null && attribute[i].value.includes('mienip')) {
            //"STRBILLING")
            this.txtTTCuocVisa = attribute[i].value
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    async traCuuThueBao_vdc(txtMaTB) {
      let result = []
      console.log('Vào đây')
      try {
        let response = await api.traCuuThueBao_vdc(this.axios, txtMaTB)
        if (response.data.error_code === 'BSS-00000000' && response.data.data != null) {
          if (response.data.data.errorCode) {
            this.$toast.error(response.data.data.faultString)
            return result
          }
          result = response.data.data.data.lookupRASAccountReturn
        }
        return result
      } catch (e) {
        console.log(e)
        return result
      }
    },
    async HienThiTT_DanhBa(txtMaTB) {
      try {
        this.KieuLD_click = false
        let response = await api.hienthitt_danhba_frmbcthaydoitocdoadsl(this.axios, {
          ds_para: JSON.stringify({
            MA_TB: txtMaTB,
            TINHTHICONG_ID: this.tinh_tb_id,
            STEP: '',
            DICHVUVT_ID: this.cboDichVuVT.SelectedValue,
            TEN_DVVT: this.cboDichVuVT.list.filter((x) => x.DICHVUVT_ID == this.cboDichVuVT.SelectedValue)[0].TEN_DVVT,
            THUEBAO_ID: null,
            MUCCUOC_ID: null
          })
        })
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          let ds = []
          let check_km = true
          let ds_muccuoc = response.data.data.RESULT.DS_MUCCUOC
          let ds_diachi = response.data.data.RESULT.DS_DIACHI

          this.dsDBTBBC = response.data.data.DS_DB
          if (response.data.data.ERROR_CODE == 0) {
            this.$toast.error(response.data.data.MESSAGE)
            return
          }
          if (response.data.data.ERROR_CODE == -1) {
            this.$toast.error(response.data.data.MESSAGE)
            return
          }
          if (response.data.data.ERROR_CODE == 1) {
            ds = response.data.data.RESULT.DS_TB_CUNGMA
          }
          if (response.data.data.ERROR_CODE == -2) {
            //call popup
          }
          ds = ds.map(function (item) {
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
          if (ds[0]['trangthaitb_id'] != TrangThaiTB.THUONG) {
            this.kiemtra = false
            this.$toast.error('Thuê bao có mã:' + ds[0]['ma_tb'] + ' đang ở trạng thái: ' + ds[0]['trangthai_tb'] + '\n Bạn không thể thực hiện thay đổi tốc độ cho thuê bao này')
            return
          }
          if (ds[0]['ky_hd'] == 0) {
            this.kiemtra = false
            this.$toast.error('Khách hàng có Account:' + txtMaTB + ' chưa ký lại hợp đồng gốc \nĐề nghị ký lại hợp đồng gốc')
            return
          }
          let ds_km = response.data.data.RESULT.DS_KM
          if (ds_km.length > 0) {
            ds_km = ds_km.map(function (item) {
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
            let str = 'Thuê bao ' + ds[0]['ma_tb']
            str += ' đang hưởng khuyến mại : \n ' + ds_km[0]['ten_km']
            str += ' \n    Có chi tiết khuyến mại : '
            for (let i = 0; i < ds_km.length; i++) {
              str += '\n     - ' + ds_km[0]['ten_ctkm']
            }
            str += '\nBạn có muốn tiếp tục thực hiện?'
            await this.$confirm(str, {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không',
              type: 'warning'
            })
              .then(async () => {
                check_km = false
              })
              .catch(async () => {
                check_km = true
              })
          } else check_km = false
          if (check_km) {
            return
          }

          this.txtMaKH = ds[0]['ma_kh']
          this.txtMaHD = ds[0]['ma_hd']
          this.khachhang_id = ds[0]['khachhang_id']
          this.txtTenKH = this.ChuanHoaTen(ds[0]['ten_kh'])
          this.txtDiaChiKH = this.ChuanHoaTen(ds[0]['diachi_kh'])
          this.plkhachhang_id = ds[0]['phanloaikh_id']
          if (ds[0]['ngay_sn'] != '' && ds[0]['ngay_sn'] != null) {
            this.txtNgaySN = moment(ds[0]['ngay_sn'], 'YYYY-MM-DD[T]HH:mm:ss').format('DD/MM/YYYY')
          }
          this.thuebao_id = ds[0]['thuebao_id']
          this.thuebao_id_tc = response.data.data.RESULT.THUEBAO_KN_ID
          this.txtTenTB = this.ChuanHoaTen(ds[0]['ten_tb'])
          this.diachi_tb = this.ChuanHoaTen(ds[0]['diachi_tb'])
          this.diachi_ld = this.ChuanHoaTen(ds[0]['diachi_ld'])
          this.cboLoaihinhTB.SelectedValue = ds[0]['loaitb_id']
          await this.cboLoaihinhTB_SelectedValueChanged()
          this.doituong_id = ds[0]['doituong_id']
          this.cboDoiTuongCu.SelectedValue = ds[0]['doituong_id']
          this.cboDoiTuongMoi.SelectedValue = ds[0]['doituong_id']
          await this.cboDoiTuongMoi_SelectedValueChanged()

          this.donvi_id = ds[0]['donviql_id']
          this.txtMaTB = ds[0]['ma_tb']

          if (ds_muccuoc != undefined && ds_muccuoc.length > 0) {
            ds_muccuoc = ds_muccuoc.map(function (item) {
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

            this.cboMucCuocTBCu.list = ds_muccuoc
            this.cboMucCuocTBCu.SelectedValue = this.cboMucCuocTBCu.list[0].mucuoctb_id
          } else {
            this.cboMucCuocTBCu.list = []
            this.cboMucCuocTBCu.SelectedValue = null
          }

          if (ds_diachi.length > 0) {
            ds_diachi = ds_diachi.map(function (item) {
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

            this.phuongld_id = ds_diachi[0].phuongld_id == null ? 0 : ds_diachi[0].phuongld_id
            this.phold_id = ds_diachi[0].phold_id == null ? 0 : ds_diachi[0].phold_id
            this.apld_id = ds_diachi[0].apld_id == null ? 0 : ds_diachi[0].apld_id
            this.khuld_id = ds_diachi[0].khuld_id == null ? 0 : ds_diachi[0].khuld_id
          } else {
            this.phuongld_id = 0
            this.phold_id = 0
            this.apld_id = 0
            this.khuld_id = 0
          }
          this.dtpNgayLapHD = moment(ds[0]['ngaylap_hd'], 'YYYY-MM-DD[T]HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
          if (ds[0]['trangbi_id'] != null && ds[0]['trangbi_id'] != '') {
            this.cboTrangBiCu.SelectedValue = ds[0]['trangbi_id']
          }

          await this.Lay_DS_MucCuoc_TB_V2(this.khuld_id, this.apld_id, this.phold_id, this.phuongld_id, this.loaitb_id, this.doituong_id, moment(this.dtpNgayYeuCau, 'DD/MM/YYYY HH:ss:mm').format('DD/MM/YYYY'), this.cboTrangBiMoi.SelectedValue)
          if (ds[0]['loaitb_id'] == LoaiHinhTB.INTERNET_ADSL || ds[0]['loaitb_id'] == LoaiHinhTB.INTERNET_FTTH || ds[0]['loaitb_id'] == LoaiHinhTB.WIFI_FIBER) {
            let ngay_yc = new Date(moment(this.dtpNgayYeuCau, 'DD/MM/YYYY HH:mm:ss').format('YYYY-MM-DD HH:mm:ss')).getDate()

            if (ngay_yc <= this.ngaytucthi) {
              this.chkTucThi.Enabled = true
            } else {
              this.chkTucThi.Enabled = false
              this.chkTucThi.Checked = false
            }
          } else {
            this.chkTucThi.Enabled = false
            this.chkTucThi.Checked = true
          }

          this.txtSoThangCK = ds[0]['sothang_ck']

          await this.HT_DS_KhuyenMai_Combobox()

          let ds_db = response.data.data.RESULT.DS_DB
          if (ds_db.length > 0) {
            ds_db = ds_db.map(function (item) {
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
            if (this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_ADSL || this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_FTTH || this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.WIFI_FIBER) {
              this.btnThongTinTC.Enabled = true
              this.txtTTCuoc.Enabled = true
              this.txtTTCuoc.Text = ''
            } else {
              this.btnThongTinTC.Enabled = false
              this.txtTTCuoc.Enabled = false
              this.txtTTCuoc.Text = ''
            }

            if (ds_db[0]['tocdo_id'] != '' && ds_db[0]['tocdo_id'] != null) {
              this.cboTocDoCu.SelectedValue = ds_db[0]['tocdo_id']
              console.log('Tốc độ cũ 2: ', this.cboTocDoCu.SelectedValue)
              await this.cboTocDoCu_SelectedValueChanged()
              if (
                (this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_MYTV && this.cboTocDoCu.SelectedValue == this.TOCDO_ADSL.MYTV) ||
                this.cboTocDoCu.SelectedValue == this.TOCDO_ADSL.MYTV_FULLOP ||
                this.cboTocDoCu.SelectedValue == this.TOCDO_ADSL.MEGAVNN_EASY ||
                this.cboTocDoCu.SelectedValue == this.TOCDO_ADSL.MYTV_HD
              ) {
                //hieutc: 29.4.2011: Với mytv chỉ cho đổi lên gói mới
                this.cboTocDoADSL.SelectedValue = this.cboTocDoADSL.list[0].tocdo_id
                await this.cboTocDoADSL_SelectedValueChanged()
              } else {
                let matocdo = ds_db[0]['matocdo']
                if (matocdo == null || matocdo == '') {
                  if (this.cboKieuLD.SelectedValue == this.KieuLapDat.THAYDOI_TOCDOTHUC_ADSL) {
                    this.cboTocDoADSL.SelectedValue = ds_db[0]['tocdo_id']
                    await this.cboTocDoADSL_SelectedValueChanged()
                  } else {
                    this.cboTocDoADSL.SelectedValue = this.cboTocDoADSL.list[0].tocdo_id
                    await this.cboTocDoADSL_SelectedValueChanged()
                  }
                } else {
                  this.cboTocDoADSL.SelectedValue = ds_db[0]['tocdo_id']
                  await this.cboTocDoADSL_SelectedValueChanged()
                }
              }
            } else {
              this.cboTocDoCu.SelectedValue = null
              this.cboTocDoADSL.SelectedValue = null
            }

            if (this.cboDichVuVT.SelectedValue != DichVuVienThong.DICHVU_CNTT && this.cboDichVuVT.SelectedValue != DichVuVienThong.TRUNGTAM_DULIEU && this.cboDichVuVT.SelectedValue != DichVuVienThong.ANTOAN_BAOMAT_TT && this.cboDichVuVT.SelectedValue != DichVuVienThong.HOINGHI_TRUYENHINH) {
              if (ds_db[0]['tocdothuc_id'] != '' && ds_db[0]['tocdothuc_id'] != null) {
                this.tocdothuc_cu_id = ds_db[0]['tocdothuc_id']
              }
            }

            if (ds_db[0]['muccuoc_id'] != '' && ds_db[0]['muccuoc_id'] != null) {
              this.cboMucCuocCu.SelectedValue = ds_db[0]['muccuoc_id']
              this.cboMucCuocMoi.SelectedValue = ds_db[0]['muccuoc_id']
              await this.cboMucCuocMoi_SelectedValueChanged()
            } else {
              this.cboMucCuocCu.SelectedValue = null
              this.cboMucCuocMoi.SelectedValue = null
            }

            if (this.cboDichVuVT.SelectedValue == DichVuVienThong.ADSL) {
              if (ds_db[0]['congnghe_id'] != '' && ds_db[0]['congnghe_id'] != null) {
                this.cboCongNghe_Cu.SelectedValue = ds_db[0]['congnghe_id']
                if (ds_db[0]['congnghe_id'] == 2 || ds_db[0]['congnghe_id'] == 3) {
                  this.cboCongNghe_Moi.SelectedValue = ds_db[0]['congnghe_id']
                } else {
                  this.cboCongNghe_Moi.SelectedValue = 0
                }
              } else {
                this.cboCongNghe_Cu.SelectedValue = 0
                this.cboCongNghe_Moi.SelectedValue = 0
              }
            }

            if (this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
              this.txtCuoc_TBI.Enabled = true
              this.txtCuocSD.Enabled = true
              if (ds_db[0]['cuoc_tbi'] != '' && ds_db[0]['cuoc_tbi'] != null) {
                this.txtCuoc_TBI.Text = this.numberWithCommas(ds_db[0]['cuoc_tbi'])
              } else {
                this.txtCuoc_TBI.Text = '0'
              }

              if (ds_db[0]['cuoc_sd'] != '' && ds_db[0]['cuoc_sd'] != null) {
                this.txtCuocSD.Text = this.numberWithCommas(ds_db[0]['cuoc_sd'])
              } else {
                this.txtCuocSD.Text = '0'
              }
            } else {
              this.txtCuoc_TBI.Enabled = false
              this.txtCuocSD.Enabled = false
              this.txtCuoc_TBI.Text = '0'
              this.txtCuocSD.Text = '0'
            }
            if (this.cboDichVuVT.SelectedValue != DichVuVienThong.DICHVU_CNTT && this.cboDichVuVT.SelectedValue != DichVuVienThong.TRUNGTAM_DULIEU && this.cboDichVuVT.SelectedValue != DichVuVienThong.ANTOAN_BAOMAT_TT && this.cboDichVuVT.SelectedValue != DichVuVienThong.HOINGHI_TRUYENHINH) {
              if (ds_db[0]['kieutra_id'] != '' && ds_db[0]['kieutra_id'] != null && this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_MYTV) {
                this.cboKieuTraCu.SelectedValue = ds_db[0]['kieutra_id']
                this.cboKieuTraMoi.SelectedValue = ds_db[0]['kieutra_id']
                await this.cboKieuTraMoi_SelectedValueChanged()
                if (ds_db[0]['kieutra_id'] == this.KieuTra.TRATRUOC) {
                  this.cboThangTra.SelectedValue = ds_db[0]['thang_id']
                  await this.cboThangTra_SelectedValueChanged()
                  this.dtpNgayTra.Text = moment(ds_db[0]['ngaytra'], 'YYYY-MM-DD[T]HH:mm:ss').format('DD/MM/YYYY')
                }
              } else {
                this.cboKieuTraCu.SelectedValue = null
                this.cboKieuTraMoi.SelectedValue = null
              }

              if (ds_db[0]['thietbidc_id'] != '' && ds_db[0]['thietbidc_id'] != null) {
                this.cboLoaiTBiADSL.SelectedValue = ds_db[0]['thietbidc_id']
                await this.cboLoaiTBiADSL_SelectedValueChanged()
              } else {
                this.cboLoaiTBiADSL.SelectedValue = null
              }

              this.IP = ds_db[0]['ip']

              if (ds_db[0]['bras_id'] != '' && ds_db[0]['bras_id'] != null) {
                this.Bras_id = ds_db[0]['bras_id']
              }

              if (ds_db[0]['trangbi_id'] != '' && ds_db[0]['trangbi_id'] != null) {
                this.trangbi_id = ds_db[0]['trangbi_id']
              } else {
                this.trangbi_id = this.TRANGBI.KHACHHANG_TU_TRANGBI
              }
            }
          }
          if (this.cboDichVuVT.SelectedValue == DichVuVienThong.DICHVU_CNTT || this.cboDichVuVT.SelectedValue == DichVuVienThong.ANTOAN_BAOMAT_TT || this.cboDichVuVT.SelectedValue == DichVuVienThong.TRUNGTAM_DULIEU || this.cboDichVuVT.SelectedValue == DichVuVienThong.HOINGHI_TRUYENHINH) {
            await this.Init_Properties()
          }
          await this.gan_thongso_theodanhba(1, this.cboDichVuVT.SelectedValue, this.thuebao_id)
          await this.gan_thongso_theodanhba(2, this.cboDichVuVT.SelectedValue, this.thuebao_id_tc)
        } else {
          this.$toast.error('Account ' + this.txtMaTB + ' đối với dịch vụ ' + this.cboDichVuVT.list.filter((x) => x.DICHVUVT_ID == this.cboDichVuVT.SelectedValue)[0].TEN_DVVT + ' Không thuộc danh sách bán chéo hợp đồng')
          this.SetButton(0)
        }
        this.KieuLD_click = true
      } catch (e) {
        console.log(e)
      }
    },
    async lay_ds_danhba_adsl(thuebao_id) {
      let result = []
      try {
        let response = await api.lay_ds_danhba_adsl(this.axios, thuebao_id)
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
        return result
      } catch (e) {
        return result
      }
    },
    async Init_Properties() {
      try {
        let response = await api.lay_ds_thuoctinh_bang(this.axios, {
          loaiTbId: this.cboLoaihinhTB.SelectedValue,
          tenBang: 'HDTB_CNTT',
          optList: '00000'
        })
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.ItemList = response.data.data
        } else {
          this.ItemList = []
        }
      } catch (e) {
        this.ItemList = []
      }
    },
    async gan_thongso_theodanhba(vkieu, vdichvu, vthuebaoid) {
      try {
        if (vkieu == 1) {
          this.thongso_ban.trangbi_ban = 0
          this.thongso_ban.muccuoc_ban = 0
          this.thongso_ban.chuquan_ban = 0
          this.thongso_ban.congnghe_ban = 0
          this.thongso_ban.doituong_ban = 0
          this.thongso_ban.kieuld_ban = 0
          this.thongso_ban.kieutra_ban = 0
          this.thongso_ban.loaikenh_ban = 0
          this.thongso_ban.loaimd_ban = 0
          this.thongso_ban.muccuoctb_ban = 0
          this.thongso_ban.thietbicuoi_ban = 0
          this.thongso_ban.thoihan_ban = 0
          this.thongso_ban.thocdo_ban = 0
          this.thongso_ban.tocdoisp_ban = 0
          this.thongso_ban.tocdopir_ban = 0
          this.thongso_ban.tocdothuc_ban = 0
          this.thongso_ban.trangbi_ban = 0
          this.thongso_ban.madoicap_ban = ''
          this.thongso_ban.matn_ban = ''
        } else {
          this.thongso_tc.trangbi_tc = 0
          this.thongso_tc.muccuoc_ban = 0
          this.thongso_tc.chuquan_ban = 0
          this.thongso_tc.congnghe_ban = 0
          this.thongso_tc.doituong_ban = 0
          this.thongso_tc.kieuld_ban = 0
          this.thongso_tc.kieutra_ban = 0
          this.thongso_tc.loaikenh_ban = 0
          this.thongso_tc.loaimd_ban = 0
          this.thongso_tc.muccuoctb_ban = 0
          this.thongso_tc.thietbicuoi_tc = 0
          this.thongso_tc.thoihan_ban = 0
          this.thongso_tc.thocdo_ban = 0
          this.thongso_tc.tocdoisp_ban = 0
          this.thongso_tc.tocdopir_ban = 0
          this.thongso_tc.tocdothuc_ban = 0
          this.thongso_tc.trangbi_ban = 0
          this.thongso_tc.madoicap_ban = ''
          this.thongso_tc.matn_ban = ''
        }
        if (vdichvu == 4) {
          if (vkieu == 1) {
            let ds = await this.lay_ds_danhba_adsl(vthuebaoid)
            if (ds.length > 0) {
              let expc = ''
              try {
                expc = 'TRANGBI_ID'
                this.thongso_ban.trangbi_id = Number(ds[0]['trangbi_id'])
                expc = 'TOCDO_ID'
                this.thongso_ban.tocdo_id = Number(ds[0]['tocdo_id'])
                this.thongso_ban.tocdothuc_id = Number(ds[0]['tocdo_id'])
                expc = 'MUCCUOC_ID'
                this.thongso_ban.muccuoc_id = Number(ds[0]['muccuoc_id'])
                expc = 'KIEUTRA_ID'
                this.thongso_ban.kieutra_id = Number(ds[0]['kieutra_id'])
                expc = 'THIETBIDC_ID'
                this.thongso_ban.thietbidc_id = Number(ds[0]['thietbidc_id'])
                expc = 'DOITUONG_ID'
                this.thongso_ban.doituong_id = Number(this.cboDoiTuongCu.SelectedValue)
                expc = 'MUCUOCTB_ID'
                this.thongso_ban.muccuoctb_id = Number(this.cboMucCuocTB.SelectedValue)
              } catch (e) {
                this.$toast.error('Thiếu dữ liệu ' + expc + ' ở tỉnh bán. Liên hệ Admin để xử lý.')
              }
            }
          } else {
            let ds1 = await api.lay_ds_db_adsl_theo_tbid(this.axios, {
              tinhthicong_id: this.tinh_tb_id,
              thuebao_id: vthuebaoid
            })
            console.log('ds1: ', ds1.data.data)
            let data = ds1.data.data
            if (data.length > 0) {
              let expc = ''
              try {
                expc = 'TRANGBI_ID'
                this.thongso_tc.trangbi_id = Number(data[0]['trangbi_id'])
                expc = 'TOCDO_ID'
                this.thongso_tc.tocdo_id = Number(data[0]['tocdo_id'])
                expc = 'TOCDO_ID'
                this.thongso_tc.tocdothuc_id = Number(data[0]['tocdo_id'])
                expc = 'MUCCUOC_ID'
                this.thongso_tc.muccuoc_id = Number(data[0]['muccuoc_id'])
                expc = 'KIEUTRA_ID'
                this.thongso_tc.kieutra_id = Number(data[0]['kieutra_id'])
                expc = 'THIETBIDC_ID'
                this.thongso_tc.thietbidc_id = Number(data[0]['thietbidc_id'])
                console.log(this.dsDBTBBC)
                if (this.dsDBTBBC != undefined) {
                  expc = 'DOITUONG_ID'
                  this.thongso_tc.doituong_id = Number(this.dsDBTBBC[0]['doituong_id'])
                  expc = 'MUCUOCTB_ID'
                  this.thongso_tc.muccuoctb_id = Number(this.dsDBTBBC[0]['mucuoctb_id'])
                }
                console.log('thongso_tc: ', this.thongso_tc)
              } catch (e) {
                console.log(e)
                this.$toast.error('Thiếu dữ liệu ' + expc + ' ở tỉnh thi công. Liên hệ Admin để xử lý.')
              }
            }
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    async chkKhuyenMai_CheckedChanged() {
      this.cboKhuyenMai.Enabled = this.chkKhuyenMai.Checked
      this.btnChiTietKM.Enabled = this.chkKhuyenMai.Checked
      this.btnChon_KhuyenMai.Enabled = this.chkKhuyenMai.Checked
      this.LaySothang_SD()

      if (this.chkKhuyenMai.Checked == true) {
        this.dtiendatcoc_km = 0
        this.dvatdatcoc_km = 0
      }
      this.HienThiTien_TraTruoc()
      await this.HT_Tien_ConLai_DatCoc_Cu(this.thuebao_id, this.loaitb_id, this.cboKieuLD.SelectedValue)
    },
    async chkTraTruoc_CheckedChanged() {
      this.btnChon_TraTruoc.Enabled = this.chkTraTruoc.Checked
      this.cboTraTruoc.Enabled = this.chkTraTruoc.Checked
      this.btnChiTietTraTruoc.Enabled = this.chkTraTruoc.Checked
      this.LaySothang_SD()
      this.dsDC_HDTB = []
      if (this.chkTraTruoc.Checked == true) {
        this.dtiendatcoc_tt = 0
        this.dvatdatcoc_tt = 0
      }
      this.HienThiTien_TraTruoc()
      await this.HT_Tien_ConLai_DatCoc_Cu()
    },
    async LaySothang_SD() {
      let response = await api.fn_lay_thang_sd(this.axios, {
        ds_para: JSON.stringify({
          CHK_KHUYENMAI: this.chkKhuyenMai.Checked,
          CHK_TRATRUOC: this.chkTraTruoc.Checked,
          CBO_TRATRUOC: this.cboTraTruoc.SelectedValue,
          CBO_KHUYENMAI: this.cboKhuyenMai.SelectedValue
        })
      })
      if (response.data.error_code === 'BSS-00000000' && response.data.data) {
        if (response.data.data.ERROR_CODE == 0) {
          this.$toast.error(response.data.data.MESSAGE)
          return
        }
        if (response.data.data.RESULT) {
          this.sothang_sd = response.data.data.RESULT.SOTHANG_SD
        } else this.sothang_sd = 0
        this.txtSoThangCK = this.sothang_sd
      }
    },

    HienThiTien_TraTruoc() {
      for (let j = 0; j < this.ds_tien_khoanmuc.length; j++) {
        if (this.ds_tien_khoanmuc[j]['KHOANMUCTT_ID'] == this.KHOANMUC_TT.KMTT_DATCOC_KM) {
          this.ds_tien_khoanmuc[j]['TIEN'] = this.dtiendatcoc_km
          this.ds_tien_khoanmuc[j]['VAT'] = this.dvatdatcoc_km
        }
        if (this.ds_tien_khoanmuc[j]['KHOANMUCTT_ID'] == this.KHOANMUC_TT.KMTT_DATCOC) {
          this.ds_tien_khoanmuc[j]['TIEN'] = this.dtiendatcoc_tt
          this.ds_tien_khoanmuc[j]['VAT'] = this.dvatdatcoc_tt
        }
      }

      this.LayTongTien_HDTB()
    },
    async HT_Tien_ConLai_DatCoc_Cu() {
      this.dtienthoai_dc = 0
      let response = await api.lay_ds_tien_dc_conlai_doi_td(this.axios, {
        vthuebao_id: this.thuebao_id,
        vthang_thoai: moment().format('YYYYMM'),
        vtucthi: this.chkTucThi.Checked ? 1 : 0
      })
      if (response.data.error_code === 'BSS-00000000' && response.data.data) {
        if (response.data.data.ERROR_CODE == 0) {
          this.$toast.error(response.data.data.MESSAGE)
          return
        }
        if (response.data.data.length > 0) {
          this.dtienthoai_dc = response.data.data[0].tien_conlai
        }
      }
      for (let j = 0; j < this.ds_tien_khoanmuc.length; j++) {
        if (this.ds_tien_khoanmuc[j]['KHOANMUCTT_ID'] == this.KHOANMUC_TT.KMTT_TIENTHOAI_DC_DOI_TD) {
          if ((this.dtiendatcoc_tt * 11) / 10 > this.dtienthoai_dc) {
            this.ds_tien_khoanmuc[j]['TIEN'] = Math.abs(Math.round((this.dtienthoai_dc * 10) / 11))
            this.ds_tien_khoanmuc[j]['VAT'] = Math.abs(Math.round(this.dtienthoai_dc / 11))
          } else {
            this.ds_tien_khoanmuc[j]['TIEN'] = Math.abs(this.dtiendatcoc_tt)
            this.ds_tien_khoanmuc[j]['VAT'] = Math.abs(this.dvatdatcoc_tt)
          }
        }
      }
      this.LayTongTien_HDTB()
    },
    //Các API
    async check_map_id() {
      try {
        let input1 = {}
        input1.type = 7
        input1.param = this.cboTocDoADSL.SelectedValue
        let response = await api.fn_tt_tocdo_adsl(this.axios, input1)
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return -1
        }
      } catch (e) {
        return -1
      }
    },

    async get_vdc_list_attr() {
      let result = []
      try {
        let response = await api.get_vdc_list_attr()
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
        return result
      } catch (e) {
        return result
      }
    },
    async Lay_DS_KHUYEN_MAI(iloaitb_id, itrangbi_id, itocdo_id, imuccuoc_id) {
      try {
        let res = await api.lay_khuyenmai_hdtb(this.axios, {
          vloaitb_id: iloaitb_id,
          vkieuld_id: this.cboKieuLD.SelectedValue,
          vphuongld_id: this.phuongld_id,
          vkhoanmuctt_id: this.KHOANMUC_TT.KMTT_DATMOI,
          vphold_id: this.phold_id,
          vapld_id: this.apld_id,
          vkhuld_id: this.khuld_id,
          vtrangbi_id: itrangbi_id,
          vdoituong_id: this.cboDoiTuongMoi.SelectedValue,
          vtocdo_id: itocdo_id,
          vmuccuoc_id: imuccuoc_id,
          vloaikm: 0
        })
        if (res.data.error_code === 'BSS-00000000' && res.data.data != '') {
          this.cboKhuyenMai.list = res.data.data
          this.cboKhuyenMai.SelectedValue = this.cboKhuyenMai.list[0]
        } else {
          this.cboKhuyenMai.list = 0
          this.cboKhuyenMai.SelectedValue = null
        }
      } catch (e) {
        console.log(e)
        this.cboKhuyenMai.list = 0
        this.cboKhuyenMai.SelectedValue = null
      }
    },
    async LAY_DS_HOPDONG_THEO_MA_TB() {
      let res = await api.sp_thaydoi_tocdo_internet_hienthi(this.axios, {
        dichvuvt_id: this.cboDichVuVT.SelectedValue,
        ma_tb: this.txtMaTB
      })
      return res.data
    },
    async Lay_DS_Tra_Truoc(iloaitb_id, itrangbi_id, itocdo_id, imuccuoc_id) {
      try {
        let res = await api.lay_khuyenmai_hdtb(this.axios, {
          vloaitb_id: iloaitb_id,
          vkieuld_id: this.cboKieuLD.SelectedValue,
          vphuongld_id: this.phuongld_id,
          vkhoanmuctt_id: this.KHOANMUC_TT.KMTT_DATMOI,
          vphold_id: this.phold_id,
          vapld_id: this.apld_id,
          vkhuld_id: this.khuld_id,
          vtrangbi_id: itrangbi_id,
          vdoituong_id: this.cboDoiTuongMoi.SelectedValue,
          vtocdo_id: itocdo_id,
          vmuccuoc_id: imuccuoc_id,
          vloaikm: 1
        })
        if (res.data.error_code === 'BSS-00000000' && res.data.data != '') {
          this.cboTraTruoc.list = res.data.data
          this.cboTraTruoc.SelectedValue = this.cboTraTruoc.list[0]
        } else {
          this.cboTraTruoc.list = 0
          this.cboTraTruoc.SelectedValue = null
        }
      } catch (e) {
        console.log(e)
        this.cboTraTruoc.list = 0
        this.cboTraTruoc.SelectedValue = null
      }
    },
    async LAY_TIEN_KHOANMUC_LD() {
      let res = await api.lay_tien_khoanmuc_ld(this.axios, {
        loaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL,
        ngay_yc: moment(this.dtpNgayYeuCau, 'DD/MM/YYYY HH:ss:mm').format('DD/MM/YYYY'),
        dichvuvt_id: Number(this.cboDichVuVT.SelectedValue),
        loaitb_id: Number(this.cboLoaihinhTB.SelectedValue),
        kieuld_id: Number(this.cboKieuLD.SelectedValue),
        phuongld_id: this.phuongld_id,
        tocdo_id: this.tocdo_id,
        muccuoc_id: this.muccuoc_id,
        ltb_adsl_id: this.ltb_adsl_id,
        ltb_adsl_moi_id: this.ltb_adsl_moi_id,
        ltb_adsl_cu_id: this.ltb_adsl_cu_id,
        tocdo_moi_id: this.tocdo_moi_id,
        tocdo_cu_id: this.tocdo_cu_id,
        kh_cd: this.kh_cd,
        phold_id: this.phold_id,
        apld_id: this.apld_id,
        khuld_id: this.khuld_id
      })
      return res
    },

    async load_tdkld_theo_lhtb_frmbcthaydoitocdoadsl(loaitb_id) {
      let result = []
      try {
        let response = await api.load_tdkld_theo_lhtb_frmbcthaydoitocdoadsl(this.axios, {
          ds_para: JSON.stringify({ LOAITB_ID: loaitb_id })
        })
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          result = response.data.data.RESULT
        }
        return result
      } catch (e) {
        console.log(e)
        return result
      }
    },

    sp_lay_ds_thamso_md(result) {
      let search1 = result.find((el) => el.ma_ts == 'NGAY_TUCTHI')
      console.log(search1)
      if (search1 != undefined) {
        if (search1.ten_ts != '') {
          this.ngaytucthi = Number(search1.ten_ts)
        }
      }
      let search2 = result.find((el) => el.ma_ts == 'NHAP_THONGTIN_TINHCUOC')
      if (search2 != undefined) {
        if (search2.ten_ts.trim() == '1') {
          this.nhap_tttc = 1
        }
      }
      let search3 = result.find((el) => el.ma_ts == 'BATBUOC_CTV_TDTD')
      if (search3 != undefined) {
        if (search3.ten_ts.trim() == '1') {
          this.batbuoc_ctv = 1
        }
      }
      let search4 = result.find((el) => el.ma_ts == 'BATBUOC_SOTHANG_CK')
      if (search4 != undefined) {
        if (search4.ten_ts.trim() != '1') {
          this.batbuoc_sothang_ck = Number(search4.ten_ts)
        }
      }
      let search5 = result.find((el) => el.ma_ts == 'KHONG_HIENTHI_TTTC_VISA')
      if (search5 != undefined) {
        if (search5.ten_ts.trim() == '1') {
          this.chkTTTCVisa.Checked = false
        }
      }
      let search6 = result.find((el) => el.ma_ts == 'MACDINH_TUCTHI_THAYDOI_TD')
      if (search6 != undefined) {
        if (search6.ten_ts.trim() == '1') {
          this.macdinh_tucthi = 1
        }
      }
      let search7 = result.find((el) => el.ma_ts == 'MACDINH_TUCTHI_THAYDOI_TD')
      if (search7 != undefined) {
        if (search7.ten_ts.trim() == '1') {
          this.kiemtra_no_tratruoc = 1
        }
      }
      let search8 = result.find((el) => el.ma_ts == 'SINH_MA_GD_THEO_DV')
      this.ts_sinhma_gd_theo_donvi = search8 != undefined ? true : false
    },
    async lay_thongtintc_frmbcthaydoitocdoadsl() {
      let result = []
      try {
        let response = await api.lay_thongtintc_frmbcthaydoitocdoadsl(this.axios, {
          ds_para: JSON.stringify({
            MUCCUOC_ID: this.cboMucCuocTB.SelectedValue,
            DICHVUVT_ID: this.cboDichVuVT.SelectedValue,
            TOCDO_ID: this.thongso_ban.tocdo_id
          })
        })
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          result = response.data.data.RESULT
        }
        return result
      } catch (e) {
        return result
      }
    },
    async sp_ht_tocdo_adsl_combobox_byloaitb(loaitb_id) {
      let response = await api.sp_ht_tocdo_adsl_combobox_byloaitb(this.axios, {
        p_loaitb_id: loaitb_id
      })
      if (response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.cboTocDoADSL.list = response.data.data
        this.cboTocDoADSL.SelectedValue = this.cboTocDoADSL.list[0].tocdo_id
        await this.cboTocDoADSL_SelectedValueChanged()
      }
    },
    async cboLoaiTBiADSL_SelectedValueChanged() {
      if (this.cboLoaiTBiADSL.list.length > 0) {
        if (this.cboLoaiTBiADSL.SelectedValue == null) return

        this.ltb_adsl_id = this.cboLoaiTBiADSL.SelectedValue
        this.LayTienTheoKhoanMuc()
      }
    },
    async cboDichVuVT_SelectedValueChanged() {
      this.cboLoaihinhTB.list = this.result_loadform.DS_LOAITB.filter((x) => x.DICHVUVT_ID == this.cboDichVuVT.SelectedValue)
      this.cboLoaihinhTB.SelectedValue = this.cboLoaihinhTB.list[0].LOAITB_ID
      await this.cboLoaihinhTB_SelectedValueChanged()
      this.dichvuvt_id = this.cboDichVuVT.SelectedValue
    },
    async cboLoaihinhTB_SelectedValueChanged() {
      if (this.cboLoaihinhTB.list.length > 0) {
        if (this.cboLoaihinhTB.SelectedValue == null) return

        this.loaitb_id = this.cboLoaihinhTB.SelectedValue
        if (this.cboLoaihinhTB.SelectedValue != LoaiHinhTB.INTERNET_FTTH && this.cboLoaihinhTB.SelectedValue != LoaiHinhTB.WIFI_FIBER) {
          this.cboLoaiTBiADSL.SelectedValue = this.LOAITBI_ADSL.ADSL
        } else {
          this.cboLoaiTBiADSL.SelectedValue = this.LOAITBI_ADSL.CPE
        }
        await this.cboLoaiTBiADSL_SelectedValueChanged()
        let response = await api.load_tdkld_theo_lhtb_frmbcthaydoitocdoadsl(this.axios, {
          ds_para: JSON.stringify({ LOAITB_ID: this.loaitb_id })
        })
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          let result = response.data.data.RESULT
          let cbotocdo_adsl = result.CBOTOCDO_ADSL
          let cbotocdo_cu = result.CBOTOCDO_CU
          let kieuld_bc = result.KIEULD_BC
          if (cbotocdo_adsl.length > 0) {
            cbotocdo_adsl = cbotocdo_adsl.map(function (item) {
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
            this.cboTocDoADSL.list = cbotocdo_adsl
            this.cboTocDoADSL.SelectedValue = this.cboTocDoADSL.list[0].tocdo_id
          }
          if (cbotocdo_cu.length > 0) {
            cbotocdo_cu = cbotocdo_cu.map(function (item) {
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
            this.cboTocDoCu.list = cbotocdo_cu
            this.cboTocDoCu.SelectedValue = this.cboTocDoCu.list[0].tocdo_id
            console.log('Tốc độ cũ 3: ', this.cboTocDoCu.SelectedValue)
            await this.cboTocDoCu_SelectedValueChanged()
          }
          if (kieuld_bc.length > 0) {
            this.cboKieuLD.list = kieuld_bc
            //this.cboKieuLD.SelectedValue = this.cboKieuLD.list[0].KIEULD_ID;
            console.log(this.cboKieuLD.list)
            await this.cboKieuLD_SelectedValueChanged()
          } else {
            this.cboKieuLD.list = []
            this.cboKieuLD.SelectedValue = null
          }
        }
      }
    },
    cboKieuLD_Close(args) {
      console.log('test')
      this.KieuLD_click = true
      this.cboKieuLD_Keypress()
    },
    selectTest() {},
    async cboKieuLD_SelectedValueChanged() {
      try {
        if (this.cboKieuLD.list.length > 0) {
          this.cboTocDoThuc_Moi.Enabled = false

          if (this.cboKieuLD.SelectedValue == null) {
            return
          }
          let kieuld_id = this.cboKieuLD.SelectedValue

          this.txtTienTra.Text = '0'
          this.EnableTraTruoc(false)
          this.pnlCongNghe.Visible = false
          if (kieuld_id == this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON || kieuld_id == this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON) {
            this.pnlCongNghe.Visible = true
            this.pnlMucCuoc.Visible = false
            this.pnlKieuTra.Visible = false

            //cboCongNghe_Moi.Enabled = true;
            this.cboTocDoADSL.Enabled = false
            this.cboMucCuocMoi.Enabled = false
            this.cboKieuTraMoi.Enabled = false
          } else {
            if (this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_MYTV) {
              this.pnlKieuTra.Visible = true
              this.pnlMucCuoc.Visible = false
              this.pnlCongNghe.Visible = false

              if (this.cboKieuLD.SelectedValue == this.KieuTra.TRATRUOC) {
                this.EnableTraTruoc(true)
              } else {
                this.EnableTraTruoc(false)
              }
            } else {
              this.pnlCongNghe.Visible = false
              this.pnlKieuTra.Visible = false
              this.pnlMucCuoc.Visible = true
              this.EnableTraTruoc(false)
            }
          }

          if (kieuld_id == this.KieuLapDat.THAYDOI_MUCCUOC_ADSL_TK) {
            console.log('Vào đây: ', this.cboTocDoADSL.SelectedValue)
            this.cboTocDoADSL.Enabled = false
            this.cboTocDoADSL.SelectedValue = this.cboTocDoCu.SelectedValue
            await this.cboTocDoADSL_SelectedValueChanged()
          }

          if (kieuld_id == this.KieuLapDat.THAYDOI_KIEUTRA_MYTV) {
            this.cboTocDoADSL.Enabled = false
            this.cboCongNghe_Moi.Enabled = false

            await this.sp_ht_tocdo_adsl_combobox_byloaitb(this.loaitb_id)
            this.cboMucCuocMoi.SelectedValue = this.cboMucCuocCu.SelectedValue
            this.cboCongNghe_Moi.SelectedValue = this.cboCongNghe_Cu.SelectedValue
            if (this.cboKieuTraCu.SelectedValue == this.KieuTra.TRASAU) {
              this.cboKieuTraMoi.SelectedValue = this.KieuTra.TRATRUOC
            } else {
              this.cboKieuTraMoi.SelectedValue = this.KieuTra.TRASAU
            }
            await this.cboKieuTraMoi_SelectedValueChanged()
            if (this.cboKieuTraMoi.SelectedValue == this.KieuTra.TRATRUOC) {
              this.EnableTraTruoc(true)
              this.txtTienTra.Text = '0'
            } else {
              this.EnableTraTruoc(false)
            }
          } else {
            this.cboKieuTraMoi.SelectedValue = this.cboKieuTraCu.SelectedValue
            await this.cboKieuTraMoi_SelectedValueChanged()
          }

          if (kieuld_id == this.KieuLapDat.THAYDOI_TOCDO_ADSL_TK || kieuld_id == this.KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL_TK || kieuld_id == this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON || kieuld_id == this.KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON) {
            this.cboMucCuocMoi.Enabled = false
            this.cboKieuTraMoi.Enabled = false
            this.cboMucCuocMoi.SelectedValue = this.cboMucCuocCu.SelectedValue
            await this.cboMucCuocMoi_SelectedValueChanged()
            this.cboCongNghe_Moi.SelectedValue = this.cboCongNghe_Cu.SelectedValue
            if (this.loaitb_id == LoaiHinhTB.INTERNET_MYTV) {
              // let dk = "";
              // dk = "sudung = 1";
              // bangts.HT_Tocdo_Adsl_Combobox_ByLoaiTB_moiV2(cboTocDoADSL, loaitb_id, dk);
              await this.sp_ht_tocdo_adsl_combobox_byloaitb(this.loaitb_id)
            }
          }

          // hoangpkn : 05/07/2012
          if (kieuld_id == this.KieuLapDat.THAYDOI_MUCCUOC_ADSL_TK || kieuld_id == this.KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL_TK) {
            if (this.cboMucCuocCu.SelectedValue == this.MUCCUOC.LUU_LUONG) {
              this.cboMucCuocMoi.SelectedValue = this.MUCCUOC.TRON_GOI
              await this.cboMucCuocMoi_SelectedValueChanged()
            }
            if (this.cboMucCuocCu.SelectedValue == this.MUCCUOC.TRON_GOI) {
              this.cboMucCuocMoi.SelectedValue = this.MUCCUOC.LUU_LUONG
              await this.cboMucCuocMoi_SelectedValueChanged()
            }
          }
          // end hoang them

          if (kieuld_id == this.KieuLapDat.THAYDOI_TOCDOTHUC_ADSL) {
            this.cboTocDoADSL.Enabled = false
            this.cboMucCuocMoi.Enabled = false
            this.cboKieuTraMoi.Enabled = false
            this.cboMucCuocTB.Enabled = false
            this.cboCongNghe_Moi.Enabled = false

            this.cboTocDoThuc_Moi.Enabled = false
            this.cboTocDoThuc_Moi.SelectedValue = -1

            this.chkTucThi.Checked = true
            this.chkTucThi.Enabled = false
          }

          if (kieuld_id == this.KieuLapDat.DOIGOI_FSECURE) {
            this.cboMucCuocMoi.Enabled = false
            this.cboKieuTraMoi.Enabled = false
            this.cboCongNghe_Moi.Enabled = false
            this.cboMucCuocMoi.SelectedValue = this.cboMucCuocCu.SelectedValue
            await this.cboMucCuocMoi_SelectedValueChanged()
          }

          ///TuấnNA bổ sung hàm lấy tiền lên ô text box --- Ngày 15/09/2010
          await this.LayTienTheoKhoanMuc()

          if (this.cboTocDoCu.SelectedValue != '' && this.cboTocDoCu.SelectedValue != null) {
            if (this.loaitb_id == LoaiHinhTB.INTERNET_MYTV && this.cboKieuLD.SelectedValue != this.KieuLapDat.THAYDOI_KIEUTRA_MYTV) {
              console.log('Vào đây 1: ', this.cboTocDoADSL.SelectedValue)
              this.cboTocDoADSL.SelectedValue = this.cboTocDoADSL.list[0]
            } else {
              console.log('Vào đây 2: ', this.cboTocDoADSL.SelectedValue)
              this.cboTocDoADSL.SelectedValue = this.cboTocDoCu.SelectedValue
            }
            await this.cboTocDoADSL_SelectedValueChanged()
          }
          if (!this.kt_load) {
            await this.HT_DS_KhuyenMai_Combobox()
          }
        }

        this.cboKieuLD_Keypress()
      } catch (ex) {
        console.log(ex)
        this.$toast.error('Có lỗi xảy ra!')
      }
    },
    async cboKieuLD_Keypress() {
      try {
        if (this.thuebao_id != 0 && this.KieuLD_click) {
          await this.$refs.TocDoMucCuocDialog.$children[0].clearData()
          await this.$refs.TocDoMucCuocDialog.$children[0].loadData()
          this.$refs.TocDoMucCuocDialog.show()
        }
      } catch (e) {}
    },

    async cboTocDoADSL_SelectedValueChanged() {
      console.log('Thay đổi')
      console.log(this.cboTocDoADSL.SelectedValue)
      try {
        if (this.cboTocDoADSL.list.length > 0) {
          if (this.cboTocDoADSL.SelectedValue == null) return
          console.log(this.cboTocDoADSL.SelectedValue)
          let response = await api.cbotocdoadsl_frmbcthaydoitocdoadsl(this.axios, {
            ds_para: JSON.stringify({
              TOCDO_ADSL_ID: this.cboTocDoADSL.SelectedValue,
              KT_LOAD: this.kt_load,
              LOAITB_ID: this.cboLoaihinhTB.SelectedValue
            })
          })
          if (response.data.error_code === 'BSS-00000000' && response.data.data) {
            let result = response.data.data.RESULT
            this.cboTocDoThuc_Moi.list = result.DS_TOCDO_THUC_MOI
            this.cboTocDoThuc_Moi.list = this.cboTocDoThuc_Moi.list.map(function (item) {
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
            this.cboTocDoThuc_Moi.SelectedValue = this.cboTocDoThuc_Moi.list[0].tocdo_id
            this.cboTocDoThuc_Moi.SelectedValue = this.cboTocDoADSL.SelectedValue

            await this.LayTienTheoKhoanMuc()
            if (!this.kt_load) {
              await this.HT_DS_KhuyenMai_Combobox()
              await this.Lay_DS_MucCuoc_TB_V2(this.khuld_id, this.apld_id, this.phold_id, this.phuongld_id, this.loaitb_id, this.doituong_id, moment(this.dtpNgayYeuCau, 'DD/MM/YYYY HH:ss:mm').format('DD/MM/YYYY'), this.cboTrangBiMoi.SelectedValue)

              if (this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_FTTH || this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.WIFI_FIBER) {
                let kt_iptinh = result.KT_IPTINH
                console.log(kt_iptinh)
                // if (kt_iptinh != '0' && kt_iptinh != '-1') {
                //   if (this.check == true)// Hungvm đánh dấu nếu không ấn nút thay đổi tốc độ hàng loạt
                //   {
                //     this.$toast.warning('Lưu ý chọn khuyến mại IP tĩnh nếu có !')
                //   }
                // }
              }
            }
            this.txtTTCuoc.Text = ''
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    cboTocDoCu_SelectedValueChanged() {
      if (this.cboTocDoCu.list.length > 0) {
        if (this.cboTocDoCu.SelectedValue == null) return
        var tocdocu_id = this.cboTocDoCu.SelectedValue
        this.tdthuccu = 0
        this.tdthuccu = this.cboTocDoThuc_Moi.list.filter((x) => (x.tocdo_id = tocdocu_id))
      }
    },
    cboKieuTraMoi_SelectedValueChanged() {
      try {
        if (this.cboKieuTraMoi.SelectedValue == null) return

        if (this.cboKieuTraMoi.list.length > 0) {
          if (this.cboKieuTraMoi.SelectedValue == 1 && this.cboKieuLD.SelectedValue == KieuLapDat.THAYDOI_KIEUTRA_MYTV) {
            this.EnableTraTruoc(true)

            this.txtTienTra.Text = '0'
          } else {
            this.txtTienTra.Text = '0'
            this.EnableTraTruoc(false)
          }
        }
      } catch (ex) {
        console.log(ex)
        this.$toast.error(ex)
      }
    },
    cboThangTra_SelectedValueChanged() {
      try {
        if (this.cboThangTra.SelectedValue == null) return

        if (this.cboThangTra.list.length > 0) {
          if (this.cboKieuTraMoi.SelectedValue == this.KieuTra.TRATRUOC && this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.INTERNET_MYTV) {
            this.txtTienTra.Text = '0'
          } else {
            this.txtTienTra.Text = '0'
          }
        }
      } catch (ex) {
        console.log(ex)
        this.$toast.error(ex)
      }
    },
    async cboMucCuocMoi_SelectedValueChanged() {
      try {
        if (this.cboMucCuocMoi.SelectedValue == null) return
        if (this.cboMucCuocMoi.list.length > 0) {
          if (!this.kt_load) {
            await this.HT_DS_KhuyenMai_Combobox()
            await this.Lay_DS_MucCuoc_TB_V2(0, 0, 0, 0, this.loaitb_id, this.doituong_id, moment(this.dtpNgayYeuCau, 'DD/MM/YYYY HH:ss:mm').format('DD/MM/YYYY'), this.cboTrangBiMoi.SelectedValue)
          }
        }
      } catch (ex) {
        console.log(e)
        this.$toast.error(ex)
      }
    },
    async cboMucCuocTB_SelectedValueChanged() {
      console.log('cboMucCuocTB_SelectedValueChanged')
      try {
        if (this.cboMucCuocTB.SelectedValue == null) return

        this.txtTTCuoc.Text = ''
        let input1 = {}
        input1.type = 5
        input1.param = this.cboMucCuocTB.SelectedValue
        let response = await api.fn_tt_muccuoc_tb(this.axios, input1)
        //nhapt them 10/10/17
        if (response.data.error_code === 'BSS-00000000' && response.data.data) {
          let mc_id = response.data.data
          if (mc_id != '-1') {
            this.cboMucCuocMoi.SelectedValue = mc_id
          }
        }
      } catch (e) {
        console.log(e)
        this.$toast.error(e)
      }
    },
    async cboDoiTuongMoi_SelectedValueChanged() {
      if (this.cboDoiTuongMoi.list.length > 0) {
        if (this.cboDoiTuongMoi.SelectedValue == null) return

        this.doituong_id = this.cboDoiTuongMoi.SelectedValue
        await this.LayTienTheoKhoanMuc()
        this.txtTTCuoc.Text = ''
        if (!this.kt_load) {
          await this.HT_DS_KhuyenMai_Combobox()
          await this.Lay_DS_MucCuoc_TB_V2(this.khuld_id, this.apld_id, this.phold_id, this.phuongld_id, this.loaitb_id, this.doituong_id, moment(this.dtpNgayYeuCau, 'DD/MM/YYYY HH:ss:mm').format('DD/MM/YYYY'), this.cboTrangBiMoi.SelectedValue)
        }
      }
    },

    async ThongTinTC() {
      try {
        if (this.cboMucCuocTB.list.length == 0 || this.cboMucCuocTB.SelectedValue == null) {
          this.$toast.error('Chưa có mức cước thuê bao. Bạn hãy kiểm tra lại!')
          return
        }
        let cuoc_tb = -1
        let cuockhoan = -1
        let cuoc_tg = -1
        let cuoc_ll = -1
        let result = await this.lay_thongtintc_frmbcthaydoitocdoadsl()
        if (result.DS_MUCCUOC != undefined && result.DS_MUCCUOC.length > 0) {
          cuoc_tb = result.DS_MUCCUOC.CUOC_TB
          cuockhoan = result.CUOCKHOAN
          cuoc_tg = result.CUOC_TG
          cuoc_ll = result.CUOC_LL
        }
        if (cuoc_tb == -1) cuoc_tb = 0
        if (cuockhoan == -1) cuockhoan = 0
        if (cuoc_tg == -1) cuoc_tg = 0
        if (cuoc_ll == -1) cuoc_ll = 0
        this.dulieu.thongtintc = this.txtTTCuoc.Text
        this.dulieu.cuoc_tb = cuoc_tb
        this.dulieu.cuoc_tg = cuoc_tg
        this.dulieu.cuockhoan = cuockhoan
        this.dulieu.cuoc_ll = cuoc_ll

        this.dulieu.loaitb_id = this.cboLoaihinhTB.SelectedValue
        if (this.cboMucCuocTB.SelectedValue != null) {
          this.dulieu.muccuoctb_id = this.cboMucCuocTB.SelectedValue
        }
        if (this.cboDichVuVT.SelectedValue == DichVuVienThong.ADSL) {
          this.dulieu.mienip = result.SL_IP_MP
        }

        //this.$refs.popupThongTinTC.show();

        this.popupComponentData = this.dulieu
        this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupThongTinTC.vue')
        this.popupComponentName = 'popup-ThongTinTC'
        this.Popup('popupComponents')
      } catch (ex) {
        console.log(ex)
        this.$toast.error(ex)
      }
    },
    acceptThongTinTC(data) {},
    acceptTocDoMucCuoc(data) {
      this.$refs.TocDoMucCuocDialog.hide()
      console.log(data)
      // if (data.thongso_ban.muccuoctb_id != 0 && data.thongso_tc.muccuoctb_id != null) {
      this.thongso_ban = { ...data.thongso_ban }
      this.thongso_tc = { ...data.thongso_tc }

      this.cboTocDoADSL.SelectedValue = this.thongso_ban.tocdo_id
      console.log('Tự thay đổi')
      this.cboTocDoADSL_SelectedValueChanged()
      this.cboKieuTraMoi.SelectedValue = this.thongso_ban.kieutra_id
      this.cboKieuTraMoi_SelectedValueChanged()
      this.cboMucCuocMoi.SelectedValue = this.thongso_ban.muccuoc_id
      this.cboMucCuocMoi_SelectedValueChanged()
      this.cboCongNghe_Moi.SelectedValue = this.thongso_ban.congnghe_id
      this.cboDoiTuongMoi.SelectedValue = this.thongso_ban.doituong_id
      this.cboMucCuocTB.SelectedValue = this.thongso_ban.muccuoctb_id
      this.cboTrangBiMoi.SelectedValue = this.thongso_ban.trangbi_id
      // }
      this.kt_load = false
    },
    async rowGridTBSelected(args) {
      this.hdtb_id = args.data.hdtb_id
      let response = await api.lay_ds_hopdong_tb_theo_hdtb_id(this.axios, {
        hdtb_id: this.hdtb_id
      })
      if (response.data.error_code === 'BSS-00000000' && response.data.data) {
        let ds = response.data.data
        await this.HienThiTTHopDongTB(ds)
      }
    }
  },

  mounted() {
    this.loadform()
    this.loai_hd_id = LoaiHopDong.THAY_DOI_TOCDO_ADSL
  },
  created() {
    if (this.$route.params.tag != null && this.$route.params.tag != '' && this.$route.params.tag.length > 0) {
      this.tag = this.$route.params.tag
    } else if (this.$route.query.tag != null && this.$route.query.tag != '' && this.$route.query.tag.length > 0) {
      this.tag = this.$route.query.tag
    }
    console.log('Tag: ' + this.tag)
  }
}
</script>
