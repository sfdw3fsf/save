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
  name: 'DanhMucChungLoaiThietBiMang',
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
      dsChungLoaiTBM: [],
      tab_index: 1,
      loaiThietBiMangOptions: [],
      fullDepthOptions: FULL_DEPTH_OPTIONS,
      hangSanXuatList: [],
      vaiTroThietBiList: [],
      chuanDauNoiList: [],
      moduleList: [],
      mucDichSuDungList: [],
      loaiCongOptions: [],
      form: { ...DEFAULT_FORM_ITEM },
      buttons: {
        btnSearch: { enabled: true },
        btnAddNew: { enabled: true },
        btnSave: { enabled: false },
        btnCancel: { enabled: false },
        btnEdit: { enabled: false },
        btnDelete: { enabled: false }
      },
      controls: {
        selectLoaiThietBiMang: { enabled: false, invalid: false },
        txtModelThietBi: { enabled: false, invalid: false },
        selectHangSanXuat: { enabled: false, invalid: false },
        selectVaiTroThietBi: { enabled: false, invalid: false },
        selectFullDepth: { enabled: false, invalid: false },
        txtCpu: { enabled: false, invalid: false },
        txtRam: { enabled: false, invalid: false },
        txtSoCong: { enabled: false, invalid: false },
        txtTenCong: { enabled: false, invalid: false },
        txtLoaiCong: { enabled: false, invalid: false },
        txtBangThong: { enabled: false, invalid: false },
        selectModule: { enabled: false, invalid: false },
        selectMucDichSuDung: { enabled: false, invalid: false },
        txtSoCongNguon: { enabled: false, invalid: false },
        txtTenCongNguon: { enabled: false, invalid: false },
        txtDongDinhDanh: { enabled: false, invalid: false },
        txtDienApDinhDanh: { enabled: false, invalid: false },
        selectChuanDauNoi: { enabled: false, invalid: false },
        txtSoLuongUCanDat: { enabled: false, invalid: false }
      },
      importConfig: IMPORT_CONFIG,
      enableWatchValidation: false
    }
  },
  computed: {
    grdItems: function() { return this.$refs.grdItems },
    saveButtonText() { return this.currentMode === 'ADD' ? 'Thêm mới' : 'Cập nhật' },
    getDataCols() {
      return [
        { fieldName: 'ID', headerText: 'ID', width: 50, textAlign: 'Center' },
        { fieldName: 'LOAITBM_TEN', headerText: 'Loại thiết bị mạng', allowFiltering: true },
        { fieldName: 'MODEL_THIETBI', headerText: 'Model thiết bị', allowFiltering: true },
        { fieldName: 'HANGSX_TEN', headerText: 'Hãng sản xuất', allowFiltering: true },
        { fieldName: 'LOAIVAITRO_TEN', headerText: 'Vai trò thiết bị', allowFiltering: true },
        { fieldName: 'FULL_DEPTH_TEXT', width: 100, headerText: 'Full depth', allowFiltering: true, textAlign: 'Center' },
        { fieldName: 'ACTIONS', headerText: 'Thao tác', width: 120, textAlign: 'Center', template: this.getActionColumnTemplate() }
      ]
    }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      const [
        tbmList,
        loaiTBMList,
        hangSanXuatList,
        vaiTroThietBiList,
        chuanDauNoiList,
        moduleList,
        mucDichSuDungList,
        loaiCongList
      ] = await Promise.all([
        this.getDanhSachChungLoaiTBM(),
        this.loadLoaiThietBiMangList(),
        this.loadHangSanXuatList(),
        this.loadVaiTroThietBiList(),
        this.loadChuanDauNoiList(),
        this.loadModuleList(),
        this.loadMucDichSuDungList(),
        this.loadLoaiCongList()
      ])
      this.dsChungLoaiTBM = tbmList
      this.loaiThietBiMangOptions = loaiTBMList
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
    onChangeTab(tabIndex) { this.tab_index = tabIndex },

    // Excel mapping
    processTBMExcelData(dataRows) {
      const nonEmptyRows = (dataRows || []).filter(row => row && row.some(cell => cell != null && cell.toString().trim() !== ''))
      return nonEmptyRows.map(row => ({
        loaiThietBiMang: row[0] ? row[0].toString().trim() : '',
        modelThietBi: row[1] ? row[1].toString().trim() : '',
        hangSanXuat: row[2] ? row[2].toString().trim() : '',
        vaiTroThietBi: row[3] ? row[3].toString().trim() : '',
        fullDepth: row[4] != null ? parseInt(row[4]) : null,
        cpu: row[5] != null ? parseInt(row[5]) : null,
        ram: row[6] != null ? parseInt(row[6]) : null
      }))
    },

    btnImportExcel_Click: async function() {
      try {
        const result = await this.$refs.modalImportExcel.showModal()
        if (result.success && result.data && result.data.length > 0) {
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.data.length} chủng loại thiết bị mạng từ file Excel?`,
            'Xác nhận import',
            { confirmButtonText: 'Có', cancelButtonText: 'Không', type: 'warning', dangerouslyUseHTMLString: true }
          ).then(s => true).catch(e => false)
          if (confirmFlag) { await this.processImportData(result.data) }
        }
      } catch (error) {
        console.error('Lỗi khi import Excel:', error)
        this.$root.toastError('Có lỗi khi import Excel: ' + error.message)
      }
    },

    btnExportExcel_Click() {
      const filtered = this.$refs.grdItems.grid.ej2Instances.currentViewData
      const exportData = filtered.map(item => ({
        ID: item.ID || '',
        LOAITBM_TEN: item.LOAITBM_TEN || '',
        MODEL_THIETBI: item.MODEL_THIETBI || '',
        HANGSX_TEN: item.HANGSX_TEN || '',
        LOAIVAITRO_TEN: item.LOAIVAITRO_TEN || '',
        FULL_DEPTH_TEXT: item.FULL_DEPTH_TEXT || ''
      }))
      const exportConfig = { fileName: 'DanhSachChungLoaiThietBiMang.xlsx', dataSource: exportData }
      this.grdItems.excelExport(exportConfig)
      this.$root.toastSuccess('Xuất dữ liệu thành công')
    },

    formatTBMCellValue(value, fieldName) {
      if (value === null || value === undefined || value === '') return ''
      if (fieldName === 'loaiThietBiMang') {
        const option = this.loaiThietBiMangOptions.find(opt => opt.ID === value || opt.ID === value.toString())
        return option ? option.TEN : value
      }
      if (fieldName === 'fullDepth') {
        const option = this.fullDepthOptions.find(opt => opt.ID === value || opt.ID === parseInt(value))
        return option ? option.TEN : value
      }
      return value
    },

    processImportData: async function(importData) {
      try {
        this.$root.showLoading(true)
        const validItems = (importData || []).map(item => ({
          id: null,
          loaiThietBiMang: item.loaiThietBiMang?.trim() || '',
          modelThietBi: item.modelThietBi?.trim() || '',
          hangSanXuatId: this.convertHangSanXuatNameToId(item.hangSanXuat),
          vaiTroThietBiId: this.convertVaiTroThietBiNameToId(item.vaiTroThietBi),
          fullDepth: item.fullDepth != null ? parseInt(item.fullDepth) : null,
          cpu: item.cpu ? parseInt(item.cpu) : null,
          ram: item.ram ? parseInt(item.ram) : null
        }))
        await this.importChungLoaiTBMData(validItems)
      } finally { this.$root.showLoading(false) }
    },

    async handleCheckData(checkDataInfo) {
      try {
        const payload = (checkDataInfo?.data || []).map(row => ({
          id: '',
          loaiThietBiMang: row.loaiThietBiMang || '',
          modelThietBi: row.modelThietBi || '',
          hangSanXuatId: this.convertHangSanXuatNameToId(row.hangSanXuat),
          vaiTroThietBiId: this.convertVaiTroThietBiNameToId(row.vaiTroThietBi),
          fullDepth: row.fullDepth != null ? parseInt(row.fullDepth) : null,
          cpu: row.cpu ? parseInt(row.cpu) : null,
          ram: row.ram ? parseInt(row.ram) : null
        }))
        const isValid = await api.validateChungLoaiTBMData(this.$root.context, payload)
        if (isValid) {
          this.importConfig.checkDataResult = false
          this.$root.toastSuccess('Dữ liệu hợp lệ!')
        }
      } catch (error) {
        const msgDetail = error?.data?.message_detail || ''
        this.importConfig.checkDataResult = true
        const errorList = msgDetail.split('\n').filter(line => line.trim() !== '')
        if (errorList.length > 0) { this.$root.toastError(`Có ${errorList.length} lỗi trong file import\n${msgDetail}`) }
        this.$refs.modalImportExcel.handleCheckDataResult({ isValid: false, errors: msgDetail })
      }
    },

    // Helpers
    findItemByIdOrName(value, list, idField = 'ID', nameField = 'TEN') {
      if (!list || list.length === 0) return null
      if (typeof value === 'number' || (!isNaN(value) && value.toString().trim() !== '')) {
        const id = typeof value === 'number' ? value : parseInt(value)
        const byId = list.find(item => item[idField] === id)
        if (byId) return byId
      }
      const valueStr = value.toString().trim().toLowerCase()
      return list.find(item => ((item[nameField] || '').toString().trim().toLowerCase() === valueStr)) || null
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

    enableControls(args) {
      for (const key in this.controls) { if (this.controls.hasOwnProperty(key)) this.controls[key].enabled = args }
      this.buttons.btnSave.enabled = args
      this.buttons.btnAddNew.enabled = !args
      this.buttons.btnSearch.enabled = !args
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled
      this.buttons.btnEdit.enabled = !args
      this.buttons.btnDelete.enabled = args ? false : (this.dsSelected && this.dsSelected.length > 0)
      if (this.form.id > 0) this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled
    },
    onSelectRowCheckbox(itemsCheck) {
      this.dsSelected = itemsCheck
      if (!this.buttons.btnSave.enabled) this.buttons.btnDelete.enabled = itemsCheck && itemsCheck.length > 0
    },

    onSave: async function() {
      if (this.validateControls()) {
        let flag = await this.$confirm('Bạn có muốn lưu thông tin này không ?', 'Xác nhận', { confirmButtonText: 'Có', cancelButtonText: 'Không', type: 'info', dangerouslyUseHTMLString: true })
          .then(s => true).catch(e => false)
        if (flag) {
          try {
            const isEdit = this.form.id
            let mess = isEdit == 0 ? 'Thêm mới chủng loại thiết bị mạng thành công.' : 'Cập nhật chủng loại thiết bị mạng thành công.'
            this.$root.showLoading(true)
            const dtoPayload = {
              chungloaitbmId: isEdit ? this.form.id : null,
              maVt: this.form.maVatTu || null,
              loaithietbimangId: this.form.loaiThietBiMang ?? null,
              modelThietbi: this.form.modelThietBi?.trim() || '',
              hangsxId: this.form.hangSanXuatId ?? null,
              loaivaitroId: this.form.vaiTroThietBiId ?? null,
              fullDepth: this.form.fullDepth ?? null,
              bodkCpu: this.form.cpu ?? null,
              bodkRam: this.form.ram ?? null,
              dcSocong: this.form.soCong ?? null,
              dcTencong: this.form.tenCong?.trim() || null,
              dcLoaicong: this.form.loaiCong ?? null,
              dcBangthong: this.form.bangThong ?? null,
              dcTtmoduleId: this.form.moduleId ?? null,
              dcMucdichsdId: this.form.mucDichSuDungId ?? null,
              ppSocong: this.form.soCongNguon ?? null,
              ppTencong: this.form.tenCongNguon?.trim() || null,
              ppDongdien: this.form.dongDinhDanh ?? null,
              ppDienap: this.form.dienApDinhDanh ?? null,
              ppChuandnId: this.form.chuanDauNoiId ?? null,
              soluongULd: this.form.soLuongUCanDat ?? null,
              sudung: this.form.suDung ?? 1,
              ghichu: this.form.ghiChu?.trim?.() || null,
              nguoiCn: null
            }
            let result = await this.upSertChungLoaiTBM(dtoPayload)
            if (result) {
              this.$root.toastSuccess(mess)
              this.enableControls(false)
              this.closeModal()
            }
          } finally {
            this.dsChungLoaiTBM = await this.getDanhSachChungLoaiTBM()
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
      this.$nextTick(() => { if (this.$refs.dialogChungLoaiTBM) { this.$refs.dialogChungLoaiTBM.show() } })
    },
    grdItems_RowSelected: async function(args) {
      if (this.showDialog && this.currentMode !== 'VIEW') {
        return
      }
      try {
        this.$root.showLoading(true)
        this.setCurrentItem(args.data)
        this.tab_index = 1
      } finally {
        this.$root.showLoading(false)
      }
    },

    onEdit: async function() {
      if (this.form.id === 0) { this.$root.toastError('Vui lòng chọn một chủng loại thiết bị mạng để sửa'); return }
      this.resetValidation(); this.enableWatchValidation = false; this.currentMode = 'EDIT'; this.tab_index = 1; this.enableControls(true); this.showDialog = true
      this.$nextTick(() => { if (this.$refs.dialogChungLoaiTBM) { this.$refs.dialogChungLoaiTBM.show() } })
    },
    closeModal: function() {
      this.showDialog = false
      if (this.$refs.dialogChungLoaiTBM) this.$refs.dialogChungLoaiTBM.hide()
      if (this.currentMode !== 'VIEW') {
        let items = this.grdItems.getSelectedRecords()
        let item = null
        if (!(items == null || items.length == 0)) {
          if (this.form.id == 0) { item = items[0] } else { item = items.find(x => x.ID == this.form.id) }
        }
        if (item != null) { this.setCurrentItem(item) } else { this.setCurrentItemDefault() }
      }
      this.currentMode = 'VIEW'; this.tab_index = 1; this.enableControls(false)
      if (this.dsSelected && this.dsSelected.length > 0) this.buttons.btnDelete.enabled = true
    },
    onDelete: async function() {
      let items = this.grdItems.getSelectedRecords()
      let ids = items.map(x => x.ID)
      let msg = `Bạn có muốn xóa ${items.length} mẫu tin đã chọn không?`
      let flag = await this.$confirm(msg, 'Xác nhận', { confirmButtonText: 'Có', cancelButtonText: 'Không', type: 'warning', dangerouslyUseHTMLString: true })
        .then(s => true).catch(e => false)
      if (flag) {
        try {
          this.$root.showLoading(true)
          let isSuccess = await this.deleteChungLoaiTBM(ids)
          if (isSuccess) {
            this.setCurrentItemDefault(); this.tab_index = 1; this.enableControls(false); this.dsSelected = []; this.buttons.btnDelete.enabled = false
            this.$root.toastSuccess('Xóa thông tin thành công')
          }
        } finally {
          this.dsChungLoaiTBM = await this.getDanhSachChungLoaiTBM()
          this.$root.showLoading(false)
        }
      }
    },

    setCurrentItem(item) {
      this.form.id = item.ID || 0
      this.form.maVatTu = item.MA_VT || null
      this.form.loaiThietBiMang = item.LOAITBM_ID || null
      this.form.modelThietBi = item.MODEL_THIETBI || ''
      this.form.hangSanXuatId = item.HANGSX_ID || null
      this.form.vaiTroThietBiId = item.LOAIVAITRO_ID || null
      this.form.fullDepth = item.FULL_DEPTH != null ? item.FULL_DEPTH : null
      this.form.cpu = item.BODK_CPU || null
      this.form.ram = item.BODK_RAM || null
      this.form.soCong = item.DC_SOCONG || null
      this.form.tenCong = item.DC_TENCONG || ''
      this.form.loaiCong = item.DC_LOAICONG != null ? Number(item.DC_LOAICONG) : null
      this.form.bangThong = item.DC_BANGTHONG || null
      this.form.moduleId = item.DC_TTMODULE_ID || null
      this.form.mucDichSuDungId = item.DC_MUCDICHSD_ID || null
      this.form.soCongNguon = item.PP_SOCONG || null
      this.form.tenCongNguon = item.PP_TENCONG || ''
      this.form.dongDinhDanh = item.PP_DONGDIEN || ''
      this.form.dienApDinhDanh = item.PP_DIENAP || null
      this.form.chuanDauNoiId = item.PP_CHUANDN_ID || null
      this.form.soLuongUCanDat = item.SOLUONG_U_LD || null
      this.currentMode = 'VIEW'
    },
    setCurrentItemDefault() {
      this.form = { ...DEFAULT_FORM_ITEM }
      this.tab_index = 1
      this.currentMode = 'ADD'
      this.resetValidation()
      this.enableWatchValidation = false
      this.enableControls(false)
      this.dsSelected = []
      this.buttons.btnDelete.enabled = false
    },
    resetValidation() { Object.keys(this.controls).forEach(key => { this.controls[key].invalid = false }) },
    validateControls() {
      this.enableWatchValidation = true
      let isValid = true
      this.resetValidation()
      const validationRules = [...FORM_VALIDATION_RULES]
      validationRules.forEach(rule => {
        const value = this.form[rule.field]
        let isFieldValid = false
        if (rule.type === 'string') isFieldValid = value && value.toString().trim() !== ''
        else if (rule.type === 'select') isFieldValid = value !== null && value !== undefined && value !== '' && value !== 0
        else if (rule.type === 'number') isFieldValid = value !== null && value !== undefined && value !== '' && !isNaN(value) && value > 0
        if (!isFieldValid) { if (this.controls[rule.control]) this.controls[rule.control].invalid = true; this.$root.toastError(rule.message); isValid = false }
      })
      POSITIVE_NUMBER_FIELDS.forEach(rule => {
        const value = this.form[rule.field]
        if (value !== null && value !== undefined && value !== '' && !isNaN(value) && Number(value) <= 0) {
          if (this.controls[rule.control]) this.controls[rule.control].invalid = true
          this.$root.toastError(rule.message)
          isValid = false
        }
      })
      return isValid
    },

    // ====== API ====== //
    getDanhSachChungLoaiTBM: async function(criteria) {
      try { return await api.getDanhSachChungLoaiTBM(this.$root.context, criteria || {}) } catch (error) { this.handleApiError(error); return [] }
    },
    onSearch: async function(criteria) { this.$root.showLoading(true); try { this.dsChungLoaiTBM = await this.getDanhSachChungLoaiTBM(criteria) } finally { this.$root.showLoading(false) } },
    onResetSearch: async function() { this.$root.showLoading(true); try { this.dsChungLoaiTBM = await this.getDanhSachChungLoaiTBM({}) } finally { this.$root.showLoading(false) } },
    viewItem(row) { if (!row) return; this.setCurrentItem(row); this.currentMode = 'VIEW'; this.showDialog = true; this.$nextTick(() => { if (this.$refs.dialogChungLoaiTBM) this.$refs.dialogChungLoaiTBM.show() }) },
    editItem(row) { if (!row) return; this.setCurrentItem(row); this.onEdit() },
    getActionColumnTemplate() {
      const parent = this
      return function() {
        return {
          template: {
            template: `
              <div class="d-flex align-items-center justify-content-center">
                <b-button size="sm" variant="outline-primary" @click="onView()"><i class="fa fa-eye"></i></b-button>
                <b-button size="sm" variant="outline-success" class="ml-1" @click="onEdit()"><i class="fa fa-edit"></i></b-button>
              </div>
            `,
            methods: {
              onView() { parent.viewItem(this.data) },
              onEdit() { parent.editItem(this.data) }
            }
          }
        }
      }
    },
    upSertChungLoaiTBM: async function(payload) { try { return await api.upSertChungLoaiTBM(this.$root.context, payload) } catch (e) { this.handleApiError(e); return false } },
    deleteChungLoaiTBM: async function(payload) { try { return await api.deleteChungLoaiTBM(this.$root.context, payload) } catch (e) { this.handleApiError(e); return false } },
    async loadHangSanXuatList() { try { return await api.getDanhMucHangSanXuat(this.$root.context) } catch (e) { this.handleApiError(e); return [] } },
    async loadVaiTroThietBiList() { try { return await api.getDanhMucVaiTroThietBi(this.$root.context) } catch (e) { this.handleApiError(e); return [] } },
    async loadLoaiThietBiMangList() { try { return await this.$root.context.post('/web-ecms/idc/danh-muc/common', { loaiDanhMuc: 'IDC-LOAI-TBM' }).then(rs => rs.data || []) } catch (e) { this.handleApiError(e); return [] } },
    async loadChuanDauNoiList() { try { return await api.getDanhMucChuanDauNoi(this.$root.context) } catch (e) { this.handleApiError(e); return [] } },
    async loadModuleList() { try { return await api.getDanhMucModule(this.$root.context) } catch (e) { this.handleApiError(e); return [] } },
    async loadMucDichSuDungList() { try { return await api.getDanhMucMucDichSuDung(this.$root.context) } catch (e) { this.handleApiError(e); return [] } },
    async loadLoaiCongList() { try { return await api.getDanhMucLoaiCong(this.$root.context) } catch (e) { this.handleApiError(e); return [] } },
    async importChungLoaiTBMData(data) {
      if (!data || data.length === 0) { this.$root.toastError('Không có dữ liệu hợp lệ để import!'); return false }
      let successCount = 0; let errorCount = 0
      try { const result = await api.importChungLoaiTBMData(this.$root.context, data); successCount = result.successCount || 0; errorCount = result.failCount || 0 }
      catch (err) { this.$root.toastError(`Import lỗi`); console.error(err) }
      finally { this.dsChungLoaiTBM = await this.getDanhSachChungLoaiTBM(); if (successCount > 0) this.$root.toastSuccess(`Import thành công ${successCount} chủng loại thiết bị mạng`); if (errorCount > 0) this.$root.toastError(`Import thất bại ${errorCount} bản ghi. Vui lòng kiểm tra lại dữ liệu.`) }
      return true
    },
    handleApiError(e) { if (e?.data?.message_detail) { this.$root.toastError(e.data.message_detail) } else { console.error(e) } }
  }
}
</script>

<style scoped>
.w200 {  width: 200px !important; }
a.disable-btn {  pointer-events: none;  cursor: default;  color: #6c757d !important; }
.check-action {  top: 3px; }
.e-rowcell .check-action input[type='checkbox']:checked:disabled + .name:before {  background: #fff;  border-color: #0176ff;  color: #0176ff; }
.e-rowcell .check-action input[type='checkbox']:disabled + .name:before {  background: #fff;  border-color: #0176ff; }
.invalidBox {  border: 1px solid red;  border-radius: 5px; }
</style>



