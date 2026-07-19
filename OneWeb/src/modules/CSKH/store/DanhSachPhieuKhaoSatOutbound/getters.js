import {searchArray} from '../../../../utils/util'
export const getters = {
    getdsLoaiNhomKhaoSatGetter: (state, getters, rootState) => state.dsLoaiNhomKhaoSat,
    getdsTrangThaiLuotGiaoKhaoSatGetter: (state, getters, rootState) => state.dsTrangThaiLuotGiaoKhaoSat,
    getdsTrangThaiPhieuGetter: (state, getters, rootState) => state.dsTrangThaiPhieu,
    getdsLuotGiaoNhanKhaoSatGetter: (state, getters, rootState) => state.dsLuotGiaoNhanKhaoSat,
    getdsPhieuDaGiaoKhaoSatGetter: (state, getters, rootState) => state.dsPhieuDaGiaoKhaoSat,
    getdsThongTinCSKHThueBaoGetter: (state, getters, rootState) => state.dsThongTinCSKHThueBao,
    getdsNhanVienKyThuatDiaBanGetter: (state, getters, rootState) => state.dsNhanVienKyThuatDiaBan,
    getdsNhanVienKinhDoanhDiaBanGetter: (state, getters, rootState) => state.dsKinhDoanhKyThuatDiaBan,
}