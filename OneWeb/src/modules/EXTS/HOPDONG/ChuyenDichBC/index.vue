<template src="./index.html">
</template>
<script>
import FormThongTinKhachHang from './Components/ThongTinKhachHang'
import FormThongTinThueBao from './Components/ThongTinThueBao'
import Enum from './Enum'
import DateTimeLib from '@/utils/DateTimeLib'
import NguoiGioiThieu from '@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu.vue'
import CongTacVien from '@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien.vue'
import ThanhToan from '@/modules/contract/setup/Payment/Payment.vue'
import ThongTinHenKhachHangModal from '@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import ThongTinLienHe from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue'
import ThongTinEmail from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue'
import InHoSo from '@/modules/print/InHopDong/InHopDong'
export default {
  name: 'ChuyenDichBC',
  components: {
    FormThongTinKhachHang,
    FormThongTinThueBao,
    DateTimeLib,
    NguoiGioiThieu,
    CongTacVien,
    ThanhToan,
    ThongTinHenKhachHangModal,
    ThongTinLienHe,
    ThongTinEmail,
    InHoSo
  },
  data () {
    return {
      lock: false,
      popupInHoSo_Param: {
        hdkh_id: 0,
        hdtb_id: 0,
        ma_gd: '',
        ngaylap_hd: null,
        ngay_yc: null
      },
      thuebao_tc_id: null,
      Click_kieuld: false,
      huonggiaotn_id: 0,
      quytrinh_id_2: 0,
      dsHDTBGOI: [],
      themMyTVvaogoi: false,
      kieuld_ds: 0,
      dsKM_HDTB: [],
      dsHDBC: [],
      dsHDTBBC: [],
      dsHDBCMGWAN: [],
      dsHDBCADSL: [],
      dsHDBCCD: [],
      dsDKBCDVGT: [],
      dsHDBCTSL: [],
      dsHDBCIMS: [],
      dsLYDOHUYBC: [],
      dsDiaChi_BanCheo: [],

      dsHDKH: [],
      dsHDTT: [],
      dsHDTB: [],
      dsHDTBDV: [],
      dsHDTB_CD: [],
      dsHDTB_ADSL: [],
      dsHDTB_TSL: [],
      dsDiaChiHDKH: [],
      dsDiaChiHDTT: [],
      dsDiaChiHDTB: [],

      dsDK_DVGT: [],
      dsHDTB_MEWAN: [],
      dsHDTB_GP: [],

      dsTTTT: [],
      dsCTTHD: [],
      dsHDTB_IMS: [],

      dsHDTB_KV: [],
      dsHDTT_NV: [],

      dsDK_DVK: [],
      dsDBTBBC: [],
      BanCheoData: {
        Tinh_TC: {
          TNS: null,
          user: null,
          pass: null,
          tentinh: null,
          matinh: null,
          nhanvien_id: null,
          donvi_id: null,
          tinhthicong_id: null,
          mavung: null,
          ip_dll: null
        }
      },
      dsKhuyenMai: [],
      lblNgaySN: null,
      m_dsThueBao_DichVu: {
        dsdvgt_ban: [],
        dsdvgt_tc: []
      },
      dtLoaiKH: [],
      khachhang_tc_id: null,
      kieukh_canhan: false,
      PHANLOAIKH_ID: -1,
      thongso_ban: {},
      thongso_tc: {},
      thongso_tc_dau: {},
      thongso_tc_cuoi: {},
      ht_loc_cbo_tram: false,
      kt_kenhphu_ao: false,
      kt_load: true,
      nvkd_id: 0, // Nhân viên phụ trách kinh doanh
      khuvuc_id: 0,
      iKenhTN: 1, // Keenh tieep nhan hop dog (trong bangr kenh_tn)
      kieu_thueso: 0, // 0 <-> ko thuê số; 1 <-> số trượt; 2 <-> thông báo
      noidung_thueso: '',
      sothang_thueso: 0,
      KieuLD_click: false,
      dc_daucuoi: 0,
      dichvuvt_id: null,
      httt_id: null,
      hdkh_id: null,
      hdtt_id: null,
      hdtb_id: null,
      hdkh_cha_id: 0,
      khachhang_id: null,
      thanhtoan_id: null,
      thuebao_id: null,
      tuyenthu_id: 0,
      matb_dd: '',
      ma_tb: '',
      // Thangph: Them 07/6/2015
      ds_tien_khoanmuc: [],
      dtienld: 0,
      dvatld: 0,
      dtien_dv: 0,
      dvat_dv: 0,
      diachikh_id: null,
      dacdiemkh_id: null,
      phokh_id: null,
      khukh_id: null,
      apkh_id: null,
      phuongkh_id: null,
      quankh_id: null,
      tinhkh_id: null,
      sonhakh: null,
      // Hieu: 2/7/2010
      kt_adsl: true, // Kiểm tra mã đôi cáp ADSL - Hiếu 22.09.2010
      khach_hang_id: 0, // biến sử dụng cho phần lắp kèm,lắp trên đường có sẵn 22.09.2010 - Hiếu
      // private long khachhang_id_dcd : 0;///Hiếu thêm bien khachhang_id_dcd (dang chuyen dịch) -- ngay 10/07/2010
      TBHD_PL: 1, /// Hiếu them bien TBHD_PL  -- ngay 10/07/2010
      trangbi_id: 3, // Defaul khách hàng tự trang bị

      khundd_id: null,
      dacdiemndd_id: null,
      apndd_id: null,
      phondd_id: null,
      phuongndd_id: null,
      quanndd_id: null,
      tinhndd_id: null,
      sonhandd: null,

      khuct_id: null,
      apct_id: null,
      phoct_id: null,
      dacdiemct_id: null,
      phuongct_id: null,
      quanct_id: null,
      tinhct_id: null,
      sonhact: null,

      diachitt_id: null,
      khutt_id: null,
      phott_id: null,
      aptt_id: null,
      phuongtt_id: null,
      dacdiemtt_id: null,
      quantt_id: null,
      tinhtt_id: null,
      sonhatt: null,

      diachitb_id: null,
      khutb_id: null,
      photb_id: null,
      dacdiemtb_id: null,
      aptb_id: null,
      phuongtb_id: null,
      quantb_id: null,
      tinhtb_id: null,
      sonhatb: null,
      // -- dia chi BC
      diachibc_id: null,
      phobc_id: null,
      dacdiembc_id: null,
      apbc_id: null,
      khubc_id: null,
      phuongbc_id: null,
      quanbc_id: null,
      tinhbc_id: null,
      sonhabc: null,
      //
      diachild_id: null,
      phold_id: null,
      dacdiemld_id: null,
      khuld_id: null,
      apld_id: null,
      phuongld_id: 0,
      quanld_id: null,
      tinhld_id: null,
      sonhald: null,

      diachidau_id: null,
      tinhdau_id: null,
      phodau_id: null,
      apdau_id: null,
      khudau_id: null,
      phuongdau_id: null,
      quandau_id: null,
      sonhadau: null,

      diachicuoi_id: null,
      phocuoi_id: null,
      khucuoi_id: null,
      apcuoi_id: null,
      phuongcuoi_id: null,
      quancuoi_id: null,
      tinhcuoi_id: null,
      sonhacuoi: null,
      SoMayTN: '',

      ma_tbmoi: '',
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

      // Hiếu bổ sung khai báo lấy đơn vị đầu, đơn vị cuối 06/09/2010
      donvi_dau_id: null,
      donvi_cuoi_id: null,
      ma_tb_dau: '', // Mã thuê bao đầu của thuê bao truyền số liệu
      ma_tb_cuoi: '', // mã thuê bao cuối của thuê bao truyền số liệu
      // Hiếu bổ sung phần khai báo cho hàm laytien 08/08/2010
      tien_cd: 0,
      // Hiếu bổ sung thêm 2 dataset thông tin thanh toán & chi tiết tiền khoản mục 15/09/2010

      CT_TIENHD_ID: null,
      // Hiếu bổ sung phần khai báo cho hàm laytien --14/09/2010
      tien_tb: 0,
      vat_tb: 0,
      tien_dv: 0,
      vat_dv: 0,
      dtCTKM: null, // hiếu 16/09/2010: Lưu danh sách các khoản mục.
      //
      // TuấnNA thêm khai báo cho nút ngân sách nhà nước --- Ngày 12/11/2010
      // Bổ sung Tien_dm ngày 16.03.2011
      DV_QHNS: null,
      MACHUONG: null,
      MN_KT: null,
      MA_NDKT: null,
      MN_NSNN: null,
      TIEN_DM: null,
      //

      loaihd_id: null,
      loaitb_id: null,
      kieuld_id: null,
      doituong_id: null,
      ngaylhd: null,
      nhaphd_kt: null,
      // private int phuongld_id;

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

      ds_isdn: [],
      isdn_sochinh: false,
      matb_isdn_chinh: '',

      hdtb_id_cha: 0,
      thuebao_id_cha: 0,
      loaisochinh: 0,
      cuoc_tk: 0,
      cuoc_tc: 0,
      nhanvientc_id: 0,
      nhanvien_ql_am_id: 0,

      tocdotn_id: 0,
      muccuoctn_id: 0,
      quytrinh_id: 0,
      luong_id: 0,

      hdmig_id: 0,
      mig_id: 0,

      _loaitb_id: null,
      _tocdo_id: null,

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

      KetCuoi_ID: -1, // KieuNV:Thêm chọn kết cuối

      Lat_TB: null,
      Lng_TB: null,
      Lat_LD: null,
      Lng_LD: null,
      Lat_TT: null,
      Lng_TT: null,
      Lat_BC: null,
      Lng_BC: null,
      Lat_KH: null,
      Lng_KH: null,
      Lat_DD: null,
      Lng_DD: null,
      Lat_DC: null,
      Lng_DC: null,
      ts_kt_mst: 0,

      ctv_id: null,
      donvi_ctv_id: null,
      loainv_ctv_id: null,
      nguoigt_id: null,
      donvi_nguoi_gt_id: null,
      loainv_nguoi_gt_id: null,

      khong_batbuoc_ctv: 0,
      batbuoc_gioithieu_dichchuyen: 0,
      tudong_ganthem_hdtb_dv: false,

      DONVIQL_HTTT: 0,
      TUYENTHU_HTTT: 0,

      ts_sinhma_gd_theo_donvi: false, // Dungf cho Hni sinh ma GD theo don vi quan ly cua ng dung
      loi_sinh_magd: false,

      quytrinh_tc_tn: 0,
      huonggiao_tc_tn: 0,

      quytrinh_tc_tn_dau: null,
      huonggiao_tc_tn_dau: 0,
      quytrinh_tc_tn_cuoi: null,
      huonggiao_tc_tn_cuoi: 0,

      khong_ht_tracuudb: 0,

      kenh_trang: true,

      tinh_dau_id: null,
      tinh_dau: {},
      tinh_cuoi: {},
      tinh_cuoi_id: null,
      thuebao_id_dau: null,
      thuebao_id_cuoi: null,
      chi_giao_net: true, // anhnt : 10/06/2020
      ma_tb_phu_30bd: '',
      lblTongTien: 0,
      s_display_div: 1,
      dataSourceDV: {
        idDichVu: '',
        idLoaihinhTb: '',
        idKieuLd: '',
        idDaiVt: ''
      },
      KIEU_HD: {
        TAI_GD: 1,
        TAI_NHA: 2
      },
      activetab: 0,
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
      modelInput: {
        txtDiaChiNDD: '',
        txtCTV: '',
        txtNguoiGT: '',
        txtTongTienHD: '',
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
        txtMaTT: '',
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
        lvwThanhToan: [],
        lvDvkhac_TT: '',
        lvwThueBao: [],
        txtKhuVuc: ''
      },
      modelCbo: {
        vcboLoaiKH: [],
        cboLoaiKH: -1,
        cboKhachHangLon: -1,
        vcboKhachHangLon: [],
        cboMaDD: -1,
        vcboMaDD: [],
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
      modelCkeckBox: {
        chkCTV: false,
        rdoGioiTinh: 0,
        rdoGTNam: true,
        rdoGTNone: true,
        rdoGTNu: true,
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
      buttonState: {
        tsbtnNhapMoi: false,
        tsbtnGhiLai: false,
        tsbtnHuyBo: false,
        tsbtnXoa: false,
        tsbtnThanhToan: false,
        toolStripDropDownButton1: false,
        tsbtnXoaTB: false,
        tsbtnThemTB_DS: false,
        tsbtnThongTinLH: true,
        tsbtnEmail: true,
        tsbtnHen: true,
        tsbtnVatTuMoi: false,
        btnChonSM: false,
        btnMaNVKD: true,
        lblRequiredTel: false,
        btnThueSo: false,
        tsbtnInPL: true,
        tsbtnThemTB: true,
        tsbtnThemTT: false,
        tsbtnXoaTT: false,
        tsbtnInhopdong: false,
        rdoGTNam: false,
        rdoGTNone: false,
        rdoGTNu: false
      },
      buttonVisible: {
        btnDiemDau: false,
        btnDiemCuoi: false,
        label76: false,
        lblKHL: false,
        cboKhachHangLon: false,
        lblPLKH: false,
        txtPhanLoaiKH: false
      },
      isEnableCboNganhNgheLoc: true,
      enableCboNganHangTT: false,
      inputObjFrmTraCuuNVTC: {},
      objChiTietNganhNghe: {},
      isEnableCboQuocTich: true,
      isEnableCboDanToc: true,
      cboNganhNgheLoc_LockEvents: false
    }
  },
  async mounted () {
    await this.frmChuyenDichBC_Load()
  },
  methods: {
    onclickTab: function (currentActivetab) {
      try {
        this.loading(true)
        this.activetab = currentActivetab
      } catch (error) {
        this.$root.toastError(' ' + error)
      } finally {
        this.loading(false)
      }
    },
    btnTimMaNVTC_Click () {},
    btnDiaChiBC_Click () {},
    btnDiaChiCT_Click () {},
    btnDiaChiTT_Click () {},
    btnDiaChiNDD_Click () {},
    btnLoaiKH_Click () {},
    btnNganhNghe_Click () {},
    btnDiaChiKH_Click () {},
    async tsbtnNhapMoi_Click () {
      this.onclickTab(0)
      await this.SetButton(1)
    },
    async tsbtnGhiLai_Click () {
      if (this.dichvuvt_id === Enum.DichVuVienThong.TSL || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM) {
        if (this.BanCheoData.Tinh_TC.tinhthicong_id > 0 && (this.thongso_tc_dau.muccuoctb_id === 0 || this.thongso_tc_cuoi.muccuoctb_id === 0)) {
          this.$root.$toast.error('Bạn chưa chọn mức cước cho thuê bao bán và thi công. Ấn vào kiểu lắp đặt để lấy thông tin')
          return false
        }
      } else {
        console.log('🚀 ~ file: index.vue ~ line 670 ~ tsbtnGhiLai_Click ~ this.thongso_tc', this.thongso_tc)
        console.log('🚀 ~ file: index.vue ~ line 670 ~ tsbtnGhiLai_Click ~ this.thongso_ban', this.thongso_ban)
        if (this.BanCheoData.Tinh_TC.tinhthicong_id > 0 && (this.thongso_ban.muccuoctb_id === 0 || this.thongso_tc.muccuoctb_id === 0)) {
          this.$root.$toast.error('Bạn chưa chọn mức cước cho thuê bao bán và thi công. Ấn vào kiểu lắp đặt để lấy thông tin')
          return false
        }
      }
      try {
        this.loading(true)
        await this.CapNhat()
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 679 ~ tsbtnGhiLai_Click ~ error', error)
      } finally {
        this.loading(false)
      }
    },
    async LAY_DS_DB_THUEBAO_THEO_TBID (tinh_id, thuebao_id) {
      try {
        // let res = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/lay_ds_db_thuebao_theo_tbid_2', {tinh_id, thuebao_id})
        let res = await this.$root.context.post('/web-bancheo/tracuu/lay_ds_db_thuebao_theo_tbid', {tinhthicong_id: tinh_id, thuebao_id})
        console.log('🚀 ~ file: index.vue ~ line 661 ~ LAY_DS_DB_THUEBAO_THEO_TBID ~ res', res)
        return this.UpperCasePropertyList(res.data)
      } catch (error) {
        return []
      }
    },
    async MAP_ID_THANHTOAN_ID (thuebao_id) {
      try {
        let res = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/map_id_thanhtoan_id', {thuebao_id})
        if (res.data.length > 0) {
          return res.data[0].thanhtoan_id
        } else return 0
      } catch (error) {
        return 0
      }
    },
    async TaoDuLieu_HDTBBC_TSL (themmoi, vdaucuoi_id) {
      try {
        let ds = []
        let rowHDBC = {}
        if (!this.kenh_trang) {
          if (vdaucuoi_id === 1) {
            ds = await this.LAY_DS_DB_THUEBAO_THEO_TBID(this.tinh_dau.tinhthicong_id, this.thuebao_id_dau) 
          } else { 
            ds = await this.LAY_DS_DB_THUEBAO_THEO_TBID(this.tinh_cuoi.tinhthicong_id, this.thuebao_id_cuoi) 
          }
          if (ds.length > 0) { rowHDBC = ds[0] }
        }

        if (vdaucuoi_id === 1) {
          rowHDBC.TINH_THICONG = this.tinh_dau.tinhthicong_id
          if (this.kenh_trang) { rowHDBC.THUEBAO_ID_THICONG = null } else { rowHDBC.THUEBAO_ID_THICONG = this.thuebao_id_dau }
        } else {
          rowHDBC.TINH_THICONG = this.tinh_cuoi.tinhthicong_id
          if (this.kenh_trang) { rowHDBC.THUEBAO_ID_THICONG = null } else { rowHDBC.THUEBAO_ID_THICONG = this.thuebao_id_cuoi }
        }

        rowHDBC.HDTB_BAN = this.hdtb_id
        rowHDBC.TINH_BAN = this.$root.token.getPhanVungID()
        rowHDBC.TRANG_THAI = 0
        rowHDBC.LOAIHD_BANCHEO = 1
        rowHDBC.DAUCUOI_ID = vdaucuoi_id
        rowHDBC.THUEBAO_ID_BAN = this.thuebao_id

        if (!this.kenh_trang) {
          rowHDBC.THANHTOAN_ID_TC = parseInt(ds[0]['thanhtoan_id'])
          rowHDBC.KHACHHANG_ID_TC = parseInt(ds[0]['khachhang_id'])
        } else {
          rowHDBC.THANHTOAN_ID_TC = 0
          rowHDBC.KHACHHANG_ID_TC = 0
        }

        rowHDBC.THANHTOAN_ID_BAN = parseInt(await this.MAP_ID_THANHTOAN_ID(this.thuebao_id))
        rowHDBC.KHACHHANG_ID_BAN = this.khachhang_id
        rowHDBC.NGUOI_CN = this.$root.token.getUserName()

        delete rowHDBC.PHANVUNG_ID
        

        this.dsHDBC.push(rowHDBC)
        let rowHDTBBC = {}

        if (!this.kenh_trang) {
          if (vdaucuoi_id === 1) {
            let ds = await this.LAY_DANHBA_THEO_THUEBAOID(this.tinh_dau.tinhthicong_id, this.thuebao_id_dau, this.modelCbo.cboDichVuVT, 0, 2)
            if (ds.length > 0) {
              rowHDTBBC = ds[0]
            }
          } else {
            let ds = await this.LAY_DANHBA_THEO_THUEBAOID(this.tinh_cuoi.tinhthicong_id, this.thuebao_id_cuoi, this.modelCbo.cboDichVuVT, 0, 2)
            if (ds.length > 0) {
              rowHDTBBC = ds[0]
            }
          }
        } else {
          rowHDTBBC = { ...this.dsHDTB[0] }
          rowHDTBBC.THUEBAO_ID = 0
          rowHDTBBC.DONVI_ID = null
        }

        rowHDTBBC.HDTB_ID = this.hdtb_id
        rowHDTBBC.HDKH_ID = this.hdkh_id
        rowHDTBBC.TTHD_ID = 1
        if (vdaucuoi_id === 1) {
          rowHDTBBC.QUYTRINH_ID = this.quytrinh_tc_tn_dau
          rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn_dau
          rowHDTBBC.DOITUONG_ID = this.thongso_tc_dau.doituong_id
          rowHDTBBC.MUCUOCTB_ID = parseInt(this.thongso_tc_dau.muccuoctb_id)
          rowHDTBBC.KIEULD_ID = this.thongso_tc_dau.kieuld_id
          rowHDTBBC.MUCUOC_TB = parseInt(await this.MAP_ID_THEO_TINH(this.tinh_dau.tinhthicong_id, 'cuoc_tb', 'muccuoc_tb', 'where mucuoctb_id = ' + this.thongso_tc_dau.muccuoctb_id))
          // rowHDTBBC.MUCUOC_TB = parseInt(await this.MAP_ID_THEO_TINH(this.tinh_dau.tinhthicong_id, 'cuoc_tb', 'muccuoc_tb', 'erehw mucuoctb_id = ' + this.thongso_tc_dau.muccuoctb_id))
          rowHDTBBC.TINH_ID = this.tinh_dau_id
        } else {
          rowHDTBBC.QUYTRINH_ID = this.uytrinh_tc_tn_cuoi
          rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn_cuoi
          rowHDTBBC.DOITUONG_ID = this.thongso_tc_cuoi.doituong_id
          rowHDTBBC.MUCUOCTB_ID = parseInt(this.thongso_tc_cuoi.muccuoctb_id)
          rowHDTBBC.KIEULD_ID = this.thongso_tc_cuoi.kieuld_id
          rowHDTBBC.MUCUOC_TB = parseInt(await this.MAP_ID_THEO_TINH(this.tinh_cuoi.tinhthicong_id, 'cuoc_tb', 'muccuoc_tb', 'where mucuoctb_id = ' + this.thongso_tc_cuoi.muccuoctb_id))
          // rowHDTBBC.MUCUOC_TB = parseInt(await this.MAP_ID_THEO_TINH(this.tinh_cuoi.tinhthicong_id, 'cuoc_tb', 'muccuoc_tb', 'erehw mucuoctb_id = ' + this.thongso_tc_cuoi.muccuoctb_id))
          rowHDTBBC.TINH_ID = this.tinh_cuoi_id
        }
        rowHDTBBC.TINH_DC_ID = vdaucuoi_id
        rowHDTBBC.DAUCUOI_ID = vdaucuoi_id
        rowHDTBBC.TEN_TB = this.StringToString(this.modelInput.txtTenTB)
        rowHDTBBC.DIACHI_TB = this.StringToString(this.modelInput.txtDiaChiTB)

        rowHDTBBC.DIACHI_LD = this.StringToString(this.modelInput.txtDiaChiLD)
        if (this.modelInput.txtNgaySinhTB !== '') {
          rowHDTBBC.NGAY_SN = this.modelInput.txtNgaySinhTB
        }

        rowHDTBBC.NGUOI_CN = this.$root.token.getUserName()

        if (this.dichvuvt_id === Enum.DichVuVienThong.TSL || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM) {
          let donvi_dau_id = parseInt(await this.MAP_ID_THEO_TINH(this.tinh_dau.tinhthicong_id, 'donvi_dau_id', 'db_thuebao', 'where thuebao_id = ' + rowHDTBBC.THUEBAO_ID))
          // let donvi_dau_id = parseInt(await this.MAP_ID_THEO_TINH(this.tinh_dau.tinhthicong_id, 'donvi_dau_id', 'db_thuebao', 'erehw thuebao_id = ' + rowHDTBBC.THUEBAO_ID))
          rowHDTBBC.DONVI_DAU_ID = donvi_dau_id
          let donvi_cuoi_id = parseInt(await this.MAP_ID_THEO_TINH(this.tinh_cuoi.tinhthicong_id, 'donvi_cuoi_id', 'db_thuebao', 'where thuebao_id = ' + rowHDTBBC.THUEBAO_ID))
          // let donvi_cuoi_id = parseInt(await this.MAP_ID_THEO_TINH(this.tinh_cuoi.tinhthicong_id, 'donvi_cuoi_id', 'db_thuebao', 'erehw thuebao_id = ' + rowHDTBBC.THUEBAO_ID))
          rowHDTBBC.DONVI_CUOI_ID = donvi_cuoi_id
        }

        if (this.dichvuvt_id === Enum.DichVuVienThong.DI_DONG || this.dichvuvt_id === Enum.DichVuVienThong.ADSL ||
                this.dichvuvt_id === Enum.DichVuVienThong.IMS || this.dichvuvt_id === Enum.DichVuVienThong.CO_DINH ||
                this.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || this.dichvuvt_id === Enum.DichVuVienThong.TSL ||
                this.dichvuvt_id === Enum.DichVuVienThong.METRONET || this.dichvuvt_id === Enum.DichVuVienThong.TRUNGTAM_DULIEU ||
                this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_DADIEM || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM ||
                this.dichvuvt_id === Enum.DichVuVienThong.DICHVU_CNTT || this.dichvuvt_id === Enum.DichVuVienThong.ANTOAN_BAOMAT_TT ||
                this.dichvuvt_id === Enum.DichVuVienThong.GPHONE || this.dichvuvt_id === Enum.DichVuVienThong.HOINGHI_TRUYENHINH) {
          rowHDTBBC.STATUS = '0'
        }

        if (this.loaitb_id === Enum.LoaiHinhTB.ISDN2B_CD || this.loaitb_id === Enum.LoaiHinhTB.ISDN2B_CQ ||
                this.loaitb_id === Enum.LoaiHinhTB.ISDN30B_CD || this.loaitb_id === Enum.LoaiHinhTB.ISDN30B_CQ ||
                this.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_2M || this.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_THUONG ||
                this.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_TUONGTU) {
          if (this.loaisochinh === 0) {
            if (this.hdtb_id_cha > 0) { rowHDTBBC.HDTB_CHA_ID = this.hdtb_id_cha }
          }
        }

        if (parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.MEGAWAN ||
                parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.METRONET ||
                parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.NOIBO_DADIEM) {
          if (this.thuebao_tc_id > 0) {
            let tb_cha_id = parseInt(await this.MAP_ID_THEO_TINH(this.BanCheoData.Tinh_TC.tinhthicong_id, 'thuebao_cha_id', 'db_thuebao', 'where thuebao_id = ' + this.thuebao_tc_id))
            // let tb_cha_id = parseInt(await this.MAP_ID_THEO_TINH(this.BanCheoData.Tinh_TC.tinhthicong_id, 'thuebao_cha_id', 'db_thuebao', 'erehw thuebao_id = ' + this.thuebao_tc_id))
            if (tb_cha_id !== -1) { rowHDTBBC.THUEBAO_CHA_ID = tb_cha_id }
          }
        }
        if (this.modelCbo.cboGoiKT > 0) { rowHDTBBC.GOIKT_ID = parseInt(this.modelCbo.cboGoiKT) }
        delete rowHDTBBC.PHANVUNG_ID
        this.dsHDTBBC.push(rowHDTBBC)
        return true
      } catch (error) {
        console.log('🚀 ~ file: index.vue:844 ~ TaoDuLieu_HDTBBC_TSL ~ error:', error)
        return false
      }
    },
    async LAY_DANHBA_THEO_THUEBAOID (tinh_id, thuebao_id, dichvuvt_id, donvi_id, loaihdbc) {
      try {
        let data = {
          tinhthicong_id: tinh_id, thuebao_id, dichvuvt_id, donvi_id, loaihdbc
        }
        let rs = await this.$root.context.post('/web-bancheo/tracuu/lay_danhba_theo_thuebaoid', data)
        return this.UpperCasePropertyList(rs.data)
      } catch (error) {
        return []
      }
    },
    selectSomeProperties (dsCol, data) {
      return Object.keys(data).reduce(function (obj, k) {
        if (dsCol.includes(k)) {
          obj[k] = data[k]
        }
        return obj
      }, {})
    },
    async TaoDuLieu_HDTBBC (themmoi) {
      try {
        this.dsHDBC = []
        let ds = []
        if (!this.kenh_trang && !this.chi_giao_net) { ds = await this.LAY_DS_DB_THUEBAO_THEO_TBID(this.BanCheoData.Tinh_TC.tinhthicong_id, this.thuebao_tc_id) }
        let rowHDBC = {}
        rowHDBC.HDTB_BAN = this.hdtb_id
        rowHDBC.TINH_THICONG = this.BanCheoData.Tinh_TC.tinhthicong_id
        rowHDBC.TINH_BAN = this.$root.token.getPhanVungID()
        rowHDBC.TRANG_THAI = 0
        rowHDBC.LOAIHD_BANCHEO = 1

        rowHDBC.THUEBAO_ID_BAN = this.thuebao_id
        rowHDBC.THUEBAO_ID_THICONG = this.thuebao_tc_id

        if (!this.kenh_trang && !this.chi_giao_net && (ds !== null && ds.length > 0)) {
          rowHDBC.THANHTOAN_ID_TC = parseInt(ds[0]['thanhtoan_id'])
          rowHDBC.KHACHHANG_ID_TC = parseInt(ds[0]['khachhang_id'])
        } else {
          rowHDBC.THANHTOAN_ID_TC = 0
          rowHDBC.KHACHHANG_ID_TC = 0
        }

        rowHDBC.THANHTOAN_ID_BAN = parseInt(await this.MAP_ID_THANHTOAN_ID(this.thuebao_id))
        rowHDBC.KHACHHANG_ID_BAN = this.khachhang_id
        rowHDBC.NGUOI_CN = this.$root.token.getUserName()
        rowHDBC.DAUCUOI_ID = null
        delete rowHDBC.PHANVUNG_ID
        console.log('rowHDBC', rowHDBC)
        this.dsHDBC.push(rowHDBC)

        this.dsHDTBBC = []
        let rowHDTBBC = {}
        let dsCol = ['HDTB_ID', 'THUEBAO_ID', 'MA_TB', 'TEN_TB', 'DIACHI_TB', 'HDTB_CHA_ID', 'HDKH_ID', 'HDTT_ID', 'KIEULD_ID', 'TTHD_ID', 'LOAITB_ID', 'DOITUONG_ID', 'DICHVUVT_ID', 'DONVI_ID', 'NGAY_HT', 'NGAY_TT', 'SOLUONG', 'KETQUAKS', 'SYNC_STATUS', 'TBHD_PL', 'DIACHI_LD', 'NGAY_SN', 'STATUS', 'KIEU_TB', 'DONVI_DAU_ID', 'DONVI_CUOI_ID', 'EMAIL', 'NGAY_INS', 'NGAY_KH', 'MUCUOCTB_ID', 'MUCUOC_TB', 'THUEBAO_CHA_ID', 'GHICHU', 'QUYTRINH_ID', 'MAY_CN', 'NGAY_CN', 'NGUOI_CN', 'CHUNHOM', 'NHOMTB_ID', 'GOI_ID', 'IP_CN', 'SO_PL', 'CHUYENMANG', 'BANGKE_ID', 'GOIKT_ID', 'CHITIEU_TG', 'THOIGIAN_CK', 'IP_KH', 'LAN_KS', 'TG_THUE_DEN', 'TG_THUE_TU', 'NGUOI_KH', 'TIENTRINHTC_ID', 'MA_DHVIP', 'SOTHANG_CK', 'NGAY_KTCK', 'HDTB_ID_HNI', 'NGAY_HH_TD', 'SYNC_STB', 'HTCM_ID', 'TRANGTHAIYC_ID', 'DOITUONG_CT_ID', 'NHACC_CU_ID', 'LOAI_CSHT_ID', 'QUAN_ID', 'PHUONG_ID', 'PHO_ID', 'KHU_ID', 'AP_ID', 'DACDIEM_ID', 'HUONGGIAO_TN_ID', 'DAUCUOI_ID', 'TINH_ID', 'TINH_DC_ID']
        if (!this.kenh_trang && !this.chi_giao_net) {
          var data = await this.LAY_DANHBA_THEO_THUEBAOID(this.BanCheoData.Tinh_TC.tinhthicong_id, parseInt(this.thuebao_tc_id), parseInt(this.modelCbo.cboDichVuVT), 0, 2)
          if (data.length === 0) {
            this.$root.$toast.error('Không tìm thấy thuê bao ứng với dịch vụ ' + this.modelCbo.cboDichVuVT + ' ở tỉnh thi công.')
            return false
          }
          rowHDTBBC = this.selectSomeProperties(dsCol, data[0])
          console.log('🚀 ~ file: index.vue:889 ~ TaoDuLieu_HDTBBC ~ rowHDTBBC:', rowHDTBBC)
        } else {
          rowHDTBBC = { ...this.dsHDTB[0] }
          rowHDTBBC.THUEBAO_ID = null
          rowHDTBBC.DONVI_ID = null
        }
        rowHDTBBC.HDTB_ID = this.hdtb_id
        rowHDTBBC.HDKH_ID = this.hdkh_id
        rowHDTBBC.TTHD_ID = 1
        rowHDTBBC.QUYTRINH_ID = this.quytrinh_tc_tn
        rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn
        rowHDTBBC.DOITUONG_ID = this.thongso_tc.doituong_id
        rowHDTBBC.DAUCUOI_ID = null
        rowHDTBBC.TEN_TB = this.StringToString(this.modelInput.txtTenTB)
        rowHDTBBC.DIACHI_TB = this.StringToString(this.modelInput.txtDiaChiTB)

        rowHDTBBC.DIACHI_LD = this.StringToString(this.modelInput.txtDiaChiLD)
        if (this.modelInput.txtNgaySinhTB !== '') {
          rowHDTBBC.NGAY_SN = (this.modelInput.txtNgaySinhTB)
        }

        rowHDTBBC.KIEULD_ID = this.thongso_tc.kieuld_id
        rowHDTBBC.MUCUOCTB_ID = parseInt(this.thongso_tc.muccuoctb_id)
        rowHDTBBC.MUCUOC_TB = parseInt(await this.MAP_ID_THEO_TINH(this.BanCheoData.Tinh_TC.tinhthicong_id, 'cuoc_tb', 'muccuoc_tb', 'where mucuoctb_id = ' + this.thongso_tc.muccuoctb_id))
        // rowHDTBBC.MUCUOC_TB = parseInt(await this.MAP_ID_THEO_TINH(this.BanCheoData.Tinh_TC.tinhthicong_id, 'cuoc_tb', 'muccuoc_tb', 'erehw mucuoctb_id = ' + this.thongso_tc.muccuoctb_id))

        rowHDTBBC.NGUOI_CN = this.$root.token.getUserName()
        rowHDTBBC.GHICHU = this.StringToString(this.modelInput.txtGhiChuTB)

        if (this.dichvuvt_id === Enum.DichVuVienThong.TSL || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM) {
          // let donvi_dau_id = parseInt(await this.MAP_ID_THEO_TINH(this.tinh_dau.tinhthicong_id, 'donvi_dau_id', 'db_thuebao', 'where thuebao_id = ' + rowHDTBBC.THUEBAO_ID))
          let donvi_dau_id = parseInt(await this.MAP_ID_THEO_TINH(this.tinh_dau.tinhthicong_id, 'donvi_dau_id', 'db_thuebao', 'erehw thuebao_id = ' + rowHDTBBC.THUEBAO_ID))
          rowHDTBBC.DONVI_DAU_ID = donvi_dau_id
          let donvi_cuoi_id = parseInt(await this.MAP_ID_THEO_TINH(this.tinh_cuoi.tinhthicong_id, 'donvi_cuoi_id', 'db_thuebao', 'where thuebao_id = ' + rowHDTBBC.THUEBAO_ID))
          // let donvi_cuoi_id = parseInt(await this.MAP_ID_THEO_TINH(this.tinh_cuoi.tinhthicong_id, 'donvi_cuoi_id', 'db_thuebao', 'erehw thuebao_id = ' + rowHDTBBC.THUEBAO_ID))
          rowHDTBBC.DONVI_CUOI_ID = donvi_cuoi_id
        }

        if (this.dichvuvt_id === Enum.DichVuVienThong.DI_DONG || this.dichvuvt_id === Enum.DichVuVienThong.ADSL ||
                this.dichvuvt_id === Enum.DichVuVienThong.IMS || this.dichvuvt_id === Enum.DichVuVienThong.CO_DINH ||
                this.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || this.dichvuvt_id === Enum.DichVuVienThong.TSL ||
                this.dichvuvt_id === Enum.DichVuVienThong.METRONET || this.dichvuvt_id === Enum.DichVuVienThong.TRUNGTAM_DULIEU ||
                this.dichvuvt_id === Enum.DichVuVienThong.DICHVU_CNTT || this.dichvuvt_id === Enum.DichVuVienThong.ANTOAN_BAOMAT_TT ||
                this.dichvuvt_id === Enum.DichVuVienThong.GPHONE || this.dichvuvt_id === Enum.DichVuVienThong.HOINGHI_TRUYENHINH) {
          rowHDTBBC.STATUS = '0'
        }

        if (this.loaitb_id === Enum.LoaiHinhTB.ISDN2B_CD || this.loaitb_id === Enum.LoaiHinhTB.ISDN2B_CQ ||
                this.loaitb_id === Enum.LoaiHinhTB.ISDN30B_CD || this.loaitb_id === Enum.LoaiHinhTB.ISDN30B_CQ ||
                this.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_2M || this.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_THUONG ||
                this.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_TUONGTU) {
          if (this.loaisochinh === 0) {
            if (this.hdtb_id_cha > 0) { rowHDTBBC.HDTB_CHA_ID = this.hdtb_id_cha }
          }
        }
        if (parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.MEGAWAN ||
                parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.METRONET ||
                parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.NOIBO_DADIEM) {
          if (this.thuebao_tc_id > 0) {
            let tb_cha_id = parseInt(await this.MAP_ID_THEO_TINH(this.BanCheoData.Tinh_TC.tinhthicong_id, 'thuebao_cha_id', 'db_thuebao', 'where thuebao_id = ' + this.thuebao_tc_id))
            // let tb_cha_id = parseInt(await this.MAP_ID_THEO_TINH(this.BanCheoData.Tinh_TC.tinhthicong_id, 'thuebao_cha_id', 'db_thuebao', 'erehw thuebao_id = ' + this.thuebao_tc_id))
            if (tb_cha_id !== -1) { rowHDTBBC.THUEBAO_CHA_ID = tb_cha_id }
          }
        }
        if (this.modelCbo.cboGoiKT > 0) { rowHDTBBC.GOIKT_ID = parseInt(this.modelCbo.cboGoiKT) }

        delete rowHDTBBC.PHANVUNG_ID
        console.log('rowHDTBBC', rowHDTBBC)
        this.dsHDTBBC.push(rowHDTBBC)
        return true
      } catch (error) {
        console.log('🚀 ~ file: index.vue:962 ~ TaoDuLieu_HDTBBC ~ error:', error)
        return false
      }
    },
    async TaoDuLieu_DKDVBC () {
      this.dsDKBCDVGT = []
      console.log('🚀 ~ file: index.vue:979 ~ TaoDuLieu_DKDVBC ~ this.m_dsThueBao_DichVu.dsdvgt_tc:', this.m_dsThueBao_DichVu.dsdvgt_tc)
      if (this.m_dsThueBao_DichVu.dsdvgt_tc.length < 2) {
        return
      }
      let ds = this.m_dsThueBao_DichVu.dsdvgt_tc[1]
      if (ds.length > 0) {
        for (let index = 0; index < ds.length; index++) {
          const dr = ds[index]
          console.log('🚀 ~ file: index.vue:982 ~ TaoDuLieu_DKDVBC ~ dr:', dr)
          let kieu_dk_dvgt
          if (dr.dangKy.toString() === '1') { kieu_dk_dvgt = Enum.KieuCaiDatDVGT.KIEU_CD_THEM_MOI_DVGT } else if (dr.huy.toString() === '1') { kieu_dk_dvgt = Enum.KieuCaiDatDVGT.KIEU_CD_HUY_DVGT } else if (dr.sua.toString() === '1') { kieu_dk_dvgt = Enum.KieuCaiDatDVGT.KIEU_CD_SUA_DVGT } else { continue }
          let rowDKBCDVGT = {}
          rowDKBCDVGT.HDTB_ID = this.hdtb_id
          rowDKBCDVGT.NGAY_YC = DateTimeLib.toDateDisplay(this.modelDateTime.dtpNgayYC)
          rowDKBCDVGT.DICHVUGT_ID = Number(dr.dichVuGtId)
          rowDKBCDVGT.NOIDUNG = dr.noiDung
          rowDKBCDVGT.KIEU_YC = kieu_dk_dvgt
          rowDKBCDVGT.TTTH = 1
          this.dsDKBCDVGT.push(rowDKBCDVGT)
        }
      }
    },
    async TaoDuLieuBC (themmoi) {
      try {
        let kq = true
        if (this.dichvuvt_id === 9) {
          this.dsHDBC = []
          this.dsHDTBBC = []
          kq = await this.TaoDuLieu_HDTBBC_TSL(themmoi, 1)
          kq = await this.TaoDuLieu_HDTBBC_TSL(themmoi, 2)
        } else { kq = await this.TaoDuLieu_HDTBBC(themmoi) }
        if (!kq) {
          return false
        }
        await this.TaoDuLieu_DKDVBC()
        this.dsHDBCCD = []
        this.dsHDBCADSL = []
        this.dsHDBCTSL = []
        this.dsHDBCMGWAN = []
        this.dsHDBCIMS = []
        let dsCol = ['HDTB_ID', 'MA_VPN', 'TOCDO_ID', 'LOAIMD_ID', 'THIETBIDC_ID', 'LOAIKENH_ID', 'CHUQUAN_ID', 'THOIHAN_ID', 'TRANGBI_ID', 'HUONG_KN', 'CULY', 'TEN_TBI', 'PE_ID', 'PORT_PE', 'SLOT_PE', 'WAN_IP', 'LAN_IP', 'MATB_TN', 'MADOICAP', 'MA_LT', 'CUOC_TC', 'CUOC_TK', 'TUYENCAP', 'PORT_ID', 'VCI_VPI_ID', 'GHICHU', 'LIENTU', 'DOICAP_P', 'HOPCAP_P', 'TUCAP_P', 'LOAICAP_ID', 'CAP_GOC', 'CAP_P', 'DOICAP_GOC', 'TUCAP_GOC', 'DSLAM', 'VRF', 'LINK', 'SERIAL', 'CUOC_HT', 'ROUTE', 'DOITAC_ID', 'CUOC_TBI', 'SVLAN', 'CVLAN', 'CUOC_TKDT', 'CUOC_TCDT', 'CAP_ID', 'KETCUOI_ID', 'DOICAP', 'VITRI', 'DOICAP_2', 'VITRI_2', 'KP_AO', 'MA_CV', 'NGAY_CV', 'NGAY_YCHT', 'MAHD_DT', 'TOCDO_PIR_ID', 'MA_PL', 'SITE_ID', 'LOAINODE_ID', 'DIACHI_KN', 'GIUKENH', 'NHAN_HD', 'MA_CRM', 'ID_ERP', 'MAKENH_VTN', 'TINH_KN_ID', 'MA_VNPT', 'SL_IP', 'CUOC_IP', 'TOCDO_NIX', 'CUOC_NIX', 'TOCDO_ISP', 'CUOC_ISP', 'TOANHA_ID', 'CONGNGHE_ID', 'CHUQUANLD_ID', 'PHANLOAI_ID', 'PHANTACH_TD', 'MA_BSS', 'TINHCUOC', 'TOCDO_POP', 'DIEM_POP', 'MHKN_ID', 'GDKN_POP_ID', 'GDKN_KH_ID', 'TEN_TRUNK_NNI', 'CE_IP', 'SO_AS', 'CAUHINH_DT_ID', 'KIEU_DUPLEX_ID', 'KIEUKN_ID', 'MTU_SIZE', 'HQOS', 'COS', 'VLAN_TRANSPARENT', 'PORT_POP', 'IP_POP', 'CUOC_CIR', 'CUOC_PIR', 'DSLAM_TINH_ID', 'THIETBI_DICH', 'TINHKHAC', 'VFI', 'SD_TBI_NET', 'DIACHI_POP_SUB']
        switch (this.dichvuvt_id) {
          case Enum.DichVuVienThong.CO_DINH:
            let CD = await this.$refs.ThongTinThueBao.$refs.refCD.TaoDuLieu_HDBC_CD(themmoi)
            this.dsHDBCCD = CD.dsHDBCCD
            delete this.dsHDBCCD[0].SONE
            delete this.dsHDBCCD[0].HOST_DONVI_ID
            delete this.dsHDBCCD[0].DSLOAITB_ID
            delete this.dsHDBCCD[0].CUOC_DOITAC
            delete this.dsHDBCCD[0].CUOC_BD 
            delete this.dsHDBCCD[0].HOPCAP_X 
            delete this.dsHDBCCD[0].THUEBAO_ID 
            delete this.dsHDBCCD[0].TRANGTHAISO_ID 
            delete this.dsHDBCCD[0].VETINH_ID          
            delete this.dsHDBCCD[0].DOITAC_ID
            delete this.dsHDBCCD[0].NGAY_INS
            delete this.dsHDBCCD[0].DONVI_ID
            delete this.dsHDBCCD[0].DOITAC_ID
            delete this.dsHDBCCD[0].DOICAP_X
            delete this.dsHDBCCD[0].SO_TAT
            delete this.dsHDBCCD[0].TINHKHAC         
            
            

            console.log('this.dsHDBCCD', this.dsHDBCCD)
            break
          case Enum.DichVuVienThong.IMS:
            let IMS = await this.$refs.ThongTinThueBao.$refs.refIMS.TaoDuLieu_HDBC_IMS(themmoi)
            this.dsHDBCIMS = IMS.dsHDBCIMS
            break
          case Enum.DichVuVienThong.ADSL:
            let ADSL = await this.$refs.ThongTinThueBao.$refs.refAdsl.TaoDuLieu_HDBC_ADSL(themmoi)
            this.dsHDBCADSL = ADSL.dsHDBCADSL
            break
          case Enum.DichVuVienThong.MEGA_EYES:
            this.$refs.ThongTinThueBao.$refs.refAdsl.TaoDuLieu_HDBC_ADSL(themmoi)
            break
          case Enum.DichVuVienThong.MEGAWAN:
            let MEGAWAN = await this.$refs.ThongTinThueBao.$refs.refWan.TaoDuLieu_HDBC_MGWAN(themmoi)
            let t1 = this.selectSomeProperties(dsCol, MEGAWAN.dsHDBCMGWAN[0])
            this.dsHDBCMGWAN.push(t1)
            break
          case Enum.DichVuVienThong.METRONET:
          case Enum.DichVuVienThong.NOIBO_DADIEM:
            let METRONET = await this.$refs.ThongTinThueBao.$refs.refWan.TaoDuLieu_HDBC_MGWAN(themmoi)
            let t2 = this.selectSomeProperties(dsCol, METRONET.dsHDBCMGWAN[0])
            this.dsHDBCMGWAN.push(t2)
            break
          case Enum.DichVuVienThong.TSL:
          case Enum.DichVuVienThong.NOIBO_2DIEM:
            this.dsHDBCTSL = []
            let ds1 = await this.$refs.ThongTinThueBao.$refs.refTSL.TaoDuLieu_HDBC_TSL(themmoi, 1, this.dsHDTB_TSL)
            this.dsHDBCTSL.push(...ds1.dsHDBCTSL)
            let ds2 = await this.$refs.ThongTinThueBao.$refs.refTSL.TaoDuLieu_HDBC_TSL(themmoi, 2, this.dsHDTB_TSL)
            this.dsHDBCTSL.push(...ds2.dsHDBCTSL)
            break
        }
        return true
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 937 ~ TaoDuLieuBC ~ error', error)
        return false
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
    async CapNhat () {
      try {
        if (this.dichvuvt_id === Enum.DichVuVienThong.TSL || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM) {
          if (this.thongso_tc_dau.kieuld_id === 0 || this.thongso_tc_cuoi.kieuld_id === 0) {
            this.$root.$toast.error('Bạn chưa chọn kiểu lắp đặt thi công, Click kiểu lắp đặt để chọn')
            return false
          }
        } else
        if (this.thongso_tc.kieuld_id === 0 || this.thongso_tc.kieuld_id === null) {
          this.$root.$toast.error('Bạn chưa chọn kiểu lắp đặt thi công, Click kiểu lắp đặt để chọn')
          return false
        }
        let kt = await this.KiemTraDL_KhachHang()
        if (!kt) return false
        kt = await this.KiemTraDL_ThanhToan()
        if (!kt) return false
        kt = await this.KiemTraDL_ThueBao()
        if (!kt) return false
        kt = await this.KiemTraDuLieuNhap()
        if (!kt) return false

        this.tocdotn_id = 0
        this.muccuoctn_id = 0
        if (this.dichvuvt_id === Enum.DichVuVienThong.ADSL) {
          this.tocdotn_id = parseInt(this.$refs.ThongTinThueBao.$refs.refAdsl.cboGoiCuocAdsl.Value)
          if (this.loaitb_id === Enum.LoaiHinhTB.INTERNET_ADSL || this.loaitb_id === Enum.LoaiHinhTB.INTERNET_FTTH ||
                          this.loaitb_id === Enum.LoaiHinhTB.WIFI_FIBER) { this.muccuoctn_id = parseInt(this.$refs.ThongTinThueBao.$refs.refAdsl.cboMucCuoc.Value) }
        } else if (this.dichvuvt_id === Enum.DichVuVienThong.MEGA_EYES) {
          this.tocdotn_id = parseInt(this.$refs.ThongTinThueBao.$refs.refAdsl.cboGoiCuocAdsl.Value)
        } else if (this.dichvuvt_id === Enum.DichVuVienThong.TSL || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM) {
          this.tocdotn_id = parseInt(this.$refs.ThongTinThueBao.$refs.refTSL.cboTocDoKenhTSL.Value)
        } else if (this.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || this.dichvuvt_id === Enum.DichVuVienThong.METRONET || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_DADIEM) {
          this.tocdotn_id = parseInt(this.$refs.ThongTinThueBao.$refs.refWan.cboTocDo.Value)
        }
        if (this.buttonState.tsbtnNhapMoi === false) {
          let KIEMTRA_DB_KHI_THEMMOI = await this.KIEMTRA_DB_KHI_THEMMOI(this.modelInput.txtMaTB, 0, this.thuebao_id, this.dichvuvt_id)
          if (!KIEMTRA_DB_KHI_THEMMOI) {
            this.$root.$toast.error('Thuê bao không tồn tại trong danh bạ, không cập nhật được')
            return false
          }
          if (this.loaitb_id === Enum.LoaiHinhTB.ISDN2B_CD || this.loaitb_id === Enum.LoaiHinhTB.ISDN2B_CQ ||
                        this.loaitb_id === Enum.LoaiHinhTB.ISDN30B_CD || this.loaitb_id === Enum.LoaiHinhTB.ISDN30B_CQ ||
                        this.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_2M || this.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_THUONG ||
                        this.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_TUONGTU) {
            console.log('🚀 ~ file: index.vue:1105 ~ CapNhat ~ this.ds_isdn:', this.ds_isdn)
            if (this.isdn_sochinh && this.ds_isdn) {
              for (let index = 0; index < this.ds_isdn.length; index++) {
                const dr = this.ds_isdn[index]
                let KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(dr.ma_tb, 0, parseInt(this.modelCbo.cboDichVuVT), Enum.LoaiHopDong.DI_CHUYEN)
                if (KIEMTRA_MA_TB_LAP_HOPDONG_KHAC) {
                  return false
                }
              }
            }
          }
          let KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.modelInput.txtMaTB, 0, parseInt(this.modelCbo.cboDichVuVT), Enum.LoaiHopDong.DI_CHUYEN)
          if (KIEMTRA_MA_TB_LAP_HOPDONG_KHAC) {
            return false
          }

          if (this.dichvuvt_id === Enum.DichVuVienThong.CO_DINH || this.dichvuvt_id === Enum.DichVuVienThong.IMS || this.dichvuvt_id === Enum.DichVuVienThong.GPHONE) {
            if (this.modelInput.txtMaTB !== this.modelInput.txtMaTBMoi) {
              KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.modelInput.txtMaTBMoi, 0, this.dichvuvt_id, Enum.LoaiHopDong.DI_CHUYEN)
              if (KIEMTRA_MA_TB_LAP_HOPDONG_KHAC) return false
            }
          }
          let dset = await this.LayHuongGiao_TiepNhan_V2(Enum.LoaiHopDong.DI_CHUYEN, parseInt(this.modelCbo.cboLoaihinhTB), this.tocdotn_id, this.muccuoctn_id, parseInt(this.modelCbo.cboKieuLD), this.vloaikenh_id, this.dichvuvt_id)
          let huonggiao_tn = 0
          this.luong_id = 0
          if (dset && dset.length <= 0) {
            this.$root.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
            return false
          }
          huonggiao_tn = parseInt(dset[0]['huonggiao_id'])
          this.quytrinh_id = parseInt(dset[0]['quytrinh_id'])
          this.luong_id = parseInt(dset[0]['luong_id'])
          let rs_qt = []

          if (this.dichvuvt_id === Enum.DichVuVienThong.TSL || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM) {
            console.log('🚀 ~ file: index.vue:1143 ~ CapNhat ~ this.thongso_tc_dau:', this.thongso_tc_dau)
            console.log('🚀 ~ file: index.vue:1143 ~ CapNhat ~ this.thongso_tc_cuoi:', this.thongso_tc_cuoi)
            let muccuoc_id = isNaN(this.thongso_tc_dau.muccuoc_id) ? 0 : parseInt(this.thongso_tc_dau.muccuoc_id)
            //rs_qt = await this.LayHuongGiao_TiepNhan(this.tinh_dau.tinhthicong_id, Enum.LoaiHopDong.DI_CHUYEN, parseInt(this.modelCbo.cboLoaihinhTB), parseInt(this.thongso_tc_dau.tocdo_id), muccuoc_id, parseInt(this.thongso_tc_dau.kieuld_id))
            rs_qt = await this.LayHuongGiao_TiepNhan_new(this.tinh_dau.tinhthicong_id, Enum.LoaiHopDong.DI_CHUYEN, parseInt(this.modelCbo.cboLoaihinhTB), parseInt(this.thongso_tc_dau.tocdo_id), muccuoc_id, parseInt(this.thongso_tc_dau.kieuld_id), this.vloaikenh_id, this.dichvuvt_id)
            if (rs_qt && rs_qt.length <= 0) {
              this.$root.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý')
              return false
            }
            this.quytrinh_tc_tn_dau = parseInt(rs_qt[0]['quytrinh_id'])
            this.huonggiao_tc_tn_dau = parseInt(rs_qt[0]['huonggiao_id'])
            muccuoc_id = isNaN(this.thongso_tc_cuoi.muccuoc_id) ? 0 : parseInt(this.thongso_tc_cuoi.muccuoc_id)
            // rs_qt = await this.LayHuongGiao_TiepNhan(this.tinh_cuoi.tinhthicong_id, Enum.LoaiHopDong.DI_CHUYEN, parseInt(this.modelCbo.cboLoaihinhTB),
              // parseInt(this.thongso_tc_cuoi.tocdo_id), muccuoc_id, parseInt(this.thongso_tc_cuoi.kieuld_id))
            rs_qt = await this.LayHuongGiao_TiepNhan_new(this.tinh_cuoi.tinhthicong_id, Enum.LoaiHopDong.DI_CHUYEN, parseInt(this.modelCbo.cboLoaihinhTB), 
              parseInt(this.thongso_tc_cuoi.tocdo_id), muccuoc_id, parseInt(this.thongso_tc_cuoi.kieuld_id), this.vloaikenh_id, this.dichvuvt_id)
            if (rs_qt && rs_qt.length <= 0) {
              this.$root.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý')
              return false
            }
            this.quytrinh_tc_tn_cuoi = parseInt(rs_qt[0]['quytrinh_id'])
            this.huonggiao_tc_tn_cuoi = parseInt(rs_qt[0]['huonggiao_id'])
          } else {
            console.log('🚀 ~ file: index.vue ~ line 1042 ~ CapNhat ~ this.thongso_tc', this.thongso_tc)
            rs_qt = await this.LayHuongGiao_TiepNhan(this.BanCheoData.Tinh_TC.tinhthicong_id, Enum.LoaiHopDong.DI_CHUYEN, parseInt(this.modelCbo.cboLoaihinhTB),
              parseInt(this.thongso_tc.tocdo_id), this.thongso_tc.muccuoc_id === null ? 0 : parseInt(this.thongso_tc.muccuoc_id), parseInt(this.thongso_tc.kieuld_id))
            if (rs_qt && rs_qt.length <= 0) {
              this.$root.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý')
              return false
            }
            this.quytrinh_tc_tn = 0
            this.huonggiao_tc_tn = 0
            this.quytrinh_tc_tn = parseInt(rs_qt[0]['quytrinh_id'])
            this.huonggiao_tc_tn = parseInt(rs_qt[0]['huonggiao_id'])
          }
          let kqTaoDuLieu = false
          kqTaoDuLieu = await this.TaoDuLieu(true)
          if (!kqTaoDuLieu) {
            this.$root.$toast.error('Lỗi tạo dữ liệu hợp đồng thuê bao')
            return false
          }
          kqTaoDuLieu = await this.TaoDuLieuBC(true)
          if (!kqTaoDuLieu) {
            this.$root.$toast.error('Lỗi tạo dữ liệu hợp đồng thuê bao bán chéo')
            return false
          }
          if (this.loi_sinh_magd) {
            this.loi_sinh_magd = false
            return false
          }
          if (this.kt_adsl === false) { return false }
          let chk = await this.dichchuyen_ThemMoi_HopDong_BanCheo(this.dsHDKH, this.dsHDTT, this.dsHDTB, this.dsHDTBDV, this.dsHDTB_CD, this.dsHDTB_ADSL, this.dsHDTB_TSL, this.dsDiaChi, this.dsDiaChiHDKH,
            this.dsDiaChiHDTT, this.dsDiaChiHDTB, this.dsDK_DVGT, this.dsDK_DVK, this.dsHDTB_MEWAN, this.dsHDTB_GP, this.dsTTTT, this.dsCTTHD, this.dsHDTB_IMS, this.dsKM_HDTB,
            huonggiao_tn, this.dsHDTB_KV, this.dsHDTT_NV, this.BanCheoData.Tinh_TC.tinhthicong_id, this.kenh_trang === true ? 1 : 0, this.dsHDBC, this.dsHDTBBC, this.dsHDBCMGWAN, this.dsHDBCADSL, this.dsHDBCCD, this.dsDKBCDVGT, this.dsHDBCTSL, this.dsHDBCIMS, this.dsLYDOHUYBC, this.dsDiaChi_BanCheo)
          if (!chk) {
            this.$root.$toast.error('Lỗi thêm hợp đồng dịch chuyển bán chéo')
            return false
          }
          // let chk = await this.dichchuyen_ThemMoi_HopDong(this.dsHDKH, this.dsHDTT, this.dsHDTB, this.dsHDTBDV, this.dsHDTB_CD, this.dsHDTB_ADSL, this.dsHDTB_TSL, this.dsDiaChi, this.dsDiaChiHDKH,
          //   this.dsDiaChiHDTT, this.dsDiaChiHDTB, this.dsDK_DVGT, this.dsDK_DVK, this.dsHDTB_MEWAN, this.dsHDTB_GP, this.dsTTTT, this.dsCTTHD, this.dsHDTB_IMS, this.dsKM_HDTB,
          //   huonggiao_tn, this.dsHDTB_KV, this.dsHDTT_NV)
          // if (!chk) {
          //   this.$root.$toast.error('Lỗi thêm hợp đồng thuê bao')
          //   return false
          // }
          // if (this.BanCheoData.Tinh_TC.tinhthicong_id > 0 || this.kenh_trang) {
          //   chk = await this.hdbancheo_themmoi_HDBANCHEO_BIENDONG_V3(this.dsHDBC, this.dsHDTBBC, this.dsHDBCMGWAN, this.dsHDBCADSL, this.dsHDBCCD, this.dsDKBCDVGT, this.dsHDBCTSL, this.dsHDBCIMS, this.dsLYDOHUYBC, this.dsDiaChi_BanCheo)
          //   if (!chk) {
          //     this.$root.$toast.error('Lỗi thêm hợp đồng thuê bao bán chéo')
          //     return false
          //   }
          // }
          await this.CapNhat_DiaChiBC()
          let donvi_id = this.$root.token.getDonViID()
          if (this.modelInput.txtMaTBMoi !== '') {
            await this.CAPNHAT_KHOSO(this.modelInput.txtMaTBMoi, '', this.dichvuvt_id, donvi_id)
          } else { await this.CAPNHAT_KHOSO(this.modelInput.txtMaTB, '', this.dichvuvt_id, donvi_id) }
          this.ma_tb = this.modelInput.txtMaTB
          if (this.tudong_ganthem_hdtb_dv) { await this.fn_tudong_ganthem_hdtb_dv(this.hdtb_id) }

          if (this.loaitb_id === Enum.LoaiHinhTB.ISDN2B_CD || this.loaitb_id === Enum.LoaiHinhTB.ISDN2B_CQ ||
                        this.loaitb_id === Enum.LoaiHinhTB.ISDN30B_CD || this.loaitb_id === Enum.LoaiHinhTB.ISDN30B_CQ ||
                        this.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_2M || this.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_THUONG ||
                        this.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_TUONGTU) {
          }
          if (this.bDuLieuKS) {
            await this.CAPNHAT_TRANGTHAI_HD(this.iHdtbID, Enum.TrangThaiHD.DA_LAY_DU_LIEU)
            this.modelCkeckBox.cboDichVuVT = true
            this.bDuLieuKS = false
          }
          await this.Them_MyTV_vao_goi_giadinh()

          await this.SetButton(3)
          let ds_hd = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id)
          await this.HienThiDanhSacHDTB(ds_hd)
          ds_hd = await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.hdkh_id)
          await this.HienThiDanhSacHDTT(ds_hd)
          await this.HienThiDS_HDTT_CBO(this.hdkh_id)
        } else {
          if (this.modelInput.txtMaTBMoi === '') {
            let kiemtra = await this.KiemTraTBTrongHD(this.modelInput.txtMaTB, this.modelInput.lvwThueBao, 3)
            if (!kiemtra) {
              this.$root.$toast.error('Không có mã thuê bao ' + this.modelInput.txtMaTB + ' trong HĐ/PL. Hãy nhập lại mã thuê bao')
              return
            }
          }
          let ds = await this.LayHuongGiao_TiepNhan_V2(Enum.LoaiHopDong.DI_CHUYEN, parseInt(this.modelCbo.cboLoaihinhTB), this.tocdotn_id, this.muccuoctn_id, parseInt(this.modelCbo.cboKieuLD), this.vloaikenh_id, parseInt(this.modelCbo.cboDichVuVT))
          let huonggiao_tn = 0
          this.luong_id = 0
          if (ds !== null && ds.length <= 0) {
            this.$root.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
            return
          }
          let kiemtra = await this.KIEMTRA_DB_KHI_THEMMOI(this.modelInput.txtMaTB, 0, this.thuebao_id, this.dichvuvt_id)
          if (!kiemtra) {
            this.$root.$toast.error('Thuê bao không tồn tại trong danh bạ, không cập nhật được ')
            return
          }
          huonggiao_tn = parseInt(ds[0]['huonggiao_id'])
          this.quytrinh_id = parseInt(ds[0]['quytrinh_id'])
          this.luong_id = parseInt(ds[0]['luong_id'])
          let rs_qt = await this.LayHuongGiao_TiepNhan(this.BanCheoData.Tinh_TC.tinhthicong_id, Enum.LoaiHopDong.DI_CHUYEN, parseInt(this.modelCbo.cboLoaihinhTB),
            parseInt(this.thongso_tc.tocdo_id), parseInt(this.thongso_tc.muccuoc_id), parseInt(this.thongso_tc.kieuld_id))
          if (rs_qt.length <= 0) {
            this.$root.$toast.warning('Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý')
            return
          }
          this.quytrinh_tc_tn = 0
          this.huonggiao_tc_tn = 0
          this.quytrinh_tc_tn = parseInt(rs_qt[0]['quytrinh_id'])
          this.huonggiao_tc_tn = parseInt(rs_qt[0]['huonggiao_id'])
          await this.TaoDuLieu(false)
          await this.TaoDuLieuBC(false)
          if (this.kt_adsl === false) { return }
          kiemtra = await this.dichchuyen_CapNhat_HopDong(this.hdtb_id, this.hdtt_id, this.dsHDKH, this.dsHDTT, this.dsHDTB, this.dsHDTBDV, this.dsHDTB_CD, this.dsHDTB_ADSL, this.dsHDTB_TSL, this.dsDiaChi,
            this.dsDiaChiHDKH, this.dsDiaChiHDTT, this.dsDiaChiHDTB, this.dsDK_DVGT, this.dsDK_DVK, this.dsHDTB_MEWAN, this.dsHDTB_GP,
            this.dsTTTT, this.dsCTTHD, this.dsHDTB_IMS, this.dsKM_HDTB, this.dsHDTB_KV, this.dsHDTT_NV)
          if (!kiemtra) {
            this.$root.$toast.error('Lỗi cập nhật hợp đồng thuê bao bán chéo')
            return
          }
          kiemtra = await this.hdbancheo_capnhat_HDBANCHEO_BIENDONG(this.dsHDBC, this.dsHDTBBC, this.dsHDBCMGWAN, this.dsHDBCADSL, this.dsHDBCCD, this.dsDKBCDVGT, this.dsHDBCTSL, this.dsHDBCIMS, this.dsDiaChi_BanCheo)
          if (!kiemtra) {
            this.$root.$toast.error('Lỗi cập nhật hợp đồng thuê bao bán chéo')
            return
          }
          await this.CapNhat_DiaChiBC()
          await this.Update_huonggiao_tiepnhan(this.hdtb_id, huonggiao_tn)
          if (this.modelInput.txtMaTBMoi !== '') { await this.CAPNHAT_KHOSO(this.modelInput.txtMaTBMoi, '', this.dichvuvt_id, this.$root.token.getDonViID()) } else { await this.CAPNHAT_KHOSO(this.modelInput.txtMaTB, '', this.dichvuvt_id, this.$root.token.getDonViID()) }
          this.ma_tb = this.modelInput.txtMaTB

          if (this.tudong_ganthem_hdtb_dv) { await this.fn_tudong_ganthem_hdtb_dv(this.hdtb_id) }
          await this.SetButton(3)
          let ds_hd = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id)
          await this.HienThiDanhSacHDTB(ds_hd)
          ds_hd = await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.hdkh_id)
          await this.HienThiDanhSacHDTT(ds_hd)
          await this.HienThiDS_HDTT_CBO(this.hdkh_id)
          if (this.buttonState.tsbtnNhapMoi === false) {

          } else {

          }
        }
        this.ma_tb = this.modelInput.txtMaTB
        this.kieuld_ds = parseInt(this.modelCbo.cboKieuLD)
        this.$root.$toast.success('Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!')
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 622 ~ CapNhat ~ error', error)
      }
    },
    async Update_huonggiao_tiepnhan (hdtb_id, huonggiao_id) {
      try {
        let res = await this.$root.context.post('/web-hopdong/chuyendich/update_huonggiao_tiepnhan', {hdtb_id, huonggiao_id})
        console.log('🚀 ~ file: index.vue ~ line 1172 ~ Update_huonggiao_tiepnhan ~ res', res)
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1173 ~ Update_huonggiao_tiepnhan ~ error', error)
      }
    },
    async dichchuyen_CapNhat_HopDong (
      hdtb_id, hdtt_id, dsHDKH, dsHDTT, dsHDTB, dsHDTBDV, dsHDTB_CD, dsHDTB_ADSL, dsHDTB_TSL, dsDiaChi,
      dsDiaChiHDKH, dsDiaChiHDTT, dsDiaChiHDTB, dsDK_DVGT, dsDK_DVK, dsHDTB_MEWAN, dsHDTB_GP, dsTTTT,
      dsCTTHD, dsHDTB_IMS, dsKM_HDTB, dsHDTB_KV, dsHDTT_NV
    ) {
      try {
        dsHDTB_MEWAN.forEach(object => {
          this.$delete(object, 'PHANVUNG_ID')
          this.$delete(object, 'THUEBAO_ID')
        })
        let data = {
          HDTB_ID: hdtb_id,
          HDTT_ID: hdtt_id,
          dsHDKH: JSON.stringify(dsHDKH),
          dsHDTT: JSON.stringify(dsHDTT),
          dsHDTB: JSON.stringify(dsHDTB),
          dsHDTBDV: JSON.stringify(dsHDTBDV),
          dsHDTB_CD: JSON.stringify(dsHDTB_CD),
          dsHDTB_ADSL: JSON.stringify(dsHDTB_ADSL),
          dsHDTB_TSL: JSON.stringify(dsHDTB_TSL),
          dsDiaChi: JSON.stringify(dsDiaChi),
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
        console.log('🚀 ~ file: index.vue ~ line 1221 ~ data', data)
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/capnhat_hopdong', data)
        console.log('🚀 ~ file: index.vue ~ line 1640 ~ rs', rs)
        return true
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1225 ~ error', error)
        return false
      }
    },
    async hdbancheo_capnhat_HDBANCHEO_BIENDONG (dsHDBC, dsHDTBBC, dsHDBCMGWAN, dsHDBCADSL, dsHDBCCD, dsDKBCDVGT, dsHDBCTSL, dsHDBCIMS, dsDiaChi_BanCheo) {
      try {
        let data = {
          dsHDBC: (dsHDBC),
          dsHDTBBC: (dsHDTBBC),
          dsHDBCMGWAN: (dsHDBCMGWAN),
          dsHDBCADSL: (dsHDBCADSL),
          dsHDBCCD: (dsHDBCCD),
          dsDKBCDVGT: (dsDKBCDVGT),
          dsHDBCTSL: (dsHDBCTSL),
          dsHDBCIMS: (dsHDBCIMS),
          dsDiaChi_BanCheo: (dsDiaChi_BanCheo)
        }
        console.log('🚀 ~ file: index.vue ~ line 1246 ~ hdbancheo_capnhat_HDBANCHEO_BIENDONG ~ data', data)
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/capnhat_hdbancheo_biendong', data)
        console.log('🚀 ~ file: index.vue ~ line 1256 ~ hdbancheo_capnhat_HDBANCHEO_BIENDONG ~ rs', rs)
        return true
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1242 ~ hdbancheo_capnhat_HDBANCHEO_BIENDONG ~ error', error)
        return false
      }
    },
    // async LayHuongGiao_TiepNhan_V2 (loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id, loaikenh_id, dichvuvt_id) {
    //   try {
    //     let res = await this.$root.context.post('/web-hopdong/thanhly-thuebao-bancheo/layhuonggiao_tiepnhan_v2', {loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id, loaikenh_id, dichvuvt_id})
    //     console.log('🚀 ~ file: index.vue ~ line 1145 ~ LayHuongGiao_TiepNhan_V2 ~ res', res)
    //     return res.data === null ? [] : res.data
    //   } catch (error) {
    //     console.log('🚀 ~ file: index.vue ~ line 1146 ~ LayHuongGiao_TiepNhan_V2 ~ error', error)
    //     return []
    //   }
    // },
    KiemTraTBTrongHD (ma_tb, lvwThueBao, tt) {
      let result = false
      for (let i = 0; i < lvwThueBao.length; i++) {
        if (ma_tb.toUpperCase() === lvwThueBao[i].ma_tb.toUpperCase()) {
          result = true
          break
        } else { result = false }
      }
      return result
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
    async hdbancheo_themmoi_HDBANCHEO_BIENDONG_V3 (dsHDBC, dsHDTBBC, dsHDBCMGWAN, dsHDBCADSL, dsHDBCCD, dsDKBCDVGT, dsHDBCTSL, dsHDBCIMS, dsLYDOHUYBC, dsDiaChi_BanCheo) {
      try {
        let data = {
          dsHDBC: JSON.stringify((dsHDBC)),
          dsHDTBBC: JSON.stringify((dsHDTBBC)),
          dsHDBCMGWAN: JSON.stringify((dsHDBCMGWAN)),
          dsHDBCADSL: JSON.stringify((dsHDBCADSL)),
          dsHDBCCD: JSON.stringify((dsHDBCCD)),
          dsDKBCDVGT: JSON.stringify((dsDKBCDVGT)),
          dsHDBCTSL: JSON.stringify((dsHDBCTSL)),
          dsHDBCIMS: JSON.stringify((dsHDBCIMS)),
          dsLYDOHUYBC: JSON.stringify((dsLYDOHUYBC)),
          dsDiaChi_BanCheo: JSON.stringify((dsDiaChi_BanCheo))
        }
        let res = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/themmoi_hdbancheo_biendong_v3', data)
        console.log('🚀 ~ file: index.vue ~ line 1139 ~ hdbancheo_ ~ res', res)
        if (res.error_code === 'BSS-00000500') {
          this.$root.$toast.error(res.message)
          return false
        }
        return true
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1127 ~ hdbancheo_themmoi_HDBANCHEO_BIENDONG_V3 ~ error', error)
        return false
      }
    },
    async dichchuyen_ThemMoi_HopDongTB (dsHDTB, dsHDTBDV, dsHDTB_CD, dsHDTB_ADSL, dsHDTB_TSL, dsDiaChi, dsDiaChiHDTB, dsDK_DVGT,
      dsDK_DVK, dsHDTB_MEWAN, dsHDTB_GP, dsTTTT, dsCTTHD, dsHDTT, dsHDTB_IMS, huonggiao_tn,
      dsHDTB_KV, dsDiaChiHDTT, dsKM_HDTB) {
      try {
        dsHDTB_MEWAN.forEach(object => {
          this.$delete(object, 'THUEBAO_ID')
        })
        let data = {
          dsHDTT: JSON.stringify(this.UpperCasePropertyList(dsHDTT)),
          dsHDTB: JSON.stringify(this.UpperCasePropertyList(dsHDTB)),
          dsHDTBDV: JSON.stringify(this.UpperCasePropertyList(dsHDTBDV)),
          dsHDTB_CD: JSON.stringify(this.UpperCasePropertyList(dsHDTB_CD)),
          dsHDTB_ADSL: JSON.stringify(this.UpperCasePropertyList(dsHDTB_ADSL)),
          dsHDTB_TSL: JSON.stringify(this.UpperCasePropertyList(dsHDTB_TSL)),
          dsDiaChi: JSON.stringify(this.UpperCasePropertyList(dsDiaChi)),
          dsDiaChiHDTT: JSON.stringify(this.UpperCasePropertyList(dsDiaChiHDTT)),
          dsDiaChiHDTB: JSON.stringify(this.UpperCasePropertyList(dsDiaChiHDTB)),
          dsDK_DVGT: JSON.stringify(this.UpperCasePropertyList(dsDK_DVGT)),
          dsDK_DVK: JSON.stringify(this.UpperCasePropertyList(dsDK_DVK)),
          dsHDTB_MEWAN: JSON.stringify(this.UpperCasePropertyList(dsHDTB_MEWAN)),
          dsHDTB_GP: JSON.stringify(this.UpperCasePropertyList(dsHDTB_GP)),
          dsTTTT: JSON.stringify(this.UpperCasePropertyList(dsTTTT)),
          dsCTTHD: JSON.stringify(this.UpperCasePropertyList(dsCTTHD)),
          dsHDTB_IMS: JSON.stringify(this.UpperCasePropertyList(dsHDTB_IMS)),
          dsKM_HDTB: JSON.stringify(this.UpperCasePropertyList(dsKM_HDTB)),
          HUONGGIAO_TN: huonggiao_tn,
          dsHDTB_KV: JSON.stringify(this.UpperCasePropertyList(dsHDTB_KV))
        }
        console.log('🚀 ~ file: index.vue ~ line 1377 ~ data', data)
        let res = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/dichchuyen_themmoi_hopdongtb', data)
        console.log('🚀 ~ file: index.vue ~ line 1379 ~ res', res)
        return true
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1382 ~ error', error)
        return false
      }
    },
    async dichchuyen_ThemMoi_HopDong_BanCheo (dsHDKH, dsHDTT, dsHDTB, dsHDTBDV, dsHDTB_CD, dsHDTB_ADSL, dsHDTB_TSL, dsDiaChi, dsDiaChiHDKH,
      dsDiaChiHDTT, dsDiaChiHDTB, dsDK_DVGT, dsDK_DVK, dsHDTB_MEWAN, dsHDTB_GP, dsTTTT, dsCTTHD, dsHDTB_IMS, dsKM_HDTB,
      huonggiao_tn, dsHDTB_KV, dsHDTT_NV, TINHTHICONG_ID, KENH_TRANG, dsHDBC, dsHDTBBC, dsHDBCMGWAN, dsHDBCADSL, dsHDBCCD, dsDKBCDVGT, dsHDBCTSL, dsHDBCIMS, dsLYDOHUYBC, dsDiaChi_BanCheo) {
      try {
        dsHDTB_MEWAN.forEach(object => {
          this.$delete(object, 'THUEBAO_ID')
        })
        let data = {
          dsHDKH: JSON.stringify(this.UpperCasePropertyList(dsHDKH)),
          dsHDTT: JSON.stringify(this.UpperCasePropertyList(dsHDTT)),
          dsHDTB: JSON.stringify(this.UpperCasePropertyList(dsHDTB)),
          dsHDTBDV: JSON.stringify(this.UpperCasePropertyList(dsHDTBDV)),
          dsHDTB_CD: JSON.stringify(this.UpperCasePropertyList(dsHDTB_CD)),
          dsHDTB_ADSL: JSON.stringify(this.UpperCasePropertyList(dsHDTB_ADSL)),
          dsHDTB_TSL: JSON.stringify(this.UpperCasePropertyList(dsHDTB_TSL)),
          dsDiaChi: JSON.stringify(this.UpperCasePropertyList(dsDiaChi)),
          dsDiaChiHDKH: JSON.stringify(this.UpperCasePropertyList(dsDiaChiHDKH)),
          dsDiaChiHDTT: JSON.stringify(this.UpperCasePropertyList(dsDiaChiHDTT)),
          dsDiaChiHDTB: JSON.stringify(this.UpperCasePropertyList(dsDiaChiHDTB)),
          dsDK_DVGT: JSON.stringify(this.UpperCasePropertyList(dsDK_DVGT)),
          dsDK_DVK: JSON.stringify(this.UpperCasePropertyList(dsDK_DVK)),
          dsHDTB_MEWAN: JSON.stringify(this.UpperCasePropertyList(dsHDTB_MEWAN)),
          dsHDTB_GP: JSON.stringify(this.UpperCasePropertyList(dsHDTB_GP)),
          dsTTTT: JSON.stringify(this.UpperCasePropertyList(dsTTTT)),
          dsCTTHD: JSON.stringify(this.UpperCasePropertyList(dsCTTHD)),
          dsHDTB_IMS: JSON.stringify(this.UpperCasePropertyList(dsHDTB_IMS)),
          dsKM_HDTB: JSON.stringify(this.UpperCasePropertyList(dsKM_HDTB)),
          HUONGGIAO_TN: huonggiao_tn,
          TINHTHICONG_ID: TINHTHICONG_ID,
          KENH_TRANG: KENH_TRANG,
          dsHDTB_KV: JSON.stringify(this.UpperCasePropertyList(dsHDTB_KV)),
          dsHDTT_NV: JSON.stringify(this.UpperCasePropertyList(dsHDTT_NV)),
          dsHDBC: JSON.stringify((dsHDBC)),
          dsHDTBBC: JSON.stringify((dsHDTBBC)),
          dsHDBCMGWAN: JSON.stringify((dsHDBCMGWAN)),
          dsHDBCADSL: JSON.stringify((dsHDBCADSL)),
          dsHDBCCD: JSON.stringify((dsHDBCCD)),
          dsDKBCDVGT: JSON.stringify((dsDKBCDVGT)),
          dsHDBCTSL: JSON.stringify((dsHDBCTSL)),
          dsHDBCIMS: JSON.stringify((dsHDBCIMS)),
          dsLYDOHUYBC: JSON.stringify((dsLYDOHUYBC)),
          dsDiaChi_BanCheo: JSON.stringify((dsDiaChi_BanCheo))
        }
        let res = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/dichchuyen_themmoi_hopdong_th', data)
        console.log('🚀 ~ file: index.vue ~ line 1155 ~ res', res)
        if (res.error_code === 'BSS-00000500' || res.error_code === '1') {
          this.$root.$toast.error(res.message)
          return false
        } else {
          return true
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1129 ~ error', error)
        return false
      }
    },
    async dichchuyen_ThemMoi_HopDong (dsHDKH, dsHDTT, dsHDTB, dsHDTBDV, dsHDTB_CD, dsHDTB_ADSL, dsHDTB_TSL, dsDiaChi, dsDiaChiHDKH,
      dsDiaChiHDTT, dsDiaChiHDTB, dsDK_DVGT, dsDK_DVK, dsHDTB_MEWAN, dsHDTB_GP, dsTTTT, dsCTTHD, dsHDTB_IMS, dsKM_HDTB,
      huonggiao_tn, dsHDTB_KV, dsHDTT_NV) {
      try {
        dsHDTB_MEWAN.forEach(object => {
          this.$delete(object, 'THUEBAO_ID')
        })
        let data = {
          dsHDKH: JSON.stringify(this.UpperCasePropertyList(dsHDKH)),
          dsHDTT: JSON.stringify(this.UpperCasePropertyList(dsHDTT)),
          dsHDTB: JSON.stringify(this.UpperCasePropertyList(dsHDTB)),
          dsHDTBDV: JSON.stringify(this.UpperCasePropertyList(dsHDTBDV)),
          dsHDTB_CD: JSON.stringify(this.UpperCasePropertyList(dsHDTB_CD)),
          dsHDTB_ADSL: JSON.stringify(this.UpperCasePropertyList(dsHDTB_ADSL)),
          dsHDTB_TSL: JSON.stringify(this.UpperCasePropertyList(dsHDTB_TSL)),
          dsDiaChi: JSON.stringify(this.UpperCasePropertyList(dsDiaChi)),
          dsDiaChiHDKH: JSON.stringify(this.UpperCasePropertyList(dsDiaChiHDKH)),
          dsDiaChiHDTT: JSON.stringify(this.UpperCasePropertyList(dsDiaChiHDTT)),
          dsDiaChiHDTB: JSON.stringify(this.UpperCasePropertyList(dsDiaChiHDTB)),
          dsDK_DVGT: JSON.stringify(this.UpperCasePropertyList(dsDK_DVGT)),
          dsDK_DVK: JSON.stringify(this.UpperCasePropertyList(dsDK_DVK)),
          dsHDTB_MEWAN: JSON.stringify(this.UpperCasePropertyList(dsHDTB_MEWAN)),
          dsHDTB_GP: JSON.stringify(this.UpperCasePropertyList(dsHDTB_GP)),
          dsTTTT: JSON.stringify(this.UpperCasePropertyList(dsTTTT)),
          dsCTTHD: JSON.stringify(this.UpperCasePropertyList(dsCTTHD)),
          dsHDTB_IMS: JSON.stringify(this.UpperCasePropertyList(dsHDTB_IMS)),
          dsKM_HDTB: JSON.stringify(this.UpperCasePropertyList(dsKM_HDTB)),
          HUONGGIAO_TN: huonggiao_tn,
          dsHDTB_KV: JSON.stringify(this.UpperCasePropertyList(dsHDTB_KV)),
          dsHDTT_NV: JSON.stringify(this.UpperCasePropertyList(dsHDTT_NV))
        }
        let res = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/dichchuyen_themmoi_hopdong', data)
        console.log('🚀 ~ file: index.vue ~ line 1155 ~ res', res)
        if (res.error_code === 'BSS-00000500') {
          this.$root.$toast.error(res.message)
          return false
        } else {
          return true
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1129 ~ error', error)
        return false
      }
    },
    async  LAY_DS_HOPDONG_TT_THEO_HDKH_ID (hdkh_id) {
      console.log('🚀 ~ file: index.vue ~ line 1191 ~ LAY_DS_HOPDONG_TT_THEO_HDKH_ID ~ hdkh_id', hdkh_id)
      try {
        let res = await this.$root.context.get('/web-hopdong/chuyendich/lay_ds_hopdong_tt_theo_hdkh_id', {hdkh_id})
        console.log('🚀 ~ file: index.vue ~ line 1193 ~ LAY_DS_HOPDONG_TT_THEO_HDKH_ID ~ res', res)
        return res.data
      } catch (error) {
        return []
      }
    },
    async LAY_DS_HOPDONG_TB_THEO_HDKH_ID (hdkh_id) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_hopdong_tb_theo_hdkh_id', {hdkh_id})
        console.log('🚀 ~ file: index.vue ~ line 1201 ~ LAY_DS_HOPDONG_TB_THEO_HDKH_ID ~ rs', rs)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async HienThiDanhSacHDTB (ds) {
      try {
        let tongtienhd = 0
        let tongtientb = 0
        this.modelInput.lvwThueBao = []
        let myDataTables = ds
        if (myDataTables !== null && myDataTables.length > 0) {
          let ds_khcu = await this.LAY_BIENDONG_THEO_HDTB_ID(myDataTables[0].hdtb_id)
          if (ds_khcu !== null && ds_khcu.length > 0) {
            this.khach_hang_id = parseInt(ds_khcu[0].khachhang_id)
          }
          for (let index = 0; index < myDataTables.length; index++) {
            const myRow = myDataTables[index]
            let lvItem = {}
            tongtientb = parseInt(myRow.tongtien)
            lvItem.stt = index + 1
            lvItem.ma_tt = myRow.ma_tt
            lvItem.hdtb_id = myRow.hdtb_id
            lvItem.ma_tb = myRow.ma_tb
            lvItem.ten_tb = this.ChuanHoaTen(myRow.ten_tb)
            lvItem.diachi_ld = this.ChuanHoaTen(myRow.diachi_ld)
            lvItem.tongtientb = tongtientb
            lvItem.ten_dvvt = myRow.ten_dvvt
            lvItem.manv_tc = myRow.manv_tc
            tongtienhd = tongtienhd + tongtientb
            this.modelInput.lvwThueBao.push(lvItem)
          }
          if (myDataTables.length <= 1) {
            this.buttonState.tsbtnXoaTB = false
          } else {
            this.buttonState.tsbtnXoaTB = true
          }
        }
        this.modelInput.txtTongTienHD = tongtienhd
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1182 ~ HienThiDanhSacHDTB ~ error', error)
      }
    },
    async LAY_BIENDONG_THEO_HDTB_ID (hdtbId) {
      console.log('🚀 ~ file: index.vue ~ line 1389 ~ LAY_BIENDONG_THEO_HDTB_ID ~ hdtbId', hdtbId)
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/lay_biendong_thongtin_thuebao', {hdtbId})
        console.log('🚀 ~ file: index.vue ~ line 1391 ~ LAY_BIENDONG_THEO_HDTB_ID ~ rs', rs)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1195 ~ LAY_BIENDONG_THEO_HDTB_ID ~ error', error)
        return []
      }
    },
    async HienThiDanhSacHDTT (ds) {
      try {
        this.modelInput.lvwThanhToan = []
        let myDataTables = ds
        if (myDataTables.length > 0) {
          for (let i = 0; i < myDataTables.length; i++) {
            let myRow = myDataTables[i]
            let lvItem = {}
            lvItem.stt = i + 1
            lvItem.hdtt_id = myRow.hdtt_id
            lvItem.ma_tt = myRow.ma_tt
            lvItem.ten_tt = this.ChuanHoaTen(myRow.ten_tt)
            lvItem.diachi_tt = this.ChuanHoaTen(myRow.diachi_tt)
            lvItem.diachi_ct = this.ChuanHoaTen(myRow.diachi_ct)
            this.modelInput.lvwThanhToan.push(lvItem)
          }
          if (myDataTables.length <= 1) { this.buttonState.tsbtnXoaTT = false } else { this.buttonState.tsbtnXoaTT = true }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1250 ~ HienThiDanhSacHDTT ~ error', error)
      }
    },
    async HienThiDS_HDTT_CBO (hdkh_id) {
      console.log('🚀 ~ file: index.vue ~ line 1454 ~ HienThiDS_HDTT_CBO ~ hdkh_id', hdkh_id)
      try {
        this.modelCbo.vcboTT = await this.LAY_DS_HDTT_THEO_HDKH_ID_1(hdkh_id)
        if (this.modelCbo.vcboTT.length > 0) {
          this.modelCbo.cboTT = this.modelCbo.vcboTT[0].hdtt_id
        } else {
          this.modelCbo.cboTT = -1
        }
      } catch (e) {
        console.log('🚀 ~ file: index.vue ~ line 1382 ~ HienThiDS_HDTT_CBO ~ e', e)
      }
    },
    async LAY_DS_HDTT_THEO_HDKH_ID_1 (hdkh_id) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_hdtt_theo_hdkh_id_1', {hdkh_id})
        console.log('🚀 ~ file: index.vue ~ line 1388 ~ LAY_DS_HDTT_THEO_HDKH_ID_1 ~ rs', rs)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1390 ~ LAY_DS_HDTT_THEO_HDKH_ID_1 ~ error', error)
        return []
      }
    },
    async lay_goivasc_goi_dadv (goi_id, tocdo_id, muccuoc_id) {
      try {
        let data = {goi_id, tocdo_id, muccuoc_id}
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
      rowGOI.HDTB_ID = this.hdtb_id
      rowGOI.THANG_HUONG = 0
      this.dsHDTBGOI.push(rowGOI)
    },
    getKey_V2: async function (keyname, SCHEMA) {
      let id = 0
      let input = {
        keyname: keyname
      }
      let res = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/getkey', input)
      if (res.data.length > 0) {
        id = res.data[0].id
      }
      return id
    },
    async GetKey (Key, SCHEMA) {
      try {
        var data = {
          VKEYNAME: Key
        }
        var rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/getkey_v2', data)
        console.log('🚀 ~ file: index.vue ~ line 1540 ~ GetKey ~ rs.data', rs.data)
        if (rs.data.length > 0) {
          return rs.data[0].KEY
        } else { return null }
      } catch (error) {
        return null
      }
    },
    async LAY_DS_DBTB_THEO_TBID (thuebao_id) {
      try {
        let rs = await this.$root.context.get('/web-hopdong/chuyendich/lay_ds_dbtb_theo_tbid?thuebao_id=' + thuebao_id)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async LayHuongGiao_TiepNhan (tinhthicong_id, loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id) {
      try {
        var data = {
          tinhthicong_id, loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id
        }
        console.log('🚀 ~ file: index.vue ~ line 1203 ~ LayHuongGiao_TiepNhan ~ data', data)
        // var rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/layhuonggiao_tiepnhan', data)
        var rs = await this.$root.context.post('/web-bancheo/danhmuc/layhuonggiao_tiepnhan', data)
        console.log('🚀 ~ file: index.vue ~ line 1206 ~ LayHuongGiao_TiepNhan ~ rs', rs)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async LayHuongGiao_TiepNhan_new (tinhthicong_id, loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id, loaikenh_id, dichvuvt_id) {
      try {
        var data = {
          tinhthicong_id, loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id, loaikenh_id, dichvuvt_id
        }
        console.log('🚀 ~ file: index.vue ~ line 1203 ~ layhuonggiao_tiepnhan_v4 ~ data', data)
        // var rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/layhuonggiao_tiepnhan', data)
        var rs = await this.$root.context.post('/web-bancheo/danhmuc/layhuonggiao_tiepnhan_v4', data)
        console.log('🚀 ~ file: index.vue ~ line 1206 ~ layhuonggiao_tiepnhan_v4 ~ rs', rs)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async LayHuongGiao_TiepNhan_2 (loaihd_id, loaitb_id) {
      try {
        var data = {
          loaihd_id, loaitb_id
        }
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/layhuonggiao_tiepnhan_2', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async LayMucCuocTB (thuebao_id, kieu) {
      let value = await this.MAP_ID_LayMucCuocTB(thuebao_id, kieu)
      return parseInt(value)
    },
    async MAP_ID_LayMucCuocTB (thuebao_id, kieu) {
      try {
        let res = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/map_id_laymuccuoctb', {thuebao_id, kieu})
        if (res.data.length > 0) {
          return res.data[0].value
        } else return 0
      } catch (error) {
        return 0
      }
    },
    async TaoDuLieu_HDTB_dkygoi_mytv (dsMyTV) {
      this.dsHDTB = []
      this.dsHDTBGOI = []
      for (let index = 0; index < dsMyTV.length; index++) {
        const element = dsMyTV[index]
        var hdtb_id_huygoi_mytv = this.getKey_V2('HD_THUEBAO', 'CSS')
        let rowHDTB = {}
        var LAY_DS_DBTB_THEO_TBID = this.LAY_DS_DBTB_THEO_TBID(element.thuebao_id)
        if (LAY_DS_DBTB_THEO_TBID.length > 0) {
          rowHDTB = LAY_DS_DBTB_THEO_TBID[0]
        }
        rowHDTB.HDTB_ID = hdtb_id_huygoi_mytv
        rowHDTB.HDKH_ID = this.hdkh_id
        rowHDTB.THUEBAO_ID = element.thuebao_id
        rowHDTB.MA_TB = element.ma_tb
        rowHDTB.TEN_TB = element.ten_tb
        rowHDTB.DIACHI_TB = element.diachi_tb
        rowHDTB.KIEULD_ID = Enum.KieuLapDat.THEM_TB_GOI_DADV
        rowHDTB.DOITUONG_ID = element.doituong_id
        rowHDTB.TTHD_ID = Enum.TrangThaiHD.MOI
        rowHDTB.LOAITB_ID = Enum.LoaiHinhTB.INTERNET_MYTV
        rowHDTB.DICHVUVT_ID = Enum.DichVuVienThong.ADSL
        var dset = await this.LayHuongGiao_TiepNhan_2(Enum.LoaiHopDong.THAYDOI_GOI_DADV, Enum.KieuLapDat.XOA_TB_GOI_DADV)
        if (dset.length > 0) {
          this.huonggiaotn_id = dset[0].huonggiao_id
          this.quytrinh_id_2 = dset[0].quytrinh_id
        }
        rowHDTB.QUYTRINH_ID = this.quytrinh_id_2
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
    async Them_MyTV_vao_goi_giadinh () {
      try {
        if ((this.loaitb_id === Enum.LoaiHinhTB.INTERNET_FTTH || this.loaitb_id === Enum.LoaiHinhTB.INTERNET_MYTV) &&
                    this.$refs.ThongTinThueBao.$refs.refAdsl.txtMaTN_ADSL.Text !== '') {
          let data = {
            vhdtb_id: this.hdtb_id,
            vmatb_tn: this.$refs.ThongTinThueBao.$refs.refAdsl.txtMaTN_ADSL.Text
          }
          let rs = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_dky_family_dichuyen', data)
          if (rs.data !== null && rs.data.length > 0) {
            if (rs.data[0].vkq.toString() === '1') {
              var goi_id = parseInt(rs.data[0].vgoi_id)
              var nhomtb_id = parseInt(rs.data[0].vnhom_id)
              var cboGoiCuocAdsl = this.$refs.ThongTinThueBao.$refs.refAdsl.cboGoiCuocAdsl.Value
              var cboMucCuoc = this.$refs.ThongTinThueBao.$refs.refAdsl.cboMucCuoc.Value
              var magoi_vasc = await this.lay_goivasc_goi_dadv(goi_id, cboGoiCuocAdsl, cboMucCuoc)
              if ((magoi_vasc !== -1 && this.loaitb_id === Enum.LoaiHinhTB.INTERNET_MYTV) || this.loaitb_id === Enum.LoaiHinhTB.INTERNET_FTTH) {
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
                if (this.loaitb_id === Enum.LoaiHinhTB.INTERNET_MYTV) {
                  await this.TaoDuLieu_HDTB_GOI_DADV(goi_id, nhomtb_id)
                  await this.ThemMoi_HopDongTB(this.dsHDTBGOI, 0)
                  this.themMyTVvaogoi = true
                }
                if (this.loaitb_id === Enum.LoaiHinhTB.INTERNET_FTTH) {
                  await this.TaoDuLieu_HDTB_dkygoi_mytv(rs.data)
                  await this.ThemMoi_HopDongTB2(this.dsHDTB, this.dsHDTBGOI, this.huonggiaotn_id)
                  this.themMyTVvaogoi = true
                }
              }
            }
          }
        }
      } catch (error) {
        throw error
      }
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
        console.log('🚀 ~ file: index.vue ~ line 1369 ~ ThemMoi_HopDongTB ~ rs', rs)
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1370 ~ ThemMoi_HopDongTB ~ error', error)
      }
    },
    PopUpCongTacVien_Thoat: function () {
      this.$refs.congtacvien.hide()
    },
    acceptChangeCTV: function (data) {
      try {
        if (data.rowData != 0) {
          this.modelInput.txtCTV = data.rowData.ten_nv
          this.ctv_id = data.nhanvien_id
          this.loainv_ctv_id = data.loainv_id
          this.donvi_ctv_id = data.donvi_id
        }
        this.$refs.congtacvien.hide()
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1699 ~ error', error)
      }
    },
    PopUpNguoiGioiThieu_Thoat: function () {
      this.$refs.nguoigioithieu.hide()
    },
    acceptChangeNGT: function (data) {
      try {
        if (data.rowData != 0) {
          this.modelInput.txtNguoiGT = data.rowData.ten_nv
          this.nguoigt_id = data.nhanvien_id
          this.loainv_nguoi_gt_id = data.loainv_id
          this.donvi_nguoi_gt_id = data.donvi_id
        }
        this.$refs.nguoigioithieu.hide()
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1713 ~ error', error)
      }
    },
    async ThemMoi_HopDongTB2 (dsHDTB, dsGOI, huonggiao_id) {
      try {
        let data = {
          dsHDTB: JSON.stringify(dsHDTB),
          dsGOI: JSON.stringify(dsGOI),
          huonggiao_id: huonggiao_id
        }
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/themmoi_hopdongtb2_v2', data)
        console.log('🚀 ~ file: index.vue ~ line 1382 ~ ThemMoi_HopDongTB2 ~ rs', rs)
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1383 ~ ThemMoi_HopDongTB2 ~ error', error)
      }
    },
    async CAPNHAT_KHOSO (ma_tb_moi, ma_tb_cu, dichvuvt_id, donvi_id) {
      try {
        let data = {
          ma_tb_moi,
          ma_tb_cu,
          dichvuvt_id,
          donvi_id
        }
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/capnhat_khoso_v2', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async fn_tudong_ganthem_hdtb_dv (hdtb_id) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/tudong_ganthem_hdtb_dv?hdtb_id=' + hdtb_id)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async CapNhat_DiaChiBC () {
      try {
        if (this.modelInput.txtDiaChiBC !== '') {
          await this.CAPNHAT_DIACHI_BC_HDTT(this.diachibc_id, this.hdtt_id, this.phobc_id, this.phuongbc_id, this.quanbc_id, this.sonhabc, this.modelInput.txtDiaChiBC, this.tinhbc_id, this.apbc_id, this.khubc_id, this.dacdiembc_id)
        } else {
          await this.CAPNHAT_DIACHI_BC_HDTT(this.diachibc_id, this.hdtt_id, this.phobc_id, this.phuongbc_id, this.quanbc_id, this.sonhabc, '0', this.tinhbc_id, this.apbc_id, this.khubc_id, this.dacdiembc_id)
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1403 ~ CapNhat_DiaChiBC ~ error', error)
      }
    },
    async CAPNHAT_DIACHI_BC_HDTT (diachibc_id, hdtt_id, pho_id, phuong_id, quan_id, sonha, diachi, tinh_id, ap_id, khu_id, dacdiem_id) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/capnhat_diachi_bc', {
          diachibc_id, hdtt_id, pho_id, phuong_id, quan_id, sonha, diachi, tinh_id, ap_id, khu_id, dacdiem_id
        })
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1523 ~ CAPNHAT_DIACHI_BC_HDTT ~ error', error)
        return []
      }
    },
    async Lay_Ma_GD_V2 (donvi_id, loaihd_id) {
      let res = await this.$root.context.get(`/web-hopdong/chuyendich/sinh_magd_v2?donvi_id=${donvi_id}&loaihd_id=${loaihd_id}`)
      return res.data
    },
    async Lay_Ma_GD (loaihd_id) {
      let res = await this.$root.context.get(`/web-hopdong/chuyendich/sinh_magd?loaihd_id=${loaihd_id}`)
      return res.data
    },
    async TaoDuLieu_HDTT (themmoi) {
      try {
        this.dsHDTT = []
        if (themmoi === true) { this.hdtt_id = await this.getKey_V2('HD_THANHTOAN', 'CSS') }
        let rowHDTT = {}
        rowHDTT.HDTT_ID = this.hdtt_id
        rowHDTT.HDKH_ID = this.hdkh_id
        if (this.modelInput.txtMaTT === '' || this.modelInput.txtMaTT === null) {
          this.modelInput.txtMaTT = await this.SINH_MATT(parseInt(this.modelCbo.cboDonViQL), this.quanld_id)
          this.thanhtoan_id = 0
        }
        if (this.thanhtoan_id > 0) { rowHDTT.THANHTOAN_ID = this.thanhtoan_id }

        rowHDTT.MA_TT = this.modelInput.txtMaTT
        rowHDTT.TEN_TT = this.StringToString(this.modelInput.txtTenTT)
        rowHDTT.DIACHI_TT = this.StringToString(this.modelInput.txtDiaChiTT)
        rowHDTT.DIACHI_BC = this.StringToString(this.modelInput.txtDiaChiBC)
        rowHDTT.SO_DT = this.StringToString(this.modelInput.txtTelKH)
        rowHDTT.MST = this.modelInput.txtMST
        rowHDTT.HTTT_ID = parseInt(this.modelCbo.cboHTTT)
        if (parseInt(this.modelCbo.cboHTTT) === Enum.HinhThucThanhToan.UYNHIEM_THU) {
          rowHDTT.NGANHANG_ID = parseInt(this.modelCbo.cboNganHangTT)
        }
        rowHDTT.STK = this.modelInput.txtSTKTT
        rowHDTT.CHU_TK = this.modelInput.txtChu_TK
        rowHDTT.DONVI_ID = parseInt(this.modelCbo.cboDonViQL)
        if (this.tuyenthu_id !== 0) { rowHDTT.TUYENTHU_ID = this.tuyenthu_id }

        rowHDTT.MATB_DD = this.modelCbo.cboMaDD
        rowHDTT.NGUOI_CN = this.$root.token.getUserName()

        rowHDTT.DIACHI_CT = this.StringToString(this.modelInput.txtDiaChiCT)

        rowHDTT.GHICHU = this.StringToString(this.modelInput.txtGhiChuTT)

        rowHDTT.TUYENTHU_ID = this.tuyenthu_id

        let loai_nh = ''
        if (this.modelCbo.cboNganHangTT != null && this.modelCbo.cboNganHangTT !== '') {
          loai_nh = await this.MAP_ID_LOAI_NH('LOAI_NH', 'CSS', 'NGANHANG', 'NGANHANG_ID', this.modelCbo.cboNganHangTT)
        }
        if (loai_nh.toString() === '2') {
          rowHDTT.DV_QHNS = this.DV_QHNS
          rowHDTT.MACHUONG = this.MACHUONG
          rowHDTT.MN_KT = this.MN_KT
          rowHDTT.MA_NDKT = this.MA_NDKT
          rowHDTT.MN_NSNN = this.MN_NSNN
          rowHDTT.TIEN_DM = this.TIEN_DM
        } else {
          rowHDTT.DV_QHNS = ''
          rowHDTT.MACHUONG = ''
          rowHDTT.MN_KT = ''
          rowHDTT.MA_NDKT = ''
          rowHDTT.MN_NSNN = ''
          rowHDTT.TIEN_DM = 0
        }
        this.dsHDTT.push(rowHDTT)
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1444 ~ TaoDuLieu_HDTT ~ error', error)
      }
    },
    async MAP_ID_LOAI_NH (nganhang_id) {
      try {
        let res = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/map_id_loai_nh', {nganhang_id})
        if (res.data.length > 0) {
          return parseInt(res.data[0].loai_nh)
        } else return 0
      } catch (error) {
        return 0
      }
    },
    async TaoDuLieu_HDTB (themmoi) {
      try {
        this.dsHDTB = []
        if (themmoi === true) { this.hdtb_id = await this.getKey_V2('HD_THUEBAO', 'CSS') }
        let rowHDTB = {}
        rowHDTB.HDTB_ID = this.hdtb_id
        if (themmoi === true) { rowHDTB.HDTT_ID = this.hdtt_id } else { rowHDTB.HDTT_ID = parseInt(this.modelCbo.cboTT) }
        rowHDTB.HDKH_ID = this.hdkh_id
        if (this.thuebao_id > 0) { rowHDTB.THUEBAO_ID = this.thuebao_id } else { rowHDTB.THUEBAO_ID = null }

        if (this.modelInput.txtMaTBMoi === '') { rowHDTB.MA_TB = this.modelInput.txtMaTB } else { rowHDTB.MA_TB = this.modelInput.txtMaTBMoi }

        rowHDTB.TEN_TB = this.StringToString(this.modelInput.txtTenTB)
        rowHDTB.DIACHI_TB = this.StringToString(this.modelInput.txtDiaChiTB)

        rowHDTB.DIACHI_LD = this.StringToString(this.modelInput.txtDiaChiLD)
        if (this.modelInput.txtNgaySinhTB !== '') {
          rowHDTB.NGAY_SN = this.modelInput.txtNgaySinhTB
        }

        rowHDTB.KIEULD_ID = parseInt(this.modelCbo.cboKieuLD)
        rowHDTB.DOITUONG_ID = parseInt(this.modelCbo.cboDoituongTB)
        rowHDTB.TTHD_ID = Enum.TrangThaiHD.MOI
        rowHDTB.LOAITB_ID = this._loaitb_id
        rowHDTB.DICHVUVT_ID = this.dichvuvt_id
        rowHDTB.QUYTRINH_ID = this.quytrinh_id
        rowHDTB.DONVI_ID = parseInt(this.modelCbo.cboDaiVT)
        rowHDTB.NGUOI_CN = this.$root.token.getUserName()
        rowHDTB.KIEU_TB = '0'

        let status = ''
        if (parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.ADSL ||
                parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.IMS) { status = '0' }
        if (await this.KT_ThaoTac(this.luong_id, 'STATUS_TONGDAI_CD')) { status = '0' }
        rowHDTB.STATUS = status
        rowHDTB.GHICHU = this.StringToString(this.modelInput.txtGhiChuTB)
        rowHDTB.DONVI_DAU_ID = this.donvi_dau_id
        rowHDTB.DONVI_CUOI_ID = this.donvi_cuoi_id

        if (this.modelCbo.vcboMucCuocTB.length > 0) {
          rowHDTB.MUCUOCTB_ID = this.thongso_ban.muccuoctb_id
          rowHDTB.MUCUOC_TB = parseInt(await this.MAP_ID_CUOC_TB(this.thongso_ban.muccuoctb_id))
        }

        if (this.loaitb_id === Enum.LoaiHinhTB.ISDN2B_CD || this.loaitb_id === Enum.LoaiHinhTB.ISDN2B_CQ ||
                this.loaitb_id === Enum.LoaiHinhTB.ISDN30B_CD || this.loaitb_id === Enum.LoaiHinhTB.ISDN30B_CQ ||
                this.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_2M || this.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_THUONG ||
                this.loaitb_id === Enum.LoaiHinhTB.TRUNGKE_TUONGTU) {
          if (this.loaisochinh === 0) {
            if (this.hdtb_id_cha > 0) { rowHDTB.HDTB_CHA_ID = this.hdtb_id_cha }
          }
        }
        if (parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.MEGAWAN ||
                parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.METRONET ||
                parseInt(this.modelCbo.cboDichVuVT) === Enum.DichVuVienThong.NOIBO_DADIEM) {
          if (this.thuebao_id > 0) {
            let tb_cha_id = parseInt(await this.MAP_ID_THUEBAO_CHA_ID(this.thuebao_id))
            if (tb_cha_id !== -1) { rowHDTB.THUEBAO_CHA_ID = tb_cha_id }
          }
        }
        if (this.modelCbo.cboGoiKT > 0) { rowHDTB.GOIKT_ID = parseInt(this.modelCbo.cboGoiKT) }
        console.log('🚀 ~ file: index.vue ~ line 1523B ~ TaoDuLieu_HDTB ~ rowHDTB', rowHDTB)
        this.dsHDTB.push(rowHDTB)
        this.dsKM_HDTB.forEach(e => {
          e.HDTB_ID = this.hdtb_id
        })
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1512 ~ TaoDuLieu_HDTB ~ error', error)
      }
    },
    async MAP_ID_CUOC_TB (muccuoctb_id) {
      try {
        let res = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/map_id_cuoc_tb', {muccuoctb_id})
        console.log('🚀 ~ file: index.vue ~ line 1907 ~ MAP_ID_CUOC_TB ~ res', res)
        if (res.data.length > 0) {
          return res.data[0].cuoc_tb
        } else return 0
      } catch (error) {
        return 0
      }
    },
    async MAP_ID_THUEBAO_CHA_ID (thuebao_id) {
      try {
        let res = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/map_id_thuebao_id', {thuebao_id})
        console.log('🚀 ~ file: index.vue ~ line 1918 ~ MAP_ID_THUEBAO_CHA_ID ~ res', res)
        if (res.data.length > 0) {
          return res.data[0].thuebao_cha_id
        } else return -1
      } catch (error) {
        return -1
      }
    },
    async KT_ThaoTac (luong_id, code) {
      try {
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/kt_thaotac', {luong_id, code})
        if (rs.data.length > 0) {
          if (rs.data[0].sl > 0) return true
          else return false
        } else return false
      } catch (error) {
        return false
      }
    },
    async SINH_MATT (donvi_id, quanld_id) {
      try {
        return this.$root.context.get(`/web-hopdong/chuyendich/sinh_matt?donvi_id=${donvi_id}&quan_id=${quanld_id}`)
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1941 ~ SINH_MATT ~ error', error)
      }
    },
    async TaoDuLieu_HDKH (themmoi) {
      try {
        let vmagd_temp = ''
        this.dsHDKH = []
        if (themmoi === true) { this.hdkh_id = await this.getKey_V2('HD_KHACHHANG', 'CSS') }
        let rowHDKH = {}
        rowHDKH.HDKH_ID = this.hdkh_id

        if (themmoi === true) {
          if (this.ts_sinhma_gd_theo_donvi) { vmagd_temp = await this.Lay_Ma_GD_V2(this.$root.token.getDonViID(), Enum.LoaiHopDong.DI_CHUYEN) } else { vmagd_temp = await this.Lay_Ma_GD(Enum.LoaiHopDong.DI_CHUYEN) }
          if (vmagd_temp.includes('ERROR:')) {
            this.$root.$toast.error(vmagd_temp)
            this.loi_sinh_magd = true
          }
          this.modelInput.txtMaGD = vmagd_temp
          rowHDKH.MA_GD = this.modelInput.txtMaGD
        } else {
          rowHDKH.MA_GD = this.modelInput.txtMaGD
        }
        rowHDKH.MA_HD = this.modelInput.txtMaHDcu
        // rowHDKH.NGAY_YC = DateTimeLib.toDateDisplay(this.modelDateTime.dtpNgayYC)
        rowHDKH.MA_KH = this.modelInput.txtMaKH
        if (this.khachhang_id > 0) { rowHDKH.KHACHHANG_ID = this.khachhang_id } else { rowHDKH.KHACHHANG_ID = 0 }
        rowHDKH.TEN_KH = this.StringToString(this.modelInput.txtTenKH)
        rowHDKH.DIACHI_KH = this.StringToString(this.modelInput.txtDiaChiKH)
        rowHDKH.NGUOI_DD = this.StringToString(this.modelInput.txtNguoiDD)
        rowHDKH.SO_DT = this.modelInput.txtTelKH
        rowHDKH.CHUCDANH_DD = this.StringToString(this.modelInput.txtChucDanhDD)
        rowHDKH.SO_FAX = this.modelInput.txtFaxKH

        if (this.modelInput.txtNgaySN !== '') {
          rowHDKH.NGAY_SN = this.modelInput.txtNgaySN
        }
        rowHDKH.NGAYLAP_HD = DateTimeLib.toDateDisplayWithFormat(this.modelDateTime.dtpNgayLapHD, 'DD/MM/YYYY')
        rowHDKH.LOAIGT_ID = parseInt(this.modelCbo.cboGiayToKH)
        rowHDKH.SO_GT = this.modelInput.txtSoGT

        if (this.modelInput.txtNgayCap !== '') {
          rowHDKH.NGAYCAP = this.modelInput.txtNgayCap
        }
        rowHDKH.NOICAP = this.StringToString(this.modelInput.txtNoiCap)
        rowHDKH.DONVI_ID = this.$root.token.getDonViID()
        rowHDKH.NHANVIEN_ID = this.$root.token.getNhanVienID()
        rowHDKH.HOKHAU = this.StringToString(this.modelInput.txtHoKhau)

        if (this.modelInput.txtNgayCapHK !== '') {
          rowHDKH.NGAY_HK = this.modelInput.txtNgayCapHK
        }
        rowHDKH.NOICAP_HK = this.StringToString(this.modelInput.txtNoiCapHK)

        rowHDKH.KHLON_ID = parseInt(this.modelCbo.cboKhachHangLon)
        rowHDKH.LOAIKH_ID = parseInt(this.modelCbo.cboLoaiKH)

        if (this.PHANLOAIKH_ID !== -1 && this.PHANLOAIKH_ID !== 0) { rowHDKH.PHANLOAIKH_ID = this.PHANLOAIKH_ID }

        rowHDKH.LOAIHD_ID = Enum.LoaiHopDong.DI_CHUYEN
        rowHDKH.NGUOI_CN = this.$root.token.getUserName()

        rowHDKH.MST = this.modelInput.txtMaSTKH
        rowHDKH.STK = this.modelInput.txtSoTKKH

        if (this.modelInput.txtNgayHetHan !== '') {
          rowHDKH.NGAYHETHAN_GT = this.modelInput.txtNgayHetHan
        }
        if (this.modelCkeckBox.rdoGioiTinh === 0) {
          rowHDKH.GIOITINH = 0
        } else if (this.modelCkeckBox.rdoGioiTinh === 1) {
          rowHDKH.GIOITINH = 1
        }
        rowHDKH.NGANHNGHE_ID = this.modelCbo.cboNganhNgheLoc

        rowHDKH.TNC1_ID = this.TNC1_ID
        rowHDKH.TNC2_ID = this.TNC2_ID
        rowHDKH.TNC3_ID = this.TNC3_ID
        rowHDKH.TNC4_ID = this.TNC4_ID

        rowHDKH.QUOCTICH_ID = parseInt(this.modelCbo.cboQuocTich)
        rowHDKH.DANTOC_ID = parseInt(this.modelCbo.cboDanToc)
        //  }
        rowHDKH.DIACHI_NDD = this.StringToString(this.modelInput.txtDiaChiNDD)

        if (this.modelCkeckBox.chkNganHangKH) {
          rowHDKH.NGANHANG_ID = parseInt(this.modelCbo.cboNganHangKH)
        }
        rowHDKH.GHICHU = this.StringToString(this.modelInput.txtGhiChuKH)
        rowHDKH.KIEUHD_ID = parseInt(this.modelCbo.cboKieu_HD)
        if (this.hdkh_cha_id > 0) {
          rowHDKH.HDKH_CHA_ID = this.hdkh_cha_id
        }

        if (this.modelCkeckBox.chkCTV && this.modelInput.txtCTV !== '') {
          rowHDKH.CTV_ID = this.ctv_id
        }
        if (this.modelCkeckBox.chkNguoiGT && this.modelInput.txtNguoiGT !== '') {
          rowHDKH.NHANVIENGT_ID = this.nguoigt_id
        }
        rowHDKH.KENHTN_ID = this.iKenhTN
        this.dsHDKH.push(rowHDKH)
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1620 ~ TaoDuLieu_HDKH ~ error', error)
      }
    },
    StringToString (xauthamso) { return xauthamso },
    async TaoDuLieu_HDTT_NV (themmoi) {
      try {
        this.dsHDTT_NV = []
        if (this.nhanvien_ql_am_id !== 0) {
          let rowhdtt_nv = {}
          rowhdtt_nv.HDTT_ID = this.hdtt_id
          rowhdtt_nv.NHANVIEN_ID = this.nhanvien_ql_am_id
          rowhdtt_nv.NGUOI_CN = this.$root.token.getUserName()
          this.dsHDTT_NV.push(rowhdtt_nv)
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1636 ~ TaoDuLieu_HDTT_NV ~ error', error)
      }
    },
    async TaoDuLieu (themmoi) {
      try {
        await this.TaoDuLieu_HDKH(themmoi)
        await this.TaoDuLieu_HDTT(themmoi)
        await this.TaoDuLieu_HDTB(themmoi)

        await this.TaoDuLieu_THONGTIN_TT(themmoi)
        await this.TaoDuLieu_CT_TIENHD(true)
        await this.TaoDuLieu_HDTT_NV(true)

        await this.TaoDuLieu_DiaChi(themmoi, 4)
        await this.TaoDuLieu_DiaChi_BanCheo(themmoi, 4)

        await this.TaoDuLieu_DKDV()
        await this.CapNhat_HDTB_ID_DangKyDV(this.hdtb_id)

        await this.CapNhat_HDTB_ID_HDTB_KV()

        this.dsHDTB_CD = []
        this.dsHDTB_ADSL = []
        this.dsHDTB_TSL = []
        this.dsHDTB_MEWAN = []
        this.dsHDTB_GP = []
        this.dsHDTB_IMS = []
        this.dsHDTBDV = []
        let data = {}
        switch (this.dichvuvt_id) {
          case Enum.DichVuVienThong.CO_DINH:
            data = await this.$refs.ThongTinThueBao.$refs.refCD.TaoDuLieu_HDTB_CD(themmoi)
            this.dsHDTB_CD = data.dsHDTB_CD
            this.dsHDTBDV = data.dsHDTBDV
            break
          case Enum.DichVuVienThong.IMS:
            data = await this.$refs.ThongTinThueBao.$refs.refIMS.TaoDuLieu_HDTB_IMS(themmoi)
            this.dsHDTB_IMS = data.dsHDTB_IMS
            this.dsHDTBDV = data.dsHDTBDV
            break
          case Enum.DichVuVienThong.ADSL:
            data = await this.$refs.ThongTinThueBao.$refs.refAdsl.TaoDuLieu_HDTB_ADSL(themmoi)
            this.dsHDTB_ADSL = data.dsHDTB_ADSL
            this.dsHDTBDV = data.dsHDTBDV
            break
          case Enum.DichVuVienThong.MEGA_EYES:
            data = await this.$refs.ThongTinThueBao.$refs.refAdsl.TaoDuLieu_HDTB_ADSL(themmoi)
            this.dsHDTB_ADSL = data.dsHDTB_ADSL
            this.dsHDTBDV = data.dsHDTBDV
            break
          case Enum.DichVuVienThong.MEGAWAN:
            data = await this.$refs.ThongTinThueBao.$refs.refWan.TaoDuLieu_HDTB_MGWAN(themmoi)
            console.log('🚀 ~ file: index.vue:2287 ~ TaoDuLieu ~ data:', data)
            this.dsHDTB_MEWAN = data.dsHDTB_MEWAN
            this.dsHDTBDV = data.dsHDTBDV
            break
          case Enum.DichVuVienThong.METRONET:
          case Enum.DichVuVienThong.NOIBO_DADIEM:
            data = await this.$refs.ThongTinThueBao.$refs.refWan.TaoDuLieu_HDTB_MGWAN(themmoi)
            console.log('🚀 ~ file: index.vue:2293 ~ TaoDuLieu ~ data:', data)
            this.dsHDTB_MEWAN = data.dsHDTB_MEWAN
            this.dsHDTBDV = data.dsHDTBDV
            break
          case Enum.DichVuVienThong.TSL:
          case Enum.DichVuVienThong.NOIBO_2DIEM:
            data = await this.$refs.ThongTinThueBao.$refs.refTSL.TaoDuLieu_HDTB_TSL(themmoi)
            this.dsHDTB_TSL = data.dsHDTB_TSL
            this.dsHDTBDV = data.dsHDTBDV
            break
          case Enum.DichVuVienThong.GPHONE:
            data = await this.$refs.ThongTinThueBao.$refs.refGP.TaoDuLieu_HDTB_GP(themmoi)
            this.dsHDTB_GP = data.dsHDTB_GP
            this.dsHDTBDV = data.dsHDTBDV
            break
        }
        return true
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1671 ~ TaoDuLieu ~ error', error)
        return false
      }
    },
    async CapNhat_HDTB_ID_DangKyDV (hdtb_id) {
      this.dsDK_DVK = []
    },
    async CapNhat_HDTB_ID_HDTB_KV () {
      for (let i = 0; i < this.dsHDTB_KV.length; i++) {
        this.dsHDTB_KV[i]['HDTB_ID'] = this.hdtb_id
      }
    },
    async TaoDuLieu_DiaChi_BanCheo (themmoi, kieu) {
      try {
        this.dsDiaChi_BanCheo = []
        if (kieu === 1 || kieu === 4) {
          let rowDiaChiKH_BC = {}
          rowDiaChiKH_BC.DIACHI_ID = this.diachikh_id
          rowDiaChiKH_BC.TINH_ID = this.tinhkh_id
          rowDiaChiKH_BC.QUAN_ID = this.quankh_id
          rowDiaChiKH_BC.PHUONG_ID = this.phuongkh_id
          if (this.phokh_id !== 0) { rowDiaChiKH_BC.PHO_ID = this.phokh_id }
          if (this.apkh_id !== 0) { rowDiaChiKH_BC.AP_ID = this.apkh_id }
          if (this.khukh_id !== 0) { rowDiaChiKH_BC.KHU_ID = this.khukh_id }
          if (this.dacdiemkh_id !== 0) { rowDiaChiKH_BC.DACDIEM_ID = this.dacdiemkh_id }
          rowDiaChiKH_BC.SONHA = this.StringToString(this.sonhakh)
          rowDiaChiKH_BC.DIACHI = this.StringToString(this.modelInput.txtDiaChiKH)
          rowDiaChiKH_BC.LOAI_DC = 1
          this.dsDiaChi_BanCheo.push(rowDiaChiKH_BC)
        }
        if (kieu === 2 || kieu === 4) {
          let rowDiaChiTT_BC = {}

          rowDiaChiTT_BC.DIACHI_ID = this.diachitt_id
          rowDiaChiTT_BC.TINH_ID = this.tinhtt_id
          rowDiaChiTT_BC.QUAN_ID = this.quantt_id
          rowDiaChiTT_BC.PHUONG_ID = this.phuongtt_id
          if (this.phott_id !== 0) { rowDiaChiTT_BC.PHO_ID = this.phott_id }
          if (this.aptt_id !== 0) { rowDiaChiTT_BC.AP_ID = this.aptt_id }
          if (this.khutt_id !== 0) { rowDiaChiTT_BC.KHU_ID = this.khutt_id }
          if (this.dacdiemtt_id !== 0) { rowDiaChiTT_BC.DACDIEM_ID = this.dacdiemtt_id }
          rowDiaChiTT_BC.SONHA = this.StringToString(this.sonhatt)
          rowDiaChiTT_BC.DIACHI = this.StringToString(this.modelInput.txtDiaChiTT)
          rowDiaChiTT_BC.LOAI_DC = 2
          this.dsDiaChi_BanCheo.push(rowDiaChiTT_BC)
        }

        if (kieu === 3 || kieu === 4) {
          let rowDiaChiTB_BC = {}
          let rowDiachiLD_BC = {}

          rowDiachiLD_BC.DIACHI_ID = this.diachild_id
          rowDiachiLD_BC.TINH_ID = this.tinhld_id
          rowDiachiLD_BC.QUAN_ID = this.quanld_id
          rowDiachiLD_BC.PHUONG_ID = this.phuongld_id
          if (this.phold_id !== 0) { rowDiachiLD_BC.PHO_ID = this.phold_id }
          if (this.apld_id !== 0) { rowDiachiLD_BC.AP_ID = this.apld_id }
          if (this.khuld_id !== 0) { rowDiachiLD_BC.KHU_ID = this.khuld_id }
          if (this.dacdiemld_id !== 0) { rowDiachiLD_BC.DACDIEM_ID = this.dacdiemld_id }
          rowDiachiLD_BC.SONHA = this.StringToString(this.sonhald)
          rowDiachiLD_BC.DIACHI = this.StringToString(this.modelInput.txtDiaChiLD)
          rowDiachiLD_BC.LOAI_DC = 4
          this.dsDiaChi_BanCheo.push(rowDiachiLD_BC)

          rowDiaChiTB_BC.DIACHI_ID = this.diachitb_id
          rowDiaChiTB_BC.TINH_ID = this.tinhtb_id
          rowDiaChiTB_BC.QUAN_ID = this.quantb_id
          rowDiaChiTB_BC.PHUONG_ID = this.phuongtb_id
          if (this.photb_id !== 0) { rowDiaChiTB_BC.PHO_ID = this.photb_id }
          if (this.aptb_id !== 0) { rowDiaChiTB_BC.AP_ID = this.aptb_id }
          if (this.khutb_id !== 0) { rowDiaChiTB_BC.KHU_ID = this.khutb_id }
          if (this.dacdiemtb_id !== 0) { rowDiaChiTB_BC.DACDIEM_ID = this.dacdiemtb_id }
          rowDiaChiTB_BC.SONHA = this.StringToString(this.sonhatb)
          rowDiaChiTB_BC.DIACHI = this.StringToString(this.modelInput.txtDiaChiTB)
          rowDiaChiTB_BC.LOAI_DC = 3
          this.dsDiaChi_BanCheo.push(rowDiaChiTB_BC)

          if (this.dichvuvt_id == Enum.DichVuVienThong.TSL || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM) {
            let rowDiaChiDau_BC = {}
            rowDiaChiDau_BC.DIACHI_ID = this.diachidau_id
            rowDiaChiDau_BC.TINH_ID = this.tinhdau_id
            rowDiaChiDau_BC.QUAN_ID = this.quandau_id
            rowDiaChiDau_BC.PHUONG_ID = this.phuongdau_id
            if (this.phodau_id !== 0) { rowDiaChiDau_BC.PHO_ID = this.phodau_id }
            if (this.apdau_id !== 0) { rowDiaChiDau_BC.AP_ID = this.apdau_id }
            if (this.khudau_id !== 0) { rowDiaChiDau_BC.KHU_ID = this.khudau_id }
            rowDiaChiDau_BC.SONHA = this.StringToString(this.sonhadau)
            rowDiaChiDau_BC.DIACHI = this.StringToString(this.modelInput.txtDiaChiDau)
            rowDiaChiDau_BC.LOAI_DC = 5
            this.dsDiaChi_BanCheo.push(rowDiaChiDau_BC)

            let rowDiaChiCuoi_BC = {}
            rowDiaChiCuoi_BC.DIACHI_ID = this.diachicuoi_id
            rowDiaChiCuoi_BC.TINH_ID = this.tinhcuoi_id
            rowDiaChiCuoi_BC.QUAN_ID = this.quancuoi_id
            rowDiaChiCuoi_BC.PHUONG_ID = this.phuongcuoi_id
            if (this.phocuoi_id !== 0) { rowDiaChiCuoi_BC.PHO_ID = this.phocuoi_id }
            if (this.apdau_id !== 0) { rowDiaChiCuoi_BC.AP_ID = this.apcuoi_id }
            if (this.khucuoi_id !== 0) { rowDiaChiCuoi_BC.KHU_ID = this.khucuoi_id }
            rowDiaChiCuoi_BC.SONHA = this.StringToString(this.sonhacuoi)
            rowDiaChiCuoi_BC.DIACHI = this.StringToString(this.modelInput.txtDiaChiCuoi)
            rowDiaChiCuoi_BC.LOAI_DC = 6
            this.dsDiaChi_BanCheo.push(rowDiaChiCuoi_BC)
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1818 ~ TaoDuLieu_DiaChi_BanCheo ~ error', error)
      }
    },
    async TaoDuLieu_DiaChi (themmoi, kieu) {
      try {
        this.dsDiaChi = []
        this.dsDiaChiHDKH = []
        this.dsDiaChiHDTT = []
        this.dsDiaChiHDTB = []

        if (kieu === 1 || kieu === 4) {
          let rowDiaChiKH = {}
          if (themmoi === true) { this.diachikh_id = await this.getKey_V2('DIACHI', 'CSS') }
          rowDiaChiKH.DIACHI_ID = this.diachikh_id
          rowDiaChiKH.TINH_ID = this.tinhkh_id
          rowDiaChiKH.QUAN_ID = this.quankh_id
          rowDiaChiKH.PHUONG_ID = this.phuongkh_id
          if (this.phokh_id !== 0) { rowDiaChiKH.PHO_ID = this.phokh_id }
          if (this.apkh_id !== 0) { rowDiaChiKH.AP_ID = this.apkh_id }
          if (this.khukh_id !== 0) { rowDiaChiKH.KHU_ID = this.khukh_id }
          if (this.dacdiemkh_id !== 0) { rowDiaChiKH.DACDIEM_ID = this.dacdiemkh_id }

          rowDiaChiKH.SONHA = this.StringToString(this.sonhakh)
          rowDiaChiKH.DIACHI = this.StringToString(this.modelInput.txtDiaChiKH)
          this.dsDiaChi.push(rowDiaChiKH)

          let rowDiaChiHDKH = {}
          rowDiaChiHDKH.DIACHI_ID = this.diachikh_id
          rowDiaChiHDKH.HDKH_ID = this.hdkh_id
          rowDiaChiHDKH.KINHDO = this.Lng_KH
          rowDiaChiHDKH.VIDO = this.Lat_KH
          this.dsDiaChiHDKH.push(rowDiaChiHDKH)
        }

        if (kieu === 2 || kieu === 4 || kieu === 5) {
          let rowDiaChiTT = {}
          if (themmoi === true) { this.diachitt_id = await this.getKey_V2('DIACHI', 'CSS') }
          rowDiaChiTT.DIACHI_ID = this.diachitt_id
          rowDiaChiTT.TINH_ID = this.tinhtt_id
          rowDiaChiTT.QUAN_ID = this.quantt_id
          rowDiaChiTT.PHUONG_ID = this.phuongtt_id

          if (this.phott_id !== 0) { rowDiaChiTT.PHO_ID = this.phott_id }
          if (this.aptt_id !== 0) { rowDiaChiTT.AP_ID = this.aptt_id }
          if (this.khutt_id !== 0) { rowDiaChiTT.KHU_ID = this.khutt_id }
          if (this.dacdiemtt_id !== 0) { rowDiaChiTT.DACDIEM_ID = this.dacdiemtt_id }

          rowDiaChiTT.SONHA = this.StringToString(this.sonhatt)
          rowDiaChiTT.DIACHI = this.StringToString(this.modelInput.txtDiaChiTT)
          this.dsDiaChi.push(rowDiaChiTT)

          let rowDiaChiHDTT = {}
          rowDiaChiHDTT.DIACHI_ID = this.diachitt_id
          rowDiaChiHDTT.HDTT_ID = this.hdtt_id
          rowDiaChiHDTT.KINHDO = this.Lng_TT
          rowDiaChiHDTT.VIDO = this.Lat_TT
          rowDiaChiHDTT.KINHDO_BC = this.Lng_BC
          rowDiaChiHDTT.VIDO_BC = this.Lat_BC
          this.dsDiaChiHDTT.push(rowDiaChiHDTT)
        }

        if (kieu === 3 || kieu === 4 || kieu === 5) {
          let rowDiaChiTB = {}
          let rowDiachiLD = {}
          if (themmoi === true) {
            this.diachitb_id = await this.getKey_V2('DIACHI', 'CSS')
            this.diachild_id = await this.getKey_V2('DIACHI', 'CSS')
          }

          rowDiachiLD.DIACHI_ID = this.diachild_id
          rowDiachiLD.TINH_ID = this.tinhtt_id
          rowDiachiLD.QUAN_ID = this.quantt_id
          rowDiachiLD.PHUONG_ID = this.phuongtt_id

          if (this.phott_id !== 0) { rowDiachiLD.PHO_ID = this.phott_id }
          if (this.aptt_id !== 0) { rowDiachiLD.AP_ID = this.aptt_id }
          if (this.khutt_id !== 0) { rowDiachiLD.KHU_ID = this.khutt_id }
          if (this.dacdiemtt_id !== 0) { rowDiachiLD.DACDIEM_ID = this.dacdiemtt_id }

          rowDiachiLD.SONHA = this.StringToString(this.sonhald)
          rowDiachiLD.DIACHI = this.StringToString(this.modelInput.txtDiaChiLD)
          this.dsDiaChi.push(rowDiachiLD)

          rowDiaChiTB.DIACHI_ID = this.diachitb_id
          rowDiaChiTB.TINH_ID = this.tinhtt_id
          rowDiaChiTB.QUAN_ID = this.quantt_id
          rowDiaChiTB.PHUONG_ID = this.phuongtt_id
          if (this.photb_id !== 0) { rowDiaChiTB.PHO_ID = this.phott_id }
          if (this.aptb_id !== 0) { rowDiaChiTB.AP_ID = this.aptt_id }
          if (this.khutb_id !== 0) { rowDiaChiTB.KHU_ID = this.khutt_id }
          if (this.dacdiemtb_id !== 0) { rowDiaChiTB.DACDIEM_ID = this.dacdiemtt_id }

          rowDiaChiTB.SONHA = this.StringToString(this.sonhatb)
          rowDiaChiTB.DIACHI = this.StringToString(this.modelInput.txtDiaChiTB)
          this.dsDiaChi.push(rowDiaChiTB)

          if (this.dichvuvt_id === Enum.DichVuVienThong.TSL || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM) {
            let rowDiaChiDau = {}
            if (themmoi === true) { this.diachidau_id = await this.getKey_V2('DIACHI', 'CSS') }

            rowDiaChiDau.DIACHI_ID = this.diachidau_id
            rowDiaChiDau.TINH_ID = this.tinhtt_id
            rowDiaChiDau.QUAN_ID = this.quantt_id
            rowDiaChiDau.PHUONG_ID = this.phuongtt_id
            if (this.phodau_id !== 0) { rowDiaChiDau.PHO_ID = this.phott_id }
            if (this.apdau_id !== 0) { rowDiaChiDau.AP_ID = this.aptt_id }
            if (this.khudau_id !== 0) { rowDiaChiDau.KHU_ID = this.khutt_id }

            rowDiaChiDau.SONHA = this.StringToString(this.sonhadau)
            rowDiaChiDau.DIACHI = this.StringToString(this.modelInput.txtDiaChiDau)
            this.dsDiaChi.push(rowDiaChiDau)

            let rowDiaChiCuoi = {}
            if (themmoi === true) { this.diachicuoi_id = await this.getKey_V2('DIACHI', 'CSS') }
            rowDiaChiCuoi.DIACHI_ID = this.diachicuoi_id
            rowDiaChiCuoi.TINH_ID = this.tinhtt_id
            rowDiaChiCuoi.QUAN_ID = this.quantt_id
            rowDiaChiCuoi.PHUONG_ID = this.phuongtt_id
            if (this.phocuoi_id !== 0) { rowDiaChiCuoi.PHO_ID = this.phott_id }
            if (this.apcuoi_id !== 0) { rowDiaChiCuoi.AP_ID = this.aptt_id }
            if (this.khucuoi_id !== 0) { rowDiaChiCuoi.KHU_ID = this.khutt_id }

            rowDiaChiCuoi.SONHA = this.StringToString(this.sonhacuoi)
            rowDiaChiCuoi.DIACHI = this.StringToString(this.modelInput.txtDiaChiCuoi)
            this.dsDiaChi.push(rowDiaChiCuoi)
          }

          let rowDiaChiHDTB = {}
          rowDiaChiHDTB.DIACHI_ID = this.diachitb_id
          rowDiaChiHDTB.HDTB_ID = this.hdtb_id
          rowDiaChiHDTB.DIACHILD_ID = this.diachild_id
          rowDiaChiHDTB.KINHDO = this.Lng_TB
          rowDiaChiHDTB.VIDO = this.Lat_TB
          rowDiaChiHDTB.KINHDO_LD = this.Lng_LD
          rowDiaChiHDTB.VIDO_LD = this.Lat_LD
          this.dsDiaChiHDTB.push(rowDiaChiHDTB)
        }
        if (themmoi === false) {
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1954 ~ TaoDuLieu_DiaChi ~ error', error)
      }
    },
    async TaoDuLieu_DKDV () {
      try {
        this.dsDK_DVGT = []
        this.m_dsThueBao_DichVu.dsdvgt_ban.forEach(dr => {
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
            rowHDTDDV.HDTB_ID = this.hdtb_id
            rowHDTDDV.NGAY_YC = DateTimeLib.toDateDisplay(this.modelDateTime.dtpNgayYC, 'DD/MM/YYYY')
            rowHDTDDV.DICHVUGT_ID = dr.dichVuGtId
            rowHDTDDV.NOIDUNG = dr.noiDung
            rowHDTDDV.KIEU_YC = kieu_dk_dvgt
            rowHDTDDV.TTTH = 1
            this.dsDK_DVGT.push(rowHDTDDV)
          }
        })
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1985 ~ TaoDuLieu_DKDV ~ error', error)
      }
    },
    async TaoDuLieu_CT_TIENHD (themmoi) {
      try {
        this.dsCTTHD = []
        console.log('🚀 ~ file: index.vue ~ line 1992 ~ TaoDuLieu_CT_TIENHD ~ this.ds_tien_khoanmuc', this.ds_tien_khoanmuc)
        console.log('🚀 ~ file: index.vue ~ line 1998 ~ TaoDuLieu_CT_TIENHD ~ this.m_dsThueBao_DichVu', this.m_dsThueBao_DichVu.dsdvgt_ban)
        for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          const e = this.ds_tien_khoanmuc[i]
          if (e.khoanmuctt_id === Enum.KHOANMUC_TT.KMTT_DICHVU) {
            for (let j = 0; j < this.m_dsThueBao_DichVu.dsdvgt_ban.length; j++) {
              const f = this.m_dsThueBao_DichVu.dsdvgt_ban[j]
              if (f.dangKy === '1' && f.huy === '0') {
                let rowCTTHD = {}
                rowCTTHD.HDTB_ID = this.hdtb_id
                if (themmoi === true) {
                  this.CT_TIENHD_ID = await this.getKey_V2('CT_TIENHD', 'CSS')
                  rowCTTHD.CT_TIENHD_ID = this.CT_TIENHD_ID
                }
                rowCTTHD.KHOANMUCTT_ID = Enum.KHOANMUC_TT.KMTT_DICHVU
                let tien = 0// Tiền dịch vụ chưa khuyến mại
                let vat = 0// Vat dịch vụ chưa khuyến mại
                tien = parseInt(f.cuocLd)
                rowCTTHD.TIEN = tien
                vat = parseInt(f.vatLd)
                rowCTTHD.VAT = vat
                rowCTTHD.ID = parseInt(f.dichVuGtId)
                rowCTTHD.LOAI_ID = 2
                this.dsCTTHD.push(rowCTTHD)
              }
            }
          } else {
            let rowCTTHD = {}
            rowCTTHD.HDTB_ID = this.hdtb_id
            if (themmoi === true) {
              this.CT_TIENHD_ID = await this.getKey_V2('CT_TIENHD', 'CSS')
              rowCTTHD.CT_TIENHD_ID = this.CT_TIENHD_ID
            }
            rowCTTHD.KHOANMUCTT_ID = parseInt(e.khoanmuctt_id)
            rowCTTHD.TIEN = parseInt(e.tien)
            rowCTTHD.VAT = parseInt(e.vat)
            if (parseInt(e.khoanmuctt_id) === Enum.KHOANMUC_TT.KMTT_KM_LAPDAT ||
                       parseInt(e.khoanmuctt_id) === Enum.KHOANMUC_TT.KMTT_KM_THIETBI ||
                       parseInt(e.khoanmuctt_id) === Enum.KHOANMUC_TT.KMTT_KM_DATCOC) {
              rowCTTHD.TIEN = Math.abs(rowCTTHD.TIEN) * -1
              rowCTTHD.VAT = Math.abs(rowCTTHD.VAT) * -1
            }
            rowCTTHD.ID = parseInt(e.khoanmuctt_id)
            rowCTTHD.LOAI_ID = 1
            this.dsCTTHD.push(rowCTTHD)
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2011 ~ TaoDuLieu_CT_TIENHD ~ error', error)
      }
    },
    async TaoDuLieu_THONGTIN_TT (themmoi) {},
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
    async KIEMTRA_MA_TB_LAP_HOPDONG_KHAC (ma_tb, ma_gd, dichvuvt_id, loaihd_id) {
      if (ma_tb === null || ma_tb === '') {
        return false
      }
      try {
        var data = {ma_tb, ma_gd, dichvuvt_id, loaihd_id}
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_ma_tb_lap_hopdong_khac', data)
        console.log('🚀 ~ file: index.vue ~ line 2224 ~ KIEMTRA_MA_TB_LAP_HOPDONG_KHAC ~ rs', rs)
        if (rs.data.length > 0) {
          this.$toast.warning('Số máy/Acc: ' + ma_tb + ' đang được lập bởi hợp đồng (phụ lục) ' + rs.data[0].ten_loaihd + ' có mã giao dịch ' + rs.data[0].ma_gd + ' do User ' + rs.data[0].nguoi_cn + ' thuộc đơn vị ' + rs.data[0].ten_dv + ' thực hiện vào ngày ' + rs.data[0].ngay_yc)
          return true
        } else return false
      } catch (error) {
        console.log('🚀 ~ file: index.vue:2603 ~ KIEMTRA_MA_TB_LAP_HOPDONG_KHAC ~ error:', error)
        return true
      }
    },
    async KIEMTRA_DB_KHI_THEMMOI (ma_tb, khachhang_id, thuebao_id, dichvuvt_id) {
      try {
        var data = {ma_tb, khachhang_id, thuebao_id, dichvuvt_id}
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_db_khi_themmoi', data)
        return parseInt(rs.data) === 1
      } catch (error) {
        return false
      }
    },
    async KiemTraDL_KhachHang () {
      // try {
      //   if (this.modelInput.txtTenKH == '') {
      //     this.$toast.warning('Bạn chưa nhập tên khách hàng !')
      //     this.onclickTab(1)
      //     this.$refs.ThongTinKhachHang.$refs.txtTenKH.focus()
      //     return false
      //   }

      //   if (this.modelInput.txtDiaChiKH === '' || this.quankh_id === 0 || this.phuongkh_id === 0 || this.tinhkh_id === 0 || this.tinhkh_id === null) {
      //     this.$toast.warning('Bạn chưa nhập địa chỉ khách hàng !')
      //     this.onclickTab(1)
      //     this.$refs.ThongTinKhachHang.$refs.txtDiaChiKH.focus()
      //     return false
      //   }
      //   if (this.modelInput.txtMaHDcu == '') {
      //     this.$toast.warning('Bạn chưa nhập mã hợp đồng cho thuê bao dịch chuyển!')
      //     this.onclickTab(0)
      //     this.$refs.ThongTinThueBao.$refs.txtMaHDcu.focus()
      //     return false
      //   }
      //   if (this.modelInput.txtSoGT == '') {
      //     this.$toast.warning('Hãy nhập số giấy tờ !')
      //     this.onclickTab(1)
      //     this.$refs.ThongTinKhachHang.$refs.txtSoGT.focus()
      //     return false
      //   }
      //   if (this.modelInput.txtNgayCap == '') {
      //     this.$toast.warning('Bạn chưa nhập ngày cấp giấy tờ !')
      //     this.onclickTab(1)
      //     this.$refs.ThongTinKhachHang.$refs.txtNgayCap.focus()
      //     return false
      //   }
      //   if (this.modelInput.txtHoKhau == '' && this.modelInput.txtSoGT == '') {
      //     this.$toast.warning('Bạn chưa nhập hộ khẩu hoặc loại giấy tờ khác !')
      //     this.$refs.ThongTinKhachHang.$refs.txtHoKhau.focus()
      //     return false
      //   }
      //   if (this.modelInput.txtNguoiDD == '') {
      //     this.$toast.warning('Hãy nhập tên người đại diện !')
      //     this.onclickTab(1)
      //     this.$refs.ThongTinKhachHang.$refs.txtNguoiDD.focus()
      //     return false
      //   }
      //   if (this.modelInput.txtNgayCap == '') {
      //     this.onclickTab(1)
      //     this.$toast.warning('Bạn chưa nhập ngày cấp giấy tờ !')
      //     this.$refs.ThongTinKhachHang.$refs.txtNgayCap.focus()
      //     return false
      //   }
      //   if (this.modelInput.txtNgaySN == '') {
      //     this.$toast.warning('Bạn chưa nhập ngày sinh khách hàng !')
      //     this.onclickTab(1)
      //     this.$refs.ThongTinKhachHang.$refs.txtNgaySN.focus()
      //     return false
      //   }
      //   // console.log('🚀 ~ file: index.vue:2671 ~ KiemTraDL_KhachHang ~ this.modelInput.txtNgaySN:', this.modelInput.txtNgaySN)
      //   // console.log('🚀 ~ file: index.vue:2672 ~ KiemTraDL_KhachHang ~ this.modelCbo.cboGiayToKH:', this.modelCbo.cboGiayToKH)
      //   // if (this.modelInput.txtNgaySN !== '') {
      //   //   if (this.kieukh_canhan || parseInt(this.modelCbo.cboGiayToKH) === Enum.LoaiGiayTo.CHUNG_MINH_THU)// Nếu là khách hàng cá nhân thì kiểm tra đã đủ 18 tuổi chưa
      //   //   {
      //   //     if (await this.KIEMTRA_NGAYSINH(this.modelInput.txtNgaySN) === 0) {
      //   //       this.onclickTab(1)
      //   //       this.$toast.warning('Khách hàng chưa đủ 18 tuổi để làm hợp đồng!')
      //   //       this.$refs.ThongTinKhachHang.$refs.txtNgaySN.focus()
      //   //       return false
      //   //     }
      //   //   }
      //   // }
      //   // return false
      //   if (this.TNC1_ID == 0 || this.TNC2_ID == 0 || this.TNC3_ID == 0) {
      //     this.$toast.warning('Bạn chưa chọn chi tiết ngành nghề.Hãy kiểm tra lại !')
      //     this.onclickTab(1)
      //     return false
      //   }
      //   // Hieutc: 11.10.2011: Kiểm tra mã khách hàng đã theo đúng chuẩn chưa
      //   if (this.modelInput.txtMaKH != '') {
      //     if (await this.Kiemtra_makh_mtt(this.modelInput.txtMaKH, 1, Enum.LoaiHopDong.DI_CHUYEN) == 0) {
      //       this.$toast.warning('Mã khách hàng không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
      //       this.$refs.ThongTinKhachHang.$refs.txtMaKH.focus()
      //       return false
      //     }
      //   }
      //   if (this.modelInput.txtMaGD != '') {
      //     if (await this.Kiemtra_makh_mtt(this.modelInput.txtMaGD, 3, Enum.LoaiHopDong.DI_CHUYEN) == 0) {
      //       this.$toast.warning('Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
      //       this.$refs.ThongTinThueBao.$refs.txtMaGD.focus()
      //       return false
      //     }
      //   }
      //   if (this.modelInput.txtTelKH == '') {
      //     this.$toast.warning('Hãy nhập số điện thoại của khách hàng!')
      //     this.onclickTab(1)
      //     this.$refs.ThongTinKhachHang.$refs.txtTelKH.focus()
      //     return false
      //   }
      //   if (this.modelInput.txtTelKH.length < 7) {
      //     this.$toast.warning('Số điện thoại của khách hàng không đúng định dạng! Độ dài tối thiểu 7 chữ số.')
      //     this.onclickTab(1)
      //     this.$refs.ThongTinKhachHang.$refs.txtTelKH.focus()
      //     return false
      //   }
      //   if (this.modelInput.txtTelKH.length > 10) {
      //     this.$toast.warning('Số điện thoại liên hệ của khách hàng không được quá 10 ký tự.')
      //     this.onclickTab(1)
      //     this.$refs.ThongTinKhachHang.$refs.txtTelKH.focus()
      //     return false
      //   }
      //   if (!this.isNumber(this.modelInput.txtTelKH)) {
      //     this.$toast.warning('Số điện thoại liên hệ của khách hàng phải là số!')
      //     this.onclickTab(1)
      //     this.$refs.ThongTinKhachHang.$refs.txtTelKH.focus()
      //     return false
      //   }
      //   console.log('🚀 ~ file: index.vue ~ line 2352 ~ KiemTraDL_KhachHang ~ this.khong_batbuoc_ctv', this.khong_batbuoc_ctv)
      //   // Test
      //   this.khong_batbuoc_ctv = 1
      //   // Test
      //   if (this.khong_batbuoc_ctv == 0) {
      //     if (!this.modelCkeckBox.chkCTV || (this.modelInput.txtCTV === '') || this.ctv_id == 0) {
      //       this.$toast.warning('Bạn chưa chọn nhân viên phát triển!')
      //       this.$refs.txtCTV.focus()
      //       return false
      //     }
      //   }
      //   if (this.batbuoc_gioithieu_dichchuyen == 1) {
      //     if (!this.modelCkeckBox.chkNguoiGT || (this.modelInput.txtNguoiGT === '') || this.nguoigt_id == 0) {
      //       this.$toast.warning('Bạn chưa chọn người giới thiệu hợp đồng/phụ lục')
      //       this.$refs.txtNguoiGT.focus()
      //       return false
      //     }
      //   }
      //   if (this.modelCkeckBox.chkCTV && (this.modelInput.txtCTV === '') && this.ctv_id == 0) {
      //     this.$toast.warning('Bạn chưa chọn nhân viên phát triển!')
      //     this.$refs.txtCTV.focus()
      //     return false
      //   }
      //   if (this.modelCkeckBox.chkNguoiGT && this.modelInput.txtNguoiGT === '' && this.nguoigt_id == 0) {
      //     this.$toast.warning('Bạn chưa chọn nhân viên giới thiệu hợp đồng!')
      //     this.$refs.txtNguoiGT.focus()
      //     return false
      //   }
      //   return true
      // } catch (error) {
      //   console.log('🚀 ~ file: index.vue ~ line 2375 ~ KiemTraDL_KhachHang ~ error', error)
      //   return false
      // }

      if (this.modelInput.txtTenKH == '') {
        this.$toast.warning('Bạn chưa nhập tên khách hàng !')
        this.onclickTab(1)
        this.$refs.ThongTinKhachHang.$refs.txtTenKH.focus()
        return false
      }

      if (this.modelInput.txtDiaChiKH === '' || this.quankh_id === 0 || this.phuongkh_id === 0 || this.tinhkh_id === 0 || this.tinhkh_id === null) {
        this.$toast.warning('Bạn chưa nhập địa chỉ khách hàng !')
        this.onclickTab(1)
        this.$refs.ThongTinKhachHang.$refs.txtDiaChiKH.focus()
        return false
      }
      if (this.modelInput.txtMaHDcu == '') {
        this.$toast.warning('Bạn chưa nhập mã hợp đồng cho thuê bao dịch chuyển!')
        this.onclickTab(0)
        this.$refs.ThongTinThueBao.$refs.txtMaHDcu.focus()
        return false
      }
      if (this.modelInput.txtSoGT == '') {
        this.$toast.warning('Hãy nhập số giấy tờ !')
        this.onclickTab(1)
        this.$refs.ThongTinKhachHang.$refs.txtSoGT.focus()
        return false
      }
      if (this.modelInput.txtNgayCap == '') {
        this.$toast.warning('Bạn chưa nhập ngày cấp giấy tờ !')
        this.onclickTab(1)
        this.$refs.ThongTinKhachHang.$refs.txtNgayCap.focus()
        return false
      }
      if (this.modelInput.txtHoKhau == '' && this.modelInput.txtSoGT == '') {
        this.$toast.warning('Bạn chưa nhập hộ khẩu hoặc loại giấy tờ khác !')
        this.$refs.ThongTinKhachHang.$refs.txtHoKhau.focus()
        return false
      }
      if (this.modelInput.txtNguoiDD == '') {
        this.$toast.warning('Hãy nhập tên người đại diện !')
        this.onclickTab(1)
        this.$refs.ThongTinKhachHang.$refs.txtNguoiDD.focus()
        return false
      }
      if (this.modelInput.txtNgayCap == '') {
        this.onclickTab(1)
        this.$toast.warning('Bạn chưa nhập ngày cấp giấy tờ !')
        this.$refs.ThongTinKhachHang.$refs.txtNgayCap.focus()
        return false
      }
      if (this.modelInput.txtNgaySN == '') {
        this.$toast.warning('Bạn chưa nhập ngày sinh khách hàng !')
        this.onclickTab(1)
        this.$refs.ThongTinKhachHang.$refs.txtNgaySN.focus()
        return false
      }
      // console.log('🚀 ~ file: index.vue:2671 ~ KiemTraDL_KhachHang ~ this.modelInput.txtNgaySN:', this.modelInput.txtNgaySN)
      // console.log('🚀 ~ file: index.vue:2672 ~ KiemTraDL_KhachHang ~ this.modelCbo.cboGiayToKH:', this.modelCbo.cboGiayToKH)
      // if (this.modelInput.txtNgaySN !== '') {
      //   if (this.kieukh_canhan || parseInt(this.modelCbo.cboGiayToKH) === Enum.LoaiGiayTo.CHUNG_MINH_THU)// Nếu là khách hàng cá nhân thì kiểm tra đã đủ 18 tuổi chưa
      //   {
      //     if (await this.KIEMTRA_NGAYSINH(this.modelInput.txtNgaySN) === 0) {
      //       this.onclickTab(1)
      //       this.$toast.warning('Khách hàng chưa đủ 18 tuổi để làm hợp đồng!')
      //       this.$refs.ThongTinKhachHang.$refs.txtNgaySN.focus()
      //       return false
      //     }
      //   }
      // }
      // return false
      if (this.TNC1_ID == 0 || this.TNC2_ID == 0 || this.TNC3_ID == 0) {
        this.$toast.warning('Bạn chưa chọn chi tiết ngành nghề.Hãy kiểm tra lại !')
        this.onclickTab(1)
        return false
      }
      // Hieutc: 11.10.2011: Kiểm tra mã khách hàng đã theo đúng chuẩn chưa
      if (this.modelInput.txtMaKH != '') {
        if (await this.Kiemtra_makh_mtt(this.modelInput.txtMaKH, 1, Enum.LoaiHopDong.DI_CHUYEN) == 0) {
          this.$toast.warning('Mã khách hàng không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
          this.$refs.ThongTinKhachHang.$refs.txtMaKH.focus()
          return false
        }
      }
      if (this.modelInput.txtMaGD != '') {
        if (await this.Kiemtra_makh_mtt(this.modelInput.txtMaGD, 3, Enum.LoaiHopDong.DI_CHUYEN) == 0) {
          this.$toast.warning('Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
          this.$refs.ThongTinThueBao.$refs.txtMaGD.focus()
          return false
        }
      }
      if (this.modelInput.txtTelKH == '' || this.modelInput.txtTelKH === null) {
        this.$toast.warning('Hãy nhập số điện thoại của khách hàng!')
        this.onclickTab(1)
        this.$refs.ThongTinKhachHang.$refs.txtTelKH.focus()
        return false
      }
      if (this.modelInput.txtTelKH.length < 7) {
        this.$toast.warning('Số điện thoại của khách hàng không đúng định dạng! Độ dài tối thiểu 7 chữ số.')
        this.onclickTab(1)
        this.$refs.ThongTinKhachHang.$refs.txtTelKH.focus()
        return false
      }
      if (this.modelInput.txtTelKH.length > 10) {
        this.$toast.warning('Số điện thoại liên hệ của khách hàng không được quá 10 ký tự.')
        this.onclickTab(1)
        this.$refs.ThongTinKhachHang.$refs.txtTelKH.focus()
        return false
      }
      if (!this.isNumber(this.modelInput.txtTelKH)) {
        this.$toast.warning('Số điện thoại liên hệ của khách hàng phải là số!')
        this.onclickTab(1)
        this.$refs.ThongTinKhachHang.$refs.txtTelKH.focus()
        return false
      }
      console.log('🚀 ~ file: index.vue ~ line 2352 ~ KiemTraDL_KhachHang ~ this.khong_batbuoc_ctv', this.khong_batbuoc_ctv)
      // Test
      // this.khong_batbuoc_ctv = 1
      // Test
      if (this.khong_batbuoc_ctv == 0) {
        if (!this.modelCkeckBox.chkCTV || (this.modelInput.txtCTV === '') || this.ctv_id == 0) {
          this.$toast.warning('Bạn chưa chọn nhân viên phát triển!')
          this.$refs.txtCTV.focus()
          return false
        }
      }
      if (this.batbuoc_gioithieu_dichchuyen == 1) {
        if (!this.modelCkeckBox.chkNguoiGT || (this.modelInput.txtNguoiGT === '') || this.nguoigt_id == 0) {
          this.$toast.warning('Bạn chưa chọn người giới thiệu hợp đồng/phụ lục')
          this.$refs.txtNguoiGT.focus()
          return false
        }
      }
      if (this.modelCkeckBox.chkCTV && (this.modelInput.txtCTV === '') && this.ctv_id == 0) {
        this.$toast.warning('Bạn chưa chọn nhân viên phát triển!')
        this.$refs.txtCTV.focus()
        return false
      }
      if (this.modelCkeckBox.chkNguoiGT && this.modelInput.txtNguoiGT === '' && this.nguoigt_id == 0) {
        this.$toast.warning('Bạn chưa chọn nhân viên giới thiệu hợp đồng!')
        this.$refs.txtNguoiGT.focus()
        return false
      }
      return true
    },
    isNumber (value) {
      var isNumber = /^\d+$/.test(value)
      return isNumber
    },
    async Kiemtra_makh_mtt (ma_kh, kieu, vloaihd_id) {
      try {
        let res = await this.$root.context.get(`/web-hopdong/chuyendich/kiemtra_makh_mtt?ma_kh=${ma_kh}&kieu=${kieu}&vloaihd_id=${vloaihd_id}`)
        console.log('🚀 ~ file: index.vue ~ line 2387 ~ Kiemtra_makh_mtt ~ res', res.data)
        if (res.data && res.data.length > 0) {
          return Number(res.data[0].rs)
        } else {
          return 0
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:2840 ~ Kiemtra_makh_mtt ~ error:', error)
        return 1
      }
    },
    async KIEMTRA_NGAYSINH (ngaysinh) {
      let res = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_ngaysinh', {ngaysinh: ngaysinh})
      return res.data
    },
    async KiemTraDL_ThanhToan () {
      try {
        if (this.modelInput.txtTenTT == '') {
          this.$toast.warning('Hãy nhập tên thanh toán !')
          this.onclickTab(1)
          this.$refs.ThongTinKhachHang.$refs.txtTenTT.focus()
          return false
        }

        if ((this.modelCbo.cboMaDD === -1 || this.modelCbo.cboMaDD === null) && this.modelInput.txtMaTT != '') {
          this.$toast.warning('Hãy chọn mã dại diện !')
          return false
        }

        if (this.modelInput.txtDiaChiTT === '' || this.quantt_id === 0 || this.phuongtt_id === 0 || this.tinhtt_id === 0 || this.tinhtt_id === null) {
          this.$toast.warning('Hãy nhập địa chỉ thanh toán !')
          this.onclickTab(1)
          this.$refs.ThongTinKhachHang.$refs.txtDiaChiTT.focus()
          return false
        }
        if (this.modelInput.txtDiaChiCT == '') {
          this.$toast.warning('Hãy nhập địa chỉ chứng từ !')
          this.onclickTab(1)
          this.$refs.ThongTinKhachHang.$refs.txtDiaChiCT.focus()
          return false
        }
        // Kiểm tra matt:Hiếu: 10/08/2010

        let kt_matt = await this.KIEMTRA_MATT(this.modelInput.txtMaTT, this.thanhtoan_id)
        if (kt_matt[0].rs) {
          this.$root.toastError('Kiểm tra lại mã thanh toán !')
          this.$refs.ThongTinKhachHang.$refs.txtMaTT.focus()
          return false
        }
        if (this.modelInput.txtMaNVTC == '') {
          this.onclickTab(1)
          this.$toast.warning('Hãy nhập mã nhân viên thu cước !')
          this.$refs.ThongTinKhachHang.$refs.txtMaNVTC.focus()
          return false
        }
        return true
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2393 ~ KiemTraDL_ThanhToan ~ error', error)
        return false
      }
    },
    async  KIEMTRA_MATT (vma_tt, vthanhtoan_id) {
      console.log('🚀 ~ file: index.vue ~ line 2443 ~ KIEMTRA_MATT ~ vma_tt, vthanhtoan_id', vma_tt, vthanhtoan_id)
      let res = await this.$root.context.get(`/web-hopdong/chuyendich/kiemtra_matt?vma_tt=${vma_tt}&vthanhtoan_id=${vthanhtoan_id}`)
      console.log('🚀 ~ file: index.vue ~ line 2444 ~ KIEMTRA_MATT ~ res', res)
      return res.data
    },
    async KiemTraDL_ThueBao () {
      try {
        // #region Kiểm tra các điều kiện trên giao diện
        if (this.modelInput.txtMaHDcu === '' && this.khachhang_id != 0) {
          this.onclickTab(0)
          this.$refs.ThongTinThueBao.$refs.txtMaHDcu.focus()
          this.$toast.error('Bạn chưa nhập mã hợp đồng !')
          return false
        }

        if (this.modelInput.txtTenTB === '') {
          this.onclickTab(0)
          this.$refs.ThongTinThueBao.$refs.txtTenTB.focus()
          this.$toast.error('Bạn chưa nhập tên thuê bao !')
          return false
        }

        if (this.modelInput.txtDiaChiTB == '' || this.quantb_id === 0 || this.phuongtb_id === 0 || this.tinhtb_id === 0 || this.tinhtb_id === null) {
          this.onclickTab(0)
          this.$refs.ThongTinThueBao.$refs.txtDiaChiTB.focus()
          this.$toast.error('Bạn chưa nhập địa chỉ thuê bao !')
          return false
        }
        if (this.modelInput.txtDiaChiLD == '' || this.quanld_id === 0 || this.phuongld_id === 0 || this.tinhld_id === 0 || this.tinhld_id === null) {
          this.onclickTab(0)
          this.$refs.ThongTinThueBao.$refs.txtDiaChiLD.focus()
          this.$toast.error('Bạn chưa nhập địa chỉ lắp đặt !')
          return false
        }
        if (this.modelInput.txtNgaySinhTB == '') {
          this.onclickTab(0)
          this.$toast.error('Hãy nhập ngày sinh thuê bao !')
          this.$refs.ThongTinThueBao.$refs.txtNgaySinhTB.focus()
          return false
        }
        if (this.modelCbo.cboKieuLD == null || this.modelCbo.cboKieuLD == -1) {
          this.$toast.error('Hãy chọn kiểu yêu cầu cho thuê bao này !')
          return false
        }

        if (this.dichvuvt_id == Enum.DichVuVienThong.CO_DINH || this.dichvuvt_id == Enum.DichVuVienThong.GPHONE) {
          if (this.modelCbo.cboMucCuocTB == '' || this.modelCbo.cboMucCuocTB == null || this.modelCbo.cboMucCuocTB == -1) {
            this.$toast.error('Hãy nhập mức cước thuê bao!')
            return false
          }
        }
        // longdx:km không có chi tiết không cho cập nhật
        if (this.modelCkeckBox.chkKhuyenMai && this.dsKM_HDTB.length === 0) {
          this.$toast.error('Bạn chưa chọn chi tiết khuyến mại!')
          return false
        }

        // #endregion

        // #region Kiểm tra truyền số liệu
        if (this.dichvuvt_id == Enum.DichVuVienThong.TSL || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM) {
        // Hiếu bổ sung bắt lỗi pải nhập vào địa chỉ đầu, địa chỉ cuối ngày 06/09/2010
          if (this.$refs.ThongTinThueBao.$refs.refTSL.cboTramVTDau.Value == null) {
            this.$toast.error('Hãy chọn đơn vị đầu !')
            this.onclickTab(0)
            return false
          }
          if (this.$refs.ThongTinThueBao.$refs.refTSL.cboTramVTCuoi.Value == null) {
            this.$toast.error('Hãy chọn đơn vị cuối !')
            this.onclickTab(0)
            return false
          }
          if (this.$refs.ThongTinThueBao.$refs.refTSL.txtDiaChiDau.Text == '' || this.quandau_id == 0 || this.phuongdau_id == 0) {
            this.$toast.error('Hãy nhập địa chỉ đầu !')
            this.onclickTab(0)
            return false
          }
          if (this.$refs.ThongTinThueBao.$refs.refTSL.txtDiaChiCuoi.Text == '' || this.quancuoi_id == 0 || this.phuongcuoi_id == 0) {
            this.$toast.error('Hãy nhập địa chỉ cuối !')
            this.onclickTab(0)
            return false
          }
          if (this.$refs.ThongTinThueBao.$refs.refTSL.chkDC_Dau.Checked == false && this.$refs.ThongTinThueBao.$refs.refTSL.chkDC_Cuoi.Checked == false) {
            this.$toast.error('Bạn chưa chọn thay đổi địa chỉ đầu hoặc cuối !')
            this.onclickTab(0)
            return false
          }
        }
        // #endregion

        // #region Kiểm tra mã truy nhập
        // HieuTC - 23/09/2010 Kiểm tra khi kiểu lắp đặt trên đường có sẵn => mã truy nhập không đc trống.

        let kieu = await this.MAP_ID_KIEULD_ID(parseInt(this.modelCbo.cboKieuLD))
        if ((kieu == 1 || kieu == 2 || kieu == 4) && parseInt(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.CO_DINH && this.$refs.ThongTinThueBao.$refs.refCD.txtMaTN_CD.Text === '') {
          this.$toast.warning('Khi kiểu lắp đặt trên đường có sẵn, mã truy nhập không được trống!')
          this.onclickTab(0)
          // this.$refs.ThongTinThueBao.$refs.refCD.txtMaTN_CD.focus()
          return false
        }
        if ((kieu == 1 || kieu == 2 || kieu == 4) && parseInt(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.IMS && this.$refs.ThongTinThueBao.$refs.refIMS.txtMATN_IMS.Text === '') {
          this.$toast.warning('Khi kiểu lắp đặt trên đường có sẵn, mã truy nhập không được trống!')
          // this.$refs.ThongTinThueBao.$refs.refIMS.txtMATN_IMS.focus()
          this.onclickTab(0)
          return false
        }
        if ((kieu == 1 || kieu == 2 || kieu == 4) && parseInt(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.ADSL && this.$refs.ThongTinThueBao.$refs.refAdsl.txtMaTN_ADSL.Text == '') {
          this.$toast.warning('Khi kiểu lắp đặt trên đường có sẵn, mã truy nhập không được trống!')
          this.onclickTab(0)
          // this.$refs.ThongTinThueBao.$refs.refAdsl.txtMaTN_ADSL.focus();
          return false
        }
        if ((kieu == 1 || kieu == 2 || kieu == 4) &&
                (parseInt(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.MEGAWAN || 
                  parseInt(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.METRONET ||
                  parseInt(this.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.NOIBO_DADIEM) &&
                this.$refs.ThongTinThueBao.$refs.refWan.txtMaTN_Wan.Text === '') {
          this.$toast.warning('Khi kiểu lắp đặt trên đường có sẵn, mã truy nhập không được trống!')
          this.onclickTab(0)
          // this.$refs.ThongTinThueBao.$refs.refWan.txtMaTN_Wan.focus();
          return false
        }
        // #endregion

        // #region Kiểm tra nếu dịch chuyển khác trạm thì phải đổi số
        if (this.dichvuvt_id == Enum.DichVuVienThong.CO_DINH) {
          let tg_thueso = await this.MAP_ID('TG_THUESO', this.thuebao_id)
          if (tg_thueso && tg_thueso.toString() != '-1') {
            this.$toast.warning('Bạn không được lập phụ lục dịch chuyển cho thuê bao thuê số !')
            return false
          }

          let nhomkieu_ld = await this.MAP_ID('NHOMKIEU_LD', parseInt(this.modelCbo.cboKieuLD))
          if (nhomkieu_ld && nhomkieu_ld.toString() == '1') {
            if (this.kieu_thueso == 0) {
              this.$toast.warning('Bạn chưa nhập thông tin thuê số !')
              this.$refs.ThongTinThueBao.$refs.btnThueSo.focus()
              return false
            }
            if (this.sothang_thueso == 0) {
              this.$toast.warning('Bạn chưa nhập thời gian thuê số !')
              this.$refs.ThongTinThueBao.$refs.btnThueSo.focus()
              return false
            }
            if (this.kieu_thueso == 1 && this.noidung_thueso == '') {
              this.$toast.warning('Bạn chưa nhập số trượt !')
              this.$refs.ThongTinThueBao.$refs.btnThueSo.focus()
              return false
            }
            if (this.kieu_thueso == 2 && this.noidung_thueso == '') {
              this.$toast.warning('Bạn chưa nhập nội dung thông báo !')
              this.$refs.ThongTinThueBao.$refs.btnThueSo.focus()
              return false
            }
          }
          // Kiểm tra di chuyển đổi số cố định
          if (this.modelInput.txtMaTBMoi == '') {
            if (await this.KIEMTRA_DOISO(this.modelInput.txtMaTB, parseInt(this.modelCbo.cboDichVuVT), this.quanld_id, this.phuongld_id) <= 0) {
              this.$toast.error('Thuê bao ' + this.modelInput.txtMaTB + ' di chuyển sang khu vực mới. Bạn hãy chọn số mới')
              this.$refs.ThongTinThueBao.$refs.btnChonSM.focus()
              return false
            }
          }
        }
        // #endregion

        if (this.dichvuvt_id == Enum.DichVuVienThong.MEGAWAN || 
            this.dichvuvt_id == Enum.DichVuVienThong.METRONET ||
            this.dichvuvt_id == Enum.DichVuVienThong.NOIBO_DADIEM) {
          if (this.kt_kenhphu_ao == true) {
            if (parseInt(this.modelCbo.cboKieuLD) != Enum.KieuLapDat.DICHCHUYEN_KP_AO) {
              this.$toast.warning('Thuê bao ' + this.modelInput.txtMaTB + " là kênh phụ ảo. Bạn phải chọn kiểu lắp đặt là 'Dịch chuyển kênh phụ ảo' !")
              return false
            }
          } else {
            if (parseInt(this.modelCbo.cboKieuLD) == Enum.KieuLapDat.DICHCHUYEN_KP_AO) {
              this.$toast.warning("Bạn không được chọn kiểu lắp đặt 'Dịch chuyển kênh phụ ảo' cho thuê bao '" + this.modelInput.txtMaTB + "' !")
              return false
            }
          }
        }
        let dtControl = await this.getControl()
        if (dtControl && dtControl.length > 0)  {
          for (let index = 0; index < dtControl.dsComboDonVi.length; index++) {
            const element = dtControl.dsComboDonVi[index]
            console.log('🚀 ~ file: index.vue ~ line 2401 ~ KiemTraDL_ThueBao ~ element', element)
            if (dtControl.ref.$refs[element.key][0].value === null || dtControl.ref.$refs[element.key][0].value === '') {
              this.$toast.error('Chưa có dữ liệu về đơn vị thi công. Bạn hãy kiểm tra lại !')
              return false
            }
          }
        }
        
        return true
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2625 ~ KiemTraDL_ThueBao ~ error', error)
        return false
      }
    },
    async MAP_ID_KIEULD_ID (kieuld_id) {
      try {
        let res = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/map_id_kieuld_id', {kieuld_id})
        if (res.data.length > 0) {
          return parseInt(res.data[0].kieu)
        } else return -1
      } catch (error) {
        return -1
      }
    },
    async  getControl () {
      let dtControl = {}
      if (this.dichvuvt_id === Enum.DichVuVienThong.CO_DINH) { dtControl = {ref: this.$refs.ThongTinThueBao.$refs.refCD, dsComboDonVi: this.$refs.ThongTinThueBao.$refs.refCD.dsComboDonVi} }
      if (this.dichvuvt_id === Enum.DichVuVienThong.IMS || this.dichvuvt_id === Enum.DichVuVienThong.IMS_DOITAC) { dtControl = {ref: this.$refs.ThongTinThueBao.$refs.refIMS, dsComboDonVi: this.$refs.ThongTinThueBao.$refs.refIMS.dsComboDonVi} }
      if (this.dichvuvt_id === Enum.DichVuVienThong.ADSL || this.dichvuvt_id === Enum.DichVuVienThong.BRCD_DOITAC) { dtControl = {ref: this.$refs.ThongTinThueBao.$refs.refAdsl, dsComboDonVi: this.$refs.ThongTinThueBao.$refs.refAdsl.dsComboDonVi} }
      if (this.dichvuvt_id === Enum.DichVuVienThong.MEGA_EYES) { dtControl = {ref: this.$refs.ThongTinThueBao.$refs.refAdsl, dsComboDonVi: this.$refs.ThongTinThueBao.$refs.refAdsl.dsComboDonVi} }
      if (this.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || this.dichvuvt_id === Enum.DichVuVienThong.METRONET || this.dichvuvt_id === Enum.DichVuVienThong.MGW_DOITAC || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_DADIEM) { dtControl = {ref: this.$refs.ThongTinThueBao.$refs.refWan, dsComboDonVi: this.$refs.ThongTinThueBao.$refs.refWan.dsComboDonVi} }
      if (this.dichvuvt_id === Enum.DichVuVienThong.TSL || this.dichvuvt_id === Enum.DichVuVienThong.TSL_DOITAC || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM) { dtControl = {ref: this.$refs.ThongTinThueBao.$refs.refTSL, dsComboDonVi: this.$refs.ThongTinThueBao.$refs.refTSL.dsComboDonVi} }
      if (this.dichvuvt_id === Enum.DichVuVienThong.GPHONE) { dtControl = {ref: this.$refs.ThongTinThueBao.$refs.refGP, dsComboDonVi: this.$refs.ThongTinThueBao.$refs.refGP.dsComboDonVi} }
      return dtControl
    },
    async KIEMTRA_DOISO (ma_tb, dichvuvt_id, quan_id, phuong_id) {
      try {
        var rs = await this.$root.context.post(
          '/web-hopdong/chuyendich/kiemtra_doiso', {ma_tb, dichvuvt_id, quan_id, phuong_id}
        )
        return rs.data
      } catch (error) {
        return 0
      }
    },
    async XoaHDKH () {
      try {
        this.loading(true)
        let kt = await this.hdbancheo_xoa_HDBC(this.hdkh_id, Enum.LoaiHopDong.DI_CHUYEN)
        if (!kt) {
          this.$root.$toast.error('Lỗi thực hiện xóa hợp đồng dịch chuyển bán chéo')
          return
        }
        kt = await this.XOA_HDKH_V2(this.hdkh_id, Enum.LoaiHopDong.DI_CHUYEN)
        if (!kt) {
          this.$root.$toast.error('Lỗi thực hiện xóa hợp đồng dịch chuyển bán chéo')
          return
        }
        if (this.modelInput.txtMaTBMoi !== '' && this.modelInput.txtMaTBMoi != null) { await this.CAPNHAT_TRANGTHAISO(this.modelInput.txtMaTBMoi, 1, this.dichvuvt_id) }
        await this.SetButton(0)
        this.$root.$toast.success('Xóa hợp đồng dịch chuyển bán chéo thành công')
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2437 ~ XoaHDKH ~ error', error)
      } finally {
        this.loading(false)
      }
    },
    async CAPNHAT_TRANGTHAISO (ma_tb, trangthai_so, dichvuvt_id) {
      try {
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/capnhat_trangthaiso', {ma_tb, trangthai_so, dichvuvt_id})
        console.log('🚀 ~ file: index.vue ~ line 2960 ~ CAPNHAT_TRANGTHAISO ~ rs', rs)
        return true
      } catch (error) {
        return false
      }
    },
    async hdbancheo_xoa_HDBC (vhdtbid, vloaihdid) {
      try {
        var rs = await this.$root.context.post(
          '/web-hopdong/ds_yeucau_khaosat/xoa_hdbc', {vhdtbid, vloaihdid}
        )
        console.log('🚀 ~ file: index.vue ~ line 2448 ~ hdbancheo_xoa_HDBC ~ rs', rs)
        return true
      } catch (error) {
        return false
      }
    },
    async XOA_HDKH_V2 (vhdkh_id, vloaihd_id) {
      try {
        var rs = await this.$root.context.post('/web-hopdong/lapdatmoi-csht/xoa_hdkd_v2', {vhdkh_id, vloaihd_id})
        console.log('🚀 ~ file: index.vue ~ line 2470 ~ XOA_HDKH_V2 ~ rs', rs)
        return true
      } catch (error) {
        return false
      }
    },
    async KiemTraDuLieuNhap () { return true },
    async tsbtnHuyBo_Click () {
      await this.SetButton(0)
    },
    async tsbtnXoa_Click () {
      try {
        let confirm = await this.$confirm('Bạn thật sự muốn xóa hợp đồng không ?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        })
        this.loading(true)
        await this.XoaHDKH()
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2439 ~ tsbtnXoa_Click ~ error', error)
      } finally {
        this.loading(false)
      }
    },
    async tsbtnThanhToan_Click () {
      // this.$refs.dialogPayment.show()
      // this.$refs.dialogPayment.HienThiTTHopDongKH()
      try {
        let code = await this.KIEMTRA_THANHTOAN_LH(this.hdkh_id)
        if (code.toString() == '0') {
          this.$root.$toast.warning('Hợp đồng chưa có thông tin liên hệ, bạn phải bổ sung thông tin liên hệ trước khi thanh toán !')
          return
        } else if (code.toString() == '2') {
          this.$root.$toast.warning('Phải chọn mục đích sử dụng là Liên Hệ cho tất cả các thuê bao!')
          return
        }
        this.$refs.dialogPayment.show()
        this.$refs.dialogPayment.HienThiTTHopDongKH()
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 3042 ~ tsbtnThanhToan_Click ~ error', error)
      }
    },
    async KIEMTRA_THANHTOAN_LH (hdkh_id) {
      try {
        let rs = await this.$root.context.get('/web-hopdong/hopdong/kiemtra_thanhtoan_lh?hdkhId=' + hdkh_id)
        return rs.data
      } catch (error) {
        return null
      }
    },
    tsbtnInPL_Click () {
      if (this.hdkh_id && this.hdkh_id > 0) {
        this.popupInHoSo_Param = {
          hdkh_id: this.hdkh_id,
          ma_gd: this.modelInput.txtMaGD,
          ngaylap_hd: DateTimeLib.toDateDisplay(this.modelDateTime.dtpNgayLapHD),
          ngay_yc: DateTimeLib.toDateDisplay(this.modelDateTime.dtpNgayYC)
        }
        this.$refs.popupInHoSo.showModal()
      } else {
        this.$root.toastError('Chưa có thông tin hợp đồng!')
      }
    },
    btnInHopDong_Click () {},
    tsbtnThemTT_Click () {},
    tsbtnXoaTT_Click () {},
    async tsbtnThemTB_Click () {
      // this.$root.$toast.warning('Không sử dụng chức năng này')
      try {
        this.loading(true)
        await this.ThemHDThueBao()
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 3138 ~ tsbtnThemTB_Click ~ error', error)
      } finally {
        this.loading(false)
      }
    },
    async KIEMTRA_HDTB_KHI_THEMMOI (maTb, hdkhId, dichVuVtId, loaiTbId) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_hdtb_khi_themmoi', {maTb, hdkhId, dichVuVtId, loaiTbId})
        return rs.data.toString() === '1'
      } catch (error) {
        return false
      }
    },
    async ThemHDThueBao () {
      try {
        this.kt_adsl = true
        let kiemtra = await this.KiemTraDL_ThueBao()
        if (!kiemtra) return
        kiemtra = await this.KiemTraDuLieuNhap()
        if (!kiemtra) return
        if (await this.KIEMTRA_HDTB_KHI_THEMMOI(this.modelInput.txtMaTB, this.hdkh_id, this.dichvuvt_id, 0)) {
          this.$root.$toast.warning('Thuê bao đã tồn tại trong hợp đồng, không thêm mới được ')
          return
        }
        if (this.modelCbo.cboKieuLD.toString() === Enum.KieuLapDat.STADSL_DOI_TOCDO.toString()) {
          if (!await this.KIEMTRA_DB_KHI_THEMMOI(this.modelInput.txtMaTB, 0, this.thuebao_id, this.dichvuvt_id)) {
            this.$root.$toast.warning('Thuê bao không tồn tại trong danh bạ, không cập nhật được ')
            return
          }
        } else if (!await this.KIEMTRA_DB_KHI_THEMMOI(this.modelInput.txtMaTB, 0, this.thuebao_id, this.dichvuvt_id)) {
          this.$root.$toast.warning('Thuê bao không tồn tại trong danh bạ, không cập nhật được ')
          return
        }
        if (!await this.KIEMTRA_TB_CUNG_MAKH_TB_PL(this.modelInput.txtMaGD, this.thuebao_id)) {
          this.$root.$toast.warning('Thuê bao không cùng mã khách hàng với thuê bao đang thực hiện chuyển dịch !')
          return
        }
        kiemtra = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.modelInput.txtMaTB, '0', Number(this.modelCbo.cboDichVuVT), Enum.LoaiHopDong.DI_CHUYEN)
        if (kiemtra) return

        this.tocdotn_id = 0
        this.muccuoctn_id = 0
        if (this.dichvuvt_id === Enum.DichVuVienThong.ADSL) {
          this.tocdotn_id = Number(this.$refs.ThongTinThueBao.$refs.refAdsl.cboGoiCuocAdsl.Value)
          if (this.loaitb_id === Enum.LoaiHinhTB.INTERNET_ADSL || this.loaitb_id === Enum.LoaiHinhTB.INTERNET_FTTH ||
                         this.loaitb_id === Enum.LoaiHinhTB.WIFI_FIBER) {
            this.muccuoctn_id = Number(this.$refs.ThongTinThueBao.$refs.refAdsl.cboMucCuoc.Value)
          }
        } else if (this.dichvuvt_id === Enum.DichVuVienThong.MEGA_EYES) {
          this.tocdotn_id = Number(this.$refs.ThongTinThueBao.$refs.refAdsl.cboGoiCuocAdsl.Value)
        } else if (this.dichvuvt_id === Enum.DichVuVienThong.TSL || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM) {
          this.tocdotn_id = Number(this.$refs.ThongTinThueBao.$refs.refTSL.cboTocDoKenhTSL.Value)
        } else if (this.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || this.dichvuvt_id === Enum.DichVuVienThong.METRONET || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_DADIEM) {
          this.tocdotn_id = Number(this.$refs.ThongTinThueBao.$refs.refWan.cboTocDo.Value)
        }
        let dset = await this.LayHuongGiao_TiepNhan_V2(Enum.LoaiHopDong.DI_CHUYEN, Number(this.modelCbo.cboLoaihinhTB), this.tocdotn_id, this.muccuoctn_id, Number(this.modelCbo.cboKieuLD), this.vloaikenh_id, this.dichvuvt_id)
        console.log('🚀 ~ file: index.vue ~ line 3062 ~ ThemHDThueBao ~ dset', dset)
        let huonggiao_tn = 0
        if (dset && dset.length <= 0) {
          this.$root.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
          return
        }
        huonggiao_tn = Number(dset[0]['huonggiao_id'])
        this.quytrinh_id = Number(dset[0]['quytrinh_id'])
        let kt_diachitt = 3
        if (this.hdkh_id != 0) {
          let _ds = await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.hdkh_id)
          if (_ds && _ds.length > 0) {
            let _q = _ds.filter(x => x.thanhtoan_id === this.thanhtoan_id)
            if (_q && _q.length > 0) {
              this.hdtt_id = Number(_q[0].hdtt_id)
            } else {
              await this.TaoDuLieu_HDTT(true)
              kt_diachitt = 5
            }
          } else {
            this.$root.$toast.warning('Không tìm thấy thông tin hợp đồng khách hàng')
            return
          }
        } else {
          await this.TaoDuLieu_HDTT(true)
          kt_diachitt = 5
        }
        await this.TaoDuLieu_HDTB(true)
        await this.TaoDuLieu_CT_TIENHD(true)
        await this.TaoDuLieu_DiaChi(true, kt_diachitt)
        await this.TaoDuLieu_DKDV()
        await this.CapNhat_HDTB_ID_DangKyDV(this.hdtb_id)
        await this.CapNhat_HDTB_ID_HDTB_KV()

        this.dsHDTB_CD = []
        this.dsHDTB_ADSL = []
        this.dsHDTB_TSL = []
        this.dsHDTB_MEWAN = []
        this.dsHDTB_GP = []
        this.dsHDTB_IMS = []
        this.dsHDTBDV = []
        let data = {}
        let themmoi = true
        switch (this.dichvuvt_id) {
          case Enum.DichVuVienThong.CO_DINH:
            data = await this.$refs.ThongTinThueBao.$refs.refCD.TaoDuLieu_HDTB_CD(themmoi)
            this.dsHDTB_CD = data.dsHDTB_CD
            this.dsHDTBDV = data.dsHDTBDV
            break
          case Enum.DichVuVienThong.IMS:
            data = await this.$refs.ThongTinThueBao.$refs.refIMS.TaoDuLieu_HDTB_IMS(themmoi)
            this.dsHDTB_IMS = data.dsHDTB_IMS
            this.dsHDTBDV = data.dsHDTBDV
            break
          case Enum.DichVuVienThong.ADSL:
            data = await this.$refs.ThongTinThueBao.$refs.refAdsl.TaoDuLieu_HDTB_ADSL(themmoi)
            this.dsHDTB_ADSL = data.dsHDTB_ADSL
            this.dsHDTBDV = data.dsHDTBDV
            break
          case Enum.DichVuVienThong.MEGA_EYES:
            data = await this.$refs.ThongTinThueBao.$refs.refAdsl.TaoDuLieu_HDTB_ADSL(themmoi)
            this.dsHDTB_ADSL = data.dsHDTB_ADSL
            this.dsHDTBDV = data.dsHDTBDV
            break
          case Enum.DichVuVienThong.MEGAWAN:
            data = await this.$refs.ThongTinThueBao.$refs.refWan.TaoDuLieu_HDTB_MGWAN(themmoi)
            console.log('🚀 ~ file: index.vue:3502 ~ ThemHDThueBao ~ data:', data)
            this.dsHDTB_MEWAN = data.dsHDTB_MEWAN
            this.dsHDTBDV = data.dsHDTBDV
            break
          case Enum.DichVuVienThong.METRONET:
          case Enum.DichVuVienThong.NOIBO_DADIEM:
            data = await this.$refs.ThongTinThueBao.$refs.refWan.TaoDuLieu_HDTB_MGWAN(themmoi)
            console.log('🚀 ~ file: index.vue:3502 ~ ThemHDThueBao ~ data:', data)
            this.dsHDTB_MEWAN = data.dsHDTB_MEWAN
            this.dsHDTBDV = data.dsHDTBDV
            break
          case Enum.DichVuVienThong.TSL:
          case Enum.DichVuVienThong.NOIBO_2DIEM:
            data = await this.$refs.ThongTinThueBao.$refs.refTSL.TaoDuLieu_HDTB_TSL(themmoi)
            this.dsHDTB_TSL = data.dsHDTB_TSL
            this.dsHDTBDV = data.dsHDTBDV
            break
          case Enum.DichVuVienThong.GPHONE:
            data = await this.$refs.ThongTinThueBao.$refs.refGP.TaoDuLieu_HDTB_GP(themmoi)
            this.dsHDTB_GP = data.dsHDTB_GP
            this.dsHDTBDV = data.dsHDTBDV
            break
        }
        if (this.kt_adsl === false) { return }
        let kt = false
        if (kt_diachitt === 3) {
          kt = await this.dichchuyen_ThemMoi_HopDongTB(this.dsHDTB, this.dsHDTBDV, this.dsHDTB_CD, this.dsHDTB_ADSL, this.dsHDTB_TSL, this.dsDiaChi, this.dsDiaChiHDTB, this.dsDK_DVGT,
            this.dsDK_DVK, this.dsHDTB_MEWAN, this.dsHDTB_GP, this.dsTTTT, this.dsCTTHD, [], this.dsHDTB_IMS, huonggiao_tn,
            this.dsHDTB_KV, this.dsDiaChiHDTT, this.dsKM_HDTB)
        } else {
          kt = await this.dichchuyen_ThemMoi_HopDongTB(this.dsHDTB, this.dsHDTBDV, this.dsHDTB_CD, this.dsHDTB_ADSL, this.dsHDTB_TSL, this.dsDiaChi, this.dsDiaChiHDTB, this.dsDK_DVGT,
            this.dsDK_DVK, this.dsHDTB_MEWAN, this.dsHDTB_GP, this.dsTTTT, this.dsCTTHD, this.dsHDTT, this.dsHDTB_IMS, huonggiao_tn,
            this.dsHDTB_KV, this.dsDiaChiHDTT, this.dsKM_HDTB)
        }
        if (!kt) {
          return
        }
        await this.TaoDuLieuBC(true)

        if (this.BanCheoData.Tinh_TC.tinhthicong_id > 0) {
          kt = await this.hdbancheo_themmoi_HDBANCHEO_BIENDONG_V3(this.dsHDBC, this.dsHDTBBC, this.dsHDBCMGWAN, this.dsHDBCADSL, this.dsHDBCCD, this.dsDKBCDVGT, this.dsHDBCTSL, this.dsHDBCIMS, this.dsLYDOHUYBC, this.dsDiaChi_BanCheo)
        }
        if (this.modelInput.txtMaTBMoi !== '') { await this.CAPNHAT_KHOSO(this.modelInput.txtMaTBMoi, '', this.dichvuvt_id, this.$root.token.getDonViID()) } else { await this.CAPNHAT_KHOSO(this.modelInput.txtMaTB, '', this.dichvuvt_id, this.$root.token.getDonViID()) }
        this.ma_tb = this.modelInput.txtMaTB
        this.$root.$toast.success('Thêm thuê bao thành công!')
        await this.Them_MyTV_vao_goi_giadinh()
        if (this.tudong_ganthem_hdtb_dv) { await this.tudong_ganthem_hdtb_dv(this.hdtb_id) }
        await this.HienThiDS_HDTT_CBO(this.hdkh_id)
        await this.HienThiDanhSacHDTT(await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.hdkh_id))
        await this.HienThiDanhSacHDTB(await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id))
        this.ma_tb = this.modelInput.txtMaTB
        this.kieuld_ds = Number(this.modelCbo.cboKieuLD)
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 3016 ~ ThemHDThueBao ~ error', error)
      }
    },
    async KIEMTRA_TB_CUNG_MAKH_TB_PL (ma_gd, thuebao_id) {
      try {
        let data = {maTb: ma_gd, thuebaoId: thuebao_id}
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_tb_cung_makh_tb_pl', data)
        return rs.data.toString() === '1'
      } catch (error) {
        return false
      }
    },
    async tsbtnXoaTB_Click () {
      try {
        if (this.modelInput.lvwThueBao.length <= 0) {
          this.$root.$toast.error('Không có dữ liệu để xóa!')
          return
        }
        let confirm = await this.$confirm('Bạn thật sự muốn xóa thuê bao không ?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        })
        this.loading(true)
        await this.XoaHDTB()
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2506 ~ tsbtnXoaTB_Click ~ error', error)
      } finally {
        this.loading(false)
      }
    },
    async XoaHDTB () {
      try {
        let kt = await this.hdbancheo_xoa_HDBC_HDTB(this.hdtb_id, Enum.LoaiHopDong.DI_CHUYEN)
        if (!kt) {
          this.loading(false)
          this.$root.$toast.error('Lỗi thực hiện xóa thuê bao dịch chuyển bán chéo.')
          return
        }
        kt = await this.XOA_HDTB(this.hdtb_id)
        if (!kt) {
          this.loading(false)
          this.$root.$toast.error('Lỗi thực hiện xóa thuê bao dịch chuyển bán chéo.')
          return
        }
        this.$root.$toast.success('Xóa thuê bao dịch chuyển bán chéo thành công!')
        let ds = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id)
        await this.HienThiDanhSacHDTB(ds)
        if (this.modelInput.lvwThueBao.length > 0) {
          this.hdtb_id = parseInt(this.modelInput.lvwThueBao[0].hdtb_id)
          await this.$refs.ThongTinThueBao.HienThiTTHopDongTB(await this.LAY_DS_HOPDONG_TB_THEO_HDTB_ID(this.hdtb_id))
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2513 ~ XoaHDTB ~ error', error)
      }
    },
    async LAY_DS_HOPDONG_TB_THEO_HDTB_ID (hdtb_id) {
      try {
        var rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/lay_ds_hopdong_tb_theo_hdtb_id', {hdtb_id})
        console.log('🚀 ~ file: index.vue ~ line 3253 ~ LAY_DS_HOPDONG_TB_THEO_HDTB_ID ~ rs', rs)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 3254 ~ LAY_DS_HOPDONG_TB_THEO_HDTB_ID ~ error', error)
        return []
      }
    },
    async hdbancheo_xoa_HDBC_HDTB (vhdtbid, vloaihdid) {
      try {
        var rs = await this.$root.context.post(
          '/web-hopdong/chuyen-dich-ban-cheo/xoa_hdbc_hdtb', {vhdtbid, vloaihdid}
        )
        console.log('🚀 ~ file: index.vue ~ line 2527 ~ hdbancheo_xoa_HDBC_HDTB ~ rs', rs)
        return true
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2526 ~ hdbancheo_xoa_HDBC_HDTB ~ error', error)
        return false
      }
    },
    async XOA_HDTB (hdtbId) {
      console.log('🚀 ~ file: index.vue ~ line 3367 ~ XOA_HDTB ~ hdtbId', hdtbId)
      try {
        var rs = await this.$root.context.post(
          '/web-hopdong/hopdong/xoa_hopdong_thuebao?hdtbId=' + hdtbId
        )
        console.log('🚀 ~ file: index.vue ~ line 2527 ~ XOA_HDTB ~ rs', rs)
        return true
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2526 ~ XOA_HDTB ~ error', error)
        return false
      }
    },
    tsbtnThemTB_DS_Click () {
      this.$root.$toast.warning('Không sử dụng chức năng này')
    },
    tsbtnThongTinLH_Click () {
      try {
        if (this.hdkh_id != 0 && this.hdkh_id != null) {
          this.$refs.thongtinlienhe.show()
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 3376 ~ tsbtnThongTinLH_Click ~ error', error)
      }
    },
    tsbtnEmail_Click () {
      try {
        if (this.hdkh_id != 0 && this.hdkh_id != null) {
          this.$refs.thongtinemail.show()
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 3381 ~ tsbtnEmail_Click ~ error', error)
      }
    },
    tsbtnHen_Click () {
      try {
        if (this.hdkh_id != 0 && this.hdkh_id != null) {
          this.$refs.thongTinHenKhachHangModal.showModal()
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 3390 ~ tsbtnHen_Click ~ error', error)
      }
    },
    async chkCTV_Click () {
      this.$nextTick(() => {
        try {
          if (!this.modelCkeckBox.chkCTV) {
            this.$refs.congtacvien.show()
            this.$refs.ref_congtacvien.nhomlnv_id = 6
            this.$refs.ref_congtacvien.donvi_id = this.donvi_ctv_id
            this.$refs.ref_congtacvien.loainv_id = this.loainv_id
            this.$refs.ref_congtacvien.ctv_id = this.ctv_id
          }
        } catch (error) {
          console.log('🚀 ~ file: index.vue ~ line 3068 ~ chkCTV_CheckedChanged ~ error', error)
        }
      })
    },
    async chkCTV_CheckedChanged () {
      // try {
      //   if (this.modelCkeckBox.chkCTV) {
      //     this.$refs.congtacvien.show()
      //     this.$refs.ref_congtacvien.nhomlnv_id = 6
      //     this.$refs.ref_congtacvien.donvi_id = this.donvi_ctv_id
      //     this.$refs.ref_congtacvien.loainv_id = this.loainv_id
      //     this.$refs.ref_congtacvien.ctv_id = this.ctv_id
      //   }
      // } catch (error) {
      //   console.log('🚀 ~ file: index.vue ~ line 3068 ~ chkCTV_CheckedChanged ~ error', error)
      // }
    },
    async chkNguoiGT_Click () {
      this.$nextTick(() => {
        try {
          if (!this.modelCkeckBox.chkNguoiGT) {
            console.log('🚀 ~ file: index.vue ~ line 3354 ~ chkNguoiGT_CheckedChanged ~ this.modelCkeckBox.chkNguoiGT', this.modelCkeckBox.chkNguoiGT)
            this.$refs.nguoigioithieu.show()
            this.$refs.ref_nguoigioithieu.nhomlnv_id = 11
            this.$refs.ref_nguoigioithieu.donvi_id = this.donvi_nguoi_gt_id
            this.$refs.ref_nguoigioithieu.loainv_id = this.loainv_nguoi_gt_id
            this.$refs.ref_nguoigioithieu.ctv_id = this.nguoigt_id
          }
        } catch (error) {
          console.log('🚀 ~ file: index.vue ~ line 3077 ~ chkNguoiGT_CheckedChanged ~ error', error)
        }
      })
    },
    async chkNguoiGT_CheckedChanged () {
      // 	this.$nextTick(() => {
      //   try {
      //     if (this.modelCkeckBox.chkNguoiGT) {
      //       console.log('🚀 ~ file: index.vue ~ line 3354 ~ chkNguoiGT_CheckedChanged ~ this.modelCkeckBox.chkNguoiGT', this.modelCkeckBox.chkNguoiGT)
      //       this.$refs.nguoigioithieu.show()
      //       this.$refs.ref_nguoigioithieu.nhomlnv_id = 11
      //       this.$refs.ref_nguoigioithieu.donvi_id = this.donvi_nguoi_gt_id
      //       this.$refs.ref_nguoigioithieu.loainv_id = this.loainv_nguoi_gt_id
      //       this.$refs.ref_nguoigioithieu.ctv_id = this.nguoigt_id
      //     }
      //   } catch (error) {
      //     console.log('🚀 ~ file: index.vue ~ line 3077 ~ chkNguoiGT_CheckedChanged ~ error', error)
      //   }
      // })
    },
    chkDC_NoiBo_CheckedChanged () {},
    btnThueSo_Click () {},
    btnTKMaTB_Click () {},
    btnChonSM_Click () {},
    btnDiaChiTB_Click () {},
    chkCongVan_CheckedChanged () {},
    chkKhuyenMai_CheckedChanged () {},
    btnChon_KhuyenMai_Click () {},
    btnChiTietKM_Click () {},
    btnMaNVKD_Click () {},
    btnDiachiLD_Click () {},
    btnTien_Click () {},
    lvwThueBao_selectedRowChanged () {},
    lvwThanhToan_selectedRowChanged () {},
    Reset_Thongso_Thuebao () {
      let ketqua = {}
      ketqua.trangbi_id = 0
      ketqua.muccuoc_id = 0
      ketqua.chuquan_id = 0
      ketqua.congnghe_id = 0
      ketqua.doituong_id = 0
      ketqua.kieuld_id = 0
      ketqua.kieutra_id = 0
      ketqua.loaikenh_id = 0
      ketqua.loaimd_id = 0
      ketqua.muccuoctb_id = 0
      ketqua.thietbidc_id = 0
      ketqua.thoihan_id = 0
      ketqua.tocdo_id = 0
      ketqua.tocdo_isp = 0
      ketqua.tocdo_pir_id = 0
      ketqua.tocdothuc_id = 0
      ketqua.trangbi_id = 0
      ketqua.ma_doicap = ''
      ketqua.ma_tn = ''
      return ketqua
    },
    async LayTienTheoKhoanMuc () {
      try {
        if (this.dichvuvt_id !== Enum.DichVuVienThong.ADSL && this.dichvuvt_id !== Enum.DichVuVienThong.MEGAWAN && 
            this.dichvuvt_id !== Enum.DichVuVienThong.METRONET && this.dichvuvt_id !== Enum.DichVuVienThong.NOIBO_DADIEM) {
          this.kh_cd = 0
          this.dsltb_adsl_id = 1
        }
        let itocdo_id = 0, imuccuoc_id = 0
        if (this.dichvuvt_id == Enum.DichVuVienThong.TSL || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM) {
          itocdo_id = parseInt(this.$refs.ThongTinThueBao.$refs.refTSL.cboTocDoKenhTSL.Value)
          this.trangbi_id = parseInt(this.$refs.ThongTinThueBao.$refs.refTSL.cboTrangBi.Value)
        }
        if (this.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || this.dichvuvt_id === Enum.DichVuVienThong.METRONET || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_DADIEM) {
          itocdo_id = parseInt(this.$refs.ThongTinThueBao.$refs.refWan.cboTocDo.Value)
          this.trangbi_id = parseInt(this.$refs.ThongTinThueBao.$refs.refWan.cboTrangBiWan.Value)
        }
        if (this.dichvuvt_id === Enum.DichVuVienThong.ADSL) {
          itocdo_id = parseInt(this.$refs.ThongTinThueBao.$refs.refAdsl.cboGoiCuocAdsl.Value)
          imuccuoc_id = parseInt(this.$refs.ThongTinThueBao.$refs.refAdsl.cboMucCuoc.Value)
          this.trangbi_id = parseInt(this.$refs.ThongTinThueBao.$refs.refAdsl.cboTrangBiADSL)
        }
        let ds = await this.LAY_TIEN_KHOANMUC_LD(Enum.LoaiHopDong.DI_CHUYEN, DateTimeLib.toDateDisplay(this.modelDateTime.dtpNgayYC), parseInt(this.modelCbo.cboDichVuVT),
          parseInt(this.modelCbo.cboLoaihinhTB), parseInt(this.modelCbo.cboKieuLD),
          this.phuongld_id, itocdo_id, imuccuoc_id, this.ltb_adsl_id, this.ltb_adsl_moi_id, this.ltb_adsl_cu_id, this.tocdo_moi_id, this.tocdo_cu_id, this.kh_cd, this.phold_id, this.apld_id, this.khuld_id, parseInt(this.modelCbo.cboDoituongTB), this.trangbi_id)
        if (ds !== null && ds.length > 0) {
          await this.HienThiTienLapDat(ds)
          await this.LayTongTien_HDTB()
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2817 ~ LayTienTheoKhoanMuc ~ error', error)
      }
    },
    async cboLoaihinhTB_SelectedValueChanged () {
      try {
        if (this.modelCbo.vcboLoaihinhTB.length > 0) {
          this.loaitb_id = parseInt(this.modelCbo.cboLoaihinhTB)
          await this.HT_Kieu_LD_BANCHEO_Combobox(this.loaitb_id, Enum.LoaiHopDong.DI_CHUYEN, 2)
          if (this.dichvuvt_id === Enum.DichVuVienThong.ADSL && this.ikieuld_id !== 0) { this.modelCbo.cboKieuLD = this.ikieuld_id }
          if (this.kt_load === false) {
            if (this.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || this.dichvuvt_id === Enum.DichVuVienThong.TSL || this.dichvuvt_id === Enum.DichVuVienThong.METRONET
              || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM || this.dichvuvt_id === Enum.DichVuVienThong.NOIBO_DADIEM) { await this.HienThi_CTK_CTC() }
          }
          await this.HT_COMBO_DONVI()
        }
      } catch (error) {

      }
    },
    // async cboKieuLD_SelectedValueChanged () {
    //   if (this.modelCbo.vcboKieuLD.length > 0) {
    //     if (this.dichvuvt_id === Enum.DichVuVienThong.CO_DINH) {
    //       let nhomkld = await this.MAP_ID('NHOMKIEU_LD', this.modelCbo.cboKieuLD)
    //       if (nhomkld.toString() === '1') { this.buttonState.btnThueSo = true } else { this.buttonState.btnThueSo = false }
    //     }
    //     this.kieu_thueso = 0
    //     this.noidung_thueso = ''
    //     this.sothang_thueso = 0
    //     if (this.dichvuvt_id !== Enum.DichVuVienThong.TSL) { await this.HT_COMBO_DONVI() }
    //     await this.LayTienTheoKhoanMuc()
    //     await this.HT_DS_KhuyenMai_Combobox()

    //     this.dataSourceDV = {
    //       idDichVu: this.modelCbo.cboDichVuVT,
    //       idLoaihinhTb: this.modelCbo.cboLoaihinhTB,
    //       idKieuLd: this.modelCbo.cboKieuLD,
    //       idDaiVt: this.modelCbo.cboDaiVT
    //     }
    //   }
    // },
    async HT_DS_KhuyenMai_Combobox () {
      let itocdo_id = 0
      let itrangbi_id = 0 // Default: KH tự trang bị
      let imuccuoc_id = 0
      let iloaitb_id = 0
      if (this.modelCbo.vcboKieuLD.length <= 0) { return }
      if (this.modelCbo.vcboDoituongTB.length <= 0) { return }
      if (this.modelCbo.cboDichVuVT !== null && !this.modelCbo.cboDichVuVT.includes('.')) {
        this.dichvuvt_id = parseInt(this.modelCbo.cboDichVuVT)
      }
      switch (this.dichvuvt_id) {
        case Enum.DichVuVienThong.CO_DINH:
          itocdo_id = 0
          break
        case Enum.DichVuVienThong.IMS:
          itocdo_id = 0
          break
        case Enum.DichVuVienThong.ADSL:
          if (
            this.$refs.refAdsl.cboGoiCuocAdsl.Value == null ||
            this.$refs.refAdsl.cboGoiCuocAdsl.Value.includes('.')
          ) {
            return
          }
          itocdo_id = this.$refs.refAdsl.cboGoiCuocAdsl.Value
          break
        case Enum.DichVuVienThong.MEGA_EYES:
          if (
            this.$refs.refAdsl.cboGoiCuocAdsl.Value == null ||
            this.$refs.refAdsl.cboGoiCuocAdsl.Value.includes('.')
          ) {
            return
          }
          itocdo_id = this.$refs.refAdsl.cboGoiCuocAdsl.Value
          break
        case Enum.DichVuVienThong.MEGAWAN:
        case Enum.DichVuVienThong.METRONET:
        case Enum.DichVuVienThong.NOIBO_DADIEM:
          if (
            this.$refs.refWan.cboTocDoCIR.Value == null ||
            this.$refs.refWan.cboTocDoCIR.Value.includes('.')
          ) {
            return
          }
          itocdo_id = this.$refs.refWan.cboTocDoCIR.Value
          break
        case Enum.DichVuVienThong.TSL:
        case Enum.DichVuVienThong.NOIBO_2DIEM:
          if (
            this.$refs.refTSL.cboTocDoKenhTSL.Value == null ||
            this.$refs.refTSL.cboTocDoKenhTSL.Value.includes('.')
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
      try {
        if (this.modelCbo.cboMucCuoc != null && !this.modelCbo.cboMucCuoc.includes('.')) {
          imuccuoc_id = this.modelCbo.cboMucCuoc
        }
        if (this.modelCbo.cboLoaihinhTB != null && !this.modelCbo.cboLoaihinhTB.includes('.')) {
          iloaitb_id = this.modelCbo.cboLoaihinhTB
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 3014 ~ HT_DS_KhuyenMai_Combobox ~ error', error)
      }

      this.dsKhuyenMai = await this.LAY_KHUYENMAI_HDTB(
        iloaitb_id,
        this.modelCbo.cboKieuLD,
        this.phuongld_id,
        Enum.KHOANMUC_TT.KMTT_DATMOI,
        this.phold_id,
        this.apld_id,
        this.khuld_id,
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
      }
      this.modelCkeckBox.chkKhuyenMai = false
    },
    async LAY_KHUYENMAI_HDTB (
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
        let res = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_khuyenmai_hdtb', data)
        return res.data
      } catch (error) {
        return []
      }
    },
    async MAP_ID (P_MAP, P_VALUE) {
      try {
        let data = {
          loai: P_MAP,
          value: P_VALUE
        }
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/getdata', data)
        console.log('🚀 ~ file: index.vue ~ line 3273 ~ MAP_ID ~ rs', rs)
        if (rs.data.length > 0) {
          var obj = rs.data[0]
          return obj[P_MAP.toLowerCase()]
        } else return -1
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 3278 ~ MAP_ID ~ error', error)
        return -1
      }
    },
    async MAP_ID_THEO_TINH (tinhthicong_id, in_id_neo, in_table, in_dk) {
      try {
        let data = {
          tinhthicong_id,
          in_id_neo,
          in_table,
          in_dk
        }
        let rs = await this.$root.context.post('/web-bancheo/tracuu/map_id', data)
        console.log('🚀 ~ file: index.vue ~ line 3291 ~ MAP_ID_THEO_TINH ~ rs', rs)
        if (rs) {
          return rs
        } else return -1
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 3295 ~ MAP_ID_THEO_TINH ~ error', error)
        return -1
      }
    },
    async HienThi_CTK_CTC () {
    },

    async HT_COMBO_DONVI () {},
    async LayTongTien_HDTB () {
      try {
        var dtongtien = 0
        var dtongvat = 0
        for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          if (this.ds_tien_khoanmuc[i].khoanmuctt_id === Enum.KHOANMUC_TT.KMTT_KM_LAPDAT) {
            dtongtien += Math.abs(parseFloat(this.ds_tien_khoanmuc[i].tien)) * -1
            dtongvat += Math.abs(parseFloat(this.ds_tien_khoanmuc[i].vat)) * -1
          } else {
            dtongtien += parseFloat(this.ds_tien_khoanmuc[i].tien)
            dtongvat += parseFloat(this.ds_tien_khoanmuc[i].vat)
          }
        }
        this.modelInput.txtTongTien = dtongtien
        this.modelInput.txtTongVat = dtongvat
        this.lblTongTien = (dtongtien + dtongvat)
      } catch (error) {
      }
    },
    async HienThiTienLapDat (ds) {
      var kt_lm = true
      if (ds[0].id !== '-1') {
        for (var i = 0; i < ds.length; i++) {
          if (ds[i].khoanmuctt_id === Enum.KHOANMUC_TT.KMTT_CHUYENDICH) {
            this.dtienld = parseFloat(ds[i].tien)
            this.dvatld = parseFloat(ds[i].vat)
            kt_lm = false
          }
        }
        if (kt_lm === true) {
          this.dtienld = 0
          this.dvatld = 0
        }
      } else {
        this.dtienld = 0
        this.dvatld = 0
      }
      for (let j = 0; j < this.ds_tien_khoanmuc.length; j++) {
        if (this.ds_tien_khoanmuc[j].khoanmuctt_id === Enum.KHOANMUC_TT.KMTT_CHUYENDICH) {
          this.ds_tien_khoanmuc[j].tien = this.dtienld
          this.ds_tien_khoanmuc[j].vat = this.dvatld
        }
      }
    },
    async LAY_TIEN_KHOANMUC_LD (loaihd_id, ngay_yc, dichvuvt_id, loaitb_id, kieuld_id, phuongld_id, tocdo_id, muccuoc_id, ltb_adsl_id, ltb_adsl_moi_id, ltb_adsl_cu_id, tocdo_moi_id, tocdo_cu_id, kh_cd, phold_id, apld_id, khuld_id, doituong_id, trangbi_id) {
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
        let response = await this.$root.context.post(`/web-hopdong/chuyendich/lay_tien_khoanmuc_ld_v2`, data)
        return response.data
      } catch (error) {
        return []
      }
    },
    async Clear () {
      console.log('🚀 ~ file: index.vue:3894 ~ Clear ~ Clear:')
      try {
        this.Lat_TB = 0; this.Lng_TB = 0; this.Lat_LD = 0; this.Lng_LD = 0
        this.Lat_TT = 0; this.Lng_TT = 0; this.Lat_BC = 0; this.Lng_BC = 0
        this.Lat_KH = 0; this.Lng_KH = 0
        this.Lat_DD = 0; this.Lng_DD = 0; this.Lat_DC = 0; this.Lng_DC = 0

        this.kt_kenhphu_ao = false
        this.chi_giao_net = true
        this.modelInput.txtCongVan = ''
        this.modelCkeckBox.chkCongVan = false

        this.modelInput.txtMaERP = ''
        this.modelInput.txtMaBSS = ''

        this.vhuong_kn = ''
        this.kieu_thueso = 0
        this.noidung_thueso = ''
        this.sothang_thueso = 0

        this.trangbi_id = 3// Default khách hàng tự trang bị
        this.modelInput.txtTongTienHD = '0'
        this.hdkh_id = 0
        this.modelInput.txtMaGD = ''
        this.modelInput.txtMaHDcu = ''
        this.modelInput.txtMaKH = ''
        this.khachhang_id = 0
        this.modelInput.txtTenKH = ''
        this.modelInput.txtDiaChiKH = ''
        this.tinhkh_id = 0
        this.quankh_id = 0
        this.phuongkh_id = 0
        this.phokh_id = 0
        this.apkh_id = 0
        this.khukh_id = 0
        this.dacdiemkh_id = 0
        this.sonhakh = ''
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
        this.modelInput.lvwThanhToan = []
        this.modelInput.lvwThueBao = []
        this.modelInput.txtMaTuyen = ''
        this.hdtt_id = 0
        this.modelInput.txtMaTT = ''
        this.thanhtoan_id = 0
        this.modelInput.txtTenTT = ''
        this.modelInput.txtDiaChiTT = ''
        // diachi bc
        this.modelInput.txtDiaChiBC = ''
        this.tinhbc_id = 0
        this.quanbc_id = 0
        this.phuongbc_id = 0
        this.phobc_id = 0
        this.apbc_id = 0
        this.khubc_id = 0
        this.dacdiembc_id = 0
        this.sonhabc = ''
        //
        this.tinhtt_id = 0
        this.quantt_id = 0
        this.phuongtt_id = 0
        this.phott_id = 0
        this.aptt_id = 0
        this.khutt_id = 0
        this.dacdiemtt_id = 0
        this.sonhatt = ''
        this.modelInput.txtMST = ''
        this.modelInput.txtSoTKKH = ''
        this.modelCbo.cboHTTT = 0
        this.modelCbo.cboDonViQL = 0
        this.modelCbo.cboNganHangTT = 0
        this.modelCbo.vcboMaDD = []
        this.modelCbo.cboMaDD = 0
        this.hdtb_id = 0
        this.thuebao_id = 0
        this.modelInput.txtMaTB = ''
        this.modelInput.txtTenTB = ''
        this.modelInput.txtDiaChiTB = ''
        this.tinhtb_id = 0
        this.quantb_id = 0
        this.phuongtb_id = 0
        this.photb_id = 0
        this.aptb_id = 0
        this.khutb_id = 0
        this.dacdiemtb_id = 0
        this.sonhatb = ''
        this.modelInput.txtDiaChiLD = ''
        this.tinhld_id = 0
        this.quanld_id = 0
        this.phuongld_id = 0
        this.phold_id = 0
        this.apld_id = 0
        this.khuld_id = 0
        this.dacdiemld_id = 0
        this.sonhald = ''

        this.tinhdau_id = 0
        this.quandau_id = 0
        this.phuongdau_id = 0
        this.phodau_id = 0
        this.apdau_id = 0
        this.sonhadau = ''

        this.tinhcuoi_id = 0
        this.quancuoi_id = 0
        this.phuongcuoi_id = 0
        this.phocuoi_id = 0
        this.apcuoi_id = 0
        this.sonhacuoi = ''

        this.modelCbo.cboDoituongTB = 0

        this.modelCkeckBox.cboDichVuVT = true

        this.modelInput.txtDiaChiCT = ''
        this.modelInput.txtChucDanhDD = ''
        this.modelInput.txtNgayHetHan = ''
        this.modelInput.txtMaSTKH = ''
        this.modelInput.txtSoTKKH = ''
        this.modelInput.txtGhiChuKH = ''

        this.modelInput.txtGiayToKem = ''
        this.modelCkeckBox.chkNganHangKH = false

        try {
          this.modelInput.txtNguoiGT = ''
          this.modelInput.txtCTV = ''
          this.nguoigt_id = 0
          this.ctv_id = 0
        } catch (Exception) {
        }

        this.modelInput.txtMaNVTC = ''
        console.log('🚀 ~ file: index.vue:4043 ~ Clear ~ this.modelInput.txtMaNVTC:', this.modelInput.txtMaNVTC)
        this.modelInput.txtNhanVienQL = ''
        this.nhanvien_ql_am_id = 0

        this.modelInput.txtGhiChuTT = ''
        this.modelInput.txtNgaySinhTB = ''

        this.modelInput.txtNgayCapHK = ''
        this.modelInput.txtNoiCapHK = ''
        this.donvi_dau_id = 0
        this.donvi_cuoi_id = 0
        if (this.modelCbo.cboDichVuVT === Enum.DichVuVienThong.ADSL) {
          this.ltb_adsl_id = parseInt(this.modelCbo.cboDichVuVT)
        } else {
          if (this.modelCbo.cboDichVuVT === Enum.DichVuVienThong.MEGAWAN ||
              this.modelCbo.cboDichVuVT === Enum.DichVuVienThong.METRONET ||
              this.modelCbo.cboDichVuVT === Enum.DichVuVienThong.NOIBO_DADIEM) { this.ltb_adsl_id = parseInt(this.$refs.ThongTinThueBao.$refs.refWan.cboLoaiTBiWan.Value) } else { this.ltb_adsl_id = 1 }
        }

        this.doituong_id = 1
        await this.$refs.ThongTinThueBao.LayTienTheoKhoanMuc()
        this.khach_hang_id = 0
        this.kt_adsl = true
        this.ikieuld_id = 0
        this.modelInput.txtDiaChiNDD = ''
        this.kt_adsl = true
        this.modelInput.txtMaTBMoi = ''
        this.ma_tbmoi = ''
        if (this.kieukh_canhan) { this.buttonState.rdoGTNam = true }
        this.DV_QHNS = ''
        this.MACHUONG = ''
        this.MN_KT = ''
        this.MA_NDKT = ''
        this.MN_NSNN = ''
        this.TIEN_DM = 0
        await this.$refs.ThongTinThueBao.LAY_DS_SUDUNG_DVGT(0)

        this.TNC1_ID = 0
        this.TNC2_ID = 0
        this.TNC3_ID = 0
        this.TNC4_ID = 0
        this.nganhnghe_id = 0

        this.cuoc_tc = 0
        this.cuoc_tk = 0

        this.hdmig_id = 0
        this.mig_id = 0
        this.modelInput.txtMaIG = ''

        this.modelInput.txtHoKhau = ''
        this.modelInput.txtSTKTT = ''
        this.modelInput.txtChu_TK = ''
        this.hdtb_id_cha = 0
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 3380 ~ Clear ~ error', error)
      }
    },
    async frmChuyenDichBC_Load () {
      this.$nextTick(async () => {
        this.modelDateTime.dtpNgayLapHD = new Date()
        this.modelDateTime.dtpNgayYC = new Date()
        await this.SetButton(-1)
        this.ds_tien_khoanmuc = await this.Lay_tienkhoanmuc_loaihd(Enum.LoaiHopDong.DI_CHUYEN)

        await this.HT_DoiTuong_Combobox()
        await this.HT_DichVuVT_Combobox_DC()
        await this.HT_LoaiGT_Combobox()
        await this.HT_LOAI_KHLON_Combobox()
        await this.HT_NganHang_Combobox_LD('cboNganHangTT')
        await this.HT_HinhThucTT_Combobox()

        this.dtLoaiKH = await this.Lay_ds_loaikh(0)
        if (this.dtLoaiKH !== null && this.dtLoaiKH.length > 0) {
          this.modelCbo.vcboLoaiKH = this.dtLoaiKH
          this.modelCbo.cboLoaiKH = this.modelCbo.vcboLoaiKH[0].loaikh_id
        }
        await this.HT_DonVi_Combobox('cboDonViQL', Enum.LOAI_DV.DONVIQL_TT)
        await this.HT_DonVi_Combobox('cboDaiVT', Enum.LOAI_DV.DONVIQL_LD)
        await this.HT_NganHang_Combobox_LD('cboNganHangKH')
        let donvi_dl_id = this.$root.context.user.getProperty('donvi_dl_id')
        if (donvi_dl_id !== 0) {
          let c = this.modelCbo.vcboDonViQL.find(x => x.donvi_id.toString() === donvi_dl_id.toString())
          if (c !== undefined) { this.modelCbo.cboDonViQL = donvi_dl_id } else {
            this.modelCbo.cboDonViQL = this.modelCbo.vcboDonViQL[0].donvi_id
          }
          let d = this.modelCbo.vcboDaiVT.find(x => x.donvi_id.toString() === donvi_dl_id.toString())
          if (d !== undefined) { this.modelCbo.cboDaiVT = donvi_dl_id } else {
            this.modelCbo.cboDaiVT = this.modelCbo.vcboDaiVT[0].donvi_id
          }
        }
        await this.$refs.ThongTinThueBao.LAY_DS_SUDUNG_DVGT(0)
        await this.HT_DanToc_Combobox()
        await this.HT_QuocTich_Combobox()
        await this.LAY_DS_NGANHNGHE()
        await this.HT_KieuHD_Combobox()
        await this.HT_Goi_KyThuat_Combobox()
        this.kt_load = false
        await this.LAY_DS_THAMSO_MD()

        var dsTS = await this.LAY_DS_THAMSO_MD_MATS('HIENTHI_KHL_DATMOI')
        var ht_khl = '-1'
        if (dsTS && dsTS.length > 0) {
          ht_khl = dsTS[0].ten_ts.toString()
        }
        if (ht_khl === '1') {
          this.buttonVisible.lblKHL = true
          this.buttonVisible.cboKhachHangLon = true
          this.buttonVisible.lblPLKH = false
          this.buttonVisible.txtPhanLoaiKH = false
        } else {
          this.buttonVisible.lblKHL = false
          this.buttonVisible.cboKhachHangLon = false
          this.buttonVisible.lblPLKH = true
          this.buttonVisible.txtPhanLoaiKH = true
        }
        if (Object.keys(this.$route.query).length > 0) {
          let query = this.$route.query
          try {
            if (query.tag) {
              this.iKenhTN = Number(query.tag)
            }
          } catch (error) {
            this.iKenhTN = 1
          }
        }
      })
    },
    async LAY_DS_THAMSO_MD_MATS (ma_ts) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/lay_ds_thamso_md_mats', {ma_ts})
        console.log('🚀 ~ file: index.vue ~ line 951 ~ LAY_DS_THAMSO_MD_MATS ~ rs', rs)
        if (rs.data.length > 0) {
          return rs.data
        }
        return []
      } catch (error) {
        return []
      }
    },
    async LAY_DS_THAMSO_MD () {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/lay_ds_thamso_md')
        console.log('🚀 ~ file: index.vue ~ line 949 ~ LAY_DS_THAMSO_MD ~ rs', rs)
        if (rs.data.length > 0) {
          let str_vtemp
          let det = []
          str_vtemp = 'NHANVIEN_DIABAN_DC'
          det = rs.data.filter(x => x.ma_ts === str_vtemp)
          if (det.length > 0) {
            if (det[0].ten_ts === '1') {
              this.nhap_nvkd = 1
            }
          }

          str_vtemp = 'KIEMTRA_MST'
          det = rs.data.filter(x => x.ma_ts === str_vtemp)
          if (det.length > 0) {
            if (det[0].ten_ts === '1') {
              this.ts_kt_mst = 1
            }
          }

          str_vtemp = 'HT_LOC_CBO_TRAM'
          det = rs.data.filter(x => x.ma_ts === str_vtemp)
          if (det.length > 0) {
            if (det[0].ten_ts === '1') {
              this.ht_loc_cbo_tram = true
            }
          }

          str_vtemp = 'KHONGBATBUOC_CTV'
          det = rs.data.filter(x => x.ma_ts === str_vtemp)
          if (det.length > 0) {
            if (det[0].ten_ts === '1') {
              this.khong_batbuoc_ctv = 1
            }
          }

          str_vtemp = 'BATBUOC_GIOITHIEU_DICHCHUYEN'
          det = rs.data.filter(x => x.ma_ts === str_vtemp)
          if (det.length > 0) {
            if (det[0].ten_ts === '1') {
              this.batbuoc_gioithieu_dichchuyen = 1
            }
          }

          str_vtemp = 'TUDONG_GANTHEM_HDTB_DV'
          det = rs.data.filter(x => x.ma_ts === str_vtemp)
          if (det.length > 0) {
            if (det[0].ten_ts === '1') {
              this.tudong_ganthem_hdtb_dv = true
            }
          }

          str_vtemp = 'DONVIQL_HTTT'
          det = rs.data.filter(x => x.ma_ts === str_vtemp)
          if (det.length > 0) {
            if (det[0].ten_ts === '1') {
              this.DONVIQL_HTTT = 1
            }
          }

          str_vtemp = 'TUYENTHU_HTTT'
          det = rs.data.filter(x => x.ma_ts === str_vtemp)
          if (det.length > 0) {
            if (det[0].ten_ts === '1') {
              this.TUYENTHU_HTTT = 1
            }
          }

          str_vtemp = 'KHONG_HIENTHI_TRACUU_DB_DICHCHUYEN'
          det = rs.data.filter(x => x.ma_ts === str_vtemp)
          if (det.length > 0) {
            if (det[0].ten_ts === '1') {
              this.khong_ht_tracuudb = 1
            }
          }

          str_vtemp = 'SINH_MA_GD_THEO_DV'
          det = rs.data.filter(x => x.ma_ts === str_vtemp)
          if (det.length > 0) {
            this.ts_sinhma_gd_theo_donvi = true
          } else {
            this.ts_sinhma_gd_theo_donvi = false
          }

          return true
        }
      } catch (error) {
        return false
      }
    },
    async LAY_DS_NGANHNGHE () {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/lay_ds_nganhnghe')
        console.log('🚀 ~ file: index.vue ~ line 948 ~ LAY_DS_NGANHNGHE ~ rs', rs)
        if (rs.data.length > 0) {
          this.modelCbo.vcboNganhNgheLoc = rs.data
          this.modelCbo.cboNganhNgheLoc = this.modelCbo.vcboNganhNgheLoc[0].nganhnghe_id
          return true
        }
      } catch (error) {
        return false
      }
    },
    ChuanHoaTen: function (ten) {
      try {
        if (ten != null) {
          let convertToArray = ten.toLowerCase().split(' ')
          let result = convertToArray.map(function (val) {
            return val.replace(val.charAt(0), val.charAt(0).toUpperCase())
          })
          return result.join(' ')
        } else {
          return ''
        }
      } catch (error) {
        return ten
      }
    },
    async HienThiTienDichVu () {
      this.dtien_dv = this.tien_dv
      this.dvat_dv = this.vat_dv
      for (let index = 0; index < this.ds_tien_khoanmuc.length; index++) {
        const element = this.ds_tien_khoanmuc[index]
        if (element.khoanmuctt_id === Enum.KHOANMUC_TT.KMTT_DICHVU) {
          element.tien = this.dtien_dv
          element.vat = this.dvat_dv
        }
      }
      await this.LayTongTien_HDTB()
    },
    async LAY_DS_THUEBAO_DICHVU (hdtbId, thueBaoId, loaiTbId, kieuId) {
      try {
        let data = {
          hdtbId: hdtbId,
          thueBaoId: thueBaoId,
          loaiTbId: loaiTbId,
          kieuId: kieuId
        }
        let res = await this.$root.context.post('web-hopdong/hopdong/lay_ds_thuebao_dichvu', data)
        console.log('🚀 ~ file: index.vue ~ line 2958 ~ LAY_DS_THUEBAO_DICHVU ~ res', res)
        return res.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 3898 ~ LAY_DS_THUEBAO_DICHVU ~ error', error)
        return []
      }
    },
    async HT_DonVi_Combobox (loai, loaidv_id) {
      try {
        let data = {
          loaidv_id
        }
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/ht_donvi_combobox', data)
        console.log('🚀 ~ file: index.vue ~ line 1030 ~ HT_DonVi_Combobox ~ rs', rs)
        if (rs.data !== null && rs.data.length > 0) {
          if (loai === 'cboDonViQL') {
            this.modelCbo.vcboDonViQL = rs.data
            this.modelCbo.cboDonViQL = this.modelCbo.vcboDonViQL[0].donvi_id
            return true
          }
          if (loai === 'cboDaiVT') {
            this.modelCbo.vcboDaiVT = rs.data
            this.modelCbo.cboDaiVT = this.modelCbo.vcboDaiVT[0].donvi_id
            return true
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 3922 ~ HT_DonVi_Combobox ~ error', error)
        return false
      }
    },
    async HT_DoiTuong_Combobox () {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/ht_doituong_combobox')
        console.log('🚀 ~ file: index.vue ~ line 1050 ~ HT_DoiTuong_Combobox ~ rs', rs)
        if (rs.data !== null && rs.data.length > 0) {
          this.modelCbo.vcboDoituongTB = rs.data
          this.modelCbo.cboDoituongTB = this.modelCbo.vcboDoituongTB[0].doituong_id
          return true
        } else {
          this.modelCbo.vcboDoituongTB = []
          this.modelCbo.cboDoituongTB = -1
          return false
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 3936 ~ HT_DoiTuong_Combobox ~ error', error)
        return false
      }
    },
    async HT_MucCuocTB_Combobox (mucuoctb_id) {
      try {
        let rs = await this.$root.context.get('/web-hopdong/hoanthienhschuyenquyen/ht-muccuoctb-combobox', {mucuoctb_id})
        console.log('🚀 ~ file: index.vue ~ line 1518 ~ HT_MucCuocTB_Combobox ~ rs', rs)
        if (rs.data !== null && rs.data.length > 0) {
          this.modelCbo.vcboMucCuocTB = rs.data
          this.modelCbo.cboMucCuocTB = this.modelCbo.vcboMucCuocTB[0].mucuoctb_id
        } else {
          this.modelCbo.vcboMucCuocTB = []
          this.modelCbo.cboMucCuocTB = -1
        }
      } catch (error) {
        this.modelCbo.vcboMucCuocTB = []
        this.modelCbo.cboMucCuocTB = -1
      }
    },
    async HT_DanToc_Combobox () {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/ht_dantoc_combobox')
        if (rs.data !== null && rs.data.length > 0) {
          this.modelCbo.vcboDanToc = rs.data
          this.modelCbo.cboDanToc = this.modelCbo.vcboDanToc[0].dantoc_id
          return true
        }
      } catch (error) {
        return false
      }
    },
    async HT_QuocTich_Combobox () {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/ht_quoctich_combobox')
        if (rs.data !== null && rs.data.length > 0) {
          this.modelCbo.vcboQuocTich = rs.data
          this.modelCbo.cboQuocTich = this.modelCbo.vcboQuocTich[0].quoctich_id
          return true
        }
      } catch (error) {
        return false
      }
    },
    async HT_DichVuVT_Combobox_DC () {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/ht_dichvuvt_combobox_dc')
        console.log('🚀 ~ file: index.vue:4373 ~ HT_DichVuVT_Combobox_DC ~ rs:', rs)
        if (rs.data !== null && rs.data.length > 0) {
          this.modelCbo.vcboDichVuVT = rs.data
          this.modelCbo.cboDichVuVT = this.modelCbo.vcboDichVuVT[0].dichvuvt_id
          await this.ThongTinThueBao.cboDichVuVT_SelectedValueChanged()
          return true
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:4380 ~ HT_DichVuVT_Combobox_DC ~ error:', error)
        return false
      }
    },
    async HT_KieuHD_Combobox () {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/ht_kieuhd_combobox')
        if (rs.data !== null && rs.data.length > 0) {
          this.modelCbo.vcboKieu_HD = rs.data
          this.modelCbo.cboKieu_HD = this.modelCbo.vcboKieu_HD[0].kieuhd_id
          return true
        }
      } catch (error) {
        return false
      }
    },
    async HT_Goi_KyThuat_Combobox () {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/ht_goi_kythuat_combobox')
        console.log('🚀 ~ file: index.vue ~ line 1522 ~ HT_Goi_KyThuat_Combobox ~ rs', rs)
        if (rs.data !== null && rs.data.length > 0) {
          this.modelCbo.vcboGoiKT = rs.data
          this.modelCbo.cboGoiKT = this.modelCbo.vcboGoiKT[0].goikt_id
          return true
        }
      } catch (error) {
        return false
      }
    },
    async HT_LoaiGT_Combobox () {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/ht_loaigt_combobox')
        if (rs.data !== null && rs.data.length > 0) {
          this.modelCbo.vcboGiayToKH = rs.data
          this.modelCbo.cboGiayToKH = this.modelCbo.vcboGiayToKH[0].loaigt_id
          return true
        }
      } catch (error) {
        return false
      }
    },
    async HT_LOAI_KHLON_Combobox () {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/ht_loai_khlon_combobox')
        if (rs.data !== null && rs.data.length > 0) {
          this.modelCbo.vcboKhachHangLon = rs.data
          this.modelCbo.cboKhachHangLon = this.modelCbo.vcboKhachHangLon[0].khlon_id
          return true
        }
      } catch (error) {
        return false
      }
    },
    async HT_NganHang_Combobox_LD (loai) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/lay_ds_nganhang_ld')
        if (rs.data !== null && rs.data.length > 0) {
          if (loai === 'cboNganHangTT') {
            this.modelCbo.vcboNganHangTT = rs.data
            this.modelCbo.cboNganHangTT = this.modelCbo.vcboNganHangTT[0].nganhang_id
          }
          if (loai === 'cboNganHangKH') {
            this.modelCbo.vcboNganHangKH = rs.data
            this.modelCbo.cboNganHangKH = this.modelCbo.vcboNganHangKH[0].nganhang_id
          }
          return true
        }
      } catch (error) {
        return false
      }
    },
    async HT_HinhThucTT_Combobox () {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/ht_hinhthuctt_combobox')
        if (rs.data !== null && rs.data.length > 0) {
          this.modelCbo.vcboHTTT = rs.data
          this.modelCbo.cboHTTT = this.modelCbo.vcboHTTT[0].httt_id
          return true
        }
      } catch (error) {
        return false
      }
    },
    async HT_LOAI_TB_ADSL_Combobox () {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/ht_loai_tb_adsl_combobox')
        return rs.data
      } catch (error) {
        return []
      }
    },
    async Lay_ds_loaikh (vnhomlkh_id) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/lay_ds_loaikh', { vnhomlkh_id })
        console.log('🚀 ~ file: index.vue ~ line 1582 ~ Lay_ds_loaikh ~ rs', rs)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async Lay_tienkhoanmuc_loaihd (vloaihd_id) {
      try {
        let rs = await this.$root.context.get('/web-hopdong/chuyendich/lay_tienkhoanmuc_loaihd', { vloaihd_id })
        console.log('🚀 ~ file: index.vue ~ line 3153 ~ Lay_tienkhoanmuc_loaihd ~ rs', rs)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async SetButton (kieu) {
      try {
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
        if (kieu === -1) {
          this.$refs.ThongTinThueBao.$refs.txtMaTB.focus()
          this.buttonState.tsbtnGhiLai = true
          this.buttonState.tsbtnHuyBo = true
          this.thongso_ban = this.Reset_Thongso_Thuebao()
          this.thongso_tc = this.Reset_Thongso_Thuebao()
        }

        if (kieu === 0) {
          this.$refs.ThongTinThueBao.$refs.txtMaTB.focus()
          this.buttonState.tsbtnNhapMoi = true
          this.thongso_ban = this.Reset_Thongso_Thuebao()
          this.thongso_tc = this.Reset_Thongso_Thuebao()
          await this.Clear()
        }

        if (kieu === 1) {
          this.$refs.ThongTinThueBao.$refs.txtMaTB.focus()
          this.buttonState.tsbtnGhiLai = true
          this.buttonState.tsbtnHuyBo = true
          this.thongso_ban = this.Reset_Thongso_Thuebao()
          this.thongso_tc = this.Reset_Thongso_Thuebao()
          await this.Clear()
        }

        if (kieu === 2) {
          this.$refs.ThongTinThueBao.$refs.txtMaGD.focus()
          this.buttonState.tsbtnNhapMoi = true
          this.buttonState.tsbtnXoa = true
          await this.Clear()
        }

        if (kieu === 3) {
          this.$refs.ThongTinThueBao.$refs.txtMaGD.focus()
          this.buttonState.tsbtnNhapMoi = true
          this.buttonState.tsbtnXoa = true
          this.buttonState.tsbtnGhiLai = true
          this.buttonState.tsbtnHuyBo = true
          this.buttonState.tsbtnThemTB = true
          this.buttonState.tsbtnThemTB_DS = true
          this.buttonState.tsbtnThemTT = true
          this.buttonState.tsbtnInhopdong = true
          this.buttonState.tsbtnThanhToan = true
          if (this.modelInput.lvwThanhToan.length > 1) { this.buttonState.tsbtnXoaTT = true }
          if (this.modelInput.lvwThueBao.length > 1) { this.buttonState.tsbtnXoaTB = true }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1679 ~ SetButton ~ error', error)
      }
    }
  },
  watch: {
    'modelInput.txtMaNVTC' (val) {
      console.log('🚀 ~ file: index.vue:4597 ~ val:', val)
    }
  },
  computed: {
    ThongTinThueBao () {
      return this.$refs.ThongTinThueBao
    },
    ThongTinKhachHang () {
      return this.$refs.ThongTinKhachHang
    }
  }
}
</script>
<style>
a.disable-btn-chuyendichbc {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
.modal-ThanhToan-ChuyenDichBC .main-wrapper {
  position: unset !important;
}
.modal-ThanhToan-ChuyenDichBC .page-content {
  position: unset !important;
}
@media (min-width: 1200px){
        .frm-thanhtoan-bc.modal-xl {
            max-width: 95% !important;
        }
    }
</style>
