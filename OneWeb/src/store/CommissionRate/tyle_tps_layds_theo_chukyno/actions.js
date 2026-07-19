import api from '@/modules/admin/category/CommissionRate/CommissionRateAPI'



// @payload Number
// 22
export const actions = {
  async getCommissionRateChuKyDaGan({ commit }, payload) {
    const data = {
      "tyle_tps_id": payload,
      "loai": 1
    }
    let response = await api.getCommissionRateChuKy(this._vm.axios, data)
    try {
      commit('setDanhSachTyleTPSTheoChuKyNoDaGan', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  },
  async getCommissionRateChuKyChuaGan({ commit }, payload) {
    const data = {
      "tyle_tps_id": payload,
      "loai": 0
    }
    let response = await api.getCommissionRateChuKy(this._vm.axios, data)
    try {
      commit('setDanhSachTyleTPSTheoChuKyNoChuaGan', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  }
}
