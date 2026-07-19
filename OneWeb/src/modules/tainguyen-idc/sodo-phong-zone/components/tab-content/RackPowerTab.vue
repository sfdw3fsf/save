<template>
  <div class="rack-power-tab">
    <div class="tab-content-area">

      <div class="legend-title d-flex align-items-center">
        <span>Thông tin Nguồn điện</span>
      </div>

      <!-- Power Summary Cards -->
      <div class="row pb-3">
        <div class="col-md-4">
          <div class="power-card power-card-total">
            <div class="power-card-header">Tổng công suất định mức</div>
            <div class="power-card-value">{{ getTotalPower() }} W</div>
            <div class="power-card-footer">Nguồn điện: {{ getPowerSource() }}</div>
          </div>
        </div>
        <div class="col-md-4">
          <div class="power-card power-card-used">
            <div class="power-card-header">Công suất đã sử dụng</div>
            <div class="power-card-value">{{ getUsedPower() }} W</div>
            <div class="power-card-footer-with-bar">
              <div class="usage-bar">
                <div class="usage-bar-fill" :style="{ width: getUsagePercentage() + '%' }"></div>
              </div>
              <div class="usage-text">{{ getUsagePercentage() }}% sử dụng</div>
            </div>
          </div>
        </div>
        <div class="col-md-4">
          <div class="power-card power-card-remaining">
            <div class="power-card-header">Công suất còn lại</div>
            <div class="power-card-value">{{ getRemainingPower() }} W</div>
            <div class="power-card-footer">{{ getRemainingPowerNote() }}</div>
          </div>
        </div>
      </div>

      <!-- PDU Cards -->
      <div class="row">
        <div class="col-md-6">
          <div class="pdu-card">
            <div class="pdu-card-header">
              <div class="pdu-status-icon pdu-status-ok">
                <i class="fa fa-check"></i>
              </div>
              <div class="pdu-title">
                <div class="pdu-name">PDU-001 (APC AP8641)</div>
                <div class="pdu-info">0U Left | 24 cổng | Dual Feed A</div>
              </div>
            </div>
            <div class="pdu-usage">
              <div class="pdu-usage-label">Sử dụng:</div>
              <div class="pdu-usage-value">{{ getPDUPower(0) }}</div>
              <div class="pdu-usage-bar">
                <div class="pdu-usage-bar-fill" :style="{ width: getPDUPercentage(0) }"></div>
              </div>
              <div class="pdu-usage-percent">{{ getPDUPercentage(0) }}</div>
            </div>
          </div>
        </div>
        <div class="col-md-6">
          <div class="pdu-card">
            <div class="pdu-card-header">
              <div class="pdu-status-icon pdu-status-warning">
                <i class="fa fa-exclamation-triangle"></i>
              </div>
              <div class="pdu-title">
                <div class="pdu-name">PDU-002 (APC AP8641)</div>
                <div class="pdu-info">0U Right | 24 cổng | Dual Feed B</div>
              </div>
            </div>
            <div class="pdu-usage">
              <div class="pdu-usage-label">Sử dụng:</div>
              <div class="pdu-usage-value">{{ getPDUPower(1) }}</div>
              <div class="pdu-usage-bar pdu-usage-bar-warning">
                <div class="pdu-usage-bar-fill" :style="{ width: getPDUPercentage(1) }"></div>
              </div>
              <div class="pdu-usage-percent">{{ getPDUPercentage(1) }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'RackPowerTab',

  props: {
    rackId: {
      type: [String, Number],
      required: true
    }
  },

  data() {
    return {
      rackData: {
        totalPower: '7,200',
        usedPower: '5,750',
        remainingPower: '1,450',
        powerSource: 'Dual Feed'
      },
      pduList: [
        {
          name: 'PDU-001 (APC AP8641)',
          info: '0U Left | 24 cổng | Dual Feed A',
          power: '2,800W',
          percentage: '77.8%',
          status: 'ok'
        },
        {
          name: 'PDU-002 (APC AP8641)',
          info: '0U Right | 24 cổng | Dual Feed B',
          power: '2,950W',
          percentage: '81.9%',
          status: 'warning'
        }
      ]
    }
  },

  methods: {
    getTotalPower() {
      return this.rackData.totalPower || '7,200'
    },

    getUsedPower() {
      return this.rackData.usedPower || '5,750'
    },

    getRemainingPower() {
      return this.rackData.remainingPower || '1,450'
    },

    getPowerSource() {
      return this.rackData.powerSource || 'Dual Feed'
    },

    getUsagePercentage() {
      const total = parseFloat(this.getTotalPower().replace(/,/g, ''))
      const used = parseFloat(this.getUsedPower().replace(/,/g, ''))
      return Math.round((used / total) * 100 * 10) / 10
    },

    getRemainingPowerNote() {
      return 'Có thể cấp phát thêm'
    },

    getPDUPower(index) {
      if (this.pduList && this.pduList[index]) {
        return this.pduList[index].power
      }
      return index === 0 ? '2,800W' : '2,950W'
    },

    getPDUPercentage(index) {
      if (this.pduList && this.pduList[index]) {
        return this.pduList[index].percentage
      }
      return index === 0 ? '77.8%' : '81.9%'
    }
  }
}
</script>

