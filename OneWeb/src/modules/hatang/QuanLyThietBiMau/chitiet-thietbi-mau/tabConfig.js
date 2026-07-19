export const TAB_IDS = {
  THONG_TIN_CHUNG: 'thongTinChung',
  LICH_SU_THAY_DOI: 'lichSuThayDoi'
}

/*
 * Danh sách cấu hình tab cho trang chi tiết thiết bị mẫu.
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
    equipmentTypes: null,
    isVisible: () => true,
    getProps: (vm) => ({
      currentItem: vm.currentItem,
      currentMode: vm.currentMode,
      controls: vm.controls,
      equipmentType: vm.equipmentType
    })
  },
  {
    id: TAB_IDS.LICH_SU_THAY_DOI,
    index: 1,
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
    equipmentTypes: null,
    isVisible: ({ currentMode }) => currentMode !== 'ADD',
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
 * @param {string|null} context.equipmentType
 * @param {string} context.currentMode
 * @param {Object} context.currentItem
 * @returns {Array} danh sách tab hợp lệ
 */
export const filterTabsByContext = (context = {}) => {
  const { equipmentType } = context
  return TAB_CONFIG.sort((a, b) => a.index - b.index).filter((tab) => {
    const allowedEquipmentTypes = !Array.isArray(tab.equipmentTypes) || tab.equipmentTypes.length === 0
    const matchEquipmentType =
      allowedEquipmentTypes || (equipmentType && tab.equipmentTypes.includes(equipmentType))
    if (!matchEquipmentType) {
      return false
    }
    if (typeof tab.isVisible === 'function') {
      return tab.isVisible(context)
    }
    return true
  })
}

export const getTabById = (tabId) => TAB_CONFIG.find((tab) => tab.id === tabId)
