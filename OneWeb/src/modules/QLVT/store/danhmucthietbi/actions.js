import API from "../../api/DanhMucThietBi";

export const actions = {
  async getDSNhomTB({ commit, state }) {
    let response = await API.getDSNhomTB(this._vm.axios);
    try {
      commit("setDSNhomTB", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getDSDVT({ commit, state }) {
    let response = await API.getDSDVT(this._vm.axios);
    try {
      commit("setDSDVT", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getDSLoaiPhi({ commit, state }) {
    let response = await API.getDSLoaiPhi(this._vm.axios);
    try {
      commit("setDSLoaiPhi", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getDSTyLeVat({ commit, state }) {
    let response = await API.getDSTyLeVat(this._vm.axios);
    try {
      commit("setDSTyLeVat", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getDSDichVuVT({ commit, state }, data) {
    let response = await API.getDSDichVuVT(this._vm.axios, data.nguoiDungId);
    try {
      commit("setDSDichVuVT", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getDSDanhMucLoaiThietBi({ commit, state }) {
    let response = await API.getDSDanhMucLoaiThietBi(this._vm.axios);
    try {
      commit("setDSLoaiThietBi", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getDSLoaiHinhTB({ commit, state }, data) {
    let response = await API.getDSLoaiHinhTB(
      this._vm.axios,
      data.dichVuVtId,
      data.loaiTbiId,
      data.chk
    );
    try {
      commit("setDSLoaiHinhTB", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async createDSLTB({ commit, state }, data) {
    let response = await API.createDSLTB(this._vm.axios, data);
    try {
      commit("setCreateDSLTB", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async createLTBLoaiHinhTB({ commit, state }, data) {
    let response = await API.createLTBLoaiHinhTB(
      this._vm.axios,
      data.loaiTbiId,
      data.loaiTbId
    );
    try {
      commit("setCreateLTBLoaiHinhTB", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async deleteLTBLoaiHinhTB({ commit, state }, data) {
    let response = await API.createLTBLoaiHinhTB(
      this._vm.axios,
      data.loaiTbiId
    );
    try {
      commit("deleteLTBLoaiHinhTB", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async deleteLTB({ commit, state }, data) {
    let response = await API.deleteLTB(
      this._vm.axios,
      data.loaiTbiId
    );
    try {
      commit("deleteLTB", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async updateDSLTB({ commit, state }, data) {
    let response = await API.updateDSLTB(this._vm.axios, data);
    try {
      commit("setUpdateDSLTB", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  }
};
