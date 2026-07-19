<template>
  <div class="tongquan-tab">
    <div class="tab-pane-content">
      <!-- Thông tin cơ sở hạ tầng -->
      <div class="legend-title">
        Cơ sở hạ tầng
      </div>
      <div class="stats-grid-3col">
        <div class="stat-card">
          <div class="stat-value">{{ idcData.SO_TOANHA }}</div>
          <div class="stat-label">Tòa nhà</div>
        </div>
        <div class="stat-card">
          <div class="stat-value">{{ idcData.SO_MATSAN }}</div>
          <div class="stat-label">Mặt sàn</div>
        </div>
        <div class="stat-card">
          <div class="stat-value">{{ idcData.SO_PHONGZONE }}</div>
          <div class="stat-label">Phòng/Zone</div>
        </div>
      </div>

      <!-- Thông tin Rack -->
      <div class="legend-title mt-4">
        Thống kê Rack
      </div>
      <div class="stats-grid-3col">
        <div class="stat-card">
          <div class="stat-value">{{ idcData.SL_RACK_THIETKE }}</div>
          <div class="stat-label">Rack thiết kế</div>
        </div>
        <div class="stat-card stat-card-success">
          <div class="stat-value">{{ idcData.SL_RACK_DALAP }}</div>
          <div class="stat-label">Rack đã lắp đặt</div>
        </div>
        <div class="stat-card stat-card-info">
          <div class="stat-value">{{ idcData.SL_RACK_CHUALAP }}</div>
          <div class="stat-label">Rack có thể lắp đặt</div>
        </div>
      </div>

      <!-- Thông tin Công suất -->
      <div class="legend-title mt-4">
        Thống kê Công suất
      </div>
      <div class="stats-grid-3col">
        <!-- <div class="stat-card stat-card-info">
          <div class="stat-value">{{ idcData.TONGCS_DIENLUOI || 550 }}W</div>
          <div class="stat-label"><i class="fa fa-bolt"></i> Công suất điện lưới</div>
        </div> -->
        <div class="stat-card stat-card-warning">
          <div class="stat-value">{{ idcData.TONGCS_TB_DALAPDAT || 0 }}W</div>
          <div class="stat-label"><i class="fa fa-bolt"></i> Công suất định danh đã cấp</div>
        </div>
        <!-- <div class="stat-card stat-card-success">
          <div class="stat-value">{{ idcData.TONGCS_COTHECAPPHAT || 550 - idcData.TONGCS_TB_DALAPDAT }}W</div>
          <div class="stat-label"><i class="fa fa-bolt"></i> Công suất định danh có thể cấp phát</div>
        </div> -->
      </div>
    </div>
    
    <!-- Action Buttons - Chỉ hiển thị trong tab Tổng quan -->
    <div class="action-buttons">
      <!-- <button class="btn btn-outline" @click="$emit('edit')">
        <i class="fa fa-edit"></i>
        Chỉnh sửa
      </button>
      <button class="btn btn-primary" @click="$emit('manage-details')">
        <i class="fa fa-cog"></i>
        Quản lý chi tiết
      </button> -->
    </div>
  </div>
</template>

<script>
export default {
  name: 'TongQuanTab',
  props: {
    idcData: {
      type: Object,
      default: () => ({})
    },
    stats: {
      type: Object,
      default: () => ({
        soToaNha: 0,
        soMatSan: 0,
        soPhongZone: 0
      })
    }
  }
}
</script>

<style scoped>
.tongquan-tab {
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

/* Section Title */
.section-title {
  font-size: 14px;
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

.mt-4 {
  margin-top: 24px;
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
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  display: flex;
  align-items: center;
  gap: 6px;
  transition: all 0.2s;
}

.btn-outline:hover {
  background: #f9fafb;
  border-color: #9ca3af;
}

.btn-primary {
  background: #2563eb;
  border: 1px solid #2563eb;
  color: white;
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  display: flex;
  align-items: center;
  gap: 6px;
  transition: all 0.2s;
}

.btn-primary:hover {
  background: #1d4ed8;
  border-color: #1d4ed8;
}
</style>

