import api from '@/modules/admin/category/UpdateHouseholdInformation/api/index'
export const actions = {
  async kiemtra_capnhat_hodan ({commit, state}, {danhsach_HopLe, danhsach_Loi}) {
    const params = {
      "danhsach_HopLe": danhsach_HopLe,
      "danhsach_Loi": danhsach_Loi
    }
    const response = await api.kiemtra_capnhat_hodan(this._vm.axios, params)
    try {
      return response.data.data
    } catch (err) {
      throw err
    }
  },
  async updateThongTinHoDan ({ commit, state }, params) {
    try {
      const res = await api.updateThongTinHoDan(this._vm.axios, params)
      console.log("api update:", res)
      return res
    } catch (error) {
      throw error
    }
  }
}
