import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  dsNganhNghe: [],
  dsLoaiKH: [],
  dsLoaiKHLon: [],
  dsHinhThucKN: [],
  dsNoiDungPA: [],
  dsPAMoiTiepNhanTheoNV: [],
}

const namespaced = true

export const tiepnhanphananhkhachhang = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
