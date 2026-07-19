<template src="./index.template.html"></template>
<script>
import ClickOutside from "vue-click-outside";
export default {
  name: "ComboboxGridCustom",
  props: {
    columns: {
      type: Array,
      default: [],
    },
    dataSource: {
      type: Array,
      default: [],
    },
    textField: {
      type: String,
      default: "",
    },
    valueField: {
      type: String,
      default: "",
    },
    showFilter: {
      type: Boolean,
      default: true,
    },
    gridPanelDataHeight: {
      type: String,
      default: "300px",
    },
    enabledSelectFirstRow: {
      type: Boolean,
      default: true,
    },
    value: null,
  },
  data: function () {
    return {
      selectedItem: {
        type: Object,
        default: null,
      },
      expanded: false,
    };
  },
  watch: {
    dataSource: function (value) {
      if (value == undefined || value == null) {
        if(this.value != null){
          this.selectedItem = value.find(x => x[this.valueField] == this.value);
        }
        else {
          this.selectedItem = null;
        }
      } else {
        this.$emit('input', null);
        this.selectedItem = null;
      }
    },
    value: function (newValue) {
      let val=newValue
      if (newValue!=null &&newValue!='' ) val=newValue.toString()
      //this.selectedItem = this.dataSource.find(x => x[this.valueField].toString() === newValue.toString())
      this.selectedItem = this.dataSource.find(x => x[this.valueField].toString() === val)
      //let index = this.dataSource.findIndex(x => x[this.valueField].toString() === newValue.toString())
      //this.$refs.gridData.selectRow(index, true)
      //if (index > -1) this.$refs.gridData.selectRow(index)
    }
  },
  computed: {
    valueSelected: function () {
      return this.selectedItem != null
        ? this.selectedItem[this.valueField]
        : null;
    },
    textSelected: function () {
      return this.selectedItem != null ? this.selectedItem[this.textField] : "";
    },
  },
  methods: {
    dataGrid_selectedRowChanged: function (dataItem) {
     if (dataItem != null) {
       this.selectedItem = dataItem;
       this.$emit('input', this.selectedItem[this.valueField]);
       this.$emit("selectedChanged", dataItem);
     }
    },
    hide: function () {
      this.expanded = false;
    },
    dataGrid_rowClicked: function (rowIndex) {
      this.expanded = false;
    }
  },
  directives: {
    ClickOutside,
  },
};
</script>
