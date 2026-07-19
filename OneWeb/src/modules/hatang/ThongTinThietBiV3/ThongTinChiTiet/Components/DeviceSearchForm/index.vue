<template src="./index.template.html"></template>

<script>
import danhMucApi from '../../../api/DanhMuc.api'

export default {
  name: 'DeviceSearchForm',
  data() {
    return {
      searchForm: {
        maThietBi: '',
        tenThietBi: '',
        loaiThietBi: null,
        model: '',
        serial: '',
        tagNumber: '',
        idcId: null,
        toaNhaId: null,
        matSanId: null,
        phongId: null,
        rackId: null,
        hieuLuc: null,
        congSuatTke: ''
      },
      // Data lists
      loaiThietBiList: [],
      toaNhaList: [],
      idcList: [],
      matSanList: [],
      phongList: [],
      rackList: [],
      hieuLucList: []
    }
  },
  computed: {
    loaiThietBiDataSource() {
      return [{ ID: -1, TEN: 'Tất cả' }, ...this.loaiThietBiList]
    },
    toaNhaDataSource() {
      return [{ ID_TOANHA: 0, TEN: 'Tất cả' }, ...this.toaNhaList]
    },
    idcDataSource() {
      return [{ ID: 0, TEN: 'Tất cả' }, ...this.idcList]
    }
  },
  watch: {
    'searchForm.idcId'(newVal) {
      if (newVal) {
        this.loadToaNha(newVal)
      } else {
        this.idcList = []
        this.searchForm.idcId = null
      }
    },
    'searchForm.toaNhaId'(newVal) {
      if (newVal && newVal !== 0) {
        this.loadMatSan(newVal)
        // Reset dependent fields
        this.searchForm.matSanId = null
        this.searchForm.phongId = null
        this.searchForm.rackId = null
      } else {
        this.matSanList = []
        this.phongList = []
        this.rackList = []
        this.searchForm.matSanId = null
        this.searchForm.phongId = null
        this.searchForm.rackId = null
      }
    },
    'searchForm.matSanId'(newVal) {
      if (newVal) {
        this.loadPhong(newVal)
        // Reset dependent fields
        this.searchForm.phongId = null
        this.searchForm.rackId = null
      } else {
        this.phongList = []
        this.rackList = []
        this.searchForm.phongId = null
        this.searchForm.rackId = null
      }
    },
    'searchForm.phongId'(newVal) {
      if (newVal) {
        this.loadRack(newVal)
        // Reset dependent field
        this.searchForm.rackId = null
      } else {
        this.rackList = []
        this.searchForm.rackId = null
      }
    }
  },
  async created() {
    await this.loadInitialData()
  },
  methods: {
    async loadInitialData() {
      try {
        this.$root.showLoading(true)
        await Promise.all([this.loadIDC(), this.loadLoaiThietBi(), this.loadHieuLuc()])
      } catch (error) {
        this.$root.toastError('Có lỗi xảy ra khi tải dữ liệu danh mục')
      } finally {
        this.$root.showLoading(false)
      }
    },
    async loadLoaiThietBi() {
      try {
        const response = await danhMucApi.getDeviceType(this.$root.context)
        this.loaiThietBiList = response || []
      } catch (error) {
        console.error('Error loading device types:', error)
      }
    },
    async loadToaNha(idcId) {
      try {
        const response = await danhMucApi.getIDCBuilding(this.$root.context, idcId)
        this.toaNhaList = response || []
      } catch (error) {
        console.error('Error loading toa nhas:', error)
      }
    },
    async loadIDC() {
      try {
        const response = await danhMucApi.getIDC(this.$root.context)
        this.idcList = response || []
      } catch (error) {
        console.error('Error loading IDCs:', error)
      }
    },
    async loadMatSan(buildingId) {
      try {
        const response = await danhMucApi.getIDCFloor(this.$root.context, buildingId)
        this.matSanList = response || []
      } catch (error) {
        console.error('Error loading floors:', error)
      }
    },
    async loadPhong(floorId) {
      try {
        const response = await danhMucApi.getIDCZone(this.$root.context, floorId)
        this.phongList = response || []
      } catch (error) {
        console.error('Error loading zones:', error)
      }
    },
    async loadRack(zoneId) {
      try {
        const response = await danhMucApi.getIDCRack(this.$root.context, zoneId)
        this.rackList = response || []
      } catch (error) {
        console.error('Error loading racks:', error)
      }
    },
    async loadHieuLuc() {
      try {
        const response = await danhMucApi.getUsageStatusList(this.$root.context)
        this.hieuLucList = response || []
      } catch (error) {
        console.error('Error loading hieu luc:', error)
      }
    },
    resetForm() {
      // Reset form to initial state
      this.searchForm = {
        maThietBi: '',
        tenThietBi: '',
        loaiThietBi: null,
        model: '',
        serial: '',
        tagNumber: '',
        idcId: null,
        toaNhaId: null,
        matSanId: null,
        phongId: null,
        rackId: null,
        hieuLuc: null,
        congSuatTke: ''
      }

      // Reset dependent lists
      this.matSanList = []
      this.phongList = []
      this.rackList = []
    },
    // Public method to get current search form data
    getSearchForm() {
      return { ...this.searchForm }
    },
    // Public method to set search form data
    setSearchForm(data) {
      this.searchForm = { ...this.searchForm, ...data }
    }
  }
}
</script>

<style scoped>
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

.btn {
  padding: 8px 16px;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  border: 1px solid transparent;
}

.btn-primary {
  background-color: #007bff;
  border-color: #007bff;
  color: white;
}

.btn-primary:hover:not(:disabled) {
  background-color: #0056b3;
  border-color: #0056b3;
}

.btn-secondary {
  background-color: #6c757d;
  border-color: #6c757d;
  color: white;
}

.btn-secondary:hover:not(:disabled) {
  background-color: #545b62;
  border-color: #545b62;
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.mr-1 {
  margin-right: 5px;
}

.mr-2 {
  margin-right: 10px;
}

.mt-3 {
  margin-top: 20px;
}

.text-center {
  text-align: center;
}
</style>
