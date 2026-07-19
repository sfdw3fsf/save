// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setDsThanhToan(state, data) {
        state.dsThanhToan = data
    },
    setDsMaTTTheoMaInGhep(state, data) {
        state.dsMaTTTheoMaInGhep = data
    },
    setDsDonViQuanLy(state, data) {
        state.dsDonViQuanLy = data
    },
    setDsMIGTheoKhachHang(state, data) {
        state.dsMIGTheoKhachHang = data
    },
    setDsMaThanhToanChuaGan(state, data) {
        state.dsMaThanhToanChuaGan = data
    },
    setDsMaThanhToanDaGan(state, data) {
        state.dsMaThanhToanDaGan = data
    },
    setDsLienHe(state, data) {
        state.dsLienHe = data
    },
    clearData(state, data) {
        state.dsThanhToan = []
        state.dsMaTTTheoMaInGhep = []
        state.dsDonViQuanLy = []
        state.dsMIGTheoKhachHang = []
        state.dsMaThanhToanChuaGan = []
        state.dsMaThanhToanDaGan = []
        state.dsLienHe = []
    }
}
