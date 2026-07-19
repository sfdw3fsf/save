<template>
  <div class="floor-detail-tab">
    <!-- Thông tin chung -->
    <div class="detail-section">
      <div class="legend-title">Thông tin chung</div>
      <div class="row">
        <div class="col-md-4">
          <div class="info-item">
            <label>ID mặt sàn:</label>
            <span>{{ floorInfo.id || 'N/A' }}</span>
          </div>
        </div>
        <div class="col-md-4">
          <div class="info-item">
            <label>Tên mặt sàn:</label>
            <span>{{ floorInfo.name || 'N/A' }}</span>
          </div>
        </div>
        <div class="col-md-4">
          <div class="info-item">
            <label>IDC:</label>
            <span>{{ floorInfo.idc || 'N/A' }}</span>
          </div>
        </div>
        <div class="col-md-4">
          <div class="info-item">
            <label>Tòa nhà:</label>
            <span>{{ floorInfo.building || 'N/A' }}</span>
          </div>
        </div>
        <div class="col-md-4">
          <div class="info-item">
            <label>Tầng:</label>
            <span>{{ floorInfo.floor || 'N/A' }}</span>
          </div>
        </div>
        <div class="col-md-4">
          <div class="info-item">
            <label>Diện tích:</label>
            <span>{{ floorInfo.area || 'N/A' }} m²</span>
          </div>
        </div>
        <div class="col-md-4">
          <div class="info-item">
            <label>Kích thước:</label>
            <span>{{ floorInfo.dimensions || 'N/A' }}</span>
          </div>
        </div>
        <div class="col-md-4">
          <div class="info-item">
            <label>Tải trọng sàn:</label>
            <span>{{ floorInfo.loadCapacity || 'N/A' }} kg/m²</span>
          </div>
        </div>
        <div class="col-md-4">
          <div class="info-item">
            <label>Trạng thái:</label>
            <span :class="getStatusClass()">{{ getStatusText() }}</span>
          </div>
        </div>
        <div class="col-md-12">
          <div class="info-item">
            <label>Ghi chú:</label>
            <span>{{ floorInfo.note || 'N/A' }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- Thống kê tài nguyên -->
    <div class="detail-section mt-4">
      <div class="legend-title">Thống kê tài nguyên</div>
      <div class="row">
        <div class="col-md-3">
          <div class="stat-card">
            <div class="stat-icon" style="background-color: #2196F3;">
              <i class="fa fa-server"></i>
            </div>
            <div class="stat-content">
              <div class="stat-value">{{ statistics.totalRacks || 0 }}</div>
              <div class="stat-label">Tổng số Rack</div>
            </div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="stat-card">
            <div class="stat-icon" style="background-color: #4CAF50;">
              <i class="fa fa-check-circle"></i>
            </div>
            <div class="stat-content">
              <div class="stat-value">{{ statistics.activeRacks || 0 }}</div>
              <div class="stat-label">Rack hoạt động</div>
            </div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="stat-card">
            <div class="stat-icon" style="background-color: #FFC107;">
              <i class="fa fa-wrench"></i>
            </div>
            <div class="stat-content">
              <div class="stat-value">{{ statistics.maintenanceRacks || 0 }}</div>
              <div class="stat-label">Rack bảo trì</div>
            </div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="stat-card">
            <div class="stat-icon" style="background-color: #9E9E9E;">
              <i class="fa fa-ban"></i>
            </div>
            <div class="stat-content">
              <div class="stat-value">{{ statistics.inactiveRacks || 0 }}</div>
              <div class="stat-label">Rack không dùng</div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Thông tin hệ thống -->
    <div class="detail-section mt-4">
      <div class="legend-title">Thông tin hệ thống</div>
      <div class="row">
        <div class="col-md-6">
          <div class="info-item">
            <label>Hệ thống điện:</label>
            <span>{{ systemInfo.powerSystem || 'N/A' }}</span>
          </div>
        </div>
        <div class="col-md-6">
          <div class="info-item">
            <label>Công suất điện thiết kế:</label>
            <span>{{ systemInfo.designPower || 'N/A' }} kW</span>
          </div>
        </div>
        <div class="col-md-6">
          <div class="info-item">
            <label>Công suất điện đang sử dụng:</label>
            <span>{{ systemInfo.usedPower || 'N/A' }} kW</span>
          </div>
        </div>
        <div class="col-md-6">
          <div class="info-item">
            <label>Hệ thống làm mát:</label>
            <span>{{ systemInfo.coolingSystem || 'N/A' }}</span>
          </div>
        </div>
        <div class="col-md-6">
          <div class="info-item">
            <label>Công suất lạnh thiết kế:</label>
            <span>{{ systemInfo.designCooling || 'N/A' }} kW</span>
          </div>
        </div>
        <div class="col-md-6">
          <div class="info-item">
            <label>Công suất lạnh đang sử dụng:</label>
            <span>{{ systemInfo.usedCooling || 'N/A' }} kW</span>
          </div>
        </div>
        <div class="col-md-6">
          <div class="info-item">
            <label>Hệ thống PCCC:</label>
            <span>{{ systemInfo.fireSystem || 'N/A' }}</span>
          </div>
        </div>
        <div class="col-md-6">
          <div class="info-item">
            <label>Hệ thống giám sát:</label>
            <span>{{ systemInfo.monitoringSystem || 'N/A' }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- Danh sách phòng/zone -->
    <div class="detail-section mt-4">
      <div class="legend-title">Danh sách phòng/zone</div>
      <div class="table-responsive">
        <table class="table table-bordered table-hover">
          <thead>
            <tr>
              <th>STT</th>
              <th>Tên phòng/zone</th>
              <th>Diện tích (m²)</th>
              <th>Số Rack</th>
              <th>Trạng thái</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(room, index) in rooms" :key="room.id">
              <td>{{ index + 1 }}</td>
              <td>{{ room.name }}</td>
              <td>{{ room.area }}</td>
              <td>{{ room.rackCount }}</td>
              <td>
                <span :class="getRoomStatusClass(room.status)">
                  {{ getRoomStatusText(room.status) }}
                </span>
              </td>
              <td>
                <button class="btn btn-sm btn-info" @click="viewRoomDetail(room)">
                  <i class="fa fa-eye"></i> Chi tiết
                </button>
              </td>
            </tr>
            <tr v-if="rooms.length === 0">
              <td colspan="6" class="text-center">Không có dữ liệu</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FloorDetailTab',

  props: {
    floorId: {
      type: [String, Number],
      required: true
    },
    floorData: {
      type: Object,
      default: null
    }
  },

  data() {
    return {
      floorInfo: {},
      statistics: {},
      systemInfo: {},
      rooms: []
    }
  },

  mounted() {
    this.loadFloorDetail()
  },

  methods: {
    async loadFloorDetail() {
      // Load floor detail from API
      let rs = await this.$root.context.post('/web-ecms/thongtin-matsan/get-by-id', {
        floorId: this.floorId
      })

      if (rs && rs.data) {
        this.floorInfo = {
          id: rs.data.ID,
          name: rs.data.TEN,
          idc: rs.data.IDC,
          building: rs.data.TOANHA,
          floor: rs.data.TANG,
          area: rs.data.DIENTICH,
          dimensions: rs.data.KICHTHUOC,
          loadCapacity: rs.data.TAITRONG,
          status: rs.data.TRANGTHAI,
          note: rs.data.GHICHU
        }
      }

      // Load statistics
      this.loadStatistics()
      
      // Load system info
      this.loadSystemInfo()
      
      // Load rooms
      this.loadRooms()
    },

    async loadStatistics() {
      // Mock data - replace with actual API call
      this.statistics = {
        totalRacks: 45,
        activeRacks: 38,
        maintenanceRacks: 5,
        inactiveRacks: 2
      }
    },

    async loadSystemInfo() {
      // Mock data - replace with actual API call
      this.systemInfo = {
        powerSystem: 'UPS 2N',
        designPower: 500,
        usedPower: 380,
        coolingSystem: 'Precision Air Conditioning',
        designCooling: 400,
        usedCooling: 320,
        fireSystem: 'FM-200',
        monitoringSystem: 'DCIM + Camera 24/7'
      }
    },

    async loadRooms() {
      // Mock data - replace with actual API call
      this.rooms = [
        { id: 1, name: 'Zone Server A', area: 150, rackCount: 20, status: 'active' },
        { id: 2, name: 'Zone Server B', area: 120, rackCount: 15, status: 'active' },
        { id: 3, name: 'Zone Network', area: 80, rackCount: 10, status: 'maintenance' }
      ]
    },

    getStatusClass() {
      const status = this.floorInfo.status
      if (status === 'active') return 'badge badge-success'
      if (status === 'maintenance') return 'badge badge-warning'
      if (status === 'inactive') return 'badge badge-secondary'
      return 'badge badge-info'
    },

    getStatusText() {
      const status = this.floorInfo.status
      const statusMap = {
        active: 'Đang hoạt động',
        maintenance: 'Bảo trì',
        inactive: 'Không hoạt động'
      }
      return statusMap[status] || 'N/A'
    },

    getRoomStatusClass(status) {
      if (status === 'active') return 'badge badge-success'
      if (status === 'maintenance') return 'badge badge-warning'
      if (status === 'inactive') return 'badge badge-secondary'
      return 'badge badge-info'
    },

    getRoomStatusText(status) {
      const statusMap = {
        active: 'Hoạt động',
        maintenance: 'Bảo trì',
        inactive: 'Không hoạt động'
      }
      return statusMap[status] || 'N/A'
    },

    viewRoomDetail(room) {
      this.$emit('view-room-detail', room)
    }
  }
}
</script>

<style scoped>
.floor-detail-tab {
  padding: 20px;
}

.detail-section {
  background: #f9f9f9;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  padding: 20px;
}

.legend-title {
  font-weight: bold;
  color: #333;
  margin-bottom: 15px;
  padding-bottom: 8px;
  border-bottom: 2px solid #ddd;
}

.info-item {
  margin-bottom: 12px;
  display: flex;
  flex-direction: column;
}

.info-item label {
  font-weight: 600;
  color: black;
  margin-bottom: 4px;
}

.info-item span {
  color: #333;
}

.stat-card {
  display: flex;
  align-items: center;
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  padding: 15px;
  margin-bottom: 15px;
}

.stat-icon {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 24px;
  margin-right: 15px;
}

.stat-content {
  flex: 1;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #333;
}

.stat-label {
  font-size: 14px;
  color: #666;
}

.table {
  background: white;
  margin-bottom: 0;
}

.table thead th {
  background: #f5f5f5;
  font-weight: 600;
  border-bottom: 2px solid #ddd;
}

.badge {
  color: white !important;
  padding: 4px 10px;
  font-weight: 500;
}

.badge-success {
  background-color: #4CAF50;
}

.badge-warning {
  background-color: #FFC107;
}

.badge-secondary {
  background-color: #9E9E9E;
}

.badge-info {
  background-color: #2196F3;
}

@media (max-width: 768px) {
  .stat-card {
    margin-bottom: 10px;
  }
}
</style>
