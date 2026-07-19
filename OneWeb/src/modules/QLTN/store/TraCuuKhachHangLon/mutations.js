// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
  setDanhSachThanhToan(state, data) {
    state.danhsachThanhToan = data;
  },
  setDanhSachLienHe(state, data) {
    state.danhsachLienHe = data;
  },
  clearData(state, data) {
    state.danhsachThanhToan = [];
    state.danhsachLienHe = [];
  }
};
