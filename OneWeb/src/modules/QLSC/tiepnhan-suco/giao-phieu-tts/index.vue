<template src="./index.template.html"></template>
<style>
    .tiepnhan-suco-giaophieu .page-content {
        overflow: visible !important;
        height: fit-content;
        background-color: white;
    }

    @media (min-width: 1200px) {
        #frm-tiep-nhan-su-co--giao-phieu-tts .modal-xl {
            max-width: 80% !important;
        }
    }
</style>
<script>
import GiaoPhieu from '../../frmGiaoPhieu_NET'
export default {
    components: { GiaoPhieu },
    props: {
        parent: Object
    },
    data() {
        return {
            maSc: "",
            chkNgayGiao: true
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
            this.chkNgayGiao = true
        },
        onModalShown() {
            this.$refs.frmGiaoPhieu.txtMaSC.Text = this.maSc
            this.$refs.frmGiaoPhieu.cbxNgayGiao.Checked = this.chkNgayGiao
        },
        onSuccessGiaoPhieu() {
            this.parent.napDsSuCoDpn()
            this.hide()
        },
        setMaSuCo(maSc) {
            this.maSc = maSc
        },
        setCheckboxNgayGiao(chkNgayGiao) {
            this.chkNgayGiao = chkNgayGiao
        }
     }
}
</script>

