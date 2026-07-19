<template src="./GachNoDiDong.html"></template>

<script>
import Vue from 'vue'
import KyCuoc from '@/components/KyCuoc'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { Query } from '@syncfusion/ej2-data'
import GachNoAPI from '../api/GachNoAPI'
import CommonsAPI from '../api/Commons'
import EventBus from '@/utils/eventBus'
import moment from 'moment'
import FormDSPhieuGachNoVNP from './components/formDSPhieuGachNoVNP.vue'

import { DialogUtility } from '@syncfusion/ej2-popups'

import {
  NHOM_LNV,
  LOAI_DV
} from '@/const/enums'

Vue.use(DialogUtility)
let DialogObj

export default{
  components: {
    appKyCuoc: KyCuoc,
    BssRequiredMarker,
    FormDSPhieuGachNoVNP
  },
  async created () {
    EventBus.$on('chukycuocChange', this.chukycuocChangeHandler)

    this.loading(true)

    Promise.all([
      this.getDSThamSoMacDinh(),
      this.getDSDonViCTV(),
      this.getDSThuNgan(),
      this.getDSUserNEO(),
      this.getDSNhomCTV()
    ])

    this.loading(false)
  },
  destroyed () {
    EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
  },
  computed: {
    f_chk_dvql () {
      return this.params.p_chk_dvql === '1'
    },
    p_tungay: {
      get () {
        return this.params.p_tungay
      },
      set (value) {
        this.params.p_tungay = value
      }
    },
    f_chk_tungay () {
      return this.params.p_chk_tungay === '1'
    },
    p_denngay: {
      get () {
        return this.params.p_denngay
      },
      set (value) {
        this.params.p_denngay = value
      }
    },
    f_chk_denngay () {
      return this.params.p_chk_denngay === '1'
    }
  },
  data () {
    return {
      selectionSettings: {checkboxOnly: true, type: 'Multiple'},
      params: {
        p_kycuoc: moment(new Date()).subtract(1, 'months').endOf('month').format('YYYYMM') + '01',
        p_kieu_timkiem: null, // 0: mã tt, 1: ma_tb
        p_loai_dongbo: 1,
        p_thungan_options: [],
        p_thungan: null,
        p_chungtu: null,
        p_ma_tt: null,
        p_acc_somay: null,
        p_userneo_options: [],
        p_userneo: null,
        p_chk_dvql: '0',
        p_dvql_options: [],
        p_dvql: null,
        p_nhomctv_options: [],
        p_nhomctv: null,
        p_ctv_options: [],
        p_ctv: null,
        p_chk_tungay: '0',
        p_tungay: new Date(),
        p_chk_denngay: '0',
        p_denngay: new Date(),
        p_ghichu: null,
        p_quaythu_id: null,
        p_donviqlno_id: null,
        p_vhtttid_neo: 0
      },
      fieldsThuNgan: { text: 'TEN_NV', value: 'NHANVIEN_ID' },
      fieldsUserNEO: { text: 'USER_VNP', value: 'USER_VNP' },
      fieldsDVQL: { text: 'TEN_HIENTHI', value: 'DONVI_ID' },
      fieldsNhomCTV: { text: 'TEN', value: 'LOAINV_ID' },
      fieldsCTV: { text: 'TEN_HIENTHI', value: 'NHANVIEN_ID' },
      dateConfig: {
        value: new Date(),
        format: 'dd/MM/y',
        max: new Date()
      },
      pageinfoPhieuTT: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      },
      dsPhieuTT: [],
      totalItemsPhieuTT: 0,
      dsCTPhieuTT: [],
      pageinfoCTPhieuTT: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      },
      totalItemsCTPhieuTT: 0,
      tongtien_phieuTT: 0,
      keyCTPhieuTT: 1,
      keyDSPhieuTT: 1
    }
  }, // end of data
  methods: {
    doNhapMoi: function () {
      this.params.p_ma_tt = null
      this.params.p_acc_somay = null
      this.$refs.p_acc_somay.focus()
    },
    validateSearchData () {
      let errors = []

      if (this.params.p_kieu_timkiem === 0 &&
          (this.params.p_ma_tt === null || this.params.p_ma_tt.trim() === '')
      ) {
        errors.push('Vui lòng nhập vào mã TT')
      }
      if (this.params.p_kieu_timkiem === 1 &&
          (this.params.p_acc_somay === null || ('' + this.params.p_acc_somay).trim() === '')
      ) {
        errors.push('Vui lòng nhập vào số máy/acc')
      }

      if (this.params.p_userneo === null) { errors.push('Vui lòng chọn nhân viên NEO') }

      if (this.params.p_chk_tungay === '1' && !moment(this.params.p_tungay).isValid()) { errors.push('Giá trị từ ngày không hợp lệ') }

      if (this.params.p_chk_denngay === '1' && !moment(this.params.p_denngay).isValid()) { errors.push('Giá trị đến ngày không hợp lệ') }

      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }
      return true
    },
    onSearch () {
      this.doLayDuLieu()
    },
    doLayDuLieu: function () {
      if (!this.validateSearchData()) { return false }
      if (this.params.p_kieu_timkiem === 0) { this.params.p_ma_tt = this.params.p_ma_tt.trim() } else { this.params.p_acc_somay = this.params.p_acc_somay !== null ? ('' + this.params.p_acc_somay).trim() : null }
      let postData = {
        kyCuoc: this.params.p_kycuoc,
        maTT: this.params.p_kieu_timkiem === 0 ? this.params.p_ma_tt : null,
        maTB: this.params.p_kieu_timkiem === 1 ? this.params.p_acc_somay : null,
        userNeo: this.params.p_userneo,
        htttIdNeo: null,
        flags: this.params.p_loai_dongbo,
        tuNgay: this.params.p_chk_tungay === '1' ? moment(this.params.p_tungay).format('DD/MM/YYYY') : null,
        denNgay: this.params.p_chk_denngay === '1' ? moment(this.params.p_denngay).format('DD/MM/YYYY') : null
      }
      this.loading(true)
      GachNoAPI.getDSPhieuTTVinaphone(this.axios, postData)
        .then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
          res.data.data !== undefined &&
          res.data.data.length > 0) {
              this.dsPhieuTT = res.data.data
              this.pageinfoPhieuTT.page = 0
              this.pageinfoPhieuTT.totalElement = res.data.data.length
              // this.totalItemsPhieuTT  = res.data.data.length
              this.tongtien_phieuTT = 0
              this.dsPhieuTT.forEach((item) => {
                this.tongtien_phieuTT += parseInt(item.TONGTRA)
              })
              this.keyDSPhieuTT++
            } else {
              this.dsPhieuTT = []
              this.pageinfoPhieuTT.page = 0
              this.pageinfoPhieuTT.totalElement = 0
              this.totalItemsPhieuTT = 0
              this.totalItemsPhieuTT = 0
              this.tongtien_phieuTT = 0
            }
          }
        )
        .catch(error => { this.$toast.error(error.data.message_detail) })
        .finally(() => { this.loading(false) })
    },
    getDSTBaoTToanVNP (phieuId) {
      let postData = {
        kyCuoc: this.params.p_kycuoc,
        phieuIdNeo: phieuId,
        userNeo: this.params.p_userneo,
        kieuDongBo: this.params.p_loai_dongbo
      }

      this.loading(true)
      GachNoAPI.getDSTBaoTToanVinaphone(this.axios, postData)
        .then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
            res.data.data !== undefined &&
            res.data.data.length > 0) {
              this.dsCTPhieuTT = res.data.data
              this.pageinfoCTPhieuTT.page = 0
              this.pageinfoCTPhieuTT.totalElement = res.data.data.length
              // this.totalItemsCTPhieuTT  = res.data.data.length
              this.keyCTPhieuTT++
            } else {
              this.dsCTPhieuTT = []
              // this.totalItemsCTPhieuTT  = 0
              this.pageinfoCTPhieuTT.page = 0
              this.pageinfoCTPhieuTT.totalElement = 0
            }
          }
        )
        .catch((error) => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },
    doDongBo: function () {
      let selected = this.$refs.gridDSPhieuTT.getSelectedRecords()

      if (selected === null || selected.length <= 0) {
        this.$toast.error('Hãy chọn phiếu để thực hiện gạch nợ.')
        return
      }

      DialogObj = DialogUtility.confirm({
        title: 'Thông báo',
        content: "<div style='padding:10px'>Bạn có thật sự muốn đồng bộ dữ liệu không ?</div>",
        okButton: { text: 'Đồng ý', click: this.confirmSync },
        cancelButton: { text: 'Hủy' },
        showCloseIcon: true,
        closeOnEscape: true,
        animationSettings: { effect: 'Zoom' }
      })
    },

    confirmSync () {
      DialogObj.hide()

      let selected = this.$refs.gridDSPhieuTT.getSelectedRecords()
      let arr = []
      selected.forEach((item, index) => {
        arr.push({
          'htttIdNeo': item.HTTT_ID_NEO,
          'phieuIdNeo': item.PHIEU_ID,
          'maTTNeo': item.MA_KH,
          'ngayTTNeo': item.NGAY_TT,
          'soPhieuNeo': item.SOPHIEU

        })
      })
      let postData = {
        'kyCuoc': this.params.p_kycuoc,
        'quayThuId': this.params.p_quaythu_id,
        'dsDongBo': arr,
        'kieuDongBo': this.params.p_loai_dongbo,
        'chungTu': this.params.p_chungtu,
        'userNeo': this.params.p_userneo,
        'ghiChu': this.params.p_ghichu !== null ? this.params.p_ghichu.trim() : null
      }
      this.loading(true)
      GachNoAPI.dongboCCBSsangDHSX(this.axios, postData)
        .then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000') {
              this.$toast.success('Đã đồng bộ thành công')
              this.doLayDuLieu()
            }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
          this.doLayDuLieu()
        })
        .finally(() => {
          this.loading(false)
        })
    },
    doPhieuTra: function () {
      // this.$refs.dlgPhieuTT.openDialog()
      this.$bvModal.show('modal-dsphieugachnovnp')
    },

    getDSThuNgan: function () {
      try {
        GachNoAPI.getDSThuNgan(this.axios).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined &&
              response.data.data.length > 0) {
              this.params.p_thungan_options = response.data.data
              this.params.p_thungan = response.data.data[0].NHANVIEN_ID

              this.params.p_quaythu_id = response.data.data[0].DONVI_ID
              this.getDonViQuanLy()
            } else {
              this.params.p_thungan_options = []
              this.params.p_thungan = null
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    getDSUserNEO: function () {
      try {
        GachNoAPI.getDSUserNEO(this.axios).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined) {
              this.params.p_userneo_options = response.data.data
              this.params.p_userneo = response.data.data[0].USER_VNP
            } else {
              this.params.p_userneo_options = []
              this.params.p_userneo = null
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    getDSHTTT: function () {
      try {
        CommonsAPI.getHinhThucThanhToan(this.axios).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined) {
              this.params.p_httt_options = response.data.data
              this.params.p_httt = response.data.data[0].HTTT_ID
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    getDSNhomCTV: function () {
      try {
        GachNoAPI.getDSNhomCTV(this.axios, NHOM_LNV.CONGTACVIEN).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined) {
              this.params.p_nhomctv_options = response.data.data
              // this.params.p_nhomctv =
              // this.params.p_nganhang = response.data.data[0].LOAITIEN_ID
            } else {
              this.params.p_nhomctv_options = []
              this.params.p_nhomctv = null
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },

    async getDSThamSoMacDinh () {
      try {
        let res = await CommonsAPI.getDSThamSoMacDinh(this.axios, '')
        return res
      } catch (error) {
        return null
      }
    },
    getDSDonViCTV: function () {
      try {
        GachNoAPI.getDSDonViCTV(this.axios, {loaiDVId: LOAI_DV.DONVIQL_TT}).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined) {
              this.params.p_dvql_options = response.data.data
              // this.params.p_dvql = this.$auth.getDonViID()
              this.params.p_dvql = response.data.data[0].DONVI_ID
              // this.params.p_nganhang = response.data.data[0].LOAITIEN_ID
            } else {
              this.params.p_dvql_options = []
              this.params.p_dvql = null
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },

    onChangeNhomCTV (e) {
      CommonsAPI.getDanhSachNhanVienTheoLoai(this.axios, e.value)
        .then(
          res => {
            if (res.data.error_code === 'BSS-00000000' &&
          res.data.data !== undefined &&
          res.data.data.length > 0
            ) {
              this.params.p_ctv_options = res.data.data
              this.params.p_ctv = res.data.data[0].NHANVIEN_ID
            } else {
              this.params.p_ctv_options = []
              this.params.p_ctv = null
            }
          }
        )
    },

    async getDonViQuanLy () {
      try {
        let postData = {
          pDonViId: this.params.p_quaythu_id,
          pDSLoaiDV: LOAI_DV.DONVIQL_TT + ',' + LOAI_DV.DONVIQL_TT_BUUDIEN
        }
        GachNoAPI.getDonViQuanLy(this.axios, postData).then(
          (response) => {
            /// console.log(response)
            if (response.data.error_code === 'BSS-00000000' &&
                response.data.data !== undefined) {
              this.params.p_donviqlno_id = response.data.data[0].DONVI_ID
              this.params.p_dvql = response.data.data[0].DONVI_ID
              if (this.params.p_donviqlno_id === '0') {
                DialogUtility.alert({
                  title: 'Thông báo',
                  content: "<div style='padding:10px'>User của bạn không được phép thao tác trong chức năng này</div>",
                  okButton: { text: ' OK ', click: function () { this.$router.push('/') } },
                  showCloseIcon: true,
                  closeOnEscape: true,
                  animationSettings: { effect: 'Zoom' }
                })
                return false
              }
            } else { this.$toast.error(response.data.message) }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },

    onFilteringThuNgan (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_NV', 'contains', e.text, true) : query
      e.updateData(this.params.p_thungan_options, query)
    },
    onFilteringNhomCTV (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.params.p_nhomctv_options, query)
    },
    onFilteringCTV (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_HIENTHI', 'contains', e.text, true) : query
      e.updateData(this.params.p_ctv_options, query)
    },
    onFilteringUserNeo (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('USER_VNP', 'contains', e.text, true) : query
      e.updateData(this.params.p_userneo_options, query)
    },
    onFilteringDVQL (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_HIENTHI', 'contains', e.text, true) : query
      e.updateData(this.params.p_dvql_options, query)
    },
    chukycuocChangeHandler (e) {
      if (e.p_chuky_no !== null) {
        this.params.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no
      } else { this.params.p_kycuoc = null }
    },

    selectedRowChanged (e) {
      if (e === null) {
        this.dsCTPhieuTT = []
        return
      }
      this.getDSTBaoTToanVNP(e.PHIEU_ID)
    },

    phieuTTRecordClick (e) {
      if (e === null) {
        this.dsCTPhieuTT = []
        return
      }
      this.getDSTBaoTToanVNP(e.rowData.PHIEU_ID)
    }

  }
}
</script>
