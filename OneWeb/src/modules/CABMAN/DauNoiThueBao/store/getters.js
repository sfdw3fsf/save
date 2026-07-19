/* eslint-disable */

export const getters = {
  gettersThongTinCap: state => {
    return (
      state.ThongTinCap || {
        CULY: null,
        KYHIEU_KC: null,
        VITRI: null,
        VITRI_2: null,
        KYHIEU_CAPGOC: null,
        DOICAP: null,
        DOICAP_2: null
      }
    );
  },
  gettersDanDoCap: state => {
    return state.DanDoCap || { dando_rutgon: [], dando_chitiet: [] };
  },
  gettersThueBao: state => {
    var ds = state.DsThueBao.slice();
    if (ds.length > 0) {
      return ds[0];
    } else return null;
  }
};
