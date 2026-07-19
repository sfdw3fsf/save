export const mutations = {
  setDanhSachDonVi(state, data) {
    state.danhsachDonViQL = data;
  },
  setDanhSachQuyTrinh(state, data) {
    state.danhsachQuyTrinh = data;
  },
  setDanhSachLoaiPhieu(state, data) {
    state.danhsachLoaiPhieu = data;
  },
  setDanhSachKhoaThueBao(state, data) {
    state.danhsachKhoaThueBao = data;
  },
  clearData(state) {
    state.danhsachDonViQL = [];
    state.danhsachQuyTrinh = [];
    state.danhsachLoaiPhieu = [];
    state.danhsachKhoaThueBao = [];
  }
};
