import api from "@/modules/admin/category/ClassifyService/ClassifyServiceAPI";

export const actions = {
  async getPhanLoaiDichVu({ commit, state }) {
    let response = await api.getPhanLoaiDichVu(this._vm.axios);
    try {
      commit("setPhanLoaiDichVu", response.data);
      return response.data.data;
    } catch (error) {
      throw error;
    }
  }
};
