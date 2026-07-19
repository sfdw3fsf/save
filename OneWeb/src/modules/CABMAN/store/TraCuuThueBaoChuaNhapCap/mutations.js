/* eslint-disable */

export const mutations = {
  setDsDonVi(state, dsDonVi) {
    state.dsDonVi = dsDonVi;
  },
  setDsThueBaoChuaNhapCap(state, dsThueBaoChuaNhapCap) {
    state.dsThueBaoChuaNhapCap = dsThueBaoChuaNhapCap;
  },
  setLsTacDongCapThueBao(state, lsTacDongCapThueBao) {
    state.lsTacDongCapThueBao = lsTacDongCapThueBao;
  },
  clearState(state) {
    state.dsDonVi = [];
    state.dsThueBaoChuaNhapCap = [];
    state.lsTacDongCapThueBao = [];
  }
};
