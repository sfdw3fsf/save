import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  dichVuList: [],
  loaiHinhList: [],
  kieuYeuCauList: [],
  kieuYeuCauListLD_BC: [],
  dsThueBao: [],
  dsDichVuGiaTang: [],
  dsSoMayAcc: [],
  dsMaGiaoDich: [],
  dsTongTienKhoanMuc: [],
  dsDichVuKhac: []
};

const namespaced = true;

export const mapChangeService = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
