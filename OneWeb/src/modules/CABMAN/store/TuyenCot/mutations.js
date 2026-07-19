export const mutations = {
    clearState: function(state) {
        state = {
            dsTTVT: null,
            dsToKT: null,
            dsCot: null,
            dsTQLChuaGan: null,
            dsTQLDaGan: null
        }
    },
    setDsTrungTamVienThong: function(state, data) {
        state.dsTTVT = data;
    },
    setDsToKyThuat: function(state, data) {
        state.dsToKT = data;
    },   
    setDsTuyenCot: function(state, data) {
        state.dsTuyenCot = data;
    },
    setDsCot: function(state, data) {
        state.dsCot = data;
    },
    setDsTQLChuaGan: function(state, data) {
        state.dsTQLChuaGan = data;
    },
    setDsTQLDaGan: function(state, data) {
        state.dsTQLDaGan = data;
    }
}
