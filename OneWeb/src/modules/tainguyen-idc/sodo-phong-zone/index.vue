<template src="./index.html"></template>

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

import RackDetailModal from './components/modals/RackDetailModal.vue'
import SelectCageModal from './components/modals/SelectCageModal.vue'
import DrawCagePolygonModal from './components/modals/DrawCagePolygonModal.vue'
import ModalThongtinLongQuay from '../thongtin-longquay/components/ModalThongtinLongQuay.vue'
import ModalChiTietThietBi from './components/chitiet-thietbi/index.vue'
import DrawAislePolygonModal from './components/modals/DrawAislePolygonModal.vue'
import ModalThongtinHanhLang from './components/modals/ModalThongtinHanhLang.vue'
import ModalCRUDHanhLang from './components/modals/ModalCRUDHanhLang.vue'
import FormPhong from '../thongtin-phong-zone/components/PhongZoneDetailForm/PhongZoneDetailForm.vue'
import ModalQuanLyQuatCot from './components/modals/ModalQuanLyQuatCot.vue'
export default {
  components: {
    RackDetailModal,
    SelectCageModal,
    ModalChiTietThietBi,
    DrawCagePolygonModal,
    DrawAislePolygonModal,
    ModalThongtinLongQuay,
    ModalThongtinHanhLang,
    ModalCRUDHanhLang,
    FormPhong,
    ModalQuanLyQuatCot
  },

  props: {
    modalItemID: {
      type: Number,
      default: 0
    }
  },

  provide: {
    diagram: [PrintAndExport, UndoRedo, Snapping, DiagramContextMenu]
  },

  data: function () {
    return {
      currentZoom: 1,
      roomPolygon: [],
      modalComponentKey: 0,
      _pendingAisleData: null,

      // Dữ liệu rack
      uninstalledRacks: [],
      installedRacks: [],

      // Map lưu các rack chồng lên nhau theo vị trí (key: "x,y", value: [rack1, rack2, ...])
      stackedRacksMap: {},

      // State cho drag detection
      isDraggingRack: false,
      draggingRackId: null,
      dragTargetPosition: null, // { x, y, hasOverlap, overlappingRacks: [] }
      dragOldPosition: null, // { x, y } - vị trí cũ khi bắt đầu kéo
      positionChangeTimeout: null, // Timer để debounce positionChange

      // Dữ liệu lồng quây (cage)
      uninstalledCages: [],
      installedCages: [],

      // Dữ liệu hành lang (aisle)
      uninstalledAisles: [],
      installedAisles: [],

      // Dữ liệu thiết bị (equipment)
      uninstalledEquipments: [],
      installedEquipments: [],

      // Dữ liệu quạt (fan)
      installedFans: [],

      // Dữ liệu cột (column)
      installedColumns: [],

      // Backup data để restore khi xóa
      allCagesBackup: [],
      allAislesBackup: [],
      allEquipmentsBackup: [],

      isDataLoaded: false,

      // Stacked racks modal
      stackedRacksList: [],

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
        minZoom: 0.1,
        maxZoom: 3,
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
            text: 'Chỉnh sửa lồng quây',
            id: 'editCage',
            target: '.e-elementcontent',
            iconCss: 'icon fa fa-edit'
          },
          {
            text: 'Chỉnh sửa hành lang',
            id: 'editAisle',
            target: '.e-elementcontent',
            iconCss: 'icon fa fa-edit'
          },
          {
            text: 'Chuyển vào quây',
            id: 'moveToCage',
            target: '.e-elementcontent',
            iconCss: 'icon fa fa-object-group'
          },
          {
            text: 'Tách khỏi quây',
            id: 'removeFromCage',
            target: '.e-elementcontent',
            iconCss: 'icon fa fa-object-ungroup'
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

      // Equipment detail modal
      selectedEquipmentDetail: null,

      // Cage detail modal
      selectedCageDetail: null,

      // Aisle detail modal
      selectedAisleDetail: null,

      // Selected items constraints
      selectedItems: {
        constraints: SelectorConstraints.All & ~SelectorConstraints.ResizeAll & ~SelectorConstraints.Rotate
      },

      // Room container node ID
      roomContainerId: 'room_container',

      // Rack detail modal
      selectedRackDetail: null,
      showEditModal: false
    }
  },

  computed: {
    // Palette symbols
    palettes() {
      if (!this.isDataLoaded) {
        return [
          {
            id: 'cages',
            expanded: true,
            title: 'Lồng quây',
            symbols: []
          },
          {
            id: 'aisles',
            expanded: true,
            title: 'Hành lang',
            symbols: []
          },
          {
            id: 'racks',
            expanded: true,
            title: 'Rack chưa lắp đặt',
            symbols: []
          },
          {
            id: 'equipments',
            expanded: true,
            title: 'Thiết bị chưa lắp đặt',
            symbols: []
          }
        ]
      }

      const cageSymbols = this.uninstalledCages.map((cage) => {
        return this.createCagePaletteNode(cage)
      })

      const aisleSymbols = this.uninstalledAisles.map((aisle) => {
        return this.createAislePaletteNode(aisle)
      })

      const rackSymbols = this.uninstalledRacks.map((rack) => {
        return this.createRackPaletteNode(rack)
      })

      const equipmentSymbols = this.uninstalledEquipments.map((equipment) => {
        return this.createEquipmentPaletteNode(equipment)
      })

      return [
        {
          id: 'cages',
          expanded: true,
          title: `Lồng quây chưa bố trí (${cageSymbols.length})`,
          symbols: cageSymbols
        },
        {
          id: 'aisles',
          expanded: true,
          title: `Hành lang chưa bố trí (${aisleSymbols.length})`,
          symbols: aisleSymbols
        },
        {
          id: 'racks',
          expanded: true,
          title: `Rack chưa lắp đặt (${rackSymbols.length})`,
          symbols: rackSymbols
        },
        {
          id: 'equipments',
          expanded: true,
          title: `Thiết bị chưa lắp đặt (${equipmentSymbols.length})`,
          symbols: equipmentSymbols
        }
      ]
    },

    // Page settings dựa trên polygon
    pageSettings() {
      const bounds = this.getPolygonBounds()
      // Đảm bảo page đủ lớn cho phòng có kích thước lớn, tối thiểu 1000x1000
      const pageWidth = Math.max(bounds.width + 200, 1000)
      const pageHeight = Math.max(bounds.height + 200, 1000)
      return {
        orientation: 'Landscape',
        width: pageWidth,
        height: pageHeight,
        multiplePage: false,
        showPageBreaks: false,
        background: {
          color: 'white'
        },
        boundaryConstraints: 'Page'
      }
    },

    modalTitle() {
      return this.modalMode === 'ADD'
        ? 'Nhập mới Phòng/Zone'
        : this.modalMode === 'EDIT'
          ? 'Chỉnh sửa Phòng/Zone'
          : 'Thông tin Phòng/Zone'
    },

    isZoomInDisabled() {
      // Use this.currentZoom which is reactive and updated by onScrollChange
      // Max zoom actual value is around 2.592 (when maxZoom = 3)
      return this.currentZoom >= 2.9
    },

    isZoomOutDisabled() {
      // Use this.currentZoom which is reactive and updated by onScrollChange
      // Min zoom actual value is around 0.1157 (when minZoom = 0.1)
      return this.currentZoom <= 0.12
    }
  },

  created: function () { },

  async mounted() {
    try {
      document.addEventListener('keydown', this.preventDeleteKey)

      try {
        await this.loadRackData()
      } catch (error) {
        console.warn('Could not load rack data, continuing anyway:', error)
        this.isDataLoaded = true
      }

      await this.$nextTick()
      await this.$nextTick()

      this.initializeDiagram()

      // Debug: Check if modal component is available
      console.log('Modal component ref:', this.$refs.modalQuanLyQuatCot)
    } catch (error) {
      console.error('Error during mounted:', error)
    }
  },

  beforeDestroy() {
    document.removeEventListener('keydown', this.preventDeleteKey)
  },

  methods: {
    async handleModalSave() {
      this.showEditModal = false
      this.modalComponentKey += 1
      // Reload toàn bộ dữ liệu phòng sau khi modal save
      await this.loadRoomData()
    },

    // Method reload toàn bộ dữ liệu phòng
    async loadRoomData() {
      if (!this.$refs.diagramObj || !this.$refs.diagramObj.ej2Instances) {
        console.warn('Diagram not ready, skipping reload')
        return
      }

      const diagram = this.$refs.diagramObj.ej2Instances

      try {
        // Lưu tham chiếu đến các node hệ thống quan trọng TRƯỚC KHI xóa
        const systemNodes = diagram.nodes.filter(node => {
          return node.id?.includes('_boundary') ||
                 node.id?.includes('_mask') ||
                 node.id?.startsWith('edgeLabel_') ||
                 node.id?.startsWith('grid_') ||
                 (node.addInfo && (
                   node.addInfo.isBoundary ||
                   node.addInfo.isMask ||
                   node.addInfo.isRoomContainer ||
                   node.addInfo.isGridCell ||
                   node.addInfo.isGridLabel ||
                   node.addInfo.isGridHeader
                 ))
        })

        console.log('Found', systemNodes.length, 'system nodes to protect')

        // Xóa CHÍNH XÁC chỉ fan/column nodes cũ - KHÔNG XÓA boundary, mask, grid
        this.removeOldFansAndColumns(diagram)

        // Kiểm tra xem các node hệ thống còn tồn tại không
        const remainingSystemNodes = systemNodes.filter(node => {
          return diagram.nodes.some(n => n.id === node.id)
        })

        console.log('System nodes still present:', remainingSystemNodes.length, '/', systemNodes.length)

        if (remainingSystemNodes.length < systemNodes.length) {
          console.error('⚠️ Some system nodes were removed! This should not happen.')
        }

        // Reload fans và columns từ API
        console.log('🌐 Fetching data from API...')
        const [fans, columns] = await Promise.all([
          this.getInstalledFans(),
          this.getInstalledColumns()
        ])

        console.log('📊 API Response - Fans:', fans.length, fans)
        console.log('📊 API Response - Columns:', columns.length, columns)

        // Update data
        this.installedFans = fans
        this.installedColumns = columns

        console.log('💾 Data updated in component state')

        // Render lại fans và columns
        this.renderFansAndColumns(diagram)

        // Refresh diagram một cách an toàn - CHỈ refresh layer hiển thị
        this.$nextTick(() => {
          diagram.refresh()
          console.log('🔒 After refresh - Total nodes:', diagram.nodes.length)
        })

        console.log('========== ROOM DATA LOADED SUCCESSFULLY ==========')
      } catch (error) {
        console.error('❌ Error reloading room data:', error)
        this.$toast.error('Có lỗi khi tải lại dữ liệu')
      }
    },

    onScrollChange(args) {
      // Update current zoom when scroll/zoom changes
      if (args && args.newValue && args.newValue.currentZoom !== undefined) {
        this.currentZoom = args.newValue.currentZoom
      }
    },

    onAddNewPhongZone() {
      this.modalMode = 'ADD'
      this.selectedPhongId = null
      this.showEditModal = true
    },

    onEditPhongZone() {
      this.modalMode = 'EDIT'
      this.selectedPhongId = this.modalItemID
      this.showEditModal = true
    },

    onOpenQuatCotModal() {
      this.debugModalState()

      if (!this.isDataLoaded) {
        console.warn('Data not loaded yet, waiting...')
        // Wait for data to load
        const checkInterval = setInterval(() => {
          if (this.isDataLoaded) {
            clearInterval(checkInterval)
            this.onOpenQuatCotModal()
          }
        }, 100)
        return
      }

      if (this.$refs.modalQuanLyQuatCot) {
        console.log('Modal found, showing...')
        this.$refs.modalQuanLyQuatCot.show()
      } else {
        console.error('Modal component not found, retrying...')
        // Try again after nextTick
        this.$nextTick(() => {
          this.debugModalState()
          if (this.$refs.modalQuanLyQuatCot) {
            console.log('Modal found after nextTick, showing...')
            this.$refs.modalQuanLyQuatCot.show()
          } else {
            console.error('Modal component still not found after nextTick')
          }
        })
      }
    },

    async onQuatCotModalClose() {
      await this.reloadFansAndColumns()
    },

    // Reload fans và columns từ API
    async reloadFansAndColumns() {
      if (!this.$refs.diagramObj || !this.$refs.diagramObj.ej2Instances) {
        console.warn('Diagram not ready')
        return
      }
      const diagram = this.$refs.diagramObj.ej2Instances

      try {
        // Step 1: Remove old fan/column nodes - CHỈ XÓA FAN/COLUMN, KHÔNG ĐỘNG VÀO HỆ THỐNG
        let nodesToRemove = diagram.nodes.filter(n => {
          const shouldRemove = n.addInfo && (n.addInfo.isFan || n.addInfo.isColumn)

          const isSystemNode = n.id?.includes('_boundary') ||
                               n.id?.includes('_mask') ||
                               n.id?.startsWith('edgeLabel_') ||
                               n.id?.startsWith('grid_') ||
                               n.addInfo?.isBoundary ||
                               n.addInfo?.isMask ||
                               n.addInfo?.isGridLabel ||
                               n.addInfo?.isGridHeader

          return shouldRemove && !isSystemNode
        })

        if (nodesToRemove.length === 0) {
          console.warn('NO NODES TO REMOVE! Checking all nodes with fan/column in ID:')
          const suspectNodes = diagram.nodes.filter(n => n.id && (n.id.includes('fan') || n.id.includes('column')))
        }

        nodesToRemove.forEach(node => {
          try {
            // Nhưng phải bật lại constraints trước
            const originalConstraints = node.constraints

            // Temporarily enable default constraints
            node.constraints = NodeConstraints.Default

            // Remove using Syncfusion API
            diagram.remove(node)

            console.log('Removed:', node.id)
          } catch (error) {
            console.error('Error removing node:', node.id, error.message)
          }
        })

        // Step 2: Reload từ API
        const [fans, columns] = await Promise.all([
          this.getInstalledFans(),
          this.getInstalledColumns()
        ])

        // Step 3: Update component state
        this.installedFans = fans
        this.installedColumns = columns

        // Step 4: Render columns trước (z-index thấp hơn)
        this.installedColumns.forEach((column) => {
          if (!column.polygon || !Array.isArray(column.polygon) || column.polygon.length < 3) {
            console.warn('Column has invalid polygon:', column.name)
            return
          }

          const columnNode = this.createColumnNode(column)
          columnNode.offsetX = column.x
          columnNode.offsetY = column.y
          columnNode.pivot = { x: 0, y: 0 }
          diagram.add(columnNode)
        })

        // Step 5: Render fans sau (z-index cao hơn)
        this.installedFans.forEach((fan) => {
          const fanNode = this.createFanNode(fan)
          fanNode.offsetX = fan.x
          fanNode.offsetY = fan.y
          fanNode.pivot = { x: 0, y: 0 }
          diagram.add(fanNode)
        })

        // Step 6: KHÔNG cần refresh - diagram.remove() đã tự động update visual
        this.$nextTick(() => {
          // Verify system nodes vẫn còn
          const systemNodes = diagram.nodes.filter(n =>
            n.id?.includes('_boundary') ||
            n.id?.includes('_mask') ||
            n.id?.startsWith('edgeLabel_') ||
            n.id?.startsWith('grid_')
          )
          if (systemNodes.length < 335) {
            console.error('SOME SYSTEM NODES WERE LOST!')
          }

          // Verify fan/column nodes đã biến mất
          const fanColumnNodes = diagram.nodes.filter(n => n.addInfo && (n.addInfo.isFan || n.addInfo.isColumn))
        })

      } catch (error) {
        console.error('Error reloading fans/columns:', error)
        this.$toast.error('Có lỗi khi tải lại dữ liệu')
      }
    },

    // Debug method
    debugModalState() {
      console.log('=== DEBUG MODAL STATE ===')
      console.log('isDataLoaded:', this.isDataLoaded)
      console.log('roomPolygon:', this.roomPolygon)
      console.log('modalItemID:', this.modalItemID)
      console.log('Modal ref:', this.$refs.modalQuanLyQuatCot)
      console.log('All refs:', Object.keys(this.$refs))
      console.log('=========================')
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
      if (!this.roomPolygon || this.roomPolygon.length === 0) {
        return { minX: 0, minY: 0, maxX: 800, maxY: 600, width: 800, height: 600 }
      }

      const xs = this.roomPolygon.map((p) => p[0])
      const ys = this.roomPolygon.map((p) => p[1])

      const minX = Math.min(...xs)
      const maxX = Math.max(...xs)
      const minY = Math.min(...ys)
      const maxY = Math.max(...ys)

      return {
        minX,
        minY,
        maxX,
        maxY,
        width: maxX - minX,
        height: maxY - minY
      }
    },

    // Kiểm tra điểm có nằm trong polygon không (Ray casting)
    isPointInPolygon(x, y) {
      const polygon = this.roomPolygon
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

    // ========== DIAGRAM INITIALIZATION ==========

    initializeDiagram() {
      if (!this.$refs.diagramObj || !this.$refs.diagramObj.ej2Instances) {
        console.error('Diagram not ready')
        return
      }

      const diagram = this.$refs.diagramObj.ej2Instances

      // 1. Tạo room boundary
      this.createRoomContainer(diagram)

      // 2. Load các rack đã lắp đặt vào container
      this.loadInstalledRacks(diagram)

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

      const pathData =
        this.roomPolygon
          .map((p, i) => {
            const relX = p[0] - centerX
            const relY = p[1] - centerY
            return i === 0 ? `M ${relX} ${relY}` : `L ${relX} ${relY}`
          })
          .join(' ') + ' Z'

      // Node hiển thị boundary (Path node)
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
        constraints: NodeConstraints.None, // Không cho select, drag, resize
        zIndex: 0,
        addInfo: {
          isBoundary: true
        }
      }

      // Container node trong suốt (để chứa rack)
      const containerNode = {
        id: this.roomContainerId,
        offsetX: centerX,
        offsetY: centerY,
        width: bounds.width,
        height: bounds.height,
        shape: {
          type: 'Basic',
          shape: 'Rectangle'
        },
        style: {
          fill: 'transparent',
          strokeColor: 'transparent',
          strokeWidth: 0
        },
        constraints: NodeConstraints.None, // Không cho select, drag, resize
        zIndex: 1,
        addInfo: {
          isRoomContainer: true,
          modalItemID: this.modalItemID,
          polygon: this.roomPolygon
        }
      }

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

      // Add boundary sau (trên mask)
      diagram.add(boundaryNode)

      // Vẽ thêm dimension labels cho các cạnh
      this.drawDimensionLabels(diagram)

      // Vẽ grid với labels
      this.drawGridLabels(diagram)
    },

    // Vẽ grid với labels (AA, AB, AC... và 01, 02, 03...)
    drawGridLabels(diagram) {
      const bounds = this.getPolygonBounds()
      const cellSize = 60 // Kích thước mỗi ô grid (cm)

      // Tính số cột và hàng - BẰNG CEIL để bao gồm cả ô không đủ kích thước
      const cols = Math.ceil(bounds.width / cellSize)
      const rows = Math.ceil(bounds.height / cellSize)

      // Labels cho cột - CHỮ CÁI (AA, AB, AC... skip I) - TRỤC NGANG
      // Bỏ qua chữ I để tránh nhầm lẫn với số 1
      const colLabels = []
      let currentLabel = 'AA'
      for (let i = 0; i < cols; i++) {
        colLabels.push(currentLabel)
        // Tăng label
        const chars = currentLabel.split('')
        let carry = 1
        for (let j = chars.length - 1; j >= 0 && carry; j--) {
          let charCode = chars[j].charCodeAt(0) + carry
          if (charCode > 90) { // 'Z'
            chars[j] = 'A'
          } else {
            // Bỏ qua chữ I
            if (charCode === 73) charCode = 74 // I -> J
            chars[j] = String.fromCharCode(charCode)
            carry = 0
          }
        }
        if (carry) chars.unshift('A')
        currentLabel = chars.join('')
      }

      // Vẽ grid lines và cell labels
      for (let row = 0; row < rows; row++) {
        for (let col = 0; col < cols; col++) {
          const x = bounds.minX + col * cellSize
          const y = bounds.minY + row * cellSize

          // Tính kích thước thực tế của ô (ô cuối cùng có thể nhỏ hơn)
          let actualWidth = cellSize
          let actualHeight = cellSize

          // Ô ở cột cuối cùng
          if (col === cols - 1) {
            actualWidth = bounds.maxX - x
            if (actualWidth <= 0) continue
          }

          // Ô ở hàng cuối cùng
          if (row === rows - 1) {
            actualHeight = bounds.maxY - y
            if (actualHeight <= 0) continue
          }

          // Kiểm tra ô có nằm trong polygon không (dùng center hoặc góc trên trái)
          const centerX = x + actualWidth / 2
          const centerY = y + actualHeight / 2
          const topLeftInside = this.isPointInPolygon(x + 1, y + 1)
          const centerInside = this.isPointInPolygon(centerX, centerY)

          // Hiển thị nếu center hoặc top-left nằm trong polygon
          if (!topLeftInside && !centerInside) continue

          // Cell label: CHỮ + SỐ (AA01, AB01, AC02...)
          const rowLabel = String(row + 1).padStart(2, '0')
          const cellLabel = `${colLabels[col]}${rowLabel}`

          // Vẽ rectangle cho cell với kích thước thực tế
          diagram.add({
            id: `grid_cell_${row}_${col}`,
            offsetX: x,
            offsetY: y,
            width: actualWidth,
            height: actualHeight,
            pivot: { x: 0, y: 0 },
            shape: {
              type: 'Basic',
              shape: 'Rectangle'
            },
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

          // Vẽ label cho cell (ở góc trên trái) - chỉ hiển thị nếu ô đủ rộng
          if (actualWidth >= 30 && actualHeight >= 15) {
            diagram.add({
              id: `grid_label_${row}_${col}`,
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

      // Vẽ column headers - CHỮ CÁI (AA, AB, AC...) - PHÍA TRÊN (trục ngang)
      for (let col = 0; col < cols; col++) {
        const x = bounds.minX + col * cellSize

        // Tính width thực tế cho header
        let headerWidth = cellSize
        if (col === cols - 1) {
          headerWidth = bounds.maxX - x
        }

        diagram.add({
          id: `grid_col_header_${col}`,
          offsetX: x + headerWidth / 2,
          offsetY: bounds.minY - 25,
          width: headerWidth,
          height: 20,
          shape: {
            type: 'Text',
            content: colLabels[col]
          },
          style: {
            fontSize: 12,
            fill: '#F5F5F5',
            color: '#757575',
            bold: true,
            textAlign: 'Center'
          },
          constraints: NodeConstraints.None,
          zIndex: 1001,
          addInfo: {
            isGridHeader: true
          }
        })
      }

      // Vẽ row headers - SỐ (01, 02, 03...) - BÊN TRÁI (trục dọc)
      for (let row = 0; row < rows; row++) {
        const y = bounds.minY + row * cellSize
        const rowLabel = String(row + 1).padStart(2, '0')

        // Tính height thực tế cho header
        let headerHeight = cellSize
        if (row === rows - 1) {
          headerHeight = bounds.maxY - y
        }

        diagram.add({
          id: `grid_row_header_${row}`,
          offsetX: bounds.minX - 30,
          offsetY: y + headerHeight / 2,
          width: 25,
          height: headerHeight,
          shape: {
            type: 'Text',
            content: rowLabel
          },
          style: {
            fontSize: 12,
            fill: '#F5F5F5',
            color: '#757575',
            bold: true,
            textAlign: 'Center'
          },
          constraints: NodeConstraints.None,
          zIndex: 1001,
          addInfo: {
            isGridHeader: true
          }
        })
      }
    },

    // Vẽ labels hiển thị độ dài các cạnh
    drawDimensionLabels(diagram) {
      const offset = 20 // Khoảng cách từ cạnh đến label

      for (let i = 0; i < this.roomPolygon.length; i++) {
        const p1 = this.roomPolygon[i]
        const p2 = this.roomPolygon[(i + 1) % this.roomPolygon.length]

        // Tính độ dài cạnh
        const dx = p2[0] - p1[0]
        const dy = p2[1] - p1[1]
        const length = Math.sqrt(dx * dx + dy * dy)

        // Tính điểm giữa cạnh
        const midX = (p1[0] + p2[0]) / 2
        const midY = (p1[1] + p2[1]) / 2

        // Tính vector vuông góc với cạnh (để đặt label ra ngoài)
        const len = Math.sqrt(dx * dx + dy * dy)
        const nx = -dy / len // Vector vuông góc
        const ny = dx / len

        // Đặt label ra ngoài cạnh
        const labelX = midX + nx * offset
        const labelY = midY + ny * offset

        // Tính góc xoay của text để song song với cạnh
        let angle = (Math.atan2(dy, dx) * 180) / Math.PI
        if (angle > 90) angle -= 180
        if (angle < -90) angle += 180

        diagram.add({
          id: `edgeLabel_${i}`,
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
          constraints: NodeConstraints.None, // Không cho select, drag
          zIndex: 1000
        })
      }
    },

    // Load các rack đã lắp đặt
    loadInstalledRacks(diagram) {
      // Load cages trước (z-index thấp hơn)
      this.installedCages.forEach((cage) => {
        // Kiểm tra polygon hợp lệ
        if (!cage.polygon || !Array.isArray(cage.polygon) || cage.polygon.length < 3) {
          console.error('❌ Invalid cage polygon:', cage)
          return
        }

        const cageNode = this.createCageNode(cage)

        // Đặt vị trí cage (góc trên bên trái)
        cageNode.offsetX = cage.x
        cageNode.offsetY = cage.y
        cageNode.pivot = { x: 0, y: 0 } // Pivot ở góc trên bên trái

        diagram.add(cageNode)
      })

      // Load aisles (z-index thấp hơn racks nhưng cao hơn cages)
      this.installedAisles.forEach((aisle) => {
        // Kiểm tra polygon hợp lệ
        if (!aisle.polygon || !Array.isArray(aisle.polygon) || aisle.polygon.length < 3) {
          console.error('❌ Invalid aisle polygon:', aisle)
          return
        }

        const aisleNode = this.createAisleNode(aisle)

        // Đặt vị trí aisle (góc trên bên trái)
        aisleNode.offsetX = aisle.x
        aisleNode.offsetY = aisle.y
        aisleNode.pivot = { x: 0, y: 0 } // Pivot ở góc trên bên trái

        diagram.add(aisleNode)
      })

      // Load racks sau (z-index cao hơn)
      this.installedRacks.forEach((rack) => {
        const rackNode = this.createRackNode(rack)

        // Đặt vị trí rack (góc trên bên trái)
        rackNode.offsetX = rack.x
        rackNode.offsetY = rack.y
        rackNode.pivot = { x: 0, y: 0 } // Pivot ở góc trên bên trái

        // Mark node là rack để constraint sau
        rackNode.addInfo.isRack = true

        // Nếu rack có longQuayId (thuộc cage), thêm vào cage
        if (rack.longQuayId && rack.longQuayId !== null && rack.longQuayId !== 0) {
          const cageNode = diagram.getObject(rack.cageId)
          if (cageNode && cageNode.addInfo && cageNode.addInfo.isCage) {
            // Tính tọa độ tương đối của rack trong cage
            const cagePivot = cageNode.pivot || { x: 0, y: 0 }
            const cageTopLeftX = cageNode.offsetX - cageNode.width * cagePivot.x
            const cageTopLeftY = cageNode.offsetY - cageNode.height * cagePivot.y

            const relativeX = rack.x - cageTopLeftX
            const relativeY = rack.y - cageTopLeftY

            // Thêm vào children của cage
            if (!cageNode.addInfo.children) {
              cageNode.addInfo.children = []
            }
            cageNode.addInfo.children.push({
              id: rackNode.id,
              relativeX: relativeX,
              relativeY: relativeY
            })

            // Đánh dấu rack thuộc về cage
            rackNode.addInfo.parentCageId = rack.cageId
          } else {
          }
        }

        diagram.add(rackNode)
      })

      // Load equipments (z-index cao hơn)
      this.installedEquipments.forEach((equipment) => {
        const equipmentNode = this.createEquipmentNode(equipment)

        // Đặt vị trí equipment (góc trên bên trái)
        equipmentNode.offsetX = equipment.x
        equipmentNode.offsetY = equipment.y
        equipmentNode.pivot = { x: 0, y: 0 } // Pivot ở góc trên bên trái

        // Mark node là equipment để constraint sau
        equipmentNode.addInfo.isEquipment = true

        // Nếu equipment có longQuayId (thuộc cage), thêm vào cage
        if (equipment.longQuayId && equipment.longQuayId !== null && equipment.longQuayId !== 0) {
          const cageNode = diagram.getObject(equipment.cageId)
          if (cageNode && cageNode.addInfo && cageNode.addInfo.isCage) {
            // Tính tọa độ tương đối của equipment trong cage
            const cagePivot = cageNode.pivot || { x: 0, y: 0 }
            const cageTopLeftX = cageNode.offsetX - cageNode.width * cagePivot.x
            const cageTopLeftY = cageNode.offsetY - cageNode.height * cagePivot.y

            const relativeX = equipment.x - cageTopLeftX
            const relativeY = equipment.y - cageTopLeftY

            // Thêm vào children của cage
            if (!cageNode.addInfo.children) {
              cageNode.addInfo.children = []
            }
            cageNode.addInfo.children.push({
              id: equipmentNode.id,
              relativeX: relativeX,
              relativeY: relativeY
            })

            // Đánh dấu equipment thuộc về cage
            equipmentNode.addInfo.parentCageId = equipment.cageId
          } else {
            console.warn(`⚠️ Cage ${equipment.cageId} not found for equipment ${equipment.id}`)
          }
        }

        diagram.add(equipmentNode)
      })

      // Load columns (z-index thấp hơn fans)
      this.installedColumns.forEach((column) => {
        // Kiểm tra polygon hợp lệ trước khi render
        if (!column.polygon || !Array.isArray(column.polygon) || column.polygon.length < 3) {
          console.warn('⚠️ Skipping column with invalid polygon:', column.name, column.polygon)
          return
        }

        console.log('Rendering column:', column.name, 'polygon:', column.polygon)

        const columnNode = this.createColumnNode(column)
        columnNode.offsetX = column.x
        columnNode.offsetY = column.y
        columnNode.pivot = { x: 0, y: 0 }

        diagram.add(columnNode)
      })

      // Load fans (z-index cao hơn columns)
      this.installedFans.forEach((fan) => {
        const fanNode = this.createFanNode(fan)
        fanNode.offsetX = fan.x
        fanNode.offsetY = fan.y
        fanNode.pivot = { x: 0, y: 0 }

        // Nếu fan thuộc về column
        if (fan.columnId) {
          const columnNode = diagram.getObject(`column_${fan.columnId}`)
          if (columnNode && columnNode.addInfo && columnNode.addInfo.isColumn) {
            fanNode.addInfo.parentColumnId = fan.columnId
          }
        }

        diagram.add(fanNode)
      })
    },

    // ========== NODE CREATION ==========

    // Tạo rack node cho palette
    createRackPaletteNode(rack) {
      return {
        id: rack.id,
        width: 140,
        height: 120,
        shape: {
          type: 'HTML',
          content: this.createRackPaletteHtml(rack)
        },
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate,
        addInfo: {
          nodeType: 'rack',
          rackId: rack.id,
          rackName: rack.name,
          rackType: rack.type,
          totalU: rack.totalU,
          width: rack.width || 60,
          height: rack.height || 60,
          color: rack.color || '#2196F3'
        }
      }
    },

    // ========== CAGE NODE CREATION ==========

    // Tạo cage node cho palette
    createCagePaletteNode(cage) {
      return {
        id: cage.id,
        width: 140,
        height: 120,
        shape: {
          type: 'HTML',
          content: this.createCagePaletteHtml(cage)
        },
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate,
        addInfo: {
          nodeType: 'cage',
          cageId: cage.id,
          cageName: cage.name,
          cageType: cage.type,
          polygon: cage.polygon,
          color: cage.color || '#4CAF50'
        }
      }
    },

    // Tạo cage node cho diagram
    createCageNode(cage) {
      // Tính bounds của polygon
      const xs = cage.polygon.map((p) => p[0])
      const ys = cage.polygon.map((p) => p[1])
      const minX = Math.min(...xs)
      const maxX = Math.max(...xs)
      const minY = Math.min(...ys)
      const maxY = Math.max(...ys)
      const width = maxX - minX
      const height = maxY - minY

      // Tạo path data từ polygon (tọa độ tương đối từ góc trên trái)
      const pathData =
        cage.polygon
          .map((p, i) => {
            const relX = p[0] - minX
            const relY = p[1] - minY
            return i === 0 ? `M ${relX} ${relY}` : `L ${relX} ${relY}`
          })
          .join(' ') + ' Z'
      return {
        id: cage.id,
        width: width,
        height: height,
        shape: {
          type: 'Path',
          data: pathData
        },
        style: {
          fill: cage.color ? `${cage.color}15` : 'rgba(76, 175, 80, 0.08)',
          strokeColor: cage.color || '#4CAF50',
          strokeWidth: 2,
          strokeDashArray: '5,5'
        },
        // Thêm annotation để hiển thị tên
        annotations: [
          {
            content: cage.name,
            style: {
              fontSize: 12,
              bold: true,
              color: cage.color || '#4CAF50',
              fill: 'white',
              strokeColor: cage.color || '#4CAF50',
              strokeWidth: 0.5
            },
            // Đặt ở góc trên bên trái
            offset: { x: 0.01, y: -0.1 },
            horizontalAlignment: 'Left',
            verticalAlignment: 'Top',
            margin: { left: 5, top: 5 }
          }
        ],
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate,
        zIndex: 3,
        addInfo: {
          nodeType: 'cage',
          isCage: true,
          isContainer: true, // Đánh dấu là container
          cageId: cage.id,
          cageName: cage.name,
          cageType: cage.type,
          polygon: cage.polygon,
          color: cage.color || '#4CAF50',
          children: [] // Lưu danh sách children để tự xử lý di chuyển
        }
      }
    },

    // HTML template cho cage palette
    createCagePaletteHtml(cage) {
      const hasPolygon = cage.polygon && cage.polygon.length >= 3
      const icon = hasPolygon ? '⬡' : '✏️'
      const badge = hasPolygon ? '' : '<div class="cage-badge">Vẽ</div>'
      const borderStyle = hasPolygon ? 'dashed' : 'solid'

      return `
        <div class="cage-palette-card" style="
          border-color: ${cage.color || '#4CAF50'};
          border-style: ${borderStyle};
          background: ${cage.color ? cage.color + '08' : 'rgba(76, 175, 80, 0.03)'};
          position: relative;
        ">
          ${badge}
          <div class="cage-title" style="color: ${cage.color || '#4CAF50'};">
            ${cage.name}
          </div>
          <div class="cage-icon" style="color: ${cage.color || '#4CAF50'};">${icon}</div>
          <div class="cage-desc">${cage.type || 'Lồng quây'}</div>
        </div>
      `
    },

    // ========== AISLE NODE CREATION ==========

    // Tạo aisle node cho palette
    createAislePaletteNode(aisle) {
      return {
        id: `aisle_palette_${aisle.id}`,
        width: 80,
        height: 100,
        shape: {
          type: 'HTML',
          content: this.createAislePaletteHtml(aisle)
        },
        constraints: NodeConstraints.Default & ~NodeConstraints.Delete,
        addInfo: {
          nodeType: 'aisle',
          aisleId: aisle.id,
          aisleName: aisle.name,
          aisleType: aisle.type,
          color: aisle.color || '#2196F3',
          polygon: aisle.polygon
        }
      }
    },

    // Tạo aisle node cho diagram
    createAisleNode(aisle) {
      // Tính bounds của polygon
      const xs = aisle.polygon.map((p) => p[0])
      const ys = aisle.polygon.map((p) => p[1])
      const minX = Math.min(...xs)
      const maxX = Math.max(...xs)
      const minY = Math.min(...ys)
      const maxY = Math.max(...ys)
      const width = maxX - minX
      const height = maxY - minY

      // Xác định góc xoay dựa trên tỷ lệ width/height
      // Nếu chiều cao > chiều rộng thì xoay dọc (90 độ)
      const isVertical = height > width
      const rotateAngle = isVertical ? 90 : 0

      // Tạo path data từ polygon (tọa độ tương đối từ góc trên trái)
      const pathData =
        'M ' +
        aisle.polygon
          .map((point, index) => {
            const relX = point[0] - minX
            const relY = point[1] - minY
            return index === 0 ? `${relX} ${relY}` : `L ${relX} ${relY}`
          })
          .join(' ') +
        ' Z'

      return {
        id: `aisle_${aisle.id}`,
        width: width,
        height: height,
        offsetX: minX + width / 2,
        offsetY: minY + height / 2,
        shape: {
          type: 'Path',
          data: pathData
        },
        style: {
          fill: aisle.color ? aisle.color + '10' : '#2196F310',
          strokeColor: aisle.color ? aisle.color + '60' : '#2196F360',
          strokeWidth: 2
        },
        // Thêm annotation để hiển thị tên và icon ở chính giữa
        annotations: [
          {
            content: `${this.getAisleIcon(aisle.type)} ${aisle.name}`,
            style: {
              fontSize: 12,
              bold: true,
              color: aisle.color || '#2196F3',
              fill: 'white',
              strokeColor: aisle.color || '#2196F3',
              strokeWidth: 0.5
            },
            offset: { x: 0.5, y: 0.5 }, // Chính giữa node
            horizontalAlignment: 'Center',
            verticalAlignment: 'Center',
            rotateAngle: rotateAngle, // Xoay 90 độ nếu dọc
            margin: { left: 0, top: 0 }
          }
        ],
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate,
        zIndex: 3, // z-index thấp hơn cage (5) và rack (10)
        addInfo: {
          nodeType: 'aisle',
          isAisle: true,
          aisleId: aisle.id,
          aisleName: aisle.name,
          aisleType: aisle.type,
          polygon: aisle.polygon,
          color: aisle.color || '#2196F3'
        }
      }
    },

    // HTML template cho aisle palette
    createAislePaletteHtml(aisle) {
      const hasPolygon = aisle.polygon && aisle.polygon.length >= 3
      const icon = hasPolygon ? this.getAisleIcon(aisle.type) : '✏️'
      const badge = hasPolygon ? '' : '<div class="aisle-badge">Vẽ</div>'
      const borderStyle = hasPolygon ? 'dashed' : 'solid'
      const typeLabel = this.getAisleTypeLabel(aisle.type)

      return `
        <div class="aisle-palette-card" style="
          border-color: ${aisle.color || '#2196F3'};
          border-style: ${borderStyle};
          background: ${aisle.color ? aisle.color + '08' : 'rgba(33, 150, 243, 0.03)'};
          position: relative;
        ">
          ${badge}
          <div class="aisle-title" style="color: ${aisle.color || '#2196F3'};">
            ${aisle.name}
          </div>
          <div class="aisle-icon" style="color: ${aisle.color || '#2196F3'};">${icon}</div>
          <div class="aisle-desc">${typeLabel}</div>
        </div>
      `
    },

    // Lấy label tiếng Việt cho loại hành lang
    getAisleTypeLabel(type) {
      const labels = {
        hot: 'Hành lang nóng',
        cold: 'Hành lang lạnh'
      }
      return labels[type] || type
    },

    // Lấy icon cho từng loại hành lang
    getAisleIcon(type) {
      const icons = {
        hot: '🔥',
        cold: '❄️',
        'Hành lang nóng': '🔥',
        'Hành lang lạnh': '❄️'
      }
      return icons[type] || '🚶'
    },

    // Tạo rack node cho diagram
    createRackNode(rack) {
      const width = rack.width || 60
      const height = rack.height || 60

      return {
        id: rack.id,
        width: width,
        height: height,
        shape: {
          type: 'HTML',
          content: this.createRackNodeHtml(rack)
        },
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate,
        zIndex: 10,
        addInfo: {
          isRack: true, // QUAN TRỌNG: Đánh dấu là rack để constraint
          rackId: rack.id,
          rackName: rack.name,
          rackType: rack.type,
          totalU: rack.totalU,
          usedU: rack.usedU || 0,
          width: width,
          height: height,
          direction: rack.direction,
          color: rack.color || '#2196F3' // Lưu màu vào addInfo
        }
      }
    },

    // ========== EQUIPMENT NODE CREATION ==========

    // Tạo equipment node cho palette
    createEquipmentPaletteNode(equipment) {
      return {
        id: equipment.id,
        width: 140,
        height: 120,
        shape: {
          type: 'HTML',
          content: this.createEquipmentPaletteHtml(equipment)
        },
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate,
        addInfo: {
          nodeType: 'equipment',
          equipmentId: equipment.id,
          equipmentName: equipment.name,
          equipmentType: equipment.type,
          width: equipment.width || 50,
          height: equipment.height || 50
        }
      }
    },

    // Tạo fan node cho diagram (view-only - không cho thao tác)
    createFanNode(fan) {
      return {
        id: `fan_${fan.id}`,
        width: fan.width || 60,
        height: fan.height || 60,
        pivot: { x: 0, y: 0 },
        shape: {
          type: 'HTML',
          content: this.createFanDiagramHtml(fan)
        },
        constraints: NodeConstraints.None, // Không cho select, drag, resize - chỉ xem
        zIndex: 3, // z-index thấp - thành phần phụ
        addInfo: {
          nodeType: 'fan',
          isFan: true,
          isReadOnly: true, // Đánh dấu read-only
          fanId: fan.id,
          fanName: fan.name,
          fanTenTat: fan.tenTat,
          ghiChu: fan.ghiChu,
          moTa: fan.moTa
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
                  opacity="0.7" />

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
            content: `🌬️ ${column.name}`, // Icon gió + tên
            style: {
              fontSize: 11,
              bold: true,
              color: '#424242',
              fill: 'rgba(255, 255, 255, 0.85)',
              strokeColor: '#757575',
              strokeWidth: 0.5
            },
            offset: { x: 0.5, y: 0.5 }, // Giữa tấm sàn
            horizontalAlignment: 'Center',
            verticalAlignment: 'Center',
            margin: { left: 0, top: 0 }
          }
        ],
        constraints: NodeConstraints.None, // Không cho select, drag, resize - chỉ xem
        zIndex: 2, // z-index thấp hơn fan - thành phần phụ
        addInfo: {
          nodeType: 'column',
          isColumn: true,
          isReadOnly: true, // Đánh dấu read-only
          isContainer: false, // Không phải container vì read-only
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
          background: #2196F310;
          box-sizing: border-box;
          opacity: 0.7;
          pointer-events: none;
        ">
          <svg width="100%" height="100%" style="position: absolute; top: 0; left: 0; pointer-events: none;">
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

    // Tạo equipment node cho diagram
    createEquipmentNode(equipment) {
      const width = equipment.width || 50
      const height = equipment.height || 50

      return {
        id: equipment.id,
        width: width,
        height: height,
        shape: {
          type: 'HTML',
          content: this.createEquipmentNodeHtml(equipment)
        },
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate,
        zIndex: 10,
        addInfo: {
          isEquipment: true,
          equipmentId: equipment.id,
          equipmentName: equipment.name,
          equipmentType: equipment.type,
          width: width,
          height: height
        }
      }
    },

    // HTML template cho equipment palette
    createEquipmentPaletteHtml(equipment) {
      const icon = this.getEquipmentIcon(equipment.type)
      return `
        <div class="equipment-palette-card">
          <div class="equipment-title">${equipment.name}</div>
          <div class="equipment-icon">${icon}</div>
          <div class="equipment-desc">
            ${this.getEquipmentTypeLabel(equipment.type)}
          </div>
        </div>
      `
    },

    getEquipmentTypeLabel(type) {
      const labels = {
        MAYCHU: 'Máy chủ',
        TUDIA: 'Tủ đĩa',
        MANG: 'Thiết bị mạng',
        DIEN: 'Thiết bị điện',
        IDC: 'Thiết bị IDC',
        KHAC: 'Thiết bị khác'
      }
      return labels[type] || type
    },

    // HTML template cho equipment diagram node
    createEquipmentNodeHtml(equipment) {
      const icon = this.getEquipmentIcon(equipment.type)
      return `
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
            text-overflow: ellipsis;
            white-space: nowrap;
          ">${equipment.name}</div>
          <div style="font-size: 22px; text-align: center; flex: 1; display: flex; align-items: center; justify-content: center;">${icon}</div>
          <div style="font-size: 8px; text-align: center;">${this.getEquipmentTypeLabel(equipment.type)}</div>
        </div>
      `
    },

    // Lấy icon cho từng loại thiết bị
    getEquipmentIcon(type) {
      const icons = {
        MAYCHU: '🖥️',
        TUDIA: '🗄️',
        MANG: '🔀',
        DIEN: '🔌',
        IDC: '🏢',
        KHAC: '📦'
      }
      return icons[type] || '📦'
    },

    // HTML template cho palette
    createRackPaletteHtml(rack) {
      const rackColor = rack.color || '#2196F3'
      return `
        <div class="rack-palette-card" style="border-color: ${rackColor};">
          <div class="rack-title" style="color: ${rackColor};">${rack.name}</div>
          <img src="/static/icons/thiet-bi/rack.png" class="rack-icon" style="filter: drop-shadow(0 2px 4px ${rackColor}40);" />
          <div class="rack-desc" style="color: ${rackColor};">${rack.totalU}U</div>
        </div>
      `
    },

    // HTML template cho diagram node
    createRackNodeHtml(rack) {
      // Tính số rack chồng lên tại vị trí này
      const posKey = `${Math.round(rack.x)},${Math.round(rack.y)}`
      const stackedRacks = this.stackedRacksMap[posKey] || [rack]
      const stackCount = stackedRacks.length

      console.log('🎨 createRackNodeHtml:', {
        rackName: rack.rackName || rack.equipmentName,
        position: posKey,
        stackCount: stackCount,
        stackedRacks: stackedRacks.map((r) => r.rackName || r.equipmentName)
      })

      // Tạo stacked layers effect (hiệu ứng 3 lớp chồng lên phía sau)
      const stackedLayers =
        stackCount > 1
          ? `

      `
          : ''

      // Lấy màu của rack (ưu tiên màu từ data)
      const rackColor = rack.color || '#2196F3'

      // Badge hiển thị số lượng rack chồng lên (nếu > 1)
      const stackBadge =
        stackCount > 1
          ? `
        <div style="
          position: absolute;
          top: -10px;
          right: -10px;
          background: red;
          color: white;
          border-radius: 50%;
          width: 20px;
          height: 20px;
          display: flex;
          align-items: center;
          justify-content: center;
          font-size: 12px;
          font-weight: bold;
          box-shadow: 0 3px 8px rgba(255, 87, 34, 0.5);
          z-index: 1000;
        ">${stackCount}</div>
      `
          : ''

      // Danh sách rack chồng lên (hiển thị bên cạnh khi hover)
      const rackListHtml =
        stackCount > 1
          ? `
        <div class="stacked-rack-list" style="
          position: absolute;
          left: 105%;
          top: 0;
          background: white;
          border: 2px solid #2196F3;
          border-radius: 6px;
          padding: 8px;
          min-width: 180px;
          z-index: 9999;
          display: none;
        ">
          <div style="
            font-size: 11px;
            font-weight: bold;
            color: #2196F3;
            margin-bottom: 6px;
            border-bottom: 1px solid #FFE0B2;
            padding-bottom: 4px;
          ">📦 ${stackCount} Rack chồng lên:</div>
          ${stackedRacks
            .map((r, idx) => {
              const level = r.zLevel || 0
              const levelText = level > 0 ? ` [T${level}]` : ''
              return `
              <div style="
                font-size: 10px;
                padding: 4px 0;
                border-bottom: 1px dashed #eee;
                color: #333;
              ">
                <strong style="color: #2196F3;">${idx + 1}.</strong>
                ${r.name}${levelText}
                <div style="color: #666; font-size: 9px; margin-top: 2px;">
                  ${r.totalU}U • ${r.type}
                </div>
              </div>
            `
            })
            .join('')}
          <div style="
            font-size: 9px;
            color: #999;
            margin-top: 6px;
            text-align: center;
          ">Click để xem chi tiết</div>
        </div>
      `
          : ''

      // Tạo tooltip text với danh sách rack
      const tooltipText =
        stackCount > 1
          ? `${stackCount} rack chồng lên: ${stackedRacks.map((r, idx) => `${idx + 1}. ${r.name}`).join(', ')}`
          : `${rack.name} (${rack.totalU}U)`

      return `
        <div class="rack-node-wrapper" style="
          width: 100%;
          height: 100%;
          position: relative;
        "
        onmouseenter="this.querySelector('.stacked-rack-list')?.style?.setProperty('display', 'block')"
        onmouseleave="this.querySelector('.stacked-rack-list')?.style?.setProperty('display', 'none')">
          ${stackedLayers}
          <div style="
            width: 100%;
            height: 100%;
            display: flex;
            flex-direction: column;
            background: #fff;
            border: 2px solid ${rackColor};
            border-radius: 3px;
            padding: 3px;
            box-sizing: border-box;
            position: relative;
            transition: all 0.3s ease;
          " title="${tooltipText}">
            ${stackBadge}
            <div style="
              font-size: 9px;
              font-weight: bold;
              text-align: center;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
              color: ${rackColor};
            ">${rack.name}</div>
            <img src="/static/icons/thiet-bi/rack.png" style="
              width: 100%;
              height: 70%;
              object-fit: contain;
              pointer-events: none;
              opacity: ${stackCount > 1 ? '0.95' : '1'};
              filter: ${stackCount > 1 ? `drop-shadow(0 2px 4px ${rackColor}40)` : 'none'};
            " />
            <div style="
              font-size: 8px;
              text-align: center;
              color: ${stackCount > 1 ? rackColor : '#666'};
              font-weight: ${stackCount > 1 ? 'bold' : 'normal'};
            ">${rack.totalU}U</div>
          </div>
          ${rackListHtml}
        </div>
      `
    },

    // ========== EVENT HANDLERS ==========

    getNodeDefaults(node) {
      return {
        width: 80,
        height: 100,
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate
      }
    },

    getDiagramNodeDefaults(node) {
      const width = (node.addInfo && node.addInfo.width) || 60
      const height = (node.addInfo && node.addInfo.height) || 60

      return {
        width: width,
        height: height,
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate
      }
    },

    getSymbolInfo(symbol) {
      return {
        fit: true,
        description: {
          text: symbol.addInfo ? symbol.addInfo.rackName : symbol.id,
          overflow: 'Wrap'
        }
      }
    },

    // Xử lý khi drop node từ palette
    onDrop(args) {
      if (!args.element || !args.element.addInfo) return

      const nodeType = args.element.addInfo.nodeType

      // Xử lý drop CAGE
      if (nodeType === 'cage') {
        this.handleCageDrop(args)
      }
      // Xử lý drop AISLE
      else if (nodeType === 'aisle') {
        this.handleAisleDrop(args)
      }
      // Xử lý drop RACK
      else if (nodeType === 'rack') {
        this.handleRackDrop(args)
      }
      // Xử lý drop EQUIPMENT
      else if (nodeType === 'equipment') {
        this.handleEquipmentDrop(args)
      }
    },

    // Xử lý drop rack
    handleRackDrop(args) {
      const rackId = args.element.addInfo.rackId
      const rack = this.uninstalledRacks.find((r) => r.id === rackId)

      if (!rack) return

      // Cập nhật HTML content cho diagram
      args.element.shape.content = this.createRackNodeHtml(rack)
      args.element.width = rack.width || 60
      args.element.height = rack.height || 60

      // Set pivot ở góc trên bên trái
      args.element.pivot = { x: 0, y: 0 }

      // Mark là rack để constraint
      args.element.addInfo.isRack = true
      args.element.addInfo.direction = rack.direction

      // Constraint vị trí trong polygon
      this.constrainNodePosition(args.element)

      // Remove from palette
      const index = this.uninstalledRacks.findIndex((r) => r.id === rackId)
      if (index !== -1) {
        this.uninstalledRacks.splice(index, 1)
      }
    },

    // Xử lý drop equipment
    handleEquipmentDrop(args) {
      const equipmentId = args.element.addInfo.equipmentId
      const equipment = this.uninstalledEquipments.find((e) => e.id === equipmentId)

      if (!equipment) return

      // Cập nhật HTML content cho diagram
      args.element.shape.content = this.createEquipmentNodeHtml(equipment)
      args.element.width = equipment.width || 60
      args.element.height = equipment.height || 60

      // Set pivot ở góc trên bên trái
      args.element.pivot = { x: 0, y: 0 }

      // Mark là equipment để constraint
      args.element.addInfo.isEquipment = true

      // Constraint vị trí trong polygon
      this.constrainNodePosition(args.element)

      // Remove from palette
      const index = this.uninstalledEquipments.findIndex((e) => e.id === equipmentId)
      if (index !== -1) {
        this.uninstalledEquipments.splice(index, 1)
      }
    },

    // Xử lý drop cage
    handleCageDrop(args) {
      const cageId = args.element.addInfo.cageId
      const cage = this.uninstalledCages.find((c) => c.id === cageId)

      if (!cage) return

      // Kiểm tra cage có polygon chưa
      if (!cage.polygon || cage.polygon.length < 3) {
        // Cancel drop event
        args.cancel = true

        // Lưu cage data và vị trí drop để xử lý sau
        this._pendingCageData = {
          cage: cage,
          dropPosition: {
            x: args.element.offsetX,
            y: args.element.offsetY
          }
        }

        // Mở modal vẽ polygon
        this.$nextTick(() => {
          const diagram = this.$refs.diagramObj.ej2Instances

          const bounds = this.getPolygonBounds()
          this.$refs.drawCagePolygonModal.show({
            id: cage.id,
            name: cage.name,
            type: cage.type,
            color: cage.color || '#4CAF50'
          })
        })

        return
      }

      // Cage đã có polygon -> Tạo node bình thường
      const cageNode = this.createCageNode(cage)

      // Copy properties từ cageNode sang args.element
      args.element.shape = cageNode.shape
      args.element.style = cageNode.style
      args.element.zIndex = cageNode.zIndex
      args.element.annotations = cageNode.annotations

      // Tính bounds của cage polygon
      const xs = cage.polygon.map((p) => p[0])
      const ys = cage.polygon.map((p) => p[1])
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

      // Mark là cage để constraint
      args.element.addInfo.isCage = true
      args.element.addInfo.cageName = cage.name
      args.element.addInfo.cageType = cage.type
      args.element.addInfo.polygon = cage.polygon
      args.element.addInfo.color = cage.color

      // Constraint vị trí trong polygon phòng
      this.constrainNodePosition(args.element)

      // Remove from palette
      const index = this.uninstalledCages.findIndex((c) => c.id === cageId)
      if (index !== -1) {
        this.uninstalledCages.splice(index, 1)
      }
    },

    // Xử lý drop aisle
    handleAisleDrop(args) {
      const aisleId = args.element.addInfo.aisleId
      const aisle = this.uninstalledAisles.find((a) => a.id === aisleId)

      if (!aisle) return

      // Kiểm tra aisle có polygon chưa
      if (!aisle.polygon || aisle.polygon.length < 3) {
        // FIX BUG 2: Cancel drop event (giống cage)
        args.cancel = true

        // Chưa có polygon -> Mở modal vẽ polygon
        this.$refs.drawAislePolygonModal.show({
          mode: 'create',
          aisle: {
            id: aisle.id,
            name: aisle.name,
            type: aisle.type,
            color: aisle.color || '#2196F3'
          }
        })

        return
      }

      // Aisle đã có polygon -> Tạo node bình thường
      const aisleNode = this.createAisleNode(aisle)

      // Copy properties từ aisleNode sang args.element
      args.element.shape = aisleNode.shape
      args.element.style = aisleNode.style
      args.element.zIndex = aisleNode.zIndex
      args.element.annotations = aisleNode.annotations

      // Tính bounds của aisle polygon
      const xs = aisle.polygon.map((p) => p[0])
      const ys = aisle.polygon.map((p) => p[1])
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

      // Mark là aisle để constraint
      args.element.addInfo.isAisle = true
      args.element.addInfo.aisleName = aisle.name
      args.element.addInfo.aisleType = aisle.type
      args.element.addInfo.polygon = aisle.polygon
      args.element.addInfo.color = aisle.color

      // Constraint vị trí trong polygon phòng
      this.constrainNodePosition(args.element)

      // Remove from palette
      const index = this.uninstalledAisles.findIndex((a) => a.id === aisleId)
      if (index !== -1) {
        this.uninstalledAisles.splice(index, 1)
      }
    },

    // Constraint vị trí node trong polygon
    constrainNodePosition(element) {
      const bounds = this.getPolygonBounds()
      const width = element.width
      const height = element.height

      // Với pivot (0,0), offsetX/Y là góc trên trái
      const minX = bounds.minX
      const minY = bounds.minY
      const maxX = bounds.maxX - width
      const maxY = bounds.maxY - height

      const oldX = element.offsetX
      const oldY = element.offsetY

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
        element.offsetX = bounds.minX
        element.offsetY = bounds.minY
      }
    },

    // Xử lý khi di chuyển node
    onPositionChange(args) {
      if (!args.newValue || !args.oldValue) return

      const node = args.source?.addInfo ? args.source : args.source.properties.nodes[0]
      const diagram = this.$refs.diagramObj.ej2Instances

      // Bỏ qua boundary node, mask, labels, và grid nodes
      if (
        node.id === this.roomContainerId + '_boundary' ||
        node.id === this.roomContainerId + '_mask' ||
        (node.id && node.id.startsWith('edgeLabel_')) ||
        (node.id && node.id.startsWith('grid_')) ||
        (node.addInfo && (node.addInfo.isGridCell || node.addInfo.isGridLabel || node.addInfo.isGridHeader))
      ) {
        return
      }

      // if (args.newValue.offsetX == args.oldValue.offsetX && args.newValue.offsetY == args.oldValue.offsetY) return

      const bounds = this.getPolygonBounds()
      let newIsInside = true
      let newIsOutOfBounds = false

      // Xử lý riêng cho cage (kiểm tra tất cả điểm trong polygon của cage)
      if (node.addInfo && node.addInfo.isCage) {
        const cagePolygon = node.addInfo.polygon

        // Tính vị trí mới của cage
        const xs = cagePolygon.map((p) => p[0])
        const ys = cagePolygon.map((p) => p[1])
        const cageMinX = Math.min(...xs)
        const cageMinY = Math.min(...ys)
        const cageMaxX = Math.max(...xs)
        const cageMaxY = Math.max(...ys)

        const deltaX = args.newValue.offsetX - cageMinX
        const deltaY = args.newValue.offsetY - cageMinY

        // Thêm tolerance để cho phép kéo sát cạnh (giống rack và equipment)
        const tolerance = 0.1

        // Kiểm tra tất cả các điểm trong polygon của cage có nằm trong room không
        for (const point of cagePolygon) {
          const absoluteX = point[0] + deltaX
          const absoluteY = point[1] + deltaY

          // Tính khoảng cách đến biên để áp dụng tolerance
          const distanceToMinX = Math.abs(absoluteX - bounds.minX)
          const distanceToMaxX = Math.abs(absoluteX - bounds.maxX)
          const distanceToMinY = Math.abs(absoluteY - bounds.minY)
          const distanceToMaxY = Math.abs(absoluteY - bounds.maxY)

          // Nếu điểm gần cạnh biên (< tolerance), điều chỉnh để kiểm tra
          let checkX = absoluteX
          let checkY = absoluteY

          if (distanceToMinX < tolerance) checkX = absoluteX + tolerance
          if (distanceToMaxX < tolerance) checkX = absoluteX - tolerance
          if (distanceToMinY < tolerance) checkY = absoluteY + tolerance
          if (distanceToMaxY < tolerance) checkY = absoluteY - tolerance

          if (!this.isPointInPolygon(checkX, checkY)) {
            newIsInside = false
            break
          }
        }

        const newMinX = cageMinX + deltaX
        const newMaxX = cageMaxX + deltaX
        const newMinY = cageMinY + deltaY
        const newMaxY = cageMaxY + deltaY

        newIsOutOfBounds =
          newMinX < bounds.minX || newMaxX > bounds.maxX || newMinY < bounds.minY || newMaxY > bounds.maxY

        // KIỂM TRA CAGE CHỒNG LÊN CAGE KHÁC - CHỈ HIỂN THỊ THÔNG BÁO
        if (newIsInside && !newIsOutOfBounds) {
          // Tạo polygon mới với vị trí mới
          const newCagePolygon = cagePolygon.map((p) => [p[0] + deltaX, p[1] + deltaY])

          // Tạo polygon cũ với vị trí cũ
          const oldDeltaX = args.oldValue.offsetX - cageMinX
          const oldDeltaY = args.oldValue.offsetY - cageMinY
          const oldCagePolygon = cagePolygon.map((p) => [p[0] + oldDeltaX, p[1] + oldDeltaY])

          // Kiểm tra overlap với các cage khác
          const otherCages = diagram.nodes.filter((n) => n.addInfo && n.addInfo.isCage && n.id !== node.id)

          const overlappingCages = []

          for (const otherCage of otherCages) {
            const hasNewOverlap = this.isCageOverlapping(newCagePolygon, otherCage)
            const hadOldOverlap = this.isCageOverlapping(oldCagePolygon, otherCage)

            // Nếu có overlap mới (không phải overlap cũ)
            if (hasNewOverlap && !hadOldOverlap) {
              overlappingCages.push(otherCage.addInfo.cageName)
            }
          }

          // CHỈ HIỂN THỊ THÔNG BÁO, KHÔNG CHẶN
          if (overlappingCages.length > 0) {
            const cageList = overlappingCages.join(', ')
            this.$toast.warning(`Lồng quây "${node.addInfo.cageName}" đang chồng lên: ${cageList}`, {
              duration: 3000
            })
          }

          // Nếu đang kéo RA khỏi overlap
          const wasOverlapping = otherCages.some((otherCage) => this.isCageOverlapping(oldCagePolygon, otherCage))
          const isNowOverlapping = otherCages.some((otherCage) => this.isCageOverlapping(newCagePolygon, otherCage))

          if (wasOverlapping && !isNowOverlapping) {
            this.$toast.success(`Lồng quây "${node.addInfo.cageName}" đã tách khỏi vị trí chồng lấn`, {
              duration: 2000
            })
          }
        }
      } else {
        // Xử lý cho rack và equipment (hình chữ nhật)
        const width = node.width || 60
        const height = node.height || 60

        // Thêm tolerance để cho phép kéo sát cạnh
        const tolerance = 0.1

        // Với pivot (0,0), offsetX/Y là góc trên trái
        // Kiểm tra cả 4 góc của node có nằm trong polygon không (VỊ TRÍ MỚI)
        const topLeft = this.isPointInPolygon(args.newValue.offsetX + tolerance, args.newValue.offsetY + tolerance)
        const topRight = this.isPointInPolygon(args.newValue.offsetX + width - tolerance, args.newValue.offsetY + tolerance)
        const bottomLeft = this.isPointInPolygon(args.newValue.offsetX + tolerance, args.newValue.offsetY + height - tolerance)
        const bottomRight = this.isPointInPolygon(args.newValue.offsetX + width - tolerance, args.newValue.offsetY + height - tolerance)
        newIsInside = topLeft && topRight && bottomLeft && bottomRight

        // Sửa logic bounds để cho phép kéo sát cạnh
        newIsOutOfBounds =
          args.newValue.offsetX < bounds.minX ||
          args.newValue.offsetX + width > bounds.maxX ||
          args.newValue.offsetY < bounds.minY ||
          args.newValue.offsetY + height > bounds.maxY
      }

      // LOGIC MỚI: Cho phép kéo VÀO lại trong biên
      // Kiểm tra vị trí CŨ có nằm trong không
      let oldIsInside = true
      let oldIsOutOfBounds = false

      if (node.addInfo && node.addInfo.isCage) {
        // Kiểm tra vị trí cũ của cage
        const cagePolygon = node.addInfo.polygon
        const xs = cagePolygon.map((p) => p[0])
        const ys = cagePolygon.map((p) => p[1])
        const cageMinX = Math.min(...xs)
        const cageMinY = Math.min(...ys)
        const cageMaxX = Math.max(...xs)
        const cageMaxY = Math.max(...ys)

        const oldDeltaX = args.oldValue.offsetX - cageMinX
        const oldDeltaY = args.oldValue.offsetY - cageMinY

        // Thêm tolerance để cho phép kéo sát cạnh (giống rack và equipment)
        const tolerance = 0.1

        for (const point of cagePolygon) {
          const absoluteX = point[0] + oldDeltaX
          const absoluteY = point[1] + oldDeltaY

          // Tính khoảng cách đến biên để áp dụng tolerance
          const distanceToMinX = Math.abs(absoluteX - bounds.minX)
          const distanceToMaxX = Math.abs(absoluteX - bounds.maxX)
          const distanceToMinY = Math.abs(absoluteY - bounds.minY)
          const distanceToMaxY = Math.abs(absoluteY - bounds.maxY)

          // Nếu điểm gần cạnh biên (< tolerance), điều chỉnh để kiểm tra
          let checkX = absoluteX
          let checkY = absoluteY

          if (distanceToMinX < tolerance) checkX = absoluteX + tolerance
          if (distanceToMaxX < tolerance) checkX = absoluteX - tolerance
          if (distanceToMinY < tolerance) checkY = absoluteY + tolerance
          if (distanceToMaxY < tolerance) checkY = absoluteY - tolerance

          if (!this.isPointInPolygon(checkX, checkY)) {
            oldIsInside = false
            break
          }
        }

        const oldMinX = cageMinX + oldDeltaX
        const oldMaxX = cageMaxX + oldDeltaX
        const oldMinY = cageMinY + oldDeltaY
        const oldMaxY = cageMaxY + oldDeltaY

        oldIsOutOfBounds =
          oldMinX < bounds.minX || oldMaxX > bounds.maxX || oldMinY < bounds.minY || oldMaxY > bounds.maxY
      } else {
        // Kiểm tra vị trí cũ của rack/equipment
        const width = node.width || 60
        const height = node.height || 60

        // Thêm tolerance để cho phép kéo sát cạnh
        const tolerance = 0.1

        const oldTopLeft = this.isPointInPolygon(args.oldValue.offsetX + tolerance, args.oldValue.offsetY + tolerance)
        const oldTopRight = this.isPointInPolygon(args.oldValue.offsetX + width - tolerance, args.oldValue.offsetY + tolerance)
        const oldBottomLeft = this.isPointInPolygon(args.oldValue.offsetX + tolerance, args.oldValue.offsetY + height - tolerance)
        const oldBottomRight = this.isPointInPolygon(args.oldValue.offsetX + width - tolerance, args.oldValue.offsetY + height - tolerance)
        oldIsInside = oldTopLeft && oldTopRight && oldBottomLeft && oldBottomRight

        // Sửa logic bounds để cho phép kéo sát cạnh
        oldIsOutOfBounds =
          args.oldValue.offsetX < bounds.minX ||
          args.oldValue.offsetX + width > bounds.maxX ||
          args.oldValue.offsetY < bounds.minY ||
          args.oldValue.offsetY + height > bounds.maxY
      }

      // CHỈ CHẶN NẾU:
      // - Vị trí CŨ đã nằm TRONG biên
      // - VÀ vị trí MỚI ra NGOÀI biên
      // CHO PHÉP:
      // - Vị trí CŨ đã NGOÀI biên -> có thể kéo VÀO hoặc di chuyển trong vùng ngoài
      const wasInside = oldIsInside && !oldIsOutOfBounds
      const isNowOutside = !newIsInside || newIsOutOfBounds

      if (wasInside && isNowOutside) {
        // Đang cố kéo RA NGOÀI từ vị trí hợp lệ -> CHẶN
        args.cancel = true
        this.$nextTick(() => {
          const diagramNode = diagram.getObject(node.id)
          if (diagramNode) {
            diagramNode.offsetX = args.oldValue.offsetX
            diagramNode.offsetY = args.oldValue.offsetY
            diagram.dataBind()
          }
        })
        return
      }

      // Log nếu đang kéo VÀO lại trong biên
      if (!wasInside && !isNowOutside) {
        console.log('Đối tượng đã được kéo VÀO lại trong biên')
      }

      // XỬ LÝ KÉO CAGE - Di chuyển tất cả children theo
      if (node.addInfo && node.addInfo.isCage && node.addInfo.children && node.addInfo.children.length > 0) {
        const deltaX = args.newValue.offsetX - args.oldValue.offsetX
        const deltaY = args.newValue.offsetY - args.oldValue.offsetY

        // Di chuyển tất cả children (chỉ những child còn thuộc về cage)
        node.addInfo.children.forEach((child) => {
          const childNode = diagram.getObject(child.id)
          if (childNode) {
            // Kiểm tra child có còn thuộc về cage này không
            if (childNode.addInfo && childNode.addInfo.parentCageId === node.id) {
              childNode.offsetX += deltaX
              childNode.offsetY += deltaY
            } else {
            }
          }
        })

        // Update diagram
        diagram.dataBind()
      }

      // Constraint rack/equipment trong cage polygon (nếu trong group)
      if (node.addInfo && (node.addInfo.isRack || node.addInfo.isEquipment) && node.addInfo.parentCageId) {
        const parentNode = diagram.getObject(node.addInfo.parentCageId)

        // Tạo object tạm với vị trí mới để kiểm tra
        const tempNode = {
          offsetX: args.newValue.offsetX,
          offsetY: args.newValue.offsetY,
          width: node.width || 60,
          height: node.height || 60
        }
        if (parentNode && !this.isRackInsideCage(tempNode, parentNode)) {
          args.cancel = true
          this.$nextTick(() => {
            const diagramNode = diagram.getObject(node.id)
            if (diagramNode) {
              diagramNode.offsetX = args.oldValue.offsetX
              diagramNode.offsetY = args.oldValue.offsetY
              diagram.dataBind()
            }
          })
          return
        }
      }

      // XỬ LÝ SAU KHI THAY ĐỔI VỊ TRÍ RACK/EQUIPMENT
      // Dùng debounce để chỉ xử lý lần cuối cùng (khi thả xuống)
      if (!args.cancel && node.addInfo && (node.addInfo.isRack || node.addInfo.isEquipment)) {
        const deltaX = Math.abs(args.newValue.offsetX - args.oldValue.offsetX)
        const deltaY = Math.abs(args.newValue.offsetY - args.oldValue.offsetY)

        // Lưu vị trí CŨ lần đầu tiên khi bắt đầu kéo (nếu chưa lưu)
        if (!this.dragOldPosition && (deltaX > 5 || deltaY > 5)) {
          this.dragOldPosition = {
            x: args.oldValue.offsetX,
            y: args.oldValue.offsetY
          }
          console.log('📍 Saved old position:', this.dragOldPosition)
        }

        // Chỉ xử lý nếu di chuyển > 5px (tránh xử lý khi chỉ click)
        if (deltaX > 5 || deltaY > 5) {
          // Kiểm tra overlap với quạt/cột ngay khi đang kéo - visual feedback
          const overlappingFansAndColumns = this.checkOverlapWithFansAndColumns(node)
          if (overlappingFansAndColumns.length > 0) {
            // Đổi màu node sang đỏ để cảnh báo
            node.style = {
              ...node.style,
              strokeColor: '#F44336',
              strokeWidth: 3,
              strokeDashArray: '8,4',
              opacity: 0.8
            }
            diagram.dataBind()
          } else {
            // Reset về màu bình thường khi không overlap
            node.style = {
              ...node.style,
              strokeColor: '#2196F3',
              strokeWidth: 1.5,
              strokeDashArray: '',
              opacity: 1
            }
            diagram.dataBind()
          }

          // Clear timeout cũ nếu có (debounce)
          if (this.positionChangeTimeout) {
            clearTimeout(this.positionChangeTimeout)
          }

          // Set timeout mới - chỉ chạy khi NGỪNG kéo (300ms không có event mới)
          this.positionChangeTimeout = setTimeout(() => {
            console.log('Debounce finished - calling onDragEnd')
            this.onDragEnd({ element: node })
            this.positionChangeTimeout = null
          }, 300)
        }
      }
    },

    // Hiển thị indicator cho các rack bị overlap
    showOverlapIndicators(diagram, overlappingRacks, targetX, targetY) {
      // Xóa các indicator cũ
      this.clearOverlapIndicators(diagram)

      if (overlappingRacks.length === 0) return

      // Tạo indicator cho mỗi rack bị overlap
      overlappingRacks.forEach((rack, index) => {
        const indicatorId = `overlap_indicator_${index}`

        // Tạo annotation để hiển thị warning icon
        const indicator = {
          id: indicatorId,
          width: 30,
          height: 30,
          offsetX: rack.offsetX - 15,
          offsetY: rack.offsetY - 15,
          shape: {
            type: 'HTML',
            content: `
              <div style="
                width: 30px;
                height: 30px;
                background: #FFA726;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                color: white;
                font-size: 16px;
                font-weight: bold;
                box-shadow: 0 2px 8px rgba(255, 167, 38, 0.5);
                animation: pulse 1s infinite;
              ">⚠</div>
            `
          },
          constraints: 0, // Không cho tương tác
          style: {
            fill: 'transparent',
            strokeColor: 'transparent'
          },
          addInfo: {
            isOverlapIndicator: true
          }
        }

        diagram.add(indicator)
      })
    },

    // Xóa tất cả overlap indicators
    clearOverlapIndicators(diagram) {
      const indicators = diagram.nodes.filter((n) => n.addInfo && n.addInfo.isOverlapIndicator)

      indicators.forEach((indicator) => {
        diagram.remove(indicator)
      })
    },

    // Xử lý khi kết thúc drag (mouse up / drop)
    onDragEnd(args) {
      console.log(this.isDraggingRack, 'hieunph check drag end')

      const diagram = this.$refs.diagramObj.ej2Instances

      // Lấy node từ args hoặc từ draggingRackId
      const node = args.element || (this.draggingRackId ? diagram.getObject(this.draggingRackId) : null)
      console.log('🎯 onDragEnd called for node:', node ? node.id : 'null')

      if (!node) {
        console.log('❌ No node found, exiting')
        this.resetDraggingState()
        return
      }

      // Kiểm tra xem có phải rack/equipment không
      if (!node.addInfo || (!node.addInfo.isRack && !node.addInfo.isEquipment)) {
        console.log('❌ Not a rack/equipment, exiting')
        return
      }

      console.log('🎯 onDragEnd - Rack dropped:', node.id, {
        oldPos: this.dragOldPosition,
        newPos: { x: node.offsetX, y: node.offsetY }
      })

      // Lưu vị trí cũ và mới
      const oldPosKey = this.dragOldPosition
        ? `${Math.round(this.dragOldPosition.x)},${Math.round(this.dragOldPosition.y)}`
        : null
      const newPosKey = `${Math.round(node.offsetX)},${Math.round(node.offsetY)}`

      console.log('🔑 Position keys:', { oldPosKey, newPosKey })

      // ⚠️ QUAN TRỌNG: Build map TRƯỚC để có dữ liệu vị trí cũ
      if (Object.keys(this.stackedRacksMap).length === 0) {
        console.log('🏗️ Building stackedRacksMap for first time...')
        this.buildStackedRacksMap()
      }

      // Lưu danh sách racks tại vị trí CŨ TRƯỚC KHI cập nhật tọa độ
      const racksAtOldPosition =
        oldPosKey && this.stackedRacksMap[oldPosKey] ? [...this.stackedRacksMap[oldPosKey]] : []

      console.log(
        '📍 Racks at OLD position (before update):',
        racksAtOldPosition.map((r) => r.rackName || r.equipmentName)
      )

      // Cập nhật tọa độ trong installedRacks data
      const rackData = this.installedRacks.find((r) => r.id === this.draggingRackId || r.id === node.id)
      if (rackData) {
        console.log('📝 Updating rack data:', rackData.rackName || rackData.equipmentName, {
          oldX: rackData.x,
          oldY: rackData.y,
          newX: node.offsetX,
          newY: node.offsetY
        })

        // ⚠️ Với pivot (0,0), offsetX/Y là góc TRÊN TRÁI của node
        // Cần lưu đúng tọa độ này
        rackData.x = node.offsetX
        rackData.y = node.offsetY

        console.log('Rack data updated:', {
          id: rackData.id,
          name: rackData.rackName || rackData.equipmentName,
          x: rackData.x,
          y: rackData.y,
          nodeOffsetX: node.offsetX,
          nodeOffsetY: node.offsetY,
          nodeWidth: node.width,
          nodeHeight: node.height,
          pivotX: node.pivot?.x || 0,
          pivotY: node.pivot?.y || 0
        })
      }

      // Rebuild stacked racks map (với tọa độ mới)
      this.buildStackedRacksMap()

      // Tìm tất cả rack bị ảnh hưởng (vị trí cũ và vị trí mới)
      const affectedRackIds = new Set()

      // Thêm rack tại vị trí CŨ (từ danh sách đã lưu)
      racksAtOldPosition.forEach((r) => affectedRackIds.add(r.id))

      // Thêm rack tại vị trí MỚI (từ map mới)
      if (this.stackedRacksMap[newPosKey]) {
        console.log(
          '📍 Racks at NEW position (after update):',
          this.stackedRacksMap[newPosKey].map((r) => r.rackName || r.equipmentName)
        )
        this.stackedRacksMap[newPosKey].forEach((r) => affectedRackIds.add(r.id))
      }

      console.log('🔄 Refreshing', affectedRackIds.size, 'racks:', Array.from(affectedRackIds))

      // Refresh display cho TẤT CẢ rack bị ảnh hưởng
      affectedRackIds.forEach((rackId) => {
        this.refreshRackNodeDisplay(rackId)
      })

      // Hiển thị thông báo nếu có overlap với rack khác
      if (this.dragTargetPosition && this.dragTargetPosition.hasOverlap) {
        const overlappingRacks = this.dragTargetPosition.overlappingRacks
        const rackName = node.addInfo.rackName || node.addInfo.equipmentName

        const racksList = overlappingRacks
          .map((r) => {
            const name = r.addInfo.rackName || r.addInfo.equipmentName
            return `• ${name}`
          })
          .join('\n')

        this.$toast.warning(`⚠️ ${rackName} đã chồng lên:\n${racksList}\n\nClick vào rack để xem chi tiết.`, {
          duration: 4000
        })
      }

      // Hiển thị cảnh báo nếu đè lên quạt/cột (chỉ warning, không chặn)
      const overlappingFansAndColumns = this.checkOverlapWithFansAndColumns(node)
      if (overlappingFansAndColumns.length > 0) {
        const nodeName = node.addInfo.rackName || node.addInfo.equipmentName
        const overlappingList = overlappingFansAndColumns
          .map((item) => {
            const type = item.addInfo.isFan ? 'Quạt' : 'Cột'
            const name = item.addInfo.fanName || item.addInfo.columnName
            return `• ${type}: ${name}`
          })
          .join('\n')

        this.$toast.warning(
          `${nodeName} đang đè lên quạt/cột:\n${overlappingList}\nVui lòng điều chỉnh trước khi lưu.`,
          { duration: 5000 }
        )
      }

      // Reset style về bình thường
      const stackedRacks = this.getStackedRacksAtPosition(node.offsetX, node.offsetY)
      const hasStack = stackedRacks.length > 1

      node.style = {
        ...node.style,
        strokeColor: hasStack ? '#FF5722' : '#2196F3',
        strokeWidth: hasStack ? 2 : 1.5,
        strokeDashArray: '',
        opacity: 1
      }
      diagram.dataBind()

      // Reset dragging state
      this.resetDraggingState()
    },

    // Reset dragging state
    resetDraggingState() {
      this.isDraggingRack = false
      this.draggingRackId = null
      this.dragTargetPosition = null
      this.dragOldPosition = null
    },

    // Xử lý khi có thay đổi trong collection (add/remove/move nodes)
    onCollectionChange(args) {
      // Khi có node được move (drag end), rebuild stacked map
      if (args.state === 'Changed' && args.type === 'Position') {
        this.$nextTick(() => {
          this.buildStackedRacksMap()
        })
      }
    },

    // Refresh display của một rack node
    refreshRackNodeDisplay(nodeId) {
      const diagram = this.$refs.diagramObj.ej2Instances
      const node = diagram.getObject(nodeId)

      console.log('🔄 Refreshing node:', nodeId, node ? 'found' : 'not found')

      if (!node || !node.addInfo) {
        console.log('❌ No node or addInfo')
        return
      }

      console.log('🔍 Node details:', {
        nodeId: nodeId,
        addInfo: node.addInfo,
        rackId: node.addInfo.rackId,
        rackName: node.addInfo.rackName,
        equipmentName: node.addInfo.equipmentName
      })

      // Tìm rack data - thử nhiều cách
      console.log('🔍 Searching in installedRacks, total:', this.installedRacks.length)
      console.log(
        '🔍 Sample rack IDs:',
        this.installedRacks.slice(0, 5).map((r) => ({
          id: r.id,
          rackName: r.rackName,
          equipmentName: r.equipmentName
        }))
      )

      let rack = this.installedRacks.find((r) => r.id === nodeId)
      console.log('🔍 Find by nodeId:', rack ? 'FOUND' : 'NOT FOUND')

      // Nếu không tìm thấy bằng id, thử tìm bằng node.addInfo
      if (!rack && node.addInfo) {
        rack = this.installedRacks.find(
          (r) =>
            r.id === node.addInfo.rackId ||
            r.rackName === node.addInfo.rackName ||
            r.equipmentName === node.addInfo.equipmentName
        )
        console.log('🔍 Find by addInfo:', rack ? 'FOUND' : 'NOT FOUND')
      }

      if (!rack) {
        console.log('❌ Rack not found in installedRacks')
        return
      }

      console.log('Found rack object:', rack)
      console.log('Rack properties:', {
        id: rack.id,
        rackName: rack.rackName,
        equipmentName: rack.equipmentName,
        name: rack.name,
        x: rack.x,
        y: rack.y
      })

      console.log('Found rack:', rack.rackName || rack.equipmentName || rack.name, 'updating HTML...')

      // Cập nhật HTML với stacked info mới
      if (node.shape && node.shape.type === 'HTML') {
        const newHtml = this.createRackNodeHtml(rack)
        console.log('📝 New HTML generated, length:', newHtml.length)

        // ⚠️ CRITICAL: Update HTML content
        node.shape.content = newHtml

        // Force re-render bằng cách update wrapper element
        this.$nextTick(() => {
          // Tìm DOM element (pattern: nodeId_content_html)
          const htmlElement = document.querySelector(`[id="${node.id}_content_html"]`)
          if (htmlElement && htmlElement.parentElement) {
            // Trigger reflow để force browser re-render
            htmlElement.parentElement.style.display = 'none'
            void htmlElement.parentElement.offsetHeight // Force reflow
            htmlElement.parentElement.style.display = ''
            console.log('✅ Forced re-render via DOM for:', node.id)
          } else {
            // Fallback: gọi dataBind
            diagram.dataBind()
            console.log('✅ Rack HTML updated via dataBind for:', node.id)
          }
        })
      }
    },

    // Context menu
    onContextMenuOpen(args) {
      const diagram = this.$refs.diagramObj.ej2Instances
      if (diagram.selectedItems.nodes.length === 0) {
        args.cancel = true
        return
      }

      const node = diagram.selectedItems.nodes[0]

      // Bỏ qua grid cells, grid labels, grid headers, dimension labels, boundary, mask
      if (node.addInfo && (
        node.addInfo.isGridCell ||
        node.addInfo.isGridLabel ||
        node.addInfo.isGridHeader ||
        node.addInfo.isBoundary ||
        node.addInfo.isMask
      )) {
        args.cancel = true
        return
      }

      if (node.id && (
        node.id.startsWith('grid_') ||
        node.id.startsWith('edgeLabel_') ||
        node.id === this.roomContainerId + '_boundary' ||
        node.id === this.roomContainerId + '_mask'
      )) {
        args.cancel = true
        return
      }

      const isRack = node.addInfo && node.addInfo.isRack
      const isCage = node.addInfo && node.addInfo.isCage
      const isAisle = node.addInfo && node.addInfo.isAisle
      const isEquipment = node.addInfo && node.addInfo.isEquipment
      const isInCage = (isRack || isEquipment) && node.addInfo && node.addInfo.parentCageId
      // Ẩn/hiện menu items dựa vào context
      args.items.forEach((item) => {
        if (item.id === 'viewDetail') {
          item.visible = isRack || isEquipment || isCage || isAisle // Hiện cho rack, equipment, cage và aisle
        } else if (item.id === 'editCage') {
          item.visible = isCage // Chỉ hiện cho cage
        } else if (item.id === 'editAisle') {
          item.visible = isAisle // Chỉ hiện cho aisle
        } else if (item.id === 'moveToCage') {
          // Ẩn hoàn toàn nếu là cage hoặc aisle
          item.visible = !isCage && !isAisle && (isRack || isEquipment) && !isInCage
        } else if (item.id === 'removeFromCage') {
          // Ẩn hoàn toàn nếu là cage hoặc aisle
          item.visible = !isCage && !isAisle && (isRack || isEquipment) && isInCage
        } else if (item.id === 'delete') {
          item.visible = true // Luôn hiện
        }
      })
    },

    onContextMenuClick(args) {
      const diagram = this.$refs.diagramObj.ej2Instances

      if (args.item.id === 'viewDetail') {
        if (diagram.selectedItems.nodes.length > 0) {
          const node = diagram.selectedItems.nodes[0]
          if (node.addInfo && node.addInfo.isRack) {
            this.showRackDetail(node)
          } else if (node.addInfo && node.addInfo.isEquipment) {
            this.showEquipmentDetail(node)
          } else if (node.addInfo && node.addInfo.isCage) {
            this.showCageDetail(node)
          } else if (node.addInfo && node.addInfo.isAisle) {
            this.showAisleDetail(node)
          }
        }
      } else if (args.item.id === 'editCage') {
        if (diagram.selectedItems.nodes.length > 0) {
          const node = diagram.selectedItems.nodes[0]
          if (node.addInfo && node.addInfo.isCage) {
            this.editCage(node)
          }
        }
      } else if (args.item.id === 'editAisle') {
        if (diagram.selectedItems.nodes.length > 0) {
          const node = diagram.selectedItems.nodes[0]
          if (node.addInfo && node.addInfo.isAisle) {
            this.editAisle(node)
          }
        }
      } else if (args.item.id === 'moveToCage') {
        if (diagram.selectedItems.nodes.length > 0) {
          const node = diagram.selectedItems.nodes[0]
          this.moveToCage(node)
        }
      } else if (args.item.id === 'removeFromCage') {
        if (diagram.selectedItems.nodes.length > 0) {
          const node = diagram.selectedItems.nodes[0]
          this.removeFromCage(node)
        }
      } else if (args.item.id === 'delete') {
        if (diagram.selectedItems.nodes.length > 0) {
          const node = diagram.selectedItems.nodes[0]
          this.deleteNode(node)
        }
      }
    },

    showRackDetail(node) {
      if (!node || !node.addInfo) return

      const stackedRacks = this.getStackedRacksAtPosition(node.offsetX, node.offsetY)

      if (stackedRacks.length > 1) {
        // Có nhiều rack chồng lên -> Hiển thị b-modal chọn rack
        this.stackedRacksList = stackedRacks.map((rack, index) => ({
          ...rack,
          displayIndex: index + 1
        }))

        this.$nextTick(() => {
          this.$refs.stackedRacksModal.show()
        })
        return
      }

      // Chỉ có 1 rack -> Hiển thị chi tiết bình thường
      this.showSingleRackDetail(stackedRacks[0])
    },

    // Hiển thị chi tiết 1 rack
    showSingleRackDetail(rack) {
      this.selectedRackDetail = {
        id: rack.id,
        name: rack.rackName || rack.equipmentName,
        type: rack.rackType,
        totalU: rack.totalU,
        usedU: rack.usedU || 0,
        availableU: rack.totalU - (rack.usedU || 0)
      }

      // Show modal
      this.$nextTick(() => {
        this.$refs.rackDetailModal.show()
      })
    },

    // Chọn rack từ danh sách chồng
    selectStackedRack(rack) {
      // Đóng modal chọn
      this.$refs.stackedRacksModal.hide()

      // Hiển thị chi tiết rack đã chọn
      this.$nextTick(() => {
        this.showSingleRackDetail(rack)
      })
    },

    closeRackDetail() {
      this.$refs.rackDetailModal.closeModal()
    },

    onModalClose() {
      this.selectedRackDetail = null
    },

    // ========== EQUIPMENT DETAIL ==========

    showEquipmentDetail(node) {
      if (!node || !node.addInfo) return

      // Set equipment detail data
      this.selectedEquipmentDetail = {
        id: node.addInfo.equipmentId,
        name: node.addInfo.equipmentName,
        type: node.addInfo.equipmentType
      }

      // Show modal
      this.$nextTick(() => {
        this.$refs.equipmentDetailModal.show(null, 'VIEW')
      })
    },

    closeEquipmentDetail() {
      this.$refs.equipmentDetailModal.closeModal()
    },

    onEquipmentModalClose() {
      this.selectedEquipmentDetail = null
    },

    // ========== CAGE DETAIL ==========

    showCageDetail(node) {
      if (!node || !node.addInfo) return

      const diagram = this.$refs.diagramObj.ej2Instances

      // Tính diện tích polygon
      let area = 0
      if (node.addInfo.polygon && node.addInfo.polygon.length >= 3) {
        const polygon = node.addInfo.polygon
        const n = polygon.length
        for (let i = 0; i < n; i++) {
          const j = (i + 1) % n
          area += polygon[i][0] * polygon[j][1]
          area -= polygon[j][0] * polygon[i][1]
        }
        area = Math.abs(area) / 2 / 10000 // Chuyển từ cm² sang m²
      }

      // Lấy danh sách children
      const children = []
      if (node.addInfo.children && node.addInfo.children.length > 0) {
        node.addInfo.children.forEach((child) => {
          const childNode = diagram.getObject(child.id)
          if (childNode && childNode.addInfo) {
            children.push({
              id: child.id,
              name: childNode.addInfo.isRack ? childNode.addInfo.rackName : childNode.addInfo.equipmentName,
              type: childNode.addInfo.isRack ? 'rack' : 'equipment',
              relativeX: child.relativeX,
              relativeY: child.relativeY
            })
          }
        })
      }

      // Set cage detail data
      this.selectedCageDetail = {
        id: node.addInfo.cageId,
        name: node.addInfo.cageName,
        type: node.addInfo.cageType,
        color: node.addInfo.color,
        width: Math.round(node.width),
        height: Math.round(node.height),
        x: Math.round(node.offsetX),
        y: Math.round(node.offsetY),
        area: area.toFixed(2),
        polygon: node.addInfo.polygon,
        children: children
      }

      // Show modal
      this.$refs.ModalThongTinLongQuay.showModal()
    },

    closeCageDetail() {
      this.$refs.ModalThongTinLongQuay.closeModal()
    },

    onCageModalClose() {
      this.selectedCageDetail = null
    },

    // ========== AISLE DETAIL ==========

    showAisleDetail(node) {
      if (!node || !node.addInfo) return

      const diagram = this.$refs.diagramObj.ej2Instances

      // Tính diện tích polygon
      let area = 0
      if (node.addInfo.polygon && node.addInfo.polygon.length >= 3) {
        const polygon = node.addInfo.polygon
        for (let i = 0; i < polygon.length; i++) {
          const j = (i + 1) % polygon.length
          area += polygon[i][0] * polygon[j][1]
          area -= polygon[j][0] * polygon[i][1]
        }
        area = Math.abs(area) / 2
      }

      // Set aisle detail data
      this.selectedAisleDetail = {
        id: node.addInfo.aisleId,
        name: node.addInfo.aisleName,
        type: node.addInfo.aisleType,
        color: node.addInfo.color,
        x: Math.round(node.offsetX),
        y: Math.round(node.offsetY),
        area: area.toFixed(2),
        polygon: node.addInfo.polygon
      }

      // Show modal
      this.$refs.ModalThongtinHanhLang.showModal()
    },

    closeAisleDetail() {
      this.$refs.ModalThongtinHanhLang.closeModal()
    },

    onAisleModalClose() {
      this.selectedAisleDetail = null
    },

    // Chỉnh sửa hành lang
    editAisle(node) {
      if (!node || !node.addInfo || !node.addInfo.isAisle) {
        this.$toast.warning('Chỉ có thể chỉnh sửa hành lang!')
        return
      }

      // Lưu thông tin aisle để xử lý sau khi vẽ xong
      this._editingAisleNode = node

      // Chuyển polygon từ tọa độ tương đối sang tuyệt đối (theo room)
      const relativePolygon = node.addInfo.polygon
      const aisleX = node.offsetX
      const aisleY = node.offsetY

      const absolutePolygon = relativePolygon.map((p) => [p[0] + aisleX, p[1] + aisleY])

      // Mở modal vẽ với polygon hiện tại
      this.$nextTick(() => {
        this.$refs.drawAislePolygonModal.show(
          {
            id: node.addInfo.aisleId,
            name: node.addInfo.aisleName,
            type: node.addInfo.aisleType,
            color: node.addInfo.color
          },
          absolutePolygon // Truyền polygon làm tham số thứ 2
        )
      })
    },

    // Chỉnh sửa lồng quây
    editCage(node) {
      if (!node || !node.addInfo || !node.addInfo.isCage) {
        this.$toast.warning('Chỉ có thể chỉnh sửa lồng quây!')
        return
      }

      // Lưu thông tin cage để xử lý sau khi vẽ xong
      this._editingCageNode = node

      // Chuyển polygon từ tọa độ tương đối sang tuyệt đối (theo room)
      const relativePolygon = node.addInfo.polygon
      const cageX = node.offsetX
      const cageY = node.offsetY

      const absolutePolygon = relativePolygon.map((p) => [p[0] + cageX, p[1] + cageY])

      // Mở modal vẽ với polygon hiện tại
      this.$nextTick(() => {
        this.$refs.drawCagePolygonModal.show(
          {
            id: node.addInfo.cageId,
            name: node.addInfo.cageName,
            type: node.addInfo.cageType,
            color: node.addInfo.color || '#4CAF50'
          },
          absolutePolygon // Truyền polygon hiện tại để edit
        )
      })
    },

    // ========== CAGE OPERATIONS ==========

    // Chuyển rack/equipment vào quây
    moveToCage(node) {
      if (!node || !node.addInfo || (!node.addInfo.isRack && !node.addInfo.isEquipment)) {
        this.$toast.warning('Chỉ có thể chuyển rack hoặc thiết bị vào quây!')
        return
      }

      // Lấy danh sách cage trên diagram
      const diagram = this.$refs.diagramObj.ej2Instances
      const cages = diagram.nodes.filter((n) => n.addInfo && n.addInfo.isCage)

      if (cages.length === 0) {
        this.$toast.warning('Chưa có lồng quây nào trên sơ đồ!')
        return
      }

      // Chuẩn bị data cho modal
      const cageList = cages.map((c) => ({
        id: c.id,
        name: c.addInfo.cageName,
        type: c.addInfo.cageType,
        color: c.addInfo.color
      }))

      // Show modal chọn cage
      this.$refs.selectCageModal.show(cageList)

      // Lưu node để xử lý sau khi confirm
      this._pendingRackNode = node
    },

    // Xử lý khi confirm chọn cage
    async onCageSelected(cageId) {
      if (!this._pendingRackNode) return

      const diagram = this.$refs.diagramObj.ej2Instances
      const nodeToMove = diagram.getObject(this._pendingRackNode.id)

      if (!nodeToMove) {
        this.$toast.error('Không tìm thấy đối tượng!')
        return
      }

      const nodeName = nodeToMove.addInfo.isRack ? nodeToMove.addInfo.rackName : nodeToMove.addInfo.equipmentName
      const nodeType = nodeToMove.addInfo.isRack ? 'rack' : 'thiết bị'

      try {
        // Tìm cage container
        const cageContainer = diagram.getObject(cageId)

        if (!cageContainer || !cageContainer.addInfo || !cageContainer.addInfo.isCage) {
          this.$toast.error('Không tìm thấy cage!')
          return
        }

        const cageName = cageContainer.addInfo.cageName

        // VALIDATION: Tìm vị trí phù hợp trong cage để đặt node
        const nodeWidth = nodeToMove.width || 60
        const nodeHeight = nodeToMove.height || 60

        // Tính bounds của cage polygon (tọa độ tương đối)
        const cagePolygon = cageContainer.addInfo.polygon

        if (!cagePolygon || cagePolygon.length < 3) {
          this.$toast.error('Cage không có polygon hợp lệ!')
          console.error('❌ Invalid polygon:', cagePolygon)
          this._pendingRackNode = null
          return
        }

        const xs = cagePolygon.map((p) => p[0])
        const ys = cagePolygon.map((p) => p[1])
        const cageMinX = Math.min(...xs)
        const cageMaxX = Math.max(...xs)
        const cageMinY = Math.min(...ys)
        const cageMaxY = Math.max(...ys)

        // Tính góc trên bên trái của cage (cage có pivot 0,0)
        const cagePivot = cageContainer.pivot || { x: 0, y: 0 }
        const cageTopLeftX = cageContainer.offsetX - cageContainer.width * cagePivot.x
        const cageTopLeftY = cageContainer.offsetY - cageContainer.height * cagePivot.y

        // Tìm vị trí phù hợp trong cage
        // Chiến lược: Thử các vị trí ưu tiên trước, sau đó scan toàn bộ
        let foundPosition = null

        // Danh sách vị trí ưu tiên (góc trên trái, giữa, v.v.)
        const priorityPositions = [
          { relX: cageMinX, relY: cageMinY }, // Góc trên trái
          { relX: cageMinX + 10, relY: cageMinY + 10 }, // Gần góc trên trái
          { relX: (cageMinX + cageMaxX - nodeWidth) / 2, relY: (cageMinY + cageMaxY - nodeHeight) / 2 } // Giữa
        ]

        // Thử các vị trí ưu tiên trước
        for (const pos of priorityPositions) {
          if (
            pos.relX >= cageMinX &&
            pos.relX <= cageMaxX - nodeWidth &&
            pos.relY >= cageMinY &&
            pos.relY <= cageMaxY - nodeHeight
          ) {
            const tempNode = {
              offsetX: cageTopLeftX + pos.relX,
              offsetY: cageTopLeftY + pos.relY,
              width: nodeWidth,
              height: nodeHeight
            }

            if (this.isRackInsideCage(tempNode, cageContainer)) {
              foundPosition = { relX: pos.relX, relY: pos.relY, absX: tempNode.offsetX, absY: tempNode.offsetY }
              break
            }
          }
        }

        // Nếu chưa tìm thấy, scan toàn bộ với bước nhảy lớn hơn
        if (!foundPosition) {
          const step = 20 // cm - bước nhảy lớn hơn để nhanh hơn

          for (let relY = cageMinY; relY <= cageMaxY - nodeHeight; relY += step) {
            for (let relX = cageMinX; relX <= cageMaxX - nodeWidth; relX += step) {
              const tempNode = {
                offsetX: cageTopLeftX + relX,
                offsetY: cageTopLeftY + relY,
                width: nodeWidth,
                height: nodeHeight
              }

              if (this.isRackInsideCage(tempNode, cageContainer)) {
                foundPosition = { relX, relY, absX: tempNode.offsetX, absY: tempNode.offsetY }
                break
              }
            }
            if (foundPosition) break
          }
        }

        // Nếu vẫn không tìm thấy vị trí phù hợp
        if (!foundPosition) {
          this.$toast.error(`${nodeType} không vừa với quây ${cageName}! Vui lòng thử quây lớn hơn.`)
          this._pendingRackNode = null
          return
        }

        // Đặt node vào vị trí đã tìm được
        const relativeX = foundPosition.relX
        const relativeY = foundPosition.relY

        // Di chuyển node đến vị trí
        nodeToMove.offsetX = foundPosition.absX
        nodeToMove.offsetY = foundPosition.absY

        // Thêm node vào danh sách children của cage
        if (!cageContainer.addInfo.children) {
          cageContainer.addInfo.children = []
        }

        cageContainer.addInfo.children.push({
          id: nodeToMove.id,
          relativeX: relativeX,
          relativeY: relativeY
        })

        // Đánh dấu node thuộc về cage
        nodeToMove.addInfo.parentCageId = cageContainer.id

        // Call api save data
        if (nodeToMove.addInfo.isRack) {
          let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/capnhat-longquay-rack', {
            rackID: nodeToMove.addInfo.rackId.replace('node_', ''),
            longQuayID: cageContainer.addInfo.cageId.replace('cage_', '')
          })
          let rsPosition = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/update-vitri-rack', {
            rackID: nodeToMove.addInfo.rackId.replace('node_', ''),
            toaDoX: foundPosition.absX,
            toaDoY: foundPosition.absY,
            huongNgang: nodeToMove.addInfo.direction == 1 ? true : false
          })
          if (rs.data.result) {
            this.$toast.success(`Đã thêm ${nodeName} vào ${cageName}`)
          } else {
            this.$toast.error(`Lỗi khi thêm ${nodeName} vào ${cageName}`)
            return
          }
        } else if (nodeToMove.addInfo.isEquipment) {
          let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/capnhat-longquay-thietbi', {
            thietBiID: nodeToMove.addInfo.equipmentId.replace('node_', ''),
            longQuayID: cageContainer.addInfo.cageId.replace('cage_', '')
          })
          let rsPosition = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/update-vitri-thietbi', {
            thietBiID: nodeToMove.addInfo.equipmentId.replace('node_', ''),
            toaDoX: foundPosition.absX,
            toaDoY: foundPosition.absY
          })
          if (rs.data.result) {
            this.$toast.success(`Đã thêm ${nodeName} vào ${cageName}`)
          } else {
            this.$toast.error(`Lỗi khi thêm ${nodeName} vào ${cageName}`)
            return
          }
        }

        // Update diagram
        diagram.dataBind()

        // Clear selection
        diagram.clearSelection()
      } catch (error) {
        console.error('❌ Error in onCageSelected:', error)
        this.$toast.error('Lỗi khi xử lý!')
      }

      this._pendingRackNode = null
    },

    // Tách rack/equipment khỏi quây
    async removeFromCage(node) {
      if (!node || !node.addInfo || (!node.addInfo.isRack && !node.addInfo.isEquipment)) {
        this.$toast.warning('Chỉ có thể tách rack hoặc thiết bị khỏi quây!')
        return
      }

      const diagram = this.$refs.diagramObj.ej2Instances
      const nodeName = node.addInfo.isRack ? node.addInfo.rackName : node.addInfo.equipmentName
      const nodeType = node.addInfo.isRack ? 'rack' : 'thiết bị'

      // Kiểm tra xem node có parentCageId không
      if (!node.addInfo.parentCageId) {
        this.$toast.warning(`${nodeType} này chưa nằm trong quây nào!`)
        return
      }

      try {
        // Lấy cage container
        const cageId = node.addInfo.parentCageId
        const cageContainer = diagram.getObject(cageId)

        if (!cageContainer) {
          this.$toast.error('Không tìm thấy cage!')
          return
        }

        // Xóa khỏi children array
        if (cageContainer.addInfo.children) {
          const index = cageContainer.addInfo.children.findIndex((c) => c.id === node.id)
          if (index !== -1) {
            cageContainer.addInfo.children.splice(index, 1)
          }
        }
        if (node.addInfo.isRack) {
          let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/xoa-longquay-rack', {
            id: node.addInfo.rackId.replace('node_', '')
          })
        } else if (node.addInfo.isEquipment) {
          let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/xoa-longquay-thietbi', {
            id: node.addInfo.equipmentId.replace('node_', '')
          })
        }
        // Xóa parentCageId
        delete node.addInfo.parentCageId

        // Update diagram
        diagram.dataBind()

        this.$toast.success(`Đã tách ${nodeName} khỏi quây`)
      } catch (error) {
        console.error('❌ Error removing from cage:', error)
        this.$toast.error(`Lỗi khi tách ${nodeType} khỏi quây!`)
      }
    },

    // Kiểm tra rack có nằm trong cage không
    isRackInsideCage(rackNode, cageNode) {
      const rackWidth = rackNode.width || 60
      const rackHeight = rackNode.height || 60

      // Rack có pivot (0,0) nên offsetX/Y đã là góc trên bên trái
      const rackTopLeftX = rackNode.offsetX
      const rackTopLeftY = rackNode.offsetY

      // Kiểm tra tất cả 4 góc của rack (tọa độ tuyệt đối)
      const corners = [
        { x: rackTopLeftX, y: rackTopLeftY }, // Top-left
        { x: rackTopLeftX + rackWidth, y: rackTopLeftY }, // Top-right
        { x: rackTopLeftX, y: rackTopLeftY + rackHeight }, // Bottom-left
        { x: rackTopLeftX + rackWidth, y: rackTopLeftY + rackHeight } // Bottom-right
      ]

      // Lấy polygon của cage và chuyển sang tọa độ tuyệt đối
      // Cage cũng có pivot (0,0) nên offsetX/Y là góc trên bên trái
      const cagePivot = cageNode.pivot || { x: 0, y: 0 }
      const cageTopLeftX = cageNode.offsetX - cageNode.width * cagePivot.x
      const cageTopLeftY = cageNode.offsetY - cageNode.height * cagePivot.y

      // Chuyển polygon từ tọa độ tương đối sang tuyệt đối
      const polygon = cageNode.addInfo.polygon.map((point) => [point[0] + cageTopLeftX, point[1] + cageTopLeftY])

      // Helper function: Kiểm tra điểm có nằm trên cạnh không
      const isPointOnEdge = (point, p1, p2, tolerance = 1) => {
        const minX = Math.min(p1[0], p2[0]) - tolerance
        const maxX = Math.max(p1[0], p2[0]) + tolerance
        const minY = Math.min(p1[1], p2[1]) - tolerance
        const maxY = Math.max(p1[1], p2[1]) + tolerance

        if (point.x < minX || point.x > maxX || point.y < minY || point.y > maxY) {
          return false
        }

        const crossProduct = Math.abs((point.y - p1[1]) * (p2[0] - p1[0]) - (point.x - p1[0]) * (p2[1] - p1[1]))
        const lineLength = Math.sqrt(Math.pow(p2[0] - p1[0], 2) + Math.pow(p2[1] - p1[1], 2))

        return crossProduct / lineLength < tolerance
      }

      // Kiểm tra từng góc của rack có nằm trong cage polygon không
      for (const corner of corners) {
        // Kiểm tra xem điểm có nằm trên cạnh của polygon không
        let onEdge = false
        for (let i = 0, j = polygon.length - 1; i < polygon.length; j = i++) {
          if (isPointOnEdge(corner, polygon[j], polygon[i])) {
            onEdge = true
            break
          }
        }

        // Nếu nằm trên cạnh, coi như nằm trong và tiếp tục kiểm tra góc tiếp theo
        if (onEdge) {
          continue
        }

        // Ray casting algorithm để kiểm tra điểm có nằm trong polygon không
        let inside = false
        for (let i = 0, j = polygon.length - 1; i < polygon.length; j = i++) {
          const xi = polygon[i][0]
          const yi = polygon[i][1]
          const xj = polygon[j][0]
          const yj = polygon[j][1]

          const intersect = yi > corner.y !== yj > corner.y && corner.x < ((xj - xi) * (corner.y - yi)) / (yj - yi) + xi

          if (intersect) inside = !inside
        }

        // Nếu có bất kỳ góc nào không nằm trên cạnh và nằm ngoài cage thì return false
        if (!inside) {
          return false
        }
      }

      // Tất cả 4 góc đều nằm trong hoặc trên cạnh cage
      return true
    },

    // KIỂM TRA 2 CAGE CÓ CHỒNG LÊN NHAU KHÔNG
    // Sử dụng Separating Axis Theorem (SAT) cho polygon collision detection
    isCageOverlapping(polygon1Absolute, cageNode2) {
      // polygon1Absolute: Polygon của cage đang di chuyển (tọa độ tuyệt đối)
      // cageNode2: Node của cage khác cần kiểm tra

      // Lấy polygon của cage 2 và chuyển sang tọa độ tuyệt đối
      const cagePivot = cageNode2.pivot || { x: 0, y: 0 }
      const cageTopLeftX = cageNode2.offsetX - cageNode2.width * cagePivot.x
      const cageTopLeftY = cageNode2.offsetY - cageNode2.height * cagePivot.y
      const polygon2Absolute = cageNode2.addInfo.polygon.map((point) => [
        point[0] + cageTopLeftX,
        point[1] + cageTopLeftY
      ])

      // Helper: Kiểm tra điểm có nằm trong polygon không (Ray casting)
      const isPointInPolygonHelper = (point, polygon) => {
        let inside = false
        for (let i = 0, j = polygon.length - 1; i < polygon.length; j = i++) {
          const xi = polygon[i][0]
          const yi = polygon[i][1]
          const xj = polygon[j][0]
          const yj = polygon[j][1]

          const intersect = yi > point[1] !== yj > point[1] && point[0] < ((xj - xi) * (point[1] - yi)) / (yj - yi) + xi

          if (intersect) inside = !inside
        }
        return inside
      }

      // Helper: Kiểm tra 2 đoạn thẳng có giao nhau không
      const doSegmentsIntersect = (p1, p2, p3, p4) => {
        const ccw = (A, B, C) => {
          return (C[1] - A[1]) * (B[0] - A[0]) > (B[1] - A[1]) * (C[0] - A[0])
        }
        return ccw(p1, p3, p4) !== ccw(p2, p3, p4) && ccw(p1, p2, p3) !== ccw(p1, p2, p4)
      }

      // Kiểm tra 1: Có điểm nào của polygon1 nằm trong polygon2 không?
      for (const point of polygon1Absolute) {
        if (isPointInPolygonHelper(point, polygon2Absolute)) {
          return true // Có overlap
        }
      }

      // Kiểm tra 2: Có điểm nào của polygon2 nằm trong polygon1 không?
      for (const point of polygon2Absolute) {
        if (isPointInPolygonHelper(point, polygon1Absolute)) {
          return true // Có overlap
        }
      }

      // Kiểm tra 3: Có cạnh nào của 2 polygon giao nhau không?
      for (let i = 0; i < polygon1Absolute.length; i++) {
        const p1 = polygon1Absolute[i]
        const p2 = polygon1Absolute[(i + 1) % polygon1Absolute.length]

        for (let j = 0; j < polygon2Absolute.length; j++) {
          const p3 = polygon2Absolute[j]
          const p4 = polygon2Absolute[(j + 1) % polygon2Absolute.length]

          if (doSegmentsIntersect(p1, p2, p3, p4)) {
            return true // Có overlap
          }
        }
      }

      // Không có overlap
      return false
    },

    // 🎯 TÌM VỊ TRÍ HỢP LỆ TRONG PHÒNG (cho cage/aisle)
    // Tương tự logic tìm vị trí rack trong cage, nhưng áp dụng cho phòng
    findValidPositionInRoom(objectWidth, objectHeight) {
      const bounds = this.getPolygonBounds()

      // Danh sách vị trí ưu tiên
      const priorityPositions = [
        { x: bounds.minX, y: bounds.minY }, // 🎯 Góc trên trái
        { x: bounds.minX + 10, y: bounds.minY + 10 }, // 🎯 Gần góc trên trái
        { x: bounds.minX + (bounds.width - objectWidth) / 2, y: bounds.minY + (bounds.height - objectHeight) / 2 } // 🎯 Giữa
      ]

      // Phase 1: Thử các vị trí ưu tiên
      for (const pos of priorityPositions) {
        // Kiểm tra bounds cơ bản
        if (
          pos.x >= bounds.minX &&
          pos.x <= bounds.maxX - objectWidth &&
          pos.y >= bounds.minY &&
          pos.y <= bounds.maxY - objectHeight
        ) {
          // Kiểm tra tất cả 4 góc có nằm trong polygon phòng không
          if (this.isObjectInsideRoom(pos.x, pos.y, objectWidth, objectHeight)) {
            console.log('Found priority position:', pos)
            return pos
          }
        }
      }

      // Phase 2: Grid scanning nếu không tìm được vị trí ưu tiên
      const step = 20 // cm - bước nhảy

      for (let y = bounds.minY; y <= bounds.maxY - objectHeight; y += step) {
        for (let x = bounds.minX; x <= bounds.maxX - objectWidth; x += step) {
          if (this.isObjectInsideRoom(x, y, objectWidth, objectHeight)) {
            console.log('Found position via grid scan:', { x, y })
            return { x, y }
          }
        }
      }

      // Không tìm được vị trí hợp lệ
      console.warn('⚠️ Could not find valid position in room')
      return null
    },

    // Kiểm tra object (cage/aisle) có nằm hoàn toàn trong polygon phòng không
    isObjectInsideRoom(x, y, width, height) {
      // Kiểm tra tất cả 4 góc
      const corners = [
        { x: x, y: y }, // Top-left
        { x: x + width, y: y }, // Top-right
        { x: x, y: y + height }, // Bottom-left
        { x: x + width, y: y + height } // Bottom-right
      ]

      // Kiểm tra từng góc có nằm trong polygon phòng không
      for (const corner of corners) {
        if (!this.isPointInPolygon(corner.x, corner.y)) {
          return false // Có góc nằm ngoài phòng
        }
      }

      return true // Tất cả 4 góc đều nằm trong phòng
    },

    // Kiểm tra rack/equipment có đè lên quạt/cột không
    checkOverlapWithFansAndColumns(node) {
      const diagram = this.$refs.diagramObj.ej2Instances
      const overlaps = []

      const nodeX = node.offsetX
      const nodeY = node.offsetY
      const nodeWidth = node.width || 60
      const nodeHeight = node.height || 60

      // Lấy tất cả quạt và cột
      const fansAndColumns = diagram.nodes.filter(
        (n) => n.addInfo && (n.addInfo.isFan || n.addInfo.isColumn)
      )

      for (const target of fansAndColumns) {
        const targetX = target.offsetX
        const targetY = target.offsetY
        const targetWidth = target.width || 60
        const targetHeight = target.height || 60

        // Kiểm tra overlap bằng AABB (Axis-Aligned Bounding Box)
        const isOverlapping = !(
          nodeX + nodeWidth <= targetX || // node ở bên trái target
          nodeX >= targetX + targetWidth || // node ở bên phải target
          nodeY + nodeHeight <= targetY || // node ở phía trên target
          nodeY >= targetY + targetHeight // node ở phía dưới target
        )

        if (isOverlapping) {
          overlaps.push(target)
        }
      }

      return overlaps
    },

    deleteNode(node) {
      if (node.id === this.roomContainerId + '_boundary' || node.id === this.roomContainerId + '_mask') {
        this.$toast.warning('Không thể xóa phòng/zone!')
        return
      }

      // Kiểm tra loại node
      const isCage = node.addInfo && node.addInfo.isCage
      const isAisle = node.addInfo && node.addInfo.isAisle
      const isRack = node.addInfo && node.addInfo.isRack
      const isEquipment = node.addInfo && node.addInfo.isEquipment

      if (!isCage && !isAisle && !isRack && !isEquipment) {
        this.$toast.warning('Không thể xóa node này!')
        return
      }

      let nodeName, nodeType
      if (isCage) {
        nodeName = node.addInfo.cageName
        nodeType = 'lồng quây'
      } else if (isAisle) {
        nodeName = node.addInfo.aisleName
        nodeType = 'hành lang'
      } else if (isRack) {
        nodeName = node.addInfo.rackName
        nodeType = 'rack'
      } else {
        nodeName = node.addInfo.equipmentName
        nodeType = 'thiết bị'
      }

      this.$confirm(`Xác nhận gỡ ${nodeType} "${nodeName}" khỏi vị trí lắp đặt?`, `Gỡ ${nodeType}`, {
        confirmButtonText: 'Xác nhận',
        cancelButtonText: 'Hủy',
        type: 'warning'
      })
        .then(async () => {
          const diagram = this.$refs.diagramObj.ej2Instances

          try {
            // Gọi API xóa vị trí
            let apiSuccess = false

            if (isCage) {
              const cageId = node.addInfo.cageId.replace('cage_', '')
              const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/delete-vitri-longquay', {
                id: cageId
              })
              apiSuccess = rs.error_code === 'BSS-00000000' || rs.data?.result === true
            } else if (isAisle) {
              const aisleId = node.addInfo.aisleId.replace('aisle_', '')
              const rs = await this.$root.context.post(
                '/web-ecms/tainguyen-idc/khong-gian-hanh-lang/delete-vitri-hanhlang',
                {
                  id: aisleId
                }
              )
              apiSuccess = rs.error_code === 'BSS-00000000' || rs.data?.result === true
            } else if (isRack) {
              const rackId = node.addInfo.rackId.replace('node_', '')
              const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/delete-vitri-rack', {
                id: rackId
              })
              apiSuccess = rs.error_code === 'BSS-00000000' || rs.data?.result === true
            } else if (isEquipment) {
              const equipmentId = node.addInfo.equipmentId.replace('node_', '')
              const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/delete-vitri-thietbi', {
                id: equipmentId
              })
              apiSuccess = rs.error_code === 'BSS-00000000' || rs.data?.result === true
            }

            if (!apiSuccess) {
              this.$toast.error(`Lỗi khi gỡ ${nodeType} khỏi vị trí!`)
              return
            }

            // Return to palette
            if (isCage) {
              // Return cage to palette - SỬ DỤNG POLYGON MỚI NHẤT TỪ NODE
              const cageId = node.addInfo.cageId
              const cageBackup = this.allCagesBackup.find((c) => c.id === cageId)

              if (cageBackup) {
                const cage = JSON.parse(JSON.stringify(cageBackup))
                // Cập nhật polygon từ node (đã được edit)
                cage.polygon = node.addInfo.polygon
                delete cage.x
                delete cage.y
                this.uninstalledCages.push(cage)

                // Cập nhật luôn trong backup để lần sau dùng đúng
                const backupIndex = this.allCagesBackup.findIndex((c) => c.id === cageId)
                if (backupIndex !== -1) {
                  this.allCagesBackup[backupIndex].polygon = node.addInfo.polygon
                }
              }
            } else if (isAisle) {
              // Return aisle to palette - SỬ DỤNG POLYGON MỚI NHẤT TỪ NODE
              const aisleId = node.addInfo.aisleId
              const aisleBackup = this.allAislesBackup.find((a) => a.id === aisleId)

              if (aisleBackup) {
                const aisle = JSON.parse(JSON.stringify(aisleBackup))
                // Cập nhật polygon từ node (đã được edit)
                aisle.polygon = node.addInfo.polygon
                delete aisle.x
                delete aisle.y
                this.uninstalledAisles.push(aisle)

                // Cập nhật luôn trong backup để lần sau dùng đúng
                const backupIndex = this.allAislesBackup.findIndex((a) => a.id === aisleId)
                if (backupIndex !== -1) {
                  this.allAislesBackup[backupIndex].polygon = node.addInfo.polygon
                }
              }
            } else if (isRack) {
              // Return rack to palette
              const rack = {
                id: node.addInfo.rackId,
                name: node.addInfo.rackName,
                type: node.addInfo.rackType,
                totalU: node.addInfo.totalU,
                width: node.addInfo.width,
                height: node.addInfo.height
              }
              this.uninstalledRacks.push(rack)
            } else if (isEquipment) {
              // Return equipment to palette từ backup
              const equipmentId = node.addInfo.equipmentId
              const equipmentBackup = this.allEquipmentsBackup.find((e) => e.id === equipmentId)

              if (equipmentBackup) {
                const equipment = JSON.parse(JSON.stringify(equipmentBackup))
                delete equipment.x
                delete equipment.y
                this.uninstalledEquipments.push(equipment)
              }
            }

            // Remove from diagram
            diagram.remove(node)
            this.$toast.success(`Đã gỡ ${nodeType}`)
          } catch (error) {
            console.error('❌ Error deleting node:', error)
            this.$toast.error(`Lỗi khi gỡ ${nodeType}!`)
          }
        })
        .catch(() => { })
    },

    // ========== TOOLBAR ACTIONS ==========

    async onSave() {
      try {
        this.$root.showLoading(true)
        const diagram = this.$refs.diagramObj.ej2Instances

        // KIỂM TRA OVERLAP VỚI QUẠT VÀ CỘT TRƯỚC KHI LƯU
        const racksAndEquipments = diagram.nodes.filter(
          (node) => node.addInfo && (node.addInfo.isRack || node.addInfo.isEquipment)
        )

        for (const node of racksAndEquipments) {
          const overlappingFansAndColumns = this.checkOverlapWithFansAndColumns(node)
          if (overlappingFansAndColumns.length > 0) {
            const nodeName = node.addInfo.rackName || node.addInfo.equipmentName
            const overlappingList = overlappingFansAndColumns
              .map((item) => {
                const type = item.addInfo.isFan ? 'Quạt' : 'Cột'
                const name = item.addInfo.fanName || item.addInfo.columnName
                return `• ${type}: ${name}`
              })
              .join('\n')

            this.$root.showLoading(false)
            this.$toast.error(
              `${nodeName} đang đè lên quạt/cột:\n${overlappingList}\nVui lòng di chuyển sang vị trí khác.`,
              { duration: 6000 }
            )
            return
          }
        }

        // KIỂM TRA OVERLAP CAGE TRƯỚC KHI LƯU
        const cageNodesAll = diagram.nodes.filter((node) => node.addInfo && node.addInfo.isCage)

        // Kiểm tra từng cặp cage có overlap không
        for (let i = 0; i < cageNodesAll.length; i++) {
          for (let j = i + 1; j < cageNodesAll.length; j++) {
            const cage1 = cageNodesAll[i]
            const cage2 = cageNodesAll[j]

            // Tính polygon vị trí hiện tại của cage1
            const polygon1 = cage1.addInfo.polygon
            const xs1 = polygon1.map((p) => p[0])
            const ys1 = polygon1.map((p) => p[1])
            const minX1 = Math.min(...xs1)
            const minY1 = Math.min(...ys1)
            const deltaX1 = cage1.offsetX - minX1
            const deltaY1 = cage1.offsetY - minY1
            const currentPolygon1 = polygon1.map((p) => [p[0] + deltaX1, p[1] + deltaY1])

            // Kiểm tra overlap với cage2
            const hasOverlap = this.isCageOverlapping(currentPolygon1, cage2)

            if (hasOverlap) {
              this.$root.showLoading(false)
              this.$toast.error('Không thể lưu! Cage "' + cage1.addInfo.cageName + '" đang chồng lên cage "' + cage2.addInfo.cageName + '". Vui lòng điều chỉnh vị trí.', 'warning')
              return
            }
          }
        }

        // Lấy tất cả nodes theo loại
        const cageNodes = diagram.nodes.filter((node) => node.addInfo && node.addInfo.isCage)
        const aisleNodes = diagram.nodes.filter((node) => node.addInfo && node.addInfo.isAisle)
        const rackNodesRaw = diagram.nodes.filter((node) => node.addInfo && node.addInfo.isRack)
        const equipmentNodesRaw = diagram.nodes.filter((node) => node.addInfo && node.addInfo.isEquipment)

        // Chuẩn bị data cho racks với 2 loại toạ độ
        const rackNodes = rackNodesRaw.map((node) => {
          const absoluteX = Math.round(node.offsetX)
          const absoluteY = Math.round(node.offsetY)
          const rackData = {
            id: node.addInfo.rackId.replace('node_', ''),
            name: node.addInfo.rackName,
            width: Math.round(node.width),
            height: Math.round(node.height),
            // Toạ độ tuyệt đối (đối với room)
            absoluteX: absoluteX,
            absoluteY: absoluteY,
            // Toạ độ tương đối (đối với cage) - null nếu không trong cage
            relativeX: null,
            relativeY: null,
            cageId: null,
            direction: node.addInfo.direction
          }

          // Nếu rack nằm trong cage, tính toạ độ tương đối
          if (node.addInfo.parentCageId) {
            const cageNode = diagram.getObject(node.addInfo.parentCageId)
            if (cageNode) {
              const cagePivot = cageNode.pivot || { x: 0.5, y: 0.5 }
              const cageTopLeftX = cageNode.offsetX - cageNode.width * cagePivot.x
              const cageTopLeftY = cageNode.offsetY - cageNode.height * cagePivot.y

              rackData.cageId = cageNode.addInfo.cageId.replace('cage_', '')
              rackData.relativeX = Math.round(absoluteX - cageTopLeftX)
              rackData.relativeY = Math.round(absoluteY - cageTopLeftY)
            }
          }

          return rackData
        })

        // Chuẩn bị data cho equipments với 2 loại toạ độ
        const equipmentNodes = equipmentNodesRaw.map((node) => {
          const absoluteX = Math.round(node.offsetX)
          const absoluteY = Math.round(node.offsetY)

          const equipmentData = {
            id: node.addInfo.equipmentId.replace('eq_', ''),
            name: node.addInfo.equipmentName,
            width: Math.round(node.width),
            height: Math.round(node.height),
            // Toạ độ tuyệt đối (đối với room)
            absoluteX: absoluteX,
            absoluteY: absoluteY,
            // Toạ độ tương đối (đối với cage) - null nếu không trong cage
            relativeX: null,
            relativeY: null,
            cageId: null
          }

          // Nếu equipment nằm trong cage, tính toạ độ tương đối
          if (node.addInfo.parentCageId) {
            const cageNode = diagram.getObject(node.addInfo.parentCageId)
            if (cageNode) {
              const cagePivot = cageNode.pivot || { x: 0.5, y: 0.5 }
              const cageTopLeftX = cageNode.offsetX - cageNode.width * cagePivot.x
              const cageTopLeftY = cageNode.offsetY - cageNode.height * cagePivot.y

              equipmentData.cageId = cageNode.addInfo.cageId.replace('cage_', '')
              equipmentData.relativeX = Math.round(absoluteX - cageTopLeftX)
              equipmentData.relativeY = Math.round(absoluteY - cageTopLeftY)
            }
          }

          return equipmentData
        })

        let totalSaved = 0
        let totalFailed = 0

        // 1. Lưu CAGES
        if (cageNodes.length > 0) {
          for (const node of cageNodes) {
            try {
              const cageId = node.addInfo.cageId.replace('cage_', '')

              // Với pivot (0,0), offsetX/Y là góc trên trái
              const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/update-vitri-longquay', {
                longQuayID: cageId,
                toaDoX: Math.round(node.offsetX),
                toaDoY: Math.round(node.offsetY)
              })

              if (rs.error_code === 'BSS-00000000') {
                totalSaved++
              } else {
                totalFailed++
              }
            } catch (error) {
              totalFailed++
              console.error('❌ Error saving cage:', error)
            }
          }
        }

        // 2. Lưu AISLES
        if (aisleNodes.length > 0) {
          for (const node of aisleNodes) {
            try {
              const aisleId = node.addInfo.aisleId.replace('aisle_', '')

              // Với pivot (0,0), offsetX/Y là góc trên trái
              const rs = await this.$root.context.post(
                '/web-ecms/tainguyen-idc/khong-gian-hanh-lang/update-vitri-hanhlang',
                {
                  hanhLangID: aisleId,
                  toaDoX: Math.round(node.offsetX),
                  toaDoY: Math.round(node.offsetY)
                }
              )

              if (rs.error_code === 'BSS-00000000') {
                totalSaved++
              } else {
                totalFailed++
              }
            } catch (error) {
              totalFailed++
              console.error('❌ Error saving aisle:', error)
            }
          }
        }

        // 3. Lưu RACKS
        if (rackNodes.length > 0) {
          for (const rack of rackNodes) {
            try {
              // Chuẩn bị payload
              const payload = {
                rackID: rack.id,
                toaDoX: rack.absoluteX,
                toaDoY: rack.absoluteY,
                huongNgang: rack.direction == 1 ? true : false
              }

              // Thêm longQuayID nếu nằm trong cage
              if (rack.cageId) {
                payload.longQuayID = rack.cageId
              }

              const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/update-vitri-rack', payload)

              if (rs.error_code === 'BSS-00000000') {
                totalSaved++
              } else {
                totalFailed++
              }
            } catch (error) {
              totalFailed++
              console.error('❌ Error saving rack:', error)
            }
          }
        }

        // 3. Lưu EQUIPMENTS
        if (equipmentNodes.length > 0) {
          for (const equipment of equipmentNodes) {
            try {
              // Chuẩn bị payload
              const payload = {
                thietBiID: equipment.id.replace('node_', ''),
                toaDoX: equipment.absoluteX,
                toaDoY: equipment.absoluteY
              }

              // Thêm longQuayID nếu nằm trong cage
              if (equipment.cageId) {
                payload.longQuayID = equipment.cageId
              }

              const rs = await this.$root.context.post(
                '/web-ecms/tainguyen-idc/khong-gian/update-vitri-thietbi',
                payload
              )

              if (rs.error_code === 'BSS-00000000') {
                totalSaved++
              } else {
                totalFailed++
              }
            } catch (error) {
              totalFailed++
              console.error('❌ Error saving equipment:', error)
            }
          }
        }

        // Hiển thị kết quả
        const totalItems = cageNodes.length + aisleNodes.length + rackNodes.length + equipmentNodes.length

        if (totalSaved > 0) {
          this.$toast.success(`Lưu thành công ${totalSaved}/${totalItems} đối tượng`)
        }
        if (totalFailed > 0) {
          this.$toast.error(`Đã có ${totalFailed}/${totalItems} đối tượng không lưu được`)
        }
        if (totalItems === 0) {
          this.$toast.warning('Không có đối tượng nào để lưu')
        }
      } catch (error) {
        console.error('Error saving diagram:', error)
        this.$toast.error('Lỗi khi lưu!')
      } finally {
        this.$root.showLoading(false)
      }
      this.$emit('refresh')
    },

    onZoomIn() {
      if (this.isZoomInDisabled) return
      const diagram = this.$refs.diagramObj.ej2Instances
      const newZoom = this.currentZoom * 1.2
      if (newZoom <= this.scrollSettings.maxZoom) {
        diagram.zoom(1.2, { x: 0.5, y: 0.5 })
        this.currentZoom = newZoom
      } else {
        // Zoom to exact max
        const factor = this.scrollSettings.maxZoom / this.currentZoom
        diagram.zoom(factor, { x: 0.5, y: 0.5 })
        this.currentZoom = this.scrollSettings.maxZoom
      }
      // Force update from diagram
      this.$nextTick(() => {
        if (diagram.scrollSettings && diagram.scrollSettings.currentZoom) {
          this.currentZoom = diagram.scrollSettings.currentZoom
        }
      })
    },

    onZoomOut() {
      if (this.isZoomOutDisabled) return
      const diagram = this.$refs.diagramObj.ej2Instances
      const newZoom = this.currentZoom / 1.2
      if (newZoom >= this.scrollSettings.minZoom) {
        diagram.zoom(1 / 1.2, { x: 0.5, y: 0.5 })
        this.currentZoom = newZoom
      } else {
        // Zoom to exact min
        const factor = this.scrollSettings.minZoom / this.currentZoom
        diagram.zoom(factor, { x: 0.5, y: 0.5 })
        this.currentZoom = this.scrollSettings.minZoom
      }
      // Force update from diagram
      this.$nextTick(() => {
        if (diagram.scrollSettings && diagram.scrollSettings.currentZoom) {
          this.currentZoom = diagram.scrollSettings.currentZoom
        }
      })
    },

    onReset() {
      const diagram = this.$refs.diagramObj.ej2Instances
      diagram.reset()
      diagram.fitToPage()
      this.currentZoom = 1
    },

    onManageAisles() {
      // Mở modal quản lý hành lang
      this.$refs.modalCRUDHanhLang.showModal()
    },

    async onAisleRefresh() {
      // Callback khi có thay đổi từ modal CRUD hành lang
      try {
        // Reload lại danh sách hành lang
        await this.loadRackData()

        // Refresh diagram
        const diagram = this.$refs.diagramObj.ej2Instances
        if (diagram) {
          diagram.dataBind()
        }

        this.$toast.success('Đã cập nhật danh sách hành lang')
      } catch (error) {
        console.error('❌ Error refreshing aisles:', error)
      }
    },

    // ========== DATA LOADING ==========

    async loadRackData() {
      try {
        this.isDataLoaded = false

        const [
          roomInfo,
          installed,
          uninstalled,
          installedCages,
          uninstalledCages,
          installedAisles,
          uninstalledAisles,
          installedEquipments,
          uninstalledEquipments,
          installedFans,
          installedColumns
        ] = await Promise.all([
          this.getRoomInfo(),
          this.getInstalledRacks(),
          this.getUninstalledRacks(),
          this.getInstalledCages(),
          this.getUninstalledCages(),
          this.getInstalledAisles(),
          this.getUninstalledAisles(),
          this.getInstalledEquipments(),
          this.getUninstalledEquipments(),
          this.getInstalledFans(),
          this.getInstalledColumns()
        ])

        // Cập nhật roomPolygon từ API
        if (roomInfo && roomInfo.polygon) {
          this.roomPolygon = roomInfo.polygon
        }

        this.installedRacks = Array.isArray(installed) ? installed : []
        this.uninstalledRacks = Array.isArray(uninstalled) ? uninstalled : []
        this.installedCages = Array.isArray(installedCages) ? installedCages : []
        this.uninstalledCages = Array.isArray(uninstalledCages) ? uninstalledCages : []
        this.installedAisles = Array.isArray(installedAisles) ? installedAisles : []
        this.uninstalledAisles = Array.isArray(uninstalledAisles) ? uninstalledAisles : []
        this.installedEquipments = Array.isArray(installedEquipments) ? installedEquipments : []
        this.uninstalledEquipments = Array.isArray(uninstalledEquipments) ? uninstalledEquipments : []
        this.installedFans = Array.isArray(installedFans) ? installedFans : []
        this.installedColumns = Array.isArray(installedColumns) ? installedColumns : []

        // Backup data để restore khi xóa
        this.allCagesBackup = JSON.parse(JSON.stringify([...this.installedCages, ...this.uninstalledCages]))
        this.allAislesBackup = JSON.parse(JSON.stringify([...this.installedAisles, ...this.uninstalledAisles]))
        this.allEquipmentsBackup = JSON.parse(
          JSON.stringify([...this.installedEquipments, ...this.uninstalledEquipments])
        )

        // Xây dựng map các rack chồng lên nhau theo vị trí
        this.buildStackedRacksMap()

        this.isDataLoaded = true
      } catch (error) {
        console.error('❌ Error loading data:', error)
        this.$toast.error('Lỗi khi tải dữ liệu')
        this.isDataLoaded = true
      }
    },

    async getRoomInfo() {
      try {
        const rs = await this.$root.context.post('/web-ecms/idc/phong-zone/get-by-id', {
          id: this.modalItemID
        })

        if (!rs || !rs.data || !Array.isArray(rs.data) || rs.data.length === 0) {
          console.warn('⚠️ No room info from API')
          return null
        }

        // API trả về array, lấy phần tử đầu tiên
        const data = rs.data[0]

        // Parse polygon từ API (có thể là string JSON hoặc array)
        let polygon = null
        if (data.KHONGGIAN) {
          polygon = typeof data.KHONGGIAN === 'string' ? JSON.parse(data.KHONGGIAN) : data.KHONGGIAN

          // Normalize polygon về tọa độ bắt đầu từ (0,0)
          if (polygon && Array.isArray(polygon) && polygon.length > 0) {
            const xs = polygon.map((p) => p[0])
            const ys = polygon.map((p) => p[1])
            const minX = Math.min(...xs)
            const minY = Math.min(...ys)

            // Chuyển tất cả điểm về tọa độ tương đối từ (0,0)
            polygon = polygon.map((p) => [p[0] - minX, p[1] - minY])
          }
        }

        return {
          id: data.ID,
          name: data.TENTAT || data.TEN,
          polygon: polygon
        }
      } catch (error) {
        console.error('❌ Error fetching room info:', error)
        return null
      }
    },

    async getUninstalledRacks() {
      try {
        const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/get-ds-rack-chua-lapdat', {
          id: this.modalItemID
        })

        if (!rs || !rs.data || !Array.isArray(rs.data)) {
          console.warn('⚠️ No uninstalled racks from API, using mock data')
        }

        return rs.data.map((item) => ({
          id: 'node_' + String(item.ID || item.id || ''),
          name: String(item.TENTAT || item.TEN || item.name || 'Unknown'),
          type: item.LOAI || item.type || 'VNPT-IT',
          totalU: Number(item.SO_U || item.totalU || 42),
          usedU: Number(item.SO_U_SD || item.usedU || 0),
          // HUONG = 1: Ngang (width = RONG, height = DAI)
          // HUONG = 0: Dọc (width = DAI, height = RONG)
          width: Number(item.HUONG == 1 ? item.RONG : item.DAI || 60),
          height: Number(item.HUONG == 1 ? item.DAI : item.RONG || 60),
          x: Number(item.TOADO_X || 0),
          y: Number(item.TOADO_Y || 0),
          direction: item.HUONG,
          color: item.MA_MAU || '#2196F3' // Lấy màu từ MA_MAU
        }))
      } catch (error) {
        console.error('❌ Error fetching uninstalled racks:', error)
      }
    },

    async getInstalledRacks() {
      try {
        if (!this.modalItemID) {
          console.warn('⚠️ No modalItemID, using mock installed racks')
        }

        const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/get-ds-rack-da-lapdat', {
          id: this.modalItemID
        })

        if (!rs || !rs.data || !Array.isArray(rs.data)) {
          console.warn('⚠️ No installed racks from API')
          return []
        }

        return rs.data.map((item) => ({
          id: 'node_' + String(item.ID || item.id || ''),
          name: String(item.TENTAT || item.TEN || item.name || 'Unknown'),
          type: item.LOAI || item.type || 'VNPT-IT',
          totalU: Number(item.SO_U || item.totalU || 42),
          usedU: Number(item.SO_U_SD || item.usedU || 0),
          // HUONG = 1: Ngang (width = RONG, height = DAI)
          // HUONG = 0: Dọc (width = DAI, height = RONG)
          width: Number(item.HUONG == 1 ? item.RONG : item.DAI || 60),
          height: Number(item.HUONG == 1 ? item.DAI : item.RONG || 60),
          x: Number(item.TOADO_X || 0),
          y: Number(item.TOADO_Y || 0),
          direction: item.HUONG,
          longQuayId: item.LONGQUAY_ID,
          cageId: item.LONGQUAY_ID ? 'cage_' + String(item.LONGQUAY_ID) : null,
          // Thêm trường z-level để phân biệt các rack chồng lên (nếu có trong DB)
          zLevel: Number(item.TANG || item.Z_LEVEL || 0),
          color: item.MA_MAU || '#2196F3' // Lấy màu từ MA_MAU
        }))
      } catch (error) {
        console.error('❌ Error fetching installed racks:', error)
        return []
      }
    },

    // ========== STACKED RACKS MANAGEMENT ==========

    /**
     * Xây dựng map các rack chồng lên nhau theo vị trí
     * Key: "x,y" (làm tròn tọa độ)
     * Value: Array các rack tại vị trí đó (sắp xếp theo zLevel)
     */
    buildStackedRacksMap() {
      this.stackedRacksMap = {}

      // Chỉ xét các rack đã lắp đặt
      this.installedRacks.forEach((rack) => {
        const posKey = `${Math.round(rack.x)},${Math.round(rack.y)}`

        if (!this.stackedRacksMap[posKey]) {
          this.stackedRacksMap[posKey] = []
        }

        this.stackedRacksMap[posKey].push(rack)
      })

      // Sắp xếp các rack trong mỗi vị trí theo zLevel (từ thấp đến cao)
      Object.keys(this.stackedRacksMap).forEach((posKey) => {
        this.stackedRacksMap[posKey].sort((a, b) => {
          const aLevel = a.zLevel || 0
          const bLevel = b.zLevel || 0
          return aLevel - bLevel
        })
      })

      // Log để debug
      const stackedPositions = Object.keys(this.stackedRacksMap).filter((key) => this.stackedRacksMap[key].length > 1)

      if (stackedPositions.length > 0) {
        console.log(`📦 Found ${stackedPositions.length} positions with stacked racks:`)
        stackedPositions.forEach((pos) => {
          const racks = this.stackedRacksMap[pos]
          console.log(`  - Position ${pos}: ${racks.length} racks`)
          racks.forEach((r, idx) => {
            console.log(`    ${idx + 1}. ${r.name} (Level ${r.zLevel || 0}, ${r.totalU}U)`)
          })
        })
      } else {
        console.log('📦 No stacked racks found')
      }
    },

    /**
     * Lấy danh sách rack tại một vị trí cụ thể
     */
    getStackedRacksAtPosition(x, y) {
      const posKey = `${Math.round(x)},${Math.round(y)}`
      return this.stackedRacksMap[posKey] || []
    },

    /**
     * Kiểm tra xem một vị trí có rack chồng lên không
     */
    hasStackedRacks(x, y) {
      const racks = this.getStackedRacksAtPosition(x, y)
      return racks.length > 1
    },

    /**
     * Xây dựng HTML cho modal chọn rack từ danh sách rack chồng lên
     */
    buildStackedRacksSelectionHtml(racks) {
      const rackItems = racks
        .map((rack, index) => {
          const bgColor = index % 2 === 0 ? '#f9f9f9' : '#ffffff'
          const usagePercent = rack.totalU > 0 ? Math.round((rack.usedU / rack.totalU) * 100) : 0
          const usageColor = usagePercent > 80 ? '#FF5722' : usagePercent > 60 ? '#FF9800' : '#4CAF50'
          const zLevel = rack.zLevel || 0
          const levelBadge =
            zLevel > 0
              ? `<span style="
          background: #FF5722;
          color: white;
          font-size: 10px;
          font-weight: bold;
          padding: 2px 8px;
          border-radius: 12px;
          margin-left: 8px;
        ">Tầng ${zLevel}</span>`
              : ''

          return `
          <div style="
            padding: 12px;
            background: ${bgColor};
            border-left: 4px solid #2196F3;
            margin-bottom: 8px;
            cursor: pointer;
            transition: all 0.2s;
            border-radius: 4px;
          "
          onclick="window.showRackDetailById_${rack.id.replace('node_', '')}()"
          onmouseover="this.style.background='#e3f2fd'; this.style.borderLeftColor='#1976D2'"
          onmouseout="this.style.background='${bgColor}'; this.style.borderLeftColor='#2196F3'">
            <div style="display: flex; justify-content: space-between; align-items: center;">
              <div style="flex: 1;">
                <div style="font-weight: bold; font-size: 14px; color: #333; margin-bottom: 4px;">
                  <i class="fa fa-server"></i> ${rack.name}
                  ${levelBadge}
                </div>
                <div style="font-size: 12px; color: #666;">
                  <span style="margin-right: 15px;">
                    <i class="fa fa-tag"></i> ${rack.type}
                  </span>
                  <span style="margin-right: 15px;">
                    <i class="fa fa-arrows-v"></i> ${rack.totalU}U
                  </span>
                  <span>
                    <i class="fa fa-chart-bar"></i> Sử dụng:
                    <strong style="color: ${usageColor}">${rack.usedU}/${rack.totalU}U (${usagePercent}%)</strong>
                  </span>
                </div>
              </div>
              <div style="margin-left: 15px;">
                <i class="fa fa-chevron-right" style="color: #2196F3;"></i>
              </div>
            </div>
          </div>
        `
        })
        .join('')

      // Inject global functions để xử lý click
      racks.forEach((rack) => {
        const funcName = `showRackDetailById_${rack.id.replace('node_', '')}`
        window[funcName] = () => {
          this.showSingleRackDetail(rack)
        }
      })

      return `
        <div style="margin-top: 10px; max-height: 400px; overflow-y: auto;">
          <p style="color: #666; font-size: 13px; margin-bottom: 15px;">
            <i class="fa fa-info-circle"></i> Click vào rack để xem chi tiết:
          </p>
          ${rackItems}
        </div>
      `
    },

    /**
     * Hiển thị chi tiết một rack cụ thể (được gọi từ modal chọn)
     */
    showSingleRackDetail(rack) {
      console.log('Showing detail for rack:', rack)
      // Tìm node tương ứng trên diagram
      const diagram = this.$refs.diagramObj.ej2Instances
      console.log('Diagram', diagram.nodes)
      console.log('🔍 Finding node for rack ID:', rack.id)
      const node = diagram.nodes.find((n) => n.id === rack.id)

      if (!node) {
        this.$toast.error('Không tìm thấy rack trên sơ đồ')
        return
      }

      // Đóng modal hiện tại
      if (this.$swal) {
        this.$swal.close()
      }

      // Hiển thị chi tiết rack
      this.selectedRackDetail = {
        id: rack.id,
        name: rack.name,
        type: rack.type,
        totalU: rack.totalU,
        usedU: rack.usedU,
        width: rack.width,
        height: rack.height,
        x: rack.x,
        y: rack.y
      }

      this.$nextTick(() => {
        this.$refs.rackDetailModal.show()
      })
    },

    // ========== CAGE DATA LOADING ==========

    async getUninstalledCages() {
      try {
        const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/get-ds-longquay-chuaco-vitri', {
          id: this.modalItemID
        })

        return rs.data.map((item) => {
          let polygon = typeof item.KHONGGIAN === 'string' ? JSON.parse(item.KHONGGIAN) : item.KHONGGIAN

          // Normalize polygon về tọa độ tương đối (0,0 là góc trên trái)
          if (polygon && Array.isArray(polygon) && polygon.length > 0) {
            const xs = polygon.map((p) => p[0])
            const ys = polygon.map((p) => p[1])
            const minX = Math.min(...xs)
            const minY = Math.min(...ys)

            polygon = polygon.map((p) => [p[0] - minX, p[1] - minY])
          }
          return {
            id: 'cage_' + String(item.LONGQUAY_ID || item.id || ''),
            name: String(item.TENTAT || item.TEN || item.name || 'Unknown'),
            type: item.LOAI || item.type || 'Lồng quây',
            color: '#FF9800',
            polygon: polygon
          }
        })
      } catch (error) {
        console.error('❌ Error fetching uninstalled racks:', error)
      }
    },

    async getInstalledCages() {
      try {
        const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/get-ds-longquay-daco-vitri', {
          id: this.modalItemID
        })

        return rs.data.map((item) => {
          let polygon = typeof item.KHONGGIAN === 'string' ? JSON.parse(item.KHONGGIAN) : item.KHONGGIAN

          let x = 0
          let y = 0

          // Normalize polygon về tọa độ tương đối (0,0 là góc trên trái)
          if (polygon && Array.isArray(polygon) && polygon.length > 0) {
            const xs = polygon.map((p) => p[0])
            const ys = polygon.map((p) => p[1])
            const minX = Math.min(...xs)
            const minY = Math.min(...ys)

            // Chuyển polygon về tọa độ tương đối
            polygon = polygon.map((p) => [p[0] - minX, p[1] - minY])

            // Nếu API có TOADO_X, TOADO_Y (kể cả giá trị 0) thì ưu tiên dùng
            // Nếu không có (null/undefined) thì dùng minX, minY từ polygon
            if (item.TOADO_X != null) {
              x = Number(item.TOADO_X)
            } else {
              x = minX
            }

            if (item.TOADO_Y != null) {
              y = Number(item.TOADO_Y)
            } else {
              y = minY
            }
          } else {
            // Không có polygon hợp lệ, dùng TOADO_X/Y từ DB
            x = Number(item.TOADO_X || 0)
            y = Number(item.TOADO_Y || 0)
          }

          return {
            id: 'cage_' + String(item.LONGQUAY_ID || item.id || ''),
            name: String(item.TENTAT || item.TEN || item.name || 'Unknown'),
            type: item.LOAI || item.type || 'Lồng quây',
            color: '#FF9800',
            polygon: polygon,
            x: x,
            y: y
          }
        })
      } catch (error) {
        console.error('❌ Error fetching installed cages:', error)
        return []
      }
    },

    // ========== AISLE DATA LOADING ==========

    async getUninstalledAisles() {
      try {
        const rs = await this.$root.context.post(
          '/web-ecms/tainguyen-idc/khong-gian-hanh-lang/get-ds-hanh-lang-chuaco-vitri',
          {
            id: this.modalItemID
          }
        )

        return rs.data.map((item) => {
          let polygon = typeof item.KHONGGIAN === 'string' ? JSON.parse(item.KHONGGIAN) : item.KHONGGIAN

          if (polygon && Array.isArray(polygon) && polygon.length > 0) {
            const xs = polygon.map((p) => p[0])
            const ys = polygon.map((p) => p[1])
            const minX = Math.min(...xs)
            const minY = Math.min(...ys)

            polygon = polygon.map((p) => [p[0] - minX, p[1] - minY])
          }

          // Xác định màu dựa vào trường NONG: 1 = Nóng (đỏ), 0 = Lạnh (xanh)
          let color = '#2196F3' // Mặc định xanh lạnh
          let type = 'cold' // Mặc định lạnh

          if (item.NONG === 1) {
            color = '#FF5252' // Đỏ cho hành lang nóng
            type = 'hot'
          } else if (item.NONG === 0) {
            color = '#42A5F5' // Xanh cho hành lang lạnh
            type = 'cold'
          }
          return {
            id: 'aisle_' + String(item.HANHLANG_ID || item.id || ''),
            name: String(item.TENTAT || item.TEN || item.name || 'Unknown'),
            type: type,
            color: color,
            polygon: polygon,
            isHot: item.NONG === 1
          }
        })
      } catch (error) {
        console.error('❌ Error fetching uninstalled aisles:', error)
        return []
      }
    },

    async getInstalledAisles() {
      try {
        const rs = await this.$root.context.post(
          '/web-ecms/tainguyen-idc/khong-gian-hanh-lang/get-ds-hanh-lang-daco-vitri',
          {
            id: this.modalItemID
          }
        )

        return rs.data.map((item) => {
          let polygon = typeof item.KHONGGIAN === 'string' ? JSON.parse(item.KHONGGIAN) : item.KHONGGIAN
          let x = 0
          let y = 0

          // Normalize polygon về tọa độ tương đối (0,0 là góc trên trái)
          if (polygon && Array.isArray(polygon) && polygon.length > 0) {
            const xs = polygon.map((p) => p[0])
            const ys = polygon.map((p) => p[1])
            const minX = Math.min(...xs)
            const minY = Math.min(...ys)

            // Chuyển polygon về tọa độ tương đối
            polygon = polygon.map((p) => [p[0] - minX, p[1] - minY])

            // Nếu API có TOADO_X, TOADO_Y (kể cả giá trị 0) thì ưu tiên dùng
            // Nếu không có (null/undefined) thì dùng minX, minY từ polygon
            if (item.TOADO_X != null) {
              x = Number(item.TOADO_X)
            } else {
              x = minX
            }

            if (item.TOADO_Y != null) {
              y = Number(item.TOADO_Y)
            } else {
              y = minY
            }
          } else {
            // Không có polygon hợp lệ, dùng TOADO_X/Y từ DB
            x = Number(item.TOADO_X || 0)
            y = Number(item.TOADO_Y || 0)
          }

          // Xác định màu dựa vào trường NONG: 1 = Nóng (đỏ), 0 = Lạnh (xanh)
          let color = '#2196F3' // Mặc định xanh lạnh
          let type = 'cold' // Mặc định lạnh

          if (item.NONG === 1) {
            color = '#FF5252' // Đỏ cho hành lang nóng
            type = 'hot'
          } else if (item.NONG === 0) {
            color = '#42A5F5' // Xanh cho hành lang lạnh
            type = 'cold'
          }

          return {
            id: 'aisle_' + String(item.HANHLANG_ID || item.id || ''),
            name: String(item.TENTAT || item.TEN || item.name || 'Unknown'),
            type: type,
            color: color,
            polygon: polygon,
            x: x,
            y: y,
            isHot: item.NONG === 1
          }
        })
      } catch (error) {
        console.error('❌ Error fetching installed aisles:', error)
        return []
      }
    },

    // ========== EQUIPMENT DATA LOADING ==========

    async getUninstalledEquipments() {
      try {
        const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/get-ds-thietbi-chuaco-vitri', {
          id: this.modalItemID
        })

        if (!rs || !rs.data || !Array.isArray(rs.data)) {
          console.warn('⚠️ No uninstalled device from API, using mock data')
        }

        return rs.data.map((item) => ({
          id: 'node_' + String(item.ID || item.id || ''),
          name: String(item.TENTAT || item.TEN || item.name || 'Unknown'),
          type: item.PHANLOAI || item.type || 'KHAC',
          totalU: Number(item.SO_U || item.totalU || 42),
          usedU: 0,
          width: 60,
          height: 60
        }))
      } catch (error) {
        console.error('❌ Error fetching uninstalled racks:', error)
      }
    },

    async getInstalledEquipments() {
      try {
        if (!this.modalItemID) {
          console.warn('⚠️ No modalItemID, using mock installed equipments')
        }

        const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/get-ds-thietbi-daco-vitri', {
          id: this.modalItemID
        })

        if (!rs || !rs.data || !Array.isArray(rs.data)) {
          console.warn('⚠️ No installed device from API, using mock data')
        }

        return rs.data.map((item) => ({
          id: 'node_' + String(item.ID || item.id || ''),
          name: String(item.TENTAT || item.TEN || item.name || 'Unknown'),
          totalU: Number(item.SO_U || item.totalU || 42),
          usedU: 0,
          width: 60,
          height: 60,
          x: Number(item.TOADO_X || 0),
          y: Number(item.TOADO_Y || 0),
          longQuayId: item.LONGQUAY_ID,
          cageId: item.LONGQUAY_ID ? 'cage_' + String(item.LONGQUAY_ID) : null,
          type: item.PHANLOAI || item.type || 'KHAC'
        }))

        return []
      } catch (error) {
        console.error('❌ Error fetching installed equipments:', error)
        return []
      }
    },

    async getInstalledFans() {
      try {
        const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khonggian/get-ds-doituong-dalapdat', {
          loaiDoiTuong: 'QUAT',
          doiTuongChaId: this.modalItemID
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
        const rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khonggian/get-ds-doituong-dalapdat', {
          loaiDoiTuong: 'COT',
          doiTuongChaId: this.modalItemID
        })

        if (rs.error_code === 'BSS-00000000' && rs.data.result) {
          return rs.data.result.map(item => {
            // Parse KHONGGIAN làm polygon (giống ModalQuanLyQuatCot)
            let polygon = item.KHONGGIAN || item.POLYGON || []
            if (typeof polygon === 'string') {
              try {
                polygon = JSON.parse(polygon)
              } catch (e) {
                console.error('❌ Error parsing KHONGGIAN:', e)
                polygon = []
              }
            }

            return {
              id: item.ID || item.id,
              name: item.TEN || '',
              tenTat: item.TENTAT || '',
              x: item.TOADO_X || 0,
              y: item.TOADO_Y || 0,
              ghiChu: item.GHICHU || '',
              moTa: item.MOTA || '',
              polygon: polygon
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

    // ========== DRAW CAGE POLYGON HANDLERS ==========

    async onCagePolygonConfirm(result) {
      const diagram = this.$refs.diagramObj.ej2Instances

      // Kiểm tra xem đang edit hay tạo mới
      const isEditMode = !!this._editingCageNode
      if (isEditMode) {
        // MODE: EDIT CAGE
        const node = this._editingCageNode
        const originalPolygon = result.polygon

        // Kiểm tra polygon cũ và mới
        const oldPolygon = node.addInfo.polygon.map((p) => [
          p[0] + node.offsetX,
          p[1] + node.offsetY
        ])

        // Tính bounds của polygon cũ
        const oldXs = oldPolygon.map((p) => p[0])
        const oldYs = oldPolygon.map((p) => p[1])
        const oldMinX = Math.min(...oldXs)
        const oldMaxX = Math.max(...oldXs)
        const oldMinY = Math.min(...oldYs)
        const oldMaxY = Math.max(...oldYs)
        const oldWidth = oldMaxX - oldMinX
        const oldHeight = oldMaxY - oldMinY

        // Tính bounds của polygon mới
        const newXs = originalPolygon.map((p) => p[0])
        const newYs = originalPolygon.map((p) => p[1])
        const newMinX = Math.min(...newXs)
        const newMaxX = Math.max(...newXs)
        const newMinY = Math.min(...newYs)
        const newMaxY = Math.max(...newYs)
        const newWidth = newMaxX - newMinX
        const newHeight = newMaxY - newMinY

        // So sánh kích thước (cho phép sai số 5px để tránh thay đổi do làm tròn khi vẽ)
        const tolerance = 5
        const widthChanged = Math.abs(oldWidth - newWidth) >= tolerance
        const heightChanged = Math.abs(oldHeight - newHeight) >= tolerance
        const positionChanged = Math.abs(oldMinX - newMinX) >= tolerance || Math.abs(oldMinY - newMinY) >= tolerance

        // So sánh từng điểm polygon
        const shapeChanged = oldPolygon.length !== originalPolygon.length ||
          !oldPolygon.every((p, i) => {
            const dx = Math.abs(p[0] - originalPolygon[i][0])
            const dy = Math.abs(p[1] - originalPolygon[i][1])
            return dx < tolerance && dy < tolerance
          })

        if (!widthChanged && !heightChanged && !positionChanged && !shapeChanged) {
          this._editingCageNode = null
          return
        }

        // Helper: Kiểm tra điểm có nằm trong polygon không
        const isPointInPolygonHelper = (point, polygon) => {
          const tolerance = 5

          const xs = polygon.map((p) => p[0])
          const ys = polygon.map((p) => p[1])
          const minX = Math.min(...xs) - tolerance
          const maxX = Math.max(...xs) + tolerance
          const minY = Math.min(...ys) - tolerance
          const maxY = Math.max(...ys) + tolerance

          if (point[0] >= minX && point[0] <= maxX && point[1] >= minY && point[1] <= maxY) {
            let inside = false
            for (let i = 0, j = polygon.length - 1; i < polygon.length; j = i++) {
              const xi = polygon[i][0]
              const yi = polygon[i][1]
              const xj = polygon[j][0]
              const yj = polygon[j][1]

              const intersect = yi > point[1] !== yj > point[1] && point[0] < ((xj - xi) * (point[1] - yi)) / (yj - yi) + xi

              if (intersect) inside = !inside
            }

            if (!inside) {
              for (let i = 0; i < polygon.length; i++) {
                const j = (i + 1) % polygon.length
                const dist = distanceToSegment(point, polygon[i], polygon[j])
                if (dist <= tolerance) {
                  return true
                }
              }
            }

            return inside
          }

          return false
        }

        const distanceToSegment = (point, p1, p2) => {
          const [x, y] = point
          const [x1, y1] = p1
          const [x2, y2] = p2

          const A = x - x1
          const B = y - y1
          const C = x2 - x1
          const D = y2 - y1

          const dot = A * C + B * D
          const lenSq = C * C + D * D
          let param = -1

          if (lenSq !== 0) param = dot / lenSq

          let xx, yy

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

          const dx = x - xx
          const dy = y - yy

          return Math.sqrt(dx * dx + dy * dy)
        }

        // Kiểm tra items trong cage
        const itemsOutsideCage = []
        const childNodes = diagram.nodes.filter(
          (n) => n.addInfo && n.addInfo.parentCageId === node.id
        )

        for (const childNode of childNodes) {
          const itemType = childNode.addInfo.isRack ? 'Rack' : 'Thiết bị'
          const itemName = childNode.addInfo.rackName || childNode.addInfo.equipmentName || 'Không rõ tên'

          const corners = [
            [childNode.offsetX, childNode.offsetY],
            [childNode.offsetX + childNode.width, childNode.offsetY],
            [childNode.offsetX, childNode.offsetY + childNode.height],
            [childNode.offsetX + childNode.width, childNode.offsetY + childNode.height]
          ]

          const allCornersInside = corners.every((corner) => isPointInPolygonHelper(corner, originalPolygon))

          if (!allCornersInside) {
            itemsOutsideCage.push(`${itemType}: ${itemName}`)
          }
        }

        if (itemsOutsideCage.length > 0) {
          const itemsList = itemsOutsideCage.join('\n- ')
          this.$toast.error(
            `Không thể thu nhỏ lồng quây vì các thiết bị/rack sau nằm ngoài vùng mới:\n- ${itemsList}\n\nVui lòng di chuyển hoặc xóa các thiết bị/rack này trước khi thay đổi kích thước lồng quây.`,
            { duration: 8000 }
          )
          this._editingCageNode = null
          return
        }

        // Polygon có thay đổi và hợp lệ → Gọi API update
        try {
          let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/update-toado-longquay', {
            longQuayID: result.cageData.id.replace('cage_', ''),
            dsToaDo: result.polygon.map((p) => ({ toaDoX: p[0], toaDoY: p[1] }))
          })
          if (rs.error_code == 'BSS-00000000') {
            this.handleEditCageConfirm(result)
          }
        } catch (e) {
          console.error(e)
        }
      } else {
        try {
          let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian/insert-toado-longquay', {
            longQuayID: result.cageData.id.replace('cage_', ''),
            dsToaDo: result.polygon.map((p) => ({ toaDoX: p[0], toaDoY: p[1] }))
          })
          if (rs.error_code == 'BSS-00000000') {
            this.handleCreateCageConfirm(result)
          }
        } catch (e) {
          console.error(e)
        }
      }
    },

    // Xử lý tạo cage mới
    handleCreateCageConfirm(result) {
      const diagram = this.$refs.diagramObj.ej2Instances

      if (!this._pendingCageData) {
        console.error('❌ No pending cage data')
        return
      }

      const cage = this._pendingCageData.cage
      const dropPosition = this._pendingCageData.dropPosition

      // Polygon từ modal có tọa độ tuyệt đối (theo room)
      // Cần chuyển về tọa độ tương đối (bắt đầu từ 0,0)
      const originalPolygon = result.polygon

      // Tính bounds của polygon
      const xs = originalPolygon.map((p) => p[0])
      const ys = originalPolygon.map((p) => p[1])
      const minX = Math.min(...xs)
      const maxX = Math.max(...xs)
      const minY = Math.min(...ys)
      const maxY = Math.max(...ys)
      const width = maxX - minX
      const height = maxY - minY

      // Chuyển polygon về tọa độ tương đối (0,0 là góc trên trái)
      const relativePolygon = originalPolygon.map((p) => [p[0] - minX, p[1] - minY])

      // Cập nhật polygon cho cage (dùng tọa độ tương đối)
      cage.polygon = relativePolygon

      // Tạo cage node với polygon mới
      const cageNode = this.createCageNode(cage)

      // 🎯 TÌM VỊ TRÍ HỢP LỆ TRONG PHÒNG (giống logic chuyển rack vào cage)
      const foundPosition = this.findValidPositionInRoom(width, height)

      if (!foundPosition) {
        // Fallback về vị trí giữa phòng nếu không tìm được vị trí hợp lệ
        const bounds = this.getPolygonBounds()
        foundPosition = {
          x: bounds.minX + (bounds.width - width) / 2,
          y: bounds.minY + (bounds.height - height) / 2
        }
        console.warn('⚠️ Could not find valid position, using center as fallback')
      }

      // Tạo node mới với vị trí đã tìm được
      cageNode.offsetX = foundPosition.x
      cageNode.offsetY = foundPosition.y
      cageNode.pivot = { x: 0, y: 0 }

      // Đảm bảo addInfo.children được khởi tạo
      if (!cageNode.addInfo.children) {
        cageNode.addInfo.children = []
      }

      // Add vào diagram
      diagram.add(cageNode)

      // Remove from palette
      const index = this.uninstalledCages.findIndex((c) => c.id === cage.id)
      if (index !== -1) {
        this.uninstalledCages.splice(index, 1)
      }

      // Clear pending data
      this._pendingCageData = null

      this.$toast.success(`Đã tạo ${cage.name} với polygon tùy chỉnh tại vị trí tối ưu`)
    },

    // Xử lý chỉnh sửa cage
    handleEditCageConfirm(result) {
      const diagram = this.$refs.diagramObj.ej2Instances

      if (!this._editingCageNode) {
        console.error('❌ No editing cage node')
        return
      }

      const node = this._editingCageNode
      const originalPolygon = result.polygon

      // Tính bounds của polygon mới
      const xs = originalPolygon.map((p) => p[0])
      const ys = originalPolygon.map((p) => p[1])
      const minX = Math.min(...xs)
      const maxX = Math.max(...xs)
      const minY = Math.min(...ys)
      const maxY = Math.max(...ys)
      const width = maxX - minX
      const height = maxY - minY

      // Chuyển polygon về tọa độ tương đối
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
      node.shape.data = pathData
      node.addInfo.polygon = relativePolygon

      // Cập nhật annotation (tên cage)
      if (node.annotations && node.annotations.length > 0) {
        node.annotations[0].content = node.addInfo.cageName
      }

      // Update diagram
      diagram.dataBind()

      // Clear editing node
      this._editingCageNode = null

      this.$toast.success(`Đã cập nhật ${node.addInfo.cageName}`)
    },

    onCagePolygonCancel() {
      this._pendingCageData = null
      this._editingCageNode = null
    },

    // ========== DRAW AISLE POLYGON HANDLERS ==========

    async onAislePolygonConfirm(result) {
      const isEditMode = !!this._editingAisleNode
      if (isEditMode) {
        // MODE: EDIT AISLE
        // Trong edit mode, result.aisleData = { id, name, type, color }
        try {
          let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian-hanh-lang/update-toado-hanhlang', {
            hanhLangID: result.aisleData.id.replace('aisle_', ''),
            dsToaDo: result.polygon.map((p) => ({ toaDoX: p[0], toaDoY: p[1] }))
          })
          if (rs.error_code == 'BSS-00000000') {
            this.handleEditAisleConfirm(result)
          }
        } catch (e) {
          console.error(e)
        }
      } else {
        // MODE: CREATE AISLE
        // Trong create mode, result.aisleData = { aisle: {...} }
        try {
          let rs = await this.$root.context.post('/web-ecms/tainguyen-idc/khong-gian-hanh-lang/insert-toado-hanhlang', {
            hanhLangID: result.aisleData.aisle.id.replace('aisle_', ''),
            dsToaDo: result.polygon.map((p) => ({ toaDoX: p[0], toaDoY: p[1] }))
          })
          if (rs.error_code == 'BSS-00000000') {
            this.handleCreateAisleConfirm(result)
          }
        } catch (e) {
          console.error(e)
        }
      }
    },

    // Xử lý tạo aisle mới
    handleCreateAisleConfirm(result) {
      const diagram = this.$refs.diagramObj.ej2Instances

      // Lấy aisle từ result.aisleData (từ modal)
      if (!result.aisleData || !result.aisleData.aisle) {
        console.error('❌ No aisle data in result')
        return
      }

      const aisle = result.aisleData.aisle

      // Polygon từ modal có tọa độ tuyệt đối (theo room)
      // Cần chuyển về tọa độ tương đối (bắt đầu từ 0,0)
      const originalPolygon = result.polygon

      // Tính bounds của polygon
      const xs = originalPolygon.map((p) => p[0])
      const ys = originalPolygon.map((p) => p[1])
      const minX = Math.min(...xs)
      const maxX = Math.max(...xs)
      const minY = Math.min(...ys)
      const maxY = Math.max(...ys)
      const width = maxX - minX
      const height = maxY - minY

      // Chuyển polygon về tọa độ tương đối (0,0 là góc trên trái)
      const relativePolygon = originalPolygon.map((p) => [p[0] - minX, p[1] - minY])

      // Cập nhật polygon cho aisle (dùng tọa độ tương đối)
      aisle.polygon = relativePolygon

      // FIX BUG 1: Cập nhật polygon trong backup để palette refresh đúng
      const aisleBackupIndex = this.allAislesBackup.findIndex((a) => a.id === aisle.id)
      if (aisleBackupIndex !== -1) {
        this.allAislesBackup[aisleBackupIndex].polygon = relativePolygon
      }

      // Tạo aisle node với polygon mới
      const aisleNode = this.createAisleNode(aisle)

      // 🎯 TÌM VỊ TRÍ HỢP LỆ TRONG PHÒNG (giống logic chuyển rack vào cage)
      const foundPosition = this.findValidPositionInRoom(width, height)

      if (!foundPosition) {
        // Fallback về vị trí giữa phòng nếu không tìm được vị trí hợp lệ
        const bounds = this.getPolygonBounds()
        foundPosition = {
          x: bounds.minX + (bounds.width - width) / 2,
          y: bounds.minY + (bounds.height - height) / 2
        }
        console.warn('⚠️ Could not find valid position for aisle, using center as fallback')
      }

      // Đặt vị trí đã tìm được
      aisleNode.offsetX = foundPosition.x
      aisleNode.offsetY = foundPosition.y
      aisleNode.pivot = { x: 0, y: 0 }

      // Add node vào diagram
      diagram.add(aisleNode)

      // Remove from uninstalledAisles
      const index = this.uninstalledAisles.findIndex((a) => a.id === aisle.id)
      if (index !== -1) {
        this.uninstalledAisles.splice(index, 1)
      }

      // Clear pending data
      this._pendingAisleData = null

      this.$toast.success(`Đã tạo ${aisle.name} với polygon tùy chỉnh tại vị trí tối ưu`)
    },

    // Xử lý chỉnh sửa aisle
    handleEditAisleConfirm(result) {
      const diagram = this.$refs.diagramObj.ej2Instances

      if (!this._editingAisleNode) {
        console.error('❌ No editing aisle node')
        return
      }

      const node = this._editingAisleNode
      const originalPolygon = result.polygon

      // Tính bounds của polygon mới
      const xs = originalPolygon.map((p) => p[0])
      const ys = originalPolygon.map((p) => p[1])
      const minX = Math.min(...xs)
      const maxX = Math.max(...xs)
      const minY = Math.min(...ys)
      const maxY = Math.max(...ys)
      const width = maxX - minX
      const height = maxY - minY

      // Chuyển polygon về tọa độ tương đối
      const relativePolygon = originalPolygon.map((p) => [p[0] - minX, p[1] - minY])

      // Xác định góc xoay dựa trên tỷ lệ width/height
      const isVertical = height > width
      const rotateAngle = isVertical ? 90 : 0

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
      node.shape.data = pathData
      node.addInfo.polygon = relativePolygon

      // Cập nhật annotation (tên aisle) với vị trí chính giữa và xoay
      if (node.annotations && node.annotations.length > 0) {
        node.annotations[0].content = `${this.getAisleIcon(node.addInfo.aisleType)} ${node.addInfo.aisleName}`
        node.annotations[0].offset = { x: 0.5, y: 0.5 }
        node.annotations[0].horizontalAlignment = 'Center'
        node.annotations[0].verticalAlignment = 'Center'
        node.annotations[0].rotateAngle = rotateAngle
        node.annotations[0].margin = { left: 0, top: 0 }
      }

      // Update diagram
      diagram.dataBind()

      // Clear editing node
      this._editingAisleNode = null

      this.$toast.success(`Đã cập nhật ${node.addInfo.aisleName}`)
    },

    onAislePolygonCancel() {
      this._pendingAisleData = null
      this._editingAisleNode = null
    },

    handleModalClose() {
      // Clear any pending or editing data
      this._pendingCageData = null
      this._editingCageNode = null
      this._pendingAisleData = null
      this._editingAisleNode = null
    }
  },

  beforeDestroy() { }
}
</script>

