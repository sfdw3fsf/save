import {searchArray} from '../../../../utils/util'
export const getters = {
  danhsachBienNhanGetter: (state, getters, rootState) => {
    return searchArray(state.danhsachBienNhan, state.objSearch)
  },
  danhsachHoaDonChuyenKhoanGetter: (state, getters, rootState) => {
    return searchArray(state.danhsachHoaDonChuyenKhoan, state.objSearch)
  }
}
