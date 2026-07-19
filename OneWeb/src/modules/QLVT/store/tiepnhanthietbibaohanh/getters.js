import {searchArray} from '../../../../utils/util'
export const getters = {
  danhsachDonViGetter: (state, getters, rootState) => {
    return searchArray(state.danhSachDonVi, state.objSearch)
  },
 
}
