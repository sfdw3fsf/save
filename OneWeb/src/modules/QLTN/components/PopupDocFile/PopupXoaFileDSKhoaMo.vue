<!-- Popup đọc file xóa cho Danh sách thuê bao không khóa mở -->
<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgXoaFileDSKhoaMo"
        :header='input.title'
        :animationSettings='animationSettings'
        showCloseIcon=true width='60%'
        :target='target'
        :open="dialogOpen"
        :close="close"
        :isModal="true"
    >
        <div class="">
            <div class="list-actions-top">
                <ul class="list">
                <li>
                    <a href="javascript: void(0)" @click="onReadFile" title="Hiển thị danh sách">
                        <span class="icon design_bullet-list-67 nc-icon-glyph"></span> Danh sách
                    </a>
                </li>
                <li>
                    <a href="javascript: void(0)" @click="onAccept" title="Chấp nhận">
                        <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
                    </a>
                </li>
                <li>
                    <a href="javascript: void(0)" @click="onExcelExport" title="Xuất excel">
                        <span class="icon one-excel"></span> Xuất Excel
                    </a>
                </li>
                <li>
                    <a href="javascript: void(0)" @click="close" title="Thoát">
                        <span class="icon one-export"></span> Thoát
                    </a>
                </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="info-row note text-main">
                        <div class="key w180">
                            <span class="one-alert f20 text-warning"></span> File excel gồm:
                        </div>
                        <div class="value" style="vertical-align: bottom;">
                            <ejs-radiobutton label='THUEBAO_ID, MA_TB' name='default1' :htmlAttributes="{ value: 1 }" v-model="params.inputType"></ejs-radiobutton>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <ejs-radiobutton label='MA_TB' name='default1' checked=true :htmlAttributes="{ value: 2 }" v-model="params.inputType"></ejs-radiobutton>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w180">Dịch vụ</div>
                        <div class="value">
                            <div>
                                    <select2
                                        :options = "params.dichVu_Options"
                                        v-model = "params.dichVu_Selected"
                                    />
                                </div>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w180">Tên file</div>
                        <div class="value">
                        <div class="input-more-button">
                            <label for="xoa-upload" class="btn">
                                <span class="-ap icon-more_horiz"></span>
                                <input ref="fileXoaInput" type="file" id="xoa-upload" style="display: none" @change="onChangeFile"
                                    accept=".xls, .xlsx" @click="$refs.fileXoaInput.value=null" />
                            </label>
                            <input type="text" class="form-control highlight" v-model="params.filePath" @change="onInputText($event.target.value)">
                        </div>
                        </div>
                    </div>
                </div>
                <div class="box-form">
                    <div class="legend-title" :class="[tableTitleClass]" style="font-weight: 600!important">
                        <div class="row">
                            <div class="col-sm-6 col-12">{{ input.title }}</div>
                            <div class="col-sm-6 red tleft col-12">
                                {{ processTime }}
                            </div>
                        </div>
                    </div>
                    <DataGrid3 v-bind:columns="thueBao_grid.fields"
                        v-bind:dataSource="thueBao_grid.items"
                        dataKeyField="MA_TB"
                        :showFilter="true"
                        :allowPaging="true"
                        :enablePagingServer="false"
                        :showColumnCheckbox="true"
                        :enabledSelectFirstRow="false"
                        :allowExcelExport="true"
                        :aggregates="aggregatesColumns"
                        @queryCellInfo="onDSTBQueryCellInfo"
                        @checkBoxChange="checkBoxChange" @rowSelected="dsRowSelected" @rowDeselected="dsRowDeselected" @dataBound="dsDatabound"
                        ref="thueBaoGrid"
                    >
                    </DataGrid3>
                </div>
            </div>
        </div>
    </ejs-dialog>
</template>
<style scoped src="../../MoMayTheoDanhSach/style.scss"></style>
<style>
    .key.w180 {
        width: 180px !important;
    }
</style>
<script>
import xlsx from 'xlsx'
import API from '../../api/DSThueBaoKhongKhoaMoAPI.js'
import { mapActions, mapGetters } from 'vuex'
import moment from 'moment'
import Vue from 'vue'

