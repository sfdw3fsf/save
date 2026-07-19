// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
// danhSachHuongDamThoai: [],
  // danhSachDichVuVT: [],
  // danhSachLoaiHinhTB: []

  setDanhSachHuongDamThoai(state, data) {
    state.danhSachHuongDamThoai = data;
  },

  setDanhSachDichVuVT(state, data) {
    state.danhSachDichVuVT = data;
  },

  setDanhSachLoaiHinhTB(state, data) {
    state.danhSachLoaiHinhTB = data;
  },

  setDanhSachCuocGoi(state, data) {
    state.danhSachCuocGoi = data;
  },
};
