<template src="./index.template.html"></template>
<script>
export default {
  props: {
    kyHieuCap: "",
    dataSource: Array,
  },
  watch: {},
  data: function () {
    return {
      dsTTVT: [],
      dsToKT: [],
      selectedToKT: 0,
      dsTongDai: [],
      selectedTongDai: 0,
      dataSourceValue: this.dataSource,
      kyHieuCapValue: this.kyHieuCap,
    };
  },
  computed: {},
  created: async function () {
    await this.getDsTTVT();
    await this.getToKT();
    await this.getDsTongDai();
  },
  methods: {
    grid_selectedRowChanged: function (dataItem) {
      this.$emit("selectedRowChanged", dataItem);
    },
    getDsTTVT: async function () {
      let rs = await this.$root.context.post(
        "/web-ecms/danhmuc/layDsTTVTTrongToken"
      );
      this.dsTTVT = rs.data;
    },
    getToKT: async function () {
      let rs = await this.$root.context.post("/web-ecms/danhmuc/layDsToKT", {
        id: $("#selectTTVT").val(),
      });
      this.dsToKT = rs.data;
    },
    getDsTongDai: async function () {
      let rs = await this.$root.context.post("/web-ecms/danhmuc/layDsVeTinh", {
        id: this.selectedToKT,
      });
      this.dsTongDai = rs.data;
    },
    getDsCap: async function () {
      if (this.selectedTongDai > 0) {
        let rs = await this.$root.context.post(
          "/web-cabman/danhmuc/layDsCapTheoTramTBI",
          {
            id: this.selectedTongDai,
          }
        );
        return rs.data;
      }
      else {
        return [];
      }
    },
    getDsCapTheoKyHieu: async function () {
      if (this.kyHieuCapValue != "") {
        let rs = await this.$root.context.post(
          "/web-cabman/danhmuc/layDsCapTheoKyHieu",
          { kyHieu: this.kyHieuCapValue }
        );
        return rs.data;
      } else {
        let r = await this.getDsCap();
        return r;
      }
    },
    selectTTVT_changed: async function () {
      this.$root.showLoading(true);
      try {
        this.dataSourceValue = [];
        this.dsTongDai = [];
        this.dsToKT = [];
        this.selectedToKT = 0;
        this.selectedTongDai = 0;
        await this.getToKT();
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    selectToKT_changed: async function () {
      this.$root.showLoading(true);
      try {
        this.dataSourceValue = [];
        this.dsTongDai = [];
        this.selectedTongDai = 0;
        await this.getDsTongDai();
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    selectTongDai_changed: async function () {
      this.$root.showLoading(true);
      try {
        this.dataSourceValue = await this.getDsCap();
        this.$emit("dataSourceChanged", this.dataSourceValue);
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    inputKyHieuCap_enter: async function () {
      this.$root.showLoading(true);
      try {
        this.dataSourceValue = await this.getDsCapTheoKyHieu();
        this.$emit("dataSourceChanged", this.dataSourceValue);
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
  },
};
</script>
