<template src="./template.html">  
</template>
<script>
import frmTraPhieuTC from "./frmTraPhieuTC.vue"
export default {
  components: {
    frmTraPhieuTC
  },
  mounted () {
    this.$refs.frmTraPhieuTC.frmTraPhieuTC_Load()
  },
  data() {
    return {
      labelFunctionTitle: ""
    }
  }
}
</script>