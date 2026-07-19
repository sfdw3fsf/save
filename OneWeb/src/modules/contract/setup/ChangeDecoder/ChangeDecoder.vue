<template>   
 <popupTCTTTB  ref="popupTCTTTB"/>
</template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import popupTCTTTB from "./popupTCTTTB.vue";
export default{
    components: {popupTCTTTB},
    mounted () {
    this.$refs.popupTCTTTB.showModal()
  },
}
</script>

