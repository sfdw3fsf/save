<template>
  <b-modal
    ref="DangKyChiTietKhuyenMaiModal"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="modal-body p-0"
    @show="handleShowModal"
  >
    <popupDangkychitietkhuyenmai ref="popupDangkychitietkhuyenmai" :dulieu="dulieu" @form-close="formClose" />
  </b-modal>
</template>
<script>
//Clone branch develop-erp
import popupDangkychitietkhuyenmai from "./popup-Dangkychitietkhuyenmai.vue";
export default {
  name: "DangKyChiTietKhuyenMaiModal",
  props: ["dulieu"],
  components: {
    popupDangkychitietkhuyenmai,
  },
  data() {
    return {};
  },
  methods: {
    showModal() {
      this.$refs["DangKyChiTietKhuyenMaiModal"].show();
    },
    hideModal() {
      this.$refs["DangKyChiTietKhuyenMaiModal"].hide();
    },
    formClose(ds) {
      this.$emit("form-close", ds);
      this.hideModal();
    },
    handleShowModal() {},
  },
};
</script>