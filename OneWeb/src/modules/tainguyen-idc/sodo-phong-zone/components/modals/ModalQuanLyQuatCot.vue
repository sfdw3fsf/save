<template>
  <b-modal id="modalQuanLyQuatCot" ref="modalQuanLyQuatCot" title="Quản lý Quạt và Cột" size="xl" no-close-on-backdrop
    no-close-on-esc hide-footer modal-class="modal-quan-ly-quat-cot" @hidden="handleModalClose">
    <div class="modal-content-wrapper">
      <!-- Toolbar -->
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <b-link href="javascript:void(0)" @click="onOpenQuanLyQuat">
              <i class="fa fa-fan"></i> Quản lý Quạt
            </b-link>
          </li>
          <li>
            <b-link href="javascript:void(0)" @click="onOpenQuanLyCot">
              <i class="fa fa-columns"></i> Quản lý Cột
            </b-link>
          </li>
          <li class="separator"></li>
          <li>
            <b-link href="javascript:void(0)" @click="onSave">
              <span class="icon one-save"></span>Ghi lại
            </b-link>
          </li>
          <li>
            <b-link href="javascript:void(0)" @click="onZoomIn" :class="{ disabled: isZoomInDisabled }">
              <i class="fa fa-search-plus"></i> Phóng to
            </b-link>
          </li>
          <li>
            <b-link href="javascript:void(0)" @click="onZoomOut" :class="{ disabled: isZoomOutDisabled }">
              <i class="fa fa-search-minus"></i> Thu nhỏ
            </b-link>
          </li>
          <li>
            <b-link href="javascript:void(0)" @click="onReset">
              <span class="icon one-refresh"></span>Đặt lại
            </b-link>
          </li>
          <li>
            <b-link href="javascript:void(0)" @click="onClose">
              <span class="icon one-times"></span>Đóng
            </b-link>
          </li>
        </ul>
      </div>

      <!-- Diagram Workspace -->
      <div class="diagram-workspace">
        <!-- Symbol Palette -->
        <div class="palette-container">
          <div v-show="!isDataLoaded" style="padding: 20px; text-align: center; color: #999">
            Đang tải dữ liệu...
          </div>
          <div v-show="isDataLoaded" style="width: 100%; height: 100%">
            <ejs-symbolpalette id="symbolpalette_quat_cot" ref="symbolpalette" :symbolHeight="100" :symbolWidth="80"
              :getNodeDefaults="getNodeDefaults" :getSymbolInfo="getSymbolInfo"
              :palettes="palettes"></ejs-symbolpalette>
          </div>
        </div>

        <!-- Diagram Canvas -->
        <div class="diagram-container">
          <ejs-diagram id="diagram_quat_cot" ref="diagramObj" :width="'100%'" :height="'100%'" :tool="7"
            :snapSettings="snapSettings" :scrollSettings="scrollSettings" :pageSettings="pageSettings"
            :selectedItems="selectedItems" :contextMenuSettings="contextMenuSettings" :commandManager="commandManager"
            :getNodeDefaults="getDiagramNodeDefaults" :drop="onDrop" :positionChange="onPositionChange"
            :contextMenuOpen="onContextMenuOpen" :contextMenuClick="onContextMenuClick"
            :scrollChange="onScrollChange"></ejs-diagram>
        </div>
      </div>
    </div>

    <!-- Draw Column Polygon Modal -->
    <draw-column-polygon-modal ref="drawColumnPolygonModal" :roomWidth="getPolygonBounds().width"
      :roomHeight="getPolygonBounds().height" @confirm="onColumnPolygonConfirm" @cancel="onColumnPolygonCancel" />

    <!-- CRUD Quạt Modal -->
    <modal-c-r-u-d-quat ref="modalCRUDQuat" :roomId="roomId" @refresh="onRefreshData" />

    <!-- CRUD Cột Modal -->
    <modal-c-r-u-d-cot ref="modalCRUDCot" :roomId="roomId" @refresh="onRefreshData" />
  </b-modal>
</template>

<script>
import Vue from 'vue'
import {
  DiagramPlugin,
  SymbolPalettePlugin,
  PrintAndExport,
  NodeConstraints,
  SelectorConstraints,
  SnapConstraints,
  UndoRedo,
  Snapping,
  DiagramContextMenu
} from '@syncfusion/ej2-vue-diagrams'

Vue.use(DiagramPlugin)
Vue.use(SymbolPalettePlugin)

import DrawColumnPolygonModal from './DrawColumnPolygonModal.vue'
import ModalCRUDQuat from './ModalCRUDQuat.vue'
import ModalCRUDCot from './ModalCRUDCot.vue'

