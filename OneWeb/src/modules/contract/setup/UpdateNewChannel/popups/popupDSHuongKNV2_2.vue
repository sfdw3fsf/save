<template>

<b-modal
    ref="popupDSHuongKN"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0">
    <popupDSHuongKN :isPopup="true" @form-close="chapNhan" :dulieu="duLieuPopupHuongKetNoi_Popup"></popupDSHuongKN>
</b-modal>

</template>

<style>
    .grid-ext {
        min-width: unset !important;
    }
</style>

<script>
import popupDSHuongKN from '@/modules/contract/setup/InstallNewSubs/Popups/popupDSHuongKN.vue'
export default {
    name:'popupDSHuongKNModal',
    
    components:{
        popupDSHuongKN
    },
    data() {
        return {
            duLieuPopupHuongKetNoi_Popup: {}
        }
    },
    props: 
        [ 'duLieuPopupHuongKetNoi' ]
    ,
    methods:{
        showModal() {
            this.duLieuPopupHuongKetNoi_Popup = this.duLieuPopupHuongKetNoi;
            this.$refs['popupDSHuongKN'].show()
        },
        hideModal() {
            this.$refs['popupDSHuongKN'].hide()
        },
        chapNhan(value){
            this.$emit('form-close', value)
            this.hideModal()
        }
    }
}

</script>

