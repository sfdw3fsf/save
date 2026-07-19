<template src="./index.template.html"></template>
<script>
import { createLookupTemplate } from '../../../../template/lookupTemplate.js'

export default {
  name: 'DeviceRelationInfo',
  props: {
    connectorList: {
      type: Array,
      default: () => []
    },
    infraList: {
      type: Array,
      default: () => []
    },
    colocationList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      currentTab: 1
    }
  },
  computed: {
    connectorCols() {
      return [
        { fieldName: 'SOTHUTU', headerText: 'STT', width: 100, allowFiltering: false },
        { fieldName: 'MATHIETBI', headerText: 'Mã thiết bị', allowFiltering: true },
        { fieldName: 'TEN', headerText: 'Tên thiết bị', allowFiltering: true },
        { fieldName: 'TEN_PHANLOAI', headerText: 'Loại thiết bị', allowFiltering: true },
        {
          fieldName: 'LINK',
          headerText: 'Chi tiết',
          allowFiltering: true,
          width: 100,
          template: this.getColumnTemplate(this)
        }
      ]
    },
    infraCols() {
      return [
        { fieldName: 'SOTHUTU', headerText: 'STT', width: 100, allowFiltering: false },
        { fieldName: 'TENHATANG', headerText: 'Tên hạ tầng', allowFiltering: true },
        { fieldName: 'LOAIHATANG', headerText: 'Loại hạ tầng', allowFiltering: true },
        { fieldName: 'NGAY_TAO', headerText: 'Ngày tạo', allowFiltering: true },
        { fieldName: 'TEN_NGUOIQLY', headerText: 'Người quản lý', allowFiltering: true },
        { fieldName: 'TEN_DONVIQLY', headerText: 'Đơn vị quản lý', allowFiltering: true }
      ]
    },
    colocationCols() {
      return [
        { fieldName: 'SOTHUTU', headerText: 'STT', width: 100, allowFiltering: false },
        { fieldName: 'MATHIETBI', headerText: 'Phiếu yêu cầu', allowFiltering: true },
        { fieldName: 'TENTHIETBI', headerText: 'Loại phiếu', allowFiltering: true },
        { fieldName: 'LOAITHIETBIMANG', headerText: 'Hạng mục triển khai', allowFiltering: true },
        { fieldName: 'LOAITHIETBIMANG', headerText: 'Loại công việc', allowFiltering: true },
        { fieldName: 'LOAITHIETBIMANG', headerText: 'Đơn vị thực hiện', allowFiltering: true },
        { fieldName: 'LOAITHIETBIMANG', headerText: 'Người thực hiện', allowFiltering: true },
        { fieldName: 'LOAITHIETBIMANG', headerText: 'Thời điểm hoàn thành', allowFiltering: true },
        { fieldName: 'LOAITHIETBIMANG', headerText: 'Trạng thái thực hiện', allowFiltering: true }
      ]
    }
  },
  methods: {
    getColumnTemplate(parent) {
      return () => {
        return {
          template: {
            template: `
              <div style="display: flex; justify-content: center; align-items: center;">
                <button class="btn btn-second btn-detail" style="width: 50%;" @click="parent.handleDetailClick(data)">Chi tiết</button>
              </div>
            `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    handleDetailClick(data) {
      this.$emit('view-device-detail', data.MATHIETBI)
    }
  }
}
</script>
<style scoped></style>
