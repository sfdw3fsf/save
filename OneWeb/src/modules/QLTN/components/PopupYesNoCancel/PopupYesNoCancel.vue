<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='animationSettings'
    ref="dlgYesNoCancel"
    :header='"Xác nhận"'
    showCloseIcon=true
    width='500'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <div class="popup-body">
        <div style="padding: 30px">{{this.message}}</div>
      </div>
      <hr>
      <div class="col-sm-12 col-12" style="text-align: center; padding: 5px 10px 10px">
        <button class="btn btn-success" @click="doYes">Đồng ý</button>
        <button class="btn btn-primary" @click="doNo">Không đồng ý</button>
        <button class="btn btn-primary" @click="doCancel">Hủy bỏ</button>
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
    props: {
      message: {
        type: String,
        required: false
      }
    },
    data () {
      return {
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' }
      }
    },
    methods : {
      openDialog() {
        this.$refs.dlgYesNoCancel.show()
      },
      closeDialog() {
        window.removeEventListener("keydown", this.keypressHandler);
        this.$refs.dlgYesNoCancel.hide()
      },
      onDialogClose() {
        window.removeEventListener("keydown", this.keypressHandler);
      },
      onDialogOpen() {
        window.addEventListener('keydown', this.keypressHandler);
      },
      clearForm() {
      },
      clearResult() {
      },
      doYes()
      {
        this.$emit("yesHandler", { })
        this.closeDialog()
      },
      doNo()
      {
        this.$emit("noHandler", { })
        this.closeDialog()
      },
      doCancel()
      {
        this.closeDialog()
      },
      keypressHandler(e){
        let key = e.key.toUpperCase()
        if(key === 'Y')
        {
          this.doYes()
        } else if(key === 'N')
        {
          this.doNo()
        } else if(key === 'C')
        {
          this.doCancel()
        }
      }
    }
  })
</script>
