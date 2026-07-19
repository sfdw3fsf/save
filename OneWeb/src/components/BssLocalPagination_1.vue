<template>
    <div class="tc-pagination">
        <button class="btn one-arrow-left-forward" :disabled="disabledPreviousPageButton" @click="pageChange(currentPage = 1)"></button>
        <button class="btn fa fa-angle-double-left" :disabled="disabledPreviousPageButton" @click="pageChange(--currentPage)"></button>
        <button class="btn fa fa-angle-left" :disabled="disabledPreviousRowButton" @click="rowChange(--currentRow)"></button>
        <span class="text">Bản ghi</span>
        <div ref="dropdown" class="dropdown inline" style="position: relative" :data-bss_pagination_identify="identify" :class="showDropdown">
            <div class="fw6 input-number" :data-bss_pagination_identify="identify" data-toggle="dropdown" @click="toggleDropdown">
                <span class="text-main" :data-bss_pagination_identify="identify">{{currentRow}}</span>/{{totalRows}}
            </div>
            <div ref="dropdown-menu" class="dropdown-menu" style="margin: .125rem 0 0" :data-bss_pagination_identify="identify" :class="showDropdown">
                <a href="javascript:void(0)" :data-bss_pagination_identify="identify" class="dropdown-item" @click="selectDropdownItem(10)">10</a>
                <a href="javascript:void(0)" :data-bss_pagination_identify="identify" class="dropdown-item" @click="selectDropdownItem(20)">20</a>
            </div>
        </div>
        <button class="btn fa fa-angle-right" :disabled="disabledNextRowButton" @click="rowChange(++currentRow)"></button>
        <button class="btn fa fa-angle-double-right" :disabled="disabledNextPageButton" @click="pageChange(++currentPage)"></button>
        <button class="btn one-arrow-right-forward" :disabled="disabledNextPageButton" @click="pageChange(currentPage = totalPage)"></button>
    </div>
</template>
<script>
import Popper from 'popper.js'

export default {
    name: 'BssLocalPagination1',
    props: {
        datasource: Array,
        selectedRowIndex: Number
    },
    computed: {
        totalRows () {
            return this.datasource.length
        },
        totalPage () {
            return Math.ceil(this.totalRows / this.perPage)
        },
        disabledPreviousPageButton () {
            return this.currentPage > 1 ? false : true
        },
        disabledNextPageButton () {
            return this.currentPage < this.totalPage ? false : true
        },
        disabledPreviousRowButton () {
            return this.currentRow > 1 ? false : true
        },
        disabledNextRowButton () {
            return this.currentRow < this.totalRows ? false : true
        }
    },
    methods: {
        selectDropdownItem (value) {
            this.toggleDropdown()
            this.perPage = value
        },
        toggleDropdown () {
            this.showDropdown = !this.showDropdown ? 'show' : ''

            if (this.showDropdown) {
                this.popper = new Popper(this.$refs["dropdown"], this.$refs["dropdown-menu"], {
                    placement: 'bottom-start',
                    modifiers: [{
                        name: 'offset',
                        options: {
                            offset: [0, 50]
                        },
                    }]
                })
            } else {
                this.popper = null
            }
        },
        considerHideDropdown (event) {
            if (event.target.getAttribute('data-bss_pagination_identify') != this.identify) {
                this.showDropdown = ''
                this.style = ''
            }
        },
        pageChange (pageIndex) {
            this.currentRow = (pageIndex -1) * this.perPage + 1
            let from = (pageIndex - 1) * this.perPage
            let to = pageIndex == this.totalPage ? this.totalRows : pageIndex * this.perPage
            let pageData = this.datasource.slice(from, to)
            pageData.forEach((item, index) => item.rowIndex = this.currentRow + index)

            this.$emit('change', {
                pageData: pageData,
                selectedRowData: {...this.datasource[this.currentRow-1]}
            })
        },
        rowChange (rowIndex) {

            if (rowIndex > this.currentPage * this.perPage) {
                ++this.currentPage
            }

            if (rowIndex <= (this.currentPage -1) * this.perPage) {
                --this.currentPage
            }

            let from = (this.currentPage - 1) * this.perPage
            let to = this.currentPage == this.totalPage ? this.totalRows : this.currentPage * this.perPage
            let pageData = this.datasource.slice(from, to)
            pageData.forEach((item, index) => item.rowIndex = this.currentRow + index)

            this.$emit('change', {
                pageData: pageData,
                selectedRowData: {...this.datasource[rowIndex-1]}
            })
        }
    },
    watch: {
        datasource (newVal) {
            this.currentRow = newVal.length > 0 ? 1 : 0
            this.currentPage = newVal.length > 0 ? 1 : 0
            let pageData = this.datasource.slice(0, this.perPage)
            pageData.forEach((item, index) => item.rowIndex = this.currentRow + index)

            this.$emit('change', {
                pageData: pageData,
                selectedRowData: newVal.length > 0 ? {...this.datasource[0]} : null
            })
        },
        perPage (newVal) {

            if (this.currentPage > this.totalPage) {
                this.currentPage = this.totalPage
            }

            this.currentRow = (this.currentPage - 1) * newVal + 1
            let from = (this.currentPage - 1) * newVal
            let to = this.currentPage == this.totalPage ? this.totalRows : this.currentPage * newVal
            let pageData = this.datasource.slice(from, to)
            pageData.forEach((item, index) => item.rowIndex = this.currentRow + index)
            
            this.$emit('change', {
                pageData: pageData,
                selectedRowData: {...this.datasource[this.currentRow - 1]}
            })
        },
        selectedRowIndex (newVal) {
            this.currentRow = newVal
        }
    },
    mounted () {
        document.addEventListener('click', this.considerHideDropdown)

        if (this.currentPage > 0) {
            let from = (this.currentPage - 1) * this.perPage
            let to = this.currentPage == this.totalPage ? this.totalRows : this.currentPage * this.perPage
            let pageData = this.datasource.slice(from, to)
            pageData.forEach((item, index) => item.rowIndex = this.currentRow + index)
            
            this.$emit('change', { 
                pageData: pageData, 
                selectedRowData: {...this.datasource[this.currentRow - 1]}
            })
        } else {
            this.$emit('change', { 
                pageData: [], 
                selectedRowData: null
            })
        }
    },
    data () {

        let defaultPerPage = 10

        return {
            identify: Date.now() + '-' + Math.floor(Math.random() * 1000) + '-' + Math.floor(Math.random() * 1000),
            perPage: defaultPerPage,
            currentPage: this.datasource.length > 0 ? 1 : 0,
            currentRow: this.datasource.length > 0 ? 1 : 0,
            showDropdown: '',
            style: '',
            popper: null,
        }
    }
}
</script>