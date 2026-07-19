import API from "../../api/GanNVChamSocKHLApi";
import { currency } from "@/filters/currency";
export const actions = {
    async getDsQuanHuyen({ commit, state }, data) {
        try {
            let response = await API.getDsQuanHuyen(this._vm.axios);
            let ds = response.data.data || [];
            let nganhNghe = [];
            ds.forEach(element => {
                nganhNghe.push({
                    id: element.QUAN_ID,
                    text: element.TEN_QUAN
                });
            });
            commit("setDsQuanHuyen", nganhNghe);
            return response;
        } catch (error) {
            commit("setDsQuanHuyen", []);
            return error.response.data;
        }
    },
    async getDsDonVi({ commit, state }, data) {
        try {
            let response = await API.getDsDonVi(this._vm.axios, data);
            let ds = response.data.data || [];
            let nganhNghe = [];
            ds.forEach(element => {
                nganhNghe.push({
                    id: element.DONVI_ID,
                    text: element.TEN_DV
                });
            });
            commit("setDsDonVi", nganhNghe);
            return response;
        } catch (error) {
            commit("setDsDonVi", []);
            return error.response.data;
        }
    },
    async getDsNhanVien({ commit, state }, data) {
        try {
            let response = await API.getDsNhanVien(this._vm.axios, data);
            let ds = response.data.data.data || [];
            let nganhNghe = [];
            ds.forEach(element => {
                nganhNghe.push({
                    id: element.NHANVIEN_ID,
                    text: element.TEN_NV
                });
            });
            commit("setDsNhanVien", nganhNghe);
            return response;
        } catch (error) {
            commit("setDsNhanVien", []);
            return error.response.data;
        }
    },
    async getDsChuongTrinhCSKH({ commit, state }, data) {
        try {
            let response = await API.getDsChuongTrinhCSKH(this._vm.axios, data);
            let ds = response.data.data.data || [];
            let nganhNghe = [];
            ds.forEach(element => {
                nganhNghe.push({
                    id: element.CTCSKH_ID,
                    text: element.TEN_CT
                });
            });
            commit("setDsChuongTrinhCSKH", nganhNghe);
            return response;
        } catch (error) {
            commit("setDsChuongTrinhCSKH", []);
            return error.response.data;
        }
    },
    async getDsKHL({ commit, state }, data) {
        try {
            let response = await API.getDsKHL(this._vm.axios, data);
            let ds = response.data.data.data || [];
            ds.forEach(e => (e.BINHQUAN_DT_TEXT = currency(e.BINHQUAN_DT)));
            commit("setDsKHL", ds);
            commit("setDsKHLTotalElements", response.data.data.totalElement);
            return response;
        } catch (error) {
            commit("setDsKHL", []);
            return error.response.data;
        }
    },
    async getDsDVKHL({ commit, state }, data) {
        try {
            let response = await API.getDsDVKHL(this._vm.axios, data);
            if (response.data.error_code === "BSS-00000000") {
                let ds = response.data.data.data || [];
                ds.forEach(e => (e.BINHQUAN_DT_TEXT = currency(e.BINHQUAN_DT)));
                commit("setDsDVKHL", ds);
                commit("setToltalElement", response.data.data.totalElement);
                return response;
            } else {
                this.$toast.error(
                    response.data.error_code
                        ? response.data.error_code
                        : response.data.message
                );
            }
        } catch (error) {
            commit("setDsDVKHL", []);
            return error.response.data;
        }
    },
    clearData({ commit, state }, data) {
        commit("clearData");
    },
    clearDataDV({ commit, state }, data) {
        commit("clearDataDV");
    }
};
