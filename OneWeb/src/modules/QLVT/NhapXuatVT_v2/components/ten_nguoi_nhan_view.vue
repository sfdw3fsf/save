<template>
  <!-- popupTenNguoiNhan -->
  <div>
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">Tên người nhận</div>
        <div class="close -ap icon-close" @click="$bvModal.hide('popupTenNguoiNhan')" data-dismiss="modal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a @click="onClickXacNhanInPhieu()"> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Xác nhận </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="info-row">
          <div class="key w90">
            Nhập tên người nhận
          </div>
          <div class="value">
            <textarea class="form-control" v-model="tenNguoiNhan" value="tenNguoiNhan" style="height: 100px;resize: vertical;"></textarea>
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
