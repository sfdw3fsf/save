export const mutations = {
    setPageInfo(state, data) {
        state.pageInfo = data
    },
    setPageInfoDsThongTinThueBaoNo(state, data) {
        state.pageInfoDsThongTinThueBaoNo = data
    },
    setDsThueBaoKhongKhoaMo(state, data) {
        state.dsThueBaoKhongKhoaMo = data
    },
    setDsThongTinThueBaoNo(state, data) {
        state.dsThongTinThueBaoNo = data
    },
    setObjSearch(state, data) {
        state.objSearch = data
    },
    clearData(state) {
        state.dsLichSuPhanAnh = []
        state.objSearch = {}
        state.dsThueBaoKhongKhoaMo = []
        state.dsThongTinThueBaoNo = []
    },
    changeIsCheckStatus(state, data) {
        console.log('data = ' + data)
        for (let i = 0; i < state.dsThueBaoKhongKhoaMo.length; i++) {
            if (state.dsThueBaoKhongKhoaMo[i].RDS_ID == data)
            {
                state.dsThueBaoKhongKhoaMo[i].isCheck = !state.dsThueBaoKhongKhoaMo[i].isCheck
            }
        }
    },
    changeIsCheckStatusAll(state) {
        state.dsThueBaoKhongKhoaMo.forEach(element => {
            console.log(element.isCheck)
            element.isCheck = !element.isCheck
            console.log(element.isCheck)
        })
    }
}