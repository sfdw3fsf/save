<template>
    <b-modal
        ref="popupPhieuHuy"
        size="xl"
        body-class="none-padding"
        no-close-on-backdrop
        hide-footer
        hide-header-close
    >
    <PhieuHuy :isPopup="true"/>
    </b-modal>
</template>
<script>
import PhieuHuy from '../PhieuHuy'
export default {
    name:'PhieuHuyModal',
    components:{
        PhieuHuy
    },
    methods:{
        showModal() {
            this.$refs['popupPhieuHuy'].show()
        },
        hideModal() {
            this.$refs['popupPhieuHuy'].hide()
        },
    }
}
</script>