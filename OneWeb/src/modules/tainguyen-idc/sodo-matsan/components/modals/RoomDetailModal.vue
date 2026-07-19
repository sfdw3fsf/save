<template>
  <b-modal id="roomDetailModal" ref="roomDetailModal" size="xl" hide-footer @hidden="$emit('close')"
    modal-class="room-detail-modal">
    <template #modal-header>
      <h5 class="modal-title">
        <strong>THÔNG TIN PHÒNG - {{ roomDetail ? roomDetail.name : '' }}</strong>
      </h5>
      <button type="button" class="close" @click="closeModal">
        <span aria-hidden="true">&times;</span>
      </button>
    </template>

    <RoomDetailContent :modalItemID="roomDetail.id" @edit="handleEdit" @view-devices="handleViewDevices"
      @delete="handleDelete" @view-rack-detail="handleViewRackDetail" />
  </b-modal>
</template>

<script>
import RoomDetailContent from '../RoomDetailContent.vue'

export default {
  name: 'RoomDetailModal',

  components: {
    RoomDetailContent
  },

  props: {
    roomDetail: {
      type: Object,
      default: null
    }
  },

  methods: {
    show() {
      this.$refs.roomDetailModal.show()
    },

    closeModal() {
      this.$refs.roomDetailModal.hide()
    },
    handleEdit() {
      this.$emit('edit', this.roomDetail)
    },

    handleViewDevices() {
      this.$emit('view-devices', this.roomDetail)
    },

    handleDelete(roomDetail) {
      this.$emit('delete', roomDetail)
    },

    handleViewRackDetail(rack) {
      this.$emit('view-rack-detail', rack)
    }
  }
}
</script>
<style>
.room-detail-modal .modal-dialog {
  max-width: 95vw !important;
  width: 95vw !important;
  margin: 1rem auto;
}

.room-detail-modal .modal-content {
  height: 95vh;
}

.room-detail-modal .modal-body {
  padding: 0;
  height: calc(95vh - 60px);
  overflow-y: scroll !important;
}
</style>
