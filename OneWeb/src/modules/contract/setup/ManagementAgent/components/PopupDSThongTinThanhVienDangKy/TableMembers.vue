<template>
  <DataGrid
    v-bind:columns="columns"
    v-bind:dataSource="dsMember"
    :showColumnCheckbox="false"
    :allowSelection="false"
    :allowPaging="true"
    :showFilter="true"
    :enabledSelectFirstRow="false"
    @rowSelected="rowSelected"
  >
  </DataGrid>
</template>

<script>
export default {
  name: "TableMembers",
  props: {
    dsMember: {
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
        {
          fieldName: "USERNAME",
          headerText: "Tên truy cập",
          allowFiltering: true
        },
        {
          fieldName: "MSISDN",
          headerText: "MSISDN",
          allowFiltering: true
        },
        { fieldName: "PHONE", headerText: "Điện thoại", allowFiltering: true },
        {
          fieldName: "FAX",
          headerText: "Fax",
          allowFiltering: true
        },
        {
          fieldName: "EMAIL",
          headerText: "Email",
          allowFiltering: true
        },
        {
          fieldName: "CREATEDATE",
          headerText: "Ngày ĐK",
          allowFiltering: true
        },
        {
          fieldName: "PROVINCEID",
          headerText: "Tỉnh",
          allowFiltering: true
        },
        { fieldName: "STATUS", headerText: "Trạng thái", allowFiltering: true }
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
