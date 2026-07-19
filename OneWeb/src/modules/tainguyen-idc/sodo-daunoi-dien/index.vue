<template src="./index.html"></template>

<script>
import Vue from 'vue'
import {
  DiagramPlugin,
  SymbolPalettePlugin,
  HierarchicalTree,
  DataBinding,
  DiagramContextMenu,
  PrintAndExport,
  SnapConstraints,
  NodeConstraints,
  ConnectorConstraints,
  SelectorConstraints,
  PortVisibility,
  PortConstraints,
  UndoRedo,
  DiagramTools
} from '@syncfusion/ej2-vue-diagrams'
import ModalChiTietThietBi from '../sodo-phong-zone/components/chitiet-thietbi/index.vue'
import ChiTietKetNoiDienRack from '../chitiet-ketnoi-dien/index.vue'
import html2canvas from 'html2canvas'

// Constants
const DIAGRAM_CONFIG = {
  LAYOUT: {
    NODE_WIDTH: 120,
    NODE_HEIGHT: 60,
    HORIZONTAL_GAP: 200,
    VERTICAL_GAP: 150,
    START_X: 600,
    START_Y: 100  // Thêm padding để tránh bị cắt ở trên
  },
  CONTAINER: {
    PADDING: 30,
    SYSTEM_Z_INDEX: -2,
    ZONE_Z_INDEX: -2
  },
  ANIMATION: {
    FIT_DELAY: 300,
    CONTAINER_DRAW_DELAY: 300,
    POSITION_CHANGE_THRESHOLD: 0.1
  }
}

const MESSAGES = {
  SUCCESS: {
    DEVICE_ADDED: (name) => `Đã thêm ${name} vào sơ đồ`,
    DEVICE_REMOVED: (name) => `Đã xóa thiết bị "${name}" khỏi sơ đồ`,
    DEVICE_REMOVED_TO_PALETTE: (name) => `Đã xóa thiết bị "${name}" khỏi sơ đồ và đưa về palette`,
    CONNECTION_CREATED: (type, from, to) => `Đã tạo kết nối ${type} từ ${from} đến ${to}`,
    CONNECTION_DELETED: 'Đã xóa kết nối'
  },
  ERROR: {
    LOAD_FAILED: 'Không thể tải dữ liệu sơ đồ kết nối điện',
    LOAD_UNCONNECTED_FAILED: 'Không thể tải danh sách thiết bị chưa đấu nối',
    DIAGRAM_CREATE_FAILED: 'Không thể tạo sơ đồ. Vui lòng thử lại.',
    GENERAL_ERROR: 'Có lỗi xảy ra khi tải sơ đồ'
  },
  WARNING: {
    SELF_CONNECTION: 'Không thể tạo kết nối với chính nó!',
    DUPLICATE_CONNECTION: 'Kết nối đã tồn tại!'
  },
  INFO: {
    CONNECT_MODE_ON: (name) => `Đã chọn thiết bị nguồn: ${name}. Click vào thiết bị đích để tạo kết nối.`,
    CONNECT_MODE_CANCELLED: 'Đã hủy chế độ tạo kết nối'
  }
}

Vue.use(DiagramPlugin)
Vue.use(SymbolPalettePlugin)

