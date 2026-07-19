import API from '../../api/QuyetToanVatTuAPI'

export const actions = {
    async getDSDonViTheoDichVu({ commit, state }, data) {
        let response = await API.getDSDonViTheoDichVu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSDichVuVatTu({ commit, state }, ) {
        let response = await API.getDSDichVuVatTu(this._vm.axios, )
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSThueBaoCapVatTu({ commit, state },data) {
        let response = await API.getDSThueBaoCapVatTu(this._vm.axios,data)
        console.log(response , " = response")
        return response
    },
    async getDSLoaiHopDong({ commit, state }, ) {
        let response = await API.getDSLoaiHopDong(this._vm.axios, )
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSKhoTheoNhanVienId({ commit, state }, nhanVienId) {
        let response = await API.getDSKhoTheoNhanVienId(this._vm.axios, nhanVienId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSVatTu({ commit, state },data) {
        let response = await API.getDSVatTu(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSVatTuQuyetToan({ commit, state },data) {
        let response = await API.getDSVatTuQuyetToan(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async huyTraPhieuQuyetToan({ commit, state },data) {
        let response = await API.huyTraPhieuQuyetToan(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSPhieuVTThueBao({ commit, state },data) {
        let response = await API.getDSPhieuVTThueBao(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async quyetToanVatTuV2({ commit, state },data) {
        let response = await API.quyetToanVatTuV2(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async huyQuyetToanVatTu({ commit, state },data) {
        let response = await API.huyQuyetToanVatTu(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getMucDichXuat({ commit, state },mucDichXuat) {
        let response = await API.getMucDichXuat(this._vm.axios,mucDichXuat)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSDonViNhan({ commit, state },) {
        let response = await API.getDSDonViNhan(this._vm.axios,)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async kiemTraHanChungTu({ commit, state },data) {
        let response = await API.kiemTraHanChungTu(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async taoPhieuChungTuV2({ commit, state },data) {
        let response = await API.taoPhieuChungTuV2(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async quyetToanVatTuThueBao({ commit, state },data) {
        let response = await API.quyetToanVatTuThueBao(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async xoaChungTu({ commit, state },chungTuId) {
        let response = await API.xoaChungTu(this._vm.axios,chungTuId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async xoaPhieuVatTu({ commit, state },data) {
        let response = await API.xoaPhieuVatTu(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async xoaAllChiTietPhieuVatTu({ commit, state },chungTuId) {
        let response = await API.xoaAllChiTietPhieuVatTu(this._vm.axios,chungTuId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async kiemTraPhieuTra({ commit, state },data) {
        let response = await API.kiemTraPhieuTra(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSPhieuKDCSUA({ commit, state },data) {
        let response = await API.getDSPhieuKDCSUA(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async kiemTraSuDungVatTu({ commit, state },thueBaoId) {
        let response = await API.kiemTraSuDungVatTu(this._vm.axios,thueBaoId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async kiemTraSuaVatTu({ commit, state },data) {
        let response = await API.kiemTraSuaVatTu(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSKhoTheoNhanVien({ commit, state },data) {
        let response = await API.getDSKhoTheoNhanVien(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSKhoTheoDonVi({ commit, state },data) {
        let response = await API.getDSKhoTheoDonVi(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }        
    },
    async getTSMacDinh({ commit, state },tsmd) {
        let response = await API.getTSMacDinh(this._vm.axios,tsmd)
        try {
            return response.data.data
        } catch (error) {
            throw error
        }
    },
    async huyQuyetToanGiaoPhieuVatTu({ commit, state },data) {
        let response = await API.huyQuyetToanGiaoPhieuVatTu(this._vm.axios,data)
        try {
            return response.data.data 
        } catch (error) {
            throw error
        }
    },
    async quyetToanVatTuTheoDonVi({ commit, state },data) {
        let response = await API.quyetToanVatTuTheoDonVi(this._vm.axios,data)
        try {
            return response.data.data
        } catch (error) {
            throw error
        }
    },
    async taoPhieuChungTuGiaoPhieu({ commit, state },data) {
        let response = await API.taoPhieuChungTuGiaoPhieu(this._vm.axios,data)
        try {
            return response.data.data
        } catch (error) {
            throw error
        }
    },
    async capNhatChungTuQuyetToan({ commit, state },data) {
        let response = await API.capNhatChungTuQuyetToan(this._vm.axios,data)
        try {
            return response.data.data
        } catch (error) {
            throw error
        }
    },
    async getDSTimKiemNangCao({ commit, state },) {
        let response = await API.getDSTimKiemNangCao(this._vm.axios,)        
        return response.data.data       
    },
    async getDSQuyetToanNangCao({ commit, state },data) {
        let response = await API.getDSQuyetToanNangCao(this._vm.axios,data)        
        return response.data.data       
    },
    async hoanTamUng({ commit, state },data) {
        let response = await API.hoanTamUng(this._vm.axios,data)        
        return response.data.data       
    },
    async getDSKhoTheoCBoDonVi({ commit, state },data) {
        let response = await API.getDSKhoTheoCBoDonVi(this._vm.axios,data)        
        return response.data.data       
    },
}