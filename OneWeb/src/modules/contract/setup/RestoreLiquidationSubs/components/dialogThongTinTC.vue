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
  <PopupThongTinTC v-if="visible" :dulieu="dulieu" @form-close="onClose"/>
</div>
</b-modal>
</template>
<script>
import PopupThongTinTC from "../../../setup/InstallNewSubs/Popups/popupThongTinTC.vue"
export default {
  components: {
    PopupThongTinTC
  },
  data: () => ({
    visible: false,
    contentVisible: false,
    dulieu: null
  }),
  methods: {
    show (visible, data) {
      this.dulieu = data
      this.visible = visible
    },
    onClose (data) {
      this.$emit('onAccept', data)
      this.show(false)
    }
  }
}
</script>
