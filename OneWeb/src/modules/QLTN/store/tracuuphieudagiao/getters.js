export const getters = {
  dsNhanVienTheoLoaiGetter: (state, getters, rootState) => {
    return state.dsNhanVienTheoLoai
  },
  BuocXuLyNoGetter: (state, getters, rootState) => {
    return state.formData.p_buocxyly_no
  },
  formDataGetter: (state, getter, rootState) => {
    return state.formData
  },
  formResultGetter: (state, getter, rootState) => {
    return state.formResult
  },
  getKyHoaDon: (state, getters, rootState, rootGetters) => {
    return rootState.qltn.qltnCommon.thongtinKyHoaDon
  },
  pageInfoGetter: (state, getters, rootState) => {
    return state.pageInfo
  }
}
