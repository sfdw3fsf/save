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
      <TienKM v-if="visible" :dulieu="dulieu" @form-close="onClose" />
    </div>
  </b-modal>
</template>
<script>
import TienKM from "../../InstallNewSubs/Popups/popupTienKM.vue";
export default {
  components: {
    TienKM
  },
  data: () => ({
    visible: false,
    dulieu: null
  }),
  methods: {
    show(visible, data) {
      this.dulieu = data;
      this.visible = visible;
    },
    onClose(data) {
      this.$emit("onAccept", data);
      this.show(false);
    }
  }
};
</script>
