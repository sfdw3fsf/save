<template>
  <div class="simple-multi-select">
    <div class="input-more-button -right">
      <button class="btn" :disabled="disabled" @click="openDialog">
        <span class="-ap icon-more_horiz"></span>
      </button>
      <input type="text" class="form-control" :value="displayText" readonly />
    </div>
    <ejs-dialog
      ref="selectDialog"
      :visible="false"
      :isModal="true"
      :header="title"
      :showCloseIcon="true"
      width="70%"
      height="auto"
      :animationSettings="{ effect: 'Zoom' }"
      target="body"
    >
      <div class="modal-content">
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="javascript:void(0)" @click="confirmSelection"> <span class="icon one-save"></span>Ghi lại </a>
            </li>
            <li>
              <a href="javascript:void(0)" @click="cancelSelection">
                <span class="icon ui-1_circle-remove nc-icon-glyph"></span>Hủy
              </a>
            </li>
          </ul>
        </div>
        <div class="popup-body">
          <DataGrid
            ref="dataGrid"
            :dataSource="dataSource"
            :columns="columns"
            :showFilter="true"
            :showColumnCheckbox="true"
            :enabledSelectFirstRow="false"
            :enablePagingServer="false"
            :panelDataHeight="'400'"
            @selectedItemsChanged="onSelectedItemsChanged"
            @dataBound="onGridDataBound"
          />
        </div>
      </div>
    </ejs-dialog>
  </div>
</template>

