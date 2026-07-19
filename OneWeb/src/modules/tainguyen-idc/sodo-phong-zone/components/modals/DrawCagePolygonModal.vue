<template>
  <b-modal id="drawCagePolygonModal" ref="drawCagePolygonModal" size="xl" hide-footer @hidden="onModalHidden">
    <template #modal-header>
      <h5 class="modal-title">
        <i :class="isEditingExisting ? 'fa fa-edit' : 'fa fa-draw-polygon'"></i>
        {{ isEditingExisting ? 'Sửa lồng quây: ' : 'Vẽ lồng quây: ' }}{{ cageData ? cageData.name : '' }}
      </h5>
      <button type="button" class="close" @click="onCancel">
        <span aria-hidden="true">&times;</span>
      </button>
    </template>

    <div v-if="cageData">
      <div v-if="!editMode">
        <div class="text-success small">
          <span>📏 Khoảng cách: {{ getMouseDistanceInfo() }}</span>
        </div>
        <div class="text-muted small">
          <span>Vị trí con trỏ: {{ getMouseFloorCoords() }}</span>
        </div>
      </div>
      <div v-if="editMode" class="mt-2 alert alert-info small py-2">
        <strong>💡 Hướng dẫn:</strong>
        <span class="ml-2">Kéo điểm để di chuyển</span>
        <span class="ml-2">• Click cạnh để thêm điểm</span>
        <span class="ml-2">• Right-click điểm để xóa</span>
      </div>
      <div class="drawing-container">
        <div class="canvas-wrapper" @wheel="onCanvasWheel">
          <canvas
            ref="canvas"
            :width="canvasWidth"
            :height="canvasHeight"
            @click="onCanvasClick"
            @mousedown="onCanvasMouseDown"
            @mousemove="onCanvasMouseMove"
            @mouseup="onCanvasMouseUp"
            @mouseleave="onCanvasMouseLeave"
            @contextmenu="onCanvasRightClick"
            :style="{
              border: '2px solid #ddd',
              cursor: getCursorStyle(),
              background: '#f9f9f9',
              transform: `scale(${zoomLevel})`,
              transformOrigin: 'top left'
            }"
          ></canvas>
        </div>
      </div>

      <div class="mt-2 d-flex justify-content-center">
        <div class="btn-group btn-group-sm">
          <button class="btn btn-outline-secondary" @click="zoomOut" :disabled="zoomLevel <= 0.5">
            <i class="fa fa-search-minus"></i> Zoom Out
          </button>
          <button class="btn btn-outline-secondary" @click="resetZoom">
            <i class="fa fa-expand"></i> {{ Math.round(zoomLevel * 100) }}%
          </button>
          <button class="btn btn-outline-secondary" @click="zoomIn" :disabled="zoomLevel >= 3">
            <i class="fa fa-search-plus"></i> Zoom In
          </button>
        </div>
      </div>

      <div class="mt-3 d-flex justify-content-between">
        <div>
          <button
            v-if="!editMode"
            class="btn btn-sm btn-warning mr-2"
            @click="undoLastPoint"
            :disabled="polygonPoints.length === 0"
          >
            <i class="fa fa-undo"></i> Hủy điểm cuối
          </button>
          <button
            v-if="!editMode"
            class="btn btn-sm btn-secondary mr-2"
            @click="clearAllPoints"
            :disabled="polygonPoints.length === 0"
          >
            <i class="fa fa-trash"></i> Xóa tất cả
          </button>
          <button v-if="polygonPoints.length >= 3 && !editMode" class="btn btn-sm btn-info" @click="enterEditMode">
            <i class="fa fa-edit"></i> Chỉnh sửa
          </button>
          <button v-if="editMode" class="btn btn-sm btn-success" @click="exitEditMode">
            <i class="fa fa-check"></i> Hoàn tất chỉnh sửa
          </button>
        </div>
        <div>
          <button class="btn btn-secondary mr-2" @click="onCancel"><i class="fa fa-times"></i> Hủy</button>
          <button class="btn btn-primary" @click="onConfirm" :disabled="polygonPoints.length < 3 || editMode">
            <i class="fa fa-check"></i> Lưu
          </button>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
