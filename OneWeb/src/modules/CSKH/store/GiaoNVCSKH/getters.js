import {searchArray} from '../../../../utils/util'
export const getters = {
  dsDonViGetter: (state, getters, rootState) => {
    return state.dsDonVi
  },
  dsChuongTrinhGetter: (state, getters, rootState) => {
    return state.dsChuongTrinh
  },
  dsDaGanGetter: (state, getters, rootState) => {
    return state.dsDaGanChuaGan
  },
  dsChuaGanGetter: (state, getters, rootState) => {
    return state.dsDaGanChuaGan
  },
}
