<template src="./HandOverTicketVerifyCombine.html"></template>
<style src="./HandOverTicketVerifyCombine.scss" scoped></style>
<script>
import breadcrumb from "@/components/breadcrumb";

import popupGiaoPhieuPhoiHop from "./popupGiaoPhieuPhoiHop.vue";

export default {
  components: {
    breadcrumb,
    popupGiaoPhieuPhoiHop,
  },
  name: "HandOverTicketVerifyCombine",
  data() {
    return {
      quytrinh_id: 69,
      nguoigiao_id: Number(this.$root.token.getNhanVienID()),
    };
  },
  methods: {
    showModal() {
      this.$refs.popupGiaoPhieuPhoiHop.showModal();
    },
  },
};
</script>
