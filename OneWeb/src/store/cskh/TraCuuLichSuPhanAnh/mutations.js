// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setDSLichSuPhanAnh(state, data) {
        state.dsLichSuPhanAnh = data
    },
    setObjSearch(state, data) {
        state.objSearch = data
    },
    clearData(state) {
        state.dsLichSuPhanAnh = []
        state.objSearch = {}
    },
    setTotalPage(state, data) {
        state.totalPage = data
    },
    setTotalItems(state, data) {
        state.totalItems = data
    },
    setPageSize(state, data) {
        state.pageSize = data
    }
}
