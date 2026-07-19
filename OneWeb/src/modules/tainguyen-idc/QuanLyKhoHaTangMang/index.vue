<template src="./index.template.html"></template>

<script>
import api from './api.js'
import HaTangMangDetail from './components/HaTangMangDetail.vue'
export default {
  name: 'QuanLyKhoHaTangMang',
  components: {
    HaTangMangDetail
  },
  data: function() {
    return {
      dialogMode: 'EDIT',
      searchConditions: {
        idcId: 0,
        ten: '',
        trangThaiCCDVId: 0,
        congNgheTrienKhai: 0
      },
      haTangMangList: [],
      idcList: [],
      trangThaiCungCapDichVuList: [],
      listHaTangMang: [],
      congNgheTrienKhaiList: [],
      selectedItemId: null,
      selectedItems: [], // Danh sách items được chọn từ checkbox
      animationSettings: { effect: 'Zoom' },
      dialogTarget: 'body'
    }
  },
  computed: {},
  watch: {},
  async mounted() {
    await this.loadDanhMuc()
    await this.loadData()
  },
  methods: {
    async loadData() {
      try {
        this.$root.loading(true)
        const response = await api.getAll(this.$root.context, this.searchConditions)
        this.haTangMangList = response.data || []
      } catch (error) {
        this.$root.toastError('Lỗi khi tải danh sách hạ tầng mạng')
      } finally {
        this.$root.loading(false)
      }
    },
    async onSearch() {
      try {
        this.$root.loading(true)
        const response = await api.getAll(this.$root.context, this.searchConditions)
        let data = response.data || []
        this.haTangMangList = data
      } catch (error) {
        this.$root.toastError('Lỗi khi tìm kiếm hạ tầng mạng')
      } finally {
        this.$root.loading(false)
      }
    },
    async loadDanhMuc() {
      try {
        this.$root.loading(true)
        // Lấy danh sách IDC
        const idcResponse = await api.getIDC(this.$root.context)
        this.idcList = [{ ID: 0, TEN: '-- Tất cả --' }, ...(idcResponse || [])]

        // Lấy danh sách trạng thái cung cấp dịch vụ
        const trangThaiCungCapDichVuResponse = await api.getTrangThaiCungCapDichVuList(this.$root.context)
        this.trangThaiCungCapDichVuList = [
          { ID: 0, TEN : '-- Tất cả --' },
          ...(trangThaiCungCapDichVuResponse || [])
        ]
      } catch (error) {
        this.$root.toastError('Lỗi khi tải danh mục')
      } finally {
        this.$root.loading(false)
      }
    },
    openNewPopup() {
      this.dialogMode = 'ADD'
      this.selectedItemId = null
      this.$nextTick(() => {
        if (this.$refs.haTangMangDialog) {
          this.$refs.haTangMangDialog.show()
        }
      })
    },
    openEditPopup(itemId) {
      this.dialogMode = 'EDIT'
      this.selectedItemId = itemId
      this.$nextTick(() => {
        if (this.$refs.haTangMangDialog) {
          this.$refs.haTangMangDialog.show()
        }
      })
    },
    onDialogBeforeOpen() {
      // Force reload data khi mở dialog
      if (this.selectedItemId) {
        // Reset rồi set lại để trigger watch
        const tempId = this.selectedItemId
        this.selectedItemId = null
        this.$nextTick(() => {
          this.selectedItemId = tempId
        })
      }
    },
    openViewPopup(itemId) {
      this.dialogMode = 'VIEW'
      this.selectedItemId = itemId
      this.$nextTick(() => {
        if (this.$refs.haTangMangDialog) {
          this.$refs.haTangMangDialog.show()
        }
      })
    },
    async deleteItem(itemId) {
      this.$confirm('Bạn có chắc chắn muốn xóa hạ tầng mạng này không?', 'Xác nhận thực hiện', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info'
      })
        .then(async (confirmed) => {
          if (confirmed) {
            try {
              this.$root.loading(true)
              const response = await api.delete(this.$root.context, itemId)
              this.$root.toastSuccess('Xóa hạ tầng mạng thành công')
              await this.loadData()
            } catch (error) {
              this.$root.toastError('Lỗi khi xóa hạ tầng mạng đang có subnet hoặc thiết bị mạng')
            } finally {
              this.$root.loading(false)
            }
          }
        })
        .catch(() => {
          // Hủy bỏ
        })
    },
    onSelectRowCheckbox(selectedItems) {
      this.selectedItems = selectedItems || []
    },
    async deleteMulti() {
      if (!this.selectedItems || this.selectedItems.length === 0) {
        this.$root.toastWarning('Vui lòng chọn ít nhất một hạ tầng mạng để xóa')
        return
      }

      this.$confirm(`Bạn có chắc chắn muốn xóa ${this.selectedItems.length} hạ tầng mạng đã chọn?`, 'Xác nhận xóa', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning'
      })
        .then(async () => {
          try {
            this.$root.loading(true)
            const response = await api.deleteMulti(this.$root.context, this.selectedItems)
            const result = response.data || {}
            const successCount = result.success || 0
            this.$root.toastSuccess(`Xóa thành công ${successCount} hạ tầng mạng`)
            this.selectedItems = []
            await this.loadData()
          } catch (error) {
            if (error.response && error.response.data) {
              const { error_code, message, data } = error.response.data
              const result = data || {}
              const successCount = result.success || 0
              const failedCount = result.failed || 0
              const errorIds = result.errorIds || ''

              if (error_code === 'BSS-PARTIAL_SUCCESS') {
                this.$root.toastSuccess(`Xóa thành công ${successCount} hạ tầng mạng`)
                this.$root.toastError(
                  `Không thể xóa ${failedCount} hạ tầng mạng do ràng buộc dữ liệu. ID lỗi: ${errorIds}`
                )
                this.selectedItems = []
                await this.loadData()
              } else {
                this.$root.toastError(message || 'Xoá thất bại các hạ tầng mạng đã chọn')
              }
            } else {
              this.$root.toastError('Lỗi kết nối hoặc lỗi không xác định')
            }
          } finally {
            this.$root.loading(false)
          }
        })
        .catch(() => {
          // Hủy bỏ
        })
    },
    closeDetailModal() {
      if (this.$refs.haTangMangDialog) {
        this.$refs.haTangMangDialog.hide()
      }
    },
    async onDetailSaved(result) {
      if (this.$refs.haTangMangDialog) {
        this.$refs.haTangMangDialog.hide()
      }
      await this.loadData() // reload grid
    },
    actionColumn(parent) {
      const self = this
      return function() {
        return {
          template: {
            template: `
                    <div class="action-buttons gap-1">
                        <button
                            class="btn btn-sm btn-outline-primary ml-1"
                            @click="onView()">
                            <i class="fa fa-eye"></i>
                        </button>
                        <button
                            class="btn btn-sm btn-outline-primary ml-1"
                            @click="onEdit()">
                            <i class="fa fa-pencil"></i>
                        </button>
                        <button
                            class="btn btn-sm btn-outline-danger ml-1"
                            @click="onDelete()">
                            <i class="fa fa-trash"></i>
                        </button>
                        </div>
                `,
            methods: {
              onEdit() {
                self.openEditPopup(this.data.id)
              },
              onView() {
                self.openViewPopup(this.data.id)
              },
              onDelete() {
                self.deleteItem(this.data.id)
              }
            }
          }
        }
      }
    }
  }
}
</script>
<style>
.search-filter-row .info-row .key {
  width: 150px;
  flex-shrink: 0;
}

.search-filter-row .info-row .value {
  flex: 1;
}

.search-filter-row .info-row input,
.search-filter-row .info-row .select2,
.search-filter-row .info-row .k-combobox {
  width: 100% !important;
}

.ha-tang-mang-detail-modal .modal-dialog {
  max-width: 95vw !important;
  width: 80vw !important;
  margin: 0 auto;
}

.ha-tang-mang-detail-modal .modal-content {
  height: auto;
}

.ha-tang-mang-detail-modal .modal-body {
  height: auto;
  overflow-y: auto;
  padding: 0 1rem;
}
</style>
