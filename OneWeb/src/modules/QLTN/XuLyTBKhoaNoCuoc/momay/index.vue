<template>
  <b-modal
    id="modal-momay-somay"
    size="lg"
    hide-footer
    body-class="p-0"
    @hide="closeForm"
    scrollable
  >
    <template #modal-title>
      <div class="title">
        <span class="icon one-notepad"></span> Mở máy theo từng số máy/ Account
      </div>
    </template>
    <MoMay :maThueBao="soMay"/>
  </b-modal>
</template>
<script>
import MoMay from "../../MoMayTheoTungSoMayAcc";
export default {
  props: ['soMay'],
  components: {
    MoMay,
  },
  methods: {
    closeForm() {
      this.$bvModal.hide("modal-momay-somay");
    },
  },
};
</script>
