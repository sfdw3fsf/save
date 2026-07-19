// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
  setDanhSachTongNoKH (state, data) {
    state.danhsachTongNoKH = data
  },
  setDanhSachTongTraKH (state, data) {
    state.danhsachTongTraKH = data
  },
  setObjSearch (state, data) {
    state.objSearch = data
  },
  setThongTinTT (state, data) {
    state.thongTinTT = data
  },
  clearData (state, data) {
    state.danhsachTongNoKH = []
    state.danhsachTongTraKH = []
    state.objSearch = {}
    state.thongTinTT = null
  }
}
