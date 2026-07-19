<template>
  <DataGrid
    v-bind:columns="columns"
    v-bind:dataSource="dsDaiLy"
    :showColumnCheckbox="false"
    :allowSelection="false"
    :allowPaging="true"
    :showFilter="true"
    @rowSelected="rowSelected"
  >
  </DataGrid>
</template>

<script>
import ActionTemplate from "./ActionTemplate.vue";
export default {
  name: "TableDSDaiLy",
  props: {
    dsDaiLy: {
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
      columns: [
        { fieldName: "TEN_DL", headerText: "Tên trạm", allowFiltering: true },
        {
          fieldName: "ELOADMSISDN",
          headerText: "Số TB/User web",
          allowFiltering: true
        },
        { fieldName: "ADDRESS", headerText: "Địa chỉ", allowFiltering: true },
        {
          fieldName: "CREATEDATE",
          headerText: "Ngày ký hợp đồng",
          allowFiltering: true
        },
        {
          fieldName: "ELOADMSISDN",
          headerText: "Số điện thoại",
          allowFiltering: true
        },
        { fieldName: "EMAIL", headerText: "Email", allowFiltering: true },
        { fieldName: "FAX", headerText: "Fax", allowFiltering: true },
        { fieldName: "TINH_TP", headerText: "Tỉnh", allowFiltering: true },
        { fieldName: "STATUS", headerText: "Trạng thái", allowFiltering: true },
        {
          fieldName: "actions",
          headerText: "Thao tác",
          template: function() {
            return {
              template: ActionTemplate
            };
          }
        }
      ]
    };
  },
  methods: {
    rowSelected(row) {
      this.$emit("update:selectedRow", row.data);
    }
  }
};
</script>
