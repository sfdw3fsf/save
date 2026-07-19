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
          <span class="icon one-notepad"></span> Giao phiếu
        </div>
        <div
          class="close -ap icon-close"
          @click.prevent="show(false)"
        ></div>
      </template>
        <div style="height:900px"><HandoverTicket ref="handoverTicket" :isDialog="true" :dichVu="dichVu"/></div>
    </b-modal>
</template>
<script>
import HandoverTicket from '../../HandoverTicket/HandoverTicket.vue'
export default {
  props: [ 'dichVu', 'visible' ],
  components: {HandoverTicket},
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
