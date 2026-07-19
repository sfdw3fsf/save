<template src="./PortData.html"></template>
<script>
import { DEFAULT_PORT_TYPE, PORT_TYPE, USAGE_STATUS_LIST, CONNECTED_LIST } from '../../../../../const.js'
import { createLookupTemplate } from '../../../../../template/lookupTemplate.js'

export default {
  name: 'PortData',
  props: {
    deviceDetailData: {
      type: Object,
      default: () => ({})
    },
    portType: {
      type: String,
      default: PORT_TYPE.INTERFACE.NAME
    },
    portTypeData: {
      type: Array,
      default: () => []
    },
    portListData: {
      type: Array,
      default: () => []
    },
    hasLineNumber: {
      type: Boolean,
      default: false
    },
    loaiCongList: {
      type: Array,
      default: () => []
    },
    mucDichList: {
      type: Array,
      default: () => []
    },
    bangThongList: {
      type: Array,
      default: () => []
    },
    thongtinModuleList: {
      type: Array,
      default: () => []
    }
  },
  inject: ['deletePortTypeItem'],
  computed: {
    createPortRef() {
      return this.$parent.$refs.createPortType
    },
    portTypeCols() {
      return [
        {
          fieldName: 'loaicong_id',
          headerText: 'Loại cổng',
          allowFiltering: true,
          template: createLookupTemplate({
            field: 'loaicong_id',
            list: this.loaiCongList,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        },
        {
          fieldName: 'so_cong',
          headerText: 'Số cổng',
          allowFiltering: true,
          width: 120
        },
        {
          fieldName: 'bat_dau',
          headerText: 'Bắt đầu từ',
          allowFiltering: true,
          width: 120
        },
        {
          fieldName: 'port_fname',
          headerText: 'Tên cổng',
          allowFiltering: true
        },
        {
          fieldName: 'mucdichsudung_cong_id',
          headerText: 'Mục đích',
          allowFiltering: true,
          template: createLookupTemplate({
            field: 'mucdichsudung_cong_id',
            list: this.mucDichList,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        },
        {
          fieldName: 'bangthongcong_id',
          headerText: 'Băng thông',
          allowFiltering: true,
          template: createLookupTemplate({
            field: 'bangthongcong_id',
            list: this.bangThongList,
            valueKey: 'ID',
            labelKey: 'TEN'
          }),
          width: 130
        },
        {
          fieldName: 'thongtinmodule_id',
          headerText: 'Thông tin Module',
          allowFiltering: true,
          template: createLookupTemplate({
            field: 'thongtinmodule_id',
            list: this.thongtinModuleList,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        },
        {
          fieldName: 'ketnoi',
          headerText: 'Kết nối',
          allowFiltering: true,
          width: 110,
          template: createLookupTemplate({
            field: 'ketnoi',
            list: CONNECTED_LIST,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        }
      ]
    },
    portListCols() {
      return [
        { fieldName: 'sothutu', headerText: 'STT', allowFiltering: true, width: 70, textAlign: 'center' },
        { fieldName: 'ten', headerText: 'Tên cổng', allowFiltering: true },
        {
          fieldName: 'loaicong_id',
          headerText: 'Loại cổng',
          allowFiltering: true,
          width: 120,
          template: createLookupTemplate({
            field: 'loaicong_id',
            list: this.loaiCongList,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        },
        {
          fieldName: 'mucdichsudung_cong_id',
          headerText: 'Mục đích',
          allowFiltering: true,
          template: createLookupTemplate({
            field: 'mucdichsudung_cong_id',
            list: this.mucDichList,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        },
        {
          fieldName: 'bangthongcong_id',
          headerText: 'Băng thông',
          allowFiltering: true,
          width: 120,
          template: createLookupTemplate({
            field: 'bangthongcong_id',
            list: this.bangThongList,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        },
        {
          fieldName: 'thongtinmodule_id',
          headerText: 'Thông tin Module',
          allowFiltering: true,
          template: createLookupTemplate({
            field: 'thongtinmodule_id',
            list: this.thongtinModuleList,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        },
        {
          fieldName: 'trangthai_sd',
          headerText: 'Trạng thái',
          allowFiltering: true,
          template: createLookupTemplate({
            field: 'trangthai_sd',
            list: USAGE_STATUS_LIST,
            valueKey: 'ID',
            labelKey: 'TEN'
          }),
          width: 100,
          textAlign: 'center'
        },
        {
          fieldName: 'NULL',
          headerText: 'Đấu nối',
          allowFiltering: false,
          template: this.getColumnDetailTemplate(this),
          width: 130
        }
      ]
    }
  },
  methods: {
    getColumnDetailTemplate(parent) {
      return () => {
        return {
          template: {
            template: `
              <div style="display: flex; justify-content: center">
                <button class="btn btn-second btn-detail" @click="parent.handleDetailClick(data)">Chi tiết</button>
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
      this.$router.push(`/ha-tang/chi-tiet-cong-thiet-bi-thiet-bi/${data.id}/${data.thietbi_id}`)
    },
    onCreatePortType() {
      this.createPortRef.dialogOpen()
    },
    grid_onCommandClicked(name, dataItem) {
      switch (name) {
        case 'SUA':
          this.handleUpdatePortType(dataItem)
          break
        case 'XOA':
          this.deletePortTypeItem(this.portType, dataItem)
      }
    },
    handleUpdatePortType(item) {
      this.createPortRef.setPortTypeData(item)
      this.createPortRef.dialogOpen()
    }
  }
}
</script>
<style scoped>
.title-with-btn {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
}

.title {
  font-weight: bold;
}

.legend-title {
  font-size: 14px;
  color: #666;
}
</style>
