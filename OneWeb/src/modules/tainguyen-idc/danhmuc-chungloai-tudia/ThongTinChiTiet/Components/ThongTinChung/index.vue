<template src="./index.template.html"></template>
<script>
export default {
  name: 'ThongTinChung_ChungLoaiTuDia',
  props: {
    currentItem: { type: Object, required: true },
    controls: { type: Object, required: true },
    loaiTuDiaOptions: { type: Array, required: true },
    hangSanXuatList: { type: Array, required: true },
    vaiTroThietBiList: { type: Array, required: true },
    fullDepthOptions: { type: Array, required: true },
    currentMode: { type: String, required: true }
  }
}
</script>
<style scoped>
.form-control::placeholder {
  color: #999 !important;
}
/deep/ .vue-treeselect__placeholder {
  color: #999 !important;
}
</style>

