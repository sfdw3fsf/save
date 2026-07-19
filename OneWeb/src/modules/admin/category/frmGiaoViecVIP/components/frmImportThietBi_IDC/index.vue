<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li v-if="btnFileMau.visible" :class="{ disabledInput: !btnFileMau.enabled }" @click="btnFileMau_Click">
          <a> <span class="icon one-reload1"></span>File mẫu</a>
        </li>
        <li v-if="tsbtnXoaTBi.visible" :class="{ disabledInput: !tsbtnXoaTBi.enabled }" @click="tsbtnXoaTBi_Click">
          <a> <span class="icon one-remove-outline"></span>Xóa thiết bị</a>
        </li>
        <li v-if="btnNapLai.visible" :class="{ disabledInput: !btnNapLai.enabled }" @click="btnNapLai_Click">
          <a> <span class="icon one-reload1"></span>Nạp lại</a>
        </li>
        <li v-if="btnThucThi.visible" :class="{ disabledInput: !btnThucThi_Click.enabled }" @click="btnThucThi_Click">
          <a> <span class="icon one-upload"></span>Thực thi</a>
        </li>
        <li v-if="btnXuatExel.visible" :class="{ disabledInput: !btnXuatExel.enabled }" @click="btnXuatExel_Click">
          <a> <span class="icon one-excel"></span>Xuất excel</a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="row">
        <div>
          <div class="info-row" style="margin: 0 0 10px 0;">
            <div class="key w100">
              Đường dẫn
            </div>
            <div class="value">
              <div style="position: relative; display: flex; align-items: center;">
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
        <div class="col-12 col-sm-2">
          <div style="font-weight: bold;">*Ghi chú:</div>
        </div>
        <div class="col-12 col-sm-10" style="margin: 10px 0 10px 0;">
          <div>- Chương trình chỉ đọc dữ liệu từ sheet có tên "DATA"</div>
          <div>- Chọn file dữ liệu sau đó ấn thực thi để import, chọn thiết bị trên lưới ấn xóa để xóa thiết bị</div>
        </div>
      </div>
      <DataGrid ref="grvExcel" :columns="grvExcel.cols" :dataSource="grvExcel.list" :showFilter="true" :enabledSelectFirstRow="false" :allowPaging="true" :enablePagingServer="false" />
      <br />
      <br />
      <DataGrid ref="grvTB" :columns="grvTB.cols" :dataSource="grvTB.list" :showFilter="true" :enabledSelectFirstRow="false" :allowPaging="true" :enablePagingServer="false" @rowSelected="grvTB_FocusedRowChanged" />
    </div>
  </div>
