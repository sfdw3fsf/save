// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
  setDanhSachQuyTrinh (state, data) {
    state.DanhSachQuyTrinh = data
  },
  setDSLuongPhieuTheoQT (state, data) {
    state.DSLuongPhieuTheoQT = data
  },
  setDSBaoHanhThietBiV2 (state, data) {
    state.DSBaoHanhThietBiV2 = data
  },
  setDSLine (state, data) {
    state.DSLine = data
  },
  setDSPic (state, data) {
    state.DSPic = data
  },
  clearData (state, data) {
    state.DanhSachQuyTrinh = [];
    state.DSLuongPhieuTheoQT = [];
    state.DSBaoHanhThietBiV2 = [];
    state.DSLine = [];
    state.DSPic = [];
  },

}
  