<template>
  <div>
    <div class="card-thuoctinh mb-2">
      <div class="legend-title d-flex justify-content-between align-items-center">
        <div class="pull-left">Thuộc tính bổ sung</div>
        <button class="btn btn-sm btn-outline-primary float-right" @click="onAddAttribute">
          <i class="fa fa-plus"></i> Thêm thuộc tính
        </button>
      </div>
      <div>
        <div class="card-body px-0  mt-2">
          <div class="table-content">
            <DataGrid
              v-bind:columns="[
                { fieldName: 'TEN', headerText: 'Thuộc tính', allowFiltering: true, allowSorting: true, width: 120 },
                { fieldName: 'GIATRI', headerText: 'Giá trị', allowFiltering: true, allowSorting: true, width: 120 },
                {
                  fieldName: 'ACTIONS',
                  headerText: 'Hành động',
                  textAlign: 'Center',
                  template: this.actionColumn(),
                  width: 150
                }
              ]"
              :dataSource="tempDsThuocTinh"
              :showColumnCheckbox="false"
              :enablePagingServer="false"
              :allowPaging="true"
              :enabledSelectFirstRow="true"
              :showFilter="true"
              ref="thuocTinhBoSung"
            />
          </div>
        </div>
        <hr class="my-1" />
      </div>
    </div>

    <b-modal
      ref="attributeDialog"
      v-model="modalState.visible"
      :title="modalHeaderText"
      size="md"
      hide-footer
      @hidden="handleDialogClose"
      @shown="handleDialogOpen"
    >
      <div class="row">
        <div class="col-12">
          <div class="box-form">
            <div class="row">
              <div class="info-row">
                <div class="col-4 key">Tên thuộc tính <span class="red">*</span></div>
                <div class="col-8 value">
                  <input
                    id="attrNameInput"
                    ref="attributeNameInput"
                    class="form-control"
                    v-model.trim="modalState.form.ten"
                    autocomplete="off"
                    placeholder="Nhập tên thuộc tính"
                  />
                </div>
              </div>
            </div>
            <div class="row">
              <div class="info-row">
                <div class="col-4 key">Giá trị <span class="red">*</span></div>
                <div class="col-8 value">
                  <input
                    id="attrValueInput"
                    class="form-control"
                    v-model.trim="modalState.form.giaTri"
                    autocomplete="off"
                    placeholder="Nhập giá trị"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-12 mt-3">
          <div class="d-flex justify-content-end gap-2">
            <button type="button" class="btn btn-secondary" @click="handleDialogCancel">Hủy</button>
            <button
              type="button"
              class="btn btn-primary"
              @click="handleModalConfirm"
              :disabled="!modalState.form.ten || !modalState.form.giaTri"
            >
              {{ confirmButtonText }}
            </button>
          </div>
        </div>
      </div>
    </b-modal>
  </div>
</template>

<script>
import api from './api'

