<template src="./index.html">

</template>

<script>
export default {
  name: "FormSearch",
  data() {
    return {
      kyHD: []
    }
  },
  props: {
    value: {
      type: Object,
      required: true
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
