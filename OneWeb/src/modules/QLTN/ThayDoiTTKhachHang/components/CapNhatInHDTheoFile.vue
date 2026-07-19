<template>
    <ejs-dialog
      :enableResize='true'
      :allowDragging='true'
      :visible='false'
      :isModal="true"
      :animationSettings='animationSettings'
      ref="dlgCNInHDTheoFile"
      :header="'Đăng ký in hoá đơn theo file'"
      showCloseIcon=true
      width='1200px'
      height='700'
      :target='target'
      :close="onDialogClose"
      :open="onDialogOpen"
      >
    <div class="modal-content popup-box">
        <div class="popup-top-action">
          <div class="acept item pointer" v-on:click.prevent="doAccept">
              <span class="icon -ap icon-ion-ios-checkmark"></span> Ghi lại
          </div>
        </div>
        <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">
            <div class="pull-left">Thông tin đăng ký in hoá đơn</div>
            <div class="clearfix"></div>
          </div>
          <div class="note marb10 red fw6">
            Hiệu lực tháng sau
          </div>
          <div class="row">
            <div class="col-sm-3 note marb10 red fw6" style="padding-left: 7px!important">
                Hình thức thanh toán
            </div>
            <div class="col-sm-3">
                1 : Tại điểm giao dịch <BR/>
                3: Uỷ nhiệm thu
            </div>
            <div class="col-sm-3">
                2 : Tại địa chỉ thanh toán <BR/>
                4: Uỷ nhiệm chi
            </div>
          </div>
          <div class="note marb10 red fw6">
            Cấu trúc file excel: có 1 sheet, có 4 cột MA_TT, HTTT_ID, MST, STK
          </div>
          <div class="row">
            <div class="key w100">Chọn File</div>
            <div class="value">
              <div class="input-more-button">
                <label for="uploadDangKyInHD" class="btn">
                  <span class="-ap icon-more_horiz"></span>
                </label>
                <input id="uploadDangKyInHD" ref="fileInputDangKyInHD" @change="onChangeFile" accept=".xlsx, .xls" type="file" hidden>
                <input type="text" class="form-control" v-model="txtFileName" />
              </div>
            </div>
          </div>
        </div>
        <div class="box-form" v-if="!isLoi">
          <div class="legend-title">Danh sách đăng ký in hoá đơn</div>
          <div class="table-content">
            <DataGrid
              v-bind:columns="[
                { fieldName: 'STT', headerText: 'STT', allowFiltering: true, allowSorting: false, width: 60 },
                { fieldName: 'MA_TT', headerText: 'Mã thanh toán', allowFiltering: true, allowSorting: false },
                { fieldName: 'HTTT_ID', headerText: 'HTTT (Excel)', allowFiltering: true, allowSorting: false },
                { fieldName: 'HTTT_CU', headerText: 'HTTT (hiện tại)', allowFiltering: true, allowSorting: false },
                { fieldName: 'STK', headerText: 'Số tài khoản', allowFiltering: true, allowSorting: false },
                { fieldName: 'MST', headerText: 'Mã số thuế', allowFiltering: true, allowSorting: false },
                ]"
              v-bind:dataSource="gridViewData"
              :showFilter="true"
              :allowPaging="true"
              :enablePagingServer="false"
              @dataBound="databound"
              @actionComplete="actionComplete"
              ref="tabledcCT"
            >
            </DataGrid>
          </div>
        </div>
        <div class="box-form" v-if="isLoi">
          <div class="legend-title red">Thông tin lỗi từ file Excel</div>
          <div class="table-content">
            <DataGrid
              v-bind:columns="[
                { fieldName: 'STT', headerText: 'STT', allowFiltering: true, allowSorting: false, width: 60 },
                { fieldName: 'MA_TT', headerText: 'Mã thanh toán', allowFiltering: true, allowSorting: false },
                { fieldName: 'HTTT_ID', headerText: 'Hình thức TT', allowFiltering: true, allowSorting: false },
                { fieldName: 'STK', headerText: 'Số tài khoản', allowFiltering: true, allowSorting: false },
                { fieldName: 'MST', headerText: 'Mã số thuế', allowFiltering: true, allowSorting: false },
                { fieldName: 'LOI', headerText: 'Lỗi', allowFiltering: true, allowSorting: false }
              ]"
              v-bind:dataSource="dsLoi"
              :showFilter="true"
              :allowPaging="true"
              :enablePagingServer="false"
              ref="tabledcCTLoi"
            >
            </DataGrid>
          </div>
        </div>
        </div> <!-- end body -->
    </div>
    </ejs-dialog>
  </template>
