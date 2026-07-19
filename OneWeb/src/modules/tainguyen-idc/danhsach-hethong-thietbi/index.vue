<template src="./index.template.html"></template>

<script>
import ModalChiTietHeThong from './chitiet-hethong/index.vue'
import api from './api'

export default {
  components: {
    ModalChiTietHeThong
  },
  data: function() {
    return {
      isNoiBo: 1,
      // Form tìm kiếm
      searchForm: {
        idHeThong: '',
        tenHeThong: '',
        idc: '',
        donViQuanLy: this.$root.token.getDonViID() || '',
        nguoiQuanLy: -1,
        trangThaiVanHanh: '',
        mangThietBi: ''
      },
      // Danh sách hệ thống thiết bị
      listHeThongThietBi: [],

      // Danh mục
      soHuuThietBiList: [
        { ID: 1, TEN: 'Nội bộ IDC' },
        { ID: 2, TEN: 'Khách hàng bên ngoài' }
      ],
      idcList: [],
      donViQuanLyList: [],
      trangThaiVanHanhList: [],
      nhanVienList: [],
      mangThietBiList: [],

      buttons: {
        btnAddNew: {
          enabled: true
        },
        btnSave: {
          enabled: false
        },
        btnCancel: {
          enabled: false
        },
        btnEdit: {
          enabled: false
        },
        btnDelete: {
          enabled: false
        }
      }
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    }
  },
  created: async function() {
    await this.loadForm()
  },
  methods: {
    // Load dữ liệu ban đầu
    async loadForm() {
      try {
        this.$root.showLoading(true)
        await this.loadDanhSachHeThongThietBi()
        await this.loadDanhMuc()
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra, vui lòng thử lại sau')
      } finally {
        this.$root.showLoading(false)
      }
    },

    // Load danh sách hệ thống thiết bị
    async loadDanhSachHeThongThietBi() {
      try {
        this.listHeThongThietBi = await api.getDanhSachHeThongThietBi(this.axios)
      } catch (error) {
        console.error('Error loading danh sách hệ thống thiết bị:', error)
        this.listHeThongThietBi = []
      }
    },

    // Load danh mục
    async loadDanhMuc() {
      try {
        this.idcList = await api.getDanhMucIDC(this.axios, this.$root.token)
        this.idcList.unshift({ ID: -1, TEN: 'Tất cả' })

        this.donViQuanLyList = await api.getDanhMucDonViQuanLy(this.axios, this.$root.token)
        this.donViQuanLyList.unshift({ ID: -1, TEN: 'Tất cả' })

        this.trangThaiVanHanhList = await api.getDanhMucTrangThaiVanHanh(this.axios)
        this.trangThaiVanHanhList.unshift({ ID: -1, TEN: 'Tất cả' })

        this.nhanVienList = await api.getDanhMucNhanVienQuanLy(this.axios, this.$root.token)
        this.nhanVienList.unshift({ ID: -1, TEN: 'Tất cả' })

        this.mangThietBiList = await api.getDanhMucMangThietBi(this.axios)
        this.mangThietBiList.unshift({ ID: -1, TEN: 'Tất cả' })

        // Set giá trị mặc định cho phạm vi IDC
        this.searchForm.idc = ''
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi lấy danh mục')
      }
    },

    // Xử lý khi thay đổi sở hữu thiết bị
    onChangeSoHuuThietBi() {
      if (this.isNoiBo == 2) {
        // Nếu chọn "Khách hàng bên ngoài" thì redirect
        this.$router.push('/tainguyen-idc/danhsach-hethong-thietbi-khachhang')
      }
    },
    // Tìm kiếm hệ thống thiết bị
    async onSearch() {
      try {
        this.$root.showLoading(true)

        // Xử lý tham số tìm kiếm - chuyển -1 thành null để bỏ qua filter
        const addParam = (key, val, ignore = [null, undefined, '']) => {
          if (val == -1) {
            return null
          } else if (!ignore.includes(val)) {
            return val?.toString?.().trim?.() || val
          }
          return null
        }

        const searchParams = {
          id: addParam('ID_HE_THONG', this.searchForm.idHeThong),
          ten: addParam('TEN', this.searchForm.tenHeThong),
          idcId: addParam('IDC', this.searchForm.idc),
          donviQlId: addParam('DON_VI_QUAN_LY', this.searchForm.donViQuanLy),
          nhanvienQlId: addParam('NGUOI_QUAN_LY', this.searchForm.nguoiQuanLy),
          trangthaiVhId: addParam('TRANG_THAI_VAN_HANH', this.searchForm.trangThaiVanHanh),
          mangThietBiId: addParam('MANG_TBI', this.searchForm.mangThietBi)
        }

        this.listHeThongThietBi = await api.searchHeThongThietBi(this.axios, searchParams)
        this.$root.toastSuccess('Tìm kiếm thành công!')
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tìm kiếm')
      } finally {
        this.$root.showLoading(false)
      }
    },

    // Refresh dữ liệu từ modal
    async onRefreshData() {
      await this.loadDanhSachHeThongThietBi()
    },

    acctionColumn(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons">
                <button class="btn btn-sm btn-outline-primary" title="Xem chi tiết" @click="view()">
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
              view() {
                parent.viewItem(this.data)
              },
              deleteItem() {
                parent.deleteItem(this.data)
              },
              editItem() {
                parent.editItem(this.data)
              }
            }
          }
        }
      }
    },
    onAddNew() {
      this.$refs.ModalChiTietHeThong.show(null, 'ADD')
    },

    viewItem(data) {
      console.log('View item:', data)
      this.$refs.ModalChiTietHeThong.show(data, 'VIEW')
    },

    editItem(data) {
      console.log('Edit item:', data)
      this.$refs.ModalChiTietHeThong.show(data, 'EDIT')
    },

    async deleteItem(data) {
      try {
        await this.$confirm('Bạn có chắc chắn muốn xóa hệ thống "' + data.TEN + '" không?', 'Xác nhận xóa', {
          confirmButtonText: 'Xóa',
          cancelButtonText: 'Hủy',
          type: 'warning'
        })

        this.$root.showLoading(true)
        await api.deleteMultiHeThongThietBi(this.axios, data.HETHONGTBI_ID ? [data.HETHONGTBI_ID] : [])
        this.$root.toastSuccess('Xóa hệ thống thành công!')
        await this.loadDanhSachHeThongThietBi()
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra, vui lòng thử lại sau')
      } finally {
        this.$root.showLoading(false)
      }
    },
    onDetailDataBound(e) {
      // Handle detail data bound event
    },

    // ====== TEMPLATE METHODS ====== //
    getSafeValue(obj, primaryKey) {
      return obj?.[primaryKey] ?? null
    },

    getTenById(list, id, idField = 'ID', nameField = 'TEN') {
      if (!Array.isArray(list) || id === null || id === undefined) {
        return ''
      }
      const found = list.find((x) => x && x[idField] === id)
      return found ? found[nameField] : ''
    },

    // Template cho cột IDC
    colIDCTemplate() {
      const parent = this
      return function() {
        return {
          template: {
            template: `<span>{{ getName(data) }}</span>`,
            methods: {
              getName(data) {
                const id = parent.getSafeValue(data, 'IDC_ID')
                return parent.getTenById(parent.idcList, id)
              }
            }
          }
        }
      }
    },

    // Template cho cột Đơn vị quản lý
    colDonViQuanLyTemplate() {
      const parent = this
      return function() {
        return {
          template: {
            template: `<span>{{ getName(data) }}</span>`,
            methods: {
              getName(data) {
                const id = parent.getSafeValue(data, 'DONVI_QL_ID')
                return parent.getTenById(parent.donViQuanLyList, id)
              }
            }
          }
        }
      }
    },

    // Template cho cột Trạng thái vận hành
    colTrangThaiVanHanhTemplate() {
      const parent = this
      return function() {
        return {
          template: {
            template: `<span>{{ getName(data) }}</span>`,
            methods: {
              getName(data) {
                const id = parent.getSafeValue(data, 'TRANGTHAI_VH_ID')
                return parent.getTenById(parent.trangThaiVanHanhList, id)
              }
            }
          }
        }
      }
    },

    // Template cho cột Nhân viên quản lý
    colNhanVienTemplate() {
      const parent = this
      return function() {
        return {
          template: {
            template: `<span>{{ getName(data) }}</span>`,
            methods: {
              getName(data) {
                const id = parent.getSafeValue(data, 'NHANVIEN_QL_ID')
                return parent.getTenById(parent.nhanVienList, id)
              }
            }
          }
        }
      }
    },

    // Template cho cột Mảng thiết bị
    colMangThietBiTemplate() {
      const parent = this
      return function() {
        return {
          template: {
            template: `<span>{{ getName(data) }}</span>`,
            methods: {
              getName(data) {
                const id = parent.getSafeValue(data, 'MANG_TBI_ID')
                return parent.getTenById(parent.mangThietBiList, id)
              }
            }
          }
        }
      }
    }
  }
}
</script>
<style scoped>
.action-buttons {
  display: flex;
  gap: 5px;
}

.action-buttons .btn {
  padding: 2px 8px;
  font-size: 12px;
}

.action-buttons .btn i {
  font-size: 14px;
}
</style>
