// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
  setDanhSachNhanVienDatCoc(state, data) {
    state.danhSachNhanVienDatCoc = data;
  },

  clearData(state, data) {
    state.danhSachNhanVienDatCoc = [];
  }
};
