export const mutations = {
    setDanhSachCongNo(state, data) {
        state.dsCongNo = data
    },
    setDanhSachNhanVienDaGan(state, data) {
        state.dsNhanVienDaGan = data
    },
    setDanhSachNhanVienChuaGan(state, data) {
        state.dsNhanVienChuaGan = data
    },
    add(state, data) {
        data.data.forEach(element => data.source.push(element))
    },
    remove(state, data) {
        data.data.forEach(element => {
            let index = data.source.indexOf(element)
            data.source.splice(index, 1)
        })
    },
    clearGrid(state, data) {
        state.dsNhanVienDaGan = []
        state.dsNhanVienChuaGan = []
    },
    setAllNhanVienDaGan(state, data) {
        state.dsAllNhanVienDaGan = data
    }
}