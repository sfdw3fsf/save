export const getters = {
  informationBrasList: (state, getters, rootState) => {
    return state.informationBrasList;
  },

  informationDslamList: (state, getters, rootState) => {
    return state.informationDslamList;
  },

  informationDslamListVDC: (state, getters, rootState) => {
    return state.informationDslamListVDC;
  },

  informationNewBrasList: (state, getters, rootState) => {
    return state.informationNewBrasList;
  },

  informationNewDslamListVDC: (state, getters, rootState) => {
    return state.informationNewDslamListVDC;
  },

  isLoading: (state, getters, rootState) => {
    return state.isLoading;
  },

  danhSachThueBao: (state, getters, rootState) => {
    return state.danhSachThueBao;
  }
};
