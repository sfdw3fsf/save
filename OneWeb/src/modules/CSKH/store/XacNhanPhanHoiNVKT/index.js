import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  DsDonViLoiXacNhan: [],
  DsNhomNguyenNhan: [],
  DsNguyenNhan: [],
  DsPhieuLH: [],
  dsPAMoiTiepNhanTheoNV: [],
  DsNhanVienLoi: [],
}

const namespaced = true

export const xacnhanphanhoinvkt = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
