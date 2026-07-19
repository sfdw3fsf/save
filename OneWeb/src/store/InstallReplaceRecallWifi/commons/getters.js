export const getters = {
  getSubcriberTypes (state, getters, rootState) {
    return state.installReplaceRecallWifi.subcriberTypes
  },
  getProcedures (state, getters, rootState) {
    return state.installReplaceRecallWifi.procedures
  },
  getPersonGiveWorks (state, getters, rootState) {
    return state.installReplaceRecallWifi.personGiveWorks
  },
  getChangeWireSubcribers (state, getters, rootState) {
    return state.installReplaceRecallWifi.changeWireSubcribers
  },
  getEmployees (state, getters, rootState) {
    return state.installReplaceRecallWifi.employees
  },
  getServices (state, getters, rootState) {
    return state.installReplaceRecallWifi.services
  },
  getIsNewCard (state, getters, rootState) {
    return state.installReplaceRecallWifi.isNewCard
  },
  getSelectedSubcriber (state, getters, rootState) {
    return state.installReplaceRecallWifi.selectedSubcriber
  },
  getPhieuId (state, getters, rootState) {
    return state.installReplaceRecallWifi.selectedSubcriber.phieu_id
  },
  getSelectedPersonGiveWorkId (state, getters, rootState) {
    return state.installReplaceRecallWifi.selectedPersonGiveWorkId
  },
  getSelectedDateGiveWork (state, getters, rootState) {
    return state.installReplaceRecallWifi.selectedDateGiveWork
  },
  getDirections (state, getters, rootState) {
    return state.installReplaceRecallWifi.directions
  }
}
