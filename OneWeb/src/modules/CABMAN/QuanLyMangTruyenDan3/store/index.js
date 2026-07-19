/* eslint-disable */

import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  map: {
    center: {
      lat: 21.037613239281285,
      lng: 105.83846254936175
    },
    zoom: 14,
    mapTypeId: "roadmap"
  },
  data: {
    dsTuyenCap: [],
    dsTTVT: [],
    dsTOVT_2: [],
    dsTOVT: [],
    dsTRAMTB: [],
    dsTRAMTB_2: [],
    dsGIANDO: [],
    dsPHANCAP: [],
    dsCAPGOC: []
}
};

const namespaced = true;

export const QuanLyMangTruyenDan = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};

export const getterName = Object.getOwnPropertyNames(getters);
export const actionName = Object.getOwnPropertyNames(actions);
export const statePropertyName = Object.getOwnPropertyNames(state);