export default {
  name: 'ModalQuanLyQuatCot',
  components: {
    DrawColumnPolygonModal,
    ModalCRUDQuat,
    ModalCRUDCot
  },

  props: {
    roomId: {
      type: Number,
      default: 0
    },
    roomPolygon: {
      type: Array,
      default: () => [[0, 0], [800, 0], [800, 600], [0, 600]]
    }
  },

  provide: {
    diagram: [PrintAndExport, UndoRedo, Snapping, DiagramContextMenu]
  },

  data() {
    return {
      currentZoom: 1,
      modalComponentKey: 0,
      _pendingColumnData: null,
      _editingColumnNode: null,

      // Dữ liệu quạt
      uninstalledFans: [],
      installedFans: [],

      // Dữ liệu cột
      uninstalledColumns: [],
      installedColumns: [],

      // Map lưu các quạt chồng lên nhau theo vị trí
      stackedFansMap: {},

      // State cho drag detection
      isDraggingFan: false,
      draggingFanId: null,
      dragTargetPosition: null,
      dragOldPosition: null,
      positionChangeTimeout: null,

      isDataLoaded: false,

      // Snap settings
      snapSettings: {
        constraints: SnapConstraints.ShowLines | SnapConstraints.SnapToLines,
        snapAngle: 5,
        snapObjectDistance: 5,
        horizontalGridlines: {
          lineColor: '#e0e0e0',
          lineDashArray: '2,2',
          lineIntervals: [1, 59, 0.25, 59.75, 0.25, 59.75, 0.25, 59.75, 0.25, 59.75],
          snapIntervals: [10]
        },
        verticalGridlines: {
          lineColor: '#e0e0e0',
          lineDashArray: '2,2',
          lineIntervals: [1, 59, 0.25, 59.75, 0.25, 59.75, 0.25, 59.75, 0.25, 59.75],
          snapIntervals: [10]
        }
      },

      // Scroll settings
      scrollSettings: {
        scrollLimit: 'Diagram',
        minZoom: 0.5,
        maxZoom: 2,
        padding: { left: 50, right: 50, top: 50, bottom: 50 },
        canAutoScroll: false
      },

      // Context menu
      contextMenuSettings: {
        show: true,
        items: [
          {
            text: 'Xem chi tiết',
            id: 'viewDetail',
            target: '.e-elementcontent',
            iconCss: 'icon fa fa-eye'
          },
          {
            text: 'Chỉnh sửa cột',
            id: 'editColumn',
            target: '.e-elementcontent',
            iconCss: 'icon fa fa-edit'
          },
          {
            separator: true
          },
          {
            text: 'Xóa',
            id: 'delete',
            target: '.e-elementcontent',
            iconCss: 'icon one-trash'
          }
        ],
        showCustomMenuOnly: true
      },

      commandManager: {
        commands: [
          {
            name: 'delete',
            canExecute: () => {
              return false
            }
          }
        ]
      },

      // Selected items constraints
      selectedItems: {
        constraints: SelectorConstraints.All & ~SelectorConstraints.ResizeAll & ~SelectorConstraints.Rotate
      },

      // Room container node ID
      roomContainerId: 'room_container_quat_cot'
    }
  },

  computed: {
    // Palette symbols
    palettes() {
      if (!this.isDataLoaded) {
        return [
          {
            id: 'columns',
            expanded: true,
            title: 'Cột',
            symbols: []
          },
          {
            id: 'fans',
            expanded: true,
            title: 'Quạt chưa lắp đặt',
            symbols: []
          }
        ]
      }

      const columnSymbols = this.uninstalledColumns.map((column) => {
        return this.createColumnPaletteNode(column)
      })

      const fanSymbols = this.uninstalledFans.map((fan) => {
        return this.createFanPaletteNode(fan)
      })

      return [
        {
          id: 'columns',
          expanded: true,
          title: `Cột chưa bố trí (${columnSymbols.length})`,
          symbols: columnSymbols
        },
        {
          id: 'fans',
          expanded: true,
          title: `Quạt chưa lắp đặt (${fanSymbols.length})`,
          symbols: fanSymbols
        }
      ]
    },

    // Page settings dựa trên polygon
    pageSettings() {
      const bounds = this.getPolygonBounds()
      return {
        orientation: 'Landscape',
        width: bounds.width + 100,
        height: bounds.height + 100,
        multiplePage: false,
        showPageBreaks: false,
        background: {
          color: 'white'
        },
        boundaryConstraints: 'Page'
      }
    },

    isZoomInDisabled() {
      return this.currentZoom >= 1.7
    },

    isZoomOutDisabled() {
      return this.currentZoom <= 0.59
    }
  },

  watch: {
    roomPolygon: {
      handler(newVal, oldVal) {
        console.log('Room polygon changed:', { newVal, oldVal })
        if (this.isDataLoaded && this.$refs.diagramObj) {
          this.$nextTick(() => {
            this.refreshDiagram()
          })
        }
      },
      deep: true,
      immediate: false
    },

    roomId: {
      handler(newVal, oldVal) {
        console.log('Room ID changed:', { newVal, oldVal })
        if (newVal && newVal !== oldVal) {
          this.loadData()
        }
      },
      immediate: false
    }
  },

  async mounted() {
    try {
      console.log('ModalQuanLyQuatCot mounted')
      document.addEventListener('keydown', this.preventDeleteKey)
      await this.loadData()
      await this.$nextTick()
      await this.$nextTick()
      this.initializeDiagram()
      console.log('ModalQuanLyQuatCot initialization complete')
    } catch (error) {
      console.error('❌ Error during ModalQuanLyQuatCot mounted:', error)
    }
  },

  beforeDestroy() {
    document.removeEventListener('keydown', this.preventDeleteKey)
  },

  methods: {
    // ========== MODAL METHODS ==========
    async show() {
      console.log('ModalQuanLyQuatCot show() called')
      this.debugRoomPolygon()

      try {
        if (this.$refs.modalQuanLyQuatCot) {
          console.log('Modal ref found, showing...')
          this.$refs.modalQuanLyQuatCot.show()

          // ✅ Reload data từ API trước khi hiển thị diagram
          this.$nextTick(async () => {
            await this.loadData()
            this.refreshDiagram()
          })
        } else {
          console.error('Modal ref not found')
        }
      } catch (error) {
        console.error('Error showing modal:', error)
      }
    },

    refreshDiagram() {
      console.log('Refreshing diagram with current room polygon:', this.roomPolygon)
      if (this.$refs.diagramObj && this.$refs.diagramObj.ej2Instances) {
        this.initializeDiagram()
      }
    },

    // Debug method
    debugRoomPolygon() {
      console.log('=== ROOM POLYGON DEBUG ===')
      console.log('roomPolygon:', this.roomPolygon)
      console.log('roomId:', this.roomId)
      console.log('isDataLoaded:', this.isDataLoaded)
      console.log('polygon type:', typeof this.roomPolygon)
      console.log('polygon length:', this.roomPolygon ? this.roomPolygon.length : 'null')
      if (this.roomPolygon && this.roomPolygon.length > 0) {
        console.log('first point:', this.roomPolygon[0])
        console.log('last point:', this.roomPolygon[this.roomPolygon.length - 1])
      }
      const bounds = this.getPolygonBounds()
      console.log('calculated bounds:', bounds)
      console.log('========================')
    },

    hide() {
      this.$refs.modalQuanLyQuatCot.hide()
    },

    onClose() {
      this.hide()
    },

    handleModalClose() {
      this._pendingColumnData = null
      this._editingColumnNode = null
      this.$emit('close')
    },

    // ========== PREVENT DELETE KEY ==========
    preventDeleteKey(event) {
      if (event.key === 'Delete' || event.keyCode === 46) {
        event.preventDefault()
        event.stopPropagation()
        return false
      }
    },

    // ========== POLYGON UTILITIES ==========
    getPolygonBounds() {
      console.log('Getting polygon bounds for:', this.roomPolygon)

      if (!this.roomPolygon || this.roomPolygon.length === 0) {
        console.log('Using default bounds')
        return { minX: 0, minY: 0, maxX: 800, maxY: 600, width: 800, height: 600 }
      }

      try {
        const xs = this.roomPolygon.map((p) => p[0])
        const ys = this.roomPolygon.map((p) => p[1])

        const minX = Math.min(...xs)
        const maxX = Math.max(...xs)
        const minY = Math.min(...ys)
        const maxY = Math.max(...ys)

        const bounds = {
          minX,
          minY,
          maxX,
          maxY,
          width: maxX - minX,
          height: maxY - minY
        }

        console.log('Calculated bounds:', bounds)
        return bounds
      } catch (error) {
        console.error('Error calculating bounds:', error)
        return { minX: 0, minY: 0, maxX: 800, maxY: 600, width: 800, height: 600 }
      }
    },

    // Kiểm tra điểm có nằm trong polygon không
    isPointInPolygon(x, y) {
      const polygon = this.roomPolygon
      if (!polygon || polygon.length === 0) {
        return false
      }
      let inside = false

      for (let i = 0, j = polygon.length - 1; i < polygon.length; j = i++) {
        const xi = polygon[i][0]
        const yi = polygon[i][1]
        const xj = polygon[j][0]
        const yj = polygon[j][1]

        const intersect = yi > y !== yj > y && x < ((xj - xi) * (y - yi)) / (yj - yi) + xi

        if (intersect) inside = !inside
      }

      return inside
    },

    // ========== DATA LOADING ==========
    async loadData() {
      try {
        console.log('Loading data with roomPolygon:', this.roomPolygon)
        console.log('Room ID:', this.roomId)

        const [fans, columns, installedFans, installedColumns] = await Promise.all([
          await this.getUninstalledFans(),
          await this.getUninstalledColumns(),
          await this.getInstalledFans(),
          await this.getInstalledColumns()
        ])

        this.uninstalledFans = fans
        this.uninstalledColumns = columns
        this.installedFans = installedFans
        this.installedColumns = installedColumns

        this.isDataLoaded = true
        console.log('Data loaded successfully')
      } catch (error) {
        console.error('❌ Error loading data:', error)
        this.isDataLoaded = true
      }
    },

    async getUninstalledFans() {
      try {
        let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khonggian/get-ds-doituong-chualapdat', {
          loaiDoiTuong: 'QUAT',
          doiTuongChaId: this.roomId
        })
        if (rs.error_code === 'BSS-00000000' && rs.data.result) {
          return rs.data.result.map(item => ({
            id: item.ID || item.id,
            name: item.TEN || '',
            tenTat: item.TENTAT || '',
            width: item.CHIEUDAI || 60,
            height: item.CHIEURONG || 60,
            ghiChu: item.GHICHU || '',
            moTa: item.MOTA || ''
          }))
        } else {
          console.error('❌ API error fetching uninstalled fans:', rs)
          return []
        }
      } catch (error) {
        console.error('❌ Error fetching uninstalled fans:', error)
        return []
      }
    },

    async getUninstalledColumns() {
      try {
        let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khonggian/get-ds-doituong-chualapdat', {
          loaiDoiTuong: 'COT',
          doiTuongChaId: this.roomId
        })
        if (rs.error_code === 'BSS-00000000' && rs.data.result) {
          return rs.data.result.map(item => {
            // Parse KHONGGIAN if it's a string
            let polygon = item.POLYGON || []
            let khongGian = item.KHONGGIAN
            if (typeof khongGian === 'string') {
              try {
                khongGian = JSON.parse(khongGian)
              } catch (e) {
                khongGian = null
              }
            }

            return {
              id: item.ID || item.id,
              name: item.TEN || '',
              tenTat: item.TENTAT || '',
              ghiChu: item.GHICHU || '',
              moTa: item.MOTA || '',
              polygon: polygon,
              khongGian: khongGian // Lưu KHONGGIAN để xác định đã vẽ hay chưa
            }
          })
        } else {
          console.error('❌ API error fetching uninstalled columns:', rs)
          return []
        }
      } catch (error) {
        console.error('❌ Error fetching uninstalled columns:', error)
        return []
      }
    },

    async getInstalledFans() {
      try {
        let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khonggian/get-ds-doituong-dalapdat', {
          loaiDoiTuong: 'QUAT',
          doiTuongChaId: this.roomId
        })
        if (rs.error_code === 'BSS-00000000' && rs.data.result) {
          return rs.data.result.map(item => ({
            id: item.ID || item.id,
            name: item.TEN || '',
            tenTat: item.TENTAT || '',
            width: item.CHIEUDAI || 60,
            height: item.CHIEURONG || 60,
            x: item.TOADO_X || 0,
            y: item.TOADO_Y || 0,
            ghiChu: item.GHICHU || '',
            moTa: item.MOTA || '',
            columnId: item.COT_ID || null
          }))
        } else {
          console.error('❌ API error fetching installed fans:', rs)
          return []
        }
      } catch (error) {
        console.error('❌ Error fetching installed fans:', error)
        return []
      }
    },

    async getInstalledColumns() {
      try {
        let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khonggian/get-ds-doituong-dalapdat', {
          loaiDoiTuong: 'COT',
          doiTuongChaId: this.roomId
        })
        if (rs.error_code === 'BSS-00000000' && rs.data.result) {
          return rs.data.result.map(item => {
            // Parse KHONGGIAN if it's a string
            let khongGian = item.KHONGGIAN
            if (typeof khongGian === 'string') {
              try {
                khongGian = JSON.parse(khongGian)
              } catch (e) {
                console.error('❌ Error parsing KHONGGIAN for column:', item.TEN, e)
                khongGian = null
              }
            }

            // ✅ Sử dụng KHONGGIAN làm polygon (ưu tiên), fallback POLYGON
            let polygon = khongGian || item.POLYGON || []

            return {
              id: item.ID || item.id,
              name: item.TEN || '',
              tenTat: item.TENTAT || '',
              x: item.TOADO_X || 0,
              y: item.TOADO_Y || 0,
              ghiChu: item.GHICHU || '',
              moTa: item.MOTA || '',
              polygon: polygon,
              khongGian: khongGian // Lưu KHONGGIAN để xác định đã vẽ hay chưa
            }
          })
        } else {
          console.error('❌ API error fetching installed columns:', rs)
          return []
        }
      } catch (error) {
        console.error('❌ Error fetching installed columns:', error)
        return []
      }
    },


    // ========== DIAGRAM INITIALIZATION ==========
    initializeDiagram() {
      if (!this.$refs.diagramObj || !this.$refs.diagramObj.ej2Instances) {
        console.error('❌ Diagram not ready')
        return
      }

      const diagram = this.$refs.diagramObj.ej2Instances
      console.log('Initializing diagram with room polygon:', this.roomPolygon)

      // Clear existing nodes first
      diagram.clear()

      // 1. Tạo room boundary
      this.createRoomContainer(diagram)

      // 2. Load các items đã lắp đặt
      this.loadInstalledItems(diagram)

      // 3. Fit diagram to page
      this.$nextTick(() => {
        diagram.fitToPage()
      })
    },

    // Tạo room container với polygon boundary
    createRoomContainer(diagram) {
      const bounds = this.getPolygonBounds()
      const centerX = bounds.minX + bounds.width / 2
      const centerY = bounds.minY + bounds.height / 2

      // Tạo pathData từ room polygon
      let pathData = 'M 0 0 L 800 0 L 800 600 L 0 600 Z' // Default rectangle

      if (this.roomPolygon && this.roomPolygon.length >= 3) {
        pathData = this.roomPolygon
          .map((p, i) => {
            const relX = p[0] - centerX
            const relY = p[1] - centerY
            return i === 0 ? `M ${relX} ${relY}` : `L ${relX} ${relY}`
          })
          .join(' ') + ' Z'
      }

      console.log('Creating room boundary with pathData:', pathData)
      console.log('Room polygon:', this.roomPolygon)
      console.log('Bounds:', bounds)

      // Node hiển thị boundary
      const boundaryNode = {
        id: this.roomContainerId + '_boundary',
        offsetX: centerX,
        offsetY: centerY,
        width: bounds.width,
        height: bounds.height,
        shape: {
          type: 'Path',
          data: pathData
        },
        style: {
          fill: 'rgba(33, 150, 243, 0.05)',
          strokeColor: '#2196F3',
          strokeWidth: 3,
          strokeDashArray: ''
        },
        constraints: NodeConstraints.None,
        zIndex: 0,
        addInfo: {
          isBoundary: true
        }
      }

      // ✅ THÊM: Tạo mask để ẩn grid ngoài polygon phòng
      if (this.roomPolygon && this.roomPolygon.length >= 3) {
        const padding = 5000

        // Outer rectangle (clockwise) - DÙNG TỌA ĐỘ TƯƠNG ĐỐI
        const outerRect = `M ${-bounds.width / 2 - padding} ${-bounds.height / 2 - padding}
                           L ${bounds.width / 2 + padding} ${-bounds.height / 2 - padding}
                           L ${bounds.width / 2 + padding} ${bounds.height / 2 + padding}
                           L ${-bounds.width / 2 - padding} ${bounds.height / 2 + padding} Z`

        // Inner polygon (counter-clockwise để tạo hole) - DÙNG TỌA ĐỘ TƯƠNG ĐỐI
        const innerPolygon =
          [...this.roomPolygon]
            .reverse()
            .map((p, i) => {
              const relX = p[0] - centerX
              const relY = p[1] - centerY
              return i === 0 ? `M ${relX} ${relY}` : `L ${relX} ${relY}`
            })
            .join(' ') + ' Z'

        // Combine outer và inner để tạo mask với hole
        const maskPath = outerRect + ' ' + innerPolygon

        const maskNode = {
          id: this.roomContainerId + '_mask',
          offsetX: centerX,
          offsetY: centerY,
          width: bounds.width + padding * 2,
          height: bounds.height + padding * 2,
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
          zIndex: -1, // Ở dưới cùng
          addInfo: {
            isMask: true
          }
        }

        // Add mask trước (dưới cùng)
        diagram.add(maskNode)
      }

      // Add boundary sau (trên mask)
      diagram.add(boundaryNode)

      // Vẽ grid labels
      this.drawGridLabels(diagram)

      // Vẽ dimension labels
      this.drawDimensionLabels(diagram)
    },

    // Vẽ grid tọa độ 60cm với labels AA, AB, AC... và 01, 02, 03...
    drawGridLabels(diagram) {
      const bounds = this.getPolygonBounds()
      const cellSize = 60 // 60cm per cell

      // Tính số hàng và cột (dùng Math.ceil để bao gồm cả ô không đủ 60x60)
      const numRows = Math.ceil(bounds.height / cellSize)
      const numCols = Math.ceil(bounds.width / cellSize)

      // Hàm tạo column label - CHỮ CÁI (AA, AB, AC... skip I) - TRỤC NGANG
      const getColLabel = (index) => {
        const chars = 'ABCDEFGHJKLMNOPQRSTUVWXYZ' // Skip 'I'
        if (index < chars.length) {
          return 'A' + chars[index]
        }
        const first = Math.floor(index / chars.length)
        const second = index % chars.length
        return chars[first] + chars[second]
      }

      // Hàm tạo row label - SỐ (01, 02, 03...) - TRỤC DỌC
      const getRowLabel = (index) => {
        return String(index + 1).padStart(2, '0')
      }

      // Vẽ grid cells
      for (let row = 0; row < numRows; row++) {
        for (let col = 0; col < numCols; col++) {
          const x = bounds.minX + col * cellSize
          const y = bounds.minY + row * cellSize

          // Tính kích thước thực tế của cell (ô cuối có thể nhỏ hơn 60x60)
          const actualWidth = Math.min(cellSize, bounds.maxX - x)
          const actualHeight = Math.min(cellSize, bounds.maxY - y)

          if (actualWidth <= 0 || actualHeight <= 0) continue

          // ✅ Kiểm tra ô có nằm trong polygon không (dùng center hoặc góc trên trái)
          const centerX = x + actualWidth / 2
          const centerY = y + actualHeight / 2
          const topLeftInside = this.isPointInPolygon(x + 1, y + 1)
          const centerInside = this.isPointInPolygon(centerX, centerY)

          // CHỈ hiển thị nếu center hoặc top-left nằm trong polygon
          if (!topLeftInside && !centerInside) continue

          // Cell label: CHỮ + SỐ (AA01, AB01, AC02...)
          const cellLabel = getColLabel(col) + getRowLabel(row)

          // Tạo grid cell rectangle với pivot top-left
          diagram.add({
            id: `grid_cell_quat_cot_${row}_${col}`,
            offsetX: x,
            offsetY: y,
            width: actualWidth,
            height: actualHeight,
            pivot: { x: 0, y: 0 },
            shape: { type: 'Basic', shape: 'Rectangle' },
            style: {
              fill: 'transparent',
              strokeColor: '#E0E0E0',
              strokeWidth: 0.5,
              strokeDashArray: '2,2'
            },
            constraints: NodeConstraints.None,
            zIndex: 1,
            addInfo: {
              isGridCell: true,
              cellLabel: cellLabel
            }
          })

          // Tạo label text cho mỗi ô (ở góc trên trái) - chỉ hiển thị nếu ô đủ rộng
          if (actualWidth >= 30 && actualHeight >= 15) {
            diagram.add({
              id: `grid_label_quat_cot_${row}_${col}`,
              offsetX: x + 3,
              offsetY: y + 2,
              width: Math.min(40, actualWidth - 6),
              height: 15,
              pivot: { x: 0, y: 0 },
              shape: {
                type: 'Text',
                content: cellLabel
              },
              style: {
                fontSize: 9,
                fill: 'transparent',
                color: '#BDBDBD',
                bold: false,
                textAlign: 'Left'
              },
              constraints: NodeConstraints.None,
              zIndex: 2,
              addInfo: {
                isGridLabel: true
              }
            })
          }
        }
      }

      // Vẽ row headers - SỐ (01, 02, 03...) - BÊN TRÁI (trục dọc)
      for (let row = 0; row < numRows; row++) {
        const y = bounds.minY + row * cellSize
        const actualHeight = Math.min(cellSize, bounds.maxY - y)
        if (actualHeight <= 0) continue

        diagram.add({
          id: `grid_row_header_quat_cot_${row}`,
          offsetX: bounds.minX - 20,
          offsetY: y + actualHeight / 2,
          width: 35,
          height: 25,
          shape: {
            type: 'Text',
            content: getRowLabel(row)
          },
          style: {
            fontSize: 11,
            fill: 'rgba(255, 255, 255, 0.95)',
            color: '#757575',
            bold: true,
            textAlign: 'Center'
          },
          constraints: NodeConstraints.None,
          zIndex: 1000,
          addInfo: {
            isGridHeader: true
          }
        })
      }

      // Vẽ column headers - CHỮ CÁI (AA, AB, AC...) - PHÍA TRÊN (trục ngang)
      for (let col = 0; col < numCols; col++) {
        const x = bounds.minX + col * cellSize
        const actualWidth = Math.min(cellSize, bounds.maxX - x)
        if (actualWidth <= 0) continue

        diagram.add({
          id: `grid_col_header_quat_cot_${col}`,
          offsetX: x + actualWidth / 2,
          offsetY: bounds.minY - 20,
          width: 35,
          height: 25,
          shape: {
            type: 'Text',
            content: getColLabel(col)
          },
          style: {
            fontSize: 11,
            fill: 'rgba(255, 255, 255, 0.95)',
            color: '#757575',
            bold: true,
            textAlign: 'Center'
          },
          constraints: NodeConstraints.None,
          zIndex: 1000,
          addInfo: {
            isGridHeader: true
          }
        })
      }
    },

    // Vẽ labels hiển thị độ dài các cạnh
    drawDimensionLabels(diagram) {
      if (!this.roomPolygon || this.roomPolygon.length === 0) {
        return
      }
      const offset = 20

      for (let i = 0; i < this.roomPolygon.length; i++) {
        const p1 = this.roomPolygon[i]
        const p2 = this.roomPolygon[(i + 1) % this.roomPolygon.length]

        const dx = p2[0] - p1[0]
        const dy = p2[1] - p1[1]
        const length = Math.sqrt(dx * dx + dy * dy)

        const midX = (p1[0] + p2[0]) / 2
        const midY = (p1[1] + p2[1]) / 2

        const len = Math.sqrt(dx * dx + dy * dy)
        const nx = -dy / len
        const ny = dx / len

        const labelX = midX + nx * offset
        const labelY = midY + ny * offset

        let angle = (Math.atan2(dy, dx) * 180) / Math.PI
        if (angle > 90) angle -= 180
        if (angle < -90) angle += 180

        diagram.add({
          id: `edgeLabel_quat_cot_${i}`,
          offsetX: labelX,
          offsetY: labelY,
          width: 80,
          height: 20,
          rotateAngle: angle,
          shape: {
            type: 'Text',
            content: `${length.toFixed(0)} cm`
          },
          style: {
            fontSize: 11,
            fill: 'rgba(255, 255, 255, 0.9)',
            color: '#1976D2',
            bold: true,
            textAlign: 'Center'
          },
          constraints: NodeConstraints.None,
          zIndex: 1000
        })
      }
    },

    // Load các items đã lắp đặt
    loadInstalledItems(diagram) {
      console.log('📊 Loading installed items...')
      console.log('Installed columns:', this.installedColumns)
      console.log('Installed fans:', this.installedFans)

      // Load columns trước (z-index thấp hơn)
      this.installedColumns.forEach((column) => {
        if (!column.polygon || !Array.isArray(column.polygon) || column.polygon.length < 3) {
          console.warn('⚠️ Skipping column with invalid polygon:', column.name, 'polygon:', column.polygon)
          return
        }

        console.log('✅ Rendering column:', column.name, 'at', column.x, column.y, 'polygon:', column.polygon)

        const columnNode = this.createColumnNode(column)
        columnNode.offsetX = column.x
        columnNode.offsetY = column.y
        columnNode.pivot = { x: 0, y: 0 }

        diagram.add(columnNode)
      })

      // Load fans sau (z-index cao hơn)
      this.installedFans.forEach((fan) => {
        const fanNode = this.createFanNode(fan)
        fanNode.offsetX = fan.x
        fanNode.offsetY = fan.y
        fanNode.pivot = { x: 0, y: 0 }

        // Nếu fan thuộc về column
        if (fan.columnId) {
          const columnNode = diagram.getObject(fan.columnId)
          if (columnNode && columnNode.addInfo && columnNode.addInfo.isColumn) {
            // Tính tọa độ tương đối trong column
            const columnPivot = columnNode.pivot || { x: 0, y: 0 }
            const columnTopLeftX = columnNode.offsetX - columnNode.width * columnPivot.x
            const columnTopLeftY = columnNode.offsetY - columnNode.height * columnPivot.y

            const relativeX = fan.x - columnTopLeftX
            const relativeY = fan.y - columnTopLeftY

            if (!columnNode.addInfo.children) {
              columnNode.addInfo.children = []
            }
            columnNode.addInfo.children.push({
              id: fanNode.id,
              relativeX: relativeX,
              relativeY: relativeY
            })

            fanNode.addInfo.parentColumnId = fan.columnId
          }
        }

        diagram.add(fanNode)
      })
    },

    // ========== NODE CREATION ==========

    // Tạo fan node cho palette
    createFanPaletteNode(fan) {
      return {
        id: `palette_fan_${fan.id}`,
        width: 60,
        height: 75,
        shape: {
          type: 'HTML',
          content: this.createFanPaletteHtml(fan)
        },
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate,
        addInfo: {
          nodeType: 'fan',
          fanId: fan.id,
          fanName: fan.name,
          fanTenTat: fan.tenTat,
          width: fan.width || 60,
          height: fan.height || 60,
          ghiChu: fan.ghiChu,
          moTa: fan.moTa
        }
      }
    },

    // Tạo fan node cho diagram
    createFanNode(fan) {
      return {
        id: `fan_${fan.id}`,
        width: fan.width || 60,
        height: fan.height || 60,
        pivot: { x: 0, y: 0 }, // ✅ Top-left origin
        shape: {
          type: 'HTML',
          content: this.createFanDiagramHtml(fan)
        },
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate,
        zIndex: 10,
        addInfo: {
          nodeType: 'fan',
          isFan: true,
          fanId: fan.id,
          fanName: fan.name,
          fanTenTat: fan.tenTat,
          ghiChu: fan.ghiChu,
          moTa: fan.moTa
        }
      }
    },

    // Tạo column node cho palette
    createColumnPaletteNode(column) {
      return {
        id: `palette_column_${column.id}`,
        width: 60,
        height: 90,
        shape: {
          type: 'HTML',
          content: this.createColumnPaletteHtml(column)
        },
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate,
        addInfo: {
          nodeType: 'column',
          columnId: column.id,
          columnName: column.name,
          columnTenTat: column.tenTat,
          ghiChu: column.ghiChu,
          moTa: column.moTa,
          polygon: column.polygon
        }
      }
    },

    // Tạo column node cho diagram
    createColumnNode(column) {
      const xs = column.polygon.map((p) => p[0])
      const ys = column.polygon.map((p) => p[1])
      const minX = Math.min(...xs)
      const maxX = Math.max(...xs)
      const minY = Math.min(...ys)
      const maxY = Math.max(...ys)
      const width = maxX - minX
      const height = maxY - minY

      const pathData =
        column.polygon
          .map((p, i) => {
            const relX = p[0] - minX
            const relY = p[1] - minY
            return i === 0 ? `M ${relX} ${relY}` : `L ${relX} ${relY}`
          })
          .join(' ') + ' Z'

      // Tạo pattern đan chéo cho tấm sàn thông hơi
      const diagonalLines = []
      const spacing = 20 // Khoảng cách giữa các đường kẻ

      // Đường chéo từ trái sang phải (↘)
      for (let i = -height; i < width + height; i += spacing) {
        diagonalLines.push(`M ${i} 0 L ${i + height} ${height}`)
      }

      // Đường chéo từ phải sang trái (↙)
      for (let i = 0; i < width + height; i += spacing) {
        diagonalLines.push(`M ${i} 0 L ${i - height} ${height}`)
      }

      const patternPath = diagonalLines.join(' ')

      return {
        id: `column_${column.id}`,
        width: width,
        height: height,
        pivot: { x: 0, y: 0 },
        shape: {
          type: 'HTML',
          content: `
            <div style="position: relative; width: 100%; height: 100%;">
              <svg width="100%" height="100%" style="position: absolute; top: 0; left: 0;">
                <defs>
                  <clipPath id="clip_column_${column.id}">
                    <path d="${pathData}" />
                  </clipPath>
                </defs>

                <!-- Nền tấm sàn -->
                <path d="${pathData}"
                  fill="rgba(189, 189, 189, 0.1)"
                  stroke="#757575"
                  stroke-width="2"
                  stroke-dasharray="8,4"
                  opacity="1" />

                <!-- Pattern đan chéo -->
                <g clip-path="url(#clip_column_${column.id})">
                  <path d="${patternPath}"
                    fill="none"
                    stroke="#757575"
                    stroke-width="1"
                    stroke-dasharray="4,4"
                    opacity="0.3" />
                </g>
              </svg>
            </div>
          `
        },
        annotations: [
          {
            content: `🌬️ ${column.name}`,
            style: {
              fontSize: 11,
              bold: true,
              color: '#424242',
              fill: 'rgba(255, 255, 255, 0.85)',
              strokeColor: '#757575',
              strokeWidth: 0.5
            },
            offset: { x: 0.5, y: 0.5 },
            horizontalAlignment: 'Center',
            verticalAlignment: 'Center',
            margin: { left: 0, top: 0 }
          }
        ],
        constraints:
          NodeConstraints.Default &
          ~NodeConstraints.Resize &
          ~NodeConstraints.Rotate,
        zIndex: 5,
        addInfo: {
          nodeType: 'column',
          isColumn: true,
          isContainer: true,
          columnId: column.id,
          columnName: column.name,
          columnTenTat: column.tenTat,
          ghiChu: column.ghiChu,
          moTa: column.moTa,
          polygon: column.polygon,
          children: []
        }
      }
    },

    // ========== HTML TEMPLATES ==========

    createFanPaletteHtml(fan) {
      const displayName = fan.tenTat || fan.name

      return `
        <div class="fan-palette-card" style="border-color: #2196F3;">
          <div class="fan-title">${displayName}</div>
          <div class="fan-icon" style="color: #2196F3;">❄️</div>
          <div class="fan-desc">Quạt làm mát</div>
        </div>
      `
    },

    createFanDiagramHtml(fan) {
      const displayName = fan.tenTat || fan.name
      const width = fan.width || 60
      const height = fan.height || 60

      // Tạo pattern chấm lỗ thông hơi
      const dots = []
      const dotSpacing = 8 // Khoảng cách giữa các chấm
      const dotRadius = 1.5

      for (let y = dotSpacing; y < height - dotSpacing; y += dotSpacing) {
        for (let x = dotSpacing; x < width - dotSpacing; x += dotSpacing) {
          dots.push(`<circle cx="${x}" cy="${y}" r="${dotRadius}" fill="#2196F3" opacity="0.3" />`)
        }
      }

      return `
        <div style="
          position: relative;
          width: 100%;
          height: 100%;
          border: 2px dashed #2196F3;
          border-radius: 6px;
          background: #2196F310;
          box-sizing: border-box;
          opacity: 1;
        ">
          <svg width="100%" height="100%" style="position: absolute; top: 0; left: 0;">
            ${dots.join('')}
          </svg>
          <div style="
            position: relative;
            width: 100%;
            height: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            padding: 4px;
          ">
            <div style="font-size: 18px; line-height: 1; color: #2196F3;">❄️</div>
            <div style="font-size: 8px; font-weight: 600; text-align: center; color: #666; line-height: 1.1; margin-top: 2px;">${displayName}</div>
          </div>
        </div>
      `
    },

    createColumnPaletteHtml(column) {
      // ✅ Kiểm tra KHONGGIAN thay vì polygon để xác định đã vẽ chưa
      const hasPolygon = column.khongGian && Array.isArray(column.khongGian) && column.khongGian.length >= 3
      const icon = hasPolygon ? '🏛️' : '✏️'
      const badge = hasPolygon ? '' : '<div class="column-badge">Vẽ</div>'
      const borderStyle = hasPolygon ? 'solid' : 'dashed'
      const displayName = column.tenTat || column.name
      const description = column.ghiChu || 'Cột'

      return `
        <div class="column-palette-card" style="border-style: ${borderStyle}; position: relative;">
          ${badge}
          <div class="column-title">${displayName}</div>
          <div class="column-icon">${icon}</div>
          <div class="column-desc">${description}</div>
        </div>
      `
    },

    // ========== PALETTE METHODS ==========

    getNodeDefaults(node) {
      return {
        width: 80,
        height: 100
      }
    },

    getSymbolInfo(symbol) {
      return {
        fit: true,
        description: {
          text: symbol.addInfo && symbol.addInfo.fanName
            ? symbol.addInfo.fanName
            : symbol.addInfo && symbol.addInfo.columnName
              ? symbol.addInfo.columnName
              : 'Item'
        }
      }
    },

    getDiagramNodeDefaults(node) {
      return node
    },

    // ========== DRAG & DROP ==========

    async onDrop(args) {
      const droppedNode = args.element
      const nodeType = droppedNode.addInfo?.nodeType

      if (nodeType === 'fan') {
        await this.handleFanDrop(args)
      } else if (nodeType === 'column') {
        await this.handleColumnDrop(args)
      }
    },

    async handleFanDrop(args) {
      const fan = this.uninstalledFans.find(f => f.id === args.element.addInfo.fanId)
      if (!fan) return

      const dropPosition = { x: args.position.x, y: args.position.y }
      const fanWidth = fan.width || 60
      const fanHeight = fan.height || 60

      // Kiểm tra vị trí có trong phòng không
      if (!this.isPointInPolygon(dropPosition.x, dropPosition.y) ||
        !this.isPointInPolygon(dropPosition.x + fanWidth, dropPosition.y) ||
        !this.isPointInPolygon(dropPosition.x, dropPosition.y + fanHeight) ||
        !this.isPointInPolygon(dropPosition.x + fanWidth, dropPosition.y + fanHeight)) {
        this.$toast.error('Không thể đặt quạt bên ngoài phòng!')
        args.cancel = true
        return
      }

      try {
        // // Gọi API để lưu vị trí quạt
        // let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khonggian/update-vitri-doituong-khonggian', {
        //   loaiDoiTuong: 'QUAT',
        //   doiTuongId: fan.id,
        //   toaDoX: dropPosition.x,
        //   toaDoY: dropPosition.y
        // })

        if (rs.error_code !== 'BSS-00000000') {
          this.$toast.error('Lưu vị trí quạt thất bại: ' + rs.error_message)
          args.cancel = true
          return
        }

        // ✅ XÓA palette node cũ và TẠO diagram node mới
        const diagram = this.$refs.diagramObj.ej2Instances
        const paletteNodeId = args.element.id

        // Xóa palette node
        diagram.remove(args.element)

        // Tạo diagram node mới
        const fanNode = this.createFanNode(fan)
        fanNode.offsetX = dropPosition.x
        fanNode.offsetY = dropPosition.y
        fanNode.pivot = { x: 0, y: 0 }

        // Thêm vào diagram
        diagram.add(fanNode)

        // ✅ Remove từ palette và thêm vào installed
        const index = this.uninstalledFans.findIndex(f => f.id === fan.id)
        if (index !== -1) {
          this.uninstalledFans.splice(index, 1)
        }

        // Thêm vào danh sách installed
        this.installedFans.push({
          ...fan,
          x: dropPosition.x,
          y: dropPosition.y
        })

      } catch (error) {
        console.error('Error saving fan position:', error)
        this.$toast.error('Có lỗi khi lưu vị trí quạt!')
        args.cancel = true
      }
    },

    async handleColumnDrop(args) {
      const column = this.uninstalledColumns.find(c => c.id === args.element.addInfo.columnId)
      if (!column) return

      // ✅ Kiểm tra KHONGGIAN để xác định đã vẽ polygon chưa (giống handleCageDrop)
      const hasPolygon = column.khongGian && Array.isArray(column.khongGian) && column.khongGian.length >= 3

      // Nếu chưa có polygon, mở modal vẽ
      if (!hasPolygon) {
        // Cancel drop event (giống cage)
        args.cancel = true

        this._pendingColumnData = {
          column: column,
          dropPosition: { x: args.position.x, y: args.position.y }
        }

        // Mở modal vẽ polygon
        this.$refs.drawColumnPolygonModal.show(column, null)

        return
      }

      // ✅ Cột đã có polygon -> Sử dụng khongGian làm polygon và tạo node (giống handleCageDrop)
      column.polygon = column.khongGian

      const columnNode = this.createColumnNode(column)

      // Copy properties từ columnNode sang args.element
      args.element.shape = columnNode.shape
      args.element.style = columnNode.style
      args.element.zIndex = columnNode.zIndex
      args.element.annotations = columnNode.annotations

      // Tính bounds của polygon
      const xs = column.polygon.map((p) => p[0])
      const ys = column.polygon.map((p) => p[1])
      const minX = Math.min(...xs)
      const maxX = Math.max(...xs)
      const minY = Math.min(...ys)
      const maxY = Math.max(...ys)
      const width = maxX - minX
      const height = maxY - minY

      args.element.width = width
      args.element.height = height

      // Set pivot ở góc trên bên trái
      args.element.pivot = { x: 0, y: 0 }

      // Mark là cột để constraint
      args.element.addInfo.isColumn = true
      args.element.addInfo.isContainer = true
      args.element.addInfo.columnName = column.name
      args.element.addInfo.columnTenTat = column.tenTat
      args.element.addInfo.ghiChu = column.ghiChu
      args.element.addInfo.moTa = column.moTa
      args.element.addInfo.polygon = column.polygon
      args.element.addInfo.children = []

      // Constraint vị trí trong polygon phòng
      this.constrainNodePosition(args.element)

      // Remove from palette
      const index = this.uninstalledColumns.findIndex((c) => c.id === column.id)
      if (index !== -1) {
        this.uninstalledColumns.splice(index, 1)
      }

    },

    // ========== POSITION CHANGE ==========

    onPositionChange(args) {
      if (!args.newValue || !args.oldValue) return

      // ✅ Lấy node từ args.source giống index.vue
      const node = args.source?.addInfo ? args.source : args.source?.properties?.nodes?.[0]
      if (!node) return

      const diagram = this.$refs.diagramObj.ej2Instances

      // Bỏ qua boundary node, mask, labels và grid nodes
      if (
        node.id === this.roomContainerId + '_boundary' ||
        node.id === this.roomContainerId + '_mask' ||
        (node.id && node.id.startsWith('edgeLabel_')) ||
        (node.id && node.id.startsWith('grid_')) ||
        node.addInfo?.isGridCell ||
        node.addInfo?.isGridLabel ||
        node.addInfo?.isGridHeader
      ) {
        return
      }

      // Xử lý khi di chuyển fan/column
      if (node.addInfo) {
        const nodeType = node.addInfo.nodeType

        if (nodeType === 'fan') {
          this.handleFanPositionChange(args, node)
        } else if (nodeType === 'column') {
          this.handleColumnPositionChange(args, node)
        }
      }
    },

    handleFanPositionChange(args, fan) {
      const bounds = this.getPolygonBounds()
      const diagram = this.$refs.diagramObj.ej2Instances

      const width = fan.width
      const height = fan.height

      // ✅ Thêm tolerance nhỏ để cho phép sát cạnh
      const tolerance = 0.1

      // Kiểm tra vị trí MỚI
      const newTopLeft = this.isPointInPolygon(args.newValue.offsetX + tolerance, args.newValue.offsetY + tolerance)
      const newTopRight = this.isPointInPolygon(args.newValue.offsetX + width - tolerance, args.newValue.offsetY + tolerance)
      const newBottomLeft = this.isPointInPolygon(args.newValue.offsetX + tolerance, args.newValue.offsetY + height - tolerance)
      const newBottomRight = this.isPointInPolygon(args.newValue.offsetX + width - tolerance, args.newValue.offsetY + height - tolerance)
      const newIsInside = newTopLeft && newTopRight && newBottomLeft && newBottomRight

      // Sửa logic bounds - không trừ width/height để cho phép kéo sát cạnh
      const newIsOutOfBounds =
        args.newValue.offsetX < bounds.minX ||
        args.newValue.offsetX + width > bounds.maxX ||
        args.newValue.offsetY < bounds.minY ||
        args.newValue.offsetY + height > bounds.maxY

      // Kiểm tra vị trí CŨ
      const oldTopLeft = this.isPointInPolygon(args.oldValue.offsetX + tolerance, args.oldValue.offsetY + tolerance)
      const oldTopRight = this.isPointInPolygon(args.oldValue.offsetX + width - tolerance, args.oldValue.offsetY + tolerance)
      const oldBottomLeft = this.isPointInPolygon(args.oldValue.offsetX + tolerance, args.oldValue.offsetY + height - tolerance)
      const oldBottomRight = this.isPointInPolygon(args.oldValue.offsetX + width - tolerance, args.oldValue.offsetY + height - tolerance)
      const oldIsInside = oldTopLeft && oldTopRight && oldBottomLeft && oldBottomRight

      const oldIsOutOfBounds =
        args.oldValue.offsetX < bounds.minX ||
        args.oldValue.offsetX + width > bounds.maxX ||
        args.oldValue.offsetY < bounds.minY ||
        args.oldValue.offsetY + height > bounds.maxY

      // CHỈ CHẶN NẾU: vị trí CŨ trong biên VÀ vị trí MỚI ngoài biên
      const wasInside = oldIsInside && !oldIsOutOfBounds
      const isNowOutside = !newIsInside || newIsOutOfBounds

      if (wasInside && isNowOutside) {
        // Đang cố kéo RA NGOÀI -> CHẶN
        args.cancel = true
        this.$nextTick(() => {
          const diagramNode = diagram.getObject(fan.id)
          if (diagramNode) {
            diagramNode.offsetX = args.oldValue.offsetX
            diagramNode.offsetY = args.oldValue.offsetY
            diagram.dataBind()
          }
        })
        return
      }

      // Debounce để tránh gọi API liên tục
      clearTimeout(this.positionChangeTimeout)
      this.positionChangeTimeout = setTimeout(() => {
        this.saveFanPosition(fan.addInfo.fanId, args.newValue.offsetX, args.newValue.offsetY)
      }, 500)
    },

    handleColumnPositionChange(args, column) {
      const bounds = this.getPolygonBounds()
      const diagram = this.$refs.diagramObj.ej2Instances

      const width = column.width
      const height = column.height

      // ✅ Thêm tolerance nhỏ để cho phép sát cạnh
      const tolerance = 0.1

      // Kiểm tra vị trí MỚI
      const newTopLeft = this.isPointInPolygon(args.newValue.offsetX + tolerance, args.newValue.offsetY + tolerance)
      const newTopRight = this.isPointInPolygon(args.newValue.offsetX + width - tolerance, args.newValue.offsetY + tolerance)
      const newBottomLeft = this.isPointInPolygon(args.newValue.offsetX + tolerance, args.newValue.offsetY + height - tolerance)
      const newBottomRight = this.isPointInPolygon(args.newValue.offsetX + width - tolerance, args.newValue.offsetY + height - tolerance)
      const newIsInside = newTopLeft && newTopRight && newBottomLeft && newBottomRight

      // Sửa logic bounds - không trừ width/height để cho phép kéo sát cạnh
      const newIsOutOfBounds =
        args.newValue.offsetX < bounds.minX ||
        args.newValue.offsetX + width > bounds.maxX ||
        args.newValue.offsetY < bounds.minY ||
        args.newValue.offsetY + height > bounds.maxY

      // Kiểm tra vị trí CŨ
      const oldTopLeft = this.isPointInPolygon(args.oldValue.offsetX + tolerance, args.oldValue.offsetY + tolerance)
      const oldTopRight = this.isPointInPolygon(args.oldValue.offsetX + width - tolerance, args.oldValue.offsetY + tolerance)
      const oldBottomLeft = this.isPointInPolygon(args.oldValue.offsetX + tolerance, args.oldValue.offsetY + height - tolerance)
      const oldBottomRight = this.isPointInPolygon(args.oldValue.offsetX + width - tolerance, args.oldValue.offsetY + height - tolerance)
      const oldIsInside = oldTopLeft && oldTopRight && oldBottomLeft && oldBottomRight

      const oldIsOutOfBounds =
        args.oldValue.offsetX < bounds.minX ||
        args.oldValue.offsetX + width > bounds.maxX ||
        args.oldValue.offsetY < bounds.minY ||
        args.oldValue.offsetY + height > bounds.maxY

      // CHỈ CHẶN NẾU: vị trí CŨ trong biên VÀ vị trí MỚI ngoài biên
      const wasInside = oldIsInside && !oldIsOutOfBounds
      const isNowOutside = !newIsInside || newIsOutOfBounds

      if (wasInside && isNowOutside) {
        // Đang cố kéo RA NGOÀI -> CHẶN
        args.cancel = true
        this.$nextTick(() => {
          const diagramNode = diagram.getObject(column.id)
          if (diagramNode) {
            diagramNode.offsetX = args.oldValue.offsetX
            diagramNode.offsetY = args.oldValue.offsetY
            diagram.dataBind()
          }
        })
        return
      }

      // Debounce để tránh gọi API liên tục
      clearTimeout(this.positionChangeTimeout)
      this.positionChangeTimeout = setTimeout(() => {
        this.saveColumnPosition(column.addInfo.columnId, args.newValue.offsetX, args.newValue.offsetY)
      }, 500)
    },

    async saveFanPosition(fanId, x, y) {
      try {
        let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khonggian/update-vitri-doituong-khonggian', {
          loaiDoiTuong: 'QUAT',
          doiTuongId: fanId,
          toaDoX: x,
          toaDoY: y
        })
        if (rs.error_code !== 'BSS-00000000') {
          console.error('Error updating fan position:', rs.error_message)
          this.$toast.error('Cập nhật vị trí quạt thất bại: ' + rs.error_message)
        }
      } catch (error) {
        console.error('Error updating fan position:', error)
        this.$toast.error('Có lỗi khi cập nhật vị trí quạt!')
      }
    },

    async saveColumnPosition(columnId, x, y) {
      try {
        let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khonggian/update-vitri-doituong-khonggian', {
          loaiDoiTuong: 'COT',
          doiTuongId: columnId,
          toaDoX: x,
          toaDoY: y
        })
        if (rs.error_code !== 'BSS-00000000') {
          console.error('Error updating column position:', rs.error_message)
          this.$toast.error('Cập nhật vị trí cột thất bại: ' + rs.error_message)
        }
      } catch (error) {
        console.error('Error updating column position:', error)
        this.$toast.error('Có lỗi khi cập nhật vị trí cột!')
      }
    },

    // ========== CONTEXT MENU ==========

    onContextMenuOpen(args) {
      console.log('Context menu opening, args:', args)
      const diagram = this.$refs.diagramObj.ej2Instances

      // ✅ Kiểm tra có node nào được chọn không
      if (!diagram.selectedItems.nodes || diagram.selectedItems.nodes.length === 0) {
        console.log('No node selected, canceling context menu')
        args.cancel = true
        return
      }

      const node = diagram.selectedItems.nodes[0]
      console.log('Selected node:', node.id, 'addInfo:', node.addInfo)

      // ✅ Không hiển thị context menu cho boundary, mask, dimension labels và grid nodes
      if (!node.addInfo ||
        node.id === this.roomContainerId + '_boundary' ||
        node.id === this.roomContainerId + '_mask' ||
        node.id?.startsWith('edgeLabel_quat_cot_') ||
        node.id?.startsWith('grid_') ||
        node.addInfo.isBoundary ||
        node.addInfo.isMask ||
        node.addInfo.isGridCell ||
        node.addInfo.isGridLabel ||
        node.addInfo.isGridHeader) {
        console.log('System node detected, canceling context menu')
        args.cancel = true
        return
      }

      const nodeType = node.addInfo.nodeType
      const isFan = nodeType === 'fan'
      const isColumn = nodeType === 'column'

      console.log('Node type:', nodeType, 'isFan:', isFan, 'isColumn:', isColumn)

      // Ẩn/hiện menu items dựa vào context
      if (args.items && Array.isArray(args.items)) {
        args.items.forEach(item => {
          if (item.id === 'viewDetail') {
            item.visible = isFan || isColumn
          } else if (item.id === 'editColumn') {
            item.visible = isColumn
          } else if (item.id === 'delete') {
            item.visible = isFan || isColumn
          }
        })
        console.log('Context menu items configured:', args.items)
      }
    },

    onContextMenuClick(args) {
      console.log('Context menu clicked:', args.item)
      const diagram = this.$refs.diagramObj.ej2Instances
      console.log('Selected nodes:', diagram.selectedItems.nodes)

      const node = diagram.selectedItems.nodes[0]
      if (!node) {
        console.log('No node selected')
        return
      }

      console.log('Processing action for node:', node.id, 'Action:', args.item.id)

      switch (args.item.id) {
        case 'viewDetail':
          this.viewNodeDetail(node)
          break
        case 'editColumn':
          this.editColumn(node)
          break
        case 'delete':
          this.deleteNode(node)
          break
      }
    },

    viewNodeDetail(node) {
      if (node.addInfo.nodeType === 'fan') {
        this.$toast.info(`Xem chi tiết quạt: ${node.addInfo.fanName}`)
      } else if (node.addInfo.nodeType === 'column') {
        this.$toast.info(`Xem chi tiết cột: ${node.addInfo.columnName}`)
      }
    },

    editColumn(node) {
      if (node.addInfo.nodeType === 'column') {
        this._editingColumnNode = node

        // Tính polygon tuyệt đối từ vị trí hiện tại
        const absolutePolygon = node.addInfo.polygon.map(p => [
          p[0] + node.offsetX,
          p[1] + node.offsetY
        ])

        const columnData = {
          id: node.addInfo.columnId,
          name: node.addInfo.columnName,
          tenTat: node.addInfo.columnTenTat,
          ghiChu: node.addInfo.ghiChu,
          moTa: node.addInfo.moTa
        }

        // ✅ FIX: Gọi show() với đúng format tham số (columnData, existingPolygon)
        this.$refs.drawColumnPolygonModal.show(columnData, absolutePolygon)
      }
    },

    async deleteNode(node) {
      try {
        const diagram = this.$refs.diagramObj.ej2Instances

        if (node.addInfo.nodeType === 'fan') {
          //Call API delete position fan
          console.log('Deleting fan position:', node.addInfo.fanId)
          let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khonggian/update-vitri-doituong-khonggian', {
            loaiDoiTuong: 'QUAT',
            doiTuongId: node.addInfo.fanId,
            toaDoX: null,
            toaDoY: null
          })
          if (rs.error_code !== 'BSS-00000000') {
            this.$toast.error('Xóa vị trí quạt thất bại: ' + rs.error_message)
            return
          }

          // ✅ Reload lại danh sách fans từ API để cập nhật đúng trạng thái
          const fans = await this.getUninstalledFans()
          this.uninstalledFans = fans

        } else if (node.addInfo.nodeType === 'column') {
          //Call API delete position column
          let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khonggian/update-vitri-doituong-khonggian', {
            loaiDoiTuong: 'COT',
            doiTuongId: node.addInfo.columnId,
            toaDoX: null,
            toaDoY: null
          })
          if (rs.error_code !== 'BSS-00000000') {
            this.$toast.error('Xóa vị trí cột thất bại: ' + rs.error_message)
            return
          }

          // ✅ Reload lại danh sách columns từ API để cập nhật đúng trạng thái (khongGian - đã vẽ hay chưa)
          const columns = await this.getUninstalledColumns()
          this.uninstalledColumns = columns
        }

        // ✅ Xóa khỏi diagram
        diagram.remove(node)

        // ✅ Reload lại installed items để cập nhật danh sách
        const installedFans = await this.getInstalledFans()
        const installedColumns = await this.getInstalledColumns()
        this.installedFans = installedFans
        this.installedColumns = installedColumns

        // ✅ Refresh palette để hiển thị đúng icon
        // this.updatePalettes()

        this.$toast.success('Đã xóa thành công')

        // ✅ Emit event để sơ đồ chính reload lại quạt và cột
        this.$emit('delete', {
          type: node.addInfo.nodeType,
          id: node.addInfo.fanId || node.addInfo.columnId
        })
      } catch (error) {
        console.error('Error deleting node:', error)
      }
    },

    // ========== COLUMN POLYGON HANDLERS ==========

    async onColumnPolygonConfirm(result) {
      const isEditMode = !!this._editingColumnNode

      if (isEditMode) {
        try {
          // result.columnData.id là số nguyên từ DB, không có prefix
          let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khonggian/update-toado-doituong', {
            doiTuongID: result.columnData.id,
            loaiDoiTuong: 'COT',
            dsToaDo: result.polygon.map((p) => ({ toaDoX: p[0], toaDoY: p[1] }))
          })
          if (rs.error_code == 'BSS-00000000') {
            this.handleEditColumnConfirm(result)
          } else {
            this.$toast.error('Cập nhật tọa độ đối tượng thất bại: ' + rs.error_message)
          }
        } catch (error) {
          console.error('Error updating column polygon:', error)
        }
      } else {
        console.log(result, 'column polygon result')
        try {
          // result.columnData.id là số nguyên từ DB, không có prefix
          let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khonggian/insert-toado-doituong', {
            doiTuongID: result.columnData.id,
            loaiDoiTuong: 'COT',
            dsToaDo: result.polygon.map((p) => ({ toaDoX: p[0], toaDoY: p[1] }))
          })
          this.handleCreateColumnConfirm(result)
        } catch (error) {
          console.error('Error inserting column polygon:', error)
        }
      }
    },

    handleCreateColumnConfirm(result) {
      const diagram = this.$refs.diagramObj.ej2Instances

      if (!this._pendingColumnData) {
        console.error('❌ No pending column data')
        return
      }

      const column = this._pendingColumnData.column
      const originalPolygon = result.polygon

      // Tính bounds và chuyển về tọa độ tương đối
      const xs = originalPolygon.map((p) => p[0])
      const ys = originalPolygon.map((p) => p[1])
      const minX = Math.min(...xs)
      const maxX = Math.max(...xs)
      const minY = Math.min(...ys)
      const maxY = Math.max(...ys)
      const width = maxX - minX
      const height = maxY - minY

      const relativePolygon = originalPolygon.map((p) => [p[0] - minX, p[1] - minY])

      // Cập nhật column
      column.polygon = relativePolygon

      // Tạo node
      const columnNode = this.createColumnNode(column)

      // Tìm vị trí hợp lệ trong phòng
      let foundPosition = this.findValidPositionInRoom(width, height)
      if (!foundPosition) {
        const bounds = this.getPolygonBounds()
        foundPosition = {
          x: bounds.minX + (bounds.width - width) / 2,
          y: bounds.minY + (bounds.height - height) / 2
        }
      }

      columnNode.offsetX = foundPosition.x
      columnNode.offsetY = foundPosition.y
      columnNode.pivot = { x: 0, y: 0 }

      if (!columnNode.addInfo.children) {
        columnNode.addInfo.children = []
      }

      diagram.add(columnNode)

      // Remove from palette
      const index = this.uninstalledColumns.findIndex((c) => c.id === column.id)
      if (index !== -1) {
        this.uninstalledColumns.splice(index, 1)
      }

      this._pendingColumnData = null
      this.$toast.success(`Đã tạo ${column.name} với polygon tùy chỉnh`)
    },

    handleEditColumnConfirm(result) {
      const diagram = this.$refs.diagramObj.ej2Instances

      if (!this._editingColumnNode) {
        console.error('❌ No editing column node')
        return
      }

      const node = this._editingColumnNode
      const originalPolygon = result.polygon

      // Tính bounds và chuyển về tọa độ tương đối
      const xs = originalPolygon.map((p) => p[0])
      const ys = originalPolygon.map((p) => p[1])
      const minX = Math.min(...xs)
      const maxX = Math.max(...xs)
      const minY = Math.min(...ys)
      const maxY = Math.max(...ys)
      const width = maxX - minX
      const height = maxY - minY

      const relativePolygon = originalPolygon.map((p) => [p[0] - minX, p[1] - minY])

      // Tạo path data mới
      const pathData =
        relativePolygon
          .map((p, i) => {
            return i === 0 ? `M ${p[0]} ${p[1]}` : `L ${p[0]} ${p[1]}`
          })
          .join(' ') + ' Z'

      // Cập nhật node
      node.width = width
      node.height = height
      node.offsetX = minX
      node.offsetY = minY
      node.pivot = { x: 0, y: 0 } // ✅ Top-left origin
      node.shape.data = pathData
      node.addInfo.polygon = relativePolygon

      // Cập nhật annotation
      if (node.annotations && node.annotations.length > 0) {
        node.annotations[0].content = node.addInfo.columnName
      }

      diagram.dataBind()
      this._editingColumnNode = null

      this.$toast.success(`Đã cập nhật ${node.addInfo.columnName}`)
    },

    onColumnPolygonCancel() {
      this._pendingColumnData = null
      this._editingColumnNode = null
    },

    // ========== UTILITY METHODS ==========

    // Constraint vị trí node trong polygon phòng
    constrainNodePosition(element) {
      const bounds = this.getPolygonBounds()
      const width = element.width
      const height = element.height

      // Với pivot (0,0), offsetX/Y là góc trên trái
      const minX = bounds.minX
      const minY = bounds.minY
      const maxX = bounds.maxX - width
      const maxY = bounds.maxY - height

      // Clamp position
      if (element.offsetX < minX) element.offsetX = minX
      if (element.offsetX > maxX) element.offsetX = maxX
      if (element.offsetY < minY) element.offsetY = minY
      if (element.offsetY > maxY) element.offsetY = maxY

      // Kiểm tra cả 4 góc có nằm trong polygon không
      const topLeft = this.isPointInPolygon(element.offsetX, element.offsetY)
      const topRight = this.isPointInPolygon(element.offsetX + width, element.offsetY)
      const bottomLeft = this.isPointInPolygon(element.offsetX, element.offsetY + height)
      const bottomRight = this.isPointInPolygon(element.offsetX + width, element.offsetY + height)
      const isInside = topLeft && topRight && bottomLeft && bottomRight

      if (!isInside) {
        // Nếu không nằm trong polygon, đưa về góc trên trái
        element.offsetX = bounds.minX
        element.offsetY = bounds.minY
      }
    },

    findValidPositionInRoom(width, height) {
      const bounds = this.getPolygonBounds()
      const margin = 10

      // Thử từ góc trên trái
      for (let y = bounds.minY + margin; y < bounds.maxY - height - margin; y += 20) {
        for (let x = bounds.minX + margin; x < bounds.maxX - width - margin; x += 20) {
          const corners = [
            [x, y],
            [x + width, y],
            [x, y + height],
            [x + width, y + height]
          ]

          const allInside = corners.every(corner => this.isPointInPolygon(corner[0], corner[1]))

          if (allInside) {
            return { x, y }
          }
        }
      }

      return null
    },

    // ========== TOOLBAR METHODS ==========

    onScrollChange(args) {
      if (args && args.newValue && args.newValue.currentZoom !== undefined) {
        this.currentZoom = args.newValue.currentZoom
      }
    },

    onZoomIn() {
      if (!this.isZoomInDisabled) {
        const diagram = this.$refs.diagramObj.ej2Instances
        diagram.zoomTo({ type: 'ZoomIn', zoomFactor: 0.2 })
      }
    },

    onZoomOut() {
      if (!this.isZoomOutDisabled) {
        const diagram = this.$refs.diagramObj.ej2Instances
        diagram.zoomTo({ type: 'ZoomOut', zoomFactor: 0.2 })
      }
    },

    onReset() {
      const diagram = this.$refs.diagramObj.ej2Instances
      diagram.reset()
      diagram.fitToPage()
    },

    // ========== OPEN CRUD MODALS ==========

    onOpenQuanLyQuat() {
      this.$refs.modalCRUDQuat.showModal()
    },

    onOpenQuanLyCot() {
      this.$refs.modalCRUDCot.showModal()
    },

    async onRefreshData() {
      // Refresh data sau khi CRUD modal đóng
      await this.loadData()
      this.initializeDiagram()
    },

    async onSave() {
      try {
        // Thu thập tất cả vị trí hiện tại
        const diagram = this.$refs.diagramObj.ej2Instances
        const fanPositions = []
        const columnPositions = []

        diagram.nodes.forEach(node => {
          if (node.addInfo && node.addInfo.nodeType === 'fan' && node.addInfo.isFan) {
            fanPositions.push({
              id: node.addInfo.fanId,
              name: node.addInfo.fanName,
              tenTat: node.addInfo.fanTenTat,
              width: node.width,
              height: node.height,
              x: node.offsetX,
              y: node.offsetY,
              ghiChu: node.addInfo.ghiChu,
              moTa: node.addInfo.moTa,
              columnId: node.addInfo.parentColumnId || null
            })
          } else if (node.addInfo && node.addInfo.nodeType === 'column' && node.addInfo.isColumn) {
            columnPositions.push({
              id: node.addInfo.columnId,
              name: node.addInfo.columnName,
              tenTat: node.addInfo.columnTenTat,
              x: node.offsetX,
              y: node.offsetY,
              polygon: node.addInfo.polygon,
              width: node.width,
              height: node.height,
              ghiChu: node.addInfo.ghiChu,
              moTa: node.addInfo.moTa
            })
          }
        })



        // Gọi API để lưu vị trí tất cả fans
        const fanPromises = fanPositions.map(fan =>
          this.$root.context.post('/web-ecms/tainguyen-idc/khonggian/update-vitri-doituong-khonggian', {
            loaiDoiTuong: 'QUAT',
            doiTuongId: fan.id,
            toaDoX: fan.x,
            toaDoY: fan.y
          })
        )

        // Gọi API để lưu vị trí tất cả columns
        const columnPromises = columnPositions.map(column =>
          this.$root.context.post('/web-ecms/tainguyen-idc/khonggian/update-vitri-doituong-khonggian', {
            loaiDoiTuong: 'COT',
            doiTuongId: column.id,
            toaDoX: column.x,
            toaDoY: column.y
          })
        )

        // Chờ tất cả API calls hoàn thành
        const results = await Promise.all([...fanPromises, ...columnPromises])

        // Kiểm tra có lỗi không
        const errors = results.filter(rs => rs.error_code !== 'BSS-00000000')
        if (errors.length > 0) {
          console.error('Some positions failed to save:', errors)
          this.$toast.warning(`Đã lưu thành công ${results.length - errors.length}/${results.length} vị trí`)
        } else {
          this.$toast.success('Đã lưu tất cả vị trí thành công!')
        }

        // ✅ Emit event để parent component cập nhật sơ đồ chính
        this.$emit('save', {
          fans: fanPositions,
          columns: columnPositions
        })
      } catch (error) {
        console.error('Error saving:', error)
        this.$toast.error('Có lỗi khi lưu dữ liệu!')
      }
    }
  }
}
</script>

