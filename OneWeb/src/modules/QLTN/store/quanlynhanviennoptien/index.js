import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  nhanVienNopTien: [],
  danhSachDonVi: [],
  nhomHinhThuc: [],
  hinhThucThanhToan: [],
  kieuNop: [],
  danhSachThuNgan: [],
  danhSachNopTien: [],
  danhSachDaiLyNopNH: [],
};

const namespaced = true;

export const quanlynhanviennoptien = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
