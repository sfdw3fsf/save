import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  danhsachDonViQL: [],
  danhsachQuyTrinh: [],
  danhsachLoaiPhieu: [],
  danhsachKhoaThueBao: []
};

const namespaced = true;

export const khoamaythuebaonocuoc = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
