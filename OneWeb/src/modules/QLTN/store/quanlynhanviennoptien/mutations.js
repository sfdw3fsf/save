export const mutations = {
  setNhanVienNopTien(state, data) {
    state.nhanVienNopTien = data;
  },
  setDanhSachDonVi(state, data) {
    state.danhSachDonVi = data;
  },
  setNhomHinhThuc(state, data) {
    state.nhomHinhThuc = data;
  },
  setHinhThucThanhToan(state, data) {
    state.hinhThucThanhToan = data;
  },
  setKieuNop(state, data) {
    state.kieuNop = data;
  },
  setDanhSachThuNgan(state, data) {
    state.danhSachThuNgan = data;
  },
  setDanhSachNopTien(state, data) {
    state.danhSachNopTien = data;
  },
  setDanhSachDaiLyNopNH(state, data) {
    state.danhSachDaiLyNopNH = data;
  },
  addNhanVienNopTienNo(state, data) {
    state.nhanVienNopTien = data;
  },
  clearData(state) {
    state.nhanVienNopTien = [];
    state.danhSachNopTien = [];
    state.danhSachDaiLyNopNH = [];
    state.objSearch = {};
  }
};
