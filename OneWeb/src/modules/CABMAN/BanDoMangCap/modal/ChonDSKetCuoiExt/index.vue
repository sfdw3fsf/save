/* eslint-disable standard/object-curly-even-spacing */
/* eslint-disable camelcase */
<template src="./index.template.html"></template>
<script>
import XLSX from 'xlsx'
export default {
  components: { },
  name: 'ModalChonDSKetCuoi',
  props: {
    modalID: null
  },
  computed: {
    getTitle () {
      if (this.text === null) {
        switch (this.kieuId) {
          case 0:
            return 'Chọn danh sách kết cuối đã đấu cáp'
          case 1:
            return 'Chọn danh sách kết cuối chưa đấu cáp'
          case 2:
            return 'Chọn danh sách kết cuối'
        }
      } else {
        return this.text
      }
    },
    getDS_KETCUOI () {
      if (this.XEMCHITIET === true) {
        let arr = []
        for (let index = 0; index < this.DS_KETCUOI.length; index++) {
          const element = this.DS_KETCUOI[index]
          arr.push({
            KETCUOI_ID: element.KETCUOI_ID,
            KYHIEU: element.KYHIEU,
            TEN_KC: element.TEN_KC,
            DIACHI: element.DIACHI,
            THONGTIN_BS: element.THONGTIN_BS !== null ? element.THONGTIN_BS.replaceAll('\r\n', '<BR/>') : null
          })
        }
        console.log('this.getDS_KETCUOI', arr)
        return arr
      } else {
        let arr = []
        for (let index = 0; index < this.DS_KETCUOI.length; index++) {
          const element = this.DS_KETCUOI[index]
          const THONGTIN_BS = element.THONGTIN_BS
          let T_THONGTIN_BS = null
          if (THONGTIN_BS !== null) {
            let arrTHONGTIN_BS = THONGTIN_BS.split('\r\n')
            if (arrTHONGTIN_BS.length > 0) {
              T_THONGTIN_BS = arrTHONGTIN_BS[0]
            }
          }
          arr.push({
            KETCUOI_ID: element.KETCUOI_ID,
            KYHIEU: element.KYHIEU,
            TEN_KC: element.TEN_KC,
            DIACHI: element.DIACHI,
            THONGTIN_BS: T_THONGTIN_BS
          })
        }
        return arr
      }
    }
  },
  data () {
    return {
      DS_TTVT: [],
      DONVI_ID: null,
      DS_TOKT: [],
      TOKT_ID: null,
      DS_LOAI_KC: [],
      LOAIKC_ID: -1,
      DS_DUNGLUONG_KC: [],
      DUNGLUONG_ID: -1,
      KYHIEU: '',
      TENKETCUOI: '',
      optionTimKiem: 1,
      DS_KETCUOI: [],
      XEMCHITIET: false,
      selectedRow: null,
      kieuId: null,
      resolvePromise: null,
      rejectPromise: null,
      DS_TRAMTBI: [],
      TRAMTBI_ID: -1,
      EnabledcboTram: false,
      isDisableBtn: true,
      text: null
    }
  },
  methods: {
    show (kieuId) {
      return new Promise((resolve, reject) => {
        this.kieuId = kieuId
        this.$bvModal.show(this.modalID)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    show2 (kieuId, donvi_id, tokt_id, tramtbi_id) {
      console.log('🚀 ~ file: index.vue:102 ~ show2 ~ kieuId, donvi_id, tokt_id, tramtbi_id:', kieuId, donvi_id, tokt_id, tramtbi_id)
      return new Promise((resolve, reject) => {
        this.kieuId = kieuId
        this.DONVI_ID = donvi_id
        this.TOKT_ID = tokt_id
        this.TRAMTBI_ID = tramtbi_id
        this.$bvModal.show(this.modalID)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    showWithText (kieuId, text) {
      return new Promise((resolve, reject) => {
        this.kieuId = kieuId
        this.text = text
        this.$bvModal.show(this.modalID)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    resetModal () {
      this.selectedRow = null
      this.DS_KETCUOI = []
    },
    grid_onCommandClicked (data) {},
    grid_onRowDoubleClicked (data) {},
    grid_onSelectedRowChanged (row) {
      console.log('grid_onSelectedRowChanged', row.data)
      this.selectedRow = row.data
    },
    closeModal: function () {
      this.resolvePromise({ok: false, selectedRow: null})
      this.$bvModal.hide(this.modalID)
    },
    onChonKetCuoi () {
      this.resolvePromise({ok: true, selectedRow: this.selectedRow})
      this.$bvModal.hide(this.modalID)
    },
    onHuyBo () { this.closeModal() },
    xuatExcel: async function () {
      try {
        var ma_thuebao = XLSX.utils.json_to_sheet(Object.values(this.DS_KETCUOI))
        var wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, ma_thuebao, 'DS') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'export.xlsx')
      } catch (ex) {
        this.$toast.error('Có lỗi trong quá trình xuất file Excel !' + ex)
      }
    },
    setDisableBtn: function (isHasData) {
      this.isDisableBtn = !isHasData
    },
    async onTraCuu () {
      try {
        let rs = []
        this.loading(true)
        let data = {
          P_TIMCHINHXAC: this.optionTimKiem,
          P_LOAIKC_ID: this.LOAIKC_ID,
          P_DUNGLUONG: this.DUNGLUONG_ID,
          P_TEN_KC: this.TENKETCUOI.trim(),
          P_KYHIEU: this.KYHIEU.trim(),
          P_TTVT_ID: this.DONVI_ID,
          P_TOKT_ID: this.TOKT_ID,
          P_TRAMTBI_ID: this.TRAMTBI_ID
        }
        switch (this.kieuId) {
          case 0:
            rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/ds-ketcuoi-da-noi-cap', data)
            break
          case 1:
            rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/ds-ketcuoi-chua-noi-cap', data)
            break
          default:
            rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/ds-ketcuoi-all', data)
            break
        }
        this.DS_KETCUOI = rs.data
        this.setDisableBtn(rs.data !== null && rs.data.length > 0)
        this.loading(false)
      } catch (error) {
        this.loading(false)
        console.log('onTraCuu', error)
      }
    },
    async NAP_DS_TTVT () {
      try {
        var rs = await this.$root.context.post('/web-ecms/danhmuc/layDsTTVTTrongToken')
        this.DS_TTVT = [{
          DONVI_ID: 0,
          TEN_DV: '--- Tất cả ---'
        }, ...rs.data]
        this.DONVI_ID = 0
        console.log('NAP_DS_TTVT', this.DS_TTVT)
      } catch (error) {
        console.log('NAP_DS_TTVT', error)
      }
    },
    async NAP_DS_TOKT (DONVI_ID) {
      try {
        var rs = await this.$root.context.post('/web-ecms/danhmuc/layDsToKT', {id: DONVI_ID})
        this.DS_TOKT = [{
          DONVI_ID: 0,
          TEN_DV: '--- Tất cả ---'
        }, ...rs.data]
        this.TOKT_ID = 0
      } catch (error) {
        console.log('NAP_DS_TOKT', error)
      }
    },
    async NAP_DS_TRAM (DONVI_ID) {
      try {
        var rs = await this.$root.context.post('/web-ecms/danhmuc/layDsVeTinhTheoToQl', {id: DONVI_ID})
        this.DS_TRAMTBI = [{
          DONVI_ID: 0,
          TEN_DV: '--- Tất cả ---'
        }, ...rs.data]
        this.TRAMTBI_ID = 0
      } catch (error) {
        console.log('NAP_DS_TRAM', error)
      }
    },
    async NAP_DS_LOAI_KC () {
      try {
        var rs = await this.$root.context.get('/web-cabman/danhmuc/layDsLoaiKetCuoi')
        this.DS_LOAI_KC = [...rs.data]
        this.LOAIKC_ID = -1
      } catch (error) {
        console.log('NAP_DS_LOAI_KC', error)
      }
    },
    async NAP_DS_DUNGLUONG_KC () {
      try {
        var rs = await this.$root.context.get('/web-cabman/danhmuc/layDsDungLuongKetCuoi')
        this.DS_DUNGLUONG_KC = [...rs.data]
        this.DUNGLUONG_ID = -1
      } catch (error) {
        console.log('NAP_DS_DUNGLUONG_KC', error)
      }
    },
    async loadData () {
      try {
        this.NAP_DS_TTVT()
        this.NAP_DS_LOAI_KC()
        this.NAP_DS_DUNGLUONG_KC()
      } catch (error) {
        console.log('loadData error', error)
      } finally {
      }
    }
  },
  watch: {
    modalShowChonDSKetCuoi (val) {
      if (val) {
        this.DS_KETCUOI = []
      }
    },
    DONVI_ID (val) {
      if (val !== null) this.NAP_DS_TOKT(val)
    },
    TOKT_ID (val) {
      if (val !== null) this.NAP_DS_TRAM(val)
    }
  },
  created () {

  },
  async mounted () {
    this.loadData()
  }
}
</script>

<style>
.e-grid .e-headercell.customcss{
    background-color: #BAE7FF;
    color: #000;
    font-family: 'Roboto', Arial;
    font-size: 14px;
    padding: 5px 7px;
}
.e-grid *{
  font-size :14px; ;
  text-align: justify;
}
.e-grid .e-altrow {
  background-color:  #efefef;
}
.e-grid .e-groupdroparea.e-grouped {
  background-color: red ;
}
.e-grid .e-groupheadercell {
  background-color: coral ;
}
</style>
