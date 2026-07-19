export const getters = {
  getDsChuKyNo: function (state, getters, rootState) {
    return state.listSubcribersNotReceiveDamage.dsChuKyNo
  },
  getDsThamSoMd: function (state, getters, rootState) {
    return state.listSubcribersNotReceiveDamage.dsThamSoMd
  },
  getSubcribers: function (state, getters, rootState) {
    return state.listSubcribersNotReceiveDamage.subcribers
  },
  getGridSubcribers: function (state, getters, rootState) {
    return state.listSubcribersNotReceiveDamage.gridSubcribers
  },
  getSubcriberSearched: function (state, getters, rootState) {
    return state.listSubcribersNotReceiveDamage.subcriberSearched
  }
}
