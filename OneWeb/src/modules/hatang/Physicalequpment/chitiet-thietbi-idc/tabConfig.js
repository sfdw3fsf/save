import { DEVICE_TYPE_ID } from '../const.js'

export const TAB_IDS = {
  THONG_TIN_CHUNG: 'thongTinChung',
  LICH_SU_THAY_DOI: 'lichSuThayDoi',
  NGHIEP_VU_IDC: 'nghiepVuIDC',
  KHACH_HANG_IDC: 'KhachHangIDC',
  TAI_SAN: 'TaiSan'
}

/*
 * Danh sách cấu hình tab cho trang chi tiết thiết bị.
 * - id: định danh duy nhất cho tab, dùng để mapping logic khác
 * - ref: tên ref để truy cập component con
 * - label: text hiển thị trên navigation
 * - component: tên component đã được register trong parent
 * - fetchBeforeFn: tên hàm/function chạy trước khi render tab, ví dụ: ['loadInitialData', 'hello']
 * - fetchAfterFn: tên hàm/function chạy sau bước trước
 * - saveFn: tên hàm/function cần gọi khi lưu
 * - typeSave: định nghĩa kiểu dữ liệu mà saveFn trả về:
 *      + 'object' : saveFn trả về object dùng để merge payload gửi API
 *      + 'method' : saveFn là string hoặc string[] tên method cần gọi (không trả payload)
 * - validateFn: tên hàm hoặc function dùng để validate tab
 * - deviceTypeIds: danh sách device type hỗ trợ tab (null = tất cả), 
 *      ví dụ: [DEVICE_TYPE_ID.MAY_PHAT_DIEN, DEVICE_TYPE_ID.UPS]
 * - isVisible: function nhận context { currentMode, deviceTypeId, currentItem }
 *              để quyết định có hiển thị tab hay không
 * - getProps: function nhận instance parent (vm) và trả props cho component con
 */
export const TAB_CONFIG = [
  {
    id: TAB_IDS.THONG_TIN_CHUNG,
    index: 0,
    ref: 'ThongTinChung',
    label: 'Thông tin chung',
    component: 'ThongTinChung',
    wrapperClass: null,
    legend: null,
    fetchBeforeFn: ['loadInitialData'],
    fetchAfterFn: null,
    saveFn: 'buildThongTinChungPayload',
    typeSave: 'object',
    validateFn: 'validateForm',
    deviceTypeIds: null,
    isVisible: () => true,
    getProps: (vm) => ({
      currentItem: vm.currentItem,
      currentMode: vm.currentMode,
      controls: vm.controls
    })
  }, 
  {
    id: TAB_IDS.CONG_KET_NOI,
    index: 1,
    ref: 'CongKetNoi',
    label: 'Cổng kết nối',
    component: 'CongKetNoi',
    wrapperClass: null,
    legend: null,
    fetchBeforeFn: ['loadInitialData'],
    fetchAfterFn: null,
    saveFn: 'onSaveCongKetNoi',
    typeSave: 'method',
    validateFn: 'validateCongKetNoi',
    deviceTypeIds: Object.values(DEVICE_TYPE_ID),
    getProps: (vm) => ({
      currentItem: vm.currentItem,
      currentMode: vm.currentMode,
      controls: vm.controls
    })
  },
  {
    id: TAB_IDS.VI_TRI_LAP_DAT,
    index: 2,
    ref: 'ViTriLapDat',
    label: 'Vị trí lắp đặt',
    component: 'ViTriLapDat',
    wrapperClass: null,
    legend: null,
    fetchBeforeFn: ['loadInitialData'],
    fetchAfterFn: null,
    saveFn: 'onSaveViTriLapDat',
    typeSave: 'method',
    validateFn: 'validateViTriLapDat',
    deviceTypeIds: Object.values(DEVICE_TYPE_ID),
    getProps: (vm) => ({
      currentItem: vm.currentItem,
      currentMode: vm.currentMode,
      controls: vm.controls
    })
  },
  {
    id: TAB_IDS.LICH_SU_THAY_DOI,
    index: 5,
    ref: 'LichSuThayDoi',
    label: 'Lịch sử thay đổi',
    component: 'LichSuThayDoi',
    wrapperClass: null,
    legend: null,
    fetchBeforeFn: ['loadInitialData'],
    fetchAfterFn: null,
    saveFn: null,
    typeSave: null,
    validateFn: null,
    deviceTypeIds: Object.values(DEVICE_TYPE_ID),
    isVisible: ({ currentMode }) => currentMode !== 'ADD',
    getProps: (vm) => ({
      currentItem: vm.currentItem,
      currentMode: vm.currentMode,
      controls: vm.controls
    })
  },
  {
    id: TAB_IDS.NGHIEP_VU_IDC,
    index: 6,
    ref: 'NghiepVuIDC',
    label: 'Nghiệp vụ IDC',
    component: 'NghiepvuIDC',
    wrapperClass: null,
    legend: null,
    fetchBeforeFn: null,
    fetchAfterFn: null,
    saveFn: null,
    typeSave: null,
    validateFn: null,
    deviceTypeIds: null,
    isVisible: () => true,
    getProps: (vm) => ({
      currentItem: vm.currentItem,
      currentMode: vm.currentMode,
      controls: vm.controls
    })
  },
  {
    id: TAB_IDS.KHACH_HANG_IDC,
    index: 4,
    ref: 'KhachHangIDC',
    label: 'Khách hàng',
    component: 'KhachHangIDC',
    wrapperClass: null,
    legend: null,
    fetchBeforeFn: null,
    fetchAfterFn: null,
    saveFn: null,
    typeSave: null,
    validateFn: null,
    deviceTypeIds: null,
    isVisible: () => true,
    getProps: (vm) => ({
      currentItem: vm.currentItem,
      currentMode: vm.currentMode,
      controls: vm.controls
    })
  },
  {
    id: TAB_IDS.TAI_SAN,
    index: 3,
    ref: 'TaiSan',
    label: 'Tài sản',
    component: 'TaiSan',
    wrapperClass: null,
    legend: null,
    fetchBeforeFn: ['loadInitialData'],
    fetchAfterFn: null,
    saveFn: 'onSave',
    typeSave: 'method',
    validateFn: 'validateForm',
    deviceTypeIds: null,
    isVisible: () => true,
    getProps: (vm) => ({
      currentItem: vm.currentItem,
      currentMode: vm.currentMode,
      controls: vm.controls
    })
  }
]

/**
 * Lọc danh sách tab dựa theo context hiện tại
 * @param {Object} context
 * @param {number|null} context.deviceTypeId
 * @param {string} context.currentMode
 * @param {Object} context.currentItem
 * @returns {Array} danh sách tab hợp lệ
 */
export const filterTabsByContext = (context = {}) => {
  const { deviceTypeId } = context
  return TAB_CONFIG.sort((a, b) => a.index - b.index).filter((tab) => {
    const allowedDeviceTypes = !Array.isArray(tab.deviceTypeIds) || tab.deviceTypeIds.length === 0
    const matchDeviceType =
      allowedDeviceTypes || (deviceTypeId && tab.deviceTypeIds.includes(deviceTypeId))
    if (!matchDeviceType) {
      return false
    }
    if (typeof tab.isVisible === 'function') {
      return tab.isVisible(context)
    }
    return true
  })
}

export const getTabById = (tabId) => TAB_CONFIG.find((tab) => tab.id === tabId)

