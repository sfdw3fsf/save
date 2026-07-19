export const getters = {
  getPurposesNotAdded (state, getters, rootState) {
    return state.purposes.purposesNotAdded
  },
  getPurposesAdded (state, getters, rootState) {
    return state.purposes.purposesAdded
  }
}
