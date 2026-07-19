// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
  setListDonVi(state, data) {
    state.listDonVi = data;
  },
  setListDichVuVT(state, data) {
    state.listDichVuVT = data;
  },
  setListLoaiHinhTB(state, data) {
    state.listLoaiHinh = data;
  },
  setTrungTamVT(state, data) {
    state.listTrungTamVT = data;
  },
  setDanhSachLoaiKH(state, data) {
    state.listLoaiKH = data;
  },
  setDanhSachKHLon(state, data) {
    state.listKHLon = data;
  },
  setDanhSachThanhLyThueBao(state, data) {
    state.listThanhLyThueBao = data;
  },
  setDMKieuTamDung(state, data) {
    state.listKieuTamDung = data;
  },
  clearData(state, data) {
    state.listDonVi = [];
    state.listDichVuVT = [];
    state.listLoaiHinh = [];
    state.listTrungTamVT = [];
    state.listLoaiKH = [];
    state.listKHLon = [];
    state.listThanhLyThueBao = [];
    state.listKieuTamDung = [];
  }
};
