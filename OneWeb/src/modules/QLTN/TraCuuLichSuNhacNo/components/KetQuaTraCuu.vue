<template>
  <div class="table-content">
    <DataGrid
      v-bind:columns="[
        {fieldName: 'MA_TB', headerText: 'Số máy/acc', allowFiltering: true},
        {fieldName: 'DICHVU_VT', headerText: 'Dịch vụ', allowFiltering: true},
        {fieldName: 'TEN_TT', headerText: 'Tên thanh toán', allowFiltering: true},
        {fieldName: 'NGAYNHAC', headerText: 'Ngày nhắc', allowFiltering: true},
        {fieldName: 'KIEUNHAC', headerText: 'Kiểu nhắc', allowFiltering: true},
        {fieldName: 'LANNHAC', headerText: 'Lần nhắc', allowFiltering: true},
        {fieldName: 'MAY_CN', headerText: 'Máy CN', allowFiltering: true},
        {fieldName: 'NGUOI_CN', headerText: 'Người CN', allowFiltering: true}
        ]"
      v-bind:dataSource="$props.items"
      :allowExcelExport="true"
      :showFilter="true"
      :allowPaging="true"
      :totalRecords="$props.totalItems"
      :id="$props.id"
      :ref="$props.id"
      >
  </DataGrid>
  </div>
</template>

<script>
import Vue from 'vue'

export default Vue.extend({
  components : { },
  props : {
    items: {
      type: Array
    },
    totalItems: {
      type: Number
    },
    id: {
      type: String
    }
  },
  data: () => {
    return {

    }
  },
  methods : {
    doExportExcel(){
      let excelExportProperties = {
          dataSource: this.items
      };
      this.$refs[this.id].excelExport(excelExportProperties)
    }
  }
})
</script>
