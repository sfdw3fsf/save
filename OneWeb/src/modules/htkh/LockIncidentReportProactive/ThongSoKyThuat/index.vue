<template src="./index.html"></template>

<script>
export default {
  name: 'ThongSoKyThuat',
  data() {
    return {}
  },
  props: {
    tskt: {
      type: Array,
      required: true
    }
  }
}
</script>

<style scoped>
.bold-alert-title {
  font-size: 13px;
  font-weight: bold;
  color: black;
}
.bold-alert-text {
  font-size: 16px;
  font-weight: bold;
  color: red;
}
</style>
