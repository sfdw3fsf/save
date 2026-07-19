<template src="./index.template.html"></template>
<script>
export default {
  name: 'ChonTramThietBi',
  props: ['modalID'],
  computed: {
    getDsTram () {
      console.log('getDsTram')
      if (this.xemchitiet === true) {
        let arr = []
        for (let index = 0; index < this.dsTram.length; index++) {
          const element = this.dsTram[index]
          arr.push({
            DONVI_ID: element.DONVI_ID,
            TEN_DV: element.TEN_DV,
            DIACHI_DV: element.DIACHI_DV,
            THONGTIN_BS: element.THONGTIN_BS !== null ? element.THONGTIN_BS.replaceAll('\r\n', '<BR/>') : null
          })
        }
        return arr
      } else {
        let arr = []
        for (let index = 0; index < this.dsTram.length; index++) {
          const element = this.dsTram[index]
          const THONGTIN_BS = element.THONGTIN_BS
          let T_THONGTIN_BS = null
          if (THONGTIN_BS !== null) {
            let arrTHONGTIN_BS = THONGTIN_BS.split('\r\n')
            if (arrTHONGTIN_BS.length > 0) {
              T_THONGTIN_BS = arrTHONGTIN_BS[0]
            }
          }
          arr.push({
            DONVI_ID: element.DONVI_ID,
            TEN_DV: element.TEN_DV,
            DIACHI_DV: element.DIACHI_DV,
            THONGTIN_BS: T_THONGTIN_BS
          })
        }
        return arr
      }
    }
  },
  created: async function () {

  },
  watch: {
    'dataSelected.toql': function (val) {
      this.getTram()
    },
    'dataSelected.ttvt' (val) {
      this.getToQL()
    }
  },
  methods: {
    show (data) {
      return new Promise((resolve, reject) => {
        this.data = data
        this.$bvModal.show(this.modalID)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    focusMyElement: async function () {
      try {
        this.tramSelected = null
        this.dsTram = []
        this.dsToQL = []
        this.dsTTVT = []
        this.dataSelected = {
          ttvt: 0,
          toql: 0
        }
        await this.getTTVT()
      } catch (er) {} finally {
      }
    },
    getTTVT: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'VIEW_DONVI', option: ''}
        )
        this.dsTTVT = rs.data
        if (this.dsTTVT.length > 0) {
          this.dataSelected.ttvt = this.dsTTVT[0].DONVI_ID
        } else {
          this.dataSelected.ttvt = 0
        }
      } catch (error) {
      } finally {
      }
    },
    getTram: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', {loai: 'DS_TRAM', option: this.dataSelected.toql}
        )
        this.dsTram = rs.data
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    getToQL: async function () {
      try {
        var rs = await this.$root.context.post(
          '/web-ecms/danhmuc/layDsToKTTheoNVTrongTokenVaDonVi',
          { id: this.dataSelected.ttvt }
        )
        this.dsToQL = rs.data
        if (this.dsToQL.length > 0) {
          this.dataSelected.toql = this.dsToQL[0].DONVI_ID
        } else {
          this.dataSelected.toql = 0
        }
      } catch (error) {
      } finally {
      }
    },
    onChangeTTVT: async function () {
      await this.getToQL()
    },
    grid_selectedRowChanged: async function (dataItem) {
      this.tramSelected = dataItem
    },
    chonTramThietBi: async function () {
      this.dsTram = []
      this.resolvePromise({ok: true, selectedRow: this.tramSelected})
      this.$bvModal.hide(this.modalID)
    },
    huyBo () {
      this.tramSelected = null
      this.dsTram = []
      this.resolvePromise({ok: false, selectedRow: null})
      this.$bvModal.hide(this.modalID)
    }
  },
  data: function () {
    return {
      dsTTVT: [],
      dsToQL: [],
      dsTram: [],
      dataSelected: {
        ttvt: 0,
        toql: 0
      },
      tramSelected: null,
      xemchitiet: false,
      data: null,
      resolvePromise: null,
      rejectPromise: null
    }
  }
}
</script>
<style>
div#popupChonTramThietBi___BV_modal_outer_{
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disabled {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
