<template>
    <div class="table-content">
        <ejs-grid
            ref="dsgnGrid"
            :dataSource="$props.items"
            :allowExcelExport='true'
            :allowPaging='false'
            :allowFiltering='true'
            :allowResizing='true'
            :allowSelection='true'
            :allowSorting='true'
            :gridLines="'Both'"
            :selectionSettings='selectionOptions'
            :filterSettings='filterOptions'
            :frozenRows='4'
            :queryCellInfo='customiseCell'
            rowHeight=32
            locale='vi-VN'
            >
            <e-columns>
                <e-column field='ID' :template='cRowSelected' headerText='' headerTextAlign='Center' textAlign='Center' minWidth=38 width=40 maxWidth=42 freeze='Left' :filterBarTemplate='templateOptions'></e-column>
                <e-column type='CHECKBOX' :allowSorting='false' minWidth=38 width=40 maxWidth=42 freeze='Left'></e-column>
                <e-column field='STT' headerTextAlign='Center' headerText='STT' textAlign='Right' width=70 :filterBarTemplate='templateOptions' freeze='Left'></e-column>
                <e-column field='NGAY_NH' headerTextAlign='Center' headerText='Ngày NH' textAlign='Right' width=120 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='TEN_NHOM' headerTextAlign='Center' headerText='Tên nhóm' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='NHOM_TT' headerTextAlign='Center' headerText='Nhóm TT' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='MA_TT' headerTextAlign='Center' headerText='Mã TT' textAlign='Left' width=120 :filterBarTemplate='templateOptions' isPrimaryKey='true'></e-column>
                <e-column field='MA_TT_NEO' headerTextAlign='Center' headerText='Mã TT Neo' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='TIEN_GOC' headerTextAlign='Center' headerText='Tiền trả' textAlign='Right' width=100 format='N0' :filterBarTemplate='templateOptions'></e-column>
                <e-column field='TIENTRA' headerTextAlign='Center' headerText='Còn nợ' textAlign='Right' width=100 format='N0' :filterBarTemplate='templateOptions'></e-column>
                <e-column field='CHUNGTU' headerTextAlign='Center' headerText='Chứng từ' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='TEN_TT' headerTextAlign='Center' headerText='Tên thanh toán' textAlign='Left' width=150 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='STK' headerTextAlign='Center' headerText='Số tài khoản' textAlign='Left' width=150 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='GHICHU' headerTextAlign='Center' headerText='Ghi chú' textAlign='Left' width=150 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='MA_GOM' headerTextAlign='Center' headerText='Gom HĐ' textAlign='Left' width=100 :filterBarTemplate='templateOptions' freeze='Right'></e-column>
            </e-columns>

            <!-- FOOTER -->
            <e-aggregates>
                <e-aggregate>
                <e-columns>
                    <e-column type="Sum" format='N0' field='TIENTRA' :footerTemplate='footerSum'>
                    </e-column>
                </e-columns>
                </e-aggregate>
            </e-aggregates>          
        </ejs-grid>
    </div>
</template>
<style>
  .ten-nhom {
    color: rgb(55, 0, 255) !important;
  }
  .ma-tt {
    color: rgb(255, 0, 0) !important;
  }
  .tien-tra {
    color: rgb(184, 8, 8) !important;
  }
</style>
<script>
import Vue from 'vue'
import { L10n, setCulture } from '@syncfusion/ej2-base';
import { GridPlugin, Filter, Resize, Sort, Selection, Aggregate, Freeze } from "@syncfusion/ej2-vue-grids"

setCulture('vi-VN');

L10n.load({
    'vi-VN': {
        'grid': {
            'EmptyRecord': 'Không có bản ghi nào để hiển thị'            
        }        
    }
});

Vue.use(GridPlugin)

export default Vue.extend({
    components : { },
    name: 'DSGNGrid',

    props: {        
        items: Array        
    },

    data () {
        return {
            selectionOptions: { persistSelection: true, type: 'Multiple', checkboxOnly: true },
            pageOptions: { pageSize: 10, pageCount: 5, pageSizes: true, totalRecordCount : 1000},
            filterOptions: { enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true},
            templateOptions : {
                create: function (args) {                    
                    if (args.column.field === 'ID')
                        return '<span class="fa"></span>'
                    else if (args.column.field === 'CHECKBOX')
                        return '<div class="e-checkbox-wrapper e-css"><input class="e-checkselect" type="checkbox"><span class="e-frame e-icons"></span><span class="e-label"> </span></div>'
                    else
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

            footerSum: function () {
                return  { template : Vue.component('sumTemplate', {
                    template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
                    data () { return { data: {data: {}}}; }
                    })
                }
            }
        }
    },

    methods: {
        customiseCell: function(args) {
            if (args.column.field === 'TEN_NHOM')
                args.cell.classList.add('ten-nhom');
            else if (args.column.field === 'MA_TT')
                args.cell.classList.add('ma-tt');
            else if (args.column.field === 'TIENTRA')
                args.cell.classList.add('tien-tra');
        },

        getCheckedRecords() {
            return this.$refs.dsgnGrid.getSelectedRecords();
        },
    },

    provide: {
      grid: [Filter, Resize, Selection, Sort, Aggregate, Freeze]
    }
})
</script>
