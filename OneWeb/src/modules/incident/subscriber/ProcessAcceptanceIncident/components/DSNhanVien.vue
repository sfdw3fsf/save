<template>
  <DataGrid
      v-bind:columns="columns"
      v-bind:dataSource="danhSach"
      :showFilter="true"
      :allowPaging="true" :enablePagingServer="false"
      :showColumnCheckbox="true"
      @selectedItemsChanged="selectedItemsChanged"
      @rowClicked="gridChiTiet_Selected"
    >
    </DataGrid>
</template>

<script>

export default {
    props: {
        danhSach: Array,
    },
    data() {
        return {
            columns: [
                {
                    fieldName: 'ten_nv',
                    headerText: 'Tên nhân viên',
                    allowFiltering: true
                },
                {
                    fieldName: 'so_dt',
                    headerText: 'Điện thoại',
                    allowFiltering: true
                },
                {
                    fieldName: 'nhiemvu',
                    headerText: 'Nhiệm vụ',
                    allowFiltering: true
                },
                {
                    fieldName: 'ghichu',
                    headerText: 'Ghi chú',
                    allowFiltering: true
                },
            ]
        }
    },
    methods: {
        selectedItemsChanged(items) {
            this.$emit("onchangeRowDS_BaoHong", items);
        },
        gridChiTiet_Selected(i, item) {
            this.$emit("onFocusRowDS_BaoHong", i, item);
        }
    }
}
</script>

<style>

</style>