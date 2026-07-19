<template>
  <ejs-dialog :enableResize='true' :allowDragging='true' :visible='false' :animationSettings='animationSettings'
    ref="dlgPhieuYeuCauMoKhoa" :header='"Tra cứu phiếu yêu cầu mở khóa"' showCloseIcon=true width='80%' :target='target'
    :isModal="true">
    <div class="">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" v-on:click.prevent="doSearch()"><span class="icon one-search"></span>Tìm kiếm</a>
          </li>
          <li>
            <a href="javascript: void(0)" @click="exportExcel">
              <span class="icon one-xlsx"></span>Xuất Excel
            </a>
          </li>
          <li>
            <a href="javascript: void(0)" @click="close">
              <span class="icon one-export"></span> Thoát
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin tra cứu</div>
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w100">
                  <div class="">
                    <b-form-checkbox v-model="params.p_chk_ngay_yc" class="check" value="1" unchecked-value="0">
                      <span class="name">Ngày YC</span>
                    </b-form-checkbox>
                  </div>
                </div>
                <div class="value">
                  <ejs-datepicker v-model="params.p_ngay_yc" :max="config.dateConfig.max"
                    :format="config.dateConfig.format" :enabled="f_ngayyc_enabled" :allowEdit=true
                    :showClearButton=false>
                  </ejs-datepicker>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w80">
                  Số máy/Acc
                </div>
                <div class="value">
                  <b-form-input ref="p_somay_acc" autofocus class="form-control highlight" v-model="params.p_somay_acc"
                    placeholder="Nhập vào số máy/ account"></b-form-input>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w100">
                  <div class="">
                    <b-form-checkbox v-model="params.p_chk_kieu_yc" class="check" value="1" unchecked-value="0">
                      <span class="name">Kiểu YC</span>
                    </b-form-checkbox>
                  </div>
                </div>
                <div class="value">
                  <div class="">
                    <select2 v-model="params.p_kieu_yc" :disabled="!f_kieu_yc_enabled"
                      :options="params.p_kieu_yc_options" />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w100">
                  <div class="">
                    <b-form-checkbox v-model="params.p_chk_denngay" class="check" value="1" unchecked-value="0">
                      <span class="name">Đến ngày</span>
                    </b-form-checkbox>
                  </div>
                </div>
                <div class="value">
                  <ejs-datepicker v-model="params.p_denngay" :format="config.dateConfig.format"
                    :enabled="f_denngay_enabled" :allowEdit=true :showClearButton=false>
                  </ejs-datepicker>
                </div>
              </div>
            </div>
            <div class="col-sm-8 col-12">
              <div class="info-row">
                <div class="key w80">
                  <div class="">
                    <b-form-checkbox v-model="params.p_chk_donvi" class="check" value="1" unchecked-value="0">
                      <span class="name">Đơn vị</span>
                    </b-form-checkbox>
                  </div>
                </div>
                <div class="value">
                  <ejs-dropdownlist :enabled="f_donvi_enabled" :dataSource='params.p_donvi_options'
                    :fields="config.fieldsDonVi" v-model="params.p_donvi"></ejs-dropdownlist>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách phiếu yêu cầu</div>
          <div class="table-content">
            <DataGrid v-bind:columns="[
              { fieldName: 'NGAY_YC', headerText: 'Ngày YC', textAlign: 'Right', allowFiltering: true, width: 120 },
              { fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true },
              { fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true, width: 120 },
              { fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: true },
              { fieldName: 'KIEU_YC', headerText: 'Kiểu YC', allowFiltering: true, width: 150 },
              { fieldName: 'TRANGTHAI', headerText: 'Trạng thái', allowFiltering: true, width: 100 },
              { fieldName: 'TEN_DV', headerText: 'Đơn vị', allowFiltering: true },
              { fieldName: 'NOIDUNG_YC', headerText: 'Nội dung YC', allowFiltering: true, width: 200 }

            ]" v-bind:dataSource="results" :showFilter="true" :allowPaging="true" :allowExcelExport="true"
              :totalRecords="pageinfo.totalElement" :pageSize="pageinfo.maxSize" :pageIndex="pageinfo.page"
              ref="grdPhieuYeuCau">
            </DataGrid>
          </div>

        </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import Vue from 'vue'
// import { DialogPlugin } from '@syncfusion/ej2-vue-popups'

import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import CommonsAPI from '../api/Commons'
import TraCuuAPI from '../api/TraCuuAPI'
import moment from 'moment'

Vue.use(DatePickerPlugin)
// Vue.use(DialogPlugin)

