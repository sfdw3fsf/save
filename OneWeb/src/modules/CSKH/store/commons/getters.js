export const getters = {
    loaiDSThueBaoKhongKhaoSatGetter: (state, getters, rootState) => {
        return state.loaiDSThueBaoKhongKhaoSat
    },
    loaiDanhMucVTGetter: (state, getters, rootState) => {
        return state.loaiDanhMucVT
    },
    cayDonViGetter: (state, getters, rootState) => {
        return state.cayDonVi
    },
    dichVuVienThongEnumGetter: (state, getters, rootState) => state.dichVuVienThongEnum,
    dsDichVuGetter: (state, getters, rootState) => state.dsDichVu
}