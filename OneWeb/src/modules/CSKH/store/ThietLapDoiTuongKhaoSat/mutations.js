export const mutations = {
    setDSDichVuKS (state, data) {
        state.dsDichVuKS = data
    },
    setDSDichVuKhongKS (state, data) {
        state.dsDichVuKhongKS = data
    },
    setDSKieuLDKS (state, data) {
        state.dsKieuLDKS = data
    },
    setDSKieuLDKhongKS (state, data) {
        state.dsKieuLDKhongKS = data
    },
    setDSTinhTrangHongKS (state, data) {
        state.dsTinhTrangHongKS = data
    },
    setDSTinhTrangHongKhongKS (state, data) {
        state.dsTinhTrangHongKhongKS = data
    },
    add(state, data) {
        data.data.forEach(element => data.source.unshift(element))
    },
    remove(state, data) {
        data.data.forEach(element => {
            let index = data.source.indexOf(element)
            data.source.splice(index, 1)
        })
    },
}