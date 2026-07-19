<template>
    <b-modal
        ref="popupGiaophieu"
        size="xl"
        body-class="modal-body p-0"
        scrollable
        hide-footer hide-header hide-header-Close
        >
        <PopupThongTinUser :isPopup="true" :tinh_id = "tinh_id" :thongTinThueBao = "thongTinThueBao" @form-close="formClose" />
    </b-modal>
</template>
<script>
import PopupThongTinUser from '@/modules/common/popup/popupGiaoPhieu/index.vue'
export default {
    name:'GiaophieuModal',
    components:{
        PopupThongTinUser
    },
    props: {
      thongTinThueBao: {},
      isPopup: {
          type: Boolean,
          default: true
        },
        tinh_id: {
          type: Number,
          default: 0
        },
    },
    methods:{
        showModal() {
            this.$refs['popupGiaophieu'].show()
        },
        hideModal() {
            this.$refs['popupGiaophieu'].hide()
        },
        formClose(value){
            this.$emit('form-close',value)
            this.hideModal()
        }
    }
}
</script>
