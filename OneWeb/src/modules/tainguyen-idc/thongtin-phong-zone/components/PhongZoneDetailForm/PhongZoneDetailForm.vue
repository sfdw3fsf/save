<template src="./PhongZoneDetailForm.template.html"></template>
<script>
import api from '../../api.js'
import { DEFAULT_FORM_ITEM, IMPORT_CONFIG } from '../../constants.js'
import moment from 'moment'
export default {
  name: 'ThongTinPhongZone',
  props: {
    parentId: {
      type: Number
    },
    parentType: {
      type: String
    },
    itemId: {
      type: Number,
      default: 0
    },
    itemType: {
      type: String
    },
    mode: {
      type: String,
      default: 'VIEW', // 'ADD', 'EDIT', 'VIEW'
      validator: (value) => ['ADD', 'EDIT', 'VIEW'].includes(value)
    },
    roomId: {
      type: [String, Number],
      required: false,
      default: null
    },
    isFromModal: {
      type: Boolean,
      required: false,
      default: false
    }
  },
  data() {
    return {
      dsSelected: [],
      dsPhongZone: [],
      dsThuocTinhBoSung: [],
      matSanSelectionList: [],
      donViIdcList: [],
      idcList: [],
      toaNhaList: [],
      mucDichSuDungList: [],
      trangThaiVanHanhList: [],
      previewAnhDaiDien: '',
      previousAnhDaiDienRelative: '',
      pendingAnhDaiDienFile: null,
      pendingAnhDaiDienPreviewUrl: '',
      pendingRemoveAnhDaiDien: false,
      loadingImageUrl: false,
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
        txtID: {
          enabled: false,
          invalid: false
        },
        txtTen: {
          enabled: false,
          invalid: false
        },
        selectMucDichSuDung: {
          enabled: false,
          invalid: false
        },
        dateThoiDiemVanHanh: {
          enabled: false,
          invalid: false
        },
        txtDienTich: {
          enabled: false,
          invalid: false
        },
        selectTrangThaiVanHanh: {
          enabled: false,
          invalid: false
        },
        btnAnhDaiDien: {
          enabled: false,
          invalid: false
        },
        txtGhiChu: {
          enabled: false,
          invalid: false
        },
        chkSuDung: {
          enabled: false,
          invalid: false
        },
        selectDonViIdc: {
          enabled: false,
          invalid: false
        },
        selectIdc: {
          enabled: false,
          invalid: false
        },
        selectToaNha: {
          enabled: false,
          invalid: false
        },
        selectMatSan: {
          enabled: false,
          invalid: false
        },
        txtViTri: {
          enabled: false,
          invalid: false
        },
        btnViTri: {
          enabled: false,
          invalid: false
        },
        txtToaDoX: {
          enabled: false,
          invalid: false
        },
        txtToaDoY: {
          enabled: false,
          invalid: false
        },
        txtSlRackThietKe: {
          enabled: false,
          invalid: false
        }
      },
      importConfig: IMPORT_CONFIG,
      enableWatchValidation: false,
      textThoiDiemVanHanh: null
    }
  },
  computed: {
    isReadOnly() {
        return this.mode === 'VIEW'
    },
    isEditMode() {
      return this.mode === 'EDIT' || this.mode === 'ADD'
    },
    displayThuocTinhBoSung() {
      // Hiển thị tempThuocTinhBoSung nếu là ADD mode, ngược lại hiển thị thuocTinhBoSung
      return this.mode === 'ADD' ? this.tempThuocTinhBoSung : this.thuocTinhBoSung
    },
    thuocTinhColumns() {
      const columns = [
        { fieldName: 'TEN', headerText: 'Thuộc tính', allowFiltering: true, allowSorting: true, width: 200 },
        { fieldName: 'GIATRI', headerText: 'Giá trị', allowFiltering: true, allowSorting: true, width: 200 }
      ]
      // Chỉ thêm cột hành động khi không phải VIEW mode
      if (!this.isReadOnly) {
        columns.push({
          fieldName: 'ACTIONS',
          headerText: 'Hành động',
          textAlign: 'Center',
          template: this.actionColumn(),
          width: 150
        })
      }
      return columns
    },
    minDate() {
      // Cho phép chọn từ năm 1900
      return new Date(1900, 0, 1)
    },
    maxDate() {
      // Cho phép chọn đến năm 2099
      return new Date(2099, 11, 31)
    }
  },
  created: async function () {
    try {
        this.$root.showLoading(true)
        const [donViIdcList, mucDichSuDungList, trangThaiVanHanhList, phongZoneList] = await Promise.all([
        this.loadDonViIdcList(),
        this.loadMucDichSuDungList(),
        this.loadTrangThaiVanHanhList(),
        this.getDanhSachPhongZone()
      ])
      this.donViIdcList = donViIdcList
      this.mucDichSuDungList = mucDichSuDungList
      this.trangThaiVanHanhList = trangThaiVanHanhList
      if (this.isFromModal && this.roomId) {
        const currentItem = phongZoneList.find((pz) => pz.ID == this.roomId)
        if (currentItem) {
          this.setCurrentItem(currentItem)
          this.dsPhongZone = [currentItem]
        }
      } else {
        this.dsPhongZone = phongZoneList
      }
      // Gán validator thực tế cho rule vừa khai báo trong importConfig
      const idxMatSan = this.importConfig.validationRules.findIndex(
        (r) => r.field === 'phongZoneId' && r.validator === null
      )
      if (idxMatSan !== -1) {
        // dùng function factory để luôn lấy Set mới nhất nếu danh mục đổi
        this.importConfig.validationRules[idxMatSan].validator = this.validateMatSanExists()
      }
      // Gán validator cho trạng thái vận hành
      const idxTrangThaiVH = this.importConfig.validationRules.findIndex(
        (r) => r.field === 'trangThaiVH' && r.validator === null
      )
      if (idxTrangThaiVH !== -1) {
        this.importConfig.validationRules[idxTrangThaiVH].validator = this.validateTrangThaiVanHanhExists()
      }
    } catch (error) {
      console.error('Error in created:', error)
      this.$toast.error('Có lỗi khi khởi tạo dữ liệu')
    } finally {
      this.$root.showLoading(false)
    }
  },
  watch: {
    mode: function (newMode) {
      if (newMode === 'VIEW') {
        this.enableControls(false)
      } else if (newMode === 'EDIT' || newMode === 'ADD') {
        if (this.form.id > 0 || newMode === 'ADD') {
          this.enableControls(true)
        }
      }
    },
    'form.donViIdc': async function (val) {
      if (val) {
        const idcList = await this.loadIdcList(val)
        this.idcList = idcList || []
        this.toaNhaList = []
        this.matSanSelectionList = []
      } else {
        this.idcList = []
      }
    },
    'form.idcId'(val) {
      if (val) {
        this.loadToaNhaList(val)
      }
    },
    'form.toaNhaId'(val) {
      if (val) {
        this.loadMatSanList(val)
      }
    },
    'form.id': function (value) {
      this.buttons.btnEdit.enabled = value > 0
      // Chỉ enable nút xóa khi có item được chọn và không ở chế độ edit
      this.buttons.btnDelete.enabled = value > 0 && !this.buttons.btnSave.enabled
    },
    'form.phongZoneId'(val) {
      this.validateField('phongZoneId', val)
    },
    'form.ten'(val) {
      this.validateField('ten', val)
    },
    dsSelected: function (value) {
      if (!this.buttons.btnSave.enabled) {
        this.buttons.btnDelete.enabled = value && value.length > 0
      }
    }
  },
  async mounted() {
    await this.init(this.itemId);
    this.$nextTick(() => {
      // Đợi các SelectExt được render và SyncFusion khởi tạo xong
      setTimeout(() => {
        // Lấy tất cả select2 đang có trong form này
        const $form = $(this.$el);
        const $selects = $form.find('select');

        $selects.each(function () {
          const $sel = $(this);
          // Nếu select2 chưa khởi tạo thì bỏ qua
          if (!$sel.data('select2')) return;

          // Xác định phần tử cha gần nhất (value/info-row/form)
          const $parent = $sel.closest('.value, .info-row, .phong-zone-detail-form');
          const dropdownParent = $parent.length ? $parent : $('body');

          // Ghi đè lại dropdownParent cho instance hiện tại
          $sel.data('select2').dropdown.$dropdownParent = dropdownParent;
        });
      }, 500); // delay nhẹ cho chắc chắn SelectExt đã mount xong
    });
  },
  updated() {
    this.$nextTick(() => {
      const $form = $(this.$el);
      const $selects = $form.find('select');

      $selects.each(function () {
        const $sel = $(this);
        if (!$sel.data('select2')) return;
        const $parent = $sel.closest('.value, .info-row, .phong-zone-detail-form');
        const dropdownParent = $parent.length ? $parent : $('body');
        $sel.data('select2').dropdown.$dropdownParent = dropdownParent;
      });
    });
  },
  methods: {
    async init(itemId) {
        await this.initFormData()
        if (itemId > 0) {
            await this.loadItemDetail(itemId)
            // Load thuộc tính bổ sung cho EDIT/VIEW
            await this.loadThuocTinh(itemId)
        } else {
          await this.setCurrentItemDefault()
          this.enableControls(true)
        }
    },
    async initFormData() {
        try {
            this.$root.showLoading(true)
            const [donViIdcList, mucDichSuDungList, trangThaiVanHanhList, phongZoneList] = await Promise.all([
                this.loadDonViIdcList(),
                this.loadMucDichSuDungList(),
                this.loadTrangThaiVanHanhList(),
                this.getDanhSachPhongZone()
            ])
                
            this.donViIdcList = donViIdcList || []
            this.mucDichSuDungList = mucDichSuDungList || []
            this.trangThaiVanHanhList = trangThaiVanHanhList || []
            if (this.isFromModal && this.itemId) {
                const currentItem = phongZoneList.find((pz) => pz.ID == this.itemId)
                if (currentItem) {
                this.setCurrentItem(currentItem)
                this.dsPhongZone = [currentItem]
                }
            } else {
                this.dsPhongZone = phongZoneList
            }
            // Gán validator thực tế cho rule vừa khai báo trong importConfig
            const idxMatSan = this.importConfig.validationRules.findIndex(
                (r) => r.field === 'phongZoneId' && r.validator === null
            )
            if (idxMatSan !== -1) {
                // dùng function factory để luôn lấy Set mới nhất nếu danh mục đổi
                this.importConfig.validationRules[idxMatSan].validator = this.validateMatSanExists()
            }
            // Gán validator cho trạng thái vận hành
            const idxTrangThaiVH = this.importConfig.validationRules.findIndex(
                (r) => r.field === 'trangThaiVH' && r.validator === null
            )
            if (idxTrangThaiVH !== -1) {
                this.importConfig.validationRules[idxTrangThaiVH].validator = this.validateTrangThaiVanHanhExists()
            }
        } finally {
            this.$root.showLoading(false)
        }

        this.textThoiDiemVanHanh = null;

        const datepickerObj = this.$refs.datepicker.ej2Instances;
        if(datepickerObj) {
          var inputEl = datepickerObj.element;
          if(inputEl)
            {
                inputEl.addEventListener('input', (e) => {
                this.textThoiDiemVanHanh = e.target.value;
            });
          }
        }
    },
    onDatePickerChange(args) {
      if(args.value)
        this.textThoiDiemVanHanh = args.value;
    },
    validateField(field, value) {
      if (!this.enableWatchValidation) return
      const fieldValidationMap = {
        phongZoneId: { control: 'selectMatSan', type: 'select' },
        ten: { control: 'txtTen', type: 'string' }
      }

      const config = fieldValidationMap[field]
      if (!config) return

      let isValid = false
      if (config.type === 'string') {
        isValid = value && value.toString().trim() !== ''
      } else if (config.type === 'select') {
        isValid = value !== null && value !== undefined && value !== '' && value !== 0
      }

      if (this.controls[config.control]) {
        this.controls[config.control].invalid = !isValid
      }
    },

    // Helper function để tìm item theo ID hoặc tên trong danh sách
    findItemByIdOrName(value, list, idField = 'ID', nameField = 'TEN') {
      if (!list || list.length === 0) return null

      // Thử tìm theo ID nếu là số
      if (typeof value === 'number' || (!isNaN(value) && value.toString().trim() !== '')) {
        const id = typeof value === 'number' ? value : parseInt(value)
        const byId = list.find((item) => item[idField] === id)
        if (byId) return byId
      }

      // Nếu không tìm thấy theo ID, thử tìm theo tên
      const valueStr = value.toString().trim().toLowerCase()
      return list.find((item) => {
        const itemName = item[nameField] == null
          ? ''
          : item[nameField].toString().trim().toLowerCase()
        return itemName === valueStr
      }) || null
    },

    convertMatSanNameToId(rawValue) {
      if (rawValue === undefined || rawValue === null || rawValue === '') return rawValue
      const matchedItem = this.findItemByIdOrName(rawValue, this.matSanSelectionList)
      return matchedItem ? matchedItem.ID : rawValue
    },
    convertTrangThaiVanHanhNameToId(rawValue) {
      if (rawValue === undefined || rawValue === null || rawValue === '') return rawValue
      const matchedItem = this.findItemByIdOrName(rawValue, this.trangThaiVanHanhList)
      return matchedItem ? matchedItem.ID : rawValue
    },
    // Hàm xử lý dữ liệu Excel cho phòng/zone
    processPhongZoneExcelData(dataRows) {
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
          phongZoneId: row[0] ? row[0].toString().trim() : '',
          ten: row[1] ? row[1].toString().trim() : '',
          dienTich: row[2] != null ? parseFloat(row[2]) : null,
          trangThaiVH: row[3] != null ? row[3] : null,
          ghiChu: row[4] ? row[4].toString().trim() : '',
          suDung: row[5] != null ? row[5] : 1
        }
      })
    },

    btnImportExcel_Click: async function () {
      try {
        // Load tất cả mặt sàn để dùng cho mapping khi import
        this.$root.showLoading(true)
        this.matSanSelectionList = await this.getDanhMucMatSan()
        this.$root.showLoading(false)

        // Đảm bảo validator được cập nhật với danh sách mới nhất
        const idxMatSan = this.importConfig.validationRules.findIndex(
          (r) => r.field === 'phongZoneId' && r.validator === null
        )
        if (idxMatSan !== -1) {
          this.importConfig.validationRules[idxMatSan].validator = this.validateMatSanExists()
        }

        const result = await this.$refs.modalImportExcel.showModal()

        if (result.success && result.data && result.data.length > 0) {
          // Xác nhận import
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.data.length} phòng/zone từ file Excel?`,
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
      } finally {
        this.matSanSelectionList = []
      }
    },

    btnExportExcel_Click() {
      const filtered = this.$refs.grdItems.grid.ej2Instances.currentViewData
      const exportData = filtered.map((item) => ({
        ID: item.ID || '',
        TEN: item.TEN || '',
        IDC: item.IDC || '',
        TOANHA: item.TOANHA || '',
        MATSAN: item.MATSAN || '',
        DIENTICH: item.DIENTICH || '',
        TRANGTHAI_VH: item.TRANGTHAI_VH || ''
      }))

      const exportConfig = {
        fileName: 'DanhSachThongTinPhongZone.xlsx',
        dataSource: exportData
      }

      this.grdItems.excelExport(exportConfig)
      this.$root.toastSuccess('Xuất dữ liệu thành công')
    },

    formatPhongZoneCellValue(value, fieldName) {
      if (value === null || value === undefined || value === '') return ''

      // Format cho trường mặt sàn (phongZoneId)
      if (fieldName === 'phongZoneId' && this.matSanSelectionList && this.matSanSelectionList.length > 0) {
        const matchedItem = this.findItemByIdOrName(value, this.matSanSelectionList)
        return matchedItem?.TEN || value
      }

      // Format cho trường trạng thái vận hành
      if (fieldName === 'trangThaiVH' && this.trangThaiVanHanhList && this.trangThaiVanHanhList.length > 0) {
        const matchedItem = this.findItemByIdOrName(value, this.trangThaiVanHanhList)
        return matchedItem?.TEN || value
      }

      // Format cho trường hiệu lực
      if (fieldName === 'suDung' && typeof value === 'number' && (value === 1 || value === 0)) {
        return value == 1 ? 'Hiệu lực' : 'Không hiệu lực'
      }

      // Format cho trường diện tích
      if (fieldName === 'dienTich' && (typeof value === 'number' || !isNaN(value))) {
        return value.toString()
      }

      return value
    },

    processImportData: async function (importData) {
      try {
        this.$root.showLoading(true)

        const validItems = (importData || []).map((item) => {
          return {
            id: null,
            phongZoneId: this.convertMatSanNameToId(item.phongZoneId),
            ten: item.ten?.trim() || '',
            dientich: item.dienTich ? parseFloat(item.dienTich) : null,
            trangthaiVh: this.convertTrangThaiVanHanhNameToId(item.trangThaiVH),
            ghiChu: item.ghiChu || '',
            suDung: item.suDung !== undefined && item.suDung !== null && item.suDung !== '' ? parseInt(item.suDung) : 1
          }
        })

        await this.importPhongZoneData(validItems)
      } finally {
        this.$root.showLoading(false)
      }
    },

    async setParentBuilding(matSanId) {
      
      const dsPhongZone = await this.getDanhSachPhongZone()
      const item = dsPhongZone.find(x => x.ID_MATSAN === matSanId)

      this.form.idd = matSanId
      this.form.donViIdc = item.ID_DONVI
      this.form.idcId = item.ID_IDC
      this.form.toaNhaId = item.ID_TOANHA
      this.form.matsanId = matSanId
    },

    async loadItemDetail(itemId) {
      try {
        this.isLoading = true
        const response = await api.getPhongZoneById(this.$root.context, itemId)
        if (response) {
            await this.setCurrentItem(response[0])
            if(this.isEditMode) {
                this.enableControls(true)
            } else if(this.isReadOnly) {
                // Disable tất cả controls khi ở chế độ VIEW
                this.enableControls(false)
            }
        }
      } catch (error) {
        console.log(error)
        this.$root.toastError('Lỗi khi tải chi tiết Phòng/Zone')
        // this.enableControls(false)
      } finally {
        this.isLoading = false
      }
    },

    async handleCheckData(checkDataInfo) {
      try {
        const payload = (checkDataInfo?.data || []).map((row) => {
          return {
            id: '',
            phongZoneId: this.convertMatSanNameToId(row.phongZoneId),
            ten: row.ten || '',
            dienTich: row.dienTich ? parseFloat(row.dienTich) : null,
            trangThaiVanHanh: this.convertTrangThaiVanHanhNameToId(row.trangThaiVH),
            ghiChu: row.ghiChu || '',
            suDung: row.suDung !== undefined && row.suDung !== null && row.suDung !== '' ? parseInt(row.suDung) : 1
          }
        })

        const response = await this.axios.post('/web-ecms/idc/phong-zone/validate-multi', payload)
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
          errors: msgDetail
        })
      }
    },

    enableControls: function (args) {
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

    onSave: async function () {
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
            let mess = isEdit == 0 ? 'Thêm mới phòng/zone thành công.' : 'Cập nhật phòng/zone thành công.'
            this.$root.showLoading(true)

            // Xử lý ảnh đại diện: xóa ảnh cũ nếu có và upload ảnh mới nếu có
            if (this.pendingRemoveAnhDaiDien && this.form.anhDaiDien) {
              await this.deleteFileFromMinio(this.form.anhDaiDien)
              this.form.anhDaiDien = ''
            }
            // Nếu có chọn ảnh mới
            if (this.pendingAnhDaiDienFile) {
              if (this.form.anhDaiDien) {
                await this.deleteFileFromMinio(this.form.anhDaiDien)
              }
              const uploadRs = await this.uploadFileToMinio(this.pendingAnhDaiDienFile)
              if (uploadRs && uploadRs.relativeUrl) {
                this.form.anhDaiDien = uploadRs.relativeUrl
                const viewUrl = await this.getPresignedUrlFromMinio(this.form.anhDaiDien)
                this.previewAnhDaiDien = viewUrl || ''
              } else {
                this.$root.toastError('Tải ảnh thất bại')
                return
              }
            }

            // Map form fields to database field names
            let payload = {
              id: isEdit ? this.form.id : null,
              matsanId: this.convertMatSanNameToId(this.form.matsanId),
              ten: this.form.ten.trim(),
              ghiChu: this.form.ghiChu,
              suDung: this.form.suDung || 1,
              dientich: this.form.dienTich,
              anhDaidien: this.form.anhDaiDien,
              trangthaiVh: this.form.trangThaiVanHanh,
              ngaySd: this.form.thoiDiemVanHanh ? moment(this.form.thoiDiemVanHanh).format('YYYY-MM-DD') : null,
              mucdichsdId: this.form.mucDichSuDung,
              slRackThietke: this.form.slRackThietKe,
              toadoX: this.form.toaDoX,
              toadoY: this.form.toaDoY
            }
            let result = await this.upSertThongTinPhongZone(payload)
            if (result) {
              // Nếu là tạo mới và có thuộc tính tạm thời, lưu chúng vào DB
              if (!isEdit && result.id && this.dsThuocTinhBoSung.length > 0) {
                try {
                  await this.saveTempThuocTinh(result.id)
                  this.form.id = result.id
                } catch (error) {
                  console.error('Error saving temp attributes:', error)
                }
              }
              
              this.$root.toastSuccess(mess)
                this.$emit('saveSuccessed', this.form)

              this.pendingAnhDaiDienFile = null
              if (this.pendingAnhDaiDienPreviewUrl) {
                try {
                  URL.revokeObjectURL(this.pendingAnhDaiDienPreviewUrl)
                } catch (e) { }
              }
              this.pendingAnhDaiDienPreviewUrl = ''
              this.pendingRemoveAnhDaiDien = false
              this.previousAnhDaiDienRelative = this.form.anhDaiDien
              this.enableControls(false)
            }
          } catch (error) {
            console.error('Error in onSave:', error)
          } finally {
            if (!this.isFromModal) {
              this.dsPhongZone = await this.getDanhSachPhongZone()
            } else if (this.itemId) {
              let dsPhongZone = await this.getDanhSachPhongZone()
              const currentItem = dsPhongZone.find((pz) => pz.ID == this.itemId)
              if (currentItem) {
                this.setCurrentItem(currentItem)
                this.dsPhongZone = [currentItem]
              }
            }
            this.$root.showLoading(false)
          }
        }
      }
    },

    onAddNew: async function () {
      await this.setCurrentItemDefault()
      this.idcList = []
      this.toaNhaList = []
      this.matSanSelectionList = []
      // this.datePickerHasInteraction = false
      this.enableControls(true)
    },
    onEdit: async function (args) {
      this.resetValidation()
      this.enableWatchValidation = false
      // this.datePickerHasInteraction = false
      this.enableControls(true)
    },
    onCancel: async function (args) {
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
        await this.setCurrentItemDefault()
      }
      // Hủy thay đổi ảnh tạm thời
      this.pendingAnhDaiDienFile = null
      if (this.pendingAnhDaiDienPreviewUrl) {
        try {
          URL.revokeObjectURL(this.pendingAnhDaiDienPreviewUrl)
        } catch (e) { }
      }
      this.pendingAnhDaiDienPreviewUrl = ''
      this.pendingRemoveAnhDaiDien = false
      if (this.previousAnhDaiDienRelative) {
        try {
          this.loadingImageUrl = true
          const url = await this.getPresignedUrlFromMinio(this.previousAnhDaiDienRelative)
          this.previewAnhDaiDien = url || ''
        } catch (e) {
          this.previewAnhDaiDien = ''
        } finally {
          this.loadingImageUrl = false
        }
      } else {
        this.previewAnhDaiDien = ''
        this.loadingImageUrl = false
      }
      this.enableControls(false)
      if (this.dsSelected && this.dsSelected.length > 0) {
        this.buttons.btnDelete.enabled = true
      }
    },
    onDelete: async function (args) {
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
          let isSuccess = await this.deletePhongZone(ids)
          if (isSuccess) {
            // Xóa ảnh từ Minio nếu có
            for (const item of items) {
              if (item.ANH_DAIDIEN) {
                try {
                  await this.deleteFileFromMinio(item.ANH_DAIDIEN)
                } catch (e) {
                  console.warn('Failed to delete image:', item.ANH_DAIDIEN, e)
                }
              }
            }
            await this.setCurrentItemDefault()
            this.enableControls(false)
            this.dsSelected = []
            this.buttons.btnDelete.enabled = false
            this.$root.toastSuccess('Xóa thông tin thành công')
          }
        } finally {
          if (!this.isFromModal) {
            this.dsPhongZone = await this.getDanhSachPhongZone()
          } else if (this.itemId) {
            let dsPhongZone = await this.getDanhSachPhongZone()
            const currentItem = dsPhongZone.find((pz) => pz.ID == this.itemId)
            if (currentItem) {
              this.setCurrentItem(currentItem)
              this.dsPhongZone = [currentItem]
            }
          }
          this.$root.showLoading(false)
        }
      }
    },

    setCurrentItem: async function (item) {
      if(this.mode == 'ADD') {        
        await this.setCurrentItemDefault()
        return
      }
      this.form.id = item.ID || 0
      this.form.ten = item.TEN
      this.form.mucDichSuDung = item.MUCDICHSD_ID
      this.form.thoiDiemVanHanh = item.NGAY_SD
      this.form.dienTich = item.DIENTICH || null
      this.form.trangThaiVanHanh = item.TRANGTHAIVH_ID
      this.form.anhDaiDien = item.ANH_DAIDIEN
      // Lưu lại giá trị cũ để tham khảo
      this.previousAnhDaiDienRelative = this.form.anhDaiDien
      this.form.ghiChu = item.GHI_CHU
      this.form.suDung = item.SUDUNG || 1
      this.form.phongZoneId = item.ID
      this.form.matsanId = item.MATSAN_ID
      this.form.toaNhaId = item.ID_TOANHA
      this.form.idcId = item.ID_IDC
      this.form.donViIdc = item.ID_DONVI
      if (item.TOADO_X && item.TOADO_Y) {
        this.form.viTri = `X: ${item.TOADO_X}, Y: ${item.TOADO_Y}`
        this.form.toaDoX = item.TOADO_X
        this.form.toaDoY = item.TOADO_Y
      } else {
        this.form.viTri = ''
        this.form.toaDoX = ''
        this.form.toaDoY = ''
      }

      this.form.slRackThietKe = item.SOLUONG_RACK || null
      this.form.slRackDaLapDat = item.TONG_RACK_LD || null
      this.form.slRackKhaDung = item.SOLUONG_RACK - item.TONG_RACK_LD
      this.form.csDinhDanhThietBi = item.CS_DINHDANH_THIETBI || null

      // Load ảnh từ server nếu có
      if (this.form.anhDaiDien) {
        try {
          this.loadingImageUrl = true
          const url = await this.getPresignedUrlFromMinio(this.form.anhDaiDien)
          this.previewAnhDaiDien = url || ''
        } catch (e) {
          this.previewAnhDaiDien = ''
        } finally {
          this.loadingImageUrl = false
        }
      } else {
        this.previewAnhDaiDien = ''
        this.loadingImageUrl = false
      }

      // Load thuộc tính bổ sung
      if (this.form.id > 0) {
        await this.loadThuocTinhBoSung(this.form.id)
      } else {
        this.dsThuocTinhBoSung = []
      }
    },
    setCurrentItemDefault: async function () {
    //   this.form = { ...DEFAULT_FORM_ITEM }
        Object.assign(this.form, DEFAULT_FORM_ITEM)
      this.previewAnhDaiDien = ''
      this.previousAnhDaiDienRelative = ''
      this.pendingAnhDaiDienFile = null
      if (this.pendingAnhDaiDienPreviewUrl) {
        try {
          URL.revokeObjectURL(this.pendingAnhDaiDienPreviewUrl)
        } catch (e) { }
      }
      this.pendingAnhDaiDienPreviewUrl = ''
      this.pendingRemoveAnhDaiDien = false
      this.loadingImageUrl = false
      this.resetValidation()
      this.enableWatchValidation = false
      // this.datePickerHasInteraction = false
      this.enableControls(false)
      this.dsSelected = []
      this.buttons.btnDelete.enabled = false
      this.dsThuocTinhBoSung = []
      
      if (this.parentId) {
        await this.setParentBuilding(this.parentId)
      }
      // await this.setParentBuilding(21)
    },
    resetValidation: function () {
      Object.keys(this.controls).forEach((key) => {
        this.controls[key].invalid = false
      })
    },
    validateDate() {
      var value = this.textThoiDiemVanHanh;
      if (value === '') {
        return { valid: true }
      }

      // Empty date is valid (optional field)
      if (!value || value === null || value === undefined || value === '') {
        return { valid: true }
      }

      // Check valid date using moment
      const dateMoment = moment(value)
      if (!dateMoment.isValid()) {
        return { valid: false, error: 'Định dạng ngày không hợp lệ. Vui lòng nhập theo định dạng DD/MM/YYYY.' }
      }

      // Check year range (1900-2099)
      const year = dateMoment.year()
      if (year < 1900 || year > 2099) {
        return { valid: false, error: `Năm phải trong khoảng 1900-2099. Năm hiện tại: ${year}` }
      }

      return { valid: true }
    },
    validateControls: function () {
      this.enableWatchValidation = true
      let isValid = true

      // Reset all validation states
      this.resetValidation()

      // Validation rules configuration
      const validationRules = [
        {
          field: 'ten',
          control: 'txtTen',
          type: 'string',
          message: 'Tên phòng/zone không được để trống.'
        },
        {
          field: 'mucDichSuDung',
          control: 'selectMucDichSuDung',
          type: 'select',
          message: 'Mục đích sử dụng không được để trống.'
        },
        {
          field: 'donViIdc',
          control: 'selectDonViIdc',
          type: 'select',
          message: 'Đơn vị IDC không được để trống.'
        },
        {
          field: 'idcId',
          control: 'selectIdc',
          type: 'select',
          message: 'IDC không được để trống.'
        },
        {
          field: 'toaNhaId',
          control: 'selectToaNha',
          type: 'select',
          message: 'Tòa nhà không được để trống.'
        },
        {
          field: 'matsanId',
          control: 'selectMatSan',
          type: 'select',
          message: 'Mặt sàn không được để trống.'
        },
        {
          field: 'thoiDiemVanHanh',
          control: 'dateThoiDiemVanHanh',
          type: 'date',
          message: 'Thời điểm vận hành không hợp lệ.'
        }
      ]

      // Validate each rule
      validationRules.forEach((rule) => {
        const value = this.form[rule.field]
        let isFieldValid = false

        if (rule.type === 'string') {
          isFieldValid = value && value.toString().trim() !== ''
        } else if (rule.type === 'select') {
          isFieldValid = value !== null && value !== undefined && value !== ''
        } else if (rule.type === 'number') {
          isFieldValid = value !== null && value !== undefined && value !== '' && !isNaN(value)
        } else if (rule.type === 'date') {
          const dateValidation = this.validateDate()
          isFieldValid = dateValidation.valid
          if (!isFieldValid) {
            this.controls[rule.control].invalid = true
            this.$root.toastError(dateValidation.error || rule.message)
            isValid = false
          } else {
            this.controls[rule.control].invalid = false
          }
        }

        // Handle validation error (skip if date already handled)
        if (!isFieldValid && rule.type !== 'date') {
          this.controls[rule.control].invalid = true
          this.$root.toastError(rule.message)
          isValid = false
        }
      })

      return isValid
    },
    grdItems_RowSelected: async function (args) {
      // Chỉ cho phép thay đổi current item khi không ở chế độ edit
      if (this.buttons.btnSave.enabled) {
        return // Không cho phép thay đổi khi đang edit
      }

      try {
        this.$root.showLoading(true)
        this.setCurrentItem(args.data)
      } finally {
        this.$root.showLoading(false)
      }
    },

    // ====== API ====== //

    getDanhSachPhongZone: async function () {
      try {
        return await api.getDanhSachPhongZone(this.axios)
      } catch (error) {
        this.handleApiError(error)
        return []
      }
    },

    getDanhMucMatSan: async function () {
      try {
        return await api.getDanhMucMatSan(this.axios)
      } catch (e) {
        this.handleApiError(e)
        return []
      }
    },

    upSertThongTinPhongZone: async function (payload) {
      try {
        return await api.upSertThongTinPhongZone(this.axios, payload)
      } catch (e) {
        this.handleApiError(e)
        return false
      }
    },

    deletePhongZone: async function (payload) {
      try {
        return await api.deletePhongZone(this.axios, payload)
      } catch (e) {
        this.handleApiError(e)
        return false
      }
    },

    async importPhongZoneData(data) {
      if (!data || data.length === 0) {
        this.$root.toastError('Không có dữ liệu hợp lệ để import!')
        return false
      }
      let successCount = 0
      let errorCount = 0
      try {
        const result = await api.importPhongZoneData(this.axios, data)
        successCount = result.successCount || 0
        errorCount = result.failCount || 0
      } catch (err) {
        this.$root.toastError(`Import lỗi`)
        console.error(err)
      } finally {
        this.dsPhongZone = await this.getDanhSachPhongZone()
        if (successCount > 0) {
          this.$root.toastSuccess(`Import thành công ${successCount} phòng/zone`)
        }
        if (errorCount > 0) {
          this.$root.toastError(`Import thất bại ${errorCount} phòng/zone. Vui lòng kiểm tra lại dữ liệu.`)
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
    },

    // ====== VALIDATE ====== //
    validateMatSanExists() {
      return (value) => {
        if (!this.matSanSelectionList || this.matSanSelectionList.length === 0) {
          return null // Không validate nếu chưa có danh mục
        }

        const valueStr = value
          .toString()
          .trim()
          .toLowerCase()
        const exists = this.matSanSelectionList.some((ms) => {
          const msName =
            ms.TEN == null
              ? ''
              : ms.TEN.toString()
                .trim()
                .toLowerCase()
          return msName === valueStr
        })

        if (!exists) {
          return `Mặt sàn với tên "${value}" không tồn tại trong danh mục`
        }
        return null
      }
    },
    validateTrangThaiVanHanhExists() {
      return (value) => {
        if (!this.trangThaiVanHanhList || this.trangThaiVanHanhList.length === 0) {
          return null // Không validate nếu chưa có danh mục
        }

        // Kiểm tra xem có phải là số (ID) không
        if (typeof value === 'number' || (!isNaN(value) && value.toString().trim() !== '')) {
          const id = typeof value === 'number' ? value : parseInt(value)
          const existsById = this.trangThaiVanHanhList.some((ttvh) => ttvh.ID === id)
          if (existsById) {
            return null
          }
          return `Trạng thái vận hành với ID ${value} không tồn tại trong danh mục`
        }

        // Kiểm tra theo tên (không phân biệt hoa thường)
        const valueStr = value
          .toString()
          .trim()
          .toLowerCase()
        const existsByName = this.trangThaiVanHanhList.some((ttvh) => {
          const ttvhName =
            ttvh.TEN == null
              ? ''
              : ttvh.TEN.toString()
                .trim()
                .toLowerCase()
          return ttvhName === valueStr
        })

        if (!existsByName) {
          return `Trạng thái vận hành với tên "${value}" không tồn tại trong danh mục`
        }

        return null
      }
    },

    // ====== DROPDOWN METHODS ====== //
    async loadDonViIdcList() {
      try {
        return await api.getDanhMucDonViIdc(this.axios)
      } catch (e) {
        this.handleApiError(e)
        return []
      }
    },

    async loadIdcList(donViId) {
      try {
        return await api.getDanhMucIdc(this.axios, donViId)
      } catch (e) {
        this.handleApiError(e)
        return []
      }
    },

    async loadToaNhaList(idcId) {
      try {
        this.toaNhaList = (await api.getDanhMucToaNha(this.axios, idcId)) || []
      } catch (e) {
        this.handleApiError(e)
        this.toaNhaList = []
      }
    },

    async loadMatSanList(toaNhaId) {
      try {
        if (!toaNhaId) {
          this.matSanSelectionList = await this.getDanhMucMatSan()
          return
        }
        this.matSanSelectionList = await api.getDanhMucMatSanByToaNha(this.axios, toaNhaId)
      } catch (e) {
        this.handleApiError(e)
        this.matSanSelectionList = []
      }
    },

    async loadMucDichSuDungList() {
      try {
        return await api.getDanhMucMucDichSuDung(this.axios)
      } catch (e) {
        this.handleApiError(e)
        return []
      }
    },

    async loadTrangThaiVanHanhList() {
      try {
        return await api.getDanhMucTrangThaiVanHanh(this.axios)
      } catch (e) {
        this.handleApiError(e)
        return []
      }
    },

    async onDonViIdcChange() {
      this.form.idcId = ''
      this.form.toaNhaId = ''
      this.form.phongZoneId = ''
      const idcList = await this.loadIdcList(this.form.donViIdc)
      this.idcList = idcList
    },

    async onIdcChange() {
      this.form.toaNhaId = ''
      this.form.phongZoneId = ''
      await this.loadToaNhaList(this.form.idcId)
    },

    async onToaNhaChange() {
      this.form.phongZoneId = ''
      await this.loadMatSanList(this.form.toaNhaId)
    },

    // ====== IMAGE UPLOAD METHODS ====== //
    onClickUploadAnhDaiDien() {
      if (this.$refs.fileInputAnhDaiDien) {
        this.$refs.fileInputAnhDaiDien.value = null
        this.$refs.fileInputAnhDaiDien.click()
      }
    },

    async onChangeAnhDaiDien(event) {
      try {
        const files = event.target.files
        if (!files || files.length === 0) {
          return
        }
        const file = files[0]

        // Validate file type
        if (!file.type.startsWith('image/')) {
          this.$root.toastError('Vui lòng chọn file ảnh hợp lệ!')
          return
        }

        // Validate file size (max 5MB)
        if (file.size > 5 * 1024 * 1024) {
          this.$root.toastError('Kích thước file không được vượt quá 5MB!')
          return
        }

        // Chỉ lưu tạm thời file và hiển thị preview local, không gọi API ngay
        this.pendingRemoveAnhDaiDien = false
        this.pendingAnhDaiDienFile = file
        // Tạo URL preview tạm thời
        if (this.pendingAnhDaiDienPreviewUrl) {
          try {
            URL.revokeObjectURL(this.pendingAnhDaiDienPreviewUrl)
          } catch (e) { }
        }
        this.pendingAnhDaiDienPreviewUrl = URL.createObjectURL(file)
        this.previewAnhDaiDien = this.pendingAnhDaiDienPreviewUrl
      } catch (e) {
        this.$root.toastError('Có lỗi khi tải ảnh')
      }
    },

    onRemoveAnhDaiDien() {
      this.pendingRemoveAnhDaiDien = true
      this.pendingAnhDaiDienFile = null
      this.pendingAnhDaiDienPreviewUrl = ''
      this.previewAnhDaiDien = ''
    },

    // ====== Minio helpers ======
    async uploadFileToMinio(item) {
      try {
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
        this.$root.toastError('Có lỗi xảy ra trong quá trình upload file!')
        return null
      }
    },
    async getPresignedUrlFromMinio(src) {
      try {
        if (!src) return null
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
        if (!src) return false
        let url = `/web-ecms/storage/deleteFile`
        let response = await this.$root.context.post(url, { fileSource: src })
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          if (!response.data.status) this.$root.toastError(response.data.message)
          return response.data.status
        } else return false
      } catch (error) {
        console.log(error)
        return false
      }
    },

    // ====== MAP LOCATION METHODS ====== //
    onClickChonViTri() {
      // TODO: Implement map location picker
      // For now, show a prompt
      const coords = prompt('Nhập tọa độ (ví dụ: 21.028511_105.804817 hoặc 21.028511, 105.804817):')
      if (coords) {
        // Format: TOADO_X + '_' + TOADO_Y
        let formattedCoords = coords.trim()
        // If user enters comma-separated, convert to underscore
        if (formattedCoords.includes(',')) {
          formattedCoords = formattedCoords.replace(/,/g, '_').replace(/\s+/g, '')
        }
        this.form.viTri = formattedCoords
      }
    },
    async loadThuocTinh(phongZoneId) {
      try {
        const response = await api.getThuocTinhPhongZone(this.$root.context, phongZoneId)
        if (response.data) {
          this.thuocTinhBoSung = response.data || []
        }
      } catch (error) {
        console.error('Error fetching thuoc tinh:', error)
        this.thuocTinhBoSung = []
      }
    },
    async saveTempThuocTinh(phongZoneId) {
      try {
        for (let i = 0; i < this.tempThuocTinhBoSung.length; i++) {
          const item = this.tempThuocTinhBoSung[i]
          const data = {
            kieuTT: 'PHONG/ZONE',
            duLieuId: phongZoneId,
            ten: item.TEN,
            giaTri: item.GIATRI,
            thuTu: i + 1
          }
          await api.addThuocTinhPhongZone(this.$root.context, phongZoneId, data)
        }
      } catch (error) {
        console.error('Error saving temp thuoc tinh:', error)
      }
    },

    // ====== THUỘC TÍNH BỔ SUNG METHODS ====== //
    actionColumn() {
      const self = this
      return function() {
        return {
          template: {
            template: `
              <div class="action-buttons" style="display: flex; gap: 5px; justify-content: center;">
                <button
                  class="btn btn-sm btn-outline-primary"
                  @click="onEdit()">
                  <i class="fa fa-pencil"></i> Sửa
                </button>
                <button
                  class="btn btn-sm btn-outline-danger"
                  @click="onDelete()">
                  <i class="fa fa-trash"></i> Xóa
                </button>
              </div>
            `,
            methods: {
              async onEdit() {
                self.onEditThuocTinh(this.data)
              },
              async onDelete() {
                self.onDeleteThuocTinh(this.data)
              }
            }
          }
        }
      }
    },

    async loadThuocTinhBoSung(phongZoneId) {
      try {
        if (!phongZoneId || phongZoneId <= 0) {
          this.dsThuocTinhBoSung = []
          return
        }
        const result = await api.getThuocTinhPhongZone(this.axios, phongZoneId)
        this.dsThuocTinhBoSung = result || []
      } catch (error) {
        console.error('Error loading thuộc tính bổ sung:', error)
        this.dsThuocTinhBoSung = []
      }
    },

    async onAddThuocTinh() {
      const timestamp = Date.now()
      const html = `
        <div style="text-align: left;">
          <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold;">Tên thuộc tính:</label>
            <input id="thuocTinhTen_${timestamp}" required class="swal2-input" style="width: 90%; margin: 0;" placeholder="Nhập tên thuộc tính" value="" autocomplete="off" />
          </div>
          <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold;">Giá trị:</label>
            <input id="thuocTinhGiaTri_${timestamp}" required class="swal2-input" style="width: 90%; margin: 0;" placeholder="Nhập giá trị" value="" autocomplete="off" />
          </div>
        </div>
      `

      const result = await this.$confirm(html, 'Thêm thuộc tính', {
        confirmButtonText: 'Thêm',
        cancelButtonText: 'Hủy',
        dangerouslyUseHTMLString: true,
        showCancelButton: true
      })
        .then(() => {
          const ten = document.getElementById(`thuocTinhTen_${timestamp}`).value
          const giaTri = document.getElementById(`thuocTinhGiaTri_${timestamp}`).value
          if (!ten || !giaTri) {
            this.$root.toastError('Vui lòng nhập đầy đủ thông tin')
            return null
          }
          return { ten, giaTri }
        })
        .catch(() => null)

      if (result) {
        if (this.form.id > 0) {
          // Phòng/zone đã tồn tại: lưu vào DB
          try {
            this.$root.showLoading(true)
            const data = {
              kieuTT: 'PHONG_ZONE',
              duLieuId: this.form.id,
              ten: result.ten,
              giaTri: result.giaTri,
              thuTu: this.dsThuocTinhBoSung.length + 1
            }

            await api.addThuocTinhPhongZone(this.axios, data)
            this.$root.toastSuccess('Thêm thuộc tính thành công')
            await this.loadThuocTinhBoSung(this.form.id)
          } catch (error) {
            this.$root.toastError('Thêm thuộc tính thất bại: ' + (error.response?.data?.message || error.message))
          } finally {
            this.$root.showLoading(false)
          }
        } else {
          // Tạo mới phòng/zone: lưu vào local state
          const tempData = {
            THUOCTINH_ID: `temp_${Date.now()}`,
            TEN: result.ten,
            GIATRI: result.giaTri,
            THUTU: this.dsThuocTinhBoSung.length + 1
          }
          this.dsThuocTinhBoSung.push(tempData)
        }
      }
    },

    async onEditThuocTinh(attribute) {
      const timestamp = Date.now()
      const html = `
        <div style="text-align: left;">
          <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold;">Tên thuộc tính:</label>
            <input id="thuocTinhTen_${timestamp}" required class="swal2-input" style="width: 90%; margin: 0;" placeholder="Nhập tên thuộc tính" value="${attribute.TEN || ''}" autocomplete="off" />
          </div>
          <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold;">Giá trị:</label>
            <input id="thuocTinhGiaTri_${timestamp}" required class="swal2-input" style="width: 90%; margin: 0;" placeholder="Nhập giá trị" value="${attribute.GIATRI || ''}" autocomplete="off" />
          </div>
        </div>
      `

      const result = await this.$confirm(html, 'Sửa thuộc tính', {
        confirmButtonText: 'Cập nhật',
        cancelButtonText: 'Hủy',
        dangerouslyUseHTMLString: true,
        showCancelButton: true
      })
        .then(() => {
          const ten = document.getElementById(`thuocTinhTen_${timestamp}`).value
          const giaTri = document.getElementById(`thuocTinhGiaTri_${timestamp}`).value
          if (!ten || !giaTri) {
            this.$root.toastError('Vui lòng nhập đầy đủ thông tin')
            return null
          }
          return { ten, giaTri }
        })
        .catch(() => null)

      if (result) {
        if (this.form.id > 0) {
          // Phòng/zone đã tồn tại: cập nhật vào DB
          try {
            this.$root.showLoading(true)
            const data = {
              kieuTT: 'PHONG_ZONE',
              duLieuId: this.form.id,
              ten: result.ten,
              giaTri: result.giaTri,
              thuTu: attribute.THUTU || 0
            }

            await api.updateThuocTinhPhongZone(this.axios, attribute.THUOCTINH_ID, data)
            this.$root.toastSuccess('Cập nhật thuộc tính thành công')
            await this.loadThuocTinhBoSung(this.form.id)
          } catch (error) {
            this.$root.toastError('Cập nhật thuộc tính thất bại: ' + (error.response?.data?.message || error.message))
          } finally {
            this.$root.showLoading(false)
          }
        } else {
          // Tạo mới phòng/zone: cập nhật local state
          const index = this.dsThuocTinhBoSung.findIndex((item) => item.THUOCTINH_ID === attribute.THUOCTINH_ID)
          if (index !== -1) {
            this.dsThuocTinhBoSung[index].TEN = result.ten
            this.dsThuocTinhBoSung[index].GIATRI = result.giaTri
          }
        }
      }
    },

    async onDeleteThuocTinh(attribute) {
      const flag = await this.$confirm(`Có chắc bạn muốn xóa thuộc tính "${attribute.TEN}"?`, 'Xác nhận xóa', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning'
      })
        .then(() => true)
        .catch(() => false)

      if (flag) {
        if (this.form.id > 0) {
          // Phòng/zone đã tồn tại: xóa khỏi DB
          try {
            this.$root.showLoading(true)
            await api.deleteThuocTinhPhongZone(this.axios, attribute.THUOCTINH_ID)
            this.$root.toastSuccess('Xóa thuộc tính thành công')
            await this.loadThuocTinhBoSung(this.form.id)
          } catch (error) {
            this.$root.toastError('Xóa thuộc tính thất bại: ' + (error.response?.data?.message || error.message))
          } finally {
            this.$root.showLoading(false)
          }
        } else {
          // Tạo mới phòng/zone: xóa khỏi local state
          const index = this.dsThuocTinhBoSung.findIndex((item) => item.THUOCTINH_ID === attribute.THUOCTINH_ID)
          if (index !== -1) {
            this.dsThuocTinhBoSung.splice(index, 1)
          }
        }
      }
    },

    // Lưu các thuộc tính tạm thời vào DB sau khi phòng/zone được tạo
    async saveTempThuocTinh(newPhongZoneId) {
      try {
        for (let i = 0; i < this.dsThuocTinhBoSung.length; i++) {
          const tempAttr = this.dsThuocTinhBoSung[i]
          // Chỉ lưu các thuộc tính tạm thời (có THUOCTINH_ID bắt đầu bằng "temp_")
          if (tempAttr.THUOCTINH_ID && tempAttr.THUOCTINH_ID.toString().startsWith('temp_')) {
            const data = {
              kieuTT: 'PHONG_ZONE',
              duLieuId: newPhongZoneId,
              ten: tempAttr.TEN,
              giaTri: tempAttr.GIATRI,
              thuTu: tempAttr.THUTU || i + 1
            }
            await api.addThuocTinhPhongZone(this.axios, data)
          }
        }

        // Load lại danh sách từ DB
        await this.loadThuocTinhBoSung(newPhongZoneId)
      } catch (error) {
        this.$root.toastError('Lỗi khi lưu thuộc tính: ' + (error.response?.data?.message || error.message))
        throw error
      }
    }
  }
}
</script>
<style scoped>
/* Fix z-index cho Select2 dropdown trong EJS Dialog */
.e-dialog .select2-container--open {
  z-index: 10000 !important;
}

.e-dialog .select2-dropdown {
  z-index: 10001 !important;
}

.info-row >>> .key,
.info-row /deep/ .key {
  white-space: nowrap;
  flex-shrink: 0;
}

.info-row >>> .key.w200,
.info-row /deep/ .key.w200 {
  width: 230px;
}

.info-row >>> .value,
.info-row /deep/ .value {
  flex: 1;
}

.info-row >>> .preview-image,
.info-row /deep/ .preview-image {
  max-width: 140px;
  border-radius: 4px;
}
</style>
