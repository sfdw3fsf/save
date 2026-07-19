<template src='./ChangeMegaWanConfigCrossSell.html'></template>
<style scoped  src='./ChangeMegaWanConfigCrossSell.scss'></style>
<script src='@/static/vendor/jquery/split.js'></script>
<script>
//import gridView from '@/modules/prepaid/ChoosePreapaidNumber/components/gridview.vue'
//import treegridView from '@/modules/contract/setup/ManageSelfcareContract/components/treegridview.vue'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
//import thongTinEmail from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue'
//import thongTinLienHe from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue'
import thongTinHenKhachHangModal from '@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
//import thanhToan from '@/modules/contract/setup/Payment/Payment.vue'

var thanhToan = () => import('@/modules/contract/setup/Payment/Payment.vue')
var defaultModule = () => import('@/modules/contract/setup/Payment/Default.vue')
var lienHe = () => import('@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue')
var thongTinEmailCom = () => import('@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue')

import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import api from './Api'
import { LoaiHopDong, TrangThaiHD, DichVuVienThong, LOAI_DV, LoaiHinhTB, TrangThaiTB, TRANGTHAI_DONGBO } from '@/const/enums/index.js'
import { KHOANMUC_TT, Loai_KM } from '../InstallNewSubs/Enums'
import frmChonTinh from './popups/frmChonTinh.vue'
import frmThongSoWan from './popups/frmThongSoWan.vue'
import frmDsThueBaoWan from './popups/frmDsThueBaoWan.vue'
import API from '../InstallNewSubs/InstallNewSubsAPI'

