import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  danhsachThanhToan: [],
  danhsachLienHe: []
};

const namespaced = true;

export const TraCuuKhachHangLon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
