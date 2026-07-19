<template src="./index.template.html"></template>

<script>
import Vue from "vue";
import { Group, Page, Sort, Filter, Aggregate, Resize} from "@syncfusion/ej2-vue-grids";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
import CongTacVien from '@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien'
import NguoiGioiThieu from '@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu'
import ChonKhuyenMai from '@/modules/contract/setup/InstallNewSubs/Popups/popup-ChonKhuyenMai.vue'
import ThongTinEmail from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue'
import ThongTinLienHe from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import DateTimeLib from '@/utils/DateTimeLib'
import moment from 'moment'
import { currency } from '@/filters/currency'
const thanhToan = () => import('@/modules/contract/setup/Payment/Payment.vue')

export default {
  components: {
    BssErrorMarker,
    BssRequiredMarker,
    SearchContractModal,
    SearchAccount,
    CongTacVien,
    NguoiGioiThieu,
    ChonKhuyenMai,
    ThongTinEmail,
    ThongTinLienHe,
    ChonThueBao : () => import('../CapNhatThueBaoDatCoc2/Popups/ChonThueBao')
  },
  data () {
    return {
      strict: true,
      selectionSettings: {checkboxOnly: true, type: 'Multiple'},
      isShowPhiTTN: false,
      chkPhiTTN: false,
      khoanmucPhiTTN : {},
      phiTTN: 0,
      tienHD: 0,
      tienVATHD: 0,
      tongTienHD: 0,
      ADSL: 4,
      userComponentModule: null,
      txtMaGDTT: '',
      popupComponentEvts: {
        paymentcreated: this.paymentcreated
      },
      popupChonKhuyenMaiData: {},
      cfg_ngay_cn: null,
      dsKieuLd: [],
      kieuLdId: null,
      ngayYeuCau: new Date(),
      dsDichVu: [],
      ds_tb: [],
      dichVuId: null,
      dsLoaiHinh: [],
      thangThoaiTra: new Date(),
      dsNhomDatCoc: [],
      nhomDatCocId: null,
      dsTraTruoc: [],
      traTruocId: null,
      dsChiTietKhuyenMai: [],
      chiTietKhuyenMaiId: null,
      dsTocDo: [],
      tocDoId: null,
      mucCuocId: null,
      dsThamSoMacDinh: [],
      maGiaoDich: null,
      maKhachHang: null,
      ghiChu: null,
      chkCtv: false,
      ctvId: null,
      tenCtv: null,
      chkNguoiGt: false,
      nguoiGtId: null,
      tenNguoiGt: null,
      dsHdtb: [],
      maThueBao: null,
      ma_tb: null,
      ob_id: null,
      user_ipcc: null,
      tienThoaiTra: 0,
      thongTinDanhBa: null,
      dsDatCoc: [],
      dsDatCoc_bk: [],
      loaiTbId: null,
      doiTuongId: null,
      thueBaoId: null,
      tthdId: null,
      thueBaoThoaiTraId: null,
      rkmId: null,
      ctTienHdId: null,
      thangBD: new Date(),
      thangBdEnabled: true,
      soThang: null,
      soThangEnabled: true,
      dsSoThang: [],
      dsCTKM: [],
      f_capnhat_from_grid: false,
      dtpThangBDMG : {
        value: new Date(),
        enabled : true
      },
      THUEBAO: {},
      // thangMgEnabled: true,
      soThangMg: null,
      soThangMgEnabled: true,
      tyLeTb: 0,
      tyLeTbEnabled: true,
      tyLeSd: 0,
      tyLeSdEnabled: true,
      tienTb: 0,
      tienTbEnabled: true,
      tienSd: 0,
      tienSdEnabled: true,
      cuocDC: null,
      cuocDcEnabled: true,
      tienTd: null,
      tienTdEnabled: true,
      rkmDcId: null,
      ctTienHdDcMoiId: null,
      tenTb: null,
      diaChiLd: null,
      thangBdCu: null,
      thangKtCu: null,
      ngayBdCu: '',
      ngayKtCu: '',
      nhomDatCocIdCu: null,
      ngayYcCu: null,
      ttdcId: null,
      cuocDcCu: null,
      tienTdCu: null,
      allowThoaiTra: 1,
      hdkhId: null,
      khachHangId: null,
      khLonId: null,
      tenKhachHang: null,
      tenKhachHangHienThi: null,
      diaChiKh: null,
      tenGoiDDV: null,
      ngayLhd: null,
      donViId: null,
      datCocDuocChon: null,
      tienThoai: 0,
      thuongHieu: null,
      activeEventThangThoaiTra: true,
      hdtbDuocChon: null,
      hideTable: true,
      ct_khuyenmai: {
        id: '',
        text: '',
        so_thang: ''
      },
      btnNhapMoi: {
        enabled: false
      },
      btnGhiLai: {
        enabled: false
      },
      btnXoa: {
        enabled: false
      },
      btnHuyBo: {
        enabled: false
      },
      btnThanhToan: {
        enabled: false
      },
      btnCapNhatDB: {
        enabled: false
      },
      error: {
        ctvId: false,
        nguoiGtId: false,
        tienThoaiTra: false,
        maThueBao: false,
        thangThoaiTra: false,
        traTruocId: false,
        chiTietKhuyenMaiId: false
      },
      dtpThangKTMG: {
        value: new Date(),
        dateFormat: 'MM/yyyy',
        enabled: false
      },
      dtpNgayBD: {
        value: new Date(),
        dateFormat: 'dd/MM/yyyy',
        enabled: true
      },
      dtpNgayKT: {
        value: new Date(),
        dateFormat: 'dd/MM/yyyy',
        enabled: false
      },
      dtpThangBD: {
        value: new Date(),
        dateFormat: 'MM/yyyy',
        enabled: false
      },
      dtpThangKT: {
        value: new Date(),
        dateFormat: 'MM/yyyy',
        enabled: false
      },
      dtpNgayBDDC: {
        value: new Date(),
        dateFormat: 'dd/MM/yyyy',
        enabled: true
      },
      dtpNgayKTDC: {
        value: new Date(),
        dateFormat: 'dd/MM/yyyy',
        enabled: false
      },
      dtpNgayThoai: {
        value: new Date(),
        dateFormat: 'dd/MM/yyyy',
        enabled: true
      },
      dataKhuyenMaiSelect: null,
      checkMaxNgayBDDC: false,
      groupSettings: {
          showDropArea: false,
          showToggleButton: false,
          showUngroupButton: false,
          columns: ['TEN_GOI_DDV']
      },
      sumTemplate: function() {
          return {
              template: Vue.component('sumTemplate', {
              template: `<span style="color:red">Tổng: {{this.$options.filters.currency(data.Sum)}}</span>`,
              data () {
                  return { data: { data: {} } }
              }
              })
          }
      },
      customTemplate: function () {
        return { template: Vue.component('footerTemplate', {
          template: `<span style="color:red">Tổng: {{this.$options.filters.currency(data.Custom)}}</span>`,
          data () { return { data: { data: {} } } }
        })
        }
      },
    }
  },
  computed: {
    tsTinhTienThoaiMoi () {
      return this.dsThamSoMacDinh.some(thamSo => {
        return thamSo.MA_TS == 'TINHTIEN_THOAI_MOI'
      })
    },
    tsKhongVuotQuaTienThoai () {
      return this.dsThamSoMacDinh.some(thamSo => {
        return thamSo.MA_TS == 'KHONG_VUOTQUA_TIENTHOAI' && thamSo.TEN_TS == '1'
      })
    },
    tsKhongSuaTienThoai () {
      return this.dsThamSoMacDinh.some(thamSo => {
        return thamSo.MA_TS == '_KHONG_SUA_TIENTHOAI' && thamSo.TEN_TS == '1'
      })
    },
    tsMacDinhThoaiDcLuiThang () {
      return this.dsThamSoMacDinh.some(thamSo => {
        return thamSo.MA_TS == 'MACDINH_THOAIDC_LUITHANG' && thamSo.TEN_TS == '1'
      })
    },
    tsSinhMaGdTheoDv () {
      return this.dsThamSoMacDinh.some(thamSo => {
        return thamSo.MA_TS == 'SINH_MA_GD_THEO_DV' && thamSo.TEN_TS == '1'
      })
    },
    tsHuyDatCocTheoThang () {
      return this.dsThamSoMacDinh.some(thamSo => {
        return thamSo.MA_TS == 'HUY_DATCOC_THEOTHANG' && thamSo.TEN_TS == '1'
      })
    },
    tsTinhCuocNgay () {
      return true
      // return this.dsThamSoMacDinh.some(thamSo => {
      //   return thamSo.MA_TS == 'TINHCUOC_NGAY' && thamSo.TEN_TS == '1'
      // })
    },
    minValNgayBDDC () {
      // let ss = moment(this.thangBD)
      // let select = ss.format('M')
      // let dnow = moment().format('M')
      // if(Number(select) > Number(dnow)){
      //   return DateTimeLib.toDateObjectWithFormat(ss.startOf('month'),'dd/MM/yyyy')
      // }
      // return DateTimeLib.toDateObjectWithFormat(ss.set('date',moment().format('D')))
      return this.dtpThangBD.value
    },
    maxValNgayBDDC () {
      if (this.checkMaxNgayBDDC) {
        return DateTimeLib.toDateObjectWithFormat(moment(this.dtpThangBD.value).endOf('month'), 'dd/MM/yyyy')
      }
      return new Date().getFullYear() + 10
    },
    // minValNgayBDMG () {
    //   return DateTimeLib.toDateObjectWithFormat(moment(this.thangMG).startOf('month'), 'dd/MM/yyyy')
    // },
    // maxValNgayBDMG () {
    //   return DateTimeLib.toDateObjectWithFormat(moment(this.thangMG).endOf('month'), 'dd/MM/yyyy')
    // },
    minValNgayThoai () {
      return DateTimeLib.toDateObjectWithFormat(moment(this.thangThoaiTra).startOf('month'), 'dd/MM/yyyy')
    },
    maxValNgayThoai () {
      return DateTimeLib.toDateObjectWithFormat(moment(this.thangThoaiTra).endOf('month'), 'dd/MM/yyyy')
    },
    statusThangThoaiTra(){
      if ([15, 7, 8, 9].includes(parseInt(this.dichVuId))) {
          return true;
        } else {
          return false;
      }
    }
  },
  watch: {
    chkPhiTTN (val) {
      if (!val) {
        this.tienTTN = 0
      } 
      this.tinhTongTienHD()
    },
    phiTTN (val) {
      this.tinhTongTienHD()
    },
    'dtpNgayBDDC.value' (val) {
      if (val === null) {
        val = new Date()
        this.dtpNgayBDDC.value = val
      }
      if (!this.chiTietKhuyenMaiId) return
      // if (!this.dataKhuyenMaiSelect || this.dataKhuyenMaiSelect.CHITIETKM_ID === null) return
      try {
        let item = this.dsChiTietKhuyenMai.find(x => x.CHITIETKM_ID === this.chiTietKhuyenMaiId)
        let ngay = moment(val).add(parseInt(item.HUONG_DC), 'M')
        let ngayktdc = DateTimeLib.toDateObject(ngay.subtract(1, 'd'))
        this.dtpNgayKTDC.value = ngayktdc
        this.dtpThangKT.value = this.dtpNgayKTDC.value

        this.dtpNgayBD.value = val
        this.dtpNgayKT.value = this.dtpNgayKTDC.value
        /** */
        this.thangThoaiTra = moment(val).startOf('month').toDate()

      } catch (error) {
        console.log('error:', error)
      }
    },
    'dtpNgayBD.value' (val) {
      if (!this.dataKhuyenMaiSelect || this.dataKhuyenMaiSelect.CHITIETKM_ID === null) return
      try {
        var item = this.dataKhuyenMaiSelect
        let ngay = moment(val).add(parseInt(item.HUONG_KM), 'M')
        let ngayktdc = DateTimeLib.toDateObject(ngay.subtract(1, 'd'))
        this.dtpNgayKT.value = ngayktdc
        this.dtpThangKTMG.value = this.dtpNgayKT.value
      } catch (error) {
        console.log('error:', error)
      }
    },
    'dtpThangBDMG.value' (val) {
      if (val === null) {
        val = new Date()
        this.dtpThangBDMG.value = val
      }
      this.dtpNgayBD.value = this.dtpThangBDMG.value

      if (!this.dataKhuyenMaiSelect || this.dataKhuyenMaiSelect.CHITIETKM_ID === null) return
      let item = this.dataKhuyenMaiSelect
      if (item) {
        if (item.LOAI_BDKM.toString() === '2') {
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(val).add(Number(item.HUONG_KM) - 1, 'M'))
        } else if (item.LOAI_BDKM.toString() === '4') {
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(val).add(Number(item.HUONG_KM) - 1, 'M'))
        } else if (item.LOAI_BDKM.toString() === '1') {
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(val).add(Number(item.HUONG_KM) - 1, 'M'))
        } else {
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(val).add(Number(item.HUONG_KM) - 1, 'M'))
        }
      }
    },
    async 'dtpNgayThoai.value' (val) {
      // console.log('🚀 ~ file: index.vue:316 ~ this.thueBaoThoaiTraId:', this.thueBaoThoaiTraId)
      // if (this.thueBaoThoaiTraId === null) return
      console.log('🚀 ~ file: index.vue:266 ~ val:', val)
      let tienThoaiDuocTinh = await this.layTienThoaiDatCoc_v4({
        rkm_id: this.THUEBAO.RKM_ID,
        thuebao_dc_id: this.THUEBAO.THUEBAO_DC_ID,
        thang_thoai: this.yyyymm(val),
        ngay_thoai: this.yyyymmdd(this.dtpNgayThoai.value)
      })
      console.log('🚀 ~ file: index.vue:274 ~ tienThoaiDuocTinh:', tienThoaiDuocTinh)
      this.tienThoai = tienThoaiDuocTinh
      this.tienThoaiTra = this.tienThoai
    },
    maThueBao (val) {
      if (val) {
        this.error.maThueBao = false
      }
    },
    tienThoaiTra (val) {
      if (val) {
        this.error.tienThoaiTra = false
      }
    },
    async dichVuId (val) {
      if (!val) {
        this.loaiTbId = null
        this.dsLoaiHinh = []
        this.dsTocDo = []
        this.tocDoId = null
        return
      }

      if (val != 4/* băng rộng cố định ADSL */) {
        this.thuongHieu = null
      }

      await this.layDsLoaiHinh(val)

      if (!!this.dsLoaiHinh && this.dsLoaiHinh.length > 0) {
        let hasValue = false

        if (this.loaiTbId) {
          hasValue = this.dsLoaiHinh.some(item => item.LOAITB_ID == this.loaiTbId)
        }

        if (!hasValue) {
          this.loaiTbId = this.dsLoaiHinh[0].LOAITB_ID
        }
      }

      if ([15, 7, 8, 9].includes(parseInt(this.dichVuId))) {
        this.dtpNgayThoai.enabled = false
        } else {
        this.dtpNgayThoai.enabled = true
      }
    },
    loaiTbId () {
      this.loadTraTruoc()
    },
    traTruocId (val) {
      if (val) {
        this.error.traTruocId = false
      }
      this.loadTraTruoc()
    },
    async nhomDatCocId (val) {
      if (val) {
        try {
          if (!this.THUEBAO.THUEBAO_ID) return
          this.loading(true)
          await this.HT_DS_KhuyenMai_Combobox(this.THUEBAO.KHUYENMAI_ID)
        } finally {
          this.loading(false)
        }
      }

      // await this.loadTraTruoc()
    },
    async thangThoaiTra (val) {
      // if (!this.activeEventThangThoaiTra) {
      //   return
      // }
      // if (this.thueBaoThoaiTraId === null) return
      // this.dtpNgayThoai.value = val
      this.dtpNgayThoai.value = moment(this.dtpNgayBDDC.value).subtract(1, "days").toDate()

      if (this.tsTinhTienThoaiMoi) {
        let tienThoaiDuocTinh = await this.layTienThoaiDatCoc_v4({
          rkm_id: this.rkmId,
          thuebao_dc_id: this.thueBaoThoaiTraId,
          thang_thoai: this.yyyymm(val),
          ngay_thoai: this.yyyymmdd(this.dtpNgayThoai.value)
        })
        console.log('🚀 ~ file: index.vue:358 ~ thangThoaiTra ~ tienThoaiDuocTinh:', tienThoaiDuocTinh)
        this.tienThoai = tienThoaiDuocTinh
        this.tienThoaiTra = this.tienThoai
      } else {
        this.tinhTienThoai()
      }
    },
    dsChiTietKhuyenMai (value) {
      if (!value || value.length == 0) {
        this.chiTietKhuyenMaiId = null
        this.ct_khuyenmai.id = null
        this.ct_khuyenmai.text = null
      }
    },
    chiTietKhuyenMaiId (val) {
      if (!this.THUEBAO.THUEBAO_ID) return
      if (val) {
        this.error.chiTietKhuyenMaiId = false
      }

      if (!val) {
        this.dtpThangBD.enabled = true
        this.dtpThangBD.value = new Date()
        this.soThangEnabled = true
        this.soThang = null
        this.dtpThangBDMG.enabled = true
        this.dtpThangBDMG.value = new Date()
        this.soThangMgEnabled = true
        this.soThangMg = null
        this.tienTbEnabled = true
        this.tienTb = 0
        this.tienSdEnabled = true
        this.tienSd = 0
        this.tyLeSdEnabled = true
        this.tyLeSd = 0
        this.tyLeTbEnabled = true
        this.tyLeTb = 0
        this.cuocDcEnabled = true
        this.cuocDC = null
        this.tienTdEnabled = true
        this.tienTd = null
        return
      }

      this.dtpThangBD.enabled = false
      this.soThangEnabled = false
      this.cuocDcEnabled = false
      this.tienTdEnabled = false

      this.dtpThangBDMG.enabled = false
      this.soThangMgEnabled = false
      this.tyLeTbEnabled = false
      this.tyLeSdEnabled = false
      this.tienTbEnabled = false
      this.tienSdEnabled = false

      let chiTietKm = this.dsChiTietKhuyenMai.find(chiTietKm => chiTietKm.CHITIETKM_ID == val)

      console.log('chiTietKm', chiTietKm)

      if (!chiTietKm) {
        return
      }
      this.dataKhuyenMaiSelect = chiTietKm

      this.soThang = chiTietKm.HUONG_DC
      this.cuocDC = chiTietKm.DATCOC_CSD
      this.tienTd = chiTietKm.TIEN_TD

      this.soThangMg = chiTietKm.HUONG_KM
      this.tienTb = chiTietKm.TIEN_TB
      this.tienSd = chiTietKm.TIEN_SD
      this.tyLeSd = chiTietKm.TYLE_SD
      this.tyLeTb = chiTietKm.TYLE_TB

      let month = new Date()
      month.setDate(1)

      // Tính cước ngày LOAI_BDDC = 1, 3 khi load dữ liệu
      switch (chiTietKm.LOAI_BDDC) {
        case 1:
          if (this.f_capnhat_from_grid && this.THUEBAO.THANG_BD !== null) {
            this.dtpThangBD.value = moment(this.THUEBAO.THANG_BD, 'YYYYMM').toDate()
          } else {
            let m = DateTimeLib.toDateObject(moment(month).add(Number(chiTietKm.THANG_BDDC), 'M'))
            this.dtpThangBD.value = m 
          }
          // month.setMonth(month.getMonth() + chiTietKm.THANG_BDDC)
          // this.dtpThangBD.value = month

          this.dtpThangKT.value = DateTimeLib.toDateObject(moment(this.dtpThangBD.value).add(Number(chiTietKm.HUONG_DC), 'M').subtract(1, 'M'))

          this.dtpThangBD.enabled = false
          this.checkMaxNgayBDDC = true
          break
        case 3:
          if (this.f_capnhat_from_grid && this.THUEBAO.THANG_BD !== null) {
            this.dtpThangBD.value = moment(this.THUEBAO.THANG_BD, 'YYYYMM').toDate()
          } else {
            this.dtpThangBD.value = DateTimeLib.toDateObject(month)
          }   
          // month.setMonth(month.getMonth() + chiTietKm.THANG_BDDC)
          // this.dtpThangBD.value = month

          this.dtpThangKT.value = DateTimeLib.toDateObject(moment(this.dtpThangBD.value).add(Number(chiTietKm.HUONG_DC), 'M').subtract(1, 'M'))

          this.dtpThangBD.enabled = true
          this.checkMaxNgayBDDC = false
          break
        default:
          this.dtpThangBD.enabled = true
          this.checkMaxNgayBDDC = false
          break
      }

      month = new Date()
      month.setDate(1)

      let thangBdTmp = new Date(this.dtpThangBD.value)

      switch (chiTietKm.LOAI_BDKM) {
        case 1:
          month.setMonth(month.getMonth() + chiTietKm.THANG_BDKM)
          this.dtpThangBDMG.value = month
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.dtpThangBDMG.value).add(Number(chiTietKm.HUONG_MG), 'M').subtract(1, 'M'))
          break
        case 2:
          thangBdTmp.setMonth(thangBdTmp.getMonth() + chiTietKm.THANG_BDKM + chiTietKm.HUONG_DC - 1)
          this.dtpThangBDMG.value = thangBdTmp
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.dtpThangBDMG.value).add(Number(chiTietKm.HUONG_MG), 'M').subtract(1, 'M'))
          break
        case 4:
          thangBdTmp.setMonth(thangBdTmp.getMonth() + chiTietKm.THANG_BDKM)
          this.dtpThangBDMG.value = thangBdTmp
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.dtpThangBDMG.value).add(Number(chiTietKm.HUONG_KM) - 1, 'M'))
          break
        default:
          this.dtpThangBDMG.enabled = true
          this.dtpThangBD.value = this.dtpThangBD.value
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.dtpThangBDMG.value).add(Number(chiTietKm.HUONG_KM) - 1, 'M'))
          break
      }

      this.cuocDcEnabled = chiTietKm.KIEU_TRATRUOC == 1
    },
    'dtpThangBD.value' (val) {
      if (val === null) {
        val = new Date()
        this.dtpThangBD.value = val
      }
      this.dtpNgayBDDC.value = this.dtpThangBD.value 
      if (moment(this.dtpNgayBDDC.value).format('YYYYMMDD') < moment().format('YYYYMMDD')) {
        this.dtpNgayBDDC.value = new Date()
      }
      if (!this.chiTietKhuyenMaiId) {
        return
      }

      let chiTietKm = this.dsChiTietKhuyenMai.find(chiTietKm => chiTietKm.CHITIETKM_ID == this.chiTietKhuyenMaiId)

      if (!chiTietKm) {
        return
      }

      let thangBdTmp = new Date(val)
      this.dtpThangKT.value = DateTimeLib.toDateObject(moment(val).add(Number(chiTietKm.HUONG_DC), 'M').subtract(1, 'M'))
      switch (chiTietKm.LOAI_BDKM) {
        case 2:
          thangBdTmp.setMonth(thangBdTmp.getMonth() + chiTietKm.THANG_BDKM + chiTietKm.HUONG_DC - 1)
          this.dtpThangBDMG.value = thangBdTmp
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.dtpThangBDMG.value).add(Number(chiTietKm.HUONG_KM) - 1, 'M'))
          break
        case 4:
          thangBdTmp.setMonth(thangBdTmp.getMonth() + chiTietKm.THANG_BDKM)
          this.dtpThangBDMG.value = thangBdTmp
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.dtpThangBDMG.value).add(Number(chiTietKm.HUONG_KM) - 1, 'M'))
          break
        case 1:
          this.dtpThangBDMG.value = val
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.dtpThangBDMG.value).add(Number(chiTietKm.HUONG_KM) - 1, 'M'))
          break
        default:
          this.dtpThangBDMG.enabled = true
          this.thangMG = val
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.dtpThangBDMG.value).add(Number(chiTietKm.HUONG_KM) - 1, 'M'))
          break
      }
    },
    // 'thangBD' (val) {
    //   if (this.dataKhuyenMaiSelect.CHITIETKM_ID === null) return
    //   let ds = this.dataKhuyenMaiSelect
    //   if (ds) {
    //     this.dtpThangKT.value = DateTimeLib.toDateObject(moment(val).add(Number(ds.HUONG_DC), 'M').subtract(1, 'M'))
    //     if (ds.LOAI_BDKM.toString() === '2') {
    //       this.thangMG = DateTimeLib.toDateObject(moment(new Date(val)).add(Number(ds.THANG_BDKM) + Number(ds.HUONG_DC) - 1, 'M'))
    //     } else if (ds.LOAI_BDKM.toString() === '4') {
    //       this.thangMG = DateTimeLib.toDateObject(moment(new Date(val)).add(Number(ds.THANG_BDKM), 'M'))
    //     } else {
    //       this.thangMgEnabled = true
    //     }
    //   }
    // },
    soThang (val) {
      if (!val || !this.cuocDC) {
        return
      }

      this.tienTd = Math.round(parseFloat(this.cuocDC) / parseInt(val))
    },
    cuocDC (val) {
      if (!val || !this.cuocDC) {
        return
      }

      this.tienTd = Math.round(parseFloat(val) / parseInt(this.soThang))
    },
    dsDatCoc (value) {
      // if (!value || value.length <= 0) {
      //   this.onSelectRowDatCoc(null)
      // }
      // if (this.thueBaoThoaiTraId) {
      //   let index = value.findIndex(x => x.THUEBAO_DC_ID == this.thueBaoThoaiTraId)
      //   // if (index != -1) {
      //   // this.$nextTick(() => this.$refs.dsDatCoc.setCurrentSelectedRow(2))
      //   // }
      //   setTimeout(() => {
      //     this.$refs.dsDatCoc.setCurrentSelectedRow(index !== -1 ? index : 0)
      //   }, 200)
      // }
    },
    tthdId (value) {
      this.btnThanhToan.enabled = parseInt(value) === 1
      this.btnCapNhatDB.enabled = parseInt(value) === 2
    }
  },
  async created () {
    try {
      this.loading(true)
      let rsPhiTTN = await this.getThongTinPhiTTN()
      if (rsPhiTTN.length > 0) {
        this.khoanmucPhiTTN.MA_KMTT = rsPhiTTN[0].MA_KMT
        this.khoanmucPhiTTN.TEN_MKMTT = rsPhiTTN[0].TEN_MKMTT
        this.khoanmucPhiTTN.TYLE_VAT = rsPhiTTN[0].TYLE_VAT
        this.isShowPhiTTN = true
      }

      if (this.$route.query.ma_gd !== undefined && this.$route.query.ma_gd !== null) {
        this.maGiaoDich = this.$route.query.ma_gd
      } else if (this.$route.query.ma_tb !== undefined && this.$route.query.ma_tb !== null) {
        this.maThueBao = this.$route.query.ma_tb
      }
      if (this.$route.query.ob_id !== undefined && this.$route.query.ob_id !== null) {
        this.ob_id = this.$route.query.ob_id
      }
      if (this.$route.query.user_ipcc !== undefined && this.$route.query.user_ipcc !== null) {
        this.user_ipcc = this.$route.query.user_ipcc
      }
    } catch (err) {

    } finally {
      this.loading(false)
    }
  },
  async mounted () {
    this.setButton(-1)

    try {
      this.loading(true)
      let loading1 = this.layDsThamSoMacDinh()
      let loading2 = this.layDsDichVu()
      let loading3 = this.layDsNhomDatCoc()
      let loading4 = this.layDsKieuLd()
      await loading1
      await loading2
      await loading3
      await loading4
    } finally {
      this.loading(false)
    }

    if (!!this.dsKieuLd && this.dsKieuLd.length > 0) {
      this.kieuLdId = this.dsKieuLd[0].KIEULD_ID
    }

    if (!!this.dsDichVu && this.dsDichVu.length > 0) {
      this.dichVuId = this.dsDichVu[0].DICHVUVT_ID
    }

    if (!!this.dsNhomDatCoc && this.dsNhomDatCoc.length > 0) {
      this.nhomDatCocId = this.dsNhomDatCoc[0].NHOM_DATCOC_ID
    }

    if (this.tsMacDinhThoaiDcLuiThang) {
      let thangThoaiTraTmp = new Date(this.thangThoaiTra)
      thangThoaiTraTmp.setMonth(thangThoaiTraTmp.getMonth() - 1)
      this.thangThoaiTra = thangThoaiTraTmp
      this.dtpNgayThoai.value = this.thangThoaiTra
    }

    if (!!this.maGiaoDich) {
      await this.onEnterInputMaGiaoDich()
    } else if (!!this.maThueBao) await this.onEnterInputSoMay()
  },
  methods: {
    customAggregate (data) {
      if (!!data.items) {
        let selected = data.items.filter(x => x._CHECK === true).reduce((a, b) => +a + +b.CUOC_DC, 0)
        return selected 
      } else {
        let selected = data.filter(x => x._CHECK === true).reduce((a, b) => +a + +b.CUOC_DC, 0)
        return selected
      }
      
    },
    async onClickBtnHoanThien () {
      if (!this.btnCapNhatDB.enabled) {
        return
      }

      if (!this.tthdId) {
        this.$root.toastError(`Chưa chọn hợp đồng để hoàn thiện!`)
        return
      }

      if (this.tthdId != 2) {
        //wtf
        let tthd = await this.layTrangThaiHd(this.tthdId)
        this.$root.toastError(`Hợp đồng ${tthd.TRANGTHAI_HD} không thể hoàn thiện!`)
        return
      }
      //datnt 22/06/2023
      let kq = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/fn_kiemtra_thaydoi_datcoc`, { "vhdkh_id" : this.hdkhId, "vkieuld_id" : this.kieuLdId })
      let str_kt = kq.data ? kq.data : kq.message_detail
      if (str_kt != "1")
      {
          this.$toast.warning(`${str_kt}`);
          return;
      }

      try {
        this.loading(true)
        await this.hoanThienThayDoiDatCoc(this.hdkhId)
      } catch (err) {
        this.$root.toastError(err)
        return
      } finally {
        this.loading(false)
      }

      this.$root.toastSuccess('Cập nhật dữ liệu thành công!')
      this.dsHdtb = []
      this.setButton(0)
    },
    async okXoaHopDong () {
      try {
        this.loading(true)
        await this.xoaHopDong(this.hdkhId)
      } catch (err) {
        this.$root.toastError(err)
        return
      } finally {
        this.loading(false)
      }

      this.$refs['ref-modal-xacnhan-xoa'].hide()
      this.$root.toastSuccess('Xóa hợp đồng khách hàng thành công!')
      this.dsHdtb = []
      this.clear()
      this.setButton(0)
    },
    async onClickBtnXoa () {
      if (!this.btnXoa.enabled) {
        return
      }

      if (!this.hdkhId) {
        this.$root.toastError('Chưa chọn hợp đồng để xóa')
        return
      }

      this.$refs['ref-modal-xacnhan-xoa'].show()
    },
    resetValidation () {
      this.error.ctvId = false
      this.error.nguoiGtId = false
      this.error.tienThoaiTra = false
      this.error.maThueBao = false
      this.error.thangThoaiTra = false
      this.error.traTruocId = false
      this.error.chiTietKhuyenMaiId = false
    },
    async kiemTraDuLieu (isThemMoi) {
      // this.resetValidation()

      let errorMessage = []

      if (this.chkCtv && !this.tenCtv && !this.ctvId) {
        errorMessage.push('Bạn chưa chọn cộng tác viên !')
        // this.error.ctvId = true
      }

      if (this.chkNguoiGt && !this.tenNguoiGt && !this.nguoiGtId) {
        errorMessage.push('Bạn chưa chọn người giới thiệu hợp đồng !')
        // this.error.nguoiGtId = true
      }

      let selected = this.dsDatCoc.filter(x => x._CHECK === true)


      if (selected.length <= 0) {
        errorMessage.push('Chưa chọn thuê bao cần thay đổi đặt cọc.')
      }

      let promises = []

      for (let i = 0; i < selected.length; i++) {
        promises.push(await this.kiemTraTBDatCoc(isThemMoi, selected[i]))       
      }

      let ret = await Promise.all(promises)
      
      for(let i = 0; i < ret.length; i++) {
        if (ret[i] !== true) errorMessage = [...errorMessage, ...ret[i]]
      }
      if (errorMessage.length > 0) {
        throw errorMessage.join('\r\n')
      }
    },

    async kiemTraTBDatCoc(isThemMoi, thuebao) {
      try{
        console.log(thuebao)
        let errorMessage = []
        if (thuebao.TIEN_THOAI === null) {
          errorMessage.push('Tiền thoái trả của thuê bao ' + thuebao.MA_TB + ' không được để trống !')
        }
        let thanght = parseInt(this.yyyymm(new Date()))
        let phanVungId = this.$root.token.getPhanVungID()
        if (!(thuebao.nhomDatCocIdCu == 2 && phanVungId == 26/* HPG */)) {
          if (thuebao.thangKtCu < thanght && thuebao.thangBdCu > 0 && parseFloat(thuebao.TIEN_THOAI) <= 0) {
            errorMessage.push('Đặt cọc của thuê bao ' + thuebao.MA_TB + ' đã hết hạn không thay đổi được !')
          }
        }
        if (thuebao.ttdcId == 1) {
          errorMessage.push('Đặt cọc của thuê bao ' + thuebao.MA_TB + ' đã thoái trả không thay đổi được !')
        }
        if (thuebao.allowThoaiTra == 0) {
          errorMessage.push('Bạn không được thực hiện thoái trả đặt cọc đối với nhóm đặt cọc này. !')
        }
        if (this.tsHuyDatCocTheoThang && thuebao.ngayYcCu != thanght) {
          errorMessage.push('Đặt cọc đã hết hạn không thay đổi được!. Chỉ được thay đổi trong tháng đặt cọc.')
        }

        if (!!this.tienThoaiTra && this.tienThoaiTra > this.tienThoai && this.tsKhongVuotQuaTienThoai) {
          errorMessage.push('Số tiền thoái trả không được nhập lớn hơn số tiền thoái.')
          this.error.tienThoaiTra = true
        }

        let thoaiTra = parseInt(this.yyyymm(thuebao.THANG_THOAI))

        let ngayCn = await this.lay_ngay_cn()
        let date = new Date(ngayCn)
        date.setDate(1)
        let thangHienTai = parseInt(this.yyyymm(date))
        date.setMonth(date.getMonth() - 1)
        let thangTruoc = parseInt(this.yyyymm(date))

        if (thoaiTra < thangTruoc) {
          errorMessage.push('Tháng thoái trả của thuê bao ' + thuebao.MA_TB + ' không được nhỏ hơn tháng trước !')
        }

        if (thoaiTra < thangHienTai) {
          errorMessage.push('Tháng thoái trả của thuê bao ' + thuebao.MA_TB + ' không được nhỏ hơn tháng hiện tại !')
        }

        if (!thuebao.KHUYENMAI_ID) {
          
          errorMessage.push('Bạn hãy chọn khuyến mại cho thuê bao ' + thuebao.MA_TB + '!')
        }

        if (!thuebao.CHITIETKM_ID) {
          errorMessage.push('Bạn hãy chọn trả trước cho thuê bao ' + thuebao.MA_TB + '!')
          // this.error.chiTietKhuyenMaiId = true
        }
        if (parseFloat(thuebao.CUOC_DC) < parseFloat(thuebao.TIEN_THOAI)) {
          errorMessage.push('Tiền thoái trả của thuê bao ' + thuebao.MA_TB + ' > Tiền đặt cọc mới. Bạn không được thực hiện !')
          // this.error.tienThoaiTra = true
        }

        if (!isThemMoi && thuebao.tthdId !== 1/* mới */) {
          errorMessage.push('Trạng thái hợp đồng của thuê bao ' + thuebao.MA_TB + ' khác Mới. Bạn không được cập nhật !')
        }
        if (errorMessage.length > 0) {
          return errorMessage
        }
        return true
      }catch(error){
        console.log(error)
        return false
      }finally{}

      
    },
    async lay_ngay_cn () {
      try {
        if (this.cfg_ngay_cn !== null)  return this.cfg_ngay_cn
        let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCocHuy/lay_ngay_cn`)
        // return !response.data ? null : response.data.ngay_cn
        this.cfg_ngay_cn = moment(rps.data.ngay_cn, 'YYYY-MM-DD HH:mm:ss').toDate()
        return this.cfg_ngay_cn
      } catch (err) {
        return null
      }
    },
    async onClickBtnGhiLai2 () {
      if (!this.btnGhiLai.enabled) {
        return
      }

      let isThemMoi = !this.btnNhapMoi.enabled

      try {
        await this.kiemTraDuLieu(isThemMoi)
      } catch (err) {
        this.$root.toastError(err)
        return
      }
      

      try {
        this.loading(true)

        let postData = await this.taoDuLieuCapNhat(isThemMoi)
        // console.log(postData)

        if (isThemMoi) {
          let result = await this.themHopDong(postData)

          if (result) {
            // await this.HienThiDanhSacHDTB()
            await this.onEnterInputMaGiaoDich()
          } else {
            this.$root.$toast.error('Lỗi thêm mới dữ liệu!')
          }
        } else {
          // data.hdkh_id = this.hdkhId
          let result = await this.capNhatHopDong(data)

          if (result) {
            await this.onEnterInputMaGiaoDich()
          } else {
            this.$root.$toast.error('Lỗi cập nhật dữ liệu!')
          }
        }
      } catch (err) {
        this.$root.toastError(err)
        return
      } finally {
        this.loading(false)
      }
      return
    },
    async onClickBtnGhiLai () {
      if (!this.btnGhiLai.enabled) {
        return
      }

      let isThemMoi = !this.btnNhapMoi.enabled

      try {        
        await this.kiemTraDuLieu(isThemMoi)
      } catch (err) {
        this.$root.toastError(err)
        return
      }

      // if (isThemMoi) {
      //   let maGdDuocSinh = null

      //   try {
      //     maGdDuocSinh = await this.sinhMaGiaoDich()
      //   } catch (err) {
      //     this.$root.toastError(err)
      //     return
      //   }

      //   this.maGiaoDich = maGdDuocSinh
      // }

      let postData = this.taoDuLieuCapNhat(isThemMoi)
      console.log(postData)
      return

      try {
        this.loading(true)

        if (isThemMoi) {
          let result = await this.themHopDong(postData)

          if (result.ERR) {
            throw result.ERR
          }

          // this.hdkhId = result.HDKH_ID
          this.$root.toastSuccess('Thêm mới dữ liệu thành công!')
        } else {
          // data.hdkh_id = this.hdkhId
          // let result = await this.capNhatHopDong(data)

          // if (result.ERR) {
          //   throw result.ERR
          // }

          this.$root.toastSuccess('Cập nhật dữ liệu thành công!')
        }
      } catch (err) {
        this.$root.toastError(err)
        return
      } finally {
        this.loading(false)
      }

      await this.hienThiDanhSachHdtb()
    },
    async taoDuLieuCapNhat (isThemMoi) {
      let selected = this.dsDatCoc.filter(x => x._CHECK === true)
      let postData = []
      for(const item of selected) {
        let old = this.dsDatCoc_bk.find(x => x._KEY === item._KEY)
        let tb = {
          donvi_id: this.$root.token.getDonViID(),
          nhanvien_id: this.$root.token.getNhanVienID(),
          khachhang_id: item.KHACHHANG_ID,
          ghichu: item.GHICHU,
          ctv_id: this.chkCTV && this.txtCTV !== '' ? this.ctv_id : null,
          nhanviengt_id: this.chkNguoiGT && this.txtNguoiGT !== '' ? this.nguoigt_id : null,
          thuebao_id: item.THUEBAO_ID,
          dichvuvt_id: item.DICHVUVT_ID,
          thuebao_dc_id: item.THUEBAO_DC_ID,
          thuebao_thoaitra_id: item.THUEBAO_DC_ID,
          nhomtb_id: item.NHOMTB_ID,
          rkm_id: item.RKM_ID,
          nhom_datcoc_id_cu: old.NHOM_DATCOC_ID,
          nhom_datcoc_id_moi: item.NHOM_DATCOC_ID,
          thang_bd: item.THANG_BD,
          thang_kt: item.THANG_KT,
          ngay_bd: item.NGAY_BD_DC,
          ngay_kt: item.NGAY_KT_DC,
          ngay_dk: moment(this.ngayYeuCau).format('DD/MM/YYYY'),
          ngay_yc: moment(this.ngayYeuCau).format('DD/MM/YYYY'),
          thang_thoaitra: item.THANG_THOAI,
          ngay_thoaitra: item.NGAY_THOAI,
          thang_bd_mg: item.THANG_BD_MG,
          thang_kt_mg: item.THANG_KT_MG,
          ngay_bd_mg: item.NGAY_BD,
          ngay_kt_mg: item.NGAY_KT,
          chitiet_km_id: item.CHITIETKM_ID,
          khuyenmai_id: item.KHUYENMAI_ID,
          cuoc_dc: item.CUOC_DC,
          tien_td: item.TIEN_TD,
          hdkh_id: isThemMoi ? null : item.hdkh_id,
          hdtb_id: isThemMoi ? null : item.hdtb_id,
          ct_tienhd_id: isThemMoi ? null : item.ct_tienhd_id,
          tien_thoaitra: 0,
          vat_thoaitra: 0,
          tien_datcoc: 0,
          vat_datcoc: 0,
          thang_bd_cu: old.THANG_BD,
          thang_kt_cu: old.THANG_KT,
          ngay_bd_cu: old.NGAY_BD,
          ngay_kt_cu: old.NGAY_KT
        }

        tb.tien_thoaitra = Math.round(parseFloat(item.TIEN_THOAI) / 1.1)
        tb.vat_thoaitra = parseFloat(item.TIEN_THOAI) - tb.tien_thoaitra

        if (tb.tien_thoaitra > 0) {
          tb.tien_thoaitra = tb.tien_thoaitra * -1
          tb.vat_thoaitra = tb.vat_thoaitra * -1
        }

        tb.tien_datcoc = Math.round(parseFloat(item.CUOC_DC) / 1.1)
        tb.vat_datcoc = parseFloat(item.CUOC_DC) - tb.tien_datcoc

        postData.push(tb)
      }

      return postData
    },
    onClickBtnLienHe () {
      if (this.hdkhId) {
        this.$refs['ref-thongtin-lienhe'].show()
      }
    },
    onClickBtnEmail () {
      if (this.hdkhId) {
        this.$refs['ref-thongtin-email'].show()
      }
    },
    async onClickBtnThanhToan () {
      if (!this.btnThanhToan.enabled) {
        return
      }

      if (this.tthdId > 1) {
        let tthd = await this.layTrangThaiHd(this.tthdId)
        this.$root.toastError(`Hợp đồng ${tthd.TRANGTHAI_HD} !`)
        return
      }

      this.loading(true)
      this.userComponentModule = thanhToan
      this.txtMaGDTT = this.maGiaoDich
    },
    open_thanhToanPopUp () {
      this.loading(false)
    },
    paymentcreated () {
      this.$refs.thanhtoanDialog.show(true)
    },
    async close_thanhToanPopUp () {
      this.userComponentModule = null

      if (this.maGiaoDich) {
        await this.onEnterInputMaGiaoDich()
      }
    },
    xacNhanNguoiGioiThieu (val) {
      if (val) {
        this.tenNguoiGt = !val.ten_ht ? '' : val.ten_ht
        this.nguoiGtId = !val.nhanvien_id ? '' : val.nhanvien_id
      }

      this.$refs.popupNguoiGioiThieu.hide()
    },
    onChangeCheckNguoiGt (event) {
      if (event.target.checked) {
        this.$refs.popupNguoiGioiThieu.show()
      }
    },
    xacNhanCongTacVien (val) {
      if (val) {
        this.tenCtv = !val.ten_ht ? '' : val.ten_ht
        this.ctvId = !val.nhanvien_id ? '' : val.nhanvien_id
      }

      this.$refs.popupCongTacVien.hide()
    },
    onChangeCheckCtv (event) {
      if (event.target.checked) {
        this.$refs.popupCongTacVien.show()
      }
    },
    onChonKhuyenMai (data) {
      if (!!data && !!data.khuyenmai.khuyenmai_id) {
        this.traTruocId = parseInt(data.khuyenmai.khuyenmai_id)
      }

      this.$refs['ref-chon-khuyen-mai'].hide()
    },
    onClickBtnChonKhuyenMai () {
      let dt = this.dsTraTruoc.map(item => {
        let newItem = {}
        Object.keys(item).forEach(key => newItem[key.toLowerCase()] = item[key])
        return newItem
      })

      this.popupChonKhuyenMaiData = {
        loai: 0, // khuyến mại
        dt: dt
      }

      this.$refs['ref-chon-khuyen-mai'].show()
    },
    async loadTraTruoc () {
      if (!this.THUEBAO.THUEBAO_ID) return
      
      let data = {
        vkhuyenmai_id: this.traTruocId,
        vloaitb_id: this.THUEBAO.LOAITB_ID,
        vnhom_datcoc_id: this.THUEBAO.NHOM_DATCOC_ID,
        vthuebao_id: this.THUEBAO.THUEBAO_ID,
        vtocdotn_id: this.THUEBAO.TOCDO_ID,
        vmuccuoctn_id: this.THUEBAO.MUCUOC_ID,
        vtrangbi_id: this.THUEBAO.TRANGBI_ID
      }

      try {

        this.dsChiTietKhuyenMai = []
        this.loading(true)
        // await this.layDsChiTietKhuyenMai(data)
        let ctkm = await this.layDsChiTietKhuyenMaiV2(data)
        // this.dsChiTietKhuyenMai = this.toUpperCases(rps.data)
        let ret = ctkm.map(x => x.HUONG_DC)
        let v1 = [...new Set(ret)]
        v1.sort(function(a, b){return a - b})
        let aV1 = []
        v1.forEach(item => {
          aV1.push({ VALUE: item })
        })
        this.dsSoThang = [...aV1]
        this.dsCTKM = [...ctkm]

        if (this.THUEBAO.SOTHANG !== null) {
          // let f = aV1.find(x => x.VALUE === this.THUEBAO.SOTHANG)
          // console.log('SOTHANG', aV1, this.THUEBAO.SOTHANG, f)
          // if (f !== undefined) {
          //   this.soThang = this.THUEBAO.SOTHANG
          // }
          this.soThang = this.THUEBAO.SOTHANG
        }

        
      } finally {
        this.loading(false)
      }

      // if (this.dsChiTietKhuyenMai.length > 0) {
      //   let hasSelected = false
        
      //   if (this.ct_khuyenmai.id) {
      //     hasSelected = this.dsChiTietKhuyenMai.some(item => item.CHITIETKM_ID == this.ct_khuyenmai.id)
      //   }

      //   if (!hasSelected) {
      //     this.chiTietKhuyenMaiId = this.dsChiTietKhuyenMai[0].CHITIETKM_ID
      //     this.ct_khuyenmai.id = this.dsChiTietKhuyenMai[0].CHITIETKM_ID
      //     this.ct_khuyenmai.text = this.dsChiTietKhuyenMai[0].TEN_CTKM
      //     this.soThang = this.dsChiTietKhuyenMai[0].HUONG_DC
      //   }
      // }
    },
    onClickBtnHuy () {
      if (this.btnHuyBo.enabled) {
        this.setButton(0)
        this.clear()
      }
    },
    onClickBtnNhapMoi () {
      if (this.btnNhapMoi.enabled) {
        this.clear()
        this.setButton(1)
      }
    },
    async btnXacNhanTTDatCoc() {
      if (this.dsDatCoc.length <= 0)  return
      // let selected = this.$refs.dsDatCoc.getSelectedRecords()
      //  console.log(selected)
      let foundIndex = this.dsDatCoc.findIndex(x => x.THUEBAO_ID == this.THUEBAO.THUEBAO_ID && x.NHOMTB_ID === this.THUEBAO.NHOMTB_ID)

      let errors = []
      if (this.nhomDatCocId === null) {
        errors.push('Chưa chọn Nhóm đặt cọc.')
      }
      if (this.traTruocId === null) {
        errors.push('Chưa chọn Trả trước.')
      }
      if (this.ct_khuyenmai.id === null) {
        errors.push('Chưa chọn Chi tiết khuyến mãi.')
      }
      if (errors.length > 1) {
        this.$toast.error(errors.join('\r\n'))
        return
      }
      if (this.nhomDatCocId === 4) {

        let ds_ddv = this.dsDatCoc_bk.filter(x => x.GOI_DDV_ID === this.THUEBAO.GOI_DDV_ID)
        let not_ds_ddv = this.dsDatCoc.filter(x => x.GOI_DDV_ID !== this.THUEBAO.GOI_DDV_ID)

        let ds = []

        for (const element of ds_ddv) {
          let item = {...element}
          let postData = {
            vkhuyenmai_id: this.traTruocId,
            vloaitb_id: item.LOAITB_ID,
            vnhom_datcoc_id: this.nhomDatCocId,
            vthuebao_id: item.THUEBAO_ID,
            vtocdotn_id: item.TOCDO_ID,
            vmuccuoctn_id: item.MUCUOC_ID,
            vtrangbi_id: item.TRANGBI_ID
          }
          let ret = await this.layDsChiTietKhuyenMaiV2(postData)
          let ctkm = ret.filter(x => x.HUONG_DC === Number(this.soThang))
          if(ctkm.length > 0) {
            for (const element2 of ctkm) {
              // console.log(element2)
              let item2 = {...element}
              item2.SOTHANG = this.soThang
              item2.NGAY_BD_DC = moment(this.dtpNgayBDDC.value).format('DD/MM/YYYY')
              item2.NGAY_KT_DC = moment(this.dtpNgayKTDC.value).format('DD/MM/YYYY')
              item2.THANG_BD = moment(this.dtpThangBD.value).format('YYYYMM')
              item2.THANG_KT = moment(this.dtpThangKT.value).format('YYYYMM')
              item2.NGAY_BD = moment(this.dtpNgayBD.value).format('DD/MM/YYYY')
              item2.NGAY_KT = moment(this.dtpNgayKT.value).format('DD/MM/YYYY')
              item2.THANG_BD_MG = moment(this.dtpThangBDMG.value).format('YYYYMM')
              item2.THANG_KT_MG = moment(this.dtpThangKTMG.value).format('YYYYMM')
              item2.CUOC_DC = element2.DATCOC_CSD
              item2.TIEN_TD = element2.TIEN_TD
              item2.NGUOI_CN = this.$auth.getUserName()
              item2.KHUYENMAI_ID = this.traTruocId
              item2.CHITIETKM_ID = element2.CHITIETKM_ID
              item2.TEN_CHITIETKM = element2.CHITIETKM_ID + '-' + element2.TEN_CTKM
              item2.NHOM_DATCOC_ID = this.nhomDatCocId
              item2.GHICHU = this.ghiChu
              item2.NGAY_THOAI = moment(this.dtpNgayThoai.value).format('DD/MM/YYYY')
              item2.THANG_THOAI = moment(this.thangThoaiTra).format('YYYYMM')
              let tienThoaiDuocTinh = await this.layTienThoaiDatCoc_v4({
                rkm_id: item.RKM_ID,
                thuebao_dc_id: item.THUEBAO_DC_ID,
                thang_thoai: this.yyyymm(this.thangThoaiTra),
                ngay_thoai: this.yyyymmdd(this.dtpNgayThoai.value)
              })
              item2.TIEN_THOAI = tienThoaiDuocTinh
              item2._CHECK = true
              console.log(item2)
              ds.push(item2)
            } // end for  
          } else {
            item._CHECK = true
            ds.push(item)
          }
        } // end for

        for (const element of not_ds_ddv) {
          ds.push(element)
        }

        console.log(ds)
        
        // let ds = []
        // for (const element of this.dsDatCoc) {
        //   let item = {...element}
        //   console.log('item', item)
        //   let postData = {
        //     vkhuyenmai_id: this.traTruocId,
        //     vloaitb_id: item.LOAITB_ID,
        //     vnhom_datcoc_id: this.nhomDatCocId,
        //     vthuebao_id: item.THUEBAO_ID,
        //     vtocdotn_id: item.TOCDO_ID,
        //     vmuccuoctn_id: item.MUCUOC_ID,
        //     vtrangbi_id: item.TRANGBI_ID
        //   }
        //   let ret = await this.layDsChiTietKhuyenMaiV2(postData)
        //   let ctkm = ret.filter(x => x.HUONG_DC === Number(this.soThang))
        //   if(ctkm.length > 0) {
        //     for (const element2 of ctkm) {
        //       // console.log(element2)
        //       let item2 = {...element}
        //       item2.SOTHANG = this.soThang
        //       item2.NGAY_BD_DC = moment(this.dtpNgayBDDC.value).format('DD/MM/YYYY')
        //       item2.NGAY_KT_DC = moment(this.dtpNgayKTDC.value).format('DD/MM/YYYY')
        //       item2.THANG_BD = moment(this.dtpThangBD.value).format('YYYYMM')
        //       item2.THANG_KT = moment(this.dtpThangKT.value).format('YYYYMM')
        //       item2.NGAY_BD = moment(this.dtpNgayBD.value).format('DD/MM/YYYY')
        //       item2.NGAY_KT = moment(this.dtpNgayKT.value).format('DD/MM/YYYY')
        //       item2.THANG_BD_MG = moment(this.dtpThangBDMG.value).format('YYYYMM')
        //       item2.THANG_KT_MG = moment(this.dtpThangKTMG.value).format('YYYYMM')
        //       item2.CUOC_DC = element2.DATCOC_CSD
        //       item2.TIEN_TD = element2.TIEN_TD
        //       item2.NGUOI_CN = this.$auth.getUserName()
        //       item2.KHUYENMAI_ID = this.traTruocId
        //       item2.CHITIETKM_ID = element2.CHITIETKM_ID
        //       item2.TEN_CHITIETKM = element2.CHITIETKM_ID + '-' + element2.TEN_CTKM
        //       item2.NHOM_DATCOC_ID = this.nhomDatCocId
        //       item2.GHICHU = this.ghiChu
        //       item2.NGAY_THOAI = moment(this.dtpNgayThoai.value).format('DD/MM/YYYY')
        //       item2.THANG_THOAI = moment(this.thangThoaiTra).format('YYYYMM')
        //       let tienThoaiDuocTinh = await this.layTienThoaiDatCoc_v4({
        //         rkm_id: item.RKM_ID,
        //         thuebao_dc_id: item.THUEBAO_DC_ID,
        //         thang_thoai: this.yyyymm(this.thangThoaiTra),
        //         ngay_thoai: this.yyyymmdd(this.dtpNgayThoai.value)
        //       })
        //       item2.TIEN_THOAI = tienThoaiDuocTinh
        //       item2._CHECK = true
        //       console.log(item2)
        //       ds.push(item2)
        //     }
        //   } else {
        //     item._CHECK = true
        //     ds.push(item)
        //   }
        // }
        // console.log(ds)

        this.dsDatCoc = [...ds]
      } else {
        let ds = [...this.dsDatCoc]
        ds[foundIndex].SOTHANG_DC = this.soThang
        ds[foundIndex].NGAY_BD_DC = moment(this.dtpNgayBDDC.value).format('DD/MM/YYYY')
        ds[foundIndex].NGAY_KT_DC = moment(this.dtpNgayKTDC.value).format('DD/MM/YYYY')
        ds[foundIndex].THANG_BD = moment(this.dtpThangBD.value).format('YYYYMM')
        ds[foundIndex].THANG_KT = moment(this.dtpThangKT.value).format('YYYYMM')
        ds[foundIndex].NGAY_BD = moment(this.dtpNgayBD.value).format('DD/MM/YYYY')
        ds[foundIndex].NGAY_KT = moment(this.dtpNgayKT.value).format('DD/MM/YYYY')
        ds[foundIndex].THANG_BD_MG = moment(this.dtpThangKTMG.value).format('YYYYMM')
        ds[foundIndex].THANG_KT_MG = moment(this.dtpThangKTMG.value).format('YYYYMM')
        ds[foundIndex].CUOC_DC = Number(this.cuocDC)
        ds[foundIndex].TIEN_TD = Number(this.tienTd)
        ds[foundIndex].NGUOI_CN = this.$auth.getUserName()
        ds[foundIndex].KHUYENMAI_ID = this.traTruocId
        ds[foundIndex].CHITIETKM_ID = this.ct_khuyenmai.id
        ds[foundIndex].TEN_CHITIETKM = this.ct_khuyenmai.id + '-' + this.ct_khuyenmai.text
        ds[foundIndex].NHOM_DATCOC_ID = this.nhomDatCocId
        ds[foundIndex].GHICHU = this.ghiChu
        ds[foundIndex].NGAY_THOAI = moment(this.dtpNgayThoai.value).format('DD/MM/YYYY')
        ds[foundIndex].THANG_THOAI = moment(this.thangThoaiTra).format('YYYYMM')
        ds[foundIndex].TIEN_THOAI = this.tienThoaiTra

        this.dsDatCoc = [...ds]
      }
      
    },
    btnXoaTTDatCoc() {
      this.CLearThongTinDatCoc()
      this.btnXacNhanTTDatCoc()
    },
    async setThongTinThueBao (data) {
      if (!data) {
        this.dichVuId = null
        this.loaiTbId = null
        this.maThueBao = null
        this.doiTuongId = null
        this.thuongHieu = null
        this.tocDoId = null
        this.tenGoiDDV = null
        this.tenKhachHangHienThi = null
        this.diaChiKh = null
        this.tthdId = null
        return
      }
      this.dichVuId = data.DICHVUVT_ID
      this.loaiTbId = data.LOAITB_ID
      this.maThueBao = data.MA_TB
      this.doiTuongId = data.DOITUONG_ID
      this.thuongHieu = data.TEN_TOCDO
      this.tocDoId = data.TOCDO_ID
      this.tenKhachHangHienThi = data.TEN_TB
      this.diaChiKh = data.DIACHI_LD
      this.tenGoiDDV = data.TEN_GOI_DDV
      this.tthdId = data.TTHD_ID

      this.btnThanhToan.enabled = (this.tthdId === 1)
      this.btnCapNhatDB.enabled = (this.tthdId === 2)

    },
    async setThongTinDatCoc (data) {
      console.log('setThongTinDatCoc ', data)
      if (!data) {
        // this.datCocDuocChon = null
        this.THUEBAO.thangBdCu = 0
        this.THUEBAO.thangKtCu = 0
        this.THUEBAO.nhomDatCocIdCu = 0
        this.THUEBAO.ttdcId = 0
        this.THUEBAO.cuocDcCu = 0
        this.THUEBAO.tienTdCu = 0
        this.THUEBAO.ngayBdCu = ''
        this.THUEBAO.ngayKtCu = ''   
        return
      } 

      // this.datCocDuocChon = data
      this.nhomDatCocId = data.NHOM_DATCOC_ID
      this.THUEBAO.thangBdCu = data.THANG_BD
      this.THUEBAO.thangKtCu = data.THANG_KT
      this.THUEBAO.ngayBdCu = data.NGAY_BD
      this.THUEBAO.ngayKtCu = data.NGAY_KT
      this.THUEBAO.ngayYcCu = data.NGAY_YC
      this.THUEBAO.nhomDatCocIdCu = data.NHOM_DATCOC_ID
      this.THUEBAO.cuocDcCu = data.CUOC_DC
      this.THUEBAO.tienTdCu = data.TIEN_TD
      this.THUEBAO.thueBaoThoaiTraId = data.THUEBAO_DC_ID
      this.THUEBAO.allowThoaiTra = data.THOAITRA
      this.THUEBAO.RKM_ID = data.RKM_ID


      if (data.DICHVUVT_ID === this.ADSL) {
        let contex = await this.MAP_ID('TOCDO_ID', 'CSS', 'DB_ADSL', 'THUEBAO_ID', data.THUEBAO_ID)
        if (contex !== null && contex.toString() !== '-1') {
          this.tocDoId= contex
        }
      }
      if (data.KHUYENMAI_ID !== null) {
        this.traTruocId = data.KHUYENMAI_ID
      } else {
        this.traTruocId = null      
      }

      await this.HT_DS_KhuyenMai_Combobox(data.KHUYENMAI_ID)

      if (data.CHITIETKM_ID !== null) {
        await this.loadTraTruoc(data.CHITIETKM_ID)
        this.chiTietKhuyenMaiId = data.CHITIETKM_ID
        this.f_capnhat_from_grid = true // cập nhật ngày, tháng đặt cọc từ grid
        let f = this.dsChiTietKhuyenMai.find(x => x.CHITIETKM_ID === data.CHITIETKM_ID)
        if (f !== undefined) {
          this.ct_khuyenmai.id = f.CHITIETKM_ID
          this.ct_khuyenmai.text = f.TEN_CTKM
          this.ct_khuyenmai.so_thang = f.HUONG_DC
        } else {
          this.ct_khuyenmai.id = null
          this.ct_khuyenmai.text = null
          this.ct_khuyenmai.so_thang = null
        }
        
      } else {
        this.chiTietKhuyenMaiId = null
        this.ct_khuyenmai.id = null
        this.ct_khuyenmai.text = null
        this.ct_khuyenmai.so_thang = null
      }      

      // this.rkmId = data.RKM_ID
      // this.ttdcId = !data.TTDC_ID ? 0 : data.TTDC_ID
      this.soThang = data.SOTHANG_DC
      this.cuocDC = data.CUOC_DC
      this.tienTd = data.TIEN_TD
      this.ghiChu = data.GHICHU
      this.dtpNgayThoai.value = !data.NGAY_THOAI ? this.thangThoaiTra : moment(data.NGAY_THOAI, 'DD/MM/YYYY').toDate()
      
      if (this.tsTinhTienThoaiMoi) {
        let tienThoaiDuocTinh = await this.layTienThoaiDatCoc_v4({
          rkm_id: data.RKM_ID,
          thuebao_dc_id: data.THUEBAO_THOAITRA_ID,
          thang_thoai: this.yyyymm(this.thangThoaiTra),
          ngay_thoai: this.yyyymmdd(this.dtpNgayThoai.value)
        })
        this.tienThoai = tienThoaiDuocTinh
        this.tienThoaiTra = tienThoaiDuocTinh
      } else {
        this.tinhTienThoai()
      }
    },
    // async onSelectRowDatCoc (data) {
    //   console.log('🚀 ~ file: index.vue:1027 ~ onSelectRowDatCoc ~ data:', data)
    //   await this.setThongTinThueBao(data)
    //   await this.setThongTinDatCoc(data)

    // },
    async tinhTienThoai () {
      let tienThoai = 0

      if (this.thangBdCu) {
        let thangThoai = parseInt(this.yyyymm(this.thangThoaiTra))
        let soThang = (await this.tinhSoThang(thangThoai, this.thangKtCu)) - 1
        let soThangCu = await this.tinhSoThang(this.thangBdCu, this.thangKtCu)
        let tienTd = this.tienTdCu

        if (this.tienTdCu == 0) {
          tienTd = Math.round(this.cuocDcCu / soThangCu)
        }

        if (soThang >= soThangCu) {
          soThang = soThangCu
          tienThoai = this.cuocDcCu
        } else {
          tienThoai = tienTd * soThang
        }

        if (tienThoai > this.cuocDcCu) {
          tienThoai = this.cuocDcCu
        }
      } else {
        tienThoai = this.cuocDcCu
      }

      if (!tienThoai || tienThoai < 0) {
        tienThoai = 0
      }

      this.tienThoaiTra = tienThoai
      this.tienThoai = tienThoai
    },
    async onSelectRowHdtb (data) {

      console.log(data)
      if (!data) {
        this.hdtbDuocChon = null
        return
      }

      // if (!!this.hdtbDuocChon && this.hdtbDuocChon.KEY == data.KEY) {
      //   return
      // }

      this.hdtbDuocChon = data

      this.dichVuId = data.DICHVUVT_ID
      this.loaiTbId = data.LOAITB_ID
      this.doiTuongId = data.DOITUONG_ID
      this.thueBaoId = data.THUEBAO_ID
      this.tthdId = data.TTHD_ID
      this.maThueBao = data.MA_TB
      this.tenTb = data.TEN_TB
      this.diaChiLd = data.DIACHI_TB

      if (this.dichVuId == 4 /* băng rộng cố định ADSL */) {
        let tocDoId = await this.layTocDoCuaTbao(this.thueBaoId)
        let thongTinTocDo = await this.layThongTinTocDo(tocDoId)
        this.thuongHieu = !thongTinTocDo ? null : thongTinTocDo.THUONGHIEU
      } else {
        this.thuongHieu = null
      }

      this.activeEventThangThoaiTra = false

      await this.hienThiDatCoc(this.thueBaoId)

      let tienThoaiTraTmp = null

      for (let i = 0; i < this.dsHdtb.length; i++) {
        let hdtb = this.dsHdtb[i]

        if (hdtb.THUEBAO_THOAITRA_ID) {
          this.thueBaoThoaiTraId = hdtb.THUEBAO_THOAITRA_ID

          let thang = hdtb.THANG_THOAITRA_SHOW.split('/')[0]
          let nam = hdtb.THANG_THOAITRA_SHOW.split('/')[1]
          this.thangThoaiTra = new Date(parseInt(nam), parseInt(thang) - 1, 1)
          tienThoaiTraTmp = hdtb.TIENTHOAI
          this.rkmId = hdtb.RKM_ID
          this.ctTienHdId = hdtb.CT_TIENHD_ID
        }

        if (hdtb.CHITIETKM_ID) {
          this.nhomDatCocId = hdtb.NHOM_DATCOC_ID

          try {
            this.loading(true)
            await this.layDsTraTruoc({
              dichvuvt_id: this.dichVuId,
              thuebao_id: this.thueBaoId,
              loaitb_id: this.loaiTbId,
              doituong_id: this.doiTuongId,
              nhom_datcoc_id: this.nhomDatCocId
            })
          } finally {
            this.loading(false)
          }

          this.traTruocId = hdtb.KHUYENMAI_ID

          try {
            this.loading(true)
            await this.loadTraTruoc()
          } finally {
            this.loading(false)
          }

          this.chiTietKhuyenMaiId = hdtb.CHITIETKM_ID

          let thang = hdtb.THANG_BDSHOW.split('/')[0]
          let nam = hdtb.THANG_BDSHOW.split('/')[1]
          // this.thangBD = new Date(parseInt(nam), parseInt(thang) - 1, 1)

          this.cuocDC = hdtb.CUOC_DC
          this.tienTd = hdtb.TIEN_TD
          this.rkmDcId = hdtb.RKM_ID
          this.ctTienHdDcMoiId = hdtb.CT_TIENHD_ID
          this.f_capnhat_from_grid = true 
        }
      }

      if (data.NGAY_THOAI) {
        this.dtpNgayThoai.value = await DateTimeLib.toDateObjectWithFormat(moment(data.NGAY_THOAI, 'DD/MM/YYYY'), 'dd/MM/yyyy')
      } else this.dtpNgayThoai.value = ''
      // this.thangBD = await DateTimeLib.toDateObjectWithFormat(moment(data.THANG_BD, 'YYYYMM'), 'M')
      this.dtpThangBD.value = moment(data.THANG_BD, 'YYYYMM').toDate()
      if (data.NGAY_BD) {
        this.dtpNgayBD.value = await DateTimeLib.toDateObjectWithFormat(moment(data.NGAY_BD, 'DD/MM/YYYY'), 'dd/MM/yyyy')
      } else this.dtpNgayBD.value = ''
      if (data.NGAY_BD_DC) {
        this.dtpNgayBDDC.value = await DateTimeLib.toDateObjectWithFormat(moment(data.NGAY_BD_DC, 'DD/MM/YYYY'), 'dd/MM/yyyy')
      } else this.dtpNgayBDDC.value = this.dtpNgayBD.value

      // if(data.THUEBAO_THOAITRA_ID){
      //     this.dtpNgayThoai.value = await DateTimeLib.toDateObjectWithFormat(moment(data.NGAY_THOAI, 'DD/MM/YYYY'), 'dd/MM/yyyy')
      // } else {
      //   this.thangBD = await DateTimeLib.toDateObjectWithFormat(moment(data.THANG_BD, 'YYYYMM'), 'M')
      //   this.dtpNgayBD.value = await DateTimeLib.toDateObjectWithFormat(moment(data.NGAY_BD, 'DD/MM/YYYY'), 'dd/MM/yyyy')
      //   if(data.NGAY_BDDC){
      //     this.dtpNgayBDDC.value = await DateTimeLib.toDateObjectWithFormat(moment(data.NGAY_BDDC, 'DD/MM/YYYY'), 'dd/MM/yyyy')
      //   } else {
      //     this.dtpNgayBDDC.value = await this.dtpNgayBD.value
      //   }
      // }

      if (tienThoaiTraTmp) {
        setTimeout(() => {
          this.tienThoaiTra = Math.abs(tienThoaiTraTmp)
          this.activeEventThangThoaiTra = true
        }, 200)
      }

      this.setButton(3)
    },
    async onEnterInputSoMay () {
      if (!this.maThueBao) {
        return
      }    

      try {
        this.loading(true)
        let ds = await this.LAY_DS_THUEBAO_DATCOC_THEOTB(this.maThueBao)

        if (!ds || ds.length <= 0) {
          this.$alert('Không tìm thấy thông tin về mã thuê bao ' + this.maThueBao, 'Thông báo', {
          confirmButtonText: 'Đóng',
          callback: action => {
          }
          })
        }
        const dsUnique = ds.filter(x => x.MA_TB === this.maThueBao)
        const uniqueArr = [... new Set(dsUnique.map(data => data.LOAITB_ID))]

        // this.dsDatCoc = ds.map(x => ({ ...x, _CHECK: true, _KEY:  this.makeKey()}))

        if (uniqueArr.length > 1) {
            this.ds_tb = [...ds]
            this.$refs.refChonThueBao.openDialog()
            return false
        }

        let dsTBDaLapHD = ds.filter(x => x.HDKH_ID !== null)
        let dsTBChuaLapHD = ds.filter(x => x.HDKH_ID === null)

        this.ma_tb = this.maThueBao

        if (this.dsHdtb.length > 0) {  // đã có DS HD cũ
          let dsTBCungKhachHang = dsTBChuaLapHD.filter(x => x.KHACHHANG_ID === this.dsDatCoc[0].KHACHHANG_ID)
          this.dsDatCoc = [...this.dsDatCoc, ...dsTBCungKhachHang.map(x => ({ ...x, _CHECK: false, _KEY:  this.makeKey()}))]
        } else {
          if (dsTBDaLapHD.length <= 0) {
            this.dsDatCoc = [...dsTBChuaLapHD.map(x => ({ ...x, _CHECK: false, _KEY:  this.makeKey()}))]
          } else {
            this.dsDatCoc = [...dsTBDaLapHD.map(x => ({ ...x, _CHECK: true, _KEY:  this.makeKey()}))]
          }
          
        }

        this.dsDatCoc_bk = [...this.dsDatCoc]

        await this.HienThiDanhSachDatCoc()

        if (this.ma_tb) {
          let f = this.dsDatCoc.find(x => x.MA_TB === this.ma_tb)
          // f._CHECK = true
          let row = {rowData: f}
          // console.log(this.dsDatCoc, this.ma_tb)
          this.dsTBDatCocRecordClick(row)
        }

        // let f = this.dsDatCoc.find(x => x.HDKH_ID !== null && x.HDKH_ID !== undefined)
        // console.log('Dòng 1714 '
        // , f)
        // if (f !== undefined) {
        //   this.setButton(3)
        //   this.maGiaoDich = f.MA_GD
        // } else this.setButton(1)

        // let ds = await this.LAY_DS_THUEBAO_DATCOC(this.maThueBao)
        // this.ds_tb = ds.filter(x => x.MA_TB === this.txtMamaThueBaoTB)

        // if (this.ds_tb.length > 1) {
        //   this.$refs.refChonThueBao.openDialog()
        //   return false
        // }
        // this.ma_tb = this.maThueBao

        // if (this.dsHdtb.length > 0) {
        //   this.dsDatCoc = [...this.dsDatCoc, ...ds.map(x => ({ ...x, _CHECK: false, _KEY:  this.makeKey()}))]
        // } else {
        //   this.dsDatCoc = [...ds.map(x => ({ ...x, _CHECK: false, _KEY:  this.makeKey()}))]
        // }
      } finally {
        this.loading(false)
      }

      // let thongTinHopDong = null

      // this.ma_tb = this.maThueBao

      // try {
      //   this.loading(true)
      //   thongTinHopDong = await this.layHopDongTheoMaTb({
      //     ma_tb: this.maThueBao,
      //     dichvuvt_id: this.dichVuId
      //   })
      // } finally {
      //   this.loading(false)
      // }

      // if (thongTinHopDong) {
      //   if (thongTinHopDong.MA_GD !== undefined) {
      //     this.maGiaoDich = thongTinHopDong.MA_GD
      //     await this.onEnterInputMaGiaoDich()
      //   }
      // } else {
      //   await this.hienThiDanhBa()
      // }
      // if (this.ma_tb) {
      //   let f = this.dsDatCoc.find(x => x.MA_TB === this.ma_tb)
      //   f._CHECK = true
      //   let row = {rowData: f}
      //   // console.log(this.dgvDatCoc.DataSource, this.ma_tb)
      //   this.dsTBDatCocRecordClick(row)
      // }
    },

    async HienThiDanhSachDatCoc () {
      this.dsHdtb = []
      if (this.dsDatCoc !== null && this.dsDatCoc.length <= 0) {
        return
      }
      let ds = []
      for (const item of this.dsDatCoc) {
        if (!!item.HDKH_ID) {
          ds.push({
            THUEBAO_DC_ID: item.THUEBAO_DC_ID,
            MA_TB: item.MA_TB,
            TEN_TB: item.TEN_TB,
            LOAIHINH_TB: item.LOAIHINH_TB,
            SOTHANG_DC: item.SOTHANG_DC,
            THANG_BD: item.THANG_BD !== null ? moment(item.THANG_BD, 'YYYYMM').format('MM/YYYY') : null,
            THANG_KT: item.THANG_KT !== null ? moment(item.THANG_KT, 'YYYYMM').format('MM/YYYY') : null,
            NGAY_BD_DC: item.NGAY_BD_DC !== null ? item.NGAY_BD_DC : null,
            NGAY_KT_DC: item.NGAY_KT_DC !== null ? item.NGAY_KT_DC : null,
            CUOC_DC: item.CUOC_DC,
            TIEN_TD: item.TIEN_TD,
            NGUOI_CN: item.NGUOI_CN,
            TEN_GOI_DDV: item.TEN_GOI_DDV,
            TTHD_ID: item.TTHD_ID,
            HDTB_ID: item.HDTB_ID
          })
        }   
      }     
      this.dsHdtb = [...ds]
    },
    async LAY_DS_THUEBAO_DATCOC_THEOTB (ma_tb) {
      try {
          let postData = {
              "opt" : 1,
              "ma_tb" : ma_tb,
              "loai_hd" : 32,
              "kieuld_id": 551
          }
          let data = {
              p_data: JSON.stringify(postData)
          }
          let rps = await this.$root.context.post(
              '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_ds_thuebao_datcoc', data
          )

          console.log(rps)

          if (rps.data && rps.data.length > 0) {
              return this.toUpperCases(rps.data)
          } else { 
              return []
          }
          
      } catch (error) {
          console.log(error)
          return []
      }
    },
    async onEnterInputMaGiaoDich () {
      if (!this.maGiaoDich) {
        return
      }

      let thongTinHopDong = null

      try {
        this.loading(true)
        // thongTinHopDong = await this.layHopDongTheoMaGd(this.maGiaoDich)
        thongTinHopDong = await this.LAY_DS_THUEBAO_DATCOC_THEOGD(this.maGiaoDich)
      } finally {
        this.loading(false)
      }

      console.log('🚀 ~ file: index.vue:1337 ~ hienThiHopDong ~ thongTinHopDong:', thongTinHopDong)
      if (!thongTinHopDong) {
        this.dsHdtb = []
        this.setButton(0)
        return
      }

      await this.hienThiHopDong(thongTinHopDong)
      let row = {rowData: this.dsDatCoc[0]}
      // console.log(this.dsDatCoc, this.ma_tb)
      this.dsTBDatCocRecordClick(row)
    },
    async hienThiDatCoc (thueBaoId) {
      this.loading(true)
      try {
        // await this.layDsDatCocTheoTbId({
        //   thuebao_id: thueBaoId,
        //   thang_ht: this.yyyymm(new Date())
        // })
        let postData = {
            p_data: JSON.stringify({"thuebao_id" : thueBaoId})
        }
        let ds_datcoc = await this.LAY_DS_DATCOC_THEO_MATB(postData)

        // console.log(ds_datcoc)
        this.dsDatCoc = [...ds_datcoc.map(x => ({ ...x, _CHECK: false, _KEY:  this.makeKey()}))]
      } finally {
        this.loading(false)
      }
    },
    async hienThiDanhBa () {
      let date = new Date()

      if (this.tsMacDinhThoaiDcLuiThang) {
        date.setDate(1)
        date.setMonth(date.getMonth() - 1)
      }

      this.thangThoaiTra = date
      console.log('🚀 ~ file: index.vue:1261 ~ hienThiDanhBa ~ this.thangThoaiTra:', this.thangThoaiTra)

      if (!this.maThueBao || !this.dichVuId) {
        return
      }

      let thongTinDanhBa = null

      try {
        this.loading(true)
        thongTinDanhBa = await this.layDanhBaTheoMaTb({
          ma_tb: this.maThueBao,
          dichvuvt_id: this.dichVuId
        })
      } finally {
        this.loading(false)
      }

      if (!thongTinDanhBa) {
        let dichVu = this.dsDichVu.find(dichVu => dichVu.DICHVUVT_ID == this.dichVuId)
        this.$root.toastError(`Không tìm thấy thông tin về mã thuê bao ${this.maThueBao} đối với dịch vụ ${dichVu.TEN_DVVT}`)
        return
      }

      if (this.btnNhapMoi.enabled) {
        this.$root.toastError('Thuê bao trong danh bạ. Hãy ấn Nhập mới trước khi tạo hợp đồng !')
        this.clear()
        return
      }

      this.thueBaoId = thongTinDanhBa.THUEBAO_ID
      this.maKhachHang = thongTinDanhBa.MA_KH
      this.khachHangId = thongTinDanhBa.KHACHHANG_ID
      this.tenKhachHang = thongTinDanhBa.TEN_KH
      this.tenKhachHangHienThi = thongTinDanhBa.TEN_TB
      this.diaChiKh = thongTinDanhBa.DIACHI_KH
      this.maThueBao = thongTinDanhBa.MA_TB
      this.tenTb = thongTinDanhBa.TEN_TB
      this.diaChiLd = thongTinDanhBa.DIACHI_LD
      this.loaiTbId = thongTinDanhBa.LOAITB_ID
      this.donViId = thongTinDanhBa.DONVIQL_ID
      this.doiTuongId = thongTinDanhBa.DOITUONG_ID
      this.khLonId = thongTinDanhBa.KHLON_ID

      if (!thongTinDanhBa.NGAYLAP_HD) {
        this.ngayLhd = new Date()
      } else {
        this.ngayLhd = new Date(thongTinDanhBa.NGAYLAP_HD)
      }

      // try {
      //   this.loading(true)
      //   await this.layDsTraTruoc({
      //     dichvuvt_id: this.dichVuId,
      //     thuebao_id: this.thueBaoId,
      //     loaitb_id: this.loaiTbId,
      //     doituong_id: this.doiTuongId,
      //     nhom_datcoc_id: this.nhomDatCocId
      //   })
      // } finally {
      //   this.loading(false)
      // }

      // if (this.dsTraTruoc.length > 0) {
      //   this.traTruocId = this.dsTraTruoc[0].KHUYENMAI_ID
      // }

      // if (this.dichVuId == 4/* băng rộng cố định ADSL */ && !!thongTinDanhBa.TOCDO_ID) {
      //   let tocDo = await this.layThongTinTocDo(thongTinDanhBa.TOCDO_ID)
      //   this.thuongHieu = !tocDo ? null : tocDo.THUONGHIEU
      // } else {
      //   this.thuongHieu = null
      // }

      await this.hienThiDatCoc(this.thueBaoId)
    },
    async hienThiHopDong (thongTinHopDong) {

      this.hdkhId = thongTinHopDong[0].HDKH_ID
      this.maGiaoDich = thongTinHopDong[0].MA_GD
      this.maKhachHang = thongTinHopDong[0].MA_KH
      this.khachHangId = !thongTinHopDong[0].KHACHHANG_ID ? null : thongTinHopDong[0].KHACHHANG_ID
      this.khLonId = thongTinHopDong[0].KHLON_ID
      this.tenKhachHang = thongTinHopDong[0].TEN_KH
      this.tenKhachHangHienThi = thongTinHopDong[0].TEN_KH
      this.diaChiKh = thongTinHopDong[0].DIACHI_KH
      // this.ghiChu = thongTinHopDong[0].GHICHU

      if (thongTinHopDong[0].NGAY_YC) {
        this.ngayYeuCau = new Date(thongTinHopDong[0].NGAY_YC)
      }

      if (thongTinHopDong[0].CTV_ID) {
        this.chkCtv = true
        this.ctvId = thongTinHopDong[0].CTV_ID
        this.tenCtv = await this.layTenNhanVienTheoId(this.ctvId)
      } else {
        this.chkCtv = false
        this.tenCtv = null
      }

      if (thongTinHopDong[0].NHANVIENGT_ID) {
        this.chkNguoiGt = true
        this.nguoiGtId = thongTinHopDong[0].NHANVIENGT_ID
        this.tenNguoiGt = await this.layTenNhanVienTheoId(this.nguoiGtId)
      } else {
        this.chkNguoiGt = false
        this.tenNguoiGt = null
      }

      this.setButton(3)
      if (thongTinHopDong) {
        let ds = thongTinHopDong.filter(x => x.THUEBAO_THOAITRA_ID === null)
        this.dsDatCoc = [...ds.map(x => ({ ...x, _CHECK: true, _KEY:  this.makeKey()}))]
        this.dsHdtb = [...thongTinHopDong]
        this.dsDatCoc_bk = [...this.dsDatCoc]
      }
      // await this.hienThiDanhSachHdtb()
    },
    async hienThiDanhSachHdtb () {
      try {
        this.loading(true)
        await this.layDsHdtbTheoHdkh(this.hdkhId)
      } finally {
        this.loading(false)
      }

      if (this.dsHdtb.length == 0) {
        this.setButton(1)
      }
    },
    acceptSearchAccount (item) {
      this.$refs['popupSearchAccount'].hide()

      if (!!item && !!item.ma_tb) {
        this.maThueBao = item.ma_tb
        this.onEnterInputSoMay()
      }
    },
    onClickBtnTraCuuDanhBa () {
      this.$refs['popupSearchAccount'].show()
    },
    acceptSearchContract (item) {
      this.maGiaoDich = item.ma_gd
      this.onEnterInputMaGiaoDich()
    },
    onClickBtnLayMaGiaoDich () {
      this.$refs.popupSearchContract.showModal()
    },
    clear () {
      this.maGiaoDich = null
      this.ngayYeuCau = new Date()
      this.maKhachHang = null
      this.maThueBao = null
      this.thuongHieu = null
      this.tenGoiDDV = null
      this.tenKhachHang = null
      this.tenKhachHangHienThi = null
      this.diaChiKh = null
      this.tienThoaiTra = 0
      this.ghiChu = null
      this.thangMG = new Date()
      this.soThangMg = null
      this.tyLeTb = 0
      this.tyLeSd = 0
      this.tienTb = 0
      this.tienSd = 0
      this.dsHdtb = []
      this.dsDatCoc = []
      this.dsDatCoc_bk = []
      this.dsTraTruoc = []
      this.traTruocId = null
      this.dsChiTietKhuyenMai = []
      this.chiTietKhuyenMaiId = null
      this.dtpThangBD.value = new Date()
      this.soThang = null
      this.cuocDC = null
      this.tienTd = null
      this.ctvId = null
      this.tenCtv = null
      this.chkCtv = false
      this.nguoiGtId = null
      this.tenNguoiGt = null
      this.chkNguoiGt = false
      this.thangMgEnabled = true
      this.soThangMgEnabled = true
      this.tyLeTbEnabled = true
      this.tyLeSdEnabled = true
      this.tienTbEnabled = true
      this.tienSdEnabled = true
      this.dtpThangBD.enabled = true
      this.soThangEnabled = true
      this.cuocDcEnabled = true
      this.tienTdEnabled = true

      this.activeEventThangThoaiTra = false
      this.thangThoaiTra = new Date()
      setTimeout(() => { this.activeEventThangThoaiTra = true }, 300)

      if (!!this.dsDichVu && this.dsDichVu.length > 0) {
        if (this.dichVuId == this.dsDichVu[0].DICHVUVT_ID) {
          this.loaiTbId = this.dsLoaiHinh[0].LOAITB_ID
        } else {
          this.dichVuId = this.dsDichVu[0].DICHVUVT_ID
        }
      }

      if (!!this.dsNhomDatCoc && this.dsNhomDatCoc.length > 0) {
        if (this.nhomDatCocId != this.dsNhomDatCoc[0].NHOM_DATCOC_ID) {
          this.nhomDatCocId = this.dsNhomDatCoc[0].NHOM_DATCOC_ID
        }
      }

      if (this.tsMacDinhThoaiDcLuiThang) {
        let thangThoaiTraTmp = new Date(this.thangThoaiTra)
        thangThoaiTraTmp.setMonth(thangThoaiTraTmp.getMonth() - 1)
        this.thangThoaiTra = thangThoaiTraTmp
      }
      this.THUEBAO = {}
    },
    setButton (kieu) {
      this.btnNhapMoi.enabled = false
      this.btnGhiLai.enabled = false
      this.btnXoa.enabled = false
      this.btnHuyBo.enabled = false

      if (kieu == -1) { // Bat dau
        this.btnGhiLai.enabled = false
        this.btnHuyBo.enabled = true
      }

      if (kieu == 0) { // Bat dau
        this.$refs['input-txt-somay'].focus()
        this.btnNhapMoi.enabled = true
        // this.clear()
      }

      if (kieu == 1) { // Them moi
        this.$refs['input-txt-somay'].focus()
        this.btnGhiLai.enabled = true
        this.btnHuyBo.enabled = true
        // this.clear()
      }

      if (kieu == 2) { // Huy
        this.btnNhapMoi.enabled = true
        this.btnXoa.enabled = true
        // this.clear()
      }

      if (kieu == 3) { // Edit
        this.btnNhapMoi.enabled = true
        this.btnXoa.enabled = true
        this.btnGhiLai.enabled = true
        this.btnHuyBo.enabled = true
      }
    },
    onSelectTocDo (data) {
      this.tocDoId = !data ? null : data.TOCDO_ID
    },
    async layTenNhanVienTheoId (nhanVienId) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/lay_ten_nv_theo_id?nhanvien_id=${nhanVienId}`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data.TEN_NV
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async layDsHdtbTheoHdkh (hdkhId) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/lay_ds_hdtb_theo_hdkh?hdkh_id=${hdkhId}`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          let rootKey = this.ddmmyyyyhhmiss(new Date())
          response.data.forEach((item, index) => item.KEY = `${rootKey}${index + 1}`)
          this.dsHdtb = response.data
          return
        }

        throw {response: { data: response}}
      } catch (err) {
        this.dsHdtb = []
      }
    },
    async layDsThamSoMacDinh () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/lay_ds_thamso_md`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.dsThamSoMacDinh = response.data
          return
        }

        throw {response: { data: response}}
      } catch (err) {
        this.dsThamSoMacDinh = []
      }
    },
    async layDsDatCocTheoTbId (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/lay_ds_datcoc_theo_tbid`, data)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          let rootKey = this.ddmmyyyyhhmiss(new Date())
          response.data.forEach((item, index) => {
            item.KEY = `${rootKey}${index + 1}`
            item.CUOC_DC_HT = this.numberFormat(item.CUOC_DC)
            item.TIEN_TD_HT = this.numberFormat(item.TIEN_TD)
            item.TIEN_TB_HT = this.numberFormat(item.TIEN_TB)
            item.TIEN_SD_HT = this.numberFormat(item.TIEN_SD)
            item.TIEN_THOAI_HT = this.numberFormat(item.TIEN_THOAI)
          })
          this.dsDatCoc = response.data
          return
        }

        throw {response: { data: response}}
      } catch (err) {
        this.dsDatCoc = []
      }
    },
    async LAY_DS_DATCOC_THEO_MATB(data) {
      try {

          let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/lay_ds_datcoc_theo_matb`, data)

          if (response.error_code === 'BSS-00000000' && response.data != null) {
            return response.data
          }

          throw { response: { data: response } }
      } catch (err) {
          return null
      }
    },
    async layDanhBaTheoMaTb (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/lay_danhba_theo_matb`, data)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async layTonCuoiKy (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/lay_ton_ck`, data)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async layTienThoaiDatCoc (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/lay_tienthoai_datcoc`, data)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async layTienThoaiDatCoc_v4 (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_tienthoai_datcoc_v4`, data)
        if (response.data != null && response.data.length > 0) {
          return response.data[0].tienthoai
        }
        throw {response: { data: response}}
      } catch (err) {
        return 0
      }
    },
    async tinhSoThang (thangBd, thangKt) {
      try {
        let data = {
          thang_bd: thangBd,
          thang_kt: thangKt
        }
        let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/tinh_sothang`, data)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data.SOTHANG
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async layHopDongTheoMaTb (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/lay_hopdong_theo_ma_tb`, data)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async layTrangThaiHd (tthdId) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/lay_trangthai_hd?tthd_id=${tthdId}`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async sinhMaGiaoDich () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/sinh_ma_gd`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data.MA_GD
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async LAY_DS_THUEBAO_DATCOC_THEOGD (ma_gd) {
      try {
        let postData = {
            "opt" : 0,
            "ma_gd" : ma_gd,
            "loai_hd": 32,
            "kieuld_id": 551
          }
        let data = {
          p_data: JSON.stringify(postData)
        }
        let rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_ds_thuebao_datcoc', data
        )

        console.log(rps)

        if (rps.data && rps.data.length > 0) {
          return this.toUpperCases(rps.data)
        } else { 
          return []
        }
        
      } catch (error) {
        console.log(error)
        return []
      }
    },
    async layHopDongTheoMaGd (maGiaoDich) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/lay_hopdong_theo_ma_gd`, {ma_gd: maGiaoDich})

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async layThongTinTocDo (tocDoId) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/lay_tt_tocdo_internet?tocdo_id=${tocDoId}`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async layTocDoCuaTbao (thueBaoId) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/lay_tocdo_cua_tbao?thuebao_id=${thueBaoId}`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data.TOCDO_ID
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async layDsChiTietKhuyenMai (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/lay_ds_chitiet_km`, data)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.dsChiTietKhuyenMai = response.data
          return
        }

        throw {response: { data: response}}
      } catch (err) {
        this.dsChiTietKhuyenMai = []
        this.chiTietKhuyenMaiId = null
        this.ct_khuyenmai.id = null
        this.ct_khuyenmai.text = null
        this.soThang = 0
      }
    },
    async layDsChiTietKhuyenMaiV2 (data) {
      try {
        let rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_chitiet_km_datcoc_v4', data
        )
        return this.toUpperCases(rps.data)
        
      } catch (err) {
        return []
      }
    },
    async layDsTraTruoc (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/lay_ds_khuyenmai`, data)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.dsTraTruoc = response.data
          return
        }

        throw {response: { data: response}}
      } catch (err) {
        this.dsTraTruoc = []
      }
    },
    async layDsNhomDatCoc () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/lay_ds_nhom_datcoc`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.dsNhomDatCoc = response.data
          this.nhomDatCocId = response.data[0].NHOM_DATCOC_ID
          return
        }

        throw {response: { data: response}}
      } catch (err) {
        this.dsNhomDatCoc = []
      }
    },
    async layDsLoaiHinh (dichVuId) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/lay_ds_loaihinh_tb?dichvu_vt_id=${dichVuId}`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.dsLoaiHinh = response.data
          return
        }

        throw {response: { data: response}}
      } catch (err) {
        this.dsLoaiHinh = []
      }
    },
    async layDsDichVu () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/lay_ds_dichvu_vt`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.dsDichVu = response.data
          return
        }

        throw {response: { data: response}}
      } catch (err) {
        this.dsDichVu = []
      }
    },
    async layDsKieuLd () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/lay_ds_kieu_ld`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.dsKieuLd = response.data
          return
        }

        throw {response: { data: response}}
      } catch (err) {
        this.dsKieuLd = []
      }
    },
    async themHopDong (postData) {
      try {
        let rs = await this.$root.context.post(`/web-hopdong/cap-nhat-thue-bao-dat-coc/themmoi-hopdong-thaydoidatcoc`, { dsData : JSON.stringify(postData)})

        // if (response.error_code === 'BSS-00000000' && response.data != null) {
        //   return response.data
        // }

        // throw {response: { data: response}}
        console.log(rs)
        if (rs.data === null) {
          this.$root.$toast.error('Đã phát sinh lỗi khi tạo hợp đồng.')
          return false
        }
        if (rs.data.length > 0) {
          if (rs.data[0].code === 1) {
            this.maGiaoDich = rs.data[0].ma_gd
            this.$toast.success(rs.data[0].msg)
            return true
          } else {
            this.$toast.error(rs.data[0].msg)
            return false
          }
          
        } else { return false }
      } catch (err) {
        console.log('🚀 ~ file: index.vue:1435 ~ ThemMoi_HopDong_V1 ~ error:', err)
        return false
      }
    },
    async capNhatHopDong (data) {
      try {
        let rs = await this.$root.context.post(`/web-hopdong/cap-nhat-thue-bao-dat-coc/capnhat-hopdong-thaydoidatcoc`, { dsData : JSON.stringify(postData)})

        // if (response.error_code === 'BSS-00000000' && response.data != null) {
        //   return response.data
        // }

        // throw {response: { data: response}}
        console.log(rs)
        if (rs.data === null) {
          this.$root.$toast.error('Đã phát sinh lỗi khi tạo hợp đồng.')
          return false
        }
        if (rs.data.length > 0) {
          if (rs.data[0].code === 1) {
            this.maGiaoDich = rs.data[0].ma_gd
            this.$toast.success(rs.data[0].msg)
            return true
          } else {
            this.$toast.error(rs.data[0].msg)
            return false
          }
          
        } else { return false }
      } catch (err) {
        console.log('🚀 ~ file: index.vue:1435 ~ ThemMoi_HopDong_V1 ~ error:', err)
        return false
      }
    },
    async xoaHopDong (hdkhId) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/xoa_hdkh`, {hdkh_id: hdkhId})

        if (response.error_code !== 'BSS-00000000') {
          throw {response: { data: response}}
        }
      } catch (err) {
        let responseData = err.response.data
        let message = []

        if (responseData.message) {
          message.push(responseData.message)
        }

        if (responseData.message_detail) {
          message.push(responseData.message_detail)
        }

        if (responseData.data) {
          let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
          message.push([...new Set(detailMessage)].join('\n'))
        }

        throw message.join('\n')
      }
    },
    async hoanThienThayDoiDatCoc (hdkhId) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/hoanthien_thaydoi_datcoc`, {hdkh_id: hdkhId})

        if (response.error_code !== 'BSS-00000000') {
          throw {response: { data: response}}
        }
      } catch (err) {
        let responseData = err.response.data
        let message = []

        if (responseData.message) {
          message.push(responseData.message)
        }

        if (responseData.message_detail) {
          message.push(responseData.message_detail)
        }

        if (responseData.data) {
          let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
          message.push([...new Set(detailMessage)].join('\n'))
        }

        throw message.join('\n')
      }
    },
    async tsbtnThemTB_Click () {
      try {
        // this.$root.showLoading(true)
        // await this.ThemHDThueBao()
        // this.$root.showLoading(false)
      } catch (error) {
        this.$root.showLoading(false)
      }
    },
    async tsbtnXoaTB_Click () {
      try {
        // this.$root.showLoading(true)
        // let selected = this.$refs.dgvDatCoc.getSelectedRecords()[0]
        // if (selected.tthd_id > 1) {
        //   var trangthai_hd = await this.trangthai_hd(selected.tthd_id)
        //   this.$root.$toast.warning('Hợp đồng ' + trangthai_hd + ' bạn không thể xóa!')
        //   this.$root.showLoading(false)
        //   return false
        // }
        // try {
        //   this.$root.showLoading(false)
        //   let confirm = null
        //   try {
        //     confirm = await this.$confirm(`Bạn thật sự muốn xóa thuê bao ${selected.ma_tb} không ?`, 'Thông báo', {
        //       confirmButtonText: 'Có',
        //       cancelButtonText: 'Không',
        //       type: 'warning'
        //     })
        //   } catch (error) {
        //     return false
        //   }
        //   this.$root.showLoading(true)
        //   let f = await this.XOA_HDTB(selected.hdtb_id)

        //   if (f) {
        //     this.txtMaGD_KeyPress()
        //   } else {
        //     this.$root.$toast.error('Có lỗi khi xóa thuê bao ' + selected.ma_tb)  
        //   }
          
        // } catch (error) {
        //   this.$root.$toast.error('Có lỗi khi xóa thông tin: ' + error)
        //   this.$root.showLoading(false)
        //   return false
        // }
        // this.$root.showLoading(false)
      } catch (error) {
        this.$root.showLoading(false)
      }
    },
    async HT_DS_KhuyenMai_Combobox (P_KHUYENMAI_ID) {
      try {
        if (!this.THUEBAO.THUEBAO_ID) return

        this.tocDoId = this.THUEBAO.TOCDO_ID
        this.mucCuocId = this.THUEBAO.MUCCUOC_ID
        let km = []
        this.dsTraTruoc = []
        this.traTruocId = null
        if (this.THUEBAO.GOI_DDV_ID !== null && this.nhomDatCocId === 4) {
          let ds_cunggoi_dadv = this.dsDatCoc.filter(x => x.GOI_DDV_ID === this.THUEBAO.GOI_DDV_ID)
          let postData = []
          for( let i = 0; i < ds_cunggoi_dadv.length; i++) {
            postData.push({
              loaitb_id: ds_cunggoi_dadv[i].LOAITB_ID,
              doituong_id: ds_cunggoi_dadv[i].DOITUONG_ID,
              tocdo_id: ds_cunggoi_dadv[i].TOCDO_ID,
              muccuoc_id: ds_cunggoi_dadv[i].MUCCUOC_ID,
              nhomdc_id: this.nhomDatCocId,
              thuebao_id: ds_cunggoi_dadv[i].THUEBAO_ID,
              goiddv_id: ds_cunggoi_dadv[i].GOI_DDV_ID
            })
          }
          km = await this.LAY_DS_KHUYENMAI_DATCOT_HIENHUU_V2(postData)
        } else {
          let postData = [{
            loaitb_id: this.THUEBAO.LOAITB_ID,
            doituong_id: this.THUEBAO.DOITUONG_ID,
            tocdo_id: this.tocDoId,
            muccuoc_id: this.mucCuocId,
            nhomdc_id: this.nhomDatCocId,
            thuebao_id: this.THUEBAO.THUEBAO_ID,
            goiddv_id: null
          }]
          km = await this.LAY_DS_KHUYENMAI_DATCOT_HIENHUU_V2(postData)
        }

        
        if (km.length > 0) {
          this.dsTraTruoc = [...km]        
          console.log('HT_DS_KhuyenMai_Combobox', this.dsTraTruoc, km)
          this.traTruocId = P_KHUYENMAI_ID
          // setTimeout(() => {
          //   this.traTruocId = P_KHUYENMAI_ID
          // }, 1000)
        } else {
          this.dsTraTruoc = []
          this.traTruocId = null
        }
      } catch (error) {
        console.log('HT_DS_KhuyenMai_Combobox', error)
      }
    },
    async LAY_DS_KHUYENMAI_DATCOT_HIENHUU_V2 (postData) {
      try {
        let data = {
          p_data : JSON.stringify(postData)
        }
        let rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_ds_khuyenmai_datcot_hienhuu_v2', data
        )
        if (rps.data && rps.data.length > 0) {
          let rs = JSON.parse(JSON.stringify(rps.data))
          return this.toUpperCases(rs)
        } else { return [] }
      } catch (error) {
        console.log('LAY_DS_KHUYENMAI_DATCOT_HIENHUU', error)
        return []
      }
    },
    async LAY_DS_THUEBAO_DATCOC (ma_tb) {
      try {
        let postData = {
            "opt" : 1,
            "ma_tb" : ma_tb,
            "loai_hd" : 32
          }
        let data = {
          p_data: JSON.stringify(postData)
        }
        let rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_ds_thuebao_datcoc', data
        )

        console.log(rps)

        if (rps.data && rps.data.length > 0) {
          return this.toUpperCases(rps.data)
        } else { 
          return []
        }
        
      } catch (error) {
        console.log(error)
        return []
      }
    },
    checkBoxChange (args) {
      if (args.target.classList.contains('e-checkselectall')) { // click check/uncheck all
        this.checkAll(args.checked)
        this.refreshAggTB()
      }
    },
    dsTBDatCocDatabound (args) {
      this.refreshGrid()
    },
    async dsTBDatCocRecordClick (row) {
      console.log('🚀 ~ file: index.vue:4135 ~ dsTBDatCocRecordClick ~ row:', row.rowData)
      this.$root.showLoading(true)
      try {
        if (this.dsDatCoc.length === 0) return false
        if (row === undefined) return false
        if (row) {
          let f = this.dsDatCoc.find(x => x._KEY === row.rowData._KEY)
          this.THUEBAO = {...f}
          if (f.HDTB_ID !== undefined) {
            this.setButton(3)
            this.maGiaoDich = f.MA_GD
          } else this.setButton(1)

          await this.setThongTinThueBao(f)
          await this.setThongTinDatCoc(f)
          
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1737 ~ dsTBDatCocRecordClick ~ error', error)
      }
      this.$root.showLoading(false)
    },
    onChangeSoThangDC (e) {
      
      if(e.value !== null) {
        let ds = this.dsCTKM.filter(x => x.HUONG_DC === e.value)
        this.dsChiTietKhuyenMai = [...ds]
        if (ds.length > 0) {
          this.ct_khuyenmai.id = ds[0].CHITIETKM_ID
          this.ct_khuyenmai.text = ds[0].TEN_CTKM
          this.chiTietKhuyenMaiId = ds[0].CHITIETKM_ID  
        }
      } else {
        this.dsChiTietKhuyenMai = [...this.dsCTKM]
        this.ct_khuyenmai.id = null
        this.ct_khuyenmai.text = null
        this.chiTietKhuyenMaiId = null
      }      
    },
    getDateElements (date) {
      date = new Date(date)

      let day = date.getDate()
      day = day > 9 ? day : '0' + day

      let month = date.getMonth() + 1
      month = month > 9 ? month : '0' + month

      let hour = date.getHours()
      hour = hour > 9 ? hour : '0' + hour

      let min = date.getMinutes()
      min = min > 9 ? min : '0' + min

      let second = date.getSeconds()
      second = second > 9 ? second : '0' + second

      return {
        y: date.getFullYear(),
        M: month,
        d: day,
        h: hour,
        m: min,
        s: second
      }
    },
    yyyymm (date) {
      // if (!date) {
      //   return ''
      // }

      // date = this.getDateElements(date)
      // return `${date.y}${date.M}`
      console.log(date, moment(date).format('YYYYMM'))
      return moment(date).format('YYYYMM')
    },
    yyyymmdd (date) {
      // if (!date) {
      //   return ''
      // }

      // date = this.getDateElements(date)
      // return `${date.y}${date.M}${date.d}`
      return moment(date).format('YYYYMMDD')
    },
    ddmmyyyy (date) {
      // if (!date) {
      //   return ''
      // }

      // date = this.getDateElements(date)
      // return `${date.d}/${date.M}/${date.y}`
      return moment(date).format('DDMMYYYY')
    },
    ddmmyyyyhhmiss (date) {
      // if (!date) {
      //   return ''
      // }

      // date = this.getDateElements(date)
      // return `${date.d}${date.M}${date.y}${date.h}${date.m}${date.s}`
      return moment(date).format('DDMMYYYYHHmmss')
    },
    numberFormat (number) {
      if (!number) {
        return number
      }

      number = `${number}`

      let decimal = ''

      if (number.includes('.')) {
        number = number.split('.')[0]
        decimal = '.' + number.split('.')[1]
      }

      let group = []

      while (number.length > 3) {
        group.push(number.substr(number.length - 3))
        number = number.substr(0, number.length - 3)
      }

      if (number.length > 0) {
        group.push(number)
      }

      return group.reverse().join() + decimal
    },
    onChangeCustomDropDown (id) {
      const isHIdden = $(id).is(':hidden')
      if (isHIdden) {
        $(id).show()
      } else {
        $(id).hide()
      }
    },
    onChangeChiTietKhuyenMai (e) {
      if (e === null) return

      // this.dataSelected.CHITIETKM_ID = e.CHITIETKM_I
      this.ct_khuyenmai.id = e.CHITIETKM_ID
      this.ct_khuyenmai.text = e.TEN_CTKM
      // this.soThang = e.HUONG_DC
      this.chiTietKhuyenMaiId = e.CHITIETKM_ID
      $('#ctkhuyenmai-pd').hide()
    },
    async filtering_nhomdatcoc (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_NHOM', 'contains', e.text, true) : query
      e.updateData(this.dsNhomDatCoc, query)
    },
    async filtering_tratruoc (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_KM', 'contains', e.text, true) : query
      e.updateData(this.dsTraTruoc, query)
    },
    toUpperCases (ds) {
      if (ds === undefined || ds === null) return []
      if (ds.length === 0) return []
      let dt = ds.map(item => {
        let newItem = {}
        Object.keys(item).forEach(key => newItem[key.toUpperCase()] = item[key])
        return newItem
      })
      return dt
    },
    refreshGrid () {
        let obj = this.$refs.dgvDatCoc.ej2Instances
        
        if (obj.currentViewData.length > 0) {
            let data = obj.currentViewData.records
            // console.log(data)
            let selectedCurrentPage = []
            data.forEach((value, index) => {
            if (value._CHECK) {
                selectedCurrentPage.push(index)
            }
            })
            // console.log(selectedCurrentPage)
            obj.selectRows(selectedCurrentPage)        
        }
    },
    refreshAggTB () {
        this.$refs.dgvDatCoc.ej2Instances.aggregateModule.refresh()
    },
    checkAll (f) {
        let temp = [...this.dsDatCoc]
        if (f) { // true
            console.log(this.$refs.dgvDatCoc)
            let filter = this.$refs.dgvDatCoc.getFilteredRecords()
            if (filter !== undefined && filter.length > 0) {
            let checked = filter.map(x => x._KEY)
            for (let i = 0; i < temp.length; i++) {
                if (checked.includes(temp[i]._KEY)) {
                temp[i]._CHECK = true
                } else {
                temp[i]._CHECK = false
                }
            }        
            } else {
            for (let i = 0; i < temp.length; i++) temp[i]._CHECK = true
            }
        } else {
            for (let i = 0; i < temp.length; i++) temp[i]._CHECK = false
        }
        this.dsDatCoc = [...temp]
    },
    dsTBDatCocRowSelected (args) {
        // console.log('sel', this.dgvDatCoc.DataSource)
        if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header
            let f = this.dsDatCoc.find(x => x._KEY === args.data._KEY)
            let goi_ddv_id = f.GOI_DDV_ID
            if (!!goi_ddv_id) {
            let ds = [...this.dsDatCoc]
            // console.log(ds)
            for (let i = 0; i < ds.length; i++) {
                if(ds[i].GOI_DDV_ID === goi_ddv_id) ds[i]._CHECK = true
            }
            this.dsDatCoc = [...ds]
            } else {
            f._CHECK = true
            }
            // if (f !== undefined) f._CHECK = true
            this.refreshAggTB()

        }
    },
    dsTBDatCocRowDeselected (args) {
        console.log('desel', args)
        if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header & paging
            let f = this.dsDatCoc.find(x => x._KEY === args.data._KEY)
            // console.log(f, args.data._KEY)
            // let goi_ddv_id = f.GOI_DDV_ID
            // if (!!goi_ddv_id) {
            // let ds = [...this.dsDatCoc]
            // // console.log(ds)
            // for (let i = 0; i < ds.length; i++) {
            //     if(ds[i].GOI_DDV_ID === goi_ddv_id) ds[i]._CHECK = false
            // }
            // this.dsDatCoc = [...ds]
            // } else {
            // f._CHECK = false
            // }
            if (f !== undefined) {
              f._CHECK = false
              this.refreshAggTB()
            }            
        }
    },
    makeKey (length = 10) {
      let result = '';
      const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
      const charactersLength = characters.length;
      let counter = 0;
      while (counter < length) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
        counter += 1;
      }
      return result;
    },
    async acceptChonMaTBHandler (e) {
        let ds = await this.LAY_DS_THUEBAO_DATCOC_THEO_TBID(e.THUEBAO_ID)
        this.dsDatCoc = ds.map(x => ({ ...x, _CHECK: true, _KEY:  this.makeKey()}))
        this.dsDatCoc_bk = [...this.dsDatCoc]
        // this.HienThiTTHopDongKH(ds[0])
    },
    async LAY_DS_THUEBAO_DATCOC_THEO_TBID (tb_id) {
      try {
          let postData = {
              "opt" : 2,
              "thuebao_id" : tb_id,
              "loai_hd" : 32
          }
          let data = {
              p_data: JSON.stringify(postData)
          }
          let rps = await this.$root.context.post(
              '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_ds_thuebao_datcoc', data
          )


          if (rps.data && rps.data.length > 0) {
              return this.toUpperCases(rps.data)
          } else { 
              return []
          }
          
      } catch (error) {
          console.log(error)
          return []
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
        if (rs.data.length > 0) {
          var obj = rs.data[0]
          return obj[P_MAP]
        } else return null
      } catch (error) {
        return null
      }
    },
    tinhTongTienHD () {
      this.tongTienHD = (this.chkPhiTTN ? this.phiTTN  : 0) + this.tienHD + this.tienVATHD
    },
    async getThongTinPhiTTN() {
      try {
        let rs = await this.$root.context.get(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_thongtin_khoanmuctt/3421'
        )
        if (rs !== undefined && rs.error_code === 'BSS-00000000' && rs.data !== null) return this.toUpperCases(rs.data)
        else return []
      } catch (er) {
        return []
      }
    },
  },
  provide: {
      grid: [Group, Page, Sort, Filter, Aggregate, Resize]
  }
}
</script>
<style scoped>
    @media (min-width: 1200px) {
        #frm-thaydoi-datcoc-modal-email .modal-xl,
        #frm-thaydoi-datcoc-modal-lienhe .modal-xl {
            max-width: 90% !important;
        }
    }

    .vattu_box {
      position: relative;
    }

    .vattu_table {
      position: absolute;
      z-index: 1000;
      max-height: 360px;
      background: aliceblue;
      overflow-y: scroll;
      min-width: 700px;
    }
    .displayNone {
      display: none;
    }


    .frm-thaydoi-datcoc .gutter {
        background-color: transparent;
    }

    .frm-thaydoi-datcoc #boxLeft {
        width: 100% !important;
    }

    .frm-thaydoi-datcoc .disable-btn:hover {
        background-color: white !important;
    }

    .frm-thaydoi-datcoc .disable-btn a {
        color: #6c757d !important;
    }

    .frm-thaydoi-datcoc .disable-btn a:hover {
        cursor: default;
        color: #6c757d;
    }

    .frm-thaydoi-datcoc .e-control.e-numerictextbox.e-lib.e-input {
        text-align: right !important;
        padding-right: 5px !important;
    }

    .frm-thaydoi-datcoc.modal-body .page-content {
        position: inherit !important;
    }

    .popupComponentPayment .page-content {
        padding: 8px 16px 16px 16px !important;
        top: 90px !important;
        bottom: 40px !important;
    }

    .dialogObjHoSoKhachHang .page-content {
        top: 65px !important;
    }

    .dialogObjHoSoKhachHang .page-content .grid-stack-box #boxLeft {
        margin-right: 10px;
        width: calc(20% - 5px);
    }

    .dialogObjHoSoKhachHang .page-content .grid-stack-box #boxMid {
        margin-right: 10px;
        width: calc(40% - 10px);
    }

    .dialogObjHoSoKhachHang .page-content .grid-stack-box #boxRight {
        width: calc(40% - 5px);
    }
    .dis-input {
        display: block;
    }
    .numeric-input {
        text-align: right;
        line-height: 175%;
        color: red; 
        font-weight: bold;
        padding-right: 50px;
    }
</style>
