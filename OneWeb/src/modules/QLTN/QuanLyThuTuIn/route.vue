<template>
    <div>
        <button ref="btn-show-modal" class="btn btn-sm btn-info" hidden>Show modal</button>
        <modal ref="popupQuanLyThuTuIn"/>
    </div>
</template>
<script>
import modal from "./QuanLyThuTuIn.vue";
export default {
  components: { modal },
  mounted() {
    this.$refs["popupQuanLyThuTuIn"].openDialog();
  }
};
</script>
