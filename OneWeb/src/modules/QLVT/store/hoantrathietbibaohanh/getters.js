import {searchArray} from '../../../../utils/util'
export const getters = {
  danhsachNhanVienGetter: (state, getters, rootState) => {
    return searchArray(state.danhSachNhanVien, state.objSearch)
  },
  // danhsachPhieuBHGetter: (state, getters, rootState) => {
  //   return searchArray(state.danhSachPhieuBH, state.objSearch)
  // },
  getPageDSPhieuBHGetter: (state, getters, rootState) => {
    return state.pageDSPhieuBH
  },
  totalItemsPhieuBHGetter: (state, getters, rootState) => {
    return state.pageInfoDSPhieuBH.totalItems
  }
}
