import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  installReplaceRecallWifi: {
    services: [],
    subcriberTypes: [],
    procedures: [],
    personGiveWorks: [],
    changeWireSubcribers: [],
    employees: [],
    isNewCard: true,
    selectedSubcriber: {},
    selectedPersonGiveWorkId: 0,
    selectedDateGiveWork: null,
    directions: []
  }
}

const namespaced = true

export const installReplaceRecallWifiCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
