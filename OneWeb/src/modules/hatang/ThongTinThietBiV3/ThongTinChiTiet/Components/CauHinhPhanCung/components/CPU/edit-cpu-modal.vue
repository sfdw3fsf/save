<template src="./edit-cpu-modal.html"></template>
<script>

import danhMucApi from '../../../../../api/DanhMuc.api'

export default {
  data() {
    return {
      dsDmTrangThaiVH:[],
      currentCpu:{},
    }
  },
  methods: {
    openModal: async function(data) {
      this.currentCpu = data;
      await this.$refs.modalEditCPU.show();
      await this.apiDmTrangThaiVH();    
    },
    closeModal() {
      this.$refs.modalEditCPU.hide();
    },
    onSave: async function(){
      if(!this.validate()) return;
      this.$parent.localDsCpu = this.$parent.localDsCpu.map(cpu => {
        if (cpu.STT === this.currentCpu.STT) {
          return { ...cpu, ...this.currentCpu };
        }
        return cpu;
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
      if (!this.currentCpu.TEN_VITRI || this.currentCpu.TEN_VITRI.trim() == '') {
        msg = 'Tên không được để trống.'
      } else if (!this.currentCpu.MODEL || this.currentCpu.MODEL.trim() == '') {
        msg = 'Model không được để trống.'
      }else if (isNaN(this.currentCpu.SL_CORES) || Number(this.currentCpu.SL_CORES) <= 0) {
        msg = 'Số lượng Cores là số lớn hơn 0.'
      } else if (isNaN(this.currentCpu.SL_THREADS) || Number(this.currentCpu.SL_THREADS) <= 0) {
        msg = 'Số lượng Threads là số lớn hơn 0.'
      } else if (!this.currentCpu.TRANGTHAIVH_ID) {
        msg = 'Trạng thái không được để trống.'
      }

      if (msg == '') return true
      this.$root.toastError(msg)
      return false
    },
    loaiTTVHChanged: function(id) {
      const tt = this.dsDmTrangThaiVH.find((item) => item.ID == id)
      this.currentCpu.TRANGTHAI_VH = tt ? tt.TEN : ''
    },
  }
}
</script>
