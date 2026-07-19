<template>
  <div class="room-resource-tab">
    <div class="tab-content-area">
      <!-- Tài nguyên không gian Rack -->
      <div class="legend-title">Tài nguyên không gian Rack</div>

      <div class="row mb-4 align-items-center">
        <div class="col-md-3">
          <div class="stat-card-modern">
            <div class="stat-number">{{ getTotalRackDesign() }}</div>
            <div class="stat-description">Rack thiết kế</div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="stat-card-modern stat-card-success">
            <div class="stat-number">{{ getUsedRack() }}</div>
            <div class="stat-description">Rack đã lắp đặt</div>
          </div>
        </div>
        <div class="col-md-3 text-center">
          <div class="stat-card-modern stat-card-info">
            <div class="stat-number">{{ getTotalRackAvailable() }}</div>
            <div class="stat-description">Rack có thể lắp đặt</div>
          </div>
        </div>
        <div class="col-md-3 text-center">
          <div class="circle-chart-container">
            <svg class="circle-chart" viewBox="0 0 200 200">
              <circle class="circle-bg" cx="100" cy="100" r="80"></circle>
              <circle
                class="circle-progress"
                cx="100"
                cy="100"
                r="80"
                :stroke-dasharray="getRackCircleStroke()"
                :stroke-dashoffset="getRackCircleOffset()"
              ></circle>
            </svg>
            <div class="circle-label">
              <div class="circle-percent">{{ getRackUsagePercent() }}%</div>
            </div>
          </div>
          <div class="circle-text">Tỷ lệ sử dụng Rack</div>
        </div>
      </div>

      <!-- Tài nguyên không gian U -->
      <div class="legend-title mt-3">Tài nguyên không gian U</div>

      <div class="row mb-4 align-items-center">
        <div class="col-md-3">
          <div class="stat-card-modern">
            <div class="stat-number">{{ getTotalU() }}</div>
            <div class="stat-description">Định mức sử dụng U</div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="stat-card-modern stat-card-success">
            <div class="stat-number">{{ getUsedU() }}</div>
            <div class="stat-description">Đã sử dụng U</div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="stat-card-modern stat-card-info">
            <div class="stat-number">{{ getAvailableU() }}</div>
            <div class="stat-description">Khả năng cấp phát U</div>
          </div>
        </div>
        <div class="col-md-3 text-center">
          <div class="circle-chart-container">
            <svg class="circle-chart" viewBox="0 0 200 200">
              <circle class="circle-bg" cx="100" cy="100" r="80"></circle>
              <circle
                class="circle-progress"
                cx="100"
                cy="100"
                r="80"
                :stroke-dasharray="getUCircleStroke()"
                :stroke-dashoffset="getUCircleOffset()"
              ></circle>
            </svg>
            <div class="circle-label">
              <div class="circle-percent">{{ getUUsagePercent() }}%</div>
            </div>
          </div>
          <div class="circle-text">Tỷ lệ sử dụng U</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'RoomResourceTab',

  props: {
    roomId: {
      type: [String, Number],
      required: true
    }
  },

  data () {
    return {
      // Sample data - should be replaced with actual data from roomData
      rackStats: {
        totalDesign: 0,
        totalAvailable: 0,
        used: 0
      },
      uStats: {
        total: 0,
        available: 0,
        used: 0
      },
      roomData: {
        totalRackDesign: 0,
        totalRackAvailable: 0,
        usedRack: 0,
        totalU: 0,
        availableU: 0,
        usedU: 0,
        powerUsagePercent: 0
      }
    }
  },

  created () {
    this.getResourceData()
  },

  methods: {
    // Rack statistics
    getTotalRackDesign () {
      return this.roomData.totalRackDesign || this.rackStats.totalDesign
    },

    getTotalRackAvailable () {
      return this.roomData.totalRackAvailable || this.rackStats.totalAvailable
    },

    getUsedRack () {
      return this.roomData.usedRack || this.rackStats.used
    },

    getRackUsagePercent () {
      const total = this.getTotalRackDesign()
      const used = this.getUsedRack()
      return total > 0 ? Math.round((used / total) * 100) : 0
    },

    // U statistics
    getTotalU () {
      return this.roomData.totalU || this.uStats.total
    },

    getAvailableU () {
      return this.roomData.availableU || this.uStats.available
    },

    getUsedU () {
      return this.roomData.usedU || this.uStats.used
    },

    getUUsagePercent () {
      const total = this.getTotalU()
      const used = this.getUsedU()
      return total > 0 ? Math.round((used / total) * 100) : 0
    },

    // Circle chart calculations
    getRackCircleStroke () {
      const radius = 80
      const circumference = 2 * Math.PI * radius
      return circumference
    },

    getRackCircleOffset () {
      const percent = this.getRackUsagePercent()
      const circumference = this.getRackCircleStroke()
      return circumference - (percent / 100) * circumference
    },

    getUCircleStroke () {
      const radius = 80
      const circumference = 2 * Math.PI * radius
      return circumference
    },

    getUCircleOffset () {
      const percent = this.getUUsagePercent()
      const circumference = this.getUCircleStroke()
      return circumference - (percent / 100) * circumference
    },

    async getResourceData () {
      let rs = await this.$root.context.post('/web-ecms/idc/phong-zone/get-by-id', {
        id: this.roomId
      })
      if (rs && rs.data) {
        let data = rs.data[0]
        this.roomData = {
          totalRackDesign: data.TONG_RACK,
          totalRackAvailable: data.TONG_RACK_CLD,
          usedRack: data.TONG_RACK_LD,
          totalU: data.TONG_U,
          availableU: data.TONG_U_CLD,
          usedU: data.TONG_U_LD
        }
      }
    }
  }
}
</script>

