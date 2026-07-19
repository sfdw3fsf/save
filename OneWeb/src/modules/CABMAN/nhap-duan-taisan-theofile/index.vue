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
        args.cell.innerHTML = '<div class="cell-error-custom">x</div>'
        args.cell.title = error
      }
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
      window.location.href = "/static/files/MauFile_Import_DuAn_TaiSan.xlsx";
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
    async thucHienThemDuAn() {

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

          let ngayDt = item.NGAY_DT

          if (!!ngayDt) {
            let array = ngayDt.split('/')
            let year = array[2]
            let month = array[1]
            let day = array[0]
            month = month.length == 1 ? `0${month}` : month
            day = day.length == 1 ? `0${day}` : day
            ngayDt = `${year}-${month}-${day}`
          }

          let data = {
            ghichu: item.GHICHU,
            ma_ct: item.MA_CT,
            ngay_nt: ngayNt,
            ngay_dt: ngayDt,
            sohieu_ct: item.SOHIEU_CT,
            soqd_dt: item.SOQD_DT,
            soqd_qt: item.SOQD_QT
          }

          try {
            item.STATUS_CODE = this.statusCode.DANG_LUU
            this.setStatusContent(item)
            await this.themDuAn(data)
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
      this.$root.toastSuccess(message.join('\n'))
    },
    async thucHienThemTaiSan() {

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
            donvi_id: item.DONVI_ID,
            duan_id: item.DUAN_ID,
            gia: item.GIA,
            ma_ts: item.MA_TS,
            ten_ts: item.TEN_TS
          }

          try {
            item.STATUS_CODE = this.statusCode.DANG_LUU
            this.setStatusContent(item)
            await this.themTaiSan(data)
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
      this.$root.toastSuccess(message.join('\n'))
    },
    buttonExecute_Click() {
      if (this.doiTuong == 'DUAN' && this.dsDuAn.length > 0) {
        this.thucHienThemDuAn()
      }

      if (this.doiTuong == 'TAISAN' && this.dsTaiSan.length > 0) {
        this.thucHienThemTaiSan()
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
    buttonReadFile_Click() {

      if (!this.fileExcel) {
        this.$root.toastError("Chưa chọn tệp dữ liệu")
        return
      }

      let reader = new FileReader();

      reader.onload = async (e) => {

        const workBook = xlsx.read(e.target.result, { type: "binary" })

        if (!workBook) {
          return
        }

        let duAnData = null
        let taiSanData = null

        for (let i = 0; i < workBook.SheetNames.length; i++) {

          const sheetName = workBook.SheetNames[i];

          if (["DUAN", "TAISAN"].indexOf(sheetName.toUpperCase()) < 0) {
            continue
          }

          const workSheet = workBook.Sheets[sheetName];
          let fileData = xlsx.utils.sheet_to_json(workSheet, { header: 1 });
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
              SOHIEU_CT: item[0],
              MA_CT: item[1],
              SOQD_DT: item[2],
              SOQD_QT: item[3],
              NGAY_NT: item[4],
              NGAY_DT: item[5],
              GHICHU: item[6],
              KEY: index + 1
            }

            if (Object.values(duAn).some(value => !!value)) {
              dsDuAn.push(duAn)
            }
          })
        }

        let dsTaiSan = []

        if (taiSanData) {

          taiSanData.forEach((item, index) => {

            let taiSan = {
              SOHIEU_CT: item[0],
              MA_TS: item[1],
              TEN_TS: item[2],
              GIA: item[3],
              MA_DV: item[4],
              KEY: index + 1
            }

            if (Object.values(taiSan).some(value => !!value)) {
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
        } finally {
          this.$root.showLoading(false);
        }
      };

      reader.readAsBinaryString(this.fileExcel)
    },
    async chuanHoaDuAn(dsDuAn) {

      for (let i = 0; i < dsDuAn.length; i++) {

        let item = dsDuAn[i]
        item.STATUS_CODE = this.statusCode.SAN_SANG
        item.ERROR = {}
        this.setStatusContent(item)

        if (!item.SOQD_DT) {
          item.ERROR.SOQD_DT = 'Số quyết định đầu tư không được để trống'
        }

        if (!item.SOQD_QT) {
          item.ERROR.SOQD_QT = 'Số quyết định quyết toán không được để trống'
        }

        if (!item.MA_CT) {
          item.ERROR.MA_CT = 'Mã công trình không được để trống'
        }

        if (!item.SOHIEU_CT) {
          item.ERROR.SOHIEU_CT = 'Số hiệu công trình không được để trống'
        }

        if (!item.GHICHU) {
          item.ERROR.GHICHU = 'Ghi chú không được để trống'
        }

        if (!item.NGAY_NT) {
          item.ERROR.NGAY_NT = 'Ngày nghiệm thu không được để trống'
        } else {
          if (isNaN(item.NGAY_NT)) {
            if (!this.validateDate(item.NGAY_NT)) {
              item.ERROR.NGAY_NT = 'Vui lòng nhập đúng định dạng ngày sử dụng (dd/MM/yyyy)'
            }
          } else {
            let date = new Date(Math.round((parseInt(item.NGAY_NT) - 25569)*86400*1000))
            item.NGAY_NT = ddmmyyyy(date)
          }
        }
        
        if (Object.keys(item.ERROR).length > 0) {
          item.STATUS_CODE = this.statusCode.LOI_DULIEU
          this.setStatusContent(item)
        }
      }
    },
    async chuanHoaTaiSan(dsTaiSan) {

      let map = {}

      for (let i = 0; i < dsTaiSan.length; i++) {

        let item = dsTaiSan[i]
        item.STATUS_CODE = this.statusCode.SAN_SANG
        item.ERROR = {}
        this.setStatusContent(item)
        
        item.DONVI_ID = null
        item.DUAN_ID = null

        if (!item.TEN_TS) {
          item.ERROR.TEN_TS = 'Tên tài sản không được để trống'
        }

        if (!!item.GIA) {
          if (!this.validateDouble(item.GIA) 
              || parseFloat(item.GIA) < 0
              || parseFloat(item.GIA) > 999999999999) {
            item.ERROR.GIA = 'Giá phải nằm trong khoảng từ 0 đến 999999999999'
          }
        } else {
          item.ERROR.GIA = 'Giá không được để trống'
        }

        if (!item.SOHIEU_CT) {
          item.ERROR.SOHIEU_CT = 'Bắt buộc phải có thông tin dự án'
        } else {
          if (!!map[`SOHIEU_CT${item.SOHIEU_CT}`]) {
            item.DUAN_ID = map[`SOHIEU_CT${item.SOHIEU_CT}`]
          } else {
            //item.DUAN_ID = await this.getDuAnTheoSoHieu(item.SOHIEU_CT)

            if (!this.dmDuAn) {
              item.DUAN_ID = null;
            } else {
              let duAn = this.dmDuAn.find(x => x.MA_CT == item.SOHIEU_CT)
              item.DUAN_ID = !duAn ? null : duAn.DUAN_ID
            }

            if(!item.DUAN_ID) {
              item.ERROR.SOHIEU_CT = 'Không tìm thấy mã dự án.'
            } else {
              map[`SOHIEU_CT_${item.SOHIEU_CT}`] = item.DUAN_ID
            }
          }
        }

        if (!item.MA_TS) {
          item.ERROR.MA_TS = 'Mã tài sản không được để trống'
        }

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
              item.ERROR.MA_DV = 'Không tìm thấy TTVT có mã như trong file nhập vào'
            } else {
              map[`MA_DV_${item.MA_DV}`] = item.DONVI_ID
            }
          }
        } else {
          item.ERROR.MA_DV = 'Đơn vị không được để trống'
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
    async themDuAn(data) {
      try {
          let response = await this.$root.context.post(`/web-cabman/duan/themDuAn`, data)
          
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
    async themTaiSan(data) {
      try {
          let response = await this.$root.context.post(`/web-cabman/taisan/themTaiSan`, data)
          
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
  },
};
</script>
<style>
  .nhap-duan-taisan-theofile .e-filterbarcell {
      padding: 5px 7px !important;
  }

  .nhap-duan-taisan-theofile th.e-headercell, .nhap-duan-taisan-theofile th.e-filterbarcell {
      background-color: #BAE7FF !important;
      border-color: white !important;
  }

  .nhap-duan-taisan-theofile th.e-filterbarcell {
      border-width: 1px 0 0 1px !important;
  }

  .nhap-duan-taisan-theofile .e-grid {
      font-family: "Roboto", Arial !important;
  }

  .nhap-duan-taisan-theofile .e-grid td.e-active {
      background: #F9E1A9 !important;
  }

  .nhap-duan-taisan-theofile .e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
  .nhap-duan-taisan-theofile .e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
  .nhap-duan-taisan-theofile .e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
  .nhap-duan-taisan-theofile .e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
  .nhap-duan-taisan-theofile .e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell) {
    background-color: #FFF9EB !important;
  }

  .nhap-duan-taisan-theofile .e-grid td.e-rowcell {
      line-height: 20px !important;
      padding: 5px 7px !important;
  }

  .nhap-duan-taisan-theofile .e-grid .e-filterdiv.e-fltrinputdiv .e-input-group.e-control-wrapper {
      border: 0 !important;
  }

  .nhap-duan-taisan-theofile .e-grid .e-filtertext.e-input.e-disable {
      display: none !important;
  }

  .nhap-duan-taisan-theofile .e-grid .cell-error-custom {
    color: white;
    background-color: red;
    width: 20px;
    height: 20px;
    border-radius: 10px;
    text-align: center;
    float: left;
  }

  .nhap-duan-taisan-theofile .e-grid tr.e-emptyrow td {
    text-align: center !important;
  }

  .nhap-duan-taisan-theofile .disable-btn:hover {
        background-color: white !important;
  }

  .nhap-duan-taisan-theofile .disable-btn button {
      color: #6c757d !important;
  }

  .nhap-duan-taisan-theofile .disable-btn button:hover {
      cursor: default;
      color: #6c757d;
  }
</style>