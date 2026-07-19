import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    // GridDS_ThanhToan: [],
    // GridDS_VT_TT: [],
    // GridDS_VT_DonHang: [],
    // dvi_id: "", //frmTraCuuHopDong
    // cboDonHang: [],
    // cboHinhThucTra: [],
    // cboNVThuNgan: [],
    // cboDonHang_Filter: [],
    // cboHinhThucTra_Filter: [],
    // cboNVThuNgan_Filter: [],
    // cboVatTu_Filter: [],
    // DSDonViTinh_Filter: [],

}

const namespaced = true

export const thanhtoandonhang = {
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