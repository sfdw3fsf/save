// khai báo 1 store
import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  stores: {
    dsCayDonvi: [],
    selectedDonVi: {}
  }
}

const namespaced = true

export const SearchMaintenanceSlipNotDone = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