<style scoped>
.tab-content-area {
  min-height: 300px;
  border-radius: 6px;
}

/* Header */
.power-header {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 16px;
  font-weight: 600;
  color: #555;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #ddd;
}

.power-header i {
  color: #f39c12;
  font-size: 18px;
}

/* Power Summary Cards */
.power-card {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);
  height: 100%;
  border: 1px solid #e0e0e0;
}

.power-card-total {
  border-left: 4px solid #3498db;
}

.power-card-used {
  border-left: 4px solid #f39c12;
}

.power-card-remaining {
  border-left: 4px solid #2ecc71;
}

.power-card-header {
  font-size: 13px;
  color: #999;
  margin-bottom: 10px;
}

.power-card-value {
  font-size: 28px;
  font-weight: 700;
  color: #2c3e50;
  margin-bottom: 10px;
}

.power-card-footer {
  font-size: 12px;
  color: #999;
}

.power-card-footer-with-bar {
  margin-top: 10px;
}

.usage-bar {
  width: 100%;
  height: 8px;
  background: #f0f0f0;
  border-radius: 4px;
  overflow: hidden;
  margin-bottom: 8px;
}

.usage-bar-fill {
  height: 100%;
  background: linear-gradient(90deg, #f39c12 0%, #e67e22 100%);
  border-radius: 4px;
  transition: width 0.3s ease;
}

.usage-text {
  font-size: 12px;
  color: #666;
}

/* PDU Cards */
.pdu-card {
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 15px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.06);
}

.pdu-card-header {
  display: flex;
  align-items: flex-start;
  gap: 15px;
  margin-bottom: 20px;
}

.pdu-status-icon {
  width: 45px;
  height: 45px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  flex-shrink: 0;
}

.pdu-status-ok {
  background-color: #d4edda;
  color: #28a745;
}

.pdu-status-warning {
  background-color: #fff3cd;
  color: #ffc107;
}

.pdu-title {
  flex: 1;
}

.pdu-name {
  font-size: 16px;
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 5px;
}

.pdu-info {
  font-size: 13px;
  color: #7f8c8d;
}

.pdu-usage {
  display: grid;
  grid-template-columns: auto 1fr auto auto;
  align-items: center;
  gap: 15px;
}

.pdu-usage-label {
  font-size: 13px;
  color: #666;
  font-weight: 500;
}

.pdu-usage-value {
  font-size: 15px;
  font-weight: 700;
  color: #2c3e50;
}

.pdu-usage-bar {
  height: 10px;
  background: #e9ecef;
  border-radius: 5px;
  overflow: hidden;
  min-width: 200px;
}

.pdu-usage-bar-warning .pdu-usage-bar-fill {
  background: linear-gradient(90deg, #f39c12 0%, #e67e22 100%);
}

.pdu-usage-bar-fill {
  height: 100%;
  background: linear-gradient(90deg, #3498db 0%, #2980b9 100%);
  border-radius: 5px;
  transition: width 0.3s ease;
}

.pdu-usage-percent {
  font-size: 15px;
  font-weight: 700;
  color: #2c3e50;
  min-width: 50px;
  text-align: right;
}

/* Responsive */
@media (max-width: 768px) {
  .power-card-value {
    font-size: 24px;
  }

  .pdu-usage {
    grid-template-columns: 1fr;
    gap: 10px;
  }

  .pdu-usage-bar {
    min-width: 100%;
  }
}
</style>
