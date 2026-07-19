<template src="./index.template.html"> </template>
<script>
import Vue from 'vue'
import moment from 'moment'
import api from './api'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import ChiTietThietBi from './chitiet-thietbi-idc-v2/index.vue'

export default {
  name: 'ThongTinThietBiIDC',
  components: {
    breadcrumb,
    KDataGrid,
    ChiTietThietBi
  },
  data() {
    return {
      header: {
        title: 'Thông tin thiết bị IDC',
        list: [
          {
            name: 'Tài nguyên IDC',
            link: { name: 'Ui.cards' },
            active: false
          },
          {
            name: 'Nghiệp vụ',
            link: { name: 'Ui.cards' },
            active: false
          },
          {
            name: 'Thông tin thiết bị IDC',
            link: { name: 'Ui.buttons' },
            active: true
          }
        ]
      },
      // Form tìm kiếm
      searchForm: {
        idThietBi: '',
        tenThietBi: '',
        mangThietBi: '',
        loaiThietBi: '',
        serial: '',
        tag: '',
        model: '',
        loaiTB: '',
        maTs: '',
        csDinhDanh: '',
        donViQLyId: null,
        heThongTbiId: null,
        idcId: null,
        toaNhaId: null,
        matSanId: null,
        phongId: null,
        rackId: null,
        trangThaiVhId: null,
        congSuatTke: ''
      },
      // Cấp 1: Thiết bị chính (Master)
      listThietBi: [],

      childGrid: {
        dataSource: [],

        // Map theo thuộc tính con THIETBICHA_ID, khớp với ID của thiết bị cha
        queryString: 'THIETBICHA_ID',
        columns: [
          { field: 'ID', headerText: 'ID Thiết bị', textAlign: 'Left', width: 120 },
          { field: 'TEN', headerText: 'Tên thiết bị', textAlign: 'Left' },
          { field: 'SERIAL_NUMBER', headerText: 'Serial', textAlign: 'Left' },
          { field: 'CS_DINHDANH', headerText: 'Công suất tke', textAlign: 'Left' },
          {
            field: 'TOANHA',
            headerText: 'Tòa nhà',
            textAlign: 'Left',
            width: 120
          },
          {
            field: 'MATSAN',
            headerText: 'Mặt sàn',
            textAlign: 'Left',
            width: 120
          },
          { field: 'PHONG', headerText: 'Phòng/Zone', textAlign: 'Left' },
          { field: 'IDC_RACK', headerText: 'Rack', textAlign: 'Left', width: 120 },
          {
            field: 'TRANGTHAI_VH',
            headerText: 'Trạng thái vận hành',
            textAlign: 'Left'
          },
          { field: 'ACTIONS', headerText: 'Thao tác', textAlign: 'Center', template: this.acctionColumn(this) }
        ]
      },

      // Danh muc
      IDCList: [],
      donviQLList: [],
      HTTBList: [],
      toaNhaList: [],
      phongAll: [],
      phongList: [],
      matSanList: [],
      matSanAll: [],
      loaiThietBiList: [],
      rackAll: [],
      rackList: [],
      danhMucIdcList: [],
      trangThaiVanHanhList: [],
      mangThietBiList: [],
      showGrid: true,
      showDetailPage: false,
      detailCurrentMode: 'VIEW',
      isLoadingDetail: false
    }
  },

  watch: {
    'searchForm.mangThietBi': async function(newVal) {
      this.searchForm.loaiThietBi = null
      this.searchForm.loaiThietBiList = []
      if (newVal && Number(newVal) !== -1) {
        await this.loadLoaiThietBi(newVal)
      }
    },
    'searchForm.toaNhaId': async function(newVal) {
      this.searchForm.matSanId = null
      this.searchForm.phongId = null
      this.searchForm.rackId = null
      this.matSanList = []
      this.phongList = []
      this.rackList = []
      if (newVal && Number(newVal) !== -1) {
        this.loadMatSanByToaNhaId(newVal)
      }
    },
    'searchForm.matSanId': async function(newVal) {
      this.searchForm.phongId = null
      this.searchForm.rackId = null
      this.phongList = []
      this.rackList = []
      if (newVal && Number(newVal) !== -1) {
        await this.loadPhongByMatSanId(newVal)
      }
    },
    'searchForm.phongId': async function(newVal) {
      this.searchForm.rackId = null
      this.rackList = []
      if (newVal && Number(newVal) !== -1) {
        await this.loadRackByPhongZoneId(newVal)
      }
    },
    showDetailPage: function(newVal) {
      newVal ? this.showGrid = false : this.showGrid = true
    }
  },
  created: async function() {
    await this.loadForm()
  },

  mounted: async function() {},
  methods: {
     async loadForm() {
      try {
        this.$root.showLoading(true)
        await this.loadDanhMucIdc()
        await this.loadTrangThaiVanHanh()
        await this.loadToaNha()
        await this.loadMatSan()
        await this.loadPhong()
        await this.loadRack()
        await this.loadDonviQL()
        await this.loadHTTB()
        await this.loadMangThietBi()
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra, vui lòng thử lại sau')
      } finally {
        this.$root.showLoading(false)
      }
    },

    async loadDanhSachThietbi() {
      this.showGrid = false
      this.listThietBi = []
      this.childGrid.dataSource = []
      const allDevices = await api.getDanhSachThietBiIDC(this.axios)

      if (Array.isArray(allDevices)) {
        allDevices.forEach((item) => {
          if (item.THIETBICHA_ID === null || item.THIETBICHA_ID === undefined || item.THIETBICHA_ID === '') {
            // Thiết bị cha
            item.THIETBICHA_ID = item.ID
            this.listThietBi.push(item)
          } else {
            // Thiết bị con
            item.THIETBICHA_ID = item.THIETBICHA_ID
            this.childGrid.dataSource.push(item)
          }
        })
      }
      this.showGrid = true
    },
    async loadIDC() {
      this.IDCList = await api.getDanhMucIDC(this.axios)
    },

    async loadDonviQL() {
      this.donviQLList = await api.getDanhMucDonViQuanLy(this.axios)
    },

    async loadHTTB() {
      this.HTTBList = await api.getDanhMucHeThongThietBi(this.axios)
    },

    async loadToaNha(idcId = null) {
      if (idcId !== null && idcId !== undefined) {
        this.toaNhaList = await api.getDanhMucToaNhaByIdcId(this.axios, idcId)
      } else {
        this.toaNhaList = await api.getDanhMucToaNha(this.axios)
      }
    },

    async loadPhong() {
      try {
        const data = await api.getDanhMucPhong(this.axios)

        this.phongAll = data.length
          ? [{ ID: -1, TEN: 'Tất cả', ID_MATSAN: -1 }, ...data.map(x => ({
              ID: x.ID,
              TEN: x.TEN,
              ID_MATSAN: x.ID_MATSAN
            }))]
          : []
      } catch (error) {
        console.error('Error loading phong:', error)
        this.phongAll = []
      }
    },
    async loadPhongByMatSanId(matSanId) {
      try {
        const id = Number(matSanId)
        console.log(id, this.phongAll);

        this.phongList = [
          { ID: -1, TEN: 'Tất cả', ID_MATSAN: -1 },
          ...this.phongAll
            .filter(item => Number(item.ID_MATSAN) === id)
            .map(item => ({
              ID: item.ID,
              TEN: item.TEN,
              ID_MATSAN: item.ID_MATSAN
            }))
        ]

      } catch (error) {
        console.error('Error loading phong by toa nha id:', error)
        this.phongList = []
      }
    },
    async loadMatSan() {
      try {
        const data = await api.getDanhMucMatSan(this.axios)

        this.matSanAll = data.length
          ? [{ ID: -1, TEN: 'Tất cả', TOANHA_ID: -1 }, ...data.map(x => ({
              ID: x.ID,
              TEN: x.TEN,
              TOANHA_ID: x.TOANHA_ID
            }))]
          : []
      } catch (error) {
        console.error('Error loading mat san:', error)
        this.matSanAll = []
      }
    },

    loadMatSanByToaNhaId(matsanId) {
      try {
        const id = Number(matsanId)

        this.matSanList = [
          { ID: -1, TEN: 'Tất cả', TOANHA_ID: -1 },
          ...this.matSanAll
            .filter(item => Number(item.TOANHA_ID) === id)
            .map(item => ({
              ID: item.ID,
              TEN: item.TEN,
              TOANHA_ID: item.TOANHA_ID
            }))
        ]

      } catch (error) {
        console.error('Error loading mat san by toa nha id:', error)
        this.matSanList = []
      }
    },

    async loadLoaiThietBi(mangThietBiId) {
      this.loaiThietBiList = await api.getDanhMucLoaiThietBiFromMangThietBi(this.axios, mangThietBiId)
      this.loaiThietBiList.unshift({ loaitbi_id: -1, loai_tbi: 'Tất cả' })
    },

    async loadRack() {
      try {
        const data = await api.getDanhMucRackByPhongZone(this.axios)

        this.rackAll = data.length
          ? [{ ID: -1, TEN: 'Tất cả', PHONG_ID: -1 }, ...data.map(x => ({
              ID: x.ID,
              TEN: x.TEN,
              PHONG_ID: x.PHONG_ID
            }))]
          : []
      } catch (error) {
        console.error('Error loading rack:', error)
        this.rackAll = []
      }
    },

    loadRackByPhongZoneId(phongId) {
      try {
        const id = Number(phongId)

        this.rackList = [
          { ID: -1, TEN: 'Tất cả', PHONG_ID: -1 },
          ...this.rackAll
            .filter(item => Number(item.PHONG_ID) === id)
            .map(item => ({
              ID: item.ID,
              TEN: item.TEN,
              PHONG_ID: item.PHONG_ID
            }))
        ]

      } catch (error) {
        console.error('Error loading rack by phong id:', error)
        this.rackList = []
      }
    },

    async loadDanhMucIdc() {
      this.danhMucIdcList = await api.getDanhMucIDC(this.axios, this.$root.token)
    },
    async loadTrangThaiVanHanh() {
      try {
        const data = await api.getDanhMucTrangThaiVanHanh(this.axios)
        this.trangThaiVanHanhList = [{ ttvh_id: -1, trangthai_vh: 'Tất cả' }, ...data] || [];
      } catch (error) {
        console.error('Error loading trang thai van hanh:', error)
        this.trangThaiVanHanhList = []
      }
    },
    async loadMangThietBi() {
      this.mangThietBiList = await api.getDanhMucMangThietBi(this.axios)
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
              editItem() {
                parent.editItem(this.data)
              },
              deleteItem() {
                parent.deleteItem(this.data)
              }
            }
          }
        }
      }
    },

    viewItem(item) {
      this.showDetailPage = true
      this.detailCurrentMode = 'VIEW'
      this.$nextTick(() => {
        this.$refs.ChiTietThietBi.show(item, 'VIEW')
      })
    },

    editItem(item) {
      this.showDetailPage = true
      this.detailCurrentMode = 'EDIT'
      this.$nextTick(() => {
        this.$refs.ChiTietThietBi.show(item, 'EDIT')
      })
    },

    deleteItem(item) {
      this.$confirm('Bạn có chắc chắn muốn xóa thiết bị: ' + item.ten, 'Xác nhận', {
        confirmButtonText: 'Xoá',
        cancelButtonText: 'Huỷ'
      }).then(async () => {
        try {
          this.$root.showLoading(true)
          await api.deleteThietBiIDC(this.axios, item.id)
          this.$root.toastSuccess('Xóa thiết bị thành công!')
          await this.loadDanhSachThietbi() // Reload danh sách
        } catch (error) {
          this.$root.toastError(error?.message || 'Có lỗi xảy ra khi xóa thiết bị')
        } finally {
          this.$root.showLoading(false)
        }
      })
    },

    // Xử lý khi detail grid được bind dữ liệu
    onDetailDataBound(args) {
      const detailGrid = args.detailElement.querySelector('.e-grid')
      if (detailGrid) {
        detailGrid.style.border = '1px solid #e0e0e0'
        detailGrid.style.borderRadius = '4px'
        detailGrid.style.margin = '10px'
        detailGrid.style.backgroundColor = '#fafafa'
      }

      // Thêm header cho child grid
      const parentData = args.data
      if (parentData) {
        // Đếm số thiết bị con dựa trên dataSource của childGrid (THIETBICHA_ID -> ID)
        const parentId = parentData.ID
        const childCount = this.childGrid.dataSource.filter((child) => child.THIETBICHA_ID === parentId).length

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

    async onSearch() {
      try {
        this.showGrid = false
        this.$root.showLoading(true)
        this.listThietBi = []
        this.childGrid.dataSource = []
        const searchParams = this.searchForm

        // Loại bỏ các giá trị null/undefined/empty
        Object.keys(searchParams).forEach((key) => {
          const value = searchParams[key]
          if (
            value === null ||
            value === undefined ||
            value === '' ||
            value.toString() === '-1' ||
            (typeof value === 'string' && value.trim() === '')
          ) {
            delete searchParams[key]
          }
        })
        const results = await api.searchThietBiIDC(this.axios, searchParams)

        if (Array.isArray(results)) {
          results.forEach((item) => {
            if (item.THIETBICHA_ID === null || item.THIETBICHA_ID === undefined || item.THIETBICHA_ID === '') {
              // Thiết bị cha
              item.THIETBICHA_ID = item.ID
              this.listThietBi.push(item)
            } else {
              // Thiết bị con
              item.THIETBICHA_ID = item.THIETBICHA_ID
              this.childGrid.dataSource.push(item)
            }
          })
        }
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tìm kiếm')
        console.error(error)
      } finally {
        this.showGrid = true
        this.$root.showLoading(false)
      }
    },
    onNew() {
      const isNoiBo = this.$root.token.getPhanVungID() == 68
      if (isNoiBo) {
        this.showDetailPage = true
        this.detailCurrentMode = 'ADD'
        this.$nextTick(() => {
          this.$refs.ChiTietThietBi.show({}, 'ADD')
        })
      } else {
        this.$root.toastError('Không thuộc nội bộ IDC !')
      }
    },
    onSaveDetail() {
      if (this.$refs.ChiTietThietBi) {
        this.$refs.ChiTietThietBi.onSave()
      }
    },
    onEditDetail() {
      if (this.$refs.ChiTietThietBi) {
        this.$refs.ChiTietThietBi.onEditDetail()
      }
    },
    onAddNewDetail() {
      if (this.$refs.ChiTietThietBi) {
        this.$refs.ChiTietThietBi.onAddNew()
      }
    },
    async onRefreshDetail() {
      this.isLoadingDetail = true
      try {
        if (this.$refs.ChiTietThietBi) {
          await this.$refs.ChiTietThietBi.onRefreshDetail()
        }
      } finally {
        this.isLoadingDetail = false
      }
    },
    async onCancelDetail() {
      if (this.$refs.ChiTietThietBi) {
        await this.$refs.ChiTietThietBi.onCancel()
      }
    },
    onDetailModeChanged(mode) {
      this.detailCurrentMode = mode
    },
    onDetailSaved() {
      this.loadDanhSachThietbi()
    },

    onResetSearch() {
      this.searchForm = {
        idThietBi: '',
        tenThietBi: '',
        mangThietBi: '',
        loaiThietBi: '',
        serial: '',
        tag: '',
        model: '',
        loaiTB: '',
        maTs: '',
        csDinhDanh: '',
        cnQL: '',
        donViQLyId: null,
        heThongTbiId: null,
        idcId: null,
        toaNhaId: null,
        matSanId: null,
        phongId: null,
        rackId: null,
        trangThaiVhId: null,
        congSuatTke: ''
      }
      this.loadDanhSachThietbi() // Load lại toàn bộ dữ liệu
    },

    getDanhMucData() {
      return {
        IDCList: this.IDCList,
        donviQLList: this.donviQLList,
        HTTBList: this.HTTBList,
        toaNhaList: this.toaNhaList,
        phongList: this.phongList,
        matSanList: this.matSanList,
        loaiThietBiList: this.loaiThietBiList,
        rackList: this.rackList,
        danhMucIdcList: this.danhMucIdcList,
        trangThaiVanHanhList: this.trangThaiVanHanhList
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
