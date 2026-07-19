<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li v-if="tsbtnFileMau.visible" @click="tsbtnFileMau_Clicked" :class="{ disabledInput: !tsbtnFileMau.enabled }">
          <a> <span class="icon one-file-plus"></span>{{ tsbtnFileMau.text }} </a>
        </li>
        <li v-if="tsbtnNapLai.visible" @click="tsbtnNapLai_Clicked" :class="{ disabledInput: !tsbtnNapLai.enabled }">
          <a> <span class="icon one-file-plus"></span>{{ tsbtnNapLai.text }} </a>
        </li>
        <li v-if="tsbtnThucThi.visible" @click="tsbtnThucThi_Clicked" :class="{ disabledInput: !tsbtnThucThi.enabled }">
          <a> <span class="icon one-file-plus"></span>{{ tsbtnThucThi.text }} </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="title-bg-main mart10">
          <div class="legend-title">
            <div class="pull-left">
              Chọn file
            </div>
            <div class="clearfix"></div>
          </div>
        </div>
        <div class="row">
          <div>
            <div class="info-row" style="margin: 0 0 10px 0;">
              <div class="key w100">
                Đường dẫn
              </div>
              <div class="value">
                <div style="position: relative; display: flex; align-items: center; cursor: pointer;">
                  <input v-if="txtFilePath.visible" readonly :disabled="!txtFilePath.enabled" v-model="txtFilePath.value" type="text" class="form-control bold" @click="$refs.files.click()" />
                  <span class="-ap icon-more_horiz" style="position: absolute; right: 10px;" @click="$refs.files.click()"></span>
                </div>
              </div>
              <input
                style="display: none;"
                type="file"
                accept="audio/*, video/*, image/* ,application/pdf, application/vnd.ms-excel, application/vnd.ms-excel.sheet.macroEnabled.1, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, text/xml"
                multiple="multiple"
                class="file"
                ref="files"
                id="upload-file"
                @change="fileHandler"
              />
            </div>
          </div>
          <div class="col-sm-12 col-12 mt-3">
            <div class="info-row">
              <div class="key w100" style="display: flex;">
                <div><b>* Ghi chú</b></div>
              </div>
              <div class="value">
                <ul>
                  <li>Tải về file mẫu click <u @click="tsbtnFileMau_Clicked" style="color: blue; cursor: pointer;">vào đây</u></li>
                  <li>Chương trình chỉ đọc dữ liệu từ sheet có tên "YCKS"</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">
          <div class="pull-left">
            Các phiếu khảo sát
          </div>
          <div class="pull-right">
            <a v-if="tsbtnXuatExcel.visible" href="#" class="link" :class="{ disabledInput: !tsbtnXuatExcel.enabled }" @click="tsbtnXuatExcel_Clicked"> <span class="icon one-excel"></span> {{ tsbtnXuatExcel.text }} </a>
          </div>
          <div class="clearfix"></div>
        </div>
        <div class="row">
          <DataGrid
            :columns="gridDanhSach.cols"
            :dataSource="gridDanhSach.list"
            @selectedRowChanged="gridDanhSach_selectedRowChanged"
            @selectedItemsChanged="gridDanhSach_selectedItemsChanged"
            :showColumnCheckbox="false"
            :showFilter="true"
            :allowPaging="true"
            :enablePagingServer="false"
            :selectionSettings="{ enableToggle: false, type: 'Multiple', checkboxOnly: true }"
          />
        </div>
      </div>
    </div>
    <DiaChiBCModal ref="diaChiModal" :tinh_bc="tinh_tc" :data="dataDiaChi" @accept="onAcceptDiaChi" />
  </div>
</template>
<script>
import XLSX from 'xlsx'
import moment from 'moment'
import DiaChiBCModal from '@/modules/contract/setup/SurveyRequestCrossSale/DiaChiBC/DiaChiBCModal.vue'
import LoaiHopDong from '@/const/enums/LoaiHopDong'
import KieuLapDat from '@/const/enums/KieuLapDat'
import TrangThaiHD from '@/const/enums/TrangThaiHD'
import LOAI_DV from '@/const/enums/LOAI_DV'
import { createObjectHDKH, createObjectHDTB, createObjectHDTB_ADSL, createObjectHD_BANCHEO, createObjectHDTB_BANCHEO } from './Utils'
import api from './api'

