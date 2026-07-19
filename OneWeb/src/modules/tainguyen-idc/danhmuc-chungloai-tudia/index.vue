<template src="./index.template.html"></template>
<script>
import ModalImportExcel from '../components/Modals/ModalImportExcel'
import api from './api'
import {
  DEFAULT_FORM_ITEM,
  IMPORT_CONFIG,
  FULL_DEPTH_OPTIONS,
  FORM_VALIDATION_RULES,
  POSITIVE_NUMBER_FIELDS
} from './constants'

export default {
  name: 'DanhMucChungLoaiTuDia',
  components: {
    ModalImportExcel,
    ThongTinChiTiet: () => import('./ThongTinChiTiet/index.vue'),
    SearchForm: () => import('./Components/SearchForm/index.vue')
  },
  data() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      showDialog: false,
      currentMode: 'VIEW', // VIEW, ADD, EDIT
      dsSelected: [],
      dsChungLoaiTuDia: [],
      tab_index: 1,
      loaiTuDiaOptions: [],
      fullDepthOptions: FULL_DEPTH_OPTIONS,
      hangSanXuatList: [],
      vaiTroThietBiList: [],
      chuanDauNoiList: [],
      moduleList: [],
      mucDichSuDungList: [],
      loaiCongOptions: [],
      form: { ...DEFAULT_FORM_ITEM },
      buttons: {
        btnSearch: {
          enabled: true
        },
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
        selectLoaiTuDia: {
          enabled: false,
          invalid: false
        },
        txtModelThietBi: {
          enabled: false,
          invalid: false
        },
        selectHangSanXuat: {
          enabled: false,
          invalid: false
        },
        txtCongSuatDinhDanh: {
          enabled: false,
          invalid: false
        },
        selectVaiTroThietBi: {
          enabled: false,
          invalid: false
        },
        selectFullDepth: {
          enabled: false,
          invalid: false
        },
        txtSoBoDieuKhien: {
          enabled: false,
          invalid: false
        },
        txtCpu: {
          enabled: false,
          invalid: false
        },
        txtRam: {
          enabled: false,
          invalid: false
        },
        txtMaVatTuQlts: {
          enabled: false,
          invalid: false
        },
        txtSoCong: {
          enabled: false,
          invalid: false
        },
        txtTenCong: {
          enabled: false,
          invalid: false
        },
        txtLoaiCong: {
          enabled: false,
          invalid: false
        },
        txtBangThong: {
          enabled: false,
          invalid: false
        },
        selectModule: {
          enabled: false,
          invalid: false
        },
        selectMucDichSuDung: {
          enabled: false,
          invalid: false
        },
        txtSoKhayDia: {
          enabled: false,
          invalid: false
        },
        txtSoThuTu: {
          enabled: false,
          invalid: false
        },
        txtSoKheCamSSD: {
          enabled: false,
          invalid: false
        },
        txtSoKheCamHDD: {
          enabled: false,
          invalid: false
        },
        txtSoKheCamNvMe: {
          enabled: false,
          invalid: false
        },
        txtSoCongNguon: {
          enabled: false,
          invalid: false
        },
        txtTenCongNguon: {
          enabled: false,
          invalid: false
        },
        txtDongDinhDanh: {
          enabled: false,
          invalid: false
        },
        txtDienApDinhDanh: {
          enabled: false,
          invalid: false
        },
        selectChuanDauNoi: {
          enabled: false,
          invalid: false
        },
        txtSoLuongUCanDat: {
          enabled: false,
          invalid: false
        }
      },
      importConfig: IMPORT_CONFIG,
      enableWatchValidation: false
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    },
    saveButtonText() {
      return this.currentMode === 'ADD' ? 'Thêm mới' : 'Cập nhật'
    },
    getDataCols() {
      return [
        { fieldName: 'ID', headerText: 'ID', width: 50, textAlign: 'Center' },
        { fieldName: 'LOAITUDIA_TEN', headerText: 'Loại tủ đĩa', allowFiltering: true },
        { fieldName: 'MODEL_THIETBI', headerText: 'Model thiết bị', allowFiltering: true },
        { fieldName: 'HANGSX_TEN', headerText: 'Hãng sản xuất', allowFiltering: true },
        { fieldName: 'CONGSUAT', width: 120, headerText: 'Công suất định danh', allowFiltering: true },
        { fieldName: 'LOAIVAITRO_TEN', headerText: 'Vai trò thiết bị', allowFiltering: true },
        {
          fieldName: 'FULL_DEPTH_TEXT',
          width: 100,
          headerText: 'Full depth',
          allowFiltering: true,
          textAlign: 'Center'
        },
        {
          fieldName: 'ACTIONS',
          headerText: 'Thao tác',
          width: 120,
          textAlign: 'Center',
          template: this.getActionColumnTemplate()
        }
      ]
    }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      const [
        chungLoaiTuDiaList,
        loaiTuDiaList,
        hangSanXuatList,
        vaiTroThietBiList,
        chuanDauNoiList,
        moduleList,
        mucDichSuDungList,
        loaiCongList
      ] = await Promise.all([
        this.getDanhSachChungLoaiTuDia(),
        this.loadLoaiTuDiaList(),
        this.loadHangSanXuatList(),
        this.loadVaiTroThietBiList(),
        this.loadChuanDauNoiList(),
        this.loadModuleList(),
        this.loadMucDichSuDungList(),
        this.loadLoaiCongList()
      ])
      console.log(chungLoaiTuDiaList)
      this.dsChungLoaiTuDia = chungLoaiTuDiaList
      this.loaiTuDiaOptions = loaiTuDiaList
      this.hangSanXuatList = hangSanXuatList
      this.vaiTroThietBiList = vaiTroThietBiList
      this.chuanDauNoiList = chuanDauNoiList
      this.moduleList = moduleList
      this.mucDichSuDungList = mucDichSuDungList
      this.loaiCongOptions = loaiCongList
    } catch (error) {
      console.error('Error in created:', error)
      this.$root.toastError('Có lỗi khi khởi tạo dữ liệu')
    } finally {
      this.$root.showLoading(false)
    }
  },
  watch: {
    'form.id': function(value) {
      this.buttons.btnEdit.enabled = value > 0
      this.buttons.btnDelete.enabled = value > 0 && !this.buttons.btnSave.enabled
    },
    dsSelected: function(value) {
      if (!this.buttons.btnSave.enabled) {
        this.buttons.btnDelete.enabled = value && value.length > 0
      }
    }
  },

  methods: {
    onChangeTab(tabIndex) {
      this.tab_index = tabIndex
    },

    // Hàm xử lý dữ liệu Excel cho chủng loại tủ đĩa
    processChungLoaiTuDiaExcelData(dataRows) {
      const nonEmptyRows = dataRows.filter((row) => {
        return (
          row &&
          row.some((cell) => {
            return cell !== null && cell !== undefined && cell !== '' && cell.toString().trim() !== ''
          })
        )
      })
      return nonEmptyRows.map((row) => {
        return {
          loaiTuDia: row[0] ? row[0].toString().trim() : '',
          modelThietBi: row[1] ? row[1].toString().trim() : '',
          hangSanXuat: row[2] ? row[2].toString().trim() : '',
          congSuatDinhDanh: row[3] != null ? parseFloat(row[3]) : null,
          vaiTroThietBi: row[4] ? row[4].toString().trim() : '',
          fullDepth: row[5] != null ? parseInt(row[5]) : null,
          soBoDieuKhien: row[6] != null ? parseInt(row[6]) : null,
          cpu: row[7] != null ? parseInt(row[7]) : null,
          ram: row[8] != null ? parseInt(row[8]) : null
        }
      })
    },

    btnImportExcel_Click: async function() {
      try {
        const result = await this.$refs.modalImportExcel.showModal()

        if (result.success && result.data && result.data.length > 0) {
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.data.length} chủng loại tủ đĩa từ file Excel?`,
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
        ID: item.ID || '',
        LOAITUDIA_TEN: item.LOAITUDIA_TEN || '',
        MODEL_THIETBI: item.MODEL_THIETBI || '',
        HANGSX_TEN: item.HANGSX_TEN || '',
        CONGSUAT: item.CONGSUAT || '',
        LOAIVAITRO_TEN: item.LOAIVAITRO_TEN || '',
        FULL_DEPTH_TEXT: item.FULL_DEPTH_TEXT || ''
      }))

      const exportConfig = {
        fileName: 'DanhSachChungLoaiTuDia.xlsx',
        dataSource: exportData
      }

      this.grdItems.excelExport(exportConfig)
      this.$root.toastSuccess('Xuất dữ liệu thành công')
    },

    formatChungLoaiTuDiaCellValue(value, fieldName) {
      if (value === null || value === undefined || value === '') return ''

      // Format cho trường loại tủ đĩa
      if (fieldName === 'loaiTuDia') {
        const option = this.loaiTuDiaOptions.find((opt) => opt.ID === value || opt.ID === value.toString())
        return option ? option.TEN : value
      }

      // Format cho trường full depth
      if (fieldName === 'fullDepth') {
        const option = this.fullDepthOptions.find((opt) => opt.ID === value || opt.ID === parseInt(value))
        return option ? option.TEN : value
      }

      return value
    },

    processImportData: async function(importData) {
      try {
        this.$root.showLoading(true)

        const validItems = (importData || []).map((item) => {
          return {
            id: null,
            loaiTuDia: item.loaiTuDia?.trim() || '',
            modelThietBi: item.modelThietBi?.trim() || '',
            hangSanXuatId: this.convertHangSanXuatNameToId(item.hangSanXuat),
            congSuatDinhDanh: item.congSuatDinhDanh ? parseFloat(item.congSuatDinhDanh) : null,
            vaiTroThietBiId: this.convertVaiTroThietBiNameToId(item.vaiTroThietBi),
            fullDepth: item.fullDepth != null ? parseInt(item.fullDepth) : null,
            soBoDieuKhien: item.soBoDieuKhien ? parseInt(item.soBoDieuKhien) : null,
            cpu: item.cpu ? parseInt(item.cpu) : null,
            ram: item.ram ? parseInt(item.ram) : null
          }
        })

        await this.importChungLoaiTuDiaData(validItems)
      } finally {
        this.$root.showLoading(false)
      }
    },

    async handleCheckData(checkDataInfo) {
      try {
        const payload = (checkDataInfo?.data || []).map((row) => {
          return {
            id: '',
            loaiTuDia: row.loaiTuDia || '',
            modelThietBi: row.modelThietBi || '',
            hangSanXuatId: this.convertHangSanXuatNameToId(row.hangSanXuat),
            congSuatDinhDanh: row.congSuatDinhDanh ? parseFloat(row.congSuatDinhDanh) : null,
            vaiTroThietBiId: this.convertVaiTroThietBiNameToId(row.vaiTroThietBi),
            fullDepth: row.fullDepth != null ? parseInt(row.fullDepth) : null,
            soBoDieuKhien: row.soBoDieuKhien ? parseInt(row.soBoDieuKhien) : null,
            cpu: row.cpu ? parseInt(row.cpu) : null,
            ram: row.ram ? parseInt(row.ram) : null
          }
        })

        const response = await this.$root.context.post('/web-ecms/idc/chung-loai-tu-dia/validate-multi', payload)
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
        this.$refs.modalImportExcel.handleCheckDataResult({
          isValid: false,
          errors: msgDetail
        })
      }
    },

    // Helper function để tìm item theo ID hoặc tên trong danh sách
    findItemByIdOrName(value, list, idField = 'ID', nameField = 'TEN') {
      if (!list || list.length === 0) return null

      if (typeof value === 'number' || (!isNaN(value) && value.toString().trim() !== '')) {
        const id = typeof value === 'number' ? value : parseInt(value)
        const byId = list.find((item) => item[idField] === id)
        if (byId) return byId
      }

      const valueStr = value
        .toString()
        .trim()
        .toLowerCase()
      return (
        list.find((item) => {
          const itemName =
            item[nameField] == null
              ? ''
              : item[nameField]
                  .toString()
                  .trim()
                  .toLowerCase()
          return itemName === valueStr
        }) || null
      )
    },

    convertHangSanXuatNameToId(rawValue) {
      if (rawValue === undefined || rawValue === null || rawValue === '') return rawValue
      const matchedItem = this.findItemByIdOrName(rawValue, this.hangSanXuatList)
      return matchedItem ? matchedItem.ID : rawValue
    },

    convertVaiTroThietBiNameToId(rawValue) {
      if (rawValue === undefined || rawValue === null || rawValue === '') return rawValue
      const matchedItem = this.findItemByIdOrName(rawValue, this.vaiTroThietBiList)
      return matchedItem ? matchedItem.ID : rawValue
    },

    enableControls: function(args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key)) {
          this.controls[key].enabled = args
        }
      }

      this.buttons.btnSave.enabled = args
      this.buttons.btnAddNew.enabled = !args
      this.buttons.btnSearch.enabled = !args
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
            const isEdit = this.form.id
            let mess = isEdit == 0 ? 'Thêm mới chủng loại tủ đĩa thành công.' : 'Cập nhật chủng loại tủ đĩa thành công.'
            this.$root.showLoading(true)

            // Map to backend DTO fields (simple, no explicit casting)
            const dtoPayload = {
              chungloaitdId: isEdit ? this.form.id : null,
              maVt: this.form.maVatTu || null,
              loaitudiaId: this.form.loaiTuDia ?? null,
              modelThietbi: this.form.modelThietBi?.trim() || '',
              hangsxId: this.form.hangSanXuatId ?? null,
              congsuat: this.form.congSuatDinhDanh ?? null,
              loaivaitroId: this.form.vaiTroThietBiId ?? null,
              fullDepth: this.form.fullDepth ?? null,
              bodkSl: this.form.soBoDieuKhien ?? null,
              bodkCpu: this.form.cpu ?? null,
              bodkRam: this.form.ram ?? null,
              bodkMaVt: null,
              dcSocong: this.form.soCong ?? null,
              dcTencong: this.form.tenCong?.trim() || null,
              dcLoaicong: this.form.loaiCong ?? null,
              dcBangthong: this.form.bangThong ?? null,
              dcTtmoduleId: this.form.moduleId ?? null,
              dcMucdichsdId: this.form.mucDichSuDungId ?? null,
              kdSl: this.form.soKhayDia ?? null,
              kdMaTudia: this.form.maTuDiaCha?.trim() || null,
              kdStt: this.form.soThuTu ?? null,
              kdSlSsd: this.form.soKheCamSSD ?? null,
              kdSlHdd: this.form.soKheCamHDD ?? null,
              kdSlNvme: this.form.soKheCamNvMe ?? null,
              ppSocong: this.form.soCongNguon ?? null,
              ppTencong: this.form.tenCongNguon?.trim() || null,
              ppDongdien: this.form.dongDinhDanh ?? null,
              ppDienap: this.form.dienApDinhDanh ?? null,
              ppLoaind: null,
              ppChuandnId: this.form.chuanDauNoiId ?? null,
              soluongULd: this.form.soLuongUCanDat ?? null,
              sudung: this.form.suDung ?? 1,
              ghichu: this.form.ghiChu?.trim?.() || null,
              nguoiCn: null
            }
            let result = await this.upSertChungLoaiTuDia(dtoPayload)
            if (result) {
              this.$root.toastSuccess(mess)
              this.enableControls(false)
              this.closeModal()
            }
          } catch (error) {
            console.error('Error in onSave:', error)
          } finally {
            this.dsChungLoaiTuDia = await this.getDanhSachChungLoaiTuDia()
            this.$root.showLoading(false)
          }
        }
      }
    },

    onAddNew: async function() {
      this.setCurrentItemDefault()
      this.currentMode = 'ADD'
      this.tab_index = 1
      this.enableControls(true)
      this.showDialog = true
      this.$nextTick(() => {
        if (this.$refs.dialogChungLoaiTuDia) {
          this.$refs.dialogChungLoaiTuDia.show()
        }
      })
    },
    onEdit: async function(args) {
      if (this.form.id === 0) {
        this.$root.toastError('Vui lòng chọn một chủng loại tủ đĩa để sửa')
        return
      }
      this.resetValidation()
      this.enableWatchValidation = false
      this.currentMode = 'EDIT'
      this.tab_index = 1
      this.enableControls(true)
      this.showDialog = true
      this.$nextTick(() => {
        if (this.$refs.dialogChungLoaiTuDia) {
          this.$refs.dialogChungLoaiTuDia.show()
        }
      })
    },
    closeModal: function() {
      this.showDialog = false
      if (this.$refs.dialogChungLoaiTuDia) {
        this.$refs.dialogChungLoaiTuDia.hide()
      }
      // Reset về trạng thái view
      if (this.currentMode !== 'VIEW') {
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
      }
      this.currentMode = 'VIEW'
      this.tab_index = 1
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
          let isSuccess = await this.deleteChungLoaiTuDia(ids)
          if (isSuccess) {
            this.setCurrentItemDefault()
            this.tab_index = 1
            this.enableControls(false)
            this.dsSelected = []
            this.buttons.btnDelete.enabled = false
            this.$root.toastSuccess('Xóa thông tin thành công')
          }
        } finally {
          this.dsChungLoaiTuDia = await this.getDanhSachChungLoaiTuDia()
          this.$root.showLoading(false)
        }
      }
    },

    setCurrentItem: async function(item) {
      this.form.id = item.ID || 0
      this.form.maVatTu = item.MA_VT || null
      this.form.loaiTuDia = item.LOAITUDIA_ID || null
      this.form.modelThietBi = item.MODEL_THIETBI || ''
      this.form.hangSanXuatId = item.HANGSX_ID || null
      this.form.congSuatDinhDanh = item.CONGSUAT || null
      this.form.vaiTroThietBiId = item.LOAIVAITRO_ID || null
      this.form.fullDepth = item.FULL_DEPTH != null ? item.FULL_DEPTH : null
      // Bo điều khiển
      this.form.soBoDieuKhien = item.BODK_SL || null
      this.form.cpu = item.BODK_CPU || null
      this.form.ram = item.BODK_RAM || null
      // Data Card
      this.form.soCong = item.DC_SOCONG || null
      this.form.tenCong = item.DC_TENCONG || ''
      this.form.loaiCong = item.DC_LOAICONG != null ? Number(item.DC_LOAICONG) : null
      this.form.bangThong = item.DC_BANGTHONG || null
      this.form.moduleId = item.DC_TTMODULE_ID || null
      this.form.mucDichSuDungId = item.DC_MUCDICHSD_ID || null
      // Khối đĩa
      this.form.soKhayDia = item.KD_SL || null
      this.form.maTuDiaCha = item.KD_MA_TUDIA || ''
      this.form.soThuTu = item.KD_STT || null
      this.form.soKheCamSSD = item.KD_SL_SSD || null
      this.form.soKheCamHDD = item.KD_SL_HDD || null
      this.form.soKheCamNvMe = item.KD_SL_NVME || null
      // Power Port
      this.form.soCongNguon = item.PP_SOCONG || null
      this.form.tenCongNguon = item.PP_TENCONG || ''
      this.form.dongDinhDanh = item.PP_DONGDIEN || ''
      this.form.dienApDinhDanh = item.PP_DIENAP || null
      this.form.chuanDauNoiId = item.PP_CHUANDN_ID || null
      // Khác
      this.form.soLuongUCanDat = item.SOLUONG_U_LD || null
      this.currentMode = 'VIEW'
    },
    setCurrentItemDefault: function() {
      this.form = { ...DEFAULT_FORM_ITEM }
      this.tab_index = 1
      this.currentMode = 'ADD'
      this.resetValidation()
      this.enableWatchValidation = false
      this.enableControls(false)
      this.dsSelected = []
      this.buttons.btnDelete.enabled = false
    },
    resetValidation: function() {
      Object.keys(this.controls).forEach((key) => {
        this.controls[key].invalid = false
      })
    },
    validateControls: function() {
      this.enableWatchValidation = true
      let isValid = true

      this.resetValidation()

      const validationRules = [...FORM_VALIDATION_RULES]

      validationRules.forEach((rule) => {
        const value = this.form[rule.field]
        let isFieldValid = false

        if (rule.type === 'string') {
          isFieldValid = value && value.toString().trim() !== ''
        } else if (rule.type === 'select') {
          isFieldValid = value !== null && value !== undefined && value !== '' && value !== 0
        } else if (rule.type === 'number') {
          isFieldValid = value !== null && value !== undefined && value !== '' && !isNaN(value) && value > 0
        }

        if (!isFieldValid) {
          this.controls[rule.control].invalid = true
          this.$root.toastError(rule.message)
          isValid = false
        }
      })

      // Validate positive numbers (if provided)
      POSITIVE_NUMBER_FIELDS.forEach((rule) => {
        const value = this.form[rule.field]
        if (value !== null && value !== undefined && value !== '' && !isNaN(value) && Number(value) <= 0) {
          if (this.controls[rule.control]) this.controls[rule.control].invalid = true
          this.$root.toastError(rule.message)
          isValid = false
        }
      })

      return isValid
    },
    grdItems_RowSelected: async function(args) {
      if (this.showDialog && this.currentMode !== 'VIEW') {
        return // Không cho phép thay đổi khi đang edit/add
      }

      try {
        this.$root.showLoading(true)
        this.setCurrentItem(args.data)
        this.tab_index = 1
        // Nếu đang ở chế độ view và chưa có dialog mở, có thể mở dialog để xem chi tiết
        // (tùy chọn, có thể bỏ qua nếu không muốn tự động mở)
      } finally {
        this.$root.showLoading(false)
      }
    },

    // ====== API ====== //

    getDanhSachChungLoaiTuDia: async function(criteria) {
      try {
        return await api.getDanhSachChungLoaiTuDia(this.$root.context, criteria || {})
      } catch (error) {
        this.handleApiError(error)
        return []
      }
    },

    onSearch: async function(criteria) {
      this.$root.showLoading(true)
      try {
        this.dsChungLoaiTuDia = await this.getDanhSachChungLoaiTuDia(criteria)
      } finally {
        this.$root.showLoading(false)
      }
    },

    onResetSearch: async function() {
      this.$root.showLoading(true)
      try {
        this.dsChungLoaiTuDia = await this.getDanhSachChungLoaiTuDia({})
      } finally {
        this.$root.showLoading(false)
      }
    },

    viewItem: function(row) {
      if (!row) return
      this.setCurrentItem(row)
      this.currentMode = 'VIEW'
      this.showDialog = true
      this.$nextTick(() => {
        if (this.$refs.dialogChungLoaiTuDia) this.$refs.dialogChungLoaiTuDia.show()
      })
    },
    editItem: function(row) {
      if (!row) return
      this.setCurrentItem(row)
      this.onEdit()
    },
    // Action column template similar to ThongTinThietBiV2/config.js
    getActionColumnTemplate() {
      const parent = this
      return function() {
        return {
          template: {
            template: `
              <div class="d-flex align-items-center justify-content-center">
                <b-button size="sm" variant="outline-primary" @click="onView()">
                  <i class="fa fa-eye"></i>
                </b-button>
                <b-button size="sm" variant="outline-success" class="ml-1" @click="onEdit()">
                  <i class="fa fa-edit"></i>
                </b-button>
              </div>
            `,
            methods: {
              onView() {
                parent.viewItem(this.data)
              },
              onEdit() {
                parent.editItem(this.data)
              }
            }
          }
        }
      }
    },

    upSertChungLoaiTuDia: async function(payload) {
      try {
        return await api.upSertChungLoaiTuDia(this.$root.context, payload)
      } catch (e) {
        this.handleApiError(e)
        return false
      }
    },

    deleteChungLoaiTuDia: async function(payload) {
      try {
        return await api.deleteChungLoaiTuDia(this.$root.context, payload)
      } catch (e) {
        this.handleApiError(e)
        return false
      }
    },

    async loadHangSanXuatList() {
      try {
        return await api.getDanhMucHangSanXuat(this.$root.context)
      } catch (e) {
        this.handleApiError(e)
        return []
      }
    },

    async loadVaiTroThietBiList() {
      try {
        return await api.getDanhMucVaiTroThietBi(this.$root.context)
      } catch (e) {
        this.handleApiError(e)
        return []
      }
    },

    async loadLoaiTuDiaList() {
      try {
        return await api.getDanhMucLoaiTuDia(this.$root.context)
      } catch (e) {
        this.handleApiError(e)
        return []
      }
    },

    async loadChuanDauNoiList() {
      try {
        return await api.getDanhMucChuanDauNoi(this.$root.context)
      } catch (e) {
        this.handleApiError(e)
        return []
      }
    },

    async loadModuleList() {
      try {
        return await api.getDanhMucModule(this.$root.context)
      } catch (e) {
        this.handleApiError(e)
        return []
      }
    },

    async loadMucDichSuDungList() {
      try {
        return await api.getDanhMucMucDichSuDung(this.$root.context)
      } catch (e) {
        this.handleApiError(e)
        return []
      }
    },

    async loadLoaiCongList() {
      try {
        return await api.getDanhMucLoaiCong(this.$root.context)
      } catch (e) {
        this.handleApiError(e)
        return []
      }
    },

    async importChungLoaiTuDiaData(data) {
      if (!data || data.length === 0) {
        this.$root.toastError('Không có dữ liệu hợp lệ để import!')
        return false
      }
      let successCount = 0
      let errorCount = 0
      try {
        const result = await api.importChungLoaiTuDiaData(this.$root.context, data)
        successCount = result.successCount || 0
        errorCount = result.failCount || 0
      } catch (err) {
        this.$root.toastError(`Import lỗi`)
        console.error(err)
      } finally {
        this.dsChungLoaiTuDia = await this.getDanhSachChungLoaiTuDia()
        if (successCount > 0) {
          this.$root.toastSuccess(`Import thành công ${successCount} chủng loại tủ đĩa`)
        }
        if (errorCount > 0) {
          this.$root.toastError(`Import thất bại ${errorCount} chủng loại tủ đĩa. Vui lòng kiểm tra lại dữ liệu.`)
        }
      }
      return true
    },

    handleApiError(e) {
      if (e?.data?.message_detail) {
        this.$root.toastError(e.data.message_detail)
      } else {
        console.error(e)
      }
    }
  }
}
</script>

<style scoped>
.w200 {
  width: 200px !important;
}

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

.invalidBox {
  border: 1px solid red;
  border-radius: 5px;
}
</style>
