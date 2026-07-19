import api from '@/modules/install/InterDividePointInstallTSL/InterDividePointInstallTSLAPI'

export const actions = {
  async getDanhSachNhanVienTheoDonVi ({ commit, state }) {
    let response = await api.getDanhSachNhanVienTheoDonVi(this._vm.axios)
    try {
      commit('setNhanVienTheoDonVi', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  }
}
