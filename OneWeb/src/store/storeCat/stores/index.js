import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  stores: {
    selectedStoreTypeId: null,
    storeTypes: [],
    selectedStore: {},
    stores: [],
    storesBelongAccount: [],
    selectedDepartment: {},
    departments: [],
    departmentsNotAdded: [],
    departmentsAdded: [],
    CSHT: [],
    selectedCSHT: {}
  }
}

const namespaced = true

export const storesCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
