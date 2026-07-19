export const mutations = {
    setDanhSachHkn(state, data) {
        state.danhSachHkn = data;
    },setDanhMucHkn(state, data) {
        state.danhMucHkn = data;
    },
    setHknNew(state, data) {
        state.hknNew = data;
    },
    setHknChitiet(state, data) {
        state.hknChitiet = data;
    },
    clearState (state) {
        state.danhSach = null
        state.danhMucHkn = null
        state.hknNew = null
        state.hknChitiet = null
    }
}
