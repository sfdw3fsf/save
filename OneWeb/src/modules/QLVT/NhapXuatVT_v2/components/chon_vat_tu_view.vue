<template>
  <!-- popupLuaChonVatTu -->
  <div>
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">Bạn hãy lựa chọn kiểu chọn vật tư</div>
        <div class="close -ap icon-close" @click="$bvModal.hide('popupLuaChonVatTu')" data-dismiss="modal"></div>
      </div>
      <div class="popup-body">
        <div class="info-row">
          <div class="value">
            <div class="list-checks mart7">
              <div class="item">
                <div class="check-action">
                  <input type="radio" name="isVatTu" :checked="!isVatTu" @change="isVatTu =false" />
                  <span class="name">Vật tư - lô hàng</span>
                </div>
              </div>
              <div class="item">
                <div class="check-action">
                  <input type="radio" name="isVatTu" :checked="isVatTu" @change="isVatTu = true" />
                  <span class="name">Vật tư</span>
                </div>
              </div>
            </div>
          </div>
          <div class="value w30 nowrap">
            <button class="btn btn-second" @click="onClickChonVatTu"><span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Xác nhận</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  props: {},
  data() {
    return {}
  },
  computed: {},
  async mounted() {},
  watch: {},
  methods: {},
  created() {
    window.addEventListener('keydown', this.keyDownHandler)
  },
  destroyed() {
    window.removeEventListener('keydown', this.keyDownHandler)
  }
}
</script>
<style scoped></style>
