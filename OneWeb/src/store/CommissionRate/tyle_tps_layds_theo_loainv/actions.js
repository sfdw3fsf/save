import api from '@/modules/admin/category/CommissionRate/CommissionRateAPI'
//payload = {
//   "tyle_tps_id": 23,
//   "loai": 0
// }
export const actions = {
  async getCommissionRateNVChuaGan({ commit }, payload) {
    const data = {
      "tyle_tps_id": payload,
      "loai": 0
    }
    let response = await api.getCommissionRateNV(this._vm.axios, data)
    try {
      commit('setDanhSachTyleTPSTheoLoaiNVChuaGan', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  },
  async getCommissionRateNVDaGan({ commit }, payload) {
    const data = {
      "tyle_tps_id": payload,
      "loai": 1
    }
    let response = await api.getCommissionRateNV(this._vm.axios, data)
    try {
      commit('setDanhSachTyleTPSTheoLoaiNVDaGan', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  }
}