<style scoped>
.page-content {
  padding: 0px;
  height: 100%;
  position: relative;
}

.diagram-workspace {
  display: flex;
  height: 100%;
  min-height: 600px;
  width: 100%;
  background-color: #fff;
  border: 1px solid #d9d9d9;
  overflow: hidden;
  -webkit-user-select: none;
  /* Cho Chrome, Safari, Opera */
  -moz-user-select: none;
  /* Cho Firefox */
  -ms-user-select: none;
  /* Cho IE/Edge */
  user-select: none;
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
  /* Fix flex overflow */
}

/* Disabled button style */
.list-actions-top .list li a.disabled {
  opacity: 0.5;
  cursor: not-allowed;
  pointer-events: none;
}
</style>

<style>
.modal-edit-phong {
  .modal-dialog {
    max-width: 90vw !important;
    width: 90vw !important;
  }

  .modal-body {
    padding: 0 !important;
  }

  .list-actions-top {
    position: relative !important;
    width: 100% !important;
    left: 0 !important;
    top: 0 !important;
  }
}

/* Diagram global styles */
#diagram {
  user-select: none !important;
  width: 100% !important;
  height: 100% !important;
}

#diagram .e-diagram-canvas {
  background: #fafafa !important;
}

.e-dragclone {
  z-index: 99999 !important;
  pointer-events: none !important;
}

