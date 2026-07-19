// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
  setDanhSachBienNhan (state, data) {
    state.danhsachBienNhan = data
  },
  setDanhSachHoaDonChuyenKhoan (state, data) {
    state.danhsachHoaDonChuyenKhoan = data
  },
  setObjSearch (state, data) {
    state.objSearch = data
  },
  clearData (state) {
    state.danhsachBienNhan = []
    state.danhsachHoaDonChuyenKhoan = []
    state.objSearch = {}
  }
}
