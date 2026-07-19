// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
  setListDonVi(state, data) {
    state.listDonVi = data;
  },
  setListHTTT(state, data) {
    state.listHTTT = data;
  },
  setDanhSachLoaiKH(state, data) {
    state.listLoaiKH = data;
  },
  setListNhomKH(state, data) {
    state.listNhomKH = data;
  },
  setListNVQuanLy(state, data) {
    state.listNVQuanLy = data;
  },
  setListNVThuCuoc(state, data) {
    state.listNVThuCuoc = data;
  },
  setNhomCongNo(state, data) {
    state.listNhomCongNo = data;
  },
  setKyHoaDon(state, data) {
    state.listKyHoaDon = data;
  },
  setListCongNo(state, data) {
    state.listCongNo = data;
  },

  clearData(state, data) {
    state.listDonVi = [];
    state.listLoaiKH = [];
    state.listHTTT = [];
    state.listNhomKH = [];
    state.listNVQuanLy = [];
    state.listNVThuCuoc = [];
    state.listNhomCongNo = [];
    state.listKyHoaDon = [];
    state.listCongNo = [];
  }
};
