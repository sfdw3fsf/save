import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    // DSMucDichDK: [],
    // DSMucDichMuaSam: [],
    // DSTrangThaiHD: [],
    // DSKhoNhaThau: [],
    // DSDonVi: [],
    // DSTrangThaiDonHang: [],
    // DSHopDongDK: [],
    // DSDonHangCha: [],
    // DSDonHangTheoHD: [],
    // GridDanhSachHD: [],
    // GridDanhSachDonHang: [],
    // GridDSVatTu_CTHD : [],
    // GridDS_VT_DonHang: []
    
}

const namespaced = true

export const tracuuhopdongvadonhang = {
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