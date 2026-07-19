<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :isModal="true"
    :animationSettings='animationSettings'
    ref="dlgDieuChinhNoFile"
    :header='"Điều chỉnh nợ từ file"'
    showCloseIcon=true
    width='80%'
    height=800
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <input id="dieuchinhno_theofile" ref="dieuchinhno_theofile" @change="uploadFile" type="file" hidden>
      <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" v-on:click.prevent="goPrev()" :class="{ disabled: show }">
                    <span class="icon nc-icon-glyph arrows-1_simple-left"></span>Trở lại
                </a>
            </li>
            <li>
                <a href="#" v-on:click.prevent="goNext()" :class="{ disabled: !show }">
                    <span class="icon nc-icon-glyph arrows-1_simple-right"></span>Tiếp tục
                </a>
            </li>
            <li>
                <a href="#" v-on:click.prevent="getDanhSach()">
                    <span class="icon nc-icon-glyph design_bullet-list-67"></span>Danh sách
                </a>
            </li>
            <li>
                <a href="#"  v-on:click.prevent="doDieuChinh()" :class="{ disabled:  uploadResults.length === 0}">
                    <span class="icon one-file-bookmark"></span>Điều chỉnh
                </a>
            </li>
            <li>
                <a href="#"  v-on:click.prevent="exportExcel()" :class="{ disabled: uploadResults.length === 0 }">
                    <span class="icon one-xlsx-import"></span>Xuất Excel
                </a>
            </li>
        </ul>
    </div>
    <div class="popup-body">
        <div v-show="show" key="1">
          <div class="box-form padt8 padb5">
            <kycuoc2 :value="p_default_kycuoc"></kycuoc2>
          </div>
          <div class="box-move-select-table">
            <div class="box-col" style="width: 340px;">
                <img src="https://st.quantrimang.com/photos/image/2020/05/12/Hinh-Nen-Buon-12.jpg" alt="" style="height: 100%;width: 100%;object-fit: cover;">
            </div>
            <div class="box-col marl10 box-form" style="width: calc(100% - 350px);">
                <div class="legend-title">Thông tin điều chỉnh nợ cước</div>
                <div class="info-row">
                    <div class="key w80">Người ĐC</div>
                    <div class="value">
                      <ejs-dropdownlist :filtering='onFilteringThuNgan' :allowFiltering="true" :dataSource='params.p_thungan_options' :fields="config.fieldsNVThuNgan" v-model = "params.p_thungan" :placeholder="'Chọn thủ quỹ'"></ejs-dropdownlist>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Ngày ĐC</div>
                            <div class="value">
                              <ejs-datepicker
                                v-model="p_ngay_dc"
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
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Tháng ĐC</div>
                            <div class="value">
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
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">CK</div>
                            <div class="value">
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
                <div class="info-row">
                    <div class="key w80">Dịch vụ</div>
                    <div class="value">
                      <ejs-dropdownlist :filtering='onFilteringDichVu' :allowFiltering="true" :dataSource='params.p_dichvu_options' :fields="config.fieldsDichVu" v-model = "params.p_dichvu" :placeholder="'Chọn dịch vụ'"></ejs-dropdownlist>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w80">Hình thức</div>
                    <div class="value">
                      <ejs-dropdownlist :filtering='onFilteringHTTT' :allowFiltering="true" :dataSource='params.p_httt_options' :fields="config.fieldsHTTT" v-model = "params.p_httt" :placeholder="'Chọn hình thức TT'"></ejs-dropdownlist>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w80">Loại tiền</div>
                    <div class="value">
                      <ejs-dropdownlist :allowFiltering="false" :dataSource='params.p_loaitien_options' :fields="config.fieldsLoaiTien" v-model = "params.p_loaitien" :placeholder="'Chọn loại tiền'"></ejs-dropdownlist>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w80">Khoản mục</div>
                    <div class="value">
                      <ejs-dropdownlist :filtering='onFilteringKhoanMuc' :allowFiltering="true" :dataSource='params.p_khoanmuc_options' :fields="config.fieldsKhoanMuc" v-model = "params.p_khoanmuc" :placeholder="'Chọn khoản mục'"></ejs-dropdownlist>
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w80">Lý do ĐC</div>
                    <div class="value">
                        <textarea v-model="params.p_lydo_dchinh" class="form-control" style="height: 140px;resize: none;" id="" cols="30" rows="10"></textarea>
                    </div>
                </div>
            </div>
          </div>
        </div>
        <div v-show="!show" key="2">
          <div class="box-form padt8 padb5">
              <div class="pull-right">
                  <kycuoc1 :value="p_default_kycuoc"></kycuoc1>
              </div>
              <div class="clearfix"></div>
          </div>
          <div class="box-form">
              <div class="row">
                  <div class="col-sm-4 col-12">
                      <div class="info-row">
                          <div class="key w80">Ngày ĐC</div>
                          <div class="key w20">

                          </div>
                          <div class="value">
                            <ejs-datepicker
                              v-model="p_ngay_dc"
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
                  <div class="col-sm-4 col-12">
                      <div class="info-row">
                          <div class="key w80">Tháng ĐC</div>
                          <div class="value">
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
                  <div class="col-sm-4 col-12">
                      <div class="info-row">
                          <div class="key w80">CK</div>
                          <div class="value">
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

              <div class="info-row">
                  <div class="key w80"></div>
                  <div class="key w20">

                  </div>
                  <div class="value">
                      <div class="mart5 marb5">
                          <span class="red fw6">Chọn file dữ liệu Excel</span>
                          <span class="one-alert text-warning f20"></span>
                          <span class="text-main fw6">File excel gồm 3 trường: MA_TT,MA_TB,TIEN (có vat)</span>
                      </div>
                  </div>
              </div>
              <div class="info-row">
                  <div class="key w80">Đường dẫn</div>

                  <div class="key w20">
                      <span class="text-main f20 one-network"></span>
                  </div>

                  <div class="value">
                      <div class="value">
                        <div class="input-more-button">
                            <button class="btn" @click="browserFile">
                                <span class="-ap icon-more_horiz"></span>
                            </button>
                            <input type="text" class="form-control " v-model="params.p_file_path">
                        </div>
                    </div>
                  </div>
              </div>
          </div>
          <div class="box-form">
              <div class="legend-title">
                  <div class="pull-left">
                      Danh sách điều chỉnh
                  </div>
                  <div class="pull-right red">
                    <span class="time">{{count}}</span> <span class="text marl30">Thông báo: {{thong_bao}}</span>
                  </div>
                  <div class="clearfix"></div>
              </div>
              <div class="table-content">
                <DataGrid
                  v-bind:columns="[
                    {fieldName: 'MA_TT', headerText: 'Mã TT', textAlign: 'Right', allowFiltering: true},
                    {fieldName: 'MA_TB', headerText: 'Số máy/Acc', textAlign: 'Left', allowFiltering: true},
                    {fieldName: 'TIEN', headerText: 'Tiền', textAlign: 'Right', format: 'N0', type: 'number', allowFiltering: true},
                    {fieldName: 'TRANGTHAI', headerText: 'Trạng thái', textAlign: 'Left', allowFiltering: true},
                    {fieldName: 'LOI', headerText: 'Lỗi', textAlign: 'Left', allowFiltering: true},
                    ]"
                  v-bind:dataSource="results"
                  :enablePagingServer="false"
                  :showFilter="false"
                  :allowPaging="true"
                  :totalRecords="pageinfo.totalElement"
                  :pageSize="pageinfo.maxSize"
                  :pageIndex="pageinfo.page"
                  :allowExcelExport="true"
                  :key="keyUploadResults"
                  ref="dsUploaded"
                  >
                </DataGrid>
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
import KyCuoc2 from '@/components/KyCuoc2'
import { Query } from '@syncfusion/ej2-data'
import CommonsAPI from '../../api/Commons'
import GachNoAPI from '../../api/GachNoAPI'
// import GachNoTuyenThu from '../../api/GachNoTuyenThu'
import moment from 'moment'

