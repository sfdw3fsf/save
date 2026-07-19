<template src="./index.template.html"></template>
<style>
    .tiepnhan-suco-giaophieu .page-content {
        overflow: visible !important;
        height: fit-content;
        background-color: white;
    }
</style>
<script>
import GiaoPhieu from '../../giao-phieu'
export default {
    components: { GiaoPhieu },
    props: {
        parent: Object
    },
    data() {
        return {
            maSc: "",
            chkNgaySuCo: true
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
            this.maSc = ""
            this.chkNgaySuCo = true
        },
        onModalShown() {
            this.$refs.frmGiaoPhieu.maSc = this.maSc
            this.$refs.frmGiaoPhieu.chkNgaySuCo = this.chkNgaySuCo
        },
        onSuccessGiaoPhieu() {
            this.parent.napDsSuCoDpn()
            this.hide()
        },
        setMaSuCo(maSc) {
            this.maSc = maSc
        },
        setCheckboxNgaySuCo(chkNgaySuCo) {
            this.chkNgaySuCo = chkNgaySuCo
        }
     }
}
</script>

