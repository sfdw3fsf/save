import API from '../../api/XuLyThietBiBaoHanhAPI'

export const actions = {
  async getDSTrangThaiBHDich ({ commit, state }, data) {
    let response = await API.getDSTrangThaiBHDich(this._vm.axios, data)
    try {
      commit('setDSTTBHDich', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },
  async getDSBaoHanhTBHienTai ({ commit, state }, data) {
    let response = await API.getDSBaoHanhTBHienTai(this._vm.axios, data)
    try {
      // let DSBaoHanhTBHienTai = response.data.data || [];
      // DSBaoHanhTBHienTai.forEach(a=>a.KEY = {BAOHANH_ID: a.BAOHANH_ID,PHIEUBH_ID:a.PHIEUBH_ID })
      // commit('setDSBaoHanhTBHienTai', DSBaoHanhTBHienTai || [])
      return response.data.data || []
    } catch (error) {
      throw error
    }
    return response
  },
  async getThongTinTrangThaiBH ({ commit, state }, data) {
    let response = await API.getThongTinTrangThaiBH(this._vm.axios, data)
    try {
      commit('setThongTinTrangThaiBH', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },
  async getDSKhoNhaCungCap ({ commit, state }, data) {
    let response = await API.getDSKhoNhaCungCap(this._vm.axios, data)
    try {
      commit('setDSKhoNhaCungCap', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },
  async chuyenTrangThaiBaoHanh ({ commit, state }, data) {
    let response = await API.chuyenTrangThaiBaoHanh(this._vm.axios, data)
    return response
  },
  async huyChuyenTrangThaiBH ({ commit, state }, data) {
    let response = await API.huyChuyenTrangThaiBH(this._vm.axios, data)
    return response
  },
  async capNhatKetQuaBH ({ commit, state }, data) {
    let response = await API.capNhatKetQuaBH(this._vm.axios, data)
    return response
  },
  async xemBaoCao ({ commit, state }, data) {
    let response = await API.xemBaoCao(this._vm.axios, data)
    return response
  },
}