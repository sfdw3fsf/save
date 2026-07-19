<template src="./index.template.html"> </template>
<script>
import Vue from 'vue'
import moment from 'moment'
import ModalChiTietThietBiKhachHang from './chitiet-thietbi-khachhang/index.vue'
import api from './api'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
export default {
  name: 'QuanLyThietBiKhachHang',
  components: {
    ModalChiTietThietBiKhachHang,
    BssErrorMarker,
    BssRequiredMarker
  },
  data() {
    return {
      //
      trangThaiVanHanhList: [],
      dsThietBiKhachHang: [],
      loaiKhachHangList: [],
      loaiThietBiList: [],
      listToaNha: [],
      matSanList: [],
      phongList: [],
      rackList: [],
      mangThietBiList: [],
      childGrid: {
        dataSource: [],
        queryString: 'ID_THIET_BI_CHA',
        columns: [
          { field: 'ID', headerText: 'ID Thiết bị con', textAlign: 'Left', width: 120 },
          { field: 'TEN', headerText: 'Tên thiết bị con', textAlign: 'Left', width: 200 },
          { field: 'LOAI_THIET_BI', headerText: 'Loại thiết bị', textAlign: 'Left', width: 120 },
          { field: 'SERIAL_NUMBER', headerText: 'Serial', textAlign: 'Left', width: 150 },
          { field: 'CS_DINHDANH', headerText: 'Công suất TKE', textAlign: 'Left', width: 120 },
          { field: 'U', headerText: 'Vị trí', textAlign: 'Left', width: 120 },
          { field: 'THONG_SO_KY_THUAT', headerText: 'Thông số kỹ thuật', textAlign: 'Left', width: 180 },
          { field: 'TRANG_THAI_VAN_HANH', headerText: 'Trạng thái vận hành', textAlign: 'Left', width: 140 },
          {
            field: 'ACTIONS',
            headerText: 'Thao tác',
            textAlign: 'Center',
            template: this.acctionColumn(this),
            width: 150
          }
        ]
      },
      searchForm: {
        thietBiKHId: null,
        ten: null,
        mangThietBi: null,
        loaiThietBiId: null,
        model: null,
        serialNumber: null,
        tag: null,
        toaNhaId: null,
        matSanId: null,
        phongId: null,
        rackId: null,
        trangThaiVanHanhId: null,
        hdtbId: null,
        loaiKhachHang: 1,
        tinhId: this.$root.token.getPhanVungID()

      },
      showGrid: true,
      currentMode: 'SEARCH'
    }
  },
  computed: {
    childGridOptions() {
      return this.childGrid
    }
  },
  watch: {},
  created: async function () { },
  mounted: async function () {
    await this.loadForm()
  },
  methods: {
    async loadForm() {
      try {
        this.$root.showLoading(true)
        await this.loadDanhMuc()
        this.loaiKhachHangList = await api.getDanhMuc(this.axios, 'LOAI-KH')
        this.loaiThietBiList = await api.getDanhMucLoaiThietBi(this.axios)
        this.trangThaiVanHanhList = await api.getDanhMucTrangThaiVanHanh(this.axios)
        this.mangThietBiList = await api.getDanhMucMangThietBi(this.axios)
        this.matSanList = await api.getDanhMucMatSan(this.axios)
        this.phongList = await api.getDanhMucPhong(this.axios)
        this.rackList = await api.getDanhMucRack(this.axios)
        await this.getDanhSachThietBiKhachHang()
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra, vui lòng thử lại sau')
      } finally {
        this.$root.showLoading(false)
      }
    },
    async loadDanhMuc() {
      try {
        // Load tòa nhà tham khảo từ thiết bị IDC
        this.listToaNha = await api.getDanhMucToaNhaAll(this.axios)
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi lấy danh mục')
      }
    },
    async getDanhSachThietBiKhachHang() {
      this.showGrid = false
      this.dsThietBiKhachHang = []
      this.childGrid.dataSource = []
      let allData = await api.getDanhSachThietBiKhachHang(this.axios, this.searchForm)

      // Phân loại thiết bị cha và con
      allData.forEach((item, index) => {
        if (item.THIETBICHA_ID === null || item.THIETBICHA_ID === undefined) {
          item.ID_THIET_BI_CHA = item.ID
          this.dsThietBiKhachHang.push(item)
        } else {
          item.ID_THIET_BI_CHA = item.THIETBICHA_ID
          this.childGrid.dataSource.push(item)
        }
      })

      this.dsThietBiKhachHang.forEach((parent) => {
        const children = this.childGrid.dataSource.filter((child) => child.ID_THIET_BI_CHA === parent.ID)
      })
      this.showGrid = true
    },
    acctionColumn(parent) {
      return function () {
        return {
          template: {
            template: `<div class="action-buttons">
                <button class="btn btn-sm btn-outline-primary" title="Xem chi tiết" @click="view()">
                  <i class="fa fa-eye"></i>
                </button>
                <button class="btn btn-sm btn-outline-success" title="Chỉnh sửa" @click="editItem()">
                  <i class="fa fa-edit"></i>
                </button>
                <button class="btn btn-sm btn-outline-danger" title="Xóa" @click="deleteItem()">
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

    viewItem(item) {
      this.$refs.ModalChiTietThietBiKhachHang.show(item, 'VIEW')
    },

    editItem(item) {
      this.$refs.ModalChiTietThietBiKhachHang.show(item, 'EDIT')
    },

    deleteItem(item) {
      this.$confirm('Bạn có chắc chắn muốn xóa thiết bị: ' + item.TEN + ' (ID: ' + item.ID + ')?', 'Xác nhận', {
        confirmButtonText: 'Xoá',
        cancelButtonText: 'Huỷ'
      })
        .then(async () => {
          try {
            this.$root.showLoading(true)
            let rs = await api.deleteThietBiKhachHang(this.axios, item.ID)
            if (rs && rs.error_code === 'BSS-00000000') {
              this.$root.toastSuccess('Xoá thiết bị thành công')
              await this.getDanhSachThietBiKhachHang()
            } else {
              this.$root.toastError(rs?.message || 'Xoá thiết bị không thành công')
            }
          } catch (error) {
            this.$root.toastError(error?.message || 'Có lỗi xảy ra, vui lòng thử lại sau')
          } finally {
            this.$root.showLoading(false)
          }
        })
        .catch(() => {
          // Hủy xóa
        })
    },

    onDetailDataBound(args) {
      const detailGrid = args.detailElement.querySelector('.e-grid')
      if (detailGrid) {
        detailGrid.style.border = '1px solid #e0e0e0'
        detailGrid.style.borderRadius = '4px'
        detailGrid.style.margin = '10px'
        detailGrid.style.backgroundColor = '#fafafa'
      }

      const parentData = args.data
      if (parentData) {
        const childDevicesForThisParent = this.childGrid.dataSource.filter(
          (child) => child.ID_THIET_BI_CHA === parentData.ID
        )
        const childCount = childDevicesForThisParent.length

        if (childCount > 0) {
          const headerElement = args.detailElement.querySelector('.e-gridheader')

          if (headerElement) {
            const customHeader = document.createElement('div')
            customHeader.innerHTML = `
              <div style="padding: 10px; background-color: #f5f5f5; border-bottom: 1px solid #ddd; font-weight: bold; color: #333;">
                <i class="fa fa-cogs" style="margin-right: 5px;"></i>
                Thiết bị con của: ${parentData.TEN} (${childCount} thiết bị)
              </div>
            `
            args.detailElement.insertBefore(customHeader, args.detailElement.firstChild)
          }
        }
      }
    },

    onSearch() {
      this.getDanhSachThietBiKhachHang()
    },
    onNew() {
      this.$refs.ModalChiTietThietBiKhachHang.show({}, 'ADD')
    },

    async onChangeToaNha() {
      this.searchForm.matSanId = null
      this.searchForm.phongId = null
      this.searchForm.rackId = null
      this.matSanList = []
      this.phongList = []
      this.rackList = []
      if (this.searchForm.toaNhaId) {
        this.matSanList = await api.getDanhMucMatSanByToaNhaId(this.axios, this.searchForm.toaNhaId)
      }
    },

    async onChangeMatSan() {
      this.searchForm.phongId = null
      this.searchForm.rackId = null
      this.phongList = []
      this.rackList = []
      if (this.searchForm.matSanId) {
        this.phongList = await api.getDanhMucPhongByMatSanId(this.axios, this.searchForm.matSanId)
      }
    },

    async onChangePhong() {
      this.searchForm.rackId = null
      this.rackList = []
      if (this.searchForm.phongId) {
        this.rackList = await api.getDanhMucRackByPhongZone(this.axios, this.searchForm.phongId)
      }
    },

    async onChangeMangThietBi() {
      this.searchForm.loaiThietBiId = null
      this.loaiThietBiList = []
      if (this.searchForm.mangThietBi && this.searchForm.mangThietBi !== -1) {
        this.loaiThietBiList = await api.getDanhMucLoaiThietBiFromMangThietBi(this.axios, this.searchForm.mangThietBi)
      } else {
        this.loaiThietBiList = await api.getDanhMucLoaiThietBi(this.axios)
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

/* Custom styles for child grid */
:deep(.e-detailrow) {
  background-color: #f8f9fa !important;
}

:deep(.e-detailrow .e-grid) {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1) !important;
  border-radius: 4px !important;
  overflow: hidden !important;
}

:deep(.e-detailrow .e-gridheader) {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
}

:deep(.e-detailrow .e-gridheader .e-headercontent) {
  color: white !important;
}

:deep(.e-detailrow .e-gridheader .e-columnheader) {
  background: transparent !important;
  border-bottom: 1px solid rgba(255, 255, 255, 0.2) !important;
}

:deep(.e-detailrow .e-gridheader th) {
  color: white !important;
  font-weight: 500 !important;
}

:deep(.e-detailrow .e-grid .e-content) {
  background-color: white !important;
}

:deep(.e-detailrow .e-row:hover) {
  background-color: #e3f2fd !important;
}

/* Icon for expand/collapse */
:deep(.e-dtdiagonaldown:before) {
  content: '\e7a9' !important;
  color: #2196f3 !important;
}

:deep(.e-dtdiagonalright:before) {
  content: '\e7a8' !important;
  color: #2196f3 !important;
}

/* Custom header for child devices */
.child-device-header {
  padding: 10px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  font-weight: bold;
  border-radius: 4px 4px 0 0;
  margin-bottom: 0;
}
</style>
