<template>
  <div class="">
    <div class="input-more-button -right">
      <button class="btn" :disabled="disabled" @click="openDialog">
        <span class="-ap icon-more_horiz"></span>
      </button>
      <input type="text" class="form-control" :value="displaySelectedNames" readonly />
    </div>
    <ejs-dialog
      ref="ejsDialogCaNhanQuanLy"
      :visible="false"
      :isModal="true"
      :enableResize="false"
      :allowDragging="false"
      :header="'Chọn cá nhân quản lý'"
      :showCloseIcon="true"
      width="70%"
      :animationSettings="{ effect: 'Zoom' }"
      :target="target"
      :position="dialogPosition"
      @beforeOpen="onBeforeOpen"
    >
      <div class="modal-content">
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <b-link href="javascript:void(0)" @click="confirmSelection">
                <a><span class="icon one-save"></span>Ghi lại </a>
              </b-link>
            </li>
            <li>
              <b-link href="javascript:void(0)" @click="cancelSelection">
                <a><span class="icon ui-1_circle-remove nc-icon-glyph"></span>Hủy</a>
              </b-link>
            </li>
          </ul>
        </div>
        <div class="popup-body">
          <DataGrid
            ref="gridNVPH"
            :key="`grid-${dataSource.length}`"
            :panelDataHeight="'480'"
            :dataSource="dataSource"
            :showFilter="true"
            :enabledSelectFirstRow="false"
            :defaultColumnCheckboxChecked="false"
            :showColumnCheckbox="true"
            :enablePagingServer="false"
            :columns="gridColumns"
            @dataBound="onGridDataBound"
            @selectedItemsChanged="onGridRowChanged"
          />
        </div>
      </div>
    </ejs-dialog>
  </div>
</template>

<script>
import DanhMucApi from '../../../../api/DanhMuc.api'

export default {
  name: 'CaNhanQuanLySelector',
  props: {
    value: {
      type: Array,
      default: () => []
    },
    dataSource: {
      type: Array,
      default: () => []
    },
    role: {
      type: Array,
      default: () => []
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      dialogPosition: { X: 'center', Y: 'center' },
      target: '.main-wrapper',
      isSaved: false,
      localSelected: []
      // roles: []
    }
  },
  computed: {
    displaySelectedNames() {
      if (!Array.isArray(this.value) || this.value.length === 0) {
        return '-- Chọn cá nhân quản lý --'
      }

      const names = this.value.map((item) => {
        const nhiemVu = this.role.find((nv) => nv.ID === item.NHIEMVU_ID)
        const tenTat = nhiemVu ? nhiemVu.TENTAT : 'N/A'

        return `${item.TEN_NV} (${tenTat})`
      })

      return names.join(', ')
    },
    gridColumns() {
      return [
        { fieldName: 'MA_NV', headerText: 'Mã nhân viên' },
        { fieldName: 'TEN_NV', headerText: 'Tên nhân viên' },
        { fieldName: 'EMAIL', headerText: 'Email' },
        { fieldName: 'SO_DT', headerText: 'SDT' },
        { fieldName: 'NHIEMVU_ID', headerText: 'Nhiệm vụ', valueAccessor: this.getNhiemVuName }
      ]
    }
  },
  watch: {
    value: {
      immediate: true,
      deep: true,
      handler(v) {
        this.localSelected = Array.isArray(v) ? [...v] : []
        this.$nextTick(() => {
          this.$forceUpdate()
        })
      }
    }
  },
  methods: {
    getNhiemVuName: function(field, data, column) {
      const nhiemVu = this.role.find((nv) => nv.ID === data.NHIEMVU_ID)
      return nhiemVu ? nhiemVu.TEN : ''
    },
    // createDropdownTemplate() {
    //   const self = this

    //   return function() {
    //     return {
    //       template: {
    //         template: `
    //           <div>
    //             <div v-if="isRowSelected">
    //             <SelectExt
    //               v-model="rowData.NHIEMVU_ID"
    //               :dataSource="roles"
    //               dataTextField="TEN"
    //               dataValueField="ID"
    //             />
    //           </div>

    //           <div v-else>
    //             {{ nhiemVuName }}
    //           </div>
    //           </div>
    //         `,
    //         data() {
    //           return {
    //             rowData: {} // dữ liệu của dòng hiện tại
    //           }
    //         },
    //         computed: {
    //           roles() {
    //             return self.role
    //           },
    //           isRowSelected() {
    //             return self.localSelected.some((item) => item.MA_NV === this.rowData.MA_NV)
    //           },
    //           nhiemVuName() {
    //             const nv = self.role.find((role) => role.ID === this.rowData.NHIEMVU_ID)
    //             return nv ? nv.TEN : ''
    //           }
    //         }
    //       }
    //     }
    //   }
    // },
    openDialog() {
      if (this.disabled) return
      this.localSelected = Array.isArray(this.value) ? [...this.value] : []
      this.$refs.ejsDialogCaNhanQuanLy.show()
    },
    onBeforeOpen() {
      this.localSelected = Array.isArray(this.value) ? [...this.value] : []
      this.$nextTick(() => {
        const grid = this.$refs.gridNVPH
        if (grid && grid.refresh) {
          grid.refresh()
        }
      })
    },
    onGridDataBound() {
      this.ensureGridSelection()
    },
    ensureGridSelection() {
      const grid = this.$refs.gridNVPH
      if (!grid || !Array.isArray(this.localSelected) || this.localSelected.length === 0) return

      try {
        this.$nextTick(() => {
          // Clear current selection first
          if (grid.clearRowSelection) {
            grid.clearRowSelection()
          }

          // Select rows based on localSelected
          const selectedMaNVs = this.localSelected.map((item) => item.MA_NV)
          const currentData = grid.dataSource || []
          const indexes = []

          for (let i = 0; i < currentData.length; i++) {
            const row = currentData[i]
            if (selectedMaNVs.includes(row.MA_NV)) {
              indexes.push(i)
            }
          }

          if (indexes.length > 0) {
            grid.selectRows(indexes)
          }
        })
      } catch (e) {
        console.log('Grid selection error:', e)
      }
    },
    onGridRowChanged(selectedItems) {
      if (!Array.isArray(selectedItems)) return

      // If no items selected, clear localSelected
      if (selectedItems.length === 0) {
        this.localSelected = []
        return
      }

      // this first item in localSelected get role 1, the other get role 2
      selectedItems.forEach((item, index) => {
        if (index === 0) {
          item.NHIEMVU_ID = this.role.length > 0 ? this.role[0].ID : null
        } else {
          item.NHIEMVU_ID = this.role.length > 0 ? this.role[1].ID : null
        }
      })
      this.localSelected = selectedItems
    },
    confirmSelection() {
      this.$refs.ejsDialogCaNhanQuanLy.hide()

      this.$emit('input', this.localSelected)
      this.$nextTick(() => {
        this.$forceUpdate()
      })
    },
    cancelSelection() {
      this.localSelected = Array.isArray(this.value) ? [...this.value] : []
      this.$refs.ejsDialogCaNhanQuanLy.hide()
    }
  }
}
</script>

<style scoped>
.e-dialog {
  position: fixed !important;
  top: 50% !important;
  height: 80% !important;
  left: 50% !important;
  transform: translate(-50%, -50%) !important;
}
</style>
