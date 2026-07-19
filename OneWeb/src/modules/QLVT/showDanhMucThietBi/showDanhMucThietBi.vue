<template src='./showDanhMucThietBi.html'></template>

<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
// import breadcrumb from "@/components/breadcrumb";
import Vue from "vue";

import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import { Query, Predicate } from "@syncfusion/ej2-data";
import DanhMucThietBi from "../DanhMucThietBi/index.js";

Vue.use(DatePickerPlugin);

export default {
  components: { DanhMucThietBi },
  name: "DanhMucTB",
  mounted() {
    var f = this.$refs.DanhMucTB;
    f.openDialog();
  },

  data() {
    return {};
  },

  computed: {},
  methods: {},
  created: async function () {},
};
</script>