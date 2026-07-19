import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  informationBrasList: [],
  informationDslamList: [],
  informationDslamListVDC: [],

  informationNewBrasList: [],
  informationNewDslamListVDC: [],

  danhSachThueBao: [],
  isLoading: false
};

const namespaced = true;

export const mapDslamTransfer = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
