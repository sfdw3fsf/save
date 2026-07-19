<template>
    <b-modal
      size="xl"
      dialog-class="dialog-content"
      body-class="body-content"
      content-class="popup-box"
      v-model="visible"
      hide-footer
      hide-header
    >
<div style="margin: -1rem">
  <Payment ref="dlgPayment"/>
</div>
</b-modal>
</template>
<script>
import Payment from "@/modules/contract/setup/Payment"
export default {
  components: {
    Payment
  },
  data: () => ({
    visible: false
  }),
  methods: {
    show (visible, maGD) {
      this.visible = visible
      setTimeout(() => {
        this.$refs.dlgPayment.txtMaGD = maGD
      }, 1000)
    }
  }
}
</script>