/* Symbol palette styles */
#symbolpalette {
  width: 100% !important;
  height: 100% !important;
}

.e-symbolpalette .e-symbol-draggable {
  cursor: grab !important;
}

.e-symbolpalette .e-symbol-draggable:active {
  cursor: grabbing !important;
}

/* Symbol grid layout */
.palette-container .e-symbolpalette .e-symbol-palette {
  padding: 10px !important;
}

.palette-container .e-symbolpalette .e-symbol-palette .e-symbol-draggable {
  margin: 8px !important;
  display: inline-block !important;
}

/* Remove default borders */
.palette-container .e-symbolpalette .e-accordion {
  border: 0px !important;
}

/* Cage palette card styling */
.cage-palette-card {
  margin-top: -20px;
  margin-left: 10px;
  width: 70px;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  border: 2px dashed;
  border-radius: 8px;
  box-sizing: border-box;
  padding: 8px 6px;
  background: rgba(255, 255, 255, 0.5);
  transition: all 0.2s;
  min-height: 100px;
}

.cage-palette-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.cage-palette-card .cage-title {
  font-size: 11px;
  font-weight: 600;
  text-align: center;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 100%;
  line-height: 1.2;
}

.cage-palette-card .cage-icon {
  font-size: 32px;
  line-height: 1;
  flex-shrink: 0;
}

