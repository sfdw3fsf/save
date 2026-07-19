export const mutations = {
    setDSQuan(state, data) {
        state.dsQuan = data;
    },
    setDSLoaiKHL(state, data) {
        state.dsLoaiKHL = data;
    },
    setDSLoaiKH(state, data) {
        state.dsLoaiKH = data;
    },
    setDSLanTao(state, data) {
        state.dsLanTao = data;
    },
    setDSChuKyNo(state, data) {
        state.dsChuKyNo = data;
    },
    setDSKhachHang(state, data) {
        state.dsKhachHang = data;
    },
    setDSChiTietKhachHang(state, data) {
        state.dsChiTietKhachHang = data;
    },
    setDSThongTinNoTheoKhachHang(state, data) {
        state.dsThongTinNo = data;
    },
    setDataExcel(state, data) {
        state.dataExcel = data;
    },
    clearData(state, data) {
        state.dsKhachHang = [];
    },
    setTotalElements(state, data) {
        state.totalElements = data;
    },
    cleanDSThongTinNo(state, data) {
        state.dsThongTinNo = []
    }
};
