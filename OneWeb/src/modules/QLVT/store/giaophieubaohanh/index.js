import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  // objSearch: {},
  danhSachKho: [],
  DSHeThongQuyTrinh: [],
  DSHuongGiao: [],
  Tag: "",
  hg_id: "",
  DSDonViNhan: [],
  DSGiaoPhieuBH: [],
  chungTu: "",
  DSLuongThaoTac: [],
  qt_id: "",
  DSThietBiBaoHanhTra: [],
  DSMucDichXuatVatTuKho: [],
  DSKhoGiaoTheoToTruong: [],
  CapNhatGPBHHuongGiao: "",
  CapNhatBaoHanhKho: "",
  DSThietBiKhoGiao: "",
  CapNhatBaoHanhThietBi: "",
  DSThietBiBaoHanh: [],
  DSKiemTraVatTuTrongBH: [],
  DSKiemTraVatTuTrongKho: [],
  DSKiemTraTaoChungTu: [],
  CapNhatGiaoPhieuBH: "",
  DSKieuBaoHanh: [],
  statusHoanThienBaoHanh: "",
  DSLoaiDichVuHuongGiao: [],
  statusTaoGiaoPhieuBaoHanh: "",
  statusCapNhatDonViBH: "",
  nhomQtId: ""
};

const namespaced = true;

export const giaophieubaohanh = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