export default {
  name: 'SoDoKetNoiDien',

  components: {
    ModalChiTietThietBi,
    ChiTietKetNoiDienRack,
    ModalChonLoaiKetNoi: () => import('./components/modals/ModalChonLoaiKetNoi.vue')
  },

  props: {
    idcId: {
      type: [String, Number],
      required: true
    }
  },

  provide: {
    diagram: [HierarchicalTree, DataBinding, DiagramContextMenu, PrintAndExport, UndoRedo]
  },

  data () {
    return {
      isDataLoaded: false,
      nodes: [],
      connectors: [],
      selectedThietBiId: null,
      selectedRackId: null,
      containersMap: {},
      containerOldPositions: {},

      // Debounced functions (will be initialized in created())
      debouncedDrawContainers: null,

      // Connection mode
      isConnectMode: false,
      sourceNode: null,
      dragConnectionMode: false,

      // Connection dialog
      connectionDialog: {
        sourceNode: null,
        targetNode: null,
        selectedType: 'NGUON_UPS',
        tempConnectorId: null
      },

      // Unconnected devices (thiết bị chưa đấu nối)
      unconnectedDevices: [],
      selectedItems: {
        constraints: SelectorConstraints.None
      },

      // Drawing object for connector - đường thẳng khi kéo
      drawingObject: null,

      // Tool settings - Cho phép vừa kéo sơ đồ vừa di chuyển node và kéo từ port
      tool: DiagramTools.Default,
      isPanMode: false,

      // Layout settings - Manual positioning
      layout: {
        type: 'None' // Tắt auto layout, tính thủ công
      },

      // Snap settings
      snapSettings: {
        constraints: SnapConstraints.None,
        snapAngle: 5,
        snapObjectDistance: 5
      },

      // Scroll settings
      scrollSettings: {
        scrollLimit: 'Diagram',  // Cho phép scroll trong vùng diagram
        minZoom: 0.2,
        maxZoom: 3,
        canAutoScroll: false,
        scrollableArea: {
          x: 0,
          y: 0,
          width: 3000,  // Tăng không gian scroll
          height: 2000
        }
      },

      // Context menu
      contextMenuSettings: {
        show: true,
        items: [
          {
            text: 'Xem chi tiết',
            id: 'viewDetail',
            target: '.e-elementcontent',
            iconCss: 'fa fa-eye'
          },
          { separator: true },
          {
            text: 'Kết nối đến thiết bị khác',
            id: 'connectTo',
            target: '.e-elementcontent',
            iconCss: 'fa fa-link'
          },
          { separator: true },
          {
            text: 'Xóa thiết bị khỏi sơ đồ',
            id: 'removeDevice',
            target: '.e-elementcontent',
            iconCss: 'fa fa-times-circle'
          },
          { separator: true },
          {
            text: 'Xóa kết nối',
            id: 'deleteConnection',
            target: '.e-diagramcontent',
            iconCss: 'fa fa-trash'
          }
        ],
        showCustomMenuOnly: true
      },

      // Màu sắc theo loại thiết bị
      deviceColors: {
        RPDU: '#FF6B6B', // Đỏ
        PDU: '#4ECDC4', // Xanh ngọc
        RACK: '#95E1D3', // Xanh nhạt
        UPS: '#FFE66D', // Vàng
        ATS: '#FFA07A', // Cam nhạt
        TRANSFORMER: '#DDA15E', // Nâu
        GENERATOR: '#BC6C25', // Nâu đậm
        SWITCH: '#6C757D', // Xám
        POWER_GRID: '#FF0000', // Đỏ - Điện lưới
        DEFAULT: '#E0E0E0' // Xám nhạt
      },

      // Device types for palette
      deviceTypes: [
        { type: 'RPDU', label: 'rPDU' },
        { type: 'PDU', label: 'PDU / MPDU' },
        { type: 'RACK', label: 'Rack' },
        { type: 'UPS', label: 'UPS' },
        { type: 'ATS', label: 'ATS' },
        { type: 'TRANSFORMER', label: 'Trạm biến áp' },
        { type: 'GENERATOR', label: 'Nguồn điện' },
        { type: 'SWITCH', label: 'Tủ phân phối' }
      ]
    }
  },

  computed: {
    palettes () {
      // Nhóm thiết bị theo loại
      const devicesByType = {}

      this.unconnectedDevices.forEach((device) => {
        if (!devicesByType[device.type]) {
          devicesByType[device.type] = []
        }
        devicesByType[device.type].push(device)
      })

      // Tạo palette cho từng loại thiết bị
      const palettes = []

      this.deviceTypes.forEach((deviceType) => {
        const devices = devicesByType[deviceType.type] || []
        if (devices.length > 0) {
          palettes.push({
            id: `palette_${deviceType.type}`,
            expanded: true,
            title: `${deviceType.label} (${devices.length})`,
            symbols: devices.map((device) => this.createDevicePaletteSymbol(device))
          })
        }
      })

      return palettes
    }
  },

  created () {
    // Initialize debounced functions
    this.debouncedDrawContainers = this.debounce(this.drawContainers, DIAGRAM_CONFIG.ANIMATION.CONTAINER_DRAW_DELAY)
  },

  beforeDestroy () {
    // Cleanup to prevent memory leaks
    this.containersMap = {}
    this.containerOldPositions = {}
    this.unconnectedDevices = []
    this.nodes = []
    this.connectors = []

    if (this.$refs.diagramObj) {
      const diagram = this.$refs.diagramObj.ej2Instances
      if (diagram) {
        diagram.clear()
      }
    }

    // Clear debounced functions
    if (this.debouncedDrawContainers) {
      this.debouncedDrawContainers = null
    }
  },

  async mounted () {
    // Khởi tạo drawingObject cho connector type Straight
    this.drawingObject = {
      type: 'Straight',
      connectorType: 'Straight',
      style: {
        strokeColor: '#2196F3',
        strokeWidth: 3
      },
      targetDecorator: {
        shape: 'None'
      },
      constraints: ConnectorConstraints.Default
    }

    // Load unconnected devices TRƯỚC
    await this.loadUnconnectedDevices()
    // Sau đó mới load diagram data
    await this.loadData()

    // Force refresh palette
    this.$nextTick(() => {
      if (this.$refs.symbolpalette) {
        console.log('✅ Palette ref found, refreshing...')
        const paletteObj = this.$refs.symbolpalette.ej2Instances
        if (paletteObj) {
          paletteObj.refresh()
        }
      }
    })
  },

  methods: {
    /**
     * Debounce utility function
     */
    debounce (func, wait) {
      let timeout
      return function executedFunction (...args) {
        const later = () => {
          clearTimeout(timeout)
          func.apply(this, args)
        }
        clearTimeout(timeout)
        timeout = setTimeout(later, wait)
      }
    },

    /**
     * Validate device data structure
     */
    validateDeviceData (device) {
      if (!device || typeof device !== 'object') {
        console.warn('Invalid device data: not an object', device)
        return false
      }
      if (!device.id || !device.name || !device.type) {
        console.warn('Invalid device data: missing required fields', device)
        return false
      }
      return true
    },

    /**
     * Check if node is a container
     */
    isContainerNode (node) {
      return node?.addInfo?.isContainer === true
    },

    /**
     * Check if device is inside container bounds
     */
    isDeviceInsideContainer (device, container) {
      const deviceW = device.width || DIAGRAM_CONFIG.LAYOUT.NODE_WIDTH
      const deviceH = device.height || DIAGRAM_CONFIG.LAYOUT.NODE_HEIGHT

      const containerBounds = {
        minX: container.offsetX - container.width / 2,
        maxX: container.offsetX + container.width / 2,
        minY: container.offsetY - container.height / 2,
        maxY: container.offsetY + container.height / 2
      }

      return (
        device.offsetX - deviceW / 2 >= containerBounds.minX &&
        device.offsetX + deviceW / 2 <= containerBounds.maxX &&
        device.offsetY - deviceH / 2 >= containerBounds.minY &&
        device.offsetY + deviceH / 2 <= containerBounds.maxY
      )
    },

    /**
     * Move all devices in container
     */
    moveDevicesInContainer (containerId, deltaX, deltaY, diagram) {
      if (
        Math.abs(deltaX) < DIAGRAM_CONFIG.ANIMATION.POSITION_CHANGE_THRESHOLD &&
        Math.abs(deltaY) < DIAGRAM_CONFIG.ANIMATION.POSITION_CHANGE_THRESHOLD
      ) {
        return
      }
      diagram.nodes.forEach((node) => {
        if (node.addInfo?.containerId == containerId && !node.addInfo?.isContainer) {
          node.offsetX += deltaX
          node.offsetY += deltaY
        }
      })
      diagram.dataBind()
    },

    /**
     * Extract node from position change args
     */
    extractNodeFromArgs (args) {
      return args.source?.addInfo ? args.source : args.element || args.source?.properties?.nodes?.[0]
    },

    /**
     * Handle container movement
     */
    handleContainerMove (node, args, diagram) {
      const containerId = node.addInfo.containerId
      console.log('Handling container move for containerId:', args)
      const deltaX = args.newValue.offsetX - args.oldValue.offsetX
      const deltaY = args.newValue.offsetY - args.oldValue.offsetY
      this.moveDevicesInContainer(containerId, deltaX, deltaY, diagram)
    },

    /**
     * Handle device movement within container
     */
    handleDeviceMove (node, args, diagram) {
      if (!node.addInfo.containerId || node.addInfo.isContainer) return

      const containerId = node.addInfo.containerId
      const containerNode = diagram.getObject(`container_${containerId}`)

      if (!containerNode) return

      const deviceW = node.width || DIAGRAM_CONFIG.LAYOUT.NODE_WIDTH
      const deviceH = node.height || DIAGRAM_CONFIG.LAYOUT.NODE_HEIGHT

      const containerBounds = {
        minX: containerNode.offsetX - containerNode.width / 2,
        maxX: containerNode.offsetX + containerNode.width / 2,
        minY: containerNode.offsetY - containerNode.height / 2,
        maxY: containerNode.offsetY + containerNode.height / 2
      }

      const newX = args.newValue.offsetX
      const newY = args.newValue.offsetY

      const isInside =
        newX - deviceW / 2 >= containerBounds.minX &&
        newX + deviceW / 2 <= containerBounds.maxX &&
        newY - deviceH / 2 >= containerBounds.minY &&
        newY + deviceH / 2 <= containerBounds.maxY

      if (!isInside) {
        args.cancel = true
        this.$nextTick(() => {
          const diagramNode = diagram.getObject(node.id)
          if (diagramNode) {
            diagramNode.offsetX = args.oldValue.offsetX
            diagramNode.offsetY = args.oldValue.offsetY
            diagram.dataBind()
          }
        })
      }
    },

    createDevicePaletteSymbol (device) {
      const color = this.deviceColors[device.type] || this.deviceColors.DEFAULT
      const icon = this.getDeviceIcon(device.type)
      const deviceTypeLabel = this.getDeviceTypeName(device.type)
      const capacityText = device.congSuat ? `${device.congSuat} kW` : deviceTypeLabel

      return {
        id: `symbol_${device.id}`,
        width: 180,
        height: 80,
        constraints: NodeConstraints.Default,
        handleConstraints: 0,
        ports: this.createNodePorts(),
        shape: {
          type: 'HTML',
          content: `
          <div style="width: 100%; height: 80px; display: flex; flex-direction: column; align-items: center; justify-content: center; background: white; border: 2px solid ${color}; border-radius: 6px; padding: 0px; box-sizing: border-box; gap: 4px;"><div style="font-size: 24px; line-height: 1;">${icon}</div><div style="font-size: 11px; font-weight: 600; color: #333; text-align: center; word-wrap: break-word; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">${device.name}</div><div style="font-size: 10px; color: #666; text-align: center;">${capacityText}</div></div>`
        },
        addInfo: {
          deviceId: device.id,
          deviceName: device.name,
          deviceType: device.type,
          level: device.level || 0,
          parentId: device.parentId || null,
          congSuat: device.congSuat,
          trangThai: device.trangThai,
          containerId: device.containerId || null
        }
      }
    },

    async loadUnconnectedDevices () {
      try {
        this.loadingUnconnectedDevices = true

        // Gọi API lấy danh sách thiết bị chưa đấu nối
        const response = await this.$root.context.post('/web-ecms/sodo-ketnoi-dien/ds-thietbi-chua-ket-noi', {
          idcId: this.idcId
        })

        if (response.error_code === 'BSS-00000000' && response.data) {
          // Transform API response to unconnectedDevices format
          this.unconnectedDevices = []

          response.data.forEach(group => {
            // Parse DANH_SACH_THIETBI JSON string
            let devices = []
            try {
              devices = JSON.parse(group.DANH_SACH_THIETBI || '[]')
            } catch (parseError) {
              console.warn('Failed to parse DANH_SACH_THIETBI:', parseError)
              return
            }

            // Map device type codes to diagram types
            const typeMapping = {
              'MAYPD': 'GENERATOR',
              'TUTK': 'TRANSFORMER',
              'MAYBA': 'TRANSFORMER',
              'TDIENHATHE': 'SWITCH',
              'TDIENATS': 'ATS',
              'TUPP': 'SWITCH',
              'UPS': 'UPS',
              'RPDU': 'RPDU',
              'DIEUHOA': 'DEFAULT',
              'THAPGNHIET': 'DEFAULT',
              'MAYBOM': 'DEFAULT'
            }

            // Add each device from the group
            console.log(devices, 'Devices in group')
            devices.forEach(device => {
              this.unconnectedDevices.push({
                id: `device_${device.ID}`,
                name: device.TEN || device.MATHIETBI,
                type: typeMapping[group.MA_LOAITBI] || 'DEFAULT',
                congSuat: device.CS_THUCTE || device.CS_DINHDANH || 0,
                trangThai: device.HIEU_LUC == '1' ? 'HOATDONG' : 'KHONG_HOATDONG',
                maThietBi: device.MATHIETBI,
                loaiThietBiId: group.LOAITBI_ID,
                loaiThietBi: group.LOAI_TBI,
                maLoaiThietBi: group.MA_LOAITBI,
                isContainer: false
              })
            })
          })

          console.log('✅ Loaded unconnected devices from API:', this.unconnectedDevices)
        } else {
          console.warn('API returned no data, using empty list')
          this.unconnectedDevices = []
        }

        console.log('✅ Palettes computed:', this.palettes)
      } catch (error) {
        console.error('Error loading unconnected devices:', error)
        this.$toast.error(MESSAGES.ERROR.LOAD_UNCONNECTED_FAILED)
        // Fallback to empty list on error
        this.unconnectedDevices = []
      } finally {
        this.loadingUnconnectedDevices = false
      }
    },

    getPaletteNodeDefaults (node) {
      return {
        width: 110,
        height: 80,
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate
      }
    },

    getSymbolInfo (symbol) {
      return {
        fit: true,
        description: {
          text: symbol.addInfo ? symbol.addInfo.deviceName || '' : '',
          overflow: 'Wrap'
        }
      }
    },

    onSave () {
      // TODO: Implement save logic
      this.$toast.info('Chức năng lưu đang phát triển')
    },

    async loadData () {
      try {
        this.$root.showLoading(true)

        // Gọi API lấy dữ liệu sơ đồ kết nối điện
        const response = await this.$root.context.post('/web-ecms/sodo-ketnoi-dien/sodo-ketnoi-dien-idc', {
          idcId: this.idcId
        })

        if (response.error_code === 'BSS-00000000' && response.data) {
          // Transform API response to diagram data format
          const diagramData = this.transformApiResponseToDiagramData(response.data)
          this.buildDiagram(diagramData)
        } else {
          console.warn('API returned no data or error:', response)
          this.$toast.error(MESSAGES.ERROR.LOAD_FAILED)
        }
      } catch (error) {
        console.error('Error loading connection diagram:', error)
        this.$toast.error(MESSAGES.ERROR.GENERAL_ERROR)
      } finally {
        this.isDataLoaded = true
        this.$root.showLoading(false)

        console.log('✅ Data loaded, isDataLoaded:', this.isDataLoaded)

        // Fit to page và vẽ containers sau khi load xong - using requestAnimationFrame for better performance
        this.$nextTick(() => {
          requestAnimationFrame(() => {
            this.onFitToPage()
            // Vẽ containers sau khi layout đã được tính toán
            requestAnimationFrame(() => {
              if (this.debouncedDrawContainers) {
                this.debouncedDrawContainers()
              }
            })
          })
        })
      }
    },

    transformApiResponseToDiagramData (apiData) {
      if (!apiData || !Array.isArray(apiData)) {
        return { devices: [], connections: [], containers: [] }
      }

      // Map device type codes to diagram types
      const typeMapping = {
        'MAYPD': 'GENERATOR',
        'TUTK': 'TRANSFORMER',
        'MAYBA': 'TRANSFORMER',
        'TDIENHATHE': 'SWITCH',
        'TDIENATS': 'ATS',
        'TUPP': 'SWITCH',
        'UPS': 'UPS',
        'RPDU': 'RPDU',
        'PDU': 'PDU',
        'DIEUHOA': 'DEFAULT',
        'THAPGNHIET': 'DEFAULT',
        'MAYBOM': 'DEFAULT'
      }

      // Collect unique devices and connections
      const devicesMap = new Map()
      const connections = []
      const systemsMap = new Map()
      const roomsMap = new Map()

      // Thêm node cố định "Điện lưới 22kV"
      const POWER_GRID_ID = 'power_grid_22kv'
      devicesMap.set(POWER_GRID_ID, {
        id: POWER_GRID_ID,
        name: 'Điện lưới 22kV',
        type: 'POWER_GRID',
        level: null, // Will be set to 0 (top level) in calculateDeviceLevels
        parentId: null,
        congSuat: 0,
        trangThai: 'HOATDONG',
        containerId: null,
        maThietBi: 'GRID-22KV',
        loaiThietBi: 'Điện lưới',
        nhomThietBi: 'Hệ thống điện',
        isContainer: false
      })

      // Track MAYBA devices for auto-connection to power grid
      const maybaDevices = []

      apiData.forEach((conn, index) => {
        // Add source device
        if (conn.THIETBI_NGUON_ID) {
          if (!devicesMap.has(conn.THIETBI_NGUON_ID)) {
            devicesMap.set(conn.THIETBI_NGUON_ID, {
              id: conn.THIETBI_NGUON_ID,
              name: conn.THIETBI_NGUON_TEN || conn.THIETBI_NGUON_MATHIETBI,
              type: typeMapping[conn.THIETBI_NGUON_MA_LOAITBI] || 'DEFAULT',
              level: null, // Will be calculated based on connections
              parentId: null, // Will be calculated based on connections
              congSuat: 0, // Can be added from API if available
              trangThai: 'HOATDONG',
              containerId: conn.THIETBI_NGUON_HETHONG_ID || null,
              maThietBi: conn.THIETBI_NGUON_MATHIETBI,
              loaiThietBi: conn.THIETBI_NGUON_LOAI_TBI,
              nhomThietBi: conn.THIETBI_NGUON_NHOM_TBI,
              phongThietBi: conn.PHONG_TBI_NGUON || null,
              phongThietBiId: conn.PHONG_TBI_NGUON_ID || null,
              rackThietBi: conn.RACK_TBI_NGUON || null,
              rackThietBiId: conn.RACK_TBI_NGUON_ID || null,
              isContainer: false
            })
          }

          // Track MAYBA devices
          if (conn.THIETBI_NGUON_MA_LOAITBI === 'MAYBA') {
            maybaDevices.push(conn.THIETBI_NGUON_ID)
          }

          // Track system/container
          if (conn.THIETBI_NGUON_HETHONG_ID && !systemsMap.has(conn.THIETBI_NGUON_HETHONG_ID)) {
            systemsMap.set(conn.THIETBI_NGUON_HETHONG_ID, {
              id: conn.THIETBI_NGUON_HETHONG_ID,
              name: conn.THIETBI_NGUON_HETHONG,
              type: 'SYSTEM'
            })
          }

          // Track room/container
          if (conn.PHONG_TBI_NGUON_ID && !roomsMap.has(conn.PHONG_TBI_NGUON_ID)) {
            roomsMap.set(conn.PHONG_TBI_NGUON_ID, {
              id: conn.PHONG_TBI_NGUON_ID,
              name: conn.PHONG_TBI_NGUON,
              type: 'ROOM'
            })
          }
        }

        // Add target device
        if (conn.THIETBI_DICH_ID) {
          if (!devicesMap.has(conn.THIETBI_DICH_ID)) {
            devicesMap.set(conn.THIETBI_DICH_ID, {
              id: conn.THIETBI_DICH_ID,
              name: conn.THIETBI_DICH_TEN || conn.THIETBI_DICH_MATHIETBI,
              type: typeMapping[conn.THIETBI_DICH_MA_LOAITBI] || 'DEFAULT',
              level: null, // Will be calculated based on connections
              parentId: null, // Will be calculated based on connections
              congSuat: 0,
              trangThai: 'HOATDONG',
              containerId: conn.THIETBI_DICH_HETHONG_ID || null,
              maThietBi: conn.THIETBI_DICH_MATHIETBI,
              loaiThietBi: conn.THIETBI_DICH_LOAI_TBI,
              nhomThietBi: conn.THIETBI_DICH_NHOM_TBI,
              phongThietBi: conn.PHONG_TBI_DICH || null,
              phongThietBiId: conn.PHONG_TBI_DICH_IK || null,
              rackThietBi: conn.RACK_TBI_DICH || null,
              rackThietBiId: conn.RACK_TBI_DICH_ID || null,
              isContainer: false
            })
          }

          // Track system/container
          if (conn.THIETBI_DICH_HETHONG_ID && !systemsMap.has(conn.THIETBI_DICH_HETHONG_ID)) {
            systemsMap.set(conn.THIETBI_DICH_HETHONG_ID, {
              id: conn.THIETBI_DICH_HETHONG_ID,
              name: conn.THIETBI_DICH_HETHONG,
              type: 'SYSTEM'
            })
          }

          // Track room/container
          if (conn.PHONG_TBI_DICH_IK && !roomsMap.has(conn.PHONG_TBI_DICH_IK)) {
            roomsMap.set(conn.PHONG_TBI_DICH_IK, {
              id: conn.PHONG_TBI_DICH_IK,
              name: conn.PHONG_TBI_DICH,
              type: 'ROOM'
            })
          }

          // Track MAYBA devices
          if (conn.THIETBI_DICH_MA_LOAITBI === 'MAYBA') {
            maybaDevices.push(conn.THIETBI_DICH_ID)
          }
        }

        // Add connection
        if (conn.THIETBI_NGUON_ID && conn.THIETBI_DICH_ID) {
          // Determine connection type based on device types and port classification
          let connectionType = 'NGUON_UPS' // Default

          // You can add logic here to determine connection type based on:
          // - PORT_NGUON_PHANLOAI, PORT_DICH_PHANLOAI
          // - Device types
          // - Other factors from the API

          connections.push({
            from: conn.THIETBI_NGUON_ID,
            to: conn.THIETBI_DICH_ID,
            type: connectionType,
            portNguon: conn.PORT_NGUON_TEN || '',
            portDich: conn.PORT_DICH_TEN || '',
            portNguonId: conn.PORT_NGUON_ID,
            portDichId: conn.PORT_DICH_ID,
            khoangCach: null, // Not provided in API
            trangThai: 'HOATDONG',
            ghiChu: ''
          })
        }
      })

      // Auto-create connections from Power Grid to MAYBA devices
      const uniqueMaybaDevices = [...new Set(maybaDevices)]
      uniqueMaybaDevices.forEach(maybaId => {
        connections.push({
          from: POWER_GRID_ID,
          to: maybaId,
          type: 'DUONG_TRUNG_THE', // Đường trung thế cho điện lưới 22kV
          portNguon: 'Grid Output',
          portDich: 'Input 22kV',
          portNguonId: null,
          portDichId: null,
          khoangCach: null,
          trangThai: 'HOATDONG',
          ghiChu: 'Kết nối tự động từ điện lưới 22kV'
        })

        // Update MAYBA device parent to Power Grid
        const maybaDevice = devicesMap.get(maybaId)
        if (maybaDevice) {
          maybaDevice.parentId = POWER_GRID_ID
        }
      })

      // Calculate proper levels for devices based on hierarchy
      const devices = Array.from(devicesMap.values())

      // Build parent-child relationships from connections
      this.buildParentChildRelationships(devices, connections)

      // Calculate levels based on the relationships
      this.calculateDeviceLevels(devices, connections)

      // Convert systems map and rooms map to containers array
      const containers = [
        ...Array.from(systemsMap.values()),
        ...Array.from(roomsMap.values())
      ]

      return {
        devices,
        connections,
        containers
      }
    },

    buildParentChildRelationships (devices, connections) {
      // Xây dựng quan hệ cha-con dựa trên connections
      // Mỗi thiết bị chỉ có 1 parent (thiết bị cung cấp điện cho nó)
      const deviceMap = new Map()
      devices.forEach(device => deviceMap.set(device.id, device))

      console.log('🔗 Building parent-child relationships...')

      connections.forEach(conn => {
        const childDevice = deviceMap.get(conn.to)
        const parentDevice = deviceMap.get(conn.from)

        if (childDevice && parentDevice) {
          // Chỉ set parent nếu chưa có hoặc parent hiện tại không hợp lệ
          if (!childDevice.parentId) {
            childDevice.parentId = conn.from
            console.log(`  ✅ ${childDevice.name} -> parent: ${parentDevice.name}`)
          }
        }
      })
    },

    calculateDeviceLevels (devices, connections) {
      console.log('📊 Calculating device levels based on device type...')

      // Định nghĩa thứ tự level cho từng loại thiết bị
      const deviceTypeLevels = {
        'POWER_GRID': 0,    // Điện lưới 22kV
        'GENERATOR': 0,      // Máy phát điện
        'TRANSFORMER': 1,    // Máy biến áp / Tủ trung thế
        'ATS': 2,            // Tủ điện ATS
        'SWITCH': 3,         // Tủ phân phối
        'UPS': 4,            // UPS
        'RPDU': 5,           // rPDU
        'PDU': 5,            // PDU
        'DEFAULT': 6         // Thiết bị khác
      }

      // Gán level cho từng thiết bị dựa trên type
      devices.forEach(device => {
        const level = deviceTypeLevels[device.type] ?? deviceTypeLevels.DEFAULT
        device.level = level
        console.log(`  ✅ ${device.name} (${device.type}) -> Level ${level}`)
      })

      console.log('✅ Level calculation complete')
    },

    buildDiagram (data) {
      try {
        this.nodes = []
        this.connectors = []

        if (!data || typeof data !== 'object') {
          throw new Error('Invalid diagram data')
        }

        // Lưu thông tin containers
        this.containersMap = {}
        if (data.containers && Array.isArray(data.containers)) {
          data.containers.forEach((container) => {
            if (container && container.id) {
              this.containersMap[container.id] = container
            } else {
              console.warn('Container missing ID:', container)
            }
          })
        }

        // Tạo device nodes với vị trí thủ công
        if (data.devices && Array.isArray(data.devices)) {
          const validDevices = data.devices.filter(this.validateDeviceData)
          const positions = this.calculateManualPositions(validDevices)

          validDevices.forEach((device) => {
            const node = this.createDeviceNode(device)
            const pos = positions[device.id]
            if (pos) {
              node.offsetX = pos.x
              node.offsetY = pos.y
            }
            this.nodes.push(node)
          })
        }

        // Tạo connectors
        if (data.connections && Array.isArray(data.connections)) {
          data.connections.forEach((conn) => {
            if (conn && conn.from && conn.to) {
              this.connectors.push(this.createConnector(conn))
            }
          })
        }
      } catch (error) {
        console.error('Error building diagram:', error)
        this.$toast.error(MESSAGES.ERROR.DIAGRAM_CREATE_FAILED)
        throw error
      }
    },

    calculateManualPositions (devices) {
      const positions = {}
      const { NODE_WIDTH, NODE_HEIGHT, HORIZONTAL_GAP, VERTICAL_GAP, START_X, START_Y } = DIAGRAM_CONFIG.LAYOUT

      // Nhóm devices theo level
      const devicesByLevel = {}
      devices.forEach((device) => {
        const level = device.level ?? 0 // Default to 0 if null/undefined
        if (!devicesByLevel[level]) {
          devicesByLevel[level] = []
        }
        devicesByLevel[level].push(device)
      })

      // Sắp xếp levels theo thứ tự tăng dần (0, 1, 2, 3...)
      const sortedLevels = Object.keys(devicesByLevel).map(l => parseInt(l)).sort((a, b) => a - b)

      sortedLevels.forEach((levelNum) => {
        const levelDevices = devicesByLevel[levelNum]
        const y = START_Y + levelNum * VERTICAL_GAP

        // Tính vị trí X cho mỗi device trong level
        const totalWidth = (levelDevices.length - 1) * HORIZONTAL_GAP
        const startXForLevel = START_X - totalWidth / 2

        levelDevices.forEach((device, index) => {
          const x = startXForLevel + index * HORIZONTAL_GAP
          positions[device.id] = { x, y }

          // Log position for debugging
          console.log(`  📍 ${device.name} (Level ${levelNum}): x=${x}, y=${y}`)
        })
      })

      return positions
    },

    createDeviceNode (device) {
      const color = this.deviceColors[device.type] || this.deviceColors.DEFAULT

      // Ưu tiên phòng container hơn hệ thống container
      const effectiveContainerId = device.phongThietBiId || device.containerId

      return {
        id: `device_${device.id}`,
        width: 120, // Giảm từ 80
        height: 60, // Giảm từ 60
        shape: {
          type: 'HTML',
          content: this.createDeviceHtml(device, color)
        },
        constraints: NodeConstraints.Default,
        ports: this.createNodePorts(),
        addInfo: {
          deviceId: device.id,
          deviceName: device.name,
          deviceType: device.type,
          level: device.level,
          parentId: device.parentId,
          congSuat: device.congSuat,
          trangThai: device.trangThai,
          containerId: effectiveContainerId,
          phongThietBi: device.phongThietBi,
          phongThietBiId: device.phongThietBiId,
          rackThietBi: device.rackThietBi,
          rackThietBiId: device.rackThietBiId
        }
      }
    },

    createNodePorts () {
      return [
        {
          id: 'port1',
          offset: { x: 0.2, y: 0.95 }, // Góc dưới bên trái
          visibility: PortVisibility.Hover | PortVisibility.Connect,
          constraints: PortConstraints.Default | PortConstraints.Draw,
          shape: 'Circle',
          width: 8,
          height: 8,
          style: {
            fill: '#2196F3',
            strokeColor: '#fff',
            strokeWidth: 2
          }
        }
      ]
    },

    createDeviceHtml (device, color) {
      const icon = this.getDeviceIcon(device.type)
      const statusColor = device.trangThai === 'HOATDONG' ? '#28a745' : '#dc3545'

      // Lấy thông tin container nếu có - Ưu tiên phòng hơn hệ thống
      let containerBadge = ''
      const effectiveContainerId = device.phongThietBiId || device.containerId
      if (effectiveContainerId && this.containersMap && this.containersMap[effectiveContainerId]) {
        const container = this.containersMap[effectiveContainerId]
        const badgeColor = container.type === 'ROOM' ? '#9C27B0' : container.type === 'SYSTEM' ? '#2196F3' : '#FF9800'
        const badgeText = container.type === 'ROOM' ? 'Phòng' : container.type === 'SYSTEM' ? 'HT' : 'Zone'

        containerBadge = `
          <div style="
            position: absolute;
            bottom: 6px;
            left: 6px;
            background: ${badgeColor};
            color: white;
            font-size: 10px;
            font-weight: bold;
            padding: 3px 8px;
            border-radius: 4px;
            box-shadow: 0 1px 2px rgba(0,0,0,0.2);
          ">${badgeText}</div>
        `
      }

      // Hiển thị thông tin rack cho rPDU
      let rackInfo = ''
      if (device.type === 'RPDU') {
        const rackName = device.rackThietBi || device.phongThietBi
        if (rackName) {
          rackInfo = `<div style="
            font-size: 9px;
            color: blue;
            font-weight: 600;
            margin-top: 2px;
          ">${rackName}</div>`
        }
      }

      return `
        <div style="
          width: 100%;
          height: 100%;
          display: flex;
          flex-direction: row;
          align-items: center;
          background: white;
          border: 3px solid ${color};
          border-radius: 5px;
          padding: 3px;
          box-sizing: border-box;
          box-shadow: 0 2px 6px rgba(0,0,0,0.15);
          position: relative;
        ">
          <!-- Icon lớn bên trái -->
          <div style="
            font-size: 20px;
            line-height: 1;
          ">${icon}</div>

          <!-- Nội dung bên phải -->
          <div style="
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: center;
          ">
            <!-- Tên thiết bị -->
            <div style="
              font-size: 10px;
              font-weight: bold;
              color: #333;
              margin-bottom: 4px;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
            ">${device.name}</div>

            <!-- Công suất -->
            <div style="
              font-size: 11px;
              color: #666;
              font-weight: 500;
            ">${device.congSuat ? device.congSuat + ' kW' : device.type}</div>

            <!-- Thông tin rack cho rPDU -->
            ${rackInfo}
          </div>

          <!-- Trạng thái (chấm tròn) -->
          <div style="
            position: absolute;
            top: -7px;
            right: -7px;
            width: 12px;
            height: 12px;
            border-radius: 50%;
            background: ${statusColor};
            box-shadow: 0 0 4px ${statusColor};
          "></div>
        </div>
      `
    },

    getDeviceIcon (type) {
      const icons = {
        RPDU: '🔌',
        PDU: '⚡',
        RACK: '🗄️',
        UPS: '🔋',
        ATS: '🔀',
        TRANSFORMER: '⚙️',
        GENERATOR: '🏭',
        SWITCH: '🔀',
        POWER_GRID: '⚡',
        DEFAULT: '📦'
      }
      return icons[type] || icons.DEFAULT
    },

    createConnector (connection) {
      // Xác định màu và style dựa vào loại kết nối theo hình ảnh chú thích
      let strokeColor = '#2196F3'
      let strokeWidth = 3

      // Mapping theo hình ảnh chú thích
      if (connection.type === 'DUONG_TRUNG_THE') {
        strokeColor = '#FF0000' // Đỏ - Đường trung thế
      } else if (connection.type === 'DUONG_HA_THE') {
        strokeColor = '#0066FF' // Xanh dương - Đường hạ thế
      } else if (connection.type === 'NGUON_DU_PHONG') {
        strokeColor = '#FFA500' // Cam - Nguồn dự phòng
      } else if (connection.type === 'NGUON_UPS') {
        strokeColor = '#00CC00' // Xanh lá - Nguồn UPS
      } else if (connection.type === 'NGUON_CHINH') {
        strokeColor = '#FF0000' // Đỏ - Nguồn chính (fallback)
      }

      return {
        id: `conn_${connection.from}_${connection.to}`,
        sourceID: `device_${connection.from}`,
        targetID: `device_${connection.to}`,
        type: 'Straight', // Đường thẳng (chim bay)
        style: {
          strokeColor: strokeColor,
          strokeWidth: strokeWidth
        },
        targetDecorator: {
          shape: 'None' // Bỏ mũi tên
        },
        annotations: [], // Không hiển thị label trên connector
        constraints:
          ConnectorConstraints.Default &
          ~ConnectorConstraints.Drag &
          ~ConnectorConstraints.DragSourceEnd &
          ~ConnectorConstraints.DragTargetEnd,
        addInfo: {
          connectionType: connection.type,
          from: connection.from,
          to: connection.to,
          portNguon: connection.portNguon || '',
          portDich: connection.portDich || '',
          khoangCach: connection.khoangCach || null,
          trangThai: connection.trangThai || 'HOATDONG',
          ghiChu: connection.ghiChu || ''
        }
      }
    },

    getNodeDefaults (node) {
      // Nếu là container, vô hiệu hóa khả năng kết nối
      if (node.addInfo?.isContainer) {
        return {
          width: 80,
          height: 60,
          constraints:
            NodeConstraints.Default &
            ~NodeConstraints.Resize &
            ~NodeConstraints.Rotate &
            ~NodeConstraints.InConnect &
            ~NodeConstraints.OutConnect
        }
      }

      return {
        width: 80,
        height: 60,
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate
      }
    },

    getConnectorDefaults (connector) {
      // Đảm bảo tất cả connector đều là đường thẳng
      connector.type = 'Straight'
      return {
        type: 'Straight',
        style: {
          strokeColor: '#2196F3',
          strokeWidth: 3
        },
        targetDecorator: {
          shape: 'None'
        },
        constraints: ConnectorConstraints.Default
      }
    },

    // Palette methods
    createPaletteSymbol (deviceType) {
      const color = this.deviceColors[deviceType.type] || this.deviceColors.DEFAULT
      const icon = this.getDeviceIcon(deviceType.type)

      return {
        id: `palette_${deviceType.type}`,
        width: 120,
        height: 60,
        shape: {
          type: 'HTML',
          content: `
            <div style="
              width: 100%;
              height: 100%;
              display: flex;
              flex-direction: column;
              align-items: center;
              justify-content: center;
              background: white;
              border: 2px solid ${color};
              border-radius: 4px;
              box-sizing: border-box;
            ">
              <div style="font-size: 18px; margin-bottom: 3px;">${icon}</div>
              <div style="font-size: 10px; font-weight: bold; color: #333;">${deviceType.label}</div>
            </div>
          `
        },
        addInfo: {
          deviceType: deviceType.type,
          deviceLabel: deviceType.label
        }
      }
    },

    onContextMenuOpen (args) {
      if (!this.$refs.diagramObj) return
      const diagram = this.$refs.diagramObj.ej2Instances

      // Kiểm tra xem có node được chọn không
      const hasSelectedNode = diagram.selectedItems.nodes && diagram.selectedItems.nodes.length > 0
      const hasSelectedConnector = diagram.selectedItems.connectors && diagram.selectedItems.connectors.length > 0

      // Nếu không có gì được chọn, hủy context menu
      if (!hasSelectedNode && !hasSelectedConnector) {
        args.cancel = true
        return
      }

      // Nếu node được chọn là container, hủy context menu
      if (hasSelectedNode) {
        const selectedNode = diagram.selectedItems.nodes[0]
        if (selectedNode.addInfo?.isContainer) {
          args.cancel = true
          return
        }
      }
    },

    onContextMenuClick (args) {
      if (!this.$refs.diagramObj) return
      const diagram = this.$refs.diagramObj.ej2Instances

      if (args.item.id === 'viewDetail') {
        if (diagram.selectedItems.nodes && diagram.selectedItems.nodes.length > 0) {
          const node = diagram.selectedItems.nodes[0]
          if (node.addInfo && node.addInfo.deviceId) {
            this.showDeviceDetail(node.addInfo.deviceId)
          }
        }
      } else if (args.item.id === 'connectTo') {
        if (diagram.selectedItems.nodes && diagram.selectedItems.nodes.length > 0) {
          const sourceNode = diagram.selectedItems.nodes[0]
          if (sourceNode.addInfo && sourceNode.addInfo.deviceId) {
            // Lưu source node và bật chế độ chọn target
            this.sourceNode = sourceNode
            this.isConnectMode = true
            this.$toast.info(MESSAGES.INFO.CONNECT_MODE_ON(sourceNode.addInfo.deviceName))
          }
        }
      } else if (args.item.id === 'removeDevice') {
        if (diagram.selectedItems.nodes && diagram.selectedItems.nodes.length > 0) {
          const node = diagram.selectedItems.nodes[0]
          if (node.addInfo && node.addInfo.deviceId) {
            this.removeDeviceFromDiagram(node)
          }
        }
      } else if (args.item.id === 'deleteConnection') {
        if (diagram.selectedItems.connectors && diagram.selectedItems.connectors.length > 0) {
          const connector = diagram.selectedItems.connectors[0]
          this.deleteConnection(connector)
        }
      }
    },

    onDiagramClick (args) {
      console.log('🖱️ Diagram click:', args)

      if (args.element && args.element.addInfo && args.element.addInfo.deviceId) {
        // Connect mode - tạo kết nối
        if (this.isConnectMode) {
          if (!this.sourceNode) {
            // Không nên xảy ra vì sourceNode đã được set từ context menu
            this.sourceNode = args.element
            this.$toast.info(`Đã chọn: ${args.element.addInfo.deviceName}. Click vào thiết bị đích để tạo kết nối.`)
          } else {
            // Chọn node đích và tạo kết nối
            const targetNode = args.element
            this.createConnection(this.sourceNode, targetNode)
            // Reset connect mode
            this.isConnectMode = false
          }
        } else if (args.count === 2) {
          // Double click to view detail
          this.showDeviceDetail(args.element.addInfo.deviceId)
        }
      } else {
        // Click vào vùng trống - hủy connect mode
        if (this.isConnectMode) {
          this.isConnectMode = false
          this.sourceNode = null
          this.$toast.info(MESSAGES.INFO.CONNECT_MODE_CANCELLED)
        }
      }
    },

    createConnection (sourceNode, targetNode) {
      // Kiểm tra không cho kết nối đến container
      if (sourceNode.addInfo?.isContainer || targetNode.addInfo?.isContainer) {
        this.$toast.warning('Không thể kết nối đến vùng container! Vui lòng kết nối đến thiết bị.')
        this.sourceNode = null
        return
      }

      if (sourceNode.id === targetNode.id) {
        this.$toast.warning(MESSAGES.WARNING.SELF_CONNECTION)
        this.sourceNode = null
        return
      }

      // Kiểm tra kết nối đã tồn tại
      if (!this.$refs.diagramObj) return
      const diagram = this.$refs.diagramObj.ej2Instances
      const existingConnection = diagram.connectors.find(
        (conn) =>
          (conn.sourceID === sourceNode.id && conn.targetID === targetNode.id) ||
          (conn.sourceID === targetNode.id && conn.targetID === sourceNode.id)
      )

      if (existingConnection) {
        this.$toast.warning(MESSAGES.WARNING.DUPLICATE_CONNECTION)
        this.sourceNode = null
        return
      }

      // Hiển thị dialog chọn loại kết nối
      this.connectionDialog.sourceNode = sourceNode
      this.connectionDialog.targetNode = targetNode
      this.connectionDialog.selectedType = 'NGUON_UPS'
      this.$refs.modalConnectionType.show()
    },

    onNodePositionChange (args) {
      if (!args.newValue || !args.oldValue) return

      const diagram = this.$refs.diagramObj?.ej2Instances
      if (!diagram) return

      const node = this.extractNodeFromArgs(args)
      if (!node?.addInfo) return

      if (this.isContainerNode(node)) {
        this.handleContainerMove(node, args, diagram)
      } else if (node.addInfo.containerId) {
        this.handleDeviceMove(node, args, diagram)
      }
    },

    onCollectionChange (args) {
      if (!args.element || args.element.addInfo?.deviceId) {
        return
      }
      console.log('📦 Collection change:', {
        changeType: args.changeType,
        cause: args.cause,
        state: args.state,
        type: args.type,
        element: args.element
      })

      // Bắt khi connector mới được tạo từ việc kéo port
      // cause: 14 = DrawingTool (kéo từ port)
      // state: 'Changed' = connector đã được thêm vào diagram
      if (args.cause === 14 && args.state === 'Changed' && args.element) {
        console.log('🔗 New connector created by dragging!', args.element)
        const connector = args.element

        const diagram = this.$refs.diagramObj?.ej2Instances
        if (!diagram) {
          console.error('❌ Diagram not found')
          return
        }

        // Kiểm tra xem có thả giữa chừng (không có targetID) không
        if (!connector.targetID || connector.targetID === '') {
          console.log('⚠️ Connector dropped in middle (no target), removing...')
          this.$nextTick(() => {
            diagram.remove(connector)
            diagram.dataBind()
          })
          return
        }

        // Kiểm tra sourceID và targetID hợp lệ
        if (!connector.sourceID || connector.sourceID === '') {
          console.log('⚠️ Connector has no source, removing...')
          this.$nextTick(() => {
            diagram.remove(connector)
            diagram.dataBind()
          })
          return
        }

        const sourceNode = diagram.getObject(connector.sourceID)
        const targetNode = diagram.getObject(connector.targetID)

        console.log('Source node:', sourceNode?.addInfo?.deviceName)
        console.log('Target node:', targetNode?.addInfo?.deviceName)

        if (!sourceNode || !targetNode) {
          console.error('❌ Source or target node not found, removing connector')
          this.$nextTick(() => {
            diagram.remove(connector)
            diagram.dataBind()
          })
          return
        }

        // Kiểm tra không cho kết nối đến container
        if (sourceNode.addInfo?.isContainer || targetNode.addInfo?.isContainer) {
          console.log('⚠️ Cannot connect to container')
          this.$nextTick(() => {
            diagram.remove(connector)
            diagram.dataBind()
          })
          this.$toast.warning('Không thể kết nối đến vùng container! Vui lòng kết nối đến thiết bị.')
          return
        }

        // Kiểm tra không tự kết nối với chính nó
        if (sourceNode.id === targetNode.id) {
          console.log('⚠️ Self connection detected')
          this.$nextTick(() => {
            diagram.remove(connector)
            diagram.dataBind()
          })
          this.$toast.warning(MESSAGES.WARNING.SELF_CONNECTION)
          return
        }

        // Kiểm tra kết nối đã tồn tại
        const existingConnection = diagram.connectors.find(
          (conn) =>
            conn.id !== connector.id &&
            ((conn.sourceID === sourceNode.id && conn.targetID === targetNode.id) ||
              (conn.sourceID === targetNode.id && conn.targetID === sourceNode.id))
        )

        if (existingConnection) {
          console.log('⚠️ Duplicate connection detected')
          this.$nextTick(() => {
            diagram.remove(connector)
            diagram.dataBind()
          })
          this.$toast.warning(MESSAGES.WARNING.DUPLICATE_CONNECTION)
          return
        }

        // Lưu ID của connector tạm để xóa sau khi user chọn loại kết nối
        const tempConnectorId = connector.id

        // Hiển thị dialog chọn loại kết nối
        console.log('📋 Showing connection type dialog')
        this.connectionDialog.sourceNode = sourceNode
        this.connectionDialog.targetNode = targetNode
        this.connectionDialog.selectedType = 'NGUON_UPS'
        this.connectionDialog.tempConnectorId = tempConnectorId

        this.$refs.modalConnectionType.show()
      }
    },

    onConnectionChange (args) {
      console.log('🔗 onConnectionChange triggered:', {
        state: args.state,
        connector: args.connector,
        sourceID: args.connector?.sourceID,
        targetID: args.connector?.targetID
      })

      const diagram = this.$refs.diagramObj?.ej2Instances
      if (!diagram) {
        console.error('❌ Diagram not found')
        return
      }

      // Ngăn không cho kết nối đến container ngay khi đang kéo
      if (args.state === 'Changing' && args.connector) {
        // Kiểm tra nếu đang hover vào container thì cancel
        if (args.connector.targetID && args.connector.targetID !== '') {
          const targetNode = diagram.getObject(args.connector.targetID)
          if (targetNode?.addInfo?.isContainer) {
            // Cancel connection đến container
            args.cancel = true
            return
          }
        }
      }

      // Xử lý khi user kéo connector từ node này sang node khác
      if (args.state === 'Completed' && args.connector) {
        console.log('✅ Connection completed')

        // Kiểm tra phải thả vào node, không được thả vào vùng trống
        if (!args.connector.targetID || args.connector.targetID === '') {
          console.log('⚠️ Connector dropped in empty space, removing...')
          diagram.remove(args.connector)
          diagram.dataBind()
          this.$toast.warning('Vui lòng kéo đến thiết bị đích!')
          return
        }

        const sourceNode = diagram.getObject(args.connector.sourceID)
        const targetNode = diagram.getObject(args.connector.targetID)

        console.log('Source node:', sourceNode?.addInfo?.deviceName)
        console.log('Target node:', targetNode?.addInfo?.deviceName)

        if (!sourceNode || !targetNode) {
          console.error('❌ Source or target node not found, removing connector')
          diagram.remove(args.connector)
          diagram.dataBind()
          return
        }

        // Kiểm tra không cho kết nối đến container
        if (sourceNode.addInfo?.isContainer || targetNode.addInfo?.isContainer) {
          console.log('⚠️ Cannot connect to container')
          diagram.remove(args.connector)
          diagram.dataBind()
          this.$toast.warning('Không thể kết nối đến vùng container! Vui lòng kết nối đến thiết bị.')
          return
        }

        // Kiểm tra không tự kết nối với chính nó
        if (sourceNode.id === targetNode.id) {
          diagram.remove(args.connector)
          this.$toast.warning(MESSAGES.WARNING.SELF_CONNECTION)
          return
        }

        // Kiểm tra kết nối đã tồn tại
        const existingConnection = diagram.connectors.find(
          (conn) =>
            conn.id !== args.connector.id &&
            ((conn.sourceID === sourceNode.id && conn.targetID === targetNode.id) ||
              (conn.sourceID === targetNode.id && conn.targetID === sourceNode.id))
        )

        if (existingConnection) {
          diagram.remove(args.connector)
          this.$toast.warning(MESSAGES.WARNING.DUPLICATE_CONNECTION)
          return
        }

        // Lưu connector tạm
        const tempConnector = args.connector

        // Xóa connector tạm
        diagram.remove(tempConnector)

        // Hiển thị dialog chọn loại kết nối
        this.showConnectionTypeDialogForDrag(sourceNode, targetNode)
      } else if (args.state === 'Changed') {
        this.dragConnectionMode = true
      } else if (args.state === 'Start') {
        this.dragConnectionMode = true
      }
    },

    showConnectionTypeDialogForDrag (sourceNode, targetNode) {
      this.connectionDialog.sourceNode = sourceNode
      this.connectionDialog.targetNode = targetNode
      this.connectionDialog.selectedType = 'NGUON_UPS'
      this.$refs.modalConnectionType.show()
    },

    showDeviceDetail (deviceId) {
      // Lấy thông tin node để kiểm tra loại thiết bị
      const diagram = this.$refs.diagramObj?.ej2Instances
      if (!diagram) return

      const node = diagram.nodes.find((n) => n.addInfo?.deviceId === deviceId)
      if (!node || !node.addInfo) return

      // Kiểm tra nếu là RACK thì hiển thị modal ChiTietKetNoiDienRack
      if (node.addInfo.deviceType === 'RACK') {
        this.selectedRackId = deviceId
        this.$nextTick(() => {
          this.$refs.modalChiTietKetNoiDienRack.show()
        })
      } else {
        // Các thiết bị khác hiển thị modal thông thường
        this.selectedThietBiId = deviceId
        this.$nextTick(() => {
          this.$refs.modalChiTietThietBi.show(null, 'VIEW')
        })
      }
    },

    onRefresh () {
      this.loadData()
    },

    onZoomIn () {
      if (!this.$refs.diagramObj) return
      const diagram = this.$refs.diagramObj.ej2Instances
      if (diagram) {
        diagram.zoom(1.2)
      }
    },

    onZoomOut () {
      if (!this.$refs.diagramObj) return
      const diagram = this.$refs.diagramObj.ej2Instances
      if (diagram) {
        diagram.zoom(1 / 1.2)
      }
    },

    onFitToPage () {
      if (!this.$refs.diagramObj) return
      const diagram = this.$refs.diagramObj.ej2Instances
      if (diagram) {
        // Fit to page với mode 'Page' và region 'Content' để hiển thị toàn bộ
        diagram.fitToPage({
          mode: 'Page',
          region: 'Content',
          margin: { left: 50, top: 50, right: 50, bottom: 50 },
          canZoomIn: true
        })
      }
    },

    onViewAllElements () {
      // TODO: Implement view all elements modal
      this.$toast.info('Chức năng đang phát triển')
    },

    showExportModal () {
      if (!this.$refs.modalExport) {
        this.$toast.error('Không thể mở modal xuất sơ đồ')
        return
      }
      this.$refs.modalExport.show()
    },

    async onPrint () {
      try {
        this.$root.showLoading(true)
        // Đóng modal trước
        if (this.$refs.modalExport) {
          this.$refs.modalExport.hide()
        }

        // Đợi modal đóng xong
        await new Promise((resolve) => setTimeout(resolve, 300))

        // Kiểm tra diagram element
        const diagramElement = document.getElementById('diagram-ketnoi')
        if (!diagramElement) {
          this.$toast.error('Không tìm thấy element sơ đồ')
          return
        }

        this.$toast.info('Đang tạo ảnh để in...')

        // Sử dụng html2canvas để chụp diagram
        const canvas = await html2canvas(diagramElement, {
          backgroundColor: '#ffffff',
          scale: 2,
          logging: false,
          useCORS: true
        })

        // Tạo cửa sổ in mới
        const printWindow = window.open('', '_blank')
        if (printWindow) {
          printWindow.document.write('<html><head><title>In sơ đồ kết nối điện</title></head><body>')
          printWindow.document.write('<img src="' + canvas.toDataURL() + '" style="width:100%;" />')
          printWindow.document.write('</body></html>')
          printWindow.document.close()
          printWindow.focus()

          // Đợi ảnh load xong rồi mới in
          setTimeout(() => {
            printWindow.print()
          }, 500)
        }
      } catch (error) {
        console.error('Print error:', error)
        this.$toast.error('Có lỗi khi in sơ đồ: ' + error.message)
      } finally {
        this.$root.showLoading(false)
      }
    },

    async onExportPNG () {
      try {
        this.$root.showLoading(true)
        // Đóng modal trước
        if (this.$refs.modalExport) {
          this.$refs.modalExport.hide()
        }

        // Đợi modal đóng xong
        await new Promise((resolve) => setTimeout(resolve, 300))

        // Kiểm tra diagram element
        const diagramElement = document.getElementById('diagram-ketnoi')
        if (!diagramElement) {
          this.$toast.error('Không tìm thấy element sơ đồ')
          return
        }

        this.$toast.info('Đang xuất hình ảnh PNG...')

        // Sử dụng html2canvas để chụp diagram
        const canvas = await html2canvas(diagramElement, {
          backgroundColor: '#ffffff',
          scale: 2,
          logging: false,
          useCORS: true
        })

        // Chuyển canvas thành blob PNG và download
        canvas.toBlob((blob) => {
          const url = URL.createObjectURL(blob)
          const link = document.createElement('a')
          link.href = url
          link.download = `SoDoKetNoiDien_${this.idcId}_${Date.now()}.png`
          document.body.appendChild(link)
          link.click()
          document.body.removeChild(link)
          URL.revokeObjectURL(url)

          this.$toast.success('Đã xuất sơ đồ ra PNG')
        })
      } catch (error) {
        console.error('Export PNG error:', error)
        this.$toast.error('Có lỗi khi xuất PNG: ' + error.message)
      } finally {
        this.$root.showLoading(false)
      }
    },

    getDeviceTypeName (type) {
      const names = {
        RPDU: 'rPDU',
        PDU: 'PDU / MPDU',
        RACK: 'Rack',
        UPS: 'UPS',
        ATS: 'ATS',
        TRANSFORMER: 'Trạm biến áp',
        GENERATOR: 'Nguồn điện',
        SWITCH: 'Tủ phân phối',
        POWER_GRID: 'Điện lưới 22kV',
        DEFAULT: 'Khác'
      }
      return names[type] || type
    },

    // Connection management methods
    toggleConnectMode () {
      this.isConnectMode = !this.isConnectMode
      this.sourceNode = null

      if (this.isConnectMode) {
        this.$toast.info('Chế độ tạo kết nối: Click vào thiết bị nguồn, sau đó click vào thiết bị đích')
      } else {
        this.$toast.info('Đã thoát chế độ tạo kết nối')
      }
    },

    createConnection (sourceNode, targetNode) {
      if (sourceNode.id === targetNode.id) {
        this.$toast.warning(MESSAGES.WARNING.SELF_CONNECTION)
        return
      }

      // Kiểm tra kết nối đã tồn tại
      if (!this.$refs.diagramObj) return
      const diagram = this.$refs.diagramObj.ej2Instances
      const existingConnection = diagram.connectors.find(
        (conn) =>
          (conn.sourceID === sourceNode.id && conn.targetID === targetNode.id) ||
          (conn.sourceID === targetNode.id && conn.targetID === sourceNode.id)
      )

      if (existingConnection) {
        this.$toast.warning(MESSAGES.WARNING.DUPLICATE_CONNECTION)
        this.sourceNode = null
        return
      }

      // Hiển thị dialog chọn loại kết nối
      this.showConnectionTypeDialog(sourceNode, targetNode)
    },

    showConnectionTypeDialog (sourceNode, targetNode) {
      this.connectionDialog.sourceNode = sourceNode
      this.connectionDialog.targetNode = targetNode
      this.connectionDialog.selectedType = 'NGUON_UPS'
      this.$refs.modalConnectionType.show()
    },

    handleCreateConnection (data) {
      const { sourceNode, targetNode, connectionData, tempConnectorId } = data

      // Xóa connector tạm nếu có
      if (tempConnectorId) {
        const diagram = this.$refs.diagramObj?.ej2Instances
        if (diagram) {
          const tempConnector = diagram.getObject(tempConnectorId)
          if (tempConnector) {
            console.log('🗑️ Removing temp connector:', tempConnectorId)
            diagram.remove(tempConnector)
            diagram.dataBind()
          }
        }
      }

      if (sourceNode && targetNode && connectionData) {
        this.addConnection(sourceNode, targetNode, connectionData)
      }

      this.sourceNode = null
      this.connectionDialog.sourceNode = null
      this.connectionDialog.targetNode = null
      this.connectionDialog.tempConnectorId = null
    },

    handleCancelConnection (data) {
      // Xóa connector tạm khi cancel
      const { tempConnectorId } = data || {}
      if (tempConnectorId) {
        const diagram = this.$refs.diagramObj?.ej2Instances
        if (diagram) {
          const tempConnector = diagram.getObject(tempConnectorId)
          if (tempConnector) {
            console.log('❌ Canceling - Removing temp connector:', tempConnectorId)
            diagram.remove(tempConnector)
            diagram.dataBind()
          }
        }
      }

      this.sourceNode = null
      this.connectionDialog.sourceNode = null
      this.connectionDialog.targetNode = null
      this.connectionDialog.tempConnectorId = null
    },

    addConnection (sourceNode, targetNode, connectionData) {
      if (!this.$refs.diagramObj) return
      const diagram = this.$refs.diagramObj.ej2Instances
      const connectionId = `conn_${Date.now()}`

      const connector = this.createConnector({
        id: connectionId,
        from: sourceNode.addInfo.deviceId,
        to: targetNode.addInfo.deviceId,
        type: connectionData.loaiKetNoi,
        portNguon: connectionData.portNguon,
        portDich: connectionData.portDich,
        khoangCach: connectionData.khoangCach,
        trangThai: connectionData.trangThai,
        ghiChu: connectionData.ghiChu
      })

      // Override sourceID và targetID
      connector.sourceID = sourceNode.id
      connector.targetID = targetNode.id

      diagram.add(connector)
      diagram.dataBind()

      this.$toast.success(
        MESSAGES.SUCCESS.CONNECTION_CREATED(
          this.getConnectionTypeName(connectionData.loaiKetNoi),
          sourceNode.addInfo.deviceName,
          targetNode.addInfo.deviceName
        )
      )

      // TODO: Gọi API để lưu kết nối mới
      // this.saveConnection(connectionData)
    },

    async deleteConnection (connector) {
      this.$bvModal
        .msgBoxConfirm('Bạn có chắc chắn muốn xóa kết nối này?', {
          title: 'Xác nhận xóa',
          size: 'sm',
          buttonSize: 'sm',
          okVariant: 'danger',
          okTitle: 'Xóa',
          cancelTitle: 'Hủy',
          footerClass: 'p-2',
          hideHeaderClose: false,
          centered: true
        })
        .then(async (value) => {
          if (value) {
            try {
              this.$root.showLoading(true)

              // Lấy thông tin thiết bị từ connector
              const sourceDeviceId = connector.addInfo?.from
              const targetDeviceId = connector.addInfo?.to

              if (!sourceDeviceId || !targetDeviceId) {
                this.$toast.error('Không tìm thấy thông tin kết nối')
                return
              }

              // Gọi API xóa kết nối
              await this.$root.context.post('/web-ecms/sodo-ketnoi-dien/go-ket-noi-dien-thiet-bi', {
                thietBiId: sourceDeviceId,
                thietbiKetnoiId: targetDeviceId
              })

              // Xóa connector khỏi diagram
              if (!this.$refs.diagramObj) return
              const diagram = this.$refs.diagramObj.ej2Instances
              diagram.remove(connector)
              diagram.dataBind()

              this.$toast.success(MESSAGES.SUCCESS.CONNECTION_DELETED)
            } catch (error) {
              console.error('Error deleting connection:', error)
              const errorMessage = error?.response?.data?.message || 'Có lỗi khi xóa kết nối'
              this.$toast.error(errorMessage)
            } finally {
              this.$root.showLoading(false)
            }
          }
        })
        .catch((err) => {
          // User cancelled or error
        })
    },

    removeDeviceFromDiagram (node) {
      this.$bvModal
        .msgBoxConfirm(`Bạn có chắc chắn muốn xóa thiết bị "${node.addInfo.deviceName}" khỏi sơ đồ?`, {
          title: 'Xác nhận xóa',
          size: 'sm',
          buttonSize: 'sm',
          okVariant: 'danger',
          okTitle: 'Xóa',
          cancelTitle: 'Hủy',
          footerClass: 'p-2',
          hideHeaderClose: false,
          centered: true
        })
        .then((value) => {
          if (value) {
            if (!this.$refs.diagramObj) return
            const diagram = this.$refs.diagramObj.ej2Instances

            // 1. Tìm và xóa tất cả connectors liên quan đến node này
            const relatedConnectors = diagram.connectors.filter(
              (conn) => conn.sourceID === node.id || conn.targetID === node.id
            )

            relatedConnectors.forEach((conn) => {
              diagram.remove(conn)
            })

            // 2. Xóa node khỏi diagram
            diagram.remove(node)
            diagram.dataBind()

            // 3. Đưa thiết bị trở lại palette (unconnectedDevices)
            const device = {
              id: node.addInfo.deviceId,
              name: node.addInfo.deviceName,
              type: node.addInfo.deviceType,
              congSuat: node.addInfo.congSuat,
              trangThai: node.addInfo.trangThai || 'HOATDONG',
              level: node.addInfo.level || 0,
              parentId: node.addInfo.parentId || null,
              containerId: node.addInfo.containerId || null
            }

            // Kiểm tra xem thiết bị đã có trong unconnectedDevices chưa
            const existingIndex = this.unconnectedDevices.findIndex((d) => d.id === device.id)
            if (existingIndex === -1) {
              this.unconnectedDevices.push(device)
              this.$toast.success(MESSAGES.SUCCESS.DEVICE_REMOVED_TO_PALETTE(device.name))
            } else {
              this.$toast.success(MESSAGES.SUCCESS.DEVICE_REMOVED(device.name))
            }

            // TODO: Gọi API để cập nhật trạng thái thiết bị
            // this.updateDeviceStatus(device.id, 'UNCONNECTED')
          }
        })
        .catch((err) => {
          // User cancelled or error
        })
    },

    getConnectionTypeName (type) {
      const names = {
        DUONG_TRUNG_THE: 'Đường trung thế',
        DUONG_HA_THE: 'Đường hạ thế',
        NGUON_DU_PHONG: 'Nguồn dự phòng',
        NGUON_UPS: 'Nguồn UPS',
        NGUON_CHINH: 'Nguồn chính'
      }
      return names[type] || type
    },

    // Diagram drop event - khi thả node từ palette
    onDiagramNodeDrop (args) {
      if (!args.element || !args.element.addInfo) return

      const deviceType = args.element.addInfo.deviceType
      const deviceLabel = args.element.addInfo.deviceLabel

      if (deviceType) {
        // Cập nhật node vừa thả với thông tin đầy đủ
        const newDeviceId = `new_device_${Date.now()}`
        const device = {
          id: newDeviceId,
          name: `${deviceLabel} mới`,
          type: deviceType,
          level: 0,
          parentId: null,
          congSuat: 0,
          trangThai: 'HOATDONG',
          containerId: null
        }

        // Cập nhật addInfo của node
        args.element.addInfo = {
          deviceId: device.id,
          deviceName: device.name,
          deviceType: device.type,
          level: device.level,
          parentId: device.parentId,
          congSuat: device.congSuat,
          trangThai: device.trangThai,
          containerId: device.containerId
        }

        // Cập nhật HTML content
        const color = this.deviceColors[device.type] || this.deviceColors.DEFAULT
        args.element.shape.content = this.createDeviceHtml(device, color)

        this.$toast.success(`Đã thêm ${deviceLabel} mới vào sơ đồ`)

        // TODO: Gọi API để lưu device mới
        // this.saveNewDevice(device)
      }
    },

    onDrop (args) {
      if (!args.element || !args.element.addInfo) return

      const addInfo = args.element.addInfo

      // Case 1: Drop từ unconnected devices (có deviceId)
      if (addInfo.deviceId) {
        const device = {
          id: addInfo.deviceId,
          name: addInfo.deviceName,
          type: addInfo.deviceType,
          level: addInfo.level || 0,
          parentId: addInfo.parentId || null,
          congSuat: addInfo.congSuat,
          trangThai: addInfo.trangThai,
          containerId: addInfo.containerId || null
        }

        // Cập nhật node với HTML đầy đủ
        const color = this.deviceColors[device.type] || this.deviceColors.DEFAULT
        args.element.shape.content = this.createDeviceHtml(device, color)
        args.element.width = DIAGRAM_CONFIG.LAYOUT.NODE_WIDTH
        args.element.height = DIAGRAM_CONFIG.LAYOUT.NODE_HEIGHT
        args.element.ports = this.createNodePorts()

        this.$toast.success(MESSAGES.SUCCESS.DEVICE_ADDED(device.name))

        // Xóa khỏi danh sách chưa đấu nối
        const index = this.unconnectedDevices.findIndex((d) => d.id === addInfo.deviceId)
        if (index > -1) {
          this.unconnectedDevices.splice(index, 1)
        }

        // TODO: Gọi API để cập nhật trạng thái thiết bị
        // this.updateDeviceStatus(addInfo.deviceId, 'CONNECTED')
      }
      // Case 2: Drop từ device types (có deviceType nhưng không có deviceId)
      else if (addInfo.deviceType && !addInfo.deviceId) {
        const deviceLabel = addInfo.deviceLabel

        // Tạo device mới
        const newDeviceId = `new_device_${Date.now()}`
        const device = {
          id: newDeviceId,
          name: `${deviceLabel} mới`,
          type: addInfo.deviceType,
          level: 0,
          parentId: null,
          congSuat: 0,
          trangThai: 'HOATDONG',
          containerId: null
        }

        // Cập nhật node với thông tin device
        const node = this.createDeviceNode(device)
        node.offsetX = args.element.offsetX
        node.offsetY = args.element.offsetY
        node.id = `device_${newDeviceId}`

        // Xóa node palette cũ và thêm node device mới
        const diagram = this.$refs.diagramObj.ej2Instances
        diagram.remove(args.element)
        diagram.add(node)
        diagram.dataBind()

        this.$toast.success(`Đã thêm ${deviceLabel} mới vào sơ đồ`)

        // TODO: Gọi API để lưu device mới
        // this.saveNewDevice(device)
      }
    },

    drawContainers () {
      const diagram = this.$refs.diagramObj?.ej2Instances
      if (!diagram || !this.containersMap) return

      // Xóa các container cũ nếu có
      const oldContainers = diagram.nodes.filter((n) => n.addInfo?.isContainer)
      oldContainers.forEach((c) => diagram.remove(c))

      // Nhóm các device theo containerId
      const devicesByContainer = {}
      diagram.nodes.forEach((node) => {
        if (node.addInfo?.containerId && !node.addInfo?.isContainer) {
          const containerId = node.addInfo.containerId
          if (!devicesByContainer[containerId]) {
            devicesByContainer[containerId] = []
          }
          devicesByContainer[containerId].push(node)
        }
      })

      // Vẽ container cho mỗi nhóm
      Object.keys(devicesByContainer).forEach((containerId) => {
        const devices = devicesByContainer[containerId]
        if (devices.length === 0) return

        const container = this.containersMap[containerId]
        if (!container) return

        // Tính bounds của tất cả devices trong container
        let minX = Infinity
        let minY = Infinity
        let maxX = -Infinity
        let maxY = -Infinity

        devices.forEach((device) => {
          const x = device.offsetX
          const y = device.offsetY
          const w = device.width || 80
          const h = device.height || 60

          minX = Math.min(minX, x - w / 2)
          maxX = Math.max(maxX, x + w / 2)
          minY = Math.min(minY, y - h / 2)
          maxY = Math.max(maxY, y + h / 2)
        })

        // Thêm padding
        const padding = DIAGRAM_CONFIG.CONTAINER.PADDING
        minX -= padding
        minY -= padding
        maxX += padding
        maxY += padding

        const width = maxX - minX
        const height = maxY - minY
        const centerX = (minX + maxX) / 2
        const centerY = (minY + maxY) / 2

        // Tạo container rectangle với màu sắc khác nhau cho ROOM, SYSTEM, ZONE
        let borderColor, borderWidth, borderStyle, zIndex

        if (container.type === 'ROOM') {
          borderColor = '#9C27B0' // Màu tím cho phòng
          borderWidth = 3
          borderStyle = '' // Solid line
          zIndex = -10 // Phòng container ở dưới thiết bị
        } else if (container.type === 'SYSTEM') {
          borderColor = '#2196F3' // Màu xanh dương cho hệ thống
          borderWidth = 3
          borderStyle = ''
          zIndex = -20 // Hệ thống ở dưới phòng
        } else {
          borderColor = '#FF9800' // Màu cam cho zone
          borderWidth = 2
          borderStyle = '5,5'
          zIndex = -30 // Zone ở dưới cùng
        }

        const containerNode = {
          id: `container_${containerId}`,
          offsetX: centerX,
          offsetY: centerY,
          width: width,
          height: height,
          shape: {
            type: 'Basic',
            shape: 'Rectangle',
            cornerRadius: 8
          },
          style: {
            fill: 'rgba(255, 255, 255, 0.5)',
            strokeColor: borderColor,
            strokeWidth: borderWidth,
            strokeDashArray: borderStyle
          },
          constraints:
            NodeConstraints.Default &
            ~NodeConstraints.Resize &
            ~NodeConstraints.Rotate &
            ~NodeConstraints.InConnect &
            ~NodeConstraints.OutConnect,
          zIndex: zIndex,
          annotations: [
            {
              content: container.name,
              offset: { x: 0.5, y: 0 },
              margin: { top: -20 },
              style: {
                fontSize: 12,
                bold: true,
                color: borderColor,
                fill: 'white',
                strokeColor: borderColor,
                strokeWidth: 1
              }
            }
          ],
          addInfo: {
            isContainer: true,
            containerId: containerId,
            containerType: container.type
          }
        }

        diagram.add(containerNode)
      })

      diagram.dataBind()
    }
  }
}
</script>

