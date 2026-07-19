<template src="./index.template.html"></template>
<script>
import xlsx from "xlsx";
import { Edit, Sort, Filter, ForeignKey, Resize, Freeze } from "@syncfusion/ej2-vue-grids"
import { DatePicker } from '@syncfusion/ej2-calendars'
import { DropDownList } from '@syncfusion/ej2-dropdowns'
import { L10n } from '@syncfusion/ej2-base'

L10n.load({
    'vi-VN': {
        grid: {
          EmptyRecord: 'Không có bản ghi nào để hiển thị'
        }
    }
})

let element
let datePickerObj
let dropdownListObj

let convertStrDdMmYyyyToDate = (dateStr) => {
    let arr = dateStr.split('/')
    return new Date(arr[2], parseInt(arr[1]) - 1, arr[0])
}

let getDateElements = date => {

    date = new Date(date)

    let day = date.getDate()
    day = day > 9 ? day : '0' + day

    let month = date.getMonth() + 1
    month = month > 9 ? month : '0' + month

    let hour = date.getHours()
    hour = hour > 9 ? hour : '0' + hour

    let min = date.getMinutes()
    min = min > 9 ? min : '0' + min

    let second = date.getSeconds()
    second = second > 9 ? second : '0' + second

    return {
        y: date.getFullYear(),
        M: month,
        d: day,
        h: hour,
        m: min,
        s: second
    }
}
    
let ddmmyyyy = (date) => {

    if (!date) {
        return ''
    }
    
    date = getDateElements(date)
    return `${date.d}/${date.M}/${date.y}`
}

