<template>
  <div class="box-form">
    <div class="legend-title">Kết quả tra cứu</div>
    <div class="table-content">
      <ejs-grid
        id='gridKetQuaTraCuu'
        ref='gridKetQuaTraCuu'
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
          <e-column field='STT' :template='cRowSelected' headerText='' headerTextAlign='Center' textAlign='Center' width=35 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='MA_TB' headerTextAlign='Center' headerText='Mã TB' textAlign='Left' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TIENTREO' headerTextAlign='Center' headerText='Tiền treo' textAlign='Right' format='N0' width=100  :filterBarTemplate='templateOptions'></e-column>
          <e-column field='NOIDUNG' headerTextAlign='Center' headerText='Nội dung' textAlign='Left' width=200 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='GHICHU' headerTextAlign='Center' headerText='Ghi chú' textAlign='Left' width=200 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='NGAY_CN' headerTextAlign='Center' headerText='Ngày cập nhật' textAlign='Right' width=120 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='NGUOI_CN' headerTextAlign='Center' headerText='Người cập nhật' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>
        </e-columns>
      </ejs-grid>
    </div>
    <div class="tc-pagination">
      <app-remote-pagination
        :pageinfo="$props.pageinfo"
        @change="onChangePage"
        @goPrev="onGoPrevItem"
        @goNext="onGoNextItem">
        </app-remote-pagination>
    </div>
</div>
</template>

<script>
  import Vue from 'vue'
  import { GridPlugin, Filter, Resize, Sort,  ExcelExport} from "@syncfusion/ej2-vue-grids"
  import RemotePagination from '@/components/RemotePagination'
  import EventBus from '@/utils/eventBus'

  Vue.use(GridPlugin)

  export default Vue.extend({
    components : { appRemotePagination : RemotePagination},
    props: {
      items: {
        type: Array
      },
      pageinfo: {
        type: Object
      }
    },
    computed : {
      currentRow (){
        return this.pageinfo.currentRow
      }
    },
    data: () => {
      // const $this = this
      return {
        selectionOptions: { persistSelection: true, type: 'Multiple', checkboxOnly: true },
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

            args.element.addEventListener('input', function(args) {
              console.log(args)
              let value = args.target.value
              let grid = document.getElementById('gridKetQuaTraCuu').ej2_instances[0]
              if(value !== '')
                grid.vueInstance.filterByColumn(args.target.id, 'contains', value, 'AND', true)
              else
              {
                grid.vueInstance.clearFiltering(new Array(args.target.id))
              }
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
      exportExcel(dataSource){
        let excelExportProperties = {
          dataSource: dataSource
        };
        this.$refs.gridKetQuaTraCuu.excelExport(excelExportProperties)
        // this.$refs.dsKetQuaTraCuu.excelExport()
      },
      activeRow (args){
        // EventBus.$emit('rowActive', parseInt(args.rowIndex + 1))
        this.pageinfo.currentRow = parseInt(args.rowIndex + 1)
      },
      actionComplete: function(args) {
        if((args.requestType == 'refresh' && this.items.length > 0)
          || (args.requestType == "filtering" && this.$refs.gridKetQuaTraCuu.getFilteredRecords().length > 0))
        {
          // EventBus.$emit('rowActive', 1)
          this.pageinfo.currentRow = 1
        }

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
    watch : {
      currentRow : {
        handler: function(new_value, old_value) {

          let newItem = this.$refs.gridKetQuaTraCuu.getRowByIndex(new_value - 1)
          let oldItem = this.$refs.gridKetQuaTraCuu.getRowByIndex(old_value - 1)
          // console.log(newItem)
          if(newItem !== undefined)
            newItem.classList.add('e-focus')
          if(oldItem !== undefined)
            oldItem.classList.remove('e-focus')
        }
      },
    },
    provide: {
      grid: [Filter, Resize, Sort, ExcelExport]
    }
  })
</script>
