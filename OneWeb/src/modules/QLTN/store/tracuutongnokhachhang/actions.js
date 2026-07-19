import API from '../../api/TraCuuTongNoKhachHangAPI'

export const actions = {
  async getDanhSachTongNoKH({ commit, state }, data) {
    try {
      let response = await API.getDanhSachTongNoKH(this._vm.axios, data)
      commit('setDanhSachTongNoKH', response.data.data.data || [])
      return response
    } catch (error) {
      // throw error
      return error.data
    }
  },
  async getDanhSachTongTraKH({ commit, state }, data) {
    try {
      let response = await API.getDanhSachTongTraKH(this._vm.axios, data)
      commit('setDanhSachTongTraKH', response.data.data.data || [])
      return response
    } catch (error) {
      // throw error
      return error.data
    }
  },
  getObjSearch({ commit, state }, data) {
    commit('setObjSearch', data)
  },
  async getThongTinTTThueBao({ commit, state }, data) {
    try {
      let response = await API.getThongTinTTThueBao(this._vm.axios, data)
      commit('setThongTinTT', response.data.data[0])
      return response
    } catch (error) {
      // throw error
      return error.data
    }
  },
  async getThongTinTTThanhToan({ commit, state }, data) {
    try {
      let response = await API.getThongTinTTThanhToan(this._vm.axios, data)
      commit('setThongTinTT', response.data.data[0])
      return response
    } catch (error) {
      // throw error
      return error.data
    }
  },
  clearData({ commit, sate }, data) {
    commit('clearData', data)
  }
}
