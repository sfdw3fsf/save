<template src="./FSEsimIndex.html"></template>

<script>
import axios from "axios";

export default {
  name: "FormSearch",
  data() {
    return {
      phoneCode: "",
    };
  },
  props: {
    value: {
      type: Object,
      required: true,
    },
  },
  computed: {
    form: {
      get() {
        return this.value;
      },
      set(form) {
        this.$emit("input", form);
      },
    },
  },
  mounted() {
  },
  methods: {
    submit(e) {
      if (e.keyCode == 13) {
        this.$parent.onSubmitSearch();
      }
    },
  },
};
</script>
<style scoped>
</style>
<style>
    .dataremain{
        border: none;
        display: block;
        background: transparent;
        -webkit-appearance:none;
    }
</style>
