<template>
  <div class="box-form">
    <div class="legend-title">
        <div class="pull-left">Thông tin nợ hiện tại</div>
        <div class="pull-right">
            <a href="#" class="btn btn-main lh14 pad4">
                <span class="nc-icon-outline files_single-content-03"></span>
            </a>
            <a href="#" class="btn btn-main lh14 pad4">
                <span class="one-calendar"></span>
            </a>
        </div>
        <div class="clearfix"></div>
    </div>
    <div class="table-content" style="height: 250px;">
      <DataGrid
        v-bind:columns="[
          {fieldName: 'KHOANMUCTT_ID', visible: false, isPrimaryKey: true},
          {fieldName: 'TENKHOANMUC', headerTextAlign: 'Center', headerText: 'Tên khoản mục', textAlign: 'Left', allowFiltering: true},
          {fieldName: 'TONGNO', headerTextAlign: 'Center', headerText: 'Tổng nợ', textAlign: 'Right', allowFiltering: true, type:'number', format:'N0'},
          {fieldName: 'NOGOC', headerTextAlign: 'Center', headerText: 'Nợ gốc', textAlign: 'Right', allowFiltering: true, type:'number', format:'N0'},
          {fieldName: 'THUE', headerTextAlign: 'Center', headerText: 'Thuế', textAlign: 'Right', allowFiltering: true, type:'number', format:'N0'},
          {fieldName: 'HOAHONG', headerTextAlign: 'Center', headerText: 'Hoa hồng', textAlign: 'Right', allowFiltering: true, type:'number', format:'N0'},
          {fieldName: 'VIETTAT', headerTextAlign: 'Center', headerText: 'Loại tiền', textAlign: 'Left', allowFiltering: true, type:'number', format:'N0'},
          ]"
        ref="grdNoChiTiet"
        v-bind:dataSource="$props.items"
        :showFilter="true"
        :allowPaging="true"
        :totalRecords="$props.totalItems"
        >
      </DataGrid>

    </div>
  </div>
</template>

<script>
  import Vue from 'vue'
  import { GridPlugin, Aggregate, Resize, Filter, Page, Sort, Group } from "@syncfusion/ej2-vue-grids"

  Vue.use(GridPlugin)
  export default Vue.extend({
    props: {
      items: {
        type: Array
      },
      totalItems: {
        type: Number
      }
    },
    computed : {
    },
    data () {
      return {
        footerSum: function () {
          return  { template : Vue.component('sumTemplate', {
            template: `<span>{{data.Sum}}</span>`,
            data () {return { data: {}};}
            })
          }
        },
      }
    },
    provide: {
      grid: [Aggregate, Group]
    },
    methods : {

    }
  })
</script>

