<template>

    <ejs-dialog :enableResize='true'  :allowDragging='true' :visible="false" ref="pop_ivan" :header='"Chi tiết tiền khoản mục"'
                showCloseIcon=true target=".main-wrapper" width='60%' height="800" body-class="modal-body p-0">
<div style="margin: -1rem">
  <TienKM v-if="visible" :dulieu="dulieu" @form-close="onClose"/>
</div>
</ejs-dialog> 

</template>
<script>
import TienKM from "../part/popupTienKM.vue"
export default {
  components: {
    TienKM
  },
  data: () => ({
    visible: false,
    dulieu: null
  }),
  methods: {
    show (visible, data) {
      this.dulieu = data
      this.visible = visible
      console.log(this.visible)
      if (this.visible == true){
        this.$refs.pop_ivan.show()        
      }
    },
    onClose (data) {
      this.$emit('onAccept', data)
      this.show(false)
    }
  }
}
</script>
