
import api from "./DeclareMPLSAPI.js";


export default {
    namespaced: true,
    state: {},
    getters: {},
    mutations: {},
    actions: {
        fetchDanhSachKenhThu(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getDanhSachKenhThu(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },


        fetchDanhSachDoiTac(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getDanhSachDoiTac(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchLoaiHD(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getLoaiHD(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },

        fetchKieuHD(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getKieuHD(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },

        fetchLoaiCap(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getLoaiCap(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },

        fetchTocDoKenh(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getTocDoKenh(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },

        fetchLoaiHinhTB(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getLoaiHinhTB(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },

        fetchNhanVienGV(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getNhanvienGV(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchDonViCuoi(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getComboDonViCuoi(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchDanhSachChuQuan(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getDanhSachChuQuan(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },

        fetchDanhSachNhanVienID(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getDanhSachNhanVienID(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchDanhSachQuyTrinh(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getDanhSachQuyTrinh(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchDSQuyTrinh(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.ds_quytrinh(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchDanhSachHuongGiao(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getDanhSachHuongGiao(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },

        fetchDanhSachThueBao(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getDanhSachThueBao(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        
        fetchTongTien(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getTongTien(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchKenhThu(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getKenhThu(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchThongTinLH(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getThongTinLH(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchDanhSachThietBi(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getDanhSachThietBi(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchDSHDTBDBKieudv(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getDSHDTBDBKieudv(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchThongTinNhanVien(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getThongTinNhanVien(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchThongTinNhanVienDV(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getThongTinNhanVienDV(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchDanhSachHopDongThueBaoTLS(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getDanhSachHopDongThueBaoTLS(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchHT_PHIEU_TRA_TSL(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getHT_PHIEU_TRA_TSL(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        }, fetchKIEMTRA_HEN_HDTB(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getKIEMTRA_HEN_HDTB(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },fetchLay_DiaChi_LD(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getLay_DiaChi_LD(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchlydotra(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getlydotra(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchDSLuongThaoTac(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getDSLuongThaoTac(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchLAY_DS_NHANVIEN_THEO_PHIEU_ID(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getLAY_DS_NHANVIEN_THEO_PHIEU_ID(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchHuongGiaoByLuongID(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getHuongGiaoByLuongID(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchKiemTra_GiaoPhieu_VatTu(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getKiemTra_GiaoPhieu_VatTu(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },  fetchPortDauCuoi(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getPortDauCuoi(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        fetchCapnhat_tt_phieugiao(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.getCapnhat_tt_phieugiao(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },PostCapnhat_noidung_th(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.postCapnhat_noidung_th(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },

        PostHoanhThanh(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.postHoanhThanh(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        PostHoanThien(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.postHoanThien(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        }, PostCapNhat(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.postCapNhat(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        LAY_DS_THAMSO_MD_MATS(ctx, payload){
            return new Promise((resolve, reject) => {
                api.lay_ds_thamso_md_ds_mats(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
        MAP_ID(ctx, payload) {
            return new Promise((resolve, reject) => {
                api.map_id(this._vm.axios, payload)
                    .then(response => resolve(response))
                    .catch(error => reject(error))
            })
        },
    },

    

}