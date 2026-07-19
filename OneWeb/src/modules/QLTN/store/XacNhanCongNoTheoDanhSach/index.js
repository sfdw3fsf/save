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
  listCongNo: [],
  listTuyenThu: [],
  listLyDo: [],
  listNguyenNhan: []
};

const namespaced = true;

export const XacNhanCongNoTheoDanhSach = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
