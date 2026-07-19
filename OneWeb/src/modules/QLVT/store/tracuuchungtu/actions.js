import API from "../../api/TraCuuChungTu";

export const actions = {
  async getListLoaiCT({ commit, state }) {
    let response = await API.getListLoaiCT(this._vm.axios);
    try {
      commit("setListLoaiCT", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getListMucDich({ commit, state }) {
    let response = await API.getListMucDich(this._vm.axios);
    try {
      commit("setListMucDich", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getListTrangThaiCT({ commit, state }) {
    let response = await API.getListTrangThaiCT(this._vm.axios);
    try {
      commit("setListTrangThaiCT", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getListDonViDK({ commit, state }) {
    let response = await API.getListDonViDK(this._vm.axios);
    try {
      commit("setListDonViDK", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getListVatTuDK({ commit, state }) {
    let response = await API.getListVatTuDK(this._vm.axios);
    try {
      commit("setListVatTuDK", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getListDonVi({ commit, state }) {
    let response = await API.getListDonVi(this._vm.axios);
    try {
      commit("setListDonVi", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getListKhoDonVi({ commit, state }, data) {
    let response = await API.getListKhoDonVi(
      this._vm.axios,
      data.nhanVienId,
      data.donViId
    );
    try {
      commit("setListKhoDonVi", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getListTuKho({ commit, state }) {
    let response = await API.getListTuKho(this._vm.axios);
    try {
      commit("setListTuKho", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getKieuPhieu({ commit, state }, data) {
    let response = await API.getKieuPhieu(this._vm.axios, data.loaiCtId);
    try {
      commit("setKieuPhieu", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getTraCuuChungTu({ commit, state }, data) {
    let response = await API.getTraCuuChungTu(this._vm.axios, data);
    try {
      commit("setTraCuuChungTu", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getChungTuVatTu({ commit, state }, data) {
    let response = await API.getChungTuVatTu(this._vm.axios, data.chungTuId);
    try {
      commit("setChungTuVatTu", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getChungTuThietBi({ commit, state }, data) {
    let response = await API.getChungTuThietBi(this._vm.axios, data.chungTuId);
    try {
      commit("setChungTuThietBi", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async tra_cuu_chung_tu_da_xoa({ commit, state }, data) {
    let response = await API.tra_cuu_chung_tu_da_xoa(this._vm.axios, data.soPhieu, data.kieu);
    try {
      commit("set_tra_cuu_chung_tu_da_xoa", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
};
