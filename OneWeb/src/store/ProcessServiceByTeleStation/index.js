import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  requestTypeList: [],
  employeeList: [],
  serviceList: [],
  processList: [],
  contractList: [],
  loaiHinhList: []
}

const namespaced = true

export const processServiceByTeleStation = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
