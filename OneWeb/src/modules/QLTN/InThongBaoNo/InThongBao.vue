<template>
    <div>
        <button ref="btn-show-modal" class="btn btn-sm btn-info" hidden>Show modal</button>
        <modal ref="popupInThongBaoNo"/>
    </div>
</template>
<script>
import modal from "./index.vue";
export default {
  components: { modal },
  mounted() {
    this.$refs["popupInThongBaoNo"].openDialog();
  }
};
</script>
