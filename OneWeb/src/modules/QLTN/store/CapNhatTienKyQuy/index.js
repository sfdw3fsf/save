import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  danhSachNhanVienDatCoc: [],
};

const namespaced = true;

export const CapNhatTienKyQuy = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
