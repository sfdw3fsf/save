<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :isModal="true"
    :animationSettings='animationSettings'
    ref="dlgDSPhimTat"
    :header='"Danh sách phím tắt"'
    showCloseIcon=true
    width='400'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <div class="popup-body">
        <ul>
          <li>Phím A: Gạch nợ tạm ứng</li>
          <li>Phím F5: Nhập mới </li>
          <li>Phím D: Gạch nợ đầu kỳ</li>
          <li>Phím P: Gạch nợ phát sinh</li>
          <li>Phím Z: Gạch nợ tất cả</li>
          <li>Phím C: Gạch nợ cố định</li>
          <li>Phím G: Gạch nợ di động</li>
          <li>Phím I: Gạch nợ Internet</li>
          <li>Phím U: Gạch nợ truy thu</li>
          <li>Phím INS: Chọn tất cả</li>
          <li>Phím Delete: Bỏ chọn tất cả</li>
        </ul>
      </div>
  </div>
  </ejs-dialog>
</template>
<script>
import Vue from "vue"
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'

Vue.use(DialogPlugin)

 export default Vue.extend({
    components : {
    },
    data () {
      return {
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' }
      }
    },
    methods : {
      openDialog() {
        this.$refs.dlgDSPhimTat.show()
      },
      closeDialog() {
        this.$refs.dlgDSPhimTat.hide()
      },
      onDialogClose() {
      },
      onDialogOpen() {
      },
      clearForm() {
      },
      clearResult() {
      },
    }
  })
</script>
