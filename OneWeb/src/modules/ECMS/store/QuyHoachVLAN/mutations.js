export const mutations = {
    setDsVungQuyHoach(state, data) {
        state.dsVungQuyHoach = data;
    },
    setDsDichVu(state, data) {
        state.dsDichVu = data;
    },
    setDsLoaiDichVu(state, data) {
        state.dsLoaiDichVu = data;
    },
    setDsQuyHoachVLAN(state, data) {
        state.dsQuyHoachVLAN = data;
    },
    clearState (state) {
        state = null
    }
}