export default {
  components: {
    DiaChiBCModal
  },
  name: 'frmChungTu_TBao',
  props: {
    // kieu: {
    //   type: [String, Number],
    //   default: false,
    //   required: false
    // },
    // chungtu_id: {
    //   type: [String, Number],
    //   default: false,
    //   required: false
    // }
  },
  computed: {},
  data() {
    return {
      tinh_tc: null,
      tsbtnFileMau: { enabled: true, visible: true, text: 'File mẫu' },
      tsbtnNapLai: { enabled: true, visible: true, text: 'Nạp lại' },
      tsbtnThucThi: { enabled: true, visible: true, text: 'Thực thi' },
      tsbtnXuatExcel: { enabled: true, visible: true, text: 'Xuất Excel' },
      txtFilePath: { visible: true, enabled: true, value: null },
      gridDanhSach: {
        cols: [
          { width: 120, headerText: '', template: this.gridDanhSachStatusTemplate(this), allowFiltering: false, allowSorting: false },
          { width: 100, headerText: 'Mã KH', template: this.dynamicTextTemplate(this, 'MA_KH'), visible: true, allowFiltering: true },
          { width: 80, headerText: 'Tỉnh TC', template: this.dynamicTextTemplate(this, 'TINH'), visible: true, allowFiltering: true },
          { width: 100, headerText: 'Loại hình TB', template: this.dynamicTextTemplate(this, 'LH_TB'), visible: true, allowFiltering: true },
          { headerText: 'Tên thuê bao', template: this.dynamicTextTemplate(this, 'TEN_TB'), visible: true, allowFiltering: true },
          { headerText: 'Điạ chỉ TB', template: this.dynamicTextTemplate(this, 'DIACHI_TB'), visible: true, allowFiltering: true },
          { headerText: 'Địa chỉ LĐ', template: this.dynamicTextTemplate(this, 'DIACHI_LD'), visible: true, allowFiltering: true },
          { width: 100, headerText: 'Điện thoại', template: this.dynamicTextTemplate(this, 'SO_DT'), visible: true, allowFiltering: true },
          { width: 80, headerText: 'Số lượng', template: this.dynamicTextTemplate(this, 'SOLUONG'), visible: true, allowFiltering: true },
          { width: 120, headerText: 'Ghi chú', template: this.dynamicTextTemplate(this, 'GHICHU'), visible: true, allowFiltering: true },
          { width: 120, headerText: 'Chọn địa chỉ', template: this.gridDanhSachChonDiaChiTemplate(this), allowFiltering: false, allowSorting: false }
        ],
        list: [],
        selectedOriginList: [],
        originList: [],
        selected: [],
        value: null
      },
      dsHDBANCHEO: [],
      dsHDTB_KV: [],
      dsHDTB_KV_tmp: [],
      dsDiaChi_BanCheo: [],
      dsHDTBBANCHEO: [],
      dsHDBCMGWAN: [],
      dsHDBC_ADSL: [],
      dsHDBCCD: [],
      dsDK_DVGT_BC: [],
      dsHDBC_TSL: [],
      dsHDBCIMS: [],
      dsHDBC_CNTT: [],
      quytrinh_tc_tn: 0,
      huonggiao_tc_tn: 0,
      quytrinh_tc_tn: 0,
      xhuonggiao_tc_tn: 0,
      tinh: null,
      loaihd_id: LoaiHopDong.KHAOSATDATMOI,
      hdkh_id: null,
      hdtb_id: null,
      diachikh_id: null,
      tinhkh_id: 0,
      quankh_id: 0,
      phuongkh_id: 0,
      phokh_id: 0,
      apkh_id: 0,
      khukh_id: 0,
      sonhakh: '',
      diachikh: '',
      huonggiaotn_id: null,
      quytrinh_id: null,
      diachitb_id: null,
      diachild_id: null,
      dataDiaChi: {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: '',
        diachi: ''
      },
      selectedIndex: 0
    }
  },
  watch: {
    'gridDanhSach.list'(val) {
      // console.log('watcher this.gridVatTu.list')
      // console.log(val)
      if (val.filter((e) => e.ID_GEN == null || e.ID_GEN == '').length) {
        this.gridDanhSach.list = this.gridDanhSach.list.map((e) => {
          e.ID_GEN = this.uuidv4()
          return e
        })
      }
      this.gridDanhSach.originList = []
      for (let item of this.gridDanhSach.list) {
        let obj = {}
        for (let key in item) {
          if (key == 'status') {
            obj[key] = item[key].message.length > 0 ? 'data_check' : 'checked'
          } else if (key == 'ID_GEN') {
            obj[key] = item[key]
          } else if (key == 'DONG_BO') {
            obj[key] = item[key].value
          } else {
            obj[key] = item[key].value
          }
        }
        this.gridDanhSach.originList.push(obj)
      }
    }
  },
  methods: {
    uuidv4() {
      return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) => (c ^ (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))).toString(16))
    },
    gridDanhSachStatusTemplate(parent) {
      return function () {
        return {
          template: {
            template: `
            <ejs-tooltip ref="tooltip" opensOn='Hover' :content='tooltipContent'>
              <span :class="icon" :style="{ color: colorText }"></span><span> {{this.text}}</span>
            </ejs-tooltip>`,
            data() {
              return {
                tooltipContent: '',
                text: '',
                data: {},
                icon: '',
                colorText: ''
              }
            },
            computed: {},
            async mounted() {
              console.log('gridDanhSachStatusTemplate')
              console.log(this.data)

              if (this.data.status.message.length == 0) {
                this.icon = 'act -ap  icon-tick f30'
                if (this.data?.DONG_BO?.value == true) {
                  this.colorText = 'blue'
                  this.text = 'Thành công'
                } else {
                  this.colorText = 'green'
                  this.text = 'Sẵn sàng'
                }
              } else {
                this.icon = 'text-warning one-alert f20'
                if (this.data?.DONG_BO?.value == false) {
                  this.text = 'Lỗi'
                  this.colorText = 'red'
                } else {
                  this.text = 'KT dữ liệu'
                  this.colorText = 'orange'
                }
                this.tooltipContent = this.data.status.message.join('<br/>')
              }
            },
            methods: {}
          }
        }
      }
    },
    dynamicTextTemplate(parent, keyField) {
      return function () {
        return {
          template: {
            template: `<span :class="icon" v-bind:style="{ color: colorText }">{{this.text}}</span>`,
            data() {
              return {
                data: {},
                text: '',
                icon: '',
                colorText: ''
              }
            },
            computed: {},
            async mounted() {
              this.text = this.data[keyField] ? this.data[keyField].value : ''
            },
            methods: {}
          }
        }
      }
    },
    gridDanhSachChonDiaChiTemplate(parent) {
      return function () {
        return {
          template: {
            template: `<div><button class="btn btn-primary" @click="tsbtn_Clicked" style="width: 100%">...</button></div>`,
            data() {
              return {
                data: {},
                icon: '',
                colorText: ''
              }
            },
            computed: {},
            async mounted() {},
            methods: {
              tsbtn_Clicked() {
                console.log('tsbtn_Clicked')

                parent.onOpenPopupChonDiaChi(this.data)
              }
            }
          }
        }
      }
    },
    async gridDanhSach_selectedRowChanged(e) {
      console.log('gridDanhSach_selectedRowChanged')
      console.log(e)
      this.gridDanhSach.value = e
    },

    async gridDanhSach_selectedItemsChanged(e) {
      console.log('gridDanhSach_selectedItemsChanged')
      console.log(e)
      this.gridDanhSach.selected = e
    },
    async getKey(keyname) {
      try {
        let response = await api.get_keys(this.axios, { keyname: keyname })
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return Number(response.data.data)
        } else {
          return 0
        }
      } catch (e) {
        return 0
      }
    },
    async sinh_magd(loaihd_id) {
      try {
        let response = await api.sinh_magd(this.axios, { loaihd_id: loaihd_id })
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return ''
        }
      } catch (e) {
        return ''
      }
    },
    async tsbtnFileMau_Clicked() {
      window.location.href = '/static/files/FileMau_Import_YCKS.xlsx'
    },
    async tsbtnNapLai_Clicked() {
      this.$refs.files.click()
    },
    async tsbtnThucThi_Clicked() {
      // console.log('tsbtnThucThi_Clicked')
      // console.log(this.gridDanhSach.list)

      // return
      this.gridDanhSach.selectedOriginList = []
      for (let item of this.gridDanhSach.list) {
        let obj = {}
        for (let key in item) {
          if (key == 'status') {
            obj[key] = item[key].message.length > 0 ? 'data_check' : 'checked'
          } else if (key == 'ID_GEN') {
            obj[key] = item[key]
          } else if (key == 'DONG_BO') {
            obj[key] = item[key].value
          } else {
            obj[key] = item[key].value
          }
        }
        this.gridDanhSach.selectedOriginList.push(obj)
      }
      console.log(this.gridDanhSach.selectedOriginList)

      for (let item of this.gridDanhSach.selectedOriginList) {
        console.log(item.DIACHI_LD)
        if (['', undefined].includes(item.DIACHI_TB) || ['', undefined].includes(item.DIACHI_LD)) {
          this.$toast.warning('Hãy chọn địa chỉ thuê bao và địa chỉ lắp đặt cho tất cả các thuê bao.')
          return
        }
      }
      console.log('qua day roi')
      let count = 0
      this.loading(true)
      for (let i = 0; i < this.gridDanhSach.selectedOriginList.length; i++) {
        console.log('qua day roi - 1')
        // try {
        if (this.gridDanhSach.selectedOriginList[i].status == 'checked' && this.gridDanhSach.selectedOriginList[i].DONG_BO != true) {
          this.dsHDKH = []
          this.dsHDTB = []
          this.dsDiaChi = []
          this.dsDiaChiHDKH = []
          this.dsHDTBDV = []
          this.dsHDTB_KV_tmp = []
          this.dsHDBANCHEO = []
          this.dsHDTBBANCHEO = []
          this.dsHDBCMGWAN = []
          this.dsHDBC_ADSL = []
          this.dsHDBCCD = []
          this.dsDK_DVGT_BC = []
          this.dsHDBC_TSL = []
          this.dsHDBCIMS = []
          this.dsDiaChi_BanCheo = []
          this.dsHDBC_CNTT = []

          await this.SaveData(i)
          // this.SaveData(i)
          count++
        }
        // } catch (e) {}
      }
      this.loading(false)
      this.formatList()
      this.gridDanhSach.list = this.gridDanhSach.list.map((e) => e)
      console.log('reset')
      console.log(this.gridDanhSach.list)
      this.$toast.success(`Đã đồng bộ: ${count} bản ghi!`)
    },
    async tsbtnXuatExcel_Clicked() {
      let colsExport = [
        { fieldName: 'MA_KH', headerText: 'Mã KH' },
        { fieldName: 'TINH', headerText: 'Tỉnh TC' },
        { fieldName: 'LHTB_ID', headerText: 'Loại hình TB' },
        { fieldName: 'TEN_TB', headerText: 'Tên thuê bao' },
        { fieldName: 'DIACHI_TB', headerText: 'Điạ chỉ TB' },
        { fieldName: 'DIACHI_LD', headerText: 'Địa chỉ LĐ' },
        { fieldName: 'SO_DT', headerText: 'Điện thoại' },
        { fieldName: 'SOLUONG', headerText: 'Số lượng' }
      ]
      // this.reformatList()
      this.exportExcelByCols(this.gridDanhSach.originList, colsExport)
    },
    async fileHandler() {
      let fileSelected = this.$refs.files.files[0]
      let formData = new FormData()
      for (let file of this.$refs.files.files) {
        formData.append('files', file)
      }
      if (this.$refs.files.files.length) {
        this.txtFilePath.value = fileSelected.name
      }
      let columns
      const reader = new FileReader()
      reader.onload = async (e) => {
        /* Parse data */
        const bstr = e.target.result
        const wb = XLSX.read(bstr, { type: 'binary' })
        /* Get first worksheet */
        const wsname = wb.SheetNames[0]
        const ws = wb.Sheets[wsname]
        /* Convert array of arrays */
        const data = XLSX.utils.sheet_to_json(ws)
        columns = data[0]

        this.tmpDSTB_Excel = []
        if (wsname != 'YCKS') {
          this.$toast.error('Không tìm thấy sheet dữ liệu yêu cầu. Sheet dữ liệu danh sách thuê bao phải đặt tên là "YCKS" ')
          return
        }
        let isValid = this.tmpDSTB_Excel.length > 0
        this.tmpDSTB_Excel = []
        let dataList = []
        for (let item of data) {
          let data = {}
          for (let key in item) {
            data[key] = {
              value: item[key],
              message: ''
            }
          }
          dataList.push(data)
        }
        this.tmpDSTB_Excel = dataList
        console.log('input')
        console.log(this.tmpDSTB_Excel)
        let loaiHinhList = await api.lay_ds_loaihinhtb(this.axios, {})
        if (loaiHinhList.data.data) {
          loaiHinhList = loaiHinhList.data.data
        } else {
          loaiHinhList = []
        }
        let tinhList = await api.lay_ds_tinh(this.axios, {})
        if (tinhList.data.data) {
          tinhList = tinhList.data.data
        } else {
          tinhList = []
        }
        for (let dr of this.tmpDSTB_Excel) {
          let loaihinhtb_id = dr.LHTB_ID.value
          let tinh = dr.TINH_ID.value
          let ma_kh = dr.MA_KH?.value
          let dicMapValue = {}
          if (loaihinhtb_id != null || loaihinhtb_id != '') {
            if (!dicMapValue.hasOwnProperty('LHTB_' + loaihinhtb_id)) {
              // let rs = cab.GET_DATA_SQL("select loaitb_id, loaihinh_tb, dichvuvt_id from {?DB2}.loaihinh_tb where loaitb_id =:vloaihinhtb_id", "vloaihinhtb_id", loaihinhtb_id);
              let rs = loaiHinhList.filter((e) => e.loaitb_id == loaihinhtb_id)
              if (rs != null && rs.length > 0) {
                dr.LHTB_ID = { value: rs[0]['loaitb_id'] }
                dr.LH_TB = { value: rs[0]['loaihinh_tb'] }
                dr.DVVT_ID = { value: rs[0]['dichvuvt_id'] }
              } else {
                dr.LHTB_ID = { value: null }
                dr.LH_TB = { value: null }
                dr.DVVT_ID = { value: null }
                // dr['LH_TB'] = 'Loại hình thuê bao không tồn tại hoặc loại hình thuê bao không được hỗ trợ.\n'
                // dr.LHTB_ID.message = 'Loại hình thuê bao không tồn tại hoặc loại hình thuê bao không được hỗ trợ.'
                dr.LH_TB = { value: null, message: 'Loại hình thuê bao không tồn tại hoặc loại hình thuê bao không được hỗ trợ.' }
                dr.validate = false
              }
              dicMapValue['LHTB_' + loaihinhtb_id] = dr.LHTB_ID.value
              dicMapValue['DVVT_' + loaihinhtb_id] = dr.DVVT_ID.value
            } else {
              dr.LHTB_ID.value = dicMapValue['LHTB_' + loaihinhtb_id]
              dr.DVVT_ID.value = dicMapValue['DVVT_' + loaihinhtb_id]
            }
          } else {
            // dr.LHTB_ID = { value: null, message: 'Chưa nhập loại hình thuê bao. Hãy nhập lại.' }
            dr.LH_TB = { value: null, message: 'Chưa nhập loại hình thuê bao. Hãy nhập lại.' }
            dr.validate = false
          }

          if (tinh != null || tinh != '') {
            if (!dicMapValue.hasOwnProperty('TINH_' + tinh)) {
              let rs = tinhList.filter((e) => e.tinh_id == tinh)
              if (rs != null && rs.length > 0) {
                dr['TINH_ID'].value = rs[0]['tinh_id']
                dr['TINH'] = { value: rs[0]['tentinh'] }
              } else {
                dr['TINH_ID'].value = null
                dr['TINH'] = { value: null, message: 'Nhập sai dữ liệu tỉnh vui lòng thử lại.' }
                dr.validate = false
              }
              dicMapValue['TINH_' + tinh] = dr['TINH']
            } else {
              dr['TINH'].value = dicMapValue['TINH_' + tinh]
            }
          } else {
            dr['TINH'].message = 'Chưa nhập Tỉnh ID. Hãy nhập lại.'
            dr.validate = false
          }
          if (ma_kh != null || ma_kh != '') {
            if (!dicMapValue.hasOwnProperty('KH_' + ma_kh)) {
              let rs = await api.lay_tt_khdn_theo_makh_v2(this.axios, { ma_kh: ma_kh })
              if (rs.data.data) {
                rs = rs.data.data
              } else {
                rs = []
              }
              console.log('MAKHA - dr')
              console.log(dr)
              console.log([null, ''].includes(dr['SO_DT'].value))
              if (rs != null && rs.length > 0) {
                dr['MA_KH'].value = rs[0]['ma_kh']
                if ([null, ''].includes(dr['TEN_TB'].value)) dr['TEN_TB'].value = rs[0]['full_name']
                if ([null, ''].includes(dr['SO_DT'].value)) dr['SO_DT'].value = rs[0]['phone']
              } else {
                dr['MA_KH'] = { value: null }
                dr['TEN_TB'] = { value: null, message: 'Nhập sai mã khách hàng vui lòng thử lại.' }
                dr.validate = false
              }
              dicMapValue['KH_' + tinh] = dr['TEN_TB'].value
            } else {
              dr['TEN_TB'].value = dicMapValue['KH_' + tinh]
            }
          } else {
            dr['MA_KH'].message = 'Chưa nhập mã khách hàng. Hãy nhập lại'
            dr.validate = false
          }

          if (dr['SO_DT'] == '') {
            dr['SO_DT'].message = 'Chưa nhập số điện thoại. Hãy nhập lại'
            dr.validate = false
          }
          // else if (!IsNumber(dr["SO_DT"]))
          // {
          //     dr.SetColumnError("SO_DT", "Số điện thoại chưa đúng định dạng. Hãy nhập lại");
          // }

          if (dr['SOLUONG'].value == '') {
            dr['SOLUONG'].value = 1
          } else {
            try {
              dr['SOLUONG'].value = dr['SOLUONG'].value
            } catch {
              dr['SOLUONG'].message = 'Nhập sai định dạng số lượng. Hãy nhập lại'
              dr.validate = false
            }
          }

          // dr.RowError = "";
          // let index = 1;
          // foreach (DataColumn col in dr.Table.Columns)
          // {
          //     let colError = dr.GetColumnError(col);
          //     if (!string.IsNullOrEmpty(colError))
          //     {
          //         dr.RowError += (index++) + ". " + colError + (colError.EndsWith(".") ? "" : ".") + "\r\n";
          //     }
          // }
        }
        console.log('output')
        console.log(this.tmpDSTB_Excel)

        let listOutput = []

        this.gridDanhSach.list = this.tmpDSTB_Excel
        this.formatList()

        // if (isValid) this.btnThucThi.enabled = true
        // else this.btnThucThi.enabled = false
      }
      reader.readAsBinaryString(fileSelected)
      // this.$refs.files.reset()
      this.$refs.files.value = null
    },
    formatList() {
      for (let item of this.gridDanhSach.list) {
        let messages = []
        for (let key in item) {
          if (item[key].message != null && item[key].message != '') {
            messages.push(item[key].message)
          }
        }
        item.status = {
          message: messages
        }
      }
    },
    reformatList() {
      for (let item of this.gridDanhSach.list) {
        let obj = {}
        for (let key in item) {
          if (key == 'status') {
            obj[key] = item[key].message.length > 0 ? 'data_check' : 'checked'
          } else if (key == 'ID_GEN') {
            obj[key] = item[key]
          } else if (key == 'DONG_BO') {
            obj[key] = item[key].value
          } else {
            obj[key] = item[key].value
          }
        }
        this.gridDanhSach.originList.push(obj)
      }
    },
    async SaveData(RowIndex) {
      console.log('SaveData')
      // if (!this.gridDanhSach.list[RowIndex].validate) return false
      var rs = await this.Do_SaveData(RowIndex)
      // if (string.IsNullOrEmpty(rs)) {
      //   SetSuccess(RowIndex, 'Thành công!')
      //   return true
      // } else {
      //   SetError(RowIndex, rs)
      //   return false
      // }
    },
    async Do_SaveData(RowIndex) {
      let row = this.gridDanhSach.selectedOriginList[RowIndex]
      console.log('Do_SaveData')
      console.log(RowIndex)
      console.log(row)
      // try {
      this.tinh = row['TINH_ID']

      let rs = (
        await api.layhuonggiao_tiepnhan_bancheo_v2(this.axios, {
          tinhthicong_id: this.tinh,
          vloaihd_id: LoaiHopDong.KHAOSATDATMOI,
          vkieuld_id: KieuLapDat.KHAOSATDATMOI
        })
      ).data.data

      if (rs.length <= 0) {
        return 'Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý'
      }
      this.quytrinh_tc_tn = 0
      this.huonggiao_tc_tn = 0
      this.quytrinh_tc_tn = rs[0].quytrinh_id
      this.huonggiao_tc_tn = rs[0].huonggiao_id
      // } catch (ex) {
      //   return 'Lỗi kiểm tra dữ liệu:\r\n' + ex
      // }
      // try {
      await this.TaoDuLieu(row)

      let kq = await api.sp_themmoi_hopdong_ks(this.axios, {
        js_hd_khachhang: JSON.stringify(this.dsHDKH),
        js_hd_thuebao: JSON.stringify(this.dsHDTB),
        js_diachi: JSON.stringify(this.dsDiaChi),
        js_diachi_hdkh: JSON.stringify(this.dsDiaChiHDKH),
        js_diachi_hdtb: JSON.stringify(this.dsDiaChiHDTB),
        js_hdtb_dv: JSON.stringify(this.dsHDTBDV),
        js_hdtb_kv: JSON.stringify(this.dsHDTB_KV_tmp),
        huonggiao_id: JSON.stringify(this.huonggiaotn_id)
      })
      console.log('sp_themmoi_hopdong_ks')
      console.log(kq.data)

      let gridRow = this.gridDanhSach.list.find((e) => row.ID_GEN == e.ID_GEN)
      if (kq.data.error_code == 'BSS-00000000') {
        let kq1 = await api.fn_ins_update_hd_datmoi_bancheo_v2(this.axios, {
          p_themmoi: 1,
          p_ds_para: JSON.stringify({
            HD_BANCHEO: this.dsHDBANCHEO,
            HDTB_BANCHEO: this.dsHDTBBANCHEO,
            HDBC_MGWAN: this.dsHDBCMGWAN,
            HDBC_ADSL: this.dsHDBC_ADSL,
            HDBC_CD: this.dsHDBCCD,
            DANGKY_DVGT_BC: this.dsDK_DVGT_BC,
            HDBC_TSL: this.dsHDBC_TSL,
            HDBC_IMS: this.dsHDBCIMS,
            DIACHI_BANCHEO: this.dsDiaChi_BanCheo,
            HDBC_CNTT: this.dsHDBC_CNTT
          }),
          p_tt_nd: JSON.stringify({
            DONVI_ID: this.$root.token.getDonViID(),
            NHANVIEN_ID: this.$root.token.getNhanVienID(),
            NGUOI_CN: this.$root.token.getUserName(),
            // MAY_CN: this.$root.token.getMachine() != null ? this.$root.token.getMachine() : '',
            MAY_CN: 'EMPTY',
            // IP_CN: this.$root.token.getIP()
            IP_CN: ''
          })
        })
        if (kq1.data.error_code == 'BSS-00000000') {
          gridRow.DONG_BO = { value: true, message: `` }
        } else {
          gridRow.DONG_BO = { value: false, message: `${kq.data.message_detail}` }
        }
      } else {
        gridRow.DONG_BO = { value: false, message: `${kq.data.message_detail}` }
      }
      // wtf
      // new HopDong().themmoi_HDBANCHEO_LAPMOI(dsHDBANCHEO, dsHDTBBANCHEO, dsHDBCMGWAN, dsHDBC_ADSL, dsHDBCCD, dsDK_DVGT_BC, dsHDBC_TSL, dsHDBCIMS, dsDiaChi_BanCheo, dsHDBC_CNTT)
      // } catch (ex) {
      //   if (ex.Message.StartsWith('ORA-20105')) {
      //     return ex.Message.Substring(11, ex.Message.Length - 11)
      //   }

      //   return 'Lỗi ghi dữ liệu:\r\n' + ex
      // }

      return null
    },

    async TaoDuLieu(row) {
      await this.TaoDuLieu_HDKH(row)
      await this.TaoDuLieu_HDTB(row)
      await this.TaoDuLieu_HDTBDV(row)
      await this.TaoDuLieu_HDTB_ADSL(row)
      await this.TaoDuLieu_DiaChi(row, 4)
      await this.TaoDuLieu_DiaChi_BanCheo(row, 4)
      await this.TaoDuLieu_HDBANCHEO(row)
      await this.CapNhat_HDTB_ID_HDTB_KV()
    },
    async TaoDuLieu_HDKH(row) {
      console.log('TaoDuLieu_HDKH')
      console.log(row)
      this.dsHDKH = []

      let ds = await api.lay_tt_khdn_theo_makh_v2(this.axios, { ma_kh: row.MA_KH })
      if (ds.data.data) {
        ds = ds.data.data
      } else {
        ds = []
      }
      var object = createObjectHDKH()
      let kdd = await this.getKey('HD_KHACHHANG')
      this.hdkh_id = kdd
      var maGD = ''
      maGD = await this.sinh_magd(this.loaihd_id)
      object.HDKH_ID = kdd
      object.MA_GD = maGD

      object.MA_KH = row.MA_KH
      object.TEN_KH = ds[0]?.full_name
      object.DIACHI_KH = ds[0]?.addr

      if (ds[0]['ngay_thanh_lap'] != '') object.NGAY_SN = ds[0]['ngay_thanh_lap']

      if (ds[0]['loai_gt'] == '2' || ds[0]['loai_gt'] == '1') {
        object.LOAIGT_ID = 3
        if (ds[0]['ngay_thanh_lap'] != '') {
          object.NGAYCAP = ds[0]['ngay_thanh_lap']
        }
      }

      object.NOICAP = ''
      object.DONVI_ID = this.$root.token.getDonViID() != null ? this.$root.token.getDonViID() : ''
      object.NHANVIEN_ID = this.$root.token.getNhanVienID() != null ? this.$root.token.getNhanVienID() : ''
      object.KHLON_ID = 1
      object.LOAIHD_ID = this.loaihd_id
      object.NGUOI_CN = this.$root.token.getUserName() != null ? this.$root.token.getUserName() : ''
      object.NGAY_CN = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      // object.MAY_CN = this.$root.token.getMachine() != null ? this.$root.token.getMachine() : ''
      object.MAY_CN = 'EMPTY'
      object.CTV_ID = this.$root.token.getNhanVienID()
      object.NGAYLAP_HD = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      object.NGAY_YC = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      object.SO_DT = row.SO_DT
      // object.GHICHU = row.GHICHU
      object.QUOCTICH_ID = 1
      object.NGANHNGHE_ID = 999

      object.TNC1_ID = 999
      object.TNC2_ID = 99911
      object.TNC3_ID = 999111

      let copyObject = this.convertToUpcaseKey(ds)
      let output = this.copyObjectKey(object, copyObject[0], ['SO_DT', 'GHICHU'])
      object = output

      console.log(object)
      this.dsHDKH = []
      this.dsHDKH.push(object)
      console.log(this.dsHDKH)
    },
    async TaoDuLieu_HDTB(row) {
      this.dsHDTB = []
      console.log('TaoDuLieu_HDTB')
      var object = createObjectHDTB()
      let hdtb_key = await this.getKey('HD_THUEBAO')
      this.hdtb_id = hdtb_key
      object.HDTB_ID = hdtb_key
      object.HDKH_ID = this.hdkh_id
      if (this.thuebao_id > 0) {
        object.THUEBAO_ID = this.thuebao_id
      }

      object.TEN_TB = row['TEN_TB']
      object.DIACHI_TB = row['DIACHI_TB']
      object.KIEULD_ID = KieuLapDat.KHAOSATDATMOI
      object.LOAITB_ID = row['LHTB_ID']
      object.DONVI_ID = 899
      object.DOITUONG_ID = 341
      object.TTHD_ID = TrangThaiHD.MOI
      object.DICHVUVT_ID = row['DVVT_ID']
      object.KIEU_TB = '0'
      object.NGUOI_CN = this.$root.token.getUserName()
      object.NGAY_CN = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      // object.MAY_CN = this.$root.token.getMachine() != null ? this.$root.token.getMachine() : ''
      object.MAY_CN = 'EMPTY'
      object.GHICHU = row.GHICHU

      if (row['SOLUONG'] != '') object.SOLUONG = row['SOLUONG']
      else object.SOLUONG = 0
      object.DIACHI_LD = row['DIACHI_LD']

      let dset = (
        await api.layhuonggiao_tiepnhan_bancheo_v2(this.axios, {
          tinhthicong_id: this.tinh,
          vloaihd_id: this.loaihd_id,
          vkieuld_id: KieuLapDat.KHAOSATDATMOI
        })
      ).data.data
      if (dset.length > 0) {
        this.huonggiaotn_id = dset[0]['huonggiao_id']
        this.quytrinh_id = dset[0]['quytrinh_id']
      }
      object.QUYTRINH_ID = this.quytrinh_id
      this.dsHDTB.push(object)
    },
    async TaoDuLieu_HDTBDV(row) {
      this.dsHDTBDV = []
      var object = {}
      object.HDTB_ID = this.hdtb_id
      object.LOAIDV_ID = this.loaihd_id == LoaiHopDong.TIEPNHANHD ? 2 : LOAI_DV.TRAM_VT
      object.DONVI_ID = 899
      object.KIEUDV_ID = 2
      this.dsHDTBDV.push(object)
    },
    async TaoDuLieu_HDTB_ADSL(row) {
      this.dsHDTB_ADSL = []
      var object = createObjectHDTB_ADSL()
      object.HDTB_ID = this.hdtb_id
      object.CULY = 0
      this.dsHDTB_ADSL.push(object)
    },
    async TaoDuLieu_DiaChi(row, kieu) {
      this.dsDiaChi = []
      this.dsDiaChiHDKH = []
      this.dsDiaChiHDTB = []

      //
      if (kieu == 1 || kieu == 4) {
        let ds = await api.lay_tt_khdn_theo_makh_v2(this.axios, { ma_kh: row.MA_KH })
        if (ds.data.data) {
          ds = ds.data.data
        } else {
          ds = []
        }
        if (ds.length > 0) {
          this.tinhkh_id = ds[0]['tinh_id']
          this.quankh_id = ds[0]['quan_id']
          if (ds[0]['phuong_id'] != '') this.phuongkh_id = ds[0]['phuong_id']
          else this.phuongkh_id = 0
          if (ds[0]['pho_id'] != '') this.phokh_id = ds[0]['pho_id']
          else this.phokh_id = 0
          if (ds[0]['ap_id'] != '') this.apkh_id = ds[0]['ap_id']
          else apkh_id = 0
          if (ds[0]['khu_id'] != '') this.khukh_id = ds[0]['khu_id']
          else this.khukh_id = 0
          this.sonhakh = ds[0]['sonha']
        }

        let addrKey = await this.getKey('DIACHI')
        this.diachikh_id = addrKey
        let rowDiaChiKH = {}
        rowDiaChiKH.DIACHI_ID = addrKey
        if (![0, undefined].includes(this.tinhkh_id)) {
          rowDiaChiKH.TINH_ID = this.tinhkh_id
        } else {
          rowDiaChiKH.TINH_ID = 99 // tinh khong xác định
        }
        if (![0, undefined].includes(this.quankh_id)) rowDiaChiKH.QUAN_ID = this.quankh_id
        else rowDiaChiKH.QUAN_ID = 99999 // quan khong xác định
        if (![0, undefined].includes(this.phuongkh_id)) rowDiaChiKH.PHUONG_ID = this.phuongkh_id
        else rowDiaChiKH.PHUONG_ID = 0
        if (![0, undefined].includes(this.phokh_id)) rowDiaChiKH.PHO_ID = this.phokh_id
        else rowDiaChiKH.PHO_ID = 0
        if (![0, undefined].includes(this.apkh_id)) rowDiaChiKH.AP_ID = this.apkh_id
        else rowDiaChiKH.AP_ID = 0
        if (![0, undefined].includes(this.khukh_id)) rowDiaChiKH.KHU_ID = this.khukh_id
        else rowDiaChiKH.KHU_ID = 0
        rowDiaChiKH.SONHA = this.sonhakh
        rowDiaChiKH.DIACHI = this.diachikh
        this.dsDiaChi.push(rowDiaChiKH)

        let rowDiaChiHDKH = {}
        rowDiaChiHDKH.DIACHI_ID = this.diachikh_id
        rowDiaChiHDKH.HDKH_ID = this.hdkh_id
        this.dsDiaChiHDKH.push(rowDiaChiHDKH)
      }
      if (kieu == 3 || kieu == 4) {
        let rowDiaChiTB = {}
        let rowDiachiLD = {}
        this.diachitb_id = await this.getKey('DIACHI')
        this.diachild_id = await this.getKey('DIACHI')
        rowDiachiLD.DIACHI_ID = this.diachild_id
        rowDiachiLD.TINH_ID = row['TINH_ID']
        rowDiachiLD.DIACHI = row['DIACHI_LD']
        this.dsDiaChi.push(rowDiachiLD)
        //Địa chỉ thuê bao --> dataset dsdiachi
        rowDiaChiTB.DIACHI_ID = this.diachitb_id
        rowDiaChiTB.TINH_ID = row['TINH_ID']
        rowDiaChiTB.DIACHI = row['DIACHI_TB']
        this.dsDiaChi.push(rowDiaChiTB)

        let rowDiaChiHDTB = {}
        rowDiaChiHDTB.DIACHI_ID = this.diachitb_id
        rowDiaChiHDTB.HDTB_ID = this.hdtb_id
        rowDiaChiHDTB.DIACHILD_ID = this.diachild_id
        rowDiaChiHDTB.KINHDO = 0
        rowDiaChiHDTB.VIDO = 0
        rowDiaChiHDTB.KINHDO_LD = 0
        rowDiaChiHDTB.VIDO_LD = 0
        this.dsDiaChiHDTB.push(rowDiaChiHDTB)
      }
    },
    async CapNhat_HDTB_ID_HDTB_KV() {
      this.dsHDTB_KV = []
      //không có xử lý với hàm này
      // dsHDTB_KV_tmp = new HDTB_KV_DATA();
      //       cmUlt.CopyDataSet(dsHDTB_KV, dsHDTB_KV_tmp);

      //       for (int i = 0; i < dsHDTB_KV_tmp.HDTB_KV.Rows.Count; i++)
      //       {
      //           dsHDTB_KV_tmp.HDTB_KV.Rows[i]["HDTB_ID"] = hdtb_id;
      //       }
    },
    async TaoDuLieu_HDBANCHEO(row) {
      this.dsHDBANCHEO = []
      var rowHDBBC = createObjectHD_BANCHEO()

      rowHDBBC.HDTB_BAN = this.hdtb_id
      rowHDBBC.TINH_THICONG = this.tinh
      rowHDBBC.TINH_BAN = this.$root.token.getPhanVungID()
      //rowHDBBC.THUEBAO_ID_BAN = thuebao_id;
      rowHDBBC.THUEBAO_ID_BAN = 0
      rowHDBBC.TRANG_THAI = 0
      rowHDBBC.LOAIHD_BANCHEO = 1

      // rowHDBBC.SetKHACHHANG_ID_BANNull()
      // rowHDBBC.SetKHACHHANG_ID_TCNull()

      this.dsHDBANCHEO.push(rowHDBBC)

      // hop dong thue bao ban cheo
      this.dsHDTBBANCHEO = []
      let rowHDTBBC = createObjectHDTB_BANCHEO()

      //Copy HDTB to HDTB_BanCheo
      let hdtb = this.dsHDTB[0]
      let keySources = Object.keys(hdtb)
      for (let i = 0; i < keySources.length; i++) {
        if (rowHDTBBC.hasOwnProperty(keySources[i])) {
          rowHDTBBC[keySources[i]] = hdtb[keySources[i]]
        }
      }

      rowHDTBBC.KIEULD_ID = KieuLapDat.KHAOSATDATMOI
      rowHDTBBC.QUAN_ID = this.quanld_id
      rowHDTBBC.PHUONG_ID = this.phuongld_id
      rowHDTBBC.KHU_ID = this.khuld_id
      rowHDTBBC.AP_ID = this.apld_id

      rowHDTBBC.QUYTRINH_ID = this.quytrinh_tc_tn
      rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn
      this.dsHDTBBANCHEO.push(rowHDTBBC)
    },
    async TaoDuLieu_DiaChi_BanCheo(row, kieu) {
      this.dsDiaChi_BanCheo = []
      if (kieu == 1 || kieu == 4) {
        let rowDiaChiKH_BC = {}
        rowDiaChiKH_BC.DIACHI_ID = this.diachikh_id
        if (![0, undefined].includes(this.tinhkh_id)) rowDiaChiKH_BC.TINH_ID = this.tinhkh_id
        else rowDiaChiKH_BC.TINH_ID = 99
        if (![0, undefined].includes(this.quankh_id)) rowDiaChiKH_BC.QUAN_ID = this.quankh_id
        else rowDiaChiKH_BC.QUAN_ID = 99999
        if (![0, undefined].includes(this.phuongkh_id)) rowDiaChiKH_BC.PHUONG_ID = this.phuongkh_id
        if (![0, undefined].includes(this.phokh_id)) rowDiaChiKH_BC.PHO_ID = this.phokh_id
        if (![0, undefined].includes(this.apkh_id)) rowDiaChiKH_BC.AP_ID = this.apkh_id
        if (![0, undefined].includes(this.khukh_id)) rowDiaChiKH_BC.KHU_ID = this.khukh_id
        rowDiaChiKH_BC.SONHA = this.sonhakh
        rowDiaChiKH_BC.DIACHI = this.diachikh
        rowDiaChiKH_BC.LOAI_DC = 1
        this.dsDiaChi_BanCheo.push(rowDiaChiKH_BC)
      }
      if (kieu == 3 || kieu == 4) {
        let rowDiaChiTB_BC = {}
        let rowDiachiLD_BC = {}

        //Địa chỉ lắp đặt --> dataset dsdiachi
        rowDiachiLD_BC.DIACHI_ID = this.diachild_id
        rowDiachiLD_BC.TINH_ID = row['TINH_ID']
        rowDiachiLD_BC.QUAN_ID = row['QUANLD_ID']
        rowDiachiLD_BC.PHUONG_ID = row['PHUONGLD_ID']
        if (row['PHOLD_ID'] != '0' || row['PHOLD_ID'] == '') rowDiachiLD_BC.PHO_ID = row['PHOLD_ID']
        if (row['APLD_ID'] != '0' || row['APLD_ID'] == '') rowDiachiLD_BC.AP_ID = row['APLD_ID']
        if (row['KHULD_ID'] != '0' || row['KHULD_ID'] == '') rowDiachiLD_BC.KHU_ID = row['KHULD_ID']

        rowDiachiLD_BC.SONHA = row['SONHALD']
        rowDiachiLD_BC.DIACHI = row['DIACHI_LD']
        rowDiachiLD_BC.LOAI_DC = 4
        this.dsDiaChi_BanCheo.push(rowDiachiLD_BC)

        //Địa chỉ thuê bao --> dataset dsdiachi
        rowDiaChiTB_BC.DIACHI_ID = this.diachitb_id
        rowDiaChiTB_BC.TINH_ID = row['TINH_ID']
        rowDiaChiTB_BC.QUAN_ID = row['QUANTB_ID']
        rowDiaChiTB_BC.PHUONG_ID = row['PHUONGTB_ID']

        if (row['PHOTB_ID'] != '0' || row['PHOTB_ID'] == '') rowDiaChiTB_BC.PHO_ID = row['PHOTB_ID']
        if (row['APTB_ID'] != '0' || row['APTB_ID'] == '') rowDiaChiTB_BC.AP_ID = row['APTB_ID']
        if (row['KHUTB_ID'] != '0' || row['KHUTB_ID'] == '') rowDiaChiTB_BC.KHU_ID = row['KHUTB_ID']
        rowDiaChiTB_BC.SONHA = this.sonhatb
        rowDiaChiTB_BC.DIACHI = row['DIACHI_TB']
        rowDiaChiTB_BC.LOAI_DC = 3
        this.dsDiaChi_BanCheo.push(rowDiaChiTB_BC)
      }
    },
    exportExcelByCols(inputArray, arrayFields) {
      let exportData = []
      exportData = inputArray.map((e) => {
        let exportEntity = {}
        arrayFields.map((x) => {
          exportEntity[x.fieldName] = e[x.fieldName]
        })
        return exportEntity
      })
      if (exportData.length > 0) {
        let worksheet = XLSX.utils.json_to_sheet(exportData)
        let workbook = XLSX.utils.book_new()
        XLSX.utils.book_append_sheet(workbook, worksheet, 'YCKS')
        XLSX.writeFile(workbook, 'export.xlsx')
      } else {
        this.$toast.warning('Không có dữ liệu để xuất file!')
      }
    },
    onOpenPopupChonDiaChi(data) {
      console.log('onOpenPopupChonDiaChi')
      console.log(data)
      this.tinh_tc = data.TINH_ID.value
      this.selectedIndex = data.index
      this.dataDiaChi = {
        diachi_id: null,
        tinh_id: data?.TINH_ID?.value,
        quan_id: data?.QUANTB_ID?.value,
        phuong_id: data?.PHUONGTB_ID?.value,
        pho_id: data?.PHOTB_ID?.value,
        ap_id: data?.APTB_ID?.value,
        khu_id: data?.KHULD_ID?.value,
        dacdiem_id: null,
        sonha: data?.SONHATB?.value,
        diachi: data?.DIACHI_TB?.value
      }
      this.$refs.diaChiModal.showModal()
    },
    onAcceptDiaChi(frm) {
      console.log('onAcceptDiaChi')
      console.log(this.gridDanhSach.value)
      let row = this.gridDanhSach.list[this.selectedIndex]
      if (row != null) {
        row['QUANTB_ID'] = { value: frm.quan_id }
        row['PHUONGTB_ID'] = { value: frm.phuong_id }
        row['PHOTB_ID'] = { value: frm.pho_id }
        row['APTB_ID'] = { value: frm.ap_id }
        row['KHUTB_ID'] = { value: frm.khu_id }
        row['SONHATB'] = { value: frm.sonha }
        row['DIACHI_TB'] = { value: frm.diachi }
        row['QUANLD_ID'] = { value: frm.quan_id }
        row['PHUONGLD_ID'] = { value: frm.phuong_id }
        row['PHOLD_ID'] = { value: frm.pho_id }
        row['APLD_ID'] = { value: frm.ap_id }
        row['KHULD_ID'] = { value: frm.khu_id }
        row['SONHALD'] = { value: frm.sonha }
        row['DIACHI_LD'] = { value: frm.diachi }
        row.RowError = ''
      }
      this.gridDanhSach.list = this.gridDanhSach.list.map((e) => e)
    },
    convertToUpcaseKey(input) {
      let results = []
      for (let item of input) {
        let result = {}
        let keys = Object.keys(item)
        let total = keys.length
        let key = null
        for (let i = 0; i < total; i++) {
          key = keys[i]
          result[key.toUpperCase()] = item[key]
        }
        results.push(result)
      }
      return results
    },
    copyObjectKey(source, obj, except) {
      for (var key in source) {
        if (obj[key] != null && !except.includes(key)) source[key] = obj[key]
      }
      return source
    }
  },
  async mounted() {}
}
</script>
<style scoped>
.disabled a {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

.disabled a:hover {
  cursor: default;
  color: #6c757d;
}
</style>
