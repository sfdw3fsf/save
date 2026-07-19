import API from '../../api/TraCuuXuLyNo'

export const actions = {
  async getDanhSachXuLyNo ({ commit, state }, data) {
    let response = await API.getDanhSachXuLyNo(this._vm.axios, data)
    try {
      commit('setDanhSachXuLyNo', response.data.data.data || []);
      return response;
    } catch (error) {
      commit('setDanhSachXuLyNo', [])
      return error.data
    }
  },
  clearData ({commit, sate}) {
    commit('clearData')
  }
}
