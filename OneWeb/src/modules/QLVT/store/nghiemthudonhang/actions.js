import API from '../../api/NghiemThuDonHangAPI'

export const actions = {    
    async getDSVatTuTuDonHang({ commit, state },donHangId) {
        let response = await API.getDSVatTuTuDonHang(this._vm.axios,donHangId)
        return response.data.data
    },
    async getDSFileNghiemThu({ commit, state },donHangId) {
        let response = await API.getDSFileNghiemThu(this._vm.axios,donHangId)
        return response.data.data
    },
    async KTDonHangDaCoChungTu({ commit, state },donHangId) {
        let response = await API.KTDonHangDaCoChungTu(this._vm.axios,donHangId)
        return response.data.data
    },
    async xoaNghiemThu({ commit, state },donHangId) {
        let response = await API.xoaNghiemThu(this._vm.axios,donHangId)
        return response.data.data
    },
    async capNhatHopDong({ commit, state },data) {
        let response = await API.capNhatHopDong(this._vm.axios,data)
        return response.data.data
    },
    async capNhatTTNghiemThuDonHang({ commit, state },data) {
        let response = await API.capNhatTTNghiemThuDonHang(this._vm.axios,data)
        return response.data.data
    },
    async getTSMacDinh({ commit, state },tsmd) {
        let response = await API.getTSMacDinh(this._vm.axios,tsmd)
        try {
            return response.data.data 
        } catch (error) {
            throw error
        }
        return response
    },

    async insertBackupDonHang({ commit, state },data) {
        let response = await API.insertBackupDonHang(this._vm.axios,data)
        return response.data.data
    },

    async getDSVatTuNghiemThu({ commit, state },hdmsId) {
        let response = await API.getDSVatTuNghiemThu(this._vm.axios,hdmsId)
        return response.data.data
    },

    async getTTHopDongDonHang({ commit, state },data) {
        let response = await API.getTTHopDongDonHang(this._vm.axios,data)
        return response.data.data
    },
    async getDSChiTietDonHang({ commit, state },donHangId) {
        let response = await API.getDSChiTietDonHang(this._vm.axios,donHangId)
        return response.data.data
    },
    async getDSDonViTinh({ commit, state },) {
        let response = await API.getDSDonViTinh(this._vm.axios,)
        return response.data.data
    },

    async uploadFile({ commit, state },data) {
        let response = await API.uploadFile(this._vm.axios,data)
        return response.data.data
    },
    async downloadFile({ commit, state },linkFile) {
        let response = await API.downloadFile(this._vm.axios,linkFile)
        return response.data
    },
    async getLinkFile({ commit, state },data) {
        let response = await API.getLinkFile(this._vm.axios,data)
        return response.data
    },
    async checkFileExist({ commit, state },data) {
        let response = await API.checkFileExist(this._vm.axios,data)
        return response.data
    },
    async capNhatFile({ commit, state },data) {
        let response = await API.capNhatFile(this._vm.axios,data)
        return response.data
    },
    async deleteFile({ commit, state },data) {
        let response = await API.deleteFile(this._vm.axios,data)
        return response.data
    },
    async capNhatNgayNghiemThuBanGiao({ commit, state },data) {
        let response = await API.capNhatNgayNghiemThuBanGiao(this._vm.axios,data)
        return response.data.data
    },
    async getNgayNghiemThuBanGiao({ commit, state },donHangId) {
        let response = await API.getNgayNghiemThuBanGiao(this._vm.axios,donHangId)
        return response.data.data
    },
}