export default {
  name: 'DrawCagePolygonModal',
  props: {
    roomWidth: {
      type: Number,
      default: 2000
    },
    roomHeight: {
      type: Number,
      default: 1500
    }
  },
  data () {
    return {
      cageData: null,
      polygonPoints: [],
      currentMousePos: null,
      canvasWidth: 0,
      canvasHeight: 0,
      scaleX: 1,
      scaleY: 1,
      zoomLevel: 1,
      alignThreshold: 15, // cm
      // Perfect size snap - bội số của 60cm (theo grid)
      perfectSizes: [
        60, 120, 180, 240, 300, 360, 420, 480, 540, 600, 660, 720, 780, 840, 900, 960, 1020, 1080, 1140, 1200, 1260,
        1320, 1380, 1440, 1500, 1560, 1620, 1680, 1740, 1800
      ],
      sizeSnapThreshold: 30, // cm - ngưỡng snap to perfect size
      // Edit mode
      editMode: false,
      selectedPointIndex: -1,
      isDraggingPoint: false,
      hoveredPointIndex: -1,
      hoveredEdgeIndex: -1,
      initialPolygonLoaded: false
    }
  },

  computed: {
    isEditingExisting () {
      return this.initialPolygonLoaded
    }
  },

  mounted () {
    const maxCanvasWidth = Math.min(window.innerWidth * 0.85, 1400)
    const maxCanvasHeight = Math.min(window.innerHeight * 0.65, 800)

    const scaleByWidth = maxCanvasWidth / this.roomWidth
    const scaleByHeight = maxCanvasHeight / this.roomHeight
    const scale = Math.min(scaleByWidth, scaleByHeight)

    this.canvasWidth = Math.floor(this.roomWidth * scale)
    this.canvasHeight = Math.floor(this.roomHeight * scale)

    window.addEventListener('keydown', this.onKeyDown)
  },

  beforeDestroy () {
    window.removeEventListener('keydown', this.onKeyDown)
  },

  methods: {
    show (cageData, existingPolygon = null) {
      this.cageData = { ...cageData }
      this.currentMousePos = null
      this.editMode = false
      this.selectedPointIndex = -1
      this.isDraggingPoint = false

      // Nếu có polygon hiện tại (edit mode), load nó vào
      if (existingPolygon && Array.isArray(existingPolygon) && existingPolygon.length >= 3) {
        this.polygonPoints = existingPolygon.map((p) => [p[0], p[1]])
        this.initialPolygonLoaded = true
      } else {
        this.polygonPoints = []
        this.initialPolygonLoaded = false
      }

      this.scaleX = this.canvasWidth / this.roomWidth
      this.scaleY = this.canvasHeight / this.roomHeight

      this.$refs.drawCagePolygonModal.show()

      this.$nextTick(() => {
        this.drawCanvas()

        // Tự động vào edit mode nếu đang edit polygon hiện có
        if (existingPolygon && existingPolygon.length >= 3) {
          this.$nextTick(() => {
            this.enterEditMode()
          })
        }
      })
    },

    hide () {
      this.$refs.drawCagePolygonModal.hide()
    },

    onCanvasClick (event) {
      if (this.editMode) {
        // ✅ EDIT MODE: Click vào cạnh để thêm điểm
        if (this.hoveredEdgeIndex !== -1) {
          this.addPointToEdge(event)
        }
        return
      }

      const rect = this.$refs.canvas.getBoundingClientRect()
      const canvasX = (event.clientX - rect.left) / this.zoomLevel
      const canvasY = (event.clientY - rect.top) / this.zoomLevel

      let floorX = canvasX / this.scaleX
      let floorY = canvasY / this.scaleY

      // Smart align với điểm cuối
      if (this.polygonPoints.length > 0) {
        const lastPoint = this.polygonPoints[this.polygonPoints.length - 1]
        const aligned = this.smartAlign(lastPoint, [floorX, floorY])
        floorX = aligned[0]
        floorY = aligned[1]
      } else {
        // Snap to grid 10cm
        floorX = Math.round(floorX / 10) * 10
        floorY = Math.round(floorY / 10) * 10
      }

      // ✅ Giới hạn trong room - KHÔNG CHO VẼ RA NGOÀI
      floorX = Math.max(0, Math.min(this.roomWidth, floorX))
      floorY = Math.max(0, Math.min(this.roomHeight, floorY))

      this.polygonPoints.push([floorX, floorY])
      this.drawCanvas()
    },

    onCanvasMouseDown (event) {
      if (!this.editMode) return

      const rect = this.$refs.canvas.getBoundingClientRect()
      const canvasX = (event.clientX - rect.left) / this.zoomLevel
      const canvasY = (event.clientY - rect.top) / this.zoomLevel

      const floorX = canvasX / this.scaleX
      const floorY = canvasY / this.scaleY

      // Tìm điểm gần nhất
      const pointIndex = this.findNearestPoint(floorX, floorY)

      if (pointIndex !== -1) {
        this.selectedPointIndex = pointIndex
        this.isDraggingPoint = true
      }
    },

    onCanvasMouseUp (event) {
      if (this.editMode && this.isDraggingPoint) {
        this.isDraggingPoint = false
        this.selectedPointIndex = -1
      }
    },

    onCanvasMouseMove (event) {
      const rect = this.$refs.canvas.getBoundingClientRect()
      const canvasX = (event.clientX - rect.left) / this.zoomLevel
      const canvasY = (event.clientY - rect.top) / this.zoomLevel

      let floorX = canvasX / this.scaleX
      let floorY = canvasY / this.scaleY

      // Nếu đang kéo điểm trong edit mode
      if (this.editMode && this.isDraggingPoint && this.selectedPointIndex !== -1) {
        // Snap to grid 10cm
        floorX = Math.round(floorX / 10) * 10
        floorY = Math.round(floorY / 10) * 10

        // ✅ Giới hạn trong room - KHÔNG CHO KÉO RA NGOÀI
        floorX = Math.max(0, Math.min(this.roomWidth, floorX))
        floorY = Math.max(0, Math.min(this.roomHeight, floorY))

        // Cập nhật vị trí điểm
        this.$set(this.polygonPoints, this.selectedPointIndex, [floorX, floorY])

        const snappedCanvasX = floorX * this.scaleX
        const snappedCanvasY = floorY * this.scaleY
        this.currentMousePos = { x: snappedCanvasX, y: snappedCanvasY }

        this.drawCanvas()
        return
      }

      // ✅ EDIT MODE: Detect hover trên điểm hoặc cạnh
      if (this.editMode && !this.isDraggingPoint) {
        this.hoveredPointIndex = this.findNearestPoint(floorX, floorY)

        if (this.hoveredPointIndex === -1) {
          this.hoveredEdgeIndex = this.findNearestEdge(floorX, floorY)
        } else {
          this.hoveredEdgeIndex = -1
        }

        this.drawCanvas()
        return
      }

      // Logic vẽ mới
      if (!this.editMode && this.polygonPoints.length > 0) {
        const lastPoint = this.polygonPoints[this.polygonPoints.length - 1]
        const aligned = this.smartAlign(lastPoint, [floorX, floorY])
        floorX = aligned[0]
        floorY = aligned[1]
      } else if (!this.editMode) {
        floorX = Math.round(floorX / 10) * 10
        floorY = Math.round(floorY / 10) * 10
      }

      // ✅ Giới hạn cursor trong room
      floorX = Math.max(0, Math.min(this.roomWidth, floorX))
      floorY = Math.max(0, Math.min(this.roomHeight, floorY))

      const snappedCanvasX = floorX * this.scaleX
      const snappedCanvasY = floorY * this.scaleY

      this.currentMousePos = { x: snappedCanvasX, y: snappedCanvasY }

      if (this.polygonPoints.length > 0) {
        this.drawCanvas()
      }
    },

    onCanvasMouseLeave () {
      this.currentMousePos = null
      this.isDraggingPoint = false
      this.selectedPointIndex = -1
      this.hoveredPointIndex = -1
      this.hoveredEdgeIndex = -1
      this.drawCanvas()
    },

    findNearestPoint (floorX, floorY) {
      let minDist = Infinity
      let nearestIndex = -1
      const threshold = 20 // cm

      this.polygonPoints.forEach((point, index) => {
        const dx = point[0] - floorX
        const dy = point[1] - floorY
        const dist = Math.sqrt(dx * dx + dy * dy)

        if (dist < minDist && dist < threshold) {
          minDist = dist
          nearestIndex = index
        }
      })

      return nearestIndex
    },

    enterEditMode () {
      this.editMode = true
      this.hoveredPointIndex = -1
      this.hoveredEdgeIndex = -1
    },

    exitEditMode () {
      this.editMode = false
      this.selectedPointIndex = -1
      this.isDraggingPoint = false
      this.hoveredPointIndex = -1
      this.hoveredEdgeIndex = -1
    },

    smartAlign (fromPoint, toPoint) {
      const dx = toPoint[0] - fromPoint[0]
      const dy = toPoint[1] - fromPoint[1]
      const distance = Math.sqrt(dx * dx + dy * dy)

      if (distance < 5) return toPoint

      let alignedX = toPoint[0]
      let alignedY = toPoint[1]

      // Snap ngang
      if (Math.abs(dy) < this.alignThreshold) {
        alignedY = fromPoint[1]

        // PERFECT SIZE: Snap độ dài ngang to perfect size
        const lengthX = Math.abs(dx)
        const perfectX = this.snapToPerfectSize(lengthX)
        if (perfectX !== null) {
          alignedX = fromPoint[0] + Math.sign(dx) * perfectX
        }
      }
      // Snap dọc
      else if (Math.abs(dx) < this.alignThreshold) {
        alignedX = fromPoint[0]

        // PERFECT SIZE: Snap độ dài dọc to perfect size
        const lengthY = Math.abs(dy)
        const perfectY = this.snapToPerfectSize(lengthY)
        if (perfectY !== null) {
          alignedY = fromPoint[1] + Math.sign(dy) * perfectY
        }
      } else {
        // Snap to grid 10cm
        alignedX = Math.round(alignedX / 10) * 10
        alignedY = Math.round(alignedY / 10) * 10
      }

      return [alignedX, alignedY]
    },

    // Snap độ dài to perfect size (kích thước chuẩn)
    snapToPerfectSize (length) {
      for (const size of this.perfectSizes) {
        if (Math.abs(length - size) <= this.sizeSnapThreshold) {
          return size
        }
      }
      return null // Không snap nếu không gần perfect size nào
    },

    drawCanvas () {
      const canvas = this.$refs.canvas
      if (!canvas) return

      const ctx = canvas.getContext('2d')
      ctx.clearRect(0, 0, this.canvasWidth, this.canvasHeight)

      // Vẽ grid
      this.drawGrid(ctx)

      if (this.polygonPoints.length > 0) {
        // Vẽ polygon
        ctx.beginPath()
        const firstPoint = this.polygonPoints[0]
        const firstCanvasX = firstPoint[0] * this.scaleX
        const firstCanvasY = firstPoint[1] * this.scaleY
        ctx.moveTo(firstCanvasX, firstCanvasY)

        for (let i = 1; i < this.polygonPoints.length; i++) {
          const point = this.polygonPoints[i]
          const canvasX = point[0] * this.scaleX
          const canvasY = point[1] * this.scaleY
          ctx.lineTo(canvasX, canvasY)
        }

        if (this.polygonPoints.length >= 3) {
          ctx.lineTo(firstCanvasX, firstCanvasY)
          ctx.fillStyle = (this.cageData.color || '#4CAF50') + '20'
          ctx.fill()
        }

        ctx.strokeStyle = this.cageData.color || '#4CAF50'
        ctx.lineWidth = 2
        ctx.stroke()

        // Vẽ đường tạm thời đến cursor
        if (this.currentMousePos && this.polygonPoints.length >= 1 && !this.editMode) {
          const lastPoint = this.polygonPoints[this.polygonPoints.length - 1]
          const lastCanvasX = lastPoint[0] * this.scaleX
          const lastCanvasY = lastPoint[1] * this.scaleY

          ctx.beginPath()
          ctx.moveTo(lastCanvasX, lastCanvasY)
          ctx.lineTo(this.currentMousePos.x, this.currentMousePos.y)
          ctx.strokeStyle = this.cageData.color || '#4CAF50'
          ctx.lineWidth = 1
          ctx.setLineDash([5, 5])
          ctx.stroke()
          ctx.setLineDash([])

          // Cursor indicator
          ctx.beginPath()
          ctx.arc(this.currentMousePos.x, this.currentMousePos.y, 3, 0, 2 * Math.PI)
          ctx.fillStyle = '#ff6600'
          ctx.fill()
          ctx.strokeStyle = '#fff'
          ctx.lineWidth = 1
          ctx.stroke()

          // Vẽ độ dài từ điểm cuối đến cursor
          this.drawDistanceToMouse(ctx, lastPoint)
        }

        // Vẽ độ dài các cạnh
        this.drawEdgeLengths(ctx)

        // ✅ Vẽ hover effect cho cạnh
        if (this.editMode && this.hoveredEdgeIndex !== -1) {
          const p1 = this.polygonPoints[this.hoveredEdgeIndex]
          const p2 = this.polygonPoints[(this.hoveredEdgeIndex + 1) % this.polygonPoints.length]

          ctx.beginPath()
          ctx.moveTo(p1[0] * this.scaleX, p1[1] * this.scaleY)
          ctx.lineTo(p2[0] * this.scaleX, p2[1] * this.scaleY)
          ctx.strokeStyle = '#ff9800'
          ctx.lineWidth = 4
          ctx.stroke()

          const midX = ((p1[0] + p2[0]) / 2) * this.scaleX
          const midY = ((p1[1] + p2[1]) / 2) * this.scaleY

          ctx.beginPath()
          ctx.arc(midX, midY, 8, 0, 2 * Math.PI)
          ctx.fillStyle = '#ff9800'
          ctx.fill()
          ctx.strokeStyle = '#fff'
          ctx.lineWidth = 2
          ctx.stroke()

          ctx.strokeStyle = '#fff'
          ctx.lineWidth = 2
          ctx.beginPath()
          ctx.moveTo(midX - 4, midY)
          ctx.lineTo(midX + 4, midY)
          ctx.moveTo(midX, midY - 4)
          ctx.lineTo(midX, midY + 4)
          ctx.stroke()
        }

        // Vẽ các điểm
        this.polygonPoints.forEach((point, index) => {
          const canvasX = point[0] * this.scaleX
          const canvasY = point[1] * this.scaleY

          const isSelected = this.editMode && index === this.selectedPointIndex
          const isHovered = this.editMode && index === this.hoveredPointIndex

          let pointRadius = this.editMode ? 8 : 5
          if (isHovered || isSelected) pointRadius += 2

          ctx.beginPath()
          ctx.arc(canvasX, canvasY, pointRadius, 0, 2 * Math.PI)

          if (isSelected) {
            ctx.fillStyle = '#ff0000'
          } else if (isHovered) {
            ctx.fillStyle = '#ff9800'
          } else if (index === 0) {
            ctx.fillStyle = '#28a745'
          } else {
            ctx.fillStyle = this.cageData.color || '#4CAF50'
          }

          ctx.fill()
          ctx.strokeStyle = '#fff'
          ctx.lineWidth = 2
          ctx.stroke()

          // ✅ Glow effect khi selected
          if (isSelected) {
            ctx.beginPath()
            ctx.arc(canvasX, canvasY, pointRadius + 4, 0, 2 * Math.PI)
            ctx.strokeStyle = 'rgba(255, 0, 0, 0.3)'
            ctx.lineWidth = 3
            ctx.stroke()
          }

          // Số thứ tự
          ctx.fillStyle = '#000'
          ctx.font = 'bold 12px Arial'
          ctx.fillText((index + 1).toString(), canvasX + 8, canvasY - 8)
        })
      }
    },

    drawGrid (ctx) {
      // Grid 60cm x 60cm theo kích thước phòng thực tế
      const floorGridSize = 60 // cm
      const canvasGridSize = floorGridSize * this.scaleX // Chuyển sang canvas size

      ctx.strokeStyle = '#e0e0e0'
      ctx.lineWidth = 0.5

      for (let x = 0; x <= this.canvasWidth; x += canvasGridSize) {
        ctx.beginPath()
        ctx.moveTo(x, 0)
        ctx.lineTo(x, this.canvasHeight)
        ctx.stroke()
      }

      for (let y = 0; y <= this.canvasHeight; y += canvasGridSize) {
        ctx.beginPath()
        ctx.moveTo(0, y)
        ctx.lineTo(this.canvasWidth, y)
        ctx.stroke()
      }

      // Vẽ viền phòng
      ctx.strokeStyle = '#FF9800'
      ctx.lineWidth = 3
      ctx.strokeRect(0, 0, this.canvasWidth, this.canvasHeight)
    },

    drawEdgeLengths (ctx) {
      if (this.polygonPoints.length < 2) return

      ctx.textAlign = 'center'
      ctx.textBaseline = 'middle'

      // Vẽ độ dài cho các cạnh
      for (let i = 0; i < this.polygonPoints.length; i++) {
        const p1 = this.polygonPoints[i]
        const p2 = this.polygonPoints[(i + 1) % this.polygonPoints.length]

        // Chỉ vẽ nếu đã có điểm tiếp theo (không vẽ cho cạnh chưa hoàn thành)
        if (i === this.polygonPoints.length - 1 && this.polygonPoints.length < 3) {
          continue
        }

        // Tính độ dài cạnh (cm)
        const dx = p2[0] - p1[0]
        const dy = p2[1] - p1[1]
        const length = Math.sqrt(dx * dx + dy * dy)

        // Vị trí giữa cạnh
        const midX = ((p1[0] + p2[0]) / 2) * this.scaleX
        const midY = ((p1[1] + p2[1]) / 2) * this.scaleY

        // Tính góc để offset label ra ngoài cạnh
        const angle = Math.atan2(dy, dx)
        const offsetDistance = 12
        const offsetX = Math.cos(angle + Math.PI / 2) * offsetDistance
        const offsetY = Math.sin(angle + Math.PI / 2) * offsetDistance

        const labelX = midX + offsetX
        const labelY = midY + offsetY

        // Vẽ text
        const text = `${Math.round(length)} cm`
        ctx.fillStyle = '#0066cc'
        ctx.font = '11px Arial'
        ctx.fillText(text, labelX, labelY)
      }

      // Reset text align
      ctx.textAlign = 'left'
    },

    // Vẽ độ dài từ điểm cuối đến cursor
    drawDistanceToMouse (ctx, lastPoint) {
      if (!this.currentMousePos) return

      const mouseFloorX = this.currentMousePos.x / this.scaleX
      const mouseFloorY = this.currentMousePos.y / this.scaleY

      const dx = mouseFloorX - lastPoint[0]
      const dy = mouseFloorY - lastPoint[1]
      const distance = Math.sqrt(dx * dx + dy * dy)

      if (distance < 5) return // Quá gần không hiển thị

      // Vị trí giữa đường
      const midX = (lastPoint[0] * this.scaleX + this.currentMousePos.x) / 2
      const midY = (lastPoint[1] * this.scaleY + this.currentMousePos.y) / 2

      // Tính góc để offset label
      const angle = Math.atan2(dy, dx)
      const offsetDistance = 12
      const offsetX = Math.cos(angle + Math.PI / 2) * offsetDistance
      const offsetY = Math.sin(angle + Math.PI / 2) * offsetDistance

      const labelX = midX + offsetX
      const labelY = midY + offsetY

      // Kiểm tra có đang snap to perfect size không
      const isPerfectSize = this.snapToPerfectSize(distance) !== null

      // Text - xanh lá nếu perfect size, xanh dương nếu không
      const text = `${Math.round(distance)} cm`
      ctx.fillStyle = isPerfectSize ? '#28a745' : '#0066cc'
      ctx.font = '11px Arial'
      ctx.textAlign = 'center'
      ctx.textBaseline = 'middle'
      ctx.fillText(text, labelX, labelY)

      ctx.textAlign = 'left'
    },

    undoLastPoint () {
      if (this.polygonPoints.length > 0) {
        this.polygonPoints.pop()
        this.drawCanvas()
      }
    },

    clearAllPoints () {
      this.polygonPoints = []
      this.drawCanvas()
    },

    zoomIn () {
      this.zoomLevel = Math.min(3, this.zoomLevel + 0.25)
    },

    zoomOut () {
      this.zoomLevel = Math.max(0.5, this.zoomLevel - 0.25)
    },

    resetZoom () {
      this.zoomLevel = 1
    },

    onCanvasWheel (event) {
      event.preventDefault()
      if (event.deltaY < 0) {
        this.zoomIn()
      } else {
        this.zoomOut()
      }
    },

    getMouseFloorCoords () {
      if (!this.currentMousePos) return ''
      const floorX = Math.round(this.currentMousePos.x / this.scaleX)
      const floorY = Math.round(this.currentMousePos.y / this.scaleY)
      return `X: ${floorX} cm, Y: ${floorY} cm`
    },

    getMouseDistanceInfo () {
      if (!this.currentMousePos || this.polygonPoints.length === 0) return ''

      const lastPoint = this.polygonPoints[this.polygonPoints.length - 1]
      const mouseFloorX = this.currentMousePos.x / this.scaleX
      const mouseFloorY = this.currentMousePos.y / this.scaleY

      const dx = mouseFloorX - lastPoint[0]
      const dy = mouseFloorY - lastPoint[1]
      const distance = Math.sqrt(dx * dx + dy * dy)

      // Kiểm tra perfect size
      const perfectSize = this.snapToPerfectSize(distance)
      const isPerfect = perfectSize !== null

      let direction = ''
      const absDx = Math.abs(dx)
      const absDy = Math.abs(dy)

      if (absDy < this.alignThreshold) {
        direction = '↔️ Ngang'
      } else if (absDx < this.alignThreshold) {
        direction = '↕️ Dọc'
      } else {
        direction = '⤢ Tự do'
      }

      // Hiển thị với icon nếu perfect size
      const distText = isPerfect ? `✓ ${Math.round(distance)} cm` : `${Math.round(distance)} cm`
      return `${distText} ${direction}`
    },

    // ✅ Tìm cạnh gần nhất
    findNearestEdge (floorX, floorY) {
      if (this.polygonPoints.length < 2) return -1

      let minDist = Infinity
      let nearestEdgeIndex = -1
      const threshold = 20

      for (let i = 0; i < this.polygonPoints.length; i++) {
        const p1 = this.polygonPoints[i]
        const p2 = this.polygonPoints[(i + 1) % this.polygonPoints.length]
        const dist = this.distanceToSegment(floorX, floorY, p1, p2)

        if (dist < minDist && dist < threshold) {
          minDist = dist
          nearestEdgeIndex = i
        }
      }

      return nearestEdgeIndex
    },

    distanceToSegment (px, py, p1, p2) {
      const x1 = p1[0]
      const y1 = p1[1]
      const x2 = p2[0]
      const y2 = p2[1]
      const A = px - x1
      const B = py - y1
      const C = x2 - x1
      const D = y2 - y1
      const dot = A * C + B * D
      const lenSq = C * C + D * D
      const param = lenSq !== 0 ? dot / lenSq : -1
      let xx
      let yy

      if (param < 0) {
        xx = x1
        yy = y1
      } else if (param > 1) {
        xx = x2
        yy = y2
      } else {
        xx = x1 + param * C
        yy = y1 + param * D
      }

      const dx = px - xx
      const dy = py - yy
      return Math.sqrt(dx * dx + dy * dy)
    },

    // ✅ Thêm điểm vào cạnh
    addPointToEdge (event) {
      if (this.hoveredEdgeIndex === -1) return

      const rect = this.$refs.canvas.getBoundingClientRect()
      const canvasX = (event.clientX - rect.left) / this.zoomLevel
      const canvasY = (event.clientY - rect.top) / this.zoomLevel

      let floorX = Math.round(canvasX / this.scaleX / 10) * 10
      let floorY = Math.round(canvasY / this.scaleY / 10) * 10

      floorX = Math.max(0, Math.min(this.roomWidth, floorX))
      floorY = Math.max(0, Math.min(this.roomHeight, floorY))

      this.polygonPoints.splice(this.hoveredEdgeIndex + 1, 0, [floorX, floorY])
      this.selectedPointIndex = this.hoveredEdgeIndex + 1
      this.hoveredEdgeIndex = -1
      this.drawCanvas()

      this.$toast.success('Đã thêm điểm mới', { duration: 1500 })
    },

    // ✅ Xóa điểm
    deletePoint (index) {
      if (this.polygonPoints.length <= 3) {
        this.$toast.warning('Polygon phải có ít nhất 3 điểm')
        return
      }

      this.polygonPoints.splice(index, 1)

      if (this.selectedPointIndex === index) {
        this.selectedPointIndex = -1
      } else if (this.selectedPointIndex > index) {
        this.selectedPointIndex--
      }

      this.drawCanvas()
      this.$toast.success('Đã xóa điểm', { duration: 1500 })
    },

    // ✅ Right-click xóa điểm
    onCanvasRightClick (event) {
      event.preventDefault()
      if (!this.editMode) return

      const rect = this.$refs.canvas.getBoundingClientRect()
      const canvasX = (event.clientX - rect.left) / this.zoomLevel
      const canvasY = (event.clientY - rect.top) / this.zoomLevel
      const floorX = canvasX / this.scaleX
      const floorY = canvasY / this.scaleY
      const pointIndex = this.findNearestPoint(floorX, floorY)

      if (pointIndex !== -1) {
        this.deletePoint(pointIndex)
      }
    },

    // ✅ Dynamic cursor
    getCursorStyle () {
      if (this.editMode) {
        if (this.isDraggingPoint) return 'grabbing'
        if (this.hoveredPointIndex !== -1) return 'grab'
        if (this.hoveredEdgeIndex !== -1) return 'copy'
        return 'default'
      }
      return 'crosshair'
    },

    // ✅ Di chuyển điểm bằng arrow keys
    movePointWithArrowKeys (key, shiftKey) {
      if (this.selectedPointIndex === -1) return

      const step = shiftKey ? 50 : 10
      const point = this.polygonPoints[this.selectedPointIndex]
      let [x, y] = point

      switch (key) {
        case 'ArrowUp':
          y -= step
          break
        case 'ArrowDown':
          y += step
          break
        case 'ArrowLeft':
          x -= step
          break
        case 'ArrowRight':
          x += step
          break
      }

      x = Math.max(0, Math.min(this.roomWidth, x))
      y = Math.max(0, Math.min(this.roomHeight, y))

      this.$set(this.polygonPoints, this.selectedPointIndex, [x, y])
      this.drawCanvas()
    },

    onKeyDown (event) {
      if (!this.$refs.drawCagePolygonModal || !this.$refs.drawCagePolygonModal.$data.isVisible) {
        return
      }

      if (event.key === 'Escape') {
        this.onCancel()
      } else if (event.key === 'Enter' && this.polygonPoints.length >= 3 && !this.editMode) {
        this.onConfirm()
      } else if (event.key === 'z' && event.ctrlKey && !this.editMode) {
        event.preventDefault()
        this.undoLastPoint()
      } else if (
        (event.key === 'Delete' || event.key === 'Backspace') &&
        this.editMode &&
        this.selectedPointIndex !== -1
      ) {
        event.preventDefault()
        this.deletePoint(this.selectedPointIndex)
      } else if (
        this.editMode &&
        this.selectedPointIndex !== -1 &&
        ['ArrowUp', 'ArrowDown', 'ArrowLeft', 'ArrowRight'].includes(event.key)
      ) {
        event.preventDefault()
        this.movePointWithArrowKeys(event.key, event.shiftKey)
      } else if (event.key === 'Tab' && this.editMode && this.polygonPoints.length > 0) {
        event.preventDefault()
        if (event.shiftKey) {
          this.selectedPointIndex =
            this.selectedPointIndex <= 0 ? this.polygonPoints.length - 1 : this.selectedPointIndex - 1
        } else {
          this.selectedPointIndex = (this.selectedPointIndex + 1) % this.polygonPoints.length
        }
        this.drawCanvas()
      }
    },

    onConfirm () {
      if (this.polygonPoints.length < 3) {
        this.$toast.error('Polygon phải có ít nhất 3 điểm')
        return
      }

      // ✅ THÊM: Kiểm tra và đưa polygon vào trong nếu vượt biên
      const adjustedPolygon = this.constrainPolygonToRoom([...this.polygonPoints])

      const result = {
        cageData: this.cageData,
        polygon: adjustedPolygon,
        mode: this.isEditingExisting
      }

      this.$emit('confirm', result)
      this.hide()
    },

    // ✅ THÊM: Hàm đưa polygon vào trong phòng nếu vượt biên
    constrainPolygonToRoom (polygon) {
      // Tìm bounding box của polygon
      const xs = polygon.map((p) => p[0])
      const ys = polygon.map((p) => p[1])
      const minX = Math.min(...xs)
      const maxX = Math.max(...xs)
      const minY = Math.min(...ys)
      const maxY = Math.max(...ys)

      // Tính offset cần thiết để đưa polygon vào trong
      let offsetX = 0
      let offsetY = 0

      if (minX < 0) {
        offsetX = -minX
      } else if (maxX > this.roomWidth) {
        offsetX = this.roomWidth - maxX
      }

      if (minY < 0) {
        offsetY = -minY
      } else if (maxY > this.roomHeight) {
        offsetY = this.roomHeight - maxY
      }

      // Apply offset nếu cần
      if (offsetX !== 0 || offsetY !== 0) {
        return polygon.map((p) => [p[0] + offsetX, p[1] + offsetY])
      }

      return polygon
    },

    onCancel () {
      this.$emit('cancel')
      this.hide()
    },

    onModalHidden () {
      this.cageData = null
      this.polygonPoints = []
      this.currentMousePos = null
      this.zoomLevel = 1
      this.editMode = false
      this.selectedPointIndex = -1
      this.isDraggingPoint = false
      this.hoveredPointIndex = -1
      this.hoveredEdgeIndex = -1
      this.initialPolygonLoaded = false
    }
  }
}
</script>

<style scoped>
.drawing-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  overflow: auto;
  max-height: 70vh;
  border: 1px solid #ddd;
  border-radius: 4px;
  background: #f0f0f0;
}

.canvas-wrapper {
  padding: 20px;
  overflow: auto;
}
</style>