<style scoped>
.tab-content-area {
  border-radius: 4px;
  min-height: 150px;
}

/* Section Title */
.section-title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 2px solid #ddd;
}

/* Stat Cards */
.stat-card-modern {
  background: #f9fafb;
  padding: 30px 20px;
  border-radius: 8px;
  text-align: center;
  border: 1px solid #e5e7eb;
  transition: transform 0.2s, box-shadow 0.2s;
}

.stat-card-modern:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.stat-card-success {
  border: 1px solid #86efac;
  background: #f0fdf4;
}

.stat-card-success .stat-number {
  color: #16a34a;
}

.stat-card-info {
  border: 1px solid #7dd3fc;
  background: #f0f9ff;
}

.stat-card-info .stat-number {
  color: #0284c7;
}

.stat-number {
  font-size: 26px;
  font-weight: 700;
  color: #111827;
  margin-bottom: 8px;
  line-height: 1;
}

.stat-description {
  font-size: 13px;
  color: #6b7280;
  font-weight: 500;
}

.stat-card {
  background: #f5f5f5;
  padding: 20px;
  border-radius: 8px;
  text-align: left;
  height: 100%;
}

.stat-card-with-bar {
  background: white;
  border: 1px solid #e0e0e0;
}

.stat-label {
  font-size: 13px;
  color: #999;
  margin-bottom: 10px;
}

.stat-value {
  font-size: 36px;
  font-weight: 700;
  color: #333;
  display: flex;
  gap: 10px;
  align-items: center;
}

.stat-unit {
  font-size: 13px;
  color: #999;
  margin-top: 15px;
}

/* Horizontal Progress Bar */
.progress-bar-horizontal {
  width: 100%;
  height: 8px;
  background: #e0e0e0;
  border-radius: 4px;
  overflow: hidden;
  margin-top: 15px;
}

.progress-bar-fill {
  height: 100%;
  background: linear-gradient(90deg, #2196f3 0%, #1976d2 100%);
  border-radius: 4px;
  transition: width 0.3s ease;
}

/* Circle Charts */
.circle-chart-container {
  position: relative;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px 0;
}

.circle-chart {
  width: 160px;
  height: 160px;
  transform: rotate(-90deg);
}

.circle-bg {
  fill: none;
  stroke: #e5e7eb;
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
  font-size: 28px;
  font-weight: 700;
  color: #111827;
  margin-bottom: 5px;
}

.circle-text {
  font-size: 12px;
  color: #6b7280;
  white-space: nowrap;
  font-weight: 500;
}

/* Responsive */
@media (max-width: 768px) {
  .stat-value {
    font-size: 28px;
  }

  .circle-chart-container {
    margin-bottom: 40px;
  }
}
</style>
