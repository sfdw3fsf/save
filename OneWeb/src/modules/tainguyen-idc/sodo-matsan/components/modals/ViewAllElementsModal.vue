<template>
  <b-modal ref="modal" title="Xem toàn bộ phần tử trên mặt sàn" size="xl" modal-class="view-all-modal-custom"
    hide-footer no-close-on-backdrop @hidden="handleClose">
    <div class="view-all-container">
      <!-- Diagram container -->
      <div class="diagram-wrapper">
        <ejs-diagram v-if="visible" id="viewAllDiagram" ref="viewAllDiagram" :width="'100%'" :height="'100%'"
          :snapSettings="snapSettings" :scrollSettings="scrollSettings" :pageSettings="pageSettings"
          :selectedItems="selectedItems"></ejs-diagram>
      </div>

      <!-- Legend -->
      <div class="legend-panel">
        <h4>Điều khiển</h4>

        <!-- Zoom controls -->
        <div class="zoom-controls">
          <b-button size="sm" variant="outline-primary" @click="onZoomIn" title="Phóng to">
            <span class="icon ui-1_zoom-in nc-icon-outline"></span>
          </b-button>
          <b-button size="sm" variant="outline-primary" @click="onZoomOut" title="Thu nhỏ">
            <span class="icon ui-1_zoom-out nc-icon-outline"></span>
          </b-button>
          <b-button size="sm" variant="outline-secondary" @click="onReset" title="Đặt lại">
            <span class="icon one-refresh"></span>
          </b-button>
        </div>

        <div class="legend-divider"></div>

        <h4>Hiển thị lớp</h4>

        <div class="legend-item">
          <b-form-checkbox v-model="layers.rooms" @change="updateLayerVisibility">
            <div class="legend-content">
              <div class="legend-color" style="background: rgba(33, 150, 243, 0.05); border: 2px solid #2196f3"></div>
              <span>Phòng/Zone</span>
            </div>
          </b-form-checkbox>
        </div>

        <div class="legend-item">
          <b-form-checkbox v-model="layers.cages" @change="updateLayerVisibility">
            <div class="legend-content">
              <div class="legend-color" style="background: rgba(255, 152, 0, 0.08); border: 2px dashed #ff9800"></div>
              <span>Lồng quây</span>
            </div>
          </b-form-checkbox>
        </div>

        <div class="legend-item">
          <b-form-checkbox v-model="layers.racks" @change="updateLayerVisibility">
            <div class="legend-content">
              <div class="legend-color" style="background: #fff; border: 1.5px solid #2196f3"></div>
              <span>Rack</span>
            </div>
          </b-form-checkbox>
        </div>

        <div class="legend-item">
          <b-form-checkbox v-model="layers.equipments" @change="updateLayerVisibility">
            <div class="legend-content">
              <div class="legend-color" style="background: #fff; border: 1.5px solid #ff9800"></div>
              <span>Thiết bị</span>
            </div>
          </b-form-checkbox>
        </div>

        <div class="legend-divider"></div>

        <div class="legend-actions">
          <b-button size="sm" variant="outline-primary" @click="showAllLayers">Hiện tất cả</b-button>
          <b-button size="sm" variant="outline-secondary" @click="hideAllLayers">Ẩn tất cả</b-button>
        </div>
      </div>
    </div>

    <div class="modal-footer-custom">
      <b-button variant="secondary" @click="closeModal">Đóng</b-button>
    </div>
  </b-modal>
</template>

<script>
import Vue from 'vue'
import { DiagramPlugin, NodeConstraints, SnapConstraints } from '@syncfusion/ej2-vue-diagrams'

Vue.use(DiagramPlugin)

