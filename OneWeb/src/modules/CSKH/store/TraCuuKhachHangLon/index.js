import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  dsThanhToan: [],
  dsMaTTTheoMaInGhep: [],
  dsDonViQuanLy: [],
  dsMIGTheoKhachHang: [],
  dsMaThanhToanChuaGan: [],
  dsMaThanhToanDaGan: [],
  dsLienHe: [],
}

const namespaced = true

export const tracuukhachhanglon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
