import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  listDonVi: [],
  listDichVuVT: [],
  listLoaiHinh: [],
  listLoaiKH: [],
  listKieuTamDung: [],
  listTrungTamVT: [],
  listKHLon: [],
  listThanhLyThueBao: []
};

const namespaced = true;

export const TaoDanhSachThanhLyThueBaoTamDung = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
