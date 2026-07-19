<template src="./ThuocTinhBoSung.html"></template>

<script>
import api from '../../api.js'

export default {
  name: 'ThuocTinhBoSung',
  props: {
    matSanId: {
      type: Number,
      default: null
    },
    mode: {
      type: String,
      default: 'VIEW', // 'ADD', 'EDIT', 'VIEW'
      validator: (value) => ['ADD', 'EDIT', 'VIEW'].includes(value)
    }
  },
  data() {
    return {
      thuocTinhBoSung: [],
      tempThuocTinhBoSung: [], // Dùng cho mode ADD
      showModal: false, // Thay thế ejs-dialog bằng b-modal
      modalMode: 'ADD', // 'ADD' hoặc 'EDIT'
      currentAttribute: {
        ten: '',
        giaTri: ''
      },
      editingIndex: -1,
      editingThuocTinhId: null
    }
  },
  computed: {
    isReadOnly() {
      return this.mode === 'VIEW'
    },
    dataSource() {
      return this.mode === 'ADD' ? this.tempThuocTinhBoSung : this.thuocTinhBoSung
    },
    thuocTinhColumns() {
      const columns = [
        { fieldName: 'TEN', headerText: 'Thuộc tính', allowFiltering: true, allowSorting: true, width: 200 },
        { fieldName: 'GIATRI', headerText: 'Giá trị', allowFiltering: true, allowSorting: true, width: 200 }
      ]

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
    }
  },
  watch: {
    matSanId: {
      immediate: true,
      handler(newVal) {
        if (newVal && this.mode !== 'ADD') {
          this.loadData()
        }
      }
    },
    mode: {
      handler(newMode) {
        if (newMode === 'ADD') {
          this.tempThuocTinhBoSung = []
        } else if (this.matSanId) {
          this.loadData()
        }
      }
    }
  },
  methods: {
    async loadData() {
      if (!this.matSanId) return

      try {
        const response = await api.getThuocTinhMatSan(this.$root.context, this.matSanId)
        if (response.data) {
          this.thuocTinhBoSung = response.data || []
        }
      } catch (error) {
        console.error('Error fetching thuoc tinh:', error)
        this.thuocTinhBoSung = []
      }
    },

    openAddModal() {
      this.modalMode = 'ADD'
      this.currentAttribute = {
        ten: '',
        giaTri: ''
      }
      this.editingIndex = -1
      this.editingThuocTinhId = null
      this.showModal = true

      // Focus vào input sau khi modal hiển thị
      this.$nextTick(() => {
        setTimeout(() => {
          if (this.$refs.tenThuocTinhInput) {
            this.$refs.tenThuocTinhInput.focus()
          }
        }, 300)
      })
    },

    openEditModal(attribute) {
      this.modalMode = 'EDIT'
      this.currentAttribute = {
        ten: attribute.TEN || '',
        giaTri: attribute.GIATRI || ''
      }

      if (this.mode === 'ADD') {
        this.editingIndex = this.tempThuocTinhBoSung.findIndex((x) => x.THUTU === attribute.THUTU)
      } else {
        this.editingThuocTinhId = attribute.THUOCTINH_ID
      }

      this.showModal = true

      // Focus vào input sau khi modal hiển thị
      this.$nextTick(() => {
        setTimeout(() => {
          if (this.$refs.tenThuocTinhInput) {
            this.$refs.tenThuocTinhInput.focus()
          }
        }, 300)
      })
    },

    closeModal() {
      this.showModal = false
      this.currentAttribute = {
        ten: '',
        giaTri: ''
      }
      this.editingIndex = -1
      this.editingThuocTinhId = null
    },

    validateForm() {
      const ten = this.currentAttribute.ten?.trim()
      const giaTri = this.currentAttribute.giaTri?.trim()

      if (!ten && !giaTri) {
        this.$root.toastError('Vui lòng nhập tên thuộc tính và giá trị')
        return false
      }
      if (!ten) {
        this.$root.toastError('Vui lòng nhập tên thuộc tính')
        return false
      }
      if (!giaTri) {
        this.$root.toastError('Vui lòng nhập giá trị')
        return false
      }

      return true
    },

    async handleSave() {
      if (!this.validateForm()) return

      const ten = this.currentAttribute.ten.trim()
      const giaTri = this.currentAttribute.giaTri.trim()

      if (this.modalMode === 'ADD') {
        await this.addAttribute(ten, giaTri)
      } else {
        await this.updateAttribute(ten, giaTri)
      }
    },

    async addAttribute(ten, giaTri) {
      if (this.mode === 'ADD') {
        // Mode ADD: Thêm vào mảng tạm
        this.tempThuocTinhBoSung.push({
          TEN: ten,
          GIATRI: giaTri,
          THUTU: this.tempThuocTinhBoSung.length + 1
        })
        this.$root.toastSuccess('Thêm thuộc tính thành công')
        this.closeModal()
      } else {
        // Mode EDIT: Gọi API
        try {
          this.$root.showLoading(true)
          const data = {
            kieuTT: 'MATSAN',
            duLieuId: this.matSanId,
            ten: ten,
            giaTri: giaTri,
            thuTu: this.thuocTinhBoSung.length + 1
          }
          await api.addThuocTinhMatSan(this.$root.context, this.matSanId, data)
          this.$root.toastSuccess('Thêm thuộc tính thành công')
          await this.loadData()
          this.closeModal()
        } catch (error) {
          this.$root.toastError('Thêm thuộc tính thất bại: ' + (error.response?.data?.message || error.message))
        } finally {
          this.$root.showLoading(false)
        }
      }
    },

    async updateAttribute(ten, giaTri) {
      if (this.mode === 'ADD') {
        // Mode ADD: Cập nhật mảng tạm
        if (this.editingIndex !== -1) {
          this.tempThuocTinhBoSung[this.editingIndex].TEN = ten
          this.tempThuocTinhBoSung[this.editingIndex].GIATRI = giaTri
          this.$root.toastSuccess('Cập nhật thuộc tính thành công')
          this.closeModal()
        }
      } else {
        // Mode EDIT: Gọi API
        try {
          this.$root.showLoading(true)
          const attribute = this.thuocTinhBoSung.find((x) => x.THUOCTINH_ID === this.editingThuocTinhId)
          const data = {
            kieuTT: 'MATSAN',
            duLieuId: this.matSanId,
            ten: ten,
            giaTri: giaTri,
            thuTu: attribute?.THUTU || 0
          }
          await api.updateThuocTinhMatSan(this.$root.context, this.editingThuocTinhId, data)
          this.$root.toastSuccess('Cập nhật thuộc tính thành công')
          await this.loadData()
          this.closeModal()
        } catch (error) {
          this.$root.toastError('Cập nhật thuộc tính thất bại: ' + (error.response?.data?.message || error.message))
        } finally {
          this.$root.showLoading(false)
        }
      }
    },

    async deleteAttribute(attribute) {
      const confirmed = await this.$confirm(`Có chắc bạn muốn xóa thuộc tính "${attribute.TEN}"?`, 'Xác nhận xóa', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning'
      })
        .then(() => true)
        .catch(() => false)

      if (!confirmed) return

      if (this.mode === 'ADD') {
        // Mode ADD: Xóa khỏi mảng tạm
        const index = this.tempThuocTinhBoSung.findIndex((x) => x.THUTU === attribute.THUTU)
        if (index !== -1) {
          this.tempThuocTinhBoSung.splice(index, 1)
          // Cập nhật lại thứ tự
          this.tempThuocTinhBoSung.forEach((item, idx) => {
            item.THUTU = idx + 1
          })
          this.$root.toastSuccess('Xóa thuộc tính thành công')
        }
      } else {
        // Mode EDIT: Gọi API
        try {
          this.$root.showLoading(true)
          await api.deleteThuocTinhMatSan(this.$root.context, attribute.THUOCTINH_ID)
          this.$root.toastSuccess('Xóa thuộc tính thành công')
          await this.loadData()
        } catch (error) {
          this.$root.toastError('Xóa thuộc tính thất bại: ' + (error.response?.data?.message || error.message))
        } finally {
          this.$root.showLoading(false)
        }
      }
    },

    actionColumn() {
      const self = this
      return function() {
        return {
          template: {
            template: `
              <div class="action-buttons gap-1">
                <button class="btn btn-sm btn-outline-primary ml-1" @click="onEdit()">
                  <i class="fa fa-pencil"></i> Sửa
                </button>
                <button class="btn btn-sm btn-outline-danger ml-1" @click="onDelete()">
                  <i class="fa fa-trash"></i> Xoá
                </button>
              </div>
            `,
            methods: {
              onEdit() {
                self.openEditModal(this.data)
              },
              onDelete() {
                self.deleteAttribute(this.data)
              }
            }
          }
        }
      }
    },

    // Public method để lấy temp data (dùng cho mode ADD khi save)
    getTempData() {
      return this.tempThuocTinhBoSung
    },

    // Public method để save temp data vào DB
    async saveTempData(matSanId) {
      try {
        for (let i = 0; i < this.tempThuocTinhBoSung.length; i++) {
          const item = this.tempThuocTinhBoSung[i]
          const data = {
            kieuTT: 'MATSAN',
            duLieuId: matSanId,
            ten: item.TEN,
            giaTri: item.GIATRI,
            thuTu: i + 1
          }
          await api.addThuocTinhMatSan(this.$root.context, matSanId, data)
        }
      } catch (error) {
        console.error('Error saving temp thuoc tinh:', error)
        throw error
      }
    },

    // Public method để reset
    reset() {
      this.tempThuocTinhBoSung = []
      this.thuocTinhBoSung = []
    }
  }
}
</script>
