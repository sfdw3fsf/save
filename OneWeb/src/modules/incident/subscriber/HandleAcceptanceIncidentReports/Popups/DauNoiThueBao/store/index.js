/* eslint-disable */

import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  ThongTinCap: {
    CULY: 0,
    KYHIEU_KC: null,
    VITRI: null,
    VITRI_2: null,
    KYHIEU_CAPGOC: null,
    DOICAP: null,
    DOICAP_2: null
  },
  loadThongTinCap: true,
  DanDoCap: { dando_rutgon: [], dando_chitiet: [] },
  DanDoCapNew: { dando_rutgon: [], dando_chitiet: [] },
  GhiChu: { RESULT: null, MSG: null },
  DsThueBao: [],
  KetCuoiNew: {},
  PortKetCuoiRoi: [],
  CapGoc: {
    CURSOR2: [
      {
        CAP_ID: null,
        SOI: null
      }
    ],
    CURSOR1: [
      {
        SOI: null,
        KYHIEU: null
      }
    ]
  }
};

const namespaced = true;

export const daunoiThuebao = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};

export const getterName = Object.getOwnPropertyNames(getters);
export const actionName = Object.getOwnPropertyNames(actions);
export const statePropertyName = Object.getOwnPropertyNames(state);
