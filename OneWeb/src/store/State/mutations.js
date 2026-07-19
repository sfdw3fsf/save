export const mutations = {
  setCityList(state, data) {
    state.cityList = data;
  },

  setDistrictList(state, data) {
    state.districtList = data;
  },

  setStatusCheckExistState(state, data) {
    state.statusCheckExistState = data;
  },

  setIsLoading(state, data) {
    state.isLoading = data;
  },

  setErrorExist(state, data) {
    state.errorExist = data;
  }
};