export default {
  components: {
    breadcrumb,
    //gridView,
    //treegridView,
    moment,
    DatePicker,
    api,
    SearchContractModal,
    // thongTinEmail,
    // thongTinLienHe,
    thongTinHenKhachHangModal,
    // thanhToan,
    frmChonTinh,
    frmThongSoWan,
    frmDsThueBaoWan,
  },
  name: 'ChangeMegaWanConfigCrossSell',
  props: {
    ispopup: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      header: {
        title: 'Thay đổi thông số MegaWan',
        list: [
          { name: 'Hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: ' Thay đổi thông số MegaWan',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      Loading: false,
      dateFormat: 'DD/MM/YYYY',
      typeFormat: 'date',
      DsThueBao: {
        headers: [
          { fieldName: 'ma_tb', headerText: 'Account', value: '' },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', value: '' },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', value: '' }
        ],
        data: []
      },
      thamsomacdinh: {},
      dsHDKH: [],
      dsHDTB: [],
      dsHDTBTest: [],
      dsHDTB_MEWAN: [],
      dsKM_HDTB: [],
      dsTTTT: [],
      dsCTTHD: [],
      dtCTKM: [],
      dsHDBCADSL: [],
      dsHDBCCD: [],
      dsHDBC: [],
      dsHDTBDV: [],
      dsHDBCMGWAN: [],
      dsHDTBBC: [],
      dsDKDVGTBC: [],
      dsHDBCTSL: [],
      dsHDBCIMS: [],
      dsDANGKYDVGT: [],
      vhuongkn: '',
      thuebao_id_cha: 0,
      huonggiao_tn: '',
      hdkh_cha_id: 0,
      doituong_id: 1, //Defaul: Tư nhân
      hdkh_id: 0,
      quytrinh_id: 0,
      hdtb_id: 0,
      khachhang_id: 0,
      thuebao_id: 0,
      port_id: 0,
      loaitb_id: 0,
      diachi_tb: '',
      diachi_ld: '', //Hiếu thêm 20.09.2010
      donvi_id: 0,
      tocdo_id: 0,
      ltb_adsl_id: 1, //Default
      kh_cd: 0,
      ltb_adsl_cu_id: 0,
      ltb_adsl_moi_id: 0,
      phuongld_id: 0,
      phold_id: 0,
      apld_id: 0,
      khuld_id: 0,
      found: false,
      //Hiếu thêm 18.09.2010
      tocdo_moi_id: 0,
      tocdo_cu_id: 0,
      targetTop: '#app .main-wrapper',
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      //dtienld: 0,
      //dvatld: 0,
      ds_tien_khoanmuc: [],
      dtienkmld: 0,
      dvatkmld: 0,
      tong_kmld: 0,
      tyle_kmld: 0,
      vsoao: '',
      ctv_id: 0,
      donvi_ctv_id: 0,
      loainv_ctv_id: 0,
      nguoigt_id: 0,
      donvi_nguoi_gt_id: 0,
      loainv_nguoi_gt_id: 0,
      ts_sinhma_gd_theo_donvi: false,
      loi_sinh_magd: false,
      //kt_load: false,
      trangbi_id: 0,
      //LoaiHopDong: LoaiHopDong,
      //TrangThaiHD: TrangThaiHD,
      chkCongVan: '',
      dichvuvt_id: 0,
      tthd_id: 1,

      txtMaDuAn: '',
      lblTongS: 'Tổng số:',
      chkCTV: false,
      txtCTV: '',
      txtCTVId: '',
      chkNguoiGT: false,
      txtNguoiGTId: '',
      txtNguoiGT: '',
      txtTongTienHD: 0,
      txtMaGD: '',
      txtMaGDTT: '',
      dtpNgayYeuCau: '',
      txtMaHD: '',
      dtpNgayLapHD: '',
      txtGhiChu: '',
      cboDichVuVT: '',
      DichVuVT: [],
      cboLoaihinhTB: '',
      LoaihinhTB: [],
      cboKieuLD: '',
      KieuLD: [],
      txtMaHD_TTKD: '',
      txtCongVan: '',
      txtVrf_moi:'',
      dtpNgayCV: '',
      txtMaTB: '',
      txtMaERP: '',
      txtSoAo: '',
      txtTenTB: '',
      txtMaBSS: '',
      txtSiteID: '',
      txtThoiHanCK: '',
      txtThoiHanCK_Up: '',
      txtThoiHanCKEnabled: true,
      txtTenKH: '',
      txtMaKH: '',
      txtDiaChiLD: '',
      txtDiaChiTB: '',
      textBox2: '',
      txtHuongKn: '',
      cboTinh_KN_Moi: '',
      Tinh_KN_Moi: [],
      cboChuQuanMEGAWAN: '',
      ChuQuanMEGAWAN: [],
      cboTrangBiMEGAWAN: '',
      TrangBiMEGAWAN: [],
      txtCuocTK: 0,
      cboPhanLoaiMGW: '',
      PhanLoaiMGW: [],
      paymentloaded: false,
      cboPhanTachTD: '',
      chkTocDo_PIR: false,
      cboTocDo_Pir: '',
      cboTocDo: '',
      txtDiaChi_KN: '',
      TocDo: [],
      TocDo_Pir: [],
      txtCuocTC: 0,
      cboLoaiKenh: '',
      LoaiKenh: [],
      chkTocDo_NIX: false,
      cboTocDo_NIX: '',
      TocDo_NIX: [],
      txtCuoc_NIX: 0,
      cboLoaiModem: '',
      LoaiModem: [],
      chkTocDo_ISP: false,
      cboTocDo_ISP: '',
      TocDo_ISP: [],
      txtCuoc_ISP: 0,
      cboLoaiTBiWan: '',
      LoaiTBiWan: [],
      cboThoiHanMEGAWAN: '',
      ThoiHanMEGAWAN: [],
      txtCuocHT: 0,
      cboVeTinh: '',
      VeTinh: [],
      txtCuocTBI: 0,
      chkDoiTac: false,
      cboDoiTac: '',
      DoiTac: [],
      txtCuocTKDT: 0,
      txtSoLuong_IP: 0,
      txtMaHD_DT: '',
      txtCuocTCDT: 0,
      txtCuoc_IP: 0,
      txtLanIP: '',
      txtWanIP: '',
      cboCongNghe_Moi: '',
      CongNghe: [],
      chkKhuyenMai: false,
      cboKhuyenMai: '',
      KhuyenMai: [],
      KhuyenMaiRaw: [],
      //
      txtHuongKnCu: '',
      cboTinh_KN_Cu: '',
      cboChuQuanMEGAWANCu: '',
      cboPhanLoaiMGWCu: '',
      cboPhanTachTDCu: '',
      cboTocDoCu: '',
      chkTocDo_PIR_Cu: false,
      txtCuocTC_Cu: 0,
      cboLoaiKenhCu: '',
      chkTocDo_NIX_Cu: false,
      cboTocDo_NIX_Cu: '',
      txtCuoc_NIX_Cu: 0,
      cboLoaiModemCu: '',
      chkTocDo_ISP_Cu: false,
      cboTocDo_ISP_Cu: '',
      txtCuoc_ISP_Cu: 0,
      cboLoaiTBiWanCu: '',
      cboTrangBiMEGAWANCu: '',
      cboThoiHanMEGAWANCu: '',
      txtCuocHT_Cu: 0,
      cboVeTinhCu: '',
      txtCuocTBI_Cu: 0,
      chkDoiTac_Cu: false,
      cboDoiTac_Cu: '',
      txtCuocTKDT_Cu: 0,
      txtSoLuong_IP_Cu: 0,
      txtMaHD_DT_Cu: '',
      txtCuocTCDT_Cu: 0,
      txtCuoc_IP_Cu: 0,
      cboCongNghe_Cu: '',
      txtDiaChi_KN_Cu: '',
      txtWanIP_Cu: '',
      txtTongTien: 0,
      txtTongVat: 0,
      MaHD_DT_Cu: [],
      txtLanIP_Cu: '',
      PhanTachTD: [],
      TocDo_PIR: [],
      txtCuocTK_Cu: 0,
      cboTocDo_PIR_Cu: '',
      textBox1: '',
      label11: 'Tốc độ CIR',
      label38: 'Tốc độ CIR',
      Tinh_KN: [],
      CT_TIENHD_ID: 0,
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close': this.popupClosed,
        acceptChangeCTV: this.popupClosed,
        acceptChangeNGT: this.popupClosed,
        PopUpCongTacVien_Thoat: this.popupClosed,
        PopUpNguoiGioiThieu_Thoat: this.popupClosed,
        paymentcreated: this.paymentcreated
      },
      popupComponentName: '',
      tsbtnXoaTB_Enabled: true,
      KieuLapDat: {
        HNI_DOI_CONGNGHE_GPON: 921,
        HNI_DOI_WANIP_LAN_IP: 903,
        HNI_DOI_GIA_CUOC: 850,
        //Sửa theo yêu cầu Tuấn Anh 851-->952
        HNI_DOI_TOCDO: 952,
        HNI_DOI_HUONG_KN: 853,
        HNI_DOI_LOAIKENH: 852,
        HNI_DOI_TOCDO_HUONGKN: 855,
        HNI_DOI_TOCDO_LOAIKENH: 854,
        HNI_LOAIKENH_HUONGKN: 856,

        VTI_DOI_GiACUOC_TSL: 13465,
        VTI_DOI_GiACUOC_MEGA: 13466,
      },
      KHOANMUC_TT: {
        KMTT_DOITS: 8
      },
      chkTocDo_NIX_Enabled: true,
      chkTocDo_ISP_Enabled: true,
      txtCuoc_ISP_Enabled: true,
      txtCuoc_NIX_Enabled: true,
      txtSoLuong_IP_Enabled: true,
      txtCuoc_IP_Enabled: true,
      cboTocDo_Pir_Enabled: true,
      tsbtnNhapMoi_Enabled: true,
      tsbtnXoa_Enabled: true,
      tsbtnGhiLai_Enabled: true,
      tsbtnHuyBo_Enabled: true,
      tsbtnThemTB_Enabled: true,
      tsbtnThanhToan_Enabled: true,
      cboPhanTachTD_Enabled: false,
      cboPhanLoaiMGW_Enabled: false,
      cboTocDo_ISP_Enabled: true,
      cboTocDo_NIX_Enabled: true,
      cboLoaiTBiWan_Enabled: false,
      chkKhuyenMai_Enabled: true,
      cboVeTinh_Enabled: false,
      chkTocDo_PIR_Enabled: true,
      cboTinh_KN_Moi_Enabled: true,
      btnHuongKNWan_Enabled: true,
      cboTocDo_Enabled: true,
      cboLoaiKenh_Enabled: true,
      cboCongNghe_Moi_Enabled: true,
      btnThaydoi_Visible: false,
      cboChuQuanMEGAWAN_Enabled: true,
      cboLoaiModem_Enabled: true,
      cboThoiHanMEGAWAN_Enabled: true,
      cboTrangBiMEGAWAN_Enabled: true,
      chi_giao_net: true,
      kenh_trang: true,
      Click_kieuld: false,
      tinh: {},
      thuebao_id_tc: null,
      khachhang_id_tc: null,
      thongsoBan: {},
      thongsoTc: {},
      Capnhatthongso: null,
      chonTinh: true,
      kieuldid_tc: 0,
      check_pir: 0,
      kt_load: true,
      dtienld: 0,
      dvatld: 0,
      dtiencd: 0,
      dvatcd: 0,
      userComponentModule: null,
      userComponentModuleLienHe: null,
      userComponentModuleEmail: null,
      may_cn:'web',
      ip_cn:'127.0.0.1',
      nguoi_cn:'',
      thongtin_nd:{}
    }
  },
  created() {
    try {
      //this.Loading = true
      this.$root.$on('bv::modal::shown', (bvEvent, modalId) => {
        document.getElementsByClassName('modal-content').forEach(function (v) {
          v.removeAttribute('tabindex')
        })
      })
      this.$root.$on('bv::modal::hidden', (bvEvent, modalId) => {
        this.ValidateModal(bvEvent, modalId)
      })
      Promise.all([this.SetButton(-1)])
        .then((r) => {})
        .finally(() => {})
    } catch (err) {
      console.log('created error', err)
      this.Loading = false
    }
  },
  mounted() {
    this.Loading = true
    Promise.all([
      this.sp_ds_tienkhoanmuc_loaihd(),
      this.sp_ht_dichvuvt_combobox(),
      this.loai_kenh(),
      this.loai_modem(),
      this.tocdo_kenh(),
      this.trangbi(),
      this.lay_ds_loai_tb_megawan(),
      this.chuquan(),
      this.thoihan(),
      this.sp_lay_ds_tinh_kn(),
      this.sp_lay_ds_tatca_vetinh(),
      this.doitac(),
      this.congnghe(),
      this.phanloai_dv_cu_moi(),
      this.phanloai_tocdo_cu_moi(1),
      this.thamso_md_theo_ma(),
      this.thongtinnguoidung()
    ])
      .then((responses) => {
        //console.log(responses)
        this.Loading = false
        this.check_hdkh_cha_id().then((response) => {
          console.log('check_hdkh_cha_id', response)
        })
        try {
          this.$refs.reftxtMaTB.focus()
          // this.HienThiTTHopDongKH(null).then((r) => {
          //   console.log(r)
          // })
        } catch (err) {
          console.log('HienThiTTHopDongKH:mounted', err)
        }
      })
      .catch((err) => {
        console.log('Promise.all.error', err)
        //this.Loading = false
      })
      .finally((x) => {
        this.Loading = false
      })
    this.dtpNgayYeuCau = moment().format(this.dateFormat)
    this.dtpNgayLapHD = moment().format(this.dateFormat)
  },
  watch: {
    chkCongVan: function (val) {
      if (val) {
      } else {
        this.txtCongVan = ''
        this.dtpNgayCV = moment().format('DD/MM/YYYY')
      }
    },
    chkDoiTac: function (val) {
      if (!val) {
        this.txtMaHD_DT = ''
        this.txtCuocTKDT = '0'
        this.txtCuocTCDT = '0'
      }
    },
    cboKieuLD: async function (val) {
      try {
        this.Click_kieuld = true
        await this.cboKieuLD_SelectedValueChanged()
      } catch (err) {
        console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 466 ~ err', err)
      } finally {
        this.Click_kieuld = false
      }
    },
    Loading: function (val) {
      this.$root.showLoading(val)
    }
  },
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
    disabledRange: function (date) {
      return date > new Date()
    },
    async ValidateModal(bvEvent, modalId) {
      switch (modalId) {
        case 'modal-tieunganh':
          // if((this.tieunganh.dsTNC1 && this.tieunganh.dsTNC1.length>0 && (!this.tieunganh.TNC1_ID || this.tieunganh.TNC1_ID<=0)) || (this.tieunganh.dsTNC2 && this.tieunganh.dsTNC2.length>0 && (!this.tieunganh.TNC2_ID || this.tieunganh.TNC2_ID<=0)) || (this.tieunganh.dsTNC3 && this.tieunganh.dsTNC3.length>0 && (!this.tieunganh.TNC3_ID || this.tieunganh.TNC3_ID<=0)) || (this.tieunganh.dsTNC4 && this.tieunganh.dsTNC4.length>0 && (!this.tieunganh.TNC4_ID || this.tieunganh.TNC4_ID<=0)))
          //   await this.$toast.error('Bạn chưa chọn chi tiết ngành nghề. Hãy kiểm tra lại!');
          return
        default:
          return
      }
    },
    thamso_md_theo_ma: async function () {
      let input = {
        ma_ts: 'SINH_MA_GD_THEO_DV'
      }
      this.ts_sinhma_gd_theo_donvi = false
      let data = this.GetData(await api.thamso_md_theo_ma(this.axios, input))
      if (data == null || data.length == 0) return
      if (data.length > 0) {
        this.ts_sinhma_gd_theo_donvi = true
      }
    },
    async thongtinnguoidung(){
      try{
        let ip= await this.$root.token.getIP()
        if(ip!=null) this.ip_cn=ip;
        let maycn= await this.$root.token.getMachine()
        if(maycn!=null) this.may_cn= maycn;
        let nguoicn= await this.$root.token.getUserName()
        if(nguoicn!=null) this.nguoi_cn= nguoicn;
        console.log('thongtinnguoidung',this.ip_cn,this.may_cn)
      }
      catch(err){
        console.log('thongtinnguoidung-err',err)
      }
    },
    sp_ds_tienkhoanmuc_loaihd: async function () {
      let input = {
        loaihd_id: LoaiHopDong.THAYDOI_THONGSO_MEGAWAN
      }
      let data = this.GetData(await api.sp_ds_tienkhoanmuc_loaihd(this.axios, input))
      if (data == null || data.length == 0) return
      this.ds_tien_khoanmuc = data
      //console.log('sp_ds_tienkhoanmuc_loaihd', data)
    },
    sp_ht_dichvuvt_combobox: async function () {
      let input = {
        dichvu_vt_id: `${DichVuVienThong.MEGAWAN},${DichVuVienThong.METRONET},26`
      }
      let data = this.GetData(await api.sp_ht_dichvuvt_combobox(this.axios, input))
      if (data == null || data.length == 0) return
      this.DichVuVT = data.map((x) => ({ id: x.dichvuvt_id, text: x.ten_dvvt }))
      this.cboDichVuVT = this.DichVuVT[0].id
      this.dichvuvt_id = this.DichVuVT[0].id
      await this.cboDichVuVT_Change(this.DichVuVT[0].id)
    },
    loai_kenh: async function () {
      let input = {}
      let data = this.GetData(await api.loai_kenh(this.axios, input))
      if (data == null || data.length == 0) return
      this.LoaiKenh = data.map((x) => ({ id: x.loaikenh_id, text: x.loai_kenh }))
      this.cboLoaiKenh = this.LoaiKenh[0].id
      this.cboLoaiKenhCu = this.LoaiKenh[0].id
    },
    loai_modem: async function () {
      let input = {}
      let data = this.GetData(await api.loai_modem(this.axios, input))
      if (data == null || data.length == 0) return
      this.LoaiModem = data.map((x) => ({ id: x.loaimd_id, text: x.loai_md == null ? '' : x.loai_md }))
      this.cboLoaiModemCu = this.LoaiModem[0].id
      this.cboLoaiModem = this.LoaiModem[0].id
    },
    tocdo_kenh: async function () {
      let input = {}
      let data = this.GetData(await api.tocdo_kenh(this.axios, input))
      if (data == null || data.length == 0) return
      this.TocDo = data.map((x) => ({ id: x.tocdo_id, text: x.toc_do == null ? '' : x.toc_do }))
      if (this.TocDo != null && this.TocDo.length > 0) {
        this.cboTocDo = this.TocDo[0].id
        this.cboTocDoCu = this.TocDo[0].id
        this.cboTocDo_Pir = this.TocDo[0].id
        this.cboTocDo_PIR_Cu = this.TocDo[0].id
        this.cboTocDo_NIX = this.TocDo[0].id
        this.cboTocDo_ISP = this.TocDo[0].id
        this.cboTocDo_NIX_Cu = this.TocDo[0].id
        this.cboTocDo_ISP_Cu = this.TocDo[0].id
      }
    },
    trangbi: async function () {
      let input = {}
      let data = this.GetData(await api.trangbi(this.axios, input))
      if (data == null || data.length == 0) return
      this.TrangBiMEGAWAN = data.map((x) => ({ id: x.trangbi_id, text: x.tentrangbi == null ? '' : x.tentrangbi }))
      this.cboTrangBiMEGAWAN = this.TrangBiMEGAWAN[0].id
      this.cboTrangBiMEGAWANCu = this.TrangBiMEGAWAN[0].id
    },
    lay_ds_loai_tb_megawan: async function () {
      let input = {}
      let data = this.GetData(await api.lay_ds_loai_tb_megawan(this.axios, input))
      if (data == null || data.length == 0) return
      this.LoaiTBiWan = data.map((x) => ({ id: x.thietbidc_id, text: x.ten_tbi == null ? '' : x.ten_tbi }))
      this.cboLoaiTBiWan = this.LoaiTBiWan[0].id
      this.cboLoaiTBiWanCu = this.LoaiTBiWan[0].id
    },
    chuquan: async function () {
      let input = {}
      let data = this.GetData(await api.chuquan(this.axios, input))
      if (data == null || data.length == 0) return
      this.ChuQuanMEGAWAN = data.map((x) => ({ id: x.chuquan_id, text: x.tenchuquan == null ? '' : x.tenchuquan }))
      this.cboChuQuanMEGAWAN = this.ChuQuanMEGAWAN[0].id
      this.cboChuQuanMEGAWANCu = this.ChuQuanMEGAWAN[0].id
    },
    thoihan: async function () {
      let input = {}
      let data = this.GetData(await api.thoihan(this.axios, input))
      if (data == null || data.length == 0) return
      this.ThoiHanMEGAWAN = data.map((x) => ({ id: x.thoihan_id, text: x.tenthoihan }))
      this.cboThoiHanMEGAWAN = this.ThoiHanMEGAWAN[0].id
      this.cboThoiHanMEGAWANCu = this.ThoiHanMEGAWAN[0].id
    },
    sp_lay_ds_tinh_kn: async function () {
      let input = {}
      let data = this.GetData(await api.sp_lay_ds_tinh_kn(this.axios, input))
      if (data == null || data.length == 0) return
      this.Tinh_KN = data.map((x) => ({ id: x.tinh_id, text: x.tentinh == null ? '' : x.tentinh }))
      this.cboTinh_KN_Cu = this.Tinh_KN[0].id
      this.cboTinh_KN_Moi = this.Tinh_KN[0].id
    },
    sp_lay_ds_tatca_vetinh: async function () {
      let input = { loaidv_id: LOAI_DV.TRAM_VT }
      let data = this.GetData(await api.sp_lay_ds_tatca_vetinh(this.axios, input))
      if (data == null || data.length == 0) return
      this.VeTinh = data.map((x) => ({ id: x.donvi_id, text: x.ten_dv == null ? '' : x.ten_dv }))
      if (this.VeTinh != null && this.VeTinh.length > 0) {
        this.cboVeTinh = this.VeTinh[0].id
        this.cboVeTinhCu = this.VeTinh[0].id
      }
    },
    doitac: async function () {
      let input = {}
      let data = this.GetData(await api.doitac(this.axios, input))
      if (data == null || data.length == 0) return
      this.DoiTac = data.map((x) => ({ id: x.doitac_id, text: x.ten_dt == null ? '' : x.ten_dt }))
      this.cboDoiTac = this.DoiTac[0].id
      this.cboDoiTac_Cu = this.DoiTac[0].id
    },
    fn_thaydoi_thongso_wan_bc_capnhat: async function (parameters) {
      try {
        console.log('fn_thaydoi_thongso_wan_bc_capnhat:p_parameters', parameters)
        let tinh_id= this.tinh != null ? this.tinh.tinhthicong_id : null;
        let input = { p_parameters: JSON.stringify(parameters), tinhthicong_id: tinh_id }
        let response = await api.fn_thaydoi_thongso_wan_bc_capnhat(this.axios, input)
        console.log('🚀 fn_thaydoi_thongso_wan_bc_capnhat', response)
        if (response.data.error == '200' && response.data.error_code == 'BSS-00000000') {
          return response.data.data
        }
        alert(response.data.message)
        return response.data.message
      } catch (err) {
        console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 672 ~ err', err)
        return `error_code:${err.data.error_code}: ${err.data.message_detail}`
      }
    },
    fn_thaydoi_thongso_wan_bc_themhdthuebao: async function (parameters) {
      try {
        let tinh_id= this.tinh != null ? this.tinh.tinhthicong_id : null;
        let input = { p_parameters: JSON.stringify(parameters), tinhthicong_id: tinh_id  }
        let response = await api.fn_thaydoi_thongso_wan_bc_themhdthuebao(this.axios, input)
        if (response.data.error == '200' && response.data.error_code == 'BSS-00000000') {
          return response.data.data
        }
        return response.data.message
      } catch (err) {
        console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 672 ~ err', err)
        return `error_code:${err.data.error_code}: ${err.data.message_detail}`
      }
    },
    fn_thaydoi_thongso_wan_bc_xoa_hdtb: async function (vhdtb_id) {
      let tinh_id= this.tinh != null ? this.tinh.tinhthicong_id : null;
      let input = { vhdtb_id: vhdtb_id, tinhthicong_id: tinh_id ,p_parameters: vhdtb_id }
      let data = this.GetData(await api.fn_thaydoi_thongso_wan_bc_xoa_hdtb(this.axios, input))
      return data
    },
    lay_thuebao_kn_id_theo_matb: async function () {
      let input = { matb: this.txtMaTB }
      let data = this.GetData(await api.lay_thuebao_kn_id_theo_matb(this.axios, input))
      return data
    },
    lay_danhba_theo_matb_bc: async function () {
      let input = { dichvuvt_id: this.dichvuvt_id, donvi_dl_id: 0, loaihd_bancheo: 1, ma_tb: this.txtMaTB }
      let data = this.GetData(await api.lay_danhba_theo_matb_bc(this.axios, input))
      return data
    },
    lay_danhba_theo_thuebaoid: async function (tinh_id, vthuebaoid, dichvuvt_id, donvi_id, vloaihdbc) {
      let input = {
        dichvuvt_id: dichvuvt_id,
        donvi_id: donvi_id,
        loaihdbc: vloaihdbc,
        thuebao_id: vthuebaoid,
        tinhthicong_id: tinh_id
      }
      let data = this.GetData(await api.lay_danhba_theo_thuebaoid(this.axios, input))
      return data
    },
    lay_ds_db_megawan_theo_tbid_bc: async function (tinh_id, vthuebaoid) {
      try {
        let input = {
          thuebao_id: vthuebaoid,
          tinhthicong_id: tinh_id
        }
        let data = this.GetData(await api.lay_ds_db_megawan_theo_tbid_bc(this.axios, input))
        if (data != null && data.length > 0) return data[0]
        return {}
      } catch (err) {
        return {}
      }
    },
    LAY_THONGSO_THUEBAO_BANCHEO_HDTBID: async function (hdtb_id) {
      try {
        console.log('🚀LAY_THONGSO_THUEBAO_BANCHEO_HDTBID', hdtb_id, this.tinh)
        let phanvungId = this.$root.token.getPhanVungID()
        let input = {
          tinhthicong_id: phanvungId, // this.tinh.tinhthicong_id,
          hdtb_id: hdtb_id
        }
        let data = this.GetData(await api.lay_thongso_thuebao_hdbc(this.axios, input))
        return data
      } catch (err) {
        console.log('🚀LAY_THONGSO_THUEBAO_BANCHEO_HDTBID ~ err', err)
        return null
      }
    },
    LAY_TINHTHICONG_THEO_THUEBAOID: async function (thuebaoid) {
      console.log('LAY_TINHTHICONG_THEO_THUEBAOID:thuebaoid',thuebaoid)
      let kq = {}
      let input = {
        thuebaoid: thuebaoid
      }
      try {
        let data = this.GetData(await api.lay_tinh_theo_thuebaoid(this.axios, input))
        if (data != null && data.length > 0) {
          let tinh_thicong_id = data[0].tinh_thicong
          kq = await this.tinh_thicong(tinh_thicong_id)
        } else {
          kq.tinhthicong_id = 0
        }
      } catch (err) {
        kq.tinhthicong_id = 0
      }
      console.log('LAY_TINHTHICONG_THEO_THUEBAOID:result:kq',kq)
      return kq
    },
    lay_ds_db_megawan_theo_tbid: async function (vthuebaoid) {
      let input = {
        in_thuebao_id: vthuebaoid
      }
      let data = this.GetData(await api.lay_ds_db_megawan_theo_tbid_v2(this.axios, input))
      return data
    },
    lay_tinh_theo_matb: async function (matb) {
      let kq = {}
      let input = { matb: matb }
      let data = this.GetData(await api.lay_tinh_theo_matb(this.axios, input))
      if (data != null && data.length > 0) {
        let tinh_thicong_id = data[0].tinh_thicong
        kq = await this.tinh_thicong(tinh_thicong_id)
      } else {
        kq.tinhthicong_id = 0
      }
      return kq
    },
    lay_thuebaoid_thicong: async function (tinh_id, thuebao_id) {
      console.log('lay_thuebaoid_thicong','tinh_id:',tinh_id,'thuebao_id:',thuebao_id)
      try {
        let phanvungId = this.$root.token.getPhanVungID()
        //let input = {tinhthicong_id:phanvungId, tinh_id: tinh_id, thuebao_id: thuebao_id }
        let input = { vtinh_id: tinh_id, vthuebao_id: thuebao_id }
        // let input = { tinh_id: phanvungId, thuebao_id: thuebao_id }
        let data = this.GetData(await api.tc_lay_thuebaoid_thicong(this.axios, input))
        if (data != null && data.length > 0) {
          return data[0].thuebao_kn_id
        }
      } catch (err) {
        console.log("lay_thuebaoid_thicong~ err", err)
      }
      return 0
    },
    congnghe: async function () {
      let input = {}
      let data = this.GetData(await api.congnghe(this.axios, input))
      if (data == null || data.length == 0) return
      this.CongNghe = data.map((x) => ({ id: x.congnghe_id, text: x.congnghe == null ? '' : x.congnghe }))
      this.cboCongNghe_Moi = this.CongNghe[0].id
      this.cboCongNghe_Cu = this.CongNghe[0].id
    },
    lay_khuyenmai_hdtb: async function (loaitb_id, kieuld_id, phuongld_id, khoanmuctt_id, phold_id, apld_id, khuld_id, trangbi_id, doituong_id, tocdo_id, muccuoc_id, vloai_km) {
      let input = {
        vloaitb_id: loaitb_id,
        vkieuld_id: kieuld_id,
        vphuongld_id: phuongld_id,
        vkhoanmuctt_id: khoanmuctt_id,
        vphold_id: phold_id,
        vapld_id: apld_id,
        vkhuld_id: khuld_id,
        vtrangbi_id: trangbi_id,
        vdoituong_id: doituong_id,
        vtocdo_id: tocdo_id,
        vmuccuoc_id: muccuoc_id,
        vloaikm: vloai_km
      }
      let data = this.GetData(await api.lay_khuyenmai_hdtb(this.axios, input))
      return data
    },
    phanloai_dv_cu_moi: async function () {
      let input = {}
      let data = this.GetData(await api.phanloai_dv_cu_moi(this.axios, input))
      if (data == null || data.length == 0) return
      this.PhanLoaiMGW = data.map((x) => ({ id: x.phanloai_id, text: x.phanloai }))
      this.cboPhanLoaiMGWCu = this.PhanLoaiMGW[0].id
      this.cboPhanLoaiMGW = this.PhanLoaiMGW[0].id
    },
    phanloai_tocdo_cu_moi: async function (vkieu) {
      let input = { vkieu: vkieu }
      let data = this.GetData(await api.phanloai_tocdo_cu_moi(this.axios, input))
      if (data == null || data.length == 0) {
        this.PhanTachTD = [
          { id: 1, text: 'Phân tách' },
          { id: 2, text: 'Không phân tách' }
        ]
      } else {
        this.PhanTachTD = data.map((x) => ({ id: x.phantach_id, text: x.phantach }))
      }
      this.cboPhanTachTD = this.PhanTachTD[0].id
      this.cboPhanTachTDCu = this.PhanTachTD[0].id
    },
    loaihinh_tb: async function (dichvuvtId) {
      try {
        let input = {
          dichvuvt_id: dichvuvtId
        }
        let data = this.GetData(await api.loaihinh_tb(this.axios, input))
        if (data == null || data.length == 0) {
          this.LoaihinhTB = []
          return
        }
        this.LoaihinhTB = data.map((x) => ({ id: x.loaitb_id, text: x.loaihinh_tb }))
        this.cboLoaihinhTB = this.LoaihinhTB[0].id
        await this.cboLoaihinhTB_SelectedValueChanged(this.cboLoaihinhTB)
        // await this.lay_ds_kieu_ld(this.cboLoaihinhTB, LoaiHopDong.THAYDOI_THONGSO_MEGAWAN)
      } catch (err) {
        console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 796 ~ err', err)
      }
    },
    kieuld: async function (loaihinhtbId, loaihdId) {
      return
      try {
        let input = {}
        let data = this.GetData(await api.kieuld(this.axios, input))
        if (data == null || data.length == 0) return
        //console.log('kieuld', data)
        this.KieuLD = data.filter((x) => x.LOAIHD_ID == loaihdId).map((x) => ({ id: x.KIEULD_ID, text: x.TEN_KIEULD }))
        this.cboKieuLD = this.KieuLD[0].id
        this.cboKieuLD_SelectedValueChanged()
      } catch (err) {}
    },
    lay_ds_kieu_ld: async function (loaihinhtbId, loaihdId) {
      return
      let input = { vloaihd_id: loaihdId, vloaitb_id: loaihinhtbId }
      let data = this.GetData(await api.lay_ds_kieu_ld(this.axios, input))
      if (data == null || data.length == 0) {
        this.KieuLD = []
        return
      }
      //console.log('kieuld', data)
      this.KieuLD = data.map((x) => ({ id: x.kieuld_id, text: x.ten_kieuld }))
      this.cboKieuLD = this.KieuLD[0].id
      this.cboKieuLD_SelectedValueChanged()
    },
    Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    async popupClosed(val) {
      if (!val) {
        this.ClosePopup('popupComponentMGs')
        return
      }
      this.loading(true)
      try {
        switch (this.popupComponentName) {
          case 'SearchAccount':
            console.log('SearchAccount', val)
            if (val.ma_tb) this.txtMaTB = val.ma_tb
            this.ma_tb = this.txtMaTB
            //if(val.ma_tt) this.thanhtoan.matt = val.ma_tt;
            if (this.ma_tb && this.ma_tb.trim() != '') await this.txtMaTB_KeyPress()
            break
          case 'popupCTV':
            this.txtCTV = val.ten_ht
            this.txtCTVId = val.nhanvien_id
            this.ctv_id= val.nhanvien_id
            break
          case 'popupNGT':
            this.txtNguoiGT = val.ten_ht
            this.txtNguoiGTId = val.nhanvien_id
            this.nguoigt_id= val.nhanvien_id
            break
          case 'popupDSHuongKN':
            // this.txtHuongKn = val.shuongkn
            try{
              this.txtHuongKn = val.vma_tb
              this.thuebao_id_cha = val.iid_sochinh
              this.vhuongkn = val.vma_tb
              this.cboTinh_KN_Moi = val.itinh_kn_id
              this.txtDiaChi_KN = val.diachi_ld;
              this.txtVrf_moi= val.vrf;
            }
            catch(err){
              console.log('popupDSHuongKN',err)
            }
            break
          case 'popup-ChonKhuyenMai':
            this.cboKhuyenMai = val.khuyenmai.khuyenmai_id
            break
          case 'popupTienKM':
            console.log('popupTienKM', val)
            // var kmtt_id = -1;
            // var tien_kmtt = 0;
            // var tien_vat = 0;
            this.ds_tien_khoanmuc = val
            if (this.ds_tien_khoanmuc.length > 0) {
              for (var item of this.ds_tien_khoanmuc) {
                if (item['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DOITS) {
                  this.dtienld = item['tien']
                  this.dvatld = item['vat']
                }
                // else
                // {
                //     kmtt_id = item["khoanmuctt_id"];
                //     tien_kmtt = item["tien"];
                //     tien_vat = item["vat"];
                // }
                //this.HienThiTienKhuyenMaiLapDat(dtienkmld, dvatkmld, dtiendatcoc_km, dtienkmdc, dvatkmdc, dtiendatcoc_tt, kmtt_id, tien_kmtt, tien_vat);
                // await this.HienThiTienKhuyenMaiLapDat()
                await this.LayTongTien_HDTB()
              }
            }
            break
          case 'popup-Dangkychitietkhuyenmai':
            this.LaySothang_SD()

            this.dsKM_HDTB = val.dsKM_HDTB
            this.dtienkmld = val.tien_kmld
            this.dvatkmld = val.vat_kmld
            this.tyle_kmld = val.tyle_kmld
            this.tien_datcoc = val.tien_datcoc
            //Kiểm tra xem nếu không có tiền khuyến mại thì ưu tiên khuyến mại theo tỷ lệ
            if (this.dtienkmld == 0) {
              if (this.tyle_kmld > 0) {
                this.dtienkmld = Math.round((this.dtienld * this.tyle_kmld) / 100)
                this.dvatkmld = ((this.dtienld + this.dvatld) * this.tyle_kmld) / 100 - this.dtienkmld
              }
            }
            this.tong_kmld = this.dtienkmld + this.dvatkmld

            //Hiếu bổ sung hàm kiểm tra xem user đã chọn chi tiết khuyến mại nào chưa - 23.09.2010
            if (this.dsKM_HDTB != null && this.dsKM_HDTB.length > 0) {
            } else {
              this.dtienkmld = 0
              this.dvatkmld = 0
              this.tong_kmld = 0
              this.tien_datcoc = 0
              this.tyle_kmld = 0
            }
            await this.HienThiTienKhuyenMaiLapDat() //dtienkmld, dvatkmld, dtiendatcoc_km, dtienkmdc, dvatkmdc, dtiendatcoc_tt);
            break
        }
      } catch (ex) {
      } finally {
        this.ClosePopup('popupComponentMGs')
        this.loading(false)
      }
    },
    async acceptSearchContract(item) {
      console.log('🚀acceptSearchContract ~ item', item)
      this.txtMaGD = item.ma_gd
      if (this.txtMaGD != null && this.txtMaGD.trim() != '') {
        await this.txtMaGD_KeyPress()
      }
    },
    Focus(el) {
      this.select2FocusById(el)
    },
    select2FocusById(id) {
      try {
        $(`#${id}`).select2('focus')
      } catch (err) {
        console.log('🚀 select2FocusById ~ err', err)
      }
    },
    ShowPopupChonKhuyenMai() {
      var dulieu = {}
      dulieu.loai = 0
      dulieu.dt = this.KhuyenMaiRaw
      this.popupComponentData = dulieu
      this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popup-ChonKhuyenMai')
      this.popupComponentName = 'popup-ChonKhuyenMai'
      this.Popup('popupComponentMGs')
    },
    popupDSHuongKN() {
      //CHUQUAN.VNPT_TINH=9
      if (this.khachhang_id == 0 && this.hdkh_id == 0 && parseInt(this.cboChuQuanMEGAWAN) == 9) {
        this.ShowError('Chưa có thông tin khách hàng/hợp đồng !')
        return
      }
      //CHUQUAN.VNPT_TINH=9
      let vkieu = 1
      if (parseInt(this.cboChuQuanMEGAWAN) == 9) {
        vkieu = 0
      } else {
        vkieu = 1
      }
      let tinhkh_id = this.$root.token.getPhanVungID() // this.cboTinh_KN_Moi
      this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupDSHuongKNV2')
      this.popupComponentName = 'popupDSHuongKN'
      this.popupComponentData = {
        kieu: vkieu,
        dichvuvt_id: this.dichvuvt_id,
        tinhkn_id: tinhkh_id,
        db_hd: 0,
        isHopDong: 1,
        vid: this.khachhang_id
      } // O day dung enums CHUQUAN.VNPT_TINH nhung chi co 1 gia tri duy nhat = 9 nen loai bo enums luon!
      console.log('call popupDSHuongKN-dulieu', this.popupComponentData)
      this.Popup('popupComponentMGs')
    },
    ShowSearchAccount() {
      this.popupComponent = () => import('@/modules/search/subscriber/SearchAccount')
      this.popupComponentName = 'SearchAccount'
      try {
        this.popupComponent.data.isPopup = true
        this.popupComponent.data.vdichvuvt_id = 0
      } catch (ex) {
        if (this.debug) console.error(ex)
      }
      this.Popup('popupComponentMGs')
    },
    showPopupSearchContract() {
      this.$refs.popupSearchContract.showModal()
    },
    ShowPopupCTV() {
      if (this.chkCTV) {
        this.popupComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien')
        this.popupComponentName = 'popupCTV'
        // SearchAccount.data.isPopup = true;
        this.Popup('popupComponentMGs')
      }
    },
    ShowPopupNguoiGT() {
      if (this.chkNguoiGT) {
        this.popupComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu')
        this.popupComponentName = 'popupNGT'
        // SearchAccount.data.isPopup = true;
        this.Popup('popupComponentMGs')
      }
    },
    // async map_ngay_kt(khuyenmai_id) {
    //   let phanvung_id = this.$root.token.getPhanVungID()
    //   let input = {
    //     id_neo: "to_char(ngay_kt,'dd/MM/yyyy')",
    //     in_table: 'css.khuyenmai',
    //     in_dk: `where khuyenmai_id = ${khuyenmai_id}  and phanvung_id=${phanvung_id}`
    //   }
    //   let data = this.GetData(await api.map_id(this.axios, input))
    //   return data
    // },
    async map_ngay_kt(khuyenmai_id) {
      // let phanvung_id = this.$root.token.getPhanVungID()
      // let input = {
      //   id_neo: "to_char(ngay_kt,'dd/MM/yyyy')",
      //   in_table: 'css.khuyenmai',
      //   in_dk: `where khuyenmai_id = ${khuyenmai_id} and phanvung_id=${phanvung_id}`
      // }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_khuyenmai(this.axios, { param: khuyenmai_id, type: 1 }))
      return data
    },
    // async mapkhlon_id(khachhang_id) {
    //   let phanvung_id = this.$root.token.getPhanVungID()
    //   let input = { id_neo: 'khlon_id', in_table: 'css.db_khachhang', in_dk: `where khachhang_id = ${khachhang_id} and phanvung_id=${phanvung_id}` }
    //   let data = this.GetData(await api.map_id(this.axios, input))
    //   return data
    // },
    async mapkhlon_id(khachhang_id) {
      let phanvung_id = this.$root.token.getPhanVungID()
      // let input = { id_neo: 'khlon_id', in_table: 'css.db_khachhang', in_dk: `where khachhang_id = ${khachhang_id} and phanvung_id=${phanvung_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_db_khachhang(this.axios, { param: khachhang_id, type: 1 }))
      return data
    },
    async maploaitb_id(thuebao_id) {
      let phanvung_id = this.$root.token.getPhanVungID()
      // let input = { id_neo: 'loaitb_id', in_table: 'css.db_thuebao', in_dk: `where thuebao_id = ${thuebao_id} and phanvung_id=${phanvung_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_db_thuebao(this.axios, { param: thuebao_id, type: 8 }))
      return data
    },
    async mapkieuld_id(hdtb_id) {
      let phanvung_id = this.$root.token.getPhanVungID()
      // let input = { id_neo: 'kieuld_id', in_table: 'css.hd_thuebao', in_dk: `where hdtb_id = ${hdtb_id} and phanvung_id=${phanvung_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_hd_thuebao(this.axios, { param: hdtb_id, type: 6 }))
      return data
    },
    async mapnhanvien(nhanvien_id) {
      let phanvung_id = this.$root.token.getPhanVungID()
      // let input = { id_neo: 'ten_nv', in_table: 'admin.nhanvien', in_dk: `where nhanvien_id = ${nhanvien_id}  and phanvung_id=${phanvung_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_nhanvien(this.axios, { param: nhanvien_id, type: 3 }))
      return data
    },
    async maptb_cha(thuebao_id_cha) {
      let phanvung_id = this.$root.token.getPhanVungID()
      // let input = { id_neo: 'ma_tb', in_table: 'css.db_thuebao', in_dk: `where thuebao_id = ${thuebao_id_cha}  and phanvung_id=${phanvung_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_db_thuebao(this.axios, { param: thuebao_id_cha, type: 2 }))
      return data
    },
    async map_doitac(doitac_id) {
      // let input = { id_neo: 'ten_dt', in_table: 'css.doitac', in_dk: `where doitac_id = ${doitac_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_doitac(this.axios, { param: doitac_id, type: 1 }))
      return data
    },
    async maptinh(thuebao_id) {
      // let phanvung_id = this.$root.token.getPhanVungID()
      // let input = { id_neo: 'tinh_kn_id', in_table: 'css.db_mgwan', in_dk: `where thuebao_id = ${thuebao_id} and phanvung_id=${phanvung_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_db_mgwan(this.axios, { param: thuebao_id, type: 1 }))
      return data
    },
    async mapthanhtoan_id(thuebao_id) {
      // let phanvung_id = this.$root.token.getPhanVungID()
      // let input = { id_neo: 'thanhtoan_id', in_table: 'css.db_thuebao', in_dk: `where thuebao_id = ${thuebao_id} and phanvung_id=${phanvung_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_db_thuebao(this.axios, { param: thuebao_id, type: 1 }))
      return data
    },
    async mapcuoc_tb() {
      // let phanvung_id = this.$root.token.getPhanVungID()
      // let input = { id_neo: 'cuoc_tb', in_table: 'css.muccuoc_tb', in_dk: `where mucuoctb_id = ${this.thongsoTc.muccuoctb_id} and phanvung_id=${phanvung_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_muccuoc_tb(this.axios, { param: this.thongsoTc.muccuoctb_id, type: 1 }))
      return data
    },
    lay_ds_hopdong_theo_ma_gd: async function () {
      let donviId = this.$root.token.getDonViID()
      let donvidlId = this.$root.token.getDonViDuLieuID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let input = {
        ma_gd: this.txtMaGD,
        loaihd_id: LoaiHopDong.THAYDOI_THONGSO_MEGAWAN,
        donvi_id: parseInt(donviId),
        tthd_id: TrangThaiHD.MOI,
        nhanvien_id: parseInt(nhanvienId),
        donvi_dl_id: 0 // theo code
      }
      let data = this.GetData(await api.lay_ds_hopdong_theo_ma_gd(this.axios, input))
      return data
    },
    async LAY_DS_HOPDONG_THEO_MA_TB() {
      return await this.lay_ds_hopdong_theo_ma_tb()
    },
    lay_ds_hopdong_theo_ma_tb: async function () {
      let donviId = this.$root.token.getDonViID()
      let donvidlId = 0 // fix trong code  this.$root.token.getDonViDuLieuID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let input = {
        ma_tb: this.txtMaTB,
        in_loaihd_id: LoaiHopDong.THAYDOI_THONGSO_MEGAWAN,
        in_donvi_id: donviId,
        in_tthd_id: TrangThaiHD.MOI,
        in_nhanvien_id: nhanvienId,
        in_donvi_dl_id: donvidlId,
        in_dichvuvt_id: this.dichvuvt_id,
        in_loaitb_id: this.loaitb_id
      }
      let data = this.GetData(await api.lay_ds_hopdong_theo_ma_tb(this.axios, input))
      return data
    },
    async TRACUU_LAY_DS_HOPDONG_THEO_MA_TB() {
      try {
        return await this.tra_cuu_lay_ds_hopdong_theo_ma_tb()
      } catch (err) {
        return []
      }
    },
    tra_cuu_lay_ds_hopdong_theo_ma_tb: async function () {
      let donviId = this.$root.token.getDonViID()
      let donvidlId = 0 // fix trong code  this.$root.token.getDonViDuLieuID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let input = {
        tinhthicong_id:this.$root.token.getPhanVungID(),
        in_ma_tb: this.txtMaTB,
        in_loaihd_id: LoaiHopDong.THAYDOI_THONGSO_MEGAWAN,
        in_donvi_id: donviId,
        in_tthd_id: TrangThaiHD.MOI,
        in_nhanvien_id: nhanvienId,
        in_donvi_dl_id: donvidlId,
        in_dichvuvt_id: this.dichvuvt_id,
        in_loaitb_id: 0 // this.loaitb_id

      }
      let data = this.GetData(await api.tra_cuu_lay_ds_hopdong_theo_ma_tb(this.axios, input))
      return data
    },
    // LAY_DS_HOPDONG_TB_THEO_HDKH_ID
    lay_ds_hopdong_thuebao_theo_hdkh: async function (hdKhId) {
      let donviId = this.$root.token.getDonViID()
      let donvidlId = this.$root.token.getDonViDuLieuID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let input = { hdKhId: hdKhId }
      let data = this.GetData(await api.lay_ds_hopdong_thuebao_theo_hdkh(this.axios, input))
      return data
    },
    lay_ds_hopdong_tb_theo_hdkh_id_bc: async function (hdKhId) {
      let input = { hdkhid: hdKhId }
      let data = this.GetData(await api.lay_ds_hopdong_tb_theo_hdkh_id(this.axios, input))
      return data
    },
    tinh_thicong: async function (tinhthicong_id) {
      let input = { tinhthicong_id: tinhthicong_id }
      let data = this.GetData(await api.tinh_thicong(this.axios, input))
      if (data != null && data.length > 0) {
        let tinh_thicong = data[0]
        tinh_thicong.tinhthicong_id = tinhthicong_id
        return tinh_thicong
      }
      return { user: '', pass: '', nhanvien_id: 0, donvi_id: 0, matinh: '', TNS: '', tinhthicong_id: 0, ip_dll: '' }
    },
    lay_ds_tinh_thicong_combobox_v2: async function () {
      let input = {}
      let data = this.GetData(await api.lay_ds_tinh_thicong_combobox_v2(this.axios, input))
      return data
    },
    lay_thongso_thuebao_thuebaoid_bc: async function (tinh_id, thuebao_id) {
      console.log('lay_thongso_thuebao_thuebaoid_bc',tinh_id, thuebao_id)
      try {
        let phanvungId = this.$root.token.getPhanVungID()

        // let input = { tinh_id: tinh_id, thuebao_id: thuebao_id }
        // let data = this.GetData(await api.lay_thongso_thuebao_thuebaoid_bc(this.axios, input))

        let input = { tinhthicong_id: tinh_id, thuebao_id: thuebao_id }
        let data = this.GetData(await api.bc_lay_thongso_thuebao_thuebaoid(this.axios, input))
        return data
      } catch (err) {
        return null
      }
    },
    lay_thongso_thuebao_thuebaoid: async function (thuebaoid) {
      console.log('lay_thongso_thuebao_thuebaoid',thuebaoid)
      let input = { thuebaoid: thuebaoid }
      let data = this.GetData(await api.lay_thongso_thuebao_thuebaoid(this.axios, input))
      return data
    },
    lay_ds_hopdong_thuebao_mgwan: async function (hdtb_id) {
      let input = { hdtbId: hdtb_id }
      let data = this.GetData(await api.lay_ds_hopdong_thuebao_mgwan(this.axios, input))
      return data
    },
    lay_tien_hd_km_theo_hdtb: async function (hdtb_id) {
      let input = { hdtb_id: hdtb_id }
      let data = this.GetData(await api.lay_tien_hd_km_theo_hdtb(this.axios, input))
      return data
    },
    fn_lay_text_huongketnoi: async function (vid, vloai) {
      let input = { vid: vid, vloai: vloai }
      let data = this.GetData(await api.fn_lay_text_huongketnoi(this.axios, input))
      return data
    },
    lay_ds_bddb_mgwan_theo_hdtb_id: async function (hdtb_id) {
      let phanvungId = this.$root.token.getPhanVungID()
      let input = {
        p_phanvung_id: phanvungId,
        vhdtb_id: hdtb_id
      }
      let data = this.GetData(await api.lay_ds_bddb_mgwan_theo_hdtb_id(this.axios, input))
      return data
    },
    lay_chitiet_km_theo_hdtb_id: async function (hdtb_id, loai_km) {
      let phanvungId = this.$root.token.getPhanVungID()
      let input = {
        hdtb_id: hdtb_id,
        loai_km: loai_km
      }
      let data = this.GetData(await api.lay_chitiet_km_theo_hdtb_id(this.axios, input))
      return data
    },
    //
    lay_ds_diachi_theo_dbtbid: async function (thuebao_id) {
      let input = {
        thuebao_id: thuebao_id
      }
      let data = this.GetData(await api.lay_ds_diachi_theo_dbtbid(this.axios, input))
      return data
    },
    lay_ds_danhba_megawan: async function (thuebaoId) {
      let input = {
        thuebaoId: thuebaoId
      }
      let data = this.GetData(await api.lay_ds_danhba_megawan(this.axios, input))
      return data
    },
    async ThemMoi_HopDong() {
      await this.sp_themmoi_hd_tdts_megawan()
    },
    sp_themmoi_hd_tdts_megawan: async function () {
      let phanvungid = this.$root.token.getPhanVungID()
      let input = {
        huonggiao_id: this.huonggiao_id,
        js_ct_tienhd: JSON.stringify(this.dsCTTHD),
        js_hd_khachhang: JSON.stringify(this.dsHDKH),
        js_hd_thuebao: JSON.stringify(this.dsHDTB),
        js_hdtb_dv: JSON.stringify(this.dsHDTBDV),
        js_hdtb_mgwan: JSON.stringify(this.dsHDTB_MEWAN),
        js_khuyenmai_hdtb: JSON.stringify(this.dsKM_HDTB),
        js_thongtin_tt: JSON.stringify(this.dsTTTT),
        phanvung_id: phanvungid
      }
      let data = this.GetData(await api.sp_themmoi_hd_tdts_megawan(this.axios, input))
      return data
    },
    async CapNhat_HopDong() {
      await this.sp_capnhat_hd_tdts_megawan()
    },
    sp_capnhat_hd_tdts_megawan: async function () {
      let phanvungid = this.$root.token.getPhanVungID()
      let input = {
        js_ct_tienhd: JSON.stringify(this.dsCTTHD),
        js_hd_khachhang: JSON.stringify(this.dsHDKH),
        js_hd_thuebao: JSON.stringify(this.dsHDTB),
        js_hdtb_dv: JSON.stringify(this.dsHDTBDV),
        js_hdtb_mgwan: JSON.stringify(this.dsHDTB_MEWAN),
        js_khuyenmai_hdtb: JSON.stringify(this.dsKM_HDTB),
        js_thongtin_tt: JSON.stringify(this.dsTTTT),
        phanvung_id: phanvungid
      }
      let data = this.GetData(await api.sp_capnhat_hd_tdts_megawan(this.axios, input))
      return data
    },
    async ThemMoi_HopDongTB() {
      let parameters = {
        MA_TB: this.txtMaTB,
        DICHVUVT_ID: this.cboDichVuVT,
        KHACHHANG_ID: this.khachhang_id,
        THUEBAO_ID: this.thuebao_id,
        HDTB_ID: this.hdtb_id,
        MA_KH: this.txtMaKH,
        TEN_KH: this.txtTenKH,
        MA_HD: this.txtMaHD,
        LOAITB_ID: this.loaitb_id,
        TOCDO_ID: this.cboTocDo,
        TOCDO_CU_ID: this.cboTocDoCu,
        MUCCUOC_ID: this.thongsoTc.muccuoc_id == null ?0: this.thongsoTc.muccuoc_id,
        KIEULD_ID: this.cboKieuLD,
        CONGNGHE_MOI_ID: this.cboCongNghe_Moi,
        CONGNGHE_CU_ID: this.cboCongNghe_Cu,
        CUOC_SD: '',
        CUOC_TBI: '',
        KIEUTRA_MOI_ID: '',
        KIEUTRA_CU_ID: '',
        TOCDOTHUC_MOI_ID: '',
        TOCDOTHUC_CU_ID: '',
        HD_KHACHHANG: this.dsHDKH,
        HD_THUEBAO: this.dsHDTBTest,
        THONGTIN_TT: this.dsTTTT,
        CT_TIENHD: this.dsCTTHD,
        KHUYENMAI_HDTB: this.dsKM_HDTB,
        HDTB_ADSL: this.dsHDTB_MEWAN, // ja_hdtb_mewan
        HDTB_DV: this.dsHDTBDV,
        HD_BANCHEO: this.dsHDBC,
        HDTB_BANCHEO: this.dsHDTBBC,
        HDBC_MGWAN: this.dsHDBCMGWAN,
        HDBC_ADSL: this.dsHDBCADSL,
        HDBC_CD: this.dsHDBCCD,
        HDBC_TSL: this.dsHDBCTSL,
        HDBC_IMS: this.dsHDBCIMS,
        DANGKY_DVGT: this.dsDANGKYDVGT,
        l_kt: 0,
        l_kiemtra: 0,
        l_loai_onu_id: 0,
        l_kt_hagoi: 0,
        l_quyen_hagoi: 0
      }
      return await this.fn_thaydoi_thongso_wan_bc_themhdthuebao(parameters)
    },
    lay_danhba_theo_matb: async function (ma_tb, dichvuvt_id, donvidlId) {
      let input = {
        in_dichvuvt_id: dichvuvt_id,
        in_donvi_dl_id: donvidlId,
        in_ma_tb: ma_tb
      }
      let data = this.GetData(await api.lay_danhba_theo_matb(this.axios, input))
      return data
    },
    kiemtra_thuebao_lap_hopdong_khac: async function (ma_tb, maGd, dichvuvt_id, loaiHdId) {
      //let donvidlId = this.$root.token.getDonViID()
      let input = {
        dichVuVtId: dichvuvt_id,
        loaiHdId: loaiHdId,
        maGd: maGd,
        maTb: ma_tb
      }
      let data = this.GetData(await api.kiemtra_thuebao_lap_hopdong_khac(this.axios, input))
      if ((data != null && data.length > 0) || (data != null && data.ma_gd != null && data.ma_gd != '')) {
        let item = data
        let message = `Số máy/Acc: ${ma_tb} đang được lập bởi hợp đồng (phụ lục) ${item.ten_loaihd} có mã giao dịch ${item.ma_gd} do User ${item.nguoi_cn} thuộc đơn vị ${item.ten_dv} thực hiện vào ngày ${item.ngay_yc}`
        this.ShowError(message)
        return true
      }
      return false
    },
    kiemtra_hd_bancheo_tc: async function (thuebao_id, loaihd_id) {
      //let donvidlId = this.$root.token.getDonViID()
      try {
          this.loading(true)
          let input = { thuebao_id: thuebao_id, loaihd_id: loaihd_id }
          let response = await api.kiemtra_hd_bancheo_tc(this.axios, input)
          this.loading(false)
          if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
              return response.data.data
          }else{
              if(response && response.data && response.data.message){
                  return response.data.message
              }else{
                return 'Lỗi kiểm tra hd bán chéo TC'
              }   
          }
      }catch(error){
        this.loading(false)
        if(error.data&&error.data.message){
          return error.data.message
        }else if(error.response&&error.response.data&&error.response.data.message){
          return error.response.data.message
        }else{
          return 'Lỗi kiểm tra hd bán chéo TC'
        }
      }
    },
    Lay_Ma_GD_V2: async function (donvi_id, loaihd_id) {
      //let donvidlId = this.$root.token.getDonViID()
      let input = { donvi_id: donvi_id, loaihd_id: loaihd_id }
      let data = this.GetData(await api.sinh_magd_v2(this.axios, input))
      return data
    },
    Lay_Ma_GD: async function (loaihd_id) {
      //let donvidlId = this.$root.token.getDonViID()
      let input = { loaihd_id: loaihd_id }
      let data = this.GetData(await api.sinh_magd(this.axios, input))
      return data
    },
    lay_tien_khoanmuc_ld: async function (loaihd_id, ngay_yc, dichvuvt_id, loaitb_id, kieuld_id, phuongld_id, tocdo_id, muccuoc_id, ltb_adsl_id, ltb_adsl_moi_id, ltb_adsl_cu_id, tocdo_moi_id, tocdo_cu_id, kh_cd, phold_id, apld_id, khuld_id, doituong_id, trangbi_id) {
      //let donvidlId = this.$root.token.getDonViID()
      let input = {
        loaihd_id: loaihd_id,
        ngay_yc: ngay_yc,
        dichvuvt_id: dichvuvt_id,
        loaitb_id: loaitb_id,
        kieuld_id: kieuld_id,
        phuongld_id: phuongld_id,
        tocdo_id: tocdo_id,
        muccuoc_id: muccuoc_id,
        ltb_adsl_id: ltb_adsl_id,
        ltb_adsl_moi_id: ltb_adsl_moi_id,
        ltb_adsl_cu_id: ltb_adsl_cu_id,
        tocdo_moi_id: tocdo_moi_id,
        tocdo_cu_id: tocdo_cu_id,
        kh_cd: kh_cd,
        phold_id: phold_id,
        apld_id: 0,
        khuld_id: khuld_id
      }
      let data = this.GetData(await api.lay_tien_khoanmuc_ld(this.axios, input))
      return data
    },
    async Lay_CUOCTK_CUOCTC(loaitb_id, tocdo_id, doituong_id, loaikenh_id, kieu) {
      return this.sp_lay_cuoctk_cuoctc(loaitb_id, tocdo_id, doituong_id, loaikenh_id, kieu)
    },
    sp_lay_cuoctk_cuoctc: async function (loaitb_id, tocdo_id, doituong_id, loaikenh_id, kieu) {
      let input = {
        vloaitb_id: loaitb_id,
        vtocdo_id: tocdo_id,
        vdoituong_id: doituong_id,
        vloaikenh_id: loaikenh_id,
        vkieu: kieu
      }
      let data = this.GetData(await api.sp_lay_cuoctk_cuoctc(this.axios, input))
      return data
    },
    kiemtra_thoihan_km: async function (khuyenmai_id, ngay_yc) {
      let input = {
        vkhuyenmai_id: khuyenmai_id,
        vngay_yc: ngay_yc
      }
      let data = this.GetData(await api.kiemtra_thoihan_km(this.axios, input))
      return data
    },
    LayHuongGiao_TiepNhan_V2: async function (loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id, vloaikenh_id, vdichvuvt_id) {
      return await this.lay_huonggiao_tiepnhan_v2(loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id, vloaikenh_id, vdichvuvt_id)
    },
    lay_huonggiao_tiepnhan_v2: async function (loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id, vloaikenh_id, vdichvuvt_id) {
      let phanvungid = this.$root.token.getPhanVungID()
      let input = {
        dichvuvt_id: vdichvuvt_id,
        kieuld_id: kieuld_id,
        loaihd_id: loaihd_id,
        loaikenh_id: vloaikenh_id,
        loaitb_id: loaitb_id,
        muccuoc_id: muccuoc_id,
        // phanvung_id: phanvungid,
        tocdo_id: tocdo_id
      }
      let data = this.GetData(await api.lay_huonggiao_tiepnhan_v2(this.axios, input))
      return data
    },
    LayHuongGiao_TiepNhan: async function (loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id) {
      return await this.lay_huonggiao_tiepnhan(loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id)
    },
    lay_huonggiao_tiepnhan: async function (loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id) {
      let input = {
        kieuLdId: kieuld_id,
        loaiHdId: loaihd_id,
        loaiTbId: loaitb_id,
        mucCuocId: muccuoc_id,
        tocDoId: tocdo_id
      }
      let data = this.GetData(await api.lay_huonggiao_tiepnhan(this.axios, input))
      return data
    },
    LayHuongGiao_TiepNhan_Tinh: async function (loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id) {
      // return await this.lay_huonggiao_tiepnhan_tinh(this.tinh.tinhthicong_id, loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id)
      return await this.layhuonggiao_tiepnhan_bancheo(this.tinh.tinhthicong_id, loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id)
    },
    lay_huonggiao_tiepnhan_tinh: async function (tinh_id, loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id) {
      let input = {
        tinhId: tinh_id,
        kieuLdId: kieuld_id,
        loaiHdId: loaihd_id,
        loaiTbId: loaitb_id,
        mucCuocId: muccuoc_id,
        tocDoId: tocdo_id
      }
      let data = this.GetData(await api.lay_huonggiao_tiepnhan_tinh(this.axios, input))
      return data
    },
    layhuonggiao_tiepnhan_bancheo: async function (tinh_id, loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id) {
      let input = {
        tinhthicong_id: tinh_id,
        vkieuld_id: kieuld_id,
        vloaihd_id: loaihd_id,
        vloaitb_id: loaitb_id,
        vmuccuoc_id: muccuoc_id,
        vtocdo_id: tocdo_id
      }
      let data = this.GetData(await api.layhuonggiao_tiepnhan_bancheo(this.axios, input))
      return data
    },
    kiemtra_makh_mtt: async function (ma_kh, kieu, loaihd_id) {
      let input = {
        ma_kh: ma_kh,
        kieu: kieu,
        loaihd_id: loaihd_id
      }
      let data = this.GetData(await api.kiemtra_makh_mtt(this.axios, input))
      return data
    },
    kiemtra_hdtb_khi_themmoi: async function (maTb, hdkh_id, dichvuvt_id, loaiTbId) {
      let input = { maTb: maTb, hdkhId: hdkh_id, dichVuVtId: dichvuvt_id, loaiTbId: loaiTbId }
      let data = this.GetData(await api.kiemtra_hdtb_khi_themmoi(this.axios, input))
      return data
    },
    //KIEMTRA_DB_KHI_THEMMOI
    kiemtra_db_khi_themmoi: async function (maTb, khachHangId, thuebao_id, dichvuvt_id) {
      let input = { maTb: maTb, khachHangId: khachHangId, thueBaoId: thuebao_id, dichVuVtId: dichvuvt_id }
      let data = this.GetData(await api.kiemtra_danhba_khi_themmoi(this.axios, input))
      return data
    },
    kiemtra_tb_cung_makh_tb_pl: async function (ma_gd, thuebao_id) {
        let input = { ma_gd: ma_gd, thuebao_id: thuebao_id }
        let data = this.GetData(await api.kiemtra_tb_cung_makh_tb_pl(this.axios, input))
        return data=='1'
    },
    get_donvi_id_loaidvId_kieudv_id: async function (thuebao_id) {
      let input = { thuebao_id: thuebao_id }
      let data = this.GetData(await api.get_donvi_id_loaidvId_kieudv_id(this.axios, input))
      return data
    },
    huonggiao_ldv: async function (quytrinh_id, loaidv_dich_id) {
      // let input = { quytrinh_id: quytrinh_id, loaidv_dich_id:  } // loaidv_dich_id fix tren api ko can truyen
      let input = { quytrinh_id: quytrinh_id, loaidv_dich_id: "" }
      let data = this.GetData(await api.huonggiao_ldv(this.axios, input))
      return data
    },
    update_huonggiao_tiepnhan: async function (hdtb_id, huonggiao_id) {
      let input = { hdtb_id: hdtb_id, huonggiao_id: huonggiao_id }
      let data = this.GetData(await api.update_huonggiao_tiepnhan(this.axios, input))
      return data
    },
    luong_dv: async function (luongId) {
      let input = { luongId: luongId }
      let data = this.GetData(await api.luong_dv(this.axios, input))
      return data
    },
    getHdkhId: async function (luongId) {
      let input = { luongId: luongId }
      let data = this.GetData(await api.getHdkhId(this.axios, input))
      return data
    },
    GetKey: async function (keyName) {
      let keyVal = await this.getKey(keyName)
      console.log(`GetKey:${keyName}:${keyVal}`)
      return keyVal
    },
    getKey: async function (keyName) {
      var input = {
        keyname: keyName,
        numblocksize: 1,
        numretry: 10
      }
      let response = await api.getKey(this.axios, input)
      if (response.data.error === 200 || response.data.error === '200') {
        let data = response.data.data
        return data
      } else {
        return 0
      }
    },
    xoa_hdkh_v2: async function (hdkh_id, loaihd_id) {
      let input = { hdkh_id: hdkh_id, loaihd_id: loaihd_id }
      let data = this.GetData(await api.xoa_hdkh_v2(this.axios, input))
      return data
    },
    xoa_hdtb: async function (hdtb_id) {
      let input = { hdtb_id: hdtb_id }
      let data = this.GetData(await api.xoa_hdtb(this.axios, input))
      return data
    },
    thongTinEmailOpen: async function () {
      if (this.hdkh_id > 0) {
        try {
          this.userComponentModuleEmail = thongTinEmailCom
          this.userComponentModuleEmail.hdkh_id = this.HdKhIdInt
          this.userComponentModuleEmail.khachhang_id = this.KhachHangIdInt
          await this.$refs.thongTinEmailPopUp.getDsThongTinEmail()
        } catch (err) {
          console.log('thongTinEmailOpen-err', err)
        } finally {
          this.$refs.dialogObjthongTinEmail.show()
        }
      }
      else{
        this.ShowError('Chưa có hợp đồng khách hàng !')
      }
    },
    thongTinEmailOpen_Old: async function () {
      try {
        await this.$refs.thongTinEmailPopUp.getDsThongTinEmail()
      } catch (err) {
        console.log('thongTinEmailOpen-err', err)
      } finally {
        this.$refs.dialogObjthongTinEmail.show()
      }
    },
    async lienHeOpen() {
      console.log('lienHeOpen:hdKhId')
      try {
        if (this.hdkh_id > 0) {
          try {
            console.log('lienHeOpen:hdKhId', this.hdkh_id)
            this.userComponentModuleLienHe = lienHe
            this.userComponentModuleLienHe.hdkh_id = this.HdKhIdInt
            this.userComponentModuleLienHe.khachhang_id = this.KhachHangIdInt
            this.userComponentModuleLienHe.ten_kh = this.txtTenKH
            this.userComponentModuleLienHe.diachi = this.txtDiaChiKH
            await this.$refs.thongTinLienHePopUp.getThongTinLienHe()
          } catch (errLH) {
            console.log('🚀 ~ file: ChangeMegaWanConfig.vue ~ line 1334 ~ lienHeOpen ~ errLH', errLH)
          } finally {
            this.$refs.thongtinlienheDialog.show(true)
          }
        }
        else{
          this.ShowError('Chưa có hợp đồng khách hàng !')
        }
      } catch (err) {
        console.log('lienHeOpen-err', err)
      }
    },
    async lienHeOpen_Old() {
      try {
        if (this.hdKhId > 0) {
          await this.$refs.thongTinLienHePopUp.getThongTinLienHe()
        }
      } catch (err) {
        console.log('lienHeOpen-err', err)
      } finally {
        this.$refs.thongtinlienheDialog.show()
      }
    },
    async btnChiTietKM_Click() {
      try {
        if (this.chkKhuyenMai) {
          if (this.KhuyenMai != null && this.KhuyenMai.length > 0) {
            let vmuccuoctn_id = 0
            let vtocdotn_id = 0
            if (this.dichvuvt_id == DichVuVienThong.ADSL) {
              vtocdotn_id = parseInt(this.cboTocDo)
              if (parseInt(this.cboLoaihinhTB) == LoaiHinhTB.INTERNET_ADSL || parseInt(this.cboLoaihinhTB) == LoaiHinhTB.INTERNET_FTTH || parseInt(cboLoaihinhTB) == LoaiHinhTB.WIFI_FIBER) {
                vmuccuoctn_id = 0
              }
            } else if (this.dichvuvt_id == DichVuVienThong.MEGAWAN||this.dichvuvt_id == 25) {
              vtocdotn_id = parseInt(this.cboTocDo)
            } else if (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id == 26) {
              vtocdotn_id = parseInt(this.cboTocDo)
            }
            //Hiếu bổ sung thêm hàm lấy tiền khuyến mại 23.09.2010
            let khuyenmai_id = parseInt(this.cboKhuyenMai)
            // CheckDataFacade obj = new CheckDataFacade();
            let ngay_cn = moment().format('DD/MM/YYYY')
            if ((await this.kiemtra_thoihan_km(khuyenmai_id, ngay_cn)) == 0) {
              let ngay_kt_km = ''
              //ngay_kt_km = objCheckData.MAP_ID("to_char(ngay_kt,'dd/MM/yyyy')", DatabaseConstants.DB2 + ".khuyenmai", "where khuyenmai_id =" + khuyenmai_id);
              ngay_kt_km = await this.map_ngay_kt(khuyenmai_id)
              this.$bvModal
                .msgBoxConfirm('Khuyến mại ' + this.cboKhuyenMai + ' đã hết thời hạn từ ngày ' + ngay_kt_km + '.Bạn có muốn tiếp tục chọn chi tiết khuyến mại?', {
                  title: '',
                  size: 'sm',
                  okTitle: 'Đồng ý',
                  cancelTitle: 'Hủy'
                })
                .then(async (v) => {
                  if (!v) {
                    this.dtienkmld = 0
                    this.dvatkmld = 0
                    this.tyle_kmld = 0
                    this.tong_kmld = 0
                    await this.HienThiTienKMLapDat()
                    return
                  }
                  // Xử lý code ở đây
                  console.log('abc')
                })
            }
            /*
                        //VinhPV xem lai sua loi cho anh Long
                        frmChonKhuyenMai frm = new frmChonKhuyenMai(dsKM_HDTB, vtocdotn_id, vmuccuoctn_id, parseInt(cboLoaihinhTB));
                        frm.khuyenmai_id = khuyenmai_id;
                        frm.vdichvuvt_id = dichvuvt_id;
                        frm.ShowDialog();
                        if (frm.chapnhan)
                        {
                          this.dsKM_HDTB = frm.dsKM_HDTB;
                          this.dtienkmld = frm.tien_kmld;
                          this.dvatkmld = frm.vat_kmld;
                          this.tyle_kmld = frm.tyle_kmld;
                          //Kiểm tra xem nếu không có tiền khuyến mại thì ưu tiên khuyến mại theo tỷ lệ
                          if (this.dtienkmld == 0)
                          {
                            if (this.tyle_kmld > 0)
                            {
                              this.dtienkmld = Math.Round(dtienld * this.tyle_kmld / 100);
                              this.dvatkmld = (this.dtienld + this.dvatld) * this.tyle_kmld / 100 - this.dtienkmld;
                            }
                          }
                          this.tong_kmld = this.dtienkmld + this.dvatkmld;
                        }
            */
            this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popup-Dangkychitietkhuyenmai.vue')
            this.popupComponentName = 'popup-Dangkychitietkhuyenmai'
            this.popupComponentData = { khuyenmai_id: this.khuyenmai_id, vdichvuvt_id: this.dichvuvt_id, dsDC_HDTB: this.LowerCasePropertyList(this.dsKM_HDTB), vtocdotn_id: vtocdotn_id, vmuccuoctn_id: vmuccuoctn_id, loaihinh_tb: this.cboLoaihinhTB } // 0: khuyen mai, 1 tra truoc
            this.Popup('popupComponentMGs')
            //Hiếu bổ sung hàm kiểm tra xem user đã chọn chi tiết khuyến mại nào chưa - 23.09.2010
            if (this.dsKM_HDTB != null && this.dsKM_HDTB.length > 0) {
            } else {
              this.dtienkmld = 0
              this.dvatkmld = 0
              this.tong_kmld = 0
              this.tyle_kmld = 0
            }
            await this.HienThiTienKMLapDat()
          }
        }
      } catch (err) {
        this.ShowError('Lỗi')
      }
    },
    async HienThiTienKMLapDat() {
      for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
        // KMTT_KM_LAPDAT = 19;//Khuyến mại lắp đặt
        if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == 19) {
          this.ds_tien_khoanmuc[i]['tien'] = Math.Abs(this.dtienkmld) * -1
          this.ds_tien_khoanmuc[i]['vat'] = Math.Abs(this.dvatkmld) * -1
        }
      }
      await this.LayTongTien_HDTB()
    },
    async LayTongTien_HDTB() {
      try {
        let dtongtien = 0
        let dtongvat = 0
        for (var item of this.ds_tien_khoanmuc) {
          dtongtien += parseFloat(item['tien'])
          dtongvat += parseFloat(item['vat'])
        }
        this.txtTongTien = dtongtien
        this.txtTongVat = dtongvat
      } catch (err) {
        console.log('LayTongTien_HDTB', err)
      }
    },
    async btnTien_Click() {},
    async popupTienKM() {
      var ds = []
      if (this.thamsomacdinh['HT_KMTT'] && this.thamsomacdinh['HT_KMTT'] == 1) {
        await API.post_lay_ds_tien_kmtt(this.axios, {
          vkieuld_id: this.cboKieuLD,
          vloaihd_id: LoaiHopDong.THAYDOI_THONGSO_MEGAWAN,
          vloaitb_id: this.cboLoaihinhTB
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
        this.popupComponentData = {
          ds: ds,
          cols: [
            {
              fieldName: 'tentat_kmtt',
              headerText: 'Khoản mục',
              allowFiltering: false,
              allowSorting: false,
              isPrimaryKey: true
            },
            {
              fieldName: 'tongtien',
              textAlign: 'right',
              allowEditing: true,
              editType: 'numericedit',
              format: 'N',
              customAttributes: { class: 'tongtien' },
              headerText: 'Tổng tiền',
              allowFiltering: false,
              allowSorting: false,
              edit: 'tongtienParam'
            },
            {
              fieldName: 'tien',
              textAlign: 'right',
              allowEditing: true,
              editType: 'numericedit',
              format: 'N',
              customAttributes: { class: 'cuoclapdat' },
              headerText: 'Tiền',
              allowFiltering: false,
              allowSorting: false,
              edit: 'tienParam'
            },
            {
              fieldName: 'vat',
              textAlign: 'right',
              format: 'N',
              customAttributes: { class: 'vatlapdat' },
              headerText: 'Vat',
              allowFiltering: false,
              allowSorting: false
            },
            {
              fieldName: 'tyle_vat',
              textAlign: 'right',
              format: 'N',
              headerText: 'Tỷ lệ vat',
              allowFiltering: false,
              allowSorting: false
            }
          ],
          keycol: 'khoanmuctt_id',
          allowExit: false,
          tieude: 'Chi tiết tiền khoản mục',
          tieudebang: 'Chi tiết khoản mục thanh toán'
        }
      } else {
        await API.post_lay_khoanmuc_tt_theo_loaihd(this.axios, { vloaihd_id: LoaiHopDong.THAYDOI_THONGSO_MEGAWAN }).then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            ds = response.data.data
            if (this.ds_tien_khoanmuc && this.ds_tien_khoanmuc.length > 0) {
              for (var item of ds) {
                var items = this.ds_tien_khoanmuc.filter((x) => x.khoanmuctt_id == item.khoanmuctt_id)
                if (items && items.length > 0) {
                  item.tien = items[0].tien ? items[0].tien : 0
                  item.vat = items[0].vat ? items[0].vat : 0
                  item.tongtien = item.tien + item.vat
                }
              }
            }
          }
        })
        this.popupComponentData = {
          ds: ds,
          cols: [
            {
              fieldName: 'tentat_kmtt',
              headerText: 'Khoản mục',
              allowFiltering: false,
              allowSorting: false,
              isPrimaryKey: true
            },
            {
              fieldName: 'tongtien',
              textAlign: 'right',
              allowEditing: true,
              editType: 'numericedit',
              format: 'N',
              customAttributes: { class: 'tongtien' },
              headerText: 'Tổng tiền',
              allowFiltering: false,
              allowSorting: false,
              edit: 'tongtienParam'
            },
            {
              fieldName: 'tien',
              textAlign: 'right',
              allowEditing: true,
              editType: 'numericedit',
              format: 'N',
              customAttributes: { class: 'cuoclapdat' },
              headerText: 'Tiền',
              allowFiltering: false,
              allowSorting: false,
              edit: 'tienParam'
            },
            {
              fieldName: 'vat',
              textAlign: 'right',
              format: 'N',
              customAttributes: { class: 'vatlapdat' },
              headerText: 'Vat',
              allowFiltering: false,
              allowSorting: false
            }
          ],
          keycol: 'khoanmuctt_id',
          allowExit: false,
          tieude: 'Chi tiết tiền khoản mục',
          tieudebang: 'Chi tiết khoản mục thanh toán'
        }
      }
      this.popupComponentName = 'popupTienKM'
      this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupTienKM')
      this.Popup('popupComponentMGs')
    },
    async tsbtnXoa_Click() {
      this.$bvModal
        .msgBoxConfirm('Bạn thật sự muốn xóa hợp đồng khách hàng không ?', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (!v) {
            return
          }
          // Xử lý code ở đây
          try {
            this.Loading = true
            if (await this.XoaHDKH()) {
              await this.SetButton(0)
              this.DsThueBao.data = []
              // dgView.DataSource = null;
              this.lblTongS = 'Tổng số : 0 thuê bao'
            }
          } catch (err) {
            this.ShowError(`${err.message}`)
          } finally {
            this.Loading = false
          }
        })
    },
    async paymentcreated(val) {
      this.paymentloaded = true
      this.$refs.thanhtoanDialog.show(true)
    },
    tsbtnThanhToan_Click() {
      try {
        this.loading(true)
        this.tsbtnThanhToan_Enabled = false
        this.userComponentModule = thanhToan
        // goi su kien load lai ma giao dich
        this.txtMaGDTT = this.txtMaGD
        // tao component neu chua tao, show popup khi da goi xong ham khoi tao
        this.$refs.thanhToanPopUp.SetMaGd(this.txtMaGDTT)
        if (this.paymentloaded) this.$refs.thanhtoanDialog.show(true)
      } catch (err) {
        this.tsbtnThanhToan_Enabled = true
        console.log('tsbtnThanhToan_Click', err)
      } finally {
        this.tsbtnThanhToan_Enabled = true
        // this.loading(false)
      }
    },
    // async tsbtnThanhToan_Click() {
    //   try {
    //     // this.$refs.thanhToanPopUp.SetMaGd(this.txtMaGD)
    //   } catch (err) {
    //     console.log('tsbtnThanhToan_Click', err)
    //   } finally {
    //     this.$refs.thanhtoanDialog.show(true)
    //     this.$refs.thanhToanPopUp.SetMaGd(this.txtMaGD)
    //   }

    //   //  frmThanhToanHD f = new frmThanhToanHD(txtMaGD);
    //   //       f.MdiParent = this.MdiParent;
    //   //       f.Show();
    //   //       f.HienThiTTHopDongKH();
    //   // this.$router.push({
    //   //   path: 'Payment',
    //   //   query: { magd: this.txtMaGD }
    //   // })
    // },
    async tsbtnHen_Click() {
      //  if (hdkh_id != 0)
      //       {
      //           frmHenKH f = new frmHenKH();
      //           f.vhdkh_id = hdkh_id;
      //           f.Quyen_Duoc_hen = 1;
      //           f.ShowDialog();
      //       }
      if (this.hdkh_id > 0) {
        this.$refs.thongTinHenKhachHangModal.showModal()
      } else {
        this.ShowError('Chưa có hợp đồng khách hàng !')
      }
    },
    async tsbtnThemTB_Click_Old() {
      try {
        this.Loading = true
        await this.ThemHDThueBao()
      } catch (err) {
        this.Loading = false
      } finally {
        this.Loading = false
      }
    },
    async tsbtnThemTB_Click() {
      try {
        this.Loading = true
        await this.ThemHDThueBao()
      } catch (err) {
        this.Loading = false
      } finally {
        this.Loading = false
      }
    },
    async ThemHDThueBao() {
      try {
        if (!(await this.KiemTraDL_ThueBao())) return
        if (await this.kiemtra_hdtb_khi_themmoi(this.txtMaTB, this.hdkh_id, this.dichvuvt_id, 0)) {
          this.ShowError('Thuê bao đã tồn tại trong hợp đồng, không thêm mới được ')
          return false
        }
        if (!(await this.kiemtra_db_khi_themmoi(this.txtMaTB, this.khachhang_id, this.thuebao_id, this.dichvuvt_id))) {
          this.ShowError('Thuê bao không tồn tại trong danh bạ, không thêm mới được ')
          return false
        }

        //Hiếu thêm kiểm tra xem thue bao có thuộc khách hàng đang lập phụ lục ko 27/08/2010
        if (this.txtMaTB != '') {
          if (!(await this.kiemtra_tb_cung_makh_tb_pl(this.txtMaGD, this.thuebao_id))) {
            this.ShowError('Thuê bao này không cùng mã khách hàng đang thực hiện thay đổi thông số megawan')
            this.$refs.reftxtMaTB.focus()
            return false
          }
        }

        if (await this.kiemtra_thuebao_lap_hopdong_khac(this.txtMaTB, '0', parseInt(this.cboDichVuVT), LoaiHopDong.THAYDOI_THONGSO_MEGAWAN)) {
          return false
        }

        // let dset = await this.lay_huonggiao_tiepnhan(LoaiHopDong.THAYDOI_THONGSO_MEGAWAN, parseInt(this.cboLoaihinhTB), parseInt(this.cboTocDo), 0, parseInt(this.cboKieuLD))
        let dset = await this.lay_huonggiao_tiepnhan_v2(LoaiHopDong.THAYDOI_THONGSO_MEGAWAN, parseInt(this.cboLoaihinhTB), parseInt(this.cboTocDo), 0, parseInt(this.cboKieuLD),parseInt(this.cboLoaiKenh),parseInt(this.cboDichVuVT))
        this.huonggiao_tn = 0
        this.quytrinh_id = 0
        if (dset == null || dset.length==0  || dset[0]['huonggiao_id'] == null) {
          this.ShowError('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
          return
        }
        this.huonggiao_tn = parseInt(dset['huonggiao_id'])
        this.quytrinh_id = parseInt(dset['quytrinh_id'])

        await this.TaoDuLieu_HDTB(true)
        await this.TaoDuLieu_HDTB_MGWAN(true)
        await this.TaoDuLieu_THONGTIN_TT(true)
        await this.TaoDuLieu_CT_TIENHD(true)
        await this.TaoDuLieu_HDTBDV(true)
        await this.TaoDuLieu_HDBANCHEO(true)
        //thaydoits.ThemMoi_HopDongTB(dsHDTB, dsHDTB_MEWAN, dsTTTT, dsCTTHD, dsHDTBDV, dsKM_HDTB, huonggiao_tn);
        let result= await this.ThemMoi_HopDongTB()
        if (result != null && result.toUpperCase() == 'OK') {
          this.ShowSuccess('Thêm thuê bao thành công!')
        }
        else
        {
          this.ShowError(result)
        }
        // call api
        // this.ShowSuccess('Thêm thuê bao thành công!')
        let dsHdTB = await this.lay_ds_hopdong_thuebao_theo_hdkh(this.hdkh_id)
        await this.HienThiDanhSacHDTB(dsHdTB)
      } catch (err) {
        this.ShowError('Lỗi thuê bao !')
        //new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
      }
    },
    async tsbtnXoaTB_Click() {
      this.$bvModal
        .msgBoxConfirm('Bạn thật sự muốn xóa thuê bao không ?', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (!v) {
            return
          }
          this.Loading = true
          try {
            await this.XoaHDTB()
            this.Loading = false
          } catch (err) {
            console.log(`${err.message}`)
          } finally {
            this.Loading = false
          }
          // Xử lý code ở đây

          // dgView.DataSource = null;
          this.lblTongS = 'Tổng số : 0 thuê bao'
        })
    },
    async XoaHDTB() {
      try {
        await this.xoa_hdtb(this.hdtb_id)
        let data = await this.lay_ds_hopdong_thuebao_theo_hdkh(this.hdkh_id)
        await this.HienThiDanhSacHDTB(data)
        this.ShowSuccess('Xóa thông tin thuê bao khỏi hợp đồng thành công!')
        return true
        //HienThiDanhSacHDTB(tchopdong.LAY_DS_HDTB_THAYDOI_TOCDO(hdkh_id));//Hiếu 23.10.2010: Ko sử dụng hàm này nữa
      } catch (err) {
        this.ShowError('Lỗi xóa hợp đồng thuê bao !')
        console.log(err)
        return false
      }
    },
    async XoaHDKH() {
      try {
        let data = this.xoa_hdkh_v2(this.hdkh_id, LoaiHopDong.THAYDOI_THONGSO_MEGAWAN)
        this.ShowSuccess('Xóa hợp đồng thành công !')
        return true
      } catch (err) {
        this.ShowError('Lỗi xóa hợp đồng !')
        return false
      }
    },
    async txtMaGD_KeyPress() {
      try {
        console.log('txtMaGD_KeyPress')
        this.Loading = true
        let result = await this.HienThiTTHopDongKH(null)
        if (!result) {
          this.ShowError(`Không tìm thấy thông tin hợp đồng với mã giao dịch: ${this.txtMaGD}`)
          this.Loading = false
          return
        }
      } catch (err) {
        // this.ShowError(`txtMaGD_KeyPress:${err}`)
        console.log('txtMaGD_KeyPress', err)
      } finally {
        this.Loading = false
      }
    },
    async closeChonTinh(data) {
      console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 1806 ~ closeChonTinh ~ data', data)
      try {
        this.Loading = true
        this.tinh = data
        this.chonTinh = false
        await this.txtMaTB_KeyPress_Step2()
      } catch (err) {
        console.error('closeChonTinh-err', err)
      } finally {
        this.Loading = false
      }
    },
    async txtMaTB_KeyPress_Step2() {
      let ds = []
      if (this.tinh.tinhthicong_id > 0 && this.kenh_trang == false) {
        ds = await this.TRACUU_LAY_DS_HOPDONG_THEO_MA_TB()
      } else {
        ds = await this.LAY_DS_HOPDONG_THEO_MA_TB()
      }
      console.log('txtMaTB_KeyPress_Step2:ds', ds)
      if (ds != null && ds.length > 0) {
        console.log('txtMaTB_KeyPress_Step2:HienThiTTHopDongKH')
        await this.HienThiTTHopDongKH(ds)
      } else {
        console.log('txtMaTB_KeyPress_Step2:HienThiTT_DanhBa')
        await this.HienThiTT_DanhBa(this.txtMaTB)
      }
      if (((ds != null && ds.length > 0) || this.found) && this.tinh != null && this.tinh.tinhthicong_id > 0) {
        await this.HienThiChonThongSo()
      }
    },
    async txtMaTB_KeyPress() {
      try {
        if (this.txtMaTB != '') {
          this.Loading = true
          this.tinh = await this.lay_tinh_theo_matb(this.txtMaTB)
          if (this.tinh.tinhthicong_id == 0) {
            this.Loading = false
            this.chi_giao_net = false
            this.kenh_trang = true
            this.$bvModal
              .msgBoxConfirm('Thuê bao không có trong danh bạ bán chéo. Bạn phải chọn tỉnh thi công !?', {
                title: '',
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
                headerClass: 'boxConfirm'
              })
              .then(async (v) => {
                if (!v) {
                  this.Clear()
                  return
                }
                // Show chon tinh
                await this.$refs.ref_frmChonTinh.showDialog()
                return
              })
          } else {
            this.kenh_trang = false
            let dtb = await this.lay_thuebao_kn_id_theo_matb()
            if (dtb != null && dtb.length > 0 && dtb[0].thuebao_kn_id == -1) {
              this.chi_giao_net = true
            } else {
              this.chi_giao_net = false
            }
            await this.txtMaTB_KeyPress_Step2()
          }
        }
      } catch (err) {
        console.log('txtMaTB_KeyPress', err)
      } finally {
        this.Loading = false
      }
    },
    async tsbtnNhapMoi_Click() {
      try {
        this.Loading = true
        await this.NhapMoi()
      } catch (err) {
      } finally {
        this.Loading = false
      }
    },
    async tsbtnHuyBo_Click() {
      await this.SetButton(0)
    },
    async NhapMoi() {
      await this.SetButton(1)
    },
    async SetButton(kieu) {
      console.log('SetButton:kieu', kieu)
      try {
        this.tsbtnNhapMoi_Enabled = false
        this.tsbtnGhiLai_Enabled = false
        this.tsbtnXoa_Enabled = false
        this.tsbtnHuyBo_Enabled = false
        this.tsbtnThemTB_Enabled = false
        this.tsbtnXoaTB_Enabled = false
        //tsbtnInhopdong_Enabled = false;
        if (kieu == -1) {
          //Bat dau
          try {
            this.$refs.reftxtMaTB.focus()
          } catch (err) {}

          this.tsbtnGhiLai_Enabled = true
          this.tsbtnHuyBo_Enabled = true
          this.chkCTV = false
          this.chkNguoiGT = false
          this.txtNguoiGT = ''
          this.txtCTV = ''
        }

        if (kieu == 0) {
          //Bat dau
          try {
            this.$refs.reftxtMaTB.focus()
          } catch (err) {}
          this.tsbtnNhapMoi_Enabled = true
          await this.Clear()
        }

        if (kieu == 1) {
          //Them moi
          this.tsbtnGhiLai_Enabled = true
          this.tsbtnHuyBo_Enabled = true
          this.chkCTV = false
          this.chkNguoiGT = false
          this.txtNguoiGT = ''
          this.txtCTV = ''
          await this.Clear()
        }

        if (kieu == 2) {
          //Huy
          try {
            this.$refs.reftxtMaGD.focus()
          } catch (err) {}

          this.tsbtnNhapMoi_Enabled = true
          this.tsbtnXoa_Enabled = true
          await this.Clear()
        }

        if (kieu == 3) {
          //Edit
          try {
            this.$refs.reftxtMaGD.focus()
          } catch (err) {}
          this.tsbtnNhapMoi_Enabled = true
          this.tsbtnXoa_Enabled = true
          this.tsbtnGhiLai_Enabled = true
          this.tsbtnHuyBo_Enabled = true
          this.tsbtnThemTB_Enabled = true
          this.tsbtnXoaTB_Enabled = true
          //tsbtnInhopdong_Enabled = true;
        }
      } catch (err) {
        console.log('SetButton:err', err)
      }
    },
    async Clear() {
      try {
        this.kenh_trang = true
        this.chi_giao_net = true

        try {
          this.thongsoBan = this.Reset_Thongso_Thuebao()
        } catch (err1) {
          console.log('Clear:Reset_Thongso_Thuebao:thongsoBan', err1)
        }
        try {
          this.thongsoTc = this.Reset_Thongso_Thuebao()
        } catch (err2) {
          console.log('Clear:Reset_Thongso_Thuebao:thongsoTc', err1)
        }

        this.txtWanIP_Cu = ''
        this.textBox2 = ''
        this.txtWanIP = ''
        this.txtLanIP = ''
        this.txtCuoc_ISP = '0'
        this.txtCuoc_NIX = '0'
        this.txtSoLuong_IP = '0'
        this.txtCuoc_IP = '0'

        this.txtCuoc_ISP_Cu = '0'
        this.txtCuoc_NIX_Cu = '0'
        this.txtSoLuong_IP_Cu = '0'
        this.txtCuoc_IP_Cu = '0'

        this.chkTocDo_ISP = false
        this.chkTocDo_ISP_Cu = false
        this.chkTocDo_NIX = false
        this.chkTocDo_NIX_Cu = false

        this.lblTongS = 'Tổng số : 0 thuê bao'
        this.txtCongVan = ''
        this.chkCongVan = false
        this.vsoao = ''
        this.txtDiaChi_KN = ''
        this.txtSiteID = ''
        this.txtThoiHanCK = ''
        this.txtMaHD_TTKD = ''

        this.doituong_id = 1 //Default: tư nhân
        this.txtSoAo = ''
        this.vhuongkn = ''
        this.txtGhiChu = ''
        this.trangbi_id = 0
        try {
          this.$refs.reftxtMaTB.focus()
        } catch (err) {}
        this.chkDoiTac = false
        this.chkDoiTac_Cu = false
        this.txtCuocTK_Cu = '0'
        this.txtCuocTC_Cu = '0'
        this.txtCuocHT_Cu = '0'
        this.txtCuocTBI_Cu = '0'
        this.txtCuocTKDT_Cu = '0'
        this.txtCuocTCDT_Cu = '0'
        this.txtMaHD_DT_Cu = ''

        this.txtCuocTK = '0'
        this.txtCuocTC = '0'
        this.txtCuocHT = '0'
        this.txtCuocTBI = '0'
        this.txtCuocTKDT = '0'
        this.txtCuocTCDT = '0'
        this.txtMaHD_DT = ''

        this.hdkh_id = 0
        this.thuebao_id_cha = 0
        this.txtMaGD = ''
        this.txtMaHD = ''
        this.txtMaKH = ''
        this.khachhang_id = 0
        this.txtTenKH = ''
        this.txtDiaChiKH = ''
        this.txtHuongKn = ''
        this.txtHuongKnCu = ''

        this.txtMaDuAn = ''
        this.dtpNgayLapHD = moment().format('DD/MM/YYYY')
        this.dtpNgayYeuCau = moment().format('DD/MM/YYYY')

        this.cboChuQuanMEGAWAN = this.ChuQuanMEGAWAN[0].id
        this.cboLoaiKenh = this.LoaiKenh[0].id
        this.cboTocDo = this.TocDo[0].id
        this.cboTocDo_Pir = this.TocDo[0].id
        this.cboLoaiTBiWan = this.LoaiTBiWan[0].id
        this.cboLoaiModem = this.LoaiModem[0].id
        this.cboTrangBiMEGAWAN = this.TrangBiMEGAWAN[0].id
        this.cboThoiHanMEGAWAN = this.ThoiHanMEGAWAN[0].id
        this.cboVeTinh = this.VeTinh[0].id

        this.hdtb_id = 0
        this.thuebao_id = 0
        this.txtTenTB = ''
        this.diachi_tb = ''
        this.diachi_ld = ''
        this.txtDiaChiLD = ''
        this.port_id = 0
        //dgView.DataSource = null;
        this.txtGhiChu = ''
        this.txtTongTienHD = '0'

        this.cboChuQuanMEGAWANCu = this.ChuQuanMEGAWAN[0].id
        this.cboLoaiKenhCu = this.LoaiKenh[0].id
        this.cboTocDoCu = this.TocDo[0].id
        this.cboLoaiTBiWanCu = this.LoaiTBiWan[0].id
        this.cboLoaiModemCu = this.LoaiModem[0].id
        this.cboTrangBiMEGAWANCu = this.TrangBiMEGAWAN[0].id
        this.cboThoiHanMEGAWANCu = this.ThoiHanMEGAWAN[0].id
        this.cboVeTinhCu = this.VeTinh[0].id
        this.phuongld_id = 0
        this.phold_id = 0
        this.apld_id = 0
        this.khuld_id = 0
        //txtTienHD= "0";
        //txtVatHD= "0";

        this.dtienld = 0
        this.dvatld = 0
        this.DsThueBao.data = []
        this.txtNguoiGT = ''
        this.txtCTV = ''
        this.nguoigt_id = 0
        this.ctv_id = 0
        this.txtMaBSS = ''
        this.txtMaERP = ''
        await this.LayTienTheoKhoanMuc()
      } catch (errClear) {
        console.log('Clear:err', errClear)
      }
    },
    async LayTienTheoKhoanMuc() {
      try {
        //Hiếu TC bổ sung hàm lấy tiền lên ô text box ngày 14/09/2010
        if (this.dichvuvt_id != DichVuVienThong.ADSL && this.dichvuvt_id != DichVuVienThong.MEGAWAN) {
          this.kh_cd = 0
          this.ltb_adsl_id = 1
        }
        if (this.ltb_adsl_cu_id == 0 || this.ltb_adsl_moi_id == 0 || this.tocdo_cu_id == 0 || this.tocdo_moi_id == 0) {
          return
        }
        let ds = await this.lay_tien_khoanmuc_ld(
          LoaiHopDong.THAYDOI_THONGSO_MEGAWAN,
          this.dtpNgayYeuCau,
          this.cboDichVuVT,
          this.cboLoaihinhTB,
          this.cboKieuLD,
          this.phuongld_id,
          this.tocdo_moi_id,
          0,
          this.ltb_adsl_id,
          this.ltb_adsl_moi_id,
          this.ltb_adsl_cu_id,
          this.tocdo_moi_id,
          this.tocdo_cu_id,
          0,
          this.phold_id,
          this.apld_id,
          this.khuld_id,
          this.doituong_id,
          this.cboTrangBiMEGAWAN
        )
        if (ds != null && ds.length > 0) {
          await this.HienThiTienLapDat(ds)
        }
      } catch (err) {
        console.log(err)
      }
    },
    async HienThiTienLapDat(ds) {
      let kt_lm = true
      if (ds[0][0] != '-1') {
        for (let i = 0; i < ds.length; i++) {
          if (ds[i]['khoanmuctt_id'] == this.KHOANMUC_TT.KMTT_DOITS) {
            //txtTienHD = parseFloat(ds[0]["tien"]).ToString(Format.FM_TIEN_VND);
            //txtVatHD = parseFloat(ds[0]["vat"]).ToString(Format.FM_TIEN_VND);
            this.dtienld = parseFloat(ds[i]['tien'])
            this.dvatld = parseFloat(ds[i]['vat'])
            this.kt_lm = false
          }
        }
        if (this.kt_lm == true) {
          //txtTienHD = "0";
          //txtVatHD = "0";
          this.dtienld = 0
          this.dvatld = 0
        }
      } else {
        //txtTienHD = "0";
        //txtVatHD = "0";
        //this.ShowError("Thông tin giá chưa được cập nhật");
        this.dtienld = 0
        this.dvatld = 0
      }
      for (let j = 0; j < this.ds_tien_khoanmuc.length; j++) {
        if (this.ds_tien_khoanmuc[j]['khoanmuctt_id'] == this.KHOANMUC_TT.KMTT_DOITS) {
          this.ds_tien_khoanmuc[j]['tien'] = dtienld
          this.ds_tien_khoanmuc[j]['vat'] = dvatld
        }
      }
    },
    async Reset_Thongso_Thuebao() {
      let ketqua = {
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
        ma_tn: 0,
        ma_doicap: 0,

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

        mtu_size: 0,
        auto_nego: 0,
        sncp: 0,
        cap_chinh: 0,
        cap_duphong: 0,
        diem_pop: 0,
        wan_ip: 0,
        lan_ip: 0,
        ce_ip: 0,
        tt_ip: 0,
        sl_ip: 0,
        ten_trunk_nni: 0,
        so_as: 0,
        hqos: 0,
        cos: 0,
        svlan: 0,
        cvlan: 0,
        vlan_transparent: 0,
        tt_port: 0,
        loai_ip: [],
        kieu_nghiemthu: [],
        thongso_cntt: []
      }
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
    async HienThiTT_DanhBa(ma_tb) {
      console.log('HienThiTT_DanhBa:', ma_tb)
      console.log('HienThiTT_DanhBaTinh:', this.tinh)
      this.found = false
      let ds = []
      if (this.tinh.tinhthicong_id > 0 && this.kenh_trang == false) {
        console.log('lay_danhba_theo_matb_bc', this.tinh.tinhthicong_id)
        // ds = await this.lay_danhba_theo_matb_bc(ma_tb, this.dichvuvt_id, 0)
        ds = await this.lay_danhba_theo_matb_bc()
      } else {
        console.log('lay_danhba_theo_matb')
        ds = await this.lay_danhba_theo_matb(ma_tb, this.dichvuvt_id, 0)
      }
      if (ds != null && ds.length > 0) {
        if (await this.kiemtra_thuebao_lap_hopdong_khac(ma_tb, '0', parseInt(this.cboDichVuVT), LoaiHopDong.THAYDOI_THONGSO_MEGAWAN)) return
        if (parseInt(ds[0]['ky_hd']) == 0) {
          this.ShowError('Khách hàng có Account: ' + this.txtMaTB + ' chưa ký lại hợp đồng gốc. Đề nghị ký lại hợp đồng gốc')
          return
        }
        // Nếu trạng thái thuê bao # " Hoạt động bình thường" ---> không cho phép làm tiếp
        if (parseInt(ds[0]['trangthaitb_id']) != TrangThaiTB.THUONG) {
          this.ShowError('Thuê bao có mã: ' + ds[0]['ma_tb'] + ' đang ở trạng thái: ' + ds[0]['trangthai_tb'] + '. Bạn không thể thực hiện thay đổi thông số cho thuê bao này. ')
          return
        }
        this.found = true
        //Thông tin khách hàng
        this.txtMaKH = ds[0]['ma_kh']
        this.txtMaHD = ds[0]['ma_hd']
        this.khachhang_id = parseInt(ds[0]['khachhang_id'])
        this.txtTenKH = ds[0]['ten_kh']
        this.txtDiaChiKH = ds[0]['diachi_kh']
        this.txtThoiHanCK = parseInt(ds[0]['sothang_ck'])

        if (ds[0]['ngaylap_hd'] != null && ds[0]['ngaylap_hd'] != '') {
          this.dtpNgayLapHD = moment(ds[0]['ngaylap_hd'], 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
        } else {
          this.dtpNgayLapHD = moment().format('DD/MM/YYYY')
        }

        //Thông tin thuê bao
        this.thuebao_id = parseInt(ds[0]['thuebao_id'])
        if (this.tinh.tinhthicong_id > 0 && this.kenh_trang == false && this.chi_giao_net == false) {
          this.thuebao_id_tc = await this.lay_thuebaoid_thicong(this.tinh.tinhthicong_id, this.thuebao_id)
        } else if (this.chi_giao_net == true) this.thuebao_id_tc = -1
        else this.thuebao_id_tc = 0

        this.txtTenTB = ds[0]['ten_tb']
        this.diachi_tb = ds[0]['diachi_tb']
        this.txtDiaChiTB = this.diachi_tb
        this.diachi_ld = ds[0]['diachi_ld']
        this.txtDiaChiLD = this.diachi_ld
        this.loaitb_id = parseInt(ds[0]['loaitb_id'])
        this.cboLoaihinhTB = this.loaitb_id
        await this.cboLoaihinhTB_SelectedValueChanged(this.loaitb_id)
        this.doituong_id = parseInt(ds[0]['doituong_id'])
        this.donvi_id = parseInt(ds[0]['donviql_id'])
        let diachi = await this.lay_ds_diachi_theo_dbtbid(this.thuebao_id)
        try {
          await this.HienThiTTDiaChi(diachi, 3)
        } catch (err) {}

        //vhuongkn
        if (ds[0]['thuebao_cha_id'] != null && ds[0]['thuebao_cha_id'] != '') {
          this.thuebao_id_cha = ds[0]['thuebao_cha_id']
          this.txtHuongKn = await this.fn_lay_text_huongketnoi(this.thuebao_id_cha, 1)
          this.txtHuongKnCu = this.txtHuongKn

          this.vhuongkn = await this.maptb_cha(this.thuebao_id_cha)
        } else {
          this.thuebao_id_cha = 0
          this.txtHuongKn = ''
          this.txtHuongKnCu = ''
          this.vhuongkn = ''
        }

        ds = await this.lay_ds_db_megawan_theo_tbid(this.thuebao_id)
        if (ds != null && ds.length > 0) {
          if (this.loaitb_id == LoaiHinhTB.MGW_INTERNET_TRUCTIEP||this.loaitb_id==LoaiHinhTB.NB_INTERNETTT) {
            if (ds[0]['phantach_td'] != null && ds[0]['phantach_td'] != '') {
              this.cboPhanTachTD = parseInt(ds[0]['phantach_td'])
              this.cboPhanTachTDCu = parseInt(ds[0]['phantach_td'])
            }
            if (ds[0]['phanloai_id'] != null && ds[0]['phanloai_id'] != '') {
              this.cboPhanLoaiMGW = parseInt(ds[0]['phanloai_id'])
              this.cboPhanLoaiMGWCu = parseInt(ds[0]['phanloai_id'])
            }
            this.cboPhanTachTD_Enabled = true
            this.cboPhanLoaiMGW_Enabled = true
          }

          //Thong tin moi
          //txtHuongKn = ds[0]["huong_kn"];
          this.txtSoAo = ds[0]['ma_lt']
          this.vsoao = ds[0]['ma_lt']
          this.cboTocDo = parseInt(ds[0]['tocdo_id'])
          if (ds[0]['congnghe_id'] != null && ds[0]['congnghe_id'] != '') {
            this.cboCongNghe_Cu = parseInt(ds[0]['congnghe_id'])
          } else {
            this.cboCongNghe_Cu = ''
          }

          if (ds[0]['congnghe_id'] != null && ds[0]['congnghe_id'] != '') this.cboCongNghe_Moi = parseInt(ds[0]['congnghe_id'])

          this.txtWanIP_Cu = ds[0]['wan_ip']
          this.textBox2 = ds[0]['lan_ip']
          this.textBox1 = ds[0]['diachi_kn']
          if (ds[0]['tocdo_pir_id'] != null && ds[0]['tocdo_pir_id'] != '') {
            this.chkTocDo_PIR = true
            this.chkTocDo_PIR_Cu = true
            this.cboTocDo_Pir = parseInt(ds[0]['tocdo_pir_id'])
            this.cboTocDo_Pir_Cu = parseInt(ds[0]['tocdo_pir_id'])
          } else {
            this.chkTocDo_PIR = false
            this.chkTocDo_PIR_Cu = false
            this.cboTocDo_Pir = parseInt(ds[0]['tocdo_id'])
            this.cboTocDo_Pir_Cu = parseInt(ds[0]['tocdo_id'])
          }

          if (ds[0]['tocdo_nix'] != null && ds[0]['tocdo_nix'] != '') {
            this.chkTocDo_NIX = true
            this.chkTocDo_NIX_Cu = true
            this.cboTocDo_NIX_Cu = parseInt(ds[0]['tocdo_nix'])
            this.cboTocDo_NIX = parseInt(ds[0]['tocdo_nix'])

            if (this.chkTocDo_NIX_Enabled == false) {
              this.cboTocDo_NIX_Enabled = false
            } else {
              this.cboTocDo_NIX_Enabled = true
            }
          } else {
            this.chkTocDo_NIX = false
            this.chkTocDo_NIX_Cu = false
          }

          if (ds[0]['tocdo_isp'] != null && ds[0]['tocdo_isp'] != '') {
            this.chkTocDo_ISP = true
            this.chkTocDo_ISP_Cu = true
            this.cboTocDo_ISP_Cu = parseInt(ds[0]['tocdo_isp'])
            this.cboTocDo_ISP = parseInt(ds[0]['tocdo_isp'])

            if (this.chkTocDo_ISP_Enabled == false) {
              this.cboTocDo_ISP_Enabled = false
            } else {
              this.cboTocDo_ISP_Enabled = true
            }
          } else {
            this.chkTocDo_ISP = false
            this.chkTocDo_ISP_Cu = false
          }

          if (ds[0]['cuoc_nix'] != null && ds[0]['cuoc_nix'] != '') {
            this.txtCuoc_NIX = parseFloat(ds[0]['cuoc_nix'])
            this.txtCuoc_NIX_Cu = parseFloat(ds[0]['cuoc_nix'])
          } else {
            this.txtCuoc_NIX = '0'
            this.txtCuoc_NIX_Cu = '0'
          }

          if (ds[0]['cuoc_isp'] != null && ds[0]['cuoc_isp'] != '') {
            this.txtCuoc_ISP = parseFloat(ds[0]['cuoc_isp'])
            this.txtCuoc_ISP_Cu = parseFloat(ds[0]['cuoc_isp'])
          } else {
            this.txtCuoc_ISP = '0'
            this.txtCuoc_ISP_Cu = '0'
          }

          if (ds[0]['sl_ip'] != null && ds[0]['sl_ip'] != '') {
            this.txtSoLuong_IP = ds[0]['sl_ip']
            this.txtSoLuong_IP_Cu = ds[0]['sl_ip']
          } else {
            this.txtSoLuong_IP = '0'
            this.txtSoLuong_IP_Cu = '0'
          }

          if (ds[0]['cuoc_ip'] != null && ds[0]['cuoc_ip'] != '') {
            this.txtCuoc_IP = parseFloat(ds[0]['cuoc_ip'])
            this.txtCuoc_IP_Cu = parseFloat(ds[0]['cuoc_ip'])
          } else {
            this.txtCuoc_IP = '0'
            this.txtCuoc_IP_Cu = '0'
          }

          if (ds[0]['tinh_kn_id'] != null && ds[0]['tinh_kn_id'] != '') {
            this.cboTinh_KN_Cu = parseInt(ds[0]['tinh_kn_id'])
            this.cboTinh_KN_Moi = parseInt(ds[0]['tinh_kn_id'])
          } else {
            this.cboTinh_KN_Cu = 0
            this.cboTinh_KN_Moi = 0
          }

          this.cboLoaiModem = parseInt(ds[0]['loaimd_id'])
          this.cboLoaiKenh = parseInt(ds[0]['loaikenh_id'])
          //txtMaHD_TTKD = ds[0]["ma_pl"];

          this.chkCongVan = false
          this.txtCongVan = ''
          this.dtpNgayCV = moment().format('DD/MM/YYYY')

          this.txtSiteID = ds[0]['site_id']

          if (ds[0]['thietbidc_id'] != null && ds[0]['thietbidc_id'] != '') this.cboLoaiTBiWan = parseInt(ds[0]['thietbidc_id'])

          //Thông tin thuê bao cũ
          this.cboTocDoCu = parseInt(ds[0]['tocdo_id'])
          this.cboLoaiModemCu = parseInt(ds[0]['loaimd_id'])
          this.cboLoaiKenhCu = parseInt(ds[0]['loaikenh_id'])
          this.cboLoaiTBiWanCu = parseInt(ds[0]['thietbidc_id'])

          if (ds[0]['doitac_id'] != null && ds[0]['doitac_id'] != '') {
            let doitac_id = ds[0]['doitac_id']
            this.cboDoiTac_Cu = doitac_id
            this.cboDoiTac = doitac_id
            this.txtMaHD_DT_Cu = ds[0]['mahd_dt']
            this.txtMaHD_DT = ds[0]['mahd_dt']
            this.txtCuocTKDT_Cu = parseFloat(ds[0]['cuoc_tkdt'])
            this.txtCuocTKDT = parseFloat(ds[0]['cuoc_tkdt'])
            this.txtCuocTCDT_Cu = parseFloat(ds[0]['cuoc_tcdt'])
            this.txtCuocTCDT = parseFloat(ds[0]['cuoc_tcdt'])
            this.chkDoiTac_Cu = true
            this.chkDoiTac = true

            //KhaiVK: 12/06/2018
            //let str_doitac = checkdata.MAP_ID('ten_dt', DatabaseConstants.DB2 + '.doitac', 'where doitac_id = ' + doitac_id)
            let str_doitac = await this.map_doitac(doitac_id)
            this.ShowError('Thuê bao này có  Hợp đồng/PLHĐ với đối tác ' + str_doitac)
          } else {
            this.chkDoiTac_Cu = false
            this.txtCuocTKDT_Cu = '0'
            this.txtCuocTCDT_Cu = '0'
            this.txtMaHD_DT_Cu = ''

            this.chkDoiTac = false
            this.txtCuocTKDT = '0'
            this.txtCuocTCDT = '0'
            this.txtMaHD_DT = ''
          }
          if (ds[0]['cuoc_tk'] != null && ds[0]['cuoc_tk'] != '') {
            this.txtCuocTK_Cu = parseFloat(ds[0]['cuoc_tk'])
            this.txtCuocTK = parseFloat(ds[0]['cuoc_tk'])
          } else {
            this.txtCuocTK_Cu = '0'
            this.txtCuocTK = '0'
          }

          if (ds[0]['cuoc_tc'] != null && ds[0]['cuoc_tc'] != '') {
            this.txtCuocTC_Cu = parseFloat(ds[0]['cuoc_tc'])
            this.txtCuocTC = parseFloat(ds[0]['cuoc_tc'])
          } else {
            this.txtCuocTC_Cu = '0'
            this.txtCuocTC = '0'
          }

          if (ds[0]['cuoc_ht'] != null && ds[0]['cuoc_ht'] != '') {
            this.txtCuocHT_Cu = parseFloat(ds[0]['cuoc_ht'])
            this.txtCuocHT = parseFloat(ds[0]['cuoc_ht'])
          } else {
            this.txtCuocHT_Cu = '0'
            this.txtCuocHT = '0'
          }

          if (ds[0]['cuoc_tbi'] != null && ds[0]['cuoc_tbi'] != '') {
            this.txtCuocTBI_Cu = parseFloat(ds[0]['cuoc_tbi'])
            this.txtCuocTBI = parseFloat(ds[0]['cuoc_tbi'])
          } else {
            this.txtCuocTBI_Cu = '0'
            this.txtCuocTBI = '0'
          }

          //cboVeTinhCu = vetinh_id;
          this.cboChuQuanMEGAWANCu = parseInt(ds[0]['chuquan_id'])
          this.cboTrangBiMEGAWANCu = parseInt(ds[0]['trangbi_id'])
          this.cboThoiHanMEGAWANCu = parseInt(ds[0]['thoihan_id'])
          //txtHuongKnCu = ds[0]["huong_kn"];
          if (ds[0]['cuoc_tc'] != null && ds[0]['cuoc_tc'] != '') {
            this.txtCuocTC_Cu = parseFloat(ds[0]['cuoc_tc'])
          } else {
            this.txtCuocTC_Cu = '0'
          }

          if (ds[0]['cuoc_tk'] != null && ds[0]['cuoc_tk'] != '') {
            this.txtCuocTK_Cu = parseFloat(ds[0]['cuoc_tk'])
          } else {
            this.txtCuocTK_Cu = '0'
          }

          //Thông tin thuê bao mới, mặc định = thông tin thuê bao cũ
          //Thông tin thuê bao cũ
          this.cboTocDo = parseInt(ds[0]['tocdo_id'])
          this.cboLoaiModem = parseInt(ds[0]['loaimd_id'])
          this.cboLoaiKenh = parseInt(ds[0]['loaikenh_id'])
          this.cboLoaiTBiWan = parseInt(ds[0]['thietbidc_id'])
          this.cboChuQuanMEGAWAN = parseInt(ds[0]['chuquan_id'])
          this.cboTrangBiMEGAWAN = parseInt(ds[0]['trangbi_id'])
          this.cboThoiHanMEGAWAN = parseInt(ds[0]['thoihan_id'])
          /*
          this.txtWanIP = ds[0]['wan_ip']
          this.txtWanIP_Cu = ds[0]['wan_ip']
          this.txtLanIP = ds[0]['lan_ip']
          this.textBox2 = ds[0]['lan_ip']

          this.txtMaBSS = ds[0]['ma_bss']
          this.txtMaERP = ds[0]['id_erp']
          */
          await this.Gan_thongso_bancheo_thuebaoid(this.thuebao_id, this.thuebao_id_tc, this.tinh.tinhthicong_id)
        }
        //frmTraCuuCTDanhBa frmTCCTDB = new frmTraCuuCTDanhBa(dichvuvt_id, txtMaTB);
        //frmTCCTDB.ShowDialog();
      } else {
        this.thuebao_id = 0
        await this.Clear()
        this.ShowError(`Không tìm thấy thông tin về mã thuê bao ${this.txtMaTB} `)
      }
    },
    NotNull(val) {
      if (val != null && val != '') {
        return true
      }
      return false
    },
    async HienThiTTHopDongKH(data) {
      if (data == null) data = await this.lay_ds_hopdong_theo_ma_gd()
      //let data =
      if (data != null && data.length > 0) {
        this.hdkh_id = parseInt(data[0]['hdkh_id'])
        this.txtMaGD = data[0]['ma_gd']
        this.txtMaHD = data[0]['ma_hd']
        this.txtMaKH = data[0]['ma_kh']
        if (data[0].khachhang_id != null && data[0]['khachhang_id'] != '') this.khachhang_id = parseInt(data[0]['khachhang_id'])
        this.txtTenKH = data[0]['ten_kh']
        this.txtDiaChiKH = data[0]['diachi_kh']
        this.dtpNgayLapHD = moment(data[0]['ngaylap_hd'], 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
        this.dtpNgayYeuCau = moment(data[0]['ngay_yc'], 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY') //Hiếu bổ sung thêm ngày yc 20.09.2010
        //luan
        if (data[0]['ctv_id'] != null && data[0]['ctv_id'] != '') {
          this.chkCTV = true
          this.ctv_id = parseInt(data[0]['ctv_id'])
          this.txtCTV = await this.mapnhanvien(this.ctv_id)
        } else {
          this.txtCTV = ''
          this.ctv_id=0
          this.chkCTV = false
        }
        if(data[0]['nhanviengt_id'] != null && data[0]['nhanviengt_id'] != ''){
          this.nguoigt_id=parseInt(data[0]['nhanviengt_id'])
          this.chkNguoiGT=true
          this.txtNguoiGT=await this.mapnhanvien(this.nguoigt_id)
          this.txtNguoiGTId=parseInt(data[0]['nhanviengt_id'])
        }else{
          this.txtNguoiGT=''
          this.nguoigt_id=0
          this.chkNguoiGT=false
        }
        this.txtMaDuAn = data[0]['ma_duan']
        let dataKh = await this.lay_ds_hopdong_tb_theo_hdkh_id_bc(this.hdkh_id)
        await this.HienThiTTHopDongTB(dataKh)

        await this.HienThiDanhSacHDTB(dataKh)
        await this.SetButton(3)
        if (this.DsThueBao.data != null && this.DsThueBao.data.length > 1) this.tsbtnXoaTB_Enabled = true
        else this.tsbtnXoaTB_Enabled = false
        return true
      } else {
        await this.SetButton(0)
        return false
      }
      return false
    },
    async Gan_thongso_bancheo_hdtb(hdtb_id) {
      try {
        console.log('Gan_thongso_bancheo_hdtb:hdtb_id', hdtb_id)
        let ds = await this.LAY_THONGSO_THUEBAO_BANCHEO_HDTBID(hdtb_id)
        if (ds == null || ds.length == 0) {
          console.log('Gan_thongso_bancheo_hdtb ko thay ket qua:', ds)
          return
        }
        let drTHONGSO = ds[0]
        console.log('drTHONGSO', drTHONGSO)
        if (ds != null && ds.length > 0) {
          this.UpperObjectKeys(drTHONGSO)
          console.log('drTHONGSO', drTHONGSO)
          let dvvt_id = parseInt(ds[0]['DICHVUVT_ID'])
          if (dvvt_id != DichVuVienThong.HOINGHI_TRUYENHINH && dvvt_id != DichVuVienThong.ANTOAN_BAOMAT_TT && dvvt_id != DichVuVienThong.DICHVU_CNTT && dvvt_id != DichVuVienThong.TRUNGTAM_DULIEU) {
            this.thongsoBan.chuquanld_id = drTHONGSO.CHUQUANLD_BAN
            this.thongsoBan.congnghe_id = drTHONGSO.CONGNGHE_BAN
            this.thongsoBan.loaikenh_id = drTHONGSO.LOAIKENH_BAN
            this.thongsoBan.loaimd_id = drTHONGSO.LOAIMD_BAN
            this.thongsoBan.ma_doicap = drTHONGSO.MADOICAP_BAN
            this.thongsoBan.chuquan_id = drTHONGSO.CHUQUAN_BAN
            this.thongsoBan.ma_tn = drTHONGSO.MATN_BAN
            this.thongsoBan.thietbidc_id = drTHONGSO.THIETBIDAU_BAN
            this.thongsoBan.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_BAN
            this.thongsoBan.thoihan_id = drTHONGSO.THOIHAN_BAN
            this.thongsoBan.tocdo_id = drTHONGSO.TOCDO_BAN
            this.thongsoBan.tocdo_isp = drTHONGSO.TOCDOISP_BAN
            this.thongsoBan.tocdo_nix = drTHONGSO.TOCDONIX_BAN
            try {
              this.thongsoBan.tocdo_pir_id = drTHONGSO.TOCDOPIR_BAN
              this.thongsoBan.phanloai_id = drTHONGSO.PHANLOAI_ID
              this.thongsoBan.phantach_id = drTHONGSO.PHANTACH_TD
            } catch (e) {}
            this.thongsoBan.tocdothuc_id = drTHONGSO.TOCDOTHUC_BAN
            this.thongsoBan.trangbi_id = drTHONGSO.TRANGBI_BAN

            this.thongsoTc.chuquan_id = drTHONGSO.CHUQUAN_TC
            this.thongsoTc.chuquanld_id = drTHONGSO.CHUQUANLD_TC
            this.thongsoTc.congnghe_id = drTHONGSO.CONGNGHE_TC
            this.thongsoTc.loaikenh_id = drTHONGSO.LOAIKENH_TC
            this.thongsoTc.loaimd_id = drTHONGSO.LOAIMD_TC
            this.thongsoTc.ma_doicap = drTHONGSO.MADOICAP_TC
            this.thongsoTc.ma_tn = drTHONGSO.MATN_TC

            this.thongsoTc.thietbidc_id = drTHONGSO.THIETBIDAU_TC
            this.thongsoTc.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_TC
            try {
              this.thongsoTc.thoihan_id = drTHONGSO.THOIHAN_TC
            } catch (e) {}
            this.thongsoTc.tocdo_id = drTHONGSO.TOCDO_TC
            this.thongsoTc.tocdo_isp = drTHONGSO.TOCDOISP_TC
            this.thongsoTc.tocdo_nix = drTHONGSO.TOCDONIX_TC
            try {
              this.thongsoTc.tocdo_pir_id = drTHONGSO.TOCDOPIR_TC
            } catch (e) {}
            this.thongsoTc.tocdothuc_id = drTHONGSO.TOCDOTHUC_TC
            this.thongsoTc.trangbi_id = drTHONGSO.TRANGBI_TC
          }
          this.thongsoBan.muccuoc_id = drTHONGSO.MUCCUOC_BAN
          this.thongsoBan.muccuoctb_id = drTHONGSO.MUCCUOCTB_BAN
          this.thongsoBan.kieuld_id = drTHONGSO.KIEULD_BAN
          this.thongsoBan.doituong_id = drTHONGSO.DOITUONG_BAN
          this.thongsoTc.kieuld_id = drTHONGSO.KIEULD_TC
          this.thongsoTc.doituong_id = drTHONGSO.DOITUONG_TC
          this.thongsoTc.muccuoc_id = drTHONGSO.MUCCUOC_TC
          this.thongsoTc.muccuoctb_id = drTHONGSO.MUCCUOCTB_TC

          this.thongsoTc.kieutra_id = drTHONGSO.KIEUTRA_TC
          this.thongsoBan.kieutra_id = drTHONGSO.KIEUTRA_BAN
        }
      } catch (err) {
        console.log('Gan_thongso_bancheo_hdtb:err', err)
      }
      finally{
        console.log('Gan_thongso_bancheo_hdtb:','hdtb_id:',hdtb_id,'thongsoBan',this.thongsoBan, 'thongsoTc:',this.thongsoTc)
      }
    },
    async HienThiTTHopDongTB(datalist) {
      console.log('HienThiTTHopDongTB', datalist)
      if (datalist != null && datalist.length > 0) {
        let data = datalist[0]
        this.hdtb_id = data['hdtb_id']
        await this.Gan_thongso_bancheo_hdtb(this.hdtb_id)
        if (data['thuebao_id'] != null && data['thuebao_id'] != '') this.thuebao_id = parseInt(data['thuebao_id'])
        this.tinh = await this.LAY_TINHTHICONG_THEO_THUEBAOID(this.thuebao_id)
        console.log('HienThiTTHopDongTB:tinh', this.tinh)
        if (this.tinh.tinhthicong_id > 0) {
          console.log(`await this.lay_thuebaoid_thicong(this.tinh.tinh_id, this.thuebao_id)`)
          this.thuebao_id_tc = await this.lay_thuebaoid_thicong(this.tinh.tinhthicong_id, this.thuebao_id)
        } else {
          console.log(`await this.tinh_thicong(parseInt(datalist[0]['tinh_thicong']))`, datalist[0]['tinh_thicong'])
          this.tinh = await this.tinh_thicong(parseInt(datalist[0]['tinh_thicong']))
          this.thuebao_id_tc = 0
        }

        this.txtMaTB = data['ma_tb']
        this.txtTenTB = data['ten_tb']
        this.diachi_tb = data['diachi_tb']
        this.txtDiaChiTB = this.diachi_tb
        this.diachi_ld = data['diachi_ld']
        this.txtDiaChiLD = this.diachi_ld
        let _loaitbId = parseInt(data['loaitb_id'])
        try {
          let phanvung_id = this.$root.token.getPhanVungID()
          let loaiTBDv = this.GetData(
            await api.lay_dichvuvt_loaitb_id(this.axios, {
              tinhthicong_id:phanvung_id,
              loaitbId: _loaitbId
            })
          )
          if (loaiTBDv != null && loaiTBDv.length > 0) {
            let dvvtid = loaiTBDv[0].dichvuvt_id
            this.cboDichVuVT = dvvtid
            await this.loaihinh_tb(dvvtid)
            // await this.cboDichVuVT_Change()
          }
        } catch (err) {
          console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 2683 ~ HienThiTTHopDongTB ~ err', err)
        }

        this.loaitb_id = parseInt(data['loaitb_id'])
        this.cboLoaihinhTB = this.loaitb_id
        await this.cboLoaihinhTB_SelectedValueChanged(this.loaitb_id)
        this.cboKieuLD = data['kieuld_id']
        await this.cboKieuLD_SelectedValueChanged()
        this.doituong_id = parseInt(data['doituong_id'])
        this.donvi_id = parseInt(data['donvi_id'])
        this.txtGhiChu = data['ghichu']
        if (data['thuebao_cha_id'] != null && data['thuebao_cha_id'] != '') {
          this.thuebao_id_cha = parseInt(data['thuebao_cha_id'])
        } else {
          this.thuebao_id_cha = 0
        }

        let hdtb = await this.lay_ds_hopdong_thuebao_mgwan(this.hdtb_id)
        if (hdtb != null && hdtb.length > 0) {
          this.txtSoAo = hdtb[0]['ma_lt']
          this.vsoao = hdtb[0]['ma_lt']
          this.cboTocDo = parseInt(hdtb[0]['tocdo_id'])

          if (this.loaitb_id == LoaiHinhTB.MGW_INTERNET_TRUCTIEP||this.loaitb_id==LoaiHinhTB.NB_INTERNETTT) {
            if (this.NotNull(hdtb[0]['phantach_td'])) {
              this.cboPhanTachTD = parseInt(hdtb[0]['phantach_td'])
              this.cboPhanTachTDCu = parseInt(hdtb[0]['phantach_td'])
            }
            if (this.NotNull(hdtb[0]['phanloai_id'])) {
              this.cboPhanLoaiMGW = parseInt(hdtb[0]['phanloai_id'])
              this.cboPhanLoaiMGWCu = parseInt(hdtb[0]['phanloai_id'])
            }
            //this.cboPhanTachTD_Enabled = true;
            //this.cboPhanLoaiMGW_Enabled = true;
          }

          if (this.NotNull(hdtb[0]['congnghe_id'])) {
            this.cboCongNghe_Moi = parseInt(hdtb[0]['congnghe_id'])
          } else {
            this.cboCongNghe_Moi = ''
          }

          if (this.NotNull(hdtb[0]['tocdo_pir_id'])) {
            this.chkTocDo_PIR = true
            this.cboTocDo_Pir = parseInt(hdtb[0]['tocdo_pir_id'])
          } else {
            this.chkTocDo_PIR = false
            this.cboTocDo_Pir = parseInt(hdtb[0]['tocdo_id'])
          }

          if (this.NotNull(hdtb[0]['tocdo_nix'])) {
            this.chkTocDo_NIX = true
            this.cboTocDo_NIX = parseInt(hdtb[0]['tocdo_nix'])
          } else {
            this.chkTocDo_NIX = false
            // this.cboTocDo_NIX = 0
          }

          if (this.NotNull(hdtb[0]['tocdo_isp'])) {
            this.chkTocDo_ISP = true
            this.cboTocDo_ISP = parseInt(hdtb[0]['tocdo_isp'])
          } else {
            this.chkTocDo_ISP = false
            //this.cboTocDo_ISP = 0
          }

          if (hdtb[0]['cuoc_nix'] != null && hdtb[0]['cuoc_nix'] != '') {
            console.log('cuoc_nix', hdtb[0]['cuoc_nix'])
            this.txtCuoc_NIX = parseFloat(hdtb[0]['cuoc_nix'])
          } else {
            this.txtCuoc_NIX = '0'
          }

          if (hdtb[0]['cuoc_isp'] != null && hdtb[0]['cuoc_isp'] != '') {
            this.txtCuoc_ISP = parseFloat(hdtb[0]['cuoc_isp'])
          } else {
            this.txtCuoc_ISP = '0'
          }

          if (this.NotNull(hdtb[0]['sl_ip'])) {
            this.txtSoLuong_IP = hdtb[0]['sl_ip']
          } else {
            this.txtSoLuong_IP = '0'
          }

          if (hdtb[0]['cuoc_ip'] != null && hdtb[0]['cuoc_ip'] != '') {
            this.txtCuoc_IP = parseFloat(hdtb[0]['cuoc_ip'])
          } else {
            this.txtCuoc_IP = '0'
          }

          if (this.NotNull(hdtb[0]['tinh_kn_id'])) {
            this.cboTinh_KN_Moi = parseInt(hdtb[0]['tinh_kn_id'])
          } else {
            this.cboTinh_KN_Moi = 0
          }

          this.cboLoaiModem = parseInt(hdtb[0]['loaimd_id'])
          this.cboLoaiKenh = parseInt(hdtb[0]['loaikenh_id'])
          this.cboLoaiTBiWan = parseInt(hdtb[0]['thietbidc_id'])
          if (this.NotNull(hdtb[0]['chuquan_id'])) this.cboChuQuanMEGAWAN = parseInt(hdtb[0]['chuquan_id'])
          if (this.NotNull(hdtb[0]['trangbi_id'])) this.cboTrangBiMEGAWAN = parseInt(hdtb[0]['trangbi_id'])
          if (this.NotNull(hdtb[0]['thoihan_id'])) this.cboThoiHanMEGAWAN = parseInt(hdtb[0]['thoihan_id'])
          //txtHuongKn = hdtb[0]["huong_kn"];
          this.vhuongkn = hdtb[0]['huong_kn']
          if (this.thuebao_id_cha != null && this.thuebao_id_cha > 0) {
            this.txtHuongKn = await this.fn_lay_text_huongketnoi(this.thuebao_id_cha, 1)
          }

          this.txtMaHD_TTKD = hdtb[0]['ma_pl']
          this.txtSiteID = hdtb[0]['site_id']
          this.txtWanIP = hdtb[0]['wan_ip']
          this.txtLanIP = hdtb[0]['lan_ip']

          if (this.NotNull(hdtb[0]['ma_cv'])) {
            this.chkCongVan = true
            this.txtCongVan = hdtb[0]['ma_cv']
            this.dtpNgayCV = hdtb[0]['ngay_cv']
          } else {
            this.chkCongVan = false
            this.txtCongVan = ''
            this.dtpNgayCV = moment().format('DD/MM/YYYY')
          }

          if (this.NotNull(hdtb[0]['doitac_id'])) {
            this.cboDoiTac = parseInt(hdtb[0]['doitac_id'])
            this.txtMaHD_DT = hdtb[0]['mahd_dt']
            this.txtCuocTKDT = parseFloat(hdtb[0]['cuoc_tkdt'])
            this.txtCuocTCDT = parseFloat(hdtb[0]['cuoc_tcdt'])
            this.chkDoiTac = true
          } else {
            this.chkDoiTac = false
            this.txtCuocTKDT = '0'
            this.txtCuocTCDT = '0'
            this.txtMaHD_DT = ''
          }
          this.txtCuocTK = parseFloat(hdtb[0]['cuoc_tk'])
          this.txtCuocTC = parseFloat(hdtb[0]['cuoc_tc'])
          this.txtCuocHT = parseFloat(hdtb[0]['cuoc_ht'])
          this.txtCuocTBI = parseFloat(hdtb[0]['cuoc_tbi'])
          this.txtMaBSS = hdtb[0]['ma_bss']
          this.txtMaERP = hdtb[0]['id_erp']
          let hdtbmg = await this.lay_ds_bddb_mgwan_theo_hdtb_id(this.hdtb_id)
          if (hdtbmg != null && hdtbmg.length > 0) {
            //Thông tin thuê bao cũ
            this.cboTocDoCu = parseInt(hdtbmg[0]['tocdo_id'])
            if (this.NotNull(hdtbmg[0]['tocdo_pir_id'])) {
              this.chkTocDo_PIR_Cu = true
              this.cboTocDo_Pir_Cu = parseInt(hdtbmg[0]['tocdo_pir_id'])
            } else {
              this.chkTocDo_PIR_Cu = false
              this.cboTocDo_Pir_Cu = parseInt(hdtbmg[0]['tocdo_id'])
            }
            if (hdtbmg[0]['tocdo_nix'] != null && hdtbmg[0]['tocdo_nix'] != '') {
              this.chkTocDo_NIX_Cu = true
              this.cboTocDo_NIX_Cu = parseInt(hdtbmg[0]['tocdo_nix'])
            } else {
              this.chkTocDo_NIX_Cu = false
              // this.cboTocDo_NIX_Cu = 0
            }
            if (hdtbmg[0]['tocdo_isp'] != null && hdtbmg[0]['tocdo_isp'] != '') {
              this.chkTocDo_ISP_Cu = true
              this.cboTocDo_ISP_Cu = parseInt(hdtbmg[0]['tocdo_isp'])
            } else {
              this.chkTocDo_ISP_Cu = false
              // this.cboTocDo_ISP_Cu = 0
            }
            if (hdtbmg[0]['cuoc_nix'] != null && hdtbmg[0]['cuoc_nix'] != '') {
              this.txtCuoc_NIX_Cu = parseFloat(hdtbmg[0]['cuoc_nix'])
            } else {
              this.txtCuoc_NIX_Cu = '0'
            }

            if (hdtbmg[0]['cuoc_isp'] != null && hdtbmg[0]['cuoc_isp'] != '') {
              this.txtCuoc_ISP_Cu = parseFloat(hdtbmg[0]['cuoc_isp'])
            } else {
              this.txtCuoc_ISP_Cu = '0'
            }

            if (hdtbmg[0]['sl_ip'] != null && hdtbmg[0]['sl_ip'] != '') {
              this.txtSoLuong_IP_Cu = hdtbmg[0]['sl_ip']
            } else {
              this.txtSoLuong_IP_Cu = '0'
            }

            if (hdtbmg[0]['cuoc_ip'] != null && hdtbmg[0]['cuoc_ip'] != '') {
              this.txtCuoc_IP_Cu = parseFloat(hdtbmg[0]['cuoc_ip'])
            } else {
              this.txtCuoc_IP_Cu = '0'
            }

            let i_tinhkn_id = parseInt(await this.maptinh(this.thuebao_id))
            if (i_tinhkn_id != -1) {
              this.cboTinh_KN_Cu = i_tinhkn_id
            } else {
              this.cboTinh_KN_Cu = 0
            }

            this.cboLoaiModemCu = parseInt(hdtbmg[0]['loaimd_id'])
            this.cboLoaiKenhCu = parseInt(hdtbmg[0]['loaikenh_id'])
            this.cboLoaiTBiWanCu = parseInt(hdtbmg[0]['thietbidc_id'])
            if (hdtbmg[0]['chuquan_id'] != null && hdtbmg[0]['chuquan_id'] != '') this.cboChuQuanMEGAWANCu = parseInt(hdtbmg[0]['chuquan_id'])
            if (hdtbmg[0]['trangbi_id'] != null && hdtbmg[0]['trangbi_id'] != '') this.cboTrangBiMEGAWANCu = parseInt(hdtbmg[0]['trangbi_id'])
            if (hdtbmg[0]['thoihan_id'] != null && hdtbmg[0]['thoihan_id'] != '') this.cboThoiHanMEGAWANCu = parseInt(hdtbmg[0]['thoihan_id'])
            this.txtHuongKnCu = hdtbmg[0]['huong_kn']
            if (hdtbmg[0]['doitac_id'] != null && hdtbmg[0]['doitac_id'] != '') {
              this.cboDoiTac_Cu = parseInt(hdtbmg[0]['doitac_id'])
              this.txtMaHD_DT_Cu = hdtbmg[0]['mahd_dt']
              this.txtCuocTKDT_Cu = parseFloat(hdtbmg[0]['cuoc_tkdt'])
              this.txtCuocTCDT_Cu = parseFloat(hdtbmg[0]['cuoc_tcdt'])
              this.chkDoiTac_Cu = true
            } else {
              this.chkDoiTac_Cu = false
              this.txtCuocTKDT_Cu = '0'
              this.txtCuocTCDT_Cu = '0'
              this.txtMaHD_DT_Cu = ''
            }
            this.txtCuocTK_Cu = parseFloat(hdtbmg[0]['cuoc_tk'])
            this.txtCuocTC_Cu = parseFloat(hdtbmg[0]['cuoc_tc'])
            this.txtCuocHT_Cu = parseFloat(hdtbmg[0]['cuoc_ht'])
            this.txtCuocTBI_Cu = parseFloat(hdtbmg[0]['cuoc_tbi'])
          }
          await this.HT_DS_KhuyenMai_Combobox()
          //Longdx: bổ sung lấy chi tiết KM
          let dsTemp = await this.lay_chitiet_km_theo_hdtb_id(this.hdtb_id, 0)
          if (dsTemp != null && dsTemp.length > 0) {
            if (dsTemp[0]['khuyenmai_id'] != null && dsTemp[0]['khuyenmai_id'] != '') {
              this.cboKhuyenMai = parseInt(dsTemp[0]['khuyenmai_id'])
              this.chkKhuyenMai = true
            } else {
              this.chkKhuyenMai = false
            }

            let tmp
            let ithang_huongdc = 0
            let ithang_huongkm = 0
            let ithang_tg = 0
            let dtiendatcoc_csd = 0
            let dtien_td = 0
            let _hdtb_id = this.hdtb_id
            this.dsKM_HDTB = []
            for (let i = 0; i < dsTemp.length; i++) {
              ithang_huongdc = 0
              ithang_huongkm = 0
              ithang_tg = 0
              dtiendatcoc_csd = 0
              dtien_td = 0
              let dr
              dr = {}
              dr.THANG_HUONGDC = dsTemp[i]['thang_huongdc']
              dr.THANG_HUONGKM = dsTemp[i]['thang_huongkm']
              dr.SOTHANG_TG = dsTemp[i]['sothang_tg']
              dr.DATCOC_CSD = dsTemp[i]['datcoc_csd']
              dr.TIEN_TD = dsTemp[i]['tien_td']

              if (dsTemp[i]['kieu_bdkm'] == '3') {
                dr.THANG_BD = parseInt(dsTemp[i]['thang_bdkm'])
                //dr.THANG_KT = parseInt(tmp.AddMonths(ithang_huongkm).ToString('yyyyMM'))
              }
              if (dsTemp[i]['kieu_bdtg'] == '3') {
                dr.THANG_BDTG = parseInt(dsTemp[i]['thang_bdtg'])
                //dr.THANG_KTTG = parseInt(tmp.AddMonths(ithang_tg).ToString('yyyyMM'))
              }
              if (dsTemp[i]['kieu_bddc'] == '3') {
                dr.THANG_BDDC = parseInt(dsTemp[i]['thang_bddc'])
                //dr.THANG_KTDC = parseInt(tmp.AddMonths(ithang_huongkm).ToString('yyyyMM'))
              }
              //longdx: bổ sung thông tin tỷ lệ km
              dr.TYLE_KMLD = parseFloat(dsTemp[i]['tyle_kmld'])
              dr.TYLE_SD = parseFloat(dsTemp[i]['tyle_sd'])
              dr.TYLE_TB = parseFloat(dsTemp[i]['tyle_tb'])
              dr.HDTB_ID = _hdtb_id
              dr.CHITIETKM_ID = parseFloat(dsTemp[i]['chitietkm_id'])
              dr.KHUYENMAI_ID = parseFloat(dsTemp[i]['khuyenmai_id'])
              //dr.KHUYENMAI_ID_NEO = parseFloat(dsTemp[i]["khuyenmai_id_neo"]);
              dr.KIEU_YC = parseInt(dsTemp[i]['kieu_yc'])
              this.dsKM_HDTB.push(dr)
            }
          } else {
            this.chkKhuyenMai = false
            // dsKM_HDTB = new KHUYENMAI_HDTB_DATA();
          }
        }
      }
    },
    async HienThiDanhSacHDTB(data) {
      console.log('HienThiDanhSacHDTB', data)
      this.txtThoiHanCK = data[0].sothang_ck
      if (data == null) this.DsThueBao.data = []
      else this.DsThueBao.data = data

      if (data != null && data.length > 0) {
        this.lblTongS = `Tổng số : ${data.length} thuê bao`
      } else {
        this.lblTongS = `Tổng số`
      }
      let tongtienhd = 0
      let tongtientb = 0
      for (let i = 0; i < data.length; i++) {
        tongtientb = parseFloat(data[i]['tongtien'])
        tongtienhd = tongtienhd + tongtientb
      }
      this.txtTongTienHD = tongtienhd
      if (data.length > 1) {
        this.tsbtnXoaTB_Enabled = true
      } else {
        this.tsbtnXoaTB_Enabled = false
      }
    },
    async HienThiTTDiaChi(ds, kieu) {
      if (ds != null && ds.length > 0) {
        if (kieu == 1) {
          //phuongld_id = parseInt(ds[0]["phuong_id"]);
          //phold_id = parseInt(ds[0]["pho_id"]);
        }
        if (kieu == 3) {
          this.phuongld_id = 0
          this.phold_id = 0
          this.apld_id = 0
          this.khuld_id = 0

          if (this.NotNull(ds[0]['phuongld_id'])) {
            this.phuongld_id = parseInt(ds[0]['phuongld_id'])
          }
          if (this.NotNull(ds[0]['phold_id'])) {
            this.phold_id = parseInt(ds[0]['phold_id'])
          }
          if (this.NotNull(ds[0]['apld_id'])) {
            this.apld_id = parseInt(ds[0]['apld_id'])
          }
          if (this.NotNull(ds[0]['khuld_id'])) {
            this.khuld_id = parseInt(ds[0]['khuld_id'])
          }
        }
      }
    },
    async TaoDuLieu(themmoi) {
      await this.TaoDuLieu_HDKH(themmoi)
      if (this.loi_sinh_magd) return
      await this.TaoDuLieu_HDTB(themmoi)
      await this.TaoDuLieu_HDTB_MGWAN(themmoi)
      //if (themmoi) TaoDuLieu_MangKH();
      //Lam thêm tạo dữ liệu thông tin thanh toán, chi tiết khoản mục
      await this.TaoDuLieu_THONGTIN_TT(themmoi)
      await this.TaoDuLieu_CT_TIENHD(true)
      await this.TaoDuLieu_HDTBDV(themmoi)
      await this.TaoDuLieu_HDBANCHEO(themmoi)
      await this.TaoDulieu_HDBCMGWAN(themmoi)
    },
    async TaoDuLieu_HDKH(themmoi) {
      console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 3340 ~ TaoDuLieu_HDKH ~ themmoi', themmoi)

      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let nguoi_cn = this.$root.token.getUserName()
      let vmagd_temp = ''
      this.dsHDKH = []
      if (themmoi == true) this.hdkh_id = await this.getKey('HD_KHACHHANG')
      let rowHDKH = {}
      rowHDKH.HDKH_ID = this.hdkh_id
      if (themmoi == true) {
        if (this.ts_sinhma_gd_theo_donvi) {
          vmagd_temp = await this.Lay_Ma_GD_V2(donviId, LoaiHopDong.THAYDOI_THONGSO_MEGAWAN)
        } else {
          vmagd_temp = await this.Lay_Ma_GD(LoaiHopDong.THAYDOI_THONGSO_MEGAWAN)
        }
        if (vmagd_temp.includes('ERROR:')) {
          this.ShowError(vmagd_temp.Split(':')[1])
          this.loi_sinh_magd = true
        }
        this.txtMaGD = vmagd_temp
        //txtMaGD = laphd.Lay_Ma_GD(LoaiHopDong.THAYDOI_THONGSO_MEGAWAN);
        rowHDKH.MA_GD = this.txtMaGD
        rowHDKH.MA_HD = this.txtMaHD
      } else {
        rowHDKH.MA_GD = this.txtMaGD
        rowHDKH.MA_HD = this.txtMaHD
      }
      rowHDKH.NGAY_YC = moment(this.dtpNgayYeuCau, 'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
      rowHDKH.MA_KH = this.txtMaKH
      if (this.khachhang_id > 0) rowHDKH.KHACHHANG_ID = this.khachhang_id
      rowHDKH.TEN_KH = this.txtTenKH
      rowHDKH.DIACHI_KH = this.txtDiaChiKH
      rowHDKH.NGAYLAP_HD = this.dtpNgayLapHD
      rowHDKH.DONVI_ID = donviId
      rowHDKH.NHANVIEN_ID = nhanvienId
      if (this.khachhang_id > 0) {
        let khl_id = await this.mapkhlon_id(this.khachhang_id)
        if (khl_id != '' && khl_id != '-1') {
          rowHDKH.KHLON_ID = parseInt(khl_id)
        } else {
          rowHDKH.KHLON_ID = 1
        }
      } else {
        rowHDKH.KHLON_ID = 1
      }
      rowHDKH.LOAIHD_ID = LoaiHopDong.THAYDOI_THONGSO_MEGAWAN
      //luan
      if (this.chkCTV && this.txtCTV != '') {
        rowHDKH.CTV_ID = this.ctv_id
      }
      if (this.chkNguoiGT && this.txtNguoiGT != '') {
        rowHDKH.NHANVIENGT_ID = this.nguoigt_id
      }
      // let tt_nd = await this.$root.token.getThongTinNguoiDung()
      rowHDKH.NGUOI_CN = nguoi_cn
      rowHDKH.NGAY_CN = moment().format('DD/MM/YYYY HH:mm:ss')
      rowHDKH.MAY_CN = this.may_cn
      rowHDKH.IP_CN = this.ip_cn
      let rowHDKHStr= JSON.stringify(rowHDKH)
      //this.dsHDKH.push(rowHDKH)
      this.dsHDKH.push(JSON.parse(rowHDKHStr))

      if (themmoi == false) {
        //dsHDKH.AcceptChanges();
        //dsHDKH.HD_KHACHHANG.Rows[0].SetModified();
      }
      return this.dsHDKH
    },
    IsNullOrEmpty(val) {
      if (val == null || val == undefined || val == '') return true
      return false
    },
    async mapdoituongId(thuebao_id) {
      try {
        let phanvungId = this.$root.token.getPhanVungID()
        let input = {
          tinhId: phanvungId,
          ptype: 14,
          param: thuebao_id
        }
        let data = this.GetData(await api.fn_tt_db_thuebao_tinh(this.axios, input))
        return data
      } catch (err) {
        return 0
      }
    },
    async mapdonviId(thuebao_id) {
      try {
        let phanvungId = this.$root.token.getPhanVungID()
        let input = {
          tinhId: phanvungId,
          ptype: 15,
          param: thuebao_id
        }
        let data = this.GetData(await api.fn_tt_db_thuebao_tinh(this.axios, input))
        return data
      } catch (err) {
        return 0
      }
    },
    async TaoDuLieu_HDTB(themmoi) {
      try{
        console.log('TaoDuLieu_HDTB ~ themmoi', themmoi, this.loaitb_id)
        let donvi_id = this.$root.token.getDonViID()
        let nhanvienId = this.$root.token.getNhanVienID()
        let ip = this.$root.token.getIP()
        let may_cn = this.$root.token.getMachine()
        let username = this.$root.token.getUserName()
        // let tt_nd = this.$root.token.getThongTinNguoiDung()
        this.dsHDTB = []
        if (themmoi == true) {
          this.hdtb_id = await this.GetKey('HD_THUEBAO')
        }
        let rowHDTB1 = {
          LOAITB_ID:1,
          DICHVUVT_ID:8
        }
        rowHDTB1.LOAITB_ID = this.loaitb_id
        console.log('TaoDuLieu_HDTB ~rowHDTB ',rowHDTB1.LOAITB_ID )
        rowHDTB1.HDTB_ID = this.hdtb_id
        rowHDTB1.HDKH_ID = this.hdkh_id
        if (this.thuebao_id > 0) rowHDTB1.THUEBAO_ID = this.thuebao_id
        rowHDTB1.MA_TB = this.txtMaTB
        rowHDTB1.TEN_TB = this.txtTenTB
        rowHDTB1.DIACHI_TB = this.diachi_tb
        rowHDTB1.DIACHI_LD = this.diachi_ld
        rowHDTB1.KIEULD_ID = parseInt(this.cboKieuLD)
        let doituong_id = await this.mapdoituongId(this.thuebao_id)
        rowHDTB1.DOITUONG_ID = doituong_id //this.doituong_id
        rowHDTB1.TTHD_ID = TrangThaiHD.MOI
        console.log('TaoDuLieu_HDTB ~ themmoi2', themmoi, this.loaitb_id)
        // rowHDTB.LOAITB_ID = this.loaitb_id
        rowHDTB1.DICHVUVT_ID = this.dichvuvt_id
        if (this.thuebao_id_cha!=null && this.thuebao_id_cha != 0) rowHDTB1.THUEBAO_CHA_ID = this.thuebao_id_cha
        try{
          let dvid = await this.mapdonviId(this.thuebao_id)
          rowHDTB1.DONVI_ID = dvid //  donvi_id
        }catch(dviderr){
          console.log('dviderr',dviderr)
        }
        rowHDTB1.NGUOI_CN = username
        rowHDTB1.NGAY_CN = moment().format('DD/MM/YYYY HH:mm:ss')
        rowHDTB1.MAY_CN =this.may_cn
        rowHDTB1.GHICHU = this.txtGhiChu
        rowHDTB1.QUYTRINH_ID = this.quytrinh_id

        let thoiHanCK = 0
        let _kieuld_id = parseInt(this.cboKieuLD)
        if(_kieuld_id == this.KieuLapDat.VTI_DOI_GiACUOC_MEGA && this.$root.token.getPhanVungID() == 98){
          thoiHanCK = this.txtThoiHanCK
        }
        rowHDTB1.SOTHANG_CK = thoiHanCK
        try{
          rowHDTB1.MUCUOC_TB = this.thongsoBan.muccuoc_id
          rowHDTB1.MUCUOCTB_ID = this.thongsoBan.muccuoctb_id
        }
        catch(errthongsoBan){
          console.log('errthongsoBan',errthongsoBan)
        }

        //Hiếu bổ sung trường tiền đặt cọc cước sử dụng = 0 - 20.10.2010
        //rowHDTB.DATCOC_CSD = 0;
        console.log('TaoDuLieu_HDTB ~rowHDTB ',rowHDTB1)
        this.dsHDTB.push(rowHDTB1)
        let a=JSON.stringify(rowHDTB1)
        this.dsHDTBTest = []
        this.dsHDTBTest.push(JSON.parse(a))
        this.dsHDTB.push(JSON.parse(a))
        if (themmoi == false) {
          //dsHDTB.AcceptChanges();
          //dsHDTB.HD_THUEBAO.Rows[0].SetModified();
        }
        this.dsKM_HDTB.forEach(function (row) {
          row.HDTB_ID = this.hdtb_id
        })
        console.log('TaoDuLieu_HDTB ~ dsHDTB', this.dsHDTB)
      }
      catch(err){
        console.error('TaoDuLieu_HDTB',err)
      }

    },
    async TaoDuLieu_HDTB_MGWAN(themmoi) {
      try {
        console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 3456 ~ TaoDuLieu_HDTB_MGWAN ~ themmoi', themmoi)
        this.dsHDTB_MEWAN = []
        let rowHDTB_MGWAN = {}
        let data_danhba_megawan = await this.lay_ds_danhba_megawan(this.thuebao_id)
        if (data_danhba_megawan != null && data_danhba_megawan.length > 0) {
          rowHDTB_MGWAN = data_danhba_megawan[0]
          this.UpperObjectKeys(rowHDTB_MGWAN)
        }
        rowHDTB_MGWAN.HDTB_ID = this.hdtb_id
        rowHDTB_MGWAN.TOCDO_ID = parseInt(this.cboTocDo)
        rowHDTB_MGWAN.MA_BSS = this.txtMaBSS
        rowHDTB_MGWAN.ID_ERP = this.txtMaERP
        if (this.chkTocDo_PIR) rowHDTB_MGWAN.TOCDO_PIR_ID = parseInt(this.cboTocDo_Pir)
        else rowHDTB_MGWAN.TOCDO_PIR_ID = null
        if (parseInt(this.cboLoaihinhTB) == LoaiHinhTB.MGW_INTERNET_TRUCTIEP||parseInt(this.cboLoaihinhTB)==LoaiHinhTB.NB_INTERNETTT||parseInt(this.cboLoaihinhTB)==259||parseInt(this.cboLoaihinhTB)==260) {
          if (this.PhanLoaiMGW != null && this.cboPhanLoaiMGW != null) rowHDTB_MGWAN.PHANLOAI_ID = parseInt(this.cboPhanLoaiMGW)
          if (this.PhanLoaiMGW != null && this.cboPhanTachTD != null) rowHDTB_MGWAN.PHANTACH_TD = parseInt(this.cboPhanTachTD)

          if (!this.IsNullOrEmpty(this.txtSoLuong_IP)) rowHDTB_MGWAN.SL_IP = parseInt(this.txtSoLuong_IP)
          else rowHDTB_MGWAN.SL_IP = 0

          if (!this.IsNullOrEmpty(this.txtCuoc_IP)) rowHDTB_MGWAN.CUOC_IP = parseInt(this.txtCuoc_IP)
          else rowHDTB_MGWAN.CUOC_IP = 0

          if (this.chkTocDo_NIX) {
            rowHDTB_MGWAN.TOCDO_NIX = parseInt(this.cboTocDo_NIX)
            if (!this.IsNullOrEmpty(this.txtCuoc_NIX)) rowHDTB_MGWAN.CUOC_NIX = parseInt(this.txtCuoc_NIX)
            else rowHDTB_MGWAN.CUOC_NIX = 0
          } else {
            rowHDTB_MGWAN.TOCDO_NIX = null
            rowHDTB_MGWAN.CUOC_NIX = null
          }

          if (this.chkTocDo_ISP) {
            rowHDTB_MGWAN.TOCDO_ISP = parseInt(this.cboTocDo_ISP)
            if (!this.IsNullOrEmpty(this.txtCuoc_ISP)) rowHDTB_MGWAN.CUOC_ISP = parseInt(this.txtCuoc_ISP)
            else rowHDTB_MGWAN.CUOC_ISP = 0
          } else {
            rowHDTB_MGWAN.TOCDO_ISP = null
            rowHDTB_MGWAN.CUOC_ISP = null
          }
        } else {
          rowHDTB_MGWAN.SL_IP = null
          rowHDTB_MGWAN.CUOC_IP = null
          rowHDTB_MGWAN.TOCDO_NIX = null
          rowHDTB_MGWAN.TOCDO_ISP = null
          rowHDTB_MGWAN.CUOC_NIX = null
          rowHDTB_MGWAN.CUOC_ISP = null
          rowHDTB_MGWAN.PHANLOAI_ID = null
          rowHDTB_MGWAN.PHANTACH_TD = null
        }

        if (parseInt(this.cboTinh_KN_Moi) != 0) rowHDTB_MGWAN.TINH_KN_ID = parseInt(this.cboTinh_KN_Moi)
        else rowHDTB_MGWAN.TINH_KN_ID = null

        rowHDTB_MGWAN.LOAIMD_ID = parseInt(this.cboLoaiModem)
        rowHDTB_MGWAN.LOAIKENH_ID = parseInt(this.cboLoaiKenh)
        rowHDTB_MGWAN.THIETBIDC_ID = parseInt(this.cboLoaiTBiWan)
        rowHDTB_MGWAN.HUONG_KN = this.vhuongkn
        rowHDTB_MGWAN.CHUQUAN_ID = parseInt(this.cboChuQuanMEGAWAN)
        rowHDTB_MGWAN.TRANGBI_ID = parseInt(this.cboTrangBiMEGAWAN)
        rowHDTB_MGWAN.THOIHAN_ID = parseInt(this.cboThoiHanMEGAWAN)
        rowHDTB_MGWAN.MA_PL = this.txtMaHD_TTKD
        rowHDTB_MGWAN.LOAITB_ID = this.loaitb_id
        if (this.chkCongVan) {
          rowHDTB_MGWAN.MA_CV = this.txtCongVan
          rowHDTB_MGWAN.NGAY_CV = dtpNgayCV.Value
        } else {
          rowHDTB_MGWAN.MA_CV = null
          rowHDTB_MGWAN.NGAY_CV = null
        }
        rowHDTB_MGWAN.SITE_ID = this.txtSiteID
        rowHDTB_MGWAN.DIACHI_KN = this.txtDiaChi_KN
        rowHDTB_MGWAN.MA_LT = this.vsoao
        if (this.txtLanIP) {
          rowHDTB_MGWAN.LAN_IP = this.txtLanIP          
        }
        else rowHDTB_MGWAN.LAN_IP =' '
        if (this.txtWanIP) {
          rowHDTB_MGWAN.WAN_IP = this.txtWanIP          
        }
        else rowHDTB_MGWAN.Wan_IP =' '


        if (this.chkDoiTac) {
          rowHDTB_MGWAN.DOITAC_ID = parseInt(this.cboDoiTac)
          if (!this.IsNullOrEmpty(this.txtCuocTKDT)) rowHDTB_MGWAN.CUOC_TKDT = this.txtCuocTKDT
          else rowHDTB_MGWAN.CUOC_TKDT = 0
          if (!this.IsNullOrEmpty(this.txtCuocTCDT)) rowHDTB_MGWAN.CUOC_TCDT = this.txtCuocTCDT
          else rowHDTB_MGWAN.CUOC_TCDT = 0
          rowHDTB_MGWAN.MAHD_DT = this.txtMaHD_DT
        } else {
          rowHDTB_MGWAN.DOITAC_ID = null
          rowHDTB_MGWAN.CUOC_TKDT = 0
          rowHDTB_MGWAN.CUOC_TCDT = 0
          rowHDTB_MGWAN.MAHD_DT = null
        }

        if (!this.IsNullOrEmpty(this.txtCuocHT)) rowHDTB_MGWAN.CUOC_HT = this.txtCuocHT
        else rowHDTB_MGWAN.CUOC_HT = 0
        if (!this.IsNullOrEmpty(this.txtCuocTBI)) rowHDTB_MGWAN.CUOC_TBI = this.txtCuocTBI
        else rowHDTB_MGWAN.CUOC_TBI = 0
        if (!this.IsNullOrEmpty(this.txtCuocTC)) rowHDTB_MGWAN.CUOC_TC = this.txtCuocTC
        else rowHDTB_MGWAN.CUOC_TC = 0
        if (!this.IsNullOrEmpty(this.txtCuocTK)) rowHDTB_MGWAN.CUOC_TK = this.txtCuocTK
        else rowHDTB_MGWAN.CUOC_TK = 0
        // this.UpperObjectKeys(rowHDTB_MGWAN)
        rowHDTB_MGWAN.HDTB_ID = this.hdtb_id
        let rowHDTB_MGWANStr=JSON.stringify(rowHDTB_MGWAN)
        //this.dsHDTB_MEWAN.push(rowHDTB_MGWAN)
        this.dsHDTB_MEWAN.push(JSON.parse(rowHDTB_MGWANStr))
        let hdtb_id = this.hdtb_id
        this.dsHDTB_MEWAN.forEach(function (row) {
          row.HDTB_ID = hdtb_id
        })
        if (themmoi == false) {
          // dsHDTB_MEWAN.AcceptChanges();
          // dsHDTB_MEWAN.HDTB_MGWAN.Rows[0].Modified();
        }
        return this.dsHDTB_MEWAN
      } catch (err) {
        console.log('TaoDuLieu_HDTB_MGWAN:err', err)
      }
      console.log('qqqqqq');
      
      return this.dsHDTB_MEWAN
    },
    UpperObjectKeys(obj) {
      let a = obj
      for (var key in a) {
        if (a.hasOwnProperty(key)) {
          a[key.toUpperCase()] = a[key]
          delete a[key]
        }
      }
      obj = a
    },
    async TaoDuLieu_THONGTIN_TT(themmoi) {
      console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 3559 ~ TaoDuLieu_THONGTIN_TT ~ themmoi', themmoi)
      // ko co code
    },
    async TaoDuLieu_CT_TIENHD(themmoi) {
      console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 3563 ~ TaoDuLieu_CT_TIENHD ~ themmoi', themmoi)
      this.dsCTTHD = []

      for (var km of this.ds_tien_khoanmuc) {
        let rowCTTHD = {}
        //rowCTTHD = dsCTTHD.CT_TIENHD.NewCT_TIENHDRow();
        rowCTTHD.HDTB_ID = this.hdtb_id
        if (themmoi == true) {
          let id = await this.GetKey('CT_TIENHD')
          rowCTTHD.CT_TIENHD_ID = id
        }
        rowCTTHD.KHOANMUCTT_ID = parseInt(km['khoanmuctt_id'])
        rowCTTHD.TIEN = parseFloat(km['tien'])
        rowCTTHD.VAT = parseFloat(km['vat'])

        rowCTTHD.ID = parseInt(km['khoanmuctt_id'])
        rowCTTHD.LOAI_ID = 1
        this.dsCTTHD.push(rowCTTHD)
      }
      return this.dsCTTHD
    },
    async TaoDuLieu_HDTBDV(themmoi) {
      console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 3585 ~ TaoDuLieu_HDTBDV ~ themmoi', themmoi)
      let tt_nd = this.$root.token.getThongTinNguoiDung()
      this.dsHDTBDV = []
      //let dtTemp = bangts.GetDataSql("select donvi_id, loaidv_id, kieudv_id from " + DatabaseConstants.DB2 + ".dbtb_dv where thuebao_id =" + thuebao_id);
      let dtTemp = await this.get_donvi_id_loaidvId_kieudv_id(this.thuebao_id)
      if (dtTemp != null) {
        for (var temp of dtTemp) {
          if (parseInt(temp['loaidv_id']) != LOAI_DV.TTKD_NGHIEMTHU && parseInt(temp['loaidv_id']) != LOAI_DV.TTDH_NGHIEMTHU) {
            let row = {}
            row.HDTB_ID = this.hdtb_id
            row.LOAIDV_ID = parseInt(temp['loaidv_id'])
            row.DONVI_ID = parseInt(temp['donvi_id'])
            row.KIEUDV_ID = parseInt(temp['kieudv_id'])
            this.dsHDTBDV.push(row)
          }
        }
      }
      let str_thaotac = ''
      // str_thaotac += " select * ";
      // str_thaotac += " from " + DatabaseConstants.DB2 + ".huonggiao_ldv ";
      // str_thaotac += " where quytrinh_id = " + quytrinh_id;
      // str_thaotac += "    and loaidv_dich_id in (" + LOAI_DV.TTKD_NGHIEMTHU + "," + LOAI_DV.TTDH_NGHIEMTHU + ") ";
      // let  ds_thaotac = new DataSet();
      // ds_thaotac = bangts.getDataFromSQL(str_thaotac, "kt");
      let ds_thaotac = await this.huonggiao_ldv(this.quytrinh_id, LOAI_DV.TTKD_NGHIEMTHU + ',' + LOAI_DV.TTDH_NGHIEMTHU)
      let donviId = this.$root.token.getDonViID()
      if (ds_thaotac != null && ds_thaotac.length > 0) {
        for (var thaotac of ds_thaotac) {
          if (parseInt(thaotac['loaidv_dich_id']) == LOAI_DV.TTKD_NGHIEMTHU) {
            let row = {}
            row.HDTB_ID = this.hdtb_id
            row.LOAIDV_ID = LOAI_DV.TTKD_NGHIEMTHU
            row.DONVI_ID = donviId
            row.KIEUDV_ID = 2
            this.dsHDTBDV.push(row)
          }
          if (parseInt(thaotac['loaidv_dich_id']) == LOAI_DV.TTDH_NGHIEMTHU) {
            let vluong_id = parseInt(thaotac['luong_id'])
            // string str_dv = "";
            // str_dv += " Select donvi_id ";
            // str_dv += " From " + DatabaseConstants.DB2 + ".luong_dv";
            // str_dv += " Where luong_id = " + vluong_id;
            // DataSet ds_dvi = new DataSet();
            // ds_dvi = bangts.getDataFromSQL(str_dv, "dv");
            let ds_dvi = await this.luong_dv(vluong_id)
            if (ds_dvi != null && ds_dvi.length > 0) {
              let l_dv_id = parseInt(ds_dvi[0]['donvi_id'])
              let row = {}
              row.HDTB_ID = this.hdtb_id
              row.LOAIDV_ID = LOAI_DV.TTDH_NGHIEMTHU
              row.DONVI_ID = l_dv_id
              row.KIEUDV_ID = 2
              this.dsHDTBDV.push(row)
            }
          }
        }
      }
      return this.dsHDTBDV
    },
    async TaoDuLieu_HDBANCHEO(themmoi) {
      console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 3645 ~ TaoDuLieu_HDBANCHEO ~ themmoi', themmoi)
      this.dsHDBC = []
      let ds = []
      if (!this.kenh_trang && !this.chi_giao_net){
        ds = await this.lay_thongso_thuebao_thuebaoid_bc(this.tinh.tinhthicong_id, this.thuebao_id_tc)
        if(!ds||ds.length==0){
          throw new Error('Không lấy được thông số thuê bao bán chéo')
        }
      }
       
      let rowHDBC = {}
      rowHDBC.HDTB_BAN = this.hdtb_id
      rowHDBC.TINH_THICONG = this.tinh.tinhthicong_id
      rowHDBC.TINH_BAN = this.$root.token.getPhanVungID() //tt_nd.tinh_id;
      rowHDBC.TRANG_THAI = 0
      rowHDBC.LOAIHD_BANCHEO = 1

      rowHDBC.THUEBAO_ID_BAN = this.thuebao_id
      rowHDBC.THUEBAO_ID_THICONG = this.thuebao_id_tc
      if (!this.kenh_trang && !this.chi_giao_net) {
        rowHDBC.THANHTOAN_ID_TC = parseInt(ds[0]['thanhtoan_id'])
        rowHDBC.KHACHHANG_ID_TC = parseInt(ds[0]['khachhang_id'])
      } else {
        rowHDBC.THANHTOAN_ID_TC = 0
        rowHDBC.KHACHHANG_ID_TC = 0
      }
      rowHDBC.THANHTOAN_ID_BAN = await this.mapthanhtoan_id(this.thuebao_id) //Convert.ToInt64(checkdata.MAP_ID("thanhtoan_id", DatabaseConstants.DB2 + ".db_thuebao", "where thuebao_id =" + thuebao_id));
      rowHDBC.KHACHHANG_ID_BAN = this.khachhang_id
      let rowHDBCStr=JSON.stringify(rowHDBC)
      this.dsHDBC.push(JSON.parse(rowHDBCStr))

      this.dsHDTBBC = []
      let rowHDTBBC = {}
      if (!this.kenh_trang && !this.chi_giao_net) {
        //let thuebao_tc_id = tcbancheo.LAY_THUEBAOID_THICONG(tinh, thuebao_id);
        let thuebao_tc_id = await this.lay_thuebaoid_thicong(this.tinh.tinhthicong_id, this.thuebao_id)
        //ds = tcbancheo.LAY_DANHBA_THEO_THUEBAOID(tinh, parseInt(thuebao_tc_id), parseInt(cboDichVuVT), 0, 2);
        ds = await this.lay_danhba_theo_thuebaoid(this.tinh.tinhthicong_id, thuebao_tc_id, this.cboDichVuVT, 0, 2)
        if (ds == null || ds.length == 0) {
          throw new Error('Không tìm thấy thuê bao tại ' + this.tinh.matinh + ' ứng với dịch vụ ' + this.cboDichVuVT)
          //this.ShowError('Không tìm thấy thuê bao tại ' + this.tinh.matinh + ' ứng với dịch vụ ' + this.cboDichVuVT)
          return
        } else {
          rowHDTBBC = ds[0]
          this.UpperObjectKeys(rowHDTBBC)
        }
      } else {
        if (this.dsHDTB != null && this.dsHDTB.length > 0) {
          rowHDTBBC = this.dsHDTB[0]
          this.UpperObjectKeys(rowHDTBBC)
        }
        // rowHDTBBC.thuebao_id = null
        rowHDTBBC.THUEBAO_ID = null
        rowHDTBBC.DONVI_ID = null
        rowHDTBBC.MA_TB = this.txtMaTB
      }
      rowHDTBBC.LOAITB_ID = this.loaitb_id
      rowHDTBBC.DICHVUVT_ID = this.dichvuvt_id
      rowHDTBBC.HDTB_ID = this.hdtb_id
      rowHDTBBC.HDKH_ID = this.hdkh_id
      rowHDTBBC.DOITUONG_ID = this.thongsoTc.doituong_id
      rowHDTBBC.MUCUOCTB_ID = this.thongsoTc.muccuoctb_id
      rowHDTBBC.TTHD_ID = 1
      rowHDTBBC.QUYTRINH_ID = this.quytrinh_tc_tn
      rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn
      //thuebao_id_tc = parseInt(rowHDTBBC.THUEBAO_ID);
      rowHDTBBC.KIEULD_ID = this.thongsoTc.kieuld_id
      rowHDTBBC.NGUOI_CN = this.nguoi_cn
      rowHDTBBC.NGAY_CN = moment().format('DD/MM/YYYY HH:mm:ss') // tt_nd.ngay_cn;
      rowHDTBBC.MAY_CN = this.may_cn //tt_nd.may_cn;
      rowHDTBBC.IP_CN = this.ip_cn // tt_nd.ip;
      rowHDTBBC.GHICHU = this.txtGhiChu // CommonFunction.StringToString(txtGhiChu.Trim());
      //rowHDTBBC.MUCUOC_TB = parseInt(new CheckData().MAP_ID(tinh, "cuoc_tb", "CSS_" + tinh.matinh + ".muccuoc_tb", "where mucuoctb_id = " + thongsoTc.muccuoctb_id));
      rowHDTBBC.MUCUOC_TB = await this.mapcuoc_tb()
      rowHDTBBC.STATUS = TRANGTHAI_DONGBO.CHUADONGBO
      rowHDTBBC.TEN_TB = this.txtTenTB

      
      let rowHDTBBCStr=JSON.stringify(rowHDTBBC)
      //this.dsHDTBBC.push(rowHDTBBC)
      this.dsHDTBBC.push(JSON.parse(rowHDTBBCStr))
    },
    async TaoDulieu_HDBCMGWAN(themmoi) {
      try {
        console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 3708 ~ TaoDulieu_HDBCMGWAN ~ themmoi', themmoi)
        this.dsHDBCMGWAN = []
        let rowHDBCMGWAN = {}
        if (!this.kenh_trang && !this.chi_giao_net) {
          //cmUlt.CopyRow(tcbancheo.LAY_DS_DB_MEGAWAN_THEO_TBID(tinh, thuebao_id_tc)[0], rowHDBCMGWAN);
          rowHDBCMGWAN = await this.lay_ds_db_megawan_theo_tbid_bc(this.tinh.tinh_id, this.thuebao_id_tc)
          this.UpperObjectKeys(rowHDBCMGWAN)
        } else {
          rowHDBCMGWAN.MADOICAP = -1
        }

        rowHDBCMGWAN.HDTB_ID = this.hdtb_id
        rowHDBCMGWAN.LOAIMD_ID = this.thongsoTc.loaimd_id
        rowHDBCMGWAN.ID_ERP = this.txtMaERP
        rowHDBCMGWAN.MA_BSS = this.txtMaBSS
        if (this.thongsoTc.chuquan_id == 0) rowHDBCMGWAN.CHUQUAN_ID = null
        else rowHDBCMGWAN.CHUQUAN_ID = this.thongsoTc.chuquan_id

        if (this.thongsoTc.loaikenh_id == 0) rowHDBCMGWAN.LOAIKENH_ID == null
        else rowHDBCMGWAN.LOAIKENH_ID = this.thongsoTc.loaikenh_id

        //if (thongsoTc.LOAIMD_ID==0) rowHDBCMGWAN.se
        if (this.thongsoTc.thietbidc_id == 0) rowHDBCMGWAN.THIETBIDC_ID = null
        else rowHDBCMGWAN.THIETBIDC_ID = this.thongsoTc.thietbidc_id

        if (this.thongsoTc.thoihan_id == 0) rowHDBCMGWAN.THOIHAN_ID = null
        else rowHDBCMGWAN.THOIHAN_ID = this.thongsoTc.thoihan_id

        if (this.thongsoTc.tocdo_id == 0) rowHDBCMGWAN.TOCDO_ISP = null
        else rowHDBCMGWAN.TOCDO_ID = this.thongsoTc.tocdo_id

        if (this.thongsoTc.tocdo_isp == 0) rowHDBCMGWAN.TOCDO_ISP = null
        else rowHDBCMGWAN.TOCDO_ISP = parseInt(this.thongsoTc.tocdo_isp)

        if (this.thongsoTc.tocdo_nix == 0) rowHDBCMGWAN.TOCDO_NIX = null
        else rowHDBCMGWAN.TOCDO_NIX = parseInt(this.thongsoTc.tocdo_nix)

        if (this.thongsoTc.tocdo_pir_id == 0) rowHDBCMGWAN.TOCDO_PIR_ID = null
        else rowHDBCMGWAN.TOCDO_PIR_ID = parseInt(this.thongsoTc.tocdo_pir_id)

        if (this.thongsoTc.trangbi_id == 0) rowHDBCMGWAN.TRANGBI_ID = null
        else rowHDBCMGWAN.TRANGBI_ID = this.thongsoTc.trangbi_id
        rowHDBCMGWAN.CHUQUANLD_ID = this.thongsoTc.chuquanld_id

        if (parseInt(this.cboLoaihinhTB) == LoaiHinhTB.MGW_INTERNET_TRUCTIEP||parseInt(this.cboLoaihinhTB)==LoaiHinhTB.NB_INTERNETTT||parseInt(this.cboLoaihinhTB)==259||parseInt(this.cboLoaihinhTB)==260) {

          if (this.PhanLoaiMGW != null && this.cboPhanLoaiMGW != null) rowHDBCMGWAN.PHANLOAI_ID = parseInt(this.cboPhanLoaiMGW)

          if (this.cboPhanTachTD != null && this.cboPhanTachTD != null) rowHDBCMGWAN.PHANTACH_TD = parseInt(this.cboPhanTachTD)

          if (!this.txtSoLuong_IP != '') rowHDBCMGWAN.SL_IP = parseInt(this.txtSoLuong_IP)
          else rowHDBCMGWAN.SL_IP = 0

          if (this.txtCuoc_IP != null && this.txtCuoc_IP != '') rowHDBCMGWAN.CUOC_IP = parseInt(this.txtCuoc_IP)
          else rowHDBCMGWAN.CUOC_IP = 0

          if (this.chkTocDo_NIX) {
            if (this.txtCuoc_NIX != '') rowHDBCMGWAN.CUOC_NIX = parseInt(this.txtCuoc_NIX)
            else rowHDBCMGWAN.CUOC_NIX = 0
          } else {
            rowHDBCMGWAN.CUOC_NIX = null
          }

          if (this.chkTocDo_ISP) {
            if (this.txtCuoc_ISP != null && this.txtCuoc_ISP != '') rowHDBCMGWAN.CUOC_ISP = parseInt(this.txtCuoc_ISP)
            else rowHDBCMGWAN.CUOC_ISP = 0
          } else {
            rowHDBCMGWAN.CUOC_ISP = null
          }
        } else {
          rowHDBCMGWAN.SL_IP = null
          rowHDBCMGWAN.CUOC_IP = null

          rowHDBCMGWAN.CUOC_NIX = null
          rowHDBCMGWAN.CUOC_ISP = null
          rowHDBCMGWAN.PHANLOAI_ID = null
          rowHDBCMGWAN.PHANTACH_TD = null
        }

        if (this.cboTinh_KN_Moi != '0') rowHDBCMGWAN.TINH_KN_ID = parseInt(this.cboTinh_KN_Moi)
        else rowHDBCMGWAN.TINH_KN_ID = null

        rowHDBCMGWAN.HUONG_KN = this.vhuongkn
        rowHDBCMGWAN.MA_PL = this.txtMaHD_TTKD
        if (this.chkCongVan) {
          rowHDBCMGWAN.MA_CV = this.txtCongVan
          rowHDBCMGWAN.NGAY_CV = this.dtpNgayCV
        } else {
          rowHDBCMGWAN.MA_CV = null
          rowHDBCMGWAN.NGAY_CV = null
        }
        rowHDBCMGWAN.SITE_ID = this.txtSiteID
        rowHDBCMGWAN.DIACHI_KN = this.txtDiaChi_KN

        if (this.chkDoiTac) {
          rowHDBCMGWAN.DOITAC_ID = parseInt(this.cboDoiTac)
          if (this.txtCuocTKDT != '') rowHDBCMGWAN.CUOC_TKDT = this.txtCuocTKDT
          else rowHDBCMGWAN.CUOC_TKDT = 0
          if (this.txtCuocTCDT != '') rowHDBCMGWAN.CUOC_TCDT = this.txtCuocTCDT
          // parseInt(Common.Utilities.CommonFunction.Unformat_tien(txtCuocTCDT));
          else rowHDBCMGWAN.CUOC_TCDT = 0

          rowHDBCMGWAN.MAHD_DT = this.txtMaHD_DT
        } else {
          rowHDBCMGWAN.DOITAC_ID = null
          rowHDBCMGWAN.CUOC_TKDT = 0
          rowHDBCMGWAN.CUOC_TCDT = 0
          rowHDBCMGWAN.MAHD_DT = null
        }

        if (this.txtCuocHT != '') rowHDBCMGWAN.CUOC_HT = this.txtCuocHT
        // parseInt(Common.Utilities.CommonFunction.Unformat_tien(txtCuocHT));
        else rowHDBCMGWAN.CUOC_HT = 0
        if (this.txtCuocTBI != '') rowHDBCMGWAN.CUOC_TBI = this.txtCuocTBI
        // parseInt(Common.Utilities.CommonFunction.Unformat_tien(txtCuocTBI));
        else rowHDBCMGWAN.CUOC_TBI = 0
        if (this.txtCuocTC != '') rowHDBCMGWAN.CUOC_TC = this.txtCuocTC
        // parseInt(Common.Utilities.CommonFunction.Unformat_tien(txtCuocTC.Text));
        else rowHDBCMGWAN.CUOC_TC = 0
        if (this.txtCuocTK != '') rowHDBCMGWAN.CUOC_TK = this.txtCuocTK
        // parseInt(Common.Utilities.CommonFunction.Unformat_tien(txtCuocTK.Text));
        else rowHDBCMGWAN.CUOC_TK = 0
        let rowHDBCMGWANStr=JSON.stringify(rowHDBCMGWAN)
        this.dsHDBCMGWAN.push(JSON.parse(rowHDBCMGWANStr))
        //this.dsHDBCMGWAN.push(rowHDBCMGWAN)
        let hdtb_id = this.hdtb_id
        this.dsHDBCMGWAN.forEach(function (row) {
          row.HDTB_ID = hdtb_id
        })
        return this.dsHDBCMGWAN
      } catch (err) {
        console.log('TaoDulieu_HDBCMGWAN-err', err)
      }
    },
    async HT_DS_KhuyenMai_Combobox() {
      let itocdo_id = 0
      let itrangbi_id = 0 //Default: KH tự trang bị
      let imuccuoc_id = 0
      let iloaitb_id = 0
      if (this.KieuLD == null || this.KieuLD.length <= 0) return
      if (this.cboDichVuVT != null) {
        this.dichvuvt_id = this.cboDichVuVT
      }
      if (this.cboTrangBiMEGAWAN != null) {
        this.itrangbi_id = parseInt(this.cboTrangBiMEGAWAN)
      }
      switch (this.dichvuvt_id) {
        case DichVuVienThong.MEGAWAN:
          if (this.cboTocDo == null) {
            return
          }
          itocdo_id = parseInt(this.cboTocDo)
          break
        default:
          itocdo_id = 0
          break
      }
      itrangbi_id = this.trangbi_id
      imuccuoc_id = 0
      if (this.cboLoaihinhTB != null) iloaitb_id = parseInt(this.cboLoaihinhTB)

      let data = await this.lay_khuyenmai_hdtb(iloaitb_id, parseInt(this.cboKieuLD), this.phuongld_id, 1 /*KHOANMUC_TT.KMTT_DATMOI*/, this.phold_id, this.apld_id, this.khuld_id, itrangbi_id, this.doituong_id, itocdo_id, imuccuoc_id, 0)
      if (data != null && data.length > 0) {
        this.KhuyenMaiRaw = data
        this.KhuyenMai = data.map((x) => ({ id: x.khuyenmai_id, text: x.ten_km }))
      } else {
        this.KhuyenMai = []
      }

      // cboKhuyenMaiMember = 'khuyenmai_id'
      // cboKhuyenMai.DisplayMember = 'ten_km'
      if (this.KhuyenMai == null || this.KhuyenMai.length == 0) {
        this.chkKhuyenMai = false
        this.chkKhuyenMai_Enabled = false
      } else {
        this.chkKhuyenMai_Enabled = true
        this.chkKhuyenMai = false
      }
    },
    async tsbtnGhiLai_Click() {
      console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 3848 ~ tsbtnGhiLai_Click ~ tsbtnGhiLai_Click')
      try {
        if ((this.kenh_trang || this.chi_giao_net) && (this.thongsoBan.muccuoctb_id == 0 || this.thongsoTc.muccuoctb_id == 0)) {
          if (this.kenh_trang) this.ShowError('Bạn chưa chọn thông số cho kênh trắng. Ấn vào kiểu yêu cầu để lấy thông tin')
          else this.ShowError('Bạn chưa chọn thông số. Ấn vào kiểu yêu cầu để lấy thông tin')
          return
        }
        this.Loading = true
        await this.CapNhat()
      } catch (err) {
        console.log('tsbtnGhiLai_Click-err', err)
      } finally {
        this.Loading = false
      }
    },
    async CapNhat() {
      console.log('tsbtnNhapMoi_Enabled', this.tsbtnNhapMoi_Enabled)
      console.log('KiemTraDL_KhachHang',this.KiemTraDL_KhachHang());
      
      try {
        
        if (!this.KiemTra_ThongSo()) return
        this.loading(true)
        if (!(await this.KiemTraDL_KhachHang())){
          this.loading(false)
          return
        }
        this.loading(true)
        if (!(await this.KiemTraDL_ThueBao())){
          this.loading(false)
          return
        }
        if (this.tsbtnNhapMoi_Enabled == false) {
          this.loading(true)
          if (!(await this.kiemtra_db_khi_themmoi(this.txtMaTB, this.khachhang_id, this.thuebao_id, this.dichvuvt_id))) {
            this.ShowError('Thuê bao không tồn tại trong danh bạ, không cập nhật được ')
            this.loading(false)
            return
          }
          this.loading(true)
          if (await this.kiemtra_thuebao_lap_hopdong_khac(this.txtMaTB, '0', parseInt(this.cboDichVuVT), LoaiHopDong.THAYDOI_THONGSO_MEGAWAN)){
            this.loading(false)
            return
          }
          // DataSet dshg = bangts.LayHuongGiao_TiepNhan_V2(LoaiHopDong.THAYDOI_THONGSO_MEGAWAN, Convert.ToInt32(cboLoaihinhTB.SelectedValue), Convert.ToInt32(cboTocDo.SelectedValue), 0, Convert.ToInt32(cboKieuLD.SelectedValue), Convert.ToInt32(cboLoaiKenh.SelectedValue), Convert.ToInt32(cboDichVuVT.SelectedValue));
          // LayHuongGiao_TiepNhan_V2: async function (loaihd_id, loaitb_id, tocdo_id, muccuoc_id, kieuld_id, vloaikenh_id, vdichvuvt_id)
          this.loading(true)
          let dshg = await this.LayHuongGiao_TiepNhan_V2(LoaiHopDong.THAYDOI_THONGSO_MEGAWAN,parseInt(this.cboLoaihinhTB), parseInt(this.cboTocDo),0, parseInt(this.cboKieuLD), parseInt(this.cboLoaiKenh), parseInt(this.cboDichVuVT))
          this.loading(false)
          console.log('LayHuongGiao_TiepNhan_V2:result',dshg)
          this.quytrinh_id = 0
          this.huonggiao_tn = 0
          if (dshg == null || dshg.length==0 || dshg[0]['huonggiao_id'] == null) {
            this.ShowError('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
            return
          }
          this.huonggiao_tn = parseInt(dshg[0]['huonggiao_id'])
          this.quytrinh_id = parseInt(dshg[0]['quytrinh_id'])
          console.log('tsbtnNhapMoi thongsoTc', this.thongsoTc)
          //let rs_qt = await this.LayHuongGiao_TiepNhan_Tinh(LoaiHopDong.THAYDOI_THONGSO_MEGAWAN, parseInt(this.cboLoaihinhTB), parseInt(this.cboTocDo), 0, parseInt(this.cboKieuLD))
          this.loading(true)
          let rs_qt = await this.LayHuongGiao_TiepNhan_Tinh(LoaiHopDong.THAYDOI_THONGSO_MEGAWAN, parseInt(this.cboLoaihinhTB), parseInt(this.thongsoTc.tocdo_id), this.thongsoTc.muccuoc_id?this.thongsoTc.muccuoc_id:0, parseInt(this.thongsoTc.kieuld_id))
          this.loading(false)
         /*
            .LayHuongGiao_TiepNhan(tinh, LoaiHopDong.THAYDOI_THONGSO_MEGAWAN, Convert.ToInt32(cboLoaihinhTB.SelectedValue),
                                                                 Convert.ToInt32(thongsoTc.tocdo_id.ToString()), Convert.ToInt32(thongsoTc.muccuoc_id.ToString()), thongsoTc.kieuld_id);
          */
          if (rs_qt==null || rs_qt.length==0 || rs_qt[0]['huonggiao_id'] == null)
          {
              this.ShowError("Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý")
              return;
          }
          this.quytrinh_tc_tn = 0
          this.huonggiao_tc_tn = 0
          this.huonggiao_tc_tn = parseInt(rs_qt[0]['huonggiao_id'])
          this.quytrinh_tc_tn = parseInt(rs_qt[0]['quytrinh_id'])

        } else {
          this.loading(true)
          let dtTemp = await this.lay_ds_hopdong_thuebao_theo_hdkh(this.hdkh_id)
          this.loading(false)
          let dr = dtTemp.filter((x) => x.dichvuvt_id == this.dichvuvt_id && x.ma_tb == this.txtMaTB)
          if (dr == null || dr.Length <= 0) {
            this.ShowError('Thuê bao không tồn tại trong hợp đồng, không thể cập nhật. Bạn phải Thêm thuê bao!')
            return
          }
          this.loading(true)
          if (!(await this.kiemtra_db_khi_themmoi(this.txtMaTB, this.khachhang_id, this.thuebao_id, this.dichvuvt_id))) {
            this.ShowError('Thuê bao không tồn tại trong danh bạ, không cập nhật được ')
            this.loading(false)
            return
          }
          this.loading(true)
          let dshg = await this.LayHuongGiao_TiepNhan_V2(LoaiHopDong.THAYDOI_THONGSO_MEGAWAN,parseInt(this.cboLoaihinhTB), parseInt(this.cboTocDo),0, parseInt(this.cboKieuLD), parseInt(this.cboLoaiKenh), parseInt(this.cboDichVuVT))
          this.loading(false)
          console.log('LayHuongGiao_TiepNhan_V2:result',dshg)
          this.quytrinh_id = 0
          this.huonggiao_tn = 0
          if (dshg == null || dshg.length==0 || dshg[0]['huonggiao_id'] == null) {
            this.ShowError('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
            return
          }
          this.huonggiao_tn = parseInt(dshg[0]['huonggiao_id'])
          this.quytrinh_id = parseInt(dshg[0]['quytrinh_id'])
          this.loading(true)
          let rs_qt = await this.LayHuongGiao_TiepNhan_Tinh(LoaiHopDong.THAYDOI_THONGSO_MEGAWAN, parseInt(this.cboLoaihinhTB), parseInt(this.cboTocDo), 0, parseInt(this.cboKieuLD))
          this.loading(false)
          if (rs_qt==null||rs_qt.length==0|| rs_qt[0]['huonggiao_id'] == null) {
            this.ShowError('Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý')
            return
          }
          this.quytrinh_tc_tn = 0
          this.huonggiao_tc_tn = 0
          this.huonggiao_tc_tn = parseInt(rs_qt[0]['huonggiao_id'])
          this.quytrinh_tc_tn = parseInt(rs_qt[0]['quytrinh_id'])
        }
        this.loading(true)
        await this.TaoDuLieu(this.tsbtnNhapMoi_Enabled?false:true)
        this.loading(false)
        console.log('TaoDuLieu', 'thongsoTc', this.thongsoTc, 'thongsoBan', this.thongsoBan)
        let p_parameters = {
          ACTION_TYPE: this.tsbtnNhapMoi_Enabled ? 'U' : 'A',
          MA_TB: this.txtMaTB,
          DICHVUVT_ID: this.cboDichVuVT,
          KHACHHANG_ID: this.khachhang_id,
          THUEBAO_ID: this.thuebao_id,
          HDTB_ID: this.hdtb_id,
          MA_KH: this.txtMaKH,
          TEN_KH: this.txtTenKH,
          MA_HD: this.txtMaHD,
          //BATBUOC_SOTHANG_CK: '',
          //HUONGGIAO_TN: this.huonggiao_tn,
          LOAITB_ID: this.loaitb_id,
          TOCDO_ID: this.cboTocDo,
          TOCDO_CU_ID: this.cboTocDoCu,

          KIEULD_ID: this.cboKieuLD,
          LOAIKENH: this.cboLoaiKenh,
          MUCCUOC_ID: this.thongsoTc.muccuoc_id == null ?0: this.thongsoTc.muccuoc_id,
          TINHTHICONG: this.tinh.tinhthicong_id,
          KIEUTRAMOI: '',
          CONGNGHE_MOI_ID: this.cboCongNghe_Moi,
          CONGNGHE_CU_ID: this.cboCongNghe_Cu,
          CUOC_SD: '',
          TINHTHICONG_ID: this.tinh != null ? this.tinh.tinhthicong_id : null,
          CUOC_TBI: '',
          KIEUTRA_MOI_ID: '',
          KIEUTRA_CU_ID: '',
          TOCDOTHUC_MOI_ID: '',
          TOCDOTHUC_CU_ID: '',
          HD_KHACHHANG: this.dsHDKH, // ja_hd_khachhang
          // HD_THUEBAO1: this.dsHDTB, //
          HD_THUEBAO: this.dsHDTBTest, //
          THONGTIN_TT: this.dsTTTT,
          CT_TIENHD: this.dsCTTHD,
          KHUYENMAI_HDTB: this.dsKM_HDTB,
          HDTB_MGWAN: this.dsHDTB_MEWAN, // ja_hdtb_mewan
          HDTB_DV: this.dsHDTBDV,
          HD_BANCHEO: this.dsHDBC,
          HDTB_BANCHEO: this.dsHDTBBC,
          HDBC_MGWAN: this.dsHDBCMGWAN,
          HDBC_ADSL: this.dsHDBCADSL,
          HDBC_CD: this.dsHDBCCD,
          HDBC_TSL: this.dsHDBCTSL,
          HDBC_IMS: this.dsHDBCIMS,
          DANGKY_DVGT: this.dsDANGKYDVGT
        }
        console.log('p_parameters', p_parameters)
        this.loading(true)
        let result = await this.fn_thaydoi_thongso_wan_bc_capnhat(p_parameters)
        this.loading(false)
        console.log('🚀fn_thaydoi_thongso_wan_bc_capnhat ~ result', result)
        if (result != null && result.toUpperCase() == 'OK') {
          await this.SetButton(3)
          this.ShowSuccess('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
        } else {
          this.ShowError(result)
        }
        this.loading(true)
        let dataHDTB = await this.lay_ds_hopdong_thuebao_theo_hdkh(this.hdkh_id)
        await this.HienThiDanhSacHDTB(dataHDTB)
        this.loading(false)
      } catch (err) {
        this.loading(false)
        console.log(err)
        this.ShowError(`${err.message}`)
      }
    },
    KiemTra_ThongSo() {
      let ketqua = false
      if (this.Capnhatthongso) {
        ketqua = true
      } else {
        this.ShowError('Bạn chưa chọn các thông số để lập hợp đồng')
        ketqua = false
      }
      return ketqua
    },
    async KiemTraDL_KhachHang() {
      console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 3975 ~ KiemTraDL_KhachHang ~ KiemTraDL_KhachHang')
      let kq_bc = await this.kiemtra_hd_bancheo_tc(this.thuebao_id, LoaiHopDong.THAYDOI_THONGSO_MEGAWAN)
      if (kq_bc != 'OK') {
        this.ShowError(kq_bc)
        return false
      }

      if (this.txtTenKH == '') {
        this.ShowError('Hãy nhập tên khách hàng !')
        this.$refs.reftxtTenKH.focus()
        return false
      }

      if (this.txtMaKH == '' && this.khachhang_id != 0) {
        this.ShowError('Kiểm tra lại mã khách hàng !')
        this.$refs.reftxtMaKH.focus()
        return false
      }
      //Hieutc: 11.10.2011: Kiểm tra mã khách hàng đã theo đúng chuẩn chưa
      if (this.txtMaKH != '') {
        if ((await this.kiemtra_makh_mtt(this.txtMaKH, 1, LoaiHopDong.THAYDOI_THONGSO_MEGAWAN)) == 0) {
          this.ShowError('Mã khách hàng không đúng chuẩn quy định của Viễn thông tỉnh. Bạn hãy kiểm tra lại!')
          this.$refs.reftxtMaKH.focus()
          return false
        }
      }
      if (this.txtMaGD != '') {
        if ((await this.kiemtra_makh_mtt(this.txtMaGD, 3, LoaiHopDong.THAYDOI_THONGSO_MEGAWAN)) == 0) {
          this.ShowError('Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh. Bạn hãy kiểm tra lại!')
          this.$refs.reftxtMaGD.focus()
          return false
        }
      }

      if (this.chkCTV && this.txtCTV == '' && this.ctv_id == 0) {
        this.ShowError('Bạn chưa chọn cộng tác viên !')
        this.$refs.reftxtCTV.focus()
        return false
      }

      if (this.chkNguoiGT && this.txtNguoiGT == '' && this.nguoigt_id == 0) {
        this.ShowError('Bạn chưa chọn người giới thiệu !')
        this.$refs.reftxtNguoiGT.focus()
        return false
      }

      if (parseInt(this.cboKieuLD) == this.KieuLapDat.HNI_DOI_WANIP_LAN_IP) {
        if (this.txtWanIP == '') {
          this.ShowError('Bạn chưa nhập thông tin Wan IP !')
          this.$refs.reftxtWanIP.focus()
          return false
        }
        if (this.txtLanIP == '') {
          this.ShowError('Bạn chưa nhập thông tin Lan IP !')
          this.$refs.reftxtLanIP.focus()
          return false
        }
      }
      return true
    },
    async KiemTraDL_ThueBao() {
      if (this.txtMaHD == '' && this.khachhang_id != 0) {
        this.ShowError('Hãy nhập mã hợp đồng !')
        this.$refs.reftxtMaHD.focus()
        return false
      }
      if (this.cboLoaiTBiWan == null) {
        this.ShowError('Thiếu dữ liệu Loại thiết bị !')
        this.$refs.cboLoaiTBiWan.focus()
        return false
      }
      if (this.txtTenTB == '') {
        this.ShowError('Hãy nhập tên thuê bao !')
        this.$refs.reftxtTenTB.focus()
        return false
      }
      if (parseInt(this.cboLoaihinhTB) == LoaiHinhTB.MGW_INTERNET_TRUCTIEP||parseInt(this.cboLoaihinhTB) == LoaiHinhTB.NB_INTERNETTT) {
        if (parseInt(this.cboPhanTachTD) == 1) {
          // Có phân tách
          let k_tocdo_id = 0
          let k_tocdo_nix = 0
          let k_tocdo_isp = 0
          k_tocdo_id = parseInt(this.cboTocDo)
          if (k_tocdo_id == 0) {
            this.ShowError('Tốc độ Tổng phải khác 0 !')
            //this.$refs.cboTocDo.focus()
            this.Focus('cboTocDo')
            return false
          }

          if (this.chkTocDo_NIX == false && this.chkTocDo_ISP == false) {
            this.ShowError('Bạn phải chọn tốc độ Nix hoặc Isp (Trường hợp phân tách tốc độ).')
            return false
          }

          if (this.chkTocDo_NIX == true) {
            k_tocdo_nix = parseInt(this.cboTocDo_NIX)
            if (k_tocdo_nix == 0) {
              this.ShowError('Tốc độ NIX phải khác 0 !')
              //this.$refs.cboTocDo_NIX.focus()
              this.Focus('cboTocDo_NIX')
              return false
            }
          }

          if (this.chkTocDo_ISP == true) {
            k_tocdo_isp = parseInt(this.cboTocDo_ISP)
            if (k_tocdo_isp == 0) {
              this.ShowError('Tốc độ ISP phải khác 0 !')
              //this.$refs.cboTocDo_NIX.focus()
              this.Focus('cboTocDo_NIX')
              return false
            }
          }

          // kiểm tra tốc độ tổng
          // LapHopDongFacade2 laphd2 = new LapHopDongFacade2();
          // let kq = laphd2.kiemtra_tocdo_internet_tt(k_tocdo_id, k_tocdo_nix, k_tocdo_isp)
          // if (kq != 'OK') {
          //   this.ShowError(kq)
          //   return false
          // }
        } else {
          if (parseInt(this.cboTocDo) == 0) {
            this.ShowError('Tốc độ Tổng phải khác 0 !')
            //this.$refs.cboTocDo.focus()
            this.Focus('cboTocDo')
            return false
          }

          if (this.chkTocDo_NIX == true || this.chkTocDo_ISP == true) {
            this.ShowError('Bạn không được chọn tốc độ Nix và Isp (Trường hợp không phân tách tốc độ).')
            return false
          }
        }

        if (this.txtSoLuong_IP == '') {
          this.ShowError('Hãy nhập Số lượng IP !')
          this.$refs.txtSoLuong_IP.focus()
          return false
        }
      }

      // var kq_1 = tchopdong.GET_VALUE_FUNC('{?DB2}.KT_TB_CHUYENDOI_LHTB', 'vthuebao_id', thuebao_id, 'vquytrinh_id', quytrinh_id)

      // if (kq_1 != 'OK') {
      //   this.ShowError(kq_1)
      //   return false
      // }

      if (parseInt(this.cboDichVuVT) == DichVuVienThong.MEGAWAN || parseInt(this.cboDichVuVT) == DichVuVienThong.METRONET) {
        if (parseInt(this.cboLoaihinhTB) != LoaiHinhTB.MGW_INTERNET_TRUCTIEP && parseInt(this.cboLoaihinhTB) != LoaiHinhTB.NB_INTERNETTT) {
          if (parseInt(this.cboTocDo) == 0) {
            this.ShowError('Tốc độ CIR phải khác 0 !')
            //this.$refs.cboTocDo.focus()
            this.Focus('cboTocDo')
            return false
          }
        }
      }

      // hoangpkn : 13/12/2018. Kiểm tra loại hình có bị thay đổi hay không ?
      //let _loaitb_id = parseInt(checkdata.MAP_ID('loaitb_id', DatabaseConstants.DB2 + '.db_thuebao', 'where thuebao_id = ' + thuebao_id))
      let _loaitb_id = parseInt(await this.maploaitb_id(this.thuebao_id))
      if (_loaitb_id != parseInt(this.cboLoaihinhTB)) {
        this.ShowError("Không đúng thông tin loại hình của thuê bao '" + this.txtMaTB + "'")
        return false
      }
      return true
    },
    async lay_ds_hopdong_tb_theo_hdtb_id(hdtb_id) {
      let input = { in_hdtb_id: hdtb_id }
      let data = this.GetData(await api.lay_ds_hopdong_tb_theo_hdtb_id(this.axios, input))
      return data
    },
    async check_hdkh_cha_id() {
      console.log('call check_hdkh_cha_id')
      try {
        if (this.$route.query.hdkh_cha_id != null && this.$route.query.hdkh_cha_id != '') {
          let hdkhchaid = parseInt(this.$route.query.hdkh_cha_id)
          if (hdkhchaid > 0) {
            this.hdkh_cha_id = hdkhchaid
            await this.LoadTuHopDongTuVan(hdkhchaid)
          }
        }
      } catch (err) {
        this.ShowError(`LoadTuHopDongTuVan-Err:${err.message}`)
      }
    },
    async LoadTuHopDongTuVan(hdkh_cha_id) {
      try {
        console.log(`LoadTuHopDongTuVan`, hdkh_cha_id)
        this.hdkh_cha_id = hdkh_cha_id
        /*
                let filter = string.Format("dichvuvt_id = 8 and ma_tb is not null and khachhang_id in (select khachhang_id from {0}.hd_khachhang WHERE hdkh_id = {1})"
                    , DatabaseConstants.DB2
                    , hdkh_cha_id);
                let dt = fcd.getData(DatabaseConstants.DB2, "db_thuebao", "ma_tb", "dichvuvt_id", filter, "").Tables[0];
          */
        // let dt = this.GetData(
        //   await api.lay_dulieu_bang_theo_dieukien(this.axios, {
        //     schema: 'css',
        //     table_name: 'db_thuebao',
        //     list_of_cols: 'ma_tb,dichvuvt_id',
        //     where: `dichvuvt_id = 8 and ma_tb is not null and khachhang_id in (select khachhang_id from css.hd_khachhang WHERE hdkh_id = ${hdkh_cha_id})`,
        //     order: ''
        //   })
        // )
        let dt = this.GetData(
          await api.sp_tt_db_thuebao(this.axios, {
            param: hdkh_cha_id,
            type: 1
          })
        )
        if (dt == null) {
          dt = this.GetData(await api.load_tuhopdong_tuvan(this.axios, { hdkhchaId: hdkh_cha_id }))
        }
        if (dt != null && dt.length > 0) {
          this.cboDichVuVT = dt[0]['dichvuvt_id']
          this.txtMaTB.Text = dt[0]['ma_tb']
          await this.txtMaTB_KeyPress()
        } else {
          this.ShowError('Khách hàng không có thuê bao MegaWan!')
          this.$emit('closeMegaWan', false)
          // this.Close();
        }
      } catch (ex) {
        this.ShowError(ex.message)
      }
    },
    async cboKieuLD_SelectedValueChanged() {
      if (this.KieuLD != null && this.KieuLD.length > 0) {
        if (this.cboKieuLD == null || this.cboKieuLD == '') return
        //if (cboKieuLD.ToString() == "System.Data.DataRowView") return;
        // bangts.HT_Kieu_LD_Combobox(cboKieuLD, dichvuvt_id, LoaiHopDong.THAYDOI_THONGSO_MEGAWAN);
        //Hiếu bổ sung lấy tiền ngày 17.09.2010
        // LayTienTheoKhoanMuc();

        //  #region Ẩn hiện đối tượng theo kiểu lắp đặt ( phục vụ HNI )
        // mặc định
        this.btnHuongKNWan_Enabled = true
        this.cboTocDo_Enabled = true
        this.chkTocDo_PIR_Enabled = true
        this.cboTocDo_Pir_Enabled = false
        this.cboLoaiKenh_Enabled = true
        this.chkTocDo_PIR = false
        this.txtThoiHanCKEnabled = true

        //this.chkTocDo_NIX = false;
        //this.chkTocDo_ISP = false;
        this.cboCongNghe_Moi_Enabled = false
        if (parseInt(this.cboLoaihinhTB) == LoaiHinhTB.MGW_INTERNET_TRUCTIEP||parseInt(this.cboLoaihinhTB) == LoaiHinhTB.NB_INTERNETTT) {
          this.chkTocDo_NIX_Enabled = true
          this.chkTocDo_ISP_Enabled = true
          //this.chkTocDo_NIX = false;
          //this.chkTocDo_ISP = false;
          this.txtCuoc_ISP_Enabled = false
          this.txtCuoc_NIX_Enabled = false
          this.txtSoLuong_IP_Enabled = true
          this.txtCuoc_IP_Enabled = true
        } else {
          this.chkTocDo_NIX_Enabled = false
          this.chkTocDo_ISP_Enabled = false
          this.chkTocDo_NIX = false
          this.chkTocDo_ISP = false
          this.cboTocDo_ISP_Enabled = false
          this.cboTocDo_NIX_Enabled = false
          this.txtCuoc_ISP_Enabled = false
          this.txtCuoc_NIX_Enabled = false
          this.txtSoLuong_IP_Enabled = false
          this.txtCuoc_IP_Enabled = false
          if (this.kt_load == false) {
            this.cboTocDo_ISP = this.TocDo[0].id
            this.cboTocDo_NIX = this.TocDo[0].id
          }
        }
        // end mặc định

        let _kieuld_id = parseInt(this.cboKieuLD)
        if (_kieuld_id == this.KieuLapDat.HNI_DOI_GIA_CUOC) {
          this.btnHuongKNWan_Enabled = false
          this.cboTocDo_Enabled = false
          this.chkTocDo_PIR_Enabled = false
          this.cboTocDo_Pir_Enabled = false
          this.cboLoaiKenh_Enabled = false
          this.chkTocDo_NIX_Enabled = false
          this.cboTocDo_NIX_Enabled = false
          this.chkTocDo_ISP_Enabled = false
          this.cboTocDo_ISP_Enabled = false
          this.txtLanIP_Enabled = false
          this.txtWanIP_Enabled = false
        }
        if (_kieuld_id == this.KieuLapDat.HNI_DOI_TOCDO) {
          this.btnHuongKNWan_Enabled = false
          this.cboTocDo_Enabled = true
          this.chkTocDo_PIR_Enabled = true
          this.cboTocDo_Pir_Enabled = false
          this.cboLoaiKenh_Enabled = false

          if (parseInt(this.cboLoaihinhTB) == LoaiHinhTB.MGW_INTERNET_TRUCTIEP||parseInt(this.cboLoaihinhTB) == LoaiHinhTB.NB_INTERNETTT) {
            this.chkTocDo_NIX_Enabled = true
            this.chkTocDo_ISP_Enabled = true
            this.cboTocDo_NIX_Enabled = true
            this.cboTocDo_ISP_Enabled = true
            //this.chkTocDo_NIX = false;
            //this.chkTocDo_ISP = false;
            this.txtCuoc_ISP_Enabled = false
            this.txtCuoc_NIX_Enabled = false
            this.txtSoLuong_IP_Enabled = true
            this.txtCuoc_IP_Enabled = true
          } else {
            this.chkTocDo_NIX_Enabled = false
            this.chkTocDo_ISP_Enabled = false
            this.chkTocDo_NIX = false
            this.chkTocDo_ISP = false
            this.cboTocDo_ISP_Enabled = false
            this.cboTocDo_NIX_Enabled = false
            this.txtCuoc_ISP_Enabled = false
            this.txtCuoc_NIX_Enabled = false
            this.txtSoLuong_IP_Enabled = false
            this.txtCuoc_IP_Enabled = false
            // this.cboTocDo_ISP = this.TocDo[0].id
            // this.cboTocDo_NIX = this.TocDo[0].id
            try {
              if (this.TocDo != null && this.TocDo.length > 0) {
                let id = this.TocDo[0].id
                this.cboTocDo_ISP = id
                this.cboTocDo_NIX = id
              }
            } catch (err) {}
          }
          this.txtLanIP_Enabled = false
          this.txtWanIP_Enabled = false
        }
        if (_kieuld_id == this.KieuLapDat.HNI_DOI_HUONG_KN) {
          this.btnHuongKNWan_Enabled = true
          this.cboTocDo_Enabled = false
          this.chkTocDo_PIR_Enabled = false
          this.cboTocDo_Pir_Enabled = false
          this.cboLoaiKenh_Enabled = false

          this.chkTocDo_NIX_Enabled = false
          this.chkTocDo_ISP_Enabled = false
          //this.chkTocDo_NIX = false;
          //this.chkTocDo_ISP = false;
          this.cboTocDo_ISP_Enabled = false
          this.cboTocDo_NIX_Enabled = false
          this.txtCuoc_ISP_Enabled = false
          this.txtCuoc_NIX_Enabled = false
          this.txtSoLuong_IP_Enabled = false
          this.txtCuoc_IP_Enabled = false
          //this.cboTocDo_ISP.SelectedIndex = 0;
          //this.cboTocDo_NIX.SelectedIndex = 0;
          try {
            if (this.TocDo != null && this.TocDo.length > 0) {
              let id = this.TocDo[0].id
              this.cboTocDo_ISP = id
              this.cboTocDo_NIX = id
            }
          } catch (err) {}
          this.txtLanIP_Enabled = false
          this.txtWanIP_Enabled = false
        }
        if (_kieuld_id == this.KieuLapDat.HNI_DOI_LOAIKENH) {
          this.btnHuongKNWan_Enabled = false
          this.cboTocDo_Enabled = false
          this.chkTocDo_PIR_Enabled = false
          this.cboTocDo_Pir_Enabled = false
          this.cboLoaiKenh_Enabled = true

          this.chkTocDo_NIX_Enabled = false
          this.chkTocDo_ISP_Enabled = false
          //this.chkTocDo_NIX = false;
          //this.chkTocDo_ISP = false;
          this.cboTocDo_ISP_Enabled = false
          this.cboTocDo_NIX_Enabled = false
          this.txtCuoc_ISP_Enabled = false
          this.txtCuoc_NIX_Enabled = false
          this.txtSoLuong_IP_Enabled = false
          this.txtCuoc_IP_Enabled = false
          //this.cboTocDo_ISP.SelectedIndex = 0;
          //this.cboTocDo_NIX.SelectedIndex = 0;
          try {
            if (this.TocDo != null && this.TocDo.length > 0) {
              let id = this.TocDo[0].id
              this.cboTocDo_ISP = id
              this.cboTocDo_NIX = id
            }
          } catch (err) {}
          this.txtLanIP_Enabled = false
          this.txtWanIP_Enabled = false
        }
        if (_kieuld_id == this.KieuLapDat.HNI_DOI_TOCDO_HUONGKN) {
          this.btnHuongKNWan_Enabled = true
          this.cboTocDo_Enabled = true
          this.chkTocDo_PIR_Enabled = true
          this.cboTocDo_Pir_Enabled = false
          this.cboLoaiKenh_Enabled = false

          if (parseInt(this.cboLoaihinhTB) == LoaiHinhTB.MGW_INTERNET_TRUCTIEP||parseInt(this.cboLoaihinhTB) == LoaiHinhTB.NB_INTERNETTT) {
            this.chkTocDo_NIX_Enabled = true
            this.chkTocDo_ISP_Enabled = true
            this.cboTocDo_NIX_Enabled = true
            this.cboTocDo_ISP_Enabled = true

            //this.chkTocDo_NIX = false;
            //this.chkTocDo_ISP = false;
            this.txtCuoc_ISP_Enabled = false
            this.txtCuoc_NIX_Enabled = false
            this.txtSoLuong_IP_Enabled = true
            this.txtCuoc_IP_Enabled = true
          } else {
            this.chkTocDo_NIX_Enabled = false
            this.chkTocDo_ISP_Enabled = false
            //this.chkTocDo_NIX = false;
            //this.chkTocDo_ISP = false;
            this.cboTocDo_ISP_Enabled = false
            this.cboTocDo_NIX_Enabled = false
            this.txtCuoc_ISP_Enabled = false
            this.txtCuoc_NIX_Enabled = false
            this.txtSoLuong_IP_Enabled = false
            this.txtCuoc_IP_Enabled = false
            this.cboTocDo_ISP = this.TocDo[0].id
            this.cboTocDo_NIX = this.TocDo[0].id
          }
          this.txtLanIP_Enabled = false
          this.txtWanIP_Enabled = false
        }
        if (_kieuld_id == this.KieuLapDat.HNI_DOI_TOCDO_LOAIKENH) {
          this.btnHuongKNWan_Enabled = false
          this.cboTocDo_Enabled = true
          this.chkTocDo_PIR_Enabled = true
          this.cboTocDo_Pir_Enabled = false
          this.cboLoaiKenh_Enabled = true

          if (parseInt(this.cboLoaihinhTB) == LoaiHinhTB.MGW_INTERNET_TRUCTIEP||parseInt(this.cboLoaihinhTB) == LoaiHinhTB.NB_INTERNETTT) {
            this.chkTocDo_NIX_Enabled = true
            this.chkTocDo_ISP_Enabled = true
            this.cboTocDo_NIX_Enabled = true
            this.cboTocDo_ISP_Enabled = true
            //this.chkTocDo_NIX = false;
            //this.chkTocDo_ISP = false;
            this.txtCuoc_ISP_Enabled = false
            this.txtCuoc_NIX_Enabled = false
            this.txtSoLuong_IP_Enabled = true
            this.txtCuoc_IP_Enabled = true
          } else {
            this.chkTocDo_NIX_Enabled = false
            this.chkTocDo_ISP_Enabled = false
            this.chkTocDo_NIX = false
            this.chkTocDo_ISP = false
            this.cboTocDo_ISP_Enabled = false
            this.cboTocDo_NIX_Enabled = false
            this.txtCuoc_ISP_Enabled = false
            this.txtCuoc_NIX_Enabled = false
            this.txtSoLuong_IP_Enabled = false
            this.txtCuoc_IP_Enabled = false
            this.cboTocDo_ISP = this.TocDo[0].id
            this.cboTocDo_NIX = this.TocDo[0].id
          }
          this.txtLanIP_Enabled = false
          this.txtWanIP_Enabled = false
        }
        if (_kieuld_id == this.KieuLapDat.HNI_LOAIKENH_HUONGKN) {
          this.btnHuongKNWan_Enabled = true
          this.cboTocDo_Enabled = false
          this.chkTocDo_PIR_Enabled = false
          this.cboTocDo_Pir_Enabled = false
          this.cboLoaiKenh_Enabled = true

          this.chkTocDo_NIX_Enabled = false
          this.chkTocDo_ISP_Enabled = false
          //this.chkTocDo_NIX = false;
          //this.chkTocDo_ISP = false;
          this.cboTocDo_ISP_Enabled = false
          this.cboTocDo_NIX_Enabled = false
          this.txtCuoc_ISP_Enabled = false
          this.txtCuoc_NIX_Enabled = false
          this.txtSoLuong_IP_Enabled = false
          this.txtCuoc_IP_Enabled = false
          //this.cboTocDo_ISP.SelectedIndex = 0;
          //this.cboTocDo_NIX.SelectedIndex = 0;
          try {
            if (this.TocDo != null && this.TocDo.length > 0) {
              let id = this.TocDo[0].id
              this.cboTocDo_ISP = id
              this.cboTocDo_NIX = id
            }
          } catch (err) {}
          this.txtLanIP_Enabled = false
          this.txtWanIP_Enabled = false
        }

        if (_kieuld_id == this.KieuLapDat.HNI_DOI_WANIP_LAN_IP) {
          this.btnHuongKNWan_Enabled = true
          this.cboTocDo_Enabled = false
          this.chkTocDo_PIR_Enabled = false
          this.cboTocDo_Pir_Enabled = false
          this.txtLanIP_Enabled = true
          this.txtWanIP_Enabled = true
          this.chkTocDo_NIX_Enabled = false
          this.chkTocDo_ISP_Enabled = false
          //this.chkTocDo_NIX = false;
          //this.chkTocDo_ISP = false;
          this.cboTocDo_ISP_Enabled = false
          this.cboTocDo_NIX_Enabled = false
          this.txtCuoc_ISP_Enabled = false
          this.txtCuoc_NIX_Enabled = false
          this.txtSoLuong_IP_Enabled = false
          this.txtCuoc_IP_Enabled = false
          //this.cboTocDo_ISP.SelectedIndex = 0;
          //this.cboTocDo_NIX.SelectedIndex = 0;
          try {
            if (this.TocDo != null && this.TocDo.length > 0) {
              let id = this.TocDo[0].id
              this.cboTocDo_ISP = id
              this.cboTocDo_NIX = id
            }
          } catch (err) {}
        }

        if (_kieuld_id == this.KieuLapDat.HNI_DOI_CONGNGHE_GPON || _kieuld_id == 922 || _kieuld_id == 923) {
          this.cboCongNghe_Moi_Enabled = true
          this.cboCongNghe_Moi = 3 //Defaul gpon
        }

        if(_kieuld_id == this.KieuLapDat.VTI_DOI_GiACUOC_MEGA && this.$root.token.getPhanVungID() == 98){
          this.txtThoiHanCKEnabled = false
        }

        console.log('this.Click_kieuld && this.tinh.tinhthicong_id > 0', this.Click_kieuld, this.tinh.tinhthicong_id)
        // if (this.Click_kieuld && this.tinh.tinhthicong_id > 0) {
        //   await this.HienThiChonThongSo()
        // }
        this.kt_load = false
        // #endregion
      }
    },
    async HienThiChonThongSo() {
      this.$refs.ref_frmThongSoWan.ThongsoBan = this.thongsoBan
      this.$refs.ref_frmThongSoWan.ThongsoTc = this.thongsoTc
      this.$refs.ref_frmThongSoWan.khachhang_id = this.khachhang_id_tc
      this.$refs.ref_frmThongSoWan.hdkh_id = this.hdkh_id
      await this.$refs.ref_frmThongSoWan.openDialog1(this.tinh, this.loaitb_id, this.loaitb_id, LoaiHopDong.THAYDOI_THONGSO_MEGAWAN, this.dichvuvt_id, this.thongsoBan, this.thongsoTc)
    },
    CloseHienThiChonThongSo(data) {
      this.Capnhatthongso = data.Capnhat
      console.log('ChonPopUpMegaWan', data)
      if (this.Capnhatthongso) {
        this.thongsoBan = data.ThongsoBan
        this.thongsoTc = data.ThongsoTc
        if (this.thongsoBan.chuquan_id != 0) this.cboChuQuanMEGAWAN = this.thongsoBan.chuquan_id
        if (this.thongsoBan.loaikenh_id != 0) this.cboLoaiKenh = this.thongsoBan.loaikenh_id
        if (this.thongsoBan.loaimd_id != 0) this.cboLoaiModem = this.thongsoBan.loaimd_id
        if (this.thongsoBan.thietbidc_id != 0) this.cboLoaiTBiWan = this.thongsoBan.thietbidc_id
        if (this.thongsoBan.thoihan_id != 0) this.cboThoiHanMEGAWAN = this.thongsoBan.thoihan_id
        if (this.thongsoBan.tocdo_id != 0) this.cboTocDo = this.thongsoBan.tocdo_id
        if (this.thongsoBan.tocdo_isp != 0) {
          this.cboTocDo_ISP = this.thongsoBan.tocdo_isp
          this.chkTocDo_ISP = true
        }else{
          this.chkTocDo_ISP = false
          this.cboTocDo_ISP =0
        }
        if (this.thongsoBan.tocdo_nix != 0){
          this.cboTocDo_NIX = this.thongsoBan.tocdo_nix
          this.chkTocDo_NIX=true
        }else{
          this.chkTocDo_NIX=false
          this.cboTocDo_NIX = 0
        }
        if (this.thongsoBan.tocdo_pir_id != 0){
          this.cboTocDo_Pir = this.thongsoBan.tocdo_pir_id
          this.chkTocDo_PIR=true
        } else{
          this.chkTocDo_PIR=false
          this.cboTocDo_Pir =0
        }
        if (this.thongsoBan.trangbi_id != 0) this.cboTrangBiMEGAWAN = this.thongsoBan.trangbi_id
        if (this.thongsoBan.phantach_id != 0) this.cboPhanTachTD = this.thongsoBan.phantach_id
        if (this.thongsoBan.phanloai_id != 0) this.cboPhanLoaiMGW = this.thongsoBan.phanloai_id
        //if (thongsoBan.chuquanld_id != 0) cboTrangBiMEGAWAN = thongsoBan.trangbi_id;
        this.kieuldid_tc = this.thongsoTc.kieuld_id
      }
    },
    async dgView_SelectionChanged(i, item) {
      try {
        console.log('dgView_SelectionChanged', item)
        let hdtb_id = item.hdtb_id
        //LAY_DS_HOPDONG_TB_THEO_HDTB_ID
        let dtTB = await this.lay_ds_hopdong_tb_theo_hdtb_id(hdtb_id)
        await this.HienThiTTHopDongTB(dtTB)
      } catch (err) {
        this.ShowError(`${err.message}`)
      }
    },
    async cboDichVuVT_Change(val) {
      //console.log('cboDichVuVT-Changed-Val', val)
      try {
        this.loading(true)
        this.dichvuvt_id = val
        this.cboDichVuVT = val
        this.LoaihinhTB = []
        this.KieuLD = []
        await this.loaihinh_tb(val)
        await this.cboLoaihinhTB_SelectedValueChanged(this.cboLoaihinhTB)
      } catch (err) {
        console.log('cboDichVuVT-Changed', err)
      } finally {
        this.loading(false)
      }
    },
    async Gan_thongso_bancheo_thuebaoid(vthuebao_ban, vthuebao_tc, tinh_tc_id) {
      console.log('Gan_thongso_bancheo_thuebaoid','vthuebao_ban:',vthuebao_ban,'vthuebao_tc:', vthuebao_tc,'tinh_tc_id:', tinh_tc_id)
      try {
        let ds = await this.lay_thongso_thuebao_thuebaoid(vthuebao_ban)
        console.log('lay_thongso_thuebao_thuebaoid-data', ds)
        let exp_text = ''
        if (ds != null && ds.length > 0) {
          try {
            exp_text = 'KIEULD_ID'
            this.thongsoBan.kieuld_id = parseInt(ds[0]['kieuld_id'])
            exp_text = 'doituong_id'
            this.thongsoBan.doituong_id = parseInt(ds[0]['doituong_id'])
            exp_text = 'chuquan_id'
            this.thongsoBan.chuquan_id = parseInt(ds[0]['chuquan_id'])
            exp_text = 'chuquanld_id'
            this.thongsoBan.chuquanld_id = parseInt(ds[0]['chuquanld_id'])
            exp_text = 'congnghe_id'
            this.thongsoBan.congnghe_id = parseInt(ds[0]['congnghe_id'])
            exp_text = 'kieutra_id'
            this.thongsoBan.kieutra_id = parseInt(ds[0]['kieutra_id'])
            exp_text = 'loaikenh_id'
            this.thongsoBan.loaikenh_id = parseInt(ds[0]['loaikenh_id'])
            exp_text = 'loaimd_id'
            this.thongsoBan.loaimd_id = parseInt(ds[0]['loaimd_id'])
            exp_text = 'ma_doicap'
            this.thongsoBan.ma_doicap = ds[0]['ma_doicap']
            exp_text = 'ma_tn'
            this.thongsoBan.ma_tn = ds[0]['ma_tn']
            exp_text = 'mucuoc_tb'
            this.thongsoBan.muccuoc_id = parseInt(ds[0]['mucuoc_tb'])
            exp_text = 'mucuoctb_id'
            this.thongsoBan.muccuoctb_id = parseInt(ds[0]['mucuoctb_id'])
            exp_text = 'thietbidc_id'
            this.thongsoBan.thietbidc_id = parseInt(ds[0]['thietbidc_id'])
            exp_text = 'THIETBI_DICH'
            this.thongsoBan.thietbidc_id_cuoi = parseInt(ds[0]['THIETBI_DICH'])
            exp_text = 'thoihan_id'
            this.thongsoBan.thoihan_id = parseInt(ds[0]['thoihan_id'])
            exp_text = 'tocdo_id'
            this.thongsoBan.tocdo_id = parseInt(ds[0]['tocdo_id'])
            exp_text = 'tocdo_isp'
            this.thongsoBan.tocdo_isp = parseInt(ds[0]['tocdo_isp'])
            exp_text = 'tocdo_nix'
            this.thongsoBan.tocdo_nix = parseInt(ds[0]['tocdo_nix'])
            try {
              this.thongsoBan.tocdo_pir_id = parseInt(ds[0]['tocdo_pir_id'])
            } catch (e) {}
            exp_text = 'tocdothuc_id'
            this.thongsoBan.tocdothuc_id = parseInt(ds[0]['tocdothuc_id'])
            exp_text = 'trangbi_id'
            this.thongsoBan.trangbi_id = parseInt(ds[0]['trangbi_id'])
          } catch (er) {
            console.log('Gan_thongso_bancheo_thuebaoid-err', er)
            let exp = 'Thiếu dữ liệu trường ' + exp_text.toLocaleLowerCase() + ' ở tỉnh bán. Liên hệ admin để xử lý'
            this.ShowError(exp)
            return
          }
        }
        if (vthuebao_tc != 0 && vthuebao_tc != -1) {
          let tinh_tc = await this.tinh_thicong(tinh_tc_id)
          let ds_tc = await this.lay_thongso_thuebao_thuebaoid_bc(tinh_tc_id, vthuebao_tc)
          if (ds_tc != null && ds_tc.length > 0) {
            try {
              exp_text = 'kieuld_id'
              this.thongsoTc.kieuld_id = parseInt(ds_tc[0]['kieuld_id'])
              exp_text = 'doituong_id'
              this.thongsoTc.doituong_id = parseInt(ds_tc[0]['doituong_id'])
              exp_text = 'chuquan_id'
              this.thongsoTc.chuquan_id = parseInt(ds_tc[0]['chuquan_id'])
              exp_text = 'chuquanld_id'
              this.thongsoTc.chuquanld_id = parseInt(ds_tc[0]['chuquanld_id'])
              exp_text = 'congnghe_id'
              this.thongsoTc.congnghe_id = parseInt(ds_tc[0]['congnghe_id'])
              exp_text = 'kieutra_id'
              this.thongsoTc.kieutra_id = parseInt(ds_tc[0]['kieutra_id'])
              exp_text = 'loaikenh_id'
              this.thongsoTc.loaikenh_id = parseInt(ds_tc[0]['loaikenh_id'])
              exp_text = 'loaimd_id'
              this.thongsoTc.loaimd_id = parseInt(ds_tc[0]['loaimd_id'])
              exp_text = 'ma_doicap'
              this.thongsoTc.ma_doicap = ds_tc[0]['ma_doicap']
              exp_text = 'ma_tn'
              this.thongsoTc.ma_tn = ds_tc[0]['ma_tn']
              exp_text = 'mucuoc_tb'
              this.thongsoTc.muccuoc_id = parseInt(ds_tc[0]['mucuoc_tb'])
              console.log('this.thongsoTc.muccuoc_id', this.thongsoTc.muccuoc_id)
              exp_text = 'mucuoctb_id'
              this.thongsoTc.muccuoctb_id = parseInt(ds_tc[0]['mucuoctb_id'])
              exp_text = 'thietbidc_id'
              this.thongsoTc.thietbidc_id = parseInt(ds_tc[0]['thietbidc_id'])
              exp_text = 'THIETBI_DICH'
              this.thongsoTc.thietbidc_id_cuoi = parseInt(ds_tc[0]['THIETBI_DICH'])
              exp_text = 'thoihan_id'
              this.thongsoTc.thoihan_id = parseInt(ds_tc[0]['thoihan_id'])
              exp_text = 'tocdo_id'
              this.thongsoTc.tocdo_id = parseInt(ds_tc[0]['tocdo_id'])
              exp_text = 'tocdo_isp'
              this.thongsoTc.tocdo_isp = parseInt(ds_tc[0]['tocdo_isp'])
              exp_text = 'tocdo_nix'
              this.thongsoTc.tocdo_nix = parseInt(ds_tc[0]['tocdo_nix'])
              try {
                this.thongsoTc.tocdo_pir_id = parseInt(ds_tc[0]['tocdo_pir_id'])
              } catch (e) {}
              exp_text = 'tocdothuc_id'
              this.thongsoTc.tocdothuc_id = parseInt(ds_tc[0]['tocdothuc_id'])
              exp_text = 'trangbi_id'
              this.thongsoTc.trangbi_id = parseInt(ds_tc[0]['trangbi_id'])
            } catch (errthongso_tc) {
              let textError = 'Thiếu dữ liệu trường ' + exp_text.toUpperCase() + ' thuê bao ' + vthuebao_tc + ' ở tỉnh thi công (' + tinh_tc.matinh + '). Liên hệ admin để xử lý'
              this.ShowError(textError)
              return
            }
          }
        }else{
          this.thongsoTc = this.Reset_Thongso_Thuebao()
        }
      } catch (err) {}
      finally{
        console.log('Gan_thongso_bancheo_thuebaoid-result','thongsoBan:',this.thongsoBan,'thongsoTc:', this.thongsoTc)
      }
    },
    GetData: function (response) {
      if (response.data.error == '200' && response.data.error_code == 'BSS-00000000') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowAlert: function (message) {
      this.$toast.error(message)
    },
    ShowError: function (message) {
      this.$toast.error(message)
    },
    ShowSuccess: function (message) {
      this.$toast.success(message)
    },
    focusByClass(classText) {
      var collection = document.getElementsByClassName(classText)
      //console.log('focusByClass',collection[0])
      if (collection != null && collection.length > 0) {
        collection[0].focus()
      }
    },
    async HienThi_CTK_NIX_ISP(
      vloai // 2 : nix, 3 isp
    ) {
      if (parseInt(this.cboLoaihinhTB) == LoaiHinhTB.MGW_INTERNET_TRUCTIEP||parseInt(this.cboLoaihinhTB) == LoaiHinhTB.NB_INTERNETTT||parseInt(this.cboLoaihinhTB) == 295||parseInt(this.cboLoaihinhTB) == 260) {
        let int_loaitb_id = 0
        let int_tocdo_id = 0
        let int_doituong_id = 0
        let int_loaikenh_id = 0
        int_loaitb_id = parseInt(this.cboLoaihinhTB)
        int_doituong_id = this.doituong_id
        int_loaikenh_id = parseInt(this.cboLoaiKenh)
        let ds_ctk_ctc = []

        if (vloai == 2) {
          int_tocdo_id = parseInt(this.cboTocDo_NIX)
          ds_ctk_ctc = await this.Lay_CUOCTK_CUOCTC(int_loaitb_id, int_tocdo_id, int_doituong_id, int_loaikenh_id, 2)
          if (ds_ctk_ctc != null && ds_ctk_ctc.length > 0 && ds_ctk_ctc[0]['cuoc_tk'] != null && ds_ctk_ctc[0]['cuoc_tk'] != '') {
            this.txtCuoc_NIX = parseFloat(ds_ctk_ctc[0]['cuoc_tk'])
          } else {
            this.txtCuoc_NIX = '0'
          }
        }
        if (vloai == 3) {
          int_tocdo_id = parseInt(this.cboTocDo_ISP)
          ds_ctk_ctc = await this.Lay_CUOCTK_CUOCTC(int_loaitb_id, int_tocdo_id, int_doituong_id, int_loaikenh_id, 3)
          if (ds_ctk_ctc != null && ds_ctk_ctc.length > 0 && ds_ctk_ctc[0]['cuoc_tk'] != null && ds_ctk_ctc[0]['cuoc_tk'] != '') {
            this.txtCuoc_ISP = parseFloat(ds_ctk_ctc[0]['cuoc_tk'])
          } else {
            this.txtCuoc_ISP = '0'
          }
        }
      } else {
        this.txtCuoc_NIX = '0'
        this.txtCuoc_ISP = '0'
      }
    },
    paymentSuccess(result) {
      // alert('Thanh toán thành công:'+ result)
      // this.$refs.thanhtoanDialog.hide()
    },
    change_chkTocDo_NIX(evt) {
      console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 4649 ~ change_chkTocDo_NIX ~ evt', evt)
      if (!evt.target.checked) {
        this.cboTocDo_NIX_Enabled = false
        this.txtCuoc_NIX_Enabled = false
      }else{
        this.cboTocDo_NIX_Enabled = true
        this.txtCuoc_NIX_Enabled = true
      }
    },
    change_chkTocDo_ISP(evt) {
      console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 4653 ~ change_chkTocDo_ISP ~ evt', evt)
      if (!evt.target.checked) {
        this.cboTocDo_ISP_Enabled = false
        this.txtCuoc_ISP_Enabled = false
      }else{
        this.cboTocDo_ISP_Enabled = true
        this.txtCuoc_ISP_Enabled = true
      }
    },
    change_chkTocDo_PIR(evt) {
      console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 4657 ~ change_chkTocDo_PIR ~ evt', evt)
      if (!evt.target.checked) {
        // this.txtCuocTC=0
      }
    },
    async btnThaydoi_Click() {
      try {
        this.Loading = true
        if (this.hdtb_id != 0) {
          let ds_mg1 = await this.lay_ds_hopdong_tb_theo_hdkh_id_bc(this.hdtb_id)
          let kieuld = this.cboKieuLD
          let cir = this.cboTocDo
          let pir = this.cboTocDo_Pir
          if (this.chkTocDo_PIR == true) this.check_pir = 1
          else this.check_pir = 0
          await this.$ref_frmDsThueBaoWan.frmDsThueBaoWan_Load(this.khachhang_id, this.txtMaTB, this.hdtb_id, this.loaitb_id)
        } else {
          this.ShowError('Cần phải tạo hợp đồng mẫu!')
          return
        }
      } catch (err) {
        console.log(err)
        this.ShowError(`${err}`)
      } finally {
        this.Loading = false
      }
    },
    async HienThiTT_DanhBa_v2(ma_tb) {},
    async ThemHDThueBao_v2(ma_tb) {},
    async frmDsThueBaoWanClose(dt) {
      try {
        if (dt != null) {
          this.loading(true)
          for (var drR of dt) {
            //test
            await this.HienThiTT_DanhBa_v2(drR['ma_tb'])
            await this.ThemHDThueBao_v2(drR['ma_tb'])
          }
          await this.HienThiDanhSacHDTB(await this.lay_ds_hopdong_tb_theo_hdkh_id_bc(this.hdkh_id))
          this.ShowSuccess('Thêm thuê bao thành công!')
        }
      } catch (err) {
        this.ShowError(`${err}`)
      } finally {
        this.loading(false)
      }
    },
    async cboTrangBiMEGAWAN_SelectedValueChanged(val) {
      if (this.TrangBiMEGAWAN != null && this.TrangBiMEGAWAN.length > 0) {
        if (this.cboTrangBiMEGAWAN == null) return
        if (this.cboTrangBiMEGAWAN == '') return
        this.trangbi_id = parseInt(this.cboTrangBiMEGAWAN)
        await this.LayTienTheoKhoanMuc()
        await this.HT_DS_KhuyenMai_Combobox()
      }
    },
    async cboLoaihinhTB_SelectedValueChanged(val) {
      console.log('cboLoaihinhTB_SelectedValueChanged', val)
      try {
        this.KieuLD = []
        this.cboKieuLD = ''
        if (this.LoaihinhTB != null && this.LoaihinhTB.length > 0) {
          if (this.cboLoaihinhTB == null) return
          if (this.cboLoaihinhTB == '') return
          ///TuanNA: Thêm code lấy thông tin cước thuê kênh, thuê cổng đối với thuê bao Megawan
          ///Ngày 08.05.2012
          //txtCuocTC.Text = Convert.ToDecimal(HT_CuocTK_CuocTC_MGWan(2)).ToString(Format.FM_TIEN_VND);
          //txtCuocTK.Text = Convert.ToDecimal(HT_CuocTK_CuocTC_MGWan(1)).ToString(Format.FM_TIEN_VND);
          this.loaitb_id = parseInt(val)
          this.txtCuoc_ISP = '0'
          this.txtCuoc_NIX = '0'
          this.txtSoLuong_IP = '0'
          this.txtCuoc_IP = '0'
          if (this.loaitb_id == LoaiHinhTB.MGW_INTERNET_TRUCTIEP||this.loaitb_id==LoaiHinhTB.NB_INTERNETTT) {
            this.label38 = 'Tốc độ tổng'
            this.label11 = this.label38
            this.chkTocDo_NIX_Enabled = true
            this.chkTocDo_ISP_Enabled = true
            this.chkTocDo_NIX = false
            this.chkTocDo_ISP = false
            this.txtCuoc_ISP_Enabled = false
            this.txtCuoc_NIX_Enabled = false
            this.txtSoLuong_IP_Enabled = true
            this.txtCuoc_IP_Enabled = true
            this.cboTocDo_Pir_Enabled = false
            this.chkTocDo_PIR = false
            this.cboTocDo_Pir = 0
            await this.HienThi_CTK_NIX_ISP(2)
            await this.HienThi_CTK_NIX_ISP(3)
          } else {
            this.label38 = 'Tốc độ CIR'
            this.label11 = this.label38
            this.chkTocDo_NIX_Enabled = false
            this.chkTocDo_ISP_Enabled = false
            this.chkTocDo_NIX = false
            this.chkTocDo_ISP = false
            this.cboTocDo_ISP_Enabled = false
            this.cboTocDo_NIX_Enabled = false
            this.txtCuoc_ISP_Enabled = false
            this.txtCuoc_NIX_Enabled = false
            this.txtSoLuong_IP_Enabled = false
            this.txtCuoc_IP_Enabled = false
            this.cboTocDo_Pir = 0
            this.chkTocDo_PIR_Enabled = true
            this.chkTocDo_PIR = false
            if (this.kt_load == false) {
              this.cboTocDo_ISP = 0
              this.cboTocDo_NIX = 0
            }
          }
          let ds_ctk_ctc = []
          ds_ctk_ctc = await this.Lay_CUOCTK_CUOCTC(this.loaitb_id, parseInt(this.cboTocDo), this.doituong_id, parseInt(this.cboLoaiKenh), 1)
          if (ds_ctk_ctc != null && ds_ctk_ctc.length > 0 && ds_ctk_ctc[0]['cuoc_tk'] != '') {
            this.txtCuocTK = ds_ctk_ctc[0]['cuoc_tk']
            this.txtCuocTC = parseInt(ds_ctk_ctc[0]['cuoc_tc'])
          } else {
            this.txtCuocTK = '0'
            this.txtCuocTC = '0'
          }
          await this.HT_Kieu_LD_BANCHEO_Combobox(parseInt(this.cboLoaihinhTB), LoaiHopDong.THAYDOI_THONGSO_MEGAWAN, 2)
          await this.HT_DS_KhuyenMai_Combobox()
          this.chkTocDo_NIX_Enabled = false
          this.chkTocDo_ISP_Enabled = false
          this.chkTocDo_PIR_Enabled = false
          this.cboChuQuanMEGAWAN_Enabled = false
          this.cboLoaiKenh_Enabled = false
          this.cboLoaiModem_Enabled = false
          this.cboLoaiTBiWan_Enabled = false
          this.cboThoiHanMEGAWAN_Enabled = false
          this.cboTocDo_Enabled = false
          this.cboTocDo_ISP_Enabled = false
          this.cboTocDo_NIX_Enabled = false
          this.cboTocDo_Pir_Enabled = false
          this.cboTrangBiMEGAWAN_Enabled = false
        }
      } catch (err) {
        console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 4755 ~ cboLoaihinhTB_SelectedValueChanged ~ err', err)
      }
    },
    async cboTocDo_SelectedValueChanged(val) {
      try {
        if (this.TocDo != null && this.TocDo.length > 0) {
          if (this.cboTocDo == null) return
          if (this.cboTocDo == '') return
          this.tocdo_moi_id = parseInt(this.cboTocDo)
          await this.LayTienTheoKhoanMuc()
          let ds_ctk_ctc = []
          ds_ctk_ctc = await this.Lay_CUOCTK_CUOCTC(parseInt(this.cboLoaihinhTB), parseInt(this.cboTocDo), this.doituong_id, parseInt(this.cboLoaiKenh), 1)
          if (ds_ctk_ctc != null && ds_ctk_ctc.length > 0 && ds_ctk_ctc[0]['cuoc_tk'] != '') {
            this.txtCuocTK = parseInt(ds_ctk_ctc[0]['cuoc_tk'])
            this.txtCuocTC = parseInt(ds_ctk_ctc[0]['cuoc_tc'])
          } else {
            this.txtCuocTK = '0'
            this.txtCuocTC = '0'
          }
          await this.HT_DS_KhuyenMai_Combobox()
        }
      } catch (err) {
        console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 4839 ~ cboTocDo_SelectedValueChanged ~ err', err)
      }
    },
    async cboLoaiTBiWan_SelectedValueChanged(val) {
      try {
        if (this.LoaiTBiWan != null && this.LoaiTBiWan.length > 0) {
          if (this.cboLoaiTBiWan == null) return
          if (this.cboLoaiTBiWan == '') return
          this.ltb_adsl_moi_id = parseInt(this.cboLoaiTBiWan)
          this.ltb_adsl_id = this.ltb_adsl_moi_id
          await this.LayTienTheoKhoanMuc()
        }
      } catch (err) {
        console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 4861 ~ cboLoaiTBiWan_SelectedValueChanged ~ err', err)
      }
    },
    async cboTocDoCu_SelectedValueChanged(val) {
      try {
        if (val != null && val != '') this.tocdo_cu_id = val
      } catch (err) {
        console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 4874 ~ cboTocDoCu_SelectedValueChanged ~ err', err)
      }
    },
    async cboLoaiTBiWanCu_SelectedValueChanged(val) {
      try {
        if (val != null && val != '') {
          this.ltb_adsl_id = val
          this.ltb_adsl_cu_id = val
        }
      } catch (err) {
        console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 4883 ~ cboLoaiTBiWanCu_SelectedValueChanged ~ err', err)
      }
    },
    async cboPhanTachTD_SelectedValueChanged(val) {
      try {
        if (this.PhanTachTD != null > 0 && this.PhanTachTD.length > 0) {
          if (val == null) return
          if (val == '') return
          if (parseInt(val) == 1) {
            this.chkTocDo_ISP_Enabled = true
            this.chkTocDo_NIX_Enabled = true

            this.cboTocDo_ISP = 0
            this.cboTocDo_NIX = 0
            this.cboTocDo_ISP = ''
            this.cboTocDo_NIX = ''

            this.txtCuoc_NIX = '0'
            this.txtCuoc_ISP = '0'
          } else {
            this.chkTocDo_ISP_Enabled = false
            this.chkTocDo_NIX_Enabled = false
            this.chkTocDo_ISP = false
            this.chkTocDo_NIX = false
            this.cboTocDo_ISP_Enabled = false
            this.cboTocDo_NIX_Enabled = false

            this.cboTocDo_ISP = 0
            this.cboTocDo_NIX = 0
            this.cboTocDo_ISP = ''
            this.cboTocDo_NIX = ''

            this.txtCuoc_NIX = '0'
            this.txtCuoc_ISP = '0'
          }
        }
      } catch (err) {
        console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 4891 ~ cboPhanTachTD_SelectedValueChanged ~ err', err)
      }
    },
    async cboLoaiKenh_SelectedValueChanged(val) {
      try {
        if (val == null) return
        if (val == '') return
        let vloaikenh_id = parseInt(this.cboLoaiKenh)
        let ds_ctk_ctc = await this.Lay_CUOCTK_CUOCTC(parseInt(this.cboLoaihinhTB), parseInt(this.cboTocDo), this.doituong_id, vloaikenh_id, 1)
        if (ds_ctk_ctc != null && ds_ctk_ctc.length > 0 && ds_ctk_ctc[0]['cuoc_tk'] != '') {
          this.txtCuocTK = parseInt(ds_ctk_ctc[0]['cuoc_tk'])
          this.txtCuocTC = parseInt(ds_ctk_ctc[0]['cuoc_tc'])
        } else {
          this.txtCuocTK = '0'
          this.txtCuocTC = '0'
        }
        await this.HienThi_CTK_NIX_ISP(2)
        await this.HienThi_CTK_NIX_ISP(3)
      } catch (err) {
        console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 4928 ~ cboLoaiKenh_SelectedValueChanged ~ err', err)
      }
    },
    // async cboDichVuVT_SelectedValueChanged(val) {
    //   try {
    //     if (val != null) {
    //       this.dichvuvt_id = parseInt(val)
    //       //bangts.HT_LoaiHinh_TB_Combobox(cboLoaihinhTB, dichvuvt_id);
    //       await this.loaihinh_tb(this.dichvuvt_id)
    //     }
    //   } catch (err) {
    //     console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 4940 ~ cboDichVuVT_SelectedValueChanged ~ err', err)
    //   }
    // },
    async HT_Kieu_LD_BANCHEO_Combobox(loaitb_id, loaihd_id, vkieu) {
      let data = this.GetData(
        await api.ht_kieu_ld_bancheo_combobox(this.axios, {
          loaihdId: loaihd_id,
          loaitbId: loaitb_id,
          vkieu: vkieu
        })
      )
      if (data != null && data.length > 0) {
        this.KieuLD = data.map((x) => ({ id: x.kieuld_id, text: x.ten_kieuld }))
        this.cboKieuLD = this.KieuLD[0].id
        this.cboKieuLD_SelectedValueChanged()
      } else {
        this.KieuLD = []
        this.cboKieuLD = ''
      }
    },
    cboKieuLD_Select(e) {
      var data = e.params.data
      console.log('cboKieuLD_Select', data)
    },
    cboKieuLD_Click() {
      //   this.Click_kieuld = true
      //   console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 5027 ~ cboKieuLD_Click ~ Click_kieuld', this.Click_kieuld)
    },
    cboKieuLD_Leave() {
      this.Click_kieuld = false
      console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 5031 ~ cboKieuLD_Leave ~ this.Click_kieuld', this.Click_kieuld)
    },
    async cboKieuLD_Open() {
      this.Click_kieuld = true
      // if (this.txtMaTB != '' && this.txtMaTB.length > 0) {
      //   await this.cboKieuLD_SelectedValueChanged()
      //   //await this.HienThiChonThongSo();
      // }
    },
    async cboKieuLD_Change(val) {},
    async cboKieuLD_Close() {
      try {
        if (this.txtMaTB != '' && this.txtMaTB.length > 0 && this.tinh.tinhthicong_id > 0 && this.thuebao_id > 0) {
          console.log('cboKieuLD_Close:this.cboKieuLD_SelectedValueChanged()', this.tinh)
          //await this.cboKieuLD_SelectedValueChanged()
        }
      } catch (err) {
        console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 5075 ~ cboKieuLD_Close ~ err', err)
      }
    },
    async clickThongSoTC(){
      try {
        if (this.txtMaTB != '' && this.txtMaTB.length > 0 && this.tinh.tinhthicong_id > 0 && this.thuebao_id > 0) {
          console.log('clickThongSoTC', this.tinh)
          await this.HienThiChonThongSo()
        }
      } catch (err) {
        console.log('🚀 ~ file: clickThongSoTC~ ', err)
      }
    },
    TestData() {
      //
    }, //
    open_thanhToanPopUp() {
      this.loading(true)
    }
  }
}
</script>
<style>
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
/* asset css select2 form */
.select2-container {
  width: 100% !important;
}

.select2-container--default .select2-selection--single {
  border-color: #E0E0E0;
  outline: none;
}

.select2-dropdown {
  border: 1px solid #E0E0E0;
}

.select2-container--default .select2-selection--single .select2-selection__arrow b {
  font-family: "FontAwesome";
  border: 0px;
  top: 0px;
  height: 1.428rem;
  bottom: 0px;
  line-height: 1.428rem;
  left: 5px;
  margin: auto;
}
.select2-container--default .select2-selection--single .select2-selection__arrow b:before {
  content: "";
}

.select2-container--default .select2-search--dropdown .select2-search__field {
  border: 0px;
  outline: none;
}

/* For Chrome, Safari, and Edge */
input[type="number"]::-webkit-inner-spin-button,
input[type="number"]::-webkit-outer-spin-button {
    -webkit-appearance: none;
    margin: 0;
}

/* For Firefox */
input[type="number"] {
    -moz-appearance: textfield; /* Remove spinner in Firefox */
}
</style>