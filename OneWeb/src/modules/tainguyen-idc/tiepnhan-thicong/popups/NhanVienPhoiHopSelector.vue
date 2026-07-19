<template>
  <div class="">
    <div class="input-more-button -right">
      <button class="btn" :disabled="disabled" @click="openDialog">
        <span class="-ap icon-more_horiz"></span>
      </button>
      <input type="text" class="form-control" :placeholder="placeHolder" :value="displaySelectedNames" readonly />
    </div>
    <ejs-dialog
      ref="ejsDialogNVPH"
      :visible="false"
      :isModal="true"
      :enableResize="false"
      :allowDragging="false"
      :header="'Chọn nhân viên phối hợp'"
      showCloseIcon="true"
      width="35%"
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
              <b-link href="javascript:void(0)" @click="$refs.ejsDialogNVPH.hide()">
                <a><span class="icon ui-1_circle-remove nc-icon-glyph"></span>Hủy bỏ</a>
              </b-link>
            </li>
          </ul>
        </div>
        <div class="popup-body">
          <DataGrid
            ref="gridNVPH"
            :panelDataHeight="'480'"
            :dataSource="dataSource"
            :showFilter="true"
            :enabledSelectFirstRow="false"
            :defaultColumnCheckboxChecked="false"
            :showColumnCheckbox="true"
            :enablePagingServer="false"
            :columns="gridColumns"
            @dataBound="onGridDataBound"
            @selectedItemsChanged="onGridSelectedItemsChanged"
          />
        </div>
      </div>
    </ejs-dialog>
  </div>
</template>

<script>
export default {
  name: 'NhanVienPhoiHopSelector',
  props: {
    value: {
      type: Array,
      default: null
    },
    dataSource: {
      type: Array,
      default: () => []
    },
    textField: {
      type: String,
      default: 'TEN_NV'
    },
    valueField: {
      type: String,
      default: 'NHANVIEN_ID'
    },
    disabled: {
      type: Boolean,
      default: false
    },
    placeHolder: {
      type: String,
      default: '-- Chọn nhân viên phối hợp --'
    }
  },
  data() {
    return {
      dialogPosition: { X: 'center', Y: 'center' },
      target: '.main-wrapper',
      localSelected: []
    }
  },
  computed: {
    displaySelectedNames() {
      if (!Array.isArray(this.value) || this.value.length === 0) return ''
      const mapById = new Map(this.dataSource.map((x) => [x[this.valueField], x]))
      const names = this.value
        .map((id) => mapById.get(id))
        .filter(Boolean)
        .map((x) => x[this.textField])
      return names.join(', ')
    },
    gridColumns() {
      return [
        { fieldName: 'MA_NV', headerText: 'Mã nhân viên' },
        { fieldName: this.textField, headerText: 'Tên nhân viên' }
      ]
    }
  },
  watch: {
    value: {
      immediate: true,
      handler(v) {
        this.localSelected = Array.isArray(v) ? [...v] : []
      }
    },
    dataSource: {
      handler() {
        this.$nextTick(this.ensureGridSelection)
      },
      deep: true
    }
  },
  methods: {
    openDialog() {
      if (this.disabled) return
      this.localSelected = Array.isArray(this.value) ? [...this.value] : []
      this.$refs.ejsDialogNVPH.show()
    },
    onBeforeOpen() {
      this.localSelected = Array.isArray(this.value) ? [...this.value] : []
      this.$nextTick(this.ensureGridSelection)
    },
    onGridDataBound() {
      this.ensureGridSelection()
    },
    ensureGridSelection() {
      const grid = this.$refs.gridNVPH
      if (!grid || !Array.isArray(this.localSelected) || this.localSelected.length === 0) return
      
      try {
        // Wait for grid to be fully rendered
        this.$nextTick(() => {
          // Clear current selection first
          if (grid.clearRowSelection) {
            grid.clearRowSelection()
          }
          
          // Get current data (filtered or not)
          const currentData = grid.dataSource || []
          const indexes = []
          
          for (let i = 0; i < currentData.length; i++) {
            const row = currentData[i]
            if (this.localSelected.includes(row[this.valueField])) {
              indexes.push(i)
            }
          }
          
          if (indexes.length > 0 && grid.grid && grid.grid.selectRows) {
            grid.grid.selectRows(indexes)
          }
        })
      } catch (e) {
        console.log('Grid selection error:', e)
      }
    },
    onGridSelectedItemsChanged(items) {
      // items expected as array of selected row objects
      if (Array.isArray(items)) {
        this.localSelected = items.map((x) => x[this.valueField])
      }
    },
    confirmSelection() {
      this.$emit('input', [...this.localSelected])
      this.$refs.ejsDialogNVPH.hide()
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
