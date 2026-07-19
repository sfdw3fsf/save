<template src="./index.template.html"></template>
<script>
export default {
  props: {
    dataSource: Array,
  },
  data: function () {
    return {};
  },
  methods: {
    test(d) {
      console.log(d);
      this.dataSource = d;
    },
  },
};
</script>
