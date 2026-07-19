<template>
<div class="box-col box-form" id="boxRight">
  <div class="legend-title">Danh sách phiếu thanh toán</div>
  <ejs-grid
    :dataSource="$props.items"
    :allowPaging='false'
    :allowSorting='true'
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
      <e-column field='PHIEU_ID' :isPrimaryKey='true' :visible='false'></e-column>
      <e-column field='STT' headerTextAlign='Center' width=50 :allowSorting='false' headerText='STT' textAlign='Center' :filterBarTemplate='templateOptions'></e-column>
      <e-column field='NGAY_TT' headerTextAlign='Center' headerText='Ngày trả' textAlign='Right' width=90 :filterBarTemplate='templateOptions' :autoFit='true'></e-column>
      <e-column field='NGAY_CN' headerTextAlign='Center' headerText='Ngày cập nhật' textAlign='Right' width=120 :filterBarTemplate='templateOptions' :autoFit='true'></e-column>
      <e-column field='SOPHIEU' headerTextAlign='Center' headerText='Số phiếu' textAlign='Right' width=80 :filterBarTemplate='templateOptions' :autoFit='true'></e-column>
      <e-column field='MA_TT' headerTextAlign='Center' headerText='Mã thanh toán' textAlign='Left' width=120 :filterBarTemplate='templateOptions' :autoFit='true'></e-column>
      <e-column field='MATB_DD' headerTextAlign='Center' headerText='Mã đại diện' textAlign='Left' width=120 :filterBarTemplate='templateOptions' :autoFit='true'></e-column>
      <e-column field='TIEN_TRA' headerTextAlign='Center' headerText='Tiền trả' textAlign='Right' format='N0' width=80 :filterBarTemplate='templateOptions' :autoFit='true'></e-column>
      <e-column field='LANTRA' headerTextAlign='Center' headerText='Lần trả' textAlign='Right' width=80 :filterBarTemplate='templateOptions'></e-column>
      <e-column field='SO_HD' headerTextAlign='Center' headerText='Số hợp đồng' textAlign='Left' width=80 :filterBarTemplate='templateOptions'></e-column>
      <e-column field='TEN_TT' headerTextAlign='Center' headerText='Tên thanh toán' textAlign='Left' width=200 :filterBarTemplate='templateOptions'></e-column>
      <e-column field='DIACHI_TT' headerTextAlign='Center' headerText='Địa chỉ thanh toán' textAlign='Left' width=200 :filterBarTemplate='templateOptions'></e-column>
      <e-column field='NGUOI_CN' headerTextAlign='Center' headerText='Người gạch' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>
      <e-column field='CHUNGTU' headerTextAlign='Center' headerText='Chứng từ' textAlign='Left' width=80 :filterBarTemplate='templateOptions'></e-column>
      <e-column field='THUNGAN' headerTextAlign='Center' headerText='Thu ngân' textAlign='Left' width=120 :filterBarTemplate='templateOptions':autoFit='true'></e-column>
      <e-column field='HDDT' headerTextAlign='Center' headerText='HĐĐT' textAlign='Left' width=60 :filterBarTemplate='templateOptions'></e-column>
      <e-column field='GHICHU' headerTextAlign='Center' headerText='Ghi chú' textAlign='Left' width=60 :filterBarTemplate='templateOptions':autoFit='true'></e-column>
      <e-column field='NGANHANG' headerTextAlign='Center' headerText='Ngân hàng' textAlign='Left' width=60 :filterBarTemplate='templateOptions'></e-column>
      <e-column field='NGAYNGANHANG' headerTextAlign='Center' headerText='Ngày ngân hàng' textAlign='Right' width=60 :filterBarTemplate='templateOptions'></e-column>
      <e-column field='SO_TC' headerTextAlign='Center' headerText='Số tham chiếu' textAlign='Left' width=60 :filterBarTemplate='templateOptions'></e-column>
      <e-column field='MA_CHUAN' headerTextAlign='Center' headerText='Mã chuẩn chi' textAlign='Left' width=60 :filterBarTemplate='templateOptions'></e-column>
      <e-column field='DOITAC' headerText='Đối tác' headerTextAlign='Center' textAlign='Left' width=60 :filterBarTemplate='templateOptions'></e-column>
      <e-column field='PHIEU_ID_NEO' headerTextAlign='Center' headerText='Phiếu ID NEO' textAlign='Left' width=60 :filterBarTemplate='templateOptions'></e-column>
      <e-column field='TRANGTHAI' headerTextAlign='Center' headerText='Trạng thái' textAlign='Center' width=80 :filterBarTemplate='templateOptions'></e-column>
    </e-columns>
    <e-aggregates>
      <e-aggregate>
          <e-columns>
            <e-column type="Sum" field="TIEN_TRA" :footerTemplate='footerSum'></e-column>
          </e-columns>
      </e-aggregate>
    </e-aggregates>
  </ejs-grid>
</div>
</template>

<script>
import Vue from 'vue'
import { GridPlugin, Filter, Page, Sort, Aggregate, Resize, Selection} from "@syncfusion/ej2-vue-grids"

Vue.use(GridPlugin)

export default Vue.extend({
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
      selectionOptions: { persistSelection: true, type: 'Multiple', checkboxOnly: true },
      currentPage: 1,
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
    grid: [Filter, Page, Sort, Aggregate, Resize, Selection]
  },
  mounted () {
  },
  computed: {
  },
  methods: {
  },
  watch: {
    /*
    currentPage : {
      handler: function(value) {
        // page based on 1
        EventBus.$emit('pageChange', value - 1);
      }
    }
    */
  }
})
</script>
