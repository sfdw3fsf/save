import API from '../../api/frmVatTuTrongKhoAPI'

export const actions = {    
    async getDSQuyenND({ commit, state },data) {
        let response = await API.getDSQuyenND(this._vm.axios,data)
        return response.data.data
    },
    async KiemTraThamSoMucDich({ commit, state },data) {
        let response = await API.KiemTraThamSoMucDich(this._vm.axios,data)
        return response.data.data
    },
    async getDSKhoGanTheoNV({ commit, state },data) {
        let response = await API.getDSKhoGanTheoNV(this._vm.axios,data)
        return response.data.data
    },
    async getDSKhoNhaCC({ commit, state },data) {
        let response = await API.getDSKhoNhaCC(this._vm.axios,data)
        return response.data.data
    },
    async getDSThietBiTonKho({ commit, state },data) {
        let response = await API.getDSThietBiTonKho(this._vm.axios,data)
        return response.data.data
    },
    async getDSHangSanXuat({ commit, state },data) {
        let response = await API.getDSHangSanXuat(this._vm.axios,data)
        return response.data.data
    },
    async getDSLinhVuc({ commit, state },data) {
        let response = await API.getDSLinhVuc(this._vm.axios,data)
        return response.data.data
    },
    async KiemTraTrangThaiVT({ commit, state },data) {
        let response = await API.KiemTraTrangThaiVT(this._vm.axios,data)
        return response.data.data
    },
    async getDSVatTuTonKho_V3({ commit, state },data) {
        let response = await API.getDSVatTuTonKho_V3(this._vm.axios,data)
        return response.data.data
    },
    async getDSVatTuTonKho_V2({ commit, state },data) {
        let response = await API.getDSVatTuTonKho_V2(this._vm.axios,data)
        return response.data.data
    },
    async getDSTonKhoNhaCC({ commit, state },data) {
        let response = await API.getDSTonKhoNhaCC(this._vm.axios,data)
        return response.data.data
    },
    async getDSLoaiKho({ commit, state },data) {
        let response = await API.getDSLoaiKho(this._vm.axios,data)
        return response.data.data
    },
    async getDSKhoTapDoan({ commit, state },data) {
        let response = await API.getDSKhoTapDoan(this._vm.axios,data)
        return response.data.data
    },
    async getDSDangKy({ commit, state },data) {
        let response = await API.getDSDangKy(this._vm.axios,data)
        return response.data.data
    },
    async getDSVatTuDK({ commit, state },data) {
        let response = await API.getDSVatTuDK(this._vm.axios,data)
        return response.data.data
    },
    async XoaDuLieuSLDuyetVT({ commit, state },data) {
        let response = await API.XoaDuLieuSLDuyetVT(this._vm.axios,data)
        return response.data.data
    },
    async getDSVatTuDangKy({ commit, state },data) {
        let response = await API.getDSVatTuDangKy(this._vm.axios,data)
        return response.data.data
    },
    async CapNhatVatTuDaDangKy({ commit, state },data) {
        let response = await API.CapNhatVatTuDaDangKy(this._vm.axios,data)
        return response.data.data
    },
    async CapNhatVatTuDKKhiXoa({ commit, state },data) {
        let response = await API.CapNhatVatTuDKKhiXoa(this._vm.axios,data)
        return response.data.data
    },
    async getDSDangKyTheoDKTT({ commit, state },data) {
        let response = await API.getDSDangKyTheoDKTT(this._vm.axios,data)
        return response.data.data
    },
    async CapNhatTraPhieu({ commit, state },data) {
        let response = await API.CapNhatTraPhieu(this._vm.axios,data)
        return response.data.data
    },
    async getDSChuaHoanTamUng({ commit, state },data) {
        let response = await API.getDSChuaHoanTamUng(this._vm.axios,data)
        return response.data.data
    },
    async getDSDongThietBi({ commit, state },data) {
        let response = await API.getDSDongThietBi(this._vm.axios,data)
        return response.data.data
    },
    async getDSHeThongVatTu({ commit, state },data) {
        let response = await API.getDSHeThongVatTu(this._vm.axios,data)
        return response.data.data
    },
    async getDSHeThongVatTu({ commit, state },data) {
        let response = await API.getDSHeThongVatTu(this._vm.axios,data)
        return response.data.data
    },
    async kiemTraTrangThaiVatTu({ commit, state },data) {
        let response = await API.kiemTraTrangThaiVatTu(this._vm.axios,data)
        return response.data.data
    },
    
}