import API from '../../api/PhanChiaTuyenThu'

export const actions = {
  async getDanhSachTuyenThuByNhanVien({ commit, state }, data) {
    try {
      let response = await API.getDanhSachTuyenThuByNhanVien(this._vm.axios, data)
      commit('setDanhSachTuyenThu', response.data.data.data || [])
      return response
    } catch (error) {
      commit('setDanhSachTuyenThu', [])
      throw error
    }
  },
  clearData({ commit, sate }) {
    commit('clearData')
  },
  async getDanhSachDotGiao({ commit, state }, data) {
    try {
      let response = await API.getDanhSachDotGiao(this._vm.axios, data)
      commit('setDanhSachDotGiao', response.data.data.data || [])
      return response
    } catch (error) {
      commit('setDanhSachDotGiao', [])
      throw error
    }
  },
  async getDanhSachQuan({ commit, state }, data) {
    try {
      let response = await API.getDanhSachQuan(this._vm.axios, data)
      commit('setDanhSachQuan', response.data.data || [])
      return response
    } catch (error) {
      commit('setDanhSachQuan', [])
      throw error
    }
  },
  async getDanhSachPhuong({ commit, state }, data) {
    try {
      let response = await API.getDanhSachPhuong(this._vm.axios, data)
      commit('setDanhSachPhuong', response.data.data || [])
      return response
    } catch (error) {
      commit('setDanhSachPhuong', [])
      throw error
    }
  },
  async getDanhSachPho({ commit, state }, data) {
    try {
      let response = await API.getDanhSachPho(this._vm.axios, data)
      commit('setDanhSachPho', response.data.data || [])
      return response
    } catch (error) {
      commit('setDanhSachPho', [])
      throw error
    }
  },
  async getDanhSachAp({ commit, state }, data) {
    try {
      let response = await API.getDanhSachAp(this._vm.axios, data)
      commit('setDanhSachAp', response.data.data || [])
      return response
    } catch (error) {
      commit('setDanhSachAp', [])
      throw error
    }
  },
  async getDanhSachPhanChiaTuyenThu({ commit, state }, data) {
    try {
      let response = await API.getDanhSachPhanChiaTuyenThu(this._vm.axios, data)
      commit('setDanhSachPhanChiaTuyenThu', response.data.data || [])
      return response
    } catch (error) {
      commit('setDanhSachPhanChiaTuyenThu', [])
      // throw error
      return error.data
    }
  },
  async getDanhSachChuaPhanTuyen({ commit, state }, data) {
    try {
      let response = await API.getDanhSachChuaPhanTuyen(this._vm.axios, data)
      commit('setDanhSachChuaPhanTuyen', response.data.data.data || [])
      return response
    } catch (error) {
      commit('setDanhSachChuaPhanTuyen', [])
      // throw error
      return error.data
    }
  },
  async capNhatDanhSachPhanChiaTuyenThu({ commit, state }, data) {
    try {
      let response = await API.capNhatDanhSachPhanChiaTuyenThu(this._vm.axios, data)
      return response
    } catch (error) {
      // throw error
      return error.data
    }
  },
}
