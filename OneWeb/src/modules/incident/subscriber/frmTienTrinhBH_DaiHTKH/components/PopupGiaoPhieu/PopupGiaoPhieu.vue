<template>
  <b-modal
    ref="giaoPhieuRef"
    modal-class="fullscreen-modal"
    body-class="p-0"
    hide-footer
    static
    @close="onClose"
  >
    <HandoverIncident ref="handoverIncident" />
  </b-modal>
</template>

<script>
import HandoverIncident from "@/modules/incident/subscriber/HandoverIncident";
export default {
  name: "PopupGiaoPhieu",
  components: {
    HandoverIncident
  },
  props: {
    selectedRow: {
      type: Object,
      default: () => {}
    }
  },
  watch: {
    selectedRow: {
      handler() {
        this.setMaTb();
      },
      deep: true
    }
  },
  methods: {
    async show() {
      document.querySelector(".dashboard-header").style.zIndex = -1;
      this.$refs.giaoPhieuRef.show();
    },
    onClose() {
      document.querySelector(".dashboard-header").style.zIndex = "initial";
      this.$refs.giaoPhieuRef.hide();
    },
    setMaTb() {
      const ma_tb = this.selectedRow.ma_tb || "";
      this.$refs.handoverIncident.txtMaTB = ma_tb;
    }
  }
};
</script>

<style>
.fullscreen-modal .modal-dialog {
  max-width: 100%;
  margin: 0;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  height: 100vh;
  padding: 25px;
  display: flex;
  position: fixed;
  z-index: 100000;
}
</style>
