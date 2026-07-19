<template src="./index.html"></template>
<script>
import ModalThongTinToaNha from './components/ModalThongTinToaNha/index.vue'

export default {
  name: 'ThongTinChiTietToanha',
  components: {
    ModalThongTinToaNha
  },
  created() {
    this.id = Number(this.$route.query.toanha) || 1
  },
  data() {
    return {
      id: null,
      tab_index: 1
    }
  },
  methods: {
    handleDelete(deletedId) {
      // Handle the deletion of the building
      this.$toast.success(`Tòa nhà với ID ${deletedId} đã được xóa thành công.`)
    }
  }
}
</script>
