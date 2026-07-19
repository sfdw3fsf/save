<template src="./NhoThuHo.html">

</template>

<script>
export default {
  name: "NhoThuHo",
  props: {
    value: {
      type: Object,
      required: true
    },
    kyHD: {
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
