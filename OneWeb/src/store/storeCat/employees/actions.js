import api from '../../../modules/admin/category/Store/api'

export const actions = {
  async loadEmployees ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getEmployees(this._vm.axios, data)
    commit('setEmployees', res.data.data)
  },
  async loadEmployeesNotAdded ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getEmployeesNotAdded(this._vm.axios, data)
    commit('setEmployeesNotAdded', res.data.data)
  },
  async loadEmployeesAdded ({ state, commit, rootState, rootGetters }, data) {
    if (!data.kho_id || data.kho_id === -1) { return }

    const res = await api.getEmployeesAdded(this._vm.axios, data)
    commit('setEmployeesAdded', res.data.data)
  },
  addEmployeesToEmployeesAdded ({ state, commit, rootState, rootGetters }, data) {
    commit('addEmployeesToEmployeesAdded', data)
  },
  removeEmployeesFromEmployeesAdded ({ state, commit, rootState, rootGetters }, data) {
    commit('removeEmployeesFromEmployeesAdded', data)
  },
  resetEmployees ({ state, commit, rootState, rootGetters }, data) {
    commit('resetEmployees', data)
  }
}
