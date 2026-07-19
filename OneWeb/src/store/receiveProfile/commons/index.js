import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  listStatus: [],
  listGroupFile: [],
  listTypeFile: [],
  listAgency: [],
  listEmp: [],
  listReason: [],
  listAssign: [],
  listAppointment: []
}

const namespaced = true

export const receiveProfileCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
