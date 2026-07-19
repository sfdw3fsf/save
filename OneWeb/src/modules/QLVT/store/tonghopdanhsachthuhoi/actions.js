import API from '../../api/TongHopDanhSachThuHoiAPI'

export const actions = {
    async getDSDonVi ({ commit, state }, data) {
        let response = await API.getDSDonVi(this._vm.axios, data)
        return response.data.data
    },
    async getDSKhoTheoNV ({ commit, state }, data) {
        let response = await API.getDSKhoTheoNV(this._vm.axios, data)
        return response.data.data
    },
    async getDSKhoKhachHang ({ commit, state }, data) {
        let response = await API.getDSKhoKhachHang(this._vm.axios, data)
        return response.data.data
    },
    async getDSDichVuVienThong ({ commit, state }, data) {
        let response = await API.getDSDichVuVienThong(this._vm.axios, data)
        return response.data.data
    },

    async getDSVatTuTimKiem ({ commit, state }, data) {
        let response = await API.getDSVatTuTimKiem(this._vm.axios, data)
        return response.data.data
    },
    async getDanhMucHopDong ({ commit, state }, data) {
        let response = await API.getDanhMucHopDong(this._vm.axios, data)
        return response.data.data
    },
    async getDSThueBaoThuHoi ({ commit, state }, data) {
        let response = await API.getDSThueBaoThuHoi(this._vm.axios, data)
        return response.data.data
    },
    async getDSVatTuThuHoi ({ commit, state }, data) {
        let response = await API.getDSVatTuThuHoi(this._vm.axios, data)
        return response.data.data
    },

    async getDSLichSuVatTuThuHoi ({ commit, state }, data) {
        let response = await API.getDSLichSuVatTuThuHoi(this._vm.axios, data)
        return response.data.data
    },
    async ThemMoiPhieuVatTu ({ commit, state }, data) {
        let response = await API.ThemMoiPhieuVatTu(this._vm.axios, data)
        return response.data.data
    },
    async KiemTraTraPhieu ({ commit, state }, data) {
        let response = await API.KiemTraTraPhieu(this._vm.axios, data)
        return response.data.data
    },
    async TraPhieuQuyetToanVatTu ({ commit, state }, data) {
        let response = await API.TraPhieuQuyetToanVatTu(this._vm.axios, data)
        return response.data.data
    },

    async HuyPhieuQuyetToan ({ commit, state }, data) {
        let response = await API.HuyPhieuQuyetToan(this._vm.axios, data)
        return response.data.data
    },
    async KiemTraVatTuNhapKho ({ commit, state }, data) {
        let response = await API.KiemTraVatTuNhapKho(this._vm.axios, data)
        return response.data.data
    },
    async getDSMucDichBaoHong ({ commit, state }, data) {
        let response = await API.getDSMucDichBaoHong(this._vm.axios, data)
        return response.data.data
    },
    async CapMaVatTuPhieu_V2 ({ commit, state }, data) {
        let response = await API.CapMaVatTuPhieu_V2(this._vm.axios, data)
        return response.data.data
    },

    async CapNhatMaPhieuVatTu ({ commit, state }, data) {
        let response = await API.CapNhatMaPhieuVatTu(this._vm.axios, data)
        return response.data.data
    },
    async getDSVatTuDanhMuc ({ commit, state }, data) {
        let response = await API.getDSVatTuDanhMuc(this._vm.axios, data)
        return response.data.data
    },
    async getDSKhoDonVi ({ commit, state }, data) {
        let response = await API.getDSKhoDonVi(this._vm.axios, data)
        return response.data.data
    },
    async LAY_DS_THUHOI_QT ({ commit, state }, data) {
        let response = await API.LAY_DS_THUHOI_QT(this._vm.axios, data)
        return response.data.data
    },
    async DS_LECH_PHIEUVT_QUYETTOAN ({ commit, state }, data) {
        let response = await API.DS_LECH_PHIEUVT_QUYETTOAN(this._vm.axios, data)
        return response.data.data
    },
    async LAY_DS_DENGHI_HT ({ commit, state }, data) {
        let response = await API.LAY_DS_DENGHI_HT(this._vm.axios, data)
        return response.data.data
    },
    async CHECK_IS_EXIST ({ commit, state }, data) {
        let response = await API.CHECK_IS_EXIST(this._vm.axios, data)
        return response.data.data
    },

    async KT_SL_PHIEUVT_QUYETTOAN ({ commit, state }, data) {
        let response = await API.KT_SL_PHIEUVT_QUYETTOAN(this._vm.axios, data)
        return response.data.data
    },

    async getTSMacDinh ({ commit, state }, data) {
        let response = await API.getTSMacDinh(this._vm.axios, data)
        return response.data.data
    },
}