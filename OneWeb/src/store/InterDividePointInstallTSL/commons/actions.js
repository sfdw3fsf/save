import api from '@/modules/admin/category/ClassifyService/ClassifyServiceAPI'

export const actions = {
  async getDanhSachThueBao ({ commit, state }) {
    let response = await api.getDanhSachThueBao(this._vm.axios)
    try {
      commit('setDanhSachThueBao', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  }
}
