import API from '../../api/CapVatTuPTMTTAPI'

export const actions = {
    async getDsQuyenND({ commit, state }, data) {
        try {
            let response = await API.getDsQuyenND(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getDSNhanVienGiaoNhan({ commit, state }, data) {
        try {
            let response = await API.getDSNhanVienGiaoNhan(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getDSMucDichLyDo({ commit, state }, data) {
        try {
            let response = await API.getDSMucDichLyDo(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getDanhMucCapVatTuPTMTT({ commit, state }, data) {
        try {
            let response = await API.getDanhMucCapVatTuPTMTT(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getDSHsccPTM({ commit, state }, data) {
        try {
            let response = await API.getDSHsccPTM(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getDSGVCaiTaoBaoDuong({ commit, state }, data) {
        try {
            let response = await API.getDSGVCaiTaoBaoDuong(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },

    async getDSTamUngVT({ commit, state }, data) {
        try {
            let response = await API.getDSTamUngVT(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getDSVatTuSDPTM({ commit, state }, data) {
        try {
            let response = await API.getDSVatTuSDPTM(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async KiemTraTinhTrangHongVT({ commit, state }, data) {
        try {
            let response = await API.KiemTraTinhTrangHongVT(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getDSThietBiDaCap({ commit, state }, data) {
        try {
            let response = await API.getDSThietBiDaCap(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getDSThietBi({ commit, state }, data) {
        try {
            let response = await API.getDSThietBi(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },

    async KiemTraThuHoi({ commit, state }, data) {
        try {
            let response = await API.KiemTraThuHoi(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async KiemTraPTMSuDungVT({ commit, state }, data) {
        try {
            let response = await API.KiemTraPTMSuDungVT(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getDSVatTu({ commit, state }, data) {
        try {
            let response = await API.getDSVatTu(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async SoLuongVatTuPTM({ commit, state }, data) {
        try {
            let response = await API.SoLuongVatTuPTM(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async CapNhatSDVT({ commit, state }, data) {
        try {
            let response = await API.CapNhatSDVT(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },

    async SoLuongThuHoiVTTheoPhieu({ commit, state }, data) {
        try {
            let response = await API.SoLuongThuHoiVTTheoPhieu(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async SoLuongThuHoiVT({ commit, state }, data) {
        try {
            let response = await API.SoLuongThuHoiVT(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async CapNhatPhieuVTThuHoi({ commit, state }, data) {
        try {
            let response = await API.CapNhatPhieuVTThuHoi(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async KiemTraVTCapRa({ commit, state }, data) {
        try {
            let response = await API.KiemTraVTCapRa(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async KiemTraDayCTBD({ commit, state }, data) {
        try {
            let response = await API.KiemTraDayCTBD(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },

    async getKeys({ commit, state }, data) {
        try {
            let response = await API.getKeys(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async InsertPhieuVT({ commit, state }, data) {
        try {
            let response = await API.InsertPhieuVT(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getDSPhieuVTPTM({ commit, state }, data) {
        try {
            let response = await API.getDSPhieuVTPTM(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getThamSoMacDinh({ commit, state }, data) {
        try {
            let response = await API.getThamSoMacDinh(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getDSKhoTheoNV({ commit, state }, data) {
        try {
            let response = await API.getDSKhoTheoNV(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },

    async getDSHinhAnh({ commit, state }, data) {
        try {
            let response = await API.getDSHinhAnh(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async XoaThongTinHinhAnh({ commit, state }, data) {
        try {
            let response = await API.XoaThongTinHinhAnh(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async CapNhatThuHoiVatTuV2({ commit, state }, data) {
        try {
            let response = await API.CapNhatThuHoiVatTuV2(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getPhieuVTHscc({ commit, state }, data) {
        try {
            let response = await API.getPhieuVTHscc(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getPhieuKhongVT({ commit, state }, data) {
        try {
            let response = await API.getPhieuKhongVT(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },

    async DelUpdateLyDo({ commit, state }, data) {
        try {
            let response = await API.DelUpdateLyDo(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async GiaoPhieuTdQTVT({ commit, state }, data) {
        try {
            let response = await API.GiaoPhieuTdQTVT(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getDSTinhTrangThietBiVT({ commit, state }, data) {
        try {
            let response = await API.getDSTinhTrangThietBiVT(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getDSTinhTrangVT({ commit, state }, data) {
        try {
            let response = await API.getDSTinhTrangVT(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getVaTuTheoSerial({ commit, state }, data) {
        try {
            let response = await API.getVaTuTheoSerial(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },

    async HuyGiaoHsccPTM({ commit, state }, data) {
        try {
            let response = await API.HuyGiaoHsccPTM(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getDSLyDoTon({ commit, state }, data) {
        try {
            let response = await API.getDSLyDoTon(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },

    async UploadFiles({ commit, state }, data) {
        try {
            let response = await API.UploadFiles(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },

    async DeleteFile({ commit, state }, data) {
        try {
            let response = await API.DeleteFile(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    
    async PresignedUrl({ commit, state }, data) {
        try {
            let response = await API.PresignedUrl(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },

    
}