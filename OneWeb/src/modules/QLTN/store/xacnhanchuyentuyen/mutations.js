export const mutations = {
  setDanhSachNhanVienThuCuoc(state, data) {
    state.danhSachNhanVienThuCuoc = data
  },
  setDanhSachChuyenTuyen(state, data) {
    state.danhSachChuyenTuyen = data;
  },
  clearData(state) {
    state.danhSachNhanVienThuCuoc = [];
    state.danhSachChuyenTuyen = []
  },
}
