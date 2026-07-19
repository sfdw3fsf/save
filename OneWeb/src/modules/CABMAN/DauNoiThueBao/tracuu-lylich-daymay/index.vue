<template src="./index.template.html"></template>
<style>
    .tracuu-lylich-daymay .page-content {
        overflow: visible !important;
        height: fit-content;
        background-color: white;
    }
</style>
<script>
import TraCuu from '../../../ECMS/tracuu-lylich-daymay'
export default {
  components: {
    TraCuu
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
    onSuccessNhapCapThueBao () {
      this.$emit('change')
    }
  }
}
</script>
