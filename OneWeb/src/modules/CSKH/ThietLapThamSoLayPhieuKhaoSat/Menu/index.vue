<template src="./index.html"></template>
<script>
import modal from "../ThietLapThamSoLayPhieuKhaoSat.vue";
export default {
  components: { modal },
      mounted() {
          this.$refs['btn-show-modal'].click()
      }
};
</script>


