<template>
    <ejs-dialog
      :enableResize='true'
      :allowDragging='true'
      :visible='false'
      :isModal="true"
      :animationSettings='animationSettings'
      ref="dlgCNTheoFile"
      :header='labelDialogTitle'
      showCloseIcon=true
      width='1100px'
      height='600'
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
            <div class="pull-left">{{ labelCaption }}</div>
            <div class="pull-right red">{{ labelThoiGian }}</div>
            <div class="clearfix"></div>
          </div>
          <div class="note marb10 red fw6">
            {{ labelCauTruc }}
          </div>
          <div class="row">
            <div class="key w100">Chọn File</div>
            <div class="value">
              <div class="input-more-button">
                <label for="uploadDiaChiCT" class="btn">
                  <span class="-ap icon-more_horiz"></span>
                </label>
                <input id="uploadDiaChiCT" ref="fileInputDiaChiCT" @change="onChangeFile" accept=".xlsx, .xls" type="file" hidden>
                <input type="text" class="form-control" v-model="txtFileName" />
              </div>
            </div>
          </div>
        </div>
        <div class="box-form" v-if="!isLoi">
          <div class="legend-title">{{ lblLabel2 }}</div>
          <div class="table-content">
            <DataGrid
              v-bind:columns="[
                { fieldName: 'MA_TT', headerText: 'Mã thanh toán', allowFiltering: true, allowSorting: false },
                { fieldName: 'DIACHI_CT', headerText: '', allowFiltering: true, allowSorting: false },
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
                {
                  fieldName: 'MA_TT',
                  headerText: 'Mã thanh toán',
                  allowFiltering: true,
                  allowSorting: false,
                  cssClass: 'red',
                },
                {
                  fieldName: 'LOI',
                  headerText: 'Lỗi',
                  allowFiltering: true,
                  allowSorting: false,
                },
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
    labelDialogTitle () {
      return this.config[this.kieuId].title
    },
    labelCaption () {
      return this.config[this.kieuId].caption
    },
    lblLabel2 () {
      return this.config[this.kieuId].label2
    },
    labelCauTruc () {
      return this.config[this.kieuId].cautruc
    }
  },
  async created () {
  },
  mounted () {
  },
  destroyed () {
  },
  data () {
    return {
      f_refresh: false,
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
        .format('YYYYMM')}01`,
      config: {
        1: {
          title: 'Cập nhật địa chỉ chứng từ theo file',
          cautruc: 'Cấu trúc file Excel : MA_TT, DIACHI_CT',
          label2: 'Danh sách thay đổi địa chỉ chứng từ',
          caption: 'Thông tin địa chỉ chứng từ',
          keyword: 'DIACHI_CT',
          headerText: 'Địa chỉ chứng từ'
        },
        2: {
          title: 'Cập nhật mã số thuế theo file',
          cautruc: 'Cấu trúc file Excel : MA_TT, MST',
          label2: 'Danh sách thay đổi mã số thuế',
          caption: 'Thông tin mã số thuế',
          keyword: 'MST',
          headerText: 'Mã số thuế'
        },
        3: {
          title: 'Cập nhật số điện thoại theo file',
          cautruc: 'Cấu trúc file Excel : MA_TT, SDT',
          label2: 'Danh sách thay đổi số điện thoại',
          caption: 'Thông tin số điện thoại',
          keyword: 'SDT',
          headerText: 'Số điện thoại'
        }
      }
    }
  },
  methods: {
    openDialog () {
      this.$refs.dlgCNTheoFile.show()
    },
    closeDialog () {
      this.$refs.dlgCNTheoFile.hide()
    },
    onDialogClose () {
      this.resetData()
    },
    updateColumns () {
      let obj = this.$refs.tabledcCT.$refs.grid.ej2Instances
      let columns = obj.columnModel
      // console.log(columns, this.config[this.kieuId])
      columns[2].headerText = this.config[this.kieuId].headerText
      columns[2].field = this.config[this.kieuId].keyword
      columns[2].foreignKeyField = this.config[this.kieuId].keyword
      obj.refreshColumns()
      this.f_refresh = false
    },
    async onDialogOpen () {
      
      this.updateColumns()
    },
    clearData () {
    },
    resetData () {
      this.gridViewData = []
      this.dsLoi = []
      this.txtFileName = ''
      this.isLoi = false
      this.f_refresh = false
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
      const data = {
        ds: this.gridViewData,
        kieuId: this.kieuId
      }
      this.loading(true)
      try {
        const response = await API.capNhatDSDiaChiCT(this.axios, data)
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
      this.$refs.dlgCapNhatTheoFile.hide()
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
          document.getElementById('uploadDiaChiCT').value = null
          return false
        }
        let exceldata1 = XLSX.utils.sheet_to_json(worksheet, { header: 1 })
        let columns = []

        exceldata1[0].forEach(item => {
          columns.push(item.toUpperCase())
        })

        if (columns.length !== 2) {
          vm.$toast.error('File gồm có 2 cột: MA_TT, ' + vm.config[vm.kieuId].keyword)
          document.getElementById('uploadDiaChiCT').value = null
          return false
        }

        if (!columns.includes('MA_TT') || !columns.includes(vm.config[vm.kieuId].keyword)) {
          vm.$toast.error('File gồm có 2 cột: MA_TT, ' + vm.config[vm.kieuId].keyword)
          document.getElementById('uploadDiaChiCT').value = null
          return false
        }
        let successData = vm.trimUploadData(exceldata)
        const errorData = vm.checkDupplicateKeys(successData, ['MA_TT'])
        if (errorData.length > 0) {
          vm.dsLoi = cloneArray(errorData)
          vm.$toast.error('Đã phát sinh lỗi khi tải file. Xem chi tiết trong danh sách lỗi.')
          vm.isLoi = true
          return false
        }
        vm.gridViewData = [...successData]
        vm.f_refresh = true
      }
      reader.readAsArrayBuffer(f)
      document.getElementById('uploadDiaChiCT').value = null
    },
    trimUploadData (data) {
      if (data.length <= 0) {
        return []
      }
      let ret = []
      data.forEach((item) => {
        item.MA_TT = ('' + item.MA_TT).trim()
        item[this.config[this.kieuId].keyword] = ('' + item[this.config[this.kieuId].keyword]).trim()
        ret.push(item)
      })

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
