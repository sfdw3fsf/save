<template src="./CustomerCareInformation.html"></template>
<style src="./CustomerCareInformation.scss" scoped></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import KBreadCrumb from '@/components/kylq_components/mirana/KBreadCrumb.vue'

import CustomerCareInformationModal from "./CustomerCareInformationModal.vue";

export default {
  components: {
    breadcrumb,
    KBreadCrumb,
    CustomerCareInformationModal,
  },
  name: "CustomerCareInformation",
  methods: {
    showCusCareModal() {
      this.$refs.CustomerCareInformationModal.showModal();
    },
  },
};
</script>
