<template src="./index.template.html"></template>

<script>
import api from '../../api'
import danhMucIdcApi from '../../../danhmuc-idc/api'
import TongQuanTab from './components/TongQuanTab.vue'
import MatSanTab from './components/MatSanTab.vue'
import RackTab from './components/RackTab.vue'
import NguonDienTab from './components/NguonDienTab.vue'
import KhachHangTab from './components/KhachHangTab.vue'
import ToaNhaTab from './components/ToaNhaTab.vue'
import defaultIdcImage from '@/assets/img/idc/idcdanang_demo.jpg'
import moment from 'moment'

export default {
  name: 'ChiTietIDC',
  components: {
    TongQuanTab,
    MatSanTab,
    RackTab,
    NguonDienTab,
    KhachHangTab,
    ToaNhaTab
  },
  props: {
    modalItemID: {
      type: [String, Number],
      required: true
    }
  },
  data() {
    return {
      activeTab: 'tongquan',
      tabs: [
        { id: 'tongquan', label: 'Tổng quan' },
        { id: 'toanha', label: 'Tòa nhà' },
        { id: 'matsan', label: 'Mặt sàn' },
        { id: 'rack', label: 'Rack' },
        { id: 'nguondien', label: 'Nguồn điện' },
        { id: 'khachhang', label: 'Khách hàng' }
      ],
      stats: {
        soToaNha: 0,
        soMatSan: 0,
        soPhongZone: 0,
        rackThietKe: 0,
        rackDaLapDat: 0,
        rackCoTheLapDat: 0
      },
      power: {
        congSuatDinhDanhDaDap: '450'
      },
      racks: [],
      matSanList: [],
      khachHangList: [],
      toaNhaList: [],
      idcData: {},
      defaultIdcImage,
      idcImageLoading: true,
      idcImageLoaded: false
    }
  },
  watch: {
    modalItemID: {
      immediate: true,
      handler: async function(newId) {
        if (newId) {
          Promise.all([
            this.fetchIdcDetail(newId),
            this.fetchRackList(newId),
            this.fetchMatSanList(newId),
            this.fetchKhachHangList(newId),
            this.fetchToaNhaList(newId)
          ]).then(() => {
            // update new stats
            this.stats = {
              soToaNha: this.idcData.SO_TOANHA,
              soMatSan: this.idcData.SO_MATSAN,
              soPhongZone: this.idcData.SO_PHONGZONE
            }
          })
        }
      }
    }
  },
  computed: {
    coordinates() {
      const lat = this.idcData.TOADO_Y
      const lng = this.idcData.TOADO_X
      return lat && lng ? `${lat}°, ${lng}°` : '-'
    },
    idcUtilizationPercent() {
      const totalU = Number(this.idcData.TONG_U) || 0
      const usedU = Number(this.idcData.SO_U_DASUDUNG) || 0
      if (!totalU || usedU <= 0) return 0
      const percent = Math.max(0, Math.min(100, Math.round((usedU / totalU) * 100)))
      return percent
    },
    idcUtilizationLabel() {
      const totalU = Number(this.idcData.TONG_U) || 0
      const usedU = Number(this.idcData.SO_U_DASUDUNG) || 0
      const percent = this.idcUtilizationPercent
      return `${percent}% (${usedU}/${totalU}U)`
    },
    progressColor() {
      return '#177ee6'
    },
    rackColumns() {
      return [
        {
          fieldName: 'TEN_RACK',
          headerText: 'Tên Rack',
          width: 150,
          textAlign: 'Left',
          allowFiltering: true
        },
        { fieldName: 'THUOC_ZONE', headerText: 'Thuộc Zone', width: 100, textAlign: 'Left', allowFiltering: true },
        // { fieldName: 'MUCDICH_SD', headerText: 'Mục đích sử dụng', width: 90, textAlign: 'Left', allowFiltering: true },
        { fieldName: 'SO_TB', headerText: 'Số thiết bị', width: 50, textAlign: 'Center', allowFiltering: true },
        {
          fieldName: 'usagePercent',
          headerText: 'Tỷ lệ sử dụng',
          width: 140,
          textAlign: 'Center',
          allowFiltering: true,
          template: this.colUsageTemplate()
        },
        { fieldName: 'TRANGTHAI_VH', headerText: 'Trạng thái', width: 100, textAlign: 'Center', allowFiltering: true }
      ]
    },
    matSanColumns() {
      return [
        {
          fieldName: 'TEN',
          headerText: 'Tên mặt sàn',
          width: 220,
          textAlign: 'Left',
          allowFiltering: true
        },
        { fieldName: 'TEN_TOANHA', headerText: 'Tòa nhà', width: 200, textAlign: 'Left', allowFiltering: true },
        {
          fieldName: 'DIEN_TICH',
          headerText: 'Diện tích mặt sàn',
          width: 140,
          textAlign: 'Right',
          allowFiltering: true,
          template: this.createUnitTemplate('DIEN_TICH', 'm²')
        },
        {
          fieldName: 'CHIEU_DAI',
          headerText: 'Chiều dài',
          width: 110,
          textAlign: 'Right',
          allowFiltering: true,
          template: this.createUnitTemplate('CHIEU_DAI', 'm')
        },
        {
          fieldName: 'CHIEU_RONG',
          headerText: 'Chiều rộng',
          width: 110,
          textAlign: 'Right',
          allowFiltering: true,
          template: this.createUnitTemplate('CHIEU_RONG', 'm')
        },
        { fieldName: 'HIEU_LUC', headerText: 'Hiệu lực', width: 120, textAlign: 'Center', allowFiltering: true }
      ]
    },
    khachHangColumns() {
      return [
        { fieldName: 'MA_KH', headerText: 'Mã KH', width: 120, textAlign: 'Left', allowFiltering: true },
        { fieldName: 'TEN_KH', headerText: 'Tên KH', width: 200, textAlign: 'Left', allowFiltering: true },
        { fieldName: 'LOAI_DICHVU', headerText: 'Loại dịch vụ', width: 120, textAlign: 'Center', allowFiltering: true },
        { fieldName: 'DICHVU', headerText: 'Dịch vụ', width: 100, textAlign: 'Center', allowFiltering: true },
        {
          fieldName: 'THAO_TAC',
          headerText: 'Thao tác',
          width: 150,
          textAlign: 'Center',
          allowFiltering: false,
          template: this.colActionTemplate()
        }
      ]
    },
    toaNhaColumns() {
      return [
        { fieldName: 'ID_TOANHA', headerText: 'ID', width: 50, textAlign: 'Left', allowFiltering: true },
        {
          fieldName: 'TEN_TOANHA',
          headerText: 'Tên tòa nhà',
          width: 200,
          textAlign: 'Left',
          allowFiltering: true
        },
        {
          fieldName: 'TEN_MUCDICHSD',
          headerText: 'Mục đích sử dụng',
          width: 140,
          textAlign: 'Left',
          allowFiltering: true
        },
        {
          fieldName: 'DIENTICH',
          headerText: 'DT tổng thể',
          width: 140,
          textAlign: 'Right',
          allowFiltering: true,
          template: this.createUnitTemplate('DIENTICH', 'm²')
        },
        {
          fieldName: 'DIENTICH_PHONGMAY',
          headerText: 'DT phòng máy',
          width: 140,
          textAlign: 'Right',
          allowFiltering: true,
          template: this.createUnitTemplate('DIENTICH_PHONGMAY', 'm²')
        },
        {
          fieldName: 'TONG_RACK_THIETKE',
          headerText: 'SL Rack thiết kế',
          width: 100,
          textAlign: 'Center',
          allowFiltering: true
        },
        {
          fieldName: 'TONG_RACK_DA_LAPDAT',
          headerText: 'SL Rack đã lắp đặt',
          width: 100,
          textAlign: 'Center',
          allowFiltering: true
        },
        {
          fieldName: 'TEN_TRANGTHAI_VH',
          headerText: 'Trạng thái VH',
          width: 120,
          textAlign: 'Center',
          allowFiltering: true
        },
        { fieldName: 'HIEU_LUC', headerText: 'Hiệu lực', width: 80, textAlign: 'Center', allowFiltering: true }
      ]
    }
  },
  methods: {
    onImgErr(event) {
      event.target.onerror = null
      event.target.src = this.defaultIdcImage
    },
    formatDate(value) {
      if (!value) return '-'
      const parsed = moment(value)
      if (!parsed.isValid()) return value
      return parsed.format('DD/MM/YYYY')
    },
    getProgressColor(percent) {
      if (percent >= 90) return '#ef4444'
      if (percent >= 70) return '#f59e0b'
      return '#10b981'
    },
    handleManageDetails() {
      // Emit event để parent component xử lý
      this.$emit('manage-details', this.modalItemID)
    },
    async deleteCurrentIdc() {
      const idcId = this.idcData?.IDC_ID || this.modalItemID
      if (!idcId) {
        this.$toast.warning('Không xác định được IDC cần xóa')
        return
      }

      const confirmed = await this.$confirm(
        `Bạn có chắc chắn muốn xóa IDC "${this.idcData?.TEN_IDC || idcId}"?`,
        'Xác nhận xóa',
        {
          confirmButtonText: 'Xóa',
          cancelButtonText: 'Hủy',
          type: 'warning'
        }
      )

      if (!confirmed) {
        return
      }

      this.$root.showLoading(true)
      try {
        const response = await danhMucIdcApi.delete(this.axios, idcId)
        if (response?.data?.error === 200) {
          this.$toast.success('Đã xóa IDC thành công')
          this.$emit('idc-deleted', idcId)
          this.$emit('close')
        } else {
          this.$toast.error(response?.data?.message_detail || 'Không thể xóa IDC')
        }
      } catch (error) {
        console.error('deleteCurrentIdc error:', error)
        this.$toast.error(error?.data?.message_detail || 'Lỗi khi xóa IDC')
      } finally {
        this.$root.showLoading(false)
      }
    },
    async fetchIdcDetail(idcId) {
      try {
        this.$root.showLoading(true)
        this.idcImageLoading = true
        this.idcImageLoaded = false
        const data = await api.getChiTietIDC(this.axios, idcId)
        // Presign image url if needed
        if (data && data.IMAGE_URL && typeof data.IMAGE_URL === 'string' && !data.IMAGE_URL.startsWith('http')) {
          try {
            const url = await this.getPresignedUrl(data.IMAGE_URL)
            if (url) data.IMAGE_URL = url
          } catch (e) {
            /* ignore */
          }
        }
        this.idcData = data || {}
        this.idcImageLoading = false
        this.idcImageLoaded = true
      } catch (error) {
        console.error('Lỗi khi tải chi tiết IDC:', error)
        this.$toast.error('Không thể tải thông tin chi tiết IDC')
      } finally {
        this.$root.showLoading(false)
      }
    },
    async getPresignedUrl(src) {
      try {
        if (!src) return null
        const rs = await this.$root.context.post('/web-ecms/storage/getPresignedUrl', { fileSource: src })
        if (rs && rs.error_code === 'BSS-00000000' && rs.data && rs.data.result) {
          return rs.data.result
        }
        return null
      } catch (e) {
        return null
      }
    },
    async fetchRackList(idcId) {
      try {
        const response = await api.getRackStatistic(this.axios, idcId)
        // Process data to add calculated fields
        this.racks = (response || []).map((rack) => ({
          ...rack,
          usagePercent: rack.TONG_U > 0 ? Math.round((rack.SO_U_DASUDUNG / rack.TONG_U) * 100) : 0
        }))
      } catch (error) {
        console.error('Lỗi khi tải danh sách rack:', error)
        this.$toast.error('Không thể tải danh sách rack')
      }
    },
    async fetchMatSanList(idcId) {
      try {
        const response = await api.getMatSanStatistic(this.axios, idcId)
        this.matSanList = response || []
      } catch (error) {
        console.error('Lỗi khi tải danh sách mặt sàn:', error)
        this.$toast.error('Không thể tải danh sách mặt sàn')
      }
    },
    async fetchKhachHangList(idcId) {
      try {
        const response = await api.getKhachHangStatistic(this.axios, idcId)
        this.khachHangList = response || []
      } catch (error) {
        console.error('Lỗi khi tải danh sách khách hàng:', error)
        this.$toast.error('Không thể tải danh sách khách hàng')
      }
    },
    async fetchToaNhaList(idcId) {
      try {
        const response = await api.getToaNhaStatistic(this.axios, idcId)
        this.toaNhaList = response || []
      } catch (error) {
        console.error('Lỗi khi tải danh sách tòa nhà:', error)
        this.$toast.error('Không thể tải danh sách tòa nhà')
      }
    },
    // Template cho cột Thao tác với link xem chi tiết
    colActionTemplate() {
      const parent = this
      return function() {
        return {
          template: {
            template: `
              <div style="display: flex; gap: 8px; justify-content: center; align-items: center;">
                <a 
                  href="javascript:void(0)" 
                  class="action-link"
                  @click="viewCustomerDetail"
                  title="Xem chi tiết khách hàng"
                >
                  <i class="fa fa-user"></i> KH
                </a>
                <a 
                  v-if="data.SO_HOPDONG"
                  href="javascript:void(0)" 
                  class="action-link"
                  @click="viewContractDetail"
                  title="Xem chi tiết hợp đồng"
                >
                  <i class="fa fa-file-contract"></i> HĐ
                </a>
              </div>
            `,
            methods: {
              viewCustomerDetail() {
                parent.handleViewCustomerDetail(this.data)
              },
              viewContractDetail() {
                parent.handleViewContractDetail(this.data)
              }
            }
          }
        }
      }
    },
    handleViewCustomerDetail(customerData) {
      this.$emit('view-customer-detail', customerData)
      // this.$router.push({ path: '/customer/detail', query: { id: customerData.KHACHHANG_ID } })
    },
    handleViewContractDetail(contractData) {
      this.$emit('view-contract-detail', contractData)
      // this.$router.push({ path: '/contract/detail', query: { id: contractData.HOPDONG_ID } })
    },
    // Template cho cột Usage với progress bar
    colUsageTemplate() {
      const parent = this
      return function() {
        return {
          template: {
            template: `
              <div style="display: flex; align-items: center; gap: 10px;">
                <span style="min-width: 40px;">{{ getPercent(data) }}%</span>
                <div style="flex: 1; height: 8px; background: #e0e0e0; border-radius: 4px; overflow: hidden;">
                  <div :style="{ height: '100%', width: getPercent(data) + '%', background: '#2196F3', borderRadius: '4px' }"></div>
                </div>
              </div>
            `,
            methods: {
              getPercent(data) {
                return data.usagePercent || 0
              },
              getLabel(data) {
                const percent = data.usagePercent || 0
                const used = data.SO_U_DASUDUNG || 0
                const total = data.TONG_U || 0
                return `${percent}% (${used}/${total}U)`
              },
              getColor(data) {
                const percent = data.usagePercent || 0
                if (percent >= 90) return '#ef4444'
                if (percent >= 70) return '#f59e0b'
                return '#10b981'
              }
            }
          }
        }
      }
    },
    createUnitTemplate(fieldName, unitLabel = '') {
      const fieldKey = fieldName
      const unit = unitLabel ? ` ${unitLabel}` : ''
      return function() {
        return {
          template: {
            template: `<span>{{ formatUnitValue(data) }}</span>`,
            methods: {
              formatUnitValue(row) {
                const rawValue = row?.[fieldKey]
                if (rawValue === null || rawValue === undefined || rawValue === '') {
                  return '-'
                }
                const numericValue = Number(rawValue)
                const formatted =
                  isNaN(numericValue) || !isFinite(numericValue)
                    ? rawValue
                    : numericValue.toLocaleString('vi-VN', { maximumFractionDigits: 2 })
                return `${formatted}${unit}`
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
.chi-tiet-idc {
  background: white;
  border-radius: 8px;
  min-height: calc(100vh - 140px);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

/* Header Section */
.idc-header {
  display: flex;
  gap: 15px;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 5px;
}

.idc-avatar-section {
  flex-shrink: 0;
}

.idc-avatar-large {
  width: 100px;
  height: 100px;
  border-radius: 6px;
  object-fit: cover;
  border: 1px solid #e5e7eb;
}

.idc-avatar-skeleton {
  width: 100px;
  height: 100px;
  border-radius: 6px;
  background: linear-gradient(90deg, #f3f4f6 25%, #e5e7eb 37%, #f3f4f6 63%);
  background-size: 400% 100%;
  animation: idc-skeleton-loading 1.2s ease-in-out infinite;
  border: 1px solid #e5e7eb;
}

@keyframes idc-skeleton-loading {
  0% {
    background-position: 100% 0;
  }
  100% {
    background-position: -100% 0;
  }
}

.idc-basic-info {
  flex: 1;
}

.idc-title {
  font-size: 18px;
  font-weight: 700;
  color: #111827;
  margin: 0 0 2px 0;
}

.idc-subtitle {
  font-size: 13px;
  color: #10b981;
  margin-bottom: 6px;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 4px 8px;
  margin-bottom: 4px;
}

.info-item {
  display: flex;
  gap: 6px;
  font-size: 13px;
}

.info-item.full-width {
  grid-column: 1 / -1;
}

.info-item label {
  color: #6b7280;
  font-weight: 500;
  min-width: fit-content;
}

.info-item span {
  color: #111827;
  font-weight: 400;
}

.list-actions-top .list li {
  padding: 10px 5px;
}

.tabs-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-height: 0;
}

/* Tab Content */
.tab-content {
  flex: 1;
  padding: 12px 20px 0px 20px;
  min-height: 0;
  display: flex;
  flex-direction: column;
}

.tab-pane {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-height: 0;
}

.tab-pane-content {
  flex: 1;
  overflow-y: auto;
  min-height: 0;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

/* Stats Cards - 3 Column Grid */
.stats-grid-3col {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 12px;
  margin-bottom: 16px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
  flex: 1;
}

.stats-row {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
}

.stat-card {
  background: #f9fafb;
  padding: 16px 12px;
  border-radius: 8px;
  text-align: center;
  border: 1px solid #e5e7eb;
  transition: transform 0.2s, box-shadow 0.2s;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.stat-card-highlight {
  border: 1px solid #93c5fd;
  background: #eff6ff;
}

.stat-card-highlight .stat-value {
  color: #2563eb;
}

.stat-card-success {
  border: 1px solid #86efac;
  background: #f0fdf4;
}

.stat-card-success .stat-value {
  color: #16a34a;
}

.stat-card-info {
  border: 1px solid #7dd3fc;
  background: #f0f9ff;
}

.stat-card-info .stat-value {
  color: #0284c7;
}

.stat-card-warning {
  border: 1px solid #fcd34d;
  background: #fefce8;
}

.stat-card-warning .stat-value {
  color: #ca8a04;
}

.stat-value {
  font-size: 26px;
  font-weight: 700;
  color: #111827;
  margin-bottom: 6px;
}

.stat-label {
  font-size: 13px;
  color: #6b7280;
  font-weight: 500;
}

/* Section Title */
.section-title {
  font-size: 16px;
  font-weight: 600;
  color: #111827;
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  gap: 8px;
  padding-bottom: 6px;
  border-bottom: 1px solid #e5e7eb;
}

.section-title i {
  color: #2563eb;
}

/* Table */
.table-responsive {
  overflow-x: auto;
  margin-top: 12px;
}

.rack-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 13px;
}

.rack-table thead {
  background: #f9fafb;
  border-bottom: 2px solid #e5e7eb;
}

.rack-table th {
  padding: 12px 16px;
  text-align: left;
  font-weight: 600;
  color: #374151;
}

.rack-table td {
  padding: 12px 16px;
  border-bottom: 1px solid #f3f4f6;
}

.rack-table tbody tr:hover {
  background: #f9fafb;
}

.progress-container {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.progress-bar {
  width: 100%;
  height: 10px;
  background: #e5e7eb;
  border-radius: 9999px;
  overflow: hidden;
  position: relative;
}

.progress-fill {
  height: 100%;
  border-radius: 9999px 0 0 9999px;
  transition: width 0.3s ease;
}

.progress-label {
  font-size: 12px;
  color: #6b7280;
}

/* Status Badge */
.status-badge {
  display: inline-block;
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
}

.status-badge.hoạt động {
  background: #d1fae5;
  color: #065f46;
}

.status-badge.cảnh báo {
  background: #fef3c7;
  color: #92400e;
}

/* Action Buttons */
.action-buttons {
  display: flex;
  gap: 12px;
  justify-content: flex-end;
  padding: 16px 0 10px 0;
  margin-top: auto;
  flex-shrink: 0;
}

.btn-outline {
  background: white;
  border: 1px solid #d1d5db;
  color: #374151;
}

.btn-outline:hover {
  background: #f9fafb;
  border-color: #9ca3af;
}

.text-muted {
  color: #9ca3af;
  font-size: 14px;
  text-align: center;
  padding: 40px 0;
}

.mt-2 {
  margin-top: 8px;
}

.mt-3 {
  margin-top: 16px;
}

.mt-4 {
  margin-top: 24px;
}

/* DataGrid custom styles */
.rack-grid-container {
  flex: 1;
  overflow: auto;
  min-height: 200px;
}

/* Styles for Toa Nha templates */
::v-deep .toanha-name-link {
  color: #2563eb;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.2s;
}

::v-deep .toanha-name-link:hover {
  color: #1d4ed8;
  text-decoration: underline;
}

::v-deep .matsan-name-link {
  color: #2563eb;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.2s;
}

::v-deep .matsan-name-link:hover {
  color: #1d4ed8;
  text-decoration: underline;
}

::v-deep .rack-name-link {
  color: #2563eb;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.2s;
}

::v-deep .rack-name-link:hover {
  color: #1d4ed8;
  text-decoration: underline;
}

::v-deep .status-badge {
  display: inline-block;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 500;
  color: #fff;
}

::v-deep .status-active {
  background-color: #10b981; /* Green */
}

::v-deep .status-maintenance {
  background-color: #f59e0b; /* Orange */
}

::v-deep .status-effective {
  background-color: #2563eb; /* Dark blue */
}

::v-deep .status-inactive {
  background-color: #9ca3af; /* Grey */
}

::v-deep .status-default {
  background-color: #6b7280; /* Default grey */
}

::v-deep .btn-action-icon {
  background: none;
  border: none;
  padding: 4px 8px;
  cursor: pointer;
  color: #6b7280;
  transition: color 0.2s;
  font-size: 14px;
}

::v-deep .btn-action-icon:hover {
  color: #2563eb;
}

::v-deep .btn-action-icon i {
  font-size: 14px;
}
</style>
