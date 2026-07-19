export const mutations = {
  setDsBras (state, dsBras) {
        state.dsBras = dsBras
    },
    setBras (state, chiTietBras) {
        state.chiTietBras = chiTietBras
    },
    setBrasNew (state, brasNew) {
        state.brasNew = brasNew
    },
    clearState (state) {
        state.dsBras = null,
        state.chiTietBras=null,
        state.brasNew=null
    }
}