.cage-palette-card .cage-desc {
  font-size: 10px;
  color: #666;
  text-align: center;
}

.cage-palette-card .cage-badge {
  position: absolute;
  top: -8px;
  right: -8px;
  background: #ff5722;
  color: white;
  font-size: 9px;
  font-weight: bold;
  padding: 2px 6px;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  z-index: 10;
}

/* Rack palette card styling */
.rack-palette-card {
  margin-top: -15px;
  margin-left: 10px;
  width: 70px;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  border: 2px solid #2196f3;
  border-radius: 8px;
  box-sizing: border-box;
  padding: 8px 6px;
  background: white;
  transition: all 0.2s;
  min-height: 100px;
}

.rack-palette-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 8px rgba(33, 150, 243, 0.2);
}

.rack-palette-card .rack-title {
  font-size: 11px;
  font-weight: 600;
  text-align: center;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 100%;
  color: #333;
  line-height: 1.2;
}

.rack-palette-card .rack-icon {
  width: 50px;
  height: 50px;
  object-fit: contain;
  flex-shrink: 0;
}

.rack-palette-card .rack-desc {
  font-size: 11px;
  color: #2196f3;
  font-weight: 600;
  text-align: center;
}

/* Equipment palette card styling */
.equipment-palette-card {
  margin-top: -15px;
  margin-left: 10px;
  width: 70px;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  border: 2px solid #ff9800;
  border-radius: 8px;
  box-sizing: border-box;
  padding: 8px 6px;
  background: white;
  transition: all 0.2s;
  min-height: 100px;
}

