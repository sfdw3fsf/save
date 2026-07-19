<template src="./index.template.html"></template>

<script>
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import ModalChiTietHeThongKhachHang from './chitiet-hethong-khachhang/index.vue'
import api from './api'

export default {
  components: {
    ModalChiTietHeThongKhachHang,
    BssErrorMarker,
    BssRequiredMarker
  },
  data: function() {
    return {
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
      },
      //
      phamViIdc: null,
      soHuuThietBi: null,
      //
      searchForm: {
        idHeThong: null,
        tenHeThong: null,
        idcId: -1,
        trangThaiVanHanh: null,
        mangThietBi: null
      },
      //
      idcList: [],
      donViQuanLyList: [],
      trangThaiVanHanhList: [],
      nhanVienList: [],
      soHuuThietBiList: [],
      mangThietBiList: [],
      //
      listHeThongThietBiKhachHang: []
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    }
  },
  created: async function() {},
  mounted: async function() {
    await this.loadForm()
  },
  methods: {
    async loadForm() {
      try {
        this.$root.showLoading(true)
        await this.loadDanhMuc()
        await this.loadDanhSachHetHongThietBiKhachHang()
        this.soHuuThietBi = 2
        this.searchForm.idcId = -1
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra, vui lòng thử lại sau')
      } finally {
        this.$root.showLoading(false)
      }
    },
    async loadDanhMuc() {
      try {
        this.idcList = await api.getDanhMucIDC(this.axios, this.$root.token)
        this.idcList.unshift({ ID: -1, TEN: 'Tất cả' })
        
        this.donViQuanLyList = await api.getDanhMuc(this.axios, 'IDC-DONVI-QLY')
        this.trangThaiVanHanhList = await api.getDanhMucTrangThaiVanHanh(this.axios)
        this.mangThietBiList = await api.getDanhMucMangThietBi(this.axios)
        this.soHuuThietBiList = [
          { ID: 1, TEN: 'Nội bộ IDC' },
          { ID: 2, TEN: 'Khách hàng bên ngoài' }
        ]
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi lấy danh mục')
      }
    },
    async loadDanhSachHetHongThietBiKhachHang() {
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
          idHeThong: addParam('ID_HE_THONG', this.searchForm.idHeThong),
          tenHeThong: addParam('TEN', this.searchForm.tenHeThong),
          idcId: addParam('IDC', this.searchForm.idcId),
          trangThaiVanHanh: addParam('TRANG_THAI_VAN_HANH', this.searchForm.trangThaiVanHanh),
          mangTbiId: addParam('MANG_TBI', this.searchForm.mangThietBi)
        }
        
        this.listHeThongThietBiKhachHang = await api.getDanhSachHetHongThietBiKhachHang(this.axios, searchParams)
      } catch (error) {
        console.error('Error loading danh sách hệ thống thiết bị khách hàng:', error)
        this.listHeThongThietBiKhachHang = []
      } finally {
        this.$root.showLoading(false)
      }
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
    onSearch() {
      this.loadDanhSachHetHongThietBiKhachHang()
    },
    onAddNew() {
      // Show modal for adding new item
      this.$refs.ModalChiTietHeThongKhachHang.show(null, 'ADD')
    },
    viewItem(data) {
      console.log('View item:', data)
      this.$refs.ModalChiTietHeThongKhachHang.show(data, 'VIEW')
    },
    editItem(data) {
      console.log('Edit item:', data)
      this.$refs.ModalChiTietHeThongKhachHang.show(data, 'EDIT')
    },
    deleteItem(data) {
      console.log('Delete item:', data)
      this.$confirm('Bạn có chắc chắn muốn xóa hệ thống "' + data.TEN + '" không?', 'Xác nhận xóa', {
        confirmButtonText: 'Xóa',
        cancelButtonText: 'Hủy',
        type: 'warning'
      })
        .then(() => {
          api
            .deleteHetHongThietBiKhachHang(this.axios, { heThongTbiId: data.HETHONGTBI_ID })
            .then((res) => {
              if (res && res.success) {
                this.$root.toastSuccess('Xóa hệ thống thiết bị khách hàng thành công')
                this.loadDanhSachHetHongThietBiKhachHang()
              } else {
                this.$root.toastError(res?.message || 'Xóa hệ thống thiết bị khách hàng thất bại')
              }
            })
            .catch((error) => {
              this.$root.toastError(error?.message || 'Xóa hệ thống thiết bị khách hàng thất bại')
            })
        })
        .catch(() => {
          // User cancelled
        })
    },
    onDetailDataBound(e) {
      // Handle detail data bound event
    },

    onRefreshData() {
      this.onSearch();
    },

    // Xử lý khi thay đổi sở hữu thiết bị
    onChangeSoHuuThietBi() {
      console.log(this.soHuuThietBi);
      
      if (this.soHuuThietBi == 1) {
        // Nếu chọn "Nội bộ IDC" thì redirect về trang nội bộ
        this.$router.push('/tainguyen-idc/danhsach-hethong-thietbi')
      }
    }
  }
}
</script>
<style></style>
