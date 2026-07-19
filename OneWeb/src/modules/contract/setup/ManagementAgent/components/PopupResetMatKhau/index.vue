<template src="./PopupResetMatKhau.html"></template>

<script>
import api from "../../api";
export default {
  name: "PopupResetMatKhau",
  props: {
    show: {
      type: Boolean,
      default: false
    },
    row: {
      type: [Object, null],
      default: () => {}
    }
  },
  data() {
    return {
      showModal: false
    };
  },
  methods: {
    async onResetPassword() {
      const { ELOADMSISDN: so_tb } = this.row;
      const payload = {
        so_tb
      };
      console.log("payload: ", payload);

      const response = await api.resetPassword(this.$root.axios, payload);
      const { result, error_code, message } = response.data;
      if (error_code === "BSS-00000000") {
        this.$toast.success(message, "title");
        this.closeModal();
      } else this.$toast.warning(message, "title");
    },
    closeModal() {
      this.$emit("update:show", false);
      this.$emit("update:row", null);
      this.showModal = false;
    }
  },
  watch: {
    show(newValue) {
      this.showModal = newValue;
    }
  }
};
</script>
