import API from "../../api/QuanLyNhanVienNopTien";

export const actions = {
  async getNhanVienNopTien({ commit, state }, data) {
    let response = await API.getNhanVienNopTien(this._vm.axios, data);
    try {
      commit("setNhanVienNopTien", response.data.data.data || []);
    } catch (error) {
      commit("setNhanVienNopTien", []);
      throw error;
    }
    return response;
  },

  async getDanhSachDonViNhanVien({ commit, state }, data) {
    let response = await API.getDanhSachDonViNhanVien(this._vm.axios, data);
    try {
      commit("setDanhSachDonVi", response.data.data || []);
    } catch (error) {
      commit("setDanhSachDonVi", []);
      throw error;
    }
    return response;
  },

  async getNhomHinhThuc({ commit, state }, data) {
    let response = await API.getNhomHinhThuc(this._vm.axios, data);
    try {
      commit("setNhomHinhThuc", response.data.data || []);
    } catch (error) {
      commit("setNhomHinhThuc", []);
      throw error;
    }
    return response;
  },

  async getHinhThucThanhToan({ commit, state }, data) {
    let response = await API.getHinhThucThanhToan(this._vm.axios, data);
    try {
      commit("setHinhThucThanhToan", response.data.data || []);
    } catch (error) {
      commit("setHinhThucThanhToan", []);
      throw error;
    }
    return response;
  },

  async getKieuNop({ commit, state }, data) {
    let response = await API.getKieuNop(this._vm.axios, data);
    try {
      commit("setKieuNop", response.data.data || []);
    } catch (error) {
      commit("setKieuNop", []);
      throw error;
    }
    return response;
  },

  async getDanhSachThuNgan({ commit, state }, data) {
    let response = await API.getDanhSachThuNgan(this._vm.axios, data);
    try {
      commit("setDanhSachThuNgan", response.data.data || []);
    } catch (error) {
      commit("setDanhSachThuNgan", []);
      throw error;
    }
    return response;
  },

  async getDanhSachNopTien({ commit, state }, data) {
    try {
      let response = await API.getDanhSachNopTien(this._vm.axios, data);
      commit("setDanhSachNopTien", response.data.data || []);
      return response;
    } catch (error) {
      commit("setDanhSachNopTien", []);
      console.log("error", error.response.data);
      return error.response.data;
    }
  },

  async getDanhSachDaiLyNopNH({ commit, state }, data) {
    try {
      let response = await API.getDanhSachDaiLyNopNH(this._vm.axios, data);
      commit("setDanhSachDaiLyNopNH", response.data.data || []);
      return response;
    } catch (error) {
      commit("setDanhSachDaiLyNopNH", []);
      return error.response.data;
      //   throw error;
    }
  },

  async addNhanVienNopTien({ commit, state }, data) {
    try {
      let response = await API.addNhanVienNopTien(this._vm.axios, data);
      return response;
    } catch (error) {
      return error.response.data;
    }
  },

  async updateNhanVienNopTien({ commit, state }, data) {
    try {
      let response = await API.updateNhanVienNopTien(this._vm.axios, data);
      return response;
    } catch (error) {
      return error.response.data;
    }
  },

  async deleteLanNopTien({ commit, state }, data) {
    try {
      let response = await API.deleteLanNopTien(this._vm.axios, data);
      return response;
    } catch (error) {
      console.log("error", error.response.data);
      return error.response.data;
    }
  },

  clearData({ commit, sate }) {
    commit("clearData");
  }
};