<style scoped>
.modal-content-wrapper {
  height: 80vh;
  display: flex;
  flex-direction: column;
}

.list-actions-top {
  flex-shrink: 0;
  margin-bottom: 10px;
}

.diagram-workspace {
  display: flex;
  flex: 1;
  min-height: 600px;
  background-color: #fff;
  border: 1px solid #d9d9d9;
  overflow: hidden;
}

.palette-container {
  width: 250px;
  background: white;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  flex-shrink: 0;
}

.diagram-container {
  flex: 1;
  background: white;
  overflow: hidden;
  position: relative;
  min-width: 0;
  user-select: none;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
}

.list-actions-top .list li a.disabled {
  opacity: 0.5;
  cursor: not-allowed;
  pointer-events: none;
}
</style>

<style>
/* Fan palette card styling */
.fan-palette-card {
  width: 60px;
  height: 70px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  border: 1.5px solid #2196F3;
  border-radius: 6px;
  box-sizing: border-box;
  padding: 4px 3px;
  background: white;
  transition: all 0.2s;
  margin: 3px;
}

.fan-palette-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 8px rgba(33, 150, 243, 0.2);
}

.fan-palette-card .fan-title {
  font-size: 9px;
  font-weight: 600;
  text-align: center;
  color: #333;
  line-height: 1.1;
}

