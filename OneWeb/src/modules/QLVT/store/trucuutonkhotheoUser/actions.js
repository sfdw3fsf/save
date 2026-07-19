import API from "../../api/TraCuuTonKhoTheoUserAPI";

export const actions = {
    async KiemTraThamSoMucDich ({ commit, state }, maThamSo) {
        let response = await API.KiemTraThamSoMucDich(this._vm.axios, maThamSo)
        return response.data.data || []
    },
    async getDSQuyenNguoiDung ({ commit, state }, nguoiDungId) {
        let response = await API.getDSQuyenNguoiDung(this._vm.axios, nguoiDungId)
        return response.data.data || []
    },
    async getDSKhoTheoNhanVien ({ commit, state }, nhanVienId) {
        let response = await API.getDSKhoTheoNhanVien(this._vm.axios, nhanVienId)
        return response.data.data || []
    },
    async getDSKhoTheoNCC ({ commit, state }, nhanVienId) {
        let response = await API.getDSKhoTheoNCC(this._vm.axios, nhanVienId)
        return response.data.data || []
    },
    async getDSThietBiTonKho ({ commit, state }, khoId) {
        let response = await API.getDSThietBiTonKho(this._vm.axios, khoId)
        return response.data.data || []
    },
   
};
