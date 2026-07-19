import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    DSTrangThaiBHDich: [],
    DSBaoHanhTBHienTai: [],
    ThongTinTrangThaiBH: [],
    DSKhoNhaCungCap: [],
    luong_id: '',
    quytrinh_id: '',
    trangthaibh_id_n: '',
    trangthai_bh_n: '',
}

const namespaced = true

export const xulythietbibaohanh = {
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