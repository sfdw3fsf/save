<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='animationSettings'
    ref="dlgGachNoTheoFile"
    :header='"Gạch nợ thuê bao theo file"'
    showCloseIcon=true
    width='80%'
    height=800
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    :beforeClose="onBeforeClose"
    :beforeOpen="onBeforeOpen"
    >
    <div class="modal-content popup-box">
      <input id="gachno_tb_theofile" ref="gachno_tb_theofile" @change="uploadFile" type="file" hidden>
      <div class="list-actions-top">
          <ul class="list">

              <li>
                <a href="#" v-on:click.prevent="doSearch()" title="Hiển thị danh sách"><span class="icon one-search"></span> Danh sách</a>
              </li>
              <li>
                <a href="#" v-on:click.prevent="doGachNo()"><span class="icon one-edit"></span> Gạch nợ (F7)</a>
              </li>
              <li>
                <a href="#" v-on:click.prevent="doCancel()" title="Hủy thao tác"><span class="icon ui-1_circle-remove  nc-icon-glyph"></span> Hủy</a>
              </li>
              <li>
                <a href="#" v-on:click.prevent="doExportExcel()"><span class="icon -ap one-excel"></span> Xuất Excel</a>
              </li>
          </ul>
          <div class="more">
              <a href="#">
                  <span class="icon -ap  icon-dots-three-vertical"></span>
              </a>
          </div>
      </div>
      <div class="popup-body">
          <div class="box-form">
              <div class="info-row">
                  <div class="key w50">
                      <span class="-ap icon-warning3 text-warning f20 mar10"></span>
                  </div>
                  <div class="key" style="width: 120px;">

                      <div class="check-action">
                          <input name="p_kieu" type="radio" value=0 class="check" v-model="params.p_kieu">
                          <span class="name">Tất cả</span>
                      </div>
                  </div>
                  <div class="value text-main fw6" v-show="params.p_kieu === '0'">
                      File excel gồm 4 trường: MA_TT, MA_TB, TIEN_TRA, GHICHU
                  </div>
                  <div class="value text-main fw6" v-show="params.p_kieu === '1'">
                      File excel gồm 4 trường: MA_TT, MA_TB, TIEN_TRA, THANG_TRA, GHICHU
                  </div>

              </div>
              <div class="info-row">
                  <div class="key w50"></div>
                  <div class="key" style="width: 120px;">
                      <div class="check-action">
                        <input name="p_kieu" type="radio" value=1 class="check" v-model="params.p_kieu">
                        <span class="name">Theo tháng trả</span>
                      </div>
                  </div>
                  <div class="value text-main fw6">
                      Viết hoa không dấu, bỏ dòng tiêu đề, Tháng trả định dạng: yyyyMMck. Tiền trả số nguyên, đã bao gồm cả thuế.
                  </div>

              </div>
              <div class="info-row">
                  <div class="key w80">Đường dẫn</div>
                  <div class="value">
                      <div class="input-more-button">
                          <button class="btn" @click="browserFile">
                              <span class="-ap icon-more_horiz"></span>
                          </button>
                          <input type="text" class="form-control " v-model="params.p_file_path">
                      </div>
                  </div>
                  <div class="value" style="width: 130px;">
                    <ejs-datepicker
                        v-model="params.p_kyhoadon"
                        :max="config.max_thang_dc"
                        :start="'Year'"
                        :depth="'Year'"
                        :format="'MM/y'"
                        :change="onKyHoaDonChange"
                        :showClearButton="false"
                        :allowEdit="false"
                        :readonly="true"
                        :disabled="true"
                        :width="'100%'"
                    >
                    </ejs-datepicker>
                  </div>
                  <div class="value" style="width: 40px;">
                    <b-form-select
                        v-model="params.p_chuky_no"
                        :options="params.p_chuky_no_options"
                        value-field="CHUKY"
                        text-field="CHUKY"
                        class="form-control w-100">
                    </b-form-select>
                  </div>
              </div>
              <div class="row">
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w80">
                              <div class="">
                                <b-form-checkbox
                                  v-model = "params.p_chk_dichvu"
                                  class = "check"
                                  value = '1'
                                  unchecked-value = '0'
                                >
                                <span class="name">Dịch vụ</span>
                                </b-form-checkbox>
                              </div>
                          </div>

                          <div class="value">
                            <ejs-dropdownlist :change="onChangeDichVu" :enabled="chk_dichvu" :filtering='onFilteringDichVu' :allowFiltering="true" :dataSource='params.p_dichvu_options' :fields="config.fieldsDichVu" v-model = "params.p_dichvu"></ejs-dropdownlist>
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w100">
                              <div class="">
                                <b-form-checkbox
                                  v-model = "params.p_chk_loaihinh"
                                  class = "check"
                                  value = '1'
                                  unchecked-value = '0'
                                >
                                <span class="name">Loại hình</span>
                                </b-form-checkbox>
                              </div>
                          </div>

                          <div class="value" >
                            <ejs-dropdownlist :enabled="chk_loaihinh" :filtering='onFilteringLoaiHinh' :allowFiltering="true" :dataSource='params.p_loaihinh_options' :fields="config.fieldsLoaiHinh" v-model = "params.p_loaihinh"></ejs-dropdownlist>
                          </div>
                      </div>
                  </div>
              </div>
              <div class="row">
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w80">
                              Thủ quỹ
                          </div>

                          <div class="value">
                            <ejs-dropdownlist :filtering='onFilteringThuQuy' :allowFiltering="true" :dataSource='params.p_thuquy_options' :fields="config.fieldsThuQuy" v-model = "params.p_thuquy" :placeholder="'Chọn thủ quỹ'"></ejs-dropdownlist>
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w100">
                              Hình thức
                          </div>
                          <div class="value" >
                            <ejs-dropdownlist :filtering='onFilteringHTTT' :allowFiltering="true" :dataSource='params.p_httt_options' :fields="config.fieldsHTTT" v-model = "params.p_httt"></ejs-dropdownlist>
                          </div>
                      </div>
                  </div>
              </div>
              <div class="row">
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w80">
                              Ngày TT
                          </div>

                          <div class="value" >
                            <ejs-datepicker
                              v-model="p_ngay_ttoan"
                              :value="config.dateConfig.value"
                              :max="config.dateConfig.maxDate"
                              :format="config.dateConfig.format"
                              :allowEdit=false
                              :showClearButton=false
                              >
                            </ejs-datepicker>
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-6 col-12">
                      <div class="row">
                          <div class="col-sm-6 col-12">
                              <div class="info-row">
                                  <div class="key w100">
                                      Tháng Đ.C
                                  </div>

                                  <div class="value" >
                                    <ejs-datepicker
                                      v-model="p_thang_dc"
                                      :max="config.max_thang_dc"
                                      :start="'Year'"
                                      :depth="'Year'"
                                      :format="'MM/y'"
                                      :change="onKyHoaDonChange"
                                      :showClearButton="false"
                                      :allowEdit="false"
                                      :width="'100%'"
                                    >
                                    </ejs-datepicker>
                                  </div>
                              </div>
                          </div>
                          <div class="col-sm-6 col-12">
                              <div class="info-row">
                                  <div class="key w80">
                                      CK
                                  </div>

                                  <div class="value" >
                                      <div class="select-custom">
                                          <b-form-select
                                            v-model="params.p_chuky"
                                            :options="params.p_chuky_options"
                                            value-field="CHUKY"
                                            text-field="CHUKY"
                                            class="form-control redcolor w-100">
                                          </b-form-select>
                                      </div>
                                  </div>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
              <div class="row">
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w80">
                              Ngân hàng
                          </div>

                          <div class="value" >
                            <ejs-dropdownlist :showClearButton="true" :filtering='onFilteringNganHang' :allowFiltering="true" :dataSource='params.p_nganhang_options' :fields="config.fieldsNganHang" v-model = "params.p_nganhang"></ejs-dropdownlist>
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-6 col-12">
                      <div class="row">
                          <div class="col-sm-6 col-12">
                              <div class="info-row">
                                  <div class="key w100">
                                      Ngày N.Hàng
                                  </div>

                                  <div class="value" >
                                    <ejs-datepicker
                                      v-model="p_ngay_nganhang"
                                      :value="config.dateConfig.value"
                                      :format="config.dateConfig.format"
                                      :allowEdit=false
                                      :showClearButton=true
                                      >
                                    </ejs-datepicker>
                                  </div>
                              </div>
                          </div>
                          <div class="col-sm-6 col-12">
                              <div class="info-row">
                                  <div class="key w80">
                                      Chứng từ
                                  </div>

                                  <div class="value" >
                                      <input type="text" class="form-control" v-model="params.p_chungtu">
                                  </div>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
              <div class="row">
                  <div class="col-sm-6 col-12">
                      <div class="row">
                          <div class="col-sm-5 col-12">
                              <div class="info-row">
                                  <div class="key w80">
                                      Seri
                                  </div>

                                  <div class="value">
                                      <input type="text" class="form-control" v-model="params.p_seri">
                                  </div>
                              </div>
                          </div>
                          <div class="col-sm-4 col-12">
                              <div class="info-row">
                                  <div class="key w30">
                                      Quyển
                                  </div>

                                  <div class="value">
                                    <input type="number" :min="0" class="form-control tright" v-model="$data.params.p_so_quyen" />
                                  </div>
                              </div>
                          </div>
                          <div class="col-sm-3 col-12">
                              <div class="info-row">
                                  <div class="key w50">
                                      Số seri
                                  </div>

                                  <div class="value">
                                    <input type="number" :min="0" class="form-control tright" v-model="$data.params.p_so_seri" />
                                  </div>
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w100">
                              Ghi chú
                          </div>

                          <div class="value">
                              <input type="text" class="form-control" v-model="params.p_ghichu">
                          </div>
                      </div>
                  </div>
              </div>
          </div>
          <div class="box-form">
              <div class="legend-title">
                  <div class="pull-left">
                      Danh sách thuê bao
                  </div>
                  <div class="pull-right red">
                      <span class="time">{{count}}</span> <span class="text marl30">Thông báo: {{thong_bao}}</span>
                  </div>
                  <div class="clearfix"></div>
              </div>
              <div class="nav tabs tab-over">
                <a href="#tabUploadResult" @click="current_tab = 1" :class="{active: current_tab === 1}" data-toggle="tab" class="tab"><span class="-ap icon-list2 text-main f20"></span> Danh sách</a>
                <a href="#tabErrorResult" @click="current_tab = 2" :class="{active: current_tab === 2}" data-toggle="tab" class="tab"><span class="-ap icon-warning3 text-warning f20"></span> Lỗi</a>
              </div>
              <div class="tab-content" style="border-top-left-radius: 0px;border-top-right-radius: 0px;">
                  <div class="tab-pane" id="tabUploadResult" :class="{active: current_tab === 1}">
                    <div class="table-content">
                      <DataGrid
                          v-bind:columns="[
                            {fieldName: 'MA_TT', headerText: 'Mã TT', textAlign: 'Right', allowFiltering: true},
                            {fieldName: 'MA_TB', headerText: 'Số máy/Acc', textAlign: 'Left', allowFiltering: true},
                            {fieldName: 'THANG_TRA', headerText: 'Tháng trả', textAlign: 'Right', allowFiltering: true},
                            {fieldName: 'TIENSETRA', headerText: 'Tiền trả', textAlign: 'Right', format: 'N0', type: 'number', allowFiltering: true},
                            {fieldName: 'PHATSINH', headerText: 'Phát sinh', textAlign: 'Right', allowFiltering: true, format: 'N0', type: 'number'},
                            {fieldName: 'TONGNO', headerText: 'Nợ', textAlign: 'Right', allowFiltering: true, format: 'N0', type: 'number'},
                            {fieldName: 'CONLAI', headerText: 'Còn lại', textAlign: 'Right', allowFiltering: true, format: 'N0', type: 'number'},
                            {fieldName: 'GHICHU', headerText: 'Ghi chú', allowFiltering: true},
                            ]"
                          v-bind:dataSource="uploadResults"
                          v-bind:aggregates="[
                            // {field: 'MA_TT', type: 'Custom', footerTemplate: this.countKhoanNo, customAggregateFn: this.countKhoanNoAggregate},
                            {field: 'TIENSETRA', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.customTienTraAggregate},
                            {field: 'PHATSINH', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.customPhatSinhAggregate},
                            {field: 'TONGNO', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.customTienNoAggregate },
                            {field: 'CONLAI', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.customConLaiAggregate }
                          ]"
                          :enablePagingServer="false"
                          :showFilter="false"
                          :allowPaging="true"
                          :totalRecords="pageinfoUpload.totalElement"
                          :pageSize="pageinfoUpload.maxSize"
                          :pageIndex="pageinfoUpload.page"
                          :allowExcelExport="true"
                          @queryCellInfo="queryCellInfo"
                          ref="dsUploaded"
                          >
                        </DataGrid>
                    </div>
                  </div>
                  <div class="tab-pane" id="tabErrorResult" :class="{active: current_tab === 2}">
                      <div class="table-content">
                        <DataGrid
                          v-bind:columns="[
                            {fieldName: 'MA_TT', headerText: 'Mã TT', textAlign: 'Right', allowFiltering: true},
                            {fieldName: 'MA_TB', headerText: 'Số máy/Acc', textAlign: 'Left', allowFiltering: true},
                            {fieldName: 'THANG', headerText: 'Tháng trả', textAlign: 'Right', allowFiltering: true},
                            {fieldName: 'TIENSETRA', headerText: 'Tiền trả', textAlign: 'Right', format: 'N0', type: 'number', allowFiltering: true},
                            {fieldName: 'TONGNO', headerText: 'Nợ', textAlign: 'Right', allowFiltering: true, format: 'N0', type: 'number'},
                            {fieldName: 'CONNO', headerText: 'Còn lại', textAlign: 'Right', allowFiltering: true, format: 'N0', type: 'number'},
                            {fieldName: 'ERROR', headerText: 'Trạng thái', textAlign: 'Left', allowFiltering: true},
                            ]"
                          v-bind:dataSource="uploadErrors"
                          :enablePagingServer="false"
                          :showFilter="false"
                          :allowPaging="true"
                          :totalRecords="pageinfoError.totalElement"
                          :pageSize="pageinfoError.maxSize"
                          :pageIndex="pageinfoError.page"
                          :allowExcelExport="true"
                          ref="dsErrors"
                          >
                        </DataGrid>
                      </div>
                  </div>

              </div>
          </div>
      </div>
  </div>
  </ejs-dialog>
