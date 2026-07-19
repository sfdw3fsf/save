import API from '../../api/XetDuyetCongCuBaoHoAPI'

export const actions = {
    
    async getDSDonViTramThiCong({ commit, state },data) {
        let response = await API.getDSDonViTramThiCong(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSTiepNhan({ commit, state },data) {
        let response = await API.getDSTiepNhan(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    
    async getDSThamSoMacDinh({ commit, state },kieu) {
        let response = await API.getDSThamSoMacDinh(this._vm.axios,kieu)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

   
    async getDSChiTietCCDC({ commit, state },phieuId) {
        let response = await API.getDSChiTietCCDC(this._vm.axios,phieuId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
   
    async duyetPhieuCapCCDC({ commit, state },data) {
        let response = await API.duyetPhieuCapCCDC(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async capNhatGhiChu({ commit, state },data) {
        let response = await API.capNhatGhiChu(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async uploadFile({ commit, state },data) {
        let response = await API.uploadFile(this._vm.axios,data)
        try {
            return response.data.data || {}
        } catch (error) {
            throw error
        }
        return response
    },
    async downloadFile({ commit, state },linkFile) {
        let response = await API.downloadFile(this._vm.axios,linkFile)
        try {
            return response.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getLinkFile({ commit, state },data) {
        let response = await API.getLinkFile(this._vm.axios,data)
        try {
            return response.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async checkFileExist({ commit, state },data) {
        let response = await API.checkFileExist(this._vm.axios,data)
        try {
            return response.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async deleteFile({ commit, state },data) {
        let response = await API.deleteFile(this._vm.axios,data)
        try {
            return response.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async downloadImageFromUrl({ commit, state },data) {
        let response = await API.downloadImageFromUrl(this._vm.axios,data)
        try {
            return response.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async checkCodeUrl({ commit, state },data) {
        let response = await API.checkCodeUrl(this._vm.axios,data)
        try {
            return response.data.data || 0
        } catch (error) {
            throw error
        }
        return response
    },

    async capNhatChiTietDungCu({ commit, state },data) {
        let response = await API.capNhatChiTietDungCu(this._vm.axios,data)
        try {
            return response.data || []
        } catch (error) {
            throw error
        }
        return response
    },

}