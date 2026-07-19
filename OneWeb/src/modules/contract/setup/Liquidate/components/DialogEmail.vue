<template>
  <div>
    <ejs-dialog
    :enableResize='true'
    :visible="visible"
    ref="thongtinemail"
    :header='"Thông tin Email"'
    showCloseIcon="true" target=".main-wrapper" width='80%' height="800"
    :position="{ X: 'center', Y: 'top' }"
    @close="visible = false">
        <ThongTinEmail v-if="visible" :hdkh_id="hdkh_id" :khachhang_id="khachhang_id" />
    </ejs-dialog>
  </div>
</template>

<script>
import ThongTinEmail from "@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue"
export default {
  components: {
    ThongTinEmail
  },
  data () {
    return {
      visible: false,
      hdkh_id: "",
      khachhang_id: ""
    }
  },
  props: {},
  methods: {
    showModal (hdkh_id, khachhang_id) {
      this.hdkh_id = hdkh_id
      this.khachhang_id = khachhang_id
      this.visible = true
    }
  }
}
</script>
