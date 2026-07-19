<template>
    <b-modal ref="dlgGiaoPhieu" size="xl" body-class="p-0" scrollable hide-footer title="Giao phiếu điều hành thi công">
        <HandoverTicketContent :isPopup="true" :data="data" ref="frmGiaoPhieu" />
    </b-modal>
</template>
<script>
//import HandoverTicketContent from "@/modules/contract/setup/HandoverTicket/HandoverTicket_content.vue"
import HandoverTicketContent from '@/modules/QLTN/XLTBGiaoPhieu'
export default {
    name: 'PopupGiaoPhieu',
    components: {
        HandoverTicketContent
    },
    data() {
        return {
            data:{huonggiao_id:1,
                dichvuvt_id:1}
        }
    },
    props: ['huongGiaoId', 'dichVuId'],
    methods: {
        showModal() {
            this.$refs['dlgGiaoPhieu'].show();
            this.$nextTick(function () {
                //this.$refs.frmGiaoPhieu.listbox.huonggiao.value.huonggiao_id = this.huongGiaoId;
                this.data.huonggiao_id=this.huongGiaoId
                this.data.dichvuvt_id=this.dichVuId
                //console.log('huonggiao',this.huongGiaoId)
                // this.$refs.frmGiaoPhieu.showPopup({
                //     trangthaiphieu: 0,
                //     loaihd: '',
                //     madv: this.dichVuId,
                //     quytrinh: '',
                //     huonggiao: this.huongGiaoId,
                //     magd: ''
                    
                // });
            });
        },
        hideModal() {
            this.$refs['dlgGiaoPhieu'].hide();
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
