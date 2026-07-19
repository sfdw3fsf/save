import API from '../../../modules/QLTN/api/TraCuuTinhHinhThuNo'

export const actions = {
  async getDanhSachNVThuCuoc({ commit, state }, data) {
    let response = await API.getDanhSachNVThuCuoc(this._vm.axios)
    try {
      commit('setDanhSachNVThuCuoc', response.data.data.data)
    } catch (error) {
      throw error
    }
    return response
  }
}
