// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setDsThanhToanThuocNhom(state, data) {
        state.dsThanhToanThuocNhom = data
    },
    setDsThanhToanKoThuocNhom(state, data) {
        state.dsThanhToanKoThuocNhom = data
    },
    setDsNhomKHLon(state, data) {
        state.dsNhomKHLon = data
    },
    clearData(state, data) {
        state.dsThanhToanThuocNhom = []
        state.dsThanhToanKoThuocNhom = []
        state.dsNhomKHLon = []
    }
}
