<template src="./TraCuuPhieuTraVinaPhone.html"></template>
<style src='./TraCuuPhieuTraVinaPhone.scss' scoped></style>

<script>
import Vue from 'vue'
import { mapActions } from 'vuex'
import TraCuuPhieuTraVinaPhoneAPI from '../api/TraCuuPhieuTraVinaPhone.js'
import CommonsAPI from '../api/Commons.js'
import GachNoAPI from '../api/GachNoAPI'

import { DialogUtility } from '@syncfusion/ej2-popups'
import { cloneArray, showError } from '@/utils/util'

Vue.use(DialogUtility)

export default {
  components: {
  },
  async created () {
    await Promise.all([this.getTSMacDinh(), this.getDSChuKyNo()])

    this.params.p_kieu_thaotac = 1 // tra cứu, tạm thời set 1
    if (this.params.p_kieu_thaotac === 1) {
      this.configs.f_xoaphieu_visible = false
      this.configs.f_gachno_visible = false
      this.configs.f_dongbo_visible = true
    } else {
      this.configs.f_xoaphieu_visible = true
      // this.configs.f_gachno_visible = false
      // this.configs.f_dongbo_visible = true
    }
  },
  destroyed () {
  },
  mounted () {
  },
  computed: {
  },
  data () {
    return {
      hideTable: true,
      footerSum: function () {
        return { template: Vue.component('sumTemplate', {
          template: `<span>{{data.Sum}}</span>`,
          data () { return {data: {}} }
        })
        }
      },
      statusTemplate: function () {
        return {
          template: Vue.component('statusTemplate', {
            template: `<span :class="{'e-icons': true, 'grid-status-0': data.STATUS == '0', 'grid-status-1': data.STATUS == '1'}"></span>`,
            data: function () { return { data: {} } }
          })
        }
      },
      configs: {
        selectionSettings: {checkboxOnly: true, type: 'Multiple'},
        fieldsKyHoaDon: {text: 'CHUKYNO', value: 'CHUKYNO'},
        f_gachno_visible: false,
        f_xoaphieu_visible: false,
        f_dongbo_visible: false,
        f_sophieu_enable: true
      },
      params: {
        p_tab_kieu: 1, // 1: danh sách VNP, 2: danh sách trả dưới tỉnh
        p_tab_kieuno: 1, // 1: tháng nợ, 2: thuê bao
        p_loai_timkiem: '0', // 0: ma_tt, 1: so_dd
        p_kieu_thaotac: 1, // tra cứu
        p_kyhoadon_options: [],
        p_kyhoadon: null,
        p_ma_tt: null,
        p_ma_kh: null,
        p_ma_tb: null,
        p_user_neo: null,
        p_ma_tt_neo: null,
        p_ma_dd: null,
        p_ten_tt: null,
        p_diachi_tt: null,
        p_so_phieu: null,
        p_status: null, // trạng thái kỳ hóa đơn
        p_gachno_chuan: -1
      },
      dsTraVNP: [],
      keyDSTraVNP: 1,
      dsTraVTT: [],
      keyDSTraVTT: 1,
      dsThueBao: [],
      keyDSThueBao: 1,
      dsThangNo: [],
      keyDSThangNo: 1,
      p_thanhtoan_options: [],
      traVNPPageInfo: {
        pageIndex: 0,
        pageSize: 10,
        totalElement: 0
      },
      traVTTPageInfo: {
        pageIndex: 0,
        pageSize: 10,
        totalElement: 0
      },
      thangnoPageInfo: {
        pageIndex: 0,
        pageSize: 10,
        totalElement: 0
      },
      thuebaoPageInfo: {
        pageIndex: 0,
        pageSize: 10,
        totalElement: 0
      }
    }
  },
  methods: {
    ...mapActions('qltnCommon', [
      'getKyHoaDonHienTai'
    ]),
    doSearch () {
      this.TIMKIEM()
    },
    doGachNo () {
      let selectedRows = this.$refs.gridDSTraVTT.getSelectedRecords()
      // console.log(selectedRows)

      if (selectedRows.length <= 0) {
        this.$toast.error('Bạn chưa chọn phiếu để gạch lên VNP')
        return false
      }

      let selectedRowsNEO = selectedRows.find(x => x.PHIEU_ID_NEO !== null)

      if (selectedRowsNEO !== undefined) {
        this.$toast.error('Bạn không thể chọn phiếu đã được gạch trên VNP')
        return false
      }
    },

    doXoaPhieu () {

    },

    async doSync () {
      if (this.params.p_status === -1) {
        this.$toast.error('Không lấy được trạng thái của kỳ hóa đơn')
        return
      }

      if (this.params.p_status === 1) {
        this.$toast.error('Kỳ hóa đơn ' + this.params.p_kyhoadon + ' không được phép đồng bộ')
        return
      }

      if (this.dsTraVNP.length === 0) {
        this.$toast.error('Không có phiếu thu cần đồng bộ.')
        return
      }

      let selectedRows = this.$refs.gridDSTraVNP.getSelectedRecords()
      if (selectedRows.length === 0) {
        this.$toast.error('Bạn chưa chọn phiếu cần đồng bộ.')
        return
      }

      /*

      let promises = []
      selectedRows.forEach((item) => {
        let postData = {
          "kieu": 1,
          "kyCuoc": this.params.p_kyhoadon,
          "phieuId": item.phieu_id
        }
        console.log(postData)
        promises.push(this.doSyncCCBS(postData))
      })
      this.loading(true)
      const res = await Promise.all(promises)
      */

      let postData = {
        'kyCuoc': this.params.p_kyhoadon,
        'ds': []
      }

      selectedRows.forEach((item) => {
        postData.ds.push({
          'PHIEU_ID': item.phieu_id,
          'MA_KH': item.ma_kh,
          'MA_TB': item.somay,
          'LOAITIEN_ID': 1,
          'KIEUTRA': 0,
          'HINHTHUC': item.hinhthuc,
          'HTTT_ID': item.httt_id,
          'NGAY_TT': item.ngay_thuc,
          'TRAGOC': item.tragoc.replace(/[^\-a-zA-Z 0-9]+/g, ''),
          'TRATHUE': item.trathue.replace(/[^\-a-zA-Z 0-9]+/g, ''),
          'CHUKYNO': item.chukyno,
          'CHIHOAHONG': 0,
          'DONVIQL_ID': this.$auth.getDonViID(),
          'SOPHIEU': item.sophieu,
          'USER_NEO': item.ma_tn
        })
      })

      this.loading(true)

      const res = await this.doSyncCCBS(postData)

      if (res) {
        this.$toast.success('Đã đồng bộ thành công.')
      }

      this.doSearch()

      this.loading(false)
      /*
      let ok = res.filter(x => x === true)
      let failed = res.filter(x => x === false)

      if(ok.length === promises.length)
      {
        this.$toast.success('Đã đồng bộ thành công ' + ok.length + ' phiếu.')
        this.doSearch()
      } else
        this.$toast.error('Đã phát sinh lỗi khi đồng bộ. Tổng số phiếu lỗi: ' + failed.length)
*/
    },

    async doSyncCCBS (postData) {
      try {
        const res = await GachNoAPI.syncCCBS2DHSX(this.axios, postData)
        const data = await res.data

        if (data.error_code === 'BSS-00000000') {
          return true
        }
        return false
      } catch (error) {
        if (error.data !== undefined) this.$toast.error(error.data.message_detail)
        else console.log(error)
        return false
      } finally {}
    },

    TIMKIEM () {
      this.doSearchAll()
    },

    validateSearch () {
      let errors = []
      if (this.params.p_kyhoadon === null) errors.push('Không lấy được kỳ hóa đơn')

      if (this.params.p_loai_timkiem === '0' && (this.params.p_ma_tt === null || ('' + this.params.p_ma_tt).trim() === '')) {
        errors.push('Chưa nhập mã khách hàng')
      }
      if (this.params.p_loai_timkiem === '1' && (this.params.p_ma_tb === null || ('' + this.params.p_ma_tb).trim() === '')) {
        errors.push('Chưa nhập số máy')
      }
      if (this.params.p_loai_timkiem === '2' && (this.params.p_user_neo === null || ('' + this.params.p_user_neo).trim() === '')) {
        errors.push('Chưa nhập user Neo')
      }

      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }

      return true
    },
    changeSearchType (type) {
      this.params.p_loai_timkiem = type
    },
    /*
      clear()
      {
        this.p_thanhtoan_options = []
        this.params.p_neo_ma_tt = null
        this.params.p_ma_dd = null
        this.params.p_ten_tt = null
        this.params.p_diachi_tt = null

        this.dsTraVNP = []
        this.traVNPPageInfo.totalElement = 0
        this.traVNPPageInfo.page = 0

        this.dsTraVTT = []
        this.traVTTPageInfo.totalElement = 0
        this.traVTTPageInfo.page = 0

        this.dsThangNo = []
        this.thangnoPageInfo.totalElement = 0
        this.thangnoPageInfo.page = 0

        this.dsThueBao = []
        this.thuebaoPageInfo.totalElement = 0
        this.thuebaoPageInfo.page = 0

      },
    */
    clear () {
      this.params.p_ma_tt_neo = null
      this.params.p_ma_dd = null
      this.params.p_ten_tt = null
      this.params.p_diachi_tt = null

      this.p_thanhtoan_options = []

      this.configs.f_xoaphieu_visible = false
      this.configs.f_gachno_visible = false
      this.configs.f_dongbo_visible = true

      this.dsTraVNP = []
      this.traVNPPageInfo.totalElement = 0
      this.traVNPPageInfo.page = 0

      this.dsTraVTT = []
      this.traVTTPageInfo.totalElement = 0
      this.traVTTPageInfo.page = 0

      this.dsThangNo = []
      this.thangnoPageInfo.totalElement = 0
      this.thangnoPageInfo.page = 0

      this.dsThueBao = []
      this.thuebaoPageInfo.totalElement = 0
      this.thuebaoPageInfo.page = 0
      if (this.params.p_loai_timkiem === '0') {
        this.params.p_ma_tb = null
        this.params.p_ma_kh = null
        this.params.p_user_neo = null
      } else if (this.params.p_loai_timkiem === '1') {
        this.params.p_ma_tt = null
        this.params.p_ma_kh = null
        this.params.p_user_neo = null
      } else if (this.params.p_loai_timkiem === '2') {
        this.params.p_ma_tb = null
        this.params.p_ma_kh = null
        this.params.p_ma_tt = null
        // this.params.p_user_neo = null

        this.configs.f_dongbo_visible = false
      }
    },

    doSearchAll () {
      if (!this.validateSearch()) return

      let keyword = ''

      if (this.params.p_loai_timkiem === '0') {
        if (typeof (this.params.p_ma_tt) === 'string') {
          this.params.p_ma_tt = this.params.p_ma_tt.trim()
        }
        keyword = this.params.p_ma_tt
      } else if (this.params.p_loai_timkiem === '1') {
        if (typeof (this.params.p_ma_tb) === 'string') {
          this.params.p_ma_tb = this.params.p_ma_tb.trim()
        }
        keyword = this.params.p_ma_tb
      } else if (this.params.p_loai_timkiem === '2') {
        if (typeof (this.params.p_user_neo) === 'string') this.params.p_user_neo = this.params.p_user_neo.trim()
        keyword = this.params.p_user_neo
      }

      if (this.params.p_loai_timkiem === '2') this.configs.f_dongbo_visible = false
      else this.configs.f_dongbo_visible = true

      let postData = {
        kyCuoc: this.params.p_kyhoadon,
        ma: keyword,
        chiTiet: 1 // tổng hợp
      }

      this.loading(true)
      this.clear()

      TraCuuPhieuTraVinaPhoneAPI.getMaTTNeoVNP(this.axios, postData)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000' &&
              res.data.data !== undefined &&
              res.data.data.length > 0) {
            if (res.data.data.length > 1) {
              // if(this.params.p_)
              this.p_thanhtoan_options = res.data.data
              this.hideTable = false
              $('#ds-thanhtoan').show()
            } else {
              this.params.p_ma_kh = res.data.data[0].MA_TT_NEO
              // this.params.p_ma_tt = res.data.data[0].MA_TT
              this.params.p_ma_tt_neo = res.data.data[0].MA_TT_NEO
              this.params.p_ma_dd = res.data.data[0].MATB_DD
              this.params.p_ten_tt = res.data.data[0].TEN_TB
              this.params.p_diachi_tt = res.data.data[0].DIACHI_TT

              this.NAP_DS_TRACUU()
            }
          } else {
            if (this.params.p_loai_timkiem === '0') this.$toast.error('Không tìm thấy mã thanh toán này')
            else if (this.params.p_loai_timkiem === '1') this.$toast.error('Không tìm thấy số mày này')
            else if (this.params.p_loai_timkiem === '2') this.$toast.error('Không tìm thấy user neo này')
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },

    NAP_DS_TRACUU () {
      this.getLSPhieuTra()
    },
    async NAP_DS_PHIEUTRA (zkieu, maTB, maKH) {
      let postData = {
        kieuTK: zkieu,
        userNeo: null,
        maTB: maTB,
        maKH: maKH,
        kyCuoc: this.params.p_kyhoadon
      }
      this.loading(true)
      TraCuuPhieuTraVinaPhoneAPI.getThongTinPhieuTra(this.axios, postData)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000' &&
              res.data.data !== undefined) {
            let arr = []
            for (let i = 0; i < res.data.data.tongHop.length; i++) {
              let item = {...res.data.data.tongHop[i]}
              for (let j = 0; j < this.dsTraVNP.length; j++) {
                if (item.PHIEU_ID_NEO === this.dsTraVNP[j].phieu_id) { item.STATUS = '1' }
              }
              arr.push(item)
            }
            this.dsTraVTT = cloneArray(arr)
            this.traVTTPageInfo.totalElement = this.dsTraVTT.length
            this.traVTTPageInfo.page = 0
            this.keyDSTraVTT++
          }
        })
        .catch((error) => {
          showError(error)
        })
        .finally(() => {
          this.loading(false)
        })
    },

    getTSMacDinh () {
      CommonsAPI.getDSThamSoMacDinhAdmin(this.axios, 'GACHNO_CHUAN')
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000' &&
              res.data.data !== undefined &&
              res.data.data.length > 0) {
            let ts = res.data.data[0].GIATRI
            this.params.p_gachno_chuan = parseInt(ts)
            if (this.params.p_gachno_chuan === -1) {
              this.$toast.error('Không lấy được phương thức gạch di động ')
              this.$router.push('/')
            }
          } else {
            this.$toast.error('Không lấy được phương thức gạch di động ')
            this.$router.push('/')
          }
        })
        .catch(error => {
          showError(error)
          // this.$router.push('/')
        })
        .finally(() => {})
    },

    getDSChuKyNo () {
      this.params.p_kyhoadon_options = []
      this.params.p_kyhoadon = null

      CommonsAPI.getDSChuKyNo(this.axios)
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000' &&
            response.data.data !== undefined &&
            response.data.data.length > 0) {
            this.params.p_kyhoadon_options = response.data.data
            this.params.p_kyhoadon = response.data.data[0].CHUKYNO
          }
        })
        .catch(error => {
          showError(error)
        })
        .finally(() => {})
    },

    getLSPhieuTra () {
      // let kyHDHienTai = moment(new Date()).subtract(1,'months').startOf('month').format('YYYYMMDD')
      // let in_kyhd = parseInt(kyHDHienTai.substring(3, 4)) * 13 + parseInt(kyHDHienTai.substr(0, 2))

      let kyhd = this.params.p_kyhoadon_options.find(x => x.CHUKYNO === this.params.p_kyhoadon)
      let postData = {
        'den_kyhoadon': kyhd.CHUKYNO_VNP,
        'denngay': null,
        'diachi_tt': null,
        'luottt': null,
        'ma_kh': this.params.p_ma_kh,
        'ma_nv': this.params.p_loai_timkiem === '2' ? this.params.p_user_neo.trim : null,
        'ma_tuyen': null,
        'page_num': 1,
        'page_rec': 100,
        'phieus': this.params.p_so_phieu,
        'so_tb': this.params.p_loai_timkiem === '1' ? '84' + this.params.p_ma_tb.trim() : null,
        'ten_tt': null,
        'tu_kyhoadon': kyhd.CHUKYNO_VNP,
        'tungay': null
      }

      this.traVNPPageInfo.totalElement = 0
      this.traVNPPageInfo.page = 0
      TraCuuPhieuTraVinaPhoneAPI.getLSPhieuTra(this.axios, postData)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000' &&
            res.data.data !== undefined &&
            res.data.data.length > 0
          ) {
            this.dsTraVNP = res.data.data
            this.keyDSTraVNP++
            this.traVNPPageInfo.totalElement = res.data.data.length
            this.traVNPPageInfo.page = 0
            if (this.params.p_loai_timkiem === '0') {
              this.NAP_DS_PHIEUTRA(1, null, this.params.p_ma_tt_neo)
            } else if (this.params.p_loai_timkiem === '1') {
              this.NAP_DS_PHIEUTRA(2, this.params.p_ma_tb, null)
            }
          } else if (res.data.message !== null) {
            this.$toast.error(res.data.message)
          }
        })
        .catch(error => {
          showError(error)
        })
        .finally(() => {})
    },

    getThangNobyVNP (key) {
      if (this.params.p_kyhoadon_options === null || this.params.p_kyhoadon_options.length <= 0) return null
      let found = this.params.p_kyhoadon_options.filter(x => x.CHUKYNO_VNP === key)

      if (found.length > 0) return found[0].THANGNO
      else return null
    },

    getLSNoTTTraPhieu (postData) {
      this.thangnoPageInfo.totalElement = 0
      this.thangnoPageInfo.page = 0
      this.thuebaoPageInfo.totalElement = 0
      this.thuebaoPageInfo.page = 0

      this.loading(true)
      TraCuuPhieuTraVinaPhoneAPI.getLSNoTTTraPhieu(this.axios, postData)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000' &&
            res.data.data !== undefined &&
            res.data.data.length > 0) {
            let result = res.data.data.reduce(function (r, a) {
              r[a.chukyno] = r[a.chukyno] || []
              r[a.chukyno].push(a)
              return r
            }, Object.create(null))

            // console.log(result);
            let arrTN = []
            let arrTB = []
            for (const [key, value] of Object.entries(result)) {
              let thangno = this.getThangNobyVNP(parseInt(key))
              if (thangno !== null) {
                let a = (
                  {
                    'chukyno': thangno,
                    'tongtra': 0,
                    'tientra': 0,
                    'trathue': 0,
                    'chihoahong': 0
                  }
                )

                value.forEach((item) => {
                  // console.log(item)
                  a.tongtra += parseInt(item.tragoc) + parseInt(item.trathue)
                  a.tientra += parseInt(item.tragoc)
                  a.trathue += parseInt(item.trathue)
                  a.chihoahong += parseInt(item.chihoahong)
                })
                arrTN.push(a)
              }
            }

            res.data.data.forEach((item) => {
              arrTB.push({
                ma_tb: item.ma_tb,
                tientra: parseInt(item.tragoc) + parseInt(item.trathue),
                tragoc: parseInt(item.tragoc),
                trathue: parseInt(item.trathue),
                chihoahong: parseInt(item.chihoahong),
                chukyno: this.getThangNobyVNP(item.chukyno)
              })
            })

            this.dsThangNo = [...arrTN]
            this.dsThueBao = [...arrTB]

            this.thangnoPageInfo.totalElement = this.dsThangNo.length
            this.thuebaoPageInfo.totalElement = this.dsThueBao.length

            this.keyDSThangNo++
            this.keyDSThueBao++
          }
        })
        .catch(error => {
          if (error.data !== undefined) this.$toast.error(error.data.message_detail)
          else console.log(error)
        })
        .finally(() => { this.loading(false) })
    },
    phieuTraVNPRecordClick (args) {
      this.dsThangNo = []
      this.dsThueBao = []

      if (args === null) return

      // console.log(args)
      // let kyhd = this.params.p_kyhoadon_options.find(x => x.CHUKYNO === this.params.p_kyhoadon)

      let postData = {
        'kyhoadon': args.rowData.chukyno,
        'phieu_id': args.rowData.phieu_id
      }

      this.getLSNoTTTraPhieu(postData)
    },

    phieuTraVNPRowChanged (args) {
      /*
      this.dsThangNo = []
      this.dsThueBao = []

      if(args === null) return

      let postData = {
        "kyhoadon": "022022",
        "phieu_id": "10"
      }

      this.getLSNoTTTraPhieu(postData)
      */
    },

    phieuTraVTTRecordClick (args) {
      this.dsThangNo = []
      this.dsThueBao = []

      if (args === null) return

      let q = this.dsTraVNP.find(x => x.phieu_id === args.rowData.PHIEU_ID_NEO)

      if (q !== null) {
        if (args === null) return

        // console.log(args)
        // let kyhd = this.params.p_kyhoadon_options.find(x => x.CHUKYNO === this.params.p_kyhoadon)

        let postData = {
          'kyhoadon': q.chukyno,
          'phieu_id': q.phieu_id
        }

        this.getLSNoTTTraPhieu(postData)
      }
    },

    getTraVTT_ThueBao (data) {

    },

    getTraVTT_ThangNo (data) {

    },

    queryCellInfoTraVNP (args) {
      if (args.column.field === 'TIENTRA') {
        args.cell.classList.add('grid-redbold')
      }
    },
    queryCellInfoChiTietThangNo (args) {
      if (args.column.field === 'TIENTRA') {
        args.cell.classList.add('grid-yellowbold')
      } else if (args.column.field === 'TRAGOC') {
        args.cell.classList.add('grid-redbold')
      } else if (args.column.field === 'TRATHUE') {
        args.cell.classList.add('grid-bluebold')
      }
    },
    queryCellInfoChiTietThueBao (args) {
      if (args.column.field === 'TIENTRA') {
        args.cell.classList.add('grid-yellowbold')
      } else if (args.column.field === 'TRAGOC') {
        args.cell.classList.add('grid-redbold')
      } else if (args.column.field === 'TRATHUE') {
        args.cell.classList.add('grid-bluebold')
      }
    },
    onChangeCustomDropDown (id) {
      const isHIdden = $(id).is(':hidden')
      if (isHIdden) {
        $(id).show()
      } else {
        $(id).hide()
      }
    },

    onChangeMaTTNeo (args) {
      // console.log(args)
      if (args === null) return

      $('#ds-thanhtoan').hide()

      this.params.p_ma_kh = args.MA_TT_NEO
      this.params.p_ma_tt = args.MA_TT_NEO
      this.params.p_ma_tt_neo = args.MA_TT_NEO
      this.params.p_ma_dd = args.MATB_DD
      this.params.p_ten_tt = args.TEN_TB
      this.params.p_diachi_tt = args.DIACHI_TT

      this.NAP_DS_TRACUU()
    },

    onChangeKyHoaDon (args) {
      if (args === null) {
        this.params.p_status = null
        return
      }

      let f = this.params.p_kyhoadon_options.filter(x => x.CHUKYNO === args.value)
      if (f.length > 0) this.params.p_status = f[0].STATUS
      else this.params.p_status = null
    }

  }
}
</script>
