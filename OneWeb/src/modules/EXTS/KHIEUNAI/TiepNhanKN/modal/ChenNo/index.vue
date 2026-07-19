<template src="./index.template.html"></template>
<script>
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required, minValue, maxValue, maxLength } from 'vuelidate/lib/validators'

export default {
  name: 'ModalChenNo',
  props: { modalID: null },
  components: {
    BssErrorMarker, BssRequiredMarker
  },
  created: async function () {
  },
  data: function () {
    return {

    }
  },
  watch: {

  },
  computed: {

  },
  methods: {
    hiddenModal () {},
    shownModal () {},
    closeModal () {
      this.resolvePromise({ok: false, data: null})
      this.$bvModal.hide(this.modalID)
    },
    show (data) {
      return new Promise((resolve, reject) => {
        this.data = data
        this.$bvModal.show(this.modalID)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    }
  },
  mounted: function () {
  }
}
</script>
<style>
</style>