export default {
  name: 'ViewAllElementsModal',

  data() {
    return {
      visible: false,
      floorData: null,

      // Layer visibility
      layers: {
        rooms: true,
        cages: true,
        racks: true,
        equipments: true
      },

      snapSettings: {
        constraints: SnapConstraints.ShowLines,
        horizontalGridlines: {
          lineColor: '#e0e0e0',
          lineDashArray: '2,2',
          lineIntervals: [1, 9, 0.25, 9.75, 0.25, 9.75, 0.25, 9.75, 0.25, 9.75],
          snapIntervals: [10]
        },
        verticalGridlines: {
          lineColor: '#e0e0e0',
          lineDashArray: '2,2',
          lineIntervals: [1, 9, 0.25, 9.75, 0.25, 9.75, 0.25, 9.75, 0.25, 9.75],
          snapIntervals: [10]
        }
      },

      scrollSettings: {
        scrollLimit: 'Diagram',
        minZoom: 0.1,
        maxZoom: 3,
        canAutoScroll: false,
        padding: { left: 100, right: 100, top: 100, bottom: 100 }
      },

      pageSettings: {
        orientation: 'Landscape',
        width: 2000,
        height: 1500,
        multiplePage: false,
        showPageBreaks: false,
        background: { color: 'white' },
        boundaryConstraints: 'Page'
      },

      selectedItems: {
        constraints: 0 // Không cho select
      }
    }
  },

  methods: {
    async show(floorId) {
      this.visible = true
      this.floorData = null

      // Show modal
      this.$refs.modal.show()

      await this.$nextTick()

      // Load data từ API
      await this.loadFloorData(floorId)

      // Render diagram
      await this.$nextTick()
      this.renderDiagram()
    },

    closeModal() {
      this.$refs.modal.hide()
    },

    onZoomIn() {
      if (!this.$refs.viewAllDiagram || !this.$refs.viewAllDiagram.ej2Instances) return
      const diagram = this.$refs.viewAllDiagram.ej2Instances
      diagram.zoom(1.2, { x: 0.5, y: 0.5 })
    },

    onZoomOut() {
      if (!this.$refs.viewAllDiagram || !this.$refs.viewAllDiagram.ej2Instances) return
      const diagram = this.$refs.viewAllDiagram.ej2Instances
      diagram.zoom(1 / 1.2, { x: 0.5, y: 0.5 })
    },

    onReset() {
      if (!this.$refs.viewAllDiagram || !this.$refs.viewAllDiagram.ej2Instances) return
      const diagram = this.$refs.viewAllDiagram.ej2Instances
      diagram.reset()
      diagram.fitToPage()
    },

    showAllLayers() {
      this.layers.rooms = true
      this.layers.cages = true
      this.layers.racks = true
      this.layers.equipments = true
      this.updateLayerVisibility()
    },

    hideAllLayers() {
      this.layers.rooms = true // Luôn hiện phòng
      this.layers.cages = false
      this.layers.racks = false
      this.layers.equipments = false
      this.updateLayerVisibility()
    },

    updateLayerVisibility() {
      if (!this.$refs.viewAllDiagram || !this.$refs.viewAllDiagram.ej2Instances) {
        console.warn('Diagram not ready for layer visibility update')
        return
      }

      const diagram = this.$refs.viewAllDiagram.ej2Instances

      const stats = {
        rooms: 0,
        cages: 0,
        racks: 0,
        equipments: 0,
        other: 0
      }

      diagram.nodes.forEach((node) => {
        let shouldBeVisible = true
        let nodeType = 'other'

        // Convert ID to string để check
        const nodeId = String(node.id)

        if (nodeId.startsWith('room_')) {
          shouldBeVisible = this.layers.rooms
          nodeType = 'rooms'
        } else if (nodeId.startsWith('cage_')) {
          shouldBeVisible = this.layers.cages
          nodeType = 'cages'
        } else if (nodeId.startsWith('rack_')) {
          shouldBeVisible = this.layers.racks
          nodeType = 'racks'
        } else if (nodeId.startsWith('equipment_')) {
          shouldBeVisible = this.layers.equipments
          nodeType = 'equipments'
        } else if (nodeId === 'floor_boundary') {
          // Luôn hiện floor boundary
          shouldBeVisible = true
          nodeType = 'other'
        }

        node.visible = shouldBeVisible
        stats[nodeType]++
      })

      diagram.dataBind()
    },

    async loadFloorData(floorId) {
      try {
        // Load thông tin mặt sàn
        const floorInfo = await this.getFloorInfo(floorId)

        // Load tất cả phòng/zone
        const rooms = await this.getRooms(floorId)

        // Load tất cả cage, rack, equipment cho từng phòng
        const roomsWithDetails = await Promise.all(
          rooms.map(async (room) => {
            const [cages, racks, equipments] = await Promise.all([
              this.getCages(room.id),
              this.getRacks(room.id),
              this.getEquipments(room.id)
            ])

            return {
              ...room,
              cages: cages || [],
              racks: racks || [],
              equipments: equipments || []
            }
          })
        )

        this.floorData = {
          ...floorInfo,
          rooms: roomsWithDetails
        }

      } catch (error) {
        console.error('❌ Error loading floor data:', error)
        this.$toast.error('Lỗi khi tải dữ liệu')
      }
    },

    async getFloorInfo(floorId) {
      try {
        const rs = await this.$root.context.post('/web-ecms/danh-muc-mat-san/get-by-id', {
          id: floorId
        })

        if (!rs || !rs.data || !Array.isArray(rs.data) || rs.data.length === 0) {
          throw new Error('No floor data')
        }

        const data = rs.data[0]
        return {
          id: data.ID,
          name: data.TENTAT || data.TEN,
          width: data.CHIEU_RONG || data.TAMSAN_RONG,
          height: data.CHIEU_DAI || data.TAMSAN_DAI
        }
      } catch (error) {
        console.error('Error loading floor info:', error)
        return {
          id: floorId,
          name: 'Mặt sàn',
          width: 2000,
          height: 1500
        }
      }
    },

    async getRooms(floorId) {
      try {
        let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/get-ds-phong-daco-vitri', {
          id: floorId
        })

        if (!rs || !rs.data || !Array.isArray(rs.data)) {
          console.warn('No rooms data for floor:', floorId)
          return []
        }

        return rs.data.map((item) => {
          let polygon = typeof item.KHONGGIAN === 'string' ? JSON.parse(item.KHONGGIAN) : item.KHONGGIAN

          // Normalize polygon về (0,0)
          if (polygon && Array.isArray(polygon) && polygon.length > 0) {
            const xs = polygon.map((p) => p[0])
            const ys = polygon.map((p) => p[1])
            const minX = Math.min(...xs)
            const minY = Math.min(...ys)
            polygon = polygon.map((p) => [p[0] - minX, p[1] - minY])
          }

          return {
            id: item.ID,
            name: item.TENTAT || item.TEN,
            polygon: polygon,
            x: Number(item.TOADO_X || 0),
            y: Number(item.TOADO_Y || 0)
          }
        })
      } catch (error) {
        console.error('Error loading rooms:', error)
        return []
      }
    },

    async getCages(roomId) {
      try {
        const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/get-ds-longquay-daco-vitri', {
          id: roomId
        })

        if (!rs || !rs.data || !Array.isArray(rs.data)) {
          return []
        }

        return rs.data.map((item) => {
          let polygon = typeof item.KHONGGIAN === 'string' ? JSON.parse(item.KHONGGIAN) : item.KHONGGIAN

          if (polygon && Array.isArray(polygon) && polygon.length > 0) {
            const xs = polygon.map((p) => p[0])
            const ys = polygon.map((p) => p[1])
            const minX = Math.min(...xs)
            const minY = Math.min(...ys)
            polygon = polygon.map((p) => [p[0] - minX, p[1] - minY])
          }

          return {
            id: item.LONGQUAY_ID,
            name: item.TENTAT || item.TEN,
            polygon: polygon,
            x: Number(item.TOADO_X || 0),
            y: Number(item.TOADO_Y || 0),
            roomId: roomId
          }
        })
      } catch (error) {
        console.error('Error loading cages for room', roomId, error)
        return []
      }
    },

    async getRacks(roomId) {
      try {
        const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/get-ds-rack-da-lapdat', {
          id: roomId
        })

        if (!rs || !rs.data || !Array.isArray(rs.data)) {
          return []
        }

        return rs.data.map((item) => ({
          id: item.ID,
          name: item.TENTAT || item.TEN,
          x: Number(item.TOADO_X || 0),
          y: Number(item.TOADO_Y || 0),
          width: Number(item.HUONG == 1 ? item.RONG : item.DAI || 60),
          height: Number(item.HUONG == 1 ? item.DAI : item.RONG || 60),
          roomId: roomId,
          color: item.MA_MAU || '#2196F3'
        }))
      } catch (error) {
        console.error('Error loading racks for room', roomId, error)
        return []
      }
    },

    async getEquipments(roomId) {
      try {
        const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/get-ds-thietbi-daco-vitri', {
          id: roomId
        })

        if (!rs || !rs.data || !Array.isArray(rs.data)) {
          return []
        }

        return rs.data.map((item) => ({
          id: item.ID,
          name: item.TENTAT || item.TEN,
          type: item.LOAI,
          x: Number(item.TOADO_X || 0),
          y: Number(item.TOADO_Y || 0),
          width: 60,
          height: 60,
          roomId: roomId
        }))
      } catch (error) {
        console.error('Error loading equipments for room', roomId, error)
        return []
      }
    },

    renderDiagram() {
      if (!this.floorData || !this.$refs.viewAllDiagram) {
        console.warn('Cannot render diagram: missing data or ref')
        return
      }

      const diagram = this.$refs.viewAllDiagram.ej2Instances

      // Clear diagram
      diagram.clear()

      // Update page settings
      diagram.pageSettings.width = this.floorData.width + 200
      diagram.pageSettings.height = this.floorData.height + 200


      // Render floor boundary
      this.renderFloorBoundary(diagram)


      // Render từng phòng
      this.floorData.rooms.forEach((room) => {
        this.renderRoom(diagram, room)
      })

      // Fit to page
      this.$nextTick(() => {
        diagram.fitToPage()
      })
    },

    renderFloorBoundary(diagram) {
      const width = this.floorData.width
      const height = this.floorData.height
      const padding = 50
      const maskPadding = 5000

      // Mask để che vùng ngoài mặt sàn (ẩn grid)
      // Outer rectangle (clockwise) - tọa độ tuyệt đối
      const outerRect = `M ${-maskPadding} ${-maskPadding}
                         L ${width + maskPadding} ${-maskPadding}
                         L ${width + maskPadding} ${height + maskPadding}
                         L ${-maskPadding} ${height + maskPadding} Z`

      // Inner rectangle (counter-clockwise để tạo hole) - tọa độ tuyệt đối
      const innerRect = `M ${width} ${0}
                         L ${0} ${0}
                         L ${0} ${height}
                         L ${width} ${height} Z`

      // Combine outer và inner để tạo mask với hole
      const maskPath = outerRect + ' ' + innerRect

      diagram.add({
        id: 'floor_mask',
        offsetX: -maskPadding,
        offsetY: -maskPadding,
        pivot: { x: 0, y: 0 },
        width: width + maskPadding * 2,
        height: height + maskPadding * 2,
        shape: {
          type: 'Path',
          data: maskPath
        },
        style: {
          fill: 'white',
          strokeColor: 'transparent',
          strokeWidth: 0
        },
        constraints: NodeConstraints.None,
        zIndex: -2
      })

      // Main floor boundary
      diagram.add({
        id: 'floor_boundary',
        offsetX: width / 2,
        offsetY: height / 2,
        width: width,
        height: height,
        shape: {
          type: 'Basic',
          shape: 'Rectangle'
        },
        style: {
          fill: 'transparent',
          strokeColor: '#2196F3',
          strokeWidth: 3
        },
        constraints: NodeConstraints.None,
        zIndex: -1
      })

      // Top dimension label (Rộng)
      diagram.add({
        id: 'floor_label_width',
        offsetX: width / 2,
        offsetY: -padding / 2,
        width: 150,
        height: 30,
        shape: {
          type: 'Text',
          content: `Rộng: ${width} cm`
        },
        style: {
          fontSize: 12,
          color: '#2196F3',
          bold: true,
          fill: 'transparent',
          strokeColor: 'transparent'
        },
        constraints: NodeConstraints.None
      })

      // Left dimension label (Dài)
      diagram.add({
        id: 'floor_label_height',
        offsetX: -padding / 2,
        offsetY: height / 2,
        width: 150,
        height: 30,
        rotateAngle: -90,
        shape: {
          type: 'Text',
          content: `Dài: ${height} cm`
        },
        style: {
          fontSize: 12,
          color: '#2196F3',
          bold: true,
          fill: 'transparent',
          strokeColor: 'transparent'
        },
        constraints: NodeConstraints.None
      })

    },

    renderRoom(diagram, room) {
      if (!room.polygon || room.polygon.length < 3) {
        return
      }

      // Tính bounds
      const xs = room.polygon.map((p) => p[0])
      const ys = room.polygon.map((p) => p[1])
      const minX = Math.min(...xs)
      const maxX = Math.max(...xs)
      const minY = Math.min(...ys)
      const maxY = Math.max(...ys)
      const width = maxX - minX
      const height = maxY - minY
      const centerX = room.x + width / 2
      const centerY = room.y + height / 2

      // Tạo path data (tọa độ tương đối từ góc trên trái)
      const pathData =
        room.polygon
          .map((p, i) => {
            return i === 0 ? `M ${p[0]} ${p[1]}` : `L ${p[0]} ${p[1]}`
          })
          .join(' ') + ' Z'

      // Room boundary
      diagram.add({
        id: `room_${room.id}`,
        offsetX: room.x,
        offsetY: room.y,
        pivot: { x: 0, y: 0 },
        width: width,
        height: height,
        shape: {
          type: 'Path',
          data: pathData
        },
        style: {
          fill: 'rgba(33, 150, 243, 0.05)',
          strokeColor: '#2196F3',
          strokeWidth: 2
        },
        annotations: [
          {
            content: room.name,
            style: {
              fontSize: 14,
              bold: true,
              color: '#2196F3'
            },
            offset: { x: 0.5, y: 0 },
            verticalAlignment: 'Top',
            margin: { top: 5 }
          }
        ],
        constraints: NodeConstraints.None
      })

      // Render cages
      room.cages.forEach((cage, index) => {
        this.renderCage(diagram, cage, room)
      })

      // Render racks
      room.racks.forEach((rack) => {
        this.renderRack(diagram, rack, room)
      })

      // Render equipments
      room.equipments.forEach((equipment) => {
        this.renderEquipment(diagram, equipment, room)
      })
    },

    renderCage(diagram, cage, room) {
      if (!cage.polygon || cage.polygon.length < 3) {
        console.warn(`        ⚠️ Cage ${cage.name || cage.id} has invalid polygon:`, cage.polygon)
        return
      }

      const xs = cage.polygon.map((p) => p[0])
      const ys = cage.polygon.map((p) => p[1])
      const minX = Math.min(...xs)
      const maxX = Math.max(...xs)
      const minY = Math.min(...ys)
      const maxY = Math.max(...ys)
      const width = maxX - minX
      const height = maxY - minY

      const pathData =
        cage.polygon
          .map((p, i) => {
            const relX = p[0] - minX
            const relY = p[1] - minY
            return i === 0 ? `M ${relX} ${relY}` : `L ${relX} ${relY}`
          })
          .join(' ') + ' Z'

      const nodeId = `cage_${cage.id}`
      const posX = room.x + (cage.x || 0)
      const posY = room.y + (cage.y || 0)

      diagram.add({
        id: nodeId,
        offsetX: posX,
        offsetY: posY,
        pivot: { x: 0, y: 0 },
        width: width,
        height: height,
        shape: {
          type: 'Path',
          data: pathData
        },
        style: {
          fill: 'rgba(255, 152, 0, 0.08)',
          strokeColor: '#FF9800',
          strokeWidth: 2,
          strokeDashArray: '5,5'
        },
        annotations: [
          {
            content: cage.name,
            style: {
              fontSize: 10,
              bold: true,
              color: '#FF9800'
            },
            offset: { x: 0.01, y: -0.1 },
            horizontalAlignment: 'Left',
            verticalAlignment: 'Top',
            margin: { left: 5, top: 5 }
          }
        ],
        constraints: NodeConstraints.None,
        zIndex: 5
      })
    },

    renderRack(diagram, rack, room) {
      const rackColor = rack.color || '#2196F3'
      const rackHtml = `
        <div style="
          width: 100%;
          height: 100%;
          display: flex;
          flex-direction: column;
          background: #fff;
          border: 1.5px solid ${rackColor};
          border-radius: 3px;
          padding: 3px;
          box-sizing: border-box;
        ">
          <div style="
            font-size: 9px;
            font-weight: bold;
            text-align: center;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            color: ${rackColor};
          ">${rack.name}</div>
          <img src="/icons/thiet-bi/rack.png" style="width: 100%; height: 70%; object-fit: contain; filter: drop-shadow(0 2px 4px ${rackColor}40);" />
        </div>
      `

      diagram.add({
        id: `rack_${rack.id}`,
        offsetX: room.x + rack.x,
        offsetY: room.y + rack.y,
        pivot: { x: 0, y: 0 },
        width: rack.width,
        height: rack.height,
        shape: {
          type: 'HTML',
          content: rackHtml
        },
        constraints: NodeConstraints.None,
        zIndex: 10
      })
    },

    renderEquipment(diagram, equipment, room) {
      const icon = this.getEquipmentIcon(equipment.type)
      const equipmentHtml = `
        <div style="
          width: 100%;
          height: 100%;
          display: flex;
          flex-direction: column;
          background: #fff;
          border: 1.5px solid #FF9800;
          border-radius: 3px;
          padding: 3px;
          box-sizing: border-box;
        ">
          <div style="
            font-size: 9px;
            font-weight: bold;
            text-align: center;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          ">${equipment.name}</div>
          <div style="font-size: 24px; text-align: center; flex: 1; display: flex; align-items: center; justify-content: center;">${icon}</div>
          <div style="font-size: 8px; text-align: center;">${equipment.type || ''}</div>
        </div>
      `

      diagram.add({
        id: `equipment_${equipment.id}`,
        offsetX: room.x + equipment.x,
        offsetY: room.y + equipment.y,
        pivot: { x: 0, y: 0 },
        width: equipment.width,
        height: equipment.height,
        shape: {
          type: 'HTML',
          content: equipmentHtml
        },
        constraints: NodeConstraints.None,
        zIndex: 10
      })
    },

    getEquipmentIcon(type) {
      const icons = {
        Switch: '🔀',
        Router: '🌐',
        Firewall: '🛡️',
        Server: '🖥️',
        Storage: '💾',
        UPS: '🔋',
        PDU: '⚡',
        AC: '❄️',
        Camera: '📹',
        Sensor: '📡'
      }
      return icons[type] || '📦'
    },

    handleClose() {
      this.visible = false
      this.floorData = null

      // Clear diagram
      if (this.$refs.viewAllDiagram && this.$refs.viewAllDiagram.ej2Instances) {
        this.$refs.viewAllDiagram.ej2Instances.clear()
      }
    }
  }
}
</script>

