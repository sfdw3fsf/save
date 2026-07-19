<template>
    <div class="table-content">
        <ejs-grid
            ref="chuyenKhoanGrid"
            :dataSource="$props.items"
            :allowExcelExport='true'
            :allowPaging='false'
            :allowFiltering='true'
            :allowResizing='true'
            :allowSelection='true'
            :allowSorting='true'
            :allowGrouping='true'
            :gridLines="'Both'"
            :selectionSettings='selectionOptions'
            :groupSettings='groupOptions'
            :filterSettings='filterOptions'            
            :queryCellInfo='customiseCell'
            rowHeight=32
            locale='vi-VN'
            >
            <e-columns>
                <e-column field='ID' :template='cRowSelected' headerText='' headerTextAlign='Center' textAlign='Center' minWidth=38 width=40 maxWidth=42 :filterBarTemplate='templateOptions'></e-column>
                <e-column type='CHECKBOX' :allowSorting='false' minWidth=38 width=40 maxWidth=42></e-column>
                <e-column field='STT' headerTextAlign='Center' headerText='STT' textAlign='Right' width=70 :filterBarTemplate='templateOptions' isPrimaryKey='true'></e-column>
                <e-column field='NGAY_NH' headerText='Ngày ngân hàng' width=100></e-column>
                <e-column field='TEN_NHOM' headerTextAlign='Center' headerText='Tên nhóm' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='TIENTRA' headerTextAlign='Center' headerText='Tiền trả' textAlign='Right' width=100 format='N0' :filterBarTemplate='templateOptions'></e-column>
                <e-column field='CHUNGTU' headerTextAlign='Center' headerText='Chứng từ' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='STK' headerTextAlign='Center' headerText='Số tài khoản' textAlign='Left' width=150 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='GHICHU' headerTextAlign='Center' headerText='Ghi chú' textAlign='Left' width=150 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='LOI' headerTextAlign='Center' headerText='Lỗi' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>                
                <e-column field='STATUS' headerTextAlign='Center' headerText='' textAlign='Left' width=40 :filterBarTemplate='templateOptions'></e-column>
            </e-columns>            
        </ejs-grid>
    </div>
</template>
<style>
  .ten-nhom {
    color: rgb(55, 0, 255) !important;
  }  
  .tien-tra {
    color: rgb(184, 8, 8) !important;
  }
</style>
<script>
import Vue from 'vue'
import { L10n, setCulture } from '@syncfusion/ej2-base';
import { GridPlugin, Filter, Resize, ExcelExport, Sort, Selection, Group } from "@syncfusion/ej2-vue-grids"

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
    name: 'ChuyenKhoanGrid',

    props: {        
        items: Array        
    },

    data () {
        return {
            selectionOptions: { persistSelection: true, type: 'Multiple', checkboxOnly: true },
            pageOptions: { pageSize: 10, pageCount: 5, pageSizes: true, totalRecordCount : 1000},
            filterOptions: { enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true},
            groupOptions: { showDropArea: false, columns: ['NGAY_NH'] },
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
            }
        }
    },

    methods: {
        customiseCell: function(args) {
            if (args.column.field === 'TEN_NHOM')
                args.cell.classList.add('ten-nhom');           
            else if (args.column.field === 'TIENTRA')
                args.cell.classList.add('tien-tra');
        },

        exportExcel() {        
            this.$refs.chuyenKhoanGrid.excelExport();
        }
    },

    provide: {
      grid: [Filter, Resize, Selection, Sort, ExcelExport, Group]
    }
})
</script>
