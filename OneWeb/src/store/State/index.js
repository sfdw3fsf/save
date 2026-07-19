import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  cityList: [],
  districtList: [],
  statusCheckExistState: "",
  isLoading: false,
  errorExist: ""
};

const namespaced = true;

export const mapStateLocation = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
