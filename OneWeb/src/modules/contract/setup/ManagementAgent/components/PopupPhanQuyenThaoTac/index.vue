<template src="./PopupPhanQuyenThaoTac.html"></template>

<script>
import api from "../../api";
export default {
  name: "PopupPhanQuyenThaoTac",
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
      showModal: false,
      dangKyHoChieu: false,
      capNhatThongTin: false,
      so_tb: ""
    };
  },
  methods: {
    async onChangePermission() {
      const payload = {
        so_tb_daily: this.so_tb,
        dk_hc: this.dangKyHoChieu,
        dk_capnhat_tt: this.capNhatThongTin
      };
      const response = await api.updateDaiLyPermission(
        this.$root.axios,
        payload
      );
      const { result, error_code, message } = response.data;

      if (error_code === "BSS-00000000") {
        this.$toast.success(message, "title");
        this.closeModal();
      } else this.$toast.warning(message, "title");
    },
    async getPermission() {
      const payload = {
        so_tb: this.so_tb
      };
      const response = await api.getDaiLyPermission(this.$root.axios, payload);
      const { result, error_code, message, data } = response.data;
      if (error_code === "BSS-00000000") {
        const { DK_HOCHIEU, EDIT_THUEBAO } = data[0];
        this.dangKyHoChieu = DK_HOCHIEU ? true : false;
        this.capNhatThongTin = EDIT_THUEBAO ? true : false;
      }
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
      if (newValue) {
        this.so_tb = this.row.ELOADMSISDN;
        this.getPermission();
      }
    }
  }
};
</script>
