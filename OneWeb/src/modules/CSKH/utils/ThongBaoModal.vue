<template>
  <b-modal :id="idModal" ref="thongBaoModal" title="Thông báo!">
    <span>{{ content }}</span>
    <template #modal-footer="{ ok }">
      <b-button variant="primary" style="color: white" @click="$bvModal.hide(idModal)"
        >OK</b-button
      >
    </template>
  </b-modal>
</template>
<script>
export default {
  props: {
    content: String,
    idModal: String,
  }
};
</script>