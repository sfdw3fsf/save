<template>
    <div class="tc-pagination">
        <a href="javascript:void(0)" class="btn one-arrow-left-forward" :class="disabledPreviousPageButton" @click="pageChange(currentPage = 1)"></a>
        <a href="javascript:void(0)" class="btn fa fa-angle-double-left" :class="disabledPreviousPageButton" @click="pageChange(--currentPage)"></a>
        <a href="javascript:void(0)" class="btn fa fa-angle-left" :class="disabledPreviousRowButton" @click="rowChange(--currentRow)"></a>
        <span class="text">Bản ghi</span>
        <div ref="dropdown" class="dropdown inline" style="position: relative">
            <div class="fw6 input-number" data-toggle="dropdown">
                <span class="text-main">{{currentRow}}</span>/{{totalRows}}
            </div>
            <div ref="dropdown-menu" class="dropdown-menu" style="margin: .125rem 0 0">
                <a href="javascript:void(0)" class="dropdown-item" @click="selectDropdownItem(10)">10</a>
                <a href="javascript:void(0)" class="dropdown-item" @click="selectDropdownItem(20)">20</a>
            </div>
        </div>
        <a href="javascript:void(0)" class="btn fa fa-angle-right" :class="disabledNextRowButton" @click="rowChange(++currentRow)"></a>
        <a href="javascript:void(0)" class="btn fa fa-angle-double-right" :class="disabledNextPageButton" @click="pageChange(++currentPage)"></a>
        <a href="javascript:void(0)" class="btn one-arrow-right-forward" :class="disabledNextPageButton" @click="pageChange(currentPage = totalPage)"></a>
    </div>
</template>
<script>
export default {
    name: 'BssPagination2',
    props: {
        totalRows: Number,
        selectedRowIndexOnPage: Number,
        selectedRowIndexOnData: Number
    },
    computed: {
        disabledPreviousPageButton () {
            return this.currentPage > 1 ? '' : 'disabled'
        },
        disabledNextPageButton () {
            return this.currentPage < this.totalPage ? '' : 'disabled'
        },
        disabledPreviousRowButton () {
            return this.currentRow > 1 ? '' : 'disabled'
        },
        disabledNextRowButton () {
            return this.currentRow < this.totalRows ? '' : 'disabled'
        },
        totalPage () {
            return Math.ceil(this.totalRows / this.pageSize)
        },
        rowIndexOnPage () {
            return this.currentRow <= 0 ? 0 : this.currentRow - this.pageSize * (this.currentPage - 1)
        },
        rowIndexOnData () {
            return this.currentRow <= 0 ? -1 : this.currentRow - 1
        }
    },
    methods: {
        selectDropdownItem (value) {
            this.pageSize = value
        },
        pageChange (pageIndex) {
            this.currentRow = (pageIndex - 1) * this.pageSize + 1
            this.$emit('pagechange', { 
                pageIndex: pageIndex, 
                pageSize: this.pageSize,
                rowIndexOnPage: this.rowIndexOnPage,
                rowIndexOnData: this.rowIndexOnData
            })
        },
        rowChange (rowIndex) {
            
            let currentPage = this.currentPage

            if (rowIndex > this.currentPage * this.pageSize) {
                ++currentPage
            }

            if (rowIndex <= (this.currentPage - 1) * this.pageSize) {
                --currentPage
            }

            if (currentPage != this.currentPage) {
                this.currentPage = currentPage
                this.$emit('pagechange', { 
                    pageIndex: this.currentPage, 
                    pageSize: this.pageSize,
                    rowIndexOnPage: this.rowIndexOnPage,
                    rowIndexOnData: this.rowIndexOnData
                })
            } else {
                this.$emit('rowchange', {
                    pageIndex: this.currentPage, 
                    pageSize: this.pageSize,
                    rowIndexOnPage: this.rowIndexOnPage,
                    rowIndexOnData: this.rowIndexOnData
                })
            }
        }
    },
    watch: {
        pageSize (newVal) {

            if (this.currentPage > this.totalPage) {
                this.currentPage = this.totalPage
            }

            this.currentRow = (this.currentPage - 1) * newVal + 1
            
            this.$emit('pagechange', { 
                pageIndex: this.currentPage, 
                pageSize: newVal,
                rowIndexOnPage: this.rowIndexOnPage,
                rowIndexOnData: this.rowIndexOnData
            })
        },
        selectedRowIndexOnPage (newVal) {

            this.currentRow = newVal <= 0 ? 0 : (this.currentPage - 1) * this.pageSize + newVal

            this.$emit('rowchange', { 
                pageIndex: this.currentPage, 
                pageSize: this.pageSize,
                rowIndexOnPage: this.rowIndexOnPage,
                rowIndexOnData: this.rowIndexOnData
            })
        }
    },
    mounted () {

        let totalRows = this.totalRows
        let selectedRowIndexOnData = !this.selectedRowIndexOnData ? 0 : this.selectedRowIndexOnData

        if (totalRows == 0) {
            selectedRowIndexOnData = -1
        }

        if (totalRows > 0) {
            
            if (selectedRowIndexOnData < 0) {
                selectedRowIndexOnData = 0
            }
            
            if (selectedRowIndexOnData >= totalRows) {
                selectedRowIndexOnData = totalRows - 1
            }
        }

        this.currentRow = selectedRowIndexOnData + 1
        this.currentPage = Math.ceil(this.currentRow / this.pageSize)

        this.$emit('pagechange', { 
            pageIndex: this.currentPage, 
            pageSize: this.pageSize,
            rowIndexOnPage: this.rowIndexOnPage,
            rowIndexOnData: this.rowIndexOnData
        })
    },
    data () {
        return {
            pageSize: 10,
            currentPage: 0,
            currentRow: 0
        }
    }
}
</script>