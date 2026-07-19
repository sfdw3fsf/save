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
        <DongBo  v-if="visible" :Tag="'2+4+4'" :isPopup="true"/>
      </ejs-dialog>
    </div>
</template>

<script>
import DongBo from "@/modules/contract/complete_profile/CompleteMobileInitProfile/index.js"
export default {
  components: {
    DongBo
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
    showModal (visible, hdkh_id, khachhang_id) {
      this.visible = visible
      // if (visible) {
      //   this.$refs.dlgDongBo.Popup('popupComponents')
      // } else {
      //   this.$refs.dlgDongBo.ClosePopup('popupComponents')
      // }
    }
  }
}
</script>
