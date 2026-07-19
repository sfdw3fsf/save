import api from '@/modules/admin/category/CommissionRate/CommissionRateAPI'



// @payload 
// {
//   "tyle_tps_id": 22,
//   "loai": 1
// }
export const actions = {
  async getCommissionRateKhuVucDaGan ({ commit },payload) {
    const data = {
      "tyle_tps_id": payload,
      "loai": 1
    }
    let response = await api.getCommissionRateKhuVuc(this._vm.axios, data)
    try {
      commit('setDanhSachTyleTPSTheoKhuVucDaGan', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  },
  async getCommissionRateKhuVucChuaGan ({ commit },payload) {
    const data = {
      "tyle_tps_id": payload,
      "loai": 0
    }
    let response = await api.getCommissionRateKhuVuc(this._vm.axios, data)
    try {
      commit('setDanhSachTyleTPSTheoKhuVucChuaGan', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  }
}
