import Vue from "vue";
import types from "./types";
export default {
  [types.SET_STATE](state, { key, value }) {
    Vue.set(state, key, value);
  },
  [types.SET_TTTHUEBAO](state, data) {
    Vue.set(state, "DS_TTTHUEBAO", data);
  },
  [types.SET_DATAGC](state, data) {
    console.log(
      "🚀 ~ file: mutations.js ~ line 11 ~ data",
      JSON.stringify(data)
    );
    let item = state.DsGC.find(x => x.ID === data.ID);
    console.log(
      "🚀 ~ file: mutations.js ~ line 20 ~ item",
      JSON.stringify(item)
    );
    if (item !== null && item !== undefined) {
      Object.assign(item, data);
    }
  },
  [types.DEL_DATAGC](state, index) {
    state.DsGC.splice(index, 1);
  },
  [types.CLEAR_STATE](state) {
    state.DS_DICHVU_VT = [];
    state.DS_LOAIHINH_TB = [];
    state.DS_LOAI_KH = [];
    state.DS_DOITUONG = [];
    state.DS_HINHTHUC_KN = [];
    state.DS_LOAI_KN = [];
    state.DS_KETQUA_KN = [];
    state.DS_CHUDEKN = [];
    state.DS_KHIEUNAI_HT = [];
    state.DS_TB_KHIEUNAI = [];
    state.DS_TB_KHIEUNAI_DAGIAO = [];
    state.DS_TB_KHIEUNAI_DAKHOA = [];
    state.DS_NHANVIEN = [];
    state.DS_TIENGC = [];
    state.DS_KQ_LOI_KN = [];
    state.DS_CHITIETNO_THEO_KHOANMUC = [];
    state.DS_TTTHUEBAO = [];
    state.DS_HUONGGIAO_LDV = [];
    state.DSThangGC = [];
    state.DSThangApDung = [];
    state.DsGC = [];
    state.DS_MD_HAILONG = [];
    state.DS_HINHTHUC_TL = [];
  }
};
