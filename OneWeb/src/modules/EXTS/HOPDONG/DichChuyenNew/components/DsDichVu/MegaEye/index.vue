<template src="./index.html">
</template>
<script>

export default {
  props: {
    dataSource: {
      type: Array,
      default: () => [],
    }
  },
  data: function() {
    return {
      text1:""
    }
  },
  watch:{
    dataSource: function (newValue) {
      this.text1 = newValue
    },
  },
  components: {
  },
}
</script>
<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
