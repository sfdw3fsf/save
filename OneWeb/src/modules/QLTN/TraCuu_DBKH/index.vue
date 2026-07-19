<template src="./index.html"></template>
<style scoped></style>

<script>
export default {
  components: {},
  props:{},
  data() {
    return {
      sources: [],
      objSelected: null,
    };
  },
  watch: {},
  created() {},
  methods: {
    ChapNhan() {
      this.$emit('onChapNhan',this.objSelected)
    },
    thuebao_selectedRowChanged(data) {
      if (data) {
        this.objSelected = data;
      } else {
        this.objSelected = null;
      }
    },
  },
};
</script>
