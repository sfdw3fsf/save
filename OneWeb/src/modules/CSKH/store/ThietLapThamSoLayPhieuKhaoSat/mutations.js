export const mutations = {
    setDsThietLapThoiGian(state, data) {
        state.dsThietLapThoiGian = data
    },
    addThietLapThoiGianRecord(state, data) {
        let ds = state.dsThietLapThoiGian
        ds.push(data)
        state.dsThietLapThoiGian = ds
    },
    removeThietLayThoiGianRecordById(state, data) {
        state.dsThietLapThoiGian = state.dsThietLapThoiGian.filter(x => x.ID != data)
    },
    clearData(state, data) {
        state.dsThietLapThoiGian = []
    },
}