// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setDsLoaiDonVi(state, data) {
        state.dsLoaiDonVi = data
    },
    setDsChuongTrinh(state, data) {
        state.dsChuongTrinh = data
    },
    setDsDaGan(state, data) {
        state.dsDaGan = data
    },
    setDsChuaGan(state, data) {
        state.dsChuaGan = data
    },
    ganTatCaDV(state, data) {
        let tmp = state.dsChuaGan
        tmp.forEach(element => {
            element.NOIDUNG = data.noiDung
        })
        state.dsDaGan.forEach(e => tmp.push(e))
        if (data.setTatCa == 1) {
            state.dsChuaGan = []
            state.dsDaGan = tmp
        }
        else if (data.setTatCa == 2) {
            state.dsChuaGan = tmp
            state.dsDaGan = []
        }
    },
    add(state, data) {

        data.data.forEach(element => {
            element.NOIDUNG = data.txt
            data.source.push(element)
        })
    },
    remove(state, data) {
        data.data.forEach(element => {
            let index = data.source.indexOf(element)
            data.source.splice(index, 1)
        })
    },
    clearData(state, data) {
        state.dsChuongTrinh = [],
        state.dsDaGan = [],
        state.dsChuaGan = []
    }
}
