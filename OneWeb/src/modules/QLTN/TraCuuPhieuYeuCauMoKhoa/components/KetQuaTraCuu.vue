<template>
  <div class="box-form">
    <div class="legend-title">Danh sách phiếu yêu cầu</div>
    <div class="table-content">
      <ejs-grid ref="grdPhieuYeuCau"
        :dataSource="$props.items"
        :allowPaging='false'
        :allowSorting='true'
        :allowFiltering='true'
        :allowResizing='true'
        :gridLines="'Both'"
        rowHeight=32
        >
        <e-columns>
          <e-column field='STT' headerText='' :allowSorting='false' width=30 class="w20" :filterBarTemplate='templateOptions'></e-column>
          <e-column field='NGAY_YC' headerText='Ngày YC' headerTextAlign='Center' width=120  textAlign='Center' :filterBarTemplate='templateOptions'></e-column>
          <e-column field='LOAIHINH_TB' headerText='Loại hình' headerTextAlign='Center' width=120  :filterBarTemplate='templateOptions'></e-column>
          <e-column field='MA_TB' headerText='Số máy/Acc' headerTextAlign='Center' width=120 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TEN_TB' headerText='Tên thuê bao' headerTextAlign='Center' width=200  :filterBarTemplate='templateOptions'></e-column>
          <e-column field='KIEU_YC' headerText='Kiểu YC' headerTextAlign='Center' width=120 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TRANGTHAI' headerText='Trạng thái' headerTextAlign='Center' width=120 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TEN_DV' headerText='Đơn vị' headerTextAlign='Center' width=200 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='NOIDUNG_YC' headerText='Nội dung YC' headerTextAlign='Center' width=120 :filterBarTemplate='templateOptions'></e-column>
        </e-columns>
      </ejs-grid>
      <div class="tc-pagination">
        <app-remote-pagination
          :pageinfo="$props.page"
          @change="onChangePage"
          @goPrev="onGoPrevItem"
          @goNext="onGoNextItem">
        </app-remote-pagination>
      </div>
    </div>

</div>
</template>
<script>
import Vue from 'vue'
import { GridPlugin, Filter, Resize, ExcelExport} from "@syncfusion/ej2-vue-grids"
import RemotePagination from '@/components/RemotePagination'
import EventBus from '@/utils/eventBus'

Vue.use(GridPlugin)

export default Vue.extend({
  components : { appRemotePagination : RemotePagination},
  props: {
    items: {
      type: Array
    },
    page: {
      type: Object
    }
  },
  data: () => {
    return {
      filterOptions: { enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true},
      templateOptions : {
        create: function (args) {
          // console.log(args.column.field)
          if(args.column.field === 'STT')
            return '<span class="fa"></span>'
          else if(args.column.field === 'CHECKBOX'){
            return '<div class="e-checkbox-wrapper e-css"><input class="e-checkselect" type="checkbox"><span class="e-frame e-icons"></span><span class="e-label"> </span></div>'
          }
          return '<div class="input-icon-right"><input id="' + args.column.field + '_filterBarcell" type="search" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>';
        },
        write: function (args) {}
      },
      searchResults : []
    }
  },
  methods : {
    exportExcel(dataSource){
      let excelExportProperties = {
          dataSource: dataSource
      };
      // this.$refs.dsPhieuDaGiao.excelExport()
      this.$refs.grdPhieuYeuCau.excelExport(excelExportProperties)
      // console.log(dataSource)
    },
    onChangePage (args) {
      EventBus.$emit('pageChange', args)
      //console.log(args)
    },
    onGoNextItem () {
    },
    onGoPrevItem () {
    }
  },
  provide: {
    grid: [Filter, Resize, ExcelExport]
  },
  watch : {}
})
</script>