<style scoped>
.sodo-ketnoi-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  background: #f5f5f5;
  -webkit-user-select: none;
  /* Cho Chrome, Safari, Opera */
  -moz-user-select: none;
  /* Cho Firefox */
  -ms-user-select: none;
  /* Cho IE/Edge */
  user-select: none;
}

.page-content {
  flex: 1;
  padding: 0 !important;
  display: flex;
  position: relative;
  overflow: hidden;
}

.diagram-workspace {
  display: flex;
  width: 100%;
  height: 100%;
  position: relative;
}

.palette-container {
  width: 260px;
  background: white;
  border-right: 1px solid #ddd;
  overflow: auto;
  flex-shrink: 0;
}

.diagram-container {
  flex: 1;
  position: relative;
  overflow: hidden;
  background: white;
}

#diagram-ketnoi {
  width: 100%;
  height: 100%;
}

.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  color: #666;
}

.list-actions-top {
  background: white;
  border-bottom: 1px solid #ddd;
  flex-shrink: 0;
}

.list-actions-top .list {
  display: flex;
  gap: 10px;
  list-style: none;
  margin: 0;
  padding: 0;
}

.list-actions-top .list li {
  margin: 0;
}

/* Dropdown styling */
.list-actions-top .dropdown {
  display: inline-block;
}

