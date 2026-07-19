<template src="./template.html"> </template>
<style src="./style.scss"></style>
<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
export default {
  components: {
    breadcrumb
  },
  data() {
    return {
      tbDSNhanVien_data: [
        { ma_nv: 'ND001', ten_nv: 'Nguyễn Văn A', donvi: 'Đơn vị 1', khoamo: 'Mở' },
        { ma_nv: 'ND002', ten_nv: 'Nguyễn Văn B', donvi: 'Đơn vị 2', khoamo: 'Khóa' },
        { ma_nv: 'ND003', ten_nv: 'Nguyễn Văn C', donvi: 'Đơn vị 3', khoamo: 'Mở' },
        { ma_nv: 'ND004', ten_nv: 'Nguyễn Văn D', donvi: 'Đơn vị 4', khoamo: 'Khóa' },
        { ma_nv: 'ND005', ten_nv: 'Nguyễn Văn E', donvi: 'Đơn vị 5', khoamo: 'Mở' }
      ],
      tbDSChon_data: [],
      tempData: [],
      tempChoosed: [],
    }
  },
  methods: {
    selectAll() {
      this.tbDSChon_data = this.tbDSNhanVien_data
      this.$toast.success('Đã chọn tất cả nhân viên')
    },
    unSelectAll(){
      this.tbDSChon_data = []
      this.$toast.success('Đã bỏ chọn tất cả nhân viên')
    },
    selectedItemsChanged(rows){
      this.tempData = rows
    },
    selectChoosed(){
      this.tbDSChon_data = this.tempData
      this.$toast.success('Đã chọn nhân viên đã chọn')
    },
    selectedItemsChoosed(rows){
      this.tempChoosed = rows
      
    },
    async unSelectChoosed(){
      try {
        this.loading(true)
        this.tbDSChon_data = await this.tbDSChon_data.filter(item => !this.tempChoosed.includes(item))
        this.$toast.success('Đã bỏ chọn nhân viên đã chọn')
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra')
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>
