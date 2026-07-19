import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  listTelecomService: [],
  listSubscriptionType: [],
  listRequestType: [],
  listChanelSpeed: [],
  listSatellite: [],
  listContract: [],
  dsThueBao: [],
  dsLyDoHuy: [],
  dsNhomHuy: []
}

const namespaced = true

export const liquidateCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
