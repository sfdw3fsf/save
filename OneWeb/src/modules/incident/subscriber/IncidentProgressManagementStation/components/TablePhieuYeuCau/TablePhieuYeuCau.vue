<template>
  <DataGrid
    v-bind:columns="columns"
    v-bind:dataSource="dsPhieuYeuCau"
    :totalRecords="dsPhieuYeuCau.length"
    :pageSize="20"
    :showColumnCheckbox="false"
    :allowSelection="false"
    :allowPaging="true"
    :showFilter="true"
    :enabledSelectFirstRow="false"
    :enable-paging-server="false"
    @rowSelected="rowSelected"
  >
  </DataGrid>
</template>

<script>
import { phieuYeuCauColumns } from "../../index";
export default {
  name: "TablePhieuYeuCau",
  props: {
    dsPhieuYeuCau: {
      type: Array,
      default: () => []
    },
    selectedRow: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      columns: phieuYeuCauColumns
    };
  },
  watch: {
    dsPhieuYeuCau(newValue) {
      if (newValue && newValue.length)
        this.$emit("update:selectedRow", newValue[0]);
    }
  },
  methods: {
    rowSelected(row) {
      this.$emit("update:selectedRow", row.data);
    }
  }
};
</script>
