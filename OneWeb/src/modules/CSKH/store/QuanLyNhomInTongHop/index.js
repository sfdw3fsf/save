import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  dsThanhToanThuocNhom: [],
  dsThanhToanKoThuocNhom: [],
  dsNhomKHLon: [],
}

const namespaced = true

export const quanlynhomintonghop = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
