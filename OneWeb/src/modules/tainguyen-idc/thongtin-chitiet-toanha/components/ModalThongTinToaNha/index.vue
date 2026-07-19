<template src="./index.html"></template>
<script>
import NangLucDien from './components/NangLucDien.vue'
import NangLucKhongGian from './components/NangLucKhongGian.vue'
import GridMatSan from './components/GridMatSan.vue'
import SoDoMatSanDetail from '../../../SoDoMatSanDetail/index.vue'
import defaultIdcImage from '@/assets/img/idc/idcdanang_demo.jpg'
import api from './api'

export default {
  name: 'ModalThongTinToaNha',
  components: {
    NangLucDien,
    NangLucKhongGian,
    GridMatSan,
    SoDoMatSanDetail
  },
  props: {
    id: {
      type: Number,
      default: 1
    }
  },
  data() {
    return {
      tab_index: 1,
      showDialog: false,
      isModalThemMatSanVisible: false,
      selectedMatSan: null,
      toaNhaDetail: {},
      defaultIdcImage,
      presignedUrl: null,
    }
  },
  computed: {
    progressRackDaLapDat() {
      if (this.toaNhaDetail.TONG_RACK_THIETKE && this.toaNhaDetail.TONG_RACK_DA_LAPDAT) {
        return Math.round((this.toaNhaDetail.TONG_RACK_DA_LAPDAT / this.toaNhaDetail.TONG_RACK_THIETKE) * 100)
      }
      return 0
    }
  },
  mounted: async function() {
    await this.loadData()
    await this.loadPresignedUrl()
  },
  methods: {
    // ======== load data ========
    async loadData() {
      try {
        const response = await api.getThongTinToaNha(this.axios, this.id)
        this.toaNhaDetail = response || {}

        // emit data for parent
        this.$emit('onLoadData', this.toaNhaDetail)
      } catch (error) {
        this.$toast.error(error.message || 'Lỗi khi tải dữ liệu tòa nhà')
      }
    },

    async loadPresignedUrl() {
      try {
        if (!this.toaNhaDetail.HINHANH) {
          this.presignedUrl = this.defaultIdcImage
        } else {
          this.presignedUrl = await api.getPresignedUrlFromMinio(this.axios, this.toaNhaDetail.HINHANH)
        }
      } catch (error) {
        console.error('Error loading presigned URL:', error)
      }
    },

    // ======== handle ui interaction ========
    onChangeTab(tabIndex) {
      this.tab_index = tabIndex
    },
    viewMatSan(matSanData) {
      this.showDialog = true
      this.selectedMatSan = matSanData
    },
    closeMatSan() {
      this.showDialog = false
      this.selectedMatSan = null
    },
    selectMatSanTaiNguyen(object) {
      console.log('Select mặt sàn tài nguyên: ', object)
      this.$emit('selectObjectTaiNguyen', object)
    },
  }
}
</script>
<!-- <style scoped lang="css"> @import './style.css'; </style> -->
<style>
.badge {
  font-size: 12px;
  padding: 4px 8px;
  border-radius: 4px;
}
.badge.bg-success {
  background-color: #10b981;
  color: #ffffff;
}
.info-section .row {
  margin-bottom: 16px !important;
  align-items: center;
}

.info-section .col-md-4 {
  padding-right: 6px;
}

.text-secondary {
  color: #212529 !important;
}

.page-header {
  background-color: #2c3f51;
  border-radius: 6px 6px 0 0;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
.header-title {
  font-size: 18px;
}
/* Stats Cards - 3 Column Grid */
.stats-grid-3col {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 12px;
  margin-bottom: 16px;
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

.mt-4 {
  margin-top: 24px;
}

.mb-4 {
  margin-bottom: 24px;
}

.mt-8 {
  margin-top: 48px;
}

.center-card {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.toanha-header {
  display: flex;
  gap: 15px;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 5px;
}

.toanha-avatar-section {
  flex-shrink: 0;
}

.toanha-avatar-large {
  width: 100px;
  height: 100px;
  border-radius: 6px;
  object-fit: cover;
  border: 1px solid #e5e7eb;
}

.toanha-avatar-skeleton {
  width: 100px;
  height: 100px;
  border-radius: 6px;
  background: linear-gradient(90deg, #f3f4f6 25%, #e5e7eb 37%, #f3f4f6 63%);
  background-size: 400% 100%;
  animation: toanha-skeleton-loading 1.2s ease-in-out infinite;
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

.toanha-basic-info {
  flex: 1;
}

.toanha-title {
  font-size: 18px;
  font-weight: 700;
  color: #111827;
  margin: 0 0 2px 0;
}

.toanha-subtitle {
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
</style>
