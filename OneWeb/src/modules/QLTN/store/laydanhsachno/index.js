import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  danhsachThueBao: []
};

const namespaced = true;

export const laydanhsachno = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
