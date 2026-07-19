<template src="./detail-bdk-modal.html"></template>
<script>
export default {
  data() {
    return {
      itemCurrent: {},
      isBDK: true,
    }
  },
  methods: {
    openModal: async function(data, isBDK) {  
      this.itemCurrent = data;
      this.isBDK = isBDK;
      this.$refs.modalDetailBDK.show();
    },
    closeModal() {
      this.$refs.modalDetailBDK.hide();
    },
  },
}
</script>
