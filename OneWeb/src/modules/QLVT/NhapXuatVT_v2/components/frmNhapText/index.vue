<template>
  <div v-if="showview">
    <div class="popup-body">
      <div class="">Bạn hãy lựa chọn kiểu xóa chứng từ</div>
      <div>
        <div class="red">
          <div v-for="(item, index) in rdioCKb.list" :key="index" class="item my-2">
            <div class="check-action">
              <input type="radio" v-model="rdioCKb.value" name="delete-type" class="check" checked :value="item.value" />
              <span class="name">{{ item.text }}</span> 
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="popup-footer d-flex justify-content-end py-3">
      <button class="btn btn-outline-primary mr-3" @click="$emit('onBtnHuyClicked')">Hủy</button>
      <button class="btn btn-outline-danger mr-4 btn-main" @click="onBtnXacNhanClicked">Xác nhận</button>
      <!-- <button class="btn btn-outline-primary" v-show="Visible.btnHuyThat"
            @click="btnHuyThat_Click">{{Text.btnHuyThat}}</button> -->
    </div>
  </div>
</template>
<script>
import Vue from 'vue'
export default {
  emits: ['onBtnHuyClicked', 'onBtnXacNhanClicked'],
  data() {
    return {
      showview: false,
      rdioCKb: {
        list: [
          { text: 'Xóa toàn bộ', value: 1 },
          { text: 'Chỉ xóa tập đoàn', value: 2 },
          { text: 'Chỉ xóa ở IMS', value: 3 }
        ],
        value: null
      }
    }
  },
  methods: {
    async onBtnXacNhanClicked() {
      this.$emit('onBtnXacNhanClicked', { vkieu_luachon: this.rdioCKb.value })
    },
    async btnHuyBo_Click() {}
  },
  async mounted() {
    this.loading(true)
    setTimeout( () => {
      this.showview = true
      this.loading(false)
    }, 500);
  }
}
</script>
<style></style>
