// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setDsNhanVienGiao(state, data) {
        state.DsNhanVienGiao = data
    },
    setDsDichVu(state, data) {
        state.DsDichVu = data
    },
    setDsNhanVienNhan(state, data) {
        state.DsNhanVienNhan = data
    },
    setDsLuotGiaoKhaoSat(state, data) {
        state.DsLuotGiaoKhaoSat = data
    },
    setDsPhieuDaGiaoKhaoSat(state, data) {
        state.DsPhieuDaGiaoKhaoSat = data
    },
    setDsThongTinGiaoKhaoSat(state, data) {
        state.DsThongTinGiaoKhaoSat = data
    },
    setDsLoaiHopDong(state, data) {
        state.DsLoaiHopDong = data
    },
    clearData(state) {
        state.DsDichVu = []
        state.DsNhanVienGiao = []
        state.DsNhanVienNhan = []
        state.DsLuotGiaoKhaoSat = []
        state.DsPhieuDaGiaoKhaoSat = []
        state.DsThongTinGiaoKhaoSat = []
        state.DsLoaiHopDong = []
    },
    clearGridData(state) {
        state.DsLuotGiaoKhaoSat = []
        state.DsPhieuDaGiaoKhaoSat = []
        state.DsThongTinGiaoKhaoSat = []
    },
}
