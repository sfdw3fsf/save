// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setDsDonViLoiXacNhan(state, data) {
        state.DsDonViLoiXacNhan = data
    },
    setDsNhomNguyenNhan(state, data) {
        state.DsNhomNguyenNhan = data
    },
    setDsNguyenNhan(state, data) {
        state.DsNguyenNhan = data
    },
    setDsPhieuLH(state, data) {
        state.DsPhieuLH = data
    },
    setDsNoiDungPA(state, data) {
        state.dsNoiDungPA = data
    },
    setDsPAMoiTiepNhanTheoNV(state, data) {
        state.dsPAMoiTiepNhanTheoNV = data
    },
    setDsNhanVienLoi(state, data) {
        state.DsNhanVienLoi = data
    },
    clearData(state) {
        state.DsDonViLoiXacNhan = []
        state.DsNhomNguyenNhan = []
        state.DsNguyenNhan = []
        state.DsPhieuLH = []
        state.dsPAMoiTiepNhanTheoNV = []
        state.DsNhanVienLoi = []
    },
    clearDsPhieuLH(state) {
        state.DsPhieuLH = []
    },
}
