<template src="./index.template.html"></template>
<script>
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  name: 'ThongTinThietBi',
  components: {
    Treeselect
  },
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
    loaiThietBiList: {
      type: Array,
      default: () => []
    },
    mangThietBiList: {
      type: Array,
      default: () => []
    },
    hangSanXuatList: {
      type: Array,
      default: () => []
    },
    trangThaiTaiSanList: {
      type: Array,
      default: () => []
    },
    trangThaiVanHanhList: {
      type: Array,
      default: () => []
    },
    thietBiChaList: {
      type: Array,
      default: () => []
    },
    thanhPhanThietBiList: {
      type: Array,
      default: () => []
    },
    nhaCungCapList: {
      type: Array,
      default: () => []
    },
    // Image upload state properties
    uploadingIcon: {
      type: Boolean,
      default: false
    },
    pendingIconFile: {
      type: Object,
      default: null
    },
    loadingImageUrl: {
      type: Boolean,
      default: false
    },
    pendingIconPreviewUrl: {
      type: String,
      default: ''
    },
    displayIconUrl: {
      type: String,
      default: ''
    }
  },
  methods: {
    onChangeLoaiThietBi() {
      this.$emit('change-loai-thiet-bi')
    },
    onChangeMangThietBi() {
      this.$emit('change-mang-thiet-bi')
    },
    onChangeHangSanXuat() {
      this.$emit('change-hang-san-xuat')
    },
    onChangeTrangThaiTaiSan() {
      this.$emit('change-trang-thai-tai-san')
    },
    onChangeTrangThaiVanHanh() {
      this.$emit('change-trang-thai-van-hanh')
    },
    onChangeThietBiCha() {
      this.$emit('change-thiet-bi-cha')
    },
    onChangeThanhPhanThietBi(selectedValues) {
      this.$emit('change-thanh-phan-thiet-bi', selectedValues)
    },
    onClickUploadIcon() {
      this.$emit('click-upload-icon')
    },
    onRemoveIcon() {
      this.$emit('remove-icon')
    },
    onChangeIconFile(event) {
      this.$emit('change-icon-file', event)
    },
    onChangeNoiSanXuat() {
      this.$emit('change-noi-san-xuat')
    },
    // Normalizer function for treeselect
    normalizer(node) {
      return {
        id: node.ID || node.id,
        label: node.TEN || node.name || node.label,
        children: node.children || node.items
      }
    },
    // Normalizer function for treeselect
    normalizer(node) {
      return {
        id: node.ID || node.id,
        label: node.TEN || node.name || node.label,
        children: node.children || node.items
      }
    }
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