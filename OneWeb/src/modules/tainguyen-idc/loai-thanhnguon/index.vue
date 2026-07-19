<template src="./index.template.html"></template>
<script>
import Vue from 'vue'
import ModalChiTietLoaiThanhNguon from './chitiet-loai-thanh-nguon/index.vue'

export default {
  name: 'LoaiThanhNguon',
  components: {
    ModalChiTietLoaiThanhNguon
  },
  data() {
    return {
      // Search form
      searchForm: {
        idLoaiThanhNguon: '',
        tenLoaiThanhNguon: '',
        loaiDongDien: -1,
        hangId: -1,
        lapDat: -1,
        congSuatThietKe: '',
        thongSoDongDien: '',
        dienApDongDien: '',
        chieuDai: '',
        chieuRong: '',
        chieuCao: '',
        soLuongIecC13: '',
        soLuongIecC19: '',
        soLuongKhac: ''
      },

      // Data source
      dsLoaiThanhNguon: [],
      hangSanXuatSelectionList: [],
      dsSelected: [],

      // Options for dropdowns
      loaiDongDienSelectionList: [
        { ID: -1, TEN: 'Tất cả' },
        { ID: 1, TEN: '1 pha' },
        { ID: 3, TEN: '3 pha' }
      ],

      lapDatSelectionList: [
        { ID: -1, TEN: 'Tất cả' },
        { ID: 0, TEN: 'Ngang' },
        { ID: 1, TEN: 'Dọc' }
      ],

      hieuLucSelectionList: [
        { ID: -1, TEN: 'Tất cả' },
        { ID: 1, TEN: 'Hiệu lực' },
        { ID: 0, TEN: 'Không hiệu lực' }
      ]
    }
  },

  created: async function() {
    try {
      this.$root.showLoading(true)
      this.hangSanXuatSelectionList = await this.getDanhMucHangSanXuat()
      this.hangSanXuatSelectionList.unshift({ ID: -1, TEN: 'Tất cả', TEN_HIEN_THI: 'Tất cả' })
      // Load initial data without search criteria
      this.dsLoaiThanhNguon = await this.getDanhSachLoaiThanhNguon()
    } catch (error) {
      console.error('Error in created:', error)
      this.$toast.error('Có lỗi khi khởi tạo dữ liệu')
    } finally {
      this.$root.showLoading(false)
    }
  },

  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    }
  },

  methods: {
    onSearch: async function() {
      try {
        this.$root.showLoading(true)
        this.dsLoaiThanhNguon = await this.searchLoaiThanhNguon()
      } catch (error) {
        console.error('Error in search:', error)
        this.$root.toastError('Có lỗi khi tìm kiếm dữ liệu')
      } finally {
        this.$root.showLoading(false)
      }
    },

    onNew: function() {
      this.$refs.ModalChiTietLoaiThanhNguon.showModal('NEW')
    },

    onView: function(item) {
      this.$refs.ModalChiTietLoaiThanhNguon.showModal('VIEW', item)
    },

    onEdit: function(item) {
      this.$refs.ModalChiTietLoaiThanhNguon.showModal('EDIT', item)
    },

    onDelete: async function(item) {
      try {
        const confirmFlag = await this.$confirm(
          `Bạn có muốn xóa loại thanh nguồn "${item.TEN}" không?`,
          'Xác nhận xóa',
          {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning',
            dangerouslyUseHTMLString: true
          }
        )
          .then((s) => true)
          .catch((e) => false)

        if (confirmFlag) {
          this.$root.showLoading(true)
          const result = await this.deleteLoaiThanhNguon([item.ID])
          if (result) {
            this.$root.toastSuccess('Xóa loại thanh nguồn thành công')
            this.dsLoaiThanhNguon = await this.searchLoaiThanhNguon()
          }
        }
      } catch (error) {
        console.error('Error in delete:', error)
        this.$root.toastError('Có lỗi khi xóa loại thanh nguồn')
      } finally {
        this.$root.showLoading(false)
      }
    },

    onDeleteMultiple: async function() {
      if (!this.dsSelected || this.dsSelected.length === 0) {
        this.$root.toastError('Vui lòng chọn ít nhất một bản ghi để xóa')
        return
      }

      try {
        const confirmFlag = await this.$confirm(
          `Bạn có muốn xóa ${this.dsSelected.length} loại thanh nguồn đã chọn không?`,
          'Xác nhận xóa',
          {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning',
            dangerouslyUseHTMLString: true
          }
        )
          .then((s) => true)
          .catch((e) => false)

        if (confirmFlag) {
          this.$root.showLoading(true)
          const ids = this.dsSelected.map((item) => item.ID)
          const result = await this.deleteLoaiThanhNguon(ids)
          if (result) {
            this.$root.toastSuccess(`Xóa ${this.dsSelected.length} loại thanh nguồn thành công`)
            this.dsSelected = []
            this.dsLoaiThanhNguon = await this.searchLoaiThanhNguon()
          }
        }
      } catch (error) {
        console.error('Error in delete multiple:', error)
        this.$root.toastError('Có lỗi khi xóa loại thanh nguồn')
      } finally {
        this.$root.showLoading(false)
      }
    },

    onSelectRowCheckbox(itemsCheck) {
      this.dsSelected = itemsCheck
    },

    onRefreshData: async function() {
      try {
        this.$root.showLoading(true)
        // Refresh with current search criteria
        this.dsLoaiThanhNguon = await this.getDanhSachLoaiThanhNguon()
      } catch (error) {
        console.error('Error in refresh:', error)
        this.$root.toastError('Có lỗi khi tải lại dữ liệu')
      } finally {
        this.$root.showLoading(false)
      }
    },

    // Action column template
    actionColumn: function(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons">
                <button class="btn btn-sm btn-outline-primary" title="Xem chi tiết" @click="viewItem()">
                  <i class="fa fa-eye"></i>
                </button>
                <button class="btn btn-sm btn-outline-primary ml-1" title="Chỉnh sửa" @click="editItem()">
                  <i class="fa fa-edit"></i>
                </button>
                <button class="btn btn-sm btn-outline-danger ml-1" title="Xóa" @click="deleteItem()">
                  <i class="fa fa-trash"></i>
                </button>
              </div>`,
            methods: {
              viewItem: function() {
                parent.onView(this.data)
              },
              editItem: function() {
                parent.onEdit(this.data)
              },
              deleteItem: function() {
                parent.onDelete(this.data)
              }
            }
          }
        }
      }
    },

    // =============== TEMPLATE ============= //
    getHangSanXuatText(value) {
      return this.convertFromList(this.hangSanXuatSelectionList, value)
    },
    getHangSanXuatTemplate: function(parent) {
      return function() {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function() {
                return parent.getHangSanXuatText(this.data.HANG_SX_ID)
              }
            }
          }
        }
      }
    },

    getLoaiDongDienText(value) {
      return this.convertFromList(this.loaiDongDienSelectionList, value)
    },

    getLoaiDongDienTemplate: function(parent) {
      return function() {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function() {
                return parent.getLoaiDongDienText(this.data.LOAI_DONGDIEN)
              }
            }
          }
        }
      }
    },

    getLapDatText(value) {
      return this.convertFromList(this.lapDatSelectionList, value)
    },

    getLapDatTemplate: function(parent) {
      return function() {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function() {
                return parent.getLapDatText(this.data.LAP_DAT)
              }
            }
          }
        }
      }
    },

    getHieuLucText: function(value) {
      return value.toString() === '1' ? 'Hiệu lực' : 'Không hiệu lực'
    },

    getHieuLucTemplate: function(parent) {
      return function() {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function() {
                return parent.getHieuLucText(this.data.SUDUNG)
              }
            }
          }
        }
      }
    },

    convertFromList(list, value) {
      if (value === null || value === undefined) return 'N/A'
      const item = list.find((x) => x.ID.toString() === value.toString())
      return item ? item.TEN : 'N/A'
    },

    // ================= API ================= //
    getDanhSachLoaiThanhNguon: async function() {
      let result = []
      try {
        let rs = await this.axios.get('/web-ecms/idc/loai-thanh-nguon')
        result = rs.data.data
      } catch (error) {
        this.handleApiError(e)
      }
      return result
    },

    searchLoaiThanhNguon: async function() {
      const params = {}
      const addParam = (key, val, ignore = [null, undefined, '']) => {
        if (val == -1) {
          params[key] = null
        } else if (!ignore.includes(val)) {
          params[key] = val?.toString?.().trim?.() || val
        }
      }

      try {
        addParam('id', this.searchForm.idLoaiThanhNguon)
        addParam('ten', this.searchForm.tenLoaiThanhNguon)
        addParam('loaiDongDien', this.searchForm.loaiDongDien)
        addParam('hangSxId', this.searchForm.hangId)
        addParam('lapDat', this.searchForm.lapDat)
        addParam('csThietKe', this.searchForm.congSuatThietKe)
        addParam('thongSoDc', this.searchForm.thongSoDongDien)
        addParam('dienApDc', this.searchForm.dienApDongDien)
        addParam('dai', this.searchForm.chieuDai)
        addParam('rong', this.searchForm.chieuRong)
        addParam('cao', this.searchForm.chieuCao)
        addParam('slIecC13', this.searchForm.soLuongIecC13)
        addParam('slIecC19', this.searchForm.soLuongIecC19)
        addParam('slKhac', this.searchForm.soLuongKhac)

        const rs = await this.axios.post('/web-ecms/idc/loai-thanh-nguon/search', params)
        return rs.data.data || []
      } catch (error) {
        this.handleApiError(error)
        return []
      }
    },

    getDanhMucHangSanXuat: async function() {
      let result = []
      try {
        let rs = await this.axios.get('/web-ecms/idc/danh-muc/hang-sx')
        result = rs.data.data.map((item) => {
          return {
            ...item,
            TEN_HIEN_THI: `${item.TEN || 'N/A'}`
          }
        })
      } catch (e) {
        this.handleApiError(e)
      }
      return result
    },

    deleteLoaiThanhNguon: async function(payload) {
      let result = 0
      try {
        let rs = await this.axios.delete('/web-ecms/idc/loai-thanh-nguon/delete-multi', {
          data: { ids: payload }
        })
        result = rs.data
      } catch (e) {
        this.handleApiError(e)
      }
      return result
    },

    handleApiError(e) {
      if (e?.data?.message_detail) {
        this.$root.toastError(e.data.message_detail)
      } else {
        console.error(e)
      }
    }
  }
}
</script>

<style>
.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

.check-action {
  top: 3px;
}

.check-action input[type='checkbox']:checked:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.check-action input[type='checkbox']:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
}
</style>