</template>

<script>
import Vue from 'vue'
import { mapActions, mapGetters } from 'vuex'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import { Query } from '@syncfusion/ej2-data'
import CommonsAPI from '../../api/Commons'
import GachNoAPI from '../../api/GachNoAPI'
import GachNoTuyenThu from '../../api/GachNoTuyenThu'
import moment from 'moment'
import {cloneArray} from '@/utils/util'
import { currency } from '@/filters/currency'

import {
  LOAI_DV,
  NHOM_HTTT,
  THUNO_KEY
} from '@/const/enums'

import XLSX from 'xlsx'

Vue.use(DialogPlugin)

export default Vue.extend({
  props: {
    nhom_httt: {
      type: Number
    },
    kycuoc: {
      type: String,
      required: false
    },
    donviqlno_id: {
      type: Number,
      default: 0
    },
    dt_thungan: {
      type: Array
    },
    dt_loaitien: {
      type: Array
    },
    thuchien_dongbo: {
      type: Number,
      default: 1
    }
  },
  async created () {
    if (this.danhSachDichVuVienThongGetter === undefined || this.danhSachDichVuVienThongGetter.length === 0) {
      await this.getDanhSachDichVuVienThong()
    }
    this.params.p_dichvu_options = this.danhSachDichVuVienThongGetter
  },
  computed: {
    ...mapGetters('qltnCommon', ['danhSachDichVuVienThongGetter']),
    chk_dichvu () {
      return this.params.p_chk_dichvu === '1'
    },
    chk_loaihinh () {
      return this.params.p_chk_loaihinh === '1'
    },
    p_ngay_ttoan: {
      get () {
        return this.params.p_ngay_ttoan
      },
      set (value) {
        this.params.p_ngay_ttoan = value
      }
    },
    p_thang_dc: {
      get () {
        return this.params.p_thang_dc
      },
      set (value) {
        this.params.p_thang_dc = value
      }
    },
    p_ngay_nganhang: {
      get () {
        return this.params.p_ngay_nganhang
      },
      set (value) {
        this.params.p_ngay_nganhang = value
      }
    },
    p_kycuoc () {
      return moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no
    }
  },
  data () {
    return {
      config: {
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        },
        max_thang_dc: moment(new Date()).subtract(1, 'months').endOf('month').toDate(),
        fieldsDichVu: { text: 'TEN_DVVT', value: 'DICHVUVT_ID' },
        fieldsLoaiHinh: { text: 'TEN_LOAIHINH', value: 'LOAITB_ID' },
        fieldsThuQuy: { text: 'TEN_NV', value: 'MA_NV' },
        fieldsHTTT: { text: 'HINHTHUC', value: 'HTTT_ID' },
        fieldsNganHang: { text: 'TEN_NH', value: 'NGANHANG_ID' }
      },
      customTemplate: function () {
        return { template: Vue.component('footerTemplate', {
          template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="font-weight:bold;margin: 7px 0px 7px 0px;" />`,
          data () { return { data: { data: {} } } }
        })
        }
      },
      p_default_kycuoc: null,
      current_tab: 1,
      params: {
        p_kyhoadon: moment(new Date()).subtract(1, 'months').startOf('month').toDate(),
        p_chuky_no_options: [{CHUKY: '01'}],
        p_chuky_no: '01',
        // p_kycuoc: null,
        p_kieu: '0',
        p_chk_dichvu: '0',
        p_dichvu: null,
        p_dichvu_options: [],
        p_chk_loaihinh: '0',
        p_loaihinh_options: [],
        p_loaihinh: null,
        p_thuquy_options: [],
        p_thuquy: null,
        p_httt_options: [],
        p_httt: null,
        p_ngay_ttoan: new Date(),
        p_thang_dc: moment(new Date()).subtract(1, 'months').endOf('month').toDate(),
        p_chuky_options: [{CHUKY: '01'}],
        p_chuky: '01',
        p_nganhang_options: [],
        p_nganhang: null,
        p_chungtu: null,
        p_seri: '',
        p_so_quyen: 0,
        p_so_seri: 0,
        p_ghichu: null,
        p_ngay_nganhang: null,
        p_dvql: null,
        p_quaythu: null,
        p_request_id: 0,
        p_cotamthu: 0,
        p_file_path: ''
      },
      uploadResults: [],
      pageinfoUpload: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      },
      totalItemsUpload: 0,
      uploadErrors: [],
      pageinfoError: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      },
      totalItemsError: 0,
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      totalTienTra: 0,
      totalConLai: 0,
      totalTienNo: 0,
      totalPhatSinh: 0,
      count: 0,
      thong_bao: '',
      counterInterval: null,
      countDown: 30 // 30 giây
    }
  },
  methods: {
    ...mapActions('qltnCommon', [
      'getDanhSachDichVuVienThong'
    ]),
    openDialog () {
      if (this.kycuoc !== null) {
        // this.p_default_kycuoc = this.kycuoc
        // this.params.p_kycuoc = this.kycuoc
      }
      if (this.params.p_nganhang_options.length <= 0) this.getDSNganHang()
      if (this.params.p_thuquy_options.length <= 0) this.getDSThuQuy()
      if (this.params.p_httt_options.length <= 0) this.getDSHTTT()

      this.countTimer()
      this.$refs.dlgGachNoTheoFile.show()
    },
    closeDialog () {
      this.$refs.dlgGachNoTheoFile.hide()
    },
    onDialogClose () {
      this.clearForm()
      this.clearResult()
      this.thong_bao = ''
      this.$emit('dialogClose')
    },
    onDialogOpen () {
      if (this.kycuoc !== null) {
        this.params.p_kyhoadon = moment(this.kycuoc, 'YYYYMMDD').toDate()
      }
      this.loadThongTinSeri()
    },
    customTienTraAggregate () {
      return currency(this.totalTienTra)
    },
    customTienNoAggregate () {
      return currency(this.totalTienNo)
    },
    customConLaiAggregate () {
      return currency(this.totalConLai)
    },
    customPhatSinhAggregate () {
      return currency(this.totalPhatSinh)
    },
    clearForm () {
      this.params.p_ghichu = ''
      this.params.p_chungtu = ''
      this.params.p_nganhang = null
      document.getElementById('gachno_tb_theofile').value = null
      this.params.p_file_path = ''
    },
    clearResult () {
      this.count = 0
      this.uploadResults = []
      this.pageinfoUpload.totalElement = 0
      this.pageinfoUpload.totalPages = 0
      this.pageinfoUpload.page = 0

      this.totalItemsUpload = 0
      this.uploadErrors = []
      this.pageinfoError.totalElement = 0
      this.pageinfoError.totalPages = 0
      this.pageinfoError.page = 0
      this.totalItemsError = 0
    },
    getKyCuoc () {
      return moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no
    },
    getDSNganHang: function () {
      try {
        CommonsAPI.getDSNganHangGachNo(this.axios).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
              this.params.p_nganhang_options = response.data.data
              // this.params.p_nganhang = response.data.data[0].LOAITIEN_ID
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    async getDSThuQuy () {
      try {
        await GachNoTuyenThu.getDSThuQuy(this.axios).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
              this.params.p_thuquy_options = response.data.data
              this.params.p_thuquy = response.data.data[0].MA_NV
              this.params.p_quaythu = response.data.data[0].DONVI_ID
              this.getDonViQuanLy()
            } else {
              this.params.p_thuquy_options = []
              this.params.p_thuquy = null
              this.params.p_quaythu = null
              this.params.p_donvi = null
            }
          }
        )
      } catch (error) {
        // this.$toast.error(error.data.message_detail)
        console.log(error)
      }
    },
    getDonViQuanLy: function () {
      try {
        let postData = {
          pDonViId: this.params.p_quaythu,
          pDSLoaiDV: LOAI_DV.DONVIQL_TT + ',' + LOAI_DV.DONVIQL_TT_BUUDIEN
        }
        GachNoAPI.getDonViQuanLy(this.axios, postData).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
              this.params.p_donvi = response.data.data[0].DONVI_ID
            } else this.params.p_donvi = null
          }
        )
      } catch (error) {
        this.$toast.error(error.data.message_detail)
      }
    },
    async getDSHTTT () {
      try {
        // await CommonsAPI.getDanhSachHinhThucThanhToan(this.axios, {kieu: 1, nhomHTTT: this.nhom_httt}).then(
        await GachNoAPI.getDanhSachHTTT(this.axios, {kieu: 0, nhomHTTT: this.nhom_httt}).then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' && res.data.data !== undefined && res.data.data.length > 0) {
              this.params.p_httt_options = res.data.data
              if (this.nhom_httt === NHOM_HTTT.GACHNO_NGANHANG) this.params.p_httt = 6    
              else this.params.p_httt = res.data.data[0].HTTT_ID
            } else {
              this.params.p_httt_options = []
              this.params.p_httt = null
            }
          }
        )
      } catch (error) {
        if (error.data !== undefined) this.$toast.error(error.data.message_detail)
        else console.log(error)
      }
    },
    onChangeDichVu (e) {
      try {
        CommonsAPI.getDanhSachLoaiHinhThueBao(this.axios, e.value).then(
          (response) => {
            // console.log(response)
            if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
              this.params.p_loaihinh_options = response.data.data
              this.params.p_loaihinh = response.data.data[0].LOAITB_ID
            } else {
              this.params.p_loaihinh_options = []
              this.params.p_loaihinh = null
            }
          }
        )
      } catch (error) {
        console.log(error)
      }
    },
    /*
    onKyHoaDonChange: function (args) {
      if (args.value === null) {
        this.$toast.error('Vui lòng chọn Kỳ hóa đơn')
        return
      }
      this.updateChuKyHoaDon(moment(args.value).format('YYYYMM'))
    },
    updateChuKyHoaDon: function (pKyHoaDon) {
      this.params.p_chuky_options = []
      this.params.p_chuky = null

      CommonsAPI.getChuKyNoTheoKyCuoc(this.axios, pKyHoaDon)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
              this.params.p_chuky_options = response.data.data
              this.params.p_chuky = this.params.p_chuky_options[0].CHUKY
            }
          }
        )
        .catch((err) => {
          console.log(err)
        })
        .finally(() => {})
    },
    */
    browserFile: function () {
      document.getElementById('gachno_tb_theofile').click()
      return false
    },
    checkFormInput () {
      let errors = []
      if (this.params.p_chk_dichvu === '1' && this.params.p_dichvu === null) {
        errors.push('Chưa chọn dịch vụ.')
      }
      if (this.params.p_chk_loaihinh === '1' && this.params.p_loaihinh === null) {
        errors.push('Chưa chọn loại hình.')
      }
      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }
      return true
    },
    doCancel () {
      this.clearForm()
      this.clearResult()
    },
    async  uploadFile (e) {
      this.uploadResults = []
      this.uploadErrors = []
      this.pageinfoUpload.page = 0
      this.pageinfoUpload.totalElement = 0
      this.pageinfoError.page = 0
      this.pageinfoError.totalElement = 0
      this.totalTienTra = 0
      this.totalTienNo = 0
      this.totalConLai = 0
      this.totalPhatSinh = 0

      if (!this.checkFormInput()) return false

      let files = e.target.files
      let f = files[0]
      // console.log(e)
      if (!files.length) {
        return
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastWarning('File excel không đúng định dạng!')
      }
      this.params.p_file_path = f.name
      var reader = new FileReader()
      const vm = this
      reader.onload = function (e) {
        var data = new Uint8Array(e.target.result)
        var workbook = XLSX.read(data, {type: 'array'})
        let sheetName = workbook.SheetNames[0]
        /* DO SOMETHING WITH workbook HERE */
        // console.log(workbook)
        let worksheet = workbook.Sheets[sheetName]
        let exceldata = XLSX.utils.sheet_to_json(worksheet)
        let exceldata1 = XLSX.utils.sheet_to_json(worksheet, { header: 1 })
        // console.log(exceldata1)

        if (exceldata.length <= 0) {
          vm.$toast.error('Không tìm thấy dữ liệu trong file đã upload.')
          document.getElementById('gachno_tb_theofile').value = null
          return false
        } else if (exceldata.length > 10001) {
          vm.$toast.error('File không được quá 10.000 bản ghi.')
          document.getElementById('gachno_tb_theofile').value = null
          return false
        }
        if (vm.params.p_kieu === '0') {
          if (!exceldata1[0].includes('MA_TT') ||
              !exceldata1[0].includes('MA_TB') ||
              !exceldata1[0].includes('TIEN_TRA') ||
              !exceldata1[0].includes('GHICHU')) {
            vm.$toast.error('File gồm có 4 cột: MA_TT, MA_TB, TIEN_TRA, GHICHU')
            document.getElementById('gachno_tb_theofile').value = null
            return false
          }
        } else {
          if (!exceldata1[0].includes('MA_TT') ||
              !exceldata1[0].includes('MA_TB') ||
              !exceldata1[0].includes('TIEN_TRA') ||
              !exceldata1[0].includes('GHICHU') ||
              !exceldata1[0].includes('THANG_TRA')) {
            vm.$toast.error('File gồm có 5 cột: MA_TT, MA_TB, THANG_TRA, TIEN_TRA, GHICHU ')
            document.getElementById('gachno_tb_theofile').value = null
            return false
          }
        }
        let arrSuccess = []
        let arrFailed = []
        exceldata.forEach((item, index) => {
          // console.log(item)
          let e = []
          let r = item
          if (item.MA_TB === undefined || item.MA_TB === null || item.MA_TB === '') {
            e.push('Mã thuê bao null')
          }
          if (item.MA_TT === undefined || item.MA_TT === null || item.MA_TT === '' || ('' + item.MA_TT.trim()) === '') {
            e.push('Mã thanh toán null')
          }
          if (item.TIEN_TRA === undefined || item.TIEN_TRA === null || item.TIEN_TRA === '') {
            e.push('Tiền trả null')
          } else {
            if (isNaN(parseInt(item.TIEN_TRA))) e.push('Tiền trả không hợp lệ')
          }
          if (vm.params.p_kieu === '1') {
            if (item.THANG_TRA === undefined || item.THANG_TRA === null || item.THANG_TRA.toString().trim() === '') {
              e.push('Tháng trả null')
            } else {
              // if (!moment(item.THANG_TRA.toString().trim(), 'YYYYMMDD', true).isValid()) {
              //   e.push('Tháng trả không đúng định dạng yyyyMMck')
              // }
              if (!moment(item.THANG_TRA, 'YYYYMMDD', true).isValid()) {
                // errors.push( 'MA_TT ' + item.MA_TT + ' có tháng trả không hợp lệ')
                if (!moment(item.THANG_TRA, 'YYYYMM', true).isValid()) {
                  e.push('Tháng trả không hợp lệ')
                }
              }
            }
          }
          if (vm.nhom_httt === NHOM_HTTT.THANHLY_NO) {
            if (item.GHICHU === undefined || item.GHICHU === null || item.GHICHU === '' || ('' + item.GHICHU.trim()) === '') {
              e.push('Ghi chú không hợp lệ')
            }
          }
          if (e.length > 0) {
            r.ERROR = e.join(',')
            arrFailed.push(r)
          } else arrSuccess.push(item)
        })
        // vm.totalItemsUpload = vm.uploadResults.length
        // vm.totalItemsError = vm.uploadErrors.length
        // vm.dsGachNoDanhSach = [...exceldata]
        // vm.dsGachNoDanhSachTotals = vm.dsGachNoDanhSach.length
        // vm.pageinfoUpload.totalElement = vm.uploadResults.length
        // vm.pageinfoError.totalElement = vm.uploadErrors.length
        if (arrFailed.length > 0) {
          vm.uploadErrors = cloneArray(arrFailed)
          vm.pageinfoError.totalElement = vm.uploadErrors.length
          vm.$toast.error('Đã phát sinh lỗi khi upload file.')
          vm.current_tab = 2
        } else {
          arrSuccess = vm.trimUploadData(arrSuccess)

          let postData = {
            pKyCuoc: vm.getKyCuoc(),
            pDS: arrSuccess
          }
          /*
          (async() => {
            vm.loading(true)
            res = await vm.doCheckTongNoTuFile(postData)
            vm.loading(false)
          })()
          */
          vm.loading(true)
          GachNoAPI.getTongNoTuFile(vm.axios, postData)
            .then((res) => {
              if (res.data.error_code === 'BSS-00000000' && res.data.data !== undefined) {
                if (res.data.data.loi_CHI_TIET !== null && res.data.data.loi_CHI_TIET.length > 0) {
                  // const error = JSON.parse(res.data.data.LOI_CHI_TIET)
                  vm.uploadErrors = res.data.data.loi_CHI_TIET
                  vm.pageinfoError.totalElement = vm.uploadErrors.length
                  vm.$toast.error('Đã phát sinh lỗi khi upload file.')
                  vm.current_tab = 2
                } else {
                  vm.uploadResults = res.data.data.danh_SACH
                  vm.pageinfoUpload.totalElement = vm.uploadResults.length
                  vm.current_tab = 1

                  // vm.totalTienTra = vm.uploadResults.reduce((acc, curr) => {
                  //   return acc + Number(curr.TIENSETRA)
                  // }, 0)
                  // vm.totalConLai = vm.uploadResults.reduce((acc, curr) => {
                  //   return acc + Number(curr.CONLAI)
                  // }, 0)
                  // vm.totalTienNo = vm.uploadResults.reduce((acc, curr) => {
                  //   return acc + Number(curr.TONGNO)
                  // }, 0)
                  // vm.totalPhatSinh = vm.uploadResults.reduce((acc, curr) => {
                  //   return acc + Number(curr.PHATSINH)
                  // }, 0)

                  vm.uploadResults.forEach(item => {
                    vm.totalTienTra += Number(item.TIENSETRA)
                    vm.totalConLai += Number(item.CONLAI)
                    vm.totalTienNo += Number(item.TONGNO)
                    vm.totalPhatSinh += Number(item.PHATSINH)
                  })
                }
              }
            })
            .catch(error => {
              if (error.data !== undefined) vm.$toast.error(error.data.message_detail)
              else console.log(error)
            })
            .finally(() => {
              vm.loading(false)
            })
        }
      }
      reader.readAsArrayBuffer(f)
      document.getElementById('gachno_tb_theofile').value = null
    },

    trimUploadData (data) {
      if (data.length <= 0) return []
      let ret = []
      data.forEach((item) => {
        item.MA_TT = ('' + item.MA_TT).trim()
        item.TIEN_TRA = ('' + item.TIEN_TRA).trim()
        item.MA_TB = ('' + item.MA_TB).trim()

        item.GHICHU = item.GHICHU !== undefined && item.GHICHU !== null ? ('' + item.GHICHU).trim() : null

        ret.push(item)
      })

      return ret
    },

    async doCheckTongNoTuFile (postData) {
      try {
        const res = await GachNoAPI.getTongNoTuFile(this.axios, postData)
        const data = await res.data

        if (data.error_code === 'BSS-00000000' && data.data !== undefined) {
          if (data.data.danh_SACH !== null) this.uploadResults = data.data.danh_SACH
          if (data.data.loi_CHI_TIET !== null) {
            const error = JSON.parse(data.data.loi_CHI_TIET)
            this.uploadErrors = cloneArray(error)
            return false
          }
          return true
        }
      } catch (error) {
        if (error.data !== undefined) this.$toast.error(error.data.message_detail)
        else console.log(error)
        return false
      }
    },

    validateData () {
      let errors = []
      if (this.uploadResults.length <= 0) {
        this.$toast.error('Không có số liệu để gạch nợ. Vui lòng upload file số liệu.')
        return false
      }

      if (this.params.p_thuquy === null) {
        errors.push('Chưa chọn thủ quỹ.')
      }
      if (this.params.p_seri === null) {
        errors.push('Chưa nhập Seri.')
      }
      if (this.params.p_httt === null) {
        errors.push('Chưa chọn hình thức thanh toán.')
      }
      if (this.params.p_donvi === null) {
        errors.push('Chưa có đơn vị quản lý.')
      }
      if (this.params.p_quaythu === null) {
        errors.push('Chưa có quầy thu.')
      }

      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }

      return true
    },
    doGachNo () {
      if (!this.validateData()) return false

      let dsTra = []
      let nSoSeri = parseInt(this.params.p_so_seri) + 1

      this.uploadResults.forEach((item, index) => {
        this.params.p_so_seri = nSoSeri + index
        dsTra.push(
          {
            'MA_TT': item.MA_TT,
            'MA_TB': item.MA_TB,
            'KHOANMUCTT_ID': item.KHOANMUCTT_ID !== undefined ? item.KHOANMUCTT_ID : null,
            'THANGTRA': item.THANG_TRA !== undefined ? item.THANG_TRA : null,
            'TIENTRA': item.TIENSETRA,
            'CHUNGTU': item.CHUNGTU,
            'GHICHU': item.GHICHU,
            'SERI': this.params.p_seri,
            'QUYEN': this.params.p_so_quyen,
            'SO_SERI': this.params.p_so_seri
          }
        )
      })

      this.setThongTinSeri({
        p_seri: this.params.p_seri,
        p_so_quyen: this.params.p_so_quyen,
        p_so_seri: this.params.p_so_seri + 1
      })

      let thungan = this.params.p_thuquy_options.filter(x => x.MA_NV === this.params.p_thuquy)

      let postData = {
        'pKyCuoc': this.getKyCuoc(),
        'pMaNVTC': this.$auth.getUserName(),
        'pNgayTT': moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY'),
        'pLoaiTienId': 1,
        'pHTTTId': this.params.p_httt,
        'pLanTra': 1,
        'pGhiChu': this.params.p_ghichu !== null ? this.params.p_ghichu.trim() : null,
        'pChungTu': this.params.p_chungtu !== null ? this.params.p_chungtu.trim() : null,
        'pCoTamThu': 1,
        'pMaTN': this.params.p_thuquy,
        'pDonViQLNoId': this.params.p_donvi,
        'pQuayThuId': this.params.p_quaythu,
        'pThuNganId': thungan[0].NHANVIEN_ID,
        'pNganHangId': this.params.p_nganhang,
        'pNgayNganHang': this.params.p_ngay_nganhang !== null ? moment(this.params.p_ngay_nganhang).format('DD/MM/YYYY') : null,
        'pMayCN': '-',
        'pIPCN': '-',
        'pDSTra': dsTra, // JSON.stringify(dsTra)
        'thucHienDongBo': this.thuchien_dongbo
      }

      this.loading(true)
      this.thong_bao = 'đang xử lý ..'
      GachNoAPI.gachNoDSTienMat(this.axios, postData)
        .then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000') {
              this.params.p_request_id = res.data.data
              this.$toast.success('Đã gửi yêu cầu gạch nợ thành công với ID: ' + res.data.data)
              this.thong_bao = 'Đã gửi yêu cầu gạch nợ ..'

              this.count = 0
              if (postData.pDSTra.length <= 10) this.countDown = 10 // 10s check 1 lần
              this.countTimer()
              this.uploadResults = []
              this.pageinfoUpload.page = 0
              this.pageinfoUpload.totalElement = 0
              this.uploadErrors = []
              this.pageinfoError.page = 0
              this.pageinfoError.totalElement = 0
            }
          }
        ).catch((err) => {
          this.$toast.error(err.data.message_detail)
          this.loading(false)
        })
        .finally(() => {})
    },
    countTimer () {
      if (this.params.p_request_id > 0) {
        this.counterInterval = setInterval(
          function () {
            if (this.params.p_request_id > 0) {
              this.count += 1
              if (this.count % this.countDown === 0) {
                this.checkDSDaGach(this.params.p_request_id)
              }
            }
          }.bind(this), 1000)
      }
    },
    checkDSDaGach (id) {
      GachNoAPI.checkDSDaGach(this.axios, {pId: id})
        .then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' && res.data.data !== undefined && res.data.data.length > 0) {
              let total = res.data.data[0].TONGSO_YEUCAU
              let success = res.data.data[0].TONGSO_THANHCONG
              let failed = res.data.data[0].TONGSO_LOI

              if (res.data.data[0].TRANG_THAI === 3 && res.data.data[0].TONGSO_THANHCONG === res.data.data[0].TONGSO_YEUCAU) {
                // const strMsg = `Gạch nợ thành công ID ${this.params.p_request_id}. Tổng số thành công/ Tổng yêu cầu`
                // this.$toast.success('Gạch nợ thành công. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_YEUCAU + ')')
                let strMsg = `Gạch nợ thành công ID ${this.params.p_request_id}. Tổng yêu cầu : ${total}, tổng thành công : ${success}, tổng lỗi : ${failed}`
                this.$toast.success(strMsg)
                this.params.p_request_id = 0
                clearInterval(this.params.counterInterval)
                this.loading(false)
              } else {
                if (res.data.data[0].TRANG_THAI === 1) {
                  // this.$toast.success('Đang gạch (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_YEUCAU + ')')
                  // const minutes = Math.floor(this.count / 60)
                  // const seconds = this.count % 60
                  // let strMsg = `Đang gạch nợ ID ${this.params.p_request_id}. Tổng yêu cầu ${total} (bản ghi). Thời gian đã thực hiện ${minutes} phút :${seconds} giây`
                  // // this.$toast.success('Đã gạch (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_YEUCAU + '). Thời gian ' + minutes + ':' + seconds)
                  // this.$toast.success(strMsg)
                  // this.params.p_request_id = 0
                  // clearInterval(this.params.counterInterval)
                  // this.loading(false)
                } else {
                  // this.$toast.error('Gạch nợ đã phát sinh lỗi. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_YEUCAU + ')')
                  let strMsg = `Gạch nợ ID ${this.params.p_request_id} đã phát sinh lỗi. Tổng yêu cầu : ${total}, tổng thành công : ${success}, tổng lỗi : ${failed}`
                  this.$toast.error(strMsg)
                  this.dsErrors = []
                  let ds = []
                  res.data.data[0].DS_LOI.forEach((item, index) => {
                    ds.push({ MA_TT: item.MA_TT, ERROR: item.LOI })
                  })

                  // console.log(res.data.data[0].DS_LOI, ds)

                  this.uploadErrors = cloneArray(ds)
                  this.params.p_request_id = 0
                  clearInterval(this.params.counterInterval)
                  this.loading(false)
                  this.current_tab = 2
                }
              }
            }
          }
        )
        .catch(error => {
          if (error.data !== undefined) this.$toast.error('Đã phát sinh lỗi: ' + error.data.message_detail)
          else console.log(error)

          this.params.p_request_id = 0
          clearInterval(this.params.counterInterval)
          this.loading(false)
        })
    },
    doExportExcel () {
      let excelExportProperties = {
        dataSource: []
      }

      if (this.current_tab === 1) { // OK
        if (this.uploadResults.length <= 0) {
          this.$toast.error('Không tìm thấy dữ liệu để xuất file.')
          return false
        }
        excelExportProperties.dataSource = this.uploadResults
        this.$refs.dsUploaded.excelExport(excelExportProperties)
      } else {
        if (this.uploadErrors.length <= 0) {
          this.$toast.error('Không tìm thấy dữ liệu để xuất file.')
          return false
        }
        excelExportProperties.dataSource = this.uploadErrors
        this.$refs.dsErrors.excelExport(excelExportProperties)
      }
    },
    loadThongTinSeri () {
      let s = this.getDataFromStorage(THUNO_KEY.KEY_SERI)
      this.params.p_seri = s === null ? '0' : s
      s = this.getDataFromStorage(THUNO_KEY.KEY_SOQUYEN)
      this.params.p_so_quyen = s === null ? 0 : s
      s = this.getDataFromStorage(THUNO_KEY.KEY_SOSERI)
      this.params.p_so_seri = s === null ? 0 : s
      // console.log(this.params.p_seri)
    },
    setThongTinSeri (data) {
      this.setDataToStorage(THUNO_KEY.KEY_SERI, data.p_seri)
      this.setDataToStorage(THUNO_KEY.KEY_SOQUYEN, data.p_so_quyen)
      this.setDataToStorage(THUNO_KEY.KEY_SOSERI, data.p_so_seri)
    },
    getDataFromStorage (key) {
      try {
        // console.log(localStorage.getItem(key))
        return localStorage.getItem(key)
      } catch (error) {
        // console.log(error)
        return null
      }
    },
    setDataToStorage (key, value) {
      try {
        localStorage.setItem(key, value)
        return true
      } catch (error) {
        return false
      }
    },
    onFilteringDichVu (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_DVVT', 'contains', e.text, true) : query
      e.updateData(this.params.p_dichvu_options, query)
    },
    onFilteringLoaiHinh (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_LOAIHINH', 'contains', e.text, true) : query
      e.updateData(this.params.p_loaihinh_options, query)
    },
    onFilteringThuQuy (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_NV', 'contains', e.text, true) : query
      e.updateData(this.params.p_thuquy_options, query)
    },
    onFilteringHTTT (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('HTTT', 'contains', e.text, true) : query
      e.updateData(this.params.p_httt_options, query)
    },
    onFilteringNganHang (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_NH', 'contains', e.text, true) : query
      e.updateData(this.params.p_nganhang_options, query)
    },
    async onKyHoaDonChange (args) {
      if (args.value === null) {
        this.$toast.error('Vui lòng chọn Kỳ hóa đơn')
        return
      }
      await this.updateChuKyHoaDon(moment(args.value).format('YYYYMM'))
    },
    async updateChuKyHoaDon (pKyHoaDon) {
      // this.loading(true)
      this.p_chuky_no_options = []
      this.p_chuky_no = null

      CommonsAPI.getChuKyNoTheoKyCuoc(this.axios, pKyHoaDon)
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            this.params.p_chuky_no_options = response.data.data
            this.params.p_chuky_no = this.params.p_chuky_no_options[0].CHUKY
          }
        }
        )
        .catch((err) => {
          if (err.data !== undefined) this.$toast.error(err.data.message_detail)
          else console.log(err)
        })
        .finally(() => {})
    },
    onBeforeOpen (args) {
      this.$emit('beforeOpen', args)
    },
    onBeforeClose (args) {
      this.$emit('beforeClose', args)
    },
    queryCellInfo (args) {
      if (args.column.field === 'PHATSINH') {
        if (args.data.TIENSETRA !== args.data.PHATSINH) {
          args.cell.classList.add('grid-phatsinh')
        } else {
          args.cell.classList.remove('grid-phatsinh')
        }
      }
    }
  }
})
</script>
<style>
  .grid-phatsinh {
    background-color: rgb(250, 223, 105);
    font-weight: bold!important;
    color: red!important;
  }
</style>
