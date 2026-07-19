// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
// danhSachHuongDamThoai: [],
  // danhSachDichVuVT: [],
  // danhSachLoaiHinhTB: []



  setDanhSachDichVuVT(state, data) {
    state.danhSachDichVuVT = data;
  },

  setDanhSachLoaiHinhTB(state, data) {
    state.danhSachLoaiHinhTB = data;
  },

  setThongTinThanhToan(state, data) {
    state.thongTinThanhToan = data;
  },

  setDanhSachKhieuNaiTB(state, data) {
    state.danhSachKhieuNaiTB = data;
  },

  setDanhSachNVQLDaiLy(state, data) {
    state.danhSachNVQLDaiLy = data;
  },
};
