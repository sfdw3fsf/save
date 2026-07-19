import api from '@/modules/admin/category/CommissionRate/CommissionRateAPI'

export const actions = {
  async getDanhSachTyLeThuCuoc ({ commit },payload) {
    let response = await api.sp_tt_tyle_tps(this._vm.axios,payload)
    try {
      commit('setDanhSachTyLeThuCuoc', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  }
}
