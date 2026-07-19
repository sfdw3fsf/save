<template>
    <div class="rack-position-content">
        <div class="legend-title mb-0">Vị trí trên rack</div>

        <!-- Phân cấp vị trí (Position Hierarchy) -->
        <div class="position-hierarchy-section">
            <div class="position-hierarchy-title pb-2">Phân cấp vị trí:</div>
            <div class="hierarchy-breadcrumb">
                <span
                v-for="(item, index) in currentRackData.hierarchy"
                :key="index"
                class="hierarchy-item"
                >
                <span
                    class="hierarchy-label"
                    :class="{ active: index === currentRackData.hierarchy.length - 1 }"
                >
                    {{ item }}
                </span>
                <i
                    v-if="index < currentRackData.hierarchy.length - 1"
                    class="fa fa-arrow-right hierarchy-arrow"
                ></i>
                </span>
            </div>
        </div>

        <!-- Detailed Attributes Section -->
        <div class="attributes-section">
            <div class="attributes-row">
                <div class="attribute-item">
                    <span class="attribute-label">IDC:</span>
                    <span class="attribute-value">{{ currentRackData.idc.name }}</span>
                </div>
                <div class="attribute-item">
                    <span class="attribute-label">Building:</span>
                    <span class="attribute-value">{{ currentRackData.building.name }}</span>
                </div>
                <div class="attribute-item">
                    <span class="attribute-label">Floor:</span>
                    <span class="attribute-value">{{ currentRackData.floor.name }}</span>
                </div>
                <div class="attribute-item">
                    <span class="attribute-label">Zone:</span>
                    <span class="attribute-value">{{ currentRackData.zone.name }}</span>
                </div>
            </div>

            <div class="attributes-row">
                <div class="attribute-item">
                    <span class="attribute-label">{{ currentRackData.idc.code }}</span>
                </div>
                <div class="attribute-item">
                    <span class="attribute-label">{{ currentRackData.building.code }}</span>
                </div>
                <div class="attribute-item">
                    <span class="attribute-label">{{ currentRackData.floor.code }}</span>
                </div>
                <div class="attribute-item">
                    <span class="attribute-label">{{ currentRackData.zone.code }}</span>
                </div>
            </div>

            <div class="attributes-row">
                <div class="attribute-item">
                    <span class="attribute-label">Cage:</span>
                    <span class="attribute-value">{{ currentRackData.cage.name }}</span>
                </div>
                <div class="attribute-item">
                    <span class="attribute-label">Rack ID:</span>
                    <span class="attribute-value">{{ currentRackData.rack.id }}</span>
                </div>
                <div class="attribute-item">
                    <span class="attribute-label">Rack Name:</span>
                    <span class="attribute-value">{{ currentRackData.rack.name }}</span>
                </div>
                <div class="attribute-item">
                    <span class="attribute-label">Vị trí U (U Position):</span>
                    <span class="attribute-value">{{ currentRackData.rack.uPosition }}</span>
                </div>
            </div>

            <div class="attributes-row">
                <div class="attribute-item">
                    <span class="attribute-label">Chiếm dụng (Occupied):</span>
                    <span class="attribute-value">{{ currentRackData.rack.occupied }}</span>
                </div>
            </div>
        </div>

        <!-- Vị trí trên Rack (42U) -->
        <div class="rack-visualization-section">
            <div class="rack-visualization-header">
                <h6>
                    <i class="fa fa-server"></i>
                    Vị trí trên Rack ({{ totalU }}U)
                </h6>
                <div class="usage-info">
                    {{ getTotalUsedU() }}/{{ totalU }} U ({{ getUsagePercentage() }}%)
                </div>
            </div>
            <div class="rack-visualization-content">
                <div class="rack-chart">
                    <!-- Y-axis labels -->
                    <div class="rack-y-axis">
                        <div
                            v-for="marker in yAxisMarkers"
                            :key="'marker-' + marker"
                            class="y-axis-marker"
                            :style="{ top: `${(totalU - marker) * (100 / totalU)}%` }"
                        >
                            {{ marker }}
                        </div>
                    </div>

                    <!-- Rack visualization -->
                    <div class="rack-bars-container">
                        <div
                            v-for="unit in rackUnits"
                            :key="'unit-' + unit.position"
                            class="rack-unit-bar"
                            :class="{ occupied: unit.occupied }"
                            :style="{
                                height: `${(unit.height / totalU) * 100}%`,
                                top: `${((totalU - unit.endPosition) / totalU) * 100}%`,
                                backgroundColor: unit.occupied ? '#2196F3' : 'transparent'
                            }"
                        >
                            <div v-if="unit.occupied" class="unit-content">
                                <div class="unit-device-name">{{ unit.deviceName }}</div>
                                <div class="unit-position">{{ unit.positionRange }}</div>
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
  name: 'RackPosition',
  props: {
    rackData: {
      type: Object,
      default: () => null
    }
  },
  data() {
    return {
      totalU: 42,
      // Dummy data mặc định
      defaultRackData: {
        // Phân cấp vị trí (hierarchy)
        hierarchy: [
          'IDC Hà Nội',
          'Building A',
          'Floor 2',
          'Zone-Network-Core',
          'Cage-Network',
          'R-NET-01'
        ],

        // Thông tin IDC
        idc: {
          name: 'IDC Hà Nội',
          code: 'IDC-HN-01'
        },

        // Thông tin Building
        building: {
          name: 'Building A',
          code: 'BLD-A'
        },

        // Thông tin Floor
        floor: {
          name: 'Floor 2',
          code: 'FL-02'
        },

        // Thông tin Zone
        zone: {
          name: 'Zone-Network-Core',
          code: 'ZONE-NET-CORE'
        },

        // Thông tin Cage
        cage: {
          name: 'Cage-Network',
          code: 'CAGE-NET'
        },

        // Thông tin Rack
        rack: {
          id: 'R-NET-01',
          name: 'Network Rack 01',
          totalU: 42,
          occupied: '5 Units',
          uPosition: 'U38 - U42',
          // Tất cả các units đã chiếm dụng (không phân biệt mặt trước/sau)
          occupiedUnits: [
            {
              positionRange: 'U24-30',
              deviceName: 'Internet Gateway',
              startU: 24,
              endU: 30
            }
          ]
        }
      }
    }
  },
  computed: {
    currentRackData() {
      return this.rackData || this.defaultRackData
    },
    yAxisMarkers() {
      // Tạo markers dựa trên totalU
      const markers = []
      const step = Math.max(1, Math.floor(this.totalU / 4))
      for (let i = 0; i <= this.totalU; i += step) {
        markers.push(i)
      }
      // Đảm bảo có marker cuối cùng
      if (markers[markers.length - 1] !== this.totalU) {
        markers.push(this.totalU)
      }
      return markers
    },
    rackUnits() {
      const data = this.currentRackData
      const units = []
      // Lấy tất cả occupied units (gộp cả front và back nếu có)
      const occupiedUnits = data.rack.occupiedUnits || 
                           data.rack.frontOccupiedUnits || 
                           data.rack.backOccupiedUnits || []

      // Nếu có cả front và back, gộp lại
      if (data.rack.frontOccupiedUnits && data.rack.backOccupiedUnits) {
        const allUnits = [...(data.rack.frontOccupiedUnits || []), ...(data.rack.backOccupiedUnits || [])]
        allUnits.forEach((occupied) => {
          this.parseOccupiedUnit(occupied, units)
        })
      } else {
        // Parse occupied units từ "U38-42" format
        occupiedUnits.forEach((occupied) => {
          this.parseOccupiedUnit(occupied, units)
        })
      }

      return units
    }
  },
  methods: {
    parseOccupiedUnit(occupied, units) {
      const match = occupied.positionRange.match(/U(\d+)-(\d+)/)
      if (match) {
        const start = parseInt(match[1])
        const end = parseInt(match[2])
        const height = end - start + 1

        units.push({
          position: start,
          endPosition: end,
          height: height,
          occupied: true,
          deviceName: occupied.deviceName,
          positionRange: occupied.positionRange
        })
      }
    },
    getTotalUsedU() {
      return this.rackUnits.reduce((sum, unit) => {
        return sum + (unit.occupied ? unit.height : 0)
      }, 0)
    },
    getUsagePercentage() {
      const used = this.getTotalUsedU()
      return Math.round((used / this.totalU) * 100 * 10) / 10
    }
  }
}
</script>

