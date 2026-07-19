import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutation";

const state = {
  LoaiDonVi: {
    list: [],
    value: 1
  },
  chuTruongList: [],
  danhSachDonViDaGan: [],
  danhSachDonViChuaGan: [],
  danhSachDuAn: [],
  isLoadingDsDuAn: false,
  isLoadingDsDonViDaGan: false,
  isLoadingDsDonViChuaGan: false,
  itemsDonViDaGan: []
};

const namespaced = true;

export const mapProject = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
