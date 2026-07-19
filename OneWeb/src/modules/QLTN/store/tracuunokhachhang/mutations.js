export const mutations = {
  setDanhSachThongTinNo (state, data) {
    state.danhsachThongTinNo = data
  },
  setObjSearch (state, data) {
    state.objSearch = data
  },
  clearData (state) {
    state.danhsachThongTinNo = []
    state.objSearch = {}
  }
}
