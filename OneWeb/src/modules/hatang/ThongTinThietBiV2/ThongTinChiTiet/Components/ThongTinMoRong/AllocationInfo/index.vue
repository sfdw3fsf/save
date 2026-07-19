<template src="./index.template.html"></template>
<script>
import { USAGE_STATUS_LIST } from '../../../../const.js'
import { createLookupTemplate } from '../../../../template/lookupTemplate.js'

export default {
  name: 'AllocationInfo',
  props: {
    VMList: {
      type: Array,
      default: () => []
    },
    NFSList: {
      type: Array,
      default: () => []
    },
    controllerList: {
      type: Array,
      default: () => []
    },
    shelvesList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      currentTab: 1,
      currentItem: this.$parent.currentItem
    }
  },
  computed: {
    VMCols() {
      return [
        { fieldName: 'TENVM', headerText: 'Tên VM', allowFiltering: false },
        { fieldName: 'SPDV', headerText: 'SPDV', allowFiltering: true },
        { fieldName: 'IP_ADDRESS', headerText: 'IP', allowFiltering: true },
        { fieldName: 'VRAM', headerText: 'RAM', allowFiltering: true },
        { fieldName: 'VCPU', headerText: 'CPU', allowFiltering: true },
        { fieldName: 'HATANGAOHOA', headerText: 'Hạ tầng ảo hóa', allowFiltering: true },
        { fieldName: 'NGUOIQTRI', headerText: 'Người quản trị', allowFiltering: true },
        { fieldName: 'DONVIQLY', headerText: 'Đơn vị quản trị', allowFiltering: true },
        {
          fieldName: 'HIEU_LUC',
          headerText: 'Hiệu lực',
          allowFiltering: true,
          template: createLookupTemplate({
            field: 'HIEU_LUC',
            list: USAGE_STATUS_LIST,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        }
      ]
    },
    NFSCols() {
      return [
        { fieldName: 'TEN', headerText: 'Tên NFS', allowFiltering: false },
        { fieldName: 'DUNGLUONG', headerText: 'Dung lượng', allowFiltering: true },
        { fieldName: 'HATANGAOHOA', headerText: 'Hạ tầng ảo hóa', allowFiltering: true },
        { fieldName: 'MAYAO', headerText: 'Gắn với VM', allowFiltering: true },
        {
          fieldName: 'HIEU_LUC',
          headerText: 'Trạng thái',
          allowFiltering: true,
          template: createLookupTemplate({
            field: 'HIEU_LUC',
            list: USAGE_STATUS_LIST,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        }
      ]
    },
    controllerCols() {
      return [
        { fieldName: 'SOTHUTU', headerText: 'STT', width: 100, allowFiltering: false },
        { fieldName: 'TEN', headerText: 'Tên thiết bị', allowFiltering: true },
        { fieldName: 'KYHIEU', headerText: 'Ký hiệu', allowFiltering: true },
        { fieldName: 'SERIAL_NUMBER', headerText: 'Serial Number', allowFiltering: true },
        { fieldName: 'SOTHUTU', headerText: 'Số thứ tự', allowFiltering: true },
        { fieldName: 'NGAY_LAPDAT', headerText: 'Ngày lắp đặt', allowFiltering: true },
        {
          fieldName: 'TRANGTHAI_SD',
          headerText: 'Trạng thái',
          allowFiltering: true,
          template: createLookupTemplate({
            field: 'TRANGTHAI_SD',
            list: USAGE_STATUS_LIST,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        },
        { fieldName: '', headerText: 'Chi tiết', allowFiltering: true, template: this.getColumnTemplate(this) }
      ]
    },
    shelvesCols() {
      return [
        { fieldName: 'sothutu', headerText: 'STT', width: 100, allowFiltering: false },
        { fieldName: 'ten', headerText: 'Tên thiết bị', allowFiltering: true },
        { fieldName: 'kyhieu', headerText: 'Ký hiệu', allowFiltering: true },
        { fieldName: 'serial_number', headerText: 'Serial Number', allowFiltering: true },
        { fieldName: 'sothutu', headerText: 'Số thứ tự', allowFiltering: true },
        { fieldName: 'ssd_tong', headerText: 'Tổng SSD', allowFiltering: true },
        { fieldName: 'ssd_capphat', headerText: 'SSD đã cắm', allowFiltering: true },
        { fieldName: 'hdd_tong', headerText: 'Tổng HDD', allowFiltering: true },
        { fieldName: 'hdd_capphat', headerText: 'HDD đã cắm', allowFiltering: true },
        {
          fieldName: 'f_trangthaisd',
          headerText: 'Trạng thái',
          allowFiltering: true,
          template: createLookupTemplate({
            field: 'f_trangthaisd',
            list: USAGE_STATUS_LIST,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        },
        { fieldName: '', headerText: 'Chi tiết', allowFiltering: true, template: this.getKhayDiaColumnTemplate(this) }
      ]
    }
  },
  watch: {},
  async created() {},
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
    getKhayDiaColumnTemplate(parent) {
      return () => {
        return {
          template: {
            template: `
              <div style="display: flex; justify-content: center; align-items: center;">
                <button class="btn btn-second btn-detail" style="width: 50%;" @click="parent.handleKhayDiaClick(data)">Chi tiết</button>
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
      console.log('Detail Clicked', data)
      this.$router.push(`/ha-tang/danhmuc-controller-tudia/${data.ID}`)
    },
    handleKhayDiaClick(data) {
      this.$router.push(`/ha-tang/khay-dia-trong-tu-dia/${data.id}`)
    }
  }
}
</script>
<style scoped></style>
