<template>
  <div>
    <InPhieuChi ref="popupInPhieuChi"/>
  </div>
</template>

<script>

import InPhieuChi from './InPhieuChi'

export default {
  name: 'InphieuChiTest',
  components: {InPhieuChi},
  mounted () {
    this.$refs.popupInPhieuChi.showModal()
  }
}
</script>

<style scoped>

</style>