.equipment-palette-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 8px rgba(255, 152, 0, 0.2);
}

.equipment-palette-card .equipment-title {
  font-size: 11px;
  font-weight: 600;
  text-align: center;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 100%;
  color: #333;
  line-height: 1.2;
}

.equipment-palette-card .equipment-icon {
  font-size: 32px;
  line-height: 1;
  flex-shrink: 0;
}

.equipment-palette-card .equipment-desc {
  font-size: 10px;
  color: #ff9800;
  font-weight: 600;
  text-align: center;
}

/* Aisle palette card styling */
.aisle-palette-card {
  margin-top: -20px;
  margin-left: 10px;
  width: 70px;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  border: 2px dashed;
  border-radius: 8px;
  box-sizing: border-box;
  padding: 8px 6px;
  background: rgba(255, 255, 255, 0.5);
  transition: all 0.2s;
  min-height: 100px;
}

.aisle-palette-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.aisle-palette-card .aisle-title {
  font-size: 11px;
  font-weight: 600;
  text-align: center;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 100%;
  line-height: 1.2;
}

.aisle-palette-card .aisle-icon {
  font-size: 32px;
  line-height: 1;
  flex-shrink: 0;
}

.aisle-palette-card .aisle-desc {
  font-size: 10px;
  color: #666;
  text-align: center;
}

