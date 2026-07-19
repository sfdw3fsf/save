import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        // tabs
        JS_TINH: [],
        JS_TINH_BK: [],
        JS_LHTB: [],
        JS_LHTB_BK: [],
        JS_KENH: [],
        JS_KENH_BK: [],

        // popup
        DS_DONVI: [],
        DS_DICHVU_VT: [],
        DS_LOAIHINH_TB: [],
        DS_TV_KENH: [],
    },
    getters: {
        // getThoiGian_ND_Temp: state => {
        //     return state.grcThoiGian_ND_temp;
        // },
    },
    mutations: {
        // popup
        SET_DS_DONVI(state, ds_donvi){

            state.DS_DONVI = ds_donvi;
            console.log(state);
        },
        SET_DS_DICHVU_VT(state, ds_dichvu_vt) {
            state.DS_DICHVU_VT = ds_dichvu_vt;
        },
        SET_DS_LOAIHINH_TB(state, ds_loaihinh_tb) {
            state.DS_LOAIHINH_TB = ds_loaihinh_tb;
        },
        SET_DS_TV_KENH(state, ds_tv_kenh) {
            state.DS_TV_KENH = ds_tv_kenh;
        },
        // tabs
        SET_JS_TINH(state, js_tinh){
            state.JS_TINH = js_tinh;
        },
        SET_JS_TINH_BK(state, js_tinh) {
            state.JS_TINH_BK = js_tinh;
        },
        SET_JS_LHTB(state, js_lhtb) {
            state.JS_LHTB = js_lhtb;
        },
        SET_JS_LHTB_BK(state, js_lhtb) {
            state.JS_LHTB_BK = js_lhtb;
        },
        SET_JS_KENH(state, js_kenh) {
            state.JS_KENH = js_kenh;
        },
        SET_JS_KENH_BK(state, js_kenh) {
            state.JS_KENH_BK = js_kenh;
        },
    },
    actions: {
    }
});