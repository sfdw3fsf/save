<template src="./template.html"></template>
<style src="./style.scss"></style>
<script>
import Vue from "vue";
import { CheckBoxPlugin } from "@syncfusion/ej2-vue-buttons";
Vue.use(CheckBoxPlugin);
export default {
  components: {},
  name: "CaptionTemplate",
  props: {},
  mounted() {
    this.bus.$on("row-selected", (data) => {
      this.selectedItems();
    });
  },
  destroyed() {},
  watch: {},
  data() {
    return { data: {}, someChecked: false, allChecked: false };
  },
  methods: {
    check: function (event) {
      if (event.checked || this.someChecked) {
        const rowInfos = this.$parent.getRows().map((item) => {
          return this.$parent.getRowInfo(item);
        });
        const flagSelectedRowIndexes = rowInfos
          .filter((item) => {
            return this.groupItems.includes(item.rowData.luottao_id);
          })
          .map((item) => item.rowIndex);
        const currentSelectedIndexs = this.$parent.getSelectedRowIndexes();
        currentSelectedIndexs.forEach((item) => {
          if (!flagSelectedRowIndexes.includes(item)) {
            flagSelectedRowIndexes.push(item);
          }
        });
        this.$parent.selectRows(flagSelectedRowIndexes);
      } else {
        const currentSelectedInfos = this.$parent.getSelectedRows().map((item) => {
          return this.$parent.getRowInfo(item);
        });
        const flagSelectedRowIndexes = currentSelectedInfos
          .filter((item) => {
            return !this.groupItems.includes(item.rowData.luottao_id);
          })
          .map((item) => item.rowIndex);
        this.$parent.selectRows(flagSelectedRowIndexes);
      }
    },
    selectedItems() {
      const selectedItems = this.$parent
        .getSelectedRecords()
        .map((item) => item.luottao_id);
      this.allChecked = this.groupItems.every((e) => selectedItems.includes(e));
      this.someChecked =
        this.groupItems.some((e) => selectedItems.includes(e)) && !this.allChecked;
    },
  },

  computed: {
    groupItems() {
      return this.data.items.map((item) => item.luottao_id);
    },
    label() {
      return `${this.data.key} - ${this.data.count} Bản ghi`;
    },
    // selectedRowIndexes() {
    //   return this.$parent.$parent.selectedRowIndexes;
    // },
    // isCheck: {
    //   get() {
    //     return true;
    //   },
    //   // setter
    //   set(newValue) {

    //   },
    // },
  },
};
</script>
