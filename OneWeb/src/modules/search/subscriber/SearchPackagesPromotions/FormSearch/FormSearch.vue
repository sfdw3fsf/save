<template src="./FSPackagesPromotionsIndex.html"></template>

<script>
import axios from "axios";
import moment from "moment";

export default {
  name: "FormSearch",
  data() {
    return {
      thangKM: [],
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
    this.loadKyHoaDon();
  },
  methods: {
    submit(e) {
      if (e.keyCode == 13) {
        this.$parent.onSubmitSearch();
      }
    },
    async loadKyHoaDon() {
      try {
        let rs = await this.axios.post(
          "web-ccdv/capnhat_loai_thietbi/lay_ds_chukyno",
          {}
        );
        this.thangKM = rs.data.data;
        if (this.thangKM.length > 0) {
          this.form.thangKM = this.thangKM[0].chukyno;
        }
      } catch (e) {
        console.log(e);
      }
    },
  },
};
</script>
