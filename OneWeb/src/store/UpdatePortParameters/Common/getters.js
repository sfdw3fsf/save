export const getters = {
  getServices (state, getters, rootState) {
    return state.portParameters.services
  },
  getSubcribertypes (state, getters, rootState) {
    return state.portParameters.subcriberTypes
  },
  getSpeedList (state, getters, rootState) {
    return state.portParameters.speedList
  },
  getBrasList (state, getters, rootState) {
    return state.portParameters.brasList
  },
  getDsLam (state, getters, rootState) {
    return state.portParameters.dsLam
  }
}
