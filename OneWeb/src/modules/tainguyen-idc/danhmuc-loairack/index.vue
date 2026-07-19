<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import api from './api'
import * as XLSX from 'xlsx'
import ModalImportExcel from '../components/Modals/ModalImportExcel'

export default {
  components: {
    ModalImportExcel
  },
  data: function() {
    return {
      dataItems: [],
      dsSelected: [],
      currentItem: {
        id: null,
        ten: '',
        soU: null,
        dai: null,
        rong: null,
        cao: null,
        maMau: null,
        ghiChu: null,
        tenTat: '',
        moTa: '',
        suDung: 1
      },
      controls: {
        txtTen: {
          enabled: false,
          invalid: false
        },
        txtSoU: {
          enabled: false,
          invalid: false
        },
        txtDai: {
          enabled: false,
          invalid: false
        },
        txtRong: {
          enabled: false,
          invalid: false
        },
        txtCao: {
          enabled: false,
          invalid: false
        },
        txtMaMau: {
          enabled: false,
          invalid: false
        },
        txtTenTat: {
          enabled: false,
          invalid: false
        },
        txtMoTa: {
          enabled: false
        },
        txtGhiChu: {
          enabled: false
        },
        checkBoxSuDung: {
          enabled: false
        }
      },
      buttons: {
        btnNhapMoi: {
          enabled: true
        },
        btnGhiLai: {
          enabled: false
        },
        btnHuyBo: {
          enabled: false
        },
        btnSua: {
          enabled: false
        },
        btnXoa: {
          enabled: false
        }
      },

      // Cấu hình cho modal import
      importConfig: {
        title: 'Import dữ liệu',
        templateFileName: 'Template_Import_LoaiRack.xlsx',
        tableColumns: [
          { key: 'ten', label: 'Tên loại rack' },
          { key: 'soU', label: 'Số U trên rack' },
          { key: 'dai', label: 'Kích thước chiều dài' },
          { key: 'rong', label: 'Kích thước chiều rộng' },
          { key: 'cao', label: 'Kích thước chiều cao' },
          { key: 'maMau', label: 'Mã màu (VD: #FF0000)' },
          { key: 'suDung', label: 'Hiệu lực (1: Hiệu lực, 0: Không hiệu lực)' },
          { key: 'ghiChu', label: 'Ghi chú' }
          // { key: 'tenTat', label: 'Tên tắt' },
          // { key: 'moTa', label: 'Mô tả' }
        ],
        // Header cho data grid preview (nếu không truyền thì sẽ dùng tableColumns)
        gridColumns: [
          { key: 'ten', label: 'Tên loại rack' },
          { key: 'soU', label: 'Số U trên rack' },
          { key: 'dai', label: 'Chiều dài' },
          { key: 'rong', label: 'Chiều rộng' },
          { key: 'cao', label: 'Chiều cao' },
          { key: 'maMau', label: 'Mã màu' },
          { key: 'suDung', label: 'Hiệu lực' },
          { key: 'ghiChu', label: 'Ghi chú' }
          // { key: 'tenTat', label: 'Tên tắt' },
          // { key: 'moTa', label: 'Mô tả' }
        ],
        validationRules: [
          { field: 'ten', required: true, message: 'Tên loại rack không được để trống' },
          { field: 'ten', maxLength: 100, message: 'Tên loại rack không được vượt quá 100 ký tự' },

          { field: 'soU', required: true, message: 'Số U không được để trống' },
          {
            field: 'soU',
            validator: (v) => {
              const num = parseFloat(v)
              if (isNaN(num)) return 'Số U phải nhập số'
              if (num <= 0) return 'Số U phải lớn hơn 0'
              if (!Number.isInteger(num)) return 'Số U phải là số nguyên'
              return null
            }
          },
          { field: 'dai', required: true, message: 'Chiều dài không được để trống' },
          { field: 'dai', type: 'number', message: 'Chiều dài phải nhập số' },
          {
            field: 'dai',
            validator: (v) => {
              const num = parseFloat(v)
              if (isNaN(num)) return null 
              if (num <= 0) return 'Chiều dài phải lớn hơn 0'
              return null
            }
          },

          { field: 'rong', required: true, message: 'Chiều rộng không được để trống' },
          { field: 'rong', type: 'number', message: 'Chiều rộng phải nhập số' },
          {
            field: 'rong',
            validator: (v) => {
              const num = parseFloat(v)
              if (isNaN(num)) return null
              if (num <= 0) return 'Chiều rộng phải lớn hơn 0'
              return null
            }
          },

          { field: 'cao', required: true, message: 'Chiều cao không được để trống' },
          { field: 'cao', type: 'number', message: 'Chiều cao phải nhập số' },
          {
            field: 'cao',
            validator: (v) => {
              const num = parseFloat(v)
              if (isNaN(num)) return null
              if (num <= 0) return 'Chiều cao phải lớn hơn 0'
              return null
            }
          },

          { field: 'maMau', required: true, message: 'Mã màu không được để trống' },
          { field: 'maMau', maxLength: 50, message: 'Mã màu không được vượt quá 50 ký tự' },
          { field: 'maMau', type: 'colorcode', message: 'Mã màu không đúng định dạng (ví dụ: #FF0000 )' },

          { field: 'suDung', type: 'number', message: 'Hiệu lực phải nhập số' },
          { field: 'suDung', type: 'number', enum: [1, 0], message: 'Hiệu lực chỉ nhận số 0 hoặc 1 hoặc để trống' },

          { field: 'ghiChu', maxLength: 500, message: 'Ghi chú không được vượt quá 500 ký tự' },
          { field: 'tenTat', maxLength: 50, message: 'Tên tắt không được vượt quá 50 ký tự' },
          { field: 'moTa', maxLength: 500, message: 'Mô tả không được vượt quá 500 ký tự' }
        ],
        checkDataResult: true
      }
    }
  },
  computed: {
    gridItems: function() {
      return this.$refs.gridItems
    }
  },
  watch: {
    'currentItem.id': function(value) {
      this.buttons.btnSua.enabled = value > 0
    },
    'currentItem.ten': function(value) {
      this.controls.txtTen.invalid = value == ''
    },
    'currentItem.soU': function(value) {
      this.controls.txtSoU.invalid = value <= 0
    },
    'currentItem.dai': function(value) {
      this.controls.txtDai.invalid = value <= 0
    },
    'currentItem.rong': function(value) {
      this.controls.txtRong.invalid = value <= 0
    },
    'currentItem.cao': function(value) {
      this.controls.txtCao.invalid = value <= 0
    },
    'currentItem.maMau': function(value) {
      const v = value == null ? '' : String(value).trim()
      this.controls.txtMaMau.invalid = v === '' ? false : !this.isValidHexColor(v)
      // lưu ý: kiểm trạng rỗng xử ở validateControls() để thông báo riêng
    }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      this.dataItems = await this.getItems()
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    getItems: async function() {
      let result = []
      try {
        let rs = await api.getDanhSachLoaiRack(this.$root.context)
        result = rs.data
        if (result) {
          this.setCurrentItemDefault()
        }
      } catch (error) {
        console.error('Error getting danh sach loai rack:', error)
      }
      return result
    },
    updateItem: async function(args) {
      let result = false
      try {
        let rs = await api.capNhatLoaiRack(this.$root.context, args)
        result = rs.data.result
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    onSelectRowCheckbox(itemsCheck) {
      this.dsSelected = itemsCheck || []
      this.buttons.btnXoa.enabled = this.dsSelected.length > 0
    },
    deleteItem: async function(id) {
      let result = false
      try {
        let rs = await api.xoaLoaiRack(this.$root.context, id)
        result = rs.data.result
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    enableControls: function(args) {
      this.controls.txtTen.enabled = args
      this.controls.txtSoU.enabled = args
      this.controls.txtDai.enabled = args
      this.controls.txtRong.enabled = args
      this.controls.txtCao.enabled = args
      this.controls.txtMaMau.enabled = args
      this.controls.checkBoxSuDung.enabled = args
      this.controls.txtGhiChu.enabled = args
      this.controls.txtTenTat.enabled = args
      this.controls.txtMoTa.enabled = args

      this.buttons.btnGhiLai.enabled = args
      this.buttons.btnNhapMoi.enabled = !args
      this.buttons.btnHuyBo.enabled = this.buttons.btnGhiLai.enabled
      if (this.currentItem.id > 0) this.buttons.btnSua.enabled = !this.buttons.btnGhiLai.enabled
    },
    setCurrentItem: function(item) {
      this.currentItem.id = item.ID
      this.currentItem.ten = item.TEN
      this.currentItem.soU = item.SO_U
      this.currentItem.dai = item.DAI
      this.currentItem.rong = item.RONG
      this.currentItem.cao = item.CAO
      this.currentItem.maMau = item.MA_MAU || item.MAMAU || null
      this.currentItem.ghiChu = item.GHI_CHU
      this.currentItem.tenTat = item.TENTAT || item.TEN_TAT || ''
      this.currentItem.moTa = item.MOTA || item.MO_TA || ''
      this.currentItem.suDung =
        item.SUDUNG !== undefined && item.SUDUNG !== null
          ? item.SUDUNG
          : item.SU_DUNG !== undefined && item.SU_DUNG !== null
          ? item.SU_DUNG
          : 1
    },
    setCurrentItemDefault: function() {
      this.setCurrentItem({
        ID: null,
        TEN: '',
        SO_U: null,
        DAI: null,
        RONG: null,
        CAO: null,
        MA_MAU: null,
        GHI_CHU: null,
        TENTAT: null,
        MOTA: null,
        SUDUNG: 1
      })
    },
    validateControls: function() {
      let msg = ''
      if (this.controls.txtTen.invalid) {
        msg = 'Tên loại rack không được để trống.'
      }
      if (this.controls.txtSoU.invalid) {
        msg = 'Số U trên rack phải lớn hơn 0.'
      }
      if (this.controls.txtDai.invalid) {
        msg = 'Kích thước chiều dài phải lớn hơn 0.'
      }
      if (this.controls.txtRong.invalid) {
        msg = 'Kích thước chiều rộng phải lớn hơn 0.'
      }
      if (this.controls.txtCao.invalid) {
        msg = 'Kích thước chiều cao phải lớn hơn 0.'
      }
      const ma =
        this.currentItem.maMau == null
          ? ''
          : String(this.currentItem.maMau)
              .replace(/[\u0000-\u001F\u007F\u00A0]/g, '')
              .trim()
      if (!msg) {
        if (ma === '') msg = 'Mã màu không được để trống.'
        else if (!this.isValidHexColor(ma)) msg = 'Mã màu không đúng định dạng (ví dụ: #FF0000).'
      }
      if (msg != '') this.$root.toastError(msg)
      return msg == ''
    },
    gridItems_RowSelected: async function(args) {
      this.setCurrentItem(args.data)
    },
    gridItems_RowDeselected: async function(args) {
      let items = this.gridItems.getSelectedRecords()
      if (!(items == null || items.length == 0)) {
        this.setCurrentItem(items[0])
      } else {
        this.setCurrentItemDefault()
      }
    },
    btnGhiLai_Click: async function(args) {
      if (this.validateControls()) {
        let flag = await this.$confirm('Có chắc bạn muốn lưu lại loại rack đang chọn?', 'Xác nhận thực hiện', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'info',
          dangerouslyUseHTMLString: true
        })
          .then((s) => true)
          .catch((e) => {
            return false
          })
        if (flag) {
          try {
            this.$root.showLoading(true)
            let msgSuccess = this.currentItem.id ? 'Cập nhật loại rack thành công' : 'Thêm mới loại rack thành công'
            let rs = await this.updateItem(this.currentItem)
            if (rs) {
              this.$root.toastSuccess(msgSuccess)

              // Reload data từ API
              this.dataItems = await this.getItems()

              // Cập nhật currentItem với dữ liệu mới nhất
              if (this.currentItem.id === 0) {
                // Nếu là thêm mới, tìm item vừa thêm (thường là item đầu tiên hoặc có ID lớn nhất)
                if (this.dataItems && this.dataItems.length > 0) {
                  // Tìm item mới nhất (có thể dựa vào ID hoặc thời gian tạo)
                  const latestItem = this.dataItems.reduce((latest, current) => {
                    return current.ID > latest.ID ? current : latest
                  })
                  this.setCurrentItem(latestItem)
                }
              } else {
                // Nếu là cập nhật, tìm item có ID tương ứng
                const updatedItem = this.dataItems.find((item) => item.ID === this.currentItem.id)
                if (updatedItem) {
                  this.setCurrentItem(updatedItem)
                }
              }
            } else {
              if (this.currentItem.id === 0) {
                this.$root.toastError('Thêm loại rack thất bại')
              } else {
                this.$root.toastError('Cập nhật loại rack thất bại')
              }
            }
            this.enableControls(!rs)
          } finally {
            this.$root.showLoading(false)
          }
        }
      }
    },
    btnNhapMoi_Click: async function(args) {
      this.enableControls(true)
      this.setCurrentItemDefault()
    },
    btnHuyBo_Click: async function(args) {
      let item = null
      item = this.dataItems.find((x) => x.ID == this.currentItem.id)
      if (!item) {
        let items = this.gridItems.getSelectedRecords()
        if (items && items.length > 0) {
          item = items[0]
        }
      }
      if (this.currentItem.id && this.currentItem.id > 0) {
        item = this.dataItems.find((x) => x.ID == this.currentItem.id)
      } else {
        let items = this.gridItems.getSelectedRecords()
        if (items && items.length > 0) {
          item = items[0]
        }
      }
      if (item != null) {
        this.setCurrentItem(item)
      } else {
        this.setCurrentItemDefault()
      }
      this.enableControls(false)
    },
    btnSua_Click: async function(args) {
      this.enableControls(true)
    },
    btnXoa_Click: async function(args) {
      if (this.dsSelected.length <= 0) {
        this.$toast.error('Chưa chọn danh mục cần xoá.')
        return
      }
      let flag = await this.$confirm('Có chắc bạn muốn xóa loại rack đang chọn?', 'Xác nhận thực hiện', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning',
        dangerouslyUseHTMLString: true
      })
        .then((s) => true)
        .catch((e) => {
          return false
        })
      if (flag) {
        try {
          this.$root.showLoading(true)
          let promises = []
          for (let item of this.dsSelected) {
            promises.push(await this.deleteItem(item.ID))
          }
          let res = await Promise.all(promises)
          let ok = res.filter((x) => x === true)

          if (ok.length > 0) {
            this.$toast.success(`Đã xoá thành công ${ok.length} / ${res.length} mục`)
          } else {
            this.$toast.error(`Đã phát sinh lỗi khi xoá danh mục.`)
          }
          this.dataItems = await this.getItems()
          this.enableControls(false)
        } finally {
          this.$root.showLoading(false)
        }
      }
    },
    btnXuatExcel_Click() {
      this.$refs.gridItems.excelExport({
        fileName: 'DanhMucLoaiRack.xlsx'
      })
    },
    // Hàm xử lý dữ liệu Excel cho loại rack
    processLoaiRackExcelData(dataRows) {
      return dataRows.map((row, index) => {
        return {
          ten: row[0] || '',
          soU: row[1],
          dai: row[2],
          rong: row[3],
          cao: row[4],
          maMau: row[5] || '',
          suDung: row[6] || 1,
          ghiChu: row[7] || '',
          tenTat: row[8] || '',
          moTa: row[9] || ''
        }
      })
    },

    // Hàm format giá trị cell cho loại rack
    formatLoaiRackCellValue(value, fieldName = null) {
      if (value === null || value === undefined || value === '') return 'Hiệu lực'

      // Chỉ format cho trường hiệu lực (suDung)
      if (fieldName === 'suDung' && typeof value === 'number' && (value === 1 || value === 0)) {
        return value === 1 ? 'Hiệu lực' : 'Không hiệu lực'
      }

      return value
    },

    parseNumber(value) {
      if (value === null || value === undefined || value === '') return null
      const num = parseFloat(value)
      return isNaN(num) ? null : num
    },

    btnImportExcel_Click: async function() {
      try {
        const result = await this.$refs.modalImportExcel.showModal()

        if (result.success && result.data && result.data.length > 0) {
          // Xác nhận import
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.data.length} loại rack từ file Excel?`,
            'Xác nhận import',
            {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không',
              type: 'warning',
              dangerouslyUseHTMLString: true
            }
          )
            .then((s) => true)
            .catch((e) => false)

          if (confirmFlag) {
            await this.processImportData(result.data)
          }
        }
      } catch (error) {
        console.error('Lỗi khi import Excel:', error)
        this.$root.toastError('Có lỗi khi import Excel: ' + error.message)
      }
    },
    // method để gọi bulk API
    bulkCreateItems: async function(items) {
      let result = false
      try {
        let rs = await api.bulkCreateLoaiRack(this.$root.context, items)
        result = rs.data
        if (result.successCount > 0) {
          this.$root.toastSuccess(`Import thành công ${result.successCount} loại rack.`)
        }
        if (result.failedCount > 0) {
          this.$root.toastError(`Bỏ qua ${result.failedCount} loại rack.`)
        }
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    processImportData: async function(importData) {
      try {
        this.$root.showLoading(true)
        let successCount = 0
        let errorCount = 0
        const errors = []
        const validItems = []

        for (let i = 0; i < importData.length; i++) {
          const item = importData[i]

          try {
            let pSuDung = 1 // mặc định hiệu lực

            if (item.suDung !== undefined && item.suDung !== null && item.suDung !== '') {
              if (typeof item.suDung === 'string') {
                const val = item.suDung.trim().toLowerCase()
                if (val === 'hiệu lực' || val === '1') pSuDung = 1
                else if (val === 'không hiệu lực' || val === '0') pSuDung = 0
              } else if (typeof item.suDung === 'number') {
                pSuDung = item.suDung === 1 ? 1 : 0
              }
            }

            // Chuẩn bị dữ liệu theo format API bulk-create
            const importItem = {
              pTen: item.ten.trim(),
              pSoU: item.soU,
              pDai: item.dai,
              pRong: item.rong,
              pCao: item.cao,
              pMaMau: item.maMau,
              pSuDung: pSuDung,
              pGhiChu: item.ghiChu || null,
              pTenTat: item.tenTat || null,
              pMoTa: item.moTa || null
            }

            validItems.push(importItem)
          } catch (error) {
            errorCount++
            errors.push(`Dòng ${i + 1}: ${error.message}`)
          }
        }

        // Nếu có dữ liệu hợp lệ, gọi bulk API
        if (validItems.length > 0) {
          try {
            const result = await this.bulkCreateItems(validItems)
            if (result) {
              successCount = validItems.length
            } else {
              errorCount += validItems.length
              errors.push(`Lỗi khi lưu dữ liệu hàng loạt`)
            }
          } catch (error) {
            errorCount += validItems.length
            errors.push(`Lỗi khi gọi API bulk-create: ${error.message}`)
          }
        }

        // Reload data sau khi import
        this.dataItems = await this.getItems()
      } finally {
        this.$root.showLoading(false)
      }
    },
    async handleCheckData(checkDataInfo) {
      try {
        // Chuẩn bị dữ liệu theo format API validate-batch
        const validateData = checkDataInfo.data.map((item) => ({
          pTen: item.ten || '',
          pSoU: item.soU || 0,
          pDai: item.dai || 0,
          pRong: item.rong || 0,
          pCao: item.cao || 0,
          pMaMau: item.maMau || '',
          pSuDung: item.suDung || 1,
          pGhiChu: item.ghiChu || '',
          pTenTat: item.tenTat || '',
          pMoTa: item.moTa || ''
        }))

        // Gọi API validate-batch
        const response = await api.validateBatchLoaiRack(this.$root.context, validateData)
        if (response.data) {
          const result = response.data

          if (result.isValid) {
            this.importConfig.checkDataResult = false // Không có lỗi -> enable button "Ghi lại"
            this.$root.toastSuccess('Dữ liệu hợp lệ!')
          } else {
            this.importConfig.checkDataResult = true // Có lỗi -> disable button "Ghi lại"
            const errorMessage = 'Có tổng ' + result.errorCount + ' dòng dữ liệu lỗi. \n' + result.errorMessage
            this.$root.toastError(errorMessage)
            // emit cho modalImportExcel để hiển thị lỗi lên grid
            this.$refs.modalImportExcel.handleCheckDataResult({
              isValid: false,
              errors: result.errorMessage
            })
          }
        } else {
          this.importConfig.checkDataResult = true // Có lỗi -> disable button "Ghi lại"
          this.$root.toastError('Lỗi khi nhận phản hồi từ server')
        }
      } catch (error) {
        this.importConfig.checkDataResult = true // Có lỗi -> disable button "Ghi lại"
        console.error('Lỗi khi validate batch:', error)

        if (error.response && error.response.data && error.response.data.message_detail) {
          this.$root.toastError('Lỗi khi kiểm tra dữ liệu: ' + error.response.data.message_detail)
        } else {
          this.$root.toastError('Lỗi khi kiểm tra dữ liệu: ' + error.message)
        }
      }
    },
    isValidHexColor(value) {
      if (!value) return false
      const v = value.trim()
      // Chỉ chấp nhận #RRGGBB
      return /^#[0-9A-Fa-f]{6}$/.test(v)
    }
  }
}
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type='checkbox']:checked:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type='checkbox']:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
}

/* CSS cho validation error */
.error-input {
  border: 1px solid #f57f6c !important;
  animation-name: bounce;
  animation-duration: 0.5s;
  animation-delay: 0.25s;
}

@keyframes bounce {
  0% {
    transform: translateX(0px);
    timing-function: ease-in;
  }
  37% {
    transform: translateX(5px);
    timing-function: ease-out;
  }
  55% {
    transform: translateX(-5px);
    timing-function: ease-in;
  }
  73% {
    transform: translateX(4px);
    timing-function: ease-out;
  }
  82% {
    transform: translateX(-4px);
    timing-function: ease-in;
  }
  91% {
    transform: translateX(2px);
    timing-function: ease-out;
  }
  96% {
    transform: translateX(-2px);
    timing-function: ease-in;
  }
  100% {
    transform: translateX(0px);
    timing-function: ease-in;
  }
}

/* CSS cho color picker - không làm mờ khi disabled */
input[type='color']:disabled {
  opacity: 1 !important;
  cursor: not-allowed !important;
  pointer-events: none !important;
}
</style>