<script>
export default {
  name: 'SimpleMultiSelect',
  props: {
    value: {
      type: Array,
      default: () => []
    },
    dataSource: {
      type: Array,
      default: () => []
    },
    textField: {
      type: String,
      default: 'TEN'
    },
    valueField: {
      type: String,
      default: 'ID'
    },
    title: {
      type: String,
      default: 'Chọn'
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      selectedItems: [],
      currentSelectedItems: [],
      nhiemVuList: [
        { ID: 1, TEN: 'Chịu trách nhiệm (A)' },
        { ID: 2, TEN: 'Trực tiếp thực hiện (R)' }
      ]
    }
  },
  computed: {
    columns() {
      return [
        { fieldName: 'MA_NV', headerText: 'Mã NV', width: 120 },
        { fieldName: 'TEN_NV', headerText: 'Tên nhân viên', width: 200 },
        { fieldName: 'EMAIL', headerText: 'Email', width: 200 },
        { fieldName: 'SO_DT', headerText: 'Số ĐT', width: 150 },
        {
          fieldName: 'actions',
          headerText: 'Nhiệm vụ',
          textAlign: 'Center',
          width: 200,
          template: this.nhiemVuTemplate()
        }
      ]
    },
    displayText() {
      if (!this.selectedItems || this.selectedItems.length === 0) {
        return ''
      }
      const aItem = this.selectedItems.find((item) => (item.nhiemvuId || item.NHIEMVU_ID) === 1)
      const otherItems = this.selectedItems.filter((item) => (item.nhiemvuId || item.NHIEMVU_ID) !== 1)
      const finalList = aItem ? [aItem, ...otherItems] : otherItems
      return finalList
        .map((item) => {
          const nhiemVuId = item.nhiemvuId || item.NHIEMVU_ID
          const nvText = nhiemVuId === 1 ? ' (A)' : nhiemVuId === 2 ? ' (R)' : ''
          return (item.nhanvienName || item.TEN_NV || item[this.textField]) + nvText
        })
        .join(', ')
    }
  },
  watch: {
    value: {
      immediate: true,
      handler(newVal) {
        if (newVal && Array.isArray(newVal)) {
          this.selectedItems = [...newVal]
        } else {
          this.selectedItems = []
        }
      }
    }
  },
  methods: {
    nhiemVuTemplate() {
      const self = this
      return function() {
        return {
          template: {
            template: `
              <div>
                <select
                  class="form-control form-control-sm"
                  :value="data.NHIEMVU_ID"
                  @change="onNhiemVuChange($event)">
                  <option value="">-- Chọn nhiệm vụ --</option>
                  <option value="1">Chịu trách nhiệm (A)</option>
                  <option value="2">Hỗ trợ (R)</option>
                </select>
              </div>
            `,
            methods: {
              onNhiemVuChange(event) {
                const selectedValue = event.target.value ? Number(event.target.value) : null
                const oldValue = this.data.NHIEMVU_ID
                const selected = self.currentSelectedItems || []
                if (selectedValue === 1) {
                  const existA = selected.find(
                    (item) => Number(item.NHIEMVU_ID) === 1 && item.NHANVIEN_ID !== this.data.NHANVIEN_ID
                  )

                  if (existA) {
                    self.$root.toastWarning('Chỉ được chọn 1 người có nhiệm vụ "Chịu trách nhiệm (A)"')
                    this.data.NHIEMVU_ID = oldValue
                    event.target.value = oldValue || ''
                    return
                  }
                }
                this.data.NHIEMVU_ID = selectedValue
                let currentItem = self.currentSelectedItems.find((item) => item.NHANVIEN_ID === this.data.NHANVIEN_ID)
                if (currentItem) {
                  currentItem.NHIEMVU_ID = selectedValue
                } else {
                  self.currentSelectedItems.push({
                    ...this.data,
                    NHIEMVU_ID: selectedValue
                  })
                }
              }
            }
          }
        }
      }
    },
    openDialog() {
      if (this.disabled) return
      this.$refs.selectDialog.show()
    },
    onSelectedItemsChanged(selectedItems) {
      if (!Array.isArray(selectedItems)) {
        this.currentSelectedItems = []
        return
      }
      const selectedIds = selectedItems.map((i) => i.NHANVIEN_ID)
      this.currentSelectedItems = this.currentSelectedItems.filter((item) => selectedIds.includes(item.NHANVIEN_ID))
      selectedItems.forEach((item) => {
        const exist = this.currentSelectedItems.find((x) => x.NHANVIEN_ID === item.NHANVIEN_ID)
        if (!exist) {
          this.currentSelectedItems.push(item)
        }
      })
    },
    confirmSelection() {
      const selected = this.currentSelectedItems
      if (!selected || selected.length === 0) {
        this.$root.toastWarning('Vui lòng chọn ít nhất 1 nhân viên ')
        return
      }

      // Kiểm tra có đúng 1 người nhiệm vụ A không
      const nhiemVuACount = selected.filter((item) => Number(item.NHIEMVU_ID) === 1).length
      //check nhiem vu
      const missingRole = selected.find((item) => !item.NHIEMVU_ID)
      if (missingRole) {
        this.$root.toastWarning('Vui lòng chọn nhiệm vụ cho tất cả nhân viên')
        return
      }

      if (nhiemVuACount === 0) {
        this.$root.toastWarning('Vui lòng chọn 1 người chịu trách nhiệm (A)')
        return
      }

      if (nhiemVuACount > 1) {
        this.$root.toastWarning('Chỉ được chọn 1 người có nhiệm vụ "Chịu trách nhiệm (A)"')
        return
      }

      // Map sang format backend yêu cầu
      const mappedData = selected.map((item) => ({
        nhanvienId: item.NHANVIEN_ID,
        nhiemvuId: item.NHIEMVU_ID || null,
        // Giữ lại data gốc để hiển thị
        TEN_NV: item.TEN_NV,
        MA_NV: item.MA_NV,
        TEN_DV: item.TEN_DV,
        EMAIL: item.EMAIL,
        SO_DT: item.SO_DT,
        NHANVIEN_ID: item.NHANVIEN_ID,
        NHIEMVU_ID: item.NHIEMVU_ID
      }))

      this.selectedItems = mappedData
      this.$emit('input', mappedData)
      this.$emit('change', mappedData)
      this.$refs.selectDialog.hide()
    },
    cancelSelection() {
      this.$refs.selectDialog.hide()
    },
    onGridDataBound() {
      const grid = this.$refs.dataGrid
      if (grid && this.selectedItems && this.selectedItems.length > 0) {
        this.$nextTick(() => {
          const dataSource = grid.dataSource || []
          const indexes = []

          dataSource.forEach((row, index) => {
            const matchedItem = this.selectedItems.find(
              (item) => (item.nhanvienId || item.NHANVIEN_ID) === row.NHANVIEN_ID
            )
            if (matchedItem) {
              indexes.push(index)
              // Set NHIEMVU_ID vào row để hiển thị trong dropdown
              row.NHIEMVU_ID = matchedItem.nhiemvuId || matchedItem.NHIEMVU_ID
            }
          })

          if (indexes.length > 0 && grid.selectRows) {
            grid.selectRows(indexes)
          }

          // Force update select values sau khi render
          this.$nextTick(() => {
            const selects = document.querySelectorAll('.popup-body select.form-control-sm')
            selects.forEach((select, idx) => {
              const rowData = dataSource[idx]
              if (rowData && rowData.NHIEMVU_ID) {
                select.value = rowData.NHIEMVU_ID
              }
            })
          })
        })
      }
    }
  }
}
</script>

<style scoped>
.simple-multi-select {
  position: relative;
}
</style>
