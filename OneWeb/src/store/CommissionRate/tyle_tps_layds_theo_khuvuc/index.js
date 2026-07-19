import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  DanhSachTyleTPSTheoKhuVucDaGan: [],
  DanhSachTyleTPSTheoKhuVucChuaGan: [],
}

const namespaced = true

export const TyleTPSLayDSTheoKhuVucCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
