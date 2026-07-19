import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  DSNhomTB: [],
  addDuLieuDSTB: "",
  getKeyNhomThietBi: "",
  updateDuLieuDSTB: "",
  deleteDLNTB: ""
};

const namespaced = true;

export const loaithietbi = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};

export const getterName = Object.getOwnPropertyNames(getters);
export const actionName = Object.getOwnPropertyNames(actions);
export const statePropertyName = Object.getOwnPropertyNames(state);
export const mutationName = Object.getOwnPropertyNames(mutations);
