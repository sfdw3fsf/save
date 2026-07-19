<template>
    <div class="table-content">
        <ejs-grid
            ref="hdtbGrid"
            :dataSource="$props.items"
            :allowPaging='false'
            :allowFiltering='true'
            :allowResizing='true'
            :allowSelection='true'
            :allowSorting='true'
            :gridLines="'Both'"
            :recordClick="activeRow"
            :selectionSettings='selectionOptions'
            :filterSettings='filterOptions'
            :queryCellInfo='customiseCell'
            rowHeight=32
            locale='vi-VN'
            >
            <e-columns>                
                <e-column field='ID' :template='cRowSelected' headerText='' headerTextAlign='Center' textAlign='Center' minWidth=38 width=40 maxWidth=42 freeze='Left' :filterBarTemplate='templateOptions'></e-column>
                <e-column field='NGAY_YC' headerTextAlign='Center' headerText='Ngày yêu cầu' textAlign='Right' width=130 :filterBarTemplate='templateOptions' freeze='Left'></e-column>
                <e-column field='MA_GD' headerTextAlign='Center' headerText='Mã giao dịch' textAlign='Left' width=130 :filterBarTemplate='templateOptions' isPrimaryKey='true' freeze='Left'></e-column>
                <e-column field='MA_KH' headerTextAlign='Center' headerText='Mã khách hàng' textAlign='Left' width=140 :filterBarTemplate='templateOptions' freeze='Left'></e-column>
                <e-column field='TEN_KH' headerTextAlign='Center' headerText='Tên khách hàng' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>                
                <e-column field='TONGTIEN' headerTextAlign='Center' headerText='Tổng tiền' textAlign='Right' width=100 format='N0' :filterBarTemplate='templateOptions'></e-column>                
                <e-column field='DIACHI_KH' headerTextAlign='Center' headerText='Địa chỉ KH' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>                
                <e-column field='NHANVIEN_LHD' headerTextAlign='Center' headerText='Người lập HĐ/PL' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='DONVI_LHD' headerTextAlign='Center' headerText='Đơn vị YC' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>                
            </e-columns>                      
        </ejs-grid>
    </div>
</template>
<style>
.tong-tien {
    color: rgb(255, 0, 0) !important;
  }</style>
<script>
import Vue from 'vue'
import { L10n, setCulture } from '@syncfusion/ej2-base';
import { GridPlugin, Filter, Resize, Sort, Selection, Freeze } from "@syncfusion/ej2-vue-grids"

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
    name: 'HDTBGrid',

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
            if (args.column.field === 'TONGTIEN')
                args.cell.classList.add('tong-tien');            
        },
        
        activeRow (args){        
            this.$emit('rowActive', parseInt(args.rowIndex + 1));
        },
    },

    provide: {
      grid: [Filter, Resize, Selection, Sort, Freeze]
    }
})
</script>
