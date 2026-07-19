export const getters = {
  dichVuVTList: (state, getters, rootState) => {
    return state.dichVuVTList;
  },

  loaiHinhTB: (state, getters, rootState) => {
    return state.loaiHinhTB;
  },
  congVanList: (state, getters, rootState) => {
    return state.congVanList;
  },
  nguonKMList: (state, getters, rootState) => {
    return state.nguonKMList;
  },
  danhSachThueBaoUuDai: (state, getters, rootState) => {
    return state.danhSachThueBaoUuDai;
  },
  thueBaoItem: (state, getters, rootState) => {
    return state.thueBaoItem;
  },
  dsThongTinHopDongTB: (state, getters, rootState) => {
    return state.dsThongTinHopDongTB;
  },
  isLoading: (state, getters, rootState) => {
    return state.isLoading;
  },
  soMayAccountTK: (state, getters, rootState)=>{
    return state.soMayAccountTK
  }
};