</template>
<script>
import XLSX from 'xlsx'
import api from './api'
import { DichVuVienThong, LoaiHopDong, TrangThaiHD, LoaiHinhTB } from '../../constants'
export default {
  props: {
    vhdtb_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    loaitb_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    ma_gd: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    ma_tb: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    tinh_tc: {
      type: [String, Number],
      defaul: null,
      require: false
    }
  },
  data() {
    return {
      localLoading: 0,
      tinh_khdn: [97, 100],
      btnFileMau: { visible: true, enabled: true },
      tsbtnXoaTBi: { visible: true, enabled: true },
      btnNapLai: { visible: true, enabled: true },
      btnThucThi: { visible: true, enabled: true },
      btnXuatExel: { visible: true, enabled: true },
      txtFilePath: { visible: true, enabled: true, value: null },
      linkFileMau:
        'https://storage-onebss.vnpt.vn/onebss-production/DHSX/QUANTRI/HO_SO/HGG/10202313/admin_hgg_1_20231013_041426_FileMau_Import_IDC.xlsx?response-content-type=application%2Fvnd.openxmlformats-officedocument.spreadsheetml.sheet&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minio%2F20231013%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20231013T091438Z&X-Amz-Expires=14400&X-Amz-SignedHeaders=host&X-Amz-Signature=d1a9ebcce4ede16b7cc21a03aff86400143741a81d2ae166320326f2bf51c6c8',
      grvExcel: {
        cols: [
          { fieldName: 'ID_TBI', headerText: 'ID Thiết bị', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'TEN_TBI', headerText: 'Tên thiết bị', allowFiltering: true, allowSorting: true },
          { fieldName: 'LOAI_TBI', headerText: 'Loại thiết bị', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'SERIAL', headerText: 'Serial', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'IP', headerText: 'IP', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'CONG_SUAT', headerText: 'Công suất', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'KHONG_GIAN', headerText: 'Không gian', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'NOI_LAPDAT', headerText: 'Nơi lắp đặt', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'DIACHI_RACK', headerText: 'Địa chỉ Rack', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'U_DUOI_TBI', headerText: 'U dưới thiết bị', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'U_TREN_TBI', headerText: 'U trên thiết bị', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAY_TIEPNHAN', headerText: 'Ngày tiếp nhận', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAY_SD', headerText: 'Ngày sử dụng', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAY_NT', headerText: 'Ngày nghiệm thu', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'TRANGTHAI_HD', headerText: 'Trạng thái HĐ', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'SO_PYC', headerText: 'SO_PYC', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAY_SD', headerText: 'Ngày sử dụng', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'kq', headerText: 'KQ', width: 100, allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      grvTB: {
        cols: [
          { fieldName: 'index', headerText: 'STT', width: 70, allowFiltering: true, allowSorting: true },
          { fieldName: 'ma_tb', headerText: 'Mã TB', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình TB', allowFiltering: true, allowSorting: true },
          { fieldName: 'ma_gd', headerText: 'Mã GD', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'id_tbi', headerText: 'ID Thiết bị', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'ten_tbi', headerText: 'Tên thiết bị', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'loai_tbi', headerText: 'Loại thiết bị', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'serial', headerText: 'Serial', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'ip', headerText: 'IP', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'cong_suat', headerText: 'Công suất', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'khong_gian', headerText: 'Không gian', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'noi_lapdat', headerText: 'Nơi lắp đặt', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'tang', headerText: 'Tầng', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'diachi_rack', headerText: 'Địa chỉ Rack', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'u_duoi_tb', headerText: 'U dưới thiết bị', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'u_ten_thi', headerText: 'U trên thiết bị', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'ngay_tiepnhan', headerText: 'Ngày tiếp nhận', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'ngay_sd', headerText: 'Ngày sử dụng', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'ngay_nt', headerText: 'Ngày nghiệm thu', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'trangthai_hd', headerText: 'Trạng thái HĐ', width: 100, allowFiltering: true, allowSorting: true },
          { fieldName: 'so_pyc', headerText: 'Số PYC', width: 100, allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      tmpDSTB_Excel: []
    }
  },
  computed: {},
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    }
  },
  methods: {
    async callApiWrapper(action, body) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        return result.data.data ? result.data.data : null
      } catch (e) {
        console.log('LOI ROI')
        console.log(e)
        if (e.data != null) {
          this.$toast.error(`${e.data.message}`)
        } else {
          this.$toast.error(`Có lỗi gọi API`)
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },
    downloadItem(url, label) {
      this.axios
        .get(url, { responseType: 'blob' })
        .then((response) => {
          const blob = new Blob([response.data], { type: 'application/pdf' })
          const link = document.createElement('a')
          link.href = URL.createObjectURL(blob)
          link.download = label
          link.click()
          URL.revokeObjectURL(link.href)
        })
        .catch(console.error)
    },
    async btnNapLai_FileHandler() {
      console.log('btnNapLai_FileHandler')
      console.log(this.$refs.files.files)
      let file = this.$refs.files.files[0]

      const reader = new FileReader()
      reader.onload = async (e) => {
        /* Parse data */
        const bstr = e.target.result
        const wb = XLSX.read(bstr, { type: 'binary' })
        /* Get first worksheet */
        const wsname = wb.SheetNames[0]
        const ws = wb.Sheets[wsname]
        /* Convert array of arrays */
        const data = XLSX.utils.sheet_to_json(ws, { header: 1 })
        columns = data[0]
      }
      reader.readAsBinaryString(file)
    },

    async CAPNHAT(arg) {
      // try {
      let dtThueBao = arg
      console.log('dtThueBao')
      console.log(dtThueBao)
      let dtThueBao_out = []
      // if (this.loaitb_id != LoaiHinhTB.VNPT_COLOCATION) {
      //   this.$toast.error('Loại hình TB phải là VNPT COLOCATION.')
      //   return
      // }
      if (dtThueBao != null) {
        let js_ds_tb = dtThueBao
        console.log('js_ds_tb')
        console.log(js_ds_tb)
        let dtTB_KQ = await this.callApiWrapper(api.insert_json_import_tbi_idc, {
          tinhthicong_id: this.tinh_tc,
          vjson: `${JSON.stringify(js_ds_tb)}`,
          vhdtb_id: this.vhdtb_id
        })
        for (let i = 0; i < this.tmpDSTB_Excel.length; i++) if (this.tmpDSTB_Excel[i]['KQ'] == 'Sẵn sàng!') this.tmpDSTB_Excel[i]['KQ'] = 'Thành công!'
        this.grvExcel.list = this.tmpDSTB_Excel
      }
      // } catch (exc) {
      //   this.$toast.error(`Có lỗi khi cập nhật dữ liệu ${exc}`)
      // }
    },
    ConvertKeysToLowerCase(obj) {
      var output = {}
      for (let i in obj) {
        if (Object.prototype.toString.apply(obj[i]) === '[object Object]') {
          output[i.toLowerCase()] = this.ConvertKeysToLowerCase(obj[i])
        } else if (Object.prototype.toString.apply(obj[i]) === '[object Array]') {
          output[i.toLowerCase()] = []
          output[i.toLowerCase()].push(this.ConvertKeysToLowerCase(obj[i][0]))
        } else {
          output[i.toLowerCase()] = obj[i]
        }
      }
      return output
    },
    async LOAD_DATA_ALL() {
      try {
        let dt = await this.callApiWrapper(api.lay_ds_tbi_idc, {
          tinhthicong_id: this.tinh_tc,
          hdtb_id: this.vhdtb_id
        })

        this.grvTB.list = dt.map((e, index) => {
          return {
            ...e,
            index: index + 1
          }
        })
        console.log('this.grvTB.list')
        console.log(this.grvTB.list)
        if (dt.length > 0) {
          this.btnXuatExel.enabled = true
        } else this.btnXuatExel.enabled = false
      } catch (exc) {
        this.$toast.error(`Có lỗi khi lấy dữ liệu: ${exc}`)
      }
    },
    async btnXuatExel_Click() {
      this.exportExcelByCols(this.grvTB.list, this.grvTB.cols)
    },
    exportExcelByCols(inputArray, arrayFields) {
      var exportData = []
      exportData = inputArray.map((e) => {
        var exportEntity = {}
        arrayFields.map((x) => {
          exportEntity[x.fieldName] = e[x.fieldName]
        })
        return exportEntity
      })
      if (exportData.length > 0) {
        let worksheet = XLSX.utils.json_to_sheet(exportData)
        let workbook = XLSX.utils.book_new()
        XLSX.utils.book_append_sheet(workbook, worksheet, 'Dữ liệu')
        XLSX.writeFile(workbook, 'export.xlsx')
      } else {
        this.$toast.warning('Không có dữ liệu để xuất file!')
      }
    },
    async btnThucThi_Click() {
      await this.CAPNHAT(this.tmpDSTB_Excel)
      await this.LOAD_DATA_ALL()
    },
    async btnNapLai_Click() {
      // await this.DOC_FILE()
      this.$refs.files.click()
    },
    async txtFilePath_ButtonClick() {
    },
    async btnFileMau_Click() {
      window.open('/static/files/FileMau_Import_IDC.xlsx')
      // await this.downloadItem(this.linkFileMau)
    },
    async frmImportThietBi_IDC_Load() {
      await this.LOAD_DATA_ALL()
    },
    async tsbtnXoaTBi_Click() {
      // try {
      if (!confirm('Xóa một thiết bị trên lưới?')) return
      if (this.vhdtb_ct_id != 0) {
        let kq = await this.callApiWrapper(api.delete_hd_thuebao_ct, {
          tinhthicong_id: this.tinh_tc,
          hdtb_ct_id: this.vhdtb_ct_id
        })
        await this.LOAD_DATA_ALL()
      } else this.$toast.warning('Chọn một thiết bị trên lưới để xóa')
      // } catch (exc) {
      //   this.$toast.error(`Có lỗi khi xóa thiết bị: ${exc}`)
      // }
    },
    async grvTB_FocusedRowChanged(e) {
      console.log('grvTB_FocusedRowChanged')
      console.log(e)
      if (e) {
        this.grvTB.value = e.data
        this.vhdtb_ct_id = this.grvTB.value.hdtb_ct_id
      } else this.vhdtb_ct_id = 0
    },
    async fileHandler() {
      console.log('fileHandler')
      let fileSelected = this.$refs.files.files[0]
      let formData = new FormData()
      for (var file of this.$refs.files.files) {
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
        console.log('wb.SheetNames')
        console.log(wb.SheetNames)
        const data = XLSX.utils.sheet_to_json(ws, { header: 1 })
        columns = data[0]
        console.log('FileReader')
        console.log(columns)
        console.log(data)

        this.tmpDSTB_Excel = []
        if (wsname != 'DATA') {
          this.$toast.error('Không tìm thấy sheet dữ liệu yêu cầu. Sheet dữ liệu danh sách thuê bao phải đặt tên là "DATA" ')
          return
        }
        let isValid = this.tmpDSTB_Excel.length > 0
        this.tmpDSTB_Excel = data.splice(1, columns.length)
        console.log('this.tmpDSTB_Excel 1')
        console.log(this.tmpDSTB_Excel)
        let dataList = []
        for (let i = 0; i < this.tmpDSTB_Excel.length; i++) {
          console.log(this.tmpDSTB_Excel[i])
          let data = {}
          for (let j = 0; j < columns.length; j++) {
            data[columns[j]] = this.tmpDSTB_Excel[i][j]
          }
          dataList.push(data)
        }
        this.tmpDSTB_Excel = dataList
        console.log('this.tmpDSTB_Excel 2')
        console.log(dataList)
        for (let i = 0; i < this.tmpDSTB_Excel.length; i++) {
          if (this.tmpDSTB_Excel[i]['SO_PYC']) this.tmpDSTB_Excel[i]['kq'] = 'Sẵn sàng!'
          else {
            this.tmpDSTB_Excel[i]['kq'] = 'Số PYC phải là số'
            isValid = false
          }
          let format = 'dd/MM/yyyy HH:mm:ss'
          if (this.tmpDSTB_Excel[i]['NGAY_TIEPNHAN']) this.tmpDSTB_Excel[i]['kq'] = 'Sẵn sàng!'
          else {
            this.tmpDSTB_Excel[i]['kq'] = 'Trường NGAY_TIEPNHAN phải có định dạng ' + format
            isValid = false
          }
          if (this.tmpDSTB_Excel[i]['NGAY_SD']) this.tmpDSTB_Excel[i]['kq'] = 'Sẵn sàng!'
          else {
            this.tmpDSTB_Excel[i]['kq'] = 'Trường NGAY_SD phải có định dạng ' + format
            isValid = false
          }
          if (this.tmpDSTB_Excel[i]['NGAY_NT']) this.tmpDSTB_Excel[i]['kq'] = 'Sẵn sàng!'
          else {
            this.tmpDSTB_Excel[i]['kq'] = 'Trường NGAY_NT phải có định dạng ' + format
            isValid = false
          }
        }
        this.grvExcel.list = this.tmpDSTB_Excel
        if (isValid) this.btnThucThi.enabled = true
        else this.btnThucThi.enabled = false
      }
      reader.readAsBinaryString(fileSelected)
      this.$refs.files.reset()
      await this.LOAD_DATA_ALL()
    }
  },
  async mounted() {
    await this.frmImportThietBi_IDC_Load()
  }
}
</script>
<style scoped></style>
