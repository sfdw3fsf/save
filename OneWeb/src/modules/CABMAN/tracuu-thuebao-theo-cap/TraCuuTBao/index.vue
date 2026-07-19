
<template src="./index.template.html"></template>
<script>
import XLSX from 'xlsx'
import DanDoCapThueBao from '../tracuu-dandocap-theothuebao'
import {debounce} from './debounce'
export default {
  components: {DanDoCapThueBao},
  name: 'TraCuuTBao',
  props: {
    doiTuong: Object,
    id: String
  },
  data () {
    return {
      key: 1,
      isShown: false,
      cap: null,
      show: false,
      dsThueBao: [],
      selectedItem: null,
      isDisableBtnNhapMoi: true,
      isDisableBtn: true,
      dataExcelEx: [],
      CURRENT_CAP: {},
      dataSearch: [],
      valueLoaiDT: 2,
      input: {
        denDoiSoi: 1,
        tuDoiSoi: 1,
        kyhieuCap: '',
        cboToKT: [],
        cboTram: [],
        cboTTVT: []
      },

      fields: {value: 'OBJ_NAME'},
      waterMark: 'Nhập thông tin ký hiệu ...',
      query: '',
      debouncedtxtTimKiem: '',
      searchquerys: '',
      ma_thuebao: ''
    }
  },
  computed: {

  },
  watch: {
    debouncedtxtTimKiem (val) {
      let txtTimKiem = val.trim()
      if (txtTimKiem.length > 0) {
        var typeList = '1,2,3'
        if (this.valueLoaiDT !== 0) {
          typeList = this.valueLoaiDT.toString()
        }
        this.queryDT(txtTimKiem, typeList)
      }
    },
    query: debounce(function (newVal) {
      this.debouncedtxtTimKiem = newVal
    }, 500)

  },
  methods: {
    showSpinner (args) {
      console.log(args)
    },
    setTxtTimKiem: async function (value, searchquery, loai) {
      this.query = value
      this.searchquerys = searchquery
      this.TIMKIEM_DOITUONG(searchquery, loai)
      var searchDatas = this.searchquerys.split('|')
      console.log('searchDatas[0] ', searchDatas[0])
      if (searchDatas[0] != 'CAP') {
        this.$root.toastError('Đối tượng ' + searchDatas + ' chưa được hỗ trợ')
        return
      }
      if (await this.LAY_THONGTIN_CAP(searchDatas[1])) {
        await this.traCuu()
      }
    },
    showPopup: async function () {
      this.$bvModal.show('dandocap')
    },
    TIMKIEM_DOITUONG (searchquery, loai) {
      var query = searchquery + '|NGUOIDUNG_ID=' + this.$root.token.getNguoiDungID()
      console.log('TIMKIEM_DOITUONG', query)
    },
    queryDT: async function (txtTimKiem, typeList) {
      try {
        // this.loading(true)
        var rs = await this.$root.context.post(
          '/web-cabman/thueBaoCapKetCuoi/goiNhacTimKiem', {searchQuery: txtTimKiem, types: typeList}
        )
        this.dataSearch = rs.data
      } catch (error) {
      } finally {
        // this.loading(false)
      }
    },
    onHiddenModal () {
      this.isShown = false
      this.show = false
      this.key++
      this.dataSearch = []
    },
    async onShownModal () {
      this.isShown = true
      this.show = true
      this.dataSearch = []
      this.dsThueBao = []
      this.input.tuDoiSoi = 0
      this.input.denDoiSoi = 0
      this.query = ''

      try {
        this.loading(true)
        if (await this.LAY_THONGTIN_CAP(this.doiTuong.cap_id)) {
          await this.traCuu()
        }
      } catch (e) {} finally {
        this.loading(false)
      }
    },
    setDisableBtn: function (isHasData) {
      this.isDisableBtn = !isHasData
    },
    onClickItem: async function () {
    },
    traCuu: async function () {
      try {
        this.loading(true)
        this.dsThueBao = []
        let vloai_dt = 0
        let vdoituong_id = 0
        vloai_dt = 1
        vdoituong_id = this.CURRENT_CAP.CAP_ID
        let data = {
          loaidt_id: vloai_dt,
          doituong_id: vdoituong_id,
          vitri_bd: this.input.tuDoiSoi,
          vitri_kt: this.input.denDoiSoi
        }
        var rs = await this.$root.context.post('web-cabman/thueBaoCapKetCuoi/dsthuebaocapketcuoi', data)
        if (rs.error_code !== 'BSS-00000000') {
          this.$root.toastError(rs.message_detail)
          this.dsThueBao = []
          return
        }
        this.dsThueBao = rs.data
        this.setDisableBtn(rs.data !== null && rs.data.length > 0)
      } catch (error) {
        this.$root.toastError(' ' + error)
      } finally {
        this.loading(false)
      }
    },
    btnKyHieuCap: async function () {
      if (this.query === null) { return }

      var searchDatas = this.searchquerys.split('|')
      console.log('searchDatas[0] ', searchDatas[0])
      if (searchDatas[0] != 'CAP') {
        this.$root.toastError('Đối tượng ' + searchDatas + ' chưa được hỗ trợ')
        return
      }
      if (await this.LAY_THONGTIN_CAP(searchDatas[1])) {
        await this.traCuu()
      }
    },
    btnDanDo: function (name, args) {
      if (name == 'xoa') {
        this.ma_thuebao = args.MA_TB
        this.$bvModal.show('dandocap')
      }
    },
    xuatExcel: async function () {
      try {
        var ma_thuebao = XLSX.utils.json_to_sheet(Object.values(this.dsThueBao))
        var wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, ma_thuebao, 'DS') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'export.xlsx')
      } catch (ex) {
        this.$toast.error('Có lỗi trong quá trình xuất file Excel !' + ex)
      }
    },
    LAY_THONGTIN_CAP: async function (cap_id) {
      this.CURRENT_CAP = {}
      let data = {
        id: cap_id
      }
      var rs = await this.$root.context.post('/web-cabman/thueBaoCapKetCuoi/layCapTheoID', data)
      console.log('LAY_THONGTIN_CAP', rs)
      if (rs.error_code !== 'BSS-00000000') {
        this.$root.toastError(rs.message_detail)
        return false
      }
      if (rs.data.length === 0) {
        this.$root.toastError('Không tìm thấy cáp có ký hiệu ' + this.input.kyhieuCap)
        return false
      } else {
        this.CURRENT_CAP = rs.data
        if (this.CURRENT_CAP.SOI_BD === 0 || this.CURRENT_CAP.SOI_BD === null) {
          this.input.tuDoiSoi = 1
        } else {
          this.input.tuDoiSoi = this.CURRENT_CAP.SOI_BD
        }
        try {
          this.input.denDoiSoi = (Number(this.input.tuDoiSoi) - 1) + Number(this.CURRENT_CAP.SODOIDAY)
        } catch (error) {
          this.input.tuDoiSoi = 1
          this.input.denDoiSoi = 1
        }
        if (this.query === '') { this.query = this.CURRENT_CAP.KYHIEU }
      }
      return true
    }
  },
  async created () {

  }
}
</script>
<style>
a.disabled {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
