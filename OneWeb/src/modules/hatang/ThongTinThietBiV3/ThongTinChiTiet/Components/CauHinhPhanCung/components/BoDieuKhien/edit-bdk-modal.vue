<template src="./edit-bdk-modal.html"></template>
<script>
export default {
  data() {
    return {
      DsBodKhNew : [],
    }
  },
  methods: {
    openModal: async function() {
      await this.$refs.modalEditBDK.show();
      await this.layDsBodkhIdc();
    },
    closeModal() {
      this.$refs.modalEditBDK.hide();
    },
    onSave: async function(){
      if(this.$refs.danhSachBDKChonGrd.getSelectedRecords().length = 0){
        this.$root.toastError('Bạn chưa chọn thiết bị nào để thêm vào bộ điều khiển');
        return;
      } 

      // sắp xêp lại STT
      let items = this.$refs.danhSachKHDChonGrd.getSelectedRecords(); 
      items.forEach((item, index) => {
        item.STT = index + 1;
      });

      this.$parent.localDsBodKh = items;
      this.closeModal();
    },
    layDsBodkhIdc: async function() {
      const response = await this.apiDsBodkhIdc();
      this.DsBodKhNew = response;
    },
    apiDsBodkhIdc: async function() {
      let result = []
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(`/web-ecms/hatang/cauhinh-phancung-thietbi/ds-bodkh-idc`, {
          thietbi_id: this.$parent.thietBiCurrent.ID
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      } finally {
        this.$root.showLoading(false)
      }
      return result
    }
  },
}
</script>
