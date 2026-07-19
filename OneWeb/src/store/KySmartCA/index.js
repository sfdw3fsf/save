// khai báo 1 store
import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  user_ca_infor: {},
  matkhau_smca: "",
  dulieu_smca: {},
  duyet_hang_loat_user_info: {
    vuid: "",
    vmatkhau: "",
    votp: ""
  }
}

export const KySmartCA = {
  state,
  actions,
  mutations
}
