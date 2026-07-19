import API from '../../api/TraCuuSeriHoaDonThanhToan'

export const actions = {
  async getDanhSachSeriHoaDon ({ commit, state }, data) {
    let response = await API.getDanhSachHoaDonThanhToan(this._vm.axios, data)
    try {
      commit('setDanhSachHoaDonThanhToan', response.data.data.data || []);
      return response;
    } catch (error) {
      commit('setDanhSachHoaDonThanhToan',[])
      return error.data
    }
  },

  async getThongTinTTByTT ({ commit, state }, data) {
    let response = await API.getThongTinTTByTT(this._vm.axios, data)
    try {
      commit('setThongTinTT', response.data.data[0] || {});
      return response
    } catch (error) {
      commit('setThongTinTT',{})
      return error.data
    }
  },

  async getThongTinTTByTB ({ commit, state }, data) {
    let response = await API.getThongTinTTByTB(this._vm.axios, data)
    try {
      commit('setThongTinTT', response.data.data[0] || {});
      return response;
    } catch (error) {
      commit('setThongTinTT',{})
      return error.data
    }
  },

  async getThongTinTTByKH ({ commit, state }, data) {
    let response = await API.getThongTinTTByKH(this._vm.axios, data)
    try {
      commit('setThongTinTT', response.data.data[0] || {});
      return response;
    } catch (error) {
      commit('setThongTinTT',{})
      return error.data
    }
  },

  getObjSearch ({commit, state}, data) {
    commit('setObjSearch', data)
  },
  clearData ({commit, sate}, data) {
    commit('clearData', data)
  }
}
