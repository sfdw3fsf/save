// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setDsNhanVienCSKH(state, data) {
        state.dsNhanVienCSKH = data
    },
    setDsCongViecCSKHTheoNguoiTao(state, data) {
        state.dsCongViecCSKHTheoNguoiTao = data
    },
    clearData(state, data) {
        state.dsNhanVienCSKH = [],
        state.dsCongViecCSKHTheoNguoiTao = []
    }
}
