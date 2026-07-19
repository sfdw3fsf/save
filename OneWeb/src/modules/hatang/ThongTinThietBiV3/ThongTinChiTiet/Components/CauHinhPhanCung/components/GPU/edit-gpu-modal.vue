<template src="./edit-gpu-modal.html"></template>
<script>

import danhMucApi from '../../../../../api/DanhMuc.api'

export default {
  data() {
    return {
      dsDmTrangThaiVH:[],
      currentGPU:{},
    }
  },
  methods: {
    openModal: async function(data) {
      await this.$refs.modalEditGPU.show();
      await this.apiDmTrangThaiVH();  
      this.currentGPU = data;
    },
    closeModal() {
      this.$refs.modalEditGPU.hide();
    },
    onSave: async function(){
      if(!this.validate()) return;
      this.$parent.localDsGPU = this.$parent.localDsGPU.map(gpu => {
        if (gpu.STT === this.currentGPU.STT) {
          return { ...gpu, ...this.currentGPU };
        }
        return gpu;
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
      if (!this.currentGPU.MODEL || this.currentGPU.MODEL.trim() == '') {
        msg = 'Model không được để trống.'
      } else if (!this.currentGPU.SERIAL || this.currentGPU.SERIAL.trim() == '') {
        msg = 'Serial không được để trống.' 
      } else if (!this.currentGPU.TRANGTHAIVH_ID) {
        msg = 'Trạng thái không được để trống.'
      }

      if (msg == '') return true
      this.$root.toastError(msg)
      return false
    },
    loaiTTVHChanged: function(id) {
      const tt = this.dsDmTrangThaiVH.find((item) => item.ID == id)
      this.currentGPU.TRANGTHAI_VH = tt ? tt.TEN : ''
    },
  }
}
</script>