.fan-palette-card .fan-icon {
  font-size: 18px;
  line-height: 1;
  flex-shrink: 0;
}

.fan-palette-card .fan-desc {
  font-size: 8px;
  color: #666;
  text-align: center;
}

/* Fan diagram node styling */
.fan-diagram-node {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border: 2px solid;
  border-radius: 6px;
  box-sizing: border-box;
  padding: 4px;
  background: white;
}

.fan-diagram-node .fan-node-icon {
  font-size: 20px;
  line-height: 1;
}

.fan-diagram-node .fan-node-name {
  font-size: 9px;
  font-weight: 600;
  text-align: center;
  color: #333;
  line-height: 1.1;
  margin-top: 2px;
}

/* Column palette card styling */
.column-palette-card {
  width: 60px;
  height: 85px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  border: 1.5px solid #9E9E9E;
  border-radius: 6px;
  box-sizing: border-box;
  padding: 4px 3px;
  background: rgba(158, 158, 158, 0.05);
  transition: all 0.2s;
  margin: 3px;
  position: relative;
}

.column-palette-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 8px rgba(158, 158, 158, 0.2);
}

.column-palette-card .column-title {
  font-size: 9px;
  font-weight: 600;
  text-align: center;
  color: #424242;
  line-height: 1.1;
}

.column-palette-card .column-icon {
  font-size: 22px;
  line-height: 1;
  flex-shrink: 0;
}

.column-palette-card .column-desc {
  font-size: 8px;
  color: #666;
  text-align: center;
}

.column-palette-card .column-badge {
  position: absolute;
  top: -8px;
  right: -8px;
  background: #9E9E9E;
  color: white;
  font-size: 9px;
  font-weight: bold;
  padding: 2px 6px;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  z-index: 10;
}
</style>
<style>
.modal-quan-ly-quat-cot .modal-dialog {
  max-width: 95vw !important;
  width: 95vw !important;
  margin: 1rem auto;
}

.modal-quan-ly-quat-cot .modal-content {
  height: 95vh;
}

.modal-quan-ly-quat-cot .modal-body {
  padding: 0;
  height: calc(95vh - 60px);
  overflow: scroll;
}
</style>
