export const getters = {
  chuTruongList: (state, getters, rootSate) => {
    return state.chuTruongList;
  },

  danhSachDuAn: (state, getters, rootSate) => {
    return state.danhSachDuAn;
  },

  danhSachDonViDaGan: (state, getters, rootSate) => {
    return state.danhSachDonViDaGan;
  },

  danhSachDonViChuaGan: (state, getters, rootSate) => {
    return state.danhSachDonViChuaGan;
  },

  isLoadingDsDuAn: (state, getters, rootSate) => {
    return state.isLoadingDsDuAn;
  },

  isLoadingDsDonViDaGan: (state, getters, rootSate) => {
    return state.isLoadingDsDonViDaGan;
  },

  isLoadingDsDonViChuaGan: (state, getters, rootSate) => {
    return state.isLoadingDsDonViChuaGan;
  },

  itemsDonViDaGan: (state, getters, rootSate) => {
    return state.itemsDonViDaGan;
  }
};
