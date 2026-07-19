import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  DanhSachTyleTPSTheoLoaiNVChuaGan: [],
  DanhSachTyleTPSTheoLoaiNVDaGan: []

}

const namespaced = true

export const TyleTPSLayDSTheoLoaiNVCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
