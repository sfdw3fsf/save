import API from '../../api/TraCuuVatTuThueBaoAPI'

export const actions = {    
    async getDSDichVuVienThong({ commit, state },data) {
        let response = await API.getDSDichVuVienThong(this._vm.axios,data)
        return response.data.data
    },

    async getDSLoaiHinhThueBao({ commit, state },dich_vu_id) {
        let response = await API.getDSLoaiHinhThueBao(this._vm.axios,dich_vu_id)
        return response.data.data
    },

    async getDanhBaTheoMaThueBao({ commit, state },data) {
        let response = await API.getDanhBaTheoMaThueBao(this._vm.axios,data)
        return response.data.data
    },

    async getDSVatTuThueBao({ commit, state },data) {
        let response = await API.getDSVatTuThueBao(this._vm.axios,data)
        return response.data.data
    },

    async getDSVatTuThueBao_phanvung_id({ commit, state },data) {
        let response = await API.getDSVatTuThueBao_phanvung_id(this._vm.axios,data)
        return response.data.data
    },

    async getBienDongVatTuThueBao({ commit, state },data) {
        let response = await API.getBienDongVatTuThueBao(this._vm.axios,data)
        return response.data.data
    },

    async getDanhBaTheoMaThanhToan({ commit, state },data) {
        let response = await API.getDanhBaTheoMaThanhToan(this._vm.axios,data)
        return response.data.data
    },

    async getDanhBaTheoKhachHangId({ commit, state },khach_hang_id) {
        let response = await API.getDanhBaTheoKhachHangId(this._vm.axios,khach_hang_id)
        return response.data.data
    },
    async getDSLichSuVatTu({ commit, state },data) {
        let response = await API.getDSLichSuVatTu(this._vm.axios,data)
        return response.data.data
    },
    async getDSLichSuVatTu_phanvung_id({ commit, state },data) {
        let response = await API.getDSLichSuVatTu_phanvung_id(this._vm.axios,data)
        return response.data.data
    },
    async getDSThamSoMacDinhTheoMaTS({ commit, state },data) {
        let response = await API.getDSThamSoMacDinhTheoMaTS(this._vm.axios,data)
        return response.data.data
    },
    async getHinhAnh({ commit, state },data) {
        let response = await API.getHinhAnh(this._vm.axios,data)
        return response.data.data
    },
    async downloadFile({ commit, state },data) {
        let response = await API.downloadFile(this._vm.axios,data)
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
    async deleteFile({ commit, state },data) {
        let response = await API.deleteFile(this._vm.axios,data)
        return response.data 
    },
}