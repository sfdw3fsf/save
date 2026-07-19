<template src="./index.template.html"></template>
<script>
export default {
  name: 'SearchFormChungLoaiTuDia',
  props: {
    loaiTuDiaOptions: { type: Array, default: () => [] },
    hangSanXuatList: { type: Array, default: () => [] }
  },
  data() {
    return {
      criteria: {
        chungloaitdId: null,
        modelThietbi: '',
        hangsxId: null,
        loaitudiaId: null
      }
    }
  },
  methods: {
    onSearch() {
      this.$emit('search', { ...this.criteria })
    },
    onReset() {
      this.criteria = { chungloaitdId: null, modelThietbi: '', hangsxId: null, loaitudiaId: null }
      this.$emit('search', { ...this.criteria })
    }
  }
}
</script>
<style scoped>
.w160 { width: 160px !important; }
</style>

