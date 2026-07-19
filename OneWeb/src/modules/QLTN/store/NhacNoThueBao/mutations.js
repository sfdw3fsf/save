// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
  setListDonVi(state, data) {
    state.listDonVi = data;
  },
  setListDichVuVT(state, data) {
    state.listDichVuVT = data;
  },
  setLanTaoDuLieu(state, data) {
    state.listLanTaoDuLieu = data;
  },
  setLanNhacNo(state, data) {
    state.listLanNhacNo = data;
  },
  clearData(state, data) {
    state.listDonVi = [];
    state.listDichVuVT = [];
    state.listLanTaoDuLieu = [];
    state.listLanNhacNo = [];
  }
};
