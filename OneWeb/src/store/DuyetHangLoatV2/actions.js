export const actions = {
  setDSHDTB({ commit, state }, postdata) {
    try {
      commit("setDSHDTB", postdata)
    } catch (e) {
      throw e;
    }
  },
  setRAKichHoat ({ commit, state }, postdata) {
    try {
      commit("setRAKichHoat", postdata)
    } catch (e) {
      throw e;
    }
  }
}
