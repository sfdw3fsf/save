<template>
    <div>
        <button ref="btn-show-modal" class="btn btn-sm btn-info" hidden>Show modal</button>
        <modal/>
    </div>
</template>
<script>
import modal from "./popup/CauHinhInKHL.vue";
export default {
  components: { modal },
  mounted() {
    this.$bvModal.show("popup-cauHinhSMTP");
  }
};
</script>
