<template src="./index.template.html"></template>

<script>
import danhMucApi from '../api/DanhMuc.api'

export default {
  name: 'ThietBiMauSearchForm',
  data() {
    return {
      searchForm: {
        maChungLoaiTB: '',
        tenChungLoaiTB: '',
        congSuatDinhDanh: '',
        hangSanXuat: null,
        mangThietBi: null,
        loaiThietBi: null,
        phanLoaiThietBi: null
      },
      // Data lists
      hangSanXuatList: [],
      mangThietBiList: [],
      loaiThietBiList: [],
      phanLoaiThietBiList: []
    }
  },
  methods: {
    async loadInitialData() {
      try {
        this.$root.showLoading(true)
        await Promise.all([
          this.loadHangSanXuat(),
          this.loadMangThietBi()
        ])
      } catch (error) {
        this.$root.toastError('Có lỗi xảy ra khi tải dữ liệu danh mục')
      } finally {
        this.$root.showLoading(false)
      }
    },
    async loadHangSanXuat() {
      try {
        const response = await danhMucApi.getManufacturerList(this.$root.context)
        this.hangSanXuatList = response || []
      } catch (error) {
        console.error('Error loading manufacturers:', error)
      }
    },
    async loadMangThietBi() {
      try {
        const response = await danhMucApi.getMangThietBi(this.$root.context)
        this.mangThietBiList = response || []
      } catch (error) {
        console.error('Error loading mang thiet bi:', error)
      }
    },
    async loadLoaiThietBi(mangThietBiId) {
      try {
        if (!mangThietBiId) {
          this.loaiThietBiList = []
          return
        }
        const response = await danhMucApi.getLoaiThietBiByMangThietBi(this.$root.context, mangThietBiId)
        this.loaiThietBiList = response || []
      } catch (error) {
        console.error('Error loading loai thiet bi:', error)
      }
    },
    async loadPhanLoaiThietBi(loaiThietBiId) {
      try {
        if (!loaiThietBiId) {
          this.phanLoaiThietBiList = []
          return
        }
        const response = await danhMucApi.getPhanLoaiTB(this.$root.context, loaiThietBiId)
        this.phanLoaiThietBiList = response || []
      } catch (error) {
        console.error('Error loading phan loai thiet bi:', error)
      }
    },
    resetForm() {
      this.searchForm = {
        maChungLoaiTB: '',
        tenChungLoaiTB: '',
        congSuatDinhDanh: '',
        hangSanXuat: null,
        mangThietBi: null,
        loaiThietBi: null,
        phanLoaiThietBi: null
      }
      // Reset dependent lists
      this.loaiThietBiList = []
      this.phanLoaiThietBiList = []
    },
    getSearchForm() {
      return { ...this.searchForm }
    },
    setSearchForm(data) {
      this.searchForm = { ...this.searchForm, ...data }
    }
  },
  watch: {
    'searchForm.mangThietBi'(newVal) {
      if (newVal) {
        this.loadLoaiThietBi(newVal)
      } else {
        this.loaiThietBiList = []
        this.phanLoaiThietBiList = []
        this.searchForm.loaiThietBi = null
        this.searchForm.phanLoaiThietBi = null
      }
    },
    'searchForm.loaiThietBi'(newVal) {
      if (newVal) {
        this.loadPhanLoaiThietBi(newVal)
      } else {
        this.phanLoaiThietBiList = []
        this.searchForm.phanLoaiThietBi = null
      }
    }
  },
  async created() {
    await this.loadInitialData()
  }
}
</script>

<style scoped>
.device-template-search-form {
  margin-bottom: 15px;
}

.box-form {
  background: #fff;
  border: 1px solid #e0e0e0;
  padding: 10px;
}

.info-row {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.info-row .key {
  font-weight: 500;
  color: #555;
  margin-right: 10px;
  min-width: 130px;
}

.info-row .value {
  flex: 1;
}

.w130 {
  width: 130px;
}

.form-control {
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 8px 12px;
  font-size: 14px;
}

.form-control:focus {
  border-color: #007bff;
  box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
}
</style>
