// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setDsChuongTrinhCSKH(state, data) {
        state.dsChuongTrinhCSKH = data
    },
    setDsCongViecCSKH(state, data) {
        state.dsCongViecCSKH = data
    },
    clearData(state, data) {
        state.dsChuongTrinhCSKH = [],
        state.dsCongViecCSKH = []
    }
}
