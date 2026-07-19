export const actions = {
  setTsktDvCntt({ commit, state }, postdata) {
    try {
      commit("setTsktDvCntt", postdata)
    } catch (e) {
      throw e;
    }
  },
  setThongTinHDTB({ commit, state }, postdata) {
    try {
      commit("setThongTinHDTB", postdata)
    } catch (e) {
      throw e;
    }
  }
}
