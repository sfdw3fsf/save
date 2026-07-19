<template>
  <div class="table-content" style="height: 600px;">
    <ejs-grid
        ref='gridPhieuDaGiao'
        :dataSource="$props.items"
        :allowExcelExport='true'
        :allowPaging='false'
        :allowFiltering='true'
        :filterSettings='filterOptions'
        :allowResizing='true'
        :allowSelection='true'
        :selectionSettings='selectionOptions'
        :gridLines="'Both'"
        rowHeight=32
        >
        <e-columns>
          <e-column type='checkbox' :allowSorting='false' width=40></e-column>
          <e-column field='STT' headerTextAlign='Center' headerText='STT' textAlign='Center' width=40 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='NGAY_TT' headerTextAlign='Center' headerText='Ngày trả' textAlign='Center' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='SOPHIEU' headerTextAlign='Center' headerText='Số phiếu' textAlign='Center' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='MA_TT' headerTextAlign='Center' headerText='Mã thanh toán' textAlign='Center' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='MATB_DD' headerTextAlign='Center' headerText='Mã đại diện' textAlign='Center' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='LANTRA' headerTextAlign='Center' headerText='Lần trả' textAlign='Right' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TIENTRA' headerTextAlign='Center' headerText='Tiền trả' textAlign='Right' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='SOHOADON' headerTextAlign='Center' headerText='Số hóa đơn' textAlign='Right' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TEN_TT' headerTextAlign='Center' headerText='Tên thanh toán' textAlign='Left' width=150 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='DIACHI_TT' headerTextAlign='Center' headerText='Địa chỉ thanh toán' textAlign='Left' width=150 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='NGUOIGACH' headerTextAlign='Center' headerText='Người gạch' textAlign='Left' width=100 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='CHUNGTU' headerTextAlign='Center' headerText='Số chứng từ' textAlign='Center' width=80 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='GHICHU' headerTextAlign='Center' headerText='Ghi chú' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='NHANVIEN_TC' headerTextAlign='Center' headerText='Nhân viên thu cước' textAlign='Left' width=100 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='ĐTCC' headerTextAlign='Center' headerText='ĐTCC' textAlign='Left' width=100 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='NGAYNGANHANG' headerTextAlign='Center' headerText='Ngày ngân hàng' textAlign='Center' width=100 :filterBarTemplate='templateOptions'></e-column>
          <e-column field='TEN_NH' headerTextAlign='Center' headerText='Ngân hàng' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>
        </e-columns>
      </ejs-grid>
</div>
</template>
<script>
import Vue from 'vue'
import { GridPlugin, Filter, Resize, ExcelExport, Sort} from "@syncfusion/ej2-vue-grids"
Vue.use(GridPlugin)

 export default Vue.extend({
    props: {
      items: {
        type: Array
      }
    },
    created () {

    },
    data () {
      return {
        selectionOptions: { persistSelection: true, type: 'Multiple', checkboxOnly: true },
        pageOptions: { pageSize: 10, pageCount: 5, pageSizes: true, totalRecordCount : 1000},
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
        }
      }
    },
    methods : {
      exportExcel(dataSource){
        let excelExportProperties = {
            dataSource: dataSource
        };
        this.$refs.gridPhieuDaGiao.excelExport(excelExportProperties)
      },
    }
  })
</script>
