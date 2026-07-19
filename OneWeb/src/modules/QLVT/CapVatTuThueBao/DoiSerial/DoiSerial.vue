<template src="./DoiSerial.html"></template>
<script>
export default {
  data() {
    return { tenVatTu: [], value_tenVatTu: "", serialChange: "" };
  },
  props: { modalId: String, serialCu: String, vatTuIdCu: String},
  methods: {
    closeModal: function () {
      this.$bvModal.hide(this.modalId);
      this.serialChange = ""
    },
    showModal: async function () {
      try {
        this.loading(true);
        var rs = await this.$root.context.get(
          "/web-qlvt/api/cap-vat-tu-thue-bao/ds-vat-tu"
        );
        console.log("rs = ", rs);
        this.tenVatTu = rs.data;
        if (this.tenVatTu) {
          this.value_tenVatTu = this.vatTuIdCu;
        }
      } catch (error) {
        this.$toast.error("Đã xảy ra lỗi!");
      } finally {
        this.loading(false);
      }
    },
    updateSerial() {
      this.$emit("update-serial", {
        CboOutPut: this.value_tenVatTu,
        Output: this.serialChange,
        Kieu_OutPut: 1,
      });
    },
    cancelUpdateSerial() {
      this.$emit("cancel-update-serial", {
        CboOutPut: this.value_tenVatTu,
        Output: this.serialChange,
        Kieu_OutPut: 0,
      });
    },
  },
  mounted: function () {},
};
</script>