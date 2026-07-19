import API from '../../api/HoanTraThietBiBaoHanhAPI'
import moment from 'moment'

export const actions = {
  async getDanhSachNhanVien ({ commit, state }, data) {
    let response = await API.getDanhSachNhanVien(this._vm.axios, data)
    try {
      commit('setDanhSachNhanVien', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },

  async getDanhSachPhieuBH ({ commit, state }, data) {
    let response = await API.getDanhSachPhieuBH(this._vm.axios, data)
    try {
      let DanhSachPhieuBH = response.data.data || []
      DanhSachPhieuBH.forEach(a=>a.NGAY_YC = moment(a.NGAY_YC,'DD-MM-YYYY hh:mm:ss').format('DD/MM/YYYY hh:mm'))
      commit('setDSPhieuBH', DanhSachPhieuBH || [])
    } catch (error) {
      throw error
    }
    return response
  },

  async getThongTinBaoHanhTB ({ commit, state }, data) {
    let response = await API.getThongTinBaoHanhTB(this._vm.axios, data)
    try {
      commit('setThongTinBaoHanhTB', response.data.data[0] || "")
    } catch (error) {
      throw error
    }
    return response
  },
  
  async hoanThienBaoHanh ({ commit, state }, data) {
    let response = await API.hoanThienBaoHanh(this._vm.axios, data)
    return response
  },

  async getMaToTheoDonVi ({ commit, state }, data) {
    let response = await API.getMaToTheoDonVi(this._vm.axios, data)
    try {
      commit('setMaToTheoDonVi', response.data.data[0] || "")
    } catch (error) {
      throw error
    }
    return response
  },
  
  getObjSearch ({commit, state}, data) {
    commit('setObjSearch', data)
  },
  clearData ({commit, sate}, data) {
    commit('clearData', data)
  }
}
