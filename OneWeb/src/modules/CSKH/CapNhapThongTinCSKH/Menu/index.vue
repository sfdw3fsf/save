<template src="./index.html"></template>
<script>
import modal from "../frmTTChamSocKH.vue";
export default {
  components: { modal },
      mounted() {
          this.$refs["frmTTChamSocKH"].openDialog();
      }
};
</script>


