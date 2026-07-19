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
    name: 'BssPagination1',
    props: {
        totalRows: Number,
        rowIndex: Number
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
            return Math.ceil(this.totalRows / this.perPage)
        },
        rowIndexOnPage () {
            return this.currentRow <= 0 ? 0 : this.currentRow - (this.currentPage - 1) * this.perPage
        }
    },
    methods: {
        selectDropdownItem (value) {
            this.perPage = value
        },
        pageChange (pageIndex) {
            this.currentRow = (pageIndex - 1) * this.perPage + 1
            this.$emit('pagechange', { 
                pageIndex: pageIndex, 
                pageSize: this.perPage,
                rowIndexOnPage: this.rowIndexOnPage
            })
        },
        rowChange (rowIndex) {

            let currentPage = this.currentPage

            if (rowIndex > this.currentPage * this.perPage) {
                ++currentPage
            }

            if (rowIndex <= (this.currentPage -1) * this.perPage) {
                --currentPage
            }

            if (currentPage != this.currentPage) {
                this.currentPage = currentPage
                this.$emit('pagechange', { 
                    pageIndex: this.currentPage, 
                    pageSize: this.perPage,
                    rowIndexOnPage: this.rowIndexOnPage
                })
            } else {
                this.$emit('rowchange', this.rowIndexOnPage)
            }
        }
    },
    watch: {
        totalRows (newVal) {
            this.currentRow = newVal > 0 ? 1 : 0
            this.currentPage = newVal > 0 ? 1 : 0
            this.$emit('pagechange', { 
                pageIndex: this.currentPage, 
                pageSize: this.perPage,
                rowIndexOnPage: this.rowIndexOnPage
            })
        },
        perPage (newVal) {
            if (this.currentPage > this.totalPage) {
                this.currentPage = this.totalPage
            }

            this.currentRow = (this.currentPage - 1) * newVal + 1
            
            this.$emit('pagechange', { 
                pageIndex: this.currentPage, 
                pageSize: newVal,
                rowIndexOnPage: this.rowIndexOnPage
            })
        },
        rowIndex (newVal) {
            this.currentRow = newVal <= 0 ? 0 : (this.currentPage - 1) * this.perPage + newVal
        }
    },
    mounted () {
        this.$emit('pagechange', { 
            pageIndex: this.currentPage, 
            pageSize: this.perPage,
            rowIndexOnPage: this.rowIndexOnPage
        })
    },
    data () {
        return {
            perPage: 10,
            currentPage: this.totalRows > 0 ? 1 : 0,
            currentRow: this.totalRows > 0 ? 1 : 0
        }
    }
}
</script>