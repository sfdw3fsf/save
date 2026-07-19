import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  dichVuVTList: [],
  loaiHinhTB: [],
  congVanList: [],
  nguonKMList: [],
  danhSachThueBaoUuDai: [],
  thueBaoItem: [],
  dsThongTinHopDongTB: [],
  isLoading: false,
  soMayAccountTK:null,
  isUpdateSuccess: false,
};

const namespaced = true;

export const mapPreferentialSubscriber = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
