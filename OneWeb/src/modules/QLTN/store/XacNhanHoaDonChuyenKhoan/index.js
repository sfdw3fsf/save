import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  listNVTC: [],
  listLuotGiao: [],
  listHoaDon: [],
  listLyDo: [],
 
};

const namespaced = true;

export const XacNhanHoaDonChuyenKhoan = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
