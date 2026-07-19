// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setDsQuanHuyen(state, data) {
        state.dsQuanHuyen = data;
    },
    setDsDonVi(state, data) {
        state.dsDonVi = data;
    },
    setDsNhanVien(state, data) {
        state.dsNhanVien = data;
    },
    setDsChuongTrinhCSKH(state, data) {
        state.dsChuongTrinhCSKH = data;
    },
    setDsKHL(state, data) {
        state.dsKHL = data;
    },
    setDsDVKHL(state, data) {
        state.dsDVKHL = data;
    },
    clearData(state, data) {
        state.dsKHL = [];
    },
    clearDataDV(state, data) {
        state.dsDVKHL = [];
    },
    setToltalElement(state, data) {
        state.totalElements = data;
    },
    setDsKHLTotalElements(state, data) {
        state.dsKHLTotalElements = data;
    },
    
};
