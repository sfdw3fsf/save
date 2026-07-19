<template src="./index.template.html"></template>

<script>
import Vue from 'vue'
import {
  DiagramPlugin,
  SymbolPalettePlugin,
  HierarchicalTree,
  DataBinding,
  DiagramContextMenu,
  PrintAndExport,
  NodeConstraints,
  ConnectorConstraints,
  PortVisibility,
  PortConstraints,
  DiagramTools
} from '@syncfusion/ej2-vue-diagrams'
import { DataManager } from '@syncfusion/ej2-data'
import ModalChiTietThietBi from '../sodo-phong-zone/components/chitiet-thietbi/index.vue'
import ConnectionStatisticsSidebar from './components/ConnectionStatisticsSidebar.vue'
import ModalChonLoaiKetNoi from './components/ModalChonLoaiKetNoi.vue'
import html2canvas from 'html2canvas'
import { DIAGRAM_CONFIG, MESSAGES } from './diagram-config.js'
import networkDiagramApi from './api.js'

const DEVICE_TYPE_BY_PHANLOAITBI_ID = Object.freeze({
  207: 'SANDIRECTOR',
  206: 'SANSWITCH',
  217: 'ROUTER',
  216: 'SWITCH',
  218: 'FIREWALL',
  219: 'LOADBALACING',
  220: 'WAF',
  901: 'CONTROLLER',
  902: 'ACCESSPOINT',
  903: 'DBFW',
  904: 'PATCH_PANNEL'
})

const DEVICE_TYPE_BY_NORMALIZED_VALUE = Object.freeze({
  SANDIRECTOR: 'SANDIRECTOR',
  SAN_DIRECTOR: 'SANDIRECTOR',
  SANSWITCH: 'SANSWITCH',
  SAN_SWITCH: 'SANSWITCH',
  ROUTER: 'ROUTER',
  SWITCH: 'SWITCH',
  CORE_SWITCH: 'SWITCH',
  DISTRIBUTION_SWITCH: 'SWITCH',
  ACCESS_SWITCH: 'SWITCH',
  FIREWALL: 'FIREWALL',
  LOADBALACING: 'LOADBALACING',
  LOADBALANCING: 'LOADBALACING',
  WAF: 'WAF',
  CONTROLLER: 'CONTROLLER',
  ACCESSPOINT: 'ACCESSPOINT',
  ACCESS_POINT: 'ACCESSPOINT',
  ACCESS_POINTS: 'ACCESSPOINT',
  AP: 'ACCESSPOINT',
  DBFW: 'DBFW',
  PATCH_PANNEL: 'PATCH_PANNEL',
  PATCH_PANEL: 'PATCH_PANNEL',
  PATCHPANEL: 'PATCH_PANNEL'
})

const normalizeDeviceTypeKey = (value) => {
  if (value === undefined || value === null) {
    return ''
  }
  return String(value)
    .trim()
    .toUpperCase()
    .replace(/[^A-Z0-9]+/g, '_')
}

const getDeviceTypeFromPhanLoaiId = (phanLoaiTbiId) => {
  if (phanLoaiTbiId === undefined || phanLoaiTbiId === null || phanLoaiTbiId === '') {
    return null
  }
  return (
    DEVICE_TYPE_BY_PHANLOAITBI_ID[phanLoaiTbiId] ||
    DEVICE_TYPE_BY_PHANLOAITBI_ID[Number(phanLoaiTbiId)] ||
    null
  )
}

const getDeviceTypeFromValue = (value) => {
  const normalized = normalizeDeviceTypeKey(value)
  if (!normalized) {
    return null
  }
  return DEVICE_TYPE_BY_NORMALIZED_VALUE[normalized] || null
}

Vue.use(DiagramPlugin)
Vue.use(SymbolPalettePlugin)

