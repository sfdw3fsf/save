import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  listDonVi: [],
  listHTTT: [],
  listNhomKH: [],
  listLoaiKH: [],
  listNVQuanLy: [],
  listNVThuCuoc: [],
  listNhomCongNo: [],
  listKyHoaDon: [],
  listCongNo: []
};

const namespaced = true;

export const GanNhomCongNo = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
