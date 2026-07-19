export const mutations = {
  setDichVuVTList(state, data) {
    state.dichVuVTList = data;
  },
  setLoaiHinhTBList(state, data) {
    state.loaiHinhTB = data;
  },
  setCongVanList(state, data) {
    state.congVanList = data;
  },
  setNguonKMList(state, data) {
    state.nguonKMList = data;
  },
  setDanhSachThueBaoUuDai(state, data) {
    state.danhSachThueBaoUuDai = data;
  },
  setThueBaoItem(state, data) {
    state.thueBaoItem = data;
  },
  setDsThongTinHopDongTB(state, data) {
    state.dsThongTinHopDongTB = data;
  },
  setIsLoading(state, data) {
    state.isLoading = data;
  },

  setSoMayAccountTK(state, data){
    state.soMayAccountTK = data;
  },
  isUpdateSuccess(state, payload){
    state.isUpdateSuccess = payload;
  }
};
