import API from '../../api/MoHinhBaoHanhThietBiAPI'

export const actions = {
  async getQuyTrinhBHTB ({ commit, state }, data) {
    let response = await API.getQuyTrinhBHTB(this._vm.axios, data)
    try {
      let DanhSachQuyTrinh = [];
      response.data.data.forEach(a=>DanhSachQuyTrinh.push({'id': parseInt(a.quyTrinhId),'text' : a.quyTrinh}))
      commit('setDanhSachQuyTrinh', DanhSachQuyTrinh || [])
    } catch (error) {
      throw error
    }
    return response
  },
  async getDSLuongPhieuTheoQT ({ commit, state }, data) {
    let response = await API.getDSLuongPhieuTheoQT(this._vm.axios, data)
    try {
      commit('setDSLuongPhieuTheoQT', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },
  async getDSBaoHanhThietBiV2 ({ commit, state }, data) {
    let response = await API.getDSBaoHanhThietBiV2(this._vm.axios, data)
    try {
      commit('setDSBaoHanhThietBiV2', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },
  async getDSLine ({ commit, state }, data) {
    let response = await API.getDSLine(this._vm.axios, data)
    try {
      commit('setDSLine', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },
  async getDSPic ({ commit, state }, data) {
    let response = await API.getDSPic(this._vm.axios, data)
    try {
      commit('setDSPic', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },
}