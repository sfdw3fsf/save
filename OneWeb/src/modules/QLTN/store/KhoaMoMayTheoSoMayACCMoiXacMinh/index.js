import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
    listDVVT: [],
    listLoaiHinh: [],
    listLyDoKhoa: [],
    listQuyTrinh: []
};

const namespaced = true;

export const KhoaMoMayTheoSoMayACCMoiXacMinh = {
    namespaced,
    state,
    getters,
    actions,
    mutations
};
