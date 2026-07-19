<template src="./index.html" />
<script>
export default {
  name: "TableC",
  props: {
    columns: Array,
    dataSource: Array,
    dataKeyNames: Array,
    dataKeyField: String,
    showFilter: {
      type: Boolean,
      default: true
    },
    showColumnCheckbox: {
      type: Boolean,
      default: false
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
    disabledCheckAll: {
      type: Boolean,
      default: false
    },
    showComponent:{
      type: Boolean,
      default: false
    }
  },
  data: function() {
    return {
      currentPageSize: this.pageSize,
      currentPageIndex: this.pageIndex,
      currentSelectedRow: this.selectedRow,
      filterValues: [],
      selectedValues: [],
      selectAllItem: this.defaultColumnCheckboxChecked
    };
  },
  watch: {
    dataSource: function(newVal, oldVal) {
        //start compare
        if (newVal.length > oldVal.length) {
		    const diff = newVal.filter(
			    (itemNewVal) =>
				    !oldVal.some((itemOldVal) => itemNewVal[this.dataKeyField] === itemOldVal[this.dataKeyField])
		    );
		    this.currentSelectedRow = newVal.indexOf(diff[0]);
		    console.log("selected row index", newVal.indexOf(diff[0]));
	    } else if (newVal.length < oldVal.length) {
		    const diff = oldVal.filter(
			    (itemOldVal) =>
				    !newVal.some((itemNewVal) => itemOldVal[this.dataKeyField] === itemNewVal[this.dataKeyField])
		    );
		    console.log("selected row index", newVal.indexOf(diff[0]));
		    if (newVal.length === oldVal.indexOf(diff[0])) {
			    this.currentSelectedRow = oldVal.indexOf(diff[0]) - 1;
		    } else {
			    this.currentSelectedRow = oldVal.indexOf(diff[0]);
		    }
	    }
        //end compare
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
      if (this.dataSource != null)
        return this.dataSource
          .filter(this.filterData)
          .slice(
            this.currentPageIndex * this.currentPageSize,
            this.currentPageIndex * this.currentPageSize + this.currentPageSize
          );
      return null;
    },
    getSelectedRow: function() {
      return this.currentSelectedRow > -1
        ? this.currentPageIndex * this.currentPageSize +
            this.currentSelectedRow +
            1
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
                  value: dataItem[column.fieldName]
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
    filterData: function(dataItem) {
      let result = true;
      this.columns.forEach(column => {
        if(column.convertData) {
            column.convertData(dataItem)
        }
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
    rowEventClick:function (index, dataItem) {
      this.$emit("rowEventClicked",{...dataItem,index})
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
          : null, this.currentSelectedRow
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
    sumField: function(field,total) {
      if (total) {
        return this.dataItems.length
      }
      return this.dataItems.reduce( function(a, b){
        return a + b[field];
    }, 0);
    }
  }
};
</script>
