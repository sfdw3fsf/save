<template>
  <b-modal
    ref="khachHangGoiRef"
    body-class="p-0"
    hide-header
    hide-footer
    hide-backdrop
    static
    @close="onClose"
  >
    <frmPhanAnhBH
      ref="dialogfrmPhanAnhBH"
      :params="frmPhanAnhBHInput"
      @closefrmPhanAnhBH="onClose"
    >
    </frmPhanAnhBH>
  </b-modal>
</template>

<script>
import frmPhanAnhBH from "@/modules/incident/subscriber/SearchIncidentUpdateAppointment/components/frmPhanAnhBH.vue";
export default {
  name: "PopupKhachHangGoi",
  props: {
    selectedRow: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      frmPhanAnhBHInput: {}
    };
  },
  components: {
    frmPhanAnhBH
  },
  methods: {
    show() {
      const { ma_tb, baohong_id, thuebao_id, dienthoai_lh } = this.selectedRow;
      this.frmPhanAnhBHInput.vbaohong_id_t = baohong_id || "";
      this.frmPhanAnhBHInput.thuebao_id = thuebao_id || "";
      this.frmPhanAnhBHInput.kieu_nhac = 2;
      this.frmPhanAnhBHInput.ma_tb = ma_tb || "";
      this.frmPhanAnhBHInput.dienthoai_lh = dienthoai_lh;
      console.log("🚀 ~ show ~ frmPhanAnhBHInput", this.frmPhanAnhBHInput);
      console.log(this.$refs.dialogfrmPhanAnhBH);
      this.$refs.khachHangGoiRef.show();
      this.$refs.dialogfrmPhanAnhBH.openDialog();
    },
    onClose() {
      this.$refs.khachHangGoiRef.hide();
    }
  }
};
</script>

<style></style>
