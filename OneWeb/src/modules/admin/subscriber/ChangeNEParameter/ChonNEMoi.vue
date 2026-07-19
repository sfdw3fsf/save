<template>
  <b-modal id="popupTCTTTB"  size="lg" hide-footer hide-header no-close-on-backdrop>
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Chọn số ne
        </div>
        <div
          @click="closeModal"
          class="close -ap icon-close"
          data-dismiss="modal"
        ></div>
      </div>
      <div class="modal-content popup-box">
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a @click="handleAccept">
                <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>
                Chấp nhận
              </a>
            </li>
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form">
            <ThongTinSoNe :checkAccept="checkAccept" />
            <DanhSachThongSoNe />
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import ThongTinSoNe from './part/ThongTinSoNe'
import DanhSachThongSoNe from './part/DanhSachThongSoNe'
export default {
  components: {
    DanhSachThongSoNe,
    ThongTinSoNe
  },
  data () {
    return {
      checkAccept: true
    }
  },
  methods: {
    closeModal () {
      this.$bvModal.hide('popupTCTTTB')
    },
    handleAccept () {
      this.checkAccept = !this.checkAccept
    }
  }
}
</script>
<style scoped>
.icon-close {
  cursor: pointer;
}
>>> .modal-body {
  padding: 0 !important;
}
>>> .modal-content {
  border: none !important;
}
</style>
