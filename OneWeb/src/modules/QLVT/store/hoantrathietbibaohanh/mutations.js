// các hàm xử lý các thuộc tính của đối tượng trong store
export const mutations = {
    setDanhSachNhanVien (state, data) {
      state.danhSachNhanVien = data
    },
    setDSPhieuBH (state, data) {
      state.danhSachPhieuBH = data
    },
    setObjSearch (state, data) {
      state.objSearch = data
    },
    setThongTinBaoHanhTB (state, data) {
      state.thongTinBaoHanhTB = data
    },
    setMaToTheoDonVi (state, data) {
      state.maToObj = data
    },
    clearData (state, data) {
      state.danhSachNhanVien = [];
      state.danhSachPhieuBH = [];
      state.maToObj = {};
      state.objSearch = {};
      state.thongTinBaoHanhTB = "";
      state.danhSachThuHoiTB = []
    //   state.thongTinTT = null
    },
    clearDanhSachPhieuBH (state, data) {
      state.danhSachPhieuBH = [];
      state.thongTinBaoHanhTB = "";
      state.danhSachThuHoiTB = []
    //   state.thongTinTT = null
    },
    
    
  }
  