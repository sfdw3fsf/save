<template src="./index.template.html"></template>
<style>
    .hoso-dauchuyen-daunoi-thuebao .page-content {
        overflow: visible !important;
        height: fit-content;
        background-color: white;
    }
</style>
<script>
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
export default {
  components: {
    SearchAccount
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
      this.$emit('hidden', this.data)
    },
    setData (data) {
      this.data = data
    },
    formClose (value) {
      this.data = value
      console.log('formClose', value)
      this.hide()
    }
  }
}
</script>
