<template src="./index.template.html"></template>
<script>
export default {
  name: 'ThongTinKheCam_ChungLoaiTuDia',
  props: {
    currentItem: { type: Object, required: true },
    controls: { type: Object, required: true },
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

