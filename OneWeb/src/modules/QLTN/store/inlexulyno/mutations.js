export const mutations = {
  setDanhSachKyHoaDon (state, data) {
    state.danhsachKyHoaDon = data
  },
  clearData (state) {
    state.danhsachKyHoaDon = []
    state.danhsachXuLyNo = []
  },
  setDanhSachXuLyNo (state, data) {
    state.danhsachXuLyNo = data
  },
}
