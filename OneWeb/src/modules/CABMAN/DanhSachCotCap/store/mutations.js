/* eslint-disable */

export const mutations = {
  setDsTTVT(state, dsTTVT) {
    state.dsTTVT = dsTTVT;
  },
  setDsTOQL(state, dsTOQL) {
    state.dsTOQL = dsTOQL;
  },
  setDsTuyenCot(state, dsTC) {
    state.dsTC = dsTC;
  },

  setDsCot(state, dsC) {
    state.dsC = dsC;
  },

  clearState(state) {
    state.dsTTVT = [];
    state.dsTOQL = [];
    state.dsTC = [];
    state.dsC = [];
  },

  clearStateDsTOQL(state) {
    state.dsTOQL = [];
  },

  clearStateDsTC(state) {
    state.dsTC = [];
  },

  clearStateDsC(state) {
    state.dsC = [];
  }
};
