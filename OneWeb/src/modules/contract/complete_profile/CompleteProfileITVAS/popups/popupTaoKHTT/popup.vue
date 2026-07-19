<template>
  <b-modal ref="popupTaoKHTT" size="xl" body-class="p-0" centered scrollable hide-footer>
    <div slot="modal-title">
      <span class="icon one-notepad"></span>
      Tạo khách hàng TTKD
    </div>
    <popupTaoKHTT :isPopup="true" :inputData="inputDataPopupTaoKHTT" />
  </b-modal>
</template>
<script>
import popupTaoKHTT from "@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupTaoKHTT";

export default {
  components: { popupTaoKHTT },
  data() {
    return {
      inputDataPopupTaoKHTT: { vhdtb_id: 0 },
    };
  },
  methods: {
    showPopupTaoKHTT(vhdtb_id) {
      this.inputDataPopupTaoKHTT = { vhdtb_id: vhdtb_id };
      this.$refs.popupTaoKHTT.show();
    },
  },
};
</script>
