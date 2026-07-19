/* eslint-disable standard/object-curly-even-spacing */
/* eslint-disable camelcase */
<template src="./TimKiemKetCuoi.html"></template>
<script>
import {debounce} from './debounce'
export default {
  components: { },
  name: 'Modal',
  props: {
    modalShow: Boolean, CurrentThueBao: Object, choPhepDoiTramTbi: Boolean, chonTramTbiTheoTB: Boolean, tramTbiId: null, KyHieuKC_New: null
  },
  computed: {

  },
  data () {
    return {
      txtTimKiem: null,
      debouncedtxtTimKiem: '',
      LockEvents: false,
      ds_TTVT: [],
      ds_TOKT: [],
      ds_TRAM: [],
      TTVT_ID: null,
      TOKT_ID: null,
      TRAM_ID: null,
      chkCAPGOC: false,
      ds_KETCUOI: [],
      ds_CAPGOC: [],
      CAP_ID: null,
      TimKetCuoi: null,
      dsAUTO_LOAD_SEARCH_KETCUOI: []
    }
  },
  methods: {
    isEmptyObj (Obj) {
      if (Obj === undefined) return true
      var isEmptyObj = !Object.keys(Obj).length
      return isEmptyObj
    },
    async TIMKIEM_DOITUONG (query) {
      try {
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/timkiem-doituong-v2', { query: query }
        )
        console.log('TIMKIEM_DOITUONG rs', rs.data)
        return rs.data
      } catch (error) {
        return null
      }
    },
    async shownModal () {
      console.log('this.ds_TTVT', this.ds_TTVT)
      console.log('this.CurrentThueBao', this.CurrentThueBao)
      if ((!this.isEmptyObj(this.CurrentThueBao) && this.CurrentThueBao !== null && this.CurrentThueBao.TRAMTBI_ID !== null) || this.tramTbiId !== -1) {
        var vtramtbi_id = this.tramTbiId
        if ((!this.isEmptyObj(this.CurrentThueBao) && this.CurrentThueBao !== null && this.CurrentThueBao.TRAMTBI_ID !== null) && this.chonTramTbiTheoTB) {
          vtramtbi_id = this.CurrentThueBao.TRAMTBI_ID
        }
        console.log('shownModal', 'TRAM|' + vtramtbi_id)
        var tbl = await this.TIMKIEM_DOITUONG('TRAM|' + vtramtbi_id)
        console.log('shownModal', tbl)
        if (tbl.dsDOITUONG.length === 0) return
        this.LockEvents = true
        if (tbl.dsTTVTS.length > 0) {
          this.ds_TTVT = tbl.dsTTVTS.map(item => {
            return {
              DONVI_ID: item.ID,
              TEN_DV: item.NAME
            }
          })
          let element = tbl.dsTTVTS.find(element => element.SELECTED === 1)
          if (element) {
            this.TTVT_ID = element.ID
          }
        }

        if (tbl.dsTOKT.length > 0) {
          console.log('dsTOKT rs', tbl.dsTOKT)
          this.ds_TOKT = tbl.dsTOKT.map(item => {
            return {
              DONVI_ID: item.ID,
              TEN_DV: item.NAME
            }
          })
          let element = tbl.dsTOKT.find(element => element.SELECTED === 1)
          if (element) this.TOKT_ID = element.ID
        }

        if (tbl.dsTONGDAI.length > 0) {
          console.log('dsTONGDAI rs', tbl.dsTONGDAI)
          this.ds_TRAM = tbl.dsTONGDAI.map(item => {
            return {
              DONVI_ID: item.ID,
              TEN_DV: item.NAME
            }
          })
          let element = tbl.dsTONGDAI.find(element => element.SELECTED === 1)
          if (element) this.TRAM_ID = element.ID
        }
        this.loadDataCapGoc()
      } else {
        if (this.KyHieuKC_New === null) return false
        if (this.KyHieuKC_New === this.TimKetCuoi) return false
        this.TimKetCuoi = this.KyHieuKC_New
        this.NAP_DS_KETCUOI()
      }
    },
    closeModal: function () {
      this.$emit('closeModal', this.modalShow)
    },
    async selected (args) {
      console.log('selected', args)
      var rowData = args.rowData
      this.$emit('selected', rowData)
      this.$emit('closeModal', this.modalShow)
    },
    async loadDataTTVT () {
      try {
        // this.loading(true)
        var rs = await this.$root.context.post(`/web-ecms/danhmuc/layDsTTVTTrongToken`)
        this.ds_TTVT = rs.data
        if (this.ds_TTVT.length > 0) {
          this.TTVT_ID = this.ds_TTVT[0].DONVI_ID
        }
      } catch (error) {
        this.$toast.error('Xảy ra lỗi')
      } finally {
        this.loading(false)
      }
    },
    ds_CAPGOC_SelectedChanged (data) {
      console.log('ds_CAPGOC_SelectedChanged', data)
      if (data !== null) { this.CAP_ID = data.CAP_ID }
    },
    async loadDataTOKT () {
      if (!this.TTVT_ID) return
      try {
        // this.loading(true)
        var rs = await this.$root.context.post(`/web-ecms/danhmuc/layDsToKT`, {'id': this.TTVT_ID})
        this.ds_TOKT = rs.data
        if (this.ds_TOKT.length > 0) {
          this.TOKT_ID = this.ds_TOKT[0].DONVI_ID
        }
      } catch (error) {
        this.$toast.error('Xảy ra lỗi')
      } finally {
        this.loading(false)
      }
    },
    async loadDataTram () {
      if (!this.TOKT_ID) return
      try {
        // this.loading(true)
        var rs = await this.$root.context.post(`/web-ecms/danhmuc/layDsVeTinh`, {'id': this.TOKT_ID})
        this.ds_TRAM = rs.data
        console.log('this.ds_TRAM ', this.ds_TRAM)
        if (this.ds_TRAM.length > 0) {
          this.TRAM_ID = this.ds_TRAM[0].DONVI_ID
        }
      } catch (error) {
        this.$toast.error('Xảy ra lỗi')
      } finally {
        this.loading(false)
      }
    },
    async loadDataKetCuoi () {
      if (!this.TRAM_ID) return
      try {
        // this.loading(true)
        var rs = await this.$root.context.get(`/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/ds-ketcuoi-theo-tram?tramtbi_id=${this.TRAM_ID}`)
        this.ds_KETCUOI = rs.data
        console.log('this.ds_KETCUOI ', this.ds_KETCUOI)
      } catch (error) {
        this.$toast.error('Xảy ra lỗi')
      } finally {
        this.loading(false)
      }
    },
    async loadDataKetCuoiTheoCapGoc () {
      if (!this.CAP_ID) return
      try {
        // this.loading(true)
        var rs = await this.$root.context.get(`/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/ds-ketcuoi-theo-capgoc?capgoc_id=${this.CAP_ID}`)
        this.ds_KETCUOI = rs.data
        console.log('this.ds_KETCUOI ', this.ds_KETCUOI)
      } catch (error) {
        this.$toast.error('Xảy ra lỗi')
      } finally {
        this.loading(false)
      }
    },
    async loadDataCapGoc () {
      if (!this.TRAM_ID) return
      try {
        // this.loading(true)
        var rs = await this.$root.context.get(`/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/ds-capgoc-theo-donvi?tramtbi_id=${this.TRAM_ID}`)
        this.ds_CAPGOC = rs.data
        console.log('this.ds_CAPGOC ', this.ds_CAPGOC)
        if (this.ds_CAPGOC.length > 0) {
          this.CAP_ID = this.ds_CAPGOC[0].CAP_ID
        }
        this.LockEvents = false
      } catch (error) {
        this.LockEvents = false
        this.$toast.error('Xảy ra lỗi')
      } finally {
        this.LockEvents = false
        this.loading(false)
      }
    },
    onChangeTTVT () {
      // this.loadDataTOKT()
    },
    async callTxtTimKiem () {
      if (this.txtTimKiem === undefined || this.txtTimKiem.length === 0) {
        this.loadDataKetCuoi()
      } else {
        try {
          var rs = await this.$root.context.get(`/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/tim-ketcuoi-theo-kyhieu?kyhieu=${this.txtTimKiem}`)
          this.ds_KETCUOI = rs.data
          console.log('this.ds_KETCUOI ', this.ds_KETCUOI)
        } catch (error) {
          this.$toast.error('Xảy ra lỗi')
        } finally {
          this.loading(false)
        }
      }
    },
    async setTxtTimKiem (value) {
      console.log('🚀 ~ file: index.vue:215 ~ setTxtTimKiem ~ value:', value)
      this.txtTimKiem = value
      if (this.txtTimKiem === undefined || this.txtTimKiem.length === 0) {
        this.loadDataKetCuoi()
      } else {
        try {
          var rs = await this.$root.context.get(`/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/tim-ketcuoi-theo-kyhieu?kyhieu=${this.txtTimKiem}`)
          this.ds_KETCUOI = rs.data
          console.log('this.ds_KETCUOI ', this.ds_KETCUOI)
        } catch (error) {
          this.$toast.error('Xảy ra lỗi')
        } finally {
          this.loading(false)
        }
      }
    },
    toggleDT () {
      this.$refs.txtTimKiem_KetCuoi.click()
    },
    queryDT: async function (P_QUERY, P_VETINH_ID) {
      try {
        var rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/auto_load_search_ketcuoi', {P_QUERY, P_VETINH_ID})
        console.log('🚀 ~ file: index.vue:218 ~ rs:', rs)
        this.dsAUTO_LOAD_SEARCH_KETCUOI = rs.data
        this.toggleDT()
      } catch (error) {
        console.log('🚀 ~ file: index.vue:222 ~ error:', error)
        this.dsAUTO_LOAD_SEARCH_KETCUOI = []
      } finally {
      }
    },
    async NAP_DS_KETCUOI () {
      if (this.TimKetCuoi === undefined || this.TimKetCuoi.length === 0) {
        this.loadDataKetCuoi()
      } else {
        try {
        // this.loading(true)
          var rs = await this.$root.context.get(`/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/tim-ketcuoi-theo-kyhieu?kyhieu=${this.TimKetCuoi}`)
          this.ds_KETCUOI = rs.data
          console.log('this.ds_KETCUOI ', this.ds_KETCUOI)
        } catch (error) {
          this.$toast.error('Xảy ra lỗi')
        } finally {
          this.loading(false)
        }
      }
    }
  },
  watch: {
    debouncedtxtTimKiem (val) {
      let txtTimKiem = val.trim()
      if (txtTimKiem.length > 2) {
        console.log('🚀 ~ file: index.vue:236 ~ debouncedtxtTimKiem ~ txtTimKiem:', txtTimKiem)
        this.queryDT(txtTimKiem, this.TRAM_ID)
      }
    },
    txtTimKiem: debounce(function (newVal) {
      this.debouncedtxtTimKiem = newVal
    }, 1000),
    TTVT_ID: function (val) {
      if (this.LockEvents) return
      console.log('watch TTVT_ID', val)
      this.TimKetCuoi = null
      this.loadDataTOKT()
    },
    TOKT_ID: function (val) {
      if (this.LockEvents) return
      console.log('watch TOKT_ID', val)
      this.TimKetCuoi = null
      this.loadDataTram()
    },
    TRAM_ID: function (val) {
      if (this.LockEvents) return
      console.log('watch TRAM_ID', val)
      this.TimKetCuoi = null
      if (this.chkCAPGOC) {
        this.loadDataCapGoc()
      } else {
        this.loadDataKetCuoi()
      }
    },
    CAP_ID: function (val) {
      if (this.LockEvents) return
      console.log('watch CAP_ID', val)
      this.TimKetCuoi = null
      this.loadDataKetCuoiTheoCapGoc()
    },
    chkCAPGOC: function (val) {
      console.log('watch chkCAPGOC', val)
      this.TimKetCuoi = null
      if (val) {
        this.loadDataCapGoc()
      } else {
        this.loadDataKetCuoi()
      }
    }
  },
  created () {

  },

  async mounted () {
    await this.loadDataTTVT()
    console.log('this.ds_TTVT', this.ds_TTVT)
  }
}
</script>

<style>

</style>
