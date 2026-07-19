import API from '../../api/TraCuuHoaDonChuyenKhoan'

export const actions = {
  async getDanhSachBienNhan ({ commit, state }, data) {
    let response = await API.getDanhSachBienNhanThuTaiNha(this._vm.axios, data)
    try {
      commit('setDanhSachBienNhan', response.data.data.data || [])
      return response
    } catch (error) {
      return error.data
    }
  },
  async getDanhSachHoaDonChuyenKhoan ({ commit, state }, data) {
    let response = await API.getDanhSachHoaDonChuyenKhoan(this._vm.axios, data)
    try {
      commit('setDanhSachHoaDonChuyenKhoan', response.data.data.data || [])
      return response
    } catch (error) {
      return error.data
    }

  },
  getObjSearch ({commit, state}, data) {
    commit('setObjSearch', data)
  },
  clearData ({commit, sate}) {
    commit('clearData')
  }
}
