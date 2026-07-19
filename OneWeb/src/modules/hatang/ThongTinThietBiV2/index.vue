<template src="./index.template.html"></template>
<style src="./styles.scss" scoped></style>
<script>
import thietBiApi from './api/ThietBi.api'
import danhMucApi from './api/DanhMuc.api'
import ThongTinChiTietThietBi from './ThongTinChiTiet/index.vue'
import { DeviceSearchForm } from './ThongTinChiTiet/Components'
import { getDataCols, getChildGridColumns } from './config'
import { NEW_DEFAULT_DEVICE, PORT_TYPE } from './const'
import moment from 'moment'

export default {
  name: 'ThongTinThietBiV2',
  components: {
    ThongTinChiTietThietBi,
    DeviceSearchForm
  },
  data() {
    return {
      showGrid: true,
      loading: false,
      deviceTypeList: [],
      dataSource: [],
      listThietBi: [],
      searchParams: {},
      defaultDevice: { ...NEW_DEFAULT_DEVICE },
      childGrid: {
        dataSource: [],
        queryString: 'thietbicha_id',
        columns: getChildGridColumns(this)
      }
    }
  },
  provide() {
    return {
      setCurrentItemValByProp: this.setCurrentItemValByProp,
      createCardModule: this.createCardModule,
      updateCardModule: this.updateCardModule,
      deleteCardItem: this.deleteCardItem,
      enableControls: this.enableControls,
      setDefaultItem: this.setDefaultItem,
      getDefaultDevice: this.getDefaultDevice
    }
  },

  async created() {
    await this.loadDeviceData()
    await this.getDeviceTypeApi()
  },
  computed: {
    getDataCols() {
      return getDataCols(this)
    }
  },
  watch: {
    showGrid(newVal) {
      if (!newVal) {
        // Bật loading khi showGrid = false
        this.$root.showLoading(true)
      } else {
        // Tắt loading khi showGrid = true
        this.$root.showLoading(false)
      }
    }
  },
  methods: {
    onRecordDoubleClick(args) {
      this.onView(args.rowData)
    },
    onView(row) {
      this.$refs.ThongTinChiTietThietBi.show(row, 'VIEW')
    },
    onEdit(row) {
      this.currentItem = row
      this.$refs.ThongTinChiTietThietBi.show(row, 'EDIT')
    },
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
        const parentId = parentData.id
        const childCount = this.childGrid.dataSource.filter((child) => child.thietbicha_id === parentId).length

        if (childCount > 0) {
          const headerElement = args.detailElement.querySelector('.e-gridheader')
          if (headerElement) {
            const customHeader = document.createElement('div')
            customHeader.innerHTML = `
              <div style="padding: 10px; background-color: #f5f5f5; border-bottom: 1px solid #ddd; font-weight: bold; color: #333;">
                <i class="fa fa-cogs" style="margin-right: 5px;"></i>
                Thiết bị con của: ${parentData.ten || 'N/A'} (${childCount} thiết bị)
              </div>
            `
            args.detailElement.insertBefore(customHeader, args.detailElement.firstChild)
          }
        }
      }
    },

    async loadDeviceData() {
      try {
        this.showGrid = false
        await this.getDanhSachThietBiApi()
        this.processDeviceData()
      } catch (error) {
        console.error(error)
      } finally {
        this.showGrid = true
      }
    },
    async getDanhSachThietBiApi() {
      try {
        const response = await thietBiApi.getDanhSachThietBi(this.$root.context)
        this.dataSource = response
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu thiết bị')
      }
    },
    async getDeviceTypeApi() {
      try {
        const response = await danhMucApi.getDeviceType(this.$root.context)
        this.deviceTypeList = response
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu loại thiết bị')
      }
    },

    // Action bar methods
    onAddNew() {
      // Create new device using default object
      this.setDefaultItem()
      this.$refs.ThongTinChiTietThietBi.show(this.getDefaultDevice(), 'ADD')
    },

    // Device creation methods based on old version
    setDefaultItem() {
      // Reset device data to default values
      this.defaultDevice = { ...NEW_DEFAULT_DEVICE }
      console.log('Default device created:', this.defaultDevice)
    },

    getDefaultDevice() {
      return this.defaultDevice || { ...NEW_DEFAULT_DEVICE }
    },

    enableControls(enabled) {
      // This method will be called by child components to enable/disable form controls
      // The actual implementation depends on how controls are managed in child components
      console.log('Controls enabled:', enabled)
    },

    async onSaveDevice(deviceData) {
      try {
        this.loading = true
        this.$root.showLoading(true)
        // Call API to create/update device
        const result = await thietBiApi.capnhatThietBi(this.$root.context, deviceData)

        if (result) {
          this.$refs.ThongTinChiTietThietBi.closeModal()
          this.$root.toastSuccess('Lưu thiết bị thành công')
          // Reload device list after successful save
          await this.loadDeviceData()
          return result
        }
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi lưu thiết bị')
        return null
      } finally {
        this.loading = false
        this.$root.showLoading(false)
      }
    },

    async onSaveCardModule(cardModuleData) {
      try {
        this.loading = true
        this.$root.showLoading(true)

        // Call API to create card module
        const result = await thietBiApi.capnhatThietBi(this.$root.context, cardModuleData)

        if (result) {
          this.$root.toastSuccess('Lưu card/module thành công')
          await this.loadDeviceData()
          await this.$nextTick()
          await this.$refs.ThongTinChiTietThietBi.getCardList(this.currentItem.id)
          return result
        }
      } catch (error) {
        console.log(error)

        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi lưu card/module')
        return null
      } finally {
        this.loading = false
        this.$root.showLoading(false)
      }
    },

    onCancel() {
      // Cancel current operation
      console.log('Cancel action triggered')
    },

    resetCurrentItem() {
      this.currentItem = { ...NEW_DEFAULT_DEVICE }
    },

    onDelete() {
      // Delete selected device
      const selectedRow = this.$refs.grdItems?.getSelectedRows()
      if (selectedRow && selectedRow.length > 0) {
        if (confirm('Bạn có chắc chắn muốn xóa thiết bị này?')) {
          console.log('Delete device:', selectedRow[0])
          // Implement delete logic here
        }
      } else {
        this.$root.toastWarning('Vui lòng chọn thiết bị cần xóa')
      }
    },

    // Search functionality
    async onSearch() {
      if (this.loading) return

      // Get search form data from the search form component
      const searchParams = this.$refs.deviceSearchForm.getSearchForm()
      this.searchParams = { ...searchParams }
      await this.searchDevices()
    },

    async onResetSearch() {
      if (this.loading) return

      // Reset search form
      this.$refs.deviceSearchForm.resetForm()
      this.searchParams = {}
      await this.loadDeviceData()
    },

    async searchDevices() {
      try {
        this.loading = true
        this.showGrid = false

        // Call API with search parameters
        await this.getDanhSachThietBiApiWithSearch()
        this.processDeviceData()
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tìm kiếm thiết bị')
      } finally {
        this.loading = false
        this.showGrid = true
      }
    },

    async getDanhSachThietBiApiWithSearch() {
      try {
        // Build query parameters from search form
        const queryParams = this.buildSearchQueryParams()
        const response = await thietBiApi.getDanhSachThietBiWithSearch(this.$root.context, queryParams)
        this.dataSource = response
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu thiết bị')
      }
    },

    buildSearchQueryParams() {
      const params = {}

      // Add search parameters if they have values
      if (this.searchParams.maThietBi) {
        params.maThietBi = this.searchParams.maThietBi
      }
      if (this.searchParams.tenThietBi) {
        params.tenThietBi = this.searchParams.tenThietBi
      }
      if (this.searchParams.loaiThietBi && this.searchParams.loaiThietBi > 0) {
        params.loaiThietBi = this.searchParams.loaiThietBi
      }
      if (this.searchParams.model) {
        params.model = this.searchParams.model
      }
      if (this.searchParams.serial) {
        params.serial = this.searchParams.serial
      }
      if (this.searchParams.tagNumber) {
        params.tagNumber = this.searchParams.tagNumber
      }
      if (this.searchParams.idcId && this.searchParams.idcId > 0) {
        params.idcId = this.searchParams.idcId
      }
      if (this.searchParams.matSanId && this.searchParams.matSanId > 0) {
        params.matSanId = this.searchParams.matSanId
      }
      if (this.searchParams.phongId && this.searchParams.phongId > 0) {
        params.phongId = this.searchParams.phongId
      }
      if (this.searchParams.rackId && this.searchParams.rackId > 0) {
        params.rackId = this.searchParams.rackId
      }
      if (this.searchParams.hieuLuc) {
        params.hieuLuc = this.searchParams.hieuLuc
      }
      if (this.searchParams.congSuatTke) {
        params.congSuatTke = this.searchParams.congSuatTke
      }

      return params
    },

    processDeviceData() {
      this.listThietBi = []
      this.childGrid.dataSource = []

      if (Array.isArray(this.dataSource)) {
        this.dataSource.forEach((item) => {
          if (item.thietbicha_id === null || item.thietbicha_id === undefined || item.thietbicha_id === '') {
            // Thiết bị cha
            item.thietbicha_id = item.id
            this.listThietBi.push(item)
          } else {
            // Thiết bị con
            item.thietbicha_id = item.thietbicha_id
            this.childGrid.dataSource.push(item)
          }
        })
      }
    },
    // ================= PROVIDE/INJECT ============
    setCurrentItemValByProp(prop, value) {
      this.currentItem[prop] = value
    },
    async createCardModule(type) {
      try {
        if(!this.currentItem || !this.currentItem.ten) {
          this.$root.toastWarning('Thiết bị cha cần được khởi tạo trước!')
          return
        }
        // Tạo card module options
        const cardModuleOptions = {
          thietbicha_id: this.currentItem.id,
          portType: type,
          isCardModuleMode: true
        }

        // Mở ThongTinChiTiet với chế độ thêm card/module
        this.$refs.ThongTinChiTietThietBi.show(this.currentItem, 'ADD', cardModuleOptions)
      } catch (error) {
        console.log(error)
        this.$root.toastError('Lỗi khởi tạo card hoặc module')
      }
    },

    async updateCardModule(item) {
      try {
        this.$refs.modalThemCard.updateCard(item)
      } catch (error) {
        this.$root.toastError('Lỗi cập nhật card hoặc module')
      }
    },
    async deleteCardItem(item) {
      let flag = await this.$confirm('Có chắc bạn muốn xóa dữ liệu đang chọn?', 'Xác nhận thực hiện', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info',
        dangerouslyUseHTMLString: true
      })
        .then((s) => true)
        .catch((e) => false)
      if (flag) {
        try {
          let rs = true
          if (item.id) {
            rs = this.deleteItems([item.id])
            this.dataSource = this.dataSource.filter((el) => {
              return el.id != item.id
            })
          }
          if (!rs) throw new Error('Lỗi api')
          this.$root.toastSuccess('Xóa thông tin thành công')
        } catch (error) {
          this.$root.toastError('Lỗi không xóa được thông tin')
        }
      }
    }
  }
}
</script>
