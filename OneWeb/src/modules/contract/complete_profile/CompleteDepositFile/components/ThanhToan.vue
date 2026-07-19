<template>
    <b-modal
        ref="dialogPayment"
        size="xl"
        body-class="p-0"
        hide-header
        hide-header-close
        hide-footer
    >
        <div class="modal-content popup-box">
            
            <div class="popup-body">
                <Payment />
            </div>
        </div>
    </b-modal>
</template>

<script>
import Payment from '@/modules/contract/setup/Payment/Payment.vue';
export default {
    components: {
        Payment,
    },
    methods: {
        showModal() {
            this.$refs.dialogPayment.show();
        },
        hideModal() {
            this.$refs.dialogPayment.hide();
        }
    }
}
</script>

<style>
</style>