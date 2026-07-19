<template src="./index.template.html"></template>
<script>
export default {
  name: 'ThongTinPort_ChungLoaiTBM',
  props: {
    currentItem: { type: Object, required: true },
    controls: { type: Object, required: true },
    moduleList: { type: Array, required: true },
    mucDichSuDungList: { type: Array, required: true },
    loaiCongOptions: { type: Array, required: true },
    currentMode: { type: String, required: true }
  }
}
</script>
<style scoped>
.form-control::placeholder {  color: #999 !important; }
/deep/ .vue-treeselect__placeholder {  color: #999 !important; }
</style>



