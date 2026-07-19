<template>
  <b-modal
    ref="popupTestport"
    size="lg"
    :title="'Test port: ' + input"
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="popup-body">
        {{test_port}}
    </div>
  </b-modal>
</template>
<script>
export default {
    props: ['test_port', 'input'],
  methods: {
    showModal() {
      this.$refs["popupTestport"].show();
    },
    hideModal() {
      this.$refs["popupTestport"].hide();
    },
    handleShowModal() {}
  },
};
</script>
