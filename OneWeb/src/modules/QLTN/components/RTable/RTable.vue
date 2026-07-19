<template src="./RTable.html"></template>
<script>
import { searchArray } from '@/utils/util'
export default {
    name: 'RTable',
    props: {
        fields: Array,
        items: Array,
        columnFilter: {
            type: Boolean,
            default: false
        },
        checkSelect: {
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
        tableHeight: {
            type: Number,
            default: 0
        },
        isDelete: {
            type: Boolean,
            default: false
        },
        isRefresh: {
            type: Boolean,
            default: false
        },
        deleteToolTip: {
            type: String,
            default: ''
        },
        refreshToolTip: {
            type: String,
            default: ''
        }
    },
    computed: {
        rawItems() {
            if (this.checkSelect) {
                this.items.forEach(item => {
                    item.isChecked = false;
                });
                this.checkAll = false;
            }
            // return this.items;
            if (this.items != null)
                return this.items                
                .slice(
                    this.currentPageIndex * this.currentPageSize,
                    this.currentPageIndex * this.currentPageSize + this.currentPageSize
                );
            return null;
        },

        rowCount: function() {
            if (this.items != null) return this.items.length;
            return 0;
        },

        pageCount: function() {
            if (this.rowCount > 0) return Math.ceil(this.rowCount / this.currentPageSize);
            return 0;
        },

        getSelectedRow: function() {
            if (this.searchResults.length > 0)
                return this.index > -1
                ? this.currentPageIndex * this.currentPageSize + this.index + 1
                : 0;
            else return 0;
        },
    },
    data () {
        return {
            checkAll: false,
            index: null,
            currentPageSize: this.pageSize,
            currentPageIndex: this.pageIndex,
            searchResults: [],
            checkedItems: [],
            height: this.tableHeight !== 0 ? this.tableHeight + 'px' : ''
        }
    },
    methods: {
        onChangeSearch(text, key) {
            let searchData = {
                key: key || '',
                value: text.trim() || ''
            };
            
            this.searchResults = searchArray(this.rawItems, searchData)
            if(this.searchResults.length > 0)
                this.index = 0
            else
                this.index = null
        },

        //One row has been selected:
        onSelectRow(item, rowIndex) {
            this.index = rowIndex;
            // this.$emit('rowSelected', item);
        },

        //Checkbox All has been checked:
        onCheckAll() {            
            this.rawItems.forEach(item => {
                item.isChecked = this.checkAll ? true : false;
            });
            
            if (this.checkAll) {                
                this.checkedItems = this.rawItems;
            } else {
                this.checkedItems = [];
            }
        },

        //Checkbox of a special row has been checked:
        onCheckRow(item) {            
            if (item.isChecked) {                
                // delete item.isChecked;
                this.checkedItems.push(item);
            } else {
                let index = this.checkedItems.indexOf(item);
                this.checkedItems.splice(index, 1);
            }            
        },

        onDeleteRow(item) {            
            this.$emit('deleteItem', item);
        },

        onRefresh() {
            this.$emit('refreshList');
        },

        setCurrentSelectedRow(value) {
            if (this.index === value) {
                if (value > this.pageRowCount - 1) value = this.pageRowCount - 1;
            } else {
                if (value > this.pageRowCount - 1 || value < 0) {
                    if (this.index == -1) {
                        if (value > this.index) value = 0;
                        else value = this.pageRowCount - 1;
                    } else value = -1;
                }
            }
            this.index = value;
            this.$emit(
                "rowSelected",
                !(this.searchResults === null || this.searchResults.length === 0)
                ? this.index > -1
                    ? this.searchResults[this.index]
                    : null
                : null
            );
            console.log(this.searchResults[this.index])
        },

        firstPage() {
            this.currentPageIndex = 0;
            if (this.index > -1)
                this.setCurrentSelectedRow(this.index);
        },

        lastPage() {
            this.currentPageIndex = this.pageCount - 1;
            if (this.index > -1)
                this.setCurrentSelectedRow(this.index);
        },

        previousPage() {
            this.currentPageIndex -= 1;
            if (this.index > -1)
                this.setCurrentSelectedRow(this.index);
        },

        nextPage() {
            this.currentPageIndex += 1;
            if (this.index > -1)
                this.setCurrentSelectedRow(this.index);
        },

        doSelectNearby(next) {
            if (next) {
                if (this.index < this.rowCount - 1) {
                    this.index += 1;
                    this.onSelectRow(this.searchResults[this.index], this.index);
                } 
            } else {
                if (this.index > 0) {
                    this.index -= 1;
                    this.onSelectRow(this.searchResults[this.index], this.index);
                }
            }

            // this.setCurrentSelectedRow(next ? this.index + 1 : this.index - 1);
        },

        setPageSize(value) {
            this.currentPageSize = value;
            if (this.index > -1)
                this.setCurrentSelectedRow(this.index);
        },
    },
    watch: {
        rawItems: {
            deep: true,
            handler (newVal) {
                let $this = this
                this.searchResults = newVal;
                if($this.searchResults.length > 0)
                {
                    $this.index = 0;

                    //Filter lại checkedItems array trong trường hợp datasource của bảng thay đổi:
                    if ($this.checkedItems.length > 0) {                        
                        $this.checkedItems.forEach((item, index) => {
                            let i = newVal.indexOf(item);
                            if (i === -1) {                                
                                $this.checkedItems.splice(index, 1);
                            };                            
                        });
                    }
                }else{
                    $this.index = null;
                    $this.checkedItems = [];
                }
            }
        },
        
        checkedItems: {
            handler (newVal) {
                this.$emit('itemsChecked', newVal);
            }
        },
        
        index: {
            handler (newVal) {                
                this.$emit('rowSelected', this.searchResults[newVal]);
            }
        }
    }
}
</script>