<template src="./index.template.html"></template>
<script>
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
    ThongTinLienHe
  },
  data () {
    return {
      strict: true,
      userComponentModule: null,
      txtMaGDTT: '',
      ob_id:  null,
      popupComponentEvts: {
        paymentcreated: this.paymentcreated
      },
      popupChonKhuyenMaiData: {},
      dsKieuLd: [],
      kieuLdId: null,
      ngayYeuCau: new Date(),
      dsDichVu: [],
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
      tienThoaiTra: 0,
      thongTinDanhBa: null,
      dsDatCoc: [],
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
      thangMG: new Date(),
      thangMgEnabled: true,
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
      ngayLhd: null,
      donViId: null,
      datCocDuocChon: null,
      tienThoai: 0,
      thuongHieu: null,
      activeEventThangThoaiTra: true,
      hdtbDuocChon: null,
      isShowPhiTTN: false,
      chkPhiTTN: false,
      khoanmucPhiTTN : {},
      phiTTNId: null,
      phiTTN: 0,
      tienHD: 0,
      tienVATHD: 0,
      tongTienHD: 0,
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
        enabled: true
      },
      btnCapNhatDB: {
        enabled: true
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
      checkMaxNgayBDDC: false
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
      return this.dsThamSoMacDinh.some(thamSo => {
        return thamSo.MA_TS == 'TINHCUOC_NGAY' && thamSo.TEN_TS == '1'
      })
    },
    minValNgayBDDC () {
      // let ss = moment(this.thangBD)
      // let select = ss.format('M')
      // let dnow = moment().format('M')
      // if(Number(select) > Number(dnow)){
      //   return DateTimeLib.toDateObjectWithFormat(ss.startOf('month'),'dd/MM/yyyy')
      // }
      // return DateTimeLib.toDateObjectWithFormat(ss.set('date',moment().format('D')))
      return this.thangBD
    },
    maxValNgayBDDC () {
      if (this.checkMaxNgayBDDC) {
        return DateTimeLib.toDateObjectWithFormat(moment(this.thangBD).endOf('month'), 'dd/MM/yyyy')
      }
      return new Date().getFullYear() + 10
    },
    minValNgayBDMG () {
      // let select = moment(this.thangMG).format('M')
      // let dnow = moment().format('M')
      // if(Number(select) > Number(dnow)){
      //   return DateTimeLib.toDateObjectWithFormat(moment(this.thangMG).startOf('month'),'dd/MM/yyyy')
      // }
      // return DateTimeLib.toDateObjectWithFormat(moment(this.thangMG).set('date',moment().format('D')))
      // return this.thangMG
      return DateTimeLib.toDateObjectWithFormat(moment(this.thangMG).startOf('month'), 'dd/MM/yyyy')
    },
    maxValNgayBDMG () {
      return DateTimeLib.toDateObjectWithFormat(moment(this.thangMG).endOf('month'), 'dd/MM/yyyy')
    },
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
      if (!this.dataKhuyenMaiSelect || this.dataKhuyenMaiSelect.CHITIETKM_ID === null) return
      try {
        var item = this.dataKhuyenMaiSelect
        let ngay = moment(val).add(parseInt(item.HUONG_DC), 'M')
        let ngayktdc = DateTimeLib.toDateObject(ngay.subtract(1, 'd'))
        this.dtpNgayKTDC.value = ngayktdc
        this.dtpThangKT.value = this.dtpNgayKTDC.value

        this.dtpNgayBD.value = val
        this.dtpNgayKT.value = this.dtpNgayKTDC.value
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
    thangMG (val) {
      if (val === null) {
        val = new Date()
        this.thangMG = val
      }
      this.dtpNgayBD.value = this.thangMG

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
    async  'dtpNgayThoai.value' (val) {
      // console.log('🚀 ~ file: index.vue:316 ~ this.thueBaoThoaiTraId:', this.thueBaoThoaiTraId)
      // if (this.thueBaoThoaiTraId === null) return
      console.log('🚀 ~ file: index.vue:266 ~ val:', val)
      let tienThoaiDuocTinh = await this.layTienThoaiDatCoc_v4({
        rkm_id: this.rkmId,
        thuebao_dc_id: this.thueBaoThoaiTraId,
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
        let data = {
          dichvuvt_id: this.dichVuId,
          thuebao_id: this.thueBaoId,
          loaitb_id: this.loaiTbId,
          doituong_id: this.doiTuongId,
          nhom_datcoc_id: this.nhomDatCocId
        }

        try {
          this.loading(true)
          await this.layDsTraTruoc(data)
        } finally {
          this.loading(false)
        }
      }

      await this.loadTraTruoc()
    },
    async thangThoaiTra (val) {
      if (!this.activeEventThangThoaiTra) {
        return
      }
      // if (this.thueBaoThoaiTraId === null) return
      this.dtpNgayThoai.value = val
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
      }
    },
    chiTietKhuyenMaiId (val) {
      if (val) {
        this.error.chiTietKhuyenMaiId = false
      }

      if (!val) {
        this.thangBdEnabled = true
        this.thangBD = new Date()
        this.soThangEnabled = true
        this.soThang = null
        this.thangMgEnabled = true
        this.thangMG = new Date()
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

      this.thangBdEnabled = false
      this.soThangEnabled = false
      this.cuocDcEnabled = false
      this.tienTdEnabled = false

      this.thangMgEnabled = false
      this.soThangMgEnabled = false
      this.tyLeTbEnabled = false
      this.tyLeSdEnabled = false
      this.tienTbEnabled = false
      this.tienSdEnabled = false

      let chiTietKm = this.dsChiTietKhuyenMai.find(chiTietKm => chiTietKm.CHITIETKM_ID == val)

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
          month.setMonth(month.getMonth() + chiTietKm.THANG_BDDC)
          this.thangBD = month

          this.dtpThangKT.value = DateTimeLib.toDateObject(moment(this.thangBD).add(Number(chiTietKm.HUONG_DC), 'M').subtract(1, 'M'))

          this.thangBdEnabled = false
          this.checkMaxNgayBDDC = true
          break
        case 3:
          month.setMonth(month.getMonth() + chiTietKm.THANG_BDDC)
          this.thangBD = month

          this.dtpThangKT.value = DateTimeLib.toDateObject(moment(this.thangBD).add(Number(chiTietKm.HUONG_DC), 'M').subtract(1, 'M'))

          this.thangBdEnabled = true
          this.checkMaxNgayBDDC = false
          break
        default:
          this.thangBdEnabled = true
          this.checkMaxNgayBDDC = false
          break
      }

      month = new Date()
      month.setDate(1)

      let thangBdTmp = new Date(this.thangBD)

      switch (chiTietKm.LOAI_BDKM) {
        case 1:
          month.setMonth(month.getMonth() + chiTietKm.THANG_BDKM)
          this.thangMG = month
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.thangMG).add(Number(chiTietKm.HUONG_MG), 'M').subtract(1, 'M'))
          break
        case 2:
          thangBdTmp.setMonth(thangBdTmp.getMonth() + chiTietKm.THANG_BDKM + chiTietKm.HUONG_DC - 1)
          this.thangMG = thangBdTmp
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.thangMG).add(Number(chiTietKm.HUONG_MG), 'M').subtract(1, 'M'))
          break
        case 4:
          thangBdTmp.setMonth(thangBdTmp.getMonth() + chiTietKm.THANG_BDKM)
          this.thangMG = thangBdTmp
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.thangMG).add(Number(chiTietKm.HUONG_KM) - 1, 'M'))
          break
        default:
          this.thangMgEnabled = true
          this.thangMG = this.thangBD
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.thangMG).add(Number(chiTietKm.HUONG_KM) - 1, 'M'))
          break
      }

      this.cuocDcEnabled = chiTietKm.KIEU_TRATRUOC == 1
    },
    thangBD (val) {
      if (val === null) {
        val = new Date()
        this.thangBD = val
      }
      this.dtpNgayBDDC.value = this.thangBD
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
          this.thangMG = thangBdTmp
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.thangMG).add(Number(chiTietKm.HUONG_KM) - 1, 'M'))
          break
        case 4:
          thangBdTmp.setMonth(thangBdTmp.getMonth() + chiTietKm.THANG_BDKM)
          this.thangMG = thangBdTmp
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.thangMG).add(Number(chiTietKm.HUONG_KM) - 1, 'M'))
          break
        case 1:
          this.thangMG = val
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.thangMG).add(Number(chiTietKm.HUONG_KM) - 1, 'M'))
          break
        default:
          this.thangMgEnabled = true
          this.thangMG = val
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.thangMG).add(Number(chiTietKm.HUONG_KM) - 1, 'M'))
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

      let tien = (Math.round(parseFloat(val) / 1.1))
      let vat = (parseFloat(val) - tien)
      this.tienHD = tien
      this.tienVATHD = vat
      this.tinhTongTienHD()
    },
    dsDatCoc (value) {
      if (!value || value.length <= 0) {
        this.onSelectRowDatCoc(null)
      }
      if (this.thueBaoThoaiTraId) {
        let index = value.findIndex(x => x.THUEBAO_DC_ID == this.thueBaoThoaiTraId)
        // if (index != -1) {
        // this.$nextTick(() => this.$refs.dsDatCoc.setCurrentSelectedRow(2))
        // }
        setTimeout(() => {
          this.$refs.dsDatCoc.setCurrentSelectedRow(index !== -1 ? index : 0)
        }, 200)
      }
    },
    tthdId (value) {
      this.btnThanhToan.enabled = parseInt(value) == 1
      this.btnCapNhatDB.enabled = parseInt(value) == 2
    }
  },
  async created () {
    try {
      this.loading(true)

      if (this.$route.query.ma_tb !== undefined && this.$route.query.ma_tb !== null) {
        this.maThueBao = this.$route.query.ma_tb
      }
      if (this.$route.query.dichvuvt_id !== undefined && this.$route.query.dichvuvt_id !== null) {
        this.dichVuId = Number(this.$route.query.dichvuvt_id)
      }
      if (this.$route.query.ob_id !== undefined && this.$route.query.ob_id !== null) {
        this.ob_id = this.$route.query.ob_id
      }

      let rsPhiTTN = await this.getThongTinPhiTTN()
      if (rsPhiTTN.length > 0) {
        this.khoanmucPhiTTN.MA_KMTT = rsPhiTTN[0].MA_KMT
        this.khoanmucPhiTTN.TEN_MKMTT = rsPhiTTN[0].TEN_MKMTT
        this.khoanmucPhiTTN.TYLE_VAT = rsPhiTTN[0].TYLE_VAT
        this.isShowPhiTTN = true
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

    if (this.dichVuId === null && !!this.dsDichVu && this.dsDichVu.length > 0) {
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

    // if (this.$route.query !== undefined && this.$route.query !== null && Object.keys(this.$route.query).length > 0) {
        // this.maThueBao = this.$route.query.ma_Tb || ""
        // let loaiTB_id = this.$route.query.loaitb_id || ""
        // const response = await this.$root.context.get('/web-cabman/ban-do-mang-cap/danh-muc', { loai: 'LOAITB_ID', option: loaiTB_id })
        // if(response.error_code === 'BSS-00000000' && response.data.length > 0){
        //     this.dichVuId = response.data[0].DICHVUVT_ID
        //     this.loaiTbId = loaiTB_id
        //     await this.onEnterInputSoMay()
        // }
    // }

    if (this.maThueBao !== null) {
      this.onEnterInputSoMay()
    }

  },
  methods: {
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
      this.resetValidation()

      let errorMessage = []

      if (this.chkCtv && !this.tenCtv && !this.ctvId) {
        errorMessage.push('Bạn chưa chọn cộng tác viên !')
        this.error.ctvId = true
      }

      if (this.chkNguoiGt && !this.tenNguoiGt && !this.nguoiGtId) {
        errorMessage.push('Bạn chưa chọn người giới thiệu hợp đồng !')
        this.error.nguoiGtId = true
      }

      if (!this.datCocDuocChon) {
        errorMessage.push('Không có thông tin đặt cọc cũ !')
      }

      if (this.tienThoaiTra == null || !this.tienThoaiTra.toString()) {
        errorMessage.push('Tiền thoái trả không được để trống !')
        this.error.tienThoaiTra = true
      }

      let thanght = parseInt(this.yyyymm(new Date()))
      let phanVungId = this.$root.token.getPhanVungID()

      if (!(this.nhomDatCocIdCu == 2 && phanVungId == 26/* HPG */)) {
        if (this.thangKtCu < thanght && this.thangBdCu > 0 && parseFloat(this.tienThoaiTra) <= 0) {
          errorMessage.push('Đặt cọc đã hết hạn không thay đổi được !')
        }
      }

      if (this.ttdcId == 1) {
        errorMessage.push('Đặt cọc đã thoái trả không thay đổi được !')
      }

      if (this.allowThoaiTra == 0) {
        errorMessage.push('Bạn không được thực hiện thoái trả đặt cọc đối với nhóm đặt cọc này. !')
      }

      if (this.tsHuyDatCocTheoThang && this.ngayYcCu != thanght) {
        errorMessage.push('Đặt cọc đã hết hạn không thay đổi được!. Chỉ được thay đổi trong tháng đặt cọc.')
      }

      if (!!this.tienThoaiTra && this.tienThoaiTra > this.tienThoai && this.tsKhongVuotQuaTienThoai) {
        errorMessage.push('Số tiền thoái trả không được nhập lớn hơn số tiền thoái.')
        this.error.tienThoaiTra = true
      }

      if (!this.maThueBao) {
        errorMessage.push('Bạn chưa nhập số máy/account !')
        this.error.maThueBao = true
      }

      let thoaiTra = parseInt(this.yyyymm(this.thangThoaiTra))

      let ngayCn = await this.lay_ngay_cn()
      let date = new Date(ngayCn)
      date.setDate(1)
      let thangHienTai = parseInt(this.yyyymm(date))
      date.setMonth(date.getMonth() - 1)
      let thangTruoc = parseInt(this.yyyymm(date))

      if (thoaiTra < thangTruoc) {
        errorMessage.push('Tháng thoái trả không được nhỏ hơn tháng trước !')
        this.error.thangThoaiTra = true
      }

      if (thoaiTra < thangHienTai) {
        errorMessage.push('Tháng thoái trả không được nhỏ hơn tháng hiện tại !')
        this.error.thangThoaiTra = true
      }

      // if (this.dtpNgayThoai.value < ngayCn) {
      //   errorMessage.push('Ngày thoái không nhỏ hơn ngày hiện tại !')
      //   this.error.thangThoaiTra = true
      // }

      // if (this.dtpNgayThoai.value < moment(this.thangThoaiTra).startOf('M') || this.dtpNgayThoai.value > moment(this.thangThoaiTra).endOf('M')) {
      //   errorMessage.push('Ngày thoái phải nằm trong tháng thoái !')
      //   this.error.thangThoaiTra = true
      // }

      // if (!(this.nhomDatCocIdCu == 2 && phanVungId == 26/* HPG */)) {
      //   if (thoaiTra > this.thangKtCu && this.thangBdCu > 0) {
      //     errorMessage.push('Tháng thoái trả không được lớn hơn tháng kết thúc đặt cọc cũ !')
      //     this.error.thangThoaiTra = true
      //   }
      // }

      if (!this.traTruocId) {
        errorMessage.push('Bạn hãy chọn khuyến mại !')
        this.error.traTruocId = true
      }

      if (!this.chiTietKhuyenMaiId) {
        errorMessage.push('Bạn hãy chọn trả trước !')
        this.error.chiTietKhuyenMaiId = true
      }

      // let thangBdMoi = parseInt(this.yyyymm(this.thangBD))

      // if (thoaiTra >= thangBdMoi) {
      //   errorMessage.push('Tháng thoái trả phải nằm ngoài khoảng thời gian đặt cọc mới !')
      //   this.error.thangThoaiTra = true
      // }

      if (parseFloat(this.cuocDC) < parseFloat(this.tienThoaiTra)) {
        errorMessage.push('Tiền thoái trả > Tiền đặt cọc mới. Bạn không được thực hiện !')
        this.error.tienThoaiTra = true
      }

      if (!isThemMoi && this.tthdId != 1/* mới */) {
        errorMessage.push('Trạng thái hợp đồng khác Mới. Bạn không được cập nhật !')
      }
      // if(this.tsTinhCuocNgay){
      //   if (this.dtpNgayBDDC.value < this.thangBD || this.dtpNgayBDDC.value > moment(this.thangBD).endOf('M')) {
      //   errorMessage.push('Ngày bắt đầu đặt cọc phải nằm trong tháng bắt đầu đặt cọc!')
      //   }

      //   if (this.dtpNgayBD.value < this.thangMG || this.dtpNgayBD.value > moment(this.thangMG).endOf('M')) {
      //     errorMessage.push('Ngày bắt đầu miễn giảm phải nằm trong tháng bắt đầu miễn giảm!')
      //   }
      // }
      if (errorMessage.length > 0) {
        throw errorMessage.join('\n')
      }
    },
    async lay_ngay_cn () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCocHuy/lay_ngay_cn`)
        return !response.data ? null : response.data.ngay_cn
      } catch (err) {
        return null
      }
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

      if (isThemMoi) {
        let maGdDuocSinh = null

        try {
          maGdDuocSinh = await this.sinhMaGiaoDich()
        } catch (err) {
          this.$root.toastError(err)
          return
        }

        this.maGiaoDich = maGdDuocSinh
      }

      let data = {
        ma_gd_moi: this.maGiaoDich,
        ma_kh: this.maKhachHang,
        khachhang_id: this.khachHangId,
        khlon_id: this.khlon_id,
        ten_kh: this.tenKhachHang,
        diachi_kh: this.diaChiKh,
        ngaylap_hd: this.ddmmyyyy(this.ngayLhd),
        ngay_yc: this.ddmmyyyy(this.ngayYeuCau),
        ghichu: this.ghiChu,
        ctv_id: this.ctvId,
        nhanviengt_id: this.nguoiGtId,
        thuebao_id: this.thueBaoId,
        ma_tb: this.maThueBao,
        ten_tb: this.tenTb,
        diachi_ld: this.diaChiLd,
        diachi_tb: this.diaChiLd,
        doituong_id: this.doiTuongId,
        loaitb_id: this.loaiTbId,
        dichvuvt_id: this.dichVuId,
        donviql_id: this.donViId,
        thuebao_thoaitra_id: this.thueBaoThoaiTraId,
        rkm_id: this.rkmId,
        nhom_datcoc_id_cu: this.nhomDatCocIdCu,
        thang_bd_cu: this.yyyymm(this.thangBdCu),
        thang_kt_cu: this.yyyymm(this.thangKtCu),
        nhom_datcoc_id_moi: this.nhomDatCocId,
        thang_thoaitra: this.yyyymm(this.thangThoaiTra),
        ngay_thoai: this.ddmmyyyy(this.dtpNgayThoai.value),
        ngay_dk: this.ddmmyyyy(this.ngayYeuCau),
        chitietkm_id: this.chiTietKhuyenMaiId,
        cuoc_dc: this.cuocDC,
        tien_td: this.tienTd,
        ob_id:  this.ob_id
      }

      data.thang_bd_moi = this.yyyymm(this.thangBD)
      let dateThangBd = new Date(this.thangBD)
      dateThangBd.setMonth(dateThangBd.getMonth() + parseInt(this.soThang) - 1)
      data.thang_kt_moi = this.yyyymm(dateThangBd)

      if (!this.soThangMg || parseInt(this.soThangMg) == 0) {
        data.thang_bd_mg = 0
        data.thang_kt_mg = 0
      } else {
        data.thang_bd_mg = this.yyyymm(this.thangMG)
        let dateThangBdMg = new Date(this.thangMG)
        dateThangBdMg.setMonth(dateThangBdMg.getMonth() + parseInt(this.soThangMg) - 1)
        data.thang_kt_mg = this.yyyymm(dateThangBdMg)
      }

      // Cuoc ngay
      data.ngay_bd_mg = DateTimeLib.toDateDisplay(this.dtpNgayBD.value)
      data.ngay_kt_mg = DateTimeLib.toDateDisplay(this.dtpNgayKT.value)
      data.ngay_bd_cu = this.ngayBdCu
      data.ngay_kt_cu = this.ngayKtCu
      data.ngay_bd_moi = DateTimeLib.toDateDisplay(this.dtpNgayBDDC.value)
      data.ngay_kt_moi = DateTimeLib.toDateDisplay(this.dtpNgayKTDC.value)
      // Cuoc ngay

      data.tien_thoaitra = Math.round(parseFloat(this.tienThoaiTra) / 1.1)
      data.vat_thoaitra = parseFloat(this.tienThoaiTra) - data.tien_thoaitra

      if (data.tien_thoaitra > 0) {
        data.tien_thoaitra = data.tien_thoaitra * -1
        data.vat_thoaitra = data.vat_thoaitra * -1
      }

      data.tien_datcoc = Math.round(parseFloat(this.cuocDC) / 1.1)
      data.vat_datcoc = parseFloat(this.cuocDC) - data.tien_datcoc

      if (this.chkPhiTTN && this.khoanmucPhiTTN.TYLE_VAT !== undefined) {
        let tienTTN = (Math.round(parseFloat(this.phiTTN) / (1 + this.khoanmucPhiTTN.TYLE_VAT/100)))
        let vatTTN = (parseFloat(this.phiTTN) - tienTTN)

        data.phi_ttn = tienTTN
        data.vat_ttn = vatTTN
      }

      try {
        this.loading(true)

        if (isThemMoi) {
          let result = await this.themHopDong(data)

          if (result.ERR) {
            throw result.ERR
          }

          this.hdkhId = result.HDKH_ID
          this.$root.toastSuccess('Thêm mới dữ liệu thành công!')
        } else {
          data.hdkh_id = this.hdkhId
          data.ob_id = this.ob_id
          let result = await this.capNhatHopDong(data)

          if (result.ERR) {
            throw result.ERR
          }

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
      if (!this.loaiTbId || !this.traTruocId || !this.nhomDatCocId) {
        return
      }

      let data = {
        khuyenmai_id: this.traTruocId,
        loaitb_id: this.loaiTbId,
        nhom_datcoc_id: this.nhomDatCocId,
        thuebao_id: this.thueBaoId,
        dichvuvt_id: this.dichVuId
      }

      try {
        this.loading(true)
        await this.layDsChiTietKhuyenMai(data)
      } finally {
        this.loading(false)
      }

      if (this.dsChiTietKhuyenMai.length > 0) {
        let hasSelected = false

        if (this.chiTietKhuyenMaiId) {
          hasSelected = this.dsChiTietKhuyenMai.some(item => item.CHITIETKM_ID == this.chiTietKhuyenMaiId)
        }

        if (!hasSelected) {
          this.chiTietKhuyenMaiId = this.dsChiTietKhuyenMai[0].CHITIETKM_ID
        }
      }
    },
    onClickBtnHuy () {
      if (this.btnHuyBo.enabled) {
        this.setButton(0)
      }
    },
    onClickBtnNhapMoi () {
      if (this.btnNhapMoi.enabled) {
        this.setButton(1)
      }
    },
    async onSelectRowDatCoc (data) {
      console.log('🚀 ~ file: index.vue:1027 ~ onSelectRowDatCoc ~ data:', data)
      if (!data) {
        this.datCocDuocChon = null
        this.thangBdCu = 0
        this.thangKtCu = 0
        this.nhomDatCocIdCu = 0
        this.ttdcId = 0
        this.cuocDcCu = 0
        this.tienTdCu = 0
        this.ngayBdCu = ''
        this.ngayKtCu = ''
        // this.tinhTienThoai()
        return
      }

      if (!!this.datCocDuocChon && this.datCocDuocChon.KEY == data.KEY) {
        return
      }

      this.datCocDuocChon = data
      this.thangBdCu = data.THANG_BD
      this.thangKtCu = data.THANG_KT
      this.ngayBdCu = data.NGAY_BD
      this.ngayKtCu = data.NGAY_KT
      this.ngayYcCu = data.NGAY_YC
      this.nhomDatCocIdCu = data.NHOM_DATCOC_ID
      this.cuocDcCu = data.CUOC_DC
      this.tienTdCu = data.TIEN_TD
      this.thueBaoThoaiTraId = data.THUEBAO_DC_ID
      this.allowThoaiTra = data.THOAITRA
      this.rkmId = data.RKM_ID
      this.ttdcId = !data.TTDC_ID ? 0 : data.TTDC_ID
      console.log('🚀 ~ file: index.vue:1135 ~ onSelectRowDatCoc ~ this.tsTinhTienThoaiMoi:', this.tsTinhTienThoaiMoi)
      if (this.tsTinhTienThoaiMoi) {
        let tienThoaiDuocTinh = await this.layTienThoaiDatCoc_v4({
          rkm_id: this.rkmId,
          thuebao_dc_id: this.thueBaoThoaiTraId,
          thang_thoai: this.yyyymm(this.thangThoaiTra),
          ngay_thoai: this.yyyymmdd(this.dtpNgayThoai.value)
        })
        this.tienThoai = tienThoaiDuocTinh
        this.tienThoaiTra = tienThoaiDuocTinh
      } else {
        this.tinhTienThoai()
      }
    },
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
      if (!data) {
        this.hdtbDuocChon = null
        return
      }

      if (!!this.hdtbDuocChon && this.hdtbDuocChon.KEY == data.KEY) {
        return
      }

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
        }
      }

      if (data.NGAY_THOAI) {
        this.dtpNgayThoai.value = await DateTimeLib.toDateObjectWithFormat(moment(data.NGAY_THOAI, 'DD/MM/YYYY'), 'dd/MM/yyyy')
      } else this.dtpNgayThoai.value = ''
      this.thangBD = await DateTimeLib.toDateObjectWithFormat(moment(data.THANG_BD, 'YYYYMM'), 'M')
      if (data.NGAY_BD) {
        this.dtpNgayBD.value = await DateTimeLib.toDateObjectWithFormat(moment(data.NGAY_BD, 'DD/MM/YYYY'), 'dd/MM/yyyy')
      } else this.dtpNgayBD.value = ''
      if (data.NGAY_BDDC) {
        this.dtpNgayBDDC.value = await DateTimeLib.toDateObjectWithFormat(moment(data.NGAY_BDDC, 'DD/MM/YYYY'), 'dd/MM/yyyy')
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

      let thongTinHopDong = null

      try {
        this.loading(true)
        thongTinHopDong = await this.layHopDongTheoMaTb({
          ma_tb: this.maThueBao,
          dichvuvt_id: this.dichVuId
        })
      } finally {
        this.loading(false)
      }

      if (thongTinHopDong) {
        await this.hienThiHopDong(thongTinHopDong)
      } else {
        await this.hienThiDanhBa()
      }
    },
    async onEnterInputMaGiaoDich () {
      if (!this.maGiaoDich) {
        return
      }

      let thongTinHopDong = null

      try {
        this.loading(true)
        thongTinHopDong = await this.layHopDongTheoMaGd(this.maGiaoDich)
      } finally {
        this.loading(false)
      }

      await this.hienThiHopDong(thongTinHopDong)
    },
    async hienThiDatCoc (thueBaoId) {
      this.loading(true)
      try {
        await this.layDsDatCocTheoTbId({
          thuebao_id: thueBaoId,
          thang_ht: this.yyyymm(new Date())
        })
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

      if (this.dsTraTruoc.length > 0) {
        this.traTruocId = this.dsTraTruoc[0].KHUYENMAI_ID
      }

      if (this.dichVuId == 4/* băng rộng cố định ADSL */ && !!thongTinDanhBa.TOCDO_ID) {
        let tocDo = await this.layThongTinTocDo(thongTinDanhBa.TOCDO_ID)
        this.thuongHieu = !tocDo ? null : tocDo.THUONGHIEU
      } else {
        this.thuongHieu = null
      }

      await this.hienThiDatCoc(this.thueBaoId)
    },
    async hienThiHopDong (thongTinHopDong) {
      console.log('🚀 ~ file: index.vue:1337 ~ hienThiHopDong ~ thongTinHopDong:', thongTinHopDong)
      if (!thongTinHopDong) {
        this.dsHdtb = []
        this.setButton(0)
        return
      }

      this.hdkhId = thongTinHopDong.HDKH_ID
      this.maGiaoDich = thongTinHopDong.MA_GD
      this.maKhachHang = thongTinHopDong.MA_KH
      this.khachHangId = !thongTinHopDong.KHACHHANG_ID ? null : thongTinHopDong.KHACHHANG_ID
      this.khLonId = thongTinHopDong.KHLON_ID
      this.tenKhachHang = thongTinHopDong.TEN_KH
      this.tenKhachHangHienThi = thongTinHopDong.TEN_KH
      this.diaChiKh = thongTinHopDong.DIACHI_KH
      this.ghiChu = thongTinHopDong.GHICHU

      if (thongTinHopDong.NGAY_YC) {
        this.ngayYeuCau = new Date(thongTinHopDong.NGAY_YC)
      }

      if (thongTinHopDong.CTV_ID) {
        this.chkCtv = true
        this.ctvId = thongTinHopDong.CTV_ID
        this.tenCtv = await this.layTenNhanVienTheoId(this.ctvId)
      } else {
        this.chkCtv = false
        this.tenCtv = null
      }

      if (thongTinHopDong.NHANVIENGT_ID) {
        this.chkNguoiGt = true
        this.nguoiGtId = thongTinHopDong.NHANVIENGT_ID
        this.tenNguoiGt = await this.layTenNhanVienTheoId(this.nguoiGtId)
      } else {
        this.chkNguoiGt = false
        this.tenNguoiGt = null
      }

      this.setButton(3)
      await this.hienThiDanhSachHdtb()

      let f_ttn = this.dsHdtb.find(x => x.TTN_ID !== null)
      if (f_ttn !== undefined) {
        this.chkPhiTTN = true
        this.phiTTN = f_ttn.TIEN_TTN
        this.phiTTNId = f_ttn.TTN_ID
      }
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
      this.dsTraTruoc = []
      this.traTruocId = null
      this.dsChiTietKhuyenMai = []
      this.chiTietKhuyenMaiId = null
      this.thangBD = new Date()
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
      this.thangBdEnabled = true
      this.soThangEnabled = true
      this.cuocDcEnabled = true
      this.tienTdEnabled = true
      this.phiTTNId = null
      this.phiTTN = 0
      this.tienHD = 0
      this.tienVATHD = 0
      this.tongTienHD = 0


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
    },
    setButton (kieu) {
      this.btnNhapMoi.enabled = false
      this.btnGhiLai.enabled = false
      this.btnXoa.enabled = false
      this.btnHuyBo.enabled = false

      if (kieu == -1) { // Bat dau
        this.btnGhiLai.enabled = true
        this.btnHuyBo.enabled = true
      }

      if (kieu == 0) { // Bat dau
        this.$refs['input-txt-somay'].focus()
        this.btnNhapMoi.enabled = true
        this.clear()
      }

      if (kieu == 1) { // Them moi
        this.$refs['input-txt-somay'].focus()
        this.btnGhiLai.enabled = true
        this.btnHuyBo.enabled = true
        this.clear()
      }

      if (kieu == 2) { // Huy
        this.btnNhapMoi.enabled = true
        this.btnXoa.enabled = true
        this.clear()
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
    async themHopDong (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/themmoi_hopdong_new`, data)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
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
    async capNhatHopDong (data) {
      try {
        // let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/capnhat_hopdong_new`, data)
        let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/capnhat_hopdong`, data)
        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }
        if (response.error_code !== 'BSS-00000000') {
          throw {response: { data: response}}
        }
      } catch (err) {
        console.log('🚀 ~ file: index.vue:1857 ~ capNhatHopDong ~ err:', err)
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
      if (!date) {
        return ''
      }

      date = this.getDateElements(date)
      return `${date.y}${date.M}`
    },
    yyyymmdd (date) {
      if (!date) {
        return ''
      }

      date = this.getDateElements(date)
      return `${date.y}${date.M}${date.d}`
    },
    ddmmyyyy (date) {
      if (!date) {
        return ''
      }

      date = this.getDateElements(date)
      return `${date.d}/${date.M}/${date.y}`
    },
    ddmmyyyyhhmiss (date) {
      if (!date) {
        return ''
      }

      date = this.getDateElements(date)
      return `${date.d}${date.M}${date.y}${date.h}${date.m}${date.s}`
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
  }
}
</script>
<style>
    @media (min-width: 1200px) {
        #frm-thaydoi-datcoc-modal-email .modal-xl,
        #frm-thaydoi-datcoc-modal-lienhe .modal-xl {
            max-width: 90% !important;
        }
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
