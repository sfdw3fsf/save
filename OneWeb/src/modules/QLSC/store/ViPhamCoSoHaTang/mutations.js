export const mutations = {
  setDsCsht (state, dsCsht) {
    state.dsCsht = dsCsht
  },
  setDsDonViXl (state, dvxl) {
    state.dvxl = dvxl
  },
  setKetQuaXl (state, kqxl) {
    state.kqxl = kqxl
  },
  setDonViViPham (state, dvvp) {
    state.dvvp = dvvp
  },
  clearState (state) {
    state.dsCsht = null
    state.dvxl = null
    state.kqxl = null
    state.dvvp = null
  }
}
