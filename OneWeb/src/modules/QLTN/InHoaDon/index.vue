<template src="./index.html"></template>
<script>
import modal from "./InHoaDon.vue";
export default {
  components: { modal },
  mounted() {
        this.$refs["popupInHoaDon"].openDialog();
  }
};
</script>


