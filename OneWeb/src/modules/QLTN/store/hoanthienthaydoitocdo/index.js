import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  initParams: {    
    dichVu_Selected: null,    
    quyTrinh_Selected: null,
    kieuYCId: null,
    kieuLapDat: null,
    TTHD_Id: null,
    loaiTB_Id: null
  }
}

const namespaced = true

export const hoanthienthaydoitocdo = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
