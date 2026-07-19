import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    // DSTongHopCCDC: [], // gridTongHop
    // DSChiTietCCDC: [], // gridVatTu
    // DSChungTu: [],
    DSTinhTrangTBi: [],
    // DSDonVi: [],
    // DSNhanVien: [],
    DSMucDichPhanBo: [],
}

const namespaced = true

export const capphatccdc = {
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