import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhsachTuyenThu: [],
  danhsachDotGiao: [],
  danhsachQuan: [],
  danhsachPhuong: [],
  danhsachPho: [],
  danhsachAp: [],
  danhsachPhanChiaTuyenThu: [],
  danhsachChuaPhanTuyen: [],
}

const namespaced = true

export const phanchiatuyenthu = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
