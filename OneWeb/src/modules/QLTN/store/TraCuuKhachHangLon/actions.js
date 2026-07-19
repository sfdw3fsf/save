import API from "../../api/TraCuuKhachHangLonApi";

export const actions = {
  async traCuuKHThanhToan({ commit, state }, data) {
    try {
      let response = await API.traCuuThanhToan(this._vm.axios, data);
      console.log("dsssssssssssssss", response.data.data.data);
      if (Array.isArray(response.data.data)) {
        commit("setDanhSachThanhToan", response.data.data);
      } else {
        commit("setDanhSachThanhToan", response.data.data);
      }
      return response;
    } catch (error) {
      console.log("error set ds thanh toan", error);

      commit("setDanhSachThanhToan", []);
      return error.response.data;
    }
  },

  async getDSThanhToanTheoMaIG({ commit, state }, data) {
    try {
      let response = await API.getDSThanhToanTheoMaIG(this._vm.axios, data);
      console.log("dsssssssssssssss", response.data.data);
      if (Array.isArray(response.data.data)) {
        commit("setDanhSachThanhToan", response.data.data);
      } else {
        commit("setDanhSachThanhToan", response.data.data.data);
      }
      return response;
    } catch (error) {
      console.log("error set ds thanh toan", error);
      commit("setDanhSachThanhToan", []);
      return error.response.data;
    }
  },

  async clearData({ commit, state }, data) {
    commit("clearData", data);
  },

  async getDanhSachLienHe({ commit, state }, data) {
    try {
      let response = await API.traCuuLienHe(this._vm.axios, data);
      console.log("ds lienhe", response);
      commit("setDanhSachLienHe", response.data.data.data || []);
      return response;
    } catch (error) {
      console.log("error", error);
      //   this._vm.$toast.error(error.response.data.message);
      commit("setDanhSachLienHe", []);
      return error.response.data;
    }
  }
};