<script>

import Vue from 'vue'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import XLSX from 'xlsx'
import API from '../../api/ThayDoiTTKH'
import moment from 'moment'
import { cloneArray } from '@/utils/util'

import {
  KTRA_MATT
} from '@/const/enums'

Vue.use(DialogPlugin)
export default Vue.extend({
  components: {
  },
  props: {
    kieuId: {
      type: Number,
      default: 1,
      required: false
    }
  },
  computed: {

  },
  async created () {
  },
  mounted () {
  },
  destroyed () {
  },
  data () {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      labelThoiGian: '00:00:00',
      gridViewData: [],
      txtFileName: '',
      isLoi: false,
      dsLoi: [],
      kieu_id: 0,
      tieuDe: 'Cập nhật theo file',
      vKyCuoc: `${moment(new Date())
        .subtract(1, 'months')
        .endOf('month')
        .format('YYYYMM')}01`
    }
  },
  methods: {
    openDialog () {
      this.$refs.dlgCNInHDTheoFile.show()
    },
    closeDialog () {
      this.$refs.dlgCNInHDTheoFile.hide()
    },
    onDialogClose () {
      this.resetData()
    },
    async onDialogOpen () {
    },
    clearData () {
    },
    resetData () {
      this.gridViewData = []
      this.dsLoi = []
      this.txtFileName = ''
      this.isLoi = false
      document.getElementById('uploadDangKyInHD').value = null
    },
    async doAccept () {
      if (this.isLoi) {
        this.$root.toastError('Dữ liệu đang có lỗi. Mời thử lại!')
        return
      }
      if (this.gridViewData.length <= 0) {
        this.$root.toastError('Chưa có danh sách để cập nhật')
        return
      }

      let postData = {
        ds: []
      }
      this.gridViewData.forEach(item => {
        postData.ds.push({
          maTT: item.MA_TT,
          htttId: item.HTTT_ID,
          mst: item.MST,
          stk: item.STK
        })
      })
      this.loading(true)
      try {
        const response = await API.capNhatHTTTTheoFile(this.axios, postData)
        if (response.data.error_code === 'BSS-00000000') {
          this.resetData()
          this.$root.toastSuccess(
            'Cập nhật dữ liệu lên viễn thông tỉnh thành công!'
          )
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail)
      } finally {
        this.loading(false)
      }

      this.$emit('xacnhan', null)
      this.$refs.dlgCNInHDTheoFile.hide()
    },
    onChangeFile (e) {
      this.txtFileName = e.target.files[0].name
      this.gridViewData = []
      this.dsLoi = []
      this.isLoi = false
      let files = e.target.files
      let f = files[0]
      if (!files.length) {
        return
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        document.getElementById('uploadDangKyInHD').value = null
        return this.$root.toastWarning('File excel không đúng định dạng!')
      }

      var reader = new FileReader()
      const vm = this
      reader.onload = function (e) {
        var data = new Uint8Array(e.target.result)
        var workbook = XLSX.read(data, {type: 'array'})
        let sheetName = workbook.SheetNames[0]
        let worksheet = workbook.Sheets[sheetName]
        let exceldata = XLSX.utils.sheet_to_json(worksheet)

        

        if (exceldata.length <= 0) {
          vm.$toast.error('Không tìm thấy dữ liệu trong file đã upload.')
          document.getElementById('uploadDangKyInHD').value = null
          return false
        }
        let exceldata1 = XLSX.utils.sheet_to_json(worksheet, { header: 1 })
        // console.log(exceldata1)
        let columns = []

        exceldata1[0].forEach(item => {
          columns.push(item.toUpperCase())
        })

        if (columns.length !== 4) {
          vm.$toast.error('Tên cột trong file không đúng định dạng : MA_TT, HTTT_ID, MST, STK!')
          document.getElementById('uploadDangKyInHD').value = null
          return false
        }

        if (!columns.includes('MA_TT') ||
            !columns.includes('HTTT_ID') ||
            !columns.includes('MST') ||
            !columns.includes('STK')) {
          vm.$toast.error('File gồm có 4 cột: MA_TT, HTTT_ID, MST, STK')
          document.getElementById('uploadDangKyInHD').value = null
          return false
        }
        vm.loading(true)
        let successData = vm.trimUploadData(exceldata)
        if (vm.isLoi) {
          document.getElementById('uploadDangKyInHD').value = null
          vm.loading(false)
          return
        }
        const errorData = vm.checkDupplicateKeys(successData, ['MA_TT'])
        if (errorData.length > 0) {
          vm.dsLoi = cloneArray(errorData)
          vm.$toast.error('Đã phát sinh lỗi khi tải file. Xem chi tiết trong danh sách lỗi.')
          vm.isLoi = true
          document.getElementById('uploadDangKyInHD').value = null
          vm.loading(false)
          return false
        }
        vm.checkHTTTTheoFile(successData)
          .then(
            (res) => {
              if (res !== undefined && res.data.error_code === 'BSS-00000000') {
                vm.gridViewData = [...res.data.data]
              }
            })
          .finally(() => {
            vm.loading(false)
          })
        
        // vm.f_refresh = true
      }
      reader.readAsArrayBuffer(f)
      document.getElementById('uploadDangKyInHD').value = null
    },
    async checkHTTTTheoFile (data) {
      let postData = []

      console.log(data)

      data.forEach(item => {
        postData.push({
          MA_TT: item.MA_TT,
          HTTT_ID: item.HTTT_ID,
          MST: item.MST,
          STK: item.STK})
      })
      try {
        const res = await API.kiemtraHTTTTheoFile(this.axios, postData)
        return res
      } catch (error) {
        this.$toast.error(error.data.message_detail)
      }
    },
    trimUploadData (data) {
      if (data.length <= 0) {
        return []
      }
      let ret = []
      let errors = []
      let i = 0
      let j = 0
      data.forEach((item) => {
        i++
        let e = []
        item.MA_TT = ('' + item.MA_TT).trim()
        if (item.MA_TT === '') {
          e.push('MA_TT không để trống')
        }
        item.HTTT_ID = ('' + item.HTTT_ID).trim()
        item.MST = item.MST !== undefined ? ('' + item.MST).trim() : null
        item.STK = item.STK !== undefined ? ('' + item.STK).trim() : null
        item.HTTT_ID = Number(item.HTTT_ID)
        if (!isNaN(item.HTTT_ID)) {
          if (item.HTTT_ID === 3) {
            if (item.MST === '') {
              e.push('MST không để trống')
            }
            if (item.STK === '') {
              e.push('STK không để trống')
            }
          }
        } else {
          e.push('Giá trị HTTT phải là kiểu số')
        }
        
        if (e.length > 0) {
          errors.push({
            STT: ++j,
            MA_TT: item.MA_TT,
            HTTT_ID: item.HTTT_ID,
            MST: item.MST,
            STK: item.STK,
            LOI: e.join(',')
          })
        } else {
          ret.push({
            STT: ++i,
            MA_TT: item.MA_TT,
            HTTT_ID: item.HTTT_ID,
            HTTT_CU: null,
            MST: item.MST,
            STK: item.STK
          })
        }
      }) // end foreach
      if (errors.length > 0) {
        this.isLoi = true
        this.dsLoi = [...errors]
        this.$toast.error('Thông tin lỗi từ file Excel')
        return null
      }
      return ret
    },
    checkDupplicateKeys (data, keys) {
      const arr = cloneArray(data)
      const result = [...arr.reduce((r, o) => {
        if (isNaN(o.used)) {
          o['used'] = 1
          o['LOI'] = 'Mã TT bị trùng'
        }
        const key = this.genKey(keys, o)
        const item = r.get(key) || Object.assign({}, o, {
          used: 0, LOI: 'Mã TT bị trùng'
        })
        item.used += o.used

        return r.set(key, item)
      }, new Map()).values()]
      const f = result.filter(x => x.used > 1)
      // console.log(f)
      return f
    },
    genKey (keys, item) {
      let key = ''
      let i = 0
      for (i = 0; i < keys.length - 1; i++) {
        key += item[keys[i]] + '-'
      }
      key += item[keys[i]]
      return key
    },
    databound () {
      console.log('databound')
      if (this.f_refresh) this.updateColumns()
    },
    actionComplete (args) {
      // console.log(args)
      // this.updateColumns()
    }
  },
  watch: {
  }
})
</script>
