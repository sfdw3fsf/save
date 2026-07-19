<template src="./edit-khaydia-modal.html"></template>
<script>
export default {
  data() {
    return {
      DsKHDNew: [],
    }
  },
  methods: {
    openModal: async function() {
      await this.$refs.modalEditKhayDia.show();
      await this.layDsDsKHDIdc();
    },
    onSave: async function(){
      if(this.$refs.danhSachKHDChonGrd.getSelectedRecords().length = 0){
        this.$root.toastError('Bạn chưa chọn thiết bị nào để thêm vào khay đĩa');
        return;
      } 

      // sắp xêp lại STT
      let items = this.$refs.danhSachKHDChonGrd.getSelectedRecords(); 
      items.forEach((item, index) => {
        item.STT = index + 1;
      });

      this.$parent.localDsKhayDia = items;
      this.closeModal();
    },
    closeModal() {
      this.$refs.modalEditKhayDia.hide();
    },
    layDsDsKHDIdc: async function() {
      const response = await this.apiDsKHDIdc();
      this.DsKHDNew = response;
    },
    apiDsKHDIdc: async function() {
      let result = []
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(`/web-ecms/hatang/cauhinh-phancung-thietbi/ds-khaydia-idc`, {
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
  }
}
</script>
