<template>
  <div class="item-selector-wrapper">
    <div class="input-more-button -right">
      <button class="btn" :disabled="isTriggerDisabled" @click="showModal">
        <span class="-ap icon-more_horiz"></span>
      </button>
      <input
        type="text"
        class="form-control"
        :value="displayValue"
        readonly
        :placeholder="placeholder"
        @click="showModal"
      />
    </div>

    <b-modal v-model="isModalOpen" :title="modalTitle" size="xl" hide-footer @hidden="closeModal" @shown="onModalShown">
      <div class="popup-box">
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <b-link
                href="javascript:void(0)"
                @click="onSelectItems"
                :class="{ disabled: localSelectedItems.length === 0 }"
              >
                <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chọn
              </b-link>
            </li>
          </ul>
        </div>
        <div class="modal-content p-2">
          <DataGrid
            ref="gridItems"
            :allowPaging="true"
            :enablePagingServer="false"
            :showColumnCheckbox="true"
            v-bind:columns="columns"
            :dataSource="dataSource"
            :enabledSelectFirstRow="false"
            @selectedRowChanged="onSelectedRowChanged"

          >
          </DataGrid>
        </div>
      </div>
    </b-modal>
  </div>
</template>
<script>
export default {
  name: 'ItemSelectorModal',
  props: {
    itemName: {
      type: String,
      default: 'Thiết bị mạng'
    },
    selectedItem: {
      type: Array,
      default: () => []
    },
    columns: {
      type: Array,
      default: () => []
    },
    dataSource: {
      type: Array,
      default: () => []
    },
    // ID của bản ghi cha (IDC, Hạ tầng, VRF, ...)
    parentId: {
      type: [Number, String],
      default: null
    },
    placeholder: {
      type: String,
      default: '-- Chọn --'
    },
    disabled: {
      type: Boolean,
      default: false
    },
    displayField: {
      type: String,
      default: 'ten'
    }
  },
  data() {
    return {
      animationSettings: { effect: 'None' },
      isModalOpen: false,
      localSelectedItems: [],
      displayText: ''
    }
  },
  computed: {
    modalTitle() {
      return `Chọn ${this.itemName}`
    },
    displayValue() {
      return this.displayText || ''
    },
    isTriggerDisabled() {
      return this.disabled
    }
  },
  watch: {
    selectedItem: {
      deep: true,
      handler(newVal) {
        this.localSelectedItems = Array.isArray(newVal) ? [...newVal] : []
        this.displayText = this.resolveDisplayText(this.localSelectedItems)
      }
    },
    // Khi parentId thay đổi thì reset lựa chọn
    parentId(newVal, oldVal) {
      if (newVal !== oldVal) {
        this.resetSelection()
      }
    }
  },
  created() {
    this.localSelectedItems = Array.isArray(this.selectedItem) ? [...this.selectedItem] : []
    this.displayText = this.resolveDisplayText(this.localSelectedItems)
  },
  methods: {
    showModal() {
      if (this.isTriggerDisabled) return
      this.isModalOpen = true
    },
    onModalShown() {
      this.$nextTick(() => {
        this.selectRowsFromSelectedItems()
      })
    },
    selectRowsFromSelectedItems() {
      const grid = this.$refs.gridItems

      if (!this.localSelectedItems || this.localSelectedItems.length === 0) {
        return
      }

      try {
        const indexes = this.localSelectedItems
          .map((selectedItem) => {

            // Tìm index trong dataSource
            const index = this.dataSource.findIndex((row) => {
              return row?.id == selectedItem?.id
            })

            return index
          })
          .filter((idx) => idx !== -1 && idx >= 0)

        // Select các rows theo indexes
        if (indexes.length > 0) {
          grid.selectRows(indexes)
        }
      } catch (error) {
        console.error('Error selecting rows:', error)
      }
    },
    closeModal() {
      this.isModalOpen = false
    },
    resetSelection() {
      // clear local display & notify parent
      this.localSelectedItems = []
      this.displayText = ''
      this.$emit('item-selected', [])
    },
    onSelectItems() {
      if (this.localSelectedItems) {
        this.$emit('item-selected', this.localSelectedItems)
        this.displayText = this.resolveDisplayText(this.localSelectedItems)
        this.closeModal()
      }
    },
    onSelectedRowChanged() {
      this.localSelectedItems = this.$refs.gridItems.$refs.grid.getSelectedRecords()
    },
    resolveDisplayText(items = []) {
      if (!Array.isArray(items) || items.length === 0) return ''
      return items
        .map((item) => {
          if (!item) return ''
          if (item[this.displayField]) return item[this.displayField]
          return ''
        })
        .filter(Boolean)
        .join(', ')
    }
  }
}
</script>

<style scoped>
.item-selector-wrapper {
  width: 100%;
}
</style>
