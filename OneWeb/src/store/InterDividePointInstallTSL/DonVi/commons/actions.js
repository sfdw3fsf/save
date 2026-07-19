import api from '@/modules/install/InterDividePointInstallTSL/InterDividePointInstallTSLAPI'

export const actions = {
  async getDanhSachDonVi ({ commit, state }, data) {
    let response = await api.getDanhSachDonVi(this._vm.axios, data)
    try {
      commit('setDonVi', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  }
}
