// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {

    setDSDotDangKy(state, data) {
        state.DSDotDangKy = data
    },
    setDSDonVi(state, data) {
        state.DSDonVi = data
    },
    setDSMucDich(state, data) {
        state.DSMucDich = data
    },
    setDSDotTongHop(state, data) {
        state.DSDotTongHop = data
    },
    // setDSDangKy(state, data) {
    //     state.DSDangKy = data
    // },
    // setDSTongHop(state, data) {
    //     state.DSTongHop = data
    // },
    setKiemTraYeuCauTongHop(state, data) {
        state.kTTongHop = data
    },
    setKiemTraNgayDuyet(state, data) {
        state.kTNgayDuyet = data
    },
    setDuyetHuyDuyetTongHop(state, data) {
        state.kqDuyetHuyDuyet = data
    },
    setKiemTraHopDongDaLap(state, data) {
        state.kiemTraLapHopDong = data
    },
    setKiemTraChungTuDaLap(state, data) {
        state.kiemTraLapChungTu = data
    },
    setKiemTraCungChuKy(state, data) {
        state.ktCungChuKy = data
    },
    setTongHopNhuCauDangKy(state, data) {
        state.ktKetQuaTongHop = data
    },
    setXoaTongHopDangKy(state, data) {
        state.ktXoaTongHop = data
    },
    setCapNhatGhiChu(state, data) {
        state.ktCapNhatGhiChu = data
    },
    clearData(state, data) {
        state.DSDangKy = [];
        state.DSTongHop = [];
        state.kTTongHop = 0;
        state.kTNgayDuyet = -1;
        state.kqDuyetHuyDuyet = -1;
        state.kiemTraLapHopDong = [];
        state.kiemTraLapChungTu = [];
        state.ktCungChuKy = [];
        state.ktKetQuaTongHop = 0;
        state.ktXoaTongHop = 0;
        state.ktCapNhatGhiChu = 0;
    }
}
