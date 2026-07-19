<template src="./index.template.html"></template>

<script>
export default {
  name: 'DacTinhThietBi',
  data() {
    return {
      tab_index: 1, // Default active tab for server info
      disk_shelf_tab_index: 1 // Default active tab for disk shelf info (1: Controller, 2: Disk Tray)
    }
  },
  props: {
    currentItem: {
      type: Object,
      required: true
    },
    controls: {
      type: Object,
      required: true
    },
    manufacturerList: {
      type: Array,
      default: () => []
    },
    supplierList: {
      type: Array,
      default: () => []
    },
    usageStatusList: {
      type: Array,
      default: () => []
    },
    deviceRoleList: {
      type: Array,
      default: () => []
    },
    OSList: {
      type: Array,
      default: () => []
    },
    isServer: {
      type: Boolean,
      default: false
    },
    isNetwork: {
      type: Boolean,
      default: false
    },
    isDiskShelf: {
      type: Boolean,
      default: false
    },
    isIDC: {
      type: Boolean,
      default: false
    },
    isOther: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    onChangeTab(index) {
      this.tab_index = index
    },
    onChangeDiskShelfTab(index) {
      this.disk_shelf_tab_index = index
    },
    validateControls() {
      let msg = ''
      // Add validation logic here if needed
      return msg
    },
    enableControls(enabled) {
      Object.keys(this.controls).forEach((key) => {
        if (this.controls[key].hasOwnProperty('enabled')) {
          this.controls[key].enabled = enabled
        }
      })
    },
    resetValidation() {
      // Reset tất cả validation states về false
      Object.keys(this.controls).forEach((key) => {
        if (this.controls[key].hasOwnProperty('invalid')) {
          this.controls[key].invalid = false
        }
      })
    },
    normalizer(node) {
      return {
        id: node.ID,
        label: node.TEN
      }
    }
  }
}
</script>

<style scoped>
.cpu-section,
.ram-section,
.controller-section,
.disk-tray-section,
.storage-capacity-section,
.hdd-section {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #dee2e6;
  border-radius: 5px;
}
.power-section {
  padding: 12px;
  border: 1px solid #dee2e6;
  border-radius: 5px;
  margin-bottom: 10px;
}

.cpu-section h6,
.ram-section h6,
.hdd-section h6,
.power-section h6,
.controller-section h6,
.disk-tray-section h6,
.storage-capacity-section h6,
.disk-shelf-section h6 {
  color: #495057;
  font-weight: bold;
}

.field-group-wrapper {
  display: flex;
  flex: 1;
  gap: 5px;
  flex-wrap: wrap;
}

.field-group {
  display: flex;
  align-items: center;
  flex: 1;
  min-width: 300px; /* Force wrapping when space is limited */
}

.field-group .value {
  flex-grow: 1;
}

.is-invalid {
  border: 1px solid #dc3545 !important;
  border-radius: 4px;
}

.invalidBox {
  border: 1px solid #dc3545 !important;
  border-radius: 4px;
}
</style>
