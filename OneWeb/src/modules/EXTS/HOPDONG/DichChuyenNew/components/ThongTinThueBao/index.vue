<template src="./index.html"> </template>
<style scoped src="./index.scss"></style>
<script>
import Adsl from '../DsDichVu/Adsl'
import CD from '../DsDichVu/CD'
import GP from '../DsDichVu/GP'
import IMS from '../DsDichVu/IMS'
import TSL from '../DsDichVu/TSL'
import Wan from '../DsDichVu/Wan'
import CNTT from '../DsDichVu/CNTT'
import DateTimeLib from '@/utils/DateTimeLib'
import ModalChonDiaChi from './Popups/ChonDiaChi/Modal'
import Gankhuvuc from './Popups/popupGanKhuVucQuanLyThueBao'
import ThueBaoCungMa from './Popups/ThueBaoCungMa/index'
import { currency } from '@/filters/currency'
import ChonDonVi from './Popups/ChonDonVi'
import TienKMCuPopup from '@/modules/search/subscriber/ModifyContractMoney/popup/TienKMCuPopup'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import Enum from '../../Enum'
const popupChonDiaChi = () => import('./Popups/popupChonDiaChi')

export default {
  props: ['parent'],
  components: {
    SearchContractModal,
    TienKMCuPopup,
    popupChonDiaChi,
    ModalChonDiaChi,
    Adsl,
    CD,
    GP,
    IMS,
    TSL,
    Wan,
    CNTT,
    DateTimeLib,
    Gankhuvuc,
    ThueBaoCungMa,
    ChonDonVi
  },
  data: function () {
    return {
      dsKhuyenMai: [],
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
        NHOM_LOAINHA_ID: null,
        DIEN_TICH: null,
        SO_TANG: null,
        SO_PHONG: null,
        TEN_LOAINHA: null,
        BLOCK: null,
        TANG: null,
        PHONG: null,
        MOTA: null
      },
      dsTBGoiDDV: [],
      dulieu_gankhuvuc: {},
      diachi: {},
      diachi_flag: null,
      showThongTinThem: false,
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: '',
      popupComponentEvents: {
        'form-close': 'popupClosed',
        'acceptChangeCTV': 'popupClosed',
        'acceptChangeNGT': 'popupClosed'
      },
      dataSourceDV: {
        idDichVu: '',
        idLoaihinhTb: '',
        idKieuLd: '',
        idDaiVt: ''
      },
      themMyTVvaogoi: false,
      s_display_div: 1,
      lblTongTien: '',
      dtList: [],
      dsHDTT_NV: [],
      dsHDKH_Sub: [],
      dsHDTT_Sub: [],
      dsHDTT: [],
      dsHDKH: [],
      dsHDTB_KV: [],
      dsDK_DVGT: [],
      dsHDTB_Sub: [],
      dsHDTB: [],
      dsKM_HDTB: [],
      dsHDTB_CD: [],
      dsHDTB_ADSL: [],
      dsHDTB_TSL: [],
      dsHDTB_MEWAN: [],
      dsHDTB_GP: [],
      dsHDTBDV: [],
      dsHDTB_IMS: [],
      dsHDTB_CNTT: [],
      dsDK_DVK: [],
      dsDiaChi: [],
      dsDiaChiHDTB: [],
      dsDiaChiHDKH: [],
      dsDiaChiHDTT: [],
      dsTTTT: [],
      dsCTTHD: [],
      dsHDTBGOI: [],
      modelDateTime: {
        dtpNgayLapHD: new Date(),
        dtpNgayCV: new Date(),
        dtpNgayYC: new Date(),
        dtpNgayTN: new Date(),
        dtpTuNgay: new Date(),
        dtpDenNgay: new Date(),
        dateFormat: 'dd/MM/yyyy',
        enableDate: false
      },
      thongtin: null,
      inputValue: {
        ma_tb_phu_30bd: '',
        IS_B2A: false,
        ds_isdn: [],
        ht_loc_cbo_tram: false,
        kt_kenhphu_ao: false,
        kt_load: true,
        nvkd_id: 0, // Nhân viên phụ trách kinh doanh
        khuvuc_id: 0,
        iKenhTN: 1, // Keenh tieep nhan hop dog (trong bangr kenh_tn)
        kieu_thueso: 0, // 0 <-> ko thuê số, 1 <-> số trượt, 2 <-> thông báo
        noidung_thueso: '',
        sothang_thueso: 0,
        kieuld_ds: 0,
        hdtb_id_huygoi_mytv: 0,
        huonggiaotn_id: 0,
        quytrinh_id_2: 0,
        dc_daucuoi: 0,
        dichvuvt_id: -1,
        httt_id: 0,
        hdkh_id: 0,
        hdtt_id: 0,
        hdtb_id: 0,
        hdkh_cha_id: 0,

        khachhang_id: -1,
        thanhtoan_id: -1,
        thuebao_id: -1,
        tuyenthu_id: 0,
        matb_dd: '',
        ma_tb: '',

        // Thangph: Them 07/6/2015
        ds_tien_khoanmuc: [],
        dtienld: 0,
        dvatld: 0,
        dtien_dv: 0,
        dvat_dv: 0,
        // ---------------------------------

        diachikh_id: -1,
        dacdiemkh_id: -1,
        phokh_id: -1,
        khukh_id: -1,
        apkh_id: -1,
        phuongkh_id: -1,
        quankh_id: -1,
        tinhkh_id: -1,
        sonhakh: '',

        blockkh: '',
        tangkh: '',
        sophongkh: '',
        motadckh: '',

        khongcho_chon_khuvuc: false,
        batbuoc_sdt: false, // AnhDt
        // Hieu: 2/7/2010
        kt_adsl: true, // Kiểm tra mã đôi cáp ADSL - Hiếu 22.09.2010
        khach_hang_id: 0, // biến sử dụng cho phần lắp kèm,lắp trên đường có sẵn 22.09.2010 - Hiếu

        TBHD_PL: 1, /// Hiếu them bien TBHD_PL  -- ngay 10/07/2010
        // private CheckDataFacade objCheckData: new CheckDataFacade(),
        trangbi_id: 3, // Defaul khách hàng tự trang bị
        kt_fiber_colapchung: false,

        khundd_id: -1,
        dacdiemndd_id: -1,
        apndd_id: -1,
        phondd_id: -1,
        phuongndd_id: -1,
        quanndd_id: -1,
        tinhndd_id: -1,
        sonhandd: '',

        khuct_id: -1,
        apct_id: -1,
        phoct_id: -1,
        dacdiemct_id: -1,
        phuongct_id: -1,
        quanct_id: -1,
        tinhct_id: -1,
        sonhact: '',

        diachitt_id: -1,
        khutt_id: -1,
        phott_id: -1,
        aptt_id: -1,
        phuongtt_id: -1,
        dacdiemtt_id: -1,
        quantt_id: -1,
        tinhtt_id: -1,
        sonhatt: '',

        blocktt: '',
        tangtt: '',
        sophongtt: '',
        motadctt: '',

        diachitb_id: -1,
        khutb_id: -1,
        photb_id: -1,
        dacdiemtb_id: -1,
        aptb_id: -1,
        phuongtb_id: -1,
        quantb_id: -1,
        tinhtb_id: -1,
        sonhatb: '',

        blocktb: '',
        tangtb: '',
        sophongtb: '',
        motadctb: '',
        // -- dia chi BC
        diachibc_id: -1,
        phobc_id: -1,
        dacdiembc_id: -1,
        apbc_id: -1,
        khubc_id: -1,
        phuongbc_id: -1,
        quanbc_id: -1,
        tinhbc_id: -1,
        sonhabc: '',
        //
        diachild_id: -1,
        phold_id: -1,
        dacdiemld_id: -1,
        khuld_id: -1,
        apld_id: -1,
        phuongld_id: 0,
        quanld_id: -1,
        tinhld_id: -1,
        sonhald: '',

        tenLoaiNhald_id: -1,
        blockld: '',
        tangld: '',
        sophongld: '',
        motadcld: '',

        diachidau_id: -1,
        tinhdau_id: -1,
        phodau_id: -1,
        apdau_id: -1,
        khudau_id: -1,
        phuongdau_id: -1,
        quandau_id: -1,
        sonhadau: '',

        diachicuoi_id: -1,
        phocuoi_id: -1,
        khucuoi_id: -1,
        apcuoi_id: -1,
        phuongcuoi_id: -1,
        quancuoi_id: -1,
        tinhcuoi_id: -1,
        sonhacuoi: '',
        SoMayTN: '',

        checkdata: null, // Hiếu:10/08/2010

        m_dsThueBao_DichVu: [],
        ma_tbmoi: '',
        ne_id: 0,
        sone: '',
        // ---------------------------------------------------------------------------------
        // Hiếu bổ sung thêm biến tocdo_id sử dụng trong hàm lấy tiền ld - 14/09/2010
        tocdo_id: 0,
        ltb_adsl_id: 1, // Default
        kh_cd: 0,
        ltb_adsl_cu_id: 0,
        ltb_adsl_moi_id: 0,
        tocdo_moi_id: 0,
        tocdo_cu_id: 0,
        //
        bDuLieuKS: false,
        iHdtbID: 0,

        khachhang_id_cu: 0,
        report: '', /// TuấnNA khai báo biến xử lý báo cáo --- Ngày 23/08/2010

        // Hiếu bổ sung khai báo lấy đơn vị đầu, đơn vị cuối 06/09/2010
        donvi_dau_id: -1,
        donvi_cuoi_id: -1,
        ma_tb_dau: '', // Mã thuê bao đầu của thuê bao truyền số liệu
        ma_tb_cuoi: '', // mã thuê bao cuối của thuê bao truyền số liệu
        // Hiếu bổ sung phần khai báo cho hàm laytien 08/08/2010
        tien_cd: 0,

        CT_TIENHD_ID: -1,
        // Hiếu bổ sung phần khai báo cho hàm laytien --14/09/2010
        tien_tb: 0,
        vat_tb: 0,
        tien_dv: 0,
        vat_dv: 0,
        // private DataTable dtCTKM;//hiếu 16/09/2010: Lưu danh sách các khoản mục.
        //
        // TuấnNA thêm khai báo cho nút ngân sách nhà nước --- Ngày 12/11/2010
        // Bổ sung Tien_dm ngày 16.03.2011
        DV_QHNS: '',
        MACHUONG: '',
        MN_KT: '',
        MA_NDKT: '',
        MN_NSNN: '',
        TIEN_DM: '',
        //

        loaihd_id: -1,
        loaitb_id: -1,
        kieuld_id: -1,
        doituong_id: -1,
        ngaylhd: '',
        nhaphd_kt: '',

        ikieuld_id: 0, // Hiếu bs biến lưu kiểu lắp đặt đã chọn khi gõ từ ô text mã thuê bao - 23.09.2010

        // TuấnNA thêm code khai báo các biến tiểu ngành
        TNC1_ID: 0,
        TNC2_ID: 0,
        TNC3_ID: 0,
        TNC4_ID: 0,
        nganhnghe_id: 0,

        host_id: 0,
        host_cu_id: 0,

        sochinh: false,

        isdn_sochinh: false,
        matb_isdn_chinh: '',

        hdtb_id_cha: 0,
        thuebao_id_cha: 0,
        loaisochinh: 0,
        cuoc_tk: 0,
        cuoc_tc: 0,
        nhanvientc_id: 0,
        nhanvien_ql_am_id: 0,

        tocdotn_id: 0, // --ttt
        muccuoctn_id: 0, // --ttt
        quytrinh_id: 0,
        luong_id: 0,

        hdmig_id: 0,
        mig_id: 0,

        _loaitb_id: -1,
        _tocdo_id: -1,

        malt_dau: '',
        malt_cuoi: '',
        // longdx: bo sung phan khuyen mai
        dtienkmld: 0,
        dvatkmld: 0,
        tong_kmld: 0,
        tyle_kmld: 0,
        vhuong_kn: '',
        vloaikenh_id: 0,
        nhap_nvkd: 0,
        check_chuahoa_cntt: false,

        KetCuoi_ID: -1, // KieuNV:Thêm chọn kết cuối

        Lat_TB: -1,
        Lng_TB: -1,
        Lat_LD: -1,
        Lng_LD: -1,
        Lat_TT: -1,
        Lng_TT: -1,
        Lat_BC: -1,
        Lng_BC: -1,
        Lat_KH: -1,
        Lng_KH: -1,
        Lat_DD: -1,
        Lng_DD: -1,
        Lat_DC: -1,
        Lng_DC: -1,
        ts_kt_mst: 0,

        ctv_id: -1,
        donvi_ctv_id: -1,
        loainv_ctv_id: -1,
        nguoigt_id: -1,
        donvi_nguoi_gt_id: -1,
        loainv_nguoi_gt_id: -1,

        khong_batbuoc_ctv: 0,
        batbuoc_gioithieu_dichchuyen: 0,
        tudong_ganthem_hdtb_dv: false,

        DONVIQL_HTTT: 0,
        TUYENTHU_HTTT: 0,

        ts_sinhma_gd_theo_donvi: false, // Dungf cho Hni sinh ma GD theo don vi quan ly cua ng dung
        loi_sinh_magd: false,
        // #endregion

        khong_ht_tracuudb: 0,

        vkieukh_id_: 0, // biến toàn cục kiểm tra KH cá nhân hay doanh nghiệp
        ts_kt_mstmns_kh: 0, // Kiểm tra MST MNS khách hàng DN
        ts_kt_tthdkhdn: 0, // Kiểm tra cho phép thêm thông tin khách hàng DN  đối với PTTB KHDN mới xài

        phantap_kh: false,
        pt_doanhthu: 0,
        pt_diem_tn: 0,

        // #region HieuTc: Tối ưu code load fom
        kiemtra_dm_mgwan_tsl: false, // Danh dau de load cac thong tin danh muc Mgwan
        kiemtra_dm_codinh: false, // Danh dau de load cac thong tin danh muc Co dinh
        kiemtra_dm_IMS: false, // Danh dau de load cac thong tin danh muc IMS
        kiemtra_dm_adsl: false, // Danh dau de load cac thong tin danh muc ADSL
        kiemtra_dm_didong: false, // Danh dau de load cac thong tin danh muc ADSL
        kiemtra_dm_gphone: false, // Danh dau de load cac thong tin danh muc ADSL

        load_ds_nvtc: true,
        load_ds_donvi: true,
        load_ds_kmai: true,
        load_ds_tien: true,
        ktratt: 0,

        VPHIEUCSKH_ID: 0,
        VPHIEUCSKHDV_ID: 0,
        batbuoc_chon_dacdiem_dcld: false,
        // #endregion

        /// <summary>
        /// true: ca nhan; false: doanh nghiep
        /// </summary>
        kieukh_canhan: false,
        PHANLOAIKH_ID: -1,
        nhomplkh: 0
      },
      modelInput: {
        txtCongVan: '',
        txtGhiChuTB: '',
        txtMaDoiCap_CD: '',
        txtSoSimGphone: '',
        txtEmailAdsl: '',
        txtMATN_IMS: '',
        txtDiaChiDau: '',
        txtDiaChiCuoi: '',
        txtCuocTBI_TSL: '',
        txtCuocTCDT_TSL: '',
        txtCuocTK_DT: '',
        txtMa_HDDT_TSL: '',
        txtCuocHT_TSL: '',
        txtCuocTKDT_TSL: '',
        txtCuocTC_TSL: '',
        txtSoAoTSL_Cuoi: '',
        txtCuocTK_TSL: '',
        txtSoAoTSL_Dau: '',
        txtMaHD_TTKD_TSL: '',
        txtCuocTC_DT: '',
        txtMaHD_DT: '',
        txtCuocHTWan: '',
        txtSoAoWan: '',
        txtSite_ID: '',
        txtMaHD_TTKD_MGWAN: '',
        txtCuocTC: '',
        txtHuongKNWan: '',
        txtCuocTK: '',
        txtCTV: '',
        txtNguoiGT: '',
        txtTongTienHD: '',
        txtMaGD: '',
        txtMaHDcu: '',
        txtMaTB: '',
        txtMaTBMoi: '',
        txtTenTB: '',
        txtDiaChiLD: '',
        txtNgaySinhTB: '',
        txtMaERP: '',
        txtDiaChiTB: '',
        txtMaBSS: '',
        txtTongTien: 0,
        txtTongVat: 0,
        txtMaTN_CD: '',
        txtTongTienSauThue: 0,
        txtMaKH: '',
        txtTenKH: '',
        txtNgaySN: '',
        txtNgayHetHan: '',
        txtGiayToKem: '',
        txtPhanLoaiKH: '',
        txtChucDanhDD: '',
        txtNguoiDD: '',
        txtTelKH: '',
        txtFaxKH: '',
        txtHoKhau: '',
        txtNoiCapHK: '',
        txtNgayCapHK: '',
        txtMaSTKH: '',
        txtSoTKKH: '',
        txtMaIG: '',
        txtGhiChuKH: '',
        txtNhanVienQL: '',
        txtTenTT: '',
        txtMaNVTC: '',
        txtMaTuyen: '',
        txtDiaChiTT: '',
        txtChu_TK: '',
        txtSTKTT: '',
        txtMST: '',
        txtDiaChiCT: '',
        txtDiaChiBC: '',
        txtGhiChuTT: '',
        lvDvgt: '',
        lvDvkhac: '',
        lvwThanhToan: null,
        lvDvkhac_TT: '',
        lvwThueBao: [],
        txtKhuVuc: ''
      },
      modelCbo: {
        cboDonViQL: -1,
        vcboDonViQL: [],
        cboGiayToKH: -1,
        vcboGiayToKH: [],
        cboHTTT: -1,
        vcboHTTT: [],
        cboQuocTich: -1,
        vcboQuocTich: [],
        cboNganhNgheLoc: -1,
        vcboNganhNgheLoc: [],
        cboDanToc: -1,
        vcboDanToc: [],
        cboLoaiKH2: -1,
        vcboLoaiKH2: [],
        cboNganHangKH: -1,
        vcboNganHangKH: [],
        cboNganHangTT: -1,
        vcboNganHangTT: [],
        cboMaTbDD: -1,
        vcboMaTbDD: [],
        cboHTHSo: -1,
        vcboHTHSo: [],
        cboTrangBiCD: -1,
        vcboTrangBiCD: [],
        cboTrangBiGP: -1,
        vcboTrangBiGP: [],
        cboTrangBiIMS: -1,
        vcboTrangBiIMS: [],
        cboKhuyenMai: -1,
        vcboKhuyenMai: [],
        cboDaiVT: -1,
        vcboDaiVT: [],
        cboDoituongTB: -1,
        vcboDoituongTB: [],
        cboDichVuVT: -1,
        vcboDichVuVT: [],
        cboLoaihinhTB: -1,
        vcboLoaihinhTB: [],
        cboKieuLD: -1,
        vcboKieuLD: [],
        cboTT: -1,
        vcboTT: [],
        cboGoiKT: -1,
        vcboGoiKT: [],
        cboMucCuocTB: -1,
        vcboMucCuocTB: [],
        cboTramCap: -1,
        vcboTramCap: [],
        cboOMC: -1,
        vcboOMC: [],
        cboTramTC: -1,
        vcboTramTC: [],
        cboKieu_HD: -1,
        vcboKieu_HD: [],
        cboLoaiTBTSL_Dau: -1,
        vcboLoaiTBTSL_Dau: [],
        cboLoaiTBTSL_Cuoi: -1,
        vcboLoaiTBTSL_Cuoi: [],
        cboDoiTac_TSL: -1,
        vcboDoiTac_TSL: [],
        cboTramVTDau: -1,
        vcboTramVTDau: [],
        cboTramVTCuoi: -1,
        vcboTramVTCuoi: [],
        cboLoaiCap_Dau: -1,
        vcboLoaiCap_Dau: [],
        cboLoaiCap_Cuoi: -1,
        vcboLoaiCap_Cuoi: [],
        cboTrangBiADSL: -1,
        vcboTrangBiADSL: [],
        cboMucCuoc: -1,
        vcboMucCuoc: [],
        cboLoaiTBAdsl: -1,
        vcboLoaiTBAdsl: [],
        cboGoiCuocAdsl: -1,
        vcboGoiCuocAdsl: []
      },
      buttonState: {
        tsbtnNhapMoi: false,
        tsbtnGhiLai: false,
        tsbtnHuyBo: false,
        tsbtnXoa: false,
        tsbtnThanhToan: false,
        toolStripDropDownButton1: false,
        tsbtnXoaTB: false,
        tsbtnThemTB_DS: false,
        tsbtnThongTinLH: false,
        tsbtnEmail: false,
        tsbtnHen: false,
        tsbtnVatTuMoi: false,
        btnChonSM: false,
        btnMaNVKD: true,
        lblRequiredTel: false,
        btnThueSo: false
      },
      modelCkeckBox: {
        chkdisableCongVan: false,
        chkCongVan: false,
        chkDoiTac_TSL: false,
        chkNganHangKH: false,
        disalbeQT: false,
        disalbeDT: false,
        chkNguoiGT: false,
        disalbeNN: false,
        chkKhuyenMai: false,
        chkdisableKhuyenMai: false,
        chkTocDo_PIR: false,
        chkDoiTacWan: false,
        chkDC_Dau: false,
        chkDC_Cuoi: false,
        ckTuCaiADSL: false,
        chkDoiTac_ADSL: false,
        vbtnThueSo: false,
        checkVSVX: false,
        btnChonDCDau: false,
        btnChonDCCuoi: false,
        btnMaTN_IMS: false,
        btnMaTN_ADSL: false,
        btnMaTN_Wan: false,
        btnMaTN_CD: false,
        chkDC_NoiBo: false,
        cboDichVuVT: true
      },
      tempMaTB: null
    }
  },
  computed: {
    ThongTinKhachHang () {
      return this.parent.$refs.ThongTinKhachHang
    },
    nhanvienId() {
      return +this.$root.token.getNhanVienID()
    },
    donViId() {
      return +this.$root.token.getDonViID()
    },
  },
  methods: {
    clearTheSpace (st) {
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
    Catdaunhay (str) {
      str = str.replace('\'', '')
      return str
    },
    async  onEnterMaGD () {
      try {
        this.loading(true)
        this.modelInput.txtMaGD = this.clearTheSpace(this.modelInput.txtMaGD)
        this.modelInput.txtMaGD = this.Catdaunhay(this.modelInput.txtMaGD)
        if (this.modelInput.txtMaGD !== '') {
          let ds = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.modelInput.txtMaGD, Enum.LoaiHopDong.DI_CHUYEN, Enum.TrangThaiHD.MOI, 0)
          this.$emit('onEnterMaGD', ds)
          await this.HienThiTTHopDongKH(ds)
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 667 ~ onEnterMaGD ~ error', error)
      } finally {
        this.loading(false)
      }
    },
    btnLayMaGD_Click: async function () {
      this.$refs.popupSearchContract.showModal()
    },
    cboKieuLD_SelectedValueChanged: async function () {
      if (this.modelCbo.vcboKieuLD.length > 0) {
        if (this.modelCbo.cboKieuLD === null) return
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.CO_DINH) {
          let temp_ = await this.MAP_ID('NHOMKIEU_LD', 'CSS', 'kieu_ld', 'kieuld_id', this.modelCbo.cboKieuLD)
          if (temp_ && temp_.toString() === '1') { this.buttonState.btnThueSo = true } else { this.buttonState.btnThueSo = false }
        }
        this.inputValue.kieu_thueso = 0
        this.inputValue.noidung_thueso = ''
        this.inputValue.sothang_thueso = 0
        if (!this.inputValue.kt_load) {
          await this.LayTienTheoKhoanMuc()
          await this.HT_DS_KhuyenMai_Combobox()
        }
        this.dataSourceDV = {
          idDichVu: this.modelCbo.cboDichVuVT,
          idLoaihinhTb: this.modelCbo.cboLoaihinhTB,
          idKieuLd: this.modelCbo.cboKieuLD,
          idDaiVt: this.modelCbo.cboDaiVT
        }
      }
    },
    async LayTienTheoKhoanMuc () {
      try {
        if (this.inputValue.dichvuvt_id !== Enum.DichVuVienThong.ADSL && this.inputValue.dichvuvt_id !== Enum.DichVuVienThong.BRCD_DOITAC &&
                    this.inputValue.dichvuvt_id !== Enum.DichVuVienThong.MEGAWAN && 
                    this.inputValue.dichvuvt_id !== Enum.DichVuVienThong.METRONET && 
                    this.inputValue.dichvuvt_id !== Enum.DichVuVienThong.NOIBO_DADIEM && 
                    this.inputValue.dichvuvt_id !== Enum.DichVuVienThong.MGW_DOITAC) {
          this.inputValue.kh_cd = 0
          this.inputValue.ltb_adsl_id = 1
        }
        var itocdo_id = 0, imuccuoc_id = 0
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TSL || 
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TSL_DOITAC ||
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM
          ) {
          itocdo_id = this.$refs.refTSL.cboTocDoKenhTSL.Value
          this.inputValue.trangbi_id = this.$refs.refTSL.cboTrangBi.Value
        }
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || 
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.METRONET || 
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MGW_DOITAC ||
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_DADIEM
          ) {
          itocdo_id = this.$refs.refWan.cboTocDo.Value
          this.inputValue.trangbi_id = this.$refs.refWan.cboTrangBiWan.Value
        }
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.ADSL || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.BRCD_DOITAC) {
          itocdo_id = this.$refs.refAdsl.cboGoiCuocAdsl.Value
          imuccuoc_id = this.$refs.refAdsl.cboMucCuoc.Value
          this.inputValue.trangbi_id = this.$refs.refAdsl.cboTrangBiADSL.Value
        }
        console.log('🚀 ~ file: index.vue:763 ~ LayTienTheoKhoanMuc ~ this.inputValue.ltb_adsl_id:', this.inputValue.ltb_adsl_id)
        var ds = await this.LAY_TIEN_KHOANMUC_LD(Enum.LoaiHopDong.DI_CHUYEN, DateTimeLib.toDateDisplay(this.modelDateTime.dtpNgayYC), this.modelCbo.cboDichVuVT, Number(this.modelCbo.cboLoaihinhTB), this.modelCbo.cboKieuLD,
          this.inputValue.phuongld_id, itocdo_id, imuccuoc_id, this.inputValue.ltb_adsl_id, this.inputValue.ltb_adsl_moi_id, this.inputValue.ltb_adsl_cu_id, this.inputValue.tocdo_moi_id, this.inputValue.tocdo_cu_id, this.inputValue.kh_cd, this.inputValue.phold_id, this.inputValue.apld_id, this.inputValue.khuld_id, this.modelCbo.cboDoituongTB, this.inputValue.trangbi_id)
        if (ds !== null && ds.length > 0) {
          await this.HienThiTienLapDat(ds)
          await this.LayTongTien_HDTB()
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 671 ~ LayTienTheoKhoanMuc ~ error', error)
      }
    },
    async HienThiTienLapDat (ds) {
      var kt_lm = true
      var kt_km = true
      if (ds[0].id.toString() !== '-1') {
        for (var i = 0; i < ds.length; i++) {
          if (ds[i].khoanmuctt_id.toString() === Enum.KHOANMUC_TT.KMTT_CHUYENDICH.toString()) {
            this.inputValue.dtienld = parseFloat(ds[i].tien)
            this.inputValue.dvatld = parseFloat(ds[i].vat)
            kt_lm = false
          }
        }
        if (kt_lm === true) {
          this.inputValue.dtienld = 0
          this.inputValue.dvatld = 0
        }
      } else {
        this.inputValue.dtienld = 0
        this.inputValue.dvatld = 0
      }
      for (let j = 0; j < this.inputValue.ds_tien_khoanmuc.length; j++) {
        if (this.inputValue.ds_tien_khoanmuc[j].khoanmuctt_id.toString() === Enum.KHOANMUC_TT.KMTT_CHUYENDICH.toString()) {
          this.inputValue.ds_tien_khoanmuc[j].tien = this.inputValue.dtienld
          this.inputValue.ds_tien_khoanmuc[j].vat = this.inputValue.dvatld
        }
      }
    },
    async LayTongTien_HDTB () {
      try {
        if (this.inputValue.ds_tien_khoanmuc !== null) {
          var dtongtien = 0
          var dtongvat = 0
          for (let i = 0; i < this.inputValue.ds_tien_khoanmuc.length; i++) {
            if (this.inputValue.ds_tien_khoanmuc[i].khoanmuctt_id.toString() === Enum.KHOANMUC_TT.KMTT_KM_LAPDAT.toString()) {
              dtongtien += Math.abs(parseFloat(this.inputValue.ds_tien_khoanmuc[i].tien)) * -1
              dtongvat += Math.abs(parseFloat(this.inputValue.ds_tien_khoanmuc[i].vat)) * -1
            } else {
              dtongtien += parseFloat(this.inputValue.ds_tien_khoanmuc[i].tien)
              dtongvat += parseFloat(this.inputValue.ds_tien_khoanmuc[i].vat)
            }
          }
          this.modelInput.txtTongTien = currency(dtongtien)
          this.modelInput.txtTongVat = currency(dtongvat)
          this.lblTongTien = currency(dtongtien + dtongvat)
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 712 ~ LayTongTien_HDTB ~ error', error)
      }
    },
    async LAY_TIEN_KHOANMUC_LD (
      loaihd_id, ngay_yc, dichvuvt_id, loaitb_id,
      kieuld_id, phuongld_id, tocdo_id, muccuoc_id, ltb_adsl_id, ltb_adsl_moi_id,
      ltb_adsl_cu_id, tocdo_moi_id, tocdo_cu_id, kh_cd, phold_id, apld_id, khuld_id,
      doituong_id, trangbi_id
    ) {
      try {
        let data = {
          loaihd_id,
          ngay_yc,
          dichvuvt_id,
          loaitb_id,
          kieuld_id,
          phuongld_id,
          tocdo_id,
          muccuoc_id,
          ltb_adsl_id,
          ltb_adsl_moi_id,
          ltb_adsl_cu_id,
          tocdo_moi_id,
          tocdo_cu_id,
          kh_cd,
          phold_id,
          apld_id,
          khuld_id,
          doituong_id,
          trangbi_id
        }
        console.log('🚀 ~ file: index.vue ~ line 673 ~ data', data)
        let response = await this.$root.context.post(`/web-hopdong/chuyendich/lay_tien_khoanmuc_ld_v2`, data)
        return response.data
      } catch (error) {
        return []
      }
    },
    async _XOA_HDTB (hdtb_id) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/chuyendich/xoa_hdtb?hdtb_id=` + hdtb_id)
        return response.data
      } catch (error) {
        return []
      }
    },
    async XoaHDTB () {
      try {
        await this._XOA_HDTB(this.inputValue.hdtb_id)
        var LAY_DS_HOPDONG_TB_THEO_HDKH_ID = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.inputValue.hdkh_id)
        await this.HienThiDanhSacHDTB(LAY_DS_HOPDONG_TB_THEO_HDKH_ID)
        if (this.modelInput.lvwThueBao.length > 0) {
          this.inputValue.hdtb_id = this.modelInput.lvwThueBao[0].hdtb_id
          var LAY_DS_HOPDONG_TB_THEO_HDTB_ID = await this.LAY_DS_HOPDONG_TB_THEO_HDTB_ID(this.inputValue.hdtb_id)
          await this.HienThiTTHopDongTB(LAY_DS_HOPDONG_TB_THEO_HDTB_ID)
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 805 ~ XoaHDTB ~ error', error)
      }
    },
    async LAY_DS_HOPDONG_TB_THEO_HDTB_ID (hdtb_id) {
      try {
        var rs = await this.$root.context.post('/web-hopdong/thaydoiloaihinhtb/lay_ds_hopdong_tb_theo_hdtb_id', {hdtb_id})
        return this.UpperCasePropertyList(rs.data)
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 838 ~ LAY_DS_HOPDONG_TB_THEO_HDTB_ID ~ error', error)
        return []
      }
    },
    cboLoaihinhTB_SelectedValueChanged: async function () {
      if (this.modelCbo.vcboLoaihinhTB.length > 0) {
        if (this.modelCbo.cboLoaihinhTB == null) return
        this.inputValue.loaitb_id = this.modelCbo.cboLoaihinhTB
        let dichvu_vt = this.modelCbo.cboDichVuVT
        let vdc_noibo = 0
        if (this.modelCkeckBox.chkDC_NoiBo) {
          vdc_noibo = 1
        }
        let _loaitb_id = this.inputValue.loaitb_id
        console.log('🚀 ~ file: index.vue:895 ~ _loaitb_id:', _loaitb_id)
        let dtKieuLD = await this.dsDanhMuc.kieu_ld.filter(
          x =>
            x.loaitb_id.toString() === _loaitb_id.toString() &&
            x.tinhkhac.toString() !== '2' &&
            x.dc_noibo.toString() === vdc_noibo.toString()
        )
        console.log('🚀 ~ file: index.vue:902 ~ this.dsDanhMuc.kieu_ld:', this.dsDanhMuc.kieu_ld)
        console.log('🚀 ~ file: index.vue:902 ~ dtKieuLD:', dtKieuLD)
        if (dtKieuLD.length > 0) {
          dtKieuLD = dtKieuLD.sort(function (a, b) { return a.thutu - b.thutu })
          this.modelCbo.vcboKieuLD = dtKieuLD
          if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.ADSL && this.inputValue.ikieuld_id !== 0) {
            this.modelCbo.cboKieuLD = Number(this.inputValue.ikieuld_id)
          } else {
            if (this.modelCbo.cboKieuLD && this.modelCbo.cboKieuLD > -1) {
              const found = this.modelCbo.vcboKieuLD.some(el => el.kieuld_id.toString() === this.modelCbo.cboKieuLD.toString())
              if (!found) {
                this.modelCbo.cboKieuLD = Number(this.modelCbo.vcboKieuLD[0].kieuld_id)
              }
            } else {
              this.modelCbo.cboKieuLD = Number(this.modelCbo.vcboKieuLD[0].kieuld_id)
            }
          }
          await this.cboKieuLD_SelectedValueChanged()
        } else {
          this.modelCbo.vcboKieuLD = []
          this.modelCbo.cboKieuLD = -1
        }
      }
    },
    cboDichVuVT_SelectedValueChanged: async function () {
      if (this.modelCbo.vcboDichVuVT.length > 0) {
        if (this.modelCbo.cboDichVuVT == null) return
        this.inputValue.dichvuvt_id = parseInt(this.modelCbo.cboDichVuVT)

        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || 
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TSL || 
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.METRONET ||
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_DADIEM ||
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM
          ) {
          this.modelCkeckBox.chkdisableCongVan = false
        } else {
          this.modelCkeckBox.chkdisableCongVan = true
          this.modelInput.txtCongVan = ''
        }

        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.CO_DINH) {
          this.buttonState.btnChonSM = true
        } else {
          this.buttonState.btnChonSM = false
        }
        await this.GiaoDien_DichVuVT(this.inputValue.dichvuvt_id)
        this.dataSourceDV = {
          idDichVu: this.modelCbo.cboDichVuVT,
          idLoaihinhTb: this.modelCbo.cboLoaihinhTB,
          idKieuLd: this.modelCbo.cboKieuLD,
          idDaiVt: this.modelCbo.cboDaiVT
        }
      }
    },
    LAY_LOAIHINH_TB: async function (dichvuvt_id) {
      if (dichvuvt_id != 0 && this.glbDanhMuc_Chung != null) {
        var dt = this.glbDanhMuc_Chung.loaihinh_tb.filter(
          x => x.dichvuvt_id === dichvuvt_id
        )
        if (dt.length > 0) {
          return dt
        } else {
          return this.glbDanhMuc_Chung.loaihinh_tb
        }
      } else {
        return this.glbDanhMuc_Chung.loaihinh_tb
      }
    },
    LAY_DS_SUDUNG_DVGT: async function (thuebao_id) {
      await this.LAY_DS_THUEBAO_DICHVU(0, thuebao_id, this.inputValue.loaitb_id, 1)
      await this.HT_DichVuGT()
    },
    LAY_DS_THUEBAO_DICHVU: async function (hdtbId, thueBaoId, loaiTbId, kieuId) {
      try {
        let data = {
          hdtbId: hdtbId,
          thueBaoId: thueBaoId,
          loaiTbId: loaiTbId,
          kieuId: kieuId
        }
        let res = await this.$root.context.post('web-hopdong/hopdong/lay_ds_thuebao_dichvu', data)
        return res.data
      } catch (error) {
        return []
      }
    },
    gankhuvuc_close () {
      this.$bvModal.hide('gankhuvuc')
    },
    async txtMaTB_KeyPress () {
      try {
        this.loading(true)
        this.modelInput.txtMaTB = this.clearTheSpace(this.modelInput.txtMaTB)
        this.modelInput.txtMaTB = this.Catdaunhay(this.modelInput.txtMaTB)
        if (this.modelInput.txtMaTB !== '') {
          var data = {
            maTb: this.modelInput.txtMaTB,
            loaiHdId: Enum.LoaiHopDong.DI_CHUYEN,
            ttHdId: Enum.TrangThaiHD.MOI,
            donViDlId: 0,
            dichVuVtId: this.modelCbo.cboDichVuVT,
            loaiTbId: 0
          }
          console.log('🚀 ~ file: index.vue ~ line 882 ~ txtMaTB_KeyPress ~ data', data)
          var rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_hopdong_theo_matb', data)
          var lay_ds_hopdong_theo_matb = this.UpperCasePropertyList(rs.data)
          if (rs.data.length > 0) {
            this.modelInput.txtDiaChiLD = ''
            this.modelInput.txtDiaChiTB = ''
            this.modelInput.txtNgaySinhTB = ''
            await this.HienThiTTHopDongKH(lay_ds_hopdong_theo_matb)
            this.$emit('txtMaTB_KeyPress', {
              loai: 1,
              data: lay_ds_hopdong_theo_matb
            })
          } else {
            this.inputValue.ikieuld_id = Number(this.modelCbo.cboKieuLD)
            this.modelInput.txtDiaChiLD = ''
            this.modelInput.txtDiaChiTB = ''
            this.modelInput.txtNgaySinhTB = ''
            if (this.inputValue.hdkh_id !== 0) {
              var ds = await this.LAY_DANHBA_THEO_MATB(this.modelInput.txtMaTB, this.inputValue.dichvuvt_id, 0)
              if (ds && ds.length > 0) {
                await this.HienThiTT_HopDong_ThueBao(this.LowerCasePropertyList(ds))
                
                this.$emit('txtMaTB_KeyPress', {
                  loai: 2,
                  data: ds
                })
              } else {
                let dv = this.modelCbo.vcboDichVuVT.find(e => e.dichvuvt_id === this.inputValue.dichvuvt_id)
                this.$root.$toast.error('Không tìm thấy thông tin thuê bao ' + this.modelInput.txtMaTB + ' ứng với dịch vụ ' + dv.ten_dvvt)
              }
            } else {
              await this.HienThiTT_DanhBa_KH_NhanCQ(this.modelInput.txtMaTB, 1)
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1005 ~ txtMaTB_KeyPress ~ error', error)
      } finally {
        this.inputValue.load_ds_nvtc = true
        this.inputValue.load_ds_donvi = true
        this.inputValue.load_ds_kmai = true
        this.inputValue.load_ds_tien = true
        this.loading(false)
      }
    },
    async LAY_DS_HOPDONG_THEO_MA_GD (in_ma_gd, in_loaihd_id, in_tthd_id, in_donvi_dl_id) {
      try {
        let data = {in_ma_gd, in_loaihd_id, in_tthd_id, in_donvi_dl_id}
        let res = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/lay_ds_hopdong_theo_ma_gd', data)
        if (res.error_code === 'BSS-00000204' && res.data === null) {
          this.$root.$toast.error(res.message)
          this.$refs.ma_tb.focus()
          return []
        }
        return this.UpperCasePropertyList(res.data)
      } catch (error) {
        return []
      }
    },
    async HienThiTTHopDongKH (ds) {

      try {
        if (ds && ds.length > 0) {
          let row = ds[0]
          this.inputValue.ikieuld_id = 0
          this.inputValue.hdkh_id = parseInt(row.HDKH_ID)
          this.modelInput.txtMaGD = row.MA_GD
          this.modelDateTime.dtpNgayYC = new Date(row.NGAY_YC)
          if (row.KHACHHANG_ID !== null) {
            this.inputValue.khachhang_id = parseInt(row.KHACHHANG_ID)
          } else {
            this.inputValue.khachhang_id = 0
          }
          await this.HienThiDS_HDTT_CBO(this.inputValue.hdkh_id)
          let LAY_DS_HOPDONG_TB_THEO_HDKH_ID = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.inputValue.hdkh_id)
          await this.HienThiTTHopDongTB(LAY_DS_HOPDONG_TB_THEO_HDKH_ID)
          await this.HienThiDanhSacHDTB(LAY_DS_HOPDONG_TB_THEO_HDKH_ID)
          this.inputValue.kieuld_ds = parseInt(this.modelCbo.cboKieuLD)
          this.$parent.SetButton(3)
        } else {
          this.modelInput.txtMaGD = ''
          this.$parent.SetButton(0)
        }
      } catch (error) {

      }
    },
    async acceptSearchContract (item) {
      this.modelInput.txtMaGD = item.ma_gd
      this.$refs.ma_gd.focus()
      if (this.modelInput.txtMaGD !== '') {
        let ds = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.modelInput.txtMaGD, Enum.LoaiHopDong.DI_CHUYEN, Enum.TrangThaiHD.MOI, 0)
        this.$emit('onEnterMaGD', ds)
        await this.HienThiTTHopDongKH(ds)
      }
    },
    async HienThiTT_HopDong_ThueBao (ds) {

      if (ds && ds.length > 0) {
        if (this.inputValue.ktratt === 1) {
          if (parseInt(ds[0].trangthaitb_id) !== 1) {
            this.$root.$toast.error('Thuê bao' + ds[0].ma_tb + ' đang ở trạng thái: ' + ds[0].trangthai_tb + '\nBạn không thể thực hiện chuyển dịch cho thuê bao này')
            return false
          }
        } else {
          if (parseInt(ds[0].trangthaitb_id) !== 1 && parseInt(ds[0].trangthaitb_id) !== 2 && parseInt(ds[0].trangthaitb_id) !== 4 /*&& parseInt(ds[0].trangthaitb_id) !== 6 */) {
            this.$root.$toast.error('Thuê bao' + ds[0].ma_tb +
                                                ' đang ở trạng thái: ' + ds[0].trangthai_tb +
                                                '\nBạn không thể thực hiện chuyển dịch cho thuê bao này')
            return false
          }
        }

        console.log('HienThiTT_HopDong_ThueBao', ds)
        this.modelInput.txtMaTB = ds[0].ma_tb
        this.inputValue.thuebao_id = parseInt(ds[0].thuebao_id)
        this.modelInput.txtDiaChiTB = ds[0].diachi_tb
        this.modelInput.txtDiaChiLD = ds[0].diachi_ld
        this.modelInput.txtTenTB = ds[0].ten_tb
        this.inputValue.ma_tbmoi = ''
        this.modelInput.txtMaTBMoi = ''
        this.inputValue.vloaikenh_id = ds[0].loaikenh_id

        let f = this.modelCbo.vcboDaiVT.find(x => x.donvi_id.toString() === ds[0].donviql_id.toString())
        if (f) {
          this.modelCbo.cboDaiVT = parseInt(ds[0].donviql_id)
        } else {
          if (this.modelCbo.vcboDaiVT.length > 0) {
            this.modelCbo.cboDaiVT = parseInt(this.modelCbo.vcboDaiVT[0].donvi_id)
          }
        }

        if (ds[0].ngay_sn !== null) { this.modelInput.txtNgaySinhTB = DateTimeLib.toDateDisplay(ds[0].ngay_sn) } else { this.modelInput.txtNgaySinhTB = null }
        var LAY_DS_DIACHI_THEO_DBTBID = await this.LAY_DS_DIACHI_THEO_DBTBID(this.inputValue.thuebao_id)
        await this.HienThiTTDiaChi(LAY_DS_DIACHI_THEO_DBTBID, 3)
        this.modelCbo.cboDoituongTB = ds[0].doituong_id
        this.modelCbo.cboLoaihinhTB = parseInt(ds[0].loaitb_id)
        this.inputValue._loaitb_id = parseInt(ds[0].loaitb_id)

        await this.cboLoaihinhTB_SelectedValueChanged()
        this.$emit('HienThiTT_HopDong_ThueBao', ds)

        if (ds[0].mucuoctb_id !== null) {
          this.modelCbo.vcboMucCuocTB = await this.HT_MucCuocTB_Combobox(ds[0].mucuoctb_id)
          if (this.modelCbo.vcboMucCuocTB.length > 0) {
            this.modelCbo.cboMucCuocTB = this.modelCbo.vcboMucCuocTB[0].MUCUOCTB_ID
          }
        } else {
          this.modelCbo.vcboMucCuocTB = []
          this.modelCbo.cboMucCuocTB = -1
        }
        await this.HienThiTTMoRongTB(this.inputValue.thuebao_id, this.inputValue.dichvuvt_id)
        await this.LAY_DS_SUDUNG_DVGT(this.inputValue.thuebao_id)
        var dsNV = await this.LAY_DS_KHUVUC_THEO_HD_DB(this.inputValue.thuebao_id, 2)
        this.modelInput.txtKhuVuc = ''
        this.inputValue.khuvuc_id = 0
        if (dsNV !== null && dsNV.length > 0) {
          this.modelInput.txtKhuVuc = dsNV[0].TEN_KV
          this.inputValue.khuvuc_id = parseInt(dsNV[0].KHUVUC_ID)
          this.dsHDTB_KV = []
          var dr = {}
          dr.HDTB_ID = this.inputValue.hdtb_id
          dr.KHUVUC_ID = this.inputValue.khuvuc_id
          dr.LOAIKV_ID = Enum.LOAI_KV.KHUVUC_DIABAN
          dr.KIEUKV_ID = 2
          this.dsHDTB_KV.push(dr)
        }
        await this.LAY_DS_SUDUNG_DVK(this.inputValue.thuebao_id, 1)
        await this.HT_DS_KhuyenMai_Combobox()
      }
    },
    async TaoDuLieu_CT_TIENHD (themmoi) {
      try {
        this.dsCTTHD = []
        for (let index = 0; index < this.inputValue.ds_tien_khoanmuc.length; index++) {
          const element = this.inputValue.ds_tien_khoanmuc[index]
          console.log('m_dsThueBao_DichVu', this.inputValue.m_dsThueBao_DichVu)
          if (element.khoanmuctt_id.toString() === Enum.KHOANMUC_TT.KMTT_DICHVU.toString()) {
            
            for (let index = 0; index < this.inputValue.m_dsThueBao_DichVu.length; index++) {
              
              const e = this.inputValue.m_dsThueBao_DichVu[index]
              if (e.dangKy.toString() === '1' && e.huy.toString() === '0') {
                let rowCTTHD = {}
                rowCTTHD.HDTB_ID = this.inputValue.hdtb_id
                if (themmoi === true) {
                  this.inputValue.CT_TIENHD_ID = await this.GetKey('CT_TIENHD', 'CSS')
                  rowCTTHD.CT_TIENHD_ID = this.inputValue.CT_TIENHD_ID
                }
                rowCTTHD.KHOANMUCTT_ID = Enum.KHOANMUC_TT.KMTT_DICHVU
                let tien = 0// Tiền dịch vụ chưa khuyến mại
                let vat = 0// Vat dịch vụ chưa khuyến mại
                tien = parseFloat(e.cuocLd)
                rowCTTHD.TIEN = tien
                vat = e.vatLd
                rowCTTHD.VAT = vat
                rowCTTHD.ID = parseFloat(e.dichVuGtId)
                rowCTTHD.LOAI_ID = 2
                this.dsCTTHD.push(rowCTTHD)
              }
            }
          } else {
            let rowCTTHD = {}
            rowCTTHD.HDTB_ID = this.inputValue.hdtb_id
            if (themmoi === true) {
              this.inputValue.CT_TIENHD_ID = await this.GetKey('CT_TIENHD', 'CSS')
              rowCTTHD.CT_TIENHD_ID = this.inputValue.CT_TIENHD_ID
            }
            rowCTTHD.KHOANMUCTT_ID = parseInt(element.khoanmuctt_id)
            rowCTTHD.TIEN = parseFloat(element.tien)
            rowCTTHD.VAT = parseFloat(element.vat)
            if (element.khoanmuctt_id === Enum.KHOANMUC_TT.KMTT_KM_LAPDAT ||
                       element.khoanmuctt_id === Enum.KHOANMUC_TT.KMTT_KM_THIETBI ||
                       element.khoanmuctt_id === Enum.KHOANMUC_TT.KMTT_KM_DATCOC) {
              rowCTTHD.TIEN = Math.abs(rowCTTHD.TIEN) * -1
              rowCTTHD.VAT = Math.abs(rowCTTHD.VAT) * -1
            }
            rowCTTHD.ID = parseInt(element.khoanmuctt_id)
            rowCTTHD.LOAI_ID = 1
            this.dsCTTHD.push(rowCTTHD)
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 978 ~ TaoDuLieu_CT_TIENHD ~ error', error)
      }
    },
    async HienThiTT_DanhBa_KH_NhanCQ (ma_tb, kieu_id) {
      try {
        this.inputValue.load_ds_nvtc = false
        this.inputValue.load_ds_donvi = false
        var ds = []
        if (kieu_id === 0) {
          ds = await this.LAY_DB_THEO_MATB(ma_tb, 0, 0)
        } else {
          ds = await this.LAY_DANHBA_THEO_MATB(
            ma_tb,
            this.modelCbo.cboDichVuVT,
            0
          )
        }
        this.inputValue.kieu_thueso = 0
        this.inputValue.noidung_thueso = ''
        this.inputValue.sothang_thueso = 0
        if (ds.length > 0) {
          if (ds.length > 1) {
            this.loading(false)
            let _ds = [...ds].map(item => {
              item.dichvuvt_id = item.DICHVUVT_ID
              item.loaitb_id = item.LOAITB_ID
              item.ma_tb = item.MA_TB
              return item
            })
            let result = await this.$refs.thuebaocungma_dc.show(_ds)
            ds = result.itemSelected
          }
          this.loading(true)
          this.thongtin = {...ds[0]}
          this.$emit('HienThiTT_DanhBa_KH_NhanCQ', ds)
          this.inputValue.thanhtoan_id = this.thongtin.THANHTOAN_ID
          this.modelInput.txtMaTB = this.thongtin.MA_TB
          if (this.inputValue.ktratt === 1) {
            if (this.thongtin.TRANGTHAITB_ID !== 1) {
              this.$root.$toast.error(
                'Thuê bao' +
                  this.thongtin.MA_TB +
                  ' đang ở trạng thái: ' +
                  this.thongtin.TRANGTHAI_TB +
                  '\nBạn không thể thực hiện chuyển dịch cho thuê bao này'
              )
              this.loading(false)
              return
            }
          } else {
            if (
              this.thongtin.TRANGTHAITB_ID !== Enum.TrangThaiTB.THUONG &&
              this.thongtin.TRANGTHAITB_ID !== Enum.TrangThaiTB.KHOA_MOTCHIEU_YEUCAU &&
              this.thongtin.TRANGTHAITB_ID !== Enum.TrangThaiTB.KHOA_HAICHIEU_YEUCAU &&
              this.thongtin.TRANGTHAITB_ID !== Enum.TrangThaiTB.TAMDUNG
            ) {
              this.$root.$toast.error(
                'Thuê bao' +
                  this.thongtin.MA_TB +
                  ' đang ở trạng thái: ' +
                  this.thongtin.TRANGTHAI_TB +
                  '\nBạn không thể thực hiện chuyển dịch cho thuê bao này'
              )
              this.loading(false)
              return
            }
          }
          if (this.thongtin.KY_HD === 0) {
            this.$root.$toast.error(
              'Khách hàng có mã thuê bao: ' +
                this.modelInput.txtMaTB +
                ' chưa ký lại hợp đồng gốc \nĐề nghị ký lại hợp đồng gốc'
            )
            this.loading(false)
            return
          }

          this.inputValue.ikieuld_id = 0

          this.inputValue.khachhang_id = parseInt(this.thongtin.KHACHHANG_ID)
          this.inputValue.khach_hang_id = parseInt(this.thongtin.KHACHHANG_ID)
          this.inputValue.khachhang_id_cu = parseInt(this.thongtin.KHACHHANG_ID)

          if (this.thongtin.NGAYLAP_HD !== null) {
            this.modelDateTime.dtpNgayLapHD = new Date(this.thongtin.NGAYLAP_HD)
          }
          if (kieu_id === 1) {
            this.modelInput.txtMaHDcu = this.thongtin.MA_HD
            this.modelInput.txtDiaChiTB = this.ChuanHoaTen(this.thongtin.DIACHI_TB)
            this.modelInput.txtDiaChiLD = this.ChuanHoaTen(this.thongtin.DIACHI_LD)
            this.inputValue.thuebao_id = parseInt(this.thongtin.THUEBAO_ID)
            var dsNV = await this.LAY_DS_KHUVUC_THEO_HD_DB(
              this.inputValue.thuebao_id,
              2
            )
            this.modelInput.txtKhuVuc = ''
            this.inputValue.khuvuc_id = 0
            if (dsNV.length > 0) {
              this.modelInput.txtKhuVuc = dsNV[0].TEN_KV
              this.inputValue.khuvuc_id = parseInt(dsNV[0].KHUVUC_ID)
              try {
                this.dsHDTB_KV = []
                var HDTB_KVRow = {}
                HDTB_KVRow.HDTB_ID = this.inputValue.hdtb_id
                HDTB_KVRow.KHUVUC_ID = this.inputValue.khuvuc_id
                HDTB_KVRow.LOAIKV_ID = Enum.LOAI_KV.KHUVUC_DIABAN
                HDTB_KVRow.KIEUKV_ID = 2
                this.dsHDTB_KV.push(HDTB_KVRow)
              } catch (error) {
                console.log('🚀 ~ file: index.vue ~ line 1066 ~ HienThiTT_DanhBa_KH_NhanCQ ~ error', error)
              }
            }
            this.modelInput.txtMaTB = this.thongtin.MA_TB
            this.modelInput.txtTenTB = this.ChuanHoaTen(this.thongtin.TEN_TB)
            this.modelCbo.cboDaiVT = this.thongtin.DONVIQL_ID

            let _ds = this.modelCbo.vcboDaiVT.find(x => x.donvi_id.toString() === this.modelCbo.cboDaiVT.toString())
            if (_ds === undefined && this.modelCbo.vcboDaiVT.length > 0) {
              this.modelCbo.cboDaiVT = this.modelCbo.vcboDaiVT[0].donvi_id
            }
            if (this.thongtin.NGAY_SN !== null) {
              this.modelInput.txtNgaySinhTB = DateTimeLib.toDateDisplay(this.thongtin.NGAY_SN)
            } else {
              this.modelInput.txtNgaySinhTB = ''
            }
            var LAY_DS_DIACHI_THEO_DBTBID = await this.LAY_DS_DIACHI_THEO_DBTBID(this.inputValue.thuebao_id)
            await this.HienThiTTDiaChi(LAY_DS_DIACHI_THEO_DBTBID, 3)
            this.modelCbo.cboDoituongTB = this.thongtin.DOITUONG_ID
            this.inputValue.load_ds_donvi = false
            this.modelCbo.cboLoaihinhTB = this.thongtin.LOAITB_ID
            await this.cboLoaihinhTB_SelectedValueChanged()
            this.inputValue._loaitb_id = this.thongtin.LOAITB_ID
            this.inputValue.loaitb_id = this.thongtin.LOAITB_ID
            this.inputValue.vloaikenh_id = this.thongtin.LOAIKENH_ID
            if (parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.ANTOAN_BAOMAT_TT || parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.DICHVU_CNTT ||
              parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.TRUNGTAM_DULIEU || parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.HOINGHI_TRUYENHINH) {
              await this.$refs.refCNTT.LoadThongTin_DV_CNTT(this.modelCbo.cboLoaihinhTB)
            }
            await this.HienThiTTMoRongTB(this.inputValue.thuebao_id, this.inputValue.dichvuvt_id)
            this.inputValue.load_ds_donvi = true
            if (this.thongtin.MUCUOCTB_ID !== null) {
              let arr = await this.HT_MucCuocTB_Combobox(
                this.thongtin.MUCUOCTB_ID
              )
              if (arr.length > 0) {
                this.modelCbo.vcboMucCuocTB = arr
                this.modelCbo.cboMucCuocTB =  this.thongtin.MUCUOCTB_ID // arr[0].MUCUOCTB_ID
              } else {
                this.modelCbo.vcboMucCuocTB = []
                this.modelCbo.cboMucCuocTB = -1
              }
            }
            this.inputValue.m_dsThueBao_DichVu = await this.LAY_DS_THUEBAO_DICHVU(0, this.inputValue.thuebao_id, this.inputValue.loaitb_id, 1)
            console.log('🚀 ~ file: index.vue:1356 ~ HienThiTT_DanhBa_KH_NhanCQ ~ this.inputValue.m_dsThueBao_DichVu:', this.inputValue.m_dsThueBao_DichVu)
            var dset = await this.LAY_DS_SUDUNG_DVGT(this.inputValue.thuebao_id)
            console.log('🚀 ~ file: index.vue:1357 ~ HienThiTT_DanhBa_KH_NhanCQ ~ dset:', dset)
            await this.HT_DichVuGT_DB(dset)
            await this.LAY_DS_SUDUNG_DVK(this.inputValue.thuebao_id, 1)
            await this.HT_DS_KhuyenMai_Combobox()
          }
        } else {
          this.thongtin = null
          let dv = this.modelCbo.vcboDichVuVT.find(e => e.dichvuvt_id === this.inputValue.dichvuvt_id)
          this.$root.$toast.error(
            'Không tìm thấy thông tin về thuê bao ' + this.modelInput.txtMaTB + ' đối với dịch vụ ' + dv.ten_dvvt
          )
          this.$parent.SetButton(1)
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1295 ~ HienThiTT_DanhBa_KH_NhanCQ ~ error', error)
      }
    },
    async GetDataSql (P_MAP, P_VALUE) {
      try {
        let data = {
          loai: P_MAP,
          value: P_VALUE
        }
        console.log('🚀 ~ file: index.vue ~ line 1302 ~ GetDataSql ~ data', data)
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/getdata', data)
        if (rs.data !== null && rs.data.length > 0) {
          return rs.data
        } else return []
      } catch (error) {
        return []
      }
    },
    async KiemTraDuLieuNhap () {
      return true
    },
    async CapNhat (themmoi) {
      try {
        let d = await this.CHAN_DICHUYEN_KH_CQSD()
        if (d.length > 0) {
          let KemTraDL_HD_CQ = await this.KemTraDL_HD_CQ(this.inputValue.thuebao_id)
          if (!KemTraDL_HD_CQ) return false
        }
        await this.KiemTraDuLieuNhap()
        if (this.modelCbo.cboKieuLD !== Enum.KieuLapDat.DC_TINH_KHAC) {
          let KIEMTRA_MATB_KHOSO = await this.KIEMTRA_MATB_KHOSO(this.modelInput.txtMaTB, this.inputValue.dichvuvt_id)
          if (!KIEMTRA_MATB_KHOSO) {
            this.$root.$toast.error(
              'Số máy chưa có trong kho số !'
            )
            return false
          }
          if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.CO_DINH || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.IMS || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.GPHONE) {
            if (this.modelInput.txtMaTBMoi !== '') {
              let KIEMTRA_MATB_KHOSO = await this.KIEMTRA_MATB_KHOSO(this.modelInput.txtMaTBMoi, this.inputValue.dichvuvt_id)
              if (!KIEMTRA_MATB_KHOSO) {
                this.$root.$toast.error(
                  'Số máy chưa có trong kho số !'
                )
                return false
              }
            }
          }
        }
        if (this.inputValue.batbuoc_sdt) {
          if (this.ThongTinKhachHang.txtTelKH === '') {
            this.$root.$toast.error(
              'Số điện thoại khách hàng không được để trống !'
            )
            return false
          }
        }
        if (this.inputValue.nhap_nvkd === 1) {
          if (this.inputValue.khuvuc_id === 0 || this.modelInput.txtKhuVuc === '') {
            this.$root.$toast.error('Bạn chưa chọn khu vực quản lý địa bàn cho thuê bao!')
            return false
          }
        }
        this.inputValue.tocdotn_id = 0
        this.inputValue.muccuoctn_id = 0
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.ADSL || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.BRCD_DOITAC) {
          this.inputValue.tocdotn_id = parseInt(this.$refs.refAdsl.cboGoiCuocAdsl.Value)
          if (this.inputValue.loaitb_id === Enum.LoaiHinhTB.INTERNET_ADSL || this.inputValue.loaitb_id === Enum.LoaiHinhTB.INTERNET_FTTH ||
                         this.inputValue.loaitb_id === Enum.LoaiHinhTB.WIFI_FIBER) {
            this.inputValue.muccuoctn_id = parseInt(this.$refs.refAdsl.cboMucCuoc.Value)
          }
        } else if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MEGA_EYES) {
          this.inputValue.tocdotn_id = parseInt(this.$refs.refAdsl.cboGoiCuocAdsl.Value)
        } else if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TSL || 
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TSL_DOITAC ||
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM
          ) {
          this.inputValue.tocdotn_id = parseInt(this.$refs.refTSL.cboTocDoKenhTSL.Value)
        } else if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || 
                    this.inputValue.dichvuvt_id === Enum.DichVuVienThong.METRONET ||
                    this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_DADIEM ||
                    this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MGW_DOITAC) {
          this.inputValue.tocdotn_id = parseInt(this.$refs.refWan.cboTocDo.Value)
        } else if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.ANTOAN_BAOMAT_TT || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.DICHVU_CNTT ||
                    this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TRUNGTAM_DULIEU || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.HOINGHI_TRUYENHINH) {
          this.inputValue.tocdotn_id = this.inputValue._tocdo_id
        }
        this.inputValue.ma_tb_phu_30bd = ''
        if (themmoi) {
          let KIEMTRA_DB_KHI_THEMMOI = await this.KIEMTRA_DB_KHI_THEMMOI(this.modelInput.txtMaTB, 0, this.inputValue.thuebao_id, this.inputValue.dichvuvt_id)
          if (!KIEMTRA_DB_KHI_THEMMOI) {
            this.$root.$toast.error('Thuê bao không tồn tại trong danh bạ, không cập nhật được')
            return false
          }
          if (this.inputValue.loaitb_id === Enum.LoaiHinhTB.ISDN2B_CD || this.inputValue.loaitb_id === Enum.LoaiHinhTB.ISDN2B_CQ ||
                        this.inputValue.loaitb_id === Enum.LoaiHinhTB.ISDN30B_CD || this.inputValue.loaitb_id === Enum.LoaiHinhTB.ISDN30B_CQ ||
                        this.inputValue.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_2M || this.inputValue.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_THUONG ||
                        this.inputValue.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_TUONGTU) {
            this.inputValue.isdn_sochinh = this.$refs.refCD.isdn_sochinh
            if (this.inputValue.isdn_sochinh) {
              for (var i = 0; i < this.inputValue.ds_isdn.length; i++) {
                let dr = this.inputValue.ds_isdn[i]
                let kiemtra = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(dr.ma_tb, 0, this.modelCbo.cboDichVuVT, Enum.LoaiHopDong.DI_CHUYEN)
                if (kiemtra) return false
              }
            }
          }
          let kiemtra = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.modelInput.txtMaTB, 0, this.modelCbo.cboDichVuVT, Enum.LoaiHopDong.DI_CHUYEN)
          if (kiemtra) return false
          if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.CO_DINH || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.IMS || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.GPHONE) {
            if (this.modelInput.txtMaTB !== this.modelInput.txtMaTBMoi) {
              let kiemtra = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.modelInput.txtMaTBMoi, 0, this.inputValue.dichvuvt_id, Enum.LoaiHopDong.DI_CHUYEN)
              if (kiemtra) return false
            }
          }
          // let dset = await this.LayHuongGiao_TiepNhan(Enum.LoaiHopDong.DI_CHUYEN, this.modelCbo.cboLoaihinhTB, this.inputValue.tocdotn_id, this.inputValue.muccuoctn_id, this.modelCbo.cboKieuLD)
          let dset = await this.LayHuongGiao_TiepNhan_V2(Enum.LoaiHopDong.DI_CHUYEN, 
            this.modelCbo.cboLoaihinhTB, 
            this.inputValue.tocdotn_id, 
            this.inputValue.muccuoctn_id, 
            this.modelCbo.cboKieuLD,
            this.inputValue.vloaikenh_id,
            this.modelCbo.cboDichVuVT)

          if (dset && dset.length === 0) {
            this.$root.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
            return false
          }
          let huonggiao_tn = 0
          this.inputValue.luong_id = 0
          if (this.inputValue.kt_fiber_colapchung) {
            let dt_kt = await this.kt_fiber_colapchung(1, this.inputValue.thuebao_id, this.inputValue.hdtb_id, this.inputValue.hdkh_id)
            if (dt_kt && dt_kt.length > 0) {
              if (dt_kt.MA_TB !== '') {
                this.$root.$toast.error('Thuê bao ' + this.modelInput.txtMaTB + ' đang có đường IMS/MyTV lắp cùng. Bạn phải làm HĐ dịch chuyển IMS/MyTV của mã thuê bao ' + dt_kt.MA_TB + ' trước !')
                return false
              }
            }
          }
          huonggiao_tn = Number(dset[0].huonggiao_id)
          this.inputValue.quytrinh_id = Number(dset[0].quytrinh_id)
          this.inputValue.luong_id = Number(dset[0].luong_id)
          await this.TaoDuLieu(true)
          if (this.inputValue.loi_sinh_magd) {
            this.inputValue.loi_sinh_magd = false
            return false
          }
          if (this.inputValue.kt_adsl === false) { return false }

          if (this.dsHDTB_CNTT.length === 0 && this.dsHDTB_CD.length === 0 && this.dsHDTB_ADSL.length === 0 && this.dsHDTB_TSL.length === 0 && this.dsHDTB_MEWAN.length === 0 && this.dsHDTB_GP.length === 0 && this.dsHDTB_IMS.length === 0) {
            this.$root.$toast.error('Lỗi khởi tạo dữ liệu cho hợp đồng dịch chuyển, bạn hãy liên hệ Admin để xử lý!')
            return false
          }

          let strkt = await this.Kiemtra_khuvuc_NLML(this.inputValue.quanld_id, this.inputValue.phuongld_id, this.inputValue.phold_id,
            this.inputValue.apld_id, this.inputValue.khuld_id, this.inputValue.dacdiemld_id, Number(this.modelCbo.cboDichVuVT))
          if (strkt !== 'ok') {
            try {
              var confirm = await this.$confirm(strkt + '\r\nBạn có muốn thay đổi địa chỉ để tiếp tục?',
                'Thông báo',
                {
                  confirmButtonText: 'Có',
                  cancelButtonText: 'Không',
                  type: 'warning'
                })
            } catch (error) {
              kiemtra = await this.ThemMoi_HopDong_Sub_V3(this.inputValue.hdtb_id, huonggiao_tn, this.dsHDKH, this.dsHDTT, this.dsHDTB, this.dsHDTBDV, this.dsHDTB_CD, this.dsHDTB_ADSL, this.dsHDTB_TSL, this.dsDiaChi, this.dsDiaChiHDKH, this.dsDiaChiHDTT, this.dsDiaChiHDTB, this.dsDK_DVGT, this.dsDK_DVK, this.dsHDTB_MEWAN, this.dsHDTB_GP, this.dsCTTHD, this.dsHDTB_IMS, this.dsKM_HDTB, this.dsHDTB_KV, this.dsHDTT_NV, this.dsHDKH_Sub, this.dsHDTB_Sub, this.dsHDTB_CNTT)
              if (!kiemtra) {
                this.$root.$toast.error('Lỗi thêm hợp đồng dịch chuyển, bạn hãy liên hệ Admin để xử lý!')
                return false
              }
              await this.Thoaitra_hdtb_khongNLML(this.dsHDKH[0].HDKH_ID, this.dsHDTB[0].HDTB_ID)
              this.$root.$toast.error('Hợp đồng đã được thoái trả. Khách hàng sẽ được đưa vào danh sách Khách hàng tiềm năng!')
              this.SetButton(0)
              this.$parent.SetButton(0)
              return true
            }
            return true
          } else {
            kiemtra = await this.ThemMoi_HopDong_Sub_V3(this.inputValue.hdtb_id, huonggiao_tn, this.dsHDKH, this.dsHDTT, this.dsHDTB, this.dsHDTBDV, this.dsHDTB_CD, this.dsHDTB_ADSL, this.dsHDTB_TSL, this.dsDiaChi, this.dsDiaChiHDKH, this.dsDiaChiHDTT, this.dsDiaChiHDTB, this.dsDK_DVGT, this.dsDK_DVK, this.dsHDTB_MEWAN, this.dsHDTB_GP, this.dsCTTHD, this.dsHDTB_IMS, this.dsKM_HDTB, this.dsHDTB_KV, this.dsHDTT_NV, this.dsHDKH_Sub, this.dsHDTB_Sub, this.dsHDTB_CNTT)
            if (!kiemtra) {
              this.$root.$toast.error('Lỗi thêm hợp đồng dịch chuyển, bạn hãy liên hệ Admin để xử lý!')
              return false
            }
          }
          await this.ThongTinKhachHang.CapNhat_DiaChiBC()
          if (this.modelInput.txtMaTBMoi !== '') {
            await this.CAPNHAT_KHOSO(this.modelInput.txtMaTBMoi, '', this.inputValue.dichvuvt_id)
          } else {
            await this.CAPNHAT_KHOSO(this.modelInput.txtMaTB, '', this.inputValue.dichvuvt_id)
          }
          this.inputValue.ma_tb = this.modelInput.txtMaTB
          if (this.inputValue.tudong_ganthem_hdtb_dv) { await this.tudong_ganthem_hdtb_dv(this.inputValue.hdtb_id) }
          if (this.inputValue.loaitb_id === Enum.LoaiHinhTB.ISDN2B_CD || this.inputValue.loaitb_id === Enum.LoaiHinhTB.ISDN2B_CQ ||
                        this.inputValue.loaitb_id === Enum.LoaiHinhTB.ISDN30B_CD || this.inputValue.loaitb_id === Enum.LoaiHinhTB.ISDN30B_CQ ||
                        this.inputValue.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_2M || this.inputValue.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_THUONG ||
                        this.inputValue.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_TUONGTU) {
            if (this.inputValue.isdn_sochinh) {
              if (this.inputValue.host_cu_id === this.inputValue.host_id) {
                this.inputValue.hdtb_id_cha = this.inputValue.hdtb_id
                for (let index = 0; index < this.inputValue.ds_isdn.length; index++) {
                  const dr = this.inputValue.ds_isdn[index]
                  if (dr.thuebao_cha_id !== null) {
                    this.inputValue.thuebao_id = parseInt(dr.thuebao_id)
                    this.modelInput.txtMaTB = dr.ma_tb.toString()
                    this.inputValue.ma_tb_phu_30bd = dr.ma_tb.toString()
                    this.inputValue.doituong_id = parseInt(dr.doituong_id)
                    let KiemTraDL_ThueBao = await this.KiemTraDL_ThueBao()
                    if (!KiemTraDL_ThueBao()) return
                    await this.KiemTraDuLieuNhap()
                    let KIEMTRA_MATB_KHOSO = await this.KIEMTRA_MATB_KHOSO(this.modelInput.txtMaTB, this.inputValue.dichvuvt_id)
                    if (!KIEMTRA_MATB_KHOSO) {
                      this.$root.$toast.error('Số máy chưa có trong kho số !')
                      return
                    }
                    let KIEMTRA_HDTB_KHI_THEMMOI = await this.KIEMTRA_HDTB_KHI_THEMMOI(this.modelInput.txtMaTB, this.inputValue.hdkh_id, this.inputValue.dichvuvt_id, 0)
                    if (KIEMTRA_HDTB_KHI_THEMMOI) {
                      this.$root.$toast.error('Thuê bao đã tồn tại trong hợp đồng, không thêm mới được.')
                      return
                    }
                    let KIEMTRA_DB_KHI_THEMMOI = await this.KIEMTRA_DB_KHI_THEMMOI(this.modelInput.txtMaTB, 0, this.inputValue.thuebao_id, this.inputValue.dichvuvt_id)
                    if (!KIEMTRA_DB_KHI_THEMMOI) {
                      this.$root.$toast.error('Thuê bao không tồn tại trong danh bạ, không cập nhật được.')
                      return
                    }
                    let KIEMTRA_TB_CUNG_MAKH_TB_PL = await this.KIEMTRA_TB_CUNG_MAKH_TB_PL(this.modelInput.txtMaGD, this.inputValue.thuebao_id)
                    if (!KIEMTRA_TB_CUNG_MAKH_TB_PL) {
                      this.$root.$toast.error('Thuê bao không cùng mã khách hàng với thuê bao đang thực hiện chuyển dịch !')
                      return
                    }
                    let KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.modelInput.txtMaTB, 0, this.modelCbo.cboDichVuVT, Enum.LoaiHopDong.DI_CHUYEN)
                    if (KIEMTRA_MA_TB_LAP_HOPDONG_KHAC) return
                    this.inputValue.tocdotn_id = 0
                    this.inputValue.muccuoctn_id = 0
                    if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.ADSL || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.BRCD_DOITAC) {
                      this.inputValue.tocdotn_id = parseInt(this.$refs.refAdsl.cboGoiCuocAdsl.Value)
                      if (this.inputValue.loaitb_id === Enum.LoaiHinhTB.INTERNET_ADSL || this.inputValue.loaitb_id === Enum.LoaiHinhTB.INTERNET_FTTH ||
                                                 this.inputValue.loaitb_id === Enum.LoaiHinhTB.WIFI_FIBER) {
                        this.inputValue.muccuoctn_id = parseInt(this.$refs.refAdsl.cboMucCuoc.Value)
                      }
                    } else if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MEGA_EYES) {
                      this.inputValue.tocdotn_id = parseInt(this.$refs.refAdsl.cboGoiCuocAdsl.Value)
                    } else if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TSL || 
                      this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TSL_DOITAC ||
                      this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM
                      ) {
                      this.inputValue.tocdotn_id = parseInt(this.$refs.refTSL.cboTocDoKenhTSL.Value)
                    } else if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN ||
                                            this.inputValue.dichvuvt_id === Enum.DichVuVienThong.METRONET ||
                                            this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_DADIEM ||
                                            this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MGW_DOITAC) {
                      this.inputValue.tocdotn_id = parseInt(this.$refs.refWan.cboTocDo.Value)
                    }
                    let dset = await this.LayHuongGiao_TiepNhan_V2(Enum.LoaiHopDong.DI_CHUYEN, 
                      parseInt(this.modelCbo.cboLoaihinhTB), 
                      this.inputValue.tocdotn_id, 
                      this.inputValue.muccuoctn_id, 
                      parseInt(this.modelCbo.cboKieuLD),
                      this.inputValue.vloaikenh_id,
                      this.modelCbo.cboDichVuVT
                      )
                    huonggiao_tn = 0
                    if (dset && dset.length <= 0) {
                      this.$root.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
                      return
                    }
                    huonggiao_tn = parseInt(dset[0].huonggiao_id)
                    this.inputValue.quytrinh_id = parseInt(dset[0].quytrinh_id)
                    await this.TaoDuLieu_HDTB(true)
                    await this.ThongTinKhachHang.TaoDuLieu_HDTT(false)
                    await this.TaoDuLieu_CT_TIENHD(true)
                    await this.TaoDuLieu_DiaChi(true, 3)
                    await this.TaoDuLieu_DKDV()
                    await this.CapNhat_HDTB_ID_HDTB_KV()

                    this.dsHDTB_CD = []
                    this.dsHDTB_ADSL = []
                    this.dsHDTB_TSL = []
                    this.dsHDTB_MEWAN = []
                    this.dsHDTB_GP = []
                    this.dsHDTB_IMS = []
                    this.dsHDTBDV = []
                    this.dsHDTB_CNTT = []
                    switch (this.inputValue.dichvuvt_id) {
                      case Enum.DichVuVienThong.CO_DINH:
                        await this.TaoDuLieu_HDTB_CD(true)
                        break
                      case Enum.DichVuVienThong.IMS:
                        await this.TaoDuLieu_HDTB_IMS(true)
                        break
                      case Enum.DichVuVienThong.ADSL:
                        await this.TaoDuLieu_HDTB_ADSL(true)
                        break
                      case Enum.DichVuVienThong.MEGA_EYES:
                        await this.TaoDuLieu_HDTB_ADSL(true)
                        break
                      case Enum.DichVuVienThong.MEGAWAN:
                      case Enum.DichVuVienThong.METRONET:
                      case Enum.DichVuVienThong.NOIBO_DADIEM:
                        await this.TaoDuLieu_HDTB_MGWAN(true)
                        break
                      case Enum.DichVuVienThong.TSL:
                      case Enum.DichVuVienThong.NOIBO_2DIEM:
                        await this.TaoDuLieu_HDTB_TSL(true)
                        break
                      case Enum.DichVuVienThong.GPHONE:
                        await this.TaoDuLieu_HDTB_GP(true)
                        break
                      case Enum.DichVuVienThong.ANTOAN_BAOMAT_TT:
                      case Enum.DichVuVienThong.DICHVU_CNTT:
                      case Enum.DichVuVienThong.TRUNGTAM_DULIEU:
                      case Enum.DichVuVienThong.HOINGHI_TRUYENHINH:
                        await this.TaoDuLieu_HDTB_CNTT(true)
                        this.dsHDTBDV = await this.$refs.refCNTT.TaoChoBang_HDTB_DV()
                        for (const element of this.dsHDTBDV) {
                          element.HDTB_ID = this.inputValue.hdtb_id
                        }
                        break
                      default:
                        await this.TaoDuLieu_HDTB_CD(true)
                        break
                    }
                    await this.DichChuyen_ThemMoi_HopDongTB_2(this.inputValue.hdtb_id, this.dsHDTB, this.dsHDTBDV, this.dsHDTB_CD, this.dsHDTB_ADSL, this.dsHDTB_TSL, this.dsDiaChi, this.dsDiaChiHDTB, this.dsDK_DVGT,
                      this.dsDK_DVK, this.dsHDTB_MEWAN, this.dsHDTB_GP, this.dsTTTT, this.dsCTTHD, this.dsHDTT, this.dsHDTB_IMS, huonggiao_tn,
                      this.dsHDTB_KV, null, null)
                    if (this.modelInput.txtMaTBMoi !== '') { await this.CAPNHAT_KHOSO(this.modelInput.txtMaTBMoi, '', this.inputValue.dichvuvt_id) } else { await this.CAPNHAT_KHOSO(this.modelInput.txtMaTB, '', this.inputValue.dichvuvt_id) }
                    this.inputValue.ma_tb = this.modelInput.txtMaTB
                    if (this.inputValue.tudong_ganthem_hdtb_dv) { await this.tudong_ganthem_hdtb_dv(this.inputValue.hdtb_id) }
                  }
                }

                await this.TUDONG_GAN_CAP_THUEBAO(this.inputValue.hdtb_id)
                await this.TUDONG_GAN_HODAN_THUEBAO(this.inputValue.hdtb_id)
                let LAY_DS_HOPDONG_TB_THEO_HDKH_ID = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.inputValue.hdkh_id)
                await this.HienThiDanhSacHDTB(LAY_DS_HOPDONG_TB_THEO_HDKH_ID)
              } else {
                this.$root.$toast.error('Thuê bao ISDN dịch chuyển khác host phải thực hiện đổi số lần lượt cho tất cả số phụ !')
                return false
              }
            }
          }
          if (this.inputValue.bDuLieuKS) {
            await this.CAPNHAT_TRANGTHAI_HD(this.inputValue.iHdtbID, Enum.TrangThaiHD.DA_LAY_DU_LIEU)
            this.modelCkeckBox.cboDichVuVT = true
            this.inputValue.bDuLieuKS = false
          }
          await this.Them_MyTV_vao_goi_giadinh()
          let ds_hdtb_tmp = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.inputValue.hdkh_id)
          await this.HienThiDanhSacHDTB(ds_hdtb_tmp)
          await this.HienThiTTHopDongTB(ds_hdtb_tmp)

          let ds_hdtt_tmp = await this.ThongTinKhachHang.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.inputValue.hdkh_id)
          await this.ThongTinKhachHang.HienThiTTHopDongTT(ds_hdtt_tmp)
          await this.ThongTinKhachHang.HienThiDanhSacHDTT(ds_hdtt_tmp)

          await this.HienThiDS_HDTT_CBO(this.inputValue.hdkh_id)
          this.$parent.SetButton(3)
        } else {
          if (this.modelInput.txtMaTBMoi === '') {
            if (!this.KiemTraTBTrongHD(this.modelInput.txtMaTB, this.modelInput.lvwThueBao, 3)) {
              this.$root.$toast.error('Không có mã thuê bao ' + this.modelInput.txtMaTB + ' trong HĐ/PL. Hãy nhập lại mã thuê bao')
              return false
            }
          }
          // let ds = await this.LayHuongGiao_TiepNhan(Enum.LoaiHopDong.DI_CHUYEN, this.modelCbo.cboLoaihinhTB, this.inputValue.tocdotn_id, this.inputValue.muccuoctn_id, this.modelCbo.cboKieuLD)
          let ds = await this.LayHuongGiao_TiepNhan_V2(Enum.LoaiHopDong.DI_CHUYEN, 
              this.modelCbo.cboLoaihinhTB, 
              this.inputValue.tocdotn_id, 
              this.inputValue.muccuoctn_id, 
              this.modelCbo.cboKieuLD,
              this.inputValue.vloaikenh_id,
              this.modelCbo.cboDichVuVT
              )
          let huonggiao_tn = 0
          this.inputValue.luong_id = 0
          if (ds.length === 0) {
            this.$root.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
            return false
          }
          let KIEMTRA_DB_KHI_THEMMOI = await this.KIEMTRA_DB_KHI_THEMMOI(this.modelInput.txtMaTB, 0, this.inputValue.thuebao_id, this.inputValue.dichvuvt_id)
          if (!KIEMTRA_DB_KHI_THEMMOI) {
            this.$root.$toast.error('Thuê bao không tồn tại trong danh bạ, không cập nhật được!!!')
            return false
          }
          huonggiao_tn = parseInt(ds[0].huonggiao_id)
          this.inputValue.quytrinh_id = parseInt(ds[0].quytrinh_id)
          this.inputValue.luong_id = parseInt(ds[0].luong_id)
          await this.TaoDuLieu(false)
          if (this.inputValue.kt_adsl === false) { return false }
          let capnhathopdong = await this.CapNhat_HopDong_v3(
            this.inputValue.hdtb_id, this.inputValue.hdtt_id, this.dsHDKH, this.dsHDTT, this.dsHDTB, this.dsHDTBDV, this.dsHDTB_CD, this.dsHDTB_ADSL, this.dsHDTB_TSL, this.dsDiaChi,
            this.dsDiaChiHDKH, this.dsDiaChiHDTT, this.dsDiaChiHDTB, this.dsDK_DVGT, this.dsDK_DVK, this.dsHDTB_MEWAN, this.dsHDTB_GP, this.dsTTTT,
            this.dsCTTHD, this.dsHDTB_IMS, this.dsKM_HDTB, this.dsHDTB_KV, this.dsHDTT_NV, this.dsHDTB_CNTT
          )
          if (!capnhathopdong) {
            this.$root.$toast.error('Lỗi cập nhật hợp đồng dịch chuyển!!!')
            return false
          }
          await this.ThongTinKhachHang.CapNhat_DiaChiBC()
          await this.Update_huonggiao_tiepnhan(this.inputValue.hdtb_id, huonggiao_tn)
          if (this.modelInput.txtMaTBMoi !== '') {
            await this.CAPNHAT_KHOSO(this.modelInput.txtMaTBMoi, '', this.inputValue.dichvuvt_id)
          } else {
            await this.CAPNHAT_KHOSO(this.modelInput.txtMaTB, '', this.inputValue.dichvuvt_id)
          }
          if (this.inputValue.tudong_ganthem_hdtb_dv) { await this.tudong_ganthem_hdtb_dv(this.inputValue.hdtb_id) }
          let LAY_DS_HOPDONG_TB_THEO_HDKH_ID = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.inputValue.hdkh_id)
          await this.HienThiDanhSacHDTB(LAY_DS_HOPDONG_TB_THEO_HDKH_ID)
          await this.HienThiDS_HDTT_CBO(this.inputValue.hdkh_id)

          let LAY_DS_HOPDONG_TT_THEO_HDKH_ID = await this.ThongTinKhachHang.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.inputValue.hdkh_id)
          await this.ThongTinKhachHang.HienThiDanhSacHDTT(LAY_DS_HOPDONG_TT_THEO_HDKH_ID)
          this.$parent.SetButton(3)
        }
        this.inputValue.ma_tb = this.modelInput.txtMaTB
        this.inputValue.kieuld_ds = this.modelCbo.cboKieuLD
        this.$root.$toast.success('Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!')
        if (this.inputValue.VPHIEUCSKHDV_ID > 0) {
          let nhanvien_id = this.$root.token.getNhanVienID()
          let donvi_id = this.$root.token.getDonViID()
          let nguoi_cn = this.$root.token.getUserName()
          let ip_cn = 'oneBSS'
          let may_cn = 'oneBSS'
          await this.b2a_ht_phieucskhdv(this.inputValue.VPHIEUCSKHDV_ID, nhanvien_id, donvi_id, nguoi_cn, ip_cn, may_cn, 1)
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1747 ~ CapNhat ~ error', error)
        this.$root.$toast.error('Lỗi thực hiện cập nhật hợp đồng dịch chuyển, liên hệ Admin để xử lý!!!' + '\r\n' + error)
      }
    },
    async Thoaitra_hdtb_khongNLML (hdkh_id, hdtb_id) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/thoaitra_hdtb_khongnlml', {hdkh_id, hdtb_id})
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1776 ~ Thoaitra_hdtb_khongNLML ~ error', error)
      }
    },
    async KIEMTRA_HDTB_KHI_THEMMOI (maTb, hdkhId, dichVuVtId, loaiTbId) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_hdtb_khi_themmoi', {maTb, hdkhId, dichVuVtId, loaiTbId})
        return rs.data.toString() === '1'
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1744 ~ KIEMTRA_HDTB_KHI_THEMMOI ~ error', error)
        return false
      }
    },
    async TUDONG_GAN_HODAN_THUEBAO (hdtb_id) {
      try {
        let data = {
          hdtb_id,
          nguoi_cn: this.$root.token.getUserName(),
          may_cn: 'oneBSS',
          ip_cn: 'oneBSS'
        }
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/tudong_gan_hodan_thuebao', data)
        return rs.data.toString() === '1'
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1759 ~ TUDONG_GAN_HODAN_THUEBAO ~ error', error)
        return false
      }
    },
    async TUDONG_GAN_CAP_THUEBAO (hdtb_id) {
      try {
        let data = {
          hdtb_id,
          nguoi_cn: this.$root.token.getUserName(),
          may_cn: 'oneBSS',
          ip_cn: 'oneBSS'
        }
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/tudong_gan_cap_thuebao', data)
        return rs.data.toString() === '1'
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1774 ~ TUDONG_GAN_CAP_THUEBAO ~ error', error)
        return false
      }
    },
    async acceptTienKM (data) {
      try {
        console.log('acceptTienKM', data)
        this.inputValue.ds_tien_khoanmuc = data
        if (this.inputValue.ds_tien_khoanmuc.length > 0) {
          for (let index = 0; index < this.inputValue.ds_tien_khoanmuc.length; index++) {
            const e = this.inputValue.ds_tien_khoanmuc[index]
            if (e.khoanmuctt_id.toString() === Enum.KHOANMUC_TT.KMTT_CHUYENDICH.toString()) {
              this.inputValue.dtienld = parseInt(e.tien)
              this.inputValue.dvatld = parseInt(e.vat)
            }
            if (e.khoanmuctt_id.toString() === Enum.KHOANMUC_TT.KMTT_DICHVU.toString()) {
              this.inputValue.dtien_dv = parseInt(e.tien)
              this.inputValue.dvat_dv = parseInt(e.vat)
            }
            await this.LayTongTien_HDTB()
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1604 ~ acceptTienKM ~ error', error)
      }
    },
    async KIEMTRA_TB_CUNG_MAKH_TB_PL (ma_gd, thuebao_id) {
      try {
        let data = {
          maTb: ma_gd, thuebaoId: thuebao_id
        }
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_tb_cung_makh_tb_pl', data)
        return rs.data.toString() === '1'
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1807 ~ KIEMTRA_TB_CUNG_MAKH_TB_PL ~ error', error)
        return false
      }
    },
    async b2a_ht_phieucskhdv (phieucskhdv_id, nhanvien_id, donvi_id, nguoi_cn, ip_cn, may_cn, kieu) {
      try {
        let data = {
          phieucskhdv_id, nhanvien_id, donvi_id, nguoi_cn, ip_cn, may_cn, kieu
        }
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/b2a_ht_phieucskhdv', data)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1819 ~ b2a_ht_phieucskhdv ~ error', error)
        return []
      }
    },
    async Update_huonggiao_tiepnhan (hdtb_id, huonggiao_id) {
      try {
        let data = {
          hdtb_id,
          huonggiao_id
        }
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/update_huonggiao_tiepnhan', data)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1832 ~ Update_huonggiao_tiepnhan ~ error', error)
        return []
      }
    },
    async CapNhat_HopDong (
      hdtb_id, hdtt_id, dsHDKH, dsHDTT, dsHDTB, dsHDTBDV, dsHDTB_CD, dsHDTB_ADSL, dsHDTB_TSL, dsDiaChi,
      dsDiaChiHDKH, dsDiaChiHDTT, dsDiaChiHDTB, dsDK_DVGT, dsDK_DVK, dsHDTB_MEWAN, dsHDTB_GP, dsTTTT,
      dsCTTHD, dsHDTB_IMS, dsKM_HDTB, dsHDTB_KV, dsHDTT_NV
    ) {
      try {
        const newArray = [...dsDiaChi].map(item => {
          item.BL_OCK = item.BLOCK
          delete item.BLOCK
          return item
        })
        const newArray2 = [...dsHDKH].map(item => {
          delete item.NGAY_YC
          return item
        })

        let data = {
          HDTB_ID: hdtb_id,
          HDTT_ID: hdtt_id,
          dsHDKH: JSON.stringify(newArray2),
          dsHDTT: JSON.stringify(dsHDTT),
          dsHDTB: JSON.stringify(dsHDTB),
          dsHDTBDV: JSON.stringify(dsHDTBDV),
          dsHDTB_CD: JSON.stringify(dsHDTB_CD),
          dsHDTB_ADSL: JSON.stringify(dsHDTB_ADSL),
          dsHDTB_TSL: JSON.stringify(dsHDTB_TSL),
          dsDiaChi: JSON.stringify(newArray),
          dsDiaChiHDKH: JSON.stringify(dsDiaChiHDKH),
          dsDiaChiHDTT: JSON.stringify(dsDiaChiHDTT),
          dsDiaChiHDTB: JSON.stringify(dsDiaChiHDTB),
          dsDK_DVGT: JSON.stringify(dsDK_DVGT),
          dsDK_DVK: JSON.stringify(dsDK_DVK),
          dsHDTB_MEWAN: JSON.stringify(dsHDTB_MEWAN),
          dsHDTB_GP: JSON.stringify(dsHDTB_GP),
          dsTTTT: JSON.stringify(dsTTTT),
          dsCTTHD: JSON.stringify(dsCTTHD),
          dsHDTB_IMS: JSON.stringify(dsHDTB_IMS),
          dsKM_HDTB: JSON.stringify(dsKM_HDTB),
          dsHDTB_KV: JSON.stringify(dsHDTB_KV),
          dsHDTT_NV: JSON.stringify(dsHDTT_NV)
        }
        console.log('🚀 ~ file: index.vue ~ CapNhat_HopDong line 1772 ~ data', data)
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/capnhat_hopdong', data)
        return true
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ CapNhat_HopDong line 1777 ~ error', error)
        return false
      }
    },
    async CapNhat_HopDong_v3 (
      hdtb_id, hdtt_id, dsHDKH, dsHDTT, dsHDTB, dsHDTBDV, dsHDTB_CD, dsHDTB_ADSL, dsHDTB_TSL, dsDiaChi,
      dsDiaChiHDKH, dsDiaChiHDTT, dsDiaChiHDTB, dsDK_DVGT, dsDK_DVK, dsHDTB_MEWAN, dsHDTB_GP, dsTTTT,
      dsCTTHD, dsHDTB_IMS, dsKM_HDTB, dsHDTB_KV, dsHDTT_NV, dsHDTB_CNTT
    ) {
      try {
        const newArray = [...dsDiaChi].map(item => {
          item.BL_OCK = item.BLOCK
          delete item.BLOCK
          return item
        })
        const newArray2 = [...dsHDKH].map(item => {
          delete item.NGAY_YC
          return item
        })
        let dsCNTT = await this.generateInsert(hdtb_id, dsHDTB_CNTT)
        let data = {
          HDTB_ID: hdtb_id,
          HDTT_ID: hdtt_id,
          dsHDKH: JSON.stringify(newArray2),
          dsHDTT: JSON.stringify(dsHDTT),
          dsHDTB: JSON.stringify(dsHDTB),
          dsHDTBDV: JSON.stringify(dsHDTBDV),
          dsHDTB_CD: JSON.stringify(dsHDTB_CD),
          dsHDTB_ADSL: JSON.stringify(dsHDTB_ADSL),
          dsHDTB_TSL: JSON.stringify(dsHDTB_TSL),
          dsDiaChi: JSON.stringify(newArray),
          dsDiaChiHDKH: JSON.stringify(dsDiaChiHDKH),
          dsDiaChiHDTT: JSON.stringify(dsDiaChiHDTT),
          dsDiaChiHDTB: JSON.stringify(dsDiaChiHDTB),
          dsDK_DVGT: JSON.stringify(dsDK_DVGT),
          dsDK_DVK: JSON.stringify(dsDK_DVK),
          dsHDTB_MEWAN: JSON.stringify(dsHDTB_MEWAN),
          dsHDTB_GP: JSON.stringify(dsHDTB_GP),
          dsTTTT: JSON.stringify(dsTTTT),
          dsCTTHD: JSON.stringify(dsCTTHD),
          dsHDTB_IMS: JSON.stringify(dsHDTB_IMS),
          dsKM_HDTB: JSON.stringify(dsKM_HDTB),
          dsHDTB_KV: JSON.stringify(dsHDTB_KV),
          dsHDTT_NV: JSON.stringify(dsHDTT_NV),
          dsHDTB_CNTT: JSON.stringify(dsCNTT)
        }
        console.log('🚀 ~ file: index.vue ~ CapNhat_HopDong_v3 line 1772 ~ data', data)
        // return false
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/capnhat_hopdong_v3', data)
        console.log('🚀 ~ file: index.vue:1973 ~ rs:', rs)
        return true
        // return false
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ CapNhat_HopDong_v3 line 1777 ~ error', error)
        return false
      }
    },
    async ThemMoi_HopDong_Sub_V3 (hdtb_id, huonggiao_tn, dsHDKH, dsHDTT, dsHDTB, dsHDTBDV, dsHDTB_CD, dsHDTB_ADSL, dsHDTB_TSL, dsDiaChi, dsDiaChiHDKH, dsDiaChiHDTT, dsDiaChiHDTB, dsDK_DVGT, dsDK_DVK, dsHDTB_MEWAN, dsHDTB_GP, dsCTTHD, dsHDTB_IMS, dsKM_HDTB, dsHDTB_KV, dsHDTT_NV, dsHDKH_Sub, dsHDTB_Sub, dsHDTB_CNTT) {
      try {
        const newArray = [...dsDiaChi].map(item => {
          item.BL_OCK = item.BLOCK
          delete item.BLOCK
          return item
        })
        let dsCNTT = await this.generateInsert(hdtb_id, dsHDTB_CNTT)
        let data = {
          HDTB_ID: hdtb_id,
          HUONGGIAO_TN: huonggiao_tn,
          dsHDKH: JSON.stringify(dsHDKH),
          dsHDTT: JSON.stringify(dsHDTT),
          dsHDTB: JSON.stringify(dsHDTB),
          dsHDTBDV: JSON.stringify(dsHDTBDV),
          dsHDTB_CD: JSON.stringify(dsHDTB_CD),
          dsHDTB_ADSL: JSON.stringify(dsHDTB_ADSL),
          dsHDTB_TSL: JSON.stringify(dsHDTB_TSL),
          dsHDTB_MEWAN: JSON.stringify(dsHDTB_MEWAN),
          dsHDTB_GP: JSON.stringify(dsHDTB_GP),
          dsHDTB_IMS: JSON.stringify(dsHDTB_IMS),
          dsDIACHI: JSON.stringify(newArray),
          dsDIACHIHDKH: JSON.stringify(dsDiaChiHDKH),
          dsDIACHIHDTT: JSON.stringify(dsDiaChiHDTT),
          dsDIACHIHDTB: JSON.stringify(dsDiaChiHDTB),
          dsDK_DVGT: JSON.stringify(dsDK_DVGT),
          dsDK_DVK: JSON.stringify(dsDK_DVK),
          dsCTTHD: JSON.stringify(dsCTTHD),
          dsKM_HDTB: JSON.stringify(dsKM_HDTB),
          dsHDTB_KV: JSON.stringify(dsHDTB_KV),
          dsHDTT_NV: JSON.stringify(dsHDTT_NV),
          dsHDKH_SUB: JSON.stringify(dsHDKH_Sub),
          dsHDTB_SUB: JSON.stringify(dsHDTB_Sub),
          dsHDTB_CNTT: JSON.stringify(dsCNTT)
        }
        console.log('🚀 ~ file: index.vue ~ line 1855 ~ ThemMoi_HopDong_Sub_V3 ~ data', data)
        // return false
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/themmoi_hopdong_sub_v3', data)
        if (rs.error_code === 'BSS-00000500') {
          this.$root.$toast.error(rs.message)
          return false
        }
        if (rs.data !== null) {
          return true
        } else {
          return false
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1747 ~ ThemMoi_HopDong_Sub_TichHop ~ error', error)
        return false
      }
    },
    async logAction (action_name, key_parameter, note) {
      let ip = 'oneBSS'
      let data = {
        user_name: this.$root.token.getUserName(),
        computer_name: ip,
        action_name: action_name,
        key_parameter: key_parameter,
        server_name: ip,
        note: note
      }
      let response = await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/log_action`, data)
      return response.data.KQ
    },
    async generateInsert (hdtb_id, dsHDTB_CNTT) {
      try {
        if (dsHDTB_CNTT.length === 0) return []
        let ds = []
        let row = {}
        row.HDTB_ID = hdtb_id
        for (let index = 0; index < dsHDTB_CNTT.length; index++) {
          const e = dsHDTB_CNTT[index]
          if (e.field_name !== 'MA_TB') {
            if (e.data_type === 2) {
              if (e.att === 9) {
                row[e.field_name] = e.sValue !== undefined ? e.sValue : null
              } else {
                row[e.field_name] = e.sValue !== undefined && e.sValue !== null ? e.sValue.substring(0, 10) : null
              }
            } else { row[e.field_name] = e.sValue !== undefined ? e.sValue : null }
          }
        }
        ds.push(row)
        return ds
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1910 ~ generateInsert ~ error', error)
        return []
      }
    },
    async Kiemtra_khuvuc_NLML (quan_id, phuong_id, pho_id, ap_id, khu_id, dacdiem_id, dichvuvt_id) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_khuvuc_nlml', {quan_id, phuong_id, pho_id, ap_id, khu_id, dacdiem_id, dichvuvt_id})
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1881 ~ Kiemtra_khuvuc_NLML ~ error', error)
        return null
      }
    },
    async ThemMoi_HopDong_Sub_TichHop (hdtb_id, huonggiao_tn, dsHDKH, dsHDTT, dsHDTB, dsHDTBDV, dsHDTB_CD, dsHDTB_ADSL, dsHDTB_TSL, dsDiaChi, dsDiaChiHDKH, dsDiaChiHDTT, dsDiaChiHDTB, dsDK_DVGT, dsDK_DVK, dsHDTB_MEWAN, dsHDTB_GP, dsCTTHD, dsHDTB_IMS, dsKM_HDTB, dsHDTB_KV, dsHDTT_NV, dsHDKH_Sub, dsHDTB_Sub) {
      try {
        const newArray = [...dsDiaChi].map(item => {
          item.BL_OCK = item.BLOCK
          delete item.BLOCK
          return item
        })
        let data = {
          HDTB_ID: hdtb_id,
          HUONGGIAO_TN: huonggiao_tn,
          dsHDKH: (dsHDKH),
          dsHDTT: (dsHDTT),
          dsHDTB: (dsHDTB),
          dsHDTBDV: (dsHDTBDV),
          dsHDTB_CD: (dsHDTB_CD),
          dsHDTB_ADSL: (dsHDTB_ADSL),
          dsHDTB_TSL: (dsHDTB_TSL),
          dsHDTB_MEWAN: (dsHDTB_MEWAN),
          dsHDTB_GP: (dsHDTB_GP),
          dsHDTB_IMS: (dsHDTB_IMS),
          dsDIACHI: (newArray),
          dsDIACHIHDKH: (dsDiaChiHDKH),
          dsDIACHIHDTT: (dsDiaChiHDTT),
          dsDIACHIHDTB: (dsDiaChiHDTB),
          dsDK_DVGT: (dsDK_DVGT),
          dsDK_DVK: (dsDK_DVK),
          dsCTTHD: (dsCTTHD),
          dsKM_HDTB: (dsKM_HDTB),
          dsHDTB_KV: (dsHDTB_KV),
          dsHDTT_NV: (dsHDTT_NV),
          dsHDKH_SUB: (dsHDKH_Sub),
          dsHDTB_SUB: (dsHDTB_Sub)
        }
        console.log('🚀 ~ file: index.vue ~ line 1421 ~ ThemMoi_HopDong_Sub_TichHop ~ data', data)
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/themmoi_hopdong_sub_tichhop', data)
        if (rs.data !== null) {
          return true
        } else {
          return false
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1747 ~ ThemMoi_HopDong_Sub_TichHop ~ error', error)
        return false
      }
    },
    KiemTraTBTrongHD (txtMaTB, dtList, tt) {
      let result = false
      for (let index = 0; index < dtList.length; index++) {
        const element = dtList[index]
        if (txtMaTB.toUpperCase() === element.ma_tb.toUpperCase()) {
          result = true
          break
        } else {
          result = false
        }
      }
      return result
    },
    async CAPNHAT_KHOSO (ma_tb_moi, ma_tb_cu, dichvuvt_id) {
      try {
        let data = {
          ma_tb_moi,
          ma_tb_cu,
          dichvuvt_id,
          donvi_id: this.$root.token.getDonViID()
        }
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/capnhat_khoso_v2', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async HienThiDanhSacHDTB (ds) {
      var tongtienhd = 0
      var tongtientb = 0
      this.modelInput.lvwThueBao = []
      if (ds !== null && ds.length > 0) {
        var ds_khcu = await this.LAY_BIENDONG_THEO_HDTB_ID(ds[0].HDTB_ID.toString())
        if (ds_khcu.length > 0) {
          this.inputValue.khach_hang_id = parseInt(ds_khcu[0].KHACHHANG_ID)
        }
        for (let index = 0; index < ds.length; index++) {
          const myRow = ds[index]
          tongtientb = parseFloat(myRow.TONGTIEN)
          let lvItem = {}
          lvItem.stt = index + 1
          lvItem.ma_tt = myRow.MA_TT
          lvItem.hdtb_id = myRow.HDTB_ID
          lvItem.ma_tb = myRow.MA_TB
          lvItem.ten_tb = this.ChuanHoaTen(myRow.TEN_TB)
          lvItem.diachi_ld = this.ChuanHoaTen(myRow.DIACHI_LD)
          lvItem.tongtientb = tongtientb
          lvItem.ten_dvvt = myRow.TEN_DVVT
          lvItem.manv_tc = myRow.MANV_TC
          tongtienhd = tongtienhd + tongtientb
          this.modelInput.lvwThueBao.push(lvItem)
        }
        if (ds.length <= 1) {
          this.$parent.tsbtnXoaTB = false
        } else {
          this.$parent.tsbtnXoaTB = true
        }
      }
      this.$parent.modelInput.txtTongTienHD = currency(tongtienhd.toString())
    },
    async LAY_TIENHD_KM_THEO_HDTB (HDTB_ID) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_tienhd_km_theo_hdtb', {HDTB_ID})
        return rs.data
      } catch (error) {
        return []
      }
    },
    async LAY_BIENDONG_THEO_HDTB_ID (HDTB_ID) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_biendong_theo_hdtb_id', {HDTB_ID})
        return this.UpperCasePropertyList(rs.data)
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2024 ~ LAY_BIENDONG_THEO_HDTB_ID ~ error', error)
        return []
      }
    },
    async LAY_DS_DIACHI_THEO_HDTB_ID (hdtb_id) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_diachi_theo_hdtb_id', {hdtb_id})
        return this.UpperCasePropertyList(rs.data)
      } catch (error) {
        return []
      }
    },
    async HienThiTTHopDongTB (ds) {
      if (ds.length > 0) {
        let row = { ...ds[0] }
        this.inputValue.ikieuld_id = 0
        this.inputValue.hdtb_id = parseInt(row.HDTB_ID)
        if (row.THUEBAO_ID !== null) {
          this.inputValue.thuebao_id = parseInt(row.THUEBAO_ID)
        } else {
          this.inputValue.thuebao_id = 0
        }
        var dsNV = []
        try {
          dsNV = await this.LAY_DS_KHUVUC_THEO_HD_DB(this.inputValue.hdtb_id, 1)
          var ktra = dsNV.length
          if (ktra < 1) {
            dsNV = await this.LAY_DS_KHUVUC_THEO_HD_DB(this.inputValue.thuebao_id, 2)
          }
        } catch (error) {
          dsNV = await this.LAY_DS_KHUVUC_THEO_HD_DB(this.inputValue.thuebao_id, 2)
        }
        this.modelInput.txtKhuVuc = ''
        if (dsNV.length > 0) {
          this.modelInput.txtKhuVuc = dsNV[0].TEN_KV
          this.inputValue.khuvuc_id = dsNV[0].KHUVUC_ID
          this.dsHDTB_KV = []
          let dr = {}
          dr.HDTB_ID = this.inputValue.hdtb_id
          dr.KHUVUC_ID = this.inputValue.khuvuc_id
          dr.LOAIKV_ID = Enum.LOAI_KV.KHUVUC_DIABAN
          dr.KIEUKV_ID = 2
          this.dsHDTB_KV.push(dr)
        }
        this.modelInput.txtMaTB = row.MA_TB
        this.inputValue.ma_tb = this.modelInput.txtMaTB
        this.modelInput.txtTenTB = this.ChuanHoaTen(row.TEN_TB)
        this.inputValue.ma_tbmoi = row.MA_TB
        this.modelInput.txtDiaChiTB = this.ChuanHoaTen(row.DIACHI_TB)
        this.modelInput.txtDiaChiLD = this.ChuanHoaTen(row.DIACHI_LD)
        this.modelInput.txtNgaySinhTB = row.NGAY_SN
        if (row.HDTB_CHA_ID !== null) {
          this.inputValue.hdtb_id_cha = parseInt(row.HDTB_CHA_ID)
          this.inputValue.loaisochinh = 0
        }
        var LAY_DS_DIACHI_THEO_HDTB_ID = await this.LAY_DS_DIACHI_THEO_HDTB_ID(this.inputValue.hdtb_id)
        await this.HienThiTTDiaChi(LAY_DS_DIACHI_THEO_HDTB_ID, 3)
        this.inputValue.dichvuvt_id = await this.MAP_ID('DICHVUVT_ID', 'CSS', 'LOAIHINH_TB', 'LOAITB_ID', row.LOAITB_ID)
        this.modelCbo.cboDichVuVT = this.inputValue.dichvuvt_id

        await this.cboDichVuVT_SelectedValueChanged()

        if (row.GOIKT_ID !== null) {
          this.modelCbo.cboGoiKT = parseInt(row.GOIKT_ID)
        } else {
          this.modelCbo.cboGoiKT = this.modelCbo.vcboGoiKT[0].GOIKT_ID
        }
        this.modelCbo.cboLoaihinhTB = parseInt(row.LOAITB_ID)
        this.inputValue._loaitb_id = this.modelCbo.cboLoaihinhTB
        await this.cboLoaihinhTB_SelectedValueChanged()
        if (parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.ANTOAN_BAOMAT_TT || parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.DICHVU_CNTT ||
              parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.TRUNGTAM_DULIEU || parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.HOINGHI_TRUYENHINH) {
          await this.$refs.refCNTT.LoadThongTin_DV_CNTT(this.modelCbo.cboLoaihinhTB)
        }
        this.modelCbo.cboKieuLD = parseInt(row.KIEULD_ID)
        this.modelCbo.cboDoituongTB = parseInt(row.DOITUONG_ID)
        this.modelCbo.cboTT = parseInt(row.HDTT_ID)
        this.modelCbo.cboDaiVT = parseInt(row.DONVI_ID)
        if (row.DONVI_DAU_ID !== null) {
          this.inputValue.donvi_dau_id = parseInt(row.DONVI_DAU_ID)
        }
        if (row.DONVI_CUOI_ID !== null) {
          this.inputValue.donvi_cuoi_id = parseInt(row.DONVI_CUOI_ID)
        }
        if (row.MUCUOCTB_ID !== null) {
          this.modelCbo.vcboMucCuocTB = await this.HT_MucCuocTB_Combobox(row.MUCUOCTB_ID)
          if (this.modelCbo.vcboMucCuocTB.length > 0) {
            this.modelCbo.cboMucCuocTB = row.MUCUOCTB_ID // this.modelCbo.vcboMucCuocTB[0].MUCUOCTB_ID
          }
        } else {
          this.modelCbo.vcboMucCuocTB = []
        }
        this.inputValue.m_dsThueBao_DichVu = await this.LAY_DS_THUEBAO_DICHVU(this.inputValue.hdtb_id, this.inputValue.thuebao_id, this.inputValue.loaitb_id, 1)
        await this.HT_DichVuGT()
        await this.LAY_DS_SUDUNG_DVK(this.inputValue.thuebao_id, 1)
        var LAY_BIENDONG_THEO_HDTB_ID = await this.LAY_BIENDONG_THEO_HDTB_ID(this.inputValue.hdtb_id)
        await this.HienThiThongTinCu(LAY_BIENDONG_THEO_HDTB_ID)
        await this.HienThiTTMoRongHD(this.inputValue.hdtb_id, this.inputValue.dichvuvt_id)
        var dtKC = await this.LayThongTinKetCuoi(this.inputValue.hdtb_id)
        if (dtKC.length > 0) {
          this.inputValue.KetCuoi_ID = parseInt(dtKC[0].KETCUOI_ID)
        }
        await this.HT_DS_KhuyenMai_Combobox()
        var dsTemp = await this.LAY_CHITIET_KM_THEO_HDTB_ID(this.inputValue.hdtb_id, 0)
        if (dsTemp.length > 0) {
          var r = dsTemp[0]
          if (r.khuyenmai_id !== null) {
            this.modelCbo.cboKhuyenMai = parseInt(r.khuyenmai_id)
            this.modelCkeckBox.chkKhuyenMai = true
          } else {
            this.modelCkeckBox.chkKhuyenMai = false
          }
          var ithang_huongdc = 0, ithang_huongkm = 0, ithang_tg = 0
          var dtiendatcoc_csd = 0, dtien_td = 0
          this.dsKM_HDTB = []
          for (let index = 0; index < dsTemp.length; index++) {
            const element = dsTemp[index]
            ithang_huongdc = 0
            ithang_huongkm = 0
            ithang_tg = 0
            dtiendatcoc_csd = 0
            dtien_td = 0
            let dr = {}
            ithang_huongdc = parseInt(element.thang_huongdc)
            ithang_huongkm = parseInt(element.thang_huongkm)
            ithang_tg = parseInt(element.sothang_tg)
            dtiendatcoc_csd = parseInt(element.datcoc_csd)
            dtien_td = parseInt(element.tien_td)
            dr.THANG_HUONGDC = ithang_huongdc
            dr.THANG_HUONGKM = ithang_huongkm
            dr.SOTHANG_TG = ithang_tg
            dr.DATCOC_CSD = dtiendatcoc_csd
            dr.TIEN_TD = dtien_td
            if (element.kieu_bdkm.toString() === '3') {
              dr.THANG_BD = parseInt(element.thang_bdkm)
            }
            if (element.kieu_bdtg.toString() === '3') {
              dr.THANG_BDTG = parseInt(element.thang_bdtg)
            }
            if (element.kieu_bddc.toString() === '3') {
              dr.THANG_BDDC = parseInt(element.thang_bddc)
            }
            dr.TYLE_KMLD = parseInt(element.tyle_kmld)
            dr.TYLE_SD = parseInt(element.tyle_sd)
            dr.TYLE_TB = parseInt(element.tyle_tb)
            dr.HDTB_ID = this.inputValue.hdtb_id
            dr.CHITIETKM_ID = parseInt(element.chitietkm_id)
            dr.KHUYENMAI_ID = parseInt(element.khuyenmai_id)
            dr.KIEU_YC = parseInt(element.kieu_yc)
            this.dsKM_HDTB.push(dr)
          }
        } else {
          this.modelCkeckBox.chkKhuyenMai = false
          this.dsKM_HDTB = []
        }
        this.inputValue.ds_tien_khoanmuc = await this.LAY_TIENHD_KM_THEO_HDTB(this.inputValue.hdtb_id)
        if (this.inputValue.ds_tien_khoanmuc !== null) {
          for (let index = 0; index < this.inputValue.ds_tien_khoanmuc.length; index++) {
            const e = this.inputValue.ds_tien_khoanmuc[index]

            if (e.khoanmuctt_id.toString() == Enum.KHOANMUC_TT.KMTT_CHUYENDICH.toString()) {
              this.inputValue.dtienld = parseFloat(e.tien)
              this.inputValue.dtienlddvatld = parseFloat(e.vat)
            }
            if (e.khoanmuctt_id.toString() == Enum.KHOANMUC_TT.KMTT_KM_LAPDAT.toString()) {
              this.inputValue.dtienkmld = parseFloat(e.tien)
              this.inputValue.dvatkmld = parseFloat(e.vat)
            }
            if (e.khoanmuctt_id.toString() == Enum.KHOANMUC_TT.KMTT_DICHVU.toString()) {
              this.inputValue.dtien_dv = parseFloat(e.tien)
              this.inputValue.dvat_dv = parseFloat(e.vat)
            }
          }
        }
        await this.LayTongTien_HDTB()
      }
    },
    async LAY_CHITIET_KM_THEO_HDTB_ID (vhdtb_id, vloai_km) {
      try {
        let rs = await this.$root.context.get('/web-hopdong/HoanCongThuTienPhatSinh/lay_chitiet_km_theo_hdtb_id?vhdtb_id=' + vhdtb_id + '&vloai_km=' + vloai_km)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue:2333 ~ LAY_CHITIET_KM_THEO_HDTB_ID ~ error', error)
        return []
      }
    },
    async LayThongTinKetCuoi (hdtb_id) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_thong_tin_ketcuoi_hdtb_id', {hdtb_id})
        return this.UpperCasePropertyList(rs.data)
      } catch (error) {
        return []
      }
    },
    async HienThiTTMoRongHD (hdtb_id, dichvuvt_id) {
      try {
        this.modelCkeckBox.chkCongVan = false
        this.modelCkeckBox.chkdisableCongVan = true
        this.modelInput.txtCongVan = ''
        this.modelDateTime.dtpNgayCV = await this.getNgay_Cn()
        this.inputValue.kt_kenhphu_ao = false
        switch (parseInt(dichvuvt_id)) {
          case Enum.DichVuVienThong.CO_DINH:
            await this.$refs.refCD.HienThiTTMoRongHD(hdtb_id)
            break
          case Enum.DichVuVienThong.IMS:
            await this.$refs.refIMS.HienThiTTMoRongHD(hdtb_id)
            break
          case Enum.DichVuVienThong.IMS_DOITAC:
            await this.$refs.refIMS.HienThiTTMoRongHD(hdtb_id)
            break
          case Enum.DichVuVienThong.ADSL:
            await this.$refs.refAdsl.HienThiTTMoRongHD(hdtb_id)
            break
          case Enum.DichVuVienThong.BRCD_DOITAC:
            await this.$refs.refAdsl.HienThiTTMoRongHD(hdtb_id)
            break
          case Enum.DichVuVienThong.MEGA_EYES:
            await this.$refs.refAdsl.HienThiTTMoRongHD(hdtb_id)
            break
          case Enum.DichVuVienThong.MEGAWAN:
          case Enum.DichVuVienThong.NOIBO_DADIEM:
            await this.$refs.refWan.HienThiTTMoRongHD(hdtb_id)
            break
          case Enum.DichVuVienThong.METRONET:
            await this.$refs.refWan.HienThiTTMoRongHD(hdtb_id)
            break
          case Enum.DichVuVienThong.MGW_DOITAC:
            await this.$refs.refWan.HienThiTTMoRongHD(hdtb_id)
            break
          case Enum.DichVuVienThong.TSL:
          case Enum.DichVuVienThong.NOIBO_2DIEM:
            await this.$refs.refTSL.HienThiTTMoRongHD(hdtb_id)
            break
          case Enum.DichVuVienThong.TSL_DOITAC:
            this.$refs.refTSL.HienThiTTMoRongHD(hdtb_id)
            break
          case Enum.DichVuVienThong.GPHONE:
            await this.$refs.refGP.HienThiTTMoRongHD(hdtb_id)
            break
          case Enum.DichVuVienThong.DICHVU_CNTT:
          case Enum.DichVuVienThong.ANTOAN_BAOMAT_TT:
          case Enum.DichVuVienThong.TRUNGTAM_DULIEU:
          case Enum.DichVuVienThong.HOINGHI_TRUYENHINH:
            await this.$refs.refCNTT.HienThiTTMoRongHD(hdtb_id)
            break
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2037 ~ HienThiTTMoRongHD ~ error', error)
      }
    },
    async HienThiThongTinCu (ds) {
      if (ds.length > 0) {
        this.modelInput.txtMaHDcu = ds[0].MA_HD
        this.modelDateTime.dtpNgayLapHD = new Date(ds[0].NGAYLAP_HD)
        this.modelInput.txtMaTB = ds[0].MA_TB
        if (this.modelInput.txtMaTB === this.modelInput.txtMaTBMoi) { this.modelInput.txtMaTBMoi = '' }
      }
    },
    async LAY_DS_HOPDONG_TB_THEO_HDKH_ID (hdkh_id) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_hopdong_tb_theo_hdkh_id', {hdkh_id})
        return this.UpperCasePropertyList(rs.data)
      } catch (error) {
        return []
      }
    },
    async lay_goivasc_goi_dadv (goi_id, tocdo_id, muccuoc_id) {
      try {
        let data = {
          goi_id, tocdo_id, muccuoc_id
        }
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_goivasc_goi_dadv', data)
        return rs.data
      } catch (error) {

      }
    },
    async TaoDuLieu_HDTB_GOI_DADV (goi_id, nhomtb_id) {
      this.dsHDTBGOI = []
      var rowGOI = {}
      rowGOI.GOI_ID = goi_id
      rowGOI.NHOMTB_ID = nhomtb_id
      rowGOI.CHUNHOM = 0
      rowGOI.HDTB_ID = this.inputValue.hdtb_id
      rowGOI.THANG_HUONG = 0
      this.dsHDTBGOI.push(rowGOI)
    },
    async ThemMoi_HopDongTB (dsGOI, huonggiao_id) {
      try {
        let data = {
          GOI_ID: dsGOI[0].GOI_ID,
          NHOMTB_ID: dsGOI[0].NHOMTB_ID,
          CHUNHOM: dsGOI[0].CHUNHOM,
          HDTB_ID: dsGOI[0].HDTB_ID,
          THANG_HUONG: dsGOI[0].THANG_HUONG
        }
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/themmoi_hopdongtb_v2', data)
      } catch (error) {
        console.log('🚀 ~ file: index.vue:2448 ~ ThemMoi_HopDongTB ~ error', error)
      }
    },
    async ThemMoi_HopDongTB2 (dsHDTB, dsGOI, huonggiao_id) {
      try {
        let data = {
          dsHDTB: JSON.stringify(dsHDTB),
          dsGOI: JSON.stringify(dsGOI),
          huonggiao_id: huonggiao_id
        }
        console.log('🚀 ~ file: index.vue ~ line 2479 ~ ThemMoi_HopDongTB2 ~ data', data)
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/themmoi_hopdongtb_goidadv', data)
        return true
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2483 ~ ThemMoi_HopDongTB2 ~ error', error)
        return false
      }
    },
    async TaoDuLieu_HDTB_dkygoi_mytv (dsMyTV) {
      this.dsHDTB = []
      this.dsHDTBGOI = []
      for (let index = 0; index < dsMyTV.length; index++) {
        const element = dsMyTV[index]
        var hdtb_id_huygoi_mytv = this.GetKey('HD_THUEBAO', 'CSS')
        var rowHDTB = {}
        var LAY_DS_DBTB_THEO_TBID = this.LAY_DS_DBTB_THEO_TBID(element.thuebao_id)
        if (LAY_DS_DBTB_THEO_TBID.length > 0) {
          rowHDTB = LAY_DS_DBTB_THEO_TBID[0]
        }
        rowHDTB.HDTB_ID = hdtb_id_huygoi_mytv
        rowHDTB.HDKH_ID = this.inputValue.hdkh_id
        rowHDTB.THUEBAO_ID = element.thuebao_id
        rowHDTB.MA_TB = element.ma_tb
        rowHDTB.TEN_TB = element.ten_tb
        rowHDTB.DIACHI_TB = element.diachi_tb
        rowHDTB.KIEULD_ID = Enum.KieuLapDat.THEM_TB_GOI_DADV
        rowHDTB.DOITUONG_ID = element.doituong_id
        rowHDTB.TTHD_ID = Enum.TrangThaiHD.MOI
        rowHDTB.LOAITB_ID = Enum.LoaiHinhTB.INTERNET_MYTV
        rowHDTB.DICHVUVT_ID = Enum.DichVuVienThong.ADSL
        rowHDTB.DONVI_ID = this.$auth.getDonViID()
        var dset = await this.LayHuongGiao_TiepNhan_2(Enum.LoaiHopDong.THAYDOI_GOI_DADV, Enum.KieuLapDat.XOA_TB_GOI_DADV)
        if (dset && dset.length > 0) {
          this.inputValue.huonggiaotn_id = dset[0].huonggiao_id
          this.inputValue.quytrinh_id_2 = dset[0].quytrinh_id
        }
        rowHDTB.QUYTRINH_ID = this.inputValue.quytrinh_id_2
        var mucuoctb_id = await this.LayMucCuocTB(element.thuebao_id, 1)
        var mucuoctb = await this.LayMucCuocTB(element.thuebao_id, 2)
        if (mucuoctb_id !== -1) {
          rowHDTB.MUCUOCTB_ID = mucuoctb_id
        }
        if (mucuoctb !== -1) {
          rowHDTB.MUCUOC_TB = mucuoctb
        }
        this.dsHDTB.push(rowHDTB)
        var rowGOI = {}
        rowGOI.GOI_ID = element.goi_id
        rowGOI.NHOMTB_ID = element.nhomtb_id
        rowGOI.CHUNHOM = 0
        rowGOI.HDTB_ID = hdtb_id_huygoi_mytv
        rowGOI.THANG_HUONG = 0
        this.dsHDTBGOI.push(rowGOI)
      }
    },
    async LayMucCuocTB (thuebao_id, kieu) {
      if (kieu === 1) {
        return this.MAP_ID('MUCCUOC_ID', 'CSS', 'DB_THUEBAO', 'THUEBAO_ID', thuebao_id)
      } else {
        return this.MAP_ID('MUCCUOC_TB', 'CSS', 'DB_THUEBAO', 'THUEBAO_ID', thuebao_id)
      }
    },
    getDiaChiTB: async function (data) {
      // this.getDiaChi(data,'diachiTB');
      // if(!this.diachiLD.DIACHI || (this.diachiLD.DIACHI==this.diachiKH.DIACHI && this.diachiLD.DIACHI!=this.diachiTB.DIACHI))
      //   this.diachiLD = Object.assign({}, this.diachiTB);
      // if([DichVuVienThong.MEGAWAN,DichVuVienThong.TSL,DichVuVienThong.ADSL,LoaiHinhTB.INTERNET_MYTV,LoaiHinhTB.MAIL_SMD,LoaiHinhTB.Web_Hosting,].indexOf(this.listbox.dichvu_vt.value*1)>=0)
      //   await this.SinhMaDVVT();

      this.diachiTB.DIACHI = data.diachimoi
      this.diachiTB.TINH_ID = data.tinh_id
      this.diachiTB.QUAN_ID = data.quan_id
      this.diachiTB.PHUONG_ID = data.phuong_id
      this.diachiTB.PHO_ID = data.pho_id
      this.diachiTB.AP_ID = data.ap_id
      this.diachiTB.KHU_ID = data.khu_id
      this.diachiTB.DACDIEM_ID = data.dacdiem_id
      this.diachiTB.SO_NHA = data.so_nha
      this.diachiTB.Lat = data.lat
      this.diachiTB.Lng = data.lng

      this.inputValue.tinhtb_id = data.tinh_id
      this.inputValue.quantb_id = data.quan_id
      this.inputValue.phuongtb_id = data.phuong_id
      this.inputValue.photb_id = data.pho_id
      this.inputValue.aptb_id = data.ap_id
      this.inputValue.khutb_id = data.khu_id
      this.inputValue.dacdiemtb_id = data.dacdiem_id
      this.inputValue.Lng_TB = data.lng
      this.inputValue.Lat_TB = data.lat
      this.inputValue.sonhatb = data.so_nha
      this.modelInput.txtDiaChiTB = data.diachimoi

      // this.inputValue.blocktb = data.block
      // this.inputValue.tangtb = data.tang
      // this.inputValue.sophongtb = data.sophong
      // this.inputValue.motadctb = data.motathem

      this.$refs.txtDiaChiTB.focus()
    },
    async LAY_DS_DBTB_THEO_TBID (thuebao_id) {
      try {
        let rs = await this.$root.context.get('/web-hopdong/chuyendich/lay_ds_dbtb_theo_tbid?thuebao_id=' + thuebao_id)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async Them_MyTV_vao_goi_giadinh () {
      try {
        if ((this.inputValue.loaitb_id === Enum.LoaiHinhTB.INTERNET_FTTH || this.inputValue.loaitb_id === Enum.LoaiHinhTB.INTERNET_MYTV) &&
                  this.$refs.refAdsl && this.$refs.refAdsl.txtMaTN_ADSL.Text !== '') {
          let data = {
            vhdtb_id: this.inputValue.hdtb_id,
            vmatb_tn: this.$refs.refAdsl.txtMaTN_ADSL.Text
          }
          let rs = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_dky_family_dichuyen', data)
          if (rs.data !== null && rs.data.length > 0) {
            if (rs.data[0].vkq.toString() === '1') {
              var goi_id = parseInt(rs.data[0].vgoi_id)
              var nhomtb_id = parseInt(rs.data[0].vnhom_id)
              var cboGoiCuocAdsl = this.$refs.refAdsl.cboGoiCuocAdsl.Value
              var cboMucCuoc = this.$refs.refAdsl.cboMucCuoc.Value
              var magoi_vasc = await this.lay_goivasc_goi_dadv(goi_id, cboGoiCuocAdsl, cboMucCuoc)
              if ((magoi_vasc !== -1 && this.inputValue.loaitb_id === Enum.LoaiHinhTB.INTERNET_MYTV) || this.inputValue.loaitb_id === Enum.LoaiHinhTB.INTERNET_FTTH) {
                try {
                  var confirm = await this.$confirm('Thuê bao đang tham gia gói Gia đình (hoặc được dịch chuyển tới cùng đường với thuê bao đang tham gia gói Gia đình). Bạn có đồng ý thêm thuê bao này vào cùng gói không?',
                    'Thông báo',
                    {
                      confirmButtonText: 'Có',
                      cancelButtonText: 'Không',
                      type: 'warning'
                    })
                } catch (error) {
                  return false
                }
                if (this.inputValue.loaitb_id === Enum.LoaiHinhTB.INTERNET_MYTV) {
                  await this.TaoDuLieu_HDTB_GOI_DADV(goi_id, nhomtb_id)
                  await this.ThemMoi_HopDongTB(this.dsHDTBGOI, 0)
                  this.themMyTVvaogoi = true
                }
                if (this.inputValue.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH) {
                  await this.TaoDuLieu_HDTB_dkygoi_mytv(rs.data)
                  await this.ThemMoi_HopDongTB2(this.dsHDTB, this.dsHDTBGOI, this.inputValue.huonggiaotn_id)
                  this.themMyTVvaogoi = true
                }
              }
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2429 ~ Them_MyTV_vao_goi_giadinh ~ error', error)
        throw error
      }
    },
    async CAPNHAT_TRANGTHAI_HD (vhdtb_id, vtthd_id) {
      try {
        var data = {vhdtb_id, vtthd_id}
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/capnhat_trangthai_hd', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async tudong_ganthem_hdtb_dv (hdtb_id) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/tudong_ganthem_hdtb_dv?hdtb_id=' + hdtb_id)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async HienThiDS_HDTT_CBO (hdkh_id) {
      var DataSource = await this.LAY_DS_HDTT_THEO_HDKH_ID_1(hdkh_id)
      if (DataSource.length > 0) {
        this.modelCbo.vcboTT = DataSource
        this.modelCbo.cboTT = DataSource[0].hdtt_id
      }
    },
    async LAY_DS_HDTT_THEO_HDKH_ID_1 (hdkh_id) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_hdtt_theo_hdkh_id_1', {hdkh_id})
        return rs.data
      } catch (error) {
        return []
      }
    },
    async TaoDuLieu (themmoi) {
      try {
        await this.ThongTinKhachHang.TaoDuLieu(themmoi)

        this.dsHDTT = this.ThongTinKhachHang.getDsHDTT()
        this.inputValue.hdtt_id = this.dsHDTT[0].HDTT_ID

        this.dsHDKH = this.ThongTinKhachHang.getDsHDKH()
        this.inputValue.hdkh_id = this.dsHDKH[0].HDKH_ID

        this.dsHDKH_SUB = this.ThongTinKhachHang.getDsHDKH_SUB()
        this.dsHDTT_NV = this.ThongTinKhachHang.getDsHDTT_NV()
        this.dsDiaChiHDKH = this.ThongTinKhachHang.getDsDIACHI_HDKH()
        this.dsDiaChiHDTT = this.ThongTinKhachHang.getDsDIACHI_HDTT()
        this.dsDiaChi = this.ThongTinKhachHang.getDsDIACHI()

        await this.TaoDuLieu_HDTB(themmoi)
        await this.TaoDuLieu_DiaChi(themmoi, 4)
        await this.TaoDuLieu_CT_TIENHD(true)
        await this.TaoDuLieu_DKDV()
        await this.CapNhat_HDTB_ID_HDTB_KV()
        this.dsHDTB_CD = []
        this.dsHDTB_ADSL = []
        this.dsHDTB_TSL = []
        this.dsHDTB_MEWAN = []
        this.dsHDTB_GP = []
        this.dsHDTBDV = []
        this.dsHDTB_IMS = []
        this.dsHDTB_CNTT = []
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.CO_DINH) { await this.TaoDuLieu_HDTB_CD(themmoi) }
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.IMS || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.IMS_DOITAC) { await this.TaoDuLieu_HDTB_IMS(themmoi) }
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.ADSL || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.BRCD_DOITAC) { await this.TaoDuLieu_HDTB_ADSL(themmoi) }
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MEGA_EYES) { await this.TaoDuLieu_HDTB_ADSL(themmoi) }
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || 
            this.inputValue.dichvuvt_id === Enum.DichVuVienThong.METRONET || 
            this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_DADIEM ||
            this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MGW_DOITAC) { await this.TaoDuLieu_HDTB_MGWAN(themmoi) }
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TSL || 
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TSL_DOITAC ||
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM
          ) { 
            await this.TaoDuLieu_HDTB_TSL(themmoi) 
        }
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.GPHONE) { await this.TaoDuLieu_HDTB_GP(themmoi) }
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.ANTOAN_BAOMAT_TT || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.DICHVU_CNTT ||
              this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TRUNGTAM_DULIEU || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.HOINGHI_TRUYENHINH) {
          await this.TaoDuLieu_HDTB_CNTT(themmoi)
          this.dsHDTBDV = await this.$refs.refCNTT.TaoChoBang_HDTB_DV()
          for (const element of this.dsHDTBDV) {
            element.HDTB_ID = this.inputValue.hdtb_id
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1639 ~ TaoDuLieu ~ error', error)
        throw error
      }
    },
    async TaoDuLieu_HDTB_CD (themmoi) {
      try {
        this.dsHDTB_CD = []
        var rowHDTB_CD = {}

        var dt = await this.$refs.refCD.TaoDuLieu_HDTB_CD(themmoi)
        rowHDTB_CD = dt.rowHDTB_CD
        this.dsHDTBDV = dt.dsHDTBDV
        for (const element of this.dsHDTBDV) {
          element.HDTB_ID = this.inputValue.hdtb_id
        }
        rowHDTB_CD.HDTB_ID = this.inputValue.hdtb_id
        rowHDTB_CD.MATB_TN = this.$refs.refCD.txtMaTN_CD.Text
        rowHDTB_CD.MADOICAP = this.$refs.refCD.txtMaDoiCap_CD.Text
        if (this.inputValue.kieu_thueso == Enum.THUESO.TRUOTSO) {
          rowHDTB_CD.TG_THUESO = this.inputValue.sothang_thueso
          rowHDTB_CD.SOTRUOT = this.inputValue.noidung_thueso
          rowHDTB_CD.NOIDUNG_TB = ''
        }
        if (this.inputValue.kieu_thueso == Enum.THUESO.THONGBAO) {
          rowHDTB_CD.TG_THUESO = this.inputValue.sothang_thueso
          rowHDTB_CD.SOTRUOT = ''
          rowHDTB_CD.NOIDUNG_TB = this.inputValue.noidung_thueso
        }
        rowHDTB_CD.TRANGBI_ID = this.$refs.refCD.cboTrangBiCD.Value
        if (this.inputValue.KetCuoi_ID > 0) { rowHDTB_CD.KETCUOI_ID = this.inputValue.KetCuoi_ID }
        rowHDTB_CD.NE_ID = this.$refs.refCD.ne_id
        rowHDTB_CD.NE = this.$refs.refCD.sone
        this.dsHDTB_CD.push(rowHDTB_CD)
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1678 ~ TaoDuLieu_HDTB_CD ~ error', error)
      }
    },
    async TaoDuLieu_HDTB_IMS (themmoi) {
      try {
        this.dsHDTB_IMS = []
        var dt = await this.$refs.refIMS.TaoDuLieu_HDTB_IMS(themmoi)
        if (dt) {
          var rowHDTB_IMS = dt.rowHDTB_IMS
          this.dsHDTBDV = dt.dsHDTBDV
          for (const element of this.dsHDTBDV) {
            element.HDTB_ID = this.inputValue.hdtb_id
          }
          this.dsHDTB_IMS.push(rowHDTB_IMS)
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2517 ~ TaoDuLieu_HDTB_IMS ~ error', error)
      }
    },
    async TaoDuLieu_HDTB_ADSL (themmoi) {
      this.dsHDTB_ADSL = []
      var dt = await this.$refs.refAdsl.TaoDuLieu_HDTB_ADSL(themmoi)
      this.inputValue.kt_adsl = this.$refs.refAdsl.kt_adsl
      var rowHDTB_ADSL = dt.rowHDTB_ADSL
      this.dsHDTBDV = dt.dsHDTBDV
      for (const element of this.dsHDTBDV) {
        element.HDTB_ID = this.inputValue.hdtb_id
      }
      rowHDTB_ADSL.HDTB_ID = this.inputValue.hdtb_id
      if (this.inputValue.KetCuoi_ID > 0) { rowHDTB_ADSL.KETCUOI_ID = this.inputValue.KetCuoi_ID } else {
        rowHDTB_ADSL.KETCUOI_ID = null
      }
      if (this.inputValue.thuebao_id > 0) {
        var malt = ''
        malt = await this.MAP_ID('MA_LT', 'CSS', 'DB_ADSL', 'THUEBAO_ID', this.inputValue.thuebao_id)
        if (malt && malt.toString() !== '-1') { rowHDTB_ADSL.MA_LT = malt } else {
          rowHDTB_ADSL.MA_LT = null
        }
        var password = ''
        password = await this.MAP_ID('PASSWORD', 'CSS', 'DB_ADSL', 'THUEBAO_ID', this.inputValue.thuebao_id)
        if (password && password.toString() !== '-1') { rowHDTB_ADSL.PASSWORD = password } else {
          rowHDTB_ADSL.PASSWORD = null
        }
      }
      this.dsHDTB_ADSL.push(rowHDTB_ADSL)
    },
    async TaoDuLieu_HDTB_MGWAN (themmoi) {
      try {
        this.dsHDTB_MEWAN = []
        var dt = await this.$refs.refWan.TaoDuLieu_HDTB_MGWAN(themmoi)
        this.dsHDTB_MEWAN = dt.dsHDTB_MEWAN
        
        this.dsHDTBDV = dt.dsHDTBDV
        for (const element of this.dsHDTBDV) {
          element.HDTB_ID = this.inputValue.hdtb_id
        }
      } catch (error) {
        this.dsHDTB_MEWAN = []
        this.dsHDTBDV = []
        console.log('🚀 ~ file: index.vue ~ line 2623 ~ TaoDuLieu_HDTB_MGWAN ~ error', error)
      }
    },
    async LAY_DS_DB_TSL_THEO_TBID (thuebao_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-tsl/lay_ds_db_tsl_theo_tbid?thuebao_id=${thuebao_id}`)
        return response.data
      } catch (err) {
        return []
      }
    },
    async TaoDuLieu_HDTB_TSL (themmoi) {
      try {
        this.dsHDTB_TSL = []
        var dt = await this.$refs.refTSL.TaoDuLieu_HDTB_TSL(themmoi)
        this.dsHDTB_TSL = dt.dsHDTB_TSL
        this.dsHDTBDV = dt.dsHDTBDV
        for (const element of this.dsHDTBDV) {
          element.HDTB_ID = this.inputValue.hdtb_id
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2589 ~ TaoDuLieu_HDTB_TSL ~ error', error)
      }
    },
    async TaoDuLieu_HDTB_GP (themmoi) {
      try {
        this.dsHDTB_GP = []
        var dt = await this.$refs.refGP.TaoDuLieu_HDTB_GP(themmoi)
        this.dsHDTB_GP = dt.dsHDTB_GP
        this.dsHDTBDV = dt.dsHDTBDV
      } catch (error) {
        this.dsHDTB_GP = []
        this.dsHDTBDV = []
        console.log('🚀 ~ file: index.vue ~ line 2627 ~ TaoDuLieu_HDTB_GP ~ error', error)
      }
    },
    async TaoDuLieu_HDTB_CNTT (themmoi) {
      try {
        this.dsHDTB_CNTT = []
        var dt = await this.$refs.refCNTT.TaoDuLieu_HDTB_CNTT(themmoi)
        this.dsHDTB_CNTT = dt
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2606 ~ TaoDuLieu_HDTB_CNTT ~ error', error)
      }
    },
    async LAY_DS_DB_CD_THEO_TBID (thuebao_id) {
      try {
        let rs = await this.$root.context.get('/web-tracuu/danhba/lay_ds_db_cd_by_tbid?thuebaoId=' + thuebao_id)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1643 ~ LAY_DS_DB_CD_THEO_TBID ~ error', error)
        return []
      }
    },
    async LAY_DS_DB_IMS_THEO_TBID (thuebao_id) {
      return []
    },
    async LAY_DS_DB_ADSL_THEO_TBID (thuebao_id) {
      try {
        let rs = await this.$root.context.get('/web-tracuu/danhba/lay_ds_db_adsl_by_tbid?thuebaoId=' + parseInt(thuebao_id))
        return rs.data
      } catch (error) {
        return []
      }
    },
    async LAY_DS_DB_MEGAWAN_THEO_TBID (thuebao_id) {
      try {
        let rs = await this.$root.context.get('/web-tracuu/danhba/lay_ds_db_megawan_by_tbid?thuebaoId=' + parseInt(thuebao_id))
        return rs.data
      } catch (error) {
        return []
      }
    },
    async LAY_DS_DB_TSL_THEO_TBID (thuebao_id) {
      try {
        let rs = await this.$root.context.get('/web-tracuu/danhba/lay_ds_db_tsl_by_tbid?thuebaoId=' + thuebao_id)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async LAY_DS_DB_GP_THEO_TBID (thuebao_id) {
      try {
        let rs = await this.$root.context.get('/web-tracuu/danhba/lay_ds_db_gp_by_tbid?thuebaoId=' + thuebao_id)
        return rs.data
      } catch (error) {
        return []
      }
    },
    chapnhan_gankhuvuc (obj) {
      if (obj) {
        if (this.inputValue.quanld_id !== 0 && this.inputValue.phuongld_id !== 0) {
          this.inputValue.khuvuc_id = Number(obj.khuvuc_id)
          this.modelInput.txtKhuVuc = obj.ma_kv
          this.dsHDTB_KV = obj.hdtb_kv_data
        } else {
          this.inputValue.khuvuc_id = 0
          this.modelInput.txtKhuVuc = ''
          this.dsHDTB_KV = []
          this.$root.$toast.error('Chưa có thông tin về địa chỉ lắp đặt. Bạn hãy kiểm tra lại!')
        }
        this.$bvModal.hide('gankhuvuc')
      }
    },
    async HienThiTTMoRongTB (thuebao_id, dichvuvt_id) {
      try {
        this.modelCkeckBox.chkCongVan = false
        this.modelCkeckBox.chkdisableCongVan = true
        this.modelInput.txtCongVan = ''
        this.modelDateTime.dtpNgayCV = await this.getNgay_Cn()
        this.inputValue.kt_kenhphu_ao = false
        switch (parseInt(dichvuvt_id)) {
          case Enum.DichVuVienThong.CO_DINH:
            await this.$refs.refCD.HienThiTTMoRongTB(thuebao_id)
            this.inputValue.ds_isdn = await this.$refs.refCD.ds_isdn
            break
          case Enum.DichVuVienThong.IMS:
            await this.$refs.refIMS.HienThiTTMoRongTB(thuebao_id)
            break
          case Enum.DichVuVienThong.IMS_DOITAC:
            await this.$refs.refIMS.HienThiTTMoRongTB(thuebao_id)
            break
          case Enum.DichVuVienThong.ADSL:
            await this.$refs.refAdsl.HienThiTTMoRongTB(thuebao_id)
            break
          case Enum.DichVuVienThong.BRCD_DOITAC:
            await this.$refs.refAdsl.HienThiTTMoRongTB(thuebao_id)
            break
          case Enum.DichVuVienThong.MEGA_EYES:
            await this.$refs.refAdsl.HienThiTTMoRongTB(thuebao_id)
            break
          case Enum.DichVuVienThong.MEGAWAN:
          case Enum.DichVuVienThong.NOIBO_DADIEM:
            await this.$refs.refWan.HienThiTTMoRongTB(thuebao_id)
            break
          case Enum.DichVuVienThong.METRONET:
            await this.$refs.refWan.HienThiTTMoRongTB(thuebao_id)
            break
          case Enum.DichVuVienThong.MGW_DOITAC:
            await this.$refs.refWan.HienThiTTMoRongTB(thuebao_id)
            break
          case Enum.DichVuVienThong.TSL:
          case Enum.DichVuVienThong.NOIBO_2DIEM:
            await this.$refs.refTSL.HienThiTTMoRongTB(thuebao_id)
            break
          case Enum.DichVuVienThong.TSL_DOITAC:
            this.$refs.refTSL.HienThiTTMoRongTB(thuebao_id)
            break
          case Enum.DichVuVienThong.GPHONE:
            await this.$refs.refGP.HienThiTTMoRongTB(thuebao_id)
            break
          case Enum.DichVuVienThong.DICHVU_CNTT:
          case Enum.DichVuVienThong.ANTOAN_BAOMAT_TT:
          case Enum.DichVuVienThong.TRUNGTAM_DULIEU:
          case Enum.DichVuVienThong.HOINGHI_TRUYENHINH:
            await this.$refs.refCNTT.HienThiTTMoRongTB(thuebao_id)
            break
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1824 ~ HienThiTTMoRongTB ~ error', error)
      }
    },
    async MAP_ID (P_MAP, P_SCHEMA, P_TABLENAME, P_PRIMARY, P_VALUE) {
      try {
        let data = {
          LOAI: P_TABLENAME,
          KHOA: P_PRIMARY,
          GIATRI: P_VALUE
        }
        let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/thong-tin-chi-tiet', data)
        if (rs.data !== null && rs.data.length > 0) {
          var obj = rs.data[0]
          return obj[P_MAP]
        } else return null
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1473 ~ MAP_ID ~ error', error)
        return null
      }
    },
    async CapNhat_HDTB_ID_HDTB_KV () {
      for (var i = 0; i < this.dsHDTB_KV.length; i++) {
        this.dsHDTB_KV[i].HDTB_ID = this.inputValue.hdtb_id
      }
    },
    async CapNhat_HDTB_ID_HDTB_KV_DS (hdtb_id) {
      for (var i = 0; i < this.dsHDTB_KV.length; i++) {
        this.dsHDTB_KV[i].HDTB_ID = hdtb_id
      }
    },
    StringToString (xauthamso) { return xauthamso },
    async TaoDuLieu_DiaChi (themmoi, kieu) {
      this.dsDiaChiHDTB = []
      if (kieu === 3 || kieu === 4 || kieu === 5) {
        let rowDiaChiTB = {}
        let rowDiachiLD = {}
        if (themmoi === true) {
          this.inputValue.diachitb_id = await this.GetKey('DIACHI', 'CSS')
          this.inputValue.diachild_id = await this.GetKey('DIACHI', 'CSS')
        }
        rowDiachiLD.DIACHI_ID = this.inputValue.diachild_id
        rowDiachiLD.TINH_ID = this.inputValue.tinhld_id
        rowDiachiLD.QUAN_ID = this.inputValue.quanld_id
        rowDiachiLD.PHUONG_ID = this.inputValue.phuongld_id

        if (this.inputValue.phold_id !== 0) { rowDiachiLD.PHO_ID = this.inputValue.phold_id }
        if (this.inputValue.apld_id !== 0) { rowDiachiLD.AP_ID = this.inputValue.apld_id }
        if (this.inputValue.khuld_id !== 0) { rowDiachiLD.KHU_ID = this.inputValue.khuld_id }
        if (this.inputValue.dacdiemld_id !== 0) { rowDiachiLD.DACDIEM_ID = this.inputValue.dacdiemld_id }

        rowDiachiLD.BLOCK = this.StringToString(this.inputValue.blockld)
        rowDiachiLD.TANG = this.StringToString(this.inputValue.tangld)
        rowDiachiLD.PHONG = this.StringToString(this.inputValue.sophongld)
        rowDiachiLD.MOTA = this.StringToString(this.inputValue.motadcld)

        rowDiachiLD.SONHA = this.StringToString(this.inputValue.sonhald)
        rowDiachiLD.DIACHI = this.StringToString(this.modelInput.txtDiaChiLD)

        // rowDiachiLD.NHOM_LOAINHA_ID = this.diachiLD.NHOM_LOAINHA_ID
        // rowDiachiLD.LOAINHA_ID = this.diachiLD.TEN_LOAINHA
        // rowDiachiLD.SOTANG = this.diachiLD.SO_TANG
        // rowDiachiLD.DIENTICH = this.diachiLD.DIEN_TICH
        // rowDiachiLD.SOPHONG = this.diachiLD.SO_PHONG
        this.dsDiaChi.push(rowDiachiLD)

        rowDiaChiTB.DIACHI_ID = this.inputValue.diachitb_id
        rowDiaChiTB.TINH_ID = this.inputValue.tinhtb_id
        rowDiaChiTB.QUAN_ID = this.inputValue.quantb_id
        rowDiaChiTB.PHUONG_ID = this.inputValue.phuongtb_id
        if (this.inputValue.photb_id !== 0) { rowDiaChiTB.PHO_ID = this.inputValue.photb_id }
        if (this.inputValue.aptb_id !== 0) { rowDiaChiTB.AP_ID = this.inputValue.aptb_id }
        if (this.inputValue.khutb_id !== 0) { rowDiaChiTB.KHU_ID = this.inputValue.khutb_id }
        if (this.inputValue.dacdiemtb_id !== 0) { rowDiaChiTB.DACDIEM_ID = this.inputValue.dacdiemtb_id }

        rowDiaChiTB.BLOCK = this.StringToString(this.inputValue.blocktb)
        rowDiaChiTB.TANG = this.StringToString(this.inputValue.tangtb)
        rowDiaChiTB.PHONG = this.StringToString(this.inputValue.sophongtb)
        rowDiaChiTB.MOTA = this.StringToString(this.inputValue.motadctb)
        rowDiaChiTB.SONHA = this.StringToString(this.inputValue.sonhatb)
        rowDiaChiTB.DIACHI = this.StringToString(this.modelInput.txtDiaChiTB)

        

        this.dsDiaChi.push(rowDiaChiTB)

        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TSL ||
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TSL_DOITAC ||
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM
          ) {
          let rowDiaChiDau = {}
          if (themmoi === true) this.inputValue.diachidau_id = await this.GetKey('DIACHI', 'CSS')
          rowDiaChiDau.DIACHI_ID = this.inputValue.diachidau_id
          rowDiaChiDau.TINH_ID = this.inputValue.tinhdau_id
          rowDiaChiDau.QUAN_ID = this.inputValue.quandau_id
          rowDiaChiDau.PHUONG_ID = this.inputValue.phuongdau_id
          if (this.inputValue.phodau_id !== 0) { rowDiaChiDau.PHO_ID = this.inputValue.phodau_id }
          if (this.inputValue.apdau_id !== 0) { rowDiaChiDau.AP_ID = this.inputValue.apdau_id }
          if (this.inputValue.khudau_id !== 0) { rowDiaChiDau.KHU_ID = this.inputValue.khudau_id }

          rowDiaChiDau.SONHA = this.StringToString(this.inputValue.sonhadau)
          rowDiaChiDau.DIACHI = this.StringToString(this.modelInput.txtDiaChiDau)
          this.dsDiaChi.push(rowDiaChiDau)

          let rowDiaChiCuoi = {}
          if (themmoi === true) { this.inputValue.diachicuoi_id = await this.GetKey('DIACHI', 'CSS') }
          rowDiaChiCuoi.DIACHI_ID = this.inputValue.diachicuoi_id
          rowDiaChiCuoi.TINH_ID = this.inputValue.tinhcuoi_id
          rowDiaChiCuoi.QUAN_ID = this.inputValue.quancuoi_id
          rowDiaChiCuoi.PHUONG_ID = this.inputValue.phuongcuoi_id
          if (this.inputValue.phocuoi_id !== 0) { rowDiaChiCuoi.PHO_ID = this.inputValue.phocuoi_id }
          if (this.inputValue.apcuoi_id !== 0) { rowDiaChiCuoi.AP_ID = this.inputValue.apcuoi_id }
          if (this.inputValue.khucuoi_id !== 0) { rowDiaChiCuoi.KHU_ID = this.inputValue.khucuoi_id }

          rowDiaChiCuoi.SONHA = this.StringToString(this.inputValue.sonhacuoi)
          rowDiaChiCuoi.DIACHI = this.StringToString(this.modelInput.txtDiaChiCuoi)
          this.dsDiaChi.push(rowDiaChiCuoi)
        }
        let rowDiaChiHDTB = {}
        rowDiaChiHDTB.DIACHI_ID = this.inputValue.diachitb_id
        rowDiaChiHDTB.HDTB_ID = this.inputValue.hdtb_id
        rowDiaChiHDTB.DIACHILD_ID = this.inputValue.diachild_id
        rowDiaChiHDTB.KINHDO = this.inputValue.Lng_TB
        rowDiaChiHDTB.VIDO = this.inputValue.Lat_TB
        rowDiaChiHDTB.KINHDO_LD = this.inputValue.Lng_LD
        rowDiaChiHDTB.VIDO_LD = this.inputValue.Lat_LD
        this.dsDiaChiHDTB.push(rowDiaChiHDTB)
        if (themmoi === false) {

        }
      }
      if (themmoi === false) {

      }
    },
    async TaoDuLieu_DKDV () {
      var self = this
      self.dsDK_DVGT = []
      console.log('🚀 ~ file: index.vue:3115 ~ TaoDuLieu_DKDV ~ this.inputValue.m_dsThueBao_DichVu:', this.inputValue.m_dsThueBao_DichVu)
      this.inputValue.m_dsThueBao_DichVu.forEach(dr => {
        let kieu_dk_dvgt = null
        if (dr.dangKy.toString() === '1') {
          kieu_dk_dvgt = Enum.KieuCaiDatDVGT.KIEU_CD_THEM_MOI_DVGT
        } else if (dr.huy.toString() === '1') {
          kieu_dk_dvgt = Enum.KieuCaiDatDVGT.KIEU_CD_HUY_DVGT
        } else if (dr.sua.toString() === '1') {
          kieu_dk_dvgt = Enum.KieuCaiDatDVGT.KIEU_CD_SUA_DVGT
        }
        if (kieu_dk_dvgt !== null) {
          let rowHDTDDV = {}
          rowHDTDDV.HDTB_ID = self.inputValue.hdtb_id
          rowHDTDDV.NGAY_YC = DateTimeLib.toDateDisplay(self.modelDateTime.dtpNgayYC)
          rowHDTDDV.DICHVUGT_ID = dr.dichVuGtId
          rowHDTDDV.NOIDUNG = dr.noiDung
          rowHDTDDV.KIEU_YC = kieu_dk_dvgt
          rowHDTDDV.TTTH = 1
          console.log('🚀 ~ file: index.vue:3111 ~ TaoDuLieu_DKDV ~ rowHDTDDV:', rowHDTDDV)
          self.dsDK_DVGT.push(rowHDTDDV)
        }
      })
    },
    async TaoDuLieu_HDTB (themmoi) {
      try {
        this.dsHDTB = []
        var rowHDTB = {}
        if (themmoi === true) {
          this.inputValue.hdtb_id = await this.GetKey('HD_THUEBAO', 'CSS')
        }
        rowHDTB.HDTB_ID = this.inputValue.hdtb_id
        if (themmoi === true) {
          rowHDTB.HDTT_ID = this.inputValue.hdtt_id
        } else { rowHDTB.HDTT_ID = this.modelCbo.cboTT }
        rowHDTB.HDKH_ID = this.inputValue.hdkh_id
        if (this.inputValue.thuebao_id > 0) { rowHDTB.THUEBAO_ID = this.inputValue.thuebao_id }
        if (this.modelInput.txtMaTBMoi === '') { rowHDTB.MA_TB = this.modelInput.txtMaTB } else { rowHDTB.MA_TB = this.modelInput.txtMaTBMoi }

        if (this.inputValue.isdn_sochinh && this.inputValue.ma_tb_phu_30bd === this.modelInput.txtMaTB && this.inputValue.hdtb_id_cha !== this.inputValue.hdtb_id) {
          rowHDTB.MA_TB = this.modelInput.txtMaTB
        }

        rowHDTB.TEN_TB = this.modelInput.txtTenTB
        rowHDTB.DIACHI_TB = this.modelInput.txtDiaChiTB
        rowHDTB.DIACHI_LD = this.modelInput.txtDiaChiLD
        if (this.modelInput.txtNgaySinhTB !== '') {
          rowHDTB.NGAY_SN = this.modelInput.txtNgaySinhTB
        }
        rowHDTB.KIEULD_ID = this.modelCbo.cboKieuLD
        rowHDTB.DOITUONG_ID = this.modelCbo.cboDoituongTB
        rowHDTB.TTHD_ID = Enum.TrangThaiHD.MOI
        rowHDTB.LOAITB_ID = this.inputValue._loaitb_id
        rowHDTB.DICHVUVT_ID = this.inputValue.dichvuvt_id
        rowHDTB.QUYTRINH_ID = this.inputValue.quytrinh_id
        rowHDTB.DONVI_ID = this.modelCbo.cboDaiVT
        rowHDTB.KIEU_TB = 0

        var status = null
        if (this.modelCbo.cboDichVuVT === Enum.DichVuVienThong.ADSL ||
                this.modelCbo.cboDichVuVT === Enum.DichVuVienThong.IMS ||
                this.modelCbo.cboDichVuVT === Enum.DichVuVienThong.IMS_DOITAC ||
                this.modelCbo.cboDichVuVT === Enum.DichVuVienThong.BRCD_DOITAC) { status = 0 }

        var kiemtra = await this.KT_ThaoTac(this.inputValue.luong_id, 'STATUS_TONGDAI_CD')
        if (kiemtra) status = 0
        rowHDTB.STATUS = status
        rowHDTB.GHICHU = this.modelInput.txtGhiChuTB.replaceAll('UPDATE', '')
        rowHDTB.DONVI_DAU_ID = this.inputValue.donvi_dau_id
        rowHDTB.DONVI_CUOI_ID = this.inputValue.donvi_cuoi_id

        if (this.modelCbo.vcboMucCuocTB.length > 0) {
          rowHDTB.MUCUOCTB_ID = this.modelCbo.cboMucCuocTB
          rowHDTB.MUCUOC_TB = await this.MAP_ID('CUOC_TB', 'CSS', 'muccuoc_tb', 'mucuoctb_id', rowHDTB.MUCUOCTB_ID)
        }
        if (this.inputValue.loaitb_id === Enum.LoaiHinhTB.ISDN2B_CD || this.inputValue.loaitb_id === Enum.LoaiHinhTB.ISDN2B_CQ ||
                this.inputValue.loaitb_id === Enum.LoaiHinhTB.ISDN30B_CD || this.inputValue.loaitb_id === Enum.LoaiHinhTB.ISDN30B_CQ ||
                this.inputValue.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_2M || this.inputValue.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_THUONG ||
                this.inputValue.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_TUONGTU) {
          if (this.inputValue.loaisochinh === 0) {
            if (this.inputValue.hdtb_id_cha > 0 && this.inputValue.hdtb_id_cha !== this.inputValue.hdtb_id) { rowHDTB.HDTB_CHA_ID = this.inputValue.hdtb_id_cha }
          }
        }
        if (this.modelCbo.cboDichVuVT === Enum.DichVuVienThong.MEGAWAN ||
              this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_DADIEM ||
              this.modelCbo.cboDichVuVT === Enum.DichVuVienThong.METRONET) {
          if (this.inputValue.thuebao_id > 0) {
            var tb_cha_id = await this.get_thuebao_cha_id(this.inputValue.thuebao_id)
            if (tb_cha_id !== -1) { rowHDTB.THUEBAO_CHA_ID = tb_cha_id }
          }
        }
        if (this.modelCbo.vcboGoiKT.length > 0) { rowHDTB.GOIKT_ID = this.modelCbo.cboGoiKT }
        this.dsHDTB.push(rowHDTB)
        var self = this
        this.dsKM_HDTB.forEach(element => {
          element.HDTB_ID = self.inputValue.hdtb_id
        })
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 3135 ~ TaoDuLieu_HDTB ~ error', error)
      }
    },
    async KT_ThaoTac (luong_id, code) {
      try {
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/kt_thaotac', {luong_id, code})
        if (rs.data.length > 0) {
          if (rs.data[0].SL > 0) return true
          else return false
        } else return false
      } catch (error) {
        return false
      }
    },
    async get_thuebao_cha_id (thuebao_id) {
      try {
        var d = await this.MAP_ID('THUEBAO_CHA_ID', 'CSS', 'db_thuebao', 'thuebao_id', thuebao_id)
        if (d === null) return -1
        return d
      } catch (error) {
        return -1
      }
    },
    async btnEditDVGT_Click () {
      return false
      if (this.inputValue.m_dsThueBao_DichVu.length === 0) {
        this.$root.$toast.error('Chưa có thông tin thuê bao')
        return false
      }
      this.popupComponent = () => import('./Popups/popupServiceAdds')
      this.popupComponentName = 'popupServiceAdds'
      var listGVGT = []
      if (this.inputValue.m_dsThueBao_DichVu.length > 0) {
        listGVGT = this.inputValue.m_dsThueBao_DichVu.filter(x => x.dangKy === 1)
      }
      this.popupComponentData = {_loaitb_id: this.inputValue.loaitb_id, _hdtb_id: this.inputValue.hdtb_id, _linhvuc_id: 0, _tocdo_id: this.inputValue.tocdo_id, lvDvgt: listGVGT}
      this.Popup('popupComponents')
    },
    Popup (modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup (modalId) {
      this.$bvModal.hide(modalId)
    },
    async get_MUCUOC_TB (mucuoctb_id) {},
    async GetKey (Key, SCHEMA) {
      try {
        var data = {
          VKEYNAME: Key,
          VKEY_SCHEMA: SCHEMA
        }
        var rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/getkey', data)
        if (rs.data.length > 0) {
          return rs.data[0].KEY
        } else { return null }
      } catch (error) {
        return null
      }
    },
    async kt_fiber_colapchung (kieu, thuebao_id, hdtb_id, hdkh_id) {
      try {
        var data = {
          kieu, thuebao_id, hdtb_id, hdkh_id
        }
        var rs = await this.$root.context.post(
          '/web-hopdong/chuyendich/kt_fiber_colapchung', data
        )
        return rs.data
      } catch (error) {
        return []
      }
    },
    async LayHuongGiao_TiepNhan (loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id) {
      try {
        var data = {
          loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id
        }
        var rs = await this.$root.context.post(
          '/web-hopdong/chuyendich/layhuonggiao_tiepnhan', data
        )
        if (rs.data && rs.data.length > 0) {
          return rs.data
        } else {
          return []
        }
      } catch (error) {
        return []
      }
    },
    async LayHuongGiao_TiepNhan_2 (loaihd_id, kieuld_id) {
      try {
        var data = {
          loaihd_id, kieuld_id
        }
        var rs = await this.$root.context.post(
          '/web-hopdong/chuyendich/layhuonggiao_tiepnhan_2', data
        )
        return rs.data
      } catch (error) {
        return []
      }
    },
    async LayHuongGiao_TiepNhan_V2 (loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id, loaikenh_id, dichvuvt_id) {
      try {
        var data = {
          loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id, loaikenh_id, dichvuvt_id
        }
        var rs = await this.$root.context.post('/web-hopdong/thanhly-thuebao-bancheo/layhuonggiao_tiepnhan_v2', data)
        console.log('🚀 ~ file: index.vue ~ line 2461 ~ LayHuongGiao_TiepNhan_V2 ~ rs', rs)
        return rs.data === null ? [] : rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2464 ~ LayHuongGiao_TiepNhan_V2 ~ error', error)
        return []
      }
    },
    async getDiaChiLD (data) {
      console.log('🚀 ~ file: index.vue:5776 ~ data', data)
      this.diachiLD.DIACHI = data.diachimoi
      this.diachiLD.TINH_ID = data.tinh_id
      this.diachiLD.QUAN_ID = data.quan_id
      this.diachiLD.PHUONG_ID = data.phuong_id
      this.diachiLD.PHO_ID = data.pho_id
      this.diachiLD.AP_ID = data.ap_id
      this.diachiLD.KHU_ID = data.khu_id
      this.diachiLD.DACDIEM_ID = data.dacdiem_id
      this.diachiLD.SO_NHA = data.so_nha
      this.diachiLD.Lat = data.lat
      this.diachiLD.Lng = data.lng

      if (data.ten_loainha !== undefined && data.ten_loainha !== null) {
        this.diachiLD.TEN_LOAINHA = data.ten_loainha
        this.diachiLD.BLOCK = data.block
        this.diachiLD.TANG = data.tang
        this.diachiLD.PHONG = data.phong
        this.diachiLD.MOTA = data.thongtin_them
        this.diachiLD.NHOM_LOAINHA_ID = data.nhom_loainha_id
        this.diachiLD.DIEN_TICH = data.dien_tich
        this.diachiLD.SO_TANG = data.so_tang
        this.diachiLD.SO_PHONG = data.so_phong
      }


      console.log('this.diachiLD', this.diachiLD)

      this.inputValue.tinhld_id = data.tinh_id
      this.inputValue.quanld_id = data.quan_id
      this.inputValue.phuongld_id = data.phuong_id
      this.inputValue.phold_id = data.pho_id
      this.inputValue.apld_id = data.ap_id
      this.inputValue.khuld_id = data.khu_id
      this.inputValue.dacdiemld_id = data.dacdiem_id
      this.inputValue.Lng_LD = data.lng
      this.inputValue.Lat_LD = data.lat
      this.inputValue.tenLoaiNhald_id = data.ten_loainha
      this.inputValue.blockld = data.blockld
      this.inputValue.tangld = data.tangld
      this.inputValue.sophongld = data.sophongld
      this.inputValue.motadcld = data.motadcld
      this.inputValue.sonhald = data.so_nha
      this.modelInput.txtDiaChiLD = data.diachimoi
      this.diachi_flag = null
      this.$refs.txtDiaChiLD.focus()
      if (data.ketcuoi_id !== -1) { this.inputValue.KetCuoi_ID = data.ketcuoi_id }
      var ds = await this.LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2(Enum.LOAI_DV.TRAM_VT, data.phuong_id, data.pho_id, data.ap_id, data.khu_id, data.dacdiem_id)
      if (ds.length <= 0) {
        ds = await this.LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2(Enum.LOAI_DV.TRAM_VT, data.phuong_id, data.pho_id, data.ap_id, 0, data.dacdiem_id)
        if (ds.length <= 0) {
          ds = await this.LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2(Enum.LOAI_DV.TRAM_VT, data.phuong_id, data.pho_id, 0, 0, data.dacdiem_id)
          if (ds.length <= 0) {
            this.$root.$toast.warning('Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt. Hãy liên hệ với Admin để xử lý!')
            if (this.inputValue.ht_loc_cbo_tram) {
              let dtControl = await this.getControl()
              for (let index = 0; index < dtControl.dsComboDonVi.length; index++) {
                const element = dtControl.dsComboDonVi[index]
                if (element.loaiDonVi === Enum.LOAI_DV.TRAM_VT || element.loaiDonVi === Enum.LOAI_DV.TRAM_CAP_PORT) {
                  await dtControl.ref.HT_DonVi_TRAMTC_Combobox(element, -7)
                }
              }
              return false
            } else {
              this.modelCbo.cboDaiVT = -1
              return false
            }
          }
        }
      }
      console.log('🚀 ~ file: index.vue:3361 ~ getDiaChiLD ~ ds:', ds)
      if (ds.length === 1) {
        this.modelCbo.cboDaiVT = parseInt(ds[0].donviql_id)
        let dtControl = await this.getControl()
        for (let index = 0; index < dtControl.dsComboDonVi.length; index++) {
          const element = dtControl.dsComboDonVi[index]
          if (element.loaiDonVi === Enum.LOAI_DV.TRAM_VT || element.loaiDonVi === Enum.LOAI_DV.TRAM_CAP_PORT) {
            console.log('🚀 ~ file: index.vue:3368 ~ getDiaChiLD ~ this.inputValue.ht_loc_cbo_tram:', this.inputValue.ht_loc_cbo_tram)
            if (this.inputValue.ht_loc_cbo_tram) {
              await dtControl.ref.HT_DonVi_TRAMTC_Combobox(element, parseInt(ds[0].donvi_id))
              // dtControl.ref.$refs[element.key][0].value = ds[0].donvi_id
            } else {
              await dtControl.ref.HT_DonVi_TRAMTC_Combobox2(element, Enum.LOAI_DV.TRAM_VT, parseInt(this.modelCbo.cboDaiVT))
              dtControl.ref.$refs[element.key][0].value = parseInt(ds[0].donvi_id)
            }
          }
        }
      } else {
        let resolvePromise = await this.$refs.popupChonDonViTC.show(null, null, null, null, [...ds])
        console.log('🚀 ~ file: index.vue:3690 ~ getDiaChiTuFormDiaChi ~ resolvePromise', resolvePromise)
        if (resolvePromise.ok) {
          this.modelCbo.cboDaiVT = resolvePromise.data.donviql_id
          let dtControl = await this.getControl()
          for (let index = 0; index < dtControl.dsComboDonVi.length; index++) {
            let element = dtControl.dsComboDonVi[index]
            if (element.loaiDonVi === Enum.LOAI_DV.TRAM_VT || element.loaiDonVi === Enum.LOAI_DV.TRAM_CAP_PORT) {
              if (this.inputValue.ht_loc_cbo_tram) {
                await dtControl.ref.HT_DonVi_TRAMTC_Combobox(element, parseInt(ds[0].donvi_id))
              } else {
                await dtControl.ref.HT_DonVi_TRAMTC_Combobox2(element, Enum.LOAI_DV.TRAM_VT, parseInt(this.modelCbo.cboDaiVT))
                dtControl.ref.$refs[element.key][0].value = resolvePromise.data.donvi_id
              }
            }
          }
        } else {
          this.modelCbo.cboDaiVT = -1
          let dtControl = await this.getControl()
          for (let index = 0; index < dtControl.dsComboDonVi.length; index++) {
            const element = dtControl.dsComboDonVi[index]
            if (element.loaiDonVi === Enum.LOAI_DV.TRAM_VT || element.loaiDonVi === Enum.LOAI_DV.TRAM_CAP_PORT) {
              dtControl.ref.$refs[element.key][0].value = -1
            }
          }
          return false
        }
      }
      await this.SP_LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2(data.phuong_id, data.pho_id, data.ap_id, data.khu_id, data.dacdiem_id)
      this.inputValue.tinhld_id = data.tinh_id
      this.inputValue.quanld_id = data.quan_id
      this.inputValue.phuongld_id = data.phuong_id
      this.inputValue.dacdiemld_id = data.dacdiem_id
      this.inputValue.phold_id = data.pho_id
      this.inputValue.apld_id = data.ap_id
      this.inputValue.khuld_id = data.khu_id
      this.inputValue.sonhald = data.so_nha
      this.inputValue.Lng_LD = data.lng
      this.inputValue.Lat_LD = data.lat
      this.modelInput.txtDiaChiLD = data.diachimoi

      this.inputValue.blockld = data.block
      this.inputValue.tangld = data.tang
      this.inputValue.sophongld = data.sophong
      this.inputValue.motadcld = data.motathem
      await this.LayTienTheoKhoanMuc()

      this.inputValue.tinhtb_id = data.tinh_id
      this.inputValue.quantb_id = data.quan_id
      this.inputValue.phuongtb_id = data.phuong_id
      this.inputValue.photb_id = data.pho_id
      this.inputValue.dacdiemtb_id = data.dacdiem_id
      this.inputValue.aptb_id = data.ap_id
      this.inputValue.khutb_id = data.khu_id

      this.inputValue.Lng_TB = data.lng
      this.inputValue.Lat_TB = data.lat

      this.inputValue.sonhatb = data.so_nha
      this.modelInput.txtDiaChiTB = data.diachimoi

      this.inputValue.blocktb = data.block
      this.inputValue.tangtb = data.tang
      this.inputValue.sophongtb = data.sophong
      this.inputValue.motadctb = data.motathem

      await this.HT_NV_KinhDoanh_Theo_DiaBan(this.inputValue.quanld_id, this.inputValue.phuongld_id, this.inputValue.phold_id, this.inputValue.apld_id, this.inputValue.khuld_id,
        parseInt(this.ThongTinKhachHang.cboDonViQL), this.inputValue.dacdiemld_id)
      this.$refs.txtDiaChiLD.focus()
      // await this.HT_DS_KhuyenMai_Combobox()
      if (this.thongtin.MUCUOCTB_ID !== null) {
        let arr = await this.HT_MucCuocTB_Combobox(
          this.thongtin.MUCUOCTB_ID
        )
        if (arr.length > 0) {
          this.modelCbo.vcboMucCuocTB = arr
          this.modelCbo.cboMucCuocTB = this.thongtin.MUCUOCTB_ID // arr[0].MUCUOCTB_ID
        } else {
          this.modelCbo.vcboMucCuocTB = []
          this.modelCbo.cboMucCuocTB = -1
        }
      }
    },
    async KIEMTRA_MA_TB_LAP_HOPDONG_KHAC (ma_tb, ma_gd, dichvuvt_id, loaihd_id) {
      try {
        var data = {ma_tb, ma_gd, dichvuvt_id, loaihd_id}
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_ma_tb_lap_hopdong_khac', data)
        if (rs.data && rs.data.length > 0) {
          let ds = rs.data
          this.$root.$toast.warning('Số máy/Acc: ' + ma_tb + ' đang được lập bởi hợp đồng (phụ lục) ' + ds[0]['ten_loaihd'] + ' có mã giao dịch ' + ds[0]['ma_gd'] || ' do User ' + ds[0]['nguoi_cn'] + ' thuộc đơn vị ' + ds[0]['ten_dv'] + ' thực hiện vào ngày ' + ds[0]['ngay_yc'])
          return true
        } else return false
      } catch (error) {
        this.$root.$toast.error('Lỗi thực hiện kiểm tra thuê bao lập hợp đồng khác, liên hệ Admin để xử lý!!!')
        return true
      }
    },
    async KIEMTRA_DB_KHI_THEMMOI (ma_tb, khachhang_id, thuebao_id, dichvuvt_id) {
      try {
        var data = {
          ma_tb, khachhang_id, thuebao_id, dichvuvt_id
        }
        var rs = await this.$root.context.post(
          '/web-hopdong/chuyendich/kiemtra_db_khi_themmoi', data
        )
        return parseInt(rs.data) === 1
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2102 ~ KIEMTRA_DB_KHI_THEMMOI ~ error', error)
        return false
      }
    },
    async KIEMTRA_MATB_KHOSO (txtMaTB, dichvuvt_id) {
      try {
        var rs = await this.$root.context.get(
          '/web-hopdong/chuyendich/kiemtra_matb_khoso?ma_tb=' + txtMaTB + '&dichvuvt_id=' + dichvuvt_id)
        if (parseInt(rs.data) === 1) {
          return true
        } else return false
      } catch (error) {
        return false
      }
    },
    async KemTraDL_HD_CQ (thuebao_id) {
      var kiemtra = await this.KiemTra_HD_CQ(thuebao_id, 2)
      if (kiemtra) {
        this.$root.$toast.error('Thuê bao đã thực hiện chuyển quyền, bạn không thể thực hiện dịch chuyển !')
        return false
      }
      return true
    },
    async KiemTra_HD_CQ (vthuebao_id, vloaihd_id) {
      try {
        var data = {
          vthuebao_id, vloaihd_id
        }
        var rs = await this.$root.context.post(
          '/web-hopdong/chuyendich/kiemtra_chuyenquyen_tb', data
        )
        if (rs.data.length > 0) {
          if (parseInt(rs.data[0]['count(*)']) > 0) return true
          else return false
        } else return false
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2140 ~ KiemTra_HD_CQ ~ error', error)
        return false
      }
    },
    async KiemTraDL_ThueBao () {
      try {
        if (this.modelInput.txtMaHDcu === '') {
          this.$root.$toast.error('Bạn chưa nhập mã hợp đồng !')
          return false
        }
        if (this.modelInput.txtTenTB === '') {
          this.$root.$toast.error('Bạn chưa nhập tên thuê bao !')
          return false
        }

        if (this.modelInput.txtDiaChiTB === '' || this.inputValue.quantb_id === 0 || this.inputValue.phuongtb_id === 0) {
          this.$root.$toast.error('Bạn chưa nhập địa chỉ thuê bao !')
          return false
        }
        if (this.modelInput.txtDiaChiLD === '' || this.inputValue.quanld_id === 0 || this.inputValue.phuongld_id === 0) {
          this.$root.$toast.error('Bạn chưa nhập địa chỉ lắp đặt !')
          return false
        }

        if (this.modelCbo.cboKieuLD === -1) {
          this.$root.$toast.error('Hãy chọn kiểu yêu cầu cho thuê bao này !')
          return false
        }

        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.CO_DINH || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.GPHONE) {
          if (this.modelCbo.cboMucCuocTB === -1) {
            this.$root.$toast.error('Hãy nhập mức cước thuê bao !')
            return false
          }
        }

        if (this.modelCkeckBox.chkKhuyenMai && this.dsKM_HDTB.length === 0) {
          this.$root.$toast.error('Bạn chưa chọn chi tiết khuyến mại !')
          return false
        }

        if (this.inputValue.batbuoc_chon_dacdiem_dcld) {
          if (this.modelCbo.cboDichVuVT.SelectedValue !== Enum.DichVuVienThong.DI_DONG && this.modelCbo.cboLoaihinhTB !== Enum.LoaiHinhTB.DIDONGTRATRUOC) {
            if ((this.inputValue.quanld_id !== 0 || this.inputValue.phuongld_id !== 0) && (this.inputValue.phold_id !== 0 || this.inputValue.apld_id !== 0 || this.inputValue.khuld_id !== 0)) {
              try {
                var data = {PHUONGLD_ID: this.inputValue.phuongld_id, PHOLD_ID: this.inputValue.phold_id}
                var rs = await this.$root.context.post('/web-hopdong/chuyendich/chon_dacdiem_dcld', data)
                if (rs.data && rs.data.length > 0) {
                  if (this.inputValue.dacdiemld_id === 0) {
                    this.$root.$toast.error('Địa chỉ lắp đặt có đặc điểm. Yêu cầu chọn đặc điểm !')
                    return false
                  }
                }
              } catch (error) {

              }
            }
          }
        }

        if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL || 
          this.inputValue.dichvuvt_id == Enum.DichVuVienThong.TSL_DOITAC ||
          this.inputValue.dichvuvt_id == Enum.DichVuVienThong.NOIBO_2DIEM
          ) {
        // Hiếu bổ sung bắt lỗi pải nhập vào địa chỉ đầu, địa chỉ cuối ngày 06/09/2010
          if (this.$refs.refTSL.cboTramVTDau.Value === null) {
            this.$root.$toast.error('Hãy chọn đơn vị đầu !')
            return false
          }
          if (this.$refs.refTSL.cboTramVTCuoi.Value === null) {
            this.$root.$toast.error('Hãy chọn đơn vị cuối !')
            return false
          }
          if (this.$refs.refTSL.txtDiaChiDau.Text == '' || this.$refs.refTSL.quandau_id == 0 || this.$refs.refTSL.phuongdau_id == 0) {
            this.$root.$toast.error('Hãy nhập địa chỉ đầu !')
            return false
          }
          if (this.$refs.refTSL.txtDiaChiCuoi.Text == '' || this.$refs.refTSL.quancuoi_id == 0 || this.$refs.refTSL.phuongcuoi_id == 0) {
            this.$root.$toast.error('Hãy nhập địa chỉ cuối !')
            return false
          }
          if (this.$refs.refTSL.chkDC_Dau.Checked == false && this.$refs.refTSL.chkDC_Cuoi.Checked == false) {
            this.$root.$toast.error('Bạn chưa chọn thay đổi địa chỉ đầu hoặc cuối !')
            return false
          }
        }

        var kieu = await this.MAP_ID('KIEU', 'CSS', 'KIEU_LD', 'KIEULD_ID', this.modelCbo.cboKieuLD)
        console.log('🚀 ~ file: index.vue ~ line 3227 ~ KiemTraDL_ThueBao ~ kieu', kieu)
        if ((kieu.toString() === '1' || kieu.toString() === '2' || kieu.toString() === '4') && this.modelCbo.cboDichVuVT === Enum.DichVuVienThong.CO_DINH && (this.$refs.refCD.txtMaTN_CD.Text === null || this.$refs.refCD.txtMaTN_CD.Text === '')) {
          this.$root.$toast.error('Khi kiểu lắp đặt trên đường có sẵn, mã truy nhập không được trống!')
          return false
        }
        if ((kieu.toString() === '1' || kieu.toString() === '2' || kieu.toString() === '4') && (this.modelCbo.cboDichVuVT === Enum.DichVuVienThong.IMS || this.modelCbo.cboDichVuVT === Enum.DichVuVienThong.IMS_DOITAC) && (this.$refs.refIMS.txtMATN_IMS.Text === null || this.$refs.refIMS.txtMATN_IMS.Text === '')) {
          this.$root.$toast.error('Khi kiểu lắp đặt trên đường có sẵn, mã truy nhập không được trống!')
          return false
        }

        if ((kieu.toString() === '1' || kieu.toString() === '2' || kieu.toString() === '4') && (this.modelCbo.cboDichVuVT === Enum.DichVuVienThong.ADSL || this.modelCbo.cboDichVuVT === Enum.DichVuVienThong.BRCD_DOITAC) && (this.$refs.refAdsl.txtMaTN_ADSL.Text === null || this.$refs.refAdsl.txtMaTN_ADSL.Text === '')) {
          this.$root.$toast.error('Khi kiểu lắp đặt trên đường có sẵn, mã truy nhập không được trống!')
          return false
        }

        if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.CO_DINH) {
          var tg_thueso = await this.MAP_ID('TG_THUESO', 'CSS', 'DB_CD', 'THUEBAO_ID', this.inputValue.thuebao_id)
          if (tg_thueso != null && tg_thueso.toString() !== '-1') {
            this.$root.$toast.error('Bạn không được lập phụ lục dịch chuyển cho thuê bao thuê số !')
            return false
          }
          var nhomkieu_ld = await this.MAP_ID('NHOMKIEU_LD', 'CSS', 'KIEU_LD', 'KIEULD_ID', this.modelCbo.cboKieuLD)
          if (nhomkieu_ld != null && nhomkieu_ld.toString() === '1') {
            if (this.inputValue.kieu_thueso === 0) {
              this.$root.$toast.error('Bạn chưa nhập thông tin thuê số !')
              return false
            }
            if (this.inputValue.sothang_thueso === 0) {
              this.$root.$toast.error('Bạn chưa nhập thời gian thuê số !')
              return false
            }
            if (this.inputValue.kieu_thueso === 1 && this.inputValue.noidung_thueso === '') {
              this.$root.$toast.error('Bạn chưa nhập số trượt !')
              return false
            }
            if (this.inputValue.kieu_thueso === 2 && this.inputValue.noidung_thueso === '') {
              this.$root.$toast.error('Bạn chưa nhập nội dung thông báo !')
              return false
            }
          }
          if (this.modelInput.txtMaTBMoi === '') {
            var KIEMTRA_DOISO = await this.KIEMTRA_DOISO(this.modelInput.txtMaTB, this.modelCbo.cboDichVuVT, this.inputValue.quanld_id, this.inputValue.phuongld_id)
            if (KIEMTRA_DOISO <= 0) {
              this.$root.$toast.error('Thuê bao ' + this.modelInput.txtMaTB + ' di chuyển sang khu vực mới. Bạn hãy chọn số mới')
              return false
            }
          }
        }
        // edit https://cntt.vnpt.vn/browse/KHDN-16892
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || 
            this.inputValue.dichvuvt_id === Enum.DichVuVienThong.METRONET || 
            this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_DADIEM) {
          if (this.inputValue.kt_kenhphu_ao) {
            if (this.modelCbo.cboKieuLD !== Enum.KieuLapDat.DICHCHUYEN_KP_AO) {
              this.$root.$toast.error('Thuê bao ' + this.modelInput.txtMaTB + " là kênh phụ ảo. Bạn phải chọn kiểu lắp đặt là 'Dịch chuyển kênh phụ ảo' !")
              return false
            }
          } else {
            if (this.modelCbo.cboKieuLD === Enum.KieuLapDat.DICHCHUYEN_KP_AO) {
              this.$root.$toast.error("Bạn không được chọn kiểu lắp đặt 'Dịch chuyển kênh phụ ảo' cho thuê bao '" + this.modelInput.txtMaTB + "' !")
              return false
            }
          }
        }
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.DICHVU_CNTT || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.ANTOAN_BAOMAT_TT ||
                this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TRUNGTAM_DULIEU || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.HOINGHI_TRUYENHINH) {
          if (this.inputValue.check_chuahoa_cntt) {
            let check_chuahoa_cntt = await this.check_chuahoa_cntt(this.inputValue.thuebao_id, Enum.LoaiHopDong.DI_CHUYEN)
            if (check_chuahoa_cntt) {
              this.$root.toastError('Thuê bao CNTT/GTGT này cần chuẩn hóa dữ liệu.\n Bạn chỉ được thực hiện Tạm dừng, Thanh lý hoặc chuẩn hóa ở chức năng Đặt mới.')
              return false
            }
          }
        }
        let dtControl = await this.getControl()
        for (let index = 0; index < dtControl.dsComboDonVi.length; index++) {
          const row = dtControl.dsComboDonVi[index]
          let donvi = dtControl.ref.$refs[row.key][0].value
          if (donvi === null || donvi === '' || donvi === 0 || donvi === -1) {
            this.$root.$toast.error('Chưa có dữ liệu về đơn vị thi công. Bạn hãy kiểm tra lại !')
            return false
          }
        }
        return true
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 3224 ~ KiemTraDL_ThueBao ~ error', error)
        return false
      }
    },
    async check_chuahoa_cntt (thuebao_id, loaihd_id) {
      try {
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/check_chuahoa_cntt', {thuebao_id, loaihd_id})
        if (rs.data) {
          return rs.data.toString() === '1'
        } else return false
      } catch (error) {
        return false
      }
    },
    async KIEMTRA_DOISO (ma_tb, dichvuvt_id, quan_id, phuong_id) {
      try {
        var data = {
          ma_tb, dichvuvt_id, quan_id, phuong_id
        }
        var rs = await this.$root.context.post(
          '/web-hopdong/chuyendich/kiemtra_doiso', data
        )
        return rs.data
      } catch (error) {
        return 0
      }
    },
    async CHAN_DICHUYEN_KH_CQSD () {
      try {
        var rs = await this.$root.context.post(
          '/web-hopdong/chuyendich/chan_dichuyen_kh_cqsd'
        )
        return rs.data
      } catch (error) {
        return []
      }
    },
    HT_DS_KhuyenMai_Combobox: async function () {
      let itocdo_id = 0
      let itrangbi_id = 0 // Default: KH tự trang bị
      let imuccuoc_id = 0
      let iloaitb_id = 0
      if (this.modelCbo.vcboKieuLD.length <= 0) {
        return
      }
      if (
        this.modelCbo.cboDoituongTB == null ||
        this.modelCbo.cboDoituongTB === -1
      ) {
        return
      }
      if (this.modelCbo.cboDichVuVT !== null && !this.modelCbo.cboDichVuVT.toString().includes('.')) {
        this.inputValue.dichvuvt_id = parseInt(this.modelCbo.cboDichVuVT)
      }
      switch (this.inputValue.dichvuvt_id) {
        case Enum.DichVuVienThong.CO_DINH:
          itocdo_id = 0
          break
        case Enum.DichVuVienThong.IMS:
        case Enum.DichVuVienThong.IMS_DOITAC:
          itocdo_id = 0
          break
        case Enum.DichVuVienThong.ADSL:
        case Enum.DichVuVienThong.BRCD_DOITAC:
          if (
            this.$refs.refAdsl.cboGoiCuocAdsl.Value == null ||
            this.$refs.refAdsl.cboGoiCuocAdsl.Value.toString().includes('.')
          ) {
            return
          }
          itocdo_id = this.$refs.refAdsl.cboGoiCuocAdsl.Value
          break
        case Enum.DichVuVienThong.MEGA_EYES:
          if (
            this.$refs.refAdsl.cboGoiCuocAdsl.Value == null ||
            this.$refs.refAdsl.cboGoiCuocAdsl.Value.toString().includes('.')
          ) {
            return
          }
          itocdo_id = this.$refs.refAdsl.cboGoiCuocAdsl.Value
          break
        case Enum.DichVuVienThong.MEGAWAN:
        case Enum.DichVuVienThong.METRONET:
        case Enum.DichVuVienThong.NOIBO_DADIEM:
        case Enum.DichVuVienThong.MGW_DOITAC:
          if (
            this.$refs.refWan.cboTocDo.Value == null ||
            this.$refs.refWan.cboTocDo.Value.toString().includes('.')
          ) {
            return
          }
          itocdo_id = this.$refs.refWan.cboTocDo.Value
          break
        case Enum.DichVuVienThong.TSL:
        case Enum.DichVuVienThong.NOIBO_2DIEM:
        case Enum.DichVuVienThong.TSL_DOITAC:
          if (
            this.$refs.refTSL.cboTocDoKenhTSL.Value == null ||
            this.$refs.refTSL.cboTocDoKenhTSL.Value.toString().includes('.')
          ) {
            return
          }
          itocdo_id = this.$refs.refTSL.cboTocDoKenhTSL.Value
          break
        case Enum.DichVuVienThong.GPHONE:
          itocdo_id = 0
          break
        default:
          itocdo_id = 0
          break
      }
      if (
        this.modelCbo.cboMucCuoc != null &&
        !this.modelCbo.cboMucCuoc.toString().includes('.')
      ) {
        imuccuoc_id = this.modelCbo.cboMucCuoc
      }
      if (
        this.modelCbo.cboLoaihinhTB != null &&
        !this.modelCbo.cboLoaihinhTB.toString().includes('.')
      ) {
        iloaitb_id = this.modelCbo.cboLoaihinhTB
      }
      this.dsKhuyenMai = await this.LAY_KHUYENMAI_HDTB(
        iloaitb_id,
        this.modelCbo.cboKieuLD,
        this.inputValue.phuongld_id,
        Enum.KHOANMUC_TT.KMTT_DATMOI,
        this.inputValue.phold_id,
        this.inputValue.apld_id,
        this.inputValue.khuld_id,
        itrangbi_id,
        this.modelCbo.cboDoituongTB,
        itocdo_id,
        imuccuoc_id,
        0
      )
      this.modelCbo.vcboKhuyenMai = this.dsKhuyenMai
      if (this.modelCbo.vcboKhuyenMai.length === 0) {
        this.modelCkeckBox.chkdisableKhuyenMai = true
      } else {
        this.modelCkeckBox.chkdisableKhuyenMai = false
        this.modelCbo.cboKhuyenMai = this.modelCbo.vcboKhuyenMai[0].khuyenmai_id
      }
      this.modelCkeckBox.chkKhuyenMai = false
    },
    LAY_KHUYENMAI_HDTB: async function (
      loaitb_id,
      kieuld_id,
      phuongld_id,
      khoanmuctt_id,
      phold_id,
      apld_id,
      khuld_id,
      trangbi_id,
      doituong_id,
      tocdo_id,
      muccuoc_id,
      loai_km
    ) {
      try {
        let data = {
          loaitb_id,
          kieuld_id,
          phuongld_id,
          khoanmuctt_id,
          phold_id,
          apld_id,
          khuld_id,
          trangbi_id,
          doituong_id,
          tocdo_id,
          muccuoc_id,
          loai_km
        }
        let res = []
        if (this.inputValue.tenLoaiNhald_id === null || this.inputValue.tenLoaiNhald_id === -1) {
          res = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_khuyenmai_hdtb', data)
        } else {
          data.p_loainha_id = this.inputValue.tenLoaiNhald_id
          res = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_khuyenmai_hdtb_v2', data)
        }
        
        return res.data
      } catch (error) {
        return []
      }
    },
    async  getControl () {
      try {
        let dtControl = {}
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.CO_DINH) { dtControl = {ref: this.$refs.refCD, dsComboDonVi: this.$refs.refCD.dsComboDonVi} }
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.IMS || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.IMS_DOITAC) { dtControl = {ref: this.$refs.refIMS, dsComboDonVi: this.$refs.refIMS.dsComboDonVi} }
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.ADSL || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.BRCD_DOITAC) { dtControl = {ref: this.$refs.refAdsl, dsComboDonVi: this.$refs.refAdsl.dsComboDonVi} }
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MEGA_EYES) { dtControl = {ref: this.$refs.refAdsl, dsComboDonVi: this.$refs.refAdsl.dsComboDonVi} }
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || 
            this.inputValue.dichvuvt_id === Enum.DichVuVienThong.METRONET || 
            this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_DADIEM ||
            this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MGW_DOITAC) { 
              dtControl = {ref: this.$refs.refWan, dsComboDonVi: this.$refs.refWan.dsComboDonVi} 
        }
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TSL || 
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TSL_DOITAC ||
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM
          ) { 
            dtControl = {ref: this.$refs.refTSL, dsComboDonVi: this.$refs.refTSL.dsComboDonVi} 
          }
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.GPHONE) { dtControl = {ref: this.$refs.refGP, dsComboDonVi: this.$refs.refGP.dsComboDonVi} }
        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.HOINGHI_TRUYENHINH || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.ANTOAN_BAOMAT_TT || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.DICHVU_CNTT || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TRUNGTAM_DULIEU) {
          dtControl = {ref: this.$refs.refCNTT, dsComboDonVi: this.$refs.refCNTT.dsComboDonVi}
        }
        return dtControl
      } catch (error) {
        return null
      }
    },
    async getDiaChiTuFormDiaChi (diachi) {
      let data = {...diachi}
      switch (this.diachi_flag) {
        case 'DiachiLD':
          this.inputValue.tinhld_id = data.tinh_id
          this.inputValue.quanld_id = data.quan_id
          this.inputValue.phuongld_id = data.phuong_id
          this.inputValue.phold_id = data.pho_id
          this.inputValue.apld_id = data.ap_id
          this.inputValue.khuld_id = data.khu_id
          this.inputValue.dacdiemld_id = data.dacdiem_id
          this.inputValue.Lng_LD = data.longtitude
          this.inputValue.Lat_LD = data.lattitude
          this.inputValue.sonhald = data.so_nha
          this.modelInput.txtDiaChiLD = data.diachimoi
          this.diachi_flag = null
          this.$refs.txtDiaChiLD.focus()
          if (data.ketcuoi_id != -1) { this.inputValue.KetCuoi_ID = data.ketcuoi_id }
          var ds = await this.LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2(Enum.LOAI_DV.TRAM_VT, data.phuong_id, data.pho_id, data.ap_id, data.khu_id, data.dacdiem_id)
          if (ds.length <= 0) {
            ds = await this.LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2(Enum.LOAI_DV.TRAM_VT, data.phuong_id, data.pho_id, data.ap_id, 0, data.dacdiem_id)
            if (ds.length <= 0) {
              ds = await this.LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2(Enum.LOAI_DV.TRAM_VT, data.phuong_id, data.pho_id, 0, 0, data.dacdiem_id)
              if (ds.length <= 0) {
                this.$root.$toast.warning('Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt. Hãy liên hệ với Admin để xử lý!')
                if (this.inputValue.ht_loc_cbo_tram) {
                  let dtControl = await this.getControl()
                  for (let index = 0; index < dtControl.dsComboDonVi.length; index++) {
                    const element = dtControl.dsComboDonVi[index]
                    if (element.loaiDonVi === Enum.LOAI_DV.TRAM_VT || element.loaiDonVi === Enum.LOAI_DV.TRAM_CAP_PORT) {
                      await dtControl.ref.HT_DonVi_TRAMTC_Combobox(element, -7)
                    }
                  }
                  return false
                } else {
                  this.modelCbo.cboDaiVT = -1
                  return false
                }
              }
            }
          }
          if (ds.length === 1) {
            this.modelCbo.cboDaiVT = parseInt(ds[0].donviql_id)
            let dtControl = await this.getControl()
            for (let index = 0; index < dtControl.dsComboDonVi.length; index++) {
              const element = dtControl.dsComboDonVi[index]
              if (element.loaiDonVi === Enum.LOAI_DV.TRAM_VT || element.loaiDonVi === Enum.LOAI_DV.TRAM_CAP_PORT) {
                if (this.inputValue.ht_loc_cbo_tram) {
                  await dtControl.ref.HT_DonVi_TRAMTC_Combobox(element, parseInt(ds[0].donvi_id))
                  dtControl.ref.$refs[element.key][0].value = ds[0].donvi_id
                } else {
                  await dtControl.ref.HT_DonVi_TRAMTC_Combobox2(element, Enum.LOAI_DV.TRAM_VT, parseInt(this.modelCbo.cboDaiVT))
                  dtControl.ref.$refs[element.key][0].value = ds[0].donvi_id
                }
              }
            }
          } else {
            let resolvePromise = await this.$refs.popupChonDonViTC.show(null, null, null, null, [...ds])
            console.log('🚀 ~ file: index.vue:3690 ~ getDiaChiTuFormDiaChi ~ resolvePromise', resolvePromise)
            if (resolvePromise.ok) {
              this.modelCbo.cboDaiVT = resolvePromise.data.donviql_id
              let dtControl = await this.getControl()
              for (let index = 0; index < dtControl.dsComboDonVi.length; index++) {
                let element = dtControl.dsComboDonVi[index]
                if (element.loaiDonVi === Enum.LOAI_DV.TRAM_VT || element.loaiDonVi === Enum.LOAI_DV.TRAM_CAP_PORT) {
                  if (this.inputValue.ht_loc_cbo_tram) {
                    await dtControl.ref.HT_DonVi_TRAMTC_Combobox(element, resolvePromise.data.donviql_id)
                    dtControl.ref.$refs[element.key][0].value = resolvePromise.data.donvi_id
                  } else {
                    await dtControl.ref.HT_DonVi_TRAMTC_Combobox2(element, Enum.LOAI_DV.TRAM_VT, parseInt(this.modelCbo.cboDaiVT))
                    dtControl.ref.$refs[element.key][0].value = resolvePromise.data.donvi_id
                  }
                }
              }
            } else {
              this.modelCbo.cboDaiVT = -1
              let dtControl = await this.getControl()
              for (let index = 0; index < dtControl.dsComboDonVi.length; index++) {
                const element = dtControl.dsComboDonVi[index]
                if (element.loaiDonVi === Enum.LOAI_DV.TRAM_VT || element.loaiDonVi === Enum.LOAI_DV.TRAM_CAP_PORT) {
                  dtControl.ref.$refs[element.key][0].value = -1
                }
              }
              return false
            }
          }
          await this.SP_LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2(data.phuong_id, data.pho_id, data.ap_id, data.khu_id, data.dacdiem_id)
          this.inputValue.tinhld_id = data.tinh_id
          this.inputValue.quanld_id = data.quan_id
          this.inputValue.phuongld_id = data.phuong_id
          this.inputValue.dacdiemld_id = data.dacdiem_id
          this.inputValue.phold_id = data.pho_id
          this.inputValue.apld_id = data.ap_id
          this.inputValue.khuld_id = data.khu_id
          this.inputValue.sonhald = data.sonha
          this.inputValue.Lng_LD = data.longtitude
          this.inputValue.Lat_LD = data.lattitude
          this.modelInput.txtDiaChiLD = data.diachimoi

          this.inputValue.blockld = data.block
          this.inputValue.tangld = data.tang
          this.inputValue.sophongld = data.sophong
          this.inputValue.motadcld = data.motathem
          await this.LayTienTheoKhoanMuc()

          this.inputValue.tinhtb_id = data.tinh_id
          this.inputValue.quantb_id = data.quan_id
          this.inputValue.phuongtb_id = data.phuong_id
          this.inputValue.photb_id = data.pho_id
          this.inputValue.dacdiemtb_id = data.dacdiem_id
          this.inputValue.aptb_id = data.ap_id
          this.inputValue.khutb_id = data.khu_id

          this.inputValue.Lng_TB = data.longtitude
          this.inputValue.Lat_TB = data.lattitude

          this.inputValue.sonhatb = data.sonha
          this.modelInput.txtDiaChiTB = data.diachimoi

          this.inputValue.blocktb = data.block
          this.inputValue.tangtb = data.tang
          this.inputValue.sophongtb = data.sophong
          this.inputValue.motadctb = data.motathem

          await this.HT_NV_KinhDoanh_Theo_DiaBan(this.inputValue.quanld_id, this.inputValue.phuongld_id, this.inputValue.phold_id, this.inputValue.apld_id, this.inputValue.khuld_id,
            parseInt(this.ThongTinKhachHang.cboDonViQL), this.inputValue.dacdiemld_id)
          this.$refs.txtDiaChiLD.focus()
          break
        case 'DiachiTB':
          this.inputValue.tinhtb_id = data.tinh_id
          this.inputValue.quantb_id = data.quan_id
          this.inputValue.phuongtb_id = data.phuong_id
          this.inputValue.photb_id = data.pho_id
          this.inputValue.aptb_id = data.ap_id
          this.inputValue.khutb_id = data.khu_id
          this.inputValue.dacdiemtb_id = data.dacdiem_id
          this.inputValue.Lng_TB = data.longtitude
          this.inputValue.Lat_TB = data.lattitude
          this.inputValue.sonhatb = data.so_nha
          this.modelInput.txtDiaChiTB = data.diachimoi

          this.inputValue.blocktb = data.block
          this.inputValue.tangtb = data.tang
          this.inputValue.sophongtb = data.sophong
          this.inputValue.motadctb = data.motathem

          this.diachi_flag = null
          this.$refs.txtDiaChiTB.focus()
          break
      }
    },
    async HT_DonVi_TRAMTC_Combobox (cbo, DONVI_ID) {
      try {
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/ht_donvi_tramtc_combobox', {DONVI_ID})
        return rs.data
      } catch (error) {
        return []
      }
    },
    async HT_NV_KinhDoanh_Theo_DiaBan (quan_id, phuong_id, pho_id, ap_id, khu_id, donvi_ql_id, dacdiem_id) {
      let ds = await this.HT_KV_CSKH_Theo_DiaBan(quan_id, phuong_id, pho_id, ap_id, khu_id, donvi_ql_id, dacdiem_id)
      console.log('🚀 ~ file: index.vue:4039 ~ HT_NV_KinhDoanh_Theo_DiaBan ~ ds:', ds)
      if (ds !== null && ds.length > 1) {
        this.dulieu_gankhuvuc = {
          hdtb_id: 0,
          donvi_id: donvi_ql_id,
          diachi: {
            'QUAN_ID': quan_id, 'PHUONG_ID': phuong_id, 'PHO_ID': pho_id, 'AP_ID': ap_id, 'KHU_ID': khu_id, 'DACDIEM_ID': dacdiem_id
          }
        }
        this.$bvModal.show('gankhuvuc')
      } else if (ds !== null && ds.length > 0) {
        this.modelInput.txtKhuVuc = ''
        this.modelInput.txtKhuVuc = ds[0]['ten_kv']
        this.inputValue.khuvuc_id = parseInt(ds[0]['khuvuc_id'])
        this.dsHDTB_KV = []
        let dr = {}
        dr.HDTB_ID = this.inputValue.hdtb_id
        dr.KHUVUC_ID = this.inputValue.khuvuc_id
        dr.LOAIKV_ID = Enum.LOAI_KV.KHUVUC_DIABAN
        dr.KIEUKV_ID = 2
        this.dsHDTB_KV.push(dr)
      } else {
        this.modelInput.txtKhuVuc = ''
        this.inputValue.khuvuc_id = 0
        this.dsHDTB_KV = []
      }
    },
    async HT_KV_CSKH_Theo_DiaBan (quan_id, phuong_id, pho_id, ap_id, khu_id, donvi_ql_id, dacdiem_id) {
      try {
        var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/sp_ht_kv_cskh_theo_diaban', {quan_id, phuong_id, pho_id, ap_id, khu_id, donvi_ql_id, dacdiem_id})
        return rs.data
      } catch (error) {
        return []
      }
    },
    async HT_DonVi_TRAMTC_Combobox2 (cbo, LOAIDV_ID, DONVI_QL_ID) {
      try {
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/ht_donvi_tramtc_combobox_2', {LOAIDV_ID, DONVI_QL_ID})
        return rs.data
      } catch (error) {
        return []
      }
    },
    async SP_LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2 (vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id) {
      try {
        let dtControl = await this.getControl()
        for (let index = 0; index < dtControl.dsComboDonVi.length; index++) {
          let e = dtControl.dsComboDonVi[index]
          if (e.loaiDonVi !== Enum.LOAI_DV.TRAM_VT) {
            let dsvt = await this.LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2(parseInt(e.loaiDonVi), vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id)
            if (dsvt.length > 0) {
              let vtid = parseInt(dsvt[0].donvi_id)
              dtControl.ref.$refs[e.key][0].value = vtid
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2579 ~ SP_LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2 ~ error', error)
      }
    },
    async LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2 (vloaidv_id, vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id) {
      try {
        var data = {vloaidv_id, vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id}
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_donvi_theo_loaidv_diachi_ld_v2', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async HienThi_NVQL_AM_DB_HD (i_kieu, id) {
      var ds_tt = await this.Lay_NhanVien_QL(i_kieu, id)
    },
    async Lay_NhanVien_QL (KIEU, ID) {
      try {
        var data = {KIEU, ID}
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_nhanvien_ql', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async LAY_DS_SUDUNG_DVK (db_id, vloaiid) {
      var dset = await this.GET_DS_SUDUNG_DVK(db_id, vloaiid)
      dset = this.UpperCasePropertyList(dset)
      this.dsDK_DVK = []
      for (let i = 0; i < dset.length; i++) {
        let row = {}
        row.DVKHAC_ID = dset[i].DVKHAC_ID
        row.KIEU_YC = 1
        row.TTTH = Enum.TrangThaiCaiDat.MOI
        row.HD_ID = this.inputValue.hdtb_id
        row.NGAY_YC = DateTimeLib.toDateDisplay(this.modelDateTime.dtpNgayYC)
        row.LOAIID = vloaiid
        this.dsDK_DVK.push(row)
      }
      this.modelInput.lvDvkhac = ''
      for (let i = 0; i < this.dsDK_DVK.length; i++) {
        let data = {
          LOAI: 'dichvu_khac',
          KHOA: 'dvkhac_id',
          GIATRI: this.dsDK_DVK[i].DVKHAC_ID
        }
        try {
          let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/thong-tin-chi-tiet', data)
          if (rs.data.length > 0) {
            let str = rs.data[0].TEN_DVKHAC + '  (Đang SD)'
            this.modelInput.lvDvkhac = this.modelInput.lvDvkhac + str + '\n'
          }
        } catch (error) {
          console.log('🚀 ~ file: index.vue ~ line 2066 ~ LAY_DS_SUDUNG_DVK ~ error', error)
        }
      }
    },
    async GET_DS_SUDUNG_DVK (DB_ID, LOAIID) {
      try {
        var data = {DB_ID, LOAIID}
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_sudung_dvk', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async HT_DichVuGT_DB (dset) {
      try {
        let str
        this.modelInput.lvDvgt = ''
        for (var i = 0; i < dset.length; i++) {
          try {
            let dichvugt_id = Number(dset[i].dichvugt_id)
            let data = {
              LOAI: 'dichvu_gt',
              KHOA: 'dichvugt_id',
              GIATRI: dichvugt_id
            }
            try {
              let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/thong-tin-chi-tiet', data)
              if (rs.data.length > 0) {
                str = rs.data[0].TEN_DVGT + ' (Đang SD)'
                this.modelInput.lvDvgt = this.modelInput.lvDvgt + str + '\n'
              }
            } catch (error) {
              console.log('🚀 ~ file: index.vue ~ line 2108 ~ HT_DichVuGT_DB ~ error', error)
            }
          } catch (error) {
            console.log('🚀 ~ file: index.vue ~ line 3527 ~ HT_DichVuGT_DB ~ error', error)
          }
        }
      } catch (error) {

      }
    },
    LAY_DS_SUDUNG_DVGT: async function (THUEBAO_ID) {
      try {
        var data = {THUEBAO_ID}
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_sudung_dvgt', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    LAY_DS_THUEBAO_DICHVU: async function (hdtbId, thueBaoId, loaiTbId, kieuId) {
      let data = {
        hdtbId: hdtbId,
        thueBaoId: thueBaoId,
        loaiTbId: loaiTbId,
        kieuId: kieuId
      }
      let res = await this.$root.context.post(
        'web-hopdong/hopdong/lay_ds_thuebao_dichvu',
        data
      )
      return res.data
    },
    async HT_MucCuocTB_Combobox (MUCUOCTB_ID) {
      try {
        // var data = {MUCUOCTB_ID}
        // var rs = await this.$root.context.post('/web-hopdong/chuyendich/load_muccuoctb', data)
        let data = {
          MUCUOCTB_ID: MUCUOCTB_ID
        }
        console.log(this.inputValue.tenLoaiNhald_id)
        let rs = {}
        // rs = await this.$root.context.post('/web-hopdong/chuyendich/load_muccuoctb_v2', data)
        if (this.inputValue.tenLoaiNhald_id !== null && this.inputValue.tenLoaiNhald_id !== -1) {
          data.vloainha_id = this.inputValue.tenLoaiNhald_id          
        } /*else {
          rs = await this.$root.context.post('/web-hopdong/chuyendich/load_muccuoctb', data)
        }*/
        rs = await this.$root.context.post('/web-hopdong/chuyendich/load_muccuoctb_v2', data)
        return this.UpperCasePropertyList(rs.data)
      } catch (error) {
        return []
      }
    },
    ChuanHoaTen: function (ten) {
      if (ten != null) {
        let convertToArray = ten.toLowerCase().split(' ')
        let result = convertToArray.map(function (val) {
          return val.replace(val.charAt(0), val.charAt(0).toUpperCase())
        })
        return result.join(' ')
      } else {
        return ''
      }
    },
    HienThiTTDiaChi: async function (ds, kieu) {
      try {
        if (ds.length > 0) {
          if (kieu == 1) {
            this.inputValue.diachikh_id = ds[0].DIACHI_ID
            this.inputValue.tinhkh_id = ds[0].TINH_ID
            this.inputValue.quankh_id = ds[0].QUAN_ID
            this.inputValue.phuongkh_id = ds[0].PHUONG_ID
            if (ds[0].PHO_ID != null) {
              this.inputValue.phokh_id = ds[0].PHO_ID
            } else {
              this.inputValue.phokh_id = 0
            }
            if (ds[0].AP_ID != null) {
              this.inputValue.apkh_id = ds[0].AP_ID
            } else {
              this.inputValue.apkh_id = 0
            }
            this.inputValue.sonhakh = this.ChuanHoaTen(ds[0].SONHA)
          }

          if (kieu == 2) {
            this.inputValue.diachitt_id = ds[0].DIACHI_ID
            this.inputValue.tinhtt_id = ds[0].TINH_ID
            this.inputValue.quantt_id = ds[0].QUAN_ID
            this.inputValue.phuongtt_id = ds[0].PHUONG_ID
            if (ds[0].PHO_ID != null) {
              this.inputValue.phott_id = ds[0].PHO_ID
            } else {
              this.inputValue.phott_id = 0
            }
            if (ds[0].ap_id != null) {
              this.inputValue.aptt_id = ds[0].AP_ID
            } else {
              this.inputValue.aptt_id = 0
            }
            this.inputValue.sonhatt = this.ChuanHoaTen(ds[0].SONHA)
          }

          if (kieu == 3) {
            this.inputValue.diachitb_id = ds[0].DIACHI_ID
            this.inputValue.tinhtb_id = ds[0].TINH_ID
            this.inputValue.quantb_id = ds[0].QUAN_ID
            this.inputValue.phuongtb_id = ds[0].PHUONG_ID
            if (ds[0].PHO_ID != null) {
              this.inputValue.photb_id = ds[0].PHO_ID
            } else {
              this.inputValue.photb_id = 0
            }
            if (ds[0].AP_ID != null) {
              this.inputValue.aptb_id = ds[0].AP_ID
            } else {
              this.inputValue.aptb_id = 0
            }

            if (ds[0].KHU_ID != null) {
              this.inputValue.khutb_id = ds[0].KHU_ID
            } else {
              this.inputValue.khutb_id = 0
            }

            if (ds[0].DACDIEM_ID != null) {
              this.inputValue.dacdiemtb_id = ds[0].DACDIEM_ID
            } else {
              this.inputValue.dacdiemtb_id = 0
            }

            if (ds[0].KINHDO != null) {
              this.inputValue.Lng_TB = parseInt(ds[0].KINHDO)
            } else {
              this.inputValue.Lng_TB = 0
            }

            if (ds[0].VIDO != null) {
              this.inputValue.Lat_TB = parseInt(ds[0].VIDO)
            } else {
              this.inputValue.Lat_TB = 0
            }

            this.inputValue.sonhatb = this.ChuanHoaTen(ds[0].SONHA)
            if (
              ds[0].hasOwnProperty('block') ||
              ds[0].hasOwnProperty('BLOCK')
            ) {
              this.inputValue.blocktb = ds[0]['BLOCK']
            }
            if (ds[0].hasOwnProperty('tang') || ds[0].hasOwnProperty('TANG')) {
              this.inputValue.tangtb = ds[0]['TANG']
            }
            if (
              ds[0].hasOwnProperty('phong') ||
              ds[0].hasOwnProperty('PHONG')
            ) {
              this.inputValue.sophongtb = ds[0]['PHONG']
            }
            if (ds[0].hasOwnProperty('mota') || ds[0].hasOwnProperty('MOTA')) {
              this.inputValue.motadctb = ds[0]['MOTA']
            }

            if (ds[0].DIACHILD_ID != null) {
              this.inputValue.diachild_id = ds[0].DIACHILD_ID
            } else {
              this.inputValue.diachild_id = 0
            }

            if (ds[0].TINHLD_ID != null) {
              this.inputValue.tinhld_id = ds[0].TINHLD_ID
            } else {
              this.inputValue.tinhld_id = 0
            }

            if (ds[0].QUANLD_ID != null) {
              this.inputValue.quanld_id = ds[0].QUANLD_ID
            } else {
              this.inputValue.quanld_id = 0
            }

            if (ds[0].PHUONGLD_ID != null) {
              this.inputValue.phuongld_id = ds[0].PHUONGLD_ID
            } else {
              this.inputValue.phuongld_id = 0
            }

            if (ds[0].PHOLD_ID != null) {
              this.inputValue.phold_id = ds[0].PHOLD_ID
            } else {
              this.inputValue.phold_id = 0
            }

            if (ds[0].APLD_ID != null) {
              this.inputValue.apld_id = ds[0].APLD_ID
            } else {
              this.inputValue.apld_id = 0
            }

            if (ds[0].KHULD_ID != null) {
              this.inputValue.khuld_id = ds[0].KHULD_ID
            } else {
              this.inputValue.khuld_id = 0
            }

            if (ds[0].DACDIEMLD_ID != null) {
              this.inputValue.dacdiemld_id = ds[0].DACDIEMLD_ID
            } else {
              this.inputValue.dacdiemld_id = 0
            }

            if (ds[0].KINHDO_LD != null) {
              this.inputValue.Lng_LD = parseInt(ds[0].KINHDO_LD)
            } else {
              this.inputValue.Lng_LD = 0
            }

            if (ds[0].VIDO_LD != null) {
              this.inputValue.Lat_LD = parseInt(ds[0].VIDO_LD)
            } else {
              this.inputValue.Lat_LD = 0
            }

            this.inputValue.sonhald = this.ChuanHoaTen(ds[0].SONHA_LD)

            if (
              ds[0].hasOwnProperty('block_ld') ||
              ds[0].hasOwnProperty('BLOCK_LD')
            ) {
              this.inputValue.block_ld = ds[0]['BLOCK_LD']
            }
            if (
              ds[0].hasOwnProperty('tang_ld') ||
              ds[0].hasOwnProperty('TANG_LD')
            ) {
              this.inputValue.tang_ld = ds[0]['TANG_LD']
            }
            if (
              ds[0].hasOwnProperty('phong_ld') ||
              ds[0].hasOwnProperty('PHONG_LD')
            ) {
              this.inputValue.phong_ld = ds[0]['PHONG_LD']
            }
            if (
              ds[0].hasOwnProperty('mota_ld') ||
              ds[0].hasOwnProperty('MOTA_LD')
            ) {
              this.inputValue.mota_ld = ds[0]['MOTA_LD']
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1042 ~ error', error)
      }
    },
    async LAY_DS_DIACHI_THEO_DBTBID (THUEBAO_ID) {
      try {
        var data = {THUEBAO_ID}
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_diachi_theo_dbtbid', data)
        return this.UpperCasePropertyList(rs.data)
      } catch (error) {
        return []
      }
    },
    async LAY_DS_KHUVUC_THEO_HD_DB (HDTB_ID, INHD_DB) {
      try {
        var data = {HDTB_ID, INHD_DB}
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_khuvuc_theo_hd_db', data)
        return this.UpperCasePropertyList(rs.data)
      } catch (error) {
        return []
      }
    },
    async LAY_DB_THEO_MATB (ma_tb, donvi_id, dichvuvt_id) {
      try {
        var data = {
          MA_TB: this.modelInput.txtMaTB,
          DONVI_DL_ID: donvi_id,
          DICHVUVT_ID: dichvuvt_id
        }
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_db_theo_matb', data)
        return this.UpperCasePropertyList(rs.data)
      } catch (error) {
        return []
      }
    },
    async LAY_DANHBA_THEO_MATB (ma_tb, dichvuvt_id, donvi_id) {
      try {
        var data = {
          MA_TB: this.modelInput.txtMaTB,
          DICHVUVT_ID: dichvuvt_id,
          DONVI_DL_ID: donvi_id
        }
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_danhba_theo_matb', data)
        return this.UpperCasePropertyList(rs.data)
      } catch (error) {
        return []
      }
    },
    HT_DichVuGT: async function () {
      try {
        this.modelInput.lvDvgt = ''
        var str
        var kt = true
        this.inputValue.tien_dv = 0
        this.inputValue.vat_dv = 0
        console.log('🚀 ~ file: index.vue:4441 ~ this.inputValue.m_dsThueBao_DichVu:', this.inputValue.m_dsThueBao_DichVu)
        for (let index = 0; index < this.inputValue.m_dsThueBao_DichVu.length; index++) {
          const element = this.inputValue.m_dsThueBao_DichVu[index]
          if (element.huy.toString() === '1' || element.dangKy.toString() === '1' || element.sua.toString() === '1') {
            str = element.tenDvGt
            if (element.dangKy.toString() === '1' && element.huy.toString() === '0' && element.sua.toString() === '0') {
              kt = false
              if (element.cuocLd !== null) { this.inputValue.tien_dv = this.inputValue.tien_dv + parseInt(element.cuocLd) }
              if (element.vatLd !== null) { this.inputValue.vat_dv = this.inputValue.vat_dv + parseInt(element.vatLd) }
              str = str + ' (ĐK)'
            }
            if (element.huy.toString() === '1') { str = str + ' (Hủy)' }
            if (element.sua.toString() === '1') { str = str + ' (Sửa)' }
            this.modelInput.lvDvgt = this.modelInput.lvDvgt + str + '\n'
          }
        }
        if (kt === true) {
          this.tien_dv = 0
          this.vat_dv = 0
        }
        await this.HienThiTienDichVu(this.tien_dv, this.vat_dv)
      } catch (error) {

      }
    },
    async HienThiTienDichVu (tien_dv, vat_dv) {
      this.inputValue.dtien_dv = tien_dv
      this.inputValue.dvat_dv = vat_dv
      for (let index = 0; index < this.inputValue.ds_tien_khoanmuc.length; index++) {
        const element = this.inputValue.ds_tien_khoanmuc[index]
        if (element.khoanmuctt_id.toString() === Enum.KHOANMUC_TT.KMTT_DICHVU.toString()) {
          this.inputValue.ds_tien_khoanmuc[index].tien = this.inputValue.dtien_dv
          this.inputValue.ds_tien_khoanmuc[index].vat = this.inputValue.dvat_dv
        }
      }
      await this.LayTongTien_HDTB()
    },
    GiaoDien_DichVuVT: async function (dichvuvt_id) {
      try {
        let vm = this
        this.s_display_div = -1
        this.modelCbo.vcboLoaihinhTB = []
        let loaihinh_tb = await this.LAY_LOAIHINH_TB(dichvuvt_id)
        this.modelCbo.vcboLoaihinhTB = loaihinh_tb
        if (loaihinh_tb.length > 0) {
          this.modelCbo.cboLoaihinhTB = loaihinh_tb[0].loaitb_id
        }
        if (!this.inputValue.kt_load) {
          await this.LAY_DS_SUDUNG_DVGT(0)
        }
        if (dichvuvt_id === Enum.DichVuVienThong.CO_DINH) {
          this.s_display_div = 1
          this.modelCbo.cboLoaihinhTB = Enum.LoaiHinhTB.DIENTHOAI_CD
          this.modelCbo.cboKieuLD = Enum.KieuLapDat.DC_DIENTHOAI
          this.inputValue.ltb_adsl_id = 1
        }
        if (dichvuvt_id === Enum.DichVuVienThong.ADSL || dichvuvt_id === Enum.DichVuVienThong.BRCD_DOITAC
        ) {
          this.s_display_div = 2
          this.modelCbo.cboLoaihinhTB = Enum.LoaiHinhTB.MEGA
          // Vue.nextTick(function () {
          //   vm.inputValue.ltb_adsl_id = vm.$refs.refAdsl.cboLoaiTBAdsl.Value
          // })
          if (this.$refs.refAdsl) {
            this.inputValue.ltb_adsl_id = this.$refs.refAdsl.cboLoaiTBAdsl.Value
            console.log('🚀 ~ file: index.vue:4538 ~ this.inputValue.ltb_adsl_id:', this.inputValue.ltb_adsl_id)
          }
          this.modelCbo.cboKieuLD = Enum.KieuLapDat.DC_ADSL_KHONG_SO
        }
        if (dichvuvt_id === Enum.DichVuVienThong.MEGA_EYES) {
          this.s_display_div = 2
          // Vue.nextTick(function () {
          //   vm.inputValue.ltb_adsl_id = vm.$refs.refAdsl.cboLoaiTBAdsl.Value
          // })
          if (this.$refs.refAdsl) {
            this.inputValue.ltb_adsl_id = this.$refs.refAdsl.cboLoaiTBAdsl.Value
            console.log('🚀 ~ file: index.vue:4546 ~ this.inputValue.ltb_adsl_id:', this.inputValue.ltb_adsl_id)
          }
        }
        if (dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || 
            dichvuvt_id === Enum.DichVuVienThong.METRONET || 
            dichvuvt_id === Enum.DichVuVienThong.NOIBO_DADIEM ||
            dichvuvt_id === Enum.DichVuVienThong.MGW_DOITAC) {
          this.s_display_div = 3
          // Vue.nextTick(function () {
          //   vm.inputValue.ltb_adsl_id = vm.$refs.refWan.cboLoaiTBiWan.Value
          // })
          if (this.$refs.refWan) {
            this.inputValue.ltb_adsl_id = this.$refs.refWan.cboLoaiTBiWan.Value
            console.log('🚀 ~ file: index.vue:4559 ~ this.inputValue.ltb_adsl_id:', this.inputValue.ltb_adsl_id)
          }
        }
        if (dichvuvt_id === Enum.DichVuVienThong.TSL || 
          dichvuvt_id === Enum.DichVuVienThong.TSL_DOITAC ||
          dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM
          ) {
          this.s_display_div = 5
          this.inputValue.ltb_adsl_id = 1
        }
        if (dichvuvt_id === Enum.DichVuVienThong.GPHONE) {
          this.s_display_div = 4
          this.inputValue.ltb_adsl_id = 1
        }
        if (dichvuvt_id === Enum.DichVuVienThong.IMS || dichvuvt_id === Enum.DichVuVienThong.IMS_DOITAC) {
          this.s_display_div = 6
          this.inputValue.ltb_adsl_id = 1
        }
        if (dichvuvt_id === Enum.DichVuVienThong.HOINGHI_TRUYENHINH || dichvuvt_id === Enum.DichVuVienThong.ANTOAN_BAOMAT_TT ||
          dichvuvt_id === Enum.DichVuVienThong.DICHVU_CNTT || dichvuvt_id === Enum.DichVuVienThong.TRUNGTAM_DULIEU) {
          this.s_display_div = 7
          this.inputValue.ltb_adsl_id = 1
        }
        // Vue.nextTick(async function () {
        //   await vm.cboLoaihinhTB_SelectedValueChanged()
        // })
        await this.cboLoaihinhTB_SelectedValueChanged()
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 3954 ~ error', error)
      }
    },
    chkDC_NoiBo_CheckedChanged: async function () {
      if (this.inputValue.kt_load == false) {
        let vdc_noibo = 0
        if (this.modelCkeckBox.chkDC_NoiBo) vdc_noibo = 1
        this.inputValue.loaitb_id = parseInt(this.modelCbo.cboLoaihinhTB)
        let dtKieuLD = await this.dsDanhMuc.kieu_ld.filter(
          x =>
            x.loaitb_id.toString() === this.inputValue.loaitb_id.toString() &&
            x.tinhkhac.toString() !== '2' &&
            x.dc_noibo.toString() === vdc_noibo.toString()
        )
        if (dtKieuLD.length > 0) {
          dtKieuLD = dtKieuLD.sort(function (a, b) { return a.thutu - b.thutu })
          this.modelCbo.vcboKieuLD = dtKieuLD
          this.modelCbo.cboKieuLD = this.modelCbo.vcboKieuLD[0].kieuld_id
          await this.cboKieuLD_SelectedValueChanged()
        } else {
          this.modelCbo.vcboKieuLD = []
          this.modelCbo.cboKieuLD = -1
        }
      }
    },
    btnTien_Click: async function () {
      this.$refs.tienKMCuPopupRef.showModal()
    },
    LowerCasePropertyList (obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toLowerCase()
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    UpperCasePropertyList (obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    async TinhTong_Tien_Dich_vu (m_dsThueBao_DichVu) {
      try {
        this.inputValue.tien_dv = 0
        this.inputValue.vat_dv = 0
        for (let index = 0; index < m_dsThueBao_DichVu.length; index++) {
          const e = m_dsThueBao_DichVu[index]
          if (e.dangKy.toString() === '1' && e.huy.toString() === '0') {
            this.inputValue.tien_dv = this.inputValue.tien_dv + parseFloat(e.cuocLd)
            this.inputValue.vat_dv = this.inputValue.vat_dv + parseFloat(e.vatLd)
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 4189 ~ TinhTong_Tien_Dich_vu ~ error', error)
      }
    },
    async ThemHDThueBao () {
      try {
        this.inputValue.kt_adsl = true
        let KiemTraDL_ThueBao = await this.KiemTraDL_ThueBao()
        if (!KiemTraDL_ThueBao) return
        await this.KiemTraDuLieuNhap()
        let KIEMTRA_MATB_KHOSO = await this.KIEMTRA_MATB_KHOSO(this.modelInput.txtMaTB, this.inputValue.dichvuvt_id)
        if (!KIEMTRA_MATB_KHOSO) {
          this.$root.$toast.error(
            'Số máy chưa có trong kho số !'
          )
          this.$refs.ma_tb.focus()
          return false
        }
        let KIEMTRA_HDTB_KHI_THEMMOI = await this.KIEMTRA_HDTB_KHI_THEMMOI(this.modelInput.txtMaTB, this.inputValue.hdkh_id, this.inputValue.dichvuvt_id, 0)
        if (KIEMTRA_HDTB_KHI_THEMMOI) {
          this.$root.$toast.error('Thuê bao đã tồn tại trong hợp đồng, không thêm mới được.')
          return false
        }

        if (this.modelCbo.cboKieuLD.toString() === Enum.KieuLapDat.STADSL_DOI_TOCDO.toString()) {
          let KIEMTRA_DB_KHI_THEMMOI = await this.KIEMTRA_DB_KHI_THEMMOI(this.modelInput.txtMaTB, 0, this.inputValue.thuebao_id, this.inputValue.dichvuvt_id)
          if (!KIEMTRA_DB_KHI_THEMMOI) {
            this.$root.$toast.error('Thuê bao không tồn tại trong danh bạ, không cập nhật được')
            return false
          }
        } else {
          let KIEMTRA_DB_KHI_THEMMOI = await this.KIEMTRA_DB_KHI_THEMMOI(this.modelInput.txtMaTB, 0, this.inputValue.thuebao_id, this.inputValue.dichvuvt_id)
          if (!KIEMTRA_DB_KHI_THEMMOI) {
            this.$root.$toast.error('Thuê bao không tồn tại trong danh bạ, không cập nhật được')
            return false
          }
        }

        let KIEMTRA_TB_CUNG_MAKH_TB_PL = await this.KIEMTRA_TB_CUNG_MAKH_TB_PL(this.modelInput.txtMaGD, this.inputValue.thuebao_id)
        if (!KIEMTRA_TB_CUNG_MAKH_TB_PL) {
          this.$root.$toast.error('Thuê bao không cùng mã khách hàng với thuê bao đang thực hiện chuyển dịch !')
          return false
        }

        let KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.modelInput.txtMaTB, 0, this.modelCbo.cboDichVuVT, Enum.LoaiHopDong.DI_CHUYEN)
        if (KIEMTRA_MA_TB_LAP_HOPDONG_KHAC) return false

        this.inputValue.tocdotn_id = 0
        this.inputValue.muccuoctn_id = 0

        if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.ADSL || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.BRCD_DOITAC) {
          this.inputValue.tocdotn_id = parseInt(this.$refs.refAdsl.cboGoiCuocAdsl.Value)
          if (this.inputValue.loaitb_id === Enum.LoaiHinhTB.INTERNET_ADSL 
            || this.inputValue.loaitb_id === Enum.LoaiHinhTB.INTERNET_FTTH 
            || this.inputValue.loaitb_id === Enum.LoaiHinhTB.WIFI_FIBER) {
            this.inputValue.muccuoctn_id = parseInt(this.$refs.refAdsl.cboMucCuoc.Value)
          }
        } else if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MEGA_EYES) {
          this.inputValue.tocdotn_id = parseInt(this.$refs.refAdsl.cboGoiCuocAdsl.Value)
        } else if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TSL || 
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TSL_DOITAC ||
          this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM
          ) {
          this.inputValue.tocdotn_id = parseInt(this.$refs.refTSL.cboTocDoKenhTSL.Value)
        } else if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN ||
                                            this.inputValue.dichvuvt_id === Enum.DichVuVienThong.METRONET ||
                                            this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_DADIEM ||
                                            this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MGW_DOITAC) {
          this.inputValue.tocdotn_id = parseInt(this.$refs.refWan.cboTocDo.Value)
        }

        var dset = await this.LayHuongGiao_TiepNhan(Enum.LoaiHopDong.DI_CHUYEN, this.modelCbo.cboLoaihinhTB, this.inputValue.tocdotn_id, this.inputValue.muccuoctn_id, this.modelCbo.cboKieuLD)
        if (dset.length === 0) {
          this.$root.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
          return false
        }
        let huonggiao_tn = parseInt(dset[0].huonggiao_id)
        this.inputValue.quytrinh_id = parseInt(dset[0].quytrinh_id)
        let kt_diachitt = 3
        console.log('🚀 ~ file: index.vue:4707 ~ ThemHDThueBao ~ this.inputValue.hdkh_id:', this.inputValue.hdkh_id)
        if (this.inputValue.hdkh_id !== 0) {
          let _ds = await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.inputValue.hdkh_id)
          console.log('🚀 ~ file: index.vue:4708 ~ ThemHDThueBao ~ _ds:', _ds)
          console.log('🚀 ~ file: index.vue:4712 ~ ThemHDThueBao ~ this.ThongTinKhachHang.thanhtoan_id:', this.ThongTinKhachHang.thanhtoan_id)
          if (_ds.length > 0) {
            let _q = _ds.filter(e => e.thanhtoan_id.toString() === this.ThongTinKhachHang.thanhtoan_id.toString())
            console.log('🚀 ~ file: index.vue:4711 ~ ThemHDThueBao ~ _q:', _q)
            if (_q.length > 0) {
              this.inputValue.hdtt_id = _q[0].hdtt_id
            } else {
              await this.ThongTinKhachHang.TaoDuLieu_HDTT(true)
              this.inputValue.hdtt_id = this.ThongTinKhachHang.hdtt_id
              console.log('🚀 ~ file: index.vue:4717 ~ ThemHDThueBao ~ this.inputValue.hdtt_id:', this.inputValue.hdtt_id)
              kt_diachitt = 5
            }
          } else {
            this.$root.$toast.error('Không tìm thấy thông tin hợp đồng khách hàng')
            return false
          }
        } else {
          await this.ThongTinKhachHang.TaoDuLieu_HDTT(true)
          this.inputValue.hdtt_id = this.ThongTinKhachHang.hdtt_id
          kt_diachitt = 5
        }
        await this.TaoDuLieu_HDTB(true)
        await this.TaoDuLieu_CT_TIENHD(true)
        await this.TaoDuLieu_DKDV()
        await this.CapNhat_HDTB_ID_HDTB_KV()

        await this.ThongTinKhachHang.TaoDuLieu_DiaChi(true, kt_diachitt)
        this.dsDiaChi = this.ThongTinKhachHang.getDsDIACHI()
        this.dsHDTT = this.ThongTinKhachHang.getDsHDTT()

        await this.TaoDuLieu_DiaChi(true, kt_diachitt)

        this.dsHDTB_CD = []
        this.dsHDTB_ADSL = []
        this.dsHDTB_TSL = []
        this.dsHDTB_MEWAN = []
        this.dsHDTB_GP = []
        this.dsHDTB_IMS = []
        this.dsHDTBDV = []
        this.dsHDTB_CNTT = []
        switch (this.inputValue.dichvuvt_id) {
          case Enum.DichVuVienThong.CO_DINH:
            await this.TaoDuLieu_HDTB_CD(true)
            break
          case Enum.DichVuVienThong.IMS:
            await this.TaoDuLieu_HDTB_IMS(true)
            break
          case Enum.DichVuVienThong.ADSL:
            await this.TaoDuLieu_HDTB_ADSL(true)
            break
          case Enum.DichVuVienThong.MEGA_EYES:
            await this.TaoDuLieu_HDTB_ADSL(true)
            break
          case Enum.DichVuVienThong.MEGAWAN:
          case Enum.DichVuVienThong.NOIBO_DADIEM:
          case Enum.DichVuVienThong.METRONET:
            await this.TaoDuLieu_HDTB_MGWAN(true)
            break
          case Enum.DichVuVienThong.TSL:
          case Enum.DichVuVienThong.NOIBO_2DIEM:
            await this.TaoDuLieu_HDTB_TSL(true)
            break
          case Enum.DichVuVienThong.GPHONE:
            await this.TaoDuLieu_HDTB_GP(true)
            break
          case Enum.DichVuVienThong.ANTOAN_BAOMAT_TT:
          case Enum.DichVuVienThong.DICHVU_CNTT:
          case Enum.DichVuVienThong.TRUNGTAM_DULIEU:
          case Enum.DichVuVienThong.HOINGHI_TRUYENHINH:
            await this.TaoDuLieu_HDTB_CNTT(true)
            this.dsHDTBDV = await this.$refs.refCNTT.TaoChoBang_HDTB_DV()
            for (const element of this.dsHDTBDV) {
              element.HDTB_ID = this.inputValue.hdtb_id
            }
            break
          default:
            await this.TaoDuLieu_HDTB_CD(true)
            break
        }
        if (this.inputValue.kt_adsl == false) { return }
        if (kt_diachitt == 3) {
          await this.ThemMoi_HopDongTB_V3(this.inputValue.hdtb_id, this.dsHDTB, this.dsHDTBDV, this.dsHDTB_CD, this.dsHDTB_ADSL, this.dsHDTB_TSL, this.dsDiaChi, this.dsDiaChiHDTB, this.dsDK_DVGT,
            this.dsDK_DVK, this.dsHDTB_MEWAN, this.dsHDTB_GP, this.dsTTTT, this.dsCTTHD, [], this.dsHDTB_IMS, huonggiao_tn,
            this.dsHDTB_KV, this.dsDiaChiHDTT, this.dsKM_HDTB, this.dsHDTB_CNTT)
        } else {
          await this.ThemMoi_HopDongTB_V3(this.inputValue.hdtb_id, this.dsHDTB, this.dsHDTBDV, this.dsHDTB_CD, this.dsHDTB_ADSL, this.dsHDTB_TSL, this.dsDiaChi, this.dsDiaChiHDTB, this.dsDK_DVGT,
            this.dsDK_DVK, this.dsHDTB_MEWAN, this.dsHDTB_GP, this.dsTTTT, this.dsCTTHD, this.dsHDTT, this.dsHDTB_IMS, huonggiao_tn,
            this.dsHDTB_KV, this.dsDiaChiHDTT, this.dsKM_HDTB, this.dsHDTB_CNTT)
        }
        if (this.modelInput.txtMaTBMoi !== '') {
          await this.CAPNHAT_KHOSO(this.modelInput.txtMaTBMoi, '', this.inputValue.dichvuvt_id)
        } else {
          await this.CAPNHAT_KHOSO(this.modelInput.txtMaTB, '', this.inputValue.dichvuvt_id)
        }
        this.inputValue.ma_tb = this.modelInput.txtMaTB
        this.$root.$toast.success('Thêm thuê bao thành công!')
        await this.Them_MyTV_vao_goi_giadinh()
        if (this.inputValue.tudong_ganthem_hdtb_dv) { await this.tudong_ganthem_hdtb_dv(this.inputValue.hdtb_id) }
        await this.HienThiDS_HDTT_CBO(this.inputValue.hdkh_id)
        let LAY_DS_HOPDONG_TB_THEO_HDKH_ID = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.inputValue.hdkh_id)
        await this.HienThiDanhSacHDTB(LAY_DS_HOPDONG_TB_THEO_HDKH_ID)
        this.inputValue.ma_tb = this.modelInput.txtMaTB
        let _a = this.modelInput.lvwThueBao.filter(e => e.hdtb_id.toString() === this.inputValue.hdtb_id)
        if (_a.length > 0) {

        }
        this.inputValue.kieuld_ds = this.modelCbo.cboKieuLD
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 4601 ~ ThemHDThueBao ~ error', error)
        this.$root.$toast.error(error)
      }
    },
    async DichChuyen_ThemMoi_HopDongTB_2 (HDTB_ID, dsHDTB, dsHDTBDV, dsHDTB_CD, dsHDTB_ADSL, dsHDTB_TSL, dsDiaChi, dsDiaChiHDTB, dsDK_DVGT,
      dsDK_DVK, dsHDTB_MEWAN, dsHDTB_GP, dsTTTT, dsCTTHD, dsHDTT, dsHDTB_IMS, huonggiao_tn,
      dsHDTB_KV, dsDiaChiHDTT, dsKM_HDTB) {
      let data = {
        P_HDTB_ID: HDTB_ID,
        P_DSHDTB: JSON.stringify(dsHDTB),
        P_DSHDTBDV: JSON.stringify(dsHDTBDV),
        P_DSHDTB_CD: JSON.stringify(dsHDTB_CD),
        P_DSHDTB_ADSL: JSON.stringify(dsHDTB_ADSL),
        P_DSHDTB_TSL: JSON.stringify(dsHDTB_TSL),
        P_DSDIACHI: JSON.stringify(dsDiaChi),
        P_DSDIACHIHDTB: JSON.stringify(dsDiaChiHDTB),
        P_DSDK_DVGT: JSON.stringify(dsDK_DVGT),
        P_DSDK_DVK: JSON.stringify(dsDK_DVK),
        P_DSHDTB_MEWAN: JSON.stringify(dsHDTB_MEWAN),
        P_DSHDTB_GP: JSON.stringify(dsHDTB_GP),
        P_DSTTTT: JSON.stringify(dsTTTT),
        P_DSCTTHD: JSON.stringify(dsCTTHD),
        P_DSHDTT: JSON.stringify(dsHDTT),
        P_DSHDTB_IMS: JSON.stringify(dsHDTB_IMS),
        P_HUONGGIAO_ID: huonggiao_tn,
        P_DSHDTB_KV: JSON.stringify(dsHDTB_KV),
        P_DSDIACHIHDTT: JSON.stringify(dsDiaChiHDTT),
        P_DSKM_HDTB: JSON.stringify(dsKM_HDTB)
      }
      let res = await this.$root.context.post('/web-hopdong/chuyendich/themmoi_hopdongtb', data)
      return res.data
    },
    async ThemMoi_HopDongTB_V3 (HDTB_ID, dsHDTB, dsHDTBDV, dsHDTB_CD, dsHDTB_ADSL, dsHDTB_TSL, dsDiaChi, dsDiaChiHDTB, dsDK_DVGT,
      dsDK_DVK, dsHDTB_MEWAN, dsHDTB_GP, dsTTTT, dsCTTHD, dsHDTT, dsHDTB_IMS, huonggiao_tn,
      dsHDTB_KV, dsDiaChiHDTT, dsKM_HDTB, dsHDTB_CNTT) {
      let dsCNTT = await this.generateInsert(this.inputValue.hdtb_id, dsHDTB_CNTT)
      let data = {
        P_HDTB_ID: HDTB_ID,
        P_DSHDTB: JSON.stringify(dsHDTB),
        P_DSHDTBDV: JSON.stringify(dsHDTBDV),
        P_DSHDTB_CD: JSON.stringify(dsHDTB_CD),
        P_DSHDTB_ADSL: JSON.stringify(dsHDTB_ADSL),
        P_DSHDTB_TSL: JSON.stringify(dsHDTB_TSL),
        P_DSDIACHI: JSON.stringify(dsDiaChi),
        P_DSDIACHIHDTB: JSON.stringify(dsDiaChiHDTB),
        P_DSDK_DVGT: JSON.stringify(dsDK_DVGT),
        P_DSDK_DVK: JSON.stringify(dsDK_DVK),
        P_DSHDTB_MEWAN: JSON.stringify(dsHDTB_MEWAN),
        P_DSHDTB_GP: JSON.stringify(dsHDTB_GP),
        P_DSTTTT: JSON.stringify(dsTTTT),
        P_DSCTTHD: JSON.stringify(dsCTTHD),
        P_DSHDTT: JSON.stringify(dsHDTT),
        P_DSHDTB_IMS: JSON.stringify(dsHDTB_IMS),
        P_HUONGGIAO_ID: huonggiao_tn,
        P_DSHDTB_KV: JSON.stringify(dsHDTB_KV),
        P_DSDIACHIHDTT: JSON.stringify(dsDiaChiHDTT),
        P_DSKM_HDTB: JSON.stringify(dsKM_HDTB),
        P_DSHDTB_CNTT: JSON.stringify(dsCNTT)
      }
      let res = await this.$root.context.post('/web-hopdong/chuyendich/themmoi_hopdongtb_v3', data)
      return res.data
    },
    LAY_DS_HOPDONG_TT_THEO_HDKH_ID: async function (hdkh_id) {
      let res = await this.$root.context.get('/web-hopdong/chuyendich/lay_ds_hopdong_tt_theo_hdkh_id', {hdkh_id: hdkh_id})
      return res.data
    },
    async popupClosed (val) {
      this.loading(true)
      try {
        switch (this.popupComponentName) {
          case 'popupChonKhuVuc':
            if (!val) return
            if (val.khuvuc_id != 0) {
              if (this.inputValue.quanld_id != 0 && this.inputValue.phuongld_id != 0) {
                this.inputValue.khuvuc_id = parseInt(val.khuvuc_id)
                this.dsHDTB_KV = val.hdtb_kv_data
                this.modelInput.txtKhuVuc = val.ma_kv
              }
            } else {
              this.$root.$toast.warning('Chưa có thông tin về địa chỉ lắp đặt. Bạn hãy kiểm tra lại!')
              this.modelInput.txtKhuVuc = ''
              this.inputValue.khuvuc_id = 0
              this.dsHDTB_KV = []
            }
            break
          case 'popupTKTBChuyenDich':
            if (!val) return
            if (val.dichvuvt_id === Enum.DichVuVienThong.GPHONE || val.dichvuvt_id == Enum.DichVuVienThong.DI_DONG) {
              this.$root.$toast.error('Không thực hiện dịch chuyển đối với thuê bao Gphone, Di động!')
              return
            }
            this.modelInput.txtMaTB = val.MaThueBao
            this.modelInput.txtMaTBMoi = ''
            if (val.dichvuvt_id != 0) {
              this.modelCbo.cboDichVuVT = val.dichvuvt_id
              await this.cboDichVuVT_SelectedValueChanged()
              await this.txtMaTB_KeyPress()
            }
            this.$refs.ma_tb.focus()
            break
          case 'popupChonSM':
            if (!val) return
            this.modelInput.txtMaTBMoi = val.somay
            break
          case 'popupServiceAdds':
            if (!val) return
            this.inputValue.m_dsThueBao_DichVu = val
            await this.HT_DichVuGT()
            await this.TinhTong_Tien_Dich_vu(this.inputValue.m_dsThueBao_DichVu)
            await this.HienThiTienDichVu(this.inputValue.tien_dv, this.inputValue.vat_dv)
            break
        }
      } catch (ex) { this.$toast.error('' + ex) } finally {
        this.ClosePopup('popupComponents')
        this.loading(false)
      }
    },
    txtDiaChiLD_KeyPress () {
      this.DiachiLD_Click()
    },
    txtDiaChiTB_KeyPress () {
      this.btnDiaChiTB_Click()
    },
    txtTenTB_Leave () {
      this.modelInput.txtTenTB = this.ChuanHoaTen(this.modelInput.txtTenTB)
    },
    btnThueSo_Click: async function () {
      if (this.modelInput.txtMaTBMoi === '') {
        this.$root.$toast.error('Bạn chưa chọn số mới!')
        return false
      }
      if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.CO_DINH) {
        alert('Tham chieu den frmChuyenDich_ThueSo')
      }
    },
    async btnTKMaTB_Click () {
      if (this.modelInput.txtMaTB !== '') {
        this.popupComponent = () => import('./Popups/popupTKTBChuyenDich')
        this.popupComponentName = 'popupTKTBChuyenDich'
        if ((this.inputValue.khachhang_id_cu) !== 0) {
          this.popupComponentData = {
            khachhang_id: this.inputValue.khachhang_id_cu,
            ma_gd: this.modelInput.txtMaGD
          }
        } else {
          var ds_khcu = await this.LAY_BIENDONG_THEO_HDTB_ID(this.inputValue.hdtb_id)
          if (ds_khcu.length > 0) {
            this.inputValue.khachhang_id_cu = parseInt(ds_khcu[0].KHACHHANG_ID)
            this.popupComponentData = {
              khachhang_id: this.inputValue.khachhang_id_cu,
              ma_gd: this.modelInput.txtMaGD
            }
          } else {
            this.$root.$toast.error('Không có thông tin về thuê bao: ' + this.modelInput.txtMaTB + '\nKiểm tra lại mã thuê bao')
            this.$refs.ma_tb.focus()
            return false
          }
        }
        this.Popup('popupComponents')
      } else {
        this.$root.$toast.error('Bạn chưa nhập mã thuê bao!!!')
      }
    },
    async btnChonSM_Click () {
      if (this.inputValue.dichvuvt_id == Enum.DichVuVienThong.CO_DINH) {
        var result = true
        if (result) {
          if (this.inputValue.quanld_id == 0 || this.inputValue.phuongld_id == 0) {
            this.$root.$toast.error('Bạn chưa chọn địa chỉ lắp đặt !')
            return
          }
          this.popupComponent = () => import('./Popups/popupChonSM')
          this.popupComponentName = 'popupChonSM'
          this.popupComponentData = {
            _quan_id: this.inputValue.quanld_id,
            _phuong_id: this.inputValue.phuongld_id,
            _loaiso_id: Enum.LOAISO.THUONG,
            _thueso: false,
            loaitb_id: this.inputValue.loaitb_id,
            dichvuvt_id: this.inputValue.dichvuvt_id,
            hdkh_id: this.inputValue.hdkh_id,
            donvi_ql_id: this.modelCbo.cboDaiVT
          }
          this.Popup('popupComponents')
        } else {
          this.modelInput.txtMaTBMoi = ''
        }
      } else {
        this.$root.$toast.warning('Bạn chỉ được đổi số cho thuê bao Cố định hoặc IMS')
      }
    },
    async btnDiachiLD_Click () {
      try {
        this.DiachiLD_Click()
      } catch (error) {

      }
    },
    DiachiLD_Click () {
      // this.showThongTinThem = true
      // this.diachi = {}
      // this.diachi.tinh_id = this.inputValue.tinhld_id
      // this.diachi.quan_id = this.inputValue.quanld_id
      // this.diachi.phuong_id = this.inputValue.phuongld_id
      // this.diachi.pho_id = this.inputValue.phold_id
      // this.diachi.ap_id = this.inputValue.apld_id
      // this.diachi.khu_id = this.inputValue.khuld_id
      // this.diachi.dacdiem_id = this.inputValue.dacdiemld_id
      // this.diachi.so_nha = this.inputValue.sonhald
      // this.diachi.diachi = this.modelInput.txtDiaChiLD

      // this.diachi.longtitude = this.inputValue.Lng_LD
      // this.diachi.lattitude = this.inputValue.Lat_LD
      // this.diachi.block = this.inputValue.blockld
      // this.diachi.tang = this.inputValue.tangld
      // this.diachi.sophong = this.inputValue.sophongld
      // this.diachi.motathem = this.inputValue.motadcld

      // this.diachi_flag = 'DiachiLD'
      // this.$bvModal.show('popupDiaChi_TB')
      this.diachiLD = {
        DIACHI: this.modelInput.txtDiaChiLD,
        TINH_ID: this.inputValue.tinhld_id,
        QUAN_ID: this.inputValue.quanld_id,
        PHUONG_ID: this.inputValue.phuongld_id,
        PHO_ID: this.inputValue.phold_id,
        AP_ID: this.inputValue.apld_id,
        KHU_ID: this.inputValue.khuld_id,
        DACDIEM_ID: this.inputValue.dacdiemld_id,
        SO_NHA: this.inputValue.sonhald,
        Lng: this.inputValue.Lng_LD,
        Lat: this.inputValue.Lat_LD,
        TEN_LOAINHA: this.diachiLD.TEN_LOAINHA,
        BLOCK: this.diachiLD.BLOCK,
        TANG: this.diachiLD.TANG,
        PHONG: this.diachiLD.PHONG,
        NHOM_LOAINHA_ID: this.diachiLD.NHOM_LOAINHA_ID,
        DIEN_TICH: this.diachiLD.DIEN_TICH,
        SO_TANG: this.diachiLD.SO_TANG,
        SO_PHONG: this.diachiLD.SO_PHONG,
        MOTA: this.inputValue.motadcld,        
        F_TOANHA: this.$parent.f_nhap_loainha 
      }
      if (this.inputValue.batbuoc_chon_dacdiem_dcld) {
        if (Number(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.DI_DONG && Number(this.modelCbo.cboLoaihinhTB) == Enum.LoaiHinhTB.DIDONGTRATRUOC) { this.diachiLD.phaichondacdiem = false } else { this.diachiLD.phaichondacdiem = true }
      }
      this.$bvModal.show('popupDiaChiLD')
    },
    DiachiTB_Click () {
      // this.showThongTinThem = true
      // this.diachi = {}
      // this.diachi.tinh_id = this.inputValue.tinhtb_id
      // this.diachi.quan_id = this.inputValue.quantb_id
      // this.diachi.phuong_id = this.inputValue.phuongtb_id
      // this.diachi.pho_id = this.inputValue.photb_id
      // this.diachi.ap_id = this.inputValue.aptb_id
      // this.diachi.khu_id = this.inputValue.khutb_id
      // this.diachi.dacdiem_id = this.inputValue.dacdiemtb_id
      // this.diachi.so_nha = this.inputValue.sonhatb
      // this.diachi.diachi = this.modelInput.txtDiaChiTB

      // this.diachi.longtitude = this.inputValue.Lng_TB
      // this.diachi.lattitude = this.inputValue.Lat_TB
      // this.diachi.block = this.inputValue.blocktb
      // this.diachi.tang = this.inputValue.tangtb
      // this.diachi.sophong = this.inputValue.sophongtb
      // this.diachi.motathem = this.inputValue.motadctb
      // this.diachi_flag = 'DiachiTB'
      // this.$bvModal.show('popupDiaChi_TB')
      try {
        this.diachiTB = {
          DIACHI: this.modelInput.txtDiaChiTB,
          TINH_ID: this.inputValue.tinhtb_id,
          QUAN_ID: this.inputValue.quantb_id,
          PHUONG_ID: this.inputValue.phuongtb_id,
          PHO_ID: this.inputValue.photb_id,
          AP_ID: this.inputValue.aptb_id,
          KHU_ID: this.inputValue.khutb_id,
          DACDIEM_ID: this.inputValue.dacdiemtb_id,
          SO_NHA: this.inputValue.sonhatb,
          Lng: this.inputValue.Lng_TB,
          Lat: this.inputValue.Lat_TB,
          BLOCK: this.inputValue.blocktb,
          TANG: this.inputValue.tangtb,
          PHONG: this.inputValue.sophongtb,
          MOTA: this.inputValue.motadctb
        }
        console.log('🚀 ~ file: index.vue:5046 ~ DiachiTB_Click ~ this.diachiTB', this.diachiTB)
        this.$bvModal.show('popupDiaChiTB')
      } catch (error) {
        console.log('🚀 ~ file: index.vue:5064 ~ DiachiTB_Click ~ error', error)
      }
    },
    async btnDiaChiTB_Click () {
      this.DiachiTB_Click()
    },
    btnChon_KhuyenMai_Click: async function () {
    },
    btnMaNVKD_Click: async function () {
      this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao')
      this.popupComponentName = 'popupChonKhuVuc'
      let diachi = {}
      diachi.QUAN_ID = this.inputValue.quanld_id
      diachi.PHUONG_ID = this.inputValue.phuongld_id
      diachi.PHO_ID = this.inputValue.phold_id
      diachi.AP_ID = this.inputValue.apld_id
      diachi.KHU_ID = this.inputValue.khuld_id
      diachi.DACDIEM_ID = this.inputValue.dacdiemld_id
      this.popupComponentData = {diachi: diachi, khuvuc_id: this.inputValue.khuvuc_id, donvi_id: Number(this.ThongTinKhachHang.cboDonViQL), hdtb_id: 0, thuebao_id: 0}
      this.$bvModal.show('popupComponents')
    },
    chkKhuyenMai_CheckedChanged: async function () {
      console.log('🚀 ~ file: index.vue ~ line 3683 ~ modelCkeckBox.chkKhuyenMai', this.modelCkeckBox.chkKhuyenMai)
    },
    chkCongVan_CheckedChanged: async function () {},
    lvwThueBao_SelectedIndexChanged: async function (row) {
      try {
        if (this.modelInput.lvwThueBao.length > 0 && row) {
          this.inputValue.hdtb_id = Number(row.hdtb_id)
          await this.HienThiTTHopDongTB(await this.LAY_DS_HOPDONG_TB_THEO_HDTB_ID(this.inputValue.hdtb_id))
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 4544 ~ error', error)
      }
    },
    btnChiTietKM_Click: async function () {
      try {
        if (this.modelCkeckBox.chkKhuyenMai) {
          if (this.modelCbo.vcboKhuyenMai.length > 0) {
            var vmuccuoctn_id = 0
            var vtocdotn_id = 0
            if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.ADSL || this.inputValue.dichvuvt_id === Enum.DichVuVienThong.BRCD_DOITAC) {
              vtocdotn_id = parseInt(this.$refs.refAdsl.cboGoiCuocAdsl.Value)
              if (parseInt(this.modelCbo.cboLoaihinhTB) === Enum.LoaiHinhTB.INTERNET_ADSL || parseInt(this.modelCbo.cboLoaihinhTB) === Enum.LoaiHinhTB.INTERNET_FTTH ||
                                 parseInt(this.modelCbo.cboLoaihinhTB) === Enum.LoaiHinhTB.WIFI_FIBER) {
                vmuccuoctn_id = parseInt(this.modelCbo.cboMucCuoc)
              }
            } else if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || 
                      this.inputValue.dichvuvt_id === Enum.DichVuVienThong.METRONET || 
                      this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_DADIEM ||
                      this.inputValue.dichvuvt_id === Enum.DichVuVienThong.MGW_DOITAC) {
              vtocdotn_id = parseInt(this.$refs.refWan.cboTocDo.Value)
            } else if (this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TSL || 
              this.inputValue.dichvuvt_id === Enum.DichVuVienThong.TSL_DOITAC ||
              this.inputValue.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM
              ) {
              vtocdotn_id = parseInt(this.$refs.refTSL.cboTocDoKenhTSL.Value)
            }
            var khuyenmai_id = parseInt(this.modelCbo.cboKhuyenMai)
            var KIEMTRA_THOIHAN_KM = await this.KIEMTRA_THOIHAN_KM(khuyenmai_id.toString())
            if (KIEMTRA_THOIHAN_KM === 0) {
              var ngay_kt_km = await this.MAP_ID('NGAY_KT', 'CSS', 'KHUYENMAI', 'KHUYENMAI_ID', khuyenmai_id)
              try {
                var confirm = await this.$confirm('Khuyến mại ' + this.modelCbo.cboKhuyenMai + ' đã hết thời hạn từ ngày ' + ngay_kt_km + '.\nBạn có muốn tiếp tục chọn chi tiết khuyến mại?', 'Thông báo',
                  {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không',
                    type: 'warning'
                  })
              } catch (error) {
                this.inputValue.dtienkmld = 0
                this.inputValue.dvatkmld = 0
                this.inputValue.tyle_kmld = 0
                this.inputValue.tong_kmld = 0
                await this.HienThiTienKMLapDat()
                return false
              }
            }
          }
        }
      } catch (error) {

      }
    },
    async HienThiTienKMLapDat () {
      for (var i = 0; i < this.inputValue.ds_tien_khoanmuc.length; i++) {
        if (this.inputValue.ds_tien_khoanmuc.khoanmuctt_id.toString() === Enum.KHOANMUC_TT.KMTT_KM_LAPDAT.toString()) {
          this.inputValue.ds_tien_khoanmuc.tien = Math.abs(dtienkmld) * -1
          this.inputValue.ds_tien_khoanmuc.vat = Math.abs(dvatkmld) * -1
        }
      }
      await this.LayTongTien_HDTB()
    },
    async KIEMTRA_THOIHAN_KM (vkhuyenmai_id) {
      try {
        let ngay_km = await this.getNgay_Cn()
        let data = {vkhuyenmai_id, vngay_yc: DateTimeLib.toDateDisplayWithFormat(ngay_km, 'DD/MM/YYYY')}
        let res = await this.$root.context.post('/web-hopdong/lapdatmoi/kiemtra_thoihan_km', data)
        return Number(res.data)
      } catch (error) {
        return null
      }
    },
    Clear: async function () {
      this.inputValue.Lat_TB = 0
      this.inputValue.Lng_TB = 0
      this.inputValue.Lat_LD = 0
      this.inputValue.Lng_LD = 0
      this.inputValue.Lat_TT = 0
      this.inputValue.Lng_TT = 0
      this.inputValue.Lat_BC = 0
      this.inputValue.Lng_BC = 0
      this.inputValue.Lat_KH = 0
      this.inputValue.Lng_KH = 0
      this.inputValue.Lat_DD = 0
      this.inputValue.Lng_DD = 0
      this.inputValue.Lat_DC = 0
      this.inputValue.Lng_DC = 0
      this.modelCbo.chkDoiTac_ADSL = false
      this.modelCbo.cboDoiTac_ADSL = -1

      this.inputValue.pt_doanhthu = 0
      this.inputValue.pt_diem_tn = 0

      this.inputValue.kt_kenhphu_ao = false
      this.modelInput.txtCongVan = ''
      this.modelCkeckBox.chkCongVan = false

      this.modelCkeckBox.chkDC_Dau = false
      this.modelCkeckBox.chkDC_Cuoi = false

      this.modelInput.txtSite_ID = ''
      this.modelInput.txtMaHD_TTKD_MGWAN = ''
      this.modelInput.txtMaHD_TTKD_TSL = ''

      this.modelCkeckBox.chkDoiTacWan = false
      this.modelInput.txtCuocTK = '0'
      this.modelInput.txtCuocTC = '0'
      this.modelInput.txtCuocHTWan = '0'
      this.modelInput.txtCuocTBiWan = '0'
      this.modelInput.txtCuocTK_DT = '0'
      this.modelInput.txtCuocTC_DT = '0'
      this.modelInput.txtMaHD_DT = ''

      this.modelCkeckBox.chkDoiTac_TSL = false
      this.modelInput.txtCuocTK_TSL = '0'
      this.modelInput.txtCuocTC_TSL = '0'
      this.modelInput.txtCuocHT_TSL = '0'
      this.modelInput.txtCuocTBI_TSL = '0'
      this.modelInput.txtCuocTKDT_TSL = '0'
      this.modelInput.txtCuocTCDT_TSL = '0'
      this.modelInput.txtMa_HDDT_TSL = ''

      this.inputValue.vhuong_kn = ''
      this.inputValue.kieu_thueso = 0
      this.inputValue.noidung_thueso = ''
      this.inputValue.sothang_thueso = 0

      this.inputValue.trangbi_id = 3

      this.$parent.modelInput.txtTongTienHD = '0'
      this.inputValue.hdkh_id = 0
      this.modelInput.txtMaGD = ''
      this.modelInput.txtMaHDcu = ''
      this.modelInput.txtMaKH = ''
      this.inputValue.khachhang_id = 0
      this.modelInput.txtTenKH = ''
      this.modelInput.txtDiaChiKH = ''
      this.inputValue.tinhkh_id = 0
      this.inputValue.quankh_id = 0
      this.inputValue.phuongkh_id = 0
      this.inputValue.phokh_id = 0
      this.inputValue.apkh_id = 0
      this.inputValue.khukh_id = 0
      this.inputValue.dacdiemkh_id = 0
      this.inputValue.sonhakh = ''
      this.modelInput.txtNguoiDD = ''
      this.modelInput.txtChucDanhDD = ''
      this.modelInput.txtTelKH = ''
      this.modelInput.txtFaxKH = ''

      this.modelInput.txtNgaySN = ''
      this.modelDateTime.dtpNgayLapHD = new Date()
      this.modelDateTime.dtpNgayYC = new Date()
      this.modelInput.txtSoGT = ''
      this.modelInput.txtNgayCap = ''
      this.modelInput.txtNoiCap = ''
      this.modelCbo.cboGiayToKH = 0
      this.modelCbo.vcboTT = []
      this.modelInput.lvwThueBao = []
      this.modelInput.txtMaTuyen = ''
      this.inputValue.hdtt_id = 0
      this.modelInput.txtMaTT = ''
      this.inputValue.thanhtoan_id = 0
      this.modelInput.txtTenTT = ''
      this.modelInput.txtDiaChiTT = ''

      this.modelInput.txtDiaChiBC = ''
      this.inputValue.tinhbc_id = 0
      this.inputValue.quanbc_id = 0
      this.inputValue.phuongbc_id = 0
      this.inputValue.phobc_id = 0
      this.inputValue.apbc_id = 0
      this.inputValue.khubc_id = 0
      this.inputValue.dacdiembc_id = 0
      this.inputValue.sonhabc = ''
      //
      this.inputValue.tinhtt_id = 0
      this.inputValue.quantt_id = 0
      this.inputValue.phuongtt_id = 0
      this.inputValue.phott_id = 0
      this.inputValue.aptt_id = 0
      this.inputValue.khutt_id = 0
      this.inputValue.dacdiemtt_id = 0
      this.inputValue.sonhatt = ''
      this.modelInput.txtMST = ''
      this.modelInput.txtSoTKKH = ''
      this.modelCbo.cboHTTT = 0
      this.modelCbo.cboDVQL = 0

      this.inputValue.hdtb_id = 0
      this.inputValue.thuebao_id = 0
      this.modelInput.txtMaTB = ''
      this.modelInput.txtTenTB = ''
      this.modelInput.txtDiaChiTB = ''
      this.inputValue.tinhtb_id = 0
      this.inputValue.quantb_id = 0
      this.inputValue.phuongtb_id = 0
      this.inputValue.photb_id = 0
      this.inputValue.aptb_id = 0
      this.inputValue.khutb_id = 0
      this.inputValue.dacdiemtb_id = 0
      this.inputValue.sonhatb = ''

      this.modelInput.txtDiaChiLD = ''
      this.inputValue.tinhld_id = 0
      this.inputValue.quanld_id = 0
      this.inputValue.phuongld_id = 0
      this.inputValue.phold_id = 0
      this.inputValue.apld_id = 0
      this.inputValue.khuld_id = 0
      this.inputValue.dacdiemld_id = 0
      this.inputValue.sonhald = ''
      this.modelInput.txtMaTN_Wan = ''
      this.modelInput.txtSoAoWan = ''
      this.inputValue.tinhdau_id = 0
      this.inputValue.quandau_id = 0
      this.inputValue.phuongdau_id = 0
      this.inputValue.phodau_id = 0
      this.inputValue.apdau_id = 0
      this.inputValue.sonhadau = ''
      this.modelInput.txtDiaChiDau = ''
      this.modelInput.txtSoAoTSL_Dau = ''

      this.inputValue.tinhcuoi_id = 0
      this.inputValue.quancuoi_id = 0
      this.inputValue.phuongcuoi_id = 0
      this.inputValue.phocuoi_id = 0
      this.inputValue.apcuoi_id = 0
      this.inputValue.sonhacuoi = ''
      this.modelInput.txtDiaChiCuoi = ''
      this.modelInput.txtSoAoTSL_Cuoi = ''

      this.modelInput.txtMaTN_CD = ''
      this.modelInput.txtMaDoiCap_CD = ''
      this.modelInput.txtMaDoiCap_ADSL = ''
      this.modelInput.txtMaTN_ADSL = ''
      this.modelInput.txtSomayTN = ''
      this.inputValue.SoMayTN = ''
      this.modelInput.txtSoMetDay = '0'
      this.modelInput.txtHuongKNWan = ''

      this.modelCkeckBox.cboDichVuVT = true
      this.modelInput.txtDiaChiCT = ''
      this.modelInput.txtChucDanhDD = ''
      this.modelInput.txtNgayHetHan = ''
      this.modelInput.txtMaSTKH = ''
      this.modelInput.txtSoTKKH = ''
      this.modelInput.txtGhiChuKH = ''

      this.modelInput.txtGiayToKem = ''
      this.modelCkeckBox.chkNganHangKH = false
      this.modelCkeckBox.cboNganHangKH = false
      this.modelInput.txtMaTBDD = ''
      this.modelInput.txtMaERP = ''
      this.modelInput.txtMaBSS = ''

      this.inputValue.blockkh = ''
      this.inputValue.tangkh = ''
      this.inputValue.sophongkh = ''
      this.inputValue.motadckh = ''

      this.inputValue.blockld = ''
      this.inputValue.tangld = ''
      this.inputValue.sophongld = ''
      this.inputValue.motadcld = ''

      this.inputValue.blocktb = ''
      this.inputValue.tangtb = ''
      this.inputValue.sophongtb = ''
      this.inputValue.motadctb = ''

      this.inputValue.blocktt = ''
      this.inputValue.tangtt = ''
      this.inputValue.sophongtt = ''
      this.inputValue.motadctt = ''
      try {
        this.modelInput.txtNguoiGT = ''
        this.modelInput.txtCTV = ''
        this.inputValue.nguoigt_id = 0
        this.inputValue.ctv_id = 0
      } catch (Exception) {}

      this.modelInput.txtMaNVTC = ''
      this.modelInput.txtNhanVienQL = ''
      this.inputValue.nhanvien_ql_am_id = 0

      this.modelInput.txtGhiChuTT = ''
      this.modelInput.txtNgaySinhTB = ''

      this.modelInput.txtNgayCapHK = ''
      this.modelInput.txtNoiCapHK = ''
      this.inputValue.donvi_dau_id = 0
      this.inputValue.donvi_cuoi_id = 0
      if (
        this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.ADSL ||
        this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.BRCD_DOITAC
      ) {
        this.inputValue.ltb_adsl_id = parseInt(this.$refs.refAdsl.cboLoaiTBAdsl.Value)
        console.log('🚀 ~ file: index.vue:5427 ~ this.inputValue.ltb_adsl_id:', this.inputValue.ltb_adsl_id)
      } else {
        if (
          this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.MEGAWAN ||
          this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.METRONET ||
          this.modelCbo.cboDichVuVT === Enum.DichVuVienThong.NOIBO_DADIEM ||
          this.modelCbo.cboDichVuVT == Enum.DichVuVienThong.MGW_DOITAC
        ) {
          this.inputValue.ltb_adsl_id = parseInt(this.$refs.refWan.cboLoaiTBiWan.Value)
          console.log('🚀 ~ file: index.vue:5435 ~ this.inputValue.ltb_adsl_id:', this.inputValue.ltb_adsl_id)
        } else {
          this.inputValue.ltb_adsl_id = 1
        }
      }

      this.inputValue.doituong_id = 1
      this.inputValue.khach_hang_id = 0
      this.inputValue.kt_adsl = true
      this.inputValue.ikieuld_id = 0
      this.modelInput.txtDiaChiNDD = ''
      this.modelInput.txtEmailAdsl = ''
      this.modelInput.txtMaTBMoi = ''
      this.inputValue.ma_tbmoi = ''
      if (this.inputValue.kieukh_canhan) {
        this.inputValue.DV_QHNS = ''
      }
      this.inputValue.MACHUONG = ''
      this.inputValue.MN_KT = ''
      this.inputValue.MA_NDKT = ''
      this.inputValue.MN_NSNN = ''
      this.inputValue.TIEN_DM = 0
      await this.LAY_DS_SUDUNG_DVGT(0)

      // TuấnNA thêm code clear biến -- Ngày 03/12/2010
      this.inputValue.TNC1_ID = 0
      this.inputValue.TNC2_ID = 0
      this.inputValue.TNC3_ID = 0
      this.inputValue.TNC4_ID = 0
      this.inputValue.nganhnghe_id = 0

      this.inputValue.cuoc_tc = 0
      this.inputValue.cuoc_tk = 0

      this.inputValue.hdmig_id = 0
      this.inputValue.mig_id = 0
      this.modelInput.txtMaIG = ''

      this.modelInput.txtHoKhau = ''
      this.modelInput.txtSTKTT = ''
      this.modelInput.txtChu_TK = ''
      this.inputValue.hdtb_id_cha = 0
      this.modelInput.txtKhuVuc = ''

      this.dsHDKH_Sub = null
      this.dsHDTB_Sub = null
    },
    SetButton: function (kieu) {
      this.buttonState.tsbtnNhapMoi = false
      this.buttonState.tsbtnGhiLai = false
      this.buttonState.tsbtnXoa = false
      this.buttonState.tsbtnHuyBo = false

      this.buttonState.tsbtnThemTB = false
      this.buttonState.tsbtnThemTB_DS = false
      this.buttonState.tsbtnThemTT = false

      this.buttonState.tsbtnInhopdong = false
      this.buttonState.tsbtnThanhToan = false
      this.buttonState.tsbtnXoaTB = false
      if (kieu == -1) {
        // Bat dau
        this.$refs['ma_tb'].focus()
        this.buttonState.tsbtnGhiLai = true
        this.buttonState.tsbtnHuyBo = true
      }

      if (kieu == 0) {
        // Bat dau
        this.$refs['ma_tb'].focus()
        this.buttonState.tsbtnNhapMoi = true
        this.Clear()
      }

      if (kieu == 1) {
        // Them moi
        this.$refs['ma_tb'].focus()
        this.buttonState.tsbtnGhiLai = true
        this.buttonState.tsbtnHuyBo = true
        this.Clear()
      }

      if (kieu == 2) {
        // Huy
        this.$refs['ma_gd'].focus()
        this.buttonState.tsbtnNhapMoi = true
        this.buttonState.tsbtnXoa = true
        this.Clear()
      }

      if (kieu == 3) {
        // Edit
        this.$refs['ma_gd'].focus()
        this.buttonState.tsbtnNhapMoi = true
        this.buttonState.tsbtnXoa = true
        this.buttonState.tsbtnGhiLai = true
        this.buttonState.tsbtnHuyBo = true

        this.buttonState.tsbtnThemTB = true
        this.buttonState.tsbtnThemTB_DS = true
        this.buttonState.tsbtnThemTT = true

        this.buttonState.tsbtnInhopdong = true
        this.buttonState.tsbtnThanhToan = true

        if (this.modelInput.lvwThanhToan.length > 1) {
          this.buttonState.tsbtnXoaTT = true
        }
        if (this.modelInput.lvwThueBao.length > 1) {
          this.buttonState.tsbtnXoaTB = true
        }
      }
    },
    async cboTrangBi_SelectedValueChanged (data) {
      try {
        if (!data) return
        let trangbi_id = Number(data.trangbi_id)
        if (Number(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.TSL ||
                    Number(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.TSL_DOITAC ||
                    Number(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.NOIBO_2DIEM
                    ) {
          this.inputValue.trangbi_id = trangbi_id
          if (!this.inputValue.kt_load) { await this.LayTienTheoKhoanMuc() }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 5055 ~ cboTrangBiADSL_SelectedValueChanged ~ error', error)
      }
    },
    async cboTrangBiCD_SelectedValueChanged (data) {
      try {
        if (!data) return
        let trangbi_id = Number(data.trangbi_id)
        if (Number(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.CO_DINH) {
          this.inputValue.trangbi_id = trangbi_id
          if (!this.inputValue.kt_load) { await this.LayTienTheoKhoanMuc() }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 5055 ~ cboTrangBiADSL_SelectedValueChanged ~ error', error)
      }
    },
    async cboTrangBiADSL_SelectedValueChanged (data) {
      try {
        if (!data) return
        let trangbi_id = Number(data.trangbi_id)
        if (Number(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.ADSL ||
                    Number(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.BRCD_DOITAC) {
          this.inputValue.trangbi_id = trangbi_id
          if (!this.inputValue.kt_load) { await this.LayTienTheoKhoanMuc() }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 5055 ~ cboTrangBiADSL_SelectedValueChanged ~ error', error)
      }
    },
    async get_thamso_md (thamso_md) {
      var str_vtemp
      var det
      if (thamso_md && thamso_md.length > 0) {
        str_vtemp = 'NHANVIEN_DIABAN_DC'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          if (det[0].ten_ts.toString() === '1') {
            this.inputValue.nhap_nvkd = 1
          }
        }
        str_vtemp = 'CHECK_CHUANHOA_CNTT'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          if (det[0].ten_ts.toString() === '1') {
            this.inputValue.check_chuahoa_cntt = true
          }
        }

        str_vtemp = 'PHANTAP_KH'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          if (det[0].ten_ts.toString() === '1') {
            this.inputValue.phantap_kh = true
          }
        }

        str_vtemp = 'KIEMTRA_MST'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          if (det[0].ten_ts.toString() === '1') {
            this.inputValue.ts_kt_mst = 1
          }
        }

        str_vtemp = 'HT_LOC_CBO_TRAM'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          if (det[0].ten_ts.toString() === '1') {
            this.inputValue.ht_loc_cbo_tram = true
          }
        }

        str_vtemp = 'KHONGBATBUOC_CTV'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          if (det[0].ten_ts.toString() === '1') {
            this.inputValue.khong_batbuoc_ctv = 1
          }
        }

        str_vtemp = 'BATBUOC_GIOITHIEU_DICHCHUYEN'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          if (det[0].ten_ts.toString() === '1') {
            this.inputValue.batbuoc_gioithieu_dichchuyen = 1
          }
        }

        str_vtemp = 'TUDONG_GANTHEM_HDTB_DV'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          if (det[0].ten_ts.toString() === '1') {
            this.inputValue.tudong_ganthem_hdtb_dv = true
          }
        }

        str_vtemp = 'DONVIQL_HTTT'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          if (det[0].ten_ts.toString() === '1') {
            this.inputValue.DONVIQL_HTTT = 1
          }
        }

        str_vtemp = 'TUYENTHU_HTTT'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          if (det[0].ten_ts.toString() === '1') {
            this.inputValue.TUYENTHU_HTTT = 1
          }
        }

        str_vtemp = 'KHONG_HIENTHI_TRACUU_DB_DICHCHUYEN'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          if (det[0].ten_ts.toString() === '1') {
            this.inputValue.khong_ht_tracuudb = 1
          }
        }

        str_vtemp = 'KHONGCHO_CHON_KHUVUC'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          if (det[0].ten_ts.toString() === '1') {
            this.inputValue.khongcho_chon_khuvuc = true
            this.buttonState.btnMaNVKD = false
          }
        }
        str_vtemp = 'SINH_MA_GD_THEO_DV'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          this.inputValue.ts_sinhma_gd_theo_donvi = true
        } else {
          this.inputValue.ts_sinhma_gd_theo_donvi = false
        }

        str_vtemp = 'KIEMTRA_SDT'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          if (det[0].ten_ts.toString() === '1') {
            this.inputValue.batbuoc_sdt = true
            this.buttonState.lblRequiredTel = true
          } else {
            this.inputValue.batbuoc_sdt = false
            this.buttonState.lblRequiredTel = false
          }
        }

        str_vtemp = 'KIEMTRA_MSTMNS_KHDN'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          if (det[0].ten_ts.toString() === '1') {
            this.inputValue.ts_kt_mstmns_kh = 1
          }
        }

        str_vtemp = 'THEM_TT_HDKHDN'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          if (det[0].ten_ts.toString() === '1') {
            this.inputValue.ts_kt_tthdkhdn = 1
          }
        }

        str_vtemp = 'KTRA_TRANGTHAI_DICHCHUYEN'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          if (det[0].ten_ts.toString() === '1') {
            this.inputValue.ktratt = 1
          }
        }

        str_vtemp = 'BATBUOC_CHON_DACDIEM_DCLD'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          if (det[0].ten_ts.toString() === '1') {
            this.inputValue.batbuoc_chon_dacdiem_dcld = true
          } else {
            this.inputValue.batbuoc_chon_dacdiem_dcld = false
          }
        }

        str_vtemp = 'KT_DICHCHUYEN_FIBER_COLAPCHUNG'
        det = thamso_md.filter(x => x.ma_ts === str_vtemp)
        if (det.length > 0) {
          if (det[0].ten_ts.toString() === '1') {
            this.inputValue.kt_fiber_colapchung = true
          }
        }
      }
    },
    async getNgay_Cn () {
      try {
        var rps = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/sp_getsysdate')
        return moment(rps.data[0].ngay_cn).toDate()
      } catch (error) {
        return new Date()
      }
    },
    frmChuyenDich_Load: async function () {
      try {
        let ngay_cn = await this.getNgay_Cn()
        this.modelDateTime.dtpNgayLapHD = ngay_cn
        this.modelDateTime.dtpNgayYC = ngay_cn
        this.SetButton(-1)
        this.inputValue.ds_tien_khoanmuc = await this.Lay_tienkhoanmuc_loaihd(Enum.LoaiHopDong.DI_CHUYEN)
        try {
          var check_kh = await this.$root.context.get('/web-hopdong/chuyendich/lay_ds_trangthaisd')
          if (check_kh && check_kh.data && check_kh.data.length > 0) {
            if ('trangthaisd_id' in check_kh.data[0]) {
              this.parent.tsbtnVatTuMoi = true
            }
          } else {
            this.parent.tsbtnVatTuMoi = false
          }
        } catch (error) {
          this.parent.tsbtnVatTuMoi = false
        }
        await this.Lay_danhmuc_chuyendich_tt_chung_v3()
        await this.Lay_dulieu_combobox()
        this.inputValue.kt_load = false
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 622 ~ error', error)
        this.$root.toastError(' ' + error)
      }
    },
    async LAY_DS_THAMSO_MD (vkieu) {
      try {
        let rs = await this.$root.context.get(
          '/web-hopdong/chuyendich/lay_danhsach_thamso?kieu=' + vkieu
        )
        return rs.data
      } catch (error) {
        return []
      }
    },
    Lay_tienkhoanmuc_loaihd: async function (input) {
      try {
        let rs = await this.$root.context.get(
          '/web-hopdong/chuyendich/lay_tienkhoanmuc_loaihd',
          { vloaihd_id: input }
        )
        return rs.data
      } catch (error) {
        return []
      }
    },
    async LAY_DOITUONG () {
      try {
        var rs = await this.$root.context.get('/web-cabman/ban-do-mang-cap/danh-muc', { loai: 'DS_DOITUONG', option: '' })
        return this.LowerCasePropertyList(rs.data)
      } catch (error) {
        return []
      }
    },
    Lay_danhmuc_chuyendich_tt_chung_v3: async function () {
      try {
        let args =
          'TRANG_BI|LOAI_TB|CHU_QUAN|TIEN_KM|LOAI_GT|PHANLOAI_KH|LOAI_KH|NGANHANG|GOI_KT|DONVI_QL|KIEU_LD|THOIHAN|DOITAC'
        let rs = await this.$root.context.post('web-hopdong/chuyendich/lay_danhmuc_chuyendich_tt_chung_v3', { vchuoi: args })
        if (rs.data != null) {
          this.dsDanhMuc = rs.data
          this.modelCbo.vcboDaiVT = rs.data.donvi_ql.filter(
            x => x.loaidv_id === Enum.LOAI_DV.DONVIQL_LD
          )
          this.modelCbo.vcboGoiKT = rs.data.goi_kt
          if (rs.data.goi_kt.length > 0) {
            this.modelCbo.cboGoiKT = this.modelCbo.vcboGoiKT[0].goikt_id
          }
          if (this.modelCbo.vcboDaiVT.length > 0) {
            this.modelCbo.cboDaiVT = this.modelCbo.vcboDaiVT[0].donvi_id
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 5410 ~ error', error)
        return []
      } finally {
      }
    },
    async LoadTuHopDongB2A (phieucskh_id, phieucskhdv_id) {
      try {
        this.inputValue.IS_B2A = true
        this.$parent.SetButton(1)
        let rs = await this.$root.context.get(
          '/web-hopdong/chuyendich/get_thongtin_tu_phieu_tuvan?phieucskhdv_id=' + phieucskhdv_id
        )
        if (rs.data.length > 0) {
          let d = this.UpperCasePropertyList(rs.data)[0]
          this.inputValue.VPHIEUCSKH_ID = phieucskh_id
          this.inputValue.VPHIEUCSKHDV_ID = phieucskhdv_id
          this.ThongTinKhachHang.txtTenKH = d.TEN_KH
          this.modelInput.txtTenTB = d.TEN_KH
          this.ThongTinKhachHang.txtDiaChiKH = d.DIACHI_KH
          this.modelInput.txtDiaChiTB = d.DIACHI_KH
          this.modelInput.txtDiaChiLD = d.DIACHI_KH
          this.ThongTinKhachHang.txtTelKH = d.SO_DT
          if (d.KHACHHANG_ID === 0 || d.KHACHHANG_ID === null || d.KHACHHANG_ID === '' || d.KHACHHANG_ID === '-1') {
            let rss = await this.$root.context.get(
              '/web-hopdong/chuyendich/get_thongtin_diachi_khachhang?phieucskh_id=' + phieucskh_id
            )
            if (rss.data.length > 0) {
              let d = this.UpperCasePropertyList(rss.data)[0]
              this.inputValue.tinhld_id = parseInt(d.TINH_ID)
              this.inputValue.tinhtb_id = parseInt(d.TINH_ID)

              this.inputValue.quanld_id = parseInt(d.QUAN_ID)
              this.inputValue.quantb_id = parseInt(d.QUAN_ID)

              this.inputValue.phuongld_id = parseInt(d.PHUONG_ID)
              this.inputValue.phuongtb_id = parseInt(d.PHUONG_ID)

              this.inputValue.phold_id = parseInt(d.PHO_ID)
              this.inputValue.photb_id = parseInt(d.PHO_ID)

              this.inputValue.khuld_id = parseInt(d.KHU_ID)
              this.inputValue.khutb_i = parseInt(d.KHU_ID)

              this.inputValue.apld_id = parseInt(d.AP_ID)
              this.inputValue.aptb_id = parseInt(d.AP_ID)

              this.inputValue.sonhald = d.SONHA
              this.inputValue.sonhatb = d.SONHA

              if (d.VIDO !== null && d.VIDO !== '') {
                this.ThongTinKhachHang.Lat_KH = d.VIDO
                this.ThongTinKhachHang.Lat_TT = d.VIDO
                this.inputValue.Lat_LD = d.VIDO
                this.inputValue.Lat_TB = d.VIDO
              }

              if (d.KINHDO !== null && d.KINHDO !== '') {
                this.ThongTinKhachHang.Lng_KH = d.KINHDO
                this.ThongTinKhachHang.Lng_TT = d.KINHDO
                this.inputValue.Lng_LD = d.KINHDO
                this.inputValue.Lng_TB = d.KINHDO
              }
            }
          } else {
            this.modelCbo.cboLoaihinhTB = d.LOAITB_ID
            this.modelCbo.cboDichVuVT = d.DICHVUVT_ID
            this.modelInput.txtMaTB = d.MA_TB
            await this.txtMaTB_KeyPress()
          }
        }
      } catch (error) {

      }
    },
    async LoadTuHopDongTuVan (hdkh_cha_id) {
      try {
        this.$parent.SetButton(1)
        let rs = await this.$root.context.get(
          '/web-hopdong/chuyendich/get_thongtin_tu_phieu_tuvan_hdkh?phdkh_cha_id=' + hdkh_cha_id
        )
        if (rs.data.length > 0) {
          let d = this.UpperCasePropertyList(rs.data)[0]
          this.inputValue.hdkh_cha_id = hdkh_cha_id
          this.ThongTinKhachHang.txtTenKH = d.TEN_KH
          this.modelInput.txtTenTB = d.TEN_KH
          this.ThongTinKhachHang.txtDiaChiKH = d.DIACHI_KH
          this.modelInput.txtDiaChiTB = d.DIACHI_KH
          this.modelInput.txtDiaChiLD = d.DIACHI_KH
          this.ThongTinKhachHang.txtTenKH = d.SO_DT
          if (d.KHACHHANG_ID === 0 || d.KHACHHANG_ID === null || d.KHACHHANG_ID === '' || d.KHACHHANG_ID === '-1') {
            let rss = await this.$root.context.get(
              '/web-hopdong/chuyendich/get_thongtin_diachi_khachhang_hdkh?phdkh_cha_id=' + hdkh_cha_id
            )
            if (rss.data.length > 0) {
              let d = this.UpperCasePropertyList(rss.data)[0]
              this.inputValue.tinhld_id = parseInt(d.TINH_ID)
              this.inputValue.tinhtb_id = parseInt(d.TINH_ID)

              this.inputValue.quanld_id = parseInt(d.QUAN_ID)
              this.inputValue.quantb_id = parseInt(d.QUAN_ID)

              this.inputValue.phuongld_id = parseInt(d.PHUONG_ID)
              this.inputValue.phuongtb_id = parseInt(d.PHUONG_ID)

              this.inputValue.phold_id = parseInt(d.PHO_ID)
              this.inputValue.photb_id = parseInt(d.PHO_ID)

              this.inputValue.khuld_id = parseInt(d.KHU_ID)
              this.inputValue.khutb_i = parseInt(d.KHU_ID)

              this.inputValue.apld_id = parseInt(d.AP_ID)
              this.inputValue.aptb_id = parseInt(d.AP_ID)

              this.inputValue.sonhald = d.SONHA
              this.inputValue.sonhatb = d.SONHA

              if (d.VIDO !== null && d.VIDO !== '') {
                this.ThongTinKhachHang.Lat_KH = d.VIDO
                this.ThongTinKhachHang.Lat_TT = d.VIDO
                this.inputValue.Lat_LD = d.VIDO
                this.inputValue.Lat_TB = d.VIDO
              }

              if (d.KINHDO !== null && d.KINHDO !== '') {
                this.ThongTinKhachHang.Lng_KH = d.KINHDO
                this.ThongTinKhachHang.Lng_TT = d.KINHDO
                this.inputValue.Lng_LD = d.KINHDO
                this.inputValue.Lng_TB = d.KINHDO
              }
            }
          } else {
            this.inputValue.khachhang_id = parseInt(d.KHACHHANG_ID)
            this.modelInput.txtMaTB = d.MA_TB
          }
        }
      } catch (error) {

      }
    },
    Lay_dulieu_combobox: async function () {
      try {
        let rs = {}
        rs.data = this.parent.glbDanhMuc_Chung
        this.glbDanhMuc_Chung = this.parent.glbDanhMuc_Chung
        if (rs.data === null) return
        if (rs.data) {
          this.modelCbo.vcboHTTT = rs.data.hinhthuc_tt
          this.modelCbo.vcboDichVuVT = rs.data.dichvu_vt
          this.modelCbo.vcboDoituongTB = rs.data.DOITUONG
          this.modelCbo.vcboDanToc = rs.data.dantoc
          this.modelCbo.vcboQuocTich = rs.data.quoctich
          this.modelCbo.vcboKieu_HD = rs.data.kieu_hd

          if (rs.data.hinhthuc_tt.length > 0) {
            this.modelCbo.cboHTTT = this.modelCbo.vcboHTTT[0].httt_id
          }
          if (rs.data.DOITUONG.length > 0) {
            this.modelCbo.cboDoituongTB = this.modelCbo.vcboDoituongTB[0].doituong_id
          }
          if (rs.data.dichvu_vt.length > 0) {
            this.modelCbo.cboDichVuVT = this.modelCbo.vcboDichVuVT[0].dichvuvt_id
            await this.cboDichVuVT_SelectedValueChanged()
          }
          if (rs.data.quoctich.length > 0) {
            this.modelCbo.cboQuocTich = this.modelCbo.vcboQuocTich[0].quoctich_id
          }
          if (rs.data.dantoc.length > 0) {
            this.modelCbo.cboDanToc = this.modelCbo.vcboDanToc[0].dantoc_id
          }
          if (rs.data.kieu_hd.length > 0) {
            this.modelCbo.cboKieu_HD = this.modelCbo.vcboKieu_HD[0].kieuhd_id
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 698 ~ error', error)
        return null
      } finally {
      }
    },
    HT_NganhNgheLoc_Combobox: async function () {
      let rs = await this.$root.context.get(
        '/web-hopdong/hoanthienhschuyenquyen/lay-ds-nganhnghe',
        { ma: 'NGANHNGHE' }
      )
      if (rs.data.length > 0) {
        this.modelCbo.vcboNganhNgheLoc = rs.data
        this.modelCbo.cboNganhNgheLoc = rs.data[0].nganhnghe_id
      }
    },
    
  },
  async mounted () {
    this.$nextTick(async () => {
      try {
        var obj = this.$route.query
        if (obj.kieu.toString() === '5') {
          let phieucskh_id = parseInt(obj.phieucskh_id)
          let phieucskhdv_id = parseInt(obj.phieucskhdv_id)
          await this.LoadTuHopDongB2A(phieucskh_id, phieucskhdv_id)
        } else {
          let hdkh_cha_id = parseInt(obj.hdkh_cha_id)
          await this.LoadTuHopDongTuVan(hdkh_cha_id)
        }
      } catch (error) {
      }
    })
  },
  watch: {
    'inputValue.hdkh_id': function (val) {
      this.$emit('change_hdkh_id', val)
    },
    'inputValue.khachhang_id': function (val) {
      this.$emit('change_khachhang_id', val)
    }
  }
}
</script>
<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
