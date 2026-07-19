// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
  setNVTC(state, data) {
    state.listNVTC = data;
  },
  setLuotGiao(state, data) {
    state.listLuotGiao = data;
  },
  setListHoaDon(state, data) {
    state.listHoaDon = data;
  },
  setLyDoTra(state, data) {
    state.listLyDo = data;
  },

  clearData(state, data) {
    state.listNVTC = [];
    state.listLuotGiao = [];
    state.listHoaDon = [];
    state.listLyDo = [];
  }
};
