export const mutations = {
    clearState (state) {
        state.dsTaiSan = null,
        state.dsDuAn = null,
        state.dsTtvt = null
    },
    setDsDuAn (state, dsDuAn) {
        state.dsDuAn = dsDuAn
    },
    setDsTTVT (state, dsTtvt) {
        state.dsTtvt = dsTtvt
    },
    setDsTaiSan (state, dsTaiSan) {
        state.dsTaiSan = dsTaiSan
    },
    setDsTaiSanTimKiem (state, dsTaiSanTimKiem) {
        state.dsTaiSanTimKiem = dsTaiSanTimKiem
    },
}
