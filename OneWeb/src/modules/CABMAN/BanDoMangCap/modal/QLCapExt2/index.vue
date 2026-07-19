<template src="./index.template.html"></template>
<script>
import ModalCsht from '../Csht/Modal'
import ModalChonDiaChi from '../../../BanDoTuyenCot/ChonDiaChi/Modal'
// import ModalIMS from '../IMS/Modal'
import frmChonTBIChiTiet from '../frmChonTBIChiTiet'
import ModalDUAN from '../DuAn/Modal'
import ModalNVQLCap from '../../../gan-nhanvien-quanly-cap/Modal'
import ModalChonToaDo from '../ChonToaDo'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required, minValue, maxValue, maxLength } from 'vuelidate/lib/validators'
import ModalChuiCap from './../../../QLCap/QLCap/ChuiCapQuaOngCong/Modal'
import ModalTreoCap from './../../../Modal/TreoCapLenCot/Modal'
import DateTimeLib from "@/utils/DateTimeLib.js";
import ModalNhapSuyHao from '../NhapSuyHao'

export default {
  name: 'ModalQLKetCuoi',
  props: { modalID: null },
  components: {
    frmChonTBIChiTiet, ModalChonToaDo, BssErrorMarker, BssRequiredMarker, ModalCsht, ModalChonDiaChi, ModalNVQLCap, ModalChuiCap, ModalTreoCap, ModalDUAN, ModalNhapSuyHao
  },
  created: async function () {
  },
  validations: {
    dataSelected:
    {
      kyhieu: {
        required,
        maxLength: maxLength(600)
      },
      kichco_id: {
        required
      },
      ten_cap: {
        required,
        maxLength: maxLength(600)
      },
      lopmc_id: {
        required
      },
      nhomlc_id: {
        required
      },
      dienct_id: {
        required
      },
      ttsd_id: {
        required
      },
      loaicap_id: {
        required
      },
      kieucap_id: {
        required
      },
      kieudc_id: {
        required
      },
      noisx_id: {
        required
      },
      sodoiday: {
        required,
        minValue: minValue(1),
        maxValue: maxValue(99999)
      },
      chieudai: {
        required,
        minValue: minValue(0),
        maxValue: maxValue(99999)
      },
      soi_bd: {
        minValue: minValue(1),
        maxValue: maxValue(99999)
      }
    }
  },
  data: function () {
    return {
      duan_ims_id: null,
      MaDA: null,
      readOnly: false,
      idPopupNVQLCap: 'idPopupNVQLCap',
      action: null,
      modalNVQLCapShow: false,
      doiTuongNVQLCap: {
        DOITUONG_ID: -1,
        LOAI_DT: 'CAP'
      },
      dataSelected: {
        // Định danh cap
        cap_id: null,
        ma_cap: null,
        kyhieu: null,
        ten_cap: null,
        ghichu: null,
        // Đặc tính
        kichco_id: null,
        kc_cot: 0,
        trokhang: 0,
        suyhao: 0,
        sodoiday: null,
        soi_bd: 1,
        dienct_id: null,
        chieudai: null,
        chieudai_tk: 0,
        noisx_id: null,
        ttsd_id: null,
        // Ngày
        ngay_sd: new Date(),
        ngay_ld: new Date(),
        // Phân loại
        loaicap_id: null,
        lopmc_id: null,
        nhomlc_id: null,
        kieudc_id: null,
        kieucap_id: null,
        capgoc_kyhieu: null,
        capgoc_id: null,
        // Vị trí
        tram_nguon: null,
        donvi_id: null,
        donvi_td_id: null,
        tram_dich: null,

        ketcuoi_n_id: null,
        ketcuoi_d_id: null,
        ketcuoi_n_kyhieu: null,
        ketcuoi_d_kyhieu: null,
        // Tài sản
        duan_id: null,
        taisan_id: null,
        // Quản lý
        toql_id: null,
        chuquan_id: null,
        ds_nhanvien_ql: null,
        // CSHT
        ims_id: null,
        ma_csht: null,
        ma_goithau_ims: null,
        ma_hopdong_ims: null,
        ma_ttvt: null,
        nguoi_cn: null,
        ngay_cn: null
      },
      modalData: {
        KICHCO_CAP: [],
        DM_DIENCT: [],
        NOI_SX: [],
        TRANGTHAI_SD: [],
        LOAI_CAP: [],
        LOP_MC: [],
        NHOM_LC: [],
        KIEU_DC: [],
        KIEU_CAP: [],
        DUAN: [],
        TAISAN: [],
        CHUQUAN: [],
        TOQL: []
      },
      category: {
        phanloai: 0,
        dinhdanh: 0,
        dactinh: 0,
        lienket: 0,
        kiemke: 0,
        vitri: 0,
        chuquan: 0,
        bando: 0,
        macsht: 0,
        ngaythang: 0
      },
      waterMark: 'Chọn ngày',
      dateVal: new Date(),
      dateFormat: 'dd/MM/yyyy',
      resolvePromise: null,
      rejectPromise: null,
      ten_duan: null,
      dataQLCap: {
        cap_id: null,
        capgoc: Object,
        donvi: Object, // Trạm thiết bị
        donvi_td_id: null, // Đơn vị truyền dẫn
        ketcuoi_n_id: null,
        ketcuoi_d_id: null,
        kieucap_id: null, // Cáp thuê bao
        toql: Object // Đơn vị quản lý
      }
    }
  },
  watch: {
    'dataSelected.duan_id' (val) {
      this.$root.context.get(
        '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'TAISAN', option: this.dataSelected.duan_id === null ? -1 : this.dataSelected.duan_id}
      ).then((rs) => {
        this.modalData.TAISAN = rs.data
      })
    },
    async 'dataSelected.nhomlc_id' (val) {
      if (this.action === 'taocap') {
        let kyhieu_cap_md = await this.sinh_ma_cap_macdinh()
        this.dataSelected.kyhieu = kyhieu_cap_md
        this.dataSelected.ten_cap = kyhieu_cap_md
      }
    },
    async 'dataSelected.toql_id' (val) {
      if (val === null) return
      try {
        let rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'DV_CHA', option: val}
        )
        if (rs && rs.data && rs.data.length > 0) {
          this.dataSelected.ma_ttvt = rs.data[0].MA_DV_CHA
        } else {
          this.dataSelected.ma_ttvt = null
        }
        console.log('🚀 ~ file: index.vue:104 ~ this.dataSelected.ma_ttvt:', this.dataSelected.ma_ttvt)
      } catch (error) {
        console.log('🚀 ~ file: index.vue:112 ~ error:', error)
      }
    }
  },
  computed: {
    dataSend () {
      return {
        CAP_ID: this.dataSelected.cap_id }
    }
  },
  methods: {
    async IMS_ID_Click () {
      if (this.dataSelected.duan_id === null || this.dataSelected.duan_id < 1) {
        this.$root.$toast.error('Bạn chưa chọn dự án!')
        return
      }
      let rs = await this.$root.context.get('/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'DUAN_CT', option: this.dataSelected.duan_id})
      console.log('🚀 ~ file: index.vue:214 ~ IMS_ID_Click ~ rs:', rs)
      if (rs.data.length > 0) {
        this.MaDA = rs.data[0].MA_CT2
      } else {
        this.MaDA = null
      }
      this.$bvModal.show('frmChonTBIChiTiet')
    },
    //hàm kiểm tra số thập phân và làm tròn 2 chữ số
    limitDecimalPlaces(event) {
    let value = event.target.value;
    if ((value.indexOf('.') !== -1) && (value.split('.')[1].length > 2)) {
      event.target.value = parseFloat(value).toFixed(2);
      this.dataSelected.suyhao = event.target.value;
      }
    },
    onChangeKyHieu () {
      if (this.action === 'taocap') {
        this.dataSelected.ten_cap = this.dataSelected.kyhieu
      }
    },
    async refresh () {
      if (this.dataSelected.cap_id == null) return
      this.$root.showLoading(true)
      try {
        await this.loadThongTinCap(this.dataSelected.cap_id)
      } catch (error) {
        console.log('🚀 ~ file: index.vue:340 ~ refresh ~ error:', error)
      }
      this.$root.showLoading(false)
    },
    showNVQLCap () {
      if (this.dataSelected.cap_id !== null) {
        this.doiTuongNVQLCap.DOITUONG_ID = this.dataSelected.cap_id
        this.doiTuongNVQLCap.LOAI_DT = 'CAP'
        this.$bvModal.show(this.idPopupNVQLCap)
      } else {
        this.$alert('Cáp chưa tồn tại, chưa thể gán nhân viên quản lý. Hãy nhấn nút <b>Ghi lại</b> để lưu thông tin cáp mới vào dữ liệu trước khi thực hiện chức năng gán nhân viên quản lý.', 'Nhân viên quản lý cáp', {
          confirmButtonText: 'Đóng',
          dangerouslyUseHTMLString: true
        })
      }
    },
    closeModalNVQLCap: function () {
      this.loadThongTinCap(this.dataSelected.cap_id)
      this.modalNVQLCapShow = !this.modalNVQLCapShow
    },
    onCHUI_CAP () { alert('onCHUI_CAP') },
    onTREO_CAP () { alert('onTREO_CAP') },
    show (data) {
      return new Promise((resolve, reject) => {
        this.data = data
        this.$refs.modalCap.show();
        //this.$bvModal.show(this.modalID)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    showQLCap (cap_id) {
      return new Promise((resolve, reject) => {
        this.dataQLCap.cap_id = cap_id
        this.action = 'readOnly'
        this.readOnly = true
        this.$refs.modalCap.show();
        //this.$bvModal.show(this.modalID)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    taoCap (dataQLCap) {
      return new Promise((resolve, reject) => {
        this.dataQLCap = dataQLCap
        this.action = 'taocap'
        this.$refs.modalCap.show();
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    suaCap (dataQLCap) {
      return new Promise((resolve, reject) => {
        this.dataQLCap = dataQLCap
        this.action = 'suacap'
        this.$refs.modalCap.show();
        //this.$bvModal.show(this.modalID)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    closeModal: function () {
      this.resolvePromise({ok: false, data: null})
      this.$bvModal.hide(this.modalID)
    },
    setDefault () {
      this.dataSelected.dienct_id = this.modalData.DM_DIENCT.length > 0 ? 1 : null
      this.dataSelected.noisx_id = this.modalData.NOI_SX.length > 0 ? 2 : null
      this.dataSelected.ttsd_id = this.modalData.TRANGTHAI_SD.length > 0 ? 1 : null
      this.dataSelected.loaicap_id = this.modalData.LOAI_CAP.length > 0 ? 1 : null
      this.dataSelected.nhomlc_id = this.modalData.NHOM_LC.length > 0 ? 1 : null
      this.dataSelected.kieudc_id = this.modalData.KIEU_DC.length > 0 ? 1 : null
      this.dataSelected.chuquan_id = this.modalData.CHUQUAN.length > 0 ? 1 : null
      this.dataSelected.soi_bd = 1
      this.dataSelected.kichco_id = 11
    },
    hiddenModal () {
      this.dataSelected = {
        // Định danh
        cap_id: null,
        ma_cap: null,
        kyhieu: null,
        ten_cap: null,
        ghichu: null,
        // Đặc tính
        kichco_id: null,
        kc_cot: 0,
        trokhang: 0,
        suyhao: 0,
        sodoiday: null,
        soi_bd: 1,
        dienct_id: null,
        chieudai: null,
        chieudai_tk: 0,
        noisx_id: null,
        ttsd_id: null,
        // Ngày
        ngay_sd: new Date(),
        ngay_ld: new Date(),
        // Phân loại
        loaicap_id: null,
        lopmc_id: null,
        nhomlc_id: null,
        kieudc_id: null,
        kieucap_id: null,
        capgoc_kyhieu: null,
        capgoc_id: null,
        // Vị trí
        tram_nguon: null,
        donvi_id: null,
        donvi_td_id: null,
        tram_dich: null,

        ketcuoi_n_id: null,
        ketcuoi_d_id: null,
        ketcuoi_n_kyhieu: null,
        ketcuoi_d_kyhieu: null,
        // Tài sản
        duan_id: null,
        taisan_id: null,
        // Quản lý
        toql_id: null,
        chuquan_id: null,
        ds_nhanvien_ql: null,
        // CSHT
        ims_id: null,
        ma_csht: null,
        ma_ttvt: null,
        ma_goithau_ims: null,
        ma_hopdong_ims: null
      }
      this.ten_duan = null
      this.$v.$reset()
    },
    async sinh_ma_cap_macdinh () {
      try {
        let data = {
          VCAPGOC_ID: this.dataSelected.capgoc_id,
          VDONVI_ID: this.dataSelected.donvi_id,
          VKETCUOI_N_ID: this.dataSelected.ketcuoi_n_id,
          VKETCUOI_D_ID: this.dataSelected.ketcuoi_d_id,
          VNHOMLC_ID: this.dataSelected.nhomlc_id,
          VLOPMC_ID: this.dataSelected.lopmc_id,
          VKIEUCAP_ID: this.dataSelected.kieucap_id
        }
        let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/sinh_kyhieu_cap_macdinh_v2', data)
        if (rs.data && rs.data.length > 0) {
          return rs.data[0].KYHIEU_CAP_MACDINH
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:345 ~ sinh_ma_cap_macdinh ~ error:', error)
        return null
      }
    },
    shownModal: async function () {
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/sp_ds_danhmuc_ql_cache', {P_LOAI: ''})
        this.modalData.DM_DIENCT = JSON.parse(rs.data[0].JSON_DM_DIENCT).DM_DIENCT
        this.modalData.KICHCO_CAP = JSON.parse(rs.data[0].JSON_KICHCO_CAP).KICHCO_CAP
        this.modalData.NOI_SX = JSON.parse(rs.data[0].JSON_NOI_SX).NOI_SX
        this.modalData.TRANGTHAI_SD = JSON.parse(rs.data[0].JSON_TRANGTHAI_SD).TRANGTHAI_SD
        this.modalData.LOAI_CAP = JSON.parse(rs.data[0].JSON_LOAI_CAP).LOAI_CAP
        this.modalData.LOP_MC = JSON.parse(rs.data[0].JSON_LOP_MC).LOP_MC
        this.modalData.NHOM_LC = JSON.parse(rs.data[0].JSON_NHOM_LC).NHOM_LC
        this.modalData.KIEU_DC = JSON.parse(rs.data[0].JSON_KIEU_DC).KIEU_DC
        this.modalData.KIEU_CAP = JSON.parse(rs.data[0].JSON_KIEU_CAP).KIEU_CAP
        this.modalData.CHUQUAN = JSON.parse(rs.data[0].JSON_CHUQUAN).CHUQUAN
        this.modalData.TOQL = JSON.parse(rs.data[0].JSON_TOQL).TOQL
        if (this.action === 'taocap') {
          this.setDefault()
          if (this.dataQLCap.donvi && this.dataQLCap.donvi.DONVI_ID !== null) {
            if (this.dataQLCap.donvi_td_id !== null) {
              this.dataSelected.donvi_td_id = this.dataQLCap.donvi_td_id
              this.dataSelected.lopmc_id = 4
            } else {
              this.dataSelected.lopmc_id = 1
            }
          } else {
            if (this.dataQLCap.donvi_td_id !== null) {
              this.dataSelected.lopmc_id = 3
            } else {
              this.dataSelected.lopmc_id = 2
            }
          }
          this.dataSelected.capgoc_kyhieu = this.dataQLCap.capgoc && this.dataQLCap.capgoc.KYHIEU
          this.dataSelected.capgoc_id = this.dataQLCap.capgoc && this.dataQLCap.capgoc.CAP_ID

          this.dataSelected.tram_nguon = this.dataQLCap.donvi && this.dataQLCap.donvi.TEN_DV
          this.dataSelected.donvi_id = this.dataQLCap.donvi && this.dataQLCap.donvi.DONVI_ID

          this.dataSelected.toql_id = this.dataQLCap.toql && this.dataQLCap.toql.DONVI_ID
          this.dataSelected.ketcuoi_n_id = this.dataQLCap.ketcuoi_n_id
          this.dataSelected.ketcuoi_d_id = this.dataQLCap.ketcuoi_d_id
          await this.getKyHieuketCuoi(this.dataSelected.ketcuoi_n_id, this.dataSelected.ketcuoi_d_id)
          this.dataSelected.kieucap_id = this.dataQLCap.kieucap_id
          let kyhieu_cap_md = await this.sinh_ma_cap_macdinh()
          this.dataSelected.kyhieu = kyhieu_cap_md
          this.dataSelected.ten_cap = kyhieu_cap_md
          this.$root.showLoading(false)
        } if (this.action === 'readOnly') {
          this.dataSelected.cap_id = this.dataQLCap.cap_id
          if (this.dataSelected.cap_id !== undefined && this.dataSelected.cap_id !== null) await this.loadThongTinCap(this.dataSelected.cap_id)
        } else {
          this.dataSelected.cap_id = this.dataQLCap.cap_id
          if (this.dataSelected.cap_id !== undefined && this.dataSelected.cap_id !== null) await this.loadThongTinCap(this.dataSelected.cap_id)
        }
        this.$refs.kyhieu.focus()
      } catch (er) {
        console.log('🚀 ~ file: index.vue:448 ~ er:', er)
        this.$root.showLoading(false)
      } finally {
        this.$root.showLoading(false)
      }
    },
    getDUAN: function (args) {
      console.log('🚀 ~ file: index.vue:464 ~ args:', args)
      this.dataSelected.duan_id = args.duan_id
      this.ten_duan = args.ten_duan
      this.duan_ims_id = args.duan_ims_id
      console.log('🚀 ~ file: index.vue:466 ~ this.duan_ims_id:', this.duan_ims_id)
    },
    async getKyHieuketCuoi (ketcuoi_n_id, ketcuoi_d_id) {
      try {
        var rs
        if (!(ketcuoi_n_id === null || ketcuoi_n_id === '' || ketcuoi_n_id === undefined)) {
          rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/sp_get_tt_ketcuoi', {P_KETCUOI_ID: ketcuoi_n_id})
          if (rs.data.length > 0) {
            let KYHIEU = rs.data[0].KYHIEU
            this.dataSelected.ketcuoi_n_kyhieu = KYHIEU
          }
        }
        if (!(ketcuoi_d_id === null || ketcuoi_d_id === '' || ketcuoi_d_id === undefined)) {
          rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/sp_get_tt_ketcuoi', {P_KETCUOI_ID: ketcuoi_d_id})
          if (rs.data.length > 0) {
            let KYHIEU = rs.data[0].KYHIEU
            this.dataSelected.ketcuoi_d_kyhieu = KYHIEU
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:412 ~ getKyHieuketCuoi ~ error:', error)
        return null
      }
    },
    xoaMA_GOITHAU_IMS () {
      this.dataSelected.ma_goithau_ims = null
    },
    xoaMaMA_HOPDONG_IMS () {
      this.dataSelected.ma_hopdong_ims = null
    },
    async loadThongTinCap (cap_id) {
      try {
        let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/sp_thongtin_cap', {P_CAP_ID: cap_id})
        if (rs.data !== null && rs.data.length === 1) {
          this.dataSelected.kyhieu = rs.data[0].KYHIEU
          this.dataSelected.ma_cap = rs.data[0].MA_CAP
          this.dataSelected.ten_cap = rs.data[0].TEN_CAP
          this.dataSelected.ghichu = rs.data[0].GHICHU
          this.dataSelected.kichco_id = rs.data[0].KICHCO_ID
          this.dataSelected.kc_cot = rs.data[0].KC_COT

          this.dataSelected.trokhang = rs.data[0].TROKHANG
          this.dataSelected.suyhao = rs.data[0].SUYHAO

          this.dataSelected.sodoiday = rs.data[0].SODOIDAY
          this.dataSelected.soi_bd = rs.data[0].SOI_BD

          this.dataSelected.dienct_id = rs.data[0].DIENCT_ID
          this.dataSelected.chieudai = rs.data[0].CHIEUDAI
          this.dataSelected.chieudai_tk = rs.data[0].CHIEUDAI_TK

          this.dataSelected.noisx_id = rs.data[0].NOISX_ID
          this.dataSelected.ttsd_id = rs.data[0].TTSD_ID

          this.dataSelected.ngay_sd = rs.data[0].NGAY_SD
          this.dataSelected.ngay_ld = rs.data[0].NGAY_LD

          this.dataSelected.loaicap_id = rs.data[0].LOAICAP_ID
          this.dataSelected.lopmc_id = rs.data[0].LOPMC_ID
          this.dataSelected.nhomlc_id = rs.data[0].NHOMLC_ID
          this.dataSelected.kieudc_id = rs.data[0].KIEUDC_ID
          this.dataSelected.kieucap_id = rs.data[0].KIEUCAP_ID

          this.dataSelected.capgoc_kyhieu = rs.data[0].CAPGOC_KYHIEU
          this.dataSelected.capgoc_id = rs.data[0].CAPGOC_ID

          this.dataSelected.donvi_id = rs.data[0].DONVI_ID
          this.dataSelected.donvi_td_id = rs.data[0].DONVI_TD_ID

          this.dataSelected.tram_nguon = rs.data[0].TRAM_NGUON
          this.dataSelected.tram_dich = rs.data[0].TRAM_DICH

          this.dataSelected.ketcuoi_n_kyhieu = rs.data[0].KETCUOI_NGUON
          this.dataSelected.ketcuoi_d_kyhieu = rs.data[0].KETCUOI_DICH

          this.dataSelected.ketcuoi_n_id = rs.data[0].KETCUOI_N_ID
          this.dataSelected.ketcuoi_d_id = rs.data[0].KETCUOI_D_ID

          this.dataSelected.duan_id = rs.data[0].DUAN_ID
          this.dataSelected.taisan_id = rs.data[0].TAISAN_ID

          this.dataSelected.toql_id = rs.data[0].TOQL_ID
          this.dataSelected.chuquan_id = rs.data[0].CHUQUAN_ID
          this.dataSelected.ds_nhanvien_ql = rs.data[0].DS_NHANVIEN_QL

          this.dataSelected.ims_id = rs.data[0].IMS_ID
          this.dataSelected.ma_csht = rs.data[0].MA_CSHT

          this.dataSelected.ma_goithau_ims = rs.data[0].MA_GOITHAU_IMS
          this.dataSelected.ma_hopdong_ims = rs.data[0].MA_HOPDONG_IMS
          this.dataSelected.ngay_cn = rs.data[0].NGAY_CN
          this.dataSelected.nguoi_cn = rs.data[0].NGUOI_CN

          this.dataSelected.ma_ttvt = rs.data[0].MA_DV_CHA

          if (this.dataSelected.duan_id !== null && this.dataSelected.duan_id !== '') {
            let rs = await this.$root.context.get(
              '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'DUAN_CT', option: this.dataSelected.duan_id}
            )
            if (rs && rs.data.length > 0) {
              this.ten_duan = rs.data[0].MA_CT
            } else {
              this.ten_duan = ''
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:549 ~ loadThongTinCap ~ error:', error)
      }
    },
    getMaIMS: function (args) {
      this.dataSelected.ims_id = args
    },
    xoaMaIMS: function () {
      this.dataSelected.ims_id = null
    },
    convertDate: function (date) {
      try {
        let ngay = new Date(date).toLocaleDateString('en-US', { day: '2-digit' }) + '/' + new Date(date).toLocaleDateString('en-US', { month: '2-digit' }) + '/' + new Date(date).toLocaleDateString('en-US', { year: 'numeric' })
        return ngay
      } catch (error) {
        console.log('convertDate', date, error)
      }
    },
    async ghiLai () {
      if (this.action === 'taocap') {
        try {
          if (this.validateData() === false) return false

          var ngay_ld = this.dataSelected.ngay_ld && this.convertDate(this.dataSelected.ngay_ld)
          var ngay_sd = this.dataSelected.ngay_sd && this.convertDate(this.dataSelected.ngay_sd)

          let data = {
            kyhieu: this.dataSelected.kyhieu,
            ten_cap: this.dataSelected.ten_cap,
            ma_cap: this.dataSelected.ma_cap,
            kichco_id: this.dataSelected.kichco_id,
            trokhang: this.dataSelected.trokhang,
            suyhao: this.dataSelected.suyhao,
            kc_cot: this.dataSelected.kc_cot,
            sodoiday: this.dataSelected.sodoiday,
            dienct_id: this.dataSelected.dienct_id,
            chieudai: this.dataSelected.chieudai,
            noisx_id: this.dataSelected.noisx_id,
            loaicap_id: this.dataSelected.loaicap_id,
            ngay_ld: ngay_ld,
            ngay_sd: ngay_sd,
            ttsd_id: this.dataSelected.ttsd_id,
            capgoc_id: this.dataSelected.capgoc_id,
            lopmc_id: this.dataSelected.lopmc_id,
            nhomlc_id: this.dataSelected.nhomlc_id,
            kieudc_id: this.dataSelected.kieudc_id,
            donvi_id: this.dataSelected.donvi_id,
            ketcuoi_n_id: this.dataSelected.ketcuoi_n_id,
            ketcuoi_d_id: this.dataSelected.ketcuoi_d_id,
            toql_id: this.dataSelected.toql_id,
            donvi_td_id: this.dataSelected.donvi_td_id,
            kieucap_id: this.dataSelected.kieucap_id,
            ghichu: this.dataSelected.ghichu,
            taisan_id: this.dataSelected.taisan_id,
            duan_id: this.dataSelected.duan_id,
            chuquan_id: this.dataSelected.chuquan_id,
            soi_bd: this.dataSelected.soi_bd,
            ma_csht: this.dataSelected.ma_csht,
            ims_id: this.dataSelected.ims_id,
            chieudai_tk: this.dataSelected.chieudai_tk,
            ma_goithau_ims: this.dataSelected.ma_goithau_ims,
            ma_hopdong_ims: this.dataSelected.ma_hopdong_ims
          }
          this.$root.showLoading(true)
          let rs = await this.$root.context.post('/web-cabman/cap/themmoi_v4', data)
          if (rs.data != null) {
            this.$root.showLoading(false)
            this.$alert('Ghi dữ liệu cáp thành công', 'Thông báo', {
              confirmButtonText: 'Đóng',
              type: 'info',
              callback: action => {
                this.resolvePromise({ok: true, data: rs.data})
                this.$bvModal.hide(this.modalID)
              }
            })
          } else {
            this.$root.showLoading(false)
            this.$alert('Ghi dữ liệu cáp thất bại', 'Thông báo', {
              confirmButtonText: 'Đóng',
              type: 'error'
            })
          }
        } catch (error) {
          this.$root.showLoading(false)
          this.$alert(error.response.data.message_detail, error.response.data.message, {
            dangerouslyUseHTMLString: true,
            confirmButtonText: 'Đóng',
            type: 'error'
          })
        }
      } else {
        try {
          if (this.validateData() === false) return false

          var ngay_ld = this.dataSelected.ngay_ld && this.convertDate(this.dataSelected.ngay_ld)
          var ngay_sd = this.dataSelected.ngay_sd && this.convertDate(this.dataSelected.ngay_sd)

          let data = {
            cap_id: this.dataSelected.cap_id,
            kyhieu: this.dataSelected.kyhieu,
            ten_cap: this.dataSelected.ten_cap,
            ma_cap: this.dataSelected.ma_cap,
            kichco_id: this.dataSelected.kichco_id,
            kc_cot: this.dataSelected.kc_cot,
            trokhang: this.dataSelected.trokhang,
            suyhao: this.dataSelected.suyhao,
            sodoiday: this.dataSelected.sodoiday,

            dienct_id: this.dataSelected.dienct_id,
            chieudai: this.dataSelected.chieudai,
            noisx_id: this.dataSelected.noisx_id,

            loaicap_id: this.dataSelected.loaicap_id,

            ngay_ld: ngay_ld,
            ngay_sd: ngay_sd,

            ttsd_id: this.dataSelected.ttsd_id,

            lopmc_id: this.dataSelected.lopmc_id,
            nhomlc_id: this.dataSelected.nhomlc_id,

            kieudc_id: this.dataSelected.kieudc_id,
            toql_id: this.dataSelected.toql_id,
            donvi_td_id: this.dataQLCap.donvi_td_id,
            kieucap_id: this.dataSelected.kieucap_id,

            ghichu: this.dataSelected.ghichu,
            taisan_id: this.dataSelected.taisan_id,
            duan_id: this.dataSelected.duan_id,
            chuquan_id: this.dataSelected.chuquan_id,

            soi_bd: this.dataSelected.soi_bd,
            ma_csht: this.dataSelected.ma_csht,
            ims_id: this.dataSelected.ims_id,
            chieudai_tk: this.dataSelected.chieudai_tk,
            ma_goithau_ims: this.dataSelected.ma_goithau_ims,
            ma_hopdong_ims: this.dataSelected.ma_hopdong_ims
          }
          this.$root.showLoading(true)
          let rs = await this.$root.context.post('/web-cabman/cap/capnhat_v4', data)
          if (rs.data != null) {
            this.$root.showLoading(false)
            this.$root.$toast.success('Cập nhật cáp thành công')
            this.resolvePromise({ok: true, data: rs.data, result: data})
            this.$bvModal.hide(this.modalID)
          } else {
            this.$root.showLoading(false)
            this.$root.$toast.error('Cập nhật cáp thất bại')
          }
        } catch (error) {
          this.$root.showLoading(false)
          this.$alert(error.response.data.message_detail, error.response.data.message, {
            dangerouslyUseHTMLString: true,
            confirmButtonText: 'Đóng',
            type: 'error'
          })
        }
      }
    },
    validateData () {
      this.$v.$touch()

      if (this.$v.$invalid && !this.$v.dataSelected.kyhieu.required) {
        this.$root.$toast.error('Ký hiệu cáp không được để trống')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.kyhieu.maxLength) {
        this.$root.$toast.error('Ký hiệu cáp không được nhập quá 600 ký tự')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.ten_cap.required) {
        this.$root.$toast.error('Tên cáp không được để trống')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.ten_cap.maxLength) {
        this.$root.$toast.error('Tên cáp không được nhập quá 600 ký tự')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.kichco_id.required) {
        this.$root.$toast.error('Kích cỡ cáp không được để trống')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.sodoiday.required) {
        this.$root.$toast.error('Dung lượng cáp không được để trống')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.sodoiday.minValue) {
        this.$root.$toast.error('Dung lượng cáp không được nhập nhỏ hơn 1')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.sodoiday.maxValue) {
        this.$root.$toast.error('Dung lượng cáp không được nhập lớn hơn 99999')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.dienct_id.required) {
        this.$root.$toast.error('Điện cao thế cáp không được để trống')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.chieudai.required) {
        this.$root.$toast.error('Chiều dài cáp không được để trống')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.chieudai.minValue) {
        this.$root.$toast.error('Chiều dài cáp không được nhập nhỏ hơn 1')
        return false
      }

      if (this.$v.$invalid && !this.$v.dataSelected.chieudai.maxValue) {
        this.$root.$toast.error('Chiều dài cáp không được nhập lớn hơn 99999')
        return false
      }

      return true
    },
    getMaCsht: function (args) {
      this.dataSelected.ma_csht = args
    },
    xoaMaTS: function () {
      this.dataSelected.taisan_id = null
    },
    xoaMaCSHT: function () {
      this.dataSelected.ma_csht = null
    },
    xoaMaDA: function () {
      this.dataSelected.duan_id = null
      this.dataSelected.taisan_id = null
      this.ten_duan = null
    },
    xoaTOQL: function () {
      this.dataSelected.donvi_id = null
    },
    getTTNgayGio: function(args){
      let rs = "";
      if(!(args == null || args == undefined))
      {
        rs = DateTimeLib.toDateTimeDisplayWithFormat(DateTimeLib.toDateTimeObjectWithFormat(args, "YYYY-MM-DD HH:mm:ss"), "HH:mm:ss, DD/MM/YYYY");
      }
      return rs;
    }
  },
  mounted: function () {
  }
}
</script>
<style>
.select2-selection {
    overflow: hidden;
  }
  .select2-container .select2-selection--single .select2-selection__rendered {
    white-space: normal !important;
    word-break: break-all;
  }
</style>
