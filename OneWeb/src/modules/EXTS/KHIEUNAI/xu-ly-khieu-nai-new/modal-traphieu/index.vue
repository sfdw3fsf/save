<template src="./index.template.html"></template>
<style>
    .modal-gqkn-traphieu-khieunai .page-content {
        overflow: visible !important;
        height: fit-content;
        background-color: white;
    }
</style>
<script>
import TraPhieuKhieuNai from '../../TraPhieuKhieuNai'
export default {
    components: { TraPhieuKhieuNai },
    data() {
        return {
            isChanged: false,
            maTb: null
        }
    },
    methods: {
        show() {
            return this.$refs['ref-modal'].show()
        },
        hide() {
            this.$refs['ref-modal'].hide()
        },
        onModalHidden() {

            this.maTb = null

            if (this.isChanged) {
                this.isChanged = false
                this.$emit('change')
            }
        },
        onModalShown() {

        },
        onSuccessTraPhieu() {
            this.isChanged = true
        },
        setMaTb(maTb) {
            this.maTb = maTb
        }
     }
}
</script>

