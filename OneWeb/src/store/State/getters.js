export const getters = {
  cityList: (state, getters, rootState) => {
    return state.cityList;
  },
  districtList: (state, getters, rootState) => {
    return state.districtList;
  },
  statusCheckExistState: (state, getter, rootState) => {
    return state.statusCheckExistState;
  },
  isLoading: (state, getter, rootState) => {
    return state.isLoading;
  },
  errorExist: (state, getter, rootState) => {
    return state.errorExist;
  }
};
