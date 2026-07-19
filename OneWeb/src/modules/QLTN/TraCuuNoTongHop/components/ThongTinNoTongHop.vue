<style sc></style>
<template>
  <div class="box-form">
    <div class="legend-title">
        <div class="pull-left">Thông tin nợ tổng hợp</div>
        <div class="pull-right">
            <a href="#" class="btn btn-main lh14 pad4">
              <span class="">D</span>
            </a>
            <a href="#" class="btn btn-main lh14 pad4">
              <span class="">P</span>
            </a>
        </div>
        <div class="clearfix"></div>
    </div>
    <div class="table-content" style="height: 250px;">
      <DataGrid
        v-bind:columns="[
          {fieldName: 'THANG_NO', headerText: 'Tháng nợ', textAlign: 'Right', allowFiltering: true},
          {fieldName: 'NO_DK', headerText: 'Đầu kỳ', textAlign: 'Right', allowFiltering: true, type:'number', format:'N0'},
          {fieldName: 'NO_PS', headerText: 'Phát sinh', textAlign: 'Right', allowFiltering: true, type:'number', format:'N0'},
          {fieldName: 'TRA_PS', headerText: 'Phát sinh có', textAlign: 'Right', allowFiltering: true, type:'number', format:'N0'},
          {fieldName: 'CONNO', headerText: 'Cuối kỳ', textAlign: 'Right', allowFiltering: true, type:'number', format:'N0'},
          ]"
        ref="grdNoTongHop"
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
      }
    },
    methods : {
    }
  })
</script>
