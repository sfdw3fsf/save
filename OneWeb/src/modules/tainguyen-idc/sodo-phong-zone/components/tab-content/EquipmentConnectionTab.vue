<template>
  <div class="equipment-connection-tab">
    <div class="row">
      <!-- Column 1: Kết nối mạng -->
      <div class="col-md-6">
        <div class="info-section">
          <div class="legend-title d-flex align-items-center">
            Kết nối mạng
          </div>
          <div class="info-item">
            <label>IP Address:</label>
            <span>{{ equipmentData.ipAddress || '192.168.1.100' }}</span>
          </div>
          <div class="info-item">
            <label>Subnet Mask:</label>
            <span>{{ equipmentData.subnetMask || '255.255.255.0' }}</span>
          </div>
          <div class="info-item">
            <label>Gateway:</label>
            <span>{{ equipmentData.gateway || '192.168.1.1' }}</span>
          </div>
          <div class="info-item">
            <label>VLAN:</label>
            <span>{{ equipmentData.vlan || 'VLAN 100' }}</span>
          </div>
          <div class="info-item">
            <label>MAC Address:</label>
            <span>{{ equipmentData.macAddress || '00:1A:2B:3C:4D:5E' }}</span>
          </div>
        </div>
      </div>

      <!-- Column 2: Kết nối nguồn -->
      <div class="col-md-6">
        <div class="info-section">
          <div class="legend-title d-flex align-items-center">
            Kết nối nguồn
          </div>
          <div class="info-item">
            <label>Nguồn chính (PSU 1):</label>
            <span>{{ equipmentData.psu1 || 'PDU-A Port 12' }}</span>
          </div>
          <div class="info-item">
            <label>Nguồn dự phòng (PSU 2):</label>
            <span>{{ equipmentData.psu2 || 'PDU-B Port 12' }}</span>
          </div>
          <div class="info-item">
            <label>Trạng thái PSU 1:</label>
            <span class="badge badge-success">{{ equipmentData.psu1Status || 'Hoạt động' }}</span>
          </div>
          <div class="info-item">
            <label>Trạng thái PSU 2:</label>
            <span class="badge badge-success">{{ equipmentData.psu2Status || 'Hoạt động' }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- Row 2: Kết nối thiết bị -->
    <div class="row mt-4">
      <div class="col-md-12">
        <div class="info-section">
          <div class="legend-title d-flex align-items-center">
            Kết nối thiết bị
          </div>
          <div class="table-responsive">
            <table class="table table-bordered table-hover">
              <thead>
                <tr>
                  <th>Port</th>
                  <th>Loại kết nối</th>
                  <th>Thiết bị đích</th>
                  <th>Port đích</th>
                  <th>Trạng thái</th>
                  <th>Tốc độ</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(conn, index) in connections" :key="index">
                  <td>{{ conn.port }}</td>
                  <td>{{ conn.type }}</td>
                  <td>{{ conn.targetDevice }}</td>
                  <td>{{ conn.targetPort }}</td>
                  <td>
                    <span :class="getStatusBadgeClass(conn.status)">
                      {{ conn.status }}
                    </span>
                  </td>
                  <td>{{ conn.speed }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>

    <!-- Row 3: Thông tin quản lý -->
    <div class="row mt-4">
      <div class="col-md-12">
        <div class="info-section">
          <div class="legend-title d-flex align-items-center">
            Thông tin quản lý
          </div>
          <div class="row">
            <div class="col-md-4">
              <div class="info-item">
                <label>Management IP:</label>
                <span>{{ equipmentData.managementIp || '192.168.100.100' }}</span>
              </div>
            </div>
            <div class="col-md-4">
              <div class="info-item">
                <label>SNMP Community:</label>
                <span>{{ equipmentData.snmpCommunity || 'public' }}</span>
              </div>
            </div>
            <div class="col-md-4">
              <div class="info-item">
                <label>Monitoring:</label>
                <span>{{ equipmentData.monitoring || 'Zabbix, Nagios' }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'EquipmentConnectionTab',

  props: {
    equipmentData: {
      type: Object,
      required: true
    }
  },

  computed: {
    connections() {
      return this.equipmentData.connections || [
        { port: 'Gi1/0/1', type: 'Uplink', targetDevice: 'Core-SW-01', targetPort: 'Gi1/0/24', status: 'Up', speed: '10Gbps' },
        { port: 'Gi1/0/2', type: 'Uplink', targetDevice: 'Core-SW-02', targetPort: 'Gi1/0/24', status: 'Up', speed: '10Gbps' },
        { port: 'Gi1/0/10', type: 'Access', targetDevice: 'Server-01', targetPort: 'eth0', status: 'Up', speed: '1Gbps' },
        { port: 'Gi1/0/11', type: 'Access', targetDevice: 'Server-02', targetPort: 'eth0', status: 'Up', speed: '1Gbps' },
        { port: 'Gi1/0/12', type: 'Access', targetDevice: 'Server-03', targetPort: 'eth0', status: 'Down', speed: '-' }
      ]
    }
  },

  methods: {
    getStatusBadgeClass(status) {
      return {
        'badge': true,
        'badge-success': status === 'Up',
        'badge-danger': status === 'Down',
        'badge-warning': status === 'Warning'
      }
    }
  }
}
</script>

<style scoped>
.info-section {
  background: #f9f9f9;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  padding: 15px;
  margin-bottom: 15px;
  height: 100%;
}

.info-item {
  margin-bottom: 12px;
  display: flex;
  flex-direction: column;
}

.info-item:last-child {
  margin-bottom: 0;
}

.info-item label {
  font-weight: 600;
  color: black;
  margin-bottom: 4px;
}

.info-item span {
  color: #333;
  word-wrap: break-word;
}

.badge {
  color: white !important;
  padding: 4px 10px;
  font-weight: 500;
}

.badge-success {
  background-color: #4CAF50;
}

.badge-danger {
  background-color: #f44336;
}

.badge-warning {
  background-color: #FF9800;
}

.table {
  margin-bottom: 0;
}

.table thead th {
  background-color: #f5f5f5;
  font-weight: 600;
  border-bottom: 2px solid #dee2e6;
}

.table tbody tr:hover {
  background-color: #f9f9f9;
}

@media (max-width: 768px) {
  .info-section {
    margin-bottom: 15px;
  }
}
</style>
