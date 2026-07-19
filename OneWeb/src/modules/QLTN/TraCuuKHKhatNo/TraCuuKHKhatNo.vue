<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='animationSettings'
    ref="dlgKhachHangKhatNo"
    :header='"Tra cứu khách hàng khất nợ"'
    showCloseIcon=true
    width='1160px'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="">
      <!-- BEGIN TOOLBAR -->
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" v-on:click.prevent="doSearch()"><span class="icon one-file-search"></span>Tra cứu</a>
          </li>
          <li>
            <a href="#" v-on:click.prevent="exportExcel()"><span class="icon one-xlsx"></span>Xuất Excel</a>
          </li>
          <li>
            <a href="#" v-on:click.prevent="closeDialog()">
              <span class="icon one-export"></span> Thoát
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin tra cứu</div>
          <div class="info-row">
            <div class="key">Mã TT/Mã TB<span class="required">*</span></div>
            <div class="value">
              <form v-on:submit.prevent="doSearch()">
                <input ref="p_ma_tt_tb" v-model="p_ma_tt_tb" autofocus type="text" class="form-control highlight" required>
              </form>
            </div>
          </div>
        </div>
        <div class="box-form">
            <div class="legend-title">Kết quả tra cứu</div>
            <div class="table-content">
              <DataGrid
                v-bind:columns="[
                {fieldName: 'MA_TB', headerText: 'Mã TB', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'NGAYKHATNO', headerText: 'Ngày khất nợ', headerTextAlign: 'Center', textAlign: 'Right', allowFiltering: true},
                {fieldName: 'NGAYHENTRA', headerText: 'Ngày hẹn trả', headerTextAlign: 'Center', textAlign: 'Right', allowFiltering: true},
                {fieldName: 'LYDO', headerText: 'Lý do', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'SO_CT', headerText: 'Số CT', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'NGAY_CN', headerText: 'Ngày CN', headerTextAlign: 'Center', textAlign: 'Right', allowFiltering: true},
                {fieldName: 'NGUOI_CN', headerText: 'Người CN', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'MAY_CN', headerText: 'Máy CN', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true},
                ]"
              v-bind:dataSource="results" :showFilter="true"
              :allowPaging="true" :totalRecords="totalItems"
              :allowExcelExport='true'
              ref=gridKhKhatNo
              >
            </DataGrid>
            </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import Vue from 'vue'
import TraCuuAPI from '../api/TraCuuAPI'
import { GridPlugin, Filter, Resize, ExcelExport, Sort} from "@syncfusion/ej2-vue-grids"
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import RemotePagination from '@/components/RemotePagination'

