<template>
    <b-modal
      size="xl"
      dialog-class="dialog-content"
      body-class="body-content"
      content-class="popup-box"
      v-model="visible"
      hide-footer
      hide-header
    >
<div style="margin: -1rem">
  <ThongTinLienHe v-if="visible" :hdkh_id="hdkh_id" :khachhang_id="khachhang_id" @form-close="show(false)"/>
</div>
</b-modal>
</template>
<script>
import ThongTinLienHe from "../../../setup/ChangeContractOwner/popup/ThongTinLienHe.vue"
export default {
  components: {
    ThongTinLienHe
  },
  data: () => ({
    visible: false,
    hdkh_id: 0,
    khachhang_id: 0
  }),
  methods: {
    show (visible, data) {
      if (data) {
        this.hdkh_id = data.hdkh_id
        this.khachhang_id = data.khachhang_id
      }
      this.visible = visible
    }
  }
}
</script>
