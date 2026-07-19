// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setDSTTBHDich (state, data) {
      state.DSTrangThaiBHDich = data
    },
    setDSBaoHanhTBHienTai (state, data) {
      state.DSBaoHanhTBHienTai = data
    },
    setThongTinTrangThaiBH (state, data) {
      state.ThongTinTrangThaiBH = data
    },
    setDSKhoNhaCungCap (state, data) {
      state.DSKhoNhaCungCap = data
    },

    clearData (state, data) {
      state.DSTrangThaiBHDich = [];
      state.DSBaoHanhTBHienTai = [];
      state.ThongTinTrangThaiBH = [];
      state.DSKhoNhaCungCap = [];
    },

    setXuLyTBBH (state, data){
        state.luong_id = data.luong_id;
        state.quytrinh_id = data.quytrinh_id;
        state.trangthaibh_id_n = data.trangthaibh_id_n;
        state.trangthai_bh_n = data.trangthai_bh_n;
    }
    
  }
    