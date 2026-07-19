import api from '@/modules/install/InterDividePointInstallTSL/InterDividePointInstallTSLAPI'

export const actions = {
  async getDanhSachTocDoKenh ({ commit, state }) {
    let response = await api.getDanhSachTocDoKenh(this._vm.axios)
    try {
      commit('setTocDoKenh', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  }
}
