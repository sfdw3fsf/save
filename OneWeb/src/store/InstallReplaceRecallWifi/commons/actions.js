import api from '../../../modules/contract/setup/InstallReplaceRecallWifi/api'

export const actions = {
  async loadSubcriberTypes ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getSubcriberTypes(this._vm.axios, data)
    commit('setSubcriberTypes', res.data.data)
  },
  async loadProcedures ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getProcedures(this._vm.axios, data)
    commit('setProcedures', res.data.data)
  },
  async loadDirectionList ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getDirectionList(this._vm.axios, data)
    commit('setDirections', res.data.data)
  },
  async loadPersonGiveWorks ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getPersonGiveWork(this._vm.axios, data)
    commit('setPersonGiceWorks', res.data.data)
  },
  async loadChangeWireSubcribers ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getChangeWireSubcribers(this._vm.axios, data)
    commit('setChangeWireSubcribers', res.data.data)
  },
  async loadEmployees ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getEmployees(this._vm.axios, data)
    commit('setEmployees', res.data.data)
  },
  setServices ({ state, commit, rootState, rootGetters }, data) {
    commit('setServices', data)
  },
  async haveToDo ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.haveToDo(this._vm.axios, data)
    console.log('haveToDo', res)
  },
  setIsNewCard ({ state, commit, rootState, rootGetters }, isNewCard) {
    commit('setIsNewCard', isNewCard)
  },
  setSelectedSubcriber ({ state, commit, rootState, rootGetters }, selectedSubcriber) {
    commit('setSelectedSubcriber', selectedSubcriber)
  },
  setSelectedPersonGiveWorkId ({ state, commit, rootState, rootGetters }, selectedPersonGiveWorkId) {
    commit('setSelectedPersonGiveWorkId', selectedPersonGiveWorkId)
  },
  setSelectedDateGiveWork ({ state, commit, rootState, rootGetters }, SelectedDateGiveWork) {
    commit('setSelectedDateGiveWork', SelectedDateGiveWork)
  },
  setDirections ({ state, commit, rootState, rootGetters }, directions) {
    commit('setDirections', directions)
  }
}
