<template src="./index.template.html"></template>
<script>
import DataGrid from '@/components/Controls/DataGrid/index.vue'

export default {
  name: 'DanhSachSoLuongOCam',
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
    }
  },
  data() {
    return {
      columns: [
        { fieldName: 'stt', headerText: 'STT', width: 80, textAlign: 'Center' },
        { fieldName: 'maLoaiO', headerText: 'Mã loại ổ', width: 150, textAlign: 'Left' },
        { fieldName: 'tenLoaiO', headerText: 'Tên loại ổ', width: 200, textAlign: 'Left' },
        { fieldName: 'soLuong', headerText: 'Số lượng', width: 120, textAlign: 'Center' }
      ],
      commands: [
        { name: 'SUA', cssClass: 'btn-primary fa fa-pencil-square-o', width: 20 },
        { name: 'XOA', cssClass: 'btn-danger fa fa-trash-o', width: 20 }
      ],
      showModal: false,
      isEditMode: false,
      originalSoLuong: 0, // Lưu số lượng ban đầu khi edit
      modalItem: {
        loaiOCamId: null,
        tenOCam: '',
        soLuong: 1
      }
    }
  },
  computed: {
    dataList() {
      if (!this.danhSachOCam || !Array.isArray(this.danhSachOCam)) {
        return []
      }
      
      // Group by loaiOCamId và TEN (tên ổ cắm) để đếm số lượng
      const grouped = {}
      
      this.danhSachOCam.forEach(item => {
        const loaiOCamId = item.LOAIOCAM_ID || item.loaiOCamId
        const tenOCam = item.TEN || item.ten || ''
        // Tạo key duy nhất từ loại ổ cắm và tên ổ cắm
        const groupKey = `${loaiOCamId}_${tenOCam}`
        
        if (!grouped[groupKey]) {
          grouped[groupKey] = {
            loaiOCamId: loaiOCamId,
            tenOCam: tenOCam,
            count: 0
          }
        }
        grouped[groupKey].count++
      })
      
      // Chuyển đổi thành mảng và map với danh mục
      return Object.values(grouped).map((item, index) => {
        const loaiO = this.loaiOCamList.find(l => (l.id || l.ID) === item.loaiOCamId)
        
        return {
          id: `${item.loaiOCamId}_${item.tenOCam}`, // Key duy nhất
          stt: index + 1,
          maLoaiO: loaiO?.ten || '', // Tên loại ổ cắm từ danh mục
          tenLoaiO: item.tenOCam || '', // Tên ổ cắm được sinh ra
          soLuong: item.count,
          loaiOCamId: item.loaiOCamId,
          tenOCam: item.tenOCam
        }
      })
    },
    disableActions() {
      return this.currentMode === 'VIEW'
    }
  },
  methods: {
    onAdd() {
      if (this.currentMode === 'VIEW') return
      this.openAddModal()
    },
    async openAddModal() {
      this.isEditMode = false
      this.originalSoLuong = 0
      this.showModal = true
      await this.$nextTick()
      this.modalItem = {
        loaiOCamId: null,
        tenOCam: '',
        soLuong: 1
      }
    },
    async openEditModal(dataItem) {
      this.isEditMode = true
      this.originalSoLuong = dataItem.soLuong || 0
      this.showModal = true
      await this.$nextTick()
      this.modalItem = {
        loaiOCamId: dataItem.loaiOCamId || null,
        tenOCam: dataItem.tenLoaiO || dataItem.tenOCam || '',
        soLuong: dataItem.soLuong || 1
      }
    },
    closeModal() {
      this.showModal = false
      this.isEditMode = false
      this.originalSoLuong = 0
      this.modalItem = {
        loaiOCamId: null,
        tenOCam: '',
        soLuong: 1
      }
    },
    saveModal() {
      // Validate
      if (!this.modalItem.loaiOCamId) {
        this.$root.toastError('Vui lòng chọn loại ổ cắm')
        return
      }
      if (!this.modalItem.tenOCam || this.modalItem.tenOCam.trim() === '') {
        this.$root.toastError('Vui lòng nhập tên ổ cắm')
        return
      }
      if (!this.modalItem.soLuong || this.modalItem.soLuong < 1) {
        this.$root.toastError('Vui lòng nhập số lượng lớn hơn 0')
        return
      }

      // Validate số lượng khi edit: không được nhỏ hơn số lượng ban đầu
      if (this.isEditMode && this.modalItem.soLuong < this.originalSoLuong) {
        this.$root.toastError(`Số lượng không được nhỏ hơn ${this.originalSoLuong}`)
        return
      }

      if (this.isEditMode) {
        // Emit event edit
        this.$emit('edit', {
          loaiOCamId: this.modalItem.loaiOCamId,
          tenOCam: this.modalItem.tenOCam.trim(),
          soLuong: parseInt(this.modalItem.soLuong),
          originalSoLuong: this.originalSoLuong
        })
      } else {
        // Emit event với thông tin để component cha tạo nhiều ổ cắm
        this.$emit('add-new', {
          loaiOCamId: this.modalItem.loaiOCamId,
          tenOCam: this.modalItem.tenOCam.trim(),
          soLuong: parseInt(this.modalItem.soLuong)
        })
      }
      this.closeModal()
    },
    async grid_onCommandClicked(name, dataItem) {
      if (this.currentMode === 'VIEW') return
      
      if (name === 'SUA') {
        this.openEditModal(dataItem)
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
            this.$emit('delete', dataItem)
          }
        } catch (error) {
          // User cancelled hoặc có lỗi
        }
      }
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
.danh-sach-so-luong-o-cam {
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

.btn-add-icon .icon {
  font-size: 18px;
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

