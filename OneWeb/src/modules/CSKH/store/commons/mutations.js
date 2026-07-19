export const mutations = {
  setLoaiDSThueBaoKhongKhaoSat(state, data) {
    state.loaiDSThueBaoKhongKhaoSat = data;
  },
  setLoaiDanhMucVT(state, data) {
    state.loaiDanhMucVT = data;
  },
  setCayDonVi(state, data) {
    data = data.sort((a, b) => a.TEN_DV.localeCompare(b.TEN_DV));
    state.cayDonVi = data;
  },
  setDsDichVu(state, data) {
    state.dsDichVu = data;
  }
};