export default {
  name: 'SoDoDauNoiMang',

  components: {
    ModalChiTietThietBi,
    ConnectionStatisticsSidebar,
    ModalChonLoaiKetNoi
  },

  props: {
    idcId: {
      type: [String, Number],
      required: true,
      default: 1,
    }
  },

  provide: {
    diagram: [HierarchicalTree, DataBinding, DiagramContextMenu, PrintAndExport]
  },

  data() {
    return {
      isDataLoaded: false,
      nodes: [],
      connectors: [],
      selectedThietBiId: null,
      containersMap: {},
      containerOldPositions: {},

      // Debounced functions (will be initialized in created())
      debouncedDrawContainers: null,
      debouncedResizeDiagram: null,

      // IntersectionObserver để detect khi component được hiển thị
      visibilityObserver: null,

      // MutationObserver để theo dõi thay đổi DOM của accordion
      accordionMutationObserver: null,

      // Connection mode
      isConnectMode: false,
      sourceNode: null,
      dragConnectionMode: false,

      // Connection dialog
      connectionDialog: {
        sourceNode: null,
        targetNode: null,
        selectedType: DIAGRAM_CONFIG.CONNECTION.DEFAULT_TYPE,
        tempConnectorId: null
      },

      // Drawing object for connector - đường thẳng khi kéo
      drawingObject: null,

      // Tool settings
      tool: DiagramTools.Default,

      // Unconnected devices (thiết bị chưa đấu nối)
      unconnectedDevices: [],

      // Layout settings - Manual positioning
      layout: {
        type: 'None'
      },

      // Snap settings
      snapSettings: {
        constraints: DIAGRAM_CONFIG.SNAP.CONSTRAINTS,
        snapAngle: DIAGRAM_CONFIG.SNAP.SNAP_ANGLE,
        snapObjectDistance: DIAGRAM_CONFIG.SNAP.SNAP_OBJECT_DISTANCE
      },

      // Scroll settings
      scrollSettings: {
        scrollLimit: DIAGRAM_CONFIG.SCROLL.SCROLL_LIMIT,
        minZoom: DIAGRAM_CONFIG.SCROLL.MIN_ZOOM,
        maxZoom: DIAGRAM_CONFIG.SCROLL.MAX_ZOOM,
        canAutoScroll: DIAGRAM_CONFIG.SCROLL.CAN_AUTO_SCROLL
      },

      // Selected items settings - Tắt các handles khi chọn node
      selectedItems: {
        constraints: DIAGRAM_CONFIG.SELECTED_ITEMS.CONSTRAINTS
      },

      // Context menu
      contextMenuSettings: {
        show: DIAGRAM_CONFIG.CONTEXT_MENU.SHOW,
        items: DIAGRAM_CONFIG.CONTEXT_MENU.ITEMS,
        showCustomMenuOnly: DIAGRAM_CONFIG.CONTEXT_MENU.SHOW_CUSTOM_MENU_ONLY
      },

      // Màu sắc theo loại thiết bị mạng
      deviceColors: DIAGRAM_CONFIG.DEVICE.COLORS,

      // Device types for palette - sẽ được cập nhật động từ API
      deviceTypes: [],
      
      // Map để lưu thông tin loại thiết bị từ API (PHANLOAITBI_ID -> { id, name, code })
      deviceTypesMap: {},

      // Statistics data for ConnectionStatisticsSidebar
      statistics: {}
    }
  },

  computed: {
    palettes() {
      // Nhóm thiết bị theo loại
      const devicesByType = {}
      
      this.unconnectedDevices.forEach((device) => {
        const deviceType = device.type || 'DEFAULT'
        if (!devicesByType[deviceType]) {
          devicesByType[deviceType] = []
        }
        devicesByType[deviceType].push(device)
      })

      // Tạo palette cho mỗi loại thiết bị
      const palettesList = []
      
      // Sử dụng thứ tự từ deviceTypesMap (theo thứ tự xuất hiện trong API)
      const deviceTypeOrder = Object.keys(this.deviceTypesMap)
      
      // Thêm các loại có trong config cũ (nếu chưa có trong map)
      const configTypeOrder = DIAGRAM_CONFIG.DEVICE.TYPE_ORDER || []
      configTypeOrder.forEach((type) => {
        if (!deviceTypeOrder.includes(type)) {
          deviceTypeOrder.push(type)
        }
      })
      
      // Render theo thứ tự - CHỈ hiển thị các loại có thiết bị
      deviceTypeOrder.forEach((type) => {
        const deviceCount = devicesByType[type] ? devicesByType[type].length : 0
        
        // Chỉ thêm vào danh sách nếu có thiết bị
        if (deviceCount > 0) {
          palettesList.push({
            id: `palette_${type.toLowerCase()}`,
            expanded: DIAGRAM_CONFIG.PALETTE.EXPANDED_DEFAULT,
            title: `${this.getDeviceTypeName(type)} (${deviceCount})`,
            symbols: devicesByType[type].map((device) => this.createDevicePaletteSymbol(device))
          })
        }
      })

      // Thêm các loại thiết bị khác (nếu có thiết bị nhưng không có trong deviceTypesMap)
      Object.keys(devicesByType).forEach((type) => {
        if (!deviceTypeOrder.includes(type) && devicesByType[type].length > 0) {
          palettesList.push({
            id: `palette_${type.toLowerCase()}`,
            expanded: DIAGRAM_CONFIG.PALETTE.EXPANDED_DEFAULT,
            title: `${this.getDeviceTypeName(type)} (${devicesByType[type].length})`,
            symbols: devicesByType[type].map((device) => this.createDevicePaletteSymbol(device))
          })
        }
      })

      return palettesList
    },

    // Computed property để lấy device types động từ API
    dynamicDeviceTypes() {
      // Tạo mảng device types từ deviceTypesMap
      return Object.values(this.deviceTypesMap).map(typeInfo => ({
        type: typeInfo.type, // Key dùng trong code (ví dụ: 'ROUTER', 'FIREWALL')
        label: typeInfo.name, // Tên hiển thị từ PHANLOAI_TBI
        id: typeInfo.id, // PHANLOAITBI_ID
        code: typeInfo.code // MA_LOAITBI (nếu có)
      }))
    }
  },

  watch: {
    // Theo dõi thay đổi của palettes để cập nhật tooltip
    palettes: {
      handler() {
        this.$nextTick(() => {
          this.setSymbolTooltips()
          this.applyAccordionContentStyles()
        })
      },
      deep: true,
      immediate: false
    },
    // Theo dõi unconnectedDevices
    unconnectedDevices: {
      handler() {
        this.$nextTick(() => {
          if (this.$refs.symbolpalette) {
            const paletteObj = this.$refs.symbolpalette.ej2Instances
            if (paletteObj) {
              paletteObj.refresh()
              this.$nextTick(() => {
                this.setSymbolTooltips()
                this.applyAccordionContentStyles()
              })
            }
          }
        })
      },
      deep: true
    },
    // Theo dõi isDataLoaded để resize diagram khi data đã load xong
    isDataLoaded: {
      handler(newVal) {
        if (newVal) {
          this.$nextTick(() => {
            // Đợi một chút để đảm bảo diagram đã render xong
            setTimeout(() => {
              this.resizeDiagram()
              this.onFitToPage()
            }, 150)
          })
        }
      },
      immediate: false
    }
  },

  created() {
    // Initialize debounced functions
    this.debouncedDrawContainers = this.debounce(this.drawContainers, DIAGRAM_CONFIG.ANIMATION.CONTAINER_DRAW_DELAY)
    
    // Debounced resize function
    this.debouncedResizeDiagram = this.debounce(() => {
      this.resizeDiagram()
    }, 200)
  },

  updated() {
    // Resize diagram khi component được update (ví dụ khi tab được active)
    if (this.isDataLoaded && this.$refs.diagramObj) {
      const diagram = this.$refs.diagramObj.ej2Instances
      if (diagram) {
        // Sử dụng requestAnimationFrame để đảm bảo DOM đã render xong
        this.$nextTick(() => {
          requestAnimationFrame(() => {
            this.resizeDiagram()
          })
        })
      }
    }
  },

  beforeDestroy() {
    // Remove event listener
    window.removeEventListener('resize', this.debouncedResizeDiagram)

    // Disconnect IntersectionObserver
    if (this.visibilityObserver) {
      this.visibilityObserver.disconnect()
      this.visibilityObserver = null
    }

    // Disconnect MutationObserver
    if (this.accordionMutationObserver) {
      this.accordionMutationObserver.disconnect()
      this.accordionMutationObserver = null
    }

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
    if (this.debouncedResizeDiagram) {
      this.debouncedResizeDiagram = null
    }
  },

  async mounted() {
    // Khởi tạo drawingObject cho connector type Orthogonal (đường kết nối ngắn hơn)
    this.drawingObject = {
      type: 'Orthogonal',
      connectorType: 'Orthogonal',
      sourcePoint: { x: 0.5, y: 1 }, // Điểm kết nối ở giữa cạnh dưới của node nguồn
      targetPoint: { x: 0.5, y: 0 }, // Điểm kết nối ở giữa cạnh trên của node đích
      style: {
        strokeColor: '#2196F3',
        strokeWidth: 3
      },
      targetDecorator: {
        shape: 'None'
      },
      constraints: ConnectorConstraints.Default
    }

    // Thêm event listener để resize khi window resize
    window.addEventListener('resize', this.debouncedResizeDiagram)

    // Setup IntersectionObserver để detect khi component được hiển thị
    this.$nextTick(() => {
      if (this.$el) {
        this.visibilityObserver = new IntersectionObserver(
          (entries) => {
            entries.forEach((entry) => {
              if (entry.isIntersecting && this.isDataLoaded) {
                // Component được hiển thị, resize diagram
                setTimeout(() => {
                  this.resizeDiagram()
                }, 100)
              }
            })
          },
          { threshold: 0.1 }
        )
        this.visibilityObserver.observe(this.$el)
      }

      // Setup MutationObserver để theo dõi thay đổi DOM của accordion
      const paletteContainer = this.$el?.querySelector('.palette-container')
      if (paletteContainer) {
        this.accordionMutationObserver = new MutationObserver(() => {
          // Khi DOM thay đổi (accordion expand/collapse), apply lại styles
          this.applyAccordionContentStyles()
        })

        // Theo dõi thay đổi trong palette container
        this.accordionMutationObserver.observe(paletteContainer, {
          childList: true,
          subtree: true,
          attributes: true,
          attributeFilter: ['class']
        })
      }
    })

    await this.loadUnconnectedDevices()
    await this.loadData()
    await this.loadStatistics()

    this.$nextTick(() => {
      if (this.$refs.symbolpalette) {
        const paletteObj = this.$refs.symbolpalette.ej2Instances
        if (paletteObj) {
          paletteObj.refresh()
          
          // Set tooltip cho các container element
          this.setSymbolTooltips()
          
          // Apply styles cho accordion content sau khi DOM render xong
          setTimeout(() => {
            this.applyAccordionContentStyles()
          }, 150)
        }
      }

      // Resize diagram sau khi tất cả đã render xong
      if (this.$refs.diagramObj) {
        const diagram = this.$refs.diagramObj.ej2Instances
        if (diagram) {
          // Đợi một chút để đảm bảo DOM đã render xong
          setTimeout(() => {
            this.resizeDiagram()
            this.onFitToPage()
          }, 100)
        }
      }
    })
  },

  methods: {
    createDevicePaletteSymbol(device) {
      const color = this.deviceColors[device.type] || this.deviceColors.DEFAULT
      const icon = this.getDeviceIcon(device.type)
      const deviceTypeLabel = this.getDeviceTypeName(device.type)

      return {
        id: `symbol_${device.id}`,
        width: DIAGRAM_CONFIG.NODE.PALETTE_WIDTH,
        height: DIAGRAM_CONFIG.NODE.PALETTE_HEIGHT,
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate & ~NodeConstraints.ConnectionPoint,
        handleConstraints: 0,
        shape: {
          type: 'HTML',
          content: `<div title="${device.name}" style="width: ${DIAGRAM_CONFIG.NODE.PALETTE_WIDTH}px; height: ${DIAGRAM_CONFIG.NODE.PALETTE_HEIGHT}px; display: flex; flex-direction: column; align-items: center; justify-content: center; background: white; border: 2px solid ${color}; border-radius: 6px; padding: 8px; box-sizing: border-box; gap: 4px;"><div style="font-size: 24px; line-height: 1;">${icon}</div><div style="font-size: 11px; font-weight: 600; color: #333; text-align: center; word-wrap: break-word; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">${device.name}</div></div>`
        },
        addInfo: {
          deviceId: device.id,
          deviceName: device.name,
          deviceType: device.type,
          level: device.level || 0,
          parentId: device.parentId || null,
          trangThai: device.trangThai,
          containerId: device.containerId || null
        }
      }
    },

    async loadUnconnectedDevices() {
      try {
        this.$root.showLoading(true)
        let devices = []

        if (this.$root?.context) {
          const response = await networkDiagramApi.getUnconnectedDevices(this.$root.context, {
            idcId: this.idcId
          })

          if (!response?.error_code || response.error_code === 'BSS-00000000') {
            const payload = response?.data || []
            devices = this.normalizeUnconnectedDevices(payload)
          } else {
            throw new Error(response?.error_msg || 'Failed to load unconnected devices')
          }
        }
        this.unconnectedDevices = devices
      } catch (error) {
        console.error('Error loading unconnected devices:', error)
        this.$toast.error(MESSAGES.ERROR.LOAD_UNCONNECTED_FAILED)
      } finally {
        this.$root.showLoading(false)
      }
    },

    async loadStatistics() {
      try {
        if (this.$root?.context) {
          const response = await networkDiagramApi.getStatistics(this.$root.context, this.idcId)

          if (!response?.error_code || response.error_code === 'BSS-00000000') {
            this.statistics = response?.data || {}
          } else {
            throw new Error(response?.error_msg || 'Failed to load statistics')
          }
        }
      } catch (error) {
        console.error('Error loading statistics:', error)
        // Không hiển thị toast error để không làm phiền user, chỉ log
      }
    },

    getPaletteNodeDefaults(node) {
      return {
        width: DIAGRAM_CONFIG.NODE.PALETTE_DEFAULTS_WIDTH,
        height: DIAGRAM_CONFIG.NODE.PALETTE_DEFAULTS_HEIGHT,
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate
      }
    },

    getSymbolInfo(symbol) {
      return {
        fit: true,
        description: {
          text: symbol.addInfo ? symbol.addInfo.deviceName || '' : '',
          overflow: 'Wrap'
        }
      }
    },

    onSave() {
      this.$toast.info('Chức năng lưu đang phát triển')
    },

    async loadData() {
      try {
        this.$root.showLoading(true)

        let diagramData = null

        if (this.$root?.context) {
          const response = await networkDiagramApi.getDiagramByIDC(this.$root.context, this.idcId)

          if (!response?.error_code || response.error_code === 'BSS-00000000') {
            const payload = response?.data || []
            // Transform API response to diagram data format (new format)
            diagramData = this.transformApiResponseToDiagramData(payload)
          } else {
            throw new Error(response?.error_msg || 'Failed to load diagram data')
          }
        }
        
        if (diagramData) {
          this.buildDiagram(diagramData)
        } else {
          this.$toast.error(MESSAGES.ERROR.LOAD_FAILED)
        }
      } catch (error) {
        console.error('Error loading network diagram:', error)
        this.$toast.error(MESSAGES.ERROR.GENERAL_ERROR)
      } finally {
        this.isDataLoaded = true
        this.$root.showLoading(false)

        this.$nextTick(() => {
          requestAnimationFrame(() => {
            // Resize diagram để đảm bảo chiếm hết không gian
            this.resizeDiagram()
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

    transformApiResponseToDiagramData(apiData) {
      if (!apiData || !Array.isArray(apiData)) {
        return { devices: [], connections: [], containers: [] }
      }

      // Collect unique devices and connections
      const devicesMap = new Map()
      const connections = []
      const systemsMap = new Map()
      const roomsMap = new Map()

      apiData.forEach((conn) => {
        // Add source device
        if (conn.THIETBI_NGUON_ID) {
          if (!devicesMap.has(conn.THIETBI_NGUON_ID)) {
            const deviceType = this.mapDeviceTypeCode(
              conn.THIETBI_NGUON_MA_PHANLOAI_TBI,
              conn.THIETBI_NGUON_MA_LOAITBI,
              conn.THIETBI_NGUON_PHANLOAI_TBI
            )
            
            devicesMap.set(conn.THIETBI_NGUON_ID, {
              id: conn.THIETBI_NGUON_ID,
              name: conn.THIETBI_NGUON_TEN || conn.THIETBI_NGUON_MATHIETBI || 'Thiết bị nguồn',
              type: deviceType,
              level: 0, // Will be calculated if needed
              parentId: null,
              trangThai: 'HOATDONG', // Default, can be updated if API provides status
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
            const deviceType = this.mapDeviceTypeCode(
              conn.THIETBI_DICH_MA_PHANLOAI_TBI,
              conn.THIETBI_DICH_MA_LOAITBI,
              conn.THIETBI_DICH_PHANLOAI_TBI
            )
            
            devicesMap.set(conn.THIETBI_DICH_ID, {
              id: conn.THIETBI_DICH_ID,
              name: conn.THIETBI_DICH_TEN || conn.THIETBI_DICH_MATHIETBI || 'Thiết bị đích',
              type: deviceType,
              level: 0, // Will be calculated if needed
              parentId: null,
              trangThai: 'HOATDONG', // Default, can be updated if API provides status
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
        }

        // Add connection
        if (conn.THIETBI_NGUON_ID && conn.THIETBI_DICH_ID) {
          // Determine connection type based on port classification or default
          let connectionType = DIAGRAM_CONFIG.CONNECTION.DEFAULT_TYPE
          
          // You can add logic here to determine connection type based on:
          // - PORT_NGUON_PHANLOAI, PORT_DICH_PHANLOAI
          // - Device types
          // - Other factors from the API
          // For now, using default type
          
          connections.push({
            from: conn.THIETBI_NGUON_ID,
            to: conn.THIETBI_DICH_ID,
            type: connectionType,
            portNguon: conn.PORT_NGUON_TEN || '',
            portDich: conn.PORT_DICH_TEN || '',
            portNguonId: conn.PORT_NGUON_ID,
            portDichId: conn.PORT_DICH_ID
          })
        }
      })

      // Convert systems map and rooms map to containers array
      const containers = [
        ...Array.from(systemsMap.values()),
        ...Array.from(roomsMap.values())
      ]

      // Tính level tự động dựa trên TYPE_ORDER trong config
      const devices = Array.from(devicesMap.values())
      this.assignLevelsFromTypeOrder(devices)

      return {
        devices,
        connections,
        containers
      }
    },

    buildDiagram(data) {
      this.nodes = []
      this.connectors = []

      this.containersMap = {}
      if (data.containers && Array.isArray(data.containers)) {
        data.containers.forEach((container) => {
          this.containersMap[container.id] = container
        })
      }

      if (data.devices && Array.isArray(data.devices)) {
        const positions = this.calculateManualPositions(data.devices)
        data.devices.forEach((device) => {
          const node = this.createDeviceNode(device)
          const pos = positions[device.id]
          if (pos) {
            node.offsetX = pos.x
            node.offsetY = pos.y
          }
          this.nodes.push(node)
        })
      }

      if (data.connections && Array.isArray(data.connections)) {
        data.connections.forEach((conn) => {
          this.connectors.push(this.createConnector(conn))
        })
      }
    },

    calculateManualPositions(devices) {
      const positions = {}
      const { NODE_WIDTH, NODE_HEIGHT, HORIZONTAL_GAP, VERTICAL_GAP, START_X, START_Y } = DIAGRAM_CONFIG.LAYOUT

      const devicesByLevel = {}
      devices.forEach((device) => {
        if (!devicesByLevel[device.level]) {
          devicesByLevel[device.level] = []
        }
        devicesByLevel[device.level].push(device)
      })

      Object.keys(devicesByLevel).forEach((level) => {
        const levelDevices = devicesByLevel[level]
        const levelNum = parseInt(level)
        const y = START_Y + levelNum * VERTICAL_GAP

        const totalWidth = (levelDevices.length - 1) * HORIZONTAL_GAP
        const startXForLevel = START_X - totalWidth / 2

        levelDevices.forEach((device, index) => {
          const x = startXForLevel + index * HORIZONTAL_GAP
          positions[device.id] = { x, y }
        })
      })

      return positions
    },

    createDeviceNode(device) {
      const color = this.deviceColors[device.type] || this.deviceColors.DEFAULT

      // Ưu tiên phòng container hơn hệ thống container
      const effectiveContainerId = device.phongThietBiId || device.containerId

      return {
        id: `device_${device.id}`,
        width: DIAGRAM_CONFIG.NODE.WIDTH,
        height: DIAGRAM_CONFIG.NODE.HEIGHT,
        shape: {
          type: 'HTML',
          content: this.createDeviceHtml(device, color)
        },
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate,
        ports: this.createNodePorts(),
        addInfo: {
          deviceId: device.id,
          deviceName: device.name,
          deviceType: device.type,
          level: device.level,
          parentId: device.parentId,
          trangThai: device.trangThai,
          containerId: effectiveContainerId,
          phongThietBi: device.phongThietBi,
          phongThietBiId: device.phongThietBiId,
          rackThietBi: device.rackThietBi,
          rackThietBiId: device.rackThietBiId
        }
      }
    },

    createNodePorts() {
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

    createDeviceHtml(device, color) {
      const icon = this.getDeviceIcon(device.type)
      const statusColor = device.trangThai === 'HOATDONG' ? DIAGRAM_CONFIG.STATUS.ACTIVE_COLOR : DIAGRAM_CONFIG.STATUS.INACTIVE_COLOR

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
          <div style="
            font-size: 20px;
            line-height: 1;
          ">${icon}</div>
          
          <div style="
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: center;
          ">
            <div style="
              font-size: 10px;
              font-weight: bold;
              color: #333;
              margin-bottom: 4px;
              overflow: hidden;
              text-overflow: ellipsis;
            ">${device.name}</div>
            
            <div style="
              font-size: 11px;
              color: #666;
              font-weight: 500;
            ">${this.getDeviceTypeName(device.type)}</div>
          </div>
          
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

    getDeviceIcon(type) {
      const icons = DIAGRAM_CONFIG.DEVICE.ICONS
      return icons[type] || icons.DEFAULT
    },

    createConnector(connection) {
      let strokeColor = DIAGRAM_CONFIG.CONNECTION.COLORS.DEFAULT
      let strokeWidth = DIAGRAM_CONFIG.CONNECTION.STROKE_WIDTH

      // Mapping theo chú thích
      if (connection.type === 'DUONG_QUANG') {
        strokeColor = DIAGRAM_CONFIG.CONNECTION.COLORS.DUONG_QUANG // Đỏ - Đường quang
      } else if (connection.type === 'DUONG_DONG') {
        strokeColor = DIAGRAM_CONFIG.CONNECTION.COLORS.DUONG_DONG // Xanh dương - Đường đồng
      } else if (connection.type === 'DUONG_DU_PHONG') {
        strokeColor = DIAGRAM_CONFIG.CONNECTION.COLORS.DUONG_DU_PHONG // Cam - Đường kết nối dự phòng
      } else if (connection.type === 'SINGLE_MODE') {
        strokeColor = DIAGRAM_CONFIG.CONNECTION.COLORS.SINGLE_MODE // Tím - Single mode
      } else if (connection.type === 'MULTI_MODE') {
        strokeColor = DIAGRAM_CONFIG.CONNECTION.COLORS.MULTI_MODE // Tím đậm - Multi mode
      }

      return {
        id: `conn_${connection.from}_${connection.to}`,
        sourceID: `device_${connection.from}`,
        targetID: `device_${connection.to}`,
        type: 'Straight',
        style: {
          strokeColor: strokeColor,
          strokeWidth: strokeWidth
        },
        targetDecorator: {
          shape: 'None'
        },
        annotations: [],
        constraints: ConnectorConstraints.Default & ~ConnectorConstraints.Drag & ~ConnectorConstraints.DragSourceEnd & ~ConnectorConstraints.DragTargetEnd,
        addInfo: {
          connectionType: connection.type,
          from: connection.from,
          to: connection.to
        }
      }
    },

    getNodeDefaults(node) {
      return {
        width: DIAGRAM_CONFIG.NODE.WIDTH,
        height: DIAGRAM_CONFIG.NODE.HEIGHT,
        constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate
      }
    },

    getConnectorDefaults(connector) {
      // Đảm bảo tất cả connector đều là đường thẳng
      connector.type = 'Straight'
      return {
        type: 'Straight',
        style: {
          strokeColor: DIAGRAM_CONFIG.CONNECTION.COLORS.DEFAULT,
          strokeWidth: DIAGRAM_CONFIG.CONNECTION.STROKE_WIDTH
        },
        targetDecorator: {
          shape: 'None'
        },
        constraints: ConnectorConstraints.Default
      }
    },

    onContextMenuOpen(args) {
      if (!this.$refs.diagramObj) return
      const diagram = this.$refs.diagramObj.ej2Instances

      const hasSelectedNode = diagram.selectedItems.nodes && diagram.selectedItems.nodes.length > 0
      const hasSelectedConnector = diagram.selectedItems.connectors && diagram.selectedItems.connectors.length > 0

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

      let hiddenItems = []

      // Nếu click vào connector, chỉ hiển thị "Xóa kết nối"
      if (hasSelectedConnector && !hasSelectedNode) {
        hiddenItems = ['viewDetail', 'connectTo', 'removeDevice']
      }
      // Nếu click vào node, ẩn "Xóa kết nối"
      else if (hasSelectedNode && !hasSelectedConnector) {
        hiddenItems = ['deleteConnection']
      }

      // Tự động ẩn separator dựa trên các items bị ẩn
      const separatorsToHide = this.getSeparatorsToHide(hiddenItems)
      args.hiddenItems = [...hiddenItems, ...separatorsToHide]
    },

    // Hàm helper để tính toán các separator cần ẩn
    // Logic: Ẩn separator nếu một trong 2 items xung quanh nó bị ẩn
    getSeparatorsToHide(hiddenItems) {
      const separatorsToHide = []
      
      // Map các separator với item trước và sau nó
      const separatorMap = {
        'separator_1': { before: 'viewDetail', after: 'connectTo' },
        'separator_2': { before: 'connectTo', after: 'removeDevice' },
        'separator_3': { before: 'removeDevice', after: 'deleteConnection' }
      }

      // Kiểm tra từng separator
      Object.keys(separatorMap).forEach((separatorId) => {
        const { before, after } = separatorMap[separatorId]
        const beforeHidden = hiddenItems.includes(before)
        const afterHidden = hiddenItems.includes(after)

        // Ẩn separator nếu một trong 2 items xung quanh bị ẩn
        if (beforeHidden || afterHidden) {
          separatorsToHide.push(separatorId)
        }
      })

      return separatorsToHide
    },

    onContextMenuClick(args) {
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

    onDiagramClick(args) {
      if (args.element && args.element.addInfo && args.element.addInfo.deviceId) {
        if (this.isConnectMode) {
          if (!this.sourceNode) {
            this.sourceNode = args.element
            this.$toast.info(MESSAGES.INFO.CONNECT_MODE_ON(args.element.addInfo.deviceName))
          } else {
            const targetNode = args.element
            this.createConnection(this.sourceNode, targetNode)
            this.isConnectMode = false
          }
        } else if (args.count === 2) {
          this.showDeviceDetail(args.element.addInfo.deviceId)
        }
      } else {
        if (this.isConnectMode) {
          this.isConnectMode = false
          this.sourceNode = null
          this.$toast.info(MESSAGES.INFO.CONNECT_MODE_CANCELLED)
        }
      }
    },

    onNodePositionChange(args) {
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

    onCollectionChange(args) {
      if (!args.element || args.element.addInfo?.deviceId) {
        return
      }
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
        this.connectionDialog.selectedType = DIAGRAM_CONFIG.CONNECTION.DEFAULT_TYPE
        this.connectionDialog.tempConnectorId = tempConnectorId

        this.$nextTick(() => {
          this.$refs.modalConnectionType.show()
        })
      }
    },

    onConnectionChange(args) {
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
        // Theo dõi khi connector đang được kéo
        this.dragConnectionMode = true
      } else if (args.state === 'Start') {
        // Bắt đầu kéo connector
        this.dragConnectionMode = true
      }
    },

    showConnectionTypeDialogForDrag(sourceNode, targetNode) {
      this.connectionDialog.sourceNode = sourceNode
      this.connectionDialog.targetNode = targetNode
      this.connectionDialog.selectedType = DIAGRAM_CONFIG.CONNECTION.DEFAULT_TYPE
      this.connectionDialog.tempConnectorId = null
      this.$refs.modalConnectionType.show()
    },

    showDeviceDetail(deviceId) {
      this.selectedThietBiId = deviceId
      this.$nextTick(() => {
        this.$refs.modalChiTietThietBi.show(null, 'VIEW')
      })
    },

    async onRefresh() {
      await this.loadData()
      await this.loadStatistics()
    },

    onZoomIn() {
      if (!this.$refs.diagramObj) return
      const diagram = this.$refs.diagramObj.ej2Instances
      if (diagram) {
        diagram.zoom(DIAGRAM_CONFIG.ZOOM.IN_FACTOR)
      }
    },

    onZoomOut() {
      if (!this.$refs.diagramObj) return
      const diagram = this.$refs.diagramObj.ej2Instances
      if (diagram) {
        diagram.zoom(DIAGRAM_CONFIG.ZOOM.OUT_FACTOR)
      }
    },

    resizeDiagram() {
      if (!this.$refs.diagramObj) return
      const diagram = this.$refs.diagramObj.ej2Instances
      if (diagram) {
        // Force resize để đảm bảo diagram chiếm hết không gian
        diagram.updateViewPort()
      }
    },

    onFitToPage() {
      if (!this.$refs.diagramObj) return
      const diagram = this.$refs.diagramObj.ej2Instances
      if (diagram) {
        diagram.fitToPage()
      }
    },

    showExportModal() {
      if (!this.$refs.modalExport) {
        this.$toast.error('Không thể mở modal xuất sơ đồ')
        return
      }
      this.$refs.modalExport.show()
    },

    async onPrint() {
      try {
        // Đóng modal trước
        if (this.$refs.modalExport) {
          this.$refs.modalExport.hide()
        }

        // Đợi modal đóng xong
        await new Promise((resolve) => setTimeout(resolve, DIAGRAM_CONFIG.EXPORT.PRINT.MODAL_CLOSE_DELAY))

        // Kiểm tra diagram element
        const diagramElement = document.getElementById('diagram-mang')
        if (!diagramElement) {
          this.$toast.error('Không tìm thấy element sơ đồ')
          return
        }

        this.$toast.info('Đang tạo ảnh để in...')

        // Sử dụng html2canvas để chụp diagram
        const canvas = await html2canvas(diagramElement, {
          backgroundColor: DIAGRAM_CONFIG.EXPORT.PRINT.BACKGROUND_COLOR,
          scale: DIAGRAM_CONFIG.EXPORT.PRINT.SCALE,
          logging: DIAGRAM_CONFIG.EXPORT.PRINT.LOGGING,
          useCORS: DIAGRAM_CONFIG.EXPORT.PRINT.USE_CORS
        })

        // Tạo cửa sổ in mới
        const printWindow = window.open('', '_blank')
        if (printWindow) {
          printWindow.document.write('<html><head><title>In sơ đồ kết nối mạng</title></head><body>')
          printWindow.document.write('<img src="' + canvas.toDataURL() + '" style="width:100%;" />')
          printWindow.document.write('</body></html>')
          printWindow.document.close()
          printWindow.focus()

          // Đợi ảnh load xong rồi mới in
          setTimeout(() => {
            printWindow.print()
          }, DIAGRAM_CONFIG.EXPORT.PRINT.IMAGE_LOAD_DELAY)

          this.$toast.success('Đã mở cửa sổ in')
        } else {
          this.$toast.error('Không thể mở cửa sổ in. Vui lòng cho phép popup.')
        }
      } catch (error) {
        console.error('Print error:', error)
        this.$toast.error('Có lỗi khi in sơ đồ: ' + error.message)
      }
    },

    async onExportPNG() {
      try {
        // Đóng modal trước
        if (this.$refs.modalExport) {
          this.$refs.modalExport.hide()
        }

        // Đợi modal đóng xong
        await new Promise((resolve) => setTimeout(resolve, DIAGRAM_CONFIG.EXPORT.PRINT.MODAL_CLOSE_DELAY))

        // Kiểm tra diagram element
        const diagramElement = document.getElementById('diagram-mang')
        if (!diagramElement) {
          this.$toast.error('Không tìm thấy element sơ đồ')
          return
        }

        this.$toast.info('Đang xuất hình ảnh PNG...')

        // Sử dụng html2canvas để chụp diagram
        const canvas = await html2canvas(diagramElement, {
          backgroundColor: DIAGRAM_CONFIG.EXPORT.PNG.BACKGROUND_COLOR,
          scale: DIAGRAM_CONFIG.EXPORT.PNG.SCALE,
          logging: DIAGRAM_CONFIG.EXPORT.PNG.LOGGING,
          useCORS: DIAGRAM_CONFIG.EXPORT.PNG.USE_CORS
        })

        // Chuyển canvas thành blob PNG và download
        canvas.toBlob((blob) => {
          const url = URL.createObjectURL(blob)
          const link = document.createElement('a')
          link.href = url
          link.download = `SoDoKetNoiMang_${this.matSanId}_${Date.now()}.png`
          document.body.appendChild(link)
          link.click()
          document.body.removeChild(link)
          URL.revokeObjectURL(url)

          this.$toast.success('Đã xuất sơ đồ ra PNG')
        })
      } catch (error) {
        console.error('Export PNG error:', error)
        this.$toast.error('Có lỗi khi xuất PNG: ' + error.message)
      }
    },

    getDeviceTypeName(type) {
      // Kiểm tra trong deviceTypesMap trước
      if (this.deviceTypesMap[type]) {
        return this.deviceTypesMap[type].name
      }
      
      // Fallback về config cũ
      const names = DIAGRAM_CONFIG.DEVICE.TYPE_NAMES
      return names[type] || type
    },

    createConnection(sourceNode, targetNode) {
      // Kiểm tra không cho kết nối đến container
      if (sourceNode.addInfo?.isContainer || targetNode.addInfo?.isContainer) {
        this.$toast.warning('Không thể kết nối đến vùng container! Vui lòng kết nối đến thiết bị.')
        this.sourceNode = null
        return
      }

      if (sourceNode.id === targetNode.id) {
        this.$toast.warning(MESSAGES.WARNING.SELF_CONNECTION)
        return
      }

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

      this.showConnectionTypeDialog(sourceNode, targetNode)
    },

    showConnectionTypeDialog(sourceNode, targetNode) {
      this.connectionDialog.sourceNode = sourceNode
      this.connectionDialog.targetNode = targetNode
      this.connectionDialog.selectedType = DIAGRAM_CONFIG.CONNECTION.DEFAULT_TYPE
      this.connectionDialog.tempConnectorId = null
      this.$refs.modalConnectionType.show()
    },

    handleCreateConnection(eventData) {
      // Nhận event từ component ModalChonLoaiKetNoi
      const { sourceNode, targetNode, selectedType, tempConnectorId } = eventData

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

      if (sourceNode && targetNode) {
        this.addConnection(sourceNode, targetNode, selectedType)
      }

      this.sourceNode = null
      this.connectionDialog.tempConnectorId = null
    },

    handleCancelConnection(eventData) {
      // Nhận event từ component ModalChonLoaiKetNoi
      const { tempConnectorId } = eventData || this.connectionDialog
      
      // Xóa connector tạm khi cancel
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

    addConnection(sourceNode, targetNode, connectionType) {
      if (!this.$refs.diagramObj) return
      const diagram = this.$refs.diagramObj.ej2Instances
      const connectionId = `conn_${Date.now()}`

      const connector = this.createConnector({
        id: connectionId,
        from: sourceNode.addInfo.deviceId,
        to: targetNode.addInfo.deviceId,
        type: connectionType
      })

      connector.sourceID = sourceNode.id
      connector.targetID = targetNode.id
      // Đảm bảo constraints được set đúng
      connector.constraints = ConnectorConstraints.Default & ~ConnectorConstraints.Drag & ~ConnectorConstraints.DragSourceEnd & ~ConnectorConstraints.DragTargetEnd
      // Đảm bảo type là Orthogonal
      connector.type = 'Orthogonal'

      diagram.add(connector)
      diagram.dataBind()

      this.$toast.success(
        MESSAGES.SUCCESS.CONNECTION_CREATED(
          this.getConnectionTypeName(connectionType),
          sourceNode.addInfo.deviceName,
          targetNode.addInfo.deviceName
        )
      )
    },

    async deleteConnection(connector) {
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

              // Gọi API xóa kết nối sử dụng function từ api.js
              const response = await networkDiagramApi.goKetNoiThietBiMang(this.$root.context, {
                thietBiId: sourceDeviceId,
                thietbiKetnoiId: targetDeviceId
              })

              if (!response?.error_code || response.error_code === 'BSS-00000000') {
                // Xóa connector khỏi diagram
                if (!this.$refs.diagramObj) return
                const diagram = this.$refs.diagramObj.ej2Instances
                diagram.remove(connector)
                diagram.dataBind()

                this.$toast.success(MESSAGES.SUCCESS.CONNECTION_DELETED)
              } else {
                throw new Error(response?.error_msg || 'Không thể xóa kết nối')
              }
            } catch (error) {
              console.error('Error deleting connection:', error)
              const errorMessage = error?.response?.data?.message || error?.message || 'Có lỗi khi xóa kết nối'
              this.$toast.error(errorMessage)
            } finally {
              this.$root.showLoading(false)
            }
          }
        })
        .catch((err) => {})
    },

    async removeDeviceFromDiagram(node) {
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
        .then(async (value) => {
          if (value) {
            try {
              this.$root.showLoading(true)

              if (!this.$refs.diagramObj) return
              const diagram = this.$refs.diagramObj.ej2Instances

              // Tìm tất cả các kết nối liên quan đến thiết bị
              const relatedConnectors = diagram.connectors.filter(
                (conn) => conn.sourceID === node.id || conn.targetID === node.id
              )

              // Gỡ tất cả các kết nối qua API
              const disconnectPromises = relatedConnectors.map(async (connector) => {
                try {
                  // Lấy sourceNode và targetNode để lấy deviceId
                  const sourceNode = diagram.getObject(connector.sourceID)
                  const targetNode = diagram.getObject(connector.targetID)

                  if (!sourceNode || !targetNode) {
                    console.warn('Cannot find source or target node for connector:', connector.id)
                    return
                  }

                  const sourceDeviceId = sourceNode.addInfo?.deviceId
                  const targetDeviceId = targetNode.addInfo?.deviceId

                  if (!sourceDeviceId || !targetDeviceId) {
                    console.warn('Missing deviceId in connector:', connector.id)
                    return
                  }

                  // Gọi API để gỡ kết nối
                  const response = await networkDiagramApi.goKetNoiThietBiMang(this.$root.context, {
                    thietBiId: sourceDeviceId,
                    thietbiKetnoiId: targetDeviceId
                  })

                  if (!response?.error_code || response.error_code === 'BSS-00000000') {
                    // Xóa connector khỏi diagram sau khi API thành công
                    diagram.remove(connector)
                    return { success: true }
                  } else {
                    throw new Error(response?.error_msg || 'Không thể gỡ kết nối')
                  }
                } catch (error) {
                  console.error('Error disconnecting devices:', error)
                  return { success: false, error }
                }
              })

              // Đợi tất cả các kết nối được gỡ
              await Promise.all(disconnectPromises)

              // Xóa thiết bị khỏi diagram
              diagram.remove(node)
              diagram.dataBind()

              // Gọi lại API để refresh sidebar bên trái
              await this.loadUnconnectedDevices()

              const device = {
                id: node.addInfo.deviceId,
                name: node.addInfo.deviceName,
                type: node.addInfo.deviceType,
                trangThai: node.addInfo.trangThai || 'HOATDONG',
                level: node.addInfo.level || 0,
                parentId: node.addInfo.parentId || null,
                containerId: node.addInfo.containerId || null
              }

              this.$toast.success(MESSAGES.SUCCESS.DEVICE_REMOVED_TO_PALETTE(device.name))
            } catch (error) {
              console.error('Error removing device from diagram:', error)
              const errorMessage = error?.response?.data?.message || error?.message || 'Có lỗi khi xóa thiết bị'
              this.$toast.error(errorMessage)
            } finally {
              this.$root.showLoading(false)
            }
          }
        })
        .catch((err) => {
          console.error('Error in removeDeviceFromDiagram confirmation:', err)
        })
    },

    getConnectionTypeName(type) {
      const names = DIAGRAM_CONFIG.CONNECTION.TYPE_NAMES
      return names[type] || type
    },

    onDrop(args) {
      if (!args.element || !args.element.addInfo) return

      const addInfo = args.element.addInfo

      if (addInfo.deviceId) {
        const device = {
          id: addInfo.deviceId,
          name: addInfo.deviceName,
          type: addInfo.deviceType,
          level: addInfo.level || 0,
          parentId: addInfo.parentId || null,
          trangThai: addInfo.trangThai,
          containerId: addInfo.containerId || null
        }

        const color = this.deviceColors[device.type] || this.deviceColors.DEFAULT
        args.element.shape.content = this.createDeviceHtml(device, color)
        args.element.width = DIAGRAM_CONFIG.NODE.WIDTH
        args.element.height = DIAGRAM_CONFIG.NODE.HEIGHT
        args.element.ports = this.createNodePorts()

        this.$toast.success(MESSAGES.SUCCESS.DEVICE_ADDED(device.name))

        const index = this.unconnectedDevices.findIndex((d) => d.id === addInfo.deviceId)
        if (index > -1) {
          this.unconnectedDevices.splice(index, 1)
        }
      } else if (addInfo.deviceType && !addInfo.deviceId) {
        const deviceLabel = addInfo.deviceLabel

        const newDeviceId = `new_device_${Date.now()}`
        const device = {
          id: newDeviceId,
          name: `${deviceLabel} mới`,
          type: addInfo.deviceType,
          level: 0,
          parentId: null,
          trangThai: 'HOATDONG',
          containerId: null
        }

        const node = this.createDeviceNode(device)
        node.offsetX = args.element.offsetX
        node.offsetY = args.element.offsetY
        node.id = `device_${newDeviceId}`

        const diagram = this.$refs.diagramObj.ej2Instances
        diagram.remove(args.element)
        diagram.add(node)
        diagram.dataBind()

        this.$toast.success(`Đã thêm ${deviceLabel} mới vào sơ đồ`)
      }
    },

    // method để tính toán statistics từ data thực tế
    calculateStatistics() {
      // TODO: Tính toán từ nodes và connectors thực tế
      // Ví dụ:
      // const totalDevices = this.nodes.length
      // const activeDevices = this.nodes.filter(n => n.addInfo.trangThai === 'HOATDONG').length
      // ...
    },

    setSymbolTooltips() {
      this.$nextTick(() => {
        // Tìm tất cả các container element có id pattern symbol_*
        const containers = document.querySelectorAll('[id^="symbol_"]')
        
        containers.forEach((container) => {
          // Lấy device ID từ container ID
          // Ví dụ: symbol_uncon_1_container -> uncon_1
          // Hoặc: symbol_uncon_1 -> uncon_1
          const idMatch = container.id.match(/symbol_(.+?)(?:_container)?$/)
          if (idMatch) {
            const deviceId = idMatch[1] // Lấy phần sau "symbol_"
            
            // Tìm device theo ID trong unconnectedDevices
            const device = this.unconnectedDevices.find(d => d.id == deviceId)
            
            if (device) {
              container.setAttribute('title', device.name)
            } else {
              // Nếu không tìm thấy, có thể device đã bị xóa, xóa tooltip
              container.removeAttribute('title')
            }
          }
        })
      })
    },

    applyAccordionContentStyles() {
      this.$nextTick(() => {
        // Tìm tất cả các element .e-acrdn-content trong palette container
        const paletteContainer = this.$el?.querySelector('.palette-container')
        if (!paletteContainer) return

        const accordionContents = paletteContainer.querySelectorAll('.e-acrdn-content')
        
        accordionContents.forEach((content) => {
          // Apply style padding: 10px 0 0 0 !important
          content.style.setProperty('padding', '10px 0 0 0', 'important')
        })
      })
    },

    normalizeDiagramResponse(rawData) {
      if (!rawData) {
        return {
          containers: [],
          devices: [],
          connections: []
        }
      }

      return {
        containers: this.normalizeContainers(rawData?.containers || rawData?.CONTAINERS || []),
        devices: this.normalizeDevices(rawData?.devices || rawData?.DEVICES || []),
        connections: this.normalizeConnections(rawData?.connections || rawData?.CONNECTIONS || [])
      }
    },

    normalizeContainers(rawContainers) {
      if (!Array.isArray(rawContainers)) return []

      return rawContainers.map((container) => {
        const id =
          container?.id ?? container?.ID ?? container?.containerId ?? container?.CONTAINER_ID ?? `container_${Date.now()}`
        return {
          ...container,
          id
        }
      })
    },

    normalizeDevices(rawDevices) {
      if (!Array.isArray(rawDevices)) return []

      return rawDevices
        .map((device) => {
          const id = device?.id ?? device?.ID ?? device?.deviceId ?? device?.DEVICE_ID
          const name = device?.name ?? device?.NAME ?? device?.deviceName ?? device?.DEVICE_NAME
          if (!id || !name) return null

          return {
            id,
            name,
            type: device?.type ?? device?.TYPE ?? 'DEFAULT',
            level: device?.HIERARCHY_LEVEL ?? 0,
            parentId: device?.parentId ?? device?.PARENT_ID ?? null,
            trangThai: device?.trangThai ?? device?.TRANGTHAI ?? 'HOATDONG',
            containerId: device?.containerId ?? device?.CONTAINER_ID ?? null
          }
        })
        .filter(Boolean)
    },

    normalizeConnections(rawConnections) {
      if (!Array.isArray(rawConnections)) return []

      return rawConnections
        .map((connection) => {
          const from = connection?.from ?? connection?.FROM ?? connection?.source ?? connection?.SOURCE
          const to = connection?.to ?? connection?.TO ?? connection?.target ?? connection?.TARGET
          if (!from || !to) return null

          return {
            from,
            to,
            type: connection?.type ?? connection?.TYPE ?? DIAGRAM_CONFIG.CONNECTION.DEFAULT_TYPE
          }
        })
        .filter(Boolean)
    },

    normalizeUnconnectedDevices(rawData) {
      if (!Array.isArray(rawData)) return []

      const devices = []
      const deviceTypesMap = {} // Map tạm để lưu loại thiết bị

      // Duyệt qua từng loại thiết bị
      rawData.forEach((deviceType) => {
        const phanLoaiTbiId = deviceType?.PHANLOAITBI_ID
        const phanLoaiTbiName = deviceType?.PHANLOAI_TBI
        const loaiTbiId = deviceType?.LOAITBI_ID
        const loaiTbiName = deviceType?.LOAI_TBI
        const maLoaiTbi = deviceType?.MA_LOAITBI

        // Lưu thông tin loại thiết bị vào map (sử dụng PHANLOAITBI_ID)
        const typeKey = this.generateTypeKey(phanLoaiTbiId, maLoaiTbi, phanLoaiTbiName)
        
        deviceTypesMap[typeKey] = {
          id: phanLoaiTbiId || null,
          name: phanLoaiTbiName || 'Khác',
          code: maLoaiTbi || null,
          type: typeKey
        }

        // Parse JSON string từ DANH_SACH_THIETBI
        let deviceList = []
        try {
          const danhSachThietBi = deviceType?.DANH_SACH_THIETBI
          if (danhSachThietBi && danhSachThietBi !== '[]' && danhSachThietBi !== '') {
            deviceList = JSON.parse(danhSachThietBi)
          }
        } catch (error) {
          console.error('Error parsing DANH_SACH_THIETBI:', error)
          // Không return, vẫn lưu thông tin loại thiết bị dù parse lỗi
        }

        // Map PHANLOAITBI_ID sang device type
        const deviceTypeCode = this.mapDeviceTypeCode(phanLoaiTbiId, maLoaiTbi, phanLoaiTbiName)

        // Chuyển đổi từng thiết bị (nếu có)
        if (Array.isArray(deviceList) && deviceList.length > 0) {
          deviceList.forEach((device) => {
            const id = device?.ID ?? device?.id
            const name = device?.TEN ?? device?.name
            
            if (!id || !name) return

            // Map HIEU_LUC: 1 = HOATDONG, 0 hoặc null = KHONG_HOATDONG
            const trangThai = device?.HIEU_LUC === 1 ? 'HOATDONG' : 'KHONG_HOATDONG'

            devices.push({
              id,
              name,
              type: deviceTypeCode,
              phanLoaiTbiId: phanLoaiTbiId, // Lưu thêm PHANLOAITBI_ID để reference
              trangThai: trangThai
            })
          })
        }
        // Nếu không có thiết bị, vẫn giữ thông tin loại thiết bị trong deviceTypesMap
      })

      // Cập nhật deviceTypesMap vào component
      this.deviceTypesMap = deviceTypesMap

      return devices
    },

    // Tạo type key từ PHANLOAITBI_ID hoặc MA_LOAITBI
    generateTypeKey(phanLoaiTbiId, maLoaiTbi, phanLoaiTbi) {
      const resolvedType = this.mapDeviceTypeCode(phanLoaiTbiId, maLoaiTbi, phanLoaiTbi)
      if (resolvedType && resolvedType !== 'DEFAULT') {
        return resolvedType
      }

      if (phanLoaiTbiId) {
        return `TYPE_${phanLoaiTbiId}`
      }

      if (maLoaiTbi) {
        return normalizeDeviceTypeKey(maLoaiTbi) || 'DEFAULT'
      }

      if (phanLoaiTbi) {
        return normalizeDeviceTypeKey(phanLoaiTbi) || 'DEFAULT'
      }

      return 'DEFAULT'
    },

    // Map PHANLOAITBI_ID hoặc MA_LOAITBI sang device type trong hệ thống
    mapDeviceTypeCode(phanLoaiTbiId, maLoaiTbi, phanLoaiTbi) {
      // Ưu tiên sử dụng PHANLOAITBI_ID để map với DEVICE_TYPE_BY_PHANLOAITBI_ID
      const typeFromPhanLoaiId = getDeviceTypeFromPhanLoaiId(phanLoaiTbiId)
      if (typeFromPhanLoaiId) {
        return typeFromPhanLoaiId
      }

      const typeFromMa = getDeviceTypeFromValue(maLoaiTbi)
      if (typeFromMa) {
        return typeFromMa
      }

      const typeFromName = getDeviceTypeFromValue(phanLoaiTbi)
      if (typeFromName) {
        return typeFromName
      }

      if (phanLoaiTbiId) {
        return `TYPE_${phanLoaiTbiId}`
      }

      if (maLoaiTbi) {
        return normalizeDeviceTypeKey(maLoaiTbi) || 'DEFAULT'
      }

      if (phanLoaiTbi) {
        return normalizeDeviceTypeKey(phanLoaiTbi) || 'DEFAULT'
      }

      return 'DEFAULT'
    },

    // Tính level tự động dựa trên TYPE_ORDER trong config
    // Thiết bị ở đầu TYPE_ORDER sẽ có level thấp hơn (ở trên), thiết bị ở cuối sẽ có level cao hơn (ở dưới)
    assignLevelsFromTypeOrder(devices) {
      const typeOrder = DIAGRAM_CONFIG.DEVICE.TYPE_ORDER || []
      
      // Tạo map từ type -> level index
      const typeToLevelMap = {}
      typeOrder.forEach((type, index) => {
        typeToLevelMap[type] = index
      })

      // Gán level cho từng device
      devices.forEach((device) => {
        const deviceType = device.type || 'DEFAULT'
        
        // Nếu device type có trong TYPE_ORDER, sử dụng index của nó làm level
        if (typeToLevelMap.hasOwnProperty(deviceType)) {
          device.level = typeToLevelMap[deviceType]
        } else {
          // Nếu không có trong TYPE_ORDER, gán level cao nhất (ở dưới cùng)
          device.level = typeOrder.length
        }
      })
    },

    /**
     * Debounce utility function
     */
    debounce(func, wait) {
      let timeout
      return function executedFunction(...args) {
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
    validateDeviceData(device) {
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
    isContainerNode(node) {
      return node?.addInfo?.isContainer === true
    },

    /**
     * Check if device is inside container bounds
     */
    isDeviceInsideContainer(device, container) {
      const deviceW = device.width || DIAGRAM_CONFIG.NODE.WIDTH
      const deviceH = device.height || DIAGRAM_CONFIG.NODE.HEIGHT

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
    moveDevicesInContainer(containerId, deltaX, deltaY, diagram) {
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
    extractNodeFromArgs(args) {
      return args.source?.addInfo ? args.source : args.element || args.source?.properties?.nodes?.[0]
    },

    /**
     * Handle container movement
     */
    handleContainerMove(node, args, diagram) {
      const containerId = node.addInfo.containerId
      console.log('Handling container move for containerId:', args)
      const deltaX = args.newValue.offsetX - args.oldValue.offsetX
      const deltaY = args.newValue.offsetY - args.oldValue.offsetY
      this.moveDevicesInContainer(containerId, deltaX, deltaY, diagram)
    },

    /**
     * Handle device movement within container
     */
    handleDeviceMove(node, args, diagram) {
      if (!node.addInfo.containerId || node.addInfo.isContainer) return

      const containerId = node.addInfo.containerId
      const containerNode = diagram.getObject(`container_${containerId}`)

      if (!containerNode) return

      const deviceW = node.width || DIAGRAM_CONFIG.NODE.WIDTH
      const deviceH = node.height || DIAGRAM_CONFIG.NODE.HEIGHT

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

    /**
     * Draw containers based on device positions
     */
    drawContainers() {
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
          const w = device.width || DIAGRAM_CONFIG.NODE.WIDTH
          const h = device.height || DIAGRAM_CONFIG.NODE.HEIGHT

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
    },
  }
}
</script>

<style scoped src="./styles.scss"></style>
