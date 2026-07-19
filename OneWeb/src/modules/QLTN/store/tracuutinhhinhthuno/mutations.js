export const mutations = {
  // setDanhSachNVThuCuoc(state, data) {
  //   state.danhSachTinhHinhThuNo = data
  // },
  setTyLeThuNVThuCuoc(state, data) {
    state.tyLeThuNVThuCuoc = data
  },
  setDanhSachTinhCuocTN(state, data) {
    state.danhSachTinhCuocTN = data
  },
  setObjSearch (state, data) {
    state.objSearch = data
  },
  clearData (state, data) {
    state.tyLeThuNVThuCuoc = []
    state.danhSachTinhCuocTN = []
    state.objSearch = {}
  }
}
