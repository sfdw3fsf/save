<template src="./CustomDataGrid.html" />
<style>
.table-result tbody tr:nth-child(2n+2) {
    background-color: unset !important;
}
</style>
<script>
import Vue from 'vue'
export default {
  name: "CustomDataGrid",
  props: {
    columns: Array,
    dataSource: Array,
    dataKeyNames: Array,
    dataKeyField: String,
    showFilter: {
      type: Boolean,
      default: true
    },
    showAddNewRow: {
      type: Boolean,
      default: false
    },
    showColumnCheckbox: {
      type: Boolean,
      default: false
    },
    showPagination: {
      type: Boolean,
      default: true
    },
    defaultColumnCheckboxChecked: {
      type: Boolean,
      default: false
    },
    pageIndex: {
      type: Number,
      default: 0
    },
    pageSize: {
      type: Number,
      default: 10
    },
    selectedRow: {
      type: Number,
      default: 0
    },
    textBoldWhenSelected: {
      type: Boolean,
      default: false
    }
  },
  data: function() {
    return {
      currentPageSize: this.showPagination ? this.pageSize : 100000,
      currentPageIndex: this.pageIndex,
      currentSelectedRow: this.selectedRow,
      filterValues: [],
      selectedValues: [],
      selectAllItem: this.defaultColumnCheckboxChecked,
      newRecord: {},
      sortingInfo: {
        fieldName: "",
        direction: "asc",
      },
    };
  },
  watch: {
    dataSource: function(newValue) {
      this.setCurrentSelectedRow(this.currentSelectedRow);
      this.selectedValues = [];
      if (this.defaultColumnCheckboxChecked) {
        if (!(this.dataSource == null || this.dataSource.length == 0)) {
          this.dataSource.forEach(dataItem => {
            this.selectedValues.push(dataItem[this.dataKeyField]);
          });
        }
      }
    },
    selectedValues: function(newValue) {
      this.$emit("selectedItemsChanged", newValue);
    },
    selectAllItem: function(newValue) {
      this.selectedValues.splice(0);
      if (newValue) {
        this.dataItems.forEach(dataItem => {
          this.selectedValues.push(dataItem[this.dataKeyField]);
        });
      }
    }
  },
  computed: {
    counteSelectedRows: function() {
      if (this.selectedValues.length == 0) 
        return ''
      return this.selectedValues.length + '/' + this.dataSource.length
    },
    rowCount: function() {
      if (this.dataSource != null) return this.dataSource.length;
      return 0;
    },
    pageCount: function() {
      if (this.rowCount > 0)
        return Math.ceil(this.rowCount / this.currentPageSize);
      return 0;
    },
    pageRowCount: function() {
      if (this.dataItems != null) return this.dataItems.length;
      return 0;
    },
    dataItems: function() {
      if (this.dataSource != null) {
        let ds = this.dataSourceFiltered
        return ds.slice(
          this.currentPageIndex * this.currentPageSize,
          this.currentPageIndex * this.currentPageSize + this.currentPageSize
        );
      }
        
      return null;
    },
    dataSourceFiltered: function () {
      if (this.dataSource != null)
        return this.dataSource.filter(this.filterData).sort(this.sortData);
      return null;
    },
    getSelectedRow: function() {
      return this.currentSelectedRow > -1
        ? (this.dataSource.length == 0 ? 0
          : this.currentPageIndex * this.currentPageSize +
            this.currentSelectedRow +
            1)
        : 0;
    },
    getGroupedColumns: function() {
      if (this.columns != null) {
        return this.columns.filter(function(column) {
          return column.isGroupedColumn;
        });
      }
      return null;
    },
    getColumnsCount: function() {
      var count = 1; //track index
      if (this.columns != null) count += this.columns.length;
      if (this.showColumnCheckbox) count += 1;
      if (this.getGroupedColumns != null)
        count = count - this.getGroupedColumns.length + 1;
      return count;
    },
    getGroupedValues: function() {
      var values = null;
      if (
        !(this.getGroupedColumns == null || this.getGroupedColumns.length == 0)
      ) {
        var values = new Array();
        this.getGroupedColumns.forEach(column => {
          if (this.dataItems != null) {
            this.dataItems.forEach(dataItem => {
              if (
                values.findIndex(
                  item =>
                    item.field == column.fieldName &&
                    item.value == dataItem[column.fieldName]
                ) < 0
              )
                values.push({
                  field: column.fieldName,
                  text: column.headerText,
                  value: dataItem[column.fieldName],
                  key: dataItem[this.dataKeyField],
                  isShow: false
                });
            });
          }
        });
        return values;
      }
      return null;
    }
  },
  methods: {
    sortData: function (a, b) {
      if (this.sortingInfo.fieldName != "") {
        let field = this.sortingInfo.fieldName;
        // if (this.sortingInfo.direction == "asc")
        //   return a[field] < b[field] ? -1 : a[field] > b[field] ? 1 : 0;
        // else return a[field] < b[field] ? 1 : a[field] > b[field] ? -1 : 0;
        if (this.sortingInfo.direction == "asc")
          return a[field] < b[field] ? -1 : 1;
        else return a[field] < b[field] ? 1 : -1;
      }
      return 0;
    },
    filterData: function(dataItem) {
      let result = true;
      this.columns.forEach(column => {
        if (column.allowFiltering) {
          if (this.filterValues[column.fieldName] != null) {
            if (result) {
              if (dataItem[column.fieldName] != null)
                result = dataItem[column.fieldName]
                  .toString()
                  .toLowerCase()
                  .includes(this.filterValues[column.fieldName].trim().toLowerCase());
              else {
                if (this.filterValues[column.fieldName].toString().trim() == "")
                  result = true;
                else result = false;
              }
            }
          }

          // if(dataItem[column.fieldName] != null)
          //   {
          //       if(this.filterValues[column.fieldName] != null)
          //           if(result)
          //               result = dataItem[column.fieldName].toString().toLowerCase().includes(this.filterValues[column.fieldName].toLowerCase());
          //   }
        }
      });
      return result;
    },
    filterChange: function() {
      if (this.currentSelectedRow > -1) this.setCurrentSelectedRow(-1);
    },
    rowClick: function(rowIndex) {
      this.setCurrentSelectedRow(rowIndex);
      this.$emit("rowClicked", rowIndex);
    },
    doSelectNearby: function(next) {
      this.setCurrentSelectedRow(
        next ? this.currentSelectedRow + 1 : this.currentSelectedRow - 1
      );
    },
    getCurrentSelectedRow: function() {
      return this.currentSelectedRow;
    },
    setCurrentSelectedRow: function(value) {
      if (this.currentSelectedRow == value) {
        if (value > this.pageRowCount - 1) value = this.pageRowCount - 1;
      } else {
        if (value > this.pageRowCount - 1 || value < 0) {
          if (this.currentSelectedRow == -1) {
            if (value > this.currentSelectedRow) value = 0;
            else value = this.pageRowCount - 1;
          } else value = -1;
        }
      }
      this.currentSelectedRow = value;
      this.$emit(
        "selectedRowChanged",
        !(this.dataItems == null || this.dataItems.length == 0)
          ? this.currentSelectedRow > -1
            ? this.dataItems[this.currentSelectedRow]
            : null
          : null
      );
    },
    setPageSize: function(value) {
      this.currentPageSize = value;
      if (this.currentSelectedRow > -1)
        this.setCurrentSelectedRow(this.currentSelectedRow);
    },
    nextPage: function() {
      this.currentPageIndex += 1;
      if (this.currentSelectedRow > -1)
        this.setCurrentSelectedRow(this.currentSelectedRow);
    },
    lastPage: function() {
      this.currentPageIndex = this.pageCount - 1;
      if (this.currentSelectedRow > -1)
        this.setCurrentSelectedRow(this.currentSelectedRow);
    },
    previousPage: function() {
      this.currentPageIndex -= 1;
      if (this.currentSelectedRow > -1)
        this.setCurrentSelectedRow(this.currentSelectedRow);
    },
    firstPage: function() {
      this.currentPageIndex = 0;
      if (this.currentSelectedRow > -1)
        this.setCurrentSelectedRow(this.currentSelectedRow);
    },
    doSelectItem: function(dataItem) {
      this.selectedValues.push(dataItem);
    },
    sumField: function(field) {
      return this.dataItems.reduce( function(a, b){
        return a + b[field];
    }, 0);
    },
    toggle: function (groupedValue) {
        let ds = []
        Vue.set(groupedValue, 'isShow', !groupedValue.isShow)
        this.dataItems.forEach((element, index) => {
            if (element[groupedValue.field] == groupedValue.value) {
              console.log(groupedValue.value)
                if (element.hasOwnProperty("isDisplay")) {
                  console.log(index, 'true')
                  let dp = element.isDisplay
                    Vue.set(element, 'isDisplay', !dp)
                    //element.isDisplay = !element.isDisplay
                }
                else {
                  console.log(index, 'false')
                    Vue.set(element, 'isDisplay', true)
                    //element.isDisplay = true
                }
            }
            ds.push(element)
        })
        
        // this.dataItems = ds
        console.log('toggle', groupedValue)
    },
    columnButtonSort_Click: function (fieldName, event) {
      let button = $(event.target);
      button
        .parents("tr")
        .find("th div.cell-header .btn-sort")
        .attr("class", "btn-sort fa fa-sort");
      if (this.sortingInfo.fieldName == fieldName) {
        if (this.sortingInfo.direction == "asc")
          this.sortingInfo.direction = "desc";
        else this.sortingInfo.direction = "asc";
      } else {
        this.sortingInfo.fieldName = fieldName;
        this.sortingInfo.direction = "asc";
      }
      button.attr(
        "class",
        "btn-sort btn-sorted fa fa-caret-" +
          (this.sortingInfo.direction == "asc" ? "up" : "down")
      );
    },
    deleteItem: function(dataItem) {
      this.$emit('deleteItem', dataItem)
    },
    addItem: function() {
      //this.newRecord.ID = this.newRecord.ID ? this.newRecord.ID : '' + this.dataSource.length + 1
      this.$emit("addItem", this.newRecord)
      this.newRecord = {}
    }
  }
};
</script>
