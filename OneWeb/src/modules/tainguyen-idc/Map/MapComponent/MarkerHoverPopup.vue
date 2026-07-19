<template>
  <div v-if="show && data" class="marker-hover-popup" :style="popupStyle">
    <div class="popup-content">
      <!-- Header -->
      <div class="popup-header">
        <h3 class="popup-title">{{ data.data.name }}</h3>
        <button class="close-btn" @click="$emit('close')">
          <i class="fa fa-times"></i>
        </button>
      </div>

      <!-- Status Indicator -->
      <div
        class="status-indicator"
        :class="{
          'status-active': data.data.status === 'Đang hoạt động',
          'status-inactive': data.data.status === 'Tạm dừng'
        }"
      >
        <i class="fa fa-info-circle status-icon"></i>
        <span class="status-text">{{ data.data.status }}</span>
      </div>

      <!-- Information Fields -->
      <div class="info-section">
        <div class="info-row">
          <span class="info-label">ID IDC:</span>
          <span class="info-value">{{ data.data.id }}</span>
        </div>
        <div class="info-row">
          <span class="info-label">Địa chỉ:</span>
          <span class="info-value address-text">{{ data.data.address }}</span>
        </div>
        <div class="info-row">
          <span class="info-label">Thời gian VH:</span>
          <span class="info-value">{{ data.data.operatingDate }}</span>
        </div>
      </div>

      <!-- Summary Cards -->
      <div class="summary-cards">
        <div class="summary-card">
          <div class="card-number">{{ data.data.buildings || 0 }}</div>
          <div class="card-label">Tòa nhà</div>
        </div>
        <div class="summary-card">
          <div class="card-number">{{ data.data.floors || 0 }}</div>
          <div class="card-label">Mặt sàn</div>
        </div>
        <div class="summary-card">
          <div class="card-number">{{ data.data.racks || 0 }}</div>
          <div class="card-label">Rack</div>
        </div>
      </div>

      <!-- Action Buttons -->
      <div class="action-buttons">
        <button class="btn btn-primary" @click="$emit('edit', data)">
          <i class="fa fa-edit"></i>
          Sửa
        </button>
        <button class="btn btn-secondary" @click="$emit('details', data)">
          <i class="fa fa-info-circle"></i>
          Chi tiết
        </button>
      </div>
    </div>
    <div :class="arrowClass"></div>
  </div>
</template>

<script>
export default {
  name: 'MarkerHoverPopup',
  props: {
    show: {
      type: Boolean,
      default: false
    },
    data: {
      type: Object,
      default: null
    },
    position: {
      type: Object,
      default: () => ({ x: 0, y: 0 })
    }
  },
  computed: {
    popupStyle() {
      const { x, y } = this.position
      const windowWidth = window.innerWidth
      const windowHeight = window.innerHeight
      const popupWidth = 320 // max-width của popup
      const popupHeight = 280 // estimated height

      let finalX = x
      let finalY = y
      let transform = 'translateX(-50%)'

      // Check if popup would go off screen horizontally
      if (x - popupWidth / 2 < 0) {
        finalX = popupWidth / 2 + 10 // 10px margin from edge
      } else if (x + popupWidth / 2 > windowWidth) {
        finalX = windowWidth - popupWidth / 2 - 10
      }

      // Check if popup would go off screen vertically (above)
      if (y - popupHeight < 40) {
        // Flip to show below marker
        finalY = y + 40 // 40px below marker to account for arrow height
        transform = 'translateX(-50%) translateY(0%)'
      } else {
        // Show above marker (default)
        finalY = y - 8
        transform = 'translateX(-50%) translateY(-100%)'
      }

      return {
        left: finalX + 'px',
        top: finalY + 'px',
        transform: transform
      }
    },

    arrowClass() {
      const { y } = this.position
      const windowHeight = window.innerHeight
      const popupHeight = 280

      // If popup is flipped to show below marker
      if (y - popupHeight < 40) {
        return 'popup-arrow popup-arrow-up'
      } else {
        return 'popup-arrow popup-arrow-down'
      }
    }
  }
}
</script>

<style scoped>
/* Marker hover popup styles */
.marker-hover-popup {
  position: fixed;
  z-index: 2000 !important;
  pointer-events: auto;
}

.popup-content {
  background: #ffffff;
  border-radius: 8px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
  min-width: 280px;
  max-width: 320px;
  overflow: hidden;
  border: 1px solid #e0e0e0;
}

/* Header */
.popup-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 5px 13px;
  border-bottom: 1px solid #f0f0f0;
}

.popup-title {
  margin: 0;
  font-size: 16px;
  font-weight: 600;
  color: #333333;
}

.close-btn {
  background: none;
  border: none;
  color: #999999;
  font-size: 16px;
  cursor: pointer;
  padding: 4px;
  border-radius: 4px;
  transition: color 0.2s;
}

.close-btn:hover {
  color: #666666;
}

/* Status Indicator */
.status-indicator {
  display: flex;
  align-items: center;
  padding: 6px 16px;
  font-size: 12px;
  font-weight: 500;
}

.status-active {
  background: #e8f5e8;
  color: #2e7d32;
}

.status-inactive {
  background: #ffebee;
  color: #c62828;
}

.status-icon {
  margin-right: 8px;
  font-size: 16px;
}

/* Information Section */
.info-section {
  padding: 12px 16px;
}

.info-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.info-row:last-child {
  margin-bottom: 0;
}

.info-label {
  font-weight: 500;
  color: #666666;
  font-size: 12px;
}

.info-value {
  color: #333333;
  font-size: 12px;
  text-align: right;
  max-width: 150px;
  word-wrap: break-word;
}

.address-text {
  max-width: 120px;
  word-wrap: break-word;
  white-space: normal;
  line-height: 1.3;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  box-orient: vertical;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* Summary Cards */
.summary-cards {
  display: flex;
  gap: 8px;
  padding: 0 16px 12px 16px;
}

.summary-card {
  flex: 1;
  background: #f8f9fa;
  border-radius: 6px;
  padding: 10px 8px;
  text-align: center;
  border: 1px solid #e9ecef;
}

.card-number {
  font-size: 18px;
  font-weight: 700;
  color: #333333;
  margin-bottom: 2px;
}

.card-label {
  font-size: 10px;
  color: #666666;
  font-weight: 500;
}

/* Action Buttons */
.action-buttons {
  display: flex;
  gap: 8px;
  padding: 0 16px 12px 16px;
}

/* Arrow */
.popup-arrow {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  width: 0;
  height: 0;
}

.popup-arrow-down {
  top: 100%;
  border-left: 8px solid transparent;
  border-right: 8px solid transparent;
  border-top: 8px solid #ffffff;
  margin-top: 2px;
}

.popup-arrow-up {
  bottom: 100%;
  border-left: 8px solid transparent;
  border-right: 8px solid transparent;
  border-bottom: 8px solid #ffffff;
  margin-bottom: 2px;
}
</style>
