// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
  GET_DANH_SACH_SO_NE (state, data) {
    state.danhSachSoNe = data
    state.itemNe = data && data.length > 0 ? data[0] : {}
  },
  SET_ITEM_NE (state, data) {
    state.itemNe = data
  },
  SET_SELECT_NE (state, data) {
    state.selectNe = data
  },
  SET_DON_VI_ID (state, data) {
    state.donViID = data
  },

  SET_IS_LOADING (state, data) {
    state.isLoading = data
  }
}
