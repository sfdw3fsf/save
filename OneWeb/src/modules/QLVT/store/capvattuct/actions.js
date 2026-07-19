import API from "../../api/CapVatTuCT";

export const actions = {
  async getNhomCT({ commit, state }) {
    let response = await API.getNhomCT(this._vm.axios);
    try {
      commit("setNhomCT", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getLoaiTBi({ commit, state }) {
    let response = await API.getLoaiTBi(this._vm.axios);
    try {
      commit("setLoaiTBi", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getLoaiCT({ commit, state }) {
    let response = await API.getLoaiCT(this._vm.axios);
    try {
      commit("setLoaiCT", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getKhofrmCVTCT({ commit, state }, data) {
    let response = await API.getKhofrmCVTCT(this._vm.axios, data.nhanVienId);
    try {
      commit("setKhofrmCVTCT", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getDanhMucVT_DVT({ commit, state }) {
    let response = await API.getDanhMucVT_DVT(this._vm.axios);
    try {
      commit("setDanhMucVT_DVT", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getKieuThietBi({ commit, state }) {
    let response = await API.getKieuThietBi(this._vm.axios);
    try {
      commit("setKieuThietBi", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getTinhTrang({ commit, state }) {
    let response = await API.getTinhTrang(this._vm.axios);
    try {
      commit("setTinhTrang", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getDSCongTrinhCapVT({ commit, state }, data) {
    let response = await API.getDSCongTrinhCapVT(this._vm.axios, data);
    return response.data.data;
  },
  async getChungLoaiTB({ commit, state }) {
    let response = await API.getChungLoaiTB(this._vm.axios);
    try {
      commit("setChungLoaiTB", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getds_LSVT({ commit, state }, data) {
    let response = await API.getds_LSVT(this._vm.axios, data);
    try {
      commit("setds_LSVT", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getDSVatTuTrongKho_CT({ commit, state }, data) {
    let response = await API.getDSVatTuTrongKho_CT(this._vm.axios, data);
    try {
      commit("setDSVatTuTrongKho_CT", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getDSVatTuSD_CT({ commit, state }, data) {
    let response = await API.getDSVatTuSD_CT(this._vm.axios, data.congTrinhId);
    try {
      commit("setDSVatTuSD_CT", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async ins_vattu_congtrinh_v2({ commit, state }, data) {
    let response = await API.ins_vattu_congtrinh_v2(this._vm.axios, data);
    try {
      commit("set_ins_vattu_congtrinh_v2", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async kiemtra_vt_thuhoi({ commit, state }, data) {
    let response = await API.kiemtra_vt_thuhoi(this._vm.axios, data.hdTbId, data.baoHongId, data.serial);
    try {
      commit("set_kiemtra_vt_thuhoi", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getThongTinThueBaoTheoSerial({ commit, state }, data) {
    let response = await API.getThongTinThueBaoTheoSerial(this._vm.axios, data.serial);
    try {
      commit("setThongTinThueBaoTheoSerial", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async KiemTraTB_SDVT({ commit, state }, data) {
    let response = await API.KiemTraTB_SDVT(this._vm.axios, data.serial);
    try {
      commit("set_KiemTraTB_SDVT", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getThietBi_DaCap({ commit, state }, data) {
    let response = await API.getThietBi_DaCap(this._vm.axios, data.serial);
    try {
      commit("setThietBi_DaCap", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async capnhat_sdvt_th({ commit, state }, data) {
    let response = await API.capnhat_sdvt_th(this._vm.axios, data);
    try {
      commit("set_capnhat_sdvt_th", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getSL_DD({ commit, state }, data) {
    let response = await API.getSL_DD(this._vm.axios, data.sdVtId);
    try {
      commit("setSL_DD", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async CAPNHAT_PHIEUVT_THUHOI({ commit, state }, data) {
    let response = await API.CAPNHAT_PHIEUVT_THUHOI(this._vm.axios, data);
    try {
      commit("set_CAPNHAT_PHIEUVT_THUHOI", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getSL_HT({ commit, state }, data) {
    let response = await API.getSL_HT(this._vm.axios, data.sdVtId);
    try {
      commit("setSL_HT", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async kiemtra_serial({ commit, state }, data) {
    let response = await API.kiemtra_serial(this._vm.axios, data)
    return response.data
  },
  async kiemtra_serial_file({ commit, state }, data) {
    let response = await API.kiemtra_serial_file(this._vm.axios, data)
    return response.data
  },
  async kiemtra_serial_thuhoi({ commit, state }, data) {
    let response = await API.kiemtra_serial_thuhoi(this._vm.axios, data.id, data.dt)
    return response.data
  },
  async LAY_TT_VATTU_THEO_SERIAL({ commit, state }, data) {
    let response = await API.LAY_TT_VATTU_THEO_SERIAL(this._vm.axios, data)
    return response.data
  },
  async LAY_ID_VATTU_THEO_MA_VT({ commit, state }, data) {
    let response = await API.LAY_ID_VATTU_THEO_MA_VT(this._vm.axios, data)
    return response.data
  },
  async thuHoiVatTuCongTrinh({ commit, state }, data) {
    let response = await API.thuHoiVatTuCongTrinh(this._vm.axios, data)
    return response.data.data
  },
  async GiaoPhieuCongTrinh({ commit, state }, data) {
    let response = await API.GiaoPhieuCongTrinh(this._vm.axios, data)
    return response.data.data
  },
  async HuyPhieuCongTrinh({ commit, state }, data) {
    let response = await API.HuyPhieuCongTrinh(this._vm.axios, data)
    return response.data.data
  },
  async XoaPhieuVatTu({ commit, state }, data) {
    let response = await API.XoaPhieuVatTu(this._vm.axios, data)
    return response.data.data
  },
  async ThuHoiVatTu({ commit, state }, data) {
    let response = await API.ThuHoiVatTu(this._vm.axios, data)
    return response.data.data
  },
  async getTinhTrangThuHoi({ commit, state }, data) {
    let response = await API.getTinhTrangThuHoi(this._vm.axios, data)
    return response.data.data
  },
};
