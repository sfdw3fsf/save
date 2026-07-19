<template src="./index.template.html"></template>
<script>
    import FrmThanhToanHopDong from '@/modules/contract/setup/Payment'
    export default {
        components: {FrmThanhToanHopDong},
        data() {
            return {
                resolvePromise: null,
                txtMaGD: null
            }
        },
        methods: {
            show(txtMaGD) {
                this.txtMaGD = txtMaGD
                this.$refs['ref-modal'].show()
                return new Promise(resolve => this.resolvePromise = resolve)
            },
            hide() {
                this.resolvePromise({ })
                this.$refs['ref-modal'].hide()
            },
            onModalHidden() {
                this.resolvePromise = null
            },
            async onModalShown() {
                
                if (!this.txtMaGD) {
                    return
                }

                let frm = this.$refs.frmThanhToanHopDong
                frm.txtMaGD = this.txtMaGD
                await frm.searchTheoMaGD(this.txtMaGD)
                frm.HienThiDoiTuong()
            }
        }
    }
</script>
<style>
    @media (min-width: 1200px) {
        #id-frm-thaydoi-datcoc-thanhtoan .modal-xl {
            max-width: 99% !important;
        }
    }
    
    .frm-thaydoi-datcoc-thanhtoan .main-wrapper {
        position: static !important;
    }

    .frm-thaydoi-datcoc-thanhtoan .page-content {
        overflow: visible !important;
        height: fit-content;
        background-color: white;
        padding: 0;
        position: static !important;
    }

    .frm-thaydoi-datcoc-thanhtoan .breadcrumb-top {
        display: none;
    }

    .frm-thaydoi-datcoc-thanhtoan .select2-container .select2-selection--single .select2-selection__rendered {
        white-space: initial;
        height: 28px;
    }
</style>