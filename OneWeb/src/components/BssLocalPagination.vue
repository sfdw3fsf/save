<template>
    <div class="tc-pagination">
        <a href="javascript:void(0)" class="btn one-arrow-left-forward" :class="disabledPreviousButton" @click="pageChange(currentPage = 1)"></a>
        <a href="javascript:void(0)" class="btn fa fa-angle-double-left" :class="disabledDoublePreviousButton" @click="pageChange(currentPage = currentPage - 2)"></a>
        <a href="javascript:void(0)" class="btn fa fa-angle-left" :class="disabledPreviousButton" @click="pageChange(--currentPage)"></a>
        <span class="text">Bản ghi</span>
        <div ref="dropdown" class="dropdown inline" style="position: relative" :data-bss_pagination_identify="identify" :class="showDropdown">
            <div class="fw6 input-number" :data-bss_pagination_identify="identify" data-toggle="dropdown" @click="toggleDropdown">
                <span class="text-main" :data-bss_pagination_identify="identify">{{textMain}}</span>/{{totalRows}}
            </div>
            <div ref="dropdown-menu" class="dropdown-menu" style="margin: .125rem 0 0" :data-bss_pagination_identify="identify" :class="showDropdown">
                <a href="javascript:void(0)" :data-bss_pagination_identify="identify" class="dropdown-item" @click="selectDropdownItem(10)">10</a>
                <a href="javascript:void(0)" :data-bss_pagination_identify="identify" class="dropdown-item" @click="selectDropdownItem(20)">20</a>
            </div>
        </div>
        <a href="javascript:void(0)" class="btn fa fa-angle-right" :class="disabledNextButton" @click="pageChange(++currentPage)"></a>
        <a href="javascript:void(0)" class="btn fa fa-angle-double-right" :class="disabledDoubleNextButton" @click="pageChange(currentPage = currentPage + 2)"></a>
        <a href="javascript:void(0)" class="btn one-arrow-right-forward" :class="disabledNextButton" @click="pageChange(currentPage = totalPage)"></a>
    </div>
</template>
<script>
import Popper from 'popper.js'

export default {
    name: 'BssLocalPagination',
    props: {
        datasource: Array
    },
    computed: {
        totalRows () {
            return this.datasource.length
        },
        disabledPreviousButton () {
            return this.currentPage > 1 ? '' : 'disabled'
        },
        disabledDoublePreviousButton () {
            return this.currentPage > 2 ? '' : 'disabled'
        },
        disabledNextButton () {
            return this.currentPage < this.totalPage ? '' : 'disabled'
        },
        disabledDoubleNextButton () {
            return this.currentPage < this.totalPage -1 ? '' : 'disabled'
        },
        totalPage () {
            return Math.ceil(this.totalRows / this.perPage)
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
            this.textMain = pageIndex == this.totalPage ? this.totalRows : pageIndex * this.perPage
            let from = (pageIndex - 1) * this.perPage
            let to = pageIndex == this.totalPage ? this.totalRows : pageIndex * this.perPage
            this.$emit('change', this.datasource.slice(from, to))
        }
    },
    watch: {
        datasource (newVal) {
            this.textMain = newVal.length > this.perPage ? this.perPage : newVal.length
            this.currentPage = newVal.length > 0 ? 1 : 0
            this.$emit('change', this.datasource.slice(0, this.perPage))
        },
        perPage (newVal) {

            if (this.currentPage > this.totalPage) {
                this.currentPage = this.totalPage
            }

            this.textMain = this.currentPage == this.totalPage ? this.totalRows : this.currentPage * newVal
            let from = (this.currentPage - 1) * newVal
            let to = this.currentPage == this.totalPage ? this.totalRows : this.currentPage * newVal
            this.$emit('change', this.datasource.slice(from, to))
        },
    },
    mounted () {
        document.addEventListener('click', this.considerHideDropdown)
        let from = (this.currentPage - 1) * this.perPage
        let to = this.currentPage == this.totalPage ? this.totalRows : this.currentPage * this.perPage
        this.$emit('change', this.datasource.slice(from, to))
    },
    data () {

        let defaultPerPage = 3

        return {
            identify: Date.now() + '-' + Math.floor(Math.random() * 1000) + '-' + Math.floor(Math.random() * 1000),
            perPage: defaultPerPage,
            textMain: this.datasource.length > defaultPerPage ? defaultPerPage : this.datasource.length,
            currentPage: this.datasource.length > 0 ? 1 : 0,
            showDropdown: '',
            style: '',
            popper: null,
        }
    }
}
</script>
