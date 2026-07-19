<template>
  <b-modal id="frmThayDoi_popup" title="Sửa kênh thu" modal-class="suakenhthu" header-class="popup-header"
      content-class="tracuu-content" body-class="none-padding tracuu-body" size="xl" hide-footer>
      <ThayDoiKenhThu :maGD="maGD" ref="frmThayDoi_KenhThu" />
  </b-modal>
</template>
<script>
import ThayDoiKenhThu from '@/modules/QLTN/ThayDoiKenhThu'

export default {
  components: {
    ThayDoiKenhThu
  },
  props: ['maGD'],
  methods: {
    show() {
      this.$bvModal.show("frmThayDoi_popup")
    }
  }
}
</script>
<style>
  .suakenhthu .page-content {
    position: unset;
  }
</style>
