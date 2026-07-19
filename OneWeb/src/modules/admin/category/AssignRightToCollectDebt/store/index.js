import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        dsDonVi: [],
        cbQuyenGN: [],
        // tab danh sach nhom nd
        grcThoiGian: [],
        grcThoiGian_temp: [],
        grcNgay_ND_DG: [],
        grcNgay_ND_CG: [],
        // vnhom_nd_id: 0,
        vnhom_id: 0,  // get nhom_id tu 1 row bang thoi gian thiet lap

        // tab danh sach quyen nguoi dung
        vnguoidung: 0,
        grcNguoiDung: [],
        grcThoiGian_ND: [],
        grcThoiGian_ND_temp: [],
        grcNgayDaGan: [],
        grcNgayChuaGan: [],
        grcNguoiDungCG: [],
        vnd_id: 0, // get nhom_id tu 1 row bang thoi gian thiet lap

        // common
        vkieu: null, // cboDs_quyen tab danh sách quyền nhóm ND
        vkieu_nd: null, // cboQuyenGN tab danh sách quyền người dùng
        vnhom_nd_id: 0, // DS quyen nhom ND

        vnguoidung_id: null,
        fieldsDonVi: null,
    },
    getters: {
        filterDonVi: (state) => (id) => {
            let newDSDV = state.dsDonVi.filter(item => {
                return item.id == id;
            });
            // console.log(newDSDV);
            return newDSDV;
        },
        getfieldsDonVi: state => {
            return state.fieldsDonVi;
        },
        getNgayCG_TAB_QUYEN_ND: state => {
            return state.grcNgayChuaGan;
        },
        getNgayDG_TAB_QUYEN_ND: state => {
            return state.grcNgayDaGan
        },
        getgrcNguoiDung: state => {
            return state.grcNguoiDung;
        },
        getgrcNgay_ND_CG: state => {
            return state.grcNgay_ND_CG;
        },
        getgrcNgay_ND_DG: state => {
            return state.grcNgay_ND_DG;
        },
        getThoiGian: state => {
            return state.grcThoiGian;
        },
        getThoiGianTemp: state => {
            return state.grcThoiGian_temp;
        },
        getThoiGian_ND: state => {
            return state.grcThoiGian_ND;
        },
        getThoiGian_ND_Temp: state => {
            return state.grcThoiGian_ND_temp;
        },

    },
    mutations: {
        // common
        SET_DONVI(state, donvi) {
            state.dsDonVi = donvi
        },
        SET_DONVI_UPDATE(state, donvi) {
            console.log(donvi);
            state.fieldsDonVi = donvi
        },
        SET_QUYENGN(state, quyenGN) {
            state.cbQuyenGN = quyenGN
        },

        SET_QUYENGN_TO_STATE(state, vkieu) {
            state.vkieu = vkieu
        },
        SET_QUYENGN_TAB_QUYEN_ND(state, vkieu) {
            state.vkieu_nd = vkieu
        },

        SET_NHOM_ND(state, nhom_nd) {
            state.vnhom_nd_id = nhom_nd
        },
        SET_NGUOIDUNG_ID(state, vnguoidung_id) {
            state.vnguoidung_id = vnguoidung_id;
        },
        // Hiển thị lưới thời gian thiết lập, tab danh sách quyền nhóm ND
        SET_DATASOURCE_TGTT(state, ThoiGian) {
            state.grcThoiGian = ThoiGian
        },
        SET_DATASOURCE_TGTT_TEMP(state, ThoiGian_temp) {
            state.grcThoiGian_temp = ThoiGian_temp
        },
        SET_DATASOURCE_NGAY_DG(state, Ngay_DG) {
            state.grcNgay_ND_DG = Ngay_DG
        },
        SET_DATASOURCE_NGAY_CG(state, Ngay_CG) {
            state.grcNgay_ND_CG = Ngay_CG
        },
        SET_NHOM_ID(state, nhom_id) {
            state.vnhom_id = nhom_id
        },
        // // Hiển thị lưới thời gian thiết lập, tab danh sách quyền người dùng
        SET_DATASOURCE_TGTT_TAB_QUYEN_ND(state, grcThoiGian_ND) {
            state.grcThoiGian_ND = grcThoiGian_ND;
        },
        SET_DATASOURCE_TGTT_TEMP_TAB_QUYEN_ND(state, grcThoiGian_ND_temp) {
            state.grcThoiGian_ND_temp = grcThoiGian_ND_temp;
        },
        SET_DATASOURCE_NGAY_DG_TAB_QUYEN_ND(state, grcNgayDaGan) {
            state.grcNgayDaGan = grcNgayDaGan
        },
        SET_DATASOURCE_NGAY_CG_TAB_QUYEN_ND(state, grcNguoiDungCG) {
            state.grcNguoiDungCG = grcNguoiDungCG
        },
        SET_DATASOURCE_NGUOIDUNG_CG_TAB_QUYEN_ND(state, grcNgayChuaGan) {
            state.grcNgayChuaGan = grcNgayChuaGan
        },
        SET_DATASOURCE_NGUOI_DUNG(state, grcNguoiDung) {
            state.grcNguoiDung = grcNguoiDung
        },
        SET_NHOM_ID_TAB_QUYEN_ND(state, vnd_id) {
            state.vnd_id = vnd_id
        },
        SET_NGUOIDUNG_TAB_QUYEN_ND(state, vnguoidung_id) {
            state.vnguoidung_id = vnguoidung_id
        },

    },
    actions: {
        async getDsDonVi({ commit }) {
            try {
                let res = await axios.get('/web-hopdong/ganquyen-gachno/sp_ds_donvi_gn');
                if (res.data.error_code == 'BSS-00000000') {
                    commit("SET_DONVI", res.data.data);
                }
            } catch (error) {
                console.log(error);
            }
        },
        // 
        async SP_DS_QUYEN_GN({ commit }) {
            try {
                let res = await axios.get('/web-hopdong/ganquyen-gachno/sp_ds_quyen_gn');
                if (res.data.error_code == 'BSS-00000000') {
                    commit('SET_QUYENGN', res.data.data);
                }
            } catch (error) {
                console.log(error);
            }
        },
        // Hiển thị lưới thời gian thiết lập, tab danh sách quyền nhóm ND BSS-66793_006-> BSS-66793_007
        async NAP_DS_NHOMND_DG({ commit, state }, params) {
            try {
                // console.log('NAP_DS_NHOMND_DG: ', state.vkieu, state.vnhom_nd_id);
                let res = await axios.post('/web-hopdong/ganquyen-gachno/sp_nap_ds_nhomnd_dg', {
                    vnhomnd_id: params.vnhomnd_id,
                    vkieu: params.vkieu
                });
                if (res.data.data) {
                    commit('SET_DATASOURCE_TGTT', res.data.data);
                } else {
                    commit('SET_DATASOURCE_TGTT', []);
                }

            } catch (error) {
                console.log(error);
            }
        },
        // Hiển thị lưới danh sách ngày đã gán, tab danh sách quyền nhóm ND BSS-66793_008 -> BSS-66793_009
        async LOAD_DS_DG({ commit, state }, params) {
            try {
                let body = {
                        vkieu_giaodien: params.vkieu_giaodien,
                        vnhom_nd_id: params.vnhom_nd_id,
                        vnguoidung_id: state.vnguoidung_id ? state.vnguoidung_id : 99999,
                        vnhom_id: params.vnhom_id,
                        vkieu: params.vkieu
                    }
                
                
                let res = await axios.post('/web-hopdong/ganquyen-gachno/sp_ds_dg_gachno', body);
                if (res.data.error_code == 'BSS-00000000') {
                    if (params.vkieu_giaodien == 1) {
                        commit('SET_DATASOURCE_NGAY_DG', res.data.data);
                    }
                    else if (params.vkieu_giaodien == 2) {
                        commit('SET_DATASOURCE_NGAY_DG_TAB_QUYEN_ND', res.data.data);
                    }
                } else {
                    if (params.vkieu_giaodien == 1) {
                        commit('SET_DATASOURCE_NGAY_DG', []);
                    }
                    else if (params.vkieu_giaodien == 2) {
                        commit('SET_DATASOURCE_NGAY_DG_TAB_QUYEN_ND', []);
                    }
                }
            } catch (error) {
                console.log(error);
            }
        },
        //Hiển thị lưới danh sách ngày chưa gán ( bên tay phải), tab danh sách quyền nhóm ND BSS-66793_010 -> BSS-66793_011
        async NAP_DS_NHOMND_CG({ commit, state }, params) {
            try {
                let res = await axios.post('/web-hopdong/ganquyen-gachno/sp_nap_ds_nhomnd_cg', {
                    vnhomnd_id: params.vnhomnd_id,
                    vkieu: params.vkieu,
                    vnhom_id: params.vnhom_id
                });
                if (res.data.error_code == 'BSS-00000000') {
                    commit('SET_DATASOURCE_NGAY_CG', res.data.data);
                }
            } catch (error) {
                console.log(error);
            }
        },
        //Hiển thị danh sách lưới người dùng, tab danh sách quyền người dùng BSS-66793_012->BSS-66793_013
        async NAP_DS_NGUOIDUNG({ commit, state }, params) {
            try {
                let res = await axios.get(
                    "/web-hopdong/ganquyen-gachno/sp_ds_nguoidung_donvi",
                    {
                        params: {
                            p_donvi_id: params.don_vi_id,
                        },
                    }
                );
                if (res.data.error_code == "BSS-00000000") {
                    commit('SET_DATASOURCE_NGUOI_DUNG', res.data.data);
                } else {
                    commit('SET_DATASOURCE_NGUOI_DUNG', []);
                }
            } catch (error) {
                console.log(error);
            }
        },
        // Hiển thị lưới thời gian thiết lập, tab danh sách quyền người dùng - NAP_DS_NGUOIDUNG_DG - BSS-66793_014-> BSS-66793_015
        async NAP_DS_NGUOIDUNG_DG({ commit, state }, params) {
            try {
                let res = await axios.post('/web-hopdong/ganquyen-gachno/sp_nap_ds_nguoidung_dg', {
                    vnguoidung_id: state.vnguoidung_id,
                    vkieu: params.vkieu
                });
                if (res.data.data) {
                    commit('SET_DATASOURCE_TGTT_TAB_QUYEN_ND', res.data.data);
                } else {
                    commit('SET_DATASOURCE_TGTT_TAB_QUYEN_ND', []);
                }
            } catch (error) {
                console.log(error);
            }
        },
        // Hiển thị lưới danh sách ngày đã gán, tab danh sách quyền người dùng
        // 1. Tham chiếu đến BSS-66793_009, lưu ý kieu = 2 , 2. Tham chiếu đến BSS- 66793_017
        // NAP_DS_KHO_DG
        async NAP_DS_NGAY_DG_TAB_QUYEN_ND({ commit, state }) {
            try {
                let res = await axios.post('/web-hopdong/ganquyen-gachno/sp_nap_ds_kho_dg', {
                    vnguoidung_id: state.vnguoidung_id,
                    vkieu: state.vkieu_nd
                });
                if (res.data.error_code == 'BSS-00000000') {
                    commit('SET_DATASOURCE_NGAY_DG_TAB_QUYEN_ND', res.data.data);
                } else {
                    commit('SET_DATASOURCE_NGAY_DG_TAB_QUYEN_ND', []);
                }
            } catch (error) {
                console.log(error);
            }
        },
        // NAP_DS_KHO_CG BSS-66793_019
        async NAP_DS_NGAY_CG_TAB_QUYEN_ND({ commit, state }) {
            try {
                let res = await axios.post('/web-hopdong/ganquyen-gachno/sp_nap_ds_kho_cg', {
                    vnguoidung_id: state.vnguoidung_id,
                    vkieu: state.vkieu
                });
                if (res.data.error_code == 'BSS-00000000') {
                    commit('SET_DATASOURCE_NGAY_CG_TAB_QUYEN_ND', res.data.data);
                }
            } catch (error) {
                console.log(error);
            }
        },
        // NAP_DS_NGUOIDUNG_CG BSS-66793_020
        async NAP_DS_NGUOIDUNG_CG_TAB_QUYEN_ND({ commit, state }, params) {
            try {
                console.log(params);
                let res = await axios.post('/web-hopdong/ganquyen-gachno/sp_nap_ds_nguoidung_cg', {
                    vnguoidung_id: state.vnguoidung_id ? state.vnguoidung_id : 99999,
                    vkieu: params.vkieu,
                    vnhom_id: params.vnhom_id
                });
                if (res.data.data && res.data.error_code == 'BSS-00000000') {
                    commit('SET_DATASOURCE_NGUOIDUNG_CG_TAB_QUYEN_ND', res.data.data);
                }
            } catch (error) {
                console.log(error);
            }
        },
    }
});