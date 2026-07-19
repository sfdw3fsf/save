<template src="./UpdateWrongIDNumber.html"></template>
<style scoped src="./UpdateWrongIDNumber.scss"></style>
<script>
import Vue from "vue";
import UpdateWrongIDNumberAPI from "../api/UpdateWrongIDNumberAPI";
import {
  GridPlugin,
  Filter,
  Resize,
  ExcelExport,
  Sort,
  DetailRow,
  Page,
  Group,
} from "@syncfusion/ej2-vue-grids";

Vue.use(GridPlugin);
export default Vue.extend({
  name: "UpdateWrongIDNumber",

  data() {
    return {
      thuebao:"",
      sogiayto:"",

    };
  },
  methods: {
    capNhap() {
      //this.loading(true);
      console.log(this.searchCondition)
      UpdateWrongIDNumberAPI.update_so_giay_to_sai(this.axios, this.searchCondition)
        .then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.$root.toastSuccess("Đã cập nhập!");
          } else {
            this.$root.toastError("Cập nhập thất bại");
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          //this.loading(false);
        });
    },

  },

  destroyed () {
  },
  computed: {
    searchCondition() {
      return { 
        so_tb: this.thuebao, 
        so_gt: this.sogiayto
      };
    }
  },
});
</script>
