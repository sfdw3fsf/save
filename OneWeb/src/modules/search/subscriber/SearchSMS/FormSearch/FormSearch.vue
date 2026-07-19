<template src="./index.html"></template>

<script>
import axios from "axios";

export default {
  name: "FormSearch",
  data() {
    return {
      // cbodauSo: {
      //   DataSource: [],
      //   value: null,
      // },
      config: {
        dateConfig: {
          value: new Date(),
          format: "dd/MM/yyyy",
          max: new Date(),
        },
      },
      dauSo: [],
      DATA_REMAIN: "",
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
    // this.GetDauSo();
  },
  methods: {
    submit(e) {
      if (e.keyCode == 13) {
        this.$parent.onSubmitSearch();
      }
    },
    async GetDauSo() {
      try {
        let rp = await this.axios.post(
          "ccbs/danhMuc/ts_danhmuc_tracuu_sms_dauso",
          {}
        );
        if (rp.data.data.length > 0) {
          // this.cbodauSo.DataSource = rp.data.data;
          // this.cbodauSo.value = this.cbodauSo.DataSource[0]["id"];
          this.$parent.cbodauSo.DataSource = rp.data.data;
          this.$parent.cbodauSo.value = this.cbodauSo.DataSource[0]["id"];

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
<style>
.dataremain {
  border: none;
  display: block;
  background: transparent;
  -webkit-appearance: none;
}
</style>