.list-actions-top .dropdown .btn-link {
  padding: 0;
  text-decoration: none;
  color: inherit;
}

.list-actions-top .dropdown .btn-link:hover {
  text-decoration: none;
}

.list-actions-top .dropdown-menu {
  min-width: 180px;
}

.list-actions-top .dropdown-item {
  padding: 8px 16px;
  font-size: 14px;
}

.list-actions-top .dropdown-item i {
  margin-right: 8px;
  width: 16px;
  text-align: center;
}

/* Legend in Palette */
.legend-section-palette {
  background: white;
  border-top: 2px solid #e9ecef;
  margin-top: 0;
}

.legend-header-palette {
  padding: 8px 15px;
  background: #f8f9fa;
  border-bottom: 1px solid #e9ecef;
  flex-shrink: 0;
}

.legend-header-palette h6 {
  margin: 0;
  font-size: 13px;
  font-weight: 600;
  color: #333;
  text-transform: uppercase;
}

.legend-content-palette {
  padding: 10px 12px;
  overflow-y: auto;
  font-size: 11px;
  min-height: 0;
  /* Important for flex scrolling */
}

.legend-group {
  margin-bottom: 10px;
}

.legend-group:last-child {
  margin-bottom: 0;
}

.legend-title {
  font-size: 10px;
  font-weight: 600;
  color: #666;
  margin-bottom: 5px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 2px 0;
  font-size: 11px;
  color: #333;
}

