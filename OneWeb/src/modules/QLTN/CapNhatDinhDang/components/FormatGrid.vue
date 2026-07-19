<template>
    <div class="table-content">
        <ejs-grid
            ref="grid"
            :dataSource="$props.items"
            :allowExcelExport='true'
            :allowPaging='false'
            :allowFiltering='true'
            :allowResizing='true'
            :allowSelection='true'
            :allowSorting='true'
            :allowGrouping='true'
            :gridLines="'Both'"
            :recordClick="activeRow"
            :selectionSettings='selectionOptions'            
            :filterSettings='filterOptions'
            :dataBound="onDataBound"
            rowHeight=32
            locale='vi-VN'
            width='100%'
            >
            <e-columns>
                <e-column field='ID' :template='cRowSelected' headerText='' headerTextAlign='Center' textAlign='Center' minWidth=38 width=40 maxWidth=42 :filterBarTemplate='templateOptions'></e-column>
                <e-column type='CHECKBOX' :allowSorting='false' minWidth=38 width=40 maxWidth=42></e-column>           
                <e-column field='FORMAT_ID' headerTextAlign='Center' headerText='ID' textAlign='Right' width=80 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='FORMAT_NAME' headerTextAlign='Center' headerText='Tên định dạng' textAlign='Left' width=200 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='FORMATTYPE' headerTextAlign='Center' headerText='Kiểu' textAlign='Left' width=120 :filterBarTemplate='templateOptions'></e-column>
                <e-column field='ORDER_ID' headerTextAlign='Center' headerText='Thứ tự' textAlign='Right' width=120 :filterBarTemplate='templateOptions'></e-column>                
            </e-columns>            
        </ejs-grid>
    </div>
</template>
<style>  
</style>
<script>
import Vue from 'vue'
import { L10n, setCulture } from '@syncfusion/ej2-base';
import { GridPlugin, Filter, Resize, Sort, Selection } from "@syncfusion/ej2-vue-grids"

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
    name: 'FormatGrid',

    props: {        
        items: Array        
    },

    data () {
        return {
            currentRow: null,
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
            }
        }
    },

    methods: {
        onDataBound() {
            if (this.items.length > 0) {
                this.currentRow = 0;
                this.$emit('rowActive', this.currentRow);
            }
        },

        activeRow (args){
            this.$emit('rowActive', args.rowIndex);
            this.currentRow = parseInt(args.rowIndex);
        },
        
        getCheckedRecords() {
            console.log('get checked')
            return this.$refs.grid.getSelectedRecords();
        },
    },

    provide: {
      grid: [Filter, Resize, Selection, Sort]
    },

    watch: {
      currentRow : {
        handler: function(newVal, oldVal) {
            if (newVal !== null) {
                let newItem = this.$refs.grid.getRowByIndex(newVal);
                if(newItem !== undefined)
                    newItem.classList.add('e-focus');
            }
            if (oldVal !== null) {
                let oldItem = this.$refs.grid.getRowByIndex(oldVal)
                if(oldItem !== undefined)
                    oldItem.classList.remove('e-focus')          
            }          
        }
      },
    }
})
</script>
