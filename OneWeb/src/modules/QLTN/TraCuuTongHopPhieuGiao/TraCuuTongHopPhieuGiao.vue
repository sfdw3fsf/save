<template src="./TraCuuTongHopPhieuGiao.html"></template>
<script>
import Vue from 'vue'
import moment from 'moment'
import KyCuoc from '@/components/KyCuoc'
import TraCuuAPI from '../api/TraCuuAPI'
import EventBus from '@/utils/eventBus'
import ModalExport from '../NhanPhieu/components/modal-export'
import { Group, Page, Sort, Aggregate, ExcelExport } from '@syncfusion/ej2-vue-grids'

export default Vue.extend({
  components: { appKyCuoc: KyCuoc, ModalExport },
  created () {
    EventBus.$on('chukycuocChange', this.chukycuocChangeHandler)
  },
  mounted () {
  },
  destroyed () {
    EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
  },
  computed: {
    getColumns () {
      return [...this.firstColumns, ...this.secondColumns, ...this.thirdColumns]
    }
  },
  data () {
    return {
      groupSettings: {
        showDropArea: false,
        columns: ['TEN_NV']
      },
      captionTemplate: '<div>${key}</div>',
      firstColumns: [
        {
          field: 'TEN_NV',
          headerText: 'NVTC',
          headerTextAlign: 'Center',
          width: 150,
          textAlign: 'Left',
          minWidth: 10,
          allowFiltering: true
          // isGroupedColumn: true,
          // captionTemplate: this.captionTemplate
        },
        {
          field: 'GIAOPHIEU_ID',
          headerText: 'Lượt giao',
          headerTextAlign: 'Center',
          width: 120,
          minWidth: 80,
          allowFiltering: true
        },
        {
          field: 'NGAYGIAO',
          headerText: 'Ngày giao',
          headerTextAlign: 'Center',
          width: 150,
          textAlign: 'Right',
          minWidth: 80,
          allowFiltering: true
        },
        {
          field: 'SOLUONG_GBC',
          headerText: 'Giấy báo giao',
          headerTextAlign: 'Center',
          width: 150,
          textAlign: 'Right',
          minWidth: 100,
          format: 'N0',
          type: 'number',
          allowFiltering: true
        },
        {
          field: 'SOLUONG_HD',
          headerText: 'Hóa đơn giao',
          headerTextAlign: 'Center',
          width: 160,
          textAlign: 'Right',
          minWidth: 80,
          format: 'N0',
          type: 'number',
          allowFiltering: true
        },
        {
          field: 'TONGTIEN_HD',
          headerText: 'Tiền hóa đơn giao',
          headerTextAlign: 'Center',
          width: 180,
          textAlign: 'Right',
          minWidth: 80,
          format: 'N0',
          type: 'number',
          allowFiltering: true
        },
        {
          field: 'HOADON_TON',
          headerText: 'Hơn đơn tồn',
          headerTextAlign: 'Center',
          width: 150,
          textAlign: 'Right',
          minWidth: 80,
          format: 'N0',
          type: 'number',
          allowFiltering: true
        }
      ],
      secondColumns: [
        {
          field: 'HENTRA',
          headerText: 'Hẹn trả',
          headerTextAlign: 'Center',
          width: 150,
          textAlign: 'Right',
          minWidth: 80,
          format: 'N0',
          type: 'number'
        },
        {
          field: 'TRA_GB',
          headerText: 'Trả giấy báo',
          width: 150,
          headerTextAlign: 'Center',
          textAlign: 'Right',
          minWidth: 80,
          format: 'N0',
          type: 'number'
        },
        {
          field: 'TRA_HD',
          headerText: 'Trả hóa đơn',
          width: 150,
          headerTextAlign: 'Center',
          textAlign: 'Right',
          minWidth: 80,
          format: 'N0',
          type: 'number'
        },
        {
          field: 'TRA_TB',
          headerText: 'Trả trọn bộ',
          width: 150,
          headerTextAlign: 'Center',
          textAlign: 'Right',
          minWidth: 80,
          format: 'N0',
          type: 'number'
        }
      ],
      thirdColumns: [
        {
          field: 'GBC_PHAT',
          headerText: 'Giấy báo phát được',
          width: 190,
          headerTextAlign: 'Center',
          textAlign: 'Right',
          minWidth: 80,
          format: 'N0',
          type: 'number'
        },
        {
          field: 'TILE_PHAT',
          headerText: 'Tỷ lệ phát',
          width: 150,
          headerTextAlign: 'Center',
          textAlign: 'Right',
          minWidth: 80,
          format: 'N2',
          type: 'number'
        },
        {
          field: 'HD_THU',
          headerText: 'Hóa đơn thu',
          width: 150,
          headerTextAlign: 'Center',
          textAlign: 'Right',
          minWidth: 80,
          format: 'N0',
          type: 'number'
        },
        {
          field: 'TILE_THU',
          headerText: 'Tỷ lệ thu',
          width: 150,
          headerTextAlign: 'Center',
          textAlign: 'Right',
          minWidth: 80,
          format: 'N2',
          type: 'number'
        }
      ],
      aggregates: [
        {field: 'SOLUONG_GBC', type: 'Sum', format: 'N0', groupFooterTemplate: this.footerSum},
        {field: 'SOLUONG_HD', type: 'Sum', format: 'N0', groupFooterTemplate: this.footerSum},
        {field: 'TONGTIEN_HD', type: 'Sum', format: 'N0', groupFooterTemplate: this.footerSum},
        {field: 'HOADON_TON', type: 'Sum', format: 'N0', groupFooterTemplate: this.footerSum}
        /* {field: 'HENTRA', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
          {field: 'TRA_GB', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
          {field: 'TRA_HD', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
          {field: 'TRA_TB', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
          {field: 'GBC_PHAT', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
          {field: 'TILE_PHAT', type: 'Average', format: 'N2', footerTemplate: this.footerAvg},
          {field: 'HD_THU', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
          {field: 'TILE_THU', type: 'Average', format: 'N2', footerTemplate: this.footerAvg},
          */
      ],
      selectionOptions: {
        type: 'Single'
      },
      // footerSum: function() {
      //   return {
      //       template: Vue.component('sumTemplate', {
      //       template: ` <span> Total units: {{data.Sum}}</span>`,
      //       data: function () {return {data: {}};}
      //       })
      //   }
      // },
      sumTemplate: function () {
        return {
          template: Vue.component('sumTemplate', {
            template: ` <span>{{data.Sum}}</span>`,
            data: function () { return {data: {}} }
          })
        }
      },
      footerSum: function () {
        return { template: Vue.component('sumTemplate', {
          template: `<span>{{data.Sum}}</span>`,
          data () { return { data: {} } }
        })
        }
      },
      footerCount: function () {
        return { template: Vue.component('countTemplate', {
          template: `<span>{{data.Count}}</span>`,
          data () { return { data: {} } }
        })
        }
      },
      // footerAvg: function () {
      //   return  { template : Vue.component('averageTemplate', {
      //     template: `<span>{{data.Average}}</span>`,
      //     data () {return { data: {}};}
      //     })
      //   }
      // },
      p_kycuoc: null,
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
      keyDSLuotGiao: 0
    }
  },
  methods: {
    exportExcel () {
      // console.log(this.$refs.grdTHPhieuGiao)
      if (this.results.length <= 0) {
        this.$toast.error('Không tìm thấy dữ liệu đề xuất file excel.')
        return false
      }
      // let excelExportProperties = {
      //   // columns: columns,
      //   dataSource: this.results
      // }
      // this.$refs.gridData.excelExport(excelExportProperties)
      // this.columnsExport = this.GRID_COLUMS[this.currentTab]
      this.$bvModal.show('modal-export')
    },
    validateData: function () {
      let error = false
      if (this.p_kycuoc === null) {
        this.$toast.error('Trường chu kỳ bắt buộc nhập.')
        error = true
      }
      return error
    },
    onSearch: function () {
      if (this.validateData()) return false

      this.loading(true)
      this.results = []

      TraCuuAPI.getTraCuuTongHopPhieuGiao(this.axios, this.p_kycuoc)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
              this.results = response.data.data
              this.pageinfo.totalElement = response.data.data.length
              this.pageinfo.totalPages = 1
              this.pageinfo.page = 1
            } else {
              this.$toast.error('Không có dữ liệu, vui lòng thử lại.')
            }
          }
        )
        .catch(error => {
          console.log(error)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    activeRow (args) {
      this.rowActiveHandler(parseInt(args.rowIndex + 1))
    },
    actionComplete: function (args) {
      if (args.requestType === 'refresh' && this.results.length > 0) {
        // EventBus.$emit('rowActive', 1)
        this.rowActiveHandler(1)
      }
    },
    dataBound: function (args) {
      console.log(this.$refs.grdTHPhieuGiao.ej2Instances)
      /*
         for (var i = 0; i < this.$refs.grdTHPhieuGiao.ej2Instances.columns.length; i++) {
            //if (this.$refs.grid.ej2Instances.columns[i].type === "date") {
            //    this.$refs.grid.ej2Instances.columns[i].format = { type: "date", format: "dd/MM/yyyy" };
            // }
            this.$refs.grdTHPhieuGiao.ej2Instances.columns[5].format = { format:'C5' , useGrouping: false, minimumSignificantDigits:1, maximumSignificantDigits:3}
        }
        */
      this.$refs.grdTHPhieuGiao.ej2Instances.columns[1].columns[4].format = { format: 'C5', useGrouping: false, minimumSignificantDigits: 1, maximumSignificantDigits: 3}
      // this.$refs.grdTHPhieuGiao.ej2Instances.refreshColumns();
    },
    chukycuocChangeHandler (e) {
      if (e.p_chuky_no === null) { this.p_kycuoc = null } else { this.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no }
    },
    onChangePage (e) {
      this.pageinfo.page = e.currentPage - 1
      this.pageinfo.maxSize = e.perPage
      this.doSearch()
    },
    onChangePage (e) {
      this.pageinfo.page = e.currentPage - 1
      this.pageinfo.maxSize = e.perPage
      this.doSearch()
    },
    onGoNextItem (value) {
      this.rowActiveHandler(value)
    },
    onGoPrevItem (value) {
      this.rowActiveHandler(value)
    },
    rowActiveHandler (e) {
      this.pageinfo.currentRow = parseInt(e)
    },
    createdHandler (args) {
      const obj = this.$refs.gridData.$refs.grid.ej2Instances
      console.log(obj)
    },
    openFilterDialog () {

    },
  },
  watch: {
    /*
      currentRow : {
        handler: function(new_value, old_value) {

          let newItem = this.$refs.grdTHPhieuGiao.getRowByIndex(new_value - 1)
          let oldItem = this.$refs.grdTHPhieuGiao.getRowByIndex(old_value - 1)
          // console.log(newItem)
          if(newItem !== undefined)
            newItem.classList.add('e-focus')
          if(oldItem !== undefined)
            oldItem.classList.remove('e-focus')

          // EventBus.$emit('rowActive', { currentRow : parseInt(args.rowIndex + 1), data : this.items[new_value]})
        }
      },
      */
  },
  provide: {
    grid: [Group, Page, Sort, Aggregate, ExcelExport]
  }
})
</script>
