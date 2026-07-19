/* eslint-disable */

import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  ds_ttvt: [],
  ds_tokt: [],
  ds_tram: [],
  ds_cap: [],
  ds_ketcuoi_cnc: [],
  ds_ketcuoi_ctd: [],
  gian_do_cap: [],
  gian_do_cong: [],
  gian_do_cot: [],
  tabPageIndex: 0,
  phanloaikc_id: null,
  IsLoadNewGraph: false,
  dataDiagram: null,
  ky_hieu_phan_biet_delete: null,
  capgoc: Object,
  toql: Object,
  donvi: Object,
  map: {
    center: {
      lat: 21.037613239281285,
      lng: 105.83846254936175
    },
    zoom: 14,
    mapTypeId: "roadmap",
    cluster_map_google: false
  },
  vaction: "None",
  vnguon_id: null,
  vdich_id: null,
  giando_config: null,
  ttvt_id: null,
  tramvt_id: null,
  node_focus: null
};

const namespaced = true;

export const GiamSatMangNgoaiVi = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};

export const getterName = Object.getOwnPropertyNames(getters);
export const actionName = Object.getOwnPropertyNames(actions);
export const statePropertyName = Object.getOwnPropertyNames(state);
