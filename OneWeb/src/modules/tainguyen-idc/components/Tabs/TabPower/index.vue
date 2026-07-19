<template src="./index.html"></template>

<script>
export default {
  name: 'TabPower',
  props: {
    dataSource: {
      type: Array,
      default: () => []
    },
    loaiCongList: {
      type: Array,
      default: () => []
    },
    isViewMode: {
      type: Boolean,
      default: false
    }
  },
  data: function() {
    return {
      // Danh mục trạng thái port vận hành
      trangThaiPortVanHanhList: [
        { ID: '0', TEN: 'Chưa sử dụng' },
        { ID: '1', TEN: 'Đang sử dụng' },
        { ID: '2', TEN: 'Hỏng' }
      ],
      selectedPorts: [] // Danh sách port được chọn để xóa
    }
  },
  components: {},
  watch: {},
  created: async function() {},
  methods: {
    // Template function cho loại cổng - follow cách của list thiết bị
    colLoaiCongTemplate() {
      const parent = this
      return function() {
        return {
          template: {
            template: `<span>{{ getName(data) }}</span>`,
            methods: {
              getName(data) {
                const id = parent.getSafeValue(data, 'LOAICONG_ID')
                return parent.getTenById(parent.loaiCongList, id)
              }
            }
          }
        }
      }
    },

    // Template function cho trạng thái port vận hành - follow cách của list thiết bị
    colTrangThaiPortVanHanhTemplate() {
      const parent = this
      return function() {
        return {
          template: {
            template: `<span>{{ getName(data) }}</span>`,
            methods: {
              getName(data) {
                const id = parent.getSafeValue(data, 'TRANGTHAI_SD')
                return parent.getTenById(parent.trangThaiPortVanHanhList, id)
              }
            }
          }
        }
      }
    },

    // Template function cho actions - nút Edit
    colActionsTemplate() {
      const parent = this
      return function() {
        return {
          template: {
            template: `
              <button
                class="btn btn-sm btn-outline-primary"
                title="Chỉnh sửa port"
                :disabled=isViewMode()
                @click="editPort(data)"
                style="padding: 2px 8px;">
                <i class="fa fa-edit"></i>
              </button>
            `,
            methods: {
              editPort(data) {
                parent.$emit('edit-port', data)
              },
              isViewMode() {
                return parent.isViewMode
              }
            }
          }
        }
      }
    },

    // Helper methods giống như trong list thiết bị
    getSafeValue(obj, primaryKey) {
      return obj?.[primaryKey] ?? null
    },

    getTenById(list, id, idField = 'ID', nameField = 'TEN') {
      if (!Array.isArray(list) || id === null || id === undefined) {
        return ''
      }
      const found = list.find((x) => x && x[idField] == id)
      return found ? found[nameField] : ''
    },

    // Xử lý khi port được chọn/bỏ chọn
    onPortSelectionChange(selectedRows) {
      this.selectedPorts = selectedRows.data || []
      this.$emit('port-selection-changed', this.selectedPorts)
    },

    // Lấy danh sách port được chọn
    getSelectedPorts() {
      return this.selectedPorts
    }
  }
}
</script>
<style></style>