export default Vue.extend({
  name: 'TraCuuPhieuYeuCauMoKhoa',
  components: {},
  created() {
  },
  mounted() {
  },
  destroyed() {
  },
  data() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      config: {
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        },
        fieldsDonVi: { text: 'TEN_HIENTHI', value: 'DONVI_ID' },
      },
      params: {
        p_chk_ngay_yc: '0',
        p_ngay_yc: new Date(),
        p_somay_acc: '',
        p_chk_kieu_yc: '0',
        p_kieu_yc_options: [
          { id: '0', text: 'Chêm thuê bao' },
          { id: '1', text: 'Mở chêm thuê bao' }
        ],
        p_kieu_yc: 0,
        p_chk_denngay: '0',
        p_denngay: new Date(),
        p_chk_donvi: '0',
        p_donvi: null,
        p_donvi_options: []
      },
      results: [],
      pageinfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      }
    }
  },
  computed: {
    p_ngay_yc: {
      get() {
        return this.params.p_ngay_yc
      },
      set(value) {
        return this.params.p_ngay_yc = value
      }
    },
    f_ngayyc_enabled() {
      return this.params.p_chk_ngay_yc === '1'
    },
    f_kieu_yc_enabled() {
      return this.params.p_chk_kieu_yc === '1'
    },
    p_kieu_yc: {
      get() {
        return this.params.p_kieu_yc
      },
      set(value) {
        this.params.p_kieu_yc = value
      }
    },
    f_denngay_enabled() {
      return this.params.p_chk_denngay === '1'
    },
    p_denngay: {
      get() {
        return this.params.p_denngay
      },
      set(value) {
        this.params.p_denngay = value
      }
    },
    f_donvi_enabled() {
      return this.params.p_chk_donvi === '1'
    },
    p_donvi: {
      get() {
        return this.params.p_donvi
      },
      set(value) {
        this.p_donvi = value
      }
    }
  },
  methods: {
    validateData() {
      let error = false
      if (this.params.p_chk_ngay_yc === '1'
        && this.params.p_ngay_yc === null) {
        this.$toast.error('Vui lòng chọn ngày yêu cầu')
        error = true
      }
      if (this.params.p_chk_kieu_yc === '1'
        && this.params.p_kieu_yc === null) {
        this.$toast.error('Vui lòng chọn kiểu yêu cầu')
        error = true
      }
      if (this.params.p_chk_denngay === '1'
        && this.params.p_denngay === null) {
        this.$toast.error('Vui lòng chọn đến ngày')
        error = true
      }
      return error
    },
    doSearch() {
      if (this.validateData())
        return false

      this.results = []
      this.pageinfo.page = 0
      this.pageinfo.totalElement = 0

      let postData = {
        pDenNgay: this.params.p_chk_denngay === '1' ? moment(this.params.p_denngay).format('DD/MM/YYYY') : null,
        pTuNgay: this.params.p_chk_ngay_yc === '1' ? moment(this.params.p_ngay_yc).format('DD/MM/YYYY') : null,
        pDonViId: this.params.p_chk_donvi === '1' ? this.params.p_donvi : null,
        pKieuYC: this.params.p_chk_kieu_yc === '1' ? this.params.p_kieu_yc : null,
        pMaTB: this.params.p_somay_acc,
        pageNo: this.pageinfo.page,
        pageSize: this.pageinfo.maxSize,
        totalRow: 1
      }

      this.loading(true)

      TraCuuAPI.getTraCuuYeuCauKhoaMo(this.axios, postData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.data !== undefined
              && response.data.data.data.length > 0) {
              this.results = response.data.data.data;

              this.pageinfo = {
                page: response.data.data.page + 1, // base 1
                maxSize: response.data.data.maxSize,
                totalElement: response.data.data.totalElement,
                totalPages: response.data.data.totalPages,
                sort: response.data.data.sort,
                propertiesSort: response.data.data.propertiesSort
              }
            }
            else {
              if (response.data.error_code === 'BSS-00000000')
                this.$toast.error('không có bảng ghi nào thỏa điều kiện')
            }
          })
        .catch(error => {
          //console.log(error)
          this.$toast.error(error.response.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    }, // end of doSearch
    clearForm() {
      this.params = {
        p_chk_ngay_yc: '0',
        p_ngay_yc: new Date(),
        p_somay_acc: '',
        p_chk_kieu_yc: '0',
        p_kieu_yc_options: [
          { id: '0', text: 'Chêm thuê bao' },
          { id: '1', text: 'Mở chêm thuê bao' }
        ],
        p_kieu_yc: 0,
        p_chk_denngay: '0',
        p_denngay: new Date(),
        p_chk_donvi: '0',
        p_donvi: null,
        p_donvi_options: []
      }
    },
    clearResult() {
      this.results = []
      this.pageinfo.page = 0
      this.pageinfo.totalElement = 0
      this.pageinfo.totalPages = 0
    },
    exportExcel() {
      if (this.results.length <= 0) {
        this.$toast.error('Không tìm thấy dữ liệu để xuất ra file excel.')
        return
      }
      this.$refs.grdPhieuYeuCau.$refs.grid.excelExport(this.results)
    },
    async loadDSDonVi() {
      await CommonsAPI.getDanhSachDonVi(this.axios).then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
            && response.data.data !== undefined
            && response.data.data.length > 0) {
            this.params.p_donvi_options = response.data.data
            this.params.p_donvi = this.params.p_donvi_options[0].DONVI_ID
          }
        }
      )
    },
    openDialog() {
      this.clearResult()
      this.clearForm()
      this.loadDSDonVi()
      this.$refs.dlgPhieuYeuCauMoKhoa.show()
    },
    close() {
      this.$refs.dlgPhieuYeuCauMoKhoa.hide()
    },

    /*
      Event Handler
    */
    pageChangeHandler(e) {
      //this.pageinfo.page = e
      this.pageinfo.page = e.currentPage - 1
      this.pageinfo.maxSize = e.perPage
      this.doSearch();
    }
  } // end of methods
})
</script>
