<template src='./ChangeMegaWanConfig.html'></template>
<style scoped  src='./ChangeMegaWanConfig.scss'></style>
<script src='@/static/vendor/jquery/split.js'></script>
<script>
// import gridView from '@/modules/prepaid/ChoosePreapaidNumber/components/gridview.vue'
//import treegridView from '@/modules/contract/setup/ManageSelfcareContract/components/treegridview.vue'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
//import thongTinEmail from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue'
//import thongTinLienHe from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue'
import thongTinHenKhachHangModal from '@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
// import thanhToan from '@/modules/contract/setup/Payment/Payment.vue'
const thanhToan = () => import('@/modules/contract/setup/Payment/Payment.vue')
const defaultModule = () => import('@/modules/contract/setup/Payment/Default.vue')
const lienHe = () => import('@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue')
const thongTinEmailCom = () => import('@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue')
// import defaultModule from '@/modules/contract/setup/Payment/Default.vue'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import api from './Api'
import { LoaiHopDong, TrangThaiHD, DichVuVienThong, LOAI_DV, LoaiHinhTB, TrangThaiTB } from '@/const/enums/index.js'
import { KHOANMUC_TT, Loai_KM } from '../InstallNewSubs/Enums'
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
    //thongTinLienHe,
    thongTinHenKhachHangModal
    //thanhToan
  },
  props: {
    ispopup: {
      type: Boolean,
      default: false
    }
  },
  name: 'ChangeMegaWanConfig',
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
      dsHDTB_MEWAN: [],
      dsKM_HDTB: [],
      dsTTTT: [],
      dsCTTHD: [],
      dtCTKM: [],
      vhuongkn: '',
      thuebao_id_cha: 0,
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
      //Hiếu thêm 18.09.2010
      tocdo_moi_id: 0,
      tocdo_cu_id: 0,
      targetTop: '#app .main-wrapper',
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      dtienld: 0,
      dvatld: 0,
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
      kt_load: false,
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
      dtpNgayCV: '',
      txtMaTB: '',
      txtMaERP: '',
      txtSoAo: '',
      txtTenTB: '',
      txtMaBSS: '',
      txtSiteID: '',
      txtTenKH: '',
      txtMaKH: '',
      txtDiaChiLD: '',
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
      paymentloaded: false,
      popupComponentName: '',
      tsbtnXoaTB_Enabled: true,
      tsbtnThanhToan_Enabled: true,
      KieuLapDat: {
        HNI_DOI_CONGNGHE_GPON: 921,
        HNI_DOI_CONGNGHE_MENE: 922,
        HNI_DOI_WANIP_LAN_IP: 903,
        HNI_DOI_GIA_CUOC: 850,
        HNI_DOI_TOCDO: 851,
        HNI_DOI_HUONG_KN: 853,
        HNI_DOI_LOAIKENH: 852,
        HNI_DOI_TOCDO_HUONGKN: 855,
        HNI_DOI_TOCDO_LOAIKENH: 854,
        HNI_LOAIKENH_HUONGKN: 856
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
      // tsbtnThanhToan_Enabled: true,
      cboPhanTachTD_Enabled: true,
      cboPhanLoaiMGW_Enabled: true,
      cboTocDo_ISP_Enabled: true,
      cboTocDo_NIX_Enabled: true,
      chkKhuyenMai_Enabled: true,
      chkTocDo_PIR_Enabled: true,
      btnHuongKNWan_Enabled: true,
      cboTocDo_Enabled: true,
      cboLoaiKenh_Enabled: true,
      cboCongNghe_Moi_Enabled: true,
      currentModule: '',
      userComponentModule: null,
      userComponentModuleLienHe: null,
      userComponentModuleEmail: null
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
      // this.Loading = false
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
      this.thamso_md_theo_ma()
    ])
      .then((responses) => {
        //console.log(responses)
        this.Loading = true
        this.check_hdkh_cha_id().then((response) => {
          console.log('check_hdkh_cha_id', response)
        })
        try {
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
        try {
          this.SetButton(-1).then((r) => {
            this.$refs.reftxtMaTB.focus()
            console.log(r)
          })
        } catch (err) {}
        try {
          this.setForPTTBOne()
        } catch (errOne) {
          console.log('setForPTTBOne', errOne)
        }
      })
    this.dtpNgayYeuCau = moment().format(this.dateFormat)
    this.dtpNgayLapHD = moment().format(this.dateFormat)
  },
  watch: {
    /*
    cboLoaihinhTB: async function (val) {
      try {
        this.loaitb_id = val
        this.txtCuoc_ISP = '0'
        this.txtCuoc_NIX = '0'
        this.txtSoLuong_IP = '0'
        this.txtCuoc_IP = '0'
        if (this.loaitb_id == LoaiHinhTB.MGW_INTERNET_TRUCTIEP) {
          this.label38 = 'Tốc độ tổng'
          this.label11 = 'Tốc độ tổng'
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
          this.label11 = 'Tốc độ CIR'
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
          try {
            this.cboTocDo_Pir = this.TocDo[0].id
            this.chkTocDo_PIR_Enabled = true
            this.chkTocDo_PIR = false

            if (this.kt_load == false) {
              if (this.TocDo != null && this.TocDo.length > 0) {
                this.cboTocDo_ISP = this.TocDo[0].id
                this.cboTocDo_NIX = this.TocDo[0].id
              }
            }
          } catch (err) {}
        }
        await this.lay_ds_kieu_ld(val, LoaiHopDong.THAYDOI_THONGSO_MEGAWAN)
        await this.HT_DS_KhuyenMai_Combobox()
      } catch (err) {
        console.log('cboLoaihinhTB', err)
      }
    },
    */

    /*
    cboDichVuVT: async function (val) {
      console.log('cboDichVuVT-Changed-Val', val)
      try {
        this.dichvuvt_id = val
        await this.loaihinh_tb(val)
      } catch (er) {
        console.log('cboDichVuVT-Changed', err)
      }
    },
    */
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
    cboKieuLD: function (val) {
      try {
        this.cboKieuLD_SelectedValueChanged()
      } catch (err) {
        console.log('🚀 ~ file: ChangeMegaWanConfig.vue ~ line 459 ~ err', err)
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
    },
    userComponent() {
      if (this.currentModule == 'thanhtoan') {
        return thanhToan
      } else return defaultModule
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
    setForPTTBOne() {
      try {
        if (this.$route.query.ma_tb != null && this.$route.query.ma_tb && this.$route.query.ma_tb.length > 0) {
          this.txtMaTB = this.$route.query.ma_tb
        }
        if (this.$route.query.ma_kh != null && this.$route.query.ma_kh && this.$route.query.ma_kh.length > 0) {
          this.txtMaKH = this.$route.query.ma_kh
        }
        if (this.$route.query.ten_kh != null && this.$route.query.ten_kh && this.$route.query.ten_kh.length > 0) {
          this.txtTenKH = this.$route.query.ten_kh
        }
        if (this.txtMaTB != null && this.txtMaTB.length > 1) {
          const elem = this.$refs.reftxtTenTB
          elem.click()
        }
      } catch (err) {
        console.log('setForPTTBOne ~ err', err)
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
        dichvu_vt_id: `${DichVuVienThong.MEGAWAN},${DichVuVienThong.METRONET},${DichVuVienThong.NOIBO_DADIEM}`
      }
      let data = this.GetData(await api.sp_ht_dichvuvt_combobox(this.axios, input))
      if (data == null || data.length == 0) return
      this.DichVuVT = data.map((x) => ({ id: x.dichvuvt_id, text: x.ten_dvvt }))
      this.cboDichVuVT = this.DichVuVT[0].id
      this.dichvuvt_id = this.DichVuVT[0].id
      await this.cboDichVuVT_Change(null)
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
      if (data == null || data.length == 0) {
        this.showError('Load danh sách chủ quản thất bại, vui lòng thử lại (F5)')
        return
      }
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
      try {
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
      } catch (err) {
        console.log('🚀 ~ file: ChangeMegaWanConfig.vue ~ line 660 ~ err', err)
      }
    },
    loaihinh_tb: async function (dichvuvtId) {
      let input = {
        dichvuvt_id: dichvuvtId
      }
      let data = this.GetData(await api.loaihinh_tb(this.axios, input))
      if (data == null || data.length == 0) return
      this.LoaihinhTB = data.map((x) => ({ id: x.loaitb_id, text: x.loaihinh_tb }))
      this.cboLoaihinhTB = this.LoaihinhTB[0].id
      // await this.lay_ds_kieu_ld(this.cboLoaihinhTB, LoaiHopDong.THAYDOI_THONGSO_MEGAWAN)
    },
    kieuld: async function (loaihinhtbId, loaihdId) {
      let input = {}
      let data = this.GetData(await api.kieuld(this.axios, input))
      if (data == null || data.length == 0) return
      //console.log('kieuld', data)
      this.KieuLD = data.filter((x) => x.LOAIHD_ID == loaihdId).map((x) => ({ id: x.KIEULD_ID, text: x.TEN_KIEULD }))
      this.cboKieuLD = this.KieuLD[0].id
    },
    lay_ds_kieu_ld: async function (loaihinhtbId, loaihdId) {
      let input = { vloaihd_id: loaihdId, vloaitb_id: loaihinhtbId }
      let data = this.GetData(await api.lay_ds_kieu_ld(this.axios, input))
      if (data == null || data.length == 0) {
        this.KieuLD = []
        return
      }
      //console.log('kieuld', data)
      this.KieuLD = data.map((x) => ({ id: x.kieuld_id, text: x.ten_kieuld }))
      this.cboKieuLD = this.KieuLD[0].id
    },
    Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    async paymentcreated(val) {
      this.paymentloaded = true
      this.$refs.thanhtoanDialog.show(true)
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
            this.ctv_id = val.nhanvien_id
            break
          case 'popupNGT':
            this.txtNguoiGT = val.ten_ht
            this.nguoigt_id = val.nhanvien_id
            break
          case 'popupDSHuongKN':
            this.txtHuongKn = val.shuongkn
            this.thuebao_id_cha = val.iid_sochinh
            this.vhuongkn = val.vma_tb
            this.cboTinh_KN_Moi = val.itinh_kn_id
            break
          case 'popup-ChonKhuyenMai':
            this.cboKhuyenMai = val.khuyenmai.khuyenmai_id
            break
          case 'popupTienKM':
            // var kmtt_id = -1;
            // var tien_kmtt = 0;
            // var tien_vat = 0;
            console.log('popupTienKM-close:', val)
            this.ds_tien_khoanmuc = val
            try {
              if (this.ds_tien_khoanmuc != null && this.ds_tien_khoanmuc.length > 0) {
                for (var item of this.ds_tien_khoanmuc) {
                  if (item['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DATMOI) {
                    this.dtienld = item['tien']
                    this.dvatld = item['vat']
                  } else if (item['khoanmuctt_id'] == KHOANMUC_TT.KMTT_CAIDAT) {
                    this.dtiencd = item['tien']
                    this.dvatcd = item['vat']
                  } else if (item['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DICHVU) {
                    this.dichvu.dtien_dv = item['tien']
                    this.dichvu.dvat_dv = item['vat']
                  } else if (item['khoanmuctt_id'] == KHOANMUC_TT.KMTT_MUATHIETBI) {
                    this.dtientbi = item['tien']
                    this.dvattbi = item['vat']
                  } else if (item['khoanmuctt_id'] == KHOANMUC_TT.KMTT_KM_LAPDAT) {
                    this.dtienkmld = item['tien']
                    this.dvatkmld = item['vat']
                  } else if (item['khoanmuctt_id'] == KHOANMUC_TT.KMTT_KM_DATCOC) {
                    this.dtienkmdc = item['tien']
                    this.dvatkmdc = item['vat']
                    if (this.dtienkmdc > dtienkmld) {
                      this.dtienkmld = this.dtienkmdc
                      this.dvatkmld = this.dvatkmdc
                    }
                  } else if (item['khoanmuctt_id'] == KHOANMUC_TT.KMTT_KM_THIETBI) {
                    this.dtien_kmtb = item['tien']
                    this.dvat_kmtb = item['vat']
                  } else if (item['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DATCOC) {
                    this.dtiendatcoc_tt = item['tien']
                    this.dvatdatcoc_tt = item['vat']
                  } else if (item['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DATCOC_KM) {
                    this.dtiendatcoc_km = item['tien']
                    this.dvatdatcoc_km = item['vat']
                  }
                  // else
                  // {
                  //     kmtt_id = item["khoanmuctt_id"];
                  //     tien_kmtt = item["tien"];
                  //     tien_vat = item["vat"];
                  // }
                  //this.HienThiTienKhuyenMaiLapDat(dtienkmld, dvatkmld, dtiendatcoc_km, dtienkmdc, dvatkmdc, dtiendatcoc_tt, kmtt_id, tien_kmtt, tien_vat);
                }
              }
            } catch (err) {
              console.log('🚀 ~ file: ChangeMegaWanConfig.vue ~ line 844 ~ popupClosed ~ err', err)
            }
            // await this.HienThiTienKhuyenMaiLapDat()
            await this.LayTongTien_HDTB()

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
      this.txtMaGD = item.ma_gd
      if (this.txtMaGD != null && this.txtMaGD.trim() != '') {
        await this.txtMaGD_KeyPress()
      }
    },
    Focus(el) {},
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
      let tinhkh_id = this.cboTinh_KN_Moi
      this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupDSHuongKNV2')
      this.popupComponentName = 'popupDSHuongKN'
      this.popupComponentData = {
        kieu: vkieu,
        dichvuvt_id: this.dichvuvt_id,
        tinhkn_id: tinhkh_id,
        db_hd: 1,
        vid: this.khachhang_id
      } // O day dung enums CHUQUAN.VNPT_TINH nhung chi co 1 gia tri duy nhat = 9 nen loai bo enums luon!
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
    ShowPopupCTV(evt) {
      console.log('ShowPopupCTV', evt)
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
    // LAY_DS_HOPDONG_TB_THEO_HDKH_ID
    lay_ds_hopdong_thuebao_theo_hdkh: async function (hdKhId) {
      let donviId = this.$root.token.getDonViID()
      let donvidlId = this.$root.token.getDonViDuLieuID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let input = { hdKhId: hdKhId }
      let data = this.GetData(await api.lay_ds_hopdong_thuebao_theo_hdkh(this.axios, input))
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
      // let input = {
      //   thuebao_id: thuebao_id,

      // }
      try {
        let input = {
          in_thuebao_id: thuebao_id
        }
        let data = this.GetData(await api.lay_ds_diachi_theo_dbtbid(this.axios, input))
        return data
      } catch (err) {
        return []
      }
    },
    lay_ds_danhba_megawan: async function (thuebaoId) {
      let input = {
        thuebaoId: thuebaoId
      }
      let data = this.GetData(await api.lay_ds_danhba_megawan(this.axios, input))
      return data
    },
    async ThemMoi_HopDong(huonggiao_tn) {
      await this.sp_themmoi_hd_tdts_megawan(huonggiao_tn)
    },
    sp_themmoi_hd_tdts_megawan: async function (huonggiao_tn) {
      let phanvungid = this.$root.token.getPhanVungID()
      let input = {
        huonggiao_id: huonggiao_tn,
        js_ct_tienhd: JSON.stringify(this.dsCTTHD),
        js_hd_khachhang: JSON.stringify(this.dsHDKH),
        js_hd_thuebao: JSON.stringify(this.dsHDTB),
        js_hdtb_dv: JSON.stringify(this.dsHDTBDV),
        js_hdtb_mgwan: JSON.stringify(this.dsHDTB_MEWAN),
        js_khuyenmai_hdtb: JSON.stringify(this.dsKM_HDTB),
        js_thongtin_tt: JSON.stringify(this.dsTTTT),
        phanvung_id: phanvungid
      }
      await api.sp_themmoi_hd_tdts_megawan(this.axios, input).then((response) => {
          if (response.data.error == '200' && response.data.error_code === 'BSS-00000000') {
             this.ShowSuccess('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
          }else{
             this.$toast.error("Lỗi thêm mới hợp đồng: "+response.data.message)
          }
      }) 
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
      await api.sp_capnhat_hd_tdts_megawan(this.axios, input).then((response) => {
          if (response.data.error == '200' && response.data.error_code === 'BSS-00000000') {
              this.ShowSuccess('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
          }else{
              this.$toast.error("Lỗi cập nhật hợp đồng: "+response.data.message)
          }
      }) 
    },
    async ThemMoi_HopDongTB(huonggiao_tn) {
      return await this.sp_themmoi_hdtb_tdts_megawan(huonggiao_tn)
    },
    async sp_themmoi_hdtb_tdts_megawan(huonggiao_tn) {
      let phanvungid = this.$root.token.getPhanVungID()
      let input = {
        huonggiao_id: huonggiao_tn,
        js_ct_tienhd: JSON.stringify(this.dsCTTHD),
        js_hd_thuebao: JSON.stringify(this.dsHDTB),
        js_hdtb_dv: JSON.stringify(this.dsHDTBDV),
        js_hdtb_mgwan: JSON.stringify(this.dsHDTB_MEWAN),
        js_khuyenmai_hdtb: JSON.stringify(this.dsKM_HDTB),
        js_thongtin_tt: JSON.stringify(this.dsTTTT),
        phanvung_id: phanvungid
      }
      await api.sp_themmoi_hdtb_tdts_megawan(this.axios, input).then((response) => {
          if (response.data.error == '200' && response.data.error_code === 'BSS-00000000') {
            this.ShowSuccess('Thêm thuê bao thành công!')
          }else{
            this.$toast.error("Lỗi thêm mới hợp đồng: "+response.data.message)
          }
      })  
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
      if (data != null && data.length > 0) {
        let item = data[0]
        let message = `Số máy/Acc: ${ma_tb} đang được lập bởi hợp đồng (phụ lục) ${item.ten_loaihd} có mã giao dịch ${item.ma_gd} do User ${item.nguoi_cn} thuộc đơn vị ${item.ten_dv} thực hiện vào ngày ${item.ngay_yc}`
        this.ShowError(message)
        return true
      }
      return false
    },
    kiemtra_hd_bancheo_tc: async function (thuebao_id, loaihd_id) {
      //let donvidlId = this.$root.token.getDonViID()
      let input = { thuebao_id: thuebao_id, loaihd_id: loaihd_id }
      let data = this.GetData(await api.kiemtra_hd_bancheo_tc(this.axios, input))
      return data
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
      return data
    },
    get_donvi_id_loaidvId_kieudv_id: async function (thuebao_id) {
      let input = { thuebao_id: thuebao_id }
      let data = this.GetData(await api.get_donvi_id_loaidvId_kieudv_id(this.axios, input))
      return data
    },
    huonggiao_ldv: async function (quytrinh_id, loaidv_dich_id) {
      try {
        let input = { quytrinh_id: quytrinh_id, loaidv_dich_id: loaidv_dich_id }
        let data = this.GetData(await api.huonggiao_ldv(this.axios, input))
        return data
      } catch (err) {
        console.log('🚀 huonggiao_ldv~ err', err)
        return null
      }
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
            } else if (this.dichvuvt_id == DichVuVienThong.MEGAWAN) {
              vtocdotn_id = parseInt(this.cboTocDo)
            } else if (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id == DichVuVienThong.NOIBO_2DIEM) {
              vtocdotn_id = parseInt(this.cboTocDo)
            }
            //Hiếu bổ sung thêm hàm lấy tiền khuyến mại 23.09.2010
            let khuyenmai_id = parseInt(this.cboKhuyenMai)
            // CheckDataFacade obj = new CheckDataFacade();
            if ((await this.kiemtra_thoihan_km(khuyenmai_id, tt_nd.ngay_cn)) == 0) {
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
                        frmChonKhuyenMai frm = new frmChonKhuyenMai(dsKM_HDTB, vtocdotn_id, vmuccuoctn_id, parseInt(cboLoaihinhTB.SelectedValue));
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
      try {
        for (var item of this.ds_tien_khoanmuc) {
          // KMTT_KM_LAPDAT = 19;//Khuyến mại lắp đặt
          if (item['khoanmuctt_id'] == 19) {
            item['tien'] = Math.Abs(this.dtienkmld) * -1
            item['vat'] = Math.Abs(this.dvatkmld) * -1
          }
        }
      } catch (err) {
        console.log('🚀 ~ file: ChangeMegaWanConfig.vue ~ line 1495 ~ HienThiTienKMLapDat ~ err', err)
      }

      await this.LayTongTien_HDTB()
    },
    async LayTongTien_HDTB() {
      console.log('LayTongTien_HDTB:', this.ds_tien_khoanmuc)
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
        console.log(err)
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
    async tsbtnThanhToanTest_Click() {
      try {
        this.loading(true)
        await this.popupThanhToan()
        this.loading(true)
      } catch (err) {
        console.log('🚀 ~ file: ChangeMegaWanConfig.vue ~ line 1575 ~ tsbtnThanhToanTest_Click ~ err', err)
      } finally {
        this.loading(false)
      }
    },
    popupThanhToan() {
      this.popupComponentName = 'popupThanhToan'
      this.popupComponent = () => import('@/modules/contract/setup/Payment/Payment.vue')
      this.Popup('popupComponentMGTT')
    },
    async tsbtnXoa_Click() {
      this.$bvModal
        .msgBoxConfirm('Bạn thật sự muốn xóa phụ lục không ?', {
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
    tsbtnThanhToan_Click() {
      try {
        this.loading(true)
        this.tsbtnThanhToan_Enabled = false
        this.userComponentModule = thanhToan
        // goi su kien load lai ma giao dich
        this.txtMaGDTT = this.txtMaGD
        // tao component neu chua tao, show popup khi da goi xong ham khoi tao
        if (this.paymentloaded) this.$refs.thanhtoanDialog.show(true)
      } catch (err) {
        this.tsbtnThanhToan_Enabled = true
        console.log('tsbtnThanhToan_Click', err)
      } finally {
        this.tsbtnThanhToan_Enabled = true
        // this.loading(false)
      }
    },
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
          return
        }
        if (!(await this.kiemtra_db_khi_themmoi(this.txtMaTB, this.khachhang_id, this.thuebao_id, this.dichvuvt_id))) {
          this.ShowError('Thuê bao không tồn tại trong danh bạ, không thêm mới được ')
          return
        }

        //Hiếu thêm kiểm tra xem thue bao có thuộc khách hàng đang lập phụ lục ko 27/08/2010
        if (this.txtMaTB != '') {
          if (!(await this.kiemtra_tb_cung_makh_tb_pl(this.txtMaGD, this.thuebao_id))) {
            this.ShowError('Thuê bao này không cùng mã khách hàng đang thực hiện thay đổi thông số megawan')
            this.$refs.reftxtMaTB.focus()
            return
          }
        }

        if (await this.kiemtra_thuebao_lap_hopdong_khac(this.txtMaTB, '0', parseInt(this.cboDichVuVT), LoaiHopDong.THAYDOI_THONGSO_MEGAWAN)) {
          return
        }

        let dset = await this.lay_huonggiao_tiepnhan(LoaiHopDong.THAYDOI_THONGSO_MEGAWAN, parseInt(this.cboLoaihinhTB), parseInt(this.cboTocDo), 0, parseInt(this.cboKieuLD))
        let huonggiao_tn = 0
        this.quytrinh_id = 0
        if (dset == null || dset['huonggiao_id'] == null) {
          this.ShowError('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
          return
        }
        huonggiao_tn = parseInt(dset['huonggiao_id'])
        this.quytrinh_id = parseInt(dset['quytrinh_id'])

        await this.TaoDuLieu_HDTB(true)
        await this.TaoDuLieu_HDTB_MGWAN(true)
        await this.TaoDuLieu_THONGTIN_TT(true)
        await this.TaoDuLieu_CT_TIENHD(true)
        await this.TaoDuLieu_HDTBDV(true)
        //thaydoits.ThemMoi_HopDongTB(dsHDTB, dsHDTB_MEWAN, dsTTTT, dsCTTHD, dsHDTBDV, dsKM_HDTB, huonggiao_tn);
        await this.ThemMoi_HopDongTB(huonggiao_tn)
        // call api
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
        this.Loading = true
        let result = await this.HienThiTTHopDongKH(null)
        if (!result) {
          this.ShowError(`Không tìm thấy thông tin hợp đồng với mã giao dịch: ${this.txtMaGD}`)
        }
      } catch (err) {
        this.ShowError(`${err.message}`)
        console.log('txtMaGD_KeyPress', err)
      } finally {
        this.Loading = false
      }
    },
    async txtMaTB_KeyPress() {
      try {
        if (this.txtMaTB != '') {
          this.Loading = true
          let ds = await this.LAY_DS_HOPDONG_THEO_MA_TB()
          if (ds != null && ds.length > 0) {
            await this.HienThiTTHopDongKH(ds)
          } else {
            await this.HienThiTT_DanhBa(this.txtMaTB)
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
          } catch (err) {
            {
            }
          }

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
      } catch (setbuttonerr) {
        console.log('🚀 ~ file: ChangeMegaWanConfig.vue ~ line 1827 ~ SetButton ~ setbuttonerr', setbuttonerr)
      }
    },
    async Clear() {
      try {
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

        this.txtNguoiGT = ''
        this.txtCTV = ''
        this.nguoigt_id = 0
        this.ctv_id = 0
        this.txtMaBSS = ''
        this.txtMaERP = ''
        await this.LayTienTheoKhoanMuc()
      } catch (clearerr) {
        console.log('🚀 ~ file: ChangeMegaWanConfig.vue ~ line 1836 ~ Clear ~ clearerr', clearerr)
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
      if (ds == null) return
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
    async HienThiTT_DanhBa(ma_tb) {
      let ds = await this.lay_danhba_theo_matb(ma_tb, this.dichvuvt_id, 0)
      if (ds != null && ds.length > 0) {
        if (await this.kiemtra_thuebao_lap_hopdong_khac(ma_tb, '0', parseInt(this.cboDichVuVT), LoaiHopDong.THAYDOI_THONGSO_MEGAWAN)) return

        if (parseInt(ds[0]['ky_hd']) == 0) {
          this.ShowError('Khách hàng có Account: ' + this.txtMaTB + ' chưa ký lại hợp đồng gốc. Đề nghị ký lại hợp đồng gốc')
          return
        }

        // Nếu trạng thái thuê bao # " Hoạt động bình thường" ---> không cho phép làm tiếp
        if (parseInt(ds[0]['trangthaitb_id']) != TrangThaiTB.THUONG) {
          this.ShowError('Thuê bao có mã: ' + ds[0]['ma_tb'] + ' đang ở trạng thái: ' + ds[0]['trangthai_tb'] + 'Bạn không thể thực hiện thay đổi thông số cho thuê bao này')
          return
        }
        //Thông tin khách hàng
        this.txtMaKH = ds[0]['ma_kh']
        this.txtMaHD = ds[0]['ma_hd']
        this.khachhang_id = parseInt(ds[0]['khachhang_id'])
        this.txtTenKH = ds[0]['ten_kh']
        this.txtDiaChiKH = ds[0]['diachi_kh']
        if (ds[0]['ngaylap_hd'] != null && ds[0]['ngaylap_hd'] != '') {
          this.dtpNgayLapHD = moment(ds[0]['ngaylap_hd'], 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
        } else {
          this.dtpNgayLapHD = moment().format('DD/MM/YYYY')
        }

        //Thông tin thuê bao
        this.thuebao_id = parseInt(ds[0]['thuebao_id'])
        this.txtTenTB = ds[0]['ten_tb']
        this.diachi_tb = ds[0]['diachi_tb']
        this.diachi_ld = ds[0]['diachi_ld']
        this.txtDiaChiLD = this.diachi_ld
        this.loaitb_id = parseInt(ds[0]['loaitb_id'])
        this.cboLoaihinhTB = this.loaitb_id
        await this.cboLoaihinhTB_Changed(this.cboLoaihinhTB)
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

        ds = await this.lay_ds_danhba_megawan(this.thuebao_id)
        if (ds != null && ds.length > 0) {
          if (this.loaitb_id == LoaiHinhTB.MGW_INTERNET_TRUCTIEP||this.loaitb_id == LoaiHinhTB.NB_INTERNETTT) {
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
            this.cboTocDo_PIR_Cu = parseInt(ds[0]['tocdo_pir_id'])
          } else {
            this.chkTocDo_PIR = false
            this.chkTocDo_PIR_Cu = false
            this.cboTocDo_Pir = parseInt(ds[0]['tocdo_id'])
            this.cboTocDo_PIR_Cu = parseInt(ds[0]['tocdo_id'])
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
          this.txtWanIP = ds[0]['wan_ip']
          this.txtWanIP_Cu = ds[0]['wan_ip']
          this.txtLanIP = ds[0]['lan_ip']
          this.textBox2 = ds[0]['lan_ip']

          this.txtMaBSS = ds[0]['ma_bss']
          this.txtMaERP = ds[0]['id_erp']
        }
        //frmTraCuuCTDanhBa frmTCCTDB = new frmTraCuuCTDanhBa(dichvuvt_id, txtMaTB);
        //frmTCCTDB.ShowDialog();
      } else {
        this.ShowError('Không tìm thấy thông tin về mã thuê bao ' + this.txtMaTB + '')
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
          this.chkCTV = false
        }
        if (data[0]['nhanviengt_id'] != null && data[0]['nhanviengt_id'] != '') {
          this.chkNguoiGT = true
          this.nguoigt_id = parseInt(data[0]['nhanviengt_id'])
          this.txtNguoiGT = await this.mapnhanvien(this.nguoigt_id)
        } else {
          this.txtNguoiGT = ''
          this.chkNguoiGT = false
        }
        this.txtMaDuAn = data[0]['ma_duan']
        let dataKh = await this.lay_ds_hopdong_thuebao_theo_hdkh(this.hdkh_id)
        await this.HienThiTTHopDongTB(dataKh)
        await this.HienThiDanhSacHDTB(dataKh)
        await this.SetButton(3)
        if (this.DsThueBao.data != null && this.DsThueBao.data.length > 1) this.tsbtnXoaTB_Enabled = true
        else this.tsbtnXoaTB_Enabled = false
        return true
      } else {
        await this.SetButton(0)
      }
      return false
    },

    async HienThiTTHopDongTB(datalist) {
      if (datalist != null && datalist.length > 0) {
        let data = datalist[0]
        this.hdtb_id = data['hdtb_id']
        if (data['thuebao_id'] != null && data['thuebao_id'] != '') this.thuebao_id = parseInt(data['thuebao_id'])
        this.txtMaTB = data['ma_tb']
        this.txtTenTB = data['ten_tb']
        this.diachi_tb = data['diachi_tb']
        this.diachi_ld = data['diachi_ld']
        this.txtDiaChiLD = this.diachi_ld
        let _loaitbId = parseInt(data['loaitb_id'])
        try {
          let phanvung_id = this.$root.token.getPhanVungID()
          // let loaiTBDv = this.GetData(
          //   await api.lay_dulieu_bang_theo_dieukien(this.axios, {
          //     schema: 'css',
          //     table_name: 'loaihinh_tb',
          //     list_of_cols: 'dichvuvt_id,loaitb_id',
          //     where: `loaitb_id= ${_loaitbId}`,
          //     order: ''
          //   })
          // )
          let loaiTBDv = this.GetData(
            await api.lay_dichvuvt_loaitb_id(this.axios, {
              loaitbId: _loaitbId
            })
          )

          if (loaiTBDv != null && loaiTBDv.length > 0) {
            let dvvtid = loaiTBDv[0].dichvuvt_id
            this.cboDichVuVT = dvvtid
            await this.loaihinh_tb(dvvtid)
          }
        } catch (err) {}

        this.loaitb_id = parseInt(data['loaitb_id'])
        this.cboLoaihinhTB = this.loaitb_id
        await this.cboLoaihinhTB_Changed(this.cboLoaihinhTB)
        this.cboKieuLD = data['kieuld_id']
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

          if (this.loaitb_id == LoaiHinhTB.MGW_INTERNET_TRUCTIEP||this.loaitb_id == LoaiHinhTB.NB_INTERNETTT) {
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
          if (hdtbmg != null && hdtbmg.Count > 0) {
            //Thông tin thuê bao cũ
            this.cboTocDoCu = parseInt(hdtbmg[0]['tocdo_id'])
            if (this.NotNull(hdtbmg[0]['tocdo_pir_id'])) {
              this.chkTocDo_PIR_Cu = true
              this.cboTocDo_PIR_Cu = parseInt(hdtbmg[0]['tocdo_pir_id'])
            } else {
              this.chkTocDo_PIR_Cu = false
              this.cboTocDo_PIR_Cu = parseInt(hdtbmg[0]['tocdo_id'])
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

            this.dsKM_HDTB = []
            let _hdtb_id=this.hdtb_id
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
      if (data != null && data.length > 1) {
        this.tsbtnXoaTB_Enabled = true
      } else {
        this.tsbtnXoaTB_Enabled = false
      }
    },
    async HienThiTTDiaChi(ds, kieu) {
      if (ds != null && ds.length > 0) {
        if (kieu == 1) {
          //phuongld_id = parseInt(ds.Tables[0].Rows[0]["phuong_id"]);
          //phold_id = parseInt(ds.Tables[0].Rows[0]["pho_id"]);
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
    },
    async TaoDuLieu_HDKH(themmoi) {
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
          this.ShowError(vmagd_temp.split(':')[1])
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
      rowHDKH.NGAY_YC = moment(this.dtpNgayYeuCau, 'DD/MM/YYYY').format('DD/MM/YYYY') + moment().format(' HH:mm:ss')
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

      if (this.hdkh_cha_id > 0) {
        rowHDKH.HDKH_CHA_ID = this.hdkh_cha_id
        this.hdkh_cha_id = 0
      }
      let tt_nd = this.$root.token.getThongTinNguoiDung()
      rowHDKH.NGUOI_CN = nguoi_cn
      rowHDKH.NGAY_CN = moment().format('DD/MM/YYYY HH:mm:ss')
      rowHDKH.MAY_CN = 'web'
      rowHDKH.IP_CN = '127.0.0.1'
      rowHDKH.NGAY_SN = ''
      rowHDKH.NGAYCAP = ''
      rowHDKH.NGAY_HK = ''
      rowHDKH.NGAYHETHAN_GT = ''
      rowHDKH.WEBSITE = ''
      rowHDKH.NGUOI_DD = ''
      rowHDKH.CHUCDANH_DD = ''
      rowHDKH.SO_DT = ''
      rowHDKH.SO_FAX = ''
      rowHDKH.WEBSITE = ''
      rowHDKH.GHICHU = ''
      rowHDKH.PHIEUCSKH_ID = ''
      //Bổ sung cho HCM theo y/c chị Tô Thảo - TP DHNV TTKD
      rowHDKH.MA_DUAN = this.txtMaDuAn
      this.dsHDKH.push(rowHDKH)

      if (themmoi == false) {
        //dsHDKH.AcceptChanges();
        //dsHDKH.HD_KHACHHANG.Rows[0].SetModified();
      }
      return this.dsHDKH
    },
    async TaoDuLieu_HDTB(themmoi) {
      let donvi_id = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let ip = this.$root.token.getIP()
      let may_cn = this.$root.token.getMachine()
      let username = this.$root.token.getUserName()
      let tt_nd = this.$root.token.getThongTinNguoiDung()
      // let dsHDTB = [] global
      this.dsHDTB = []
      if (themmoi == true) {
        this.hdtb_id = await this.GetKey('HD_THUEBAO')
      }
      let rowHDTB = {}
      rowHDTB.HDTB_ID = this.hdtb_id
      rowHDTB.HDKH_ID = this.hdkh_id
      if (this.thuebao_id > 0) rowHDTB.THUEBAO_ID = this.thuebao_id
      rowHDTB.MA_TB = this.txtMaTB
      rowHDTB.TEN_TB = this.txtTenTB
      rowHDTB.DIACHI_TB = this.diachi_tb
      rowHDTB.DIACHI_LD = this.diachi_ld
      rowHDTB.KIEULD_ID = parseInt(this.cboKieuLD)
      rowHDTB.DOITUONG_ID = this.doituong_id
      rowHDTB.TTHD_ID = TrangThaiHD.MOI
      rowHDTB.LOAITB_ID = this.loaitb_id
      rowHDTB.DICHVUVT_ID = this.dichvuvt_id
      if (this.thuebao_id_cha != 0) rowHDTB.THUEBAO_CHA_ID = this.thuebao_id_cha
      rowHDTB.DONVI_ID = donvi_id
      rowHDTB.NGUOI_CN = username
      rowHDTB.NGAY_CN = moment().format('DD/MM/YYYY HH:mm:ss')
      rowHDTB.MAY_CN = 'web'
      rowHDTB.GHICHU = this.txtGhiChu
      rowHDTB.QUYTRINH_ID = this.quytrinh_id
      //Hiếu bổ sung trường tiền đặt cọc cước sử dụng = 0 - 20.10.2010
      //rowHDTB.DATCOC_CSD = 0;
      this.dsHDTB.push(rowHDTB)
      if (themmoi == false) {
        //dsHDTB.AcceptChanges();
        //dsHDTB.HD_THUEBAO.Rows[0].SetModified();
      }
      this.dsKM_HDTB.forEach(function (row) {
        row.HDTB_ID = this.hdtb_id
      })
    },
    async TaoDuLieu_HDTB_MGWAN(themmoi) {
      this.dsHDTB_MEWAN = []
      let rowHDTB_MGWAN = {}
      let data_danhba_megawan = await this.lay_ds_danhba_megawan(this.thuebao_id)
      if (data_danhba_megawan != null && data_danhba_megawan.length > 0) {
        rowHDTB_MGWAN = data_danhba_megawan[0]
      }
      rowHDTB_MGWAN.HDTB_ID = this.hdtb_id
      rowHDTB_MGWAN.TOCDO_ID = parseInt(this.cboTocDo)
      if (this.chkTocDo_PIR) {
        rowHDTB_MGWAN.TOCDO_PIR_ID = parseInt(this.cboTocDo_Pir)
      } else {
        rowHDTB_MGWAN.TOCDO_PIR_ID = null
      }
      //if (parseInt(this.cboLoaiKenh) == LOAIKENH.INTERNET_TRUCTIEP)
      if (parseInt(this.cboLoaihinhTB) == LoaiHinhTB.MGW_INTERNET_TRUCTIEP||parseInt(this.cboLoaihinhTB) == LoaiHinhTB.NB_INTERNETTT) {
        if (this.PhanLoaiMGW != null && this.cboPhanLoaiMGW != null) rowHDTB_MGWAN.PHANLOAI_ID = parseInt(this.cboPhanLoaiMGW)
        if (this.PhanTachTD != null && this.cboPhanTachTD != null) rowHDTB_MGWAN.PHANTACH_TD = parseInt(this.cboPhanTachTD)

        if (this.NotNull(this.txtSoLuong_IP)) {
          rowHDTB_MGWAN.SL_IP = parseInt(this.txtSoLuong_IP)
        } else {
          rowHDTB_MGWAN.SL_IP = 0
        }

        if (this.NotNull(this.txtCuoc_IP)) {
          rowHDTB_MGWAN.CUOC_IP = parseFloat(this.txtCuoc_IP)
        } else {
          rowHDTB_MGWAN.CUOC_IP = 0
        }

        if (this.chkTocDo_NIX) {
          rowHDTB_MGWAN.TOCDO_NIX = parseInt(this.cboTocDo_NIX)
          if (this.NotNull(this.txtCuoc_NIX)) {
            rowHDTB_MGWAN.CUOC_NIX = parseFloat(this.txtCuoc_NIX)
          } else {
            rowHDTB_MGWAN.CUOC_NIX = 0
          }
        } else {
          rowHDTB_MGWAN.TOCDO_NIX = null
          rowHDTB_MGWAN.CUOC_NIX = null
        }

        if (this.chkTocDo_ISP) {
          rowHDTB_MGWAN.TOCDO_ISP = parseInt(this.cboTocDo_ISP)
          if (this.txtCuoc_ISP != '') {
            rowHDTB_MGWAN.CUOC_ISP = parseFloat(this.txtCuoc_ISP)
          } else {
            rowHDTB_MGWAN.CUOC_ISP = 0
          }
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
      }
      if (parseInt(this.cboTinh_KN_Moi) != 0) {
        rowHDTB_MGWAN.TINH_KN_ID = parseInt(this.cboTinh_KN_Moi)
      } else {
        rowHDTB_MGWAN.TINH_KN_ID = null
      }
      if (parseInt(this.cboKieuLD) == this.KieuLapDat.HNI_DOI_CONGNGHE_GPON || parseInt(this.cboKieuLD) == this.KieuLapDat.HNI_DOI_CONGNGHE_MENE) {
        rowHDTB_MGWAN.CONGNGHE_ID = parseInt(this.cboCongNghe_Moi)
      }
      rowHDTB_MGWAN.LOAIMD_ID = parseInt(this.cboLoaiModem)
      rowHDTB_MGWAN.LOAIKENH_ID = parseInt(this.cboLoaiKenh)
      rowHDTB_MGWAN.THIETBIDC_ID = parseInt(this.cboLoaiTBiWan)
      rowHDTB_MGWAN.HUONG_KN = this.vhuongkn
      rowHDTB_MGWAN.CHUQUAN_ID = parseInt(this.cboChuQuanMEGAWAN)
      rowHDTB_MGWAN.TRANGBI_ID = parseInt(this.cboTrangBiMEGAWAN)
      rowHDTB_MGWAN.THOIHAN_ID = parseInt(this.cboThoiHanMEGAWAN)
      rowHDTB_MGWAN.MA_PL = this.txtMaHD_TTKD
      rowHDTB_MGWAN.MA_BSS = this.txtMaBSS
      rowHDTB_MGWAN.ID_ERP = this.txtMaERP
      if (this.chkCongVan) {
        rowHDTB_MGWAN.MA_CV = this.txtCongVan
        rowHDTB_MGWAN.NGAY_CV = this.dtpNgayCV
      } else {
        rowHDTB_MGWAN.MA_CV = null
        rowHDTB_MGWAN.NGAY_CV = null
      }
      rowHDTB_MGWAN.SITE_ID = this.txtSiteID
      rowHDTB_MGWAN.DIACHI_KN = this.txtDiaChi_KN
      rowHDTB_MGWAN.MA_LT = this.vsoao

      if (this.chkDoiTac) {
        rowHDTB_MGWAN.DOITAC_ID = parseInt(this.cboDoiTac)
        if (this.txtCuocTKDT != '') {
          rowHDTB_MGWAN.CUOC_TKDT = parseFloat(this.txtCuocTKDT)
        } else {
          rowHDTB_MGWAN.CUOC_TKDT = 0
        }
        if (this.txtCuocTCDT) {
          rowHDTB_MGWAN.CUOC_TCDT = parseFloat(this.txtCuocTCDT)
        } else {
          rowHDTB_MGWAN.CUOC_TCDT = 0
        }

        rowHDTB_MGWAN.MAHD_DT = this.txtMaHD_DT
      } else {
        rowHDTB_MGWAN.DOITAC_ID = null
        rowHDTB_MGWAN.CUOC_TKDT = 0
        rowHDTB_MGWAN.CUOC_TCDT = 0
        rowHDTB_MGWAN.MAHD_DT = null
      }

      if (this.txtCuocHT != '') {
        rowHDTB_MGWAN.CUOC_HT = parseFloat(this.txtCuocHT)
      } else {
        rowHDTB_MGWAN.CUOC_HT = 0
      }
      if (this.txtCuocTBI != '') {
        rowHDTB_MGWAN.CUOC_TBI = parseFloat(this.txtCuocTBI)
      } else {
        rowHDTB_MGWAN.CUOC_TBI = 0
      }
      if (this.txtCuocTC != '') {
        rowHDTB_MGWAN.CUOC_TC = parseFloat(this.txtCuocTC)
      } else {
        rowHDTB_MGWAN.CUOC_TC = 0
      }
      if (this.txtCuocTK != '') {
        rowHDTB_MGWAN.CUOC_TK = parseFloat(this.txtCuocTK)
      } else {
        rowHDTB_MGWAN.CUOC_TK = 0
      }
      rowHDTB_MGWAN.LAN_IP = this.txtLanIP
      rowHDTB_MGWAN.WAN_IP = this.txtWanIP

      this.dsHDTB_MEWAN.push(rowHDTB_MGWAN)
      if (themmoi == false) {
        // dsHDTB_MEWAN.AcceptChanges();
        // dsHDTB_MEWAN.HDTB_MGWAN.Rows[0].SetModified();
      }
      return this.dsHDTB_MEWAN
    },
    async TaoDuLieu_THONGTIN_TT(themmoi) {
      // ko co code
    },
    async TaoDuLieu_CT_TIENHD(themmoi) {
      this.dsCTTHD = []

      for (const km of this.ds_tien_khoanmuc) {
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
      let tt_nd = this.$root.token.getThongTinNguoiDung()
      this.dsHDTBDV = []
      //let dtTemp = bangts.GetDataSql("select donvi_id, loaidv_id, kieudv_id from " + DatabaseConstants.DB2 + ".dbtb_dv where thuebao_id =" + thuebao_id);
      let dtTemp = await this.get_donvi_id_loaidvId_kieudv_id(this.thuebao_id)
      if (dtTemp != null) {
        for (const temp of dtTemp) {
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
      let j=0;
      if (ds_thaotac != null && ds_thaotac.length > 0) {
        for (const thaotac of ds_thaotac) {
          if (parseInt(thaotac['loaidv_dich_id']) == LOAI_DV.TTKD_NGHIEMTHU) {
            let row = {}
            row.HDTB_ID = this.hdtb_id
            row.LOAIDV_ID = LOAI_DV.TTKD_NGHIEMTHU
            row.DONVI_ID = donviId
            row.KIEUDV_ID = 2
            this.dsHDTBDV.push(row)
          }
          if (parseInt(ds_thaotac[j]['loaidv_dich_id']) == LOAI_DV.TTDH_NGHIEMTHU) {
            let vluong_id = parseInt(ds_thaotac[j]['luong_id'])
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
          j=j+1;
        }
      }
      return this.dsHDTBDV
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
      this.KhuyenMaiRaw = data
      if (data != null) this.KhuyenMai = data.map((x) => ({ id: x.khuyenmai_id, text: x.ten_km }))
      else this.KhuyenMai = []
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
      try {
        this.Loading = true
        await this.CapNhat()
      } catch (err) {
        console.log(err)
      } finally {
        this.Loading = false
      }
    },
    async CapNhat() {
      try {
        if (!(await this.KiemTraDL_KhachHang())) return
        if (!(await this.KiemTraDL_ThueBao())) return
        if (this.txtCuocTC != '') {
          let s1 = this.txtCuocTC
        }
        if (this.txtCuocTK != '') {
          let s2 = this.txtCuocTK
        }
        if (this.txtSoLuong_IP != '') {
          if (parseInt(this.txtSoLuong_IP) > 999) {
            this.ShowError('Kiểm tra lại số lượng IP')
            return
          }
        }
        if (parseInt(this.cboKieuLD) == this.KieuLapDat.HNI_DOI_CONGNGHE_GPON) {
          if (parseInt(this.cboCongNghe_Moi) != 3) {
            this.ShowError('Hãy chọn công nghệ mới là Gpon')
            return
          }
        }
        if (this.tsbtnNhapMoi_Enabled == false) {
          console.log(`Thêm mới hđ`)
          if (!(await this.kiemtra_db_khi_themmoi(this.txtMaTB, this.khachhang_id, this.thuebao_id, this.dichvuvt_id))) {
            this.ShowError('Thuê bao không tồn tại trong danh bạ, không cập nhật được ')
            return
          }
          if (await this.kiemtra_thuebao_lap_hopdong_khac(this.txtMaTB, '0', parseInt(this.cboDichVuVT), LoaiHopDong.THAYDOI_THONGSO_MEGAWAN)) return

          let dshg = await this.LayHuongGiao_TiepNhan(LoaiHopDong.THAYDOI_THONGSO_MEGAWAN, parseInt(this.cboLoaihinhTB), parseInt(this.cboTocDo), 0, parseInt(this.cboKieuLD))
          this.quytrinh_id = 0
          let huonggiao_tn = 0
          if (dshg == null || dshg['huonggiao_id'] == null) {
            this.ShowError('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
            return
          }
          huonggiao_tn = parseInt(dshg['huonggiao_id'])
          this.quytrinh_id = parseInt(dshg['quytrinh_id'])
          await this.TaoDuLieu(true)
          if (this.loi_sinh_magd) {
            this.loi_sinh_magd = false
            return
          }
          //ThemMoi_HopDong
          // this.ThemMoi_HopDong(dsHDKH, dsHDTB, dsHDTB_MEWAN, dsTTTT, dsCTTHD, dsHDTBDV, dsKM_HDTB, huonggiao_tn);
          await this.ThemMoi_HopDong(huonggiao_tn)
          await this.SetButton(3)
          let dataHDTB = await this.lay_ds_hopdong_thuebao_theo_hdkh(this.hdkh_id)
          await this.HienThiDanhSacHDTB(dataHDTB)
        } else {
          console.log(`Cập nhật thuê bao`)
          // let dtTemp = bangts.GetDataSql("select ma_tb, dichvuvt_id from " + DatabaseConstants.DB2 + ".hd_thuebao where hdkh_id =" + hdkh_id);
          // DataRow[] dr = dtTemp.Select("ma_tb = '" + txtMaTB.Trim() + "' and dichvuvt_id = '" + dichvuvt_id + "'");
          let dtTemp = await this.lay_ds_hopdong_thuebao_theo_hdkh(this.hdkh_id)
          let dr = dtTemp.filter((x) => x.dichvuvt_id == this.dichvuvt_id && x.ma_tb == this.txtMaTB)
          if (dr == null || dr.Length <= 0) {
            this.ShowError('Thuê bao không tồn tại trong hợp đồng, không thể cập nhật. Bạn phải Thêm thuê bao!')
            return
          }

          if (!(await this.kiemtra_db_khi_themmoi(this.txtMaTB, this.khachhang_id, this.thuebao_id, this.dichvuvt_id))) {
            this.ShowError('Thuê bao không tồn tại trong danh bạ, không cập nhật được ')
            return
          }

          let dshg = await this.LayHuongGiao_TiepNhan(LoaiHopDong.THAYDOI_THONGSO_MEGAWAN, parseInt(this.cboLoaihinhTB), parseInt(this.cboTocDo), 0, parseInt(this.cboKieuLD))
          let huonggiao_tn = 0
          this.quytrinh_id = 0
          if (dshg == null || dshg['huonggiao_id'] == null) {
            this.ShowError('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
            return
          }
          huonggiao_tn = parseInt(dshg['huonggiao_id'])
          this.quytrinh_id = parseInt(dshg['quytrinh_id'])

          await this.TaoDuLieu(false)
          await this.CapNhat_HopDong()
          //
          //thaydoits.CapNhat_HopDong(dsHDKH, dsHDTB, dsHDTB_MEWAN, dsTTTT, dsCTTHD, dsKM_HDTB);

          //hoangpkn : 14/06/2014
          // Đối với loại hợp đồng có thay đổi tốc độ, mức cước
          // Phải lấy lại huonggiao_tn để update vào bảng giaophieu
          //bangts.Update_huonggiao_tiepnhan(hdtb_id, huonggiao_tn);
          // end hoàng thêm

          await this.update_huonggiao_tiepnhan(this.hdtb_id, huonggiao_tn)
          await this.SetButton(3)
          let dataHDTB = await this.lay_ds_hopdong_thuebao_theo_hdkh(this.hdkh_id)
          await this.HienThiDanhSacHDTB(dataHDTB)
        }
      } catch (err) {
        console.log(err)
        this.ShowError(`${err.message}`)
      }
    },
    async KiemTraDL_KhachHang() {
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
          this.ShowError('Mã khách hàng không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
          this.$refs.reftxtMaKH.focus()
          return false
        }
      }
      if (this.txtMaGD != '') {
        if ((await this.kiemtra_makh_mtt(this.txtMaGD, 3, LoaiHopDong.THAYDOI_THONGSO_MEGAWAN)) == 0) {
          this.ShowError('Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
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
        this.$refs.refcboLoaiTBiWan.focus()
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
          this.$refs.reftxtSoLuong_IP.focus()
          return false
        }
      }

      // var kq_1 = tchopdong.GET_VALUE_FUNC('{?DB2}.KT_TB_CHUYENDOI_LHTB', 'vthuebao_id', thuebao_id, 'vquytrinh_id', quytrinh_id)

      // if (kq_1 != 'OK') {
      //   this.ShowError(kq_1)
      //   return false
      // }

      if (parseInt(this.cboDichVuVT) == DichVuVienThong.MEGAWAN || parseInt(this.cboDichVuVT) == DichVuVienThong.METRONET || parseInt(this.cboDichVuVT) == DichVuVienThong.NOIBO_DADIEM) {
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
        console.log("Không đúng thông tin loại hình của thuê bao '" + this.txtMaTB + "'", _loaitb_id, this.cboLoaihinhTB)
        return false
      }

      if (this.tsbtnNhapMoi_Enabled == true) {
        // hoangpkn : 03/04/2020. Cập nhật. không cho thay đổi kiểu lắp đặt. sẽ phát sinh nhiều rủi ro
        let jkieuld_id = parseInt(await this.mapkieuld_id(this.hdtb_id))
        {
          if (jkieuld_id != parseInt(this.cboKieuLD)) {
            this.ShowError('Bạn không được thay đổi kiểu yêu cầu của hợp đồng')
            return false
          }
        }
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
        let phanvung_id = this.$root.token.getPhanVungID()
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
        //     where: `dichvuvt_id = 8 and ma_tb is not null and phanvung_id=${phanvung_id} and khachhang_id in (select khachhang_id from css.hd_khachhang WHERE hdkh_id = ${hdkh_cha_id} and phanvung_id=${phanvung_id})`,
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
    cboKieuLD_SelectedValueChanged() {
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
            try {
              if (this.TocDo != null && this.TocDo.length > 0) {
                let id = this.TocDo[0].id
                this.cboTocDo_ISP = id
                this.cboTocDo_NIX = id
              }
            } catch (err) {}
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
            // this.cboTocDo_ISP.SelectedIndex = 0
            // this.cboTocDo_NIX.SelectedIndex = 0
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
          //Code cũ không set lại tốc độ -- Thitv.hue Edit
          // try {
          //   if (this.TocDo != null && this.TocDo.length > 0) {
          //     let id = this.TocDo[0].id
          //     this.cboTocDo_ISP = id
          //     this.cboTocDo_NIX = id
          //   }
          // } catch (err) {}
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

          //Code cũ không set lại tốc độ -- Thitv.hue Edit
          // try {
          //   if (this.TocDo != null && this.TocDo.length > 0) {
          //     let id = this.TocDo[0].id
          //     this.cboTocDo_ISP = id
          //     this.cboTocDo_NIX = id
          //   }
          // } catch (err) {}
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
            // this.cboTocDo_ISP.SelectedIndex = 0
            // this.cboTocDo_NIX.SelectedIndex = 0
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
            // this.cboTocDo_ISP.SelectedIndex = 0
            // this.cboTocDo_NIX.SelectedIndex = 0
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

          //Code cũ không set lại tốc độ -- Thitv.hue Edit
          // try {
          //   if (this.TocDo != null && this.TocDo.length > 0) {
          //     let id = this.TocDo[0].id
          //     this.cboTocDo_ISP = id
          //     this.cboTocDo_NIX = id
          //   }
          // } catch (err) {}
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

          //Code cũ không set lại tốc độ -- Thitv.hue Edit
          // try {
          //   if (this.TocDo != null && this.TocDo.length > 0) {
          //     let id = this.TocDo[0].id
          //     this.cboTocDo_ISP = id
          //     this.cboTocDo_NIX = id
          //   }
          // } catch (err) {}
        }

        if (_kieuld_id == this.KieuLapDat.HNI_DOI_CONGNGHE_GPON || _kieuld_id == 922 || _kieuld_id == 923) {
          this.cboCongNghe_Moi_Enabled = true
          this.cboCongNghe_Moi = 3 //Defaul gpon
        }
        // #endregion
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
    async cboDichVuVT_Change(evt) {
      console.log('cboDichVuVT-Changed-Val', evt)
      if (evt != undefined && evt != null && evt > 0) {
        this.Loading = true
        this.dichvuvt_id = evt
        this.cboDichVuVT = evt
      } else {
        this.dichvuvt_id = this.cboDichVuVT
      }
      try {
        await this.loaihinh_tb(this.dichvuvt_id)
        if (this.LoaihinhTB != null && this.LoaihinhTB.length > 0) await this.cboLoaihinhTB_Changed(this.LoaihinhTB[0].id)
      } catch (err) {
        console.log('cboDichVuVT-Changed', err)
      } finally {
        if (evt != undefined && evt != null && evt > 0) {
          this.Loading = false
        }
      }
    },
    GetData: function (response) {
      try {
        if (response.data.error == '200' && response.data.error_code == 'BSS-00000000') {
          return response.data.data
        } else {
          console.log(response.data.error_code)
        }
      } catch (getdataerr) {
        console.log('🚀 ~ file: ChangeMegaWanConfig.vue ~ line 3748 ~ getdataerr', getdataerr)
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
      const collection = document.getElementsByClassName(classText)
      //console.log('focusByClass',collection[0])
      if (collection != null && collection.length > 0) {
        collection[0].focus()
      }
    },
    async HienThi_CTK_NIX_ISP(
      vloai // 2 : nix, 3 isp
    ) {
      if (parseInt(this.cboLoaihinhTB) == LoaiHinhTB.MGW_INTERNET_TRUCTIEP||parseInt(this.cboLoaihinhTB) == LoaiHinhTB.NB_INTERNETTT) {
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
        this.txtCuoc_NIX = 0
        this.txtCuoc_NIX_Enabled= false
        this.cboTocDo_NIX_Enabled=false
      }
      else {
        this.cboTocDo_NIX_Enabled=true
        this.txtCuoc_NIX_Enabled=true
      }
    },
    change_chkTocDo_ISP(evt) {
      console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 4653 ~ change_chkTocDo_ISP ~ evt', evt)
      if (!evt.target.checked) {
        this.txtCuoc_ISP = 0
        this.cboTocDo_ISP_Enabled=false
        this.txtCuoc_ISP_Enabled=false
      }
      else {
        this.cboTocDo_ISP_Enabled=true
        this.txtCuoc_ISP_Enabled=true
      }
    },
    change_chkTocDo_PIR(evt) {
      console.log('🚀 ~ file: ChangeMegaWanConfigCrossSell.vue ~ line 4657 ~ change_chkTocDo_PIR ~ evt', evt)
      if (!evt.target.checked) {
        // this.txtCuocTC=0
      }
    },
    open_thanhToanPopUp() {
      this.loading(true)
    },
    onlyNumber($event) {
      let keyCode = $event.keyCode ? $event.keyCode : $event.which
      if ((keyCode < 48 || keyCode > 57) && keyCode !== 46) {
        // 46 is dot
        $event.preventDefault()
      }
    },
    async cboLoaihinhTB_Changed(val) {
      try {
        this.loaitb_id = val
        this.txtCuoc_ISP = '0'
        this.txtCuoc_NIX = '0'
        this.txtSoLuong_IP = '0'
        this.txtCuoc_IP = '0'
        if (this.loaitb_id == LoaiHinhTB.MGW_INTERNET_TRUCTIEP||this.loaitb_id == LoaiHinhTB.NB_INTERNETTT) {
          this.label38 = 'Tốc độ tổng'
          this.label11 = 'Tốc độ tổng'
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
          this.label11 = 'Tốc độ CIR'
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
          try {
            this.cboTocDo_Pir = this.TocDo[0].id
            this.chkTocDo_PIR_Enabled = true
            this.chkTocDo_PIR = false

            if (this.kt_load == false) {
              if (this.TocDo != null && this.TocDo.length > 0) {
                this.cboTocDo_ISP = this.TocDo[0].id
                this.cboTocDo_NIX = this.TocDo[0].id
              }
            }
          } catch (err) {}
        }
        await this.lay_ds_kieu_ld(val, LoaiHopDong.THAYDOI_THONGSO_MEGAWAN)
        await this.HT_DS_KhuyenMai_Combobox()
      } catch (err) {
        console.log('cboLoaihinhTB', err)
      }
    }
  }
}
</script>
