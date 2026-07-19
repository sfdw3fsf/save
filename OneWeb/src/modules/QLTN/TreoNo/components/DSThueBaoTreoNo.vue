<template>
  <div class="box-form">
    <div class="legend-title">Danh sách treo nợ</div>
    <div class="table-content" style="height: 400px">
      <ejs-grid
        id="gridDSTreoNo"
        ref='gridDSTreoNo'
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
        :actionComplete="actionComplete"
        rowHeight=32
        >
        <e-columns>
          <e-column field='STT' :template='cRowSelected' headerText='' headerTextAlign='Center' textAlign='Center' width=30 :filterBarTemplate='templateOptions'></e-column>
          <e-column type='checkbox' :allowSorting='false' width=40></e-column>
          <c-column field='DICHVUVT_ID' :visible=false></c-column>
          <c-column field='THUEBAO_ID' :isPrimaryKey=true :visible=false></c-column>
          <e-column field='MA_TB' headerTextAlign='Center' headerText='Số máy/Acc' textAlign='Left' width=150 type="string" :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TIENTREO' headerTextAlign='Center' format='N0' headerText='Tiền treo' textAlign='Right' width=110 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='KYHOADON' headerTextAlign='Center' headerText='Kỳ hóa đơn' textAlign='Right' width=110 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TEN_TB' headerTextAlign='Center' headerText='Tên thuê bao' textAlign='Left' width=150 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='DIACHI_LD' headerTextAlign='Center' headerText='Địa chỉ LĐ' textAlign='Left' width=200 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='NOIDUNG' headerTextAlign='Center' headerText='Nội dung' textAlign='Left' width=150 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='GHICHU' headerTextAlign='Center' headerText='Ghi chú' textAlign='Left' width=100 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='NGAY_CN' headerTextAlign='Center' headerText='Ngày CN' textAlign='Right' width=170 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TRANGTHAI_TB' headerTextAlign='Center' headerText='Trạng thái TB' textAlign='Left' width=100 :filterBarTemplate='templateOptions'></e-column>
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
                $this.$refs.gridDSTreoNo.filterByColumn(args.target.id, 'contains', value, 'AND', false)
              else
              {
                let arrFilter = []
                $this.$refs.gridDSTreoNo.clearFiltering(new Array(args.target.id))
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
        // console.log(args)
        //this.rowActived = parseInt(args.rowIndex)
        EventBus.$emit('rowActive', parseInt(args.rowIndex + 1))
      },
      actionComplete: function(args) {
        //console.log(this.$refs.gridDSTreoNo.getDataRows())
        //console.log(this.$refs.gridDSTreoNo.ej2Instances)
        if((args.requestType == 'refresh' && this.items.length > 0)
          || (args.requestType == "filtering" && this.$refs.gridDSTreoNo.getFilteredRecords().length > 0))
          {
            EventBus.$emit('rowActive', 1)
        }

      },
      getSelectedIds() {
        let selectedrows = this.$refs.gridDSTreoNo.getSelectedRecords();  // Get the selected row indexes.
        let a = selectedrows.map(item => (item.THUEBAO_ID))
        return a
      },
      exportExcel(dataSource){

        let excelExportProperties = {
            dataSource: dataSource
        };
        this.$refs.gridDSTreoNo.excelExport(excelExportProperties)
      },
      onChangePage (args) {
        EventBus.$emit('pageChange', args)
      },
      onGoNextItem (args) {
        // console.log('gonext ' + args)
        EventBus.$emit('rowActive', args)
      },
      onGoPrevItem (args) {
        // console.log('goprev ' + args)
        EventBus.$emit('rowActive', args)
      },
    },
    provide: {
      grid: [Filter, Resize, Selection, Sort, ExcelExport]
    },
    watch: {
      /*
      pageinfo: {
        immediate: true,
        deep: true,
        handler(newValue, oldValue) {
          console.log(newValue, oldValue)
          if(this.$refs.gridDSTreoNo === undefined)
            return
          let newItem = this.$refs.gridDSTreoNo.getRowByIndex(newValue)
          let oldItem = null
          if(oldValue !== undefined)
            oldItem = this.$refs.gridDSTreoNo.getRowByIndex(oldValue)
          if(newItem !== undefined)
            newItem.classList.add('e-focus')
          if(oldItem !== undefined && oldItem !== null)
            oldItem.classList.remove('e-focus')

          // EventBus.$emit('rowActive', newValue)
        }
      }
      */

      currentRow : {
        handler: function(new_value, old_value) {
          // console.log(new_value, old_value)
          // console.log(this.$refs.gridDSTreoNo.getRowByIndex(new_value))

          //this.$refs.gridDSTreoNo.getDataRows()

          let newItem = this.$refs.gridDSTreoNo.getRowByIndex(new_value - 1)
          let oldItem = this.$refs.gridDSTreoNo.getRowByIndex(old_value - 1)
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
