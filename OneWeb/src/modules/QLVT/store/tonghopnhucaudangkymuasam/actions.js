import API from '../../api/TongHopNhuCauDangKyMuaSamAPI'

export const actions = {
  async getDSDotDangKy({ commit, state }, data) {
    let response = await API.getDSDotDangKy(this._vm.axios, data)
    try {
      commit('setDSDotDangKy', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },

  async getDSDonVi({ commit, state }) {
    let response = await API.getDSDonVi(this._vm.axios)
    try {
      commit('setDSDonVi', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },

  async getDSMucDich({ commit, state }) {
    let response = await API.getDSMucDich(this._vm.axios)
    try {
      commit('setDSMucDich', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },

  async getDSDotTongHop({ commit, state }, thangTh) {
    let response = await API.getDSDotTongHop(this._vm.axios, thangTh)
    try {
      commit('setDSDotTongHop', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },

  async getDSDangKy({ commit, state }, data) {
    let response = await API.getDSDangKy(this._vm.axios, data)
    try {
        return response.data.data || []
    } catch (error) {
        throw error
    }
    return response
  },
  // async getDSDangKy({ commit, state }, data) {
  //   let response = await API.getDSDangKy(this._vm.axios, data)
  //   try {
  //     commit('setDSDangKy', response.data.data || [])
  //   } catch (error) {
  //     throw error
  //   }
  //   return response
  // },

  async getDSTongHop({ commit, state }, data) {
    let response = await API.getDSTongHop(this._vm.axios, data)
    try {
        return response.data.data || []
    } catch (error) {
        throw error
    }
    return response
  },
  // async getDSTongHop({ commit, state }, tongHopId) {
  //   let response = await API.getDSTongHop(this._vm.axios, tongHopId)
  //   try {
  //     commit('setDSTongHop', response.data.data || [])
  //   } catch (error) {
  //     throw error
  //   }
  //   return response
  // },

  async kiemTraYeuCauTongHop({ commit, state }, tongHopId) {
    let response = await API.kiemTraYeuCauTongHop(this._vm.axios, tongHopId)
    try {
      commit('setKiemTraYeuCauTongHop', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },

  async kiemTraNgayDuyet({ commit, state }, tongHopId) {
    let response = await API.kiemTraNgayDuyet(this._vm.axios, tongHopId)
    try {
      commit('setKiemTraNgayDuyet', response.data.data || 0)
    } catch (error) {
      throw error
    }
    return response
  },

  async duyetHuyDuyetTongHop({ commit, state }, data) {
    let response = await API.duyetHuyDuyetTongHop(this._vm.axios, data)
    try {
      commit('setDuyetHuyDuyetTongHop', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },

  async kiemTraHopDongDaLap({ commit, state }, tongHopId) {
    let response = await API.kiemTraHopDongDaLap(this._vm.axios, tongHopId)
    try {
      commit('setKiemTraHopDongDaLap', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },

  async kiemTraChungTuDaLap({ commit, state }, tongHopId) {
    let response = await API.kiemTraChungTuDaLap(this._vm.axios, tongHopId)
    try {
      commit('setKiemTraChungTuDaLap', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },

  async kiemTraCungChuKy({ commit, state }, data) {
    let response = await API.kiemTraCungChuKy(this._vm.axios, data)
    try {
      commit('setKiemTraCungChuKy', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },

  async tongHopNhuCauDangKy({ commit, state }, data) {
    let response = await API.tongHopNhuCauDangKy(this._vm.axios, data)
    try {
      commit('setTongHopNhuCauDangKy', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },

  async xoaTongHopDangKy({ commit, state }, listVTDKId) {
    let response = await API.xoaTongHopDangKy(this._vm.axios, listVTDKId)
    try {
      commit('setXoaTongHopDangKy', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },
  async capNhatGhiChu({ commit, state }, data) {
    let response = await API.capNhatGhiChu(this._vm.axios, data)
    try {
      commit('setCapNhatGhiChu', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },
  async getParamsBaoCao({ commit, state }, maBc) {
    let response = await API.getParamsBaoCao(this._vm.axios, maBc)
    return response.data.data
  },
}