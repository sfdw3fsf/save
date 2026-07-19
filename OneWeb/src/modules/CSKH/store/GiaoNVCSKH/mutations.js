// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setDsDonVi(state, data) {
        state.dsDonVi = data
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
    },
    ganTatCaNV(state, data) {
        let tmp = state.dsChuaGan
        state.dsDaGan.forEach(e => tmp.push(e))
        if (data == 1) {
            state.dsChuaGan = []
            state.dsDaGan = tmp
        }
        else {
            state.dsChuaGan = tmp
            state.dsDaGan = []
        }
    },
}
