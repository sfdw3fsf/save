<template>
    <b-modal
        id="dlg-giao-phieu"
        ref="dlgGiaoPhieu"
        size="xl"
        body-class="p-0"
        scrollable
        hide-footer>
        <HandoverTicketContent :isPopup="'true'" ref="frmGiaoPhieu"/>
    </b-modal>
</template>
<script>
import HandoverTicketContent from "@/modules/contract/setup/HandoverTicket/HandoverTicket_content.vue"
export default {
    name:'PopupGiaoPhieu',
    components:{
        HandoverTicketContent
    },
    props: {
      huongGiaoId: {
        type: Number,
        default: 0
      },
      trangthaiPhieuId: {
        type: Number,
        default: 0
      },
      maGD: {
        type: String,
        required: false,
        default: '0'
      },
      hdtbId: {
        type: Number,
        default: 0
      },
      luongId: {
        type: Number,
        default: 0
      }
    },
    methods:{
        showModal() {
          this.$refs.frmGiaoPhieu.listbox.huonggiao.value.huonggiao_id = this.huongGiaoId
          this.$refs.frmGiaoPhieu.listbox.trangthaiphieu.value.id = this.trangthaiPhieuId
          this.$refs.frmGiaoPhieu.listbox.loaihd.value.LOAIHD_ID =  this.trangthaiPhieuId
          this.$refs.frmGiaoPhieu.txtMaGD = this.maGD

          this.$refs['dlgGiaoPhieu'].show();
        },
        hideModal() {
          this.$refs['dlgGiaoPhieu'].hide();
        }
    }
}
</script>
