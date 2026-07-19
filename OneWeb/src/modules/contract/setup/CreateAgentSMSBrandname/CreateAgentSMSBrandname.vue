<template src="./CreateAgentSMSBrandname.html"></template>
<style src="./CreateAgentSMSBrandname.scss" scoped></style>
<script>
import breadcrumb from "@/components/breadcrumb";

import popupCreateAgentSMSBrandname from "./popupCreateAgentSMSBrandname.vue";

const defaultModule = () => import('@/modules/search/subscriber/SearchIncomeTicket/Default.vue')

export default {
  components: {
    breadcrumb,
    popupCreateAgentSMSBrandname,
    defaultModule
  },
  name: "CreateAgentSMSBrandname",
  data() {
    return {
      vhdtb_id: 9110701,
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      userComponentModule: defaultModule,
      popupComponentEvts: {},
      popupComponentName: '',
      animationSettings: { effect: 'Zoom' },
      position: { X: 'top', Y: 'top' },
      modalTitle: '',
    };
  },
  methods: {
    DblClickTree: async function (i, item) {
      this.popupComponentName = 'DeclareMegawan'
      this.popupComponentAttr = {
          "isPopup": true,
          "Tag": "9+4+8",
          "tag": "9+4+8",
          "matb_link": "MW001124129",
          "_vquytrinh_id": 10540
      }
      this.userComponentModule = () => import('@/modules/contract/setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue')
      // this.modalTitle = 'HTHS Khôi phục Gphone'
      
      this.$nextTick(() => {
        this.$refs.generalDialog.show(true)
      })                
    },
    showModal() {
      this.$refs.popupCreateAgentSMSBrandname.showModal();
    },
  },
};
</script>
