import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  employees: {
    employees: [],
    employeesNotAdded: [],
    employeesAdded: []
  }
}

const namespaced = true
export const employeesCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
