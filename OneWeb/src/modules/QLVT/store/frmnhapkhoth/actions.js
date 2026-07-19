import API from '../../api/frmNhapKhoTHAPI'

export const actions = {
    async KiemTraMaThamSo({ commit, state },data) {
        let response = await API.KiemTraMaThamSo(this._vm.axios, data)
        return response.data.data 
    },
    async getDSThuHoiVatTu({ commit, state },data) {
        let response = await API.getDSThuHoiVatTu(this._vm.axios, data)
        return response.data.data 
    },
    async getDSLoHang({ commit, state },data) {
        let response = await API.getDSLoHang(this._vm.axios, data)
        return response.data.data 
    },
    async KiemTraHienThiIDVT({ commit, state },data) {
        let response = await API.KiemTraHienThiIDVT(this._vm.axios, data)
        return response.data.data 
    },
    async KiemTraSerialThuHoi({ commit, state },data) {
        let response = await API.KiemTraSerialThuHoi(this._vm.axios, data)
        return response.data.data 
    },
    async KiemTraKhoThuHoi({ commit, state },data) {
        let response = await API.KiemTraKhoThuHoi(this._vm.axios, data)
        return response.data.data 
    },
    async TaoPhieuChungTuV4_HCM({ commit, state },data) {
        let response = await API.TaoPhieuChungTuV4_HCM(this._vm.axios, data)
        return response.data.data 
    },
    async TaoPhieuChungTuV2({ commit, state },data) {
        let response = await API.TaoPhieuChungTuV2(this._vm.axios, data)
        return response.data.data 
    },
    async TaoPhieuCTKemGiaoPhieu({ commit, state },data) {
        let response = await API.TaoPhieuCTKemGiaoPhieu(this._vm.axios, data)
        return response.data.data 
    },
    async QuyetToanThuHoiThueBao({ commit, state },data) {
        let response = await API.QuyetToanThuHoiThueBao(this._vm.axios, data)
        return response.data.data        
    },
    async XoaChungTuTheoIDChungTu({ commit, state },data) {
        let response = await API.XoaChungTuTheoIDChungTu(this._vm.axios, data)
        return response.data.data 
    },
    async XoaPhieuVatTu({ commit, state },data) {
        let response = await API.XoaPhieuVatTu(this._vm.axios, data)
        return response.data.data 
    },
    async XoaTatCaChiTietChungTu({ commit, state },data) {
        let response = await API.XoaTatCaChiTietChungTu(this._vm.axios, data)
        return response.data.data 
    },
    async getDSKieuPhieu({ commit, state },data) {
        let response = await API.getDSKieuPhieu(this._vm.axios, data)
        return response.data.data 
    },
    async KiemTraSLThietBiTrongKho({ commit, state },data) {
        let response = await API.KiemTraSLThietBiTrongKho(this._vm.axios, data)
        return response.data.data 
    },
    async getDSPhieuVatTu({ commit, state },data) {
        let response = await API.getDSPhieuVatTu(this._vm.axios, data)
        return response.data.data 
    },
    async getDSLoaiKho({ commit, state },data) {
        let response = await API.getDSLoaiKho(this._vm.axios, data)
        return response.data.data 
    },
    async getDSMaKhoTapDoan({ commit, state },data) {
        let response = await API.getDSMaKhoTapDoan(this._vm.axios, data)
        return response.data.data 
    },
    async nhapKhoThuHoi({ commit, state },data) {
        let response = await API.nhapKhoThuHoi(this._vm.axios, data)
        return response.data.data 
    },
    async nhapKhoThuHoi_v2({ commit, state },data) {
        let response = await API.nhapKhoThuHoi_v2(this._vm.axios, data)
        return response.data.data 
    },
    
}