export const mutations = {
  setDanhSachTuyenThu (state, data) {
    state.danhsachTuyenThu = data
  },
  setDanhSachDotGiao (state, data) {
    state.danhsachDotGiao = data
  },
  setDanhSachQuan (state, data) {
    state.danhsachQuan = data
  },
  setDanhSachPhuong (state, data) {
    state.danhsachPhuong = data
  },
  setDanhSachPho (state, data) {
    state.danhsachPho = data
  },
  setDanhSachAp (state, data) {
    state.danhsachAp = data
  },
  setDanhSachPhanChiaTuyenThu (state, data) {
    state.danhsachPhanChiaTuyenThu = data
  },
  setDanhSachChuaPhanTuyen (state, data) {
    state.danhsachChuaPhanTuyen = data
  },
  clearData (state) {
    state.danhsachPhanChiaTuyenThu = []
    state.danhsachChuaPhanTuyen = []
  }
}
