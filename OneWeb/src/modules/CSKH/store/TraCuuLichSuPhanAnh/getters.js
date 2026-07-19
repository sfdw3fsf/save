import {searchArray} from '../../../../utils/util'
export const getters = {
  dsLichSuPhanAnhGetter: (state, getters, rootState) => {
    return searchArray(state.dsLichSuPhanAnh, state.objSearch)
  },
  totalPageGetter: (state, getters, rootState) => {
    return state.totalPage
  },
  pageSizeGetter: (state, getters, rootState) => {
    return state.pageSize
  },
  totalItemsGetter: (state, getters, rootState) => {
    return state.totalItems
  }
}
