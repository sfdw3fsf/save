<template>
  <DataGrid
    v-bind:columns="columns"
    v-bind:dataSource="dataSource"
    :showColumnCheckbox="showColumnCheckbox"
    :showFilter="true"
    :allowPaging="true"
    ref="DataGridCustom"
    :enablePagingServer="false"
    @actionComplete="dataBound"
    :enabledSelectFirstRow="
      enabledSelectFirstRow != null ? enabledSelectFirstRow : true
    "
    :selectionSettings="{ checkboxMode: checkboxMode ? checkboxMode : 'ResetOnRowClick' }"
    :panelDataHeight="heigh ? heigh : '170'"
    @rowSelected="GridFocus"
    @recordDoubleClick="GridDoubleClick"
    @rowClicked="rowClicked"
    @queryCellInfo="gview_DanhSach_queryCellInfo"
    @rowDataBound="rowDataBound"
  ></DataGrid>
</template>
<script>
export default {
  data() {
    return {
      dataSource: [],
    };
  },
  props: [
    "columns",
    "showColumnCheckbox",
    "enabledSelectFirstRow",
    "heigh",
    "autofit",
    "checkboxMode"
  ],
  methods: {
    rowClicked(index, data) {
      this.$emit("rowClicked", index, data);
    },
    GridFocus(data) {
      this.$emit("rowSelected", data);
    },

    GridDoubleClick(data) {
      this.$emit("recordDoubleClick", data);
    },
    getSelectedRecords() {
      return this.$refs.DataGridCustom.getSelectedRecords();
    },
    getSelectedRowIndexes() {
      return this.$refs.DataGridCustom.$refs.grid.getSelectedRowIndexes();
    },
    setCurrentSelectedRow(item) {
      this.$refs.DataGridCustom.setCurrentSelectedRow(item);
      this.$refs.DataGridCustom.flagSelectedRowIndexes = [];
      this.$refs.DataGridCustom.flagSelectedRowIndexes.push(item);
    },
    gview_DanhSach_queryCellInfo(data) {
      this.$emit("queryCellInfo", data)
    },
    dataBound() {
      if(this.autofit)
        this.$refs.DataGridCustom.$refs.grid.autoFitColumns(this.autofit)
    },
    rowDataBound(args){
      this.$emit('rowDataBound', args)
    }
  },
};
</script>