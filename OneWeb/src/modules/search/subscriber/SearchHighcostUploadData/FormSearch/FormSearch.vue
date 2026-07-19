<template src="./FSHighcostUploadDataIndex.html"></template>

<script>
import axios from "axios";

export default {
  name: "FormSearch",
  data() {
    return {
      config: {
        dateConfig: {
          value: new Date(),
          format: "dd/MM/yyyy",
          max: new Date(),
        },
      },
      dstinh: [],
      displayType: [],
      tableType: [],
    };
  },
  props: {
    value: {
      type: Object,
      required: true,
    },
  },
  computed: {
    form: {
      get() {
        return this.value;
      },
      set(form) {
        this.$emit("input", form);
      },
    },
  },
  mounted() {
    this.LoadProvinceCode();
    this.LoadDisplayType();
    this.LoadTableType();
  },

  methods: {
    async LoadProvinceCode() {
      try {
        let rp = await this.axios.post("ccbs/executor/ts_dm_dstinh", {});
        this.dstinh = rp.data.data;
        if (this.dstinh.length > 0) {
          this.form.dstinh = this.dstinh[0];
        }
      } catch (error) {
        console.log(error);
      }
    },
    async LoadDisplayType() {
      try {
        let rp = await this.axios.post(
          "ccbs/danhMuc/ts_danhmuc_cuoccao_kieu_hienthi",
          {}
        );
        this.displayType = rp.data.data;
        if (this.displayType.length > 0) {
          this.form.displayType = this.displayType[0];
        }
      } catch (error) {
        console.log(error);
      }
    },
    async LoadTableType() {
      try {
        let rp = await this.axios.post(
          "ccbs/danhMuc/ts_danhmuc_cuoccao_loaibang",
          {}
        );
        this.tableType = rp.data.data;
        if (this.tableType.length > 0) {
          this.form.tableType = this.tableType[0];
        }
      } catch (error) {
        console.log(error);
      }
    },
  },
};
</script>
<style scoped>
</style>
