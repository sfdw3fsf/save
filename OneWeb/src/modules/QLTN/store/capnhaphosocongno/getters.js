export const getters = {
    danhSachDonViGetter: (state, getter, rootState) => {
        return state.dsDonVi
    },
    danhSachNhanVienQLGetter: (state, getter, rootState) => {
        return state.dsNhanVienQL
    },
    danhSachNhanVienThuCuocGetter: (state, getter, rootState) => {
        return state.dsNhanVienThuCuoc
    },
    danhSachLoaiKhachHang: (state, getter, rootState) => {
        return state.dsLoaiKhachHang
    },
    danhSachPhuongAnXLGetter: (state, getter, rootState) => {
        return state.dsPhuongAnXL
    },
    danhSachKyHDGetter: (state, getter, rootState) => {
        return state.dsKyHD
    },
    danhMucHoSoGetter: (state, getter, rootState) => {
        return state.danhMucHoSo
    },
    danhSachPhanLoaiKHGetter: (state, getter, rootState) => {
        return state.dsPhanLoaiKH
    },
    danhSachCongNoGetter: (state, getter, rootState) => {
        return state.dsCongNo
    },
}