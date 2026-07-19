import thietBiApi from '../api/ThietBi.api'
import { TAB_INDICES } from './tabVisibility.js'

/**
 * Tạo danh sách các tab actions (validate và save) cho component
 * @param {Object} context - Context của component chứa $refs, currentItem, $root
 * @returns {Array} Danh sách các tab với validateFn và saveFn
 */
export function createTabActions(context) {
  const { $refs, currentItem, $root } = context

  return [
    {
      id: TAB_INDICES.THONG_TIN_CHUNG,
      label: 'Thông tin chung',
      ref: 'thongTinChung',
      type: 'object',
      validateFn: () => $refs.thongTinChung?.validateControls(),
      saveFn: () => {
        const payload = $refs.thongTinChung.buildUpsertThongTinChungPayload(currentItem)
        return thietBiApi.upsertThongTinChung($root.context, payload)
      },
      fetchFn: (deviceId) =>
        thietBiApi.getThongTinChungById($root.context, deviceId).catch(() => {
          $root.toastError('Không tìm thấy thông tin thiết bị')
        })
    },
    {
      id: TAB_INDICES.THONG_TIN_LAP_DAT,
      label: 'Thông tin lắp đặt',
      ref: 'thongTinLapDat',
      type: 'object',
      validateFn: () => $refs.thongTinLapDat?.validateControls(),
      saveFn: () => $refs.thongTinLapDat.updateViTriLapDat()
    },
    {
      id: TAB_INDICES.TAI_SAN,
      label: 'Tài sản',
      ref: 'thongTinKiemKe',
      type: 'array',
      validateFn: () => $refs.thongTinKiemKe?.validateControls(),
      saveFn: () => {
        const promises = []
        const thongTinTaiSanPayLoad = $refs.thongTinKiemKe.preparePayloadForTaiSan(currentItem)
        promises.push(thietBiApi.upsertThongTinTaiSan($root.context, thongTinTaiSanPayLoad))
        const thongTinCaNhanQuanLyPayload = $refs.thongTinKiemKe.preparePayloadForCaNhanQuanLy(currentItem)
        promises.push(thietBiApi.upsertCaNhanQuanLy($root.context, thongTinCaNhanQuanLyPayload || {}))
        return Promise.all(promises)
      },
      fetchFn: (deviceId) => {
        const promises = []
        promises.push(
          thietBiApi.getCaNhanQuanLy($root.context, deviceId).catch(() => {
            $root.toastError('Không tìm thấy thông tin cá nhân quản lý thiết bị')
          })
        )
        promises.push(
          thietBiApi.getThongTinTaiSan($root.context, deviceId).catch(() => {
            $root.toastError('Không tìm thấy thông tin tài sản thiết bị')
          })
        )
        return Promise.all(promises)
      }
    },
    {
      id: TAB_INDICES.NANG_LUC_LUU_TRU,
      label: 'Năng Lực Lưu Trữ',
      ref: 'nangLucLuuTru',
      type: 'object',
      validateFn: () => $refs.nangLucLuuTru?.validateControls(),
      saveFn: () => {
        const payload = $refs.nangLucLuuTru.preparePayloadForNangLucLuuTru(currentItem)
        return thietBiApi.upsertNangLucLuuTru($root.context, payload)
      },
      fetchFn: (deviceId) =>
        thietBiApi.getNangLucLuuTru($root.context, deviceId).catch(() => {
          $root.toastError('Không tìm thấy năng lực lưu trữ')
        })
    },
    {
      id: TAB_INDICES.CONG_KET_NOI,
      label: 'Cổng kết nối',
      ref: 'congKetNoi',
      type: 'object',
      validateFn: () => $refs.congKetNoi?.validateControlsCustom(),
      saveFn: () => $refs.congKetNoi.onSavePort(),
      fetchFn: (deviceId) => $refs.congKetNoi.loadData(deviceId)
    }
  ]
}
