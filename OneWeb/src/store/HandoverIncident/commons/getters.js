export const getters = {
  getDsDichVuVt (state, getters, rootState) {
    return state.handoverIncident.dsDichVuVt
  },
  getStatuses (state, getters, rootState) {
    return state.handoverIncident.statuses
  }
}
