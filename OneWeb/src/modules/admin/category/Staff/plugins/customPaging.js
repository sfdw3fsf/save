import Vue from 'vue'

export function customPaging () {
  return {
    template: Vue.component('pagerTemplate', {
      template: `
        <div class="tc-pagination nowrap ovauto h40">
        <a href="#" @click.prevent="gotoFirstPage" class="btn one-arrow-left-forward"></a>
<!--        <a href="#" class="btn fa fa-angle-double-left" @click.prevent="previousPage(2)"></a>-->
        <a href="#" class="btn fa fa-angle-left" @click.prevent="previousPage(1)"></a>
        <span class="text">Bản ghi</span>
        <div class="dropdown inline">
          <div class="fw6 input-number" data-toggle="dropdown">
            <span class="text-main">{{ currentRecords }}</span>/{{ this.$data.data.totalRecordsCount }}
          </div>
        </div>
        <a href="#" class="btn fa fa-angle-right" @click.prevent="nextPage(1)"></a>
<!--        <a href="#" class="btn fa fa-angle-double-right" @click.prevent="nextPage(2)"></a>-->
        <a href="#" @click.prevent="gotoLastPage" class="btn one-arrow-right-forward"></a>
        </div>`,
      computed: {
        value: function () {
          return this.$data.data.currentPage
        },
        currentRecords: function () {
          return this.$data.data.currentPage * this.$data.data.pageSize < this.$data.data.totalRecordsCount
            ? this.$data.data.currentPage * this.$data.data.pageSize
            : this.$data.data.totalRecordsCount
        }
      },
      methods: {
        nextPage (nextPage) {
          let grid = this.$el.closest('.e-grid').ej2_instances[0]
          grid.goToPage(this.$data.data.currentPage + nextPage)
        },
        previousPage (previousPage) {
          let grid = this.$el.closest('.e-grid').ej2_instances[0]
          grid.goToPage(this.$data.data.currentPage - previousPage)
        },
        gotoFirstPage () {
          let grid = this.$el.closest('.e-grid').ej2_instances[0]
          grid.goToPage(1)
        },
        gotoLastPage () {
          let grid = this.$el.closest('.e-grid').ej2_instances[0]
          grid.goToPage(this.$data.data.totalPages)
        }
      }
    })
  }
}
