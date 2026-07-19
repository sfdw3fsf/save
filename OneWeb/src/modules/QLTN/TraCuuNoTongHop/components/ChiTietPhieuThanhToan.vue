<template>
  <div class="box-form">
    <div class="legend-title">
        <div class="pull-left">Thông tin chi tiết phiếu thanh toán</div>
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
          {fieldName: 'TENKHOANMUC', headerText: 'Tên khoản mục', textAlign: 'Left', allowFiltering: true},
          {fieldName: 'TONGTRA', headerText: 'Tiền trả', textAlign: 'Right', allowFiltering: true, type:'number', format:'N0'},
          {fieldName: 'TRAGOC', headerText: 'Trả gốc', textAlign: 'Right', allowFiltering: true, type:'number', format:'N0'},
          {fieldName: 'TRATHUE', headerText: 'Trả thuế', textAlign: 'Right', allowFiltering: true, type:'number', format:'N0'},
          {fieldName: 'CHIHOAHONG', headerText: 'Chi hoa hồng', textAlign: 'Right', allowFiltering: true, type:'number', format:'N0'},
          {fieldName: 'VIETTAT', headerText: 'Loại tiền', textAlign: 'Left', allowFiltering: true}
          ]"
        ref="grdTTChiTiet"
        v-bind:dataSource="$props.items"
        v-bind:aggregates="[
          {field: 'TONGTRA', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
          {field: 'TRAGOC', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
          {field: 'TRATHUE', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
          {field: 'CHIHOAHONG', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        ]"
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

  export default Vue.extend({
    props: {
      items: {
        type: Array
      }
    },
    data () {
      return {
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
    },
    methods : {
    },
    watch : {
    }
  })
</script>

