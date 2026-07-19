import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
    dsKhachHang: [],
    dsLanTao: [],
    dsQuan: [],
    dsLoaiKHL: [],
    dsLoaiKH: [],
    dsChuKyNo: [],
    dsChiTietKhachHang: [],
    dsThongTinNo: [],
    dataExcel: [],
    totalElements: 0
};

const namespaced = true;

export const danhsachkhachhanglon = {
    actions,
    getters,
    mutations,
    state,
    namespaced
};
