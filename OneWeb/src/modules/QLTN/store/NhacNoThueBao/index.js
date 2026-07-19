import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  listDonVi: [],
  listDichVuVT: [],
  listLanTaoDuLieu: [],
  listLanNhacNo: [],
};

const namespaced = true;

export const NhacNoThueBao = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
