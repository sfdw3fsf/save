// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setDsCauHoi(state, data) {
        state.DsCauHoi = data
    },
    setDsThueBaoKS(state, data) {
        state.DsThueBaoKS = data
    },
    setDsLyDoKTL(state, data) {
        state.DsLyDoKTL = data
    },
    clearData(state) {
        state.DsCauHoi = []
        state.DsThueBaoKS = []
        state.DsLyDoKTL = []
    },
    clearDsPhieuLH(state) {
        state.DsPhieuLH = []
    },
}
