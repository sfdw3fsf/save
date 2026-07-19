<template>
    <div>
        <button ref="btn-show-modal" class="btn btn-sm btn-info" hidden>Show modal</button>
        <modal ref="popupTraCuuPhieuYCMoKhoa"/>
    </div>
</template>
<script>
import modal from "./TraCuuPhieuYeuCauMoKhoa.vue";
export default {
  components: { modal },
  mounted() {
    this.$refs["popupTraCuuPhieuYCMoKhoa"].openDialog();
  }
};
</script>
