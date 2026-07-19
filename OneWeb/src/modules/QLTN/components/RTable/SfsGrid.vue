<template>
  <div>      
      <div class="table-content">
        <ejs-grid
          :ref="$props.gridRef"
          :dataSource="$props.items"
          :allowExcelExport='true'
          :allowPaging='false'
          :allowFiltering='true'
          :allowResizing='true'
          :allowSelection='true'
          :allowSorting='true'
          :gridLines="'Both'"
          :recordClick="activeRow"
          :rowSelected="rowSelected"
          :rowDeselected="rowDeselected"
          :selectionSettings='selectionOptions'
          :actionComplete="actionComplete"
          :filterSettings='filterOptions'          
          :dataBound="onDataBound"
          rowHeight=32
          locale='vi-VN'
          >
          <e-columns>            
            <e-column field='IDX' :template='cRowSelected' headerText='' headerTextAlign='Center' textAlign='Center' minWidth=38 width=39 maxWidth=40 :filterBarTemplate='templateOptions'></e-column>
            <e-column v-if="$props.showCheckbox" type='checkbox' :allowSorting='false' minWidth=38 width=40 maxWidth=42></e-column>
            <e-column v-for="item in $props.fields" :key="item.key" 
              :field='item.key'
              :isPrimaryKey='item.isPrimaryKey'
              :visible='!item.hidden'
              headerTextAlign='Center' 
              :headerText='item.label' 
              :textAlign='item.tdClass ? item.tdClass : "Left"'
              :width='item.width'
              :format="item.isCurrency ? 'N0' : null"
              :filterBarTemplate='templateOptions'
            >
            </e-column>

            <!-- THAO TAC column -->
            <!-- Only show delete button -->
            <e-column v-if="$props.showDeleteAction && !$props.showRefreshAction" 
              headerText='Thao tác'
              width='150' 
              textAlign='Center'
              :allowSorting='false' 
              :template='deleteTemplate'
            >
            </e-column>
            <!-- Show both delete and refresh button -->
            <e-column v-if="$props.showDeleteAction && $props.showRefreshAction" 
              headerText='Thao tác' 
              width='150' 
              textAlign='Center'
              :allowSorting='false'
              :template='deleteRefreshTemplate'
            >
            </e-column>
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
<style scoped>
>>> .e-emptyrow {
  text-align: center!important;
}
</style>
<script>
import Vue from 'vue'
import { L10n, setCulture } from '@syncfusion/ej2-base';
import { GridPlugin, Filter, Resize, ExcelExport, Sort, Selection, Aggregate } from "@syncfusion/ej2-vue-grids"
import { currency } from '@/filters/currency'
import RemotePagination from '@/components/RemotePagination'
import EventBus from '@/utils/eventBus'

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
    components : { appRemotePagination: RemotePagination },
    name: 'SfsGrid',

    props: {
        fields: Array,
        items: Array,
        gridRef: String,        
        pageinfo:{
            type: Object
        },
        showCheckbox: {
          type: Boolean,
          default: false
        },
        preCheckedCheckbox: {
          type: Boolean,
          default: false
        },
        showDeleteAction: {
          type: Boolean,
          default: false
        },
        showRefreshAction: {
          type: Boolean,
          default: false
        }        
    },

    created () {
      if (this.showDeleteAction)
        EventBus.$on('onDeleteRow', this.onDeleteRowHandler);
      if (this.showRefreshAction)
        EventBus.$on('onRefreshRow', this.onRefreshRowHandler);
    },

    destroyed () {
      if (this.showDeleteAction)
        EventBus.$off('onDeleteRow', this.onDeleteRowHandler);
      if (this.showRefreshAction)
        EventBus.$off('onRefreshRow', this.onRefreshRowHandler);
    },

    computed: {
      currentRow (){
        return this.pageinfo.currentRow;
      },

      sumFooter() {
        return this.fields.filter(i => i.footer === 'Sum');
      },

      countSelectedFooter() {
        return this.fields.filter(i => i.footer === 'CountSelected');
      },
      
      sumSelectedFooter() {
        return this.fields.filter(i => i.footer === 'SumSelected');
      }      
    },

    data () {
      return {        
        footerData: {
          countSelected: '0/0',
          sumSelected: []
        },
        columnAggregate: [],
        pageOptions: { pageSize: 10, pageCount: 5, pageSizes: true, totalRecordCount : 1000},
        filterOptions: { enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true },
        selectionOptions: { mode: 'Row', checkboxOnly: true },
        locale: {},

        templateOptions: {
          create: function (args) {
            if(args.column.field === 'IDX')
              return '<span class="fa"></span>'
            else if(args.column.field === 'CHECKBOX'){
              return '<div class="e-checkbox-wrapper e-css"><input class="e-checkselect" type="checkbox"><span class="e-frame e-icons"></span><span class="e-label"> </span></div>'
            }
            return '<div class="input-icon-right"><input id="' + args.column.field + '_filterBarcell" type="search" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>';
          },
          write: function (args) {}
        },

        deleteTemplate: function () {
          return { template : Vue.component('columnTemplate', {
              template: `<button class="btn btn-danger lh14 pad3" @click="onDeleteRow">
                          <span class="-ap icon-close"></span>
                        </button>`,                        
              data () { 
                return { 
                  data: {}                  
                }; 
              },
              methods: {
                onDeleteRow() {                  
                  EventBus.$emit('onDeleteRow', this.data);
                }                
              }
            })
          }
        },

        deleteRefreshTemplate: function () {
          return { template : Vue.component('columnTemplate', {
              template: `<div>
                          <button class="btn btn-danger lh14 pad3" @click="onDeleteRow">
                            <span class="-ap icon-close"></span>
                          </button>
                          <button class="btn btn-primary lh14 pad3" @click="onRefreshRow">
                            <span class="btn-refresh nc-icon-outline arrows-1_refresh-68"></span>
                          </button>
                        </div>`,                        
              data () { 
                return { 
                  data: {}                  
                }; 
              },
              methods: {
                onDeleteRow() {                  
                  EventBus.$emit('onDeleteRow', this.data);
                },
                onRefreshRow() {
                  EventBus.$emit('onRefreshRow', this.data);
                }
              }
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
              template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
              data () { return { data: {data: {}}}; }
            })
          }
        },

        footerCustomSelected: function () {
          return  { template : Vue.component('footerTemplate', {
              template: `<input type="text" class="form-control tright" :value="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
              data () { return { data: {data: {}}}; }
            })
          }
        },

        rowActived: -1
      }
    },
    methods : {
      activeRow (args){        
        this.$emit('rowActive', parseInt(args.rowIndex + 1));
      },

      onDataBound() {
        /* CHECK ALL CHECKBOX AFTER RENDERED */
        if (this.showCheckbox && this.preCheckedCheckbox && this.items.length > 0) {
          let itemsIndexes = [...Array(this.items.length).keys()];
          this.setCheckedRecords(itemsIndexes);
        }

        /* INIT FOOTER AGGREGATES */
        this.sumFooter.forEach(item => {
          this.columnAggregate.push({ field: item.key, type: 'Sum', format: 'N0', footerTemplate: this.footerSum });
        });

        if (this.countSelectedFooter.length > 0) {
          this.footerData.countSelected = '0/' + this.items.length;
          this.countSelectedFooter.forEach(item => {
            this.columnAggregate.push({ field: item.key, type: 'Custom', footerTemplate: this.footerCustomSelected, customAggregate: this.countSelectedAggregateFn });
          });
        }

        if (this.sumSelectedFooter.length > 0) {
          this.footerData.sumSelected = [];
          this.sumSelectedFooter.forEach(item => {
            let data = '0/' + this.calculateSum(this.items, item.key);           
            this.footerData.sumSelected.push({ key: item.key, data: data });
          });

          this.footerData.sumSelected.forEach(item => {
            this.columnAggregate.push({ field: item.key, type: 'Custom', footerTemplate: this.footerCustomSelected, customAggregate: () => (item.data)});
          })          
        }

        this.$refs[this.gridRef].ej2Instances.setProperties({
            aggregates: [{ columns: this.columnAggregate }]});
      },

      calculateSum(obj, field) {
        return currency(obj.map(items => items[field])
        .reduce((prev, curr) => parseInt(prev) + parseInt(curr), 0));
      },

      rowSelected: function(args) {
        /* REFRESH FOOTER AGGREGATES */
        let isAggregateRefresh = false;
        if (this.countSelectedFooter.length > 0) {
          isAggregateRefresh = true;
          this.footerData.countSelected = args.rowIndexes.length + '/' + this.items.length;          
        }

        if (this.sumSelectedFooter.length > 0) {
          isAggregateRefresh = true;          
          let checkedItems = this.$refs[this.gridRef].getSelectedRecords();

          this.footerData.sumSelected = [];
          this.sumSelectedFooter.forEach(item => {
            let data = this.calculateSum(checkedItems, item.key) + '/' + this.calculateSum(this.items, item.key);           
            this.footerData.sumSelected.push({ key: item.key, data: data });

            let index = this.columnAggregate.findIndex(e => e.field === item.key);
            this.columnAggregate.splice(index, 1);
            this.columnAggregate.push({ field: item.key, type: 'Custom', footerTemplate: this.footerCustomSelected, customAggregate: () => (data)});
          });
        }

        // Refresh footer aggregates:
        if (isAggregateRefresh)
          this.$refs[this.gridRef].ej2Instances.setProperties({
              aggregates: [{ columns: this.columnAggregate }]});
      },

      rowDeselected: function(args) {
        /* REFRESH FOOTER AGGREGATES */
        let isAggregateRefresh = false;
        if (this.countSelectedFooter.length > 0) {
          isAggregateRefresh = true;
          this.footerData.countSelected = this.$refs[this.gridRef].getSelectedRecords().length + '/' + this.items.length;          
        }

        if (this.sumSelectedFooter.length > 0) {
          isAggregateRefresh = true;          
          let checkedItems = this.$refs[this.gridRef].getSelectedRecords();

          this.footerData.sumSelected = [];
          this.sumSelectedFooter.forEach(item => {
            let data = this.calculateSum(checkedItems, item.key) + '/' + this.calculateSum(this.items, item.key);           
            this.footerData.sumSelected.push({ key: item.key, data: data });

            let index = this.columnAggregate.findIndex(e => e.field === item.key);
            this.columnAggregate.splice(index, 1);
            this.columnAggregate.push({ field: item.key, type: 'Custom', footerTemplate: this.footerCustomSelected, customAggregate: () => (data)});
          });
        }

        // Refresh footer aggregates:
        if (isAggregateRefresh)
          this.$refs[this.gridRef].ej2Instances.setProperties({
              aggregates: [{ columns: this.columnAggregate }]});
      },

      actionComplete: function(args) {        
        if(args.requestType == 'refresh')
          this.$emit('rowActive', 1);
      },

      getCheckedRecords() {
        return this.$refs[this.gridRef].getSelectedRecords();
      },

      setCheckedRecords(indexesArray) {
        this.$refs[this.gridRef].selectRows(indexesArray);
      },

      exportExcel(dataSource){
        let excelExportProperties = {
            dataSource: dataSource
        };
        this.$refs[this.gridRef].excelExport(excelExportProperties)
      },

      onChangePage (args) {
        this.$emit('pageChange', args)
      },

      onGoNextItem (args) {        
        this.$emit('rowActive', args);
      },

      onGoPrevItem (args) {        
        this.$emit('rowActive', args);
      },

      onDeleteRowHandler (e) {        
        this.$emit('onDelete', e);
      },

      onRefreshRowHandler(e) {
        this.$emit('onRefresh', e);
      },

      countSelectedAggregateFn: function (data) {
        return this.footerData.countSelected;
      },

      sumSelectedAggregateFn: function (data) {
        return this.footerData.sumSelected[0].data;        
      },
    },

    provide: {
      grid: [Filter, Resize, Selection, Sort, ExcelExport, Aggregate]
    },

    watch: {
      currentRow : {
        handler: function(new_value, old_value) {          
          let newItem = this.$refs[this.gridRef].getRowByIndex(new_value - 1)
          let oldItem = this.$refs[this.gridRef].getRowByIndex(old_value - 1)
          if(newItem !== undefined)
            newItem.classList.add('e-focus')
          if(oldItem !== undefined)
            oldItem.classList.remove('e-focus')          
        }
      },
    }
  })
</script>
