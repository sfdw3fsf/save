<template src="./index.template.html"></template>
<style src="./styles.scss" scoped></style>
<script>
import thietBiMauApi from './api/ThietBi.api.js'
import ThietBiMauSearchForm from './ThietBiMauSearchForm/index.vue'
import { getDataCols } from './config'

export default {
  name: 'QuanLyThietBiMau',
  components: {
    ThietBiMauSearchForm
  },
  data() {
    return {
      showGrid: true,
      loading: false,
      dataSource: [],
      listThietBiMau: [],
      searchParams: {}
    }
  },
  computed: {
    getDataCols() {
      return getDataCols(this)
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
    await this.loadData()
  },
  methods: {
    onRecordDoubleClick(args) {
      this.onView(args.rowData)
    },
    onView(row) {
      this.$root.toastInfo(`Xem chi tiết: ${row.tenChungLoaiTB}`)
      // TODO: Navigate to detail view
      // this.$router.push(`/ha-tang/thiet-bi-mau/${row.id}`)
    },
    onEdit(row) {
      this.$root.toastInfo(`Sửa thiết bị mẫu: ${row.tenChungLoaiTB}`)
      // TODO: Navigate to edit form
      // this.$router.push(`/ha-tang/thiet-bi-mau/${row.id}/edit`)
    },
    async loadData() {
      try {
        this.showGrid = false
        await this.getDanhSachThietBiMauApi(this.searchParams)
        this.processData()
      } catch (error) {
        console.error(error)
        this.$root.toastError('Có lỗi xảy ra khi tải danh sách thiết bị mẫu')
      } finally {
        this.showGrid = true
      }
    },
    async getDanhSachThietBiMauApi(searchParams = {}) {
      try {
        const response = await thietBiMauApi.getDanhSachThietBiMau(searchParams)
        this.dataSource = response
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu thiết bị mẫu')
      }
    },
    processData() {
      this.listThietBiMau = []
      if (Array.isArray(this.dataSource)) {
        this.listThietBiMau = [...this.dataSource]
      }
    },
    onAddNew() {
      this.$root.toastInfo('Chức năng thêm mới đang được phát triển')
      // TODO: Navigate to add form
      // this.$router.push('/ha-tang/thiet-bi-mau/add')
    },
    onDelete(row) {
      if (!row) {
        const selectedRow = this.$refs.grdItems?.getSelectedRows()
        if (selectedRow && selectedRow.length > 0) {
          row = selectedRow[0]
        } else {
          this.$root.toastWarning('Vui lòng chọn thiết bị mẫu cần xóa')
          return
        }
      }
      this.$confirm('Bạn có chắc chắn muốn xóa thiết bị mẫu này?', 'Xác nhận', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning',
        dangerouslyUseHTMLString: true
      }).then(async (result) => {
        if (result) {
          try {
            await thietBiMauApi.deleteThietBiMau(row.id)
            this.$root.toastSuccess('Xóa thiết bị mẫu thành công')
            this.refresh()
          } catch (error) {
            this.$root.toastError(error?.message || 'Xóa thiết bị mẫu thất bại')
          }
        }
      })
    },
    // Search functionality
    async onSearch() {
      if (this.loading) return

      // Get search form data from the search form component
      const searchParams = this.$refs.thietBiMauSearchForm.getSearchForm()
      this.searchParams = { ...searchParams }
      await this.searchThietBiMau()
    },
    async onResetSearch() {
      if (this.loading) return

      // Reset search form
      this.$refs.thietBiMauSearchForm.resetForm()
      this.searchParams = {}
      await this.loadData()
    },
    async searchThietBiMau() {
      try {
        this.loading = true
        this.showGrid = false
        const queryParams = this.buildSearchQueryParams()
        await this.getDanhSachThietBiMauApi(queryParams)
        this.processData()
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tìm kiếm thiết bị mẫu')
      } finally {
        this.loading = false
        this.showGrid = true
      }
    },
    buildSearchQueryParams() {
      const params = {}

      // Add search parameters if they have values
      if (this.searchParams.maChungLoaiTB) {
        params.maChungLoaiTB = this.searchParams.maChungLoaiTB
      }
      if (this.searchParams.tenChungLoaiTB) {
        params.tenChungLoaiTB = this.searchParams.tenChungLoaiTB
      }
      if (this.searchParams.congSuatDinhDanh) {
        params.congSuatDinhDanh = this.searchParams.congSuatDinhDanh
      }
      if (this.searchParams.hangSanXuat) {
        params.hangSanXuat = this.searchParams.hangSanXuat
      }
      if (this.searchParams.mangThietBi) {
        params.mangThietBi = this.searchParams.mangThietBi
      }
      if (this.searchParams.loaiThietBi) {
        params.loaiThietBi = this.searchParams.loaiThietBi
      }
      if (this.searchParams.phanLoaiThietBi) {
        params.phanLoaiThietBi = this.searchParams.phanLoaiThietBi
      }

      return params
    },
    async refresh() {
      await this.loadData()
    }
  }
}
</script>
