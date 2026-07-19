<template src="./index.template.html"></template>
<script>
export default {
  components: { },
  name: 'Modaltracnghiem',
  props: {
    id: String
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
      },
      checked: -1
    }
  },
  updated () {
  },
  methods: {
    show (tracnghiem) {
      return new Promise((resolve, reject) => {
        this.tracnghiem = tracnghiem
        this.$bvModal.show(this.id)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    showWithSetChecked (tracnghiem, index) {
      return new Promise((resolve, reject) => {
        this.tracnghiem = tracnghiem
        this.checked = index
        this.$bvModal.show(this.id)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    resetModal () { this.option = null },
    closeModal: function () {
    },
    onXacnhan () {
      this.resolvePromise({ok: true, option: this.option, data: this.tracnghiem.data})
      this.$bvModal.hide(this.id)
    },
    onHuybo () {
      this.resolvePromise({ok: false, option: null, data: null})
      this.$bvModal.hide(this.id)
    }
  },
  created () {
  },
  mounted () {
  }
}
</script>
