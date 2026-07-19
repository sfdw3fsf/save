import API from '../../../modules/QLTN/api/Commons'

export const actions = {
  async getDanhSachDonVi ({ commit, state }, data) {
    let response = await API.getDanhSachDonVi(this._vm.axios)
    try {
      commit('setDanhSachDonVi', response.data.data.body)
    } catch (error) {
      throw error
    }
    return response
  }
}
