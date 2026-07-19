import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  isLoading: false,
  dichVuList: [],
  loaiHinhList: [],
  kieuYeuCauList: [],
  DVTinhCuocList: [],
  tocDoList: [],
  mucCuocList: [],
  loaiThietBiList: [],
  brasList: [],
  dslamList: [],
  loaiKenhList: [],
  thoiHanList: [],
  modemList: [],
  tocDoKenhList: [],
  trangBiList: [],
  chuQuanList: [],
  routerList: [],

  dsThueBaoThayDoiLoaiHinh: []
};

const namespaced = true;

export const mapChangeSubsTypeByInstallOperation = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
