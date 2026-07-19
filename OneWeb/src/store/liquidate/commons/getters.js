export const getters = {
  listTelecomService: (state) => {
    return state.listTelecomService
  },
  listSubscriptionType: (state) => {
    return state.listSubscriptionType
  },
  listRequestType: (state) => {
    return state.listRequestType
  },
  listChanelSpeed: (state) => {
    return state.listChanelSpeed
  },
  listSatellite: (state) => {
    return state.listSatellite
  },
  listContract: (state, getters, rootState) => {
    return state.listContract
  },
  dsThueBao: (state, getters, rootState) => {
    return state.dsThueBao
  },
  dsLyDoHuy: (state) => {
    return state.dsLyDoHuy
  },
  dsNhomHuy: (state) => {
    return state.dsNhomHuy
  }
}
