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

  danhSachCuocGoiGetter: (state, getters, rootState) => {
    return state.danhSachCuocGoi
  },
};
