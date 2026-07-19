import API from '../../api/NhapDeNghiVatTuAPI'

export const actions = {
    async getDSKhoDenNghiVT ({ commit, state }) {
        let response = await API.getDSKhoDenNghiVT(this._vm.axios)
        return response.data.data
    },
    async getDSKhoDonViVT ({ commit, state }) {
        let response = await API.getDSKhoDonViVT(this._vm.axios)
        return response.data.data
    },
    async getDSVatTuMucDich ({ commit, state }) {
        let response = await API.getDSVatTuMucDich(this._vm.axios)
        return response.data.data
    },
    async getDSThamSoMacDinh ({ commit, state }) {
        let response = await API.getDSThamSoMacDinh(this._vm.axios)
        return response.data.data
    },
    async getDSLoaiPhieu ({ commit, state },data) {
        let response = await API.getDSLoaiPhieu(this._vm.axios,data)
        return response.data.data
    },
    async getDSDangKyVT ({ commit, state },data) {
        let response = await API.getDSDangKyVT(this._vm.axios,data)
        return response.data.data
    },
    async getDSVatTuDaDk ({ commit, state },denghi_id) {
        let response = await API.getDSVatTuDaDk(this._vm.axios,denghi_id)
        return response.data.data
    },
    async xoaDeNghi ({ commit, state },denghi_id) {
        let response = await API.xoaDeNghi(this._vm.axios,denghi_id)
        return response.data.data
    },
    async getQuyTrinhHuongGiaoTheoDeNghiId ({ commit, state },denghi_id) {
        let response = await API.getQuyTrinhHuongGiaoTheoDeNghiId(this._vm.axios,denghi_id)
        return response.data.data
    },
    async getQuyTrinhHuongGiaoTiepTheo ({ commit, state },huongGiaoId) {
        let response = await API.getQuyTrinhHuongGiaoTiepTheo(this._vm.axios,huongGiaoId)
        return response.data.data
    },
    async capNhatDenghi ({ commit, state },data) {
        let response = await API.capNhatDenghi(this._vm.axios,data)
        return response.data.data
    },
    async getKey ({ commit, state },data) {
        let response = await API.getKey(this._vm.axios,data)
        return response.data.data
    },
    async getHuongGiaoTiepTheo ({ commit, state },denghi_id) {
        let response = await API.getHuongGiaoTiepTheo(this._vm.axios,denghi_id)
        return response.data.data      
    },
    async getDSDonViFMIS ({ commit, state },) {
        let response = await API.getDSDonViFMIS(this._vm.axios,)
        return response.data.data        
    },
    async getDSDeNghiV2 ({ commit, state },data) {
        let response = await API.getDSDeNghiV2(this._vm.axios,data)
        return response.data.data        
    },
    async getDSChungTuHoanUng ({ commit, state },data) {
        let response = await API.getDSChungTuHoanUng(this._vm.axios,data)
        return response.data.data        
    },
    async xoaDeNghiChungTu ({ commit, state },data) {
        let response = await API.xoaDeNghiChungTu(this._vm.axios,data)
        return response.data.data        
    },
    async kiemTraHanChungTuV3 ({ commit, state },data) {
        let response = await API.kiemTraHanChungTuV3(this._vm.axios,data)
        return response.data.data        
    },
    async dongBoDeNghi ({ commit, state },data) {
        let response = await API.dongBoDeNghi(this._vm.axios,data)
        return response.data.data        
    },
    async capNhatDongBoChungTu ({ commit, state },data) {
        let response = await API.capNhatDongBoChungTu(this._vm.axios,data)
        return response.data.data        
    },
    async xoaChungTuDongBo ({ commit, state },data) {
        let response = await API.xoaChungTuDongBo(this._vm.axios,data)
        return response.data.data        
    },
    async getLinkFile({ commit, state },data) {
        let response = await API.getLinkFile(this._vm.axios,data)
        return response.data 
    },
    async checkFileExist({ commit, state },data) {
        let response = await API.checkFileExist(this._vm.axios,data)
        return response.data 
    },
    async getDSFile ({ commit, state },denghi_id) {
        let response = await API.getDSFile(this._vm.axios,denghi_id)
        return response.data.data      
    },

    async capNhatDenghiV2 ({ commit, state },data) {
        let response = await API.capNhatDenghiV2(this._vm.axios,data)
        return response.data.data
    },
    async getDSDonViDeNghi ({ commit, state }, ) {
        let response = await API.getDSDonViDeNghi(this._vm.axios, )       
        return response.data.data         
    },
    async getDSTinh ({ commit, state },data) {
        let response = await API.getDSTinh(this._vm.axios,data)
        return response.data.data
    },
    async themSuaXoaDieuChuyen ({ commit, state }, data) {
        let response = await API.themSuaXoaDieuChuyen(this._vm.axios, data)       
        return response.data.data         
    },
    async getMaBC ({ commit, state }, baocao_id) {
        let response = await API.getMaBC(this._vm.axios, baocao_id)       
        return response.data.data         
    },

    async getDSDeNghiLuanChuyen ({ commit, state },data) {
        let response = await API.getDSDeNghiLuanChuyen(this._vm.axios,data)
        return response.data.data        
    },
}
