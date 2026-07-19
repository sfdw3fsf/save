import api from '@/modules/install/InterDividePointInstallTSL/InterDividePointInstallTSLAPI'

export const actions = {
  async getDanhSachLoaiHD ({ commit, state }) {
    let response = await api.getDanhSachLoaiHD(this._vm.axios)
    try {
      commit('setLoaiHD', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  }
}
