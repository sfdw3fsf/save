import API from '../../api/XacNhanDeNghiAPI'

export const actions = {
    async getDSLoaiPhieu({ commit, state },data) {
        let response = await API.getDSLoaiPhieu(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getDSThamSoMacDinh({ commit, state },kieu) {
        let response = await API.getDSThamSoMacDinh(this._vm.axios,kieu)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getDSDeNghiQuyetToan({ commit, state },kieu) {
        let response = await API.getDSDeNghiQuyetToan(this._vm.axios,kieu)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getDSThamSoMacDinhTheoMaTS({ commit, state },data) {
        let response = await API.getDSThamSoMacDinhTheoMaTS(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getDSDeNghiChungTu({ commit, state },deNghiId) {
        let response = await API.getDSDeNghiChungTu(this._vm.axios,deNghiId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getDSDeNghiQuyetToanChungTu({ commit, state },quyetToanId) {
        let response = await API.getDSDeNghiQuyetToanChungTu(this._vm.axios,quyetToanId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getHuongGiaoTiepTheo({ commit, state },huongGiaoId) {
        let response = await API.getHuongGiaoTiepTheo(this._vm.axios,huongGiaoId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async traPhieuChiTiet({ commit, state },data) {
        let response = await API.traPhieuChiTiet(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async hoanThanhPhieu({ commit, state },phieuId) {
        let response = await API.hoanThanhPhieu(this._vm.axios,phieuId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async downloadFile({ commit, state },linkFile) {
        let response = await API.downloadFile(this._vm.axios,linkFile)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async checkFileExist({ commit, state },data) {
        let response = await API.checkFileExist(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
}