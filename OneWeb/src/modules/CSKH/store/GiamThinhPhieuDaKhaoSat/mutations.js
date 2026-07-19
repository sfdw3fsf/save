export const mutations = {
    setPageInfo(state, data) {
        state.pageInfo = data
    },
    setdsLoaiPhieuKhaoSat(state, data) {
        state.dsLoaiPhieuKhaoSat = data
    },
    setdsDonViTrungTamVienThong(state, data) {
        state.dsDonViTrungTamVienThong = data
    },
    setdsDienThoaiVien(state, data) {
        state.dsDienThoaiVien = data
    },
    setdsPhanLoaiKhachHang(state, data) {
        state.dsPhanLoaiKhachHang = data
    },
    setdsLoaiKQGiamThinh(state, data) {
        state.dsLoaiKQGiamThinh = data
    },
    setDsThueBao(state, data) {
        state.dsThueBao = data
        state.dsNoiDungKS = []
    },
    setNoiDungKS(state, data) {
        state.dsNoiDungKS = data
    },
    clearData(state, data) {
        state.dsLoaiPhieuKhaoSat = []
        state.dsDonViTrungTamVienThong = []
        state.dsDienThoaiVien = []
        state.dsPhanLoaiKhachHang = []
        state.dsLoaiKQGiamThinh = []
        state.dsThueBao = []
        state.dsNoiDungKS = []
    }
}