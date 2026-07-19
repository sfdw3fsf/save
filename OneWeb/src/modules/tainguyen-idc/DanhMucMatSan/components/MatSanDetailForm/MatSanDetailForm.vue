<template src="./MatSanDetailForm.template.html"></template>
<script>
import api from '../../api.js'
import ThuocTinhBoSung from '../ThuocTinhBoSung'

const defaultItem = {
  id: 0,
  toanha_id: 0,
  ten: '',
  dien_tich: 0,
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
  name: 'MatSanDetailForm',
  props: {
    itemId: {
      type: Number,
      default: 0
    },
    parentId: {
      type: Number
    },
    mode: {
      type: String,
      default: 'VIEW', // 'ADD', 'EDIT', 'VIEW'
      validator: (value) => ['ADD', 'EDIT', 'VIEW'].includes(value)
    }
  },
  data() {
    return {
      currentItem: { ...defaultItem },
      originalName: '',
      tenMatSanExists: false,
      isLoading: false,
      buildingList: [],
      donviIdcList: [],
      idcList: [],
      filteredIdcList: [],
      filteredBuildings: [],
      selectedDonviIDC: null,
      selectedIDC: null,
      showImageModal: false,
      trangThaiVhList: []
    }
  },
  components: {
    ThuocTinhBoSung
  },
  computed: {
    isReadOnly() {
      return this.mode === 'VIEW'
    },
    isEditMode() {
      return this.mode === 'EDIT' || this.mode === 'ADD'
    }
  },
  async mounted() {
    await this.init()
  },
  methods: {
    async init() {
      // Load trạng thái vận hành
      this.trangThaiVhList = [
        { id: 10, ten: 'Hoạt động bình thường' },
        { id: 11, ten: 'Bảo trì' },
        { id: 12, ten: 'Cảnh báo' },
        { id: 13, ten: 'Sự cố' },
        { id: 14, ten: 'Khôi phục' },
        { id: 15, ten: 'Chưa sử dụng' }
      ]

      // Load building list
      await this.loadBuildingList()

      if (this.itemId > 0 && this.mode !== 'ADD') {
        await this.loadItemDetail()
      } else {
        // Reset data cho mode ADD
        this.currentItem = { ...defaultItem }
        this.originalName = ''
        this.selectedDonviIDC = null
        this.selectedIDC = null
        this.filteredIdcList = []
        this.filteredBuildings = []

        // Reset component Thuộc tính bổ sung
        if (this.$refs.thuocTinhBoSung) {
          this.$refs.thuocTinhBoSung.reset()
        }

        // Nếu có parentId (ID toà nhà), set giá trị cho toà nhà
        if (this.parentId) {
          await this.setParentBuilding(this.parentId)
        }
      }
    },
    async loadBuildingList() {
      try {
        const rs = await api.getIdcToaNha(this.$root.context)
        if (rs && rs.data) {
          this.buildingList = rs.data || []

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
        console.error('Lỗi khi load danh sách tòa nhà:', error)
        this.$root.toastError('Lỗi khi tải danh sách tòa nhà')
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
    async setParentBuilding(toaNhaId) {
      // Tìm toà nhà trong buildingList
      const building = this.buildingList.find((x) => x.ID_TOANHA == toaNhaId)
      if (building) {
        // Set Đơn vị IDC
        this.selectedDonviIDC = building.ID_DONVI
        // Filter IDC theo đơn vị
        this.filteredIdcList = this.idcList.filter((x) => x.donvi_id == building.ID_DONVI)

        // Set IDC
        this.selectedIDC = building.ID_IDC
        // Filter toà nhà theo IDC
        this.filteredBuildings = this.buildingList.filter((x) => x.ID_IDC == building.ID_IDC && x.HIEULUC_TOANHA == 1)

        // Set toà nhà
        this.currentItem.toanha_id = toaNhaId
      }
    },
    async loadItemDetail() {
      try {
        this.isLoading = true
        const response = await api.getMatSanById(this.$root.context, this.itemId)
        if (response && response.data) {
          await this.setCurrentItem(response.data)
        }
      } catch (error) {
        this.$root.toastError('Lỗi khi tải chi tiết mặt sàn')
      } finally {
        this.isLoading = false
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

      this.originalName = this.currentItem.ten

      // Set selected values khi load data
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

      // Lấy presigned URL nếu có hình ảnh
      if (this.currentItem.hinhanh) {
        try {
          const rs = await this.getPresignedUrl(this.currentItem.hinhanh)
          if (rs && rs.data) {
            this.currentItem.imageUrl = rs.data
          }
        } catch (error) {
          console.error('Lỗi khi lấy URL ảnh:', error)
        }
      }
    },
    formatDateForInput(date) {
      if (!date) return null
      try {
        const d = new Date(date)
        if (isNaN(d.getTime())) return null
        const year = d.getFullYear()
        const month = String(d.getMonth() + 1).padStart(2, '0')
        const day = String(d.getDate()).padStart(2, '0')
        return `${year}-${month}-${day}`
      } catch (e) {
        return null
      }
    },
    async getPresignedUrl(relativeUrl) {
      try {
        this.$root.showLoading(true)
        const response = await this.$root.context.post('/web-ecms/storage/getPresignedUrl', {
          fileSource: relativeUrl
        })

        if (response && response.error === '200' && response.data && response.data.result) {
          this.currentItem.imageUrl = response.data.result
        } else {
          console.error('Failed to get presigned URL:', response)
        }
      } catch (error) {
        console.error('Error getting presigned URL:', error)
      } finally {
        this.$root.showLoading(false)
      }
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
        const rs = await api.checkTenMatSan(this.$root.context, newVal, this.currentItem.id)
        this.tenMatSanExists = rs?.data?.exists || false
      } catch (error) {
        this.tenMatSanExists = false
      }
    },
    getSuDungText(value) {
      return value === 1 ? 'Hiệu lực' : 'Không hiệu lực'
    },
    getTrangThaiVhText(id) {
      const item = this.trangThaiVhList.find((x) => x.id === id)
      return item ? item.ten : ''
    },
    getImageDisplayName() {
      if (!this.currentItem.hinhanh) return 'Chưa có ảnh'
      const fileName = this.currentItem.hinhanh.split('/').pop()
      return fileName.length > 30 ? fileName.substring(0, 27) + '...' : fileName
    },
    viewImage() {
      if (!this.currentItem.hinhanh) {
        this.$root.toastError('Không có ảnh để xem')
        return
      }
      this.showImageModal = true
    },
    uploadImage() {
      this.$refs.fileInput.click()
    },
    validateForm() {
      // 1. Kiểm tra tên mặt sàn
      if (!this.currentItem.ten?.trim()) {
        this.$root.toastError('Vui lòng nhập tên mặt sàn')
        return false
      }

      // 2. Kiểm tra tên mặt sàn đã tồn tại
      if (this.tenMatSanExists) {
        this.$root.toastError('Tên mặt sàn đã tồn tại')
        return false
      }

      // 3. Kiểm tra toà nhà
      if (!this.currentItem.toanha_id || this.currentItem.toanha_id === 0) {
        this.$root.toastError('Vui lòng chọn tòa nhà')
        return false
      }

      // 4. Kiểm tra trạng thái vận hành
      if (!this.currentItem.trangthai_vh) {
        this.$root.toastError('Vui lòng chọn trạng thái vận hành')
        return false
      }

      // 5. Kiểm tra kích thước tấm sàn
      if (
        this.currentItem.tam_san_dai !== null &&
        this.currentItem.tam_san_dai !== undefined &&
        this.currentItem.tam_san_dai !== ''
      ) {
        const tamSanDai = parseFloat(this.currentItem.tam_san_dai)
        if (isNaN(tamSanDai) || tamSanDai <= 0) {
          this.$root.toastError('Tấm sàn dài phải là số lớn hơn 0')
          return false
        }
      }

      if (
        this.currentItem.tam_san_rong !== null &&
        this.currentItem.tam_san_rong !== undefined &&
        this.currentItem.tam_san_rong !== ''
      ) {
        const tamSanRong = parseFloat(this.currentItem.tam_san_rong)
        if (isNaN(tamSanRong) || tamSanRong <= 0) {
          this.$root.toastError('Tấm sàn rộng phải là số lớn hơn 0')
          return false
        }
      }

      // 6. Kiểm tra diện tích
      if (!this.currentItem.dien_tich || this.currentItem.dien_tich <= 0) {
        this.$root.toastError('Diện tích phải lớn hơn 0')
        return false
      }

      // 7. Kiểm tra tổng diện tích zone (nếu có)
      if (
        this.currentItem.tong_dien_tich_zone !== null &&
        this.currentItem.tong_dien_tich_zone !== undefined &&
        this.currentItem.tong_dien_tich_zone !== ''
      ) {
        const tongDienTichZone = parseFloat(this.currentItem.tong_dien_tich_zone)
        if (isNaN(tongDienTichZone) || tongDienTichZone < 0) {
          this.$root.toastError('Tổng diện tích zone phải là số không âm')
          return false
        }
        // Kiểm tra tổng DT zone không được lớn hơn diện tích mặt sàn
        // if (tongDienTichZone > this.currentItem.dien_tich) {
        //   this.$root.toastError('Diện tích mặt sàn phải lớn hơn hoặc bằng tổng diện tích zone')
        //   return false
        // }
      }

      // 8. Kiểm tra ngày sử dụng (nếu có)
      if (this.currentItem.ngay_sd) {
        const ngaySd = new Date(this.currentItem.ngay_sd)
        if (isNaN(ngaySd.getTime())) {
          this.$root.toastError('Ngày sử dụng không hợp lệ')
          return false
        }
      }

      return true
    },
    async handleSave() {
      if (!this.validateForm()) return

      try {
        this.$root.showLoading(true)
        const payload = {
          id: this.mode === 'ADD' ? 0 : this.currentItem.id,
          ten: this.currentItem.ten?.trim(),
          toanha_id: this.currentItem.toanha_id,
          dien_tich: this.currentItem.dien_tich,
          ghi_chu: this.currentItem.ghi_chu?.trim(),
          mo_ta: this.currentItem.mo_ta?.trim(),
          su_dung: this.currentItem.su_dung,
          hinh_anh: this.currentItem.hinhanh || null,
          ngay_sd: this.currentItem.ngay_sd || null,
          trangthai_vh: this.currentItem.trangthai_vh || null,
          vi_tri: this.currentItem.vi_tri?.trim() || null,
          tam_san_rong: this.currentItem.tam_san_rong || null,
          tam_san_dai: this.currentItem.tam_san_dai || null
        }

        const rs = await api.insertUpdateMatSan(this.$root.context, payload)
        if (rs && rs.error === '200') {
          const matSanId = rs.data

          // Nếu là ADD mode, lưu thuộc tính tạm vào DB
          if (this.mode === 'ADD' && this.$refs.thuocTinhBoSung) {
            await this.$refs.thuocTinhBoSung.saveTempData(matSanId)
          }

          this.$root.toastSuccess(this.mode === 'ADD' ? 'Thêm mới thành công' : 'Cập nhật thành công')
          this.$emit('saved', { id: matSanId })
          this.$emit('saveSuccessed', true)
        } else {
          this.$root.toastError(rs?.message || 'Có lỗi xảy ra')
        }
      } catch (error) {
        this.$root.toastError(error?.response?.data?.message || 'Lỗi khi lưu dữ liệu')
      } finally {
        this.$root.showLoading(false)
      }
    },
    async handleFileUpload(event) {
      const file = event.target.files[0]
      if (!file) return

      if (!file.type.startsWith('image/')) {
        this.$root.toastError('Vui lòng chọn file hình ảnh.')
        return
      }

      try {
        this.$root.showLoading(true)
        const formData = new FormData()
        formData.append('file', file)

        const response = await this.$root.context.post('/web-ecms/storage/uploadFile', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })

        if (response && response.error === '200' && response.data && response.data.message) {
          const relativeUrl = response.data.message.relativeUrl
          const presignedUrl = response.data.message.presignedUrl
          if (relativeUrl) {
            this.currentItem.hinhanh = relativeUrl
            this.currentItem.imageUrl = presignedUrl
            this.$root.toastSuccess('Tải ảnh lên thành công.')
          } else {
            this.$root.toastError('Không nhận được URL ảnh.')
          }
        } else {
          this.$root.toastError('Tải ảnh lên thất bại.')
        }
      } catch (error) {
        this.$root.toastError('Có lỗi khi tải ảnh lên: ' + (error.message || ''))
      } finally {
        this.$root.showLoading(false)
        event.target.value = ''
      }
    }
  }
}
</script>
