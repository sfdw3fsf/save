<template>
    <div class="tc-pagination">
        <a href="javascript:void(0)" class="btn one-arrow-left-forward" :class="disabledDoublePreviousButton" @click="pageChange(1)"></a>
        <a href="javascript:void(0)" class="btn fa fa-angle-double-left" :class="disabledDoublePreviousButton" @click="pageChange($props.pageinfo.page - 1)"></a>
        <a href="javascript:void(0)" class="btn fa fa-angle-left" :class="disabledPreviousButton" @click="goPrevItem()"></a>
        <span class="text">Bản ghi</span>
        <div class="dropdown inline" :data-bss_pagination_identify="identify" :class="showDropdown">
            <div class="fw6 input-number" :data-bss_pagination_identify="identify" data-toggle="dropdown" @click="toggleDropdown">
                <span class="text-main" :data-bss_pagination_identify="identify">{{info}}</span>/{{$props.pageinfo.totalElement}}
            </div>
            <div class="dropdown-menu" :data-bss_pagination_identify="identify" :class="showDropdown">
                <a href="javascript:void(0)" :data-bss_pagination_identify="identify" class="dropdown-item" @click="selectDropdownItem(10)">10</a>
                <a href="javascript:void(0)" :data-bss_pagination_identify="identify" class="dropdown-item" @click="selectDropdownItem(20)">20</a>
                <a href="javascript:void(0)" :data-bss_pagination_identify="identify" class="dropdown-item" @click="selectDropdownItem(50)">50</a>
                <a href="javascript:void(0)" :data-bss_pagination_identify="identify" class="dropdown-item" @click="selectDropdownItem(100)">100</a>
            </div>
        </div>
        <a href="javascript:void(0)" class="btn fa fa-angle-right" :class="disabledNextButton" @click="goNextItem()"></a>
        <a href="javascript:void(0)" class="btn fa fa-angle-double-right" :class="disabledDoubleNextButton" @click="pageChange($props.pageinfo.page + 1)"></a>
        <a href="javascript:void(0)" class="btn one-arrow-right-forward" :class="disabledDoubleNextButton" @click="pageChange($props.pageinfo.totalPages)"></a>
    </div>
</template>
<script>
export default {
    name: 'RemotePagination',
    props: {
      pageinfo : Object
    },
    computed: {
        disabledPreviousButton () {
            return this.pageinfo.currentRow  > 1 ? '' : 'disabled'
        },
        disabledDoublePreviousButton () {
            return this.pageinfo.page > 1 ? '' : 'disabled'
        },
        disabledNextButton () {
          let items = this.pageinfo.page < this.pageinfo.totalPages ? this.pageinfo.maxSize : (this.pageinfo.totalElement - this.pageinfo.maxSize*(this.pageinfo.page-1))
          return this.pageinfo.currentRow < items ? '' : 'disabled'
        },
        disabledDoubleNextButton () {
          // console.log(this.currentPage, this.totalPage)
          return this.pageinfo.page < this.pageinfo.totalPages ? '' : 'disabled'
        },
        totalPage () {
            return Math.ceil(this.totalRows / this.perPage)
        },
        info () {
          if(this.pageinfo.page === 0)
            return this.pageinfo.currentRow
          else
            return (this.pageinfo.page - 1) * this.pageinfo.maxSize + this.pageinfo.currentRow
        }
    },
    methods: {
        selectDropdownItem (value) {
          this.toggleDropdown()
          // this.perPage = value
          this.$emit('change', { currentPage: this.pageinfo.page, maxSize: value})
        },
        toggleDropdown () {
          this.showDropdown = !this.showDropdown ? 'show' : ''
        },
        considerHideDropdown (event) {
          if (event.target.getAttribute('data-bss_pagination_identify') != this.identify) {
              this.showDropdown = ''
          }
        },
        pageChange (pageNo) {
          //this.textMain =  pageIndex * this.perPage + this.currentRow//pageIndex == this.totalPage ? this.totalRows : pageIndex * this.perPage
          this.$emit('change', { currentPage: pageNo, maxSize: this.pageinfo.maxSize})
            // console.log('pageChange change ', pageIndex)
        },
        goPrevItem() {
          this.$emit('goPrev', this.pageinfo.currentRow - 1)
        },
        goNextItem() {
          this.$emit('goNext', this.pageinfo.currentRow + 1)
        }
    },
    watch: {
      /*
        totalRows (newVal) {
            // this.currentRow = newVal > 0 ? 1 : 0,
            this.currentPage = newVal > 0 ? 1 : 0
            this.textMain = this.perPage * (this.currentPage - 1) + (this.currentRow + 1) //newVal > this.perPage ? this.perPage : newVal
            //this.$emit('change', { currentPage: this.currentPage, perPage: this.perPage })
            // console.log(this.perPage * (this.currentPage - 1) + this.currentRow)
        },
      */
        perPage (newVal) {
          // this.textMain = this.currentPage == this.totalPage ? this.totalRows : this.currentPage * newVal
          this.textMain = this.perPage * (this.currentPage - 1) + (this.currentRow + 1)
          this.$emit('change', { currentPage: this.currentPage, perPage: this.perPage })
          // console.log('perPage change ', newVal)
        }
    },
    mounted () {
        document.addEventListener('click', this.considerHideDropdown)
        // this.$emit('change', { currentPage: this.currentPage, perPage: this.perPage })
    },
    data () {

        return {

            identify: Date.now() + '-' + Math.floor(Math.random() * 1000) + '-' + Math.floor(Math.random() * 1000),
          /*
            perPage: defaultPerPage,
            currentPage: this.totalRows > 0 ? 1 : 0,
            // currentRow: this.totalRows > 0 ? 1 : 0,
            textMain: this.perPage * (this.currentPage - 1) + this.currentRow, //this.totalRows > defaultPerPage ? defaultPerPage : this.totalRows,
          */
            showDropdown: ''
        }
    }
}
</script>
