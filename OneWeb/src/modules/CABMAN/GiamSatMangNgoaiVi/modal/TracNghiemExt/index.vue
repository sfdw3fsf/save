<template src="./index.template.html"></template>
<script>
export default {
  components: { },
  name: 'Modaltracnghiem',
  props: {
    modalID: {
      type: String,
      default: 'Modaltracnghiem'
    }
  },
  watch: {
  },
  computed: {
    getData () {
      return this.tracnghiem
    }
  },
  data () {
    return {
      option: 0,
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
        this.$bvModal.show(this.modalID)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    show2 (tracnghiem) {
      return new Promise((resolve, reject) => {
        this.tracnghiem = tracnghiem
        this.$bvModal.show(this.modalID)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    showWithSetChecked (tracnghiem, index) {
      return new Promise((resolve, reject) => {
        this.tracnghiem = tracnghiem
        this.checked = index
        this.$bvModal.show(this.modalID)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    resetModal () { this.option = null },
    closeModal: function () {
    },
    onXacnhan () {
      this.resolvePromise({ok: true, option: this.option, data: this.tracnghiem.data})
      this.$bvModal.hide(this.modalID)
    },
    onHuybo () {
      this.resolvePromise({ok: false, option: null, data: null})
      this.$bvModal.hide(this.modalID)
    }
  },
  created () {
  },
  mounted () {
  }
}
</script>
