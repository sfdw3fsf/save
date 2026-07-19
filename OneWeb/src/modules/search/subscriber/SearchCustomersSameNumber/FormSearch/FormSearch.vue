<template src="./FSCustomersSameNumberIndex.html"></template>

<script>
import axios from "axios";

export default {
  name: "FormSearch",
  data () {
    return {}
  },
  props: {
    value: {
      type: Object,
      required: true
    }
  },
  computed: {
    form: {
      get () {
        return this.value
      },
      set (form) {
        this.$emit("input", form)
      }
    }
  },
  mounted() {},
  methods: {
    submit (e) {
      if (e.keyCode == 13) {
        this.$parent.onSubmitSearch()
      }
    }
  }
}
</script>
<style scoped>
</style>
