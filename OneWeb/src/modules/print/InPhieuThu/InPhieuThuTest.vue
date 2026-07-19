<template>
  <div>
    <InPhieuThu ref="popupInPhieuThu" ma_gd="HPG-LD/00113977"/>
  </div>
</template>

<script>
import InPhieuThu from './InPhieuThu'
export default {
  name: 'InPhieuThuTest',
  components: {
    InPhieuThu
  },
  mounted () {
    this.$refs.popupInPhieuThu.showModal()
  }
}
</script>

<style scoped>

</style>
