<template src="./index.template.html" />
<style scoped>
.combobox-grid {
    outline: 0;
}

.combobox-grid .select-custom input[type="text"]:disabled {
    background-color: #e9ecef !important;
}

.combobox-grid:focus input {
    border-color: #80bdff;
    -webkit-box-shadow: 0 0 0 3px rgb(0 123 255 / 25%);
    box-shadow: 0 0 0 3px rgb(0 123 255 / 25%);
}
</style>
<script>
import ClickOutside from "vue-click-outside";
export default {
    name: "ComboboxGrid",
    props: {
        columns: {
            type: Array,
            default: []
        },
        dataSource: {
            type: Array,
            default: []
        },
        textField: {
            type: String,
            default: ""
        },
        valueField: {
            type: String,
            default: ""
        },
        showFilter: {
            type: Boolean,
            default: true
        },
        disabled: {
            type: Boolean,
            default: false
        },
        rowSelected: {
            default: 0
        },
        gridPanelDataHeight: {
            type: String,
            default: "300px"
        },
        enabledSelectFirstRow: {
            type: Boolean,
            default: true
        },
        placeholder: {
            type: String,
            default: "Chọn"
        },
        allowPaging: {
            type: Boolean,
            default: false
        },
        value: null
    },
    data: function() {
        return {
            selectedItem: {
                type: Object,
                default: null
            },
            selectedFromGridFlag: false,
            selectedFromManualFlag: false,
            expanded: false
        };
    },
    watch: {
        dataSource: function(v) {
            if (v == undefined || v == null) {
                if (this.value != null) {
                    this.selectedItem = v.find(
                        x => x[this.valueField] == this.value
                    );
                } else {
                    this.selectedItem = null;
                }
            } else {
                this.$emit("input", null);
                this.selectedItem = null;
            }
        },
        value: function(v) {
            if (!(this.dataSource == undefined || this.dataSource == null)) {
                if (!this.selectedFromGridFlag) {
                    this.doSelectValue(
                        this.dataSource.find(x => x[this.valueField] == v)
                    );
                }
            } else {
                this.selectedItem = null;
            }
            this.selectedFromGridFlag = false;
        }
    },
    computed: {
        gridColumns: function() {
            let rs = [];
            if (!(this.columns == undefined || this.columns == null)) {
                rs = this.columns;
                let ck = false;
                for (let i = 0; i < rs.length; i++) {
                    let r = rs[i];
                    let keys = Object.keys(r);
                    keys.forEach(key => {
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
                        visible: false
                    });
                }
            }
            return rs;
        },
        valueSelected: function() {
            return this.selectedItem != null
                ? this.selectedItem[this.valueField]
                : null;
        },
        textSelected: function() {
            return this.selectedItem != null
                ? this.selectedItem[this.textField]
                : this.placeholder;
        }
    },
    methods: {
        dataGrid_selectedRowChanged: function(dataItem) {
            if (!this.selectedFromManualFlag) {
                this.selectedFromGridFlag = true;
                this.doSelectValue(dataItem);
            } else {
                this.selectedFromManualFlag = false;
            }
        },
        doSelectValue: function(dataItem) {
            this.selectedItem = dataItem;
            if (this.selectedFromGridFlag) {
                this.$emit("input",this.selectedItem ? this.selectedItem[this.valueField] : "");
                this.$emit("selectedChanged", dataItem);
            } else {
                this.selectedFromManualFlag = true;
                this.$refs.grid.selectRowByRowData(this.selectedItem);
            }
        },
        hide: function() {
            this.expanded = false;
        },
        dataGrid_rowClicked: function(rowIndex) {
            this.expanded = false;
        },
        dataGrid_dataBound: function(args) {
            this.$emit("dataGridDataBound", args);
        }
    },
    directives: {
        ClickOutside
    }
};
</script>
