<template>
  <DataGrid
    v-bind:columns="columns"
    v-bind:dataSource="lichSuThaoTac"
    :showColumnCheckbox="false"
    :allowSelection="false"
    :allowPaging="true"
    :showFilter="true"
  >
  </DataGrid>
</template>

<script>
import api from "../../api";
export default {
  name: "TableDSDaiLy",
  props: {
    selectedRow: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      columns: [
        {
          fieldName: "ELOADMSISDN",
          headerText: "Số TB/User web"
        },
        {
          fieldName: "CREATEDATE",
          headerText: "Ngày cập nhật"
        },
        {
          fieldName: "ELOADMSISDN",
          headerText: "Nội dung"
        },
        {
          fieldName: "IP",
          headerText: "IP"
        },
        {
          fieldName: "IP",
          headerText: "Đại lý"
        },
        {
          fieldName: "IP",
          headerText: "Trạng thái"
        }
      ],
      lichSuThaoTac: []
    };
  },
  watch: {
    selectedRow: {
      async handler(newVal) {
        const { ELOADMSISDN } = newVal;
        await this.getLichSuThaoTacTB(ELOADMSISDN);
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    rowSelected(row) {
      this.$emit("update:selectedRow", row.data);
    },
    async getLichSuThaoTacTB(so_tb) {
      const payload = {
        so_tb
      };
      const response = await api.getLichSuDaiLy(this.$root.axios, payload);
      const { result, error_code } = response.data;
      if (error_code === "BSS-00000000") {
        this.lichSuThaoTac = result || [];
      }
    }
  }
};
</script>
