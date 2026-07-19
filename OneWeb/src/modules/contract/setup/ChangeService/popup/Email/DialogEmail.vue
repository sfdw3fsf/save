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
      <ThongTinEmail />
    </div>
  </b-modal>
</template>
<script>
import ThongTinEmail from "../../../ChangeContractOwner/popup/ThongTinEmail.vue";
export default {
  components: {
    ThongTinEmail
  },
  data: () => ({
    visible: false,
    source: null
  }),
  methods: {
    show(visible, source) {
      this.visible = visible;
      this.source = source;
    },
    onAccept(data) {
      console.log("data:", data);
      this.$emit("onAccept", { source: this.source, data });
      this.show(false);
    }
  }
};
</script>
