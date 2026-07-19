import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  formResult: [],
  formData: {
  }
}

const namespaced = true

export const storeTraCuuPhieuTraTheoUser = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
