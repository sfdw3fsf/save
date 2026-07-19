<template>
    <b-modal
        ref="dlgTienTrinhDT"
        size="xl"
        body-class="p-0"
        scrollable
        title="Tiến trình đầu tư hợp đồng"
        hide-footer>
        <frmTienTrinhDT 
            ref="frmTienTrinhDT">
        </frmTienTrinhDT>
    </b-modal>
</template>
<script>
import TienTrinhDauTu from '../../TienTrinhDauTu'
export default {
    name:'PopupTienTrinhDT',
    components:{
        frmTienTrinhDT: TienTrinhDauTu
    },
    props: {
        vma_hd: {
            type: String,
            default: ''
        },
        vten_hd: {
            type: String,
            default: ''
        },
        vhddtu_id: {
            type: Number,
            default: 0
        }
    },
    methods:{
        showModal() {
            this.$refs['dlgTienTrinhDT'].show();
            this.$nextTick(function () {
                this.$refs.frmTienTrinhDT.vma_hd = this.vma_hd;
                this.$refs.frmTienTrinhDT.vten_hd = this.vten_hd;
                this.$refs.frmTienTrinhDT.vhddt_id = this.vhddtu_id;
            });
        },
        hideModal() {
            this.$refs['dlgTienTrinhDT'].hide();
        }
    }
}
</script>
<style>
    .page-content {
        top: 40px !important;
        bottom: 0 !important;
    }
</style>
