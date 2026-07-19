import API from '../../api/NhapThietBiBaoHanhAPI'

export const actions = {
    async getDSHuongGiaoBHNhapThueBao ({ commit, state }, data) {
        let response = await API.getDSHuongGiaoBHNhapThueBao(this._vm.axios, data)
        return response.data.data
    },
    async getDSHuongGiaoBH_Filter ({ commit, state }, data) {
        let response = await API.getDSHuongGiaoBH_Filter(this._vm.axios, data)
        return response.data.data
    },
    async getDSTrangThaiBH ({ commit, state }, data) {
        let response = await API.getDSTrangThaiBH(this._vm.axios, data)
        return response.data.data
    },
    async getDSQuyenNguoiDung ({ commit, state }, data) {
        let response = await API.getDSQuyenNguoiDung(this._vm.axios, data)
        return response.data.data
    },
    async getDSKhoGiaoTheoHG ({ commit, state }, data) {
        let response = await API.getDSKhoGiaoTheoHG(this._vm.axios, data)
        return response.data.data
    },
    async getDSKhoNhanTheoHG ({ commit, state }, data) {
        let response = await API.getDSKhoNhanTheoHG(this._vm.axios, data)
        return response.data.data
    },
    async getDSKhoNhanTheoKieu ({ commit, state }, data) {
        let response = await API.getDSKhoNhanTheoKieu(this._vm.axios, data)
        return response.data.data
    },
    async getDSKhoGiaoTheoTTBH ({ commit, state }, data) {
        let response = await API.getDSKhoGiaoTheoTTBH(this._vm.axios, data)
        return response.data.data
    },
    async getDSKhoNhanTheoTTBH ({ commit, state }, data) {
        let response = await API.getDSKhoNhanTheoTTBH(this._vm.axios, data)
        return response.data.data
    },
    async getDSGiaoPhieuBH ({ commit, state }, data) {
        let response = await API.getDSGiaoPhieuBH(this._vm.axios, data)
        return response.data.data
    },
    async KiemTraHanChungTu ({ commit, state }, data) {
        let response = await API.KiemTraHanChungTu(this._vm.axios, data)
        return response.data.data
    },
    async getMucDichIDTheoMaMD ({ commit, state }, data) {
        let response = await API.getMucDichIDTheoMaMD(this._vm.axios, data)
        return response.data.data
    },
    async getThongTinTBCuaPhieuBH ({ commit, state }, data) {
        let response = await API.getThongTinTBCuaPhieuBH(this._vm.axios, data)     
        return response.data.data        
    },
    async TaoChungTuTruyenDan ({ commit, state }, data) {
        let response = await API.TaoChungTuTruyenDan(this._vm.axios, data)
        return response.data.data
    },
    async getDSLuongThaoTac ({ commit, state }, data) {
        let response = await API.getDSLuongThaoTac(this._vm.axios, data)
        return response.data.data
    },
    async getThamSoMacDinh ({ commit, state }, data) {
        let response = await API.getThamSoMacDinh(this._vm.axios, data)
        return response.data.data       
    },
    async NhapKhoBaoHanh ({ commit, state }, data) {
        let response = await API.NhapKhoBaoHanh(this._vm.axios, data)
        return response.data.data       
    },
    async NhapThietBiBaoHanh ({ commit, state }, data) {
        let response = await API.NhapThietBiBaoHanh(this._vm.axios, data)
        return response.data.data       
    },
    async getDSKhoBH ({ commit, state }, data) {
        let response = await API.getDSKhoBH(this._vm.axios, data)
        return response.data.data       
    },
}