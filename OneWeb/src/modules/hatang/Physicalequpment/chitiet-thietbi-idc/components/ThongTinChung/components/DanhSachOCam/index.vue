<template src="./index.template.html"></template>
<script>
import DataGrid from '@/components/Controls/DataGrid/index.vue'

export default {
  name: 'DanhSachOCam',
  components: {
    DataGrid
  },
  props: {
    currentMode: {
      type: String,
      required: true
    },
    deviceId: {
      type: [Number, String],
      default: null
    },
    danhSachOCam: {
      type: Array,
      default: () => []
    },
    loaiOCamList: {
      type: Array,
      default: () => []
    },
    trangThaiOCamList: {
      type: Array,
      default: () => []
    },
    trangThaiKetNoiList: {
      type: Array,
      default: () => []
    },
    thietBiList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      columns: [
        { fieldName: 'stt', headerText: 'STT', width: 80, textAlign: 'Center' },
        { fieldName: 'nhanO', headerText: 'Nhãn ổ', width: 150, textAlign: 'Left' },
        { fieldName: 'loaiO', headerText: 'Loại ổ', width: 150, textAlign: 'Left' },
        { fieldName: 'trangThaiOCam', headerText: 'Trạng thái ổ cắm', width: 180, textAlign: 'Left' },
        { fieldName: 'trangThaiKetNoi', headerText: 'Trạng thái kết nối', width: 180, textAlign: 'Left' },
        { fieldName: 'thietBiKetNoi', headerText: 'Thiết bị kết nối', width: 200, textAlign: 'Left' }
      ],
      commands: [
        { name: 'SUA', cssClass: 'btn-primary fa fa-pencil-square-o', width: 20, disable: this.disableActions },
        { name: 'XOA', cssClass: 'btn-danger fa fa-trash-o', width: 20, disable: this.disableActions }
        // { name: 'CHI_TIET', buttonOption: { content: 'chi tiết kết nối', cssClass: 'btn-primary', disable: this.disableActions } }
      ],
      showModal: false,
      modalTitle: '',
      isEditMode: false,
      currentItem: {
        TEN: '',
        LOAIOCAM_ID: null,
        TRANGTHAI_PORT: null,
        TRANGTHAIKN_ID: null,
        THIETBIKN_ID: null
      }
    }
  },
  computed: {
    dataList() {
      if (!this.danhSachOCam || !Array.isArray(this.danhSachOCam)) {
        return []
      }
      return this.danhSachOCam.map((item, index) => {
        // Convert string sang number cho các giá trị trạng thái
        const trangThaiOCamValue = parseInt(item.TRANGTHAI_PORT, 10)
        const trangThaiKetNoiValue = parseInt(item.TRANGTHAIKN_ID, 10)
        const loaiOCamId = parseInt(item.LOAIOCAM_ID, 10)
        const thietBiKnId = parseInt(item.THIETBIKN_ID, 10)

        // Map trạng thái ổ cắm: 0 = hỏng, 1 = hoạt động
        const trangThaiOCamText = trangThaiOCamValue === 1 ? 'Hoạt động' : trangThaiOCamValue === 0 ? 'Hỏng' : ''

        // Map trạng thái kết nối: 0 = chưa kết nối, 1 = đã kết nối
        const trangThaiKetNoiText =
          trangThaiKetNoiValue === 1 ? 'Đã kết nối' : trangThaiKetNoiValue === 0 ? 'Chưa kết nối' : ''

        // Tìm tên loại ổ cắm từ danh mục
        const loaiO = this.loaiOCamList.find((l) => (l.id || l.ID) === loaiOCamId)
        const loaiOText = loaiO ? loaiO.ten || loaiO.TEN || '' : ''

        // Tìm tên thiết bị kết nối từ danh mục
        const thietBi = this.thietBiList.find((t) => (t.id || t.ID) === thietBiKnId)
        const thietBiText = thietBi ? thietBi.ten || thietBi.TEN || thietBi.THIETBI_TEN || thietBi.tenThietBi || '' : ''

        return {
          id: item.tempId || item.RPDUOCAM_ID || item.ID || index + 1,
          stt: index + 1,
          nhanO: item.TEN || item.ten || '', // Nhãn ổ là tên ổ cắm
          loaiO: loaiOText, // Tên loại ổ cắm từ danh mục
          trangThaiOCam: trangThaiOCamText,
          trangThaiKetNoi: trangThaiKetNoiText,
          thietBiKetNoi: thietBiText, // Tên thiết bị kết nối từ danh mục
          originalData: item // Giữ nguyên toàn bộ dữ liệu gốc bao gồm tempId
        }
      })
    },
    disableActions() {
      return this.currentMode === 'VIEW'
    },
    trangThaiOCamOptions() {
      return [
        { text: 'Hỏng', value: 0 },
        { text: 'Hoạt động', value: 1 }
      ]
    },
    trangThaiKetNoiOptions() {
      return [
        { text: 'Chưa kết nối', value: 0 },
        { text: 'Đã kết nối', value: 1 }
      ]
    }
  },
  methods: {
    async grid_onCommandClicked(name, dataItem) {
      if (this.currentMode === 'VIEW') return

      if (name === 'SUA') {
        this.openEditModal(dataItem.originalData)
      } else if (name === 'XOA') {
        try {
          const confirmed = await this.$bvModal.msgBoxConfirm(
            'Bạn có chắc chắn muốn xóa bản ghi này?',
            {
              title: 'Xác nhận xóa',
              size: 'sm',
              buttonSize: 'sm',
              okVariant: 'danger',
              okTitle: 'Xóa',
              cancelTitle: 'Hủy',
              hideHeaderClose: false,
              centered: true
            }
          )
          if (confirmed) {
            this.$emit('delete-item', dataItem.originalData)
          }
        } catch (error) {
          // User cancelled hoặc có lỗi
        }
      } else if (name === 'CHI_TIET') {
        this.$emit('view-details', dataItem)
      }
    },
    onAdd() {
      if (this.currentMode === 'VIEW') return
      this.openAddModal()
    },
    async openAddModal() {
      this.showModal = true
      await this.$nextTick()
      this.isEditMode = false
      this.modalTitle = 'Thêm mới ổ cắm'
      this.currentItem = {
        TEN: '',
        LOAIOCAM_ID: null,
        TRANGTHAI_PORT: null,
        TRANGTHAIKN_ID: null,
        THIETBIKN_ID: null
      }
    },
    async openEditModal(item) {
      this.showModal = true
      await this.$nextTick()
      this.isEditMode = true
      this.modalTitle = 'Sửa thông tin ổ cắm'
      const originalData = item.originalData || item
      this.currentItem = {
        ...originalData,
        TEN: originalData.TEN || item.TEN || '',
        LOAIOCAM_ID: originalData.LOAIOCAM_ID || item.LOAIOCAM_ID || null,
        TRANGTHAI_PORT: originalData.TRANGTHAI_PORT !== undefined ? originalData.TRANGTHAI_PORT : (item.TRANGTHAI_PORT !== undefined ? item.TRANGTHAI_PORT : null),
        TRANGTHAIKN_ID: originalData.TRANGTHAIKN_ID !== undefined ? originalData.TRANGTHAIKN_ID : (item.TRANGTHAIKN_ID !== undefined ? item.TRANGTHAIKN_ID : null),
        THIETBIKN_ID: originalData.THIETBIKN_ID || item.THIETBIKN_ID || null,
        // Giữ lại tempId và RPDUOCAM_ID để tìm đúng item khi update
        tempId: originalData.tempId,
        RPDUOCAM_ID: originalData.RPDUOCAM_ID
      }
    },
    closeModal() {
      this.showModal = false
      this.currentItem = {
        TEN: '',
        LOAIOCAM_ID: null,
        TRANGTHAI_PORT: null,
        TRANGTHAIKN_ID: null,
        THIETBIKN_ID: null
      }
    },
    saveItem() {
      if (!this.currentItem.TEN || this.currentItem.TEN.trim() === '') {
        this.$root.toastError('Vui lòng nhập tên ổ cắm')
        return
      }

      if (this.isEditMode) {
        this.$emit('update-item', this.currentItem)
      } else {
        this.$emit('add-item', this.currentItem)
      }
      this.closeModal()
    },
    refreshData() {
      if (this.$refs.grdItems) {
        this.$refs.grdItems.refresh()
      }
    }
  }
}
</script>
<style scoped>
.danh-sach-o-cam {
  margin-top: 20px;
}

.header-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.title {
  font-weight: 600;
  font-size: 16px;
}

.header-actions {
  display: flex;
  gap: 10px;
  align-items: center;
}

.btn-add-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 22px;
  height: 22px;
  border-radius: 50%;
  background-color: #007bff;
  color: white;
  text-decoration: none;
  cursor: pointer;
  transition: background-color 0.2s;
}

.btn-add-icon:hover:not(.disabled) {
  background-color: #0056b3;
  color: white;
  text-decoration: none;
}

.btn-add-icon.disabled {
  background-color: #ccc;
  cursor: not-allowed;
  opacity: 0.6;
}

.info-row {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.info-row .key {
  min-width: 150px;
  padding-right: 10px;
  font-weight: 500;
}

.info-row .value {
  flex: 1;
}

.w150 {
  min-width: 150px;
}
</style>
