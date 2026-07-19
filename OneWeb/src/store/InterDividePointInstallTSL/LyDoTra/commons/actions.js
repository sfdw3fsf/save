import api from '@/modules/install/InterDividePointInstallTSL/InterDividePointInstallTSLAPI'

export const actions = {
  async getDanhSachLyDoTra ({ commit, state }, data) {
    let response = await api.getDanhSachLyDoTra(this._vm.axios, data)
    try {
      commit('setLyDoTra', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  }
}
