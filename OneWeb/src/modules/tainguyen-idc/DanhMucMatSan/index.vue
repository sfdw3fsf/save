<template src="./index.template.html"></template>
<script>
import axios from 'axios'
import api from './api.js'
import ModalImportExcel from '../components/Modals/ModalImportExcel'
import PopupMatSanDetail from './components/PopupMatSanDetail/PopupMatSanDetail.vue'
const defaultItem = {
  id: 0,
  toanha_id: 0,
  ten: '',
  dien_tich: 0,
  chieu_dai: 0,
  chieu_rong: 0,
  mo_ta: '',
  su_dung: 1,
  ghi_chu: '',
  tong_rack_thiet_ke: 0,
  tong_rack_lap_dat: 0,
  so_rack_con_lai: 0,
  cong_suat_dinh_danh: 0,
  hinhanh: '',
  imageUrl: '',
  ten_dv: '',
  donvi_id: 0,
  vi_tri_toa_nha: '',
  vi_tri: '',
  ngay_sd: null,
  trangthai_vh: null,
  tong_dien_tich_zone: 0,
  tam_san_rong: null,
  tam_san_dai: null
}

export default {
  components: {
    ModalImportExcel,
    PopupMatSanDetail
  },
  data: function() {
    return {
      buttons: {
        btnNhapMoi: { enabled: true },
        btnGhiLai: { enabled: false },
        btnSua: { enabled: false },
        btnXoa: { enabled: false },
        btnHuyBo: { enabled: false }
      },
      originalName: '',
      listMatSan: [],
      currentItem: { ...defaultItem },
      controls: {
        txtID: { enabled: false, invalid: false },
        txtName: { enabled: false, invalid: false },
        txtDienTich: { enabled: false, invalid: false },
        txtChieuDai: { enabled: false, invalid: false },
        txtChieuRong: { enabled: false, invalid: false },
        checkBoxSuDung: { enabled: false },
        txtNote: { enabled: false }
      },
      donviIdcList: [],
      idcList: [],
      filteredIdcList: [],
      buildingList: [],
      filteredBuildings: [],
      selectedDonviIDC: null,
      selectedIDC: null,
      dsSelectedItems: [],
      tenMatSanExists: false,
      trangThaiVhList: [
        { id: 10, ten: 'Hoạt động bình thường' },
        { id: 11, ten: 'Bảo trì' },
        { id: 12, ten: 'Cảnh báo' },
        { id: 13, ten: 'Sự cố' },
        { id: 14, ten: 'Khôi phục' },
        { id: 15, ten: 'Chưa sử dụng' }
      ],
      // Popup state
      selectedItemId: 0,
      dialogMode: 'VIEW',
      // Cấu hình cho modal import
      importConfig: {
        title: 'Import dữ liệu Mặt sàn',
        templateFileName: 'Template_Import_MatSan.xlsx',
        tableColumns: [
          { key: 'ten', label: 'Tên mặt sàn' },
          { key: 'toa_nha', label: 'Tòa nhà' },
          { key: 'dien_tich', label: 'Diện tích (m²)' },
          { key: 'tam_san_rong', label: 'Tấm sàn rộng (cm)' },
          { key: 'tam_san_dai', label: 'Tấm sàn dài (cm)' },
          { key: 'su_dung', label: 'Hiệu lực (1 :Hiệu lực | 0 : không hiệu lực)' },
          { key: 'ngay_sd', label: 'Thời điểm đưa vào sử dụng (DD/MM/YYYY)' },
          { key: 'trangthai_vh', label: 'Trạng thái vận hành' },
          { key: 'vi_tri', label: 'Vị trí mặt sàn' },
          { key: 'ghi_chu', label: 'Ghi chú' }
        ],
        gridColumns: [
          { key: 'ten', label: 'Tên mặt sàn ' },
          { key: 'toa_nha', label: 'Tòa nhà' },
          { key: 'dien_tich', label: 'Diện tích (m²)' },
          { key: 'tam_san_rong', label: 'Tấm sàn rộng (cm)' },
          { key: 'tam_san_dai', label: 'Tấm sàn dài (cm)' },
          { key: 'su_dung', label: 'Hiệu lực (1 :Hiệu lực | 0 : không hiệu lực)' },
          { key: 'ngay_sd_display', label: 'Thời điểm đưa vào sử dụng' },
          { key: 'trangthai_vh_text', label: 'Trạng thái vận hành' },
          { key: 'vi_tri', label: 'Vị trí mặt sàn' },
          { key: 'ghi_chu', label: 'Ghi chú' }
        ],
        validationRules: [
          { field: 'ten', required: true, message: 'Tên mặt sàn không được để trống' },
          { field: 'ten', maxLength: 100, message: 'Tên mặt sàn không được vượt quá 100 ký tự' },
          { field: 'toa_nha', required: true, message: 'Tòa nhà không được để trống' },
          { field: 'dien_tich', required: true, message: 'Diện tích không được để trống' },
          { field: 'dien_tich', min: 0, type: 'number', message: 'Diện tích phải lớn hơn 0' },
          { field: 'tam_san_rong', min: 0, type: 'number', message: 'Tấm sàn rộng phải là số dương' },
          { field: 'tam_san_dai', min: 0, type: 'number', message: 'Tấm sàn dài phải là số dương' },
          // { field: 'chieu_dai', required: true, message: 'Chiều dài không được để trống' },
          // { field: 'chieu_dai', min: 0, type: 'number', message: 'Chiều dài phải lớn hơn 0' },
          // { field: 'chieu_rong', required: true, message: 'Chiều rộng không được để trống' },
          // { field: 'chieu_rong', min: 0, type: 'number', message: 'Chiều rộng phải lớn hơn 0' },
          {
            field: 'su_dung',
            type: 'number',
            enum: [0, 1],
            message: 'Hiệu lực chỉ nhận giá trị 0 và 1 (1 :Hiệu lực | 0 : không hiệu lực)  '
          },
          // { field: 'ngay_sd', required: true, message: 'Thời điểm đưa vào vận hành không được để trống' },
          { field: 'trangthai_vh', required: true, message: 'Trạng thái vận hành không được để trống' },
          { field: 'vi_tri', maxLength: 200, message: 'Vị trí mặt sàn không được vượt quá 200 ký tự' },
          { field: 'ghi_chu', maxLength: 500, message: 'Ghi chú không được vượt quá 500 ký tự' }
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
      await this.loadIdcBuildings()
      let rawList = await this.getList()
      // Map tên IDC và Toà nhà
      this.listMatSan = rawList.map((item) => {
        const idc = this.idcList.find((x) => x.id === item.IDC_ID)
        const building = this.buildingList.find((x) => x.ID_TOANHA === item.TOANHA_ID)
        return {
          ...item,
          IDC_NAME: idc ? idc.ten : '',
          TOANHA_NAME: building ? building.TEN_TOANHA : '',
          SUDUNG_TEXT: item.SUDUNG == 1 ? 'Hiệu lực' : 'Không hiệu lực'
        }
      })

      // Lấy ID từ URL query params (?id=123)
      const idFromUrl = parseInt(this.$route?.query?.id || this.modalItemID || 0)
      if (idFromUrl > 0) {
        let item = this.listMatSan.find((x) => x.ID == idFromUrl)
        if (item != null) {
          await this.setCurrentItem(item)
          // Đợi nextTick để đảm bảo grid đã render
          await this.$nextTick()
          this.$refs.grdItems.selectRow(item)
        }
      }
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    actionColumn(parent) {
      const self = this
      return function() {
        return {
          template: {
            template: `
              <div style="display: flex; gap: 8px; justify-content: center;">
                <button @click="onEdit()" class="btn btn-sm btn-warning" title="Sửa">
                  <i class="fa fa-edit"></i>
                </button>
                <button @click="onView()" class="btn btn-sm btn-info" title="Xem">
                  <i class="fa fa-eye"></i>
                </button>
              </div>
            `,
            methods: {
              onEdit() {
                self.openEditPopup(this.data.ID)
              },
              onView() {
                self.openViewPopup(this.data.ID)
              }
            }
          }
        }
      }
    },
    openEditPopup(itemId) {
      this.selectedItemId = itemId
      this.dialogMode = 'EDIT'
      this.$nextTick(() => {
        this.$refs.popupMatSan.open()
      })
    },
    openViewPopup(itemId) {
      this.selectedItemId = itemId
      this.dialogMode = 'VIEW'
      this.$nextTick(() => {
        this.$refs.popupMatSan.open()
      })
    },
    handleErrorResponse(error) {
      if (error?.response?.data) {
        this.$root.toastError(error.response.data.message_detail)
      }
    },
    getSuDungText(value) {
      return ''
    },
    async getList() {
      try {
        let rs = await api.getAllMatSan(this.$root.context)
        // let rs = await axios.get('http://localhost:10990/danh-muc-mat-san/get-all')
        return rs.data || []
      } catch (e) {
        this.handleErrorResponse(e)
        return []
      }
    },
    async loadIdcBuildings() {
      try {
        // const rs = await axios.get('http://localhost:10990/danh-muc-mat-san/idc-toanha')
        const rs = await api.getIdcToaNha(this.$root.context)
        if (rs) {
          this.buildingList = rs?.data || []

          // Lấy danh sách Đơn vị IDC (unique, không lọc HIEULUC vì API đã lọc)
          this.donviIdcList = [
            ...new Map(
              this.buildingList.map((item) => [item.ID_DONVI, { id: item.ID_DONVI, ten: item.TEN_DONVI }])
            ).values()
          ]

          // Lấy tất cả IDC (sẽ filter khi chọn đơn vị)
          this.idcList = [
            ...new Map(
              this.buildingList
                .filter((item) => item.HIEULUC_IDC == 1)
                .map((item) => [item.ID_IDC, { id: item.ID_IDC, ten: item.TEN_IDC, donvi_id: item.ID_DONVI }])
            ).values()
          ]
        }
      } catch (error) {
        this.handleErrorResponse(error)
      }
    },
    onDonviIDCChange(donviId) {
      this.selectedDonviIDC = donviId
      // Lọc IDC theo đơn vị và HIEULUC_IDC
      this.filteredIdcList = this.idcList.filter((x) => x.donvi_id == donviId)
      // Reset IDC và Toà nhà
      this.selectedIDC = null
      this.filteredBuildings = []
      this.currentItem.toanha_id = 0
    },
    onIDCChange(idcId) {
      this.selectedIDC = idcId
      // Lọc toà nhà theo IDC và HIEULUC_TOANHA
      this.filteredBuildings = this.buildingList.filter((x) => x.ID_IDC == idcId && x.HIEULUC_TOANHA == 1)
      this.currentItem.toanha_id = 0
    },
    async addItem(args) {
      try {
        const payload = {
          id: 0,
          ten: args.ten?.trim(),
          toanha_id: args.toanha_id,
          dien_tich: args.dien_tich,
          chieu_dai: args.chieu_dai,
          chieu_rong: args.chieu_rong,
          ghi_chu: args.ghi_chu?.trim(),
          mo_ta: args.mo_ta?.trim(),
          su_dung: args.su_dung,
          hinh_anh: args.hinhanh || null,
          ngay_sd: args.ngay_sd || null,
          trangthai_vh: args.trangthai_vh || null,
          vi_tri: args.vi_tri?.trim() || null,
          tam_san_rong: args.tam_san_rong || null,
          tam_san_dai: args.tam_san_dai || null
        }
        let rs = await api.insertUpdateMatSan(this.$root.context, payload)
        // let rs = await this.$root.context.post('/web-ecms/danh-muc-mat-san/insert-update', payload)
        return rs
      } catch (error) {
        this.$root.toastError('Lỗi khi thêm mặt sàn')
      }
    },
    async updateItem(args) {
      const payload = {
        id: args.id,
        ten: args.ten?.trim(),
        toanha_id: args.toanha_id,
        dien_tich: args.dien_tich,
        chieu_dai: args.chieu_dai,
        chieu_rong: args.chieu_rong,
        ghi_chu: args.ghi_chu?.trim(),
        mo_ta: args.mo_ta?.trim(),
        su_dung: args.su_dung,
        hinh_anh: args.hinhanh || null,
        ngay_sd: args.ngay_sd || null,
        trangthai_vh: args.trangthai_vh || null,
        vi_tri: args.vi_tri?.trim() || null,
        tam_san_rong: args.tam_san_rong || null,
        tam_san_dai: args.tam_san_dai || null
      }
      let rs = await api.insertUpdateMatSan(this.$root.context, payload)
      return rs
    },
    async deleteItem(id) {
      try {
        const rs = await api.deleteMatSan(this.$root.context, id)
        // const rs = await axios.delete(`https://api-dev-onebss.vnpt.vn/web-ecms/danh-muc-mat-san/delete/${id}`)
        // const rs = await axios.post(`http://localhost:10990/danh-muc-mat-san/delete/${id}`)
        return rs // dữ liệu trả về từ ApiResult
      } catch (error) {
        return error.response.data
      }
    },
    async setCurrentItem(data) {
      const v = (k1, k2, d = 0) => data?.[k1] ?? data?.[k2] ?? d
      this.currentItem = {
        id: v('ID', 'id'),
        ten: v('TEN', 'ten', ''),
        toanha_id: v('TOANHA_ID', 'toanha_id', 0),
        dien_tich: v('DIEN_TICH', 'dien_tich', 0),
        chieu_dai: v('CHIEU_DAI', 'chieu_dai', 0),
        chieu_rong: v('CHIEU_RONG', 'chieu_rong', 0),
        ghi_chu: v('GHI_CHU', 'ghi_chu', ''),
        mo_ta: v('MOTA', 'mo_ta', ''),
        su_dung: v('SUDUNG', 'su_dung', 1),

        // --- Các field chỉ hiển thị, không update/insert ---
        tong_rack_thiet_ke: v('TONG_RACK_THIET_KE', 'tong_rack_thiet_ke', 0),
        tong_rack_lap_dat: v('TONG_RACK_LAP_DAT', 'tong_rack_lap_dat', 0),
        so_rack_con_lai: v('SO_RACK_CON_LAI', 'so_rack_con_lai', 0),
        cong_suat_dinh_danh: v('CONG_SUAT_DINH_DANH', 'cong_suat_dinh_danh', 0),
        hinhanh: v('HINHANH', 'hinhanh', ''),
        imageUrl: '',
        ten_dv: v('TEN_DV', 'ten_dv', ''),
        donvi_id: v('DONVI_ID', 'donvi_id', 0),
        vi_tri_toa_nha: v('VI_TRI_MAT_SAN', 'vi_tri_mat_san', ''),
        vi_tri: v('VI_TRI', 'vi_tri', ''),
        ngay_sd: this.formatDateForInput(v('NGAY_SD', 'ngay_sd', null)),
        trangthai_vh: v('TRANGTHAI_VH', 'trangthai_vh', null),
        tong_dien_tich_zone: v('TONG_DIEN_TICH_ZONE', 'tong_dien_tich_zone', 0),
        tam_san_rong: v('TAM_SAN_RONG', 'tam_san_rong', null),
        tam_san_dai: v('TAM_SAN_DAI', 'tam_san_dai', null)
      }

      // Lấy presigned URL nếu có hình ảnh
      if (this.currentItem.hinhanh) {
        await this.getPresignedUrl(this.currentItem.hinhanh)
      }

      this.originalName = this.currentItem.ten

      // Set selected values khi chọn row
      this.selectedDonviIDC = v('DONVI_ID', 'donvi_id', null)
      this.selectedIDC = v('IDC_ID', 'idc_id', null)

      // Filter IDC theo đơn vị
      if (this.selectedDonviIDC) {
        this.filteredIdcList = this.idcList.filter((x) => x.donvi_id == this.selectedDonviIDC)
      }

      // Filter toà nhà theo IDC và HIEULUC_TOANHA
      if (this.selectedIDC) {
        this.filteredBuildings = this.buildingList.filter((x) => x.ID_IDC === this.selectedIDC && x.HIEULUC_TOANHA == 1)
      }
    },
    enableControls(args) {
      this.controls.txtName.enabled = args
      this.controls.txtDienTich.enabled = args
      this.controls.txtChieuDai.enabled = args
      this.controls.txtChieuRong.enabled = args
      this.controls.checkBoxSuDung.enabled = args
      this.controls.txtNote.enabled = args
      this.buttons.btnGhiLai.enabled = args
      this.buttons.btnNhapMoi.enabled = !args
      this.buttons.btnHuyBo.enabled = args
      if (this.currentItem.id > 0) {
        this.buttons.btnSua.enabled = !args
      }
    },
    onSelectRowChanged(item) {
      if (!item || !item.data) return
      this.setCurrentItem(item.data)
      this.enableControls(false)
    },
    onSelectRowCheckbox(selectedItems) {
      this.dsSelectedItems = selectedItems || []
      this.buttons.btnXoa.enabled = this.dsSelectedItems.length > 0
    },
    setDefaultItem() {
      Object.assign(this.currentItem, defaultItem)
    },
    validateControls() {
      if (!this.currentItem.ten || this.currentItem.ten.trim() === '') {
        this.$root.toastError('Tên mặt sàn không được để trống.')
        return false
      }
      if (!this.currentItem.toanha_id || this.currentItem.toanha_id === 0) {
        this.$root.toastError('Toà nhà không được để trống.')
        return false
      }
      if (this.tenMatSanExists) {
        this.$root.toastError('Tên mặt sàn đã tồn tại. Vui lòng chọn tên khác.')
        return false
      }
      // if (this.currentItem.chieu_dai < 0) {
      //   this.$root.toastError('Chiều dài không được nhỏ hơn 0.')
      //   return false
      // }
      // if (
      //   this.currentItem.chieu_dai == null ||
      //   this.currentItem.chieu_dai == undefined ||
      //   this.currentItem.chieu_dai == ''
      // ) {
      //   this.$root.toastError('Chiều dài không được để trống.')
      //   return false
      // }
      // if (this.currentItem.chieu_rong < 0) {
      //   this.$root.toastError('Chiều rộng không được nhỏ hơn 0.')
      //   return false
      // }
      // if (
      //   this.currentItem.chieu_rong == null ||
      //   this.currentItem.chieu_rong == undefined ||
      //   this.currentItem.chieu_rong == ''
      // ) {
      //   this.$root.toastError('Chiều rộng không được để trống.')
      //   return false
      // }
      if (this.currentItem.dien_tich < 0) {
        this.$root.toastError('Diện tích không được nhỏ hơn 0.')
        return false
      }
      if (
        this.currentItem.dien_tich == null ||
        this.currentItem.dien_tich == undefined ||
        this.currentItem.dien_tich == ''
      ) {
        this.$root.toastError('Diện tích không được để trống.')
        return false
      }

      // Validate kích thước tấm sàn
      if (this.currentItem.tam_san_rong != null && this.currentItem.tam_san_rong !== '') {
        if (this.currentItem.tam_san_rong <= 0) {
          this.$root.toastError('Tấm sàn rộng phải lớn hơn 0.')
          return false
        }
      }
      if (this.currentItem.tam_san_dai != null && this.currentItem.tam_san_dai !== '') {
        if (this.currentItem.tam_san_dai <= 0) {
          this.$root.toastError('Tấm sàn dài phải lớn hơn 0.')
          return false
        }
      }

      // if (!this.currentItem.hinhanh || this.currentItem.hinhanh.trim() === '') {
      //   this.$root.toastError('Ảnh đại diện không được để trống.')
      //   return false
      // }
      // if (!this.currentItem.ngay_sd) {
      //   this.$root.toastError('Thời điểm đưa vào vận hành không được để trống.')
      //   return false
      // }
      if (!this.currentItem.trangthai_vh) {
        this.$root.toastError('Trạng thái vận hành không được để trống.')
        return false
      }
      return true
    },
    async btnSaveClick() {
      if (this.validateControls()) {
        let flag = await this.$confirm('Có chắc bạn muốn lưu lại mặt sàn đang chọn?', 'Xác nhận thực hiện', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'info'
        })
          .then(() => true)
          .catch(() => false)
        if (flag) {
          try {
            this.$root.showLoading(true)
            let rs = null
            if (this.currentItem.id == 0) {
              rs = await this.addItem(this.currentItem)
              if (rs) this.$root.toastSuccess('Thêm mặt sàn thành công')
            } else {
              rs = await this.updateItem(this.currentItem)
              if (rs && rs.error == '200') {
                this.$root.toastSuccess('Cập nhật mặt sàn thành công')
              } else {
                this.$root.toastError('Cập nhật thất bại')
              }
            }
            this.enableControls(!rs)
            //await this.load()
            const rawList = await this.getList()
            this.listMatSan = this.mapListMatSan(rawList)
          } catch (e) {
            this.handleErrorResponse(e)
          } finally {
            this.$root.showLoading(false)
            this.enableControls(false)
          }
        }
      }
    },
    btnNewClick() {
      // Mở popup ở chế độ ADD
      this.selectedItemId = 0
      this.dialogMode = 'ADD'
      this.$nextTick(() => {
        this.$refs.popupMatSan.open()
      })
    },
    btnCancelClick() {
      this.setDefaultItem()
      this.enableControls(false)
      this.tenMatSanExists = false
      // Reset các dropdown
      this.selectedDonviIDC = null
      this.selectedIDC = null
      this.filteredIdcList = []
      this.filteredBuildings = []
    },
    btnEditClick() {
      // Mở popup edit thay vì enable form
      if (this.currentItem.id > 0) {
        this.selectedItemId = this.currentItem.id
        this.dialogMode = 'EDIT'
        this.$nextTick(() => {
          this.$refs.popupMatSan.open()
        })
      }
    },
    btnDeleteClick() {
      this.$confirm(`Bạn có chắc muốn xóa ${this.dsSelectedItems.length} mặt sàn đã chọn?`, 'Xác nhận xóa', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning',
        dangerouslyUseHTMLString: true
      })
        .then(async () => {
          try {
            this.$root.showLoading(true)
            let successCount = 0
            let errorCount = 0
            for (let i = 0; i < this.dsSelectedItems.length; i++) {
              const id = this.dsSelectedItems[i]
              const rs = await this.deleteItem(id)
              if (rs.error == '200' && rs.data != '-1' && rs.data != '0') {
                successCount++
              } else {
                this.$root.toastError(`Mặt sàn ID ${id} đang được sử dụng, không thể xóa.`)
                errorCount++
              }
            }
            if (successCount > 0) this.$root.toastSuccess(`Xóa thành công ${successCount} mặt sàn`)
            if (errorCount > 0) this.$root.toastError(`Xóa thất bại ${errorCount} mặt sàn`)

            //await this.load()
            const rawList = await this.getList()
            this.listMatSan = this.mapListMatSan(rawList)
            this.setDefaultItem()
            this.enableControls(false)
          } catch (e) {
            this.handleErrorResponse(e)
          } finally {
            this.$root.showLoading(false)
          }
        })
        .catch(() => {
          // Hủy xóa
        })
    },
    btnXuatExcel_Click() {
      this.$refs.grdItems.excelExport({
        fileName: 'danh_sach_mat_san.xlsx'
      })
    },
    btnImportExcel_Click: async function() {
      try {
        const result = await this.$refs.modalImportExcel.showModal()

        if (result.success && result.data && result.data.length > 0) {
          // Xác nhận import
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.length} mặt sàn từ file Excel?`,
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
        this.$root.toastError('Có lỗi khi import Excel: ' + error.message)
      }
    },
    getColumnSuDungTemplate(value) {
      return this.getSuDungText(value)
    },
    rowUnselected(args) {
      this.setDefaultItem()
    },
    onRowDoubleClick(args) {
      // Double click để xem chi tiết
      if (args && args.rowData && args.rowData.ID) {
        this.selectedItemId = args.rowData.ID
        this.dialogMode = 'VIEW'
        this.$nextTick(() => {
          this.$refs.popupMatSan.open()
        })
      }
    },
    async handlePopupSaved(data) {
      // Reload danh sách sau khi lưu từ popup
      try {
        this.$root.showLoading(true)
        const rawList = await this.getList()
        this.listMatSan = this.mapListMatSan(rawList)

        // Nếu có data trả về, select item đó
        if (data && data.id) {
          this.$nextTick(() => {
            const item = this.listMatSan.find((x) => x.ID === data.id)
            if (item) {
              this.grdItems.selectRow(this.listMatSan.indexOf(item))
            }
          })
        }
      } catch (error) {
        console.error('Error reloading list:', error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    mapListMatSan(rawList) {
      return rawList.map((item) => {
        const idc = this.idcList.find((x) => x.id === item.IDC_ID)
        const building = this.buildingList.find((x) => x.ID_TOANHA === item.TOANHA_ID)
        return {
          ...item,
          IDC_NAME: idc ? idc.ten : '',
          TOANHA_NAME: building ? building.TEN_TOANHA : '',
          SUDUNG_TEXT: item.SUDUNG == 1 ? 'Hiệu lực' : 'Không hiệu lực'
        }
      })
    }, // Hàm xử lý dữ liệu Excel cho mặt sàn
    processMatSanExcelData(dataRows) {
      return dataRows.map((row, index) => {
        const toaNhaTen = (row[1] || '').trim()
        const buildingList = this.buildingList || []
        const toaNha = buildingList.find(
          (b) => (b.TEN_TOANHA || '').trim().toUpperCase() === toaNhaTen.toUpperCase() && b.HIEULUC_TOANHA == 1
        )
        if (!toaNha) {
          this.$root.toastError(`Không tìm thấy tòa nhà '${toaNhaTen}' ở dòng ${index + 1}`)
        }

        // Map trạng thái vận hành từ tên sang ID
        const trangThaiText = (row[7] || '').trim()
        const trangThaiTextUpper = trangThaiText.toUpperCase()
        const trangThaiMap = {
          'HOẠT ĐỘNG BÌNH THƯỜNG': 10,
          'HOAT DONG BINH THUONG': 10,
          'BẢO TRÌ': 11,
          'BAO TRI': 11,
          'CẢNH BÁO': 12,
          'CANH BAO': 12,
          'SỰ CỐ': 13,
          'SU CO': 13,
          'KHÔI PHỤC': 14,
          'KHOI PHUC': 14,
          'CHƯA SỬ DỤNG': 15,
          'CHUA SU DUNG': 15
        }
        const trangThaiId = trangThaiMap[trangThaiTextUpper]

        // Báo lỗi nếu trạng thái không hợp lệ
        if (trangThaiText && !trangThaiId) {
          this.$root.toastError(
            `Trạng thái vận hành không hợp lệ '${trangThaiText}' ở dòng ${index +
              1}. Các giá trị hợp lệ: Hoạt động bình thường, Bảo trì, Cảnh báo, Sự cố, Khôi phục, Chưa sử dụng`
          )
        }

        // Chuyển đổi ngày từ MM/DD/YYYY sang yyyy-MM-dd
        let ngaySdFormatted = null
        if (row[6]) {
          ngaySdFormatted = this.convertExcelDateToISO(row[6])
        }

        return {
          toanha_id: toaNha ? toaNha.ID_TOANHA : 0,
          ten: row[0] || '',
          toa_nha: row[1] || '',
          dien_tich: row[2],
          tam_san_rong: row[3],
          tam_san_dai: row[4],
          // chieu_dai: row[3],
          // chieu_rong: row[4],
          su_dung: row[5] || 1,
          ngay_sd: ngaySdFormatted,
          ngay_sd_display: ngaySdFormatted ? this.formatDateForDisplay(ngaySdFormatted) : '', // Hiển thị DD/MM/YYYY
          trangthai_vh: trangThaiId,
          trangthai_vh_text: row[7] || '', // Lưu text gốc để hiển thị lỗi nếu cần
          vi_tri: row[8] || '',
          ghi_chu: row[9] || '',
          mo_ta: ''
        }
      })
    },
    // Chuyển đổi ngày từ Excel sang yyyy-MM-dd
    convertExcelDateToISO(excelDate) {
      try {
        // Kiểm tra giá trị rỗng hoặc null trước
        if (!excelDate || excelDate === '' || excelDate === 0) {
          return null
        }

        let date

        // Nếu Excel trả về Date object
        if (excelDate instanceof Date) {
          date = excelDate
        }
        // Nếu là string
        else if (typeof excelDate === 'string') {
          const trimmed = excelDate.trim()
          if (!trimmed) return null

          const parts = trimmed.split('/')
          if (parts.length === 3) {
            const part1 = parseInt(parts[0])
            const part2 = parseInt(parts[1])
            const part3 = parseInt(parts[2])

            // Phân biệt DD/MM/YYYY vs MM/DD/YYYY
            // Nếu part1 > 12 => chắc chắn là DD/MM/YYYY
            // Nếu part2 > 12 => chắc chắn là MM/DD/YYYY
            if (part1 > 12) {
              // DD/MM/YYYY: 22/03/2024
              date = new Date(part3, part2 - 1, part1)
            } else if (part2 > 12) {
              // MM/DD/YYYY: 03/22/2024
              date = new Date(part3, part1 - 1, part2)
            } else {
              // Không rõ ràng, ưu tiên DD/MM/YYYY (format Việt Nam)
              // Kiểm tra xem có hợp lệ không
              const ddmmyyyy = new Date(part3, part2 - 1, part1)
              if (!isNaN(ddmmyyyy.getTime())) {
                date = ddmmyyyy
              } else {
                // Thử MM/DD/YYYY
                date = new Date(part3, part1 - 1, part2)
              }
            }
          } else {
            date = new Date(excelDate)
          }
        }
        // Nếu là số (Excel serial date)
        else if (typeof excelDate === 'number') {
          // Kiểm tra giá trị hợp lệ (phải > 0 và < một ngày xa trong tương lai)
          if (excelDate <= 0 || excelDate > 100000) {
            return null
          }
          // Excel date serial number (days since 1900-01-01)
          date = new Date((excelDate - 25569) * 86400 * 1000)
        }

        if (date && !isNaN(date.getTime())) {
          const year = date.getFullYear()
          const month = String(date.getMonth() + 1).padStart(2, '0')
          const day = String(date.getDate()).padStart(2, '0')
          return `${year}-${month}-${day}`
        }

        return null
      } catch (e) {
        console.error('Error converting date:', e)
        return null
      }
    },
    // Format ngày từ yyyy-MM-dd sang DD/MM/YYYY để hiển thị
    formatDateForDisplay(dateString) {
      if (!dateString) return ''
      try {
        // Từ "2025-10-29" -> "29/10/2025"
        const parts = dateString.split('-')
        if (parts.length === 3) {
          return `${parts[2]}/${parts[1]}/${parts[0]}`
        }
        return dateString
      } catch (e) {
        return dateString
      }
    }, // Hàm format giá trị cell cho mặt sàn
    formatMatSanCellValue(value) {
      if (value === null || value === undefined || value === '') return ''

      // Format cho trường hiệu lực
      if (typeof value === 'su_dung' && (value === 1 || value === 0)) {
        return value === 1 ? 'Hiệu lực' : 'Không hiệu lực'
      }

      return value
    },
    async handleCheckData(checkDataInfo) {
      try {
        const result = await this.validateDataWithServer(checkDataInfo.data)
        if (result.hasErrors) {
          this.importConfig.checkDataResult = true // Có lỗi -> disable button "Ghi lại"
          this.$root.toastError('Dữ liệu có lỗi: ' + result.errorMessage)

          // emit cho modalImportExcel để hiển thị lỗi lên grid
          this.$refs.modalImportExcel.handleCheckDataResult({
            isValid: false,
            errors: result.errorMessage
          })
        } else {
          this.importConfig.checkDataResult = false // Không có lỗi -> enable button "Ghi lại"
          this.$root.toastSuccess('Dữ liệu hợp lệ!')
        }
      } catch (error) {
        this.importConfig.checkDataResult = true // Có lỗi -> disable button "Ghi lại"
        this.$root.toastError('Lỗi khi kiểm tra dữ liệu: ' + error.message)
      }
    },
    async validateDataWithServer(dataList) {
      try {
        const payload = dataList.map((item) => {
          let pIdToaNha =
            this.buildingList.find(
              (b) => (b.TEN_TOANHA || '').trim().toUpperCase() === (item.toa_nha || '').trim().toUpperCase()
            )?.ID_TOANHA || 0

          // Map tên trạng thái sang ID để validate
          const trangThaiText = (item.trangthai_vh_text || '').trim().toUpperCase()
          const trangThaiMap = {
            'HOẠT ĐỘNG BÌNH THƯỜNG': 10,
            'HOAT DONG BINH THUONG': 10,
            'BẢO TRÌ': 11,
            'BAO TRI': 11,
            'CẢNH BÁO': 12,
            'CANH BAO': 12,
            'SỰ CỐ': 13,
            'SU CO': 13,
            'KHÔI PHỤC': 14,
            'KHOI PHUC': 14,
            'CHƯA SỬ DỤNG': 15,
            'CHUA SU DUNG': 15
          }
          const trangThaiId = trangThaiMap[trangThaiText] || item.trangthai_vh

          return {
            ...item,
            toanha_id: pIdToaNha,
            su_dung: item.su_dung || 0,
            ngay_sd: item.ngay_sd || null,
            trangthai_vh: trangThaiId || null,
            vi_tri: item.vi_tri || null
          }
        })

        // Kiểm tra lỗi trước khi gọi API
        const errors = []
        payload.forEach((item, idx) => {
          if (!item.toanha_id) {
            errors.push(`Dòng ${idx + 2}: Tòa nhà không tồn tại`)
          }
          if (!item.trangthai_vh) {
            errors.push(`Dòng ${idx + 2}: Trạng thái vận hành không hợp lệ`)
          }
        })

        if (errors.length > 0) {
          return {
            hasErrors: true,
            errorMessage: errors.join('; ')
          }
        }

        // Gọi API backend
        const response = await api.validateListMatSan(this.$root.context, payload)
        // const response = await this.$root.context.post('/web-ecms/danh-muc-mat-san/validate-list-mat-san', payload)

        const resData = response || {}
        return {
          hasErrors: resData.error !== '200',
          errorMessage: resData.message_detail || resData.message || ''
        }
      } catch (err) {
        return {
          hasErrors: true,
          errorMessage: err.response.data.message + '...'
        }
      }
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
          // Chuẩn bị dữ liệu gửi API
          const importItem = {
            toanha_id: item.toanha_id,
            ten: (item.ten || '').trim(),
            dien_tich: item.dien_tich,
            tam_san_rong: item.tam_san_rong || null,
            tam_san_dai: item.tam_san_dai || null,
            su_dung: item.su_dung || 0,
            ngay_sd: item.ngay_sd || null,
            trangthai_vh: item.trangthai_vh || null,
            vi_tri: (item.vi_tri || '').trim() || null,
            ghi_chu: item.ghi_chu || null,
            mo_ta: item.mo_ta || null
          }
          validItems.push(importItem)
        }
        // Gọi API lưu
        await this.addItems(validItems)

        // Reload data
        const rawList = await this.getList()
        this.listMatSan = this.mapListMatSan(rawList)
      } finally {
        this.$root.showLoading(false)
      }
    },
    async addItems(items) {
      let successCount = 0
      let errorCount = 0

      for (let i = 0; i < items.length; i++) {
        const item = items[i]
        const rs = await this.addItem(item)
        if (rs.error == '200') {
          successCount++
        } else {
          errorCount++
        }
      }
      if (successCount > 0) this.$root.toastSuccess(`Lưu thành công ${successCount} mặt sàn`)
      if (errorCount > 0) this.$root.toastError(`Bỏ qua ${errorCount} mặt sàn`)
    },
    async checkTenMatSanBlur() {
      const newVal = this.currentItem.ten?.trim()
      if (!newVal) {
        this.tenMatSanExists = false
        return
      }
      if (this.currentItem.id > 0 && newVal === this.originalName) {
        this.tenMatSanExists = false
        return
      }
      try {
        // const rs = await axios.get('http://localhost:10990/danh-muc-mat-san/check-ten-mat-san', {
        //   params: { ten: newVal }
        // })
        const rs = await this.$root.context.get(
          `/web-ecms/danh-muc-mat-san/check-ten-mat-san?ten=${encodeURIComponent(newVal)}`
        )
        this.tenMatSanExists = rs.data
      } catch (e) {
        this.tenMatSanExists = false
        this.handleErrorResponse(e)
      }
    },
    // Helper methods để lấy tên hiển thị
    getIdcName() {
      if (!this.selectedIDC) return ''
      const idc = this.idcList.find((x) => x.id === this.selectedIDC)
      return idc ? idc.ten : ''
    },
    getToaNhaName() {
      if (!this.currentItem.toanha_id) return ''
      const building = this.buildingList.find((x) => x.ID_TOANHA === this.currentItem.toanha_id)
      return building ? building.TEN_TOANHA : ''
    },
    // Lấy tên file hiển thị (rút gọn nếu quá dài)
    getImageDisplayName() {
      if (!this.currentItem.hinhanh) {
        return 'Chưa có ảnh'
      }

      // Lấy tên file từ URL
      const url = this.currentItem.hinhanh
      const fileName = url
        .split('/')
        .pop()
        .split('?')[0] // Lấy phần cuối sau dấu / và loại bỏ query params

      // Nếu tên file quá dài (>30 ký tự), rút gọn
      const maxLength = 30
      if (fileName.length > maxLength) {
        // Tách tên và extension
        const parts = fileName.split('.')
        const extension = parts.length > 1 ? '.' + parts.pop() : ''
        const nameWithoutExt = parts.join('.')

        // Tính toán độ dài cho phần đầu và cuối
        const extLength = extension.length
        const availableLength = maxLength - extLength - 3 // -3 cho dấu "..."
        const startLength = Math.ceil(availableLength / 2)
        const endLength = Math.floor(availableLength / 2)

        // Rút gọn: lấy đầu...cuối.ext
        return (
          nameWithoutExt.substring(0, startLength) +
          '...' +
          nameWithoutExt.substring(nameWithoutExt.length - endLength) +
          extension
        )
      }

      return fileName
    },
    // Format date từ DB sang yyyy-MM-dd cho input type="date"
    formatDateForInput(dateString) {
      if (!dateString) return null
      try {
        // Từ "2025-10-29 11:21:58.0" -> "2025-10-29"
        const date = new Date(dateString)
        if (isNaN(date.getTime())) return null
        const year = date.getFullYear()
        const month = String(date.getMonth() + 1).padStart(2, '0')
        const day = String(date.getDate()).padStart(2, '0')
        return `${year}-${month}-${day}`
      } catch (e) {
        return null
      }
    },
    // Xử lý xem ảnh
    viewImage() {
      if (!this.currentItem.hinhanh) {
        this.$root.toastWarning('Chưa có hình ảnh để xem.')
        return
      }
      // Hiển thị modal xem ảnh
      this.$refs.imageViewModal.show()
    }
  }
}
</script>
