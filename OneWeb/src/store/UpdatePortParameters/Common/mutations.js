export const mutations = {
  setSubcriberTypes (state, data) {
    state.portParameters.subcriberTypes = data
  },
  setBrasList (state, data) {
    state.portParameters.brasList = data
  },
  setServices (state, data) {
    state.portParameters.services = data
  },
  setDsLam (state, data) {
    state.portParameters.dsLam = data
  },
  setSpeedList (state, data) {
    state.portParameters.speedList = data
  }
}
