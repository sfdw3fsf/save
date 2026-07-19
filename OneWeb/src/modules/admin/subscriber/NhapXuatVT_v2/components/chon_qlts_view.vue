<template>
  <!-- popupLuaChonQLTS -->
  <div>
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">Danh sách kiểu nhập serial</div>
        <div class="close -ap icon-close" @click="$bvModal.hide('popupLuaChonQLTS')" data-dismiss="modal"></div>
      </div>
      <div class="popup-body">
        <div class="info-row">
          <div class="value">
            <div class="list-checks mart7">
              <div class="item">
                <div class="check-action">
                  <input type="radio" name="isQLTS" :checked="!isQLTS" @change="isQLTS = false" />
                  <span class="name">Bình thường</span>
                </div>
              </div>
              <div class="item">
                <div class="check-action">
                  <input type="radio" name="isQLTS" :checked="isQLTS" @change="isQLTS = true" />
                  <span class="name">QLTS</span>
                </div>
              </div>
            </div>
          </div>
          <div class="value w30 nowrap">
            <button class="btn btn-second" @click="onClickChonQLTS"><span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Xác nhận</button>
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
