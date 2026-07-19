<template>
  <div class="box-col box-form">
    <div class="legend-title">Danh sách lần tạo</div>
      <div class="table-content">
        <ejs-grid
        id="gridDSLanTao"
        ref='gridDSLanTao'
        :dataSource="$props.items"
        :allowExcelExport='true'
        :allowPaging='false'
        :allowFiltering='true'
        :allowResizing='true'
        :allowSelection='true'
        :allowSorting='true'
        :selectionSettings='selectionOptions'
        :gridLines="'Both'"
        :recordClick="activeRow"
        locale="vi-VN"
        :actionComplete="actionComplete"
        rowHeight=32
        >
        <e-columns>
          <e-column field='STT' :template='cRowSelected' headerText='' headerTextAlign='Center' textAlign='Center' width=30 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='DICHVUVT_ID' :visible=false></e-column>
          <e-column field='LANTAO_NN_ID' :visible=false></e-column>
          <c-column field='THUEBAO_ID' :isPrimaryKey=true :visible=false></c-column>
          <e-column field='NGAY_TAO' headerTextAlign='Center' headerText='Ngày tạo' textAlign='Right' width=170 type="string" :filterBarTemplate='templateOptions'></e-column>
          <e-column field='SOLUONG' headerTextAlign='Center' format='N0' headerText='Số lượng' textAlign='Right' width=90 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TONGTIEN' headerTextAlign='Center' format='N0' headerText='Tổng tiền' textAlign='Right' width=120 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TU_TIEN' headerTextAlign='Center' format='N0' headerText='Từ tiền' textAlign='Right' width=90 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='DEN_TIEN' headerTextAlign='Center' format='N0' headerText='Đến tiền' textAlign='Right' width=90 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='DANHAC' headerTextAlign='Center' format='N0' headerText='Đã nhắc' textAlign='Right' width=90 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='NGUOI_CN' headerTextAlign='Center' headerText='Người tạo' textAlign='Left' width=150 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='DICHVU_VT' headerTextAlign='Center' headerText='Dịch vụ' textAlign='Left' width=200 :filterBarTemplate='templateOptions'></e-column>
        </e-columns>
      </ejs-grid>
      </div>
      <app-remote-pagination
        :pageinfo="$props.pageinfo"
        @change="onChangePage"
        @goPrev="onGoPrevItem"
        @goNext="onGoNextItem">
    </app-remote-pagination>
  </div>
</template>
<script>
import Vue from 'vue'

import { GridPlugin, Filter, Resize, ExcelExport, Sort, Selection} from "@syncfusion/ej2-vue-grids"
import RemotePagination from '@/components/RemotePagination'
import EventBus from '@/utils/eventBus'

Vue.use(GridPlugin)

 export default Vue.extend({
   components : { appRemotePagination: RemotePagination},
    props: {
      items: {
        type: Array
      },
      pageinfo:{
        type: Object
      }
    },
    computed : {
      currentRow (){
        return this.pageinfo.currentRow
      }
    },
    created () {

    },
    data () {
      const $this = this
      return {
        selectionOptions: { type: 'Multiple', checkboxOnly: true},
        pageOptions: { pageSize: 10, pageCount: 5, pageSizes: true, totalRecordCount : 1000},
        filterOptions: { enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true},
        templateOptions : {
          create: function (args) {
            if(args.column.field === 'STT')
              return '<span class="fa"></span>'
            else if(args.column.field === 'CHECKBOX'){
              return '<div class="e-checkbox-wrapper e-css"><input class="e-checkselect" type="checkbox"><span class="e-frame e-icons"></span><span class="e-label"> </span></div>'
            } else {
               return '<div class="input-icon-right"><input id="' + args.column.field + '" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>';
            }

          },
          write: function (args) {
            // const $this = this
            args.element.addEventListener('input', (args) => {
              let value = args.target.value
              if(value !== '')
                $this.$refs.gridDSLanTao.filterByColumn(args.target.id, 'contains', value, 'AND', true)
              else
              {
                let arrFilter = []
                $this.$refs.gridDSLanTao.clearFiltering(new Array(args.target.id))
              }
              // console.log($this.$refs.gridDSTreoNo.getFilteredRecords())
            })
          }
        },
        cRowSelected: function() {
          return {
            template : Vue.component('columntemplate', {
              template : '<span class="fa"></span>'
            })  // end of template
          }
        }
      }
    },
    methods : {
      activeRow (args){
        //this.rowActived = parseInt(args.rowIndex)
        // EventBus.$emit('rowActive', parseInt(args.rowIndex + 1))
        this.onRowActive(parseInt(args.rowIndex) + 1)
      },
      actionComplete: function(args) {
        // console.log(args)
        if((args.requestType == 'refresh' && this.items.length > 0)
          || (args.requestType == "filtering" && this.$refs.gridDSLanTao.getFilteredRecords().length > 0))
          {
            //EventBus.$emit('rowActive', 1)
            this.onRowActive(1)
        }

      },
      getSelectedIds() {
        let selectedrows = this.$refs.gridDSLanTao.getSelectedRecords();  // Get the selected row indexes.
        let a = selectedrows.map(item => (item.THUEBAO_ID))
        return a
      },
      exportExcel(dataSource){

        let excelExportProperties = {
            dataSource: dataSource
        };
       // this.$refs.gridDSTreoNo.excelExport(excelExportProperties)
      },
      onChangePage (args) {
        EventBus.$emit('pageChange', args)
      },
      onGoNextItem (args) {
        // console.log('gonext ' + args)
        // EventBus.$emit('rowActive', args)
        this.onRowActive(args)
      },
      onGoPrevItem (args) {
        // console.log('goprev ' + args)
        // EventBus.$emit('rowActive', args)
        this.onRowActive(args)
      },
      onRowActive (index) {
        this.pageinfo.currentRow = index
        EventBus.$emit('rowActive', this.items[this.pageinfo.currentRow - 1])
      }
    },
    provide: {
      grid: [Filter, Resize, Selection, Sort, ExcelExport]
    },
    watch: {


      currentRow : {
        handler: function(new_value, old_value) {
          // console.log(new_value, old_value)
          // console.log(this.$refs.gridDSTreoNo.getRowByIndex(new_value))

          let newItem = this.$refs.gridDSLanTao.getRowByIndex(new_value - 1)
          let oldItem = this.$refs.gridDSLanTao.getRowByIndex(old_value - 1)
          // console.log(newItem)
          if(newItem !== undefined)
            newItem.classList.add('e-focus')
          if(oldItem !== undefined)
            oldItem.classList.remove('e-focus')

          // EventBus.$emit('rowActive', { currentRow : parseInt(args.rowIndex + 1), data : this.items[new_value]})
        }
      },
    }
  })
</script>