Vue.use(GridPlugin)
Vue.use(DialogPlugin)

 export default Vue.extend({
    components : {appRemotePagination : RemotePagination},
    computed : {
      currentRow (){
        return this.pageinfo.currentRow
      }
    },
    created () {

    },
    data () {
      return {
        p_ma_tt_tb : null,
        results : [],
        totalItems: 0,
        pageinfo : {
          page: 1,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          sort: null,
          propertiesSort: null,
          currentRow: 0
        },
        filterOptions: { enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true},
        templateOptions : {
          create: function (args) {
            // console.log(args.column.field)
            if(args.column.field === 'STT')
              return '<span class="fa"></span>'
            else if(args.column.field === 'CHECKBOX'){
              return '<div class="e-checkbox-wrapper e-css"><input class="e-checkselect" type="checkbox"><span class="e-frame e-icons"></span><span class="e-label"> </span></div>'
            }
            return '<div class="input-icon-right"><input id="' + args.column.field + '_filterBarcell" type="search" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>';
          },
          write: function (args) {
            args.element.addEventListener('input', (args) => {
              console.log(args)
              //this.$refs.gridKhKhatNo.filterByColumn(args.currentTarget.id, 'contains', value, '');
            })
          }
        },
        cRowSelected: function() {
          return {
            template : Vue.component('columntemplate', {
              template : '<span class="fa"></span>'
            })  // end of template
          }
        },
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' }
      }
    },
    methods : {
      activeRow (args){
        //this.rowActived = parseInt(args.rowIndex)
        // EventBus.$emit('rowActive', parseInt(args.rowIndex + 1))
        this.rowActiveHandler(parseInt(args.rowIndex + 1))
      },
      actionComplete: function(args) {
        // console.log(args)
        if(args.requestType == 'refresh' && this.results.length > 0)
          // EventBus.$emit('rowActive', 1)
          this.rowActiveHandler(1)
      },
      validateData () {
        let error = false
        if(this.p_ma_tt_tb === null
          || this.p_ma_tt_tb.trim() === ''){
            this.$toast.error('Trường Mã TT/ Mã TB bắt buộc nhập.')
            this.$refs.p_ma_tt_tb.focus()
            error = true
        }
        return error
      },
      doSearch () {
        if(this.validateData())
          return false

        let postData = {
          pMa: this.p_ma_tt_tb,
          pPhanVungId: this.$auth.getPhanVungID(),
          pageNo: this.pageinfo.page - 1,
          pageSize: this.pageinfo.pageSize
        }

        this.loading(true)
        this.clearResult()

        TraCuuAPI.getTraCuuKhachHangKhatNo(this.axios, postData)
          .then(
            (response) => {
              if (response.data.error_code === 'BSS-00000000'
                  && response.data.data !== undefined
                  && response.data.data.data !== undefined
              )
              {
                this.results = response.data.data.data
                this.pageinfo = {
                  page : response.data.data.page + 1, // base 1
                  maxSize: response.data.data.maxSize,
                  totalElement: response.data.data.totalElement,
                  totalPages: response.data.data.totalPages,
                  sort: response.data.data.sort,
                  propertiesSort: response.data.data.propertiesSort,
                  currentRow: 0
                }
                this.totalItems = response.data.data.totalElement
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
      clearForm () {
        this.p_ma_tt_tb = ''
      },
      clearResult () {
        this.results = []
        this.pageinfo.page = 0
        this.pageinfo.totalElement = 0
        this.pageinfo.totalPages = 0
        this.pageinfo.currentRow = 0
      },
      exportExcel() {
        //let grid = document.getElementById('gridKhKhatNo').ej
        if(this.results.length <= 0) {
          this.$toast.error('Không tìm thấy dữ liệu đề xuất file excel.')
          return false
        }
        let excelExportProperties = {
            dataSource: this.results
        };
        this.$refs.gridKhKhatNo.excelExport(excelExportProperties)
      },
      openDialog () {
        this.$refs.dlgKhachHangKhatNo.show()
      },
      closeDialog() {
        this.$refs.dlgKhachHangKhatNo.hide()
      },
      onDialogClose () {
        this.clearForm()
        this.clearResult()
      },
      onDialogOpen () {
        this.$refs.p_ma_tt_tb.focus()
      },
      onChangePage (e) {
        this.pageinfo.page = e.currentPage - 1
        this.pageinfo.maxSize = e.perPage
        this.doSearch()
      },
      onGoNextItem (value) {
        this.rowActiveHandler(value)

      },
      onGoPrevItem (value) {
        this.rowActiveHandler(value)
      },
      rowActiveHandler(e) {
        this.pageinfo.currentRow = parseInt(e)
      }
    },
    provide: {
      grid: [Filter, Resize, Sort, ExcelExport]
    },
    watch : {
      currentRow : {
        handler: function(new_value, old_value) {

          let newItem = this.$refs.gridKhKhatNo.getRowByIndex(new_value - 1)
          let oldItem = this.$refs.gridKhKhatNo.getRowByIndex(old_value - 1)
          // console.log(newItem)
          if(newItem !== undefined)
            newItem.classList.add('e-focus')
          if(oldItem !== undefined)
            oldItem.classList.remove('e-focus')

        }
      },
    }
  })
</script>
