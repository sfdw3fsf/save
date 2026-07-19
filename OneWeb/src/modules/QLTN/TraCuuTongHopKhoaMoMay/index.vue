<template src="./index.html"></template>
<script>
import modal from "./TraCuuTongHopKhoaMoMay.vue";
export default {
  components: { modal },
  mounted() {
        this.$refs["popupTraCuu"].openDialog();
  }
};
</script>


