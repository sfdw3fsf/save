<template>
  <div class="box-form">
    <div class="legend-title">Danh sách thuê bao</div>
    <div class="table-content">
      <ejs-grid
        ref='gridDanhSachThueBao'
        :dataSource="$props.items"
        :allowExcelExport='true'
        :allowPaging='false'
        :allowFiltering='true'
        :filterSettings='filterOptions'
        :allowResizing='true'
        :allowSelection='true'
        :selectionSettings='selectionOptions'
        :recordClick="activeRow"
        :actionComplete="actionComplete"
        :gridLines="'Both'"
        rowHeight=32
        >
        <e-columns>
          <e-column field='STT' :template='cRowSelected' headerTextAlign='Center' headerText='' textAlign='Center' width=40 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='YEUCAU_ID' :visible='false'></e-column>
          <e-column field='THUEBAO_ID' :visible='false'></e-column>
          <e-column field='TRANGTHAI_TB' :visible='false'></e-column>
          <e-column field='MA_TT' :visible='false'></e-column>
          <e-column field='TEN_TT' :visible='false'></e-column>
          <e-column field='DIACHI_TT' :visible='false'></e-column>
          <e-column field='DIACHI_LD' :visible='false'></e-column>
          <e-column field='NGAY_YC' headerTextAlign='Center' headerText='Ngày YC' textAlign='Right' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='LOAIHINH_TB' headerTextAlign='Center' headerText='Loại hình' textAlign='Left' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='MA_TB' headerTextAlign='Center' headerText='Số máy/Acc' textAlign='Left' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='KIEU_YC' headerTextAlign='Center' headerText='Kiểu YC' textAlign='Left' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TRANGTHAI' headerTextAlign='Center' headerText='Trạng thái' textAlign='Left' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TEN_DV' headerTextAlign='Center' headerText='Đơn vị' textAlign='Left' width=200 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='NOIDUNG_YC' headerTextAlign='Center' headerText='Nội dung YC' textAlign='Left' width=200 :filterBarTemplate='templateOptions'></e-column>
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
import { GridPlugin, Filter, Resize, Sort, ExcelExport} from "@syncfusion/ej2-vue-grids"
import RemotePagination from '@/components/RemotePagination'

Vue.use(GridPlugin)

 export default Vue.extend({
    components : { appRemotePagination: RemotePagination},
    props: {
      items: {
        type: Array
      },
      pageinfo: {
        type: Object
      }
    },
    computed : {
      currentRow () {
        return this.pageinfo.currentRow
      },
    },
    created () {

    },
    data () {
      return {
        selectionOptions: { persistSelection: true, type: 'Single'},
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
                $this.$refs.gridDanhSachThueBao.filterByColumn(args.target.id, 'contains', value, 'AND', false)
              else
              {
                let arrFilter = []
                $this.$refs.gridDanhSachThueBao.clearFiltering(new Array(args.target.id))
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
        },
      }
    },
    methods : {
      exportExcel(dataSource){
        let excelExportProperties = {
            dataSource: dataSource
        };
        // this.$refs.dsPhieuDaGiao.excelExport()
        this.$refs.gridDanhSachThueBao.excelExport(excelExportProperties)
        // console.log(dataSource)
      },
      activeRow (args){
        this.rowActiveHandler(parseInt(args.rowIndex + 1))
      },
      actionComplete: function(args) {
        if((args.requestType == 'refresh' && this.items.length > 0)
          || (args.requestType == "filtering" && this.$refs.gridDanhSachThueBao.getFilteredRecords().length > 0))
          {
            this.rowActiveHandler(1)
        }

      },
      rowActiveHandler(e){
        this.pageinfo.currentRow = e
      },
      onChangePage: function (args) {

      },
      onGoPrevItem: function (args) {

      },
      onGoNextItem: function (args) {

      },
    },
    provide: {
      grid: [Filter, Resize, Sort, ExcelExport]
    },
    watch: {
      currentRow : {
        handler: function(new_value, old_value) {

          let newItem = this.$refs.gridDanhSachThueBao.getRowByIndex(new_value - 1)
          let oldItem = this.$refs.gridDanhSachThueBao.getRowByIndex(old_value - 1)
          // console.log(newItem)
          if(newItem !== undefined)
            newItem.classList.add('e-focus')
          if(oldItem !== undefined)
            oldItem.classList.remove('e-focus')
        }
      },
    }
  })
</script>

