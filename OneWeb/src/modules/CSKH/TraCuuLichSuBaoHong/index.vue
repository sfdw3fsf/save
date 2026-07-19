<template>
  <b-modal
    id="modal-tracuu-baohong"
    size="xl"
    hide-footer
    body-class="p-0"
    @hide="closeForm"
    scrollable
  >
    <template #modal-title>
      <div class="title">
        <span class="icon one-notepad"></span> Tra cứu chi tiết phiếu báo hỏng
      </div>
    </template>
    <LishSuBaoHong :p_ma_tb="soMay"/>
  </b-modal>
</template>
<script>
import LishSuBaoHong from "@/modules/incident/subscriber/SearchIncidentUpdateAppointment";
export default {
  props: ['soMay'],
  components: {
    LishSuBaoHong,
  },
  methods: {
    closeForm() {
      this.$bvModal.hide("modal-tracuu-baohong");
    },
  },
};
</script>
