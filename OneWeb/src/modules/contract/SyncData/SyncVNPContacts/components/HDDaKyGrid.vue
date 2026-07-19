<script>
export default {
  name: 'HDDaKyGrid',
  props: {
    dataGrid: [],
  },
  data: () => ({
    pageSize: 10,
    pageIndex: 0,
    totalRecords: 0,
    current: 0,
  }),
  methods: {
    rowSelected({data}) {
        this.$emit("ttdanhba_DvSelected", data);
      },
      rowDeselected() {
        this.$emit("ttdanhba_DvSelected", null);
      },
      pageChanged(args) {
        this.pageSize = args.pageSize;
        this.pageIndex = args.pageIndex;
        this.current = 0
      },
      setCurrent(data) {
        this.current = data.rowIndex;
      },
  },
  computed: {
    columns: () => ([
      {
        fieldName: "ten_dv",
        headerText: "Mã GD",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "ten_dv",
        headerText: "Tên TB",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "ten_dv",
        headerText: "Điện thoại LH",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "ten_dv",
        headerText: "Loại hình",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "ten_dv",
        headerText: "Địa chỉ LĐ",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "ten_dv",
        headerText: "Ngày YC",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "ten_dv",
        headerText: "Ghi chú",
        allowSorting: true,
        autoFit: "true",
      },
    ])
  }
}
</script>
<template>
  <div class="SyncVNPContacts HDDaKyGrid">
    <DataGrid
      ref="danh_sach_hd_da_ky"
      v-bind:dataSource="dataGrid||[]"
      :columns="columns"
      :enablePagingServer="false"
      :allowPaging="true"
      :pageIndex="pageIndex"
      :pageSize="pageSize"
      :showColumnCheckbox="false"
      :totalRecords="totalRecords"
      @pageChanged="pageChanged"
      @rowSelected="rowSelected"
      @rowDeselected="rowDeselected"
      panelDataHeight="330"
    />
  </div>
</template>