<template src="./GachNoThueBaoDiDongCCBS.html"></template>

<script>
import Vue from 'vue'
import { maxLength, numeric, required } from 'vuelidate/lib/validators'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import KyCuoc from '@/components/KyCuoc'
import moment from 'moment'
import EventBus from '@/utils/eventBus'

import GachNoDiDongCCBSAPI from '../api/GachNoDiDongCCBSAPI'
import TraCuuPhieuTraVinaPhone from './components/TraCuuPhieuTraVinaPhone'

export default {
  components: {
    appKyCuoc: KyCuoc,
    appTraCuuPhieuTraVinaPhone: TraCuuPhieuTraVinaPhone
    // appDanhSachPhieuTraHuy: DanhSachPhieuTraHuy
  },
  created () {
    EventBus.$on('chukycuocChange', this.chukycuocChangeHandler)
  },
  mounted () {
  },
  destroyed () {
    EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
  },
  data () {
    return {
      params: {
        p_kycuoc: moment(new Date()).subtract(1, 'months').endOf('month').format('YYYYMM') + '01',
        p_loai_timkiem: 0, // 0: ma_tt, 1: so_dd
        p_ma_tt: null,
        p_ma_kh: null,
        p_so_dd: null,
        p_tong_no: 0,
        p_tien_tra: 0,
        p_kieu_dongbo: 0, // 0: qua DBSync, 1: qua WS
        p_neo_ma_tt: null,
        p_ma_dd: null,
        p_ten_tt: null,
        p_diachi_tt: null,
        logCCBS: ''
      },
      results: [],
      pageinfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      },
    }
  },
  methods: {
    validateSearch () {
      let errors = []
      if (this.params.p_loai_timkiem === 0 && (this.params.p_ma_tt === null || ('' + this.params.p_ma_tt).trim() === '')) {
        errors.push('Chưa nhập mã khách hàng')
      }
      if (this.params.p_loai_timkiem === 1 && (this.params.p_so_dd === null || ('' + this.params.p_so_dd).trim() === '')) {
        errors.push('Chưa nhập số máy')
      }

      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }

      return true
    },
    doSearchByMaTT: function () {
      if (!this.validateSearch())
        return
      this.params.p_ma_dd = null
      this.params.p_ten_tt = null
      this.params.p_diachi_tt = null
      this.params.p_neo_ma_tt = null

      this.loading(true)
      this.getThongTinNo().then(
        (res) => {
          if (res.data.error_code === 'BSS-00000000'
              && res.data.data !== undefined) {
            let resultCT = res.data.data.chiTiet
            let resultTH = res.data.data.tongHop

            this.params.p_neo_ma_tt = resultCT[0].MA_TT_NEO

            if (resultCT.length === 1) {
              this.params.p_ma_kh = resultCT[0].MA_TT_NEO
              this.params.p_ma_tt = resultCT[0].MA_TT
            } else if (resultCT.length > 1) {

            }

            if (resultTH !== null && resultTH.length > 0) {
              this.params.p_ma_dd = resultTH[0].MATB_DD
              this.params.p_ten_tt = resultTH[0].TEN_TB
              this.params.p_diachi_tt = resultTH[0].DIACHI_TT
            }
            this.tracuuTheoMaKH(resultCT)
          } else {
            this.$toast.error('Không tìm thấy thông tin nợ.')
          }
        }
      )
        .catch((error) => { this.$toast.error(error.data.message_detail) })
        .finally(() => { this.loading(false) })
    },

    async doSearchBySM () {
      if (!this.validateSearch()) return
      this.params.p_ma_dd = null
      this.params.p_ten_tt = null
      this.params.p_diachi_tt = null
      this.params.p_neo_ma_tt = null

      this.loading(true)

      // await this.tracuuTheoMaTB('84' + this.params.p_so_dd)

      // if (this.results.length <= 0)
      //   return

      this.getThongTinNo().then(
        (res) => {
          if (res.data.error_code === 'BSS-00000000' && res.data.data !== undefined) {
            let resultCT = res.data.data.chiTiet
            let resultTH = res.data.data.tongHop

            if (resultTH !== null && resultTH.length > 0) {
              this.params.p_neo_ma_tt = resultTH[0].MA_TT_NEO
              this.params.p_ma_dd = resultTH[0].MATB_DD
              this.params.p_ten_tt = resultTH[0].TEN_TB
              this.params.p_diachi_tt = resultTH[0].DIACHI_TT
            }
            this.tracuuTheoMaKH(resultCT)
          } else {
            this.$toast.error('Không tìm thấy thông tin nợ.')
          }
        }
      )
        .catch((error) => { this.$toast.error(error.response.data.message_detail) })
        .finally(() => { this.loading(false) })
    },
    async getTTNoDBSync () {
      let postData = {
        kyCuoc: this.params.p_kycuoc,
        ma: this.params.p_loai_timkiem === 0 ? this.params.p_ma_tt : this.params.p_so_dd
      }
      try {
        const result = await GachNoDiDongCCBSAPI.getTTNoDBSync(this.axios, postData)
        return result
      } catch (err) {
        this.$toast.error(err.data.message_detail)
      }
    },

    async doGachNo () {
      if (this.results.length <= 0) {
        this.$toast.error('Không có thuê bao để gạch nợ.')
        return
      }

      // let postData = {
      //   kyCuoc: this.params.p_kycuoc,
      //   userNeo: this.$auth.getMaCCBS(),
      //   flags: 1,
      //   khdNext: moment(this.params.p_kycuoc).add(1, 'M'),
      //   maTB: this.params.p_loai_timkiem === 1 ? this.params.p_so_dd : null,
      //   maKH: this.params.p_ma_kh,
      //   phieuIdNeo: []
      // }

      // let selectedRows = this.$refs.gridDSThueBao.getSelectedRecords()

      // if (selectedRows.length <= 0) {
      //   this.$toast.error('Chưa chọn thuê bao để gạch nợ.')
      //   return
      // }

      // selectedRows.forEach((item) => {
      //   postData.phieuIdNeo.push(item.PHIEU_NEO_ID)
      // })

      // this.loading(true)

      // GachNoDiDongCCBSAPI.gachNoCCBS(this.axios, postData)
      //   .then((res) => {

      //   })
      //   .catch((error) => {
      //     this.$toast.error(error.data.message_detail)
      //   })
      //   .finally(() => { this.loading(false) })

      let selectedRows = this.$refs.gridDSThueBao.getSelectedRecords()
      this.params.logCCBS = ''
      if (selectedRows.length <= 0) {
        this.$toast.error('Chưa chọn thuê bao để gạch nợ.')
        return
      }
      // this.loading(true)
      let promises = []
      // let i = 0
      selectedRows.forEach((item) => {
        let postData = {
          kyCuoc: this.params.p_kycuoc,
          flags: 1,
          maTB: this.params.p_so_dd,
          maKH: item.MA_NEO_TT,
          gachNo: {
            kyhoadon: this.params.p_kycuoc,
            dschukyno: item.THANGNO,
            dsma_kh: item.MA_NEO_TT,
            dsso_tb: this.params.p_loai_timkiem === 1 ? '84' + this.params.p_so_dd : null,
            ngaytt: moment(Date.now()).format('DD/MM/YYYY'),
            dstientra: item.TIENTRA
          }

        }
        promises.push(this.gachnoCCBS(postData))
        // console.log('postData', postData)
        // GachNoDiDongCCBSAPI.gachNoCCBS(this.axios, postData)
        //   .then((res) => {
        //     this.$toast.success('Đồng bộ thành công')
        //   })
        //   .catch((error) => {
        //     this.params.logCCBS = this.params.logCCBS + moment(Date.now()).format('DD/MM/YYYY') + '- Bản ghi thứ ' + i + ' chu kỳ :' + item.THANGNO + ' ' + error.response.data.message_detail + '\r\n'
        //   })
        //   .finally(() => { this.loading(false) })
        // i++
      })

      this.loading(true)
      const res = await Promise.all(promises)
      const failed = res.filter(x => (x === false))
      this.loading(false)

      if (failed.length > 0) {
        this.$toast.error('Đã đồng bộ lỗi ' + failed.length + ' / ' + res.length)
      }

    },
    async gachnoCCBS (postData) {
      try {
        const res = await GachNoDiDongCCBSAPI.gachNoCCBS(this.axios, postData)
        const data = await res.data
        return true
      } catch (error) {
        return false
      }
    },
    dongBo () {

    },
    async getThongTinNo () {
      let postData = {
        kyCuoc: this.params.p_kycuoc,
        ma: this.params.p_loai_timkiem === 0 ? this.params.p_ma_tt : this.params.p_so_dd
      }
      try {
        const result = await GachNoDiDongCCBSAPI.getTTNoTheoMaTT(this.axios, postData)
        return result
      } catch (err) {
        this.$toast.error(err.data.message_detail)
      }
    },

    doDSPhieuThu () {
      this.$refs.refTraCuuPhieuTraVinaPhone.openDialog()
    },
    async tracuuTheoMaKH (danhsach) {
      this.params.p_tong_no = 0
      this.results = []
      danhsach.forEach(item => {
        this.getThongTinNoCCBS(item.MA_TT_NEO).then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000'
              && res.data.data !== undefined) {
              let resultTH = res.data.data

              resultTH.forEach(i => {
                this.params.p_tong_no = this.params.p_tong_no + parseInt(i.TONGNO)
                this.params.p_tien_tra = this.params.p_tong_no
                this.results.push({ MA_TT: item.MA_TT, MA_NEO_TT: item.MA_TT_NEO, SM_DD: i.MA_TB, THANGNO: i.CHUKYNO, TONG_NO: parseInt(i.TONGNO), CONNO: 0, TIENTRA: parseInt(i.TONGNO) })
              })
            }
          }
        )
      })
    },
    async tracuuTheoMaTB (maTB) {
      let postData = {

      }
    },
    async getThongTinNoCCBS (ma) {
      let postData = {
        kyHoaDon: this.params.p_kycuoc,
        maKH: ma
      }
      try {
        const result = await GachNoDiDongCCBSAPI.getTTNoWS(this.axios, postData)
        return result
      } catch (err) {
        this.$toast.error(err.data.message_detail)
      }
    },
    changeSearchType (type) {
      // console.log(this.$refs.gridDSThueBao.$refs.grid)
      let objGrid = this.$refs.gridDSThueBao.$refs.grid.ej2Instances
      let columns = objGrid.columns
      // console.log()
      this.params.p_loai_timkiem = type

      if (this.params.p_loai_timkiem === 0) {
        columns.filter(x => x.foreignKeyField === 'MA_TT').forEach( y => y.visible = true )
        columns.filter(x => x.foreignKeyField === 'MA_NEO_TT').forEach(y => y.visible = true)
        // objGrid.showColumns(['MA_TT', 'MA_NEO_TT'])
      } else {
        columns.filter(x => x.foreignKeyField === 'MA_TT').forEach(y => y.visible = false)
        columns.filter(x => x.foreignKeyField === 'MA_NEO_TT').forEach(y => y.visible = false)
        // objGrid.hideColumns(['MA_TT', 'MA_NEO_TT'])
      }
      // objGrid.headerModule.refresh()
      objGrid.refreshColumns()
    },
    chukycuocChangeHandler (e) {
      if (e.p_chuky_no !== null) {
        this.params.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no
      } else {
        this.params.p_kycuoc = null
      }
    },
    RowChanged (e) {

    }
  }
}
</script>
