export const mutations = {
  setDanhSachNV (state, data) {
    state.danhsachNhanVien = data
  },
  setDanhSachNVDungHuong (state, data) {
    state.danhsachNhanVienDungHuong = data
  },
  setDanhSachHoaDon (state, data) {
    state.danhsachHoaDon = data
  },
  clearData (state) {
    state.danhsachNhanVien = []
    state.danhsachNhanVienDungHuong = []
    state.danhsachHoaDon = []
  },
}
