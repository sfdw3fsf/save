<template src="./index.template.html"></template>
<script>

export default {
  name: 'ModalPhanChiaDoanCapChenKetCuoi',
  props: { modalID: null },
  components: {
  },
  created: async function () {
  },
  data: function () {
    return {
      data: null,
      resolvePromise: null,
      rejectPromise: null,
      chieudaicap: null,
      value: 0,
      chieudaicap_truoc: null,
      chieudaicap_sau: null
    }
  },
  watch: {
    value (val) {
      this.chieudaicap_truoc = Math.round((val * this.chieudaicap) / 100)
      this.chieudaicap_sau = this.chieudaicap - this.chieudaicap_truoc
    }
  },
  computed: {
    getchieudaicap_truoc () {
      // this.chieudaicap_truoc = Math.round((this.value * this.chieudaicap) / 100)
      return this.chieudaicap_truoc === null ? null : this.chieudaicap_truoc + ' m'
    },
    getchieudaicap_sau () {
      // this.chieudaicap_sau = this.chieudaicap - this.chieudaicap_truoc
      return this.chieudaicap_sau === null ? null : this.chieudaicap_sau + ' m'
    }
  },
  methods: {
    formatTooltip (val) {
      let v = Math.round(this.chieudaicap * val / 100)
      return v.toString() + ' m'
    },

    show (chieudaicap) {
      return new Promise((resolve, reject) => {
        this.chieudaicap = chieudaicap
        this.$bvModal.show(this.modalID)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    onXacNhan () {
      let data = {
        chieudaicap_truoc: this.chieudaicap_truoc,
        chieudaicap_sau: this.chieudaicap_sau
      }
      this.resolvePromise({ok: true, data: data})
      this.$bvModal.hide(this.modalID)
    },

    closeModal: function () {
      this.resolvePromise({ok: false, data: null})
      this.$bvModal.hide(this.modalID)
    },
    hiddenModal: async function () {
      this.chieudaicap_truoc = null
      this.chieudaicap_sau = null
      this.value = 0
    },
    shownModal: async function () {
      this.value = 50
    }
  },
  mounted: function () {
  }
}
</script>
<style>
</style>
