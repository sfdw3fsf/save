import {searchArray} from '../../../../utils/util'
export const getters = {
  danhsachTongNoGetter: (state, getters, rootState) => {
    return searchArray(state.danhsachTongNoKH, state.objSearch)
  },
  danhsachTongTraKHGetter: (state, getters, rootState) => {
    return searchArray(state.danhsachTongTraKH, state.objSearch)
  }
}