export default {
  name: 'AttributeGrid',
  props: {
    idcId: {
      type: Number
    }
  },
  data() {
    return {
      dsThuocTinh: [],
      tempDsThuocTinh: [],
      modalState: {
        visible: false,
        mode: null,
        form: {
          ten: '',
          giaTri: ''
        },
        target: null
      }
    }
  },
  computed: {
    modalHeaderText() {
      switch (this.modalState.mode) {
        case 'add':
          return 'Thêm thuộc tính'
        case 'edit':
          return 'Cập nhật thuộc tính'
        default:
          return ''
      }
    },
    confirmButtonText() {
      switch (this.modalState.mode) {
        case 'add':
          return 'Thêm'
        case 'edit':
          return 'Cập nhật'
        default:
          return 'Đồng ý'
      }
    }
  },
  watch: {
    idcId: {
      immediate: true,
      handler(newVal) {
        if (newVal) {
          this.onLoadAttributes(newVal)
        } else {
          // Reset khi tạo mới
          this.dsThuocTinh = []
          this.tempDsThuocTinh = []
        }
      }
    }
  },
  methods: {
    openAttributeModal(mode, attribute = null) {
      if (mode === 'delete') {
        const attributeName = attribute?.TEN ? `"${attribute.TEN}"` : ''
        this.$confirm(`Bạn có chắc chắn muốn xóa thuộc tính ${attributeName}?`, 'Xác nhận xóa', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning'
        })
          .then(() => {
            this.handleDelete(attribute)
          })
          .catch(() => {})
        return
      }

      this.modalState.mode = mode
      this.modalState.visible = true
      this.modalState.target = attribute

      if (mode === 'add') {
        this.modalState.form = { ten: '', giaTri: '' }
      } else if (mode === 'edit') {
        this.modalState.form = {
          ten: attribute?.TEN || '',
          giaTri: attribute?.GIATRI || ''
        }
      }
    },
    closeAttributeModal() {
      this.modalState.visible = false
      this.modalState.mode = null
      this.modalState.target = null
      this.modalState.form = { ten: '', giaTri: '' }
    },
    async handleModalConfirm() {
      if (this.modalState.mode === 'edit') {
        await this.handleEdit()
      } else if (this.modalState.mode === 'add') {
        await this.handleAdd()
      }
    },
    handleDialogCancel() {
      if (this.$refs.attributeDialog) {
        this.$refs.attributeDialog.hide()
      } else {
        this.closeAttributeModal()
      }
    },
    handleDialogClose() {
      this.closeAttributeModal()
    },
    handleDialogOpen() {
      this.$nextTick(() => {
        const input = this.$refs.attributeNameInput
        if (input && typeof input.focus === 'function') {
          input.focus()
          if (typeof input.select === 'function') {
            input.select()
          }
        }
      })
    },
    async handleAdd() {
      const ten = (this.modalState.form.ten || '').trim()
      const giaTri = (this.modalState.form.giaTri || '').trim()

      if (!ten || !giaTri) {
        this.$toast.error('Vui lòng nhập đầy đủ thông tin')
        return
      }

      if (this.idcId != null) {
        try {
          this.$root.loading(true)
          const data = {
            kieuTT: 'IDC',
            duLieuId: this.idcId,
            ten,
            giaTri,
            thuTu: this.dsThuocTinh.length + 1
          }

          await api.addThuocTinhIdc(this.axios, data)
          await this.onLoadAttributes(this.idcId)

          this.closeAttributeModal()
          this.$toast.success('Thêm thuộc tính thành công')
        } catch (error) {
          this.$toast.error('Thêm thuộc tính thất bại: ' + (error.response?.data?.message || error.message))
        } finally {
          this.$root.loading(false)
        }
      } else {
        const tempData = {
          THUOCTINH_ID: `temp_${Date.now()}`,
          TEN: ten,
          GIATRI: giaTri,
          THUTU: this.tempDsThuocTinh.length + 1
        }
        this.tempDsThuocTinh.push(tempData)
        this.closeAttributeModal()
      }
    },
    async handleEdit() {
      const ten = (this.modalState.form.ten || '').trim()
      const giaTri = (this.modalState.form.giaTri || '').trim()

      if (!ten || !giaTri) {
        this.$toast.error('Vui lòng nhập đầy đủ thông tin')
        return
      }

      const attribute = this.modalState.target
      if (!attribute) {
        this.$toast.error('Không tìm thấy thuộc tính cần cập nhật')
        return
      }

      if (this.idcId != null) {
        try {
          this.$root.loading(true)
          const data = {
            kieuTT: 'IDC',
            duLieuId: this.idcId,
            ten,
            giaTri,
            thuTu: attribute.THUTU || 0
          }

          await api.updateThuocTinhIdc(this.axios, attribute.THUOCTINH_ID, data)
          await this.onLoadAttributes(this.idcId)

          this.closeAttributeModal()
          this.$toast.success('Cập nhật thuộc tính thành công')
        } catch (error) {
          this.$toast.error('Cập nhật thuộc tính thất bại: ' + (error.response?.data?.message || error.message))
        } finally {
          this.$root.loading(false)
        }
      } else {
        const index = this.tempDsThuocTinh.findIndex((item) => item.THUOCTINH_ID === attribute.THUOCTINH_ID)
        if (index !== -1) {
          this.$set(this.tempDsThuocTinh, index, {
            ...this.tempDsThuocTinh[index],
            TEN: ten,
            GIATRI: giaTri
          })
          this.closeAttributeModal()
        }
      }
    },
    async handleDelete(attributeParam = null) {
      const attribute = attributeParam || this.modalState.target
      if (!attribute) {
        this.$toast.error('Không tìm thấy thuộc tính cần xóa')
        return
      }

      const shouldCloseModal = this.modalState.visible && this.modalState.target === attribute

      if (this.idcId != null) {
        try {
          this.$root.loading(true)

          await api.deleteThuocTinhIdc(this.axios, attribute.THUOCTINH_ID)
          await this.onLoadAttributes(this.idcId)

          this.closeAttributeModal()
          this.$toast.success('Xóa thuộc tính thành công')
        } catch (error) {
          this.$toast.error('Xóa thuộc tính thất bại: ' + (error.response?.data?.message || error.message))
        } finally {
          this.$root.loading(false)
        }
        if (shouldCloseModal) {
          this.closeAttributeModal()
        }
      } else {
        const index = this.tempDsThuocTinh.findIndex((item) => item.THUOCTINH_ID === attribute.THUOCTINH_ID)
        if (index !== -1) {
          this.tempDsThuocTinh.splice(index, 1)
        }
        if (shouldCloseModal) {
          this.closeAttributeModal()
        }
        return
      }

      if (shouldCloseModal) {
        this.closeAttributeModal()
      }
    },
    actionColumn(parent) {
      const self = this
      return function() {
        return {
          template: {
            template: `
                        <div class="action-buttons gap-1">
                        <!-- sửa -->
                        <button
                            class="btn btn-sm btn-outline-primary ml-1"
                            @click="onEdit()">
                            <i class="fa fa-pencil"></i> Sửa
                        </button>
                        <button
                            class="btn btn-sm btn-outline-danger ml-1"
                            @click="onDelete()">
                            <i class="fa fa-trash"></i> Xoá
                        </button>
                        </div>
                    `,
            methods: {
              async onEdit() {
                self.onEditAttribute(this.data)
              },
              async onDelete() {
                self.onDeleteAttribute(this.data)
              }
            }
          }
        }
      }
    },
    async onLoadAttributes(idcId) {
      try {
        const res = await api.getThuocTinhIdc(this.axios, {
          kieuTT: 'IDC',
          duLieuId: idcId
        })
        if (res && res.data) {
          this.dsThuocTinh = res.data.data
          this.tempDsThuocTinh = this.dsThuocTinh
        }
      } catch (error) {
        this.$toast.error('Lấy danh sách thuộc tính thất bại: ' + (error.response?.data?.message || error.message))
      }
    },
    async onAddAttribute() {
      this.openAttributeModal('add')
    },
    async onEditAttribute(attribute) {
      this.openAttributeModal('edit', attribute)
    },
    async onDeleteAttribute(attribute) {
      this.openAttributeModal('delete', attribute)
    },

    // Thêm tất cả thuộc tính từ tempDsThuocTinh vào DB sau khi IDC được tạo
    async saveTempAttributes(newIdcId) {
      try {
        for (let i = 0; i < this.tempDsThuocTinh.length; i++) {
          const tempAttr = this.tempDsThuocTinh[i]
          const data = {
            kieuTT: 'IDC',
            duLieuId: newIdcId,
            ten: tempAttr.TEN,
            giaTri: tempAttr.GIATRI,
            thuTu: tempAttr.THUTU || i + 1
          }
          await api.addThuocTinhIdc(this.axios, data)
        }

        // Load lại danh sách từ DB
        await this.onLoadAttributes(newIdcId)

        // Clear temp list
        this.tempDsThuocTinh = []
      } catch (error) {
        this.$toast.error('Lỗi khi lưu thuộc tính: ' + (error.response?.data?.message || error.message))
        throw error
      }
    }
  }
}
</script>
