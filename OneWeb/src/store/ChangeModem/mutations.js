// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
  GET_DANH_SACH_DICH_VU (state, data) {
    state.danhSachDichVu = data
  },
  GET_DANH_SACH_YEU_CAU (state, data) {
    state.danhSachYeuCau = data
  },
  GET_HOP_DONG_THEO_TB (state, data) {
    state.hopDongTheoTB = data
  },
  GET_DANH_SACH_LOAI_THIET_BI (state, data) {
    state.danhSachLoaiThietBi = data
  },
  GET_DANH_THAY_THE_VT (state, data) {
    state.danhSachThayTheVT = data
  },
  GET_DANH_BA_THEO_MTB(state, data) {
    state.dataItem = data
  },
  GET_KEY_THEM_MOI (state, data) {
    state.keyThemMoi = data
  },
  SET_IS_LOADING (state, data) {
    state.isLoading = data
  },
  SET_PARAM_SUPPLIES (state, data) {
    state.paramSupplies = data
  },
  GET_DANH_SACH_THAY_THE_HOP_DONG (state, data) {
    state.danhSachThayTheHopDong = data
  },
  SET_DATA_ITEM (state, data) {
    console.log('ffafafa', data)
    state.dataItem = data
  }
}
