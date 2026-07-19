<template>
  <div class="box-form">
      <div class="legend-title">Kết quả tìm kiếm</div>
      <div class="table-content">
        <ejs-grid
        ref='gridLichSuThanhToan'
        :dataSource="$props.items"
        :allowExcelExport='true'
        :allowPaging='false'
        :allowFiltering='true'
        :allowResizing='true'
        :allowSelection='true'
        :selectionSettings='selectionOptions'
        :gridLines="'Both'"
        :recordClick="activeRow"
        :rowDataBound="rowDataBound"
        rowHeight=32
        >
        <e-columns>
          <e-column field='STT' :template='cRowSelected' headerText='' headerTextAlign='Center' textAlign='Center' width=50 :filterBarTemplate='templateOptions'></e-column>
          <e-column type='checkbox' headerText='Chọn' :allowSorting='false' width=40></e-column>
          <c-column field='NGANHANG_ID' :visible=false></c-column>
          <c-column field='HINHTHUC_TT' :visible=false></c-column>
          <c-column field='THANHTOAN_ID' :isPrimaryKey=true :visible=false></c-column>
          <e-column field='MA_TT' headerTextAlign='Center' headerText='Mã thanh toán' textAlign='Left' width=130 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='MA_TT_NEO' headerTextAlign='Center' headerText='Mã TT Neo' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='MAGOM' headerTextAlign='Center' headerText='Mã Gom' textAlign='Left' width=100 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='MATB_DD' headerTextAlign='Center' headerText='Mã đại diện' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TONGNO' headerTextAlign='Center' format='N0' headerText='Tiền nợ' textAlign='Right' width=90 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='NOPS' headerTextAlign='Center' format='N0' headerText='Tiền phát sinh' textAlign='Right' width=100 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='NODK' headerTextAlign='Center' format='N0' headerText='Tiền đầu kỳ' textAlign='Right' width=100 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='NGAY_TT' headerTextAlign='Center' headerText='Ngày TT' textAlign='Right' width=120 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='SO_TB' headerTextAlign='Center' headerText='Số TB' textAlign='Left' width=100 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='CHUNGTU' headerTextAlign='Center' headerText='Chứng từ' textAlign='Left' width=100 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='NGANHANG' headerTextAlign='Center' headerText='Ngân hàng' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TENNHOM' headerTextAlign='Center' headerText='Tên nhóm' textAlign='Left' width=100 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TEN_TT' headerTextAlign='Center' headerText='Tên thanh toán' textAlign='Left' width=170 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='DIACHI_TT' headerTextAlign='Center' headerText='Địa chỉ thanh toán' textAlign='Left' width=200 :filterBarTemplate='templateOptions'></e-column>
        </e-columns>
      </ejs-grid>
      </div>
      <div class="tc-pagination">

      </div>
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
    created () {

    },
    data () {
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
            }
            return '<div class="input-icon-right"><input id="' + args.column.field + '_filterBarcell" type="search" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>';
          },
          write: function (args) {}
        },
        cRowSelected: function() {
          return {
            template : Vue.component('columntemplate', {
              template : '<span class="fa"></span>'
            })  // end of template
          }
        },

        rowActived: -1
      }
    },
    methods : {
      activeRow (args){
        this.rowActived = parseInt(args.rowIndex)
        EventBus.$emit('rowActive', args.rowData)
      },
      rowDataBound: function(args) {
        /*
        let index = parseInt(args.row.getAttribute('aria-rowindex'))
        console.log(index, this.rowActived)
        if(index === this.rowActived)
          args.row.classList.add('e-focus')
        */

      },
      getSelectedIds() {
        let selectedrows = this.$refs.gridDSTreoNo.getSelectedRecords();  // Get the selected row indexes.
        let a = selectedrows.map(item => (item.THANHTOAN_ID))
        return a
      },
      exportExcel(dataSource){

        let excelExportProperties = {
            dataSource: dataSource
        };
        this.$refs.gridLichSuThanhToan.excelExport(excelExportProperties)
      },
      onChangePage (args) {
        EventBus.$emit('pageChange', args)
      },
      onGoNextItem () {
        if(this.items.length > 0 && this.items.length > this.rowActived)
          this.rowActived = this.rowActived + 1
      },
      onGoPrevItem () {
        if(this.items.length > 0 && this.rowActived > 0)
          this.rowActived = this.rowActived - 1
      },
    },
    provide: {
      grid: [Filter, Resize, Selection, Sort, ExcelExport]
    },
    watch: {
      rowActived : {
        handler: function(new_value, old_value) {
          // console.log(this.$refs.gridDSTreoNo.getRowByIndex(new_value))
          let newItem = this.$refs.gridDSTreoNo.getRowByIndex(new_value)
          let oldItem = this.$refs.gridDSTreoNo.getRowByIndex(old_value)
          if(newItem !== undefined)
            newItem.classList.add('e-focus')
          if(oldItem !== undefined)
            oldItem.classList.remove('e-focus')

          // EventBus.$emit('rowActive', args.rowData)
        }
      },
    }
  })
</script>
