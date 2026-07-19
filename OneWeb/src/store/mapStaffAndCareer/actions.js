export const actions = {
  moveCareerNotMappedToMapped ({ commit, state }, lstChecked) {
    if (lstChecked.length == 0) return
    commit('pushToCareerMappedList', lstChecked)
    commit('removeFromNotCareerMappedList', lstChecked)
  },

  moveCareerMappedToNotMapped ({ commit, state }, lstChecked) {
    if (lstChecked.length == 0) return
    commit('pushToNotCareerMappedList', lstChecked)
    commit('removeFromCareerMappedList', lstChecked)
  }
}
