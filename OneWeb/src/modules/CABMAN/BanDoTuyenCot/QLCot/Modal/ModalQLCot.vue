<template src="./ModalQLCot.html"></template>
<script>
import ModalTreocap from '../../TreoCap/Modal'
import ModalAnhChup from '../../AnhChup/Modal'
import ModalCsht from '../../Csht/Modal'
import ModalDsCot from '@/modules/CABMAN/BanDoMangCap/modal/dsCot/Modal'
import ModalChonDiaChi from '../../ChonDiaChi/Modal'
import ToaDo from '../../ToaDo/Modal'
import ModalChonToaDo from '@/modules/CABMAN/BanDoMangCap/modal/ChonToaDo' // Sinh edit
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import EventBus from '@/utils/eventBus'
import ModalNVQLCot from '../../../gan-nhanvien-quanly-cap/Modal'
import {
  minLength,
  maxLength,
  numeric,
  required
} from 'vuelidate/lib/validators'
const INVALID_DATE = 'Invalid Date/Invalid Date/Invalid Date'
export default {
  name: 'ModalQLCot',
  props: ['modalId', 'data'],
  components: { ModalTreocap, ModalAnhChup, ModalCsht, ModalChonDiaChi, ToaDo, BssErrorMarker, BssRequiredMarker, ModalChonToaDo, ModalDsCot, ModalNVQLCot },
  created: async function () {},
  watch: {},
  computed: {
    getPosition () {
      return {
        lat: parseFloat(this.dataSelected.vi_do),
        lng: parseFloat(this.dataSelected.kinh_do)
      }
    },
    getDiachi () {
      return this.dataSelected.diachi
    },
    objToaDo: function () {
      let toados = ''
      if (this.dataSelected.vi_do == null || this.dataSelected.kinh_do == null) { toados = '0,0' } else { toados = this.dataSelected.vi_do + ',' + this.dataSelected.kinh_do }
      return {
        diachi: this.dataSelected.diachi,
        toado: toados
      }
    }
  },
  methods: {
    initData: function (_readOnly) {
      console.log('_readOnly', _readOnly)
      this.readOnly = _readOnly
    },
    focusMyElement: async function () {
      try {
        this.$root.showLoading(true)
        await this.initForm()
        if (this.data != null) {
          this.dataSelected.vi_do = this.data.vi_do
          this.dataSelected.kinh_do = this.data.kinh_do
          this.dataSelected.cot_id =
            this.data.cot_id == null ? 0 : this.data.cot_id
          this.dataSelected.tuyen_cot_id = this.data.tuyencot_id ? this.data.tuyencot_id : '-1'
          this.readOnly = this.data.readOnly
          this.ten_tuyen = this.data.ten_tuyen ? this.data.ten_tuyen : ''
          this.donviql = this.data.dvql ? this.data.dvql : []
          if (this.donviql != []) { this.dataSelected.donvi_id = this.donviql.id ? this.donviql.id : -1 }
          if (this.dataSelected.cot_id != null &&
                 this.dataSelected.cot_id != 0) { await this.chitietCot() }
        } else {
          this.dataSelected.tinh_id = null
          this.dataSelected.quan_id = null
          this.dataSelected.phuong_id = null
          this.dataSelected.pho_id = null
          this.dataSelected.ap_id = null
          this.dataSelected.khu_id = null
          this.dataSelected.dac_diem_id = null
          this.dataSelected.diachi = ''
          this.dataSelected.so_nha = ''
        }
        this.disabledClass = this.dataSelected.cot_id == 0 || this.readOnly ? 'disabled' : ''
      } catch (er) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    hiddenForm: function () {
      this.$v.$reset()
      this.dataSelected = {
        cot_id: null,
        loai_cot: 0,
        kieu_cot: 0,
        chu_quan_id: null,
        tai_san_id: null,
        du_an_id: null,
        ttsd_id: null,
        ma_cot: '',
        ten_cot: '',
        so_hieu: '',
        diachi: '',
        docao: 0,
        ngay_ld: Date,
        ngay_sd: Date,
        tuyen_cot_id: null,
        cot_sau_id: '',
        kc_cot_sau: null,
        socaptreo: null,
        donvi_id: null,
        kinh_do: 0,
        vi_do: 0,
        ma_csht: null,
        diachi_id: null,
        tinh_id: null,
        quan_id: null,
        phuong_id: null,
        pho_id: null,
        ap_id: null,
        khu_id: null,
        dac_diem_id: null,
        so_nha: '',
        tramba_id : null
      }
      this.readOnly = false
    },

    chitietCot: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/chitiet_cot_theo_id/' +
            this.dataSelected.cot_id
        )
        console.log('dataCot:', rs.data)
        if (rs.data != null) this.mapDataCot(rs.data)
      } catch (error) {
      } finally {
      }
    },
    getColSau (data) {
      console.log('🚀 ~ file: ModalQLCot.vue:134 ~ getColSau ~ data:', data)
      if (data) {
        this.dataSelected.cot_sau_id = data.COT_ID
        this.dataSelected.sohieu_cot_sau = data.SO_HIEU
      }
    },
    initForm: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/lay_dulieu_qlcot_v2'
        )
        if (rs.data != null) {
          this.dsChuQuan = rs.data.dsChuQuan
          this.dsLoaiCot = rs.data.dsLoaiCot
          this.dsKieuCot = rs.data.dsKieuCot
          this.dsTTSD = rs.data.dsTTSD
          this.dsDuAn = rs.data.dsDuAn
          this.dsTaiSan = rs.data.dsTaiSan
          this.dsTramBA = rs.data.dsTramBA
        }
        this.$v.dataSelected.so_hieu.$error = false
        this.$v.dataSelected.ten_cot.$error = false
        this.$v.dataSelected.diachi.$error = false
        this.$v.dataSelected.kinh_do.$error = false
        this.$v.dataSelected.vi_do.$error = false
      } catch (error) {
      } finally {
      }
    },
    mapDataCot: function (cot) {
      if (cot != null) {
        this.doiTuongObj.DOITUONG_ID = cot.COT_ID
        this.dataSelected.loai_cot = cot.LOAICOT_ID
        this.dataSelected.kieu_cot = cot.KIEUCOT_ID
        this.dataSelected.chu_quan_id = cot.CHUQUAN_ID
        this.dataSelected.tai_san_id = cot.TAISAN_ID
        this.dataSelected.du_an_id = cot.DUAN_ID
        this.dataSelected.ttsd_id = cot.TTSD_ID
        this.dataSelected.ma_cot = cot.MA_COT
        this.dataSelected.ten_cot = cot.TEN_COT
        this.dataSelected.so_hieu = cot.SO_HIEU
        this.dataSelected.diachi = cot.DIACHI
        this.dataSelected.docao = cot.DOCAO
        this.dataSelected.ngay_ld = cot.NGAY_LD
        this.dataSelected.ngay_sd = cot.NGAY_SD
        this.dataSelected.cot_sau_id = cot.COTSAU_ID
        this.dataSelected.kc_cot_sau = cot.KC_COTSAU
        this.dataSelected.socaptreo = cot.SO_CAPTREO
        this.dataSelected.kinh_do = cot.KINHDO
        this.dataSelected.vi_do = cot.VIDO
        this.dataSelected.ma_csht = cot.MA_CSHT
        this.dataSelected.diachi_id = cot.DIACHI_ID

        this.dataSelected.tinh_id = cot.TINH_ID
        this.dataSelected.quan_id = cot.QUAN_ID
        this.dataSelected.phuong_id = cot.PHUONG_ID
        this.dataSelected.pho_id = cot.PHO_ID
        this.dataSelected.ap_id = cot.AP_ID
        this.dataSelected.khu_id = cot.KHU_ID
        this.dataSelected.dac_diem_id = cot.DACDIEM_ID
        this.dataSelected.so_nha = cot.SONHA
        this.toado = cot.KINHDO + ',' + cot.VIDO
        this.dataSelected.tuyen_cot_id = cot.TUYENCOT_ID
        this.ten_tuyen = cot.TUYEN_COT
        this.dataSelected.donvi_id = cot.DONVI_ID
        this.donviql = {
          id: cot.DONVI_ID,
          name: cot.TEN_DV
        },
        this.dataSelected.TEN_NVQL = cot.DS_NHANVIEN_QL
        this.dataSelected.tramba_id = cot.TRAMBA_ID
        this.dataSelected.ten_tramba = cot.TEN_TRAMBA
      }
    },
    ghiLai: async function () {
      if (!this.validateData()) {
        if (this.dataSelected.cot_id != null && this.dataSelected.cot_id != 0) { await this.capNhat() } else await this.themMoi()
        if (this.isSuccess) { this.closeForm() }
      }
    },
    capNhat: async function () {
      try {
        this.$root.showLoading(true)
        this.checkValidDate()
        var rs = await this.$root.context.post(
          '/web-cabman/bando_tuyencot/capnhat_cot_v2',
          this.dataSelected
        )
        if (rs.data != null) {
          this.$toast.success('Cập nhật cột thành công')
          this.isSuccess = true
        } else this.$toast.error('Cập nhật cột thất bại')
      } catch (error) {
        this.$toast.error(error.response.data.message_detail)
      } finally {
        this.$root.showLoading(false)
      }
    },
    themMoi: async function () {
      try {
        this.$root.showLoading(true)
        this.checkValidDate()
        var rs = await this.$root.context.post(
          '/web-cabman/bando_tuyencot/them_cot_v2',
          this.dataSelected
        )
        if (rs.data != null) {
          this.$toast.success('Thêm cột thành công')
          this.isThemCot = true
          this.isSuccess = true
        } else this.$toast.error('Thêm cột thất bại')
      } catch (error) {
        this.$toast.error(error.response.data.message_detail)
      } finally {
        this.$root.showLoading(false)
      }
    },

    getDiaChi: function (data) {
      this.dataSelected.diachi = data.diachimoi
      this.dataSelected.tinh_id = data.tinh_id
      this.dataSelected.quan_id = data.quan_id
      this.dataSelected.phuong_id = data.phuong_id
      this.dataSelected.pho_id = data.pho_id
      this.dataSelected.ap_id = data.ap_id
      this.dataSelected.khu_id = data.khu_id
      this.dataSelected.dac_diem_id = data.dac_diem_id
      this.dataSelected.so_nha = data.so_nha
    },
    checkValidDate () {
      if (this.dataSelected.ngay_ld != null) { this.dataSelected.ngay_ld = this.convertDate(this.dataSelected.ngay_ld) }
      if (this.dataSelected.ngay_sd != null) { this.dataSelected.ngay_sd = this.convertDate(this.dataSelected.ngay_sd) }
      if (this.dataSelected.ngay_ld == INVALID_DATE) { this.dataSelected.ngay_ld = null }
      if (this.dataSelected.ngay_sd == INVALID_DATE) { this.dataSelected.ngay_sd = null }
    },
    convertDate: function (date) {
      try {
        return (
          new Date(date).toLocaleDateString('en-US', { day: '2-digit' }) +
        '/' +
        new Date(date).toLocaleDateString('en-US', { month: '2-digit' }) +
        '/' +
        new Date(date).toLocaleDateString('en-US', { year: 'numeric' })
        )
      } catch (e) { return null }
    },
    xoaMaCSHT: function () {
      this.dataSelected.ma_csht = ''
    },
    xoaMaTS: function () {
      this.dataSelected.tai_san_id = ''
    },
    xoaMaDA: function () {
      this.dataSelected.du_an_id = ''
    },
    getMaCsht: function (args) {
      this.dataSelected.ma_csht = args
    },
    getToaDo: function (toado) {
      console.log('🚀 ~ file: ModalQLCot.vue:281 ~ toado:', toado)
      if (toado != null) {
        this.dataSelected.vi_do = toado.split(',')[0]
        this.dataSelected.kinh_do = toado.split(',')[1]
      }
    },
    getToaDo2: function (toado) {
      console.log('🚀 ~ file: ModalQLCot.vue:281 ~ toado:', toado)
      if (toado != null) {
        this.dataSelected.vi_do = isNaN(toado.lat) ? null : toado.lat
        this.dataSelected.kinh_do = isNaN(toado.lng) ? null : toado.lng
      }
    },
    validateData (data) {
      this.$v.$touch()
      let flag = false
      let errorMessage = []
      if (!this.$v.dataSelected.loai_cot.required) {
        errorMessage.push('Trường Loại cột bắt buộc nhập dữ liệu.')
        if (!flag) { this.$refs['loai-cot'].focus() }
        flag = true
      }
      if (!this.$v.dataSelected.kieu_cot.required) {
        errorMessage.push('Trường Kiểu cột bắt buộc nhập dữ liệu.')
        if (!flag) { this.$refs['kieu-cot'].focus() }
        flag = true
      }

      if (!this.$v.dataSelected.so_hieu.required) {
        errorMessage.push('Trường Số hiệu bắt buộc nhập dữ liệu.')
        if (!flag) { this.$refs.so_hieu.focus() }
        flag = true
      }

      if (!this.$v.dataSelected.so_hieu.maxLength) {
        errorMessage.push('Trường Số hiệu dữ liệu vượt quá độ dài cho phép.')
        if (!flag) { this.$refs.so_hieu.focus() }
        flag = true
      }

      if (!this.$v.dataSelected.tramba_id.required) {
        errorMessage.push('Trường trạm biến áp cột bắt buộc nhập dữ liệu.')
        if (!flag) { this.$refs.tram_ba.focus() }
        flag = true
      }

      if (!this.$v.dataSelected.ten_cot.required) {
        errorMessage.push('Trường Tên cột bắt buộc nhập dữ liệu.')
        if (!flag) { this.$refs.ten_cot.focus() }
        flag = true
      }
      if (!this.$v.dataSelected.ten_cot.maxLength) {
        errorMessage.push('Trường Tên cột dữ liệu vượt quá độ dài cho phép.')
        if (!flag) { this.$refs.ten_cot.focus() }
        flag = true
      }

      if (!this.$v.dataSelected.diachi.required) {
        errorMessage.push('Trường Địa chỉ bắt buộc nhập dữ liệu.')
        if (!flag) { this.$refs.diachi.focus() }
        flag = true
      }
      if (!this.$v.dataSelected.diachi.maxLength) {
        errorMessage.push('Trường Địa chỉ dữ liệu vượt quá độ dài cho phép.')
        if (!flag) { this.$refs.diachi.focus() }
        flag = true
      }

      if (!this.$v.dataSelected.ttsd_id.required) {
        errorMessage.push('Trường Trạng thái sử dụng bắt buộc nhập dữ liệu.')
        if (!flag) { this.$refs.ttsd.focus() }
        flag = true
      }
      if (!this.$v.dataSelected.kinh_do.required) {
        errorMessage.push('Trường Kinh độ bắt buộc nhập dữ liệu.')
      }
      if (!this.$v.dataSelected.vi_do.required) {
        errorMessage.push('Trường Vĩ độ bắt buộc nhập dữ liệu.')
      }
      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))

        return true
      } else {
        return false
      }
    },
    closeForm: function () {
      this.$bvModal.hide(this.modalId)
      EventBus.$emit('NAP_GIAN_DO', this.isThemCot, this.isSuccess)
    },
    modalNVQLCotShow(){

    },
    closeModalNVQLCot() {
      this.chitietCot()
    },
    showModalNVQLCot() {
      this.$bvModal.show(this.idPopupNVQLCot)
    }
  },
  data: function () {
    return {
      dsChuQuan: [],
      dsLoaiCot: [],
      dsKieuCot: [],
      dsTaiSan: [],
      dsDuAn: [],
      dsTTSD: [],
      dsTramBA: [],
      dataSelected: {
        cot_id: 0,
        loai_cot: 1,
        kieu_cot: 1,
        chu_quan_id: 1,
        tai_san_id: null,
        du_an_id: null,
        ttsd_id: 1,
        ma_cot: '',
        ten_cot: '',
        so_hieu: '',
        diachi: '',
        docao: 0,
        ngay_ld: Date,
        ngay_sd: Date,
        tuyen_cot_id: null,
        cot_sau_id: null,
        sohieu_cot_sau: null,
        kc_cot_sau: null,
        socaptreo: null,
        donvi_id: null,
        kinh_do: 0,
        vi_do: 0,
        ma_csht: null,
        diachi_id: null,
        tinh_id: null,
        quan_id: null,
        phuong_id: null,
        pho_id: null,
        ap_id: null,
        khu_id: null,
        dac_diem_id: null,
        so_nha: '',
        TEN_NVQL: null,
        tramba_id: null,
        ten_tramba: null
      },
      category: {
        phanloai: 0,
        dinhdanh: 0,
        dactinh: 0,
        lienket: 0,
        kiemke: 0,
        chuquan: 0,
        bando: 0,
        macsht: 0,
        ngaythang: 0
      },
      waterMark: 'Chọn ngày',
      dateVal: Date,
      dateFormat: 'dd/MM/yyyy',
      donviql: [],
      ten_tuyen: '',
      toado: '',
      disabledClass: '',
      isThemCot: false,
      isSuccess: false,
      readOnly: false,
      idPopupNVQLCot: 'idPopupNVQLCot',
      doiTuongObj: {
        LOAI_DT: 'COT',
        DOITUONG_ID: -1,
      }
    }
  },
  validations: {
    dataSelected: {
      cot_id: { required },
      loai_cot: { required },
      kieu_cot: { required },
      ttsd_id: { required },
      tramba_id: { required },
      ten_cot: { required,
        maxLength: maxLength(100)},
      so_hieu: { required,
        maxLength: maxLength(300) },
      diachi: { required,
        maxLength: maxLength(400) },
      tuyen_cot_id: { required },
      donvi_id: { required },
      kinh_do: { required },
      vi_do: { required }
    }
  },
  mounted: function () {}
}
</script>
<style>
span.select2-search {
    z-index:10050 !important;
}
div#popupQLCot___BV_modal_outer_ {
  z-index: 1000 !important;
  margin-top: 100px;
}
a.disabled {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
.table-result td,
.table-result th {
  padding: 0px 0px !important;
}
</style>
