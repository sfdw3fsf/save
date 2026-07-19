<template>
  <b-modal
    id="popupDoiCong"
    ref="popupDoiCong"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0 chuyento"
    @show="handleShowModal"
  >
    <ThayDoiThongSoInternet ref="ThayDoiThongSoInternet" :outData="propData" />
  </b-modal>
</template>
<script>
import ThayDoiThongSoInternet from "@/modules/EXTS/QUANTRI/ThayDoiThongSoInternet"
export default {
  props() {
    return {
      propData: {}
    }
  },
  components: {
    ThayDoiThongSoInternet
  },
  methods: {
    showModal() {
      this.$nextTick(() => {
        this.$refs.popupDoiCong.$refs.ThayDoiThongSoInternet.setPropData(this.propData)
        // this.$refs["popupDoiCong"].show();
        this.$bvModal.show("popupDoiCong")
      });
    },
    hideModal() {
      this.$bvModal.hide("popupDoiCong")
    },
    handleShowModal() {}
  },
};
</script>
<style>
  .chuyento .page-content {
    position: unset;
  }
</style>
