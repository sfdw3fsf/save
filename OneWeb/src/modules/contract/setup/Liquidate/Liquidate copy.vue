<template src='./Liquidate.html'></template>
<style scoped src='./Liquidate.scss'></style>
<script>
import gridview from '@/components/Shared/gridview.vue'
import select3 from '@/components/form/VueSelect.vue'
import input3 from '@/components/form/VueInput.vue'
import DatePicker from 'vue2-datepicker'
import { mapActions } from 'vuex'
import 'vue2-datepicker/index.css'
import * as moment from 'moment'
import api from './LiquidateAPI'
import {formatDate, formatCurrency} from '@/utils/format'
// import LiquidateAPI from './LiquidateAPI'
import { Page, Filter } from '@syncfusion/ej2-vue-grids'
import { TRANGTHAI_DONGBO, LOAI_HOPDONG, TRANGTHAI_THUEBAO, LOAIHIN_THUEBAO, DICHVU_VIENTHONG, KIEU_LAPDAT, TRANGTHAI_HOPDONG, LOAI_DONVI } from '@/constants'
import DialogGiaoPhieu from './components/DialogGiaoPhieu.vue'
import DialogUpHoSo from './components/DialogUpHoSo.vue'
import DialogLyDoHuy from './components/DialogLyDoHuy.vue'
import DialogSearchAccount from './components/DialogSearchAccount.vue'
import SearchContractModal from '@/modules/contract/setup/ChangeInstallUnit/popup/SearchContract/SearchContractModal.vue'
import DialogTraCuuThueBao from './components/popupTraCuuThueBao.vue'
import VueHtml2pdf from 'vue-html2pdf'
import { SUCCESS_CODE } from '../../../../constants'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import DialogEmail from '@/modules/contract/setup/Liquidate/components/DialogEmail.vue'
import VatTuThueBao from '@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/VatTuThueBao.vue'
import DialogInHoSo from '@/modules/contract/setup/Liquidate/components/InHoSo.vue'
import DialogHenKH from '@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import PopupCapNhatDV from './components/popupCapNhatDV.vue'
import cmFunc from '@/utils/commonFunction'
import listBoxItem from "./components/LyDoHuyListBoxItemTemplate.vue"
import { on } from "@/utils/event"
// import DongBo from "./components/DongBo.vue"
import { pagingAndFilter } from '@/modules/admin/category/Staff/mixins/pagingAndFilter.js'
export default {
  components: {
    gridview,
    select3,
    input3,
    DatePicker,
    DialogGiaoPhieu,
    DialogUpHoSo,
    DialogLyDoHuy,
    DialogSearchAccount,
    SearchContractModal,
    DialogTraCuuThueBao,
    VueHtml2pdf,
    breadcrumb,
    DialogEmail,
    DialogInHoSo,
    VatTuThueBao,
    DialogHenKH,
    PopupCapNhatDV
  },
  mixins: [pagingAndFilter],
  name: 'Liquidate',
  props: {
    inThuebaoId: String,
    inDichVuId: Number
  },
  data: () => {
    return {
      datatemplate: function () {
        return { template: listBoxItem }
      },
      headerBreadcrumb: {
        title: "Thanh lý",
        list: [
          { name: "Lập hợp đồng", active: true },
          { name: "Thanh lý", active: true }
        ]
      },
      toolbarButtons: {
        nhapMoi: true,
        ghiLai: true,
        huy: true,
        xoaHD: true,
        inPL: true,
        inPhieuYC: false,
        dongBo: true,
        upHS: true,
        giaoPhieu: true,
        themTB: true,
        xoaTB: true,
        henLD: true,
        vatTu: true,
        email: true,
        cuocNong: true
      },
      pageSettings: { pageSizes: [10, 50, 100, 200], pageCount: 4, pageSize: 10 },
      selectionOptions: {
        enableToggle: false,
        type: 'Single'
      },
      momentFormat: {
        stringify: (date) => {
          return date ? moment(date).format('DD/MM/YYYY') : ''
        },
        parse: (value) => {
          return value ? moment(value, 'DD/MM/YYYY').toDate() : null
        }
      },
      requestFormInfo: {
        maGiaoDich: '', // 'DTLD-03/0015834',
        maHDPL: null,
        checkboxSoCV: false,
        checkboxSoCVEnable: true,
        soCV: null,
        ngayYC: moment(new Date()).format('DD/MM/YYYY'),
        ngayLHD: moment(new Date()).format('DD/MM/YYYY'),
        ngayCV: moment(new Date()).format('DD/MM/YYYY'),
        ngayCVEnable: true,
        dichVu: 1,
        kieuYeuCau: null,
        maHDTTKD: null,
        loaiHinh: null,
        ngayYCHT: moment(new Date()).format('DD/MM/YYYY'),
        cbxNgayHT: true,
        cbxNgayHTEnable: true,
        cuocNong: '0'
      },
      subscriptionFormInfo: {
        soMayAcc: '',
        tocDo: null,
        huongKN: null,
        siteID: null,
        tenThueBao: null,
        diaChiTB: null,
        maERP: null,
        diaChiLD: null,
        maBSS: null,
        tramVT: null,
        soAo: null,
        ghiChu: null
      },
      customerFormInfo: {
        tenKhachHang: null,
        maKhachHang: null,
        diaChiKH: null,
        diaChiTT: null,
        lyDoHuy: null
      },
      startPointFormInfo: {
        diaChiLD: null,
        tramVT: null,
        soAo: null
      },
      endPointFormInfo: {
        diaChiLD: null,
        tramVT: null,
        soAo: null
      },
      checkboxThuHoiThietBiGiaoDich: false,
      checkboxKhongCanThuHoiThietBi: false,
      dsThueBao: [],
      hdkh_id: null,
      hdtb_id: 0,
      khachhang_id: null,
      thuebao_id: null,
      dichvuvt_id: null,
      quytrinh_id: null,
      h_thuebao_cha_id: 0,
      kiemtra: true,
      doituong_id: null,
      donvi_id: null,
      loaitb_id: null,
      goicuoc_id: null,
      simcard: null,
      doituong_id_neo: null,
      muccuoc_id: 0,
      linhvuc_id: null,
      tocdo_id: 0,
      doitac_id: null,
      kt_kenhphu_ao: null,
      luong_id: 0,
      so_gt: null,
      so_dt: null,
      enable_ngay_ycht: true,
      ts_khong_kt_nocuoc: null,
      _chktudong_thuhoiVT: null,
      kt_thanhly_trongthang: null,
      huygoi_hoancong_tl: null,
      kt_qt_kieuld_isdn: null,
      check_gc: null,
      nhantin_nhanvien_diaban: null,
      KHONG_KTRA_NO_HTVC_ANALOG: null,
      kt_fiber_colapchung: null,
      hdtb_id_cha: null,
      isShowUploadEProfile: false,
      isShowHandoverTicket: false,
      dtThaoTac: [],
      isdn_sochinh: null,
      matb_isdn_chinh: null,
      ds_isdn: null,
      dsHDTB: [{}],
      dsHDKH: [{}],
      dsDKHUY: [],
      dsLyDoHuy: [],
      dsHDTB_DD: [{}],
      dsHDTB_CD: [{}],
      dsHDTB_ADSL: [{}],
      dsHDTB_TSL: [{}],
      dsHDTB_MGW: [{}],
      dsHDTB_GP: [{}],
      dsHDTB_IMS: [{}],
      dsHDTB_CNTT: [{}],
      dsThemTB_chamducSD: [],
      ts_sinhma_gd_theo_donvi: false,
      loi_sinh_magd: false,
      hdkh_cha_id: -1,
      ma_gd_neo: null,
      lbLyDoHuy: {
        SelectedItems: []
      },
      thueBaoTonTai: false,
      inHoSoParams: null,
      dlgVatTuInput: {},
      TRANGTHAI_DONGBO: true,
      ItemList: [] , //update
    }
  },
  computed: {
    async tt_nd () {
      return {
        nhanvien_id: this.$auth.getNhanVienID(),
        donvi_id: this.$auth.getDonViID(),
        ngay_cn: this.$auth.getNgayCapNhat(),
        nguoidung_id: this.$auth.getNguoiDungID(),
        USER_NEO: this.$auth.getUserName(),
        ma_nd: this.$auth.getMaNhanVien(),
        may_cn: await this.$root.token.getMachine(),
        ip: await this.$root.token.getIP(),
        tinh_id: this.$auth.getPhanVungID(),
        ma_tinh: this.$auth.getMaTinh()
      }
    },
    dsThueBaoPrint () {
      return this.dsThueBao.map((rs, index) => {
        return {
          ...rs,
          index: index + 1,
          ngaylap_HD: moment(rs.ngaylap_HD, 'YYYY-MM-DD hh:mm:ss').format('DD/MM/YYYY')
        }
      })
    },
    datePrint () {
      return moment().format('DD')
    },
    monthPrint () {
      return moment().format('MM')
    },
    yearPrint () {
      return moment().format('YYYY')
    },
    listTelecomService () {
      return this.$store.getters['liquidateCommon/listTelecomService']
    },
    listSubscriptionTypeData () {
      return this.$store.getters['liquidateCommon/listSubscriptionType']
    },
    listSubscriptionType () {
      return this.listSubscriptionTypeData.filter(el => el.DICHVUVT_ID === +this.requestFormInfo.dichVu)
    },
    listRequestTypeData () {
      return this.$store.getters['liquidateCommon/listRequestType']
    },
    listChanelSpeed () {
      return this.$store.getters['liquidateCommon/listChanelSpeed']
    },
    listSatellite () {
      return this.$store.getters['liquidateCommon/listSatellite']
    },
    dsNhomHuy () {
      return this.$store.getters['liquidateCommon/dsNhomHuy']
    },
    dsLyDoHuyTheoNhom () {
      return this.$store.getters['liquidateCommon/dsLyDoHuy']
    }
    // dsThueBao () {
    //   return this.$store.getters['liquidateCommon/dsThueBao']
    // }
  },
  mounted () {
    on('LyDoHuyItemDelete', this.onLydoHuyItemDelete)
    document.addEventListener("keyup", this.onKeyupDel)
    this.loadData()
    api.get_app_config().then(rs => {
      this.TRANGTHAI_DONGBO = JSON.parse(rs['keyvalue'].toLowerCase())
      console.log('this.TRANGTHAI_DONGBO', this.TRANGTHAI_DONGBO)
    })
  },
  destroyed () {
    document.removeEventListener("keyup", this.onKeyupDel)
  },
  watch: {
    dsThueBao (val) {
      this.toolbarButtons.inPhieuYC = val.length > 0
    }
  },
  methods: {
    ...mapActions('liquidateCommon', [
      'getListTelecomService',
      'getListSubscriptionType',
      'getListRequestType',
      'getListChanelSpeed',
      'getListSatellite',
      'getListContract',
      'layDsThueBao'
    ]),
    onLydoHuyItemDelete (id) {
      this.dsDKHUY = this.dsDKHUY.filter(item => item.lydohuy_id !== id)
    },
    onKeyupDel (e) {
      if (e.key == "Delete" && this.lbLyDoHuy.SelectedItems.length > 0) {
        const selectedId = this.lbLyDoHuy.SelectedItems[0]
        this.dsDKHUY = this.dsDKHUY.filter(i => i.lydohuy_id != selectedId)
      }
    },
    async beforeDownload ({ html2pdf, options, pdfContent }) {
      await html2pdf().set(options).from(pdfContent).toPdf().get('pdf').then((pdf) => {
        const totalPages = pdf.internal.getNumberOfPages()
        for (let i = 1; i <= totalPages; i++) {
          pdf.setPage(i)
          pdf.setFontSize(10)
          pdf.setTextColor(150)
          pdf.text('Page ' + i + ' of ' + totalPages, (pdf.internal.pageSize.getWidth() * 0.88), (pdf.internal.pageSize.getHeight() - 0.3))
        }
        const blob = pdf.output('blob')
        const blobURL = URL.createObjectURL(blob)
        const iframe = document.createElement('iframe')
        document.body.appendChild(iframe)
        iframe.style.display = 'none'
        iframe.src = blobURL
        iframe.onload = function () {
          setTimeout(function () {
            iframe.focus()
            iframe.contentWindow.print()
          }, 1)
        }
      })
    },
    downloadPDF () {
      this.$refs.html2Pdf.generatePdf()
    },
    initSpliter () {
      window['Split'](['#boxLeft', '#boxRight'], {
        sizes: [40, 60],
        gutterSize: 16,
        onDragEnd: function (sizes) {}
      })
      // window['Split'](['#boxTop', '#boxBottom'], {
      //   sizes: [65, 35],
      //   direction: 'vertical'
      // })
    },
    async loadData () {
      this.setButton(0)
      this.requestFormInfo.ngayYC = formatDate(new Date())
      this.$store.dispatch('liquidateCommon/getListTelecomService').then(rs => {
        setTimeout(() => {
          if (this.listTelecomService.length > 0) {
            this.requestFormInfo.dichVu = this.listTelecomService[0].DICHVUVT_ID
            this.dichvuvt_id = this.listTelecomService[0].DICHVUVT_ID
          }
        }, 300)
      })
      // TODO
      // bangts.HT_GoiCuoc_DD_Combobox(cboGoiCuocDD, LoaiHinhTB.DIDONGTRASAU);
      this.$store.dispatch('liquidateCommon/getListSatellite', {'loaidv_id': 5})
      this.$store.dispatch('liquidateCommon/getListChanelSpeed')
      this.$store.dispatch('liquidateCommon/getListSubscriptionType')
      // this.getListRequestType(,LOAI_HOPDONG.CHAMDUT_SD)
      // tsbnGuiCatMo.Visible = false;
      // tsbtnKichHoat.Enabled = false;
      let dataTSMD = {
        'kieu_id': -1
      }
      const dsThamSoMD = await api.lay_ds_thamso_md(dataTSMD)
      if (!dsThamSoMD || dsThamSoMD.length === 0) {
        this.$toast.error('Không tìm thấy thông tin mặc định')
      }
      if (dsThamSoMD.length > 0) {
        if (dsThamSoMD.filter(el => el.ma_ts === 'KHONG_KT_NO_THANHLY').length > 0) {
          if (dsThamSoMD.filter(el => el.ma_ts === 'KHONG_KT_NO_THANHLY')[0].ten_ts.trim() === '1') {
            this.ts_khong_kt_nocuoc = 1
          }
        }
        if (dsThamSoMD.filter(el => el.ma_ts === 'CHECK_THUHOI_VATTU').length > 0) {
          if (dsThamSoMD.filter(el => el.ma_ts === 'CHECK_THUHOI_VATTU')[0].ten_ts.trim() === '1') {
            this._chktudong_thuhoiVT = 1
          }
        }
        if (dsThamSoMD.filter(el => el.ma_ts === 'KHONGCHECK_NGAY_YCHT').length > 0) {
          if (dsThamSoMD.filter(el => el.ma_ts === 'KHONGCHECK_NGAY_YCHT')[0].ten_ts.trim() === '1') {
            this.enable_ngay_ycht = false
          }
        }
        if (dsThamSoMD.filter(el => el.ma_ts === 'CHECK_THANHLY_TRONGTHANG').length > 0) {
          this.kt_thanhly_trongthang = dsThamSoMD.filter(el => el.ma_ts === 'CHECK_THANHLY_TRONGTHANG')[0].ten_ts
        }
        if (dsThamSoMD.filter(el => el.ma_ts === 'HUYGOI_KHI_HOANCONG_TL').length > 0) {
          if (dsThamSoMD.filter(el => el.ma_ts === 'HUYGOI_KHI_HOANCONG_TL')[0].ten_ts.trim() === '1') {
            this.huygoi_hoancong_tl = 1
          }
        }
        if (dsThamSoMD.filter(el => el.ma_ts === 'KT_QT_KIEULD_ISDN').length > 0) {
          if (dsThamSoMD.filter(el => el.ma_ts === 'KT_QT_KIEULD_ISDN')[0].ten_ts.trim() === '1') {
            this.kt_qt_kieuld_isdn = true
          }
        }
        if (dsThamSoMD.filter(el => el.ma_ts === 'CHECK_GC_THUHOI').length > 0) {
          if (dsThamSoMD.filter(el => el.ma_ts === 'CHECK_GC_THUHOI')[0].ten_ts.trim() === '1') {
            this.check_gc = true
          }
        }
        if (dsThamSoMD.filter(el => el.ma_ts === 'LHD_NHANTIN_DIABAN').length > 0) {
          if (dsThamSoMD.filter(el => el.ma_ts === 'LHD_NHANTIN_DIABAN')[0].ten_ts.trim() === '1') {
            this.nhantin_nhanvien_diaban = 1
          }
        }
        if (dsThamSoMD.filter(el => el.ma_ts === 'KHONG_KTRA_NO_HTVC_ANALOG').length > 0) {
          if (dsThamSoMD.filter(el => el.ma_ts === 'KHONG_KTRA_NO_HTVC_ANALOG')[0].ten_ts.trim() === '1') {
            this.KHONG_KTRA_NO_HTVC_ANALOG = 1
          }
        }
        if (dsThamSoMD.filter(el => el.ma_ts === 'KT_THAOHUY_FIBER_COLAPCHUNG').length > 0) {
          if (dsThamSoMD.filter(el => el.ma_ts === 'KT_THAOHUY_FIBER_COLAPCHUNG')[0].ten_ts.trim() === '1') {
            this.kt_fiber_colapchung = true
          }
        }
        if (this._chktudong_thuhoiVT === 1) {
          this.checkboxThuHoiThietBiGiaoDich = true
        }
        this.ts_sinhma_gd_theo_donvi = dsThamSoMD.filter(x => x['ma_ts'] === 'SINH_MA_GD_THEO_DV').length > 0
      }
      // if there are input parameters
      if (this.inDichVuId) {
        this.requestFormInfo.dichVu = +this.inDichVuId
      }
      if (this.$route.query) {
        if (this.$route.query.ma_tb) {
          this.subscriptionFormInfo.soMayAcc = this.$route.query.ma_tb
          await this.onMaTBKeyPress({charCode: 13})
          this.setButton(3)
        }
      }
      if (!this.$route.query || !this.$route.query.ma_tb) {
        if (this.inThuebaoId) {
          this.subscriptionFormInfo.soMayAcc = this.inThuebaoId
          await this.onMaTBKeyPress({charCode: 13})
          this.setButton(3)
        }
      }
      if (this.$route.query && this.$route.query.hdkh_cha_id) {
        this.loadTuHopDongTuVan(this.$route.query.hdkh_cha_id)
      }
      this.nhapMoi()
    },
    btnLyDoHuyClick () {
      this.$refs.dlgHuy.show(true)
    },
    setButton (type) {
      this.toolbarButtons.nhapMoi = false
      this.toolbarButtons.ghiLai = false
      this.toolbarButtons.huy = false
      this.toolbarButtons.xoaHD = false
      this.toolbarButtons.themTB = false
      this.toolbarButtons.xoaTB = false
      this.toolbarButtons.giaoPhieu = false
      // TODO
      // tsbnHoanThienDB.Enabled = false;
      // btnPhieuYC.Enabled = false;
      if (type === -1) { // Bat dau
        this.$refs.txtMaTB.focus()
        this.toolbarButtons.ghiLai = true
        this.toolbarButtons.huy = true
      }
      if (type === 0) { // Bat dau
        this.$refs.txtMaTB.focus()
        this.toolbarButtons.nhapMoi = true
        this.clear()
      }
      if (type === 1) { // Nhap moi
        this.$refs.txtMaTB.focus()
        this.toolbarButtons.ghiLai = true
        this.toolbarButtons.huy = true
        this.clear()
      }
      if (type === 2) { // Huy
        this.$refs.txtMaGD.focus()
        this.toolbarButtons.nhapMoi = true
        this.toolbarButtons.xoaHD = true
        this.clear()
      }
      if (type === 3) { // Edit
        this.$refs.txtMaGD.focus()
        this.toolbarButtons.nhapMoi = true
        this.toolbarButtons.ghiLai = true
        this.toolbarButtons.huy = true
        this.toolbarButtons.xoaHD = true
        this.toolbarButtons.themTB = true
        this.toolbarButtons.xoaTB = true
        // TODO
        // btnPhieuYC.Enabled = true;
        this.toolbarButtons.giaoPhieu = true
        // TODO
        if (this.dsThueBao.length > 1) {
          this.toolbarButtons.xoaTB = true
        } else {
          this.toolbarButtons.xoaTB = false
        }
        if (+this.requestFormInfo.dichVu === DICHVU_VIENTHONG.DI_DONG) {
          this.toolbarButtons.giaoPhieu = false
        }
      }
    },
    clear () {
      if (+this.hdtb_id !== 0) {
        // ? TODO
        // if (dsTemp.Tables.Count > 0)
        //   {dsTemp.Clear();}
      }
      this.h_thuebao_cha_id = 0
      this.checkboxThuHoiThietBiGiaoDich = false
      this.checkboxKhongCanThuHoiThietBi = false
      this.requestFormInfo.checkboxSoCV = false
      this.requestFormInfo.soCV = ''
      this.requestFormInfo.cbxNgayHT = false
      this.requestFormInfo.dichVu = 1
      this.requestFormInfo.loaiHinh = null
      this.requestFormInfo.kieuYeuCau = null
      this.hdkh_id = 0
      this.subscriptionFormInfo.siteID = ''
      this.requestFormInfo.maHDTTKD = ''
      this.requestFormInfo.maGiaoDich = ''
      this.requestFormInfo.maHDPL = ''
      this.customerFormInfo.maKhachHang = ''
      this.khachhang_id = 0
      this.customerFormInfo.tenKhachHang = ''
      this.customerFormInfo.diaChiKH = ''
      this.startPointFormInfo.diaChiLD = ''
      this.endPointFormInfo.diaChiLD = ''
      this.startPointFormInfo.soAo = ''
      this.endPointFormInfo.soAo = ''
      this.requestFormInfo.ngayYC = formatDate(new Date())
      this.hdtb_id = 0
      this.thuebao_id = 0
      this.subscriptionFormInfo.tenThueBao = ''
      this.subscriptionFormInfo.diaChiTB = ''
      this.subscriptionFormInfo.diaChiLD = ''
      // TODO WAIT GIAO DIEN
      // lvLyDoHuy.Items.Clear();
      this.$refs.txtMaTB.focus()
      this.customerFormInfo.diaChiTT = ''
      this.subscriptionFormInfo.ghiChu = ''
      this.hdtb_id_cha = 0
      this.subscriptionFormInfo.huongKN = ''
      this.subscriptionFormInfo.soAo = ''
      this.so_gt = ''
      this.so_dt = ''
      this.subscriptionFormInfo.maBSS = ''
      this.subscriptionFormInfo.maERP = ''
      this.subscriptionFormInfo.tramVT = null
      this.subscriptionFormInfo.soMayAcc = ''
      this.dsLyDoHuy = []
      this.dsDKHUY = []
    },
    resetForms () {
    },
    nhapMoi (e) {
      if (e) {
        e.preventDefault()
      }
      this.dsThueBao = []
      this.setButton(1)
    },
    async btnGhiLaiClick () {
      if (!parseInt(this.requestFormInfo.kieuYeuCau)) {
        this.$toast.error('Vui lòng chọn kiểu yêu cầu')
        return
      }
      if (!this.dsDKHUY || this.dsDKHUY.length === 0) {
        this.$toast.error('Vui lòng chọn lý do hủy')
        return
      }
      // let rsLapHDKhac = await api.kiemtra_ma_tb_lap_hopdong_khac(this.subscriptionFormInfo.soMayAcc, this.ma_gd_neo, this.requestFormInfo.dichVu, LOAI_HOPDONG.CHAMDUT_SD)
      // console.log('rs HD khac 1', rsLapHDKhac)
      // if (rsLapHDKhac.length > 0) {
      // } else {
      // }
      // rsLapHDKhac = await api.kiemtra_ma_tb_lap_hopdong_khac(this.subscriptionFormInfo.soMayAcc, 0, this.requestFormInfo.dichVu, LOAI_HOPDONG.CHAMDUT_SD)
      // console.log('rs HD khac 2', rsLapHDKhac)
      // if (rsLapHDKhac.length > 0) {
      //   return
      // }
      let muocCuocId = 0
      if (this.loaitb_id === LOAIHIN_THUEBAO.INTERNET_ADSL || this.loaitb_id === LOAIHIN_THUEBAO.INTERNET_FTTH ||
                           this.loaitb_id === LOAIHIN_THUEBAO.WIFI_FIBER ||
                          this.loaitb_id === LOAIHIN_THUEBAO.INTERNET_1260 || this.loaitb_id === LOAIHIN_THUEBAO.INTERNET_ADSL_TINH_KHAC ||
                          this.loaitb_id === LOAIHIN_THUEBAO.INTERNET_FIBER_TINH_KHAC) {
        muocCuocId = this.muccuoc_id
      }
      //const dset = await api.layhuonggiao_tiepnhan_v2(LOAI_HOPDONG.CHAMDUT_SD, this.loaitb_id, this.tocdo_id, muocCuocId, parseInt(this.requestFormInfo.kieuYeuCau), 8, parseInt(this.requestFormInfo.dichVu))
      let input = {
                            "kieuLdId": parseInt(this.requestFormInfo.kieuYeuCau),
                            "loaiHdId": LOAI_HOPDONG.CHAMDUT_SD,
                            "loaiTbId": this.loaitb_id,
                            "mucCuocId":  muocCuocId,
                            "tocDoId": this.tocdo_id}
      let dset = this.GetData(await api.LayHuongGiao_TiepNhan(this.axios, input));
      // let huongGiao = 0
      this.quytrinh_id = 0
      if (!dset || dset.length <= 0) {
        this.$toast.warning('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
        return
      }
      // this.huongGiao = parseInt(dset[0]['huonggiao_id'])
      this.quytrinh_id = parseInt(dset.quytrinh_id)
      // TODO: chua co api
      // if (kt_fiber_colapchung)
      // {
      //     if (loaitb_id == LoaiHinhTB.INTERNET_FTTH)
      //     {
      //         var dt_kt = _tchd.GET_DATA_PROC("{?DB2}.kt_fiber_colapchung", "vkieu", 0,
      //             "vthuebao_id", thuebao_id, "vhdtb_id", hdtb_id, "vhdkh_id", hdkh_id);
      //         if (dt_kt.Rows.Count > 0)
      //         {
      //             if (dt_kt.Rows[0]["MA_TB"] != "")
      //             {
      //                 Message_Box.ShowWarning("Thuê bao Fiber: " + txtMaTB.Text.Trim() +
      //                     " đang có đường IMS/MyTV lắp trên đường Fiber. Bạn phải làm HĐ thanh lý IMS/MyTV của mã tb "
      //                     + dt_kt.Rows[0]["MA_TB"] + " trước!");
      //                 return;
      //             }
      //         }
      //     }
      // }
      if (!this.checkboxThuHoiThietBiGiaoDich) {
        if (!window.confirm('Bạn chắc chắn không thu hồi thiết bị của khách hàng tại điểm giao dịch?')) {
          return
        }
      }
      if (this.loaitb_id == LOAIHIN_THUEBAO.INTERNET_MYTV) {
        const dtKtra = await api.kiemtra_tb_mytv_chinhphu(this.subscriptionFormInfo.soMayAcc, 1) // new XuLyThueBaoFacade().Kiemtra_tb_mytv_chinhphu(txtMaTB.Text.Trim(), 1);
        if (dtKtra.length > 0) {
          const dsTS = await api.lay_ds_thamso_md_mats("KHONG_KIEMTRA_MYTV_CHINHPHU")
          let khong_ktra = -1
          if (dsTS.length > 0) {
            khong_ktra = dsTS[0]["TEN_TS"]
          }
          if (khong_ktra == 1) {
            const cf = await this.$bvModal.msgBoxConfirm(`Thuê bao ${this.subscriptionFormInfo.soMayAcc} đang là thuê bao MyTV chính của thuê bao: ${dtKtra[0].ma_tb_con}. Bạn có đồng ý tiếp tục?`, {
              okTitle: 'Có',
              cancelTitle: 'Không'
            })
            if (!cf) return
          } else {
            this.$toast.error(`Thuê bao ${this.subscriptionFormInfo.soMayAcc} đang là thuê bao MyTV chính của thuê bao: ${dtKtra[0].ma_tb_con}. Yêu cầu hủy chính phụ trước khi thực hiện!`)
            return
          }
        }
      }
      this.loading(true)
      // Them moi HD
      if (!this.toolbarButtons.nhapMoi) {
        if (parseInt(this.requestFormInfo.loaiHinh) === LOAIHIN_THUEBAO.ISDN2B_CD ||
                            parseInt(this.requestFormInfo.loaiHinh) === LOAIHIN_THUEBAO.ISDN2B_CQ ||
                            parseInt(this.requestFormInfo.loaiHinh) === LOAIHIN_THUEBAO.ISDN30B_CD ||
                            parseInt(this.requestFormInfo.loaiHinh) === LOAIHIN_THUEBAO.ISDN30B_CQ ||
                            parseInt(this.requestFormInfo.loaiHinh) === LOAIHIN_THUEBAO.TRUNGKE_2M ||
                            parseInt(this.requestFormInfo.loaiHinh) === LOAIHIN_THUEBAO.TRUNGKE_THUONG ||
                            parseInt(this.requestFormInfo.loaiHinh) === LOAIHIN_THUEBAO.TRUNGKE_TUONGTU) {
          this.layDanhSachISDNTheoThueBaoID(this.thuebao_id)
          if (this.isdn_sochinh) {
            for (let i = 0; i < this.ds_isdn[0].length; i++) {
            /// Kiểm tra trong danh sách thuê bao thuộc số chính có thuê nào lập phụ lục khác phụ lục này không.
            ///     Nếu có lập hợp đồng khác và chưa hoàn thiện => không cho làm
              let kt = true
              let ds = await api.kiemtra_ma_tb_lap_hopdong_khac(this.ds_isdn[i]['ma_tb'], 0, this.dichvuvt_id, LOAI_HOPDONG.CHAMDUT_SD)
              if (ds && ds.length > 0) {
                kt = false
              }
              if (!kt) {
                this.$toast.warning('Thuê bao ISDN: ' + this.ds_isdn[i]['ma_tb'] + ' đang được lập phụ lục khác và chưa hoàn thành!\n Bạn không thể thực hiện tiếp. ')
                return
              }
            }
            await this.taoDuLieu(true)
            if (this.loi_sinh_magd) {
              this.loi_sinh_magd = false
              return
            }
            const dsHDTB_didong = this.dsHDTB.filter(r => r["DICHVUVT_ID"] == DICHVU_VIENTHONG.DI_DONG).map(r => r["THUEBAO_ID"] || 0)
            const data = {
              'ds': JSON.stringify(this.dsThemTB_chamducSD),
              'ds_hdkh': JSON.stringify(this.dsHDKH),
              'ds_hdtb': JSON.stringify(this.dsHDTB),
              'ds_hdtb_dd': JSON.stringify(this.dsHDTB_DD),
              'ds_lydohuy_hdtb': JSON.stringify(this.dsLyDoHuy),
              'ds_hdtb_cntt': JSON.stringify(this.dsHDTB_CNTT),
              'hdtb_id': null,
              'kieu': 0,
              'txtMaTB': this.requestFormInfo.maGiaoDich,
              'ma_gd_neo': this.ma_gd_neo,
              'cboDichVuVT': this.requestFormInfo.dichVu,
              'cboLoaihinhTB': this.requestFormInfo.loaiHinh,
              'dtpNgayYeuCau': this.requestFormInfo.ngayYC,
              'dsHDTB_didong': dsHDTB_didong
            }
            const rs = await api.capnhat_chamdutsd(data)
            if (rs.error_code == SUCCESS_CODE) {
              this.$toast.success('Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!')
            } else {
              this.$toast.error(rs.message)
            }
          // TODO: chua co api
          // if (chkThuHoi.Checked)
          //     new LapHopDongFacade2().capnhat_giaophieu_nv_thuhoi(hdtb_id, tt_nd.nhanvien_id, 1);
          // else
          // {
          //     int thuhoi = ckbKhong_TH.Checked ? -1 : 0;
          //     new LapHopDongFacade2().capnhat_giaophieu_nv_thuhoi(hdtb_id, tt_nd.nhanvien_id, thuhoi);
          // }
          } else {
            await this.taoDuLieu(true)
            if (this.loi_sinh_magd) {
              this.loi_sinh_magd = false
              return
            }
            const dsHDTB_didong = this.dsHDTB.filter(r => r["DICHVUVT_ID"] == DICHVU_VIENTHONG.DI_DONG).map(r => r["THUEBAO_ID"] || 0)
            const data = {
              'ds': JSON.stringify(this.dsThemTB_chamducSD),
              'ds_hdkh': JSON.stringify(this.dsHDKH),
              'ds_hdtb': JSON.stringify(this.dsHDTB),
              'ds_hdtb_dd': JSON.stringify(this.dsHDTB_DD),
              'ds_lydohuy_hdtb': JSON.stringify(this.dsLyDoHuy),
              'ds_hdtb_cntt': JSON.stringify(this.dsHDTB_CNTT),
              'hdtb_id': null,
              'kieu': 0,
              'txtMaTB': this.requestFormInfo.maGiaoDich,
              'ma_gd_neo': this.ma_gd_neo,
              'cboDichVuVT': this.requestFormInfo.dichVu,
              'cboLoaihinhTB': this.requestFormInfo.loaiHinh,
              'dtpNgayYeuCau': this.requestFormInfo.ngayYC,
              'dsHDTB_didong': dsHDTB_didong
            }
            const rs = await api.capnhat_chamdutsd(data)
            if (rs.error_code == SUCCESS_CODE) {
              const arr = rs.data ? rs.data.split('-') : []
              this.quitrinh_id = parseInt(arr.length == 2 ? arr[1] : 0)
              this.$toast.success('Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!')
            } else {
              this.$toast.error(rs.message)
            }
          }
        } else {
          await this.taoDuLieu(true)
          if (this.loi_sinh_magd) {
            this.loi_sinh_magd = false
            return
          }
          const dsHDTB_didong = this.dsHDTB.filter(r => r["DICHVUVT_ID"] == DICHVU_VIENTHONG.DI_DONG).map(r => r["THUEBAO_ID"] || 0)
          const data = {
            'ds': JSON.stringify(this.dsThemTB_chamducSD),
            'ds_hdkh': JSON.stringify(this.dsHDKH),
            'ds_hdtb': JSON.stringify(this.dsHDTB),
            'ds_hdtb_dd': JSON.stringify(this.dsHDTB_DD),
            'ds_lydohuy_hdtb': JSON.stringify(this.dsLyDoHuy),
            'ds_hdtb_cntt': JSON.stringify(this.dsHDTB_CNTT),
            'hdtb_id': null,
            'kieu': 0,
            'txtMaTB': this.requestFormInfo.maGiaoDich,
            'ma_gd_neo': this.ma_gd_neo,
            'cboDichVuVT': this.requestFormInfo.dichVu,
            'cboLoaihinhTB': this.requestFormInfo.loaiHinh,
            'dtpNgayYeuCau': this.requestFormInfo.ngayYC,
            'dsHDTB_didong': dsHDTB_didong
          }
          const rs = await api.capnhat_chamdutsd(data)
          if (rs.error_code == SUCCESS_CODE) {
            this.$toast.success('Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!')
          } else {
            this.$toast.error(rs.message)
          }
        }
      } else { // Cap nhat HD
        await this.taoDuLieu(false)
        const dsHDTB_didong = this.dsHDTB.filter(r => r["DICHVUVT_ID"] == DICHVU_VIENTHONG.DI_DONG).map(r => r["THUEBAO_ID"] || 0)
        const data = {
          'ds': JSON.stringify(this.dsThemTB_chamducSD),
          'ds_hdkh': JSON.stringify(this.dsHDKH),
          'ds_hdtb': JSON.stringify(this.dsHDTB),
          'ds_hdtb_dd': JSON.stringify(this.dsHDTB_DD),
          'ds_lydohuy_hdtb': JSON.stringify(this.dsLyDoHuy),
          'ds_hdtb_cntt': JSON.stringify(this.dsHDTB_CNTT),
          'hdtb_id': this.hdtb_id,
          'kieu': 1,
          'txtMaTB': this.requestFormInfo.maGiaoDich,
          'ma_gd_neo': this.ma_gd_neo,
          'cboDichVuVT': this.requestFormInfo.dichVu,
          'cboLoaihinhTB': this.requestFormInfo.loaiHinh,
          'dtpNgayYeuCau': this.requestFormInfo.ngayYC,
          'dsHDTB_didong': dsHDTB_didong
        }
        const rs = await api.capnhat_chamdutsd(data)
        if (rs.error_code == SUCCESS_CODE) {
          this.$toast.success('Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!')
        } else {
          this.$toast.error(rs.message)
        }
      }
      this.dsThueBao = []
      const dsHD = await api.lay_ds_hdtb_theo_hdkh_tthd(this.hdkh_id, TRANGTHAI_HOPDONG.MOI)
      this.hienThiDanhSacHDTB(dsHD)
      this.setButton(3)
      this.loading(false)
    },
    GetData: function (response) {
            if (response.data.error === 200 || response.data.error === '200'|| response.data.error === '0') {
                return response.data.data
            } else {
                console.log(response.data.error_code)
            }
            return []
    },
    async btnXoaHDClick () {
      const cf = confirm('Bạn có chắc muốn xóa hợp đồng không ?')
      if (!cf) {
        return
      }
      this.loading(true)
      const hdtbKhacMoi = await api.sp_lay_ds_hdtb_khac_moi_theo_hdkh_id(this.hdkh_id)
      if (hdtbKhacMoi.length > 0) {
        this.$toast.error('Khách hàng đang có thuê bao không ở trạng thái mới (hoặc có thuê bao đã kích hoạt). Không được xóa!')
        this.loading(false)
        return
      }
      const dstb = await api.lay_ds_hdtb_theo_hdkh(this.hdkh_id)
      if (dstb && dstb.length === this.dsThueBao.length) {
        await this.xoaHDKH()
      } else {
        await this.xoaHDTB()
      }
      this.dsThueBao = []
      this.clear()
      this.loading(false)
    },
    async taoDuLieu (themmoi, themTB) {
      if (!themTB) {
        await this.taoDuLieuHDKH(themmoi)
      }
      await this.taoDuLieuHDTB(themmoi)
      this.taoDuLieuLYDOHUY()
      // dsHDTB_DD = new HDTB_DD_DATA();
      // dsHDTB_CD = new HDTB_CD_DATA();
      // dsHDTB_ADSL = new HDTB_ADSL_DATA();
      // dsHDTB_TSL = new HDTB_TSL_DATA();
      // dsHDTB_MGW = new HDTB_MGWAN_DATA();
      // dsHDTB_GP = new HDTB_GP_DATA();
      // dsHDTB_IMS = new HDTB_IMS_DATA();
      // dsHDTB_CNTT = new ITEM_TYPE[0];
      this.dsThemTB_chamducSD = [{
        'MA_GD': this.requestFormInfo.maGiaoDich,
        'MA_GD_NEO': this.ma_gd_neo,
        'MA_TB': this.subscriptionFormInfo.soMayAcc,
        'THUEBAO_ID': this.thuebao_id,
        'LUONG_ID': this.luong_id,
        'NHANVIEN_ID': this.$auth.getNhanVienID(),
        'DONVI_ID': this.$auth.getDonViID(),
        'NGUOI_CN': this.$auth.getMaNhanVien(),
        'MAY_CN': await this.$root.token.getMachine(),
        'IP_CN': await this.$root.token.getIP(),
        'LOAITB_ID': this.loaitb_id,
        'LOAITB_ID_CBO': this.requestFormInfo.loaiHinh,
        'DICHVUVT_ID': this.dichvuvt_id,
        'DICHVUVT_ID_CBO': this.requestFormInfo.dichVu,
        'DOITUONG_ID': this.doituong_id,
        'LOAIHD_ID': this.loaihd_id,
        'HDTB_ID': this.hdtb_id,
        'HDKH_ID': this.hdkh_id,
        'KT_KENHPHU_AO': this.kt_kenhphu_ao,
        'KIEULD_ID': this.requestFormInfo.kieuYeuCau,
        'H_THUEBAO_CHA_ID': this.h_thuebao_cha_id,
        'NHAPMOI': this.toolbarButtons.nhapMoi ? 1 : 0,
        'MUCCUOC_ID': this.muccuoc_id,
        'TOCDO_ID': this.tocdo_id,
        'CHKTHUHOI': this.checkboxThuHoiThietBiGiaoDich ? 1 : 0,
        'CKBKHONG_TH': this.checkboxKhongCanThuHoiThietBi ? 1 : 0,
        'CHKCONGVAN': this.requestFormInfo.checkboxSoCV ? 1 : 0,
        'CHKNGAYHT': this.requestFormInfo.cbxNgayHT ? 1 : 0,
        'MA_PL': this.requestFormInfo.maHDPL,
        'MA_BSS': this.subscriptionFormInfo.maBSS,
        'ID_ERP': this.subscriptionFormInfo.maERP,
        'MA_CV': this.requestFormInfo.soCV,
        'NGAY_CV': this.requestFormInfo.ngayCV,
        'NGAY_YCHT': this.requestFormInfo.ngayYCHT,
        'KHACHHANG_ID': this.khachhang_id
      }]
      if (+this.requestFormInfo.dichVu === DICHVU_VIENTHONG.DI_DONG) { await this.taoDuLieuHDTB_DD(themmoi) }
      if (+this.requestFormInfo.dichVu === DICHVU_VIENTHONG.ADSL ||
                +this.requestFormInfo.dichVu === DICHVU_VIENTHONG.MEGA_EYES ||
                +this.requestFormInfo.dichVu === DICHVU_VIENTHONG.EMAIL ||
                +this.requestFormInfo.dichVu === DICHVU_VIENTHONG.HATANG_VIENTHONG ||
                +this.requestFormInfo.dichVu === DICHVU_VIENTHONG.BRCD_DOITAC) { await this.taoDuLieuHDTB_ADSL(themmoi) }
      if (+this.requestFormInfo.dichVu === DICHVU_VIENTHONG.CO_DINH) { await this.taoDuLieuHDTB_CD(themmoi) }
      if (+this.requestFormInfo.dichVu === DICHVU_VIENTHONG.GPHONE) { await this.taoDuLieuHDTB_GP(themmoi) }
      if (+this.requestFormInfo.dichVu === DICHVU_VIENTHONG.TSL ||
                +this.requestFormInfo.dichVu === DICHVU_VIENTHONG.TSL_DOITAC) { await this.taoDuLieuHDTB_TSL(themmoi) }
      if (+this.requestFormInfo.dichVu === DICHVU_VIENTHONG.MEGAWAN ||
                +this.requestFormInfo.dichVu === DICHVU_VIENTHONG.MGW_DOITAC) { await this.taoDuLieuHDTB_MGWAN(themmoi) }
      if (+this.requestFormInfo.dichVu === DICHVU_VIENTHONG.METRONET) { await this.taoDuLieuHDTB_MGWAN(themmoi) }
      if (+this.requestFormInfo.dichVu === DICHVU_VIENTHONG.IMS ||
                +this.requestFormInfo.dichVu === DICHVU_VIENTHONG.IMS_DOITAC) { await this.taoDuLieuHDTB_IMS(themmoi) }
      if (+this.requestFormInfo.dichVu === DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT ||
                +this.requestFormInfo.dichVu === DICHVU_VIENTHONG.TRUNGTAM_DULIEU ||
                +this.requestFormInfo.dichVu === DICHVU_VIENTHONG.DICHVU_CNTT ||
                +this.requestFormInfo.dichVu === DICHVU_VIENTHONG.HOINGHI_TRUYENHINH) { await this.taoDuLieuHDTB_CNTT(themmoi) }
      await this.taoDuLieuHDTBDV(themmoi)
    },
    async taoDuLieuHDKH (themmoi) {
      if (themmoi) {
        this.hdkh_id = await api.get_keys('HD_KHACHHANG')
      }
      this.dsHDKH[0].HDKH_ID = this.hdkh_id
      let maGD = ''
      if (themmoi) {
        if (this.ts_sinhma_gd_theo_donvi) {
          maGD = await api.sinh_magd_v2(this.$auth.getDonViID(), LOAI_HOPDONG.CHAMDUT_SD)
        } else { maGD = await api.sinh_magd(LOAI_HOPDONG.CHAMDUT_SD) }
        if (!maGD) {
          this.$toast.error('Xẩy ra lổi khi tạo mã giao dịch!')
          this.loi_sinh_magd = true
        }
        this.requestFormInfo.maGiaoDich = maGD
        this.dsHDKH[0].MA_GD = this.requestFormInfo.maGiaoDich
        this.dsHDKH[0].MA_HD = this.requestFormInfo.maHDPL
      } else {
        this.dsHDKH[0].MA_GD = this.requestFormInfo.maGiaoDich
        this.dsHDKH[0].MA_HD = this.requestFormInfo.maHDPL
      }
      this.dsHDKH[0].MA_KH = this.customerFormInfo.maKhachHang
      if (this.khachhang_id > 0) {
        this.dsHDKH[0].KHACHHANG_ID = this.khachhang_id
      }
      this.dsHDKH[0].TEN_KH = this.customerFormInfo.tenKhachHang
      this.dsHDKH[0].DIACHI_KH = this.customerFormInfo.diaChiKH
      this.dsHDKH[0].SO_DT = this.so_dt
      this.dsHDKH[0].SO_GT = this.so_gt
      this.dsHDKH[0].DONVI_ID = this.$auth.getDonViID() // tt_nd.donvi_id;
      this.dsHDKH[0].NHANVIEN_ID = this.$auth.getNhanVienID() // tt_nd.nhanvien_id;
      if (this.khachhang_id > 0) {
        let khachHangId = await api.fn_tt_db_khachhang(this.khachhang_id, 1)
        if (khachHangId !== '' && khachHangId !== '-1') {
          this.dsHDKH[0].KHLON_ID = parseInt(khachHangId)
        } else {
          this.dsHDKH[0].KHLON_ID = 1
        }
      } else {
        this.dsHDKH[0].KHLON_ID = 1
      }
      if (this.hdkh_cha_id > 0) {
        this.dsHDKH[0].HDKH_CHA_ID = this.hdkh_cha_id
        this.hdkh_cha_id = 0
      }
      this.dsHDKH[0].LOAIHD_ID = LOAI_HOPDONG.CHAMDUT_SD
      this.dsHDKH[0].NGUOI_CN = this.$auth.getMaNhanVien() // tt_nd.ma_nd;
      this.dsHDKH[0].NGAY_CN = this.$auth.getNgayCapNhat() // tt_nd.ngay_cn;
      this.dsHDKH[0].MAY_CN = await this.$root.token.getMachine()// tt_nd.may_cn;
      this.dsHDKH[0].IP_CN = await this.$root.token.getIP() // tt_nd.ip;
      this.dsHDKH[0].NGAY_YC = this.requestFormInfo.ngayYC
      this.dsHDKH[0].NGAYLAP_HD = this.requestFormInfo.ngayLHD
    },
    async taoDuLieuHDTB (themmoi) {
      if (themmoi) {
        this.hdtb_id = await api.get_keys('HD_THUEBAO')
      }
      this.dsHDTB[0] = {
        HDTB_ID: this.hdtb_id,
        HDKH_ID: this.hdkh_id,
        GHICHU: this.subscriptionFormInfo.ghiChu,
        KIEULD_ID: this.requestFormInfo.kieuYeuCau, // Convert.ToInt32(cboKieuLD.SelectedValue),
        DOITUONG_ID: this.doituong_id,
        TTHD_ID: TRANGTHAI_HOPDONG.MOI,
        DONVI_ID: this.donvi_id,
        NGUOI_CN: this.$auth.getUserName(), // tt_nd.ma_nd,
        NGAY_CN: this.$auth.getNgayCapNhat(), // tt_nd.ngay_cn,
        MAY_CN: await this.$root.token.getMachine(), // tt_nd.may_cn,
        IP_CN: await this.$root.token.getIP(), // tt_nd.ip,
        QUYTRINH_ID: this.quytrinh_id,
        LOAITB_ID: this.loaitb_id,
        DICHVUVT_ID: this.dichvuvt_id,
        MA_TB: this.subscriptionFormInfo.soMayAcc,
        TEN_TB: this.subscriptionFormInfo.tenThueBao,
        DIACHI_TB: this.subscriptionFormInfo.diaChiTB,
        DIACHI_LD: this.subscriptionFormInfo.diaChiLD
      }
      if (this.thuebao_id > 0) { this.dsHDTB[0].THUEBAO_ID = this.thuebao_id }
      if (this.khachhang_id > 0) { this.dsHDTB[0].KHACHHANG_ID = this.khachhang_id }
      if (this.dichvuvt_id === DICHVU_VIENTHONG.TSL || this.dichvuvt_id === DICHVU_VIENTHONG.TSL_DOITAC) {
        const donViDau = await api.fn_tt_db_thuebao(this.thuebao_id, 6)
        this.dsHDTB[0].DONVI_DAU_ID = donViDau
        const donViCuoi = await api.fn_tt_db_thuebao(this.thuebao_id, 7)
        this.dsHDTB[0].DONVI_CUOI_ID = donViCuoi
      }
      if (this.loaitb_id === LOAIHIN_THUEBAO.PABX) {
        if (this.isdn_sochinh) {
          if (this.hdtb_id_cha > 0) { this.dsHDTB[0].HDTB_CHA_ID = this.hdtb_id_cha }
        }
      }
      if (this.dichvuvt_id === DICHVU_VIENTHONG.DI_DONG || this.dichvuvt_id === DICHVU_VIENTHONG.ADSL ||
                this.dichvuvt_id === DICHVU_VIENTHONG.IMS || this.dichvuvt_id === DICHVU_VIENTHONG.CO_DINH ||
                this.dichvuvt_id === DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id === DICHVU_VIENTHONG.TSL ||
                this.dichvuvt_id === DICHVU_VIENTHONG.METRONET || this.dichvuvt_id === DICHVU_VIENTHONG.TRUNGTAM_DULIEU ||
                this.dichvuvt_id === DICHVU_VIENTHONG.DICHVU_CNTT || this.dichvuvt_id === DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT ||
                this.dichvuvt_id === DICHVU_VIENTHONG.GPHONE || this.dichvuvt_id === DICHVU_VIENTHONG.HOINGHI_TRUYENHINH) {
        this.dsHDTB[0].STATUS = '0'
      }
    },
    taoDuLieuLYDOHUY () {
      this.dsLyDoHuy = []
      for (const item of this.dsDKHUY) {
        let _item = {}
        for (const key in item) {
          _item[key.toUpperCase()] = item[key]
        }
        _item['HDTB_ID'] = this.hdtb_id
        _item['NGAY_YC'] = this.requestFormInfo.ngayYC
        this.dsLyDoHuy.push(_item)
      }
    },
    taoDuLieuHDTB_DD () {
      this.dsHDTB_DD[0].HDTB_ID = this.hdtb_id
      this.dsHDTB_DD[0].MA_GD_NEO = this.ma_gd_neo
      this.dsHDTB_DD[0].SIMCARD = this.simcard
      this.dsHDTB_DD[0].GOICUOC_ID = this.goicuoc_id
      this.dsHDTB_DD[0].DOITUONG_ID_NEO = this.doituong_id_neo
      this.dsHDTB_DD[0].KIEU_TL = this.kieu_tl
    },
    taoDuLieuHDTB_ADSL () {},
    taoDuLieuHDTB_CD () {},
    taoDuLieuHDTB_GP () {},
    taoDuLieuHDTB_TSL () {},
    taoDuLieuHDTB_MGWAN () {},
    taoDuLieuHDTB_IMS () {},
    async taoDuLieuHDTB_CNTT () {
      try {
        const rs = await api.lay_ds_danhba_cntt(this.thuebao_id)
      if (!rs || rs.length === 0) {
        return
      }
      for (let i = 0; i < this.ItemList.length; i++) {
        const fieldName = this.ItemList[i]['sFieldName']
        if (fieldName == 'MA_TB') continue
        const value = rs[0][fieldName.toLowerCase()]
        if (value) {
          this.ItemList[i].sValue = value
        }
      }
      this.dsHDTB_CNTT = [...this.ItemList]
      if (parseInt(this.requestFormInfo.loaiHinh) == 116) {
        this.dsHDTB_CNTT.push({ sFieldName: 'EE_ID', sValue: rs[0]['ee_id'] })
        this.dsHDTB_CNTT.push({ sFieldName: 'REQ_ID', sValue: rs[0]['req_id'] })
        this.dsHDTB_CNTT.push({ sFieldName: 'CTS_ID', sValue: rs[0]['cts_id'] })
        this.dsHDTB_CNTT.push({ sFieldName: 'CHUTHE_CTS', sValue: rs[0]['chuthe_cts'] })
        this.dsHDTB_CNTT.push({ sFieldName: 'NHACC_CTS', sValue: rs[0]['nhacc_cts'] })
      }
      } catch (error) {
       console.log(error)
      }
    },
    async taoDuLieuHDTBDV () {
      this.dsHDTBDV = []
      try {
        const dtTemp = await api.sp_lay_ds_dbtb_dv(this.thuebao_id)
        for (let i = 0; i < dtTemp.length; i++) {
          const row = {}
          row.HDTB_ID = this.hdtb_id
          row.LOAIDV_ID = dtTemp[i]["loaidv_id"]
          row.DONVI_ID = dtTemp[i]["donvi_id"]
          row.KIEUDV_ID = dtTemp[i]["kieudv_id"]
          this.dsHDTBDV.push(row)
        }
      } catch (e) {
        console.error(e)
      }
    },
    huy (e) {
      e.preventDefault()
      this.dsThueBao = []
      this.setButton(0)
    },
    async xoaHDKH () {
      const rs = await api.xoa_hdkh_v2(this.hdkh_id, LOAI_HOPDONG.CHAMDUT_SD)
      if (rs.error_code == SUCCESS_CODE) {
        this.$toast.success('Xóa dữ liệu hợp đồng khách hàng thành công !')
        if (this.TRANGTHAI_DONGBO && this.requestFormInfo.dichVu == DICHVU_VIENTHONG.DI_DONG) {
          const ds_hd = await api.ts_laphd_thanhly_danhsach_hopdong(this.tt_nd.ma_tinh, this.tt_nd.USER_NEO)
          if (ds_hd && ds_hd.length > 0) {
            const columnNames = ds_hd[0]
            if ("MA_HD" in columnNames && "MA_TB" in columnNames) {
              const current_somay = '84' + this.subscriptionFormInfo.soMayAcc
              const ma_hd_thanhly_neo = ds_hd.filter(r => r['MA_TB'] == current_somay && r["TRANGTHAIHD_ID"] == "2").map(r => r["MA_HD"])
              if (ma_hd_thanhly_neo && ma_hd_thanhly_neo.length && ma_hd_thanhly_neo.length > 0) {
                const result = await api.ts_xoa_hd_thanhly(this.tt_nd.ma_tinh, this.tt_nd.USER_NEO, ma_hd_thanhly_neo[0])
                if (result.error_code != SUCCESS_CODE) {
                  this.$toast.error("Có lỗi khi xóa hđ thanh lý trên vinaphone " + result.message)
                }
              }
            }
          }
        }
      } else {
        this.$toast.error(rs.message)
      }
    },
    async xoaHDTB () {
      const rs = await api.xoa_hdtb(this.hdtb_id)
      if (rs.error_code == SUCCESS_CODE) {
        this.$toast.success('Xóa dữ liệu hợp đồng thuê bao thành công !')
        if (this.TRANGTHAI_DONGBO && this.requestFormInfo.dichVu == DICHVU_VIENTHONG.DI_DONG) {
          const ds_hd = await api.ts_laphd_thanhly_danhsach_hopdong(this.tt_nd.ma_tinh, this.tt_nd.USER_NEO)
          if (ds_hd && ds_hd.length > 0) {
            const columnNames = ds_hd[0]
            if ("MA_HD" in columnNames && "MA_TB" in columnNames) {
              const current_somay = '84' + this.subscriptionFormInfo.soMayAcc
              const ma_hd_thanhly_neo = ds_hd.filter(r => r['MA_TB'] == current_somay && r["TRANGTHAIHD_ID"] == "2").map(r => r["MA_HD"])
              if (ma_hd_thanhly_neo && ma_hd_thanhly_neo.length && ma_hd_thanhly_neo.length > 0) {
                const result = await api.ts_xoa_hd_thanhly(this.tt_nd.ma_tinh, this.tt_nd.USER_NEO, ma_hd_thanhly_neo[0])
                if (result.error_code != SUCCESS_CODE) {
                  this.$toast.error("Có lỗi khi xóa hđ thanh lý trên vinaphone " + result.message)
                }
              }
            }
          }
        }
      } else {
        this.$toast.error(rs.message)
      }
    },
    btnInPL_Click () {
      if (!this.hdkh_id) {
        this.$toast.warning('Chưa có thông tin hợp đồng!')
        return
      }
      this.inHoSoParams = {
        hdkh_id: this.hdkh_id,
        loaihd_id: LOAI_HOPDONG.CHAMDUT_SD,
        phanvung_id: this.$auth.getPhanVungID(),
        ma_gd: this.requestFormInfo.maGiaoDich
      }
      this.$refs.dlgInHopDong.showModal()
    },
    btnDongBoClick () {
      // this.$toast.error('Chức năng đang xây dựng')
      // this.$refs.dlgDongBo.showModal(true)
      window.open(`/#/ext-hopdong/dongbo-visa-vasc-update?tag=2%2B4%3B7%3B8%3B14%2B4`)
    },
    upHS (e) {
      e.preventDefault()
      // this.isShowUploadEProfile = true
      // this.$forceUpdate()
      // // TODO
      // // f.Tag = "1";
      // this.$refs.uploadEProfile.setTransactionCode(this.requestFormInfo.maGiaoDich)
      // if (this.requestFormInfo.maGiaoDich) {
      //   this.$refs.uploadEProfile.findAccounts()
      // }
      // this.$refs.dlgUpHoSo.show(true)
      window.open(`/#/contract/UploadEProfile`)
    },
    async giaoPhieu (e) {
      e.preventDefault()
      try {
        if (this.dsThueBao.length === 0) {
          this.$toast.error('Thuê bao chưa lập HĐ không thể giao phiếu')
          return
        }
        const quytrinhId = await api.fn_tt_hd_thuebao(this.hdtb_id, 3)
        let data = {
          'hdtb_id': this.hdtb_id,
          'hdkh_id': this.hdkh_id,
          'loaitb_id': this.loaitb_id,
          'luong_id': this.luong_id
        }
        try {
          const KT_DONVI_QUYTRINH_CNTT = (await this.axios.get('/web-hopdong/vbn/KT_DONVI_QUYTRINH_CNTT?vhdtb_id=' + this.hdtb_id)).data.data;
          if (KT_DONVI_QUYTRINH_CNTT != "ok")
          {
            this.btnCapNhatDVClick();
            return;
          }
        }
        catch(e) {
          console.error(e);
        }
        const huongGiaoId = await api.fn_giaophieu_chamdutsd(data)
        if (huongGiaoId && huongGiaoId.result && +huongGiaoId.result != 0) {
          window.open(`/#/contract/HandoverTicket?vquytrinh=${quytrinhId}&vhuonggiao_id=${huongGiaoId.result}&vdichvuvt_id=${this.dichvuvt_id}&vloai_hd=${LOAI_HOPDONG.CHAMDUT_SD}`)
        } else {
          this.$toast.error('Không tìm thấy hướng giao')
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error('Có lỗi xảy ra ' + cmFunc.getErrorMessage(ex))
      }
    },
    async btnThemTBClick () {
      try {
        if (!parseInt(this.requestFormInfo.kieuYeuCau)) {
          this.$toast.error('Vui lòng chọn kiểu yêu cầu')
          return
        }
        if (!this.dsDKHUY || this.dsDKHUY.length === 0) {
          this.$toast.error('Vui lòng chọn lý do hủy')
          return
        }
        if (this.dsThueBao.length > 0) {
          for (let i = 0; i < this.dsThueBao.length; i++) {
            if (this.dsThueBao[0]['dichvuvt_ID'] === DICHVU_VIENTHONG.DI_DONG) {
              if (this.requestFormInfo.dichVu !== DICHVU_VIENTHONG.DI_DONG) {
                this.$toast.warning('Hợp đồng thanh lý chỉ được phép bao gồm các thuê bao di động !')
                return
              }
            } else {
              if (this.requestFormInfo.dichVu === DICHVU_VIENTHONG.DI_DONG) {
                this.$toast.warning('Bạn không được phép thanh lý thuê bao di động trên cùng hợp đồng với thuê bao khác !')
                return
              }
            }
          }
        }
        this.loading(true)
        let muocCuocId = 0
        if (this.loaitb_id === LOAIHIN_THUEBAO.INTERNET_ADSL || this.loaitb_id === LOAIHIN_THUEBAO.INTERNET_FTTH ||
                            this.loaitb_id === LOAIHIN_THUEBAO.WIFI_FIBER ||
                            this.loaitb_id === LOAIHIN_THUEBAO.INTERNET_1260 || this.loaitb_id === LOAIHIN_THUEBAO.INTERNET_ADSL_TINH_KHAC ||
                            this.loaitb_id === LOAIHIN_THUEBAO.INTERNET_FIBER_TINH_KHAC) {
          muocCuocId = this.muccuoc_id
        }
        //const dset = await api.layhuonggiao_tiepnhan_v2(LOAI_HOPDONG.CHAMDUT_SD, this.loaitb_id, this.tocdo_id, muocCuocId, parseInt(this.requestFormInfo.kieuYeuCau), 8, parseInt(this.requestFormInfo.dichVu))
        let input = {
                            "kieuLdId": parseInt(this.requestFormInfo.kieuYeuCau),
                            "loaiHdId": LOAI_HOPDONG.CHAMDUT_SD,
                            "loaiTbId": this.loaitb_id,
                            "mucCuocId":  muocCuocId,
                            "tocDoId": this.tocdo_id}
       let dset = this.GetData(await api.LayHuongGiao_TiepNhan(this.axios, input));
        // let huongGiao = 0
        this.quytrinh_id = 0
        if (!dset || dset.length <= 0) {
          this.$toast.warning('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
          return
        }
        // this.huongGiao = parseInt(dset[0]['huonggiao_id'])
        this.quytrinh_id = parseInt(dset.quytrinh_id)
        await this.themTB()
        this.dsThueBao = []
        if (parseInt(this.dichvuvt_id) === DICHVU_VIENTHONG.DI_DONG) {
          const dshd = await await api.lay_ds_hd_tbdd_theo_hdkh_id(this.hdkh_id) || []
          await this.hienThiDanhSacHDTB(dshd)
        } else {
          const dshd = await api.lay_ds_hdtb_theo_hdkh_tthd(this.hdkh_id, TRANGTHAI_HOPDONG.MOI) || []
          await this.hienThiDanhSacHDTB(dshd)
        }
        if (this.dsThueBao.length > 0) {
          setTimeout(() => {
            this.$refs.lvwThueBao.selectRow(this.dsThueBao.length - 1, false)
          }, 1000)
        }
        this.loading(false)
      } catch (ex) {
        console.error(ex)
        this.$toast.error('Có lỗi xảy ra ' + cmFunc.getErrorMessage(ex))
      }
    },
    async btnVatTuClick () {
      const ds_ttphieu = await api.lay_thongtin_phieu_tn({
        'hdtb_id': this.hdtb_id
      })
      if (ds_ttphieu.length > 0 && ds_ttphieu[0]["kqxl_id"] == "3") {
        this.$toast.error("Bạn phải cập nhật chọn 'Thu hồi thiết bị tại Giao dịch' trước khi nhập vật tư !")
        return
      }
      const phieu_id = parseInt(await api.map_id("min(phieu_id)", "giaophieu", "where hdtb_id =" + this.hdtb_id))
      if (phieu_id != 0) {
        this.dlgVatTuInput = {
          phieu_id: phieu_id,
          loaihd_id: LOAI_HOPDONG.CHAMDUT_SD,
          hdtb_id: this.hdtb_id,
          thuebao_id: this.thuebao_id
        }
        this.$refs.dlgVatTuThueBao.showModal()
      } else {
        this.$toast.error("Bạn chưa chọn phiếu!")
      }
    },
    btnHenLDClick () {
      this.$refs.dlgHenKH.showModal()
    },
    btnCapNhatDVClick () {
      let self = this;
      let dulieu = {
        hdtb_id: self.hdtb_id
      };
      this.$refs.popupCapNhatDV.openDialog(dulieu)
    },
    btnEmailClick () {
      this.$refs['dlgEmail'].showModal(this.hdkh_id, this.khachhang_id)
    },
    async themTB () {
      try {
        await this.taoDuLieu(true, true)
        const rs = await api.themtb_chamdutsd({
          'ds': JSON.stringify(this.dsThemTB_chamducSD),
          'ds_hdtb': JSON.stringify(this.dsHDTB),
          'ds_hdtb_dd': JSON.stringify(this.dsHDTB_DD),
          'ds_lydohuy_hdtb': JSON.stringify(this.dsLyDoHuy),
          'ds_hdtb_cntt': JSON.stringify(this.dsHDTB_CNTT),
          'txtMaTB': this.subscriptionFormInfo.soMayAcc,
          'txtMaGD': this.requestFormInfo.maGiaoDich,
          'cboDichVuVT': this.requestFormInfo.dichVu,
          'cboLoaihinhTB': this.requestFormInfo.loaiHinh,
          'thuebao_id': this.thuebao_id,
          'dtpNgayYeuCau': this.requestFormInfo.ngayYC,
          'hdtb_id': this.hdtb_id
        })
        if (rs.error_code == SUCCESS_CODE) {
          this.$toast.success('Thêm thuê bao thành công!')
        } else {
          this.$toast.error(rs.message)
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error('Có lỗi xảy ra ' + cmFunc.getErrorMessage(ex))
      }
    },
    async btnXoaTBClick () {
      const cf = confirm('Bạn có chắc muốn xóa thuê bao này không ?')
      if (!cf) {
        return
      }
      this.loading(true)
      await api.fn_xoa_thanhly(this.hdtb_id)
      this.loading(false)
      this.$toast.success('Xóa thuê bao thành công')
      this.dsThueBao = []
      const dshd = await api.lay_ds_hdtb_theo_hdkh_tthd(this.hdkh_id, TRANGTHAI_HOPDONG.MOI) || []
      await this.hienThiDanhSacHDTB(dshd)
      if (this.dsThueBao.length > 0) {
        setTimeout(() => {
          this.$refs.lvwThueBao.selectRow(0, false)
        }, 1000)
      }
      // TODO: Chon va hien thi thong tin dong dau tien trong bang
      // if (this.dsThueBao.length > 0) {
      //   this.hdtb_id = 0 // Convert.ToInt32(lvwThueBao.Items[0].SubItems[1].Text)
      //   this.hienThiTTHopDongTB(this.LAY_DS_HOPDONG_TB_THEO_HDTB_ID(this.hdtb_id))
      // }
    },
    henLD () {},
    async tinhCuocNong () {
      this.loading(true)
      this.requestFormInfo.cuocNong = ''
      // this.thuebao_id = 2994427
      if (this.thuebao_id) {
        const date = moment(new Date()).format('YYYYMM') + '01'
        const rs = await api.tinh_cuoc_nong(this.thuebao_id, date)
        this.requestFormInfo.cuocNong = formatCurrency(rs, 0, 3, '.', '')
      } else {
        this.$toast.warning('Không có thuê bao để tìm cước nóng')
      }
      this.loading(false)
    },
    selectSubscriptionTypeOnChange ({value}) {
      if (!value) {
        return
      }
      this.requestFormInfo.kieuYeuCau = null
      this.getListRequestType({loaiTBID: value, loaiHDID: LOAI_HOPDONG.CHAMDUT_SD})
    },
    selectTelecomServiceOnChange () {
      this.dichvuvt_id = this.requestFormInfo.dichVu
      // TODO
      // tsbtnGiaoPhieu.Visible = true;
      // tsbtnCapNhatDB.Visible = false;
      if (+this.requestFormInfo.dichVu === DICHVU_VIENTHONG.DI_DONG) {
      // splitContainer2.Visible = true;
      } else {
      // splitContainer2.Visible = false;
      }
      this.requestFormInfo.loaiHinh = null
      if (this.requestFormInfo.dichVu === DICHVU_VIENTHONG.MEGAWAN ||
      this.requestFormInfo.dichVu === DICHVU_VIENTHONG.TSL ||
      this.requestFormInfo.dichVu === DICHVU_VIENTHONG.METRONET) {
        if (this.enable_ngay_ycht) {
          this.requestFormInfo.cbxNgayHTEnable = true
        } else {
          this.requestFormInfo.cbxNgayHTEnable = false
        }
        this.requestFormInfo.checkboxSoCVEnable = true
      } else {
        this.requestFormInfo.checkboxSoCVEnable = false
        this.requestFormInfo.cbxNgayHTEnable = false
        this.requestFormInfo.soCV = ''
      }
    },
    async onMaGiaoDichKeyPress (e) {
      if (e.charCode !== 13) {
        return
      }
      if (this.requestFormInfo.maGiaoDich && this.requestFormInfo.maGiaoDich.length > 0) {
        this.loading(true)
        this.dsThueBao = []
        const rs = await api.lay_ds_hopdong_theo_ma_gd(this.requestFormInfo.maGiaoDich)
        this.hienThiTTHopDongKH(rs)
        this.loading(false)
      }
    },
    async onMaTBKeyPress (e) {
      if (e.charCode !== 13) {
        return
      }
      if (!this.subscriptionFormInfo.soMayAcc.trim()) {
        return
      }
      this.loading(true)
      // const soMayAcc = this.subscriptionFormInfo.soMayAcc
      // const dichVu = this.requestFormInfo.dichVu
      // const _hdkhID = this.hdkh_id
      this.dsDKHUY = []
      // this.clear()
      // this.subscriptionFormInfo.soMayAcc = soMayAcc
      // this.requestFormInfo.dichVu = dichVu
      // this.hdkh_id = _hdkhID
      // TODO if !kiemTraHD return
      const rs = await api.lay_ds_hopdong_theo_ma_tb({
        'ma_tb': this.subscriptionFormInfo.soMayAcc,
        'in_loaihd_id': LOAI_HOPDONG.CHAMDUT_SD,
        'in_donvi_id': this.$auth.getDonViID(),
        'in_tthd_id': 1,
        'in_nhanvien_id': this.$auth.getNhanVienID(),
        'in_donvi_dl_id': 0,
        'in_dichvuvt_id': this.requestFormInfo.dichVu,
        'in_loaitb_id': 0
      })
      if (rs && rs.length > 0) {
        this.dsThueBao = []
        this.thuebao_id = rs[0]['thuebao_id']
        await this.hienThiTTHopDongKH(rs)
      } else {
        await this.hienThiThongTinDanhBa(this.subscriptionFormInfo.soMayAcc)
        this.hdtb_id = 0
      }
      this.loading(false)
    },
    hienThiDiemDauCuoi (data) {
    },
    hienThiThongTinThueBao (data) {
    },
    async phaiLamKichHoat (quytrinhId) {
      const data = await api.lay_ds_thaotac_theo_quytring_id(quytrinhId)// 159
      if (data && data.length > 0 && data.filter(el => +el.enable === 1 && el.code === 'KICHHOAT_TD').length > 0) {
        return true
      }
      return false
    },
    async gdvHoanThien (quytrinhId) {
      const data = await api.lay_ds_thaotac_theo_quytring_id(quytrinhId)// 159
      if (data && data.length > 0 && data.filter(el => +el.enable === 1 && el.code === 'HOANTHIENHOSO').length > 0) {
        return true
      }
      return false
    },
    async phaiLam (luongId, code) {
      if (this.dtThaoTac.length > 0 && this.dtThaoTac.filter(el => +el.enable === 1 && el.code === code).length > 0) {
        return true
      }
      return false
    },
    async hienThiTTMoRong (hdtbId, dichvuvtId) {
      let ds = []
      this.requestFormInfo.cuocNong = ''
      this.startPointFormInfo.diaChiLD = ''
      this.endPointFormInfo.diaChiLD = ''
      this.startPointFormInfo.soAo = ''
      this.endPointFormInfo.soAo = ''
      this.tocdo_id = 0
      this.muccuoc_id = 0
      this.subscriptionFormInfo.soAo = ''
      this.subscriptionFormInfo.huongKN = ''
      this.subscriptionFormInfo.siteID = ''
      this.requestFormInfo.maHDTTKD = ''
      this.kt_kenhphu_ao = false
      this.requestFormInfo.checkboxSoCVEnable = false
      this.requestFormInfo.checkboxSoCV = false
      this.requestFormInfo.soCV = ''
      this.requestFormInfo.ngayCV = formatDate(new Date())
      this.requestFormInfo.cbxNgayHTEnable = false
      this.requestFormInfo.cbxNgayHT = false
      // ? TODO
      // cbxNgayHT.Enabled = false
      switch (dichvuvtId) {
        case DICHVU_VIENTHONG.CO_DINH:
          ds = await api.lay_ds_hopdong_thuebao_codinh(hdtbId)
          break
        case DICHVU_VIENTHONG.ADSL:
        case DICHVU_VIENTHONG.BRCD_DOITAC:
        case DICHVU_VIENTHONG.MEGA_EYES:
        case DICHVU_VIENTHONG.EMAIL:
          ds = await api.lay_ds_hopdong_thuebao_adsl(hdtbId)
          break
        case DICHVU_VIENTHONG.MEGAWAN:
        case DICHVU_VIENTHONG.METRONET:
        case DICHVU_VIENTHONG.MGW_DOITAC:
          ds = await api.lay_ds_hopdong_thuebao_mgwan(hdtbId)
          break
        case DICHVU_VIENTHONG.TSL:
        case DICHVU_VIENTHONG.TSL_DOITAC:
          ds = await api.lay_ds_hdtb_tsl_theo_hdtb_id(hdtbId)
          break
        case DICHVU_VIENTHONG.GPHONE:
          ds = await api.lay_ds_hopdong_thuebao_gp(hdtbId)
          break
        case DICHVU_VIENTHONG.TRUNGTAM_DULIEU:
        case DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT:
        case DICHVU_VIENTHONG.DICHVU_CNTT:
        case DICHVU_VIENTHONG.HOINGHI_TRUYENHINH:
          ds = await api.lay_ds_hopdong_thuebao_cntt(hdtbId)
          break
        default:
          ds = await api.lay_ds_hopdong_thuebao_codinh(hdtbId)// 1185631
          break
      }
      if (!ds || ds.length <= 0) {
        return
      }
      switch (this.requestFormInfo.dichVu) {
        case DICHVU_VIENTHONG.CO_DINH:
          break
        case DICHVU_VIENTHONG.ADSL:
        case DICHVU_VIENTHONG.BRCD_DOITAC:
          if (ds[0]['tocdo_id']) {
            this.subscriptionFormInfo.tocDo = ds[0]['tocdo_id']
          } else { this.subscriptionFormInfo.tocDo = '' }
          this.tocdo_id = ds[0]['tocdo_id']
          this.muccuoc_id = ds[0]['muccuoc_id']
          if (ds[0]['muccuoc_id']) { this.muccuoc_id = ds[0]['muccuoc_id'] }
          this.subscriptionFormInfo.soAo = ds[0]['ma_lt']
          break
        case DICHVU_VIENTHONG.EMAIL:
          if (ds[0]['tocdo_id']) {
            this.tocdo_id = ds[0]['tocdo_id']
            this.subscriptionFormInfo.tocDo = this.tocdo_id
          } else { this.subscriptionFormInfo.tocDo = '' }
          if (ds[0]['muccuoc_id']) { this.muccuoc_id = ds[0]['muccuoc_id'] }
          this.tocdo_id = ds[0]['tocdo_id']
          this.muccuoc_id = ds[0]['muccuoc_id']
          this.subscriptionFormInfo.soAo = ds[0]['ma_lt']
          break
        case DICHVU_VIENTHONG.MEGA_EYES:
          if (ds[0]['tramtb_id']) { this.subscriptionFormInfo.tramVT = ds[0]['tramtb_id'] }
          if (ds[0]['tocdo_id']) {
            this.tocdo_id = ds[0]['tocdo_id']
            this.subscriptionFormInfo.tocDo = this.tocdo_id
          } else { this.subscriptionFormInfo.tocDo = '' }
          this.tocdo_id = ds[0]['tocdo_id']
          break
        case DICHVU_VIENTHONG.MEGAWAN:
        case DICHVU_VIENTHONG.METRONET:
        case DICHVU_VIENTHONG.MGW_DOITAC:
          this.muccuoc_id = 0
          if (ds[0]['tocdo_id']) {
            this.tocdo_id = ds[0]['tocdo_id']
            this.subscriptionFormInfo.tocDo = this.tocdo_id
          }
          this.subscriptionFormInfo.soAo = ds[0]['ma_lt']
          if (ds[0]['huong_kn']) { this.subscriptionFormInfo.huongKN = await api.lay_text_huongketnoi(ds[0]['huong_kn']) } else { this.subscriptionFormInfo.huongKN = '' }
          this.requestFormInfo.maHDTTKD = ds[0]['ma_pl']
          this.subscriptionFormInfo.siteID = ds[0]['site_id']
          // if (ds[0]['doitac_id']) { this.doitac_id = ds[0]['doitac_id'] }
          if (ds[0]['kp_ao'] === 1) { this.kt_kenhphu_ao = true } else { this.kt_kenhphu_ao = false }
          this.requestFormInfo.checkboxSoCVEnable = true
          if (ds[0]['ma_cv']) {
            this.requestFormInfo.checkboxSoCV = true
            this.requestFormInfo.soCV = ds[0]['ma_cv']
            this.requestFormInfo.ngayCV = formatDate(ds[0]['ngay_cv'])
          } else {
            this.requestFormInfo.ngayCV = formatDate(new Date())
          }
          // //chkNgayHT.Enabled = true;
          // if (enable_ngay_ycht)
          //     chkNgayHT.Enabled = true;
          // else
          //     chkNgayHT.Enabled = false;
          if (this.enable_ngay_ycht) {
            this.requestFormInfo.cbxNgayHTEnable = true
          } else {
            this.requestFormInfo.cbxNgayHTEnable = false
          }
          if (ds[0]['ngay_ycht']) {
            this.requestFormInfo.cbxNgayHT = true
            this.requestFormInfo.ngayYCHT = ds[0]['ngay_ycht']?moment(ds[0]['ngay_ycht'], 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY'):null
          } else {
            this.requestFormInfo.ngayYCHT = formatDate(new Date())
          }
          this.subscriptionFormInfo.maBSS = ds[0]['ma_bss']
          this.subscriptionFormInfo.maERP = ds[0]['id_erp']
          break
        case DICHVU_VIENTHONG.TSL:
        case DICHVU_VIENTHONG.TSL_DOITAC:
          this.requestFormInfo.maHDTTKD = ds[0]['ma_pl']
          if (ds[0]['tocdo_id']) {
            this.tocdo_id = ds[0]['tocdo_id']
            this.subscriptionFormInfo.tocDo = this.tocdo_id
          }
          // if (ds[0]['doitac_id']) { this.doitac_id = ds[0]['doitac_id'] }
          if (ds[0]['daucuoi_id']) {
            this.startPointFormInfo.diaChiLD = ds[0]['diachi']
            this.startPointFormInfo.soAo = ds[0]['ma_lt']
            this.endPointFormInfo.diaChiLD = ds[1]['diachi']
            this.endPointFormInfo.soAo = ds[1]['ma_lt']
          } else {
            this.startPointFormInfo.diaChiLD = ds[1]['diachi']
            this.startPointFormInfo.soAo = ds[1]['ma_lt']
            this.endPointFormInfo.diaChiLD = ds[0]['diachi']
            this.endPointFormInfo.soAo = ds[0]['ma_lt']
          }
          if (ds[0]['ma_cv']) {
            this.requestFormInfo.checkboxSoCV = true
            this.requestFormInfo.soCV = ds[0]['ma_cv']
            this.requestFormInfo.ngayCV = formatDate(ds[0]['ngay_cv'])
          } else {
            this.requestFormInfo.ngayCV = formatDate(new Date())
          }
          // //chkNgayHT.Enabled = true;
          // if (enable_ngay_ycht)
          //     chkNgayHT.Enabled = true;
          // else
          //     chkNgayHT.Enabled = false;
          if (this.enable_ngay_ycht) {
            this.requestFormInfo.cbxNgayHTEnable = true
          } else {
            this.requestFormInfo.cbxNgayHTEnable = false
          }
          if (ds[0]['ngay_ycht']) {
            this.requestFormInfo.cbxNgayHT = true
            this.requestFormInfo.ngayYCHT = formatDate(ds[0]['ngay_ycht'])
          } else {
            this.requestFormInfo.ngayYCHT = formatDate(new Date())
          }
          this.subscriptionFormInfo.maBSS = ds[0]['ma_bss']
          this.subscriptionFormInfo.maERP = ds[0]['id_erp']
          break
        case DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT:
        case DICHVU_VIENTHONG.DICHVU_CNTT:
        case DICHVU_VIENTHONG.TRUNGTAM_DULIEU:
        case DICHVU_VIENTHONG.HOINGHI_TRUYENHINH:
          this.Init_Properties(this.loaitb_id, "00000")
          if (ds && ds.length > 0) {
            if (ds[0]['linhvuc_id']) { this.linhvuc_id = ds[0]['linhvuc_id'] }
            if (ds[0]['tocdo_id']) { this.tocdo_id = ds[0]['tocdo_id'] }
            if (ds[0]['muccuoc_id']) { this.muccuoc_id = ds[0]['muccuoc_id'] }
          } else { this.linhvuc_id = 0 }
          break
      }
    },
    async hienThiTTDBMoRong () {
      this.requestFormInfo.cuocNong = ''
      this.requestFormInfo.maHDTTKD = ''
      this.requestFormInfo.checkboxSoCV = false
      this.requestFormInfo.checkboxSoCVEnable = false
      this.requestFormInfo.soCV = ''
      this.requestFormInfo.ngayCV = formatDate(new Date())
      this.requestFormInfo.cbxNgayHT = false
      this.requestFormInfo.cbxNgayHTEnable = false
      this.startPointFormInfo.diaChiLD = ''
      this.startPointFormInfo.soAo = ''
      this.endPointFormInfo.diaChiLD = ''
      this.endPointFormInfo.soAo = ''
      this.subscriptionFormInfo.huongKN = ''
      this.subscriptionFormInfo.siteID = ''
      let rsKieuTB = []
      switch (this.requestFormInfo.dichVu) {
        case DICHVU_VIENTHONG.CO_DINH:
          rsKieuTB = await api.lay_ds_danhba_codinh(this.thuebao_id)
          break
        case DICHVU_VIENTHONG.TSL:
        case DICHVU_VIENTHONG.TSL_DOITAC:
          rsKieuTB = await api.lay_ds_danhba_tsl(this.thuebao_id)
          break
        case DICHVU_VIENTHONG.ADSL:
        case DICHVU_VIENTHONG.BRCD_DOITAC:
        case DICHVU_VIENTHONG.EMAIL:
          rsKieuTB = await api.lay_ds_danhba_adsl(this.thuebao_id)
          break
        case DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT:
        case DICHVU_VIENTHONG.DICHVU_CNTT:
        case DICHVU_VIENTHONG.TRUNGTAM_DULIEU:
        case DICHVU_VIENTHONG.HOINGHI_TRUYENHINH:
        case 50:
          rsKieuTB = await api.lay_ds_danhba_cntt(this.thuebao_id)
          break
        case DICHVU_VIENTHONG.MEGAWAN:
        case DICHVU_VIENTHONG.METRONET:
        case DICHVU_VIENTHONG.MGW_DOITAC:
          rsKieuTB = await api.lay_ds_danhba_megawan(this.thuebao_id)
          break
        case DICHVU_VIENTHONG.GPHONE:
          rsKieuTB = await api.lay_ds_danhba_gp(this.thuebao_id)
          break
        // case DICHVU_VIENTHONG.MEGA_EYES:
        //   ds = new TraCuuDanhBaFacade().LAY_DS_DB_MEGAEYES_THEO_TBID(this.thuebao_id)
        //   break
        default:
          rsKieuTB = await api.lay_ds_danhba_codinh(this.thuebao_id)
          break
      }
      if (!rsKieuTB || rsKieuTB.length <= 0) {
        return
      }
      switch (+this.dichvuvt_id) {
        case DICHVU_VIENTHONG.CO_DINH:
          if (rsKieuTB[0]['tg_thueso']) { this.requestFormInfo.kieuYeuCau = KIEU_LAPDAT.THANHLY_THUESO_DT } else { this.requestFormInfo.kieuYeuCau = KIEU_LAPDAT.THANH_LY_CODINH }
          break
        case DICHVU_VIENTHONG.ADSL:
        case DICHVU_VIENTHONG.BRCD_DOITAC:
          if (rsKieuTB[0]['tocdo_id']) {
            this.subscriptionFormInfo.tocDo = rsKieuTB[0]['tocdo_id']
          } else { this.subscriptionFormInfo.tocDo = '' }
          if (rsKieuTB[0]['muccuoc_id']) { this.muccuoc_id = rsKieuTB[0]['muccuoc_id'] }
          this.subscriptionFormInfo.soAo = rsKieuTB[0]['ma_lt']
          break
        case DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT:
        case DICHVU_VIENTHONG.DICHVU_CNTT:
        case DICHVU_VIENTHONG.TRUNGTAM_DULIEU:
        case DICHVU_VIENTHONG.HOINGHI_TRUYENHINH:
          this.Init_Properties(this.loaitb_id, "00000")
          if (rsKieuTB.length > 0) {
            if (rsKieuTB[0]['linhvuc_id']) { this.linhvuc_id = rsKieuTB[0]['linhvuc_id'] }
            if (rsKieuTB[0]['tocdo_id']) { this.tocdo_id = rsKieuTB[0]['tocdo_id'] }
            if (rsKieuTB[0]['muccuoc_id']) { this.muccuoc_id = rsKieuTB[0]['muccuoc_id'] }
          } else { this.linhvuc_id = 0 }
          break
        case DICHVU_VIENTHONG.EMAIL:
          if (rsKieuTB[0]['tocdo_id']) {
            this.tocdo_id = rsKieuTB[0]['tocdo_id']
            this.subscriptionFormInfo.tocDo = this.tocdo_id
          } else { this.subscriptionFormInfo.tocDo = '' }
          if (rsKieuTB[0]['muccuoc_id']) { this.muccuoc_id = rsKieuTB[0]['muccuoc_id'] }
          this.subscriptionFormInfo.soAo = rsKieuTB[0]['ma_lt']
          break
        case DICHVU_VIENTHONG.MEGA_EYES:
          if (rsKieuTB[0]['tramtb_id']) { this.subscriptionFormInfo.tramVT = rsKieuTB[0]['tramtb_id'] }
          if (rsKieuTB[0]['tocdo_id']) {
            this.tocdo_id = rsKieuTB[0]['tocdo_id']
            this.subscriptionFormInfo.tocDo = this.tocdo_id
          } else { this.subscriptionFormInfo.tocDo = '' }
          break
        case DICHVU_VIENTHONG.MEGAWAN:
        case DICHVU_VIENTHONG.METRONET:
        case DICHVU_VIENTHONG.MGW_DOITAC:
          if (rsKieuTB[0]['tocdo_id']) {
            this.tocdo_id = rsKieuTB[0]['tocdo_id']
            this.subscriptionFormInfo.tocDo = this.tocdo_id
          }
          this.subscriptionFormInfo.soAo = rsKieuTB[0]['ma_lt']
          if (rsKieuTB[0]['huong_kn']) { this.subscriptionFormInfo.huongKN = await api.lay_text_huongketnoi(rsKieuTB[0]['huong_kn']) } else { this.subscriptionFormInfo.huongKN = '' }
          this.requestFormInfo.maHDTTKD = rsKieuTB[0]['ma_pl']
          this.subscriptionFormInfo.siteID = rsKieuTB[0]['site_id']
          if (rsKieuTB[0]['doitac_id']) { this.doitac_id = rsKieuTB[0]['doitac_id'] }
          if (rsKieuTB[0]['kp_ao'] === 1) { this.kt_kenhphu_ao = true } else { this.kt_kenhphu_ao = false }
          // this.requestFormInfo.checkboxSoCVEnable = true
          this.requestFormInfo.ngayCV = formatDate(new Date())
          // //chkNgayHT.Enabled = true;
          // if (enable_ngay_ycht)
          //     chkNgayHT.Enabled = true;
          // else
          //     chkNgayHT.Enabled = false;
          if (this.enable_ngay_ycht) {
            this.requestFormInfo.cbxNgayHTEnable = true
          } else {
            this.requestFormInfo.cbxNgayHTEnable = false
          }
          this.requestFormInfo.ngayYCHT = formatDate(new Date())
          this.subscriptionFormInfo.maBSS = rsKieuTB[0]['ma_bss']
          this.subscriptionFormInfo.maERP = rsKieuTB[0]['id_erp']
          break
        case DICHVU_VIENTHONG.TSL:
        case DICHVU_VIENTHONG.TSL_DOITAC:
          this.requestFormInfo.maHDTTKD = rsKieuTB[0]['ma_pl']
          if (rsKieuTB[0]['tocdo_id']) {
            this.tocdo_id = rsKieuTB[0]['tocdo_id']
            this.subscriptionFormInfo.tocDo = this.tocdo_id
          }
          if (rsKieuTB[0]['doitac_id']) { this.doitac_id = rsKieuTB[0]['doitac_id'] }
          if (rsKieuTB[0]['daucuoi_id']) {
            this.startPointFormInfo.diaChiLD = rsKieuTB[0]['diachi']
            this.startPointFormInfo.soAo = rsKieuTB[0]['ma_lt']
            this.endPointFormInfo.diaChiLD = rsKieuTB[1]['diachi']
            this.endPointFormInfo.soAo = rsKieuTB[1]['ma_lt']
          } else {
            this.startPointFormInfo.diaChiLD = rsKieuTB[1]['diachi']
            this.startPointFormInfo.soAo = rsKieuTB[1]['ma_lt']
            this.endPointFormInfo.diaChiLD = rsKieuTB[0]['diachi']
            this.endPointFormInfo.soAo = rsKieuTB[0]['ma_lt']
          }
          this.subscriptionFormInfo.maBSS = rsKieuTB[0]['ma_bss']
          this.subscriptionFormInfo.maERP = rsKieuTB[0]['id_erp']
          if (this.enable_ngay_ycht) {
            this.requestFormInfo.cbxNgayHTEnable = true
          } else {
            this.requestFormInfo.cbxNgayHTEnable = false
          }
          // this.requestFormInfo.checkboxSoCVEnable = true
          // dtpNgayCV.Value = tt_nd.ngay_cn;
          // dtpNgayHT.Value = tt_nd.ngay_cn;
          break
        case 50: // Dich vu CNTT
          this.Init_Properties(this.loaitb_id, "00000")
          if (rsKieuTB && rsKieuTB.length > 0) {
            if (rsKieuTB[0]['linhvuc_id']) { this.linhvuc_id = rsKieuTB[0]['linhvuc_id'] }
          } else { this.linhvuc_id = 0 }
          this.tocdo_id = 0
          break
      }
    },
    async hienThiGiaoDien (luongID) {
    // tsbtnVatTuMoi.Visible = false
      this.dtThaoTac = await api.lay_ds_thaotac_theo_luong_id_code(this.$auth.getPhanVungID(), luongID)
      if (this.dtThaoTac && this.dtThaoTac.length > 0) {
        for (let i = 0; i < this.dtThaoTac.length; i++) {
          const dtControl = await api.lay_ct_thaotac_control(this.dtThaoTac[i].thaotac_id)
          if (dtControl.length > 0) {
            for (let j = 0; j < dtControl.length; j++) {
              if (this.dtThaoTac[i].enable === '1') {
                this.$refs[dtControl[j].control_name].setAttribute('disabled', false)
                if (this.$refs[dtControl[j].control_name].classList.contains('disabled')) {
                  this.$refs[dtControl[j].control_name].classList.remove('disabled')
                }
              } else {
                this.$refs[dtControl[j].control_name].setAttribute('disabled', true)
                if (!this.$refs[dtControl[j].control_name].classList.contains('disabled')) {
                  this.$refs[dtControl[j].control_name].classList.add('disabled')
                }
              }
            }
          }
        }
      }
    },
    async hienThiTTHopDongKH (data) {
      this.requestFormInfo.cuocNong = ''
      this.thueBaoTonTai = false
      if (!data || data.length === 0) {
        this.$toast.error('Không tìm thấy dữ liệu với mã giao dịch này')
        // TODO
        this.dsThueBao = []
        this.setButton(0)
        return
      }
      this.thueBaoTonTai = true
      if (data[0].status === TRANGTHAI_DONGBO.DONGBO_CM || data[0].status === TRANGTHAI_DONGBO.DONGBO_SERVICE) {
        // this.$toast.error('Thuê bao đã được kích hoạt !')
        // this.resetForms()
        // return
      }
      this.hdkh_id = data[0].hdkh_id // 5310880 // REPLACE data[0].hdkh_id
      this.requestFormInfo.maGiaoDich = data[0].ma_gd
      this.requestFormInfo.maHDPL = data[0].ma_hd
      this.customerFormInfo.maKhachHang = data[0].ma_kh
      this.so_gt = data[0].so_gt
      this.so_dt = data[0].so_dt
      if (data[0].khachhang_id) {
        this.khachhang_id = data[0].khachhang_id
      }
      this.customerFormInfo.tenKhachHang = data[0].ten_kh
      this.customerFormInfo.diaChiKH = data[0].diachi_kh
      if (data[0].ngaylap_hd) {
        this.requestFormInfo.ngayLHD = formatDate(data[0].ngaylap_hd)
      }
      if (data[0].ngay_yc) {
        this.requestFormInfo.ngayYC = formatDate(data[0].ngay_yc)
      }
      this.setButton(3)
      const dsHD = await api.lay_ds_hdtb_theo_hdkh_tthd(this.hdkh_id, TRANGTHAI_HOPDONG.MOI) || []
      this.hienThiTTHopDongTB(dsHD)
      this.hienThiDanhSacHDTB(dsHD)
    // TODO
    // tsbnHoanThienDB.Enabled = false;
    },
    async hienThiTTHopDongTB (data) {
      this.requestFormInfo.cuocNong = ''
      if (!data || data.length === 0) {
        return
      }
      const dataHDTB = this.formatJson(data[0])
      this.hdtb_id = dataHDTB.hdtb_id
      if (dataHDTB.thuebao_id && dataHDTB.thuebao_id !== '') {
        this.thuebao_id = dataHDTB.thuebao_id
      }
      if (this.thuebao_id) {
        let dataTb = {
          'phanvung_id': this.$auth.getPhanVungID(),
          'thuebao_id': this.thuebao_id // 1521244
        }
        const dstb = await api.lay_ds_dbtb_theo_tbid(dataTb) || []
        let dataTt = {
          'phanvung_id': this.$auth.getPhanVungID(),
          'thanhtoan_id': dstb[0].thanhtoan_id // 275848
        }
        const dstt = await api.lay_ds_dbtt_theo_ttid(dataTt)
        if (dstt && dstt.length > 0) {
          this.customerFormInfo.diaChiTT = dstt[0].diachi_tt
        } else {
          this.customerFormInfo.diaChiTT = ''
        }
        this.subscriptionFormInfo.soMayAcc = dataHDTB.ma_tb
        this.dichvuvt_id = dataHDTB.dichvuvt_id
        this.quytrinh_id = dataHDTB.quytrinh_id
        if (dataHDTB.thuebao_cha_id) {
          this.h_thuebao_cha_id = dataHDTB.thuebao_cha_id
        } else {
          this.h_thuebao_cha_id = 0
        }
        this.luong_id = 0
        const dshg = await api.lay_huonggiao_theo_quytrinh(this.quytrinh_id, dataHDTB.tthd_id, 1)
        if (dshg && dshg.length > 0) {
          this.luong_id = dshg[0].luong_id
        }
        this.hienThiGiaoDien(this.luong_id)
        this.requestFormInfo.dichVu = dataHDTB.dichvuvt_id
        this.loaitb_id = parseInt(dataHDTB.loaitb_id)
        this.requestFormInfo.loaiHinh = dataHDTB.loaitb_id
        this.subscriptionFormInfo.tenThueBao = dataHDTB.ten_tb
        this.subscriptionFormInfo.diaChiTB = dataHDTB.diachi_tb
        this.subscriptionFormInfo.diaChiLD = dataHDTB.diachi_ld
        this.subscriptionFormInfo.ghiChu = dataHDTB.ghichu
        // this.layDsLyDoHuy(this.hdtb_id) // REPLACE: this.hdtb_id
        this.dsDKHUY = await api.lay_ds_dk_lydohuy(this.hdtb_id)
        this.requestFormInfo.kieuYeuCau = dataHDTB.kieuld_id
        this.doituong_id = dataHDTB.doituong_id
        this.donvi_id = dataHDTB.donvi_id
        if (parseInt(this.requestFormInfo.dichVu) === DICHVU_VIENTHONG.DI_DONG) {
          this.goicuoc_id = parseInt(dataHDTB['goicuoc_id'])
          if (!dataHDTB['doituong_id_neo']) { this.doituong_id_neo = 1 } else { this.doituong_id_neo = parseInt(dataHDTB['doituong_id_neo']) }
          this.simcard = dataHDTB['simcard']
        }
        this.hienThiTTMoRong(this.hdtb_id, this.dichvuvt_id)
        // VE TINH
        if (this.dichvuvt_id !== DICHVU_VIENTHONG.TSL && this.dichvuvt_id !== DICHVU_VIENTHONG.TSL_DOITAC) {
          this.startPointFormInfo.tramVT = null
          this.endPointFormInfo.tramVT = null
          const dataDVTC = {
            'phanvung_id': this.$auth.getPhanVungID(),
            'hdtb_id': this.hdtb_id, // 2115443
            'loaidv_id': 5,
            'kieudv_id': 2, // Mặc định
            'kieu': 2
          }
          const dstc = await api.lay_donvi_thicong(dataDVTC)
          if (dstc && dstc.length > 0) {
            this.subscriptionFormInfo.tramVT = +dstc[0].donvi_id
          }
        } else {
          this.subscriptionFormInfo.tramVT = null
          const dataDVTCDau = {
            'phanvung_id': this.$auth.getPhanVungID(),
            'hdtb_id': this.hdtb_id, // 2115443
            'loaidv_id': LOAI_DONVI.TRAM_VT,
            'kieudv_id': 1, // Mặc định
            'kieu': 2
          }
          const dstcDau = await api.lay_donvi_thicong(dataDVTCDau)
          if (dstcDau && dstcDau.length > 0) {
            this.startPointFormInfo.tramVT = dstcDau[0].donvi_id
          }
          const dataDVTCCuoi = {
            'phanvung_id': this.$auth.getPhanVungID(),
            'hdtb_id': this.hdtb_id, // 2115443
            'loaidv_id': 5,
            'kieudv_id': 2, // Mặc định
            'kieu': 2
          }
          const dstcCuoi = await api.lay_donvi_thicong(dataDVTCCuoi)
          if (dstcCuoi && dstcCuoi.length > 0) {
            this.endPointFormInfo.tramVT = dstcCuoi[0].donvi_id
          }
        }
        // THÔNG TIN PHIẾU TN
        const dataPhieuTN = {
          'phanvung_id': this.$auth.getPhanVungID(),
          'hdtb_id': this.hdtb_id // 2115443
        }
        const dsttphieu = await api.lay_thongtin_phieu_tn(dataPhieuTN)
        if (dsttphieu && dsttphieu.length > 0 && dsttphieu[0].kqxl_id) {
          if (dsttphieu[0].kqxl_id.toString === '3') {
            this.checkboxThuHoiThietBiGiaoDich = true
          } else {
            this.checkboxThuHoiThietBiGiaoDich = false
          }
          this.checkboxKhongCanThuHoiThietBi = false
          if (dsttphieu[0].kqxl_id.toString === '1') {
            this.checkboxKhongCanThuHoiThietBi = true
          }
        } else {
          this.checkboxThuHoiThietBiGiaoDich = false
        }
        if ((this.requestFormInfo.dichVu === DICHVU_VIENTHONG.ADSL || this.requestFormInfo.dichVu === DICHVU_VIENTHONG.DI_DONG) && await this.phaiLamKichHoat(this.quytrinh_id)) {
          // TODO
          // tsbtnKichHoat.Enabled = true;
        } else {
          // TODO
          // tsbtnKichHoat.Enabled = true;
        }
      }
    },
    async hienThiThongTinDanhBa (tbid) {
      let rs = []
      this.thueBaoTonTai = false
      try {
        rs = await api.lay_danhba_theo_matb({
          'in_ma_tb': tbid,
          'in_dichvuvt_id': this.requestFormInfo.dichVu,
          'in_donvi_dl_id': 0
        })
      } catch (ex) {
        console.error(ex)
        this.$toast.error('Có lỗi xảy ra ' + cmFunc.getErrorMessage(ex))
      }
      if (!rs || rs.length === 0) {
        const dv = this.listTelecomService.find(dv => dv.DICHVUVT_ID === +this.requestFormInfo.dichVu)
        const tenDv = dv ? dv.TEN_DVVT : this.requestFormInfo.dichVu
        this.$toast.warning(`Không tìm thấy thông tin về mã thuê bao ${this.subscriptionFormInfo.soMayAcc} đối với dịch vụ ${tenDv}`)
        return
      }
      // const rsLapHopDongKhac = await api.kiemtra_ma_tb_lap_hopdong_khac(this.subscriptionFormInfo.soMayAcc, 0, this.requestFormInfo.dichVu, LOAI_HOPDONG.CHAMDUT_SD)
      // if (rsLapHopDongKhac.length > 0) {
      //   this.$toast.warning(`Thuê bao này đã có lập HĐ khác`)
      //   return
      // }
      if (rs[0].trangthaitb_id === TRANGTHAI_THUEBAO.THANHLY || rs[0].trangthaitb_id === TRANGTHAI_THUEBAO.THANHLY_NO || rs[0].trangthaitb_id === TRANGTHAI_THUEBAO.DOISO) {
        this.$toast.warning(`Thuê bao có mã: ${rs[0].ma_tb} đang ở trạng thái: ${rs[0].trangthai_tb}. \nBạn không thể thực hiện thanh lý thuê bao này`)
        return
      }
      this.thueBaoTonTai = true
      this.thuebao_id = rs[0].thuebao_id
      this.requestFormInfo.loaiHinh = rs[0].loaitb_id
      const rsLapHDTrongThang = await api.kt_laphopdong_trongthang({
        'thuebao_id': this.thuebao_id,
        'loaihd_id': LOAI_HOPDONG.CHAMDUT_SD
      })
      // const rsLapHDTrongThang = []
      if (rsLapHDTrongThang && rsLapHDTrongThang.length > 0) {
        const rsSoLuongNV = await api.sl_nhanvien({
          'nhanvien_id': 0, // 0 = Lay tu token ?
          'kt_thanhly_trongthang': '897'
        })
        if (rsSoLuongNV && rsSoLuongNV.length > 0 && rsSoLuongNV[0].sl > 0) {
          const rsConfirm = confirm('Thuê bao có tháng Đặt mới khác tháng hiện tại. Bạn muốn tiếp tục thanh lý?')
          if (!rsConfirm) { return }
        } else {
        // TODO: map load hinh id => name
          this.$toast.warning(`Không được Thanh lý thuê bao ${this.requestFormInfo.loaiHinh} khác tháng đặt mới`)
          return
        }
      }
      const rsDsKM = await api.lay_ds_chitiet_khuyenmai(this.thuebao_id, 5)
      if (rsDsKM && rsDsKM.length > 0) {
        let tb = 'Thuê bao ' + this.thuebao_id
            tb += ' đang hưởng khuyến mại : \n ' + rsDsKM[0].ten_km
            tb += ' \n    Có chi tiết khuyến mại : '
            for (let i = 0; i < rsDsKM.length; i++) {
              tb += '\n     - ' + rsDsKM[i]['ten_ctkm']
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
                this.setButton(1)
                this.kiemtra = false
              })
        // this.$toast.warning(`Thuê bao ${this.thuebao_id} đang hưởng khuyến mại: \n${rsDsKM[0].ten_km} \n Có chi tiết khuyến mại: ${rsDsKM.map(r => `\n   -${r.ten_ctkm}`)} \nBạn không được thực hiện tiếp với thuê bao này!`)
        // return
      }
      this.h_thuebao_cha_id = rs[0].thuebao_cha_id ? rs[0].thuebao_cha_id : 0
      this.loaitb_id = rs[0].loaitb_id
      this.donvi_id = rs[0].donviql_id
      this.doituong_id = rs[0].doituong_id
      this.khachhang_id = parseInt(rs[0]['khachhang_id'])
      // TODO: LAY_DS_ISDN_THEO_THUEBAO_ID(thuebao_id);
      this.customerFormInfo.maKhachHang = rs[0].ma_kh
      this.customerFormInfo.tenKhachHang = rs[0].ten_kh
      this.customerFormInfo.diaChiKH = rs[0].diachi_kh
      this.customerFormInfo.diaChiTT = rs[0].diachi_tt
      this.requestFormInfo.maHDPL = rs[0].ma_hd
      this.requestFormInfo.ngayLHD = formatDate(rs[0].ngaylap_hd) || formatDate(new Date())
      this.subscriptionFormInfo.soMayAcc = rs[0].ma_tb
      this.subscriptionFormInfo.tenThueBao = rs[0].ten_tb
      this.subscriptionFormInfo.diaChiTB = rs[0].diachi_tb
      this.subscriptionFormInfo.diaChiLD = rs[0].diachi_ld
      this.subscriptionFormInfo.soMayAcc = rs[0].ma_tb
      if (this.requestFormInfo.loaiHinh === LOAIHIN_THUEBAO.HTVC_FIBERVNN_ANALOG) {
      } else {
        const rsNoTT = await api.tracuu_no_thanhtoan(rs[0].thanhtoan_id)
        if (rsNoTT > 0) {
          const rsNoTB = await api.tracuu_no_thuebao(this.thuebao_id)
          if (rsNoTB > 0) {
            this.$toast.warning(`Thuê bao ${this.thuebao_id} còn nợ: ${formatCurrency(rsNoTB, 0, 3, '.', '')}. \nPhải thanh toán trước khi thực hiện!`)
            return
          }
        }
      }
      if (parseInt(this.requestFormInfo.dichVu) === DICHVU_VIENTHONG.DI_DONG) {
        this.goicuoc_id = parseInt(rs[0]['goicuoc_id'])
        let tmp = ''
        tmp = rs[0]['doituong_id_neo']
        if (!tmp) { this.doituong_id_neo = 1 } else { this.doituong_id_neo = parseInt(rs[0]['doituong_id_neo']) }
        this.simcard = rs[0]['simcard']
      }
      this.hienThiTTDBMoRong()
      // if ((this.dichvuvt_id === DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id === DICHVU_VIENTHONG.TSL || this.dichvuvt_id === DICHVU_VIENTHONG.METRONET) && this.doitac_id != 0)
      // {
      //     //CheckDataFacade checkdata = new CheckDataFacade();
      //     string str_doitac = checkdata.MAP_ID("ten_dt", DatabaseConstants.DB2 + ".doitac", "where doitac_id = " + this.doitac_id);
      //     Message_Box.ShowTB("Thuê bao này có  Hợp đồng/PLHĐ với đối tác " + str_doitac);
      // }
      this.startPointFormInfo.tramVT = null
      this.endPointFormInfo.tramVT = null
      if (this.dichvuvt_id !== DICHVU_VIENTHONG.TSL && this.dichvuvt_id !== DICHVU_VIENTHONG.TSL_DOITAC) {
        this.startPointFormInfo.tramVT = null
        this.endPointFormInfo.tramVT = null
        const dvtc = await api.lay_donvi_thicong({
          'phanvung_id': this.$auth.getPhanVungID(),
          'hdtb_id': this.hdtb_id,
          'loaidv_id': LOAI_DONVI.TRAM_VT,
          'kieudv_id': 2,
          'kieu': 1
        })
        if (dvtc && dvtc.length > 0) {
          this.subscriptionFormInfo.tramVT = parseInt(dvtc[0]['donvi_id'])
        }
      } else {
        this.subscriptionFormInfo.tramVT = null
        let dvtc = await api.lay_donvi_thicong({
          'phanvung_id': this.$auth.getPhanVungID(),
          'hdtb_id': this.thuebao_id,
          'loaidv_id': LOAI_DONVI.TRAM_VT,
          'kieudv_id': 1,
          'kieu': 1
        })
        if (dvtc && dvtc.length > 0) {
          this.startPointFormInfo.tramVT = parseInt(dvtc[0]['donvi_id'])
        }
        dvtc = await api.lay_donvi_thicong({
          'phanvung_id': this.$auth.getPhanVungID(),
          'hdtb_id': this.thuebao_id,
          'loaidv_id': LOAI_DONVI.TRAM_VT,
          'kieudv_id': 2,
          'kieu': 1
        })
        if (dvtc && dvtc.length > 0) {
          this.endPointFormInfo.tramVT = parseInt(dvtc[0]['donvi_id'])
        }
      }
      // Lay danh sach thue bao cung ma doi cap de hien thi ghi chu
      let ghichu = ''
      if (parseInt(this.requestFormInfo.dichVu) !== DICHVU_VIENTHONG.DI_DONG && parseInt(this.requestFormInfo.dichVu) !== DICHVU_VIENTHONG.GPHONE &&
                        parseInt(this.requestFormInfo.dichVu) !== DICHVU_VIENTHONG.DICHVU_CNTT) {
        const dstb = await api.lay_ds_tb_cung_madoicap(this.thuebao_id, 1)
        if (dstb && dstb.length > 1) {
          ghichu += 'Thuê bao ' + this.subscriptionFormInfo.soMayAcc + ' đang nằm trên cùng đôi cáp với các thuê bao: '
          for (let i = 0; i < dstb.length; i++) {
            if (dstb[i]['ma_tb'] === this.subscriptionFormInfo.soMayAcc) { continue }
            ghichu += dstb[i]['ma_tb'] + ','
          }
          ghichu = ghichu.substr(0, ghichu.length - 1) + '.\n\n'
        }
      }
      this.subscriptionFormInfo.ghiChu = ghichu
      this.hdtb_id = 0
    },
    async hienThiDanhSacHDTB (data) {
      this.dsThueBao = []
      this.dsThueBao = data.filter(r => (r['tthd_ID'] && r['tthd_ID'] === TRANGTHAI_HOPDONG.MOI)
      // && (r['status'] && r['status'] !== TRANGTHAI_DONGBO.DONGBO_CM)
      )
        .map((r, index) => ({stt: index + 1, ...r}))
      if (this.dsThueBao.length <= 1) {
        if (this.dsThueBao.length === 0) {
          this.toolbarButtons.themTB = false
          const rsSlHD = await api.lay_sl_hd(this.hdkh_id) // 5548465
          if (rsSlHD && rsSlHD.length > 0) {
            if (rsSlHD[0]['sl'] > 1) {
              this.toolbarButtons.xoaTB = true
            } else {
              this.toolbarButtons.xoaTB = false
            }
          } else {
            this.toolbarButtons.xoaTB = false
          }
        }
      } else {
        this.toolbarButtons.themTB = true
        this.toolbarButtons.xoaTB = true
      }
    },
    async lvwThueBaoSelectedIndexChanged (data) {
      let selectedData = data.data
      if (selectedData) {
        this.hdtb_id = +selectedData.hdtb_ID
        this.dichvuvt_id = +selectedData.dichvuvt_ID
        // TODO
        // tsbnGuiCatMo.Enabled = false;
        const rs = await api.lay_ds_hopdong_tb_theo_hdtb_id(this.hdtb_id)
        if (rs && rs.length > 0) {
          rs[0] = this.formatJson(rs[0])
        }
        this.toolbarButtons.xoaTB = true
        await this.hienThiTTHopDongTB(rs)
        if (await this.gdvHoanThien(this.quytrinh_id)) {
        // TODO
        // tsbtnCapNhatDB.Visible = true;
        } else {
        // TODO
        // tsbtnCapNhatDB.Visible = false;
        }
        if (this.dichvuvt_id === DICHVU_VIENTHONG.GPHONE) {
        // TODO
        // tsbtnCapNhatDB.Visible = true;
        } else {
        // TODO
        // tsbtnCapNhatDB.Visible = false;
        }
        if ((+this.requestFormInfo.dichVu === DICHVU_VIENTHONG.ADSL || +this.requestFormInfo.dichVu === DICHVU_VIENTHONG.DI_DONG) && this.phaiLamKichHoat(this.quytrinh_id)) {
        // TODO
        // tsbtnKichHoat.Enabled = true;
        } else {
        // TODO
        // tsbtnKichHoat.Enabled = false;
        }
      }
    },
    formatJson (data) {
      let output = {}
      Object.keys(data).forEach(el => {
        if (el.indexOf('_')) {
          output[el.replace(el.substring(el.indexOf('_'), el.length), el.substring(el.indexOf('_'), el.length).toLowerCase())] = data[el]
        } else {
          output[el] = data[el]
        }
      })
      return output
    },
    async layDanhSachISDNTheoThueBaoID (tbid) {
      try {
        this.isdn_sochinh = false
        this.matb_isdn_chinh = ''
        this.ds_isdn = await api.lay_ds_isdn_theo_thuebao_id(tbid) // 1478219
        for (let i = 0; i < this.ds_isdn[0].length; i++) {
          if (parseInt(this.ds_isdn[i]['thuebao_id']) === tbid) {
            if (!this.ds_isdn[i]['thuebao_cha_id']) {
            // lblTB_ISDN.Text = "Số PABX chính";
              this.isdn_sochinh = true
            } else {
            // lblTB_ISDN.Text = "Số PABX phụ";
            }
          }
          if (!this.ds_isdn[i]['thuebao_cha_id']) {
            this.matb_isdn_chinh = this.ds_isdn[i]['ma_tb']
          }
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error('Có lỗi xảy ra ' + cmFunc.getErrorMessage(ex))
      }
    },
    onDialogLyDoHuyAccepted (data) {
      let lsTmp = []
      for (const item of data) {
        if (!this.dsDKHUY.find(i => i.lydohuy_id === item.lydohuy_id)) {
          lsTmp.push(item)
        }
      }
      this.dsDKHUY = [...this.dsDKHUY, ...lsTmp]
    },
    btnSearchAccountClick () {
      this.$refs.dlgSearchAccount.show(true)
    },
    onSelectCustomer (e) {
      this.subscriptionFormInfo.soMayAcc = e.ma_tb
      this.onMaTBKeyPress({charCode: 13})
    },
    async onGiaoPhieuClose () {
      if (this.requestFormInfo.maGiaoDich) {
        this.dsThueBao = []
        const dsHD = await api.lay_ds_hopdong_theo_ma_gd(this.requestFormInfo.maGiaoDich)
        this.hienThiTTHopDongKH(dsHD)
      }
    },
    onUpHoSoClose () {
    },
    btnTraCuuHopDongClick () {
      this.$refs['dlgSearchContract'].showModal()
    },
    async btnLayDSTBClick () {
      if (!this.subscriptionFormInfo.soMayAcc || this.subscriptionFormInfo.soMayAcc.trim() == "") {
        this.$toast.error("Bạn chưa nhập mã thuê bao!")
        return
      }
      if (!this.thueBaoTonTai) {
        this.$toast.error("Thuê bao không tồn tại!")
        return
      }
      this.$refs.dlgTraCuuThueBao.showModal(this.khachhang_id, this.requestFormInfo.dichVu, this.requestFormInfo.maGiaoDich, this.showErrorDSTBC)
    },
    onTraCuuAccept (item) {
      this.subscriptionFormInfo.soMayAcc = item['ma_tb']
      this.onMaTBKeyPress({charCode: 13})
    },
    async onAcceptSearchContract (e) {
      this.requestFormInfo.maGiaoDich = e.ma_gd
      if (this.requestFormInfo.maGiaoDich && this.requestFormInfo.maGiaoDich.length > 0) {
        this.dsThueBao = []
        const rs = await api.lay_ds_hopdong_theo_ma_gd(this.requestFormInfo.maGiaoDich)
        this.hienThiTTHopDongKH(rs)
      }
    },
    ckbThuHoiChange () {
      if (this.checkboxThuHoiThietBiGiaoDich) {
        this.checkboxKhongCanThuHoiThietBi = false
      }
    },
    ckbKhongThuHoiChange () {
      if (this.checkboxKhongCanThuHoiThietBi) {
        this.checkboxThuHoiThietBiGiaoDich = false
      }
    },
    async loadTuHopDongTuVan (hdkh_cha_id) {
      this.hdkh_cha_id = hdkh_cha_id
      // const dt = await api.lay_dulieu_theo_dieukien('hd_thuebao', 'ma_tb', 'dichvuvt_id', 'hdkh_id = ' + hdkh_cha_id, '')
      // fix att
      const dt = await api.lay_dulieu_theo_dieukien_hd_thuebao(hdkh_cha_id);
      if (dt && dt.length > 0) {
        this.requestFormInfo.dichVu = dt[0][1]
        this.subscriptionFormInfo.soMayAcc = dt[0][0]
        this.onMaTBKeyPress({charCode: 13})
      } else {
        this.$toast.error("Khách hàng không có thuê bao yêu cầu thanh lý")
      }
    },
    async Init_Properties (vloaitb_id, opt_list) {
      const ds = await api.lay_danhsach_thuoctinh_v2(vloaitb_id, opt_list) || []
      this.ItemList = []
      for (let i = 0; i < ds.length; i++) {
        const row = ds[i]
        const obj = {
          sFieldName: row["field_name"],
          sCaption: row["ten_hienthi"],
          iAtt: row["att"],
          iDataType: row["data_type"],
          sMaxValue: row["max_value"],
          sMinvalue: row["min_value"],
          sValue: row["field_value"],
          nFieldLength: (row["field_length"] == null || row["field_length"] == undefined) ? 0 : row["field_length"],
          bIsNotNull: row["field_nullable"] == 0,
          bIsUnique: row["field_unique"] != null && row["field_unique"] != undefined && row["field_unique"] != 0,
          sLookUpTableName: row["parent_table"],
          sLookUpKeyField: row["parent_keyfield"],
          sLookUpValueField: row["parent_descfield"],
          strDepandField: row["depend_field"],
          StrSql: row["lookup_sql"]
        }
        this.ItemList.push(obj)
      }
    },
    showErrorDSTBC () {
      this.$toast.error("Chưa có thông tin về khách hàng thanh lý có thuê bao: " + this.subscriptionFormInfo.soMayAcc + ". Kiểm tra lại !")
    }
  },
  provide: {
    grid: [Page, Filter]
  }
}
</script>
<style>
  .row-container {
    display: flex;
  }
  .row-text {
    flex-grow: 1;
    display: flex;
    align-items: center;
  }
  .row-remove-icon {
    width: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: large;
    font-weight: bold;
    color: #898989 !important;
  }
  .list-lydohuy{
    height: 140px;
  }
  .list-lydohuy .e-list-parent .e-list-item.e-selected{
    background-color: #bae7ff;
  }
  #thanhLy .gutter.gutter-horizontal{
    height: auto !important;
    margin: 0px 10px;
  }
</style>
<style scoped>
.dropdown-menu a {
  cursor: pointer;
}
</style>