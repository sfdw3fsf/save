<template src="./SearchDigitizedDataCollection.html"></template>
<script>
import Vue from 'vue'
import api from './api.js'
import { GridPlugin, Sort, Filter, Page, GridComponent } from '@syncfusion/ej2-vue-grids'
Vue.use(GridPlugin)
import { Tooltip } from '@syncfusion/ej2-popups'
import XLSX from 'xlsx'
export default {
  components: {},
  data() {
    return {
      type: null,
      enableExportExcel: false,
      typeGrid: [],
      resultGrid: {
        columns: [],
        columns1: [
          {
            fieldName: 'ma_tt',
            headerText: 'MA_TT',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'so_dt',
            headerText: 'SO_DT',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'tongtien',
            headerText: 'TONGTIEN',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'no_dk',
            headerText: 'NO_DK',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'no_ps',
            headerText: 'NO_PS',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'kyhoadon',
            headerText: 'KYHOADON',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'loaitin_id',
            headerText: 'LOAITIN_ID',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          }
        ],
        columns2: [
          {
            fieldName: 'ma_tt',
            headerText: 'MA_TT',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'so_dt',
            headerText: 'SO_DT',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'cuoc_pt',
            headerText: 'CUOC_PT',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          }
        ],
        columns3: [
          {
            fieldName: 'thanhtoan_id',
            headerText: 'THANHTOAN_ID',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'ma_tt',
            headerText: 'MA_TT',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'so_dt',
            headerText: 'SO_DT',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'email',
            headerText: 'EMAIL',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'nodk_tt',
            headerText: 'NODK_TT',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'nops_tt',
            headerText: 'NOPS_TT',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'file_ctc',
            headerText: 'FILE_CTC',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          }
        ],
        columns4: [
          {
            fieldName: 'thanhtoan_id',
            headerText: 'THANHTOAN_ID',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'so_dt',
            headerText: 'SO_DT',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'email',
            headerText: 'EMAIL',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'nops_tt',
            headerText: 'NOPS_TT',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'file_ctc',
            headerText: 'FILE_CTC',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          }
        ],
        columns5: [
          {
            fieldName: 'ma_tt',
            headerText: 'MA_TT',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'so_dt',
            headerText: 'SO_DT',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'tienno',
            headerText: 'TIENNO',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          }
        ],
        columns6: [
          {
            fieldName: 'ma_tt',
            headerText: 'MA_TT',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'so_dt',
            headerText: 'SO_DT',
            allowfiltering: true,
            allowSorting: true,
            width: '100'
          }
        ],
        dataSource: []
      }
    }
  },
  async mounted() {
    await this.loadData()
  },
  provide: {
    grid: [Filter, Sort, Page]
  },
  methods: {
    async btnSyncOnClicked() {
      if (this.type) {
        await this.handlerSyncData()
        if (this.enableExportExcel) {
          this.handlerExportExcel()
        }
      }
    },
    btnExportFileOnClicked() {
      this.handlerExportExcel()
    },
    async onTypeChanged(val) {
      // await this.handlerSyncData()
    },
    async loadData() {
      var data = await api.lay_nguon_dl_doisoat_sohoa_thucuoc(this.axios)
      this.typeGrid = data.data.data
    },
    handlerExportExcel() {
      this.loading(true)
      var exportData = []
      exportData = this.resultGrid.dataSource.map((e) => {
        var exportEntity = {}
        this.resultGrid.columns.map((x) => {
          exportEntity[x.fieldName] = e[x.fieldName]
        })
        return exportEntity
      })
      if (exportData.length > 0) {
        let worksheet = XLSX.utils.json_to_sheet(exportData)
        let workbook = XLSX.utils.book_new()
        XLSX.utils.book_append_sheet(workbook, worksheet, 'Dữ liệu')
        XLSX.writeFile(workbook, 'export.xlsx')
      } else {
        this.$toast.warning('Không có dữ liệu để xuất file!')
      }
      this.loading(false)
    },
    async handlerSyncData() {
      this.loading(true)
      switch (this.type) {
        case '1':
          this.resultGrid.columns = this.resultGrid.columns1
          break
        case '2':
          this.resultGrid.columns = this.resultGrid.columns2
          break
        case '3':
          this.resultGrid.columns = this.resultGrid.columns3
          break
        case '4':
          this.resultGrid.columns = this.resultGrid.columns4
          break
        case '5':
          this.resultGrid.columns = this.resultGrid.columns5
          break
        case '6':
          this.resultGrid.columns = this.resultGrid.columns6
          break
      }
      var data = await api.lay_dl_doisoat_sohoa_thucuoc(this.axios, { id: this.type })
      this.resultGrid.dataSource = data.data.data

      // if (this.resultGrid.dataSource.length > 0) {
      //   var jsonFields = Object.keys(this.resultGrid.dataSource[0])
      //   jsonFields = jsonFields.filter((e) => e != 'phanvung_id')
      //   if (this.type == 1) {

      //   }
      //     jsonFields.map((e) => {
      //       this.resultGrid.columns.push({
      //         fieldName: e,
      //         headerText: e.toUpperCase(),
      //         allowFiltering: true,
      //         allowSorting: true,
      //         width: '100'
      //       })
      //     })
      // }
      this.loading(false)
    }
  }
}
</script>
<style scoped>
.non-ative a {
  color: #d3d3d3 !important;
}
.ejs-grid td.e-active {
  background-color: #887f6c;
}

.inline-form {
  display: flex !important;
  justify-content: center;
  align-items: center;
}
</style>
