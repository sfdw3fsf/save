<template>
  <div class="nang-luc-khong-gian card">
    <div class="card-body">
      <div class="row align-items-center">
        <!-- Donut: Tỷ lệ sử dụng rack -->
        <div class="col-lg-4 mb-2">
          <div class="circle-chart-container">
            <svg class="circle-chart" viewBox="0 0 200 200">
              <circle class="circle-bg" cx="100" cy="100" r="80"></circle>
              <circle
                class="circle-progress"
                cx="100"
                cy="100"
                r="80"
                :stroke-dasharray="circleStroke"
                :stroke-dashoffset="circleOffset"
              ></circle>
            </svg>

            <div class="circle-label">
              <div class="circle-percent">{{ usagePercent.toFixed(0) }}%</div>
              <div class="circle-text">Tỷ lệ sử dụng Rack</div>
            </div>
          </div>
        </div>

        <!-- Cards -->
        <div class="col-lg-8">
          <div class="row">
            <div class="stats-grid-3col">
              <div class="stat-card">
                <div class="stat-value">{{ fmt(tongRackThietKe) }}</div>
                <div class="stat-title text-muted">Rack thiết kế</div>
              </div>
              <div class="stat-card stat-card-success">
                <div class="stat-value">{{ fmt(tongRackLapDat) }}</div>
                <div class="stat-label">Rack đã lắp đặt</div>
              </div>
              <div class="stat-card stat-card-info">
                <div class="stat-value">{{ fmt(soRackConLai) }}</div>
                <div class="stat-title text-muted">Rack có thể lắp đặt</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '../api.js'
export default {
  name: 'NangLucKhongGian',
  props: {
    matSanId: { type: Number, required: true }
  },
  data() {
    return {
      tongRackLapDat: 0,
      tongRackThietKe: 0,
      soRackConLai: 0,
      loading: false
    }
  },
  computed: {
    usagePercent() {
      const t = Number(this.tongRackThietKe) || 0
      const u = Number(this.tongRackLapDat) || 0
      return t > 0 ? (u / t) * 100 : 0
    },
    circleStroke() {
      const r = 80
      return 2 * Math.PI * r
    },
    circleOffset() {
      return this.circleStroke - (this.usagePercent / 100) * this.circleStroke
    }
  },
  watch: {
    matSanId: {
      immediate: true,
      handler() {
        this.fetchNangLucKhongGian()
      }
    }
  },
  methods: {
    async fetchNangLucKhongGian() {
      try {
        this.$root.loading(true)
        const res = await api.getNangLucKhongGian(this.$root.context, this.matSanId)
        const d = res.data || {}
        this.tongRackLapDat = Number(d.tongRackLapDat || 0)
        this.tongRackThietKe = Number(d.tongRackThietKe || 0)
        this.soRackConLai = Number(d.soRackConLai || Math.max(0, this.tongRackThietKe - this.tongRackLapDat))
      } catch (e) {
        this.$toast.error('Lấy năng lực không gian thất bại' + e.response.data.message || '')
      } finally {
        this.$root.loading(false)
      }
    },
    fmt(v) {
      return (v ?? 0).toLocaleString()
    }
  }
}
</script>

<style scoped>
.nang-luc-khong-gian {
  padding: 8px;
}

/* Donut Chart */
.circle-chart-container {
  position: relative;
  width: 200px;
  height: 240px;
  margin: 0 auto;
}

.circle-chart {
  width: 200px;
  height: 200px;
  transform: rotate(-90deg);
}

.circle-bg {
  fill: none;
  stroke: #e0e0e0;
  stroke-width: 12;
}

.circle-progress {
  fill: none;
  stroke: #2196f3;
  stroke-width: 12;
  stroke-linecap: round;
  transition: stroke-dashoffset 0.5s ease;
}

.circle-label {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}

.circle-percent {
  font-size: 32px;
  font-weight: 700;
  color: #333;
}

.circle-text {
  font-size: 13px;
  color: #999;
  margin-top: 4px;
  position: absolute;
  bottom: -95px;
  left: 50%;
  transform: translateX(-50%);
  width: 200px;
  white-space: nowrap;
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
</style>
