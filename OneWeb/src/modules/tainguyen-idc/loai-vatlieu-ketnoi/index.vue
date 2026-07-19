<template src="./index.template.html"></template>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import ModalImportExcel from '../components/Modals/ModalImportExcel'

export default {
  name: 'LoaiVatLieuKetNoi',
  components: {
    ModalImportExcel
  },
  data() {
    return {
      currentId: 0,
      isDisableSave: true,
      isDisableNew: false,
      isDisableUpdate: false,
      isDisableDelete: false,
      dsSelected: [],
      dsVatLieuKetNoi: [],
      loaiVatLieuSelectionList: [],
      displayIconUrl: '',
      previousIconRelative: '',
      pendingIconFile: null,
      pendingIconPreviewUrl: '',
      pendingRemoveIcon: false,
      form: {
        id: 0,
        ten: '',
        loaivlId: null,
        thongSo: '',
        iconPath: '',
        ghiChu: '',
        suDung: 1
      },
      buttons: {
        btnAddNew: {
          enabled: true
        },
        btnSave: {
          enabled: false
        },
        btnCancel: {
          enabled: false
        },
        btnEdit: {
          enabled: false
        },
        btnDelete: {
          enabled: false
        }
      },
      controls: {
        txtID: {
          enabled: false,
          invalid: false
        },
        txtTen: {
          enabled: false,
          invalid: false
        },
        selectLoaiVatLieu: {
          enabled: false,
          invalid: false
        },
        txtThongSo: {
          enabled: false,
          invalid: false
        },
        txtIcon: {
          enabled: false,
          invalid: false
        },
        txtGhiChu: {
          enabled: false,
          invalid: false
        },
        chkHieuLuc: {
          enabled: false,
          invalid: false
        }
      },
      enableValidate: false,
      uploadingIcon: false,
      loadingImageUrl: false,
      importConfig: {
        title: 'Import dữ liệu loại vật liệu kết nối',
        templateFileName: 'Template_Import_LoaiVatLieuKetNoi.xlsx',
        tableColumns: [
          { key: 'loaivlId', label: 'Loại vật liệu' },
          { key: 'ten', label: 'Tên loại vật liệu' },
          { key: 'thongSo', label: 'Thông số kỹ thuật' },
          { key: 'ghiChu', label: 'Ghi chú' },
          { key: 'suDung', label: 'Hiệu lực' }
        ],
        validationRules: [
          { field: 'loaivlId', validator: null },
          { field: 'loaivlId', required: true, message: 'Loại vật liệu không được để trống' },
          { field: 'ten', required: true, message: 'Tên loại vật liệu kết nối không được để trống' },
          { field: 'ten', maxLength: 255, message: 'Tên loại vật liệu kết nối không được vượt quá 255 ký tự' },
          { field: 'thongSo', required: true, message: 'Thông số kỹ thuật không được để trống' },
          { field: 'thongSo', maxLength: 500, message: 'Thông số kỹ thuật không được vượt quá 500 ký tự' },
          { field: 'ghiChu', maxLength: 500, message: 'Ghi chú không được vượt quá 500 ký tự' },
          { field: 'suDung', type: 'number', enum: [1, 0], message: 'Hiệu lực chỉ nhận số 0 hoặc 1 hoặc để trống' }
        ],
        checkDataResult: true
      }
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      this.loaiVatLieuSelectionList = [
        { ID: 1, TEN: 'Cáp điện' },
        { ID: 2, TEN: 'Busway' },
        { ID: 3, TEN: 'Thanh cái' }
      ]
      await this.getDanhSachVLKN()

      // Gán validator thực tế cho rule vừa khai báo trong importConfig
      const idx = this.importConfig.validationRules.findIndex((r) => r.field === 'loaivlId' && r.validator === null)
      if (idx !== -1) {
        // dùng function factory để luôn lấy Set mới nhất nếu danh mục đổi
        this.importConfig.validationRules[idx].validator = this.validateVLKNExists()
      }
    } catch (error) {
      console.error('Error in created:', error)
      this.$toast.error('Có lỗi khi khởi tạo dữ liệu')
    } finally {
      this.$root.showLoading(false)
    }
  },
  watch: {
    'form.id': function(value) {
      this.buttons.btnEdit.enabled = value > 0
      this.buttons.btnDelete.enabled = value > 0 && !this.buttons.btnSave.enabled
    },
    'form.ten'(val) {
      this.validateField('ten', val)
    },
    'form.loaivlId'(val) {
      this.validateField('loaivlId', val)
    },
    'form.thongSo'(val) {
      this.validateField('thongSo', val)
    },
    'form.iconPath'(val) {
      this.validateField('iconPath', val)
    },
    pendingIconFile: function(value) {
      if (value) {
        this.controls.txtIcon.invalid = false
      }
    },
    dsSelected: function(value) {
      if (!this.buttons.btnSave.enabled) {
        this.buttons.btnDelete.enabled = value && value.length > 0
      }
    }
  },

  methods: {
    validateField(field, value) {
      if (!this.enableValidate) return
      const fieldValidationMap = {
        ten: { control: 'txtTen', type: 'string' },
        loaivlId: { control: 'selectLoaiVatLieu', type: 'select' },
        thongSo: { control: 'txtThongSo', type: 'string' },
        iconPath: { control: 'txtIcon', type: 'icon' }
      }

      const config = fieldValidationMap[field]
      if (!config) return

      let isValid = false
      if (config.type === 'string') {
        isValid = value && value.toString().trim() !== ''
      } else if (config.type === 'select') {
        isValid = value !== null && value !== undefined && value !== '' && value !== 0
      } else if (config.type === 'icon') {
        isValid = (value && value.toString().trim() !== '') || this.pendingIconFile
      }

      if (this.controls[config.control]) {
        this.controls[config.control].invalid = !isValid
      }
    },

    // ====== IMPORT/EXPORT EXCEL ====== //

    btnImportExcel_Click: async function() {
      try {
        const result = await this.$refs.modalImportExcel.showModal()

        if (result.success && result.data && result.data.length > 0) {
          // Xác nhận import
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.data.length} loại vật liệu kết nối từ file Excel?`,
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

    btnExportExcel_Click() {
      const filtered = this.$refs.grdItems.grid.ej2Instances.currentViewData
      const exportData = filtered.map((item) => ({
        ...item,
        LOAIVATLIEU_ID: this.getLoaiVatLieuText(item.LOAIVATLIEU_ID)
      }))

      // Tạo cấu hình export
      const exportConfig = {
        fileName: 'DanhSachLoaiVatLieuKetNoi.xlsx',
        dataSource: exportData
      }

      this.grdItems.excelExport(exportConfig)
      this.$root.toastSuccess('Xuất dữ liệu thành công')
    },
    getSuDungText: function(value) {
      return value.toString() === '1' ? 'Hiệu lực' : 'Không hiệu lực'
    },

    // Hàm xử lý dữ liệu Excel
    processLoaiVLKNExcelData(dataRows) {
      return dataRows.map((row, index) => {
        return {
          loaivlId: row[0] || '',
          ten: row[1] || '',
          thongSo: row[2] || '',
          ghiChu: row[3] || '',
          suDung: row[4]
        }
      })
    },

    processImportData: async function(importData) {
      try {
        this.$root.showLoading(true)

        const validItems = (importData || []).map((item) => {
          return {
            id: null,
            ten: item.ten?.trim() || '',
            loaivlId: this.convertLoaiVatLieuNameToId(item.loaivlId),
            thongSo: item.thongSo?.trim() || '',
            ghiChu: item.ghiChu?.toString().trim() || '',
            iconPath: '',
            suDung: this.parseNumber(item.suDung) || 1
          }
        })

        await this.importVatLieuKetNoiData(validItems)
      } finally {
        this.$root.showLoading(false)
      }
    },

    async handleCheckData(checkDataInfo) {
      try {
        const payload = (checkDataInfo?.data || []).map((row) => ({
          ...row,
          id: '',
          loaivlId: this.parseNumber(row.loaivlId)
        }))

        const response = await this.axios.post('/web-ecms/idc/loai-vatlieu-ketnoi/validate-multi', payload)
        const isValid = response?.data.data
        if (isValid) {
          this.importConfig.checkDataResult = false
          this.$root.toastSuccess('Dữ liệu hợp lệ!')
        }
      } catch (error) {
        const msgDetail = error?.data?.message_detail || ''
        this.importConfig.checkDataResult = true
        const errorList = msgDetail.split('\n').filter((line) => line.trim() !== '')
        if (errorList.length > 0) {
          this.$root.toastError(`Có ${errorList.length} lỗi trong file import\n${msgDetail}`)
        }
        // emit cho modalImportExcel để hiển thị lỗi lên grid
        this.$refs.modalImportExcel.handleCheckDataResult({
          isValid: false,
          errors: msgDetail,
        })  
      }
    },

    parseNumber(value) {
      if (value === null || value === undefined || value === '') return null
      const num = parseFloat(value)
      return isNaN(num) ? null : num
    },

    async importVatLieuKetNoiData(data) {
      if (!data || data.length === 0) {
        this.$root.toastError('Không có dữ liệu hợp lệ để import!')
        return false
      }

      let successCount = 0
      let errorCount = 0

      try {
        const response = await this.axios.post('/web-ecms/idc/loai-vatlieu-ketnoi/upsert-multi', data)

        if (response?.data?.data) {
          successCount = response.data.data.successCount || 0
          errorCount = response.data.data.failCount || 0
        }
      } catch (err) {
        this.$root.toastError(`Import lỗi: ${err?.data?.message_detail || err.message}`)
        console.error(err)
      } finally {
        await this.getDanhSachVLKN()
        if (successCount > 0) {
          this.$root.toastSuccess(`Import thành công ${successCount} loại vật liệu kết nối`)
        }
        if (errorCount > 0) {
          this.$root.toastError(`Bỏ qua ${errorCount} dòng. Vui lòng kiểm tra lại dữ liệu.`)
        }
      }
      return true
    },

    onClickUploadIcon: function() {
      if (this.$refs.fileInputIcon) {
        this.$refs.fileInputIcon.value = null
        this.$refs.fileInputIcon.click()
      }
    },
    onRemoveIcon: function() {
      this.pendingRemoveIcon = true
      this.pendingIconFile = null
      this.pendingIconPreviewUrl = ''
      this.displayIconUrl = ''
    },
    onChangeIconFile: async function(event) {
      try {
        const files = event.target.files
        if (!files || files.length === 0) {
          return
        }
        const file = files[0]
        // Chỉ lưu tạm thời file và hiển thị preview local, không gọi API ngay
        this.pendingRemoveIcon = false
        this.pendingIconFile = file
        // Tạo URL preview tạm thời
        if (this.pendingIconPreviewUrl) {
          try {
            URL.revokeObjectURL(this.pendingIconPreviewUrl)
          } catch (e) {}
        }
        this.pendingIconPreviewUrl = URL.createObjectURL(file)
        this.displayIconUrl = this.pendingIconPreviewUrl
      } catch (e) {
        this.$root.toastError('Có lỗi khi tải ảnh')
      } finally {
        this.uploadingIcon = false
      }
    },

    enableControls: function(args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key)) {
          this.controls[key].enabled = args
        }
      }

      this.buttons.btnSave.enabled = args
      this.buttons.btnAddNew.enabled = !args
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled
      this.buttons.btnEdit.enabled = !args
      if (!args) {
        this.buttons.btnDelete.enabled = this.dsSelected && this.dsSelected.length > 0
      } else {
        this.buttons.btnDelete.enabled = false
      }

      if (this.form.id > 0) this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled
    },

    onSelectRowCheckbox(itemsCheck) {
      this.dsSelected = itemsCheck
      if (!this.buttons.btnSave.enabled) {
        this.buttons.btnDelete.enabled = itemsCheck && itemsCheck.length > 0
      }
    },

    onSave: async function() {
      if (this.validateControls()) {
        let flag = await this.$confirm('Bạn có muốn lưu thông tin này không ?', 'Xác nhận', {
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
            let mess = this.form.id == 0 ? 'Thêm mới thông tin thành công.' : 'Cập nhật thông tin thành công.'
            this.$root.showLoading(true)

            if (this.pendingRemoveIcon && this.form.iconPath) {
              await this.deleteFileFromMinio(this.form.iconPath)
              this.form.iconPath = ''
            }
            // 2) Nếu có chọn ảnh mới
            if (this.pendingIconFile) {
              if (this.form.iconPath) {
                await this.deleteFileFromMinio(this.form.iconPath)
              }
              const uploadRs = await this.uploadFileToMinio(this.pendingIconFile)
              if (uploadRs && uploadRs.relativeUrl) {
                this.form.iconPath = uploadRs.relativeUrl
                const viewUrl = await this.getPresignedUrlFromMinio(this.form.iconPath)
                this.displayIconUrl = viewUrl || ''
              } else {
                this.$root.toastError('Tải ảnh thất bại')
                return
              }
            }

            let rs = await this.apiSave(this.form)
            if (rs.data && rs.data.data > 0) {
              this.$root.toastSuccess(mess)
              this.pendingIconFile = null
              if (this.pendingIconPreviewUrl) {
                try {
                  URL.revokeObjectURL(this.pendingIconPreviewUrl)
                } catch (e) {}
              }
              this.pendingIconPreviewUrl = ''
              this.pendingRemoveIcon = false
              this.previousIconRelative = this.form.iconPath

              if (this.form.id === 0) {
                this.setCurrentItemDefault()
              }
            } else if (rs.data == 0) {
              this.$root.toastError('Đối tượng thao tác không tồn tại.')
            } else if (rs.data == -1) {
              this.$root.toastError('Tên loại vật liệu kết nối đã tồn tại.')
            } else {
              this.$root.toastError('Chức năng lỗi.')
            }

            this.enableControls(false)
          } finally {
            await this.getDanhSachVLKN()
            this.$root.showLoading(false)
          }
        }
      }
    },

    onAddNew: async function() {
      this.setCurrentItemDefault()
      this.enableControls(true)
    },
    onEdit: async function(args) {
      this.enableControls(true)
    },
    onCancel: async function(args) {
      let items = this.grdItems.getSelectedRecords()
      let item = null
      if (!(items == null || items.length == 0)) {
        if (this.form.id == 0) {
          item = items[0]
        } else {
          item = items.find((x) => x.ID == this.form.id)
        }
      }
      if (item != null) {
        this.setCurrentItem(item)
      } else {
        this.setCurrentItemDefault()
      }
      // Hủy thay đổi icon tạm thời
      this.pendingIconFile = null
      if (this.pendingIconPreviewUrl) {
        try {
          URL.revokeObjectURL(this.pendingIconPreviewUrl)
        } catch (e) {}
      }
      this.pendingIconPreviewUrl = ''
      this.pendingRemoveIcon = false
      if (this.previousIconRelative) {
        try {
          this.loadingImageUrl = true
          const url = await this.getPresignedUrlFromMinio(this.previousIconRelative)
          this.displayIconUrl = url || ''
        } catch (e) {
          this.displayIconUrl = ''
        } finally {
          this.loadingImageUrl = false
        }
      } else {
        this.displayIconUrl = ''
      }
      this.enableControls(false)
      if (this.dsSelected && this.dsSelected.length > 0) {
        this.buttons.btnDelete.enabled = true
      }
    },
    onDelete: async function(args) {
      let items = this.grdItems.getSelectedRecords()
      let ids = items.map((x) => x.ID)
      let msg = `Bạn có muốn xóa ${items.length} mẫu tin đã chọn không?`
      let flag = await this.$confirm(msg, 'Xác nhận', {
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
          let rs = await this.apiDelete(ids)
          if (rs && rs.data > 0) {
            for (const item of items) {
              if (item.ICON_PATH) {
                try {
                  await this.deleteFileFromMinio(item.ICON_PATH)
                } catch (e) {
                  console.warn('Failed to delete icon:', item.ICON_PATH, e)
                }
              }
            }
            await this.getDanhSachVLKN()
            this.setCurrentItemDefault()
            this.enableControls(false)
            this.dsSelected = []
            this.buttons.btnDelete.enabled = false
            this.$root.toastSuccess('Xóa thông tin thành công')
          } else if (rs.data == 0) {
            this.$root.toastError('Không tìm thấy đối tượng cần xóa.')
          } else {
            this.$root.toastError('Có lỗi khi xóa dữ liệu.')
          }
        } catch (error) {
          this.handleApiError(error)
        } finally {
          this.$root.showLoading(false)
        }
      }
    },

    setCurrentItem: async function(item) {
      this.form.id = item.ID
      this.form.ten = item.TEN
      this.form.loaivlId = item.LOAIVATLIEU_ID
      this.form.thongSo = item.THONGSO_KYTHUAT
      this.form.iconPath = item.ICON_PATH
      // Lưu lại giá trị cũ để tham khảo
      this.previousIconRelative = this.form.iconPath
      // Lấy presigned URL để hiển thị
      if (this.form.iconPath) {
        try {
          this.loadingImageUrl = true
          const url = await this.getPresignedUrlFromMinio(this.form.iconPath)
          this.displayIconUrl = url || ''
        } catch (e) {
          this.displayIconUrl = ''
        } finally {
          this.loadingImageUrl = false
        }
      } else {
        this.displayIconUrl = ''
      }
      this.form.ghiChu = item.GHICHU
      this.form.suDung = item.SUDUNG == 'Hiệu lực' ? 1 : 0

      // Reset validation states when loading existing item
      this.controls.txtTen.invalid = false
      this.controls.selectLoaiVatLieu.invalid = false
      this.controls.txtThongSo.invalid = false
      this.controls.txtIcon.invalid = false
    },
    setCurrentItemDefault: function() {
      this.setCurrentItem({
        ID: 0,
        TEN: '',
        LOAIVATLIEU_ID: '',
        THONGSO_KYTHUAT: '',
        ICON_PATH: '',
        GHICHU: '',
        SUDUNG: 1
      })
      this.pendingIconFile = null
      if (this.pendingIconPreviewUrl) {
        try {
          URL.revokeObjectURL(this.pendingIconPreviewUrl)
        } catch (e) {}
      }
      this.pendingIconPreviewUrl = ''
      this.pendingRemoveIcon = false
      this.displayIconUrl = ''
      this.enableControls(false)
      this.dsSelected = []
      this.buttons.btnDelete.enabled = false

      // Reset validation states
      this.controls.txtTen.invalid = false
      this.controls.selectLoaiVatLieu.invalid = false
      this.controls.txtThongSo.invalid = false
      this.controls.txtIcon.invalid = false
    },
    validateControls: function() {
      this.enableValidate = true
      let isValid = true

      // Reset all validation states
      Object.keys(this.controls).forEach((key) => {
        this.controls[key].invalid = false
      })

      // Validation rules configuration
      const validationRules = [
        {
          field: 'ten',
          control: 'txtTen',
          type: 'string',
          message: 'Tên loại vật liệu kết nối không được để trống.'
        },
        {
          field: 'loaivlId',
          control: 'selectLoaiVatLieu',
          type: 'select',
          message: 'Loại vật liệu không được để trống.'
        },
        {
          field: 'thongSo',
          control: 'txtThongSo',
          type: 'string',
          message: 'Thông số kỹ thuật không được để trống.'
        },
        {
          field: 'iconPath',
          control: 'txtIcon',
          type: 'icon',
          message: 'Icon không được để trống.'
        }
      ]

      // Validate each rule
      validationRules.forEach((rule) => {
        const value = this.form[rule.field]
        let isFieldValid = false

        if (rule.type === 'string') {
          isFieldValid = value && value.toString().trim() !== ''
        } else if (rule.type === 'select') {
          isFieldValid = value !== null && value !== undefined && value !== '' && value !== 0
        } else if (rule.type === 'icon') {
          isFieldValid = (value && value.toString().trim() !== '') || this.pendingIconFile
        }

        if (!isFieldValid) {
          this.controls[rule.control].invalid = true
          this.$root.toastError(rule.message)
          isValid = false
        }
      })

      return isValid
    },
    grdItems_RowSelected: async function(args) {
      if (this.buttons.btnSave.enabled) {
        return
      }
      try {
        this.$root.showLoading(true)
        this.setCurrentItem(args.data)
      } finally {
        this.$root.showLoading(false)
      }
    },

    // ====== API ======
    getDanhSachVLKN: async function() {
      let result = []
      try {
        let rs = await this.axios.get('/web-ecms/idc/loai-vatlieu-ketnoi')
        result = rs.data.data
      } catch (error) {
        this.handleApiError(error)
      } finally {
        this.dsVatLieuKetNoi = result
      }
    },
    async apiSave(formData) {
      try {
        const url = '/web-ecms/idc/loai-vatlieu-ketnoi'
        return await this.axios.post('/web-ecms/idc/loai-vatlieu-ketnoi', formData)
      } catch (error) {
        this.handleApiError(error)
        throw error
      }
    },

    apiDelete: async function(payload) {
      let result = 0
      try {
        let rs = await this.axios.delete('/web-ecms/idc/loai-vatlieu-ketnoi/delete-multi', {
          data: { ids: payload }
        })
        result = rs.data
      } catch (e) {
        this.handleApiError(e)
      }
      return result
    },

    handleApiError(e) {
      if (e?.data?.message_detail) {
        this.$root.toastError(e.data.message_detail)
      } else {
        console.error(e)
      }
    },

    // ====== Minio helpers ======
    async uploadFileToMinio(item) {
      try {
        this.$root.showLoading(true)
        let formData = new FormData()
        formData.append('file', item)
        let url = `/web-ecms/storage/uploadFile`
        let response = await this.$root.context.post(url, formData)
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          if (response.data.status) {
            return response.data.message
          } else return null
        } else return null
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi xảy ra trong quá trình upload file!')
        return null
      } finally {
        this.$root.showLoading(false)
      }
    },
    async getPresignedUrlFromMinio(src) {
      try {
        let url = `/web-ecms/storage/getPresignedUrl`
        let response = await this.$root.context.post(url, { fileSource: src })
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          return response.data.result
        } else return null
      } catch (error) {
        console.log(error)
        return null
      }
    },
    async deleteFileFromMinio(src) {
      try {
        this.$root.showLoading(true)
        let url = `/web-ecms/storage/deleteFile`
        let response = await this.$root.context.post(url, { fileSource: src })
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          if (!response.data.status) this.$toast.error(response.data.message)
          return response.data.status
        } else return false
      } catch (error) {
        console.log(error)
        return false
      } finally {
        this.$root.showLoading(false)
      }
    },

    // ====== TEMPLATE ====== //
    formatLoaiVLKNCellValue(value, fieldName) {
      if (value === null || value === undefined || value === '') return ''

      if (fieldName === 'suDung' && (value === 1 || value === 0)) {
        return value == 1 ? 'Hiệu lực' : 'Không hiệu lực'
      }

      return value
    },
    getLoaiVatLieuText: function(value) {
      switch (value) {
        case 1:
          return 'Cáp điện'
        case 2:
          return 'Busway'
        case 3:
          return 'Thanh cái'
        default:
          return 'Không xác định'
      }
    },

    getLoaiVatLieuTemplate: function(parent) {
      return function() {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function() {
                return parent.getLoaiVatLieuText(this.data.LOAIVATLIEU_ID)
              }
            }
          }
        }
      }
    },

    convertLoaiVatLieuNameToId(rawValue) {
      if (rawValue === undefined || rawValue === null) return rawValue
      const value = rawValue.toString().trim()
      if (value === '') return rawValue

      // Nếu là số thì giữ nguyên
      if (!isNaN(value)) return Number(value)

      const normName = value.toLowerCase()
      const byName = this.loaiVatLieuSelectionList.find((vl) => {
        const vlName = vl.TEN
          ? vl.TEN.toString()
              .trim()
              .toLowerCase()
          : ''
        return vlName === normName
      })
      return byName ? byName.ID : rawValue
    },

    // ====== VALIDATE ====== //
    validateVLKNExists() {
      return (value) => {
        const exists = this.loaiVatLieuSelectionList.some((ms) => ms.TEN?.toString().trim().toUpperCase() === value.toString().trim().toUpperCase())
        if (!exists) {
          return `Loại vật liệu ${value} không tồn tại trong danh mục`
        }
        return null
      }
    }
  }
}
</script>

<style>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

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

.preview img {
  max-width: 120px;
  border: 1px solid #ddd;
  padding: 2px;
  display: block;
}

/* Validation styles */
.is-invalid {
  border: 1px solid red;
  border-radius: 5px;
}

.required {
  color: #dc3545;
  font-weight: bold;
}

/* Loading state for image */
.image-loading {
  border-radius: 4px;
}

.image-loading .spinner-border-sm {
  width: 1.5rem;
  height: 1.5rem;
}

.fixed-icon-box {
  width: 100%;
  max-height: 130px;
  overflow: hidden !important;
}
</style>
