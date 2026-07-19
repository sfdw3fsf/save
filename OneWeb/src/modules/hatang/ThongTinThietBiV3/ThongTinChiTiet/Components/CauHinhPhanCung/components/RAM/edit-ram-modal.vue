<template src="./edit-ram-modal.html"></template>
<script>

import danhMucApi from '../../../../../api/DanhMuc.api'

export default {
  data() {
    return {
      dsDmTrangThaiVH:[],
      currentRam:{},
    }
  },
  methods: {
    openModal: async function(data) {
      this.currentRam = data;
      await this.$refs.modalEditRAM.show();
      await this.apiDmTrangThaiVH();  
    },
    closeModal() {
      this.$refs.modalEditRAM.hide();
    },
    onSave: async function(){
      if(!this.validate()) return;
      this.$parent.localDsRAM = this.$parent.localDsRAM.map(ram => {
        if (ram.STT === this.currentRam.STT) {
          return { ...ram, ...this.currentRam };
        }
        return ram;
      });
      await this.$parent.loadThongTinDaCam();
      this.closeModal();
    },
    apiDmTrangThaiVH: async function() {
      try {
        this.$root.showLoading(true);
        const response = await danhMucApi.getTrangThaiVH(this.$root.context)
        this.dsDmTrangThaiVH = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu loại thiết bị')
      } finally {
        this.$root.showLoading(false);
      }
    },
    validate() {      
      let msg = ''
      if (!this.currentRam.TEN_VITRI || this.currentRam.TEN_VITRI.trim() == '') {
        msg = 'Tên khe không được để trống.'
      } else if (!this.currentRam.TEN_RAM || this.currentRam.TEN_RAM.trim() == '') {
        msg = 'Tên Ram không được để trống.'
      } else if (!this.currentRam.LOAI_RAM || this.currentRam.LOAI_RAM.trim() == '') {
        msg = 'Loại Ram không được để trống.'
      }else if (isNaN(this.currentRam.DUNGLUONG) || Number(this.currentRam.DUNGLUONG) <= 0) {
        msg = 'Dung lượng Cores là số lớn hơn 0.'
      } else if (isNaN(this.currentRam.TOCDO) || Number(this.currentRam.TOCDO) <= 0) {
        msg = 'Tốc độ là số lớn hơn 0.'
      } else if (!this.currentRam.TRANGTHAIVH_ID) {
        msg = 'Trạng thái không được để trống.'
      }

      if (msg == '') return true
      this.$root.toastError(msg)
      return false
    },
    loaiTTVHChanged: function(id) {
      const tt = this.dsDmTrangThaiVH.find((item) => item.ID == id)
      this.currentRam.TRANGTHAI_VH = tt ? tt.TEN : ''
    },
  }
}
</script>
