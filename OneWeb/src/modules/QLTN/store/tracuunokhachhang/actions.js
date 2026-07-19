import API from '../../api/TraCuuNoKhachHang'

export const actions = {
  async getDanhSachThongTinNo ({ commit, state }, data) {
    let response = await API.getDanhSachThongTinNo(this._vm.axios, data)
    try {
      commit('setDanhSachThongTinNo', response.data.data.data || []);
      return response;
    } catch (error) {
      commit('setDanhSachThongTinNo', [])
      return error.data;
    }
  },
  getObjSearch ({commit, state}, data) {
    commit('setObjSearch', data)
  },
  clearData ({commit, sate}) {
    commit('clearData')
  }
}
