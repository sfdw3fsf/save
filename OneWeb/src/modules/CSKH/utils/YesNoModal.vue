<template>
  <b-modal :id="idModal" ref="xoaConfirm" title="Thông báo!">
    <span>{{ content }}</span>
    <template #modal-footer="{ ok, cancel }">
      <b-button
        variant="secondary"
        style="color: white"
        @click="onCancel"
        >Hủy</b-button
      >
      <b-button variant="primary" style="color: white" @click='onOk'
        >Đồng ý</b-button
      >
    </template>
  </b-modal>
</template>
<script>
export default {
  props: {
    content: String,
    idModal: String,
  },
  methods: {
    onOk() {
      this.$emit("onOk");
      this.$bvModal.hide(this.idModal)
    },
    onCancel() {
      this.$emit("onCancel");
      this.$bvModal.hide(this.idModal)
    },
  },
};
</script>