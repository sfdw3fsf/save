<template>
  <div class="floor-diagram-tab">
    <div class="diagram-toolbar">
      <div class="toolbar-left">
        <button class="btn btn-sm btn-primary" @click="zoomIn">
          <i class="fa fa-search-plus"></i> Phóng to
        </button>
        <button class="btn btn-sm btn-primary" @click="zoomOut">
          <i class="fa fa-search-minus"></i> Thu nhỏ
        </button>
        <button class="btn btn-sm btn-secondary" @click="resetZoom">
          <i class="fa fa-refresh"></i> Đặt lại
        </button>
      </div>
      <div class="toolbar-right">
        <button class="btn btn-sm btn-info" @click="toggleGrid">
          <i class="fa fa-th"></i> {{ showGrid ? 'Ẩn lưới' : 'Hiện lưới' }}
        </button>
        <button class="btn btn-sm btn-success" @click="exportDiagram">
          <i class="fa fa-download"></i> Xuất sơ đồ
        </button>
      </div>
    </div>

    <div class="diagram-container" ref="diagramContainer">
      <canvas
        ref="canvas"
        :width="canvasWidth"
        :height="canvasHeight"
        @mousedown="handleMouseDown"
        @mousemove="handleMouseMove"
        @mouseup="handleMouseUp"
        @wheel="handleWheel"
      ></canvas>
    </div>

    <div class="diagram-legend">
      <div class="legend-title">Chú thích</div>
      <div class="legend-items">
        <div class="legend-item">
          <span class="legend-color" style="background-color: #4CAF50;"></span>
          <span>Rack đang hoạt động</span>
        </div>
        <div class="legend-item">
          <span class="legend-color" style="background-color: #FFC107;"></span>
          <span>Rack bảo trì</span>
        </div>
        <div class="legend-item">
          <span class="legend-color" style="background-color: #F44336;"></span>
          <span>Rack lỗi</span>
        </div>
        <div class="legend-item">
          <span class="legend-color" style="background-color: #9E9E9E;"></span>
          <span>Rack không sử dụng</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FloorDiagramTab',

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
      canvasWidth: 1200,
      canvasHeight: 800,
      zoom: 1,
      offsetX: 0,
      offsetY: 0,
      isDragging: false,
      dragStartX: 0,
      dragStartY: 0,
      showGrid: true,
      racks: []
    }
  },

  mounted() {
    this.loadFloorDiagram()
    this.drawDiagram()
  },

  methods: {
    async loadFloorDiagram() {
      // Load rack positions and data for this floor
      // This would call your API
      this.racks = [
        { id: 1, x: 100, y: 100, width: 60, height: 90, status: 'active', name: 'RACK-001' },
        { id: 2, x: 200, y: 100, width: 60, height: 90, status: 'active', name: 'RACK-002' },
        { id: 3, x: 300, y: 100, width: 60, height: 90, status: 'maintenance', name: 'RACK-003' }
      ]
      this.drawDiagram()
    },

    drawDiagram() {
      const canvas = this.$refs.canvas
      if (!canvas) return

      const ctx = canvas.getContext('2d')
      ctx.clearRect(0, 0, this.canvasWidth, this.canvasHeight)

      ctx.save()
      ctx.translate(this.offsetX, this.offsetY)
      ctx.scale(this.zoom, this.zoom)

      if (this.showGrid) {
        this.drawGrid(ctx)
      }

      this.racks.forEach(rack => {
        this.drawRack(ctx, rack)
      })

      ctx.restore()
    },

    drawGrid(ctx) {
      const gridSize = 50
      ctx.strokeStyle = '#e0e0e0'
      ctx.lineWidth = 0.5

      for (let x = 0; x < this.canvasWidth / this.zoom; x += gridSize) {
        ctx.beginPath()
        ctx.moveTo(x, 0)
        ctx.lineTo(x, this.canvasHeight / this.zoom)
        ctx.stroke()
      }

      for (let y = 0; y < this.canvasHeight / this.zoom; y += gridSize) {
        ctx.beginPath()
        ctx.moveTo(0, y)
        ctx.lineTo(this.canvasWidth / this.zoom, y)
        ctx.stroke()
      }
    },

    drawRack(ctx, rack) {
      const statusColors = {
        active: '#4CAF50',
        maintenance: '#FFC107',
        error: '#F44336',
        inactive: '#9E9E9E'
      }

      ctx.fillStyle = statusColors[rack.status] || '#9E9E9E'
      ctx.fillRect(rack.x, rack.y, rack.width, rack.height)

      ctx.strokeStyle = '#333'
      ctx.lineWidth = 2
      ctx.strokeRect(rack.x, rack.y, rack.width, rack.height)

      ctx.fillStyle = '#fff'
      ctx.font = '12px Arial'
      ctx.textAlign = 'center'
      ctx.fillText(rack.name, rack.x + rack.width / 2, rack.y + rack.height / 2)
    },

    zoomIn() {
      this.zoom = Math.min(this.zoom * 1.2, 3)
      this.drawDiagram()
    },

    zoomOut() {
      this.zoom = Math.max(this.zoom / 1.2, 0.5)
      this.drawDiagram()
    },

    resetZoom() {
      this.zoom = 1
      this.offsetX = 0
      this.offsetY = 0
      this.drawDiagram()
    },

    toggleGrid() {
      this.showGrid = !this.showGrid
      this.drawDiagram()
    },

    exportDiagram() {
      const canvas = this.$refs.canvas
      const link = document.createElement('a')
      link.download = `floor-diagram-${this.floorId}.png`
      link.href = canvas.toDataURL()
      link.click()
    },

    handleMouseDown(e) {
      this.isDragging = true
      this.dragStartX = e.clientX - this.offsetX
      this.dragStartY = e.clientY - this.offsetY
    },

    handleMouseMove(e) {
      if (!this.isDragging) return
      this.offsetX = e.clientX - this.dragStartX
      this.offsetY = e.clientY - this.dragStartY
      this.drawDiagram()
    },

    handleMouseUp() {
      this.isDragging = false
    },

    handleWheel(e) {
      e.preventDefault()
      const delta = e.deltaY > 0 ? 0.9 : 1.1
      this.zoom = Math.max(0.5, Math.min(3, this.zoom * delta))
      this.drawDiagram()
    }
  }
}
</script>

<style scoped>
.floor-diagram-tab {
  padding: 20px;
}

.diagram-toolbar {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
  padding: 10px;
  background: #f5f5f5;
  border-radius: 4px;
}

.toolbar-left,
.toolbar-right {
  display: flex;
  gap: 10px;
}

.diagram-container {
  border: 1px solid #ddd;
  border-radius: 4px;
  overflow: hidden;
  background: #fff;
  margin-bottom: 15px;
}

canvas {
  cursor: grab;
  display: block;
}

canvas:active {
  cursor: grabbing;
}

.diagram-legend {
  padding: 15px;
  background: #f9f9f9;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
}

.legend-title {
  font-weight: bold;
  margin-bottom: 10px;
  color: #333;
}

.legend-items {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 8px;
}

.legend-color {
  width: 20px;
  height: 20px;
  border-radius: 3px;
  border: 1px solid #333;
}
</style>
