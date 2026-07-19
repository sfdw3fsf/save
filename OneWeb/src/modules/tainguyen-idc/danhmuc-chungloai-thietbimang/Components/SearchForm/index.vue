<template src="./index.template.html"></template>
<script>
export default {
  name: 'SearchFormChungLoaiTBM',
  props: {
    loaiThietBiMangOptions: { type: Array, default: () => [] },
    hangSanXuatList: { type: Array, default: () => [] }
  },
  data() {
    return {
      criteria: {
        chungloaitbmId: null,
        modelThietbi: '',
        hangsxId: null,
        loaithietbimangId: null
      }
    }
  },
  methods: {
    onSearch() { this.$emit('search', { ...this.criteria }) },
    onReset() {
      this.criteria = { chungloaitbmId: null, modelThietbi: '', hangsxId: null, loaithietbimangId: null }
      this.$emit('search', { ...this.criteria })
    }
  }
}
</script>
<style scoped>
.w160 { width: 160px !important; }
</style>



