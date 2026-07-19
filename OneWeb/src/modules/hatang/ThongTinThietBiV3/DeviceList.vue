<template src="./DeviceList.template.html"></template>
<style src="./styles.scss" scoped></style>
<script>
import thietBiApi from './api/ThietBi.api'
import { DeviceSearchForm } from './ThongTinChiTiet/Components'
import { getDataCols, getChildGridColumns } from './config'
import { getDeviceUrls } from './const'

export default {
  name: 'DeviceList',
  components: {
    DeviceSearchForm
  },
  props: {
    deviceTypeId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      showGrid: true,
      loading: false,
      dataSource: [],
      listThietBi: [],
      searchParams: {},
      childGrid: {
        dataSource: [],
        queryString: 'thietbicha_id',
        columns: getChildGridColumns(this)
      }
    }
  },
  computed: {
    getDataCols() {
      return getDataCols(this)
    },
    deviceUrls() {
      return getDeviceUrls(this.deviceTypeId)
    }
  },
  watch: {
    showGrid(newVal) {
      if (!newVal) {
        this.$root.showLoading(true)
      } else {
        this.$root.showLoading(false)
      }
    }
  },
  async created() {
    await this.loadDevicesByTypeId(this.deviceTypeId)
  },
  methods: {
    onRecordDoubleClick(args) {
      this.onView(args.rowData)
    },
    onView(row) {
      const urls = getDeviceUrls(row.loaithietbi_id)
      if (!urls || !urls.detailUrl) {
        this.$root.toastWarning('Không tìm thấy loại thiết bị')
        return
      }
      this.$router.push(`${urls.detailUrl}?id=${row.id}`)
    },
    onEdit(row) {
      const urls = getDeviceUrls(row.loaithietbi_id)
      if (!urls || !urls.detailUrl) {
        this.$root.toastWarning('Không tìm thấy loại thiết bị')
        return
      }
      this.$router.push(`${urls.detailUrl}?id=${row.id}&mode=EDIT`)
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
        await this.loadDevicesByTypeId(this.deviceTypeId)
      } catch (error) {
        console.error(error)
      } finally {
        this.showGrid = true
      }
    },
    async getDanhSachThietBiApi(searchParams = {}) {
      try {
        const response = await thietBiApi.getDanhSachThietBi(this.$root.context, searchParams)
        this.dataSource = response
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu thiết bị')
      }
    },
    onAddNew() {
      if (!this.deviceUrls || !this.deviceUrls.detailUrl) {
        this.$root.toastWarning('Không tìm thấy URL điều hướng')
        return
      }
      this.$router.push(this.deviceUrls.detailUrl)
    },
    onDelete(row) {
      if (!row) {
        const selectedRow = this.$refs.grdItems?.getSelectedRows()
        if (selectedRow && selectedRow.length > 0) {
          row = selectedRow[0]
        } else {
          this.$root.toastWarning('Vui lòng chọn thiết bị cần xóa')
          return
        }
      }
      this.$confirm('Bạn có chắc chắn muốn xóa thiết bị này?', 'Xác nhận', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning',
        dangerouslyUseHTMLString: true
      }).then(async (result) => {
        if (result) {
          try {
            await this.deleteDevice(row.id)
            this.$root.toastSuccess('Xóa thiết bị thành công')
            this.refresh()
          } catch (error) {
            this.$root.toastError('Xóa thiết bị thất bại')
          }
        }
      })
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
        const queryParams = this.buildSearchQueryParams()
        // Luôn filter theo deviceTypeId từ prop
        queryParams.loaiThietBi = this.deviceTypeId
        await this.getDanhSachThietBiApi(queryParams)
        this.processDeviceData()
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tìm kiếm thiết bị')
      } finally {
        this.loading = false
        this.showGrid = true
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
          // Xử lý theo response mới: thietbicha_id có thể là null hoặc có giá trị
          if (
            item.thietbicha_id === null ||
            item.thietbicha_id === undefined ||
            item.thietbicha_id === '' ||
            item.thietbicha_id === item.id
          ) {
            // Thiết bị cha (không có thiết bị cha hoặc tự trỏ về chính nó)
            if (!item.thietbicha_id || item.thietbicha_id === item.id) {
              item.thietbicha_id = item.id
            }
            this.listThietBi.push(item)
          } else {
            // Thiết bị con (có thiết bị cha khác với id của nó)
            this.childGrid.dataSource.push(item)
          }
        })
      }
    },
    // Get danh sách thiết bị với param theo loại thiết bị ID
    async loadDevicesByTypeId(deviceTypeId) {
      try {
        this.showGrid = false
        const queryParams = {
          loaiThietBi: deviceTypeId
        }
        await this.getDanhSachThietBiApi(queryParams)
        this.processDeviceData()
      } catch (error) {
        console.error('Error loading devices by type:', error)
        this.$root.toastError('Có lỗi xảy ra khi tải danh sách thiết bị theo loại')
      } finally {
        this.showGrid = true
      }
    },
    async refresh() {
      await this.loadDevicesByTypeId(this.deviceTypeId)
    }
  }
}
</script>
