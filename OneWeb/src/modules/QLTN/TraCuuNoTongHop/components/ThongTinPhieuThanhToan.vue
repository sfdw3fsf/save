<template>
  <div class="box-form">
    <div class="legend-title">
        Thông tin phiếu thanh toán
    </div>
    <div class="table-content" style="height: 250px;">
      <DataGrid
        v-bind:columns="[
          {fieldName: 'PHIEU_ID', visible: false, isPrimaryKey: true},
          {fieldName: 'KYTRA', headerText: 'Kỳ trả', textAlign: 'Right', allowFiltering: true},
          {fieldName: 'NGAY_TT', headerText: 'Ngày trả', textAlign: 'Right', allowFiltering: true},
          {fieldName: 'TONGTRA', headerText: 'Tiền trả', textAlign: 'Right', allowFiltering: true, type:'number', format:'N0'},
          {fieldName: 'KYTRA', headerText: 'Kỳ trả', textAlign: 'Right', allowFiltering: true},
          {fieldName: 'NGUOI_CN', headerText: 'User gạch', textAlign: 'Left', allowFiltering: true},
          {fieldName: 'MA_TN', headerText: 'Thu ngân', textAlign: 'Left', allowFiltering: true},
          {fieldName: 'HINHTHUC', headerText: 'Hình thức TT', textAlign: 'Left', allowFiltering: true},
          {fieldName: 'SERI', headerText: 'Seri', textAlign: 'Left', allowFiltering: true},
          {fieldName: 'SOSERI', headerText: 'Số Seri', textAlign: 'Right', allowFiltering: true},
          {fieldName: 'QUYEN', headerText: 'Quyển', textAlign: 'Right', allowFiltering: true},
          {fieldName: 'MANV_TC', headerText: 'Nhân viên TC', textAlign: 'Left', allowFiltering: true},
          {fieldName: 'GHICHU', headerText: 'Ghi chú', textAlign: 'Left', allowFiltering: true},
          {fieldName: 'NGAYGACH', headerText: 'Ngày gạch', textAlign: 'Right', allowFiltering: true},
          {fieldName: 'NGANHANG', headerText: 'Ngân hàng', textAlign: 'Left', allowFiltering: true},
          {fieldName: 'NGAYNGANHANG', headerText: 'Ngày ngân hàng', textAlign: 'Right', allowFiltering: true}
          ]"
        ref="grdDSTToan"
        v-bind:dataSource="$props.items"
        v-bind:aggregates="[
          {field: 'TONGTRA', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
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
  import EventBus from '@/utils/eventBus'

  export default Vue.extend({
    props: {
      items: {
        type: Array
      },
      pageinfo: {
        type: Object
      }
    },
    computed : {
      currentRow () {
        return this.pageinfo.currentRow
      },
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
    },
    methods : {
    },
    watch : {
    }
  })
</script>
