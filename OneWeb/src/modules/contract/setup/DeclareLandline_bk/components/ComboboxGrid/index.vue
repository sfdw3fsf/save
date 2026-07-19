<template src="./index.template.html"></template>
<script>
import ClickOutside from "vue-click-outside";
export default {
  name: "ComboboxGrid",
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
    enabled: {
      type: Boolean,
      default: true,
    },
    enabledSelectFirstRow: {
      type: Boolean,
      default: true,
    },
    placeHolder: {
      type: String,
      default: "",
    },
    value: null,
  },
  mounted() {
    this.isMounted = true;
  },
  data: function () {
    return {
      selectedItem: {
        type: Object,
        default: null,
      },
      textSelected: "",
      selectedFromGridFlag: false,
      selectedFromManualFlag: false,
      expanded: false,
      isMounted: false
    };
  },
  watch: {
    dataSource: function (v) {
      if (v == undefined || v == null) {
        if (this.value != null) {
          this.selectedItem = v.find((x) => x[this.valueField] == this.value);
        } else {
          this.selectedItem = null;
        }
      } else {
        this.$emit("input", null);
        this.selectedItem = null;
      }
    },
    value: function (items) {
      items.forEach((v) => {
        this.doSelectValue(
          this.dataSource.find((x) => x[this.valueField] == v.NHIEMVU_ID)
        );
      })
    },
  },
  computed: {
    gridColumns: function () {
      let rs = [];
      if (!(this.columns == undefined || this.columns == null)) {
        rs = this.columns;
        let ck = false;
        for (let i = 0; i < rs.length; i++) {
          let r = rs[i];
          let keys = Object.keys(r);
          keys.forEach((key) => {
            if (r[key] == this.valueField) {
              r.isPrimaryKey = true;
              ck = true;
            }
          });
          if (ck) break;
        }
        if (!ck) {
          rs.push({
            fieldName: this.valueField,
            isPrimaryKey: true,
            visible: false,
          });
        }
      }
      return rs;
    },
    valueSelected: function () {
      return this.selectedItem != null
        ? this.selectedItem[this.valueField]
        : null;
    }
  },
  methods: {
    dataGrid_selectedRowChanged: function (dataItem) {
      this.selectedFromGridFlag = true;
      this.doSelectValue(dataItem);
      this.textSelected = ""

      let data = this.$refs.grid.getSelectedRecords()
        data.forEach((item) => {
          this.textSelected += item.NHIEMVU + ", "
        })

    },
    doSelectValue: function (dataItem) {
      this.selectedItem = dataItem;
      if (this.selectedFromGridFlag) {
        this.$emit("selectedChanged", dataItem);
      } else {
        this.$refs.grid.selectRowByRowData(this.selectedItem);
      }
    },
    hide: function () {
      this.expanded = false;
    },
    dataGrid_rowClicked: function (rowIndex) {
      //this.expanded = false;
    },
    selectedItemsChanged(items) {
      this.$emit('input', items)
    },
    input_Click: function (args) {
      if (this.enabled == true) this.expanded = !this.expanded;
      else this.expanded = false;
    },
  },
  directives: {
    ClickOutside,
  },
};
</script>
