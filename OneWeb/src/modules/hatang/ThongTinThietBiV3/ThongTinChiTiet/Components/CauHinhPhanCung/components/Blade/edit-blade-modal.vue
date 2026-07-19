<template src="./edit-blade-modal.html"></template>
<script>
export default {
  data() {
    return {
      currentBlade:{},
      dsBladeNew : [],
    }
  },
  methods: {
    openModal: async function(data) {
      this.currentBlade = data;
      await this.layDsBladeIdc();
      await this.$refs.modalEditBlade.show();
    },
    closeModal() {
      this.$refs.modalEditBlade.hide();
    },
    layDsBladeIdc: async function() {
      const response = await this.apiDsBladeIdc();
      this.dsBladeNew = response;
    },
    apiDsBladeIdc: async function() {
      let result = []
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(`/web-ecms/hatang/cauhinh-phancung-thietbi/ds-blade-idc`, {
          thietbi_id: this.$parent.thietBiCurrent.ID
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      } finally {
        this.$root.showLoading(false)
      }
      return result
    },
    onRecordDoubleClick: async function(data) {
      this.currentBlade.THIETBI_ID = data.rowData.THIETBI_ID;
      this.currentBlade.TEN = data.rowData.TEN; 
      this.currentBlade.SERIAL_NUMBER = data.rowData.SERIAL_NUMBER;
      await this.$parent.updateBlade(this.currentBlade);
      this.closeModal();
    },
  }
}
</script>
