// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
  setDanhSachHoaDonThanhToan (state, data) {
    state.danhsachHoaDonThanhToan = data
  },
  setObjSearch (state, data) {
    state.objSearch = data
  },
  setThongTinTT (state, data) {
    state.thongTinTT = data
  },
  clearData (state, data) {
    state.danhsachHoaDonThanhToan = []
    state.objSearch = {}
    state.thongTinTT = null
  }
}
