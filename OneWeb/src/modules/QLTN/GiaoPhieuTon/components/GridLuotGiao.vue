<template>
    <div class="table-content">
        <ejs-grid
            ref="luotGiaoGrid"
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
            rowHeight=32
            locale='vi-VN'
            >
            <e-columns>
                <e-column field='STT' :template='cRowSelected' headerText='' headerTextAlign='Center' textAlign='Center' minWidth=38 width=40 maxWidth=42 :filterBarTemplate='templateOptions'></e-column>
                <e-column type='CHECKBOX' :allowSorting='false' minWidth=38 width=40 maxWidth=42></e-column>                
                <e-column field='TEN_NV' headerText='Nhân viên' width=100 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='GIAOPHIEU_ID' isPrimaryKey='true' headerTextAlign='Center' headerText='Lượt giao' textAlign='Right' width=100 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='NGAYGIAO' headerTextAlign='Center' headerText='Ngày giao' textAlign='Right' width=100 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='SOLUONG_GBC' headerTextAlign='Center' headerText='Giấy báo giao' textAlign='Right' width=100 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='SOLUONG_HD' headerTextAlign='Center' headerText='Hóa đơn giao' textAlign='Right' width=100 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='TONGTIEN_HD' headerTextAlign='Center' headerText='Tiền hóa đơn giao' textAlign='Right' width=100 format="N0" :filterBarTemplate='templateOptions'></e-column>
                <e-column field='TRA_HD' headerTextAlign='Center' headerText='Trả hóa đơn' textAlign='Right' width=100 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='TRA_TB' headerTextAlign='Center' headerText='Trả trọn bộ' textAlign='Right' width=100 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='DS_DUONG' headerTextAlign='Center' headerText='Danh sách đường' textAlign='Left' width=100 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='DS_DOT' headerTextAlign='Center' headerText='Danh sách đợt' textAlign='Right' width=100 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='NVGIAO' headerTextAlign='Center' headerText='NV giao' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>                
            </e-columns>            
        </ejs-grid>
    </div>
</template>
<style>  
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
    name: 'LuotGiaoGrid',

    props: {        
        items: Array        
    },

    data () {
        return {
            selectionOptions: { persistSelection: true, type: 'Multiple', checkboxOnly: true },
            pageOptions: { pageSize: 10, pageCount: 5, pageSizes: true, totalRecordCount : 1000},
            filterOptions: { enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true},
            groupOptions: { showDropArea: false, columns: ['TEN_NV'] },
            templateOptions : {
                create: function (args) {                    
                    if (args.column.field === 'STT')
                        return '<span class="fa"></span>'
                    else if (args.column.field === 'TEN_NV')
                        return '<span></span>'
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
        getCheckedRecords() {
            return this.$refs.luotGiaoGrid.getSelectedRecords();        
        },

        exportExcel() {
            this.$refs.luotGiaoGrid.excelExport();
        }
    },

    provide: {
      grid: [Filter, Resize, Selection, Sort, ExcelExport, Group]
    }
})
</script>
