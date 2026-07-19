<template src="./index.html"></template>

<script>
import api from './api';
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
export default {
  name: 'ModalChonRack',
  props: {},
  data: function() {
    return {
      selectedRack: null,

      // Search criteria
      searchCriteria: {
        idc: '',
        toaNha: '',
        tang: '',
        matSan: '',
        phongZone: '',
        tenRack: ''
      },

      // Dropdown options (populated from API data)
      idcOptions: [],
      toaNhaOptions: [],
      tangOptions: [],
      matSanOptions: [],
      phongZoneOptions: [],
      tenRackOptions: [],

      // Rack list data
      rackList: [],
      allRacks: [],
      loading: false
    }
  },
  components: {},
  computed: {},
  watch: {
    'searchCriteria.idc': function() {
      this.applyCascadingFilters()
    },
    'searchCriteria.matSan': function() {
      this.applyCascadingFilters()
    }
  },
  created: async function() {
    // No-op. Data loads when modal is shown
  },
  methods: {
    // ===== Utils =====
    _toText(value) {
      return (value || '').toString().trim()
    },
    _toOption(value) {
      const text = this._toText(value)
      return text ? { text, value: text } : null
    },
    _uniqueOptions(items, key) {
      const seen = new Set()
      const out = []
      for (const it of items || []) {
        const text = this._toText(it[key])
        if (!text || seen.has(text)) continue
        seen.add(text)
        out.push({ text, value: text })
      }
      return out
    },
    _ci(v) {
      return this._toText(v).toLowerCase()
    },
    _getDonViId() {
      return this.$root.token.getDonViID()
    },
    async _postDanhMucCommon(loaiDanhMuc, thamSo1 = null) {
      const payload = { loaiDanhMuc }
      if (thamSo1 !== null && thamSo1 !== undefined) payload.thamSo1 = thamSo1
      const rs = await this.$root.context.post('/web-ecms/idc/danh-muc/common', payload)
      return rs && rs.data ? rs.data : []
    },

    show() {
      this.$bvModal.show('ModalChonRack')
      this.loadDefaultData()
    },
    closeModal() {
      this.$bvModal.hide('ModalChonRack')
      this.selectedRack = null
    },

    async loadDefaultData() {
      // Load initial rack data and dropdown options when modal opens
      try {
        this.fetchRacks()
        this.loadDropdowns()
      } catch (error) {
        this.$toast.error('Có lỗi khi lấy dữ liệu ')
      }
    },

    async loadDropdowns() {
      try {
        // IDC (tham khảo QL thiết bị IDC): IDC-DS-IDC + DONVI_ID từ token
        this.idcOptions = await this._postDanhMucCommon('IDC-DS-IDC', this._getDonViId())

        // Toà nhà
        this.toaNhaOptions = await this._postDanhMucCommon('IDC-TOANHA')

        this.matSanOptions = await this._postDanhMucCommon('IDC-MATSAN')

        // Phòng/Zone
        this.phongZoneOptions = await this._postDanhMucCommon('IDC-PHONG')

        // Tầng và Tên Rack lấy từ dữ liệu rack hiện có
        this._refreshDerivedOptionsFromRacks()
      } catch (e) {
        const msg = e?.response?.data?.message_detail || e?.message || 'Không tải được danh mục tìm kiếm'
        this.$toast.error(msg)
      }
    },

    async fetchRacks() {
      try {
        this.$root.showLoading(true)
        const rs = await this.$root.context.post('/web-ecms/thongtin-rack/get-tt-rack')
        const data = rs && rs.data ? rs.data : []
        this.allRacks = Array.isArray(data) ? data : []
        this.rackList = this.allRacks

        // Populate quick options from racks where possible
      } catch (e) {
        const msg = e?.response?.data?.message_detail || e?.message || 'Không tải được danh sách rack'
        this.$toast.error(msg)
        this.allRacks = []
        this.rackList = []
      } finally {
        this.$root.showLoading(false)
      }
    },

    _refreshDerivedOptionsFromRacks() {
      // Build Tầng and Tên Rack options from rack data
      this.tangOptions = this._uniqueOptions(this.allRacks, 'TANG')
      this.tenRackOptions = this._uniqueOptions(this.allRacks, 'TEN_RACK')
    },

    _resetDependentCriteriaOnIdcChange() {
      this.searchCriteria.matSan = ''
      this.searchCriteria.phongZone = ''
    },

    _resetDependentCriteriaOnMatSanChange() {
      this.searchCriteria.phongZone = ''
    },

    applyCascadingFilters() {
      const ci = this._ci

      if (this.searchCriteria.idc) {
        this._resetDependentCriteriaOnIdcChange()
        const matsans = (this.allRacks || [])
          .filter((r) => ci(r.TEN_IDC) === ci(this.searchCriteria.idc))
          .map((r) => (r.TEN_MATSAN || '').toString().trim())
          .filter(Boolean)
        const uniqMs = Array.from(new Set(matsans))
        this.matSanOptions = uniqMs.map((v) => ({ text: v, value: v }))
      }

      if (this.searchCriteria.matSan) {
        this._resetDependentCriteriaOnMatSanChange()
        const phongs = (this.allRacks || [])
          .filter((r) => !this.searchCriteria.idc || ci(r.TEN_IDC) === ci(this.searchCriteria.idc))
          .filter((r) => ci(r.TEN_MATSAN) === ci(this.searchCriteria.matSan))
          .map((r) => (r.TEN_PHONG || '').toString().trim())
          .filter(Boolean)
        const uniqP = Array.from(new Set(phongs))
        this.phongZoneOptions = uniqP.map((v) => ({ text: v, value: v }))
      }
    },

    searchRacks() {
      // Client-side filter from allRacks based on search criteria
      const ci = this._ci
      let filteredRacks = (this.allRacks || []).filter((rack) => {
        let ok = true
        if (this.searchCriteria.idc) {
          ok = ok && ci(rack.TEN_IDC) === ci(this.searchCriteria.idc)
        }
        if (this.searchCriteria.toaNha) {
          ok = ok && ci(rack.TOANHA) === ci(this.searchCriteria.toaNha)
        }
        if (this.searchCriteria.tang) {
          ok = ok && ci(rack.TANG) === ci(this.searchCriteria.tang)
        }
        if (this.searchCriteria.matSan) {
          ok = ok && ci(rack.TEN_MATSAN) === ci(this.searchCriteria.matSan)
        }
        if (this.searchCriteria.phongZone) {
          ok = ok && ci(rack.TEN_PHONG) === ci(this.searchCriteria.phongZone)
        }
        if (this.searchCriteria.tenRack) {
          ok = ok && ci(rack.TEN_RACK) === ci(this.searchCriteria.tenRack)
        }
        return ok
      })

      this.rackList = filteredRacks
      if (this.$refs.grdRackList) {
        this.$refs.grdRackList.dataSource = filteredRacks
      }
    },

    onRackSelected(args) {
      // Handle row selection in DataGrid
      this.selectedRack = args.data
    },

    selectRack() {
      if (!this.selectedRack) {
        this.$toast.warning('Vui lòng chọn một rack từ danh sách!')
        return
      }
      this.$emit('rackSelected', this.selectedRack)
      this.closeModal()
    }
  }
}
</script>
<style>
.modal-chon-rack {
  z-index: 1050 !important;
}

