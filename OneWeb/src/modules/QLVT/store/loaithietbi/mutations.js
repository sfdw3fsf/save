// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
  setDSNhomTB(state, data) {
    state.DSNhomTB = data;
  },
  setAddDuLieuDSTB(state, data) {
    state.addDuLieuDSTB = data;
  },
  setGetKeyNhomThietBi(state, data) {
    state.getKeyNhomThietBi = data;
  },
  setUpdateDuLieuDSTB(state, data) {
    state.updateDuLieuDSTB = data;
  },
  setDeleteDLNTB(state, data) {
    state.deleteDLNTB = data;
  },
};
