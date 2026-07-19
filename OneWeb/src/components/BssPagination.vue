<template>
    <div class="tc-pagination">
        <a href="javascript:void(0)" class="btn one-arrow-left-forward" :class="disabledPreviousButton" @click="pageChange(currentPage = 1)"></a>
        <a href="javascript:void(0)" class="btn fa fa-angle-double-left" :class="disabledDoublePreviousButton" @click="pageChange(currentPage = currentPage - 2)"></a>
        <a href="javascript:void(0)" class="btn fa fa-angle-left" :class="disabledPreviousButton" @click="pageChange(--currentPage)"></a>
        <span class="text">Bản ghi</span>
        <div class="dropdown inline" :data-bss_pagination_identify="identify" :class="showDropdown">
            <div class="fw6 input-number" :data-bss_pagination_identify="identify" data-toggle="dropdown" @click="toggleDropdown">
                <span class="text-main" :data-bss_pagination_identify="identify">{{textMain}}</span>/{{totalRows}}
            </div>
            <div class="dropdown-menu" :data-bss_pagination_identify="identify" :class="showDropdown">
                <a href="javascript:void(0)" :data-bss_pagination_identify="identify" class="dropdown-item" @click="selectDropdownItem(10)">10</a>
                <a href="javascript:void(0)" :data-bss_pagination_identify="identify" class="dropdown-item" @click="selectDropdownItem(20)">20</a>
                <a href="javascript:void(0)" :data-bss_pagination_identify="identify" class="dropdown-item" @click="selectDropdownItem(50)">50</a>
                <a href="javascript:void(0)" :data-bss_pagination_identify="identify" class="dropdown-item" @click="selectDropdownItem(100)">100</a>
            </div>
        </div>
        <a href="javascript:void(0)" class="btn fa fa-angle-right" :class="disabledNextButton" @click="pageChange(++currentPage)"></a>
        <a href="javascript:void(0)" class="btn fa fa-angle-double-right" :class="disabledDoubleNextButton" @click="pageChange(currentPage = currentPage + 2)"></a>
        <a href="javascript:void(0)" class="btn one-arrow-right-forward" :class="disabledNextButton" @click="pageChange(currentPage = totalPage)"></a>
    </div>
</template>
<script>
export default {
    name: 'BssPagination',
    props: {
        totalRows: Number
    },
    computed: {
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
        },
        considerHideDropdown (event) {
            if (event.target.getAttribute('data-bss_pagination_identify') != this.identify) {
                this.showDropdown = ''
            }
        },
        pageChange (pageIndex) {
            this.textMain = pageIndex == this.totalPage ? this.totalRows : pageIndex * this.perPage
            this.$emit('change', { currentPage: pageIndex, perPage: this.perPage })
        }
    },
    watch: {
        totalRows (newVal) {
            this.textMain = newVal > this.perPage ? this.perPage : newVal
            this.currentPage = newVal > 0 ? 1 : 0
            this.$emit('change', { currentPage: this.currentPage, perPage: this.perPage })
        },
        perPage (newVal) {

            if (this.currentPage > this.totalPage) {
                this.currentPage = this.totalPage
            }
            this.textMain = this.currentPage == this.totalPage ? this.totalRows : this.currentPage * newVal
            this.$emit('change', { currentPage: this.currentPage, perPage: this.perPage })
        }
    },
    mounted () {
        document.addEventListener('click', this.considerHideDropdown)
        this.$emit('change', { currentPage: this.currentPage, perPage: this.perPage })
    },
    data () {

        let defaultPerPage = 10

        return {
            identify: Date.now() + '-' + Math.floor(Math.random() * 1000) + '-' + Math.floor(Math.random() * 1000),
            perPage: defaultPerPage,
            textMain: this.totalRows > defaultPerPage ? defaultPerPage : this.totalRows,
            currentPage: this.totalRows > 0 ? 1 : 0,
            showDropdown: ''
        }
    }
}
</script>
