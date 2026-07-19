import API from '../../api/QuanLyThuTuIn'

export const actions = {
  async fetchKiemtraSolieu({ commit, state }, data) {
    try {
      let response = await API.fetchKiemtraSolieu(this._vm.axios, data)
      commit('setDanhSachLoi', response.data.data || [])
      return response
    } catch (error) {
      commit('setDanhSachLoi', [])
      // throw error
      return error.data
    }
  },
  clearData({ commit, sate }) {
    commit('clearData')
  },
  async getDanhSachNVThuCuoc({ commit, state }, data) {
    try {
      let response = await API.getDanhSachNVThuCuoc(this._vm.axios, data)
      commit('setDanhSachNhanVien', response.data.data.data || [])
      return response
    } catch (error) {
      commit('setDanhSachNhanVien', [])
      throw error
    }
  },
  async capNhatDotGiao({ commit, state }, data) {
    try {
      const response = await API.capNhatDotGiao(this._vm.axios, data)
      return response
    } catch (error) {
      console.log('err', error.response)
      // if (error.response && error.response.status === 400) {
      //   throw new Error(error.response.data.message);
      // }
      return error.data
    }
  },
}
