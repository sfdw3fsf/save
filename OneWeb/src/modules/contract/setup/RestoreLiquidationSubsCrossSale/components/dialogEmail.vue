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
  <ThongTinEmail @form-close="show(false)"/>
</div>
</b-modal>
</template>
<script>
import ThongTinEmail from "../../../setup/ChangeContractOwner/popup/ThongTinEmail.vue"
export default {
  components: {
    ThongTinEmail
  },
  data: () => ({
    visible: false
  }),
  methods: {
    show (visible) {
      this.visible = visible
    }
  }
}
</script>
