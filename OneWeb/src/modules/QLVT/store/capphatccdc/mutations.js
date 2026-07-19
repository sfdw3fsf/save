// các hàm xử lý các thuộc tính của đối tượng trong store
import moment from "moment"
export const mutations = {
    
    clearData (state, data) {
        // state.DSTongHopCCDC= []
        // state.DSChiTietCCDC= []
        // state.DSChungTu= []
        state.DSTinhTrangTBi= []
        // state.DSDonVi= []
        // state.DSNhanVien= []
        state.DSMucDichPhanBo = []
    },
    
    
    // clearDSChiTietCCDC(state, data) {
    //     state.DSChiTietCCDC= []
    // },
    
    setDSTinhTrangTBi(state, data) {
        state.DSTinhTrangTBi = data;
    },
    // setDSDonVi(state, data) {
    //     state.DSDonVi = data;
    // },
    
    // setDSNhanVien(state, data) {
    //     state.DSNhanVien = data;
    // },
    setDSMucDichPhanBo(state, data) {
        state.DSMucDichPhanBo = data;
    },
    // setDSCapCCDC(state, data) {
    //     data.forEach(a=>a.TIEN = parseInt(a.TIEN) || 0)
    //     state.DSChungTu = data;  
    // },
    // setDSChiTietCCDC(state, data) {
    //     data.forEach(a=>{
    //         a.DONGIA = parseInt(a.DONGIA) || 0
    //         a.SOTHANG_PB = parseInt(a.SOTHANG_PB) || 0
    //         a.TIEN_PB = parseInt(a.TIEN_PB) || 0

    //     })
    //     state.DSChiTietCCDC = data;  
    // },
    // setDSTongHopCCDC(state, data) {
    //     data.forEach(a=>{
    //         a.TIEN = parseInt(a.TIEN) || 0;
    //         a.SOLUONG = parseInt(a.SOLUONG) || 0;
    //         a.DONGIA = parseInt(a.DONGIA) || 0;
    //     })
    //     state.DSTongHopCCDC = data;  
    // },
  }
  