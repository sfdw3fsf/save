import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  listTraCuuVatTu: [],
  DanhSachKho: []
};

const namespaced = true;

export const tracuutonkhoTTCUVT = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
