<template src="./TraCuuThueBaoTheoCap.html"></template>
<script>
export default {
  components: { },
  name: 'Modaltracnghiem',
  props: {
  },
  watch: {
  },
  computed: {

  },
  data () {
    return {
      option: null,
      resolvePromise: null,
      rejectPromise: null,
      tracnghiem: {
        title: null,
        caption: null,
        options: [],
        data: null
      }
    }
  },
  updated () {
  },
  methods: {
    show (tracnghiem) {
      return new Promise((resolve, reject) => {
        this.tracnghiem = tracnghiem
        this.$bvModal.show('modalConfirmationTracNghiem')
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    resetModal () { this.option = null },
    closeModal: function () {
    },
    onXacnhan () {
      this.resolvePromise({ok: true, option: this.option, data: this.tracnghiem.data})
      this.$bvModal.hide('modalConfirmationTracNghiem')
    },
    onHuybo () {
      this.resolvePromise({ok: false, option: null, data: null})
      this.$bvModal.hide('modalConfirmationTracNghiem')
    }
  },
  created () {
  },
  mounted () {
  }
}
</script>
