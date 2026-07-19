import API from '../../api/DonHangAPI'

export const actions = {
    async getTrangThaiDonHang({ commit, state }) {
        let response = await API.getTrangThaiDonHang(this._vm.axios)
        return response.data.data
    },
    async getDSHopDong({ commit, state }, donViId) {
        let response = await API.getDSHopDong(this._vm.axios, donViId)
        return response.data.data
    },
    async getDSKhoNhaCungCap({ commit, state }, kieu) {
        let response = await API.getDSKhoNhaCungCap(this._vm.axios, kieu)
        return response.data.data
    },
    async getTrangThaiHopDong({ commit, state }, kieu) {
        let response = await API.getTrangThaiHopDong(this._vm.axios, kieu)
        return response.data.data
    },
    async getDSHopDongTheoDK({ commit, state }, donViId) {
        let response = await API.getDSHopDongTheoDK(this._vm.axios, donViId)
        return response.data.data
    },
    async getDSLoaiDonHang({ commit, state }) {
        let response = await API.getDSLoaiDonHang(this._vm.axios)
        return response.data.data
    },
    async getDSDonHangTheoDieuKien({ commit, state }, data) {
        let response = await API.getDSDonHangTheoDieuKien(this._vm.axios, data)
        return response.data.data
    },
    async getDSComboDonHang({ commit, state }, data) {
        let response = await API.getDSComboDonHang(this._vm.axios, data)
        return response.data.data
    },
    async getDSThongTinHopDong({ commit, state }, data) {
        let response = await API.getDSThongTinHopDong(this._vm.axios, data)
        return response.data.data
    },
    async getDSDuAn({ commit, state }, data) {
        let response = await API.getDSDuAn(this._vm.axios, data)
        return response.data.data
    },
    async getTenNguoiDungTheoDonHang({ commit, state }, data) {
        let response = await API.getTenNguoiDungTheoDonHang(this._vm.axios, data)
        return response.data.data
    },
    async getDSVatTuHopDong({ commit, state }, data) {
        let response = await API.getDSVatTuHopDong(this._vm.axios, data)
        return response.data.data
    },
    async getDSVatTuDonHang({ commit, state }, donHangId) {
        let response = await API.getDSVatTuDonHang(this._vm.axios, donHangId)
        return response.data.data
    },
    async getTenNguoiCapNhap({ commit, state }, donHangId) {
        let response = await API.getTenNguoiCapNhap(this._vm.axios, donHangId)
        return response.data.data
    },
    // async getDSChiTietDonHang({ commit, state },donHangId) {
    //     let response = await API.getDSChiTietDonHang(this._vm.axios,donHangId)
    //     try {
    //         return response.data.data || []
    //     } catch (error) {
    //         throw error
    //     }
    //     return response
    // },
    async getNgayKy({ commit, state }, data) {
        let response = await API.getNgayKy(this._vm.axios, data)
        return response.data.data
    },
    async getDSLoaiDonHangTheoId({ commit, state }, donHangId) {
        let response = await API.getDSLoaiDonHangTheoId(this._vm.axios, donHangId)
        return response.data.data
    },

    async getThongTinDonHangCha({ commit, state }, donHangId) {
        let response = await API.getThongTinDonHangCha(this._vm.axios, donHangId)
        return response.data.data
    },
    async getKey({ commit, state }, data) {
        let response = await API.getKey(this._vm.axios, data)
        return response.data.data
    },
    async getMaDonHang({ commit, state }, maHopDong) {
        let response = await API.getMaDonHang(this._vm.axios, maHopDong)
        return response.data.data
    },
    async themMoiDonHang({ commit, state }, data) {
        let response = await API.themMoiDonHang(this._vm.axios, data)
        return response.data.data
    },
    async getTTDonHangTheoDHId({ commit, state }, donHangId) {
        let response = await API.getTTDonHangTheoDHId(this._vm.axios, donHangId)
        return response.data.data
    },
    async capNhatTrangThaiNTDonHang({ commit, state }, data) {
        let response = await API.capNhatTrangThaiNTDonHang(this._vm.axios, data)
        return response.data.data
    },
    async xoaDonHang({ commit, state }, donHangId) {
        let response = await API.xoaDonHang(this._vm.axios, donHangId)
        return response.data.data
    },
    async getDSVatTuTuDonHangTT({ commit, state }, donHangId) {
        let response = await API.getDSVatTuTuDonHangTT(this._vm.axios, donHangId)
        return response.data.data
    },
    async getLoaiDHVaTrangThaiDH({ commit, state }, donHangId) {
        let response = await API.getLoaiDHVaTrangThaiDH(this._vm.axios, donHangId)
        return response.data.data
    },
    async getChiTietDHCha({ commit, state }, donHangId) {
        let response = await API.getChiTietDHCha(this._vm.axios, donHangId)
        return response.data.data
    },

    async insertBackupDonHang({ commit, state }, data) {
        let response = await API.insertBackupDonHang(this._vm.axios, data)
        return response.data.data
    },

    async updateBackupDonHang({ commit, state }, data) {
        let response = await API.updateBackupDonHang(this._vm.axios, data)
        return response.data.data
    },

    async xoaChiTietDonHang({ commit, state }, data) {
        let response = await API.xoaChiTietDonHang(this._vm.axios, data)
        return response.data.data
    },

    async getNgayNghiemThuNhapKho({ commit, state }, donHangId) {
        let response = await API.getNgayNghiemThuNhapKho(this._vm.axios, donHangId)
        return response.data.data
    },
    async getDSQuyen({ commit, state }, nguoiDungId) {
        let response = await API.getDSQuyen(this._vm.axios, nguoiDungId)
        return response.data.data
    },
    async getThamSoMacDinh({ commit, state }, thamSo) {
        let response = await API.getThamSoMacDinh(this._vm.axios, thamSo)
        return response.data.data
    },
    async getDSTrangThaiDHHT({ commit, state },) {
        let response = await API.getDSTrangThaiDHHT(this._vm.axios,)
        return response.data.data
    },
    async updateChiTietDonHang({ commit, state }, data) {
        let response = await API.updateChiTietDonHang(this._vm.axios, data)
        return response.data.data || ""

    },
    async getDSDonViTinh({ commit, state },) {
        let response = await API.getDSDonViTinh(this._vm.axios,)
        return response.data.data || ""
    },

    async getDSNoiDung({ commit, state }, hdmsId) {
        let response = await API.getDSNoiDung(this._vm.axios, hdmsId)
        return response.data.data
    },
    async getTSMacDinh({ commit, state }, tsmd) {
        let response = await API.getTSMacDinh(this._vm.axios, tsmd)
        return response.data.data
    },
    async getDSThaoTac({ commit, state },) {
        let response = await API.getDSThaoTac(this._vm.axios,)
        return response.data.data
    },
    async kySoDonHang({ commit, state }, data) {
        let response = await API.kySoDonHang(this._vm.axios, data)
        return response.data.data
    },
    async getTaiKhoanKySo({ commit, state }, data) {
        let response = await API.getTaiKhoanKySo(this._vm.axios, data)
        return response.data.data
    },
    async kiemTraMaDonHang({ commit, state }, data) {
        let response = await API.kiemTraMaDonHang(this._vm.axios, data)
        return response.data.data
    },
    async capNhatMaDonHang({ commit, state }, data) {
        let response = await API.capNhatMaDonHang(this._vm.axios, data)
        return response.data.data
    },
}