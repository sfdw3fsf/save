<template src="./index.template.html"> </template>
<script>
import Vue from 'vue'
import ModalImportExcel from '../../components/Modals/ModalImportExcel'
import ImageSelector from '../../components/ImageSelector/ImageSelector.vue'
import api from '../api'
import moment from 'moment'
import { buildController, rules as R, createControls } from '../controls'

export default {
  name: 'ModalDanhMucToaNha',
  props: {
    parentId: {
      //(VD: Component Phòng/Zone => parentId: ID Mặt sàn)
      type: Number
    },
    parentType: {
      //(VD: Component Phòng/Zone => parentType: 'MATSAN')
      type: String
    },
    itemId: {
      //(VD: Component Phòng/Zone => itemId: ID Phòng/Zone cần show chi tiết/ sửa)
      type: Number
    },
    itemType: {
      //(VD: Component Phòng/Zone => itemType: 'PHONG/ZONE')
      type: String
    },
    mode: {
      //(VD: 'ADD', 'EDIT', 'VIEW')
      type: String
    }
  },
  components: {
    ModalImportExcel,
    ImageSelector
  },
  data() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      listIDC: [],
      listDonViIDC: [],
      listMucDichSuDung: [],
      listTrangThaiVanHanh: [],
      dataItems: [],
      selectedRows: [],
      newBtn: true,
      saveBtn: false,
      editBtn: true,
      cancelBtn: false,
      deleteBtn: true,
      searchParams: {
        ten: '',
        idDonVi: '',
        suDung: null
      },
      importConfig: {
        title: 'Import dữ liệu tòa nhà',
        templateFileName: 'Template_Import_ToaNha.xlsx',
        tableColumns: [
          { key: 'IDC_ID', label: 'ID IDC' },
          { key: 'TEN', label: 'Tên tòa nhà' },
          { key: 'MUC_DICH_SU_DUNG', label: 'Mục đích sử dụng (1: Cung cấp dịch vụ, 2: Phụ trợ, 3: Khác)' },
          { key: 'THOI_DIEM_VAN_HANH', label: 'Thời điểm vận hành' },
          {
            key: 'TRANG_THAI_VAN_HANH',
            label:
              'Trạng thái vận hành (10: Hoạt động bình thường, 11: Bảo trì, 12: Cảnh báo, 13: Sự cố, 14: Khôi phục )'
          },
          { key: 'DIEN_TICH', label: 'Diện tích' },
          // ==== Location
          { key: 'TOADO_X', label: 'Tọa độ X' },
          { key: 'TOADO_Y', label: 'Tọa độ Y' },
          { key: 'GHICHU', label: 'Ghi chú' },
          { key: 'SUDUNG', label: 'Hiệu lực (0: Không hiệu lực, 1: Có hiệu lực)' }
        ],
        gridColumns: [
          { key: 'IDC_ID', label: 'ID IDC' },
          { key: 'TEN', label: 'Tên tòa nhà' },
          { key: 'MUC_DICH_SU_DUNG', label: 'Mục đích sử dụng' },
          { key: 'THOI_DIEM_VAN_HANH', label: 'Thời điểm vận hành' },
          { key: 'TRANG_THAI_VAN_HANH', label: 'Trạng thái vận hành' },
          { key: 'DIEN_TICH', label: 'Diện tích' },
          // ==== Location
          { key: 'TOADO_X', label: 'Tọa độ X' },
          { key: 'TOADO_Y', label: 'Tọa độ Y' },
          { key: 'GHI_CHU', label: 'Ghi chú' },
          { key: 'SUDUNG', label: 'Hiệu lực' }
        ],
        validationRules: [
          { field: 'IDC_ID', validator: null },
          { field: 'IDC_ID', required: true, message: 'ID IDC không được để trống' },
          { field: 'IDC_ID', type: 'number', required: true, message: 'ID IDC chỉ nhận số' },
          { field: 'TEN', validator: null },
          { field: 'TEN', required: true, message: 'Tên tòa nhà không được để trống' },
          { field: 'TEN', maxLength: 200, message: 'Tên tòa nhà không được vượt quá 200 ký tự' },
          { field: 'MUC_DICH_SU_DUNG', required: true, message: 'Mục đích sử dụng không được để trống' },
          {
            field: 'MUC_DICH_SU_DUNG',
            type: 'number',
            enum: [1, 2, 3],
            message: 'Mục đích sử dụng chỉ nhận số trong khoảng quy định'
          },
          { field: 'THOI_DIEM_VAN_HANH', type: 'date', message: 'Thời điểm vận hành sai định dạng ngày' },
          {
            field: 'TRANG_THAI_VAN_HANH',
            type: 'number',
            enum: [10, 11, 12, 13, 14],
            message: 'Trạng thái vận hành chỉ nhận số trong khoảng quy định'
          },
          { field: 'DIEN_TICH', type: 'number', message: 'Diện tích chỉ nhận số' },
          { field: 'SUDUNG', type: 'number', required: true, enum: [1, 0], message: 'Hiệu lực chỉ nhận số 0 hoặc 1' },
          { field: 'GHI_CHU', maxLength: 500, message: 'Ghi chú không được vượt quá 500 ký tự' }
        ],
        checkDataResult: true
      },
      // Lưu trữ dữ liệu import để validator có thể truy cập
      currentImportData: [],
      // Lưu file ảnh tạm để upload khi nhấn Ghi lại
      tempImageFile: null,
      textThoiDiemVanHanh: '',
      thoiDiemVanHanhHasInteraction: false,
      controls: createControls([
        'id',
        'suDung',
        'tenToaNha',
        'mucDichSuDung',
        'thoiDiemVanHanh',
        'trangThaiVanHanh',
        'dienTichTongThe',
        'anhDaiDien',
        // 'viTriToaNha',
        'toaDoX',
        'toaDoY',
        'ghiChu',
        'idDonViIDC',
        'idIDC'
      ]),
      currentItem: {}
    }
  },
  watch: {
    'currentItem.thoiDiemVanHanh'(newValue) {
      if (
        this.thoiDiemVanHanhHasInteraction &&
        newValue === null &&
        this.textThoiDiemVanHanh &&
        this.textThoiDiemVanHanh.trim() !== ''
      ) {
        this.controls.thoiDiemVanHanh.invalid = true
      }
    }
  },
  computed: {
    idcIdSet() {
      return new Set(this.listIDC.map((idc) => idc.id?.toString()))
    }
  },
  created() {
    // Init controls controller with validation rules
    this.controlsController = buildController(this.controls, {
      tenToaNha: [R.required()],
      mucDichSuDung: [R.required()],
      idDonViIDC: [R.required()],
      idIDC: [R.required()],
      thoiDiemVanHanh: [R.date()],
      dienTichTongThe: [R.number(), R.min(0)]
    })

    // Gán validator thực tế cho rule vừa khai báo trong importConfig
    const idx = this.importConfig.validationRules.findIndex((r) => r.field === 'IDC_ID' && r.validator === null)
    if (idx !== -1) {
      this.importConfig.validationRules[idx].validator = this.validateIDCExists()
    }

    const idxTen = this.importConfig.validationRules.findIndex((r) => r.field === 'TEN' && r.validator === null)
    if (idxTen !== -1) {
      this.importConfig.validationRules[idxTen].validator = this.validateTenToaNhaDuplicate()
    }

    const idxThoiDiem = this.importConfig.validationRules.findIndex(
      (r) => r.field === 'THOI_DIEM_VAN_HANH' && r.validator === null
    )
    if (idxThoiDiem !== -1) {
      this.importConfig.validationRules[idxThoiDiem].validator = this.validateDateFormat()
    }
    if (this.parentId) {
      this.setToNewOrEditMode()
    }
  },
  mounted: async function() {
    await this.loadDanhMuc()
    if (this.mode == 'ADD') {
      this.newBtn = true
      this.saveBtn = false
      this.editBtn = false
      this.cancelBtn = true
      this.clearDataForm()
      this.currentItem.idIDC = this.parentId
      const idc = this.listIDC.find((item) => Number(item.ID) == Number(this.itemId))
      // const donviIdc = this.listDonViIDC.find(item => item.ID = idc.DONVI_CHA_ID)
      // this.currentItem.idDonViIDC = donviIdc.ID
      this.currentItem.idIDC = idc.ID
      this.controls['idDonViIDC'].enabled = false
      this.controls['idIDC'].enabled = false
    } else if (this.mode == 'EDIT') {
      await this.loadData()
      const editItem = this.dataItems.find((item) => item.ID_TOANHA === this.itemId)
      this.fillDataForm(editItem)
      this.controls['idDonViIDC'].enabled = false
      this.controls['idIDC'].enabled = false
    } else {
      await this.loadData()
      const editItem = this.dataItems.find((item) => item.ID_TOANHA === this.itemId)
      this.fillDataForm(editItem)
      this.disableControls()
    }
    await this.initFormData()
  },
  methods: {
    // ============= utilities =============
    closeModal: function() {
      this.$refs.ModalDanhMucToaNha.hide(this.modalID)
    },
    show: function(parentId, parentType, itemId, itemType, mode) {
      // if (this.mode == 'ADD') {
      //   this.newBtn = true
      //   this.saveBtn = false
      //   this.editBtn = false
      //   this.cancelBtn = true
      //   this.clearDataForm();
      //   this.currentItem.idIDC = parentId
      //   const idc = this.listIDC.find(item => item.ID == itemId)
      //   const donviIdc = this.donViRes.find(item => item.ID = idc.DONVI_CHA_ID)
      //   this.currentItem.idDonViIDC = donviIdc.ID
      //   this.currentItem.idIDC = idc.ID
      //   this.controls['idDonViIDC'].enabled = false
      //   this.controls['idIDC'].enabled = false
      // } else if (this.mode == 'EDIT') {
      //   const editItem = this.dataItems.find(item => item.ID_TOANHA === itemId);
      //   this.fillDataForm(editItem)
      //   this.controls['idDonViIDC'].enabled = false
      //   this.controls['idIDC'].enabled = false
      // } else {
      //   const editItem = this.dataItems.find(item => item.ID_TOANHA === itemId);
      //   this.fillDataForm(editItem)
      //   this.disableControls();
      // }
      // this.$refs.ModalDanhMucToaNha.show();
    },
    formatDate: function(date) {
      if (date instanceof Date) {
        const day = date
          .getDate()
          .toString()
          .padStart(2, '0')
        const month = (date.getMonth() + 1).toString().padStart(2, '0')
        const year = date.getFullYear()
        return `${day}/${month}/${year}`
      }

      return date
    },

    fillDataForm: function(data) {
      this.currentItem = {
        id: data.ID_TOANHA,
        tenToaNha: data.TEN_TOANHA || '',
        idIDC: data.ID_IDC || null,
        tenIDC: data.TEN_IDC || '',
        idDonViIDC: data.ID_DONVI || null,
        mucDichSuDung: data.ID_MUCDICHSD || null,
        tenMucDichSuDung: data.TEN_MUCDICHSD || '',
        thoiDiemVanHanh: data.NGAY_SD ? moment(data.NGAY_SD, 'DD/MM/YYYY').toDate() : null,
        trangThaiVanHanh: data.ID_TRANGTHAI_VH || null,
        tenTrangThaiVanHanh: data.TEN_TRANGTHAI_VH || '',
        dienTichTongThe: data.DIENTICH || null,
        // ==== Location
        toaDoX: data.TOADO_X || '',
        toaDoY: data.TOADO_Y || '',
        tongDienTichPhongMay: data.DIENTICH_PHONGMAY || null,
        tongSoLuongRackThietKe: data.TONG_RACK_THIETKE || null,
        tongSoLuongRackLapDat: data.TONG_RACK_DA_LAPDAT || null,
        tongSoLuongRackConLai: data.TONG_RACK_CON || null,
        congSuatDinhDanh: data.CS_DINHDANH_TB || null,
        anhDaiDien: data.HINHANH || '',
        ghiChu: data.GHI_CHU || '',
        suDung: data.SUDUNG || 1
      }

      console.log('Current item:', this.currentItem)
      this.$nextTick(() => {
        this.initFormData()
      })
    },

    formatCurrentItem: function(currentItem) {
      const res = {
        id: currentItem.id,
        tenToaNha: currentItem.tenToaNha,
        idIDC: currentItem.idIDC ? Number(currentItem.idIDC) : null,
        idDonViIDC: currentItem.idDonViIDC ? Number(currentItem.idDonViIDC) : null,
        mucDichSuDung: currentItem.mucDichSuDung ? Number(currentItem.mucDichSuDung) : null,
        thoiDiemVanHanh: currentItem.thoiDiemVanHanh ? moment(currentItem.thoiDiemVanHanh).format('DD/MM/YYYY') : null,
        trangThaiVanHanh: currentItem.trangThaiVanHanh ? Number(currentItem.trangThaiVanHanh) : null,
        dienTichTongThe: currentItem.dienTichTongThe ? Number(currentItem.dienTichTongThe) : null,
        // ==== Location
        toaDoX: Number(currentItem.toaDoX),
        toaDoY: Number(currentItem.toaDoY),
        anhDaiDien: currentItem.anhDaiDien,
        ghiChu: currentItem.ghiChu,
        suDung: currentItem.suDung ? Number(currentItem.suDung) : null,
        mota: currentItem.mota,
        tentat: currentItem.tentat
      }
      console.log('Formatted item to save:', res)
      return res
    },

    clearDataForm: function() {
      this.currentItem = { mucDichSuDung: 1, suDung: 1 }
      this.textThoiDiemVanHanh = ''
      this.thoiDiemVanHanhHasInteraction = false
    },

    // Validate form
    validateForm: function() {
      const dateValidation = this.validateThoiDiemVanHanhForSubmit()
      if (!dateValidation.valid) {
        this.$toast.error(dateValidation.error)
        return false
      }
      const { isValid, errors } = this.controlsController.validate(this.currentItem)
      if (!isValid) {
        this.$toast.error('Vui lòng kiểm tra lại các trường dữ liệu')
      }
      return isValid
    },

    disableControls: function() {
      this.controlsController?.setEnabledAll(false)
    },

    enableControls: function() {
      this.controlsController?.setEnabledAll(true)
      this.controls.id.enabled = false
    },

    setToDefault: function() {
      this.newBtn = true
      this.saveBtn = false
      this.editBtn = true
      this.cancelBtn = false
      this.deleteBtn = true
      this.tempImageFile = null
      this.disableControls()
    },

    setToNewOrEditMode: function() {
      this.newBtn = false
      this.saveBtn = true
      this.editBtn = false
      this.cancelBtn = true
      this.deleteBtn = false

      // this.selectedRows = this.$refs.gridItems.$refs.grid.getSelectedRecords();
      this.enableControls()
    },

    onRowSelected: function(selectedRow) {
      if (this.currentItem.mucDichSuDung == 1) {
      } else {
        console.log('Selected row:', selectedRow)
        this.fillDataForm(selectedRow.data)
      }
    },

    mapToIDC: function(field, data, column) {
      if (field === 'ID_IDC') {
        return this.listIDC.find((item) => item.id === data.ID_IDC)?.text || ''
      }

      return data[field]
    },

    mapToHieuLuc: function(field, data, column) {
      if (field === 'SUDUNG') {
        return data.SUDUNG === 1 ? 'Có hiệu lực' : 'Không hiệu lực'
      }
      return data[field]
    },

    // ============= load data =============

    loadDanhMuc: async function() {
      // use Promise
      const [donViRes, idcRes, mucDichRes, trangThaiRes] = await Promise.all([
        api.getDanhMuc(this.axios, 'IDC-DONVI-QLY'),
        api.getDanhMuc(this.axios, 'IDC-DS-IDC', null, 'VIEW'),
        api.getDanhMuc(this.axios, 'IDC-MUCDICH-SD-TOANHA'),
        api.getDanhMuc(this.axios, 'IDC-TTVH-KHONGGIAN')
      ])

      this.listIDC = idcRes || []
      this.listDonViIDC = donViRes || []
      this.listMucDichSuDung = mucDichRes || []
      this.listTrangThaiVanHanh = trangThaiRes || []
    },

    loadData: async function() {
      try {
        this.$root.loading(true)
        const res = await api.getList(this.axios)

        if (res.data.error == 200) {
          this.dataItems = res.data.data || []
        } else {
          this.$toast.error('Lỗi khi tải dữ liệu')
        }
      } catch (error) {
        console.log('Lỗi khi tải dữ liệu', error)
      } finally {
        this.$root.loading(false)
      }
    },

    onDonViIDCChange: async function(newDonVi) {
      this.listIDC = await api.getDanhMuc(this.axios, 'IDC-DS-IDC', newDonVi)
    },

    // ======== handle button clicks ========

    onClickNhapMoi: function() {
      this.clearDataForm()
      this.setToNewOrEditMode()
    },

    onClickSua: function() {
      this.setToNewOrEditMode()
    },

    onClickGhiLai: async function() {
      if (this.validateForm()) {
        this.$root.loading(true)

        try {
          if (this.tempImageFile) {
            await this.handleImageUploaded()
          }
          const res = await api.save(this.axios, this.formatCurrentItem(this.currentItem))

          if (res.data.error == 200) {
            this.$toast.success('Lưu thành công')
            this.$emit('saveSuccessed', true)

            this.setToDefault()
          }
        } catch (error) {
          this.$emit('saveSuccessed', false)
          this.$toast.error(error.data?.message_detail || error.message || 'Lỗi khi lưu dữ liệu')
        } finally {
          this.$root.loading(false)
          this.closeModal()
        }
      }
    },

    onClickXoa: async function () {
      const confirmed = await this.$confirm(`Bạn có chắc chắn muốn xóa tòa nhà đã chọn?`, 'Xác nhận xóa', {
          confirmButtonText: 'Xóa',
          cancelButtonText: 'Hủy',
          type: 'warning'
        })

        if (confirmed) {
          try {
            const res = await api.delete(this.axios, this.itemId)

            if (res && res.data && res.data.error === 200) {
              this.$toast.success('Xóa tòa nhà thành công')
            }
          } catch (error) {
            this.$toast.error(error.data?.message_detail || 'Lỗi khi xóa dữ liệu')
          }
        }
    },

    onClickHuy: function() {
      this.setToDefault()
      this.selectedRows = this.$refs.gridItems.$refs.grid.getSelectedRecords()
      if (this.selectedRows && this.selectedRows.length > 0) {
        this.fillDataForm(this.selectedRows[0])
      }
    },

    // ======== handle import/export ========

    onClickExport: async function() {
      this.$refs.gridItems.excelExport()
    },

    onClickImport: function() {
      this.btnImportExcel_Click()
    },

    validateDateFormat() {
      const isValidDate = (value) => {
        if (typeof value !== 'string') return false
        const regex = /^\d{2}\/\d{2}\/\d{4}$/
        if (!regex.test(value)) return false
        const [ddStr, mmStr, yyyyStr] = value.split('/')
        const dd = parseInt(ddStr, 10)
        const mm = parseInt(mmStr, 10)
        const yyyy = parseInt(yyyyStr, 10)
        if (mm < 1 || mm > 12) return false
        if (dd < 1 || dd > 31) return false
        const daysInMonth = new Date(yyyy, mm, 0).getDate()
        return dd <= daysInMonth
      }
      return (value) => {
        console.log('value thoidiem', value)
        if (value == null || value === '') return ''
        return isValidDate(value) ? '' : 'Thời điểm vận hành phải có định dạng dd/MM/yyyy hợp lệ'
      }
    },

    // Validator factory để kiểm tra IDC có tồn tại
    validateIDCExists() {
      return (value) => {
        const isValid = this.idcIdSet.has(value.toString())
        return isValid ? '' : `IDC "${value}" không tồn tại trong hệ thống`
      }
    },

    // Validator: kiểm tra tên tòa nhà trùng lặp trong file import
    validateTenToaNhaDuplicate() {
      return (value, rowData) => {
        if (!value || value.trim() === '') return ''
        if (!this.currentImportData || !Array.isArray(this.currentImportData)) return ''

        const trimmedValue = value.trim().toLowerCase()
        const duplicateRows = []

        // Tìm các dòng có tên tòa nhà trùng lặp
        this.currentImportData.forEach((row, index) => {
          if (row && row.TEN && row.TEN.trim().toLowerCase() === trimmedValue) {
            const currentRowIndex = rowData.stt - 1
            if (index !== currentRowIndex) {
              duplicateRows.push(index + 1) // +1 vì dòng bắt đầu từ 1
            }
          }
        })

        if (duplicateRows.length > 0) {
          return `Tên tòa nhà "${value}" trùng với dòng ${duplicateRows.join(', ')}`
        }

        return ''
      }
    },

    // Hàm xử lý dữ liệu Excel cho tòa nhà
    processToaNhaExcelData(dataRows) {
      const processedData = dataRows.map((row, index) => {
        // Convert Excel serial date to dd/MM/yyyy if needed
        let thoiDiem = row[3] || ''
        if (typeof thoiDiem === 'number' && thoiDiem > 0) {
          const excelEpoch = new Date(1900, 0, 1)
          const date = new Date(excelEpoch.getTime() + (thoiDiem - 1) * 24 * 60 * 60 * 1000)
          const day = date
            .getDate()
            .toString()
            .padStart(2, '0')
          const month = (date.getMonth() + 1).toString().padStart(2, '0')
          const year = date.getFullYear()
          thoiDiem = `${day}/${month}/${year}`
        }

        return {
          IDC_ID: row[0] || '',
          TEN: row[1] || '',
          MUC_DICH_SU_DUNG: row[2] || '',
          THOI_DIEM_VAN_HANH: thoiDiem,
          TRANG_THAI_VAN_HANH: row[4] || '',
          DIEN_TICH: row[5] || '',
          // ==== Location
          TOADO_X: row[6] || '',
          TOADO_Y: row[7] || '',
          GHI_CHU: row[8] || '',
          SUDUNG: row[9] || 1
        }
      })

      // Lưu dữ liệu để validator có thể truy cập
      this.currentImportData = processedData
      return processedData
    },

    formatToaNhaCellValue(value, fieldName) {
      if (value === null || value === undefined || value === '') return ''

      // Format cho trường hiệu lực
      if (fieldName === 'suDung' && typeof value === 'number' && (value === 1 || value === 0)) {
        return value == 1 ? 'Hiệu lực' : 'Không hiệu lực'
      }

      return value
    },

    // Khi click button "kiểm tra"
    async handleCheckData(checkDataInfo) {
      this.$root.loading(true)
      try {
        const payload = (checkDataInfo?.data || []).map((row) => ({
          ...row,
          id: null,
          idIDC: row.idIDC,
          mota: null,
          tentat: null
        }))
        const res = await api.validate(this.axios, payload)

        if (res.data.error == 200) {
          this.dbInValidRows = res.data.data.invalidRows
          const dataTypeErrors = res.data.data.dataTypeError || []
          const businessLogicErrors = res.data.data.businessLogicError

          if (
            this.dbInValidRows === 0 &&
            dataTypeErrors.length === 0 &&
            (businessLogicErrors == null || businessLogicErrors.length === 0)
          ) {
            this.$toast.success('File hợp lệ, bạn có thể ghi lại')
            this.importConfig.checkDataResult = false
          } else {
            if (dataTypeErrors.length > 0) {
              dataTypeErrors.forEach((err) => {
                this.$toast.error(err)
              })
            }

            if (businessLogicErrors != null && businessLogicErrors.length != undefined) {
              const errorList = businessLogicErrors.split('\n')

              errorList.forEach((err) => {
                if (err && err.trim() !== '') this.$toast.error(err)
              })
            }

            // emit cho modalImportExcel để hiển thị lỗi lên grid
            this.$refs.modalImportExcel.handleCheckDataResult({
              isValid: false,
              errors: businessLogicErrors
            })
          }
        }
      } catch (error) {
        this.$toast.error(error.data?.message_detail || error.message || 'Lỗi khi kiểm tra file')
      } finally {
        this.$root.loading(false)
      }
    },

    // Khi click button "import"
    btnImportExcel_Click: async function() {
      try {
        const result = await this.$refs.modalImportExcel.showModal()

        if (result.success && result.data && result.data.length > 0) {
          // Xác nhận import
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.data.length} tòa nhà từ file Excel?`,
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
            console.log('Dữ liệu import:', result.data)
            await this.processImportData(result.data)
          }
        }
      } catch (error) {
        console.error('Lỗi khi import Excel:', error)
        this.$toast.error('Có lỗi khi import Excel: ' + error.message)
      }
    },

    processImportData: async function(importData) {
      try {
        this.$root.loading(true)

        const validItems = (importData || []).map((item) => {
          return {
            ID: null,
            TEN: item.TEN?.trim() || '',
            IDC_ID: item.IDC_ID,
            MUC_DICH_SU_DUNG: item.MUC_DICH_SU_DUNG || null,
            THOI_DIEM_VAN_HANH: item.THOI_DIEM_VAN_HANH || null,
            TRANG_THAI_VAN_HANH: item.TRANG_THAI_VAN_HANH || null,
            DIEN_TICH: item.DIEN_TICH || null,
            // ==== Location
            TOADO_X: item.TOADO_X || null,
            TOADO_Y: item.TOADO_Y || null,
            SUDUNG: item.SUDUNG || 1,
            GHI_CHU: item.GHI_CHU || '',
            MOTA: null,
            TENTAT: null
          }
        })

        // Gọi API để import dữ liệu
        const res = await api.import(this.axios, validItems)
        console.log('Response from import API:', res)

        if (res.data.error == 200) {
          const invalidRows = res.data.data.invalidRows
          const validRows = res.data.data.validRows
          this.$toast.success('Đã thêm: ' + validRows + ' bản ghi')

          if (invalidRows > 0) {
            this.$toast.error('Đã bỏ qua: ' + invalidRows + ' bản ghi không thể thêm do lỗi dữ liệu')
          }

          await this.loadData()
        }
      } catch (error) {
        this.$toast.error(error.data?.message_detail || error.message)
      } finally {
        this.$root.loading(false)
      }
    },

    // ======== controls helpers for template ========
    isEnabled: function(field) {
      return !!(this.controls && this.controls[field] && this.controls[field].enabled)
    },
    isInvalid: function(field) {
      return !!(this.controls && this.controls[field] && this.controls[field].invalid)
    },

    // ======== handle image selection ========
    onClickChonAnh: function() {
      this.$refs.inputFileAnh.click()
    },

    // ImageSelector events
    handleImageSelected: function(file) {
      this.tempImageFile = file
    },

    handleImageCleared: function() {
      this.currentItem.anhDaiDien = ''
      this.tempImageFile = null
    },

    handleImageUploaded: async function() {
      try {
        const rs = await this.$refs.ImageSelector.uploadImage(this.tempImageFile)
        // this.currentItem.anhDaiDien = rs.message.relativeUrl
        if (rs.status == true) {
          this.currentItem.anhDaiDien = rs.message.relativeUrl
        } else {
          throw new Error(`${rs.message} Vui lòng chọn ảnh có định dạng .jpg, .png, .jpeg.`)
        }
      } catch (error) {
        throw error
      }
    },

    // ======== datepicker validation ========
    async initFormData() {
      await this.$nextTick()
      const inputEl = this.$refs.thoiDiemVanHanhPicker?.ej2Instances?.element
      if (inputEl) {
        inputEl.addEventListener('input', (e) => {
          this.textThoiDiemVanHanh = e.target.value
          this.thoiDiemVanHanhHasInteraction = true
          this.controls.thoiDiemVanHanh.invalid = !this.isValidDate(e.target.value)
        })
      }
    },
    isValidDate: function(textValue) {
      if (!textValue || !textValue.trim()) {
        return this.currentItem.thoiDiemVanHanh === null
      }
      const date = moment(textValue.trim(), 'dd/MM/yyyy', true)
      return date.isValid() && date.year() > 1900 && date.year() < 2099
    },
    validateThoiDiemVanHanhForSubmit: function() {
      const textValue = (this.textThoiDiemVanHanh || '').trim()
      if (!this.thoiDiemVanHanhHasInteraction || !this.controls.thoiDiemVanHanh.invalid || !textValue) {
        return { valid: true }
      }
      const date = moment(textValue, 'dd/MM/yyyy')
      if (
        !textValue ||
        !date.isValid() ||
        date.year() <= 1900 ||
        date.year() >= 2099 
      ) {
        return { valid: false, error: 'Định dạng ngày không hợp lệ. Vui lòng nhập theo định dạng DD/MM/YYYY.' }
      }
      return { valid: true }
    }
  }
}
</script>
<style scoped>
.date-picker .e-input-group {
  border-color: 1px solid red !important;
}

.invalidBox {
  border: 1px solid red;
  border-radius: 5px;
}

.e-error .e-input {
  border: 1px solid red !important;
}
</style>
