import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  listLoaiCT: [],
  listMucDich: [],
  listTrangThaiCT: [],
  listDonViDK: [],
  listVatTuDK: [],
  listDonVi: [],
  listKhoDonVi: [],
  listTuKho: [],
  listKieuPhieu: [],
  listTCCT: [],
  listChungTuVatTu: [],
  listChungTuThietBi: [],
  value_tra_cuu_chung_tu_da_xoa: []
};

const namespaced = true;

export const tracuuchungtu = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
