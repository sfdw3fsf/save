/* eslint-disable */

export const mutations = {
  setThongTinCap(state, data) {
    if (data) state.ThongTinCap = data;
  },
  setDanDoCap(state, data) {
    state.DanDoCap = data;
  },
  setDanDoCapNew(state, data) {
    state.DanDoCapNew = data;
  },
  setGhiChuCap(state, data) {
    state.GhiChu = data;
  },
  setDsThueBaoTheoMaTB(state, data) {
    state.DsThueBao = data;
  },
  setKetCuoiNew(state, data) {
    state.KetCuoiNew = data;
  },
  setPortKetCuoiRoi(state, data) {
    state.PortKetCuoiRoi = data;
  },
  setCapGoc(state, data) {
    state.CapGoc = data;
  },
  clearStateDanDoCapNew(state) {
    state.DanDoCapNew = { dando_rutgon: [], dando_chitiet: [] };
  },
  clearState(state) {
    state.ThongTinCap = {
      CULY: 0,
      KYHIEU_KC: null,
      VITRI: null,
      VITRI_2: null,
      KYHIEU_CAPGOC: null,
      DOICAP: null,
      DOICAP_2: null
    };
    state.GhiChu = { RESULT: null, MSG: null };
    state.DsThueBao = [];
  },
  clearStateDanDo(state) {
    state.DanDoCap = { dando_rutgon: [], dando_chitiet: [] };
    state.DanDoCapNew = { dando_rutgon: [], dando_chitiet: [] };
  }
};
