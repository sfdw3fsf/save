<template>
  <div class="connection-statistics-sidebar">
    <!-- Header -->
    <div class="sidebar-header">
      <i class="fa fa-line-chart header-icon"></i>
      <h5>Thống kê kết nối</h5>
    </div>

    <!-- Statistics Cards -->
    <div class="statistics-content">
      <!-- Card 1: Tổng quan thiết bị -->
      <div class="stat-card">
        <div class="stat-card-title">Tổng quan thiết bị</div>
        <div class="stat-card-body">
          <div class="stat-row">
            <span class="stat-label">Tổng thiết bị:</span>
            <span class="stat-value">{{ statistics.TOTAL || 0 }}</span>
          </div>
          <div class="stat-row">
            <span class="stat-label">Hoạt động:</span>
            <span class="stat-value stat-success">{{ statistics.ACTIVE || 0 }}</span>
          </div>
          <div class="stat-row">
            <span class="stat-label">Tạm ngưng:</span>
            <span class="stat-value stat-warning">{{ statistics.SUSPENDED || 0 }}</span>
          </div>
          <div class="stat-row">
            <span class="stat-label">Hỏng:</span>
            <span class="stat-value stat-danger">{{ statistics.BROKEN || 0 }}</span>
          </div>
          <div class="stat-row">
            <span class="stat-label">Thanh lý:</span>
            <span class="stat-value">{{ statistics.LIQUIDATED || 0 }}</span>
          </div>
          <div class="stat-row">
            <span class="stat-label">Hiệu lực:</span>
            <span class="stat-value stat-success">{{ statistics.EFFECTIVE || 0 }}</span>
          </div>
          <div class="stat-row">
            <span class="stat-label">Không hiệu lực:</span>
            <span class="stat-value stat-danger">{{ statistics.INEFFECTIVE || 0 }}</span>
          </div>
        </div>
      </div>

      <!-- Card 2: Trạng thái kết nối -->
      <div class="stat-card">
        <div class="stat-card-title">Trạng thái kết nối</div>
        <div class="stat-card-body">
          <div class="stat-row">
            <span class="stat-label">Tổng kết nối:</span>
            <span class="stat-value">{{ statistics.CONNECTIONTOTAL || 0 }}</span>
          </div>
          <div class="stat-row">
            <span class="stat-label">Hoạt động:</span>
            <span class="stat-value stat-success">{{ statistics.CONNECTIONACTIVE || 0 }}</span>
          </div>
          <div class="stat-row">
            <span class="stat-label">Suy giảm:</span>
            <span class="stat-value stat-warning">{{ statistics.DEGRADED || 0 }}</span>
          </div>
          <div class="stat-row">
            <span class="stat-label">Ngắt:</span>
            <span class="stat-value stat-danger">{{ statistics.DISCONNECTED || 0 }}</span>
          </div>
        </div>
      </div>

      <!-- Card 3: Phân loại kết nối -->
      <div class="stat-card">
        <div class="stat-card-title">Phân loại kết nối</div>
        <div class="stat-card-body">
          <div class="stat-row">
            <span class="stat-label">Fiber:</span>
            <span class="stat-value stat-info">{{ statistics.FIBER || 0 }}</span>
          </div>
          <div class="stat-row">
            <span class="stat-label">Copper:</span>
            <span class="stat-value stat-warning">{{ statistics.COPPER || 0 }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ConnectionStatisticsSidebar',
  props: {
    statistics: {
      type: Object,
      default: () => ({
        // Device overview
        TOTAL: 0,
        ACTIVE: 0,
        SUSPENDED: 0,
        BROKEN: 0,
        LIQUIDATED: 0,
        EFFECTIVE: 0,
        INEFFECTIVE: 0,
        // Connection status
        CONNECTIONTOTAL: 0,
        CONNECTIONACTIVE: 0,
        DEGRADED: 0,
        DISCONNECTED: 0,
        // Connection type
        FIBER: 0,
        COPPER: 0
      })
    }
  }
}
</script>

<style scoped>
.connection-statistics-sidebar {
  width: 250px;
  height: 100%;
  background: #f5f5f5;
  border-left: 1px solid #e0e0e0;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.sidebar-header {
  padding: 15px 20px;
  background: #ffffff;
  border-bottom: 1px solid #e0e0e0;
  display: flex;
  align-items: center;
  gap: 10px;
}

.header-icon {
  font-size: 16px;
  color: #666;
}

.sidebar-header h5 {
  margin: 0;
  font-size: 14px;
  font-weight: 600;
  color: #333;
}

.statistics-content {
  flex: 1;
  overflow-y: auto;
  padding: 15px;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.stat-card {
  background: #ffffff;
  border-radius: 6px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.stat-card-title {
  padding: 12px 15px;
  background: #fafafa;
  border-bottom: 1px solid #e9ecef;
  font-size: 13px;
  font-weight: 600;
  color: #333;
}

.stat-card-body {
  padding: 12px 15px;
}

.stat-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 0;
  font-size: 13px;
  border-bottom: 1px solid #f0f0f0;
}

.stat-row:last-child {
  border-bottom: none;
}

.stat-label {
  color: #666;
  flex: 1;
}

.stat-value {
  font-weight: 600;
  color: #333;
  text-align: right;
}

.stat-success {
  color: #28a745;
}

.stat-warning {
  color: #ff9800;
}

.stat-danger {
  color: #dc3545;
}

.stat-info {
  color: #2196f3;
}
</style>
