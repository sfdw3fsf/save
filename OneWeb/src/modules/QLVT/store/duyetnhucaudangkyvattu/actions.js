import API from '../../api/DuyetNhuCauDangKyVatTuAPI'

export const actions = {
  async getDSDonViTinh({ commit, state }) {
    let response = await API.getDSDonViTinh(this._vm.axios)
    return response.data.data
  },

  async getDSTrangThaiDangKy({ commit, state }) {
    let response = await API.getDSTrangThaiDangKy(this._vm.axios)
    return response.data.data
  },

  async getDSDonViDangKy({ commit, state }) {
    let response = await API.getDSDonViDangKy(this._vm.axios)
    return response.data.data
  },

  async getDSNhuCauDangKy({ commit, state }, data) {
    let response = await API.getDSNhuCauDangKy(this._vm.axios, data)
    return response.data.data
  },

  async getDSChiTietVatTu({ commit, state }, dang_ky_id) {
    let response = await API.getDSChiTietVatTu(this._vm.axios, dang_ky_id)
    return response.data.data
  },

  async kiemTraTrangThai({ commit, state }, data) {
    let response = await API.kiemTraTrangThai(this._vm.axios, data)
    return response.data.data
  },

  async capNhatNhuCauDangKy({ commit, state }, data) {
    let response = await API.capNhatNhuCauDangKy(this._vm.axios, data)
    return response.data.data
  },

  async getDsQuyTrinh({ commit, state }, data) {
    let response = await API.getDsQuyTrinh(this._vm.axios, data)
    return response.data.data
  },
  async getDsLuanChuyen({ commit, state }, data) {
    let response = await API.getDsLuanChuyen(this._vm.axios, data)
    return response.data.data
  },
  async getDsChiTietLuanChuyen({ commit, state }, data) {
    let response = await API.getDsChiTietLuanChuyen(this._vm.axios, data)
    return response.data.data
  },
  async traPhieuLuanChuyen({ commit, state }, data) {
    let response = await API.traPhieuLuanChuyen(this._vm.axios, data)
    return response.data.data
  },
  async layDSSerialExcel({ commit, state }, deNghiId) {
    let response = await API.layDSSerialExcel(this._vm.axios, deNghiId)
    return response.data.data
  },
  async hoanThanhGiaoPhieuLuanChuyen({ commit, state }, data) {
    let response = await API.hoanThanhGiaoPhieuLuanChuyen(this._vm.axios, data)
    return response.data.data
  },
}