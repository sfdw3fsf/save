import {searchArray} from '../../../../utils/util'
export const getters = {
  dsThanhToanGetter: (state, getters, rootState) => {
    return state.dsThanhToan
  },
  dsMaTTTheoMaInGhepGetter: (state, getters, rootState) => {
    return state.dsMaTTTheoMaInGhep
  },
  dsDonViQuanLyGetter: (state, getters, rootState) => {
    return state.dsDonViQuanLy
  },
  dsMIGTheoKhachHangGetter: (state, getters, rootState) => {
    return state.dsMIGTheoKhachHang
  },
  dsMaThanhToanChuaGanGetter: (state, getters, rootState) => {
    return state.dsMaThanhToanChuaGan
  },
  dsMaThanhToanDaGanGetter: (state, getters, rootState) => {
    return state.dsMaThanhToanDaGan
  },
  dsLienHeGetter: (state, getters, rootState) => {
    return state.dsLienHe
  }
}
