<template>
  <div>
    <div class="box-form">
      <div class="legend-title">Vui lòng nhập mật khẩu quyền để sử dụng chức năng này</div>
      <div class="row">
        <div class="col-sm-12 col-12">
          <div class="info-row">
            <div class="key w100">Mật khẩu</div>
            <div class="value" style="display: flex">
              <input type="password" class="form-control" style="flex-grow: 1" v-model="password.value" v-on:keyup.enter="onPasswordSubmitted" />
              <button class="btn btn-primary" style="width: 150px; margin-left: 15px;" @click="onPasswordSubmitted">
                Xác nhận
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import apiProvider from '../GrantUserPermissionAPI'
export default {
  emits: ['onPasswordSubmittedSuccessListener'],
  data() {
    return {
      password: {
        value: ''
      }
    }
  },
  methods: {
    async onPasswordSubmitted() {
      if (this.password.value.length == 0) {
        this.$toast.warning('Vui lòng nhập mật khẩu!')
        return
      }
      try {
        this.loading(true)
        var data = await apiProvider.kiemtra_quyen_chucnang(this.axios, { password: this.password.value })
        this.loading(false)
        if (data.data.data && data.data.data == 1) {
          this.password.hasPermission = true
          this.$emit('onPasswordSubmittedSuccessListener')
        } else {
          this.$toast.error('Mật khẩu không đúng. Vui lòng thử lại!')
        }
      } catch (e) {
        this.$toast.error(`Đã xảy ra lỗi: ${e}`)
      }
    }
  }
}
</script>
<style lang="" scoped></style>
