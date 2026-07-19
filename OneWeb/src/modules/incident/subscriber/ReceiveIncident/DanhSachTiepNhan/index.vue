<template src="./index.html"></template>

<script>
import Const from './const';
export default {
  name: "DanhSachTiepNhan",
  data() {
    return {
      cols: Const.cols,
      menuItems: Const.contextMenuItems
    }
  },
  props: {
    dsTiepNhan: {
      type: Array,
      required: true
    },
  },
  methods: {
    dataBound() {
      // this.$refs.dsTiepNhan.grid.autoFitColumns();
    },
    reset() {
      this.$refs.dsTiepNhan.grid.clearSelection();
    },
    contextMenuClick(args) {
      let data = args.rowInfo.rowData;
      if(args.item.id === 'dstn_showport') {
        this.$emit('showPort', data);
      } else if (args.item.id === 'dstn_xoa') {
        this.$emit('xoa', data);
      }
    },
    selectedRowChanged(item) {
      this.$emit('selectedRowChanged', item);
    },
    togglePanel(panelName) {
      this.$emit('togglePanel', panelName);
    }
  }
}
</script>

<style scoped>
</style>
