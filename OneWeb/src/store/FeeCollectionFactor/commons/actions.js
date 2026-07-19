import api from '../../../modules/admin/category/FeeCollectionFactor/api'

export const actions = {
  setSelectedFeeFactor ({ state, commit, rootState, rootGetters }, data) {
    commit('setSelectedFeeFactor', data)
  },
  async loadFeeList ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getFeeList(this._vm.axios, data)
    if (res.data.data) {
      commit('setFeeList', res.data.data)

      const feeTypes = []
      for (let i = 0; i < res.data.data.length; i++) {
        const existEle = feeTypes.find(k => k.kieu_tcr_id === res.data.data[i].kieu_tcr_id)
        if (existEle) {
          continue
        }
        feeTypes.push(res.data.data[i])
      }

      commit('setFeeTypes', feeTypes)
    }
  },
  async loadEmployeeTypesNotAdd ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getEmployeeTypes(this._vm.axios, data)
    commit('setEmployeeTypesNotAdd', res.data.data)
  },
  async loadEmployeeTypesAdded ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getEmployeeTypes(this._vm.axios, data)
    commit('setEmployeeTypesAdded', res.data.data)
  },
  setEmployeeTypesAdded ({ state, commit, rootState, rootGetters }, data) {
    commit('setEmployeeTypesAdded', data)
  },
  async loadInvoicePeriodsNotAdd ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getInvoicePeriods(this._vm.axios, data)
    commit('setInvoicePeriodsNotAdd', res.data.data)
  },
  async loadInvoicePeriodsAdded ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getInvoicePeriods(this._vm.axios, data)
    commit('setInvoicePeriodsAdded', res.data.data)
  },
  setInvoicePeriodsAdded ({ state, commit, rootState, rootGetters }, data) {
    commit('setInvoicePeriodsAdded', data)
  },
  addInvoicePeriodsToAddedList ({ state, commit, rootState, rootGetters }, data) {
    commit('addInvoicePeriodsToAddedList', data)
  },
  removeInvoicePeriodsFromList ({ state, commit, rootState, rootGetters }, data) {
    commit('removeInvoicePeriodsFromList', data)
  },
  addEmployeesToAddedList ({ state, commit, rootState, rootGetters }, data) {
    commit('addEmployeesToAddedList', data)
  },
  removeEmployeesFromAddedList ({ state, commit, rootState, rootGetters }, data) {
    commit('removeEmployeesFromAddedList', data)
  },
  async saveRecord ({ state, commit, rootState, rootGetters }, data) {
    await api.save(this._vm.axios, data)
  },
  async deleteRecord ({ state, commit, rootState, rootGetters }, data) {
    await api.deleteRecord(this._vm.axios, data)
  }
}
