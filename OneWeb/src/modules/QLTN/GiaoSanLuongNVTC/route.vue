<template>
    <div>
        <button ref="btn-show-modal" class="btn btn-sm btn-info" hidden>Show modal</button>
        <modal ref="popupGiaoSanLuongNVTC"/>
    </div>
</template>
<script>
import modal from "./GiaoSanLuongNVTC.vue";
export default {
  components: { modal },
  mounted() {
    this.$refs["popupGiaoSanLuongNVTC"].openDialog();
  }
};
</script>
