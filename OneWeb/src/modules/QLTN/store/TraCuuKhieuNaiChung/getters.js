export const getters = {
  // danhSachHuongDamThoai: [],
  // danhSachDichVuVT: [],
  // danhSachLoaiHinhTB: []
  danhSachHuongDamThoaiGetter: (state, getters, rootState) => {
    return state.danhSachHuongDamThoai
  },

  danhSachDichVuVTGetter: (state, getters, rootState) => {
    return state.danhSachDichVuVT
  },

  danhSachLoaiHinhTBGetter: (state, getters, rootState) => {
    return state.danhSachLoaiHinhTB
  },

  danhSachKhieuNaiTBGetter: (state, getters, rootState) => {
    return state.danhSachCuocGoi
  },
  danhSachNVQLDaiLyGetter: (state, getters, rootState) => {
    return state.danhSachCuocGoi
  },

  thongTinThanhToanGetter: (state, getters, rootState) => {
    return state.thongTinThanhToan
  },
};
