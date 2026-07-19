export const mutations = {
  setDsLoaiDonVi(state, data){
    state.LoaiDonVi.list = data;
  },
  setLoaiDonVi(state, data){
    state.LoaiDonVi.value = data;
  },
  setChuTruongList(state, data) {
    state.chuTruongList = data;
  },

  setDanhSachDuAn(state, data) {
    state.danhSachDuAn = data;
  },

  setDanhSachDonViDaGan(state, data) {
    state.danhSachDonViDaGan = data;
  },

  setDanhSachDonViChuaGan(state, data) {
    state.danhSachDonViChuaGan = data;
  },

  setIsLoadingDsDuAn(state, data) {
    state.isLoadingDsDuAn = data;
  },

  setIsLoadingDsDonViDaGan(state, data) {
    state.isLoadingDsDonViDaGan = data;
  },

  setIsLoadingDsDonViChuaGan(state, data) {
    state.isLoadingDsDonViChuaGan = data;
  },

  setItemsDonViDaGan(state, data) {
    state.itemsDonViDaGan = data;
  }
};