export default {
  provide: {
    grid: [Edit, Sort, Filter, ForeignKey, Resize, Freeze ]
  },
  data: function () {
    return {
      dsDuAn: [],
      dsTaiSan: [],
      dmDuAn: [],
      dsDuAnGoc: [],
      dsTaiSanGoc: [],
      dsTtvt: [],
      doiTuong: 'DUAN',
      fileExcel: null,
      statusCode: {
        LOI_DULIEU: -1,
        SAN_SANG: 0,
        DANG_LUU: 1,
        DA_LUU: 2,
        THAT_BAI: 3
      },
      columnTemplate: function () {
          return { 
              template: {
                  template: '<span :id="data.index" :style="{display: display }" style="font-size: 12px;" class="col-mark-selected-row fa fa-play text-main"></span>',
                  data() {
                      return { 
                          data: { } 
                      }
                  },
                  computed: {
                    display() {
                      return parseInt(this.data.index) == this.$parent.getSelectedRowIndexes()[0] ? 'block' : 'none'
                    }
                  }
              }
          }
      },
      filterBarTemplate: {
            create: function (args) {

              let div = document.createElement('div')
              div.classList.add('input-icon-right')
              
              let input = document.createElement('input')
              input.classList.add('form-control')
              input.id = args.column.foreignKeyField

              let span = document.createElement('span')
              span.classList.add('icon', 'nc-icon-outline', 'ui-1_zoom')

              div.append(input)
              div.append(span)

              return div
          },
          write: function (args) {

              args.element.querySelector('input').addEventListener('input', (args) => {

                  let value = args.currentTarget.value

                  if (!value) {
                      this.parent.removeFilteredColsByField(args.currentTarget.id);
                  } else {
                      this.parent.filterByColumn(args.currentTarget.id, 'contains', value)
                  }
              })
          }
      },
      editNgay: {
        create: () => {
            element = document.createElement('input')
            return element
        },
        read: () => {
            return ddmmyyyy(datePickerObj.value)
        },
        destroy: () => {
            datePickerObj.destroy()
        },
        write: (args) => {
            let value = args.rowData[args.column.field]
            datePickerObj = new DatePicker({
                value: !value ? null : convertStrDdMmYyyyToDate(value),
                format: 'dd/MM/yyyy',
                floatLabelType: 'Never'
            })
            datePickerObj.appendTo(element)
        }
      },
      editDropdownList: {
        create: () => {
            element = document.createElement('input')
            return element
        },
        read: () => {
            let key = dropdownListObj.column.rowData.KEY
            let column = dropdownListObj.column.column
            let rowData = column.parent.dataSource.filter(item => item.KEY == key)[0]
            rowData[column.drValueName] = dropdownListObj.value
            return dropdownListObj.text
        },
        destroy: () => {
            dropdownListObj.destroy()
        },
        write: (args) => {
            dropdownListObj = new DropDownList({
                value: args.rowData[args.column.drValueName],
                dataSource: args.column.drDataSource,
                fields: {value: args.column.drValueName, text: args.column.drTextName},
                allowFiltering: true
            })
            dropdownListObj.column = args
            dropdownListObj.appendTo(element)
        }
      }
    }
  },
  computed: {
    disableButton() {

      if (this.doiTuong == 'DUAN') {
        return this.dsDuAn.length == 0
      }

      if (this.doiTuong == 'TAISAN') {
        return this.dsTaiSan.length == 0
      }
    }
  },
  async mounted() {
    try {
      this.loading(true)
      await this.layDmDuAn()
      await this.layDsTtvt()
    } finally {
      this.loading(false)
    }
  },
  methods: {
    onActionComplete(args) {
        if (args.requestType == "filtering") {
            let filterObj = args.currentFilterObject
            let grid = document.querySelector(`[e-mappinguid="${filterObj.uid}"]`).closest('.e-grid')
            let filterInput = grid.querySelector(`#${filterObj.field}`)
            filterInput.click() 
            filterInput.focus() 
        }
    },
    onQueryCellInfo(args) {

      let field = args.column.field

      if (field == 'TRANG_THAI') {

        let error = Object.values(args.data.ERROR).map((item, index) => `${index + 1}. ${item}`).join('\n')
        
        if (!!error) {
          args.cell.title = error
        } else if (args.data.STATUS_CODE == this.statusCode.THAT_BAI) {
          args.cell.title = args.data.TRANG_THAI
        }

        args.cell.style.color = args.data.COLOR
        return
      }

      let error = args.data.ERROR[field]

      if (!!error) {
        args.cell.innerHTML = `<div class="cell-error-custom" title="${error}">x</div>`
      }

      args.cell.title = 'Nhấp đúp chuột lên ô để sửa dữ liệu'
    },
    async onCellSavedTaiSan(args) {
      let taiSanGrid = this.$refs['grid-taisan']
      let changes = taiSanGrid.getBatchChanges()
      await this.chuanHoaTaiSan(changes.changedRecords)
      taiSanGrid.batchUpdate(changes)
    },
    onCellSavedDuAn(args) {
      let duAnGrid = this.$refs['grid-duan']
      let changes = duAnGrid.getBatchChanges()
      this.chuanHoaDuAn(changes.changedRecords)
      duAnGrid.batchUpdate(changes)
    },
    onRowSelectedTaiSan(args) {
      this.$refs['grid-taisan'].$el.querySelectorAll('.col-mark-selected-row').forEach(item => {
        item.style.display = args.rowIndex == parseInt(item.id) ? 'block' : 'none'
      })
    },
    onDataBoundTaiSan() {
      this.$refs['grid-taisan'].selectRow(0)
    },
    onCreateGridTaiSan() { 
      this.$refs['grid-taisan'].$el.classList.add('table-result')
    },
    onRowSelectedDuAn(args) {
      this.$refs['grid-duan'].$el.querySelectorAll('.col-mark-selected-row').forEach(item => {
        item.style.display = args.rowIndex == parseInt(item.id) ? 'block' : 'none'
      })
    },
    onDataBoundDuAn() {
      this.$refs['grid-duan'].selectRow(0)
    },
    onCreateGridDuAn() {
      this.$refs['grid-duan'].$el.classList.add('table-result')
    },
    linkTaiFileMau_Click: function () {
      window.location.href = "/static/files/MauFile_Update_DuAn_TaiSan.xlsx";
    },
    validateDate: function (value) {
      if (value == undefined || value == null) return null;
      else {
        let regEx = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
        return value.match(regEx);
      }
    },
    validateDouble: function (value) {
      if (value == undefined || value == null) return null;
      else {
        let regEx = /^(?!0\d)\d*(\.\d+)?$/;
        return value.toString().match(regEx);
      }
    },
    buttonExportFile_Click() {

      let exportData = []

      if (this.doiTuong == 'DUAN') {
        this.dsDuAn.forEach(item => {
          let error = Object.values(item.ERROR).map((item, index) => `${index + 1}. ${item}`).join('\n')
          exportData.push({
            'Trạng thái cập nhật': !!error ? error : (!item.TRANG_THAI ? null : item.TRANG_THAI),
            'Số quyết định đầu tư': item.SOQD_DT,
            'Số quyết định quyết toán': item.SOQD_QT,
            'Mã công trình (*)': item.MA_CT,
            'Số hiệu công trình (*)': item.SOHIEU_CT,
            'Ghi chú': item.GHICHU,
            'Ngày nghiệm thu': item.NGAY_NT
          })
        })
      }

      if (this.doiTuong == 'TAISAN') {
        this.dsTaiSan.forEach(item => {
          let error = Object.values(item.ERROR).map((item, index) => `${index + 1}. ${item}`).join('\n')
          exportData.push({
            'Trạng thái cập nhật': !!error ? error : (!item.TRANG_THAI ? null : item.TRANG_THAI),
            'Tên tài sản': item.TEN_TS,
            'Giá': item.GIA,
            'Dự án': item.SOHIEU_CT,
            'Mã tài sản (*)': item.MA_TS,
            'TTVT': item.MA_DV,
          })
        })
      }

      if (exportData.length == 0) {
        this.$root.toastError('Không có dữ liệu để xuất')
        return
      }

      let worksheet = xlsx.utils.json_to_sheet(exportData)
      let workbook = xlsx.utils.book_new()
      xlsx.utils.book_append_sheet(workbook, worksheet, "Sheet1")
      xlsx.writeFile(workbook, "export.xlsx")
    },
    async thucHienCapNhatDuAn() {

      let countSuccess = 0

      try {
        this.loading(true)

        let gridDuAn = this.$refs['grid-duan']

        for (let i = 0; i < gridDuAn.dataSource.length; i++) {

          let item = {...this.dsDuAn[i]}

          if (Object.keys(item.ERROR).length > 0) {
            continue
          }

          let ngayNt = item.NGAY_NT

          if (!!ngayNt) {
            let array = ngayNt.split('/')
            let year = array[2]
            let month = array[1]
            let day = array[0]
            month = month.length == 1 ? `0${month}` : month
            day = day.length == 1 ? `0${day}` : day
            ngayNt = `${year}-${month}-${day}`
          }

          let data = {
            duan_id: item.DUAN_ID,
            ghichu: item.GHICHU,
            ma_ct: item.MA_CT,
            ngay_nt: ngayNt,
            sohieu_ct: item.SOHIEU_CT,
            soqd_dt: item.SOQD_DT,
            soqd_qt: item.SOQD_QT
          }

          try {
            item.STATUS_CODE = this.statusCode.DANG_LUU
            this.setStatusContent(item)
            await this.capNhatDuAn(data)
            item.STATUS_CODE = this.statusCode.DA_LUU
            this.setStatusContent(item)
            ++countSuccess
          } catch (err) {
            item.STATUS_CODE = this.statusCode.THAT_BAI
            this.setStatusContent(item)
            item.TRANG_THAI += `: ${err}`
          }

          gridDuAn.batchUpdate({changedRecords: [item]})
        }
      } finally {
        this.loading(false)
      }

      let message = []
      message.push('Tiến trình nhập file kết thúc!')
      message.push(`Tổng cộng ${this.dsDuAn.length} đối tượng trên danh sách:`)
      message.push(`Bỏ qua: ${this.dsDuAn.length - countSuccess} đối tượng`)
      message.push(`Cập nhật thành công: ${countSuccess} đối tượng`)

      if (this.dsDuAn.length == countSuccess) {
        this.$root.toastSuccess(message.join('\n'))
      } else {
        this.$root.toastError(message.join('\n'))
      }
    },
    async thucHienCapNhatTaiSan() {

      let countSuccess = 0

      try {
        this.loading(true)

        let gridTaiSan = this.$refs['grid-taisan']

        for (let i = 0; i < gridTaiSan.dataSource.length; i++) {

          let item = {...this.dsTaiSan[i]}

          if (Object.keys(item.ERROR).length > 0) {
            continue
          }

          let data = {
            taisan_id: item.TAISAN_ID,
            donvi_id: item.DONVI_ID,
            duan_id: item.DUAN_ID,
            gia: item.GIA,
            ma_ts: item.MA_TS,
            ten_ts: item.TEN_TS
          }

          try {
            item.STATUS_CODE = this.statusCode.DANG_LUU
            this.setStatusContent(item)
            await this.capNhatTaiSan(data)
            item.STATUS_CODE = this.statusCode.DA_LUU
            this.setStatusContent(item)
            ++countSuccess
          } catch (err) {
            item.STATUS_CODE = this.statusCode.THAT_BAI
            this.setStatusContent(item)
            item.TRANG_THAI += `: ${err}`
          }

          gridTaiSan.batchUpdate({changedRecords: [item]})
        }
      } finally {
        this.loading(false)
      }

      let message = []
      message.push('Tiến trình nhập file kết thúc!')
      message.push(`Tổng cộng ${this.dsTaiSan.length} đối tượng trên danh sách:`)
      message.push(`Bỏ qua: ${this.dsTaiSan.length - countSuccess} đối tượng`)
      message.push(`Cập nhật thành công: ${countSuccess} đối tượng`)
      
      if (this.dsTaiSan.length == countSuccess) {
        this.$root.toastSuccess(message.join('\n'))
      } else {
        this.$root.toastError(message.join('\n'))
      }
    },
    buttonExecute_Click() {
      if (this.doiTuong == 'DUAN' && this.dsDuAn.length > 0) {
        this.thucHienCapNhatDuAn()
      }

      if (this.doiTuong == 'TAISAN' && this.dsTaiSan.length > 0) {
        this.thucHienCapNhatTaiSan()
      }
    },
    onFileChange() {
      this.fileExcel = this.$refs['ref-upload-file'].$el.querySelector('input').files[0]
      this.$refs['ref-upload-file'].reset()
      this.$refs['ref-upload-file'].$el.querySelector('.form-file-text').innerHTML = this.fileExcel.name

      if (!!this.fileExcel) {
        this.buttonReadFile_Click()
      }
    },
    checkFile(file) {
      let fileType = [
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", 
        "application/vnd.ms-excel"
      ]

      if (fileType.indexOf(file.type) < 0) {
        throw "File không phải định dạng xlsx hoặc xls!"
      }

      let fileSize = 100 * 1024 * 1024 // 100MB

      if (file.size > fileSize) {
        throw "File có dung lượng vượt quá 100MB!"
      }
    }, 
    buttonReadFile_Click() {

      if (!this.fileExcel) {
        this.$root.toastError("Chưa chọn tệp dữ liệu")
        return
      }

      try {
        this.checkFile(this.fileExcel)
      } catch(err) {
        this.$root.toastError(err)
        return
      }

      let reader = new FileReader();

      reader.onload = async (e) => {

        const workBook = xlsx.read(e.target.result, { type: "binary" });

        if (!workBook) {
          return
        }

        let duAnData = null
        let taiSanData = null

        for (let i = 0; i < workBook.SheetNames.length; i++) {

          const sheetName = workBook.SheetNames[i]

          if (["DUAN", "TAISAN"].indexOf(sheetName.toUpperCase()) < 0) {
            continue
          }

          const workSheet = workBook.Sheets[sheetName]
          let fileData = xlsx.utils.sheet_to_json(workSheet, { header: 1 })
          fileData.shift(0)

          if (sheetName.toUpperCase() == "DUAN") {
            duAnData = fileData
          }

          if (sheetName.toUpperCase() == "TAISAN") {
            taiSanData = fileData
          }
        }

        if (!duAnData && !taiSanData) {
          this.$root.toastError('Không tìm thấy sheet dữ liệu yêu cầu. Sheet dữ liệu dự án, tài sản phải đặt tên tương ứng là "DUAN", "TAISAN".\nTải về file mẫu để đảm bảo định dạng chuẩn nhất.')
          return
        }

        let dsDuAn = []

        if (duAnData) {

          duAnData.forEach((item, index) => {

            let duAn = {
              DUAN_ID: item[0],
              SOHIEU_CT: item[1],
              MA_CT: item[2],
              SOQD_DT: item[3],
              SOQD_QT: item[4],
              NGAY_NT: item[5],
              GHICHU: item[6],
            }

            if (Object.values(duAn).some(value => !!value)) {
              duAn.ELEMENT_NAME = Object.keys(duAn)
              duAn.KEY = index + 1
              dsDuAn.push(duAn)
            }
          })
        }

        let dsTaiSan = []

        if (taiSanData) {

          taiSanData.forEach((item, index) => {

            let taiSan = {
              TAISAN_ID: item[0],
              SOHIEU_CT: item[1],
              MA_TS: item[2],
              TEN_TS: item[3],
              GIA: item[4],
              MA_DV: item[5]
            }

            if (Object.values(taiSan).some(value => !!value)) {
              taiSan.ELEMENT_NAME = Object.keys(taiSan)
              taiSan.KEY = index + 1
              dsTaiSan.push(taiSan)
            }
          })
        }

        try {
          this.$root.showLoading(true)
          await this.chuanHoaDuAn(dsDuAn)
          await this.chuanHoaTaiSan(dsTaiSan)
          this.dsDuAn = dsDuAn
          this.dsTaiSan = dsTaiSan
        } catch(error) {
          this.$root.toastError(error)
        }
        finally {
          this.$root.showLoading(false)
        }
      }

      reader.readAsBinaryString(this.fileExcel)
    },
    async chuanHoaDuAn(dsDuAn) {

      if (dsDuAn.length == 0) {
        return
      }

      let dsDuAnId = dsDuAn.filter(item => !!item.DUAN_ID).map(item => item.DUAN_ID)
      let dsSoHieuCt = dsDuAn.filter(item => !!item.SOHIEU_CT).map(item => item.SOHIEU_CT)
      let theoId

      if (dsDuAnId.length == dsDuAn.length) {
        theoId = true
      } else if (dsSoHieuCt.length == dsDuAn.length) {
        theoId = false
      } else {
        throw 'Trường dự án id hoặc số hiệu CT đang bị thiếu dữ liệu. Vui lòng bổ sung và thử lại.'
      }

      try {
        this.loading(true)

        if (theoId) {
          await this.layDsDuAnTheoDsId(dsDuAnId.join())
        } else {
          await this.layDsDuAnTheoDsSoHieuCt(dsSoHieuCt.join())
        }
      } finally {
        this.loading(false)
      }
      
      let dsDuAnGoc = this.dsDuAnGoc

      for (let i = 0; i < dsDuAn.length; i++) {

        let item = dsDuAn[i]
        item.STATUS_CODE = this.statusCode.SAN_SANG
        item.ERROR = {}
        this.setStatusContent(item)

        if (!!item.NGAY_NT && !isNaN(item.NGAY_NT)) {
          let date = new Date(Math.round((parseInt(item.NGAY_NT) - 25569)*86400*1000))
          item.NGAY_NT = ddmmyyyy(date)
        }

        let duAnGoc = null

        if (theoId) {

          duAnGoc = dsDuAnGoc.filter(element => element.DUAN_ID == parseInt(item.DUAN_ID))[0]

          if (!duAnGoc) {
            item.ERROR.DUAN_ID = 'Dự án không tồn tại. Hãy nhập chính xác dự án ID của dự án cần sửa và thử lại'
          }
        } else {
          duAnGoc = dsDuAnGoc.filter(element => element.SOHIEU_CT == item.SOHIEU_CT)[0]

          if (!duAnGoc) {
            item.ERROR.SOHIEU_CT = 'Dự án không tồn tại. Hãy nhập chính xác số hiệu CT cần sửa và thử lại'
          }
        }

        if (!duAnGoc) {
          item.STATUS_CODE = this.statusCode.LOI_DULIEU
          this.setStatusContent(item)
          continue
        }

        item.ELEMENT_NAME.forEach(key => {

          let value = item[key]
          value = !value ? '' : value.toString().trim()

          if (!value) {
            let valueGoc = duAnGoc[key]
            item[key] = !valueGoc ? '' : valueGoc.toString().trim()
          }
        })

        if (!theoId) {
          item.DUAN_ID = duAnGoc.DUAN_ID
        }

        if (!item.MA_CT) {
          item.ERROR.MA_CT = 'Mã công trình không được để trống'
        }

        if (!item.SOHIEU_CT) {
          item.ERROR.SOHIEU_CT = 'Số hiệu công trình không được để trống'
        }

        if (!!item.NGAY_NT && !this.validateDate(item.NGAY_NT)) {
          item.ERROR.NGAY_NT = 'Vui lòng nhập đúng định dạng ngày sử dụng (dd/MM/yyyy)'
        }

        if (Object.keys(item.ERROR).length > 0) {
          item.STATUS_CODE = this.statusCode.LOI_DULIEU
          this.setStatusContent(item)
        }
      }
    },
    async chuanHoaTaiSan(dsTaiSan) {

      if (dsTaiSan.length == 0) {
        return
      }

      let dsTaiSanId = dsTaiSan.filter(item => !!item.TAISAN_ID).map(item => item.TAISAN_ID)
      let dsMaTaiSan = dsTaiSan.filter(item => !!item.MA_TS).map(item => item.MA_TS)
      let theoId

      if (dsTaiSanId.length == dsTaiSan.length) {
        theoId = true
      } else if (dsMaTaiSan.length == dsTaiSan.length) {
        theoId = false
      } else {
        throw 'Trường tài sản id hoặc mã tài sản đang bị thiếu dữ liệu. Vui lòng bổ sung và thử lại.'
      }

      try {
        this.loading(true)

        if (theoId) {
          await this.layDsTaiSanTheoDsId(dsTaiSanId.join())
        } else {
          await this.layDsTaiSanTheoDsMa(dsMaTaiSan.join())
        }
      } finally {
        this.loading(false)
      }
      
      let dsTaiSanGoc = this.dsTaiSanGoc
      let map = {}

      for (let i = 0; i < dsTaiSan.length; i++) {

        let item = dsTaiSan[i]
        item.STATUS_CODE = this.statusCode.SAN_SANG
        item.ERROR = {}
        this.setStatusContent(item)

        let taiSanGoc = null

        if (theoId) {

          taiSanGoc = dsTaiSanGoc.filter(element => element.TAISAN_ID == parseInt(item.TAISAN_ID))[0]

          if (!taiSanGoc) {
            item.ERROR.TAISAN_ID = 'Tài sản không tồn tại. Hãy nhập chính xác tài sản ID của tài sản cần sửa và thử lại'
          }
        } else {
          taiSanGoc = dsTaiSanGoc.filter(element => element.MA_TS == item.MA_TS)[0]

          if (!taiSanGoc) {
            item.ERROR.MA_TS = 'Tài sản không tồn tại. Hãy nhập chính xác mã tài sản của tài sản cần sửa và thử lại'
          }
        }

        if (!taiSanGoc) {
          item.STATUS_CODE = this.statusCode.LOI_DULIEU
          this.setStatusContent(item)
          continue
        }

        item.DONVI_ID = null
        item.DUAN_ID = null

        if (!!item.MA_DV) {
          if (!!map[`MA_DV_${item.MA_DV}`]) {
            item.DONVI_ID = map[`MA_DV_${item.MA_DV}`]
          } else {
            try {
              this.loading(true)
              item.DONVI_ID = await this.layDonViId({muc: 1, ten_donvi: item.MA_DV})
            } finally {
              this.loading(false)
            }

            if (!item.DONVI_ID) {
              item.ERROR.MA_DV = 'Không tìm thấy TTVT có mã như trong file nhập vào hoặc nhân viên nhập dữ liệu không thuộc TTVT.'
            } else {
              map[`MA_DV_${item.MA_DV}`] = item.DONVI_ID
            }
          }
        }

        if (!!item.SOHIEU_CT) {
          if (!!map[`SOHIEU_CT${item.SOHIEU_CT}`]) {
            item.DUAN_ID = map[`SOHIEU_CT${item.SOHIEU_CT}`]
          } else {
            item.DUAN_ID = await this.getDuAnTheoSoHieu(item.SOHIEU_CT)

            if(!item.DUAN_ID) {
              item.ERROR.SOHIEU_CT = 'Không tìm thấy mã dự án.'
            } else {
              map[`SOHIEU_CT_${item.SOHIEU_CT}`] = item.DUAN_ID
            }
          }
        }

        item.ELEMENT_NAME.forEach(key => {

          let value = item[key]
          value = !value ? '' : value.toString().trim()

          if (!value) {
            let valueGoc = taiSanGoc[key]
            item[key] = !valueGoc ? '' : valueGoc.toString().trim()
          }
        })

        if (!theoId) {
          item.TAISAN_ID = taiSanGoc.TAISAN_ID
        }

        if (!!item.GIA) {
          if (!this.validateDouble(item.GIA) 
              || parseFloat(item.GIA) < 0
              || parseFloat(item.GIA) > 999999999999) {
            item.ERROR.GIA = 'Giá phải nằm trong khoảng từ 0 đến 999999999999'
          }
        }

        if (Object.keys(item.ERROR).length > 0) {
          item.STATUS_CODE = this.statusCode.LOI_DULIEU
          this.setStatusContent(item)
        }
      }
    },
    setStatusContent(item) {
      switch(item.STATUS_CODE) {
        case this.statusCode.LOI_DULIEU:
          item.TRANG_THAI = `1. ${Object.values(item.ERROR)[0]}`
          item.COLOR = 'red'
          return
        case this.statusCode.SAN_SANG:
          item.TRANG_THAI = `Sẵn sàng`
          item.COLOR = 'black'
          return
        case this.statusCode.DANG_LUU:
          item.TRANG_THAI = `Đang lưu`
          item.COLOR = 'blue'
          return
        case this.statusCode.DA_LUU:
          item.TRANG_THAI = `Đã lưu`
          item.COLOR = 'green'
          return
        case this.statusCode.THAT_BAI:
          item.TRANG_THAI = `Lưu thất bại`
          item.COLOR = 'red'
          return
      }
    },
    async layDonViId(data) {
      try {
        let response = await this.$root.context.post(`/web-cabman/donvi/lay-id`, data)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
            return response.data.DONVI_ID
        }
        
        throw {response: { data: response}}
      } catch (err) {
          return null
      }
    },
    async getDuAnTheoSoHieu(soHieuCT) {
      try {
        let response = await this.$root.context.post(`/web-cabman/cot-cong-be/getDuAnTheoSoHieu`, {soHieuCT: soHieuCT})

        if (response.error_code === 'BSS-00000000' && response.data != null) {
            return response.data.DUAN_ID
        }
        
        throw {response: { data: response}}
      } catch (err) {
          return null
      }
    },
    async layDsTtvt() {
      try {
        let response = await this.$root.context.post(`/web-ecms/danhmuc/layDsTTVTTheoNVTrongToken`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
            this.dsTtvt = response.data
            return
        }
        
        throw {response: { data: response}}
      } catch (err) {
          this.dsTtvt = []
      }
    },
    async layDmDuAn() {
      try {
        let response = await this.$root.context.get(`/web-cabman/duan/layDsDuAn`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
            this.dmDuAn = response.data
            return
        }
        
        throw {response: { data: response}}
      } catch (err) {
          this.dmDuAn = []
      }
    },
    async capNhatDuAn(data) {
      try {
          let response = await this.$root.context.post(`/web-cabman/duan/capNhatDuAn`, data)
          
          if (response.error_code !== 'BSS-00000000') {
              throw {response: { data: response}}
          }
      } catch (err) {
          let responseData = err.response.data
          let message = [] 

          if (responseData.message) {
              message.push(responseData.message)
          }

          if (responseData.message_detail) {
              message.push(responseData.message_detail)
          }

          if (responseData.data) {
              let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
              message.push([...new Set(detailMessage)].join('\n'))
          }

          throw message.join('\n')
      }
    },
    async capNhatTaiSan(data) {
      try {
          let response = await this.$root.context.post(`/web-cabman/taisan/capNhatTaiSan`, data)
          
          if (response.error_code !== 'BSS-00000000') {
              throw {response: { data: response}}
          }
      } catch (err) {
          let responseData = err.response.data
          let message = [] 

          if (responseData.message) {
              message.push(responseData.message)
          }

          if (responseData.message_detail) {
              message.push(responseData.message_detail)
          }

          if (responseData.data) {
              let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
              message.push([...new Set(detailMessage)].join('\n'))
          }

          throw message.join('\n')
      }
    },
    async layDsDuAnTheoDsId(dsDuAnId) {
        try {
            let response = await this.$root.context.post(`/web-cabman/duan/layDsDuAnTheoDsId`, { ds_duan_id: dsDuAnId})

            if (response.error_code === 'BSS-00000000' && response.data != null) {
                this.dsDuAnGoc = response.data
                return
            } 
          
            throw {response: { data: response}}
        } catch (err) {
            this.dsDuAnGoc = []
        }
    },
    async layDsDuAnTheoDsSoHieuCt(dsSoHieuCt) {
        try {
            let response = await this.$root.context.post(`/web-cabman/duan/layDsDuAnTheoDsSoHieuCt`, { ds_sohieu_ct: dsSoHieuCt})

            if (response.error_code === 'BSS-00000000' && response.data != null) {
                this.dsDuAnGoc = response.data
                return
            } 
          
            throw {response: { data: response}}
        } catch (err) {
            this.dsDuAnGoc = []
        }
    },
    async layDsTaiSanTheoDsId(dsTaiSanId) {
        try {
            let response = await this.$root.context.post(`/web-cabman/taisan/layDsTaiSanTheoDsId`, { ds_taisan_id: dsTaiSanId})

            if (response.error_code === 'BSS-00000000' && response.data != null) {
                this.dsTaiSanGoc = response.data
                return
            } 
          
            throw {response: { data: response}}
        } catch (err) {
            this.dsTaiSanGoc = []
        }
    },
    async layDsTaiSanTheoDsMa(dsMaTs) {
        try {
            let response = await this.$root.context.post(`/web-cabman/taisan/layDsTaiSanTheoDsMa`, { ds_ma_ts: dsMaTs})

            if (response.error_code === 'BSS-00000000' && response.data != null) {
                this.dsTaiSanGoc = response.data
                return
            } 
          
            throw {response: { data: response}}
        } catch (err) {
            this.dsTaiSanGoc = []
        }
    },
  },
};
</script>
<style>
  .capnhat-duan-taisan-theofile .e-filterbarcell {
      padding: 5px 7px !important;
  }

  .capnhat-duan-taisan-theofile th.e-headercell, .capnhat-duan-taisan-theofile th.e-filterbarcell {
      background-color: #BAE7FF !important;
      border-color: white !important;
  }

  .capnhat-duan-taisan-theofile th.e-filterbarcell {
      border-width: 1px 0 0 1px !important;
  }

  .capnhat-duan-taisan-theofile .e-grid {
      font-family: "Roboto", Arial !important;
  }

  .capnhat-duan-taisan-theofile .e-grid td.e-active {
      background: #F9E1A9 !important;
  }

  .capnhat-duan-taisan-theofile .e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
  .capnhat-duan-taisan-theofile .e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
  .capnhat-duan-taisan-theofile .e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
  .capnhat-duan-taisan-theofile .e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
  .capnhat-duan-taisan-theofile .e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell) {
    background-color: #FFF9EB !important;
  }

  .capnhat-duan-taisan-theofile .e-grid td.e-rowcell {
      line-height: 20px !important;
      padding: 5px 7px !important;
  }

  .capnhat-duan-taisan-theofile .e-grid .e-filterdiv.e-fltrinputdiv .e-input-group.e-control-wrapper {
      border: 0 !important;
  }

  .capnhat-duan-taisan-theofile .e-grid .e-filtertext.e-input.e-disable {
      display: none !important;
  }

  .capnhat-duan-taisan-theofile .e-grid .cell-error-custom {
    color: white;
    background-color: red;
    width: 20px;
    height: 20px;
    border-radius: 10px;
    text-align: center;
    float: left;
  }

  .capnhat-duan-taisan-theofile .e-grid tr.e-emptyrow td {
    text-align: center !important;
  }

  .capnhat-duan-taisan-theofile .disable-btn:hover {
        background-color: white !important;
  }

  .capnhat-duan-taisan-theofile .disable-btn button {
      color: #6c757d !important;
  }

  .capnhat-duan-taisan-theofile .disable-btn button:hover {
      cursor: default;
      color: #6c757d;
  }
</style>