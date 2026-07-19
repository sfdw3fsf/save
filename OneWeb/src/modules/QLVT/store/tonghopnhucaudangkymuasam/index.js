import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  DSDotDangKy: [],
  DSDonVi: [],
  DSMucDich: [],
  DSDotTongHop: [],
  // DSDangKy: [],
  // DSTongHop: [],
  kTTongHop: 0,
  kTNgayDuyet: -1,
  kqDuyetHuyDuyet: -1,
  kiemTraLapHopDong: [],
  kiemTraLapChungTu: [],
  ktCungChuKy: [],
  ktKetQuaTongHop: 0,
  ktXoaTongHop: 0,
  ktCapNhatGhiChu: 0,
}

const namespaced = true

export const tonghopnhucaudangkymuasam = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}

export const getterName = Object.getOwnPropertyNames(getters);
export const actionName = Object.getOwnPropertyNames(actions);
export const statePropertyName = Object.getOwnPropertyNames(state);
export const mutationName = Object.getOwnPropertyNames(mutations);