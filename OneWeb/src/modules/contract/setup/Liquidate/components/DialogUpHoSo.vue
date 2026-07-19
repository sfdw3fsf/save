<template>
    <b-modal
      size="xl"
      dialog-class="dialog-content"
      body-class="body-content"
      header-class="popup-header"
      content-class="popup-box"
      v-model="isShowModal"
      hide-footer
      @close="onClose"
    >
      <template #modal-header>
        <div class="title">
          <span class="icon one-notepad"></span> Up Hồ Sơ
        </div>
        <div
          class="close -ap icon-close"
          @click.prevent="show(false)"
        ></div>
      </template>
        <div style="height:900px"><UploadEProfile ref="upHoSo"/></div>
    </b-modal>
</template>
<script>
import UploadEProfile from '../../../../../modules/contract/profile/UploadEProfile/UploadEProfile.vue'
export default {
  props: [ ],
  components: {UploadEProfile},
  data: () => ({
    isShowModal: false
  }),
  methods: {
    show (visible) {
      this.isShowModal = visible
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>
