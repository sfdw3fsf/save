<template src="./index.template.html"></template>
<script>
export default {
  name: 'ThongTinQuanLy',
  props: {
    currentItem: {
      type: Object,
      required: true
    },
    currentMode: {
      type: String,
      required: true
    },
    controls: {
      type: Object,
      required: true
    },
    donViQuanLyList: {
      type: Array,
      default: () => []
    },
    nhanVienList: {
      type: Array,
      default: () => []
    },
    heThongList: {
      type: Array,
      default: () => []
    },
    danhMucIdcList: {
      type: Array,
      default: () => []
    }
  },
  methods: {
    onChangeDonViQuanLy() {
      this.$emit('change-don-vi-quan-ly')
    },
    onChangeNhanVienQuanLy() {
      this.$emit('change-nhan-vien-quan-ly')
    },
    onChangeHeThong() {
      this.$emit('change-he-thong')
    },
    onChangePhamViIdc() {
      this.$emit('change-pham-vi-idc')
    }
  }
}
</script>
