<template>
  <!-- popupSaveNoiDung -->
  <div>
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">Nội dung chứng từ</div>
        <div class="close -ap icon-close" @click="$bvModal.hide('popupSaveNoiDung')" data-dismiss="modal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li @click="onClickGhiLaiNoiDung()">
            <a> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Ghi Lại </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="info-row">
          <div class="value">
            <textarea class="form-control" v-model="txtNoiDung.value" style="height: 200px;resize: vertical;"></textarea>
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
