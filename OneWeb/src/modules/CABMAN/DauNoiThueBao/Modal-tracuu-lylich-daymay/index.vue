<template src="./index.template.html"></template>
<style>
    .inlylich-daymay .page-content {
        overflow: visible !important;
        height: fit-content;
        background-color: white;
    }
    .modal .modal-inlylich-daymay-size {
    max-width: 85% !important;
    }
</style>
<script>
import TraCuuLyLichDayMay from './tracuu-lylich-daymay'
export default {
  components: {
    TraCuuLyLichDayMay
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
    onModalShown () {},
    setData (data) {
      this.data = data
    }
  }
}
</script>
<style scoped>
</style>