import {
  NHOM_HTTT,
  DichVuVienThong,
  LOAI_DV,
  THUNO_KEY
} from '@/const/enums'

import XLSX from 'xlsx'

import { DialogUtility } from '@syncfusion/ej2-popups'
Vue.use(DialogUtility)
let DialogObj

Vue.use(DialogPlugin)

export default Vue.extend({
  props: {
    kycuoc: {
      type: String,
      required: false
    },
    donviqlno_id: {
      type: Number,
      required: false
    },
    quaythu_id: {
      type: String,
      required: false
    },
    ngay_bd_gach: {
      type: String,
      required: false
    },
    ngay_kt_gach: {
      type: String,
      required: false
    },
    dc_canbang: {
      type: Number,
      required: false,
      default: 0
    },
    dong_bo :{
      type: Number,      
      default: 1
    },
  },
  components: {
    kycuoc1: KyCuoc2,
    kycuoc2: KyCuoc2
  },
  async created () {
    if (this.danhSachDichVuVienThongGetter === undefined || this.danhSachDichVuVienThongGetter.length === 0) {
      await this.getDanhSachDichVuVienThong()
    }
    this.params.p_dichvu_options = this.danhSachDichVuVienThongGetter
    if (this.params.p_dichvu_options.length > 0) this.params.p_dichvu = DichVuVienThong.CO_DINH
  },
  computed: {
    ...mapGetters('qltnCommon', ['danhSachDichVuVienThongGetter']),
    p_ngay_dc: {
      get () {
        return this.params.p_ngay_dc
      },
      set (value) {
        this.params.p_ngay_dc = value
      }
    },
    p_thang_dc: {
      get () {
        return this.params.p_thang_dc
      },
      set (value) {
        this.params.p_thang_dc = value
      }
    }
  },
  data () {
    return {
      show: true,
      p_request_id: 0,
      count: 0,
      thong_bao: '',
      counterInterval: null,
      config: {
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        },
        max_thang_dc: moment(new Date()).subtract(1,'months').endOf('month').toDate(),
        fieldsDichVu: { text: 'TEN_DVVT', value: 'DICHVUVT_ID' },
        fieldsLoaiHinh: { text: 'TEN_LOAIHINH', value: 'LOAITB_ID' },
        fieldsNVThuNgan: { text: 'TEN_NV', value: 'NHANVIEN_ID' },
        fieldsHTTT: { text: 'HINHTHUC', value: 'HTTT_ID' },
        fieldsLoaiTien: { text: 'TENLOAI', value: 'LOAITIEN_ID' },
        fieldsKhoanMuc: { text: 'TEN_KM', value: 'KHOANMUCTT_ID' },
      },
      p_default_kycuoc: null,
      params: {
        p_ngay_dc: new Date(),
        p_thang_dc: moment(new Date()).subtract(1,'months').endOf('month').toDate(),
        p_chuky_options: [{CHUKY: '01'}],
        p_chuky: '01',
        p_kycuoc: null,
        p_thungan_options: [],
        p_thungan: null,
        p_dichvu_options: [],
        p_dichvu: null,
        p_loaitien_options: [],
        p_loaitien: null,
        p_httt_options: [],
        p_httt: null,
        p_lydo_dchinh: null,
        p_khoanmuc: null,
        p_khoanmuc_options: [],
        p_quaythu_id: null,
        p_donviqlno_id: null,
        p_file_path: null,
        p_seri: '',
        p_so_quyen: 0,
        p_so_seri: 0,
        p_canbang: 0
      },
      results: [],
      keyUploadResults: 0,
      pageinfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      },
      uploadResults: [],
      uploadErrors: [],
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' }
    }
  },
  methods: {
    ...mapActions('qltnCommon', [
      'getDanhSachDichVuVienThong'
    ]),
    openDialog () {
      this.$refs.dlgDieuChinhNoFile.show()
    },
    closeDialog () {
      this.$refs.dlgDieuChinhNoFile.hide()
    },
    onDialogClose () {
      this.clearForm()
      this.clearResult()
      this.$emit('dialogClose')
    },
    onDialogOpen () { // call before open Dialog
      this.onInitData()
      this.loadThongTinSeri()
    },
    onInitData () {
      this.params.p_canbang = this.dc_canbang
      if (this.donviqlno_id !== null) {
        this.params.p_donviqlno_id = this.donviqlno_id
      }

      if (this.quaythu_id !== null) {
        this.params.p_quaythu_id = this.quaythu_id
      }

      if (this.kycuoc !== null) {
        this.p_default_kycuoc = this.kycuoc
        this.params.p_kycuoc = this.kycuoc
        if (this.params.p_loaitien_options.length <= 0) this.getDSLoaiTien()
      }
      if (this.params.p_thungan_options.length <= 0) this.getDSThuNgan()
      if (this.params.p_khoanmuc_options.length <= 0) this.getDSKhoanMuc()
      if (this.params.p_httt_options.length <= 0) this.getDSHTTT()
    },
    clearForm () {
    },
    clearResult () {
      this.results = []
      this.uploadResults = []
      this.uploadErrors = []

      this.pageinfo.totalElement = 0
      this.pageinfo.page = 0

      this.thong_bao = ''
    },
    exportExcel () {
      if (this.uploadErrors.length > 0 || this.results.length <= 0) {
        this.$toast.error('Không tìm thấy dữ liệu để xuất file.')
        return false
      }
      let excelExportProperties = {
        dataSource: this.results
      }
      this.$refs.dsUploaded.excelExport(excelExportProperties)
    },
    browserFile: function () {
      document.getElementById('dieuchinhno_theofile').click()
      return false
    },
    uploadFile: function (e) {
      let files = e.target.files
      let f = files[0]

      // clear grid trước khi xử lý
      this.clearResult()

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
        let columns = []
        // console.log(exceldata1)

        if (exceldata.length <= 0) {
          vm.$toast.error('Không tìm thấy dữ liệu trong file đã upload.')
          document.getElementById('dieuchinhno_theofile').value = null
          return false
        } else if (exceldata.length > 5000) {
          vm.$toast.error('File không được quá 5000 bản ghi.')
          document.getElementById('dieuchinhno_theofile').value = null
          return false
        }
        exceldata1[0].forEach(item => {
          columns.push(item.toUpperCase())
        })
        if (!columns.includes('MA_TT') ||
            !columns.includes('MA_TB') ||
            !columns.includes('TIEN')) {
          vm.$toast.error('File gồm có 3 cột: MA_TT, MA_TB, TIEN')
          document.getElementById('dieuchinhno_theofile').value = null
          return false
        }
        exceldata.forEach((item, index) => {
          if (item.MA_TB === undefined || item.MA_TB === null || (item.MA_TB !== null && item.MA_TB.toString().trim() === '')) {
            let r = item
            r.TRANGTHAI = 'Mã thuê bao null'
            vm.uploadErrors.push(r)
          } else if (item.MA_TT === undefined || item.MA_TT === null || (item.MA_TT !== null && item.MA_TT.toString().trim() === '')) {
            let r = item
            r.TRANGTHAI = 'Mã thanh toán null'
            vm.uploadErrors.push(r)
          } else if (item.TIEN === undefined || item.TIEN === null || (item.TIEN !== null && item.TIEN === '')) {
            let r = item
            r.TRANGTHAI = 'Tiền trả null'
            vm.uploadErrors.push(r)
          } else {
            let n = parseInt(item.TIEN)
            if (isNaN(n)) {
              let r = item
              r.TRANGTHAI = 'Tiền không hợp lệ'
              vm.uploadErrors.push(r)
            } else {
              item.MA_TB = ('' + item.MA_TB).trim()
              item.MA_TT = ('' + item.MA_TT).trim()
              vm.uploadResults.push(item)
            }
            // console.log(vm.uploadResults)
          }
        }) // end of forEach

        vm.keyUploadResults++

        // vm.dsGachNoDanhSach = [...exceldata]
        // vm.dsGachNoDanhSachTotals = vm.dsGachNoDanhSach.length
        if (vm.uploadErrors.length > 0) {
          vm.results = [...vm.uploadErrors]
          vm.pageinfo.totalElement = vm.uploadErrors.length
          vm.thong_bao = 'Danh sách thuê bao không thỏa mãn'
          document.getElementById('dieuchinhno_theofile').value = null
        } else {
          vm.results = [...vm.uploadResults]
          vm.pageinfo.totalElement = vm.uploadResults.length
        }
      }
      reader.readAsArrayBuffer(f)
    },
    countTimer () {
      if (this.params.p_request_id > 0) {
        this.counterInterval = setInterval(
          function () {
            if (this.params.p_request_id > 0) {
              this.count += 1
              if (this.count % 10 === 0) {
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
              if (res.data.data[0].TRANG_THAI === 3) {
                // this.$toast.success('Gạch nợ thành công. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_MA_GACHNO + ')')
                // this.thong_bao = 'Gạch nợ thành công. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_MA_GACHNO + ')'
                if (res.data.data[0].TONGSO_THANHCONG === res.data.data[0].TONGSO_YEUCAU) {
                  this.$toast.success('Gạch nợ thành công. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_YEUCAU + ')')
                  this.thong_bao = 'Gạch nợ thành công. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_YEUCAU + ')'
                } else {
                  this.$toast.error('Đã phát sinh lỗi khi gạch nợ. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_YEUCAU + ')')
                  this.thong_bao = 'Đã gạch xong. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_YEUCAU + ')'
                }
                this.params.p_request_id = 0
                clearInterval(this.counterInterval)
                if (res.data.data[0].TONGSO_LOI > 0) {
                  this.results = res.data.data[0].DS_LOI
                  this.pageinfo.page = 0
                  this.pageinfo.totalElement = res.data.data[0].DS_LOI.length
                }
                document.getElementById('dieuchinhno_theofile').value = null
                this.loading(false)
                // clearInterval(this.params.refreshIntervalId)
                // this.params.refreshIntervalId = null
              } else if (res.data.data[0].TRANG_THAI === 2) {
                // this.$toast.error('Đã gạch xong. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_MA_GACHNO + ') Tổng số lỗi ' + res.data.data[0].TONGSO_LOI)
                // this.thong_bao = 'Đã gạch xong. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_MA_GACHNO + ') Tổng số lỗi ' + res.data.data[0].TONGSO_LOI
                this.$toast.error('Đã gạch xong. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_YEUCAU + ') Tổng số lỗi ' + res.data.data[0].TONGSO_LOI)
                this.thong_bao = 'Đã gạch xong. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_YEUCAU + ') Tổng số lỗi ' + res.data.data[0].TONGSO_LOI
                this.params.p_request_id = 0
                clearInterval(this.counterInterval)
                if (res.data.data[0].TONGSO_LOI > 0) {
                  this.results = res.data.data[0].DS_LOI
                  this.pageinfo.page = 0
                  this.pageinfo.totalElement = res.data.data[0].DS_LOI.length
                }
                document.getElementById('dieuchinhno_theofile').value = null
                this.loading(false)
              } else {
                // this.params.p_request_id = 0
                // clearInterval(this.counterInterval)
                // this.thong_bao = 'Đã gạch (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_MA_GACHNO + ')'
                // document.getElementById('dieuchinhno_theofile').value = null  
                // this.loading(false)
              }
            } else {
              this.params.p_request_id = 0
              clearInterval(this.counterInterval)
              this.thong_bao = 'Đã gạch (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_MA_GACHNO + ')'
              document.getElementById('dieuchinhno_theofile').value = null
              this.loading(false)
            }
          }
        )
    },
    get_thungan () {
      let ret = this.params.p_thungan_options.filter(x => x.NHANVIEN_ID === this.params.p_thungan)
      if (ret.length > 0) return ret[0]
      return null
    },

    confirmDieuChinh () {
      DialogObj.hide()

      let thungan = this.get_thungan()
      let dsTra = []
      let soSeri = parseInt(this.params.p_so_seri)

      this.results.forEach((item, index) => {
        this.params.p_so_seri = soSeri + index
        dsTra.push(
          {
            'MA_TT': item.MA_TT,
            'MA_TB': item.MA_TB,
            'TIENTRA': item.TIEN,
            'THANGTRA': moment(this.params.p_thang_dc).format('YYYYMM') + '01',
            'KHOANMUCTT_ID': this.params.p_khoanmuc,
            'SERI': '0', // this.params.p_seri,
            'QUYEN': this.params.p_so_quyen,
            'SO_SERI': this.params.p_so_seri,
            'GHICHU': this.params.p_lydo_dchinh
          }
        )
      })

      this.setThongTinSeri({
        p_seri: this.params.p_seri,
        p_so_quyen: this.params.p_so_quyen,
        p_so_seri: this.params.p_so_seri + 1
      })

      let postData = {
        'pKyCuoc': moment(this.params.p_ngay_dc).subtract(1, 'months').endOf('month').format('YYYYMM') + '01',
        'pCoTamThu': 1,
        'pDCCanBang': this.params.p_canbang,
        'pMaNVTC': this.$auth.getMaNhanVien(),
        'pNgayTT': moment(this.params.p_ngay_dc).format('DD/MM/YYYY'),
        'pLoaiTienId': this.params.p_loaitien,
        'pHTTTId': this.params.p_httt,
        'pLanTra': 1,
        'pGhiChu': ('' + this.params.p_lydo_dchinh).trim(),
        'pChungTu': null,
        'pMaTN': thungan.MA_NV,
        'pDonViQLNoId': this.params.p_donviqlno_id,
        'pQuayThuId': this.params.p_quaythu_id,
        'pThuNganId': this.params.p_thungan,
        'pNganHangId': null,
        'pNgayNganHang': null,
        'pMayCN': '-',
        'pIPCN': '-',
        'pDSTra': dsTra,
        'thucHienDongBo':this.dong_bo
      }
      this.loading(true)
      GachNoAPI.dieuchinhNoDanhSach(this.axios, postData)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000') {
            if (res.data.data.length > 0) {
              let errors = []
              res.data.data.forEach((item) => {
                errors.push({MA_TB: item.MA_TB, TRANGTHAI: item.LOI})
              })
              this.$toast.error('Đã phát sinh lỗi dữ liệu tải lên. Vui lòng kiểm tra lại.')
              this.thong_bao = 'Lỗi dữ liệu'
              this.uploadErrors = [...errors]
              this.results = [...errors]
              this.pageinfo.page = 0
              this.pageinfo.totalElement = errors.length
              this.loading(false)
            } else {
              this.params.p_request_id = res.data.data
              this.$toast.success('Đã gửi yêu cầu gạch nợ thành công với ID: ' + res.data.data)

              this.count = 0
              this.countTimer()

              this.clearResult()
              this.thong_bao = 'Đã gửi yêu cầu gạch nợ ..'
            }
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
          this.loading(false)
        })
        .finally(() => {

        })
    },
    doDieuChinh () {
      if (this.uploadErrors.length > 0) {
        this.$toast.error('Đang có lỗi về dữ liệu điều chỉnh nợ')
        return false
      }

      if (this.results.length <= 0) {
        this.$toast.error('Chưa có dữ liệu để điều chỉnh nợ')
        return false
      }

      if (this.params.p_donviqlno_id === null || this.params.p_donviqlno_id === 0) {
        this.$toast.error('Không lấy được đơn vị theo quầy thu')
        return false
      }

      DialogObj = DialogUtility.confirm({
        title: 'Thông báo',
        content: "<div style='padding:10px'>Bạn chắc chắn muốn thực hiện điều chỉnh ?</div>",
        okButton: { text: 'Đồng ý', click: this.confirmDieuChinh },
        cancelButton: { text: 'Hủy', click: function () { DialogObj.hide(); return false } },
        showCloseIcon: true,
        closeOnEscape: true,
        animationSettings: { effect: 'Zoom' }
      })
    },
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
            if (response.data.error_code === 'BSS-00000000' &&
                response.data.data !== undefined &&
                response.data.data.length > 0) {
              this.params.p_chuky_options = response.data.data
              this.params.p_chuky = this.params.p_chuky_options[0].CHUKY
            }
          }
        )
        .catch(() => {})
        .finally(() => {
        // this.loading(false)
        })
    },
    goPrev () {
      this.show = true
    },
    goNext () {
      let errors = []

      if (this.params.p_thungan === null) {
        errors.push('Hãy chọn người điều chỉnh!')
      }

      if (this.params.p_thang_dc === null) {
        errors.push('Chưa có thông tin về chu kỳ!')
      }

      if (this.params.p_loaitien === null) {
        errors.push('Chưa có thông tin về loại tiền!')
      }

      if (this.params.p_khoanmuc === null) {
        errors.push('Chưa có thông tin về khoản mục điều chỉnh!')
      }

      if (this.params.p_lydo_dchinh === null || ('' + this.params.p_lydo_dchinh).trim() === '') {
        errors.push('Bạn chưa nhập lý do điều chỉnh.')
      }

      // if (this.params.p_dichvu === DichVuVienThong.DI_DONG) {
      //   errors.push('Không được phép điều chỉnh nợ di động !')
      // }
      /*
      let d1 = moment(new Date()).startOf('day')
      let d2 = moment(this.ngay_kt_gach, 'DD/MM/YYYY').toDate()

      let t = d2.diff(d1, 'days')

      if(t <= 3)
      {

      }
*/
      let d1 = moment(this.ngay_bd_gach, 'DD/MM/YYYY').toDate()
      let d2 = moment(this.ngay_kt_gach, 'DD/MM/YYYY').toDate()

      let current = new moment()
      let ngayDC = new moment(this.params.p_ngay_dc).startOf('day')

      // tạm thời đóng
      /*

      if(current.isAfter(d2) || current.isBefore(d1))
      {
        errors.push("Ngày hiện tại không thuộc chu kỳ nợ " + this.kycuoc)
      }
      */

      // console.log(ngayDC, d1, d2)

      if (ngayDC.isAfter(d2) || ngayDC.isBefore(d1)) {
        errors.push('Ngày thanh toán không thuộc chu kỳ nợ ' + this.kycuoc + '. Bạn không được phép gạch nợ!')
      }

      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return
      }

      this.show = false
      // this.show = 2
    },
    getDSThuNgan: function () {
      try {
        GachNoAPI.getDSThuNgan(this.axios).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined) {
              this.params.p_thungan_options = response.data.data
              this.params.p_thungan = response.data.data[0].NHANVIEN_ID
              this.params.p_quaythu_id = response.data.data[0].DONVI_ID
              this.getDonViQuanLy()
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    getDSLoaiTien: function () {
      try {
        GachNoAPI.getDSLoaiTien(this.axios, {pKyCuoc: this.params.p_kycuoc}).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined) {
              this.params.p_loaitien_options = response.data.data
              this.params.p_loaitien = response.data.data[0].LOAITIEN_ID
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    getDSHTTT: function () {
      try {
        // CommonsAPI.getDanhSachHinhThucThanhToan(this.axios, {kieu: kieu, nhomHTTT: nhom}).then(
        GachNoAPI.getDanhSachHTTT(this.axios, {kieu: 0, nhomHTTT: NHOM_HTTT.DIEU_CHINH_NO}).then(

          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined) {
              this.params.p_httt_options = response.data.data
              this.params.p_httt = response.data.data[0].HTTT_ID
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    getDSKhoanMuc: function () {
      try {
        CommonsAPI.getDanhSachKhoanMucGachNo(this.axios).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined) {
              this.params.p_khoanmuc_options = response.data.data
              this.params.p_khoanmuc = response.data.data[0].KHOANMUCTT_ID
            } else this.$toast.error(response.data.message)
          }
        )
      } catch (err) {
        console.log(err)
      }
    },
    getDonViQuanLy: function () {
      try {
        let postData = {
          pDonViId: this.params.p_quaythu_id,
          pDSLoaiDV: LOAI_DV.DONVIQL_TT + ',' + LOAI_DV.DONVIQL_TT_BUUDIEN
        }
        GachNoAPI.getDonViQuanLy(this.axios, postData).then(
          (response) => {
            /// console.log(response)
            if (response.data.error_code === 'BSS-00000000' &&
                response.data.data !== undefined) {
              this.params.p_donviqlno_id = response.data.data[0].DONVI_ID
              this.params.p_donvi_quanly_ten = response.data.data[0].TEN_DV

              if (this.params.p_donviqlno_id === '0' &&
                  this.params.p_nhom_httt !== NHOM_HTTT.DIEU_CHINH_NO) {
                DialogObj = DialogUtility.alert({
                  title: 'Thông báo',
                  content: "<div style='padding:10px'>User của bạn không được phép thao tác trong chức năng này</div>",
                  okButton: { text: ' OK ', click: function () { this.$router.push('/')} },
                  showCloseIcon: true,
                  closeOnEscape: true,
                  animationSettings: { effect: 'Zoom' }
                })
                return false
              }
            } else this.$toast.error(response.data.message)
          }
        )
      } catch (error) {
        this.$toast.error(error)
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
    onFilteringThuNgan (e) {
      var query = new Query()
      let keyword = e.text.trim()
      query = (e.text !== '') ? query.where('TEN_NV', 'contains', keyword, true) : query
      e.updateData(this.params.p_thungan_options, query)
    },
    onFilteringKhoanMuc (e) {
      var query = new Query()
      let keyword = e.text.trim()
      query = (e.text !== '') ? query.where('TEN_KM', 'contains', keyword, true) : query
      e.updateData(this.params.p_khoanmuc_options, query)
    },
    onFilteringHTTT (e) {
      var query = new Query()
      let keyword = e.text.trim()
      query = (e.text !== '') ? query.where('HINHTHUC', 'contains', keyword, true) : query
      e.updateData(this.params.p_httt_options, query)
    },
    onFilteringDichVu (e) {
      var query = new Query()
      let keyword = e.text.trim()
      query = (e.text !== '') ? query.where('TEN_DVVT', 'contains', keyword, true) : query
      e.updateData(this.params.p_dichvu_options, query)
    }
  },
  watch: {
    kycuoc: {
      handler: function (newValue, oldValue) {
        this.p_default_kycuoc = newValue
      }
    }
  }
})
</script>