.legend-color {
  width: 18px;
  height: 18px;
  border-radius: 3px;
  border: 1px solid #ddd;
  flex-shrink: 0;
}

.legend-line {
  width: 28px;
  height: 3px;
  flex-shrink: 0;
  border-radius: 2px;
}

.legend-status {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  flex-shrink: 0;
}

/* Palette Styling */
.palette-container {
  background: #fafafa !important;
}

.palette-container .e-symbolpalette {
  background: transparent !important;
}

.palette-container .e-symbolpalette .e-symbol-palette {
  padding: 15px !important;
  background: #fafafa !important;
}

.palette-container .e-symbolpalette .e-accordion {
  border: 0px !important;
  background: transparent !important;
}

.palette-container .e-symbolpalette .e-accordion .e-acrdn-item {
  border: 0px !important;
  margin-bottom: 10px !important;
  background: white !important;
  border-radius: 6px !important;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08) !important;
}

.palette-container .e-symbolpalette .e-accordion .e-acrdn-header {
  background: #f8f9fa !important;
  border-radius: 6px 6px 0 0 !important;
  padding: 12px 15px !important;
  font-weight: 600 !important;
  font-size: 13px !important;
  color: #333 !important;
  border-bottom: 1px solid #e9ecef !important;
}

.palette-container .e-symbolpalette .e-accordion .e-acrdn-header:hover {
  background: #e9ecef !important;
}

