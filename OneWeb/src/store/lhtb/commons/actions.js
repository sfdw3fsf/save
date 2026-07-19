import api from '@/modules/admin/category/ClassifyService/ClassifyServiceAPI'

export const actions = {
  async getDanhSachLoaiHinhTB ({ commit, state }) {
    let response = await api.getDanhSachLoaiHinhTB(this._vm.axios)
    try {
      commit('setLoaiHinhThueBao', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  }
}
