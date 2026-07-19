import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  DsNhanVienGiao: [],
  DsNhanVienNhan: [],
  DsLuotGiaoKhaoSat: [],
  DsPhieuDaGiaoKhaoSat: [],
  DsDichVu: [],
  DsThongTinGiaoKhaoSat: [],
  DsLoaiHopDong: [],
}

const namespaced = true

export const giaophieukhaosatkhachhang = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
