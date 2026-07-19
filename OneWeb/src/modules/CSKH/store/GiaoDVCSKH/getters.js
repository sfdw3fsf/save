import {searchArray} from '../../../../utils/util'
export const getters = {
  dsLoaiDonViGetter: (state, getters, rootState) => {
    return state.dsLoaiDonVi
  },
  dsChuongTrinhGetter: (state, getters, rootState) => {
    return state.dsChuongTrinh
  },
  dsDaGanGetter: (state, getters, rootState) => {
    return state.dsDaGan
  },
  dsChuaGanGetter: (state, getters, rootState) => {
    return state.dsChuaGan
  },
}
