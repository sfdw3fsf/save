<template>
  <div>
    <div class="popup-body">
      <div class="">Nhập nội dung chuyển VIP</div>
      <div>
        <textarea :disabled="!txtNhapText.enabled" v-model="txtNhapText.value" name="" class="form-control" style="height: 100px; resize: none;"></textarea>
      </div>
    </div>
    <div class="popup-footer d-flex justify-content-end py-3">
      <button class="btn btn-outline-primary mr-3" @click="$emit('tsbtnHuy_Clicked')">Hủy</button>
      <button class="btn btn-outline-danger mr-4 btn-main" @click="tsbtnXacNhan_Clicked">Xác nhận</button>
      <!-- <button class="btn btn-outline-primary" v-show="Visible.btnHuyThat"
            @click="btnHuyThat_Click">{{Text.btnHuyThat}}</button> -->
    </div>
  </div>
</template>
<script>
import Vue from 'vue'
export default {
  emits: ['tsbtnHuy_Clicked', 'tsbtnXacNhan_Clicked'],
  data() {
    return {
      txtNhapText: { visible: true, enabled: true, value: '' }
    }
  },
  methods: {
    async tsbtnXacNhan_Clicked() {
      this.$emit('tsbtnXacNhan_Clicked', { Output: this.txtNhapText.value })
    }
  },
  async mounted() {}
}
</script>
<style></style>
