import {searchArray} from '../../../../utils/util'
export const getters = {
    getDsThueBaoKhongKhoaMoGetter: (state, getters, rootState) => searchArray(state.dsThueBaoKhongKhoaMo, state.objSearch),
    getDsThongTinThueBaoNoGetter: (state, getters, rootState) => searchArray(state.dsThongTinThueBaoNo, state.objSearch),
    getPageInfoGetter: (state, getters, rootState) => state.pageInfo,
    getPageInfoDsThongTinThueBaoNoGetter: (state, getters, rootState) => state.pageInfoDsThongTinThueBaoNo,
}