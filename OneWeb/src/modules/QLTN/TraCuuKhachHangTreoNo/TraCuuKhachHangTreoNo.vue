<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='animationSettings'
    ref="dlgKhachHangTreoNo"
    :header='"Tra cứu khách hàng treo nợ"'
    showCloseIcon=true
    width='80%'
    :target='target'
    :close="closeDialog"
    :open="open"
    >
    <div class="">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" v-on:click.prevent="doSearch()"><span class="icon one-file-search"></span>Tra cứu</a>
          </li>
          <li>
            <a href="#" v-on:click.prevent="exportExcel"><span class="icon one-xlsx"></span>Xuất Excel</a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin tra cứu</div>
          <div class="info-row">
              <div class="key w100">
                Mã TT/Mã TB <span class="required">*</span>
              </div>
              <div class="value">
                <form v-on:submit.prevent="doSearch()">
                  <b-form-input ref="p_ma_tt_tb" class="form-control highlight" autofocus v-model="p_ma_tt_tb" placeholder="Nhập vào Mã TT/ Mã TB" @focus="$event.target.select()"></b-form-input>
                </form>
              </div>
          </div>
        </div>
        <app-ket-qua-tra-cuu ref="dataGrid" :items="$data.results" :pageinfo="$data.pageinfo"></app-ket-qua-tra-cuu>
      </div>
  </div>
  </ejs-dialog>
</template>

<script>
  import Vue from 'vue'
  import { DialogPlugin } from '@syncfusion/ej2-vue-popups'

  import KetQuaTraCuu from './components/KetQuaTraCuu.vue'
  import TraCuuAPI from '../api/TraCuuAPI'
  import EventBus from '@/utils/eventBus'

  Vue.use(DialogPlugin)

  export default Vue.extend({
    components : { appKetQuaTraCuu : KetQuaTraCuu},
    created () {
      EventBus.$on('pageChange', this.pageChangeHandler)
      EventBus.$on('rowActive', this.rowActiveHandler)
    },
    destroyed () {
      EventBus.$off('pageChange', this.pageChangeHandler)
      EventBus.$off('rowActive', this.rowActiveHandler)
    },
    data () {
      return {
        p_ma_tt_tb : '',
        results : [],
        pageinfo : {
          page: 1,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          sort: null,
          propertiesSort: null,
          currentRow: 0
        },
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' },
      }
    },
    methods : {
      clearForm () {
        this.p_ma_tt_tb = ''
      },
      clearResult () {
        this.results = []
        this.pageinfo.currentRow = 0
      },
      validateData () {
        let error = false
        if(this.p_ma_tt_tb === null
          || this.p_ma_tt_tb.trim() === ''){
            this.$toast.error('Trường Mã TT/ Mã TB bặt buộc nhập.')
            this.$refs.p_ma_tt_tb.focus()
            error = true
        }
        return error
      },
      doSearch () {
        if(this.validateData())
          return false
        this.loading(true)
        this.clearResult()

        TraCuuAPI.getTraCuuKhachHangTreoNo(this.axios, this.p_ma_tt_tb)
          .then(
            (response) => {
              if (response.data.error_code === 'BSS-00000000'
                  && response.data.data !== undefined
                  && response.data.data.length > 0)
              {
                this.results = response.data.data
                this.pageinfo.page = 0
                this.pageinfo.totalElement = this.results.length
              } else {
                this.$toast.error('Không có dữ liệu, vui lòng thử lại.')
              }
            })
          .catch(error => {
            console.log(error)
          })
          .finally(() => {
            this.loading(false)
          })
      },
      exportExcel () {
        if(this.results.length <= 0){
          this.$toast.error('Không tìm thấy dữ liệu để xuất file excel.')
          return false
        }
        this.$refs.dataGrid.exportExcel(this.results)
      },
      clearResult () {
        this.results = []
        this.pageinfo.page = 1
        this.pageinfo.totalElement = 0
        this.pageinfo.totalPages = 0
      },
      openDialog () {
        this.$refs.dlgKhachHangTreoNo.show()

      },
      closeDialog () {
        this.clearResult()
      },
      open() {
        this.$refs.p_ma_tt_tb.focus()
      },
      /*
        Event Handler
      */
      pageChangeHandler(e) {
        //this.pageinfo.page = e
        this.pageinfo.page = e.currentPage - 1
        this.pageinfo.maxSize = e.perPage
        this.doSearch()
      },
      rowActiveHandler(e) {
        try{
          let index = parseInt(e)
          this.pageinfo.currentRow = index
        }catch (err) {

        }
      }
    }
  })
</script>
