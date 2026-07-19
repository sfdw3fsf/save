<template src="./index.html"></template>

<script>
export default {
  name: 'ThongTinTiepNhan',
  data() {
    return {}
  },
  props: {
    value: {
      type: Object,
      required: true
    },
    options: {
      type: Object,
      required: true
    }
  },
  computed: {
    tiepNhanInfo: {
      get() {
        return this.value
      },
      set(tiepNhanInfo) {
        this.$emit('input', tiepNhanInfo)
      }
    }
  },
  methods: {
    onEnterMaTB(event) {
      this.$emit('onEnterMaTB', event.target.value)
    },
    showFormTraCuuDanhBa() {
      this.$emit('showFormTraCuuDanhBa')
    },
    togglePanel(panelName) {
      this.$emit('togglePanel', panelName)
    }
  },
  async mounted() {}
}
</script>

<style scoped>
.btn-second {
  background-color: #fff;
  border-color: #0176ff;
  color: #0176ff;
  font-weight: 600;
}

.btn-second:hover {
  background: #0176ff;
  color: #fff;
}

.form-control[readonly] {
  background: #fff;
}
</style>
