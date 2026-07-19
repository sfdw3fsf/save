<template>
    <div>
      <InPhieuChi ref="popupInPhieuChi"/>
    </div>
</template>
<script>
import InPhieuChi from '../../../print/InPhieuChi/InPhieuChi'

export default {
  components: {
    InPhieuChi
  },
  mounted () {
    this.$refs.popupInPhieuChi.showModal()
  },
  data () {
    return {
      open: false
    }
  }
}
</script>