.palette-container .e-symbolpalette .e-accordion .e-acrdn-panel {
  padding: 10px !important;
  background: white !important;
  border-radius: 0 0 6px 6px !important;
}

.palette-container .e-symbolpalette .e-symbol-draggable {
  margin: 6px !important;
  display: inline-block !important;
  border-radius: 4px !important;
  overflow: hidden !important;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1) !important;
  transition: all 0.2s ease !important;
  cursor: grab !important;
}

/* Device palette card hover */
.palette-container .e-symbolpalette .e-symbol-draggable:hover {
  transform: translateY(-2px) scale(1.02);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15) !important;
  transition: all 0.2s ease;
}

.palette-container .e-symbolpalette .e-symbol-draggable:active {
  cursor: grabbing !important;
  transform: scale(0.98);
}

/* Tắt selection/highlight trên diagram */
.diagram-container .e-diagram .e-diagram-layer svg g.e-adorner-layer {
  display: none !important;
}

.diagram-container .e-diagram .e-diagram-layer svg g[id*='selector'] {
  display: none !important;
}

.diagram-container .e-diagram .e-node.e-selected,
.diagram-container .e-diagram .e-connector.e-selected {
  outline: none !important;
  box-shadow: none !important;
}

/* Tắt border khi hover/select node */
#diagram-ketnoi .e-node.e-selected,
#diagram-ketnoi .e-connector.e-selected {
  outline: none !important;
}

