export const actions = {
  async setDsThuocTinh({ commit, state }, { dsThuocTinh }) {
    commit('setDsThuocTinh', dsThuocTinh)
  },
  async setValueThuocTinh({ commit, state }, { id, gttt_id, giatri }) {
    commit('setValueThuocTinh', { id, gttt_id, giatri })
  },
  async setErrorThuocTinh({ commit, state }, { id, error }) {
    commit('setErrorThuocTinh', { id, error })
  }
}
