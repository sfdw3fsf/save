<template src="./index.template.html"></template>
<script>
    import UC_PTM_V3 from './UC_PTM_V3'
    export default {
        components: {
            UC_PTM_V3
        },
        data() {
            return {
                resolvePromise: null,
                vhscc_id: 0,
                vphieu_id: 0,
                KtThucHien: false
            }
        },
        methods: {
            show() {
                this.$refs['ref-modal'].show()
                return new Promise(resolve => this.resolvePromise = resolve)
            },
            hide() {
                this.resolvePromise({ KtThucHien: this.KtThucHien })
                this.$refs['ref-modal'].hide()
            },
            onModalHidden() {
                this.resolvePromise = null
                this.vhscc_id = 0
                this.vphieu_id = 0
                this.KtThucHien = false
            },
            onModalShown() {
                this.frmThemPhanTuMang_Load()
            },
            frmThemPhanTuMang_Load() {
                this.$refs.uc_ptm_v3.UC_PTM_V3_2(this.vphieu_id, this.vhscc_id)
            },
            onCompleteThemPtm(data) {
                this.KtThucHien = !!data.KtThucHien
                this.hide()
            }
        }
    }
</script>
<style>
    @media (min-width: 1200px){
        .frm-them-phan-tu-mang.modal-xl {
            max-width: 95% !important;
        }
    }
</style>