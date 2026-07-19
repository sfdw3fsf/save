import PortModal from './PortModal.vue'
import PortData from './PortData.vue'
import CreatePortType from './Modal/CreatePortType.vue'
import ModalThemCard from './Modal/ModalThemCard/index.vue'
import { PORT_TYPE } from './test.js'

export default {
  name: 'PortType',
  components: {
    PortModal,
    PortData,
    CreatePortType,
    ModalThemCard
  },
  props: {
    deviceData: {
      type: Object,
      default: () => ({})
    },
    interfaceList: {
      type: Array,
      default: () => []
    },
    powerList: {
      type: Array,
      default: () => []
    },
    consoleList: {
      type: Array,
      default: () => []
    },
    portInterfaceList: {
      type: Array,
      default: () => []
    },
    portTypeInterfaceList: {
      type: Array,
      default: () => []
    },
    portPowerList: {
      type: Array,
      default: () => []
    },
    portTypePowerList: {
      type: Array,
      default: () => []
    },
    portConsoleList: {
      type: Array,
      default: () => []
    },
    portTypeConsoleList: {
      type: Array,
      default: () => []
    },
    isInModal: {
      type: Boolean,
      default: () => false
    }
  },
  inject: ['setCurrentItemValByProp', 'createCardModule'],
  data() {
    return {
      currentTab: 1,
      controls: {
        lineNumber: {
          enabled: false
        },
        operation: {
          enabled: false
        },
        interface: {
          enabled: true
        },
        power: {
          enabled: true
        },
        console: {
          enabled: true
        }
      }
    }
  },
  computed: {
    currentItem() {
      if (this.isInModal) return this.$parent.$parent.currentItem
      return this.$parent.currentItem
    },
    cardModuleInfo() {
      if (this.isInModal) return this.$parent.$parent.cardModuleInfo
      return this.$parent.cardModuleInfo
    },
    disabledInterface() {
      return this.cardModuleInfo.isCardModuleMode
        ? !(this.cardModuleInfo.portType === PORT_TYPE.INTERFACE.ID)
        : !this.controls.interface.enabled
    },
    disabledPower() {
      return this.cardModuleInfo.isCardModuleMode
        ? !(this.cardModuleInfo.portType === PORT_TYPE.POWER.ID)
        : !this.controls.power.enabled
    },
    disabledConsole() {
      return this.cardModuleInfo.isCardModuleMode
        ? !(this.cardModuleInfo.portType === PORT_TYPE.CONSOLE.ID)
        : !this.controls.console.enabled
    },
    disabledLineNumber() {
      return this.cardModuleInfo.isCardModuleMode ? true : !this.controls.lineNumber.enabled
    },
    lineNumber: {
      get() {
        const lineNumberMapping = {
          [PORT_TYPE.INTERFACE.ID]: 'so_linecard',
          [PORT_TYPE.POWER.ID]: 'so_power',
          [PORT_TYPE.CONSOLE.ID]: 'so_controlcard'
        }

        return this.currentItem[lineNumberMapping[this.currentTab]] || 0
      },
      set(newValue) {
        const value = Number.parseInt(newValue)
        let numValue = Number.isInteger(value) ? value : 0

        const lineNumberMapping = {
          [PORT_TYPE.INTERFACE.ID]: 'so_linecard',
          [PORT_TYPE.POWER.ID]: 'so_power',
          [PORT_TYPE.CONSOLE.ID]: 'so_controlcard'
        }

        const propName = lineNumberMapping[this.currentTab]

        if (propName) {
          this.currentItem[propName] = numValue
          this.setCurrentItemValByProp(propName, numValue)
        }
      }
    },
    totalPort: {
      get() {
        return this.getTotalPort(this.currentTab)
      },
      set(newValue) {
        newValue = Number.isInteger(newValue) ? newValue : 0
        this.setTotalPort(this.currentTab, newValue)
      }
    },
    hasLineNumber() {
      return this.lineNumber ? true : false
    },
    currentPortTypeList() {
      const portTypeMapping = {
        [PORT_TYPE.INTERFACE.ID]: this.portTypeInterfaceList,
        [PORT_TYPE.POWER.ID]: this.portTypePowerList,
        [PORT_TYPE.CONSOLE.ID]: this.portTypeConsoleList
      }

      return portTypeMapping[this.currentTab] || []
    },
    deviceList() {
      const deviceMapping = {
        [PORT_TYPE.INTERFACE.ID]: this.interfaceList,
        [PORT_TYPE.POWER.ID]: this.powerList,
        [PORT_TYPE.CONSOLE.ID]: this.consoleList
      }

      return deviceMapping[this.currentTab] || []
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
                <button class="btn btn-second btn-detail" style="width: 50%;" @click="parent.handleDetailCLick(data)">Chi tiết</button>
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
    enableControls(args) {
      Object.keys(this.controls).forEach((key) => {
        if (this.controls[key].hasOwnProperty('enabled')) {
          this.controls[key].enabled = args
        }
      })
    },
    openModal() {
      this.$refs.dialogPortList.dialogOpen()
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
