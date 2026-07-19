// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setListDVVT(state, data) {
        state.listDVVT = data;
    },
    setListLoaiHinhTB(state, data) {
        state.listLoaiHinh = data;
    },
    setListLyDoKhoa(state, data) {
        state.listLyDoKhoa = data;
    },
    setListQuyTrinh(state, data) {
        state.listQuyTrinh = data;
    },

    clearData(state, data) {
        state.listDVVT = [];
        state.listLoaiHinh = [];
        state.listLyDoKhoa = [];
        state.listQuyTrinh = [];
    }
};
