import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  danhSachHuongDamThoai: [],
  danhSachDichVuVT: [],
  danhSachLoaiHinhTB: [],
  danhSachCuocGoi: []
};

const namespaced = true;

export const TCCuocGoi = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
