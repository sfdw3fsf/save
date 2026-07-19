import {searchArray} from '../../../../utils/util'
export const getters = {
  danhsachHoaDonThanhToanGetter: (state, getters, rootState) => {
    return searchArray(state.danhsachHoaDonThanhToan, state.objSearch)
  },
}
