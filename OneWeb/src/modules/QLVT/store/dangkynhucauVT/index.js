import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  listThoiGianDK: [],
  listDotDK: [],
  listDuAnNV: [],
  listCongTrinh: [],
  lisNhanVienLienHe: [],
  listKhoDVCungUng: [],
  DSDangKyNhuCauTinhTrang: [],
  DSVatTuDaDK: [],
  DSCuonCapTheoKho: [],
  DSMucDichDK: [],
  DSVatTuMD: [],
  DSVatTuTK: [],
  deleteDSVatTuTK: "",
  DSVatTuTon: [],
  DSVattu_MDDK: [],
  deleteDuLieuVT: "",
  kiemTraDangKyCT: [],
  kiemTraDSDangKyTheoTime: [],
  themMoiVatTuDK: "",
  keyDKVTMS: "",
  checkInfoVatTu: [],
  addDuLieuVatTuDKv2: "",
  listThamSoMacDinh: [],
  DSDangKy_frmCopy: [],
  value_IU_DANGKY_TT: "",
  DSVattu_MDDK_v2: [],
  valueDSKhoTheoNV: [],
  value_listVTThuocNhomMDDK: []
};

const namespaced = true;

export const dangkynhucauVT = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
