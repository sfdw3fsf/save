<script>
export default {
  name: 'TTDanhbaGrid',
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
        headerText: "Số máy",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "ten_dv",
        headerText: "Trạng thái",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "ten_dv",
        headerText: "Datesync",
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
        headerText: "Địa chỉ TB",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "ten_dv",
        headerText: "Ghi chú",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "ten_dv",
        headerText: "Nhóm lý do hủy",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "ten_dv",
        headerText: "Lý do hủy",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "ten_dv",
        headerText: "User Neo",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "ten_dv",
        headerText: "Người đồng bộ",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "ten_dv",
        headerText: "Ngày đồng bộ",
        allowSorting: true,
        autoFit: "true",
      },
      {
        fieldName: "ten_dv",
        headerText: "Đơn vị đồng bộ",
        allowSorting: true,
        autoFit: "true",
      },
    ])
  }
}
</script>
<template>
  <div class="SyncVNPContacts TTDanhbaGrid">
    <DataGrid
      ref="danh_sach_tt_danhba"
      v-bind:dataSource="dataGrid||[]"
      :columns="columns"
      :enablePagingServer="false"
      :allowPaging="true"
      :pageIndex="pageIndex"
      :pageSize="pageSize"
      :showColumnCheckbox="true"
      :totalRecords="totalRecords"
      @pageChanged="pageChanged"
      @rowSelected="rowSelected"
      @rowDeselected="rowDeselected"
      panelDataHeight="330"
    />
  </div>
</template>