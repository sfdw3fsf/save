import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  feeCollectionFactor: {
    feeList: [],
    employeeTypesAdded: [],
    employeeTypesNotAdd: [],
    invoicePeriodsNotAdd: [],
    invoicePeriodsAdded: [],
    feeTypes: [],
    selectedFeeFactor: {}
  }
}

const namespaced = true

export const feeCollectionFactorCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
