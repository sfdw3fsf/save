<template src="./index.template.html"></template>
<script>
import ClickOutside from 'vue-click-outside'
import DataGridEdit from '../DataGrid2'

export default {
  name: 'ComboboxGridEdit',
  props: {
    columns: {
      type: Array,
      default: function () {
        return []
      }
    },
    dataSource: {
      type: Array,
      default: function () {
        return []
      }
    },
    showFilter: {
      type: Boolean,
      default: true
    },
    gridPanelDataHeight: {
      type: String,
      default: '300px'
    },
    enabledSelectFirstRow: {
      type: Boolean,
      default: true
    },
    editSettings: {
      type: Object,
      default: function () {
        return {}
      }
    }
  },
  components: {
    DataGridEdit
  },
  watch: {
    dataSource: function (value) {
      if (value === undefined || value === null) {
        if (this.value != null) {
          this.selectedItem = value.find(x => x[this.valueField] === this.value)
        } else {
          this.selectedItem = null
        }
      } else {
        this.$emit('input', null)
        this.selectedItem = null
      }
    },
    value: function (newValue) {
      this.selectedItem = this.dataSource.find(x => x[this.valueField] === newValue)
      this.setInputText(this.selectedItem.serial)
    }
  },
  computed: {
    valueSelected: function () {
      return this.selectedItem != null
        ? this.selectedItem[this.valueField]
        : null
    },
    textSelected: function () {
      return this.selectedItem != null ? this.selectedItem[this.textField] : ''
    }
  },
  data: function () {
    return {
      waterMark: 'CHỌN SERIAL/Mã T.BI',
      inputText: '',
      expanded: false,
      disabled: false,
      visible: true
    }
  },
  methods: {
    hide: function () {
      this.expanded = false
    },
    dataGrid_selectedRowChanged: function (dataItem) {
      if (dataItem != null) {
        this.selectedItem = dataItem
        let serial = this.selectedItem.serial === null ? '' : this.selectedItem.serial
        this.setInputText(serial)
      }
    },
    inputClick () {
      if (this.visible) {
        this.expanded = !this.expanded
      } else {
        this.expanded = false
      }
    },
    setVisible (value) {
      this.visible = value
    },
    setInputText (value) {
      this.inputText = value
    },
    getInputText () {
      return this.inputText
    },
    setDisable (value) {
      this.disabled = value
    },
    actionComplete (arg) {
      if (arg.requestType === 'save') {
        console.log('Du Lieu Sau Khi Sua ', arg)
        this.$emit('InsertSerial', arg)
      } else if (arg.requestType === 'filtering') {
        if (arg.action == '"filter"') {
          let filterColum = arg.currentFilterObject.field
          let value = arg.currentFilterObject.value
          if (value) {
            let ds = this.dataSource.filter(item => item[filterColum].toLowerCase().includes(value.toLowerCase()))
            this.$refs.gridInner.dataSource = [...ds]
          }
        } else {
          this.$refs.gridInner.dataSource = [...this.dataSource]
        }
      }
    }
  },
  directives: {
    ClickOutside
  }
}
</script>
<style scoped>

.custom-combobox-grid {
  position: relative;
}

.custom-combobox-grid .select-custom {
}

.custom-combobox-grid .select-custom input[type="text"] {
  cursor: default;
}

.custom-combobox-grid .grid-panel {
  position: absolute;
  right: 0;
  width: 220%;
  background-color: white;
  border: 1px solid #ddd;
  border-radius: 5px;
  z-index: 9;
  box-shadow: 0 4px 4px rgb(177 177 177 / 15%);
}

.custom-combobox-grid .grid-panel .grid {
  padding: 5px;
}
</style>
