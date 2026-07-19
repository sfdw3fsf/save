<template src="./ThuHo.html">

</template>

<script>
export default {
  name: "ThuHo",
  props: {
    value: {
      type: Object,
      required: true
    },
    kyHD: {
      type: Array,
      default: []
    },
    buucuc: {
      type: Array,
      default: []
    },
    quaythu: {
      type: Array,
      default: []
    },
    thungan: {
      type: Array,
      default: []
    },
    tinh: {
      type: Array,
      default: []
    },
    trangthai: {
      type: Array,
      default: []
    },
  },
  computed: {
    form: {
      get() {
        return this.value;
      },
      set(form) {
        this.$emit('input', form);
      }
    }
  },
}
</script>

<style scoped>

</style>
