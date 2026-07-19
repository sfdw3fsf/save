<template>
  <div class="box-form">
      <div class="legend-title">Danh sách thuê bao</div>
      <div class="table-content">
        <DataGrid
          ref:gridDSThueBao
          v-bind:columns="[
            {fieldName: 'DICHVUVT_ID', visible: false},
            {fieldName: 'THUEBAO_ID', visible: false, isPrimaryKey: true},
            {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true},
            {fieldName: 'TEN_DVVT', headerText: 'Dịch vụ', allowFiltering: true},
            {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true},
            {fieldName: 'TRANGTHAI', headerText: 'Trạng thái', allowFiltering: true},
            {fieldName: 'NGAY_YC', headerText: 'Ngày yêu cầu', allowFiltering: true},
            {fieldName: 'NGUOI_YC', headerText: 'Người yêu cầu', allowFiltering: true},
            {fieldName: 'TEN_DVVT', headerText: 'Đơn vị YC', allowFiltering: true},
            ]"
          v-bind:dataSource="$props.items"
          :showFilter="true"
          :allowPaging="true"
          :totalRecords="$props.totalItems"
          @selectedRowChanged="selectedRowChanged"
          >
        </DataGrid>
      </div>
  </div>
</template>

<script>
import Vue from 'vue'
import { L10n} from '@syncfusion/ej2-base'
import EventBus from '@/utils/eventBus'

 export default Vue.extend({
    props: {
      items: {
        type: Array
      },
      totalItems:{
        type: Object
      }
    },
    created () {

    },
    data () {
      return {
      }
    },
    methods : {
      exportExcel(dataSource){

        let excelExportProperties = {
            dataSource: dataSource
        };
        this.$refs.gridDSThueBao.excelExport(excelExportProperties)
      },
    },
})
</script>
