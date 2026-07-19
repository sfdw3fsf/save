// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
  GET_DANH_SACH_VAI_TRO (state, data) {
    state.danhSachVaiTro = data
  },
  SET_IS_LOADING (state, data) {
    state.isLoading = data
  },
  SET_DATA_ITEM (state, data) {
    state.dataItem = data
  }
}
