<template src="./index.template.html"></template>
<style>
    .giao-phieu-xu-ly-khieu-nai .page-content {
        overflow: visible !important;
        height: fit-content;
        background-color: white;
    }
    @media (min-width: 992px)
    {
      .modal .modal-huge {
      max-width: 90% !important;
      width: 90% !important;
    }
}
</style>
<script>
import GiaoPhieuKhieuNai from '../../../GiaoPhieuKhieuNai/index.vue'
export default {
  components: {
    GiaoPhieuKhieuNai
  },
  data () {
    return {
      data: null
    }
  },
  methods: {
    show () {
      return this.$refs['ref-modal'].show()
    },
    hide () {
      this.$refs['ref-modal'].hide()
    },
    onModalHidden () {
      this.data = null
      this.$emit('hidden')
    },
    setData (data) {
      this.data = data
    },
    onSuccess () {
      this.$emit('change')
    }
  }
}
</script>
