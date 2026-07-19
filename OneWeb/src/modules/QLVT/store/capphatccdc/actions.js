import API from "../../api/CapPhatCCDCAPI";

export const actions = {
    async getKey ({ commit, state }, data) {
        let response = await API.getKey(this._vm.axios, data)
        return response.data.data || ""
    },
    async getTinhTrangTBi ({ commit, state }, data) {
        let response = await API.getTinhTrangTBi(this._vm.axios, data)
        try {
            commit("setDSTinhTrangTBi", response.data.data || []);
          } catch (error) {
            throw error;
          }
        return response
    },
    async getMucDichPhanBo ({ commit, state }, data) {
        let response = await API.getMucDichPhanBo(this._vm.axios, data)
        try {
            commit("setDSMucDichPhanBo", response.data.data || []);
          } catch (error) {
            throw error;
          }
        return response
    },
    async getDSDonVi ({ commit, state }, data) {
        let response = await API.getDSDonVi(this._vm.axios, data)
        try {
            // commit("setDSDonVi", response.data.data || []);
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getChuoiDonVi ({ commit, state }, data) {
        let response = await API.getChuoiDonVi(this._vm.axios, data)
        return response
    },
    async getDSNhanVien ({ commit, state }, data) {
        let response = await API.getDSNhanVien(this._vm.axios, data)
        try {
            // commit("setDSNhanVien", response.data.data || []);
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async ThemMoiChungTuCCDC ({ commit, state }, data) {
        let response = await API.ThemMoiChungTuCCDC(this._vm.axios, data)
        try {
            return response.data.data || ""
        } catch (error) {
            throw error;
        }
        return reponse
    },
    async CapNhatChungTuCCDC ({ commit, state }, data) {
        let response = await API.CapNhatChungTuCCDC(this._vm.axios, data)
        try {
            return response.data.data || ""
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSCapCCDC ({ commit, state }, data) {
        let response = await API.getDSCapCCDC(this._vm.axios, data)
        try {
            // commit("setDSCapCCDC", response.data.data || []);
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSChiTietCCDC ({ commit, state }, data) {
        let response = await API.getDSChiTietCCDC(this._vm.axios, data)
        try {
            // commit("setDSChiTietCCDC", response.data.data || []);
            return response.data.data || []
          } catch (error) {
            throw error;
          }
        return response
    },
    async getDSTongHopNXCC ({ commit, state }, data) {
        let response = await API.getDSTongHopNXCC(this._vm.axios, data)
        try {
            // commit("setDSTongHopCCDC", response.data.data || []);
            return response.data.data || []
          } catch (error) {
            throw error;
          }
        return response
    },
    async getNgayChungTu ({ commit, state }, data) {
        let response = await API.getNgayChungTu(this._vm.axios, data)
        return response.data.data || []
    },
    async HoanThienCCDC ({ commit, state }, data) {
        let response = await API.HoanThienCCDC(this._vm.axios, data)
        return  response.data.data
    },
    async XoaChungTuCCDC ({ commit, state }, data) {
        let response = await API.XoaChungTuCCDC(this._vm.axios, data)
        return  response.data.data
    },
    async CapNhatGhiChuNXCC ({ commit, state }, data) {
        let response = await API.CapNhatGhiChuNXCC(this._vm.axios, data)
        return  response.data.data
    },
    async getMaDVTheoMaNV ({ commit, state }, data) {
        let response = await API.getMaDVTheoMaNV(this._vm.axios, data)
        return  response.data.data
    },
    async getLoaiThietBiTheoVT ({ commit, state }, data) {
        let response = await API.getLoaiThietBiTheoVT(this._vm.axios, data)
        return  response.data.data
    },
    async getThongTinID ({ commit, state }, data) {
        let response = await API.getThongTinID(this._vm.axios, data)
        return  response.data.data
    },
    async CapNhatThongTinThietBi ({ commit, state }, data) {
        let response = await API.CapNhatThongTinThietBi(this._vm.axios, data)
        return  response.data.data
    },

    async xemBaoCao ({ commit, state }, data) {
        let response = await API.xemBaoCao(this._vm.axios, data)
        return response
    },
    async getDSNhanVienTheoDonVi ({ commit, state }, data) {
        let response = await API.getDSNhanVienTheoDonVi(this._vm.axios, data)
        return  response.data.data
    },
};

