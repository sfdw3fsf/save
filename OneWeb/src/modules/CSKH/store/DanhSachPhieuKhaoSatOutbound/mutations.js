export const mutations = {
    setPageInfo(state, data) {
        state.pageInfo = data
    },
    setdsLoaiNhomKhaoSat(state, data) {
        state.dsLoaiNhomKhaoSat = data
    },
    setdsTrangThaiLuotGiaoKhaoSat(state, data) {
        state.dsTrangThaiLuotGiaoKhaoSat = data
    },
    setdsTrangThaiPhieu(state, data) {
        state.dsTrangThaiPhieu = data
    },
    setDsLuotGiaoNhanKhaoSat(state, data) {
        state.dsLuotGiaoNhanKhaoSat = data
    },
    setdsPhieuDaGiaoKhaoSat(state, data) {
        state.dsPhieuDaGiaoKhaoSat = data
    },
    setdsNhanVienKyThuatDiaBan(state, data) {
        state.dsNhanVienKyThuatDiaBan = data
    },
    setdsNhanVienKinhDoanhDiaBan(state, data) {
        state.dsNhanVienKinhDoanhDiaBan = data
    },
    setdsThongTinCSKHThueBao(state, data) {
        state.dsThongTinCSKHThueBao = data
    },
    clearData(state, data) {
        state.dsLoaiNhomKhaoSat = []
        state.dsTrangThaiLuotGiaoKhaoSat = []
        state.dsTrangThaiPhieu = []
        state.dsLuotGiaoNhanKhaoSat = []
        state.dsPhieuDaGiaoKhaoSat = []
        state.dsThongTinCSKHThueBao = []
        state.dsNhanVienKyThuatDiaBan = []
        state.dsNhanVienKinhDoanhDiaBan = []
    }
}