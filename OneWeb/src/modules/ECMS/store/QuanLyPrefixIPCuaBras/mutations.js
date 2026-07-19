export const mutations = {
    setDsPrefixIp (state, dsPrefixIp) {
        state.dsPrefixIp = dsPrefixIp
    },
    setDsLoaiTbDaGan (state, dsLoaiTbDaGan) {
        state.dsLoaiTbDaGan = dsLoaiTbDaGan
    },
    setDsLoaiTbChuaGan (state, dsLoaiTbChuaGan) {
        state.dsLoaiTbChuaGan = dsLoaiTbChuaGan
    },
    setDsIp (state, dsIp) {
        state.dsIp = dsIp
    },
    setDsBras (state, dsBras) {
        state.dsBras = dsBras
    },
    setDsDichVuVt (state, dsDichVuVt) {
        state.dsDichVuVt = dsDichVuVt
    },
    setDsLoaiIp (state, dsLoaiIp) {
        state.dsLoaiIp = dsLoaiIp
    },
    clearState (state) {
        state.dsPrefixIp = []
        state.dsLoaiTbDaGan = []
        state.dsLoaiTbChuaGan = []
        state.dsIp = []
        state.dsBras = []
        state.dsLoaiIp = []
        state.dsDichVuVt = []
    }
}