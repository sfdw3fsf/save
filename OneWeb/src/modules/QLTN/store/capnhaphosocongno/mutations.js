export const mutations = {
    setDanhSachDonVi(state, data) {
        state.dsDonVi = data
    },
    setDanhSachNhanVienQL(state, data) {
        state.dsNhanVienQL = data
    },
    setDanhSachNhanVienThuCuoc(state, data) {
        state.dsNhanVienThuCuoc = data
    },
    setDanhSachLoaiKhachHang(state, data) {
        state.dsLoaiKhachHang = data
    },
    setDanhSachPhuongAnXL(state, data) {
        state.dsPhuongAnXL = data
    },
    setDanhSachKyHD(state, data) {
        state.dsKyHD = data
    },
    setDanhMucHoSo(state, data) {
        state.danhMucHoSo = data
    },
    setDanhSachPhanLoaiKH(state, data) {
        state.dsPhanLoaiKH = data
    },
    setDanhSachCongNo(state, data) {
        state.dsCongNo = data
    },
    clearGrid(state, data) {
        state.dsCongNo = []
    }
}