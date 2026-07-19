// các hàm xử lý các thuộc tính của đối tượng trong store
import moment from 'moment';
export const mutations = {
    clearData (state, data) {
        state.DSKhoTheoNV = [];
        state.DSTongHopTB = [];
        state.DSChiTietTB = [];
        state.DSThueBao = [];
        state.DSThietBi = [];
    },
    setDSKhoTheoNV (state, data) {
        state.DSKhoTheoNV = data
    },
    setDSTongHopTB (state, data) {
        data.forEach(a=>{
            a.SOLUONG.toString() ? a.SOLUONG = parseFloat(a.SOLUONG) : 0
            a.DACAP.toString() ? a.DACAP = parseFloat(a.DACAP) : 0
            a.CONLAI.toString() ? a.CONLAI = parseFloat(a.CONLAI) : 0
        })
        state.DSTongHopTB = data
    },
    setDSChiTietTB (state, data) {
        data.forEach(a=>{
            a.DONGIA.toString() ? a.DONGIA = parseFloat(a.DONGIA) : 0
            a.SL_TON.toString() ? a.SL_TON = parseFloat(a.SL_TON) : 0
        })
        state.DSChiTietTB = data
    },
    setDSThueBaoDangCap (state, data) {
        if (Array.isArray (data) && data.length){
            if (data[0]){
                state.DSThueBao = data[0][0];
                state.DSThueBao.forEach(e => {                    
                    e.SOLUONG.toString() ? e.SOLUONG = parseFloat(e.SOLUONG) : 0
                });
            }
            if (data[1]){
                state.DSThietBi = data[1][0];
                state.DSThietBi.forEach(e => {                    
                    e.SOLUONG.toString() ? e.SOLUONG = parseFloat(e.SOLUONG) : 0
                });
            }
        }
        else{
            state.DSThueBao = [];
            state.DSThietBi = [];
        }
        
    },

  }
    