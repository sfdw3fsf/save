import { DiagramConstraints, SnapConstraints, SelectorConstraints } from '@syncfusion/ej2-vue-diagrams';

// Màu sắc cho container
const CONTAINER_COLORS = {
  fill: [
    'rgba(255, 87, 51, 0.5)',  // Đỏ cam
    'rgba(51, 255, 87, 0.5)',  // Xanh lá
    'rgba(51, 87, 255, 0.5)',  // Xanh dương
    'rgba(241, 196, 15, 0.5)', // Vàng
    'rgba(142, 68, 173, 0.5)', // Tím
    'rgba(231, 76, 60, 0.5)',  // Đỏ
    'rgba(52, 152, 219, 0.5)'  // Xanh biển
  ],
  stroke: [
    '#FF5733', // Đỏ cam đậm
    '#33FF57', // Xanh lá đậm
    '#3357FF', // Xanh dương đậm
    '#F1C40F', // Vàng đậm
    '#8E44AD', // Tím đậm
    '#E74C3C', // Đỏ đậm
    '#3498DB'  // Xanh biển đậm
  ]
};

// Màu sắc cho thiết bị
const DEVICE_COLORS = {
  fill: [
    'rgba(41, 128, 185, 0.5)',  // Xanh dương nhạt
    'rgba(46, 204, 113, 0.5)',  // Xanh lá cây
    'rgba(155, 89, 182, 0.5)',  // Tím nhạt
    'rgba(243, 156, 18, 0.5)',  // Cam vàng
    'rgba(231, 76, 60, 0.5)',   // Đỏ nhạt
    'rgba(52, 73, 94, 0.5)',    // Xám đen
    'rgba(26, 188, 156, 0.5)'   // Ngọc lam
  ],
  stroke: [
    '#2980b9', // Xanh dương đậm
    '#27ae60', // Xanh lá cây đậm
    '#8e44ad', // Tím đậm
    '#d35400', // Cam đậm
    '#c0392b', // Đỏ đậm
    '#2c3e50', // Xám đen đậm
    '#16a085'  // Ngọc lam đậm
  ]
};

// Màu sắc cho connector
const CONNECTOR_COLORS = {
  default: '#000000',
  selected: '#000000',
  hover: '#000000',
  inter: '#000000'
};

export const COLORS = {
  container: CONTAINER_COLORS,
  thietBi: DEVICE_COLORS,
  connector: CONNECTOR_COLORS
};

// Cấu hình mặc định cho connector
export const CONNECTOR_DEFAULTS = {
  type: 'Orthogonal',
  style: {
    strokeColor: COLORS.connector.default,
    strokeWidth: 1
  },
  targetDecorator: {
    shape: 'None'
  },
  constraints: DiagramConstraints.Default & ~DiagramConstraints.Select
};

// Cấu hình layout cho diagram
const LAYOUT_CONFIG = {
  type: 'OrganizationalChart',
  orientation: 'TopToBottom',
  horizontalSpacing: 80, // Khoảng cách ngang giữa các node
  verticalSpacing: 100,  // Khoảng cách dọc giữa các node
  margin: {
    left: 100,
    right: 100,
    top: 50,
    bottom: 50
  },
  // Cấu hình để tạo dạng kim tự tháp
  getLayoutInfo: (node, options) => {
    if (!node.data) {
      return {
        assistantCount: 0,
        childCount: options.childNodes.length,
        level: options.level,
        // Tăng offset để các node con được sắp xếp rộng dần theo level
        offset: (options.level * 2) + 1,
        orientation: 'Horizontal',
        rows: options.rows,
        columns: options.columns
      };
    }
  }
};

// Cấu hình chung cho diagram
export const DIAGRAM_CONFIG = {
  tool: 'ZoomPan',
  snapSettings: {
    constraints: SnapConstraints.None
  },
  height: '800px',
  width: '100%',
  diagramSettings: {
    constraints: DiagramConstraints.Default & ~DiagramConstraints.Select,
    dragLimit: {
      right: 1500, // Tăng giới hạn kéo để hiển thị đầy đủ kim tự tháp
      bottom: 1500
    }
  },
  layout: LAYOUT_CONFIG,
  selectedItems: {
    constraints: SelectorConstraints.All
      & ~SelectorConstraints.ResizeAll
      & ~SelectorConstraints.Rotate
      & ~SelectorConstraints.Drag
  }
};
