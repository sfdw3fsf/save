<style scoped>
  .e-groupdroparea e-grouped e-lib e-droppable e-draggable{
    display: none!important;
  }
</style>
<template>
  <div class="box-form">
    <div class="legend-title">
        <div class="pull-left">Danh sách khách hàng xử lý nợ</div>
        <div class="pull-right">
            <div class="-color normal">
              <b-form-checkbox
                v-model="p_chk_group"
                class="check"
                value="1"
                unchecked-value="0"
              >
              <span class="name">Nhóm theo nhân viên</span>
              </b-form-checkbox>
            </div>
        </div>
        <div class="clearfix"></div>
    </div>
    <div class="table-content">
      <DataGrid
          v-bind:columns="dsPhieuTTCols"
          :showColumnCheckbox="true"
          ref="dsPhieuDaGiao"
          v-bind:dataSource="dsPhieuTToan"
          v-bind:aggregates="sumColumnsPhieuTT"
          :allowExcelExport="true"
          :showFilter="true"
          :allowPaging="true"
          :totalRecords="pageinfoDSPhieuTToan.totalElement"
          :pageSize="pageinfoDSPhieuTToan.maxSize"
          :pageIndex="pageinfoDSPhieuTToan.page"
          @selectedRowChanged="phieuTTRowChanged"
          @pageChanged="phieuTTpageChangeHandler"
          >
        </DataGrid>
      <ejs-grid
        ref='dsPhieuDaGiao'
        :dataSource="$props.items"
        :allowExcelExport='true'
        :allowPaging='false'
        :allowFiltering='true'
        :filterSettings='filterOptions'
        :allowResizing='true'
        :allowSelection='true'
        :selectionSettings='selectionOptions'
        :allowGrouping='true'
        :groupSettings='groupOptions'
        :recordClick="activeRow"
        :actionComplete="actionComplete"
        :gridLines="'Both'"
        rowHeight=32
        >
        <e-columns>
          <e-column field='STT' :allowSorting='false' :template='cRowSelected' headerText='' headerTextAlign='Center' textAlign='Center' width=30 :filterBarTemplate='templateOptions'></e-column>
          <e-column type='checkbox' :allowSorting='false' width=40></e-column>
          <e-column field='PHIEU_ID' :isPrimaryKey='true' :visible='false'></e-column>
          <e-column field='NHANVIEN_XL' headerText='Nhân viên xử lý':filterBarTemplate='templateOptions'></e-column>
          <e-column field='MA_KHACHHANG' headerTextAlign='Center' headerText='Mã khách hàng' textAlign='Center' width=120 :filterBarTemplate='templateOptions' :autoFit='true'></e-column>
          <e-column field='MATB_DD' headerTextAlign='Center' headerText='Mã đại diện' textAlign='Center' width=120 :filterBarTemplate='templateOptions' :autoFit='true'></e-column>
          <e-column field='TEN_KH' headerTextAlign='Center' headerText='Mã đại diện' textAlign='Left' width=200 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='DIACHI_TT' headerTextAlign='Center' headerText='Tên khách hàng' textAlign='Left' width=200 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='DIACHI_KH' headerTextAlign='Center' headerText='Địa chỉ khách hàng' textAlign='Left' width=200 :filterBarTemplate='templateOptions' :autoFit='true'></e-column>
          <e-column field='TONGNO' headerTextAlign='Center' headerText='Nợ hiện tại' textAlign='Right' width=80 format="{N0}" :filterBarTemplate='templateOptions'></e-column>
          <e-column field='SO_CV' headerTextAlign='Center' headerText='Số công văn' textAlign='Right' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='LAN_XLN' headerTextAlign='Center' headerText='Lần XLN' textAlign='Right' width=80 :filterBarTemplate='templateOptions'></e-column>
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
import { GridPlugin, Filter, Resize, Selection, Group, ExcelExport} from "@syncfusion/ej2-vue-grids"
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
  data: () => {
    const $this = this
    return {
      p_chk_group: '1',
      selectionOptions: { persistSelection: true, type: 'Multiple', checkboxOnly: true },
      currentPage: 1,
      pageOptions: { pageSize: 10, pageCount: 5, pageSizes: true, totalRecordCount : 1000},
      filterOptions: { enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true},
      groupOptions: {
        columns: ["NHANVIEN_XL"]
      },
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
            console.log($this)
            if(value !== '')
              $this.$refs.dsPhieuDaGiao.filterByColumn(args.target.id, 'contains', value, 'AND', true)
            else
            {
              let arrFilter = []
              $this.$refs.dsPhieuDaGiao.clearFiltering(new Array(args.target.id))
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
      footerSum: function () {
        return  { template : Vue.component('sumTemplate', {
          template: `<span>{{data.Sum}}</span>`,
          data () {return { data: {}};}
          })
        }
      }
    }
  },
  provide: {
    grid: [Filter, Resize, Selection, Group, ExcelExport]
  },
  mounted () {
  },
  computed: {
    currentRow (){
      return this.pageinfo.currentRow
    }
  },
  methods: {
    activeRow (args){
      //this.rowActived = parseInt(args.rowIndex)
      EventBus.$emit('rowActive', parseInt(args.rowIndex + 1))
    },
    actionComplete: function(args) {
      if((args.requestType == 'refresh' && this.$refs.dsPhieuDaGiao.length > 0)
        || (args.requestType == "filtering" && this.$refs.dsPhieuDaGiao.getFilteredRecords().length > 0))
        {
          EventBus.$emit('rowActive', 1)
      }
    },
    exportExcel(dataSource){
      let excelExportProperties = {
          dataSource: dataSource
      };
      // this.$refs.dsPhieuDaGiao.excelExport()
      this.$refs.dsPhieuDaGiao.$refs.grid.excelExport(excelExportProperties)
      // console.log(dataSource)
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
  watch: {
    p_chk_group : {
      handler (newVal) {
        if(newVal === '0')
          this.$refs.dsPhieuDaGiao.$refs.grid.ungroupColumn('NHANVIEN_XL')
        else
          this.$refs.dsPhieuDaGiao.$refs.grid.groupColumn('NHANVIEN_XL')
      }
    },
    currentRow : {
      handler: function(new_value, old_value) {
        // console.log(new_value, old_value)
        // console.log(this.$refs.gridDSTreoNo.getRowByIndex(new_value))

        let newItem = this.$refs.dsPhieuDaGiao.getRowByIndex(new_value - 1)
        let oldItem = this.$refs.dsPhieuDaGiao.getRowByIndex(old_value - 1)
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
