// Cấu hình snap
export const SNAP_SETTINGS = {
  constraints: 31, // ShowLines(1) + SnapToLines(2) + ShowHorizontal(4) + ShowVertical(8) + SnapToObject(16) = 31
  snapAngle: 5,
  snapObjectDistance: 10,
  gridInterval: 10,
  gridColor: '#e0e0e0',
  gridDashArray: '2,2'
}

// Cấu hình scroll
export const SCROLL_SETTINGS = {
  scrollLimit: 'Limited',
  minZoom: 0.2,
  maxZoom: 5,
  horizontalOffset: 0,
  verticalOffset: 0,
  canAutoScroll: true,
  autoScrollBorder: { left: 15, right: 15, top: 15, bottom: 15 }
}

// Context menu items
export const CONTEXT_MENU_ITEMS = [
  {
    text: 'Xem chi tiết',
    id: 'viewDetail',
    target: '.e-elementcontent',
    iconCss: 'icon fa fa-eye'
  },
  {
    text: 'Sửa polygon',
    id: 'editPolygon',
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
]

// ✅ THÊM: Drawing constants để tránh magic numbers
export const DRAWING_CONSTANTS = {
  POINT_CLICK_THRESHOLD: 20, // cm - khoảng cách để chọn điểm
  POINT_RADIUS_NORMAL: 5, // Bán kính điểm bình thường
  POINT_RADIUS_EDIT: 8, // Bán kính điểm khi edit
  CANVAS_GRID_SIZE: 50, // Kích thước grid trên canvas
  SNAP_GRID_SIZE: 10, // cm - snap to grid
  MIN_ROOM_AREA: 1, // m² - diện tích tối thiểu
  MIN_POLYGON_POINTS: 3, // Số điểm tối thiểu để tạo polygon
  CANVAS_REDRAW_THROTTLE: 16 // ms - ~60fps
}
