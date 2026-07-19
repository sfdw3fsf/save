import {
  SnapConstraints,
  SelectorConstraints
} from '@syncfusion/ej2-vue-diagrams'

export const DIAGRAM_CONFIG = {
  LAYOUT: {
    NODE_WIDTH: 150,
    NODE_HEIGHT: 70,
    HORIZONTAL_GAP: 180,
    VERTICAL_GAP: 150,
    START_X: 600,
    START_Y: 50
  },
  CONTAINER: {
    PADDING: 20,
    SYSTEM_Z_INDEX: -2,
    ZONE_Z_INDEX: -2
  },
  ANIMATION: {
    FIT_DELAY: 300,
    CONTAINER_DRAW_DELAY: 300,
    POSITION_CHANGE_THRESHOLD: 0.1
  },
  NODE: {
    WIDTH: 150,
    HEIGHT: 70,
    PALETTE_WIDTH: 100,
    PALETTE_HEIGHT: 65,
    PALETTE_DEFAULTS_WIDTH: 110,
    PALETTE_DEFAULTS_HEIGHT: 80
  },
  DEVICE: {
    COLORS: {
      SANDIRECTOR: '#6A1B9A',
      SANSWITCH: '#00897B',
      ROUTER: '#1E88E5',
      SWITCH: '#43A047',
      FIREWALL: '#E53935',
      LOADBALACING: '#FB8C00',
      WAF: '#8E24AA',
      CONTROLLER: '#5E35B1',
      ACCESSPOINT: '#00ACC1',
      DBFW: '#F4511E',
      PATCH_PANNEL: '#546E7A',
      DEFAULT: '#9E9E9E'
    },
    ICONS: {
      SANDIRECTOR: '🧠',
      SANSWITCH: '🔁',
      ROUTER: '📡',
      SWITCH: '🔀',
      FIREWALL: '🛡️',
      LOADBALACING: '⚖️',
      WAF: '🧱',
      CONTROLLER: '🎛️',
      ACCESSPOINT: '📶',
      DBFW: '🖧',
      PATCH_PANNEL: '🔌',
      DEFAULT: '📦'
    },
    TYPE_NAMES: {
      SANDIRECTOR: 'San Director',
      SANSWITCH: 'San Switch',
      ROUTER: 'Router',
      SWITCH: 'Switch',
      FIREWALL: 'Firewall',
      LOADBALACING: 'Load Balancing',
      WAF: 'WAF',
      CONTROLLER: 'Controller',
      ACCESSPOINT: 'Access Point',
      DBFW: 'DBFW',
      PATCH_PANNEL: 'Patch Pannel',
      DEFAULT: 'Khác'
    },
    TYPES: [
      { type: 'SANDIRECTOR', label: 'San Director' },
      { type: 'SANSWITCH', label: 'San Switch' },
      { type: 'ROUTER', label: 'Router' },
      { type: 'SWITCH', label: 'Switch' },
      { type: 'FIREWALL', label: 'Firewall' },
      { type: 'LOADBALACING', label: 'Load Balancing' },
      { type: 'WAF', label: 'WAF' },
      { type: 'CONTROLLER', label: 'Controller' },
      { type: 'ACCESSPOINT', label: 'Access Point' },
      { type: 'DBFW', label: 'DBFW' },
      { type: 'PATCH_PANNEL', label: 'Patch Pannel' }
    ],
    TYPE_ORDER: [
      'SANDIRECTOR',
      'SANSWITCH',
      'ROUTER',
      'SWITCH',
      'FIREWALL',
      'LOADBALACING',
      'WAF',
      'CONTROLLER',
      'ACCESSPOINT',
      'DBFW',
      'PATCH_PANNEL'
    ]
  },
  CONNECTION: {
    COLORS: {
      DUONG_QUANG: '#FF0000', // Đỏ - Đường quang
      DUONG_DONG: '#0066FF', // Xanh dương - Đường đồng
      DUONG_DU_PHONG: '#FFA500', // Cam - Đường kết nối dự phòng
      SINGLE_MODE: '#9933FF', // Tím - Single mode
      MULTI_MODE: '#CC66FF', // Tím đậm - Multi mode
      DEFAULT: '#2196F3'
    },
    STROKE_WIDTH: 3,
    DEFAULT_TYPE: 'DUONG_QUANG',
    TYPE_NAMES: {
      DUONG_QUANG: 'Đường quang',
      DUONG_DONG: 'Đường đồng',
      DUONG_DU_PHONG: 'Đường kết nối dự phòng',
      SINGLE_MODE: 'Single mode',
      MULTI_MODE: 'Multi mode'
    }
  },
  STATUS: {
    ACTIVE_COLOR: '#28a745',
    INACTIVE_COLOR: '#dc3545'
  },
  SNAP: {
    CONSTRAINTS: SnapConstraints.None,
    SNAP_ANGLE: 5,
    SNAP_OBJECT_DISTANCE: 5
  },
  SCROLL: {
    SCROLL_LIMIT: 'Diagram',
    MIN_ZOOM: 0.2,
    MAX_ZOOM: 3,
    CAN_AUTO_SCROLL: false
  },
  SELECTED_ITEMS: {
    CONSTRAINTS: SelectorConstraints.None
  },
  CONTEXT_MENU: {
    SHOW: true,
    SHOW_CUSTOM_MENU_ONLY: true,
    ITEMS: [
      {
        text: 'Xem chi tiết',
        id: 'viewDetail',
        target: '.e-elementcontent',
        iconCss: 'fa fa-eye'
      },
      { 
        id: 'separator_1',
        separator: true 
      },
      {
        text: 'Kết nối đến thiết bị khác',
        id: 'connectTo',
        target: '.e-elementcontent',
        iconCss: 'fa fa-link'
      },
      { 
        id: 'separator_2',
        separator: true 
      },
      {
        text: 'Xóa thiết bị khỏi sơ đồ',
        id: 'removeDevice',
        target: '.e-elementcontent',
        iconCss: 'fa fa-times-circle'
      },
      { 
        id: 'separator_3',
        separator: true 
      },
      {
        text: 'Xóa kết nối',
        id: 'deleteConnection',
        target: '.e-diagramcontent',
        iconCss: 'fa fa-trash'
      }
    ]
  },
  ZOOM: {
    IN_FACTOR: 1.2,
    OUT_FACTOR: 1 / 1.2
  },
  EXPORT: {
    PNG: {
      SCALE: 2,
      BACKGROUND_COLOR: '#ffffff',
      LOGGING: false,
      USE_CORS: true
    },
    PRINT: {
      MODAL_CLOSE_DELAY: 300,
      IMAGE_LOAD_DELAY: 500,
      SCALE: 2,
      BACKGROUND_COLOR: '#ffffff',
      LOGGING: false,
      USE_CORS: true
    }
  },
  PALETTE: {
    EXPANDED_DEFAULT: true
  },
  STYLES: {
    DEVICE_NODE: {
      BORDER_RADIUS: 5,
      BORDER_WIDTH: 3,
      PADDING: 3,
      BOX_SHADOW: '0 2px 6px rgba(0,0,0,0.15)',
      ICON_FONT_SIZE: '20px',
      NAME_FONT_SIZE: '10px',
      NAME_FONT_WEIGHT: 'bold',
      NAME_COLOR: '#333',
      TYPE_FONT_SIZE: '11px',
      TYPE_COLOR: '#666',
      TYPE_FONT_WEIGHT: 500,
      STATUS_DOT_SIZE: 12,
      STATUS_DOT_OFFSET: -7
    },
    DEVICE_PALETTE: {
      BORDER_RADIUS: 6,
      BORDER_WIDTH: 2,
      PADDING: 8,
      GAP: 4,
      ICON_FONT_SIZE: '24px',
      NAME_FONT_SIZE: '11px',
      NAME_FONT_WEIGHT: 600,
      NAME_COLOR: '#333'
    }
  }
}

export const MESSAGES = {
  SUCCESS: {
    DEVICE_ADDED: (name) => `Đã thêm ${name} vào sơ đồ`,
    DEVICE_REMOVED: (name) => `Đã xóa thiết bị "${name}" khỏi sơ đồ`,
    DEVICE_REMOVED_TO_PALETTE: (name) => `Đã xóa thiết bị "${name}" khỏi sơ đồ và đưa về palette`,
    CONNECTION_CREATED: (type, from, to) => `Đã tạo kết nối ${type} từ ${from} đến ${to}`,
    CONNECTION_DELETED: 'Đã xóa kết nối'
  },
  ERROR: {
    LOAD_FAILED: 'Không thể tải dữ liệu sơ đồ kết nối mạng',
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
