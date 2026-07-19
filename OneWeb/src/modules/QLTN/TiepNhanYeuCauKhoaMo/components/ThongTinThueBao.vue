<template>
  <div>
    <div class="table-content">
      <ejs-grid
        ref='gridPhieuChuaGiao'
        :dataSource="$props.items"
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
          <e-column field='MA_TB' headerTextAlign='Center' headerText='Số máy/Acc' textAlign='Left' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TEN_TB' headerTextAlign='Center' headerText='Tên thuê bao' textAlign='Left' width=200 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TEN_DVVT' headerTextAlign='Center' headerText='Loại hình' textAlign='Center' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TEN_TB' headerTextAlign='Center' headerText='Tên thuê bao' textAlign='Center' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='THANGNO' headerTextAlign='Center' headerText='Tháng nợ' textAlign='Center' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TONGNO' headerTextAlign='Center' headerText='Tổng nợ' textAlign='Right' width=80 :filterBarTemplate='templateOptions'></e-column>
        </e-columns>
      </ejs-grid>
    </div>
    <!-- PAGINATION -->
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
import { GridPlugin, Filter, Resize, Sort} from "@syncfusion/ej2-vue-grids"
Vue.use(GridPlugin)

 export default Vue.extend({
    props: {
      items: {
        type: Array
      },
      pageinfo: {
        type: Object
      }
    },
    created () {

    },
    computed : {
      currentRow () {
        return this.pageinfo.currentRow
      },
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
                $this.$refs.gridPhieuChuaGiao.filterByColumn(args.target.id, 'contains', value, 'AND', false)
              else
              {
                let arrFilter = []
                $this.$refs.gridPhieuChuaGiao.clearFiltering(new Array(args.target.id))
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
      activeRow (args){
        this.rowActiveHandler(parseInt(args.rowIndex + 1))
      },
      actionComplete: function(args) {
        if((args.requestType == 'refresh' && this.items.length > 0)
          || (args.requestType == "filtering" && this.$refs.gridPhieuChuaGiao.getFilteredRecords().length > 0))
          {
            this.rowActiveHandler(1)
        }

      },
      rowActiveHandler(e){
        this.pageinfo.currentRow = e
      },
    },
    provide: {
      grid: [Filter, Resize, Sort]
    },
    watch: {
      currentRow : {
        handler: function(new_value, old_value) {

          let newItem = this.$refs.gridPhieuChuaGiao.getRowByIndex(new_value - 1)
          let oldItem = this.$refs.gridPhieuChuaGiao.getRowByIndex(old_value - 1)
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

