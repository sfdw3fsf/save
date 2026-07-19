import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  DSNhomTB: [],
  DSDVT: [],
  DSLoaiPhi: [],
  DSTyLeVat: [],
  DSDichVuVT: [],
  DSLoaiThietBi: [],
  DSLoaiHinhTB: [],
  createDSLTB:"",
  updateDSLTB:"",
  createLTBLoaiHinhTB:""
};

const namespaced = true;

export const danhmucthietbi = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
