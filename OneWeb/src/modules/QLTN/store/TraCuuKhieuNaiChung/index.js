import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  danhSachDichVuVT: [],
  danhSachLoaiHinhTB: [],
  danhSachKhieuNaiTB: [],
  danhSachNVQLDaiLy: [],
  thongTinThanhToan: []
};

const namespaced = true;

export const TraCuuKhieuNaiChung = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