<style scoped>

.sidebar-header h5 {
  margin: 0;
  font-size: 14px;
  font-weight: 600;
  color: #333;
}

.position-hierarchy-title {
  color: #424242;
}

.rack-position-content {
  flex: 1;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

/* Phân cấp vị trí */
.position-hierarchy-section {
  background: #e3f2fd;
  border: 2px solid #bde2fd;
  border-radius: 8px;
  padding: 12px 15px;
}

.hierarchy-breadcrumb {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 8px;
}

.hierarchy-item {
  display: flex;
  align-items: center;
  gap: 8px;
}

.hierarchy-label {
  padding: 6px 12px;
  background: white;
  border-radius: 4px;
  font-size: 12px;
  color: #666;
  white-space: nowrap;
}

.hierarchy-label.active {
  background: #2196f3;
  color: white;
  font-weight: 600;
}

.hierarchy-arrow {
  font-size: 10px;
  color: #999;
}

/* Detailed Attributes */
.attributes-section {
  padding: 0 15px;
}

.attributes-row {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  gap: 15px;
  margin-bottom: 12px;
}

.attributes-row:last-child {
  margin-bottom: 0;
}

.attribute-item {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.attribute-label {
  font-size: 12px;
  color: #666;
  font-weight: 500;
}

.attribute-value {
  font-size: 13px;
  color: #2196f3;
  font-weight: 600;
}

/* Rack Visualization */
.rack-visualization-section {
  background: white;
  border-radius: 6px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.rack-visualization-header {
  padding: 12px 15px;
  background: #fafafa;
  border-bottom: 1px solid #e9ecef;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.rack-visualization-header h6 {
  margin: 0;
  font-size: 13px;
  font-weight: 600;
  color: #333;
  display: flex;
  align-items: center;
  gap: 8px;
}

.rack-visualization-header h6 i {
  font-size: 12px;
  color: #666;
}

.usage-info {
  font-size: 11px;
  color: #666;
  font-weight: 500;
}

.rack-visualization-content {
  padding: 10px 5px;
}

.rack-chart {
  position: relative;
  height: 230px;
  display: flex;
  align-items: flex-start;
}

.rack-y-axis {
  position: relative;
  width: 30px;
  height: 100%;
}

.y-axis-marker {
  position: absolute;
  font-size: 11px;
  color: #666;
  transform: translateY(-50%);
  font-weight: 500;
}

.rack-bars-container {
  flex: 1;
  position: relative;
  height: 100%;
  border-left: 2px solid #ddd;
  border-right: 2px solid #ddd;
  background: repeating-linear-gradient(
    to bottom,
    transparent,
    transparent 9.5%,
    #f0f0f0 9.5%,
    #f0f0f0 10%
  );
}

.rack-unit-bar {
  position: absolute;
  left: 0;
  right: 0;
  border-radius: 4px;
  border: 1px solid #1976d2;
  box-shadow: 0 2px 4px rgba(33, 150, 243, 0.3);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 4px;
  box-sizing: border-box;
}

.rack-unit-bar.occupied {
  background: #2196f3;
  color: white;
}

.unit-content {
  text-align: center;
  width: 100%;
}

.unit-device-name {
  font-size: 11px;
  font-weight: 600;
  margin-bottom: 2px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.unit-position {
  font-size: 10px;
  opacity: 0.9;
}
</style>