.modal-chon-rack .e-dialog {
  position: fixed !important;
  top: 10% !important;
  left: 50% !important;
  transform: translateX(-50%) !important;
  max-height: 80vh !important;
  overflow-y: auto !important;
}

.modal-chon-rack .popup-box {
  max-height: calc(80vh - 40px);
  overflow-y: auto;
}

.modal-chon-rack .popup-body {
  max-height: calc(80vh - 100px);
  overflow-y: auto;
  padding: 20px;
}

.modal-chon-rack .search-form {
  background-color: #f8f9fa;
  padding: 15px;
  border-radius: 5px;
  margin-bottom: 20px;
}

.modal-chon-rack .search-form h5 {
  margin-bottom: 15px;
  color: #495057;
  font-weight: bold;
}

.modal-chon-rack .rack-list h5 {
  margin-bottom: 15px;
  color: #495057;
  font-weight: bold;
}

.modal-chon-rack .form-group {
  margin-bottom: 15px;
}

.modal-chon-rack .form-group label {
  font-weight: 600;
  color: #495057;
  margin-bottom: 5px;
}

.modal-chon-rack .popup-footer {
  padding: 15px 20px;
  border-top: 1px solid #dee2e6;
  background-color: #f8f9fa;
}

.modal-chon-rack .btn {
  min-width: 100px;
}

/* Override Syncfusion dialog default styles */
.modal-chon-rack .e-dialog .e-dlg-content {
  padding: 0 !important;
  overflow: visible !important;
}

.modal-chon-rack .e-dialog .e-dlg-header-content {
  display: none !important;
}
</style>
