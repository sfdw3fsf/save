import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhsachNhanVien: [],
  danhsachNhanVienDungHuong: [],
  danhsachHoaDon: [],
}

const namespaced = true

export const chuyenphieulachuong = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
