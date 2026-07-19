export const getters = {
    danhSachVersionGetter: (state, getters, rootState) => {
        return state.dsVersion
    },
    danhSachInvGetter: (state, getters, rootState) => {
        return state.dsInv
    },
    danhSachGroupGetter: (state, getters, rootState) => {
        return state.dsGroup
    },
    danhSachFormatGetter: (state, getters, rootState) => {
        return state.dsFormat
    },
    danhSachDataLayoutGetter: (state, getters, rootState) => {
        return state.dsDataLayout
    }
}