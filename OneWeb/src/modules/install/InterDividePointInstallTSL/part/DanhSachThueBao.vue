<template>
  <div class="box-form">
    <div class="legend-title">Danh sách thuê bao</div>

     <div class="table-content" v-if="!formProps.table.loading">
      <DataGrid v-bind:columns="[
                        {fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true, width: '140'},
                        {fieldName: 'ma_tb', headerText: 'Account', allowFiltering: true},
                        {fieldName: 'trangthai_hd', headerText: 'Trạng thái hợp đồng', allowFiltering: true, width: '140'}, 
                        {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true},
                        {fieldName: 'diachi_tb', headerText: 'Địa chỉ thuê bao', allowFiltering: true, width: '300'},
                        {fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true, textAlign: 'right', width: '148'},
                        {fieldName: 'ngaygiao', headerText: 'Ngày giao', allowFiltering: true, textAlign: 'right', width: '148'},                                                
                        {fieldName: 'ten_dv_nhan', headerText: 'Đơn vị nhận', allowFiltering: true},
                    ]"
                  ref="DSTB"
                  :showColumnCheckbox='false'
                  v-bind:dataSource="formProps.table.options"
                  @selectedRowChanged="rowSelected" 
                  :enable-paging-server="false"
                  :allowPaging="true"
                  :allowSelection='true'
                  :multiple="false"
                  :unique="'ma_tb'" 
                  :enabled-select-first-row="true"
                  :showFilter="true"> 
        </DataGrid>
    </div>
  </div>
</template>

<script>
import { mixin } from "./mixins";

export default {
  mixins: [mixin],

  components: {},
  props: ["formProps"],


  methods: {
    rowSelected(row) {
      // this.promotionsCurrentRecordDetail = row.data;
      this.$emit("input", row);

      this.$emit("currentRecordDetail", row);
    },
  },
};
</script>

<style>
.table-result tbody tr:nth-child(2n+2){
    background-color: #F9E1A9 !important;
    background:  #F9E1A9 !important;    
  }
  .highlight{
    background-color: #F9E1A9 !important;
    background:  #F9E1A9 !important;              
  }

  
</style>
//  {
//               label: "Số máy/Acc",
//               filterAble: true,
//               key: "ma_tb",
//             },
//             {
//               label: "Trạng thái hợp đồng",
//               filterAble: true,
//               key: "trangthai_hd",
//             },
//             {
//               label: "Tên thuê bao",
//               filterAble: true,
//               key: "ten_tb",
//             },
//             {
//               label: "Địa chỉ thuê bao",
//               filterAble: true,
//               key: "diachi_tb",
//             },
//             {
//               label: "Ngày YC",
//               filterAble: true,
//               key: "ngay_yc",
//             },
//             {
//               label: "Ngày giao",
//               filterAble: true,
//               key: "ngaygiao",
//             },
//             {
//               label: "Đơn vị nhận",
//               filterAble: true,
//               key: "ten_dv_nhan",
//             },