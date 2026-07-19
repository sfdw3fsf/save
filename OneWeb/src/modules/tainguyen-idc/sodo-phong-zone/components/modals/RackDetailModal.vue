<template>
  <b-modal
    id="rackDetailModal"
    ref="rackDetailModal"
    size="xl"
    hide-footer
    @hidden="$emit('close')"
    modal-class="rack-detail-modal"
  >
    <template #modal-header>
      <h5 class="modal-title">
        <strong>THÔNG TIN RACK - {{ rackDetail ? rackDetail.name : '' }}</strong>
      </h5>
      <button type="button" class="close" @click="closeModal">
        <span aria-hidden="true">&times;</span>
      </button>
    </template>
    <RackDetailContent :modalItemID="rackDetail && rackDetail.id ? (typeof rackDetail.id === 'string' ? rackDetail.id.replace('node_', '') : rackDetail.id) : ''" />
  </b-modal>
</template>

<script>
import RackDetailContent from '../tab-content/RackDetailContent.vue'

export default {
  name: 'RackDetailModal',
  components: {
    RackDetailContent
  },
  props: {
    rackDetail: {
      type: Object,
      default: null
    }
  },
  methods: {
    show() {
      this.$refs.rackDetailModal.show()
    },
    closeModal() {
      this.$refs.rackDetailModal.hide()
    }
  }
}
</script>
<style>
.rack-detail-modal .modal-dialog {
  max-width: 95vw !important;
  width: 95vw !important;
  margin: 1rem auto;
}

.rack-detail-modal .modal-content {
  height: 95vh;
}

.rack-detail-modal .modal-body {
  padding: 1rem;
  height: calc(95vh - 60px);
  overflow: scroll;
}
</style>
