export const mutations = {
  setDanhSachXuLyNo (state, data) {
    state.danhsachXuLyNo = data
  },
  clearData (state) {
    state.danhsachXuLyNo = []
    state.objSearch = {}
  }
}
