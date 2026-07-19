export const mutations = {
  setInformationBrasList(state, data) {
    state.informationBrasList = data;
  },
  setInformationDslamList(state, data) {
    state.informationDslamList = data;
  },

  setInformationDslamVDCList(state, data) {
    state.informationDslamListVDC = data;
  },

  setInformationNewBrasList(state, data) {
    state.informationNewBrasList = data;
  },
  setInformationNewDslamVDCList(state, data) {
    state.informationNewDslamListVDC = data;
  },

  setDanhSachThueBao(state, data) {
    state.danhSachThueBao = data;
  },

  setIsLoading(state, data) {
    state.isLoading = data;
  }
};
