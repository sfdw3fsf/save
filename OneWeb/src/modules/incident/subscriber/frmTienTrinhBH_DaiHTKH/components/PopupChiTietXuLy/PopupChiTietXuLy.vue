<template>
  <b-modal
    ref="chiTietXuLyRef"
    modal-class="fullscreen-modal"
    body-class="p-0"
    hide-footer
    static
    @close="onClose"
  >
    <ProcessAcceptanceIncident ref="processAcceptanceIncident" />
  </b-modal>
</template>

<script>
import ProcessAcceptanceIncident from "@/modules/incident/subscriber/ProcessAcceptanceIncident";
export default {
  name: "PopupChiTietXuLy",
  components: {
    ProcessAcceptanceIncident
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
      document.querySelector("#ProcessAcceptanceIncident.main-wrapper").style.position = 'initial'

      this.$refs.chiTietXuLyRef.show();
    },
    onClose() {
      document.querySelector(".dashboard-header").style.zIndex = "initial";
      document.querySelector("#ProcessAcceptanceIncident.main-wrapper").style.postition = "fixed";
      this.$refs.chiTietXuLyRef.hide();
    },
    setMaTb() {
      const ma_tb = this.selectedRow.ma_tb || "";
      this.$refs.processAcceptanceIncident.MaTB = ma_tb;
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
