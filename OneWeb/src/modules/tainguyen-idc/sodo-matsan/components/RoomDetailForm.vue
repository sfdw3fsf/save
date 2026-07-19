<template>
    <b-modal id="room-detail-modal" ref="roomDetailModal" title="Thông tin phòng" size="xl" hide-footer
        @hidden="onModalHidden" modal-class="room-detail-modal">
        <thong-tin-phong-zone :roomId="roomData.zoneId" :isFromModal="true" @close="closeModal" />
    </b-modal>
</template>

<script>
import ThongTinPhongZone from '../../thongtin-phong-zone/index.vue'

export default {
    name: 'RoomDetailForm',

    components: {
        ThongTinPhongZone
    },

    props: {
        roomData: {
            type: Object,
            default: () => ({})
        }
    },

    data() {
        return {

        }
    },

    created() {
    },

    computed: {},

    methods: {
        showModal() {
            this.$refs.roomDetailModal.show()
        },

        closeModal() {
            this.$refs.roomDetailModal.hide()
        },

        onModalHidden() {
            this.$emit('hidden')
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
  padding: 0 ;
  height: calc(95vh - 60px);
  overflow: hidden;
}
</style>