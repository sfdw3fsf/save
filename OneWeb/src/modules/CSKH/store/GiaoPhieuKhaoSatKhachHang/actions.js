import API from "@/modules/CSKH/api/GiaoPhieuKhaoSatKhachHangApi";

export const actions = {
  async clearData({ commit, state }) {
    commit("clearData");
  },
  async clearGridData({ commit, state }) {
    commit("clearGridData");
  },
  async getDsLoaiHopDong({ commit, state }, data) {
    try {
      //@fakedata
      let response = await API.getDsLoaiHopDong(this._vm.axios, data);
      let ds = response.data.data || [];
      ds.forEach((element, index) => (element.STT = index + 1));
      commit("setDsLoaiHopDong", ds);
      return response;
    } catch (error) {
      commit("setDsLoaiHopDong", []);
      return error.response.data;
    }
  },
  async getDsThongTinGiaoKhaoSat({ commit, state }, data) {
    try {
      //@fakedata
      let response = await API.getDsThongTinGiaoKhaoSat(this._vm.axios, data);
      let ds = response.data.data.data || [];
      ds.forEach((element, index) => (element.STT = index + 1));
      commit("setDsThongTinGiaoKhaoSat", ds);
      return response;
    } catch (error) {
      commit("setDsThongTinGiaoKhaoSat", []);
      return error.response.data;
    }
  },
  async getDsDichVu({ commit, state }, data) {
    try {
      let response = await API.getDsDichVu(this._vm.axios, data);
      let ds = response.data.data || [];
      commit("setDsDichVu", ds);
      return response;
    } catch (error) {
      commit("setDsDichVu", []);
      return error.response.data;
    }
  },
  async getDsNhanVienGiao({ commit, state }, data) {
    try {
      let response = await API.getDsNhanVienGiao(this._vm.axios, data);
      let ds = response.data.data || [];
      commit("setDsNhanVienGiao", ds);
      return response;
    } catch (error) {
      commit("setDsNhanVienGiao", []);
      return error.response.data;
    }
  },
  async getDsNhanVienNhan({ commit, state }, data) {
    try {
      let response = await API.getDsNhanVienNhan(this._vm.axios, data);
      let ds = response.data.data || [];
      ds.forEach(e => e.TEN_HT = e.MA_NV + ' - ' + e.TEN_NV )
      commit("setDsNhanVienNhan", ds);
      return response;
    } catch (error) {
      commit("setDsNhanVienNhan", []);
      return error.response.data;
    }
  },
  async getDsLuotGiaoKhaoSat({ commit, state }, data) {
    try {
      //@fakedata
      let response = await API.getDsLuotGiaoKhaoSat(this._vm.axios, data);

      let ds = response.data.data.data || [];
      ds.forEach((element, index) => (element.STT = index + 1));
      commit("setDsLuotGiaoKhaoSat", ds);
      if(ds.length <= 0){
        commit("setDsPhieuDaGiaoKhaoSat", []);
      }
      return response;
    } catch (error) {
      commit("setDsLuotGiaoKhaoSat", []);
      return error.response.data;
    }
  },
  async getDsPhieuDaGiaoKhaoSat({ commit, state }, data) {
    try {
      let response = await API.getDsPhieuDaGiaoKhaoSat(this._vm.axios, data);
      let ds = response.data.data.data || [];
      ds.forEach((element, index) => (element.STT = index + 1));
      commit("setDsPhieuDaGiaoKhaoSat", ds);
      return response;
    } catch (error) {
      commit("setDsPhieuDaGiaoKhaoSat", []);
      return error.response.data;
    }
  }
};
