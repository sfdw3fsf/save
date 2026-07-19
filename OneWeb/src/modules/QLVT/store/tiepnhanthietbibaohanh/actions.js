import API from '../../api/TiepNhanThietBiBaoHanhAPI'
import moment from 'moment'

export const actions = {
  async getDSDonViTheoNVLoaiDV ({ commit, state }, data) {
    let response = await API.getDSDonViTheoNVLoaiDV(this._vm.axios, data)
    try {
      let DanhSachDonVi = [];
      response.data.data.forEach(a=>DanhSachDonVi.push({'id': a.DONVI_ID,'text' : a.TEN_HT}))
      commit('setDanhSachDonVi', DanhSachDonVi || [])
    } catch (error) {
      throw error
    }
    return response
  },
  async getDSKieuBaoHanh ({ commit, state }, data) {
    let response = await API.getDSKieuBaoHanh(this._vm.axios, data)
    try {
        let KieuBH_array = [];
        response.data.data.forEach(a=>KieuBH_array.push({'id': a.kieuBHId,'text' : a.kieuBH})) 
        commit('setDanhSachKieuBH', KieuBH_array || [])
    } catch (error) {
      throw error
    }
    return response
  },
  async getDSTinhTrang ({ commit, state }, data) {
    let response = await API.getDSTinhTrang(this._vm.axios, data)
    try {
      commit('setDSTinhTrang', response.data.data.map(a=>a.ttbh) || [])
    } catch (error) {
      throw error
    }
    return response
  },
  async getDSNhomThietBi ({ commit, state }, data) {
    let response = await API.getDSNhomThietBi(this._vm.axios, data)
    try {
      let DSNhomThietBi = [];
      response.data.data.forEach(a=>DSNhomThietBi.push({'id': parseInt(a.nhomTBiId),'text' : a.nhomTBi})) 
      commit('setDSNhomThietBi', DSNhomThietBi || [])
    } catch (error) {
      throw error
    }
    return response
  },
  async getDSLoaiThietBi ({ commit, state }, data) {
    let response = await API.getDSLoaiThietBi(this._vm.axios, data)
    try {
      let DSLoaiThietBi = [];
      response.data.data.forEach(a=>DSLoaiThietBi.push({'id': parseInt(a.LOAITBI_ID),'text' : a.LOAI_TBI})) 
      commit('setDSLoaiThietBi', DSLoaiThietBi || [])
    } catch (error) {
      throw error
    }
    return response
  },
  async getDSVatTu ({ commit, state }, data) {
    let response = await API.getDSVatTu(this._vm.axios, data)
    try {
      let DSVatTu = [];
      response.data.data.forEach(a=>DSVatTu.push({'vatTuId': a.vatTuId,'tenVT' : a.maVT + " - " + a.tenVT})) 
      commit('setDSVatTu', DSVatTu || [])
    } catch (error) {
      throw error
    }
    return response
  },
  async getDSTiepNhanBH ({ commit, state }, data) {
    let response = await API.getDSTiepNhanBH(this._vm.axios, data)
    try {
      // let DSTiepNhanBH = response.data.data || []
      // DSTiepNhanBH.forEach(a=>a.NGAY_TN = moment(a.NGAY_YC,'DD-MM-YYYY hh:mm:ss').format('DD/MM/YYYY'))
      commit('setDSTiepNhanBH', response.data.data || [])
    } catch (error) {
      throw error
    }
    return response
  },
  
  async getDSThamSoMacDinh ({ commit, state }, data) {
    let response = await API.getDSThamSoMacDinh(this._vm.axios, data)
    try {
      commit('setDSThamSoMacDinh', response.data.data || [])
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
  },
  clearLoaiTB ({commit, sate}, data) {
    commit('clearLoaiTB', data)
  },
  clearThongTinThueBao ({commit, sate}, data) {
    commit('clearThongTinThueBao', data)
  },

  async getThongTinBaoHanhTB ({ commit, state }, data) {
    let response = await API.getThongTinBaoHanhTB(this._vm.axios, data)
    try {
      commit('setThongTinBaoHanhTB', response.data.data)
    } catch (error) {
      throw error
    }
    return response
  },
  
  async getMapID ({ commit, state }, data) {
    let response = await API.getMapID(this._vm.axios, data)
    try {
      commit('setMapID', response.data.data)
    } catch (error) {
      throw error
    }
    return response
  },

  async getTinhTrangBHTheoID ({ commit, state }, data) {
    let response = await API.getTinhTrangBHTheoID(this._vm.axios, data)
    try {
      commit('setTinhTrangBHTheoID', response.data.data)
    } catch (error) {
      throw error
    }
    return response
  },
  
  async kiemTraSerialChoTiepNhan ({ commit, state }, data) {
    let response = await API.kiemTraSerialChoTiepNhan(this._vm.axios, data)
    try {
      // commit('setSerialChoTiepNhan', response.data.data || "")
    } catch (error) {
      throw error
    }
    return response
  },

  async kiemTraSerialBH ({ commit, state }, data) {
    let response = await API.kiemTraSerialBH(this._vm.axios, data)
    try {
      // commit('setTinhTrangBHTheoID', response.data.data || "")
    } catch (error) {
      throw error
    }
    return response
  },

  async getKey ({ commit, state }, data) {
    let response = await API.getKey(this._vm.axios, data)
    return response.data.data
  },
  
  async getQuyTrinhBH ({ commit, state }, data) {
    let response = await API.getQuyTrinhBH(this._vm.axios, data)
    try {
      // commit('setTinhTrangBHTheoID', response.data.data || "")
    } catch (error) {
      throw error
    }
    return response
  },

  async getDonViBH ({ commit, state }, data) {
    let response = await API.getDonViBH(this._vm.axios, data)
    return response.data.data
  },

  async getDonViTheoKho ({ commit, state }, data) {
    let response = await API.getDonViTheoKho(this._vm.axios, data)
    return response.data.data
  },

  async getToBHSuaChua ({ commit, state }, data) {
    let response = await API.getToBHSuaChua(this._vm.axios, data)
    return response.data.data
  },

  async updateBaoHanh ({ commit, state }, data) {
    let response = await API.updateBaoHanh(this._vm.axios, data)
    return response
  },

  async insertBaoHanhDV ({ commit, state }, data) {
    let response = await API.insertBaoHanhDV(this._vm.axios, data)
    return response
  },

  async insertDSBaoHanh ({commit, state}, data) {
    let response = await API.insertDSBaoHanh(this._vm.axios, data)
    return response
  },

  async xoaBaoHanhDV ({ commit, state }, data) {
    let response = await API.xoaBaoHanhDV(this._vm.axios, data)
    return response
  },

  async xoaBaoHanhTinhTrang ({ commit, state }, data) {
    let response = await API.xoaBaoHanhTinhTrang(this._vm.axios, data)
    return response
  },

  async insertBaoHanhTinhTrang ({ commit, state }, data) {
    let response = await API.insertBaoHanhTinhTrang(this._vm.axios, data)
    return response
  },

  async giaoPhieuTNBaoHanh ({ commit, state }, data) {
    let response = await API.giaoPhieuTNBaoHanh(this._vm.axios, data)
    return response
  },
  
  async updateBaoHanh ({ commit, state }, data) {
    let response = await API.updateBaoHanh(this._vm.axios, data)
    return response
  },

  async getThongTinVTTheoSerial ({ commit, state }, data) {
    let response = await API.getThongTinVTTheoSerial(this._vm.axios, data)
    return response
  },

  async getHuongGiaoQuyTrinh ({ commit, state }, data) {
    let response = await API.getHuongGiaoQuyTrinh(this._vm.axios, data)
    return response
  },

  
  async getDanhSachHuongGiao ({ commit, state }, data) {
    let response = await API.getDanhSachHuongGiao(this._vm.axios, data)
    return response
  },

  async getDKChoBang ({ commit, state }, data) {
    let response = await API.getDKChoBang(this._vm.axios, data)
    return response
  },

  async updateDSBaoHanh ({ commit, state }, data) {
    let response = await API.updateDSBaoHanh(this._vm.axios, data)
    return response
  },
  async tiepNhanBaoHanh ({ commit, state }, data) {
    let response = await API.tiepNhanBaoHanh(this._vm.axios, data)
    return response.data.data
  },
  async xoaBaoHanh ({ commit, state }, baoHanhId) {
    let response = await API.xoaBaoHanh(this._vm.axios, baoHanhId)
    return response.data.data
  },
}
