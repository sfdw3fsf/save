<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='$data.animationSettings'
    ref="dlgPrintPreview"
    :header='"Xem trước khi in"'
    showCloseIcon=true
    width='90%'
    :target='$data.target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title"><span class="icon one-notepad"></span>BC_CT_THANHTOANNO_KH - Preview report</div>
            <div class="close -ap icon-close" data-dismiss="modal">
            </div>
        </div>
        <div class="popup-body pad0">
            <h1>Chức năng In đang được xây dựng</h1>
        </div>
    </div>
  </ejs-dialog>
</template>

<script>
  import Vue from 'vue'
  import TraCuuNoTongHopAPI from '../../api/TraCuuNoTongHopAPI'
  import { DialogPlugin } from '@syncfusion/ej2-vue-popups'

  Vue.use(DialogPlugin)

  export default Vue.extend({
    components : {
      //appRemotePagination : RemotePagination,
    },
    props: {
      ma_tt: {
        type: String,
        required: false
      }
    },
    computed : {
    },
    data () {
      return {
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' },
      }
    },
    methods : {
      openDialog () {
        this.$refs.dlgPrintPreview.show()
      },
      closeDialog() {
        this.$refs.dlgPrintPreview.hide()
      },
      onDialogClose () {
      },
      onDialogOpen () {
      },
    },
  })
</script>