.aisle-palette-card .aisle-badge {
  position: absolute;
  top: -8px;
  right: -8px;
  background: #2196f3;
  color: white;
  font-size: 9px;
  font-weight: bold;
  padding: 2px 6px;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  z-index: 10;
}

/* Stacked Racks Modal Styling */
::v-deep .stacked-racks-modal .swal2-popup {
  width: 600px !important;
  max-width: 90vw;
}

::v-deep .stacked-racks-modal .swal2-title {
  font-size: 18px !important;
  color: #2196f3 !important;
}

::v-deep .stacked-racks-modal .swal2-html-container {
  max-height: 500px;
  overflow-y: auto;
  padding: 0 !important;
  margin: 0 !important;
}

::v-deep .stacked-racks-modal .swal2-confirm {
  background-color: #2196f3 !important;
}

/* Stacked Rack Node Animation */
@keyframes pulse {

  0%,
  100% {
    transform: scale(1);
    box-shadow: 0 3px 8px rgba(255, 87, 34, 0.5);
  }

  50% {
    transform: scale(1.1);
    box-shadow: 0 4px 12px rgba(255, 87, 34, 0.8);
  }
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateX(-10px);
  }

  to {
    opacity: 1;
    transform: translateX(0);
  }
}

/* Fan icon styling */
.fa-fan {
  color: #2196F3;
}

/* Stacked Racks Modal Styling */
.stacked-racks-list {
  max-height: 400px;
  overflow-y: auto;
}

.rack-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  margin-bottom: 10px;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s ease;
  background: white;
}

.rack-item:hover {
  background: #f5f5f5;
  border-color: #2196f3;
  box-shadow: 0 2px 8px rgba(33, 150, 243, 0.2);
}

.rack-info {
  display: flex;
  align-items: center;
  gap: 15px;
  flex: 1;
}

.rack-number {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 32px;
  height: 32px;
  background-color: #2196f3;
  color: white;
  border-radius: 50%;
  font-weight: bold;
  font-size: 14px;
}

.rack-details {
  flex: 1;
}

.rack-name {
  font-weight: 600;
  font-size: 15px;
  color: #333;
  margin-bottom: 5px;
}

.rack-meta {
  display: flex;
  gap: 8px;
}

.rack-meta .badge {
  font-size: 11px;
  padding: 3px 8px;
}

.rack-item .fa-chevron-right {
  color: #999;
  font-size: 14px;
}
</style>