#diagram-ketnoi .e-adorner-layer {
  display: none !important;
}

/* Tắt handles và selection border */
#diagram-ketnoi .e-diagram-resize-handle,
#diagram-ketnoi .e-diagram-rotate-handle,
#diagram-ketnoi .e-diagram-selector {
  display: none !important;
}

#diagram-ketnoi .e-diagram .e-diagram-layer .e-adorner-layer {
  pointer-events: none !important;
  opacity: 0 !important;
}

/* Export Modal Styling */
.export-options {
  padding: 10px 0;
}

.export-options .btn {
  text-align: left;
  font-size: 14px;
  padding: 12px 20px;
  border-width: 2px;
  transition: all 0.2s ease;
}

.export-options .btn i {
  margin-right: 10px;
  width: 20px;
  text-align: center;
}

.export-options .btn:hover {
  transform: translateX(5px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
</style>

<style>
.chi-tiet-ket-noi-dien-rack-modal .modal-dialog {
  max-width: 95vw !important;
  width: 95vw !important;
  margin: 1rem auto;
}

.chi-tiet-ket-noi-dien-rack-modal .modal-content {
  height: 95vh;
}

.chi-tiet-ket-noi-dien-rack-modal .modal-body {
  padding: 0;
  height: calc(95vh - 60px);
  overflow-y: scroll !important;
}
</style>
