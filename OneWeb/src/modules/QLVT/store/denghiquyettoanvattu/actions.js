import API from '../../api/DeNghiQuyetToanVatTuAPI'

export const actions = {    
    async getDSDichVu({ commit, state },) {
        let response = await API.getDSDichVu(this._vm.axios,)
        try {
            return response.data.data || []
        } catch (error) {
            return response.data.message
        }    
    },
    async getDSNhanVien({ commit, state },data) {
        let response = await API.getDSNhanVien(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            return response.data.message
        }    
    },
    async getDSKieuLapDat({ commit, state },) {
        let response = await API.getDSKieuLapDat(this._vm.axios,)
        try {
            return response.data.data || []
        } catch (error) {
            return response.data.message
        }    
    },
    async getDSDeNghiQuyetToan({ commit, state },data) {
        let response = await API.getDSDeNghiQuyetToan(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            return response.data.message
        }        
    },
    async getDSKhoTheoNV({ commit, state },data) {
        let response = await API.getDSKhoTheoNV(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }    
    },
    async getDSLoaiHinhThueBao({ commit, state },dichVuId) {
        let response = await API.getDSLoaiHinhThueBao(this._vm.axios,dichVuId)
        try {            
            return response.data.data || []
        } catch (error) {          
            return response.data.message
        }       
    },
    async getDSLyDoCap({ commit, state },data) {
        let response = await API.getDSLyDoCap(this._vm.axios,data)
        try {
            
            return response.data.data || []
        } catch (error) {
            return response.data.message
        }
        
    },
    async capNhatQuyetToanChungTu({ commit, state },data) {
        let response = await API.capNhatQuyetToanChungTu(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            return response.data.message
        }
    },
    async ghiLaiDeNghiQuyetToan({ commit, state },data) {
        let response = await API.ghiLaiDeNghiQuyetToan(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            return response.data.message
        }
    },
    async xoaDeNghiQuyetToan({ commit, state },quyetToanId) {
        let response = await API.xoaDeNghiQuyetToan(this._vm.axios,quyetToanId)
        try {
            return response.data.data || []
        } catch (error) {
            return response.data.message
        }
    },
    async kiemTraQuyetToanChungTu({ commit, state },quyetToanId) {
        let response = await API.kiemTraQuyetToanChungTu(this._vm.axios,quyetToanId)
        try {
            return response.data.data || []
        } catch (error) {
            return response.data.message
        }
    },
    async layQuyTrinhHuongGiao({ commit, state },loaiPhieuId) {
        let response = await API.layQuyTrinhHuongGiao(this._vm.axios,loaiPhieuId)
        try {
            return response.data.data || []
        } catch (error) {
            return response.data.message
        }
    },
    async layHuongGiaoTiepTheo({ commit, state },huongGiaoId) {
        let response = await API.layHuongGiaoTiepTheo(this._vm.axios,huongGiaoId)
        try {
            return response.data.data || []
        } catch (error) {
            return response.data.message
        }
    },
    async getDSBaoCaoQuyetToan({ commit, state },data) {
        let response = await API.getDSBaoCaoQuyetToan(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            return response.data.message
        }
    },
    async getDSBaoCaoThuHoi({ commit, state },data) {
        let response = await API.getDSBaoCaoThuHoi(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            return response.data.message
        }
    },
}