<style>
/* Modal size override */
.view-all-modal-custom .modal-dialog {
  max-width: 95vw !important;
  width: 95vw !important;
  margin: 1rem auto;
}

.view-all-modal-custom .modal-content {
  height: 95vh;
}

.view-all-modal-custom .modal-body {
  padding: 1rem;
  height: calc(95vh - 60px);
  overflow: hidden;
}
</style>

<style scoped>
.view-all-container {
  display: flex;
  gap: 20px;
  height: 100%;
  min-height: 500px;
}

.diagram-wrapper {
  flex: 1;
  border: 1px solid #ddd;
  background: #fafafa;
  overflow: hidden;
}

.legend-panel {
  width: 220px;
  padding: 15px;
  background: #f5f5f5;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.legend-panel h4 {
  margin: 0 0 15px 0;
  font-size: 14px;
  font-weight: bold;
  color: #333;
}

.zoom-controls {
  display: flex;
  gap: 5px;
  margin-bottom: 15px;
}

.zoom-controls .btn {
  flex: 1;
  padding: 5px;
}

.legend-item {
  margin-bottom: 10px;
}

.legend-content {
  display: flex;
  align-items: center;
  gap: 10px;
}

.legend-color {
  width: 30px;
  height: 30px;
  border-radius: 3px;
  flex-shrink: 0;
}

.legend-item span {
  font-size: 13px;
  color: #666;
}

.legend-divider {
  height: 1px;
  background: #ddd;
  margin: 15px 0;
}

.legend-actions {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.legend-actions .btn {
  width: 100%;
}

.modal-footer-custom {
  margin-top: 20px;
  padding-top: 15px;
  border-top: 1px solid #dee2e6;
  text-align: right;
}
</style>
