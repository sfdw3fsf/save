// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
  GET_DANH_SACH_KHUYEN_MAI (state, data) {
    state.danhSachKhuyenMai = data
    if (data.pageInfo && data.pageInfo.record_count > 0) {
      state.totalRow = data.pageInfo.record_count
    }
  },
  SET_DATA_ITEM (state, data) {
    state.dataItem = data
  },
  SET_SELECT_NE (state, data) {
    state.selectNe = data
  },
  SET_DON_VI_ID (state, data) {
    state.donViID = data
  },
  SET_TOTA_ROW (state, data) {
    state.totalRow = data
  },
  SET_IS_LOADING (state, data) {
    state.isLoading = data
  }
}
