<template src="./index.html"></template>

<script>
import Const from './const';
export default {
  name: "LichSuBaoHong",
  data() {
    return {
      cols: Const.cols
    }
  },
  props: {
    lsBaoHong: {
      type: Array,
      required: true
    },
  },
  methods: {
    dataBound() {
      this.$refs.lsBaoHong.grid.autoFitColumns();
    },
  }
}
</script>

<style scoped>
</style>
