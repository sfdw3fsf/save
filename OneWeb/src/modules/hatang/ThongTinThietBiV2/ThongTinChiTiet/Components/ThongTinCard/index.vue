<template src="./index.template.html"></template>
<script>
import ModalListPortOfCard from './ModalListPortOfCard/index.vue'
import CardData from './CardData/CardData.vue'
import { PORT_TYPE } from '../../../const.js'

export default {
  name: 'ThongTinCard',
  components: {
    ModalListPortOfCard,
    CardData
  },
  props: {
    deviceData: { type: Object, default: () => ({}) },
    interfaceList: { type: Array, default: () => [] },
    powerList: { type: Array, default: () => [] },
    consoleList: { type: Array, default: () => [] },
    portInterfaceList: { type: Array, default: () => [] },
    portTypeInterfaceList: { type: Array, default: () => [] },
    portPowerList: { type: Array, default: () => [] },
    portTypePowerList: { type: Array, default: () => [] },
    portConsoleList: { type: Array, default: () => [] },
    portTypeConsoleList: { type: Array, default: () => [] }
  },
  inject: ['setCurrentItemValByProp'],
  data() {
    return {
      PORT_TYPE,
      currentTab: 1,
      controls: {
        operation: { enabled: false },
        interface: { enabled: true },
        power: { enabled: true },
        console: { enabled: true }
      }
    }
  },
  computed: {
    currentItem() {
      return this.deviceData || {}
    },
    currentPortTypeList() {
      const mapping = {
        [PORT_TYPE.INTERFACE.ID]: this.portTypeInterfaceList,
        [PORT_TYPE.POWER.ID]: this.portTypePowerList,
        [PORT_TYPE.CONSOLE.ID]: this.portTypeConsoleList
      }
      return mapping[this.currentTab] || []
    },
    deviceList() {
      const mapping = {
        [PORT_TYPE.INTERFACE.ID]: this.interfaceList,
        [PORT_TYPE.POWER.ID]: this.powerList,
        [PORT_TYPE.CONSOLE.ID]: this.consoleList
      }
      return mapping[this.currentTab] || []
    },
    totalPort: {
      get() {
        return this.getTotalPort(this.currentTab)
      },
      set(newValue) {
        const v = Number.isInteger(newValue) ? newValue : 0
        this.setTotalPort(this.currentTab, v)
      }
    },
  },
  methods: {
    enableControls(args) {
      Object.keys(this.controls).forEach((key) => {
        if (this.controls[key].hasOwnProperty('enabled')) {
          this.controls[key].enabled = args
        }
      })
    },
    onAdd(portType) {
      this.$emit('add-card', portType)
    },
    openModal() {
      this.$refs.dialogPortList?.dialogOpen()
    },
    getTotalPort(tab) {
      const portList = {
        [PORT_TYPE.INTERFACE.ID]: this.interfaceList,
        [PORT_TYPE.POWER.ID]: this.powerList,
        [PORT_TYPE.CONSOLE.ID]: this.consoleList
      }

      return portList[tab].reduce((acc, item) => acc + (item.port_qty || 0), 0) || 0
    },
    setTotalPort(tab, value) {
      const portMapping = {
        [PORT_TYPE.INTERFACE.ID]: 'so_port_interface',
        [PORT_TYPE.POWER.ID]: 'so_port_power',
        [PORT_TYPE.CONSOLE.ID]: 'so_port_console'
      }

      if (portMapping[tab]) {
        //this.$set() được dùng để đảm bảo Vue nhận diện thay đổi khi cập nhật currentItem
        this.$set(this.currentItem, postMapping[tab], value)
      }
    },
  }
}
</script>
<style scoped>
.table {
  width: 100%;
  border-collapse: collapse;
}
.table th,
.table td {
  border: 1px solid #e5e7eb;
  padding: 8px 10px;
}
.table th {
  background: #f9fafb;
  text-align: left;
}
</style>
