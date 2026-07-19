import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    dsDonVi: [],
    dsNhanVienQL: [],
    dsNhanVienThuCuoc: [],
    dsLoaiKhachHang: [],
    dsPhuongAnXL: [],
    dsKyHD: [],
    danhMucHoSo: [],
    dsPhanLoaiKH: [],
    dsCongNo: [],
}

const namespaced = true

export const capnhaphosocongno = {
    actions,
    getters,
    mutations,
    state,
    namespaced
}