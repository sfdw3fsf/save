<template src="./CardData.html"></template>
<style src="./styles.scss" scoped></style>
<script>
import { PORT_TYPE, USAGE_STATUS_LIST } from '../../../../const.js'
import { createLookupTemplate } from '../../../../template/lookupTemplate.js'

export default {
  name: 'CardData',
  props: {
    deviceData: {
      type: Array,
      default: () => []
    },
    portType: {
      type: String,
      default: PORT_TYPE.INTERFACE.NAME
    },
  },
  inject: ['deleteCardItem', 'updateCardModule', 'createCardModule'],
  computed: {
    deviceCols() {
      return [
        { fieldName: 'model', headerText: 'Model thiết bị', allowFiltering: false },
        {
          fieldName: 'hangsx_id',
          headerText: 'Nhà sản xuất',
          allowFiltering: true,
          template: createLookupTemplate({
            field: 'hangsx_id',
            list: this.manufacturerList,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        },
        { fieldName: 'up_unit_id', headerText: 'Chiều cao', allowFiltering: true },
        { fieldName: 'unit_qty', headerText: 'Full Depth', allowFiltering: true },
        { fieldName: 'cs_dinhdanh', headerText: 'Công suất định danh', allowFiltering: true },
        {
          fieldName: 'hieu_luc',
          headerText: 'Hiệu lực',
          allowFiltering: true,
          template: createLookupTemplate({
            field: 'hieu_luc',
            list: USAGE_STATUS_LIST,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        }
        // { fieldName: 'CHI_TIET', headerText: 'Chi tiết', allowFiltering: false, template: this.getColumnTemplate(this) }
      ]
    },
    deviceDataLabel() {
      const labelMapping = {
        [PORT_TYPE.INTERFACE.NAME]: 'Line Card',
        [PORT_TYPE.POWER.NAME]: 'Power Module',
        [PORT_TYPE.CONSOLE.NAME]: 'Console Card'
      }
      return labelMapping[this.portType] || 'Thiết bị'
    },
    manufacturerList() {
      return this.$parent.$parent.$parent.manufacturerList
    },
    isEditable() {
      return this.$parent.$parent.$parent.currentMode != 'VIEW'
    }
  },
  methods: {
    onCreateCardModule() {
      this.createCardModule(this.portType)
    }
  }
}
</script>