export default {
  components: { },
  name: 'PopupXoaFileDSKhoaMo',

  props: {
    input: Object
  },

  async created () {
    if (this.danhSachDichVuVienThongGetter == null || this.danhSachDichVuVienThongGetter.length === 0) { await this.getDanhSachDichVuVienThong() }
    this.danhSachDichVuVienThongGetter.forEach(item => {
      this.params.dichVu_Options.push({ id: item.DICHVUVT_ID, text: item.TEN_DVVT })
    })
    if (this.params.dichVu_Options.length > 0) {
      this.params.dichVu_Options.sort(this.compare)
      this.params.dichVu_Selected = this.params.dichVu_Options[0].id
    }

    await this.getDanhSachThamSoMD(0)
    if (this.commons_params.khongChan_NhanHong !== 1) { this.thueBao_grid.fields.shift() }
  },

  computed: {
    ...mapGetters('qltnCommon', ['danhSachDichVuVienThongGetter']),
    aggregatesColumns () {
      return [{ field: 'MA_TB', type: 'Custom', footerTemplate: this.countTemplate, customAggregateFn: this.countMaTT }]
    }
  },

  data () {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      thamSo_Items: [],
      tableTitleClass: 'text-main',
      processTime: '00:00:00',
      params: {
        dichVu_Options: [],
        dichVu_Selected: null,
        filePath: '',
        inputType: '2',
        files: {}
      },
      commons_params: {
        kiemTraQuyen: false,
        keyRDS_ID: 0,
        docFile_DSTB_KhongNhanHong: -1,
        khongChan_NhanHong: -1
      },
      thueBao_grid: {
        items: [],
        fields: [
          {
            fieldName: 'THUEBAO_ID',
            headerText: 'Thuê bao ID',
            allowFiltering: true,
            allowSorting: false,
            width: 120,
            cssClass: 'text-left'
          },
          {
            fieldName: 'MA_TB',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            allowSorting: false,
            width: 120,
            cssClass: 'text-left'
          },
          {
            fieldName: 'TRANGTHAI_TB',
            headerText: 'Trạng thái TB',
            allowFiltering: true,
            allowSorting: false,
            cssClass: 'trangthai-col',
            width: 200
          },
          {
            fieldName: 'TRANGTHAI',
            headerText: 'Trạng thái',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {fieldName: '_CHECK', headerText: 'Chọn', visible: false}
        ]
      },
      countTemplate: function () {
          return {
            template: Vue.component('footerTemplate', {
              template: `<input type="text" class="form-control tright sum-record" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px; margin-left: -50px" />`,
              data () {
                return { data: { data: {} } }
              }
            })
          }
        }
      }
  },
  methods: {
    ...mapActions('qltnCommon', ['getDanhSachDichVuVienThong']),
    countMaTT () {
      let selected = this.thueBao_grid.items.filter(x => x._CHECK === true).length
      return `${selected}/${this.thueBao_grid.items.length}`
    },

    compare (a, b) {
      if (a.text < b.text) { return -1 }
      if (a.text > b.text) { return 1 }
      return 0
    },

    openDialog () {
      this.$refs.dlgXoaFileDSKhoaMo.show()
    },

    close () {
      this.params.filePath = ''
      this.params.inputType = '2'
      this.params.dichVu_Selected = this.params.dichVu_Options[0].id
      this.params.files = {}
      this.thueBao_grid.items = []
      this.tableTitleClass = 'text-main',
      this.processTime = '00:00:00',
      this.$refs.dlgXoaFileDSKhoaMo.hide()
    },

    onDSTBQueryCellInfo (args) {
      if (args.column.field === 'TRANGTHAI') {
        if (args.data.TRANGTHAI !== 'OK') { args.cell.classList.add('hd-khac') } else { args.cell.classList.remove('hd-khac') }
      }
    },

    async onReadFile () {
      if (this.params.filePath.trim() === '') { return this.$toast.error('Chưa chọn file để mở!') }
      try {
        await this.$bvModal.msgBoxConfirm('Bạn muốn mở file?', {
          'title': 'Thông báo',
          'size': 'lg',
          'buttonSize': 'lg',
          'okVariant': 'primary',
          'okTitle': 'Đồng ý',
          'cancelTitle': 'Hủy bỏ',
          'hideHeaderClose': false,
          'centered': true,
          'modal-class': ['f18', 'text-center']
        })
          .then(async (value) => {
            if (value) {
              if (this.params.files !== {}) { await this.onChangeFile(this.params.files) } else { this.$toast.error('Vui lòng chọn lại file!') }
            }
          })
          .catch(err => {
            // An error occurred
            console.log(err)
          })
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
      }
    },

    async onChangeFile (e) {
      let beginTime = new Date()
      this.processTime = '00:00:00'
      this.thueBao_grid.items = []
      this.params.files = e
      const files = e.target.files
      if (!files.length) { return } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) { return this.$toast.error('File excel không đúng định dạng!') }

      const fileReader = new FileReader()
      fileReader.onload = async (ev) => {
        try {
          const data = ev.target.result
          const workbook = xlsx.read(data, { type: 'binary' })
          const wsname = workbook.SheetNames[0]
          const ws = xlsx.utils.sheet_to_json(workbook.Sheets[wsname])

          // Validate input file:
          if (this.params.inputType === '1') {
            if (!ws[0].hasOwnProperty('THUEBAO_ID')) { return this.$toast.error('Tên cột đầu tiên trong file không đúng định dạng: THUEBAO_ID') }
            if (!ws[0].hasOwnProperty('MA_TB')) { return this.$toast.error('Tên cột thứ 2 trong file không đúng định dạng: MA_TB') }
          } else {
            if (!ws[0].hasOwnProperty('MA_TB')) { return this.$toast.error('Tên cột trong file không đúng định dạng: MA_TB') }
          }

          if (ws.length > 0) {
            let dsData = []
            let kieu = 0
            if (this.input.loaiDSId === 5 && this.commons_params.docFile_DSTB_KhongNhanHong === 1) {
              // Code HCM
            } else {
              kieu = this.input.loaiDSId
              ws.forEach(item => {
                let data = {
                  ma_tt: item.MA_TB,
                  giatri: this.params.dichVu_Selected,
                  ghichu: null
                }
                dsData.push(data)
              })

              await this.postLocDanhSachTuFileXoa(dsData, kieu)

              if (this.thueBao_grid.items.length <= 0) {
                return this.$toast.error('Không tìm thấy dữ liệu!')
              } else {
                if (this.thueBao_grid.items.filter(item => item.TRANGTHAI !== 'OK').length > 0) {
                  this.tableTitleClass = 'red'
                } else {
                  this.tableTitleClass = 'text-main'
                }
              }

              let timeSpan = new Date() - beginTime
              let seconds = timeSpan / 1000
              let hours = parseInt(seconds / 3600)
              seconds = seconds % 3600
              let minutes = parseInt(seconds / 60)
              seconds = Math.round(seconds % 60)
              this.processTime = hours.toString().padStart(2, '0') + ':' + minutes.toString().padStart(2, '0') + ':' + seconds.toString().padStart(2, '0')
            }
          }
        } catch (e) {
          console.log(e)
          return this.$toast.error('Kiểm tra dữ liệu file đầu vào!')
        }
      }
      fileReader.readAsBinaryString(files[0])
      this.params.filePath = files[0].name
    },

    async onAccept () {
      let accept = false
      if (this.thueBao_grid.items.length <= 0) { return this.$toast.error('Chưa có dữ liệu để cập nhật!') }
      let selected = this.thueBao_grid.items.filter(x => x._CHECK === true)
      if (selected.length <= 0) { return this.$toast.error('Chưa chọn trong danh sách để cập nhật!') }
      if (selected.filter(item => item.TRANGTHAI !== 'OK').length > 0) { return this.$toast.error('Trạng thái không thỏa mãn điều kiện!') }

      try {
        await this.$bvModal.msgBoxConfirm('Bạn thật sự muốn cập nhật dữ liệu không?', {
          'title': 'Thông báo',
          'size': 'lg',
          'buttonSize': 'lg',
          'okVariant': 'primary',
          'okTitle': 'Đồng ý',
          'cancelTitle': 'Không đồng ý',
          'hideHeaderClose': false,
          'centered': true,
          'modal-class': ['f18', 'text-center']
        })
          .then(async (value) => {
            if (value) {
              if (this.input.loaiDSId === 5 && this.commons_params.docFile_DSTB_KhongNhanHong === 1) {
                // Code HCM
              } else {
                let kieu = this.input.loaiDSId
                let ds = []
                for (let item of selected) {
                  let data = {
                    dichVuVTId: this.params.dichVu_Selected,
                    ghiChu: null,
                    maTB: item.MA_TB,
                    ngayTH: moment(this.input.tuNgay).format('DD/MM/YYYY')
                  }
                  ds.push(data)
                }
                let result = await this.deleteDSKhachHangDacBiet(ds, kieu)
                if (result) {
                  accept = true
                  this.thueBao_grid.items = []
                  this.$toast.success('Cập nhật dữ liệu thành công!')
                }
              }
            }
          })
          .catch(err => {
            // An error occurred
            console.log(err)
          })
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
      }

      if (accept) {
        this.$emit('acceptChange', true)
        this.close()
      }
    },

    onExcelExport () {
      if (this.thueBao_grid.items.length <= 0) { this.$toast.error('Chưa có dữ liệu để xuất excel!') } else {
        let excelExportProperties = {
          fileName: 'XoaFileDSKhoaMo.xlsx',
          dataSource: this.thueBao_grid.items
        }
        this.$refs.thueBaoGrid.excelExport(excelExportProperties)
      }
    },

    dialogOpen () {
    },

    dialogClose () {
    },

    async getDanhSachThamSoMD (id) {
      try {
        this.loading(true)
        this.commons_params.kiemTraQuyen = false
        let response = await API.getDanhSachThamSoMD(this.axios, id)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          response.data.data.forEach(item => {
            if (item.MA_TS === 'DOCFILE_DSTB_KHONGNHANHONG' && item.TEN_TS.trim() === '1') {
              this.commons_params.kiemTraQuyen = true
              this.commons_params.docFile_DSTB_KhongNhanHong = 1
            }
            if (item.MA_TS === 'KHONGCHAN_NHANHONG' && item.TEN_TS.trim() === '1') { this.commons_params.khongChan_NhanHong = 1 }
          })
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    checkAll (f) {
      let temp = [...this.thueBao_grid.items]
      if (f) { // true
        let filter = this.$refs.thueBaoGrid.getCurrentFilter()
        if (filter !== undefined && filter.fieldName !== undefined && filter.value !== '') {
          let checked = this.$refs.thueBaoGrid.filteredDataSource.map(x => x.MA_TB)
          for (let i = 0; i < temp.length; i++) {
            if (checked.includes(temp[i].MA_TB)) {
              temp[i]._CHECK = true
            } else {
              temp[i]._CHECK = false
            }
          }
        } else {
          for (let i = 0; i < temp.length; i++) temp[i]._CHECK = true
        }
      } else {
        for (let i = 0; i < temp.length; i++) temp[i]._CHECK = false
      }
      this.thueBao_grid.items = [...temp]
    },
    checkBoxChange (args) {
      if (args.target.classList.contains('e-checkselectall')) { // click check/uncheck all
        this.checkAll(args.checked)
        this.refreshAgg()
      }
    },
    dsRowSelected (args) {
      // console.log('sel', args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header
        let f = this.thueBao_grid.items.find(x => x.MA_TB === args.data.MA_TB)
        if (f !== undefined) f._CHECK = true
        // this.refreshGrid()
        this.refreshAgg()
      }
    },
    dsRowDeselected (args) {
      // console.log('desel', args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header & paging
        let f = this.thueBao_grid.items.find(x => x.MA_TB === args.data.MA_TB)
        if (f !== undefined) f._CHECK = false
        this.refreshAgg()
      }
    },
    refreshAgg () {
      this.$refs.thueBaoGrid.$refs.grid.ej2Instances.aggregateModule.refresh()
    },
    dsDatabound (args) {
      // console.log('db')
      this.refreshGrid()
    },
    refreshGrid () {
      let obj = this.$refs.thueBaoGrid.$refs.grid.ej2Instances
      if (obj.currentViewData.length > 0) {
        let data = obj.currentViewData
        let selectedCurrentPage = []
        data.forEach((value, index) => {
          if (value._CHECK) {
            selectedCurrentPage.push(index)
          }
        })
        obj.selectRows(selectedCurrentPage)
      }
    },

    /* ds: [{ ma_tt: String, giatri: Number, ghichu: String }] */
    async postLocDanhSachTuFileXoa (ds, kieu) {
      try {
        this.loading(true)
        this.thueBao_grid.items = []
        let apiBody = {
          pKyCuoc: this.input.kyHoaDon,
          pKieuId: kieu,
          pData: ds
        }
        let response = await API.postLocDanhSachTuFileXoa(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.thueBao_grid.items = response.data.data.map(x => ({ ...x, _CHECK: false }))
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.data.message_detail)
      } finally {
        this.loading(false)
      }
    },

    /* ds: [{ dichVuVTId: Number, ghiChu: String, maTB: String, ngayTH: String }] */
    async deleteDSKhachHangDacBiet (ds, kieu) {
      try {
        this.loading(true)
        let apiBody = {
          kieu: kieu,
          kyCuoc: this.input.kyHoaDon,
          dsKHDB: ds
        }
        let response = await API.deleteDSKhachHangDacBiet(this.axios, apiBody)
        if (response.data.error_code === 'BSS-00000000') { return true } else {
          return false
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.data.message_detail)
        return false
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>
