import api from '@/modules/admin/category/ClassifyService/ClassifyServiceAPI'

export const actions = {
  async getDanhSachDichVuVienThong ({ commit }) {
    let response = await api.getDanhSachDichVuVT(this._vm.axios)
    try {
      commit('setDanhSachDichVuVienThong', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  }
}
